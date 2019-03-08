
//@ sourceURL=paymanage.js
//缴费管理页面js
var pil={};
//编辑保存
pil.save_edit=function(){
	
	
};

//获取选中的行
pil.getIdSelections=function () {
		var $table = $('#payItemListTable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.PKID;
    });
};
pil.buttonStatuss = function(){
	var ids = pil.getIdSelections()+"";
	if(ids != ''){
		$("#btn_export").attr("disabled",false);
	}else{
		$("#btn_export").attr("disabled",false);
	}
}

$("#pay_style_md").change(function(){
	var SCHOOL_YEAR_PKID = $("#school_year_md").val();
	var PAY_TYPE_PKID = $("#pay_style_md").val();
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

$("#school_year_md").change(function(){
	var SCHOOL_YEAR_PKID = $("#school_year_md").val();
	var PAY_TYPE_PKID = $("#pay_style_md").val();
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
pil.getTab=function (url,isRefresh) {
	
	// 引例四
	var startusing = $("#startusingselect  option:selected").val();
	var url = _basepath+"pay/payItemListTable.json";

	$('#payItemListTable').bootstrapTable(
					{
						url : url,//数据源
						dataField : "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
						totalField : 'total',
						method : 'post',
						contentType:"application/x-www-form-urlencoded; charset=UTF-8",
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
						showColumns : true, //显示下拉框勾选要显示的列  
						showRefresh : false, //显示刷新按钮  --只是前台刷新，个人觉得没什么用
						minimumCountColumns : 2, //最少允许的列数
						sidePagination : "server", //表示服务端请求  
						totalRows : 0, // server side need to set
						singleSelect : false,
						clickToSelect : true,
						onColumnSwitch: function(field,checked){

							var resultColumnList = $('#payItemListTable').bootstrapTable('getVisibleColumns');
							var resultColumns = "";		
							$.each(resultColumnList, function(i, column) {
								if(column.field=='0'/*||column.field=='opt'*/){
									return;
								}else{
									resultColumns+=column.field+",";
								}									
					     	});
							$.post("stuinfo/updateShowCols.json",{table_show_cols:resultColumns,table_name:'T_JIAOFEIMINGDAN'},function(data){
								if(data.result=="success"){
//									layer.msg("保存成功!");
								}
							})
						},
						onDblClickRow:function(row){
						},
						//行选
						onClickRow : function(row, tr,flied){
			            	//设置批量按钮是否可点击
			            	pil.buttonStatuss();
				        },
				        //全选
				        onCheckAll:function(rows){
				        	//设置批量按钮是否可点击
			            	pil.buttonStatuss();
				        	
				        },
			            //点击每一个单选框时触发的操作
			            onCheck:function(row){
			            	//设置批量按钮是否可点击
			            	pil.buttonStatuss();
			            },
			            //取消每一个单选框时对应的操作；
			            onUncheck:function(row){
			            	//设置批量按钮是否可点击
			            	pil.buttonStatuss();
//			            	alert(pil.studentPkids)
			            },
			            //取消每一个单选框时对应的操作；
			            onUncheckAll:function(rows){
			            	//设置批量按钮是否可点击
			            	pil.buttonStatuss();
			            },
						queryParams : function getParams(params) {
							//项目pkid 缴费名单搜索功能 add by kangcl
							var payitempkid=$("#orgtree2").val();
							//查询条件-缴费状态
							var status = $("#statusselect  option:selected").val();
							//查询条件-入学年份
							var grade = $("#gradeselect  option:selected").val();
							//查询条件-院校专业
							var DEPARTMENT_PKID = $("#orgtree").val();
							//查询条件-姓名/学号/身份证号
							var conditions = $("#conditionsselect").val();
							
							var PAY_TYPE_PKID = $("#pay_style_md").val();
							var SCHOOL_YEAR_PKID = $("#school_year_md").val();
							var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
								limit : params.limit, //页面大小
								searchText : this.searchText,
								sortName : this.sortName,
								sortOrder : this.sortOrder,
								pageindex : this.pageNumber,
								status:status,
						    	grade:grade,
						    	DEPARTMENT_PKID:DEPARTMENT_PKID,
						    	conditions:conditions,
						    	payItemPkid:payitempkid,
						    	PAY_TYPE_PKID :PAY_TYPE_PKID,
						    	SCHOOL_YEAR_PKID : SCHOOL_YEAR_PKID,
						    	showStatus:'12'
								
							//当前页码
							};
							return temp;
						},
						buttonsAlign : "right",//按钮对齐方式
						selectItemName : 'id',
						toolbar : "#toolbar",
						toolbarAlign : 'left',
						columns : [{
					                checkbox: true
					            },
								{
									field : 'PKID',// 可不加
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
									field: 'XUEHAO',//可不加  
									title:'学号',
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
									field : 'ZUZHINAME',
									title:'院校专业',
									align : "left",
									halign : 'center',
									sortable : false
								},
								{
									field : 'NIANJI',
									title:'入学年份',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'CENGCI_NAME',
									title:'学生类型',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'PICI_NAME',
									title:'批次',
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
									field : 'SCHOOL_YEAR_NAME',
									title:'学年',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'PAYITEM',
									title:'项目名称',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'PAYITEM_CHILD',
									title:'子项目',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'COST',
									title:'费用',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										return value != null?value.toFixed(2):0;
										
									}
									
								},
								{
									field : 'DISCOUNT',
									title:'优惠',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'AMOUNTRECEIVABLE',
									title:'应收金额',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										return value != null?value.toFixed(2):0;
										
									}
								},
								{
									field : 'LOAN_MONE',
									title:'贷款/缓交',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										var LOAN_MONE = row.LOAN_MONE != null?row.LOAN_MONE.toFixed(2):0;
										return LOAN_MONE
									}
								},
								{
									field : 'AMOUNTCOLLECTION',
									title:'实收金额',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										return value != null?value.toFixed(2):0;
										
									}
								},
								{
									field : 'STATUS',
									title:'缴费状态',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'AMOUNTQF',
									title:'欠费金额',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										var AMOUNTRECEIVABLE = row.AMOUNTRECEIVABLE != null?row.AMOUNTRECEIVABLE.toFixed(2):0;
										var AMOUNTCOLLECTION = row.AMOUNTCOLLECTION != null?row.AMOUNTCOLLECTION.toFixed(2):0;
										return (AMOUNTRECEIVABLE - AMOUNTCOLLECTION).toFixed(2)
									}
								},
								{
									field : 'GOODS_GET_COUNT',
									title:'是否领取',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										/*return value > 0 ? "是" : "-";*/
										if(value>0&&row.AMOUNTCOLLECTION>0){//表示审核通过
											return '是';
										}else{
											return '-';
										}
										
									}
								},
								
								{
									field : 'opt',
									title : '操作',
									align : "center",
									halign : 'center',
									formatter : function(value, row,
											index) {
										return [
												'<span class="see fa fa-clock-o jf_icon" title="缴费记录"></span>&nbsp;&nbsp;']
												.join('');
										
									}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
									 events : {
										'click .see' : function(e,value, row, index) {
											$('#mymoda5').modal({
												keyboard: true
											});
											var sPkid = row.SPKID;
											var payItemPkid = row.PAY_ITEM_PKID;
//											getPayItemList(payItemPkid,sPkid);
											payRecord(payItemPkid,sPkid);
    							        
										},

											} 
								}
						],
					});
    $('#payItemListTable').bootstrapTable('hideColumn', 'PKID');//隱藏列
    
    if(colStr!=null && colStr!=''){
		var resultColumnList = $('#payItemListTable').bootstrapTable('getVisibleColumns');

		$.each(resultColumnList, function(i, column) {
			if(column.field=='0'/*||column.field=='opt'*/){
				return;
			}
			 $('#payItemListTable').bootstrapTable('hideColumn', column.field);
     	});		
		var array=colStr.split(",");
		for(var i=0;i<array.length;i++){
			var field=array[i];
			if(field==''){
				continue;
			}
			$('#payItemListTable').bootstrapTable('showColumn', field);
			
		}
		
	}
    
   
};
var getPayItemList = function(payItemPkid,sPkid){
//	alert(payItemPkid+"--"+sPkid);
	$.ajax({
		  type: 'POST',
		  url: _basepath+"pay/getPayItemList.json",
		  async: false,
		  data: {
			payItemPkid:payItemPkid,
			studentPkid:sPkid
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  $("#payItemObj").html(result.payItemListObject.PAYITEM);
				  $("#costObj").html(result.payItemListObject.COST+"元");
				  
				 /* var moneyResult = '';
				  if(result.payItemListObject.DISCOUNT_MODE == '1'){
					  moneyResult = result.payItemListObject.DISCOUNT_MONEY +"元";
				  }else if(result.payItemListObject.DISCOUNT_MODE == '2'){
					  moneyResult = result.payItemListObject.DISCOUNT +"折";
				  }
				  $("#discountScopeObj").html(result.payItemListObject.DISCOUNT_SCOPE != null ? result.payItemListObject.DISCOUNT_SCOPE:""
					  +" "
					  + moneyResult);*/
				  $("#discountScopeObj").html(result.payItemListObject.DISCOUNTS);
				  $("#discountMoneyObj").html((result.payItemListObject.AMOUNTRECEIVABLE - result.payItemListObject.AMOUNTCOLLECTION)+"元");
				  var statusResult = "";
				  if(result.payItemListObject.STATUS == '0'){
					  statusResult = "欠费";
				  }else if(result.payItemListObject.STATUS == '1'){
					  statusResult = "核验中";
				  }else if(result.payItemListObject.STATUS == '2'){
					  statusResult = "已完成";
				  }else if(result.payItemListObject.STATUS == '3'){
					  statusResult = "已关闭";
				  }
				  $("#statusObj").html(statusResult);
			  }
			  return false;
		  }
		});
}
//点击缴费记录
var payRecord = function(payItemPkid,sPkid){
	 $("#payRecordList").html("");
	 $("#payOrderDetailObj").html("");
	  $("#payRecordList").append('<tr>'+
			  '<th>缴费项目</th>'+
			  '<th>子项目</th>'+
				'<th>缴费时间</th>'+
				'<th>缴费类型</th>'+
				'<th>金额</th>'+
				'<th>支付方式</th>'+
				'<th>现金</th>'+
				'<th>银行卡</th>'+
				'<th>电汇</th>'+
				'<th>微信</th>'+
				'<th>支付宝</th>'+
				'<th>缴费状态</th>'+
			'</tr>');
	  
	var url=_basepath+"pay/getPayOrderDetailList.json";
	$.ajax({
		  type: 'POST',
		  url: url,
		  async: false,
		  data: {
			payItemPkid:payItemPkid,
			studentPkid:sPkid
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				 
				  var recordDataList = result.payOrderDetailList;
				  if(recordDataList != ''){
					  $("#payRecordList").html('');
					  for(var i=0;i < recordDataList.length; i++){
						  if(i == 0){
							  if(recordDataList[i].PAYITEM_CHILD == null || recordDataList[i].PAYITEM_CHILD == '' || recordDataList[i].PAYITEM_CHILD == 'undefined' || recordDataList[i].PAYITEM_CHILD == 'null'){
								  $("#payRecordList").append('<tr>'+
										  '<th>缴费项目</th>'+
											'<th>缴费时间</th>'+
											'<th>缴费类型</th>'+
											'<th>金额</th>'+
											'<th>支付方式</th>'+
											'<th>现金</th>'+
											'<th>银行卡</th>'+
											'<th>电汇</th>'+
											'<th>微信</th>'+
											'<th>支付宝</th>'+
											'<th>缴费状态</th>'+
										'</tr>');
							  }else{
								  $("#payRecordList").append('<tr>'+
										  '<th>缴费项目</th>'+
										  '<th>子项目</th>'+
											'<th>缴费时间</th>'+
											'<th>缴费类型</th>'+
											'<th>金额</th>'+
											'<th>支付方式</th>'+
											'<th>现金</th>'+
											'<th>银行卡</th>'+
											'<th>电汇</th>'+
											'<th>微信</th>'+
											'<th>支付宝</th>'+
											'<th>缴费状态</th>'+
										'</tr>');
							  }
							  
						  }
						  var record = '<tr id="rowRecord'+i+'" PAYITEM="'+recordDataList[i].PAYITEM+'"  PAYITEM_CHILD="'+recordDataList[i].PAYITEM_CHILD+'"  onclick="javascript:seeRecordDetail(\''+recordDataList[i].PKID+'\',\''+recordDataList[i].ORDERCREATE_MODE+'\',this);">'+
						  '<td>'+recordDataList[i].PAYITEM+'</td>';
						  if(recordDataList[i].PAYITEM_CHILD == null || recordDataList[i].PAYITEM_CHILD == '' || recordDataList[i].PAYITEM_CHILD == 'undefined' || recordDataList[i].PAYITEM_CHILD == 'null'){
							  record+= '';
						  }else{
							  record+= '<td>'+recordDataList[i].PAYITEM_CHILD+'</td>';
						  }
						  record+= '<td>'+recordDataList[i].CJSJ+'</td>';
						  var inOutPut = '';
						  if(typeof recordDataList[i].INPUT_OUTPUT == 'undefined' || recordDataList[i].INPUT_OUTPUT == 'null'){
							  inOutPut = '-';
						  }else{
							  if(recordDataList[i].INPUT_OUTPUT == 'JX'){
								  inOutPut = '收入';
							  }else if(recordDataList[i].INPUT_OUTPUT == 'XX'){
								  inOutPut = '支出';
							  }else{
								  inOutPut = '-';
							  }
							  
						  }
						  record+='<td>'+inOutPut+'</td>'+
								'<td>'+recordDataList[i].totalMoney+'</td>'+
								'<td>'+(typeof recordDataList[i].ORDERCREATE_MODE == 'undefined' || recordDataList[i].ORDERCREATE_MODE == 'null'?"-":recordDataList[i].ORDERCREATE_MODE)+'</td>'+
								'<td>'+(typeof recordDataList[i].CASH == 'undefined'?"-":recordDataList[i].CASH)+'</td>'+
								'<td>'+(typeof recordDataList[i].CARD == 'undefined'?"-":recordDataList[i].CARD)+'</td>'+
								'<td>'+(typeof recordDataList[i].TT == 'undefined'?"-":recordDataList[i].TT)+'</td>'+
								'<td>'+(typeof recordDataList[i].WX == 'undefined'?"-":recordDataList[i].WX)+'</td>'+
								'<td>'+(typeof recordDataList[i].ZFB == 'undefined'?"-":recordDataList[i].ZFB)+'</td>'+
								'<td>'+recordDataList[i].STATUS+'</td>'+
							'</tr>';
						  $("#payRecordList").append(record);
					  }
				  }
				  
				  return false;
			  }else{
				  layer.msg("查看失败，请重试!");
			  }
			  
			  return false;
		  }
		});
}
//查看某一条缴费记录详情payOrderDetailObj
var seeRecordDetail = function(orderDetailPkid,orderCreateModel,obj){
	$("#payOrderDetailObj").html("");
	var url=_basepath+"pay/getPayOrderDetail.json";
	$.ajax({
		  type: 'POST',
		  url: url,
		  async: false,
		  data: {
			  orderDetailPkid:orderDetailPkid,
			  orderCreateModel:orderCreateModel
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  var recordDataList = result.payOrderDetail;
				  if(recordDataList != ''){
					  var record = "";
					  for(var i=0;i < recordDataList.length; i++){
						  if(i == 0){
							  $("#payItemCJSJ").html(recordDataList[i].PAY_ITEM_CJSJ);
						  }
						  record += '<tr class="jf_TrNone">'+
							'<td></td>'+
							'<td class="jf_TdBold">缴费名称</td>'+
							'<td>'+$(obj).attr("PAYITEM")+'</td>'+
						'</tr>';
						  if($(obj).attr("PAYITEM_CHILD") == null || $(obj).attr("PAYITEM_CHILD") == '' || $(obj).attr("PAYITEM_CHILD") == 'undefined' || $(obj).attr("PAYITEM_CHILD") == 'null'){
							  
						  }else{
							  record += '<tr class="jf_TrNone">'+
								'<td></td>'+
								'<td class="jf_TdBold">子项目</td>'+
								'<td>'+$(obj).attr("PAYITEM_CHILD")+'</td>'+
							'</tr>';
						  }
						  
						  record += '<tr class="jf_TrNone">'+
								'<td></td>'+
								'<td class="jf_TdBold">缴费方式</td>'+
								'<td>'+recordDataList[i].ORDERCREATE_MODE+'</td>'+
							'</tr>';
						  if(recordDataList[i].ORDERCREATE_MODE == '线上'){
							  record += '<tr class="jf_TrNone">'+
								'<td></td>'+
								'<td class="jf_TdBold">订单编号</td>'+
								'<td>'+recordDataList[i].PAY_ORDERNO+'</td>'+
							'</tr>'+
							'<tr class="jf_TrNone">'+
								'<td></td>'+
								'<td class="jf_TdBold">交易号</td>'+
								'<td>'+recordDataList[i].TRANSACTION_HOST_SN+'</td>'+
							'</tr>'+
								'<tr class="jf_TrNone">'+
								'<td></td>'+
								'<td class="jf_TdBold">创建时间</td>'+
								'<td>'+recordDataList[i].CJSJ+'</td>'+
							'</tr>'+
								'<tr class="jf_TrNone">'+
								'<td></td>'+
								'<td class="jf_TdBold">付款时间</td>'+
								'<td>'+recordDataList[i].CJSJ+'</td>'+
							'</tr>';
							  var zzfs = recordDataList[i].ZFFS;
							  if(typeof zzfs !='undefined'){
								  zzfs = zzfs.split("|");
								  var zzfsHtml = '';
								  for (var int = 0; int < zzfs.length; int++) {
									  var zzfss = zzfs[int].split(":");
									  zzfsHtml += '<tr class="jf_TrNone"><td></td>';
									  zzfsHtml += '<td class="jf_TdBold">'+(int == 0?'支付方式':'')+'</td>';
									  if(zzfss[0]== 'CASH'){
										  zzfsHtml += '<td>现金：'+zzfss[1]+'元</td>';
										  zzfsHtml += '</tr>';
									  }else if(zzfss[0]== 'CARD'){
										  var zzfsss = zzfss[1].split("@");
										  zzfsHtml += '<td>银行卡：'+zzfsss[0]+'元</td>';
										  zzfsHtml += '</tr>';
										  zzfsHtml += '<tr class="jf_TrNone">'+
											'<td></td>'+
											'<td></td>'+
											'<td>卡号  '+(typeof zzfsss[1] != 'undefined' && zzfsss[1] != 'null'?zzfsss[1]:"")+'</td>'+
										'</tr>';
									  }else if(zzfss[0]== 'TT'){
										  zzfsHtml += '<td>电汇：'+zzfss[1]+'元</td>';
										  zzfsHtml += '</tr>';
									  }else if(zzfss[0]== 'WX'){
										  zzfsHtml += '<td>微信：'+zzfss[1]+'元</td>';
										  zzfsHtml += '</tr>';
									  }else if(zzfss[0]== 'ZFB'){
										  zzfsHtml += '<td>支付宝：'+zzfss[1]+'元</td>';
										  zzfsHtml += '</tr>';
									  }else{
										  zzfsHtml += '<td></td>';
										  zzfsHtml += '</tr>';
									  }
									  record += zzfsHtml;
								  }
							  }
							  
						  }else{
							  record += '<tr class="jf_TrNone">'+
								'<td></td>'+
								'<td class="jf_TdBold">缴费时间</td>'+
								'<td>'+recordDataList[i].CJSJ+'</td>'+
							'</tr>'+
							'<tr class="jf_TrNone">'+
								'<td></td>'+
								'<td class="jf_TdBold">收款人</td>'+
								'<td>'+(recordDataList[i].USERNAME != ""?recordDataList[i].USERNAME:"")+'</td>'+
							'</tr>';
						  var zzfs = recordDataList[i].ZFFS;
						  if(typeof zzfs !='undefined'){
							  zzfs = zzfs.split("|");
							  var zzfsHtml = '';
							  for (var int = 0; int < zzfs.length; int++) {
								  var zzfss = zzfs[int].split(":");
								  zzfsHtml += '<tr class="jf_TrNone"><td></td>';
								  zzfsHtml += '<td class="jf_TdBold">'+(int == 0?'支付方式':'')+'</td>';
								  if(zzfss[0]== 'CASH'){
									  zzfsHtml += '<td>现金：'+zzfss[1]+'元</td>';
									  zzfsHtml += '</tr>';
								  }else if(zzfss[0]== 'CARD'){
									  var zzfsss = zzfss[1].split("@");
									  zzfsHtml += '<td>银行卡：'+zzfsss[0]+'元</td>';
									  zzfsHtml += '</tr>';
									  zzfsHtml += '<tr class="jf_TrNone">'+
										'<td></td>'+
										'<td></td>'+
										'<td>卡号  '+(typeof zzfsss[1] != 'undefined' && zzfsss[1] != 'null'?zzfsss[1]:"")+'</td>'+
									'</tr>';
								  }else if(zzfss[0]== 'TT'){
									  zzfsHtml += '<td>电汇：'+zzfss[1]+'元</td>';
									  zzfsHtml += '</tr>';
								  }else if(zzfss[0]== 'WX'){
									  zzfsHtml += '<td>微信：'+zzfss[1]+'元</td>';
									  zzfsHtml += '</tr>';
								  }else if(zzfss[0]== 'ZFB'){
									  zzfsHtml += '<td>支付宝：'+zzfss[1]+'元</td>';
									  zzfsHtml += '</tr>';
								  }else{
									  zzfsHtml += '<td></td>';
									  zzfsHtml += '</tr>';
								  }
								  
							  }
						  }
						  
						  record += zzfsHtml;
						  record += '<tr class="jf_TrNone">'+
								'<td></td>'+
								'<td class="jf_TdBold">支付金额</td>'+
								'<td>'+recordDataList[i].MONEY+'元</td>'+
							'</tr>';
						  }
						  record += '<tr class="jf_TrNone">'+
							'<td>&nbsp;</td>'+
							'<td class="jf_TdBold">&nbsp;</td>'+
							'<td>&nbsp;</td>'+
						'</tr>';
						  record += '<tr class="jf_TrNone">'+
							'<td>&nbsp;</td>'+
							'<td class="jf_TdBold">&nbsp;</td>'+
							'<td>&nbsp;</td>'+
						'</tr>';
						
						  
					  }
					  $("#payOrderDetailObj").html(record);
				  }
				  
				 
			  }
			  
			  return false;
		  }
		});
}
//点击保存
$('#btn_save').click(function(){
	//pkid
	var pkid=$("#pkid").val();
	//payItemId
	var payItemPkid=$("#payItemPkid").val();
	//应收金额
	var amountreceivable_new = $("#amountreceivable_new").val();
	//费用
	var cost_new = $("#cost_new").val();
	//折扣模式
	var discountMode = $("input[name='discountMode']:checked").val();
	var discount;
	var discountVal = false;
	if(discountMode == 0){//无优惠
		discount = '不优惠';
	}else if(discountMode == 1){//打折
		discount = $("#discount_zhe").val()+'折';
		discountVal = true;
		if(!$("#discount_zhe").val().match("^[0-9]{1}$|^10$")){
			layer.msg("折扣必须在1~10之间!");
			return false;
		}
	}else if(discountMode == 2){//立减
		discount = '立减 -'+$("#discount_jian").val();
		discountVal = true;
	}else{
		layer.msg("请先选择优惠方式!");
		return false;
	}
	if(cost_new == ''){
		layer.msg("调整后的费用不能为空！");
		return false;
	}
	if(discountVal == 1 && $("#discount_zhe").val() == '' && $("#discount_jian").val() == ''){
		layer.msg("调整后的优惠方式不能为空！");
		return false;
	}

	var url=_basepath+"pay/updatePayItemList.json";
	$.ajax({
		  type: 'POST',
		  url: url,
		  async: false,
		  data: {
			pkid:pkid,
			amountreceivable:amountreceivable_new,
			cost:cost_new,
			discountMode:discountMode,
			discount:discount
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result == 'success'){
				  layer.msg("保存成功!");
				  $("#btn_cancel2").click();
				  $("#payItemListTable").bootstrapTable('refresh', {url: _basepath+"pay/payItemListTable.json?payItemPkid="+payItemPkid});
				  return false;
			  }else{
				  layer.msg("保存失败!");
			  }
			  
			  return false;
		  }
		});
	return false;
//	var result = pil.save_edit();
//	if(result == 0 || result == 1 ){
//		return false;
//	}
});


