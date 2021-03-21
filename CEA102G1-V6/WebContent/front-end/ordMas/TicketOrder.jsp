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
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">


<style>
div>p {
/* 	font-size: 8px; */
}

div>p>span {
	margin: 0 3px;
}

div>p>img {
	width: 30px;
	height: 40px;
}

img.card-img-top {
	max-width: 100%;
	max-height: 100%;
}
.foodImg {
/* 	width: 200px; */
	height: 150px;
}

#grade {
	border: 1px solid black;
	height: 100px;
	width: 100px;
}
#nextStep {
	position: absolute;
	bottom: 1%;
	right: 5%;
}
#grade-number {
	color: white;
}

#grade-number,#grade-word {
	padding-top: 15px;
	height: 50px;
}

.list-group-item, #tabs_item, .card {
	border: 1px solid #aa9166;
	margin-bottom: 10px;

}

.info {
	border: 0;
	background-color: #b39d76;
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

.ui-widget.ui-widget-content,.ui-widget-content {
	border:0;
}

.card-header {
	background-color: #c5b497;
}

select.form-control{
	width:70px;
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
													<jsp:useBean id="ticTypSvc" scope="page" class="com.ticket_type.model.TicTypService" />
													<jsp:useBean id="ideSvc" scope="page" class="com.identity.model.IdeService" />
													<jsp:useBean id="theSvc" scope="page" class="com.theater.model.TheService" />
													<c:forEach var="ticTypVO" items="${ticTypSvc.getTicTypsByMovVerNo(theSvc.getOneTheater(sesSvc.getOneSes(param.sesNo).theNo).movver_no)}">
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
												<c:forEach var="fooVO" items="${fooCatSvc.getFoosByFooCatNo(fooCatVO.fooCatNo)}">
													<div class="col-4">
														<div class="card">
														<div class="foodImg">
															<img src="<%=request.getContextPath()%>/util/imgReader${fooVO.fooImgParam}" class="card-img-top">
															</div>
															<div class="card-body">
																<h5 class="card-title foodName">${fooVO.fooName}</h5>
																<p class="card-text">
																	$
																	<span class="foodPrice">${fooVO.fooPrice}</span>
																</p>
																<select name="fooNo${fooVO.fooNo}" class="form-control foodCount">
																	<c:forEach varStatus="index" begin="0" end="10">
																		<option value="${index.index}">${index.index}
																	</c:forEach>
																</select>
															</div>
														</div>
													</div>
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
		
 
        
        $(".ticketCount").change(function() {
        	//計算此票種的小計
            $(this).parent().next().text($(this).parent().prev().children("span.ticketPrice").text() * $(this).val());
        	
            let ticketCountDoms = $(".ticketCount");
        	//判斷是否有選票
        	let counts = 0;
        	for (let i = 0; i < ticketCountDoms.length; i++) {
        		counts += parseInt($(ticketCountDoms[i]).val());
        	}
        	if (counts !== 0) {
        		$("#nextStep").text("繼續");
        		$("#nextStep").prop("disabled",false);
        	} else {
        		$("#nextStep").text("請至少選一張票");
        		$("#nextStep").prop("disabled",true);
        	}
        	
        	//判斷還能選多少張票
        	let countDiff = 10 - counts;
        	for (let i = 0; i < ticketCountDoms.length; i++) {
        		let nowSelect = parseInt($(ticketCountDoms[i]).val());
        		$(ticketCountDoms[i]).empty();
        		for (let j = 0; j <= (countDiff + nowSelect); j++) {
                    if (nowSelect === j) 
                        $(ticketCountDoms[i]).append("<option value=" + j + " selected>" + j + "</option>");
                    else 
                        $(ticketCountDoms[i]).append("<option value=" + j + ">" + j + "</option>");
                }
        	}
        });
        
        $(".ticketCount").change(function() {
        	let productPrice = $(this).parent().prev().children("span.ticketPrice").text();
        	let productCount = parseInt($(this).val());
        	let productName = $(this).parent().prev().prev().text();
        	let tableStructure = "";
        	
        	let idName = "#" + productName;
        	if (productCount !== 0) {
        		if ($(idName)) {
        			$(idName).remove();
        		}
        		
        		tableStructure += "<tr id='"+productName +"'><td>";
        		tableStructure += "<p>"+ productName +"</p>";
        		tableStructure += "<p class='text-right'>$";
        		tableStructure += "<span style='margin:3px;'>" + productPrice + "</span>X";
        		tableStructure += "<span style='margin:3px;'>" + productCount + "</span>=";
        		tableStructure += "<span class='subtotal' style='margin:3px;'>"+ (productPrice * productCount) +"</span>";
        		tableStructure += "</p></td></tr>";
        	} else {
        		$(idName).remove();
        	}
        	$("#addhere").before(tableStructure);
        	
        });
        
        $(".foodCount").change(function() {
        	let productPrice = $(this).prev().children("span.foodPrice").text();
        	let productCount = parseInt($(this).val());
        	let productName = $(this).prev().prev().text();
        	let tableStructure = "";
        	
        	let target = $(this).attr("name");
        	let idName = "#" + target;
        	if (productCount !== 0) {
        		if ($(idName)) {
        			$(idName).remove();
        		}
        		
        		tableStructure += "<tr id='"+target +"'><td>";
        		tableStructure += "<p>"+ productName +"</p>";
        		tableStructure += "<p class='text-right'>$";
        		tableStructure += "<span style='margin:3px;'>" + productPrice + "</span>X";
        		tableStructure += "<span style='margin:3px;'>" + productCount + "</span>=";
        		tableStructure += "<span class='subtotal' style='margin:3px;'>"+ (productPrice * productCount) +"</span>";
        		tableStructure += "</p></td></tr>";
        		
        	} else {
        		$(idName).remove();
        	}
        	$("#addhere").before(tableStructure);
        });
        
        $(".ticketCount,.foodCount").change(function() {
        	let orderList = $(".subtotal");
        	let totalPrice = 0;
        	
        	for (let i = 0; i < orderList.length; i++) {
        		totalPrice += parseInt($(orderList[i]).text());
        	}
        	$("#orderTotal").text(totalPrice);
        })
		
	</script>
</body>
</html>