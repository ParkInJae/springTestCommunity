package com.springCommunity.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springCommunity.vo.DailyWorkTimeVO;

@Repository
public class DailyWorkTimeDAO {
	
	@Autowired
	SqlSession sqlSession;
	public final String namespace = "com.springCommunity.mapper.DailyWorkTimeMapper.";

	public int checkIn(DailyWorkTimeVO dailyWorkTimeVO ) {
		
		return sqlSession.insert(namespace + "CheckIn" ,dailyWorkTimeVO);
	}
	public int checkOut(DailyWorkTimeVO dailyWorkTimeVO ) {
		return sqlSession.update(namespace+ "checkOut",dailyWorkTimeVO); 
	}
	
	public void getWorkTime(String userId) {
		
	}
	
	
}
