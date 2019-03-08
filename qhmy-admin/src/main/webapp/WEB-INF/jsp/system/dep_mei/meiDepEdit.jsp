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
		<title>美院</title>
		
		<div class="form-horizontal" >
			<input type="hidden" id="PKID" name="PKID" value="${dep.PKID}" />
			<%-- 
			<div class="form-group">
				<label for="" class="col-xs-3 control-label">美院编号</label>
				<div class="col-xs-7">
					<input name="NAME" type="text" class="form-control" value="${dep.ORDER_NUMBER}" placeholder="请输入编号" id="ORDER_NUMBER" maxlength="255"/>
				</div>
			</div> --%>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">美院名称</label>
				<div class="col-xs-7">
									<input name="BEIZHU" type="hidden" class="form-control" value="${dep.NAME }" placeholder="请输入名称" id="CFNAME" maxlength="25"/>
				
					<input name="BEIZHU" type="text" class="form-control" value="${dep.NAME }" placeholder="请输入名称" id="NAME" maxlength="25"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label ">是否启用</label>
				<div class="col-xs-7">
					<input type="checkbox" class="al-toggle-button" 
					
					<c:if test="${dep.IS_QY=='Y' ||dep.IS_QY==null ||dep.IS_QY=='' }">checked="checked" </c:if>
					 id="IS_QY">
				</div>
			</div>
			
		</div>

		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
