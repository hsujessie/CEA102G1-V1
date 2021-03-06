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
//??????????????????	
function ListArtQuery(){
	debugger;
	$.ajax({
		type: 'POST',
		url: '<%=request.getContextPath()%>/art/art.do',
		data: {'action':'art_Show_By_AJAX'},
		dataType: 'json',
		success: function (artVO){
			debugger;
			//??????????????????
			$(artVO).each(function(i, item){
				$('#artListCenter').append(
						'<div class="oneArtDiv"><div id="artAuthor" style="display: inline-block"><div style="display: inline-block"><img class="authorIMG" src="<%=request.getContextPath()%>/util/imgReader?columnName=MEM_IMG&tableName=member&fieldName=MEM_NO&fieldValue='+item.memNo+'"></div><div style="display: inline-block">'+item.memName+'</div></div>'
						+'<div id="movType" style="display: inline-block"><div style="display: inline-block">???????????????</div> <div style="display: inline-block">'+item.artMovType+'</div></div>'
						+'<div id="artTitle"><div style="font-size: 1.2rem;"><b>'+item.artTitle+'</b></div></div>'
						+'<div id="artTime"><div style="display: inline-block">???????????????</div> <div style="display: inline-block">'+moment(item.artTime).locale('zh_TW').format('llll')+'</div></div>'
						+'<div><div class="artContent" data-value="'+item.artNo+'">'+item.artContent+'</div></div></div>')			
						;
				});
			//????????????
			if (artVO.length == 0){
				$('#artListCenter').append('<div class="noArticle">????????????</div>');
			}
		},
		error: function(){console.log("AJAX-ListArtQuery???????????????!")}
	});
		
	//?????????????????????????????????????????????????????????
	if('${openModal}' === 'openModal'){
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/art/art.do',
			data: {'action':'art_Show_By_AJAX', 'artNo':'${artNo}'},
			dataType: 'json',
			success: function (artVO){
				//????????????
				$('#basicModal').modal('show');
				$(artVO).each(function(i, item){
					clearOneArticle();
					clearArtReplyno();
 					$('#myModalLabel').append(item.artTitle);
 					$('#artFav_header_like').attr('data-value', item.artNo)
 					$('#myModalLabel').attr('data-value', item.artNo)
 					$('#oneArtContent').append('<p>'+item.artContent+'</p>');
 					$('#artReplyno').append('???????????? '+item.artReplyno);
					console.log("item.memNo:"+item.memNo);
						
 					//??????????????????????????????????????????
 					if(item.memNo == '${memNo}' && '${MemberVO}' != ""){
 						$('#memUpdateArt').show();
 						$('#artUpdateMemNo').val(item.memNo);
 						$('#artUpdateArtNo').val(item.artNo);
 					}else{
 						$('#memUpdateArt').hide();
 					}
 					 					
					//?????????????????????
					debugger;
					if('${MemberVO}' != ""){
						isArtFav();
					}
					
	 			  	//?????????????????????
	 			    listAllArtRepByArtNo();
				});
			},
			error: function(){console.log("AJAX-openModal???????????????!")}
		});
	}

	console.log("??????????????????====="+'${memNo}');
};


//??????Top3??????????????????	
function ListArtTopThreeQuery(){
	debugger;
	$.ajax({
		type: 'POST',
		url: '<%=request.getContextPath()%>/art/art.do',
		data: {'action':'artTopThree_Show_By_AJAX', 'artMovType':$('.selectedMovType').attr('data-value')},
		dataType: 'json',
		success: function (artVO){
			debugger;
			//??????????????????
			if(artVO.length == 0){
				$('#Top3ArticleTitle').hide();
				$('.swiper-pagination').hide();
			}else{
				$('#Top3ArticleTitle').show();
				$('.swiper-pagination').show();
			}
			$('#Top3ArticleTitle').empty();
			if($('.selectedMovType').attr('data-value') == null){
				$('#Top3ArticleTitle').append('<h4 style="text-align: left;">????????????</h4>');
			}else{
				$('#Top3ArticleTitle').append('<h4 style="text-align: left;">'+$('.selectedMovType').attr('data-value')+'????????????</h4>');
			}
			$(artVO).each(function(i, item){
				$('#Top3Article').append(
			            '<div id="hotArticleDiv" class="HotArticleDiv swiper-slide"><div id="topThreeArticle" style="color: #FF7575;"><i class="fas fa-crown" style="color: #bb9d52; margin: 0px 5px;"></i><b>HOT</b></div><div style="display: inline-block"><div style="display: inline-block"><img class="authorIMG" src="<%=request.getContextPath()%>/util/imgReader?columnName=MEM_IMG&tableName=member&fieldName=MEM_NO&fieldValue='+item.memNo+'"></div><div style="display: inline-block">'+item.memName+'</div></div>'
						+'<div id="movType" style="display: inline-block"><div style="display: inline-block">???????????????</div> <div style="display: inline-block">'+item.artMovType+'</div></div>'
						+'<div id="artTitle" class="artTitle"><div><b>'+item.artTitle+'</b></div></div>'
						+'<div id="artTime"><div style="display: inline-block">'+moment(item.artTime).locale('zh_TW').format('ll')+'</div></div>'
						+'<div><div class="artContent" data-value="'+item.artNo+'">'+item.artContent+'</div></div></div>')			
			});
			$('#Top3Article').append('<hr>');
			//????????????
			if (artVO.length == 0){
				$('#Top3Article').empty();
			}
		},
		error: function(){console.log("AJAX-ListArtTopThreeQuery???????????????!")}
	});
}

