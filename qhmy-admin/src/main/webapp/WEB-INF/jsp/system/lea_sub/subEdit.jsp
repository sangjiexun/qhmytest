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
		<title>学习科目</title>
		
		<div class="form-horizontal" >
			<input type="hidden" id="PKID" name="PKID" value="${sub.PKID}" />
			
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">科目名称</label>
				<div class="col-xs-7">
				<input value="${sub.NAME }" id="CFNAME" type="hidden"/>
					<input name="BEIZHU"  type="text" class="form-control" value="${sub.NAME }" placeholder="请输入名称" id="NAME" maxlength="20"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label ">是否启用</label>
				<div class="col-xs-7">
					<input type="checkbox" class="al-toggle-button" 
					
					<c:if test="${sub.IS_QY=='Y' ||sub.IS_QY==null ||sub.IS_QY=='' }">checked="checked" </c:if>
					 id="IS_QY">
					 
					 <input style="display:none;" type="checkbox"  class="al-toggle-button" 
					
					<c:if test="${sub.IS_QY=='Y' ||sub.IS_QY==null ||sub.IS_QY=='' }">checked="checked" </c:if>
					 id="IS_QYS">
				</div>
			</div>
			
		</div>

		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
