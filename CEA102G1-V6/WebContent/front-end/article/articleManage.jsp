<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.member.model.MemberVO"%>

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
	<%@ include file="/front-end/files/frontend_importCss.file"%>
<!-- 	fontawesome -->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.2/css/all.css" integrity="sha384-vSIIfh2YWi9wW0r9iZe7RJPrKwp6bG+s9QZMoITbCckVJqGCCRhc+ccxNcdpHuYu" crossorigin="anonymous">
    <!-- toastr v2.1.4 -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css" rel="stylesheet" />
<style>	
	.topDiv{
		margin: 0 auto 5vh auto;
    	width: 17vw;
    	height: 10vh;
    	display: inline-block;
  	}
  	.divWidth{
		width: 40vw;
	}
	.divHeight{
		height: 5vh;
	}
	.modal-dialog {
	    position: relative;
	    width: auto;
	    margin: auto;
	    pointer-events: none;
	}
	@media (min-width: 576px){
		.modal-dialog {
		    max-width: 575px;
		    margin: auto;
		}	
	}
    .modal-dialog-scrollable .modal-content {
	     max-height: 100vh;
	     overflow: hidden;
	     margin: 0px;
	}
    @media (min-width: 576px){
		.modal-dialog-scrollable .modal-content {
	     	max-height: 100vh;
		}    
    }
    .nav-pills .nav-link.active,.nav-pills .show>.nav-link {
    	 background-color: #aa9166;
         color: #000;
    }

    a {
         color: #000;
    }

    a:hover {
         color: #000;
    }
    .artContent img{
		width: 10%;
		height: 10%;
		display: block;
    	margin-left: auto;
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
</style>
<title>Article Manage</title>

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
                            <h2>Article Manage</h2> 
                        </div>
                    </div>
                </div>
            </div>
            <!-- Page Header End -->

            <!-- PUT HERE Start -->
            <div class="container">
			    <div class="row">
			        <div class="col-3">
			            <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
			                <a class="nav-link active" id="v-pills-article-tab" data-toggle="pill" href="#v-pills-article"
			                    role="tab" aria-controls="v-pills-article" aria-selected="true">????????????</a>
			                <a class="nav-link" id="v-pills-artFav-tab" data-toggle="pill" href="#v-pills-artFav" role="tab"
			                    aria-controls="v-pills-artFav" aria-selected="false">????????????</a>
			                <a class="nav-link" id="v-pills-artRep-tab" data-toggle="pill" href="#v-pills-artRep" role="tab"
			                    aria-controls="v-pills-artRep" aria-selected="false">????????????</a>
			            </div>
			        </div>
			        <div class="col-9">
			            <div class="tab-content" id="v-pills-tabContent">
			                <div class="tab-pane fade show active" id="v-pills-article" role="tabpanel"
			                    aria-labelledby="v-pills-article-tab">
			                    <!-- ???????????? -->
			                    <div class="topDiv">
			                        <div class="input-group mb-3">
			                            <input type="text" id="artTitleByCompositeQuery" class="form-control" placeholder="??????????????????"
			                                aria-label="Recipient's username" aria-describedby="findArtByTitleButton">
			                            <div class="input-group-append">
			                                <button class="btn combtn" type="button"
			                                    id="findArtByTitleButton">??????</button>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="topDiv">
			                        <div class="input-group mb-3">
			                            <input type="date" id="artTimeForByCompositeQuery" class="form-control" placeholder="???????????????"
			                                aria-label="Recipient's username" aria-describedby="findArtByTimeButton">
			                            <div class="input-group-append">
			                                <button class="btn combtn" type="button"
			                                    id="findArtByTimeButton">??????</button>
			                            </div>
			                        </div>
			                    </div>
				                    <div id="articleItems" class="topDiv">
				                    </div>
			                    <div id="artListCenter"></div>
			                </div>
			                <div class="tab-pane fade" id="v-pills-artFav" role="tabpanel" aria-labelledby="v-pills-artFav-tab">
			                    <!-- ???????????? -->
			                    <div class="topDiv">
			                        <div class="input-group mb-3">
			                            <input type="text" id="artFavTitleByCompositeQuery" class="form-control"
			                                placeholder="????????????????????????" aria-label="Recipient's username"
			                                aria-describedby="findArtFavTitleByButton">
			                            <div class="input-group-append">
			                                <button class="btn combtn" type="button"
			                                    id="findArtFavTitleByButton">??????</button>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="topDiv">
			                        <div class="input-group mb-3">
			                            <input type="date" id="artFavTimeByCompositeQuery" class="form-control" placeholder="???????????????"
			                                aria-label="Recipient's username" aria-describedby="findArtFavTimeByButton">
			                            <div class="input-group-append">
			                                <button class="btn combtn" type="button"
			                                    id="findArtFavTimeByButton">??????</button>
			                            </div>
			                        </div>
			                    </div>
				                <div id="artFavItems" class="topDiv">
				                </div>			                    
			                    <div id="artFavListCenter"></div>
			                </div>
			                <div class="tab-pane fade" id="v-pills-artRep" role="tabpanel" aria-labelledby="v-pills-artRep-tab">
			                    <!-- ???????????? -->
			                    <div class="topDiv">
			                        <div class="input-group mb-3">
			                            <input type="text" id="artRepTitleByCompositeQuery" class="form-control"
			                                placeholder="????????????????????????" aria-label="Recipient's username"
			                                aria-describedby="findArtRepTitleByButton">
			                            <div class="input-group-append">
			                                <button class="btn combtn" type="button"
			                                    id="findArtRepTitleByButton">??????</button>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="topDiv">
			                        <div class="input-group mb-3">
			                            <input type="date" id="artRepTimeByCompositeQuery" class="form-control" placeholder="???????????????"
			                                aria-label="Recipient's username" aria-describedby="findArtRepTimeByButton">
			                            <div class="input-group-append">
			                                <button class="btn combtn" type="button"
			                                    id="findArtRepTimeByButton">??????</button>
			                            </div>
			                        </div>
			                    </div>
			                    <div id="artRepItems" class="topDiv">
				                </div>
			                    <div id="artRepListCenter"></div>
			                </div>
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
        
            <!-- ???????????? -->
	    <div class="modal fade" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	        <div class="modal-dialog modal-dialog-scrollable" role="document">
	            <div class="modal-content" style="height: 100vh">
	                <div id="art_modal-header" class="modal-header">
	                    <div class="modal-title col-md-11">
	                        <h3 id="myModalLabel">??????????????????</h3>
	                    </div>
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                </div>
	                <div class="modal-body">
	                    <div id="art_modal_body" data-value="">
	                        <!-- ???????????? -->
	                        <div id="oneArtContent">
	                        </div>
	                    </div>
	                </div>
	
	            </div>
	        </div>
	    </div>
	    <!-- ???????????? -->

        
<%@ include file="/front-end/files/frontend_importJs.file"%>

<!-- 	moment -->
	<script src="<%=request.getContextPath()%>/resource/js/moment-with-locales.min.js" ></script>
    <!-- toastr v2.1.4 -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>
    <script>
        toastr.options = {
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
 
<script>
//??????????????????
function clearArtList() {
    $('#artListCenter').empty();
};

//??????????????????
function clearArtFavList(){
	$('#artFavListCenter').empty();
}

//??????????????????
function clearArtRepList(){
	$('#artRepListCenter').empty();
}

//????????????
function clearOneArticle(){
	$('#oneArtContent').empty();
	$('#myModalLabel').empty();
};

//??????????????????data
function addCompositeQueryData(e) {
  var addArtDataAttr = {
      'action': 'find_By_CompositeQuery_Use_AJAX', 'memNo':'${MemberVO.memNo}'
  };
  	debugger;
  if ($('#artTitleByCompositeQuery').val() != null) {
      addArtDataAttr['artTitle'] = $('#artTitleByCompositeQuery').val();
  }
//   	debugger;
  if ($('#artTimeForByCompositeQuery').val() != null || $('#artTimeForByCompositeQuery').val() != 'null') {
      addArtDataAttr['artTime'] = $('#artTimeForByCompositeQuery').val();
  }
  return addArtDataAttr;
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
            clearArtFavList();
            clearArtRepList();
            $(artVO).each(function (i, item) {
                 debugger;
                $('#artListCenter').append(
                    '<div id="movType" class="divWidth divHeight" style="display: inline-block"><div style="display: inline-block">???????????????</div> <div style="display: inline-block">' +
                    item.artMovType + '</div></div>' +
                   	'<div id="updateArticleDiv" style="display: inline-block"><form method="POST" action="<%= request.getContextPath()%>/art/art.do"><input type="hidden" class="artUpdateMemNo" name="memNo" value="${MemberVO.memNo}"><input type="hidden" class="artUpdateArtNo" name="artNo" value="'+item.artNo+'"><input type="hidden" name="action" value="select_Upadte_One_Art"><input type="hidden" class="artManageUpdate" name="artManageUpdate" value="true"><input type="submit" class="updateArticleButton combtn" value="????????????"></form></div>' +
                    '<div id="artTitle"><div class="divHeight" style="font-size: 1.2rem;"><b>' + item.artTitle + '</b></div></div>' +
                    '<div id="artTime"><div class="divHeight" style="display: inline-block">???????????????</div> <div class="divHeight" style="display: inline-block">' +
                    moment(item.artTime).locale('zh_TW').format('llll') +
                    '</div></div>' +
                    '<div><div class="artContent" data-value="' + item.artNo + '">' +
                    item.artContent + '</div></div><hr>');
                $('#artListCenter .artContent').css({
                    'height': '10vh',
                    'white-space': 'nowrap',
                    'overflow': 'hidden',
                    'text-overflow': 'ellipsis',
                    'cursor': 'pointer'
                });
            });
            $('#articleItems').html('<div class="inlineBlockDIV" style="text-align: center!important;"><b>???<font style="color:#bb9d52;">'+artVO.length+'</font>???</b></div>');
        },
        error: function () {
            console.log('AJAX-findArtByCompositeQuery???????????????!')
        }
    });
};