//??????????????????????????????
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
		error: function(){console.log("AJAX-isArtFav???????????????!")}
	});
};

//???????????????????????????
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
							toastr['warning']('????????????', '??????');
				},
				error: function(){console.log("AJAX-changeArtFav???????????????!")}
			});
		}else{
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/art/artFav.do',
				data: {'action':'addArtFav', 'artNo':$('#artFav_header_like').attr('data-value')},
				dataType: 'json',
				success: function (){
							$('#artFav_header_like').css('color', 'red');
							toastr['success']('????????????', '??????');
				},
				error: function(){console.log("AJAX-changeArtFav???????????????!")}
			});
		}
	});	
};

//????????????
function addArtRpt(){
	$('#artRptButton').click(function(){
		debugger;
		if($('#artRptReson').val().trim().length == 0){
			toastr['error']('???????????????????????????', '??????');
		}else{
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/art/artRpt.do',
				data: {'action':'addArtRpt', 'artNo':$('#artRptButton').attr('data-value'), 'artRptReson':$('#artRptReson').val()},
				dataType: 'json',
				success: function(){
					clearArtRptText();
					toastr['warning']('????????????', '??????');
				},
				error: function(){console.log("AJAX-addArtRpe???????????????!")}
			});
		}
	});
};

//????????????
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
			$('.artRepAlertDanger').html('<div class="alert alert-danger alert-dismissible fade show" role="alert">?????????????????????????????????????????????<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>');
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
					toastr['success']('????????????', '??????');
					$(artRepVO).each(function(i, item){
						$('#artReplyno').append('???????????? '+item.artReplyno);
					});

				},
				error: function(){console.log("AJAX-addArtRpe???????????????!")}
			});
		}
	}

	});
};

//???????????????????????????
function clearArtRepEmptyAlert(){
	$('#artRepContent').click(function(){
		$('.artRepAlertDanger').empty();
	});
}

//???????????????
function listAllArtRepByArtNo(){
	debugger;
	$.ajax({
		type: 'POST',
		url: '<%=request.getContextPath()%>/art/artRep.do',
		data: {'action':'listAllArtRepByArtNo', 'artNo':$('#myModalLabel').attr('data-value')},
		dataType: 'json',
		success: function(artRepVO){
			//??????????????????
			clearArtRepList();
			debugger;
			if(artRepVO.length == 0){
				$('#artRep').append('<div style="font-size: 3rem; color: #808080">????????????</div>');
			}else{
		        $(artRepVO).each(function (i, item) {
		            $('#artRep').append(
		            	'<img class="repAuthorIMG" src="<%=request.getContextPath()%>/util/imgReader?columnName=MEM_IMG&tableName=member&fieldName=MEM_NO&fieldValue='+item.memNo+'">'+
		                '<div style="line-height: 300%"><div id="memName" style="display:inline-block; width: 17%;">' +
		                item.memName +
		                '</div><div id="artRepTime" style="display:inline-block; color: #6C6C6C; width: 40%">' +
		                moment(item.artRepTime).locale('zh_TW').format('llll') +
		                '</div><c:if test="${MemberVO != null}"><i id="artRepRpt_icon'+item.artRepNo+'" class="fas fa-exclamation-circle dropdown-toggle dropdown" data-toggle="dropdown" title="????????????" style="font-size: 1.5rem; color: #94B8D5;"></i></c:if><div class="dropdown-menu"><div class="form-group" data-value=' +
		                item.artRepNo +
		                '>????????????<input type="text" class="form-control artRepRptReson" placeholder="????????????" style="width: 100%;"></div><button class="btn combtn artRepRptButton">??????</button></div><div class="artRepContentList" style="text-indent: 2em; padding: 10px 0;">' +
		                item.artRepContent + '</div><hr>');
		            
		            	if(item.artRepStatus == 1){
		            		$('#artRepRpt_icon'+item.artRepNo).hide();
		            	}
		        });					
			}

		},
		error: function(){console.log("AJAX-listAllArtRepByArtNo???????????????!")}
	});
};

