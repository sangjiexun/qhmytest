<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>


<!DOCTYPE html>
<html>
	    
		<link href="${basepath}static/js/layer/skin/default/layer.css" rel="stylesheet">
		<script src="static/js/layer/layer.js"></script>
	     <!-- ztree所需css以及js -->
	     <link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css">
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script>
	    <script type="text/javascript" src="${basepath}static/js/common/jquery.jPrintArea.js"></script>
	    
		<style type="text/css">
			html { overflow-x:hidden; }
			.jf_add:hover{
				color:#db2823 !important;
				text-decoration: none !important;
			}
		</style>
<script type="text/javascript">
	var _basepath = "${basepath}";
	var user_id="${userpd.USERID}"
	var zNodes=new Array();
	<c:forEach items="${zuzhilist}" var="t">  
	var a={id:'${t.DEPARTMENT_ID}',pId:'${t.PARENT_ID}',name:'${t.NAME}'};
	zNodes.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
    </c:forEach>
	//项目数组
	var xms=new Array();
	<c:forEach items="${payitemslist}" var="t">  
	var a={pkid:'${t.PKID}',xmname:'${t.PAYITEM}',xmje:'${t.COST}'};
	xms.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
    </c:forEach>
</script>
<script type="text/javascript" src="${basepath}static/js/myjs/stu_ztree.js"></script>
	<body>
		<div class="form-inline">
			<input type="text" class="form-control jf_FltConditions" style="width:18%;" placeholder="身份证/学号/姓名" id="search"/>
			<select name="" class="form-control jf_FltConditions" style="width:18%;" id="otherpayitem">
					<option  value="">请选择缴费项目</option>
				<c:forEach items="${otherpayList}" var="otherpay">
					<option value="${otherpay.PKID}">${otherpay.PAYITEM} </option>
				</c:forEach>
			</select>
			<button class="btn btn-danger" id="btn_search"><span class="fa fa-search"></span> 查询</button>
		</div> 
		<div class="jf_message bg-warning">
			<div class="row">
				<div class="jf_MsList">
					<label class="jf_OfLabel" for="">姓名：</label>
					<span id="name"></span>
				</div>
				<div class="jf_MsList">
					<label class="jf_OfLabel" for="">身份证：</label>
					<span id="sfz"></span>
				</div>
				<div class="jf_MsList">
					<label class="jf_OfLabel" for="">学号：</label>
					<span id="xuehao"></span>
				</div>
				<div class="jf_MsList" style="width:150px;">
					<label class="jf_OfLabel" for="">在学状态：</label>
					<span id="zxzt"></span>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="row">
				<div class="jf_MsList">
					<label class="jf_OfLabel" for="">年级：</label>
					<span id="nianji"></span>
				</div>
				<div  class="jf_MsList">
					<label class="jf_OfLabel" for="">院校专业：</label>
					<span id="zuzhi"></span>
				</div>
				
			</div>
		</div>
		<div class="form-inline" style="text-align: right;">
			<a href="" id="btn_qtjf" class="btn btn-danger" data-toggle="modal" data-target="" disabled="disabled"><span class="fa fa-plus"></span> 其他缴费</a>
			<!-- <a id="payPrintBatchButton" class="btn btn-danger" href="javascript:void(0);" disabled="disabled">
				<span class="fa fa-print"></span>
				批打收据
			</a> -->
		</div>
	
		<table id="optable" class="table table-striped table-hover table-border jf_table" style="text-align: center;">
		</table>
	
		
		<!--批量缴费弹框-->
	<div id="mymodal" class="modal fade">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header jf_mod-header">
								<div class="close" data-dismiss="modal">&times;</div>
								<b class="jf_HeaderText">其他缴费</b>
							</div>
							<div class="modal-body">
								<div class="list form-inline">
									<div class="form-group col-xs-4">
										<label class="jf_OfLabel">收费项目</label>
										<select size="1" name="D1"  class="form-control jf_ModInputW" id="select_pro">  
											   <option value="" tzzy="" tzxm="">请选择其他缴费项目</option>
											<c:forEach items="${otherpayList}" var="otherpay">
					                           <option value="${otherpay.PKID}" tzzy="${otherpay.UPDATEPROFESSION}" tzxm="${otherpay.UPDATEPAYITEM}">${otherpay.PAYITEM}</option>
											</c:forEach>
										</select>
									</div>
									<div class="pull-right">
											<a href="javascript:void(0);" id="addone" class="jf_add"><span class="fa fa-plus-circle jf_icon"></span> 添加</a>
									</div>
									<div class="clearfix"></div>
								</div>
									
								<!-- z-tree结构 -->
					<!-- begin z-tree结构 -->
					<div class="form-inline" style="margin:15px 0;">
						<div id="zuzhitree" style="display:none;">
							<div class="form-group col-xs-4" >
				                  <div class="zTreeDemoBackground left">
									<ul class="list" style="margin-bottom:0;">
										<li class="jf_ZtLaber title">
										<label class="jf_label jf_OfLabel" for="">院校专业</label>
										<input class="form-control" id="citySel" type="text" readonly value="" style="width:150px;" onclick="showMenu();" />
											<div id="menuContent" class="jf_ZtBox menuContent">
												<ul id="treeDemo" class="jf_ZtList ztree"></ul>
											</div>
										</li>
									</ul>
								 </div>
				 <div style="display:none;">
			        <!--用来存储 树节点的id -->
				    <input type="text" name="tree" id="orgtree" class="form-control"/>
			     </div>
						     </div>
					   			<div class="form-group col-xs-4" >  
							     <label class="jf_label jf_OfLabel" for="">年级</label>
					               <select name="" class="form-control jf_FltConditions jf_ModInputW" id="grade" placeholder="请输入内容"> 
					                   <option value="">请选择年级</option>  
					                   <c:forEach items="${gradelist}" var="nianji">
					                        <option value="${nianji.BIANMA }">${nianji.NAME }</option>
					                   </c:forEach>
							   		</select>
					   			</div>
					   			<div class="clearfix"></div>
						</div>
					</div>
			<!-- end z-tree结构 -->
					<!--begin 每一组转入转出项目添加到这里  -->
					<div style="margin: 15px;border-top: 1px dashed red;"></div>
					<div id="quyu" style="margin-left:4px;">
						
					</div>
					<!--end 每一组转入转出项目添加到这里  -->
								
								
											
								<div class="clearfix"></div>
								<div>
									<div class="form-group col-xs-4">
										<input type="checkbox" id="xcsf"/>
										<label >现场收费</label>
										<a href="javascript:void(0);" id="calTotalMoney" class="btn btn-danger"  disabled="disabled" style="margin-left:13px;">计算总金额</a>
									</div>
									<div class="clearfix"></div>
								</div>
								
								<div id="jiaoqian" style="display:none;">
								<div class="list form-inline">
									<div style="margin-bottom: 15px;"> 
										<div class="form-group" style="margin:0 40px;">
											<input type="radio" name="jiaof" checked id="sfradio"/>
											<label >收费</label> 
										</div>
										<div class="form-group">
											<input type="radio" name="jiaof" id="tfradio"/>
											<label >退费</label>
										</div>
										<div class="clearfix"></div>
									</div>
									
								</div>
								<div class="list form-inline">
									<div style="margin-bottom: 15px;"> 
										<div class="form-group col-xs-4">
											<label class="jf_OfLabel">现金</label>
											<input class="form-control jf_ModInputW" placeholder="请输入内容" id="totalcash"/> 
										</div>
										<div class="clearfix"></div>
									</div>
								</div>
								<div class="list form-inline">
									<div style="margin-bottom: 15px;">
										<div class="form-group col-xs-4">
											<label class="jf_OfLabel">银行卡</label>
											<input class="form-control jf_ModInputW" placeholder="请输入内容" id="totalcard"/>
										</div>
										<div class="form-group col-xs-4">
											<label class="jf_OfLabel">卡号</label>
											<input class="form-control jf_ModInputW" placeholder="请输入内容" id="cardnum"/>
										</div>
										<div class="clearfix"></div>
									</div>
								</div>
								<div class="list form-inline" id="wx">
									<div style="margin-bottom: 15px;"> 
										<div class="form-group col-xs-4">
											<label class="jf_OfLabel">微信</label>
											<input class="form-control jf_ModInputW" placeholder="请输入内容" id="totalwechat"/> 
										</div>
										<div class="clearfix"></div>
									</div>
								</div>
								<div class="list form-inline" id="zfb">
									<div style="margin-bottom: 15px;"> 
										<div class="form-group col-xs-4">
											<label class="jf_OfLabel">支付宝</label>
											<input class="form-control jf_ModInputW" placeholder="请输入内容" id="totalalipay"/> 
										</div>
										<div class="clearfix"></div>
									</div>
								</div>
								<div class="list form-inline">
									<div style="margin-bottom: 15px;"> 
										<div class="form-group col-xs-4">
											<label class="jf_OfLabel">备注</label>
											<input class="form-control jf_ModInputW" placeholder="请输入内容" id="beizhu"/>
										</div>
										<div class="clearfix"></div>
									</div>
								</div>
						</div>
						</div>
							<div class="modal-footer">
								<button class="btn btn-danger" id="btn_save">保存</button>
								<button class="btn btn-default" data-dismiss="modal" id="btn_cancel">取消</buttom>
							</div>
					</div>
				</div>
			</div>
			<!--批量打印-->
			<div id="batchPrintArea"  style="position: relative;display:none;">
			</div>
	</body>
	<script type="text/javascript" src="${basepath}static/js/myjs/otherpay.js"></script>
</html>
	