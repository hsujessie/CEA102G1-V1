<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
	<%@ include file="/front-end/files/frontend_importCss.file"%>
    <!-- toastr v2.1.4 -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css" rel="stylesheet" />
<style>	
	.topDiv{
		margin: 0 auto 5vh auto;
    	width: 23vw;
    	height: 10vh;
  	}
  	.divWidth{
		width: 40vh;
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
                    <a class="nav-link active" id="v-pills-home-tab" data-toggle="pill" href="#v-pills-home" role="tab"
                        aria-controls="v-pills-home" aria-selected="true">管理文章</a>
                    <a class="nav-link" id="v-pills-profile-tab" data-toggle="pill" href="#v-pills-profile" role="tab"
                        aria-controls="v-pills-profile" aria-selected="false">管理收藏</a>
                </div>
            </div>
            <div class="col-9">
                <div class="tab-content" id="v-pills-tabContent">
                    <div class="tab-pane fade show active" id="v-pills-home" role="tabpanel"
                        aria-labelledby="v-pills-home-tab">
                        <!-- 管理文章 -->
                        <div class="row">
                            <div class="col-6">
                                <div class="topDiv">
                                    <div class="input-group mb-3">
                                        <input type="text" id="artTitleByCompositeQuery" class="form-control"
                                            placeholder="搜尋標題" aria-label="Recipient's username"
                                            aria-describedby="findArtByTitleButton">
                                        <div class="input-group-append">
                                            <button class="btn btn-outline-secondary" type="button"
                                                id="findArtByTitleButton">查詢</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="topDiv">
                                    <div class="input-group mb-3">
                                        <input type="date" id="artTimeForByCompositeQuery" class="form-control"
                                            placeholder="依日期搜尋" aria-label="Recipient's username"
                                            aria-describedby="findArtByTimeButton">
                                        <div class="input-group-append">
                                            <button class="btn btn-outline-secondary" type="button"
                                                id="findArtByTimeButton">查詢</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div id="artListCenter"></div>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="v-pills-profile" role="tabpanel"
                        aria-labelledby="v-pills-profile-tab">
                        <!-- 管理收藏 -->
                        <div class="row">
                            <div class="col-6">
                                <div class="topDiv">
                                    <div class="input-group mb-3">
                                        <input type="text" id="artFavByCompositeQuery" class="form-control"
                                            placeholder="搜尋標題" aria-label="Recipient's username"
                                            aria-describedby="findArtFavByTitleButton">
                                        <div class="input-group-append">
                                            <button class="btn btn-outline-secondary" type="button"
                                                id="findArtFavByTitleButton">查詢</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="topDiv">
                                    <div class="input-group mb-3">
                                        <input type="date" id="artFavTimeForByCompositeQuery" class="form-control"
                                            placeholder="依日期搜尋" aria-label="Recipient's username"
                                            aria-describedby="findArtFavByTimeButton">
                                        <div class="input-group-append">
                                            <button class="btn btn-outline-secondary" type="button"
                                                id="findArtFavByTimeButton">查詢</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div id="artFavListCenter"></div>
                            </div>
                        </div>
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
        
            <!-- 燈箱開始 -->
	    <div class="modal fade" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	        <div class="modal-dialog modal-dialog-scrollable" role="document">
	            <div class="modal-content" style="height: 100vh">
	                <div id="art_modal-header" class="modal-header">
	                    <div class="modal-title col-md-11">
	                        <h3 id="myModalLabel">文章已被移除</h3>
	                    </div>
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                </div>
	                <div class="modal-body">
	                    <div id="art_modal_body" data-value="">
	                        <!-- 顯示文章 -->
	                        <div id="oneArtContent">
	                        </div>
	                    </div>
	                </div>
	
	            </div>
	        </div>
	    </div>
	    <!-- 燈箱結束 -->

        
<%@ include file="/front-end/files/frontend_importJs.file"%>
	<script src="<%=request.getContextPath()%>/resource/js/moment-with-locales.min.js" ></script>
    <!-- toastr v2.1.4 -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>
    <script>
        toastr.options = {
            // 參數設定
            "closeButton": false, // 顯示關閉按鈕
            "debug": false, // 除錯
            "newestOnTop": false, // 最新一筆顯示在最上面
            "progressBar": true, // 顯示隱藏時間進度條
            "positionClass": "toast-top-right", // 位置的類別
            "preventDuplicates": false, // 隱藏重覆訊息
            "onclick": null, // 當點選提示訊息時，則執行此函式
            "showDuration": "300", // 顯示時間(單位: 毫秒)
            "hideDuration": "1000", // 隱藏時間(單位: 毫秒)
            "timeOut": "5000", // 當超過此設定時間時，則隱藏提示訊息(單位: 毫秒)
            "extendedTimeOut": "1000", // 當使用者觸碰到提示訊息時，離開後超過此設定時間則隱藏提示訊息(單位: 毫秒)
            "showEasing": "swing", // 顯示動畫時間曲線
            "hideEasing": "linear", // 隱藏動畫時間曲線
            "showMethod": "fadeIn", // 顯示動畫效果
            "hideMethod": "fadeOut" // 隱藏動畫效果
        }
    </script>
 
<script>
//清空文章列表
function clearArtList() {
    $('#artListCenter').empty();
};

//清空燈箱
function clearOneArticle(){
	$('#oneArtContent').empty();
	$('#myModalLabel').empty();
};

//複合查詢data
function addCompositeQueryData(e) {
  var addArtDataAttr = {
      'action': 'find_By_CompositeQuery_Use_AJAX', 'memNo':'${MemberVO.memNo}'
  };
  	debugger;
  if ($('#artTitleByCompositeQuery').val() != null) {
      addArtDataAttr['artTitle'] = $('#artTitleByCompositeQuery').val();
      console.log("artTitle add ");
  }
//   	debugger;
  if ($('#artTimeForByCompositeQuery').val() != null || $('#artTimeForByCompositeQuery').val() != 'null') {
      addArtDataAttr['artTime'] = $('#artTimeForByCompositeQuery').val();
      console.log("artTime add " + addArtDataAttr);
  }

  console.log("addCompositeQueryData:" + addArtDataAttr);
  return addArtDataAttr;
};

//複合查詢
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
                 debugger;
                $('#artListCenter').append(
                    '<div id="movType" class="divWidth divHeight" style="display: inline-block"><div style="display: inline-block">電影類型：</div> <div style="display: inline-block">' +
                    item.artMovType + '</div></div>' +
                   	'<div id="updateArticleDiv" style="display: inline-block"><form method="POST" action="<%= request.getContextPath()%>/art/art.do"><input type="hidden" class="artUpdateMemNo" name="memNo" value="${MemberVO.memNo}"><input type="hidden" class="artUpdateArtNo" name="artNo" value="'+item.artNo+'"><input type="hidden" name="action" value="select_Upadte_One_Art"><input type="hidden" class="artManageUpdate" name="artManageUpdate" value="true"><input type="submit" class="updateArticleButton combtn" value="修改文章"></form></div>' +
                    '<div id="artTitle"><div class="divHeight" style="font-size: 1.2rem;"><b>' + item.artTitle + '</b></div></div>' +
                    '<div id="artTime"><div class="divHeight" style="display: inline-block">修改時間：</div> <div class="divHeight" style="display: inline-block">' +
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
        },
        error: function () {
            console.log('AJAX-findArtByCompositeQuery發生錯誤囉!')
        }
    });
};

//觸發複合查詢
$('#findArtByTitleButton, #artTitleByCompositeQuery ,#findArtByTimeButton, #artTimeForByCompositeQuery').on('click keypress', function (e) {
        // debugger;
        if (e.which === 13 || e.currentTarget.id === 'findArtByTimeButton' || e.currentTarget.id === 'findArtByTitleButton') {
            debugger;
            findArtByCompositeQuery(e);
        }
    });

//進入查該會員全部文章
debugger;
findArtByCompositeQuery(this);

//修改成功訊息
if ('${updateSuccess}' == 'updateSuccess') {
    //      	debugger;
    toastr['success']('修改成功！！', '成功'); 
    <%
    session.removeAttribute("updateSuccess"); 
    %>
}

//單篇文章燈箱
debugger;
$('#artListCenter').on('click', '.artContent', function (event) {
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
            //開啟燈箱
            $('#basicModal').modal('show');

            //把文章標題內容放進燈箱
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
            console.log("AJAX-ready發生錯誤囉!")
        }
    });
});
</script>  
</body>
</html>