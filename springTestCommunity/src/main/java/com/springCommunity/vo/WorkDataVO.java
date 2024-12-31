package com.springCommunity.vo;

public class WorkDataVO {


    private String date;   // 근무한 날짜 (예: 2024-12-20)
    private long workTime; // 해당 날짜의 근무 시간 (분 단위)

    // 생성자
    public WorkDataVO(String date, long workTime) {
        this.date = date;
        this.workTime = workTime;
    }

    // Getter와 Setter
    public String getDate() {      return date;   }

    public void setDate(String date) {      this.date = date;   }

    public long getWorkTime() {      return workTime;   }

    public void setWorkTime(long workTime) {      this.workTime = workTime;   }

    @Override
    public String toString() {
        return "WorkData{date='" + date + "', workTime=" + workTime + "}";
    }
}