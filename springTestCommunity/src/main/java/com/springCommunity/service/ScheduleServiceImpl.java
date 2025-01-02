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
    // 부서에 맞는 내용만 조회
    public List<ScheduleVO> selectSchedulesByDepartment(ScheduleVO scheduleVO) {
        return scheduleServiceDAO.selectSchedulesByDepartment(scheduleVO);
    }

    // 일정 추가
    public int insertSchedule(ScheduleVO scheduleVO) {
        return scheduleServiceDAO.insertSchedule(scheduleVO);
    }

    // 일정 수정
    public int updateSchedule(ScheduleVO scheduleVO) {
        return scheduleServiceDAO.updateSchedule(scheduleVO);
    }

    // 일정 삭제
    public void deleteSchedule(ScheduleVO scheduleVO) {

    }

}
