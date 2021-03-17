<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Front-End</title>
<%@ include file="/front-end/files/frontend_importCss.file"%>
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
										<div id="grade-word">${movSvc.getOneMov(sesSvc.getOneSes(1).movNo).movrating}</div>
									</div>
								</div>
								<div class="col-7">
									<h3>(${movSvc.getOneMov(sesSvc.getOneSes(1).movNo).movver}${movSvc.getOneMov(sesSvc.getOneSes(1).movNo).movlan})${movSvc.getOneMov(sesSvc.getOneSes(1).movNo).movname}</h3>
								</div>
								<div class="col-3">
									<p>
										<img src="<%=request.getContextPath()%>/resource/images/ordMasIcons/sesTime.png"><span> </span>${sesSvc.getOneSes(1).sesDate}${sesSvc.getOneSes(1).sesTime}
									</p>
									<p>
										<img src="<%=request.getContextPath()%>/resource/images/ordMasIcons/theater.png"><span> </span>第${sesSvc.getOneSes(1).theNo}廳
									</p>
									<p>
										<img src="<%=request.getContextPath()%>/resource/images/ordMasIcons/seatNo.png"><span> </span><span id="chooseSeatNo"></span>
									</p>
								</div>
							</div>
						</div>
						<div class="list-group-item">付款成功</div>
						<div class="list-group-item">
							會員資料
						</div>
						<div class="list-group-item">
							<table class="table">
								<thead>
									<tr class="table-secondary">
										<th>商品</th>
										<th>價格</th>
										<th>數量</th>
										<th>小計</th>
									</tr>
								</thead>
								<tbody>

								<jsp:useBean id="theSvc" scope="page" class="com.theater.model.TheService" />
								<jsp:useBean id="ticTypSvc" scope="page" class="com.ticket_type.model.TicTypService" />
								<jsp:useBean id="ideSvc" scope="page" class="com.identity.model.IdeService" />

									<c:forEach var="ticTypCartVO" items="${ticTypCartSet}">
										<tr>
											<td>${ideSvc.getOneDept(ticTypCartVO.ideNo).ide_name}</td>
											<td>$<span>${ticTypCartVO.ticLisPrice}</span></td>
											<td>${ticTypCartVO.ticTypCount}</td>
											<td>123</td>
										</tr>
									</c:forEach>
							
									<c:forEach var="fooCartVO" items="${fooCartSet}">
										<tr>
											<td>${fooCartVO.fooName}</td>
											<td>
												$
												<span>${fooCartVO.fooPrice}</span>
											</td>
											<td>${fooCartVO.fooCount}</td>
											<td>123</td>
										</tr>
									</c:forEach>
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
		var chooseSeatNo = "${param.chooseSeatNo}";
		console.log(addComma(chooseSeatNo));
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