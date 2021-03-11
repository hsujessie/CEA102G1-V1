<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.board.model.*"%>
<%@ page import="com.board_type.model.*"%>
<%@ page import="java.util.*"%>

<%
  	BoardVO boardVO = (BoardVO) request.getAttribute("BoardVO");
	pageContext.setAttribute("boardVO", boardVO);
%>




<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>公告資料修改 - update_board_input.jsp</title>

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
		 <h3>公告資料修改 - update_board_input.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back_end/Board/select_Board_page.jsp">
		 <img src="<%=request.getContextPath()%>/resource/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料修改:</h3>

<!-- 錯誤表列 -->
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="board.do" name="form1">
<table>
	<tr>
		<td>公告編號:<font color=red><b>*</b></font></td>
		<td><%=boardVO.getBoaNo()%></td>
	</tr>
		

<jsp:useBean id="boardTypeSvc" scope="page" class="com.board_type.model.BoardTypeService" />
	<tr>
		<td>公告種類編號:<font color=red><b>*</b></font></td>
		<td><select size="1" name="boatypNo">
					<c:forEach var="boardTypeVO" items="${boardTypeSvc.all}">
					<option value="${boardTypeVO.boatypNo}" ${(boardVO.boatypNo==boardTypeVO.boatypNo) ? 'selected' : ' ' } >${boardTypeVO.boatypName} </option>
					</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td>公告內容:</td>
		<td><input type="TEXT" name="boaContent" size="45"	value="<%=boardVO.getBoaContent()%>" /></td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="boaNo" value="<%=boardVO.getBoaNo()%>">
<input type="submit" value="送出修改"></FORM>
</body>






<style>
  
</style>

<script>
       
        
</script>
</html>