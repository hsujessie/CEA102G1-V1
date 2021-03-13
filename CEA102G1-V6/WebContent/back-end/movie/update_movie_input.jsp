<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.movie.model.*"%>

<html>
<head>
	<title>電影修改</title>
	<%@ include file="/back-end/files/sb_head.file"%>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/datetimepicker/jquery.datetimepicker.css" />
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
  .xdsoft_datetimepicker.xdsoft_dark .xdsoft_calendar td, .xdsoft_datetimepicker.xdsoft_dark .xdsoft_calendar th {
    border-radius: 0px;
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
					   <FORM method="post" action="<%=request.getContextPath()%>/movie/mov.do" name="form_updateMovie" enctype="multipart/form-data">	                 	
                       <h3 class="h3-style listOne-h3-pos">電影修改</h3>
						
			            <table>
							<tr>
								<th>名稱</th>
								<td><input class="sty-input mr-left mr-btm-normal" type="text" name="movname" value="${movVO.movname}" />
									<span id="movname-errmsg" style="display:none;">			
										<i class="far fa-hand-point-left" style="color:#bb9d52;"></i>
										<label id="movname-errmsg-txt" class="err-color"></label>
									</span>
								</td>
							</tr>
						
							<jsp:useBean id="movSvc" scope="page" class="com.movie.model.MovService" />
							<tr>
								<th>種類</th>
								<td>
									<!-- 多選checkbox -->
										<input class="mr-left mr-btm-sm" type="checkbox" name="movver" value="2D"      <c:forEach var="i" begin="0" end="2"> <c:if test="${movverToken[i].contains('2D')}">      checked </c:if></c:forEach> ><span class="ml-ten">2D</span><br/>
										<input class="mr-left mr-btm-sm" type="checkbox" name="movver" value="3D"      <c:forEach var="i" begin="0" end="2"> <c:if test="${movverToken[i].contains('3D')}">      checked </c:if></c:forEach> ><span class="ml-ten">3D</span><br/>
										<input class="mr-left mr-btm-sm" type="checkbox" name="movver" value="IMAX"    <c:forEach var="i" begin="0" end="2"> <c:if test="${movverToken[i].contains('IMAX')}">    checked </c:if></c:forEach> ><span class="ml-ten">IMAX</span><br/>
										<span id="movver-errmsg" style="display:none;">			
											<i class="far fa-hand-point-up" style="color:#bb9d52;"></i>
											<label id="movver-errmsg-txt" class="err-color"></label>
										</span>
								</td>
							</tr>
							<tr>
								<th>類型</th>
								<td>
									<select class="mr-left mr-btm-normal" name="movtype">
										<option <c:if test="${movVO.movtype.contains('劇情片')}"> selected </c:if>>劇情片</option>	
										<option <c:if test="${movVO.movtype.contains('動作片')}"> selected </c:if>>動作片</option>
										<option <c:if test="${movVO.movtype.contains('動畫片')}"> selected </c:if>>動畫片</option>
										<option <c:if test="${movVO.movtype.contains('喜劇片')}"> selected </c:if>>喜劇片</option>
										<option <c:if test="${movVO.movtype.contains('愛情片')}"> selected </c:if>>愛情片</option>
										<option <c:if test="${movVO.movtype.contains('科幻片')}"> selected </c:if>>科幻片</option>
										<option <c:if test="${movVO.movtype.contains('恐怖片')}"> selected </c:if>>恐怖片</option> 
									</select>
								</td>
							</tr>
							<tr>
								<th>語言</th>
								<td>
									<!-- 多選checkbox -->
									<input class="mr-left mr-btm-sm" type="checkbox" name="movlan" value="英文" <c:forEach var="i" begin="0" end="1"> <c:if test="${movlanToken[i].contains('英文')}"> checked </c:if></c:forEach> ><span class="ml-ten">英文</span><br/>
									<input class="mr-left mr-btm-sm" type="checkbox" name="movlan" value="中文" <c:forEach var="i" begin="0" end="1"> <c:if test="${movlanToken[i].contains('中文')}"> checked </c:if></c:forEach> ><span class="ml-ten">中文</span><br/>
									<input class="mr-left mr-btm-sm" type="checkbox" name="movlan" value="日文" <c:forEach var="i" begin="0" end="1"> <c:if test="${movlanToken[i].contains('日文')}"> checked </c:if></c:forEach> ><span class="ml-ten">日文</span><br/>
									<span id="movlan-errmsg" style="display:none;">			
										<i class="far fa-hand-point-up" style="color:#bb9d52;"></i>
										<label id="movlan-errmsg-txt" class="err-color"></label>
									</span>
								</td>
							</tr>
							<tr>
								<th>上映日期</th>
								<td><input class="sty-input mr-left mr-btm-normal" name="movondate" id="mov_ondate" type="text" value="<c:if test="${not empty movVO.movondate}">${movVO.movondate}</c:if>"></td>
							</tr>
							<tr>
								<th>下檔日期</th>
								<td><input class="sty-input mr-left mr-btm-normal" name="movoffdate" id="mov_offdate" type="text" value="<c:if test="${not empty movVO.movoffdate}">${movVO.movoffdate}</c:if>"></td>
							</tr>
							<tr>
								<th>片長</th>
								<td class="fake-txt"><input id="movDurat" class="sty-input mr-left mr-btm-normal" type="text" name="movdurat" value="${ empty movVO.movdurat ? '2' : movVO.movdurat}"/>
									<span id="movdurat-errmsg" class="errmsg-pos" style="display:none;">			
										<i class="far fa-hand-point-left" style="color:#bb9d52"></i>
										<label id="movdurat-errmsg-txt" class="err-color"></label>
									</span>
								</td>
							</tr>
							<tr>
								<th>級數</th>
								<td>
									<select class="mr-left mr-btm-normal" name="movrating">
										<option <c:if test="${movVO.movrating.contains('普遍級')}"> selected </c:if>>普遍級</option>
										<option <c:if test="${movVO.movrating.contains('保護級')}"> selected </c:if>>保護級</option>
										<option <c:if test="${movVO.movrating.contains('輔導級')}"> selected </c:if>>輔導級</option>
										<option <c:if test="${movVO.movrating.contains('限制級')}"> selected </c:if>>限制級</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>導演</th>
								<td><input id="movDitor" class="sty-input mr-left mr-btm-normal" type="text" name="movditor" value="${movVO.movditor}" />
									<span id="movditor-errmsg" style="display:none;">			
								        <i class="far fa-hand-point-left" style="color:#bb9d52;"></i>
								        <label id="movditor-errmsg-txt" class="err-color"></label>
								    </span>
								</td>
							</tr>
							<tr>
								<th>演員</th>
								<td><input id="movCast" class="sty-input mr-left mr-btm-normal" type="text" name="movcast" value="${movVO.movcast}" />
									<span id="movcast-errmsg" style="display:none;">			
								        <i class="far fa-hand-point-left" style="color:#bb9d52;"></i>
								        <label id="movcast-errmsg-txt" class="err-color"></label>
								    </span>	
								</td>
							</tr>
							<tr>
								<th>簡介</th>
								<td><textarea id="movDes" name="movdes" class="sty-input mr-left">${movVO.movdes}</textarea>
									<span id="movdes-errmsg" style="display:none;">			
								        <i class="far fa-hand-point-left" style="color:#bb9d52;"></i>
								        <label id="movdes-errmsg-txt" class="err-color"></label>
								    </span>
								</td>
							</tr>
							<tr>
								<th>海報</th>
								<td>
									<label id="posterBtn" class="btn" style="margin-left: 35%;">
									<input style="display:none;" type="file" name="movpos" value="${movVO.movpos}"/>
										<i class="far fa-image"></i>
									</label>
								</td>
							</tr>
							<tr>
								<th></th>
								<td id="show-poster">
									<span id="fileImg">	
									<c:if test="${not empty movVO.movpos}">
										<img width="150px" class="img" src="<%=request.getContextPath()%>/movie/mov.do?movno=${movVO.movno}&img=movpos&action=get_One_MovPos">
									</c:if>
									</span>
								</td>
							</tr>
							<tr>
								<th>預告片</th>
								<td>			
									<label id="trailerBtn" class="btn" style="margin-left: 35%;">
									<input style="display:none;" type="file" name="movtra" value="${movVO.movtra}"/>
										<i class="far fa-file-video"></i>
									</label>
								</td>
							</tr>
							<tr>
								<th></th>
								<td id="show-trailer">
									<span id="trailerVdo">		
									<c:if test="${not empty movVO.movtra}">		
										<video width="300px" class="vdo" src="<%=request.getContextPath()%>/movie/mov.do?movno=${movVO.movno}&action=get_One_MovTra"></video>
									</c:if>
									</span>
								</td>
							</tr>
						</table>
						<br>
						<input type="hidden" name="action" value="update">
						<input type="hidden" name="movno" value="${movVO.movno}">
						<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>">
						<input type="hidden" name="whichPage"  value="<%=request.getParameter("whichPage")%>">
						<a id="abled-btn" class="btn btn-light btn-brd grd1 effect-1 btn-pos" style="margin: 1% 0 1% 50%; display:block;" >
							<input type="submit" value="送出" class="input-pos">
						</a>
						<a id="disabled-btn" class="btn btn-light btn-brd grd1 btn-pos" style="display:none; margin: 1% 0 1% 50%; background-color: #808080; border: 2px solid #808080!important; cursor: default;" >
							<input type="submit" value="送出" class="input-pos" style="background-color: #808080;" disabled>
						</a>
						</FORM>
                       <!-- update movie End -->
                    
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
		 $('#mov_ondate').datetimepicker({
		  theme:'dark',
		  format:'Y-m-d',
		  onShow:function(){
		   this.setOptions({
		    maxDate:$('#mov_offdate').val()?$('#mov_offdate').val():false
		   })
		  },
		  timepicker:false
		 });

		 $('#mov_offdate').datetimepicker({
		  theme:'dark',		  
		  format:'Y-m-d',
		  onShow:function(){
		   this.setOptions({
		    minDate:$('#mov_ondate').val()?$('#mov_ondate').val():false
		   })
		  },
		  timepicker:false
		 });
	});
	
