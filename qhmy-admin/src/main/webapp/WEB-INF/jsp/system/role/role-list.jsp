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
		<title>权限管理-角色管理</title>
		<link rel="stylesheet" href="${basepath }static/css/bs/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${basepath }static/css/jf_custom.css" />
		<link rel="stylesheet" href="${basepath }static/css/framework-font.css" />
		<link href="${basepath}static/js/layer/skin/default/layer.css" rel="stylesheet">

		<script type="text/javascript" src="${basepath }static/css/bs/js/jquery.min.js"></script>
		<script type="text/javascript" src="${basepath }static/css/bs/js/bootstrap.js"></script>
		
		<script src="${basepath}static/js/layer/layer.js"></script>
	    <script src="${basepath}static/js/bootstrap-dialog.min.js"></script>
	    
	    <link href="${basepath}static/css/bootstrap-table.min.css" rel="stylesheet">
		<script src="${basepath}static/js/bootstrap-table.min.js"></script>
	    <script src="${basepath}static/js/bootstrap-table-zh-CN.min.js"></script>
	    
	    <link rel="stylesheet" href="static/gxjf/css/Employee.css" />
		<link rel="stylesheet" type="text/css" href="${basepath}static/css/bootstrap-treeview.css">
	    <script src="${basepath}static/js/bootstrap-treeview.js"></script>
		
		<style type="text/css">
			html { overflow-x:hidden; }
		</style>
		
		
		<div class="jf_tabrow">
			<button class="btn btn-danger" data-toggle="modal" data-target="#mymodal2"><span class="fa fa-plus"></span> 新增角色</button>
			<div id="mymodal2" class="modal fade">
				<div class="jf_SmallMd modal-dialog" style="z-index: 1100;">
					<div class="modal-content">
						<div class="modal-header jf_mod-header">
							<div class="close" data-dismiss="modal">&times;</div>
							<b class="jf_HeaderText">新增角色</b>
						</div>
						<div class="modal-body">
							<form action="" class="form-horizontal">
								<div class="form-group">
									<label for="" class="col-xs-3 control-label">角色名</label>
									<div class="col-xs-7">
										<input type="text" class="form-control" placeholder="请输入内容"/>
									</div>
								</div>
								<div class="form-group">
									<label for="" class="col-xs-3 control-label">权限</label>
									<div class="col-xs-3">
										<div class="radio">
											<input type="radio" name="role" id="role" value="" />缴费列表
										</div>
										<div class="radio">
											<input type="radio" name="role" id="role" value="" />缴费详情页
										</div>
										<div class="radio">
											<input type="radio" name="role" id="role" value="" />缴费名单
										</div>
										<div class="radio">
											<input type="radio" name="role" id="role" value="" />发布缴费
										</div>
									</div>
									<div class="col-xs-3">
										<div class="radio">
											<input type="radio" name="role" id="role" value="" />校长
										</div>
										<div class="radio">
											<input type="radio" name="role" id="role" value="" />财务主管
										</div>
										<div class="radio">
											<input type="radio" name="role" id="role" value="" />财务员工
										</div>
										<div class="radio">
											<input type="radio" name="role" id="role" value="" />系主任
										</div>
									</div>
									<div class="col-xs-3">
										<div class="radio">
											<input type="radio" name="role" id="role" value="" />校长
										</div>
										<div class="radio">
											<input type="radio" name="role" id="role" value="" />财务主管
										</div>
										<div class="radio">
											<input type="radio" name="role" id="role" value="" />财务员工
										</div>
										<div class="radio">
											<input type="radio" name="role" id="role" value="" />系主任
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="" class="col-xs-3 control-label">是否启用</label>
									<div class="col-xs-7">
										<input type="checkbox" class="al-toggle-button">
									</div>
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button class="btn btn-info">确认</button>
							<button class="btn btn-danger" data-dismiss="modal">取消</buttom>
						</div>
					</div>
				</div>
			</div>
			<table class="table table-striped table-hover table-border jf_table" style="text-align: center;">
				<tr>
					<th>角色</th>
					<th>角色权限</th>
					<th>最后登录时间</th>
					<th>是否启用</th>
					<th>操作</th>
				</tr>
				<tr>
					<td>admin</td>
					<td>财务专员</td>
					<td>2017.7.20</td>
					<td><input type="checkbox" class="al-toggle-button"></td>
					<td class="jf_Operation"><span class="fa fa-pencil"></span>&nbsp;&nbsp;
						<span class="fa fa-trash-o"></span>
					</td>
				</tr>
				<tr>
					<td>admin</td>
					<td>财务专员</td>
					<td>2017.7.20</td>
					<td><input type="checkbox" class="al-toggle-button"></td>
					<td class="jf_Operation"><span class="fa fa-pencil"></span>&nbsp;&nbsp;
						<span class="fa fa-trash-o"></span>
					</td>
				</tr>
				<tr>
					<td>admin</td>
					<td>财务专员</td>
					<td>2017.7.20</td>
					<td><input type="checkbox" class="al-toggle-button"></td>
					<td class="jf_Operation"><span class="fa fa-pencil"></span>&nbsp;&nbsp;
						<span class="fa fa-trash-o"></span>
					</td>
				</tr>
			</table>
		</div>
		
		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
		
		<script type="text/javascript" src="${basepath }static/js/myjs/role-list.js"></script>
