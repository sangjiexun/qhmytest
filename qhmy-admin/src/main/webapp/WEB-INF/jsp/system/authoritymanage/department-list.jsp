<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("basepath", basePath);
%>
	    <style>
	    	.bs-bars{
	    		margin-top:0 !important;
	    	}
	    	.bootstrap-dialog-title{
	    		font-size:16px !important;
	    		font-weight:bold;
	    	}
	    </style>
		
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
				<table id="departmentTable" class="table table-striped table-hover table-border jf_table" style="text-align: center !important;">
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
						placeholder="请输入组织名称查询">
				<!-- </div> -->
				<button id="btn_search" type="button" class="btn btn-danger">
					<span class="fa fa-search" aria-hidden="true"></span> 查询
				</button>
				
				
				<c:if test="${SESSION_MENU_BUTTONS.add == '1' }">
					<button id="btn_add" type="button" class="btn btn-danger"
						data-toggle="modal" data-target="#mymodal">
						<span class="fa fa-plus" aria-hidden="true"></span>
						新增
					</button>
				</c:if>
				
			</div>
		</div>
		<!-- end 列表工具行 -->
		

		<script type="text/javascript">
			var _basepath = "${basepath}";
			//权限
			var jurisdiction_add = "${SESSION_MENU_BUTTONS.add}";
			var jurisdiction_edit = "${SESSION_MENU_BUTTONS.edit}";
			var jurisdiction_del = "${SESSION_MENU_BUTTONS.del}";
		</script>
		
		<script type="text/javascript" src="${basepath }static/js/myjs/department-list.js"></script>
