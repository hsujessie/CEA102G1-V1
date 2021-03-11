<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import = "javax.servlet.http.* " %>

<%	
List<MemberVO> memberVO = (List<MemberVO>)request.getAttribute("list");//從controller傳回View,jsp不能簡寫唷!!
System.out.println(memberVO);
%>


<html>
<head>
<title>所有公告資料 - listAllMember.jsp</title>
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
	width:850px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 2px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
    text-align: center;
  }
</style>

<table id="table-1">
	<tr>
	<td>
		 <h3>所有會員資料 - listAllMember.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back_end/Member/select_member_page.jsp">
		 <img src="<%=request.getContextPath()%>/resource/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td>
	</tr>
</table>

<%-- 錯誤表列 --%> 
<%-- <c:if test="${not empty errorMsgs}"> --%>
<!-- 	<font style="color:red">請修正以下錯誤:</font> -->
<!-- 	<ul> -->
<%-- 		<c:forEach var="message" items="${errorMsgs}"> --%>
<%-- 			<li style="color:red">${message}</li> --%>
<%-- 		</c:forEach> --%>
<!-- 	</ul> -->
<%-- </c:if> --%>


<table>
	<tr>
		<th>會員編號</th>
		<th>會員名稱</th>
		<th>會員帳號</th>
		<th>會員密碼</th>
		<th>會員mail</th>
		
		<th>修改</th>
		<th>刪除</th>
	</tr>
 
	<c:forEach var="memberVO" items="${list}" >
		
			<tr>
				<td>${memberVO.memNo}</td>
				<td>${memberVO.memName}</td>
				<td>${memberVO.memAccount}</td>
				<td>${memberVO.memPassword}</td>
				<td>${memberVO.memMail}</td>
				
				<td>
						   <FORM METHOD="post" ACTION="<%=request.getContextPath() %>/Member/member.do"  style="margin-bottom: 0px;">
								   <input type="submit" value="修改">
								   <input type="hidden" name="memNo"  value="${memberVO.memNo}">
								   <input type="hidden" name="action"	value="getOne_For_Update">
						   </FORM>
				</td>
				
				<td>
						    <FORM METHOD="post" ACTION="<%=request.getContextPath() %>/Member/member.do" style="margin-bottom: 0px;">
									<input type="submit" value="刪除">
									<input type="hidden" name="memNo"  value="${memberVO.memNo}">
									<input type="hidden" name="action" value="delete">
							</FORM>
				</td>
				
				
				
			</tr>
			
		</c:forEach>
</table>


</body>
</html>