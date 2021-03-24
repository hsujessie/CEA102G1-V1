<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.board.model.*"%>
<%@ page import="com.member.model.MemberVO"%>
<%@ page import="java.util.*"%>

<%
	MemberVO memberVO = (MemberVO) request.getAttribute("MemberVO");
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>會員資料修改 - update_member_input.jsp</title>

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
	width: 550px;
	background-color: lime;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 1px solid #CCCCFF;
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
				<h3>會員資料修改 - Front_forgot_Memuptate.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/front-end/index.jsp"> <%-- 回首頁--%>
						<img src="<%=request.getContextPath()%>/resource/images/back1.gif"
						width="100" height="32" border="0">回首頁
					</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料修改:</h3>

	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post"
		ACTION="<%=request.getContextPath()%>/Member/member.do" name="form1"
		enctype="multipart/form-data">
		<table>
			<input type="hidden" name="memNo" value="<%=memberVO.getMemNo()%>" />
			<tr>
				<td>會員姓名:</td>
				<td><input type="TEXT" name="memName" size="45"
					value="<%=memberVO.getMemName()%>" /></td>
			</tr>
			<tr>
				<td>會員帳號:<font color=red><b>*</b></font></td>
				<td><%=memberVO.getMemAccount()%></td>
			</tr>
			<tr>
				<td>會員密碼:</td>
				<td><input type="TEXT" name="memPassword" size="45"
					value="<%=memberVO.getMemPassword()%>" /></td>
			</tr>
			<tr>
				<td>會員mail:<font color=red><b>*</b></font></td>
				<td><%=memberVO.getMemMail()%></td>
			</tr>
			<tr>
				<td>會員大頭照:</td>
				<td><img
					src="<%=request.getContextPath()%>/Member/reader.do?memNo=${memberVO.memNo}"
					width="100" height="100"> <input type="file" name="memImg"
					size="45" value="" /></td>

			</tr>

		</table>

		<br> <input type="hidden" name="action" value="updateFront">
		<input type="hidden" name="memNo" value="<%=memberVO.getMemNo()%>">
		<input type="hidden" name="memAccount"
			value="<%=memberVO.getMemAccount()%>"> <input type="hidden"
			name="memMail" value="<%=memberVO.getMemMail()%>"> <input
			type="hidden" name="memWallet" value="<%=memberVO.getMemWallet()%>" />
		<input type="hidden" name="memstatus"
			value="<%=memberVO.getMemstatus()%>" /> <input type="submit"
			value="送出修改">
	</FORM>
</body>

<script>
	// 單一頁面預覽
	$("#progressbarTWInput").change(function() {
		readURL(this);
	});

	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$("#preview_progressbarTW_img").attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
</script>

</html>