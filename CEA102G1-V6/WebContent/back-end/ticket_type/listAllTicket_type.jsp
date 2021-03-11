<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ticket_type.model.*"%>


<%
	Ticket_typeService ticket_typeSvc = new Ticket_typeService();
	List<Ticket_typeVO> list = ticket_typeSvc.getAll();
	pageContext.setAttribute("list",list);
%>
<jsp:useBean id="identitySvc" scope="page" class="com.identity.model.IdentityService" />
<jsp:useBean id="movie_versionSvc" scope="page" class="com.movie_version.model.Movie_versionService" />
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>票種資料</title>

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
		 <h3>票種資料</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/ticket_type/select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>
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
	<%@ include file="/resource/pages/page1.file" %> 
	<%  rowsPerPage = 4; %>
	<c:forEach var="ticket_typeVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr ${(ticket_typeVO.tictyp_no==param.tictyp_no) ? 'bgcolor=#CCCCFF':''}><!--將修改的那一筆加入對比色而已-->
			<td>${ticket_typeVO.tictyp_no}</td>
			<td><c:forEach var="movie_versionVO" items="${movie_versionSvc.all}">
                    <c:if test="${ticket_typeVO.movver_no==movie_versionVO.movver_no}">
	                    ${movie_versionVO.movver_name}
                    </c:if>
                </c:forEach>
			</td>
			<td><c:forEach var="identityVO" items="${identitySvc.all}">
                    <c:if test="${ticket_typeVO.ide_no==identityVO.ide_no}">
	                    ${identityVO.ide_name}
                    </c:if>
                </c:forEach>
			</td>			
			<td>${ticket_typeVO.tictyp_price}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/ticket_type/ticket_type.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改"> 
			     <input type="hidden" name="tictyp_no"     value="${ticket_typeVO.tictyp_no}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			     <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
			     <input type="hidden" name="action"	    value="getOne_For_Update"></FORM>
			</td>

		</tr>
	</c:forEach>
</table>
<%@ include file="/resource/pages/page2.file" %>
</body>
</html>