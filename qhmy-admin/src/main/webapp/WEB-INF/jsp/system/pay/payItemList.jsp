<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>


		<link href="${basepath}static/js/layer/skin/default/layer.css" rel="stylesheet">
		<script src="${basepath}static/js/layer/layer.js"></script>
		<link href="${basepath}static/js/layer/skin/default/layer.css" rel="stylesheet">
	    <script src="${basepath}static/js/bootstrap-dialog.min.js"></script>
	    <!-- ztree所需css以及js -->
		 <link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css">
		 		    <!--  table横纵向滚动条 -->
	   <link rel="stylesheet" href="${basepath}static/css/bootstrap-table.css">
	   
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script>
	   <%--  <script type="text/javascript" src="${basepath}static/js/myjs/stu_ztree.js"></script>
	    <script type="text/javascript" src="${basepath}static/js/myjs/jieshaoren_ztree.js"></script> --%>
	    <script type="text/javascript">		
		var colStr="${colStr}";
		</script>
		<style type="text/css">
			html { overflow-x:hidden; }
			.jf_mdContent{
				max-height:600px  !important;
				overflow: auto !important;
			}
			.jf_ZtBox{
				left:0 !important;
			}
			.stream-browse-files {
		    	margin-bottom: 15px !important;
		    }
		</style>
		<div class="form-inline" style="margin-bottom:15px;">
			<select name="" class="form-control jf_FltConditions" style="width:28%;" id="statusselect">
					<option  value="">缴费状态</option>
					<c:forEach items="${statusMap}" var="status">
						<option value="${status.value}">${status.key} </option>
					</c:forEach>
			</select>
			<select name="" id="mdruxuenianfen" class="form-control jf_FltConditions"  placeholder="入学年份">
			<option value="">入学年份</option>
			<c:forEach items="${ruxuenianfen}" var="rxnf" varStatus="rxnft" >
				<option value="${rxnf.DICTIONARIES_ID}">${rxnf.NAME}</option>
			</c:forEach>
			</select>
			
			<select name="" id="mdbanxing" class="form-control jf_FltConditions"  placeholder="班型">
			<option value="">班型</option>
			<c:forEach items="${banxing}" var="bx" varStatus="bxt" >
				<option value="${bx.DICTIONARIES_ID}">${bx.NAME}</option>
			</c:forEach>
			</select>
			<!-- z-tree结构 -->
			<!-- <div class="form-group">
                 <div class="zTreeDemoBackground left">
					<ul class="list" style="margin-bottom:0;">
						<li class="jf_ZtLaber title"><input class="form-control" id="citySel" type="text" readonly value="" onclick="showMenu();" placeholder="院系专业" style="width:183px;"/>
							<div id="menuContent" class="jf_ZtBox menuContent" style="left: 0px; top: 34px; display: none;width:300px;">
								<ul id="treeDemo" class="jf_ZtList ztree"></ul>
							</div>
						</li>
					</ul>
				 </div>
				 <div style="display:none;">
			        用来存储 树节点的id
				    <input type="text" name="tree" id="orgtree" class="form-control"/>
			     </div>
			</div> -->
						
										
			
			<input type="text" class="form-control jf_FltConditions" style="width:38%;" placeholder="身份证/学号/姓名" name="PAYITEM" id="conditionsselect"/>
			
		</div> 
		<button class="btn btn-danger sfzkzqsousuobtn" id="btn_search"><span class="fa fa-search"></span> 查询</button>
		<button class="btn btn-danger" id="btn_msg"><span class="fa fa-hand-o-down"></span> 一键催缴</button>
		<!-- <button class="btn btn-danger" id="btn_search"><span class="fa fa-search"></span> 查询</button> -->
		<%-- <c:if test="${pd.status==0 or pd.status==1}">
				<c:if test="${SESSION_MENU_BUTTONS.jfgl_tjjfmd == '1'  and pd.IS_INCLUDE_CHILD !='是'}">
					<button class="btn btn-danger" id="btn_import_add"><span class="fa fa-plus"></span> 添加缴费名单</button>
				</c:if>
		</c:if> --%>
		<%-- <c:if test="${pd.status==0 or pd.status==1}">
		<c:if test="${pd.IS_INCLUDE_CHILD!= '是'}">
			<c:if test="${SESSION_MENU_BUTTONS.jfgl_drjfmd == '1' }">
				<button class="btn btn-danger" id="btn_import_list"><span class="fa fa-sign-in"></span> 导入缴费名单</button>
			</c:if>
		</c:if>
		</c:if> --%>
		<button class="btn btn-danger" id="delete_list"><span class="fa fa-download"></span> 删除名单</button>
		<c:if test="${pd.JFMDFS!=1}">
		<button class="btn btn-danger" id="add_list"><span class="fa fa-download"></span> 添加缴费名单</button>
		</c:if>
		<c:if test="${pd.JFMDFS!=2}">
		<button class="btn btn-danger" id="btn_import_list"><span class="fa fa-sign-in"></span> 导入缴费名单</button>
		</c:if>	
		<button class="btn btn-danger" id="btn_import_already_list"><span class="fa fa-download"></span> 导入已缴名单</button>
		<%-- <c:if test="${pd.status==1 or pd.status==2}">
		<c:if test="${pd.IS_INCLUDE_CHILD!= '是'}">
			<c:if test="${SESSION_MENU_BUTTONS.jfgl_dryjmd == '1' }">
				<button class="btn btn-danger" id="btn_import_already_list"><span class="fa fa-download"></span> 导入已缴名单</button>
			</c:if>
		</c:if>	
		</c:if> --%>
		<%-- <c:if test="${pd.status==0 or pd.status==1}">
			<c:if test="${SESSION_MENU_BUTTONS.jfgl_drjfmd == '1' }">
				<button class="btn btn-danger" id="btn_import_jianmian"><span class="fa fa-sign-in"></span>导入减免</button>
			</c:if>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.jfgl_drdk == '1' }">
			<button class="btn btn-danger" id="btn_import_daikuan"><span class="fa fa-sign-in"></span>导入贷款</button>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.jfgl_drtzys == '1' }">
			<button class="btn btn-danger" id="btn_import_tzys"><span class="fa fa-sign-in"></span>导入调整应收</button>
		</c:if> --%>
		<%-- <c:if test="${SESSION_MENU_BUTTONS.jfgl_pljm == '1' }">
			<button class="btn btn-danger" id="btn_import_pljm"><span class="fa fa-pencil"></span>批量减免</button>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.jfgl_pldk == '1' }">
			<button class="btn btn-danger" id="btn_import_pldk"><span class="fa fa-pencil"></span>批量贷款</button>
		</c:if> --%>
		<%-- <c:if test="${SESSION_MENU_BUTTONS.jfgl_pltzys == '1' }">
			<button class="btn btn-danger" id="btn_import_pltzys"><span class="fa fa-pencil"></span>批量调整应收</button>
		</c:if> --%>
		<button class="btn btn-danger" id="btn_export"><span class="fa fa-upload"></span> 导出</button>
		<button class="btn btn-danger" id="btn_reply"><span class="fa fa-reply"></span> 返回</button>
		
		<div>
		<input id ="SESSION_MENU_BUTTONS" value="${SESSION_MENU_BUTTONS }" style="display:none;">
		<!--待缴学生名单-->
		<div id="mymoda6" class="modal fade">
			<div class="modal-dialog modal-lg" style="width:70%">
						<div class="modal-content">
							<div class="modal-header jf_mod-header">
								<div class="close" data-dismiss="modal">&times;</div>
								<b class="jf_HeaderText">未导学生名单</b>
							</div>
							<div style="padding:10px 8px 0px 20px;">
								<input type="text" class="form-control jf_FltConditions sfzkzqsousuo" style="width:38%;display:inline-block;" placeholder="身份证/学号/姓名" name="studentConditionSelect" id="studentConditionSelect"/>
								<button class="btn btn-danger" id="btn_search_student"><span class="fa fa-search"></span> 查询</button>
								
							</div> 
							<div class="modal-body" style="padding:0 20px 20px 20px;">
								<table id="studentListTable"
									class="table table-striped table-hover table-bordered jf_table" style="text-align: center;">
								</table>
							</div>
							<div class="modal-footer">
								<button class="btn btn-danger" id="btn_save_add" disabled="disabled">添加</button>
								<button class="btn btn-default" data-dismiss="modal" id="btn_cancel3">取消</button>
							</div>
		
				</div>
			</div>
		</div>
			<!--缴费名单-->
	<div id="mymoda5" class="modal fade">
		<div class="modal-dialog modal-lg" style="width:60%">
					<div class="modal-content">
						<div class="modal-header jf_mod-header">
							<div class="close" data-dismiss="modal">&times;</div>
							<b class="jf_HeaderText">缴费记录</b>
						</div>
					<div class="jf_mdContent" style="padding:20px;">
						<!-- <table class="table bg-warning" style="margin-bottom: 30px;">
			<tr style="height: 40px;">
				<td></td>
				<td class="jf_DetailsTd" id="payItemObj"></td>
				<td class="jf_DetailsTd" id="costObj"></td>
				<td class="jf_DetailsTd" id="discountScopeObj"></td>
				<td class="jf_DetailsTd" id="discountMoneyObj"></td>
				<td class="jf_DetailsTd"><span class="label label-success" id="statusObj">已完成</span></td>
			</tr>
			</table> -->
		
						<!-- <div class="modal-header jf_mod-header">
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
		
		
		
		
		
		
					<!--费用变动记录-->
	<div id="mymodalog" class="modal fade">
		<div class="modal-dialog modal-lg" style="width:60%">
					<div class="modal-content">
						<div class="modal-header jf_mod-header">
							<div class="close" data-dismiss="modal">&times;</div>
							<b class="jf_HeaderText">费用变动记录</b>
						</div>
					<div class="jf_mdContent" style="padding:20px;">
			<table class="table table-striped table-hover table-bordered jf_table" style="text-align: center;" id="payRecordListLOG">
			</table>
		</div>
		</div>
		</div>
		</div>
		
		
		
		<!--缴费记录-->
	<div id="mymodajfjl" class="modal fade">
		<div class="modal-dialog modal-lg" style="width:60%">
					<div class="modal-content">
						<div class="modal-header jf_mod-header">
							<div class="close" data-dismiss="modal">&times;</div>
							<b class="jf_HeaderText">缴费记录</b>
						</div>
					<div class="jf_mdContent" style="padding:20px;">
			<table class="table table-striped table-hover table-bordered jf_table" style="text-align: center;" id="payRecordListJFJL">
			</table>
		</div>
		</div>
		</div>
		</div>
		
		
		
		
		
		
		
		
		
		
			<div id="mymodal2" class="modal fade" >
			<input type="hidden" name="yspkid" id="yspkid" value="" />
				<div class="modal-dialog">
					<form action="" class="form-horizontal">
						<div class="modal-content" >
							<div class="modal-header jf_mod-header">
								<div class="close" data-dismiss="modal">&times;</div>
								<b class="jf_HeaderText">调整应收金额</b>
							</div>
							<div class="modal-body" >
								<div class="form-group">
									<label for="" class="col-xs-3 control-label">原应收金额</label>
									<div class="col-xs-7">
										<input readonly="readonly" type="text" class="form-control" value="" id="AMOUNTRECEIVABLE" maxlength="255"/>
									</div>
								</div>
								<div class="form-group">
									<label for="" class="col-xs-3 control-label jf_xing">调整后应收</label>
									<div class="col-xs-7">
										<input type="number" class="form-control" value="" id="TZHMONEY" onkeyup="this.value=this.value.toString().match(/^\d+(?:\.\d{0,2})?/)"/>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button class="btn btn-danger" id="btn_save">保存</button>
								<button class="btn btn-default" data-dismiss="modal" id="btn_cancel2">取消</buttom>
							</div>
							</div>
						</form>		
					</div>
				</div>
				<div id="mymodal11" class="modal fade" >
				<div class="modal-dialog">
					<form action="" class="form-horizontal">
						<div class="modal-content" >
							<div class="modal-header jf_mod-header">
								<div class="close" data-dismiss="modal">&times;</div>
								<b class="jf_HeaderText">批量调整应收</b>
							</div>
							<div class="modal-body">
									<div class="form-group">
										<label for="" class="col-xs-2 control-label">原费用</label>
										<div class="col-xs-9">
											<table class="table table-bordered text-center " id="table11">
											      
											</table>
										</div>
									</div>
									<div class="form-group">
										<label for="" class="col-xs-2 control-label">调整后</label>
										<div class="col-xs-9" style="margin-top:4px;">
											<input type="hidden" placeholder="请输入内容" name="pkid" id="pkid11" />
											费用&nbsp;&nbsp;<input class="form-control jf_LiInput" type="number" placeholder="" name="discount" id="cost_new11" style="display: inline;margin-left:34px;"/><br/><br/>
											<span style="padding-top:10px;">
											优惠方式&nbsp;&nbsp;
											<c:forEach items="${discountModeMap}" var="mode" varStatus="status">
												<input  type="radio" name="discountMode11" value="${mode.value}" id="rd${mode.value}">${mode.key}&nbsp;&nbsp;
												<c:if test="${mode.value == 1}">
	   												<input class="form-control jf_LiInput1" type="text" placeholder="" id="discount_zhe11" style="display: inline;" disabled="disabled"  readonly="readonly"  maxlength="1" onkeyup="value=value.replace(/[^\d.]/g,'')"/> 折&nbsp;&nbsp;
												</c:if>
												<c:if test="${mode.value == 2}">
	   												<input class="form-control jf_LiInput1" type="text" placeholder="" id="discount_jian11" style="display: inline;" disabled="disabled"  readonly="readonly" onkeyup="value=value.replace(/[^\d.]/g,'')"/> 元&nbsp;&nbsp;
												</c:if>
											</c:forEach>
											</span>
											<br/><br/>
											<span style="margin-top:10px;">
											应收金额&nbsp;&nbsp;<input class="form-control jf_LiInput" type="number" placeholder="" name="amountreceivable" id="amountreceivable_new11"  disabled="disabled"  readonly="readonly" style="display: inline;"/>
											</span>
											<br/><br/>
											<span style="margin-top:10px;">
											备注&nbsp;&nbsp;<input class="form-control jf_LiInput" type="text" placeholder="" name="beizhu" id="beizhu" style="display: inline-block;width:300px !important;"/><br/>
											</span>
										</div>
									</div>
							</div>
							<div class="modal-footer">
								<button class="btn btn-danger" id="btn_save11">保存</button>
								<button class="btn btn-default" data-dismiss="modal" id="btn_cancel11">取消</buttom>
							</div>
							</div>
						</form>		
					</div>
				</div>
				<input type="hidden" name="payItemPkid12" value="" id="payItemPkid12">
				<input type="hidden" name="stuPkids12" value="" id="stuPkids12">
				<div id="mymodal12" class="modal fade" >
					<div class="modal-dialog">
					<form action="" class="form-horizontal">
						<div class="modal-content">
							<div class="modal-header jf_mod-header">
								<div class="close" data-dismiss="modal">&times;</div>
								<b class="jf_HeaderText">批量贷款/缓交登记</b>
							</div>
							<div class="modal-body" >
								<div class="form-group">
									<label for="" class="col-xs-2 control-label">原费用</label>
									<div class="col-xs-9">
										<table class="table table-bordered text-center " id="table12">
										      
										</table>
									</div>
								</div>
								<div class="form-group">
									<label for="" class="col-xs-3 control-label jf_xing">贷款/缓交金额</label>
									<div class="col-xs-7">
										<input type="number" class="form-control" value="" id="DKMONEY12" maxlength="255" min="0.01" max="99999.99" step="0.01"/>
									</div>
								</div>
								<div class="form-group">
									<label for="" class="col-xs-3 control-label">备注</label>
									<div class="col-xs-7">
										<input type="text" class="form-control" id="beizhu12" />
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button class="btn btn-danger" id="btn_pldk">保存</button>
								<button class="btn btn-default" data-dismiss="modal" id="btn_cancel12">取消</buttom>
							</div>
						</div>
						</form>
					</div>
				</div>
				<input type="hidden" name="SHENFENZHENGHAOS" value="" id="SHENFENZHENGHAOS">
				<input type="hidden" name="FEIYONG13" value="" id="FEIYONG13">
				<div id="mymodal13" class="modal fade" >
					<div class="modal-dialog">
					<form action="" class="form-horizontal">
						<div class="modal-content">
							<div class="modal-header jf_mod-header">
								<div class="close" data-dismiss="modal">&times;</div>
								<b class="jf_HeaderText">减免登记</b>
							</div>
							<div class="modal-body" >
								<div class="form-group">
									<label for="" class="col-xs-2 control-label">原费用</label>
									<div class="col-xs-9">
										<table class="table table-bordered text-center " id="table13">
										      
										</table>
									</div>
								</div>
								<div class="form-group">
									<label for="" class="col-xs-3 control-label jf_xing">减免金额</label>
									<div class="col-xs-7">
										<input type="number" class="form-control" value="" id="JMMONEY13" maxlength="255" min="0.01" max="99999.99" step="0.01"/>
									</div>
								</div>
								<div class="form-group">
									<label for="" class="col-xs-3 control-label">备注</label>
									<div class="col-xs-7">
										<input type="text" class="form-control" id="beizhu13" />
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button class="btn btn-danger" id="btn_pljm">保存</button>
								<button class="btn btn-default" data-dismiss="modal" id="btn_cancel13">取消</buttom>
							</div>
						</div>
						</form>
					</div>
				</div>
				<input type="hidden" name="STUDENT_PKID" value="" id="STUDENT_PKID">
				<div id="mymodal7" class="modal fade" >
					<div class="modal-dialog">
					<form action="" class="form-horizontal">
						<div class="modal-content">
							<div class="modal-header jf_mod-header">
								<div class="close" data-dismiss="modal">&times;</div>
								<b class="jf_HeaderText">贷款/缓交登记</b>
							</div>
							<div class="modal-body" >
								<div class="form-group">
									<label for="" class="col-xs-3 control-label">欠费金额</label>
									<div class="col-xs-7">
										<input type="text" class="form-control" value="" readonly="readonly" id="QFMONEY1" maxlength="255"/>
									</div>
								</div>
								<div class="form-group">
									<label for="" class="col-xs-3 control-label jf_xing">贷款/缓交金额</label>
									<div class="col-xs-7">
										<input type="number" class="form-control" value="" id="DKMONEY" maxlength="255" min="0.01" max="99999.99" step="0.01"/>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button class="btn btn-danger" id="btn_dkhjdj">保存</button>
								<button class="btn btn-default" data-dismiss="modal" id="btn_cancel7">取消</buttom>
							</div>
						</div>
						</form>
					</div>
				</div>
				<input type="hidden" name="SHENFENZHENGHAO" value="" id="SHENFENZHENGHAO">
				<input type="hidden" name="FEIYONG" value="" id="FEIYONG">
				<div id="mymodal8" class="modal fade" >
				<input type="hidden" name="xzpkid" id="xzpkid" value="" />
					<div class="modal-dialog">
					<form action="" class="form-horizontal">
						<div class="modal-content">
							<div class="modal-header jf_mod-header">
								<div class="close" data-dismiss="modal">&times;</div>
								<b class="jf_HeaderText">减免登记</b>
							</div>
							<div class="modal-body" >
								<div class="form-group">
									<label for="" class="col-xs-3 control-label">原应收金额</label>
									<div class="col-xs-7">
										<input readonly="readonly" type="text" class="form-control" value="" id="jmdjAMOUNTRECEIVABLE" maxlength="255"/>
									</div>
								</div>
								<div class="form-group">
									<label for="" class="col-xs-3 control-label jf_xing">优惠金额</label>
									<div class="col-xs-7">
										<input type="text"  class="form-control" value="" id="jmdjYHJE" onkeyup="this.value=this.value.toString().match(/^\d+(?:\.\d{0,2})?/)"/>
									</div>
								</div>
								<div class="form-group">
									<label for="" class="col-xs-3 control-label jf_xing">优惠说明</label>
									<div class="col-xs-7">
										<input type="text"  class="form-control" value="" id="jmdjYHSM" />
									</div>
								</div>
								<div class="form-group">
									<label for="" class="col-xs-3 control-label jf_xing">经办人</label>
									<div class="col-xs-7">
										<input type="text"  class="form-control" value="" id="jmdjJBR" />
									</div>
								</div>
								<div class="form-group">
									<label for="" class="col-xs-3 control-label jf_xing">应收金额</label>
									<div class="col-xs-7">
										<input type="text" readonly="readonly" class="form-control" value="" id="jmdjTZHMONEY" />
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button class="btn btn-danger" id="btn_jmdj">保存</button>
								<button class="btn btn-default" data-dismiss="modal" id="btn_cancel8">取消</buttom>
							</div>
						</div>
						</form>
					</div>
				</div>
			</div>		
	<fieldset>
		<input type="hidden" name="payItemPkid" value="${payItemPkid}" id="payItemPkid">
		<table class="table"  id="payItemListTable" >
		</table>
	</fieldset>

		<div class="clearfix"></div>
		<OBJECT id=SynCardOcx1 style="WIDTH: 0px; HEIGHT: 0px"
		codeBase=${basepath}static/js/myjs/SynCardOcx.CAB#version=1,0,0,1
		classid=clsid:4B3CB088-9A00-4D24-87AA-F65C58531039></OBJECT>
	<script type="text/javascript" src="${basepath}static/js/myjs/sfzsousuo.js"></script>
	<script type="text/javascript" src="${basepath}static/js/myjs/payItemList.js"></script>
    <script type="text/javascript">
			var _basepath = "${basepath}";
			var payitempkid = "${pd.payItemPkid}";
			var payitem="${pd.payitem}"
				
    </script>