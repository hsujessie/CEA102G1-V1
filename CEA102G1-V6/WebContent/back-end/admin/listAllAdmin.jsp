<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.movie.model.*"%>

<html>
<head>
	<title>Movies Management</title>
	<%@ include file="/back-end/files/sb_head.file"%>

<style>
	.success-span{
	    color: #bb9d52;
		position: absolute;
	    top: 10%;
	    left: 17%;
	}
	.th-adjust{
		width: 120px;
	}
	.form-sty{
		margin: 20px 0 0 10px;
	}
	img {
		width: 50px;
		height: 50px;
	}
</style>
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
                    	
                    	<!-- error message Start -->
                    	<h3 class="h3-style" style="display: inline-block;">電影列表</h3>
						<c:if test="${addSuccess != null}">
							<span class="success-span"> 
								${addSuccess}
								<i class="fa fa-hand-peace-o"></i>
							</span>
						</c:if>
						<c:if test="${updateSuccess != null }">
							<span class="success-span"> 
								${updateSuccess}
								<i class="far fa-laugh-wink"></i>
							</span>
						</c:if>
                    	<!-- error message End -->
						
                    	<!-- search Start -->
                    	<div class="row " style="margin: -60px 0 20px 0;">         
			                <div class="col-2"></div>
	                        <div class="col-10">          
		            			<jsp:useBean id="admSvc" scope="page" class="com.admin.model.AdmService"/>                        
	                           	<FORM class="form-sty" METHOD="post" ACTION="<%=request.getContextPath()%>/movie/mov.do">				                        
			                        <b>電影名稱</b>
			                            <select name="mov_no" style="width: 80px;">
			                                <option value=""></option>
			                                <c:forEach var="movVO" items="${movSvcAll.all}" >
			                                    <option value="${movVO.movno}">${movVO.movname}
			                                </c:forEach>
			                            </select>
			                        <b>電影類型</b>
			                            <select name="mov_type">
			                                <option value=""></option>
											<option value="劇情片">劇情片</option>
											<option value="動作片">動作片</option>
											<option value="動畫片">動畫片</option>
											<option value="喜劇片">喜劇片</option>
											<option value="愛情片">愛情片</option>
											<option value="科幻片">科幻片</option>
											<option value="恐怖片">恐怖片</option>
			                            </select>
			                        <b>選擇年份</b>
			                        <select name="mov_ondate_year">
			                            <option value=""></option>
			                            <c:forEach var="year" begin="1970" end="<%= (int) (java.util.Calendar.getInstance().get(java.util.Calendar.YEAR))+1%>">
			                                <option value="${year}">${year}年</option>
			                            </c:forEach>
			                        </select>
			                        <b>選擇月份</b>
			                        <select name="mov_ondate_month">
			                            <option value=""></option>
			                            <c:forEach var="month" begin="1" end="12">
			                                <option value="${month}">${month}月</option>
			                            </c:forEach>
			                        </select>
			                        <input type="hidden" name="action" value="listMovies_ByCompositeQuery">
				        			<a class="btn btn-light btn-brd grd1 effect-1">
										<input type="submit" value="搜尋" class="input-pos">
				        			</a>
		                    	</FORM>                    
                        	</div>                 
                        </div>
                    	<!-- search End -->
                    	
                    	<!-- listAllMovie Start -->
			            <table class="table table-hover">
							<thead>
								<tr style="border-bottom: 3px solid #bb9d52;">
									<th>列表編號</th>
									<th class="th-adjust">姓名</th>
									<th>照片</th>
									<th>帳號</th>
									<th>密碼</th>
									<th>信箱</th>
									<th>在職狀態</th>
								</tr>				
							</thead>
									
							<tbody>
								<c:forEach var="admVO" items="${admSvc.all}" varStatus="no">					
								<tr class="sty-height" valign='middle' ${(admVO.admNo==param.admNo) ? 'style="background-color:#bb9d52; color:#fff;"':''}>
									<td>${no.index+1}</td>
									<td>${admVO.admName}</td>
									<td><img src="<%=request.getContextPath()%>/util/imgReader${admVO.admImgParam}"></td>
									<td>${admVO.admAccount}</td>
									<td>${admVO.admPassword}</td>
									<td>${admVO.admMail}</td>
									<td>${admVO.admStatus=="0" ?"在職中":"已離職"}</td>
									<td>
										
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
                       <!-- listAllMovie End -->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
		

</body>
</html>