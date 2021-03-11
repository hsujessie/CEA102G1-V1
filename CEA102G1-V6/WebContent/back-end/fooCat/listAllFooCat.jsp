<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.food_cate.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	FooCatService fooCatSvc = new FooCatService();
	List<FooCatVO> list = fooCatSvc.getAll();
	pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>所有員工資料 - listAllEmp.jsp</title>

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

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有餐點類別資料 - listAllFooCat.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/foo/fooSelectPage.jsp">回首頁</a></h4>
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
		<th>餐點類別編號</th>
		<th>餐點類別名稱</th>
		<th>修改</th>
		<th>刪除</th>
		<th>查詢該類別餐點</th>
	</tr>
	<c:forEach var="fooCatVO" items="${list}">
		<tr ${(fooCatVO.fooCatNo==param.fooCatNo) ? 'bgcolor=#CCCCFF':''}><!--將修改的那一筆加入對比色而已-->
			<td>${fooCatVO.fooCatNo}</td>
			<td>${fooCatVO.fooCatName}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/fooCat/fooCat.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改"> 
			     <input type="hidden" name="fooCatNo"      value="${fooCatVO.fooCatNo}">
			     <input type="hidden" name="action"	    value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/fooCat/fooCat.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="fooCatNo"      value="${fooCatVO.fooCatNo}">
			     <input type="hidden" name="action"     value="delete"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/fooCat/fooCat.do" style="margin-bottom: 0px;">
			    <input type="submit" value="送出查詢"> 
			    <input type="hidden" name="fooCatNo" value="${fooCatVO.fooCatNo}">
			    <input type="hidden" name="action" value="listFoos_ByFooCatNo_fromFooCate"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>

<%if (request.getAttribute("listFoos_ByFooCatNo")!=null){%>
       <jsp:include page="listFoosByFooCatNo.jsp" />
<%} %>

</body>
</html>