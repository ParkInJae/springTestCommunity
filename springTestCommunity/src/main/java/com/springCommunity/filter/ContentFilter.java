package com.springCommunity.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@WebFilter({"/post/writeOk.do","/post/modifyOk.do"
	,"/chat/sendMessage.do","/chat/updateChatUserName.do"
	,"/comment/write.do","/comment/modify.do"})
@Component
public class ContentFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//필터 생성 시점 호출. 단 한번만 호출
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {
		System.out.println("필터실행");
		String post_title = "";
		if(request.getParameter("post_title") != null && !request.getParameter("post_title").equals("")) {
			post_title = request.getParameter("post_title");
		    post_title = sanitizeInput(post_title);       // XSS 방지 필터링
		    post_title = filterProfanity(post_title);     // 비속어 처리
		    request.setAttribute("post_title", post_title);
		    System.out.println("Filter post_title: " + post_title);
		}
		String post_content = "";
		if(request.getParameter("post_content") != null && !request.getParameter("post_content").equals("")) {
			post_content = request.getParameter("post_content");
			post_content = sanitizeInput(post_content);   // XSS 방지 필터링
		    post_content = filterProfanity(post_content); // 비속어 처리
		    request.setAttribute("post_content", post_content);
		    System.out.println("Filter post_content: " + post_content);
		}
		
		String chat_message_content = "";
		if(request.getParameter("chat_message_content") != null 
				&& !request.getParameter("chat_message_content").equals("")) {
			System.out.println("request chat_message_content" + request.getParameter("chat_message_content"));
			chat_message_content = request.getParameter("chat_message_content");
			chat_message_content = sanitizeInput(chat_message_content);   // XSS 방지 필터링
			chat_message_content = filterProfanity(chat_message_content); // 비속어 처리
		    request.setAttribute("chat_message_content", chat_message_content);
		    System.out.println("Filter chat_message_content: " + chat_message_content);
		}
		
		String chat_users_name = "";
		if(request.getParameter("chat_users_name") != null 
				&& !request.getParameter("chat_users_name").equals("")) {
			chat_users_name = request.getParameter("chat_users_name");
			chat_users_name = sanitizeInput(chat_users_name);   // XSS 방지 필터링
			chat_users_name = filterProfanity(chat_users_name); // 비속어 처리
		    request.setAttribute("chat_users_name", chat_users_name);
		    System.out.println("Filter chat_users_name: " + chat_users_name);
		}
		
		String comment_content = "";
		if(request.getParameter("comment_content") != null 
				&& !request.getParameter("comment_content").equals("")) {
			comment_content = request.getParameter("comment_content");
			comment_content = sanitizeInput(comment_content);   // XSS 방지 필터링
			comment_content = filterProfanity(comment_content); // 비속어 처리
		    request.setAttribute("comment_content", comment_content);
		    System.out.println("Filter comment_content: " + comment_content);
		}

	    chain.doFilter(request, response);
	}

	private String sanitizeInput(String input) {
	    if(input == null) {
	        return null;
	    }
	    return input
	            .replaceAll("<", "&lt;")
	            .replaceAll(">", "&gt;")
	            .replaceAll("\"", "&quot;")
	            .replaceAll("'", "&#x27;")
	            .replaceAll("&", "&amp;")
	            .replaceAll("\n", "<br>"); 
	}

	private String filterProfanity(String input) {
	    if(input == null) {
	        return null;
	    }
	    // 비속어 목록
	    String[] list = {
	    	    "ㅅㅂ", "ㅆㅂ", "ㅂㅅ", "ㅄ", "ㅈㄹ", "ㅉㅉ",
	    	    "씨발", "씨바", "씨빨", "시발", "시빨", "씨1발", "시1발",
	    	    "ㅅㅐ끼", "새끼", "썅", "병신", "ㅂㅕㅇ신", "멍청이",
	    	    "개새끼", "개새", "좆", "ㅈㄱㅊ", "ㅈ같", "ㅈ까",
	    	    "똘추", "또라이", "닭대가리", "미친놈", "미친년",
	    	    "애미", "애비", "닥쳐", "꺼져", "죽어", "꺼지",
	    	    "빡대가리", "좆같", "엿", "느금", "느금마", "느개비",
	    	    "ㄴㄱㅁ", "ㄱㅅㄲ", "ㄱㅐㅅㅐㄲㅣ", "잡것"
	    	};

	    for(String word : list) {
	        input = input.replaceAll("(?i)" + word, "****");
	    }
	    return input;
	}

	@Override
	public void destroy() {
		
	}
	
}