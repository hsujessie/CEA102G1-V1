<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
    background-color: rgb(255,255,255);
    border-radius: 10px;
	-webkit-box-shadow: 0px 3px 5px rgb(8,8,8, 0.3);
	-moz-box-shadow: 0px 3px 5px rgb(8,8,8, 0.3);
	box-shadow: 0px 3px 5px rgb(8,8,8, 0.3);
  }
  th,td{
  	box-sizing:border-box;
    border-radius: 10px;
  }
  th{
  	width: 200px;
  	padding: 10px 0px 10px 70px;
  }
  td{
  	width: 250px;
  	padding: 10px 20px 10px 30px;
    border-bottom: 2px dotted #bb9d52;
  }
  .listOne-h3-pos,#a-color{
  	margin-left: 50%;
  }
  .listOne-h3-pos{
  	display: inline-block; 
  }
  #a-color{
  	font-size: 16px;
  }
</style>
</head>
<body>
        <div class="wrapper">
            <!-- Nav Bar Start -->
			<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set>
            <%@ include file="/front-end/files/frontend_navbar.file"%>
            <!-- Nav Bar End -->


            <!-- Page Header Start --> <!-- 看自己需不需要標題 -->
<!--             <div class="page-header"> -->
<!--                 <div class="container"> -->
<!--                     <div class="row"> -->
<!--                         <div class="col-12"> -->
<!--                             <h2>Front-End</h2>  -->
<!--                         </div> -->
<!--                     </div> -->
<!--                 </div> -->
<!--             </div> -->
            <!-- Page Header End -->


            <!-- PUT HERE Start -->
            <div class="container-fluid">
            	<div class="row">
<!--             	<div class="col-2"></div> -->
                       <!-- listOneMovie Start -->
                       <jsp:useBean id="sesSvc" scope="page" class="com.session.model.SesService" />
					   <jsp:useBean id="movSvc" scope="page" class="com.movie.model.MovService" />
				<div class="col">
                       <h3 class="h3-style listOne-h3-pos">訂單詳情</h3>
                       <h3>${ordMasVO.ordMasStatus == "0"?"未取票":(ordMasVO.ordMasStatus == "1")?"已取票" :"已取消"}</h3>
					   <table>
							<tr>
								<th>取票二維碼</th>
								<td><img src="<%=request.getContextPath()%>/util/generateQRcode?ordMasNo=${ordMasVO.ordMasNo}"></td>
							</tr>
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
								<td>${sesSvc.getOneSes(ordMasVO.sesNo).sesTime}</td>
							</tr>
							<tr>
							<jsp:useBean id="ticLisSvc" scope="page" class="com.ticket_list.model.TicLisService" />
								<th>座位</th>
								<td>${ticLisSvc.getSeatNo(ticLisSvc.getByOrdMasNo(ordMasVO.ordMasNo))}</td>
							</tr>
							<tr>
								<th>商品</th>
								<td>
									<table class="table">
							<tbody>
								
								<jsp:useBean id="ideSvc" scope="page" class="com.identity.model.IdeService" />
								<jsp:useBean id="ticTypSvc" scope="page" class="com.ticket_type.model.TicTypService" />
								<jsp:useBean id="fooLisSvc" scope="page" class="com.food_list.model.FooLisService" />
								<c:forEach var="ticTypCartVO" items="${ticLisSvc.convertToTicTypCart(ticLisSvc.getByOrdMasNo(ordMasVO.ordMasNo))}">
									<tr>
										<td>
											<p>${ideSvc.getOneDept(ticTypSvc.getOneTicket_type(ticTypCartVO.ticTypNo).ide_no).ide_name}</p>
											<p class="text-right">
												<span>${ticTypCartVO.ticLisPrice}</span>
												X
												<span>${ticTypCartVO.ticTypCount}</span>
											</p>
										</td>
									</tr>
								</c:forEach>
								<jsp:useBean id="fooSvc" scope="page" class="com.food.model.FooService" />
								<c:forEach var="fooCartVO" items="${fooLisSvc.convertToFooCart(fooLisSvc.getByOrdMasNo(ordMasVO.ordMasNo))}">
									<tr>
										<td>
											<p>${fooSvc.getOneFoo(fooCartVO.fooNo).fooName}</p>
											<p class="text-right">
												<span>${fooCartVO.fooPrice}</span>
												X
												<span>${fooCartVO.fooCount}</span>
											</p>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
								</td>
							</tr>
							<tr>
								<th>總價</th>
								<td>${ordMasVO.ordMasPrice}</td>
							</tr>
							
						</table>			
			   			<a class="combtn" href="<%=request.getContextPath()%>/front-end/ordMas/listMemOrder.jsp">回訂單列表</a>
   </div>
<!--    <div class="col-2"></div> -->
                       <!-- listOneMovie End -->
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
</body>
</html>