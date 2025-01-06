package com.springCommunity.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springCommunity.service.CommentService;
import com.springCommunity.util.PagingUtil;
import com.springCommunity.vo.CommentVO;
import com.springCommunity.vo.SearchVO;


@RequestMapping(value="/comment")
@Controller
public class CommentController {
	
	@Autowired
	public CommentService commentService;
	
	@ResponseBody
	@RequestMapping(value="/loadComment.do",method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	public Map<String,Object> loadComment(int post_no, Model model, SearchVO searchVO
			,@RequestParam(value="cnowpage"
			,required = false
			,defaultValue="1")int cnowpage){
		
		int total = commentService.selectTotal(searchVO);
		
		PagingUtil paging = new PagingUtil(cnowpage, total, 5);
		
		searchVO.setStart(paging.getStart());
		searchVO.setPerPage(paging.getPerPage());
		
		List<CommentVO> list = commentService.list(searchVO);
		
		// 번호 계산 및 설정
	    int displayNo = total - (cnowpage - 1) * paging.getPerPage();
	    for(CommentVO cvo : list) {
	    	cvo.setNo(displayNo--); // 각 게시물 번호 설정
	    	cvo.setComment_content(restoreSanitizedInput(cvo.getComment_content()));
	    }
	    
	    Map<String,Object> map = new HashedMap<String, Object>();
		map.put("clist",list);
		map.put("cpaging",paging);
		map.put("post_type",searchVO.getPost_type());
		return map;
	}
	
	@RequestMapping(value="/write.do",method=RequestMethod.POST)
	public String write(CommentVO vo,HttpServletRequest request,Principal principal,int post_type) {
		
		vo.setUser_id(principal.getName());
		
		if(request.getAttribute("comment_content") != null && !request.getAttribute("comment_content").equals("")) {
    		vo.setComment_content((String)request.getAttribute("comment_content"));
    	}
		
		int result = commentService.insert(vo);
		
		if(result>0) {
			System.out.println("등록성공");
		}else {
			System.out.println("등록실패");
		}
		return "redirect:/post/view.do?post_no="+vo.getPost_no()+"&post_type="+post_type;
	}
	
	@ResponseBody
    @RequestMapping(value="/modify.do", method=RequestMethod.POST)
    public String modify(CommentVO vo,HttpServletRequest request) {
    	
    	if(request.getAttribute("comment_content") != null && !request.getAttribute("comment_content").equals("")) {
    		vo.setComment_content((String)request.getAttribute("comment_content"));
    	}
    	
        int result = commentService.update(vo);
        
        if(result > 0) {
        	System.out.println("수정성공");
        	return "Success";
        }else {
        	System.out.println("수정실패");
        	return "Fail";
        }
    }
    
    @ResponseBody
	@RequestMapping(value="/delete.do",method=RequestMethod.POST)
	public String delete(int comment_no) {
		
		int result = commentService.changeState(comment_no);
		if(result > 0) {
			return "Success";
		}else {
			return "Fail";
		}
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