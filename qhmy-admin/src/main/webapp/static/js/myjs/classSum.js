/**
 * 班级汇总统计对象
 */
//@ sourceURL=classSum.js
(function($, window) {
	var classSum = {};
	
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		$("#classSumTable").bootstrapTable('destroy'); 
		classSum.getTab();
	});
	//导出按钮点击事件
	$("#checkOut").click(function(){
		//选择的入学年份
		var RUXUENIANFEN=$("#RUXUENIANFEN  option:selected").val();
		//院校专业pkids
		var orgtree=$('#orgtree').val();
		var BANJI = $("#BANJI  option:selected").val();
		window.location.href=encodeURI(_basepath+'reportClassSum/classSumExcel.json?RUXUENIANFEN='+RUXUENIANFEN
				+"&orgtree="+orgtree+"&BANJI="+BANJI
				);
	});
	
	classSum.getClassList = function(){
		$("#BANJI").html('');
		var url = _basepath + "reportClassSum/getClassList.json";
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  data: {
				  	RUXUENIANFEN : $("#RUXUENIANFEN  option:selected").val(),//入学年份
				  	orgtree : $('#orgtree').val()//院校
			  },
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  $("#BANJI").html('<option value="">班级</option>');
					  var list = result.list;
					  for (var i = 0; i < list.length; i++) {
						  var pd = list[i];
						  $("#BANJI").append('<option value="'+pd.PKID+'">'+pd.CLASS_NAME+'</option>');
					  }
				  }
			  }
			});
	};
	/*$('#BANJI').click(function(){
		classSum.getClassList();
	});*/
	$("#RUXUENIANFEN").bind("change", function() {
		changeGrade();
	});
	
	// 获取bootStrapTable表格数据,及参数设置
	classSum.getTab = function() {
		$("#classSumTable").html('');
		var htmlHead = '<tr>'+
							'<th class="tb-th bg-warning" style="vertical-align:middle;" rowspan="2">入学年份</th>'+
							'<th class="tb-th bg-warning" style="vertical-align:middle;" rowspan="2">学院 </th>'+
							'<th class="tb-th bg-warning" style="vertical-align:middle;" rowspan="2">专业</th>'+
							'<th class="tb-th bg-warning" style="vertical-align:middle;" rowspan="2">班级</th>'+
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
		var htmls = "";
		var url = _basepath + "reportClassSum/getReportClassSumList.json";
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  data: {
				  	RUXUENIANFEN : $("#RUXUENIANFEN  option:selected").val(),//入学年份
				  	orgtree : $('#orgtree').val(),//院校
				  	BANJI:$("#BANJI  option:selected").val()//
			  },
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  var list = result.list;
					  if(list != null && list !=''){
						  for (var i = 0; i < list.length; i++) {
							  var pd = list[i];
							  htmls +='<tr '+(pd.XUEYUANNAME == '合计' ? 'class="warning"' : '')+'>'+
										  '<td>'+pd.RUXUENIANFEN+'</td>'+
										  '<td>'+pd.XUEYUANNAME+'</td>'+
										  '<td>'+pd.ZHUANYENAME+'</td>'+
										  '<td>'+pd.BANJINAME+'</td>'+
										  '<td>'+pd.LUQUCOUNT+'</td>'+
										  '<td>'+pd.LUQUCOUNT_NAN+'</td>'+
										  '<td>'+pd.LUQUCOUNT_NV+'</td>'+
										  '<td>'+pd.BAODAOCOUNT+'</td>'+
										  '<td>'+pd.BAODAOCOUNT_NAN+'</td>'+
										  '<td>'+pd.BAODAOCOUNT_NV+'</td>'+
										  '<td>'+pd.BAODAOCOUNT_WEI+'</td>'+
										  '<td>'+pd.BAODAOCOUNT_NAN_WEI+'</td>'+
										  '<td>'+pd.BAODAOCOUNT_NV_WEI+'</td>'+
										  '<td>'+pd.BAODAOLV+'</td>'+
									  '</tr>';
						  }
						  
					  }else{
						  htmls +='<tr>'+
									  '<td colspan="14">未查询到相关数据</td>'+
								  '</tr>';
					  }
					  
				  }
			  }
			});
		
		$("#classSumTable").html(htmlHead + htmls);
	};
	//加载表格数据
	classSum.getTab();
})(jQuery, window);
