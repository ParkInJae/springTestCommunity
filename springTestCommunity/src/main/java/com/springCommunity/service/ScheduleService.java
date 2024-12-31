package com.springCommunity.service;

import java.util.List;

import com.springCommunity.vo.ScheduleVO;

public interface ScheduleService {
	// 스케쥴 전체 조회 
	public List<ScheduleVO> selectAllSchedule ();	

	
}
