package com.springCommunity.vo;

public class TaskVO {

	private String task_name;
	private int task_no;
	private String task_start_date;
	private String task_end_date;
	private String task_state;
	private String user_id;
	private String schedule_no;
	
	
	  
	public String getTask_name() {return task_name;}
	public int getTask_no() {return task_no;}
	public String getTask_start_date() {return task_start_date;}
	public String getTask_end_date() {return task_end_date;}
	public String getTask_state() {return task_state;}
	public String getUser_id() {return user_id;}
	public String getSchedule_no() {return schedule_no;}
	
	
	
	public void setTask_name(String task_name) {this.task_name = task_name;}
	public void setTask_no(int task_no) {this.task_no = task_no;}
	public void setTask_start_date(String task_start_date) {this.task_start_date = task_start_date;}
	public void setTask_end_date(String task_end_date) {this.task_end_date = task_end_date;}
	public void setTask_state(String task_state) {this.task_state = task_state;}
	public void setUser_id(String user_id) {this.user_id = user_id;	}
	public void setSchedule_no(String schedule_no) {this.schedule_no = schedule_no;}
	
	
	
	
}
