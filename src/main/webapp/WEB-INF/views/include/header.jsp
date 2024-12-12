<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>커뮤니티</title>
<link href="<%= request.getContextPath()%>/resources/css/header.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/chat.css" />
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/login.css" />
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/board.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<script src="<%= request.getContextPath() %>/resources/js/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css"><!-- jQuery UI CSS -->
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script><!-- jQuery UI JavaScript -->
<script>
let user_name = "";
let user_id = "";
const chatRoomList = $("#chatRoomList");
window.onload = function(){
	user_name = "${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.user_name}";
	user_id = "${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.user_id}";
	console.log("user_name :" + user_name);
	console.log("user_id :" + user_id);
	
	$("#chatRoomList").css("display","none");
	
	
	// 모달 열기
	$('#loginBtn').click(function (e) {
	    $('#loginModal').fadeIn();
	});

	// 모달 닫기
	$(window).click(function (event) {
		if ($(event.target).is("#loginModal")) {
            $("#loginModal").fadeOut();
		}
	});
}


$(document).on('keyup', '#user_search', function() {
	let search_value = $(this).val();
    if(search_value.length > 0) {
        $.ajax({
            url: '<%= request.getContextPath() %>/chat/searchUsers.do',
            type: 'GET',
            data: { search_value: search_value },
            success: function(data) {
                console.log('응답 데이터:', data);
            	$('#userList').empty();
                if(data.length === 0) {
                    $('#userList').append('<li>검색 결과가 없습니다.</li>');
                }else {
                    data.forEach(function(item) {
                        console.log("item user_id" + item.user_id);
                        $("#userList").show();
                        $('#userList').append(`<li><input type="checkbox" value="\${item.user_id}:\${item.user_name}">`+ item.user_name + ` - ` + item.department_name + ` - ` + item.job_position_name + `</li>`);
                    });
                }
            },
            error: function(xhr, status, error) {
                console.error('AJAX Error:', error);
            }
        });
    }
});

function closeModal() {
    $("#modal").fadeOut(); // 모달 숨기기
}

function chatModal(){
	if(user_id === ""){
		alert("로그인 후 이용가능합니다.");
		return;
	}
    $("#modal").fadeIn(); // 모달 창 보이게 하기
    $.ajax({
        url: "<%= request.getContextPath() %>/chat/chat.do",
        type: "GET",
        success: function(data) {
        	let html = `
       		<div class="modalHeader">
		        <h2>채팅</h2>
		        <button class="closeBtn" onclick="closeModal();">X</button>
		    </div>
    		<div id="chatContainer">
    			<div id="chatContainerHeader">
	                <button id="createChatButton">채팅방생성</button>
		            <form action="<%= request.getContextPath() %>/chat/chat.do" method="get" name="searchFn">
	                    <div id="search_container" style="display: flex; margin-top: 1.5%; 
								 align-items: center; border: 1px solid #ccc;
								 background-color: white; border-radius: 25px; width:98%; height: 30px;">
	      	   				  <i class="fas fa-magnifying-glass search-icon" style="margin-left:10px;"></i>
		                      <input type="text" name="search_value" id="search_input" placeholder="채팅방검색"
		                      style="border:none; width:85%; height:auto; margin-left:5px;">
		                      <i class="fas fa-times" id="clearBtn"></i>
	                    </div>
		            </form>
	            </div>
	            <div id="chatSidebar">
	                <ul id="chatRoomList">`;
	                
        	for(item of data.list){
        		console.log("item chat_no" + item.chat_no);
        		$("#chatRoomList").show();
				html += `
						<li onclick="chatRoomView(\${item.chat_no},'\${item.chat_name}');">
							<div>
								\${item.chat_name}
						 	</div>
						</li>`;
			}
        	
        	html += `</ul>
	            </div>
        	</div>
        	`;
            $("#modalContent").html(html);
			
            
        }
    });
    
    $("#modalContent").draggable({
        handle: ".modalHeader", // 헤더 부분만 드래그 가능하도록 설정
    });
    
}


//채팅방 생성 슬라이더 열기
$(document).on("click", "#createChatButton", function () {
    $("#slider").fadeIn(); // 슬라이더 표시
    $("#slider").draggable({
        handle: ".modalHeader", // 헤더 부분만 드래그 가능하도록 설정
    });
});

// 채팅방 생성 슬라이더 닫기
function closeSlider() {
    $("#slider").fadeOut(); // 슬라이더 숨김
    $("#user_search").val('');
    $("#userList").empty();
    $("#userList").toggle();
}

