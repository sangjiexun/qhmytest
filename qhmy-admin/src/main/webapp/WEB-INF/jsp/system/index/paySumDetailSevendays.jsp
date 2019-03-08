<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>缴费明细</title>
		<style type="text/css">
			html { overflow-x:hidden; }
		</style>
	</head>
	<link href="${basepath}static/css/bootstrapValidator.min.css" rel="stylesheet">
	<script src="static/js/bootstrap-dialog.min.js"></script>
	<script type="text/javascript">
	var _basepath = "${basepath}";
	</script>
	<body>
		<div style="background:white;padding:15px;border-radius:5px;">
			<form class="form-inline">
				<input type="text"  id="DATESTART" class="laydate-icon form-control" value="${pd.DATESTART}" placeholder="日期起" readonly="readonly"  /> ~
				<input type="text"  id="DATEEND" class="laydate-icon form-control" value="${pd.DATEEND}" placeholder="日期止" readonly="readonly"  />
				<input type="text" class="form-control jf_FltConditions" id="serchText" style="width:18%;" placeholder="学号/姓名/项目名"/>
				<a class="btn btn-danger" id="checkQuery"><span class="fa fa-search"></span> 查询</a>
				<a class="btn btn-danger" id="checkOut"><span class="fa fa-upload"></span> 导出</a>
			    <a class="btn btn-danger" id="backtohome"><span class="fa fa-reply"></span> 返回</a>
			</form> 
			<input hidden="hidden" id="paySumPKID" value="${pd.PKID}">
			<table class="table table-striped table-hover table-border jf_table" id="paySumDetailTable" style="text-align: center;">
			</table>
		</div>
	</body>
	<script type="text/javascript" src="${basepath}static/js/myjs/paySumDetail.js"></script>
	<script type="text/javascript">
	$("#backtohome").bind("click",function(){
		$('#nameli').load(_basepath+"main/gotopmain.php?r="+Math.random());
	});
	</script>
</html>
