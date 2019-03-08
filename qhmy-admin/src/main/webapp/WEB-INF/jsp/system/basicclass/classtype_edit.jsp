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
		<link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css">
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script>
	    <style>
	    	.list .jf_ZtBox{
	    		left:0 !important;
	    		background:#fff;
	    	} 
	    </style>
		<div class="form-horizontal" >
			<input type="hidden" id="PKID" name="PKID" value="${pd_class.PKID}" />
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">班型名称</label>
				<div class="col-xs-7">
					<input name="NAME" type="text" class="form-control" value="${pd_class.NAME}" placeholder="请输入班型名称" id="NAME" maxlength="255"/>
				</div>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">学号代码</label>
				<div class="col-xs-7">
					<input name="BIANMA" type="text" class="form-control" value="${pd_class.BIANMA }" placeholder="请输入学号代码" id="BIANMA" maxlength="4" onkeyup="this.value=this.value.replace(/[^a-zA-Z]/g,'')"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label ">是否预交</label>
				<div class="col-xs-7">
					<input type="checkbox" class="al-toggle-button" 
					<c:if test="${pd_class.SFQY=='1' ||pd_class.SFQY==null ||pd_class.SFQY=='' }">checked="checked" </c:if>
					 id="SFQY">
				</div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label ">是否启用</label>
				<div class="col-xs-7">
					<input type="checkbox" class="al-toggle-button" 
					
					<c:if test="${pd_class.IS_USED=='Y' ||pd_class.IS_USED==null ||pd_class.IS_USED=='' }">checked="checked" </c:if>
					 id="IS_USED">
				</div>
			</div>
			
		</div>

		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
