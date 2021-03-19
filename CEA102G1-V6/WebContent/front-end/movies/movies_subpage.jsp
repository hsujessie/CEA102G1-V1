<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="loginUrl" value="${pageContext.request.contextPath}/front-end/Member_Login/login.jsp" scope="page"/>
<c:set var="location" value="${pageContext.request.requestURI}" scope="session"/> <!-- 若沒登入，無法對該電影評分+短評，here have tp set a session attribute for the login page to get the origin url -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Movies</title>
<%@ include file="/front-end/files/frontend_importCss.file"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/movie/frontendMovies.css">
<style>
	.ml{
		margin-left: 2px;
	}
	.str-color{
		color: #bb9d52
	}
	
	/* -- Light box -- */
	#movies-comrep{
		position: fixed;
		top: 0;
    	left: 0;
		right: 0;
		bottom: 0;
		background-color: rgba(0,0,0,.9);
		z-index: 100;
	}
	.movies-comrep-content{
		width: 40%;
    	height: 50%;
		box-sizing: border-box;
		padding: 3% 0 0 3%;
		position: fixed;
		top: 20%;
		left:50%;
		transform: translateX(-50%);
        background-color: rgba(187,157,82, .7);
        box-shadow: 0px 1px 5px 0px rgb(110 110 110 / 90%);
	}
	.close{
		width: 30px;
		height: 30px;
		position: absolute;
		top: 0px;
		right: 0px;
		z-index: 1;
	}
	.close:hover{
		cursor: pointer;
	}
	.movies-lightbox-inside{
		width: 100%;
		display: flex;
		flex-direction: row;
		margin: auto;
	}
	.movies-comrep-content:before{
		content: "";
	    display: block;
	    height: 30px;
	    width: 3px;
	    background-color: #bb9d52;
	    position: absolute;
	    top: 0px;
	    right: 15px;
	    transform: rotate(45deg);
	}
	.movies-comrep-content:after{
		content: "";
	    display: block;
	    height: 30px;
	    width: 3px;
	    background-color: #bb9d52;
	    position: absolute;
	    top: 0px;
	    right: 15px;
	    transform: rotate(-45deg);
	}
	ol, ul {
    	list-style: none;
	}
	#comrepForm ul li input{
    	margin-right: 10px;
	}
	#comrepForm label{
       color: #2C2C2C;
	}
	.lightbox-btn{
		position: absolute;
	    bottom: 5%;
	    right: 4%;
	}
	
	/* -- 檢舉btn -- */
	#comrep a {
	    color: #888888;
	    transition: .3s;
	    cursor: pointer;
	}
	
	#comrep a:hover,
	#comrep a:active,
	#comrep a:focus {
	    color: #aa9166;
	    outline: none;
	    text-decoration: none;
	}
	
	/* -- 點我寫短評 -- */
	.writeComment{
		border: 3px solid #aa9166;
    	box-sizing: border-box;
    	padding: 1% 0 1% 4%;
    	font-size: 16px;
	}
	.writeComment:hover{
		box-shadow: 5px 5px 5px #121518;
		transition: 1s ease-in-out;
	}
