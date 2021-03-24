<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.member.model.*" %>

<c:set var="location" value="${pageContext.request.requestURI}" scope="session"/> <!-- set a session attribute for the login page to get the origin url -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SEENEMA</title>
<%@ include file="/front-end/files/frontend_importCss.file"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/frontendIndex.css">

<%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/chatbox/chatbox.css"> --%>


<style>
.art-box {
	width: 230px;
}
.art-content {
	overflow:hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 5;
	-webkit-box-orient: vertical;
	white-space: normal;
}
</style>

</head>
<body>

        <div class="wrapper">
            <!-- Nav Bar Start -->
			<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set>
            <%@ include file="/front-end/files/frontend_navbar.file"%>
            <!-- Nav Bar End -->

            <!-- Carousel Start -->
            <div id="carousel" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#carousel" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel" data-slide-to="1"></li>
                    <li data-target="#carousel" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img src="<%=request.getContextPath()%>/resource/images/carousel-1.jpg" alt="Carousel Image">
                        <div class="carousel-caption">
                            <h1 class="animated fadeInLeft">Welcome to the SEENEMA</h1>
                            <p class="animated fadeInRight">the best cinema in the world...</p>
                        </div>
                    </div>

                    <div class="carousel-item">
                        <img class="filter" src="<%=request.getContextPath()%>/resource/images/carousel-2.jpg" alt="Carousel Image">
                        <div class="carousel-caption">
                            <h1 class="animated fadeInLeft">Enjoy the Films here</h1>
                            <p class="animated fadeInRight">with the people who are important in your life...</p>
                        </div>
                    </div>

                    <div class="carousel-item">
                        <img src="<%=request.getContextPath()%>/resource/images/carousel-3.jpg" alt="Carousel Image">
                        <div class="carousel-caption">
                            <h1 class="animated fadeInLeft">Having a Lovely time</h1>
                            <p class="animated fadeInRight">cherish the memories of the time we spent together...</p>
                        </div>
                    </div>
                </div>

                <a class="carousel-control-prev" href="#carousel" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#carousel" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
            <!-- Carousel End -->

            <!-- Movie Start -->
            <jsp:useBean id="movSvc" scope="page" class="com.movie.model.MovService"/>
            <div class="movie">
                <div class="container">
                    <div class="section-header">
                        <h2>Movies</h2>
                    </div>
                    <div class="owl-carousel movie-carousel">
                        <!-- move content Start -->
						<c:forEach var="movVO" items="${movSvc.all}" >
						<!-- for 判斷 電影是否下檔 Start -->
						<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy-MM-dd" var="today" />
			      		<fmt:formatDate value="${movVO.movoffdate}" pattern="yyyy-MM-dd" var="movOffdate" />
			            <!-- for 判斷 電影是否下檔 End -->
			            	<c:if test="${today lt movOffdate}">
		                        <div class="col-lg-10 col-md-12">
		                            <div class="movie-item">
			                            <div class="movie-img">
											<%-- <c:if test="${not empty movVO.movpos}"> --%>
			                                	<%-- <img src="<%=request.getContextPath()%>/movie/mov.do?movno=${movVO.movno}&img=movpos&action=get_One_MovPos"> --%>
			                                	<img src="<%=request.getContextPath()%>/util/imgReader${movVO.movPosParam}">
	  <%--                       				</c:if>
											<c:if test="${empty movVO.movpos}">											
		                                        <a href="<%=request.getContextPath()%>/movie/mov.do?action=getOne_For_Display&requestURL=<%=request.getServletPath()%>&movno=${movVO.movno}&fromFrontend=true"><img src="<%=request.getContextPath()%>/resource/images/film.jpg"></a>			                                	
	                        				</c:if> --%>
	                        				
	                        			</div>
		                                <div class="movie-text">
		                                    <h2>${movVO.movrating}</h2>
		                                    <p>${movVO.movondate}</p>
		                                    <div class="movie-social">
		                                        <a href="<%=request.getContextPath()%>/movie/mov.do?action=getOne_For_Display&requestURL=<%=request.getServletPath()%>&movno=${movVO.movno}&fromFrontend=true">${movVO.movname}</a>
		                                    </div>
		                                </div>
		                            </div>
		                        </div>
	                    	</c:if>
						</c:forEach>
                        <!-- move content End -->
                    </div>
                </div>
            </div>
            <!-- Movie End -->

            <!-- About Start -->
            <div class="about">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-lg-5 col-md-6">
                            <div class="about-img">
                                <img src="<%=request.getContextPath()%>/resource/images/about.png" alt="About Image">
                            </div>
                        </div>
                        <div class="col-lg-7 col-md-6">
                            <div class="section-header">
                                <h2>Seenema</h2>
                            </div>
                            <div class="about-text">
                                <p>到電影院看場電影，是現代人不可或缺的休閒娛樂之一。</p>
                                <p style="margin-top: 24px;">SEENEMA影城，提供網路訂票功能，使用者不必到現場排隊購票，不只省時又便利，且使用者也能夠隨時隨地瀏覽近期有哪些上映或即將上映的電影。</p>
                                <p style="margin-top: 24px;">除此之外，SEENEMA也提供了一個平台，讓使用者不需要再透過其他網站就能瀏覽或參與電影討論，或尋找志同道合的夥伴一同觀看電影。</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- About End -->

            <!-- Forum Start -->
            <jsp:useBean id="artSvc" scope="page" class="com.art.model.ArtService"/>
            <div class="forum">
                <div class="container">
                    <div class="section-header">
                        <h2>Forum</h2>
                    </div>
                    <div class="owl-carousel forum-carousel">
                    
						<c:forEach var="artVO" items="${artSvc.all}" >
	                        <div class="forum-item">
	                            <h3>${artVO.artTitle}</h3>
	                            <div class="meta">
	                                <i class="fas fa-user-edit"></i>																				
									<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService"/>
									<c:set value="${memSvc.getOneMember(artVO.memNo)}" var="memObj"></c:set>
	                                <p>${memObj.memName}</p>
	                                <i class="fa fa-calendar-alt"></i>
	                                <p><fmt:formatDate value="${artVO.artTime}" type="DATE" pattern="yyyy-MM-dd"/></p>
	                            </div>
	                            <div class="art-box">
	                            	<p class="art-content">${artVO.artContent}</p>
	                            </div>
	                            <a class="btn" href="<%=request.getContextPath()%>/front-end/article/article.jsp">Read More <i class="fa fa-angle-right"></i></a>
	                        </div>
                        </c:forEach>
                        
                    </div>
                </div>
            </div>
            <!-- Forum End -->


            <!-- Book Tickets Start -->
            <%@ include file="/front-end/files/frontend_bookTicketsTamplate.file"%>
            <!-- Book Tickets End -->

            <!-- Footer Start -->
            <%@ include file="/front-end/files/frontend_footer.file"%>
            <!-- Footer End -->
        </div>
<%@ include file="/front-end/files/frontend_importJs.file"%>
	
</body>
</html>