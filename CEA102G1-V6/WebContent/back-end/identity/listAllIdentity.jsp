<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ticket_type.model.*"%>

<jsp:useBean id="identitySvc" scope="page" class="com.identity.model.IdeService" />

<html>
<head><title>�Ҧ�����</title>

<style>
  table#table-1 {
	background-color: orange;
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
		 <h3>�Ҧ�����</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/ticket_type/select_page.jsp">�^���魺��</a></h4>
	</td></tr>
</table>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>�����s��</th>
		<th>�����W��</th>
		<th>�d��</th>
	</tr>
	
	<c:forEach var="identityVO" items="${identitySvc.all}">
		<tr>
			<td>${identityVO.ide_no}</td>
			<td>${identityVO.ide_name}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/identity/identity.do" style="margin-bottom: 0px;">
			    <input type="submit" value="�e�X�d��"> 
			    <input type="hidden" name="ide_no" value="${identityVO.ide_no}">
			    <input type="hidden" name="action" value="listTicket_typesByIde_no_B"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>

<%if (request.getAttribute("listTicket_typesByIde_no")!=null){%>
       <jsp:include page="listTicket_typesByIde_no.jsp" />
<%} %>

</body>
</html>