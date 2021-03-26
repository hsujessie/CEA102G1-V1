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
	<title>修改公告資料</title>
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
table{
  width: 100%;
  border-collapse: collapse;
}

table tr{
  border-bottom: solid 2px white;
}

table tr:last-child{
  border-bottom: none;
}

table th{
  position: relative;
  width: 25%;
  background-color: #7d7d7d;
  color: white;
  text-align: center;
  padding: 10px 0;
}

table th:after{
  display: block;
  content: "";
  width: 0px;
  height: 0px;
  position: absolute;
  top:calc(50% - 10px);
  right:-10px;
  border-left: 10px solid #7d7d7d;
  border-top: 10px solid transparent;
  border-bottom: 10px solid transparent;
}

table td{
  text-align: left; 
  width: 70%;
/*   text-align: center; */
  background-color: #eee;
  padding: 10px 40px;
}

.main {
  margin: 20px auto;
  item-align: center;
  width: 80%;
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
			                <div class="col-2"></div>
	                        <div class="col-10">          
	                           	<FORM class="form-sty" METHOD="post" ACTION="<%=request.getContextPath()%>/Board/board.do">		
			         					
			         				 <div class="main">
											<table>
											  <tr>
												    <th>公告編號:<font color=red><b>*</b></font></th>
												    <td class="text"><%=boardVO.getBoaNo()%></td>
											  </tr>
											  <jsp:useBean id="boardTypeSvc" scope="page" class="com.board_type.model.BoardTypeService" />
											  <tr>
												    <th>公告種類編號:<font color=red><b>*</b></font></th>
												    <td class="text">
													    <select size="1" name="boatypNo">
															<c:forEach var="boardTypeVO" items="${boardTypeSvc.all}">
																<option value="${boardTypeVO.boatypNo}" ${(boardVO.boatypNo==boardTypeVO.boatypNo) ? 'selected' : ' ' } >${boardTypeVO.boatypName} </option>
															</c:forEach>
														</select>
													</td>
											  </tr>
											  <tr>
											    <th>公告內容:</th>
											    <td class="text"><input type="TEXT" name="boaContent" size="45"	value="<%=boardVO.getBoaContent()%>" /></td>
											  </tr>
										
					         			</table>
									</div>
		         					<a class="btn btn-light btn-brd grd1 effect-1 btn-pos">
										<input type="submit" value="修改" class="input-pos">
										<input type="hidden" name="boaNo" value="<%=boardVO.getBoaNo()%>">
										<input type="hidden" name="action" value="update">
			        				</a>
		                    	</FORM>     
                        	</div>                 
                        </div>
                    	<!-- search End -->
                        
                    	<!-- listSession Start -->
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