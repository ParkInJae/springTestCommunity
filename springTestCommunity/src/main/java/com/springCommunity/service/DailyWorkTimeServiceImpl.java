package com.springCommunity.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	// 공휴일 가져오는 메소드 
	
	
	@Override
	public Map<String, Object> calculateWorkTime(String user_id) {
		//1. 해당 유저의 전체 출퇴근 시간을 가져옴
		List<DailyWorkTimeVO> list =dailyWorkTimeDAO.selectList(user_id);
		
		for(DailyWorkTimeVO item : list) {
			System.out.println("--------------------");
			System.out.println(item.toString());
		}
		
		// 근무 시간 변수 선언 
		Map<String, Long> dailyRegularWorkHours = new HashMap <>(); 	// 일간 정규 근무 시간
		Map<String, Long> weeklyRegularWorkHours = new HashMap <>();	// 주간 정규 근무 시간
		
		Map<String, Long> dailyExtendWorkHours = new HashMap <>();		// 일간 연장 근무 시간
		Map<String, Long> weeklyExtendHours = new HashMap <>();   		// 주간 연장 근무 시간

		Map<String, Long> dailySpecialWorkHours = new HashMap <>();		// 일간 특별 근무 시간
		Map<String, Long> WeeklySpecialWorkHours = new HashMap <>();	// 주간 특별 근무 시간
		
		
		 // 공휴일 리스트 (예시로 2025년 공휴일을 추가)
	    Set<LocalDate> holidays = new HashSet<>(Arrays.asList(
	        LocalDate.of(2025, 1, 1),  // 새해
	        LocalDate.of(2025, 3, 1),  // 삼일절
	        LocalDate.of(2025, 5, 5),  // 어린이날
	        LocalDate.of(2025, 6, 6),  // 현충일
	        LocalDate.of(2025, 8, 15), // 광복절
	        LocalDate.of(2025, 10, 3), // 개천절
	        LocalDate.of(2025, 10, 9), // 한글날
	        LocalDate.of(2025, 12, 25) // 크리스마스
	    ));
		
		
		// 기존 코드에 추가된 내용 ( n주차별 키 생성 관련 변수 )
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");		// DateTimeFormatter 클래스는 날짜와 시간을 원하는 형식으로 출력 및 해석하는데 사용
		List<LocalDate> sortDatesList = new ArrayList<>();//년 월 일 을 담는 리스트 선언
		
		// 각 근무 시간 처리 
		list.forEach(workTime -> {
            String checkInStr  = workTime.getCheck_in_time(); 	// 출근 시간 ex: "2024-12-17 20:30:06"
            String checkOutStr = workTime.getCheck_out_time();	// 퇴근 시간 
            if (checkInStr == null || checkOutStr == null) {
                System.out.println("출근 또는 퇴근 시간이 null입니다.");
                return; // null 데이터는 건너뜀
                }
            
            LocalDateTime  CheckIn = LocalDateTime .parse(checkInStr, formatter);  		//  LocalDateTime >>날짜와 시간 정보를 모두 포함  ex: "2024-12-17 20:30:06"
            LocalDateTime  checkOut  = LocalDateTime .parse(checkOutStr, formatter);  	//  LocalDateTime >>날짜와 시간 정보를 모두 포함  ex: "2024-12-17 20:30:06"
            LocalDate localDate = CheckIn.toLocalDate(); 						 		// LocalDate >>날짜 정보를 포함
            sortDatesList.add(localDate);	            						 		//리스트에 날짜를 넣음

            // 기준 시간 18:00:00 설정
            LocalDateTime cutoffTime = LocalDateTime.of(localDate, LocalTime.parse("18:00:00"));
            
            // 18:00 이전과 이후 근무 시간 계산
            long dailyMinutes = calculateDailyHours(checkInStr, checkOutStr);
            if (checkOut.isBefore(cutoffTime)) {
                // 18:00 이전 퇴근
                dailyRegularWorkHours.put(localDate.toString(), dailyMinutes);  // 일간 정규 근무 시간에 (키, 값) 대입
            } else {
                // 18:00 이후 퇴근 (연장 근무)
                long extendedMinutes = Duration.between(cutoffTime, checkOut).toMinutes();  // 초과 시간 계산
                dailyExtendWorkHours.put(localDate.toString(), extendedMinutes); // 일간 연장 근무 시간에 (키, 값) 대입
            }

            // 일요일 또는 공휴일에 근무하면 특근 처리
            if (localDate.getDayOfWeek() == DayOfWeek.SUNDAY || holidays.contains(localDate)) {
                dailySpecialWorkHours.put(localDate.toString(), dailyMinutes); // 일간 특별 근무 시간에 (키, 값) 대입
            }
        });
		
		System.out.println("sortDatesList 원소 개수 : " + sortDatesList.size());
        // 날짜 정렬
		sortDatesList.sort(Comparator.naturalOrder());
		
		sortDatesList.forEach(item -> {
			System.out.println(item);
		});
        // 주차별 키 생성 및 근무 시간 합산 
		sortDatesList.forEach(date -> {
			// 주차별 키 생성 
            int weekOfYear = date.get(WeekFields.ISO.weekOfYear());
            String weekKey = date.getYear() + "년 " + weekOfYear + "주차";
            System.out.println("weekKey : "+weekKey);
            //merge >> String key, Long value, BiFunction
            
            /*
             	원리 >
             	1) forEach를 활용하면서 반복   
             	2) 주차별 키를 생성 
             	3) 해당 주차에 대한 일간 (정규 근무 , 연장 근무, 특별 근무) 시간을 가져온 뒤 , 결과를 합산하여 주간 (정규 근무, 연장 근무, 특별 근무 ) 시간을 가져옴 
             	4) 해당하는 정보를 result키에 담고 return으로 반환 
             */
            
            // 일간 근무 시간 가져오기 	
            long dailyHours = dailyRegularWorkHours.getOrDefault(date.toString(), 0L);
            // 주간 근무 시간 가져오기 
            weeklyRegularWorkHours.merge(weekKey, dailyHours, Long::sum);             
            
            // 일간 연장 근무 시간 가져오기 
            long dailyExtendHours = dailyExtendWorkHours.getOrDefault(date.toString(), 0L);
            // 주간 연장 근무 시간 가져오기
            // weeklyExtendHours.merge(weekKey, dailyExtendHours, Long::sum); >>  각 주차별로 연장 근무 시간을 누적하여 합산하는 역할
            // weeklyExtendHours는 누적된 주간 연장 근무 시간을 의미
            weeklyExtendHours.merge(weekKey, dailyExtendHours, Long::sum);
            
            
            // 일간 특별별 근무 시간 가져오기 
            long dailySpecialHours= dailySpecialWorkHours.getOrDefault(date.toString(), 0L);
            
            //	주간 특별 근무 시간 가져오기                 
            WeeklySpecialWorkHours.merge(weekKey, dailySpecialHours, Long::sum);
        });
        // 결과 반환 
		Map<String, Object> result = new HashMap<>();
		
        result.put("dailyRegularWorkHours", dailyRegularWorkHours);								// 일간 근무 시간
        result.put("weeklyRegularWorkHours", weeklyRegularWorkHours);							// 주간 근무 시간
        
        result.put("dailyExtendWorkHours", dailyExtendWorkHours);					    		// 일간 연장 근무 시간
        result.put("weeklyExtendHours", weeklyExtendHours);										// 주간 연장 근무 시간
        
        result.put("dailySpecialWorkHours", dailySpecialWorkHours);								// 일간 특별 근무 시간
        result.put("WeeklySpecialWorkHours", WeeklySpecialWorkHours);							// 주간 특별 근무 시간
        
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
