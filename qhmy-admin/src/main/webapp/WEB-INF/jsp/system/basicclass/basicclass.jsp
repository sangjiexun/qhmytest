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
		<title>班级管理</title>
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
			<select name="" class="form-control jf_FltConditions" id="banxing">
                        <option value="">班型</option>
                   <c:forEach items="${banXingList}" var="banxing">
                        <option value="${banxing.DICTIONARIES_ID }">${banxing.NAME }</option>
                   </c:forEach>
	   		</select>
			<select name="" class="form-control jf_FltConditions" id="grade">
                        <option value="">入学年份</option>
                   <c:forEach items="${gradeList}" var="grade">
                        <option value="${grade.DICTIONARIES_ID }">${grade.NAME }</option>
                   </c:forEach>
	   		</select>
			<input type="text" class="form-control jf_FltConditions" id="className" style="width:18%;" placeholder="班级名称"/>
			<a class="btn btn-danger" id="classQuery"><span class="fa fa-search"></span> 查询</a>
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_add"><span class="fa fa-plus" ></span> 新增</a>
		</div> 
		<table class="table table-striped table-hover table-border jf_table" id="classTable" style="text-align: center;">
		</table>
	</body>
	<script type="text/javascript" src="${basepath}static/js/myjs/basicclass.js"></script>
</html>
