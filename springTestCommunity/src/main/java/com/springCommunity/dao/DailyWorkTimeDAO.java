package com.springCommunity.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springCommunity.vo.DailyWorkTimeVO;
import com.springCommunity.vo.UserVO;

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
	
	// selectList >> 해당 쿼리에 해당 하는 전체 행을 가져옴
	// selectOne >> 해당 쿼리에 해당하는 한 행을 가져옴 (상세 조회할 때 사용 )
	public List<DailyWorkTimeVO> selectList(String user_id) {
		
		
		return sqlSession.selectList(namespace + "selectList" ,user_id);
	}
	
	
	public void getWorkTime(String userId) {
		
	}
	
	
}