//??????????????????data
function addFavCompositeQueryData(e) {
	var addArtDataAttr = {
	    'action': 'find_By_CompositeQuery_Use_AJAX', 'memNo':'${MemberVO.memNo}'
	};
		debugger;
	if ($('#artFavTitleByCompositeQuery').val() != null) {
	    addArtDataAttr['artTitle'] = $('#artFavTitleByCompositeQuery').val();
	}
	// 	debugger;
	if ($('#artFavTimeByCompositeQuery').val() != null || $('#artFavTimeByCompositeQuery').val() != 'null') {
	    addArtDataAttr['artFavTime'] = $('#artFavTimeByCompositeQuery').val();
	}
	return addArtDataAttr;
};

//??????????????????
function findArtFavByCompositeQuery(e) {
  	debugger;
  $.ajax({
      type: 'post',
      url: '<%=request.getContextPath()%>/art/artFav.do',
      data: addFavCompositeQueryData(e),
      dataType: 'json',
      success: function (artFavVO) {
    	  clearArtList();
    	  clearArtFavList();
    	  clearArtRepList();
          $(artFavVO).each(function (i, item) {
               debugger;
              $('#artFavListCenter').append(
                  '<div id="movType" class="divWidth divHeight" style="display: inline-block"><div style="display: inline-block">???????????????</div> <div style="display: inline-block">' +
                  item.artMovType + '</div></div>' +
                  '<div id="artLike" style="display: inline-block"><button class="artFavButton combtn" data-value="'+item.artNo+'" title="????????????">????????????</button></div>' +
                  '<div id="artTitle"><div class="divHeight" style="font-size: 1.2rem;"><b>' + item.artTitle + '</b></div></div>' +
                  '<div id="artFavTime"><div class="divHeight" style="display: inline-block">?????????????????????</div> <div class="divHeight" style="display: inline-block">' +
                  moment(item.artFavTime).locale('zh_TW').format('llll') +
                  '</div></div>' +
                  '<div><div class="artContent" data-value="' + item.artNo + '">' +
                  item.artContent + '</div></div><hr>');
              $('#artFavListCenter .artContent').css({
                  'height': '10vh',
                  'white-space': 'nowrap',
                  'overflow': 'hidden',
                  'text-overflow': 'ellipsis',
                  'cursor': 'pointer'
              });
          });
          $('#artFavItems').html('<div class="inlineBlockDIV" style="text-align: center!important;"><b>???<font style="color:#bb9d52;">'+artFavVO.length+'</font>???</b></div>');
      },
      error: function () {
          console.log('AJAX-findArtByCompositeQuery???????????????!')
      }
  });
};


