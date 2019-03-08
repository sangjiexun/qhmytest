//@ sourceURL=offlinePay.js
//线下缴费页面js
var offlinePay={};

/*
 * 此属性决定收据打印的模板
 * CAIJING 财经学院
 * LIGONG  理工学院
 * GUANGDIAN 光电
 */
offlinePay.fromSchool = $("#COLLEGES_NAME_EN").val();

offlinePay.payItemPkid = '';
offlinePay.userPkid = '';
offlinePay.studentPkid = '';
offlinePay.studentBmPkid = '';
offlinePay.studentBxPkid = '';
offlinePay.studentRXNFPkid = '';
offlinePay.studentName = '';
offlinePay.jj = '';
offlinePay.payItemPkids = '';
offlinePay.payItemNames = '';
offlinePay.payItemPkidsNew = '';
offlinePay.moneyBatchArray = '';
offlinePay.moneyCardBatchArray = '';
offlinePay.moneyAlipayBatchArray = '';
offlinePay.moneyWechatBatchArray = '';
offlinePay.moneyTTBatchArray = '';
offlinePay.ccount = 0;
offlinePay.AMOUNTRECEIVABLE_total = 0;
offlinePay.AMOUNTCOLLECTION_total = 0;
offlinePay.moneyBatchTextIndex = 0;
offlinePay.payItemListAllChild;
offlinePay.defaultDormMoneyTui = 0;
offlinePay.defaultDormTui = false;


var payButtonStatus = false;
var tuixueStatus = false;

var resetPayButtionStatus = function(){
	$("#payButton").removeAttr("disabled");
	$("#payButtonBatch").removeAttr("disabled");
	payButtonStatus = false;
};
var resetPrintButtonStatusOffine = function(){
	$("#payPrintBatchButton").removeAttr("disabled");
	$("#printBatchButton").removeAttr("disabled");
};

//获得按钮权限
var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");

offlinePay.buttonStatus = function(){
	if(offlinePay.getIdSelections() != ''){
		$("#payBatchButton").attr("disabled",false);
		$("#payBatchButton").attr("data-target","#mymoda1");
		$("#payTuiBatchButton").attr("disabled",false);
		$("#payTuiBatchButton").attr("data-target","#mymoda1");
		$("#payPrintBatchButton").attr("disabled",false);
		$("#payPrintBatchButton").attr("data-target","#mymoda2");
		$("#printBatchButton").attr("disabled",false);
	}else{
		$("#payBatchButton").attr("data-target","#mymoda1");
		$("#payBatchButton").attr("disabled","disabled");
		$("#payTuiBatchButton").attr("data-target","#mymoda1");
		$("#payTuiBatchButton").attr("disabled","disabled");
		$("#payPrintBatchButton").attr("data-target","#mymoda2");
		$("#payPrintBatchButton").attr("disabled","disabled");
		$("#printBatchButton").attr("disabled","disabled");
	}
};
//获取选中的行
offlinePay.getPkidSelections=function () {
		var $table = $('#offlinePayTable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.PKID;
    });
};

//获取选中的行
offlinePay.getIdSelections=function () {
		var $table = $('#offlinePayTable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.PAY_ITEM_PKID;
    });
};
//获取选中的行
offlinePay.getNameSelections=function () {
		var $table = $('#offlinePayTable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.PAY_STYLE_NAME;
    });
};

//获取选中的行
offlinePay.getChildNameSelections=function () {
		var $table = $('#offlinePayTable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.PAYITEM_CHILD;
    });
};

//获取选中的行
offlinePay.getStatusSelections=function () {
		var $table = $('#offlinePayTable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.STATUS;
    });
};

//获取选中的行
offlinePay.getAMOUNTRECEIVABLESelections=function () {
		var $table = $('#offlinePayTable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.AMOUNTRECEIVABLE;
    });
};

//获取选中的行获得 贷款/缓交 金额
offlinePay.getLOANMONEYSelections=function () {
	var $table = $('#offlinePayTable');
	return $.map($table.bootstrapTable('getSelections'), function(row) {
		return row.LOAN_MONE;
	});
};
//获取选中的行
offlinePay.getAMOUNTCOLLECTIONSelections=function () {
		var $table = $('#offlinePayTable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.AMOUNTCOLLECTION;
    });
};

//获取选中的行
offlinePay.getPAYSTYLEBIANMASelections=function () {
		var $table = $('#offlinePayTable');
  	return $.map($table.bootstrapTable('getSelections'), function(row) {
      return row.PAY_STYLE_BIANMA;
  });
};
//缴费状态点击
$('.pa-item').click(function () {
	$(this).addClass('active');
	$('.pa-item').not($(this)).removeClass('active');
	
	idx = $(this).index('.pa-item');
	$('.pa-row').eq(idx).stop().show(100);
	$('.pa-row').not($('.pa-row').eq(idx)).hide(100);
	
    //刷新页面
    var status = $("#statusSelected").find(".active").attr("text");
    offlinePay.studentBmPkid = $("#stuBmPkid").val();
    offlinePay.studentPkid = $("#stuPkid").val();
    doSearchForOneStudent(offlinePay.studentBmPkid, status,offlinePay.studentPkid);
    $("#payItemListCount").html(0);
	$("#payItemListCost").html(0);
});

/**
 * 检验支付授权码是否是支付宝或微信付款码
 * authCode : 支付授权码
 * type : 1支付宝，2微信
 */
offlinePay.checkAuthCode = function(authCode, type){
	var flag = false;
	var startCodeArray;
	if(type == 1){//支付宝
		//支付授权码，25~30开头的长度为16~24位的数字，实际字符串长度以开发者获取的付款码长度为准
		startCodeArray = ["25","26","27","28","29","30"];
		if($.inArray(authCode.substring(0,2), startCodeArray) >= 0 
				&& (authCode.length >= 16 && authCode.length <= 24)){
			flag = true;
		}
	}else if(type == 2){//微信
		// 扫码支付授权码，设备读取用户微信中的条码或者二维码信息，（注：用户刷卡付款码规则：18位纯数字，以10、11、12、13、14、15开头）
		startCodeArray = ["10","11","12","13","14","15"];
		if($.inArray(authCode.substring(0,2), startCodeArray) >= 0 
				&& (authCode.length == 18)){
			flag = true;
		}
	}
	return flag;
};
//获取list表格数据
offlinePay.getTab=function (url,isRefresh) {
	
	// 引例四
/*	var startusing = $("#startusingselect  option:selected").val();*/
	var url = _basepath+"pay/offlinePayTable.json";

	$('#offlinePayTable').bootstrapTable(
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
						onLoadSuccess:function(){
				            $('.bootstrap-table tr td').each(function () {
				                $(this).attr("title", $(this).text());
				                $(this).css("cursor", 'pointer');
				            });
				        },
						//行选
						onClickRow : function(row, tr,flied){
							var rowPayItemPKID = row.PAY_ITEM_PKID;
							var rowPayItemName = row.PAYITEM;
							//包含
			            	if(offlinePay.payItemPkids.indexOf(rowPayItemPKID) < 0){
			            		offlinePay.payItemPkids += rowPayItemPKID;
								offlinePay.payItemPkids += ',';
								offlinePay.payItemNames += rowPayItemName;
								offlinePay.payItemNames += ',';
			            	}
			            	//设置批量按钮是否可点击
			            	offlinePay.buttonStatus();
				        },
				        //全选
				        onCheckAll:function(rows){
				        	var payItemListCount = 0;
				        	var payItemListCost = 0;
				        	for (var i=0;i<rows.length;i++){
				        		var rowPayItemPKID = rows[i].PAY_ITEM_PKID;
				        		var rowPayItemName = rows[i].PAYITEM;
				        		//包含
				            	if(offlinePay.payItemPkids.indexOf(rowPayItemPKID) >= 0){
				            		offlinePay.payItemPkids = offlinePay.payItemPkids.replace(rowPayItemPKID+",","");
				            		offlinePay.payItemNames = offlinePay.payItemNames.replace(rowPayItemName+",","");
				            	}else{
				            		offlinePay.payItemPkids +=rowPayItemPKID;
									offlinePay.payItemPkids += ',';
									offlinePay.payItemNames +=rowPayItemName;
									offlinePay.payItemNames += ',';
				            	}
				            	//统计
				            	payItemListCount = payItemListCount + 1;
				            	payItemListCost += Number(rows[i].AMOUNTRECEIVABLE - rows[i].AMOUNTCOLLECTION);
				        		
				        	}
				        	//设置批量按钮是否可点击
			            	offlinePay.buttonStatus();
			            	
			            	$("#payItemListCount").html(" "+payItemListCount+ " ");
			            	$("#payItemListCost").html(payItemListCost.toFixed(2));
				        	
				        },
			            //点击每一个单选框时触发的操作
			            onCheck:function(row){
			            	var rowPayItemPKID = row.PAY_ITEM_PKID;
			            	var rowPayItemName = row.PAYITEM;
							//不包含
			            	if(offlinePay.payItemPkids.indexOf(rowPayItemPKID) < 0){
			            		offlinePay.payItemPkids += rowPayItemPKID;
								offlinePay.payItemPkids += ',';
								offlinePay.payItemNames += rowPayItemName;
								offlinePay.payItemNames += ',';
			            	}
			            	//设置批量按钮是否可点击
			            	offlinePay.buttonStatus();
			            	//统计
			            	runCountAndMoney(1,row);
			            },
			            //取消每一个单选框时对应的操作；
			            onUncheck:function(row){
			            	var rowPayItemPKID = row.PAY_ITEM_PKID;
			            	var rowPayItemName = row.PAYITEM;
			            	//包含
			            	if(offlinePay.payItemPkids.indexOf(rowPayItemPKID) >= 0){
			            		offlinePay.payItemPkids = offlinePay.payItemPkids.replace(rowPayItemPKID+",","");
			            		offlinePay.payItemNames = offlinePay.payItemNames.replace(rowPayItemName+",","");
			            	}
			            	//设置批量按钮是否可点击
			            	offlinePay.buttonStatus();
			            	//统计
			            	runCountAndMoney(0,row);
			            },
			            //取消每一个单选框时对应的操作；
			            onUncheckAll:function(rows){
			            	var payItemListCount = 0;
				        	var payItemListCost = 0;
			            	for (var i=0;i<rows.length;i++){
				        		var rowPayItemPKID = rows[i].PAY_ITEM_PKID;
				        		var rowPayItemName = rows[i].PAYITEM;
				        		//包含
				            	if(offlinePay.payItemPkids.indexOf(rowPayItemPKID) >= 0){
				            		offlinePay.payItemPkids = offlinePay.payItemPkids.replace(rowPayItemPKID+",","");
				            		offlinePay.payItemNames = offlinePay.payItemNames.replace(rowPayItemName+",","");
				            	}else{
				            		offlinePay.payItemPkids +=rowPayItemPKID;
									offlinePay.payItemPkids += ',';
									offlinePay.payItemNames +=rowPayItemName;
									offlinePay.payItemNames += ',';
				            	}
				        		
				        	}
			            	//设置批量按钮是否可点击
			            	offlinePay.buttonStatus();
			            	$("#payItemListCount").html(" 0 ");
			            	$("#payItemListCost").html("0");
			            },
						queryParams : function getParams(params) {
							offlinePay.studentPkid = $("#stuPkid").val();
							offlinePay.studentBmPkid = $("#stuBmPkid").val();
							var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
								limit : params.limit, //页面大小
								searchText : this.searchText,
								sortName : this.sortName,
								sortOrder : this.sortOrder,
								pageindex : this.pageNumber,
								studentPkid:offlinePay.studentBmPkid,
								pkidMain:offlinePay.studentPkid,
						    	status:$("#statusSelected").find(".active").attr("text")
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
					                	if(row.STATUS==3){
					                		return {
												 disabled : true
												   };
					                	}
									}
					            },
								{
									field : 'PKID',
									align : "center",
									halign : 'center'
								},
								{
									field: 'RXNIANFEN',
									title:'入学年份',
									align : "center",
									halign : 'center',
								},
								{
									field: 'BANJI_TYPE',
									title:'班型',
									align : "center",
									halign : 'center',
//									width:'300px'
								},
								{
									field: 'PAY_STYLE_NAME',
									title:'缴费类型',
									align : "center",
									halign : 'center',
									width:'150px'
								},

								{
									field: 'STATUS',
									title:'缴费状态',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										if(value == 0){
											return "欠费";
										}else if(value == 1){
											return "核验中";
										}else if(value == 2){
											return "已完成";
										}else if(value == 3){
											return "已关闭";
										}
									}
								},
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
									field : 'QIANFEI',
									title:'欠费金额',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										return (row.AMOUNTRECEIVABLE-row.AMOUNTCOLLECTION).toFixed(2);
										
									}
								},
								{
									field : 'opt',
									title : '操作',
									align : "center",
									halign : 'center',
									formatter : function(value, row,
											index) {

										var jihuoHtml = "";
										if(row.STATUS==3 ){//表示名单已经关闭，此时显示激活按钮
											jihuoHtml = SESSION_MENU_BUTTONS.xxjf_jh == 1?'<span class="jihuoitemlist fa fa-chain-broken" title="激活"></span>&nbsp;&nbsp;':'';
										}
										var delListHtml = "";
										var patlogHtml = '<span class="fa fa-clock-o" title="缴费记录" PAYITEM="'+row.PAYITEM+'" PAYITEM_CHILD="'+row.PAYITEM_CHILD+'" onclick="javascript:payRecord(\''+row.PKID+'\',\''+row.PAYITEM+'\',\''+row.COST+'\',\''+row.DISCOUNT+'\',\''+row.AMOUNTRECEIVABLE+'\',\''+row.AMOUNTCOLLECTION+'\');"></span>&nbsp;&nbsp;';
										if(row.STATUS==0){//表示名单已关闭并且没有缴费记录，此时显示删除项目按钮。隐藏缴费记录按钮。
											delListHtml = '<span class="delitemlist fa fa-trash-o" title="删除"></span>&nbsp;&nbsp;';
											/*patlogHtml = "";*/
										}
										return [ 
												patlogHtml,
												jihuoHtml,
												delListHtml,
												SESSION_MENU_BUTTONS.xxjf_gbxm == 1?row.STATUS == 0?'<span class="fa fa-close" title="关闭项目" onclick="javascript:closeProject(\''+row.PKID+'\');"></span>':'':'',
														]
												.join('');
										
									}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
									 events : {
										'click .edit' : function(e,value, row, index) {
											$('#payitem').val(row.PAYITEM);
											$('#pkid').val(row.PKID);
											if(row.MUSTPAY=="是"){
												$("#mustpay")[0].checked=true;
											}else{
												$("#mustpay")[0].checked=false;
											}
											  //编辑
											$('#mymoda2').modal({
												keyboard: true
											});
    							        
										},
										'click .see' : function(e,value, row, index) {
											var r = Math.random();
											$('.jf_szright').load('pay/seePayItemList.json?payItemPkid='+row.PKID+'&r='+r);
    							        
										},
										'click .jihuoitemlist' : function(e,value, row, index) {//激活
											offlinePay.jihuoitemlist(row.PKID);
										},
										'click .delitemlist' : function(e,value, row, index) {//删除缴费名单
											offlinePay.delitemlist(row.PKID);
										}
										
									} 
								}
						],
					});
	$('#offlinePayTable').bootstrapTable('hideColumn', 'PKID');//隱藏列
};


