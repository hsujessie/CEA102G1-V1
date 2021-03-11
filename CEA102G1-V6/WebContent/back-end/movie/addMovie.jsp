<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.movie.model.*"%>

<%MovVO movVO = (MovVO) request.getAttribute("movVO");%>

<html>
<head>
	<title>電影新增</title>	
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
            	<c:set value="movieAdd" var="urlRecog"></c:set> <!-- 給sb_sidebar.file的參數-Add -->
				<%@ include file="/back-end/files/sb_sidebar.file"%> <!-- 引入sidebar (左方) -->
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                    
                       <!-- addMovie Start -->  
						<FORM method="post" action="<%=request.getContextPath()%>/movie/mov.do" name="form_addMovie" enctype="multipart/form-data">
						<h3 class="h3-style listOne-h3-pos">電影新增</h3>
						<table>
							<tr>
								<th>名稱</th>
								<td><input class="sty-input mr-left mr-btm-normal" type="text" name="movname" value="<%= (movVO==null)? "金牌特務" : movVO.getMovname()%>" /></td>		
								<c:if test="${not empty errorMsgs.movname}">
									<td class="errmsg-pos">		
										<i class="fa fa-hand-o-left" style="color:#bb9d52"></i>
										<label class="err-color">${errorMsgs.movname}</label>
									</td>
								</c:if>
							</tr>
							<tr>
								<th>種類</th>
								<td>
									<!-- 多選checkbox -->
									<input class="mr-left mr-btm-sm" type="checkbox" name="movver" value="2D" ${movver == null? "checked":""} ><span class="ml-ten">2D</span><br>
									<input class="mr-left mr-btm-sm" type="checkbox" name="movver" value="3D"><span class="ml-ten">3D</span><br>
									<input class="mr-left mr-btm-sm" type="checkbox" name="movver" value="IMAX"><span class="ml-ten">IMAX</span><br>
								</td>
								<c:if test="${not empty errorMsgs.movver}">
									<td class="errmsg-pos">		
										<i class="fa fa-hand-o-left" style="color:#bb9d52"></i>
										<label class="err-color">${errorMsgs.movver}</label>
									</td>
								</c:if>
							</tr>
							<tr>
								<th>類型</th>
								<td>
									<select class="mr-left mr-btm-normal" name="movtype">
										<option value="劇情片">劇情片</option>
										<option value="動作片">動作片</option>
										<option value="動畫片">動畫片</option>
										<option value="喜劇片">喜劇片</option>
										<option value="愛情片">愛情片</option>
										<option value="科幻片">科幻片</option>
										<option value="恐怖片">恐怖片</option>
									</select>
								</td>
								<c:if test="${not empty errorMsgs.movtype}">
									<td class="errmsg-pos">		
										<i class="fa fa-hand-o-left" style="color:#bb9d52"></i>
										<label class="err-color">${errorMsgs.movtype}</label>
									</td>
								</c:if>
							</tr>
							<tr>
								<th>語言</th>
								<td>
									<!-- 多選checkbox -->
									<input class="mr-left mr-btm-sm" type="checkbox" name="movlan" value="英文" ${movlan == null? "checked":""} ><span class="ml-ten">英文</span><br>
									<input class="mr-left mr-btm-sm" type="checkbox" name="movlan" value="中文"><span class="ml-ten">中文</span><br>
									<input class="mr-left mr-btm-sm" type="checkbox" name="movlan" value="日文"><span class="ml-ten">日文</span><br>
								</td>
								<c:if test="${not empty errorMsgs.movlan}">
									<td class="errmsg-pos">		
										<i class="fa fa-hand-o-left" style="color:#bb9d52"></i>
										<label class="err-color">${errorMsgs.movlan}</label>
									</td>
								</c:if>
							</tr>
							<%
							  java.sql.Date movondate = null;
							  try {
								  movondate = movVO.getMovondate();
							   } catch (Exception e) {
								   movondate = new java.sql.Date(System.currentTimeMillis());
							   }
							%>
							<tr>
								<th>上映日期</th>
								<td><input class="sty-input mr-left mr-btm-normal" name="movondate" id="mov_ondate" type="date" value="<%=movondate%>"></td>
								<c:if test="${not empty errorMsgs.movondate}">
									<td class="errmsg-pos">		
										<i class="fa fa-hand-o-left" style="color:#bb9d52"></i>
										<label class="err-color">${errorMsgs.movondate}</label>
									</td>
								</c:if>
							</tr>
							<tr>
								<th>下檔日期</th>
								<td><input class="sty-input mr-left mr-btm-normal" name="movoffdate" id="mov_offdate" type="date" value=""></td>
								<c:if test="${not empty errorMsgs.movoffdate}">
									<td class="errmsg-pos">		
										<i class="fa fa-hand-o-left" style="color:#bb9d52"></i>
										<label class="err-color">${errorMsgs.movoffdate}</label>
									</td>
								</c:if>
							</tr>
							<tr>
								<th>片長</th>
								<td class="fake-txt"><input class="sty-input mr-left mr-btm-normal" type="text" name="movdurat" value="<%= (movVO==null)? "2" : movVO.getMovdurat()%>"/></td>
								<c:if test="${not empty errorMsgs.movdurat}">
									<td class="errmsg-pos" style="padding-left: 25%;">		
										<i class="fa fa-hand-o-left" style="color:#bb9d52"></i>
										<label class="err-color">${errorMsgs.movdurat}</label>
									</td>
								</c:if>
							</tr>
							<tr>
								<th>片長</th>
								<td>
									<select class="mr-left mr-btm-normal" name="movrating">
										<option value="普遍級">普遍級</option>
										<option value="保護級">保護級</option>
										<option value="輔導級">輔導級</option>
										<option value="限制級">限制級</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>導演</th>
								<td><input class="sty-input mr-left mr-btm-normal" type="text" name="movditor" value="<%= (movVO==null)? "dicrector" : movVO.getMovditor()%>" /></td>
								<c:if test="${not empty errorMsgs.movditor}">
									<td class="errmsg-pos">		
										<i class="fa fa-hand-o-left" style="color:#bb9d52"></i>
										<label class="err-color">${errorMsgs.movditor}</label>
									</td>
								</c:if>
							</tr>
							<tr>
								<th>演員</th>
								<td><input class="sty-input mr-left mr-btm-normal" type="text" name="movcast" value="<%= (movVO==null)? "actors" : movVO.getMovcast()%>" /></td>
								<c:if test="${not empty errorMsgs.movcast}">
									<td class="errmsg-pos">		
										<i class="fa fa-hand-o-left" style="color:#bb9d52"></i>
										<label class="err-color">${errorMsgs.movcast}</label>
									</td>
								</c:if>
							</tr>
							<tr>
								<th>簡介</th>
								<td><textarea name="movdes" class="sty-input mr-left"><%= (movVO==null)? "description" : movVO.getMovdes()%></textarea></td>
								<c:if test="${not empty errorMsgs.movdes}">
									<td class="errmsg-pos">		
										<i class="fa fa-hand-o-left" style="color:#bb9d52"></i>
										<label class="err-color">${errorMsgs.movdes}</label>
									</td>
								</c:if>
							</tr>
							<tr>
								<th>海報</th>
								<td>	
									<label class="btn" style="margin-left: 35%;">
										<i class="far fa-image"></i>
									<input id="uploadFile" style="display:none;" type="file" name="movpos" value="<%= (movVO==null)? "poster" : movVO.getMovpos()%>"/>			
									</label>
								</td>
							</tr>
							<tr>
								<th></th>
								<td id="show-poster">
									<span id="fileImg"></span>
								</td>
							</tr>
							<tr>
								<th>預告片</th>
								<td>
									<label class="btn" style="margin-left: 35%;">
										<i class="far fa-file-video"></i>
									<input id="uploadTrailer" style="display:none;" type="file" name="movtra" value="<%=(movVO==null)? "trailer" : movVO.getMovtra()%>"/>	
									</label>
								</td>
							</tr>
							<tr>
								<th></th>
								<td id="show-trailer">
									<span id="trailerVdo"></span>
								</td>
							</tr>
						</table>
						<br>
						<input type="hidden" name="action" value="insert">
						<a class="btn btn-light btn-brd grd1 effect-1 btn-pos" style="margin: 1% 0 1% 50%;" >
							<input type="submit" value="送出" class="input-pos">
						</a>
						</FORM>
                       <!-- addMovie End -->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
