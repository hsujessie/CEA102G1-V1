<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Front-End</title>
<%@ include file="/front-end/files/frontend_importCss.file"%>
<link href='https://fonts.googleapis.com/css?family=Lato:400,700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resource/css/selectSeat/jquery.seat-charts.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resource/css/selectSeat/style.css">

<style type="text/css">
/* 修改座位的顏色 */
div.seatCharts-seat {
	color: black;
	/* border: 1px solid black; */
}

div.seatCharts-seat.selected.first-class {
	color: white;
	background-color: #7e6946;
}

div.seatCharts-seat.focused {
	color: white;
	background-color: #7e6946;
}

div.seatCharts-seat.available.first-class {
	background-color: #cec0a7;
	color: black;
	/* background-color: white; */
}

div.seatCharts-seat.unavailable {
	background-color: red;
}

#seat-map {
	width: 100%;
	border: 0;
}
/* div.front-indicator {
            width: 100%;
        } */
div.seatCharts-legend {
	position: static;
}

div>p>span {
	margin: 0 3px;
}

div>p>img {
	width: 30px;
	height: 40px;
}

#nextStep {
	position: absolute;
	bottom: 1%;
	right: 5%;
}

#grade {
	border: 1px solid black;
	height: 100px;
	width: 100px;
}

#grade-number {
	color: white;
}

#grade-number, #grade-word {
	padding-top: 15px;
	height: 50px;
}

.list-group-item, #tabs_item, .card {
	border: 1px solid #aa9166;
	margin-bottom: 10px;
}

.info {
	border: 0;
	background-color: #b39d76;
}

#table-secondary {
	background-color: #c5b497;
}

#tabs>.ui-widget-header {
	background-color: #d7ccb8;
}

#tabs>ul>li {
	background-color: #c5b497;
	border: 1px solid #b39d76;
}

.ui-widget.ui-widget-content, .ui-widget-content {
	border: 0;
}

.card-header {
	background-color: #c5b497;
}

.list-group-item+.list-group-item {
	border: 1px solid #aa9166;
}

ul.seatCharts-legendList {
	text-align: center;
}

li.seatCharts-legendItem {
	display: inline-block;
}

.front-indicator {
	padding: 0px;
	width: 608px;
}

.swal-button {
	background-color: #121518;
}

