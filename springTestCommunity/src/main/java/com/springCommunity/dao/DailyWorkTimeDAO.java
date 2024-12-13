package com.springCommunity.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.springCommunity.vo.DailyWorkTimeVO;

public class DailyWorkTimeDAO {
	
	@Autowired
	SqlSession sqlsession;
	public final String namespace = "com.springCommunity.mapper.DailyWorkTimeMapper";

	public int checkIn(DailyWorkTimeVO dailyWorkTimeVO ) {
		
		return sqlsession.insert(namespace + "checkIn" ,dailyWorkTimeVO);
	}
	
}
