<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	<a href="<%= request.getContextPath() %>"><button class="sBtn">home</button></a>
	<h2>회원가입</h2><hr>
	<form action="joinOk.do" method="post">
		<table>
			<tr>
				<th>아이디 : </th>
				<td>
					<input type="text" name="user_id">
				</td>
			</tr>
			<tr>
				<th>비밀번호 : </th>
				<td><input type="password" name="user_password"></td>
			</tr>
			<tr>
				<th>이름 : </th>
				<td><input type="text" name="user_name"></td>
			</tr>
			<tr>
				<th>이메일 : </th>
				<td><input type="text" name="user_email"></td>
			</tr>
			<tr>
				<th>연락처 : </th>
				<td><input type="text" name="user_phone"></td>
			</tr>
			<tr>
				<th>부서 : </th>
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
			</tr>
			<tr>
				<th>직책 : </th>
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
			</tr>
			<tr>
				<th>입사일 : </th>
				<td><input type="date" name="user_join_date"></td>
			</tr>
			<tr>
				<th>은행 : </th>
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
			</tr>
			<tr>
				<th>계좌번호 : </th>
				<td><input type="text" name="user_account"></td>
			</tr>
			<tr>
				<th>주소 : </th>
				<td><input type="text" name="user_address"></td>
			</tr>
		</table>
		<br>
		<button>회원가입</button>
	</form>
</body>
</html>