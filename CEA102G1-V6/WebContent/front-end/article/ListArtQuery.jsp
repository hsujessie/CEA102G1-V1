<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="java.util.*" %>
<%@ page import="com.art.model.*" %>

<%-- <jsp:useBean id="ListArt_ByCompositeQuery" scope="request" type="java.util.List<ArtVO>"/> --%>
<jsp:useBean id="artSvc" scope="page" class="com.art.model.ArtService"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

    <style>
        #Top3Article {
            text-align: center;
        }

        #artListCenter {
            line-height: 2rem;
            max-width: 100%;
            margin: 2rem;
        }

        .oneArtDiv {
            margin: 2rem;
        }

        .oneArtDiv:hover {
            position: relative;
            top: 5px;
            left: 0;
            border-radius: 10px;
            box-shadow: 10px 10px 10px 0px #aa9166;
            animation: moving .3s;
            background: -webkit-radial-gradient(ellipse, rgba(255, 255, 255, 0), rgba(170, 145, 102, 0.3));
            background: -o-radial-gradient(ellipse, rgba(255, 255, 255, 0), rgba(170, 145, 102, 0.3));
            background: -moz-radial-gradient(ellipse, rgba(255, 255, 255, 0), rgba(170, 145, 102, 0.3));
            background: radial-gradient(ellipse, white, rgba(255, 255, 255, 0), rgba(170, 145, 102, 0.3));
        }

        @keyframes moving {
            0% {
                top: 0px;
            }

            50% {
                top: 2px;
            }

            100% {
                top: 5px;
            }
        }

        .artTitle {
            font-size: 1.2rem;
            height: 5vh;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .artContent {
            margin: 2%;
            height: 10vh;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            cursor: pointer;
        }

        .artContent img {
            width: 10%;
            height: 10%;
            display: block;
            margin-left: auto;
        }

        #artAuthor {
            width: 40vh;
        }

        #movType {
            width: 40vh;
        }

        #art_modal-header_like {
            margin-top: 6px;
            margin-left: 25px;
        }

        #artRpt_icon {
            margin-top: 6px;
            margin-left: 20px;
        }

        .modal-dialog {
            position: relative;
            width: auto;
            margin: auto;
            pointer-events: none;
        }

        @media (min-width: 576px) {
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

        @media (min-width: 576px) {
            .modal-dialog-scrollable .modal-content {
                max-height: 100vh;
            }
        }

        .modal-body {
            padding: 0px;
            background-color: #F5F5F5;
        }

        #oneArtContent {
            padding: 5%;
            background-color: #ffffff;
        }

        #artRep {
            padding: 5%;
        }

        #artRepDiv {
            width: 100%;
        }

        #artRepDiv textarea {
            width: 90%;
            border-style: hidden;
            display: inline-block;
            vertical-align: top;
        }

        #artRepDiv #artRepButton {
            display: inline-block;
            vertical-align: bottom;
            font-size: 2rem;

        }

        #artReplyno {
            color: #808080;
            text-align: right;
            padding: 5%;
        }

        .HotArticleDiv {
        	background-color: #000;
        	color: #fff;
            display: inline-block;
            width: 17vw;
            height: 40vh;
            box-shadow: 3px 3px 10px #aa9166;
            padding: 1% 5%;
            margin: 1%;
            vertical-align: top;
            text-align: left;
        }
        
        .HotArticleDiv:hover{
        	background-color: #aa9166;
        	color: #000;
        	box-shadow: 0 0 5px #AA9166, 0 0 25px #AA9166, 0 0 50px #AA9166, 0 0 50px #AA9166;
        }

        @media (max-width: 991px) {
            .HotArticleDiv {
                display: block;
                width: 50vw;
                height: 30vh;
                margin: auto;
            }
        }

        .nav-item {
            cursor: pointer;
        }

        .noArticle {
            font-size: 3rem;
            color: #808080;
            text-align: center
        }
    </style>

