//@ sourceURL=payItemList
//缴费管理页面js
var pil={};
pil.studentPkids='';
pil.payItemListPkids='';
pil.isFlag = true;

$("#jmdjYHJE").on("input",function(e){
    //获取input输入的值
	var jmdjYHJE=$("#jmdjYHJE").val();
	var jmdjAMOUNTRECEIVABLE=$("#jmdjAMOUNTRECEIVABLE").val();
	//^([1-9]\d{0,9}|0)([.]?|(\.\d{1,2})?)$  .toFixed(2)
	if (!/^([1-9]\d{0,9}|0)([.]?|(\.\d{1,2})?)$/.test(jmdjYHJE)) {
		layer.msg('只能输入数字，小数点后只能保留两位!');
		var jmdjYHJE=$("#jmdjYHJE").val("");
		var ys=$("#jmdjAMOUNTRECEIVABLE").val();
		$("#jmdjTZHMONEY").val(ys);
		//$("#test3").val("Dolly Duck");
	}else{
		var c =Number(jmdjAMOUNTRECEIVABLE.replace(/,/g, "")) - 
                Number(jmdjYHJE.replace(/,/g,""));
		var jmdjTZHMONEY=$("#jmdjTZHMONEY").val(c.toFixed(2));
		
	}


});



$("#btn_msg").bind("click",function(){
	 var payitempkid=$("#payItemPkid").val();
	var dialog = BootstrapDialog.show({
		title:'一键催缴',
		message: $('<div></div>').load("pay/tosendMsg.json?payitempkid="+payitempkid),
		closable: true,//是否显示叉号
		draggable: true,//可以拖拽
		 buttons: [{
			    label: '确定',
			    cssClass: 'btn-danger',
			    action: function(dialogRef){
			    	//
			    	pil.sendWxMsg();
			    	dialogRef.close();
             }
		  }, {
		    label: '取消',
		    cssClass: 'btn-default',
		    action: function(dialogRef){
		       dialogRef.close();
		    }
		  }
		  ]
	
	});
	
});
pil.sendWxMsg=function(){
	 var payitempkid=$("#payItemPkid").val();
	$.get(_basepath+"pay/sendWxMsg.json?payitempkid="+payitempkid,function(){
		
	});
};
//获取选中的行
pil.getIdSelections=function () {
		var $table = $('#studentListTable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.PKID;
    });
};
//获取选中的行
pil.getPayItemListPkidsSelections=function () {
		var $table = $('#payItemListTable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.PKID;
    });
};
pil.buttonStatus = function(){
	var ids = pil.getIdSelections()+"";
	if(ids != ''){
		$("#btn_save_add").attr("disabled",false);
	}else{
		$("#btn_save_add").attr("disabled","disabled");
	}
}

pil.buttonStatuss = function(){
	var ids = pil.getPayItemListPkidsSelections()+"";
	if(ids != ''){
		$("#btn_export").attr("disabled",false);
	}else{
		$("#btn_export").attr("disabled",false);
	}
}
$("#btn_import_jianmian").click(function(){
	var payitempkid=$("#payItemPkid").val();
	pil.openImportJianmian(payitempkid);
});
//批量减免
pil.openImportJianmian = function(payitem){
	
	var dialog = BootstrapDialog.show({
		title:'批量减免',
		message: $('<div></div>').load("import/importJianMian.json?item_pkid="+payitem),
		closable: true,//是否显示叉号
		draggable: true,//可以拖拽
		buttons: [{
            label: '关闭',
            cssClass: 'btn-danger',
            action: function(dialog) {
            	dialog.close();
            	$('#btn_search').click();
            	return false;
            }
        }]
	});
	
};

