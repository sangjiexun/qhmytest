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
		<title>权限管理-用户</title>
		
		<form action="" class="form-horizontal">
			<input type="hidden" id="user_id" name="user_id" value="${userPageData.USER_ID }" />
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">用户名称</label>
				<div class="col-xs-7">
					<input name="username" <c:if test="${userPageData.USER_ID!=null }">readonly='readonly'</c:if>   type="text" class="form-control" value="${userPageData.USERNAME }" placeholder="请输入内容" id="username"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label">所属组织</label>
				<div class="col-xs-7">
					<input type="hidden" id="department_id" name="department_id" value="${pd.department_id}" />
					<input name="department_name" type="text" readonly='readonly' class="form-control" value="${pd.department_name}" placeholder="请输入内容" id="department_name"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">真实姓名</label>
				<div class="col-xs-7">
					<input name="name" type="text" class="form-control" value="${userPageData.NAME }" placeholder="请输入内容" id="name"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">手机号</label>
				<div class="col-xs-7">
					<input name="phone" type="text" class="form-control" value="${userPageData.PHONE }" placeholder="请输入内容" id="phone"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label">状态</label>
				<div class="col-xs-7">
					
					<select id="status" name="status" class="form-control">
						<c:forEach items="${userStatusList }" var="ustatus" varStatus="">
							<option <c:if test="${userPageData.STATUS==ustatus.value }">selected</c:if> value="${ustatus.value }">${ustatus.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</form>

		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
		
		<script type="text/javascript" src="${basepath }static/js/myjs/user-add.js"></script>
		
