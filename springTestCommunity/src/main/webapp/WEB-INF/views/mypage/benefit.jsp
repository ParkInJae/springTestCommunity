<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script src="<%= request.getContextPath() %>/resources/js/jquery-3.7.1.js"></script>
	<script>
  function benefitSelect(obj) {
    var select = obj
    var option = obj.value;

    console.log(obj.options[obj.selectedIndex].text);
            		    
    document.getElementById('btText').innerText = obj.options[obj.selectedIndex].text;
    
    updateCost();  
   }
  function updateCost() {
      const selectedAType = $("#benefit_sub").val();
      const selectedBType = $("#benefit_type").val();
      let cost = null;
      
      console.log("selectedAType::"+selectedAType);
      console.log("selectedBType::"+selectedBType);

      if (selectedAType === '0' || selectedBType === '0') {
    	  $("#benefit_money").val(' ');
        return;
      }

      switch (selectedAType) {
        case '1': // 본인
          switch (selectedBType) {
            case '1': // 결혼
              cost = 1000000;
              break;
            case '2': // 회갑
              cost = 1000000;
              break;
            case '3': // 칠순
                cost = 50;
                break;
            case '4': // 출산
                cost = 100000;
                break;
            case '5': // 사망
                cost = 5000000;
                break;
            default:
              cost = null;
          }
      break;
        case '2': // 부
          switch (selectedBType) {
          	case '1': // 결혼
              cost = null;
              break;
            case '2': // 회갑
              cost = null;
              break;
            case '3': // 칠순
                cost = 500000;
                break;
            case '4': // 출산
                cost = null;
                break;
            case '5': // 사망
                cost = 1000000;
                break;
            default:
              cost = null;
          }
      break;
      	case '3': // 모
          switch (selectedBType) {
          	case '1': // 결혼
              cost = null;
              break;
            case '2': // 회갑
              cost = null;
              break;
            case '3': // 칠순
                cost = 500000;
                break;
            case '4': // 출산
                cost = null;
                break;
            case '5': // 사망
                cost = 1000000;
                break;
            default:
              cost = null;
          }
      break;
      	case '4': // 형제
          switch (selectedBType) {
          	case '1': // 결혼
              cost = null;
              break;
            case '2': // 회갑
              cost = null;
              break;
            case '3': // 칠순
                cost = null;
                break;
            case '4': // 출산
                cost = null;
                break;
            case '5': // 사망
                cost = 100000;
                break;
            default:
              cost = null;
          }
      break;
      	case '5': // 자매
          switch (selectedBType) {
          	case '1': // 결혼
              cost = null;
              break;
            case '2': // 회갑
              cost = null;
              break;
            case '3': // 칠순
                cost = null;
                break;
            case '4': // 출산
                cost = null;
                break;
            case '5': // 사망
                cost = 100000;
                break;
            default:
              cost = null;
          }
      break;
      	case '6': // 조부
          switch (selectedBType) {
          	case '1': // 결혼
              cost = null;
              break;
            case '2': // 회갑
              cost = null;
              break;
            case '3': // 칠순
                cost = null;
                break;
            case '4': // 출산
                cost = null;
                break;
            case '5': // 사망
                cost = 200000;
                break;
            default:
              cost = null;
          }
      break;
      	case '7': // 조모
          switch (selectedBType) {
          	case '1': // 결혼
              cost = null;
              break;
            case '2': // 회갑
              cost = null;
              break;
            case '3': // 칠순
                cost = null;
                break;
            case '4': // 출산
                cost = null;
                break;
            case '5': // 사망
                cost = 200000;
                break;
            default:
              cost = null;
          }
      break;
      	case '8': // 자녀
          switch (selectedBType) {
          	case '1': // 결혼
              cost = 300000;
              break;
            case '2': // 회갑
              cost = null;
              break;
            case '3': // 칠순
                cost = null;
                break;
            case '4': // 출산
                cost = null;
                break;
            case '5': // 사망
                cost = 1000000;
                break;
            default:
              cost = null;
          }
      break;
      	case '9': // 배우자
          switch (selectedBType) {
          	case '1': // 결혼
              cost = null;
              break;
            case '2': // 회갑
              cost = null;
              break;
            case '3': // 칠순
                cost = null;
                break;
            case '4': // 출산
                cost = null;
                break;
            case '5': // 사망
                cost = 1000000;
                break;
            default:
              cost = null;
          }
          break;
        default:
          cost = null;
      }
      
      console.log("cost::"+cost);

      if (cost !== null) {
    	  $("#benefit_money").val(`\${cost}`);
      } else {
    	  $("#benefit_money").val(' ');
      }
    }
	</script>
