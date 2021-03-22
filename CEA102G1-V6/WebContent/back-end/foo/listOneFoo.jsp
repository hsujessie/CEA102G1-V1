<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.food.model.*"%>
<%@ page import="com.food_cate.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%-- 取出 Controller EmpServlet.java已存入request的FooVO物件--%>
<%FooVO fooVO = (FooVO) request.getAttribute("fooVO");%>

<%-- 取出 對應的FooCatVO物件--%>
<%
  FooCatService fooCatSvc = new FooCatService();
  FooCatVO fooCatVO = fooCatSvc.getOneFooCat(fooVO.getFooCatNo());
%>

<html>
<head>
<title>餐點資料 - listOneFoo.jsp</title>

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

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>餐點資料 - listOneFoo.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/foo/fooSelectPage.jsp">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>餐點編號</th>
		<th>餐點名稱</th>
		<th>餐點類別</th>
		<th>餐點圖片</th>
		<th>餐點售價</th>
		<th>餐點狀態</th>
	</tr>
	<tr>
		<td><%=fooVO.getFooNo()%></td>
		<td><%=fooVO.getFooName()%></td>
		<td><%=fooCatVO.getFooCatName()%></td>
		<td><img src="<%=request.getContextPath()%>/util/imgReader${fooVO.fooImgParam}"></td>
		<td><%=fooVO.getFooPrice()%></td>
		<td><%=fooVO.getFooStatus()%></td>
	</tr>
</table>

</body>
</html>