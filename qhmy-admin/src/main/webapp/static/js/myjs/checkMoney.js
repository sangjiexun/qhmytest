/**
 * 核账页面对象
 */
//@ sourceURL=checkMoney.js
(function($, window) {
	var checkMoney = {};
	
	//日期插件参数设置
	laydate.render({
		elem:'#checkDate',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		//选择的日期
		var checkDate=$('#checkDate').val();
		//核验状态
		var checkStatus=$('#checkStatus').val();
		//收款人
		var receiver = $('#receiver').val();
		checkMoney.updateTotalMsg(checkDate,checkStatus,receiver);
		$("#checkMoneyTable").bootstrapTable('refresh', {url: encodeURI(_basepath+"checkMoney/checkMoneyTable.json?CHECKDATE="+checkDate+"&&CHECKSTATUS="+checkStatus+"&&RECEIVER="+receiver)});
		
	});
	
	//获取选中的行
	checkMoney.getPkidSelections=function () {
			var $table = $('#checkMoneyTable');
	    	return $.map($table.bootstrapTable('getSelections'), function(row) {
	        return row.PKID;
	    });
	};
	
	//点击批量验收
	$("#checkBatch").click(function(){
		if(checkMoney.getPkidSelections() == ''){
			layer.msg('请至少选择一条未验的记录！');
			return false;
		}
		BootstrapDialog.show({  
		 	 	title: '提示信息',
	            message: '确定验收吗？',
	            size :BootstrapDialog.SIZE_NORMAL,
	            closable: true, 
	              buttons: [{
				    label: '确定',
				    cssClass: 'btn btn-danger',
				    action: function(dialogRef){
		                    dialogRef.close();
		                    var url = _basepath+"checkMoney/updateCheckedBatch.json";
		             		$.ajax({
		             			type : 'POST',
		             			url : url,
		             			async : false,
		             			data : {
		             				PKIDS : checkMoney.getPkidSelections()+""
		             			},
		             			dataType : "json",
		             			success : function(data) {
		             				if(data.result=='success'){
		             					layer.msg('验收成功',{skin : 0,offset: [($(window).height())/8, ($(window).width())/2.2]});
		             					//选择的日期
		             					var checkDate=$('#checkDate').val();
		             					//核验状态
		             					var checkStatus=$('#checkStatus').val();
		             					//收款人
		             					var receiver = $('#receiver').val();
		             					checkMoney.updateTotalMsg(checkDate,checkStatus,receiver);
		             					$("#checkMoneyTable").bootstrapTable('refresh', {url: encodeURI(_basepath+"checkMoney/checkMoneyTable.json?CHECKDATE="+checkDate+"CHECKSTATUS="+checkStatus+"RECEIVER="+receiver)});
		             				}else{
		             					layer.msg('验收失败，请重试');
		             					return false;
		             				}
		             			}
		             		});
			  	}
	              },{
				    label: '取消',
				    cssClass: 'btn-default',
				    action: function(dialogRef){
				       dialogRef.close();
				    }
	              }]
	    });
	});
	
	//点击按钮打印
	$("#printOut").click(function(){
		$("#batchPrintArea2").html('');
		var htmlHead = '<div><h3 style="text-align: center;margin:7px 0px 7px 0px;">核账</h3></div>';
		htmlHead += '<table class="table table-bordered sf_table" id="" style="margin-top:15px;">';
		htmlHead += '<tr>'+
					'<th style="text-align: center">结账单号</th>'+
					'<th style="text-align: center">收款人</th>'+
					'<th style="text-align: center">结算时间</th>'+
					'<th style="text-align: center">结算金额</th>'+
					'<th style="text-align: center">现金</th>'+
					'<th style="text-align: center">银行卡</th>'+
					'<th style="text-align: center">电汇</th>'+
					'<th style="text-align: center">微信</th>'+
					'<th style="text-align: center">支付宝</th>'+
//					'<th style="text-align: center">收费金额</th>'+
//					'<th style="text-align: center">退费金额</th>'+
					'<th style="text-align: center">核验状态</th>'+
				'</tr>';
		//选择的日期
		var checkDate=$('#checkDate').val();
		//核验状态
		var checkStatus=$('#checkStatus').val();
		//收款人
		var receiver = $('#receiver').val();
		
		var url=_basepath+'checkMoney/checkMoneyPrint.json';
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  data: {
				  CHECKDATE : checkDate,
				  CHECKSTATUS : checkStatus,
				  RECEIVER : receiver
			  },
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  var htmlArray = new Array();
					  
					  var amountMsg = result.amountMsg;
					  var amountPeople = result.amountPeople;
						
					  var list = result.checkMoneyList;
					  for (var i = 0; i < list.length; i++) {
						  var data = list[i];
						  htmlArray.push('<tr>'+
								  	'<td>'+(data.CHECKDAY_NO == "" || data.CHECKDAY_NO == null || data.CHECKDAY_NO == 'null' ? "":data.CHECKDAY_NO)+'</td>'+
								  	'<td>'+(data.RECEIVER == "" || data.RECEIVER == null || data.RECEIVER == 'null' ? "":data.RECEIVER)+'</td>'+
								  	'<td>'+(data.CHECKDAY_DATE == "" || data.CHECKDAY_DATE == null || data.CHECKDAY_DATE == 'null' ? "":data.CHECKDAY_DATE)+'</td>'+
								  	'<td>'+(data.TOTALCOST == "" || data.TOTALCOST == null || data.TOTALCOST == 'null' ? "0":data.TOTALCOST)+'</td>'+
								  	'<td>'+(data.CASHMONEY == "" || data.CASHMONEY == null || data.CASHMONEY == 'null' ? "0":data.CASHMONEY)+'</td>'+
								  	'<td>'+(data.CARDMONEY == "" || data.CARDMONEY == null || data.CARDMONEY == 'null' ? "0":data.CARDMONEY)+'</td>'+
								  	'<td>'+(data.TTMONEY == "" || data.TTMONEY == null || data.TTMONEY == 'null' ? "0":data.TTMONEY)+'</td>'+
								  	'<td>'+(data.WXMONEY == "" || data.WXMONEY == null || data.WXMONEY == 'null' ? "0":data.WXMONEY)+'</td>'+
								  	'<td>'+(data.ZFBMONEY == "" || data.ZFBMONEY == null || data.ZFBMONEY == 'null' ? "0":data.ZFBMONEY)+'</td>'+
//								  	'<td>'+(data.TOTALMONEYSHOU == "" || data.TOTALMONEYSHOU == null || data.TOTALMONEYSHOU == 'null' ? "0":data.TOTALMONEYSHOU)+'</td>'+
//								  	'<td>'+(data.TOTALMONEYTUI == "" || data.TOTALMONEYTUI == null || data.TOTALMONEYTUI == 'null' ? "":data.TOTALMONEYTUI)+'</td>'+
								  	'<td>'+(data.STATUS == "" || data.STATUS == null || data.STATUS == 'null' ? "":data.STATUS)+'</td>'+
								'</tr>');
					  }
					  htmlArray.push('<tr>'+
							  	'<td  colspan="11"  align="left">收款人数：'+amountPeople.TOTALPEOPLE+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;结算总金额：'+amountMsg.TOTALALL
							  	+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;现金：'+amountMsg.TOTALCASH
							  	+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;银行卡：'+amountMsg.TOTALCARD
							  	+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;电汇：'+amountMsg.TOTALTT
							  	+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;支付宝：'+amountMsg.TOTALZFB
							  	+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;微信：'+amountMsg.TOTALWX
//							  	+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收费总金额：'+amountMsg.TOTALMONEYSHOU
//							  	+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;退费总金额：'+amountMsg.TOTALMONEYTUI
							  	+'</td>'+
							'</tr>');
					  $('#batchPrintArea2').html(htmlHead + htmlArray.join('') + '</table>');
					  $('#batchPrintArea').printArea();
				  }else{
					  layer.msg("打印失败，请重试!");
				  }
				  return false;
			  }
			});
	});
	
	//更新汇总信息
	checkMoney.updateTotalMsg = function(checkDate,checkStatus,receiver){
		var url = _basepath+"checkMoney/getAmountMsg.json";
		$.ajax({
			type : 'POST',
			url : url,
			async : false,
			data : {
				CHECKDATE : checkDate,
				CHECKSTATUS : checkStatus,
				RECEIVER : receiver
			},
			dataType : "json",
			success : function(result) {
				var amountMsg = result.amountMsg;
				var amountPeople = result.amountPeople;
				$("#TOTALPEOPLE").html("收费人数：<a style='color: #f0ad4e !important;'>"+amountPeople.TOTALPEOPLE+"</a>"); 
				$("#TOTALALL").html("结算总金额：<a style='color: #f0ad4e !important;'>"+amountMsg.TOTALALL+"</a>"); 
				$("#TOTALCASH").html("现金：<a style='color: #f0ad4e !important;'>"+amountMsg.TOTALCASH+"</a>"); 
				$("#TOTALCARD").html("银行卡：<a style='color: #f0ad4e !important;'>"+amountMsg.TOTALCARD+"</a>"); 
				$("#TOTALTT").html("电汇：<a style='color: #f0ad4e !important;'>"+amountMsg.TOTALTT+"</a>"); 
				$("#TOTALZFB").html("支付宝：<a style='color: #f0ad4e !important;'>"+amountMsg.TOTALZFB+"</a>"); 
				$("#TOTALWX").html("微信：<a style='color: #f0ad4e !important;'>"+amountMsg.TOTALWX+"</a>");
//				$("#TOTALMONEYSHOU").html("收费总金额：<a style='color: #f0ad4e !important;'>"+amountMsg.TOTALMONEYSHOU+"</a>");
//				$("#TOTALMONEYTUI").html("退费总金额：<a style='color: #f0ad4e !important;'>"+amountMsg.TOTALMONEYTUI+"</a>");
			}
		});
	};
	
	//更改为验收状态
	checkMoney.updateChecked = function(PKID){
		BootstrapDialog.show({  
		 	 	title: '提示信息',
	            message: '确定验收吗？',
	            size :BootstrapDialog.SIZE_NORMAL,
	            closable: true, 
	              buttons: [{
				    label: '确定',
				    cssClass: 'btn btn-danger',
				    action: function(dialogRef){
		                    dialogRef.close();
		                    var url = _basepath+"checkMoney/updateChecked.json";
		             		$.ajax({
		             			type : 'POST',
		             			url : url,
		             			async : false,
		             			data : {
		             				PKID : PKID
		             			},
		             			dataType : "json",
		             			success : function(data) {
		             				if(data.result=='success'){
		             					layer.msg('验收成功',{skin : 0,offset: [($(window).height())/8, ($(window).width())/2.2]});
		             					//选择的日期
		             					var checkDate=$('#checkDate').val();
		             					//核验状态
		             					var checkStatus=$('#checkStatus').val();
		             					//收款人
		             					var receiver = $('#receiver').val();
		             					checkMoney.updateTotalMsg(checkDate,checkStatus,receiver);
		             					$("#checkMoneyTable").bootstrapTable('refresh', {url: encodeURI(_basepath+"checkMoney/checkMoneyTable.json?CHECKDATE="+checkDate+"CHECKSTATUS="+checkStatus+"RECEIVER="+receiver)});
		             				}
		             			}
		             		});
			  	}
	              },{
				    label: '取消',
				    cssClass: 'btn-default',
				    action: function(dialogRef){
				       dialogRef.close();
				    }
	              }]
	    });
	};
	
	// 获取bootStrapTable表格数据,及参数设置
	checkMoney.getTab = function() {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		var url = _basepath + "checkMoney/checkMoneyTable.json";
		$('#checkMoneyTable').bootstrapTable(
						{
							url : url,// 数据源
							dataField : "rows",// 服务端返回数据键值
							// 就是说记录放的键值是rows，分页时使用总记录数的键值为total
							totalField : 'total',
							sortOrder : 'desc',
							striped : true, // 表格显示条纹
							pagination : true, // 启动分页
							pageNumber : 1, // 当前第几页
							pageSize : 20, // 每页显示的记录数
							pageList : [ 1, 5, 10, 20, 30, 50, 100 ], // 记录数可选列表
							search : false, // 是否启用查询
							formatSearch : function() {
								return '请输入简拼.编码.企业名称查询';
							},// 搜索框提示文字
							searchAlign : 'left',
							buttonsAlign : 'left',
							toolbarAlign : 'left',
							searchOnEnterKey : false,// true为按回车触发搜索事件
							showColumns : false, // 显示下拉框勾选要显示的列
							showRefresh : false, // 显示刷新按钮 --只是前台刷新，个人觉得没什么用
							minimumCountColumns : 2, // 最少允许的列数
							sidePagination : "server", // 表示服务端请求
							totalRows : 0, // server side need to set
							singleSelect : false,
							clickToSelect : true,
							onDblClickRow : function(row) {
							},
							queryParams : function getParams(params) {
								//收款人
								var receiver = $('#receiver').val();
								//选择的日期
								var checkDate=$('#checkDate').val();
								//核验状态
								var checkStatus=$('#checkStatus').val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									RECEIVER : receiver,
									CHECKDATE : checkDate,
									CHECKSTATUS : checkStatus,
									sortName : this.sortName,
									sortOrder : this.sortOrder,
									pageindex : this.pageNumber
								// 当前页码
								};
								return temp;
							},
							buttonsAlign : "right",// 按钮对齐方式
							selectItemName : 'id',
							toolbar : "#toolbar",
							toolbarAlign : 'left',
							columns : [{
						                checkbox: true,
						                formatter : function(value, row,
												index) {
						                	if(row.STATUS!='未验'){
						                		return {
													 disabled : true
													   };
						                	}
										}
						            },
									{
										field : 'CHECKDAY_NO',
										title : '结账单号',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'RECEIVER',
										title : '收款人',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'CHECKDAY_DATE',
										title : '结算时间',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'TOTALCOST',
										title : '结算金额',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'CASHMONEY',
										title : '现金',
										align : "center",
										halign : 'center',
										sortable : false
									},

									{
										field : 'CARDMONEY',
										title : '银行卡',
										align : "center",
										halign : 'center',
										sortable : false

									},
									{
										field : 'TTMONEY',
										title : '电汇',
										align : "center",
										halign : 'center',
										sortable : false

									},
									{
										field : 'WXMONEY',
										title : '微信',
										align : "center",
										halign : 'center',
										sortable : false

									},
									{
										field : 'ZFBMONEY',
										title : '支付宝',
										align : "center",
										halign : 'center',
										sortable : false

									},
//									{
//										field : 'TOTALMONEYSHOU',
//										title : '收费金额',
//										align : "center",
//										halign : 'center',
//										sortable : false
//
//									},
//									{
//										field : 'TOTALMONEYTUI',
//										title : '退费金额',
//										align : "center",
//										halign : 'center',
//										sortable : false,
//										formatter : function(value, row, index) {
//											if(value == '0'){
//												return "";
//											}else{
//												return value;
//											}
//										}
//
//									},
									{
										field : 'STATUS',
										title : '核验状态',
										align : "center",
										halign : 'center',
										sortable : false

									},

									{
										field : 'opt',
										title : '操作',
										align : "center",
										halign : 'center',
										formatter : function(value, row, index) {
											if(row.STATUS=='未验'){
												return [
														'<span title="明细" class="fa fa-bars detail" style=" cursor:pointer;"></span>&nbsp;&nbsp;',
														SESSION_MENU_BUTTONS.hz_ys == 1?'<span title="验收" class="fa fa-magic checkAccept" style=" cursor:pointer;"></span>':'']
																.join('');
											}else{
												return [
												        '<span title="明细" class="fa fa-bars detail" style=" cursor:pointer;"></span>']
												.join('');
											}
										}, // 紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										events : {
											'click .detail' : function(e,value, row, index) {
												//明细注册事件，去明细页面
												$(".jf_szright").load(_basepath+ 'checkMoney/goCheckMoneyDetail.json?PKID='+ row.PKID);
											},
											'click .checkAccept' : function(e,value, row, index) {
												//验收注册事件,更改为已验收
												checkMoney.updateChecked(row.PKID);
											}
										}
									} ],
						});
	};
	//加载表格数据
	checkMoney.getTab();
})(jQuery, window);
