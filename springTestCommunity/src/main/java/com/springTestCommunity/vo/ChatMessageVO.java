package com.springTestCommunity.vo;

public class ChatMessageVO {
    private int chat_message_no;
    private String chat_message_content;
    private String chat_message_date;
    private String chat_message_time;  //채팅방 메시지 시간안내용으로 추가
	private String chat_message_read_state; //읽음여부(안읽음 0, 읽음 1)
    private int chat_no;
    private String user_id;
    private String user_name;
    private int unread_count;
    
	public int getChat_message_no()            { return chat_message_no;        }
	public String getChat_message_content()    { return chat_message_content;   }
	public String getChat_message_time()       { return chat_message_time;      }
	public String getChat_message_date()       { return chat_message_date;      }
	public String getChat_message_read_state() { return chat_message_read_state;}
	public int getChat_no()                    { return chat_no;                }
	public String getUser_id()                 { return user_id;                }
	public String getUser_name()               { return user_name;              }
	public int getUnread_count()               { return unread_count;           }
	
	public void setChat_message_no(int chat_message_no)              { this.chat_message_no = chat_message_no; }
	public void setChat_message_time(String chat_message_time)       { this.chat_message_time = chat_message_time;}
	public void setChat_message_date(String chat_message_date)       { this.chat_message_date = chat_message_date;}
	public void setChat_message_read_state(String chat_message_read_state) { this.chat_message_read_state = chat_message_read_state;}
	public void setChat_message_content(String chat_message_content) { this.chat_message_content = chat_message_content;}
	public void setChat_no(int chat_no)           { this.chat_no = chat_no;          }
	public void setUser_id(String user_id)        { this.user_id = user_id;          }
	public void setUser_name(String user_name)    { this.user_name = user_name;      }
	public void setUnread_count(int unread_count) { this.unread_count = unread_count;}
}