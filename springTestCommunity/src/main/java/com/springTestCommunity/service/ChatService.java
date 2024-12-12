package com.springTestCommunity.service;

import java.util.List;

import com.springTestCommunity.vo.*;

public interface ChatService {
	
	public int selectTotal(SearchVO searchVO);
	public List<ChatVO> selectAll(SearchVO searchVO);
	
	public List<UserInfoVO> searchUsers(SearchVO searchVO);
	
	public int insertRoom(ChatVO chatVO);
	public void insertRoomAfterUser(ChatVO chatVO);
	
	public List<UserInfoVO> chatUsers(int chat_no);
	
	public int leaveChatRoom(ChatVO chatVO);
	
	public int sendMessage(ChatMessageVO chatMessageVO);
    public int sendMessageAfterFirst(ChatMessageVO chatMessageVO);
    public int sendMessageAfterSecond(ChatMessageVO chatMessageVO);
    
    public List<ChatMessageVO> loadMessage(int chat_no);
}
