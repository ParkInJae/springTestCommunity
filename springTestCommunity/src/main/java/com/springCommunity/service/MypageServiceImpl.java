package com.springCommunity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springCommunity.dao.MypageDAO;
import com.springCommunity.vo.MypageVO;

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

	@Override
	public MypageVO selectOne(String user_id) {
		return mypageDAO.selectOne(user_id);
	}

	@Override
	public int insertBenefit(MypageVO mypageVO) {
		return mypageDAO.insertBenefit(mypageVO);
	}

	@Override
	public int insertAttach(MypageVO mypageVO) {
		return mypageDAO.insertAttach(mypageVO);
	}

	@Override
	public int insertMedical(MypageVO mypageVO) {
		return mypageDAO.insertMedical(mypageVO);
	}

	@Override
	public MypageVO selectOneRequest(int request_no) {
		return mypageDAO.selectOneRequest(request_no);
	}

	@Override
	public List<MypageVO> selectAll(MypageVO mypageVO) {
		return mypageDAO.selectAll(mypageVO);
	}

	@Override
	public List<MypageVO> selectAllBene(MypageVO mypageVO) {
		return mypageDAO.selectAllBene(mypageVO);
	}

	@Override
	public List<MypageVO> selectAllMedi(MypageVO mypageVO) {
		return mypageDAO.selectAllMedi(mypageVO);
	}

	@Override
	public int updateRequest(MypageVO mypageVO) {
		return mypageDAO.updateRequest(mypageVO);
	}

}
