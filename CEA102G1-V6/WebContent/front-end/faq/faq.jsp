<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.faq.model.*"%>

<jsp:useBean id="faq_typeSvc" scope="page" class="com.faq_type.model.FaqtypService" />
<jsp:useBean id="faqSvc" scope="page" class="com.faq.model.FaqService" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Front-End</title>
<%@ include file="/front-end/files/frontend_importCss.file"%>
<style>
.faqs {
    position: relative;
    width: 100%;
    padding: 45px 0;
}

.faqs .faqs-img {
    position: relative;
    height: 100%;
    padding: 10px;
    background: #121518;
}

.faqs .faqs-img img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

@media(max-width: 767.98px) {
    .faqs .faqs-img {
        margin-bottom: 30px;
        height: auto;
    }
}

.faqs .card {
    margin-bottom: 15px;
    padding-top: 15px;
    border: none;
    border-radius: 0;
    border-top: 1px solid #aa9166;
}

.faqs .card:last-child {
    margin-bottom: 0;
    padding-bottom: 15px;
    border-bottom: 1px solid #aa9166;
}

.faqs .card-header {
    padding: 0;
    border: none;
    background: #ffffff;
}

.faqs .card-header a {
    display: block;
    width: 100%;
    color: #121518;
    font-size: 18px;
    line-height: 40px;
}

.faqs .card-header a span {
    display: inline-block;
    width: 40px;
    height: 40px;
    margin-right: 10px;
    text-align: center;
    background: #121518;
    color: #aa9166;
    font-weight: 700;
}

.faqs .card-header [data-toggle="collapse"]:after {
    font-family: 'font Awesome 5 Free';
    content: "\f067";
    float: right;
    color: #121518;
    font-size: 12px;
    font-weight: 900;
    transition: .3s;
}

.faqs .card-header [data-toggle="collapse"][aria-expanded="true"]:after {
    font-family: 'font Awesome 5 Free';
    content: "\f068";
    float: right;
    color: #121518;
    font-size: 12px;
    font-weight: 900;
    transition: .3s;
}

.faqs .card-body {
    padding: 15px 0 0 0;
    font-size: 16px;
    border: none;
    background: #ffffff;
}

.faqs a.btn {
    position: relative;
    margin-top: 15px;
    padding: 15px 35px;
    font-size: 16px;
    font-weight: 500;
    letter-spacing: 1px;
    text-transform: uppercase;
    color: #aa9166;
    border: 2px solid #aa9166;
    border-radius: 0;
    background: #121518;
    transition: .3s;
}

.faqs a.btn:hover {
    color: #121518;
    background: #aa9166;
}
.section-header {
    position: relative;
    width: 100%;
    text-align: center;
    margin-top:45px;
    margin-bottom: 45px;
}
</style>
</head>
<body>
        <div class="wrapper">
            <!-- Nav Bar Start -->
			<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set>
            <%@ include file="/front-end/files/frontend_navbar.file"%>
            <!-- Nav Bar End -->


            <!-- Page Header Start --> <!-- 看自己需不需要標題 -->
            <div class="page-header">
                <div class="container">
                    <div class="row">
                        <div class="col-12">
                            <h2>FAQs</h2> 
                        </div>
                    </div>
                </div>
            </div>
            <!-- Page Header End -->


            <!-- PUT HERE Start -->
                        <div class="faqs">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                        <c:forEach var="faq_typeVO" items="${faq_typeSvc.all}">
                            <div class="section-header">
                                <h2>${faq_typeVO.faqtyp_name}</h2>
                            </div>
                            <div id="accordion">
 							<c:forEach var="faqVO" items="${faqSvc.all}">
 							<c:if test="${faqVO.faqtyp_no==faq_typeVO.faqtyp_no}">
                                <div class="card">
                                    <div class="card-header">
                                        <a class="card-link collapsed" data-toggle="collapse" href="#collapse${faqVO.faq_no}" aria-expanded="true">
                                            <span>▼</span>${faqVO.faq_question}
                                        </a>
                                    </div>
                                    <div id="collapse${faqVO.faq_no}" class="collapse" data-parent="#accordion">
                                        <div class="card-body">
                                        ${faqVO.faq_answer}
                                        </div>
                                    </div>
                                </div>
 							</c:if>
 							</c:forEach>
                            </div>
                        </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <!-- PUT HERE End -->
            
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