<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="<%= request.getContextPath() %>/resources/js/jquery-3.7.1.js"></script>
  <%-- <script>
	function addMedicalTable() {//행 추가
	  var form = new FormData();
	  form.append( "medical_name", $("#medical_name").val() );
	  form.append( "medical_type", $("#medical_type").val() );
	  form.append( "medical_center_type", $("#medical_center_type").val() );
	  form.append( "medical_center_name", $("#medical_center_name").val() );
	  form.append( "medical_start_date", $("#medical_start_date").val() );
	  form.append( "medical_end_date", $("#medical_end_date").val() );
	  form.append( "medical_money", $("#medical_money").val() );
	  form.append( "medical_attach_type", $("#medical_attach_type").val() );
	  form.append( "medical_content", $("#medical_content").val() );
	  form.append( "fileName", $("#file_name")[0].files[0] );
	  
	  $.ajax({
	  	url : '<%= request.getContextPath() %>/mypage/medical.do',
	  	type : "POST",
	  	processData : false,
	  	contentType : false,
	  	data: form,      // 폼 데이터를 전송
	  	success: function(data){
			console.log("등록에 성공했습니다.");

			let requestApproveState1 = "";
			let requestApproveState2 = "";
			
			if(data.request_approve_state1 == 0){
				requestApproveState1 = "대기";
			}else if(data.request_approve_state1 == 1){
				requestApproveState1 = "승인";
			}else if(data.request_approve_state1 == 2){
				requestApproveState1 = "거절";
			}
			
			if(data.request_approve_state2 == 0){
				requestApproveState2 = "대기";
			}else if(data.request_approve_state2 == 1){
				requestApproveState2 = "승인";
			}else if(data.request_approve_state2 == 2){
				requestApproveState2 = "거절";
			}
				
			let html = "";
			html += "<tr>";
			html += "<td>"+data.request_no+"</td>";
			html += "<td>"+data.request_date+"</td>";
			html += "<td>"+data.medical_money+"</td>";
			html += "<td>"+requestApproveState1+"</td>";
			html += "<td>"+requestApproveState2+"</td>";
			html += "<td>"+data.request_approve_money+"</td>";
			html += "<td>"+data.request_repuse+"</td>";
			html += "</tr>";
			$("#medical_check_table").append(html);
		},
	  	error: function (xml,error){ 
	  	  alert("등록에 실패했습니다. 다시시도하세요."); 
	  	}
	  });
	}
</script> --%>
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
		#medicalBtn{
			width: 80px;
      height: 30px;
      border: 1px solid lightgray;
      border-radius: 20px;
      margin-left: 65%;
      background-color: lightgray;
      font-size: medium;
      color: black;
      cursor: pointer;
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
	<div id="mypage_medical">
    <div id="mypage_medical_top">
      <form action="medical.do" method="post" enctype="multipart/form-data">
				<br>
        <input id="medicalBtn" type="submit" value="신청">
        <br>
        <br>
        <div id="medical_table_div">
					<table id="medical_table" border="1">
						<tr>
							<th colspan="9"style="text-align: left; background-color: lightgray;"> >의료비신청/진행현황</th>
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
						</tr>
						<tr>
							<td><input style="width: 70px;" type="text" name="medical_name" id="medical_name"></td>
							<td>
								<select name="medical_type" id="medical_type">
									<option value="0" selected="selected">선택</option>
									<option value="1">입원</option>
									<option value="2">외래</option>
								</select>
							</td>
							<td>
								<select name="medical_center_type" id="medical_center_type">
									<option value="0" selected="selected">선택</option>
									<option value="1">약국</option>
									<option value="2">병원</option>
								</select>
							</td>
							<td><input style="width: 100px;" type="text" name="medical_center_name" id="medical_center_name"></td>
							<td><input type="date" name="medical_start_date" id="medical_start_date"></td>
							<td><input type="date" name="medical_end_date" id="medical_end_date"></td>
							<td><input style="width: 70px;" type="text" name="medical_money" id="medical_money"></td>
							<td>
								<select name="medical_attach_type" id="medical_attach_type">
									<option value="0" selected="selected">선택</option>
									<option value="1">Y</option>
									<option value="2">N</option>
								</select>
							</td>
						</tr>
					</table>
				</div>
				<br>
				<div id="medical_file_div">
					<table id="medical_file" border="1">
						<tr>
							<th style="background-color: lightgray;">첨부파일</th>
							<td><input type="file" name="fileName" id="file_name"></td>
						</tr>
						<tr>
							<th style="background-color: lightgray">요청사유</th>
							<td ><input type="text" style="width: 400px;" name="medical_content" id="medical_content"></td>
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
					<th colspan="8"style="text-align: left; background-color: lightgray;"> >의료비신청/진행현황</th>
				</tr>
				<tr style="background-color: lightgray;">
					<th>번호</th>
					<th>구분</th>
					<th style="width: 200px;">신청일자</th>
					<th>신청금액</th>
					<th>1차결제</th>
					<th>2차결제</th>
					<th>지원금액</th>
					<th>비고</th>
				</tr>
			<c:forEach items="${list}" var="vo">
				<tr>
					<td>${vo.request_no}</td>
					<td>
						<c:choose>
      					<c:when test="${vo.medical_type eq 1}">입원</c:when>
      					<c:when test="${vo.medical_type eq 2}">외래</c:when>
      				</c:choose>
					</td>
					<td>${vo.medical_start_date}</td>
					<td>${vo.medical_money}</td>
					<td>
      				<c:choose>
      					<c:when test="${vo.request_approve_state1 eq 0}">대기</c:when>
      					<c:when test="${vo.request_approve_state1 eq 1}">승인</c:when>
      					<c:when test="${vo.request_approve_state1 eq 2}">거절</c:when>
      				</c:choose>
      			</td>
      			<td>
      				<c:choose>
      					<c:when test="${vo.request_approve_state2 eq 0}">대기</c:when>
      					<c:when test="${vo.request_approve_state2 eq 1}">승인</c:when>
      					<c:when test="${vo.request_approve_state2 eq 2}">거절</c:when>
      				</c:choose>
      			</td>
					<td>${vo.medical_money}</td>
					<td>${vo.request_repuse}</td>
				</tr>
			</c:forEach>
			</table>
  	</div>
  </div>
</body>
</html>