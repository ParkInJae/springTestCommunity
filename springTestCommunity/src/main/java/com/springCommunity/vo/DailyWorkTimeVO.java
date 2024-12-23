package com.springCommunity.vo;

public class DailyWorkTimeVO {
	public String work_time_no;
	public String user_id;
	public String check_in_time;
	public String check_out_time;
	public String created_at;
	public String updated_at;
	public String latitude;
	public String longitude;
	
	/*
	 @RequestBody를 사용하면, AJAX에서 보낸 JSON 데이터가 VO 객체로 자동 매핑됩니다.
	매핑 과정에서 JSON의 키 이름이 VO의 필드 이름과 동일하다면, Spring이 VO의 setter 메서드를 사용해 데이터를 채웁니다.
	 */
	
	// 기본 생성자 
	public DailyWorkTimeVO() {}

	public String getWork_time_no() 							{return work_time_no;}
	public String getUser_id() 									{return user_id;}
	public String getCheck_in_time() 							{return check_in_time;}
	public String getCheck_out_time() 							{return check_out_time;}
	public String getCreated_at() 								{return created_at;}
	public String getUpdated_at() 								{return updated_at;}
	public String getLatitude() 								{return latitude;}
	public String getLongitude() 								{return longitude;}
	
	public void setWork_time_no(String work_time_no) 			{this.work_time_no = work_time_no;	 	  }	
	public void setUser_id(String user_id) 						{this.user_id = user_id;			  	  }
	public void setCheck_in_time(String check_in_time) 			{this.check_in_time = check_in_time;  	  }
	public void setCheck_out_time(String check_out_time) 		{this.check_out_time = check_out_time;	  }
	public void setCreated_at(String created_at) 				{this.created_at = created_at;		  	  }
	public void setUpdated_at(String updated_at) 				{this.updated_at = updated_at;		 	  }
	public void setLatitude(String latitude) 					{this.latitude = latitude;			  	  }
	public void setLongitude(String longitude) 					{this.longitude = longitude;	 	 	  }

	@Override
	public String toString() {
		return "DailyWorkTimeVO [work_time_no=" + work_time_no + ", user_id=" + user_id + ", check_in_time="
				+ check_in_time + ", check_out_time=" + check_out_time + ", created_at=" + created_at + ", updated_at="
				+ updated_at + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

	
	
	
	
	
}
