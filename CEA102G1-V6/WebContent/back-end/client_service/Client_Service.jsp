<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
      <link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/chatbox/cliSerForBac.css" >
</head>
<body>
<div class="wrapper">
            <div class="container">
                <div class="left">
                    <div class="top" id="chatArea">
                        <span class="name"><i class="far fa-user"></i>客服專員： -</span>
                    </div>
                    <div class="write">
                        <input type="text" id="sendMessage" onkeydown="if (event.keyCode == 13) sendMessage();" disabled />
                        <i class="fas fa-paper-plane"></i>
                    </div>
                </div>
                <div class="right">
                    <ul class="people" id="members">
                       
                    </ul>
                </div>
            </div>
        </div>
        
            <script>
        //WebSocket
        var MyPoint = "/serviceWS/admin/${admVO.admNo}";
		var host = window.location.host;
		var path = window.location.pathname;
		var webCtx = path.substring(0, path.indexOf('/', 1));
		var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
		var members;
		var chat;
		var webSocket;
		
		// 註冊列表點擊事件並抓取好友名字以取得歷史訊息

		
		
		function connect() {
			// create a websocket
			webSocket = new WebSocket(endPointURL);
	
			webSocket.onopen = function(event) {
				console.log("Connect Success!");
				document.getElementById('sendMessage').disabled = false;
			}
	
			webSocket.onmessage = function(event) {
				var jsonObj = JSON.parse(event.data);
				if ("open" === jsonObj.type) { //會員上線
					refreshCustomerList(jsonObj);
				} else if ("openAdm" === jsonObj.type) { //客服上線
					refreshCustomerList(jsonObj);
						members = {
				            list: document.querySelector("ul.people"),
				            all: document.querySelectorAll(".right .person"),
				            name: "",
				        },
				        chat = {
				            container: document.querySelector(".container .left"),
				            current: null,
				            person: null,
				            name: document.querySelector(".container .left .top .name"),
				        };
				    members.all.forEach((f) => {
				        f.addEventListener("mousedown", () => {
				            f.classList.contains("active") || setAciveChat(f);
				        });
				    });
					    
				} else if ("history" === jsonObj.type) {
					var messages = JSON.parse(jsonObj.message);
					let chatBox;
					let memNo;
					let admNo;
					for (var i = 0; i < messages.length; i++) {
						let msg = messages[i];
						let showMsg = msg.message;
						let sender = msg.sender;
						let receiver = msg.receiver;
						let div = document.createElement("div");
						let img = document.createElement("img");
						let span = document.createElement("span");
						
						div.classList.add("bubble");
						if (sender.indexOf("adm") >= 0){
							admNo = sender.split("-")[1];
							memNo = receiver.split("-")[1];
							div.classList.add("me");
							img.setAttribute("src", "pic---for---adm---!!!");
							img.classList.add("admpic");
							div.append(showMsg);
							div.append(img);
							div.append(span);
						} else {
							memNo = sender.split("-")[1];
							admNo = receiver.split("-")[1];
							div.classList.add("you");
							img.setAttribute("src","pic---for---mem---!!!");
							img.classList.add("mempic");
							div.append(span);
							div.append(img);
							div.append(showMsg);
						}
						chatBox = document.getElementById("chat-"+ memNo);
						// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
						chatBox.append(div);
						chatBox.scrollTop = chatBox.scrollHeight;
					}
					showNewestMsg(memNo);
				} else if ("chat" === jsonObj.type) {
					let showMsg = jsonObj.message;
					let div = document.createElement('div');
					let img = document.createElement("img");
					let span = document.createElement("span");
					div.classList.add("bubble");
					let admNo;
					let memNo;
					if (jsonObj.sender.indexOf("EMP") >= 0){
						admNo = jsonObj.sender.split("-")[0];
						memNo = jsonObj.receiver.split("-")[0];
						div.classList.add("me");
						img.setAttribute("src", "<%=request.getContextPath()%>/emp/emp.do?action=getEmpPic&emp_id=${empVO.emp_id}");
						img.classList.add("emppic");
						div.append(showMsg);
						div.append(img);
						div.append(span);
					} else {
						memNo = jsonObj.sender.split("-")[0];
						admNo = jsonObj.receiver.split("-")[0];
						div.classList.add("you");
						img.setAttribute("src", "<%=request.getContextPath()%>/MembersServlet?action=getMbPicForChat&mb_id=" + memNo);
						img.classList.add("mempic");
						div.append(span);
						div.append(img);
						div.append(showMsg);
					}
					let chatArea = document.getElementById("chat-"+ memNo);
					chatArea.append(div);
					chatArea.scrollTop = chatArea.scrollHeight;
					showNewestMsg(memNo);
				} else if ("close" === jsonObj.type) {
					refreshCustomerList(jsonObj);
					let closememNo = jsonObj.memNo;
					let chatArea = document.getElementById("chat-"+ closememNo);
					chatArea.remove();
				}
			};
	
			webSocket.onclose = function(event) {
				console.log("Disconnected!");
			};
		}
		
		function setAciveChat(f) {
			if (members.list.querySelector(".active") != null) {
				members.list.querySelector(".active").classList.remove("active");
			}
            f.classList.add("active");
            chat.current = chat.container.querySelector(".active-chat");
            chat.person = f.getAttribute("data-mbid");
            if (chat.current != null) chat.current.classList.remove("active-chat");
            chat.container.querySelector('[data-mbid="' + chat.person + '"]').classList.add("active-chat");
            members.name = f.querySelector(".name").innerText;
            chat.name.innerHTML = members.name;
        }
		
		function sendMessage() {
			var inputMessage = document.getElementById("sendMessage");
			var memNo = $(".person.active").attr("data-mbid");
			var memName = $(".person.active").attr("data-mbname");
			var message = inputMessage.value.trim();
			if (message === "") {
				inputMessage.focus();
				return;
			} else if (memNo === undefined) {
				return;
			} else {
				var jsonObj = {
					"type" : "chat",
					"sender" : "adm-${admVO.admNo}-${admVO.admName}",
					"receiver" : "mem-" + memNo + "-" + memName,
					"message" : message,
				};
				webSocket.send(JSON.stringify(jsonObj));
				inputMessage.value = "";
				inputMessage.focus();
			}
		}
		
		function showNewestMsg(memNo){
			let memberChats = $("#chat-"+memNo).children(".you").last();
			let memberBox = $("#"+memNo).children(".preview");
			let unreadMsg = $("#"+memNo).children(".unread");
			let msg = memberChats.html().split(">").slice(-1)[0];
			if (msg.length > 20) {
				msg  = msg.slice(0, 20) + "..."
			} 
			if (!$("#"+memNo).hasClass("active")){
				if (unreadMsg.text() == "-1"){
					let number = parseInt(unreadMsg.text());
					unreadMsg.text(number+1);
				} else {
					unreadMsg.show();
					let number = parseInt(unreadMsg.text());
					unreadMsg.text(number+1);
				}
			}
			memberBox.text(msg);
		}
		
		// 有新的客戶上線或離線就更新列表
		function refreshCustomerList(jsonObj) {
			let memNos = jsonObj.memNos;
			let currentChatMember = $(".container .left .active-chat").eq(0).attr("id")
			let memberList = document.getElementById("members");
			let chatArea = document.getElementById("chatArea");
			memberList.innerHTML = '';
			for (var i = 0; i < memNos.length; i++) {
				let memNo = memNos[i];
				var memName = "";
				$.ajax({
					url: "<%=request.getContextPath()%>/MembersServlet?action=ajaxGetmemName",
					data: {
						mb_id: memNo,
					},
					type: "POST",
					success: function(msg){
						memName = msg;
						memberList.innerHTML += 
							`
							<li class="person" id="\${memNo}" data-mbid="\${memNo}" data-mbname="\${memName}">
		                            <img src="pic---for---mem---!!!" alt="" />
		                            <span class="name">\${memName}</span>
		                            <span class="preview"></span>
		                            <span class="unread">-1</span>
		                    </li>
							`;
						var div = document.createElement("div");
						div.classList.add("chat");
						div.setAttribute("id", "chat-" + memNo);
						chatArea.after(div);
						let aMember = memNo + "-" + memName;
						$("#" + memNo).children(".unread").hide();
						let jsonObj = {
								"type" : "history",
								"sender" : "${empVO.emp_id}-${empVO.emp_name}",
								"receiver" : aMember,
								"message" : ""
							};
						webSocket.send(JSON.stringify(jsonObj));
						if (currentChatMember != null) {
							let id = currentChatMember.split("-")[1];
							$("#" + id).addClass("active");
						}
					}
				})
				
			}
		}
		
		function disconnect() {
			webSocket.close();
			document.getElementById('sendMessage').disabled = true;
		}
    </script>
</body>
</html>