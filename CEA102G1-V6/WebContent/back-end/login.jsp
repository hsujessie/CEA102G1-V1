<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="shortcut icon" href="<%=request.getContextPath()%>/resource/images/logos/seenema_W.ico" type="image/x-icon" />
    <title>Login</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <style type="text/css">
    	@import url(https://fonts.googleapis.com/css?family=Roboto:300);
        body {
            background: rgba(187,157,82, .3) !important;
        }     
		.login-page {
		  width: 360px;
		  padding: 8% 0 0 0;
		  margin: auto;
		}
		.form {
		  position: relative;
		  z-index: 1;
		  background: #FFFFFF;
		  max-width: 360px;
		  margin: 0 auto 100px;
		  padding: 45px;
		  text-align: center;
		  box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
		}
		.form input {
		  font-family: "Roboto", sans-serif;
		  outline: 0;
		  background: #f2f2f2;
		  width: 100%;
		  border: 0;
		  margin: 0 0 15px;
		  padding: 15px;
		  box-sizing: border-box;
		  font-size: 14px;
		}
		.form button {
		  font-family: "Roboto", sans-serif;
		  text-transform: uppercase;
		  outline: 0;
		  background: #bb9d52;
		  width: 100%;
		  border: 0;
		  padding: 13px;
		  color: #fff;
		  font-size: 14px;
		  -webkit-transition: all 0.3 ease;
		  transition: all 0.3 ease;
		  cursor: pointer;
		  border: 2px solid transparent;
		}
		.form button:hover,.form button:active,.form button:focus {
		  background: #fff;
		  border: 2px solid #bb9d52;
		  color: #bb9d52;
		}
		.form .register-form {
		  display: none;
		}
    </style>
</head>

<body>
    <div class="login-page">
        <div class="form">
          <form class="login-form" method="post" action="<%=request.getContextPath()%>/adm/adm.do">       
            <img style="width:130px;" src="<%=request.getContextPath()%>/resource/images/logos/logo_B.png" alt="image">&ensp;
            <h5 style="color:#bb9d52; margin-bottom: 10%; margin-top: 2%;">後台管理系統</h5>
            <c:if test="${not empty errorMsgs}">
				<c:forEach var="message" items="${errorMsgs}">
					<div style="color:red">${message}</div>
				</c:forEach>
			</c:if>
            <input type="text" name="admAccount" placeholder="account" required autofocus/>
            <input type="password" name="admPassword" placeholder="password" required/>
            <input type="hidden" name="action" value="login" />
            <button>Login</button>
          </form>
        </div>
    </div>
</body>

</html>