p {
	margin-bottom: 0.5rem;
}
</style>
</head>
<body>

	<div class="wrapper">
		<!-- Nav Bar Start -->
		<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set>
		<%@ include file="/front-end/files/frontend_navbar.file"%>
		<!-- Nav Bar End -->


		<!-- Page Header Start -->
		<!-- 看自己需不需要標題 -->

		<!-- Page Header End -->


		<!-- PUT HERE Start -->
		<div class="container">
			<div class="row">
				<div class="col-9">
					<div class="list-group">
						<div class="list-group-item">
							<div class="row">
								<jsp:useBean id="sesSvc" scope="request" class="com.session.model.SesService" />
								<jsp:useBean id="movSvc" scope="request" class="com.movie.model.MovService" />
								<c:set var="sesVO" value="${sesSvc.getOneSes(param.sesNo)}" scope="request" />
								<c:set var="movVO" value="${movSvc.getOneMov(sesVO.movNo)}" scope="request" />
								<div class="col-2">
									<div id="grade" class="text-center">
										<div id="grade-number"></div>
										<div id="grade-word">${movVO.movrating}</div>
									</div>
								</div>
								<div class="col-7">
									<h3>(${movVO.movver}${movVO.movlan})${movVO.movname}</h3>
								</div>
								<div class="col-3">
									<p>
										<img src="<%=request.getContextPath()%>/resource/images/ordMasIcons/sesTime.png">
										<span></span> ${sesVO.sesDate} <fmt:formatDate value="${sesVO.sesTime}" pattern="HH:mm" />
									</p>
									<p>
										<img src="<%=request.getContextPath()%>/resource/images/ordMasIcons/theater.png"><span></span>第${sesVO.theNo}廳
									</p>
								</div>
							</div>
						</div>
						<div class="list-group-item info">
							<div class="row">
								<div class="col text-center">
									<h2 class="title">選擇座位</h2>
									<p>選擇您希望的購買的座位, 每筆交易最多可購買10張電影票</p>
								</div>
							</div>
						</div>
						<div class="list-group-item">
							<form method="post" action="<%=request.getContextPath()%>/ordMas/ordMas.do" id="form">
								<div class="row">
									<div class="col">
										<div id="seat-map">
											<div id="legend"></div>
											<div class="front-indicator">螢幕</div>
										</div>
									</div>
								</div>
								<input type="hidden" name="sesNo" value="${param.sesNo}">
								<input type="hidden" name="ticTypTotal" value="${ticTypTotal}">
								<input type="hidden" name="chooseSeatNo" value="" id="chooseSeatNo"> 
								<input type="hidden" name="action" value="confirm_order">
							</form>
						</div>
					</div>
				</div>

				<div class="col-3">
					<div class="card mb-3">
						<div class="card-header">會員專區</div>
						<div class="card-body">嗨!${MemberVO.memName} 您好</div>
					</div>

					<div class="card mb-3">
						<div class="card-header">購物清單</div>
						<div class="card-body">
							<table class="table">
								<tbody>
									<jsp:useBean id="ideSvc" scope="request" class="com.identity.model.IdeService" />
									<c:forEach var="ticTypCartVO" items="${ticTypCartSet}">
										<c:if test="${check != ticTypCartVO.ideNo}">
											<tr class="orderlist">
												<td>
													<p>${ideSvc.getOneDept(ticTypCartVO.ideNo).ide_name}</p>
													<p class="text-right">
														$ 
														<span>${ticTypCartVO.ticLisPrice}</span> 
														X
														<span>${ticTypCartVO.ticTypCount}</span>
														= 
														<span class="subtotal"></span>
													</p>
												</td>
											</tr>
										</c:if>
										<c:set var="check" value="${ticTypCartVO.ideNo}"/>
									</c:forEach>
									<c:forEach var="fooCartVO" items="${fooCartSet}">
										<tr class="orderlist">
											<td>
												<p>${fooCartVO.fooName}</p>
												<p class="text-right">
													$ 
													<span>${fooCartVO.fooPrice}</span> 
													X 
													<span>${fooCartVO.fooCount}</span>
													= 
													<span class="subtotal"></span>
												</p>
											</td>
										</tr>
									</c:forEach>
									<tr>
										<td>
											<p class="text-right">
												合計 <span id="orderTotal">0</span>
											</p>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<button id="nextStep" class="btn combtn btn-lg" disabled>還有 ${ticTypTotal} 個座位待劃位</button>
				</div>
			</div>
		</div>
		<!-- PUT HERE End -->

		<!-- Book Tickets Start -->
		<%--             <%@ include file="/front-end/files/frontend_bookTicketsTamplate.file"%> --%>
		<!-- Book Tickets End -->

		<!-- Footer Start -->
		<%@ include file="/front-end/files/frontend_footer.file"%>
		<!-- Footer End -->
	</div>
	<script>
		var ticTypTotal = ${ticTypTotal};
		var sesSeatStatus = "${sesSeatStatus}";
		var seatMap = [];
		var rowNo = [];
		var columnNo = [];
		for (let i = 0; i < 20; i++) {
			let begin = i * 20;
			let end = (i + 1) * 20;
			let str = sesSeatStatus.substring(begin, end);

			str = str.replace(new RegExp('9', 'g'), "_");
			str = str.replace(new RegExp('3', 'g'), "_");
			str = str.replace(new RegExp('0', 'g'), "t");
			str = str.replace(new RegExp('1', 'g'), "f");
			seatMap.push(str);
		}

		let count = 0;
		for (let i = 0; i < seatMap.length; i++) {
			if (seatMap[i] === "____________________") {
				rowNo.push("");
				continue;
			}
			count++;
			rowNo.push(count);
		}

		count = 0;
		out: for (let j = 0; j < seatMap.length; j++) {
			let str = "";
			for (let i = 0; i < seatMap.length; i++) {
				str = str + seatMap[i].charAt(j);
			}
			if (str === "____________________") {
				columnNo.push("");
				continue out;
			}
			count++;
			columnNo.push(count);
		}

		for (let i = 0; i < columnNo.length; i++) {
			if (columnNo[i] !== "" && columnNo[i] < 10) {
				columnNo[i] = "0" + columnNo[i];
			}
		}
		
		rowNo = replaceNo(rowNo);
		
		function replaceNo(arr) {
			let charArr = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
			let index = 0;
			for (let i = 0; i < arr.length; i++) {
				if (arr[i] !== "") {
					arr[i] = charArr[index];
					index++;
				}
			}
			return arr;
		}
		
	</script>

	<%@ include file="/front-end/files/frontend_importJs.file"%>
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resource/js/selectSeat/jquery.seat-charts.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resource/js/selectSeat/SelectSeatJS.js"></script>
	<script>
	$("#orderTotal").text(getTotalPrice());

    function getTotalPrice() {
        let list = $("tr.orderlist");
        let total = 0;
        for (let i = 0; i < list.length; i++) {
            let thisSubTotal = $(list[i]).find("span.subtotal");
            thisSubTotal.text(thisSubTotal.prev().text() * thisSubTotal.prev().prev().text());
            total = total + parseInt(thisSubTotal.text());
        }
        return total;
    };
    
    $(".seatCharts-seat").click(function() {
    	diff = ticTypTotal - sc.find('selected').length
    	if (diff === 0) {
    		$("#nextStep").text("確認訂單");
    		$("#nextStep").prop("disabled",false);
    	} else {
    		$("#nextStep").text("還有 "+ diff + "個座位待劃位");
    		$("#nextStep").prop("disabled", true);
    	}
    })
    
    $("#grade-number").text(getGradeNumber($("#grade-word").text()));

		function getGradeNumber(gradeWord) {
			if ("普遍級" === gradeWord) {
				$("#grade-number").css("background-color","#00A600");
				return "0+";
			} else if ("保護級" === gradeWord) {
				$("#grade-number").css("background-color","#2894FF");
				return "6+";
			} else if ("輔導級" === gradeWord) {
				$("#grade-number").css("background-color","#FFE153");
				return "12+";
			} else {
				$("#grade-number").css("background-color","#EA0000");
				return "18+";
			}
		}
    	
		if (${not empty errorMsgs}) {
			swal("位置已被選擇", "請重新選位", "error", {button: "關閉"});
		}
	</script>
</body>
</html>