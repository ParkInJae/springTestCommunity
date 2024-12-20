package com.springCommunity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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

    @RequestMapping(value = "user/checkIn.do", method = RequestMethod.POST)
    public ResponseEntity<String> checkIn(@RequestBody DailyWorkTimeVO dailyWorkTimeVO) {
    	 // VO에서 위도와 경도 가져오기
        String latitude = dailyWorkTimeVO.getLatitude();
        String longitude = dailyWorkTimeVO.getLongitude();
    	 
            // 비즈니스 로직을 Service로 위임
    		// service인터페이스에서는 메소드의 호출 및 처리는 실행할 수 없으므로 , String 타입으로 전송 후 비즈니스 로직을 처리하는 serviceImpl에서 형변환 시켜서 사용하면 됨  
            boolean isWithinRange = dailyWorkTimeService.checkIn(dailyWorkTimeVO,latitude, longitude);

            if (isWithinRange) {
                return ResponseEntity.ok("출근 성공.");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("회사에서 멀리 있음.");
            }
        
    }
    
	// ResponseEntity<String>과 그에 해당 하는 메소드 및 출퇴근 로직 ReadMe에 작성 
    // ajax에서 보내는 내부 데이터는 vo의 setter를 통해서 자동으로 값이 셋팅 되기 때문에, dailyWorkTimeVO만 매개변수로 받아도 된다.
    /*  @RequestBody를 사용하면, AJAX에서 보낸 JSON 데이터가 VO 객체로 자동 매핑됩니다.
    	매핑 과정에서 JSON의 키 이름이 VO의 필드 이름과 동일하다면, Spring이 VO의 setter 메서드를 사용해 데이터를 채웁니다.
    */
    @PostMapping
	  @RequestMapping(value="user/checkOut.do", method = RequestMethod.POST)
    public ResponseEntity<String> checkOut(@RequestBody DailyWorkTimeVO dailyWorkTimeVO) {
		  // VO에서 위도와 경도 가져오기 
		  String latitude = dailyWorkTimeVO.getLatitude();
		  String longitude = dailyWorkTimeVO.getLongitude();
		  
		  // @s
		  boolean isWithinRange = dailyWorkTimeService.checkOut(dailyWorkTimeVO,latitude, longitude);
		  if(isWithinRange) {
			  return ResponseEntity.ok("수고하셨습니다. 퇴근하세요");
		  }else {
			  return ResponseEntity.status(HttpStatus.FORBIDDEN).body("시스템 오류로 퇴근 처리가 되지 않습니다.");
		  }
	  }
	     
    
    
    
}