//点击缴费/退费
var jiaotui = function(payItemPkid, index, status,AMOUNTRECEIVABLE,AMOUNTCOLLECTION,PAY_STYLE_BIANMA){

	//清空文本框
	$("#moneyPay").val('');
	$("#moneyCardPay").val('');
	$("#moneyTTPay").val('');
	$("#moneyAlipayPay").val('');
	$("#moneyWechatPay").val('');
	$("#remarksPay").val('');
	$("#cardNOPay").val('');
	
	$("#zfb_txm").val('');
	$("#wx_txm").val('');

	$("input[name='payType']").get(1).checked=true; 
	$("#moneyPay").attr("disabled","disabled");
	$("#moneyCardPay").removeAttr("disabled");
	$("#cardNOPay").removeAttr("disabled");
	
	$("#moneyAlipayPay").attr("disabled","disabled");
	$("#zfb_txm").attr("disabled","disabled");
	$("#moneyWechatPay").attr("disabled","disabled");
	$("#wx_txm").attr("disabled","disabled");
	$("#moneyTTPay").attr("disabled","disabled");
	
	offlinePay.AMOUNTRECEIVABLE_total = AMOUNTRECEIVABLE;
	offlinePay.payItemPkid = payItemPkid;
	if(index == 1){
		offlinePay.AMOUNTRECEIVABLE_total = AMOUNTRECEIVABLE-AMOUNTCOLLECTION;
		$("#jiaotuifei").html("缴费");
		offlinePay.jj = 'SF';
		$("#tuiObj").show();
		$("#cankaoMoneyDiv").css("display","none");
		$("#cankaoMoney").html("");
		offlinePay.defaultDormTui = false;
	}else if(index == 2){
		if(AMOUNTCOLLECTION == '' || AMOUNTCOLLECTION <= 0){
			layer.msg("操作失败，只有已缴过费的项目才能进行退费!");
			return false;
		}
		$("#jiaotuifei").html("退费");
		offlinePay.AMOUNTCOLLECTION_total = AMOUNTCOLLECTION;
		//如果是住宿费
		if('43' == PAY_STYLE_BIANMA){
			$("#cankaoMoneyDiv").css("display","block");
			//是否住宿费
			offlinePay.defaultDormTui = true;
			
			offlinePay.defaultDormMoneyTui = AMOUNTCOLLECTION - AMOUNTRECEIVABLE;
			var defaultMoneyTui = offlinePay.defaultDormMoneyTui < 0 ? 0 : offlinePay.defaultDormMoneyTui;
			$("#moneyPay").val(defaultMoneyTui);
			$("#cankaoMoney").html(defaultMoneyTui);
			
			
		}else{
			$("#cankaoMoneyDiv").css("display","block");
			offlinePay.defaultDormMoneyTui = AMOUNTCOLLECTION - AMOUNTRECEIVABLE;
			var defaultMoneyTui = offlinePay.defaultDormMoneyTui < 0 ? 0 : offlinePay.defaultDormMoneyTui;
			$("#moneyPay").val(defaultMoneyTui);
			$("#cankaoMoney").html(defaultMoneyTui);
			offlinePay.defaultDormTui = false;
		}
		offlinePay.jj = 'TF';
		$("#tuiObj").css("display","none");
	}
	$('#mymoda3').modal({
			keyboard: true
		});
	setTimeout(function(){
		$("#moneyCardPay").focus(); 
	}, 300);
	
};
var success_jsonpCallback = function(data){
	
};
//缴费/退费/批量缴费
var payJT = function(moneyPay,moneyCardPay,cardNOPay,moneyAlipayPay,moneyWechatPay,moneyTTPay,remarksPay,
		moneyPayTotal,moneyCardPayTotal,moneyWechatPayTotal,moneyAlipayPayTotal,moneyTTPayTotal,payItemPkids,zfb_txm,wx_txm){
	offlinePay.studentPkid = $("#stuPkid").val();
	offlinePay.studentBmPkid = $("#stuBmPkid").val();
	offlinePay.userPkid = $("#userId").val();
	//批量退费时实收合计
	if(offlinePay.jj == 'TF'){
		var AMOUNTCOLLECTIONss = offlinePay.getAMOUNTCOLLECTIONSelections()+"";
		var AMOUNTCOLLECTIONArrays = AMOUNTCOLLECTIONss.split(",");
		for(var i=0;i<AMOUNTCOLLECTIONArrays.length;i++){
			offlinePay.AMOUNTCOLLECTION_total += Number(AMOUNTCOLLECTIONArrays[i]);
		}
	}
//	alert(moneyPay+'-'+moneyCardPay+'-'+cardNOPay+'-'+moneyAlipayPay+'-'+moneyWechatPay+'-'+remarksPay+'-'+offlinePay.userPkid);
//	alert(moneyPayTotal+'-'+moneyCardPayTotal+'-'+moneyWechatPayTotal+'-'+moneyAlipayPayTotal+'-'+offlinePay.AMOUNTRECEIVABLE_total);
	if(moneyCardPay == '' || moneyCardPay == '0'){
		cardNOPay = '';
	}
	var AMOUNTRECEIVABLE_total = Number(offlinePay.AMOUNTRECEIVABLE_total);
	if(offlinePay.jj == 'SF' && ((Number(moneyPayTotal) + Number(moneyCardPayTotal) + Number(moneyWechatPayTotal) + Number(moneyAlipayPayTotal) +Number(moneyTTPayTotal)) > AMOUNTRECEIVABLE_total.toFixed(2))){
		layer.msg("实收金额不能超出应收金额!");
		resetPayButtionStatus();
		return false;
	}
	if(offlinePay.jj == 'TF' && ((Number(moneyPayTotal) + Number(moneyCardPayTotal) + Number(moneyWechatPayTotal) + Number(moneyAlipayPayTotal) +Number(moneyTTPayTotal)) > offlinePay.AMOUNTCOLLECTION_total)){
		layer.msg("退费超上限！");
		resetPayButtionStatus();
		return false;
	}
	/*if(Number(moneyPayTotal) + Number(moneyCardPayTotal) + Number(moneyWechatPayTotal) + Number(moneyAlipayPayTotal) +Number(moneyTTPayTotal)<= 0){
		layer.msg("输入金额不能为空!");
		resetPayButtionStatus();
		return false;
	}*/
	
	
	payButtonStatus = true;
	//加密学生
	$.ajax({
		  type: 'POST',
		  url: _basepath+"pay/encryptStudent.json",
		  async: false,
		  data: {
			studentPkid:offlinePay.studentPkid,
			TRANSACTION_TYPE:offlinePay.jj,//收费还是退费
			PAY_ITEM_LIST_PKIDS:payItemPkids,//缴费名单集合，通过逗号分隔
			TOTALMONEY_CASH:moneyPayTotal == ''?'0':moneyPayTotal,//订单现金总金额
			TOTALMONEY_CARD:moneyCardPayTotal == ''?'0':moneyCardPayTotal,//订单银行卡总金额
			TOTALMONEY_WX:moneyWechatPayTotal == ''?'0':moneyWechatPayTotal,//订单微信总金额
			TOTALMONEY_ZFB:moneyAlipayPayTotal == ''?'0':moneyAlipayPayTotal,//订单支付宝总金额
			TOTALMONEY_TT:moneyTTPayTotal == ''?'0':moneyTTPayTotal//订单支付宝总金额
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == "success"){
				  var url = result.methodurl+"/qhmy-pay/dingDanPayController/dingDan.php";
					var data={
							ORDERCREATE_MODE:'D',//订单生成方式:U:线上（学生APP或PC）D:线下（线下缴费操作）  DO:线下其他收费（其他收费）I:导入
							STUDENT_PKID:offlinePay.studentPkid,//学生PKID
							ORDERCREATE_TERMINAL:'07',//订单生成终端  07:互联网  08:移动-iOS 09:移动-Andriod  10:移动-其他
							TRANSACTION_TYPE:offlinePay.jj,//收费还是退费
							TOTALMONEY_CASH:moneyPayTotal == ''?'0':moneyPayTotal,//订单现金总金额
							TOTALMONEY_CARD:moneyCardPayTotal == ''?'0':moneyCardPayTotal,//订单银行卡总金额
							TOTALMONEY_WX:moneyWechatPayTotal == ''?'0':moneyWechatPayTotal,//订单微信总金额
							TOTALMONEY_ZFB:moneyAlipayPayTotal == ''?'0':moneyAlipayPayTotal,//订单支付宝总金额
							TOTALMONEY_TT:moneyTTPayTotal == ''?'0':moneyTTPayTotal,//订单电汇总金额
							CJR:offlinePay.userPkid,//创建人
							REMARK:remarksPay,//备注
							CARDNO:cardNOPay,//银行卡号 （当银行卡金额不为'0'时必填）
							CASH:moneyPay == ''?'0':moneyPay,//现金各缴费项目缴费金额（当有转出转入项目时必传）
							CARD:moneyCardPay == ''?'0':moneyCardPay,//银行卡各缴费项目缴费金额（当有转出转入项目时必传）
							WX:moneyWechatPay == ''?'0':moneyWechatPay,//微信支付各缴费项目金额（当有转出转入项目时必传）
							ZFB:moneyAlipayPay == ''?'0':moneyAlipayPay,//支付宝支付各缴费项目金额（当有转出转入项目时必传）
							TT:moneyTTPay == ''?'0':moneyTTPay,//电汇支付各缴费项目金额（当有转出转入项目时必传）
							PAY_ITEM_LIST_PKIDS:payItemPkids,
							PASSMSG:result.PASSMSG,
							ZFB_TXM:zfb_txm,
							WX_TXM:wx_txm
							
					};
					
					$.ajax({
						type: 'POST',
				        url: url,
				        data:data,
				        dataType:'JSONP',//here
				        jsonp:"callback",
//				        jsonpCallback:"success_jsonpCallback",
				        success:function (data) {
				        	
				        	if(data.result == 'true'){
				        		layer.msg("操作成功!");
				    			$("#cancelButton").click();
				    			$("#cancelButtonBatch").click();
				    			//隐藏蒙版
				    			$(".modal-backdrop").remove();
				    			
				    			var status = $("#statusSelected").find(".active").attr("text");
				    			//刷新页面
				    			doSearchForOneStudent(offlinePay.studentBmPkid, status,offlinePay.studentPkid);
				    			$("#payItemListCount").html(0);
				    			$("#payItemListCost").html(0);
				    			
				        	}else{
				        		layer.msg(data.msg);
				        	}
				        	setTimeout(function(){
				        		resetPayButtionStatus();
				        	}, 800);
				        	
				        	
				        },
				        error: function (XMLHttpRequest, textStatus, errorThrown) {
				        	resetPayButtionStatus();
		                    // 状态码
		                    console.log(XMLHttpRequest.status);
		                    // 状态
		                    console.log(XMLHttpRequest.readyState);
		                    // 错误信息   
		                    console.log(textStatus);
		                }
				    });
			  }else{//验证宿舍资源失败或其他情况失败
				  resetPayButtionStatus();
				  if(result.errorinfo == ''){
					  layer.msg("操作失败，请重试!");
				  }else{//1.含有宿舍类型的缴费项目，并且宿舍资源已被抢完.  2.其他错误
					  layer.msg(result.errorinfo);
				  }
				  
			  }

		  
		  }
		});
	return false;

	
};

