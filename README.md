home.jsp에서  jstl 사용할 때 <c:set > 사용하지 않고 <fnt: >사용한 이유 정리 
일간 근무 시간, 주간 근무 시간 계산하는 로직 (List<map>의 형식으로 존재하는 이유 설명 )
연장 근무 시간, 특별 근무 시간 작성 
-------------------------------------------------------
DailyWorkTimeServiceImpl
package com.springCommunity.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springCommunity.dao.DailyWorkTimeDAO;
import com.springCommunity.vo.DailyWorkTimeVO;

@Service
public class DailyWorkTimeServiceImpl implements DailyWorkTimeService {

	@Autowired
	private DailyWorkTimeDAO dailyWorkTimeDAO;
	//sqlSession >> 쿼리를 실행하는 객체
	//세션과 해당 쿼리의 값을 가져오기 위해 세션과 정적변수 선언 
	@Autowired
	private SqlSession sqlSession;
	public final String namespace = "com.springCommunity.mapper.DailyWorkTimeMapper.";
	// 회사의 위도와 경도는 Service에서 관리
	private final double COMPANY_LATITUDE = 35.8402587260868; // 예: 전주 이젠 위도
	private final double COMPANY_LONGITUDE = 127.132499131298; // 예: 전주 이젠 경도
	private final double CHECK_IN_DISTANCE_KM = 5.0; // 반경 1km

	// 지구 반지름 
	private static final double EARTH_RADIUS = 6371.0;
	
	
	@Override
	public boolean checkIn(DailyWorkTimeVO dailyWorkTimeVO, String latitude, String longitude) {
		
		int count = sqlSession.selectOne(namespace + "DailyCheckIn",dailyWorkTimeVO);
		double userLat = Double.parseDouble(latitude); // 유저의 위도
		double userLon = Double.parseDouble(longitude); // 유저의 경도

		double distance = calculateDistance(COMPANY_LATITUDE, COMPANY_LONGITUDE, userLat, userLon);
		System.out.println("beford if문 distance=======================" + distance);
		// 메소드 distance를 통해 얻은 경도가 상수의 반경보다 작을 경우 DAO에게 위도와 경도 값을 빼고 전달
		if (distance <= CHECK_IN_DISTANCE_KM) {
			System.out.println("distance=======================" + distance);
			if(count == 0 ) {
				dailyWorkTimeDAO.checkIn(dailyWorkTimeVO); // 거리 범위 내면 데이터 저장
				return true;
			}else {
				System.out.println("이미 존재하는 출근 기록입니다.");
			}
		}
		return false; // 거리 범위 밖이면 저장하지 않음
	}
	
	@Override
	public boolean checkOut(DailyWorkTimeVO dailyWorkTimeVO, String latitude, String longitude) {
		double userLat = Double.parseDouble(latitude); // 유저의 위도
		double userLon = Double.parseDouble(longitude); // 유저의 경도

		double distance = calculateDistance(COMPANY_LATITUDE, COMPANY_LONGITUDE, userLat, userLon);
		System.out.println("beford if문 distance=======================" + distance);
		// 메소드 distance를 통해 얻은 경도가 상수의 반경보다 작을 경우 DAO에게 위도와 경도 값을 빼고 전달
		if (distance <= CHECK_IN_DISTANCE_KM) {
			System.out.println("distance=======================" + distance);
			dailyWorkTimeDAO.checkOut(dailyWorkTimeVO);
				 // 거리 범위 이내이면 , 퇴근 정보 저장 
				return true;
			}else {
				System.out.println("거리를 벗어났습니다.");
			}
		return false; // 거리 범위 밖이면 저장하지 않음
	}

	// 거리 계산 메소드 
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // 위도와 경도를 라디안으로 변환
        double lat1Rad = Math.toRadians(lat1); 	// 회사 위도 
        double lon1Rad = Math.toRadians(lon1); 	// 회사 경도
        double lat2Rad = Math.toRadians(lat2);	// 사용자 위도 
        double lon2Rad = Math.toRadians(lon2);	// 사용자 경도

        // 위도 및 경도 차이 계산
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Haversine 공식을 사용하여 거리 계산
        double a = Math.pow(Math.sin(deltaLat / 2), 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 거리 계산 (단위: km)
        double distance = EARTH_RADIUS * c;
        return distance;
    }
   

    // 해당 유저의 전체 출퇴근 시간을 가져오는 메소드 
	@Override
	public List<DailyWorkTimeVO> selectList (String user_id) {

		return dailyWorkTimeDAO.selectList(user_id);
	}
/*	
	// 앞서 가져온 전체 출퇴근 시간을  이용해서
	// 일별 근무 시간, 주간 근무 시간을 계산함 

	1. 해당 유저의 전체 출퇴근 시간을 가져옴 
	2. 일간 근무 시간 계산
	3. 주간 근무 시간 계산 (월요일 기준으로 ) 
*/
/*
	 readMe 정리해야할 부분 
	 하루 근무 시간 & 주간 시간 계산 및  
	 	// Long::sum은 long 타입의 값을 더하는 함수, 즉 두 개의 Long 값을 합산하는 역활을 하며, Map의 merge메서드에서 사용
    	//함수형 인터페이스 >> 
  	 	//Bifunction >> 두개의 입력을 받아 하나의 결과를 반환하는 함수형 인터페이스 
    	//Long::sum >> Bifunction을 간결하게 표현
    	>> localDateTime 과 localTime의 차이는 ???>  
     	정리 
 */
	
/*
    월요일부터 금요일까지의 날짜 데이터를 가져오는 메소드가 필요하고 
     그 내용을 이용해서 ,코드를 수정해야함
     주간 근무 시간 계산시 > merge를 사용함  
	merge할 때 주의점, 키의 이름이 동일하면 이번 주 근무 기록과 다음 주 근무 기록이 합산될 수 있음(유의) 
*/
/* 기존 코드에서 수정해야하는 내용 
	현재 calculateWorkTime 메소드 내부의 weekKey 키 이름이  너무 어려움 
	또한 weekKey를 조회하면 키 이름이 예를들어 2024.12.23~2024.12.29 이런 형식으로 이루어져 있는데, 
	1. 날짜를 정수형 배열에 담고 , sort를 이용해서 오름차순으로 정렬 
	2. 오름차순으로 정렬한 배열에서 반복문 돌려서 , i가 x일 때, Stirng weekKey = "a 주차 키 " 이런식으로 if -else 사용 또는 subString 사용해서 생성 
 */
	
