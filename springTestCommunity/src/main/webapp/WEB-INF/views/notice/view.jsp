<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
	<section>
		<div class="view-container">
			<div class="post-view">
				<h2 class="menu-title">공지사항</h2>
				<table class="post-table">
						<tr>
							<td class="post-title">제목 : </td>
							<td colspan="3">${vo.post_title }</td>
						</tr>
						<tr>
							<td class="post-title">작성자 : </td>
							<td>${vo.user_id }</td>
							<td class="post-title">작성일 : </td>
							<td>${vo.post_date }</td>
						</tr>
 						<tr>
							<td class="post-content" colspan="99" >${vo.post_content}</td>
						</tr>
				</table>
				<div class="post-actions">
					<button class="edit-button" onclick="document.modifyfrm.submit();">수정</button>
					<form name="modifyfim" action="modify.do" method="get">
					
					</form>
					
					<button class="delete-button" onclick="document.deletefrm.submit();">삭제</button>
					<form name="deletefrm" action="delete.do" method="post">
						<input type="hidden" name="post_no" value="${vo.post_no }">
					</form>
					<button class="back-button" onclick="location.href='<%=request.getContextPath() %>/notice/list.do'">목록으로</button>
				</div>
				
			</div>
		</div>
	</section>
</body>
</html>