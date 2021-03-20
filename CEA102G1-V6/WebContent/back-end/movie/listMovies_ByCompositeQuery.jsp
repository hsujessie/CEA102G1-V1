<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.movie.model.*"%>

<jsp:useBean id="listMovies_ByCompositeQuery" scope="request" type="java.util.List<MovVO>"/>
<html>
<head>
	<title>電影查詢</title>
	<%@ include file="/back-end/files/sb_head.file"%>

<style>
	.th-adjust{
		width: 120px;
	}
	.form-sty{
		margin: 20px 0 0 12.5%;
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

                    	<!-- search Start -->
                    	<h3 class="h3-style" style="display: inline-block;">電影查詢</h3>
                    	<div class="row " style="margin: -60px 0 20px 0;">         
			                <div class="col-2"></div>
	                        <div class="col-10">          
		            			<jsp:useBean id="movSvcAll" scope="page" class="com.movie.model.MovService"/>                        
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
										<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy" var="thisYear" />
			                            <option value=""></option>
			                            <c:forEach var="year" begin="${thisYear-4}" end="${thisYear}">
			                                <option value="${year}">${year}年</option>
			                            </c:forEach>
			                        </select>
			                        <b>選擇月份</b>
			                        <select name="mov_ondate_month" style="margin-right: 1%;">
			                            <option value=""></option>
			                            <c:forEach var="month" begin="1" end="12">
			                                <option value="${month}">${month}月</option>
			                            </c:forEach>
			                        </select>
			                        <input type="hidden" name="action" value="listMovies_ByCompositeQuery">
				        			<a class="btn btn-light btn-brd grd1 effect-1 mr-botm">
										<input type="submit" value="搜尋" class="input-pos">
				        			</a>
		                    	</FORM>                    
                        	</div>                 
                        </div>
                    	<!-- search End -->
                    	                    
                    	<!-- listAllMovie_ByCompositeQuery Start -->
			            <table class="table table-hover">
							<thead>
								<tr class="th-sty" style="border-bottom: 3px solid #bb9d52;">
									<th>列表編號</th>
									<th class="th-adjust">名稱</th>
									<th>上映日期</th>
									<th>下檔日期</th>
									<th>片長</th>
									<th>類型</th>
									<th>查看</th>
									<th>修改</th>
								</tr>				
							</thead>
									
							<tbody>
								<%@ include file="/back-end/movie/pages/page1_ByCompositeQuery.file"%> 
								<c:forEach var="movVO" varStatus="no" items="${listMovies_ByCompositeQuery}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
								<tr class="sty-height" valign='middle' ${(movVO.movno==param.movno) ? 'style="background-color:#bb9d52; color:#fff;"':''}>
									<td>${no.index+1}</td>
									<td>${movVO.getMovname()}</td>
									<td>${movVO.getMovondate()}</td>
									<td>${movVO.getMovoffdate()}</td>
									<td>${movVO.getMovdurat()}分鐘</td>
									<td>${movVO.getMovtype()}</td>
									<td>
					        			 <a id="listOne" onclick="getData(this,${movVO.movno})" class="btn btn-light btn-brd grd1 effect-1">
											<input type="button" value="查看" class="input-pos">
					        			 </a>	
									</td>
									<td>
										<a class="btn btn-light btn-brd grd1 effect-1" onclick="updateData(this,${movVO.movno})" >
											<input type="submit" value="修改" class="input-pos">
					        			 </a>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<%@ include file="/back-end/movie/pages/page2_ByCompositeQuery.file" %>
                       <!-- listAllMovie_ByCompositeQuery End -->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
		
<script>				
	function getData(e,movno){
		let href = "<%=request.getContextPath()%>/movie/mov.do?action=getOne_For_Display&requestURL=<%=request.getServletPath()%>&movno="+movno;
		e.setAttribute("href", href);
	}		
	function updateData(e,movno){
		let href = "<%=request.getContextPath()%>/movie/mov.do?action=getOne_For_Update&requestURL=<%=request.getServletPath()%>&movno="+movno;
		e.setAttribute("href", href);
	}
</script>
</body>
</html>