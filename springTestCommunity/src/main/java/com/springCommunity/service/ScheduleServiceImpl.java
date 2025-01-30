package com.springCommunity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springCommunity.dao.ScheduleDAO;
import com.springCommunity.vo.ScheduleVO;

@Service
public class ScheduleServiceImpl implements ScheduleService{
	
	@Autowired
	private ScheduleDAO scheduleDAO ;
	
    // 부서에 맞는 내용만 조회
    public List<ScheduleVO> selectSchedulesByDepartment(int department_id) {
        return scheduleDAO.selectSchedulesByDepartment(department_id);
    }

    // 일정 추가
    public int insertSchedule(ScheduleVO scheduleVO) {
        return scheduleDAO.insertSchedule(scheduleVO);
    }

    // 일정 수정
    public int updateSchedule(ScheduleVO scheduleVO) {
        return scheduleDAO.updateSchedule(scheduleVO);
    }

    // 일정 삭제
    public int deleteSchedule(ScheduleVO scheduleVO) {
    	return scheduleDAO.deleteSchedule(scheduleVO);
    }

}
