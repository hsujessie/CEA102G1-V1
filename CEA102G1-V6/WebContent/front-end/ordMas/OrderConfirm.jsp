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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resource/css/creditCard/card.css">
<style>
#nextStep {
	position: absolute;
	bottom: 1%;
	right: 5%;
}

div>p>img {
	width: 30px;
	height: 40px;
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

div>p>span {
	margin: 0 3px;
}

.info {
	border: 0;
	background-color: #b39d76;
}

.jp-card-front {
	background-color: black !important;
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
				<div id="a" class="col-9">
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
										<img src="<%=request.getContextPath()%>/resource/images/ordMasIcons/theater.png">
										<span></span>第${sesVO.theNo}廳
									</p>
									<p>
										<img src="<%=request.getContextPath()%>/resource/images/ordMasIcons/seatNo.png">
										<span></span><span id="chooseSeatNo"></span>
									</p>
								</div>
							</div>
						</div>
						<div class="list-group-item info">
							<div class="row">
								<div class="col text-center">
									<h2 class="title">確認訂單內容</h2>
									<p>請再次確認所選座位以及以下商品名稱、價格、數量</p>
								</div>
							</div>
						</div>

						<div class="list-group-item">
							<form method="post" action="<%=request.getContextPath()%>/ordMas/ordMas.do" id="form">
								<table class="table">
									<thead>
										<tr id="table-secondary">
											<th>商品</th>
											<th>價格</th>
											<th>數量</th>
											<th>合計</th>
										</tr>
									</thead>
									<tbody>
									
										<jsp:useBean id="theSvc" scope="request" class="com.theater.model.TheService" />
										<jsp:useBean id="ticTypSvc" scope="request" class="com.ticket_type.model.TicTypService" />
										<jsp:useBean id="ideSvc" scope="request" class="com.identity.model.IdeService" />

										<c:forEach var="ticTypCartVO" items="${ticTypCartSet}">
											<c:if test="${check != ticTypCartVO.ideNo}">
												<tr>
													<td>${ideSvc.getOneDept(ticTypCartVO.ideNo).ide_name}</td>
													<td>$<span>${ticTypCartVO.ticLisPrice}</span></td>
													<td>${ticTypCartVO.ticTypCount}</td>
													<td class="subtotal">${ticTypCartVO.ticLisPrice * ticTypCartVO.ticTypCount}</td>
												</tr>
											</c:if>
											<c:set var="check" value="${ticTypCartVO.ideNo}" />
										</c:forEach>

										<c:forEach var="fooCartVO" items="${fooCartSet}">
											<tr>
												<td>${fooCartVO.fooName}</td>
												<td>$ <span>${fooCartVO.fooPrice}</span>
												</td>
												<td>${fooCartVO.fooCount}</td>
												<td class="subtotal">${fooCartVO.fooPrice * fooCartVO.fooCount}</td>
											</tr>
										</c:forEach>

										<tr>
											<th>總額</th>
											<td></td>
											<td></td>
											<td id="totalPrice"></td>
										</tr>
									</tbody>
								</table>

								<input type="hidden" name="memNo" value="${MemberVO.memNo}">
								<input type="hidden" name="sesNo" value="${param.sesNo}">
								<input type="hidden" name="chooseSeatNo" value="${param.chooseSeatNo}"> 
								<input type="hidden" name="action" value="check_out">
							</form>
						</div>

						<div class="list-group-item info">
							<div class="row">
								<div class="col text-center">
									<h2 class="title">請輸入付款資訊</h2>
									<p>以下輸入您的信用卡資訊</p>
								</div>
							</div>
						</div>
						<div class="list-group-item">
							<form id="creditCard">
								<div class="form-container">
									<div class="card-wrapper"></div>
									<table class="table">
										<tr>
											<td>持卡人姓名</td>
											<td><input type="text" name="first-name" placeholder="Name" class="form-control" /></td>
										</tr>
										<tr>
											<td>卡號</td>
											<td><input type="text" name="number" placeholder="Card Number" class="form-control" /></td>
										</tr>
										<tr>
											<td>到期日</td>
											<td><input type="text" name="expiry" placeholder="MM / YY" class="form-control" /></td>
										</tr>
										<tr>
											<td>CVV</td>
											<td><input type="text" name="cvc" placeholder="CCV" class="form-control" /></td>
										</tr>
									</table>
								</div>
							</form>
						</div>
					</div>
				</div>

				<div id="b" class="col-3">
					<div class="card mb-3">
						<div class="card-header">
							時間剩餘 <span id="timeOut">5:00</span>
						</div>
						<div class="card-body"></div>
					</div>
					<div class="card mb-3">
						<div class="card-header">會員專區</div>
						<div class="card-body">嗨!${MemberVO.memName} 您好</div>
					</div>

					<button id="nextStep" class="btn combtn btn-lg">確認結帳</button>
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

	<%@ include file="/front-end/files/frontend_importJs.file"%>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resource/js/creditCard/jquery.card.js"></script>
	<script>
		$('#creditCard').card({
			container : '.card-wrapper',
			width : 280,
			formSelectors : {
				nameInput : 'input[name="first-name"], input[name="last-name"]'
			}
		});
	</script>
	<script>
		$("#grade-number").text(getGradeNumber($("#grade-word").text()));

		function getGradeNumber(gradeWord) {
			if ("普遍級" === gradeWord) {
				$("#grade-number").css("background-color", "#00A600");
				return "0+";
			} else if ("保護級" === gradeWord) {
				$("#grade-number").css("background-color", "#2894FF");
				return "6+";
			} else if ("輔導級" === gradeWord) {
				$("#grade-number").css("background-color", "#FFE153");
				return "12+";
			} else {
				$("#grade-number").css("background-color", "#EA0000");
				return "18+";
			}
		}

		let chooseSeatNo = "${param.chooseSeatNo}";
		$("#chooseSeatNo").text(addComma(chooseSeatNo));

		function addComma(chooseSeatNo) {
			let result = "";
			for (let i = 0; i < chooseSeatNo.length; i += 3) {
				let subStr = chooseSeatNo.substring(i, i + 3);
				if (i + 3 !== chooseSeatNo.length) {
					result = result + subStr + ", ";
				} else {
					result = result + subStr;
				}
			}
			return result;
		}

		$("#nextStep").click(function() {
			$("#form").submit();
		});

		$("#totalPrice").text(getTotalPrice());
		function getTotalPrice() {
			let total = 0;
			let items = $(".subtotal");

			for (let i = 0; i < items.length; i++) {
				total += parseInt($(items[i]).text());
			}
			return total;
		}

		let sec = 59;
		setInterval(function() {
			$("#timeOut").text(timeFormat(sec));
			sec -= 1;
		}, 1000)

		setTimeout(
				function() {
					window.location
							.replace("${pageContext.request.contextPath}/front-end/index.jsp");
				}, sec * 1000);

		function timeFormat(second) {
			let minute = parseInt(second / 60);
			second %= 60;
			(second < 10) ? second = '0' + second : second;
			return minute + ":" + second;
		}
	</script>
</body>
</html>