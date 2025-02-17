package com.springCommunity.service;

import java.util.List;

import com.springCommunity.vo.ScheduleVO;

public interface ScheduleService {
	


    // 부서에 맞는 내용만 조회
    public List<ScheduleVO> selectSchedulesByDepartment(int department_id);      

    // 일정 추가;
    public int insertSchedule(ScheduleVO scheduleVO);
    
    // 일정 수정
    public int updateSchedule(ScheduleVO scheduleVO);
    
    // 일정 삭제
    public int deleteSchedule(ScheduleVO scheduleVO) ;
}
