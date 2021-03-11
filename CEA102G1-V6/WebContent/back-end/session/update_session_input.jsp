<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.session.model.*"%>

<html>
<head>
	<title>場次修改</title>
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
                    
                       <!-- update session Start -->  
					   <FORM method="post" action="<%=request.getContextPath()%>/session/ses.do" name="form_updateSession" enctype="multipart/form-data">	                 	
                       <h3 class="h3-style listOne-h3-pos">場次修改</h3>
						<c:if test="${addSuccess != null}">
							<span style="color: #bb9d52">  
								${addSuccess}
								<i class="fa fa-hand-peace-o"></i>
							</span>
						</c:if>
						<c:if test="${updateSuccess != null }">
							<span style="color: #bb9d52">  
								${updateSuccess}
								<i class="fa fa-hand-peace-o"></i>
							</span>
						</c:if>
						
			            <table>
							<tr>	
								<jsp:useBean id="movSvc" scope="page" class="com.movie.model.MovService"/>
								<th>電影</th>
								<td style="color: #bb9d52; padding-left: 3%;">
									<c:if test="${not empty sesVO.movNo}"> <!-- 沒寫這行判斷，會有java.lang.NullPointerException -->
										<c:set value="${movSvc.getOneMov(sesVO.movNo)}" var="movObj"></c:set>
									    ${movObj.movname}
								   </c:if>
								</td>
							</tr>
							<tr>
								<th>廳院</th>
								<td>
									<!-- 多選checkbox -->			
									<input class="mr-left mr-btm-sm" type="radio" name="theNo" value="1" <c:if test="${sesVO.theNo == 1}">checked</c:if> ><span class="ml-ten">A廳 (2D)</span><br>
									<input class="mr-left mr-btm-sm" type="radio" name="theNo" value="2" <c:if test="${sesVO.theNo == 2}">checked</c:if> ><span class="ml-ten">B廳 (3D)</span><br>
									<input class="mr-left mr-btm-sm" type="radio" name="theNo" value="3" <c:if test="${sesVO.theNo == 3}">checked</c:if> ><span class="ml-ten">C廳 (IMAX)</span><br>
								</td>
								<c:if test="${not empty errorMsgs.theNo}">
									<td class="errmsg-pos">		
										<i class="fa fa-hand-o-left" style="color:#bb9d52"></i>
										<label class="err-color">${errorMsgs.theNo}</label>
									</td>
								</c:if>
							</tr>
							<tr>
								<th>日期</th>
								<td>
									<input class="sty-input" name="sesDate" id="" type="date" value="${sesVO.sesDate}">
								</td>
								<c:if test="${not empty errorMsgs.sesDate}">
									<td class="errmsg-pos">		
										<i class="fa fa-hand-o-left" style="color:#bb9d52"></i>
										<label class="err-color">${errorMsgs.sesDate}</label>
									</td>
								</c:if>
							</tr>
							<tr>
								<th>時間</th>
								<td>	
								    <input class="sty-input" type="time" name="sesTime" value="${sesVO.sesTime}">
								</td>
							</tr>
						</table>
						<br>
						<input type="hidden" name="action" value="update">
						<input type="hidden" name="sesNo" value="${sesVO.sesNo}">
						<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>">
						<input type="hidden" name="whichPage"  value="<%=request.getParameter("whichPage")%>">
						<a class="btn btn-light btn-brd grd1 effect-1 btn-pos" style="margin: 1% 0 1% 50%;" >
							<input type="submit" value="送出" class="input-pos">
						</a>
						</FORM>
                       <!-- update session End -->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
</body>
</html>