//??????????????????data
function addRepCompositeQueryData(e) {
	var addArtDataAttr = {
	    'action': 'find_By_CompositeQuery_Use_AJAX', 'memNo':'${MemberVO.memNo}'
	};
		debugger;
	if ($('#artRepTitleByCompositeQuery').val() != null) {
	    addArtDataAttr['artTitle'] = $('#artRepTitleByCompositeQuery').val();
	}
	// 	debugger;
	if ($('#artRepTimeByCompositeQuery').val() != null || $('#artRepTimeByCompositeQuery').val() != 'null') {
	    addArtDataAttr['artRepTime'] = $('#artRepTimeByCompositeQuery').val();
	}
	return addArtDataAttr;
};

//??????????????????
function findArtRepByCompositeQuery(e) {
	debugger;
	$.ajax({
	    type: 'post',
	    url: '<%=request.getContextPath()%>/art/artRep.do',
	    data: addRepCompositeQueryData(e),
	    dataType: 'json',
	    success: function (artRepVO) {
	    	clearArtList();
	    	clearArtFavList();
	    	clearArtRepList();
	        $(artRepVO).each(function (i, item) {
	             debugger;
	            $('#artRepListCenter').append(
		            '<div id="artTitle"><div style="display: inline-block;">???????????????</div><div class="artTitle artContent divHeight" style="display: inline-block;" data-value="' + item.artNo + '"><b>' + item.artTitle + '</b></div></div>' +
	                '<div id="movType" class="divWidth divHeight" style="display: inline-block"><div style="display: inline-block">???????????????</div> <div style="display: inline-block">' +item.artMovType + '</div></div>' +
	                '<div id="artRep" style="display: inline-block"><button class="artRepButton combtn" title="????????????" data-value="'+item.artRepNo+'">????????????</button></div>' +
	                '<div id="artRepTime"><div class="divHeight" style="display: inline-block">???????????????</div> <div class="divHeight" style="display: inline-block">' +
	                moment(item.artRepTime).locale('zh_TW').format('llll') +
	                '</div></div>' +
	                '<div><div class="artRepContent" style="font-size: 1.2rem; text-indent: 2rem;">' +
	                item.artRepContent + '</div></div><hr>');
	            $('#artRepListCenter .artTitle').css({
	                'cursor': 'pointer'
	            });
	        });
	        $('#artRepItems').html('<div class="inlineBlockDIV" style="text-align: center!important;"><b>???<font style="color:#bb9d52;">'+artRepVO.length+'</font>???</b></div>');
	    },
	    error: function () {
	        console.log('AJAX-findArtByCompositeQuery???????????????!')
	    }
	});
};

