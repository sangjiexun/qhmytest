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
		<title>基础配置-升学标识</title>
		
		<form action="" class="form-horizontal" >
			<input type="hidden" id="PKID" name="PKID" value="${pd_signup.DICTIONARIES_ID}" />
			<input type="hidden" id="SIGNUP_BIANMA" name="SIGNUP_BIANMA" value="${pd_signup.BIANMA}" />
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">升学标识名称</label>
				<div class="col-xs-7">
					<input name="SIGNUP_NAME" type="text" class="form-control" value="${pd_signup.NAME}" placeholder="请输入升学标识名称" id="SIGNUP_NAME" maxlength="255"/>
				</div>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label ">是否启用</label>
				<div class="col-xs-7">
					<input type="checkbox" class="al-toggle-button" <c:if test="${pd_signup.IS_USED=='1'||empty pd_signup.IS_USED}">checked="checked" </c:if> id="IS_USED">
				</div>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label">备注</label>
				<div class="col-xs-7">
					<input name="BEIZHU" maxlength="255" type="text" class="form-control" value="${pd_signup.BZ }" placeholder="请输入备注" id="BEIZHU"/>
				</div>
			</div>
		</form>

		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
