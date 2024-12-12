package com.springTestCommunity.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springTestCommunity.service.NoticeService;
import com.springTestCommunity.util.PagingUtil;
import com.springTestCommunity.vo.NoticeVO;
import com.springTestCommunity.vo.SearchVO;
import com.springTestCommunity.vo.UserVO;

@Controller
public class NoticeController {
	
	@Autowired
	public NoticeService noticeService;
	
	@RequestMapping(value="/notice/list.do")
	public String list(Model model,SearchVO searchVO
			,@RequestParam(value="nowpage"
						,required = false
						,defaultValue="1")int nowpage) {
		
		int total = noticeService.selectTotal(searchVO);
		
		PagingUtil paging = new PagingUtil(nowpage, total, 5);
		
		searchVO.setStart(paging.getStart());
		searchVO.setPerPage(paging.getPerPage());
		
		List<NoticeVO> list = noticeService.list(searchVO);
		
		model.addAttribute("list",list);
		model.addAttribute("paging",paging);
		
		
		return "notice/list";
	}
	
	@RequestMapping(value="/notice/write.do", method=RequestMethod.GET)
	public String write(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		
		/*
		 * if(principal== null || principal.getName() == null) {
		 * 
		 * response.setCharacterEncoding("utf-8");
		 * response.setContentType("text/html;charset=utf-8");
		 * 
		 * response.getWriter().append("<script>alert('로로그인 후 이용하세요.');"
		 * +"location.href='" +request.getContextPath() +"/login.do'</script>").flush();
		 * 
		 * }
		 */
		
		return "notice/write";
	}
	
	
	@RequestMapping(value="/notice/writeOk.do",method=RequestMethod.POST)
	public String write(NoticeVO vo,HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO)session.getAttribute("loginUser");
		
		vo.setUser_id(loginUser.getUser_id());
		
		System.out.println(vo.getPost_no());
		
		
		vo.setPost_title((String)request.getAttribute("post_title"));
		vo.setPost_content((String)request.getAttribute("post_content"));
		
		int result = noticeService.insert(vo);
		
		if(result>0) {
			return "redirect:view.do?nno="+vo.getPost_no();
			
		}else {
			return "redirect:write.do";
		}
	
	
	}
	
	@RequestMapping(value="/notice/view.do")
	public String view(int post_no, Model model) {
		
		NoticeVO vo = noticeService.selectOne(post_no);
		
		model.addAttribute("vo", vo);
		
		return "notice/view";
	}
	
	@RequestMapping(value="/notice/delete.do",method=RequestMethod.POST)
	public String delete(int post_no) {
		
		int result = noticeService.changeState(post_no);
		
		return "redirect:list.do";
	}
	
}