</style>
</head>
<body>
        <div class="wrapper">
            <!-- Nav Bar Start -->
			<c:set value="moviesSub" var="urlRecog"></c:set> <!-- 給navbar_frontend.file的參數-Sub -->
            <%@ include file="/front-end/files/frontend_navbar.file"%>
            <!-- Nav Bar End -->


            <!-- Information Start -->
            <div class="movinfo">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-lg-5 col-md-6">
                            <div class="movinfo-vdo">
                             	<video controls width="150"><source src="<%=request.getContextPath()%>/movie/mov.do?movno=${movVO.movno}&action=get_One_MovTra" type="video/mp4"></video>
                            </div>
                        </div>
                        <div class="col-lg-7 col-md-6">
                            <div class="section-header">
                                <h2>Information</h2>
                            </div>
                            <div class="movinfo-text">
                                <p>名&emsp;&emsp;稱 &emsp;|&emsp; ${movVO.movname}</p>
                                <p>上映日期 &emsp;|&emsp; ${movVO.movondate}</p>
                                <p>類&emsp;&emsp;型 &emsp;|&emsp; ${movVO.movtype}</p>
                                <p>級&emsp;&emsp;數 &emsp;|&emsp; ${movVO.movrating}</p>
                                <p>片&emsp;&emsp;長 &emsp;|&emsp; ${movVO.movdurat}小時</p>
                                <p>導&emsp;&emsp;演 &emsp;|&emsp; ${movVO.movditor}</p>
                                <p>演&emsp;&emsp;員 &emsp;|&emsp; ${movVO.movcast}</p>
                                
		            			<jsp:useBean id="expSvc" scope="page" class="com.expectation.model.ExpService"/>
		            			<jsp:useBean id="satSvc" scope="page" class="com.satisfaction.model.SatService"/>
                                <p><span style="letter-spacing: 8px;">期待度</span><span style="margin-left: 12px;">|</span>&emsp; ${expSvc.getExpRatingAvg(movVO.movno)}</p>
                                <p><span style="letter-spacing: 8px;">滿意度</span><span style="margin-left: 12px;">|</span>&emsp; ${satSvc.getSatRatingAvg(movVO.movno)}</p>
                            </div>
                        </div>
                    </div>
                    
					<!-- for 判斷 電影是否上映 Start -->
					<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy-MM-dd" var="today" />
        			<fmt:formatDate value="${movVO.movondate}" pattern="yyyy-MM-dd" var="movOndate" />
        			<fmt:formatDate value="${movVO.movoffdate}" pattern="yyyy-MM-dd" var="movOffdate" />
                    <!-- for 判斷 電影是否上映 End -->
                    
                    <div class="row">
                        <div class="col-lg-1 col-md-3">
                            <p style="color:#aa9166;">期待度</p>
                        </div>
                        <div class="col-lg-11 col-md-9">                    								          <!-- 已上映 - 上映日大於等於當日 and 下檔日小於當日-->          													   <!-- 已下檔 - 下檔日大於當日 -->
                            <form method="post" action="<%=request.getContextPath()%>/expectation/exp.do" <c:if test="${today ge movOndate and today lt movOffdate}">style="display:none;"</c:if> <c:if test="${today ge movOffdate}">style="display:none;"</c:if> >                                                       
	                            <label><input type="radio" name="expRating" value="1"><span class="ml">想看</span><i class="far fa-smile ml" style="color:#aa9166;"></i></label>&emsp;&emsp;
	                            <label><input type="radio" name="expRating" value="0"><span class="ml">不想看</span><i class="far fa-meh ml" style="color:#aa9166;"></i></label>

  								<input type="hidden" name="movNo" value="${movVO.movno}" />
								<input type="hidden" name="action" value="insert">
								
	                    		<c:if test="${not empty MemberVO.memAccount}"> <!-- 已登入 --> 
  									<input type="hidden" name="memNo" value="${MemberVO.memNo}" />
                            		<input class="combtn" type="submit" value="送出" style="margin-left: 5%; padding: 2px 10px;">
	                            </c:if>
	                    		<c:if test="${empty MemberVO.memAccount}"> <!-- 未登入 --> 
	                    			<a class="combtn" style="margin-left: 5%; padding: 5px 10px;" href="${loginUrl}">送出</a>
	                            </c:if>
                            </form>
                            
                        	<c:if test="${today ge movOndate and today lt movOffdate}"> <!-- 已上映 --> 
	                        	<label style="font-size: 14px;">【電影已上映,投票結束】</label>
	                        </c:if>
	                        <c:if test="${today ge movOffdate}"> <!-- 已下檔 --> 
	                        	<label style="font-size: 14px;">【電影已下檔,投票結束】</label>
	                        </c:if>
                        </div>
                    </div>
                    
                     <div class="row" style="margin-top: 5px;">
                        <div class="col-lg-1 col-md-3">
                            <p style="color:#aa9166;">滿意度</p>
                        </div>
                        <div class="col-lg-11 col-md-9">                    							    <!-- 已上映 - 上映日小於等於當日-->          					    <!-- 已下檔 - 下檔日大於當日 -->                   
                            <form method="post" action="<%=request.getContextPath()%>/satisfaction/sat.do" <c:if test="${today le movOndate}">style="display:none;"</c:if> <c:if test="${today ge movOffdate}">style="display:none;"</c:if> >        	
                            	<label><input type="checkbox" name="satRating" value="1" style="display:none;" /><i class="fa fa-star" aria-hidden="true"></i></label>
                            	<label><input type="checkbox" name="satRating" value="1" style="display:none;" /><i class="fa fa-star" aria-hidden="true"></i></label>
                            	<label><input type="checkbox" name="satRating" value="1" style="display:none;" /><i class="fa fa-star" aria-hidden="true"></i></label>
                            	<label><input type="checkbox" name="satRating" value="1" style="display:none;" /><i class="fa fa-star" aria-hidden="true"></i></label>
                            	<label><input type="checkbox" name="satRating" value="1" style="display:none;" /><i class="fa fa-star" aria-hidden="true"></i></label>
                  
  								<input type="hidden" name="movNo" value="${movVO.movno}" />
								<input type="hidden" name="action" value="insert">
								
	                    		<c:if test="${not empty MemberVO.memAccount}"> <!-- 已登入 --> 
  									<input type="hidden" name="memNo" value="${MemberVO.memNo}" />
	                            	<input class="combtn" type="submit" value="送出" style="margin-left: 13.4%; padding: 2px 10px;">
	                            </c:if>
	                    		<c:if test="${empty MemberVO.memAccount}"> <!-- 未登入 --> 
	                    			<a class="combtn" style="margin-left: 13.4%; padding: 5px 10px;" href="${loginUrl}">送出</a>
	                            </c:if>
                            </form>
                            
                        	<c:if test="${today le movOndate}"> <!-- 已上映 --> 
	                        	<label style="font-size: 14px;">【電影未上映,投票尚未開始】</label>
	                        </c:if>
	                        <c:if test="${today ge movOffdate}"> <!-- 已下檔 --> 
	                        	<label style="font-size: 14px;">【電影已下檔,投票結束】</label>
	                        </c:if>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Information End -->


            <!-- Synopsis Start -->
            <jsp:useBean id="comSvc" scope="page" class="com.comment.model.ComService"/>
            <div class="movinfo">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-lg-12 col-md-12">
                            <div class="section-header">
                                <h2>Synopsis</h2>
                            </div>
                            <div class="movinfo-text">
                                <p>${movVO.movdes}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Synopsis End -->


            <!-- Reviews Start -->
            <div class="reviews">
                <div class="container">
                    <div class="section-header">
                        <h2>Reviews</h2>
                    </div>
                    <div class="reviews-start">
                    	<c:forEach var="comVO" items="${comSvc.all}" varStatus="no">
                    		<c:if test="${(comVO.movNo == movVO.movno) and (comVO.comStatus == 0)}">
		                         <div class="reviews-container ${(no.index mod 2 == 0) ? 'left' : 'right'}"><!-- 這邊怪怪的ㄋㄟ -->
		                            <div class="reviews-content">
		                                <c:set var="satObj" value="${satSvc.getOneSat(comVO.movNo,comVO.memNo)}"></c:set>                            
		                                <h2><span>Ratings</span><c:forEach var="i" begin="1" end="${satObj.satRating}"><i class="fa fa-star" aria-hidden="true"></i></c:forEach></h2>	                                
		                                <p>${comVO.comContent}</p>
		                                																														
										<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService"/>
										<c:set value="${memSvc.getOneMember(comVO.memNo)}" var="memObj"></c:set>
		                                <span>發表人 <label style="color:#aa9166;">${memObj.memName}</label>&emsp;|&emsp;</span>
		                                
		                                <span>發表時間 <label style="color:#aa9166;"><fmt:formatDate value="${comVO.comTime}" pattern="yyy-MM-dd HH:mm" type="DATE"/></label>&emsp;|&emsp;</span>
                   						<span id="comrep" onclick='openComRepLightbox(this,${comVO.comNo},${comVO.memNo},${comVO.movNo})'><a>檢舉</a></span>   
		                            </div>
		                        </div>
		                      </c:if>                 
	                    </c:forEach>
                    </div>                   
                </div>
            </div>
            <!-- Reviews End -->
            
	
            <!-- Comment Start -->
            <div class="movinfo">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-lg-12 col-md-12">
                            <div class="section-header">
                                <h2>Comments</h2>
                            </div>
                        </div>
                    </div>

                    <%-- <div class="row align-items-center" ${not empty MemberVO.memAccount ? '':'style="display:none;"'}> --%>
                    <div class="row align-items-center">
                        <div class="col-lg-12 col-md-12">
                            <form method="post" action="<%=request.getContextPath()%>/comment/com.do">
                                <textarea name="comContent" cols="30" rows="5" style="width: 100%; margin: 20px 0 5px 0;" placeholder="Write something here..."></textarea>                          
                                
  								<input type="hidden" name="movNo" value="${movVO.movno}" />
  								<%-- <input type="hidden" name="memNo" value="${MemberVO.memNo}" /> --%>
  								<input type="hidden" name="memNo" value="1" /> <!-- 測試用 -->
  								
								<input type="hidden" name="action" value="insert">
                            	<input class="combtn" type="submit" value="送出">
                            </form>
                        </div>
                    </div>

