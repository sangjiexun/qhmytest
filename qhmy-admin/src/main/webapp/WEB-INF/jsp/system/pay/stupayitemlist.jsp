<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>


		<link href="${basepath}static/js/layer/skin/default/layer.css" rel="stylesheet">
		<script src="static/js/layer/layer.js"></script>
		<link href="${basepath}static/js/layer/skin/default/layer.css" rel="stylesheet">
	    <script src="static/js/bootstrap-dialog.min.js"></script>
	    <!-- ztree所需css以及js -->
		 <link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css">
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script>
	    <script type="text/javascript" src="${basepath}static/js/myjs/stu_ztree.js"></script>
	    <script type="text/javascript" src="${basepath}static/js/myjs/fbjf_ztree.js"></script>
	    
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
		<div class="form-inline">
			<select name="" class="form-control jf_FltConditions" style="width:28%;" id="payitem">
					<option  value="">缴费项目</option>
					<c:forEach items="${allPayItemsList}" var="i">
						<option value="${i.PKID}">${i.PAYITEM} </option>
					</c:forEach>
			</select>
			<select name="" class="form-control jf_FltConditions" style="width:28%;" id="statusselect">
					<option  value="">缴费状态</option>
					<c:forEach items="${statusMap}" var="status">
						<option value="${status.value}">${status.key} </option>
					</c:forEach>
			</select>
			<select name="GRADE" class="form-control jf_FltConditions" style="width:18%;" id="gradeselect">
					<option  value="">年级</option>
					<c:forEach items="${dictionariesList}" var="dictionaries">
						<option value="${dictionaries.BIANMA}">${dictionaries.NAME} </option>
					</c:forEach>
					
			</select>
			<!-- z-tree结构 -->
			<div class="form-group">
                 <div class="zTreeDemoBackground left">
					<ul class="list" style="margin-bottom:0;">
						<li class="jf_ZtLaber title"><input class="form-control" id="citySel" type="text" readonly value="" onclick="showMenu();" placeholder="院系专业" style="width:183px;"/>
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
			
			<input type="text" class="form-control jf_FltConditions" style="width:38%;" placeholder="身份证/学号/姓名" name="PAYITEM" id="conditionsselect"/>
			<div style="margin-top:15px;">
			<button class="btn btn-danger" id="btn_export"><span class="fa fa-upload"></span> 导出</button>
				
				<button class="btn btn-danger" id="btn_search"><span class="fa fa-search"></span> 查询</button>
	<!-- 			<button class="btn btn-danger" id="btn_import_add"><span class="fa fa-plus"></span> 添加缴费名单</button>&nbsp;&nbsp; -->
	<!-- 			<button class="btn btn-danger" id="btn_import"><span class="fa fa-sign-in"></span> 导入缴费名单</button>&nbsp;&nbsp; -->
	<!-- 			<button class="btn btn-danger" id="btn_import2"><span class="fa fa-download"></span> 导入已缴名单</button>&nbsp;&nbsp; -->
			</div>
		</div> 
			<!--缴费名单-->
	<div id="mymoda5" class="modal fade">
		<div class="modal-dialog modal-lg">
						<div class="modal-content">
						<div class="modal-header jf_mod-header">
								<div class="close" data-dismiss="modal">&times;</div>
								<b class="jf_HeaderText">缴费记录</b>
							</div>
					<div class="jf_mdContent" style="padding:20px;">
						<table class="table bg-warning" style="margin-bottom: 30px;">
			<tr style="height: 40px;">
				<td></td>
				<td class="jf_DetailsTd" id="payItemObj"></td>
				<td class="jf_DetailsTd" id="costObj"></td>
				<td class="jf_DetailsTd" id="discountScopeObj"></td>
				<td class="jf_DetailsTd" id="discountMoneyObj"></td>
				<td class="jf_DetailsTd"><span class="label label-success" id="statusObj">已完成</span></td>
			</tr>
		</table>
						<!-- <div class="jf_PanelHead">
								<b class="jf_HeaderText">缴费记录</b>
							</div> -->
							
		<table class="table table-striped table-hover table-bordered jf_table" style="text-align: center;" id="payRecordList">
			
		</table>
		
		<div class="panel panel-info" style="margin-top: 15px;">
			<div class="jf_PanelHead panel-heading">
				<div class="panel-title">
					缴费记录
				</div>
			</div>
			<div class="panel-body">
				<table class="table table-striped jf_TrNone" id="payOrderDetailObj">
					
				</table>
			</div>
		</div>
		</div>
		</div>
		</div>
		</div>
			<div id="mymodal2" class="modal fade" >
				<div class="modal-dialog">
					<form action="" class="form-horizontal">
						<div class="modal-content" >
							<div class="modal-header jf_mod-header">
								<div class="close" data-dismiss="modal">&times;</div>
								<b class="jf_HeaderText">调整应收金额</b>
							</div>
							<div class="modal-body">
									<div class="form-group">
										<label for="" class="col-xs-2 control-label">原费用</label>
										<div class="col-xs-9">
											<table class="table table-bordered text-center ">
											      <tr class="active"  style="border:1px solid #ddd;">
											         <th class=" text-center">费用</th>
											         <th class=" text-center">优惠方式</th>
											         <th class=" text-center">应收金额</th>
											      </tr>
											      <tr style="border:1px solid #ddd;">
											         <td id="COST"></td>
											         <td id="DISCOUNT"></td>
											         <td id="AMOUNTRECEIVABLE"></td>
											      </tr>
											</table>
										</div>
									</div>
									<div class="form-group">
										<label for="" class="col-xs-2 control-label">调整后</label>
										<div class="col-xs-9" style="margin-top:4px;">
											<input type="hidden" placeholder="请输入内容" name="pkid" id="pkid" />
											费用<input class="form-control jf_LiInput" type="number" placeholder="" name="discount" id="cost_new" style="display: inline;margin-left:37px;"/><br/><br/>
											<span style="padding-top:10px;">
											优惠方式&nbsp;&nbsp;
											<c:forEach items="${discountModeMap}" var="mode" varStatus="status">
												<input  type="radio" name="discountMode" value="${mode.value}" id="r${mode.value}">${mode.key}&nbsp;&nbsp;
												<c:if test="${mode.value == 1}">
	   												<input class="form-control jf_LiInput1" type="text" placeholder="" id="discount_zhe" style="display: inline;" disabled="disabled"  readonly="readonly" onkeyup="value=value.replace(/[^\d.]/g,'')"/> 折&nbsp;&nbsp;
												</c:if>
												<c:if test="${mode.value == 2}">
	   												<input class="form-control jf_LiInput1" type="text" placeholder="" id="discount_jian" style="display: inline;" disabled="disabled"  readonly="readonly" onkeyup="value=value.replace(/[^\d.]/g,'')"/> 元&nbsp;&nbsp;
												</c:if>
											</c:forEach>
											</span>
											<br/><br/>
											<span style="margin-top:10px;">
											应收金额&nbsp;&nbsp;<input class="form-control jf_LiInput" type="number" placeholder="" name="amountreceivable" id="amountreceivable_new"  disabled="disabled"  readonly="readonly" style="display: inline;"/><br/>
											</span>
										</div>
									</div>
							</div>
						</form>
						
						<div class="modal-footer">
							<button class="btn btn-danger" id="btn_save">保存</button>
							<button class="btn btn-default" data-dismiss="modal" id="btn_cancel2">取消</buttom>
						</div>
						</div>
					</div>
				</div>

	<fieldset>
		<input type="hidden" name="payItemPkid" value="${payItemPkid}" id="payItemPkid">
		<table id="payItemListTable"
			class="table table-striped table-hover table-bordered jf_table" style="text-align: center;">
		</table>
	</fieldset>

	<div class="clearfix"></div>
	<script type="text/javascript" src="${basepath}static/js/myjs/stupayItemList.js"></script>
