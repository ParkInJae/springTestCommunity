package com.springCommunity.vo;

public class EmailVO {
	private int email_no;
	private String email_to;
	private String email_title;
	private String email_content;
	private int email_state;
	private String email_create_date;
	private String email_send_date;
	
	public int getEmail_no()             { return email_no;         }
	public String getEmail_to()          { return email_to;         }
	public String getEmail_title()       { return email_title;      }
	public String getEmail_content()     { return email_content;    }
	public int getEmail_state()          { return email_state;      }
	public String getEmail_create_date() { return email_create_date;}
	public String getEmail_send_date()   { return email_send_date;  }
	
	public void setEmail_no(int email_no)                      { this.email_no = email_no;                  }
	public void setEmail_to(String email_to)                   { this.email_to = email_to;                  }
	public void setEmail_title(String email_title)             { this.email_title = email_title;            }
	public void setEmail_content(String email_content)         { this.email_content = email_content;        }
	public void setEmail_state(int email_state)                { this.email_state = email_state;            }
	public void setEmail_create_date(String email_create_date) { this.email_create_date = email_create_date;}
	public void setEmail_send_date(String email_send_date)     { this.email_send_date = email_send_date;    }
}