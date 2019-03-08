//@ sourceURL=stuPayDetail.js
(function($, window) {
	var stuPayDetail = {};
	
	$("#NIANJI").bind("change", function() {
		changeGrade();
	});
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		  $("#toFeeSumlistTable").html("");
		stuPayDetail.getTab();
	});
	//导出按钮点击事件
	$("#checkOut").click(function(){
		var BIANMA=$("#BIANMA").val();
		var SYDNAME=$("#SYDNAME").val();
		var LQPEOPLE=$("#LQPEOPLE").val();
		var BDPEOPLE=$("#BDPEOPLE").val();
		
		window.location.href=encodeURI(_basepath+'report/allShengYuanDetailExcel.json?BIANMA='+BIANMA
				+"&&SYDNAME="+SYDNAME
				+"&&LQPEOPLE="+LQPEOPLE
				+"&&BDPEOPLE="+BDPEOPLE
				);
	});
	

	
	// 获取bootStrapTable表格数据,及参数设置
	stuPayDetail.getTab = function() {
		var htmlHead = '<tr>'+
		'<th>生源地</th>'+
		'<th style="white-space:nowrap;">录取人数</th>'+
		'<th>报到人数</th>'+
	'</tr>';
		var url = _basepath + "report/getShengYuan.json";
		var BIANMA=$("#BIANMA").val();
		var SYDNAME=$("#SYDNAME").val();
		$.ajax({	
			  type: 'POST',
			  url: url,
			  async: false,
			  data:{
				  BIANMA:BIANMA,
				  SYDNAME:SYDNAME
			  },
			  dataType : "json",
			  success : function(result) {
				  var htmlArray = new Array();
                  var ssList=result["ssList"];//宿舍\专业
                    
                  var lqpeople=0;//录取人数
                  var bdpeople=0;//报道人数

                  for(var i=0;i<ssList.length;i++){
				      
							htmlArray.push(
									"<tr style='text-align: center;'>"+
									    "<td>"+ssList[i].NAME+"</td>"+
									    "<td>"+ssList[i].LQPEOPLE+"</td>"+
									    "<td>"+ssList[i].BDPEOPLE+"</td>"+
									    "</tr>"
							)
							 lqpeople+=ssList[i].LQPEOPLE;
							bdpeople+=ssList[i].BDPEOPLE;
				  }
			
			
					htmlArray.push(
						    "<tr class='danger'  style='text-align: center;'>"+
							    "<td>合计</td>"+
							    "<td>"+lqpeople+"<input value='"+lqpeople+"' type='hidden' id='LQPEOPLE'></td>"+
								"<td>"+bdpeople+"<input value='"+bdpeople+"' type='hidden' id='BDPEOPLE'></td>"+
							    "</tr>")
				  
				  
				  
				  $("#getAllShengYuanTable").html(htmlHead + htmlArray.join(''));
			  },error : function(){
				  alert("网络错误");
			  }
		});
		
	};
	//加载表格数据
	stuPayDetail.getTab();
})(jQuery, window);
