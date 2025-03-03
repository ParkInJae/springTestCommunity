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
        
        <!-- 근무시간 나타내기 -->
        <div class="summaryContainer">
            <div>현재 주차 </div>
            <div class="mainCalender">
                <a href="?startDate=${prevWeekStart}"> &lt; </a>
                <span id="currentWeekDisplay">${startOfWeek}~${endOfWeek}</span>
                <a href="?startDate=${nextWeekStart}"> &gt; </a>
            </div>
        </div>
        <!-- 월요일부터 일요일까지 근무 시간 나타내기 -->
        <div class="diagramContainer">
            <canvas class="chart" id="workChart" style="height: 400px;"></canvas>
        </div>
        <script>
            var chartData = {
                labels: ['월', '화', '수', '목', '금', '토', '일'],
                datasets: [{
                    label: '일일 근무 시간',
                    data: [
                        <c:choose>
                            <c:when test="${not empty workTimeDetails}">
                                <c:forEach items="${workTimeDetails}" var="day" varStatus="loop">
                                    ${day.workDuration}<c:if test="${!loop.last}">,</c:if>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                0,0,0,0,0,0,0
                            </c:otherwise>
                        </c:choose>
                    ],
                    backgroundColor: 'rgba(54, 162, 235, 0.5)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1
                }]
            };

            var chartOptions = {
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true, 
                        min: 0,
                        max: 540,
                        ticks: {
                            stepSize: 60,
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

            var ctx = document.getElementById('workChart').getContext('2d');
            new Chart(ctx, {
                type: 'bar',
                data: chartData,
                options: chartOptions
            });
        </script>
        
        <!-- 주간 테이블 -->
        <hr>
        <table class="workTable" border="1">
            <thead>
                <tr>
                    <th>날짜</th>
                    <th>출근 시간</th>
                    <th>퇴근 시간</th>
                    <th>근무 시간</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="workTime" items="${workTimeDetails}">
                    <fmt:formatNumber value="${workTime.workDuration / 60}" type="number" var="hours" /> 
                    <fmt:formatNumber value="${workTime.workDuration % 60}" type="number" var="minutes" />
                    <fmt:formatNumber value="${hours - (hours % 1)}" type="number" var="roundedHours" /> 
                    <tr>
                        <td>${workTime.date}</td>    
                        <td>${workTime.checkInTime}</td>  
                        <td>${workTime.checkOutTime}</td>
                        <td>${roundedHours}시간 ${minutes}분</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</sec:authorize>
</body>
</html>