//点击缴费/退费保存
$('#payButton').click(function(){
	if(payButtonStatus){
		return false;
	}
	$(this).attr("disabled","disabled");
	payButtonStatus = true;
	
	var moneyPay = $("#moneyPay").val();
	if(moneyPay != ''){
		moneyPay = Number(moneyPay);
	}else{
		moneyPay = '0';
	}
	var moneyCardPay = $("#moneyCardPay").val();
	if(moneyCardPay != ''){
		moneyCardPay = Number(moneyCardPay);
	}else{
		moneyCardPay = '0';
	}
	var cardNOPay = $("#cardNOPay").val();
	var moneyAlipayPay = $("#moneyAlipayPay").val();
	if(moneyAlipayPay != ''){
		moneyAlipayPay = Number(moneyAlipayPay);
	}else{
		moneyAlipayPay = '0';
	}
	var moneyWechatPay = $("#moneyWechatPay").val();
	if(moneyWechatPay != ''){
		moneyWechatPay = Number(moneyWechatPay);
	}else{
		moneyWechatPay = '0';
	}
	var moneyTTPay = $("#moneyTTPay").val();
	if(moneyTTPay != ''){
		moneyTTPay = Number(moneyTTPay);
	}else{
		moneyTTPay = '0';
	}
	var remarksPay = $("#remarksPay").val();
	
	var zfb_txm = $("#zfb_txm").val();
	var wx_txm = $("#wx_txm").val();
	
	var moneyPayTotal = moneyPay;
	var moneyCardPayTotal = moneyCardPay;
	var moneyWechatPayTotal = moneyWechatPay;
	var moneyAlipayPayTotal = moneyAlipayPay;
	var moneyTTPayTotal = moneyTTPay;
	
	var isCheckPayTypeMoney = $('input:radio[name=payType]')[0].checked;
	var isCheckPayTypeCard = $('input:radio[name=payType]')[1].checked;
	var isCheckPayTypeAlipay = $('input:radio[name=payType]')[2].checked;
	var isCheckPayTypeWechat = $('input:radio[name=payType]')[3].checked;
	var isCheckPayTypeTT = $('input:radio[name=payType]')[4].checked;
	if(isCheckPayTypeAlipay){
		if(zfb_txm == '' || zfb_txm == 'undefined' || typeof zfb_txm == 'undefined'){
			layer.msg("支付宝付款码不能为空，请先扫码！");
			resetPayButtionStatus();
			return false;
		}else if(!offlinePay.checkAuthCode(zfb_txm, 1)){
			layer.msg("非支付宝付款码，请确认！");
			resetPayButtionStatus();
			return false;
		}
	}else if(isCheckPayTypeWechat){
		if(wx_txm == '' || wx_txm == 'undefined' || typeof wx_txm == 'undefined'){
			layer.msg("微信付款码不能为空，请先扫码！");
			resetPayButtionStatus();
			return false;
		}else if(!offlinePay.checkAuthCode(wx_txm, 2)){
			layer.msg("非微信付款码，请确认！");
			resetPayButtionStatus();
			return false;
		}
	}else if(isCheckPayTypeMoney){
		
	}else if(isCheckPayTypeTT){
		
	}else if(isCheckPayTypeCard){
		
	}else{
		layer.msg("请选择支付方式！");
		resetPayButtionStatus();
		return false;
	}
	
	//缴费、退费
	payJT(moneyPay,
			moneyCardPay,
			cardNOPay,
			moneyAlipayPay,
			moneyWechatPay,
			moneyTTPay,
			remarksPay,
			moneyPayTotal,moneyCardPayTotal,moneyWechatPayTotal,moneyAlipayPayTotal,moneyTTPayTotal,offlinePay.payItemPkid,zfb_txm,wx_txm);
	
});

var getMoneyArray = function(count){
	var moneyArray = "";
	for (var i = 0; i < count; i++) {
		moneyArray += "0";
		if(i != count -1){
			moneyArray += ",";
		}
	}
	return moneyArray;
};