//名单-- 点击导出
$("#btn_export").click(function(){
	var payitempkid=$("#orgtree2").val();
	//查询条件-缴费状态
	var status = $("#statusselect  option:selected").val();
	//查询条件-入学年份
	var grade = $("#gradeselect  option:selected").val();
	//查询条件-院校专业
	var DEPARTMENT_PKID = $("#orgtree").val();
	//查询条件-姓名/学号/身份证号
	var keywords = $("#conditionsselect").val();
	var PAY_TYPE_PKID = $("#pay_style_md").val();
	var SCHOOL_YEAR_PKID = $("#school_year_md").val();
	window.location.href=encodeURI(_basepath+'pay/exportStuPayList.json?payitempkid='+payitempkid+
			'&status='+status+'&nianji='+grade+'&DEPARTMENT_PKID='+DEPARTMENT_PKID+'&keywords='+
			keywords+'&showStatus=12'+'&PAY_TYPE_PKID='+PAY_TYPE_PKID+'&SCHOOL_YEAR_PKID='+SCHOOL_YEAR_PKID
	);
//	pil.buttonStatuss();
//	window.location.href=encodeURI(_basepath+'pay/exportStudentPayList.json?payItemPkid='+$("#payItemPkid").val()+'&&payItemListPkids='+(pil.getIdSelections()+""));
	
});

