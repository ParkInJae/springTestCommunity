package com.springCommunity.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springCommunity.service.NoticeService;
import com.springCommunity.util.PagingUtil;
import com.springCommunity.vo.NoticeVO;
import com.springCommunity.vo.SearchVO;

@RequestMapping(value="/login/notice")
@Controller
public class Notice2Controller {
	
	@Autowired
	public NoticeService noticeService;
	
	@RequestMapping(value="/list.do")
	public String list(Model model,SearchVO searchVO
			,@RequestParam(value="nowpage"
						,required = false
						,defaultValue="1")int nowpage) {
		
		
		int total = noticeService.selectTotal(searchVO);
		
		PagingUtil paging = new PagingUtil(nowpage, total, 2);
		
		searchVO.setStart(paging.getStart());
		searchVO.setPerPage(paging.getPerPage());
		
		List<NoticeVO> list = noticeService.list(searchVO);
		
		model.addAttribute("list",list);
		model.addAttribute("paging",paging);
		
		
		return "notice/list";
	}
	
	@RequestMapping(value="/write.do",method=RequestMethod.GET)
	public String write(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		
		
		
		
		
		
		return "notice/write";
	}
	
	
}
