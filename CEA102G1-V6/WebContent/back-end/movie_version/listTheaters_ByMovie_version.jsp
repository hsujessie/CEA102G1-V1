<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.theater.model.*"%>

<jsp:useBean id="movie_versionSvc" scope="page" class="com.movie_version.model.MovVerService" />


<html>
<head><title>��M�U�|</title>

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

<h4>�����m�߱ĥ� EL ���g�k����:</h4>
<table id="table-2">
	<tr><td>
		 <h3>��M�U�|</h3>
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
		<th>�U�|�s��</th>
		<th>�v�U����</th>
		<th>�U�|�y��s��</th>
		<th>�U�|�y��s��</th>
	</tr>
	
	<c:forEach var="theaterVO" items="${listTheaters_Bymovie_version}" >
		<tr ${(theaterVO.the_no==param.the_no) ? 'bgcolor=#CCCCFF':''}><!--�N�ק諸���@���[�J����-->
			<td>${theaterVO.the_no}</td>
			<td><c:forEach var="movie_versionVO" items="${movie_versionSvc.all}">
                    <c:if test="${theaterVO.movver_no==movie_versionVO.movver_no}">
	                    �i<font color=orange>${movie_versionVO.movver_name}</font>�j
                    </c:if>
                </c:forEach>
			</td>
			<td>${theaterVO.the_seat}</td>
			<td>${theaterVO.the_seatno}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/theater/theater.do" style="margin-bottom: 0px;">
			    <input type="submit" value="�ק�"> 
			    <input type="hidden" name="the_no"      value="${theaterVO.the_no}">
			    <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller--><!-- �ثe�|���Ψ�  -->
			    <input type="hidden" name="action"	   value="getOne_For_Update"></FORM>
			</td>

		</tr>
	</c:forEach>
</table>

<br>�����������|:<br><b>
   <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%> </b>

</body>
</html>