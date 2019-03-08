//@ sourceURL=receiptPrint.js
//收据打印页面js
var receiptPrint={};


//日期插件参数设置

laydate.render({
	elem:'#startDate',
	isclear: true, //是否显示清空
	istoday: true, //是否显示今天
	type: 'datetime',
	max: 1,
	format: 'yyyy-MM-dd HH:mm:ss',
	festival: true, //显示节日
	start: 0
});
laydate.render({
	elem:'#endDate',
	isclear: true, //是否显示清空
	istoday: true, //是否显示今天
	type: 'datetime',
	max: 1,
	format: 'yyyy-MM-dd HH:mm:ss',
	festival: true, //显示节日
	start: 0
});
laydate.render({
	elem:'#RIQIqi',
	isclear: true, //是否显示清空
	istoday: true, //是否显示今天
	type: 'datetime',
	min: 0,
	format: 'yyyy-MM-dd HH:mm:ss',
	festival: true, //显示节日
	start: 0
});
laydate.render({
	elem:'#RIQIzhi',
	isclear: true, //是否显示清空
	istoday: true, //是否显示今天
	type: 'datetime',
	min: 0,
	format: 'yyyy-MM-dd HH:mm:ss',
	festival: true, //显示节日
	start: 0
});
/*
 * 此属性决定收据打印的模板
 * CAIJING 财经学院
 * LIGONG  理工学院
 * GUANGDIAN 光电
 */
receiptPrint.fromSchool = $("#COLLEGES_NAME_EN").val();

receiptPrint.studentPkid;
receiptPrint.buttonStatus = function(){
	if(receiptPrint.getIdSelections() != ''){
		$("#payPrintBatchButton").attr("disabled",false);
		$("#printBatchButton").attr("disabled",false);
		$("#payPrintButton").attr("disabled",false);
	}else{
		$("#payPrintBatchButton").attr("disabled","disabled");
		$("#printBatchButton").attr("disabled","disabled");
		$("#payPrintButton").attr("disabled","disabled");
	}
};

//获取选中的行
receiptPrint.getIdSelections=function () {
		var $table = $('#receiptPrintTable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.PKID;
    });
};

//获取选中的行打印状态
receiptPrint.getPRINTFLAG=function () {
		var $table = $('#receiptPrintTable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.PRINTFLAG;
    });
};
//获取选中的行是否发票
receiptPrint.getBILL_TYPE=function () {
		var $table = $('#receiptPrintTable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.BILL_TYPE;
    });
};

$("#pay_style_sj").change(function(){
	var SCHOOL_YEAR_PKID = $("#school_year_sj").val();
	var PAY_TYPE_PKID = $("#pay_style_sj").val();
	var zNodes2;
	var setting2 = {
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "s", "N": "ps" }
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick2,
				onCheck: onCheck2
			}
	};
	$.ajax({
		  type: 'POST',
		  url: _basepath+"receipt/getAllPayItemListList.json",
		  async: false,
		  data: {SCHOOL_YEAR_PKID:SCHOOL_YEAR_PKID,PAY_TYPE_PKID:PAY_TYPE_PKID},
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  zNodes2 = result.payItemListResult;
			  }
			  return false;
		  }
		});
	zNodes2 = eval(zNodes2);
	$.fn.zTree.init($("#treeDemo2"), setting2, zNodes2);
});

$("#school_year_sj").change(function(){
	var SCHOOL_YEAR_PKID = $("#school_year_sj").val();
	var PAY_TYPE_PKID = $("#pay_style_sj").val();
	var zNodes2;
	var setting2 = {
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "s", "N": "ps" }
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick2,
				onCheck: onCheck2
			}
	};
	$.ajax({
		  type: 'POST',
		  url: _basepath+"receipt/getAllPayItemListList.json",
		  async: false,
		  data: {SCHOOL_YEAR_PKID:SCHOOL_YEAR_PKID,PAY_TYPE_PKID:PAY_TYPE_PKID},
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  zNodes2 = result.payItemListResult;
			  }
			  return false;
		  }
		});
	zNodes2 = eval(zNodes2);
	$.fn.zTree.init($("#treeDemo2"), setting2, zNodes2);
});

