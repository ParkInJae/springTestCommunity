package com.springTestCommunity.controller;

import com.springTestCommunity.service.ExcelUserService;
import com.springTestCommunity.service.UserService;
import com.springTestCommunity.vo.UserInfoVO;

import java.io.InputStream;
import java.security.SecureRandom;
import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
	UserService userService;
    
    @Autowired
    ExcelUserService excelUserService;

    @RequestMapping(value="/upload_users.do", method = RequestMethod.GET)
    public String uploadUsers() {
        return "admin/upload_users";
    }
    
    @PostMapping("/upload_users.do")
    public String uploadUsers(@RequestParam("file") MultipartFile file,Model model) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어 있습니다.");
        }
        System.out.println("test0");
        try (InputStream inputStream = file.getInputStream()) {
            List<UserInfoVO> users = excelUserService.readUsersFromExcel(inputStream);

            // 비밀번호 암호화
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            for(UserInfoVO user : users) {
            	System.out.println("admincontroller userid:" + user.getUser_id());
                user.setUser_password(encryptPassword(user.getUser_password()));
            }

            // MyBatis 매퍼 호출
            int result = userService.insertUsers(users);
            if(result > 0) {
            	model.addAttribute("Message", "사용자 정보가 성공적으로 업로드되었습니다.");
            }else {
            	model.addAttribute("Message", "파일 처리 중 오류가 발생했습니다.");
            }
            return "admin/upload_users";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("Message", "파일 처리 중 오류가 발생했습니다.");
            return "admin/upload_users";
        }
    }
    
    @RequestMapping(value="/insert_user.do", method = RequestMethod.POST)
	public String insertUser(UserInfoVO userInfoVO) {
		BCryptPasswordEncoder epwe = new BCryptPasswordEncoder();
		
		String random_PASSWORD = generateRandom(6);
		userInfoVO.setUser_password(random_PASSWORD); // 비밀번호는 암호화 필요
		
		String encodedPassword = epwe.encode(userInfoVO.getUser_password());
		userInfoVO.setUser_password(encodedPassword);
		System.out.println("암호화된 비밀번호: " + encodedPassword);
		
		userInfoVO.setUser_id("jj"+userInfoVO.getUser_id());
		
		int result = userService.insertUser(userInfoVO);
		
		if(result > 0) {
			System.out.println("회원등록성공");
		}else {
			System.out.println("회원등록실패");
		}
		
		return "admin/upload_users";
	}
    
    //랜덤 비밀번호 6글자 생성
  	public String generateRandom(int length) {
  		String datas = "abcdefghijklmnopqrstuvwxyz0123456789";
  		SecureRandom secureRandom = new SecureRandom();
  		StringBuilder code = new StringBuilder();
  		
  		for (int i = 0; i < length; i++) {
  			int rand = secureRandom.nextInt(datas.length());
  			code.append(datas.charAt(rand));
  		}
  		
  		return code.toString();
  	}
    
    // 비밀번호 암호화 메소드
    private String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    
    @RequestMapping(value="/list.do", method = RequestMethod.GET)
    public String list() {
        return "admin/list";
    }
}
