<%@page import="com.member.model.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*" %>
<%@ page import="com.art.model.*" %>
<jsp:useBean id="artSvc" scope="page" class="com.art.model.ArtService" />

<%
if(session.getAttribute("MemberVO") != null){
	MemberVO memberVO = (MemberVO)session.getAttribute("MemberVO");
	session.setAttribute("memNo", memberVO.getMemNo());
	session.getAttribute("memNo");			
}
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
<!-- swiper -->
	<link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css" />
	<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
<!-- 	jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!--     datetimepicker -->
    <link rel="stylesheet" type="text/css"
        href="<%=request.getContextPath()%>/resource/datetimepicker/jquery.datetimepicker.css" />
    <script src="<%=request.getContextPath()%>/resource/datetimepicker/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/resource/datetimepicker/jquery.datetimepicker.full.js"></script>
    <!-- bootstrap -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous">
    </script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <%@ include file="/front-end/files/frontend_importCss.file"%>
    <!-- toastr v2.1.4 -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css" rel="stylesheet" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>
<!--     moment -->
    <script src="<%=request.getContextPath()%>/resource/js/moment-with-locales.min.js" ></script>
<!-- 	fontawesome -->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.2/css/all.css" integrity="sha384-vSIIfh2YWi9wW0r9iZe7RJPrKwp6bG+s9QZMoITbCckVJqGCCRhc+ccxNcdpHuYu" crossorigin="anonymous">
<!--     fronend_importJs -->
	<script src="<%=request.getContextPath()%>/resource/easing/easing.min.js"></script>
	<script src="<%=request.getContextPath()%>/resource/owlcarousel/owl.carousel.min.js"></script>
	<script src="<%=request.getContextPath()%>/resource/js/frontend.js"></script>

	<script>
        toastr.options = {
            // ????????????
            "closeButton": false,
            "debug": false,
            "newestOnTop": false,
            "progressBar": true,
            "positionClass": "toast-top-right",
            "preventDuplicates": false,
            "onclick": null,
            "showDuration": "300",
            "hideDuration": "1000",
            "timeOut": "5000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        }
	</script>
    <style>
        * {
            box-sizing: border-box;
        }
        body {
            margin: 0;
        }
        img {
            max-width: 100%;
        }
        #artListLeft {
            vertical-align: top;
            position: sticky;
            top: 90px;
            overflow-y: auto;
            height: 80vh;

        }
        @media (max-width: 991px) {
            #artListLeft {
                height: auto;
            }
            #artListLeft #newArt {
                width: 54px;
                max-width: 100%;
            }
        }
        #artListLeft a {
            color: #666666;
        }
        #artListLeft a:hover {
            cursor: pointer;
        }
        .selectedMovType {
            background-color: #AA9166;
        }
        #newArt {
            margin: 5px;
            width: 54px;
        }
        @media (max-width: 576px) {
            #newArt {
                width: 90%;
            }
        }
        #artMovTypeList li:hover{
        	cursor: pointer;
        }
        .swiper-container {
            width: 100%;
            padding-top: 50px;
            padding-bottom: 50px;
        }
        .swiper-slide {
            background-position: center;
            background-size: cover;
            width: 300px;
            height: 300px;

        }
        .ellipse{
			background: -webkit-radial-gradient(ellipse,rgba(255, 255, 255, 0),rgba(170, 145, 102, 0.3));
			background: -o-radial-gradient(ellipse,rgba(255, 255, 255, 0),rgba(170, 145, 102, 0.3));
			background: -moz-radial-gradient(ellipse,rgba(255, 255, 255, 0),rgba(170, 145, 102, 0.3));
			background: radial-gradient(ellipse,white,rgba(255, 255, 255, 0),rgba(170, 145, 102, 0.3));
		}
		.authorIMG{
			width: 2.7vw;
			height: 5vh;
			border-radius: 50%;
			margin: 4%;
		}
		.repAuthorIMG{
			width: 2.7vw;
			height: 5vh;
			border-radius: 50%;
		}
		.form-control {
		    display: block;
		    width: 100%;
		    height: calc(1.5em + .75rem + 2px);
		    padding: 1.21rem .75rem;
		    font-size: 1rem;
		    font-weight: 400;
		    line-height: 1.5;
		    color: #495057;
		    background-color: #fff;
		    background-clip: padding-box;
		    border: 2px solid #AA9166;
		    border-radius: .25rem;
		    transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
		}
		#artRepButton:hover{
			cursor: pointer;
			color: #AA9166;
		}
    </style>

    <script>
