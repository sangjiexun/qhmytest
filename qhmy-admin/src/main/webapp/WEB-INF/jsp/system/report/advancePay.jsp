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
		<title>预交费明细</title>
		<style type="text/css">
			html { overflow-x:hidden; }
		</style>
	</head>
	<link href="${basepath}static/css/bootstrapValidator.min.css" rel="stylesheet">
	<script src="static/js/bootstrap-dialog.min.js"></script>
	<script type="text/javascript">
	var _basepath = "${basepath}";
	</script>
	<style type="text/css">
			html { overflow-x:hidden; }
			.sf_rowspan{
				vertical-align: middle !important;
			}
			.sf_table tr th{
				text-align: center;
			}
			.font{
				font-size: 14px;
			}
		</style>
	<body>
		<form class="form-inline">
			<input type="text"  id="DATESTART" class="laydate-icon form-control font" placeholder="缴费时间起" readonly="readonly"  /> ~
			<input type="text"  id="DATEEND" class="laydate-icon form-control font" placeholder="缴费时间止" readonly="readonly"  />
			<select name="" id="schoolYear" class="form-control jf_FltConditions"  placeholder="入学年份">
				<option value="">预交费年份</option>
				<c:forEach items="${schoolYearList}" var="sy" varStatus="syt" >
					<option value="${sy.DICTIONARIES_ID}">${sy.NAME}</option>
				</c:forEach>
			</select>
			<select name="" id="classType" class="form-control jf_FltConditions"  placeholder="班型">
				<option value="">预交费班型</option>
				<c:forEach items="${classTypeList}" var="ct" varStatus="ctt" >
					<option value="${ct.DICTIONARIES_ID}">${ct.NAME}</option>
				</c:forEach>
			</select>
			<input type="text" class="form-control jf_FltConditions sfzkzqsousuo" id="keywords" style="width:18%;" placeholder="身份证/姓名"/>
			<a class="btn btn-danger" id="checkQuery"><span class="fa fa-search"></span> 查询</a>
			<a class="btn btn-danger" id="checkOut"><span class="fa fa-upload"></span> 导出</a>
		</form> 
		<div class="form-inline" style="text-align: right;">
			<span class="jf_YiJiaoFei" id="TOTALMONEY" style="margin-right:30px;">预交费总金额：<a class='gold'>${amountMsg.TOTALJYMONEY}</a></span>
		</div>
		<table class="table table-bordered sf_table" id="advancePayTable" style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
		</table>
	</body>
	<script type="text/javascript" src="${basepath}static/js/myjs/advancePay.js"></script>
</html>