//????????????????????????
$('#v-pills-article-tab, #findArtByTitleButton, #artTitleByCompositeQuery ,#findArtByTimeButton, #artTimeForByCompositeQuery').on('click keypress', function (e) {
        // debugger;
        if (e.which === 13 || e.currentTarget.id === 'v-pills-article-tab' ||e.currentTarget.id === 'findArtByTimeButton' || e.currentTarget.id === 'findArtByTitleButton') {
            findArtByCompositeQuery(e);
            $('#artTitleByCompositeQuery').val('');
            $('#artTimeForByCompositeQuery').val('');
        }
    });
    
//????????????????????????
$('#v-pills-artFav-tab, #artFavTitleByCompositeQuery, #findArtFavTitleByButton, #artFavTimeByCompositeQuery, #findArtFavTimeByButton').on('click keypress', function(e){
	if(e.which === 13 || this.id === 'v-pills-artFav-tab' || this.id === 'findArtFavTitleByButton' || this.id === 'findArtFavTimeByButton'){
		findArtFavByCompositeQuery(e);
		$('#artFavTitleByCompositeQuery').val('');
		$('#artFavTimeForByCompositeQuery').val('');
	}
});

//????????????????????????
$('#v-pills-artRep-tab, #artRepTitleByCompositeQuery, #findArtRepTitleByButton, #artRepTimeByCompositeQuery, #findArtRepTimeByButton').on('click keypress', function(e){
	if(e.which === 13 || this.id === 'v-pills-artRep-tab' || this.id === 'findArtRepTitleByButton' || this.id === 'findArtRepTimeByButton'){
		debugger;
		findArtRepByCompositeQuery(e);
		$('#artRepTitleByCompositeQuery').val('');
		$('#artRepTimeForByCompositeQuery').val('');
	}
});

