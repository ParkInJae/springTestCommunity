package com.springCommunity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springCommunity.dao.ChatDAO;
import com.springCommunity.vo.*;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatDAO chatDAO;
	
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
	public int addChatUser(int chat_no, String user_id, String chat_users_name) {
		ChatVO chatVO = new ChatVO();
		chatVO.setChat_no(chat_no);
        chatVO.setUser_id(user_id);
        ChatVO userState = chatDAO.selectUserState(chatVO);

        if(userState != null) {
            if(userState.getChat_users_state() == 0) {
            	ChatVO message = new ChatVO();
                message.setChat_no(chat_no);
                message.setChat_message_content(userState.getUser_name() + "님이 초대되었습니다.");
                chatDAO.sendSystemMessage(message);
                // 기존에 나간 사용자 상태를 참여로 변경
            	chatVO.setChat_users_state(1);
                return chatDAO.updateUserState(chatVO);
            }else {
                // 이미 참여 중인 사용자 (추가 작업 필요 없음)
                return 1;
            }
        }else {
            // 새로운 사용자 추가
            chatVO.setChat_users_name(chat_users_name);
            return chatDAO.addChatUser(chatVO);
        }
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
	
	@Override
	public int updateReadState(ChatVO chatVO) {
		return chatDAO.updateReadState(chatVO);
	}

	@Override
	public int updateChatName(ChatVO vo) {
		return chatDAO.updateChatName(vo);
	}

	@Override
	public int updateChatUserName(ChatVO vo) {
		return chatDAO.updateChatUserName(vo);
	}

	@Override
	public int chatStateCount(ChatVO chatVO) {
		return chatDAO.chatStateCount(chatVO);
	}

	@Override
	public List<ChatVO> chatInfo(int chat_no) {
		return chatDAO.chatInfo(chat_no);
	}

	@Override
	public int updateChatState(ChatVO chatVO) {
		return chatDAO.updateChatState(chatVO);
	}

	@Override
	public int sendSystemMessage(ChatVO chatVO) {
		return chatDAO.sendSystemMessage(chatVO);
	}

	@Override
	public int updateChatGroup(int chat_no) {
		return chatDAO.updateChatGroup(chat_no);
	}

	@Override
	public int updateChatOne(int chat_no) {
		return chatDAO.updateChatOne(chat_no);
	}

	@Override
	public ChatVO chatName(ChatVO vo) {
		return chatDAO.chatName(vo);
	}

	@Override
	public int updateChatTop(ChatVO vo) {
		return chatDAO.updateChatTop(vo);
	}

	@Override
	public int unreadMessageCounts(String user_id) {
		return chatDAO.unreadMessageCounts(user_id);
	}
}
