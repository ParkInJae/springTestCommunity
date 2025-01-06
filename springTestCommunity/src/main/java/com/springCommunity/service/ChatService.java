package com.springCommunity.service;

import java.util.List;

import com.springCommunity.vo.*;

public interface ChatService {
	
	public List<ChatVO> selectAll(SearchVO searchVO);
	
	public List<UserInfoVO> searchUsers(SearchVO searchVO);
	
	public int insertRoom(ChatVO chatVO);
	public int addChatUser(int chat_no, String user_id, String chatName);
	
	public List<UserInfoVO> chatUsers(int chat_no);
	
	public int leaveChatRoom(ChatVO chatVO);
	
	public int sendMessage(ChatMessageVO chatMessageVO);
    public int sendMessageAfterFirst(ChatMessageVO chatMessageVO);
    public int sendMessageAfterSecond(ChatMessageVO chatMessageVO);
    
    public List<ChatMessageVO> loadMessage(int chat_no);
    public int updateReadState(ChatVO chatVO);
    
    public int updateChatName(ChatVO vo);
	public int updateChatUserName(ChatVO vo);
	
	public int chatStateCount(ChatVO chatVO);
	public List<ChatVO> chatInfo(int chat_no);
    public int updateChatState(ChatVO chatVO);
    public int sendSystemMessage(ChatVO chatVO);
    
    public int updateChatGroup(int chat_no);
    public int updateChatOne(int chat_no);
    
    public ChatVO chatName(ChatVO vo);
    
    public int updateChatTop(ChatVO vo);
    
    public int unreadMessageCounts(String user_id);
}
