<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.theater.model.*" %>
<%
	TheVO theaterVO = (TheVO) request.getAttribute("theaterVO");
%>
<html>
<head>
	<title>影廳修改</title>
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
  .listOne-h3-pos,#a-color{
  	margin-left: 50%;
  }
  .listOne-h3-pos{
  	display: inline-block; 
  }
  #a-color{
  	font-size: 16px;
  }
  .ml-ten{
  	margin-left: 10px;
  }
  #table2{
  margin: 0 auto;
  }
 #tbody1 [data-seat] {
 	width:40px;
 	height:40px;
 	border:5px solid;
 	border-color:white;
 	border-radius:10px;
 }
 .s0{
 	background-color:#cec0a7;
 	}
 .s3{
 	background-color:#FF9797;
 	}
 .s9{
 	background-color:#8E8E8E;
 	}
 .selector{
 	width:40px;
 	height:40px;
 	border:5px solid;
 	border-color:white;
  	border-radius:10px; 
  	background-color:#4A6768; 
  	opacity:0.1;
 }

.front-indicator {
    padding: 0px;
    width: 800px;
    margin: 15px auto 15px auto;
    background-color: #f6f6f6;
    color: #adadad;
    text-align: center;
    padding: 3px;
    border-radius: 5px;
}
#legend{
margin:100px auto 0 auto;
text-align: center;
padding-left: 0px;
list-style: none;
}
li .seatCharts-seat{
display: inline-block;
width:20px;
height:20px;
}
li .seatCharts-legendDescription{
display: inline-block;
line-height: 30px;
margin-left: 5px;
}
ul.seatCharts-legendList {
	text-align: center;
}
li.seatCharts-legendItem {
	display: inline-block;
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
                    <div id="legend" class="seatCharts-legend">
                    <ul class="seatCharts-legendList">
                                            <li class="seatCharts-legendItem">
                                                <div class="s0 seatCharts-seat seatCharts-cell available first-class"></div><span class="seatCharts-legendDescription">座位</span>
                                            </li>
                                            <li class="seatCharts-legendItem">
                                                <div class="s3 seatCharts-seat seatCharts-cell selected first-class"></div><span class="seatCharts-legendDescription">走道</span>
                                            </li>
                                            <li class="seatCharts-legendItem">
                                                <div class="s9 seatCharts-seat seatCharts-cell unavailable"></div><span class="seatCharts-legendDescription">禁售</span>
                                            </li>
                                        </ul>
                                        </div>
                    <div class="front-indicator">螢幕</div>
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
			
			for(i=0;i<20;i++){
				tableData+="<td class=selector data-column="+(i+1)+">";
			}
			tableData+="</tr>"
			for(i=0;i<20;i++){
				tableData+="<tr>";
					tableData+="<td class=selector data-row="+(i+1)+">";
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
 		$("#tbody1 td[data-seat!='true']").on("click",function(){
 			let row = $(this).attr("data-row");
 			let column = $(this).attr("data-column");
 			if(row){ 			
 				if($("[data-seat][data-row="+row+"]").first().attr("class")==="s0"){
 					$("[data-seat][data-row="+row+"]").attr("class","s3");
	 			} else if($($("[data-seat][data-row="+row+"]").first()).attr("class")==="s3") {
	 				$("[data-seat][data-row="+row+"]").attr("class","s9"); 				
	 			} else if($($("[data-seat][data-row="+row+"]").first()).attr("class")==="s9") {
	 				$("[data-seat][data-row="+row+"]").attr("class","s0"); 				
	 			}				
 			} else if (column){
 				if($("[data-seat][data-column="+column+"]").first().attr("class")==="s0"){
 					$("[data-seat][data-column="+column+"]").attr("class","s3");
	 			} else if($($("[data-seat][data-column="+column+"]").first()).attr("class")==="s3") {
	 				$("[data-seat][data-column="+column+"]").attr("class","s9"); 				
	 			} else if($($("[data-seat][data-column="+column+"]").first()).attr("class")==="s9") {
	 				$("[data-seat][data-column="+column+"]").attr("class","s0"); 				
	 			}					
 			}
 		})
 		
		$("a").click(function(e){
			e.preventDefault();
			let seatClass = "";
				$("[data-seat]").each(function(){
					seatClass+=$(this).attr("class")[1];
				});
			
 			$("#the_seat").val(seatClass);
 			$("#form").submit();
		});
  		}
</script>
    </body>
</html>