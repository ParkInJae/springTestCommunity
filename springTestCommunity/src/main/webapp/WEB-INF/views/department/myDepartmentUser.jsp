<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<script src='<%= request.getContextPath()%>/resources/js/jquery-3.7.1.js'></script>
<script src="https://cdn.jsdelivr.net/npm/moment@2.30.1/moment.min.js"></script><!-- moment.js 추가  -->
<script src='<%= request.getContextPath()%>/resources/js/index.global.js'></script>
<script src='<%= request.getContextPath()%>/resources/js/index.global.min.js'></script>

<script>
document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
            left: 'prevYear,prev,next,nextYear today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        initialDate: new Date(),
        navLinks: true,
        editable: true,
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

        events: '/api/schedule',  // 이벤트 조회를 위한 API 엔드포인트

        select: function(info) {
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
                schedule_start_date: combineDateAndTime(info.start, startTime),
                schedule_end_date: combineDateAndTime(info.end, endTime),
                schedule_state: '0',
                department_id: '${vo.department_id}',  
                job_position_id: '${vo.job_position_id}', 
                user_id: '${vo.user_id}'
            };
            
            $.ajax({
                url: '/api/schedule',
                method: 'POST',
                data: JSON.stringify(event),
                contentType: 'application/json',
                success: function(result) {
                    calendar.addEvent({
                        id: result.schedule_id,
                        title: result.schedule_name,
                        start: result.schedule_start_date,
                        end: result.schedule_end_date,
                        allDay: false
                    });
                    alert('일정이 생성되었습니다.');
                },
                error: function(xhr) {
                    alert('일정 생성 실패: ' + xhr.responseText);
                }
            });
        },

        eventDrop: function(info) {
            updateEvent(info.event, calendar);
        },
        eventResize: function(info) {
            updateEvent(info.event, calendar);
        },

        eventClick: function(info) {
            if (confirm('이 일정을 삭제하시겠습니까?')) {
                $.ajax({
                    url: '/api/scheduleDelete.do', // 삭제 요청 URL
                    method: 'DELETE',
                    data: JSON.stringify({schedule_id: info.event.id}),
                    contentType: 'application/json',
                    success: function() {
                        info.event.remove();
                        alert('일정이 삭제되었습니다.');
                    },
                    error: function(xhr) {
                        alert('일정 삭제 실패: ' + xhr.responseText);
                    }
                });
            }
        }
    });
    calendar.render();
});

function updateEvent(event, calendar) {
    const updateData = {
        schedule_id: event.id,
        schedule_name: event.title,
        schedule_start_date: event.start,
        schedule_end_date: event.end,
        department_id: '${vo.department_id}',
        job_position_id: '${vo.job_position_id}',
        user_id: '${vo.user_id}'
    };
    
    $.ajax({
        url: '/api/scheduleUpdate.do',
        method: 'PUT',
        data: JSON.stringify(updateData),
        contentType: 'application/json',
        success: function(result) {
            alert('일정이 수정되었습니다.');
        },
        error: function(xhr) {
            event.revert();
            alert('일정 수정 실패: ' + xhr.responseText);
        }
    });
}

function combineDateAndTime(date, timeString) {
    const [hours, minutes] = timeString.split(':').map(Number);
    const newDate = new Date(date);
    newDate.setHours(hours, minutes, 0);
    return newDate.toISOString();
}
</script>
<style>

  body {
    margin: 40px 10px;
    padding: 0;
    font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
    font-size: 14px;
  }

  #calendar {
    max-width: 1100px;
    margin: 0 auto;
  }

</style>
</head>
<body>
  <div id='calendar'></div>
</body>
</html>
