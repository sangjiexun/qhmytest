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
		<title>日结账</title>
		<style type="text/css">
			html { overflow-x:hidden; }
		</style>
	</head>
	<link href="${basepath}static/css/bootstrapValidator.min.css" rel="stylesheet">
	<script src="static/js/bootstrap-dialog.min.js"></script>
	<script type="text/javascript">
	var _basepath = "${basepath}";
	</script>
	 <script type="text/javascript">
	var colStr="${colStr}";
	</script>
	<body>
		<form class="form-inline" style="margin-bottom:15px;">
			<input type="text" id="dateAccountDateq" class="laydate-icon form-control" placeholder="操作时间起" readonly="readonly"  />
			<input type="text" id="dateAccountDatez" class="laydate-icon form-control" placeholder="操作时间止" readonly="readonly"  />
			<select name="" class="form-control jf_FltConditions" id="JIAOFEILEIXING" style="width:18%;">
				<option value="">缴费方式</option>
				<option value="CASH">现金</option>
				<option value="CARD">银行卡</option>
				<option value="WX">微信</option>
				<option value="ZFB">支付宝</option>
				<option value="TT">电汇</option>
			</select>
			<input type="text" class="form-control jf_FltConditions" id="studentName" style="width:18%;" placeholder="姓名/身份证号/学号"/>
			<a class="btn btn-danger" id="accountQuery"><span class="fa fa-search"></span> 查询</a>
			<a class="btn btn-danger" id="accountZuoFei"><span class="fa fa-upload"></span>作废</a>
		</form> 
		<table class="table table-striped table-hover table-border jf_table" id="dateAccountTable" style="text-align: center;">
		</table>
	</body>
	<script type="text/javascript" src="${basepath}static/js/myjs/dateAccount.js"></script>
</html>