//批量缴费点击保存
$('#payButtonBatch').click(function(){
	var selectPayItemPkids = offlinePay.getIdSelections()+"";
	var payItemPkidArrays = selectPayItemPkids.split(",");
	if(payButtonStatus){
		return false;
	}
	$(this).attr("disabled","disabled");
	payButtonStatus = true;
	
	offlinePay.moneyBatchArray = '';
	offlinePay.moneyCardBatchArray = '';
	offlinePay.moneyAlipayBatchArray = '';
	offlinePay.moneyWechatBatchArray = '';
	offlinePay.moneyTTBatchArray = '';
	
	
	var inputCount = 0;
	//拼接金额
	$(".moneyPayBatch").each(function(){
		inputCount ++;
		if($(this).val() != '0.00'&&$(this).val() !=""&&$(this).val() !=null){
			offlinePay.moneyBatchArray += $(this).val();
			offlinePay.moneyBatchArray += ',';
		}
		
	});
	$(".moneyCardPayBatch").each(function(){
		inputCount ++;
		if($(this).val() != '0.00'&&$(this).val() !=""&&$(this).val() !=null){
			offlinePay.moneyCardBatchArray += $(this).val();
			offlinePay.moneyCardBatchArray += ',';
		}
		
	});
	$(".moneyAlipayPayBatch").each(function(){
		inputCount ++;
		if($(this).val() != '0.00'&&$(this).val() !=""&&$(this).val() !=null){
			offlinePay.moneyAlipayBatchArray += $(this).val();
			offlinePay.moneyAlipayBatchArray += ',';
		}
		
	});
	$(".moneyWechatPayBatch").each(function(){
		inputCount ++;
		if($(this).val() != '0.00'&&$(this).val() !=""&&$(this).val() !=null){
			offlinePay.moneyWechatBatchArray += $(this).val();
			offlinePay.moneyWechatBatchArray += ',';
		}
		
	});
	$(".moneyTTPayBatch").each(function(){
		inputCount ++;
		if($(this).val() != '0.00'&&$(this).val() !=""&&$(this).val() !=null){
			offlinePay.moneyTTBatchArray += $(this).val();
			offlinePay.moneyTTBatchArray += ',';
		}
		
	});

	var remarksPay = $("#remarksPayBatch").val();
	var cardNOPay = $("#cardNOPayBatch").val();
	
	var moneyPayTotal = $("#moneyTotalBatch").html();
	var moneyCardPayTotal = $("#moneyCardTotalBatch").html();
	var moneyWechatPayTotal = $("#moneyWechatTotalBatch").html();
	var moneyAlipayPayTotal = $("#moneyAlipayTotalBatch").html();
	var moneyTTPayTotal = $("#moneyTTTotalBatch").html();
	
	var cardNOPayBatch = $("#cardNOPayBatch").val();
	var zfb_txm = $("#zfb_txm_batch").val();
	var wx_txm = $("#wx_txm_batch").val();
	var isCheckPayTypeMoneyBatch = $('input:radio[name=payTypeBatch]')[0].checked;
	var isCheckPayTypeCardBatch = $('input:radio[name=payTypeBatch]')[1].checked;
	var isCheckPayTypeAlipayBatch = $('input:radio[name=payTypeBatch]')[2].checked;
	var isCheckPayTypeWechatBatch = $('input:radio[name=payTypeBatch]')[3].checked;
	var isCheckPayTypeTTBatch = $('input:radio[name=payTypeBatch]')[4].checked;
	if(isCheckPayTypeMoneyBatch){
		
		offlinePay.moneyAlipayBatchArray = getMoneyArray(inputCount);
		offlinePay.moneyCardBatchArray = getMoneyArray(inputCount);
		offlinePay.moneyWechatBatchArray = getMoneyArray(inputCount);
		offlinePay.moneyTTBatchArray = getMoneyArray(inputCount);
		moneyAlipayPayTotal = 0;
		moneyCardPayTotal = 0;
		moneyWechatPayTotal = 0;
		moneyTTPayTotal = 0;
	}else if(isCheckPayTypeCardBatch){
		offlinePay.moneyCardBatchArray = offlinePay.moneyBatchArray;
		moneyCardPayTotal = moneyPayTotal;
		
		offlinePay.moneyBatchArray = getMoneyArray(inputCount);
		offlinePay.moneyAlipayBatchArray = getMoneyArray(inputCount);
		offlinePay.moneyWechatBatchArray = getMoneyArray(inputCount);
		offlinePay.moneyTTBatchArray = getMoneyArray(inputCount);
		moneyPayTotal = 0;
		moneyAlipayPayTotal = 0;
		moneyWechatPayTotal = 0;
		moneyTTPayTotal = 0;

	}else if(isCheckPayTypeAlipayBatch){
		offlinePay.moneyAlipayBatchArray = offlinePay.moneyBatchArray;
		moneyAlipayPayTotal = moneyPayTotal;
		
		offlinePay.moneyBatchArray = getMoneyArray(inputCount);
		offlinePay.moneyCardBatchArray = getMoneyArray(inputCount);
		offlinePay.moneyWechatBatchArray = getMoneyArray(inputCount);
		offlinePay.moneyTTBatchArray = getMoneyArray(inputCount);
		moneyPayTotal = 0;
		moneyCardPayTotal = 0;
		moneyWechatPayTotal = 0;
		moneyTTPayTotal = 0;
		if(zfb_txm == '' || zfb_txm == 'undefined' || typeof zfb_txm == 'undefined'){
			layer.msg("支付宝付款码不能为空，请先扫码！");
			resetPayButtionStatus();
			return false;
		}else if(!offlinePay.checkAuthCode(zfb_txm, 1)){
			layer.msg("非支付宝付款码，请确认！");
			resetPayButtionStatus();
			return false;
		}
	}else if(isCheckPayTypeWechatBatch){
		
		offlinePay.moneyWechatBatchArray = offlinePay.moneyBatchArray;
		moneyWechatPayTotal = moneyPayTotal;
		
		offlinePay.moneyBatchArray = getMoneyArray(inputCount);
		offlinePay.moneyCardBatchArray = getMoneyArray(inputCount);
		offlinePay.moneyAlipayBatchArray = getMoneyArray(inputCount);
		offlinePay.moneyTTBatchArray = getMoneyArray(inputCount);
		moneyPayTotal = 0;
		moneyCardPayTotal = 0;
		moneyAlipayPayTotal = 0;
		moneyTTPayTotal = 0;
		if(wx_txm == '' || wx_txm == 'undefined' || typeof wx_txm == 'undefined'){
			layer.msg("微信付款码不能为空，请先扫码！");
			resetPayButtionStatus();
			return false;
		}else if(!offlinePay.checkAuthCode(wx_txm, 2)){
			layer.msg("非微信付款码，请确认！");
			resetPayButtionStatus();
			return false;
		}
	}else if(isCheckPayTypeTTBatch){
		offlinePay.moneyTTBatchArray = offlinePay.moneyBatchArray;
		moneyTTPayTotal = moneyPayTotal;
		
		offlinePay.moneyBatchArray = getMoneyArray(inputCount);
		offlinePay.moneyCardBatchArray = getMoneyArray(inputCount);
		offlinePay.moneyAlipayBatchArray = getMoneyArray(inputCount);
		offlinePay.moneyWechatBatchArray = getMoneyArray(inputCount);
		moneyPayTotal = 0;
		moneyCardPayTotal = 0;
		moneyAlipayPayTotal = 0;
		moneyWechatPayTotal = 0;
	}else{
		layer.msg("请选择支付方式！");
		return false;
	}
	//拼接应交金额不为0的pkid
	var payItemPkidNewArray="";
	var length = payItemPkidArrays.length-1;
	for(var i=0; i<payItemPkidArrays.length; i++){
		var moneyTotalBatch =  $("#yingjiaojine_"+i).val();
		//拼接缴费项目id
		if(moneyTotalBatch!=0){				
			if(i==length){
				payItemPkidNewArray+=$("#payItemPkidArrays_"+i).val();
			}else{
				payItemPkidNewArray+=($("#payItemPkidArrays_"+i).val()+",");
			}				
		}			
	}
	offlinePay.payItemPkidsNew = payItemPkidNewArray;
	if(offlinePay.payItemPkidsNew==""){
		if(offlinePay.jj == 'TF'){
			layer.msg("退费金额必须大于0！");
		}else if(offlinePay.jj == 'SF'){
			layer.msg("实交金额必须大于0！");
		}	
		resetPayButtionStatus();
		return false;
	}
	//批量缴费
	payJT(offlinePay.moneyBatchArray,
			offlinePay.moneyCardBatchArray,
			cardNOPay,
			offlinePay.moneyAlipayBatchArray,
			offlinePay.moneyWechatBatchArray,
			offlinePay.moneyTTBatchArray,
			remarksPay,
			moneyPayTotal,moneyCardPayTotal,moneyWechatPayTotal,moneyAlipayPayTotal,moneyTTPayTotal,offlinePay.payItemPkidsNew+"",zfb_txm,wx_txm);
	tuixueStatus = true;
});
offlinePay.getStudentPayTab = function(){
	
	var status = $("#statusSelected").find(".active").attr("text");
	var conditions = $('#conditionsSelect').val();
	
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
							var studentPkid = row.TBPKID;
							var pkidMain = row.PKID;
							var status = $("#statusSelected").find(".active").attr("text");
			            	doSearchForOneStudent(studentPkid, status,pkidMain);
			            	$("#payItemListCount").html(0);
			            	$("#payItemListCost").html(0);
						},
						queryParams : function getParams(params) {
							var conditions = $("#conditionsSelect").val();
							var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
								limit : params.limit, //页面大小
								searchText : this.searchText,
								sortName : this.sortName,
								sortOrder : this.sortOrder,
								pageindex : this.pageNumber,
								status: status,
								conditions:conditions,
								departmentPkidList:null
								
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
									field : 'TBPKID',// 可不加
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
									field: 'RXNIANFEN',//可不加  
									title:'入学年份',
									align : "center",
									halign : 'center'
								},
								{
									field: 'BANJI_TYPE',//可不加  
									title:'班型',
									align : "center",
									halign : 'center'
								},
								{
									field: 'WHKXUEXIAONAME',//可不加  
									title:'文化课学校',
									align : "center",
									halign : 'center'
								}
								
						],
					});
    $('#studentListTable').bootstrapTable('hideColumn', 'PKID');//隱藏列
    $('#studentListTable').bootstrapTable('hideColumn', 'TBPKID');//隱藏列
};
offlinePay.getStudentPayTabQH = function(){
	
	var status = $("#statusSelected").find(".active").attr("text");
	var sfzh = $("#studentSFZ").html();
	var url = _basepath+"pay/getStudentQuerylistPage.json";
	$('#studentListTableQH').bootstrapTable(
			{
				url : url,//数据源
				dataField : "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
				totalField : 'total',
				sortOrder : 'desc',
				striped : true, //表格显示条纹  
				pagination : true, //启动分页  
				pageNumber : 1, //当前第几页  
				pageSize : 2, //每页显示的记录数  
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
					var studentPkid = row.TBPKID;
					var pkidMain = row.PKID;
					var status = $("#statusSelected").find(".active").attr("text");
					doSearchForOneStudent(studentPkid, status,pkidMain);
				},
				queryParams : function getParams(params) {
					var conditions = $("#conditionsSelect").val();
					var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
							limit : params.limit, //页面大小
							searchText : this.searchText,
							sortName : this.sortName,
							sortOrder : this.sortOrder,
							pageindex : this.pageNumber,
							status: status,
							sfzh: $("#studentSFZ").html(),
							studentBmPkid: $("#stuBmPkid").val(),
							conditions: null,
							departmentPkidList:null
							
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
				        	   field : 'TBPKID',// 可不加
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
				        	   field: 'XUEHAO',//可不加  
				        	   title:'学号',
				        	   align : "center",
				        	   halign : 'center'
				           },
				           {
				        	   field: 'RXNIANFEN',//可不加  
				        	   title:'入学年份',
				        	   align : "center",
				        	   halign : 'center'
				           },
				           {
				        	   field: 'BANJI_TYPE',//可不加  
				        	   title:'班型',
				        	   align : "center",
				        	   halign : 'center'
				           },
				           {
				        	   field: 'BANJINAME',//可不加  
				        	   title:'班级',
				        	   align : "center",
				        	   halign : 'center'
				           }
				           
				           ],
			});
	$('#studentListTableQH').bootstrapTable('hideColumn', 'PKID');//隱藏列
	$('#studentListTableQH').bootstrapTable('hideColumn', 'TBPKID');//隱藏列
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
//刷新学生table
var refreshStudentTable2 = function(sfzh, status){
	var url = _basepath+"pay/getStudentQuerylistPage.json";
	var opt = {
			url :url,
			silent: true,
			query:{
				"status":status,
				"sfzh":sfzh
			}
	};
	$("#studentListTableQH").bootstrapTable('refresh', opt);
};


