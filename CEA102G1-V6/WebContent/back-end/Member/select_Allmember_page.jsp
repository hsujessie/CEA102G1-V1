<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="javax.servlet.http.* "%>
<%
	MemberService memberSvc = new MemberService();
	List<MemberVO> list = memberSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<html>
<head>
<title>Sessions Management</title>
<%@ include file="/back-end/files/sb_head.file"%>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resource/datetimepicker/jquery.datetimepicker.css" />
</head>
<style>
.success-span {
	color: #bb9d52;
	position: absolute;
	top: 10%;
	left: 20%;
	font-size: 16px;
}

.form-sty {
	margin: 20px 0 0 0;
}
</style>
<body class="sb-nav-fixed">
	<%@ include file="/back-end/files/sb_navbar.file"%>
	<!-- 引入navbar (上方) -->
	<div id="layoutSidenav">
		<div id="layoutSidenav_nav">
			<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set>
			<!-- 給sb_sidebar.file的參數-Home -->
			<%@ include file="/back-end/files/sb_sidebar.file"%>
			<!-- 引入sidebar (左方) -->
		</div>
		<div id="layoutSidenav_content">
			<main>
				<div class="container-fluid">

					<h3 class="h3-style" style="display: inline-block;">所有會員資料</h3>
					<!-- success message Start -->
					<%-- 						<c:if test="${addSuccess != null}"> --%>
					<!-- 							<span class="success-span">  -->
					<%-- 								${addSuccess} --%>
					<!-- 								<i class="far fa-smile-wink"></i> -->
					<!-- 							</span> -->
					<%-- 						</c:if> --%>
					<%-- 						<c:if test="${updateSuccess != null }"> --%>
					<!-- 							<span class="success-span">  -->
					<%-- 								${updateSuccess} --%>
					<!-- 								<i class="far fa-smile-wink"></i> -->
					<!-- 							</span> -->
					<%-- 						</c:if> --%>
					<c:if test="${not empty errorMsgs}">
						<font style="color: red">請修正以下錯誤:</font>
						<br>
						<c:forEach var="message" items="${errorMsgs}">
							<span style="color: red">${message}</span>
						</c:forEach>
					</c:if>
					<!-- success message End -->

					<!-- search Start -->
					<div class="row " style="margin: 0px 0 20px -200px;">
						<div class="col-2"></div>
						<div class="col-10">
							<FORM class="form-sty" METHOD="post"
								ACTION="<%=request.getContextPath()%>/Member/member.do">
								<b>會員編號:</b> <input type="text" name="mem_No" value="1">
								<b>會員姓名:</b> <input type="text" name="mem_Name" value="Jack">
								<b>會員帳號:</b> <input type="text" name="mem_Account" value="abc">
								<b>會員信箱:</b> <input type="text" name="mem_Mail"
									value="qwe@gmail.com"> <a
									class="btn btn-light btn-brd grd1 effect-1"> <input
									type="submit" value="搜尋" class="input-pos"> <input
									type="hidden" name="action" value="listMember_ByCompositeQuery">
								</a>
							</FORM>
						</div>
					</div>
					<!-- search End -->

					<!-- listSession Start -->
					<table class="table table-hover">
						<thead>
							<tr style="border-bottom: 3px solid #bb9d52;">
								<th>會員編號</th>
								<th>會員名稱</th>
								<th>會員帳號</th>
								<th>會員密碼</th>
								<th>會員信箱</th>
								<td>會員圖片</td>
								<th>會員狀態</th>
								<th>修改</th>
								<th>修改狀態</th>
							</tr>
						</thead>

						<tbody>
							<%@ include file="/back-end/Member/pages/page1.file"%>
							<c:forEach var="memberVO" items="${list}" begin="<%=pageIndex%>"
								end="<%=pageIndex+rowsPerPage-1%>">

								<tr class="sty-height" valign='middle'>
									<td>${memberVO.memNo}</td>
									<td>${memberVO.memName}</td>
									<td>${memberVO.memAccount}</td>
									<td>${memberVO.memPassword}</td>
									<td>${memberVO.memMail}</td>
									<td><img src="<%=request.getContextPath()%>/util/imgReader${MemberVO.memImgParam}" width="100" height="100"></td>
									<td>${(memberVO.memstatus==0)?"未啟動":(memberVO.memstatus==1?"已啟動":"已停權")}</td>

									<td>
										<FORM METHOD="post"
											ACTION="<%=request.getContextPath()%>/Member/member.do">
											<a class="btn btn-light btn-brd grd1 effect-1"> <input
												type="submit" value="修改" class="input-pos"> <input
												type="hidden" name="memNo" value="${memberVO.memNo}">
												<input type="hidden" name="action" value="getOne_For_Update">
											</a>
										</FORM>
									</td>
									<td>
										<FORM METHOD="post"
											ACTION="<%=request.getContextPath()%>/Member/member.do">
											<a class="btn btn-light btn-brd grd1 effect-1"> <input
												type="submit"
												value=${(memberVO.memstatus==0)?"啟動":(memberVO.memstatus==1?"停權":"啟動")}
												class="input-pos"> <input type="hidden"
												name="requestURL" value="<%=request.getServletPath()%>">
												<input type="hidden" name="memNo" value="${memberVO.memNo}">
												<input type="hidden" name="action" value="change_Status">
											</a>
										</FORM>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<%@ include file="/back-end/Member/pages/page2.file"%>
					<!-- listSession End -->

				</div>
			</main>
			<%@ include file="/back-end/files/sb_footer.file"%>
		</div>
	</div>
	<%@ include file="/back-end/files/sb_importJs.file"%>
	<!-- 引入template要用的js -->
</body>
</html>