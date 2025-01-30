package com.springCommunity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springCommunity.service.MyDepartmentService;
import com.springCommunity.vo.ScheduleVO;
import com.springCommunity.vo.TaskVO;
import com.springCommunity.vo.UserInfoVO;
 
@Controller  // component로 작성하면 서버가 찾지 못함 

@RequestMapping("/user")
public class MyDepartmentController {
	@Autowired
	MyDepartmentService myDepartmentService;
	
	// 페이지를 보여주는 역활  즉 다른 페이지에서 부서 페이지로 이동할 때 전체 데이터를 보여줘야함 
	@RequestMapping(value="/myDepartment.do")
	public String list(Model model, UserInfoVO userinfo,Authentication authentication,TaskVO taskVO){
		// 스프링 시큐리티를 이용해  유저인지 아닌지 권한을 체크 
		String auth = authentication.getAuthorities().toString(); // null값
		
		// 로그인한 사용자 정보 
		String user_id = authentication.getName().toString();	// 로그인 사람의 id를 갖고 있음
		UserInfoVO vo  =  myDepartmentService.selectUser(user_id);
		
		System.out.println("반환받은 vo의 부서 확인하기================" + vo.getDepartment_id());
		System.out.println("반환받은 vo의 아이디 확인하기================" + vo.getUser_id());

		/* 부서 id를 가져와서 
		 각 부서의 업무만 볼 수 있도록 제한할 수 있습니다.
		특정 부서에 속한 팀장만 특정 권한을 가지도록 처리할 수 있습니다.
		따라서, 이 코드는 부서별로 일정 데이터를 분리하고 조회하는 로직을 추가하기 위한 것입니다.*/
		int department_id = vo.getDepartment_id();
		
		List<ScheduleVO> ScheduleList =  myDepartmentService.selectSchedulesByDepartment(department_id);
		System.out.println("가져온 정보 중 첫번째 일정의 name : "+ ScheduleList.get(0).getSchedule_name());
		
		
		
		// 담아온 부서 정보를 list에 담기 
	    model.addAttribute("ScheduleList", ScheduleList);
	    model.addAttribute("vo", vo);
	    
		
		System.out.println("현재 인증된 사용자 정보 가져오기 =================\n" + auth);
		
		if(auth.equals("[ROLE_USER]" )|| auth.equals("[ROLE_ADMIN]")){
			System.out.println("일반유저이거나 관리자 입니다.");
			return "department/myDepartmentUser";
		}else {
			return "home";
		 	}
    	}
}