/* =========================================================================================== */
								/* Varify Inputs */
/* =========================================================================================== */	
	<%@ include file="/back-end/movie/files/varifyInputs.file"%>
	
	
/* =========================================================================================== */
	    						/* CALCULATE mov_ondate & mov_offdate */
/* =========================================================================================== */
	<%@ include file="/back-end/movie/files/changeMovOffDate.file"%>
		let mov_ondate = document.getElementById('mov_ondate');
		mov_ondate.addEventListener('change',function(){
			changeMovOffDate();
		});
		
	/* =========================================================================================== */
    								/* 以下 SHOW a UPLOADED IMAGE & VIDEO */
	/* =========================================================================================== */
function init(){
    let uploadFile = document.getElementById('uploadFile');
    let fileImg = document.getElementById('fileImg');
    let uploadTrailer = document.getElementById('uploadTrailer');
    let trailerVdo = document.getElementById('trailerVdo');
    let posterBtn = document.getElementById('posterBtn');
    let trailerBtn = document.getElementById('trailerBtn');
    
    /* IMAGE */
    let changeImg = function (e) {
    	 let files = e.target.files;
         if(files){
             for(let i = 0; i<files.length; i++){
                 let file = files[i];
                 if(file.type.indexOf('image') != -1){
                     let reader = new FileReader();
                     reader.addEventListener('load',function(e){
                         let result = e.target.result;
                         
                         <c:choose>
 	                        <c:when test="${not empty movVO.movpos}">
 	                        	let img = document.getElementsByClassName('img')[0];
 	                        </c:when>
 	                        <c:otherwise>
 	                    		let img = document.createElement('img');
 	                    		img.style.width='150px';  //這邊要動態增加css寬度，不然會跑版
 	                    		img.classList.add('img');
                         	</c:otherwise>
                     	</c:choose>
                     	                   	
                         img.src = result;
                         fileImg.append(img);
                     });

                     reader.readAsDataURL(file);
                 }
             }
         }
   	};

   	/* VIDEO */
    let changeVdo = function (e) {
    	 let files = e.target.files;
         if(files){
             for(let i = 0; i<files.length; i++){
                 let file = files[i];
                 if(file.type.indexOf('video') != -1 ){
                     let reader = new FileReader();
                     reader.addEventListener('load',function(e){
                         let result = e.target.result;   
                         
                         <c:choose>
 	                        <c:when test="${not empty movVO.movtra}">
 	                    		let video = document.getElementsByClassName('vdo')[0];
 	                        </c:when>
 	                        <c:otherwise>
 	                    		let video = document.createElement('video');
 	                            video.style.width='300px';  //這邊要動態增加css寬度，不然會跑版
 	                    		video.classList.add('vdo');
                         	</c:otherwise>
                     	</c:choose>
                     
                         video.src = result;
                         trailerVdo.append(video);
                     });

                     reader.readAsDataURL(file);
                 }
             }
         }
   	};
   	
   	/*======= two js events for changing a img & video immediately ======*/
   	posterBtn.addEventListener('click', changeImg, false);
   	window.addEventListener('change', changeImg, false);
    
   	trailerBtn.addEventListener('click', changeVdo, false);
   	window.addEventListener('change', changeVdo, false);
}
window.onload = init;
</script>
</body>
</html>