//获取list表格数据
receiptPrint.getTab=function (url,isRefresh) {
	
	
	var url = _basepath+"receipt/getReceiptlistPage.json";

	$('#receiptPrintTable').bootstrapTable(
					{
						url : url,//数据源
						dataField : "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
						totalField : 'total',
						sortOrder : 'desc',
						striped : true, //表格显示条纹  
						pagination : true, //启动分页  
						pageNumber : 1, //当前第几页  
						pageSize : 200, //每页显示的记录数  
						pageList : [1,50,100,200,300,500,1000,2000], //记录数可选列表  
						search : false, //是否启用查询  
						formatSearch : function() {
							return '查询';
						},//搜索框提示文字
						searchAlign : 'left',
						buttonsAlign : 'left',
						toolbarAlign : 'left',
						searchOnEnterKey : false,//true为按回车触发搜索事件
						showColumns : false, //显示下拉框勾选要显示的列  
						showRefresh : false, //显示刷新按钮  --只是前台刷新，个人觉得没什么用
						minimumCountColumns : 2, //最少允许的列数
						sidePagination : "server", //表示服务端请求  
						totalRows : 0, // server side need to set
						singleSelect : false,
						clickToSelect : true,
						onDblClickRow:function(row){
						},
						//行选
						onClickRow : function(row, tr,flied){
			            	//设置批量按钮是否可点击
			            	receiptPrint.buttonStatus();
				        },
				        //全选
				        onCheckAll:function(rows){
				        	//设置批量按钮是否可点击
			            	receiptPrint.buttonStatus();
				        	
				        },
			            //点击每一个单选框时触发的操作
			            onCheck:function(row){
			            	//设置批量按钮是否可点击
			            	receiptPrint.buttonStatus();
			            },
			            //取消每一个单选框时对应的操作；
			            onUncheck:function(row){
			            	//设置批量按钮是否可点击
			            	receiptPrint.buttonStatus();
			            },
			            //取消每一个单选框时对应的操作；
			            onUncheckAll:function(rows){
			            	//设置批量按钮是否可点击
			            	receiptPrint.buttonStatus();
			            },
						queryParams : function getParams(params) {
							//开始日期
							var startDate = $("#startDate").val();
							var endDate = $("#endDate").val();
							var ordercreateMode = $("#ordercreateModeSelect  option:selected").val();
							var printFlag = $("#printFlagSelect  option:selected").val();
							var INPUT_OUTPUT = $("#INPUT_OUTPUTSelect  option:selected").val();
							var conditions = $('#conditionsSelect').val();
							var PAY_TYPE_PKID = $("#pay_style_sj").val();
							var PAY_MODE = $("#payModeSelect  option:selected").val();
							var SKR=$("#shoukuanrenSelect  option:selected").val();
							
							var BILL_TYPE=$("#BILL_TYPE").val();
							
							var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
								limit : params.limit, //页面大小
								searchText : this.searchText,
								sortName : this.sortName,
								sortOrder : this.sortOrder,
								pageindex : this.pageNumber,
								ordercreateMode:ordercreateMode,
								printFlag:printFlag,
								INPUT_OUTPUT:INPUT_OUTPUT,
								conditions:conditions,
								PAY_TYPE_PKID:PAY_TYPE_PKID,
								startDate:startDate,
						    	endDate:endDate,
						    	PAY_MODE:PAY_MODE,
						    	SKR:SKR,
								BILL_TYPE:BILL_TYPE
							//当前页码
							};
							return temp;
						},
						buttonsAlign : "right",//按钮对齐方式
						selectItemName : 'id',
						toolbar : "#toolbar",
						toolbarAlign : 'left',
						columns : [{
					                checkbox: true,
					                formatter : function(value, row,
											index) {
									}
					            },
								{
									field : 'PKID',// 可不加
									align : "center",
									halign : 'center'
								},
								{
									field : 'XUEHAO',
									title:'学号',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'XINGMING',
									title:'姓名',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'SHENFENZHENGHAO',
									title:'身份证号',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'RUXUENIANFEN',
									title:'入学年份',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'BANXING',
									title:'班型',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'PAY_STYLE_NAME',
									title:'缴费类型',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'ORDERCREATE_MODE',
									title:'缴费方式',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'PAY_MODE',
									title:'支付方式',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										if(value == 'CASH'){
											value = "现金";
										}else if(value == 'CARD'){
											value = "银行卡";
										}else if(value == 'ZFB'){
											value = "支付宝";
										}else if(value == 'WX'){
											value = "微信";
										}else if(value == 'TT'){
											value = "电汇";
										}
										return value;
									}
								},
								
								{
									field : 'INPUT_OUTPUT',
									title:'支付类型',
									align : "center",
									halign : 'center',
									sortable : false
								},
								
								{
									field : 'MONEY',
									title:'缴费金额',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'CJSJ',
									title:'缴费时间',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										return value != ''?new Date(value).format("yyyy-MM-dd hh:mm:ss"):"-";
									}
									
								},
								{
									field : 'SKR',
									title:'收款人',
									align : "center",
									halign : 'center',
									sortable : false
									
								},
								{
									field : 'RECEIPTNO',
									title:'收据号',
									align : "center",
									halign : 'center',
									sortable : false
									
								},
								
								{
									field : 'PRINTFLAG',
									title:'打印状态',
									align : "center",
									halign : 'center',
									sortable : false
								}
						],
					});
    $('#receiptPrintTable').bootstrapTable('hideColumn', 'PKID');//隱藏列
    
   
};
//学生列表弹框
receiptPrint.getStudentPayTab = function(){
	
	var ordercreateMode = $("#ordercreateModeSelect  option:selected").val();
	var printFlag = $("#printFlagSelect  option:selected").val();
	var conditions = $('#conditionsSelect').val();
	var payItem = $('#orgtree2').val();
	var orgtree = $('#orgtree').val();
	
	var url = _basepath+"pay/getStudentQuerylistPage.json";
	$('#studentListTable').bootstrapTable(
					{
						url : url,//数据源
						dataField : "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
						totalField : 'total',
						sortOrder : 'desc',
						striped : true, //表格显示条纹  
						pagination : true, //启动分页  
						pageNumber : 1, //当前第几页  
						pageSize : 20, //每页显示的记录数  
						pageList : [1,5,10,20,30,50,100], //记录数可选列表  
						search : false, //是否启用查询  
						formatSearch : function() {
							return '查询';
						},//搜索框提示文字
						searchAlign : 'left',
						buttonsAlign : 'left',
						toolbarAlign : 'left',
						searchOnEnterKey : false,//true为按回车触发搜索事件
						showColumns : false, //显示下拉框勾选要显示的列  
						showRefresh : false, //显示刷新按钮  --只是前台刷新，个人觉得没什么用
						minimumCountColumns : 2, //最少允许的列数
						sidePagination : "server", //表示服务端请求  
						totalRows : 0, // server side need to set
						singleSelect : false,
						clickToSelect : true,
						onDblClickRow:function(row){
							var studentPkid = row.PKID;
			            	doSearchForOneStudent(studentPkid, status);
						},
						queryParams : function getParams(params) {
							var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
								limit : params.limit, //页面大小
								searchText : this.searchText,
								sortName : this.sortName,
								sortOrder : this.sortOrder,
								pageindex : this.pageNumber,
								ordercreateMode:ordercreateMode,
								printFlag:printFlag,
								conditions:conditions,
								payItem:payItem,
								departmentIdList:orgtree
								
							//当前页码
							};
							return temp;
						},
						buttonsAlign : "right",//按钮对齐方式
						selectItemName : 'id',
						toolbar : "#toolbar",
						toolbarAlign : 'left',
						columns : [
								{
									field : 'PKID',// 可不加
									align : "center",
									halign : 'center'
								},

								{
									field: 'XUEHAO',//可不加  
									title:'学号',
									align : "center",
									halign : 'center'
								},
								{
									field: 'SHENFENZHENGHAO',//可不加  
									title:'身份证',
									align : "center",
									halign : 'center'
								},
								{
									field : 'XINGMING',
									title:'姓名',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'DEPARTMENT_NAME',
									title:'所属组织',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'XINGBIE',
									title:'性别',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'RUXUESHIJIAN',
									title:'入学时间',
									align : "center",
									halign : 'center',
									sortable : false
									
								},
								{
									field : 'ZAIXUEZT',
									title:'在学状态',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										if(value == 'ZX'){
											return '在学';
										}else if(value == 'TX'){
											return '退学';
										}else if(value == 'XX'){
											return '休学';
										}else if(value == 'RW'){
											return '入伍';
										}
									}
									
								},
								{
									field : 'SUSHE',
									title:'宿舍',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'DAOSHI',
									title:'导师',
									align : "center",
									halign : 'center',
									sortable : false
								}
						],
					});
    $('#studentListTable').bootstrapTable('hideColumn', 'PKID');//隱藏列
};
//刷新学生table
var refreshStudentTable = function(conditions, status){
	var url = _basepath+"pay/getStudentQuerylistPage.json";
	 var opt = {
			url :url,
		    silent: true,
		    query:{
		    	"status":status,
		    	"conditions":conditions
		    	
		    }
		  };
	 $("#studentListTable").bootstrapTable('refresh', opt);
};

