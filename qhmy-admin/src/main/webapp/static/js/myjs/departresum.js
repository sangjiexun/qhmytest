//@ sourceURL=addstus.js 
/**
 * 院系报到统计
 */
(function($, window) {
	
	var departresum = {};
	
	//日期插件参数设置
	laydate.render({
		elem:'#BAODAOSJBEGIN',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	//日期插件参数设置
	laydate.render({
		elem:'#BAODAOSJEND',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		$("#departresumTable").bootstrapTable('destroy'); 
		departresum.getTab();
	});
	
	//导出按钮点击事件
	$("#checkOut").click(function(){
		  var NIANJI = $('#NIANJI').val();//入学年份
		  var DEPARTMENTID = $('#DEPARTMENTID').val();//院校
		  var BAODAOSJBEGIN = $("#BAODAOSJBEGIN").val();
		  var BAODAOSJEND = $("#BAODAOSJEND").val();
		window.location.href=encodeURI(_basepath+'departresum/collegeSummaryExcel.json?NIANJI='+NIANJI
				+"&&DEPARTMENTID="+DEPARTMENTID+"&BAODAOSJBEGIN="+BAODAOSJBEGIN+"&BAODAOSJEND="+BAODAOSJEND
				);
	});
	
	// 获取bootStrapTable表格数据,及参数设置
	departresum.getTab = function() {
		$("#departresumTable").html('');
		var htmls= '<tr>'+
						'<th class="tb-th bg-warning" style="vertical-align:middle;" rowspan="2">入学年份</th>'+
						'<th class="tb-th bg-warning" style="vertical-align:middle;" rowspan="2">院系 </th>'+
						'<th class="bg-warning" style="vertical-align:middle;" colspan="3">录取情况</th>'+
						'<th class="bg-warning" style="vertical-align:middle;" colspan="3">报到情况</th>'+
						'<th class="bg-warning" style="vertical-align:middle;" colspan="3">未报到情况</th>'+
						'<th class="tb-th bg-warning" style="vertical-align:middle;" rowspan="2">报到率</th>'+
						'</tr>'+
						'<tr>'+
						'<td class="bg-warning text"><b>录取人数</b></td>'+
						'<td class="bg-warning"><b>男生人数</b></td>'+
						'<td class="bg-warning"><b>女生人数</b></td>'+
						'<td class="bg-warning"><b>报到人数</b></td>'+
						'<td class="bg-warning"><b>男生人数</b></td>'+
						'<td class="bg-warning"><b>女生人数</b></td>'+
						'<td class="bg-warning"><b>未报到人数</b></td>'+
						'<td class="bg-warning"><b>男生人数</b></td>'+
						'<td class="bg-warning"><b>女生人数</b></td>'+
						'</tr>';
		
		var url = _basepath + "departresum/getDepartReSumTable.json";
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  dataType : "json",
			  data:{
				  NIANJI : $('#NIANJI').val(),//入学年份
				  DEPARTMENTID : $('#DEPARTMENTID').val(),//院校
				  BAODAOSJBEGIN : $("#BAODAOSJBEGIN").val(),
				  BAODAOSJEND : $("#BAODAOSJEND").val()
			  },
			  success : function(result) {
				  var dormPlanList = result.departReSumTable;
				  if(dormPlanList != ''&&dormPlanList!=null){

					  var RUXUENIANFEN_FLAG = '';
					  var RUXUENIANFEN_TEMP = '';
					  
					  var LUQUCOUNT_SUM = 0;
					  var LUQUCOUNT_NAN_SUM = 0;
					  var LUQUCOUNT_NV_SUM = 0;

					  var BAODAOCOUNT_SUM = 0;
					  var BAODAOCOUNT_NAN_SUM = 0;
					  var BAODAOCOUNT_NV_SUM = 0;

					  var BAODAOCOUNT_WEI_SUM = 0;
					  var BAODAOCOUNT_NAN_WEI_SUM = 0;
					  var BAODAOCOUNT_NV_WEI_SUM = 0;
					  

					  for(var i=0; i< dormPlanList.length; i++){
						  var dormPlan = dormPlanList[i];

						  RUXUENIANFEN_TEMP = dormPlan.RUXUENIANFEN;
						  if(RUXUENIANFEN_FLAG != RUXUENIANFEN_TEMP){
							 RUXUENIANFEN_FLAG = RUXUENIANFEN_TEMP;
							 
							 
							 if(i != 0){
								//展示合计
								htmls += '<tr class="warning">'+
									  	'<td></td>'+
									  	'<td>合计</td>'+
									  	'<td>'+LUQUCOUNT_SUM+'</td>'+									  	
									  	'<td>'+LUQUCOUNT_NAN_SUM+'</td>'+
									  	'<td>'+LUQUCOUNT_NV_SUM+'</td>'+
									  	'<td>'+BAODAOCOUNT_SUM+'</td>'+
									  	'<td>'+BAODAOCOUNT_NAN_SUM+'</td>'+
									  	'<td>'+BAODAOCOUNT_NV_SUM+'</td>'+
									  	'<td>'+BAODAOCOUNT_WEI_SUM+'</td>'+
									  	'<td>'+BAODAOCOUNT_NAN_WEI_SUM+'</td>'+
									  	'<td>'+BAODAOCOUNT_NV_WEI_SUM+'</td>'+
									  	'<td>'+(BAODAOCOUNT_SUM/LUQUCOUNT_SUM*100).toFixed(2)+'%</td>'+
								   '</tr>';

								//统计清零
								LUQUCOUNT_SUM = 0;
								LUQUCOUNT_NAN_SUM = 0;
								LUQUCOUNT_NV_SUM = 0;

								BAODAOCOUNT_SUM = 0;
								BAODAOCOUNT_NAN_SUM = 0;
								BAODAOCOUNT_NV_SUM = 0;

								BAODAOCOUNT_WEI_SUM = 0;
								BAODAOCOUNT_NAN_WEI_SUM = 0;
								BAODAOCOUNT_NV_WEI_SUM = 0;
								
							 }

						  }else{
							 RUXUENIANFEN_TEMP = '';
						  }

						  LUQUCOUNT_SUM += parseInt(dormPlan.LUQUCOUNT);
						  LUQUCOUNT_NAN_SUM += parseInt(dormPlan.LUQUCOUNT_NAN);
						  LUQUCOUNT_NV_SUM += parseInt(dormPlan.LUQUCOUNT_NV);

						  BAODAOCOUNT_SUM += parseInt(dormPlan.BAODAOCOUNT);
						  BAODAOCOUNT_NAN_SUM += parseInt(dormPlan.BAODAOCOUNT_NAN);
						  BAODAOCOUNT_NV_SUM += parseInt(dormPlan.BAODAOCOUNT_NV);

						  BAODAOCOUNT_WEI_SUM += parseInt(dormPlan.BAODAOCOUNT_WEI);
						  BAODAOCOUNT_NAN_WEI_SUM += parseInt(dormPlan.BAODAOCOUNT_NAN_WEI);
						  BAODAOCOUNT_NV_WEI_SUM += parseInt(dormPlan.BAODAOCOUNT_NV_WEI);
						  



						  htmls += '<tr>'+
									  	'<td>'+RUXUENIANFEN_TEMP+'</td>'+
									  	'<td>'+dormPlan.XUEYUANNAME+'</td>'+
									  	'<td>'+dormPlan.LUQUCOUNT+'</td>'+									  	
									  	'<td>'+dormPlan.LUQUCOUNT_NAN+'</td>'+
									  	'<td>'+dormPlan.LUQUCOUNT_NV+'</td>'+
									  	'<td>'+dormPlan.BAODAOCOUNT+'</td>'+
									  	'<td>'+dormPlan.BAODAOCOUNT_NAN+'</td>'+
									  	'<td>'+dormPlan.BAODAOCOUNT_NV+'</td>'+
									  	'<td>'+dormPlan.BAODAOCOUNT_WEI+'</td>'+
									  	'<td>'+dormPlan.BAODAOCOUNT_NAN_WEI+'</td>'+
									  	'<td>'+dormPlan.BAODAOCOUNT_NV_WEI+'</td>'+
									  	'<td>'+dormPlan.BAODAOLV.toFixed(2)+'%</td>'+
								   '</tr>';
					  }
					//展示合计
						htmls += '<tr  class="warning">'+
							  	'<td></td>'+
							  	'<td>合计</td>'+
							  	'<td>'+LUQUCOUNT_SUM+'</td>'+									  	
							  	'<td>'+LUQUCOUNT_NAN_SUM+'</td>'+
							  	'<td>'+LUQUCOUNT_NV_SUM+'</td>'+
							  	'<td>'+BAODAOCOUNT_SUM+'</td>'+
							  	'<td>'+BAODAOCOUNT_NAN_SUM+'</td>'+
							  	'<td>'+BAODAOCOUNT_NV_SUM+'</td>'+
							  	'<td>'+BAODAOCOUNT_WEI_SUM+'</td>'+
							  	'<td>'+BAODAOCOUNT_NAN_WEI_SUM+'</td>'+
							  	'<td>'+BAODAOCOUNT_NV_WEI_SUM+'</td>'+
							  	'<td>'+(BAODAOCOUNT_SUM/LUQUCOUNT_SUM*100).toFixed(2)+'%</td>'+
						   '</tr>';




				  }else{
					  htmls += '<tr class="no-records-found">'+
								  	'<td colspan="8" text-align="center">没有找到匹配的记录</td>'+
							   '</tr>';
				  }
				  
				  
			  }
		});
		$("#departresumTable").html(htmls);
		
	};
	//加载表格数据
	departresum.getTab();
	
	
	
})(jQuery, window);

