<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.board.model.*"%>
<%@ page import = "javax.servlet.http.* " %>
<%	
BoardVO boardVO = (BoardVO) request.getAttribute("BoardVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Front-End</title>
<%@ include file="/front-end/files/frontend_importCss.file"%>
<link rel="stylesheet" href="https://unpkg.com/purecss@2.0.5/build/pure-min.css" integrity="sha384-LTIDeidl25h2dPxrB2Ekgc9c7sEC3CWGM6HeFmuDNUjX76Ert4Z4IY714dhZHPLd" crossorigin="anonymous">
</head>
<style>
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
  .nav-bar .navbar {
   height: 8%;
    padding: 0;
    }

</style>
<body>
        <div class="wrapper">
            <!-- Nav Bar Start -->
			<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set>
            <%@ include file="/front-end/files/frontend_navbar.file"%>
            <!-- Nav Bar End -->
            <c:if test="${not empty errorMsgs}">
					<font style="color:red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color:red">${message}</li>
							</c:forEach>
						</ul>
				</c:if>


            <!-- Page Header Start --><!-- 看自己需不需要標題 -->
            <div class="page-header">
                <div class="container">
                    <div class="row">
                        <div class="col-12" >
                            <h2 style="color: rgba(0,255,197,0.8);text-shadow: 7px 5px 4px rgba(0,0,0,1);">影  城  公  告  查  詢</h2> 
                        </div>
                    </div>
                </div>
            </div>
            <!-- Page Header End -->
								  

            <!-- PUT HERE Start -->
            <div class="row " style="margin: 0px 0 20px 0px;"> 
            <div class="col-10">          
		                           	<FORM class="pure-form" METHOD="post" ACTION="<%=request.getContextPath() %>/Board/board.do">		
			                        
			                        	<jsp:useBean id="boardSvc" scope="page" class="com.board.model.BoardService" />

											<b>選擇公告編號:</b>
												<select size="1" name="boaNo">
												   <c:forEach var="boardVO" items="${boardSvc.all}" > 
												      <option value="${boardVO.boaNo}">${boardVO.boaNo}
												    </c:forEach>   
												</select>
												
											 <a class="btn btn-light btn-brd grd1 effect-1">
												  <input  type="hidden" name="action" value="get_For_Display">
												  <input class="pure-input-rounded" type="submit" value="送出" style="width:50px;height:30px;text-align:center;font-size:12px;color:#aa9166;">
				                        	 </a>
			                        </FORM>
			                        <jsp:useBean id="boardTypeSvc" scope="page" class="com.board_type.model.BoardTypeService" />
										<FORM  class="pure-form" METHOD="post" ACTION="<%=request.getContextPath()%>/Board/board.do" >
											<b>選擇公告種類編號:</b>
												 <select size="1" name="boatypNo">
												    <c:forEach var="boardTypeVO" items="${boardTypeSvc.all}" > 
												        <option value="${boardTypeVO.boatypNo}">${boardTypeVO.boatypName}
												    </c:forEach>   
												  </select>
				                        
					        			<a class="btn btn-light btn-brd grd1 effect-1">
					        				<input  type="hidden" name="action" value="get_For_Display2">
											<input class="pure-input-rounded" type="submit" value="送出" style="width:50px;height:30px;text-align:center;font-size:12px;color:#aa9166;">
										</a>
		                    		</FORM>                 
                        	</div>
                        	
                        </div>
<!--                         <div style="border: 2px solid #ffffff ;width: 340px;color:#bb9d52;margin: 0px 0 20px 50px;">	  -->
<%--                 		     <%@ include file="/front-end/board/pages/page1.file" %> --%>
<!--                 		 </div> -->
                        <table class="table table-hover" style="border-bottom: 3px solid #bb9d52;width: 90%;margin: 0px 0 20px 50px;">
                       
							<thead>
								<tr >
									<tr style="border-bottom: 3px solid #bb9d52;">
										<th>公告編號</th>
										<th>公告種類編號</th>
										<th>公告內容</th>
										<th>公告日期日期</th>
								   </tr>
								</tr>				
							</thead>
									<tbody >
											<tr class="sty-height" valign='middle'onMouseOver="this.style.backgroundColor='ddca64';" onMouseOut="this.style.backgroundColor='ffffff';">
											
												<td>${boardVO.boaNo}</td>
												<td>${boardVO.boatypNo}</td>
												<td>${boardVO.boaContent}</td>
												<td>${boardVO.boaTime}</td>
		<%-- 								<td>${(memberVO.memstatus==0)?"未啟動":(memberVO.memstatus==1?"已啟動":"已停權")}</td> --%>
											</tr>
									</tbody>
							</table>   
							<div style="margin: 0px 0 20px 50px;">
<%-- 								 <%@ include file="/front-end/board/pages/page2.file" %>  --%>
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