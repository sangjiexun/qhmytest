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
		<title>合作学校新增</title>
	    <style>
	    	.list .jf_ZtBox{
	    		left:0 !important;
	    		background:#fff;
	    	} 
	    </style>
		<form action="" class="form-horizontal" >
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">文化课学校</label>
				<div class="col-xs-7">
					<input name="SCHOOLNAME"  value="${pdResult.SCHOOLNAME }" maxlength="255" type="text" class="form-control"  placeholder="请输入文化课学校" id="SCHOOLNAME"/>
					<input  value="${pdResult.PKID }" type="hidden" id ="SchoolPkid"/>
					<input  value="${pdResult.SCHOOLNAME }" type="hidden" id ="SchoolNameHidden"/>
				</div>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label">定金</label>
				<div class="col-xs-7">
					<input name="DINGJIN" value="${pdResult.DINGJIN }" maxlength="50" type="text" class="form-control" placeholder="请输入定金" id="DINGJIN"/>
				</div>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label ">是否合作学校</label>
				<div class="col-xs-7">
					<input type="checkbox" class="al-toggle-button" <c:if test="${pdResult.ISHEZUO!='N'}">checked="checked" </c:if> id="ISHEZUO">
				</div>
			</div>
			
		</form>

		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
