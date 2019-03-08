<%@ page language="java" contentType="text/html; charset=GB2312"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basepath", basePath); 
%>

<!DOCTYPE html>
<html>
	<head>
		<title>缴费平台</title>
		<link href="${basepath}static/js/layer/skin/default/layer.css" rel="stylesheet">
		
		<link rel="stylesheet" href="${basepath}static/gxjf/css/Employee.css" />
		<link rel="stylesheet" href="${basepath}static/gxjf/css/framework-font.css" />
		<link href="${basepath}static/js/layer/skin/default/layer.css" rel="stylesheet">
		<link href="${basepath}static/css/bootstrap-table.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="${basepath}static/css/bootstrap-treeview.css">
		
		<link rel="stylesheet" href="${basepath}static/gxjf/bs/css/bootstrap.min.css" />
		<script type="text/javascript" src="${basepath }static/css/bs/js/jquery.min.js"  charset="UTF-8"></script>
		<script type="text/javascript" src="${basepath }static/css/bs/js/bootstrap.js"  charset="UTF-8"></script>
		
		<script src="${basepath}static/js/layer/layer.js"  charset="UTF-8"></script>
	    <script src="${basepath}static/js/bootstrap-dialog.min.js"  charset="UTF-8"></script>
		<script src="${basepath}static/js/bootstrap-table.min.js"  charset="UTF-8"></script>
	    <script src="${basepath}static/js/bootstrap-table-zh-CN.min.js"  charset="UTF-8"></script>
	    <script src="${basepath}static/js/bootstrap-treeview.js"  charset="UTF-8"></script>
	    <script src="${basepath}static/js/common/keman_common.js"  charset="UTF-8"></script>
	    <script src="${basepath}static/bootstrap_multiselect/bootstrap-multiselect.js"  charset="UTF-8"></script>
		<link href="${basepath}static/bootstrap_multiselect/bootstrap-multiselect.css" rel="stylesheet">
		<script	src="${basepath}static/My97DatePicker/WdatePicker.js"></script>
		
		<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->

	<style>
		.jf_navIcon{
			color:#444 !important;
			font-size:16px !important;
		}
		.jf_navIcon:focus, .jf_navIcon:hover{
			background:#edf0f5 !important;
		}
		.navbar-brand{
			padding:6px 20px !important;
			width:20%;
			line-height:36px !important;
		}
		.bootstrap-table{
			overflow: hidden;text-overflow: ellipsis;white-space: nowrap;
		}
	</style>
	</head>
	<body onload="closeLoading()">
	
	<div class="jf_ctn">
			<nav class="navbar jf_nav">
				<div class="navbar-brand" style="width:20%;">
					<a href="<%=basePath%>main/index">
						<img src="${basepath}static/gxjf/images/zhigong_sy/njci_logo.png" height="100%"/>
					</a>
				</div>
				<div class="collapse navbar-collapse">
				    <!-- 顶部菜单 -->
					<ul class="nav navbar-nav">
						<c:forEach items="${menuList}" var="menu">
					  		<li class="jf_hover topmenu">
	  				                <a href="javascript:void(0);" onclick="js_method('${menu.MENU_URL}','${zzname}','${menu.MENU_NAME}',this)">
										<span class="fa ${menu.MENU_ICON }"></span>
											${menu.MENU_NAME}
						  			</a>
						    </li>
	                    </c:forEach>
					</ul>
					<!-- end 顶部菜单 -->
					
					<ul class="nav navbar-nav navbar-right">
						<li class="jf_bell" title="修改密码" id="editpwd"><a class="jf_a" href="javascript:void(0);">
							<span class="fa fa-lock jf_navIcon">
								<span class="badge jf_xiaoxshu"></span>
							</span>
						</a></li>
						<li><a class="jf_navIcon" href="javascript:void(0);">${user.USERNAME}</a></li>
						<li><a class="jf_biga" href="logout" id="logout"><span class="fa fa-power-off jf_navIcon"></span></a></li>
					</ul>
				</div>
			</nav>
			<div id="nameli">
			</div>
		</div>
		<script type="text/javascript" src="${basepath }static/js/myjs/topmain.js"  charset="UTF-8"></script>
		<script src="${basepath}static/js/layDate-v5.0.9/laydate/laydate.js"  charset="UTF-8"></script>
		<script type="text/javascript">
		var _basepath = "${basepath}";
		</script>
	</body>
</html>
