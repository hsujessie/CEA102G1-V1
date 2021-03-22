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
                       <h3 class="h3-style listOne-h3-pos">電影詳情</h3>
					   <table>
							<tr>
								<th>名稱</th>
								<td><%=movVO.getMovname()%></td>
							</tr>
							<tr>
								<th>種類</th>
								<td><%=movVO.getMovver()%></td>
							</tr>
							<tr>
								<th>類型</th>
								<td><%=movVO.getMovtype()%></td>
							</tr>
							<tr>
								<th>語言</th>
								<td><%=movVO.getMovlan()%></td>
							</tr>
							<tr>
								<th>上映日期</th>
								<td><%=movVO.getMovondate()%></td>
							</tr>
							<tr>
								<th>下檔日期</th>
								<td><%=movVO.getMovoffdate()%></td>
							</tr>
							<tr>
								<th>片長</th>
								<td><%=movVO.getMovdurat()%>分鐘</td>
							</tr>
							<tr>
								<th>級數</th>
								<td><%=movVO.getMovrating()%></td>
							</tr>
							<tr>
								<th>導演</th>
								<td><%=movVO.getMovditor()%></td>
							</tr>
							<tr>
								<th>演員</th>
								<td><%=movVO.getMovcast()%></td>
							</tr>
							<tr>
								<th>簡介</th>
								<td><%=movVO.getMovdes()%></td>
							</tr>
							<tr>
								<th>海報</th>
								<td>				
									<c:if test="${not empty movVO.movpos}">
										<img width="150px" src="<%=request.getContextPath()%>/movie/mov.do?movno=${movVO.movno}&action=get_One_MovPos">
									</c:if>
								</td>
							</tr>
							<tr>
								<th>預告片</th>
								<td>	
									<c:if test="${not empty movVO.movtra}">		
										<video controls width="300px"><source src="<%=request.getContextPath()%>/movie/mov.do?movno=${movVO.movno}&action=get_One_MovTra" type="video/mp4"></video>
									</c:if>
								</td>
							</tr>
							<tr>
								<th>滿意度總分</th>
								<td>${satSum}</td>
							</tr>
							<tr>
								<th>滿意度評價人數</th>
								<td>${satPeo}</td>
							</tr>
							<tr>
								<th>期待度總分</th>
								<td>${expSum}</td>
							</tr>
							<tr>
								<th>期待度評價人數</th>
								<td>${expPeo}</td>
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