<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.theater.model.*"%>
<%@ page import="com.ticket_type.model.*"%>

<jsp:useBean id="movie_versionSvc" scope="page" class="com.movie_version.model.Movie_versionService" />

<html>
<head><title>�Ҧ���M����</title>

<style>
  table#table-1 {
	background-color: orange;
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
		 <h3>�Ҧ���M����</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/theater/select_page.jsp">�^�v�U����</a></h4>
	</td></tr>
</table>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>��M�����s��</th>
		<th>��M�����W��</th>
		<th>�d�ߩ�M�U�|</th>
		<th>�d�߲���</th>
	</tr>
	
	<c:forEach var="movie_versionVO" items="${movie_versionSvc.all}">
		<tr>
			<td>${movie_versionVO.movver_no}</td>
			<td>${movie_versionVO.movver_name}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/movie_version/movie_version.do" style="margin-bottom: 0px;">
			    <input type="submit" value="�e�X�d��"> 
			    <input type="hidden" name="movver_no" value="${movie_versionVO.movver_no}">
			    <input type="hidden" name="action" value="listTheaters_Bymovie_version_B"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/movie_version/movie_version.do" style="margin-bottom: 0px;">
			    <input type="submit" value="�e�X�d��"> 
			    <input type="hidden" name="movver_no" value="${movie_versionVO.movver_no}">
			    <input type="hidden" name="action" value="listTicket_type_ByMovver_no_B"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>

<%if (request.getAttribute("listTheaters_Bymovie_version")!=null){%>
       <jsp:include page="listTheaters_ByMovie_version.jsp" />
<%} %>
<%if (request.getAttribute("listTicket_type_ByMovver_no")!=null){%>
       <jsp:include page="listTicket_type_ByMovver_no.jsp" />
<%} %>

</body>
</html>