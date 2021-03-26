<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.board.model.*"%>
<%@ page import = "javax.servlet.http.* " %>
<%	
List<BoardVO> boardVO = (List<BoardVO>) request.getAttribute("BoardNolist");
System.out.println("進入jsp");
%>
<html>
<head>
	<title>公告資料 - listOneBoard.jsp</title>
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
		margin: 20px 0 0 0;
	}
	.but-01 {
    position: absolute;
    top: 40%;
    right: 0;
    left: 8%;
    bottom: 20px;
    font-size: 14px;
    text-decoration: none;
    background-color: transparent;
    border: 20px;
    color: #fff
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
                    
                    	<h3 class="h3-style" style="display: inline-block;">查詢公告資料</h3>
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
                    	<!-- success message End -->
						
                    	<!-- search Start -->
                    	<div class="row " style="margin: 0px 0 20px -200px;">          
			                <div class="col-2"></div>
	                        <div class="col-10">          
	                           	                 
                        	</div>                 
                        </div>
                    	<!-- search End -->
                        
                    	<!-- listSession Start -->
			            <table class="table table-hover">
							<thead>
								<tr style="border-bottom: 3px solid #bb9d52;">
									<th>公告編號</th>
									<th>公告種類編號</th>
									<th>公告內容</th>
									<th>公告日期日期</th>
								</tr>				
							</thead>
									
							<tbody>
							<c:forEach var="boardVO" items="${BoardNolist}" >
								<tr class="sty-height" valign='middle'>
									<td>${boardVO.boaNo}</td>
									<td>${boardVO.boatypNo}</td>
									<td>${boardVO.boaContent}</td>
									<td>${boardVO.boaTime}</td>
								</tr>
							</c:forEach>
							</tbody>
							 
						</div>
						</table>
						<div class="but-01" style="margin-left: 45%;"> 
								<a href="<%=request.getContextPath()%>/back-end/board/listAllBoard_new.jsp">
									<button  style="width:70px;height:30px;text-align:center;font-size:18px;color:#aa9166;">回上頁</button>
								</a>
                       <!-- listSession End -->
                    
                    </div>
                    
                </main>
                
            </div>
            
        </div>
        				
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
		
<!-- <br>本網頁的路徑:<br><b> -->
<%--    <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br> --%>
<%--    <font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%> </b>		 --%>
</body>
</html>