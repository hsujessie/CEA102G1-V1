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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/css/order/listMemOrder.css">
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
				<div class="col-3">
					<div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
						<a class="nav-link active" id="v-pills-home-tab" data-toggle="pill" href="#v-pills-home">未取票</a> 
						<a class="nav-link" id="v-pills-profile-tab" data-toggle="pill" href="#v-pills-profile">購票紀錄</a>
					</div>
				</div>
				<div class="col-9">
					<div class="tab-content" id="v-pills-tabContent">
						<div class="tab-pane fade show active" id="v-pills-home">
							<table class="table table-hover">
								<thead>
									<tr style="border-bottom: 3px solid #bb9d52;">
										<th>列表編號</th>
										<th class="th-adjust">電影</th>
										<th>場次時間</th>
										<th>訂單狀態</th>
										<th>訂購時間</th>
										<th>查看</th>
										<th>退票</th>
									</tr>
								</thead>

								<tbody>
									<jsp:useBean id="ordMasSvc" class="com.order_master.model.OrdMasService"></jsp:useBean>
									<jsp:useBean id="sesSvc" class="com.session.model.SesService" scope="request"></jsp:useBean>
									<jsp:useBean id="movSvc" class="com.movie.model.MovService" scope="request"></jsp:useBean>
									<c:set var="count" value="1" />
									<c:set var="ordMaslist" value="${ordMasSvc.getByMemNo(MemberVO.memNo)}" />
									<c:forEach var="ordMasVO" items="${ordMaslist}">
										<c:set var="sesVO" value="${sesSvc.getOneSes(ordMasVO.sesNo)}" />
										<c:if test="${ordMasVO.ordMasStatus == 0 && !sesSvc.checkOverdue(sesVO.sesNo, 1)}">
											<tr class="sty-height" valign='middle'>
												<td>${count}</td>
												<td>${movSvc.getOneMov(sesVO.movNo).movname}</td>
												<td>${sesVO.sesDate} <fmt:formatDate value="${sesVO.sesTime}" pattern="HH:mm" /></td>
												<td style="color: blue">未取票</td>
												<td><fmt:formatDate value="${ordMasVO.ordMasDate}" pattern="yyyy-MM-dd HH:mm" /></td>
												<td>
													<form method="post" action="<%=request.getContextPath()%>/ordMas/ordMas.do">
														<input type="hidden" name="ordMasNo" value="${ordMasVO.ordMasNo}" /> 
														<input type="hidden" name="action" value="getOne_For_Display" /> 
														<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>" /> 
														<input type="submit" value="查看詳情" class="input-pos combtn" />
													</form>
												</td>
												<td>
													<form method="post" action="<%=request.getContextPath()%>/ordMas/ordMas.do">
														<input type="hidden" name="action" value="change_status">
														<input type="hidden" name="ordMasNo" value="${ordMasVO.ordMasNo}">
													</form>
													<button class="refund combtn" ${sesSvc.checkOverdue(sesVO.sesNo, 0)?"disabled":""}>退票</button>
												</td>
											</tr>
											<c:set var="count" value="${count + 1}" />
										</c:if>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="tab-pane fade" id="v-pills-profile">
							<table class="table table-hover">
								<thead>
									<tr style="border-bottom: 3px solid #bb9d52;">
										<th>列表編號</th>
										<th class="th-adjust">電影</th>
										<th>場次時間</th>
										<th>訂單狀態</th>
										<th>查看</th>
									</tr>
								</thead>

								<tbody>
									<c:set var="count" value="1" />
									<c:forEach var="ordMasVO" items="${ordMaslist}">
										<c:set var="sesVO" value="${sesSvc.getOneSes(ordMasVO.sesNo)}" />
										<c:if test="${ordMasVO.ordMasStatus == 1 || ordMasVO.ordMasStatus == 2 || sesSvc.checkOverdue(sesVO.sesNo, 1)}">
											<tr class="sty-height" valign='middle'>
												<td>${count}</td>
												<td>${movSvc.getOneMov(sesVO.movNo).movname}</td>
												<td>${sesVO.sesDate} <fmt:formatDate value="${sesVO.sesTime}" pattern="HH:mm" /></td>
												<td
													${ordMasVO.ordMasStatus == 1?"style='color:green'":"style='color:red'"}>${ordMasVO.ordMasStatus == 1?"已完成":(ordMasVO.ordMasStatus == 2)?"已取消":"逾期未取票"}</td>
												<td>
													<form method="post" action="<%=request.getContextPath()%>/ordMas/ordMas.do">
														<input type="hidden" name="ordMasNo" value="${ordMasVO.ordMasNo}" /> 
														<input type="hidden" name="action" value="getOne_For_Display" /> 
														<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>" /> 
														<input type="submit" value="查看詳情" class="input-pos combtn" />
													</form>
												</td>

											</tr>
											<c:set var="count" value="${count + 1}" />
										</c:if>
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
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<script src="<%=request.getContextPath()%>/resource/js/order/listMemOrder.js"></script>
</body>
</html>