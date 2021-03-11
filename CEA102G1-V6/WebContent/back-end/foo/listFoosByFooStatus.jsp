<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.food.model.*"%>
<%@ page import="java.util.*" %>

<jsp:useBean id="listFoos_ByFooStatus" scope="request" type="java.util.Set<FooVO>" /> <!-- 於EL此行可省略 -->
<jsp:useBean id="fooCatSvc" scope="page" class="com.food_cate.model.FooCatService" />


<html>
<head><title>依據餐點搜尋餐點 - listFoos_ByFooStatus.jsp</title>

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
		 <h3>依據餐點搜尋餐點 - listFoos_ByFooStatus.jsp</h3>
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
		<th>餐點編號</th>
		<th>餐點名稱</th>
		<th>餐點類別</th>
		<th>餐點簡介</th>
		<th>餐點圖片</th>
		<th>餐點售價</th>
		<th>餐點狀態</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	
	<c:forEach var="fooVO" items="${listFoos_ByFooStatus}" >
		<tr ${(fooVO.fooNo==param.fooNo) ? 'bgcolor=#CCCCFF':''}><!--將修改的那一筆加入對比色而已-->
			<td>${fooVO.fooNo}</td>
			<td>${fooVO.fooName}</td>
			<td>${fooVO.fooCatNo}--${fooCatSvc.getOneFooCat(fooVO.fooCatNo).fooCatName}</td>
			<td>${fooVO.fooIntro}</td>
			<td><img src="<%=request.getContextPath()%>/foo/reader.do?columnName=foo_img&tableName=food&fieldName=foo_no&fieldValue=${fooVO.fooNo}"></td>
			<td>${fooVO.fooPrice}</td>			
			<td>${fooVO.fooStatus}--${(fooVO.fooStatus==0)?"上架":"下架"}</td>			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/foo/foo.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改"> 
			     <input type="hidden" name="fooNo"      value="${fooVO.fooNo}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			     <input type="hidden" name="action"	    value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/foo/foo.do" style="margin-bottom: 0px;">
			     <input type="submit" value=${(fooVO.fooStatus)==0?"下架":"上架"}>
			     <input type="hidden" name="fooNo"      value="${fooVO.fooNo}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			     <input type="hidden" name="action"     value="change_Status"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<br>本網頁的路徑:<br><b>
   <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%> </b>

</body>
</html>