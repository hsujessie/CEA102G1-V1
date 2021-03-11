<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.member.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
MemberVO memberVO = (MemberVO) request.getAttribute("MemberVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
  
%>

<html>
<head>
<title>公告資料 - listOneMember.jsp</title>

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

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>會員資料 - ListOneMember.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back_end/Member/select_member_page.jsp">
		 <img src="<%=request.getContextPath()%>/resource/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>會員編號</th>
		<th>會員名稱</th>
		<th>會員帳號</th>
		<th>會員密碼</th>
		<th>會員mail</th>

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
		<td><%= memberVO.getMemNo()%></td>
		<td><%= memberVO.getMemName()%></td>
		<td><%= memberVO.getMemAccount()%></td>
		<td><%= memberVO.getMemPassword()%></td>
		<td><%= memberVO.getMemMail()%></td>
		
	</tr>
</table>

</body>
</html>