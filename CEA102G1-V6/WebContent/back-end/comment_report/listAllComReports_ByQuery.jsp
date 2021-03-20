<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.comment_report.model.*"%>

<jsp:useBean id="listComReps_ByQuery" scope="request" type="java.util.List<ComRepVO>"/>

<html>
<head>
	<title>Comment Reports Management</title>
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
                                      	
                    	<h3 class="h3-style" style="display: inline-block;">短評檢舉列表</h3>
						<!-- success message Start -->
						<c:if test="${updateSuccess != null }">
							<span class="success-span">  
								${updateSuccess}
								<i class="far fa-laugh-wink"></i>
							</span>
						</c:if>
                    	<!-- success message End -->
						
						<!-- listComReports_ByQuery Start -->
                    	<div class="row " style="margin: -50px 0 0 0;">         
			                <div class="col-8"></div>
	                        <div class="col-4">                                 
	                           	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/comment_report/comrep.do">	
			                        <b>檢舉狀態</b>
		                            <select name="comRepStatus" style="margin-right: 2.5%;">
	                                	<option value="">全部</option>
	                                    <option value="0" <c:if test="${param.comRepStatus == 0}">selected</c:if> >未處理 
	                                    <option value="1" <c:if test="${param.comRepStatus == 1 }">selected</c:if> >檢舉成功 
	                                    <option value="2" <c:if test="${param.comRepStatus == 2}">selected</c:if> >檢舉失敗
		                            </select>
			                        <input type="hidden" name="action" value="searchComRepStatus">
				        			<a class="btn btn-light btn-brd grd1 effect-1">
										<input type="submit" value="搜尋" class="input-pos">
				        			</a>
		                    	</FORM>                    
                        	</div>                 
                        </div>
			            <table class="table table-hover">
							<thead>
								<tr style="border-bottom: 3px solid #bb9d52;">								
									<th>列表編號</th>		
									<th>短評編號</th>
									<th>檢舉原因</th>						
									<th>檢舉人帳號</th>
									<th>檢舉時間</th>
									<th>檢舉狀態</th>
									<th>審核</th>						
								</tr>				
							</thead>	
							<tbody>
								<jsp:useBean id="comSvc" scope="page" class="com.comment.model.ComService"/>	
								<%@ include file="/back-end/comment_report/pages/page1_ByQuery.file"%> 
									<c:forEach var="comRepVO" items="${listComReps_ByQuery}" varStatus="no" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
									<tr class="sty-height" valign='middle' ${(comRepVO.comRepNo==param.comRepNo) ? 'style="background-color:#bb9d52; color:#fff;"':''}>																				
										<td>${no.index+1}</td>
										<td>${comRepVO.comNo}</td>
										<c:if test="${comRepVO.comRepReason eq 1}"><td>與本電影無關、捏造假冒、不實敘述</td></c:if>	
										<c:if test="${comRepVO.comRepReason eq 2}"><td>具有廣告性質或大量重複散布</td></c:if>	
										<c:if test="${comRepVO.comRepReason eq 3}"><td>相互惡意攻訐、猥褻騷擾、人身攻擊</td></c:if>	
										<c:if test="${comRepVO.comRepReason eq 4}"><td>侵犯隱私權、違反智慧財產權、涉及違法情事</td></c:if>	
										<c:if test="${comRepVO.comRepReason eq 5}"><td>違背善良風俗</td></c:if>										
										<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService"/>
										<c:set value="${memSvc.getOneMember(comRepVO.memNo)}" var="memObj"></c:set>
										<td>${memObj.memAccount}</td>
										<td><fmt:formatDate value="${comRepVO.getComRepTime()}" pattern="yyy-MM-dd" type="DATE"/><br>
											<fmt:formatDate value="${comRepVO.getComRepTime()}" pattern="HH:mm:ss" type="DATE"/>
										</td>	
										<c:if test="${comRepVO.comRepStatus eq 0}"><td>未處理</td></c:if>
										<c:if test="${comRepVO.comRepStatus eq 1}"><td>檢舉成功 </td></c:if>
										<c:if test="${comRepVO.comRepStatus eq 2}"><td>檢舉失敗</td></c:if>
										<td>
										<c:if test="${comRepVO.comRepStatus eq 0}">
											<a class="btn btn-light btn-brd grd1 effect-1" onclick="updateData(this,${comRepVO.comRepNo},${comRepVO.comNo})" >											
												<input type="button" value="審核" class="input-pos">											
						        			</a>										
										</c:if>
										<c:if test="${comRepVO.comRepStatus ne 0}">
											已審核		
										</c:if>
										</td>
									 </tr>
									</c:forEach>	
							</tbody>
						</table>
			    		<%@ include file="/back-end/comment_report/pages/page2_ByQuery.file" %>
                       <!-- listComReports_ByQuery End -->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
		
<script>
	function updateData(e,comRepNo,comNo){
		let href = "<%=request.getContextPath()%>/comment_report/comrep.do?action=getOne_For_Update&requestURL=<%=request.getServletPath()%>&whichPage=<%=whichPage%>&comRepNo="+comRepNo+"&comNo="+comNo;
		e.setAttribute("href", href);
	}
</script>
</body>
</html>