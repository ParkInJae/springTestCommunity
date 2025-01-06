package com.springCommunity.service;

import java.util.List;
import java.util.Map;

import com.springCommunity.vo.*;


public interface AdminService {
	
	public int insertUsers(List<UserInfoVO> users);
	public int insertUser(UserInfoVO userInfoVO);
	public List<UserInfoVO> selectAll(SearchVO searchVO);
	public int selectCount(SearchVO searchVO);
	public int updateUser(UserInfoVO vo);
	public UserInfoVO selectById(String user_id);
	public int insertEmail(EmailVO vo);
	public List<EmailVO> selectEmails();
	public int updateEmailState(EmailVO vo);
}