//     window.load
        $(document).ready(function () {
            //???????????????????????????
            showArtMoveType();
            
            //????????????????????????
            ListArtQuery();
            
            //???????????????????????????
            ListArtTopThreeQuery();

            //??????????????????
            $('#artListCenter ,#Top3Article').on('click', '.artContent', function (event) {
                console.log("artContent clicked:" + $(event.currentTarget).html());
                console.log("artNo:" + $(event.currentTarget).attr('data-value'));
                // 		debugger;
                $.ajax({
                    type: 'POST',
                    url: '<%=request.getContextPath()%>/art/art.do',
                    data: {
                        'action': 'art_Show_By_AJAX',
                        'artNo': $(event.currentTarget).attr('data-value')
                    },
                    dataType: 'json',
                    success: function (artVO) {
                        debugger;
                        console.log('#basicModal' + document.getElementById('#basicModal'));
                        //????????????
                        $('#basicModal').modal('show');

                        //?????????????????????????????????
                        $(artVO).each(function (i, item) {
                            //???include ArticleContent.jsp??????id
                            console.log(item.artTitle);
                            clearOneArticle();
                            clearArtReplyno();
                            $('#myModalLabel').attr('data-value', item.artNo);
                            $('#myModalLabel').append(item.artTitle);
                            $('#oneArtContent').append('<p>' + item.artContent +'</p>');
                            $('#artFav_header_like').attr('data-value', item.artNo);
                            $('#artRptButton').attr('data-value', item.artNo);
                            $('#artReplyno').append('???????????? '+item.artReplyno);
                            console.log("item.memNo:" + item.memNo);

                            //??????????????????????????????????????????
                            if (item.memNo == '${memNo}' && '${MemberVO}' != "") {
                                $('#memUpdateArt').show();
                                $('#artUpdateMemNo').val(item.memNo);
                                $('#artUpdateArtNo').val(item.artNo);
                            } else {
                                $('#memUpdateArt').hide();
                            }

        					//?????????????????????
        					debugger;
        					if('${MemberVO}' != ""){
        						isArtFav();
        					}

                            //?????????????????????
                            debugger;
                            listAllArtRepByArtNo();
                        });
                        console.log("success");
                        console.log("${memNo}");

                    },
                    error: function () {
                        console.log("AJAX-ready???????????????!")
                    }
                });
            });

            //??????????????????
            $('#findArtByTitleButton ,#findArtByCompositeQueryButton, #artTitleByCompositeQuery, #artTimeForByCompositeQuery, #artAuthorForByCompositeQuery').on('click keypress', function (e) {
                    //  debugger;
                    if (e.which === 13 || e.currentTarget.id === 'findArtByCompositeQueryButton' || e.currentTarget.id === 'findArtByTitleButton') {
                    	findArtByCompositeQuery(e);
                    }
            });

            //?????????????????????
            $('#artMovTypeList').on('click', 'li', function (e) {
                $('.selectedMovType').removeClass('selectedMovType');
                document.getElementById("artTitleByCompositeQuery").placeholder = '???' + $(this).data(
                    'value') + '??????';
              	//????????????????????????
    			clearListArtTopThreeQuery();
              	
                clearArtCompositeQuery();
                $(this).addClass('selectedMovType');
                findArtByCompositeQuery(e);
            });
            
            //?????????????????????
            $('#returnArticle').click(function(){
					window.location.reload("<%=request.getContextPath()%>/front-end/article/article.jsp");
            });
            
            //????????????
            $.datetimepicker.setLocale('zh');
            $('#artTimeForByCompositeQuery').datetimepicker({
                theme: '', //theme: 'dark',
                timepicker: false, //timepicker:true,
                format: 'Y-m-d', //format:'Y-m-d H:i:s',
                value: '', // value:   new Date(),
            });

            //??????????????????
            if ('${updateSuccess}' == 'updateSuccess') {
                //      	debugger;
                <%
                session.removeAttribute("updateSuccess"); 
                %>
                toastr['success']('??????????????????', '??????'); 
            }

            //??????????????????
            if ('${addSuccess}' == 'addSuccess') {
                //      	debugger;
                <%
                session.removeAttribute("addSuccess"); 
                %>
                toastr['success']('??????????????????', '??????'); 
            }

            //????????????
            changeArtFav();

            //????????????
            addArtRpt();

            //??????????????????
            addArtRep();

            //??????????????????
            addRepRpt();
            
            //????????????????????????
            clearArtRepEmptyAlert();
            
//          Initialize Swiper
        	var swiper = new Swiper('.swiper-container', {
        	  	effect: 'coverflow',
        	    grabCursor: true,
        	    centeredSlides: true,
        	    slidesPerView: 'auto',
        	    coverflowEffect: {
        	        rotate: 50,
        	        stretch: 0,
        	        depth: 100,
        	        modifier: 1,
        	        slideShadows: true,
        	    },
        	    autoplay: {
        	        delay: 2500,
        	        disableOnInteraction: false,
        	    },
        	    pagination: {
        	        el: '.swiper-pagination',
        	    },
        	    observer:true,//??????swiper???????????????????????????????????????swiper
                observeParents:true//??????swiper?????????????????????????????????swiper
        	 });
        	
        });
