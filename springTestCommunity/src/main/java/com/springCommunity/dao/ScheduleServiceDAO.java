package com.springCommunity.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springCommunity.vo.ScheduleVO;

@Repository
public class ScheduleServiceDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	String namespace="com.springCommunity.mapper.ScheduleServiceMapper.";
	public List<ScheduleVO> selectAllSchedule() {
        return sqlSession.selectList(namespace + "selectAllSchedule");
    }

    // 부서에 맞는 내용만 조회
    public List<ScheduleVO> selectSchedulesByDepartment(ScheduleVO scheduleVO) {
        return sqlSession.selectList(namespace + "selectSchedulesByDepartment", scheduleVO);
    }

    // 일정 추가
    public int insertSchedule(ScheduleVO scheduleVO) {
        return sqlSession.insert(namespace + "insertSchedule", scheduleVO);
    }

    // 일정 수정
    public int updateSchedule(ScheduleVO scheduleVO) {
        return sqlSession.update(namespace + "updateSchedule", scheduleVO);
    }

    // 일정 삭제
    public void deleteSchedule(ScheduleVO scheduleVO) {
        sqlSession.delete(namespace + "deleteSchedule", scheduleVO);
    }

}



