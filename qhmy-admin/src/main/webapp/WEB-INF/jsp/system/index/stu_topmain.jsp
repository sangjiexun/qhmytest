<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basepath", basePath); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="UTF-8">
		<title>职工首页</title>
		<link rel="stylesheet" href="${basepath }static/css/bs/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${basepath }static/css/jf_custom.css" />
		<link rel="stylesheet" href="${basepath }static/css/framework-font.css" />
		<script type="text/javascript" src="${basepath }static/css/bs/js/jquery.min.js"></script>
		<script type="text/javascript" src="${basepath }static/css/bs/js/bootstrap.js"></script>
		<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->

	<style type="text/css">
	.commitopacity{position:absolute; width:100%; height:100px; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.8; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
	</style>
	<style>
		ul,li{
			list-style:none;
		}
	</style>
	</head>
	<body>
		<div class="jf_ctn container">
					<nav class="navbar jf_nav">
				<div class="navbar-header">
					<a href="${basepath}main/index" class="jf_navbra navbar-brand">
						<img src="${basepath}static/images/zhigong_sy/logo3.png" width="100%"/>
				</div>
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav navbar-right">
						<li class="jf_bell"><a class="jf_a" href="">
							<span class="fa fa-bell ">
								<span class="badge jf_xiaoxshu">6</span>
							</span>
						</a></li>
						<li><a class="jf_a" href="">${stuInfoPd.USERNAME}</a></li>
						<li><a class="jf_biga" href="${basepath}logout"><span class="fa fa-power-off"></span></a></li>
					</ul>
				</div>
			</nav>
			<div id="nameli">
			</div>
		</div>	
		<script type="text/javascript" src="${basepath }static/js/myjs/topmain.js"></script>
		<script type="text/javascript">

		var _basepath = "${basepath}";
		$(function(){
			$("#nameli").load(_basepath+"main/gostutopmain.json");
		})

		</script>
	</body>
</html>
