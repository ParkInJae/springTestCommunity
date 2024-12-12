package com.springCommunity.service;

import java.util.List;

import com.springCommunity.vo.*;

import com.springCommunity.vo.UserInfoVO;

public interface UserService {
	
	public int insertUsers(List<UserInfoVO> users);
	public int insertUser(UserInfoVO userInfoVO);
}
