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
		<title>缴费审核</title>
	
<%-- 		<script type="text/javascript" src="${basepath}static/js/jquery.min.js"></script>
		<script type="text/javascript" src="${basepath}static/js/bootstrap.js"></script>
		<link rel="stylesheet" href="${basepath}static/gxjf/bs/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${basepath}static/gxjf/css/Employee.css" />
		<link rel="stylesheet" href="${basepath}static/gxjf/css/framework-font.css" />
		<link href="${basepath}static/css/bootstrap-table.min.css" rel="stylesheet">
		<link href="${basepath}static/js/layer/skin/default/layer.css" rel="stylesheet">
		<script src="static/js/layer/layer.js"></script>
		
		
		<script src="static/js/bootstrap-table.min.js"></script>
	    <script src="static/js/bootstrap-dialog.min.js"></script>
	    <script src="static/js/bootstrap-table-zh-CN.min.js"></script>
	    
		
	     <!-- ztree所需css以及js -->
	     <link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css">
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script> --%>
	    <style type="text/css">
			html { overflow-x:hidden; }
			.modal-backdrop{
				z-index:-1 !important;
			}
		</style> 
	</head>
	<script type="text/javascript">
	var _basepath = "${basepath}";
	</script>
		
	<body>
		<form class="form-inline">
		<input type="text" name="RIQIqi" id="RIQIqi" class="laydate-icon form-control" placeholder="开始日期" readonly="readonly"  />
			~
		<input  type="text" class="laydate-icon form-control"  name="RIQIzhi" id="RIQIzhi"  readonly="readonly" style="cursor:pointer;"  placeholder="结束日期"/>
		
		
			<select name="" id="shstatus" class="form-control jf_FltConditions"  placeholder="审核状态">
			<option value="">审核状态</option>
			<c:forEach items="${zhuangtai}" var="fs" varStatus="fst" >
				<option value="${fs.VALUE}">${fs.NAME}</option>
			</c:forEach>
			</select>
			<input type="text" class="form-control jf_FltConditions" id="xmmc" maxlength="50" placeholder="缴费项目名称"/>
			<a  class="btn btn-danger" id="btn_search"><span class="fa fa-search"></span> 查询</a>
			<c:if test="${SESSION_MENU_BUTTONS.jfsh_tg == '1' }">
				<a  class="btn btn-danger" id="btn_tongguo"><span class="fa fa-check-circle" ></span> 通过</a>
			</c:if>
			<c:if test="${SESSION_MENU_BUTTONS.jfsh_btg == '1' }">
				<a  class="btn btn-danger" id="btn_btongguo"><span class="fa fa-times-circle"></span> 不通过</a>
			</c:if>
			
		</form>
	
	
	
	
		<table id="jfshenheinfotable" class="table table-striped table-hover table-border jf_table" style="text-align: center;">
		</table>
		
	<div class="clearfix"></div>
	</body>
	
	<script type="text/javascript" src="${basepath}static/js/myjs/payexamine.js"></script>
</html>
	