<script type="text/javascript">
//列出文章列表	
function ListArtQuery(){
	debugger;
	$.ajax({
		type: 'POST',
		url: '<%=request.getContextPath()%>/art/art.do',
		data: {'action':'art_Show_By_AJAX'},
		dataType: 'json',
		success: function (artVO){
			debugger;
			//加入文章內容
			$(artVO).each(function(i, item){
				$('#artListCenter').append(
						'<div class="oneArtDiv"><div id="artAuthor" style="display: inline-block"><div style="display: inline-block"><img class="authorIMG" src="<%=request.getContextPath()%>/util/imgReader?columnName=MEM_IMG&tableName=member&fieldName=MEM_NO&fieldValue='+item.memNo+'></div><div style="display: inline-block">'+item.memName+'</div></div>'
						+'<div id="movType" style="display: inline-block"><div style="display: inline-block">電影類型：</div> <div style="display: inline-block">'+item.artMovType+'</div></div>'
						+'<div id="artTitle"><div style="font-size: 1.2rem;"><b>'+item.artTitle+'</b></div></div>'
						+'<div id="artTime"><div style="display: inline-block">修改時間：</div> <div style="display: inline-block">'+moment(item.artTime).locale('zh_TW').format('llll')+'</div></div>'
						+'<div><div class="artContent" data-value="'+item.artNo+'">'+item.artContent+'</div></div></div>')			
						;
				});
			//若無文章
			if (artVO.length == 0){
				$('#artListCenter').append('<div class="noArticle">尚無文章</div>');
			}
		},
		error: function(){console.log("AJAX-ListArtQuery發生錯誤囉!")}
	});
		
	//新增或修改完成後開啟燈箱及載入文章內容
	if('${openModal}' === 'openModal'){
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/art/art.do',
			data: {'action':'art_Show_By_AJAX', 'artNo':'${artNo}'},
			dataType: 'json',
			success: function (artVO){
				//開啟燈箱
				$('#basicModal').modal('show');
				$(artVO).each(function(i, item){
					clearOneArticle();
					clearArtReplyno();
 					$('#myModalLabel').append(item.artTitle);
 					$('#artFav_header_like').attr('data-value', item.artNo)
 					$('#myModalLabel').attr('data-value', item.artNo)
 					$('#oneArtContent').append('<p>'+item.artContent+'</p>');
 					$('#artReplyno').append('回應數量 '+item.artReplyno);
					console.log("item.memNo:"+item.memNo);
						
 					//判斷是否為會員本人發表的文章
 					if(item.memNo == '${memNo}' && '${MemberVO}' != ""){
 						$('#memUpdateArt').show();
 						$('#artUpdateMemNo').val(item.memNo);
 						$('#artUpdateArtNo').val(item.artNo);
 					}else{
 						$('#memUpdateArt').hide();
 					}
 					 					
					//判斷是否已收藏
					debugger;
					if('${MemberVO}' != ""){
						isArtFav();
					}
					
	 			  	//呼叫列全部回文
	 			    listAllArtRepByArtNo();
				});
			},
			error: function(){console.log("AJAX-openModal發生錯誤囉!")}
		});
	}

	console.log("目前登入會員====="+'${memNo}');
};


