package com.springCommunity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springCommunity.dao.CommentDAO;
import com.springCommunity.vo.CommentVO;
import com.springCommunity.vo.SearchVO;



@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	public CommentDAO commentDAO;
	
	@Override
	public List<CommentVO> list(SearchVO searchVO) {
		return commentDAO.list(searchVO);
	}
	
	@Override
	public int selectTotal(SearchVO searchVO) {
		return commentDAO.total(searchVO);
	}
	
	@Override
	public int insert(CommentVO vo) {
		return commentDAO.insert(vo);
	}
	
	@Override
	public CommentVO selectOne(int comment_no) {
		return commentDAO.selectOne(comment_no);
	}
	
	@Override
	public int changeState(int comment_no) {
		return commentDAO.changeState(comment_no);
	}
	
	@Override
	public int update(CommentVO vo) {
		return commentDAO.update(vo);
	}

}