//查询按钮
$('#btn_search').click(function(){
	
	var status = "";
	var conditions = $('#conditionsSelect').val();
	
	
	
	//查询学生信息
	var url=_basepath+"pay/getStudentQuerylist2.json";
	$.post(url,{conditions:conditions},function(result){
		if(result.result == 'success'){
			if(result.stuList.length == 0){
				layer.msg("未查询到该学生！");
			}else if(result.stuList.length == 1){
				
				doSearchForOneStudent(result.stuList[0].TBPKID,status,result.stuList[0].PKID);
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
	
	return false;
   
});
//切换按钮
$('#qiehuan_search').click(function(){
	var sfzh = $("#studentSFZ").html();
	var reg=/^\s+$/g;
    //判空
	if(sfzh==''||sfzh==null||reg.test(sfzh)){
		layer.msg("未查询到可切换的数据！");
		return false;
	}
	$('#mymoda9').modal({
		keyboard: true
	});
	refreshStudentTable2(sfzh, status);
});

//分学号
$('#btn_fenxuehao').click(function(){
	var sfzh = $("#studentSFZ").html();
	var reg=/^\s+$/g;
	//判空
	if(sfzh==''||sfzh==null||reg.test(sfzh)){
		layer.msg("未查询到可分学号的学生数据！");
		return false;
	}
	var xuehao = $("#studentXH").html();
	if(xuehao!=""&&xuehao!=null){
		layer.msg("该学生已有学号！");
		return false;
	}
	offlinePay.studentPkid = $("#stuPkid").val();
	offlinePay.studentBmPkid = $("#stuBmPkid").val();
	var status = $("#statusSelected").find(".active").attr("text");
	BootstrapDialog.show({  //显示需要提交的表单。
      	title:'提示信息',	
	      message: '确定要分学号吗？',
	     closable: false, 
	      buttons: [{
		    label: '确定',
		    cssClass: 'btn-danger',
		    action: function(dialogRef){
		    	$.ajax({
            		type:'post',
            		dataType:'json',
            		data:{keywords:sfzh,studentBmPkid:offlinePay.studentBmPkid},
            		url: _basepath+'assignedClass/diviXuehao.json',
            		success:function(data){
            			if(data.result=='success'){
            				dialogRef.close();
            				layer.msg("分学号成功！");
            				doSearchForOneStudent(offlinePay.studentBmPkid, status,offlinePay.studentPkid);
            			}else{
            				layer.msg(data.errorinfo);
            			}
            		},
            		error:function(a,error,c){
            			layer.msg("分学号失败，请重试！");
            		}
            	});
          }
	  },
	  {
	    label: '关闭',
	    cssClass: 'btn-default',
	    action: function(dialogRef){
	       dialogRef.close();
	    }
	  }]
});
	
});

//选择一个学生查询
var doSearchForOneStudent = function(studentPkid, status,pkidMain){
	if(studentPkid=='' || studentPkid=='undefined' || studentPkid==null){
		return false;
	}
	offlinePay.studentPkid = studentPkid;
	var url=_basepath+"pay/getStudentInfoForOfflinePay.json";
	$.post(url,{studentPkid:studentPkid},function(result){
		if(result.result == 'success'){
			if(result.stuInfo == null){
				layer.msg("未查询到该学生！");
				return false;
			}
			//关闭弹框
			$("#btn_query_cancel").click();
			$("#btn_query_cancelqiehuan").click();
			
			  $("#studentXM").html(result.stuInfo.XINGMING);
			  $("#studentSFZ").html(result.stuInfo.SHENFENZHENGHAO);
			  $("#studentWHKXX").html(result.stuInfo.WHKXUEXIAONAME);
			  $("#studentZXZT").html(result.stuInfo.ZXZT);
			  $("#studentRXNF").html(result.stuInfo.RXNIANFEN);
			  $("#studentBX").html(result.stuInfo.BANJI_TYPE);
			  $("#studentBXSP").html("班型："+result.stuInfo.RXNIANFEN+"-"+result.stuInfo.BANJI_TYPE);
			  $("#studentBJ").html(result.stuInfo.BANJINAME);
			  $("#studentXH").html(result.stuInfo.XUEHAO);
			  $("#studentZHYE").html("0.00");
			  $("#studentYJNF").html(result.stuInfo.YJ_NIANFEN);
			  $("#stuPkid").val(result.stuInfo.PKID);
			  $("#stuBmPkid").val(result.stuInfo.TBPKID);
			  $("#stuBXPkid").val(result.stuInfo.BANJI_TYPE_PKID);
			  $("#stuRXNFPkid").val(result.stuInfo.RXNIANFEN_PKID);
			  var status = $("#statusSelected").find(".active").attr("text");
			  var url = _basepath+"pay/offlinePayTable.json";
			  	 var opt = {
						url :url,
					    silent: true,
					    query:{
					    	"studentPkid":studentPkid,
					    	"pkidMain":pkidMain,
					    	"status":status
					    }
					  };
				 $("#offlinePayTable").bootstrapTable('refresh', opt);
		}
		 
	});
};

//激活缴费名单
offlinePay.jihuoitemlist = function(PKID){
	offlinePay.studentBmPkid = $("#stuBmPkid").val();
	offlinePay.studentPkid = $("#stuPkid").val();
	BootstrapDialog.show({  //显示需要提交的表单。
    	title:'提示信息',	
	    message: '你确定要激活该缴费项目吗？',
	    closable: true, 
	      buttons: [{
		    label: '确定',
		    cssClass: 'btn-danger',
		    action: function(dialogRef){
		    	var url=_basepath+"pay/jihuoItemList.json";
				$.ajax({
					  type: 'POST',
					  url: url,
					  async: false,
					  data: {
						  PKID:PKID,
						  STUDENT_BM_PKID:offlinePay.studentBmPkid,
						  STUDENT_PKID:offlinePay.studentPkid
					  },
					  dataType : "json",
					  success : function(result) {
						  if(result.result == 'success'){
							layer.msg("操作成功!"); 
							var status = $("#statusSelected").find(".active").attr("text");
							//刷新页面
							doSearchForOneStudent(offlinePay.studentBmPkid, status,offlinePay.studentPkid);
						  }else{
							  layer.msg("操作失败，请重试!");  
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
    });
	
};

//关闭缴费名单
offlinePay.delitemlist = function(PKID){
	offlinePay.studentBmPkid = $("#stuBmPkid").val();
	offlinePay.studentPkid = $("#stuPkid").val();
	BootstrapDialog.show({  //显示需要提交的表单。
    	title:'提示信息',	
	    message: '你确定要删除该缴费项目吗？',
	    closable: true, 
	      buttons: [{
		    label: '确定',
		    cssClass: 'btn-danger',
		    action: function(dialogRef){
		    	//关闭项目
		  	  	var url=_basepath+"pay/delitemlist.json";
		  		$.ajax({
		  			  type: 'POST',
		  			  url: url,
		  			  async: false,
		  			  data: {
		  				PKID:PKID
		  			  },
		  			  success : function(result) {
		  				  if(result.result == 'success'){
		  					layer.msg("操作成功!"); 
		  					var status = $("#statusSelected").find(".active").attr("text");
		  					//刷新页面
			    			doSearchForOneStudent(offlinePay.studentBmPkid, status,offlinePay.studentPkid);
			    			$("#payItemListCount").html(0);
			    			$("#payItemListCost").html(0);
		  				  }else if(result.result == 'isJf'){
		  					layer.msg("学生已经缴费，不允许删除名单!"); 
		  				  }else{
		  					  layer.msg("操作失败，请重试!");  
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
    });
}


//关闭缴费项目
var closeProject = function(payItemListPkid){
	offlinePay.studentBmPkid = $("#stuBmPkid").val();
    offlinePay.studentPkid = $("#stuPkid").val();
	BootstrapDialog.show({  //显示需要提交的表单。
    	title:'提示信息',	
	    message: '你确定要关闭 此项目 吗？',
	    closable: true, 
	      buttons: [{
		    label: '确定',
		    cssClass: 'btn-danger',
		    action: function(dialogRef){
		    	//关闭项目
		  	  	var url=_basepath+"pay/closeProject.json";
		  		$.ajax({
		  			  type: 'POST',
		  			  url: url,
		  			  async: false,
		  			  data: {
		  				PKID:payItemListPkid,
		  				STATUS:3
		  			  },
		  			  dataType : "json",
		  			  success : function(result) {
		  				  if(result.result = 'success'){
		  					layer.msg("操作成功!"); 
		  					
		  					var status = $("#statusSelected").find(".active").attr("text");
			    			
		  					//刷新页面
			    			doSearchForOneStudent(offlinePay.studentBmPkid, status,offlinePay.studentPkid);
			    			$("#payItemListCount").html(0);
			    			$("#payItemListCost").html(0);
		  				  }else{
		  					  layer.msg("操作失败，请重试!");  
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
    });
};
// 生成缴费记录表头
var tableHeader = function(COST,DISCOUNT,AMOUNTRECEIVABLE,AMOUNTCOLLECTION,SHOUKUANHEJI,TUIKUANHEJI){
	$("#table_header").html("");
	$("#table_header").append('<ul>'+
			'<li class="jf-xiang pull-left">'+
			'费用<span>'+COST+'</span>'+
		'</li>'+
		'<li class="jf-xiang pull-left" style="width:160px;">'+
			'优惠方式<span>'+DISCOUNT+'</span>'+
		'</li>'+
		'<li class="jf-xiang pull-left">'+
			'应收金额<span>'+AMOUNTRECEIVABLE+'</span>'+
		'</li>'+
		'<li class="jf-xiang pull-left">'+
			'实收金额<span>'+AMOUNTCOLLECTION+'</span>'+
		'</li>'+
		'<li class="jf-xiang pull-left">'+
			'欠费金额<span>'+(AMOUNTRECEIVABLE-AMOUNTCOLLECTION).toFixed(2)+'</span>'+
		'</li>'+
		'<li class="jf-xiang pull-left">'+
			'收款合计<span>'+SHOUKUANHEJI.toFixed(2)+'</span>'+
		'</li>'+
		'<li class="jf-xiang pull-left">'+
			'退款合计<span>'+TUIKUANHEJI.toFixed(2)+'</span>'+
		'</li>'+
		'<li class="clearfix"></li>'+
	'</ul>');
};
//点击缴费记录
var payRecord = function(payItemListPkid, payItem,COST,DISCOUNT,AMOUNTRECEIVABLE,AMOUNTCOLLECTION){
	 $("#payRecordList").html("");
	 offlinePay.studentPkid = $("#stuPkid").val();
	  $("#payRecordList").append('<tr>'+
			    '<th>支付类型</th>'+
			    '<th>支付渠道</th>'+
			    '<th>支付方式</th>'+
			    '<th>金额</th>'+
			    '<th>收款人</th>'+
				'<th>缴费时间</th>'+
				'<th>收据</th>'+
				'<th>&nbsp;</th>'+
			'</tr>');
	  
	var url=_basepath+"pay/getPayOrderDetailList.json";
	$.ajax({
		  type: 'POST',
		  url: url,
		  async: false,
		  data: {
			payItemListPkid:payItemListPkid,
			studentPkid:offlinePay.studentPkid,
			"createModels":'D,DO'
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  var SHOUKUANHEJI = 0;//收款合计
				  var TUIKUANHEJI = 0;//退款合计
				  var recordDataList = result.payOrderDetailList;
				  if(recordDataList != ''){
					  $("#payRecordList").html("");
					  for(var i=0;i < recordDataList.length; i++){
						  if(i == 0){
							  
								  $("#payRecordList").append('<tr>'+
										  '<th>支付类型</th>'+
										    '<th>支付渠道</th>'+
										    '<th>支付方式</th>'+
										    '<th>金额</th>'+
										    '<th>收款人</th>'+
											'<th>缴费时间</th>'+
											'<th>收据</th>'+
											'<th>收据打印</th>'+
										'</tr>');
						  }
						  var record = '<tr>';
						  
						  var inOutPut = '';
						  if(typeof recordDataList[i].INPUT_OUTPUT == 'undefined' || recordDataList[i].INPUT_OUTPUT == 'null'){
							  inOutPut = '-';
						  }else{
							  if(recordDataList[i].INPUT_OUTPUT == 'JX'){
								  inOutPut = '收入';
								  SHOUKUANHEJI = SHOUKUANHEJI*1+recordDataList[i].totalMoney*1;
							  }else if(recordDataList[i].INPUT_OUTPUT == 'XX'){
								  inOutPut = '支出';
								  TUIKUANHEJI = TUIKUANHEJI*1+recordDataList[i].totalMoney*1;
							  }else{
								  inOutPut = '-';
							  }
							  
						  }
						  
							record+='<td>'+inOutPut+'</td>'+
									'<td>'+recordDataList[i].ORDERCREATE_MODE+'</td>'+
									'<td>'+recordDataList[i].PAY_MODE+'</td>'+	
									'<td>'+recordDataList[i].totalMoney+'</td>';	
									record+= '<td>'+recordDataList[i].NAME+'</td>';
									  record+= '<td>'+recordDataList[i].CJSJ+'</td>'+		
									'<td id="printFlag'+i+'">'+recordDataList[i].PRINTFLAG+'</td>';
							record+='<td style="cursor:pointer;" onclick="javascript:printReceipt('+i+',\''+recordDataList[i].CJSJ+'\',\''+recordDataList[i].totalMoney+'\',\''+recordDataList[i].TRANSACTION_TYPE+'\',\''+recordDataList[i].PKID+'\',\''+payItem+'\',\''+recordDataList[i].INPUT_OUTPUT+'\')">'+
							(SESSION_MENU_BUTTONS.xxjf_dy == 1?'<span class="fa fa-print" title="打印收据"></span>':'')
							+'</td>';			
							record+='</tr>';
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
	
	$('#mymoda5').modal({
			keyboard: true
		});
};

//单条收据打印
var printReceipt = function(index, createDate, totalMoney, type, orderDetailPkid, payItem, inOutPut){
	$.ajax({
		  type: 'POST',
		  url: _basepath+"receipt/getPayOrderDetail.json",
		  async: false,
		  data: {
			  detailPkids:orderDetailPkid,
			  fromSchool:offlinePay.fromSchool,
			  printFlag:'N'
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
				  var htmls = '';
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
										'<div style="height:6mm;">'+
											'<ul>'+
												'<li class="huoBi">'+
													'开票人<span>&nbsp;&nbsp;'+KAIPIAOREN+'</span>'+
												'</li>'+
												'<li class="huoBi" style="margin-left: 32%;">'+
													'学生签字<span></span>'+
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
									'<li class="huoBi" style="margin-left: 32%;">'+
										'学生签字<span></span>'+
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
				$("#printAreaWAIGUOYUshouju2Danda").html(htmls);
				
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
				$("#printAreaWAIGUOYUshoujuDanda").printArea();
				
				$("#printFlag"+index+"").html("已打");
				
			  }else{
				  layer.msg("打印失败，请重试!");
			  }
			  return false;
		  }
		});
	//批打按钮显示
	offlinePay.buttonStatus();
};

//点击批量打印
$("#payPrintBatchButton").click(function(){
	//如果没有多选,批量打印点击无效果
	if($("#payPrintBatchButton").attr("disabled") == "disabled" ){
		$("#payPrintBatchButton").attr("data-target","")
		return;
	}
	var payItemPkids = offlinePay.getPkidSelections()+"";
	var payItemPkidsArrays = payItemPkids.split(",");
	//如果已经多选
	if(payItemPkidsArrays != '' && payItemPkidsArrays.length > 0){
		$("#payBatchButton").attr("disabled",false);
		$("#payBatchButton").attr("data-target","#mymoda3");
	}
	
	$.ajax({
		  type: 'POST',
		  url: _basepath+"receipt/getPayOrderDetail.json",
		  async: false,
		  data: {
			payItemPkids:payItemPkids,
			studentPkid:offlinePay.studentPkid,
			isOffline:'true',
			printFlag:'N',
			fromSchool:offlinePay.fromSchool,
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
				  var htmls = '';
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
										'<div style="height:6mm;">'+
											'<ul>'+
												'<li class="huoBi">'+
													'开票人<span>&nbsp;&nbsp;'+KAIPIAOREN+'</span>'+
												'</li>'+
												'<li class="huoBi" style="margin-left: 32%;">'+
													'学生签字<span></span>'+
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
									'<li class="huoBi" style="margin-left: 32%;">'+
										'学生签字<span></span>'+
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
				
			  }else{
				  layer.msg("打印失败，请重试!");
			  }
			  return false;
		  }
		});
	//批打按钮显示
	offlinePay.buttonStatus();
});

//点击批量打印发票
$("#printBatchButton").click(function(){
	var pkids = offlinePay.getPkidSelections() +"";
	if(pkids == ''){
		layer.msg("请至少选择一条数据！");
		return false;
	}

	
});



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



//高度计数器
offlinePay.noGroupCount = 0;
offlinePay.groupCount = 0;


//点击批量缴费
$("#payBatchButton").click(function(){
	$("#tuiXuePay").hide();
	var pkids = offlinePay.getPkidSelections() +"";
	if(pkids == ''){
		return false;
	}

	offlinePay.AMOUNTRECEIVABLE_total = 0;
	
	var AMOUNTRECEIVABLEs = offlinePay.getAMOUNTRECEIVABLESelections() + "";
	var AMOUNTRECEIVABLEsArray = AMOUNTRECEIVABLEs.split(",");
	for (var int = 0; int < AMOUNTRECEIVABLEsArray.length; int++) {
		if(AMOUNTRECEIVABLEsArray[int] != ''){
			offlinePay.AMOUNTRECEIVABLE_total += Number(AMOUNTRECEIVABLEsArray[int]);
		}
	}
	
	$(".moneyPayBatch").val('');
	$(".moneyCardPayBatch").val('');
	$(".moneyAlipayPayBatch").val('');
	$(".moneyWechatPayBatch").val('');
	$(".moneyTTPayBatch").val('');
	$("#remarksPayBatch").val('');
	$("#cardNOPayBatch").val('');
	
	offlinePay.jj = 'SF';
	$("#batchText").html("收费");
	
	payBatch();
});

//点击批量退费
$("#payTuiBatchButton").click(function(){
	$("#tuiXuePay").show();
	var pkids = offlinePay.getPkidSelections() +"";
	if(pkids == ''){
		return false;
	}
	var statuss = offlinePay.getAMOUNTCOLLECTIONSelections() + "";
	var statusArray = statuss.split(",");
	for (var int = 0; int < statusArray.length; int++) {
		if(statusArray[int] == 0){
			layer.msg("操作失败，只有已缴过费的项目才能进行退费!");
			return false;
		}
	}
	offlinePay.AMOUNTRECEIVABLE_total = 0;
	
	var AMOUNTRECEIVABLEs = offlinePay.getAMOUNTRECEIVABLESelections() + "";
	var AMOUNTRECEIVABLEsArray = AMOUNTRECEIVABLEs.split(",");
	for (var int = 0; int < AMOUNTRECEIVABLEsArray.length; int++) {
		if(AMOUNTRECEIVABLEsArray[int] != ''){
			offlinePay.AMOUNTRECEIVABLE_total += Number(AMOUNTRECEIVABLEsArray[int]);
		}
	}
	
	$(".moneyPayBatch").val('');
	$(".moneyCardPayBatch").val('');
	$(".moneyAlipayPayBatch").val('');
	$(".moneyWechatPayBatch").val('');
	$(".moneyTTPayBatch").val('');
	$("#remarksPayBatch").val('');
	$("#cardNOPayBatch").val('');
	
	offlinePay.jj = 'TF';
	$("#batchText").html("退费");
	
	payBatch();
	var selectPayItemPkids = offlinePay.getIdSelections()+"";
	var payItemPkidArrays = selectPayItemPkids.split(",");
	for(var i=0; i<payItemPkidArrays.length; i++){
		$("#daikuanjine_"+i).hide();			
		$("#daikuanhuanjiao_"+i).hide();			
	}
	$("input[name='changeAmountColl']").each(function(){
		$(this).css("display","none");
		
	});
});

function callBackFunction(){
	alert(1);
}

$("#tuiXuePay").click(function(){
	BootstrapDialog.show({ // 显示需要提交的表单。
		title : '提示信息',
		message : '如需调整应收的缴费项，请先调整应收金额后再进行退费退学操作！',
		closable : true,
		buttons : [
				{
					label : '继续',
					cssClass : 'btn-danger',
					action : function(dialogRef) {
						dialogRef.close();
						tuixueStatus = false;
						var flag = $("#payButtonBatch").click();
						if(tuixueStatus){
							$.ajax({
								type : "post",
								dataType : "json",
								async: false,
								url : _basepath+ 'pay/tuixue.json',
								data : {"T_STUDENT_BM_PKID":$("#stuBmPkid").val()},
								success : function(data) {
									if(data.result == 'success'){
										 $("#studentZXZT").html("退学");
									}
								},
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									alert(errorThrown);
								}
							});
						}
					}
				}, {
					label : '取消',
					cssClass : 'btn-default',
					action : function(dialogRef) {
						dialogRef.close();
					}
				} ]
	});
	
});

var payBatch = function(){

	var payBatchArrays = '';
	var selectPayItemPkids = offlinePay.getPkidSelections()+"";
	var payItemPkidArrays = selectPayItemPkids.split(",");
	var payItemNamess = offlinePay.getNameSelections()+"";
	var payItemNameArrays = payItemNamess.split(",");
	var payItemChildNamess = offlinePay.getChildNameSelections()+"";
	var payItemChildNameArrays = payItemChildNamess.split(",");
	var AMOUNTRECEIVABLEss = offlinePay.getAMOUNTRECEIVABLESelections()+"";
	var AMOUNTRECEIVABLEArrays = AMOUNTRECEIVABLEss.split(",");
	var AMOUNTCOLLECTIONss = offlinePay.getAMOUNTCOLLECTIONSelections()+"";
	var AMOUNTCOLLECTIONArrays = AMOUNTCOLLECTIONss.split(",");
	var PAYSTYLEBIANMAS = offlinePay.getPAYSTYLEBIANMASelections() + "";
	var PAYSTYLEBIANMAArrays = PAYSTYLEBIANMAS.split(",");
	var LOANMONEYss = offlinePay.getLOANMONEYSelections()+"";
	var LOANMONEYArrays = LOANMONEYss.split(",");
	var moneyTotalBatchs = 0;
	
	
	//如果已经多选
	if(offlinePay.getIdSelections() !=''){
//	if(payItemPkidArrays != '' && payItemPkidArrays.length > 0){
		$("#payBatchButton").attr("disabled",false);
		$("#payBatchButton").attr("data-target","#mymoda1");
	}
	
	//批量索引
	offlinePay.moneyBatchTextIndex = payItemPkidArrays.length;
	
	payBatchArrays += '<div style="margin-bottom: 15px;">'+
	'<label for="">缴费项目</label>';
	
	for(var i=0; i<payItemPkidArrays.length; i++){
		if(payItemPkidArrays[i] != ''){
			//剩余应缴费用 有贷款的，欠费金额-贷款金额，出现负数时，默认0，0项保存时过滤此项
			var surplusMoney = Number(AMOUNTRECEIVABLEArrays[i]) - Number(AMOUNTCOLLECTIONArrays[i]);
			if(surplusMoney<0){
				surplusMoney = 0;
			}
			var surplusMoneyHidden = Number(AMOUNTRECEIVABLEArrays[i]) - Number(AMOUNTCOLLECTIONArrays[i]);
			payBatchArrays += '<div action="" class="form-inline">'+
				'<div class="form-group col-xs-3" style="line-height:42px;">'+
					'<span>'+payItemNameArrays[i]+(payItemChildNameArrays[i] !='' && payItemChildNameArrays[i] !='undefined'  && payItemChildNameArrays[i] !=' '&& typeof payItemChildNameArrays[i] !='undefined'? '-'+payItemChildNameArrays[i] : '' )+'</span>'+
				'</div>'+
				'<div class="form-group col-xs-8">'+
					'<span>金额 </span>'+
					'<span style="padding-left:20px;"><input type="text" id="yingjiaojine_'+i+'" '+(
							offlinePay.jj == "SF"? ' value="'+surplusMoney.toFixed(2)+'" ' : ''		
					)+' class="moneyPayBatch moneyBatchText'+i+' form-control" style="width: 83px;" placeholder="金额" onkeyup="clearNoNum(this)"/>&nbsp;'+
					'<input type="hidden" id="qianfeijine_'+i+'" value="'+surplusMoneyHidden.toFixed(2)+'" >'+
					'<input type="hidden" id="yingjiaojinehidden_'+i+'" value="'+surplusMoney.toFixed(2)+'" >'+
					'<input type="hidden" id="payItemPkidArrays_'+i+'" value="'+payItemPkidArrays[i]+'" >'+					
					'</span>'+
					
				'</div>';
			payBatchArrays += '<div class="clearfix"></div>'+
			'</div>';
			
			moneyTotalBatchs += surplusMoney;
		}
		
	}
	payBatchArrays += '</div>';
	
	payBatchArrays += '<div style="margin-bottom: 15px;"><label for="">合计</label>';
	
	payBatchArrays += '<div action="" class="form-inline">'+
		'<div class="form-group col-xs-3">'+
			'<span id="moneyTotalBatch" style="color:#f0ad4e !important;font-weight:bold;font-size: 20px;">'+(offlinePay.jj == "SF"? moneyTotalBatchs.toFixed(2) : '0')+'</span>'+
		'</div><div class="clearfix"></div></div></div>';
	
	payBatchArrays += '<div style="margin-bottom: 15px;">'+
	'<label for="">支付方式</label>';
	payBatchArrays += '<div action="" class="form-inline" style="margin-bottom: 10px;">'+
		'<div class="form-group col-xs-5">'+
			'<label><input class="payTypeBatch" name="payTypeBatch" type="radio" value="" /></label><span>现金</span>'+
		'</div><div class="clearfix"></div></div>';
	
	payBatchArrays += '<div action="" class="form-inline" style="margin-bottom: 10px;" >'+
	'<div class="form-group col-xs-5">'+
	'<label><input class="payTypeBatch" name="payTypeBatch" type="radio" value="" checked /></label><span>银行卡</span>'+
	'<span style="padding-left:20px;"><input type="text" name="" value="" class="form-control" style="height:34px;" placeholder="卡号" id="cardNOPayBatch"/></span>'+
	'</div><div class="clearfix"></div></div>';
	
	/*payBatchArrays += '<div action="" class="form-inline" style="margin-bottom: 10px;'+isDisplay+'" >'+
	'<div class="form-group col-xs-5">'+
	'<label><input class="payTypeBatch" name="payTypeBatch" type="radio" value="" /></label><span>电汇</span>'+
	'</div><div class="clearfix"></div></div>';*/
	
	var isDisplay = offlinePay.jj == 'TF' ? ' display:none; ' : '';
	payBatchArrays += '<div action="" class="form-inline" style="margin-bottom: 10px;'+isDisplay+'" >'+
	'<div class="form-group col-xs-5">'+
	'<label><input class="payTypeBatch" name="payTypeBatch" type="radio" value="" /></label><span>支付宝</span>'+
	'<span style="padding-left:20px;"><input type="text" name="" value="" class="form-control" style="height:34px;" placeholder="支付宝付款码" id="zfb_txm_batch"/></span>'+
	'</div><div class="clearfix"></div></div>';
	payBatchArrays += '<div action="" class="form-inline" style="margin-bottom: 10px;'+isDisplay+'" >'+
	'<div class="form-group col-xs-5">'+
	'<label><input class="payTypeBatch" name="payTypeBatch" type="radio" value="" /></label><span>微信</span>'+
	'<span style="padding-left:35px;"><input type="text" name="" value="" class="form-control" style="height:34px;" placeholder="微信付款码" id="wx_txm_batch"/></span>'+
	'</div><div class="clearfix"></div></div>';
	payBatchArrays += '<div action="" class="form-inline" style="margin-bottom: 10px;" >'+
	'<div class="form-group col-xs-5">'+
	'<label><input class="payTypeBatch" name="payTypeBatch" type="radio" value="" /></label><span>电汇</span>'+
	'</div><div class="clearfix"></div></div>';
	payBatchArrays += '</div>';
		
	payBatchArrays += '<div style="margin-bottom: 15px;"><label for="">备注</label>';
	payBatchArrays += '<div action="" class="form-inline">'+
		'<div class="form-group col-xs-3">'+
			'<span><input  id="remarksPayBatch" type="text" name=""  value="" class="form-control" placeholder="备注" style="width:450px;"/></span>'+
		'</div><div class="clearfix"></div></div></div>';
	
	//总金额赋初值
	$("#moneyTotalBatch").html(0);
	$("#moneyCardTotalBatch").html(0);
	$("#moneyAlipayTotalBatch").html(0);
	$("#moneyWechatTotalBatch").html(0);
	$("#moneyTTTotalBatch").html(0);
	
	$("#payBatchArray").html(payBatchArrays);
	
	if(offlinePay.jj == 'TF'){
		//隐藏支付宝和微信
		$(".payBatchTui").hide();
	}else{
		$(".payBatchTui").show();
	}
	
	
	//绑定输入框事件
	$(".moneyPayBatch").bind('input propertychange', function() {
		var moneyTotalBatchs = 0;
		$(".moneyPayBatch").each(function(){
			clearNoNum(this);
			moneyTotalBatchs += Number($(this).val());
		});
		$("#moneyTotalBatch").html(moneyTotalBatchs.toFixed(2));

	});
	$(".moneyCardPayBatch").bind('input propertychange', function() {
		
		var moneyCardTotalBatchs = 0;
		$(".moneyCardPayBatch").each(function(){
			clearNoNum(this);
			moneyCardTotalBatchs += Number($(this).val());
		});
		$("#moneyCardTotalBatch").html(moneyCardTotalBatchs);

		
	});
	$(".moneyAlipayPayBatch").bind('input propertychange', function() {
		
		var moneyAlipayPayBatchs = 0;
		$(".moneyAlipayPayBatch").each(function(){
			clearNoNum(this);
			moneyAlipayPayBatchs += Number($(this).val());
		});
		$("#moneyAlipayTotalBatch").html(moneyAlipayPayBatchs);
		
	});
	$(".moneyWechatPayBatch").bind('input propertychange', function() {
		
		var moneyWechatTotalBatchs = 0;
		$(".moneyWechatPayBatch").each(function(){
			clearNoNum(this);
			moneyWechatTotalBatchs += Number($(this).val());
		});
		$("#moneyWechatTotalBatch").html(moneyWechatTotalBatchs);
	});
	
	$(".moneyTTPayBatch").bind('input propertychange', function() {
		
		var moneyTTTotalBatchs = 0;
		$(".moneyTTPayBatch").each(function(){
			clearNoNum(this);
			moneyTTTotalBatchs += Number($(this).val());
		});
		$("#moneyTTTotalBatch").html(moneyTTTotalBatchs);
	});
	$('#mymoda1').modal({
			keyboard: true
		});

	
	$("#cardNOPayBatch").val('');
	setTimeout(function(){
		$("#cardNOPayBatch").focus();
	}, 300);
	$("#zfb_txm_batch").val('');
	$("#zfb_txm_batch").attr("disabled","disabled");
	$("#wx_txm_batch").val('');
	$("#wx_txm_batch").attr("disabled","disabled");
	//批量缴费页面的checkbox点击事件
	/*$("input[name='changeAmountColl']").click(function(){
		//判断页面是否选中checkbox
		var idCheck = $(this).is(":checked");
		//欠费金额
		var qianfeijine = $("#qianfeijine_"+this.value).val();
		//页面加载出来的应交金额
		var yingjiaojine = $("#yingjiaojinehidden_"+this.value).val();
		//如果选中金额列展示 有贷款的，欠费金额-贷款金额，出现负数时，默认0，0项保存时过滤此项
		//如果不选中金额列展示欠费金额
		if(idCheck==true){
			$("#yingjiaojine_"+this.value).val(yingjiaojine);
		}else{
			$("#yingjiaojine_"+this.value).val(qianfeijine);
		}
		//将缴费项的金额合计
		var moneyTotalBatchUpdate = 0;				
		for(var i=0; i<payItemPkidArrays.length; i++){
			var moneyTotalBatch =  $("#yingjiaojine_"+i).val();
			moneyTotalBatchUpdate+=Number(moneyTotalBatch);		
		}		
		//改变合计金额
		var moneyTotalBatchresult = moneyTotalBatchUpdate.toFixed(2);
		$("#moneyTotalBatch").html(moneyTotalBatchresult);
		
	});*/
	
	$(".payTypeBatch").click(function(){
		var isCheckPayTypeMoney = $('input:radio[name=payTypeBatch]')[0].checked;
		var isCheckPayTypeCard = $('input:radio[name=payTypeBatch]')[1].checked;
		var isCheckPayTypeAlipay = $('input:radio[name=payTypeBatch]')[2].checked;
		var isCheckPayTypeWechat = $('input:radio[name=payTypeBatch]')[3].checked;
		var isCheckPayTypeTT = $('input:radio[name=payTypeBatch]')[4].checked;
		if(isCheckPayTypeMoney){
			$("#cardNOPayBatch").val('');
			$("#cardNOPayBatch").attr("disabled","disabled");
			$("#zfb_txm_batch").val('');
			$("#zfb_txm_batch").attr("disabled","disabled");
			$("#wx_txm_batch").val('');
			$("#wx_txm_batch").attr("disabled","disabled");
			
		}else if(isCheckPayTypeCard){
			$("#zfb_txm_batch").val('');
			$("#zfb_txm_batch").attr("disabled","disabled");
			$("#wx_txm_batch").val('');
			$("#wx_txm_batch").attr("disabled","disabled");
			$("#cardNOPayBatch").removeAttr("disabled");
			
		}else if(isCheckPayTypeAlipay){
			$("#cardNOPayBatch").val('');
			$("#cardNOPayBatch").attr("disabled","disabled");
			$("#wx_txm_batch").val('');
			$("#wx_txm_batch").attr("disabled","disabled");
			$("#zfb_txm_batch").removeAttr("disabled");
		}else if(isCheckPayTypeWechat){
			$("#cardNOPayBatch").val('');
			$("#cardNOPayBatch").attr("disabled","disabled");
			$("#zfb_txm_batch").val('');
			$("#zfb_txm_batch").attr("disabled","disabled");
			$("#wx_txm_batch").removeAttr("disabled");
		}else if(isCheckPayTypeTT){
			$("#cardNOPayBatch").val('');
			$("#cardNOPayBatch").attr("disabled","disabled");
			$("#zfb_txm_batch").val('');
			$("#zfb_txm_batch").attr("disabled","disabled");
			$("#wx_txm_batch").val('');
			$("#wx_txm_batch").attr("disabled","disabled");
		}
	});
};




//添加缴费项目名单
$("#insertPayItemListButton").click(function(){
	 var stuBXPkid = $("#stuBXPkid").val();//班型pkid
	 var stuRXNFPkid = $("#stuRXNFPkid").val();//入学年份pkid
	 var stuPkid = $("#stuPkid").val();//学生pkid
	 var stuBmPkid = $("#stuBmPkid").val();//学生副表pkid
	 offlinePay.studentPkid = stuPkid;
	 offlinePay.studentBmPkid = stuBmPkid;
	 offlinePay.studentRXNFPkid = stuRXNFPkid;
	 offlinePay.studentBxPkid = stuBXPkid;
	if(stuBXPkid==null||stuBXPkid==''||stuRXNFPkid==null||stuRXNFPkid==''){
		layer.msg("未知学生！");
		return false;
	}
	
	$("#payitem_pkid_select").html("");
	$("#payitem_pkid_select").html("<option value=''>选择缴费类型</option>");
	$("#cost_html").html('<input type="text" name="" value="" class="form-control" placeholder="金额" id="cost_change"  onkeyup="clearNoNum(this)"/>');
	$("#savePayItemListButton").attr("disabled",false);
	$.ajax({
		  type: 'POST',
		  url: _basepath+"pay/getAllPayItemListList3.json",
		  async: false,
		  data: {
			  studentPkid: offlinePay.studentPkid,
			  studentBmPkid: offlinePay.studentBmPkid,
			  stuBXPkid: stuBXPkid,
			  stuRXNFPkid: stuRXNFPkid
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  var pdTypeList = result.pdTypeList;
				  if(pdTypeList!=null){
					  for (var i = 0; i < pdTypeList.length; i++) {
						  var payItem = pdTypeList[i];
						  $("#payitem_pkid_select").append("<option value='"+payItem.PAY_TYPE_PKID+"' T_PAY_ITEM_PKID='"+payItem.PKID+"'>"+payItem.pdTypeName+"</option>");
					  }	
				  }				  			  
				  $('#mymoda7').modal({
						keyboard: true
				  });
			  }
			  return false;
		  }
		});
});


$("#payitem_pkid_select").change(function(){
	var T_PAY_ITEM_PKID = $("#payitem_pkid_select").find("option:selected").attr("T_PAY_ITEM_PKID");
	var PAY_TYPE_PKID = $("#payitem_pkid_select").val();
	$.ajax({
		  type: 'POST',
		  url: _basepath+"pay/getItemCost.json",
		  async: false,
		  data: {
			  studentPkid: offlinePay.studentPkid,
			  STUDENT_BM_PKID: $("#stuBmPkid").val(),
			  T_PAY_ITEM_PKID: T_PAY_ITEM_PKID
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				 	$("#cost_change").val(result.dbCost);		  			  
			  }
			  return false;
		  }
		});
});

//添加缴费项目名单 -- 点击提交
var submitInsertPayItemListButton = function(){
	 var cost = $("#cost_change").val();//金额

	var payitem_pkid_type= $("#payitem_pkid_select  option:selected").val();
	var T_PAY_ITEM_PKID = $("#payitem_pkid_select  option:selected").attr("T_PAY_ITEM_PKID");

	if(offlinePay.studentPkid == ''){
		layer.msg("未知学生！");
		return false;
	}
	if(typeof payitem_pkid_type == 'undefined' || payitem_pkid_type == ''){
		layer.msg("请先选择缴费类型！");
		return false;
	}	
	if(typeof cost == 'undefined' || cost == ''){
		layer.msg("费用不能为空！");
		return false;
	}else if(Number(cost) <= 0){
		layer.msg("费用必须大于0！");
		return false;
	}
	$("#savePayItemListButton").attr("disabled",true);
	$.ajax({
		  type: 'POST',
		  url: _basepath+"pay/insertPayItemList.json",
		  async: false,
		  data: {
			  stuPkid:offlinePay.studentPkid,
			  stuBmPkid:offlinePay.studentBmPkid ,
			  stuRXNFPkid:offlinePay.studentRXNFPkid,
			  stuBXPkid:offlinePay.studentBxPkid,
			  payitem_pkid_type:payitem_pkid_type,
			  COST:cost,
			  T_PAY_ITEM_PKID:T_PAY_ITEM_PKID
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  layer.msg("添加成功！");
				  $("#cancelInsertPayItemListButton").click();				  
				  //刷新页面
				  var status = $("#statusSelected").find(".active").attr("text");
				  doSearchForOneStudent(offlinePay.studentBmPkid, status,offlinePay.studentPkid);
			  }else{
				  layer.msg(result.failMsg);
			  }
			  return false;
		  }
		});
};


//选择子项目时触发
$("#payitem_child").change(function(){
	  $("#cost_html").html('<input type="text" name="" value="" readonly="readonly" class="form-control" placeholder="金额" id="cost"  onkeyup="clearNoNum(this)"/>');
	  var payitem_pkid = $("#payitem_child  option:selected").val();
	  var ITEMLIST_CREATEMODE = $("#payitem_child  option:selected").attr("ITEMLIST_CREATEMODE");
	  putCostHtml(payitem_pkid,ITEMLIST_CREATEMODE,$("#cost_html"),"cost","");
});

//选择子项目时触发
$("#payitem_child_change").change(function(){
	  $("#cost_change_html").html('<input type="text" name="" value="" readonly="readonly" class="form-control" placeholder="金额" id="cost_change"  onkeyup="clearNoNum(this)"/>');
	  var payitem_pkid = $("#payitem_child_change  option:selected").val();
	  var ITEMLIST_CREATEMODE = $("#payitem_child_change  option:selected").attr("ITEMLIST_CREATEMODE");
	  var PAY_ITEM_PKID_YET = $("#payitem_child_change  option:selected").attr("PAY_ITEM_PKID_YET");
	  putCostHtml(payitem_pkid,ITEMLIST_CREATEMODE,$("#cost_change_html"),"cost_change",'readonly="readonly"',PAY_ITEM_PKID_YET);
});

//给金额赋内容
var putCostHtml = function(payitem_pkid,ITEMLIST_CREATEMODE,costObj,costid,readonly,PAY_ITEM_PKID_YET){
	 if(ITEMLIST_CREATEMODE == 1){//导入方式
		 costObj.html('<input type="text" name="" value="" class="form-control" placeholder="金额" id="'+costid+'"  '+readonly+' onkeyup="clearNoNum(this)"/>');
	  }else if(ITEMLIST_CREATEMODE == 2){//规则生成
		  
		  $.ajax({
			  type: 'POST',
			  url: _basepath+"pay/getRuleCOST.json",
			  async: false,
			  data: {
				studentPkid:offlinePay.studentPkid,
				PAY_ITEM_PKID:payitem_pkid,
				PAY_ITEM_PKID_YET:PAY_ITEM_PKID_YET
			  },
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  if(result.COST == 0){//未匹配到规则
						  costObj.html('<input type="text" name=""  class="form-control" placeholder="金额" id="'+costid+'"  onkeyup="clearNoNum(this)"/>');
					  }else{
						  costObj.html('<input type="text" name="" value="'+result.COST+'" RULE_COST_UNIT="'+result.RULE_COST_UNIT+'" class="form-control" placeholder="金额" id="'+costid+'"  '+readonly+'  onkeyup="clearNoNum(this)"/>');
					  }
				  }
				  return false;
			  }
			});
		  
	  }
};


//统计
var runCountAndMoney = function(type, row){
	var payItemListCountHtml = $("#payItemListCount").html();
	var payItemListCount = payItemListCountHtml == "" ? 0 : Number(payItemListCountHtml);
	if(type == 1){
		payItemListCount = payItemListCount + 1;
	}else{
		payItemListCount = payItemListCount - 1;
	}
	$("#payItemListCount").html(" "+payItemListCount+ " ");
	
	var payItemListCostHtml = $("#payItemListCost").html();
	var payItemListCost = payItemListCostHtml == "" ? 0 : Number(payItemListCostHtml);
	if(type == "1"){
		payItemListCost += Number(row.AMOUNTRECEIVABLE - row.AMOUNTCOLLECTION);
	}else{
		payItemListCost -= Number(row.AMOUNTRECEIVABLE - row.AMOUNTCOLLECTION);
	}
	
	$("#payItemListCost").html(payItemListCost.toFixed(2));
};

$(".payType").click(function(){
	
	var isCheckPayTypeMoney = $('input:radio[name=payType]')[0].checked;
	var isCheckPayTypeCard = $('input:radio[name=payType]')[1].checked;
	var isCheckPayTypeAlipay = $('input:radio[name=payType]')[2].checked;
	var isCheckPayTypeWechat = $('input:radio[name=payType]')[3].checked;
	var isCheckPayTypeTT = $('input:radio[name=payType]')[4].checked;
	if(isCheckPayTypeMoney){
		$("#moneyCardPay").val('');
		$("#moneyCardPay").attr("disabled","disabled");
		$("#cardNOPay").val('');
		$("#cardNOPay").attr("disabled","disabled");
		$("#moneyAlipayPay").val('');
		$("#moneyAlipayPay").attr("disabled","disabled");
		$("#zfb_txm").val('');
		$("#zfb_txm").attr("disabled","disabled");
		$("#moneyWechatPay").val('');
		$("#moneyWechatPay").attr("disabled","disabled");
		$("#wx_txm").val('');
		$("#wx_txm").attr("disabled","disabled");
		$("#moneyPay").removeAttr("disabled");
		if(offlinePay.defaultDormTui){
			var defaultMoneyTui = offlinePay.defaultDormMoneyTui < 0 ? 0 : offlinePay.defaultDormMoneyTui;
			$("#moneyPay").val(defaultMoneyTui);
		}
		$("#moneyTTPay").val('');
		$("#moneyTTPay").attr("disabled","disabled");
	}else if(isCheckPayTypeCard){
		$("#moneyPay").val('');
		$("#moneyPay").attr("disabled","disabled");
		$("#cardNOPay").val('');
		$("#cardNOPay").attr("disabled","disabled");
		$("#moneyAlipayPay").val('');
		$("#moneyAlipayPay").attr("disabled","disabled");
		$("#zfb_txm").val('');
		$("#zfb_txm").attr("disabled","disabled");
		$("#moneyWechatPay").val('');
		$("#moneyWechatPay").attr("disabled","disabled");
		$("#wx_txm").val('');
		$("#wx_txm").attr("disabled","disabled");
		$("#moneyCardPay").removeAttr("disabled");
		$("#cardNOPay").removeAttr("disabled");
		if(offlinePay.defaultDormTui){
			var defaultMoneyTui = offlinePay.defaultDormMoneyTui < 0 ? 0 : offlinePay.defaultDormMoneyTui;
			$("#moneyCardPay").val(defaultMoneyTui);
		}
		$("#moneyTTPay").val('');
		$("#moneyTTPay").attr("disabled","disabled");
	}else if(isCheckPayTypeAlipay){
		$("#moneyPay").val('');
		$("#moneyPay").attr("disabled","disabled");
		$("#moneyCardPay").val('');
		$("#moneyCardPay").attr("disabled","disabled");
		$("#cardNOPay").val('');
		$("#cardNOPay").attr("disabled","disabled");
		$("#moneyWechatPay").val('');
		$("#moneyWechatPay").attr("disabled","disabled");
		$("#wx_txm").val('');
		$("#wx_txm").attr("disabled","disabled");
		$("#moneyAlipayPay").removeAttr("disabled");
		$("#zfb_txm").removeAttr("disabled");
		if(offlinePay.defaultDormTui){
			var defaultMoneyTui = offlinePay.defaultDormMoneyTui < 0 ? 0 : offlinePay.defaultDormMoneyTui;
			$("#moneyAlipayPay").val(defaultMoneyTui);
		}
		$("#moneyTTPay").val('');
		$("#moneyTTPay").attr("disabled","disabled");
	}else if(isCheckPayTypeWechat){
		$("#moneyPay").val('');
		$("#moneyPay").attr("disabled","disabled");
		$("#moneyCardPay").val('');
		$("#moneyCardPay").attr("disabled","disabled");
		$("#cardNOPay").val('');
		$("#cardNOPay").attr("disabled","disabled");
		$("#moneyAlipayPay").val('');
		$("#moneyAlipayPay").attr("disabled","disabled");
		$("#zfb_txm").val('');
		$("#zfb_txm").attr("disabled","disabled");
		$("#wx_txm").removeAttr("disabled");
		$("#moneyWechatPay").removeAttr("disabled");
		if(offlinePay.defaultDormTui){
			var defaultMoneyTui = offlinePay.defaultDormMoneyTui < 0 ? 0 : offlinePay.defaultDormMoneyTui;
			$("#moneyWechatPay").val(defaultMoneyTui);
		}
		$("#moneyTTPay").val('');
		$("#moneyTTPay").attr("disabled","disabled");
	}else if(isCheckPayTypeTT){
		$("#moneyPay").val('');
		$("#moneyPay").attr("disabled","disabled");
		$("#moneyCardPay").val('');
		$("#moneyCardPay").attr("disabled","disabled");
		$("#cardNOPay").val('');
		$("#cardNOPay").attr("disabled","disabled");
		$("#moneyAlipayPay").val('');
		$("#moneyAlipayPay").attr("disabled","disabled");
		$("#zfb_txm").val('');
		$("#zfb_txm").attr("disabled","disabled");
		$("#moneyWechatPay").val('');
		$("#moneyWechatPay").attr("disabled","disabled");
		$("#wx_txm").val('');
		$("#wx_txm").attr("disabled","disabled");
		if(offlinePay.defaultDormTui){
			var defaultMoneyTui = offlinePay.defaultDormMoneyTui < 0 ? 0 : offlinePay.defaultDormMoneyTui;
			$("#moneyTTPay").val(defaultMoneyTui);
		}
//		$("#moneyTTPay").val('');
		$("#moneyTTPay").removeAttr("disabled");
	}
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
};

function clearNoNum(obj){  
    //修复第一个字符是小数点 的情况.  
    if(obj.value !=''&& obj.value.substr(0,1) == '.'){  
        obj.value="";  
    }  
    obj.value = obj.value.replace(/^0*(0\.|[1-9])/, '$1');//解决 粘贴不生效  
    obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的       
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");      
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数       
    if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额  
        if(obj.value.substr(0,1) == '0' && obj.value.length == 2){  
            obj.value= obj.value.substr(1,obj.value.length);      
        }  
    }      
} 

$(function(){
	
	offlinePay.getTab();
	offlinePay.getStudentPayTab();
	offlinePay.getStudentPayTabQH();
	
});

