<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.board.model.*"%>
<%@ page import="com.member.model.MemberVO"%>
<%@ page import="java.util.*"%>
<%
MemberVO memberVO = (MemberVO) request.getAttribute("MemberVO"); 
List<MemberVO> memberVOList = (List<MemberVO>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Front-End</title>
<%@ include file="/front-end/files/frontend_importCss.file"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/datetimepicker/jquery.datetimepicker.css" />
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


/* 	================================== */


</style>
<body>
        <div class="wrapper">
            <!-- Nav Bar Start -->
			<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set>
            <%@ include file="/front-end/files/frontend_navbar.file"%>
            <!-- Nav Bar End -->
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
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Member/member.do" name="form1" enctype="multipart/form-data">
					<table>
				<tr>
					<th>會員名稱:</th>
					<td><%= memberVO.getMemName()%></td>
				</tr>	
				<tr>
					<th>會員帳號</th>
					<td><%= memberVO.getMemAccount()%></td>
				</tr>
				<tr>	
					<th>會員密碼</th>
					<td><%= memberVO.getMemPassword()%></td>
				</tr>
				<tr>	
					<th>會員信箱</th>
					<td><%= memberVO.getMemMail()%></td>
				</tr>
				<tr>
					<th>會員錢包</th>
					<td><%= memberVO.getMemWallet()%></td>
				</tr>
				<tr>
					<th>會員圖像</th>
					<td><img src="<%=request.getContextPath()%>/util/imgReader${MemberVO.memImgParam}" width="100" height="100"></td>
				</tr>
				
				
			</table>
		
								
		 	</FORM>

            <!-- PUT HERE Start -->
            
            
            <!-- PUT HERE End -->
            
            <!-- Book Tickets Start -->
            <%@ include file="/front-end/files/frontend_bookTicketsTamplate.file"%>
            <!-- Book Tickets End -->

            <!-- Footer Start -->
            <%@ include file="/front-end/files/frontend_footer.file"%>
            <!-- Footer End -->
        </div>
        
<%@ include file="/front-end/files/frontend_importJs.file"%>
<br>本網頁的路徑:<br><b>
   <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%> </b>  
   <script language=javascript>
		setTimeout('window.location="<%=request.getContextPath()%>/front-end/index.jsp"', 5000)  <%-- 三秒後轉登入畫面--%>
	</script> 
</body>
</html>