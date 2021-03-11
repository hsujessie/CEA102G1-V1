<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Seenema電影院</title>
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
	$(function() {
		$("#tabs").tabs();
	});
</script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div id="a" class="col-9">
				<form method="post"
					action="<%=request.getContextPath()%>/ordMas/ordMas.do" id="form">
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

							<jsp:useBean id="sesSvc" scope="page"
								class="com.session.model.SesService" />
							<jsp:useBean id="theSvc" scope="page"
								class="com.theater.model.TheaterService" />
							<jsp:useBean id="ticTypSvc" scope="page"
								class="com.ticket_type.model.Ticket_typeService" />
							<jsp:useBean id="ideSvc" scope="page"
								class="com.identity.model.IdentityService" />

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
									<td>$<span>${fooCartVO.fooPrice}</span>
									</td>
									<td>${fooCartVO.fooCount}</td>
									<td>123</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>


					<input type="hidden" name="sesNo" value="${param.sesNo}"> <input
						type="hidden" name="action" value="check_out">
				</form>
			</div>

			<div id="b" class="col-3">
				<div class="card border-primary mb-3">
					<div class="card-header">會員專區</div>
					<div class="card-body">XXX 你好</div>
				</div>

				<div class="card border-primary mb-3">
					<div class="card-header">購物清單</div>
					<div class="card-body">
<!-- 						<table class="table"> -->
<!-- 							<tbody> -->
<%-- 								<c:forEach var="ticTypCartVO" items="${ticTypCartSet}"> --%>
<!-- 									<tr> -->
<!-- 										<td> -->
<%-- 											<p>${ideSvc.getOneDept(ticTypCartVO.ideNo).ide_name}</p> --%>
<!-- 											<p class="text-right"> -->
<%-- 												X <span>${ticTypCartVO.ticLisPrice}</span> --%>
<!-- 											</p> -->
<!-- 										</td> -->
<!-- 									</tr> -->
<%-- 								</c:forEach> --%>
<%-- 								<c:forEach var="fooCartVO" items="${fooCartSet}"> --%>
<!-- 									<tr> -->
<!-- 										<td> -->
<%-- 											<p>${fooCartVO.fooName}</p> --%>
<!-- 											<p class="text-right"> -->
<%-- 												X <span>${fooCartVO.fooCount}</span> --%>
<!-- 											</p> -->
<!-- 										</td> -->
<!-- 									</tr> -->
<%-- 								</c:forEach> --%>
<!-- 								<tr> -->
<!-- 									<td> -->
<!-- 										<p class="text-right"> -->
<!-- 											合計 <span id="orderTotal">0</span> -->
<!-- 										</p> -->
<!-- 									</td> -->
<!-- 								</tr> -->
<!-- 							</tbody> -->
<!-- 						</table> -->
					</div>
				</div>
				<button id="nextStep" class="btn btn-primary btn-lg">確認結帳</button>
			</div>
		</div>
	</div>
</body>
</html>