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
  var _basepath = "${basepath}";
	
</script>

</head>
<body>
	
	<h1>假设此页面时编辑页面</h1>
	
	<div class="btn-toolbar" role="toolbar">
		<div class="btn-group">
			<button type="button" class="btn btn-default" id="i_select_files">
				<span class="glyphicon glyphicon-plus-sign"></span>添加文件
			</button>
			<button type="button" class="btn btn-default" id="baocun" >
				<span class="glyphicon glyphicon-plus-sign"></span>保存附件
			</button>
		</div>
	</div>
	
	<!-- 选择的文件列表 -->
	<table id="data_table" style="width: 30%" class="table tablesorter">
		<thead>
			<tr><th>编号</th>
				<th>文件</th>
				<th>大小</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="bootstrap-stream-container">
		</tbody>
	</table>
	<!-- 选择的文件列表 -->
	
	
	<!-- 点新增或编辑页面的时候，读取附件表，将附件循环显示到UL列表中,这只是其中一种方法，另一种方法可以将json对象数组直接传递给fhadminUpload对象 -->
	<div class="dbfujian_div" style="display: none;">
		<UL id="yscwj1">
			<li fileid="12123123123" filename="xxx.doc" filesize="50KB" >xxx.doc</li>
			<li fileid="45643645646" filename="yyy.doc" filesize="10KB" >yyy.doc</li>
		</UL>
		
		<UL id="自定义2">
			
		</UL>
		
		<UL id="自定义3">
			
		</UL>
	</div>
	<!-- end 点新增或编辑页面的时候，读取附件表，将附件循环显示到UL列表中,这只是其中一种方法，另一种方法可以将json对象数组直接传递给fhadminUpload对象 -->
	
	
	
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
	
	
	<!-- 文件上传二次封装 -->
	<script type="text/javascript" src="${basepath }static/js/myjs/upload.js"></script>
	<!-- end 文件上传二次封装 -->
	
	<!-- 本功能处理附件上传-->
	<script type="text/javascript" src="${basepath }static/js/myjs/fenbaofj_upload.js"></script>
	<!-- 本功能处理附件上传-->
	
	
	
	
</body>
</html>