<%-- 	<script>
  	function addBenefitTable() {//행 추가
  		var form = new FormData();
  	    form.append( "benefit_sub", $("#benefit_sub").val() );
  	    form.append( "benefit_date", $("#benefit_date").val() );
  	    form.append( "benefit_tour", $("#benefit_tour").val() );
  	    form.append( "benefit_type", $("#benefit_type").val() );
  	    form.append( "benefit_money", $("#benefit_money").val() );
  	    form.append( "benefit_content", $("#benefit_content").val() );
  	    form.append( "fileName", $("#file_name")[0].files[0] );
  	    
  	  $.ajax({
  	  	url : '<%= request.getContextPath() %>/mypage/benefit.do',
  	    type : "POST",
  	    processData : false,
  	    contentType : false,
  	    data: form,      // 폼 데이터를 전송
  	    success: function(data){
					console.log("등록에 성공했습니다.");
						
						let benefitType = "";
						let benefitSub = "";
						let requestApproveState1 = "";
						let requestApproveState2 = "";
						
						if(data.benefit_type == 1){
							benefitType = "결혼";
						}else if(data.benefit_type == 2){
							benefitType = "회갑";
						}else if(data.benefit_type == 3){
							benefitType = "칠순";
						}else if(data.benefit_type == 4){
							benefitType = "출산";
						}else if(data.benefit_type == 5){
							benefitType = "사망";
						}else if(data.benefit_type == 0){
							benefitType = "미선택";
						}
						
						if(data.benefit_sub == 1){
							benefitSub = "본인";
						}else if(data.benefit_sub == 2){
							benefitSub = "부";
						}else if(data.benefit_sub == 3){
							benefitSub = "모";
						}else if(data.benefit_sub == 4){
							benefitSub = "형제";
						}else if(data.benefit_sub == 5){
							benefitSub = "자매";
						}else if(data.benefit_sub == 6){
							benefitSub = "조부";
						}else if(data.benefit_sub == 7){
							benefitSub = "조모";
						}else if(data.benefit_sub == 8){
							benefitSub = "자녀";
						}else if(data.benefit_sub == 9){
							benefitSub = "배우자";
						}else if(data.benefit_type == 0){
							benefitType = "미선택";
						}
						
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
						html += "<td>"+benefitType+"</td>";
						html += "<td>"+data.request_date+"</td>";
						html += "<td>"+benefitSub+"</td>";
						html += "<td>"+data.benefit_tour+"</td>";
						html += "<td>"+data.benefit_money+"</td>";
						html += "<td>"+requestApproveState1+"</td>";
						html += "<td>"+requestApproveState2+"</td>";
						html += "<td>"+data.request_repuse+"</td>";
						html += "</tr>";
						$("#benefit_check_table").append(html);
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
      cursor: pointer;
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
  <div id="mypage_benefit">
    <div id="mypage_benefit_top">
      <form action="benefit.do" name="benefit" method="post" enctype="multipart/form-data">
        <br>
        <button type="submit" id="benefit_btn" >신청</button>
        <br>
        <br>
        <table id="benefit_table" border="1">
        	<tr>
          	<th colspan="4" style="text-align: left;">
          		>경조금 신청정보
          	</th>
        	</tr>
        	<tr>
          	<th>
          		<label for="benefit_sub">경조대상자</label>
          		<input type="hidden" name="user_id" value="${vo.user_id}">
          	</th>
          	<td>
            	<select style="width:150px" name="benefit_sub" id="benefit_sub" onChange="benefitSelect(this);">
            		<option value="0" selected="selected">선택
              	<option value="1">본인</option>
              	<option value="2">부</option>
              	<option value="3">모</option>
              	<option value="4">형제</option>
              	<option value="5">자매</option>
              	<option value="6">조부</option>
              	<option value="7">조모</option>
              	<option value="8">자녀</option>
              	<option value="9">배우자</option>
            	</select>
          	</td>
          	<th style="width:150px; background-color: lightgray;">관계</th>
          	<td style="width:200px;">
          		<p id="btText"/>
          	</td>
        	</tr>
        	<tr>
          	<th style="width:150px"><label for="benefit_type">경조구분</label></th>
          	<td colspan="3">
            	<select style="width:150px" name="benefit_type" id="benefit_type" onChange="benefitSelect(this)">
            	<option value="0" selected="selected">선택</option>
              	<option value="1">결혼</option>
              	<option value="2">회갑</option>
              	<option value="3">칠순</option>
              	<option value="4">출산</option>
              	<option value="5">사망</option>
            	</select>
          	</td>
        	</tr>
        	<tr>
          	<th>경조일자</th>
          	<td colspan="3">
          		<input type="date" name="benefit_date" id="benefit_date" value="${benefit_date}" style="width:200px">
          	</td>
        	</tr>
        	<tr>
          	<th>근속기준</th>
          	<td>
          		<input type="hidden" name="years" value="${vo.years}">
          		<input type="text" name="benefit_tour" id="benefit_tour" value="${vo.years}" readonly="readonly" style="background:lightgray;">
          	</td>
          	<th style="background-color: lightgray;">경조금신청금액</th>
          	<td>
          		<input type="text" name="benefit_money" id="benefit_money" value="${benefit_money}">
          	</td>
        	</tr>
        	<tr>
          	<th>첨부파일</th>
          	<td colspan="3">
          		<input type="file" name="fileName" id="file_name" value="${file_name}" multiple>
          	</td>
        	</tr>
        	<tr>
          	<th>요청사유</th>
          	<td colspan="3">
          		<input type="text" name="benefit_content" id="benefit_content" value="${benefit_content}" style="width:400px">
          	</td>
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
        		<th style="width: 30px;">번호</th>
        		<th style="width: 30px;">구분</th>
        		<th style="width: 150px;">신청일자</th>
        		<th style="width: 40px;">대상자</th>
        		<th style="width: 50px;">근속기준</th>
        		<th style="width: 80px;">신청금액</th>
        		<th style="width: 100px;">1차결제</th>
        		<th style="width: 100px;">2차결제</th>
        		<th style="width: 80px;">비고</th>
      		</tr>
      	<c:forEach items="${list}" var="vo">
      		<tr>
      			<td>${vo.request_no}</td>
      			<td>
      				<c:choose>
      					<c:when test="${vo.benefit_type eq 1}">결혼</c:when>
      					<c:when test="${vo.benefit_type eq 2}">회갑</c:when>
      					<c:when test="${vo.benefit_type eq 3}">칠순</c:when>
      					<c:when test="${vo.benefit_type eq 4}">출산</c:when>
      					<c:when test="${vo.benefit_type eq 5}">사망</c:when>
      				</c:choose>
      			</td>
      			<td>${vo.benefit_date}</td>
      			<td>
      				<c:choose>
      					<c:when test="${vo.benefit_sub eq 1}">본인</c:when>
      					<c:when test="${vo.benefit_sub eq 2}">부</c:when>
      					<c:when test="${vo.benefit_sub eq 3}">모</c:when>
      					<c:when test="${vo.benefit_sub eq 4}">형제</c:when>
      					<c:when test="${vo.benefit_sub eq 5}">자매</c:when>
      					<c:when test="${vo.benefit_sub eq 6}">조부</c:when>
      					<c:when test="${vo.benefit_sub eq 7}">조모</c:when>
      					<c:when test="${vo.benefit_sub eq 8}">자녀</c:when>
      					<c:when test="${vo.benefit_sub eq 9}">배우자</c:when>
      				</c:choose>
      			</td>
      			<td>${vo.benefit_tour}</td>
      			<td>${vo.benefit_money}</td>
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
      			<td>${vo.request_repuse}</td>
      		</tr>
      	</c:forEach>
    	</table>
  	</div>
  </div>
</body>
</html>