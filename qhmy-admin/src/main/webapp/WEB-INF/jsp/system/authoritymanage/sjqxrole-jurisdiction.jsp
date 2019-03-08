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
		<div class="btn btn-danger pull-left" style="margin-right:15px;margin-bottom:10px;" id='quanxuan'>全选</div>
		<div class="btn btn-default pull-left" id='quxiaoqx'>取消</div>
		<div class="clearfix"></div>
		<!-- 权限树 -->
		<div class="row-fluid kema_fluid">
			<div id="sjqxTreeview" style="overflow:auto;"></div>
			<input name="sjqxTreeData" id="sjqxTreeData" type="hidden" value='${qxTreeData }' />
		</div>
		<!-- end 权限树 -->
		
		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
		
		<script type="text/javascript" src="${basepath }static/js/myjs/sjqxrole-jurisdiction.js"></script>
