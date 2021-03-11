<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Movies</title>
<%@ include file="/front-end/files/frontend_importCss.file"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/movie/frontendMovies.css">

<style>
#movies-flters a:hover,
#movies-flters a:active,
#movies-flters a:focus {
    color: #454545;
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
                            <h2>Movies</h2>
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
                                <li <c:if test="${empty nowShowing && empty commingSoon}">class="filter-active"</c:if> ><a href="${movies}">All</a></li>
                                <li <c:if test="${not empty nowShowing}">class="filter-active"</c:if> ><a href="<%=request.getContextPath()%>/movie/mov.do?action=now_Showing">Now Showing</a></li>
                                <li <c:if test="${not empty commingSoon}">class="filter-active"</c:if> ><a href="<%=request.getContextPath()%>/movie/mov.do?action=comming_Soon">Comming Soon</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="row movies-container">
                    
						<c:if test="${empty nowShowing && empty commingSoon}">	
						<c:forEach var="movVO" items="${movSvc.all}" >
							<c:if test="${not empty movVO.movpos}">						    
		                        <div class="col-lg-4 col-md-6 col-sm-12 movies-item">
		                            <div class="movies-wrap" onclick="sendData(this,${movVO.movno})" style="cursor:pointer;">
		                                <img src="<%=request.getContextPath()%>/movie/mov.do?movno=${movVO.movno}&img=movpos&action=get_One_MovPos" alt="Movies Image">
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
							<%-- <c:if test="${not empty movVO.movpos}">	 --%>					    
		                        <div class="col-lg-4 col-md-6 col-sm-12 movies-item">
		                            <div class="movies-wrap" onclick="sendData(this,${movVO.movno})" style="cursor:pointer;">
		                                <img src="<%=request.getContextPath()%>/movie/mov.do?movno=${movVO.movno}&img=movpos&action=get_One_MovPos" alt="Movies Image">
		                                <figure>
		                                    <p><img style="width:100%; max-width: 40px;" src="<%=request.getContextPath()%>/resource/images/logos/seenema_W.ico" alt="Logo"></p>
		                                    <a href="<%=request.getContextPath()%>/movie/mov.do?action=getOne_For_Display&fromFrontend=true&movno=${movVO.movno}">${movVO.movname}</a>
		                                    <span>${movVO.movondate}</span>
		                                </figure>
		                            </div>
		                        </div>
	                       <%--  </c:if> --%>
						</c:forEach>
	                    </c:if>
	                    
						<!-- Comming Soon -->
						<c:if test="${not empty commingSoon}">	
						<c:forEach var="movVO" items="${commingSoon}" >
							<%-- <c:if test="${not empty movVO.movpos}">		 --%>				    
		                        <div class="col-lg-4 col-md-6 col-sm-12 movies-item">
		                            <div class="movies-wrap" onclick="sendData(this,${movVO.movno})" style="cursor:pointer;">
		                                <img src="<%=request.getContextPath()%>/movie/mov.do?movno=${movVO.movno}&img=movpos&action=get_One_MovPos" alt="Movies Image">
		                                <figure>
		                                    <p><img style="width:100%; max-width: 40px;" src="<%=request.getContextPath()%>/resource/images/logos/seenema_W.ico" alt="Logo"></p>
		                                    <a href="<%=request.getContextPath()%>/movie/mov.do?action=getOne_For_Display&fromFrontend=true&movno=${movVO.movno}">${movVO.movname}</a>
		                                    <span>${movVO.movondate}</span>
		                                </figure>
		                            </div>
		                        </div>
	                        <%-- </c:if> --%>
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