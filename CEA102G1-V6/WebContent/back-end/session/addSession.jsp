<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.session.model.*"%>
<%@ page import="com.movie.model.*"%>

<%SesVO sesVO = (SesVO) request.getAttribute("sesVO");%>

<html>
<head>
	<title>場次新增</title>	
	<%@ include file="/back-end/files/sb_head.file"%>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/datetimepicker/jquery.datetimepicker.css" />
	<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
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
  .err-color{
    color: #A50203;
    font-size: 14px;
    margin-top: 5px;
    font-weight: normal;
  }
  .xdsoft_datetimepicker.xdsoft_dark .xdsoft_calendar td, .xdsoft_datetimepicker.xdsoft_dark .xdsoft_calendar th {
   	border-radius: 0px;
  }
  .ui-timepicker-standard .ui-state-hover{
  	background-color: #bb9d52;
  	border: 1px solid #aa9166;
  	color: #fff;
  	cursor: pointer;
  }
  #abled-btn{
    z-index: 0;
  }
  .fail-span{
    color: #A50203;
    font-size: 16px;
    margin-left: 10px;
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
                    	
                    	<!-- failure message Start -->
						<c:if test="${errorSessionMsgs != null}">
							<span class="fail-span"> 
								<i class="far fa-frown"></i>
								${errorSessionMsgs}
							</span>
						</c:if>
                    	<!-- failure message End -->
                    	
						<table>
							<tr>
							<jsp:useBean id="movSvc" scope="page" class="com.movie.model.MovService"/>
								<th>電影</th>																		<!-- Only display the movies which are the difference of 上映日期 minus 當天日期 is greater than 7days -->
								<td>
									<select name="movNo" style="width: 400px;">
							             <option value=""></option>
							             <c:forEach var="movVO" items="${movSvc.all}" >											
											<jsp:useBean id="now" class="java.util.Date"/>
											<c:if test="${movVO.movoffdate ge now}">                                                         <!-- 在SesServlet.java 驗證，若有不符驗證，會丟 a movNo attribute 到 jsp，為了留住原本已選的電影。 -->
												<option value="${movVO.movno}" data-movver="${movVO.movver}" data-movondate="${movVO.movondate}" <c:if test="${not empty movNo and movNo == movVO.movno}">selected</c:if>>${movVO.movname}
											</c:if>
							             </c:forEach>
						             </select>
						             <span id="movNo-errmsg" style="display:none;">			
										<i class="far fa-hand-point-up" style="color:#bb9d52;"></i>
										<label id="movNo-errmsg-txt" class="err-color"></label>
									</span>
								</td>
							</tr>
							<tr>
								<th>廳院</th>
								<td>
									<!-- 多選checkbox -->
									<jsp:useBean id="theSvc" scope="page" class="com.theater.model.TheService"/>
									<jsp:useBean id="movVerSvc" scope="page" class="com.movie_version.model.MovVerService"/>
									<c:forEach var="theVO" items="${theSvc.all}" >	
										<input class="mr-left mr-btm-sm" type="checkbox" name="theNo" value="${theVO.the_no}" <c:if test="${not empty theNo and theNo eq theVO.the_no}">checked</c:if> >
										<c:choose>
											<c:when test="${theVO.the_no ge 10 && theVO.the_no != 1 && theVO.the_no != 7 && theVO.the_no != 11}">
												<c:set var="left" value="10"/>
    										</c:when>
											<c:when test="${theVO.the_no eq 1}">
												<c:set var="left" value="20"/>
    										</c:when>
											<c:when test="${theVO.the_no eq 7}">
												<c:set var="left" value="19"/>
    										</c:when>
											<c:when test="${theVO.the_no eq 11}">
												<c:set var="left" value="12"/>
    										</c:when>
    										<c:otherwise>
												<c:set var="left" value="18"/>
    										</c:otherwise>
										</c:choose>
										<span class="ml-ten">${theVO.the_no}廳</span><span style="padding-left:${left}px;">【<c:forEach var="movVerVO" items="${movVerSvc.all}"><c:if test="${theVO.movver_no == movVerVO.movver_no}"><span class="movverName">${movVerVO.movver_name}</span></c:if></c:forEach>】</span><br>
									</c:forEach>
									<span id="theNo-errmsg" style="display:none;">			
										<i class="far fa-hand-point-up" style="color:#bb9d52;"></i>
										<label id="theNo-errmsg-txt" class="err-color"></label>
									</span>
								</td>
							</tr>
							<tr>
								<th>日期</th>
								<td>
									<input class="sty-input" name="sesDateBegin" id="sesdate_begin" type="text" value="${not empty sesDateBegin ? sesDateBegin : ''}" style="width: 150px;"> 
							        ~ <input class="sty-input" name="sesDateEnd" id="sesdate_end" type="text" value="${not empty sesDateEnd ? sesDateEnd : ''}" style="width: 150px;">
							        <span id="sesDate-errmsg" style="display:none;">			
										<i class="far fa-hand-point-left" style="color:#bb9d52;"></i>
										<label id="sesDate-errmsg-txt" class="err-color"></label>
									</span>
									<c:if test="${not empty errorDateMsgs}">					
										<span>		
											<label class="err-color"><i class="far fa-hand-point-up" style="color:#bb9d52;"></i> ${errorDateMsgs}</label>
										</span>
									</c:if>	
								</td>
							</tr>
							<tr>
								<th>	
									<input id="addtime" type="button" value="新增時間">
									<span id="addtime-errmsg" style="display:none;">			
										<i class="far fa-hand-point-up" style="color:#bb9d52;"></i>
										<label id="addtime-errmsg-txt" class="err-color"></label>
									</span>	
								</th>
							</tr>
						</table>
						<table id="timetb" ${not empty errorTimeMsgs? 'style="display:block;"' : 'style="display:none;"'}>
							<tr>
								<th>編號</th>
								<th style="padding-left: 10px;">時間
									<c:if test="${not empty errorTimeMsgs}">					
										<span id="sesTime-errmsg">		
											<label class="err-color"><i class="far fa-hand-point-down" style="color:#bb9d52;"></i> ${errorTimeMsgs}</label>
										</span>
									</c:if>	
								</th>								
							</tr>
							<c:if test="${not empty sesTimeList}">
								<c:forEach var="sesTimelist" items="${sesTimeList}" varStatus="no">
									<tr><th>${no.index+1}</th>
										<td>
											<input type="text" name="sesTime" value="${sesTimelist}">
										</td>
					   					<td>
					   						<input type=button value="刪除" id="delete" class="delete-btn-sty" onclick='removeTr(this)'>
					   					</td>
					   				</tr>								
								</c:forEach>
							</c:if>
						</table>
						<br>
						<input type="hidden" name="action" value="insert">
						<a id="abled-btn" class="btn btn-light btn-brd grd1 effect-1 btn-pos" style="margin: 1% 0 1% 50%; display:none;" >
							<input type="submit" value="送出" class="input-pos">
						</a>
						<a id="disabled-btn" class="btn btn-light btn-brd grd1 btn-pos" style="display:block; margin: 1% 0 1% 50%; background-color: #808080; border: 2px solid #808080!important; cursor: default;" >
							<input type="submit" value="送出" class="input-pos" style="background-color: #808080;" disabled>
						</a>
						</FORM>
                       <!-- addSession End -->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
		<script src="<%=request.getContextPath()%>/resource/datetimepicker/jquery.js"></script>
		<script src="<%=request.getContextPath()%>/resource/datetimepicker/jquery.datetimepicker.full.js"></script>
		<script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
<script>
	$.datetimepicker.setLocale('zh');
	$(function(){
		 /* let date = new Date();
		 date = new Date(date.setDate(date.getDate()+3));  // 當日 plus 3 days
		 let cusDefaultDate = date.getFullYear() + "-" + ((date.getMonth()+1) > 9 ? date.getMonth()+1: "0"+( date.getMonth()+1)) + "-" + (date.getDate() > 9 ? date.getDate():"0" + date.getDate());
		 $('#sesdate_begin').val(cusDefaultDate);  // make the input displays 當日plus 3 days 的日期
		 $('#sesdate_end').val(cusDefaultDate); */
		 
		 $('#sesdate_begin').datetimepicker({
		  theme:'dark',
		  format:'Y-m-d',
		  onShow:function(){
		   this.setOptions({
		    maxDate:$('#sesdate_end').val()?$('#sesdate_end').val():false
		   })
		  },
		  timepicker:false
		  /* ,defaultDate: cusDefaultDate   // 自訂預設日期為 當日 plus 3 days */
		 });
	
		 $('#sesdate_end').datetimepicker({
		  theme:'dark',
		  format:'Y-m-d',
		  onShow:function(){
		   this.setOptions({
		    minDate:$('#sesdate_begin').val()?$('#sesdate_begin').val():false
		   })
		  },
		  timepicker:false
		 });
	});
	
	
	let theNoZero = $("input[name='theNo']")[0];
	let theNoFirst = $("input[name='theNo']")[1];
	let theNoSecond = $("input[name='theNo']")[2];
	let theNothree = $("input[name='theNo']")[3];
	let theNoFour = $("input[name='theNo']")[4];
	let theNoFif = $("input[name='theNo']")[5];
	
	/* =====================================================================================================
		    * 在SesServlet.java 驗證，若有不符驗證，會丟 a theNoList attribute 到 jsp，為了留住原本已勾選的廳院。
	======================================================================================================== */
	<c:if test="${not empty theNoList}">
		let theNoArr = new Array();
		<c:forEach items="${theNoList}" var="theNoList"> 
			theNoArr.push(${theNoList});
	 	</c:forEach>
		for(let i = 0; i < theNoArr.length; i++){
	 		if(theNoArr[i] == "1"){	// 2D
				theNoZero.checked = true;
			}
			if(theNoArr[i] == "2"){	// 3D
				theNoFirst.checked = true;
			}
			if(theNoArr[i] == "3"){	// IMAX
				theNoSecond.checked = true;
			} 
	 		if(theNoArr[i] == "4"){	// 2D
	 			theNothree.checked = true;
			}
			if(theNoArr[i] == "5"){	// 3D
				theNoFour.checked = true;
			}
	 		if(theNoArr[i] == "6"){	// IMAX
	 			theNoFif.checked = true;
			}
		}
 	</c:if> 


	let theNo = $("input[name='theNo']");
	let movVerName = $('.movverName');
	
	$('select[name="movNo"]').change(function(){
		/* =========================================================================================== */
										/* 選擇電影後，自動勾選相對應的廳院 */
		/* =========================================================================================== */
		let movVer = $("select[name='movNo'] :selected").data('movver');
		let movVerArr = [];
		if(movVer.indexOf(",") == -1){
			/* for(let i = 0; i < theNo.length; i++){
				console.log("theNoVal= " + theNo[i].value);
			} */
			/* ========================================================================================================
			  	   * have to make 「checked false」 to prevent the remained 「checked」 which are from other checkboxes
			=========================================================================================================== */
			
			theNoZero.checked = false;
			theNoFirst.checked = false;
			theNoSecond.checked = false;
			theNothree.checked = false;
			theNoFour.checked = false;
			theNoFif.checked = false;
			
		/* 	let movverNames = $('.movverName');
			console.log("length= " + movverNames.length);
			let movTxt = '';
			for(let i = 0; i < movverNames.length; i++){
				let movTxt = movverNames[i].innerText;
				
				if(movVer == movTxt){ 
					console.log("==enter== "+movTxt);
			 	} 
			} */
			
			/* if(movVer == "2D"){
				theNoZero.checked = true;
				theNothree.checked = true;
			}
			if(movVer == "3D"){
				theNoFirst.checked = true;
				theNoFour.checked = true;
			}
			if(movVer == "IMAX"){
				theNoSecond.checked = true;
				theNoFif.checked = true;
			} */	
		}else{
			
			theNoZero.checked = false;
			theNoFirst.checked = false;
			theNoSecond.checked = false;
			theNothree.checked = false;
			theNoFour.checked = false;
			theNoFif.checked = false;
			
			/* ================================================================================================
			    * 新增電影時，movver是checkbox多選，用字串串接的方式存進db, so that it has to split the string here.
			=================================================================================================== */
			movVerArr = movVer.split(",")

			/* for(let i = 0; i < theNo.length; i++){
				console.log("theNoVal= " + theNo[i].value);
			} */ 

			let movTxt = '';
			for(let i = 0; i < 3; i++){
				movTxt.push(movVerName[i].innerText);
				console.log("movTxt= " + movTxt);
			}
			
			for(let i = 0; i < movVerArr.length; i++){
				console.log("movVerArr= " + movVerArr[i]);	
				
				console.log("movTxt= " + movTxt);	
				if(movVerArr[i] == movTxt){
					console.log("---enter---");
					continue;
				}
				
				/* 
				if(movVerArr[i] == "2D"){
					theNoZero.checked = true;
					theNothree.checked = true;
				}
				if(movVerArr[i] == "3D"){
					theNoFirst.checked = true;
					theNoFour.checked = true;
				}
				if(movVerArr[i] == "IMAX"){
					theNoSecond.checked = true;
					theNoFif.checked = true;
				}	 */
			}
		}
		

		/* =========================================================================================== */
									  /* 選擇電影後，自動帶出相對應的上映日期 */
		/* =========================================================================================== */
		let movOndate = $("select[name='movNo'] :selected").data('movondate');
		$('#sesdate_begin').val(movOndate);
		$('#sesdate_end').val(movOndate);
	});
	
	 
	/* =========================================================================================== */
									/* 新增時間 */ 
	/* =========================================================================================== */
	let addtime = document.getElementById("addtime");
	let count = 0;
	let timeCount = 10;   // 預設 10'o clock
	addtime.addEventListener("click",function(){
		$('#sesTime-errmsg').css('display','none');
		count+=1;
		let timetb = document.getElementById("timetb");
		timetb.style.display="block";
		
		/* ${'$'} 用EL，把 js ES6的$符號包起來，避免語法衝突。 */
		let tag = `<tr>
						<th>${'${count}'}</th>
						<td>                                      
							<input type="text" name="sesTime" value="${'$'}{timeCount}:00">
						</td>
				   		<td>
				   			<input type=button value="刪除" id="delete" class="delete-btn-sty" onclick='removeTr(this)'>
				   		</td>
				   	</tr>`;
		timetb.innerHTML += tag;
		
		/* =========================================================================================== */
		  						/* timepicker */
		/* =========================================================================================== */
		$('input[name="sesTime"]').timepicker({
		    timeFormat: 'HH:mm',
		    interval: 120,	//時間間隔 120 min
		    dynamic: true,
		    dropdown: true,
		    scrollbar: false
		 });
		
		timeCount += 2;  // 每個input的時間間隔 2hr
		if(timeCount == 24){
			timeCount = 0;
		}
		
	},false);
	
	function removeTr(e){
		count--;
		e.closest('tr').remove();
	}
	/* =========================================================================================== */
									/* Varify Inputs */
	/* =========================================================================================== */
	let movNoselect = $("select[name='movNo']")[0];
	
	theNoZero.addEventListener('change', isEmpty, false);
	theNoFirst.addEventListener('change', isEmpty, false);
	theNoSecond.addEventListener('change', isEmpty, false);
	theNothree.addEventListener('change', isEmpty, false);
	theNoFour.addEventListener('change', isEmpty, false);
	theNoFif.addEventListener('change', isEmpty, false);
	
	addtime.addEventListener("click", isEmpty, false);
	$('#sesdate_begin').change(isEmpty);
	$('#sesdate_end').change(isEmpty);

	<c:if test="${not empty sesTimeList}">
		$('input[name="sesTime"]').focus(isEmpty);
		$('input[name="sesTime"]').timepicker({
		    timeFormat: 'HH:mm',
		    interval: 120,	//時間間隔 120 min
		    dynamic: true,
		    dropdown: true,
		    scrollbar: false
		 });
	</c:if>
	
	function isEmpty(e){
		if(!theNoZero.checked && !theNoFirst.checked && !theNoSecond.checked && 
		   !theNothree.checked && !theNoFour.checked && !theNoFif.checked){
			$("#abled-btn").css('display','none');
			$("#disabled-btn").css('display','block'); 
			$("#theNo-errmsg").css('display','inline-block'); 
			$("#theNo-errmsg-txt").text("請選擇影廳");
		}
		
		if(theNoZero.checked || theNoFirst.checked || theNoSecond.checked ||
		   theNothree.checked || theNoFour.checked || theNoFif.checked){
			$("#abled-btn").css('display','block');
			$("#disabled-btn").css('display','none'); 
			$("#theNo-errmsg").css('display','none'); 
		}
		
		if($("select[name='movNo'] :selected").text() == ''){
			$("#abled-btn").css('display','none');
			$("#disabled-btn").css('display','block'); 
			$("#movNo-errmsg").css('display','inline-block'); 
			$("#movNo-errmsg-txt").text("請選擇電影");
		}else{
			$("#movNo-errmsg").css('display','none');
			$("#movNo-errmsg-txt").text("");
		}
		
		if($('#sesdate_begin').val() == '' || $('#sesdate_end').val() == ''){
			$("#abled-btn").css('display','none');
			$("#disabled-btn").css('display','block'); 
			$("#sesDate-errmsg").css('display','inline-block'); 
			$("#sesDate-errmsg-txt").text("請選擇日期");
		}else{
			$("#sesDate-errmsg").css('display','none');
			$("#sesDate-errmsg-txt").text("");
		}
		
		if(!document.querySelector('input[name="sesTime"]')){
			$("#abled-btn").css('display','none');
			$("#disabled-btn").css('display','block'); 
			$("#addtime-errmsg").css('display','inline-block'); 
			$("#addtime-errmsg-txt").text("請新增時間");
		}else{
			$("#addtime-errmsg").css('display','none');
			$("#addtime-errmsg-txt").text("");
		}
	}
</script>
</body>
</html>