package com.springTestCommunity.service;

import com.springTestCommunity.vo.MypageVO;

public interface MypageService {

	public int userUpdate(MypageVO mypageVO);
	
	public int userPwUpdate(MypageVO mypageVO);
}
