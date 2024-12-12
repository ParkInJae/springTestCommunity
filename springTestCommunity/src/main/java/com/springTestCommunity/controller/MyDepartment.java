package com.springTestCommunity.controller;

import java.util.Collection;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springTestCommunity.vo.UserInfoVO;
 
@Controller  // component로 작성하면 서버가 찾지 못함 
public class MyDepartment {
	
	@Autowired
	SqlSession sqlSession;

	@RequestMapping(value="/user/myDepartment.do")
	public String list(Model model, UserInfoVO userinfo,Authentication authentication){
		// 스프링 시큐리티를 이용해 권한을 체크 
		String auth = authentication.getAuthorities().toString();
		System.out.println("현재 인증된 사용자 정보 가져오기 =================\n" + auth);

		// 홈에서는  주간 근무 시간 , 연장 근무 시간, 특별 근무 시간, 총 근무 시간을 나타내야함
		// 주간 근무 시간 > 40 시간 default
		//연장근무는 특근, 야간근무, 주말휴일근무,시간외 근무  등 모든근무
		
		// 1. 일반 유저일 경우
		// 		일반 유저에게 보여줄 정보를 모델에 저장
		// 		일반 유저용 뷰 페이지 연결
		if(auth.equals("[ROLE_USER]")){
			System.out.println("일반유저입니다");
			return "department/myDepartmentUser";
		} /*
			  else if(auth.equals("[ROLE_ADMIN]")) { 
			  // 2. 관리자 유저인 경우 
			  // 관리자 유저에게 보여줄 정보를  모델에 저장 
			  // 관리자 유저용 뷰 페이지 연결 System.out.println("관리자입니다"); 
			   return "department/myDepartmentAdmin"; 
			   }
		 */else {
			return "home"; // 접근 권한이 없음 -> 
		}

		
		
		
		



    	}
	
}
