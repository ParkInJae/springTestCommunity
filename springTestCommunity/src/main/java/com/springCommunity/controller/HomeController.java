package com.springCommunity.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springCommunity.service.DailyWorkTimeService;
import com.springCommunity.vo.UserVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private DailyWorkTimeService dailyWorkTimeService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 @AuthenticationPrincipa 사용하면 vo 의 정보를 가져올  수 있음 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(@AuthenticationPrincipal UserVO vo,@RequestParam(value = "startDate", required = false) String startDate/* 주차 시작일 (파라미터로 받음)*/
			, Model model) {
		
		// 유저 정보가 null이 아닌 경우 
		if (vo != null) {
				// Service에서 주차별 근무 시간 목록을 가져옴
				logger.info("user_id:{}" ,vo.getUser_id());
				logger.info("user_authority", vo.getAuthorities());
				Map<String, Object> result = dailyWorkTimeService.getWeeklyWorkTimeDetails(vo.getUser_id(), startDate);

				// null일 경우 
		        if (result == null) {
	                result = Map.of(
	                    "workTimeDetails", new Object[0],
	                    "startOfWeek", LocalDate.now(),
	                    "endOfWeek", LocalDate.now().plusDays(6)
	                );
	            }
		        // 	null이 아닌 경우 반환 받는 값을 대입 
		        model.addAttribute("workTimeDetails", result.get("workTimeDetails"));
		        model.addAttribute("startOfWeek", result.get("startOfWeek") != null ? result.get("startOfWeek").toString() : "");
		        model.addAttribute("endOfWeek", result.get("endOfWeek") != null ? result.get("endOfWeek").toString() : "");
	
		        
		        //currentStartDate null 체크 
		        LocalDate currentStartDate = result.get("startOfWeek") != null 
	                    ? (LocalDate) result.get("startOfWeek") : LocalDate.now();

		            model.addAttribute("prevWeekStart", currentStartDate.minusWeeks(1).toString());
		            model.addAttribute("nextWeekStart", currentStartDate.plusWeeks(1).toString());

		    }
	    return "home";
	}
	
	
	
}

