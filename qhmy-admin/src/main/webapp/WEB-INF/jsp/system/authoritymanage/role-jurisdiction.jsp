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
		
		<!-- 权限树 -->
		<div class="row-fluid kema_fluid">
			<div id="menuAndButtonTreeview" style="overflow:auto;"></div>
			<input name="menuAndButtonTreeData" id="menuAndButtonTreeData" type="hidden" value='${menuAndButtonTreeData }' />
		</div>
		<!-- end 权限树 -->
		
		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
		
		<script type="text/javascript" src="${basepath }static/js/myjs/role-jurisdiction.js"></script>
