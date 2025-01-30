package com.springCommunity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springCommunity.service.CheckInCheckOutService;
import com.springCommunity.service.CheckInCheckOutServiceImpl.CheckInResult;
import com.springCommunity.vo.CheckInCheckOutVO;
import com.springCommunity.vo.DailyWorkTimeVO;

@Controller
public class CheckInCheckOutController {

    @Autowired
    CheckInCheckOutService checkInCheckOutService;

    

    @RequestMapping(value = "user/checkIn.do", method = RequestMethod.POST)
    public ResponseEntity<String> checkIn(@RequestBody CheckInCheckOutVO checkInCheckOutVO) {
        String latitude = checkInCheckOutVO.getLatitude();
        String longitude = checkInCheckOutVO.getLongitude();
        
        CheckInResult result = checkInCheckOutService.checkIn(checkInCheckOutVO, latitude, longitude); 
        
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("출근 성공.");
            case ALREADY_CHECKED_IN:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("이미 오늘 출근했습니다.");
            case OUTSIDE_RANGE:
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("회사 위치에서 벗어났습니다.");
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("시스템 오류가 발생했습니다.");
        }
    }
    
        @RequestMapping(value="user/checkOut.do", method = RequestMethod.POST)
        public ResponseEntity<String> checkOut(@RequestBody CheckInCheckOutVO checkInCheckOutVO) {
            String latitude = checkInCheckOutVO.getLatitude();
            String longitude = checkInCheckOutVO.getLongitude();
            
            boolean isWithinRange = checkInCheckOutService.checkOut(checkInCheckOutVO, latitude, longitude);
            if (isWithinRange) {
                return ResponseEntity.ok("수고하셨습니다. 퇴근하세요");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("시스템 오류로 퇴근 처리가 되지 않습니다.");
            }
        }
    
        
        
        
}




