package com.springTestCommunity.service;

import java.util.List;

import com.springTestCommunity.vo.NoticeVO;
import com.springTestCommunity.vo.SearchVO;

public interface NoticeService {
	
	public List<NoticeVO> list(SearchVO searchVO);
	public int selectTotal(SearchVO searchVO);
	public int insert(NoticeVO vo);
	
	public NoticeVO selectOne(int post_no);
	
	public int changeState(int post_no);
	
	public int selectCntFreeByUid(String user_id);
	
}
