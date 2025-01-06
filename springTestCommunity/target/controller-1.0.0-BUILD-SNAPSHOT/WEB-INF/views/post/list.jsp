<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/board.css" />
	<section>
		<div class="board-container">
			<c:if test="${param.post_type eq 1}">
				<h2>공지사항</h2>
			</c:if>
			<c:if test="${param.post_type eq 0}">
				<h2>사내커뮤니티</h2>
			</c:if>
			<div class="search-container">
				<form action="list.do" method="get">
					<select name="search_type">
						<option value="post_title"<c:if test="${param.search_type eq 'post_title'}">selected</c:if>>제목</option>
					</select>
					<input type="text" name="search_value" value="${param.search_value}" placeholder="검색어를 입력하세요">
					<button>검색</button>
				</form>
			</div>
			<table class="board-table" border="1">
				<tr>
					<th>No</th>
					<th>제목</th>
					<th>글쓴이</th>
					<th>작성일</th>
				</tr>
				<c:forEach items="${list}" var="vo">
					<tr>
						<td>${vo.no}</td>
						<td>
							<a href="view.do?post_no=${vo.post_no }&post_type=${param.post_type}">
								${vo.post_title } 
								<c:if test="${vo.comment_count > 0}">
									<span style="color:#FF5722;">(${vo.comment_count })</span>
								</c:if>
							</a>
						</td>
						<td>${vo.user_name} ${vo.job_position_name}[${vo.department_name}]</td>
						<td>${vo.post_date}</td>
					</tr>
				</c:forEach>
			</table>
			
			<div class="board_writebtn">
				<button class="write_btn" onclick="location.href='write.do?post_type=${param.post_type}'">글 등록</button>
			</div>
			
			<!-- 페이징 영역 -->
			<div class="pagination">
				<c:if test="${paging.startPage > 1 }">
					<a href="list.do?nowpage=${paging.startPage-1 }&post_type=${param.post_type}
					&search_value=${param.search_value}
					&search_type=${param.search_type}">&lt;</a>
				</c:if>
				
				<c:forEach begin="${paging.startPage }" end="${paging.endPage }" var="cnt">
					<c:if test="${paging.nowPage eq cnt }">
						<a id="default" style="color:#FF5722; cursor:default;">${cnt }</a>
					</c:if>
					<c:if test="${paging.nowPage ne cnt }">
						<a href="list.do?nowpage=${cnt }&post_type=${param.post_type}
						&search_value=${param.search_value}
						&search_type=${param.search_type}">${cnt }</a>
					</c:if>
				</c:forEach>
				
				<c:if test="${paging.endPage < paging.lastPage }">
					<a href="list.do?nowpage=${paging.endPage+1 }&post_type=${param.post_type}
					&search_value=${param.search_value}
					&search_type=${param.search_type}">&gt;</a>
				</c:if>
			</div>
	</div>
	</section>
</body>
</html>