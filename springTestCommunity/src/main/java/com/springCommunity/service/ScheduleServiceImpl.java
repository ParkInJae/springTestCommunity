package com.springCommunity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springCommunity.dao.ScheduleServiceDAO;
import com.springCommunity.vo.ScheduleVO;

@Service
public class ScheduleServiceImpl implements ScheduleService{
	
	@Autowired
	private ScheduleServiceDAO scheduleServiceDAO ;

	public List<ScheduleVO> selectAllSchedule (){
		return scheduleServiceDAO .selectAllSchedule(); 
	}
	
}
