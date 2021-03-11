<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ticket_type.model.*"%>

<%
Ticket_typeVO ticket_typeVO = (Ticket_typeVO) request.getAttribute("ticket_typeVO"); //EmpServlet.java (Concroller) �s�Jreq��empVO���� (�]�A�������X��empVO, �]�]�A��J��ƿ��~�ɪ�empVO����)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>���ظ�ƭק�</title>

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
		 <h3>���ظ�ƭק�</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/ticket_type/select_page.jsp">�^����</a></h4>
	</td></tr>
</table>

<h3>��ƭק�:</h3>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="ticket_type.do" name="form1">
<table>
	<tr>
		<td>���ؽs��:<font color=red><b>*</b></font></td>
		<td><%=ticket_typeVO.getTictyp_no()%></td>
	</tr>
	<jsp:useBean id="movie_versionSvc" scope="page" class="com.movie_version.model.Movie_versionService" />
	<tr>
		<td>��M����:<font color=red><b>*</b></font></td>
		<td><select size="1" name="movver_no">
			<c:forEach var="movie_versionVO" items="${movie_versionSvc.all}">
				<option value="${movie_versionVO.movver_no}" ${(ticket_typeVO.movver_no==movie_versionVO.movver_no)?'selected':'' } >${movie_versionVO.movver_name}
			</c:forEach>
		</select></td>
	</tr>
	<jsp:useBean id="identitySvc" scope="page" class="com.identity.model.IdentityService" />
	<tr>
		<td>�����W��:<font color=red><b>*</b></font></td>
		<td><select size="1" name="ide_no">
			<c:forEach var="identityVO" items="${identitySvc.all}">
				<option value="${identityVO.ide_no}" ${(ticket_typeVO.ide_no==identityVO.ide_no)?'selected':'' } >${identityVO.ide_name}
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td>���� :</td>
		<td><input type="TEXT" name="tictyp_price" size="45" value="<%=ticket_typeVO.getTictyp_price()%>" /></td>
	</tr>


</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="tictyp_no"  value="<%=ticket_typeVO.getTictyp_no()%>">
<input type="hidden" name="requestURL" value="<%=request.getAttribute("requestURL")%>"> <!--��e�X�ק諸�ӷ��������|,�qrequest���X��,�A�e��Controller�ǳ���椧��-->
<input type="hidden" name="whichPage"  value="<%=request.getAttribute("whichPage")%>">  <!--�u�Ω�:istAllEmp.jsp-->
<input type="submit" value="�e�X�ק�"></FORM>

<br>�e�X�ק諸�ӷ��������|:<br><b>
   <font color=blue>request.getAttribute("requestURL"):</font> <%=request.getAttribute("requestURL")%><br>
   <font color=blue>request.getAttribute("whichPage"): </font> <%=request.getAttribute("whichPage")%> (���d�ҥثe�u�Ω�:istAllEmp.jsp))</b>
</body>






</html>