<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.board.model.*"%>
<%@ page import="com.member.model.*"%>

<%
// 	MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
// 	List<MemberVO> memberVOList = (List<MemberVO>)request.getAttribute("memberVOList"); //將boardTypeVOList裡的值給select表單用
	

%>

<html>
<head>
<!-- <!-- CSS only -->
<!-- <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous"> -->
<!-- <!-- JavaScript Bundle with Popper -->
<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script> -->

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>公告資料新增 - addMember.jsp</title>

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
	<tr>
	<td>
		 <h3>公告資料新增 - addMember.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/Member/select_member_page.jsp">
		 <img src="<%=request.getContextPath()%>/resource/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td>
	</tr>
</table>

<h3>資料新增:</h3>

<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color:red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/Member/member.do" name="form1" enctype="multipart/form-data">
<table>
<!-- 				<tr> -->
<!-- 						<td>會員編號:<font color=red><b>*</b></font></td> -->
<%-- 						<td><%=memberVO.getMemNo()%></td> --%>
<!-- 				</tr> -->
		
				<tr>
						<td>會員名稱:</td>
						<td><input type="TEXT" name="memName" size="45" value=" ${(empty MmberVO) ? ' 請輸入會員名稱' : '${MmberVO.memName}'  }" /></td>
				</tr>     
				<tr>
						<td>會員帳號:</td>
						<td><input type="TEXT" name="memAccount" size="45" value=" ${(empty MmberVO) ? ' 請輸入會員帳號' : '${MmberVO.memAccount}'  }" /></td>
						
				</tr>     
				<tr>
						<td>會員密碼:</td>
						<td><input type="TEXT" name="memPassword" size="45" value=" ${(empty MmberVO) ? ' 請輸入會員密碼' : '${MmberVO.memPassword}'  }" /></td>
						
				</tr>     
				<tr>
						<td>會員信箱:</td>
						<td><input type="TEXT" name="memMail" size="45" value=" ${(empty MmberVO) ? ' 請輸入會員信箱' : '${MmberVO.memMail}'  }" /></td>
						
				</tr> 
				<tr>
						<td>會員錢包:</td>
						<td><input type="TEXT" name="memWallet" size="45" value=" ${(empty MmberVO) ? ' 最少輸入為0' : '${MmberVO.memWallet}'  }" /></td>
						
				</tr>     
				<tr>
						<td>會員狀態:</td>
					<td><input type="TEXT" name="memStatus" size="45" value=" ${(empty MmberVO) ? ' 請輸入狀態' : '${MmberVO.memstatus}'  }" /></td>
						
				</tr>         
				<tr> 
						<td>會員頭像:</td>
					<td><input type="file" name="memImg" size="45"  /></td>
						
				</tr>         
</table>
	<br>
	<input type="hidden" name="action" value="insert">
	<input type="submit" value="送出新增"></FORM>
</body>

<script>
       
        
</script>
</html>