package com.springCommunity.vo;

public class WeeklyWorkTimeVO {

	// 페이징과 비슷한 맥락 
	private String work_time_no;
	private String work_time_week;
	private String work_time_regular;
	private String work_time_over;
	private String work_time_total;
	private String user_id;
	
	
	
	
	public String getWork_time_no() {return work_time_no;}
	public String getWork_time_week() {return work_time_week;}
	public String getWork_time_regular() {return work_time_regular;}
	public String getWork_time_over() {return work_time_over;}
	public String getWork_time_total() {return work_time_total;}
	public String getUser_id() {return user_id;}
	
	
	
	public void setWork_time_no(String work_time_no) {this.work_time_no = work_time_no;}
	public void setWork_time_week(String work_time_week) {this.work_time_week = work_time_week;}
	public void setWork_time_regular(String work_time_regular) {this.work_time_regular = work_time_regular;}
	public void setWork_time_over(String work_time_over) {this.work_time_over = work_time_over;}
	public void setWork_time_total(String work_time_total) {this.work_time_total = work_time_total;}
	public void setUser_id(String user_id) {this.user_id = user_id;}
}
