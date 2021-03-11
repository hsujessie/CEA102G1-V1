<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.theater.model.*"%>


<%
	TheaterService theSvc = new TheaterService();
	List<TheaterVO> list = theSvc.getAll();
	pageContext.setAttribute("list",list);
%>
<jsp:useBean id="movie_versionSvc" scope="page" class="com.movie_version.model.Movie_versionService" />
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>廳院資料</title>

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
		 <h3>廳院資料</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/theater/select_page.jsp">回廳院首頁</a></h4>
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
		<th>廳院編號</th>
		<th>影廳種類</th>
		<th>廳院座位編排</th>
		<th>廳院座位編號</th>
	</tr>
	<%@ include file="/resource/pages/page1.file" %> 
	<c:forEach var="theaterVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr ${(theaterVO.the_no==param.the_no) ? 'bgcolor=#CCCCFF':''}><!--將修改的那一筆加入對比色而已-->
			<td>${theaterVO.the_no}</td>
			<td><c:forEach var="movie_versionVO" items="${movie_versionSvc.all}">
                    <c:if test="${theaterVO.movver_no==movie_versionVO.movver_no}">
	                    ${movie_versionVO.movver_name}
                    </c:if>
                </c:forEach>
			</td>
			<td>${theaterVO.the_seat}</td>
			<td>${theaterVO.the_seatno}</td>		
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/theater/theater.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改"> 
			     <input type="hidden" name="the_no"     value="${theaterVO.the_no}">
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