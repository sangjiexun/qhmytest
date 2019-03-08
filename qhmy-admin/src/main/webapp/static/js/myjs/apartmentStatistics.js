/**
 * 公寓使用统计对象
 */
//@ sourceURL=apartmentStatistics.js
(function($, window) {
	var apartmentStatistics = {};
	
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		$("#apartmentStatisticsTable").bootstrapTable('destroy'); 
		apartmentStatistics.getTab();
	});
	//导出按钮点击事件
	$("#checkOut").click(function(){
		//宿舍
		var PARENT_PKID=$('#orgtree3').val();
		window.location.href=encodeURI(_basepath+'studentDorm/exportApartmentStatistics.json?PARENT_PKID='+PARENT_PKID);
	});
	//两小数相加解决精度出现的问题
	apartmentStatistics.numAdd=function (num1, num2) {//要相加的两个数
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
	apartmentStatistics.getTab = function() {
		$("#apartmentStatisticsTable").html('');
		var htmlHead = '<tr>'+
					'<th rowspan="2" style="vertical-align:middle">校区</th>'+
					'<th rowspan="2" style="vertical-align:middle">宿舍楼</th>'+
					'<th rowspan="2" style="vertical-align:middle">楼层</th>'+
					'<th colspan="3">总床位数</th>'+
					'<th colspan="3">已住人数</th>'+
					'<th colspan="3">空闲床位数</th>'+
					'<th rowspan="2" style="vertical-align:middle">入住率</th>'+
				'</tr>'+
				'<tr>'+
					'<th>总床位数</th>'+
					'<th>男生床位</th>'+
					'<th>女生床位</th>'+
					'<th>总已住数</th>'+
					'<th>男生已住</th>'+
					'<th>女生已住</th>'+
					'<th>总空闲床位</th>'+
					'<th>男生空闲</th>'+
					'<th>女生空闲</th>'+
				'</tr>';
		var url = _basepath + "studentDorm/getApartmentStatisticslistPage.json";
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  data: {
				  PARENT_PKID : $('#orgtree3').val()	
			  },
			  dataType : "json",
			  success : function(result) {
				  var htmlArray = new Array();
				  if(result.result == 'success'){
					  var list = result.list;
					  var xiaoqu = new Array();
					  
					  //总床位数
					  var zongchuangwei = 0;
					  //总人数(男)
					  var totalnan = 0;
					  //总人数(女)
					  var totalnv = 0;
					  //已住总人数
					  var zongzhanyong = 0;
					  //已住总人数(男)
					  var zhanyongnan = 0;
					  //已住总人数(女)
					  var zhanyongnv = 0;
					  //空闲床位数
					  var zongkongxian = 0;
					  //空闲床位数(男)
					  var kongxiannan = 0;
					  //空闲床位数(女)
					  var kongxiannv = 0;

					  //行数
					  var count = 0;
					  for (var i = 0; i < list.length; i++) {
						  var data = list[i];
						  //是否包含校区
						  var containsXiaoQu = $.inArray(data.XIAOQU, xiaoqu);
						  
						  htmlArray.push('<tr>'+
								  		'<td>'+(containsXiaoQu >= 0 ? '' : data.XIAOQU)+'</td>'+
								  		'<td>'+(data.SUSHELOU)+'</td>'+
								  		'<td>'+(data.LOUCENG)+'</td>'+
										'<td>'+(data.ZONGCHUANGWEI)+'</td>'+
										'<td>'+(data.TOTALNAN)+'</td>'+
										'<td>'+(data.TOTALNV)+'</td>'+
										'<td>'+(data.ZONGZHANYONG)+'</td>'+
										'<td>'+(data.ZHANYONGNAN)+'</td>'+
										'<td>'+(data.ZHANYONGNV)+'</td>'+
										'<td>'+(data.ZONGKONGXIAN)+'</td>'+
										'<td>'+(data.KONGXIANNAN)+'</td>'+
										'<td>'+(data.KONGXIANNV)+'</td>'+
										'<td>'+(data.RUZHULV)+'</td>'+
									'</tr>');
						  
						  count ++;
						  			
						  //换校区时重置小计
						  if(containsXiaoQu == -1 ){
							  zongchuangwei = 0;//总床位数
							  totalnan = 0;//总人数(男)
							  totalnv = 0;//总人数(女)
							  zongzhanyong = 0;//已住总人数
							  zhanyongnan = 0;//已住总人数(男)
							  zhanyongnv = 0;//已住总人数(女)
							  zongkongxian = 0;//空闲床位数
							  kongxiannan = 0;//空闲床位数(男)
							  kongxiannv = 0;//空闲床位数(女)
							  xiaoqu = new Array(); 
						  }

						  zongchuangwei = apartmentStatistics.numAdd(zongchuangwei,data.ZONGCHUANGWEI);//总床位数
						  totalnan = apartmentStatistics.numAdd(totalnan,data.TOTALNAN);//总人数(男)
						  totalnv = apartmentStatistics.numAdd(totalnv,data.TOTALNV);//总人数(女)
						  zongzhanyong = apartmentStatistics.numAdd(zongzhanyong,data.ZONGZHANYONG);//已住总人数
						  zhanyongnan = apartmentStatistics.numAdd(zhanyongnan,data.ZHANYONGNAN);//已住总人数(男)
						  zhanyongnv = apartmentStatistics.numAdd(zhanyongnv,data.ZHANYONGNV);//已住总人数(女)
						  zongkongxian = apartmentStatistics.numAdd(zongkongxian,data.ZONGKONGXIAN);//空闲床位数
						  kongxiannan = apartmentStatistics.numAdd(kongxiannan,data.KONGXIANNAN);//空闲床位数(男)
						  kongxiannv = apartmentStatistics.numAdd(kongxiannv,data.KONGXIANNV);//空闲床位数(女)
						  
						  htmlArray.push('<tr class="warning">'+
								  	'<td>&nbsp;</td>'+
									'<td>合计</td>'+
									'<td>&nbsp;</td>'+
									'<td>'+zongchuangwei+'</td>'+
									'<td>'+totalnan+'</td>'+
									'<td>'+totalnv+'</td>'+
									'<td>'+zongzhanyong+'</td>'+
									'<td>'+zhanyongnan+'</td>'+
									'<td>'+zhanyongnv+'</td>'+
									'<td>'+zongkongxian+'</td>'+
									'<td>'+kongxiannan+'</td>'+
									'<td>'+kongxiannv+'</td>'+
									'<td>'+(zongzhanyong/zongchuangwei*100).toFixed(2)+'%</td>'+
								'</tr>');
						  count ++;
						  //去除重复的合计
						  if(containsXiaoQu >= 0 ){
							  htmlArray.splice(count - 3,1);
							  count --;
						  }
						  //添加不重复校区
						  if(containsXiaoQu == -1){
							  xiaoqu.push(data.XIAOQU);
						  }
						  
					  }
					 
				  }
				  
				  $("#apartmentStatisticsTable").html(htmlHead + htmlArray.join(''));
				  return false;
			  }
			});
	};
	//加载表格数据
	apartmentStatistics.getTab();
})(jQuery, window);
