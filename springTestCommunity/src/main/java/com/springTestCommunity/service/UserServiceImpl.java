package com.springTestCommunity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springTestCommunity.dao.UserDAO;
import com.springTestCommunity.vo.UserInfoVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public int insertUsers(List<UserInfoVO> users) {
		return userDAO.insertUsers(users);
	}
	@Override
	public int insertUser(UserInfoVO userInfoVO) {
		return userDAO.insertUser(userInfoVO);
	}
}