//列出Top3點擊文章列表	
function ListArtTopThreeQuery(){
	debugger;
	$.ajax({
		type: 'POST',
		url: '<%=request.getContextPath()%>/art/art.do',
		data: {'action':'artTopThree_Show_By_AJAX', 'artMovType':$('.selectedMovType').attr('data-value')},
		dataType: 'json',
		success: function (artVO){
			debugger;
			//加入文章內容
			if(artVO.length == 0){
				$('#Top3ArticleTitle').hide();
				$('.swiper-pagination').hide();
			}else{
				$('#Top3ArticleTitle').show();
				$('.swiper-pagination').show();
			}
			$('#Top3ArticleTitle').empty();
			if($('.selectedMovType').attr('data-value') == null){
				$('#Top3ArticleTitle').append('<h4 style="text-align: left;">熱門文章</h4>');
			}else{
				$('#Top3ArticleTitle').append('<h4 style="text-align: left;">'+$('.selectedMovType').attr('data-value')+'熱門文章</h4>');
			}
			$(artVO).each(function(i, item){
				$('#Top3Article').append(
						'<div id="hotArticleDiv" class="HotArticleDiv swiper-slide"><div id="topThreeArticle" style="color: #FF7575;"><i class="fas fa-crown" style="color: #bb9d52; margin: 0px 5px;"></i><b>HOT</b></div><div style="display: inline-block"><div style="display: inline-block"><img class="authorIMG" src="<%=request.getContextPath()%>/util/imgReader?columnName=MEM_IMG&tableName=member&fieldName=MEM_NO&fieldValue='+item.memNo+'></div> <div style="display: inline-block">'+item.memName+'</div></div>'
						+'<div id="movType" style="display: inline-block"><div style="display: inline-block">電影類型：</div> <div style="display: inline-block">'+item.artMovType+'</div></div>'
						+'<div id="artTitle" class="artTitle"><div><b>'+item.artTitle+'</b></div></div>'
						+'<div id="artTime"><div style="display: inline-block">'+moment(item.artTime).locale('zh_TW').format('ll')+'</div></div>'
						+'<div><div class="artContent" data-value="'+item.artNo+'">'+item.artContent+'</div></div></div>')			
			});
			$('#Top3Article').append('<hr>');
			//若無文章
			if (artVO.length == 0){
				$('#Top3Article').empty();
			}
		},
		error: function(){console.log("AJAX-ListArtTopThreeQuery發生錯誤囉!")}
	});
}

//查詢收藏此文章的情況
function isArtFav(){
	$.ajax({
		type: 'POST',
		url: '<%=request.getContextPath()%>/art/artFav.do',
		data: {'action':'isArtFav', 'artNo':$('#artFav_header_like').attr('data-value')},
		dataType: 'json',
		success: function (artFavVO){
			$(artFavVO).each(function(i, item){
				debugger;
				if(item.isArtFav == true){
					$('#artFav_header_like').css('color', 'red');				    
				}else if(item.isArtFav == false){
					$('#artFav_header_like').css('color', '#94B8D5');
				}
			});
		},
		error: function(){console.log("AJAX-isArtFav發生錯誤囉!")}
	});
};

//新增收藏或刪除收藏
function changeArtFav(){
	$('#artFav_header_like').click(function(){
		console.log("$('#artFav_header_like').css('color'):"+$('#artFav_header_like').css('color'));
		if($('#artFav_header_like').css('color') == 'rgb(255, 0, 0)'){
// 			debugger;
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/art/artFav.do',
				data: {'action':'deleteArtFav', 'artNo':$('#artFav_header_like').attr('data-value')},
				dataType: 'json',
				success: function (){
							$('#artFav_header_like').css('color', '#94B8D5');
							toastr['warning']('刪除收藏', '成功');
				},
				error: function(){console.log("AJAX-changeArtFav發生錯誤囉!")}
			});
		}else{
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/art/artFav.do',
				data: {'action':'addArtFav', 'artNo':$('#artFav_header_like').attr('data-value')},
				dataType: 'json',
				success: function (){
							$('#artFav_header_like').css('color', 'red');
							toastr['success']('新增收藏', '成功');
				},
				error: function(){console.log("AJAX-changeArtFav發生錯誤囉!")}
			});
		}
	});	
};

//新增檢舉
function addArtRpt(){
	$('#artRptButton').click(function(){
		debugger;
		if($('#artRptReson').val().trim().length == 0){
			toastr['error']('檢舉原因不能空白！', '失敗');
		}else{
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/art/artRpt.do',
				data: {'action':'addArtRpt', 'artNo':$('#artRptButton').attr('data-value'), 'artRptReson':$('#artRptReson').val()},
				dataType: 'json',
				success: function(){
					clearArtRptText();
					toastr['warning']('檢舉文章', '成功');
				},
				error: function(){console.log("AJAX-addArtRpe發生錯誤囉!")}
			});
		}
	});
};

