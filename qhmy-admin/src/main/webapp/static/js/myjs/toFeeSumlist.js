/**
 * 介绍人明细页面对象
 */
//@ sourceURL=toFeeSumlist.js
(function($, window) {
	var toFeeSumlist = {};
	
	//日期插件参数设置
	laydate.render({
		elem:'#DATESTART',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		max:1,
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	laydate.render({
		elem:'#DATEEND',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		max:1,
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	
	//两小数相加解决精度出现的问题
	toFeeSumlist.numAdd=function (num1, num2) {//要相加的两个数
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
		//选择的日期起
		var DATESTART=$('#DATESTART').val();
		//选择的日期止
		var DATEEND=$('#DATEEND').val();
		var date1 = new Date(Date.parse(DATESTART));
		var date2 = new Date(Date.parse(DATEEND));
		if(date1>date2){
			layer.msg("结束时间不能早于开始时间");
			return false;
		}
		$("#toFeeSumlistTable").bootstrapTable('destroy');
		toFeeSumlist.getTab();
	});
	
	//点击按钮导出
	$("#checkOut").click(function(){
		//选择的日期起
		var DATESTART=$('#DATESTART').val();
		//选择的日期止
		var DATEEND=$('#DATEEND').val();
		var date1 = new Date(Date.parse(DATESTART));
		var date2 = new Date(Date.parse(DATEEND));
		if(date1>date2){
			layer.msg("结束时间不能早于开始时间");
			return false;
		}
		var RUXUENIANFEN = $("#RUXUENIANFEN").val();
		var BANXING = $("#BANXING").val();
		var JIAOFEILEIXING = $("#JIAOFEILEIXING").val();
		window.location.href=encodeURI(_basepath+'reportStat/feeSumlistExcel.json?RUXUENIANFEN='+RUXUENIANFEN
				+"&&BANXING="+BANXING+"&&JIAOFEILEIXING="+JIAOFEILEIXING+"&&DATESTART="+DATESTART+"&&DATEEND="+DATEEND
				);
	});
	
	//点击按钮打印
	$("#printOut").click(function(){
		$("#batchPrintArea2").html('');
		//选择的日期起
		var DATESTART=$('#DATESTART').val();
		//选择的日期止
		var DATEEND=$('#DATEEND').val();
		var htmlHead = '';
		
		
		htmlHead += '<div><h3 style="text-align: center;margin:7px 0px 7px 0px;">收费汇总</h3></div>';
		
		htmlHead += '<table class="table table-bordered2 sf_table" id="" style="margin-top:15px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">';
		
		htmlHead += '<tr>'+
					'<th style="text-align: center">入学年份</th>'+
					'<th style="text-align: center">班型</th>'+
					'<th style="text-align: center">缴费类型</th>'+
					'<th style="text-align: center">人数</th>'+
					'<th style="text-align: center">实收总金额</th>'+
					'<th style="text-align: center">现金总金额</th>'+
					'<th style="text-align: center">银行卡总金额</th>'+
					'<th style="text-align: center">电汇总金额</th>'+
					'<th style="text-align: center">支付宝总金额</th>'+
					'<th style="text-align: center">微信总金额</th>'+
				'</tr>';
		var RUXUENIANFEN = $("#RUXUENIANFEN").val();
		var BANXING = $("#BANXING").val();
		var JIAOFEILEIXING = $("#JIAOFEILEIXING").val();
		
		var url=_basepath+'reportStat/feeSumlistPrint.json?RUXUENIANFEN='+RUXUENIANFEN
		+"&&BANXING="+BANXING+"&&JIAOFEILEIXING="+JIAOFEILEIXING+'&&DATESTART='+DATESTART+'&&DATEEND='+DATEEND;
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  data: {
			  },
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  var htmlArray = new Array();
					  var list = result.list;
					  for (var i = 0; i < list.length; i++) {
						  var data = list[i];
						  var CASH_MONEY = data.CASH_MONEY == null || data.CASH_MONEY == '' || data.CASH_MONEY == 'null' ? 0 : Number(data.CASH_MONEY);
						  var CARD_MONEY = data.CARD_MONEY == null || data.CARD_MONEY == '' || data.CARD_MONEY == 'null' ? 0 : Number(data.CARD_MONEY);
						  var TT_MONEY = data.TT_MONEY == null || data.TT_MONEY == '' || data.TT_MONEY == 'null' ? 0 : Number(data.TT_MONEY);
						  var ZFB_MONEY = data.ZFB_MONEY == null || data.ZFB_MONEY == '' || data.ZFB_MONEY == 'null' ? 0 : Number(data.ZFB_MONEY);
						  var WX_MONEY = data.WX_MONEY == null || data.WX_MONEY == '' || data.WX_MONEY == 'null' ? 0 : Number(data.WX_MONEY);
						  var SHISHOUMONEY = CASH_MONEY + CARD_MONEY + TT_MONEY + ZFB_MONEY + WX_MONEY;
						  SHISHOUMONEY = SHISHOUMONEY.toFixed(2);
						  htmlArray.push('<tr>'+
								  	'<td>'+data.RUXUENIANFEN+'</td>'+
							  		'<td>'+data.BANXING+'</td>'+
							  		'<td>'+(data.JIAOFEILEIXING == null || data.JIAOFEILEIXING == 'null' || data.JIAOFEILEIXING == 'undefined' ? '' : data.JIAOFEILEIXING)+'</td>'+
									'<td>'+data.STU_COUNT+'</td>'+
									'<td>'+SHISHOUMONEY+'</td>'+
									'<td>'+(data.CASH_MONEY == null || data.CASH_MONEY == '' || data.CASH_MONEY == 'null' ? 0 : Number(data.CASH_MONEY).toFixed(2))+'</td>'+
									'<td>'+(data.CARD_MONEY == null || data.CARD_MONEY == '' || data.CARD_MONEY == 'null' ? 0 : Number(data.CARD_MONEY).toFixed(2))+'</td>'+
									'<td>'+(data.TT_MONEY == null || data.TT_MONEY == '' || data.TT_MONEY == 'null' ? 0 : Number(data.TT_MONEY).toFixed(2))+'</td>'+
									'<td>'+(data.ZFB_MONEY == null || data.ZFB_MONEY == '' || data.ZFB_MONEY == 'null' ? 0 : Number(data.ZFB_MONEY).toFixed(2))+'</td>'+
									'<td>'+(data.WX_MONEY == null || data.WX_MONEY == '' || data.WX_MONEY == 'null' ? 0 : Number(data.WX_MONEY).toFixed(2))+'</td>'+
								'</tr>');
						  
					  }
					  $('#batchPrintArea2').html(htmlHead + htmlArray.join('') + '</table>');
					  $('#batchPrintArea').printArea();
				  }else{
					  layer.msg("打印失败，请重试!");
				  }
				  return false;
			  }
			});
	});
	
	// 获取bootStrapTable表格数据,及参数设置
	toFeeSumlist.getTab = function() {
		$("#toFeeSumlistTable").html('');
		//选择的日期起
		var DATESTART=$('#DATESTART').val();
		//选择的日期止
		var DATEEND=$('#DATEEND').val();
		var htmlHead = '<tr>'+
					'<th>入学年份</th>'+
					'<th>班型</th>'+
					'<th style="white-space:nowrap;">缴费类型</th>'+
					'<th>人数</th>'+
					'<th>实收总金额</th>'+
					'<th>现金总金额</th>'+
					'<th>银行卡总金额</th>'+
					'<th>电汇总金额</th>'+
					'<th>支付宝总金额</th>'+
					'<th>微信总金额</th>'+
				'</tr>';
		var url = _basepath + "reportStat/getFeeSumlistTable.json";
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  data: {
				  RUXUENIANFEN:$("#RUXUENIANFEN").val(),
				  BANXING:$("#BANXING").val(),
				  JIAOFEILEIXING:$("#JIAOFEILEIXING").val(),
				  DATESTART : DATESTART,
					DATEEND : DATEEND,
			  },
			  dataType : "json",
			  success : function(result) {
				  var htmlArray = new Array();
				  if(result.result == 'success'){
					  var list = result.list;
					  var departments = new Array();
					  
					  //学生总数
					  var stucountTotals = 0;
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
					  //学生总数小计
					  var stucountTotalsXiaoji = 0;
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
						  //是否包含该部门
						  var isContainDepartment = $.inArray(data.RUXUENIANFEN + data.BANXING, departments);
						  //学生总数
						  var stucountTotal = data.STU_COUNT;
						  //实收总额
						  var shishoumoneyTotal = data.SHISHOUMONEY;
						  var CASH_MONEY = data.CASH_MONEY == null || data.CASH_MONEY == '' || data.CASH_MONEY == 'null' ? 0 : Number(data.CASH_MONEY);
						  var CARD_MONEY = data.CARD_MONEY == null || data.CARD_MONEY == '' || data.CARD_MONEY == 'null' ? 0 : Number(data.CARD_MONEY);
						  var TT_MONEY = data.TT_MONEY == null || data.TT_MONEY == '' || data.TT_MONEY == 'null' ? 0 : Number(data.TT_MONEY);
						  var ZFB_MONEY = data.ZFB_MONEY == null || data.ZFB_MONEY == '' || data.ZFB_MONEY == 'null' ? 0 : Number(data.ZFB_MONEY);
						  var WX_MONEY = data.WX_MONEY == null || data.WX_MONEY == '' || data.WX_MONEY == 'null' ? 0 : Number(data.WX_MONEY);
						  shishoumoneyTotal = CASH_MONEY + CARD_MONEY + TT_MONEY + ZFB_MONEY + WX_MONEY;
						  
						  if(stucountTotal == 0) continue;
						  
						  //现金总额
						  var cashmoneyTotal = data.CASH_MONEY == null || data.CASH_MONEY == '' || data.CASH_MONEY == 'null' ? 0 : Number(data.CASH_MONEY);
						  //银行卡总额
						  var cardmoneyTotal = data.CARD_MONEY == null || data.CARD_MONEY == '' || data.CARD_MONEY == 'null' ? 0 : Number(data.CARD_MONEY);
						  //电汇总额
						  var ttmoneyTotal = data.TT_MONEY == null || data.TT_MONEY == '' || data.TT_MONEY == 'null' ? 0 : Number(data.TT_MONEY);
						  //微信总额
						  var wxmoneyTotal = data.WX_MONEY == null || data.WX_MONEY == '' || data.WX_MONEY == 'null' ? 0 : Number(data.WX_MONEY);
						  //支付宝总额
						  var zfbmoneyTotal = data.ZFB_MONEY == null || data.ZFB_MONEY == '' || data.ZFB_MONEY == 'null' ? 0 : Number(data.ZFB_MONEY);
						  
						  //总计 start=========================================================
						  stucountTotals = toFeeSumlist.numAdd(stucountTotals,stucountTotal);
						  shishoumoneyTotals = toFeeSumlist.numAdd(shishoumoneyTotal,shishoumoneyTotals);
						  cashmoneyTotals = toFeeSumlist.numAdd(cashmoneyTotals,cashmoneyTotal);
						  cardmoneyTotals = toFeeSumlist.numAdd(cardmoneyTotals,cardmoneyTotal);
						  ttmoneyTotals = toFeeSumlist.numAdd(ttmoneyTotals,ttmoneyTotal);
						  wxmoneyTotals = toFeeSumlist.numAdd(wxmoneyTotals,wxmoneyTotal);
						  zfbmoneyTotals = toFeeSumlist.numAdd(zfbmoneyTotals,zfbmoneyTotal);
						  //总计 end =========================================================
						  
						  htmlArray.push('<tr>'+
								  		'<td>'+($.inArray(data.RUXUENIANFEN + data.BANXING, departments) >=0 ? data.RUXUENIANFEN : data.RUXUENIANFEN)+'</td>'+
								  		'<td>'+($.inArray(data.RUXUENIANFEN + data.BANXING, departments) >=0 ? data.BANXING : data.BANXING)+'</td>'+
								  		'<td>'+(data.JIAOFEILEIXING == null || data.JIAOFEILEIXING == 'null' || data.JIAOFEILEIXING == 'undefined' ? '' : data.JIAOFEILEIXING)+'</td>'+
										'<td>'+data.STU_COUNT+'</td>'+
										'<td>'+shishoumoneyTotal.toFixed(2)+'</td>'+
										'<td>'+cashmoneyTotal.toFixed(2)+'</td>'+
										'<td>'+cardmoneyTotal.toFixed(2)+'</td>'+
										'<td>'+ttmoneyTotal.toFixed(2)+'</td>'+
										'<td>'+zfbmoneyTotal.toFixed(2)+'</td>'+
										'<td>'+wxmoneyTotal.toFixed(2)+'</td>'+
									'</tr>');
						  count ++;
						  //换部门时重置小计
						  if(isContainDepartment == -1){
							  stucountTotalsXiaoji = 0;
							  shishoumoneyTotalsXiaoji = 0;
							  cashmoneyTotalXiaoji = 0;
							  cardmoneyTotalXiaoji = 0;
							  ttmoneyTotalXiaoji = 0;
							  wxmoneyTotalXiaoji = 0;
							  zfbmoneyTotalXiaoji = 0; 
						  }
						  stucountTotalsXiaoji = toFeeSumlist.numAdd(stucountTotalsXiaoji,stucountTotal);
						  shishoumoneyTotalsXiaoji = toFeeSumlist.numAdd(shishoumoneyTotal,shishoumoneyTotalsXiaoji);
						  cashmoneyTotalXiaoji = toFeeSumlist.numAdd(cashmoneyTotalXiaoji,cashmoneyTotal);
						  cardmoneyTotalXiaoji = toFeeSumlist.numAdd(cardmoneyTotalXiaoji,cardmoneyTotal);
						  ttmoneyTotalXiaoji = toFeeSumlist.numAdd(ttmoneyTotalXiaoji,ttmoneyTotal);
						  wxmoneyTotalXiaoji = toFeeSumlist.numAdd(wxmoneyTotalXiaoji,wxmoneyTotal);
						  zfbmoneyTotalXiaoji = toFeeSumlist.numAdd(zfbmoneyTotalXiaoji,zfbmoneyTotal);
						  
						  htmlArray.push('<tr class="warning">'+
							  		'<td >&nbsp;</td>'+
							  		'<td>小计</td>'+
							  		'<td >&nbsp;</td>'+
									'<td>'+stucountTotalsXiaoji+'</td>'+
									'<td>'+shishoumoneyTotalsXiaoji.toFixed(2)+'</td>'+
									'<td>'+cashmoneyTotalXiaoji.toFixed(2)+'</td>'+
									'<td>'+cardmoneyTotalXiaoji.toFixed(2)+'</td>'+
									'<td>'+ttmoneyTotalXiaoji.toFixed(2)+'</td>'+
									'<td>'+zfbmoneyTotalXiaoji.toFixed(2)+'</td>'+
									'<td>'+wxmoneyTotalXiaoji.toFixed(2)+'</td>'+
								'</tr>');
						  count ++;
						  //去除重复的小计
						  if(isContainDepartment >= 0){
							  htmlArray.splice(count - 3,1);
							  count --;
						  }
						  //添加不重复部门
						  if(isContainDepartment == -1){
							  departments.push(data.RUXUENIANFEN + data.BANXING);
						  }
						  
						  
					  }
					  htmlArray.push('<tr class="danger">'+
								'<td>&nbsp;</td>'+
								'<td>总计</td>'+
								'<td>&nbsp;</td>'+
								'<td>'+stucountTotals+'</td>'+
								'<td>'+shishoumoneyTotals.toFixed(2)+'</td>'+
								'<td>'+cashmoneyTotals.toFixed(2)+'</td>'+
								'<td>'+cardmoneyTotals.toFixed(2)+'</td>'+
								'<td>'+ttmoneyTotals.toFixed(2)+'</td>'+
								'<td>'+zfbmoneyTotals.toFixed(2)+'</td>'+
								'<td>'+wxmoneyTotals.toFixed(2)+'</td>'+
							'</tr>')
				  }
				  
				  $("#toFeeSumlistTable").html(htmlHead + htmlArray.join(''));
				  
				  return false;
			  }
			});
	};
	//加载表格数据
	toFeeSumlist.getTab();
})(jQuery, window);
