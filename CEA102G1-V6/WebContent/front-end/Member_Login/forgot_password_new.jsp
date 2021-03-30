<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<%@ page import="java.util.*"%>
<%
MemberVO memberVO = (MemberVO) request.getAttribute("MemberVO"); 
List<MemberVO> memberVOList = (List<MemberVO>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Front-End</title>
<%@ include file="/front-end/files/frontend_importCss.file"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/datetimepicker/jquery.datetimepicker.css" />
<link rel="stylesheet" href="https://unpkg.com/purecss@2.0.5/build/pure-min.css" integrity="sha384-LTIDeidl25h2dPxrB2Ekgc9c7sEC3CWGM6HeFmuDNUjX76Ert4Z4IY714dhZHPLd" crossorigin="anonymous">
<script src="<%=request.getContextPath()%>/resource/jquery/jquery-3.5.1.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/popper/popper.min.js"></script>
</head>
<style>
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
  background-color: rgba(170,145,102,0.8);
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
.img{
padding:

}

.main {
  margin: 20px auto;
  item-align: center;
  width: 80%;
}
.btn-pos{ 
	position: absolute; 
   
     right:50%; 
     } 

body{
/*   border: 1px solid red; */
  margin: 0 auto;  
}
div.container{
text-align: center; 
}
 .message{ 
font-size:16px;
border-width:0px;
border-style:dashed;
border-color:;
padding:5px;
width:320px;
height:35px;
top:350px;
left:650px;
position:absolute;

 }
 .form_01 {
 border-color: #000000;
 border-style:ridge;
 border-width:1px 15px 9px 3px;
 padding:5px;
 width: 490px;
 margin-left:34%;
 background: linear-gradient(270deg,  #cfd8dc 1%,#aa9166 100%,#aa9166 90%);
 border-radius: 40px 20px 40px 20px;
 
 }
 .btn {
 margin-left: 42%;
/*  background: linear-gradient(90deg,  #001cff  10%,#007dff  50%,  #cfd8dc 100%);  */
 }
 

/* 	================================== */


</style>
<body>
        <div class="wrapper">
            <!-- Nav Bar Start -->
			<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set>
            <%@ include file="/front-end/files/frontend_navbar.file"%>
            <!-- Nav Bar End -->
         </div>
            <!-- Page Header Start --> <!-- 看自己需不需要標題 -->
            <div class="page-header">
                <div class="container">
                    <div class="row">
                        <div class="col-12">
                            <h2>找回密碼</h2> 
                        </div>
                    </div>
                </div>
            </div>
            <!-- Page Header End -->
            <div class="container">
						<p><a href="login.jsp">
						<img src="<%=request.getContextPath()%>/resource/images/forgot.jpg" style="width: 250px;hight:250px"><b><span style="font-color:#cfd8dc;">回登入頁面</span></b></a></p>
			</div>
						<!--錯誤表列 -->
						<div class="message" >
							<c:if test="${not empty errorMsgs}">
								<b>	<font style="color:red">請修正以下錯誤:</font></b>
											<c:forEach var="message" items="${errorMsgs}">
												<b><span style="color: red">${message}</span></b>
											</c:forEach>
								</c:if>
							</div>
							<hr style="border-width:3px;border-color:#aaa400;width:700px;">
			<div class="form_01 pure-form" >
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Member/member.do" name="form"  style="width: 450px;">
					<div class="form-group pure-input-rounded">
						<h3><label for="exampleInputAccount">請輸入帳號:</label></h3> 
						<input class="form-control" type="TEXT" name="memAccount" size="45" value="" style="border-width:5px"/>
						
						<h3><label for="exampleInputmail">請輸入信箱:</label></h3> 
						<input class="form-control" type="TEXT" name="memMail" size="45" value="" style="border-width:5px" />
					</div><br>
						<input class="form-control" type="hidden" name="action" value="forgot_password">
						<button type="submit" class="btn combtn " >送出驗證</button><br>
			</Form>	
			</div>
			<hr style="border-width:3px;border-color:#aaa400;width:700px;">
            <!-- PUT HERE Start -->
            
            
            <!-- PUT HERE End -->
            
          

            <!-- Footer Start -->
            <%@ include file="/front-end/files/frontend_footer.file"%>
            <!-- Footer End -->
        
        
<%@ include file="/front-end/files/frontend_importJs.file"%>
<!-- <br>本網頁的路徑:<br><b> -->
<%--    <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br> --%>
<%--    <font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%> </b>   --%>

</body>
</html>