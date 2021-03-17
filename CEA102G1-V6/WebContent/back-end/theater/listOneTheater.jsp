<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ page import="com.theater.model.*"%>
<%@ page import="com.movie_version.model.*"%>

<%
	TheVO theaterVO = (TheVO) request.getAttribute("theaterVO");
%>
<%
	MovVerService movie_versionSvc = new MovVerService();
  MovVerVO movie_versionVO = movie_versionSvc.getOneMovie_version(theaterVO.getMovver_no());
%>
<!DOCTYPE html>
<html>
<head>
<title>�U�|���</title>

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
	width: 800px;
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

<table id="table-1">
	<tr><td>
		 <h3>�U�|���</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/theater/select_page.jsp">�^����</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>�U�|�s��</th>
		<th>��M����</th>
		<th>�U�|�y��s��</th>
		<th>�U�|�y��s��</th>

	</tr>
	<tr>
		<td><%=theaterVO.getThe_no()%></td>
		<td><%=movie_versionVO.getMovver_name()%></td>
		<td><%=theaterVO.getThe_seat()%></td>
		<td><%=theaterVO.getThe_seatno()%></td>
	</tr>
</table>

</body>
</html>