//查询按钮
$('#btn_search').click(function(){
	/*var status = $("#statusSelect  option:selected").val();
	
	var conditions = $('#conditionsSelect').val();
	
	if(conditions != ''){
		//查询学生信息
		var url=_basepath+"pay/getStudentQuerylist2.json";
		$.post(url,{conditions:conditions},function(result){
			if(result.result == 'success'){
				if(result.stuList.length == 0){
					layer.msg("未查询到该学生！");
				}else if(result.stuList.length == 1){
					
					doSearchForOneStudent(result.stuList[0].PKID);
					return false;
				}else{
					$('#mymoda6').modal({
						keyboard: true
					});
					refreshStudentTable(conditions, status);
				}
				
			}else{
				$('#mymoda6').modal({
					keyboard: true
				});
				refreshStudentTable(conditions, status);
			}
			
		});
	}else{
		//刷新table
		 refreshReceiptPrintTable('');
		
	}*/
	refreshReceiptPrintTable('');
	 return false;
   
});
//选择一个学生查询
var doSearchForOneStudent = function(studentPkid){
	receiptPrint.studentPkid = studentPkid;
	
	var url=_basepath+"pay/getStudentInfoForOfflinePay.json";
	$.post(url,{studentPkid:studentPkid},function(result){
		if(result.result == 'success'){
			if(result.stuInfo == null){
				layer.msg("未查询到该学生！");
				return false;
			}
			//关闭弹框
			$("#btn_query_cancel").click();
			
			//刷新table
			refreshReceiptPrintTable(studentPkid);
		  
		}
		 
	});
};

