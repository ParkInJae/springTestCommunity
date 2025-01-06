package com.springCommunity.vo;

import com.springCommunity.util.PagingUtil;

public class SearchVO extends PagingUtil {
	private String search_type;
	private String search_value;
	private String user_id;
	private int chat_no;
	private int department_id = 0;     
    private int job_position_id = 0;   
    private int user_state = 0;   
    private String first_date;     
    private String second_date;    
    private int post_type = 0;    
    private int post_no = 0;    
	
	
	public String getSearch_type()  { return search_type;    }
	public String getSearch_value() { return search_value;   }
	public String getUser_id()      { return user_id;        }
	public int getChat_no()         { return chat_no;        }
	public int getDepartment_id()   { return department_id;  }
	public int getJob_position_id() { return job_position_id;}
	public int getUser_state()      { return user_state;     }
	public String getFirst_date()   { return first_date;     }
	public String getSecond_date()  { return second_date;    }
	public int getPost_type()       { return post_type;      }
	public int getPost_no()         { return post_no;        }
	
	public void setSearch_type(String search_type)      { this.search_type = search_type;        }
	public void setSearch_value(String search_value)    { this.search_value = search_value;      }
	public void setUser_id(String user_id)              { this.user_id = user_id;                }
	public void setChat_no(int chat_no)                 { this.chat_no = chat_no;                }
	public void setDepartment_id(int department_id)     { this.department_id = department_id;    }
	public void setJob_position_id(int job_position_id) { this.job_position_id = job_position_id;}
	public void setUser_state(int user_state)           { this.user_state = user_state;          }
	public void setFirst_date(String first_date)        { this.first_date = first_date;          }
	public void setSecond_date(String second_date)      { this.second_date = second_date;        }
	public void setPost_type(int post_type)             { this.post_type = post_type;            }
	public void setPost_no(int post_no)                 { this.post_no = post_no;                }
}