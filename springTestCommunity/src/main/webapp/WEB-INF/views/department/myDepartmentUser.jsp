<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<script src='<%= request.getContextPath()%>/resources/js/jquery-3.7.1.js'></script>
<!-- CDN > 해당 링크에서 그대로 가져옴 -->
<script src="https://cdn.jsdelivr.net/npm/moment@2.30.1/moment.min.js"></script><!-- moment.js 추가  -->
<script src='<%= request.getContextPath()%>/resources/js/index.global.js'></script>
<script src='<%= request.getContextPath()%>/resources/js/index.global.min.js'></script>

<script>
document.addEventListener('DOMContentLoaded', function() {
	 var manager = "${vo.user_id}";  // 현재 로그인한 사용자 id 
	  console.log(manager); // 디버깅용 
    var calendarEl = document.getElementById('calendar'); 
 	// FullCalendar 초기화
    var calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
            left: 'prevYear,prev,next,nextYear today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        // 기본 설정 
        initialDate: new Date(),
        navLinks: true,
        // 직급 5이상만 일정 수정 가능 
        editable: parseInt('${vo.job_position_id}') >=5 ,
        dayMaxEvents: true,
        selectable: true,
        
        slotMinTime: '00:00:00',
        slotMaxTime: '24:00:00',
        slotDuration: '00:30:00',
        
        eventTimeFormat: {
            hour: '2-digit',
            minute: '2-digit',
            hour12: false
        },
		// 일정 데이터 로드 
       events: function(info, successCallback, failureCallback) {
		    const department_id = parseInt('${vo.department_id}'); // 현재 사용자의 부서 ID
		    $.ajax({
		        url: '<c:url value="/api/schedule.do" />',
		        method: 'GET',
		        data: { department_id: department_id }, // 부서 ID를 파라미터로 전달
		        success: function(response) {
		            if (response.status === 'success' && response.data) {
		                const events = response.data.map(event => ({
		                    id: event.schedule_no,
		                    title: event.schedule_name,
		                    start: event.schedule_start_date,
		                    end: event.schedule_end_date,
		                    allDay: false
		                }));
		                successCallback(events);
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
        select: function(info) {
            if (parseInt('${vo.job_position_id}') < 5) {
                alert('일정 생성 권한이 없습니다.');
                return;
            }

            const title = prompt('일정 제목을 입력하세요:');
            if (!title) return;

            // 시작 시간 입력 (기본값: 선택한 시간)
            const startTime = prompt('시작 시간을 입력하세요 (HH:MM):', 
                info.start.getHours().toString().padStart(2, '0') + ':' + 
                info.start.getMinutes().toString().padStart(2, '0'));

            // 종료 시간 입력 (기본값: 시작 시간 + 1시간)
            const defaultEndTime = new Date(info.start);
            defaultEndTime.setHours(defaultEndTime.getHours() + 1); // 시작 시간 + 1시간
            const endTime = prompt('종료 시간을 입력하세요 (HH:MM):', 
                defaultEndTime.getHours().toString().padStart(2, '0') + ':' + 
                defaultEndTime.getMinutes().toString().padStart(2, '0'));

            // 날짜와 시간 조합 
            const startDate = formatDateTime(info.start, startTime);
            const endDate = formatDateTime(info.start, endTime);  // info.start를 기준으로 종료 시간 설정 
            
            const event = {
            		   schedule_name: title,
            	        schedule_start_date: startDate,
            	        schedule_end_date: endDate,
            	        schedule_state: '0',
            	        department_id: parseInt('${vo.department_id}'),
            	        job_position_id: parseInt('${vo.job_position_id}'),
/* JavaScript에서는 EL 태그를 직접 사용할 수 없으므로, JSP에서 미리 변수로 설정한 후 JavaScript에서 사용 */
            	        user_id: manager, // 위에서  var manager = "${vo.user_id}";로 설정함
            	        schedule_no: null
            };

            $.ajax({
                url: '<c:url value="/api/scheduleInsert.do" />',
                method: 'POST',
                data: JSON.stringify(event),
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
            if (parseInt('${vo.job_position_id}') < 5) {
                alert('일정 수정 권한이 없습니다.');
                return;
            }

            // 사용자에게 수정 또는 삭제 선택을 묻는 팝업
            const action = prompt('수정하려면 "edit"을, 삭제하려면 "delete"를 입력하세요:', 'edit');

            if (action === 'edit') {
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
                    user_id: manager
                };
                console.log("-----------------------------------");
                console.log(info.event);

                $.ajax({
                    url: '<c:url value="/api/scheduleUpdate.do" />',
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
                        url: '<c:url value="/api/scheduleDelete.do" />',
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
    console.log('FullCalendar initialized successfully!');
});

function updateEvent(event, calendar) {
    const updateData = {
        schedule_id: parseInt(event.id),
        schedule_name: event.title,
        schedule_start_date: formatDateTime(event.start),
        schedule_end_date: formatDateTime(event.end),
        department_id: parseInt('${vo.department_id}'),
        job_position_id: parseInt('${vo.job_position_id}'),
        user_id: manager
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

    // 날짜 객체 복사 (원본 수정 방지)
    const d = new Date(date.getTime());

    if (timeStr) {
        // 시간 설정
        const [hours, minutes] = timeStr.split(':').map(Number);
        d.setHours(hours);
        d.setMinutes(minutes);
        d.setSeconds(0);
        d.setMilliseconds(0);
    }

    return moment(d).format('YYYY-MM-DD HH:mm:ss');
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
