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
let openChats = []; // 열린 채팅방 번호를 저장하는 배열
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
	
	/* 채팅방 검색창 글지우는 버튼 */
	$(document).on('keyup', '#search_input', function() {
		if($(this).val().length > 0){
			$("#clearBtn").css("display","inline");
		}else{
			$("#clearBtn").css("display","none");
		}
	});
	$(document).on('click', '#clearBtn', function() {
	    $("#search_input").val('');  
	    $(this).css("display","none");
	});
	
	/* 클릭할때 chat_no를 받아와서 배열에 담고, 해당 채팅창을 닫으면 배열에서 삭제. 매초마다 메시지내용 다시 불러오기 */
	setInterval(() => {
	    openChats.forEach((chat_no) => {
	        loadMessage(chat_no);
	    });
	}, 1000);
	 
	
	/* 채팅방목록 매초마다 불러오기. 마지막 채팅내용과 채팅시간에 필요 */
	setInterval(function(){
		$.ajax({
	        url: "<%= request.getContextPath() %>/chat/chat.do",
	        type: "GET",
	        success: function(data) {
	        	let html = ``;
	        	for(item of data.list){
					html += `
					<li onclick="chatRoomView(\${item.chat_no},'\${item.chat_name}');">
						<div class="chat_item">
							<div class="message_wrapper">
				          	  <div class="chat_name">\${item.chat_name}</div>`;
		            if(item.unread_count > 0) {
		           		html += `<div class="unread_count">\${item.unread_count}</div>`;
		            }else{
		            	html += `<div></div>`;
		            }
	            	html += `</div>
		          			<div class="last_message_wrapper">
				                <div class="last_message">\${item.chat_message_content || ""}</div>
				                <div class="last_message_time">\${item.chat_message_time || ""}</div>
				            </div>
				        </div>
					</li>`;
				}
	            $("#chatRoomList").html(html);
	        }
	    });
	},1000);
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

/* 채팅방 목록 출력되는 모달 닫기 */
function closeModal() {
    $("#modal").fadeOut(); // 모달 숨기기
}