// window load
        
        //?????????????????????
        function showArtMoveType() {
            $.ajax({
                type: 'POST',
                url: '<%=request.getContextPath()%>/art/art.do',
                data: {
                    'action': 'find_MoveType_By_AJAX'
                },
                dataType: 'json',
                success: function (artVO) {
                    clearArtMoveList();
                    // 			debugger;
                    $(artVO).each(function (i, item) {
                        $('#artMovTypeList').append(
                            '<li id="' + item.movTypeIndex + '" class="nav-item" data-value="' +
                            item.artMovType + '"><a class="nav-link">' + item
                            .artMovType + '</a></li>'
                        );
                    });
                },
                error: function () {
                    console.log("AJAX-showArtMoveType???????????????!")
                }
            });
        };

        //??????????????????
        function findArtByCompositeQuery(e) {
            	debugger;
            $.ajax({
                type: 'post',
                url: '<%=request.getContextPath()%>/art/art.do',
                data: addCompositeQueryData(e),
                dataType: 'json',
                success: function (artVO) {
                    clearArtList();
                    $(artVO).each(function (i, item) {
                        $('#artListCenter').append(
                            '<div class="oneArtDiv"><div id="artAuthor" style="display: inline-block"><div style="display: inline-block"><img class="authorIMG" src="<%=request.getContextPath()%>/util/imgReader?columnName=MEM_IMG&tableName=member&fieldName=MEM_NO&fieldValue='+item.memNo+'"></div> <div style="display: inline-block">' +
                            item.memName + '</div></div>' +
                            '<div id="movType" style="display: inline-block"><div style="display: inline-block">???????????????</div> <div style="display: inline-block">' +
                            item.artMovType + '</div></div>' +
                            '<div id="artTitle"><div style="font-size: 1.2rem;"><b>' + item
                            .artTitle + '</b></div></div>' +
                            '<div id="artTime"><div style="display: inline-block">???????????????</div> <div style="display: inline-block">' +
                            moment(item.artTime).locale('zh_TW').format('llll') +
                            '</div></div>' +
                            '<div><div class="artContent" data-value="' + item.artNo + '">' +
                            item.artContent + '</div></div></div>');
                        $('#artListCenter .artContent').css({
                            'height': '10vh',
                            'white-space': 'nowrap',
                            'overflow': 'hidden',
                            'text-overflow': 'ellipsis',
                            'cursor': 'pointer'
                        });
                    });
                    clearListArtTopThreeQuery();
                    ListArtTopThreeQuery();
        			//????????????
        			if (artVO.length == 0){
        				$('#artListCenter').append('<div class="noArticle">????????????</div>');
        			}
                },
                error: function () {
                    console.log('AJAX-findArtByCompositeQuery???????????????!')
                }
            });
        };

        //????????????data
        function addCompositeQueryData(e) {
            var addArtDataAttr = {
                'action': 'find_By_CompositeQuery_Use_AJAX'
            };
            addArtDataAttr['artMovType'] = $('.selectedMovType').attr('data-value');
            console.log('artMovType add ' + addArtDataAttr);
            // 	debugger;
            if ($('#artTitleByCompositeQuery').val() != null) {
                addArtDataAttr['artTitle'] = $('#artTitleByCompositeQuery').val();
                console.log("artTitle add ");
            }
            // 	debugger;
            if ($('#artTimeForByCompositeQuery').val() != null) {
                addArtDataAttr['artTime'] = $('#artTimeForByCompositeQuery').val();
                console.log("artTime add " + addArtDataAttr);
            }
            // 	debugger;
            if ($('#artAuthorForByCompositeQuery').val() != null) {
                addArtDataAttr['artAuthor'] = $('#artAuthorForByCompositeQuery').val();
                console.log("artAuthor add " + addArtDataAttr);
            }

            console.log("addCompositeQueryData:" + addArtDataAttr);
            return addArtDataAttr;
        };
        
        //?????????????????????????????????
        function movTypeHotArticle(data){
        	$.ajax({
        		type: 'POST',
        		url: '<%=request.getContextPath()%>/art/art.do',
        		data: {'action':'movTypeHotArticle', 'movType':data},
        		dataType: 'json',
        		success: function (artVO){
        			//????????????????????????
        			clearListArtTopThreeQuery();
        			
        			//??????????????????
        			$(artVO).each(function(i, item){
        				$('#Top3Article').append(
        						'<div id="artAuthor" style="display: inline-block"><div style="display: inline-block">?????????</div> <div style="display: inline-block">'+item.memName+'</div></div>'
        						+'<div id="movType" style="display: inline-block"><div style="display: inline-block">???????????????</div> <div style="display: inline-block">'+item.artMovType+'</div></div>'
        						+'<div id="topThreeArticle" style="display: inline-block; color: #FF7575;"><i class="fas fa-crown" style="color: #bb9d52; margin: 0px 5px;"></i><b>HOT</b></div>'
        						+'<div id="artTitle"><div style="font-size: 1.2rem;"><b>'+item.artTitle+'</b></div></div>'
        						+'<div id="artTime"><div style="display: inline-block">???????????????</div> <div style="display: inline-block">'+moment(item.artTime).locale('zh_TW').format('llll')+'</div></div>'
        						+'<div><div class="artContent" data-value="'+item.artNo+'">'+item.artContent+'</div></div><hr>')			
        						;
        				$('#Top3Article').addClass('HotArticleDiv');
        				});
        		},
        		error: function(){console.log("AJAX-ListArtTopThreeQuery???????????????!")}
        	});
        };

        //??????????????????
        function clearArtList() {
            $('#artListCenter').empty();
        };

        //????????????????????????
        function clearArtMoveList() {
            $('#artMovTypeList').empty();
        };

        //??????????????????
        function clearArtCompositeQuery() {
            $('#artAuthorForByCompositeQuery').val("");
            $('#artTimeForByCompositeQuery').val("");
            $('#artTitleByCompositeQuery').val("");
        };
        
        //????????????????????????
        function clearListArtTopThreeQuery(){
        	$('#Top3Article').empty();
        	$('#Top3Article').removeClass('HotArticleDiv');
        }
    </script>
    <title>SEENEMA ARTICLE</title>
