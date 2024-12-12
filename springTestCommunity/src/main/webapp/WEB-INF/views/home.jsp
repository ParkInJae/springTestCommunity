<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ include file="./include/header.jsp" %>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/home.css" />
	<br>
	<sec:authorize access="isAuthenticated()">
		<div style="font-size:18px; text-decoration: none; color:black; font-weight: bold;">
			<br>
			<table class="mainTable">
		        <tr>
		            <th class="existValue"><a href="user/companyCommunity.do"></a>공지사항</th>
		            <th>|</th>
		            <th class="existValue"><a href="user/companyCommunity.do">사내 커뮤니티</a></th>
		            <th>|</th>
		            <th class="existValue"><a href="user/myDepartment.do">나의 부서 업무 상황</a></th>
		        </tr>
		    </table>
			<!-- 근무시간 나타내기  -->
			 <div class="summaryContainer">
		        <div>근무 관련 요약 </div>
		        <div class="mainCalender">&lt; 2024-12-09 ~ 2024-12-13 &gt;</div>
	    	</div>
		    <div class="diagramContainer">
		        <div class="weekDiagram">
		        <!-- 특정 값을 나중에 스크립트로 바꿔야함  -->
		        <div><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMwAAADACAMAAAB/Pny7AAAAjVBMVEX///8yqbj1vkAkgTnPNyLh8PJQsr+XzNUpp7YWo7P53KX99ebQPiz88/IAagAbfjOCrorce3HNJgD0tgn86MGyy7b87dPqtrLz1dLNJwcAcQX0uBzLCADS6ewJeinN3c/zrwBpu8ej09r++/anxKzvycYAYwDbdGnOMBkAVgBzpnz52Z3ZaV7MHhLZ5dvpL6TXAAABSklEQVR4nO3dOVLDQBBAURuQsDCrAbMZzL7D/Y9HLFepSwoYdfB+3EG/dIKeyUSSJEmS/qvbsGrs9QZVPdRRj2PvN6iqbmbdNbtj7zeoqp5Nu4MZL5iswWQNJmswWYPJGkzWYLIGkzWYrMFkDSZrMFmDyRpM1mCyBpM1mKzBZA0mazBZgynY8izsqTWcHfN1E3U/bw1nx+xtRZ3CjBYMTIFgYAoEA1MgGJgCwcAUCAamQOkxz+dR61KYl4uw9itWZ693UcelMG+rq+4W7wf9MIfbQUfFMCeLnaB9GBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYmAyYj+uoz9bssAun0yZqA/O9ivrpiVmH/bZm58uwjasdg74Hv4zrZ5EkSZIkSUrdH3tK3RwZZcGjAAAAAElFTkSuQmCC" alt="stick" /> </div>
		            <div >주간 근무 시간 </div> 
		        </div>
		        <div class="addWeekDiagram">
		        <!-- 특정 값을 나중에 스크립트로 바꿔야함  -->
		        <div><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMwAAADACAMAAAB/Pny7AAAAjVBMVEX///8yqbj1vkAkgTnPNyLh8PJQsr+XzNUpp7YWo7P53KX99ebQPiz88/IAagAbfjOCrorce3HNJgD0tgn86MGyy7b87dPqtrLz1dLNJwcAcQX0uBzLCADS6ewJeinN3c/zrwBpu8ej09r++/anxKzvycYAYwDbdGnOMBkAVgBzpnz52Z3ZaV7MHhLZ5dvpL6TXAAABSklEQVR4nO3dOVLDQBBAURuQsDCrAbMZzL7D/Y9HLFepSwoYdfB+3EG/dIKeyUSSJEmS/qvbsGrs9QZVPdRRj2PvN6iqbmbdNbtj7zeoqp5Nu4MZL5iswWQNJmswWYPJGkzWYLIGkzWYrMFkDSZrMFmDyRpM1mCyBpM1mKzBZA0mazBZgynY8izsqTWcHfN1E3U/bw1nx+xtRZ3CjBYMTIFgYAoEA1MgGJgCwcAUCAamQOkxz+dR61KYl4uw9itWZ693UcelMG+rq+4W7wf9MIfbQUfFMCeLnaB9GBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYmAyYj+uoz9bssAun0yZqA/O9ivrpiVmH/bZm58uwjasdg74Hv4zrZ5EkSZIkSUrdH3tK3RwZZcGjAAAAAElFTkSuQmCC" alt="stick" /> </div>
		            <div>추가 근무 시간 </div>
		        </div>
		        <div class="specialWeekDiagram">
		        <!-- 특정 값을 나중에 스크립트로 바꿔야함  -->
		            <div><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMwAAADACAMAAAB/Pny7AAAAjVBMVEX///8yqbj1vkAkgTnPNyLh8PJQsr+XzNUpp7YWo7P53KX99ebQPiz88/IAagAbfjOCrorce3HNJgD0tgn86MGyy7b87dPqtrLz1dLNJwcAcQX0uBzLCADS6ewJeinN3c/zrwBpu8ej09r++/anxKzvycYAYwDbdGnOMBkAVgBzpnz52Z3ZaV7MHhLZ5dvpL6TXAAABSklEQVR4nO3dOVLDQBBAURuQsDCrAbMZzL7D/Y9HLFepSwoYdfB+3EG/dIKeyUSSJEmS/qvbsGrs9QZVPdRRj2PvN6iqbmbdNbtj7zeoqp5Nu4MZL5iswWQNJmswWYPJGkzWYLIGkzWYrMFkDSZrMFmDyRpM1mCyBpM1mKzBZA0mazBZgynY8izsqTWcHfN1E3U/bw1nx+xtRZ3CjBYMTIFgYAoEA1MgGJgCwcAUCAamQOkxz+dR61KYl4uw9itWZ693UcelMG+rq+4W7wf9MIfbQUfFMCeLnaB9GBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYmAyYj+uoz9bssAun0yZqA/O9ivrpiVmH/bZm58uwjasdg74Hv4zrZ5EkSZIkSUrdH3tK3RwZZcGjAAAAAElFTkSuQmCC" alt="stick" /> </div>
		            <div>특별 근무 시간 </div>
		        </div>
		        <div class="wholeWeekDiagram">
		            <div><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMwAAADACAMAAAB/Pny7AAAAjVBMVEX///8yqbj1vkAkgTnPNyLh8PJQsr+XzNUpp7YWo7P53KX99ebQPiz88/IAagAbfjOCrorce3HNJgD0tgn86MGyy7b87dPqtrLz1dLNJwcAcQX0uBzLCADS6ewJeinN3c/zrwBpu8ej09r++/anxKzvycYAYwDbdGnOMBkAVgBzpnz52Z3ZaV7MHhLZ5dvpL6TXAAABSklEQVR4nO3dOVLDQBBAURuQsDCrAbMZzL7D/Y9HLFepSwoYdfB+3EG/dIKeyUSSJEmS/qvbsGrs9QZVPdRRj2PvN6iqbmbdNbtj7zeoqp5Nu4MZL5iswWQNJmswWYPJGkzWYLIGkzWYrMFkDSZrMFmDyRpM1mCyBpM1mKzBZA0mazBZgynY8izsqTWcHfN1E3U/bw1nx+xtRZ3CjBYMTIFgYAoEA1MgGJgCwcAUCAamQOkxz+dR61KYl4uw9itWZ693UcelMG+rq+4W7wf9MIfbQUfFMCeLnaB9GBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYmAyYj+uoz9bssAun0yZqA/O9ivrpiVmH/bZm58uwjasdg74Hv4zrZ5EkSZIkSUrdH3tK3RwZZcGjAAAAAElFTkSuQmCC" alt="stick" /> </div>
		            <div>총 근무 시간 </div>
		        </div>
		    </div>
		    <!-- 주간 테이블 (연장 근무 시간을 포함한 총 근무 시간을 작성 ) -->
				<hr>
				<table class="workTable" border=1>
				<thead>
					<tr>
		                <th>일자(요일) </th>
		                <th>근무 시간 </th>
		                <th>연장 근무 시간 </th>
		                <th>특별 근무 시간 </th>
		                <th>특별 근무 시간 </th>
		                <th>총 근무 시간 </th>
				</thead>
				<tbody>
				 	<tr>
		                <th>2024.12.09</th>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		            </tr>
		            <tr>
		                <td>2024.12.10</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		            </tr>
		            <tr>
		            	<td>2024.12.11</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		            </tr>
		            <tr>
		                <td>2024.12.12</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		            </tr>
		            <tr>
		                <td>2024.12.13</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		            </tr>
		            <tr>
		                <td>2024.12.14</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		            </tr>
		            <tr>
		                <td>2024.12.15</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		                <td>8</td>
		            </tr>
				</tbody>
		  	  </table>
		  	  
		  	  
		</div>
	</sec:authorize>
</body>
</html>