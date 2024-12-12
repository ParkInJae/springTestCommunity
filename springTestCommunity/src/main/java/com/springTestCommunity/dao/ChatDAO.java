package com.springTestCommunity.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springTestCommunity.vo.*;

@Repository
public class ChatDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	private final String name_space = "com.springCommunity.mapper.chatMapper.";
	
	public int selectTotal(SearchVO searchVO) {
		return sqlSession.selectOne(name_space+"selectTotal",searchVO);
	}
	
	public List<ChatVO> selectAll(SearchVO searchVO){
		return sqlSession.selectList(name_space+"selectAll",searchVO);
	}
	
	public List<UserInfoVO> searchUsers(SearchVO searchVO) {
		return sqlSession.selectList(name_space+"searchUsers",searchVO);
	}
	
	public int insertRoom(ChatVO chatVO) {
		return sqlSession.insert(name_space+"insertRoom",chatVO);
	}
	
	public void insertRoomAfterUser(ChatVO chatVO) {
		sqlSession.insert(name_space+"insertRoomAfterUser",chatVO);
	}
	
	public List<UserInfoVO> chatUsers(int chat_no){
		return sqlSession.selectList(name_space+"chatUsers", chat_no);
	}
	
	public int leaveChatRoom(ChatVO chatVO) {
		return sqlSession.delete(name_space+"leaveChatRoom",chatVO);
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
}