//新增回文
function addArtRep(){
	$('#artRepButton').click(function(){
		debugger;
	if('${MemberVO}' == ''){
		<%
		session.setAttribute("location", request.getRequestURI());
		%>
		window.location.href = "<%=request.getContextPath()%>/front-end/Member_Login/login.jsp"
	}else{
		if($('#artRepContent').val().trim().length == 0){
			$('.artRepAlertDanger').html('<div class="alert alert-danger alert-dismissible fade show" role="alert">留言內容不能空白，請輸入內容！<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>');
		}else{
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/art/artRep.do',
				data: {'action':'addArtRep', 'artNo':$('#myModalLabel').attr('data-value'), 'artRepContent':$('#artRepContent').val()},
				dataType: 'json',
				success: function(artRepVO){
					clearArtRepContent();
					clearArtReplyno();
					listAllArtRepByArtNo();
					toastr['success']('回覆成功', '成功');
					$(artRepVO).each(function(i, item){
						$('#artReplyno').append('回應數量 '+item.artReplyno);
					});

				},
				error: function(){console.log("AJAX-addArtRpe發生錯誤囉!")}
			});
		}
	}

	});
};

//清除留言為空值警告
function clearArtRepEmptyAlert(){
	$('#artRepContent').click(function(){
		$('.artRepAlertDanger').empty();
	});
}

//列全部回文
function listAllArtRepByArtNo(){
	debugger;
	$.ajax({
		type: 'POST',
		url: '<%=request.getContextPath()%>/art/artRep.do',
		data: {'action':'listAllArtRepByArtNo', 'artNo':$('#myModalLabel').attr('data-value')},
		dataType: 'json',
		success: function(artRepVO){
			//加入回覆內容
			clearArtRepList();
			debugger;
			if(artRepVO.length == 0){
				$('#artRep').append('<div style="font-size: 3rem; color: #808080">尚無回文</div>');
			}else{
		        $(artRepVO).each(function (i, item) {
		            $('#artRep').append(
		            	'<img class="repAuthorIMG" src="<%=request.getContextPath()%>/util/imgReader?columnName=MEM_IMG&tableName=member&fieldName=MEM_NO&fieldValue='+item.memNo+'>'+
		                '<div style="line-height: 300%"><div id="memName" style="display:inline-block; width: 17%;">' +
		                item.memName +
		                '</div><div id="artRepTime" style="display:inline-block; color: #6C6C6C; width: 40%">' +
		                moment(item.artRepTime).locale('zh_TW').format('llll') +
		                '</div><c:if test="${MemberVO != null}"><i id="artRepRpt_icon'+item.artRepNo+'" class="fas fa-exclamation-circle dropdown-toggle dropdown" data-toggle="dropdown" title="檢舉留言" style="font-size: 1.5rem; color: #94B8D5;"></i></c:if><div class="dropdown-menu"><div class="form-group" data-value=' +
		                item.artRepNo +
		                '>檢舉留言<input type="text" class="form-control artRepRptReson" placeholder="輸入原因" style="width: 100%;"></div><button class="btn combtn artRepRptButton">確定</button></div><div class="artRepContentList" style="text-indent: 2em; padding: 10px 0;">' +
		                item.artRepContent + '</div><hr>');
		            
		            	if(item.artRepStatus == 1){
		            		$('#artRepRpt_icon'+item.artRepNo).hide();
		            	}
		        });					
			}

		},
		error: function(){console.log("AJAX-listAllArtRepByArtNo發生錯誤囉!")}
	});
};

//檢舉回文
function addRepRpt(){
	$('#artRep').on('click', '.artRepRptButton', function(event){
		debugger;
		if($(event.currentTarget).prev('.form-group').find('.artRepRptReson').val().trim().length == 0){
			toastr['error']('檢舉原因不能空白！', '失敗');
		}else{
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/art/artRepRpt.do',
				data: {'action':'addRepRpt', 'artRepNo':$(event.currentTarget).prev('.form-group').attr('data-value') , 'artRepRptReson':$(event.currentTarget).prev('.form-group').find('.artRepRptReson').val()},
				dataType: 'json',
				success: function(){
					clearRepRptReson();
					toastr['warning']('檢舉留言', '成功');
				},
				error: function(){console.log("AJAX-addRepRpt發生錯誤囉!")}
			});
		}
	});
};

