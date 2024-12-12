package com.springCommunity.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.springCommunity.vo.DailyWorkTimeVO;

public class WorkTimeDAO {

	@Autowired
	SqlSession sqlSession;
	
	// namespace 지정 
	private final String namespace= "com.springCommunity.mapper.WorkTimeMapper";

	// 출퇴근 관련 데이터 집어넣기 
	public int insertOne(DailyWorkTimeVO dailyWorkTimeVO) {
		// mapper에 전송 한 값을 받기 
		return 	sqlSession.insert(namespace+"insertCheckIn",dailyWorkTimeVO);
	}
	
	
	
}
