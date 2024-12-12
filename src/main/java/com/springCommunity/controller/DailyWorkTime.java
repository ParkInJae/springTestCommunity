package com.springCommunity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springCommunity.service.WorkTimeService;
import com.springCommunity.vo.DailyWorkTimeVO;
@RequestMapping(value="/user")
@Controller
public class DailyWorkTime {
	
	@Autowired
	WorkTimeService workTimeService;
	
//1. 유효 위치 확인 
	
	
// 2.출석 확인
	@RequestMapping(value="/checkIn.do" ,method = RequestMethod.POST)
	public ResponseEntity<DailyWorkTimeVO> checkIn(@RequestParam double latitude,@RequestParam double longitude){
		boolean isValid = workTimeService.
		
		return"";
	} 

	
	
//	3. 퇴근 확인
	
}
