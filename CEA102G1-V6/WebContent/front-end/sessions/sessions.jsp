<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="location" value="${pageContext.request.requestURI}" scope="session"/> <!-- here have to set a session attribute for the login page to get the origin url -->

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
                            <h2></h2>
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
		                        <b>????????????</b>
                        
	                            <select id="sesDateId" name="sesDate" class="vta-bm">
	                                <!-- ?????????????????????????????????????????????+6???(???7?????????)?????????2021-03-10????????????2021-03-11????????????2021-03-12????????? -->
	                                <option value="">
	                            </select>
	                            
		                        <input type="hidden" name="action" value="searchSesDate" class="vta-bm">
								<input type="submit" value="??????" class="combtn combtn-search">
	                    	</FORM>                    
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-12">
	                            
                           <c:if test="${empty getMovies_BySesDate}"> <!-- ????????????????????? Start -->
                               	<c:forEach var="distinctMovNo" items="${sesSvc.getDistinctMovNo()}" >
                               		<div class="row align-items-center session-item"> <!-- session-item Start -->
	                            	<c:set var="movVO" value="${movSvc.getOneMov(distinctMovNo)}" />	
                       				<c:if test="${(not empty movVO.movpos)}">
		                                <div class="col-5 pd-bottom"> <!-- col-5 Start -->
		                                    <div class="session-icon">
		                                       <%--  <img onclick="sendData(this,${distinctMovNo})"  style="height: 100%; cursor: pointer; border: 2px solid #aa9166;" src="<%=request.getContextPath()%>/movie/mov.do?movno=${distinctMovNo}&img=movpos&action=get_One_MovPos" alt="Movies Image">	 --%>
		                                        <img onclick="sendData(this,${distinctMovNo})"  style="height: 100%; cursor: pointer; border: 2px solid #aa9166;" src="<%=request.getContextPath()%>/util/imgReader${movVO.movPosParam}">				                                    			                                        		                                        
		                                    </div>
		                                </div> <!-- col-5 End -->
                                	</c:if>	
                               	
			                           	<div class="col-7 line-spearate pd-bottom"> <!-- col-7 Start -->
	                                 		<h3><a href="<%=request.getContextPath()%>/movie/mov.do?action=getOne_For_Display&fromFrontend=true&movno=${distinctMovNo}">${movVO.movname}</a></h3>		                                	
                                 			<c:forEach var="distinctSesDate" items="${sesSvc.getDistinctSesDate()}" >
                                 				<p><fmt:formatDate value="${distinctSesDate}" type="DATE" dateStyle="FULL"/></p>
                                 				<c:forEach var="sesTimes" items="${sesSvc.getSesTimes(distinctMovNo,distinctSesDate)}" > 
                                  					<p>
                                  						<a href="<%=request.getContextPath()%>/front-end/ordMas/TicketOrder.jsp?sesNo=${sesTimes.sesNo}"><fmt:formatDate value="${sesTimes.sesTime}" pattern="HH:mm" type="DATE"/> <i class="fas fa-ticket-alt" style="color:#aa9166;"></i></a> <label>???${sesTimes.theNo}??????</label>
                                  					</p>          				
                                				</c:forEach>
                                 			</c:forEach>
	                                 	</div> <!-- col-7 End --> 
                                	</div> <!-- session-item End --> 
                               	</c:forEach>
	                            </c:if> <!-- ????????????????????? End --> 
	                             
	                         
	                            <!-- ??????????????? Start -->
	                            <c:if test="${not empty getMovies_BySesDate}"> <!-- ????????????????????? Start -->
		                               	<c:forEach var="list" items="${getMovies_BySesDate}" >
		                               		<c:set var="searchDate" value="${list.sesDate}" />
		                               	</c:forEach>
		                               	<c:forEach var="distinctMovNo" items="${sesSvc.getDistinctMovNoBySearchDate(searchDate)}" >
		                               		<div class="row align-items-center session-item"> <!-- session-item Start -->
			                            	<c:set var="movVO" value="${movSvc.getOneMov(distinctMovNo)}" />	
		                       				<c:if test="${(not empty movVO.movpos)}">
				                                <div class="col-5 pd-bottom"> <!-- col-5 Start -->
				                                    <div class="session-icon">
				                                        <%-- <img onclick="sendData(this,${distinctMovNo})"  style="height: 100%; cursor: pointer; border: 2px solid #aa9166;" src="<%=request.getContextPath()%>/movie/mov.do?movno=${distinctMovNo}&img=movpos&action=get_One_MovPos" alt="Movies Image"> --%>					                                        
		                                        		<img onclick="sendData(this,${distinctMovNo})"  style="height: 100%; cursor: pointer; border: 2px solid #aa9166;" src="<%=request.getContextPath()%>/util/imgReader${movVO.movPosParam}">				             			                                    	
				                                    </div>
				                                </div> <!-- col-5 End -->
		                                	</c:if>	
		                               	
					                           	<div class="col-7 line-spearate pd-bottom"> <!-- col-7 Start -->
			                                 		<h3><a href="<%=request.getContextPath()%>/movie/mov.do?action=getOne_For_Display&fromFrontend=true&movno=${distinctMovNo}">${movVO.movname}</a></h3>		                                	
		                                 			<c:forEach var="distinctSesDate" items="${sesSvc.getDistinctSesDateBySearchDate(searchDate)}" >
		                                 				<p><fmt:formatDate value="${distinctSesDate}" type="DATE" dateStyle="FULL"/></p>
		                                 				<c:forEach var="sesTimes" items="${sesSvc.getSesTimes(distinctMovNo,distinctSesDate)}" > 
		                                  					<p>
		                                  						<a href="<%=request.getContextPath()%>/front-end/ordMas/TicketOrder.jsp?sesNo=${sesTimes.sesNo}"><fmt:formatDate value="${sesTimes.sesTime}" pattern="HH:mm" type="DATE"/> <i class="fas fa-ticket-alt" style="color:#aa9166;"></i></a> <label>???${sesTimes.theNo}??????</label>		                                  						
		                                  					</p>          				
		                                				</c:forEach>
		                                 			</c:forEach>
			                                 	</div> <!-- col-7 End --> 
		                                	</div> <!-- session-item End --> 
		                               	</c:forEach> 
	                            </c:if> <!-- ????????????????????? End --> 
	                            <!-- ??????????????? End -->                         
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
		?????????????????? ???????????? -> ???????????????+6???(???7?????????)
    ==========================================================*/
	window.onload = function(){
		let daysDuration = 7;
		
		let today = new Date();	
		let today_year = today.getFullYear();
		let today_month = getMonth(today);
		let today_date = getDate(today);	
		let today_day = today.getDay();	
		let show_day = "";
		let date = getDays(today_month,today_year);
		
		for (let i = 0; i < daysDuration; i++){
			
			if( today_day == 7){
				today_day = 0;
			}
			if( today_day == 0){
				show_day = "???";
			}
			if( today_day == 1){
				show_day = "???";
			}
			if( today_day == 2){
				show_day = "???";
			}
			if( today_day == 3){
				show_day = "???";
			}
			if( today_day == 4){
				show_day = "???";
			}
			if( today_day == 5){
				show_day = "???";
			}
			if( today_day == 6){
				show_day = "???";
			}


	        if(today_date < 10){
	        	today_date = '0' + today_date;
	        }
			
 			let sesDateValue = today_year + "-" + today_month + "-" + today_date;
 			 $('#sesDateId').append("<option value=" + "\"" + sesDateValue + "\"" +">" + today_year + "-" + today_month + "-" + today_date + " ??????" + show_day + "</option>");

			today_date++;
			today_day++;
			
			if(today_date > date){
				today_month++;   // ????????????????????????????????????+1
				today_date = 1;  // ??????????????????????????????????????????1???

		        if(today_month < 10){
		        	today_month = '0' + today_month;
		        }
			}
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
	
	// ?????????????????????????????? ??????
    function getDays(month,year){
        let mDays = 31;
        month = parseInt(month);
        year = parseInt(year);
        if(month%2 === 0 && month != 2 && month < 7){
            mDays = 30;
        }
        if(month ===  2){
            if(year%4 === 0 | year%400 === 0){
                mDays = 29;
            }else{
                mDays = 28;
            }
        }
        if(month ===  9){
            mDays = 30;
        }
        if(month%2 != 0 && month > 10){
            mDays = 30;
        }
        return mDays;
    }
</script>
</body>
</html>