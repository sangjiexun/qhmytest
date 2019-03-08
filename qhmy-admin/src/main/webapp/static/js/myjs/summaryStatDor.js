/**
 * 退费汇总页面对象
 */
//@ sourceURL=refundSumlist.js
(function($, window) {
	var refundSumlist = {};
	
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		$("#refundSumlistTable").bootstrapTable('destroy');
		refundSumlist.getTab();
	});
	
	//点击按钮导出
	$("#checkOut").click(function(){
		var NAME =$("#orgtree3").val();
		window.location.href=encodeURI(_basepath+'summarystat/exportExcel.json?NAME='+NAME
				);
	});
	

	
	// 获取bootStrapTable表格数据,及参数设置
	refundSumlist.getTab = function() {
		$("#refundSumlistTable").html('');
		
		var htmlHead = '<tr>'+
					'<th rowspan="2" style="padding-top:25px;">校区</th>'+
					'<th rowspan="2" style="padding-top:25px;">宿舍楼</th>'+
					'<th rowspan="2" style="padding-top:25px;">楼层</th>'+
					'<th colspan="3">总床位数</th>'+
					'<th colspan="3">已住人数</th>'+
					'<th colspan="3">空闲床位数</th>'+
					'<th rowspan="2" style="padding-top:25px;">入住率</th>'+
				'</tr>'+
				 '<tr>'+
			
					'<th>总床位数</th>'+
					'<th>男生人数</th>'+
					'<th>女生人数</th>'+
					'<th>总人数</th>'+
					'<th>男生人数</th>'+
					'<th>女生人数</th>'+
					'<th>总床位数</th>'+
					'<th>男生人数</th>'+
					'<th>女生人数</th>'+
				'</tr>';
		
		
		
		
		var url = _basepath + "summarystat/table.json";
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  data: {
				  	NAME : $("#orgtree3").val(),
			  },
			  dataType : "json",
			  success : function(result) {
				  var list=result.list;
				  var body = "";
					  
				  for(var i=0;i<list.length;i++){
					  var data=list[i];
					  var style=data.hj_style;
					  if (typeof(style) == "undefined")
						  style="";
					  
					  
					  
					  body+="<tr class='"+style+"'><td>"+data.XIAOQU+"</td>"+
					        "<td>"+data.LOU+"</td>"+
					        "<td>"+data.CENG+"</td>"+
					        "<td>"+data.Z_MAXCHUANG+"</td>"+
					        "<td>"+data.Z_NANSHENG+"</td>"+
					        "<td>"+data.Z_NVSHENG+"</td>"+
					        "<td>"+data.Y_MAXCHUANG+"</td>"+
					        "<td>"+data.Y_NANSHENG+"</td>"+
					        "<td>"+data.Y_NVSHENG+"</td>"+
					        "<td>"+data.K_MAXCHUANG+"</td>"+
					        "<td>"+data.K_NANSHENG+"</td>"+
					        "<td>"+data.K_NVSHENG+"</td>"+
					  		"<td>"+data.RZL+"</td></tr>";					  
				  }
				  
				
				  
				  $("#refundSumlistTable").html(htmlHead + body+"");
				  
				  return false;
			  }
			});
	};
	//加载表格数据
	refundSumlist.getTab();
})(jQuery, window);
