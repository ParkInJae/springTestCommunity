package com.springCommunity.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class ChatVO{
	private int chat_no;
	private String chat_name;
	private String chat_date;
	private String chat_state;
	private int chat_group;
	private int chat_top;
	private String user_id;
	
	public int getChat_no()       { return chat_no;    }
	public String getChat_name()  { return chat_name;  }
	public String getChat_date()  { return chat_date;  }
	public String getChat_state() { return chat_state; }
	public int getChat_group()    { return chat_group; }
	public int getChat_top()      { return chat_top;   }
	public String getUser_id()    { return user_id;    }
	
	public void setChat_no(int chat_no)          { this.chat_no = chat_no;       }
	public void setChat_name(String chat_name)   { this.chat_name = chat_name;   }
	public void setChat_date(String chat_date)   { this.chat_date = chat_date;   }
	public void setChat_state(String chat_state) { this.chat_state = chat_state; }
	public void setChat_group(int chat_group)    { this.chat_group = chat_group; }
	public void setChat_top(int chat_top)        { this.chat_top = chat_top;     }
	public void setUser_id(String user_id)       { this.user_id = user_id;       }
}