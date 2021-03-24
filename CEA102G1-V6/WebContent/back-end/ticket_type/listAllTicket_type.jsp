<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ticket_type.model.*"%>


<%
	TicTypService ticket_typeSvc = new TicTypService();
	List<TicTypVO> list = ticket_typeSvc.getAll();
	pageContext.setAttribute("list",list);
%>
<jsp:useBean id="identitySvc" scope="page" class="com.identity.model.IdeService" />
<jsp:useBean id="movie_versionSvc" scope="page" class="com.movie_version.model.MovVerService" />
<!DOCTYPE html>
<html>
<head>
<title>票種資料</title>
<%@ include file="/back-end/files/sb_head.file"%>
<style>
	.success-span{
	    color: #bb9d52;
		position: absolute;
	    top: 10%;
	    left: 19%;
	    font-size: 16px;
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
				<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set> <!-- 給sb_sidebar.file的參數-Home -->
				<%@ include file="/back-end/files/sb_sidebar.file"%> <!-- 引入sidebar (左方) -->
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                    	
                    	<h3 class="h3-style" style="display: inline-block;">票券列表</h3>

			            <table class="table table-hover">
							<thead>
								<tr style="border-bottom: 3px solid #bb9d52;">
									<th>票種編號</th>
									<th>放映種類</th>
									<th>身分</th>
									<th>票價</th>
									<th>修改</th>
								</tr>				
							</thead>
							<tbody>
	<c:forEach var="ticket_typeVO" items="${list}" >
		<tr class="sty-height" valign='middle' ${(ticket_typeVO.tictyp_no==param.tictyp_no) ? 'bgcolor=#bb9d52':''}><!--將修改的那一筆加入對比色而已-->
			<td>${ticket_typeVO.tictyp_no}</td>
			<td><c:forEach var="movie_versionVO" items="${movie_versionSvc.all}">
                    <c:if test="${ticket_typeVO.movver_no==movie_versionVO.movver_no}">
	                    ${movie_versionVO.movver_name}
                    </c:if>
                </c:forEach>
			</td>
			<td><c:forEach var="identityVO" items="${identitySvc.all}">
                    <c:if test="${ticket_typeVO.ide_no==identityVO.ide_no}">
	                    ${identityVO.ide_name}
                    </c:if>
                </c:forEach>
			</td>			
			<td>${ticket_typeVO.tictyp_price}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/ticket_type/ticket_type.do" style="margin-bottom: 0px;">
			     										<a class="btn btn-light btn-brd grd1 effect-1" onclick="updateData(this,${movVO.movno})" >
											<input type="submit" value="修改" class="input-pos">
					        			 </a>
			     <input type="hidden" name="tictyp_no"     value="${ticket_typeVO.tictyp_no}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			     <input type="hidden" name="action"	    value="getOne_For_Update"></FORM>
			</td>

		</tr>
	</c:forEach>
</table>
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
</body>
</html>