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
    text-align: center;
    margin-top: 1%;
    position: absolute;
		left: 28%;
    }
    th{
      background-color: lightgray;
      width: 120px;
    }
    select{
      width: 100px;
      height: 25px;
      text-align: center;
    }
    input{
      width: 100px;
      height: 20px;
    }
    #btn{
      width: 80px;
      height: 90px;
      text-align: center;
      font-size: medium;
    }
    #date_input{
      width: 120px;
      height: 25px;
      text-align: center;
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
	<div id="membership"><a href="<%= request.getContextPath() %>/admin/list.jsp">회원관리</a></div>
	<hr>
<div id="list">
  <div id="list_menu">
    <div id="menu_bar1">
      <div>모든사원 ></div><br>
      <div>임원 ></div><br>
      <div>그룹없음 ></div><br>
      <div style="cursor:pointer;" onclick="location.href='upload_users.do'">신규사원 ></div>
    </div>
		<div id="menu_bar2">
      <div>경영 ></div><br>
      <div>개발 ></div><br>
      <div>인사 ></div><br>
      <div>고객 ></div><br>
			<div>기획 ></div><br>
			<div>디자인 ></div><br>
			<div>마케팅 ></div><br>
			<div>회계 ></div>
    </div>
	</div>
  <div id="list_search">

    <table id="membership_table">
      <tr>
        <th>재직상태</th>
        <td style="width: 120px;">
          <select>
            <option>선택</option>
            <option>재직</option>
            <option>휴직</option>
            <option>퇴직</option>
          </select>
        </td>
        <th>사원번호</th>
        <td><input type="text"></td>
        <th>사원명</th>
        <td><input type="text"></td>
        <td rowspan="3" style="text-align: left;"><input id="btn" type="button" value="검색"></td>
      </tr>
      <tr>
        <th>부서</th>
        <td style="width: 120px;">
          <select>
            <option>선택</option>
            <option>경영</option>
            <option>개발</option>
            <option>인사</option>
            <option>고객</option>
            <option>기획</option>
            <option>디자인</option>
            <option>마케팅</option>
            <option>회계</option>
          </select>
        </td>
        <th>팀</th>
        <td>
          <select>
            <option>선택</option>
            <option>1팀</option>
            <option>2팀</option>
            <option>3팀</option>
            <option>4팀</option>
            <option>5팀</option>
          </select>
        </td>
        <th>직책</th>
        <td>
          <select>
            <option>선택</option>
            <option>이사</option>
            <option>부장</option>
            <option>팀장</option>
            <option>과장</option>
            <option>대리</option>
            <option>주임</option>
            <option>사원</option>
          </select>
        </td>
      </tr>
      <tr>
        <th>유형</th>
        <td colspan="2">
          <select style="width: 150px;">
            <option>선택</option>
            <option>일반정규직</option>
            <option>현장계약직</option>
            <option>인턴십</option>
          </select>
        </td>
        <th>입사일</th>
        <td colspan="2">
          <label><input id="date_input" type="date"></label>~
          <label><input id="date_input" type="date"></label>
        </td>
      </tr>
    </table>
  </div>
</div>
</body>
</html>