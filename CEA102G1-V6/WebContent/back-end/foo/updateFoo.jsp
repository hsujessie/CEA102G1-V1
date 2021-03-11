<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.food.model.*"%>

<%
	FooVO fooVO = (FooVO) request.getAttribute("fooVO"); //fooServlet.java (Controller) 存入req的fooVO物件 (包括幫忙取出的fooVO, 也包括輸入資料錯誤時的fooVO物件)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>餐點資料修改 - updateFoo.jsp</title>

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
		 <h3>餐點資料修改 - updateFoo.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/foo/fooSelectPage.jsp">回首頁</a></h4>
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

<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/foo/foo.do" enctype="multipart/form-data">
<table>
	<tr>
		<td>餐點編號:<font color=red><b>*</b></font></td>
		<td>${fooVO.fooNo}</td>
	</tr>
	<tr>
		<td>餐點名稱:</td>
		<td><input type="TEXT" name="fooName" size="45" value="${fooVO.fooName}" /></td>
	</tr>
	
	<jsp:useBean id="fooCatSvc" scope="page" class="com.food_cate.model.FooCatService" />
	
	<tr>
		<td>餐點類別:</td>
		<td><select size="1" name="fooCatNo">
			<c:forEach var="fooCatVO" items="${fooCatSvc.all}">
				<option value="${fooCatVO.fooCatNo}" ${(fooVO.fooCatNo==fooCatVO.fooCatNo)?'selected':'' } >${fooCatVO.fooCatName}
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td>餐點簡介:</td>
		<td><input type="text" name="fooIntro" value="${fooVO.fooIntro}" ></td>
	</tr>
	<tr>
		<td>餐點圖片:</td>
		<td><input type="file" name="fooImg" size="45" /></td>
	</tr>
	<tr>
		<td>餐點售價:</td>
		<td><input type="TEXT" name="fooPrice" size="45" value="${fooVO.fooPrice}" /></td>
	</tr>

	<tr>
		<td>餐點狀態:</td>
		<td><select size="1" name="fooStatus">
				<% for (int i = 0; i <= 1; i++) {%>
				<option value="<%=i%>" <%=(fooVO.getFooStatus()==i) ?"selected":"" %> ><%=(i==0)?"上架":"下架"%>
				<% } %>
		</select></td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="fooNo"  value="${fooVO.fooNo}">
<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"> <!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
<input type="hidden" name="whichPage"  value="<%=request.getParameter("whichPage")%>">  <!--只用於:istAllEmp.jsp-->
<input type="submit" value="送出修改"></FORM>

<br>送出修改的來源網頁路徑:<br><b>
   <font color=blue>request.getParameter("requestURL"):</font> <%=request.getParameter("requestURL")%><br>
   <font color=blue>request.getParameter("whichPage"): </font> <%=request.getParameter("whichPage")%> (此範例目前只用於:istAllEmp.jsp))</b>
</body>

</html>