$(document).on("click", "#completeChatButton", function () {
	let selectedUsers = [];
    let chatNames = [];
    
    $('#userList input[type="checkbox"]:checked').each(function () {
        const value = $(this).val();  // value는 "user_id:user_name" 형태
        selectedUsers.push(value.split(":")[0]);  // user_id만 users 배열에 추가
        chatNames.push(value.split(":")[1]);  // user_name만 채팅방 이름 생성에 사용
    });

    if(selectedUsers.length > 0) {
        const chat_name = chatNames.join(', ');
        console.log('생성된 채팅방 이름:', chat_name);
    
	    if(chat_name && selectedUsers.length > 0) {
	        $.ajax({
	            url: "chat/create.do",
	            method: "POST",
	            contentType: "application/json; charset=utf-8",
	            data: JSON.stringify({user_id: user_id, chat_name: chat_name, users: selectedUsers}),
	            success: function (result) {
	                result.trim();
	                console.log("result : " + result);
	                if(result === '0'){
	                	alert("채팅방 생성실패");
	                }else{
	                	let chat = result;
	                	let parts = chat.split(":");
	
	                	let chat_no = parts[0];   // 왼쪽 부분
	                	let chat_name = parts[1]; // 오른쪽 부분
	                	console.log("채팅방 생성 후 chatModal()실행전 chat_no" + chat_no);
	                	console.log("채팅방 생성 후 chatModal()실행전 chat_name" + chat_name);
	                	closeSlider();
	                	chatRoomView(chat_no,chat_name);
	                	chatModal();
	                }
	            },
	            error: function () {
	                console.error("채팅방 생성 실패");
	            },
	        });
	    }	
    }else {
        alert("채팅에 초대할 직원을 선택해주세요.");
    }
});


function chatRoomView(chat_no,chat_name){
	chatUser(chat_no);
	const modalId = 'chatModal_' + chat_no;
	console.log("modalId :"+modalId);
	
	if($('#' + modalId).length === 0) {
        const newModalContent = `
            <div class="chatModal" id="\${modalId}">
                <div class="chatModalHeader">
                    <button class="hamburgerMenu" onclick="toggleChatMenu('\${modalId}')">☰</button>
                    <h2>\${chat_name}</h2>
                   	<button class="closeBtn" onclick="closeChatModal('\${modalId}')">X</button>
                </div>
                <div class="chatContent">
	                <div id="chatLog_\${chat_no}" class="chatLog wrap">
	              		<!-- 기존 채팅내용. 추가될 채팅내용 -->
	                </div>
	                <input type="text" class="messageInput" id="messageInput_\${chat_no}" 
	               	onkeydown="if(event.key === 'Enter') sendMessage(\${chat_no});"
	                style="width: 92%;" placeholder="메시지를 입력하세요" />
                </div>
                <div class="chatSidebar" id="chatSidebar_\${modalId}">
	                <h3>채팅초대</h3>
	                <ul id="participantList_\${chat_no}">
	                    <!-- 참가자 목록 -->
	                </ul>
	                <button class="leaveChatBtn" onclick="leaveChatRoom(\${chat_no},'\${user_id}')">나가기</button>
	            </div>
            </div>
        `;
		//console.log(newModalContent);
        // 새로운 모달을 화면에 추가
        $("body").append(newModalContent);
		
        $("#" + modalId).show();
        
        // 모달을 드래그 가능하게 설정
        $("#" + modalId).draggable({
            handle: ".chatModalHeader"  // 헤더를 드래그 가능한 영역으로 설정
        });

        // 모달 CSS
        $("#" + modalId).css({
            position: 'absolute',
            top: '50px', // 화면 상단에서 50px 떨어진 곳에 모달이 보이도록 설정
            left: '150px', // 화면 왼쪽에서 150px 떨어진 곳에 모달이 보이도록 설정
            width: '300px',
            height: '400px',
            background: '#fff',
            border: '1px solid #ccc',
            boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
            zIndex: 9999
        });
        $("#chatSidebar_" + modalId).hide();
    }
}

//햄버거 메뉴 토글 기능
function toggleChatMenu(modalId) {
    const sidebar = $("#chatSidebar_" + modalId);
    sidebar.toggle("slide", { direction: "right" }, 300); // 오른쪽으로 슬라이드 토글
}

//채팅방 모달을 닫는 함수
function closeChatModal(modalId) {
    $("#" + modalId).remove();  
}

let user_list = "";
function chatUser(chat_no){
	$.ajax({
		url : "<%= request.getContextPath() %>/chat/chatUsers.do",
		type: 'post',
		data : {chat_no : chat_no},
		success : function(data){
			for(item of data){
				console.log(item.user_name);
				user_list += "<li>" + item.user_name + "</li>";
			}
			$("#participantList_"+chat_no).append(user_list);
			user_list = "";
		}
	});
}

