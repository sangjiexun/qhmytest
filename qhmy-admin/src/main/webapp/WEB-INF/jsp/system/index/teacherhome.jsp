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

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>职工首页</title>
		<link rel="stylesheet" href="${basepath}static/gxjf/bs/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${basepath}static/gxjf/css/Employee.css" />
		<link rel="stylesheet" href="${basepath}static/gxjf/css/framework-font.css" />
        <script type="text/javascript" src="${basepath}static/js/jquery.min.js"></script>
		<script type="text/javascript" src="${basepath}static/js/bootstrap.js"></script>
		<!-- 引入echarts -->
        <script src="${basepath}static/js/myjs/echarts.min.js"></script>
         <script src="${basepath}static/js/myjs/teacherhome.js"></script>
	</head>
	<body>
		
		<div class="jf_ctn jf_conbg">
			<div class="jf_content">
				<div class="jf_row">
					<div class="jf_sanfen pull-left">
						<div class="jf_mdborder">
							<div class="jf_jiaofeirs">
								<div class="jf_shuzi">今日缴费人数<span> ${pd.totalCounts} </span>人</div>
							</div>
							<span><img src="${basepath}static/gxjf/images/<=basePath>main/index/jiaofei1.png" alt="" width="100%;"/></span>
						</div>
					</div>
					<div class="jf_rightbox pull-left">
						<div class="jf_mdborder" style="height: 334px;">
						<div class="jf_jiaofeirs">
							<div class="jf_shuzi">今日缴费总额<span> 86000 </span>人</div>
						</div>
						<span><img src="${basepath}static/gxjf/images/<=basePath>main/index/jiaofei2.png" alt="" width="100%;"/></span>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="panel panel-info">
					<div class="panel-heading">
						<div class="panel-title jf_pantit">
							<span class="fa fa-flag"></span> 2017-2018年学费
							<a href="" class="pull-right">查看详情>></a>
						</div>
					</div>
					<div class="panel-body jf_panel">
						<div class="pull-left">
							<ul class="pull-left">
								<li class="jf_li">
									<div class="jf_shuzis"><span>2350</span>人</div>
									<div>应收总人数</div>
								</li>
								<li class="jf_li">
									<div class="jf_shuzis"><span>1350</span>人</div>
									<div>应收总金额</div>
								</li>
								<li class="jf_li">
									<div class="jf_shuzis"><span>3250</span>人</div>
									<div>现金金额</div>
								</li>
							</ul>
							<ul class="pull-left">
								<li class="jf_li">
									<div class="jf_shuzis"><span>3050</span>人</div>
									<div>实收总人数</div>
								</li>
								<li class="jf_li">
									<div class="jf_shuzis"><span>5350</span>人</div>
									<div>实收总金额</div>
								</li>
								<li class="jf_li">
									<div class="jf_shuzis"><span>6350</span>人</div>
									<div>网上金额</div>
								</li>
							</ul>
						</div>
						<span class="pull-right"><img src="${basepath}static/gxjf/images/<=basePath>main/index/tubiao.png" alt="" width="100%;"/></span>
					</div>
				</div>
				<div class="panel panel-info">
					<div class="panel-heading">
						<div class="panel-title jf_pantit">
							<span class="fa fa-flag"></span> 2017-2018年学费
							<a href="" class="pull-right">查看详情>></a>
						</div>
					</div>
					<div class="panel-body jf_panel">
						<div class="pull-left">
							<ul class="pull-left">
								<li class="jf_li">
									<div class="jf_shuzis"><span>2350</span>人</div>
									<div>应收总人数</div>
								</li>
								<li class="jf_li">
									<div class="jf_shuzis"><span>1350</span>人</div>
									<div>应收总金额</div>
								</li>
								<li class="jf_li">
									<div class="jf_shuzis"><span>3250</span>人</div>
									<div>现金金额</div>
								</li>
							</ul>
							<ul class="pull-left">
								<li class="jf_li">
									<div class="jf_shuzis"><span>3050</span>人</div>
									<div>实收总人数</div>
								</li>
								<li class="jf_li">
									<div class="jf_shuzis"><span>5350</span>人</div>
									<div>实收总金额</div>
								</li>
								<li class="jf_li">
									<div class="jf_shuzis"><span>6350</span>人</div>
									<div>网上金额</div>
								</li>
							</ul>
						</div>
						<span class="pull-right"><img src="${basepath}static/gxjf/images/<=basePath>main/index/tubiao.png" alt="" width="100%;"/></span>
					</div>
				</div>
			</div>
		</div>	
	</body>
</html>