$('#discount_zhe').click(function(){
	$("#r2").removeAttr("checked");
	$("#r0").removeAttr("checked");
	$("#r1").attr("checked","checked");
	
});
$('#discount_jian').click(function(){
	$("#r1").removeAttr("checked");
	$("#r0").removeAttr("checked");
	$("#r2").attr("checked","checked");
	
});




$('#btn_search').click(function(){
	//项目pkid 缴费名单搜索功能 add by kangcl
	var payitempkid=$("#orgtree2").val();
	//查询条件-缴费状态
	var status = $("#statusselect  option:selected").val();
	//查询条件-入学年份
	var grade = $("#gradeselect  option:selected").val();
	//查询条件-院校专业
	var DEPARTMENT_PKID = $("#orgtree").val();
	//查询条件-姓名/学号/身份证号
	var conditions = $("#conditionsselect").val();
	var url = _basepath+"pay/payItemListTable.json";
   	 var opt = {
			url :url,
		    silent: true,
		    query:{
		    	"status":status,
		    	"grade":grade,
		    	"DEPARTMENT_PKID":DEPARTMENT_PKID,
		    	"conditions":conditions,
		    	"payItemPkid":payitempkid,
		    	"showStatus":"12"
		    	
		    }
		  };
	 $("#payItemListTable").bootstrapTable('refresh', opt);
   
});

