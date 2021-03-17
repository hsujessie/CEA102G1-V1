<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<%
	
%>
<html>
<head>
<meta charset="UTF-8">
<title>forgot_password</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/resource/jquery/jquery-3.5.1.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
<h1>找回密碼</h1>				
			<p><a href="login.jsp">
			<img src="<%=request.getContextPath()%>/resource/images/forgot.jpg" width="100" height="100" border="0">回登陸頁面</a></p>
</div>

<style>
body{
/*   border: 1px solid red; */
  margin: 0 auto;  
}
div.container{
text-align: center; 
}
form{
margin: 0  auto;
}
</style>
	
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Member/member.do" name="form"  style="width: 450px;">

		<div class="form-group">
			<label for="exampleInputAccount">請輸入帳號:</label> 
			<input class="form-control" type="TEXT" name="memAccount" size="45" value="" />
		</div>
		<div class="form-group">
			<label for="exampleInputmail">請輸入信箱:</label> 
			<input class="form-control" type="TEXT" name="memMail" size="45" value="" />
		</div>
		
<!-- 		<input class="form-control" type="hidden" name="memID"value="">  -->
		<br>
		<input class="form-control" type="hidden" name="action" value="forgot_password">
		<button type="submit" class="btn btn-primary">送出驗證</button>
		<br>
	</Form>	
	

</body>
</html>