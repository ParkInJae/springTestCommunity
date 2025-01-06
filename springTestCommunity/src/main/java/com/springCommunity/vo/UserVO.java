package com.springCommunity.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserVO extends User{
	
	private String user_id;
	private String user_name;
	private String user_authority;
	private boolean user_enabled;
	private int department_id;
	private int job_position_id;
	private int user_state;
	
	public UserVO(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities,
			String authority,String user_name, int department_id, int job_position_id, int user_state) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		
		this.user_id = username;
		this.user_authority = authority;
		this.user_enabled = enabled;
		this.user_name = user_name;
		this.department_id = department_id;
		this.job_position_id = job_position_id;
		this.user_state = user_state;
	}

	public String getUser_id()        { return user_id;        }
	public String getUser_name()      { return user_name;      }
	public String getUser_authority() { return user_authority; }
	public boolean isUser_enabled()   { return user_enabled;   }
	public int getDepartment_id()     { return department_id;  }
	public int getJob_position_id()   { return job_position_id;}
	public int getUser_state()        { return user_state;     }
}