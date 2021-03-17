<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import = "javax.servlet.http.* " %>

<%
MemberVO memberVO = (MemberVO) request.getAttribute("MemberVO"); 
List<MemberVO> memberVOList = (List<MemberVO>)request.getAttribute("list");

%>

<html>
<head>
<title>您修改的會員資料 - listOneMember2.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>5秒後將轉跳登入頁面</h4>
<table id="table-1">
	<tr><td>
		 <h3>更新會員資料 - ListOneMember2.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/Member/select_member_page.jsp">
		 <img src="<%=request.getContextPath()%>/resource/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%> 
<%-- <c:if test="${not empty errorMsgs}"> --%>
<!-- 	<font style="color:red">請修正以下錯誤:</font> -->
<!-- 	<ul> -->
<%-- 		<c:forEach var="message" items="${errorMsgs}"> --%>
<%-- 			<li style="color:red">${message}</li> --%>
<%-- 		</c:forEach> --%>
<!-- 	</ul> -->
<%-- </c:if> --%>

<table>
	<tr>
<!-- 		<th>會員編號</th> -->
		<th>會員名稱</th>
		<th>會員帳號</th>
		<th>會員密碼</th>
		<th>會員mail</th>
		<th>會員錢包</th>
		<th>會員圖像</th>

	</tr>
	<!-- EL取值 -->
<!-- 	<tr> -->
<%-- 				<td>${memberVO.memNo}</td> --%>
<%-- 				<td>${memberVO.memName}</td> --%>
<%-- 				<td>${memberVO.memAccount}</td> --%>
<%-- 				<td>${memberVO.memPassword}</td> --%>
<%-- 				<td>${memberVO.memMail}</td> --%>
<!-- 	</tr> -->
	<!-- java取值 -->
	<tr>
<%-- 		<td><%= memberVO.getMemNo()%></td>  --%>
		<td><%= memberVO.getMemName()%></td>
		<td><%= memberVO.getMemAccount()%></td>
		<td><%= memberVO.getMemPassword()%></td>
		<td><%= memberVO.getMemMail()%></td>
		<td><%= memberVO.getMemWallet()%></td>
<%-- 		<td><%= memberVO.getMemstatus()%></td> --%>
		<td><img src="<%=request.getContextPath()%>/Member/reader.do?memNo=${memberVO.memNo}" width="100" height="100"></td>
		
	</tr>
	
	
	
	<script language=javascript>
		setTimeout('window.location="<%=request.getContextPath()%>/front-end/Member_Login/login.jsp"', 5000)  <%-- 三秒後轉登入畫面--%>
	</script>
</table>

</body>
</html>