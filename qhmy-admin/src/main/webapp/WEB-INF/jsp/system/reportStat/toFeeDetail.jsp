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
		<title>收费明细</title>
		<style type="text/css">
			html { overflow-x:hidden; }
		</style>
	</head>
	<link href="${basepath}static/css/bootstrapValidator.min.css" rel="stylesheet">
	<script src="static/js/bootstrap-dialog.min.js"></script>
	<link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css">
			
		    <!--  table横纵向滚动条 -->
	   <link rel="stylesheet" href="${basepath}static/css/bootstrap-table.css">
	   
	   
    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script>

	<script type="text/javascript">
	var _basepath = "${basepath}";
	</script>
	<body>
		<div class="form-inline">
			<input type="text"  id="DATESTART" class="laydate-icon form-control" value="${pd.DATESTART}" placeholder="缴费时间起" readonly="readonly"  /> ~
			<input type="text"  id="DATEEND" class="laydate-icon form-control" value="${pd.DATEEND}" placeholder="缴费时间止" readonly="readonly"  />
			<select name="" class="form-control jf_FltConditions" id="RUXUENIANFEN">
                  <option value="">入学年份</option>
                  <c:forEach items="${ruxuenianfenList}" var="var">
                       <option value="${var.DICTIONARIES_ID }">${var.NAME}</option>
                  </c:forEach>
	   		</select>
	   		<select name="" class="form-control jf_FltConditions" id="BANXING">
                  <option value="">班型</option>
                  <c:forEach items="${banxingList}" var="var">
                       <option value="${var.DICTIONARIES_ID }">${var.NAME}</option>
                  </c:forEach>
	   		</select>
	   		<select name="" class="form-control jf_FltConditions" id="CENGCI">
                  <option value="">学生类型</option>
                  <c:forEach items="${cengci_list}" var="var">
                       <option value="${var.PKID }">${var.CENGCI_NAME}</option>
                  </c:forEach>
	   		</select>
	   		<select name="" class="form-control jf_FltConditions" id="JIAOFEILEIXING">
                  <option value="">缴费类型</option>
                  <c:forEach items="${jiaofeileixingList}" var="var">
                       <option value="${var.PKID }">${var.PAY_STYLE_NAME}</option>
                  </c:forEach>
	   		</select>

			<input type="text" class="form-control jf_FltConditions sfzkzqsousuo" id="keywords" style="width:18%;" placeholder="身份证/姓名"/>

			<button class="btn btn-danger sfzkzqsousuobtn" id="checkQuery"><span class="fa fa-search"></span> 查询</button>
			<a class="btn btn-danger" id="checkOut"><span class="fa fa-upload"></span> 导出</a>
		</div>
		<div class="form-inline" style="text-align: right;">
			<span class="jf_YiJiaoFei" id="TOTALMONEY" style="margin-right:30px;">实收总金额：<a class='gold'>${amountMsg.TOTALMONEY}</a></span>
			<span class="jf_YiJiaoFei" id="CASHTOTALMONEY" style="margin-right:30px;">现金：<a class='gold'>${amountMsg.CASHTOTALMONEY}</a></span>
			<span class="jf_YiJiaoFei" id="CARDTOTALMONEY" style="margin-right:30px;">银行卡：<a class='gold'>${amountMsg.CARDTOTALMONEY}</a></span>			
			<span class="jf_YiJiaoFei" id="ZFBTOTALMONEY" style="margin-right:30px;">支付宝：<a class='gold'>${amountMsg.ZFBTOTALMONEY}</a></span>
			<span class="jf_YiJiaoFei" id="WXTOTALMONEY" style="margin-right:30px;">微信：<a class='gold'>${amountMsg.WXTOTALMONEY}</a></span>
			<span class="jf_YiJiaoFei" id="TTTOTALMONEY" style="margin-right:30px;">电汇：<a class='gold'>${amountMsg.TTTOTALMONEY}</a></span>
		</div>
		<table class="table table-striped table-hover table-border jf_table" id="jieshaorenDetailTable" style="text-align: center;" data-height="640">
		</table>
		
	</body>
	<script type="text/javascript" src="${basepath}static/js/myjs/feeDetail.js"></script>
	<OBJECT id=SynCardOcx1 style="WIDTH: 0px; HEIGHT: 0px"
		codeBase=${basepath}static/js/myjs/SynCardOcx.CAB#version=1,0,0,1
		classid=clsid:4B3CB088-9A00-4D24-87AA-F65C58531039></OBJECT>
	<script type="text/javascript" src="${basepath}static/js/myjs/sfzsousuo.js"></script>

</html>
