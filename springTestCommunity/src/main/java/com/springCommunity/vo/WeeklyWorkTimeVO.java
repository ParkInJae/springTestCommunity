package com.springCommunity.vo;

import java.util.ArrayList;
import java.util.List;

public class WeeklyWorkTimeVO {

    private List<String> checkInTimes = new ArrayList<>();  // 출근 시간 목록
    private List<String> checkOutTimes = new ArrayList<>(); // 퇴근 시간 목록
    private long totalWorkTime = 0;  // 총 근무 시간 (분 단위)

    // 일일 근무 시간을 추가
    public void addDailyWorkTime(long dailyMinutes, String checkInStr, String checkOutStr) {
        this.totalWorkTime += dailyMinutes;
        this.checkInTimes.add(checkInStr);
        this.checkOutTimes.add(checkOutStr);
    }

    public List<String> getCheckInTimes() {
        return checkInTimes;
    }

    public List<String> getCheckOutTimes() {
        return checkOutTimes;
    }

    public long getTotalWorkTime() {
        return totalWorkTime;
    }
}

