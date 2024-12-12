<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script src="<%= request.getContextPath() %>/resources/js/jquery-3.7.1.js"></script>
	<script>
		function pwChangeModal(){
			$("#pwChangeTable").fadeIn();
			// 모달 창 보이게 하기
			$("#modal").fadeIn(); 
		}
	
		function closePwChangeTable() {
	  	$("#pwChangeTable").fadeOut(); // 모달 숨기기
		}
	</script>
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
      cursor: pointer;
    }
    #pwChangeBtn{
    	margin-left: 50%;
    }
    #pwChangeTable{
    	width: 420px;
      height: 190px;
    	display:none;
    	border: 1px solid black;
    	background-color : white;
    	position: fixed;
 			top: 35%;
  		left: 50%;
  		transform: translate(-50%, -50%);
    }
    #pwChangeBtn{
    	border: 1px solid black;
    	color: black;
      cursor: pointer;
    	border-radius: 40%;
    	background-color : lightgray;
    }
    #pwCancelBtn{
    	border: 1px solid black;
    	color: black;
      cursor: pointer;
    	border-radius: 40%;
    	background-color : lightgray;
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
  <div id="mypage_info">
    <form action="info.do" method="post">
      <br>
      <button id="info_btn">수정</button>
      <br>
      <br>
      <table id="info_table" border="1">
        <tr>
          <th colspan="2">근무부서:고객지원팀&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 사번:417976&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 성명:홍길자</th>
        </tr>
        <tr>
          <th style="width: 150px;">성명 </th>
        	<td>홍길자<input type="hidden" name="user_id"></td>
      	</tr>
      	<tr>
        	<th>입사일 </th>
        	<td>2002년 03년 28일</td>
      	</tr>
      	<tr>
        	<th>전화번호 </th>
        	<td><input type="tel" name="user_phone"></td>
      	</tr>
      	<tr>
        	<th>월급은행 </th>
        	<td>
          	<select name="bank_id">
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
      	</tr>
      	<tr>
        	<th>계좌번호 </th>
        	<td><input type="text" name="user_account"></td>
      	</tr>
      	<tr>
        	<th colspan="2">*계좌변경은 월 말일(30일)전에 변경되어야 익월 반영됩니다.</th>
      	</tr>
      	<tr>
        	<th>우편번호 </th>
        	<td><input type="text" id="sample4_postcode"><input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기"></td>
      	</tr>
      	<tr>
        	<th>주소 </th>
        	<td><input type="text" name="user_address" id="sample4_roadAddress"><input type="hidden" id="sample4_jibunAddress"></td>
      	</tr>
      	<tr>
        	<th>상세주소 </th>
        	<td><input type="text" id="sample4_detailAddress"></td>
      	</tr>
      	<tr>
        	<th>이메일 </th>
        	<td><input type="email" name="user_email"></td>
      	</tr>
      	<tr>
        	<th>비밀번호변경 </th>
        	<td><button  type="button" onclick="pwChangeModal();">비밀번호변경하기</button></td>
      	</tr>
    	</table>
  	</form>
  	<div id="pwChangeTable">
  		<div id="pwChangeDiv">비밀번호변경
  			<button id="pwChangeBtn" onclick="pwChangeOk();">확인</button>
  			<button id="pwCancelBtn" type="reset" onclick="closePwChangeTable();">취소</button>
  		</div>
  		<table>
  			<tr>
  				<th>이메일 입력</th>
  				<td><input type="text"></td>
  				<td><button>발송</button></td>
  			</tr>
  			<tr>
  				<th>인증번호 입력</th>
  				<td><input type="text"></td>
  				<td><button>인증확인</button></td>
  			</tr>
  			<tr>
  				<th colspan="3">
  				인증번호가 일치합니다.
  				</th>
  			</tr>
  			<tr>
  				<th>변경할 비밀번호 입력</th>
  				<td colspan="2"><input type="password"></td>
  			</tr>
  			<tr>
  				<th>변경할 비밀번호 확인</th>
  				<td><input type="text"></td>
  				<td><button>확인</button></td>
  			</tr>
  			<tr>
  				<th colspan="3">두 비밀번호가 일치합니다.</th>
  			</tr>
  		</table>
  	</div>
  </div>
	<span id="guide" style="color:#999;display:none"></span>
	<input type="hidden" id="sample4_detailAddress" placeholder="상세주소">
	<input type="hidden" id="sample4_extraAddress" placeholder="참고항목"> 
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script>
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample4_postcode').value = data.zonecode;
                document.getElementById("sample4_roadAddress").value = roadAddr;
                document.getElementById("sample4_jibunAddress").value = data.jibunAddress;
                
                // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
                if(roadAddr !== ''){
                    document.getElementById("sample4_extraAddress").value = extraRoadAddr;
                } else {
                    document.getElementById("sample4_extraAddress").value = '';
                }

                var guideTextBox = document.getElementById("guide");
                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
                if(data.autoRoadAddress) {
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                    guideTextBox.style.display = 'block';

                } else if(data.autoJibunAddress) {
                    var expJibunAddr = data.autoJibunAddress;
                    guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                    guideTextBox.style.display = 'block';
                } else {
                    guideTextBox.innerHTML = '';
                    guideTextBox.style.display = 'none';
                }
            }
        }).open();
    }
</script>
</body>
</html>