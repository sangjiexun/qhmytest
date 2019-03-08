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
		<!--Fa字体样式-->
		<link href="${basepath}static/dormitoryPla/css/framework-font.css" rel="stylesheet"/>
		<!-- ztree所需css以及js -->
		 <link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css">
		<link rel="stylesheet" href="${basepath}static/dormitoryPla/css/pl-share.css" />
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script>
	    <script type="text/javascript" src="${basepath}static/js/myjs/departmentFordormPlanQuery_ztree.js"></script>
	    <script type="text/javascript">
			var _basePath = '${basepath}';
		</script>
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
			.bg-hui{background-color:#ECEAE9;height:215px;}
			.list-group{
				margin-bottom:0px;
			}
			.sg_wsolid:hover {background:#b3e0fa;}
			.fangstatus:hover {background:#b3e0fa;}
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
			
			table{
			   width:100px;
			   table-layout:fixed;/* 只有定义了表格的布局算法为fixed，下面td的定义才能起作用。 */
			}
			
			td{
			   width:100%;
			   word-break:keep-all;/* 不换行 */
			   white-space:nowrap;/* 不换行 */
			   overflow:hidden;/* 内容超出宽度时隐藏超出部分的内容 */
			   text-overflow:ellipsis;/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用*/
			   -o-text-overflow:ellipsis;
			   -icab-text-overflow: ellipsis;
			   -khtml-text-overflow: ellipsis;
			   -moz-text-overflow: ellipsis;
			   -webkit-text-overflow: ellipsis;
			}
			.sg_wsolid{
				width:90%;
				margin:0 auto;
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
					<select name="BANXING" class="form-control jf_FltConditions" id="BANXING2">
							<option  value="">班型</option>
							<c:forEach var="banxing" items="${banxingList }">
								<option value="<c:out value="${banxing.DICTIONARIES_ID}"/>"><c:out value="${banxing.NAME}"/></option>
							</c:forEach>
					</select>
					<select name="WENHUAKEXUEXIAO" class="form-control jf_FltConditions" id="WENHUAKEXUEXIAO2">
							<option  value="">文化课学校</option>
							<c:forEach var="wenhuakexuexiao" items="${wenhuakexuexiaoList }">
								<option value="<c:out value="${wenhuakexuexiao.PKID}"/>"><c:out value="${wenhuakexuexiao.SCHOOLNAME}"/></option>
							</c:forEach>
					</select>
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
							<option value="0">女</option>
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
					<button id="btn_export" type="button" class="btn btn-danger"
						data-toggle="modal" data-target="#mymodal">
						<span class="fa fa-upload" aria-hidden="true"></span>
						导出
					</button>
					
			</div>
			<!-- 列表数据 -->
			<fieldset>
				<div style="padding-top:15px;">
					<ul class="nav nav-pills">
						<li class="pl-item active" id="cardTab"><a><span class="fa fa-picture-o"></span>&nbsp;图标显示</a></li>
						<li class="pl-item" id="cardTabTable"><a><span class="fa fa-list-ul"></span>&nbsp;列表显示</a></li>
					</ul>
					<div class="pa-row" id="cardList">
						
					</div>
					<div class="fixed-table-pagination" id="cardViewPage" style="display: none;">
						<div class="pull-left pagination-detail">
							<span class="pagination-info"><span id="pageNumberStr"></span><span id="pageNumber" style="display:none;">${pageNumber }</span><span id="pageNumberTotal" style="display:none;">${pageNumberTotal }</span></span>
							<span class="page-list">每页显示 <span class="btn-group dropup"><button type="button" class="btn btn- dropdown-toggle" data-toggle="dropdown"><span class="page-size">48</span> <span class="caret"></span></button><ul class="dropdown-menu" role="menu" id="dropdown-menu22"><li><a href="javascript:void(0)">1</a></li><li><a href="javascript:void(0)">5</a></li><li><a href="javascript:void(0)">10</a></li><li class="active"><a href="javascript:void(0)">15</a></li><li><a href="javascript:void(0)">20</a></li><li><a href="javascript:void(0)">30</a></li><li><a href="javascript:void(0)">48</a></li></ul></span> 条记录</span>
						</div>
						<div class="pull-right pagination">
							<ul class="pagination" id="pagenumberli">
							</ul>
						</div>
					</div>
					<%-- <div class="pl-fanYe" style="display: none;padding: 20px;">
						<a href="javascript:nextPage('-1');" class="btn btn-danger"> < </a>&nbsp;&nbsp;<span id="pageNumberStr"></span><span id="pageNumber" style="display:none;">${pageNumber }</span><span id="pageNumberTotal" style="display:none;">${pageNumberTotal }</span>&nbsp;&nbsp;<span id="totalData">${totalData }</span>&nbsp;&nbsp;<span class="page-list">每页显示 <span class="btn-group dropup"><button type="button" class="btn btn- dropdown-toggle" data-toggle="dropdown"><span class="page-size">48</span> <span class="caret"></span></button><ul class="dropdown-menu" role="menu" id="dropdown-menu22"><li><a href="javascript:void(0)">1</a></li><li><a href="javascript:void(0)">5</a></li><li><a href="javascript:void(0)">10</a></li><li class="active"><a href="javascript:void(0)">15</a></li><li><a href="javascript:void(0)">20</a></li><li><a href="javascript:void(0)">30</a></li><li><a href="javascript:void(0)">48</a></li></ul></span> 条记录</span>
						&nbsp;&nbsp;<a href="javascript:nextPage('+1');" class="btn btn-danger"> > </a>
						
					</div>
					<div class="pull-right pagination">
						<ul class="pagination">
						<li class="page-pre"><a href="javascript:void(0)">‹</a></li>
						<li class="page-number active"><a href="javascript:void(0)">1</a></li>
						<li class="page-number"><a href="javascript:void(0)">2</a></li>
						<li class="page-next"><a href="javascript:void(0)">›</a></li>
					</ul></div> --%>
					<div class="pa-row" style="display: none;padding: 20px;" >
						<table class="table table-bordered table-striped" id="cardListTable">
							
						</table>
					</div>
				</div>
				
			</fieldset>
			<!-- end 列表数据 -->
			
			
			
		</div>
		<!-- end 列表工具行 -->

		<script>
			$(function () { $("[data-toggle='tooltip']").tooltip(); });
		</script>
			<script  type="text/javascript">
			    $('.pl-item').click(function () {
						$(this).addClass('active');
						$('.pl-item').not($(this)).removeClass('active');
						
						idx = $(this).index('.pl-item');
						if(idx == 0){
							$(".pl-fanYe").css("display","block");
							$("#cardViewPage").css("display","block");
						}else{
							$(".pl-fanYe").css("display","none");
							$("#cardViewPage").css("display","none");
						}
						$('.pa-row').eq(idx).stop().show(100);
						$('.pa-row').not($('.pa-row').eq(idx)).hide(100);
					});
			</script>

		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
		<script type="text/javascript" src="${basepath }static/js/myjs/studentDormPlanList.js"></script>
