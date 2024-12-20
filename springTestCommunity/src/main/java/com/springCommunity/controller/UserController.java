package com.springCommunity.controller;


import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springCommunity.vo.UserInfoVO;

@Controller
public class UserController {
	
	@Autowired
	SqlSession sqlSession;
	
	
	@RequestMapping(value="/join.do", method = RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="/joinOk.do", method = RequestMethod.POST)
	public String joinOk(UserInfoVO userInfoVO) {
		System.out.println("user_id:" + userInfoVO.getUser_id());
		BCryptPasswordEncoder epwe = new BCryptPasswordEncoder();
		String encodedPassword = epwe.encode(userInfoVO.getUser_password());
		userInfoVO.setUser_password(encodedPassword);
		System.out.println("암호화된 비밀번호: " + encodedPassword);
		
		int result = sqlSession.insert("com.springCommunity.mapper.userMapper.insert", userInfoVO);
		
		// select절 이용해서 workTime의 값을 가져오고 , 
		// 가져온 workTime을 통해서 근무 시간, 날짜,연장 근무 시간, 특별 근무 시간을 계산해야함
		// 계산한 값을 데이터에 담아서 , 같이 메인으로 넘어가야함 
		
		if(result > 0) {
			System.out.println("회원가입성공");
		}else {
			System.out.println("회원가입실패");
		}
		
		return "redirect:/";
	}
}