//????????????
$('#artRepListCenter').on('click', '.artRepButton', function(e){
	debugger;
	$.ajax({
		type: 'POST',
		url: '<%=request.getContextPath()%>/art/artRep.do',
		data: {'action':'changeArticleReportStatus', 'artRepNo':$(e.currentTarget).attr('data-value')},
		dataType: 'json',
		success: function (){
					toastr['warning']('????????????', '??????');
					debugger;
					findArtRepByCompositeQuery('v-pills-artRep-tab');
					
		},
		error: function(){console.log("AJAX-changeArtFav???????????????!")}
	});
});

//??????????????????????????????
debugger;
findArtByCompositeQuery(this);

//??????????????????
if ('${updateSuccess}' == 'updateSuccess') {
    <%
    session.removeAttribute("updateSuccess");
    %>
    toastr['success']('??????????????????', '??????');
}

//??????????????????
$('#artFavListCenter').on('click', '.artFavButton', function (event){
	debugger;
	$.ajax({
		type: 'POST',
		url: '<%=request.getContextPath()%>/art/artFav.do',
		data: {'action':'deleteArtFav', 'artNo':$(this).attr('data-value')},
		dataType: 'json',
		success: function (){
					toastr['warning']('????????????', '??????');
					debugger;
					findArtFavByCompositeQuery('v-pills-artFav-tab');
					
		},
		error: function(){console.log("AJAX-changeArtFav???????????????!")}
	});
});

//??????????????????
debugger;
$('#artListCenter, #artFavListCenter, #artRepListCenter').on('click', '.artContent', function (event) {
    console.log("artContent clicked:" + $(event.currentTarget).html());
    console.log("artNo:" + $(event.currentTarget).attr('data-value'));
    // debugger;
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
                console.log(item.artTitle);
                clearOneArticle();
                $('#myModalLabel').attr('data-value', item.artNo);
                $('#myModalLabel').append(item.artTitle);
                $('#oneArtContent').append('<p>' + item.artContent +'</p>');
                console.log("item.memNo:" + item.memNo);
            });
        },
        error: function () {
            console.log("AJAX-ready???????????????!")
        }
    });
});
</script>  
</body>
</html>