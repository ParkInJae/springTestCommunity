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
    #mypage_benefit{
      width: 80%;
      height: 100%;
      padding-right: 20%;
      padding-left: 20%;
      display: flex;
      flex-direction: column;
      justify-content: center;
    }
    #mypage_benefit_top{
      width: 100%;
      height: 40%;
    }
    #benefit_table{
      width: 900px;
      border: 1px none lightgray;
    }
    th:nth-child(1){
			background-color: lightgray;
		}
    #benefit_btn{
      width: 80px;
      height: 30px;
      border: 1px solid lightgray;
      border-radius: 20px;
      margin-left: 65%;
      background-color: lightgray;
      font-size: medium;
      color: black;
    }
    #mypage_benefit_mid{
      width: 100%;
      height: 30%;
      padding-top: 5%;
      padding-bottom: 5%;
    }
    #benefit_info_list_ul{
      margin: 0%;
      padding: 0%;
      list-style-type: none;
    }
    #mypage_benefit_bottom{
      width: 100%;
      height: 30%;
    }
    #benefit_check_table{
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
      <a href="logout.do">로그아웃</a>
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
    |
    <a href="form.do">증명서 발급</a>
  </div>
  <hr>
  <div id="mypage_benefit">
    <div id="mypage_benefit_top">
      <form>
        <br>
        <input id="benefit_btn" type="button" value="신청">
        <br>
        <br>
        <table id="benefit_table" border="1">
        	<tr>
          	<th colspan="4" style="text-align: left;"> >경조금 신청정보</th>
        	</tr>
        	<tr>
          	<th style="width:150px">경조구분</th>
          	<td colspan="3">
            	<select style="width:150px">
              	<option>결혼</option>
              	<option>회갑</option>
              	<option>칠순</option>
              	<option>출산</option>
              	<option>사망</option>
            	</select>
          	</td>
        	</tr>
        	<tr>
          	<th>경조대상자</th>
          	<td>
            	<select style="width:150px">
              	<option>홍길자</option>
              	<option>홍길연</option>
              	<option>홍길우</option>
              	<option>박선유</option>
              	<option>홍길동</option>
            	</select>
          	</td>
          	<th style="width:150px; background-color: lightgray;">관계</th>
          	<td>본인</td>
        	</tr>
        	<tr>
          	<th>경조일자</th>
          	<td colspan="3"><input type="date" style="width:200px"></td>
        	</tr>
        	<tr>
          	<th>근속기준</th>
          	<td>1년이상</td>
          	<th style="background-color: lightgray;">경조금신청금액</th>
          	<td>1,000,000</td>
        	</tr>
        	<tr>
          	<th>첨부파일</th>
          	<td colspan="3"><input type="file"></td>
        	</tr>
        	<tr>
          	<th>요청사유</th>
          	<td colspan="3"><input type="text" style="width:400px"></td>
        	</tr>
    		</table>
  		</form>
  	</div>
  	<div id="mypage_benefit_mid">
    	<div id="benefit_info_list">
      	<ul id="benefit_info_list_ul">
      		<li>- 경조 규정 관련해서는 회사 공지사이트에서 참고해 신청한다.</li>
      		<li>- 경조 규정중 경조 휴가가 포함되어 있지 않은 경조를 신청한다. 휴가가 있는 경조여도 휴가를 미사용할 경우 경조금에서 신청하여야 한다.</li>
      		<li>- 이전 신청 및 결재된 정보가 있을 경우, “경조구분=관계=대상자”가 동일할 경우 추가 신청을 할 수 없다. (단, 결혼은 제외/ 규정확인)</li>
      		<li>- 회갑/ 칠순은 법정생년월일 기준이며, 본인 회갑은 61세, (배우자)부모칠순은 70세이다.</li>
      		<li>- 경조금 신청기한은 경조일자 기준으로 이전/이후 1개월까지 신청가능하다(ex. 경조일 : 2월1일 경우 1월 1일 ~ 3월 1일 이내 신청)</li>
      	</ul>
    	</div>
  	</div>
  	<div id="mypage_benefit_bottom">
    	<table id="benefit_check_table" border="1">
      	<tr>
        	<th colspan="9"style="text-align: left; background-color: lightgray;"> >경조금신청/진행현황</th>
      	</tr>
      	<tr style="background-color: lightgray;">
        	<th>번호</th>
        	<th>구분</th>
        	<th>신청일자</th>
        	<th>대상자</th>
        	<th>근속기준</th>
        	<th>신청금액</th>
        	<th>1차결제</th>
        	<th>2차결제</th>
        	<th>비고</th>
      	</tr>
      	<tr>
        	<td>1</td>
        	<td>칠순</td>
        	<td>2024-11-18</td>
        	<td>홍길동</td>
        	<td>1년이상</td>
        	<td>50만원</td>
        	<td>2024-12-18</td>
        	<td>2024-12-19</td>
        	<td style="width: 100px;"></td>
      	</tr>
    	</table>
  	</div>
  </div>
</body>
</html>