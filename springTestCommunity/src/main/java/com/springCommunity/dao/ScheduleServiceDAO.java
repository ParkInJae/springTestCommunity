package com.springCommunity.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springCommunity.vo.ScheduleVO;

@Repository
public class ScheduleServiceDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public final String namespace = "com.springCommunity.mapper.MyDepartmentMapper.";
	
	public List<ScheduleVO> selectAllSchedule(){
		
		return sqlSession.selectList(namespace+ "selectAllSchedule "); 
	}

}
