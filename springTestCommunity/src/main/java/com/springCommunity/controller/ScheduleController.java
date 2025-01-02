package com.springCommunity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springCommunity.service.MyDepartmentService;
import com.springCommunity.service.ScheduleService;
import com.springCommunity.vo.ScheduleVO;
import com.springCommunity.vo.SearchVO;

@RestController
public class ScheduleController {

	@Autowired
	MyDepartmentService myDepartmentService;
	
	@Autowired
	ScheduleService scheduleService;

    // 전체 일정 조회 (GET)
    // ModelAttribute 바인딩하여 URL에서 파라미터로 전달되는 값들을 SearchVO 객체로 묶어서 처리할 수 있습니다.
	@GetMapping("/api/schedule.do")
	public ResponseEntity<Map<String, Object>> getSchedule(@ModelAttribute SearchVO searchVO) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        List<ScheduleVO> schedules = scheduleService.selectAllSchedule();
	        response.put("status", "success");
	        response.put("data", schedules);
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        response.put("status", "fail");
	        response.put("message", e.getMessage());
	        return ResponseEntity.badRequest().body(response);
	    }
	}
    // 특정 부서의 일정만 조회하는 것 
    @GetMapping("/api/schedule/department.do")
    public ResponseEntity<Map<String, Object>> selectSchedulesByDepartment(ScheduleVO scheduleVO) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ScheduleVO> schedules = scheduleService.selectSchedulesByDepartment(scheduleVO);
            response.put("status", "success");
            response.put("data", schedules);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "fail");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    //일정 삽입
    @PostMapping("/api/schedule.do")
    public ResponseEntity<Map<String, Object>> insertSchedule(@RequestBody ScheduleVO scheduleVO) {
        Map<String, Object> response = new HashMap<>();
        try {
            int result = scheduleService.insertSchedule(scheduleVO);  // 반환 타입을 int로 변경
            response.put("status", "success");
            response.put("data", result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "fail");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    // 일정 수정
    @PutMapping("/api/scheduleUpdate.do")
    public ResponseEntity<Map<String, Object>> updateSchedule(@RequestBody ScheduleVO scheduleVO) {
        Map<String, Object> response = new HashMap<>();
        try {
            int result = scheduleService.updateSchedule(scheduleVO);  // 반환 타입을 int로 변경
            response.put("status", "success");
            response.put("data", result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "fail");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // 일정 삭제 
    @DeleteMapping("/api/scheduleDelete.do")
    public ResponseEntity<Map<String, Object>> deleteSchedule(@RequestBody ScheduleVO scheduleVO) {
        Map<String, Object> response = new HashMap<>();
        try {
            scheduleService.deleteSchedule(scheduleVO);  // ScheduleVO 객체 전체를 전달
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "fail");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
