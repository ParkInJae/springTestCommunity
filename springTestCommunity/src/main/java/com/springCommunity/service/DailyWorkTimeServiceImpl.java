package com.springCommunity.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springCommunity.dao.DailyWorkTimeDAO;
import com.springCommunity.vo.DailyWorkTimeVO;

@Service
public class DailyWorkTimeServiceImpl implements DailyWorkTimeService {

	@Autowired
	DailyWorkTimeDAO dailyWorkTimeDAO ;
	
    // 해당 유저의 전체 출퇴근 시간을 가져오는 메소드 
	@Override
	public List<DailyWorkTimeVO> selectList (String user_id) {

		return dailyWorkTimeDAO.selectList(user_id);
	}


// 전체 근무 시간 및 일간 근무 시간 코드 
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	 @Override
	    public Map<String, Object> getWeeklyWorkTimeDetails(String user_id, String startDate) {
		 
	        // 현재 날짜 기준으로 주차 계산
	        LocalDate currentDate = startDate != null ? LocalDate.parse(startDate) : LocalDate.now();
	        
	        // 1. 주의 시작일과 종료일을 계산 (월요일~ 일요일)
	        LocalDate startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)); // 주의 시작일 (월요일)
	        LocalDate endOfWeek = startOfWeek.plusDays(6); // 월요일부터 6일 추가  // 주의 종료일 (일요일)

	        // 2. DAO를 통해 해당 주차의 근무 시간 목록을 조회
	        List<DailyWorkTimeVO> dbWorkTimes = dailyWorkTimeDAO.selectDetailedListByWeek(user_id, startOfWeek.toString(), endOfWeek.toString());
	
	        
	        // 디버깅 
	        if(dbWorkTimes == null || dbWorkTimes.isEmpty()) {
	        	System.out.println("workTimes의 값이 비어있음");
	        }else {
	        	System.out.println("0번째 인덱스의 출근 시간 깂 >>>>>>>>>>>>>>>>>>>>" + dbWorkTimes.get(0).getCheck_in_time());
	        	System.out.println("0번째 인덱스의 출근 시간 깂 >>>>>>>>>>>>>>>>>>>>"  + dbWorkTimes.get(0).getCheck_out_time());
	        }
	        //3 [핵심 로직]  jsp화면에서 chart.js를 사용하기 때문에  데이터와 레이블의 동기화가 필요함 (데이터베이스에 없는 날짜는 결과에 포함되지 않아 chart.js에 누락될 수 있기 때문에 레이블과 데이터의 동기화가 잘 되지 않을 수 있음) 
	        // 만약 chart.js를 이용하지 않고 근무 시간만 보여준다면 굳이 작성할 필요가 없음 
	        // 결국 데이터와 레이블의 동기화를 위해 필요한 로직임 
	        
	        // 7일간의 데이터 구조 생성 (월~일)
	        List<Map<String, Object>> workTimeDetails = new ArrayList<>();
	        for (int i = 0; i < 7; i++) {
	            LocalDate currentDay = startOfWeek.plusDays(i);
	            Map<String, Object> dayData = createDayData(currentDay, dbWorkTimes); // dayData에 createDayData를 메소드를 활용한 값을 집어 넣음 
	            workTimeDetails.add(dayData);
	        }

	        // 결과 맵 구성
	        Map<String, Object> result = new HashMap<>();
	        result.put("workTimeDetails", workTimeDetails);
	        result.put("startOfWeek", startOfWeek);
	        result.put("endOfWeek", endOfWeek);
	        return result;
	    }
	        
	        
	        
	 private Map<String, Object> createDayData(LocalDate date, List<DailyWorkTimeVO> dbWorkTimes) {
		 
 /*
  .stream 	 ::  dbWorkTimes는 DB에서 조회한 유저의 근무 시간, 이때 , stream()을 사용하여 , 반복을 돌림
  
  .filter()  :: .stream()에서 각 요소에 대한 조건을 검사함
  
  w 		 :: .stream()을통한 요소들  즉 , dbWorkTimes.stream()의 타입이 DailyWorkTimeVO 이고, 
  				 w는 스트림의 요소이기 때문에 , w의 타입은 DailyWorkTimeVO 이다.
  				 
.findFirst   ::  위의 .filter의 조건에 만족하는 첫 번재 요소가 없는 경우 Optional.empty()를 반환함
 
.orElse(null):: Optional에서 값이 없을 때 , 명시적으로 null을 반환하게 하는 방법 

 *Optional  >> null을 직접 다루지 않고 안전하게 값 유무를 관리하기 위한 도구이며, 자바8이상부터 사용가능함 
  				  
  */
		 //4. DB에서 해당 날짜 데이터 찾기 
		    Map<String, Object> dayData = new HashMap<>();
		    DailyWorkTimeVO workTime = dbWorkTimes.stream() // dbWorkTimes는 유저의 근무 시간, .stream()을 돌려서 근무시간을 반복
		    		.filter(w -> date.equals(LocalDate.parse(w.getCheck_in_time().split(" ")[0])))   // 날짜만 추출하고 , 추출한 날짜를 LocalDate로 변환 
		    		.findFirst() 
		    		.orElse(null);
		    
		    
/*
 출근 시간이 존재하나, 퇴근시간이 없으면 NullPointException 오류 발생  따라서 출근 시간, 퇴근 시간 둘 다 null인 경우도 생각해서 유효성 검사를 해야함
  */
		    
		    // 출근 시간 ,퇴근 시간, 근무 시간 유효성 검사  
		    // 5. 근무 시간에 대한 데이터가 존재시 , 데이터를 map에 키와 값으로 저장
		    if (workTime != null) {
		        // 출근 시간이 null인 경우 처리
		    	//null이 아닌 경우, fomatter 형식으로 변환 null인 경우 null
		        LocalDateTime checkIn = (workTime.getCheck_in_time() != null) ? LocalDateTime.parse(workTime.getCheck_in_time(), formatter) : null;

		        // 퇴근 시간이 null인 경우 처리
		        LocalDateTime checkOut = (workTime.getCheck_out_time() != null) ? LocalDateTime.parse(workTime.getCheck_out_time(), formatter) : null;

		        // 근무 시간 계산 (출근 또는 퇴근 시간이 null이면 0분)
		        long minutes = (checkIn != null && checkOut != null) ? Duration.between(checkIn, checkOut).toMinutes() : 0L;
		        dayData.put("date", date.toString());
		        dayData.put("checkInTime", workTime.getCheck_in_time());
		        dayData.put("checkOutTime", workTime.getCheck_out_time());
		        dayData.put("workDuration", minutes);
		    } else {
		    	// 데이터가 존재하지 않는다면, 근무일자는 해당 일자 .  출근 시간, 퇴근 시간은 null , 근무 시간을 0을 map에 대입  
		        dayData.put("date", date.toString());
		        dayData.put("checkInTime", "null");
		        dayData.put("checkOutTime", "null");
		        dayData.put("workDuration", 0L);
		    }
		    return dayData;
	    }
	 

/*		    
유효성 검사 전  코드 
		    if (workTime != null) {
		        LocalDateTime checkIn = LocalDateTime.parse(workTime.getCheck_in_time(), formatter);
		        LocalDateTime checkOut = LocalDateTime.parse(workTime.getCheck_out_time(), formatter);
		        // Duration.between(a,b).toMinutes() :: 두 시간 객체 사이에서 지속 시간을 나타내며, 분으로 표현함
		        long minutes = Duration.between(checkIn, checkOut).toMinutes();  
*/
	 
	 
	 
}


