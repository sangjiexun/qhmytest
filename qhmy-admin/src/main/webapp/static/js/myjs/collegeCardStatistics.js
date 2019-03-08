/**
 * 学院领卡统计对象
 */
//@ sourceURL=collegeCardStatistics.js
(function($, window) {
	var collegeCardStatistics = {};
	
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
		$("#collegeCardStatisticsTable").bootstrapTable('destroy'); 
		collegeCardStatistics.getTab();
	});
	//导出按钮点击事件
	$("#checkOut").click(function(){
		var DATESTART = $("#DATESTART").val();
		var DATEEND = $("#DATEEND").val();
		var date1 = new Date(Date.parse(DATESTART));
		var date2 = new Date(Date.parse(DATEEND));
		if(date1>date2){
			layer.msg("结束时间不能早于开始时间");
			return false;
		}
		//选择的入学年份
		var NIANJI=$('#NIANJI').val();
		//院校专业pkids
		var orgtree=$('#orgtree').val();
		window.location.href=encodeURI(_basepath+'collegeCardStatistics/exportCollegeCardStatistics.json?NIANJI='+NIANJI
				+'&&orgtree='+orgtree+"&&DATEEND="+DATEEND+"&&DATESTART="+DATESTART);
	});
	//两小数相加解决精度出现的问题
	collegeCardStatistics.numAdd=function (num1, num2) {//要相加的两个数
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
	collegeCardStatistics.getTab = function() {
		$("#collegeCardStatisticsTable").html('');
		var htmlHead = '<tr>'+
					'<th>入学年份</th>'+
					'<th>院校</th>'+
					'<th>领取人数</th>'+
				'</tr>';
		var url = _basepath + "collegeCardStatistics/collegeCardStatisticsTable.json";
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  data: {
				  //选择的入学年份
				  NIANJI:$('#NIANJI').val(),
				  //院校专业pkids
				  orgtree:$('#orgtree').val(),
				  DATESTART:$("#DATESTART").val(),
				  DATEEND:$("#DATEEND").val()
			  },
			  dataType : "json",
			  success : function(result) {
				  var htmlArray = new Array();
				  if(result.result == 'success'){
					  var list = result.list;
					  var years = new Array();
					  
					  //领取人数
					  var lingqurenshu = 0;

					  //行数
					  var count = 0;
					  for (var i = 0; i < list.length; i++) {
						  var data = list[i];
						  //是否包含入学年份
						  var containsYears = $.inArray(data.NIANFEN, years);
						  
						  htmlArray.push('<tr>'+
								  		'<td>'+(containsYears >= 0 ? '' : data.NIANFEN)+'</td>'+
								  		'<td>'+(data.XUEYUAN)+'</td>'+
								  		'<td>'+(data.OPENCARDNUM)+'</td>'+
									'</tr>');
						  
						  count ++;
						  			
						  //换校区时重置小计
						  if(containsYears == -1 ){
							  lingqurenshu = 0;//领取人数
							  years = new Array(); 
						  }

						  lingqurenshu = collegeCardStatistics.numAdd(lingqurenshu,data.OPENCARDNUM);//领取人数
						  
						  htmlArray.push('<tr class="warning">'+
								  	'<td>&nbsp;</td>'+
									'<td>合计</td>'+
									'<td>'+lingqurenshu+'</td>'+
								'</tr>');
						  count ++;
						  //去除重复的合计
						  if(containsYears >= 0 ){
							  htmlArray.splice(count - 3,1);
							  count --;
						  }
						  //添加不重复入学年份
						  if(containsYears == -1){
							  years.push(data.NIANFEN);
						  }
						  
					  }
					 
				  }
				  
				  $("#collegeCardStatisticsTable").html(htmlHead + htmlArray.join(''));
				  return false;
			  }
			});
	};
	//加载表格数据
	collegeCardStatistics.getTab();
})(jQuery, window);
