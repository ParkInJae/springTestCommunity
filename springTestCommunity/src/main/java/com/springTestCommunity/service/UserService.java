package com.springTestCommunity.service;

import java.util.List;

import com.springTestCommunity.vo.*;

public interface UserService {
	
	public int insertUsers(List<UserInfoVO> users);
	public int insertUser(UserInfoVO userInfoVO);
}
