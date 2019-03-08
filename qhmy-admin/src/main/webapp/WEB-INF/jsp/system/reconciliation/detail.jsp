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
		<title>其他缴费</title>
		<link href="${basepath}static/js/layer/skin/default/layer.css" rel="stylesheet">
		<script src="static/js/layer/layer.js"></script>
	    <script src="static/js/bootstrap-dialog.min.js"></script>   
		<style type="text/css">
			html { overflow-x:hidden; }
		</style>
		
	</head>

	<body>
		<div class="form-inline">
			<input type="text" class="form-control jf_FltConditions" style="width:18%;" placeholder="开始时间" id="startdate"/> ~
			<input type="text" class="form-control jf_FltConditions" style="width:18%;" placeholder="截止时间" id="enddate"/>
			<input type="text" class="form-control jf_FltConditions" style="width:18%;" placeholder="身份证号/姓名" id="keywords"/>
			<!-- 支付方式 -->
			<select name="" class="form-control jf_FltConditions" style="width:18%;" id="TRANSACTION_PAY_PLATFORM">
				<option value="">支付方式</option>
				<option value="ZFB">支付宝</option>
				<option value="WX">微信</option>
			</select>
			<select name="" class="form-control jf_FltConditions" style="width:18%;" id="TRANSACTION_STATUS">
				<option value="">对账结果</option>
				<option value="2">对账成功</option>
				<option value="-2">对账失败</option>
			</select>
			<button class="btn btn-danger" id="btn_search"><span class="fa fa-search" ></span> 查询</button>
			<button class="btn btn-danger" id="checkOut"><span class="fa fa-upload"></span> 导出</button>
			<c:if test="${pd.flag=='y' }">
				<button class="btn btn-danger" id="back_tohuizong"><span class="fa fa-reply"></span> 返回</button>
			</c:if>
		</div>
	    <table id="detailtable" class="table table-striped table-hover table-border jf_table" style="text-align: center;">
		</table>
	</body>
	<script type="text/javascript">
	var RIQI="${pd.RIQI}";
	</script>
	<script type="text/javascript" src="${basepath}static/js/myjs/reconciliationdetail.js"></script>
</html>
	