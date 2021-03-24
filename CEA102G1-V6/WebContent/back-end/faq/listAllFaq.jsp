<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.faq.model.*"%>

<%
	FaqService faqSvc = new FaqService();
	List<FaqVO> list = faqSvc.getAll();
	pageContext.setAttribute("list",list);
%>
<html>
<head>
	<title>FAQs</title>
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
				<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set> <!-- 給sb_sidebar.file的參數-Home -->
				<%@ include file="/back-end/files/sb_sidebar.file"%> <!-- 引入sidebar (左方) -->
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">

                    	<h3 class="h3-style" style="display: inline-block;">FAQ列表</h3>
			            <table class="table table-hover">
							<thead>
								<tr style="border-bottom: 3px solid #bb9d52;">
									<th>編號</th>
									<th>種類</th>
									<th>問題</th>
									<th>回答</th>
									<th>查看</th>
									<th>修改</th>
								</tr>				
							</thead>

							<tbody>
								<c:forEach var="faqVO" items="${list}">					
								<tr class="sty-height" valign='middle' ${(faqVO.faq_no==param.faq_no) ? 'style="background-color:#bb9d52; color:#fff;"':''}>
									<td>${faqVO.faq_no}</td>
									<jsp:useBean id="faq_typeSvc" scope="page" class="com.faq_type.model.FaqtypService" />
									<td><c:forEach var="faq_typeVO" items="${faq_typeSvc.all}">
							            <c:if test="${faqVO.faqtyp_no==faq_typeVO.faqtyp_no}">
								        	${faq_typeVO.faqtyp_name}
							            </c:if></c:forEach>
									<td>${faqVO.faq_question}</td>
									<td>${faqVO.faq_answer}</td>
									<td>
									  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/faq/faq.do" style="margin-bottom: 0px;">
					        			 <a id="listOne"  class="btn btn-light btn-brd grd1 effect-1">
											<input type="submit" value="查看" class="input-pos">
					        			 </a>
									     <input type="hidden" name="faq_no"     value="${faqVO.faq_no}">
									     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
									     <input type="hidden" name="action"	    value="getOne_For_Display"></FORM>
									</td>
									<td>
									  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/faq/faq.do" style="margin-bottom: 0px;">
									    <a class="btn btn-light btn-brd grd1 effect-1 btn-pos">
										<input type="submit" value="修改" class="input-pos">
										</a>
									     <input type="hidden" name="faq_no"     value="${faqVO.faq_no}">
									     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
									     <input type="hidden" name="action"	    value="getOne_For_Update"></FORM>
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