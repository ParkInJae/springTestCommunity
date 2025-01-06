package com.springCommunity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springCommunity.dao.PostDAO;
import com.springCommunity.vo.PostVO;
import com.springCommunity.vo.SearchVO;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	public PostDAO postDAO;
	
	@Override
	public List<PostVO> list(SearchVO searchVO) {
		return postDAO.list(searchVO);
	}
	
	@Override
	public int selectTotal(SearchVO searchVO) {
		return postDAO.total(searchVO);
	}
	
	@Override
	public int insert(PostVO vo) {
		return postDAO.insert(vo);
	}
	
	@Override
	public PostVO selectOne(int post_no) {
		return postDAO.selectOne(post_no);
	}
	
	@Override
	public int changeState(int post_no) {
		return postDAO.changeState(post_no);
	}
	
	@Override
	public int update(PostVO vo) {
		return postDAO.update(vo);
	}

}