<%--                     <div class="row align-items-center" ${not empty MemberVO.memAccount ? 'style="display:none;"':''}>
                        <div class="col-lg-45 col-md-5"></div>
                        <div class="col-lg-2 col-md-2 writeComment">
                            <a href="${loginUrl}">點我寫短評 <i class="fas fa-pencil-alt" style="color:#aa9166;"></i></a>
                        </div>
                        <div class="col-lg-5 col-md-5"></div>
                    </div> --%>
                </div>
            </div>
            <!-- Comment End -->
            
            <!-- Book Tickets Start -->
            <%@ include file="/front-end/files/frontend_bookTicketsTamplate.file"%>
            <!-- Book Tickets End -->

            <!-- Footer Start -->
            <%@ include file="/front-end/files/frontend_footer.file"%>
            <!-- Footer End -->
        </div>
        
<!-- ================================================Light Box============================================ -->
<div class="movies-lightbox" id="movies-comrep" style="display: none;">
  	<div class="movies-comrep-content">
	  	<div class="close"></div>
		<div class="movies-lightbox-inside">
			<div>
				<form id="comrepForm" method="post" action="">
					<ul style="margin-left: -30px;">
					<li><input type="radio" name="comRepReason" value="1"><label>與本電影無關、捏造假冒、不實敘述</label></li>
					<li><input type="radio" name="comRepReason" value="2"><label>具有廣告性質或大量重複散布</label></li>
					<li><input type="radio" name="comRepReason" value="3"><label>相互惡意攻訐、猥褻騷擾、人身攻擊</label></li>
					<li><input type="radio" name="comRepReason" value="4"><label>侵犯隱私權、違反智慧財產權、涉及違法情事</label></li>
					<li><input type="radio" name="comRepReason" value="5"><label>違背善良風俗</label></li>
					</ul>
	                <input class="combtn lightbox-btn" type="submit" value="確認送出">
				</form>
			</div>
		</div>
 	</div>
