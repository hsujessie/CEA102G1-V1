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
<link href='https://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/css/selectSeat/jquery.seat-charts.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/css/selectSeat/style.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/css/selectSeat/SelectSeat.css">

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
	</script>
	<script src="<%=request.getContextPath()%>/resource/js/selectSeat/SelectSeat1.js"></script>

	<%@ include file="/front-end/files/frontend_importJs.file"%>
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<script src="<%=request.getContextPath()%>/resource/js/selectSeat/jquery.seat-charts.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/resource/js/selectSeat/SelectSeatJS.js"></script>
	<script src="<%=request.getContextPath()%>/resource/js/selectSeat/SelectSeat2.js"></script>
	<script>
		if (${not empty errorMsgs}) {
			swal("位置已被選擇", "請重新選位", "error", {button: "關閉"});
		}
	</script>
</body>
</html>