<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/datetimepicker/jquery.datetimepicker.css" />
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
	    width: 150px;
	}
	.fail-span{
	    color: #A50203;
		position: absolute;
	    top: 8%;
	    left: 17%;
	    font-size: 16px;
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
						<!-- success message Start -->
						<c:if test="${addSuccess != null}">
							<span class="success-span"> 
								${addSuccess}
								<i class="far fa-smile-wink"></i>
							</span>
						</c:if>
						<c:if test="${updateSuccess != null }">
							<span class="success-span"> 
								${updateSuccess}
								<i class="far fa-smile-wink"></i>
							</span>
						</c:if>
                    	<!-- success message End -->
                    	<!-- failure message Start -->
						<c:if test="${errMsg != null}">
							<span class="fail-span"> 
								<i class="far fa-frown"></i>
								${errMsg}
							</span>
						</c:if>
                    	<!-- failure message End -->
						
                    	<!-- search Start -->
                    	<div class="row " style="margin: -60px 0 20px 50px;">          
			                <div class="col-2"></div>
	                        <div class="col-10">          
		            			<jsp:useBean id="movSvcAll" scope="page" class="com.movie.model.MovService"/>                        
	                           	<FORM class="form-sty" METHOD="post" ACTION="<%=request.getContextPath()%>/session/ses.do">				                        
			                        <b>電影名稱</b>
			                            <select name="movNo" style="width: 185px;">
			                                <option value=""></option>
			                                <c:forEach var="movVO" items="${movSvcAll.all}" >
			                                    <option value="${movVO.movno}">${movVO.movname}
			                                </c:forEach>
			                            </select>&ensp;&ensp;
			                        <b>場次日期</b>
			                        <input class="sty-input time-input-sty" name="sesDateBegin" id="sesdate_Begin" type="text" value=""> 
			                        ~ <input class="sty-input time-input-sty" name="sesDateEnd" id="sesdate_End" type="text" value="">
			                        
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
										<td><fmt:formatDate value="${sesVO.getSesTime()}" pattern="HH:mm" type="DATE"/></td>
										
										<jsp:useBean id="theSvc" scope="page" class="com.theater.model.TheService"/>
										<jsp:useBean id="movVerSvc" scope="page" class="com.movie_version.model.MovVerService"/>	
										<c:set value="${theSvc.getOneTheater(sesVO.theNo)}" var="theObj"></c:set>
										<c:set value="${movVerSvc.getOneMovie_version(theObj.movver_no)}" var="movVerObj"></c:set>
										<td>${sesVO.theNo}廳 【${movVerObj.movver_name}】</td>
										
										<td>
						        			 <fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy-MM-dd" var="today" />
						        			 <fmt:formatDate value="${movObj.movondate}" pattern="yyyy-MM-dd" var="movOndate" />
						        			 <fmt:formatDate value="${movObj.movoffdate}" pattern="yyyy-MM-dd" var="movOffdate" />
										     <c:if test="${today le movOndate}">
												 <a class="btn btn-light btn-brd grd1 effect-1" onclick="updateData(this,${sesVO.sesNo})" >
													<input type="submit" value="修改" class="input-pos"> 
							        			 </a>
										     </c:if>
											 <c:if test="${today ge movOndate and today lt movOffdate}">
												    已上映
											  </c:if>
											  <c:if test="${movOffdate le today}">
													 已下檔
											  </c:if>
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
		<script src="<%=request.getContextPath()%>/resource/datetimepicker/jquery.js"></script>
		<script src="<%=request.getContextPath()%>/resource/datetimepicker/jquery.datetimepicker.full.js"></script>		
<script>
	$.datetimepicker.setLocale('zh');
	$(function(){
		 $('#sesdate_Begin').datetimepicker({
		  theme:'dark',
		  format:'Y-m-d',
		  onShow:function(){
		   this.setOptions({
		    maxDate:$('#sesdate_End').val()?$('#sesdate_End').val():false
		   })
		  },
		  timepicker:false
		 });
	
		 $('#sesdate_End').datetimepicker({
		  theme:'dark',
		  format:'Y-m-d',
		  onShow:function(){
		   this.setOptions({
		    minDate:$('#sesdate_Begin').val()?$('#sesdate_Begin').val():false
		   })
		  },
		  timepicker:false
		 });
	});
	
	function updateData(e,sesNo){
		let href = "<%=request.getContextPath()%>/session/ses.do?action=getOne_For_Update&requestURL=<%=request.getServletPath()%>&whichPage=<%=whichPage%>&sesNo="+sesNo;
		e.setAttribute("href", href);
	}
</script>
</body>
</html>