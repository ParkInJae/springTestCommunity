package com.springCommunity.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springCommunity.vo.PostVO;
import com.springCommunity.vo.SearchVO;

@Repository
public class PostDAO {
	
	@Autowired
	public SqlSession sqlSession;
	
	private final String namespace = "com.springCommunity.mapper.postMapper.";
	
	public List<PostVO> list(SearchVO searchVO){
		return sqlSession.selectList(namespace+"selectAll",searchVO);
	}
	
	public int total(SearchVO searchVO) {
		return sqlSession.selectOne(namespace+"selectTotal",searchVO);
	}
	
	public int insert(PostVO vo) {
		return sqlSession.insert(namespace+"insert", vo);
	}
	
	public PostVO selectOne(int post_no) {
		return sqlSession.selectOne(namespace+"selectOne", post_no);
	}
	
	public int changeState(int post_no) {
		return sqlSession.update(namespace+"changeState",post_no);
	}
	
	public int update(PostVO vo) {
		return sqlSession.update(namespace+"update", vo);
	}
}