</div>

<%@ include file="/front-end/files/frontend_importJs.file"%>
<script>
	let satRatingZer = $("input[name='satRating']")[0];
	let satRatingOne = $("input[name='satRating']")[1];
	let satRatingTwo = $("input[name='satRating']")[2];
	let satRatingThr = $("input[name='satRating']")[3];
	let satRatingFou = $("input[name='satRating']")[4];
	let faStars = document.getElementsByClassName("fa-star");
	
	satRatingZer.onclick = function(){
		console.log("0");
		if ($(this).prop('checked')) { 
			$(this).next().addClass('str-color');
		}
		else{
			$(this).next().removeClass('str-color'); 
			
			if(!faStars[0].classList.contains('str-color')){
				faStars[1].classList.remove('str-color');
				faStars[2].classList.remove('str-color');
				faStars[3].classList.remove('str-color');
				faStars[4].classList.remove('str-color');
			}
		}
	}	
	
	satRatingOne.onclick = function(){
		console.log("1");
		if ($(this).prop('checked')) { 
			$(this).next().addClass('str-color');

			if(faStars[1].classList.contains('str-color')){
				faStars[0].classList.add('str-color');
			}
		}
		else{
			$(this).next().removeClass('str-color'); 

			if(!faStars[1].classList.contains('str-color')){
				faStars[2].classList.remove('str-color');
				faStars[3].classList.remove('str-color');
				faStars[4].classList.remove('str-color');
			}
		}
	}
	
	satRatingTwo.onclick = function(){
		console.log("2");
		if ($(this).prop('checked')) { 
			$(this).next().addClass('str-color');
			
			if(faStars[2].classList.contains('str-color')){
				faStars[0].classList.add('str-color');
				faStars[1].classList.add('str-color');
			}
		}
		else{
			$(this).next().removeClass('str-color'); 
			
			if(!faStars[2].classList.contains('str-color')){
				faStars[3].classList.remove('str-color');
				faStars[4].classList.remove('str-color');
			}
		}
	}

	satRatingThr.onclick = function(){
		console.log("3");
		if ($(this).prop('checked')) { 
			$(this).next().addClass('str-color');
			
			if(faStars[3].classList.contains('str-color')){
				faStars[0].classList.add('str-color');
				faStars[1].classList.add('str-color');
				faStars[2].classList.add('str-color');
			}
		}
		else{
			$(this).next().removeClass('str-color'); 
			if(!faStars[3].classList.contains('str-color')){
				faStars[4].classList.remove('str-color');
			}
		}	
	}
	satRatingFou.onclick = function(){
		console.log("4");
		$(this).next().addClass('str-color');
		if ($(this).prop('checked')) { 

			if(faStars[4].classList.contains('str-color')){
				faStars[0].classList.add('str-color');
				faStars[1].classList.add('str-color');
				faStars[2].classList.add('str-color');
				faStars[3].classList.add('str-color');
			}
		}
		else{
			$(this).next().removeClass('str-color'); 
		}	
	}

	
	
/* =========================================================================================== */
   							/* Light box */
/* =========================================================================================== */
	  let lightbox = document.getElementsByClassName("movies-lightbox")[0];
	  let closeLightbox = document.getElementsByClassName("close")[0];
      closeLightbox.onclick=function(){
          lightbox.style.display="none";
      }

      function openComRepLightbox(e,comNo,memNo,movNo){
          lightbox.style.display="block";
          
          let url = "<%=request.getContextPath()%>/comment_report/comrep.do?action=insert&comNo=" + comNo + "&memNo=" + memNo + "&movNo=" + movNo;
          console.log("url= " + url);
          $('#comrepForm').attr("action", url);
      }
</script>
</body>
</html>