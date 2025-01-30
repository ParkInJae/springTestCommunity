package com.springCommunity.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springCommunity.vo.CheckInCheckOutVO;

@Repository
public class CheckInCheckOutDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	// 상수 선언 
	public final String namespace = "com.springCommunity.mapper.CheckInCheckOutMapper.";
	

	// 출근 시간 DB 저장
	public int checkIn(CheckInCheckOutVO checkInCheckOutVO ) {
		
		return sqlSession.insert(namespace + "CheckIn" ,checkInCheckOutVO);
	}
	
	
	// 출근 시간 DB에 존재하는지 확인
		public int DailyCheckIn(CheckInCheckOutVO checkInCheckOutVO ) {
			
			return sqlSession.selectOne(namespace+"DailyCheckIn", checkInCheckOutVO);
		}
	
	
	// 퇴근 시간 DB 저장
	public int checkOut(CheckInCheckOutVO checkInCheckOutVO ) {
		return sqlSession.update(namespace+ "checkOut",checkInCheckOutVO); 
	}
	
	
	// selectList >> 해당 쿼리에 해당 하는 전체 행을 가져옴
	// selectOne >> 해당 쿼리에 해당하는 한 행을 가져옴 (상세 조회할 때 사용 )
	// 아이디가 일치하는 회원의 전체 출근 및 퇴근 시간을 가져오기
	
	public List<CheckInCheckOutVO> selectList(String user_id) {  	// 아이디가 일치하는 회원의 전체 출근 및 퇴근 시간을 가져오기
		return sqlSession.selectList(namespace + "selectList" ,user_id);
	}
	
}
