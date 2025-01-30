package com.springCommunity.vo;
 
public class PostVO extends UserInfoVO {
	private int post_no;
	private String user_id;
	private String post_title;
	private String post_content;
	private String post_date;
	private String post_state;
	private String post_type;
	private int no; // 게시물 번호
	private int comment_count; // 댓글 갯수
	
	public int getPost_no()         { return post_no;     }
	public String getUser_id()      { return user_id;     }
	public String getPost_title()   { return post_title;  }
	public String getPost_content() { return post_content;}
	public String getPost_date()    { return post_date;   }
	public String getPost_state()   { return post_state;  }
	public String getPost_type()    { return post_type;   }
	public int getNo()              { return no;          }
	public int getComment_count()   { return comment_count;}	
	
	public void setPost_no(int post_no)              { this.post_no = post_no;          }
	public void setUser_id(String user_id)           { this.user_id = user_id;          }
	public void setPost_title(String post_title)     { this.post_title = post_title;    }
	public void setPost_content(String post_content) { this.post_content = post_content;}
	public void setPost_date(String post_date)       { this.post_date = post_date;      }
	public void setPost_state(String post_state)     { this.post_state = post_state;    }
	public void setPost_type(String post_type)       { this.post_type = post_type;      }
	public void setNo(int no)                        { this.no = no;                    }
	public void setComment_count(int comment_count)  { this.comment_count = comment_count;}
}