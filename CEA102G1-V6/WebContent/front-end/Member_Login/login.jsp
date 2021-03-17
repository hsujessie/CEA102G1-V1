<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<%
	MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");

%>
<html>
<head>
<meta charset="UTF-8">
<title>login page</title>

<%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/resource/bootstrap/css/bootstrap.min.css"> --%>
<!-- <link rel="stylesheet" href="https://yourwebsit.com/style.css"> -->

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/bootstrap/css3/login3.css">

</head>
<body>

	<script src="<%=request.getContextPath()%>/resource/jquery/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/resource/popper/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/resource/bootstrap/js3/newlogin.js"></script>

<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

		<div class="cotn_principal">
			<div class="cont_centrar">
				<div class="cont_login">
					<div class="cont_info_log_sign_up">
						<div class="col_md_login">
							<div class="cont_ba_opcitiy"><h2>LOGIN</h2>  
								<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p> 
								  <button class="btn_login" onclick="cambiar_login()">LOGIN</button>
							</div>
						</div>
						
								<div class="col_md_sign_up">
									<div class="cont_ba_opcitiy"><h2>SIGN UP</h2>						
								  	<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
									<button class="btn_sign_up" onclick="cambiar_sign_up()">SIGN UP</button>
									</div>
								</div>
						</div>
								 <div class="cont_back_info">
								       <div class="cont_img_back_grey">
								       <img src="https://images.unsplash.com/42/U7Fc1sy5SCUDIu4tlJY3_NY_by_PhilippHenzler_philmotion.de.jpg?ixlib=rb-0.3.5&q=50&fm=jpg&crop=entropy&s=7686972873678f32efaf2cd79671673d" alt="" />
								       </div>
								  </div>
								  
									<div class="cont_forms" >
								   		 <div class="cont_img_back_">
								       	<img src="https://images.unsplash.com/42/U7Fc1sy5SCUDIu4tlJY3_NY_by_PhilippHenzler_philmotion.de.jpg?ixlib=rb-0.3.5&q=50&fm=jpg&crop=entropy&s=7686972873678f32efaf2cd79671673d" alt="" />
								       	</div>
								       	<form METHOD="post" ACTION="<%=request.getContextPath()%>/Member/member.do" name="form1"> 
								 			<div class="cont_form_login">
											<a href="#" onclick="ocultar_login_sign_up()" ><i class="material-icons">Back</i></a>
								  			 <h2>LOGIN</h2>
								 			<input type="text"  name ="memAccount" placeholder="Account" />
											<input type="password"  name ="memPassword" placeholder="Password" />
											<button type="submit" class="btn_login" onclick="cambiar_login()">LOGIN</button>
											<input type="hidden" name="action" value="get_Login">
										</form>
										
											<div class="foot-lnk">
												<a href="<%=request.getContextPath()%>/front-end/Member_Login/forgot_password.jsp">忘記密碼?</a>
											</div>
								  			</div>
								  	   
								  	   <form METHOD="post" ACTION="<%=request.getContextPath()%>/Member/member.do" name="form2"enctype="multipart/form-data"> 
										   <div class="cont_form_sign_up">
												<a href="#" onclick="ocultar_login_sign_up()"><i class="material-icons">Back</i></a>
											    <h2>SIGN UP</h2>
												<input type="text"  name="memName" placeholder="Name" />
												<input type="text"  name="memAccount" placeholder="Account" />
												<input type="password" name="memPassword"  placeholder="Password" />
												<input type="text"  name="memMail" placeholder="Mail" />
												<input type="file" name="memImg" placeholder="Img" width="40" height="40" border="0"/>
												<button type="submit" class="btn_sign_up" onclick="cambiar_sign_up()">SIGN UP</button>
												<input type="hidden" name="action" value="signup">
												
											</div>
										</form>
									</div>
			</div>
		</div>
	</div>
	
        
       		
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				

</body>
</html>