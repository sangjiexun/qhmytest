/**
 * 结账汇总统计页面对象
 */
//@ sourceURL=theInvoicing_list.js
(function($, window) {
	var theInvoicing_list = {};
	
	//日期插件参数设置
	laydate.render({
		elem:'#DATESTART',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'datetime',
		max:1,
		format: 'yyyy-MM-dd HH:mm:ss',
		festival: true, //显示节日
		start: 0
	});
	laydate.render({
		elem:'#DATEEND',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'datetime',
		max:1,
		format: 'yyyy-MM-dd HH:mm:ss',
		festival: true, //显示节日
		start: 0
	});
	
	//两小数相加解决精度出现的问题
	theInvoicing_list.numAdd=function (num1, num2) {//要相加的两个数
		var baseNum, baseNum1, baseNum2;
			 try {
		baseNum1 = num1.toString().split(".")[1].length;
		} catch (e) {
		baseNum1 = 0;
		}
		try {
		baseNum2 = num2.toString().split(".")[1].length;
		} catch (e) {
		baseNum2 = 0;
		}
		baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
		return (num1 * baseNum + num2 * baseNum) / baseNum;
	};
	
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		var DATESTART = $("#DATESTART").val();
		var DATEEND = $("#DATEEND").val();
		var date1 = new Date(Date.parse(DATESTART));
		var date2 = new Date(Date.parse(DATEEND));
		if(date1>date2){
			layer.msg("结束时间不能早于开始时间");
			return false;
		}
		$("#theInvoicingTable").bootstrapTable('destroy');
		theInvoicing_list.getTab();
	});
	
	//点击按钮导出
	$("#checkOut").click(function(){
		var DATESTART = $("#DATESTART").val();
		var DATEEND = $("#DATEEND").val();
		var date1 = new Date(Date.parse(DATESTART));
		var date2 = new Date(Date.parse(DATEEND));
		if(date1>date2){
			layer.msg("结束时间不能早于开始时间");
			return false;
		}
		/*var shoufeixuenian = $('#shoufeixuenian').val();*/
		var shoufeiyuan = $('#shoufeiyuan').val();
		var xuenianname = $('#shoufeixuenian option:checked').text() == '学年' ? '' : $('#shoufeixuenian option:checked').text();
		var shoufeiname = $('#shoufeiyuan option:checked').text() == '收费员' ? '' : $('#shoufeiyuan option:checked').text();
		window.location.href=encodeURI(_basepath+'reportStat/theInvoicingExcel.json?shoufeiyuan='+
				shoufeiyuan+"&&DATEEND="+DATEEND+"&&DATESTART="+DATESTART+"&&xuenianname="+xuenianname
				+"&&shoufeiname="+shoufeiname);
	});
	
	// 获取bootStrapTable表格数据,及参数设置
	theInvoicing_list.getTab = function() {
		$("#theInvoicingTable").html('');
		var htmlHead = '<tr>'+
					'<th>收费员</th>'+
					/*'<th>学年</th>'+*/
					'<th>缴费类型</th>'+
					'<th>实收总金额</th>'+
					'<th>现金总金额</th>'+
					'<th>银行卡总金额</th>'+
					'<th>电汇总金额</th>'+
					'<th>支付宝总金额</th>'+
					'<th>微信总金额</th>'+
				'</tr>';
		var url = _basepath + "reportStat/getTheInvoicingTable.json";
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  data: {
				 /* shoufeixuenian:$("#shoufeixuenian").val(),*/
				  shoufeiyuan:$("#shoufeiyuan").val(),
				  DATESTART:$("#DATESTART").val(),
				  DATEEND:$("#DATEEND").val()
			  },
			  dataType : "json",
			  success : function(result) {
				  var htmlArray = new Array();
				  if(result.result == 'success'){
					  var list = result.list;
					  var cjrs = new Array();
						  
					  //实收总额
					  var shishoumoneyTotals = 0;
					  //现金总额
					  var cashmoneyTotals = 0;
					  //银行卡总额
					  var cardmoneyTotals = 0;
					  //电汇总额
					  var ttmoneyTotals = 0;
					  //微信总额
					  var wxmoneyTotals = 0;
					  //支付宝总额
					  var zfbmoneyTotals = 0;

					  //实收总额小计
					  var shishoumoneyTotalsXiaoji = 0;
					  //现金总额小计
					  var cashmoneyTotalXiaoji = 0;
					  //银行卡总额小计
					  var cardmoneyTotalXiaoji = 0;
					  //电汇总额小计
					  var ttmoneyTotalXiaoji = 0;
					  //微信总额小计
					  var wxmoneyTotalXiaoji = 0;
					  //支付宝总额小计
					  var zfbmoneyTotalXiaoji = 0;
					  //行数
					  var count = 0;
					  for (var i = 0; i < list.length; i++) {
						  var data = list[i];
						  //是否包含该操作员
						  var isContainCJR = $.inArray(data.USER_ID, cjrs);
						  
						  //实收总额
						  var shishoumoneyTotal = data.SHISHOUMONEY;
						  //现金总额
						  var cashmoneyTotal = data.CASH_MONEY;
						  //银行卡总额
						  var cardmoneyTotal = data.CARD_MONEY;
						  //电汇总额
						  var ttmoneyTotal = data.TT_MONEY;
						  //微信总额
						  var wxmoneyTotal = data.WX_MONEY;
						  //支付宝总额
						  var zfbmoneyTotal = data.ZFB_MONEY;
						  
						  
						  
						  //总计 start=========================================================
						  shishoumoneyTotals = theInvoicing_list.numAdd(shishoumoneyTotal,shishoumoneyTotals);
						  cashmoneyTotals = theInvoicing_list.numAdd(cashmoneyTotals,cashmoneyTotal);
						  cardmoneyTotals = theInvoicing_list.numAdd(cardmoneyTotals,cardmoneyTotal);
						  ttmoneyTotals = theInvoicing_list.numAdd(ttmoneyTotals,ttmoneyTotal);
						  wxmoneyTotals = theInvoicing_list.numAdd(wxmoneyTotals,wxmoneyTotal);
						  zfbmoneyTotals = theInvoicing_list.numAdd(zfbmoneyTotals,zfbmoneyTotal);
						  //总计 end =========================================================
						  
						  htmlArray.push('<tr>'+
										'<td width="30%">'+(isContainCJR >=0 ? "&nbsp;" : data.NAME)+'</td>'+
										/*'<td>'+(data.SCHOOL_YEAR_NAME == null || data.SCHOOL_YEAR_NAME == 'null' || data.SCHOOL_YEAR_NAME == 'undefined' ? '' : data.SCHOOL_YEAR_NAME)+'</td>'+*/
										'<td>'+(data.PAY_STYLE_NAME == null || data.PAY_STYLE_NAME == 'null' || data.PAY_STYLE_NAME == 'undefined' ? '' : data.PAY_STYLE_NAME)+'</td>'+
										'<td>'+(shishoumoneyTotal == 0 ? '':shishoumoneyTotal)+'</td>'+
										'<td>'+(data.CASH_MONEY == 0 ? '':data.CASH_MONEY)+'</td>'+
										'<td>'+(data.CARD_MONEY == 0 ? '':data.CARD_MONEY)+'</td>'+
										'<td>'+(data.TT_MONEY == 0 ? '':data.TT_MONEY)+'</td>'+
										'<td>'+(data.ZFB_MONEY == 0 ? '':data.ZFB_MONEY)+'</td>'+
										'<td>'+(data.WX_MONEY == 0 ? '':data.WX_MONEY)+'</td>'+
									'</tr>');
						  count ++;
						  //换收费员时重置小计
						  if(isContainCJR == -1){
							  shishoumoneyTotalsXiaoji = 0;
							  cashmoneyTotalXiaoji = 0;
							  cardmoneyTotalXiaoji = 0;
							  ttmoneyTotalXiaoji = 0;
							  wxmoneyTotalXiaoji = 0;
							  zfbmoneyTotalXiaoji = 0; 
						  }

						  shishoumoneyTotalsXiaoji = theInvoicing_list.numAdd(shishoumoneyTotal,shishoumoneyTotalsXiaoji);
						  cashmoneyTotalXiaoji = theInvoicing_list.numAdd(cashmoneyTotalXiaoji,cashmoneyTotal);
						  cardmoneyTotalXiaoji = theInvoicing_list.numAdd(cardmoneyTotalXiaoji,cardmoneyTotal);
						  ttmoneyTotalXiaoji = theInvoicing_list.numAdd(ttmoneyTotalXiaoji,ttmoneyTotal);
						  wxmoneyTotalXiaoji = theInvoicing_list.numAdd(wxmoneyTotalXiaoji,wxmoneyTotal);
						  zfbmoneyTotalXiaoji = theInvoicing_list.numAdd(zfbmoneyTotalXiaoji,zfbmoneyTotal);
						  
						  htmlArray.push('<tr class="warning">'+
									'<td >&nbsp;</td>'+
									'<td>小计</td>'+
									/*'<td >&nbsp;</td>'+*/
									'<td>'+(shishoumoneyTotalsXiaoji == 0 ? '':shishoumoneyTotalsXiaoji.toFixed(2))+'</td>'+
									'<td>'+(cashmoneyTotalXiaoji == 0 ? '':cashmoneyTotalXiaoji.toFixed(2))+'</td>'+
									'<td>'+(cardmoneyTotalXiaoji == 0 ? '':cardmoneyTotalXiaoji.toFixed(2))+'</td>'+
									'<td>'+(ttmoneyTotalXiaoji == 0 ? '':ttmoneyTotalXiaoji.toFixed(2))+'</td>'+
									'<td>'+(zfbmoneyTotalXiaoji == 0 ? '':zfbmoneyTotalXiaoji.toFixed(2))+'</td>'+
									'<td>'+(wxmoneyTotalXiaoji == 0 ? '':wxmoneyTotalXiaoji.toFixed(2))+'</td>'+
								'</tr>');
						  count ++;
						  //去除重复的小计
						  if(isContainCJR >= 0){
							  htmlArray.splice(count - 3,1);
							  count --;
						  }
						  //添加不重复收费员
						  if(isContainCJR == -1){
							  cjrs.push(data.USER_ID);
						  }
							  
					  }
					  htmlArray.push('<tr class="danger">'+
								'<td>&nbsp;</td>'+
								'<td>总计</td>'+
								/*'<td>&nbsp;</td>'+*/
								'<td>'+(shishoumoneyTotals == 0 ? '':shishoumoneyTotals.toFixed(2))+'</td>'+
								'<td>'+(cashmoneyTotals == 0 ? '':cashmoneyTotals.toFixed(2))+'</td>'+
								'<td>'+(cardmoneyTotals == 0 ? '':cardmoneyTotals.toFixed(2))+'</td>'+
								'<td>'+(ttmoneyTotals == 0 ? '':ttmoneyTotals.toFixed(2))+'</td>'+
								'<td>'+(zfbmoneyTotals == 0 ? '':zfbmoneyTotals.toFixed(2))+'</td>'+
								'<td>'+(wxmoneyTotals == 0 ? '':wxmoneyTotals.toFixed(2))+'</td>'+
							'</tr>')
				  }
				  
				  $("#theInvoicingTable").html(htmlHead + htmlArray.join(''));
				  
				  return false;
			  }
			});
	};
	//加载表格数据
	theInvoicing_list.getTab();
})(jQuery, window);
