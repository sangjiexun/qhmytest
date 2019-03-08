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
		<title>权限管理-修改密码</title>
		
		<form action="" class="form-horizontal">
			<div class="form-group">
				<label for="" class="col-xs-3 control-label">原密码</label>
				<div class="col-xs-7">
					<input name="username" type="password" id="oldpwd"  type="text" class="form-control" placeholder="请输入内容" id="username"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label">新密码</label>
				<div class="col-xs-7">
					<input name="department_name" type="password" id="newpwd" class="form-control" value="${pd.department_name}" placeholder="请输入内容" id="department_name"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label">确认密码</label>
				<div class="col-xs-7">
					<input name="name" type="password" class="form-control"  placeholder="请输入内容" id="confirmpwd"/>
				</div>
			</div>
			
			
		</form>

		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>

