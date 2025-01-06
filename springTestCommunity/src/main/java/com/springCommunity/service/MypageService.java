package com.springCommunity.service;

import java.util.List;

import com.springCommunity.vo.MypageVO;

public interface MypageService { 

public int userUpdate (MypageVO mypageVO);
	
	public int userPwUpdate(MypageVO mypageVO);
	
	public MypageVO selectOne(String user_id);
	
	public int insertBenefit (MypageVO mypageVO); 
	
	public int insertAttach (MypageVO mypageVO);
	
	public int insertMedical (MypageVO mypageVO); 
	
	public MypageVO selectOneRequest (int request_no);

	public List<MypageVO> selectAll (MypageVO mypageVO);
	
	public List<MypageVO> selectAllBene (MypageVO mypageVO);
	
	public List<MypageVO> selectAllMedi (MypageVO mypageVO);
	
	public int updateRequest(MypageVO mypageVO);
}
