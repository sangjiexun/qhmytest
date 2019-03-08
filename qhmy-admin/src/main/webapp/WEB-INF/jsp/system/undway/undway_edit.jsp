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
		<title></title>
		
		<form action="" class="form-horizontal" >
			<input type="hidden" id="PKID" name="PKID" value="${pd_undway.DICTIONARIES_ID}" />
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">了解途径名称</label>
				<div class="col-xs-7">
					<input name="UNDWAY_NAME" type="text" class="form-control" value="${pd_undway.NAME}" placeholder="请输入了解途径名称" id="UNDWAY_NAME" maxlength="255"/>
				</div>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label ">是否启用</label>
				<div class="col-xs-7">
					<input type="checkbox" class="al-toggle-button" <c:if test="${pd_undway.IS_USED!='N'}">checked="checked" </c:if> id="IS_USED">
				</div>
			</div>
		</form>

		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
