/**
 * 退费汇总页面对象
 */
//@ sourceURL=refundSumlist.js
(function($, window) {
	var refundSumlist = {};
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
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		$("#refundSumlistTable").bootstrapTable('destroy');
		refundSumlist.getTab();
	});
	
	//点击按钮导出
	$("#checkOut").click(function(){
		
		var DATESTART=$('#DATESTART').val();
		//选择的日期止
		var DATEEND=$('#DATEEND').val();
		var date1 = new Date(Date.parse(DATESTART));
		var date2 = new Date(Date.parse(DATEEND));
		if(date1>date2){
			layer.msg("结束时间不能早于开始时间");
			return false;
		}
		// 入学年份
		var SCHOOLYEAR = $('#schoolYear').val();
		// 班型
		var CLASSTYPE = $('#classType').val();
		// 缴费类型
		var PAYSTYLE = $("#payStyle").val();
		
		
		window.location.href=encodeURI(_basepath+'report/refundSumExcel.json?SCHOOLYEAR='+SCHOOLYEAR
				+"&&CLASSTYPE="+CLASSTYPE+"&&PAYSTYLE="+PAYSTYLE+"&&DATESTART="+DATESTART+"&&DATEEND="+DATEEND);
	});
	
	//两小数相加解决精度出现的问题
	refundSumlist.numAdd=function (num1, num2) {//要相加的两个数
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
	refundSumlist.getTab = function() {
		$("#refundSumlistTable").html('');
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
		var htmlHead = '<tr>'+
					'<th>入学年份</th>'+
					'<th>班型</th>'+
					'<th>缴费类型</th>'+
					'<th>人数</th>'+
					'<th>收费总金额</th>'+
					'<th>退费</th>'+
					'<th>退费比例</th>'+
				'</tr>';
		var url = _basepath + "report/getRefundSumlistTable.json";
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  data: {
				  	SCHOOLYEAR : $('#schoolYear').val(),
					CLASSTYPE : $('#classType').val(),
					PAYSTYLE : $("#payStyle").val(),
					DATESTART:DATESTART,
					DATEEND:DATEEND,
			  },
			  dataType : "json",
			  success : function(result) {
				  var htmlArray = new Array();
				  if(result.result == 'success'){
					  var list = result.list;
					  //入学年份集合
					  var schoolYears = new Array();
					  //班型集合
					  var classTypes = new Array();
					  
					  //学生总数
					  var stucountTotals = 0;
					  //实收总额
					  var shishoumoneyTotals = 0;
					  //退费总额
					  var tuifeimoneyTotals = 0;
					  
					  //学生总数小计
					  var stucountTotalsXiaoji = 0;
					  //实收总额小计
					  var shishoumoneyTotalsXiaoji = 0;
					  //退费总额小计
					  var tuifeimoneyTotalsXiaoji = 0;
					  
					  //行数
					  var count = 0;
					  for (var i = 0; i < list.length; i++) {
						  var data = list[i];
						  //是否包含该入学年份
						  var isContainSchoolYear = $.inArray(data.SCHOOLYEAR, schoolYears);
						  //是否包含该班型
						  var isContainClassType = $.inArray(data.CLASSTYPE, classTypes);
						  
						  //学生总数
						  var stucountTotal = data.STUCOUNT;
						  //实收总额
						  var shishoumoneyTotal = data.SHISHOUMONEY;
						  //退费总额
						  var tuifeimoneyTotal = data.TUIFEIMONEY;
							  
						  //总计 start=========================================================
						  stucountTotals = refundSumlist.numAdd(stucountTotals,stucountTotal);
						  shishoumoneyTotals = refundSumlist.numAdd(shishoumoneyTotals,shishoumoneyTotal);
						  tuifeimoneyTotals = refundSumlist.numAdd(tuifeimoneyTotals,tuifeimoneyTotal);
						  //总计 end =========================================================
							  
						  htmlArray.push('<tr>'+
								  		'<td align="center">'+(data.SCHOOLYEAR)+'</td>'+
								  		'<td align="center">'+(data.CLASSTYPE)+'</td>'+
								  		'<td align="center">'+(data.PAY_STYLE_NAME)+'</td>'+
										'<td align="center">'+data.STUCOUNT+'</td>'+
										'<td align="center">'+data.SHISHOUMONEY.toFixed(2)+'</td>'+
										'<td align="center">'+data.TUIFEIMONEY.toFixed(2)+'</td>'+
										'<td align="center">'+data.TUIMONEYPERSENT.toFixed(2)+'%</td>'+
									'</tr>');
						  count ++;
						  //换入学年份或班型时重置小计
						  if(isContainSchoolYear == -1 || isContainClassType == -1){
							  stucountTotalsXiaoji = 0;
							  shishoumoneyTotalsXiaoji = 0;
							  tuifeimoneyTotalsXiaoji = 0;
						  }else if(isContainSchoolYear == -1 && isContainClassType == -1){
							  classTypes = new Array();
						  }
						  
						  stucountTotalsXiaoji = refundSumlist.numAdd(stucountTotalsXiaoji,stucountTotal);
						  shishoumoneyTotalsXiaoji = refundSumlist.numAdd(shishoumoneyTotalsXiaoji,shishoumoneyTotal);
						  tuifeimoneyTotalsXiaoji = refundSumlist.numAdd(tuifeimoneyTotalsXiaoji,tuifeimoneyTotal);
						  
						  htmlArray.push('<tr class="warning">'+
							  		'<td align="center">&nbsp;</td>'+
							  		'<td align="center">小计</td>'+
							  		'<td align="center">&nbsp;</td>'+
									'<td align="center">'+stucountTotalsXiaoji+'</td>'+
									'<td align="center">'+shishoumoneyTotalsXiaoji.toFixed(2)+'</td>'+
									'<td align="center">'+tuifeimoneyTotalsXiaoji.toFixed(2)+'</td>'+
									'<td align="center">'+(shishoumoneyTotalsXiaoji == 0 ? 100 : tuifeimoneyTotalsXiaoji/shishoumoneyTotalsXiaoji*100).toFixed(2)+'%</td>'+
								'</tr>');
						  count ++;
						  //去除重复的小计
						  if(isContainSchoolYear >= 0 && isContainClassType >= 0){
							  htmlArray.splice(count - 3,1);
							  count --;
						  }
						  //添加不重复入学年份
						  if(isContainSchoolYear == -1){
							  schoolYears.push(data.SCHOOLYEAR);
						  }
						  //添加不重复班型
						  if(isContainClassType == -1){
							  classTypes.push(data.CLASSTYPE);
						  }
						  
					  }
					  htmlArray.push('<tr class="danger">'+
								'<td align="center">&nbsp;</td>'+
								'<td align="center">总计</td>'+
								'<td align="center">&nbsp;</td>'+
								'<td align="center">'+stucountTotals+'</td>'+
								'<td align="center">'+shishoumoneyTotals.toFixed(2)+'</td>'+
								'<td align="center">'+tuifeimoneyTotals.toFixed(2)+'</td>'+
								'<td align="center">&nbsp;</td>'+
							'</tr>');
				  }
				  
				  $("#refundSumlistTable").html(htmlHead + htmlArray.join(''));
				  
				  return false;
			  }
			});
	};
	//加载表格数据
	refundSumlist.getTab();
})(jQuery, window);
