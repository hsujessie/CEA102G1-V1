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
div>p>img {
	width: 30px;
	height: 40px;
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
				<div class="col">
					<div class="list-group">
						<div class="list-group-item">
							<div class="row">
								<jsp:useBean id="sesSvc" scope="page" class="com.session.model.SesService" />
								<jsp:useBean id="movSvc" scope="page" class="com.movie.model.MovService" />

								<div class="col-2">
									<div id="grade" class="text-center">
										<div id="grade-number"></div>
										<div id="grade-word">${movSvc.getOneMov(sesSvc.getOneSes(param.sesNo).movNo).movrating}</div>
									</div>
								</div>
								<div class="col-7">
									<h3>(${movSvc.getOneMov(sesSvc.getOneSes(param.sesNo).movNo).movver}${movSvc.getOneMov(sesSvc.getOneSes(param.sesNo).movNo).movlan})${movSvc.getOneMov(sesSvc.getOneSes(param.sesNo).movNo).movname}</h3>
								</div>
								<div class="col-3">
									<p>
										<img src="<%=request.getContextPath()%>/resource/images/ordMasIcons/sesTime.png"><span> </span>${sesSvc.getOneSes(param.sesNo).sesDate} <fmt:formatDate value="${sesSvc.getOneSes(param.sesNo).sesTime}" pattern="HH:mm"/>
									</p>
									<p>
										<img src="<%=request.getContextPath()%>/resource/images/ordMasIcons/theater.png"><span> </span>第${sesSvc.getOneSes(param.sesNo).theNo}廳
									</p>
									<p>
										<img src="<%=request.getContextPath()%>/resource/images/ordMasIcons/seatNo.png"><span> </span><span id="chooseSeatNo"></span>
									</p>
								</div>
							</div>
						</div>
						<div class="list-group-item">
							<h2>取票二維碼</h2>
							<img src="<%=request.getContextPath()%>/util/generateQRcode?ordMasNo=${ordMasVO.ordMasNo}">
						</div>
						<div class="list-group-item">付款成功</div>
						<div class="list-group-item">
							會員資料
						</div>
						<div class="list-group-item">
							<table class="table">
							<tbody>
								<jsp:useBean id="ideSvc" scope="page" class="com.identity.model.IdeService" />
								<c:forEach var="ticTypCartVO" items="${ticTypCartSet}">
								<c:if test="${check != ticTypCartVO.ideNo}">
									<tr>
										<td>
											<p>${ideSvc.getOneDept(ticTypCartVO.ideNo).ide_name}</p>
											<p class="text-right">
												X
												<span>${ticTypCartVO.ticTypCount}</span>
											</p>
										</td>
									</tr>
									</c:if>
									<c:set var="check" value="${ticTypCartVO.ideNo}"/>
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
											<span id="orderTotal">${ordMasVO.ordMasPrice}</span>
										</p>
									</td>
								</tr>
							</tbody>
						</table>
						</div>
					</div>
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
	
	
		let chooseSeatNo = "${param.chooseSeatNo}";
		$("#chooseSeatNo").text(addComma(chooseSeatNo));
		
		function addComma(chooseSeatNo) {
			let result = "";
			for (let i = 0; i < chooseSeatNo.length; i += 3) {
				let subStr = chooseSeatNo.substring(i, i + 3);
				if (i + 3 !== chooseSeatNo.length) {
					result = result + subStr + " ,";
				} else {
					result = result + subStr;
				}
			}
			return result;
		}
	</script>
</body>
</html>