<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<style>
		.inlineBlockDIV{
			display: inline-block; 
			width: 5vw; 
			height: 10vh;
			text-align: left;
			font-size: 1rem;
            vertical-align: middle; 
			
		}
        .inlineBlockLongDIV{
            display: inline-block; 
			width: 10vw; 
			height: 10vh;
			text-align: left;
			font-size: 1rem;
            vertical-align: middle;
        }
        .longText{
        	white-space:nowrap;
			overflow:hidden;
			text-overflow:ellipsis;
			padding: 0px 5px;
        }
        .bgGray:hover{
            background-color: rgba(166, 166, 166, 0.3);
        }
	</style>
	<title>Back-End Management</title>
	<%@ include file="/back-end/files/sb_head.file"%>
</head>
<body class="sb-nav-fixed">
		<%@ include file="/back-end/files/sb_navbar.file"%> <!-- 引入navbar (上方) -->
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">            
            	<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set> <!-- 在listAllXXX.jsp，加上這行，給sb_sidebar.file的參數-Home -->
                <%-- <c:set value="movieAdd" var="urlRecog"></c:set> --%> <!-- 在addXXX.jsp，加上這行，「value」請參照「sb_sidebar.file」給予相對應的值，給sb_sidebar.file的參數-Add -->
                <%-- <c:set value="movieSub" var="urlRecog"></c:set> --%> <!-- 在除了以上兩個jsp以外的子頁面，加上這行，「value」請參照「sb_sidebar.file」給予相對應的值，給sb_sidebar.file的參數-Sub -->         
				<%@ include file="/back-end/files/sb_sidebar.file"%> <!-- 引入sidebar (左方) -->
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                    <br>
                    <br>
                    <!-- PUT HERE Start-->
                    <div  class="form-group" style="height: 10vh;">
                    	<b>請選擇欲查看的檢舉分類：</b>
                    	<select id="selectReportType" class="form-control" style="width: 15vw; display: inline-block;">
                    		<option>請選擇</option>
                    		<option value="listAllArticleReport">文章檢舉列表</option>
                    		<option value="listAllArticleReplyReport">留言檢舉列表</option>
                    	</select>
                    </div>

                    <h3 id="tableTitle" class="h3-style" style="display: inline-block;"></h3>
					<hr style="background-color:#bb9d52; height:1.5px;">
		            <div id="listAllReport" style="font-size: 0px">

					</div>
                                        
                    <!-- PUT HERE End-->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="<%=request.getContextPath()%>/resource/js/moment-with-locales.min.js" ></script>
		
		<script>
		$(document).ready(function(){
			$('#listAllReport').hide();
			
			$('#selectReportType').change(function(){
				if($(this).val() == "listAllArticleReport"){
					//全部文章檢舉
					debugger;
					clearListAllReport();
					$.ajax({
						type: 'POST',
						url: '<%=request.getContextPath()%>/art/artRpt.do',
						data: {'action':$(this).val()},
						dataType: 'json',
						success: function(artRptVO){
							$('#listAllReport').append('<div><div class="inlineBlockLongDIV">檢舉編號</div><div class="inlineBlockDIV">作者</div><div class="inlineBlockLongDIV">文章標題</div><div class="inlineBlockLongDIV">檢舉時間</div><div class="inlineBlockLongDIV">檢舉人</div><div class="inlineBlockLongDIV">檢舉理由</div><div class="inlineBlockDIV">狀態</div><div class="inlineBlockLongDIV">確認/取消</div></div>');
							$('#tableTitle').append("文章檢舉列表");
							$('#listAllReport').show();
							$(artRptVO).each(function(i, item){
								$('#listAllReport').append('<div class="bgGray"><div class="artRptNo inlineBlockLongDIV" data-value='+item.artRptNo+'>'+item.artRptNo+'</div><div class="inlineBlockDIV longText">'+item.memName+'</div><div class="inlineBlockLongDIV longText">'+item.artTitle+'</div><div class="inlineBlockLongDIV">'+moment(item.artRptTime).locale('zh_TW').format('LL')+'</div><div class="inlineBlockLongDIV longText">'+item.reportMemName+'</div><div class="inlineBlockLongDIV">'+item.artRptContent+'</div><div class="artRptStatus inlineBlockDIV">'+item.artRptStatus+'</div><div class="inlineBlockLongDIV"><button class="checkedButton btn-light btn-brd grd1 effect-1" style="color: #fff" data-value="'+item.artNo+'">'+item.artRptStatusButton+'</button></div></div>');					
							});						
						},
		                error: function () {
		                    console.log("AJAX-listAllArticleReport發生錯誤囉!")
		                }
					});
				}else if($(this).val() == "listAllArticleReplyReport"){
					//列出全部留言檢舉
					debugger;
					clearListAllReport();
					$.ajax({
						type: 'POST',
						url: '<%=request.getContextPath()%>/art/artRepRpt.do',
						data: {'action':$(this).val()},
						dataType: 'json',
						success: function(artRepRptVO){
							$('#listAllReport').append('<div><div class="inlineBlockLongDIV">檢舉編號</div><div class="inlineBlockLongDIV">留言文章</div><div class="inlineBlockLongDIV">留言內容</div><div class="inlineBlockLongDIV">檢舉時間</div><div class="inlineBlockLongDIV">檢舉人</div><div class="inlineBlockLongDIV">檢舉理由</div><div class="inlineBlockDIV">狀態</div><div class="inlineBlockLongDIV">確認/取消</div></div>');
							$('#listAllReport').show();
							$('#tableTitle').append("留言檢舉列表");
							$(artRepRptVO).each(function(i, item){
								$('#listAllReport').append('<div class="bgGray"><div class="artRepRptNo inlineBlockLongDIV" data-value='+item.artRepRptNo+'>'+item.artRepRptNo+'</div><div class="inlineBlockLongDIV longText">'+item.artTitle+'</div><div class="inlineBlockLongDIV">'+item.artRepContent+'</div><div class="inlineBlockLongDIV">'+moment(item.artRepRptTime).locale('zh_TW').format('LL')+'</div><div class="inlineBlockLongDIV longText">'+item.reportMemName+'</div><div class="inlineBlockLongDIV">'+item.artRepRptReson+'</div><div class="artRepRptStatus inlineBlockDIV">'+item.artRepRptStatus+'</div><div class="inlineBlockLongDIV"><button class="checkedReplyButton btn-light btn-brd grd1 effect-1" style="color: #fff" data-value="'+item.artRepNo+'">'+item.artRepRptStatusButton+'</button></div></div>');					
							});						
						},
		                error: function () {
		                    console.log("AJAX-listAllArticleReplyReport發生錯誤囉!")
		                }
					});
				}
			});				

			//更新文章檢舉狀態
			changeArticleReport();
			
			//更新留言檢舉狀態
			changeArticleReplyReport();
						
		});

		
		//更改確認文章檢舉
		function changeArticleReport(){
			$('#listAllReport').on('click', '.checkedButton', function(event){
				console.log('changeArticleReport ok!');
				debugger;
				$.ajax({
					type: 'POST',
					url: '<%=request.getContextPath()%>/art/artRpt.do',
					data: {'action':'changeArticleReport', 'artNo':$(event.currentTarget).attr('data-value'), 'artRptNo':$(event.currentTarget).parent().siblings('.artRptNo').attr('data-value')},
					dataType: 'json',
					success: function(artRptVO){
						debugger;
						$(artRptVO).each(function(i, item){
							$(event.currentTarget).text(item.artRptStatusButton);
							$(event.currentTarget).parent('div').siblings('.artRptStatus').text(item.artRptStatus);
						});
					},
	                error: function () {
	                    console.log("AJAX-changeArticleReport發生錯誤囉!")
	                }					
				});
			});
		};
		
		//更改確認留言檢舉
		function changeArticleReplyReport(){
			$('#listAllReport').on('click', '.checkedReplyButton', function(event){
				console.log('changeArticleReplyReport ok!');
				debugger;
				$.ajax({
					type: 'POST',
					url: '<%=request.getContextPath()%>/art/artRepRpt.do',
					data: {'action':'changeArticleReplyReport', 'artRepNo':$(event.currentTarget).attr('data-value'), 'artRepRptNo':$(event.currentTarget).parent().siblings('.artRepRptNo').attr('data-value')},
					dataType: 'json',
					success: function(artRepRptVO){
						debugger;
						$(artRepRptVO).each(function(i, item){
							$(event.currentTarget).text(item.artRepRptStatusButton);
							$(event.currentTarget).parent('div').siblings('.artRepRptStatus').text(item.artRepRptStatus);
						});
					},
	                error: function () {
	                    console.log("AJAX-changeArticleReplyReport發生錯誤囉!")
	                }					
				});
			});
		};
		
		//清除檢舉列表
		function clearListAllReport(){
			$('#listAllReport').empty();
			$('#tableTitle').empty();
		};
		</script>
    </body>
</html>