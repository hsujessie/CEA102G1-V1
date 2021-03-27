<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<title>餐點修改</title>	
	<%@ include file="/back-end/files/sb_head.file"%>

<style>
  table {
	width: 750px;
	margin: 5px auto 5px auto;
    background-color: rgb(255,255,255);
    border-radius: 10px;
	-webkit-box-shadow: 0px 3px 5px rgb(8,8,8, 0.3);
	-moz-box-shadow: 0px 3px 5px rgb(8,8,8, 0.3);
	box-shadow: 0px 3px 5px rgb(8,8,8, 0.3);
  }
  th,td{
  	box-sizing:border-box;
    border-radius: 10px;
  }
  th{
  	width: 200px;
  	padding: 10px 0px 10px 70px;
  }
  td{
  	width: 250px;
  	padding: 10px 20px 10px 30px;
    border-bottom: 2px dotted #bb9d52;
  }
  .listOne-h3-pos{
  	display: inline-block;	
  	margin-left: 45%;
  }
  .ml-ten{
  	margin-left: 10px;
  }
    #myImg {
  	height: 150px;
  	width: 150px;
  }
  
    input.form-control,select.form-control {
  	width:50%;
  	display:inline-block;
  }
  
</style>
</head>
<body class="sb-nav-fixed">
		<%@ include file="/back-end/files/sb_navbar.file"%> <!-- 引入navbar (上方) -->
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
            	<c:set value="movieAdd" var="urlRecog"></c:set> <!-- 給sb_sidebar.file的參數-Add -->
				<%@ include file="/back-end/files/sb_sidebar.file"%> <!-- 引入sidebar (左方) -->
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                    
                       <!-- addMovie Start -->  
                       
						<FORM method="post" action="<%=request.getContextPath()%>/foo/foo.do" enctype="multipart/form-data">
						<h3 class="h3-style listOne-h3-pos">餐點修改</h3>
						<table>
							<tr>
								<th>餐點名稱</th>
								<td><input class="sty-input mr-left mr-btm-normal form-control" type="text" name="fooName" value="${fooVO.fooName}" required/>${errorMsgs.fooName}</td>		
							</tr>
							<tr>
							<jsp:useBean id="fooCatSvc" scope="page" class="com.food_cate.model.FooCatService" />
								<th>餐點類別</th>
								<td>
									<select size="1" name="fooCatNo" class="form-control">
										<c:forEach var="fooCatVO" items="${fooCatSvc.all}">
											<option value="${fooCatVO.fooCatNo}" ${(fooVO.fooCatNo==fooCatVO.fooCatNo)?'selected':'' } >${fooCatVO.fooCatName}
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th>餐點圖片</th>
								<td>
									<input id="myfile" type="file" name="fooImg">${errorMsgs.fooImg}
									<img id="myImg" src="<%=request.getContextPath()%>/util/imgReader${fooVO.fooImgParam}">
								</td>
							</tr>
							<tr>
								<th>餐點售價</th>
								<td style="color:red;"><input class="sty-input mr-left mr-btm-normal form-control" name="fooPrice" type="text" value="${fooVO.fooPrice}" required />${errorMsgs.fooPrice}</td>
							</tr>
							<tr>
								<th>狀態</th>
								<td>
									<select name="fooStatus" class="form-control">
										<c:forEach varStatus="i" begin="0" end="1">
											<option value="${i.index}" ${i.index==fooVO.fooStatus ? "selected" :""}>${i.index=="0" ? "上架" : "下架"}
										</c:forEach>
									</select>
								</td>
							</tr>
						</table>
						<br>
						<input type="hidden" name="fooNo" value="${fooVO.fooNo}">
						<input type="hidden" name="action" value="update">
						<input type="hidden" name="requestURL" value="${param.requestURL}">
						<input type="hidden" name="whichPage" value="${param.whichPage}">
						<a class="btn btn-light btn-brd grd1 effect-1 btn-pos" style="margin: 1% 0 1% 50%;" >
							<input type="submit" value="送出" class="input-pos">
						</a>
						</FORM>
                       <!-- addMovie End -->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
		<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
		<script>
                	$("#myfile").change(function(e) {
                		let files = e.target.files;
                		let file = files[0];
                		let reader = new FileReader();
                        reader.readAsDataURL(file);
                        
                        if (file.type.indexOf("image") > -1) {
                        	reader.addEventListener("load", function(e) {
                            	let result = e.target.result;
                            	$("#myImg").css("display","block");
                            	$("#myImg").attr("src", result);
                       	 	});
                        } else {
                        	swal("請上傳圖片", "所選的檔案不是圖片,請重新確認", "warning", {button: "確定"});
                        }
                	});
                </script>
</body>



</html>