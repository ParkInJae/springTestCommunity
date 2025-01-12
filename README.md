-------------------------------------------------------------------------------------- <br/>
 http://54.180.26.201:8080/springCommunity/ (현재 출퇴근 위도 경도를 지정해놓았기 때문에 출퇴근 버튼을 눌러도 출퇴근 인식이 되지 않음)<br/>
 id : hong <br/>
 pw : 1234 <br/>
 id : manager <br/>
 pw : 1234 <br/>
 id : admin <br/>
 pw : 1234 <br/>
-------------------------------------------------------------------------------------- <br/>

// 1. home.jsp에서  jstl 사용할 때 <c:set > 사용하지 않고 <fnt: >사용한 이유 정리<br/>
// 2. 거리계산 메소드 및 출근, 퇴근 시간 DB에 저장하는 비즈니스 로직 <br/>
// 3. 일간 근무 시간, 주간 근무 시간 계산하는 로직 <br/>
// 4. fullCalender 내부의 ajax 의미 <br/>

//📗 1. home.jsp에서  jstl 사용할 때 <c:set > 사용하지 않고 <fnt: >사용한 이유 정리<br/>
// home.jsp <br/>
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



<br/>   
//📗 2. 거리계산 메소드 및 출근, 퇴근 시간 DB에 저장하는 비즈니스 로직 <br/>
// -------------------------------------------------------<br/>

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
	private final double COMPANY_LATITUDE = 35.8402587260868; // 예: 전주 이젠 위도
	private final double COMPANY_LONGITUDE = 127.132499131298; // 예: 전주 이젠 경도
	private final double CHECK_IN_DISTANCE_KM = 5.0; // 반경 1km

	// 지구 반지름 
	private static final double EARTH_RADIUS = 6371.0;
	
	
	@Override
	public boolean checkIn(DailyWorkTimeVO dailyWorkTimeVO, String latitude, String longitude) {
		
		int count = sqlSession.selectOne(namespace + "DailyCheckIn",dailyWorkTimeVO);
		double userLat = Double.parseDouble(latitude); // 유저의 위도
		double userLon = Double.parseDouble(longitude); // 유저의 경도

		double distance = calculateDistance(COMPANY_LATITUDE, COMPANY_LONGITUDE, userLat, userLon);
		System.out.println("beford if문 distance=======================" + distance);
		// 메소드 distance를 통해 얻은 경도가 상수의 반경보다 작을 경우 DAO에게 위도와 경도 값을 빼고 전달
		if (distance <= CHECK_IN_DISTANCE_KM) {
			System.out.println("distance=======================" + distance);
			if(count == 0 ) {
				dailyWorkTimeDAO.checkIn(dailyWorkTimeVO); // 거리 범위 내면 데이터 저장
				return true;
			}else {
				System.out.println("이미 존재하는 출근 기록입니다.");
			}
		}
		return false; // 거리 범위 밖이면 저장하지 않음
	}
	
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
	public List<DailyWorkTimeVO> selectList (String user_id) {

		return dailyWorkTimeDAO.selectList(user_id);
	}


 // 출근 시간 계산 로직 
 //📗 3. 일간 근무 시간, 주간 근무 시간 계산하는 로직 
 // -------------------------------------------------------
	
	@Override
	public Map<String, Object> calculateWorkTime(String user_id) {
	    // 1. 해당 유저의 전체 출퇴근 시간을 가져옴
	    List<DailyWorkTimeVO> list = dailyWorkTimeDAO.selectList(user_id);

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    Map<String, Long> dailyWorkHours = new TreeMap<>(Comparator.reverseOrder()); // 내림차순 정렬
	    Map<String, Long> weeklyWorkHours = new TreeMap<>(Comparator.reverseOrder()); // 주간 근무 시간

	    // 1) 날짜별 근무시간 계산
	    list.forEach(workTime -> {
	        String checkInStr = workTime.getCheck_in_time(); // 출근 시간
	        String checkOutStr = workTime.getCheck_out_time(); // 퇴근 시간

	        if (checkInStr == null || checkOutStr == null) {
	            System.out.println("출근 또는 퇴근 시간이 null입니다.");
	            return; // null 데이터는 건너뜀
	        }

	        LocalDateTime checkIn = LocalDateTime.parse(checkInStr, formatter);
	        LocalDateTime checkOut = LocalDateTime.parse(checkOutStr, formatter);
	        LocalDate localDate = checkIn.toLocalDate();
 
	        // 하루 근무 시간 계산
	        long dailyMinutes = Duration.between(checkIn, checkOut).toMinutes();
	        dailyWorkHours.merge(localDate.toString(), dailyMinutes, Long::sum); // TreeMap이므로 자동으로 정렬
	    });

	    // 2) 주차별 근무시간 합산
	    dailyWorkHours.keySet().forEach(dateStr -> {
	        LocalDate date = LocalDate.parse(dateStr);
	        int weekOfYear = date.get(WeekFields.ISO.weekOfYear());
	        String weekKey = date.getYear() + "년 " + weekOfYear + "주차";

	        // 해당 날짜의 근무시간 가져오기
	        long dailyMinutes = dailyWorkHours.getOrDefault(dateStr, 0L);

	        // 주간 데이터에 합산
	        weeklyWorkHours.merge(weekKey, dailyMinutes, Long::sum);
	    });

	    // 3) 결과 출력 (디버깅용)
	    System.out.println("=== 일간 근무시간 (정렬됨) ===");
	    dailyWorkHours.forEach((date, minutes) -> {
	        System.out.printf("Date: %s, Work Time: %d minutes\n", date, minutes);
	    });

	    System.out.println("=== 주간 근무시간 ===");
	    weeklyWorkHours.forEach((week, minutes) -> {
	        System.out.printf("Week: %s, Work Time: %d minutes\n", week, minutes);
	    });

	    // 결과 반환
	    Map<String, Object> result = new HashMap<>();
	    result.put("dailyWorkHours", dailyWorkHours); // 일간 근무 시간 (정렬된 TreeMap)
	    result.put("weeklyWorkHours", weeklyWorkHours); // 주간 근무 시간

	    return result;
	}

	// 유틸 메서드: 하루 근무시간 계산
    private long calculateDailyHours(String checkInTime, String checkOutTime) {
    	// 만약 출근 or 퇴근 시간 둘 중 하나라도 null이면 시간 계산을 할 수 없음 
    	if (checkInTime == null || checkOutTime == null ) {
    		System.out.println("둘 중 하나가 null이라서 시간 계산을 할 수 없음 ");
    		return 0;
    	}
    	
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime  start = LocalDateTime .parse(checkInTime, formatter);
        LocalDateTime  end = LocalDateTime .parse(checkOutTime, formatter);
        
        return Duration.between(start, end).toMinutes(); // 시간 단위로 반환 >> 버림처리 
 
        // 따라서 시간 ,분을 이용해서 반환하게끔 설정
        // 초 단위로는 버림 
        //시간과 , 분은 버리지 않고 가져오게끔 설정
    }
}

// 📗4. fullCalender 내부의 ajax 의미 <br/>
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

