<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.food.model.*"%>

<%
  FooVO fooVO = (FooVO) request.getAttribute("fooVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>員工資料新增 - addEmp.jsp</title>

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
		 <h3>餐點資料新增 - addFoo.jsp</h3></td><td>
		 <h4><a href="<%=request.getContextPath()%>/back-end/foo/fooSelectPage.jsp">回首頁</a></h4>
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

<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/foo/foo.do" enctype="multipart/form-data">
<table>
	<tr>
		<td>餐點名稱:</td>
		<td><input type="TEXT" name="fooName" size="45" 
			 value="<%= (fooVO==null)? "" : fooVO.getFooName()%>" /></td>
	</tr>
	
	<jsp:useBean id="fooCatSvc" scope="page" class="com.food_cate.model.FooCatService" />
	<tr>
		<td>餐點類別:<font color=red><b>*</b></font></td>
		<td><select size="1" name="fooCatNo">
			<c:forEach var="fooCatVO" items="${fooCatSvc.all}">
				<option value="${fooCatVO.fooCatNo}" ${(fooVO.fooCatNo==fooCatVO.fooCatNo)? 'selected':'' } >${fooCatVO.fooCatName}
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td>餐點簡介:</td>
		<td><input name="fooIntro" type="text"
		    value="<%= (fooVO==null)? "" : fooVO.getFooIntro()%>" /></td>
	</tr>
	<tr>
		<td>餐點圖片:</td>
		<td><input name="fooImg" type="file"></td>
	</tr>
	<tr>
		<td>餐點售價:</td>
		<td><input type="TEXT" name="fooPrice" size="45"
			 value="<%= (fooVO==null)? "100" : fooVO.getFooPrice()%>" /></td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>