$(function(){
	//控制单选按钮
	$(":radio").click(function(){
		var index = $(this).val();
		if(index == 1){
			$('#discount_zhe').removeAttr("disabled");
			$('#discount_zhe').removeAttr("readonly");
			$('#discount_jian').attr("disabled","disabled");
			$('#discount_jian').attr("readonly","readonly");
			$('#discount_jian').val("");
			$('#amountreceivable_new').val("");
		}else if(index == 2){
			$('#discount_jian').removeAttr("disabled");
			$('#discount_jian').removeAttr("readonly");
			$('#discount_zhe').attr("disabled","disabled");
			$('#discount_zhe').attr("readonly","readonly");
			$('#discount_zhe').val("");
			$('#amountreceivable_new').val("");
		}else if(index == 0){
			$('#discount_zhe').attr("disabled","disabled");
			$('#discount_zhe').attr("readonly","readonly");
			$('#discount_jian').attr("disabled","disabled");
			$('#discount_jian').attr("readonly","readonly");
			$('#discount_jian').val("");
			$('#discount_zhe').val("");
			var discountMode = $("input[name='discountMode']:checked").val();
			if(discountMode == "0" && $('#cost_new').val() !=''){
				$('#amountreceivable_new').val($('#cost_new').val());
			}else{
				$('#amountreceivable_new').val("");
			}
		}
	});
	//计算应收金额开始..........
	$("input[name='discountMode']:eq(2)").attr("checked",'checked'); 
	$('#discount_zhe').bind('input propertychange', function() {
		var result = $("#cost_new").val() * $('#discount_zhe').val() /10;
		$("#amountreceivable_new").val(result);
	}); 
	$('#discount_jian').bind('input propertychange', function() {
		var result = $("#cost_new").val() - $('#discount_jian').val();
		$("#amountreceivable_new").val(result);
	}); 
	$('#cost_new').bind('input propertychange', function() {
		var discountMode = $("input[name='discountMode']:checked").val();
		if(discountMode == "0"){
			var result = $('#cost_new').val();
			$("#amountreceivable_new").val(result);
		}else if(discountMode == "1"){
			var result = $("#cost_new").val() * $('#discount_zhe').val() /10;
			$("#amountreceivable_new").val(result);
		}else if(discountMode == "2"){
			var result = $("#cost_new").val() - $('#discount_jian').val();
			$("#amountreceivable_new").val(result);
		}
		
		
	}); 
	
	//计算应收金额结束..........
	pil.getTab();
	pil.buttonStatuss();
});
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
}
