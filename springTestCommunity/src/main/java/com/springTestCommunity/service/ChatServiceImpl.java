package com.springTestCommunity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springTestCommunity.dao.ChatDAO;
import com.springTestCommunity.vo.*;

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
	
	@Override
	public int sendMessage(ChatMessageVO chatMessageVO) {
		return chatDAO.sendMessage(chatMessageVO);
	}
	
	@Override
	public int sendMessageAfterFirst(ChatMessageVO chatMessageVO) {
		return chatDAO.sendMessageAfterFirst(chatMessageVO);
	}
	
	@Override
	public int sendMessageAfterSecond(ChatMessageVO chatMessageVO) {
		return chatDAO.sendMessageAfterSecond(chatMessageVO);
	}

	@Override
	public List<ChatMessageVO> loadMessage(int chat_no) {
		return chatDAO.loadMessage(chat_no);
	}
}
