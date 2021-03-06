<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/resource/jquery/jquery-3.5.1.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/bootstrap/js/bootstrap.min.js"></script>
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

body{
/*   border: 1px solid red; */
  margin: 0 auto;  
}
div.container{
text-align: center; 
}
form{
margin: 0  auto;
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
					<font style="color:red">?????????????????????:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color:red">${message}</li>
							</c:forEach>
						</ul>
				</c:if>

            <!-- Page Header Start --> <!-- ??????????????????????????? -->
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
            <div class="container">
						<h1>????????????</h1>				
						<p><a href="login.jsp">
						<img src="<%=request.getContextPath()%>/resource/images/forgot.jpg" width="100" height="100" border="0">???????????????</a></p>
			</div>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Member/member.do" name="form"  style="width: 450px;">

					<div class="form-group">
						<label for="exampleInputAccount">???????????????:</label> 
						<input class="form-control" type="TEXT" name="memAccount" size="45" value="" />
					</div>
					<div class="form-group">
						<label for="exampleInputmail">???????????????:</label> 
						<input class="form-control" type="TEXT" name="memMail" size="45" value="" />
					</div>
					<br>
					<input class="form-control" type="hidden" name="action" value="forgot_password">
					<button type="submit" class="btn btn-primary">????????????</button>
					<br>
			</Form>	

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
<br>??????????????????:<br><b>
   <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%> </b>  

</body>
</html>