-------------------------------------------------------------------------------------- <br/>

-------------------------------------------------------------------------------------- <br/>

 1. home.jsp에서  jstl 사용할 때 <c:set > 사용하지 않고 <fnt: >사용한 이유 정리<br/>
 2. 거리계산 메소드 및 출근, 퇴근 시간 DB에 저장하는 비즈니스 로직 <br/>
 3. 일간 근무 시간, 주간 근무 시간 계산하는 로직 <br/>
 4. fullCalender 내부의 ajax 의미 <br/>
 5. pom.xml 정리 

📗 1. home.jsp에서  jstl 사용할 때 <c:set > 사용하지 않고 <fnt: >사용한 이유 정리<br/>

 home.jsp <br/>
```
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <br/>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %><br/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><br/>
<%@ include file="./include/header.jsp" %><br/>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/home.css" />
	<hr>
	<sec:authorize access="isAuthenticated()">
		<div style="font-size:18px; text-decoration: none; color:black; font-weight: bold;">
			<br>
			<!-- 근무시간 나타내기  -->
			 <div class="summaryContainer">
			    <div>근무 관련 요약 </div>
			    <div class="mainCalender">
			        <span id="currentWeekDisplay">1주차</span>
			    </div>
			</div>
		    <div class="diagramContainer">
		        <div class="weekDiagram">
		        <!-- 특정 값을 나중에 스크립트로 바꿔야함  -->
		            <div >주간 근무 시간 </div> 
		        </div>
		        <div class="addWeekDiagram">
		        <!-- 특정 값을 나중에 스크립트로 바꿔야함  -->
		            <div>추가 근무 시간 </div>
		        </div>
		    </div>
		    <!-- 주간 테이블 (연장 근무 시간을 포함한 총 근무 시간을 작성 ) -->
				<hr>
				<table class="workTable" border=1 style="tex-align">
					<thead>
				        <tr>
				            <th>날짜</th>
				            <th>근무 시간</th>
				        </tr>
				    </thead>
					<tbody>
					<c:forEach var="entry" items="${dailyWorkHours}"> <--! forEach 사용해서 반복문 돌림 --> 
					    <fmt:formatNumber value="${entry.value/ 60}" type="number" var="hours" /> <--!이때, fmt를 사용해서 기본 값을 실수가 아닌 정수로 나타내도록 표시 -->
					    <fmt:formatNumber value="${entry.value % 60}" type="number" var="minutes" />
					    <fmt:formatNumber value="${hours - (hours % 1)}" type="number" var="roundedHours" />
					 	<tr>
					 	<!-- key > 날짜, value > 근무 시간  -->
			                <td>${entry.key}</td>
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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springCommunity.dao.DailyWorkTimeDAO;
import com.springCommunity.vo.DailyWorkTimeVO;
import com.springCommunity.vo.WeeklyWorkTimeVO;

@Service
public class DailyWorkTimeServiceImpl implements DailyWorkTimeService {

	@Autowired
	private DailyWorkTimeDAO dailyWorkTimeDAO;
	//sqlSession >> 쿼리를 실행하는 객체
	//세션과 해당 쿼리의 값을 가져오기 위해 세션과 정적변수 선언 
	@Autowired
	private SqlSession sqlSession;
	public final String namespace = "com.springCommunity.mapper.DailyWorkTimeMapper.";
	// 회사의 위도와 경도는 Service에서 관리
	private final double COMPANY_LATITUDE  = 35.84218185982273 ; // 예: 전주 이젠 위도     위브 // 35.84218185982273   // 전주 이젠 35.8402587260868;
	private final double COMPANY_LONGITUDE = 127.15232222091124; // 예: 전주 이젠 경도   위브 // 127.15232222091124  // 전주 이젠 127.132499131298;
	
	private final double CHECK_IN_DISTANCE_KM = 5.0; // 반경 5km

	// 지구 반지름 
	private static final double EARTH_RADIUS = 6371.0;
	
	// 출근 결과를 알려주기 위한 변수들 
/*
 * 사용자의 현재 위치가 회사 위치 범위 안에 있는지 확인  아니면 OUTSIDE_RANGE 반환.
오늘 이미 출근한 기록이 있는지 확인 → 있으면 ALREADY_CHECKED_IN 반환.
위 두 조건에 해당하지 않을 경우, 출근 처리를 하고 SUCCESS 반환.

 */
	// enum 사용시 상태나 결과를 더 명확하고 직관적으로 표현할 수 있음 
	   public enum CheckInResult {
	        SUCCESS,           // 출근 성공
	        ALREADY_CHECKED_IN, // 이미 출근함
	        OUTSIDE_RANGE      // 범위 밖
	    }
	@Override
	public CheckInResult  checkIn(DailyWorkTimeVO dailyWorkTimeVO, String latitude, String longitude) {

		// 1. 유저의 위도 경도 
		double userLat = Double.parseDouble(latitude); // 유저의 위도
		double userLon = Double.parseDouble(longitude); // 유저의 경도
		
		// 2. 위치거리 계산 
		double distance = calculateDistance(COMPANY_LATITUDE, COMPANY_LONGITUDE, userLat, userLon);
		System.out.println("before if문 distance=======================" + distance);
		
 
		// 3. 위치 범위 체크 하여 범위 밖인지 확인 
        if (distance > CHECK_IN_DISTANCE_KM) {
        	System.out.println("정해진 범위 초과됨");
            return CheckInResult.OUTSIDE_RANGE;
        }
		
        // 4. 당일 출근 기록 확인
        int count = sqlSession.selectOne(namespace + "DailyCheckIn", dailyWorkTimeVO);
        if (count > 0) {
            System.out.println("이전에 출근버튼 누름");
            return CheckInResult.ALREADY_CHECKED_IN;
        }
        

        // 5. 출근 처리
        dailyWorkTimeDAO.checkIn(dailyWorkTimeVO);
        return CheckInResult.SUCCESS;
		
	}
```

