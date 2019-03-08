<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("basepath", basePath);
%>
		<meta charset="UTF-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<title>导入发布缴费名单</title>
		
	    <link rel="stylesheet" href="${basepath }static/css/bs/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${basepath }static/css/jf_custom.css" />
		<link rel="stylesheet" href="${basepath }static/css/framework-font.css" />
		<link href="${basepath}static/js/layer/skin/default/layer.css" rel="stylesheet">
		<script type="text/javascript" src="${basepath }static/css/bs/js/bootstrap.js"></script>
		<script src="${basepath}static/js/layer/layer.js"></script>
	    <script src="${basepath}static/js/bootstrap-dialog.min.js"></script>
	    <link href="${basepath}static/css/bootstrap-table.min.css" rel="stylesheet">
		<script src="${basepath}static/js/bootstrap-table.min.js"></script>
	    <script src="${basepath}static/js/bootstrap-table-zh-CN.min.js"></script>
	    <link rel="stylesheet" href="static/gxjf/css/Employee.css" />
	    <link href="${basepath }static/css/stream-v1.css" rel="stylesheet" type="text/css">
	    
	    <style>
	    	.bs-bars{
	    		margin-top:0 !important;
	    	}
	    	.bootstrap-dialog-title{
	    		font-size:16px !important;
	    		font-weight:bold;
	    	}
	    </style>
		
		<div style="margin-left:10px;">
			<input type="hidden" value="${pd.item_pkid }" id="item_pkid" name="item_pkid" >
			<input type="hidden" value="${pd.GRADE_PKID }" id="GRADE_PKID" name="GRADE_PKID" >
			<input type="hidden" value="${pd.CLASSTYPE_PKID}" id="CLASSTYPE_PKID" name="CLASSTYPE_PKID" >			
			<div class="row clearfix">
				<div class="col-md-12 column" style="margin-bottom:15px;">
					<!-- 选择文件按钮 -->
					<button id="i_select_files" type="button" class="btn btn-danger" style="float:left;margin-right:5px;"/>
					<!-- end 选择文件按钮 -->
					<button id="downloadfbjf" type="button" class="btn btn-danger">下载模板</button>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="i_stream_files_queue" style="width: 550px;"></div>
				</div>
			</div>
			
			<div class="row clearfix">
				<div class="col-md-12 column">
					<table>
						<tr>
							<td id="totalCount"></td>
							<td id="successCount"></td>
							<td id="failCount"></td>
							<td><a id="mulu" href="" style="display: none;">下载错误文件</a></td>
						</tr>
					</table>
				</div>
			</div>
	   </div>

		<script type="text/javascript">
			var _basepath = "${basepath}";
			var _uuid = "${uuid}";
		</script>
		
		<!-- 上传插件 -->
		<script type="text/javascript" src="${basepath }static/js/stream-v1.js"></script>
		<script type="text/javascript" src="${basepath }static/js/myjs/import/importjffb.js"></script>