</head>

<body>
	<div class="wrapper">
		<!-- Nav Bar Start -->
		<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set>
	    <%@ include file="/front-end/files/frontend_navbar.file"%>
	    <!-- Nav Bar End -->


	    <div id="articleTop" class="container">
	    
	        <div class="row">
	            <div class="col-12 col-lg-3">
	                <!-- ??????????????? -->
	                <div id="artListLeft">
	                    <!-- ???????????? -->
	                    <c:if test="${not empty errorMsgs}">
	                        <font style="color:red"></font>
	                        <ul>
	                            <c:forEach var="message" items="${errorMsgs}">
	                                <li style="color:red">${message}</li>
	                            </c:forEach>
	                        </ul>
	                    </c:if>
	                    <nav class="navbar navbar-expand-lg navbar-light" style="padding: 5px;">
	                        <button class="navbar-toggler" type="button" data-toggle="collapse"
	                            data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false"
	                            aria-label="Toggle navigation">
	                            <span class="navbar-toggler-icon"></span>
	                        </button>
	                        <div class="collapse navbar-collapse" id="navbarNavDropdown">
	                            <ul class="nav flex-column">
	                                <div id="artListLeftTop">
	                                    <li>
	                                        <!-- ???????????? -->
	                                        <form method="post" action="<%=request.getContextPath()%>/art/art.do">
	                                            <input type="hidden" name="action" value="newArt">
	                                            <label>
	                                                <div id="newArtDiv">
	                                                    <input type="image" id="newArt"
	                                                        src="<%=request.getContextPath()%>/resource/images/newArtIcon.png"
	                                                        alt="??????" title="??????">
	                                                </div>
	                                            </label>
	                                        </form>
	                                    </li>
	                                    <li>
	                                        <div class="input-group mb-3">
	                                            <input type="text" id="artTitleByCompositeQuery" class="form-control"
	                                                placeholder="????????????" aria-label="Recipient's username"
	                                                aria-describedby="findArtByTitleButton">
	                                            <div class="input-group-append">
	                                                <button class="btn combtn" type="button"
	                                                    id="findArtByTitleButton">??????</button>
	                                            </div>
	                                        </div>
	                                    </li>
	                                    <li>
	                                        <hr>
	                                    </li>
	                                    <li class="nav-item active ">
	                                        <a id="returnArticle" class="nav-link">???????????????<span class="sr-only">(current)</span></a>
	                                    </li>
	                                    <li>
	                                        <hr>
	                                    </li>                                    
	                                </div>
	                                <div id="artListLeftDown">
	                                    <li class="nav-item" style="font-size: 0.8em;">
	                                        	????????????
	                                    </li>
	                                    <div id="artMovTypeList">
	                                        <li class="nav-item">
	                                            <a class="nav-link" href="#">????????????</a>
	                                        </li>
	                                    </div>
	                                    <li>
	                                        <hr>
	                                    </li>
	                                    <li>
										    <div class="accordion" id="accordionExample">
										        <div class="card" style="border-radius: 0; border: 2px #AA9166">
										          <div class="card-header combtn" id="headingOne">
										            <h2 class="mb-0">
										              <button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne" style=" color: #AA9166; text-decoration: none;">
										                ????????????
										              </button>
										            </h2>
										          </div>
										      
										          <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionExample">
										            <div class="card-body" style="border-radius: 0; border: 2px #AA9166">
										                <table>
										                    <tr>
										                        <td><input type="text" id="artAuthorForByCompositeQuery"
										                                class="form-control" name="artAuthor" placeholder="????????????"></td>
										                    </tr>
										                    <tr>
										                        <td><input type="text" id="artTimeForByCompositeQuery"
										                                class="form-control" name="artTime" placeholder="??????????????????"></td>
										                    </tr>
										                    <tr>
										                        <td><input id="findArtByCompositeQueryButton"
										                                class="btn combtn" type="button"
										                                value="??????"></td>
										                    </tr>
										                </table>
										            </div>
										          </div>
										        </div>
										    </div>
	                                    </li>
	                                </div>
	                            </ul>
	                        </div>
	                    </nav>
	                </div>
	                <!-- ??????????????? -->
	            </div>
	            <div class="col-12 col-lg-9">
	                <!-- ?????????????????? -->
	                    <!-- ====================include ListArtQuery.jsp==================== -->
	                    <jsp:include page="/front-end/article/ListArtQuery.jsp"></jsp:include>
	                    <!-- ====================include ListArtQuery.jsp==================== -->
	                <!-- ?????????????????? -->
	            </div>
	        </div>
	    </div>
	    
	    <!-- Book Tickets Start -->
	    <%@ include file="/front-end/files/frontend_bookTicketsTamplate.file"%>
	    <!-- Book Tickets End -->
	
	    <!-- Footer Start -->
	    <%@ include file="/front-end/files/frontend_footer.file"%>
	    <!-- Footer End -->
	</div>
	<script src="<%=request.getContextPath()%>/resource/easing/easing.min.js"></script>
	<script src="<%=request.getContextPath()%>/resource/owlcarousel/owl.carousel.min.js"></script>
	<script src="<%=request.getContextPath()%>/resource/js/frontend.js"></script>
</html>