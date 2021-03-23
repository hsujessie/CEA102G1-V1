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
	</script>
</body>
</html>