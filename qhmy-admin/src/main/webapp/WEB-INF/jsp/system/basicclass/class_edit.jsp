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
		<form action="" class="form-horizontal" >
			<input type="hidden" id="PKID" name="PKID" value="${pd_class.PKID}" />
			<input type="hidden" id="SYS_DICTIONARIES_PKID_HIDDEN" name="PKID" value="${pd_class.SYS_DICTIONARIES_PKID}" />
			<input type="hidden" id="BANXING_PKID_HIDDEN" name="PKID" value="${pd_class.BANXING_PKID}" />
			<input type="hidden" id="CLASS_NAME_HIDDEN" name="PKID" value="${pd_class.CLASS_NAME}" />
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">入学年份</label>
				<div class="col-xs-7">
					<select name="" <c:if test="${pd_class.PKID != null && pd_class.PKID != ''}"> disabled="disabled" </c:if>   class="form-control jf_FltConditions" id="grade_add">
						<c:forEach items="${gradeList}" var="grade">
								<option <c:if test="${pd_class.SYS_DICTIONARIES_PKID == grade.DICTIONARIES_ID}"> selected="selected" </c:if> value="${grade.DICTIONARIES_ID}">${grade.NAME }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">班型</label>
				<div class="col-xs-7">
					<select name="" <c:if test="${pd_class.PKID != null && pd_class.PKID != ''}"> disabled="disabled" </c:if>   class="form-control jf_FltConditions" id="banxing_add">
						<c:forEach items="${banXingList}" var="banxing">
								<option <c:if test="${pd_class.BANXING_PKID == banxing.DICTIONARIES_ID}"> selected="selected" </c:if> value="${banxing.DICTIONARIES_ID}">${banxing.NAME }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">班级容量</label>
				<div class="col-xs-7">
					<input name="BANRONGLIANG" maxlength="255" type="text" class="form-control" value="${pd_class.BANRONGLIANG }" placeholder="请输入班级容量" id="BANRONGLIANG"/>
				</div>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">班级名称</label>
				<div class="col-xs-7">
					<input name="CLASS_NAME" maxlength="255" type="text" class="form-control" value="${pd_class.CLASS_NAME }" placeholder="请输入班级名称" id="CLASS_NAME"/>
				</div>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">是否启用</label>
				<div class="col-xs-7">
					<input type="checkbox" class="al-toggle-button" <c:if test="${pd_class.IS_USE=='1' || pd_class.IS_USE== null}">checked="checked"</c:if> id="IS_USE">
				</div>
			</div>
			
		</form>

		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
