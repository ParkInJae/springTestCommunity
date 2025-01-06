package com.springCommunity.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springCommunity.vo.*;

@Repository
public class ChatDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	private final String name_space = "com.springCommunity.mapper.chatMapper.";
	
	public List<ChatVO> selectAll(SearchVO searchVO){
		return sqlSession.selectList(name_space+"selectAll",searchVO);
	}
	
	public List<UserInfoVO> searchUsers(SearchVO searchVO) {
		return sqlSession.selectList(name_space+"searchUsers",searchVO);
	}
	
	public int insertRoom(ChatVO chatVO) {
		return sqlSession.insert(name_space+"insertRoom",chatVO);
	}
	
	// 채팅방에 사용자 추가
    public int addChatUser(ChatVO chatVO) {
    	return sqlSession.insert(name_space + "addChatUser", chatVO);
    }
	
	public List<UserInfoVO> chatUsers(int chat_no){
		return sqlSession.selectList(name_space+"chatUsers", chat_no);
	}
	
	public int leaveChatRoom(ChatVO chatVO) {
		return sqlSession.update(name_space+"leaveChatRoom",chatVO);
	}
	
	// 채팅메시지 저장
    public int sendMessage(ChatMessageVO chatMessageVO) {
    	return sqlSession.insert(name_space + "sendMessage", chatMessageVO);
    }
    
    public int sendMessageAfterFirst(ChatMessageVO chatMessageVO) {
    	return sqlSession.insert(name_space + "sendMessageAfterFirst", chatMessageVO);
    }
    
    public int sendMessageAfterSecond(ChatMessageVO chatMessageVO) {
    	return sqlSession.insert(name_space + "sendMessageAfterSecond", chatMessageVO);
    }
    
    // 기존 채팅메시지 목록
    public List<ChatMessageVO> loadMessage(int chat_no){
    	return sqlSession.selectList(name_space + "loadMessage", chat_no);
    }
    
    public int updateReadState(ChatVO chatVO) {
    	return sqlSession.update(name_space + "updateReadState", chatVO);
    }
    
    // 채팅방 전체이름 수정 
    public int updateChatName(ChatVO vo) {
        return sqlSession.update(name_space + "updateChatName", vo);
    }
    // 채팅방 각자이름 수정
    public int updateChatUserName(ChatVO vo) {
    	return sqlSession.update(name_space + "updateChatUserName", vo);
    }
    
    public int chatStateCount(ChatVO chatVO) {
    	return sqlSession.update(name_space + "chatStateCount", chatVO);
    }
    
    public List<ChatVO> chatInfo(int chat_no) {
    	return sqlSession.selectList(name_space + "chatInfo", chat_no);
    }
    
    public int updateChatState(ChatVO chatVO) {
    	return sqlSession.update(name_space + "updateChatState", chatVO);
    }
    
    public int sendSystemMessage(ChatVO chatVO) {
    	return sqlSession.insert(name_space + "sendSystemMessage", chatVO);
    }
    
    public int updateChatGroup(int chat_no) {
    	return sqlSession.update(name_space + "updateChatGroup", chat_no);
    }
    
    public int updateChatOne(int chat_no) {
    	return sqlSession.update(name_space + "updateChatOne", chat_no);
    }
    
    public ChatVO chatName(ChatVO vo) {
    	return sqlSession.selectOne(name_space + "chatName", vo);
    }
    
    public int updateChatTop(ChatVO vo) {
    	return sqlSession.update(name_space + "updateChatTop", vo);
    }
    
    public ChatVO selectUserState(ChatVO vo) {
    	return sqlSession.selectOne(name_space + "selectUserState", vo);
    }
    
    public int updateUserState(ChatVO vo) {
    	return sqlSession.update(name_space + "updateUserState", vo);
    }
    
    public int unreadMessageCounts(String user_id) {
    	return sqlSession.selectOne(name_space + "unreadMessageCounts", user_id);
    }
}
