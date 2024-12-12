package com.springTestCommunity.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springTestCommunity.vo.MypageVO;

@Repository
public class MypageDAO {
	
	@Autowired
	SqlSession sqlSession;

	private final String namespace = "com.springCommunity.mapper.mypageMapper.";
	
	public int userUpdate (MypageVO mypageVO) {
		return sqlSession.update(namespace+"userUpdate",mypageVO);
	}
	
	public int userPwUpdate (MypageVO mypageVO) {
		return sqlSession.update(namespace+"userPwUpdate",mypageVO);
	}
}
