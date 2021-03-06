<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.theater.model.*"%>


<%
	TheService theSvc = new TheService();
	List<TheVO> list = theSvc.getAll();
	pageContext.setAttribute("list",list);
%>
<jsp:useBean id="movie_versionSvc" scope="page" class="com.movie_version.model.MovVerService" />
<html>
<head>
	<title>Theater</title>
	<%@ include file="/back-end/files/sb_head.file"%>
	<style>
		.success-span{
	    color: #bb9d52;
		position: absolute;
	    top: 10%;
	    left: 19%;
	    font-size: 16px;
	}
	.th-adjust{
		width: 120px;
	}
	.form-sty{
		margin: 20px 0 0 30px;
	}
	.mr-botm{
		margin-bottom: 5px;
	}
</style>
</head>
<body class="sb-nav-fixed">
		<%@ include file="/back-end/files/sb_navbar.file"%> <!-- 引入navbar (上方) -->
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">            
            	<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set> <!-- 在listAllXXX.jsp，加上這行，給sb_sidebar.file的參數-Home -->
				<%@ include file="/back-end/files/sb_sidebar.file"%> <!-- 引入sidebar (左方) -->
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                    
                    <!-- PUT HERE Start-->
                   	<h3 class="h3-style" style="display: inline-block;">廳院列表</h3>

			            <table class="table table-hover">
							<thead>
								<tr style="border-bottom: 3px solid #bb9d52;">
									<th>廳院編號</th>
									<th>影廳種類</th>
									<th>查看</th>
									<th>修改</th>		
								</tr>
							</thead>
							<tbody>
								<c:forEach var="theaterVO" items="${list}" >
									<tr ${(theaterVO.the_no==param.the_no) ? 'bgcolor=#bb9d52':''}><!--將修改的那一筆加入對比色而已-->
										<td>${theaterVO.the_no}</td>
										<td><c:forEach var="movie_versionVO" items="${movie_versionSvc.all}">
							                    <c:if test="${theaterVO.movver_no==movie_versionVO.movver_no}">
								                    ${movie_versionVO.movver_name}
							                    </c:if>
							                </c:forEach>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/theater/theater.do" style="margin-bottom: 0px;">
			    <a class="btn btn-light btn-brd grd1 effect-1 btn-pos">
				<input type="submit" value="查看" class="input-pos">
				</a>
			     <input type="hidden" name="the_no"     value="${theaterVO.the_no}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			     <input type="hidden" name="action"	    value="getOne_For_Display"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/theater/theater.do" style="margin-bottom: 0px;">
			    <a class="btn btn-light btn-brd grd1 effect-1 btn-pos">
				<input type="submit" value="修改" class="input-pos">
				</a>
			     <input type="hidden" name="the_no"     value="${theaterVO.the_no}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			     <input type="hidden" name="action"	    value="getOne_For_Update"></FORM>
			</td>

		</tr>
	</c:forEach>
	</tbody>
</table>
                    <!-- PUT HERE End-->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
    </body>
</html>