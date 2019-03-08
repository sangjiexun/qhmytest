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
		<title>宿舍配置</title>
		<link rel="stylesheet" href="${basepath}static/gxjf/css/share.css" />
	    <style>
	    	.bs-bars{
	    		margin-top:0 !important;
	    	}
	    	.bootstrap-dialog-title{
	    		font-size:16px !important;
	    		font-weight:bold;
	    	}
	    </style>
	    <style>
			.bg-danger{
				background: #d9534f;
			}
			.bg-success{
				background: #5cb85c;
			}
			.list-group{
				margin-bottom:0px;
			}
			.sg_louhao {
			    font-size: 16px;
			    margin-bottom: 10px;
			}
		</style>
		<script type="text/javascript">
			//左侧树选择的节点
			var targetNodeSelectedNode = null;
		</script>
		<input id ="isStudentDormSetting" value="true" style="display:none;">
		<input id ="SESSION_MENU_BUTTONS" value="${SESSION_MENU_BUTTONS }" style="display:none;">
		<input id ="defaultTopPkid" value="${pd.defaultTopPkid }" style="display:none;">
		<!-- 左侧树展示 -->
		<div class="row-fluid" style="float:left;width:280px;margin:0 15px 0 0;">
			<div class="btn-group" role="group" style="display:inline;">
				<button type="button" class="btn btn-default btn-sm" id="department_unfold"
					 style="border-radius:4px 0 0 0;height:45px;width:140px;"><span class="glyphicon glyphicon-chevron-down" aria-hidden="true" ></span> 展开</button>
				<button type="button" class="btn btn-default btn-sm" id="department_fold"
					 style="border-radius:0 4px 0 0;height:45px;width:140px;"><span class="glyphicon glyphicon-chevron-up" aria-hidden="true" ></span> 折叠</button>
			</div>
			 <!-- 左侧树展示 -->
			<div id="departmentTreeview" style="overflow:auto; width:279px;font-size:14px;"></div>
			<input name="departmentTreeJsonDataDiv" id="departmentTreeJsonDataDiv" type="hidden" value='${studentDormTreeJsonData }' />
			<!-- end 左侧树展示 -->
		</div>
		
		<!-- end 左侧树展示 -->


		<!-- 列表工具行 -->
		<div class="row-fluid kema_fluid">
			<div id="toolbar" class="form-inline">
			
					<!-- <div class="search" style="margin-top: 0px;"> 
						<input class="form-control kema_input" id="searchsr" type="text"
							placeholder="请输入组织名称查询">
					</div> -->
					<input id="treeNodeId" hidden="hidden">
					<!-- <button id="btn_search" type="button" class="btn btn-danger">
						<span class="fa fa-search" aria-hidden="true"></span> 查询
					</button> -->
					<span id="btn_add_span">
						<c:if test="${SESSION_MENU_BUTTONS.sspz_xz == '1' }">
							<button id="btn_add" type="button" class="btn btn-danger"
								data-toggle="modal" data-target="#mymodal">
								<span class="fa fa-plus" aria-hidden="true"></span>
								新增
							</button>
						</c:if>
					</span>
					
					
			</div>
			<!-- 列表数据 -->
			<fieldset>
				<div class="container-fluid" id="cardList" style="margin-top:10px;">
				
				</div>
				
			</fieldset>
			<!-- end 列表数据 -->
			
			
			
		</div>
		<!-- end 列表工具行 -->
		


		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
		<script type="text/javascript" src="${basepath }static/js/myjs/studentDormList.js"></script>
