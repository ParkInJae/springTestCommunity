package com.springCommunity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springCommunity.dao.ChatDAO;
import com.springCommunity.vo.ChatVO;
import com.springCommunity.vo.SearchVO;
import com.springCommunity.vo.UserInfoVO;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatDAO chatDAO;
	
	@Override
	public int selectTotal(SearchVO searchVO) {
		return chatDAO.selectTotal(searchVO);
	}

	@Override
	public List<ChatVO> selectAll(SearchVO searchVO) {
		return chatDAO.selectAll(searchVO);
	}

	@Override
	public List<UserInfoVO> searchUsers(SearchVO searchVO) {
		return chatDAO.searchUsers(searchVO);
	}

	@Override
	public int insertRoom(ChatVO chatVO) {
		return chatDAO.insertRoom(chatVO);
	}

	@Override
	public void insertRoomAfterUser(ChatVO chatVO) {
		chatDAO.insertRoomAfterUser(chatVO);
	}

	@Override
	public List<UserInfoVO> chatUsers(int chat_no) {
		return chatDAO.chatUsers(chat_no);
	}

	@Override
	public int leaveChatRoom(ChatVO chatVO) {
		return chatDAO.leaveChatRoom(chatVO);
	}
}
