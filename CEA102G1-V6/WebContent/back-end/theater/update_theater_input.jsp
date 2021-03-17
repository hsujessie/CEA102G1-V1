<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.theater.model.*" %>
<%
	TheVO theaterVO = (TheVO) request.getAttribute("theaterVO");
%>
<html>
<head>
	<title>Back-End Management</title>
	<%@ include file="/back-end/files/sb_head.file"%>
	<style>
  #table1 {
	width: 750px;
	margin: 5px auto 5px auto;
    background-color: rgb(255,255,255);
    border-radius: 10px;
	-webkit-box-shadow: 0px 3px 5px rgb(8,8,8, 0.3);
	-moz-box-shadow: 0px 3px 5px rgb(8,8,8, 0.3);
	box-shadow: 0px 3px 5px rgb(8,8,8, 0.3);
  }
 #table1 th td{
  	box-sizing:border-box;
    border-radius: 10px;
  }
 #table1 th{
  	width: 200px;
  	padding: 10px 0px 10px 70px;
  }
 #table1 td{
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
  #table2{
	margin: 100px auto 5px auto;
  }
 #tbody1 tr td {
/*  box-sizing:border-box; */
 	width:30px;
 	height:30px;
/*  	background-color:#bb9d52; */
 	border:5px solid;
 	border-color:white;
 }
 .s0{
 	background-color:#6AD5D2;
 	}
 .s3{
 	background-color:#D44444;
 	}
 .s9{
 	background-color:#103C3B;
 	}
</style>
</head>
<body class="sb-nav-fixed">
		<%@ include file="/back-end/files/sb_navbar.file"%> <!-- 引入navbar (上方) -->
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">            
            	<%-- <c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set> --%> <!-- 在listAllXXX.jsp，加上這行，給sb_sidebar.file的參數-Home -->
                <%-- <c:set value="movieAdd" var="urlRecog"></c:set> --%> <!-- 在addXXX.jsp，加上這行，「value」請參照「sb_sidebar.file」給予相對應的值，給sb_sidebar.file的參數-Add -->
                <%-- <c:set value="movieSub" var="urlRecog"></c:set> --%> <!-- 在除了以上兩個jsp以外的子頁面，加上這行，「value」請參照「sb_sidebar.file」給予相對應的值，給sb_sidebar.file的參數-Sub -->         
				<%@ include file="/back-end/files/sb_sidebar.file"%> <!-- 引入sidebar (左方) -->
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                    
                    <!-- PUT HERE Start-->
                    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/theater/theater.do" id="form">
                    <h3 class="h3-style listOne-h3-pos">電影修改</h3>
                    <table id="table1">
                    	<tr>
                    		<th>廳院編號</th>
                    		<td><%=theaterVO.getThe_no()%></td>
                    	</tr>
                    		<jsp:useBean id="movie_versionSvc" scope="page" class="com.movie_version.model.MovVerService" />
						<tr>
							<th>種類</th>
							<td><select size="1" name="movver_no">
								<c:forEach var="movie_versionVO" items="${movie_versionSvc.all}">
									<option value="${movie_versionVO.movver_no}" ${(theaterVO.movver_no==movie_versionVO.movver_no)?'selected':'' } >${movie_versionVO.movver_name}
								</c:forEach>
							</select></td>
						</tr>
                    </table>
                    <table id="table2">
					    <tbody id="tbody1">
					    </tbody>
					</table>
				 <input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>">
			     <input type="hidden" name="the_no"     value="${theaterVO.the_no}">
			     <input type="hidden" name="action"     value="update">
			     <input type="hidden" id="the_seat" name="the_seat"     value="">
			    <a class="btn btn-light btn-brd grd1 effect-1 btn-pos" style="margin: 1% 0 1% 50%;" >
				<input type="submit" value="送出" class="input-pos">
				</a>
					</FORM>
					<!-- PUT HERE End-->
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
		<script type="text/javascript">
		window.onload=function () {
			let index = 0;
			let theSeat = '<%=theaterVO.getThe_seat()%>';
			let tableData="<tr><td></td>";
			
// 			  for(var i=0;i<theSeat.length;i++){
// 			    tableData+="<td>"+theSeat.charAt(i)+"</td>"
// 			  }
			for(i=0;i<20;i++){
				tableData+="<td data-column="+(i+1)+">";
			}
			tableData+="</tr>"
			for(i=0;i<20;i++){
				tableData+="<tr>";
					tableData+="<td data-row="+(i+1)+">";
				for(j=0;j<20;j++){
					switch(theSeat.charAt(index)) {
						case '0':
							tableData+="<td class=s0";
							break;
						case '3':
							tableData+="<td class=s3";
							break;
						case '9':
							tableData+="<td class=s9";
							break;
					}
					tableData+=" data-seat=true data-row="+(i+1)+" data-column="+(j+1)+"></td>";
					index++;
				}
			  tableData+="</tr>"
			}
		
			  //現在tableData已經生成好了，把他賦值給上面的tbody
			  $("#tbody1").html(tableData)
				console.log(theSeat);
				console.log(theSeat.length);
				console.log(theSeat.charAt(0));
		console.log($("[data-seat]"));
 		$("[data-seat]").on("click",function(){
 			if($(this).attr("class")==="s0"){
 			$(this).attr("class","s3");
 			} else if($(this).attr("class")==="s3") {
 			$(this).attr("class","s9"); 				
 			} else if($(this).attr("class")==="s9") {
 			$(this).attr("class","s0"); 				
 			} 				
 		});
		
		$("a").click(function(e){
			e.preventDefault();
			let seatClass = "";
				$("[data-seat]").each(function(){
					seatClass+=$(this).attr("class")[1];
				});
			console.log(seatClass);
			
 			$("#the_seat").val(seatClass);
 			$("#form").submit();
		});
  		}
</script>
    </body>
</html>