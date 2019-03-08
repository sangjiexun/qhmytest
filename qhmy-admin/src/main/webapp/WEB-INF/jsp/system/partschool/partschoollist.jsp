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
		<title>合作学校管理</title>
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
			<input type="text" class="form-control jf_FltConditions" id="SCHOOLNAMESEARCH" style="width:18%;" placeholder="文化课学校"/>
			<a class="btn btn-danger" id="partSchoolQuery"><span class="fa fa-search"></span> 查询</a>
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_add"><span class="fa fa-plus" ></span>新增</a>
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_del"><span class="fa fa-trash-o"></span> 批量删除</a>
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_import"><span class="fa fa-sign-in"></span> 导入</a>
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_export"><span class="fa fa-upload"></span> 导出</a>
		</div> 
		<table class="table table-striped table-hover table-border jf_table" id="partSchoolTable" style="text-align: center;">
		</table>
	</body>
	<script type="text/javascript" src="${basepath}static/js/myjs/partschoollist.js"></script>
</html>
