<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import = "javax.servlet.http.* " %>
<%@ page import="com.member.model.*"%>
<%@ page import="java.util.*"%>
<% 
MemberVO memberVO = (MemberVO) session.getAttribute("MemberVO");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Front-End</title>
<%@ include file="/front-end/files/frontend_importCss.file"%>
</head>
<style>
/* 	================================== */
table{
  width: 100%;
  border-collapse: collapse;
}

table tr{
  border-bottom: solid 2px white;
}

table tr:last-child{
  border-bottom: none;
}

table th{
  position: relative;
  width: 25%;
  background-color: rgba(170,145,102,0.8);
  color: white;
  text-align: center;
  padding: 10px 0;
}

table th:after{
  display: block;
  content: "";
  width: 0px;
  height: 0px;
  position: absolute;
  top:calc(50% - 10px);
  right:-10px;
  border-left: 10px solid #7d7d7d;
  border-top: 10px solid transparent;
  border-bottom: 10px solid transparent;
}

table td{
  text-align: left; 
  width: 70%;
/*   text-align: center; */
  background-color: #eee;
  padding: 10px 40px;
}
.img{
padding:

}

.main {
  margin: 20px auto;
  item-align: center;
  width: 80%;
}
.btn-pos{ 
	position: absolute; 
   
     right:50%; 
     } 


/* ================================== */


</style>
<body>
        <div class="wrapper">
            <!-- Nav Bar Start -->
			<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set>
            <%@ include file="/front-end/files/frontend_navbar.file"%>
            <!-- Nav Bar End -->
<title>會員資料管理 - update_member_input2.jsp</title>
			
			<c:if test="${not empty errorMsgs}">
				<font style="color:red">請修正以下錯誤:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color:red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>

            <!-- Page Header Start --> <!-- 看自己需不需要標題 -->
            <div class="page-header">
                <div class="container">
                    <div class="row">
                        <div class="col-12">
                            <h2>Front-End</h2> 
                        </div>
                    </div>
                </div>
            </div>
            <!-- Page Header End -->
			

            <!-- PUT HERE Start -->
            
            <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Member/member.do" name="form1" enctype="multipart/form-data">
				<table>
					<tr>
						<th>會員姓名:</th>
						<td><input type="TEXT" name="memName" size="45"	value="<%=memberVO.getMemName()%>" /></td>
					</tr>
					<tr>
						<th>會員帳號:<font color=red><b>*</b></font></th>
						<td><%=memberVO.getMemAccount()%></td>
					</tr>  
					<tr>
						<th>會員密碼:</th>
						<td><input type="password" name="memPassword" size="45"	value="<%=memberVO.getMemPassword()%>" /></td>
					</tr>
					<tr>
						<th>會員信箱:<font color=red><b>*</b></font></td>
						<td><%=memberVO.getMemMail()%></td>
					</tr>
					<tr>
						<th>會員大頭照:</th>
						<td><img src="<%=request.getContextPath()%>/util/imgReader${MemberVO.memImgParam}" style="width:100px; height:100px">
						<input class=img type="file" name="memImg" size="45"value="" /></td>
					</tr>
				</table>
				<br>
				
					<input type="hidden" name="action" value="updateFront">
					<input type="hidden" name="memNo" value="<%=memberVO.getMemNo()%>">
					<input type="hidden" name="memAccount" value="<%=memberVO.getMemAccount()%>">
					<input type="hidden" name="memMail" value="<%=memberVO.getMemMail()%>">
					<input type="hidden" name="memWallet" value="<%=memberVO.getMemWallet()%>" />
					<input type="hidden" name="memstatus" value="<%=memberVO.getMemstatus()%>" />
					<a class="btn btn-light btn-brd grd1 effect-1 btn-pos">
						<input type="submit" value="送出修改">
					</a>
			</FORM>
            <!-- PUT HERE End -->
            
            <!-- Book Tickets Start -->
            <%@ include file="/front-end/files/frontend_bookTicketsTamplate.file"%>
            <!-- Book Tickets End -->

            <!-- Footer Start -->
            <%@ include file="/front-end/files/frontend_footer.file"%>
            <!-- Footer End -->
        </div>
        
<%@ include file="/front-end/files/frontend_importJs.file"%>   
<!-- <br>本網頁的路徑:<br><b> -->
<%--    <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br> --%>
<%--    <font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%> </b> --%>
</body>
</html>