$("#btn_import_daikuan").click(function(){
	var payitempkid=$("#payItemPkid").val();
	pil.openImportdaikuan(payitempkid);
});
//批量减免
pil.openImportdaikuan= function(payitem){
	
	var dialog = BootstrapDialog.show({
		title:'批量贷款',
		message: $('<div></div>').load("importloan/importloanmoney.json?item_pkid="+payitem),
		closable: true,//是否显示叉号
		draggable: true,//可以拖拽
		buttons: [{
            label: '关闭',
            cssClass: 'btn-danger',
            action: function(dialog) {
            	dialog.close();
            	$('#btn_search').click();
            	return false;
            }
        }]
	});
	
};
pil.getStudentPayTab = function(){
	var url = _basepath+"pay/getStudentPaylistPage.json";
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
						},
						//行选
						onClickRow : function(row, tr,flied){
							var rowPayItemPKID = row.PKID;
			            	//设置批量按钮是否可点击
			            	pil.buttonStatus();
				        },
				        //全选
				        onCheckAll:function(rows){
				        	//设置批量按钮是否可点击
			            	pil.buttonStatus();
				        	
				        },
			            //点击每一个单选框时触发的操作
			            onCheck:function(row){
			            	var rowPayItemPKID = row.PKID;
			            	//设置批量按钮是否可点击
			            	pil.buttonStatus();
			            },
			            //取消每一个单选框时对应的操作；
			            onUncheck:function(row){
			            	var rowPayItemPKID = row.PKID;
			            	//设置批量按钮是否可点击
			            	pil.buttonStatus();
//			            	alert(pil.studentPkids)
			            },
			            //取消每一个单选框时对应的操作；
			            onUncheckAll:function(rows){
			            	//设置批量按钮是否可点击
			            	pil.buttonStatus();
			            },
						queryParams : function getParams(params) {
							var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
								limit : params.limit, //页面大小
								searchText : this.searchText,
								sortName : this.sortName,
								sortOrder : this.sortOrder,
								pageindex : this.pageNumber,
								payItemPkid: $("#payItemPkid").val(),
								conditions:$("#studentConditionSelect").val()
								
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
									title:'院校专业',
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
}
//获取list表格数据
pil.getTab=function (url,isRefresh) {
	//获得按钮权限
	var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
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
						showColumns : false, //显示下拉框勾选要显示的列  
						showRefresh : false, //显示刷新按钮  --只是前台刷新，个人觉得没什么用
						minimumCountColumns : 2, //最少允许的列数
						sidePagination : "server", //表示服务端请求  
						totalRows : 0, // server side need to set
						singleSelect : false,
						clickToSelect : true,
						onColumnSwitch: function(field,checked){
							//获取全部可显示的节点信息集合
							var resultColumnList = $('#payItemListTable').bootstrapTable('getVisibleColumns');
							var resultColumns = "";		
							//将所有field组合成以逗号隔开的字符串
							$.each(resultColumnList, function(i, column) {
								if(column.field=='0'/*||column.field=='opt'*/){
									return;
								}else{
									resultColumns+=column.field+",";
								}									
					     	});
							//更新可查看字段内容
							$.post("stuinfo/updateShowCols.json?table_name=CKJFMD_LIST",{table_show_cols:resultColumns},function(data){
								if(data.result=="success"){

								}
							})
						},
						onDblClickRow:function(row){
						},
						//行选
						onClickRow : function(row, tr,flied){
							var rowPayItemListPKID = row.PKID;
							//包含
			            	if(pil.payItemListPkids.indexOf(rowPayItemListPKID) < 0){
			            		pil.payItemListPkids += rowPayItemListPKID;
								pil.payItemListPkids += ',';
			            	}
			            	//设置批量按钮是否可点击
			            	pil.buttonStatuss();
				        },
				        //全选
				        onCheckAll:function(rows){
				        	for (var i=0;i<rows.length;i++){
				        		var rowPayItemListPKID = rows[i].PKID;
				            	if(pil.payItemListPkids.indexOf(rowPayItemListPKID) == -1){
				            		pil.payItemListPkids +=rowPayItemListPKID;
									pil.payItemListPkids += ',';
				            	}else{//包含
				            		pil.payItemListPkids = pil.payItemListPkids.replace(rowPayItemListPKID+",","");
				            		
				            	}
				        		
				        	}
				        	//设置批量按钮是否可点击
			            	pil.buttonStatuss();
				        	
				        },
			            //点击每一个单选框时触发的操作
			            onCheck:function(row){
			            	var rowPayItemListPKID = row.PKID;
							//不包含
			            	if(pil.payItemListPkids.indexOf(rowPayItemListPKID) < 0){
			            		pil.payItemListPkids += rowPayItemListPKID;
								pil.payItemListPkids += ',';
			            	}
			            	//设置批量按钮是否可点击
			            	pil.buttonStatuss();
			            },
			            //取消每一个单选框时对应的操作；
			            onUncheck:function(row){
			            	var rowPayItemListPKID = row.PKID;
			            	//包含
			            	if(pil.payItemListPkids.indexOf(rowPayItemListPKID) >= 0){
			            		pil.payItemListPkids = pil.payItemListPkids.replace(rowPayItemListPKID+",","");
			            	}
			            	//设置批量按钮是否可点击
			            	pil.buttonStatuss();
//			            	alert(pil.payItemListPkids)
			            },
			            //取消每一个单选框时对应的操作；
			            onUncheckAll:function(rows){
			            	for (var i=0;i<rows.length;i++){
				        		var rowPayItemListPKID = rows[i].PKID;
				        		//包含
				            	if(pil.payItemListPkids.indexOf(rowPayItemListPKID) >= 0){
				            		pil.payItemListPkids = pil.payItemListPkids.replace(rowPayItemListPKID+",","");
				            	}else{
				            		pil.payItemListPkids +=rowPayItemListPKID;
									pil.payItemListPkids += ',';
				            	}
				        		
				        	}
			            	//设置批量按钮是否可点击
			            	pil.buttonStatuss();
			            },
						queryParams : function getParams(params) {
							//项目pkid 
							var payitempkid=$("#payItemPkid").val();
							//查询条件-缴费状态
							var status = $("#statusselect  option:selected").val();
							//查询条件-入学年份
							var grade = $("#gradeselect  option:selected").val();
							//查询条件-所属组织
							var DEPARTMENT_PKID = $("#orgtree").val();
							//查询条件-姓名/学号/身份证号
							var conditions = $("#conditionsselect").val();
							
							var mdruxuenianfen = $("#mdruxuenianfen  option:selected").val();
							var mdbanxing = $("#mdbanxing  option:selected").val();
							
							var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
								limit : params.limit, //页面大小
								searchText : this.searchText,
								sortName : this.sortName,
								sortOrder : this.sortOrder,
								pageindex : this.pageNumber,
								STATUS:status,
						    	grade:grade,
						    	DEPARTMENT_PKID:DEPARTMENT_PKID,
						    	conditions:conditions,
						    	payItemPkid:payitempkid,
						    	
						    	RXNIANFEN_PKID:mdruxuenianfen,
						    	BANJI_TYPE_PKID:mdbanxing,
						    	
						    	
								showStatus:'012'
								
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
									title:'身份证号',
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
									field : 'RUXUENIANFEN',
									title:'入学年份',
									align : "center",
									halign : 'center',
									sortable : false
								}
								,
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
									field : 'CLASS_NAME',
									title:'班级',
									align : "center",
									halign : 'center',
									sortable : false
								}
								,
								{
									field : 'AMOUNTRECEIVABLE',
									title:'应收金额',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										return value.toFixed(2);
										
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
										return value.toFixed(2);
										
									}
								},
								{
									field : 'AMOUNTQF',
									title:'欠费金额',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										return (row.AMOUNTRECEIVABLE - row.AMOUNTCOLLECTION).toFixed(2);
									}
								},
								{
									field : 'ZTSTATUS',
									title:'缴费状态',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'opt',
									title : '操作',
									align : "center",
									halign : 'center',
									formatter : function(value, row,
											index) {
										var AMOUNTCOLLECTION = row.AMOUNTCOLLECTION;
										return [
												/*SESSION_MENU_BUTTONS.jfgl_tzysje == 1?'<span class="edit fa fa-tasks jf_icon" title="调整应收金额"></span>&nbsp;&nbsp;':'',*/
														'<span class="edit fa fa-tasks jf_icon" title="调整应收金额"></span>&nbsp;&nbsp;',
												'<span class="derate fa fa-yen jf_icon" title="减免登记"></span>&nbsp;&nbsp;',
												'<span class="seejfjl fa fa-clock-o jf_icon" title="缴费记录"></span>&nbsp;&nbsp;',
												'<span class="seelog fa fa-clock-o jf_icon" title="费用变动记录"></span>&nbsp;&nbsp;'
												/*'<span class="msglog fa fa-comment-o jf_icon" title="通知记录"></span>&nbsp;&nbsp;',*/
												/*SESSION_MENU_BUTTONS.jfgl_dkhjdj == 1?'<span class="loan fa fa-pencil jf_icon" title="贷款/缓交登记"></span>&nbsp;&nbsp;':'',
												SESSION_MENU_BUTTONS.jfgl_jmdj == 1?'<span class="derate fa fa-yen jf_icon" title="减免登记"></span>&nbsp;&nbsp;':'',
												SESSION_MENU_BUTTONS.jfgl_scjfmd == 1? AMOUNTCOLLECTION <=0 ?'<span class="del fa fa-trash-o" title="删除"></span>':'':''*/]
												.join('');
										
									}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
									 events : {
										'click .edit' : function(e,value, row, index) {
											
											
											$('#AMOUNTRECEIVABLE').val(row.AMOUNTRECEIVABLE);
											$('#yspkid').val(row.PKID);
											$('#TZHMONEY').val('');
											//编辑
									  		$('#mymodal2').modal({
									  			keyboard: true
									  		});
											
											
											/*
											$.ajax({
												  type: 'POST',
												  url: _basepath+"pay/getPayOrderDetailCount.json",
												  async: false,
												  data: {
													payItemPkid:row.PAY_ITEM_PKID,
													Pkid:row.PKID
												  },
												  dataType : "json",
												  success : function(result) {
													  
													  $('#pkid').val(row.PKID);
												  		$('#COST').html(row.COST);
												  		$('#DISCOUNT').html(row.DISCOUNT);
												  		$('#AMOUNTRECEIVABLE').html(row.AMOUNTRECEIVABLE);
												  		
												  		$("#cost_new").val("");
												  		$("#discount_zhe").val("");
												  		$("#discount_jian").val("");
												  		$("#amountreceivable_new").val("");
												  		//编辑
												  		$('#mymodal2').modal({
												  			keyboard: true
												  		});
													  
													  if(result.result == 'fail'){//有记录
														  	if(row.AMOUNTRECEIVABLE - row.AMOUNTCOLLECTION > 0){
														  		$('#pkid').val(row.PKID);
														  		$('#COST').html(row.COST);
														  		$('#DISCOUNT').html(row.DISCOUNT);
														  		$('#AMOUNTRECEIVABLE').html(row.AMOUNTRECEIVABLE);
														  		
														  		$("#cost_new").val("");
														  		$("#discount_zhe").val("");
														  		$("#discount_jian").val("");
														  		$("#amountreceivable_new").val("");
														  		//编辑
														  		$('#mymodal2').modal({
														  			keyboard: true
														  		});
														  	}else{
														  		layer.msg("只有欠费状态才可调整应收金额！");
														  	}
													  }else{
														  layer.msg("该学生存在缴费记录，无法调整应收金额！");
													  }
												  }
												});
											
    							        
										*/},
										'click .see' : function(e,value, row, index) {
											$('#mymoda5').modal({
												keyboard: true
											});
											var sPkid = row.SPKID;
											var payItemPkid = row.PAY_ITEM_PKID;
//											getPayItemList(payItemPkid,sPkid);
											payRecord(payItemPkid,sPkid);
    							        
										},
										'click .seelog' : function(e,value, row, index) {
											$('#mymodalog').modal({
												keyboard: true
											});
											var Pkid = row.PKID;
											var PAY_ITEM_PKID = $("#payItemPkid").val();
											//var payItemPkid = row.PAY_ITEM_PKID;
//											getPayItemList(payItemPkid,sPkid);
											payRecordLOG(PAY_ITEM_PKID,Pkid);
    							        
										},
										'click .seejfjl' : function(e,value, row, index) {
											$('#mymodajfjl').modal({
												keyboard: true
											});
											var Pkid = row.PKID;
											var PAY_ITEM_PKID = $("#payItemPkid").val();
											//var payItemPkid = row.PAY_ITEM_PKID;
//											getPayItemList(payItemPkid,sPkid);
											/* $("#payRecordListJFJL").html("");*/
											
											/* var url = _basepath+"pay/getManeyJLlistPage.json";
										  	 var opt = {
													url :url,
												    silent: true,
												    query:{
												    	payItemPkid: payItemPkid,
														T_PAY_ITEM_LIST_PKID:Pkid
												    }
												  };
											 $("#payRecordListJFJL").bootstrapTable('refresh', opt);*/
											
											
											
											payRecordJFJL(PAY_ITEM_PKID,Pkid);
    							        
										},
										'click .msglog' : function(e,value, row, index) {
											
											var dialog = BootstrapDialog.show({
												title:'通知记录',
												message: $('<div></div>').load("pay/tosendMsgLog.json?payitemlistpkid="+row.PKID),
												closable: true,//是否显示叉号
												draggable: true,//可以拖拽
												 buttons: [{
													    label: '确定',
													    cssClass: 'btn-danger',
													    action: function(dialogRef){
													    	//
													    	pil.sendWxMsg();
													    	dialogRef.close();
										             }
												  }, {
												    label: '取消',
												    cssClass: 'btn-default',
												    action: function(dialogRef){
												       dialogRef.close();
												    }
												  }
												  ]
											
											});
    							        
										},
										'click .loan' : function(e,value, row, index) {
											$('#QFMONEY1').val((row.AMOUNTRECEIVABLE - row.AMOUNTCOLLECTION).toFixed(2));
											$('#DKMONEY').val('');
											$('#mymodal7').modal({
												keyboard: true
											});
											$("#STUDENT_PKID").val(row.SPKID);
											$("#payItemPkid").val(row.PAY_ITEM_PARENT_PKID);
										},
										'click .derate' : function(e,value, row, index) {
												$('#jmdjAMOUNTRECEIVABLE').val(row.AMOUNTRECEIVABLE);
												$('#jmdjTZHMONEY').val(row.AMOUNTRECEIVABLE);
												$('#xzpkid').val(row.PKID);
												$('#jmdjYHJE').val('');
												$('#jmdjYHSM').val('');
												$('#jmdjJBR').val('');
												$('#mymodal8').modal({
													keyboard: true
												});
											
											
										},
										'click .del' : function(e,value, row, index) {
											var pkid = row.PKID;
											
											BootstrapDialog.show({  //显示需要提交的表单。
								            	title:'提示信息',	
								            message: '你确定要删除这条记录吗？',
								            closable: true, 
								              buttons: [{
											    label: '确定',
											    cssClass: 'btn-danger',
											    action: function(dialogRef){
											    	deletePayItemList(pkid);
											    	dialogRef.close();
								                }
										  }, {
										    label: '取消',
										    cssClass: 'btn-default',
										    action: function(dialogRef){
										       dialogRef.close();
										    }
										  }
										  ]
								            });
											
											
    							        
										}

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
//删除缴费记录
var deletePayItemList = function(pkid){
	var url=_basepath+"pay/deletePayItemListObject.json";
	$.ajax({
		  type: 'POST',
		  url: url,
		  async: false,
		  data: {
			  pkid:pkid
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  layer.msg("删除成功!");
				  $("#payItemListTable").bootstrapTable('refresh');
			  }else{
				  layer.msg("删除失败，请重试!");
			  }
			  
			  return false;
		  }
		});
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
				  
//				  var moneyResult = '';
//				  if(result.payItemListObject.DISCOUNT_MODE == '1'){
//					  moneyResult = result.payItemListObject.DISCOUNT_MONEY +"元";
//				  }else if(result.payItemListObject.DISCOUNT_MODE == '2'){
//					  moneyResult = result.payItemListObject.DISCOUNT +"折";
//				  }
//				  $("#discountScopeObj").html(result.payItemListObject.DISCOUNT_SCOPE != null ? result.payItemListObject.DISCOUNT_SCOPE:""
//					  +" "
//					  + moneyResult);
				  $("#discountScopeObj").html(result.payItemListObject.DISCOUNTS != null ? result.payItemListObject.DISCOUNTS:"");
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
var payRecordJFJL = function(payItemPkid,Pkid){
	$("#payRecordListJFJL").bootstrapTable('destroy');
	var url = _basepath+"pay/getManeyJLlistPage.json";
	$('#payRecordListJFJL').bootstrapTable(
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
						},
						//行选
						onClickRow : function(row, tr,flied){
							var rowPayItemPKID = row.PKID;
			            	//设置批量按钮是否可点击
			            	pil.buttonStatus();
				        },
				        //全选
				        onCheckAll:function(rows){
				        	//设置批量按钮是否可点击
			            	pil.buttonStatus();
				        	
				        },
			            //点击每一个单选框时触发的操作
			            onCheck:function(row){
			            	var rowPayItemPKID = row.PKID;
			            	//设置批量按钮是否可点击
			            	pil.buttonStatus();
			            },
			            //取消每一个单选框时对应的操作；
			            onUncheck:function(row){
			            	var rowPayItemPKID = row.PKID;
			            	//设置批量按钮是否可点击
			            	pil.buttonStatus();
//			            	alert(pil.studentPkids)
			            },
			            //取消每一个单选框时对应的操作；
			            onUncheckAll:function(rows){
			            	//设置批量按钮是否可点击
			            	pil.buttonStatus();
			            },
						queryParams : function getParams(params) {
							var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
								limit : params.limit, //页面大小
								searchText : this.searchText,
								sortName : this.sortName,
								sortOrder : this.sortOrder,
								pageindex : this.pageNumber,
								payItemPkid: payItemPkid,
								T_PAY_ITEM_LIST_PKID:Pkid
								
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
									field: 'INPUT_OUTPUT',//可不加  
									title:'支付类型',
									align : "center",
									halign : 'center',
									formatter : function(value, row,index) {
										if(value == 'JX'){
											return '收入';
										}else if(value == 'XX'){
											return '支出';
										}
									}
								},
								{
									field: 'ORDERCREATE_MODE',//可不加  
									title:'支付渠道',
									align : "center",
									halign : 'center',
									formatter : function(value, row,index) {
										if(value == 'U'){
											return '线上';
										}else{
											return '线下';
										}
										
									}
								},
								{
									field : 'PAY_MODE',
									title:'支付方式',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,index) {
										if(value == 'CASH'){
											return '现金';
										}else if(value == 'CARD'){
											return '银行卡';
										}else if(value == 'WX'){
											return '微信';
										}else if(value == 'ZFB'){
											return '支付宝';
										}else if(value == 'TT'){
											return '电汇';
										}
										
									}
								},
								{
									field : 'MONEY',
									title:'金额',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,index) {
										return value.toFixed(2);
										
									}
								},
								{
									field : 'CZR',
									title:'收款人',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'CJSJ',
									title:'缴费时间',
									align : "center",
									halign : 'center',
									sortable : false
									
								}
						],
					});
    $('#payRecordListJFJL').bootstrapTable('hideColumn', 'PKID');//隱藏列

	
	
}
//点击f费用变动记录
var payRecordLOG = function(payItemPkid,Pkid){
	$("#payRecordListLOG").bootstrapTable('destroy');
	var url = _basepath+"pay/getManeyChangelistPage.json";
	$('#payRecordListLOG').bootstrapTable(
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
						},
						//行选
						onClickRow : function(row, tr,flied){
							var rowPayItemPKID = row.PKID;
			            	//设置批量按钮是否可点击
			            	pil.buttonStatus();
				        },
				        //全选
				        onCheckAll:function(rows){
				        	//设置批量按钮是否可点击
			            	pil.buttonStatus();
				        	
				        },
			            //点击每一个单选框时触发的操作
			            onCheck:function(row){
			            	var rowPayItemPKID = row.PKID;
			            	//设置批量按钮是否可点击
			            	pil.buttonStatus();
			            },
			            //取消每一个单选框时对应的操作；
			            onUncheck:function(row){
			            	var rowPayItemPKID = row.PKID;
			            	//设置批量按钮是否可点击
			            	pil.buttonStatus();
//			            	alert(pil.studentPkids)
			            },
			            //取消每一个单选框时对应的操作；
			            onUncheckAll:function(rows){
			            	//设置批量按钮是否可点击
			            	pil.buttonStatus();
			            },
						queryParams : function getParams(params) {
							var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
								limit : params.limit, //页面大小
								searchText : this.searchText,
								sortName : this.sortName,
								sortOrder : this.sortOrder,
								pageindex : this.pageNumber,
								payItemPkid: payItemPkid,
								T_PAY_ITEM_LIST_PKID:Pkid
								
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
									field: 'BDLX',//可不加  
									title:'变动类型',
									align : "center",
									halign : 'center',
									formatter : function(value, row,index) {
										if(value == '1'){
											return '减免';
										}else if(value == '2'){
											return '调整应收';
										}
									}
								},
								{
									field: 'YYSJE',//可不加  
									title:'变动前应收',
									align : "center",
									halign : 'center',
									formatter : function(value, row,
											index) {
										return value.toFixed(2);
										
									}
								},
								{
									field : 'TZHJE',
									title:'变动后应收',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										return value.toFixed(2);
										
									}
								},
								{
									field : 'CHAE',
									title:'差额',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										return value.toFixed(2);
										
									}
								},
								{
									field : 'YHSM',
									title:'说明',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'JBR',
									title:'经办人',
									align : "center",
									halign : 'center',
									sortable : false
									
								},
								{
									field : 'CCRNAME',
									title:'操作人',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'SJ',
									title:'操作时间',
									align : "center",
									halign : 'center',
									sortable : false
								}
						],
					});
    $('#payRecordListLOG').bootstrapTable('hideColumn', 'PKID');//隱藏列

	
	
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
						  
						  var record = '<tr id="rowRecord'+i+'" PAYITEM="'+recordDataList[i].PAYITEM+'"  PAYITEM_CHILD="'+recordDataList[i].PAYITEM_CHILD+'" onclick="javascript:seeRecordDetail(\''+recordDataList[i].PKID+'\',\''+recordDataList[i].ORDERCREATE_MODE+'\',this);">'+
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
						  record+= '<td>'+inOutPut+'</td>'+
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
								'<td>'+recordDataList[i].USERNAME+'</td>'+
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
	/*//pkid
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
		var discount_zhe = $("#discount_zhe").val();
		var regu =  /^[1-9]+[1-9]*]*$/;
    	if( !regu.test(discount_zhe)){
    		layer.msg("打折范围为1-9之间正整数！");
    		return false;
    	}
	}else if(discountMode == 2){//直减
		discount = '直减 -'+$("#discount_jian").val();
		discountVal = true;
		if(Number($("#discount_jian").val()) > Number(cost_new)){
			layer.msg("优惠金额不能大于费用金额！");
			return false;
		}
	}else{
		layer.msg("请先选择优惠方式!");
		return false;
	}*/
	/*if(discountVal == 1 && $("#discount_zhe").val() == '' && $("#discount_jian").val() == ''){
		layer.msg("调整后的优惠方式不能为空！");
		return false;
	}*/
	
	
	//调整后费用
	var TZHMONEY = $("#TZHMONEY").val();
	//原费用
	var AMOUNTRECEIVABLE = $("#AMOUNTRECEIVABLE").val();
	//T_PAY_ITEM_LIST_PKID
	var T_PAY_ITEM_LIST_PKID=$("#yspkid").val();
	if(TZHMONEY == ''){
		layer.msg("调整后的费用不能为空！");
		return false;
	}
	if(Number(TZHMONEY.replace(/,/g, ""))<0){
		layer.msg("调整后的费用不能小于0！");
		return false;
		}
	
	
	var url=_basepath+"pay/updatePayItemListTZ.json";
	$.ajax({
		  type: 'POST',
		  url: url,
		  async: false,
		  data: {
			T_PAY_ITEM_LIST_PKID:T_PAY_ITEM_LIST_PKID,
			AMOUNTRECEIVABLE:AMOUNTRECEIVABLE,
			TZHMONEY:TZHMONEY
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  layer.msg("保存成功!");
				  $("#btn_cancel2").click();
				  $("#payItemListTable").bootstrapTable('refresh');
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
//添加待缴学生名单列表
/*$("#btn_import_add").click(function(){
	$("#studentConditionSelect").val('');
    $('#mymoda6').modal({
		keyboard: true
	});
    var url = _basepath+"pay/getStudentPaylistPage.json";
  	 var opt = {
			url :url,
		    silent: true,
		    query:{
		    	payItemPkid:$("#payItemPkid").val(),
		    	conditions:''
		    }
		  };
	 $("#studentListTable").bootstrapTable('refresh', opt);
});
*/

//添加待缴学生名单列表 -- 点击添加
$("#btn_save_add").click(function(){
	//禁止点击多次
	if(!pil.isFlag){
		return false;
	}
	var ids = pil.getIdSelections()+"";
	pil.isFlag = false;
	$.ajax({
		  type: 'POST',
		  url: _basepath+"pay/savePayItemList.json",
		  async: false,
		  data: {
			payItemPkid:$("#payItemPkid").val(),
			studentPkids:ids//pil.studentPkids
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  layer.msg("添加成功！");
				  $("#btn_cancel3").click();
				  $("#payItemListTable").bootstrapTable('refresh');
			  }
			  pil.isFlag = true;
			  return false;
		  }
		});
	pil.isFlag = true;
});

//添加待缴学生名单列表 -- 点击导出
$("#btn_export").click(function(){
	//导出按钮是否显示
    //页面展示字段拼接
	var resultColumnList = $('#payItemListTable').bootstrapTable('getVisibleColumns');
	var resultColumns = "";			
	$.each(resultColumnList, function(i, column) {
		if(column.field=='0'||column.field=='opt'){
			return;
		}else{
			resultColumns+=","+column.field;
		}									
 	});
	resultColumns = resultColumns.substr(1);
	//项目pkid
	var payItemPkid=$("#payItemPkid").val();
	//查询条件-缴费状态
	var status = $("#statusselect  option:selected").val();
	//查询条件-入学年份
	var grade = $("#gradeselect  option:selected").val();
	//查询条件-所属组织
	var DEPARTMENT_PKID = $("#orgtree").val();
	//查询条件-姓名/学号/身份证号
	var conditions = $("#conditionsselect").val();
	var mdruxuenianfen = $("#mdruxuenianfen  option:selected").val();
	var mdbanxing = $("#mdbanxing  option:selected").val();
	
	window.location.href=encodeURI(_basepath+'pay/exportStudentPayList.json?payItemPkid='+payItemPkid+
			'&STATUS='+status+'&grade='+grade+'&departmentPkids='+DEPARTMENT_PKID+
			'&conditions='+conditions+'&RXNIANFEN_PKID='+mdruxuenianfen+'&BANJI_TYPE_PKID='+mdbanxing);
	
});

//查询
$('#btn_search').click(function(){
	//项目pkid 缴费名单搜索功能 add by kangcl
	var payitempkid=$("#payItemPkid").val();
	//查询条件-缴费状态
	var status = $("#statusselect  option:selected").val();
	//查询条件-入学年份
	var grade = $("#gradeselect  option:selected").val();
	//查询条件-所属组织
	var DEPARTMENT_PKID = $("#orgtree").val();
	//查询条件-姓名/学号/身份证号
	var conditions = $("#conditionsselect").val();
	var mdruxuenianfen = $("#mdruxuenianfen  option:selected").val();
	var mdbanxing = $("#mdbanxing  option:selected").val();
	
	
	var url = _basepath+"pay/payItemListTable.json";
   	 var opt = {
			url :url,
		    silent: true,
		    query:{
		    	"STATUS":status,
		    	"grade":grade,
		    	"RXNIANFEN_PKID":mdruxuenianfen,
		    	"BANJI_TYPE_PKID":mdbanxing,
		    	"DEPARTMENT_PKID":DEPARTMENT_PKID,
		    	"conditions":conditions,
		    	"payItemPkid":payitempkid,
				showStatus:'012'
		    	
		    }
		  };
	 $("#payItemListTable").bootstrapTable('refresh', opt);
   
});
//获取选中的行
pil.getIdSelections=function () {
		var $table = $('#addListtable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.PKID;
    });
};
//添加缴费名单
$("#add_list").click(function(){
	var T_PAY_ITEM_PKID = $("#payItemPkid").val();
	BootstrapDialog.show({
		title:'添加缴费名单',
		message: $('<div></div>').load("pay/addItemList.json?item_pkid="+T_PAY_ITEM_PKID),
		closable: true,//是否显示叉号
		draggable: true,//可以拖拽
		 buttons: [{
			    label: '确定',
			    cssClass: 'btn-danger',
			    action: function(dialogRef){
			    	var PKIDS = pil.getIdSelections();
			    	var STUDENT_BM_PKIDs = "";
			    	for(var i=0;i<PKIDS.length;i++){
			    		STUDENT_BM_PKIDs = STUDENT_BM_PKIDs+PKIDS[i]+",";
			    	}
			    	if(STUDENT_BM_PKIDs==""){
			    		layer.msg("请先勾选学生！");
			    		return false;
			    	}
			    	var item_pkid = $("#item_pkid").val();
			    	$.ajax({
			  		  type: 'POST',
			  		  url: _basepath+"pay/saveAddItemList.json",
			  		  async: false,
			  		  data: {
			  			STUDENT_BM_PKIDs:STUDENT_BM_PKIDs,
			  			T_PAY_ITEM_PKID:item_pkid
			  		  },
			  		  dataType : "json",
			  		  success : function(result) {
			  			  if(result.result == 'success'){
			  				  layer.msg("添加成功！");
			  				  $("#btn_cancel3").click();
			  				  $("#payItemListTable").bootstrapTable('refresh');
			  			  }
			  			  pil.isFlag = true;
			  			  return false;
			  		  }
			  		});
			    	dialogRef.close();
             }
		  }, {
		    label: '取消',
		    cssClass: 'btn-default',
		    action: function(dialogRef){
		       dialogRef.close();
		    }
		  }
		  ]
	
	});
});



pil.batchshanchu = function (ids){
	$.post(
			_basepath+"pay/deletePayItemList.json",
			{"pkids":ids},
			function(result){
				 if(result.result == 'success'){
	   				  if(result.sfz!=''){
	   					  layer.msg("身份证号为"+result.sfz+"删除失败,订单已存在!");
	   					  $("#payItemListTable").bootstrapTable('refresh'); 
	   				  }else{
	   					  layer.msg("删除成功!");
	   					  $("#payItemListTable").bootstrapTable('refresh');
	   				  }
	   				  
	   			  }else if(result.result == 'false'){
	   				  layer.msg("删除失败，请重试!");
	   			  }
			}
		); 
 };


//删除名单
$('#delete_list').click(function(){
	
	
	var ids = pil.getPayItemListPkidsSelections()+"";
	//var url=_basepath+"pay/deletePayItemList.json";
	
	 BootstrapDialog.show({  //显示需要提交的表单。
       	title:'提示信息',	
       message: '你确定要删除吗？',
       closable: false, 
         buttons: [{
			    label: '确定',
			    cssClass: 'btn-danger',
			    action: function(dialogRef){
			    	pil.batchshanchu(ids);
			    	dialogRef.close();
			    	$("#payItemListTable").bootstrapTable('refresh');
           }
		  }, {
		    label: '关闭',
		    cssClass: 'btn-default',
		    action: function(dialogRef){
		       dialogRef.close();
		    }
		  }
		  ]
       });
	
	/*var ids = pil.getPayItemListPkidsSelections()+"";
	var url=_basepath+"pay/deletePayItemList.json";*/
	/*BootstrapDialog.show({  //显示需要提交的表单。
    	title:'提示信息',	
        message: '你确定要删除记录吗？',
        closable: false, 
      buttons: [{
	    label: '确定',
	    cssClass: 'btn-danger',
	    action: function(dialogRef){
	    	var url=_basepath+"pay/deletePayItemList.json";
	    	 $.ajax({
	   		  type: 'POST',
	   		  url: url,
	   		  async: false,
	   		  data: {
	   			  pkids:ids
	   		  },
	   		  dataType : "json",
	   		  success : function(result) {
	   			  if(result.result == 'success'){
	   				  if(result.sfz!=''){
	   					  layer.msg("身份证号为"+sfz+"删除失败,订单表已存在!");
	   					  $("#payItemListTable").bootstrapTable('refresh'); 
	   				  }else{
	   					  layer.msg("删除成功!");
	   					  $("#payItemListTable").bootstrapTable('refresh');
	   				  }
	   				  
	   			  }else if(result.result == 'false'){
	   				  layer.msg("删除失败，请重试!");
	   			  }
	   			  
	   			  return false;
	   		  }
	   		
	    	 });
	    	
	    	
        dialogRef.close();
        }
  }, {
    label: '取消',
    cssClass: 'btn-default',
    action: function(dialogRef){
       dialogRef.close();
    }
  }
  ]
    });*/
	
	
    


	
	
	
	
	
	
	
	/*var ids = pil.getPayItemListPkidsSelections()+"";
	var url=_basepath+"pay/deletePayItemList.json";
	$.ajax({
		  type: 'POST',
		  url: url,
		  async: false,
		  data: {
			  pkids:ids
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  if(result.sfz!=''){
					  layer.msg("身份证号为"+sfz+"删除失败,订单表已存在!");
					  $("#payItemListTable").bootstrapTable('refresh'); 
				  }else{
					  layer.msg("删除成功!");
					  $("#payItemListTable").bootstrapTable('refresh');
				  }
				  
			  }else if(result.result == 'false'){
				  layer.msg("删除失败，请重试!");
			  }
			  
			  return false;
		  }
		});
	
	
	
	 $("#payItemListTable").bootstrapTable('refresh');*/
//	//项目pkid 缴费名单搜索功能 add by kangcl
//	var payitempkid=$("#payItemPkid").val();
//	//查询条件-缴费状态
//	var status = $("#statusselect  option:selected").val();
//	//查询条件-入学年份
//	var grade = $("#gradeselect  option:selected").val();
//	//查询条件-所属组织
//	var DEPARTMENT_PKID = $("#orgtree").val();
//	//查询条件-姓名/学号/身份证号
//	var conditions = $("#conditionsselect").val();
//	var mdruxuenianfen = $("#mdruxuenianfen  option:selected").val();
//	var mdbanxing = $("#mdbanxing  option:selected").val();
//	
//	
//	var url = _basepath+"pay/payItemListTable.json";
//   	 var opt = {
//			url :url,
//		    silent: true,
//		    query:{
//		    	"STATUS":status,
//		    	"grade":grade,
//		    	"RXNIANFEN_PKID":mdruxuenianfen,
//		    	"BANJI_TYPE_PKID":mdbanxing,
//		    	"DEPARTMENT_PKID":DEPARTMENT_PKID,
//		    	"conditions":conditions,
//		    	"payItemPkid":payitempkid,
//				showStatus:'012'
//		    	
//		    }
//		  };
	
   
});































































//返回
$('#btn_reply').click(function(){
	$('.jf_szright').load(_basepath+"pay/paymanage.php");
});
//学生查询
/*$("#btn_search_student").click(function(){
	//alert("33");
	//查询条件-姓名/学号/身份证号
	var conditions = $("#studentConditionSelect").val();
	var url = _basepath+"pay/getStudentPaylistPage.json";
  	 var opt = {
			url :url,
		    silent: true,
		    query:{
		    	"conditions":conditions,
		    	"payItemPkid":$("#payItemPkid").val()
		    	
		    }
		  };
	 $("#studentListTable").bootstrapTable('refresh', opt);
});*/
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
		$("#amountreceivable_new").val(result.toFixed(2));
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
			$("#amountreceivable_new").val(result.toFixed(2));
		}
		
		
	}); 
	
	//计算应收金额结束..........
	pil.getTab();
	//payRecordJFJL("","");
	/*pil.getStudentPayTab();*/
	//导出按钮是否显示
	pil.buttonStatuss();
	
	
	//隐藏专业树
	$("#menuContent").css("display","none");
	
	
	/**
	 * 弹出 点击 导入已缴费名单
	 */
	pil.openImportPaidlist = function(payitem){
		var dialog = BootstrapDialog.show({
			title:'导入已缴名单',
			message: $('<div></div>').load("import/importpaidlist.json?item_pkid="+payitem),
			closable: false,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
	            label: '关闭',
	            cssClass: 'btn-danger',
	            action: function(dialog) {
	            	dialog.close();
	            	refreshPayItemListTable();
	            }
	        }]
		});
		
	};
	$("#btn_import_already_list").click(function(){
		pil.openImportPaidlist($("#payItemPkid").val());
	});
	
	$("#btn_import_list").click(function(){
		pil.openImportlist(payitempkid);
	});
	
	pil.openImportlist = function(item_pkid){
		
//		if("undefined" == typeof item_pkid){
//			item_pkid = "";
//		}
		
		var dialog = BootstrapDialog.show({
			title:'导入缴费名单',
			message: $('<div></div>').load("import/import-list.json?item_pkid="+item_pkid),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
	            label: '关闭',
	            cssClass: 'btn-danger',
	            action: function(dialog) {
	            	dialog.close();
//	           	 	$("#payItemListTable").bootstrapTable('destroy');
//	           	 	//pil.getTab();
//	           	 pil.getTab();
//	         	pil.getStudentPayTab();
//	         	//导出按钮是否显示
//	         	pil.buttonStatuss();
	            	
//	            	$(".jf_szright").load(_basepath + "pay/payItemListTable.php");
//	            	$('#payItemListTable').bootstrapTable("refresh");
//	            	$("#payItemListTable").bootstrapTable('destroy');
	            	refreshPayItemListTable();
	            	
	            }
	        }]
		
		});
		
	};
	//贷款/缓交登记保存按钮
	$("#btn_dkhjdj").click(function(){
		var LOAN = $("#DKMONEY").val();
		var STUDENT_PKID = $("#STUDENT_PKID").val();
		var PAY_ITEM_PKID = $("#payItemPkid").val();
		var reg = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/;
		if(LOAN=="" || LOAN<=0 ){
			layer.msg("请输入正确的贷款/缓交金额！");
			return false;
		}
		if(LOAN>=100000){
			layer.msg("贷款/缓交金额不能大于99999.99！");
			return false;
		}
		if(LOAN.match(reg) == null){
			layer.msg("贷款/缓交金额最多只能保留两位小数！");
			return false;
		}
		$.ajax({
			  type: 'POST',
			  url: _basepath+"importloan/updateStuPayLoan.json",
			  async: false,
			  data: {
				  LOAN:LOAN,
				  STUDENT_PKID:STUDENT_PKID,
				  PAY_ITEM_PKID:PAY_ITEM_PKID
			  },
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  layer.msg("登记成功！");
					  $("#btn_cancel7").click();
					  $("#payItemListTable").bootstrapTable('refresh');
				  }else{
					  layer.msg("登记失败，请重试！");
				  }
				  return false;
			  }
		});
		return false;
	});
	//减免登记保存按钮
	$("#btn_jmdj").click(function(){
		/*var DERATE = $("#JMMONEY").val();
		var FEIYONG = $("#FEIYONG").val();
		var SHENFENZHENGHAO = $("#SHENFENZHENGHAO").val();
		var PAY_ITEM_PKID = $("#payItemPkid").val();
		var reg = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/;
		if(DERATE=="" || Number(DERATE)<=0){
			layer.msg("减免金额不合法！");
			return false;
		}
		if(Number(DERATE)>Number(FEIYONG)){
			layer.msg("减免金额不能大于当前费用！");
			return false;
		}
		if(DERATE.match(reg) == null){
			layer.msg("减免金额最多只能保留两位小数！");
			return false;
		}*/
		
		//调整后费用
		var jmdjTZHMONEY = $("#jmdjTZHMONEY").val();
		//原费用
		var jmdjAMOUNTRECEIVABLE = $("#jmdjAMOUNTRECEIVABLE").val();
		//优惠金额
		var jmdjYHJE = $("#jmdjYHJE").val();
		//优惠说明
		var jmdjYHSM = $("#jmdjYHSM").val();
		//经办人
		var jmdjJBR = $("#jmdjJBR").val();
		//T_PAY_ITEM_LIST_PKID
		var T_PAY_ITEM_LIST_PKID=$("#xzpkid").val();
		if(T_PAY_ITEM_LIST_PKID=='' || T_PAY_ITEM_LIST_PKID==null || T_PAY_ITEM_LIST_PKID=='null' || T_PAY_ITEM_LIST_PKID=='undifind'){
			layer.msg("请选中需要修改的条数！");
			return false;
		}
		
		
		
		
		if(jmdjTZHMONEY == ''){
			layer.msg("调整后的费用不能为空！");
			return false;
		}
		if(jmdjYHJE == ''){
			layer.msg("优惠金额不能为空！");
			return false;
		}
		if(jmdjYHSM == ''){
			layer.msg("优惠说明不能为空！");
			return false;
		}
		if(jmdjJBR == ''){
			layer.msg("经办人不能为空！");
			return false;
		}
		if(Number(jmdjTZHMONEY.replace(/,/g, ""))<0){
			layer.msg("调整后的费用不能小于0！");
			return false;
			}
		
		//var T_PAY_ITEM_LIST_PKID=pil.getPayItemListPkidsSelections()[0];
		console.log(T_PAY_ITEM_LIST_PKID);
		
		$.ajax({
			  type: 'POST',
			  url: _basepath+"pay/updatePayItemListJM.json",
			  async: false,
			  data: {
				  jmdjTZHMONEY:jmdjTZHMONEY,
				  jmdjAMOUNTRECEIVABLE:jmdjAMOUNTRECEIVABLE,
				  jmdjYHJE:jmdjYHJE,
				  jmdjYHSM:jmdjYHSM,
				  jmdjJBR:jmdjJBR,
				  T_PAY_ITEM_LIST_PKID:T_PAY_ITEM_LIST_PKID
			  },
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  layer.msg("登记成功！");
					  $("#btn_cancel8").click();
					  $("#payItemListTable").bootstrapTable('refresh');
				  }else if(result.result == 'error'){
					  layer.msg("该学生已有缴费记录！");
				  }else{
					  layer.msg("登记失败，请重试！");
				  }
				  return false;
			  }
		});
		return false;
	});
});
//刷新table
var refreshPayItemListTable = function(){
	var payitempkid=$("#payItemPkid").val();
	//查询条件-缴费状态
	var status = $("#statusselect  option:selected").val();
	//查询条件-入学年份
	var grade = $("#gradeselect  option:selected").val();
	//查询条件-所属组织
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
				"showStatus":'012'
		    	
		    }
		  };
	 $("#payItemListTable").bootstrapTable('refresh', opt);
};
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
