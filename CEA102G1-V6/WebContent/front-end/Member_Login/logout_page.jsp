<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>logout_page</title>
<%
session.invalidate();
%>

　<p align="center" valign="center"><font size="5">登出已成功,歡迎再登入</font></p>

</head>
<body>
	<script language=javascript>
		setTimeout('window.location="<%=request.getContextPath()%>/front-end/Member_Login/login.jsp"', 2000)
	</script>

</body>
</html>