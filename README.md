1. home.jsp에서  jstl 사용할 때 <c:set > 사용하지 않고 <fnt: >사용한 이유 정리

2. 일간 근무 시간, 주간 근무 시간 계산하는 로직 (List<map>의 형식으로 존재하는 이유 설명 )

3. 연장 근무 시간, 특별 근무 시간 작성

4. chat.js 사용 
-------------------------------------------------------
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
import java.util.TreeMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springCommunity.dao.DailyWorkTimeDAO;
import com.springCommunity.vo.DailyWorkTimeVO;
import com.springCommunity.vo.WeeklyWorkTimeVO;

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
 
	
	@Override
	public Map<String, Object> calculateWorkTime(String user_id) {
	    // 1. 해당 유저의 전체 출퇴근 시간을 가져옴
	    List<DailyWorkTimeVO> list = dailyWorkTimeDAO.selectList(user_id);

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    Map<String, Long> dailyWorkHours = new TreeMap<>(Comparator.reverseOrder()); // 내림차순 정렬
	    Map<String, Long> weeklyWorkHours = new TreeMap<>(Comparator.reverseOrder()); // 주간 근무 시간

	    // 1) 날짜별 근무시간 계산
	    list.forEach(workTime -> {
	        String checkInStr = workTime.getCheck_in_time(); // 출근 시간
	        String checkOutStr = workTime.getCheck_out_time(); // 퇴근 시간

	        if (checkInStr == null || checkOutStr == null) {
	            System.out.println("출근 또는 퇴근 시간이 null입니다.");
	            return; // null 데이터는 건너뜀
	        }

	        LocalDateTime checkIn = LocalDateTime.parse(checkInStr, formatter);
	        LocalDateTime checkOut = LocalDateTime.parse(checkOutStr, formatter);
	        LocalDate localDate = checkIn.toLocalDate();
 
	        // 하루 근무 시간 계산
	        long dailyMinutes = Duration.between(checkIn, checkOut).toMinutes();
	        dailyWorkHours.merge(localDate.toString(), dailyMinutes, Long::sum); // TreeMap이므로 자동으로 정렬
	    });

	    // 2) 주차별 근무시간 합산
	    dailyWorkHours.keySet().forEach(dateStr -> {
	        LocalDate date = LocalDate.parse(dateStr);
	        int weekOfYear = date.get(WeekFields.ISO.weekOfYear());
	        String weekKey = date.getYear() + "년 " + weekOfYear + "주차";

	        // 해당 날짜의 근무시간 가져오기
	        long dailyMinutes = dailyWorkHours.getOrDefault(dateStr, 0L);

	        // 주간 데이터에 합산
	        weeklyWorkHours.merge(weekKey, dailyMinutes, Long::sum);
	    });

	    // 3) 결과 출력 (디버깅용)
	    System.out.println("=== 일간 근무시간 (정렬됨) ===");
	    dailyWorkHours.forEach((date, minutes) -> {
	        System.out.printf("Date: %s, Work Time: %d minutes\n", date, minutes);
	    });

	    System.out.println("=== 주간 근무시간 ===");
	    weeklyWorkHours.forEach((week, minutes) -> {
	        System.out.printf("Week: %s, Work Time: %d minutes\n", week, minutes);
	    });

	    // 결과 반환
	    Map<String, Object> result = new HashMap<>();
	    result.put("dailyWorkHours", dailyWorkHours); // 일간 근무 시간 (정렬된 TreeMap)
	    result.put("weeklyWorkHours", weeklyWorkHours); // 주간 근무 시간

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
