<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.board.model.*"%>
<%@ page import = "javax.servlet.http.* " %>
<%	

BoardVO boardVO = (BoardVO) request.getAttribute("BoardVO");
pageContext.setAttribute("boardVO", boardVO);

%>
<html>
<head>
	<title>公告資料已修改</title>
	<%@ include file="/back-end/files/sb_head.file"%>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/datetimepicker/jquery.datetimepicker.css" />
</head>
<style>
	.success-span{
	    color: #bb9d52;
		position: absolute;
	    top: 10%;
	    left: 20%;
	    font-size: 16px;
	}
	.form-sty{
		margin: 20px 0 0 100px;
		 width: 100%;
		 border-collapse: collapse;
	}
 	.btn-pos{ 
	position: absolute; 
   
     right:40%; 
     } 
     .h3-style{
     padding: 10px 0 0 50px;
     }
/* 	================================== */

.but-01 {
    position: absolute;
    top: 40%;
    
    left: 8%;
    bottom: 0;
    font-size: 14px;
    text-decoration: none;
    background-color: transparent;
    border: 0px;
    color: #fff
    }


/* 	================================== */
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
                    
                    	<h3 class="h3-style" style="display: inline-block;">修改會員資料</h3>
						<!-- success message Start -->
<%-- 						<c:if test="${addSuccess != null}"> --%>
<!-- 							<span class="success-span">  -->
<%-- 								${addSuccess} --%>
<!-- 								<i class="far fa-smile-wink"></i> -->
<!-- 							</span> -->
<%-- 						</c:if> --%>
<%-- 						<c:if test="${updateSuccess != null }"> --%>
<!-- 							<span class="success-span">  -->
<%-- 								${updateSuccess} --%>
<!-- 								<i class="far fa-smile-wink"></i> -->
<!-- 							</span> -->
<%-- 						</c:if> --%>
							<c:if test="${not empty errorMsgs}">
								<font style="color:red">請修正以下錯誤:</font>
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
										<li style="color:red">${message}</li>
									</c:forEach>
								</ul>
							</c:if>
                    	<!-- success message End -->
						
                    	<!-- search Start -->
                    	<div class="row " style="margin: 0px 0 20px -400px;">          
	                        <div class="col-10">          
			         				 <div class="main">
<!-- 											<table> -->
<!-- 												<tr> -->
<!-- 													<th>公告編號</th> -->
<!-- 													<th>公告種類編號</th> -->
<!-- 													<th>公告內容</th> -->
<!-- 													<th>公告日期日期</th> -->
<!-- 												</tr> -->
<!-- 												<tr> -->
<%-- 													<td>${BoardVO.boaNo}</td> --%>
<%-- 													<td>${BoardVO.boatypNo}</td> --%>
<%-- 													<td>${BoardVO.boaContent}</td> --%>
<%-- 													<td>${BoardVO.boaTime}</td> --%>
<!-- 												</tr> -->
<!-- 											</table> -->
									</div>
                        	</div>                 
                        </div>
                        
                    	<!-- search End -->
                        
                    	<!-- listSession Start -->
			           <table class="table table-hover">
							<thead>
								<tr style="border-bottom: 3px solid #bb9d52;">
									<tr>
										<th>公告編號</th>
										<th>公告種類編號</th>
										<th>公告內容</th>
										<th>公告日期日期</th>
										<th>修改</th>
								   </tr>
								</tr>				
							</thead>
									
							<tbody>
									<tr class="sty-height" valign='middle'>
										<td>${BoardVO.boaNo}</td>
										<td>${BoardVO.boatypNo}</td>
										<td>${BoardVO.boaContent}</td>
										<td>${BoardVO.boaTime}</td>
<%-- 										<td>${(memberVO.memstatus==0)?"未啟動":(memberVO.memstatus==1?"已啟動":"已停權")}</td> --%>
									</tr>
							</tbody>
						</table>
						<div class="but-01" style="margin-left: 45%;"> 
							<a href="<%=request.getContextPath()%>/back-end/board/listAllBoard_new.jsp">
								<button  style="width:70px;height:30px;text-align:center;font-size:18px;color:#aa9166;">回上頁</button>
							</a> 
						</div>
                       <!-- listSession End -->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
		
	<br>本網頁的路徑:<br><b>
   <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%> </b>
</body>
</html>