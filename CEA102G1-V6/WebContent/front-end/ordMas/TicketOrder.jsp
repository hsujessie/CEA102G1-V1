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
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/order/TicketOrder.css">

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
										<span></span>${sesVO.sesDate} <fmt:formatDate value="${sesVO.sesTime}" pattern="HH:mm" />
									</p>
									<p>
										<img src="<%=request.getContextPath()%>/resource/images/ordMasIcons/theater.png">
										<span></span>第${sesVO.theNo}廳
									</p>
								</div>
							</div>
						</div>
						<div class="list-group-item info">
							<div class="row">
								<div class="col text-center">
									<h2 class="title">選擇電影票</h2>
									<p>選擇您希望的電影票張數及類型, 每筆交易最多可購買10張電影票</p>
								</div>
							</div>
						</div>
						<form method="post" action="<%=request.getContextPath()%>/ordMas/ordMas.do" id="form">
							<div class="list-group-item">
								<table class="table">
									<thead>
										<tr id="table-secondary">
											<th>票種</th>
											<th>票價</th>
											<th>數量</th>
											<th>小計</th>
										</tr>
									</thead>
									<tbody>
										<jsp:useBean id="ticTypSvc" scope="request" class="com.ticket_type.model.TicTypService" />
										<jsp:useBean id="ideSvc" scope="request" class="com.identity.model.IdeService" />
										<jsp:useBean id="theSvc" scope="request" class="com.theater.model.TheService" />
										<c:forEach var="ticTypVO" items="${ticTypSvc.getTicTypsByMovVerNo(theSvc.getOneTheater(sesVO.theNo).movver_no)}">
											<tr>
												<td class="ticketName">${ideSvc.getOneDept(ticTypVO.ide_no).ide_name}</td>
												<td>$<span class="ticketPrice">${ticTypVO.tictyp_price}</span></td>
												<td>
													<select class="form-control ticketCount" name="ticTypNo${ticTypVO.tictyp_no}">
														<c:forEach varStatus="i" begin="0" end="10">
															<option value="${i.index}">${i.index}
														</c:forEach>
													</select>
												</td>
												<td>0</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="list-group-item info">
								<div class="row">
									<div class="col text-center">
										<h2 class="title">選擇餐點</h2>
										<p>請選擇您希望的購買的餐點</p>
									</div>
								</div>
							</div>
							<div id="tabs_item" class="list-group-item">
								<div id="tabs">
									<jsp:useBean id="fooCatSvc" scope="request" class="com.food_cate.model.FooCatService" />
									<jsp:useBean id="fooSvc" scope="request" class="com.food.model.FooService" />

									<ul>
										<c:forEach var="fooCatVO" items="${fooCatSvc.all}">
											<li><a href="#tabs-${fooCatVO.fooCatNo}">${fooCatVO.fooCatName}</a></li>
										</c:forEach>
									</ul>

									<c:forEach var="fooCatVO" items="${fooCatSvc.all}">
										<div id="tabs-${fooCatVO.fooCatNo}">
											<div class="row">
												<c:forEach var="fooVO" items="${fooCatSvc.getFoosByFooCatNo(fooCatVO.fooCatNo)}">
													<c:if test="${fooVO.fooStatus==0}">
														<div class="col-4">
															<div class="card">
																<div class="foodImg">
																	<img
																		src="<%=request.getContextPath()%>/util/imgReader${fooVO.fooImgParam}" class="card-img-top">
																</div>
																<div class="card-body">
																	<h5 class="card-title foodName">${fooVO.fooName}</h5>
																	<p class="card-text">
																		$ <span class="foodPrice">${fooVO.fooPrice}</span>
																	</p>
																	<select name="fooNo${fooVO.fooNo}" class="form-control foodCount">
																		<c:forEach varStatus="index" begin="0" end="10">
																			<option value="${index.index}">${index.index}
																		</c:forEach>
																	</select>
																</div>
															</div>
														</div>
													</c:if>
												</c:forEach>
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
							<input type="hidden" name="sesNo" value="${param.sesNo}" /> 
							<input type="hidden" name="action" value="go_select_seat" />
						</form>
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
									<tr id="addhere">
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
					<button id="nextStep" class="btn combtn btn-lg" disabled>請至少選一張票</button>
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
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/resource/js/order/TicketOrder.js"></script>

</body>
</html>