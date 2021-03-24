<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import = "javax.servlet.http.* " %>
<%	
// List<MemberVO> memberVO = (List<MemberVO>)request.getAttribute("list");//從controller傳回View,jsp不能簡寫唷!!
MemberVO memberVO = (MemberVO) request.getAttribute("MemberVO");
// List<MemberVO> list = (List<MemberVO>)session.getAttribute("list");
// pageContext.setAttribute("memberVO",memberVO);
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
                    
                    	<h3 class="h3-style" style="display: inline-block;">已修改會員資料</h3>
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
									<th>會員編號</th>
									<th>會員名稱</th>
									<th>會員帳號</th>
									<th>會員密碼</th>
									<th>會員信箱</th>
									<td>會員圖片</td>
									<th>會員狀態</th>
								</tr>				
							</thead>
									
							<tbody>
								<tr class="sty-height" valign='middle'>
									<td>${MemberVO.memNo}</td>
									<td>${MemberVO.memName}</td>
									<td>${MemberVO.memAccount}</td>
									<td>${MemberVO.memPassword}</td>
									<td>${MemberVO.memMail}</td>
									<td><img src="<%=request.getContextPath()%>/util/imgReader${MemberVO.memImg}"width="50" height="50"></td>
 									<td>${(MemberVO.memstatus==0)?"未啟動":(MemberVO.memstatus==1?"已啟動":"已停權")}</td>
								</tr>
							</tbody>
						</table>
                       <!-- listSession End -->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
</body>
</html>