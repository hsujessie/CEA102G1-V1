
		  var INDEX = 0; 
		  var memberImg = "<%=request.getContextPath()%>/Member/reader.do?memNo=${MemberVO.memNo}";
		  var admImg = "<%=request.getContextPath()%>/resource/images/clientAdm.png";
		  $("#chat-submit").click(sendMessage);
		  
		  function sendMsg() {
		    var msg = $("#chat-input").val(); 
		    if(msg.trim() == ''){
		      return false;
		    }
		    generate_message(msg, 'mem');
		  }
		  function generate_message(msg, type) {
		    INDEX++;
		    let img;
		    type === "mem" ? img = memberImg : img = admImg
		    var str="";
		    str += "<div id='cm-msg-"+INDEX+"' class=\"chat-msg "+type+"\">";
		    str += "          <span class=\"msg-avatar\">";
		    str += "            <img src=\"" + img + "\">";
		    str += "          <\/span>";
		    str += "          <div class=\"cm-msg-text\">";
		    str += msg;
		    str += "          <\/div>";
		    str += "        <\/div>";
		    $(".chat-logs").append(str);
		    $("#cm-msg-"+INDEX).hide().fadeIn(300);
		    $("#chat-input").val(''); 
		    $(".chat-logs").stop().animate({ scrollTop: $(".chat-logs")[0].scrollHeight}, 1000);    
		  }  
		  
		  $("#chat-circle").click(function() {  
			if (webSocket == null){
				connect();
			}
		    $("#chat-circle").toggle(500);
		    $(".chat-box").toggle(500);	
		  })
		  
		  $(".chat-box-toggle").click(function() {
		    $("#chat-circle").toggle(500);
		    $(".chat-box").toggle(500);
		  })
		 //WebSocket
        var MyPoint = "/ServiceWS/member/${MemberVO.memNo}";
		var host = window.location.host;
		var path = window.location.pathname;
		var webCtx = path.substring(0, path.indexOf('/', 1));
		var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
		var admNo;
		var admName;
		var webSocket = null;
	
		function connect() {
			// create a websocket
			webSocket = new WebSocket(endPointURL);
	
			webSocket.onopen = function(event) {
				console.log("Connect Success!");
				document.getElementById('chat-input').disabled = false;
			}
	
			webSocket.onmessage = function(event) {
				var jsonObj = JSON.parse(event.data);
				if ("open" === jsonObj.type) {
					admNo = jsonObj.admNo;
					admImg = "<%=request.getContextPath()%>/resource/images/clientAdm.png";
					admName = jsonObj.admName;
					let msg = jsonObj.message;
					generate_message(msg, "adm")
				} else if ("history" === jsonObj.type) {
					let memNo;
					if (jsonObj.sender.indexOf("mem") >= 0){
						memNo = jsonObj.sender;
					} else {
						memNo = jsonObj.receiver;
					}
					// 這行的jsonObj.message是從redis撈出與客戶的歷史訊息，再parse成JSON格式處理
					var messages = JSON.parse(jsonObj.message);
					for (var i = 0; i < messages.length; i++) {
						var historyData = JSON.parse(messages[i]);
						var msg = historyData.message;
						// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
						let type = "";
						historyData.sender.indexOf("mem") >= 0 ? type = 'mem' : type = 'adm';
						generate_message(msg, type)
					}
				} else if ("chat" === jsonObj.type) {
					let msg = jsonObj.message;
					let type;
					jsonObj.sender.indexOf("mem") >= 0 ? type = 'mem' : type = 'adm';
					generate_message(msg, type)
				} else if ("noAdminOnline" === jsonObj.type){
					let msg = jsonObj.message;
					generate_message(msg, "adm")
				}
				console.log("onMessage");
			};
	
			webSocket.onclose = function(event) {
				console.log("Disconnected!");
			};
		}
		
		function sendMessage() {
			var inputMessage = document.getElementById("chat-input");
			var memberID = "${MemberVO.memNo}";
			var memberName = "${MemberVO.memName}";
			var message = inputMessage.value.trim();
			if (message === "") {
				inputMessage.focus();
				return;
			} else {
				var jsonObj = {
					"type" : "chat",
					"sender" : "mem-" +memberID + "-" + memberName,
					"receiver" : "adm-" +admNo + "-" + admName,
					"message" : message,
				};
				webSocket.send(JSON.stringify(jsonObj));
				inputMessage.value = "";
				inputMessage.focus();
				console.log("sendMessage&admNo="+admNo);
			}
		}
		
		function disconnect() {
			if (webSocket != null) webSocket.close();
		}

