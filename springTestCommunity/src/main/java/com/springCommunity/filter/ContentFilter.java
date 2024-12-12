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

@Order(1) 
@WebFilter({"/board/writeOk.do"}) 
@Component
public class ContentFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//필터 생성 시점 호출. 단 한번만 호출
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String title = request.getParameter("title");
		title = title.replaceAll("<", "&lt;");
		title = title.replaceAll(">", "&gt;");
		
		request.setAttribute("title", title);
		
		
		System.out.println("Filter request title =>" + title);
		String content = request.getParameter("content");
		content = content.replaceAll("<", "&lt;");
		content = content.replaceAll(">", "&gt;");
		content = content.replaceAll("\n", "<br>");
		
		
		
		request.setAttribute("content", content);
		
		
		System.out.println("Filter request content =>" + content);
		
		
		chain.doFilter(request, response); 
	}

	@Override
	public void destroy() {
		
	}
	
}