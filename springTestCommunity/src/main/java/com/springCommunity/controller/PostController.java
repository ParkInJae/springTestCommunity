package com.springCommunity.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springCommunity.service.PostService;
import com.springCommunity.util.PagingUtil;
import com.springCommunity.vo.PostVO;
import com.springCommunity.vo.SearchVO;
import com.springCommunity.vo.UserVO;

@RequestMapping(value="/post")
@Controller
public class PostController {
	
	@Autowired
	public PostService postService;
	
	@RequestMapping(value="/list.do", method = RequestMethod.GET)
	public String list(Model model,SearchVO searchVO
			,@RequestParam(value="nowpage"
						,required = false
						,defaultValue="1")int nowpage) {
		
		int total = postService.selectTotal(searchVO);
		
		PagingUtil paging = new PagingUtil(nowpage, total, 5);
		
		searchVO.setStart(paging.getStart());
		searchVO.setPerPage(paging.getPerPage());
		
		List<PostVO> list = postService.list(searchVO);
		
		// 번호 계산 및 설정
	    int displayNo = total - (nowpage - 1) * paging.getPerPage();
	    for(PostVO vo : list) {
	        vo.setNo(displayNo--); // 각 게시물 번호 설정
	        vo.setPost_title(restoreSanitizedInput(vo.getPost_title()));
	        vo.setPost_content(restoreSanitizedInput(vo.getPost_content()));
	    }
		
		model.addAttribute("list",list);
		model.addAttribute("paging",paging);
		
		
		return "post/list";
	}
	
	@RequestMapping(value="/write.do", method=RequestMethod.GET)
	public String write(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		
		return "post/write";
	}
	
	
	@RequestMapping(value="/writeOk.do",method=RequestMethod.POST)
	public String write(PostVO vo,HttpServletRequest request,Principal principal) {
		
		vo.setUser_id(principal.getName());
		
		if(request.getAttribute("post_title") != null && !request.getAttribute("post_title").equals("")) {
    		vo.setPost_title((String)request.getAttribute("post_title"));
    	}
    	if(request.getAttribute("post_content") != null && !request.getAttribute("post_content").equals("")) {
    		vo.setPost_content((String)request.getAttribute("post_content"));
    	}
		
		int result = postService.insert(vo);
		
		if(result>0) {
			return "redirect:list.do?post_no="+vo.getPost_no()+"&post_type="+vo.getPost_type();
			
		}else {
			return "redirect:write.do?post_type="+vo.getPost_type();
		}
	}
	
	@RequestMapping(value="/modify.do", method=RequestMethod.GET)
	public String modify(int post_no, Principal principal, Model model) {
		String loginUser = principal.getName(); 
        System.out.println("loginUser : "+ loginUser);
        PostVO vo = postService.selectOne(post_no);
        String writer = vo.getUser_id();
        
        // 작성자와 로그인한 사용자가 다르면 리턴 처리
        if(!loginUser.equals(writer)) {
            return "redirect:list.do?post_type=" + vo.getPost_type();
        }
		
		model.addAttribute("vo", vo);
		
		return "post/modify";
	}
	
    @RequestMapping(value="/modifyOk.do", method=RequestMethod.POST)
    public String modify(PostVO vo,HttpServletRequest request) {
    	
    	if(request.getAttribute("post_title") != null && !request.getAttribute("post_title").equals("")) {
    		vo.setPost_title((String)request.getAttribute("post_title"));
    	}
    	if(request.getAttribute("post_content") != null && !request.getAttribute("post_content").equals("")) {
    		vo.setPost_content((String)request.getAttribute("post_content"));
    	}
    	
        int result = postService.update(vo);
        
        if(result > 0) {
        	System.out.println("수정성공");
        }else {
        	System.out.println("수정실패");
        }
        
        return "redirect:list.do?post_type="+vo.getPost_type();
    }

	@RequestMapping(value="/view.do", method = RequestMethod.GET)
	public String view(int post_no, Model model) {
		
		PostVO vo = postService.selectOne(post_no);
		vo.setPost_title(restoreSanitizedInput(vo.getPost_title()));
        vo.setPost_content(restoreSanitizedInput(vo.getPost_content()));
		model.addAttribute("vo", vo);
		
		return "post/view";
	}
	
	@RequestMapping(value="/delete.do",method=RequestMethod.POST)
	public String delete(int post_no) {
		
		int result = postService.changeState(post_no);
		
		return "redirect:list.do";
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