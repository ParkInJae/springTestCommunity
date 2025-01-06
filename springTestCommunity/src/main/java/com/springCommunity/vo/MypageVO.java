package com.springCommunity.vo;


public class MypageVO { 

	private String user_id;
	private String user_password;
	private String user_name;
	private String user_email;
	private String user_phone;
	private String user_join_date;
	private String user_account;
	private int bank_id;
	private String user_address;
	private String mail_key;
	private String mail_auth;
	
	private String department_name; //부서명
	private int job_position_id; //직책코드
	private String job_position_name; //직책명
	private int department_id; //부서코드
	
	private String years;  // 일자계산 위한 근속 년수
	
	private int request_no;  // 청구번호
	private String request_date;  //청구일
	private String request_type;  //청구타입
	private String request_approve_money;  //청구승인 금액
	private String request_approve_state1;  //1차 승인상태(0대기/1승인/3거절)
	private String request_approve_state2;  //2차 승인상태(0대기/1승인/3거절)
	private String request_approve_date;  //승인일시
	private String request_approve_id;  //승인자
	private String request_repuse;  //거절사유
	
	private String benefit_sub;		//경조대상(0 선택/1 본인/2 부/3 모/4 형제/5 자매/6 조부/7 조모)
	private String benefit_type;		//경조구분(0 선택/1 결혼/2 회갑/3 칠순/4 출산/5 사망)
	private String benefit_date;		//경조일자
	private String benefit_tour;		//근속기간
	private String benefit_money;		//경조금액
	private String benefit_content;		//경조요청사유
	
	private String medical_name;		//상병명
	private String medical_type;		//진료구분(0선택/1입원/2외래)
	private String medical_center_type;		//진료기관(0선택/1병원/2약국)
	private String medical_center_name;		//진료기관명
	private String medical_start_date;		//진료시작일
	private String medical_end_date;		//진료종료일
	private String medical_money;		//수납금액
	private String medical_attach_type;		//진료기관(0선택/1Y/2N)
	private String medical_content;  //의료비요청사유
	
	private String file_name;  //원래파일명
	private String attach_name;  //변경되는파일명
	
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
	public String getRequest_approve_money() {
		return request_approve_money;
	}
	public void setRequest_approve_money(String request_approve_money) {
		this.request_approve_money = request_approve_money;
	}
	public String getRequest_approve_state1() {
		return request_approve_state1;
	}
	public void setRequest_approve_state1(String request_approve_state1) {
		this.request_approve_state1 = request_approve_state1;
	}
	public String getRequest_approve_state2() {
		return request_approve_state2;
	}
	public void setRequest_approve_state2(String request_approve_state2) {
		this.request_approve_state2 = request_approve_state2;
	}
	public String getBenefit_sub() {
		return benefit_sub;
	}
	public void setBenefit_sub(String benefit_sub) {
		this.benefit_sub = benefit_sub;
	}
	public String getBenefit_type() {
		return benefit_type;
	}
	public void setBenefit_type(String benefit_type) {
		this.benefit_type = benefit_type;
	}
	public String getBenefit_date() {
		return benefit_date;
	}
	public void setBenefit_date(String benefit_date) {
		this.benefit_date = benefit_date;
	}
	public String getBenefit_tour() {
		return benefit_tour;
	}
	public void setBenefit_tour(String benefit_tour) {
		this.benefit_tour = benefit_tour;
	}
	public String getBenefit_money() {
		return benefit_money;
	}
	public void setBenefit_money(String benefit_money) {
		this.benefit_money = benefit_money;
	}
	public String getBenefit_content() {
		return benefit_content;
	}
	public void setBenefit_content(String benefit_content) {
		this.benefit_content = benefit_content;
	}
	public String getMedical_name() {
		return medical_name;
	}
	public void setMedical_name(String medical_name) {
		this.medical_name = medical_name;
	}
	public String getMedical_type() {
		return medical_type;
	}
	public void setMedical_type(String medical_type) {
		this.medical_type = medical_type;
	}
	public String getMedical_center_type() {
		return medical_center_type;
	}
	public void setMedical_center_type(String medical_center_type) {
		this.medical_center_type = medical_center_type;
	}
	public String getMedical_center_name() {
		return medical_center_name;
	}
	public void setMedical_center_name(String medical_center_name) {
		this.medical_center_name = medical_center_name;
	}
	public String getMedical_start_date() {
		return medical_start_date;
	}
	public void setMedical_start_date(String medical_start_date) {
		this.medical_start_date = medical_start_date;
	}
	public String getMedical_end_date() {
		return medical_end_date;
	}
	public void setMedical_end_date(String medical_end_date) {
		this.medical_end_date = medical_end_date;
	}
	public String getMedical_money() {
		return medical_money;
	}
	public void setMedical_money(String medical_money) {
		this.medical_money = medical_money;
	}
	public String getMedical_attach_type() {
		return medical_attach_type;
	}
	public void setMedical_attach_type(String medical_attach_type) {
		this.medical_attach_type = medical_attach_type;
	}
	public String getMedical_content() {
		return medical_content;
	}
	public void setMedical_content(String medical_content) {
		this.medical_content = medical_content;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getAttach_name() {
		return attach_name;
	}
	public void setAttach_name(String attach_name) {
		this.attach_name = attach_name;
	}
	public int getRequest_no() {
		return request_no;
	}
	public void setRequest_no(int request_no) {
		this.request_no = request_no;
	}
	public String getRequest_date() {
		return request_date;
	}
	public void setRequest_date(String request_date) {
		this.request_date = request_date;
	}
	public String getRequest_type() {
		return request_type;
	}
	public void setRequest_type(String request_type) {
		this.request_type = request_type;
	}
	public String getRequest_approve_date() {
		return request_approve_date;
	}
	public void setRequest_approve_date(String request_approve_date) {
		this.request_approve_date = request_approve_date;
	}
	public String getRequest_approve_id() {
		return request_approve_id;
	}
	public void setRequest_approve_id(String request_approve_id) {
		this.request_approve_id = request_approve_id;
	}
	public String getRequest_repuse() {
		return request_repuse;
	}
	public void setRequest_repuse(String request_repuse) {
		this.request_repuse = request_repuse;
	}
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
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
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getUser_join_date() {
		return user_join_date;
	}
	public void setUser_join_date(String user_join_date) {
		this.user_join_date = user_join_date;
	}
	public String getUser_account() {
		return user_account;
	}
	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}
	public int getBank_id() {
		return bank_id;
	}
	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}
	public String getUser_address() {
		return user_address;
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	public String getDepartment_name() {
		return department_name;
	}
	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
		
}
