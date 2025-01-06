package com.springCommunity.service;

import java.util.List;

import com.springCommunity.vo.CommentVO;
import com.springCommunity.vo.SearchVO;


public interface CommentService {
	
	public List<CommentVO> list(SearchVO searchVO);
	public int selectTotal(SearchVO searchVO);
	public int insert(CommentVO vo);
	
	public CommentVO selectOne(int comment_no);
	
	public int changeState(int comment_no);
	
	public int update(CommentVO vo);
}