<br/>
*️⃣ 퇴근 메소드   <br/>
<br/>

```
	@Override
	public boolean checkOut(DailyWorkTimeVO dailyWorkTimeVO, String latitude, String longitude) {
		double userLat = Double.parseDouble(latitude); // 유저의 위도
		double userLon = Double.parseDouble(longitude); // 유저의 경도

		double distance = calculateDistance(COMPANY_LATITUDE, COMPANY_LONGITUDE, userLat, userLon);
		System.out.println("beford if문 distance=======================" + distance);
		// 메소드 distance를 통해 얻은 경도가 상수의 반경보다 작을 경우 DAO에게 위도와 경도 값을 빼고 전달
		if (distance <= CHECK_IN_DISTANCE_KM) {
			System.out.println("distance=======================" + distance);
			dailyWorkTimeDAO.checkOut(dailyWorkTimeVO);
				 // 거리 범위 이내이면 , 퇴근 정보 저장 
				return true;
			}else {
				System.out.println("거리를 벗어났습니다.");
			}
		return false; // 거리 범위 밖이면 저장하지 않음
	}
```

<br/>
*️⃣ 위도 경도를 이용한 거리 계산 메소드  <br/>
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

import com.springCommunity.service.DailyWorkTimeService;
import com.springCommunity.service.DailyWorkTimeServiceImpl.CheckInResult;
import com.springCommunity.vo.DailyWorkTimeVO;

@Controller
public class DailyWorkTimeController {

    @Autowired
    private DailyWorkTimeService dailyWorkTimeService;

