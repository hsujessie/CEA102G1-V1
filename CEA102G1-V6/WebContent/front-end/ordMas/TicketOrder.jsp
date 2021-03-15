<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Front-End</title>
<%@ include file="/front-end/files/frontend_importCss.file"%>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">


<style>
	div>p {
		font-size: 8px;
	}
	
	div>p>span {
		margin: 0 3px;
	}
	
	div>p>img {
 		width: 30px; 
		height: 40px; 
	}
	#grade {
	border: 1px solid black;
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
				<div class="col-9">
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
									<p><img src="<%=request.getContextPath()%>/resource/images/ordMasIcons/sesTime.png"><span> </span>${sesSvc.getOneSes(1).sesDate} ${sesSvc.getOneSes(1).sesTime}</p>
									<p><img src="<%=request.getContextPath()%>/resource/images/ordMasIcons/theater.png"><span> </span>第${sesSvc.getOneSes(1).theNo}廳</p>
								</div>
							</div>
						</div>
						<div class="list-group-item">放選擇電影票</div>
						<form method="post"
							action="<%=request.getContextPath()%>/ordMas/ordMas.do" id="form">
							<div class="list-group-item">
								<div id="accordion">

									<div class="card">
										<div class="card-header">一般票種</div>
										<div class="card-body">
											<table class="table">
												<thead>
													<tr class="table-secondary">
														<th>票種</th>
														<th>票價</th>
														<th>數量</th>
														<th>小計</th>
													</tr>
												</thead>
												<tbody>


													<jsp:useBean id="ticTypSvc" scope="page"
														class="com.ticket_type.model.Ticket_typeService" />
													<jsp:useBean id="ideSvc" scope="page"
														class="com.identity.model.IdentityService" />
													<jsp:useBean id="theSvc" scope="page" class="com.theater.model.TheaterService" />
													<c:forEach var="ticTypVO"
														items="${ticTypSvc.getTicTypsByMovVerNo(theSvc.getOneTheater(sesSvc.getOneSes(1).theNo).movver_no)}">
														<tr>
															<td>${ideSvc.getOneDept(ticTypVO.ide_no).ide_name}</td>
															<td>$<span>${ticTypVO.tictyp_price}</span></td>
															<td><select class="form-control"
																name="ticTypNo${ticTypVO.tictyp_no}">
																	<c:forEach varStatus="i" begin="0" end="10">
																		<option value="${i.index}">${i.index}
																	</c:forEach>
															</select></td>
															<td>0</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>

									</div>

									<div class="card">
										<div class="card-header">
											<a class="collapsed card-link" data-toggle="collapse"
												href="#collapseTwo"> Collapsible Group Item #2 </a>
										</div>
										<div id="collapseTwo" class="collapse"
											data-parent="#accordion">
											<div class="card-body">Lorem ipsum..</div>
										</div>
									</div>

								</div>
							</div>
							<div class="list-group-item">放選擇餐飲</div>
							<div class="list-group-item">
								<div id="tabs">
									<jsp:useBean id="fooCatSvc" scope="page"
										class="com.food_cate.model.FooCatService" />
									<jsp:useBean id="fooSvc" scope="page"
										class="com.food.model.FooService" />

									<ul>
										<c:forEach var="fooCatVO" items="${fooCatSvc.all}">
											<li><a href="#tabs-${fooCatVO.fooCatNo}">${fooCatVO.fooCatName}</a></li>
										</c:forEach>
									</ul>

									<c:forEach var="fooCatVO" items="${fooCatSvc.all}">
										<div id="tabs-${fooCatVO.fooCatNo}">
											<div class="row">
												<c:forEach var="fooVO"
													items="${fooCatSvc.getFoosByFooCatNo(fooCatVO.fooCatNo)}">
													<div class="food col-4">
														<div>
															<img
																src="<%=request.getContextPath()%>/foo/reader.do?columnName=foo_img&tableName=food&fieldName=foo_no&fieldValue=${fooVO.fooNo}">
														</div>
														<div>
															<p>${fooVO.fooName}</p>
															<p class="count">$${fooVO.fooPrice}</p>
															<select name="fooNo${fooVO.fooNo}" class="count">
																<c:forEach varStatus="index" begin="0" end="10">
																	<option value="${index.index}">${index.index}
																</c:forEach>
															</select>
														</div>
													</div>
												</c:forEach>
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
							<input type="hidden" name="sesNo" value="1"> <input
								type="hidden" name="action" value="go_select_seat">
						</form>
					</div>
				</div>

				<div class="col-3">
					<div class="card border-primary mb-3">
						<div class="card-header">會員</div>
						<div class="card-body">Content</div>
					</div>

					<div class="card border-primary mb-3">
						<div class="card-header">購物清單</div>
						<div class="card-body">
							<table class="table">
								<tbody>
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
					<button id="nextStep" class="btn btn-primary btn-lg">下一步</button>
				</div>
			</div>
		</div>
		<!-- PUT HERE End -->

		<!-- Book Tickets Start -->
		<%@ include file="/front-end/files/frontend_bookTicketsTamplate.file"%>
		<!-- Book Tickets End -->

		<!-- Footer Start -->
		<%@ include file="/front-end/files/frontend_footer.file"%>
		<!-- Footer End -->
	</div>

	<%@ include file="/front-end/files/frontend_importJs.file"%>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
	<script>
		$(function() {
			$("#tabs").tabs();
		});
		$("#nextStep").click(function() {
			$("#form").submit();
		});
		
		$("#grade-number").text(getGradeNumber($("#grade-word").text()));
		
		function getGradeNumber(gradeWord) {
			if ("普遍級" === gradeWord) {
				return "0+";
			} else if ("保護級" === gradeWord) {
				return "6+";
			} else if ("輔導級" === gradeWord) {
				return "12+";
			} else {
				return "18+";
			}
		}
	</script>
</body>
</html>