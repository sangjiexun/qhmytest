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
		<title>权限管理-院校专业</title>
		
		 <link href="${basepath}static/css/bootstrap-table.min.css" rel="stylesheet">
		 <link href="${basepath}static/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet">
	     <script src="${basepath}static/bootstrap3-editable/js/bootstrap-editable.min.js"></script>
		<script src="${basepath}static/js/bootstrap-table.min.js"></script>
	    <script src="${basepath}static/js/bootstrap-table-zh-CN.min.js"></script>
	    
	    <style>
	    	.bs-bars{
	    		margin-top:0 !important;
	    	}
	    	.bootstrap-dialog-title{
	    		font-size:16px !important;
	    		font-weight:bold;
	    	}
	    </style>
	   <input id ="SESSION_MENU_BUTTONS" value="${SESSION_MENU_BUTTONS }" style="display:none;">
	   <input id ="COLLEGES_NAME_EN" value="${COLLEGES_NAME_EN }" style="display:none;">
		<!-- 左侧树展示 -->
		<div class="row-fluid" style="float:left;width:220px;margin:0 15px 0 0;">
			<div class="btn-group" role="group" style="display:inline;">
				<button type="button" class="btn btn-default btn-sm" id="department_unfold"
					 style="border-radius:4px 0 0 0;height:45px;width:110px;"><span class="glyphicon glyphicon-chevron-down" aria-hidden="true" ></span> 展开</button>
				<button type="button" class="btn btn-default btn-sm" id="department_fold"
					 style="border-radius:0 4px 0 0;height:45px;width:110px;"><span class="glyphicon glyphicon-chevron-up" aria-hidden="true" ></span> 折叠</button>
			</div>
			 <!-- 左侧树展示 -->
			<div id="departmentTreeview" style="overflow:auto; width:219px;font-size:14px;"></div>
			<input name="departmentTreeJsonDataDiv" id="departmentTreeJsonDataDiv" type="hidden" value='${departmentTreeJsonData }' />
			<!-- end 左侧树展示 -->
		</div>
		
		<!-- end 左侧树展示 -->


		<!-- 列表工具行 -->
		<div class="row-fluid kema_fluid">
			<!-- 列表数据 -->
			<fieldset>
				<table id="departmentTable" class="table table-striped table-hover table-border jf_table text-nowrap" style="text-align: center !important;">
				</table>
			</fieldset>
			<!-- end 列表数据 -->
			
			
			<div id="toolbar" class="form-inline">
				<!-- 
				<button id="btn_delete" type="button"
					class="btn btn-default kema_btnsm">
					<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					删除
				</button>
				
				
				<button id="btn_qiy" type="button" class="btn btn-default kema_btnsm">
					<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>
					启用
				</button>
				
				<button id="btn_ty" type="button" class="btn btn-default kema_btnsm">
					<span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span>
					停用
				</button>
				 -->
		
				<!-- <div class="search" style="margin-top: 0px;"> -->
					<input class="form-control kema_input" id="searchsr" type="text"
						placeholder="请输入院校专业名称查询">
				<!-- </div> -->
				<input id="treeNodeId" hidden="hidden">
				<button id="btn_search" type="button" class="btn btn-danger">
					<span class="fa fa-search" aria-hidden="true"></span> 查询
				</button>
				
				<c:if test="${SESSION_MENU_BUTTONS.yxsz_xz == '1' }">
					<button id="btn_add" type="button" class="btn btn-danger"
						data-toggle="modal" data-target="#mymodal">
						<span class="fa fa-plus" aria-hidden="true"></span>
						新增
					</button>
				</c:if>
				<c:if test="${SESSION_MENU_BUTTONS.yxzy_import == '1' }">
				<a href="javascript:void(0);" class="btn btn-danger" id="btn_import_dept"><span class="fa fa-sign-in"></span> 导入</a>
				</c:if>
			</div>
		</div>
		<!-- end 列表工具行 -->
		


		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
		<script src="${basepath}static/js/bootstrap-table-editable.js"></script>
		<script type="text/javascript" src="${basepath }static/js/myjs/student-department-list.js"></script>
