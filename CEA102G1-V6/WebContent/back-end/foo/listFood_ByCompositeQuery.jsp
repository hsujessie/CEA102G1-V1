<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.food.model.*"%>

<html>
<head>
<title>Foods Management</title>
<%@ include file="/back-end/files/sb_head.file"%>

<style>
.success-span {
	color: #bb9d52;
	position: absolute;
	top: 7%;
	left: 19%;
}

.th-adjust {
	/*  		width: 120px;  */
	
}

.form-sty {
	margin: 20px 0 0 10px;
}

img {
	width: 50px;
	height: 50px;
}

table {
	word-wrap: break-word;
}
.form-control {
	display : inline-block;
	width: 25%;
}
</style>
<body class="sb-nav-fixed">
	<%@ include file="/back-end/files/sb_navbar.file"%>
	<!-- 引入navbar (上方) -->
	<div id="layoutSidenav">
		<div id="layoutSidenav_nav">
			<c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set>
			<!-- 給sb_sidebar.file的參數-Home -->
			<%@ include file="/back-end/files/sb_sidebar.file"%>
			<!-- 引入sidebar (左方) -->
		</div>
		<div id="layoutSidenav_content">
			<main>
				<div class="container-fluid">
					<!-- error message Start -->
					<h3 class="h3-style" style="display: inline-block;">餐點列表</h3>
					<c:if test="${addSuccess != null}">
						<span class="success-span"> ${addSuccess} <i
							class="fa fa-hand-peace-o"></i>
						</span>
					</c:if>
					<c:if test="${updateSuccess != null }">
						<span class="success-span"> ${updateSuccess} <i
							class="far fa-laugh-wink"></i>
						</span>
					</c:if>
					<!-- error message End -->

					<!-- search Start -->
					<div class="row " style="margin: -60px 0 20px 0;">
						<div class="col-2"></div>
						<div class="col-10">
							<FORM class="form-sty" METHOD="post" ACTION="<%=request.getContextPath()%>/foo/foo.do">
								<b>上架狀態</b> <select name="foo_status" style="width: 58px;padding:0px;" class="form-control">
									<option value="">全部
									<c:forEach varStatus="i" begin="0" end="1">
										<option value="${i.index}">${i.index==0?"上架":"下架"}
									</c:forEach>
								</select>
								<jsp:useBean id="fooCatSvc" class="com.food_cate.model.FooCatService"/>
								<b>商品類別</b>
								<select name="foocat_no" style="width: 114px;" class="form-control">
										<option value="">全部
									<c:forEach var="fooCatVO" items="${fooCatSvc.all}">
										<option value="${fooCatVO.fooCatNo}">${fooCatVO.fooCatName}
									</c:forEach> 
								</select> 
								<b>商品售價</b>
									<select  id="s1" style="width: 80px;" class="form-control" name="foo_price1">
										<option value="0">0
										<option value="50">50
										<option value="100">100
										<option value="150">150
										<option value="200">200
									</select>
									~
									<select id="s2" style="width: 80px;" class="form-control" name="foo_price2">
										<option value="">不限
										<option value="50">50
										<option value="100">100
										<option value="150">150
										<option value="200">200
									</select>
								<b>商品名稱</b> <input name="foo_name" class="form-control" style="width: 100px;"/>
								<input type="hidden" name="action" value="listFoods_ByCompositeQuery"> 
								<a
									class="btn btn-light btn-brd grd1 effect-1"> <input
									type="submit" value="搜尋" class="input-pos">
								</a>
							</FORM>
						</div>
					</div>
					<!-- search End -->

					<!-- listAllMovie Start -->
					<div class="row">
						<div class="col">
							<table class="table table-hover">
								<thead>
									<tr style="border-bottom: 3px solid #bb9d52;">
										<th>列表編號</th>
										<th class="th-adjust">姓名</th>
										<th>照片</th>
										<th>帳號</th>
										<th>信箱</th>
										<th>在職狀態</th>
										<th>查看權限</th>
										<th>修改</th>
									</tr>
								</thead>

								<tbody>
								<jsp:useBean id="listFoods_ByCompositeQuery" scope="request" type="java.util.List<FooVO>"/>
								<%@ include file="/back-end/foo/pages/page1_ByCompositeQuery.file"%> 
									<c:forEach var="fooVO" items="${listFoods_ByCompositeQuery}" varStatus="no" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
										<tr class="sty-height" valign='middle'
											${(fooVO.fooNo==param.fooNo) ? 'style="background-color:#bb9d52; color:#fff;"':''}>
											<td>${no.index+1}</td>
											<td>${fooVO.fooName}</td>
											<td>${fooCatSvc.getOneFooCat(fooVO.fooCatNo).fooCatName}</td>
											<td><img
												src="<%=request.getContextPath()%>/util/imgReader${fooVO.fooImgParam}"></td>
											<td>$ ${fooVO.fooPrice}</td>
											<td
												${fooVO.fooStatus=="0" ?"style='color:blue;'":"style='color:red;'"}>${fooVO.fooStatus=="0" ?"上架中":"已下架"}</td>
											<td>
												<form method="post" action="<%=request.getContextPath()%>/foo/foo.do">
													<a class="btn btn-light btn-brd grd1 effect-1"> 
														<input type="hidden" name="fooNo" value="${fooVO.fooNo}"/>
														<input type="hidden" name="action" value="getOne_For_Update"/> 
														<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"/> 
														<input type="hidden" name="whichPage" value="${param.whichPage}"/> 
														<input type="submit" value="修改" class="input-pos"/>
													</a>
												</form>
											</td>
											<td>
												<form method="post" action="<%=request.getContextPath()%>/foo/foo.do">
													<a class="btn btn-light btn-brd grd1 effect-1"> 
														<input type="hidden" name="action" value="change_Status"/> 
														<input type="hidden" name="fooNo" value="${fooVO.fooNo}"/>
														<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"/> 
														<input type="hidden" name="whichPage" value="${param.whichPage}"/> 
														<input type="submit" value=${(fooVO.fooStatus)==0?"下架":"上架"} class="input-pos"/>
													</a>
												</form>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<%@ include file="/back-end/foo/pages/page2_ByCompositeQuery.file" %>
						</div>
					</div>
					<!-- listAllMovie End -->

				</div>

				<div class="modal fade" id="exampleModal" tabindex="-1"
					aria-labelledby="exampleModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel"> </h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body" id="modal-body"> </div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal" style="width: 60px; padding: 0px;">關閉</button>
								<form method="post" action="<%=request.getContextPath()%>/adm/adm.do">
								<input id="admNo" type="hidden" name="admNo" value=""/>
														<input type="hidden" name="action" value="getOne_for_update"/> 
														<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"/> 
														<input type="hidden" name="whichPage" value="${param.whichPage}"/> 
								<button type="submit" class="btn btn-primary" style="width: 90px; padding: 0px;">前往修改</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</main>
			<%@ include file="/back-end/files/sb_footer.file"%>
		</div>
	</div>
	<%@ include file="/back-end/files/sb_importJs.file"%>
	<script>
		$("#s1").change(function() {
			let choose = parseInt($(this).val());
			$("#s2").empty();
			$("#s2").append("<option value=''>不限</option>");
			if (choose !== 200) {
				for ( let i = choose + 50; i <= 200; i+=50) {
					if (i !== 0)
						$("#s2").append("<option value=" + i +">" + i + "</option>");
				}
			}
			
		});
	</script>
	<!-- 引入template要用的js -->


</body>
</html>