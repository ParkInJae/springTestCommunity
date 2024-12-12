package com.springCommunity.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springCommunity.service.MypageService;
import com.springCommunity.vo.MypageVO;

@Controller
public class MypageController {
	
	@Autowired
	public MypageService mypageService;

	@RequestMapping(value="/mypage/info.do", method = RequestMethod.GET)
	public String info() {
		return "mypage/info";
	}
	
	@RequestMapping(value="/mypage/info.do", method = RequestMethod.POST)
	public String info(MypageVO mypageVO,Principal principal) {
		
		String userId = principal.getName();
		System.out.println("userid=>"+userId);
		mypageVO.setUser_id(userId);
		
		int rs = mypageService.userUpdate(mypageVO);
			
		if(rs>0) {
			System.out.println("회원정보 수정 성공.");
			return "redirect:/";
			
		}else {
			System.out.println("회원정보 수정 실패.");
			return "redirect:info.do";
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/mypage/info/pwChangeModal.do", method = RequestMethod.GET)
	public String pwChangeModal(String pwChangeTable) {
		return "success";
	}	
	
	@RequestMapping(value="/mypage/medical.do", method = RequestMethod.GET)
	public String medical() {
		return "mypage/medical";
	}
	@RequestMapping(value="/mypage/benefit.do", method = RequestMethod.GET)
	public String benefit() {
		return "mypage/benefit";
	}
	@RequestMapping(value="/mypage/form.do", method = RequestMethod.GET)
	public String form() {
		return "mypage/form";
	}
}