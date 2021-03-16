<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<title>Login Successful</title>
	<%@ include file="/back-end/files/sb_head.file"%>
</head>
<body class="sb-nav-fixed">
		<%@ include file="/back-end/files/sb_navbar.file"%> <!-- 引入navbar (上方) -->
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">       
				<%@ include file="/back-end/files/sb_sidebar.file"%> <!-- 引入sidebar (左方) -->
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                    
                    <!-- Login successful Start -->
			        <div class="loginSucs">
			            <div class="container">
			                <div class="section-header text-center">
			                    <h4 style="color:#bb9d52;"><i class="far fa-check-circle" style="color:#bb9d52;"></i>&ensp;LOGIN&ensp;SUCCESSFUL</h4>
			                </div>
			                <div class="row">
			                    <div class="col-lg-4 col-md-4">
			                    </div>
			                    <div class="col-lg-4 col-md-4">
			                        <div class="loginSucs-item">
			                            <div class="loginSucs-img">
			                                <img src="<%=request.getContextPath()%>/resource/images/tomcat.png" alt="loginSucs Image">
			                            </div>
			                            <div class="loginSucs-text">
			                                <h2>Tomcat</h2>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="col-lg-4 col-md-4">
			                    </div>
			                </div>
			            </div>
			        </div>
			        <!-- Login successful End -->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
    </body>
</html>