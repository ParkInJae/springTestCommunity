package com.springCommunity.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springCommunity.dao.DailyWorkTimeDAO;
import com.springCommunity.vo.DailyWorkTimeVO;

@Service
public class DailyWorkTimeServiceImpl implements DailyWorkTimeService {

	@Autowired
	DailyWorkTimeDAO dailyWorkTimeDAO;
	//sqlSession >> 쿼리를 실행하는 객체
	//세션과 해당 쿼리의 값을 가져오기 위해 세션과 정적변수 선언 
	@Autowired
	SqlSession sqlSession;
	public final String namespace = "com.springCommunity.mapper.DailyWorkTimeMapper.";
	// 회사의 위도와 경도는 Service에서 관리
	private final double COMPANY_LATITUDE = 35.8402587260868; // 예: 전주 이젠 위도
	private final double COMPANY_LONGITUDE = 127.132499131298; // 예: 전주 이젠 경도
	private final double CHECK_IN_DISTANCE_KM = 2.0; // 반경 1km

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
				System.out.println("이미 존재하는 출근 기록입니다.");
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
    /*
	  // 구글 거리계산 api 
	   * private double calculateDistance(double companyLat, double companyLng, double userLat, double userLng) {
	   String apiKey = "YOUR_GOOGLE_API_KEY"; 
	   String apiUrl = String.format("https://maps.googleapis.com/maps/api/distancematrix/json?origins=%f,%f&destinations=%f,%f&key=%s",companyLat, companyLng, userLat, userLng, apiKey);
	  
	  RestTemplate restTemplate = new RestTemplate(); 
	  try {
	   Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
	   System.out.println("response=============" + response);
	    
	   Map<String, Object>row = ((List<Map<String, Object>>) response.get("rows")).get(0);
	   System.out.println("row=============" + row); 
	   
	   Map<String, Object> element =((List<Map<String, Object>>) row.get("elements")).get(0);
	   System.out.println("element =============" + element );
	   
	  	   if ("OK".equals(element.get("status"))) { 
	  	   	Map<String, Object> distanceObj =(Map<String, Object>) element.get("distance"); 
	  	   	double distanceInMeters =(double) distanceObj.get("value"); 
	  	   	
	  	  // Google API 문서에 따르면 distance.value는정수(Integer)로만 반환 
	  	  // 애초에 정확한 거리 데이터를 정수로 계산하기 때문에 따라서 정수로 반환되더라도 오류 발생 x 따라서 타입을 Double이 아닌 Integer로 설정해도 데이터 손실은 없다
	  	    
		   Integer distanceInMeters =(Integer) distanceObj.get("value"); 
		   return distanceInMeters / 1000.0; // m to km 정수 / 실수 >> 실수로 결과값 나옴 
	   		} 
	   } catch (Exception e) { e.printStackTrace(); }	   
	  	return Double.MAX_VALUE; // API 호출 실패 시 무한대로 반환 
	  }
	 */

	@Override
	public void getWorkTime(String userId) {
		dailyWorkTimeDAO.getWorkTime(userId);
	}
	
}