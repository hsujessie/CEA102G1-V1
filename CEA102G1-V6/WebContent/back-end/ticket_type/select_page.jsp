<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>���غ޲z</title>

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
   <tr><td><h3>���غ޲z</h3><h4><a href="<%=request.getContextPath()%>/select_page.jsp">�^��x����</a></h4></td></tr>
</table>

<p>This is the Home page for ����</p>

<h3>��Ƭd��:</h3>
<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font color='red'>�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='<%=request.getContextPath()%>/back-end/ticket_type/listAllTicket_type.jsp'>List</a> all Ticket_types. <br><br></li>
  


  <jsp:useBean id="ticket_typeSvc" scope="page" class="com.ticket_type.model.TicTypService" />    
  <jsp:useBean id="identitySvc" scope="page" class="com.identity.model.IdeService" />
  <jsp:useBean id="movie_versionSvc" scope="page" class="com.movie_version.model.MovVerService" />  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/movie_version/movie_version.do" >
       <b><font color=orange>��ܩ�M����:</font></b>
       <select size="1" name="movver_no">
         <c:forEach var="movie_versionVO" items="${movie_versionSvc.all}" > 
          <option value="${movie_versionVO.movver_no}">${movie_versionVO.movver_name}
         </c:forEach>   
       </select>
       <input type="submit" value="�e�X">
       <input type="hidden" name="action" value="listTicket_type_ByMovver_no_A">
     </FORM>
  </li>
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/identity/identity.do" >
       <b><font color=orange>��ܨ���:</font></b>
       <select size="1" name="ide_no">
         <c:forEach var="identityVO" items="${identitySvc.all}" > 
          <option value="${identityVO.ide_no}">${identityVO.ide_name}
         </c:forEach>   
       </select>
       <input type="submit" value="�e�X">
       <input type="hidden" name="action" value="listTicket_typesByIde_no_A">
     </FORM>
  </li>
</ul>





</body>
</html>