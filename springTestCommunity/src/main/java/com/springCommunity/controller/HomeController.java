package com.springCommunity.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springCommunity.service.DailyWorkTimeService;
import com.springCommunity.vo.DailyWorkTimeVO;
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
	public String home(Locale locale, Model model,@AuthenticationPrincipal UserVO vo ) {
		// 만약 로그인 한 사용자가 존재한다면, 
		if(vo != null) {
			// session을 통해서 mapper에서 시간계산 후 값을 가져옴 
			logger.info("vo : {} ", vo.getUser_id());
			
			// 1. 해당 사원의 전체 출퇴근 시간을 가져오기
	        List<DailyWorkTimeVO> workTimes = dailyWorkTimeService.selectList(vo.getUser_id());
	        
	         // 비즈니스 로직을 구현하는 service로 의존성 주입 
			 Map<String, Object> WorkTimeData = dailyWorkTimeService.calculateWorkTime(vo.getUser_id());
// 디버깅
			 logger.info("dailyWorkHours : {}", WorkTimeData.get("dailyWorkHours"));
			 logger.info("weeklyWorkHours : {}", WorkTimeData.get("weeklyWorkHours"));
			 
			 
			 // service의 리턴값을 받아서 모델에 데이터 추가
			 model.addAttribute("dailyWorkHours",WorkTimeData.get("dailyWorkHours"));  	// 일간 정규 근무 시간
			 model.addAttribute("weeklyWorkHours",WorkTimeData.get("weeklyWorkHours"));	// 주간 정규 근무 시간
		
		}		
		return "home";
	}
	
}
