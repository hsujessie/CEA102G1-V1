<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.board.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  BoardVO boardVO = (BoardVO) request.getAttribute("BoardVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
  
%>

<html>
<head>
<title>公告資料 - listOneBoard.jsp</title>

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
		 <h3>公告資料 - ListOneBoard.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back_end/Board/select_Board_page.jsp">
		 <img src="<%=request.getContextPath()%>/resource/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>公告編號</th>
		<th>公告種類編號</th>
		<th>公告內容</th>
		<th>公告日期日期</th>

	</tr>

	<tr>
		<td>${BoardVO.boaNo}</td>
		<td>${BoardVO.boatypNo}</td>
		<td>${BoardVO.boaContent}</td>
		<td>${BoardVO.boaTime}</td>
	</tr>

</table>

</body>
</html>