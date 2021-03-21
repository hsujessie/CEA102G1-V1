<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sessions</title>
<%@ include file="/front-end/files/frontend_importCss.file"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/movie/frontendMovies.css">

<style type="text/css">
.combtn-search {
    padding: 2px 10px;
}
.vta-bm{
	vertical-align: bottom;
	width: 150px;
}
.line-spearate{
	border-bottom: 2px solid rgb(170,145,102);
    box-sizing: border-box;
}
.pd-bottom{
    padding-bottom: 3%;
}
</style>
</head>
<body>
        <div class="wrapper">
            <!-- Nav Bar Start -->
			<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set>
            <%@ include file="/front-end/files/frontend_navbar.file"%>
            <!-- Nav Bar End -->


            <!-- Page Header Start -->
            <div class="page-header">
                <div class="container">
                    <div class="row">
                        <div class="col-12">
                            <h2>Sessions</h2>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Page Header End -->


            <jsp:useBean id="movSvc" scope="page" class="com.movie.model.MovService"/>
            <jsp:useBean id="sesSvc" scope="page" class="com.session.model.SesService"/>
            <!-- Sessions Start -->
            <div class="session">
                <div class="container">
                    <div class="row " style="margin: -50px 0 50px 0;">         
		                <div class="col-5"></div>
                        <div class="col-7">                          
                           	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/session/ses.do">	
		                        <b>場次日期</b>
                        
	                            <select id="sesDateId" name="sesDate" class="vta-bm">
	                                <!-- 下拉選單，日期搜尋，只顯示當天+6日(共7天日期)，如：2021-03-10星期三、2021-03-11星期四、2021-03-12星期五 -->
	                                <option value="">
	                            </select>
	                            
		                        <input type="hidden" name="action" value="searchSesDate" class="vta-bm">
								<input type="submit" value="搜尋" class="combtn combtn-search">
	                    	</FORM>                    
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-12">
	                            
                           <c:if test="${empty getMovies_BySesDate}"> <!-- 只顯示當日電影 Start -->
                               	<c:forEach var="distinctMovNo" items="${sesSvc.getDistinctMovNo()}" >
                               		<div class="row align-items-center session-item"> <!-- session-item Start -->
	                            	<c:set var="movVO" value="${movSvc.getOneMov(distinctMovNo)}" />	
                       				<c:if test="${(not empty movVO.movpos)}">
		                                <div class="col-5 pd-bottom"> <!-- col-5 Start -->
		                                    <div class="session-icon">
		                                        <img onclick="sendData(this,${distinctMovNo})"  style="height: 100%; cursor: pointer; border: 2px solid #aa9166;" src="<%=request.getContextPath()%>/movie/mov.do?movno=${distinctMovNo}&img=movpos&action=get_One_MovPos" alt="Movies Image">				                                    	
		                                    </div>
		                                </div> <!-- col-5 End -->
                                	</c:if>	
                               	
			                           	<div class="col-7 line-spearate pd-bottom"> <!-- col-7 Start -->
	                                 		<h3><a href="<%=request.getContextPath()%>/movie/mov.do?action=getOne_For_Display&fromFrontend=true&movno=${distinctMovNo}">${movVO.movname}</a></h3>		                                	
                                 			<c:forEach var="distinctSesDate" items="${sesSvc.getDistinctSesDate()}" >
                                 				<p><fmt:formatDate value="${distinctSesDate}" type="DATE" dateStyle="FULL"/></p>
                                 				<c:forEach var="sesTimes" items="${sesSvc.getSesTimes(distinctMovNo,distinctSesDate)}" > 
                                  					<p>
                                  						<a href="<%=request.getContextPath()%>/front-end/ordMas/TicketOrder.jsp?sesNo=${sesTimes.sesNo}"><fmt:formatDate value="${sesTimes.sesTime}" pattern="HH:mm" type="DATE"/> <i class="fas fa-ticket-alt" style="color:#aa9166;"></i></a> <label>【${sesTimes.theNo}廳】</label>
                                  					</p>          				
                                				</c:forEach>
                                 			</c:forEach>
	                                 	</div> <!-- col-7 End --> 
                                	</div> <!-- session-item End --> 
                               	</c:forEach>
	                            </c:if> <!-- 只顯示當日電影 End --> 
	                             
	                         
	                            <!-- 依日期查詢 Start -->
	                            <c:if test="${not empty getMovies_BySesDate}"> <!-- 顯示查詢之電影 Start -->
		                               	<c:forEach var="list" items="${getMovies_BySesDate}" >
		                               		<c:set var="searchDate" value="${list.sesDate}" />
		                               	</c:forEach>
		                               	<c:forEach var="distinctMovNo" items="${sesSvc.getDistinctMovNoBySearchDate(searchDate)}" >
		                               		<div class="row align-items-center session-item"> <!-- session-item Start -->
			                            	<c:set var="movVO" value="${movSvc.getOneMov(distinctMovNo)}" />	
		                       				<c:if test="${(not empty movVO.movpos)}">
				                                <div class="col-5 pd-bottom"> <!-- col-5 Start -->
				                                    <div class="session-icon">
				                                        <img onclick="sendData(this,${distinctMovNo})"  style="height: 100%; cursor: pointer; border: 2px solid #aa9166;" src="<%=request.getContextPath()%>/movie/mov.do?movno=${distinctMovNo}&img=movpos&action=get_One_MovPos" alt="Movies Image">				                                    	
				                                    </div>
				                                </div> <!-- col-5 End -->
		                                	</c:if>	
		                               	
					                           	<div class="col-7 line-spearate pd-bottom"> <!-- col-7 Start -->
			                                 		<h3><a href="<%=request.getContextPath()%>/movie/mov.do?action=getOne_For_Display&fromFrontend=true&movno=${distinctMovNo}">${movVO.movname}</a></h3>		                                	
		                                 			<c:forEach var="distinctSesDate" items="${sesSvc.getDistinctSesDateBySearchDate(searchDate)}" >
		                                 				<p><fmt:formatDate value="${distinctSesDate}" type="DATE" dateStyle="FULL"/></p>
		                                 				<c:forEach var="sesTimes" items="${sesSvc.getSesTimes(distinctMovNo,distinctSesDate)}" > 
		                                  					<p>
		                                  						<a href="<%=request.getContextPath()%>/front-end/ordMas/TicketOrder.jsp?sesNo=${sesTimes.sesNo}"><fmt:formatDate value="${sesTimes.sesTime}" pattern="HH:mm" type="DATE"/> <i class="fas fa-ticket-alt" style="color:#aa9166;"></i></a> <label>【${sesTimes.theNo}廳】</label>		                                  						
		                                  					</p>          				
		                                				</c:forEach>
		                                 			</c:forEach>
			                                 	</div> <!-- col-7 End --> 
		                                	</div> <!-- session-item End --> 
		                               	</c:forEach> 
	                            </c:if> <!-- 顯示查詢之電影 End --> 
	                            <!-- 依日期查詢 End -->                         
                        </div>
                    </div>
                </div>
            </div>
            <!-- Sessions End -->
            
            <!-- Book Tickets Start -->
            <%@ include file="/front-end/files/frontend_bookTicketsTamplate.file"%>
            <!-- Book Tickets End -->

            <!-- Footer Start -->
            <%@ include file="/front-end/files/frontend_footer.file"%>
            <!-- Footer End -->
        </div>
        
