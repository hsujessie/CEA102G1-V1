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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/css/order/OrderComplete.css">
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
				<div class="col-12">
					<h2 style="color: green;">付款成功</h2>
				</div>
				<div class="col-9">
					<div class="list-group">
						<div class="row">
							<div class="list-group-item" style="width: 100%;">
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
											<span></span> 第${sesVO.theNo}廳
										</p>
										<p>
											<img src="<%=request.getContextPath()%>/resource/images/ordMasIcons/seatNo.png">
											<span></span><span id="chooseSeatNo"></span>
										</p>
									</div>
								</div>
							</div>

							<div class="list-group-item" style="width: 100%;">
								<h4 style="font-style: normal;">商品明細</h4>
								<table class="table">
									<tbody>
										<jsp:useBean id="ideSvc" scope="request" class="com.identity.model.IdeService" />
										<c:forEach var="ticTypCartVO" items="${ticTypCartSet}">
											<c:if test="${check != ticTypCartVO.ideNo}">
												<tr>
													<td>
														<p>${ideSvc.getOneDept(ticTypCartVO.ideNo).ide_name}</p>
														<p class="text-right">
															X <span>${ticTypCartVO.ticTypCount}</span>
														</p>
													</td>
												</tr>
											</c:if>
											<c:set var="check" value="${ticTypCartVO.ideNo}" />
										</c:forEach>
										<c:forEach var="fooCartVO" items="${fooCartSet}">
											<tr>
												<td>
													<p>${fooCartVO.fooName}</p>
													<p class="text-right">
														X <span>${fooCartVO.fooCount}</span>
													</p>
												</td>
											</tr>
										</c:forEach>
										<tr>
											<td>
												<p class="text-right">
													合計 <span id="orderTotal">${ordMasVO.ordMasPrice}</span>
												</p>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="col-3">
					<div class="list-group-item" style="text-align: center;">
						<h2 style="font-style: normal;">取票二維碼</h2>
						<img src="<%=request.getContextPath()%>/util/generateQRcode?ordMasNo=${ordMasVO.ordMasNo}">
					</div>
					<a id="nextStep" class="btn combtn" href="<%=request.getContextPath()%>/front-end/ordMas/listMemOrder.jsp">前往購票管理</a>
				</div>
			</div>
		</div>
		<!-- PUT HERE End -->

		<!-- Book Tickets Start -->
		<%-- 		<%@ include file="/front-end/files/frontend_bookTicketsTamplate.file"%> --%>
		<!-- Book Tickets End -->

		<!-- Footer Start -->
		<%@ include file="/front-end/files/frontend_footer.file"%>
		<!-- Footer End -->
	</div>

	<%@ include file="/front-end/files/frontend_importJs.file"%>
	<script>
		let chooseSeatNo = "${param.chooseSeatNo}";
	</script>
	<script src="<%=request.getContextPath()%>/resource/js/order/OrderComplete.js"></script>
</body>
</html>