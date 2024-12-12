package com.springCommunity.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springCommunity.vo.ChatVO;
import com.springCommunity.vo.SearchVO;
import com.springCommunity.vo.UserInfoVO;

@Repository
public class UserDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	private final String name_space = "com.springCommunity.mapper.userMapper.";
	
	public int insertUsers(List<UserInfoVO> users) {
		return sqlSession.insert(name_space+"insertUsers",users);
	}
	
	public int insertUser(UserInfoVO userInfoVO) {
		return sqlSession.insert(name_space+"insert",userInfoVO);
	}
}