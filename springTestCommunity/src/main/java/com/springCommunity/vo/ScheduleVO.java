package com.springCommunity.vo;

public class ScheduleVO {

	private String schedule_no;					//업무일정번호
	private String schedule_name;				//업무명
	private String schedule_start_date;			//시작일
	private String schedule_end_date;			//종료일
	private String schedule_state;				//상태(0 대기, 1 진행중, 2 완료)
	private int schedule_id;					// 업무 일정 코드
	private int department_id;					// 부서 코드
	private int job_position_id;				// 직책 
	private String user_id;						// 담당자 
	
	
	
	
	
	public int getSchedule_id() {
		return schedule_id;
	}
	
	public String getSchedule_no() {
		return schedule_no;
	}
	public String getSchedule_name() {
		return schedule_name;
	}
	public String getSchedule_start_date() {
		return schedule_start_date;
	}
	public String getSchedule_end_date() {
		return schedule_end_date;
	}
	public String getSchedule_state() {
		return schedule_state;
	}
	public int getDepartment_id() {
		return department_id;
	}
	public int getJob_position_id() {
		return job_position_id;
	}
	
	public String getUser_id() {
		return user_id;
	}

	
	
	
	
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
	}
	
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	public void setJob_position_id(int job_position_id) {
		this.job_position_id = job_position_id;
	}
	public void setSchedule_no(String schedule_no) {
		this.schedule_no = schedule_no;
	}
	public void setSchedule_name(String schedule_name) {
		this.schedule_name = schedule_name;
	}
	public void setSchedule_start_date(String schedule_start_date) {
		this.schedule_start_date = schedule_start_date;
	}
	public void setSchedule_end_date(String schedule_end_date) {
		this.schedule_end_date = schedule_end_date;
	}
	public void setSchedule_state(String schedule_state) {
		this.schedule_state = schedule_state;
	}

}
