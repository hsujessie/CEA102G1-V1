<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="location" value="${pageContext.request.requestURI}" scope="session"/> <!-- here have to set a session attribute for the login page to get the origin url -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Movies</title>
<%@ include file="/front-end/files/frontend_importCss.file"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/movie/frontendMovies.css">
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


            <!-- Movies Start -->
            <jsp:useBean id="movSvc" scope="page" class="com.movie.model.MovService"/>
            <div class="movies">
                <div class="container">
                    <div class="row">
                        <div class="col-12">
                            <ul id="movies-flters">
                                <li <c:if test="${empty nowShowing && empty commingSoon}">class="filter-active"</c:if> ><a <c:if test="${not empty nowShowing || not empty commingSoon}">class="a-color"</c:if> href="${movies}">All</a></li>
                                <li <c:if test="${not empty nowShowing}">class="filter-active"</c:if> ><a <c:if test="${empty nowShowing}">class="a-color"</c:if> href="<%=request.getContextPath()%>/movie/mov.do?action=now_Showing">????????????</a></li>
                                <li <c:if test="${not empty commingSoon}">class="filter-active"</c:if> ><a <c:if test="${empty commingSoon}">class="a-color"</c:if> href="<%=request.getContextPath()%>/movie/mov.do?action=comming_Soon">????????????</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="row movies-container">
                    
						<c:if test="${empty nowShowing && empty commingSoon}">	
						<c:forEach var="movVO" items="${movSvc.all}" >
						<!-- for ?????? ?????????????????? Start -->
						<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy-MM-dd" var="today" />
			      		<fmt:formatDate value="${movVO.movoffdate}" pattern="yyyy-MM-dd" var="movOffdate" />
			            <!-- for ?????? ?????????????????? End -->
			            	<c:if test="${today lt movOffdate}">
		                        <div class="col-lg-3 col-md-4 col-sm-12 movies-item">
		                            <div class="movies-wrap" onclick="sendData(this,${movVO.movno})" style="cursor:pointer;">		                               
										
										<%-- <c:if test="${not empty movVO.movpos}">						    
		                                	<img src="<%=request.getContextPath()%>/movie/mov.do?movno=${movVO.movno}&img=movpos&action=get_One_MovPos" alt="Movies Image">	 --%>
		                                	<img src="<%=request.getContextPath()%>/util/imgReader${movVO.movPosParam}">	                                
	                        			<%-- </c:if>	
										<c:if test="${empty movVO.movpos}">											
	                                        <img src="<%=request.getContextPath()%>/resource/images/film.jpg" alt="Movie Image">		                                	
                        				</c:if> --%>
                        				
		                                <figure>
		                                    <p><img style="width:100%; max-width: 40px;" src="<%=request.getContextPath()%>/resource/images/logos/seenema_W.ico" alt="Logo"></p>
		                                    <a href="<%=request.getContextPath()%>/movie/mov.do?action=getOne_For_Display&fromFrontend=true&movno=${movVO.movno}">${movVO.movname}</a>
		                                    <span>${movVO.movondate}</span>
		                                </figure>
		                            </div>
		                        </div>
	                    	</c:if>
						</c:forEach>
	                    </c:if>
						
						<!-- Now Showing -->
						<c:if test="${not empty nowShowing}">	
						<c:forEach var="movVO" items="${nowShowing}" >				    
	                        <div class="col-lg-3 col-md-4 col-sm-12 movies-item">
	                            <div class="movies-wrap" onclick="sendData(this,${movVO.movno})" style="cursor:pointer;">
	                                <%-- <c:if test="${not empty movVO.movpos}">
	                                	<img src="<%=request.getContextPath()%>/movie/mov.do?movno=${movVO.movno}&img=movpos&action=get_One_MovPos" alt="Movies Image"> --%>
	                                	<img src="<%=request.getContextPath()%>/util/imgReader${movVO.movPosParam}">
	                                <%-- </c:if>
									<c:if test="${empty movVO.movpos}">											
                                        <img src="<%=request.getContextPath()%>/resource/images/film.jpg" alt="Movie Image">		                                	
                       				</c:if> --%>
                       				
	                                <figure>
	                                    <p><img style="width:100%; max-width: 40px;" src="<%=request.getContextPath()%>/resource/images/logos/seenema_W.ico" alt="Logo"></p>
	                                    <a href="<%=request.getContextPath()%>/movie/mov.do?action=getOne_For_Display&fromFrontend=true&movno=${movVO.movno}">${movVO.movname}</a>
	                                    <span>${movVO.movondate}</span>
	                                </figure>
	                            </div>
	                        </div>
						</c:forEach>
	                    </c:if>
	                    
						<!-- Comming Soon -->
						<c:if test="${not empty commingSoon}">	
						<c:forEach var="movVO" items="${commingSoon}" >				    
		                        <div class="col-lg-3 col-md-4 col-sm-12 movies-item">
		                            <div class="movies-wrap" onclick="sendData(this,${movVO.movno})" style="cursor:pointer;">
		                               <%--  <c:if test="${not empty movVO.movpos}">
		                                	<img src="<%=request.getContextPath()%>/movie/mov.do?movno=${movVO.movno}&img=movpos&action=get_One_MovPos" alt="Movies Image"> --%>
		                                	<img src="<%=request.getContextPath()%>/util/imgReader${movVO.movPosParam}">
		                                <%-- </c:if>
										<c:if test="${empty movVO.movpos}">											
	                                        <img src="<%=request.getContextPath()%>/resource/images/film.jpg" alt="Movie Image">		                                	
	                       				</c:if> --%>
	                       				
		                                <figure>
		                                    <p><img style="width:100%; max-width: 40px;" src="<%=request.getContextPath()%>/resource/images/logos/seenema_W.ico" alt="Logo"></p>
		                                    <a href="<%=request.getContextPath()%>/movie/mov.do?action=getOne_For_Display&fromFrontend=true&movno=${movVO.movno}">${movVO.movname}</a>
		                                    <span>${movVO.movondate}</span>
		                                </figure>
		                            </div>
		                        </div>
						</c:forEach>
	                    </c:if>
                    </div>
                </div>
            </div>
            <!-- Movies End -->
            
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
</script>
</body>
</html>