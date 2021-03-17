<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>

<html>
<head>
<meta charset="UTF-8">
<title>login page</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/bootstrap/css2/login2.css">
</head>
<body>
	<script src="<%=request.getContextPath()%>/resource/jquery/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/resource/popper/popper.min.js"></script>
	
	<script src="<%=request.getContextPath()%>/resource/bootstrap/js2/newlogin2.js"></script>
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
				
				<form METHOD="post" ACTION="<%=request.getContextPath()%>/Member/member.do" name="form1">
						  <h2><span class="entypo-login"><i class="fa fa-sign-in"></i></span> Login</h2>
						  <button class="submit" name="action" value="get_Login"><span class="entypo-lock"><i class="fa fa-lock"></i></span></button>
						  
						  <span class="entypo-user inputUserIcon"> <i class="fa fa-user"></i> </span>
						  <input type="text" class="user" name="memAccount" placeholder="ursername"/>
						  <span class="entypo-key inputPassIcon"> <i class="fa fa-key"></i>  </span>
						  <input type="password" class="pass" name="memPassword" placeholder="password"/>
				</form>

</body>
</html>