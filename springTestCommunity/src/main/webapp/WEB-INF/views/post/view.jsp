<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/board.css" />
	<section>
		<div class="view-container">
			<div class="post-view">
				<c:if test="${param.post_type eq 1}">
					<h2 class="menu-title">공지사항</h2>
				</c:if>
				<c:if test="${param.post_type eq 0}">
					<h2 class="menu-title">사내커뮤니티</h2>
				</c:if>
				<table class="post-table">
						<tr>
							<td class="post-title">제목 </td>
							<td colspan="3">${vo.post_title }</td>
						</tr>
						<tr>
							<td class="post-title">작성자 </td>
							<td>${vo.user_name } ${vo.job_position_name}[${vo.department_name}]</td>
							<td class="post-title">작성일 </td>
							<td>${vo.post_date }</td>
						</tr>
 						<tr>
							<td class="post-content" colspan="99" >${vo.post_content}</td>
						</tr>
				</table>
				<div class="post-actions">
					<c:if test="${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.user_id == vo.user_id}">
						<button class="edit-button" onclick="location.href='<%=request.getContextPath() %>/post/modify.do?post_no=${vo.post_no }&post_type=${param.post_type}'">수정</button>
						<button class="delete-button" onclick="document.deletefrm.submit();">삭제</button>
						<form name="deletefrm" action="delete.do" method="post">
							<input type="hidden" name="post_no" value="${vo.post_no }">
						</form>
					</c:if>
					<button class="back-button" onclick="location.href='<%=request.getContextPath() %>/post/list.do?post_type=${param.post_type}'">목록으로</button>
				</div>
				
			</div>
			<script>
			let postNo = "";
			let postType = "";
			$(document).ready(function() {
				postNo = ${vo.post_no};
				postType = ${vo.post_type};
				console.log(postNo);
		        loadComment(postNo);
		        
		        // 메뉴 버튼 이벤트 초기화
		        $(document).on('click', '.menuB', function(event) {
		            event.stopPropagation(); // 이벤트 전파 방지
		            let commentNo = $(this).data("comment-id");
		            $(".menutableB").hide(); // 다른 메뉴 숨김
		            $("#menutableB" + commentNo).toggle(); // 현재 메뉴 토글
		        });

		        // 문서의 다른 곳 클릭하면 모든 메뉴 숨김
		        $(document).click(function() {
		            $(".menutableB").hide();
		        });

		        // 메뉴 내부 클릭 시 메뉴가 닫히지 않도록 방지
		        $(document).on('click', '.menutableB', function(event) {
		            event.stopPropagation();
		        });
			});
			
			//두번째 변수 생략시 1로 들어감
			function loadComment(post_no,page = 1) {
			    $.ajax({
			        url: "<%= request.getContextPath()%>/comment/loadComment.do",
			        type: "get",
			        data: { post_no: post_no , cnowpage:page},
			        success: function(data) {
			        	let html = "";
			        	console.log("data:" + data);
			        	for(cvo of data.clist){
				        	html += `
			        		<div class="comment-item" id="comment-item\${cvo.comment_no}">
					            <div class="comment-header">
					                <span class="comment-author">\${cvo.user_name} \${cvo.job_position_name}[\${cvo.department_name}]</span>
					                `;
			                if(cvo.user_id == user_id){
					        html += `<div style="display: flex; align-items: center; gap: 10px;"> 
							            <span data-comment-id="\${cvo.comment_no}" class="menuB" style="display: flex; align-items: center; gap: 10px;">
							            	<i class="fas fa-solid fa-bars"></i>
									        <span id="menutableB\${cvo.comment_no}"  class="menutableB">
								                <div class="menu-container">
							                        <i class="fas fa-solid fa-pen-nib"></i>
									                <span id="cModify" 
							                        onclick="commentUpdate(\${cvo.comment_no})">수정</span>
							                    </div>
								                <div class="menu-container">
							                        <i class="fas fa-solid fa-eraser"></i>
									                <span id="cDelete"
						                      		 onclick="commentDel(\${cvo.comment_no})">삭제</span>
							                    </div>
									        </span>
								  	  	</span>
							  	    </div>`;
			                }
				            html += `
					            </div>
				            	<div class="comment-body" id="comment-body\${cvo.comment_no}">
					             	  \${cvo.comment_content}
					            </div>
					            <div class="comment-footer">
				                	<span class="comment-date">\${cvo.comment_date}</span>
				                </div>
					        </div>
				        	`;
			        	}
			        	if(data.cpaging){
			        		paging = data.cpaging;
			        		html += `<div class="pagination">`;
		    				if(paging.startPage > 1){
		    					html += `<a class="paging-link" data-page="\${paging.startPage - 1}">&lt;</a>`;
		    				} 
			    			for(let cnt = paging.startPage; cnt <= paging.endPage; cnt++){
			    				if(paging.nowPage == cnt){
				    				html += `<a id="default" style="color:#FF5722; cursor:default;">\${cnt }</a>`;
			    				}else{
			    					html += `<a class="paging-link" data-page="\${cnt}">\${cnt}</a>`;
			    				}
			    			}	
			    			if(paging.endPage < paging.lastPage){
			    				html += `<a class="paging-link" data-page="\${paging.endPage + 1}">&gt;</a>`;
			    			}
			    			html += `</div>`;
			        	}
			            $(".comment-list").html(html);
			            
			            // 페이징 링크에 이벤트 바인딩
			            $(".paging-link").click(function(e) {
			                e.preventDefault();
			                let page = $(this).data("page");
			                loadComment(post_no, page);
			            });
			            
			        },
			        error: function(xhr, status, error) {
			            console.error("AJAX Error:", status, error);  // AJAX 오류 상태 및 에러 메시지 출력
			            alert("댓글 로딩 중 오류가 발생했습니다.");
			        }
			    });
			}
			
			function commentInsert(post_no,post_type){
				$.ajax({
					url : "<%= request.getContextPath()%>/comment/write.do",
					type : "post",
					data : {
						post_no : post_no,
						post_type : post_type,
						user_id : user_id,
						comment_content : $("#comment-input").val()
					},
					success : function(result){
						loadComment(post_no);
						$("#comment-input").val("");
					}
				});
			}
			
			function commentUpdate(comment_no){
			    const commentElement = $("#comment-item" + comment_no);
			    const currentText = $("#comment-body" + comment_no).text().trim();
			    
			    const inputElement = $(`
		    	   <div class="comment-input-container">
				        <textarea id="comment-input-\${comment_no}" class="comment-input" name="comment_content"
				        placeholder="댓글을 입력하세요..." rows="4">\${currentText}</textarea>
				        <button class="comment-submit-btn" style="width:120px;">
				        <a class="cancel-btn">취소</a> &nbsp;
				        <a class="save-btn">저장</a>
				        </button>
				    </div>`);
			    
			    commentElement.replaceWith(inputElement);
			    
			    inputElement.find(".cancel-btn").on("click", function (e) {
			        e.preventDefault();
			        inputElement.replaceWith(commentElement); 
			    });

			    inputElement.find(".save-btn").on("click", function (e) {
			        e.preventDefault();
			        const newText = inputElement.find(`#comment-input-\${comment_no}`).val(); 

			        if(newText && newText !== currentText) {
			            saveComment(comment_no, newText, inputElement, commentElement); 
			        }else {
			            alert("댓글 내용이 비어있거나 변경되지 않았습니다.");
			        }
			    });
			}
			
			function saveComment(comment_no, newText, inputElement,commentElement){
				const originalElement = commentElement.text(inputElement.val().trim());
				
				$.ajax({
					url : "<%= request.getContextPath()%>/comment/modify.do",
					type : "post",
					data : {
						comment_no : comment_no,
						comment_content : newText,
						user_id : user_id
					},
					success : function(result){
						if(result === "Success"){
							const updatedElement = $("#comment-body" + comment_no).text(newText);
				            inputElement.replaceWith(updatedElement);
				            loadComment(postNo);
						}else{
							inputElement.replaceWith(originalElement);
							alert("댓글 수정에 실패하였습니다.");
						}
					},
			        error: function () {
			            alert("서버 오류로 인해 수정에 실패했습니다.");
			            inputElement.replaceWith(originalElement);
			        }
				});
			}
			
			function commentDel(comment_no){
				$.ajax({
					url : "<%= request.getContextPath()%>/comment/delete.do",
					type : "post",
					data : {comment_no : comment_no},
					success : function(result){
						if(result === "Success"){
							loadComment(postNo);
						}else{
							alert("댓글 삭제에 실패하였습니다.");
						}
					}
				});
			}
			</script>
			<div class="comment-view">
			    <!-- 댓글 입력란 -->
			    <div class="comment-input-container">
			        <textarea id="comment-input" class="comment-input" name="comment_content"
			        placeholder="댓글을 입력하세요..." rows="4"></textarea>
			        <button class="comment-submit-btn" onclick="commentInsert(${vo.post_no},${vo.post_type});">등록</button>
			    </div>
			    <!-- 댓글 목록 출력 -->
			    <div class="comment-list">
			    </div>
			</div>
		</div>
	</section>
</body>
</html>