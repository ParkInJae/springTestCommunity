package com.springCommunity.service;

import java.util.List;

import com.springCommunity.vo.PostVO;
import com.springCommunity.vo.SearchVO;

public interface PostService {
	
	public List<PostVO> list(SearchVO searchVO);
	public int selectTotal(SearchVO searchVO);
	public int insert(PostVO vo);
	
	public PostVO selectOne(int post_no);
	
	public int changeState(int post_no);
	
	public int update(PostVO vo);
}
