package com.springCommunity.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springCommunity.service.ChatService;
import com.springCommunity.vo.*;


@RequestMapping(value="/chat")
@Controller
public class ChatController {
	
	@Autowired
	ChatService chatService;
	
	@ResponseBody
    @RequestMapping(value = "/chat.do", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public Map<String,Object> chat(Model model,SearchVO searchVO) {
    	// 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // 사용자 ID 추출
        String loggedInUserId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if(principal instanceof UserDetails) {
                loggedInUserId = ((UserDetails) principal).getUsername();
            }else {
                // principal이 문자열일 경우
                loggedInUserId = principal.toString();
            }
        }

        // 로그인한 사용자 ID를 searchVO에 설정
        searchVO.setUser_id(loggedInUserId);
        
        if (searchVO.getSearch_value() == null || searchVO.getSearch_value().isEmpty()) {
            searchVO.setSearch_value("");
        }

        
        Map<String,Object> map = new HashMap<String, Object>();
		
		List<ChatVO> list = chatService.selectAll(searchVO);
		for(ChatVO vo : list) {
			vo.setChat_message_content(restoreSanitizedInput(vo.getChat_message_content()));
			vo.setChat_users_name(restoreSanitizedInput(vo.getChat_users_name()));
		}
		model.addAttribute("list", list);
    	
		if(list.isEmpty()) {
			if(!searchVO.getSearch_value().isEmpty()) {
	            map.put("message", "검색한 내용에 맞는 채팅방이 없습니다.");
	        }else {
	            map.put("message", "현재 채팅방이 없습니다. 새 채팅방을 생성해 보세요.");
	        }
		}else{
			map.put("list", list);
		}
		
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
		
		return list;
	}
	
