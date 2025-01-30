package com.springCommunity.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springCommunity.vo.ScheduleVO;
import com.springCommunity.vo.TaskVO;
import com.springCommunity.vo.UserInfoVO;


@Repository
public class MyDepartmentDAO {

	@Autowired
	private SqlSession sqlSession;
	public final String namespace = "com.springCommunity.mapper.MyDepartmentMapper.";
	
	// 유저 정보 조회 
	public UserInfoVO selectUser(String user_id) {
		return sqlSession.selectOne(namespace + "selectUser" , user_id);
	}
	
	// 부서 업무 조회 

	
	
	// 글 작성 
	public int insert(ScheduleVO scheduleVO) {
		
		return sqlSession.insert(namespace + "insert" ,scheduleVO);
	}
	
	
	public List<ScheduleVO> selectSchedulesByDepartment(int department_id) {
		
		return sqlSession.selectList(namespace + "selectSchedulesByDepartment",department_id);
	}
	
	
}
