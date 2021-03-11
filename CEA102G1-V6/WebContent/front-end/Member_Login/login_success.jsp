<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.member.model.*"%>

<%
	MemberVO memberVO = (MemberVO) request.getAttribute("MemberVO");
%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.80.0">
    <title>Login_Success</title>

<link rel="canonical" href="https://getbootstrap.com/docs/4.6/examples/starter-template/">
    <!-- Bootstrap 的 CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/bootstrap/css/bootstrap.min.css">
 <!-- Custom styles for this template -->
    <link href="<%=request.getContextPath()%>/resource/bootstrap/css/starter-template.css" rel="stylesheet">


    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>

    
   
  </head>
  <body>
<!-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) -->
<script src="<%=request.getContextPath()%>/resourceresources/jquery/jquery-3.5.1.min.js"></script>
<script src="<%=request.getContextPath()%>/resourceresources/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/resourceresources/bootstrap/js/bootstrap.min.js"></script>  
    
<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
  <a class="navbar-brand" href="#">Navbar</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarsExampleDefault">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/front-end/login.jsp">LOGIN PAGE</a>
      </li>
      <li class="nav-item-out">      
       <a class="nav-link " href="<%=request.getContextPath()%>/front-end/logout_page.jsp" >LOGOUT </a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Dropdown</a>
        <div class="dropdown-menu" aria-labelledby="dropdown01">
          <a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/mem_update.jsp">會員資料修改</a>
          <a class="dropdown-item" href="#">Another action</a>
          <a class="dropdown-item" href="#">Something else here</a>
        </div>
      </li>
    </ul>
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
      <button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
    </form>
  </div>
</nav>

<main role="main" class="container">

  <div class="starter-template">
    <h1>登入成功的頁面</h1>
    <p class="lead">歡迎:<font color=blue><%= memberVO.getMemName()%></font>您好<br>

    <br>
    
    </p>
    
  </div>

</main><!-- /.container -->


   

      
  </body>
</html>
