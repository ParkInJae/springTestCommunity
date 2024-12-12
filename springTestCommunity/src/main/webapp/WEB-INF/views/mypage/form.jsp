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
      text-align: left;
		}
    #form_table_text{
      font-size: small;
      display: contents;
    }
    th:nth-child(1){
			background-color: lightgray;
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
	<div id="mypage_form">
    <div id="mypage_form_top">
      <form>
				<br>
        <input id="form_btn" type="button" value="신청">
        <br>
        <br>
        <div id="form_table_div">
					<table id="form_table" border="1">
            <tr>
              <th style="text-align: left;">신청자</th>
              <td>&nbsp;홍길자</td>
            </tr>
            <tr>
              <th style="text-align: left;">사원번호</th>
              <td>&nbsp;417976</td>
            </tr>
            <tr>
              <th style="text-align: left;">신청할 증명서 선택</th>
              <td>
                <label><input type="radio" checked>재직증명서</label>
                <label><input type="radio">경력증명서</label>
              </td>
            </tr>
            <tr>
              <th style="text-align: left;">주민번호 노출 선택</th>
              <td>
                <label><input type="radio" checked>주민번호 앞부분(123456-*******)</label>
                <label><input type="radio">주민번호 전부(123456-1234567)</label>
              </td>
            </tr>
            <tr>
              <th style="text-align: left;">신청부수</th>
              <td>
                &nbsp;<select>
                  <option>선택</option>
                  <option>1부</option>
                  <option>2부</option>
                  <option>3부</option>
                  <option>4부</option>
                </select>
              </td>
            </tr>
            <tr>
              <th style="text-align: left;">사용용도</th>
              <td>&nbsp;<input type="text" style="width: 320px;">&nbsp;&nbsp;<p id="form_table_text">*예:은행제출용/관공서제출용 등</p></td>
            </tr>
            <tr>
              <th style="text-align: left;">제출처</th>
              <td>&nbsp;<input type="text" style="width: 320px;">&nbsp;&nbsp;<p id="form_table_text">*예:은행제출용/관공서제출용 등</p></td>
            </tr>
          </table>
				</div>
  		</form>
  	</div>
  	<div id="mypage_form_mid">
    	<div id="form_info_list">
      	<ul id="form_info_list_ul">
      		<li>- 출력가능한 기간은 신청일 기준 5일 까지 입니다.</li>
      		<li>- 사용용도에 대해 정확한 기재가 필요합니다.</li>
      	</ul>
    	</div>
  	</div>
  	<div id="mypage_form_bottom">
    	<form>
        <table id="form_check_table" border="1">
          <tr>
            <th colspan="10"style="text-align: left; background-color: lightgray;"> >증명서신청/진행현황</th>
          </tr>
          <tr style="background-color: lightgray;">
            <th>번호</th>
            <th>구분</th>
            <th>신청일자</th>
            <th>처리상태</th>
            <th>사용용도</th>
            <th>부수</th>
            <th>출력</th>
            <th>출력기간</th>
            <th>비고</th>
          </tr>
          <tr>
            <td>4</td>
            <td>재직증명서</td>
            <td>2025-01-02</td>
            <td>승인</td>
            <td>은행납부</td>
            <td>0/1</td>
            <td><input type="button" value="출력가능"></td>
            <td>~2025-01-07까지 : 5일</td>
            <td style="width: 100px;"></td>
          </tr>
          <tr>
            <td>3</td>
            <td>재직증명서</td>
            <td>2024-10-02</td>
            <td>승인</td>
            <td>은행납부</td>
            <td>0/1</td>
            <td></td>
            <td>~2024-09-07까지 : 5일</td>
            <td style="width: 100px;"></td>
          </tr>
          <tr>
            <td>2</td>
            <td>경력증명서</td>
            <td>2024-09-05</td>
            <td>승인</td>
            <td>정부기관납부</td>
            <td>0/1</td>
            <td></td>
            <td>~2024-09-10까지 : 5일</td>
            <td></td>
          </tr>
          <tr>
            <td>1</td>
            <td>재직증명서</td>
            <td>2024-09-05</td>
            <td>승인</td>
            <td>정부기관납부</td>
            <td>0/1</td>
            <td></td>
            <td>~2024-09-10까지 : 5일</td>
            <td></td>
          </tr>
        </table>
      </form>
  	</div>
  </div>
</body>
</html>