<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>�U�|�޲z</title>

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
   <tr><td><h3>�U�|�޲z</h3>
   <h4><a href="<%=request.getContextPath()%>/back-end/index.jsp">�^��x����</a></h4></td></tr>
</table>
	
<p>This is the Home page for Theater</p>

<h3>��Ƭd��:</h3>
<c:if test="${not empty errorMsgs}">
	<font color='red'>�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='<%=request.getContextPath()%>/back-end/theater/listAllTheater.jsp'>List</a> all Theaters. <br><br></li>
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/theater/theater.do" >
        <b>��J�U�|�s�� :</b>
        <input type="text" name="the_no">
        <input type="submit" value="�e�X">
        <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
   <br></li>
     
  <jsp:useBean id="theSvc" scope="page" class="com.theater.model.TheaterService" />
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/theater/theater.do" >
       <b>����U�|�s��:</b>
       <select size="1" name="the_no">
         <c:forEach var="theaterVO" items="${theSvc.all}" > 
          <option value="${theaterVO.the_no}">${theaterVO.the_no}
         </c:forEach>   
       </select>
       <input type="submit" value="�e�X">
       <input type="hidden" name="action" value="getOne_For_Display">
    </FORM>
   <br></li>

  
  
   <jsp:useBean id="movie_versionSvc" scope="page" class="com.movie_version.model.Movie_versionService" />
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/movie_version/movie_version.do" >
       <b><font color=orange>��ܩ�M����:</font></b>
       <select size="1" name="movver_no">
         <c:forEach var="movie_versionVO" items="${movie_versionSvc.all}" > 
          <option value="${movie_versionVO.movver_no}">${movie_versionVO.movver_name}
         </c:forEach>   
       </select>
       <input type="submit" value="�e�X">
       <input type="hidden" name="action" value="listTheaters_Bymovie_version_A">
     </FORM>
   <br></li>
</ul>

<h3>�U�|�޲z</h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/back-end/theater/addTheater.jsp'>Add</a> a new Theater.</li>
</ul>


<h3><font color=orange>��M�����޲z</font></h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/back-end/movie_version/listAllMovie_version.jsp'>List</a> all Movie_versions. </li>
</ul>



</body>
</html>