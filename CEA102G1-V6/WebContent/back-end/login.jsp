<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>登入</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <style type="text/css">
        @import "bourbon";

        body {
            background: #eee !important;
        }

        .wrapper {
            margin-top: 80px;
            margin-bottom: 80px;
        }

        .form-signin {
            max-width: 380px;
            padding: 15px 35px 45px;
            margin: 0 auto;
            background-color: #fff;
            border: 1px solid rgba(0, 0, 0, 0.1);
        }
        .form-signin-heading, .checkbox {
            margin-bottom: 30px;
        }

        .checkbox {
            font-weight: normal;
        }

        .form-control {
            position: relative;
            font-size: 16px;
            height: auto;
            padding: 10px;

        }

        input[type="text"] {
            margin-bottom: -1px;
            border-bottom-left-radius: 0;
            border-bottom-right-radius: 0;
        }

        input[type="password"] {
            margin-bottom: 20px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }
    </style>
</head>

<body>
    <div class="wrapper">
        <form class="form-signin" method="post" action="<%=request.getContextPath()%>/adm/adm.do">
            <h2 class="form-signin-heading">請登入</h2>
            <c:if test="${not empty errorMsgs}">
				<c:forEach var="message" items="${errorMsgs}">
					<div style="color:red">${message}</div>
				</c:forEach>
			</c:if>
			
            <div>帳號:</div>
            <input type="text" class="form-control" name="admAccount" placeholder="account" required autofocus />
            <div>密碼:</div>
            <input type="password" class="form-control" name="admPassword" placeholder="password" required />
            
            <input type="hidden" name="action" value="login" />
            <button class="btn btn-lg btn-primary btn-block" type="submit">登入</button>
        </form>
    </div>
</body>

</html>