//채팅방 나가기 기능
function leaveChatRoom(chat_no,user_id) {
    $.ajax({
        url: "<%= request.getContextPath() %>/chat/leaveChatRoom.do",
        type: "POST",
        data: { chat_no : chat_no, user_id : user_id },
        success: function(response) {
            if(response === 'Success'){
	            chatModal();
	            $('#chatModal_' + chat_no).remove(); // 모달 닫기
            }else{
            	alert("채팅방 나가기에 실패했습니다.");
            }
        },
        error: function() {
            alert("채팅방 나가기에 실패했습니다.");
        }
    });
}
</script>
</head>
<body>
	<!-- 로그인 X -->
	<sec:authorize access="isAnonymous()">
		<div class="main_container" style="text-align: center;">
            <img src="<%= request.getContextPath() %>/resources/img/메인.jpg" alt="메인 이미지" 
            style="width: 100%; height: 820px;">
            <div class="log_Info" style="font-size: 18px; text-decoration: none; color: white; font-weight: bold; margin-top: 20px;">
                <a href="join.do" style="color:white;">회원가입</a> | 
                <a id="loginBtn" style="color:white;">로그인</a><br>
            </div>
        </div>
        
        <!-- 로그인 모달 -->
	    <div id="loginModal" class="modal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5);">
	        <div class="login_Modal_content" 
	        style="width: 350px; height: 350px; margin: 10% auto; padding: 20px; background-color: white; border-radius: 10px;">
	        	<h2 style="text-align: center;">로그인</h2>
				<form action="loginOk.do" method="post">
					<table>
						<tr>
							<td>
								<div class="user_container">
		                            <i class="fas fa-user"></i>
		                            <input type="text" name="user_id" placeholder="아이디">
		                        </div>
	                        </td>
						</tr>
						<tr>
							<td>
								<div class="user_container">
		                            <i class="fas fa-lock"></i>
		                            <input type="password" name="user_password" placeholder="비밀번호">
		                        </div>
							</td>
						</tr>
					</table>
					<br>
					<button class="login_modal_btn">로그인</button>
					<button type="button" class="login_modal_btn">비밀번호 찾기</button>
				</form>
	        </div>
	    </div>
	</sec:authorize>
	<sec:authorize access="isAuthenticated()"><!-- 로그인 O -->
		<div id="header1">
			<a href="<%= request.getContextPath() %>">
				<img id="logo_img"  src="<%= request.getContextPath() %>/resources/img/logo.png" alt="회사로고" >
			</a>
			<div id = "login_info">
				<a href="logout.do">로그아웃</a>
				|
				<a href="<%= request.getContextPath() %>/mypage/info.do">마이페이지</a>
			</div>
		</div>
		<hr>
		<div id="header2">
			<div id="bar">
				<div id="working_info_bar">
					<div id="working_info">근태정보</div><br>
					<div id="working_info">2024-12-05 11:51</div><br>
					<div id="working_info">출근시각 : </div><br>
					<div id="working_info">퇴근시각 : </div>
					<div id="alarm"><img id="alarm_icon" src="<%= request.getContextPath() %>/resources/img/icon/alarm.png" alt="알림"></div>
				</div>
				<div id="menu_bar">
					<div id="menu"><img id="menu_icon" src="<%= request.getContextPath() %>/resources/img/icon/groups.png" alt="조직도"></div>
					<div id="menu"><img id="menu_icon" src="<%= request.getContextPath() %>/resources/img/icon/calendar.png" alt="근태"></div>
					<div id="menu" style="cursor: pointer;" onclick="chatModal();"><img id="menu_icon" src="<%= request.getContextPath() %>/resources/img/icon/talk.png" alt="대화"></div>
					<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
						<div id="menu" style="cursor: pointer;" onclick="location.href='<%= request.getContextPath() %>/admin/list.do'"><img id="menu_icon" src="<%= request.getContextPath() %>/resources/img/icon/setting.png" alt="관리자"></div>
					</sec:authorize>
				</div>
			</div>
			<div id="member">
				<img id="member_img" src="<%= request.getContextPath() %>/resources/img/member1.jpeg" alt="회사원1">
				<div id="member_name">JJ417976 홍길자</div>
			</div>
		</div>
		<hr>
		<div id="modal">
			<div id="modalContent">
			    <div class="modalBody">
			        <!-- 채팅창 모달 -->
			    </div>
			</div>
		</div>
		<div id="slider" class="hidden">
		    <div class="modalHeader">
		        <h2>채팅방 참가자 검색</h2>
		        <button class="closeBtn" onclick="closeSlider()">X</button>
		    </div>
		    <input type="text" name="search_value" id="user_search" placeholder="이름, 부서, 직책으로 검색하세요">
		    <ul id="userList" style="display:none;"></ul>
		    <button id="completeChatButton">완료</button>
		</div>
	</sec:authorize>