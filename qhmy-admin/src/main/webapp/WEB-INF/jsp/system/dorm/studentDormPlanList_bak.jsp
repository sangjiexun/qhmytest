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
		<title>宿舍计划</title>
		<link rel="stylesheet" href="${basepath}static/gxjf/css/share.css" />
		<!-- ztree所需css以及js -->
		 <link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css">
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script>
	    <script type="text/javascript" src="${basepath}static/js/myjs/departmentFordormPlanQuery_ztree.js"></script>
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
			.sg_wsolid:hover {background:#b3e0fa;}
			.sg_louhao {
			    font-size: 16px;
			    margin-bottom: 10px;
			}
			
			element.style {
			    position: absolute;
			    z-index: 1;
			    left: 73px;
			    top: 34px;
			    display: none;
			}
		</style>
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
			<input name="departmentTreeview" id="departmentTreeJsonDataDiv" type="hidden" value='${studentDormTreeJsonData }' />
			<%-- <input name="departmentTreeJsonDataDiv" id="departmentTreeJsonDataDiv" type="hidden" value='${departmentTreeJsonData }' /> --%>
			<!-- end 左侧树展示 -->
		</div>
		
		<!-- end 左侧树展示 -->


		<!-- 列表工具行 -->
		<div class="row-fluid kema_fluid">
			<div id="toolbar" class="form-inline">
					<input id="treeNodeId" hidden="hidden" value="${pd.treeNodeId }">
					<select name="RUXUENIANFEN" class="form-control jf_FltConditions" style="width:28%;" id="RUXUENIANFEN">
							<option  value="">入学年份</option>
							<c:forEach var="ruxuenianfen" items="${ruxuenianfenList }">
								<option value="<c:out value="${ruxuenianfen.BIANMA}"/>"><c:out value="${ruxuenianfen.NAME}"/></option>
							</c:forEach>
					</select>
					<!-- z-tree结构 -->
					<div class="form-group">
		                 <div class="zTreeDemoBackground left">
							<ul class="list" style="margin-bottom:0;">
								<li class="jf_ZtLaber title"><input class="form-control" id="citySel2" type="text" readonly value="" onclick="showMenu2();" placeholder="院系专业" style="width:183px;"/>
									<div id="menuContent2" class="jf_ZtBox menuContent" style="margin-top:0; width:300px; max-height: 300px;overflow:auto;">
										<ul id="treeDemo2" class="jf_ZtList ztree"></ul>
									</div>
								</li>
							</ul>
						 </div>
						 <div style="display:none;">
					        <!--用来存储 树节点的id -->
						    <input type="text" name="tree" id="orgtree2" class="form-control"/>
					     </div>
					</div>
					<select name="T_STUDENT_DORM_TYPE_PKID" class="form-control jf_FltConditions" style="width:28%;" id="T_STUDENT_DORM_TYPE_PKID">
							<option  value="">宿舍类型</option>
							<c:forEach var="dormType" items="${studentDormTypeList }">
								<option value="<c:out value="${dormType.PKID}"/>"><c:out value="${dormType.DT_NAME}"/></option>
							</c:forEach>
					</select>
					<select name="STATUS" class="form-control jf_FltConditions" style="width:28%;" id="STATUS">
							<option  value="">使用状态</option>
							<option value="0">未占用</option>
							<option value="1">已占用</option>
					</select>
					<select name="SD_SEX" class="form-control jf_FltConditions" style="width:28%;" id="SD_SEX">
							<option value="">性别</option>
							<option value="1">男</option>
							<option value="2">女</option>
					</select>
					<button id="btn_search" type="button" class="btn btn-danger">
						<span class="fa fa-search" aria-hidden="true"></span> 查询
					</button>
					
					<c:if test="${SESSION_MENU_BUTTONS.ssjh_fp == '1' }">
						<button id="btn_allot" type="button" class="btn btn-danger"
							data-toggle="modal" data-target="#mymodal">
							<span class="fa fa-sitemap" aria-hidden="true"></span>
							分配
						</button>
					</c:if>
					<c:if test="${SESSION_MENU_BUTTONS.ssjh_hs == '1' }">
						<button id="btn_recovery" type="button" class="btn btn-danger"
							data-toggle="modal" data-target="#mymodal">
							<span class="fa fa-retweet" aria-hidden="true"></span>
							回收
						</button>
					</c:if>
					
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
		<script type="text/javascript" src="${basepath }static/js/myjs/studentDormPlanList.js"></script>
