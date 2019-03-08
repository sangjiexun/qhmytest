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
		<title>退费明细</title>
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
			input::-webkit-input-placeholder {
			  font-size:14px;
			}
			
		</style>
	<body>
		<form class="form-inline">
			<input type="text"  id="DATESTART" value="${dateStringstart }" class="laydate-icon form-control" placeholder="缴费时间起" readonly="readonly"  /> ~
			<input type="text"  id="DATEEND" value="${dateStringend }" class="laydate-icon form-control" placeholder="缴费时间止" readonly="readonly"  />
			<select name="" id="schoolYear" class="form-control jf_FltConditions"  placeholder="入学年份">
				<option value="">入学年份</option>
				<c:forEach items="${rxnf}" var="sy" varStatus="syt" >
					<option value="${sy.PKID}">${sy.NAME}</option>
				</c:forEach>
			</select>
		
			<select name="" id="classType" class="form-control jf_FltConditions"  placeholder="班型">
				<option value="">班型</option>
				<c:forEach items="${banji_type}" var="sy" varStatus="syt" >
					<option value="${sy.PKID}">${sy.NAME}</option>
				</c:forEach>
			</select>
		
		<select name="" id="cengci" class="form-control jf_FltConditions"  placeholder="学生类型">
				<option value="">学生类型</option>
				<c:forEach items="${cengci}" var="sy" varStatus="syt" >
					<option value="${sy.PKID}">${sy.CENGCI_NAME}</option>
				</c:forEach>
			</select>
			
				<select name="" id="itempay" class="form-control jf_FltConditions"  placeholder="缴费类型">
				<option value="">缴费类型</option>
				<c:forEach items="${payitem}" var="sy" varStatus="syt" >
					<option value="${sy.PKID}">${sy.NAME}</option>
				</c:forEach>
			</select>
			
			<input type="text" class="form-control jf_FltConditions sfzkzqsousuo" id="keywords" style="width:18%;" placeholder="身份证/姓名"/>
			<a class="btn btn-danger" id="checkQuery"><span class="fa fa-search"></span> 查询</a>
			<a class="btn btn-danger" id="checkOut"><span class="fa fa-upload"></span> 导出</a>
		</form> 
		<div class="form-inline" style="text-align: right;">
			<span class="jf_YiJiaoFei" id="TOTALMONEY" style="margin-right:30px;">退费总金额：<a class='gold'>${amountMsg.TOALREFUNDMONEY}</a></span>
			<span class="jf_YiJiaoFei" id="CASHTOTALMONEY" style="margin-right:30px;">现金：<a class='gold'>${amountMsg.TOTALCASH}</a></span>
			<span class="jf_YiJiaoFei" id="CARDTOTALMONEY" style="margin-right:30px;">银行卡：<a class='gold'>${amountMsg.TOTALCARDMONEY}</a></span>
			<span class="jf_YiJiaoFei" id="TTTOTALMONEY" style="margin-right:30px;">电汇：<a class='gold'>${amountMsg.TOTALTTMONEY}</a></span>
		</div>
		<table class="table table-bordered sf_table" id="advancePayTable" style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
		</table>
	</body>
	<script type="text/javascript" src="${basepath}static/js/myjs/summaryRefund.js"></script>
</html>
