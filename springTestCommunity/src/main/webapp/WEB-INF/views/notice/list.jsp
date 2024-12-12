<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
	<section>
		<div class="board-container">
			<h2>공지사항</h2>
			<form action="list.do" method="get">
				<select name="searchType">
					<option value="post_title"<c:if test="${param.searchType eq 'post_title'}">selected</c:if>>제목</option>
				</select>
				<input type="text" name="searchValue" value="${param.searchValue}" placeholder="">
				<button>검색</button>
			</form>
			<table class="board-table" border="1">
				<tr>
					<th>No</th>
					<th>제목</th>
					<th>글쓴이</th>
					<th>작성일</th>
				</tr>
				<c:forEach items="${list}" var="vo">
					<tr>
						<td>${vo.post_no }</td>
						<td><a href="view.do?post_no=${vo.post_no }">${vo.post_title }</a></td>
						<td>${vo.user_id }</td>
						<td>${vo.post_date }</td>
					</tr>
				</c:forEach>
			</table>
			
			<div class="board_writebtn">
				<button onclick="location.href='<%=request.getContextPath() %>/notice/write.do'">글 등록</button>
			</div>
			
			<!-- 페이징 영역 -->
			<div class="pagination">
				<c:if test="${paging.startPage > 1 }">
					<a href="list.do?nowpage=${paging.startPage-1 }">&lt;</a>
				</c:if>
				
				<c:forEach begin="${paging.startPage }" end="${paging.endPage }" var="cnt">
					<c:if test="${paging.nowPage eq cnt }">
						<b>${cnt }</b>
					</c:if>
					<c:if test="${paging.nowPage ne cnt }">
						<a href="list.do?nowpage=${cnt }">${cnt }</a>
					</c:if>
				</c:forEach>
				
				<c:if test="${paging.endPage < paging.lastPage }">
					<a href="list.do?nowpage=${paging.endPage+1 }">&gt;</a>
				</c:if>
			</div>
	</div>
	</section>
</body>
</html>