package com.springCommunity.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springCommunity.vo.CommentVO;
import com.springCommunity.vo.SearchVO;



@Repository
public class CommentDAO {
	
	@Autowired
	public SqlSession sqlSession;
	
	private final String namespace = "com.springCommunity.mapper.commentMapper.";
	
	public List<CommentVO> list(SearchVO searchVO){
		return sqlSession.selectList(namespace+"selectAll",searchVO);
	}
	
	public int total(SearchVO searchVO) {
		return sqlSession.selectOne(namespace+"selectTotal",searchVO);
	}
	
	public int insert(CommentVO vo) {
		return sqlSession.insert(namespace+"insert", vo);
	}
	
	public CommentVO selectOne(int comment_no) {
		return sqlSession.selectOne(namespace+"selectOne", comment_no);
	}
	
	public int changeState(int comment_no) {
		return sqlSession.update(namespace+"changeState",comment_no);
	}
	
	public int update(CommentVO vo) {
		return sqlSession.update(namespace+"update", vo);
	}
}