	@Override
	public Map<String, Object> calculateWorkTime(String user_id) {
	
		//1. 해당 유저의 전체 출퇴근 시간을 가져옴
		List<DailyWorkTimeVO> list =dailyWorkTimeDAO.selectList(user_id);
		
		// 근무 시간 변수 선언 
		Map<String, Long> dailyWorkHours = new HashMap <>(); 		// 일간 근무 시간
		Map<String, Long> weeklyWorkHours = new HashMap <>();		// 주간 근무 시간
		
		// 기존 코드에 추가된 내용 (x주차별 키 생성 관련 변수 )
		// DateTimeFormatter 클래스는 날짜와 시간을 원하는 형식으로 출력 및 해석하는데 사용
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		//
		List<LocalDate> sortDatesList = new ArrayList<>();
		
		// 일간 근무 시간 계산 및 날짜 수집 
		// LocalDateTime > 날짜와 시간을 함께 표현한다.
		// 해당 회원의 출퇴근 기록이 담긴 리스트에서 , 반복문을 돌려 출근 시간을 가져온 후 날짜와 시간꺄지 포맷, 포맷된 데이터를 toLocalDate()메소드 이용해서 변환 후 , LocalDate타입의 리스트에 삽입한다. 
		list.forEach(workTime -> {
			// DB를 조회하고 list에 담겨진 출근 시간의 기록을 dateStr에 담는다.  
            String dateStr = workTime.getCheck_in_time(); 						 // ex: "2024-12-17 20:30:06"
            //담겨진 dateStr을  dateTime이라는 변수에 시간만 담는다. 
            // LocalDateTime >>날짜와 시간 정보를 모두 포함
            LocalDateTime  dateTime = LocalDateTime .parse(dateStr, formatter);  //  ex: "2024-12-17 20:30:06"
            // LocalDate >>날짜 정보를 포함
            LocalDate localDate = dateTime.toLocalDate();
            //리스트에 날짜를 넣음 
            sortDatesList.add(localDate);
            
            // 하루 근무 시간 계산
            // dailyHours >> 하루 근무 시간의 값 
            //calculateDailyHours >> 하루 근무 시간 메소드 : 해당하는 일자의 시간을 계산하고 분으로 표현한다. 
            long dailyHours = calculateDailyHours(workTime.getCheck_in_time(), workTime.getCheck_out_time()); 

            /*
             forEach가 만족할 때까지 , 
             	위에서 선언한 일간 근무시간 맵에 문자열로 변환한 localDate를 키로 , 계산한 dailyHours값(분)을 맵에 담는다. 
             */
            // 맵에 키와 값을 넣음
            // 키는 해당 날짜를 문자열로 변환한 것 , 값은 해당 날짜의 근무시간을 분으로 표시한 값 
            dailyWorkHours.put(localDate.toString(), dailyHours);
		});    
		
            
            // 날짜 정렬
			//LocalDate(일과 날짜 )
			sortDatesList.sort(Comparator.naturalOrder());
            
            // 주차별 키 생성 및 근무 시간 합산 
			sortDatesList.forEach(date -> {
				// 국제 표준 규격 사용 
                int weekOfYear = date.get(WeekFields.ISO.weekOfYear());
                String weekKey = date.getYear() + "년 " + weekOfYear + "주차";
                
                //	
                long dailyHours = dailyWorkHours.getOrDefault(date.toString(), 0L);
                //merge >> String key, Long value, BiFunction
                weeklyWorkHours.merge(weekKey, dailyHours, Long::sum);
            });
 
            // 결과 반환 
			Map<String, Object> result = new HashMap<>();
	        result.put("dailyWorkHours", dailyWorkHours);
	        result.put("weeklyWorkHours", weeklyWorkHours);
	        return result;
	}
	
	
    // 유틸 메서드: 하루 근무시간 계산
    private long calculateDailyHours(String checkInTime, String checkOutTime) {
    	// 만약 출근 or 퇴근 시간 둘 중 하나라도 null이면 시간 계산을 할 수 없음 
    	if (checkInTime == null || checkOutTime == null ) {
    		System.out.println("둘 중 하나가 null이라서 시간 계산을 할 수 없음 ");
    		return 0;
    	}
    	
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime  start = LocalDateTime .parse(checkInTime, formatter);
        LocalDateTime  end = LocalDateTime .parse(checkOutTime, formatter);
        
        return Duration.between(start, end).toMinutes(); // 시간 단위로 반환 >> 버림처리 
        // 따라서 시간 ,분을 이용해서 반환하게끔 설정
        // 초 단위로는 버림 
        //시간과 , 분은 버리지 않고 가져오게끔 설정 
    }
	
	
	
	
}
