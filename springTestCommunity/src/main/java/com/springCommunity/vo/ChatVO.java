package com.springCommunity.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.springCommunity.vo.ChatMessageVO;

public class ChatVO extends ChatMessageVO{
	private int chat_no;
	private String chat_name;
	private String chat_date;
	private String chat_state;
	private int chat_group;
	private String user_id;
	private String chat_users_name;
	private int chat_users_state;
	private String user_name;
	private int user_count;
	private int chat_users_top;
	
	public int getChat_no()       { return chat_no;    }
	public String getChat_name()  { return chat_name;  }
	public String getChat_date()  { return chat_date;  }
	public String getChat_state() { return chat_state; }
	public int getChat_group()    { return chat_group; }
	public String getUser_id()    { return user_id;    }
	public int getUser_count()    { return user_count; }
	public String getChat_users_name() { return chat_users_name;  }
	public int getChat_users_state()   { return chat_users_state; }
	public String getUser_name()       { return user_name;        }
	public int getChat_users_top()     { return chat_users_top;   }
	
	public void setChat_no(int chat_no)          { this.chat_no = chat_no;       }
	public void setChat_name(String chat_name)   { this.chat_name = chat_name;   }
	public void setChat_date(String chat_date)   { this.chat_date = chat_date;   }
	public void setChat_state(String chat_state) { this.chat_state = chat_state; }
	public void setChat_group(int chat_group)    { this.chat_group = chat_group; }
	public void setUser_id(String user_id)       { this.user_id = user_id;       }
	public void setUser_count(int user_count)    { this.user_count = user_count; }
	public void setChat_users_name(String chat_users_name) { this.chat_users_name = chat_users_name;   }
	public void setChat_users_state(int chat_users_state)  { this.chat_users_state = chat_users_state; }
	public void setUser_name(String user_name)             { this.user_name = user_name;               }
	public void setChat_users_top(int chat_users_top)      { this.chat_users_top = chat_users_top;     }
}