<%@ include file="/front-end/files/frontend_importJs.file"%>   


<script>
	function sendData(e,movno){
	    let url = "<%=request.getContextPath()%>/movie/mov.do?action=getOne_For_Display&fromFrontend=true&movno="+movno;
	    window.location.href = url;
	}
	
	
	/*========================================================
		場次日期搜尋 下拉選單 -> 只顯示當天+6日(共7天日期)
    ==========================================================*/
	window.onload = function(){
		let daysDuration = 5;
		
		let today = new Date();	
		let today_year = today.getFullYear();
		let today_month = getMonth(today);
		let today_date = getDate(today);	
		let today_day = today.getDay();	
		let show_day = "";
		
		for (let i = 0; i < daysDuration; i++){
			
			if( today_day == 7){
				today_day = 0;
			}
			if( today_day == 0){
				show_day = "日";
			}
			if( today_day == 1){
				show_day = "一";
			}
			if( today_day == 2){
				show_day = "二";
			}
			if( today_day == 3){
				show_day = "三";
			}
			if( today_day == 4){
				show_day = "四";
			}
			if( today_day == 5){
				show_day = "五";
			}
			if( today_day == 6){
				show_day = "六";
			}
			
			
 			let sesDateValue = today_year + "-" + today_month + "-" + today_date;
 			 $('#sesDateId').append("<option value=" + "\"" + sesDateValue + "\"" +">" + today_year + "-" + today_month + "-" + today_date + " 星期" + show_day + "</option>");

			today_date++;
			today_day++;
		}
	}
	
	function getMonth(mon) {
	  var month = mon.getMonth() + 1;
	  return month < 10 ? '0' + month : '' + month;
	}  
	
	function getDate(date) {
	  var date = date.getDate();
	  return date < 10 ? '0' + date : '' + date;
	}
</script>
</body>
</html>