<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import = "javax.servlet.http.* " %>
<%	
// List<MemberVO> memberVO = (List<MemberVO>)request.getAttribute("list");//從controller傳回View,jsp不能簡寫唷!!

	MemberVO memberVO = (MemberVO) request.getAttribute("MemberVO");
	System.out.println(memberVO);
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
		margin: 20px 0 0 -100;
	}
	.btn-pos{ 
	position: absolute; 
   
     right:50%; 
     } 
	/* 	================================== */
table{
  width: 70%;
  border-collapse: collapse;
  margin-left:100px;
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
#table:hover{
background:#cd9a0b;
}
.message{ 
font-size:16px;
border-width:;
border-style:dashed;
border-color:#ffffff;
padding:5px;
width:550px;
height:35px;
top:20px;
left:430px;
position:absolute;

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
						
							<!--錯誤表列 -->
						<div class="message" ">
							<c:if test="${not empty errorMsgs}">
								<b>	<font style="color:red">請修正以下錯誤:</font></b>
											<c:forEach var="message" items="${errorMsgs}">
												<b><span style="color: red">${message}</span></b>
											</c:forEach>
								</c:if>
							</div>
                    	<!-- success message End -->
						
                    	<!-- search Start -->
                    	<div class="row " style="margin: 0px 0 20px -200px;">          
			                <div class="col-2"></div>
	                        <div class="col-10">          
	                           	<FORM class="form-sty" METHOD="post" ACTION="<%=request.getContextPath()%>/Member/member.do">		
									<div class="main">
										<table>
											  <tr>
											    <th>會員編號:</th>
											    <td><%=memberVO.getMemNo()%></td>
											  </tr>
											  <tr>
											    <th>會員姓名:</th>
											    <td><%=memberVO.getMemName()%></td>
											  </tr>
											  <tr>
											    <th>會員帳號:</th>
											    <td><%=memberVO.getMemAccount()%></td>
											  </tr>
											  <tr>
											    <th>會員密碼:</th>
											    <td><%=memberVO.getMemPassword()%></td>
											  </tr>
											  <tr>
											    <th>會員錢包:</th>
											    <td><input type="TEXT" name="memWallet" size="5"value="<%=memberVO.getMemWallet()%>" /></td>
											  </tr>
											  <tr>
											    <th>會員狀態:</th>
											    <td><input type="TEXT" name="memstatus" size="5"value="<%=memberVO.getMemstatus()%>" /></td>
											  </tr>
											  <tr>
											    <th>會員照片:</th>
											    <td>
											    <img src="<%=request.getContextPath()%>/util/imgReader${MemberVO.memImgParam}" style="width:100px; height:100px;">
											    </td>
											  </tr>
										</table>
									</div>
											<input type="hidden" name="memNo" value="<%=memberVO.getMemNo()%>">
											<input type="hidden" name="memName" value="<%=memberVO.getMemName()%>">
											<input type="hidden" name="memAccount" value="<%=memberVO.getMemAccount()%>">
											<input type="hidden" name="memPassword" value="<%=memberVO.getMemPassword()%>">
											<input type="hidden" name="memMail" value="<%=memberVO.getMemMail()%>">
											
					        			<a class="btn btn-light btn-brd grd1 effect-1 btn-pos">
											<input type="submit" value="修改" class="input-pos">
											<input type="hidden" name="action" value="update">
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
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
<br>本網頁的路徑:<br><b>
   <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%> </b>
</body>
</html>