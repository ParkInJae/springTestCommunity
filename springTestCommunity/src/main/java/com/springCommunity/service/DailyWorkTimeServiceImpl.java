package com.springCommunity.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.springCommunity.dao.DailyWorkTimeDAO;
import com.springCommunity.vo.DailyWorkTimeVO;

public class DailyWorkTimeServiceImpl implements DailyWorkTimeService{

	@Autowired
    DailyWorkTimeDAO dailyWorkTimeDAO;
	
    // 회사의 위도와 경도는 Service에서 관리
    private final double COMPANY_LATITUDE = 37.5665; // 예: 서울 위도
    private final double COMPANY_LONGITUDE = 126.9780; // 예: 서울 경도
    private final double CHECK_IN_DISTANCE_KM = 1.0; // 반경 1km
	

	@Override
	public boolean checkIn(DailyWorkTimeVO dailyWorkTimeVO, String latitude, String longitude) {
		double userLat = Double.parseDouble(latitude); 				//유저의 위도 
		double userLon = Double.parseDouble(longitude);				//유저의 경도 
		
		 double distance = calculateDistance(COMPANY_LATITUDE, COMPANY_LONGITUDE, userLat, userLon );

		 // 메소드 distance를 통해 얻은 경도가 상수의 반경보다 작을 경우 DAO에게 위도와 경도 값을 빼고 전달 
	        if (distance <= CHECK_IN_DISTANCE_KM) {
	            dailyWorkTimeDAO.checkIn(dailyWorkTimeVO); // 거리 범위 내면 데이터 저장
	            return true;
	        }
	        return false; // 거리 범위 밖이면 저장하지 않음
	    }

		// 구글 거리계산 api
	    private double calculateDistance(double companyLat, double companyLng, double userLat, double userLng) {
	        String apiKey = "YOUR_GOOGLE_API_KEY";
	        String apiUrl = String.format("https://maps.googleapis.com/maps/api/distancematrix/json?origins=%f,%f&destinations=%f,%f&key=%s",
	                                        companyLat, companyLng, userLat, userLng, apiKey);

	        RestTemplate restTemplate = new RestTemplate();
	        try {
	            Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
	            Map<String, Object> row = ((List<Map<String, Object>>) response.get("rows")).get(0);
	            Map<String, Object> element = ((List<Map<String, Object>>) row.get("elements")).get(0);

	            if ("OK".equals(element.get("status"))) {
	                Map<String, Object> distanceObj = (Map<String, Object>) element.get("distance");
	                double distanceInMeters = (double) distanceObj.get("value");
	                return distanceInMeters / 1000.0; // m to km
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return Double.MAX_VALUE; // API 호출 실패 시 무한대로 반환
	    }
	}