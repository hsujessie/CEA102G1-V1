<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.session.model.*"%>

<%    
    SesService sesSvc = new SesService();
    List<SesVO> list = sesSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<html>
<head>
	<title>Sessions Management</title>
	<%@ include file="/back-end/files/sb_head.file"%>
</head>
<style>
	.success-span{
	    color: #bb9d52;
		position: absolute;
	    top: 8%;
	    left: 17%;
	    font-size: 16px;
	}
	.form-sty{
		margin: 20px 0 0 0;
	}
	.time-input-sty{
		font-size: 14px;
	    vertical-align: middle;
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
                    
                    	<h3 class="h3-style" style="display: inline-block;">場次列表</h3>
						<!-- error message Start -->
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
                    	<div class="row " style="margin: -60px 0 20px 50px;">          
			                <div class="col-2"></div>
	                        <div class="col-10">          
		            			<jsp:useBean id="movSvcAll" scope="page" class="com.movie.model.MovService"/>                        
	                           	<FORM class="form-sty" METHOD="post" ACTION="<%=request.getContextPath()%>/session/ses.do">				                        
			                        <b>電影名稱</b>
			                            <select name="movNo" style="width: 80px;">
			                                <option value=""></option>
			                                <c:forEach var="movVO" items="${movSvcAll.all}" >
			                                    <option value="${movVO.movno}">${movVO.movname}
			                                </c:forEach>
			                            </select>&ensp;&ensp;
			                        <b>場次日期</b>
			                        <input class="sty-input time-input-sty" name="sesDateBegin" id="" type="date" value=""> 
			                        ~ <input class="sty-input time-input-sty" name="sesDateEnd" id="" type="date" value="">
			                        
			                        <input type="hidden" name="action" value="listSessions_ByCompositeQuery">
				        			<a class="btn btn-light btn-brd grd1 effect-1">
										<input type="submit" value="搜尋" class="input-pos">
				        			</a>
		                    	</FORM>                    
                        	</div>                 
                        </div>
                    	<!-- search End -->
                        
                    	<!-- listSession Start -->
			            <table class="table table-hover">
							<thead>
								<tr style="border-bottom: 3px solid #bb9d52;">
									<th>列表編號</th>
									<th>電影</th>
									<th>場次日期</th>
									<th>場次時間</th>
									<th>廳院</th>
									<th>修改</th>
								</tr>				
							</thead>
									
							<tbody>
								<jsp:useBean id="movSvc" scope="page" class="com.movie.model.MovService"/>	
								<%@ include file="/back-end/movie/pages/page1.file" %> 
									<c:forEach var="sesVO" items="${list}" varStatus="no" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
									<c:set value="${movSvc.getOneMov(sesVO.movNo)}" var="movObj"></c:set>
									<tr class="sty-height" valign='middle' ${(sesVO.sesNo==param.sesNo) ? 'style="background-color:#bb9d52; color:#fff;"':''}>
										<td>${no.index+1}</td>
										<td>${movObj.movname}</td>
										<td>${sesVO.getSesDate()}</td>
										<td>${sesVO.getSesTime()}</td>
										<td>${sesVO.getTheNo()}</td>
										<td>
											<a class="btn btn-light btn-brd grd1 effect-1" onclick="updateData(this,${sesVO.sesNo})" >
												<input type="submit" value="修改" class="input-pos">
						        			 </a>
										</td>
									</tr>
									</c:forEach>
							</tbody>
						</table>
			    		<%@ include file="/back-end/movie/pages/page2.file" %>
                       <!-- listSession End -->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
		
<script>
	function updateData(e,sesNo){
		let href = "<%=request.getContextPath()%>/session/ses.do?action=getOne_For_Update&requestURL=<%=request.getServletPath()%>&whichPage=<%=whichPage%>&sesNo="+sesNo;
		e.setAttribute("href", href);
	}
</script>
</body>
</html>