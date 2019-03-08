<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>


<!-- 		<link href="${basepath}static/js/layer/skin/default/layer.css" rel="stylesheet"> -->
<!-- 		<script src="static/js/layer/layer.js"></script> -->
<!-- 		<link href="${basepath}static/js/layer/skin/default/layer.css" rel="stylesheet"> -->
<!-- 	    <script src="static/js/bootstrap-dialog.min.js"></script> -->
	    <!-- ztree所需css以及js -->
<!-- 		 <link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css"> -->
<!-- 	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script> -->
<!-- 	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script> -->
<!-- 	    <script type="text/javascript" src="${basepath}static/js/myjs/stu_ztree.js"></script> -->
<!-- 	    <script type="text/javascript" src="${basepath}static/js/myjs/fbjf_ztree.js"></script> -->
	    
		<style type="text/css">
			html { overflow-x:hidden; }
			.jf_ZtBox{
				left:0 !important;
				width:300px !important;
			}
			.jf_mdContent{
				max-height:600px  !important;
				overflow: auto !important;
			}
		</style>
		


	<fieldset>
		<table id="stuinfolist"
			class="table table-striped table-hover table-bordered jf_table" style="text-align: center;">
		</table>
	</fieldset>

	<div class="clearfix"></div>
	<script type="text/javascript" src="${basepath}static/js/myjs/stuinfolist.js"></script>
