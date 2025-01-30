package com.springCommunity.service;

import java.util.List;

import com.springCommunity.vo.ScheduleVO;
import com.springCommunity.vo.TaskVO;
import com.springCommunity.vo.UserInfoVO;

public interface MyDepartmentService {
	// 접속한 유저 정보의 조회 
	public UserInfoVO selectUser(String user_id);
	
	// 부서 업무 조회 
	public List<ScheduleVO> selectSchedulesByDepartment(int department_id);
	
	// 부서 업무 삽입
	public int insert(ScheduleVO scheduleVO);
	
	// 아이디와 부서 확인 
	boolean validateUserDepartment(String user_id, int department_id);
	
	// 권한에 대한 직책 확인 
    boolean hasSchedulePermission(String user_id);
	
}
