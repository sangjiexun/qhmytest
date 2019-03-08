/**
 * 学院汇总统计对象
 */
//@ sourceURL=collegeSummary.js
(function($, window) {
	var collegeSummary = {};
	
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		$("#collegeSummaryTable").bootstrapTable('destroy'); 
		collegeSummary.getTab();
	});
	//导出按钮点击事件
	$("#checkOut").click(function(){
		//选择的入学年份
		var NIANJI=$('#NIANJI').val();
		//院校专业pkids
		var orgtree=$('#orgtree').val();
		var shoufeixuenian = $('#shoufeixuenian').val();
		var PAY_TYPE_PKID = $('#pay_style_sh').val()
		window.location.href=encodeURI(_basepath+'reportStat/collegeSummaryExcel.json?NIANJI='+NIANJI
				+"&&orgtree="+orgtree+"&shoufeixuenian="+shoufeixuenian+"&PAY_TYPE_PKID="+PAY_TYPE_PKID
				);
	});
	//两小数相加解决精度出现的问题
	collegeSummary.numAdd=function (num1, num2) {//要相加的两个数
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
	// 获取bootStrapTable表格数据,及参数设置
	collegeSummary.getTab = function() {
		$("#collegeSummaryTable").html('');
		var htmlHead = '<tr>'+
					'<th>入学年份</th>'+
					'<th>院校</th>'+
					'<th>学年</th>'+
					'<th>缴费类型</th>'+
					'<th>应收总金额</th>'+
					'<th>实收总金额</th>'+
					'<th>欠费总金额</th>'+
				'</tr>';
		var url = _basepath + "reportStat/collegeSummary_list.json";
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  data: {
				  	NIANJI : $('#NIANJI').val(),//入学年份
				  	orgtree : $('#orgtree').val(),//院校
					shoufeixuenian:$('#shoufeixuenian').val(),//学年
					PAY_TYPE_PKID:$("#pay_style_sh").val()//收费类型
			  },
			  dataType : "json",
			  success : function(result) {
				  var htmlArray = new Array();
				  if(result.result == 'success'){
					  var list = result.list;
					  var years = new Array();
					  var orgtrees = new Array();
					  
					  //应收总额
					  var yingshoumoneyTotals = 0;
					  //实收总额
					  var shishoumoneyTotals = 0;
					  //欠费总额
					  var qianfeimoneyTotals = 0;
					  //应收总额小计
					  var yingshoumoneyTotalsXiaoji = 0;
					  //实收总额小计
					  var shishoumoneyTotalsXiaoji = 0;
					  //欠费总额小计
					  var qianfeimoneyTotalsXiaoji = 0;

					  //行数
					  var count = 0;
					  for (var i = 0; i < list.length; i++) {
						  var data = list[i];
						  //是否包含该年份
						  var isContainYear = $.inArray(data.NIANJI, years);
						  //是否包含该院校专业
						  var isContainProfessional = $.inArray(data.NAME, orgtrees);
						  
						  //应收总数
						  var TOTALAMOUNTRECEIVABLE = data.TOTALAMOUNTRECEIVABLE;
						  //实收总额
						  var TOTALAMOUNTCOLLECTION = data.TOTALAMOUNTCOLLECTION;
						  //欠费总额
						  var QIANFEIHEJI = data.QIANFEIHEJI;					  						  
						  
						  //总计 start=========================================================
						  yingshoumoneyTotals = collegeSummary.numAdd(yingshoumoneyTotals,TOTALAMOUNTRECEIVABLE);
						  shishoumoneyTotals = collegeSummary.numAdd(shishoumoneyTotals,TOTALAMOUNTCOLLECTION);
						  qianfeimoneyTotals = collegeSummary.numAdd(qianfeimoneyTotals,QIANFEIHEJI);
						  //总计 end =========================================================
						  htmlArray.push('<tr>'+
								  		'<td>'+(isContainYear>=0 && isContainProfessional >=0 ? '' : data.NIANJI)+'</td>'+
								  		'<td>'+(isContainYear>=0 && isContainProfessional >=0 ? '' : data.NAME)+'</td>'+
								  		'<td>'+(data.SCHOOL_YEAR_NAME == null || data.SCHOOL_YEAR_NAME == 'null' || data.SCHOOL_YEAR_NAME == 'undefined' ? '' : data.SCHOOL_YEAR_NAME)+'</td>'+
										'<td>'+(data.PTP == null || data.PTP == 'null' || data.PTP == 'undefined' ? '' : data.PTP)+'</td>'+
										'<td>'+(data.TOTALAMOUNTRECEIVABLE == 0 ? '':data.TOTALAMOUNTRECEIVABLE)+'</td>'+
										'<td>'+(data.TOTALAMOUNTCOLLECTION == 0 ? '':data.TOTALAMOUNTCOLLECTION)+'</td>'+
										'<td>'+(data.QIANFEIHEJI == 0 ? '':data.QIANFEIHEJI)+'</td>'+
									'</tr>');
						  count ++;
						  //换年份且换院校专业时重置小计
						  if(isContainYear == -1 || isContainProfessional == -1){
							  yingshoumoneyTotalsXiaoji = 0;
							  shishoumoneyTotalsXiaoji = 0;
							  qianfeimoneyTotalsXiaoji = 0;
							  orgtrees = new Array(); 
						  }
						  yingshoumoneyTotalsXiaoji = collegeSummary.numAdd(yingshoumoneyTotalsXiaoji,TOTALAMOUNTRECEIVABLE);
						  shishoumoneyTotalsXiaoji = collegeSummary.numAdd(shishoumoneyTotalsXiaoji,TOTALAMOUNTCOLLECTION);
						  qianfeimoneyTotalsXiaoji = collegeSummary.numAdd(qianfeimoneyTotalsXiaoji,QIANFEIHEJI);
						  
						  htmlArray.push('<tr class="warning">'+
								  	'<td>&nbsp;</td>'+
									'<td>&nbsp;</td>'+
									'<td>小计</td>'+
									'<td>&nbsp;</td>'+
									'<td>'+yingshoumoneyTotalsXiaoji.toFixed(2)+'</td>'+
									'<td>'+shishoumoneyTotalsXiaoji.toFixed(2)+'</td>'+
									'<td>'+qianfeimoneyTotalsXiaoji.toFixed(2)+'</td>'+
								'</tr>');
						  count ++;
						  //去除重复的小计
						  if(isContainYear >= 0 && isContainProfessional >= 0){
							  htmlArray.splice(count - 3,1);
							  count --;
						  }
						  //添加不重复入学年份
						  if(isContainYear == -1){
							  years.push(data.NIANJI);
						  }
						  //添加不重复院校
						  if(isContainProfessional == -1){
							  orgtrees.push(data.NAME);
						  }
						  
					  }
					  htmlArray.push('<tr class="danger">'+
							  	'<td>&nbsp;</td>'+
								'<td>&nbsp;</td>'+
								'<td>总计</td>'+
								'<td>&nbsp;</td>'+
								'<td>'+yingshoumoneyTotals.toFixed(2)+'</td>'+
								'<td>'+shishoumoneyTotals.toFixed(2)+'</td>'+
								'<td>'+qianfeimoneyTotals.toFixed(2)+'</td>'+
							'</tr>')
				  }
				  
				  $("#collegeSummaryTable").html(htmlHead + htmlArray.join(''));
				  return false;
			  }
			});
	};
	//加载表格数据
	collegeSummary.getTab();
})(jQuery, window);
