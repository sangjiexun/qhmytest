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
		<title>基础配置-介绍人</title>
		
		<form action="" class="form-horizontal" >
			<input type="hidden" id="PKID" name="PKID" value="${pd_cengci.PKID}" />
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">学生类型编号</label>
				<div class="col-xs-7">
					<input id="CENGCI_BIANMA" name="CENGCI_BIANMA" readonly="readonly" maxlength="50" type="text" class="form-control" value="${pd_cengci.CENGCI_BIANMA}"  />
				</div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">学生类型名称</label>
				<div class="col-xs-7">
					<input name="CENGCI_NAME" type="text" class="form-control" value="${pd_cengci.CENGCI_NAME}" placeholder="请输入学生类型名称" id="CENGCI_NAME" maxlength="255"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">学年制</label>
				<div class="col-xs-7">
					<input name="XUENIANZHI" type="text" class="form-control" value="${pd_cengci.XUENIANZHI}" placeholder="请输入学年制" id="XUENIANZHI" maxlength="50"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label ">是否启用</label>
				<div class="col-xs-7">
					<input type="checkbox" class="al-toggle-button" <c:if test="${pd_cengci.IS_USE=='1'}">checked="checked" </c:if> id="IS_USE">
				</div>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label">学生类型代码</label>
				<div class="col-xs-7">
					<input name="CCDAIMA" maxlength="2" type="text" class="form-control" value="${pd_cengci.CCDAIMA }" placeholder="请输入备注" id="CCDAIMA"/>
				</div>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label">备注</label>
				<div class="col-xs-7">
					<input name="BEIZHU" maxlength="255" type="text" class="form-control" value="${pd_cengci.BEIZHU }" placeholder="请输入备注" id="BEIZHU"/>
				</div>
			</div>
		</form>

		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
