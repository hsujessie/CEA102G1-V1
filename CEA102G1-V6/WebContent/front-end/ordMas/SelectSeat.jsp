<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Front-End</title>
<%@ include file="/front-end/files/frontend_importCss.file"%>
<link href='https://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/css/selectSeat/jquery.seat-charts.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/css/selectSeat/style.css">

<style type="text/css">
/* 修改座位的顏色 */
div.seatCharts-seat {
	color: black;
	/* border: 1px solid black; */
}

div.seatCharts-seat.selected.first-class {
	background-color: cyan;
}

div.seatCharts-seat.available.first-class {
	color: black;
	/* background-color: white; */
}

div.seatCharts-seat.unavailable {
	background-color: red;
}

#seat-map {
	width: 100%;
}
/* div.front-indicator {
            width: 100%;
        } */


div.seatCharts-legend {
	position: static;
}

#nextStep {
	position: absolute;
	bottom: 1%;
	right: 5%;
}
</style>
</head>
<body>
        <div class="wrapper">
            <!-- Nav Bar Start -->
			<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set>
            <%@ include file="/front-end/files/frontend_navbar.file"%>
            <!-- Nav Bar End -->


            <!-- Page Header Start --> <!-- 看自己需不需要標題 -->
            
            <!-- Page Header End -->


            <!-- PUT HERE Start -->
            <div class="container">
		<div class="row">
			<div class="col-9">
				<div class="list-group">
					<div class="list-group-item">
						<div class="row">
							<jsp:useBean id="sesSvc" scope="page" class="com.session.model.SesService" />
							<jsp:useBean id="movSvc" scope="page" class="com.movie.model.MovService" />

							<div class="col-2">
								<div id="grade" class="text-center">
									<div id="grade-number"></div>
									<div id="grade-word">${movSvc.getOneMov(sesSvc.getOneSes(1).movNo).movrating}</div>
								</div>
							</div>
							<div class="col-7">
								<h3>(${movSvc.getOneMov(sesSvc.getOneSes(1).movNo).movver}${movSvc.getOneMov(sesSvc.getOneSes(1).movNo).movlan})${movSvc.getOneMov(sesSvc.getOneSes(1).movNo).movname}</h3>
							</div>
							<div class="col-3">
								<p>
									<img src="<%=request.getContextPath()%>/resource/images/ordMasIcons/sesTime.png"><span></span>${sesSvc.getOneSes(1).sesDate} ${sesSvc.getOneSes(1).sesTime}
								</p>
								<p>
									<img src="<%=request.getContextPath()%>/resource/images/ordMasIcons/theater.png"><span></span>第${sesSvc.getOneSes(1).theNo}廳
								</p>
							</div>
						</div>
					</div>
					<div class="list-group-item">
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
							<input type="hidden" name="chooseSeatNo" value="" id="chooseSeatNo">
							<input type="hidden" name="action" value="confirm_order">
						</form>
					</div>
				</div>
			</div>

			<div class="col-3">
				<div class="card border-primary mb-3">
					<div class="card-header">會員專區</div>
					<div class="card-body">XXX 你好</div>
				</div>

				<div class="card border-primary mb-3">
					<div class="card-header">購物清單</div>
					<div class="card-body">
						<table class="table">
							<tbody>
								<jsp:useBean id="ideSvc" scope="page" class="com.identity.model.IdentityService" />
								<c:forEach var="ticTypCartVO" items="${ticTypCartSet}">
									<tr>
										<td>
											<p>${ideSvc.getOneDept(ticTypCartVO.ideNo).ide_name}</p>
											<p class="text-right">
												X
												<span>${ticTypCartVO.ticLisPrice}</span>
											</p>
										</td>
									</tr>
								</c:forEach>
								<c:forEach var="fooCartVO" items="${fooCartSet}">
									<tr>
										<td>
											<p>${fooCartVO.fooName}</p>
											<p class="text-right">
												X
												<span>${fooCartVO.fooCount}</span>
											</p>
										</td>
									</tr>
								</c:forEach>
								<tr>
									<td>
										<p class="text-right">
											合計
											<span id="orderTotal">0</span>
										</p>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<button id="nextStep" class="btn btn-primary btn-lg">繼續</button>
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

// 		console.log(seatMap);
// 		console.log(rowNo);
// 		console.log(columnNo);

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
<script src="<%=request.getContextPath()%>/resource/js/selectSeat/jquery.seat-charts.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/resource/js/selectSeat/SelectSeatJS.js"></script>
</body>
</html>