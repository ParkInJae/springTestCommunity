package com.springCommunity.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysql.cj.protocol.Resultset;
import com.springCommunity.vo.*;


@Repository
public class AdminDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	private final String name_space = "com.springCommunity.mapper.adminMapper.";
	
	public int insertUsers(List<UserInfoVO> users) {
		return sqlSession.insert(name_space+"insertUsers",users);
	}
	
	public int insertUser(UserInfoVO userInfoVO) {
		return sqlSession.insert(name_space+"insertUser",userInfoVO);
	}
	
	public List<UserInfoVO> selectAll(SearchVO searchVO){
		return sqlSession.selectList(name_space+"selectAll",searchVO);
	}
	
	public int selectCount(SearchVO searchVO) {
		return sqlSession.selectOne(name_space+"selectCount",searchVO);
	}
	
	public int updateUser(UserInfoVO vo) {
		return sqlSession.update(name_space+"updateUser",vo);
	}
	
	public UserInfoVO selectById(String user_id) {
		return sqlSession.selectOne(name_space+"selectById",user_id);
	}
	
	public int insertEmail(EmailVO vo) {
		return sqlSession.insert(name_space+"insertEmail",vo);
	}
	
	public List<EmailVO> selectEmails() {
		return sqlSession.selectList(name_space+"selectEmails");
	}
	
	public int updateEmailState(EmailVO vo) {
		return sqlSession.update(name_space+"updateEmailState",vo);
	}
}
