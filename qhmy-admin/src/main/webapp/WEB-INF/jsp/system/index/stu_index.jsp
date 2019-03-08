
﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
		<meta charset="UTF-8">
		<title>学生首页</title>
		<link rel="stylesheet" href="${basepath}static/css/bs/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="${basepath}static/css/jf_custom.css" />
		<link rel="stylesheet" href="${basepath}static/css/framework-font.css" />
		<script type="text/javascript" src="${basepath}static/css/bs/js/jquery.min.js"></script>
		<script type="text/javascript" src="${basepath}static/css/bs/js/bootstrap.js"></script>
	</head>
	<body>
		<div class="jf_ctn container">
		<div class="jf_ctn jf_conbg container">
			<div class="jf_content">
				<div class="jf_row">
					<div class="jf_erfen pull-left">
						<div class="jf_xxborder">
							<div class="jf_zhaop pull-left" style="width: 65px;">
								<img src="${basepath}${stuInfoPd.TOUXIANG }" width="100%"/>
							</div>
							<div class="pull-left jf_jichuxx" style="border-right: 1px dashed #ccc;margin-left:10px;">
								<div class="jf_xinxi">
									<span>姓名：<a href="">${stuInfoPd.NAME}</a></span>
								</div>
								<div class="jf_xinxi">
									<span>性别：<a href="">${stuInfoPd.XINGBIE}</a></span>
								</div>
								<div class="jf_xinxi">
									<span>身份证号：<a href="">${stuInfoPd.SHENFENZHENGHAO}</a></span>
								</div>
								<div class="jf_xinxi">
									<span>手机：<a href="">${stuInfoPd.SHOUJI} </a></span>
								</div>
							</div>
							<div class="pull-left jf_jichuxx" style="margin-left:25px;">
								<div class="jf_xinxi">
									<span>学号：<a href="">${stuInfoPd.XUEHAO}</a></span>
								</div>
								<div class="jf_xinxi">
									<span>专业：<a href=""></a></span>
								</div>
								<div class="jf_xinxi">
									<span>在学状态：<a href="">${stuInfoPd.ZHUANGTAI=='1'?'在读':'不在读'}</a></span>
								</div>
								<div class="jf_xinxi">
									<span>班级：<a href=""></a></span>
								</div>
							</div>
							<div class="jf_xiangq"><a href="">详情>></a></div>
							<div class="clearfix"></div>
						</div>
					</div>
					<div class="jf_erfen pull-left">
						<div class="jf_xxborder">
							<div class="pull-left jf_xiangmu">缴费项目</div>
							<ul class="pull-left jf_jichuxx">
								<li class="jf_tixing pull-left">
									<h4>5</h4>
									<div>未缴费项</div>
								</li>
								<li class="jf_tixing pull-left">
									<h4>3</h4>
									<div>欠费项</div>
								</li>
								<li class="jf_tixing pull-left">
									<h4>8</h4>
									<div>核验中项</div>
								</li>
								<li class="jf_tixing pull-left">
									<h4>5</h4>
									<div>已完成项</div>
								</li>
							</ul>
							<div class="clearfix"></div>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="panel panel-info">
					<div class="panel-heading">
						<div class="panel-title jf_pantit">
							<span class="fa fa-flag"></span> 缴费项目
							<a href="" class="pull-right">查看详情>></a>
						</div>
					</div>
					<div class="panel-body jf_panel">
						<span><img src="${basepath}static/images/xuesheng_sy/tu.png" alt="" width="100%;"/></span>
					</div>
				</div>
			</div>
			</div>
		</div>	
	</body>
</html>
