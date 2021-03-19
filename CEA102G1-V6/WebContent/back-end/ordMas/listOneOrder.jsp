<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.movie.model.*"%>
<%
MovVO movVO = (MovVO) request.getAttribute("movVO");
%>

<head>
	<title>Movies Management</title>
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
  .listOne-h3-pos,#a-color{
  	margin-left: 50%;
  }
  .listOne-h3-pos{
  	display: inline-block; 
  }
  #a-color{
  	font-size: 16px;
  }
</style>
</head>

<body class="sb-nav-fixed">
		<%@ include file="/back-end/files/sb_navbar.file"%> <!-- 引入navbar (上方) -->
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
            	<c:set value="movieSub" var="urlRecog"></c:set> <!-- 給sb_sidebar.file的參數-Sub -->
				<%@ include file="/back-end/files/sb_sidebar.file"%> <!-- 引入sidebar (左方) -->
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                    
                       <!-- listOneMovie Start -->
                       <jsp:useBean id="sesSvc" scope="page" class="com.session.model.SesService" />
					   <jsp:useBean id="movSvc" scope="page" class="com.movie.model.MovService" />
					   
                       <h3 class="h3-style listOne-h3-pos">訂單詳情</h3>
                       <h3>${ordMasVO.ordMasStatus == "0"?"未取票":ordMasVO.ordMasStatus == "1"?"已取票" :"已取消"}</h3>
					   <table>
							<tr>
								<th>電影</th>
								<td>${movSvc.getOneMov(sesSvc.getOneSes(ordMasVO.sesNo).movNo).movname}</td>
							</tr>
							<tr>
								<th>日期</th>
								<td>${sesSvc.getOneSes(ordMasVO.sesNo).sesDate}</td>
							</tr>
							<tr>
								<th>場次</th>
								<td>${sesSvc.getOneSes(ordMasVO.sesNo).sesTime}</td>
							</tr>
							<tr>
								<th>座位</th>
								<td></td>
							</tr>
							<tr>
								<th>商品</th>
								<td></td>
							</tr>
							<tr>
								<th>總價</th>
								<td>${ordMasVO.ordMasPrice}</td>
							</tr>
							
						</table>			
			   			<a id="a-color" href="<%=request.getContextPath()%>/back-end/movie/listAllMovie.jsp">回電影列表</a>
   
                       <!-- listOneMovie End -->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
</body>
</html>