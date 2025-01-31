 1. home.jsp에서 chart.js 정리  <br/>
 	❌ 오류 내용 <br/>
	✔️ 해결 방법 <br/>

 3. 거리계산 메소드 및 출근, 퇴근 시간 DB에 저장하는 비즈니스 로직 <br/>
 	❌ 오류 내용 <br/>
	✔️ 해결 방법 <br/>
 
 4. 일간 근무 시간, 주간 근무 시간 계산하는 로직 <br/>
 	❌ 오류 내용 <br/>
	✔️ 해결 방법 <br/>
 
 5. fullCalender 내부의 ajax 의미 <br/>
 	❌ 오류 내용 <br/>
	✔️ 해결 방법 <br/>

 
 7. pom.xml 정리 


📗 1. home.jsp에서  jstl 사용할 때 <c:set > 사용하지 않고 <fnt: >사용한 이유 정리<br/>

 home.jsp <br/>
```
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="./include/header.jsp" %>
<%@ page isELIgnored="false" %>
<!-- Chart.js CDN  >> Chart.js라이브러리를 CDN으로 가져오겠다는 의미 -->
<!-- 이때, CDN을 사용하면 서버에서 직접 다운로드하지 않고, 직접 가져와서 사용할 수 있음  -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/home.css" />
	<hr>
	<sec:authorize access="isAuthenticated()">
		<div style="font-size:18px; text-decoration: none; color:black; font-weight: bold;">
			<br>
			<!-- 근무시간 나타내기  -->
			 <div class="summaryContainer">
			    <div>현재 주차 </div>
			    <div class="mainCalender">
		            <a href="?startDate=${startOfWeek.minusWeeks(1)}">&lt;</a>
			        <span id="currentWeekDisplay">${startOfWeek}~${endOfWeek}</span>
			        <a href="?startDate=${startOfWeek.plusWeeks(1)}"> &gt;</a>
			    </div>
			</div>
			<!-- 월요일부터 일요일까지 근무 시간 나타내기  -->
		    <div class="diagramContainer">
		    	<canvas class="chart" id="workChart"  style="height: 400px;"<%-- width="400" height="200" --%>></canvas>
		    </div>
		    

  
<%-- var workTimes = [
    <c:forEach var="workTime" items="${workTimeDetails}" varStatus="status">
        Math.round(${workTime.workDuration}),  // minutes 단위로 변환하지 않고 그대로 사용
        <c:if test="${!status.last}">,</c:if>
    </c:forEach>
]; --%>

 
<script>

//차트 데이터 구성
var chartData = {
	    labels: ['월', '화', '수', '목', '금', '토', '일'],
	    datasets: [{
	        label: '일일 근무 시간',
	        data: [
	            <c:forEach items="${workTimeDetails}" var="day" varStatus="loop">
	                ${day.workDuration}<c:if test="${!loop.last}">,</c:if>
	            </c:forEach>
	        ],
	        backgroundColor: 'rgba(54, 162, 235, 0.5)',
	        borderColor: 'rgba(54, 162, 235, 1)',
	        borderWidth: 1
	    }]
	};
//차트 옵션
var chartOptions = {
    maintainAspectRatio: false,
    scales: {
        y: {
            beginAtZero: true,
            min: 0,  // 세로축 최소값
            max: 540, // 세로축 최대값 (예시: 12시간)
            ticks: {
                stepSize: 60, // 1시간(60분) 단위로 눈금 표시
                callback: function(value) {
                    return Math.floor(value / 60) + "h " + (value % 60) + "m";
                }
            }
        }
    },
    plugins: {
        tooltip: {
            callbacks: {
                label: function(context) {
                    var label = context.dataset.label || '';
                    if (label) label += ': ';
                    if (context.parsed.y !== null) {
                        label += Math.floor(context.parsed.y / 60) + "시간 " + (context.parsed.y % 60) + "분";
                    }
                    return label;
                }
            }
        }
    }
};

// 차트 생성
var ctx = document.getElementById('workChart').getContext('2d');
new Chart(ctx, {
    type: 'bar',
    data: chartData,
    options: chartOptions
});
</script>	    
		    
		    
		    
		    
		    <!-- 주간 테이블 (연장 근무 시간을 포함한 총 근무 시간을 작성 ) -->
				<hr>
				<table class="workTable" border=1 style="tex-align">
					<thead>
				        <tr>
				            <th>날짜</th>
				            <th>출근 시간</th>
				            <th>퇴근 시간</th>
				            <th>근무 시간</th>
				            
				        </tr>
				    </thead>
					<tbody>
					 <c:forEach var="workTime" items="${workTimeDetails}" >
					    <fmt:formatNumber value="${workTime.workDuration / 60}" type="number" var="hours" /> 
					    <fmt:formatNumber value="${workTime.workDuration  % 60}" type="number" var="minutes" />
					    <fmt:formatNumber value="${hours - (hours % 1)}" type="number" var="roundedHours" /> 
		                <tr>
		                <!-- 점표기법, key가 고정일 때 해당 키의 값을 화면에 보여줌-->
		                   <td>${workTime.date}</td>    
						   <td>${workTime.checkInTime}</td>  
						   <td>${workTime.checkOutTime}</td>
						   <td>${roundedHours}시간${minutes}분</td>
		                </tr>
		            </c:forEach>
					</tbody>
		  	  </table>
		</div>
	</sec:authorize>
</body>
</html>

```


