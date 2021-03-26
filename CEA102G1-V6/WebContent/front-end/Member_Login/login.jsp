<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Front-End</title>
<%@ include file="/front-end/files/frontend_importCss.file"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/bootstrap/css3/login3.css">
 <!-- toastr v2.1.4 -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css" rel="stylesheet" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>
</head>
<body>
<script>
        toastr.options = {
            // 參數設定
            "closeButton": false,
            "debug": false,
            "newestOnTop": false,
            "progressBar": true,
            "positionClass": "toast-top-right",
            "preventDuplicates": false,
            "onclick": null,
            "showDuration": "300",
            "hideDuration": "1000",
            "timeOut": "5000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        }
    </script>
<style>
#preview{
	float:left;
}
img{
    width: 40px;
}

</style>
        <div class="wrapper"><!-- wrapper Start -->
            <!-- Nav Bar Start -->
			<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set>
            <%@ include file="/front-end/files/frontend_navbar.file"%>
            <!-- Nav Bar End -->
	   </div><!-- wrapper End -->
	   					  

            <!-- PUT HERE Start -->
            <div class="cotn_principal">
				<div class="cont_centrar">
<!-- 				 			<div class="success"> -->
<%-- 								成功表列 --%>
<%-- 								<c:if test="${not empty success}"> --%>
<%-- 										<c:forEach var="message" items="${success}"> --%>
<%-- 											<h5><span style="color: green">${message}</span></h5><br> --%>
<%-- 										</c:forEach> --%>
<%-- 								</c:if> --%>
<!-- 						   </div> -->
	   
	   
<!-- 	  						<div class="errorMegs"> -->
<%-- 								錯誤表列 --%>
<%-- 								<c:if test="${not empty errorMsgs}"> --%>
<!-- 									<h5><font style="color: red">請修正以下錯誤:</font></h5> -->
<%-- 										<c:forEach var="message" items="${errorMsgs}"> --%>
<%-- 										<h5><span style="color: red">${message}</span></h5><br> --%>
<%-- 										</c:forEach> --%>
<%-- 								</c:if> --%>
<!-- 						   </div> -->
								 //修改成功訊息
								            if ('${updateSuccess}' == 'updateSuccess') {
								                //      	debugger;
								                <%
								                session.removeAttribute("updateSuccess"); 
								                %>
								                toastr['success']('修改成功！！', '成功'); 
								            }
								
								            //新增成功訊息
								            if ('${addSuccess}' == 'addSuccess') {
								                //      	debugger;
								                <%
								                session.removeAttribute("addSuccess"); 
								                %>
								                toastr['success']('發文成功！！', '成功'); 
								            }
							
					<div class="cont_login">
<!--============================================================================================-->						
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
<!--============================================================================================-->							
							<div class="cont_back_info">
								<div class="cont_img_back_grey">
								   <img src="https://images.unsplash.com/42/U7Fc1sy5SCUDIu4tlJY3_NY_by_PhilippHenzler_philmotion.de.jpg?ixlib=rb-0.3.5&q=50&fm=jpg&crop=entropy&s=7686972873678f32efaf2cd79671673d" alt="" />
								 </div>
							 </div>
<!--============================================================================================-->									  
							<div class="cont_forms" >
								 <div class="cont_img_back_">
								     <img src="https://images.unsplash.com/42/U7Fc1sy5SCUDIu4tlJY3_NY_by_PhilippHenzler_philmotion.de.jpg?ixlib=rb-0.3.5&q=50&fm=jpg&crop=entropy&s=7686972873678f32efaf2cd79671673d" alt="" />
								 </div>
								       	
								       	<div class="cont_form_login">
										   <form METHOD="post" ACTION="<%=request.getContextPath()%>/Member/member.do" name="form1">
												<a class="login-a-sty" href="#" onclick="ocultar_login_sign_up()" ><i class="material-icons">Back</i></a>
										  		<h2 class="login-sty">LOGIN</h2>
										 		<input class="login-input-sty" type="text"  name ="memAccount" placeholder="Account" />
												<input class="login-input-sty"  type="password"  name ="memPassword" placeholder="Password" />
												<button type="submit" class="btn_login" onclick="cambiar_login()">LOGIN</button>
												<input type="hidden" name="action" value="get_Login">
											</form>
													<div class="foot-lnk">
														<a href="<%=request.getContextPath()%>/front-end/Member_Login/forgot_password_new.jsp">忘記密碼?</a>
													</div>
										</div>
<!--============================================================================================-->								  			
									  	   <form METHOD="post" ACTION="<%=request.getContextPath()%>/Member/member.do" name="form2"enctype="multipart/form-data"> 
												<div class="cont_form_sign_up">
													<a href="#" onclick="ocultar_login_sign_up()"><i class="material-icons">Back</i></a>
													<h2>SIGN UP</h2>
													<input type="text"  name="memName" placeholder="Name" />
													<input type="text"  name="memAccount" placeholder="Account" />
													<input type="password" name="memPassword"  placeholder="Password" />
													<input type="text"  name="memMail" placeholder="Mail" />
													
													 
													<div class="row">
													<div id="preview" style="padding:5px;"  ></div>
											            <input  id="myImg" type="file" name="memImg" placeholder="Img" style="padding:5px;width:90px;margin-right: 50px;" />
											        </div> 
											           
													
											        
													
													<button type="submit" class="btn_sign_up" onclick="cambiar_sign_up()">SIGN UP</button>
													<input type="hidden" name="action" value="signup">
												</div>
										 </form>
<!--============================================================================================-->										 
								</div>
							</div>
						</div>
					</div>
            
            <!-- PUT HERE End -->
            
       
        
<%@ include file="/front-end/files/frontend_importJs.file"%>
<script src="<%=request.getContextPath()%>/resource/bootstrap/js3/newlogin.js"></script>
<script>
function init() {

    var myFile = document.getElementById("myImg");
       var preview = document.getElementById('preview');
    myFile.addEventListener('change', function(e) {
        let files = e.target.files;
        if (files) {
            for (let i = 0; i < files.length; i++) {
                let file = files[i];
                if (file.type.indexOf('image') > -1) {
                    let reader = new FileReader();
                    reader.addEventListener('load', function(e) {
                        let result = e.target.result;
                        console.log(result);
                        let img = document.createElement('img');
                        img.setAttribute('src', result);
                        preview.append(img);
                    });
                    reader.readAsDataURL(file); 
                }
//                 else {
//                     // 彈出警告視窗 
//                     alert('請上傳圖片！');
//                 }
            }
        }
    });

}

window.onload = init;
</script> 
</body>
</html>