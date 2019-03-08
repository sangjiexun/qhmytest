<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>收据设置</title>

		<link href="${basepath}static/js/layer/skin/default/layer.css" rel="stylesheet">
		<script src="static/js/layer/layer.js"></script>
		<style type="text/css">
			html { overflow-x:hidden; }
		</style>
	</head>	
	<body>
		<button class="btn btn-danger" data-toggle="modal" data-target="" id="addone"><span class="fa fa-plus"></span> 新增缴费项</button>
		   <div id="mymodal" class="modal fade" >
				<div class="jf_SmallMd modal-dialog">
					<div class="modal-content" >
						<div class="modal-header jf_mod-header">
							<div class="close" data-dismiss="modal">&times;</div>
							<b class="jf_HeaderText">新增缴费项</b>
						</div>
						<div class="modal-body">
							<form action="" class="form-horizontal">
								<div class="form-group">
									<label for="" class="col-xs-4 control-label">项目名称</label>
									<div class="col-xs-6">
										<input type="text" class="form-control" placeholder="请输入内容" id="xmmc"/>
									</div>
								</div>
								<div class="form-group">
									<label for="" class="col-xs-4 control-label">改变专业</label>
									<div class="col-xs-6">
										<input type="checkbox" class="al-toggle-button" id="gbzy">
									</div>   
								</div>
								<div class="form-group">
									<label for="" class="col-xs-4 control-label">改变缴费项目</label>
									<div class="col-xs-6">
										<input type="checkbox" class="al-toggle-button" id="gbjfxm">
									</div>
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button class="btn btn-danger" id="btn_save">保存</button>
							<button class="btn btn-default" data-dismiss="modal" id="btn_cancel">取消</buttom>
						</div>
					</div>
				</div>
			</div>
			
			<div id="mymodal2" class="modal fade">
				<div class="jf_SmallMd modal-dialog">
					<div class="modal-content" >
						<div class="modal-header jf_mod-header">
							<div class="close" data-dismiss="modal">&times;</div>
							<b class="jf_HeaderText">编辑缴费项</b>
						</div>
						<div class="modal-body">
							<form action="" class="form-horizontal">
								<div class="form-group">
									<label for="" class="col-xs-4 control-label">项目名称</label>
									<div class="col-xs-7">
										<input type="text" class="form-control" placeholder="请输入内容" id="xmmc2"/>
										<input type="text" style="display:none;" class="form-control" placeholder="请输入内容" id="pkid"/>
									</div>
								</div>
								<div class="form-group">
									<label for="" class="col-xs-4 control-label">改变专业</label>
									<div class="col-xs-7">
										<input type="checkbox" class="al-toggle-button" id="gbzy2">
									</div>
								</div>
								<div class="form-group">
									<label for="" class="col-xs-4 control-label">改变缴费项目</label>
									<div class="col-xs-7">
										<input type="checkbox" class="al-toggle-button" id="gbjfxm2">
									</div>
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button class="btn btn-danger" id="btn_save2">保存</button>
							<button class="btn btn-default" data-dismiss="modal" id="btn_cancel2">取消</buttom>
						</div>
					</div>
				</div>
			</div>

	<fieldset>
		<table id="otherpaytable"
			class="table table-striped table-hover table-border jf_table" style="text-align: center;">
		</table>
	</fieldset>

		<div class="clearfix"></div>
		</body>
	<script type="text/javascript" src="${basepath}static/js/myjs/otherpayitemlist.js"></script>

</html>