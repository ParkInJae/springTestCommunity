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
    #mypage_info{
      width: 100%;
      height: 100%;
      display: flex;
      justify-content: space-evenly;
    }
    #info_table{
      width: 800px;
      border: 1px none lightgray;
    }
    th:nth-child(1){
			background-color: lightgray;
		}
    #info_btn{
      width: 80px;
      height: 30px;
      border: 1px solid lightgray;
      border-radius: 20px;
      margin-left: 90%;
      background-color: lightgray;
      font-size: medium;
      color: black;
    }
  </style>
</head>
<body>
  <div id="header1">
    <img id="logo_img" src="./logo.png"alt="회사로고" >
    <div id = "login_info">
      <a href="<%= request.getContextPath() %>/user/logout.jsp">로그아웃</a>
      |
      <a href="<%= request.getContextPath() %>/user/mypage.jsp">마이페이지</a>
    </div>
  </div>
  <hr>
  <div id="mypage_list">
    <a href="<%= request.getContextPath() %>/mypage/info.jsp">내 정보 수정</a>
    |
    <a href="<%= request.getContextPath() %>/mypage/benefit.jsp">경조금 신청</a>
    |
    <a href="<%= request.getContextPath() %>/mypage/medical.jsp">의료비 신청</a>
    |
    <a href="<%= request.getContextPath() %>/mypage/form.jsp">증명서 발급</a>
  </div>
  <hr>
  <div id="mypage_info">
    <form>
      <br>
      <input id="info_btn" type="button" value="수정">
      <br>
      <br>
      <table id="info_table" border="1">
        <tr>
          <th colspan="2">근무부서:고객지원팀&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 사번:417976&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 성명:홍길자</th>
        </tr>
        <tr>
          <th style="width: 150px;">성명 </th>
        	<td>홍길자</td>
      	</tr>
      	<tr>
        	<th>영문성명 </th>
        	<td><input type="text" value="Honggilja"></td>
      	</tr>
      	<tr>
        	<th>전화번호 </th>
        	<td><input type="tel"></td>
      	</tr>
      	<tr>
        	<th>월급은행 </th>
        	<td>
          	<select>
            	<option>국민은행</option>
              <option>농협은행</option>
              <option>신한은행</option>
              <option>카카오뱅크</option>
              <option>우리은행</option>
              <option>하나은행</option>
              <option>전북은행</option>
              <option>토스뱅크</option>
              <option>기업은행</option>
            	<option>수협은행</option>
            	<option>부산은행</option>
            	<option>새마을금고</option>
            	<option>광주은행</option>
          	</select>
        	</td>
      	</tr>
      	<tr>
        	<th>계좌번호 </th>
        	<td><input type="text"></td>
      	</tr>
      	<tr>
        	<th colspan="2">*계좌변경은 월 말일(30일)전에 변경되어야 익월 반영됩니다.</th>
      	</tr>
      	<tr>
        	<th>우편번호 </th>
        	<td><input type="button" value="우편번호찾기"></td>
      	</tr>
      	<tr>
        	<th>주소 </th>
        	<td>전주시 덕진구 백제대로 572</td>
      	</tr>
      	<tr>
        	<th>상세주소 </th>
        	<td><input type="text"></td>
      	</tr>
      	<tr>
        	<th>이메일 </th>
        	<td><input type="email"></td>
      	</tr>
      	<tr>
        	<th>비밀번호변경 </th>
        	<td><input type="button" value="비밀번호변경하기"></td>
      	</tr>
    	</table>
  	</form>
  </div>
</body>
</html>