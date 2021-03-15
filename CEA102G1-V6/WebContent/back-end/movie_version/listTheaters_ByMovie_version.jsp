<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.theater.model.*"%>

<jsp:useBean id="movie_versionSvc" scope="page" class="com.movie_version.model.MovVerService" />


<html>
<head><title>放映廳院</title>

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
		 <h3>放映廳院</h3>
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
		<th>廳院編號</th>
		<th>影廳種類</th>
		<th>廳院座位編排</th>
		<th>廳院座位編號</th>
	</tr>
	
	<c:forEach var="theaterVO" items="${listTheaters_Bymovie_version}" >
		<tr ${(theaterVO.the_no==param.the_no) ? 'bgcolor=#CCCCFF':''}><!--將修改的那一筆加入對比色-->
			<td>${theaterVO.the_no}</td>
			<td><c:forEach var="movie_versionVO" items="${movie_versionSvc.all}">
                    <c:if test="${theaterVO.movver_no==movie_versionVO.movver_no}">
	                    【<font color=orange>${movie_versionVO.movver_name}</font>】
                    </c:if>
                </c:forEach>
			</td>
			<td>${theaterVO.the_seat}</td>
			<td>${theaterVO.the_seatno}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/theater/theater.do" style="margin-bottom: 0px;">
			    <input type="submit" value="修改"> 
			    <input type="hidden" name="the_no"      value="${theaterVO.the_no}">
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