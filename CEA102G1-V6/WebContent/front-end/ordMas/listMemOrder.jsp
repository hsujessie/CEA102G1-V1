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
.success-span {
	color: #bb9d52;
	position: absolute;
	top: 8%;
	left: 17%;
}

.th-adjust {
	width: 120px;
}

.form-sty {
	margin: 20px 0 0 10px;
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
<!-- 		<div class="page-header"> -->
<!-- 			<div class="container"> -->
<!-- 				<div class="row"> -->
<!-- 					<div class="col-12"> -->
<!-- 						<h2>Front-End</h2> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<!-- Page Header End -->


		<!-- PUT HERE Start -->
		<div class="container">
			<div class="row">
				<div class="col-3">
					<div class="nav flex-column nav-pills" id="v-pills-tab"
						role="tablist" aria-orientation="vertical">
						<a class="nav-link active" id="v-pills-home-tab"
							data-toggle="pill" href="#v-pills-home">未取票</a> <a
							class="nav-link" id="v-pills-profile-tab" data-toggle="pill"
							href="#v-pills-profile">購票紀錄</a>
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
										<th>查看</th>
									</tr>
								</thead>

								<tbody>
									<jsp:useBean id="ordMasSvc"
										class="com.order_master.model.OrdMasService"></jsp:useBean>
									<jsp:useBean id="sesSvc" class="com.session.model.SesService"></jsp:useBean>
									<jsp:useBean id="movSvc" class="com.movie.model.MovService"></jsp:useBean>
									<c:set var="count" value="1" />
									<c:forEach var="ordMasVO" items="${ordMasSvc.getByMemNo(1)}">
										<c:if test="${ordMasVO.ordMasStatus == 0}">
											<c:set var="sesNo" value="${ordMasVO.sesNo}" />
											<tr class="sty-height" valign='middle'>
												<td>${count}</td>
												<td>${movSvc.getOneMov(sesSvc.getOneSes(sesNo).movNo).movname}</td>
												<td>${sesSvc.getOneSes(sesNo).sesDate} <fmt:formatDate value="${sesSvc.getOneSes(sesNo).sesTime}" pattern="HH:mm"/></td>
												<td>未取票</td>
												<td>
																									<form method="post" action="<%=request.getContextPath()%>/ordMas/ordMas.do">
																										<input type="hidden" name="ordMasNo" value="${ordMasVO.ordMasNo}" />
																										<input type="hidden" name="action" value="getOne_For_Display" />
																										<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>" />
																										<input type="submit" value="查看詳情" class="input-pos combtn" />
																									</form>
<!-- 													<button type="button" class="combtn look-detail" data-toggle="modal" data-target="#exampleModal">查看詳情</button> -->
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
									<c:forEach var="ordMasVO" items="${ordMasSvc.getByMemNo(1)}">
										<c:if test="${ordMasVO.ordMasStatus == 1 || ordMasVO.ordMasStatus == 2}">
											<c:set var="sesNo" value="${ordMasVO.sesNo}" />
											<tr class="sty-height" valign='middle'>
												<td>${count}</td>
												<td>${movSvc.getOneMov(sesSvc.getOneSes(sesNo).movNo).movname}</td>
												<td>${sesSvc.getOneSes(sesNo).sesDate}${sesSvc.getOneSes(sesNo).sesTime}</td>
												<td>${ordMasVO.ordMasStatus == 1?"已完成":"已取消"}</td>
												<td>
																									<form method="post" action="<%=request.getContextPath()%>/ordMas/ordMas.do">
																										<input type="hidden" name="ordMasNo" value="${ordMasVO.ordMasNo}" />
																										<input type="hidden" name="action" value="getOne_For_Display" />
																										<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>" />
																										<input type="submit" value="查看詳情" class="input-pos combtn" />
																									</form>
<!-- 													<button type="button" class=" combtn look-detail" data-toggle="modal" data-target="#exampleModal">查看詳情</button> -->
												</td>
											</tr>
											<c:set var="count" value="${count + 1}" />
										</c:if>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<div class="modal fade" id="exampleModal" tabindex="-1"
						aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel">訂單詳情</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">...</div>
								<div class="modal-footer">
									<button type="button" class="combtn" data-dismiss="modal">關閉</button>
								</div>
							</div>
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
		$(".look-detail").click(function() {
			
		})
	</script>
</body>
</html>