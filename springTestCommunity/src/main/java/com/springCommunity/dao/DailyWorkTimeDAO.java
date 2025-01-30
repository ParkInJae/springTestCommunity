package com.springCommunity.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springCommunity.vo.DailyWorkTimeVO;

@Repository
public class DailyWorkTimeDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	// 상수 선언 
	public final String namespace = "com.springCommunity.mapper.DailyWorkTimeMapper.";

	
	
	// selectList >> 해당 쿼리에 해당 하는 전체 행을 가져옴
	// selectOne >> 해당 쿼리에 해당하는 한 행을 가져옴 (상세 조회할 때 사용 )
	// 아이디가 일치하는 회원의 전체 출근 및 퇴근 시간을 가져오기
	
	public List<DailyWorkTimeVO> selectList(String user_id) {  	// 아이디가 일치하는 회원의 전체 출근 및 퇴근 시간을 가져오기
		return sqlSession.selectList(namespace + "selectList" ,user_id);
	}
	
	
	
	public List<DailyWorkTimeVO> selectDetailedListByWeek(String user_id, String startDate, String endDate){
		
		Map<String, Object> details = new HashMap<>();
		details.put("user_id", user_id);
		details.put("startDate", startDate);
		details.put("endDate", endDate);
		
		return sqlSession.selectList(namespace + "selectDetailedListByWeek", details );

	}
	
	
	
	
}
