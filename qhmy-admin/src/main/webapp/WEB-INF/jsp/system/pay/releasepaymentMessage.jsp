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
		<title>发布缴费</title>
		<link href="${basepath}static/js/layer/skin/default/layer.css" rel="stylesheet">
		<link rel="stylesheet" href="${basepath}static/gxjf/css/Employee.css" />
		<script src="static/js/layer/layer.js"></script>
	    <script src="static/js/bootstrap-dialog.min.js"></script>
	    <script src="${basepath}plugins/laydate-v1.1/laydate/laydate.js"></script>
	     <!-- ztree所需css以及js -->
		<link rel="stylesheet" href="${basepath}static/zTree/css/demo.css" type="text/css">
	    <link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<!-- 	    <script type="text/javascript" src="../../../js/jquery-1.4.4.min.js"></script> -->
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script>
	    <script type="text/javascript">
	
	var zNodes=new Array();

	<c:forEach items="${zuzhilist}" var="t">  
		var a={id:'${t.DEPARTMENT_ID}',pId:'${t.PARENT_ID}',name:'${t.NAME}'};
		zNodes.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
	</c:forEach>
	
	</script>
	     <script type="text/javascript" src="${basepath}static/js/myjs/fbjf_ztree.js"></script>
	    
	    
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
		<a  class="btn btn-danger" id="fanhui"><span class="fa fa-reply"></span> 返回</a>
		<div class="panel panel-info" style="margin-top:15px;">
			<div class="jf_PanelHead panel-heading">
				<div class="panel-title">
					缴费项目
				</div>
			</div>
			<div class="panel-body">
				<form class="form-inline">
					<div class="form-group" style="margin-right:5%;">
						<label for="" class="jf_xing"> 缴费项目</label>
						<input type="text" id="fabuxm" readonly class="form-control" value="${pdItem.PAYITEM}" placeholder="填写缴费项目" />
					</div>
					<div class="form-group">
						<label for="" class="jf_xing"> 费用</label>
						<input type="text" id="fabuje" readonly class="form-control" value="${pdItem.COST}" placeholder="填写缴费金额" />
					</div>
					<div class="clearfix"></div>
				</form>
				<div class="checkbox" style="margin:15px 0 0 2px;">
					<label for="">
						<input id="bixujf"  <c:if test="${pdItem.MUSTPAY == 'Y' }">checked = 'checked'</c:if> type="checkbox" disabled /> 必须缴费
					</label>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="jf_PanelHead panel-heading">
				<div class="panel-title">
					缴费名单
				</div>
			</div>
			<div class="panel-body">
			<c:if test="${CREATEMODE == '1' }">
				<div class="form-inline" style="margin-bottom:15px;">
					<div class="radio" style="float:left;">
						<label for="">
							<input type="radio" id="daorumd" <c:if test="${pdItem.ITEMLIST_CREATEMODE == '1' }">checked = 'checked'</c:if> value="dr" disabled  name="jfszr"/> 导入名单
						</label>
					</div>
					
				</div>
				</c:if>
				<c:if test="${CREATEMODE == '2' }">
				<div class="form-inline" style="margin-bottom:15px;">
					<div class="radio" style="margin-right:15px;">
						<label for="">
							<input type="radio" id="guizesc" <c:if test="${pdItem.ITEMLIST_CREATEMODE == '2' }">checked = 'checked'</c:if> value="gz" disabled name="jfszr"/> 系统根据规则生成
						</label>
					</div>
					<button class="btn btn-danger" disabled data-toggle="modal" data-target="#mymodaljf" id="jfszbtn">
						<span class="fa fa-gear"></span> 缴费设置
					</button>
				</div>
				</c:if>
				
				<div id="mymodaljf" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header jf_mod-header">
								<div class="close" data-dismiss="modal">&times;</div>
								<b class="jf_HeaderText">缴费设置</b>
							</div>
							<div class="modal-body">
								<form action="" class="form-inline">
										<div class="form-group" style="margin-bottom: 15px;">
											<label for="" style="width:60px;">专业</label>
											<input type="text" class="form-control" value=""  onclick="showMenu();" id="citySel" style="margin-left:14px;"/>
										</div>
								<div id="menuContent" class="menuContent"
									style="display:none; position: absolute;z-index:1;">
									<ul id="treeDemo" class="ztree"
										style="margin-top:0; width:300px; height: 300px;"></ul>
								</div>
								<div style="display:none;">
									<!--用来存储 树节点的id -->
									<input type="text" name="tree" id="orgtree" class="form-control" />
								</div>


								<br>
										<div class="form-group" style="margin-bottom: 15px;">
											<label for="" style="width:60px;">年级</label>
											<c:forEach items="${listnianji}" var="var" varStatus="vs" >
												<div class="checkbox" style="margin: 0 15px;">
												<label for="">
													<input type="checkbox" name="nianji" val="${var.NAME}" value="${var.BIANMA}"/> ${var.NAME}
												</label>
											</div>
											</c:forEach>
										</div>
										<div class="form-group" style="margin-bottom: 15px;">
											<label for="" style="width:60px;">优惠方式</label>
											<c:forEach items="${listyouhuifs}" var="fs" varStatus="fst" >
											<c:if test ="${fs.VALUE == 0}">
											<div class="radio" style="margin: 0 15px;">
												<label for="">
													<input type="radio" Checked value="${fs.VALUE}" val="${fs.NAME}"  name="yhfs"/> ${fs.NAME}
												</label>
											</div>
											</c:if>
											
											<c:if test ="${fs.VALUE == 1}">
											<div class="form-group" style="margin-right: 15px;">
												<div class="radio">
													<label for="">
														<input type="radio" value="${fs.VALUE}" val="${fs.NAME}" name="yhfs"/> ${fs.NAME}
													</label>
												</div>
												<input type="" name="" id="dazheshu" value="" class="form-control jf_SmInput" /> 折
											</div>
											</c:if>
											
											<c:if test ="${fs.VALUE == 2}">
											<div class="form-group">
												<div class="radio">
													<label for="">
														<input type="radio" value="${fs.VALUE}" val="${fs.NAME}" name="yhfs"/> ${fs.NAME}
													</label>
												</div>
												<input type="" name="" id="zhijianshu" value="" class="form-control jf_SmInput" /> 元
											</div>
											</c:if>
											</c:forEach>
										</div>
										<div class="form-group" style="margin-bottom: 15px;">
											<label for="" style="width:60px;">优惠范围</label>
											<c:forEach items="${listyouhuifw}" var="fw" varStatus="fw1" >
												<div class="checkbox" style="margin: 0 15px;">
												<label for="">
													<input type="checkbox" name="youhuifw" val="${fw.NAME}" value="${fw.BIANMA}"/> ${fw.NAME}
												</label>
												</div>
												</c:forEach>
										</div>
								</form>
							</div>
							<div class="modal-footer">
								<button class="btn btn-info queren"  data-dismiss="modal"  >确认</button>
								<button class="btn btn-danger"  data-dismiss="modal">取消</buttom>
						</div>
						<input type="hidden" name="trnumber" id="trnumber" value="" />
					</div>
				</div>
			</div>
		
				<c:if test="${CREATEMODE == '2' }">
				<table class="table table-striped table-hover table-bordered jf_table" style="text-align: center;" id="fabutable">
				<tr >
					<th>年级</th>
					<th>专业</th>
					<th>学生类型</th>
					<th>批次</th>
					<th>优惠学生类型</th>
					<th>优惠方式</th>
					
				</tr>
			</table>
			</c:if>
			</div>
		</div>
		
		<div class="panel panel-info">
			<div class="jf_PanelHead panel-heading">
				<div class="panel-title">
					有效期设置
				</div>
			</div>
			<div class="panel-body">
				<form class="form-inline">
					<div class="form-group" style="margin-right:5%;">
						<label for=""> 起始日期</label>
						<input type="text" name="RIQIqi" id="RIQIqi" value="${pdItem.STARTDATE}" disabled class="laydate-icon form-control" placeholder="起始日期" readonly="readonly"  />
					</div>
					<div class="form-group" >
						<label for=""> 结束日期</label>
						<input type="text" name="RIQIzhi" id="RIQIzhi" value="${pdItem.ENDDATE}" disabled class="laydate-icon form-control" placeholder="结束日期" readonly="readonly"  />
					</div>
					<div class="clearfix"></div>
				</form>
			</div>
		</div>
		<div class="jf_BtnCenter pull-right" <c:if test="${pd.ZT != '待审核' }">style='display:none;'</c:if>>
			<a  class="btn btn-danger" id="Mtongguo" style="width: 70px;">通过</a>
			<a  class="btn btn-default" id="Mbtongguo">不通过</a>
		</div>
		<div class="clearfix"></div>
		<input type="hidden" name="PKID" id="PKID" value="${pd.PKID}" />
		<input type="hidden" name="xgym" id="xgym" value="${pd.xgym}" />
	</body>
	<script type="text/javascript" src="${basepath}static/js/myjs/releasepaymentMessage.js"></script>
	
	<script type="text/javascript">
	var a='${json}';
	var dataset = $.parseJSON(a);
	for(var i=0;i<dataset.length;i++){
		$("<tr ><td >"+dataset[i].GRADE+"</td><td>"+dataset[i].NIANJI+"</td><td>"+dataset[i].CENGCI_NAME+"</td><td>"+dataset[i].PICI_NAME+"</td><td>"+dataset[i].XSLX+"</td><td>"+dataset[i].YHFS+"</tr>").appendTo("#fabutable");	
	}
	</script>
</html>
	