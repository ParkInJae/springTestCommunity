package com.springCommunity.service;

import java.util.List;

import com.springCommunity.service.CheckInCheckOutServiceImpl.CheckInResult;
import com.springCommunity.vo.CheckInCheckOutVO;
import com.springCommunity.vo.DailyWorkTimeVO;

public interface CheckInCheckOutService {
	/*
//	public boolean checkIn(DailyWorkTimeVO dailyWorkTimeVO, Double.parseDouble(latitude), Double.parseDouble(longitude));에서 오류가 발생하는 이유 '
  	
	  Double.parseDouble(latitude)와 Double.parseDouble(longitude)는 메서드 호출입니다. 그러나
	  인터페이스는 메서드의 선언부만 정의해야 하며, 구체적인 구현(메서드 호출 또는 처리)은 포함할 수 없습니다.
	*/
	/*
	 * 인터페이스는 **메서드의 시그니처(이름, 반환 타입, 매개변수)**만 정의해야 합니다. 매개변수에는 데이터 타입과 변수 이름만 작성해야
	 * 하며, 메서드 구현은 인터페이스에 포함될 수 없습니다.
	 */
	
	
		public CheckInResult checkIn(CheckInCheckOutVO checkInCheckOutVO, String latitude, String longitude);
		
		public boolean checkOut(CheckInCheckOutVO checkInCheckOutVO, String latitude, String longitude);
		
		public List<CheckInCheckOutVO> selectList (String user_id);
	
	
		
}

	