<br/>

📗 2. 거리계산 메소드 및 출근, 퇴근 시간 DB에 저장하는 비즈니스 로직 <br/>


➔ 위도 경도가 일치하는 경우 <br/>

▶ 출근버튼을 눌렀을 때    <br/>
![Image](https://github.com/user-attachments/assets/f14e16ef-e1ce-4f60-9587-63fd869164fc) 
 <br/>
▶ 출근 버튼을 누른 후 한 번 더 눌렀을 때 <br/>
![Image](https://github.com/user-attachments/assets/5e11c3f2-5e0f-40a0-99d8-f4d9318fda94) <br/>

▶ 퇴근버튼을 눌렀을 때    <br/>
![Image](https://github.com/user-attachments/assets/527d0831-d041-4a0d-9780-bb9fe33e4cc6) <bt/>


<br/><br/>

➔ 위도 경도가 일치하지 않는 경우 <br/><br/>

▶ 출근버튼을 눌렀을 때    <br/>
![Image](https://github.com/user-attachments/assets/1595dcd1-2d13-485e-85a3-7f650ffb4671) <br/>

▶ 퇴근버튼을 눌렀을 때    <br/>
![Image](https://github.com/user-attachments/assets/5abd5cc9-b663-4e87-84dc-8dcde9298dd6) <br/>


▶ 내부 소스코드에 오류가 있을 경우     <br/>
![image](https://github.com/user-attachments/assets/ea37aa9d-0c50-4a01-aa41-2a711e668456)

▶ 오류 내용 <br/>

```

java.lang.IllegalArgumentException: Mapped Statements collection does not contain value for com.springCommunity.mapper.DailyWorkTimeMapper.DailyCheckIn

```

✔️ 해결 방법 

DAO에 존재하는 namespace의 문자열을 잘못 작성해서 발생한 오류였다 .
해당 블로그를 참고해서 오류가 발생할 부분을 찾아보았다.
링크 :: https://blog.naver.com/javaking75/220315971085


```

// 오류 발생 코드 
// 상수 선언 
	public final String namespace = "com.springCommunity.mapper.DailyWorkTimeMapper.";

// 오류 수정 코드 
// 상수 선언 
	public final String namespace = "com.springCommunity.mapper.CheckInCheckOutMapper.";


```







<br/> 
아래는 비즈니스 로직 및 jsp의 ajax를 통해 나타낸 소스코드이다. <br/>
<br/>

*️⃣ serviceImpl (인터페이스 구현 객체 )<br/>
<br/>

``` 
package com.springCommunity.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springCommunity.dao.CheckInCheckOutDAO;
import com.springCommunity.vo.CheckInCheckOutVO;
import com.springCommunity.vo.DailyWorkTimeVO;

@Service
public class CheckInCheckOutServiceImpl implements CheckInCheckOutService{

	@Autowired
	private CheckInCheckOutDAO checkInCheckOutDAO;
	//sqlSession >> 쿼리를 실행하는 객체
	//세션과 해당 쿼리의 값을 가져오기 위해 세션과 정적변수 선언 
	
	// 회사의 위도와 경도는 Service에서 관리
	private final double COMPANY_LATITUDE =  35.8399616515785;// 예: 전주 이젠 위도      35.8402587260868; // 전주 위브 위도 35.8399616515785
	private final double COMPANY_LONGITUDE = 127.155041406919;// 예: 전주 이젠 경도     127.132499131298;  // 전주 위브 경도  127.155041406919
	private final double CHECK_IN_DISTANCE_KM = 5.0; // 반경 1km

	// 지구 반지름 
	private static final double EARTH_RADIUS = 6371.0;
	
	
	   // 출근 결과를 위한 열거형 추가
    public enum CheckInResult {
        SUCCESS,           // 출근 성공
        ALREADY_CHECKED_IN, // 이미 출근함
        OUTSIDE_RANGE      // 범위 밖
    }

    @Override
    public CheckInResult checkIn(CheckInCheckOutVO checkInCheckOutVO, String latitude, String longitude) {
        // 1. 위도, 경도 파싱
        double userLat = Double.parseDouble(latitude);
        double userLon = Double.parseDouble(longitude);

        // 2. 위치 거리 계산
        double distance = calculateDistance(COMPANY_LATITUDE, COMPANY_LONGITUDE, userLat, userLon);
        
        // 3. 위치 범위 벗어남 체크
        if (distance > CHECK_IN_DISTANCE_KM) {
            return CheckInResult.OUTSIDE_RANGE;
        }

        // 4. 당일 출근 기록 확인
        int count = checkInCheckOutDAO.DailyCheckIn(checkInCheckOutVO);
        
        if (count > 0) {
            return CheckInResult.ALREADY_CHECKED_IN;
        }

        // 5. 출근 처리
        checkInCheckOutDAO.checkIn(checkInCheckOutVO);
        return CheckInResult.SUCCESS;
    }
	
	@Override
	public boolean checkOut(CheckInCheckOutVO checkInCheckOutVO, String latitude, String longitude) {
		double userLat = Double.parseDouble(latitude); // 유저의 위도
		double userLon = Double.parseDouble(longitude); // 유저의 경도

		double distance = calculateDistance(COMPANY_LATITUDE, COMPANY_LONGITUDE, userLat, userLon);
		System.out.println("beford if문 distance=======================" + distance);
		
		// 메소드 distance를 통해 얻은 경도가 상수의 반경보다 작을 경우 DAO에게 위도와 경도 값을 빼고 전달
		if (distance <= CHECK_IN_DISTANCE_KM) {
			System.out.println("distance=======================" + distance);
			
			checkInCheckOutDAO.checkOut(checkInCheckOutVO);
				 // 거리 범위 이내이면 , 퇴근 정보 저장 
				return true;
			}else {
				System.out.println("거리를 벗어났습니다.");
			}
		return false; // 거리 범위 밖이면 저장하지 않음
	}
	 	
	
	

	// 거리 계산 메소드 
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // 위도와 경도를 라디안으로 변환
        double lat1Rad = Math.toRadians(lat1); 	// 회사 위도 
        double lon1Rad = Math.toRadians(lon1); 	// 회사 경도
        double lat2Rad = Math.toRadians(lat2);	// 사용자 위도 
        double lon2Rad = Math.toRadians(lon2);	// 사용자 경도

        // 위도 및 경도 차이 계산
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Haversine 공식을 사용하여 거리 계산
        double a = Math.pow(Math.sin(deltaLat / 2), 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 거리 계산 (단위: km)
        double distance = EARTH_RADIUS * c;
        return distance;
    }
   

    // 해당 유저의 전체 출퇴근 시간을 가져오는 메소드 
	@Override
	public List<CheckInCheckOutVO> selectList (String user_id) {

		return checkInCheckOutDAO.selectList(user_id);
	}

}

```



<br/>
*️⃣ API 대신 사용한 위도 경도를 이용한 거리 계산 메소드  <br/>
<br/>

```
// 거리 계산 메소드 
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // 위도와 경도를 라디안으로 변환
        double lat1Rad = Math.toRadians(lat1); 	// 회사 위도 
        double lon1Rad = Math.toRadians(lon1); 	// 회사 경도
        double lat2Rad = Math.toRadians(lat2);	// 사용자 위도 
        double lon2Rad = Math.toRadians(lon2);	// 사용자 경도

        // 위도 및 경도 차이 계산
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Haversine 공식을 사용하여 거리 계산
        double a = Math.pow(Math.sin(deltaLat / 2), 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 거리 계산 (단위: km)
        double distance = EARTH_RADIUS * c;
        return distance;
    }
   
```

<br/>
*️⃣ Controller화면   <br/>
<br/>

```
package com.springCommunity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springCommunity.service.CheckInCheckOutService;
import com.springCommunity.service.CheckInCheckOutServiceImpl.CheckInResult;
import com.springCommunity.vo.CheckInCheckOutVO;
import com.springCommunity.vo.DailyWorkTimeVO;

@Controller
public class CheckInCheckOutController {

    @Autowired
    CheckInCheckOutService checkInCheckOutService;

    

    @RequestMapping(value = "user/checkIn.do", method = RequestMethod.POST)
    public ResponseEntity<String> checkIn(@RequestBody CheckInCheckOutVO checkInCheckOutVO) {
        String latitude = checkInCheckOutVO.getLatitude();
        String longitude = checkInCheckOutVO.getLongitude();
        
        CheckInResult result = checkInCheckOutService.checkIn(checkInCheckOutVO, latitude, longitude); 
        
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("출근 성공.");
            case ALREADY_CHECKED_IN:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("이미 오늘 출근했습니다.");
            case OUTSIDE_RANGE:
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("회사 위치에서 벗어났습니다.");
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("시스템 오류가 발생했습니다.");
        }
    }
    
        @RequestMapping(value="user/checkOut.do", method = RequestMethod.POST)
        public ResponseEntity<String> checkOut(@RequestBody CheckInCheckOutVO checkInCheckOutVO) {
            String latitude = checkInCheckOutVO.getLatitude();
            String longitude = checkInCheckOutVO.getLongitude();
            
            boolean isWithinRange = checkInCheckOutService.checkOut(checkInCheckOutVO, latitude, longitude);
            if (isWithinRange) {
                return ResponseEntity.ok("수고하셨습니다. 퇴근하세요");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("시스템 오류로 퇴근 처리가 되지 않습니다.");
            }
        }
    
        
        
        
}

```

<br/>
*️⃣ jsp에 존재하는 , ajax 코드    <br/>
<br/>

```
<script>
function checkIn() {
    if ("geolocation" in navigator) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                const { latitude, longitude } = position.coords;
                $.ajax({
                    url: "user/checkIn.do",
                    method: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({
                        latitude: latitude,
                        longitude: longitude,
                        user_id: user_id  // 세션의 사용자 ID
                    }),
                    success: function (data) {
                        alert('출근 완료!');
                    },
                    error: function (xhr) {
                        if (xhr.status === 400) {
                            alert('이미 오늘 출근했습니다.');
                        } else if (xhr.status === 403) {
                            alert('회사 위치에서 벗어났습니다.');
                        } else {
                            alert('출근 처리 중 오류가 발생했습니다.');
                        }
                    }
                });
            },
            (error) => {
                alert(`위치 정보를 가져올 수 없습니다: ${error.message}`);
            },
            {
                enableHighAccuracy: true,
                timeout: 10000,
                maximumAge: 0
            }
        );
    } else {
        alert("브라우저가 위치 서비스를 지원하지 않습니다.");
    }
}
// 퇴근 함수 
function checkOut() {
    if ("geolocation" in navigator) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                const { latitude, longitude } = position.coords;

                // AJAX 요청
                $.ajax({
                    url: "user/checkOut.do",
                    method: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({
                        latitude: latitude,         // 위도
                        longitude: longitude,       // 경도
                        user_id: user_id  // 사용자 ID (VO의 필드와 동일해야함)
                    }),
                    success: function (data) {
                        alert('퇴근 완료! ');
                    },
                    error: function (xhr, status, error) {
                        alert('퇴근 처리가 되지 않았습니다.');
                       console.log(xhr.responseText);
                    }
                });
            },
            (error) => {
                alert(`위치 정보를 가져올 수 없습니다: ${error.message}`);
            },
            {
                enableHighAccuracy: true, // 정확도 우선 모드
                timeout: 10000,           // 10초 이내 응답 없으면 에러 발생
                maximumAge: 0             // 항상 최신 위치 정보 수집
            }
        );
    } else {
        alert("브라우저가 위치 서비스를 지원하지 않습니다.");
    }
}
</script>

```

<br/>
 📗 3. 일간 근무 시간, 주간 근무 시간 계산하는 로직 <br/>
 <br/>
 
*️⃣주간 근무 시간 계산할 때 어려웠던 점 

<br/>
serviceImple에서 로직을 설계할 때 논리적 오류로 인해서 잘못된 로직을 생성하여 , 주간 근무 시간을 계산하지 못했음 <br/>

*️⃣ mapper
```

//Mapper

<select id="selectDetailedListByWeek" parameterType="map" resultType="dailyWorkTimeVO">
    SELECT CHECK_IN_TIME, CHECK_OUT_TIME
    FROM DAILY_WORK_TIME
    WHERE USER_ID = #{user_id} AND DATE(check_in_time) BETWEEN #{startDate} AND #{endDate}
    ORDER BY CHECK_IN_TIME ASC
</select>


```

➡️ startDate와 endDate는 VO(DailyWorkTimeVO)의 필드가 아니라, SQL 쿼리에서 사용되는 파라미터임 <br/>
따라서 VO에 startDate와 endDate 필드가 없어도 코드는 정상적으로 동작하며 VO 필드와는 무관함



*️⃣ dao

```

public List<DailyWorkTimeVO> selectDetailedListByWeek(String user_id, String startDate, String endDate){
		
		Map<String, Object> details = new HashMap<>();
		details.put("user_id", user_id);
		details.put("startDate", startDate);
		details.put("endDate", endDate);
		
		return sqlSession.selectList(namespace + "selectDetailedListByWeek", details );

	}
```

➡️ userId, startDate, endDate를 매개변수로 받았으나 selectList에는 받은 매개 변수를 1개 밖에 넣을 수 없기 때문에 , map에 매개변수를 담아서, sqlSession에 map을 전달한다.

<br/>

*️⃣ ServiceImpl(구현 클래스)

```
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

//3 [핵심 로직]  jsp화면에서 chart.js를 사용하기 때문에  데이터와 레이블의 동기화가 필요함 (데이터베이스에 없는 날짜는 결과에 포함되지 않아 chart.js에 누락될 수 있기 때문에 레이블과 데이터의 동기화가 잘 되지 않을 수 있음) 
// 만약 chart.js를 이용하지 않고 근무 시간만 보여준다면 굳이 작성할 필요가 없음 
// 결국 데이터와 레이블의 동기화를 위해 필요한 로직임 
	        
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
}




```


➡️ 매개변수 startDate를 받고, startDate를 계산하여  currentDate에 담는다. 

➡️ startOfWeek: currendDate를 계산하여 startOfWeek에 담는다.

➡️ endOfWeek :  currendDate를 계산하여 endOfWeek에 담는다.

➡️ startOfWeek과 endOfWeek는 DAO와 Mapper에서는 startDate, EndDate로 사용된다.

❌ 오류 내용 <br/>
checkIn만 하고 다른 페이지로 이동하면 나타나는 오류 <br/>

org.springframework.web.util.NestedServletException: Request processing failed; nested exception is java.lang.NullPointerException: text

<br/>

*️⃣ 원인 ::  check_out_time이 null인 경우에 해당 오류가 발생함 
<br/>

✔️해결 방법 

```
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
```
코드를 이용해서 유효성 검사를 추가했음 







<br/>
📗4. fullCalender 내부의 ajax 의미 


일정 수정 및 삭제 선택 

존재하는 일정을 클릭 후 문자열을 입력하여 수정 및 삭제를 할 수 있다. <br/>

![image](https://github.com/user-attachments/assets/e0083729-113d-4ed0-bb09-8d966c25f49b) <br/>

잘못된 문자열을 입력한다면, 아래의 알림창이 뜸 <br/>

![image](https://github.com/user-attachments/assets/f1ee8c85-b041-472b-9835-55f1daadd7fa) <br/>


수정 전 일정 <br/>
![image](https://github.com/user-attachments/assets/bb7a1368-d40b-4a3c-8018-23b1cb382849) <br/>

수정 후 일정 <br/>
![image](https://github.com/user-attachments/assets/25483010-5828-41b6-9e48-ec18a029c8d3) <br/>


삭제 전 일정 <br/>
![image](https://github.com/user-attachments/assets/04b76312-d225-4410-b6ce-47a000c4829c) <br/>

삭제 후 일정 <br/>
![image](https://github.com/user-attachments/assets/a33a9226-52b7-41c8-8c1c-c86f3fb358de) <br/>



<br/>

```
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" %>  <br/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  <br/>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>  <br/>
<%@ include file="../include/header.jsp" %>  <br/>
<!DOCTYPE html><br/>
<html><br/>
<head><br/>
<!--fullCalendar사용할 때 스크립트 순서를 지키지 않으면, fullCalendar를 불러올 수 없고, console창에 오류가 발생함 , 따라서 순서를 지켜야함 --> <br/>
<script src='<%= request.getContextPath()%>/resources/js/jquery-3.7.1.js'></script> <br/>
<script src="https://cdn.jsdelivr.net/npm/moment@2.30.1/moment.min.js"></script><!-- moment.js 추가  --> <br/>
<script src='<%= request.getContextPath()%>/resources/js/index.global.js'></script> <br/>
<script src='<%= request.getContextPath()%>/resources/js/index.global.min.js'></script> <br/>

<script>
document.addEventListener('DOMContentLoaded', function() {
	 var manager = "${vo.user_id}";  // EL 태그 사용
	  console.log(manager);
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
            left: 'prevYear,prev,next,nextYear today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        initialDate: new Date(),
        navLinks: true, 	// 날짜 선택시 , day캘린더나 week 캘린더로 링크
        editable: true, 	// 수정 가능 여부 설정
        dayMaxEvents: true,     // 특정 이벤트의 개수가 일정 개수 이상일 경우에 +n개 형식으로 나타남 
        selectable: true,	// 달력 일자 드래그 설정 가능 
        
        slotMinTime: '00:00:00', // 시작 시간을 의미
        slotMaxTime: '24:00:00', // 종료 시간을 의미
        slotDuration: '00:30:00',// 시간 간격을 의미 
        
      eventTimeFormat: {
	    hour: '2-digit',   // 시간을 2자리 형식으로 표시 (예: 01시, 13시)
	    minute: '2-digit', // 분을 2자리 형식으로 표시 (예: 05분, 30분)
	    hour12: false      // 시간을 표시할 때 ,  24시간 형식으로 표시(01시, 13시)
	},

        events: function(info, successCallback, failureCallback) {
            $.ajax({
                url: '<c:url value="/api/schedule.do" />', // request.getContextPath()/api/schedule.do와 같은 경로(절대 경로) 
                method: 'GET',
                success: function(response) {
                    if (response.status === 'success' && response.data) {
                        const events = response.data.map(event => ({
                            id: event.schedule_no,  			// 일정 ID
                            title: event.schedule_name,  		// 일정 제목
                            start: event.schedule_start_date,		// 일정 시작 날짜
                            end: event.schedule_end_date,		// 일정 종료 날짜
                            allDay: false				// 하루 종일 이벤트인지 여부 (false로 설정)
                        }));
                        successCallback(events);			// 성공적으로 받은 이벤트 데이터를 FullCalendar로 전달
                    } else {
                        failureCallback(response.message || '일정을 불러오는데 실패했습니다.');
                    }
                },
                error: function(xhr) {
                    failureCallback('일정을 불러오는데 실패했습니다.');
                    handleError(xhr);
                }
            });
        },
/*
info 객체는 fullCalendar의 select 이벤트가 발생할 때 , 제공되는 객체
이 info 객체를 통해서 start 및 end를 설정할 수 있다 .
*/
        select: function(info) {
            if (parseInt('${vo.job_position_id}') < 3) {   // 특정 직책 미만은 작성할 수 없게 권한을 생성 
                alert('일정 생성 권한이 없습니다.');
                return;
            } 

            const title = prompt('일정 제목을 입력하세요:');
            if (!title) return;

            const startTime = prompt('시작 시간을 입력하세요 (HH:MM):', 
                info.start.getHours().toString().padStart(2, '0') + ':' + 
                info.start.getMinutes().toString().padStart(2, '0'));

            const endTime = prompt('종료 시간을 입력하세요 (HH:MM):', 
                info.end.getHours().toString().padStart(2, '0') + ':' + 
                info.end.getMinutes().toString().padStart(2, '0'));

            const event = {
                schedule_name: title,
                schedule_start_date: formatDateTime(info.start, startTime),
                schedule_end_date: formatDateTime(info.end, endTime),
                schedule_state: '0',
                department_id: parseInt('${vo.department_id}'),
                job_position_id: parseInt('${vo.job_position_id}'),
                user_id: ${vo.user_id},  // vo에서 user_id 값 사용
                schedule_no: null // 새로 생성될 일정에 대해 no 값은 null (후에 서버에서 처리)
            };

/*
	JSON.stringify() >> JSON문자열로 변환 
  	user_id: ${vo.user_id},  // vo에서 user_id 값 사용 >>   "user_id": "${vo.user_id}"의 형태를 이루고 있음
*/
            $.ajax({
                url: '<c:url value="/api/scheduleInsert.do" />', // 절대 경로 설정  						// ex)http://localhost:8080/controller/api/schedule.do의 값으로 구성(controller는 contextPath()라고 볼 수 있음)
                method: 'POST',
                data: JSON.stringify(event), // event객체를 JSON 문자열로 변환하여 서버에 전송 
                contentType: 'application/json',
                success: function(response) {
                    if (response.status === 'success') {
                        calendar.addEvent({
                            id: event.schedule_no,  // 서버에서 반환된 schedule_id
                            title: event.schedule_name,
                            start: event.schedule_start_date,
                            end: event.schedule_end_date,
                            allDay: false
                        });
                        alert('일정이 생성되었습니다.');
                    } else {
                        alert(response.message || '일정 생성에 실패했습니다.');
                    }
                },
                error: handleError
            });
        },
        eventClick: function(info) {
            if (parseInt('${vo.job_position_id}') < 3) {
                alert('일정 수정 권한이 없습니다.');
                return;
            }

            // 사용자에게 수정 또는 삭제 선택을 묻는 팝업
            const action = prompt('수정하려면 "edit"을, 삭제하려면 "delete"를 입력하세요:', 'edit'); 
		// 클릭했을 때 수정 및 삭제 선택
		
            if (action === 'edit') { // 수정할 때  
                // 수정하는 로직
                const newTitle = prompt('일정 제목을 수정하세요:', info.event.title);
                if (!newTitle) return;

                const startTime = prompt('시작 시간을 수정하세요 (HH:MM):', 
                    info.event.start.getHours().toString().padStart(2, '0') + ':' + 
                    info.event.start.getMinutes().toString().padStart(2, '0'));

                const endTime = prompt('종료 시간을 수정하세요 (HH:MM):', 
                    info.event.end.getHours().toString().padStart(2, '0') + ':' + 
                    info.event.end.getMinutes().toString().padStart(2, '0'));

                const updateData = {
                    schedule_no: parseInt(info.event.id),
                    schedule_name: newTitle,
                    schedule_start_date: formatDateTime(info.event.start, startTime),
                    schedule_end_date: formatDateTime(info.event.end, endTime),
                    department_id: parseInt('${vo.department_id}'),
                    job_position_id: parseInt('${vo.job_position_id}'),
                    user_id:  ${vo.user_id}
                };
                console.log("-----------------------------------");
                console.log(info.event);

                $.ajax({
                    url: '<c:url value="/api/scheduleUpdate.do" />', // controller에 연결 
                    method: 'PUT',
                    data: JSON.stringify(updateData),
                    contentType: 'application/json',
                    success: function(response) {
                        if (response.status === 'success') {
                            info.event.setProp('title', newTitle); // 제목 수정
                            info.event.setStart(formatDateTime(info.event.start, startTime)); // 시작 시간 수정
                            info.event.setEnd(formatDateTime(info.event.end, endTime)); // 종료 시간 수정
                            alert('일정이 수정되었습니다.');
                        } else {
                            alert(response.message || '일정 수정에 실패했습니다.');
                        }
                    },
                    error: handleError
                });
            } else if (action === 'delete') {
                // 삭제하는 로직
                if (confirm('이 일정을 삭제하시겠습니까?')) {
                    const deleteData = {
                        schedule_no:parseInt(info.event.id)
                    };

                    $.ajax({
                        url: '<c:url value="/api/scheduleDelete.do" />', // controller에 연결 
                        method: 'DELETE',
                        data: JSON.stringify(deleteData),
                        contentType: 'application/json',
                        success: function(response) {
                            if (response.status === 'success') {
                                info.event.remove();
                                alert('일정이 삭제되었습니다.');
                            } else {
                                alert(response.message || '일정 삭제에 실패했습니다.');
                            }
                        },
                        error: handleError
                    });
                }
            } else {
                alert('잘못된 입력입니다. "edit" 또는 "delete"를 입력해주세요.');
            }
        }
    });
    
    calendar.render();
});

function updateEvent(event, calendar) {
    const updateData = {
        schedule_id: parseInt(event.id),
        schedule_name: event.title,
        schedule_start_date: formatDateTime(event.start),
        schedule_end_date: formatDateTime(event.end),
        department_id: parseInt('${vo.department_id}'),
        job_position_id: parseInt('${vo.job_position_id}'),
        user_id:  ${vo.user_id}
    };
    
    $.ajax({
        url: '<c:url value="/api/scheduleUpdate.do" />',
        method: 'PUT',
        data: JSON.stringify(updateData),
        contentType: 'application/json',
        success: function(response) {
            if (response.status === 'success') {
                alert('일정이 수정되었습니다.');
            } else {
                event.revert();
                alert(response.message || '일정 수정에 실패했습니다.');
            }
        },
        error: function(xhr) {
            event.revert();
            handleError(xhr);
        }
    });
}

function formatDateTime(date, timeStr = null) {
    if (!date) return null;
    const d = new Date(date);
    if (timeStr) {
        const [hours, minutes] = timeStr.split(':').map(Number);
        d.setHours(hours, minutes, 0);
    }
    return moment(d).format('YYYY-MM-DD HH:mm:ss');  // moment.js를 사용하여 날짜 포맷팅
}

function handleError(xhr) {
    console.error('API Error:', xhr);
    if (xhr.status === 403) {
        alert('권한이 없습니다.');
    } else if (xhr.status === 400) {
        alert('잘못된 일정 데이터입니다: ' + (xhr.responseJSON?.message || xhr.responseText));
    } else {
        alert('서버 오류가 발생했습니다. 개발자 도구의 콘솔을 확인해주세요.');
    }
}
</script>
</head>
<body>
  <div id='calendar'></div>
</body>
</html>

```

<br/>

📗 5. pom.xml 정리 
<br/>

*️⃣기존의 코드 

```
	<properties>
		<java-version>11</java-version>
		<org.springframework-version>4.3.3.RELEASE</org.springframework-version>
		<spring.security.version>3.2.10.RELEASE</spring.security.version>
		<org.aspectj-version>1.6.10</org.aspectj-version>
		<org.slf4j-version>1.6.6</org.slf4j-version>
	</properties>
 
```

*️⃣ 수정된 코드 

```

	<properties>
		<java-version>11</java-version>
		<org.springframework-version>5.2.22.RELEASE</org.springframework-version>
		<spring.security.version>3.2.10.RELEASE</spring.security.version>
		<org.aspectj-version>1.6.10</org.aspectj-version>
		<org.slf4j-version>1.6.6</org.slf4j-version>
	</properties>

```
 <br/>
위의 코드는 버전을 <org.springframework-version>4.3.3.RELEASE</org.springframework-version> 에서<org.springframework-version>5.2.22.RELEASE</org.springframework-version>로 업그레이드 하였다. <br/>
<org.springframework-version> 의 버전을 업그레이드 하지 않는 경우에는 spring security가 적용이 되지 않아서, Run on Server를 작동시켜도 웹 페이지가 나오지 않으며, 자바 오류가 발생하기 때문에 spring security를 
사용하기 위해서라면 반드시 버전을 업그레이드 해야한다. 
 