</body>

<!-- =========================================================================================== 
    								以下 CALCULATE mov_ondate & mov_offdate
	 ===========================================================================================  -->
	<script>
	<%@ include file="files/changeMovOffDate.file"%>
		let mov_ondate = document.getElementById('mov_ondate');
		let mov_offdate_val = document.getElementById('mov_offdate').value;
		if(mov_offdate_val.length == 0){
			changeMovOffDate();
		}
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
    
    uploadFile.addEventListener('change',function(e){
        let files = e.target.files;
        if(files){
            for(let i = 0; i<files.length; i++){
                let file = files[i];
                if(file.type.indexOf('image') != -1){
                    let reader = new FileReader();
                    reader.addEventListener('load',function(e){
                        let result = e.target.result;
                        let img = document.createElement('img');
                 		img.style.width='150px';  //這邊要動態增加css寬度，不然會跑版
                        img.classList.add('img');
                        img.src = result;
                        fileImg.append(img);
                    });

                    reader.readAsDataURL(file);
                }
            }
        }
    });
    

    uploadTrailer.addEventListener('change',function(e){
        let files = e.target.files;
        if(files){
            for(let i = 0; i<files.length; i++){
                let file = files[i];
                if(file.type.indexOf('video') != -1 ){
                    let reader = new FileReader();
                    reader.addEventListener('load',function(e){
                        let result = e.target.result;
                        let video = document.createElement('video');
                        video.style.width='300px';  //這邊要動態增加css寬度，不然會跑版
                        video.classList.add('vdo');
                        video.src = result;
                        trailerVdo.append(video);
                    });

                    reader.readAsDataURL(file);    	
    			}
            }
        }
    });
}
window.onload = init;
</script>
</html>