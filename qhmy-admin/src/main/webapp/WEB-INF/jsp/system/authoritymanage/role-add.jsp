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
		<title>权限管理-角色管理</title>
		
		<form action="" class="form-horizontal">
			<input type="hidden" id="pkid" name="pkid" value="${role.ROLE_ID }" />
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">角色名称</label>
				<div class="col-xs-7">
					<input name="role_name" type="text" class="form-control" value="${role.ROLE_NAME}" placeholder="请输入内容" id="role_name"/>
				</div>
			</div>
		</form>

		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
		
		<script type="text/javascript" src="${basepath }static/js/myjs/role-add.js"></script>
		
