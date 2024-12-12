package com.springTestCommunity.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springTestCommunity.vo.NoticeVO;
import com.springTestCommunity.vo.SearchVO;

@Repository
public class NoticeDAO {
	
	@Autowired
	public SqlSession sqlSession;
	
	private final String namespace = "com.springCommunity.mapper.noticeMapper";
	
	public List<NoticeVO> list(SearchVO searchVO){
		return sqlSession.selectList(namespace+".selectAll",searchVO);
	}
	
	public int total(SearchVO searchVO) {
		return sqlSession.selectOne(namespace+".selectTotal",searchVO);
	}
	
	public int insert(NoticeVO vo) {
		return sqlSession.insert(namespace+".insert", vo);
	}
	
	public NoticeVO selectOne(int post_no) {
		return sqlSession.selectOne(namespace+".selectOne", post_no);
	}
	
	public int changeState(int post_no) {
		return sqlSession.update(namespace+".changeState",post_no);
	}
	
	public int selectCntFreeByUid(String user_id) {
		return sqlSession.selectOne(namespace+".selectCntFreeByUid", user_id);
	}
}
