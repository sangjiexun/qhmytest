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
		<title>入学年份</title>
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
	<input id ="SESSION_MENU_BUTTONS" value="${SESSION_MENU_BUTTONS }" style="display:none;">
		<div class="form-inline">
			<input type="text" class="form-control jf_FltConditions" id="keywords" style="width:18%;" placeholder="入学年份"/>
			<a class="btn btn-danger" id="schoolYearQuery"><span class="fa fa-search"></span> 查询</a>
			<c:if test="${SESSION_MENU_BUTTONS.nianji_xz == '1' }">
				<a href="javascript:void(0);" class="btn btn-danger" id="btn_add"><span class="fa fa-plus" ></span> 新增</a>
			</c:if>
		</div> 
		<table class="table table-striped table-hover table-border jf_table" id="schoolYearTable" style="text-align: center;">
		</table>
	</body>
	<script type="text/javascript" src="${basepath}static/js/myjs/schoolYear.js"></script>
</html>
