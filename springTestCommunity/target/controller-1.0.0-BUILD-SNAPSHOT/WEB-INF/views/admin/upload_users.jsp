<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>커뮤니티</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/upload_users.css" />
    <script src="<%= request.getContextPath() %>/resources/js/jquery-3.7.1.js"></script>
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
    
    #membership_table {
	    width: auto; /* 자식 요소 크기에 따라 변경 */
	    max-width: 100%; /* 화면 크기 초과 방지 */
	    overflow-x: auto; /* 테이블 가로 스크롤 허용 */
	    border: 1px none lightgray;
	    text-align: center;
	    margin-top: 1%;
	    position: absolute;
		left: 22%;
	}
    
    th{
      background-color: lightgray;
      text-align: center;
    }
    
    #pass_table th{
    	width: 120px;
    }
    
    #insert_table {
	    width: 100%; /* 테이블이 부모 요소의 폭을 따릅니다 */
	    max-width: 700px; /* 최대 크기를 설정합니다 */
	    table-layout: auto; /* 테이블 열 크기를 자동으로 조정 */
	    border-collapse: collapse; /* 테이블 경계선이 하나로 합쳐지게 */
	    overflow-x: auto; /* 가로 스크롤 가능 */
	}
	
	input[type="text"] {
		outline:none;
		height: 30px;
		border: 1px solid #EEEEEE;
		border-radius: 5px;
		width: 120px;
	}
    </style>
<script>
document.addEventListener("DOMContentLoaded", function () { 
    const fileInput = document.querySelector("input[type='file']");
    fileInput.addEventListener("change", function () {
        $("#messageU").text(""); 
        $("#messageE").text(""); 
    });

    const insertForm = document.querySelector("form[action='insert_user.do']");
    insertForm.addEventListener("input", clearInsertMessages);
    insertForm.addEventListener("change", clearInsertMessages);

    function clearInsertMessages() {
        $("#iMessageU").text(""); 
        $("#iMessageE").text(""); 
    }

    function disableButton(formSelector, buttonSelector, text) {
        const form = document.querySelector(formSelector);
        const button = form.querySelector(buttonSelector);

        form.addEventListener("submit", function () {
            button.disabled = true;
            button.textContent = text;
        });
    }

    disableButton("form[action='upload_users.do']", "#uploadButton", "등록중...");
    disableButton("form[action='insert_user.do']", "button", "등록중...");
});



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
		<a href="list.do" style="color:#767676;">모든사원</a>
	  	  |
  	    <a href="upload_users.do">신규사원등록</a>
	</div>
	<hr>
	<div id="list">
		<div id="list_search">
			<table id="membership_table" style="left:23%; text-align: center;">
				<tr>
					<td style="text-align: left;"><h3>다건등록</h3></td>
				</tr>
				<tr>
					<td style="text-align: left;">
						<a href="<%= request.getContextPath() %>/resources/upload/회원데이터(양식).xlsx" 
						   download="회원데이터(양식).xlsx">양식 파일 받기</a>
					</td>
				</tr>
				<tr>
					<td style="text-align: left;">
						<form action="upload_users.do" method="post" enctype="multipart/form-data">
							<label for="file">엑셀 파일 선택:</label>
							<input type="file" id="file" name="file" style="width:250px; height:30px;" accept=".xlsx, .xls" required>
							<button type="submit" id="uploadButton" style="width:100px; height: 30px; font-size: 16px;">직원등록</button>
							<!-- 업로드 결과 메시지 -->
							<c:if test="${not empty Message}">
							<div class="message" id="messageU" style="color:green;">${Message}</div>
							</c:if>
							<!-- 이메일 발송 결과 메시지 -->
							<c:if test="${not empty MessageE}">
							<div class="message" id="messageE" style="color:red;">${MessageE}</div>
							</c:if>
						</form>
					</td>
				</tr>
				<tr>
					<td>
					<form action="insert_user.do" method="post">
						<table id="insert_table" style="width:700px; margin-top:100px;">
							<tr>
								<td><h3>단건등록</h3></td>
							</tr>
							<tr>
								<th>사번</th>
								<th>이름</th>
								<th>이메일</th>
								<th>전화번호</th>
								<th>부서명</th>
								<th>직책</th>
								<th>입사일</th>
								<th>은행</th>
								<th>계좌번호</th>
								<th>주소</th>
							</tr>
							<tr>
								<td>
									<input type="text" name="user_id" style="width:80px;" placeholder="사번">
								</td>
								<td>
									<input type="text" name="user_name" style="width:80px;" placeholder="이름">
								</td>
								<td>
									<input type="text" name="user_email" style="width:140px;" placeholder="ex)admin@naver.com">
								</td>
								<td>
									<input type="text" name="user_phone" placeholder="ex)010-1111-2222">
								</td>
								<td>
									<select name="department_id" id="department_id">
										<option value="1">경영지원팀</option>
										<option value="2">인사팀</option>
										<option value="3">마케팅팀</option>
										<option value="4">기획팀</option>
										<option value="5">개발팀</option>
										<option value="6">디자인팀</option>
										<option value="7">고객지원팀</option>
										<option value="8">회계팀</option>
									</select>
								</td>
								<td>
									<select name="job_position_id" id="job_position_id">
										<option value="1">사원</option>
										<option value="2">주임</option>
										<option value="3">대리</option>
										<option value="4">과장</option>
										<option value="5">팀장</option>
										<option value="6">부장</option>
										<option value="7">이사</option>
										<option value="8">대표이사</option>
									</select>
								</td>
								<td>
									<input type="text" name="user_join_date" placeholder="입사일">
								</td>
								<td>
									<select name="bank_id" id="bank_id">
										<option value="1">국민은행</option>
										<option value="2">농협은행</option>
										<option value="3">신한은행</option>
										<option value="4">카카오뱅크</option>
										<option value="5">우리은행</option>
										<option value="6">하나은행</option>
										<option value="7">전북은행</option>
										<option value="8">토스뱅크</option>
										<option value="9">기업은행</option>
										<option value="10">수협은행</option>
										<option value="11">부산은행</option>
										<option value="12">새마을금고</option>
										<option value="13">광주은행</option>
									</select>
								</td>
								<td>
									<input type="text" name="user_account" placeholder="계좌번호">
								</td>
								<td>
									<input type="text" name="user_address" placeholder="주소">
								</td>
								<td>
									<button style="width:80px; height: 30px; font-size: 16px;">등록</button>
								</td>
							</tr>
						</table>
					</form>
					<div>
						<!-- 등록 결과 메시지 -->
						<c:if test="${not empty iMessage}">
						<p id="iMessageU" style="color: green; font-weight: bold;">${iMessage}</p>
						</c:if>
						
						<!-- 이메일 발송 결과 메시지 -->
						<c:if test="${not empty iMessageE}">
						<p id="iMessageE" style="color: blue; font-weight: bold;">${iMessageE}</p>
						</c:if>
					</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>