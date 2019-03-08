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
		<title>预定分配</title>
		<style type="text/css">
			html { overflow-x:hidden; }
		</style>
	</head>
	<link href="${basepath}static/css/bootstrapValidator.min.css" rel="stylesheet">
	<script src="static/js/bootstrap-dialog.min.js"></script>
	<!-- ztree所需css以及js -->
    <link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css">
    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script>
    <!--end ztree所需css以及js -->
	    
    <script type="text/javascript">
		var _basepath = "${basepath}";
	</script>
	<style type="text/css">
		html {overflow-x:hidden;}
		.jf_ZtBox{
			left:0 !important;
			width:300px;
		}
	</style>
	<body>
		<div class="form-inline" style="margin-bottom:15px;">
			<select name="" class="form-control jf_FltConditions" id="part_school">
                  <option value="">文化课学校</option>
                  <c:forEach items="${list_wenhuake}" var="var">
                       <option value="${var.PKID}" >${var.SCHOOLNAME }</option>
                  </c:forEach>
			</select>
			<select name="" class="form-control jf_FltConditions" id="RUXUENIANFEN">
                  <option value="">入学年份</option>
                  <c:forEach items="${ruxuenianfenList}"  var="var">
                       <option value="${var.DICTIONARIES_ID }"  <c:if test="${var.NAME==pd.totalYear}">selected="selected"</c:if>>${var.NAME}</option>
                  </c:forEach>
	   		</select>
			<select name="" class="form-control jf_FltConditions" id="class_type">
                  <option value="">班型</option>
                  <c:forEach items="${list_classType}" var="var">
                       <option value="${var.DICTIONARIES_ID}" >${var.NAME }</option>
                  </c:forEach>
			</select>
			<select name="" class="form-control jf_FltConditions" id="XINGBIE" style="width:18%;">
				<option value="">性别</option>
				<option value="M">男</option>
				<option value="W">女</option>
			</select>
			<select name="" class="form-control jf_FltConditions" id="IS_FENPEI" style="width:18%;">
				<option value="0">未分配</option>
				<option value="1">已分配</option>
				<option value="">分配状态</option>
			</select>
			<input type="text" class="form-control jf_FltConditions sfzkzqsousuo" id="keywords" style="width:18%;" placeholder="身份证/学号/姓名"/>
			<button class="btn btn-danger sfzkzqsousuobtn" id="dormOrderQuery"><span class="fa fa-search"></span> 查询</button>
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_fenpei"><span class="fa fa-tag" ></span>分配</a>
		</div> 
		<table class="table table-striped table-hover table-border jf_table" id="dormOrderTable" style="text-align: center;">
		</table>
		<input id ="SESSION_MENU_BUTTONS" value="${SESSION_MENU_BUTTONS }" style="display:none;">
	</body>
	<script type="text/javascript" src="${basepath}static/js/myjs/dormOrder.js"></script>
	<OBJECT id=SynCardOcx1 style="WIDTH: 0px; HEIGHT: 0px"
		codeBase=${basepath}static/js/myjs/SynCardOcx.CAB#version=1,0,0,1
		classid=clsid:4B3CB088-9A00-4D24-87AA-F65C58531039></OBJECT>
	<script type="text/javascript" src="${basepath}static/js/myjs/sfzsousuo.js"></script>
</html>
