<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>IBM Board: Home</title>
<title>Insert title here</title>
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
<body>


<table id="table-1">
   <tr><td><h3>IBM Board: Home</h3><h4>( MVC )</h4></td></tr>
</table>




<h3>公告資料查詢:</h3> 
 
  <ul>
	  <li>
		    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Board/board.do">
			     <input type="hidden" name="action" value="getAll">
			     <input type="submit" value="全部查循">
		    </FORM>
	  </li>

			<jsp:useBean id="boardSvc" scope="page" class="com.board.model.BoardService" />

	  <li>
		     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Board/board.do" >
		       <b>選擇公告編號:</b>
		       <select size="1" name="boaNo">
		         <c:forEach var="boardVO" items="${boardSvc.all}" > 
		          <option value="${boardVO.boaNo}">${boardVO.boaNo}
		         </c:forEach>   
		       </select>
		       <input type="hidden" name="action" value="getOne_For_Display">
		       <input type="submit" value="送出">
		    </FORM>
	  </li>
	  
			<jsp:useBean id="boardTypeSvc" scope="page" class="com.board_type.model.BoardTypeService" />

	  <li>
		     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Board/board.do" >
		       <b>選擇公告種類編號:</b>
		       <select size="1" name="boatypNo">
		         <c:forEach var="boardTypeVO" items="${boardTypeSvc.all}" > 
		          <option value="${boardTypeVO.boatypNo}">${boardTypeVO.boatypName}
		         </c:forEach>   
		       </select>
		       <input type="hidden" name="action" value="getOne_For_Display2">
		       <input type="submit" value="送出">
		    </FORM>
	  </li>

</ul>

<h3>新增公告:</h3> 

		<ul>
			<li>
			       <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Board/board.do" >
				       <input type="hidden" name="action" value="getAllA">
				       <input type="submit" value="Add">
				    </FORM>
			</li>	    
		</ul>



<br>本網頁的路徑:<br><b>
   <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%> </b>

</body>
</html>