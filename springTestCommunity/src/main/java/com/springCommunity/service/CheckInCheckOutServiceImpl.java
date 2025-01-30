package com.springCommunity.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springCommunity.dao.CheckInCheckOutDAO;
import com.springCommunity.vo.CheckInCheckOutVO;
import com.springCommunity.vo.DailyWorkTimeVO;

@Service
public class CheckInCheckOutServiceImpl implements CheckInCheckOutService{

	@Autowired
	private CheckInCheckOutDAO checkInCheckOutDAO;
	//sqlSession >> 쿼리를 실행하는 객체
	//세션과 해당 쿼리의 값을 가져오기 위해 세션과 정적변수 선언 
	
	// 회사의 위도와 경도는 Service에서 관리
	private final double COMPANY_LATITUDE =  35.8399616515785;// 예: 전주 이젠 위도      35.8402587260868; // 전주 위브 위도 35.8399616515785
	private final double COMPANY_LONGITUDE = 127.155041406919;// 예: 전주 이젠 경도     127.132499131298;  // 전주 위브 경도  127.155041406919
	private final double CHECK_IN_DISTANCE_KM = 5.0; // 반경 1km

	// 지구 반지름 
	private static final double EARTH_RADIUS = 6371.0;
	
	
	   // 출근 결과를 위한 열거형 추가
    public enum CheckInResult {
        SUCCESS,           // 출근 성공
        ALREADY_CHECKED_IN, // 이미 출근함
        OUTSIDE_RANGE      // 범위 밖
    }

    @Override
    public CheckInResult checkIn(CheckInCheckOutVO checkInCheckOutVO, String latitude, String longitude) {
        // 1. 위도, 경도 파싱
        double userLat = Double.parseDouble(latitude);
        double userLon = Double.parseDouble(longitude);

        // 2. 위치 거리 계산
        double distance = calculateDistance(COMPANY_LATITUDE, COMPANY_LONGITUDE, userLat, userLon);
        
        // 3. 위치 범위 벗어남 체크
        if (distance > CHECK_IN_DISTANCE_KM) {
            return CheckInResult.OUTSIDE_RANGE;
        }

        // 4. 당일 출근 기록 확인
        int count = checkInCheckOutDAO.DailyCheckIn(checkInCheckOutVO);
        
        if (count > 0) {
            return CheckInResult.ALREADY_CHECKED_IN;
        }

        // 5. 출근 처리
        checkInCheckOutDAO.checkIn(checkInCheckOutVO);
        return CheckInResult.SUCCESS;
    }
	
	@Override
	public boolean checkOut(CheckInCheckOutVO checkInCheckOutVO, String latitude, String longitude) {
		double userLat = Double.parseDouble(latitude); // 유저의 위도
		double userLon = Double.parseDouble(longitude); // 유저의 경도

		double distance = calculateDistance(COMPANY_LATITUDE, COMPANY_LONGITUDE, userLat, userLon);
		System.out.println("beford if문 distance=======================" + distance);
		
		// 메소드 distance를 통해 얻은 경도가 상수의 반경보다 작을 경우 DAO에게 위도와 경도 값을 빼고 전달
		if (distance <= CHECK_IN_DISTANCE_KM) {
			System.out.println("distance=======================" + distance);
			
			checkInCheckOutDAO.checkOut(checkInCheckOutVO);
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
	public List<CheckInCheckOutVO> selectList (String user_id) {

		return checkInCheckOutDAO.selectList(user_id);
	}

}
