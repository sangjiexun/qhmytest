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
<title>Stream demo</title>
<base href="<%=basePath%>">
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!-- jsp文件头和头部 -->
<link href="${basepath }static/css/stream-v1.css" rel="stylesheet" type="text/css">
<%@include file="system/index/top.jsp"%>
<link href="${basepath }static/css/bootstrap-dialog.min.css" rel="stylesheet">

<style>
	.btn-group > button {
		width: 100px;
	}
</style>

<script type="text/javascript">
	
	
	
</script>

</head>
<body>
	<div id="i_select_files">
	</div>

	<div id="i_stream_files_queue">
	</div>
	
	 <button onclick="javascript:_t.upload();">开始上传</button>|<button onclick="javascript:_t.stop();">停止上传</button>|<button onclick="javascript:_t.cancel();">取消</button>
	|<button onclick="javascript:_t.disable();">禁用文件选择</button>|<button onclick="javascript:_t.enable();">启用文件选择</button>
	
	<br>
		Messages:
		<div id="i_stream_message_container" class="stream-main-upload-box" style="overflow: auto;height:200px;">
		</div>
	<br>
	
	
	
	
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
	
	<!-- 文件上传 -->
	<script type="text/javascript" src="${basepath }static/js/stream-v1.js"></script>
	<!-- end 文件上传 -->
	
	<!-- 使用文件上传 --> 
	<script type="text/javascript" src="${basepath }static/js/myjs/upload.js"></script>
	<!-- end 使用文件上传 -->
	
	
</body>
</html>