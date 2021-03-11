<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.session.model.*"%>

<jsp:useBean id="listSessions_ByCompositeQuery" scope="request" type="java.util.List<SesVO>"/>
<jsp:useBean id="movSvc" scope="page" class="com.movie.model.MovService"/>
<html>
<head>
	<title>場次查詢</title>
	<%@ include file="/back-end/files/sb_head.file"%>
	
<style>
	.form-sty{
		margin: 20px 0 0 0;
	}
	.time-input-sty{
		font-size: 14px;
	    vertical-align: middle;
	}
</style>
</head>
<body class="sb-nav-fixed">
		<%@ include file="/back-end/files/sb_navbar.file"%> <!-- 引入navbar (上方) -->
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <c:set value="sessionSub" var="urlRecog"></c:set> <!-- 給sb_sidebar.file的參數-Sub -->
				<%@ include file="/back-end/files/sb_sidebar.file"%> <!-- 引入sidebar (左方) -->
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                    
                    	<h3 class="h3-style" style="display: inline-block;">場次查詢</h3>
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
                    	
                    	<!-- listAllSessions_ByCompositeQuery Start -->
			            <table class="table table-hover">
							<thead>
								<tr class="th-sty" style="border-bottom: 3px solid #bb9d52;">
									<th>列表編號</th>
									<th>電影</th>
									<th>場次日期</th>
									<th>場次時間</th>
									<th>廳院</th>
									<th>修改</th>
								</tr>				
							</thead>
									
							<tbody>
								<%@ include file="/back-end/session/pages/page1_ByCompositeQuery.file"%> 
								<c:forEach var="sesVO" varStatus="no" items="${listSessions_ByCompositeQuery}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
								<c:set value="${movSvc.getOneMov(sesVO.movNo)}" var="movObj"></c:set>
								<tr class="sty-height" valign='middle' ${(sesVO.sesNo==param.sesNo) ? 'style="background-color:#bb9d52; color:#fff;"':''}>
									<td>${no.index+1}</td>
									<td>${movObj.movname}</td>
									<td>${sesVO.getSesDate()}</td>
									<td>${sesVO.getSesTime()}</td>
									<td>${sesVO.getTheNo()}</td>
									<td>
									   <a class="btn btn-light btn-brd grd1 effect-1" onclick="updateData(this,${sesVO.sesNo})" >
										 <input type="button" value="修改" class="input-pos update-btn">
					        		   </a>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<%@ include file="/back-end/session/pages/page2_ByCompositeQuery.file" %>
                       <!-- listAllSessions_ByCompositeQuery End -->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
		
<script>				
	function updateData(e,sesNo){
		let href = "<%=request.getContextPath()%>/session/ses.do?action=getOne_For_Update&requestURL=<%=request.getServletPath()%>&sesNo="+sesNo;
		e.setAttribute("href", href);
	}
</script>
</body>
</html>