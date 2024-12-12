<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<a href="<%= request.getContextPath() %>"><button class="sBtn">home</button></a>
	<h2>로그인</h2><hr>
	<form action="loginOk.do" method="post">
		<table>
			<tr>
				<th>아이디 : </th>
				<td><input type="text" name="user_id"></td>
			</tr>
			<tr>
				<th>비밀번호 : </th>
				<td><input type="password" name="user_password"></td>
			</tr>
		</table>
		<br>
		<button>로그인</button>
	</form>
</body>
</html>