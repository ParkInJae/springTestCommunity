package com.springCommunity.vo;

public class CommentVO extends UserInfoVO {
	private int comment_no;
	private String comment_date;
	private String comment_content;
	private String comment_state;
	private String user_id;
	private int post_no;
	private int no;
	
	public int getComment_no()         { return comment_no;     }
	public String getComment_date()    { return comment_date;   }
	public String getComment_content() { return comment_content;}
	public String getComment_state()   { return comment_state;  }
	public String getUser_id()         { return user_id;        }
	public int getPost_no()            { return post_no;        }
	public int getNo()                 { return no;             }
	
	public void setComment_no(int comment_no)              { this.comment_no = comment_no;          }
	public void setComment_date(String comment_date)       { this.comment_date = comment_date;      }
	public void setComment_content(String comment_content) { this.comment_content = comment_content;}
	public void setComment_state(String comment_state)     { this.comment_state = comment_state;    }
	public void setUser_id(String user_id)                 { this.user_id = user_id;                }
	public void setPost_no(int post_no)                    { this.post_no = post_no;                }
	public void setNo(int no)                              { this.no = no;                          }
		
}