package com.springCommunity.vo;

public class VacationVO {

	private String user_id;
	private String user_password;
	private String user_join_date;
	private String mail_key;
	private String mail_auth;
	
	private String years;  // 일자계산 위한 근속 년수
	
	private String department_name; //부서명
	private int job_position_id; //직책코드
	private String job_position_name; //직책명
	private int department_id; //부서코드
	
	private String task_name;  //업무명
	private String task_start_date; //업무 시작일
	private String task_end_date;  //업무마감일
	private String task_state;  //상태 (0대기/1진행중)
	
	private int vacation_no;	//근태신청번호
	private String vacation_type;  //근태구분(0선택, 1 연차, 2 대휴, 3 공가, 4 병결, 5 휴가, 6 기타, 7 연장근무, 8 휴일근무, 9 휴일연장근무)
	private String vacation_day_type;	//근태반/종일구분(0 종일, 1오전, 2오후)
	private String vacation_start_date;	//근태신청시작일
	private String vacation_end_date;	//근태신청종료일
	private String vacation_reason;	//신청사유
	private String vacation_state;	//신청상태(0: 대기,1: 1차승인,2: 2차승인,3:거절)
	private String vacation_repuse;		//거절사유
	private String vacation_first_approver;		//1차승인자
	private String vacation_second_approver;	//2차승인자
	private String vacation_application_date;	//근태 신청일
	
	public String getDepartment_name() {
		return department_name;
	}
	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
	public int getJob_position_id() {
		return job_position_id;
	}
	public void setJob_position_id(int job_position_id) {
		this.job_position_id = job_position_id;
	}
	public String getJob_position_name() {
		return job_position_name;
	}
	public void setJob_position_name(String job_position_name) {
		this.job_position_name = job_position_name;
	}
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	public String getVacation_application_date() {
		return vacation_application_date;
	}
	public void setVacation_application_date(String vacation_application_date) {
		this.vacation_application_date = vacation_application_date;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_join_date() {
		return user_join_date;
	}
	public void setUser_join_date(String user_join_date) {
		this.user_join_date = user_join_date;
	}
	public String getMail_key() {
		return mail_key;
	}
	public void setMail_key(String mail_key) {
		this.mail_key = mail_key;
	}
	public String getMail_auth() {
		return mail_auth;
	}
	public void setMail_auth(String mail_auth) {
		this.mail_auth = mail_auth;
	}
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	public String getTask_name() {
		return task_name;
	}
	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	public String getTask_start_date() {
		return task_start_date;
	}
	public void setTask_start_date(String task_start_date) {
		this.task_start_date = task_start_date;
	}
	public String getTask_end_date() {
		return task_end_date;
	}
	public void setTask_end_date(String task_end_date) {
		this.task_end_date = task_end_date;
	}
	public String getTask_state() {
		return task_state;
	}
	public void setTask_state(String task_state) {
		this.task_state = task_state;
	}
	public int getVacation_no() {
		return vacation_no;
	}
	public void setVacation_no(int vacation_no) {
		this.vacation_no = vacation_no;
	}
	public String getVacation_type() {
		return vacation_type;
	}
	public void setVacation_type(String vacation_type) {
		this.vacation_type = vacation_type;
	}
	public String getVacation_day_type() {
		return vacation_day_type;
	}
	public void setVacation_day_type(String vacation_day_type) {
		this.vacation_day_type = vacation_day_type;
	}
	public String getVacation_start_date() {
		return vacation_start_date;
	}
	public void setVacation_start_date(String vacation_start_date) {
		this.vacation_start_date = vacation_start_date;
	}
	public String getVacation_end_date() {
		return vacation_end_date;
	}
	public void setVacation_end_date(String vacation_end_date) {
		this.vacation_end_date = vacation_end_date;
	}
	public String getVacation_reason() {
		return vacation_reason;
	}
	public void setVacation_reason(String vacation_reason) {
		this.vacation_reason = vacation_reason;
	}
	public String getVacation_state() {
		return vacation_state;
	}
	public void setVacation_state(String vacation_state) {
		this.vacation_state = vacation_state;
	}
	public String getVacation_repuse() {
		return vacation_repuse;
	}
	public void setVacation_repuse(String vacation_repuse) {
		this.vacation_repuse = vacation_repuse;
	}
	public String getVacation_first_approver() {
		return vacation_first_approver;
	}
	public void setVacation_first_approver(String vacation_first_approver) {
		this.vacation_first_approver = vacation_first_approver;
	}
	public String getVacation_second_approver() {
		return vacation_second_approver;
	}
	public void setVacation_second_approver(String vacation_second_approver) {
		this.vacation_second_approver = vacation_second_approver;
	}
}
