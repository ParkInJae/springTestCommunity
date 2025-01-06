<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
  <style>
    body{
      width: 80%;
      margin-left: 10%;
      margin-right: 10%;
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
    #mypage_list{
			padding-left: 1%;
		}
		#mypage_form{
			width: 80%;
      height: 100%;
      padding-right: 20%;
      padding-left: 20%;
      display: flex;
      flex-direction: column;
      justify-content: center;
		}
		#mypage_form_top{
			width: 100%;
      height: 50%;
		}
		#form_btn{
			width: 80px;
      height: 30px;
      border: 1px solid lightgray;
      border-radius: 20px;
      margin-left: 65%;
      background-color: lightgray;
      font-size: medium;
      color: black;
		}
		#form_table{
			width: 900px;
      border: 1px none lightgray;
      text-align: center;
		}
		th{
			background-color: lightgray;
			text-align: center;
		}
		select{
			width: 70px;
      height: 23px;
			text-align: center;
			font-size: medium;
		}
		#mypage_form_mid{
			width: 100%;
      height: 20%;
      padding-top: 5%;
      padding-bottom: 5%;
		}
		#form_info_list_ul{
      margin: 0%;
      padding: 0%;
      list-style-type: none;
    }
		#mypage_form_bottom{
			width: 100%;
      height: 30%;
		}
		#form_check_table{
			width: 900px;
      border: 1px none lightgray;
      text-align: center;
		}
  </style>
</head>
<body>
  <div id="header1">
    <a href="<%= request.getContextPath() %>">
    	<img id="logo_img" src="<%= request.getContextPath() %>/resources/img/logo.png" alt="회사로고" >
    </a>
    <div id = "login_info">
      <a href="<%= request.getContextPath() %>/logout.do">로그아웃</a>
      |
      <a href="info.do">마이페이지</a>
    </div>
  </div>
  <hr>
  <div id="mypage_list">
    <a href="info.do">내 정보 수정</a>
    |
    <a href="benefit.do">경조금 신청</a>
    |
    <a href="medical.do">의료비 신청</a>
    <c:if test="${vo.job_position_id >= 5 }">
    |
    <a href="form.do">신청내용 확인</a>
    </c:if>
  </div>
  <hr>
	<div id="mypage_form">
    <div id="mypage_form_top">
     
				<table id="form_table" border="1">
    			<tr>
      			<th colspan="9" style="text-align:left">>신청내용확인</th>
    			</tr>
    			<tr>
      			<th>번호</th>
      			<th>신청자</th>
      			<th>구분</th>
      			<th>신청일자</th>
      			<th>신청금액</th>
      			<th>신청관련서류확인</th>
      			<th>승인Y/N</th>
      			<th>반려사유</th>
      			<th>결제</th>
    			</tr>
    		<c:forEach items="${list}" var="vo">
    		 <form action="form.do" name="mypage_form" method="post" enctype="form-data">
    		 	<input type="hidden" name="request_no" value="${vo.request_no}">
    			<tr>
      			<td>${vo.request_no}</td>
      			<td>${vo.user_id}</td>
      			<td>
      				<c:choose>
      					<c:when test="${vo.benefit_type eq 1}">결혼</c:when>
      					<c:when test="${vo.benefit_type eq 2}">회갑</c:when>
      					<c:when test="${vo.benefit_type eq 3}">칠순</c:when>
      					<c:when test="${vo.benefit_type eq 4}">출산</c:when>
      					<c:when test="${vo.benefit_type eq 5}">사망</c:when>
      					<c:when test="${vo.medical_type eq 1}">입원</c:when>
      					<c:when test="${vo.medical_type eq 2}">외래</c:when>
      				</c:choose>
      			</td>
      			<td>${vo.benefit_date} ${vo.medical_start_date}</td>
      			<td>${vo.benefit_money}${vo.medical_money}</td>
      			<td>${vo.file_name}${vo.medical_attach_type}</td>
      			<td>
      				<select style="text-align: center;" id="request_approve_state1" name="request_approve_state1" >
      					<option value="0">선택</option>
      					<option value="1">Y</option>
      					<option value="2">N</option>
      				</select>
      			</td>
      			<td>
      				<input type="text" name="request_repuse" value="${vo.request_repuse}">
      			</td>
      			<td>
      				<input type="submit" value="결제">
      			</td>
    			</tr>
    			</form>
    		</c:forEach>
  			</table>
  		
  	</div>
  	<div id="mypage_form_mid">

  	</div>
  	<div id="mypage_form_bottom">

  	</div>
  </div>
</body>
</html>