//刷新table
var refreshReceiptPrintTable = function(studentPkid){
	var startDate=$("#startDate").val();
    var endDate=$("#endDate").val();
    
    var date1 = new Date(Date.parse(startDate));
	var date2 = new Date(Date.parse(endDate));
	if(date1>date2){
		layer.msg("结束时间不能早于开始时间");
		return false;
	}
    
    
	var url = _basepath+"receipt/getReceiptlistPage.json";
 	 var opt = {
			url :url,
		    silent: true,
		    query:{
		    	"studentPkid":studentPkid,
		    	"conditions":$("#conditionsSelect").val(),
		    	"payItem":$("#orgtree2").val(),
		    	"printFlag": $("#printFlagSelect  option:selected").val(),
		    	"ordercreateMode": $("#ordercreateModeSelect  option:selected").val(),
		    	"departmentIdList":$("#orgtree").val(),
		    	"startDate":startDate,
		    	"endDate":endDate
		    	
		    }
		  };
	 $("#receiptPrintTable").bootstrapTable('refresh', opt);
};

//点击批量打印
$("#payPrintBatchButton").click(function(){
	payPrintBatch();
});
$("#payPrintButton").click(function(){
	payPrint();
});

//打印收据（光电）
var payPrint = function(){
	if(receiptPrint.fromSchool == "GUANGDIAN"){
		printBatch3();
	}
};

//批打收据（财经）
var payPrintBatch = function(){
	if(receiptPrint.fromSchool == "CAIJING"){
		
	}else if(receiptPrint.fromSchool == "LIGONG"){
		payPrintBatch2();
	}else if(receiptPrint.fromSchool == "GUANGDIAN"){
		payPrintBatch3();
	}else if(receiptPrint.fromSchool == "QHMY"){
		payPrintBatch4();
	}
	
};

