<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.movie.model.*"%>

<html>
<head>
	<title>電影新增</title>	
	<%@ include file="/back-end/files/sb_head.file"%>

<style>
  table {
	width: 750px;
	margin: 5px auto 5px auto;
    background-color: rgb(255,255,255);
    border-radius: 10px;
	-webkit-box-shadow: 0px 3px 5px rgb(8,8,8, 0.3);
	-moz-box-shadow: 0px 3px 5px rgb(8,8,8, 0.3);
	box-shadow: 0px 3px 5px rgb(8,8,8, 0.3);
  }
  th,td{
  	box-sizing:border-box;
    border-radius: 10px;
  }
  th{
  	width: 200px;
  	padding: 10px 0px 10px 70px;
  }
  td{
  	width: 250px;
  	padding: 10px 20px 10px 30px;
    border-bottom: 2px dotted #bb9d52;
  }
  .listOne-h3-pos{
  	display: inline-block;	
  	margin-left: 45%;
  }
  .ml-ten{
  	margin-left: 10px;
  }
</style>
</head>
<body class="sb-nav-fixed">
		<%@ include file="/back-end/files/sb_navbar.file"%> <!-- 引入navbar (上方) -->
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
            	<c:set value="movieAdd" var="urlRecog"></c:set> <!-- 給sb_sidebar.file的參數-Add -->
				<%@ include file="/back-end/files/sb_sidebar.file"%> <!-- 引入sidebar (左方) -->
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                    
                       <!-- addMovie Start -->  
                       <c:if test="${not empty errorMsgs}">
							<font style="color:red">請修正以下錯誤:</font>
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
										<li style="color:red">${message}</li>
									</c:forEach>
								</ul>
					   </c:if>
                       
						<FORM method="post" action="<%=request.getContextPath()%>/adm/adm.do" enctype="multipart/form-data">
						<h3 class="h3-style listOne-h3-pos">員工新增</h3>
						<table>
							<tr>
								<th>姓名</th>
								<td><input class="sty-input mr-left mr-btm-normal" type="text" name="admName" value="${addAdmVO.admName}" /></td>		
							</tr>
							<tr>
								<th>照片</th>
								<td>
									<input type="file" name="admImg">
								</td>
								
							</tr>
							<tr>
								<th>帳號</th>
								<td>
									<input class="sty-input mr-left mr-btm-normal" type="text" name="admAccount" value="${addAdmVO.admAccount}" />
								</td>
							</tr>
							
							<tr>
								<th>信箱</th>
								<td><input class="sty-input mr-left mr-btm-normal" name="admMail" type="text" value="${addAdmVO.admMail}"></td>
							</tr>
							
							<tr>
								<th>設定權限</th>
								<td>
									<jsp:useBean id="funSvc" scope="page" class="com.func.model.FunService"></jsp:useBean>
									<div class="row">
										<c:forEach var="funVO" items="${funSvc.all}">
											<div class="col-4"><input type="checkbox" name="funNo" value="${funVO.funNo}">${funVO.funName}</div>
										</c:forEach>
									</div>
								</td>
							</tr>
						</table>
						<br>
						<input type="hidden" name="action" value="insert">
						<a class="btn btn-light btn-brd grd1 effect-1 btn-pos" style="margin: 1% 0 1% 50%;" >
							<input type="submit" value="送出" class="input-pos">
						</a>
						</FORM>
                       <!-- addMovie End -->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
</body>



</html>