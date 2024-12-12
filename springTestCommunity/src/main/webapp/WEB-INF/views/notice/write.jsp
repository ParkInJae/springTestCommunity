<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
	<section>
		<form action="writeOk.do" method="post">
			<table>
				<tr>
					<th>글제목 : </th>
					<td><input type="text" name="post_title"></td>
				</tr>
				<tr>
					<th>내용 : </th>
					<td><textarea name="post_content" cols="40" rows="10"></textarea></td>
				</tr>
			</table>
			<button>작성</button>
		</form>
			<button onclick="location.href='<%=request.getContextPath() %>/notice/list.do'">취소</button>
	</section>
</body>
</html>