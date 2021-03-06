<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.board.model.*"%>
<%@ page import = "javax.servlet.http.* " %>
<%	
BoardService boardSvc_useBean = new BoardService();
List<BoardVO> list = boardSvc_useBean.getAll();
pageContext.setAttribute("list",list);
%>
<html>
<head>
	<title>Sessions Management</title>
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
		margin: 20 0 20 0;
	}
	input, button, select, optgroup, textarea {
    margin-right: 8px;
    }
    td{
    margin-right: 60px;
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
                    
                    	<h3 class="h3-style" style="display: inline-block;">所有公告資料</h3>
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
			                        
			                        	<jsp:useBean id="boardSvc" scope="page" class="com.board.model.BoardService" />

											<b>選擇公告編號:</b>
												<select size="1" name="boaNo">
												   <c:forEach var="boardVO" items="${boardSvc.all}" > 
												      <option value="${boardVO.boaNo}">${boardVO.boaNo}
												    </c:forEach>   
												</select>
												
											 <a class="btn btn-light btn-brd grd1 effect-1">
												  <input type="hidden" name="action" value="getOne_For_Display">
												  <input type="submit" value="送出" class="input-pos">
				                        	 </a>
			                        </FORM>
			                        <jsp:useBean id="boardTypeSvc" scope="page" class="com.board_type.model.BoardTypeService" />
										<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Board/board.do" >
											<b>選擇公告種類編號:</b>
												 <select size="1" name="boatypNo">
												    <c:forEach var="boardTypeVO" items="${boardTypeSvc.all}" > 
												        <option value="${boardTypeVO.boatypNo}">${boardTypeVO.boatypName}
												    </c:forEach>   
												  </select>
				                        
					        			<a class="btn btn-light btn-brd grd1 effect-1">
											<input type="submit" value="送出" class="input-pos">
											<input type="hidden" name="action" value="getOne_For_Display2">
					        			</a>
		                    		</FORM>                 
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
							<%@ include file="/back-end/Board/pages/page1.file" %> 
	<c:forEach var="boardVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
								
									<tr class="sty-height" valign='middle'>
										<td>${boardVO.boaNo}</td>
										<td>${boardVO.boatypNo}</td>
										<td>${boardVO.boaContent}</td>
										<td>${boardVO.boaTime}</td>
<%-- 										<td>${(memberVO.memstatus==0)?"未啟動":(memberVO.memstatus==1?"已啟動":"已停權")}</td> --%>
																				
										<td>
											<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/Board/board.do">
												<a class="btn btn-light btn-brd grd1 effect-1">
													<input type="submit" value="修改" class="input-pos">
								        			<input type="hidden" name="boaNo"  value="${boardVO.boaNo}">
													<input type="hidden" name="action"	value="getOne_For_Update">
							        			</a>
											</FORM>
										</td>
										
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<%@ include file="/back-end/Board/pages/page2.file" %> 
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