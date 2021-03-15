<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>選位</title>

<link href='https://fonts.googleapis.com/css?family=Lato:400,700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resource/css/selectSeat/style.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resource/css/selectSeat/jquery.seat-charts.css">

<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="<%=request.getContextPath()%>/resource/js/selectSeat/jquery.seat-charts.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script>
	$(function() {
		$("#tabs").tabs();
	});
</script>

<style type="text/css">
/* 修改座位的顏色 */
div.seatCharts-seat {
	color: black;
	/* border: 1px solid black; */
}

div.seatCharts-seat.selected.first-class {
	background-color: cyan;
}

div.seatCharts-seat.available.first-class {
	color: black;
	/* background-color: white; */
}

div.seatCharts-seat.unavailable {
	background-color: red;
}

#seat-map {
	width: 100%;
}
/* div.front-indicator {
            width: 100%;
        } */
#a, #b {
	border: 1px solid red;
}

div.seatCharts-legend {
	position: static;
}
</style>
</head>
<body>

	<div class="container">
		<div class="row">
			<div id="a" class="col-9">
				<form method="post" action="<%=request.getContextPath()%>/ordMas/ordMas.do" id="form">
					<div class="row">
						<div class="col">
							<div id="seat-map">
								<div id="legend"></div>
								<div class="front-indicator">螢幕</div>
							</div>
						</div>
					</div>
					<input type="hidden" name="sesNo" value="${param.sesNo}">
					<input type="hidden" name="chooseSeatNo" value="" id="chooseSeatNo">
					<input type="hidden" name="action" value="confirm_order">
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
						<table class="table">
							<tbody>
								<jsp:useBean id="ideSvc" scope="page" class="com.identity.model.IdeService" />
								<c:forEach var="ticTypCartVO" items="${ticTypCartSet}">
									<tr>
										<td>
											<p>${ideSvc.getOneDept(ticTypCartVO.ideNo).ide_name}</p>
											<p class="text-right">
												X
												<span>${ticTypCartVO.ticLisPrice}</span>
											</p>
										</td>
									</tr>
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
											<span id="orderTotal">0</span>
										</p>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<button id="nextStep" class="btn btn-primary btn-lg">繼續</button>
			</div>
		</div>
	</div>

	<script>
		var ticTypTotal = ${ticTypTotal};
		var sesSeatStatus = "${sesSeatStatus}";
		var seatMap = [];
		var rowNo = [];
		var columnNo = [];
		for (let i = 0; i < 20; i++) {
			let begin = i * 20;
			let end = (i + 1) * 20;
			let str = sesSeatStatus.substring(begin, end);

			str = str.replace(new RegExp('9', 'g'), "_");
			str = str.replace(new RegExp('3', 'g'), "_");
			str = str.replace(new RegExp('0', 'g'), "t");
			str = str.replace(new RegExp('1', 'g'), "f");
			seatMap.push(str);
		}

		let count = 0;
		for (let i = 0; i < seatMap.length; i++) {
			if (seatMap[i] === "____________________") {
				rowNo.push("");
				continue;
			}
			count++;
			rowNo.push(count);
		}

		count = 0;
		out: for (let j = 0; j < seatMap.length; j++) {
			let str = "";
			for (let i = 0; i < seatMap.length; i++) {
				str = str + seatMap[i].charAt(j);
			}
			if (str === "____________________") {
				columnNo.push("");
				continue out;
			}
			count++;
			columnNo.push(count);
		}

// 		console.log(seatMap);
// 		console.log(rowNo);
// 		console.log(columnNo);

		for (let i = 0; i < columnNo.length; i++) {
			if (columnNo[i] !== "" && columnNo[i] < 10) {
				columnNo[i] = "0" + columnNo[i];
			}
		}
		
		rowNo = replaceNo(rowNo);
		
		function replaceNo(arr) {
			let charArr = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
			let index = 0;
			for (let i = 0; i < arr.length; i++) {
				if (arr[i] !== "") {
					arr[i] = charArr[index];
					index++;
				}
			}
			return arr;
		}

	</script>
	<script src="<%=request.getContextPath()%>/resource/js/selectSeat/SelectSeatJS.js"></script>
	
</body>
</html>