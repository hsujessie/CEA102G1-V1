<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.session.model.*"%>
<%@ page import="com.movie.model.*"%>

<%SesVO sesVO = (SesVO) request.getAttribute("sesVO");%>

<html>
<head>
	<title>場次新增</title>	
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
  #timetb tbody{
  	border-bottom: 2px dotted #bb9d52;
  }
  #addtime{
  	width: 80px;
    margin: 20px 0 8px 0;
    border: transparent;
    color: #fff;
    background-color: #bb9d52;
    box-sizing: border-box;
    padding: 8px;
    border-radius: 6px;
    font-size: 16px;
  }
  .delete-btn-sty{
  	width: 50px;
    border: transparent;
    color: #fff;
    background-color: #000;
    box-sizing: border-box;
    padding: 6px;
    border-radius: 6px;
    font-size: 14px;
  }
</style>
</head>
<body class="sb-nav-fixed">
		<%@ include file="/back-end/files/sb_navbar.file"%> <!-- 引入navbar (上方) -->
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <c:set value="sessionAdd" var="urlRecog"></c:set> <!-- 給sb_sidebar.file的參數-Add -->            
				<%@ include file="/back-end/files/sb_sidebar.file"%> <!-- 引入sidebar (左方) -->
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                    
                       <!-- addSession Start -->  
						<FORM method="post" action="<%=request.getContextPath()%>/session/ses.do" name="form_addSession"  enctype="multipart/form-data">
						<h3 class="h3-style listOne-h3-pos">場次新增</h3>
						<table>
							<tr>
							<jsp:useBean id="movSvc" scope="page" class="com.movie.model.MovService"/>
								<th>電影</th>
								<td>
									<select name="movNo">
							             <option value=""></option>
							             <c:forEach var="movVO" items="${movSvc.all}" >
							             	<option value="${movVO.movno}">${movVO.movname}
							             </c:forEach>
						             </select>
								</td>
							</tr>
							<tr>
								<th>廳院</th>
								<td>
									<!-- 多選checkbox -->
									<input class="mr-left mr-btm-sm" type="checkbox" name="theNo" value="1"><span class="ml-ten">A廳 (2D)</span><br>
									<input class="mr-left mr-btm-sm" type="checkbox" name="theNo" value="2"><span class="ml-ten">B廳 (3D)</span><br>
									<input class="mr-left mr-btm-sm" type="checkbox" name="theNo" value="3"><span class="ml-ten">C廳 (IMAX)</span><br>
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
									<input class="sty-input" name="sesDateBegin" id="" type="date" value=""> 
							        ~ <input class="sty-input" name="sesDateEnd" id="" type="date" value="">
								</td>
								<c:if test="${not empty errorMsgs.sesDate}">
									<td class="errmsg-pos">		
										<i class="fa fa-hand-o-left" style="color:#bb9d52"></i>
										<label class="err-color">${errorMsgs.sesDate}</label>
									</td>
								</c:if>
							</tr>
							<tr>
								<th>	
									<input id="addtime" type="button" value="新增時間">
								</th>
							</tr>
						</table>
						<table id="timetb" style="display:none;">
							<tr>
								<th>編號</th>
								<th style="padding-left: 10px;">時間</th>
							</tr>
						</table>
						<br>
						<input type="hidden" name="action" value="insert">
						<a class="btn btn-light btn-brd grd1 effect-1 btn-pos" style="margin: 1% 0 1% 50%;" >
							<input type="submit" value="送出" class="input-pos">
						</a>
						</FORM>
                       <!-- addSession End -->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
</body>
<script>
	let addtime = document.getElementById("addtime");
	let i = 0;
	addtime.addEventListener("click",function(){
		i+=1;
		let timetb = document.getElementById("timetb");
		timetb.style.display="block";
		let tag = "<tr><th>"+i+"</th><td><input type="+"\""+"time"+"\""+"name="+"\""+"sesTime"+"\""+"></td><td><input type="+"\""+"button"+"\""+"value="+"\""+"刪除"+"\""+"id="+"\""+"delete"+"\""+"class=\"delete-btn-sty\""+"onclick='removeTr(this)'></td></tr>";
		timetb.innerHTML += tag;
		
	},false);
	
	function removeTr(e){
		i--;
		e.closest('tr').remove();
	}
</script>
</html>