        @RequestMapping(value = "user/checkIn.do", method = RequestMethod.POST)
        public ResponseEntity<String> checkIn(@RequestBody DailyWorkTimeVO dailyWorkTimeVO) {
       	 // VO에서 위도와 경도 가져오기
        
            String latitude = dailyWorkTimeVO.getLatitude();
            String longitude = dailyWorkTimeVO.getLongitude(); 
        
        
        
            // 비즈니스 로직을 Service로 위임
    		// service인터페이스에서는 메소드의 호출 및 처리는 실행할 수 없으므로 , String 타입으로 전송 후 비즈니스 로직을 처리하는 serviceImpl에서 형변환 시켜서 사용하면 됨  
            CheckInResult result = dailyWorkTimeService.checkIn(dailyWorkTimeVO, latitude, longitude);
            
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
        
	  @RequestMapping(value="user/checkOut.do", method = RequestMethod.POST) public
	  ResponseEntity<String> checkOut(@RequestBody DailyWorkTimeVO dailyWorkTimeVO) {
		  // VO에서 위도와 경도 가져오기 
		  String latitude = dailyWorkTimeVO.getLatitude();
		  String longitude = dailyWorkTimeVO.getLongitude();
		  
		  boolean isWithinRange = dailyWorkTimeService.checkOut(dailyWorkTimeVO,latitude, longitude);
		  if(isWithinRange) {
			  return ResponseEntity.ok("수고하셨습니다. 퇴근하세요");
		  }else {
			  return ResponseEntity.status(HttpStatus.FORBIDDEN).body("시스템 오류로 퇴근 처리가 되지 않습니다.");
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

                // AJAX 요청
                $.ajax({
                    url: "user/checkIn.do",
                    method: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({
                        latitude: latitude,         // 위도
                        longitude: longitude,       // 경도
                        user_id: user_id  // 사용자 ID (VO의 필드와 동일해야함)
                    }),
                    success: function (data) {
                    	console.log(data);
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
                enableHighAccuracy: true, // 정확도 우선 모드
                timeout: 10000,           // 10초 이내 응답 없으면 에러 발생
                maximumAge: 0             // 항상 최신 위치 정보 수집
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
mapper의 startDate와 endDate를 

1) controller <br/>
2) serviceImpl(구현 클래스) <br/>
3) DAO <br/>
4) Mapper <br/>



<br/>

*️⃣ mapper
```
//Mapper

<select id="selectDetailedListByWeek" resultType="dailyWorkTimeVO">   <!-- my batis에서 설정해놓아서 오류 없음-->
    SELECT 
        check_in_time, 
        check_out_time
    FROM 
        daily_work_time
    WHERE 
        user_id = #{userId}
        AND DATE(check_in_time) BETWEEN #{startDate} AND #{endDate}
    ORDER BY 
        check_in_time ASC
</select>
```
➡️ startDate와 endDate는 VO(DailyWorkTimeVO)의 필드가 아니라, SQL 쿼리에서 사용되는 파라미터임 <br/>
따라서 VO에 startDate와 endDate 필드가 없어도 코드는 정상적으로 동작하며 VO 필드와는 무관함



*️⃣ dao

```

// dao
List<DailyWorkTimeVO> selectDetailedListByWeek(String userId, String startDate, String endDate);

```

➡️ userId, startDate, endDate를 매개변수로 사용하며, sql의 조건절에 매핑할 것 

<br/>

*️⃣ ServiceImpl(구현 클래스)

```

@Override
public Map<String, Object> getWeeklyWorkTimeDetails(String userId, String startDate) {
// 현재 주차는 월요일부터 일요일까지로 설정
/*
만약 월요일부터 금요일까지 원한다면
LocalDate endOfWeek = currentDate.with(TemppraAdjusters.nextOrSame(DayOfWeek.FRIDAY));로 설정하면 된다. 
*/
    // 주차 계산
    LocalDate currentDate = startDate != null ? LocalDate.parse(startDate) : LocalDate.now();
    LocalDate startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)); // 주의 시작일 (월요일)
    LocalDate endOfWeek = currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));       // 주의 종료일 (일요일)

    // DAO 호출
// DAO의 startDate와 endDate는 사실상 startOfWeek.toString(), endOfWeek.toString()으로 볼 수 있다. 
    List<DailyWorkTimeVO> workTimes = dailyWorkTimeDAO.selectDetailedListByWeek(userId, startOfWeek.toString(), endOfWeek.toString());

}

```

➡️ 매개변수 startDate를 받고, startDate를 계산하여  currentDate에 담는다. 

➡️ startOfWeek: currendDate를 계산하여 startOfWeek에 담는다.

➡️ endOfWeek :  currendDate를 계산하여 endOfWeek에 담는다.

startOfWeek과 endOfWeek는 DAO와 Mapper에서는 startDate, EndDate로 사용된다.







<br/>
📗4. fullCalender 내부의 ajax 의미 
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
 
