package com.springCommunity.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springCommunity.service.ChatService;
import com.springCommunity.vo.ChatVO;
import com.springCommunity.vo.SearchVO;
import com.springCommunity.vo.UserInfoVO;

@RequestMapping(value="/chat")
@Controller
public class CharController {
	
	@Autowired
	ChatService chatService;
	
	@ResponseBody
	@RequestMapping(value="/chat.do",produces = "application/json; charset=utf-8")
	public Map<String,Object> chat(SearchVO searchVO){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginUser = null;
		if(authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
			if(principal instanceof UserDetails) {
				loginUser = ((UserDetails)principal).getUsername();
			}else {
				loginUser = principal.toString();
			}
		}
		System.out.println("로그인 유저:" + loginUser);
		
		searchVO.setUser_id(loginUser);
		
		if(searchVO.getSearch_value() == null || searchVO.getSearch_value().isEmpty()) {
			searchVO.setSearch_value("");
		}
		
		int total = chatService.selectTotal(searchVO);
		
		List<ChatVO> list = chatService.selectAll(searchVO);
		
		Map<String,Object> map = new HashedMap<String, Object>();
		
		map.put("list", list);
		map.put("total", total);
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="/searchUsers.do", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public List<UserInfoVO> searchUsers(SearchVO searchVO){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginUser = null;
		if(authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
			if(principal instanceof UserDetails) {
				loginUser = ((UserDetails)principal).getUsername();
			}else {
				loginUser = principal.toString();
			}
		}
		System.out.println("검색 값: " + searchVO.getSearch_value());
		System.out.println("searchUsers 로그인 유저:" + loginUser);
		searchVO.setUser_id(loginUser);
		
		List<UserInfoVO> list = chatService.searchUsers(searchVO);
		if(list.size()>0) {
			System.out.println("user =>"+list.get(0).getUser_id());
		}
		System.out.println("list 길이" + list.size());
		
		return list;
	}
	
	// 채팅방 생성
    @ResponseBody
    @RequestMapping(value ="/create.do", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
    public String createChatRoom(@RequestBody Map<String,Object> data) {
    	System.out.println("createChatRoom controller 실행");
        String user_id = (String)data.get("user_id");
        System.out.println("controller createChatRoom user_id "+user_id);
        String chat_name = (String)data.get("chat_name");
        List<String> users = (List<String>)data.get("users");
        
        ChatVO chatVO = new ChatVO();
        chatVO.setChat_name(chat_name);
        chatVO.setUser_id(user_id);
        
        // 채팅방의 chat_group 값 설정 (유저 수에 따라)
        int chatGroup = (users.size() > 1) ? 0 : 1; // 2명 이상이면 0, 1명일 경우 1
        chatVO.setChat_group(chatGroup);

        // 채팅방 생성
        int result = chatService.insertRoom(chatVO);
        
        if(result > 0) {
            for(String user : users) {
            	ChatVO insertVO = new ChatVO();
                System.out.println("insert 후 chat_no: " + chatVO.getChat_no());
                System.out.println("chatVO.getCHAT_NO() user " + user);
                insertVO.setChat_no(chatVO.getChat_no());
                insertVO.setUser_id(user);
                chatService.insertRoomAfterUser(insertVO);
            }
            ChatVO insertVO2 = new ChatVO();
            insertVO2.setChat_no(chatVO.getChat_no());
            insertVO2.setUser_id(chatVO.getUser_id());
            chatService.insertRoomAfterUser(insertVO2);
            return chatVO.getChat_no()+":"+chat_name;
        }else {
            System.out.println("채팅방 생성 실패");
            return "0";
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "/chatUsers.do", method = RequestMethod.POST)
    public List<UserInfoVO> chatUser(int chat_no) {
    	List<UserInfoVO> list = chatService.chatUsers(chat_no);
    	
    	for(UserInfoVO vo : list) {
    		System.out.println("chatUsers : " + vo.getUser_name());
    	}
    	
    	return list;
    }
    
    @ResponseBody
    @RequestMapping(value = "/leaveChatRoom.do", method = RequestMethod.POST)
    public String leaveChatRoom(ChatVO chatVO) {
    	
    	int result = chatService.leaveChatRoom(chatVO);
    	if(result > 0) {
    		System.out.println("채팅방 나가기 성공");
    		return "Success";
    	}else {
    		System.out.println("채팅방 나가기 실패");
    		return "Failed";
    	}
    }
}
