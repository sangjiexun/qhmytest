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
		<title>权限管理-用户</title>
		
	    <style>
	    	.bs-bars{
	    		margin-top:0 !important;
	    	}
	    	.bootstrap-dialog-title{
	    		font-size:16px !important;
	    		font-weight:bold;
	    	}
	    	.bootstrap-table{
	    		overflow:auto !important;
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
				<table id="userTable" class="table table-striped table-hover table-border jf_table" style="text-align: center !important;">
				</table>
			</fieldset>
			<!-- end 列表数据 -->
			
			
			<div id="toolbar_user" class="form-inline">
				<input class="form-control kema_input" id="searchsr" type="text"
					placeholder="用户名.真实名.手机号查询">
				<button id="user_btn_search" type="button" class="btn btn-danger">
					<span class="fa fa-search" aria-hidden="true"></span> 查询
				</button>
				
				<c:if test="${SESSION_MENU_BUTTONS.add == '1' }">
					<button id="user_btn_add" type="button" class="btn btn-danger"
						data-toggle="modal" data-target="#mymodal">
						<span class="fa fa-plus" aria-hidden="true"></span>
						新增
					</button>
				</c:if>
				
				<c:if test="${SESSION_MENU_BUTTONS.edit == '1' }">
					<button id="user_btn_edit" type="button"
						class="btn btn-danger" data-toggle="modal"
						data-target="#editModal">
						<span class="fa fa-edit" aria-hidden="true"></span>
						修改
					</button>
				</c:if>
				<c:if test="${SESSION_MENU_BUTTONS.del == '1' }">
					<button id="user_btn_del" type="button"
						class="btn btn-danger" data-toggle="modal"
						data-target="#editModal">
						<span class="fa fa-edit" aria-hidden="true"></span>
						删除
					</button>
				</c:if>
				
				<c:if test="${SESSION_MENU_BUTTONS.start == '1' }">
					<button id="user_btn_start" type="button" class="btn btn-danger" data-toggle="modal" data-target="#editModal" >
						<span class="fa fa-check-circle" aria-hidden="true"></span>
						启用
					</button>
				</c:if>
				
				<c:if test="${SESSION_MENU_BUTTONS.stop == '1' }">
					<button id="user_btn_stop" type="button" class="btn btn-danger" data-toggle="modal" data-target="#editModal" >
						<span class="fa fa-minus-circle" aria-hidden="true"></span>
						停用
					</button>
				</c:if>
				
				<c:if test="${SESSION_MENU_BUTTONS.role == '1' }">
					<button id="user_btn_role" type="button"
						class="btn btn-danger" data-toggle="modal"
						data-target="#editModal">
						<span class="fa fa-sitemap" aria-hidden="true"></span>
						分配角色
					</button>
				</c:if>
				
				<c:if test="${SESSION_MENU_BUTTONS.restpwd == '1' }">
					<button id="user_btn_password" type="button"
						class="btn btn-danger" data-toggle="modal"
						data-target="#editModal">
						<span class="fa fa-unlock-alt" aria-hidden="true"></span>
						密码重置
					</button>
				</c:if>
			</div>
		</div>
		<!-- end 列表工具行 -->
		


		<script type="text/javascript">
			var _basepath = "${basepath}";
			var jurisdiction_add = "${SESSION_MENU_BUTTONS.add}";
			var jurisdiction_edit = "${SESSION_MENU_BUTTONS.edit}";
			var jurisdiction_start = "${SESSION_MENU_BUTTONS.start}";
			var jurisdiction_stop = "${SESSION_MENU_BUTTONS.stop}";
			var jurisdiction_role = "${SESSION_MENU_BUTTONS.role}";
			var jurisdiction_restpwd = "${SESSION_MENU_BUTTONS.restpwd}";
		</script>
		
		<script type="text/javascript" src="${basepath }static/js/myjs/user-list.js"></script>
