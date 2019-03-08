<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("basepath", basePath);
	
%>
	    <link href="${basepath}static/css/bootstrap-table.min.css" rel="stylesheet">
	     <link href="${basepath}static/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet">
	     
	     <script src="${basepath}static/bootstrap3-editable/js/bootstrap-editable.min.js"></script>
	     
		<script src="${basepath}static/js/bootstrap-table.min.js"></script>
	    <script src="${basepath}static/js/bootstrap-table-zh-CN.min.js"></script>
	    
	   <script src="${basepath}static/js/bootstrap-table-editable.js"></script>
	    <script type="text/javascript" src="${basepath}static/js/myjs/paytype_list.js"></script>
		<style type="text/css">
			html {overflow-x:hidden;}
			.jf_ZtBox{
				left:0 !important;
				width:300px;
			}
		</style>
		<script type="text/javascript">
              var _basepath = "${basepath}";
        </script>
        <input id ="SESSION_MENU_BUTTONS" value="${SESSION_MENU_BUTTONS }" style="display:none;">
        <input type="hidden" name="COLLEGES_NAME_EN" id="COLLEGES_NAME_EN" value="${sessionUser.COLLEGES_NAME_EN }" />
		<div class="form-inline">
			
			<label class="jf_label" for=""></label>		   		
			<input type="text" class="form-control jf_FltConditions" placeholder="缴费类型名称" id="search"/>
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_search"><span class="fa fa-search"></span> 查询</a>
			<c:if test="${SESSION_MENU_BUTTONS.type_xz == '1' }">
				<a href="javascript:void(0);" class="btn btn-danger" id="btn_add"><span class="fa fa-plus" ></span> 新增</a>
			</c:if>
		</div>	
			
	<fieldset>
		<table id="paytypetable" class="table table-striped table-hover table-border jf_table" style="text-align: center;">
		</table>
	</fieldset>
	<div class="clearfix"></div>

