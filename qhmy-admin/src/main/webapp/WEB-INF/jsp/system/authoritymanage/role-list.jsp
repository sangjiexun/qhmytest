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
	    
	    <style>
		    .bs-bars {
		    	margin-top:-1px !important;
		    }
	    </style>
		<!-- 列表工具行 -->
		<div class="row-fluid kema_fluid">
			<!-- 列表数据 -->
			<fieldset>
				<table id="roleTable" class="table table-striped table-hover table-border jf_table"
					 style="text-align: center !important;">
				</table>
			</fieldset>
			<!-- end 列表数据 -->
			
			<input type="hidden" name="showFlag" id="showFlag" value="${pd.showFlag }" >
			
			<div id="toolbar" class="form-inline">
				<input class="form-control kema_input" id="searchsr_role" type="text"
						placeholder="请输入角色名称查询">
				<button id="btn_search" type="button" class="btn btn-danger">
					<span class="fa fa-search" aria-hidden="true"></span> 查询
				</button>
				
				<c:if test="${pd.showFlag == null || pd.showFlag==false }">
					<c:if test="${SESSION_MENU_BUTTONS.add == '1' }">
						<button id="btn_add" type="button" class="btn btn-danger"
							data-toggle="modal" data-target="#mymodal">
							<span class="fa fa-plus" aria-hidden="true"></span>
							新增
						</button>
					</c:if>
					
					<c:if test="${SESSION_MENU_BUTTONS.edit == '1' }">
						<button id="btn_edit" type="button"
							class="btn btn-danger" data-toggle="modal"
							data-target="#editModal">
							<span class="fa fa-edit" aria-hidden="true"></span>
							修改
						</button>
					</c:if>
					
					<c:if test="${SESSION_MENU_BUTTONS.del == '1' }">
						<button id="btn_delete" type="button" class="btn btn-danger" data-toggle="modal" data-target="#editModal" >
							<span class="fa fa-remove" aria-hidden="true"></span>
							删除
						</button>
					</c:if>
					
					<c:if test="${SESSION_MENU_BUTTONS.jurisdiction == '1' }">
						<button id="btn_jurisdiction" type="button" class="btn btn-danger" data-toggle="modal" data-target="#editModal" >
							<span class="fa fa-key" aria-hidden="true"></span>
							分配权限
						</button>
					</c:if>
				</c:if>
				
			</div>
		</div>
		<!-- end 列表工具行 -->
		
		<script type="text/javascript">
			var _basepath = "${basepath}";
			var jurisdiction_add = "${SESSION_MENU_BUTTONS.add}";
			var jurisdiction_edit = "${SESSION_MENU_BUTTONS.edit}";
			var jurisdiction_del = "${SESSION_MENU_BUTTONS.del}";
			var jurisdiction_jurisdiction = "${SESSION_MENU_BUTTONS.jurisdiction}";
		</script>
		
		<script type="text/javascript" src="${basepath }static/js/myjs/role-list.js"></script>
