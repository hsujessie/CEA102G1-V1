<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.faq.model.*"%>

<html>
<head>
	<title>FAQ修改</title>
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
  .listOne-h3-pos{
  	display: inline-block;	
  	margin-left: 45%;
  }
  .ml-ten{
  	margin-left: 10px;
  }
  .err-color{
    text-shadow: 0 0 0.1em #f87, 0 0 0.1em #f87;
    font-size: 14px;
  }
  #faq_question {
  width:100%;
  height:50px;
  }
  #faq_answer {
  width:100%;
  height:300px;
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

                       <!-- update movie Start -->  
					   <FORM method="post" action="<%=request.getContextPath()%>/faq/faq.do" name="form_updateFAQ">	                 	
                       <h3 class="h3-style listOne-h3-pos">FAQ修改</h3>

			            <table>
							<jsp:useBean id="faq_typeSvc" scope="page" class="com.faq_type.model.FaqtypService" />
						<tr>
							<th>種類</th>
							<td><select size="1" name="faqtyp_no" style="width:100%;">
								<c:forEach var="faq_typVO" items="${faq_typeSvc.all}">
									<option value="${faq_typVO.faqtyp_no}" ${(faqVO.faqtyp_no==faq_typVO.faqtyp_no)?'selected':'' } >${faq_typVO.faqtyp_name}
								</c:forEach>
							</select></td>
						</tr>
							<tr>
								<th>問題</th>
								<td><textarea required id="faq_question" class="sty-input mr-left mr-btm-normal"  name="faq_question" >${faqVO.faq_question}</textarea>
								</td>
							</tr>
							<tr>
								<th>回答</th>
								<td><textarea required id="faq_answer" class="sty-input mr-left mr-btm-normal"  name="faq_answer" >${faqVO.faq_answer}</textarea>
								</td>
							</tr>

						</table>
						<br>
						<input type="hidden" name="action" value="update">
						<input type="hidden" name="faq_no" value="${faqVO.faq_no}">
						<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>">
						<a id="abled-btn" class="btn btn-light btn-brd grd1 effect-1 btn-pos" style="margin: 1% 0 1% 50%; display:block;" >
							<input type="submit" value="送出" class="input-pos">
						</a>
						</FORM>
                       <!-- update movie End -->

                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->


</body>
</html>