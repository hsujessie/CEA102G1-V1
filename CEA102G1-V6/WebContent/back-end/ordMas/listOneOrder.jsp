<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.movie.model.*"%>

<head>
<title>Movies Management</title>
<%@ include file="/back-end/files/sb_head.file"%>

<style>
table {
	width: 80%;
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
 	width: 150px; 
	padding: 10px 0px 10px 70px;
}

td {
/* 	width: 250px; */
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
	display:inline-block;
}

.detailBlock{
	position:relative;
}
.detail.price {
	position: absolute;
	right: 0%;
}

.btn {
	width:20%;
	color:white;
	padding:0px 0px;
}
</style>
</head>

<body class="sb-nav-fixed">
	<%@ include file="/back-end/files/sb_navbar.file"%>
	<!-- 引入navbar (上方) -->
	<div id="layoutSidenav">
		<div id="layoutSidenav_nav">
			<c:set value="movieSub" var="urlRecog"></c:set>
			<!-- 給sb_sidebar.file的參數-Sub -->
			<%@ include file="/back-end/files/sb_sidebar.file"%>
			<!-- 引入sidebar (左方) -->
		</div>
		<div id="layoutSidenav_content">
			<main>
				<div class="container-fluid">

					<div class="row">
						<div class="col-2"></div>
						<!-- listOneMovie Start -->
						<jsp:useBean id="sesSvc" scope="page"
							class="com.session.model.SesService" />
						<jsp:useBean id="movSvc" scope="page"
							class="com.movie.model.MovService" />
						<div class="col-8">
							<h3 class="h3-style listOne-h3-pos">訂單詳情</h3>
							<table>
								<tr>
									<th>電影</th>
									<td>${movSvc.getOneMov(sesSvc.getOneSes(ordMasVO.sesNo).movNo).movname}</td>
								</tr>
								<tr>
									<th>日期</th>
									<td>${sesSvc.getOneSes(ordMasVO.sesNo).sesDate}</td>
								</tr>
								<tr>
									<th>場次</th>
									<td><fmt:formatDate
											value="${sesSvc.getOneSes(ordMasVO.sesNo).sesTime}"
											pattern="HH:mm" /></td>
								</tr>
								<tr>
									<jsp:useBean id="ticLisSvc" scope="page"
										class="com.ticket_list.model.TicLisService" />
									<th>座位</th>
									<td>${ticLisSvc.getSeatNo(ticLisSvc.getByOrdMasNo(ordMasVO.ordMasNo))}</td>
								</tr>
								<tr>
									<th>商品</th>
									<td><jsp:useBean id="ideSvc" scope="page"
											class="com.identity.model.IdeService" /> <jsp:useBean
											id="ticTypSvc" scope="page"
											class="com.ticket_type.model.TicTypService" /> <jsp:useBean
											id="fooLisSvc" scope="page"
											class="com.food_list.model.FooLisService" /> <c:forEach
											var="ticTypCartVO"
											items="${ticLisSvc.convertToTicTypCart(ticLisSvc.getByOrdMasNo(ordMasVO.ordMasNo))}">
											<div class="detailBlock">
												<p class="detail">${ideSvc.getOneDept(ticTypSvc.getOneTicket_type(ticTypCartVO.ticTypNo).ide_no).ide_name}</p>
												<p class="detail price">
													$<span>${ticTypCartVO.ticLisPrice}</span> X <span>${ticTypCartVO.ticTypCount}</span>
												</p>
											</div>
										</c:forEach> <jsp:useBean id="fooSvc" scope="page"
											class="com.food.model.FooService" /> <c:forEach
											var="fooCartVO"
											items="${fooLisSvc.convertToFooCart(fooLisSvc.getByOrdMasNo(ordMasVO.ordMasNo))}">
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
							
							
							<a class="btn btn-light btn-brd grd1 effect-1"
								href="<%=request.getContextPath()%>/back-end/index.jsp"
								style="margin: 10px 0 0 300px;">回訂單列表</a>
						</div>
						<div class="col-2"></div>
						<!-- listOneMovie End -->
					</div>
				</div>
			</main>
			<%@ include file="/back-end/files/sb_footer.file"%>
		</div>
	</div>
	<%@ include file="/back-end/files/sb_importJs.file"%>
	<!-- 引入template要用的js -->
</body>
</html>