/* 채팅방 목록 출력되는 모달 */
function chatModal(){
    $("#modal").fadeIn(); // 모달 창 보이게 하기
    
    $.ajax({
        url: "<%= request.getContextPath() %>/chat/chat.do",
        type: "GET",
        success: function(data) {
        	let html = `
       		<div class="modalHeader">
		        <h2>채팅</h2>
		        <button class="closeBtn" onclick="closeModal()">X</button>
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
				html += `
						<li onclick="chatRoomView(\${item.chat_no},'\${item.chat_name}');">
							<div class="chat_item">
								<div class="message_wrapper">
						            <div class="chat_name">\${item.chat_name}</div>`;
	            if(item.unread_count > 0) {
	           		html += `			<div class="unread_count">\${item.unread_count || ""}</div>`;
	            }else{
	            	html += `			<div>\${item.unread_count || ""}</div>`;
	            }
         		html += `	        </div>
					            <div class="last_message_wrapper">
					                <div class="last_message">\${item.chat_message_content || ""}</div>
					                <div class="last_message_time">\${item.chat_message_time || ""}</div>
					            </div>
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


/* 채팅방 생성 슬라이더 열기 */
$(document).on("click", "#createChatButton", function () {
    $("#slider").fadeIn(); // 슬라이더 표시
});

/* 채팅방 생성 슬라이더 닫기 */
function closeSlider() {
    $("#slider").fadeOut(); // 슬라이더 숨김
    $("#user_search").val('');
    $("#userList").empty();
    $("#userList").toggle();
}

/* 초대할 회원 체크하고 채팅방 생성 */
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

/* 개별채팅방 모달 만드는 함수 */
function chatRoomView(chat_no,chat_name){
	if(!openChats.includes(chat_no)) {
		openChats.push(chat_no); // 배열에 chat_no 추가
    }
	chatUser(chat_no);
	loadMessage(chat_no);
	connectWebSocket(chat_no);
	console.log("chatRoomView chat_no :" + chat_no);
	console.log("chatRoomView chat_name :" + chat_name);
	
	const modalId = 'chatModal_' + chat_no;
	console.log("modalId :"+modalId);
	
	if($('#' + modalId).length === 0) {
        const newModalContent = `
            <div class="chatModal" id="\${modalId}">
                <div class="chatModalHeader">
                    <button class="hamburgerMenu" onclick="toggleChatMenu('\${modalId}')">☰</button>
                    <h2>\${chat_name}</h2>
                   	<button class="closeBtn" onclick="closeChatModal('\${chat_no}')">X</button>
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

        // 모달 스타일링 (CSS를 통해 채팅방 모달의 위치 및 크기 설정)
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
        /* setTimeout없이 동작시킬 경우 스크롤이 안먹힘 */
        setTimeout(function(){
        	$("#chatLog_"+chat_no).scrollTop($("#chatLog_"+chat_no)[0].scrollHeight);
        },100);
        
    }
}

/* 햄버거 메뉴 토글 기능 */
function toggleChatMenu(modalId) {
    const sidebar = $("#chatSidebar_" + modalId);
    sidebar.toggle("slide", { direction: "right" }, 300); // 오른쪽으로 슬라이드 토글
}

/* 채팅방 모달을 닫는 함수 */
function closeChatModal(chat_no) {
	const index = openChats.indexOf(chat_no);
    if(index !== -1) {
    	openChats.splice(index, 1); // 배열에서 chat_no 삭제
        const socket = chatWebSockets[chat_no];
        if(socket && socket.readyState === WebSocket.OPEN) {
            socket.close(); // WebSocket 연결 종료
        }
        delete chatWebSockets[chat_no]; // WebSocket 객체 삭제
        console.log("채팅방 닫힘: ", openChats);
    }
    $("#chatModal_" + chat_no).remove();  
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

/* 채팅방 웹소켓 */
let socket;
const chatWebSockets = {}; // 채팅방 WebSocket 저장 객체
function connectWebSocket(chat_no) {
    // 채팅방 번호에 따라 WebSocket을 생성
    if(!chatWebSockets[chat_no]) {
    	/* 시연용 */
        //const socket = new WebSocket("ws://192.168.0.175:8080/community/chat"); 
        //각자 컴퓨터에서 돌릴용
        const socket = new WebSocket("ws://localhost:8080/community/chat"); 

        socket.onopen = function () {
            console.log(`WebSocket 연결 성공: 채팅방 \${chat_no}`);
        };

        socket.onmessage = function (event) {
            const chatLog = document.getElementById("chatLog_" + chat_no);

            // 서버에서 보낸 메시지(JSON 형식)를 파싱
            const messageData = JSON.parse(event.data);
            
            // 오늘 날짜를 '연도.월.일' 형식으로 가져오기
            const currentDate = new Date();
            const todayDate = `\${currentDate.getFullYear()}.\${(currentDate.getMonth() + 1).toString().padStart(2, '0')}.\${currentDate.getDate().toString().padStart(2, '0')}`;

            // 채팅 로그에 오늘 날짜의 chatDate가 이미 있는지 확인
            const existingDateElement = chatLog.querySelector(`.chatDate[data-date="\${todayDate}"]`);

            if(!existingDateElement) {
                // 오늘 날짜가 없다면 chatDate 추가
                const dateHTML = `
                    <div class="chatDate" data-date="\${todayDate}">
                        \${todayDate}
                    </div>
                `;
                chatLog.insertAdjacentHTML('beforeend', dateHTML);
            }

            // 현재 시간을 '시:분' 형식으로 가져오기
            const formattedTime = `\${currentDate.getHours()}:\${currentDate.getMinutes().toString().padStart(2, '0')}`;


            // 현재 로그인한 사용자인지 확인
            const isCurrentUser = messageData.user_name === user_name;

            // 동적으로 스타일 클래스 지정. ch1 다른채팅유저(왼쪽), ch2 로그인한 유저(오른쪽)
            const messageClass = isCurrentUser ? "chat ch2" : "chat ch1";

            // DOM에 메시지 추가
            const messageHTML = isCurrentUser
	        ? `
	            <div class="\${messageClass}">
	                <div class="messageContent textbox">\${messageData.chat_message_content}</div>
	                <div class="messageDate_ch2">\${formattedTime}</div>
	            </div>
	        `
	        : `
                <div class="messageUser">\${messageData.user_name}</div>
	            <div class="\${messageClass}">
	                <div class="messageContent textbox">\${messageData.chat_message_content}</div>
	                <div class="messageDate_ch1">\${formattedTime}</div>
	            </div>
	        `;

		    // DOM에 메시지 추가
		    chatLog.insertAdjacentHTML('beforeend', messageHTML);

            // 스크롤을 맨 아래로 유지
            chatLog.scrollTop = chatLog.scrollHeight;
        };

        socket.onclose = function () {
            console.log(`WebSocket 연결 종료: 채팅방\${chat_no}`);
        };

        chatWebSockets[chat_no] = socket;
    }
}
/* 채팅메시지 DB에 저장 */
function sendMessage(chat_no) {
    const input = document.getElementById("messageInput_" + chat_no);
    const chat_message_content = input.value.trim();

    if (chat_message_content && chatWebSockets[chat_no]?.readyState === WebSocket.OPEN) {
        const payload = JSON.stringify({
            user_name: user_name, 
            chat_message_content: chat_message_content,
        });

        chatWebSockets[chat_no].send(payload); // 서버로 메시지 전송
        
        // 메시지를 서버에 저장
        $.ajax({
            url: '<%= request.getContextPath() %>/chat/sendMessage.do',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ chat_no, user_id, chat_message_content }),
            success: function() {
                console.log('메시지가 저장되었습니다.');
            },
            error: function() {
                console.error('메시지 저장 중 오류 발생');
            }
        });

        input.value = ""; // 입력 필드 초기화
    } else {
        console.log(`WebSocket이 열려 있지 않음: 채팅방 \${chat_no}`);
    }
}

/* 기존 채팅메시지 불러오기 */
function loadMessage(chat_no){
	$.ajax({
		url : "<%= request.getContextPath() %>/chat/loadMessage.do",
		type : "get",
		data : {chat_no : chat_no},
		success : function(data){
			let html = "";
			
			const currentDate = new Date();
            const todayDate = `\${currentDate.getFullYear()}.\${(currentDate.getMonth() + 1)
                .toString()
                .padStart(2, "0")}.\${currentDate.getDate().toString().padStart(2, "0")}`;

            let chat_date = ""; // 현재 DOM에 표시 중인 날짜

            for(let item of data) {
                if(item.chat_message_date !== chat_date) {
                	chat_date = item.chat_message_date;
                    // 날짜 구분 추가
                    html += `
                        <div class="chatDate" data-date="\${chat_date}">
                            \${chat_date}
                        </div>
                    `;
                }
                
	            // 현재 로그인한 사용자인지 확인
	            let isCurrentUser = item.user_id === user_id;

	            // 동적으로 스타일 클래스 지정
	            let messageClass = isCurrentUser ? "chat ch2" : "chat ch1";

	            // DOM에 메시지 추가
	            html += isCurrentUser
		        ? `
		            <div class="\${messageClass}">
		                <div class="messageContent textbox">\${item.chat_message_content}</div>
		                <div class="messageDate_ch2">\${item.chat_message_time}</div>
		            </div>
		        `
		        : `
	                <div class="messageUser">\${item.user_name}</div>
		            <div class="\${messageClass}">
		                <div class="messageContent textbox">\${item.chat_message_content}</div>
		                <div class="messageDate_ch1">\${item.chat_message_time}</div>
		            </div>
		        `;
			}
			
			$("#chatLog_"+chat_no).html(html);
		},
	    error: function(xhr, status, error) {
	        console.error("Error:", status, error);
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
		
		<table class="menu_table">
			<tr>
				<th><a href="<%=request.getContextPath() %>/notice/list.do">공지사항</a></th>
				<th><a href="<%=request.getContextPath() %>/board/list.do">사내 커뮤니티</a></th>
				<th><a href="">나의 부서 업무 상황</a></th>
			</tr>
		</table>
	</sec:authorize>