//????????????
function addRepRpt(){
	$('#artRep').on('click', '.artRepRptButton', function(event){
		debugger;
		if($(event.currentTarget).prev('.form-group').find('.artRepRptReson').val().trim().length == 0){
			toastr['error']('???????????????????????????', '??????');
		}else{
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/art/artRepRpt.do',
				data: {'action':'addRepRpt', 'artRepNo':$(event.currentTarget).prev('.form-group').attr('data-value') , 'artRepRptReson':$(event.currentTarget).prev('.form-group').find('.artRepRptReson').val()},
				dataType: 'json',
				success: function(){
					clearRepRptReson();
					toastr['warning']('????????????', '??????');
				},
				error: function(){console.log("AJAX-addRepRpt???????????????!")}
			});
		}
	});
};

//????????????
function clearOneArticle(){
	$('#oneArtContent').empty();
	$('#myModalLabel').empty();
};
//??????????????????
function clearArtReplyno(){
	$('#artReplyno').empty();
};
//??????????????????
function clearArtRepList(){
	$('#artRep').empty();
};

//??????????????????
function clearArtRptText(){
	$('#artRptReson').val("");
};

//??????????????????
function clearArtRepContent(){
	$('#artRepContent').val("");
};

//????????????????????????
function clearRepRptReson(){
	$('.artRepRptReson').val("");
};
</script>

</head>
<body>
<!-- ?????????????????? -->
<!-- ?????????????????? -->
<div id="Top3ArticleTitle"></div>
<div class="swiper-container">
	<div id="Top3Article" class="swiper-wrapper">
		
	</div>
	<!-- Add Pagination -->
    <div class="swiper-pagination"></div>
</div>
<!--??????AJAX?????????????????? -->
<div id="artListCenter">

</div>
<!-- ?????????????????? -->

<!-- ???????????? -->
<div class="modal fade" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	<div class="modal-dialog modal-dialog-scrollable" role="document">
		<div class="modal-content" style="height: 100vh">
                <div id="art_modal-header" class="modal-header">
                    <div class="modal-title col-md-11">
                        <h3 id="myModalLabel">??????????????????</h3>

                        <!--???????????? -->
                        <div id="art_modal-header_like" class="float-right">
                            <c:if test="${MemberVO != null}">
                                    <i id="artFav_header_like" class="fas fa-heart" title="??????" style="font-size: 1.8em; color: #94B8D5;"></i>
                            </c:if>
                        </div>
                        
                        <!-- ??????button -->
						<div class="btn-group float-right" style="box-sizing: border-box;">
							<c:if test="${MemberVO != null}">
								<i id="artRpt_icon" class="fas fa-frown dropdown-toggle dropdown" data-toggle="dropdown" title="??????" style="font-size: 1.8em; color: #94B8D5;"></i>
						    </c:if>
						    <div class="dropdown-menu">
						         <div class="form-group">
						              <label for="artRptReson">????????????</label>
						              <div id="artRptAlert"></div>
						              <input type="text" class="form-control" id="artRptReson" placeholder="????????????" style="width: 100%;">
						         </div>
						         <button id="artRptButton" class="btn combtn">??????</button>
						    </div>
						 </div>
						                          
                        <!--?????????????????? -->
                        <div id="art_modal-header_update" class="float-right">
                            <form method="POST" action="<%= request.getContextPath()%>/art/art.do">
                                <input type="hidden" id="artUpdateMemNo" name="memNo" value="">
                                <input type="hidden" id="artUpdateArtNo" name="artNo" value="">
                                <input type="hidden" name="action" value="select_Upadte_One_Art">
                                <input type="submit" id="memUpdateArt" class="btn combtn" value="????????????">
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
					<textarea id="artRepContent" placeholder="????????????"></textarea>
	            	<i id="artRepButton" class="fas fa-reply-all" title="??????"></i>
            	</div>
            </div>
		</div>
	</div>	
</div>
<!-- ???????????? -->
	
</body>
</html>