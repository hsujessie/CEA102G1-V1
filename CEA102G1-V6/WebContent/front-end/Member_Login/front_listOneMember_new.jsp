<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.board.model.*"%>
<%@ page import="com.member.model.MemberVO"%>
<%@ page import="java.util.*"%>
<%
MemberVO memberVO = (MemberVO) request.getAttribute("MemberVO"); 
System.out.println(memberVO);
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
 width: 55%;
  border-collapse: collapse;
  margin-left:340px;
 border-width:3px;
 border-style:dashed;
 border-color:#FFAC55;
 padding:5px;"
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
  color: #000000;
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
   
     right:40%; 
     } 
 
 }    
     
.errorMegs{
font-size:16px;
border-width:0px;
border-style:dashed;
border-color:#ffffff;
padding:5px;
width:600px;
height:35px;
top:153px;
left:462px;
position: absolute;
}

/* 	================================== */


</style>
<body>
        <div class="wrapper">
            <!-- Nav Bar Start -->
			<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set>
            <%@ include file="/front-end/files/frontend_navbar.file"%>
            <!-- Nav Bar End -->
         </div>
			<!-- Page Header Start --> <!-- 看自己需不需要標題 -->
            
            <!-- Page Header End -->
			<!--錯誤表列 -->
            <div class="errorMegs" style="color: red;">
				<c:if test="${not empty errorMsgs}">
					<b><font >請修正錯誤:</font></b>
						<c:forEach var="message" items="${errorMsgs}">
							<b><span style="color: red">${message}</span></b>
						</c:forEach>
				</c:if>
			</div>

            <!-- Page Header Start --> <!-- 看自己需不需要標題 -->
            <div class="page-header">
                <div class="container">
                    <div class="row">
                        <div class="col-12">
                            <h2 style="color: rgba(192,210,256,0.8);text-shadow: 7px 5px 4px rgba(0,0,0,1);">已更新會員資料</h2> 
                        </div>
                    </div>
                </div>
            </div>
            <!-- Page Header End -->
			<FORM class="from1"METHOD="post" ACTION="<%=request.getContextPath()%>/Member/member.do" name="form1" enctype="multipart/form-data">
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
					<td><img src="<%=request.getContextPath()%>/util/imgReader${MemberVO.memImgParam}" style="width:100px; height:100px"></td>
				</tr>
				
				
			</table>
		 	</FORM>
							<div style="width:100px;height:30px;left:720px;top:20px;position:relative;border-width:3px;">
								<a href="<%=request.getContextPath()%>/front-end/index.jsp">
							    	<button style="text-align:center;font-size:18px;top:20px;">回上頁</button>
							    </a> 
							 </div>   
            <!-- PUT HERE Start -->
            
            
            <!-- PUT HERE End -->
            
            <!-- Book Tickets Start -->
            <%@ include file="/front-end/files/frontend_bookTicketsTamplate.file"%>
            <!-- Book Tickets End -->

            <!-- Footer Start -->
            <%@ include file="/front-end/files/frontend_footer.file"%>
            <!-- Footer End -->
        
        
<%@ include file="/front-end/files/frontend_importJs.file"%>
<!-- <br>本網頁的路徑:<br><b> -->
<%--    <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br> --%>
<%--    <font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%> </b>   --%>
   <script language=javascript>
		setTimeout('window.location="<%=request.getContextPath()%>/front-end/index.jsp"', 3000)  <%--三秒後轉登入畫面--%>
   </script> 
</body>
</html>