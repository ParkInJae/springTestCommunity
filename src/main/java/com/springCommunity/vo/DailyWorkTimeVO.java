package com.springCommunity.vo;

import com.springCommunity.controller.DailyWorkTime;

public class DailyWorkTimeVO {

	private String work_time_no;    // 근무시간번호 
	private String user_id; 		// 아이디
	private String work_date; 		// 매일
	private String check_in_time; 	// 출근시간
	private String check_out_time; 	// 퇴근시간
	private String created_at;		// 기록생성시간
	private String updated_at;		// 기록수정시간
	
	// 수정 해봐야함
	private double totalWorkHours;	// 총 근무 시간 
	private double overtimeHours;	// 연장 근무 시간 
	private double nightWorkHours;	// 야간 근무 시간 
	
	//기본 생성자 기입 
	public DailyWorkTimeVO() {}
	
	// 일일히 다 기입, 
	public DailyWorkTimeVO(String work_time_no,String user_id,String work_date,String check_in_time,String check_out_time
			,String created_at,String updated_at) {
		this.setWork_time_no(work_time_no);
		this.setUser_id(user_id);
		this.setWork_date(work_date);
		this.setCheck_in_time(check_in_time);
		this.setCheck_out_time(check_out_time);
		this.setCreated_at(created_at);
		this.setUpdated_at(updated_at);
		
	}
	
	
	
	
	public String getWork_time_no() {return work_time_no;}	
	public String getUser_id() {return user_id;}
	public String getWork_date() {return work_date;}
	public String getCheck_in_time() {return check_in_time;}
	public String getCheck_out_time() {return check_out_time;}
	public String getCreated_at() {return created_at;}
	public String getUpdated_at() {return updated_at;}
	
	
	
	public void setWork_time_no(String work_time_no) {this.work_time_no = work_time_no;}
	public void setUser_id(String user_id) {this.user_id = user_id;}
	public void setWork_date(String work_date) {this.work_date = work_date;}
	public void setCheck_in_time(String check_in_time) {this.check_in_time = check_in_time;}
	public void setCheck_out_time(String check_out_time) {this.check_out_time = check_out_time;}
	public void setCreated_at(String created_at) {this.created_at = created_at;}
	public void setUpdated_at(String updated_at) {this.updated_at = updated_at;} 
	
	
	
	
}
