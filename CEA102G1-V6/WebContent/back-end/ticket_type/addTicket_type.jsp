<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ticket_type.model.*"%>

<%
	TicTypVO ticket_typeVO = (TicTypVO) request.getAttribute("ticket_typeVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>票種資料新增</title>

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
		 <h3>票種資料新增</h3></td><td>
		 <h4><a href="<%=request.getContextPath()%>/back-end/ticket_type/select_page.jsp">回票種首頁</a></h4>
	</td></tr>
</table>

<h3>資料新增:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/ticket_type/ticket_type.do" name="form1">
<table>
	<tr>
	<jsp:useBean id="movie_versionSvc" scope="page" class="com.movie_version.model.MovVerService" />
		<td>放映種類:<font color=red><b>*</b></font></td>
		<td><select size="1" name="movver_no">
			<c:forEach var="movie_versionVO" items="${movie_versionSvc.all}">
				<option value="${movie_versionVO.movver_no}" ${(ticket_typeVO.movver_no==movie_versionVO.movver_no)? 'selected':'' } >${movie_versionVO.movver_name}
			</c:forEach>
		</select></td>
	</tr>
	<tr>
	<jsp:useBean id="identitySvc" scope="page" class="com.identity.model.IdeService" />
		<td>身分種類:<font color=red><b>*</b></font></td>
		<td><select size="1" name="ide_no">
			<c:forEach var="identityVO" items="${identitySvc.all}">
				<option value="${identityVO.ide_no}" ${(ticket_typeVO.ide_no==identityVO.ide_no)? 'selected':'' } >${identityVO.ide_name}
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td>票價:</td>
		<td><input type="TEXT" name="tictyp_price" size="45" 
			 value="<%= (ticket_typeVO==null)? "" : ticket_typeVO.getTictyp_price()%>" /></td>
	</tr>


</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>





</html>