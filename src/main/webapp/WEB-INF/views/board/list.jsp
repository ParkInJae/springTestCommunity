<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
	<section>
		<h2>공지사항</h2>
		<form action="list.do" method="get">
			<select name="searchType">
				<option value="title"<c:if test="${param.searchType eq 'title'}">selected</c:if>>제목</option>
			</select>
			<input type="text" name="searchValue" value="${param.searchValue}">
			<button>검색</button>
		</form>
		<table border="1">
			<tr>
				<th>No</th>
				<th>제목</th>
				<th>글쓴이</th>
				<th>작성일</th>
			</tr>
			<c:forEach items="${list}" var="vo">
				<tr>
					<td>${vo.nno }</td>
					<td><a href="view.do?nno=${vo.nno }">${vo.title }</a></td>
					<td>${vo.uid }</td>
					<td>${vo.rdate }</td>
				</tr>
			</c:forEach>
		</table>
		
		<!-- 페이징 영역 -->
		<c:if test="${paging.startPage > 1 }">
			<a href="list.do?nowpage=${paging.startPage-1 }">&lt;</a>
		</c:if>
		
		<c:forEach begin="${paging.startPage }" end="${pagin.endPage }" var="cnt">
			<c:if test="${paging.nowPage eq cnt }">
				<b>${cnt }</b>
			</c:if>
			<c:if test="${paging.nowPage ne cnt }">
				<a href="list.do?nowpage=${cnt }">${cnt }</a>
			</c:if>
		</c:forEach>
		
		<c:if test="${pagin.endPage < paging.lastPage }">
			<a href="list.do?nowpage=${paging.endPage+1 }">&gt;</a>
		</c:if>
		
		<a href="write.do">글 등록</a>
	
	</section>
</body>
</html>