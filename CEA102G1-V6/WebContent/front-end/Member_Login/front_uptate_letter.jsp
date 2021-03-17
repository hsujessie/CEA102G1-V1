<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%System.out.println("successPage"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login_success_page</title>

<p align="center" ><font size="5">請至信箱收信,並重新更改資料.</font></p>

</head>
<body>
	<script language=javascript>
		setTimeout('window.location="<%=request.getContextPath()%>/front-end/Member_Login/login.jsp"', 2000)
	</script>

</body>
</html>