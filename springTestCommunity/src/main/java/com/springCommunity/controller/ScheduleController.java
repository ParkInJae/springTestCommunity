package com.springCommunity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springCommunity.service.MyDepartmentService;
import com.springCommunity.service.ScheduleService;
import com.springCommunity.vo.ScheduleVO;
import com.springCommunity.vo.SearchVO;

@Controller
public class ScheduleController {

	@Autowired
	MyDepartmentService myDepartmentService;
	
	@Autowired
	ScheduleService scheduleService;

    // 일정 조회 (GET)
    @GetMapping
    @RequestMapping(value = "/api/schedule" , method=RequestMethod.GET)
    // ModelAttribute 바인딩하여 URL에서 파라미터로 전달되는 값들을 SearchVO 객체로 묶어서 처리할 수 있습니다.
    public List<ScheduleVO> getSchedule(@ModelAttribute SearchVO searchVO ) {
    	
    	//바인딩 되었는지 디버깅
    	System.out.println("searchVO.getUser_id()================================" + searchVO.getUser_id());
    	scheduleService.selectAllSchedule();
    	
    return scheduleService.selectAllSchedule();
    }
	
    //일정 삽입
    
    
    // 일정 수정
    
    
    // 일정 삭제 
    

}
