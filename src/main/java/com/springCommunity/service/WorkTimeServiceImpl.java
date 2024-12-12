package com.springCommunity.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.springCommunity.dao.WorkTimeDAO;
import com.springCommunity.vo.DailyWorkTimeVO;

public class WorkTimeServiceImpl implements WorkTimeService{

	@Autowired
	WorkTimeDAO workTimeDAO;
	
	@Override
	public int insertOne(DailyWorkTimeVO dailyWorkTimeVO) {
		// 
		return workTimeDAO.insertOne(dailyWorkTimeVO);
	}
	
	
	
	
}
