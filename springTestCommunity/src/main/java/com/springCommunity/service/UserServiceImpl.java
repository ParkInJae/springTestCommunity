package com.springCommunity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springCommunity.dao.UserDAO;
import com.springCommunity.vo.UserInfoVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public int insertUser(UserInfoVO userInfoVO) {
		return userDAO.insertUser(userInfoVO);
	}
}
