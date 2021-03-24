<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ticket_type.model.*"%>


<html>
<head>
	<title>����ק�</title>
	<%@ include file="/back-end/files/sb_head.file"%>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/datetimepicker/jquery.datetimepicker.css" />
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
  .err-color{
    text-shadow: 0 0 0.1em #f87, 0 0 0.1em #f87;
    font-size: 14px;
  }
</style>
</head>
<body class="sb-nav-fixed">
		<%@ include file="/back-end/files/sb_navbar.file"%> <!-- �ޤJnavbar (�W��) -->
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <c:set value="movieSub" var="urlRecog"></c:set> <!-- ��sb_sidebar.file���Ѽ�-Sub -->         
				<%@ include file="/back-end/files/sb_sidebar.file"%> <!-- �ޤJsidebar (����) -->
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                       <h3 class="h3-style listOne-h3-pos">����ק�</h3>
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/ticket_type/ticket_type.do" name="form1">
<table>
	<jsp:useBean id="movie_versionSvc" scope="page" class="com.movie_version.model.MovVerService" />
	<tr>
		<td>��M����</td>
			<td><c:forEach var="movie_versionVO" items="${movie_versionSvc.all}">
                    <c:if test="${ticket_typeVO.movver_no==movie_versionVO.movver_no}">
	                    ${movie_versionVO.movver_name}
                    </c:if>
                </c:forEach>
			</td>
	</tr>
	<jsp:useBean id="identitySvc" scope="page" class="com.identity.model.IdeService" />
	<tr>
		<td>�����W��</td>
			<td><c:forEach var="identityVO" items="${identitySvc.all}">
                    <c:if test="${ticket_typeVO.ide_no==identityVO.ide_no}">
	                    ${identityVO.ide_name}
                    </c:if>
                </c:forEach>
			</td>		
	</tr>
	<tr>
		<td>����</td>
		<td><input required type="number"  min="0" name="tictyp_price" size="45" value="${ticket_typeVO.tictyp_price}" /></td>
	</tr>


</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="tictyp_no"  value="${ticket_typeVO.tictyp_no}">
<input type="hidden" name="movver_no"  value="${ticket_typeVO.movver_no}">
<input type="hidden" name="ide_no"  value="${ticket_typeVO.ide_no}">
<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"> <!--��e�X�ק諸�ӷ��������|,�qrequest���X��,�A�e��Controller�ǳ���椧��-->
						<a id="abled-btn" class="btn btn-light btn-brd grd1 effect-1 btn-pos" style="margin: 1% 0 1% 46%; display:block;" >
							<input type="submit" value="�e�X" class="input-pos">
						</a></FORM>
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- �ޤJtemplate�n�Ϊ�js -->
</body>






</html>