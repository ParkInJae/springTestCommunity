package com.springCommunity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springCommunity.dao.AdminDAO;
import com.springCommunity.vo.EmailVO;
import com.springCommunity.vo.SearchVO;
import com.springCommunity.vo.UserInfoVO;

@Service //업무로직을 담당하는 구현 객체를 스프링이 생성하여 관리
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	public AdminDAO adminDAO;
	
	@Override
	public int insertUsers(List<UserInfoVO> users) {
		return adminDAO.insertUsers(users);
	}
	
	@Override
	public int insertUser(UserInfoVO userInfoVO) {
		return adminDAO.insertUser(userInfoVO);
	}

	@Override
	public List<UserInfoVO> selectAll(SearchVO searchVO) {
		return adminDAO.selectAll(searchVO);
	}

	@Override
	public int selectCount(SearchVO searchVO) {
		return adminDAO.selectCount(searchVO);
	}

	@Override
	public int updateUser(UserInfoVO vo) {
		return adminDAO.updateUser(vo);
	}

	@Override
	public UserInfoVO selectById(String user_id) {
		return adminDAO.selectById(user_id);
	}

	@Override
	public int insertEmail(EmailVO vo) {
		return adminDAO.insertEmail(vo);
	}

	@Override
	public List<EmailVO> selectEmails() {
		return adminDAO.selectEmails();
	}

	@Override
	public int updateEmailState(EmailVO vo) {
		return adminDAO.updateEmailState(vo);
	}
}