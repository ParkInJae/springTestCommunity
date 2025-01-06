<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>커뮤니티</title>
<script src="<%= request.getContextPath() %>/resources/js/jquery-3.7.1.js"></script>
  <style>
	body{
		width: 80%;
		margin-left: 10%;
		margin-right: 10%;
	}
    #header{
	    height: 60px;
	    margin: 0%;
	    position: relative;
	    display: flex;
	    width: 100%;
    }
    #logo_img{
	    width: 120px;
	    height: 60px;
	    margin:0px auto;
    }
    #login_info{
  		width: 200px;
		display: inline;
		position: absolute;
		left: 80%;
		top: 5%;
    }
	#membership{
		padding-left: 1%;
	}
    #list{
	    width: 100%;
	    height: 100%;
    }
    #list_menu{
	    width: 25%;
	    height: 100%;
    }
    #menu_bar1{
		width: 10%;
    	height: 25%;
		margin-top: 1%;
    	background-color: lightgray;
    	float: left;
		position: absolute;
		left: 10%;
		text-align: center;
		display: flex;
		flex-direction: column;
		justify-content: center;
    }
	#menu_bar2{
	    width: 10%;
	    height: 42%;
		margin-top: 16%;
    	background-color: lightgray;
		position: absolute;
		left: 10%;
		text-align: center;
		display: flex;
		flex-direction: column;
		justify-content: center;
    }
    #list_search{
	    width: 75%;
	    height: 30%;
    }
    #membership_table{
	    width: 1000px;
	    height: 100px;
	    border: 1px none lightgray;
	    margin-top: 1%;
	    position: absolute;
		left: 28%;
    }
    th{
	    background-color: lightgray;
	    width: 120px;
    }
    
    #btn{
	    width: 80px;
	    height: 90px;
	    text-align: center;
	    font-size: medium;
    }
 
    a{
		text-decoration-line: none;
		color: black;
		font-weight:bold;
	}
	
	input[type="text"],input[type="date"] {
		outline:none;
		height: 30px;
		border: 1px solid #EEEEEE;
		border-radius: 5px;
		width: 200px;
	}
	
	.sBtn{
		width:80px; 
		height:30px;
		background-color: #333333;
		color:white;
		font-weight: bold;
		border: 1px solid white;
		border-radius: 5px;
		cursor:pointer;
		font-size: 16px;
	}
	
	select{
		height:30px;
		border: 1px solid #EEEEEE;
		border-radius: 5px;
		outline: none;
		width: 120px;
		text-align: center;
	}
	
	.pagination {
	    text-align: center;
	}
	
	.pagination a {
	    text-decoration: none;
	    color: #222222;
	    font-size: 16px;
	    padding: 8px 15px;
	    margin: 0 5px;
	    border: 1px solid #E2E2E2;
	    border-radius: 5px;
	    transition: all 0.3s;
	}
	
	.pagination a.active {
	    background: #E2E2E2;
	    color: white;
	}
	
	.pagination a:hover {
	    background: #E2E2E2;
	    /* color: white; */
	}
	
	#default:hover{
		background: white;
	}
  </style>
  <script>
  function updateUser(user_id,obj){
	console.log(user_id);
	console.log($(obj).parent().parent());
	const row = $(obj).parent().parent()[0];
	const userState = row.querySelector('select[name="user_state"]');
    const departmentId = row.querySelector('select[name="department_id"]');
    const jobPositionId = row.querySelector('select[name="job_position_id"]');
	
    console.log(userState.value);
    console.log(departmentId.value);
    console.log(jobPositionId.value);
    
	$.ajax({
		url : "<%= request.getContextPath() %>/admin/updateUser.do",
		type : "post",
		data : {
			user_id : user_id,
			user_state : userState.value,
			department_id :departmentId.value,
			job_position_id : jobPositionId.value
		},
		success : function(data){
			console.log(data)
			if(data) {
				console.log(data);
                userState.value = data.user_state;
                departmentId.value = data.department_id;
                jobPositionId.value = data.job_position_id;
            }else {
                alert('수정에 실패했습니다. 다시 시도해주세요.');
            }
		}
	});
  }
  </script>