	// 채팅방 생성
    @ResponseBody
    @RequestMapping(value ="/create.do", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
    public String createChatRoom(@RequestBody Map<String,Object> data) {
    	System.out.println("createChatRoom controller 실행");
        String user_id = (String)data.get("user_id");
        String user_name = (String)data.get("user_name");
        System.out.println("controller createChatRoom user_id "+user_id);
        
        List<String> users = (List<String>)data.get("users");
        
        ChatVO chatVO = new ChatVO();
        chatVO.setUser_id(user_id);
        
        // 채팅방의 chat_group 값 설정 (유저 수에 따라)
        int chatGroup = (users.size() > 1) ? 0 : 1; // 2명 이상이면 0, 1명일 경우 1
        chatVO.setChat_group(chatGroup);

        // 채팅방 생성
        int result = chatService.insertRoom(chatVO);
        
        String chat_users_name = (String)data.get("chat_users_name"); 
        if(result > 0) {
            for(String user : users) {
                if(chatGroup == 1) {
                	chatService.addChatUser(chatVO.getChat_no(), user, user_name);
                }else {
                	chatService.addChatUser(chatVO.getChat_no(), user, chat_users_name);
                }
            }
            chatService.addChatUser(chatVO.getChat_no(), chatVO.getUser_id(), chat_users_name);
            return chatVO.getChat_no()+":"+chat_users_name;
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
    public String leaveChatRoom(ChatVO chatVO,String user_name) {
    	
    	int result = chatService.leaveChatRoom(chatVO);
    	if(result > 0) {
    		System.out.println("채팅방 나가기 성공");
    		
    		//채팅방 참가하고 있는 회원의 참가상태 확인
    		int count = chatService.chatStateCount(chatVO);
    		
    		if(count == 0) {
    			//참가자들 전원이 나감상태일때 채팅방 상태 비활성화처리
    			chatService.updateChatState(chatVO);
    		}
    		
    		//그룹 채팅일 경우 알림 메시지 추가
            if(chatVO.getChat_group() == 0) {
            	chatVO.setChat_message_content(user_name + "님이 나갔습니다.");
                chatService.sendSystemMessage(chatVO);
                
                int chatState = chatService.chatStateCount(chatVO);
    			if(chatState <= 2) {
    				//그룹(0)에서 1대1채팅(1)으로 변경
    				chatService.updateChatOne(chatVO.getChat_no());
    			}
            }
    		
    		return "Success";
    	}else {
    		System.out.println("채팅방 나가기 실패");
    		return "Failed";
    	}
    }
    
    // 메시지 전송
    @ResponseBody
    @RequestMapping(value ="/sendMessage.do", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
    public void sendMessage(ChatMessageVO chatMessageVO,HttpServletRequest request) {
    	System.out.println("chatMessageVO "+chatMessageVO.getChat_message_content());
    	
    	if(request.getAttribute("chat_message_content") != null 
    			&& !request.getAttribute("chat_message_content").equals("")) {
    		chatMessageVO.setChat_message_content((String)request.getAttribute("chat_message_content"));
    	}
    	
    	int result = chatService.sendMessage(chatMessageVO);
    	
    	if(result > 0) {
    		System.out.println("채팅메시지 저장 성공");
    		System.out.println("chatMessageVO 기본키:" + chatMessageVO.getChat_message_no());
    		chatService.sendMessageAfterFirst(chatMessageVO);
    		chatService.sendMessageAfterSecond(chatMessageVO);
    	}else {
    		System.out.println("채팅메시지 저장 실패");
    	}
    }
    
    @ResponseBody
    @RequestMapping(value="/loadMessage.do", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public List<ChatMessageVO> loadMessage(int chat_no){
    	List<ChatMessageVO> list = chatService.loadMessage(chat_no);
    	for(ChatMessageVO vo : list) {
    		vo.setChat_message_content(restoreSanitizedInput(vo.getChat_message_content()));
    	}
    	return list;
    }
    
    @RequestMapping(value ="/messagesRead.do", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
    public void messagesRead(@RequestBody ChatVO chatVO) {
        int result = chatService.updateReadState(chatVO);
    }
    
   // 채팅방 이름 변경(참가자 전체변경)
    @ResponseBody
    @PostMapping("/updateChatName.do")
    public String updateChatName(ChatVO vo) {
        int result = chatService.updateChatName(vo);
        if(result > 0) {
        	return "Success";
        }else {
        	return "Fail";
        }
    }
    
    // 채팅방 이름 변경(참가자 각자변경)
    @ResponseBody
    @PostMapping("/updateChatUserName.do")
    public Map<String,Object> updateChatUserName(ChatVO vo,HttpServletRequest request) {
    	if(request.getAttribute("chat_users_name") != null 
    			&& !request.getAttribute("chat_users_name").equals("")) {
    		vo.setChat_users_name((String)request.getAttribute("chat_users_name"));
    	}
    	
        int result = chatService.updateChatUserName(vo);
        
        Map<String,Object> map = new HashMap<String, Object>();
        
        if(result > 0) {
        	ChatVO aVO = chatName(vo);
        	map.put("result", "Success");
        	map.put("vo", aVO);
        }else {
        	map.put("result", "Fail");
        }
        return map;
    }
    
    @ResponseBody
    @RequestMapping(value = "/addUser.do", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
    public String addChatUser(@RequestBody Map<String, Object> data) {
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
    	
        List<String> users = (List<String>)data.get("users"); // 초대할 사용자 리스트
        int chat_no = (Integer)data.get("chat_no");          // 채팅방 번호
        String chat_users_name = (String)data.get("chat_users_name"); // 채팅방 이름

        int result = 0;

        List<ChatVO> list = chatService.chatInfo(chat_no); // 기존 채팅 정보 조회
        // 채팅 그룹이 1인 경우 이름 업데이트
        if(!list.isEmpty() && list.get(0).getChat_group() == 1) {
            ChatVO vo = list.get(0); // 첫 번째 채팅 정보 사용
            vo.setChat_users_name(chat_users_name);
            result = chatService.updateChatName(vo);
            chatService.updateChatGroup(chat_no);
        }

        // 사용자 추가 처리
        List<String> beforeUserNames = new ArrayList<>();
        for(ChatVO vo : list) {
            beforeUserNames.add(vo.getUser_name());
        }

        // 모든 사용자 처리
        for(String user : users) {
            int addResult = chatService.addChatUser(chat_no, user, chat_users_name);
            if(addResult <= 0) {
                return "Fail"; // 한 명이라도 추가 실패 시 Fail 반환
            }
        }

        // 사용자 추가 후 결과 확인
        List<ChatVO> afterList = chatService.chatInfo(chat_no); 
        List<String> afterUserNames = new ArrayList<>();
        for(ChatVO afterUserName : afterList) {
            afterUserNames.add(afterUserName.getUser_name());
        }

        afterUserNames.removeAll(beforeUserNames); 

        // 초대 메시지 전송
        for(String afterUserName : afterUserNames) {
            ChatVO message = new ChatVO();
            message.setChat_no(chat_no);
            message.setChat_message_content(afterUserName + "님이 초대되었습니다.");
            int result2 = chatService.sendSystemMessage(message);
            if(result2 > 0) {
            	ChatVO readVO = new ChatVO();
            	readVO.setChat_no(chat_no);
            	readVO.setUser_id(loginUser);
            	readVO.setChat_message_no(message.getChat_message_no());
            	chatService.sendMessageAfterFirst(readVO);
        		chatService.sendMessageAfterSecond(readVO);
            }
        }

        return "Success"; 
    }
    
    @ResponseBody
    @PostMapping("/chatName.do")
    public ChatVO chatName(ChatVO vo) {
    	
    	ChatVO resultVO = chatService.chatName(vo);
    	resultVO.setChat_users_name(restoreSanitizedInput(resultVO.getChat_users_name()));
    	
        return resultVO;
    }
    
    @ResponseBody
    @PostMapping("/updateChatTop.do")
    public String updateChatTop(ChatVO vo) {
        int result = chatService.updateChatTop(vo); 
        if(result > 0) {
        	return("Success");
        }else {
        	return("Fail");
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "/unreadMessageCounts.do", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public int unreadMessageCounts(String user_id) {
        return chatService.unreadMessageCounts(user_id);
    }
    
    private String restoreSanitizedInput(String input) {
        if(input == null) {
            return null;
        }
        input = input
                .replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&quot;", "\"")
                .replaceAll("&#x27;", "'")
                .replaceAll("&amp;", "&")
                .replaceAll("<br>", "\n");
        return input;
    }
}