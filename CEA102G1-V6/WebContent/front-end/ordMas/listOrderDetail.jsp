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
table {
	/* 	width: 750px; */
	margin: 5px auto 5px auto;
	background-color: rgb(255, 255, 255);
	border-radius: 10px;
	-webkit-box-shadow: 0px 3px 5px rgb(8, 8, 8, 0.3);
	-moz-box-shadow: 0px 3px 5px rgb(8, 8, 8, 0.3);
	box-shadow: 0px 3px 5px rgb(8, 8, 8, 0.3);
}

th, td {
	box-sizing: border-box;
	border-radius: 10px;
}

th {
	width: 200px;
	padding: 10px 0px 10px 70px;
}

td {
	width: 250px;
	padding: 10px 20px 10px 30px;
	border-bottom: 2px dotted #bb9d52;
}

.listOne-h3-pos, #a-color {
	margin-left: 50%;
}

.listOne-h3-pos {
	display: inline-block;
}

#a-color {
	font-size: 16px;
}

.detail {
	display: inline-block;
}

.detailBlock {
	position: relative;
}

.detail.price {
	position: absolute;
	right: 0%;
}

#status {
	margin-left: 180px;
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
		<div class="container-fluid">
			<div class="row">
				<div class="col-2"></div>
				<!-- listOneMovie Start -->
				<jsp:useBean id="sesSvc" scope="request" class="com.session.model.SesService" />
				<jsp:useBean id="movSvc" scope="request" class="com.movie.model.MovService" />
				<div class="col-8">
					<h3 class="h3-style listOne-h3-pos">訂單詳情</h3>
					<h2 id="status">${(ordMasVO.ordMasStatus == "0")?"未取票":(ordMasVO.ordMasStatus == "1")?"已取票" :"已取消"}</h2>
					<table>
						<tr>
							<th>取票二維碼</th>
							<td><img src="<%=request.getContextPath()%>/util/generateQRcode?ordMasNo=${ordMasVO.ordMasNo}"></td>
						</tr>
						<tr>
						<c:set var="sesVO" value="${sesSvc.getOneSes(ordMasVO.sesNo)}"/>
							<th>電影</th>
							<td>${movSvc.getOneMov(sesVO.movNo).movname}</td>
						</tr>
						<tr>
							<th>日期</th>
							<td>${sesVO.sesDate}</td>
						</tr>
						<tr>
							<th>場次</th>
							<td><fmt:formatDate value="${sesVO.sesTime}" pattern="HH:mm" /></td>
						</tr>
						<tr>
							<jsp:useBean id="ticLisSvc" scope="page" class="com.ticket_list.model.TicLisService" />
							<th>座位</th>
							<td>${ticLisSvc.getSeatNo(ticLisSvc.getByOrdMasNo(ordMasVO.ordMasNo))}</td>
						</tr>
						<tr>
							<th>商品</th>
							<td>
								<jsp:useBean id="ideSvc" scope="request" class="com.identity.model.IdeService" /> 
								<jsp:useBean id="ticTypSvc" scope="request" class="com.ticket_type.model.TicTypService" /> 
								<jsp:useBean id="fooLisSvc" scope="request" class="com.food_list.model.FooLisService" /> 
								<c:forEach var="ticTypCartVO" items="${ticLisSvc.convertToTicTypCart(ticLisSvc.getByOrdMasNo(ordMasVO.ordMasNo))}">
									<div class="detailBlock">
										<p class="detail">${ideSvc.getOneDept(ticTypSvc.getOneTicket_type(ticTypCartVO.ticTypNo).ide_no).ide_name}</p>
										<p class="detail price">
											$<span>${ticTypCartVO.ticLisPrice}</span> X <span>${ticTypCartVO.ticTypCount}</span>
										</p>
									</div>
								</c:forEach> 
								<jsp:useBean id="fooSvc" scope="page" class="com.food.model.FooService" /> 
								<c:forEach var="fooCartVO" items="${fooLisSvc.convertToFooCart(fooLisSvc.getByOrdMasNo(ordMasVO.ordMasNo))}">
									<div class="detailBlock">
										<p class="detail">${fooSvc.getOneFoo(fooCartVO.fooNo).fooName}</p>
										<p class="detail price">
											$<span>${fooCartVO.fooPrice}</span> X <span>${fooCartVO.fooCount}</span>
										</p>
									</div>
								</c:forEach></td>
						</tr>
						<tr>
							<th>總價</th>
							<td>${ordMasVO.ordMasPrice}</td>
						</tr>

					</table>
					<a class="combtn" href="<%=request.getContextPath()%>/front-end/ordMas/listMemOrder.jsp" style="margin: 10px 0 0 350px; display: inline-block;">回訂單列表</a>
				</div>
				<div class="col-2"></div>
				<!-- listOneMovie End -->
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
	<script>
		let status = "${ordMasVO.ordMasStatus}";
		let result = "${sesSvc.checkOverdue(ordMasVO.sesNo, 1)}";
		switch (status) {
		case '0':
			if (result === "true")
				$("#status").css("color", "red");
			else
				$("#status").css("color", "blue");

			break;
		case '1':
			$("#status").css("color", "green");
			break;
		case '2':
			$("#status").css("color", "red");
			break;
		}
	</script>
</body>
</html>