//清空燈箱
function clearOneArticle(){
	$('#oneArtContent').empty();
	$('#myModalLabel').empty();
};
//清空回文數量
function clearArtReplyno(){
	$('#artReplyno').empty();
};
//清空回文列表
function clearArtRepList(){
	$('#artRep').empty();
};

//清空檢舉原因
function clearArtRptText(){
	$('#artRptReson').val("");
};

//清空回文內容
function clearArtRepContent(){
	$('#artRepContent').val("");
};

//清空留言檢舉原因
function clearRepRptReson(){
	$('.artRepRptReson').val("");
};
</script>

</head>
<body>
<!-- 中間區塊開始 -->
<!-- 前三熱門文章 -->
<div id="Top3ArticleTitle"></div>
<div class="swiper-container">
	<div id="Top3Article" class="swiper-wrapper">
		
	</div>
	<!-- Add Pagination -->
    <div class="swiper-pagination"></div>
</div>
<!--使用AJAX查詢文章列表 -->
<div id="artListCenter">

</div>
<!-- 中間區塊結束 -->

<!-- 燈箱開始 -->
<div class="modal fade" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	<div class="modal-dialog modal-dialog-scrollable" role="document">
		<div class="modal-content" style="height: 100vh">
                <div id="art_modal-header" class="modal-header">
                    <div class="modal-title col-md-11">
                        <h3 id="myModalLabel">文章已被移除</h3>

                        <!--收藏按鈕 -->
                        <div id="art_modal-header_like" class="float-right">
                            <c:if test="${MemberVO != null}">
                                    <i id="artFav_header_like" class="fas fa-heart" title="收藏" style="font-size: 1.8em; color: #94B8D5;"></i>
                            </c:if>
                        </div>
                        
                        <!-- 檢舉button -->
						<div class="btn-group float-right" style="box-sizing: border-box;">
							<c:if test="${MemberVO != null}">
								<i id="artRpt_icon" class="fas fa-frown dropdown-toggle dropdown" data-toggle="dropdown" title="檢舉" style="font-size: 1.8em; color: #94B8D5;"></i>
						    </c:if>
						    <div class="dropdown-menu">
						         <div class="form-group">
						              <label for="artRptReson">檢舉文章</label>
						              <div id="artRptAlert"></div>
						              <input type="text" class="form-control" id="artRptReson" placeholder="輸入原因" style="width: 100%;">
						         </div>
						         <button id="artRptButton" class="btn combtn">確定</button>
						    </div>
						 </div>
						                          
                        <!--修改文章按鈕 -->
                        <div id="art_modal-header_update" class="float-right">
                            <form method="POST" action="<%= request.getContextPath()%>/art/art.do">
                                <input type="hidden" id="artUpdateMemNo" name="memNo" value="">
                                <input type="hidden" id="artUpdateArtNo" name="artNo" value="">
                                <input type="hidden" name="action" value="select_Upadte_One_Art">
                                <input type="submit" id="memUpdateArt" class="btn combtn" value="修改文章">
                            </form>
                        </div>
                        
                    </div>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>			
			<div class="modal-body">
<!-- ====================include ArticleContent.file==================== -->
				<div id="art_modal_body" data-value="">
					<%@ include file="/front-end/article/ArticleContent.file" %> 
				</div>
<!-- ====================include ArticleContent.file==================== -->
			</div>

			<div id="art_modal-footer" class="modal-footer" style="padding: 0px">
				<div id="artRepDiv">
					<div class="artRepAlertDanger"></div>
					<textarea id="artRepContent" placeholder="輸入留言"></textarea>
	            	<i id="artRepButton" class="fas fa-reply-all" title="回覆"></i>
            	</div>
            </div>
		</div>
	</div>	
</div>
<!-- 燈箱結束 -->
	
</body>
</html>