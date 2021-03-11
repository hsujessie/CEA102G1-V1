<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ticket_type.model.*"%>

<%
Ticket_typeVO ticket_typeVO = (Ticket_typeVO) request.getAttribute("ticket_typeVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>票種資料修改</title>

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
		 <h3>票種資料修改</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/ticket_type/select_page.jsp">回首頁</a></h4>
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

<FORM METHOD="post" ACTION="ticket_type.do" name="form1">
<table>
	<tr>
		<td>票種編號:<font color=red><b>*</b></font></td>
		<td><%=ticket_typeVO.getTictyp_no()%></td>
	</tr>
	<jsp:useBean id="movie_versionSvc" scope="page" class="com.movie_version.model.Movie_versionService" />
	<tr>
		<td>放映種類:<font color=red><b>*</b></font></td>
		<td><select size="1" name="movver_no">
			<c:forEach var="movie_versionVO" items="${movie_versionSvc.all}">
				<option value="${movie_versionVO.movver_no}" ${(ticket_typeVO.movver_no==movie_versionVO.movver_no)?'selected':'' } >${movie_versionVO.movver_name}
			</c:forEach>
		</select></td>
	</tr>
	<jsp:useBean id="identitySvc" scope="page" class="com.identity.model.IdentityService" />
	<tr>
		<td>身分名稱:<font color=red><b>*</b></font></td>
		<td><select size="1" name="ide_no">
			<c:forEach var="identityVO" items="${identitySvc.all}">
				<option value="${identityVO.ide_no}" ${(ticket_typeVO.ide_no==identityVO.ide_no)?'selected':'' } >${identityVO.ide_name}
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td>票價 :</td>
		<td><input type="TEXT" name="tictyp_price" size="45" value="<%=ticket_typeVO.getTictyp_price()%>" /></td>
	</tr>


</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="tictyp_no"  value="<%=ticket_typeVO.getTictyp_no()%>">
<input type="hidden" name="requestURL" value="<%=request.getAttribute("requestURL")%>"> <!--原送出修改的來源網頁路徑,從request取出後,再送給Controller準備轉交之用-->
<input type="hidden" name="whichPage"  value="<%=request.getAttribute("whichPage")%>">  <!--只用於:istAllEmp.jsp-->
<input type="submit" value="送出修改"></FORM>

<br>送出修改的來源網頁路徑:<br><b>
   <font color=blue>request.getAttribute("requestURL"):</font> <%=request.getAttribute("requestURL")%><br>
   <font color=blue>request.getAttribute("whichPage"): </font> <%=request.getAttribute("whichPage")%> (此範例目前只用於:istAllEmp.jsp))</b>
</body>






</html>