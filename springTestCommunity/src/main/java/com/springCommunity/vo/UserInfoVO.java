package com.springCommunity.vo;

public class UserInfoVO{
	
	private String user_id;
	private String user_password;
	private String user_name;
	private String user_email;
	private String user_phone;
	private String user_join_date;
	private String user_account;
	private String user_authority;
	private boolean user_enabled;
	private int department_id; 
	private int job_position_id;
	private int bank_id;
	private String user_address;
	private String department_name;
	private String job_position_name;
	
	public String getUser_id()           { return user_id;           }
	public String getUser_password()     { return user_password;     }
	public String getUser_name()         { return user_name;         }
	public String getUser_email()        { return user_email;        }
	public String getUser_phone()        { return user_phone;        }
	public String getUser_join_date()    { return user_join_date;    }
	public String getUser_account()      { return user_account;      }
	public String getUser_authority()    { return user_authority;    }
	public boolean isUser_enabled()      { return user_enabled;      }
	public int getDepartment_id()        { return department_id;     }
	public int getJob_position_id()      { return job_position_id;   }
	public int getBank_id()              { return bank_id;           }
	public String getUser_address()      { return user_address;      }
	public String getDepartment_name()   { return department_name;   }
	public String getJob_position_name() { return job_position_name; }
	
	public void setUser_id(String user_id)                     { this.user_id = user_id;                     }
	public void setUser_password(String user_password)         { this.user_password = user_password;         }
	public void setUser_name(String user_name)                 { this.user_name = user_name;                 }
	public void setUser_email(String user_email)               { this.user_email = user_email;               }
	public void setUser_phone(String user_phone)               { this.user_phone = user_phone;               }
	public void setUser_join_date(String user_join_date)       { this.user_join_date = user_join_date;       }
	public void setUser_account(String user_account)           { this.user_account = user_account;           }
	public void setUser_authority(String user_authority)       { this.user_authority = user_authority;       }
	public void setUser_enabled(boolean user_enabled)          { this.user_enabled = user_enabled;           }
	public void setDepartment_id(int department_id)            { this.department_id = department_id;         }
	public void setJob_position_id(int job_position_id)        { this.job_position_id = job_position_id;     }
	public void setBank_id(int bank_id)                        { this.bank_id = bank_id;                     }
	public void setUser_address(String user_address)           { this.user_address = user_address;           }
	public void setDepartment_name(String department_name)     { this.department_name = department_name;     }
	public void setJob_position_name(String job_position_name) { this.job_position_name = job_position_name; }
	
}