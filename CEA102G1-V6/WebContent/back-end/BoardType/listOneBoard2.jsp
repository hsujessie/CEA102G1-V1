<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.board.model.*"%>
<%@ page import = "javax.servlet.http.* " %>
<%-- �����Ƚm�߱ĥ� Script ���g�k���� --%>

<%
List<BoardVO> boardVO = (List<BoardVO>) request.getAttribute("BoatypNolist"); //EmpServlet.java(Concroller), �s�Jreq��empVO����

%>

<html>
<head>
<title>���i��� - listOneBoard2.jsp</title>

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

<h4>�����Ƚm�߱ĥ� Script ���g�k����:</h4>
<table id="table-1">
	<tr><td>
		 <h3>���i��� - ListOneBoard2.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back_end/Board/select_Board_page.jsp">
		 <img src="<%=request.getContextPath()%>/resource/images/back1.gif" width="100" height="32" border="0">�^����</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		
		<th>���i�s��</th>
		<th>���i�����s��</th>
		<th>���i���e</th>
		<th>���i������</th>

	</tr>
	<c:forEach var="boardVO" items="${BoatypNolist}" >
	<tr>
		
		<td>${boardVO.boaNo}</td>
		<td>${boardVO.boatypNo}</td>
		<td>${boardVO.boaContent}</td>
		<td>${boardVO.boaTime}</td>
	</tr>
	</c:forEach>
</table>

</body>
</html>