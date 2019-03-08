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
<title>制卡DEMO</title>
<base href="<%=basePath%>">
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!-- jsp文件头和头部 -->
<%@include file="system/index/top.jsp"%>

<link href="${basepath }static/css/bootstrap-dialog.min.css" rel="stylesheet">

<script type="text/javascript">
	
	
	
</script>

</head>
<body>
	<!-- 引入明华读卡器cab文件 -->
	<OBJECT id=MWRFATL style="WIDTH: 0px; HEIGHT: 0px"
		codeBase=${basepath}static/js/myjs/rf35.cab#version=1,0,0,1
		classid=CLSID:8C11E03B-3F3E-4457-A5A1-B9ECEC5FDA80></OBJECT>
	
	<input class="zhika" type="button" value="制卡" />
	
	<input class="ceshi" type="button" value="测试阻断" />
	
	<!-- 制卡弹出层 -->
	<div id="zhikaDialog">
		
	</div>
	<!-- end 制卡弹出层 -->
	
	
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
	
	<!-- 引入读卡器API -->
	<script type="text/javascript" src="${basepath }static/js/myjs/dukaqi.js"></script>
	<!-- end 引入读卡器API -->
	
	<script type="text/javascript" src="${basepath }static/js/myjs/zhika.js"></script>
	
	<link href="${basepath }static/css/labor.css" rel="stylesheet">
	
</body>
</html>