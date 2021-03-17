
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
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
   <tr><td><h3>IBM Member: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<h3>會員資料查詢:</h3>

			<ul>
				  <li>
					    <FORM METHOD="post" ACTION="<%=request.getContextPath() %>/Member/member.do">
						     <input type="hidden" name="action" value="getAll">
						     <input type="submit" value="全部查循">
					    </FORM>
				  </li>
				  
<!-- 				 	<li>    -->
<%-- 					    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Member/member.do" name="form1"> --%>
<!-- 					        <b><font color=blue>萬用複合查詢:</font></b> <br> -->
<!-- 					        <b>輸入會員名稱:</b> -->
<!-- 					        <input type="text" name="memName" value=""><br> -->
					           
<!-- 					       <b>輸入會員帳號:</b> -->
<!-- 					       <input type="text" name="memAccount" value=""><br> -->
					       
<!-- 					       <b>輸入會員信箱:</b> -->
<!-- 					       <input type="text" name="memMail" value=""><br> -->
					    
<!-- 					       <b>選擇會員圖片:</b> -->
<!-- 					       <input type="text" name="memImg" value=""><br> -->
					        
<!-- 					        <input type="submit" value="送出"> -->
<!-- 					        <input type="hidden" name="action" value="listMembers_ByCompositeQuery"> -->
<!-- 					     </FORM> -->
<!-- 					 </li> -->
			</ul>
			
<!-- <h2></h2> -->
<!-- <h3>新增會員資料:</h3>  -->
<!-- 		<ul> -->
<!-- 			<li> -->
<%-- 					    <FORM METHOD="post" ACTION="<%=request.getContextPath() %>/back-end/Member/addMember.jsp"> --%>
<!-- 						    <input type="hidden" name="action" value="insert"> -->
<!-- 				       		<input type="submit" value="Add"> -->
<!-- 					    </FORM> -->
<!-- 			 </li> -->
  
<!-- 		</ul> -->




</body>
</html>