//批打收据（理工）
var payPrintBatch2 = function(){};

//批打发票（光电）
var printBatch3 = function(){};


//打印收据（光电）
var payPrintBatch3 = function(){};


//批打收据（强化美院）
var payPrintBatch4 = function(){
	var detailPkids = receiptPrint.getIdSelections()+"";
	//如果未选择
	if(detailPkids == ''){
		layer.msg("请至少选择一条数据！");
		return false;
	}
	
	$.ajax({
		  type: 'POST',
		  url: _basepath+"receipt/getPayOrderDetail.json",
		  async: false,
		  data: {
			detailPkids:detailPkids,
			fromSchool:receiptPrint.fromSchool,
			INPUT_OUTPUT:'JX'
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  var dataMap = result.dataMap;
				  if(JSON.stringify(dataMap) == "{}"){
					  layer.msg("请至少选择一条缴费记录！"); 
					  return false;
				  }
				  var isEnd = false;
				  var htmlss = '';
				  var totalMoney = 0;
				  var XGR = "";
				  var KAIPIAOREN = "";
				  //循环人
				  var count = 0;//单据数量
				  var recordCount = 0;//每张单据里记录条数
				  var isFirstCount = 0;
				  var RECEIPTNO = "";
				  var paperCount = 0;
				  var RECEIPTNOArray = new Array();
				  var RECEIPTNOS = "";
				  var ORDERCREATE_MODES = "";//收款方式
				  var ORDERCREATE_MODEArray = new Array();
				  var SHOUKUANRENArray = new Array();
				  var SHOUKUANRENS = "";
				  var htmls = "";
				 var createDate = result.sysDay;
				  var createDateArray = createDate.split("-");
				  
				  for(var key in dataMap){
					    var payOrderDetailList = dataMap[key];
					    //循环人拥有的所有记录
					    for (var i = 0; i < payOrderDetailList.length - 1; i++) {

					    	var payItemList = payOrderDetailList[i];
							//表头-------------------------------------------------------------------------------------------------
							if(i == 0 || i % 7  == 0){//4条数据打一张单据;border:1px solid
								count ++;
								totalMoney = 0;
//								count ++;
								htmls += '<div class="box" style="width:179mm;height:83mm";margin:0 auto;position:relative;">';//
								htmls += '<div class="chart-head" style="height:7mm;">'+
											'石家庄强化美术培训学校（收据）'+
											'<div style="position:absolute;right:0;top:0;">No：<span>'+payItemList.RECEIPTNO+'</span></div>'+
										'</div>';
								htmls += '<table style="border-collapse: collapse;width:100%;">'+
												'<tbody>'+
												'<tr style="height:7mm;">'+
													'<td class="bg-gray">姓名</td>'+
													'<td style="width:80px;white-space:nowrap !important;">'+(payItemList.XINGMING == "" || payItemList.XINGMING == null || payItemList.XINGMING == 'null'  || typeof payItemList.XINGMING == "undefined"
														? "&nbsp;" :payItemList.XINGMING)+'</td>'+
													'<td class="bg-gray">学号</td>'+
													'<td style="width:80px;white-space:nowrap !important;">'+(payItemList.XUEHAO == "" || payItemList.XUEHAO == null || payItemList.XUEHAO == 'null'  || typeof payItemList.XUEHAO == "undefined"
														? "&nbsp;" :payItemList.XUEHAO)+'</td>'+
													'<td class="bg-gray">班型</td>'+
													'<td style="width:80px;white-space:nowrap !important;">'+(payItemList.BANXING == "" || payItemList.BANXING == null || payItemList.BANXING == 'null'  || typeof payItemList.BANXING == "undefined"
														? "&nbsp;" :payItemList.BANXING)+'</td>'+
													'<td class="bg-gray">文化课学校</td>'+
													'<td style="width:180px;white-space:nowrap !important;">'+(payItemList.SCHOOLNAME == "" || payItemList.SCHOOLNAME == null || payItemList.SCHOOLNAME == 'null'  || typeof payItemList.SCHOOLNAME == "undefined"
														? "&nbsp;" :payItemList.SCHOOLNAME)+'</td>'+
												'</tr>'+
												'<tr style="height:7mm;">'+
													'<td colspan="6" class="bg-gray">项目</td>'+
													'<td colspan="2" class="bg-gray">金额</td>'+
												'</tr>';
								isFirstCount ++;
							};
						  
							if(payItemList.INPUT_OUTPUT == 'JX'){
								totalMoney += payItemList.MONEY;
							}else{
								totalMoney -= payItemList.MONEY;
							}
							
							XGR = payItemList.XGR;
							KAIPIAOREN = payItemList.KAIPIAOREN;
							
							RECEIPTNOS += payItemList.RECEIPTNO;
							RECEIPTNOS += " |";
							
							if(ORDERCREATE_MODES.indexOf(payItemList.PAY_MODE) < 0){
								ORDERCREATE_MODES += payItemList.PAY_MODE;
								ORDERCREATE_MODES += "+";
							}
							
							if(payItemList.XGR != '' && payItemList.XGR != null && payItemList.XGR != 'null'){
								if(SHOUKUANRENS.indexOf(payItemList.XGR) < 0){
									SHOUKUANRENS += payItemList.XGR;
									SHOUKUANRENS += "/";
								}
							}else{
								SHOUKUANRENS += "";
							}
							//(payItemList.INPUT_OUTPUT == 'XX' ? '-' : '')+
//							var money = ""+payItemList.MONEY +"";
//							var moneyArray = splitMoney(money);
							var money = payItemList.INPUT_OUTPUT == 'JX'? payItemList.MONEY : "-"+payItemList.MONEY;
							//表中-项目记录-------------------------------------------------------------------------------------------------
							htmls += '<tr style="height:6mm;">'+
										'<td colspan="6" style="white-space:nowrap !important;">'+(payItemList.PAY_STYLE_NAME == '' || payItemList.PAY_STYLE_NAME == null || payItemList.PAY_STYLE_NAME == 'null'  || typeof payItemList.PAY_STYLE_NAME == 'undefined'
												? '' :payItemList.PAY_STYLE_NAME)+'</td>'+
										'<td colspan="2">'+money+'</td>'+
									'</tr>';
							recordCount ++;
							
							//表尾-------------------------------------------------------------------------------------------------
							if((i + 1) % 7  == 0){//4条数据打一张单据
								isEnd = true;
//								var totalMoneyArray = splitMoney(""+totalMoney+"");
								htmls += '<tr style="height:7mm;">'+
											'<td colspan="2">合计</td>'+
											'<td colspan="2" style="white-space:nowrap !important;">'+totalMoney.toFixed(2)+'</td>'+
											'<td colspan="2">大写金额</td>'+
											'<td colspan="2" style="white-space:nowrap !important;">'+smalltoBIG(totalMoney)+'</td>'+
										'</tr>';
								htmls += '</table>'+
								'<div style="height:7mm;">'+
								'<ul>'+
									'<li class="huoBi">'+
										'开票人<span>&nbsp;&nbsp;'+KAIPIAOREN+'</span>'+
									'</li>'+
									'<li class="huoBi" style="margin-left: 32%;margin-right:145px;">'+
										'学生签字<span></span>'+
									'</li>'+
									'<li class="huoBi3">'+
									'开票时间&nbsp;'+curDateTime()+'<span></span>'+
									'</li>'+
									'<li class="clearfix"></li>'+
								'</ul>'+
							'</div>';
								htmls += '</div>';
								totalMoney = 0;
								recordCount = 0;
								
								RECEIPTNOArray[paperCount] = RECEIPTNOS;
								RECEIPTNOS = "";

								ORDERCREATE_MODEArray[paperCount] = ORDERCREATE_MODES;
								ORDERCREATE_MODES = "";
								
								SHOUKUANRENArray[paperCount] = SHOUKUANRENS;
								SHOUKUANRENS = "";
								
								paperCount ++;
								
								//一张纸打3个一样的收据
//								  htmlss += (htmls + htmls + htmls);
								  
							}else{
								isEnd = false;
								
							}
							
					  }
					  //表尾-------------------------------------------------------------------------------------------------
					  if(!isEnd){
						  //补空行
						  for (var i = 0; i < (7 - recordCount); i++) {
							  htmls += '<tr style="height:6mm;">'+
											'<td colspan="6"></td>'+
											'<td colspan="2"></td>'+
										'</tr>';
						  }
//						  var totalMoneyArray = splitMoney(""+totalMoney+"");
						  htmls += '<tr style="height:7mm;">'+
							'<td colspan="2">合计</td>'+
							'<td colspan="2">'+totalMoney.toFixed(2)+'</td>'+
							'<td colspan="2">大写金额</td>'+
							'<td colspan="2">'+smalltoBIG(totalMoney)+'</td>'+
						'</tr>';
						  htmls += '</table>'+
							'<div style="height:7mm;">'+
								'<ul>'+
									'<li class="huoBi">'+
										'开票人<span>&nbsp;&nbsp;'+KAIPIAOREN+'</span>'+
									'</li>'+
									'<li class="huoBi" style="margin-left: 32%;margin-right:145px;">'+
										'学生签字<span></span>'+
									'</li>'+
									'<li class="huoBi3">'+
									'开票时间&nbsp;'+curDateTime()+'<span></span>'+
									'</li>'+
									'<li class="clearfix"></li>'+
								'</ul>'+
							'</div>';
						  htmls += '</div>';
						  
						  totalMoney = 0;
						  recordCount = 0;
						  XGR = "";
						  
						  RECEIPTNOArray[paperCount] = RECEIPTNOS;
						  RECEIPTNOS = "";
						  
						  ORDERCREATE_MODEArray[paperCount] = ORDERCREATE_MODES;
						  ORDERCREATE_MODES = "";
						  
						  SHOUKUANRENArray[paperCount] = SHOUKUANRENS;
						  SHOUKUANRENS = "";
							
						  paperCount ++;
						//一张纸打3个一样的收据
//						  htmlss += (htmls + htmls + htmls);
//						  
//						  htmls = "";
					  }
					  
					  
					  
				}
				$("#printAreaWAIGUOYUshouju2").html(htmls);
				
				//将每张票的收据号添加到票上
				/*for(var i = 0; i < RECEIPTNOArray.length; i++){
					$("#paperRECEIPTNO"+i).html(RECEIPTNOArray[i].substr(0, RECEIPTNOArray[i].length - 1));
				}*/
				/*for(var i = 0; i < ORDERCREATE_MODEArray.length; i++){
					$("#paperORDERCREATE_MODE"+i).html(ORDERCREATE_MODEArray[i].substr(0, ORDERCREATE_MODEArray[i].length - 1));
				}*/
				//将每张票的收款人添加到票上
				/*for(var i = 0; i < SHOUKUANRENArray.length; i++){
					var SHOUKUANRENSS = SHOUKUANRENArray[i].substr(0, SHOUKUANRENArray[i].length - 1);
					var SHOUKUANRENSSArray = SHOUKUANRENSS.split("/");
					if(SHOUKUANRENSSArray.length > 2){
						SHOUKUANRENSS = SHOUKUANRENSSArray[0]+"/"+SHOUKUANRENSSArray[1];
					}
					$("#paperSHOUKUANREN"+i).html(SHOUKUANRENSS);
				}*/
				
				//打印区域
				$("#printAreaWAIGUOYUshouju").printArea();
				
				//刷新table
				refreshReceiptPrintTable(receiptPrint.studentPkid);
				 
			  }else{
				  layer.msg("打印失败，请重试!");
			  }
			  return false;
		  }
		});
	//批打按钮显示
	receiptPrint.buttonStatus();
};



