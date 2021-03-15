<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.theater.model.*" %>
<%
	TheVO theaterVO = (TheVO) request.getAttribute("theaterVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>員工資料修改 - update_emp_input.jsp</title>

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
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>員工資料修改 - update_emp_input.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/theater/select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料修改:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="theater.do" name="form1">
<table>
	<tr>
		<td>廳院編號:<font color=red><b>*</b></font></td>
		<td><%=theaterVO.getThe_no()%></td>
	</tr>
	<jsp:useBean id="movie_versionSvc" scope="page" class="com.movie_version.model.MovVerService" />
	<tr>
		<td>放映種類:<font color=red><b>*</b></font></td>
		<td><select size="1" name="movver_no">
			<c:forEach var="movie_versionVO" items="${movie_versionSvc.all}">
				<option value="${movie_versionVO.movver_no}" ${(theaterVO.movver_no==movie_versionVO.movver_no)?'selected':'' } >${movie_versionVO.movver_name}
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td>廳院座位編排:</td>
		<td><input type="TEXT" name="the_seat" size="45" value="<%=theaterVO.getThe_seat()%>" /></td>
	</tr>
	<tr>
		<td>廳院座位編號:</td>
		<td><input type="TEXT" name="the_seatno" size="45"	value="<%=theaterVO.getThe_seatno()%>" /></td>
	</tr>



</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="the_no"  value="<%=theaterVO.getThe_no()%>">
<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"> <!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
<input type="hidden" name="whichPage"  value="<%=request.getParameter("whichPage")%>">  <!--只用於:istAllEmp.jsp-->
<input type="submit" value="送出修改"></FORM>

<br>送出修改的來源網頁路徑:<br><b>
   <font color=blue>request.getParameter("requestURL"):</font> <%=request.getParameter("requestURL")%><br>
   <font color=blue>request.getParameter("whichPage"): </font> <%=request.getParameter("whichPage")%> (此範例目前只用於:istAllEmp.jsp))</b>
</body>
</html>