<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ticket_type.model.*"%>

<jsp:useBean id="movie_versionSvc" scope="page" class="com.movie_version.model.MovVerService" />
<jsp:useBean id="identitySvc" scope="page" class="com.identity.model.IdeService" />

<html>
<head><title>放映票種</title>

<style>
  table#table-2 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-2 h4 {
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

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-2">
	<tr><td>
		 <h3>放映票種</h3>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>票種編號 </th>
		<th>放映種類編號 </th>
		<th>身分名稱</th>
		<th>票價</th>
		<th>修改</th>
	</tr>
	
	<c:forEach var="ticket_typeVO" items="${listTicket_type_ByMovver_no}" >
		<tr ${(ticket_typeVO.tictyp_no==param.tictyp_no) ? 'bgcolor=#CCCCFF':''}><!--將修改的那一筆加入對比色-->
			<td>${ticket_typeVO.tictyp_no}</td>
			<td><c:forEach var="movie_versionVO" items="${movie_versionSvc.all}">
                    <c:if test="${ticket_typeVO.movver_no==movie_versionVO.movver_no}">
	                    ${movie_versionVO.movver_name}
                    </c:if>
                </c:forEach>
			</td>
			<td><c:forEach var="identityVO" items="${identitySvc.all}">
                    <c:if test="${ticket_typeVO.ide_no==identityVO.ide_no}">
	                    【<font color=orange>${identityVO.ide_name}</font>】
                    </c:if>
                </c:forEach>
			</td>
			<td>${ticket_typeVO.tictyp_price}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/ticket_type/ticket_type.do" style="margin-bottom: 0px;">
			    <input type="submit" value="修改"> 
			    <input type="hidden" name="tictyp_no"      value="${ticket_typeVO.tictyp_no}">
			    <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
			    <input type="hidden" name="action"	   value="getOne_For_Update"></FORM>
			</td>

		</tr>
	</c:forEach>
</table>

<br>本網頁的路徑:<br><b>
   <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%> </b>

</body>
</html>