<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.board.model.*"%>
<%@ page import = "javax.servlet.http.* " %>
<%	

%>
<jsp:useBean id="BoardVO" scope="request" class="com.board.model.BoardVO" />
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
                    
                    	<h3 class="h3-style" style="display: inline-block;">新增公告資料</h3>
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
	                        	<FORM class="form-sty" METHOD="post" ACTION="<%=request.getContextPath() %>/Board/board.do">        
	                           	  <jsp:useBean id="boardTypeSvc" scope="page" class="com.board_type.model.BoardTypeService" />
										<tr>
											<td>公告種類編號:<font color=red><b>*</b></font></td>
											<td>
												<select size="1" name="boatypNo">
														<c:forEach var="boardTypeVO" items="${boardTypeSvc.all}">
														<option value="${boardTypeVO.boatypNo}" ${(BoardVO.boatypNo==boardTypeVO.boatypNo) ? 'selected' : ' ' } >${boardTypeVO.boatypName} </option>
														</c:forEach>
												</select>
											</td>
										</tr> <br><br>  
										
										<tr>
											<td>公告內容:</td>
											<td><input type="TEXT" name="boaContent" size="45" value=" ${(empty boardVO) ? ' 請輸入公告' : '${boardVO.boaContent}'  }" /></td>
										</tr>   
										<a class="btn btn-light btn-brd grd1 effect-1">
										   <input type="hidden" name="action" value="insert">
										   <input type="submit" value="新增" class="input-pos"></FORM>
										</a>
									</FORM>	
                        	</div>                 
                        </div>
                    	<!-- search End -->
                        
                    	<!-- listSession Start -->
<!-- 			            <table class="table table-hover"> -->
<!-- 							<thead> -->
<!-- 								<tr style="border-bottom: 3px solid #bb9d52;"> -->
									
<!-- 								</tr>				 -->
<!-- 							</thead> -->
									
<!-- 							<tbody> -->
								
<!-- 							</tbody> -->
<!-- 						</table> -->
                       <!-- listSession End -->
                    
                    </div>
                </main>
                
            </div>
        </div>
		
<br>本網頁的路徑:<br><b>
   <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%> </b>		
</body>
</html>