function curDateTime(){
	var d = new Date(); 
	var year = d.getFullYear(); 
	var month = d.getMonth()+1; 
	var date = d.getDate(); 
	var day = d.getDay(); 
	/*var hours = d.getHours(); 
	var minutes = d.getMinutes(); 
	var seconds = d.getSeconds(); 
	var ms = d.getMilliseconds(); */
	var curDateTime= year;
	if(month>9)
	curDateTime = curDateTime +"-"+month;
	else
	curDateTime = curDateTime +"-0"+month;
	if(date>9)
	curDateTime = curDateTime +"-"+date;
	else
	curDateTime = curDateTime +"-0"+date;
	/*if(hours>9)
	curDateTime = curDateTime +""+hours;
	else
	curDateTime = curDateTime +"0"+hours;
	if(minutes>9)
	curDateTime = curDateTime +":"+minutes;
	else
	curDateTime = curDateTime +":0"+minutes;
	if(seconds>9)
	curDateTime = curDateTime +":"+seconds;
	else
	curDateTime = curDateTime +":0"+seconds;*/
	return curDateTime; 
	}

/*
 * 拆分金额
 */
var splitMoney = function(money){
	var wan = "";
	var qian = "";
	var bai = "";
	var shi = "";
	var yuan = "";
	var jiao = "";
	var fen = "";
	
	var index = money.indexOf('.');
	if(index > 0){//有小数点 110.01
		var moneyArray = money.split(".");
		if(moneyArray[0] == ""){
			moneyArray[0] = "0";
		}
		if(Number(moneyArray[0]) > 0){//1.01
			var moneyTemp = money.replace(".", "");
			var moneyResult = moneyTemp.split("");
			if(moneyResult.length == 2){
				yuan = moneyResult[0];
				jiao = moneyResult[1];
				fen = "0";
			}else if(moneyResult.length == 3){
				yuan = moneyResult[0];
				jiao = moneyResult[1];
				fen = moneyResult[2];
			}else if(moneyResult.length == 4){
				shi = moneyResult[0];
				yuan = moneyResult[1];
				jiao = moneyResult[2];
				fen = moneyResult[3];
			}else if(moneyResult.length == 5){
				bai = moneyResult[0];
				shi = moneyResult[1];
				yuan = moneyResult[2];
				jiao = moneyResult[3];
				fen = moneyResult[4];
			}else if(moneyResult.length == 6){
				qian = moneyResult[0];
				bai = moneyResult[1];
				shi = moneyResult[2];
				yuan = moneyResult[3];
				jiao = moneyResult[4];
				fen = moneyResult[5];
			}else if(moneyResult.length >= 7){
				wan = moneyResult[0];
				qian = moneyResult[1];
				bai = moneyResult[2];
				shi = moneyResult[3];
				yuan = moneyResult[4];
				jiao = moneyResult[5];
				fen = moneyResult[6];
			}
		}else{//0.01
			var moneyResult = Number(moneyArray[1]);
			if(moneyResult.length > 1){
				moneyResult = ""+moneyResult+"";
				moneyResult = moneyResult.split("");
				jiao = moneyResult[0];
				fen = moneyResult[1];
			}else{
				fen = moneyResult;
			}
		}
	}else{//没有小数点 110
		jiao = "0";
		fen = "0";
		var moneyResult = money.split("");
		if(moneyResult.length == 1){
			yuan = moneyResult[0];
		}else if(moneyResult.length == 2){
			shi = moneyResult[0];
			yuan = moneyResult[1];
		}else if(moneyResult.length == 3){
			bai = moneyResult[0];
			shi = moneyResult[1];
			yuan = moneyResult[2];
		}else if(moneyResult.length == 4){
			qian = moneyResult[0];
			bai = moneyResult[1];
			shi = moneyResult[2];
			yuan = moneyResult[3];
		}else if(moneyResult.length >= 5){
			wan = moneyResult[0];
			qian = moneyResult[1];
			bai = moneyResult[2];
			shi = moneyResult[3];
			yuan = moneyResult[4];
		}
	}
	return new Array(wan,qian,bai,shi,yuan,jiao,fen);
	
};

var payPrintBatch_demo = function(){};

Date.prototype.Format = function (fmt) { //author: meizz 
 var o = {
     "M+": this.getMonth() + 1, //月份 
     "d+": this.getDate(), //日 
     "h+": this.getHours(), //小时 
     "m+": this.getMinutes(), //分 
     "s+": this.getSeconds(), //秒 
     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
     "S": this.getMilliseconds() //毫秒 
 };
 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
 for (var k in o)
 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
 return fmt;
};

$(function(){
	//隐藏专业树
	$("#menuContent").css("display","none");
	$("#menuContent2").css("display","none");
	receiptPrint.getTab();
	receiptPrint.getStudentPayTab();
	

	$('#conditionsSelect').keydown(function(e){if(e.keyCode==13){
	    $('#btn_search').triggerHandler('click');
	}
	});

});
