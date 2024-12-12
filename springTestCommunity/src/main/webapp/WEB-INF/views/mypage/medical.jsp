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
		#mypage_medical{
			width: 80%;
      height: 100%;
      padding-right: 20%;
      padding-left: 20%;
      display: flex;
      flex-direction: column;
      justify-content: center;
		}
		#mypage_medical_top{
			width: 100%;
      height: 50%;
		}
		#medical_btn{
			width: 80px;
      height: 30px;
      border: 1px solid lightgray;
      border-radius: 20px;
      margin-left: 65%;
      background-color: lightgray;
      font-size: medium;
      color: black;
		}
		#medical_table{
			width: 900px;
      border: 1px none lightgray;
      text-align: center;
		}
		select{
			width: 70px;
      height: 23px;
			text-align: center;
			font-size: medium;
		}
		#medical_file{
			width: 900px;
      border: 1px none lightgray;
		}
		#mypage_medical_mid{
			width: 100%;
      height: 20%;
      padding-top: 5%;
      padding-bottom: 5%;
		}
		#medical_info_list_ul{
      margin: 0%;
      padding: 0%;
      list-style-type: none;
    }
		#mypage_medical_bottom{
			width: 100%;
      height: 30%;
		}
		#medical_check_table{
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
	<div id="mypage_medical">
    <div id="mypage_medical_top">
      <form>
				<br>
        <input id="medical_btn" type="button" value="신청">
        <br>
        <br>
        <div id="medical_table_div">
					<table id="medical_table" border="1">
						<tr>
							<th colspan="10"style="text-align: left; background-color: lightgray;"> >의료비신청/진행현황</th>
						</tr>
						<tr style="background-color: lightgray;">
							<th>상병명</th>
							<th>진료구분</th>
							<th>진료기관</th>
							<th>진료기관명</th>
							<th>진료시작일</th>
							<th>진료종료일</th>
							<th>수납금액</th>
							<th>진단서제출</th>
							<th style="width: 50px;">+</th>
						</tr>
						<tr>
							<td>인후두염</td>
							<td>
								<select>
									<option>입원</option>
									<option>외래</option>
								</select>
							</td>
							<td>
								<select>
									<option>약국</option>
									<option>병원</option>
								</select>
							</td>
							<td>이젠이비인후과</td>
							<td>2024-12-03</td>
							<td>2024-12-03</td>
							<td>25,800</td>
							<td>Y</td>
							<td></td>
						</tr>
						<tr>
							<td>인후두염</td>
							<td>
								<select>
									<option>입원</option>
									<option>외래</option>
								</select>
							</td>
							<td>
								<select>
									<option>약국</option>
									<option>병원</option>
								</select>
							</td>
							<td>이젠약국</td>
							<td>2024-12-03</td>
							<td>2024-12-03</td>
							<td>25,800</td>
							<td>Y</td>
							<td>-</td>
						</tr>
					</table>
				</div>
				<br>
				<div id="medical_file_div">
					<table id="medical_file" border="1">
						<tr>
							<th style="background-color: lightgray;">첨부파일</th>
							<td><input type="file"></td>
						</tr>
						<tr>
							<th style="background-color: lightgray">요청사유</th>
							<td ><input type="text" style="width: 400px;"></td>
						</tr>
					</table>
				</div>
  		</form>
  	</div>
  	<div id="mypage_medical_mid">
    	<div id="medical_info_list">
      	<ul id="medical_info_list_ul">
      		<li>- 3월 청구의 신청기한은 4월 2일까지 입니다.</li>
      		<li>- 진료시작일 ~ 진료종료일에서 진료종료일자 기준으로 3월말까지 입니다.</li>
      		<li>- 한도는 신청일자 기준으로 지원 한도를 결정합니다.</li>
      		<li>- 지원한도의 산정구간은 매월 1일 부터 말일까지 입니다.</li>
      		<li>- 해당 월 발생분만 신청이 가능하며, 기간 경과 후, 신청을 할 수가 없음을 알려드리며, 첨부하는 영수증과 금액, 진료일자가 동일해야 합니다.</li>
      	</ul>
    	</div>
  	</div>
  	<div id="mypage_medical_bottom">
    	<table id="medical_check_table" border="1">
				<tr>
					<th colspan="9"style="text-align: left; background-color: lightgray;"> >의료비신청/진행현황</th>
				</tr>
				<tr style="background-color: lightgray;">
					<th>번호</th>
					<th>신청일자</th>
					<th>신청금액</th>
					<th>1차결제</th>
					<th>2차결제</th>
					<th>지원금액</th>
					<th>비고</th>
				</tr>
				<tr>
					<td>3</td>
					<td>2024-11-28</td>
					<td>24,800</td>
					<td>2024-11-28</td>
					<td>2024-11-28</td>
					<td>24,800</td>
					<td></td>
				</tr>
				<tr>
					<td>2</td>
					<td>2024-11-27</td>
					<td>24,800</td>
					<td>2024-11-27</td>
					<td>----------</td>
					<td>----------</td>
					<td>반려(영수증확인)</td>
				</tr>
				<tr>
					<td>1</td>
					<td>2024-10-18</td>
					<td>12,800</td>
					<td>2024-10-18</td>
					<td>2024-10-19</td>
					<td>12,800</td>
					<td></td>
				</tr>
			</table>
  	</div>
  </div>
</body>
</html>