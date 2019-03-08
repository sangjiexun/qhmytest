<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="${basepath}static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="${basepath}static/ace/css/datepicker.css" />
	<!-- 新增样式 -->
	<link href="${basepath}static/ace/mycss/keman_yu.css" rel="stylesheet">
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
				
					<form action="control/${msg }.do" name="Form1" id="Form1" method="post">
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
						    <tr>
								<td style="width:25%;text-align:center;padding-top: 13px;">原密码:</td>
								<td><input type="text"  name="EBIANMA" id="oldpwd" value="" maxlength="255" placeholder="请输入原密码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:25%;text-align: center;padding-top: 13px;">新密码:</td>
								<td><input type="text" name="GONGZUOZHANMC" id="newpwd" value="" maxlength="255" placeholder="请输入新密码" title="请输入新密码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:25%;text-align: center;padding-top: 13px;">确认密码:</td>
								<td><input type="text" name="BEIZHU" id="confirmpwd" value="" maxlength="255" placeholder="请再次输入新密码" title="请再次输入新密码" style="width:98%;"/></td>
							</tr>
							
						</table>
						</div>
						
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
						
					</form>
	
					<div id="zhongxin2" class="center" style="display:none"><img src="static/images/jzx.gif" style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div></div></div>


	
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
<!-- 	<script type="text/javascript" src="static/js/jquery.tips.js"></script> -->
</body>
</html>