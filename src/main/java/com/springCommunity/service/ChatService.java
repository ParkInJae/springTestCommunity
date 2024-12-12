package com.springCommunity.service;

import java.util.List;

import com.springCommunity.vo.*;

public interface ChatService {
	
	public int selectTotal(SearchVO searchVO);
	public List<ChatVO> selectAll(SearchVO searchVO);
	public List<UserInfoVO> searchUsers(SearchVO searchVO);
	public int insertRoom(ChatVO chatVO);
	public void insertRoomAfterUser(ChatVO chatVO);
	public List<UserInfoVO> chatUsers(int chat_no);
	public int leaveChatRoom(ChatVO chatVO);
}
