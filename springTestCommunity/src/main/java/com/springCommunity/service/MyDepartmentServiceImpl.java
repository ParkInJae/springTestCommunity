package com.springCommunity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springCommunity.dao.MyDepartmentDAO;
import com.springCommunity.vo.ScheduleVO;
import com.springCommunity.vo.UserInfoVO;

@Service
public class MyDepartmentServiceImpl implements MyDepartmentService{

	
	// 의존성 주입 
	@Autowired
	private MyDepartmentDAO myDepartmentDAO;
	
	
	// 유저 정보 조회 
	public UserInfoVO selectUser(String user_id) {
		return myDepartmentDAO.selectUser(user_id);
	}
	// 전체 조회 
	@Override
	public List<ScheduleVO> selectSchedulesByDepartment( int departmentId) {
		return myDepartmentDAO.selectSchedulesByDepartment(departmentId);
		
	}

	
	
	@Override
	public int insert(ScheduleVO scheduleVO) {
		// 팀장 이상이면 캘린더에 삽입이 가능하게 설정
		
		// 이상이면 DAO로 연결 아니면 return 으로 종료 
//		if()
		
		
		return 0;
	}
	
	
}
