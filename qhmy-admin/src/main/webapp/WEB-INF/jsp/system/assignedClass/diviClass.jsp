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
		<title>班级管理</title>
	    <style>
	    	.list .jf_ZtBox{
	    		left:0 !important;
	    		background:#fff;
	    	} 
	    </style>
		<form action="" class="form-horizontal" >
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">班级名称</label>
				<div class="col-xs-7">
					<select name="" class="form-control jf_FltConditions" id="banji_fen">
						<option value="">请选择班级</option>
						<c:forEach items="${list_class}" var="var">
								<option value="${var.PKID}">${var.NAME }</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</form>

		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
