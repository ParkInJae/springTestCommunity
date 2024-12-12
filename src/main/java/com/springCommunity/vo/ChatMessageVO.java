package com.springCommunity.vo;

public class ChatMessageVO {
	private int chat_no;
    private int chat_message_no;
    private String chat_message_content;
    private String user_id;
    
	public int getChat_no()         { return chat_no; }
	public int getChat_message_no() { return chat_message_no; }
	public String getChat_message_content() { return chat_message_content;}
	public String getUser_id()      { return user_id; }
	
	public void setChat_no(int chat_no)                 { this.chat_no = chat_no; }
	public void setChat_message_no(int chat_message_no) { this.chat_message_no = chat_message_no; }
	public void setChat_message_content(String chat_message_content) { this.chat_message_content = chat_message_content;}
	public void setUser_id(String user_id)              { this.user_id = user_id; }
}