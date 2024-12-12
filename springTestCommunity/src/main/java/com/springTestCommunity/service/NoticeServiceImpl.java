package com.springTestCommunity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springTestCommunity.dao.NoticeDAO;
import com.springTestCommunity.vo.NoticeVO;
import com.springTestCommunity.vo.SearchVO;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	public NoticeDAO noticeDAO;
	
	@Override
	public List<NoticeVO> list(SearchVO searchVO) {
		return noticeDAO.list(searchVO);
	}
	
	@Override
	public int selectTotal(SearchVO searchVO) {
		return noticeDAO.total(searchVO);
	}
	
	@Override
	public int insert(NoticeVO vo) {
		return noticeDAO.insert(vo);
	}
	
	@Override
	public NoticeVO selectOne(int post_no) {
		return noticeDAO.selectOne(post_no);
	}
	
	@Override
	public int changeState(int post_no) {
		return noticeDAO.changeState(post_no);
	}
	
	@Override
	public int selectCntFreeByUid(String user_id) {
		return noticeDAO.selectCntFreeByUid(user_id);
	}
	
	
}
