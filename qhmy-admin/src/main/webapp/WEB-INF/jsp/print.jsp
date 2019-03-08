<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("basepath", basePath);
	
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>打印</title>
<base href="<%=basePath%>">
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!-- jsp文件头和头部 -->
<%@include file="system/index/top.jsp"%>
<link href="${basepath }static/css/bootstrap-dialog.min.css" rel="stylesheet">

</head>
<body>
	
	<button type="button" id="dayin" value="打印">打印</button>
	
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@include file="system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/jquery.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/bootstrap.js"></script>
	<!-- 日期框 -->
	<script src="static/js/bootstrapValidator.js"></script>
	<!--提示框-->
	<script src="static/js/layer/layer.js"></script>
	
	<script src="static/js/bootstrap-dialog.min.js"></script>
	
	<link href="${basepath }static/css/labor.css" rel="stylesheet">
	
	
	<script type="text/javascript">
		$(function(){
			
			var _basepath = "${basepath}";
			
			$("#dayin").bind("click",function(){
				window.location.href=_basepath+"demo/printexce.json";
			});
			
			
		});
	</script>
	
</body>
</html>