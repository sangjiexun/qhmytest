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
		<title>权限管理-组织机构</title>
		
		<form action="" class="form-horizontal">
			<input type="hidden" id="pkid" name="pkid" value="${department.PKID }" />
		    <input type="hidden" id="parent_pkid" name="parent_pkid" value="${pd.pkid }" />
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">组织名称</label>
				<div class="col-xs-7">
					<input name="department_name" type="text" class="form-control" value="${department.DEPARTMENT_NAME}" placeholder="请输入内容" id="department_name"/>
				</div>
			</div>
			
			<c:choose>
				<c:when test="${department.PKID!=null && department.PKID!='' }">
					<div class="form-group">
						<label for="" class="col-xs-3 control-label">上级组织</label>
						<div class="col-xs-7">
							<input type="text" id="txt_departmentname" name="txt_departmentname" class="form-control" 
							value="${department.PARENT_NAME }"  placeholder="所属上级组织">  
							<div id="parendDepartmentTreeview" style="display: none;"></div>
						</div>
					</div>
				</c:when>
			</c:choose>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label">备注</label>
				<div class="col-xs-7">
					<textarea class="form-control" name="remark" id="remark" rows="5" cols="50" style="max-width:318px;min-width:318px;">${department.REMARK}</textarea>
				</div>
			</div>
		</form>

		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
		
		<script type="text/javascript" src="${basepath }static/js/myjs/department-add.js"></script>
		
