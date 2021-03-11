<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>CEA102G1 foo : Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
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

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>CEA102G1 foo : Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for CEA102G1 foo : Home</p>

<h3>資料查詢:</h3>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font color='red'>請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='<%=request.getContextPath()%>/back-end/foo/listAllFoo.jsp'>List</a> all Foods. <br><br></li>
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/foo/foo.do" >
        <b>輸入餐點編號 (如1):</b>
        <input type="text" name="fooNo">
        <input type="submit" value="送出">
        <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>

  <jsp:useBean id="fooSvc" scope="page" class="com.food.model.FooService" />
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/foo/foo.do" >
       <b>選擇餐點編號:</b>
       <select size="1" name="fooNo">
         <c:forEach var="fooVO" items="${fooSvc.all}" > 
          <option value="${fooVO.fooNo}">${fooVO.fooNo}
         </c:forEach>   
       </select>
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/foo/foo.do" >
       <b>選擇餐點名稱:</b>
       <select size="1" name="fooNo">
         <c:forEach var="fooVO" items="${fooSvc.all}" > 
          <option value="${fooVO.fooNo}">${fooVO.fooName}
         </c:forEach>   
       </select>
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
  </li>
  
  <jsp:useBean id="fooCatSvc" scope="page" class="com.food_cate.model.FooCatService" />
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/fooCat/fooCat.do" >
       <b><font color=orange>選擇餐點類別:</font></b>
       <select size="1" name="fooCatNo">
         <c:forEach var="fooCatVO" items="${fooCatSvc.all}" > 
          <option value="${fooCatVO.fooCatNo}">${fooCatVO.fooCatName}
         </c:forEach>   
       </select>
       <input type="submit" value="送出">
       <input type="hidden" name="action" value="listFoos_ByFooCatNo_fromFoo">
     </FORM>
  </li>
  
  <li>
  	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/foo/foo.do">
  		<b><font color=orange>選擇上架狀態:</font></b>
  		<select size="1" name="fooStatus">
  			<% for (int i = 0; i <= 1; i++) {%>
				<option value="<%=i%>" ><%=(i==0)?"上架":"下架"%>
			<% } %>
  		</select>
  		<input type="submit" value="送出">
        <input type="hidden" name="action" value="listFoos_ByFooStatus">
  	</FORM>
  </li>
</ul>

<h3>餐點類別管理</h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/back-end/foo/addFoo.jsp'>Add</a> a new Food.</li>
</ul>

<h3><font color=orange>餐點類別管理</font></h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/back-end/fooCat/listAllFooCat.jsp'>List</a> all FooCats. </li>
</ul>

</body>
</html>