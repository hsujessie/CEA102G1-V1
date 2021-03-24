<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>

<html>
<head>
	<title>Order Management</title>
	<%@ include file="/back-end/files/sb_head.file"%>

<style>
	.success-span{
	    color: #bb9d52;
		position: absolute;
	    top: 8%;
	    left: 17%;
	}
	.th-adjust{
		width: 120px;
	}
	.form-sty{
		margin: 20px 0 0 10px;
	}
	img {
		width: 50px;
		height: 50px;
	}
</style>
<body class="sb-nav-fixed">

		<%@ include file="/back-end/files/sb_navbar.file"%> <!-- 引入navbar (上方) -->
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
				<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set> <!-- 給sb_sidebar.file的參數-Home -->
				<%@ include file="/back-end/files/sb_sidebar.file"%> <!-- 引入sidebar (左方) -->
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                    	
                    	<!-- error message Start -->
                    	
                    	<!-- error message End -->
						
                    	<!-- search Start -->
                    	<div class="row " style="margin: -60px 0 20px 0;">         
			                <div class="col-2"></div>
	                        <div class="col-10">          
		            			<jsp:useBean id="ordMasSvc" scope="page" class="com.order_master.model.OrdMasService"/>                        
	                           	<FORM class="form-sty" METHOD="post" ACTION="<%=request.getContextPath()%>/movie/mov.do">				                        
			                        <b>電影名稱</b>
			                            <select name="mov_no" style="width: 80px;">
			                                <option value=""></option>
			                                <c:forEach var="movVO" items="${movSvcAll.all}" >
			                                    <option value="${movVO.movno}">${movVO.movname}
			                                </c:forEach>
			                            </select>
			                        <b>電影類型</b>
			                            <select name="mov_type">
			                                <option value=""></option>
											<option value="劇情片">劇情片</option>
											<option value="動作片">動作片</option>
											<option value="動畫片">動畫片</option>
											<option value="喜劇片">喜劇片</option>
											<option value="愛情片">愛情片</option>
											<option value="科幻片">科幻片</option>
											<option value="恐怖片">恐怖片</option>
			                            </select>
			                        <b>選擇年份</b>
			                        <select name="mov_ondate_year">
			                            <option value=""></option>
			                            <c:forEach var="year" begin="1970" end="<%= (int) (java.util.Calendar.getInstance().get(java.util.Calendar.YEAR))+1%>">
			                                <option value="${year}">${year}年</option>
			                            </c:forEach>
			                        </select>
			                        <b>選擇月份</b>
			                        <select name="mov_ondate_month">
			                            <option value=""></option>
			                            <c:forEach var="month" begin="1" end="12">
			                                <option value="${month}">${month}月</option>
			                            </c:forEach>
			                        </select>
			                        <input type="hidden" name="action" value="listMovies_ByCompositeQuery">
				        			<a class="btn btn-light btn-brd grd1 effect-1">
										<input type="submit" value="搜尋" class="input-pos">
				        			</a>
		                    	</FORM>                    
                        	</div>                 
                        </div>
                    	<!-- search End -->
                    	
                    	<!-- listAllMovie Start -->
			            <table class="table table-hover">
							<thead>
								<tr style="border-bottom: 3px solid #bb9d52;">
									<th>列表編號</th>
									<th class="th-adjust">會員姓名</th>
									<th class="th-adjust">電影名稱</th>
									<th>場次時間</th>
									<th>訂單日期</th>
									<th>訂單總金額</th>
									<th>訂單狀態</th>
									<th>修改</th>
								</tr>				
							</thead>
									
							<tbody>
								<jsp:useBean id="movSvc" class="com.movie.model.MovService"/>
								<jsp:useBean id="sesSvc" class="com.session.model.SesService"/>
								<jsp:useBean id="memSvc" class="com.member.model.MemberService"/>
								<c:forEach var="ordMasVO" items="${ordMasSvc.all}" varStatus="index">					
								<tr class="sty-height" valign='middle' ${(ordMasVO.ordMasNo==param.ordMasNo) ? 'style="background-color:#bb9d52; color:#fff;"':''}>
									<td>${index.count}</td>
									<td>${memSvc.getOneMember(ordMasVO.memNo).memName}</td>
									<td>${movSvc.getOneMov(sesSvc.getOneSes(ordMasVO.sesNo).movNo).movname}</td>
									<td>${sesSvc.getOneSes(ordMasVO.sesNo).sesDate} <fmt:formatDate value="${sesSvc.getOneSes(ordMasVO.sesNo).sesTime}" pattern="HH:mm" /></td>
									<td><fmt:formatDate value="${ordMasVO.ordMasDate}" pattern="yyyy-MM-dd HH:mm" /></td>
									<td>
										$
										<span>${ordMasVO.ordMasPrice}</span>
									</td>
									<td>${ordMasVO.ordMasStatus == "0" ? "未取票" :(ordMasVO.ordMasStatus == "1") ? "已取票" : "已取消"}</td>
									
								</tr>
								</c:forEach>
							</tbody>
						</table>
                       <!-- listAllMovie End -->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
		

</body>
</html>