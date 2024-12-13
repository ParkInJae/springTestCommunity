package com.springCommunity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springCommunity.service.DailyWorkTimeService;
import com.springCommunity.vo.DailyWorkTimeVO;

@Controller
public class WorkTimeController {

    @Autowired
    DailyWorkTimeService dailyWorkTimeService;

    @RequestMapping(value = "/user/checkIn.do", method = RequestMethod.POST)
    public ResponseEntity<String> checkIn(@RequestBody DailyWorkTimeVO dailyWorkTimeVO,@RequestParam String latitude,@RequestParam String longitude) {
    	
    	 
            // 비즈니스 로직을 Service로 위임
    		// service인터페이스에서는 메소드의 호출 및 처리는 실행할 수 없으므로 , String 타입으로 전송 후 비즈니스 로직을 처리하는 serviceImpl에서 형변환 시켜서 사용하면 됨  
            boolean isWithinRange = dailyWorkTimeService.checkIn(dailyWorkTimeVO,latitude, longitude);

            if (isWithinRange) {
                return ResponseEntity.ok("Check-in successful.");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are too far from the company location.");
            }
        
    }
}