</head>
<body>
	<div id="header1">
	    <img id="logo_img" style="cursor:pointer;" onclick="location.href='<%= request.getContextPath() %>'" src="<%= request.getContextPath() %>/resources/img/logo.png" alt="회사로고">
	    <div id = "login_info">
	  	    <a href="<%= request.getContextPath() %>/logout.do">로그아웃</a>
	  	  |
	  	    <a href="<%= request.getContextPath() %>/mypage/info.do">마이페이지</a>
	    </div>
	</div>
	<hr>
	<div id="membership">
		<a href="list.do">모든사원</a>
	  	  |
  	    <a href="upload_users.do" style="color:#767676;">신규사원등록</a>
	</div>
	<hr>
	<div id="list">
		<div id="list_search">
			<h2 style="margin-left:30%;">직원조회</h2>
			<form action="list.do" method="get">
				<table id="membership_table">
					<tr>
						<th>재직상태</th>
						<td style="width: 120px;">
							<select name="user_state">
								<option value="0">선택</option>
								<option value="1"
								<c:if test="${param.user_state eq '1'}">
									selected
								</c:if>
								>재직</option>
								<option value="2"
								<c:if test="${param.user_state eq '2'}">
									selected
								</c:if>
								>휴직</option>
								<option value="3"
								<c:if test="${param.user_state eq '3'}">
									selected
								</c:if>
								>퇴직</option>
							</select>
						</td>
						<th>부서</th>
						<td style="width: 120px;">
							<select name="department_id">
								<option value="0">선택</option>
								<option value="1"
								<c:if test="${param.department_id eq '1'}">
									selected
								</c:if>
								>경영지원팀</option>
								<option value="2"
								<c:if test="${param.department_id eq '2'}">
									selected
								</c:if>
								>인사팀</option>
								<option value="3"
								<c:if test="${param.department_id eq '3'}">
									selected
								</c:if>
								>마케팅팀</option>
								<option value="4"
								<c:if test="${param.department_id eq '4'}">
									selected
								</c:if>
								>기획팀</option>
								<option value="5"
								<c:if test="${param.department_id eq '5'}">
									selected
								</c:if>
								>개발팀</option>
								<option value="6"
								<c:if test="${param.department_id eq '6'}">
									selected
								</c:if>
								>디자인팀</option>
								<option value="7"
								<c:if test="${param.department_id eq '7'}">
									selected
								</c:if>
								>고객지원팀</option>
								<option value="8"
								<c:if test="${param.department_id eq '8'}">
									selected
								</c:if>
								>회계팀</option>
							</select>
						</td>
						<th>직책</th>
						<td style="width: 120px;">
							<select name="job_position_id">
								<option value="0">선택</option>
								<option value="1"
								<c:if test="${param.job_position_id eq '1'}">
									selected
								</c:if>
								>사원</option>
								<option value="2"
								<c:if test="${param.job_position_id eq '2'}">
									selected
								</c:if>
								>주임</option>
								<option value="3"
								<c:if test="${param.job_position_id eq '3'}">
									selected
								</c:if>
								>대리</option>
								<option value="4"
								<c:if test="${param.job_position_id eq '4'}">
									selected
								</c:if>
								>과장</option>
								<option value="5"
								<c:if test="${param.job_position_id eq '5'}">
									selected
								</c:if>
								>팀장</option>
								<option value="6"
								<c:if test="${param.job_position_id eq '6'}">
									selected
								</c:if>
								>부장</option>
								<option value="7"
								<c:if test="${param.job_position_id eq '7'}">
									selected
								</c:if>
								>이사</option>
								<option value="8"
								<c:if test="${param.job_position_id eq '8'}">
									selected
								</c:if>
								>대표이사</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>입사일</th>
				        <td colspan="6">
					        <input id="date_input" type="date" name="first_date" value="${param.first_date}">~
					        <input id="date_input" type="date" name="second_date" value="${param.second_date}">
				        </td>
					</tr>
					<tr>
						<td>
							<select name="search_type">
								<option value="user_id"
								<c:if test="${param.search_type eq 'user_id'}">
									selected
								</c:if>
								>사번</option>
								<option value="user_name"
								<c:if test="${param.search_type eq 'user_name'}">
									selected
								</c:if>
								>이름</option>
							</select>
						</td>
						<td>
							<input type="text" name="search_value" value="${param.search_value}">
						</td>
						<td style="text-align: left;">
							<button class="sBtn">검색</button>
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<table border="0" style="text-align: center; width:100%;">
								<tr>
									<th style="text-align: center; width:60px;">재직상태</th>
									<th style="text-align: center; width:120px;">사번</th>
									<th style="text-align: center; width:160px;">사원명</th>
									<th style="text-align: center; width:160px;">입사일</th>
									<th style="text-align: center; width:280px;">부서</th>
									<th style="text-align: center; width:200px;">직책</th>
									<th style="text-align: center; width:250px;">비상연락망</th>
									<th style="text-align: center; width:120px;">정보수정</th>
								</tr>
								<c:forEach items="${userList}" var="userList">
									<tr class="trHover">
										<td>
											<select name="user_state">
												<option value="0">선택</option>
												<option value="1"
												<c:if test="${userList.user_state eq '1'}">
													selected
												</c:if>
												>재직</option>
												<option value="2"
												<c:if test="${userList.user_state eq '2'}">
													selected
												</c:if>
												>휴직</option>
												<option value="3"
												<c:if test="${userList.user_state eq '3'}">
													selected
												</c:if>
												>퇴직</option>
											</select>
										</td>
										<!-- user_id에서 앞의 두 글자를 제외한 부분 출력 -->
										<!-- fn:substring(문자열, 시작인덱스, 끝인덱스) -->
         							    <td>${fn:substring(userList.user_id, 2, fn:length(userList.user_id))}</td>
										<td>${userList.user_name}</td>
										<td>${userList.user_join_date}</td>
										<td>
											<select name="department_id">
												<option value="0">선택</option>
												<option value="1"
												<c:if test="${userList.department_id eq '1'}">
													selected
												</c:if>
												>경영지원팀</option>
												<option value="2"
												<c:if test="${userList.department_id eq '2'}">
													selected
												</c:if>
												>인사팀</option>
												<option value="3"
												<c:if test="${userList.department_id eq '3'}">
													selected
												</c:if>
												>마케팅팀</option>
												<option value="4"
												<c:if test="${userList.department_id eq '4'}">
													selected
												</c:if>
												>기획팀</option>
												<option value="5"
												<c:if test="${userList.department_id eq '5'}">
													selected
												</c:if>
												>개발팀</option>
												<option value="6"
												<c:if test="${userList.department_id eq '6'}">
													selected
												</c:if>
												>디자인팀</option>
												<option value="7"
												<c:if test="${userList.department_id eq '7'}">
													selected
												</c:if>
												>고객지원팀</option>
												<option value="8"
												<c:if test="${userList.department_id eq '8'}">
													selected
												</c:if>
												>회계팀</option>
											</select>
										</td>
										<td>
											<select name="job_position_id">
												<option value="0">선택</option>
												<option value="1"
												<c:if test="${userList.job_position_id eq '1'}">
													selected
												</c:if>
												>사원</option>
												<option value="2"
												<c:if test="${userList.job_position_id eq '2'}">
													selected
												</c:if>
												>주임</option>
												<option value="3"
												<c:if test="${userList.job_position_id eq '3'}">
													selected
												</c:if>
												>대리</option>
												<option value="4"
												<c:if test="${userList.job_position_id eq '4'}">
													selected
												</c:if>
												>과장</option>
												<option value="5"
												<c:if test="${userList.job_position_id eq '5'}">
													selected
												</c:if>
												>팀장</option>
												<option value="6"
												<c:if test="${userList.job_position_id eq '6'}">
													selected
												</c:if>
												>부장</option>
												<option value="7"
												<c:if test="${userList.job_position_id eq '7'}">
													selected
												</c:if>
												>이사</option>
												<option value="8"
												<c:if test="${userList.job_position_id eq '8'}">
													selected
												</c:if>
												>대표이사</option>
											</select>
										</td>
										<td>${userList.user_phone}</td>
										<td>
											<button type="button" class="sBtn" onclick="updateUser('${userList.user_id}',this)">수정</button>
										</td>
									</tr>
								</c:forEach>
							</table>
							<!-- 페이징 영역 -->
							<div style="text-align: center; margin-top:30px;" class="pagination">
								<!-- 이전페이지로 이동 -->
								<c:if test="${paging.startPage > 1 }">
									<a href="list.do?nowPage=${paging.startPage-1}
									&search_value=${param.search_value}
									&search_type=${param.search_type}
									<c:if test="${param.department_id != null && param.department_id != 0}">&department_id=${param.department_id}</c:if>
    								<c:if test="${param.job_position_id != null && param.job_position_id != 0}">&job_position_id=${param.job_position_id}</c:if>
    								<c:if test="${param.user_state != null && param.user_state != 0}">&user_state=${param.user_state}</c:if>
									<c:if test="${not empty param.first_date}">&first_date=${param.first_date}</c:if>
   									<c:if test="${not empty param.second_date}">&second_date=${param.second_date}</c:if>
									">&lt;</a>
								</c:if>
								
								<!-- 페이지번호 -->
								<c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="cnt">
									<c:if test="${paging.nowPage eq cnt}">
										<a id="default" style="color:#FF5722; cursor:default;">${cnt}</a>
									</c:if>
									<c:if test="${paging.nowPage ne cnt}">
										<a href="list.do?nowPage=${cnt}
										&search_value=${param.search_value}
										&search_type=${param.search_type}
										<c:if test="${param.department_id != null && param.department_id != 0}">&department_id=${param.department_id}</c:if>
									    <c:if test="${param.job_position_id != null && param.job_position_id != 0}">&job_position_id=${param.job_position_id}</c:if>
									    <c:if test="${param.user_state != null && param.user_state != 0}">&user_state=${param.user_state}</c:if>
										<c:if test="${not empty param.first_date}">&first_date=${param.first_date}</c:if>
   										<c:if test="${not empty param.second_date}">&second_date=${param.second_date}</c:if>
										">${cnt}</a>
									</c:if>
								</c:forEach>
								
								<!-- 다음페이지로 이동 -->
								<c:if test="${paging.endPage < paging.lastPage}">
									<a href="list.do?nowPage=${paging.endPage+1}
									&search_value=${param.search_value}
									&search_type=${param.search_type}
									<c:if test="${param.department_id != null && param.department_id != 0}">&department_id=${param.department_id}</c:if>
								    <c:if test="${param.job_position_id != null && param.job_position_id != 0}">&job_position_id=${param.job_position_id}</c:if>
								    <c:if test="${param.user_state != null && param.user_state != 0}">&user_state=${param.user_state}</c:if>
									<c:if test="${not empty param.first_date}">&first_date=${param.first_date}</c:if>
   									<c:if test="${not empty param.second_date}">&second_date=${param.second_date}</c:if>
									">&gt;</a>
								</c:if>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>