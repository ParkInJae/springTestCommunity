package com.springTestCommunity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springTestCommunity.dao.MypageDAO;
import com.springTestCommunity.vo.MypageVO;

@Service
public class MypageServiceImpl implements MypageService {

	@Autowired
	private MypageDAO mypageDAO;

	@Override
	public int userUpdate(MypageVO mypageVO) {
		return mypageDAO.userUpdate(mypageVO);
	}

	@Override
	public int userPwUpdate(MypageVO mypageVO) {
		return mypageDAO.userPwUpdate(mypageVO);
	}

}
