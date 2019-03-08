/**
 * 班级汇总统计对象
 */
//@ sourceURL=stuFenBu.js
(function($, window) {
	var classSum = {};
	
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		//$("#stuFenBuTable").bootstrapTable('destroy'); 
		classSum.getTab();
	});
	//导出按钮点击事件
	$("#checkOut").click(function(){
		var LOUCENGPKID = $("#LOUCENG  option:selected").val();
		var XUEYUANPKID = $("#XUEYUAN  option:selected").val();
		window.location.href=encodeURI(_basepath+'report/qingkuangExcel.json?LOUCENGPKID='+LOUCENGPKID
				+"&XUEYUANPKID="+XUEYUANPKID
				);
	});
	// 获取bootStrapTable表格数据,及参数设置
	classSum.getTab = function() {
		$("#stuFenBuTable").html('');
		var LOUCENGPKID = $("#LOUCENG  option:selected").val();
		var XUEYUANPKID = $("#XUEYUAN  option:selected").val();
		var htmlHead="";
		var url = _basepath + "report/getfenbuList.json";
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  data: {
				  LOUCENGPKID : LOUCENGPKID,
				  	XUEYUANPKID:XUEYUANPKID
			  },
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  var list = result.list;
					  if(list != null && list !=''){
						   htmlHead += '<tr>'+
							'<th class="tb-th bg-warning" style="vertical-align:middle;" >楼宇</th>';
						  for (var i = 0; i < list.length; i++) {
							  var pds = list[i];
							  htmlHead +='<th class="tb-th bg-warning" style="vertical-align:middle;" >'+pds.NAME+'</th>';
						  }
						  htmlHead +='</tr>';
					  }/*else{
						  htmlHead +='<tr>'+
									  '<td colspan="14">未查询到相关数据</td>'+
								  '</tr>';
					  }*/
					  
				  }
			  }
			});
		
		var htmls = "";
		var htmlss = "";
		var url = _basepath + "report/getallList.json";
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  data: {
				  LOUCENGPKID : LOUCENGPKID,
				  	XUEYUANPKID:XUEYUANPKID
			  },
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  var list = result.ALLLIST;
					  if(list != null && list !=''){
						  for (var i = 0; i < list.length; i++) {
							 // var pd = list[i];
							  htmlss += '<td>'+list[i]+'</td>';
							  if((i+1)%(result.XYSIZE+1)==0 && i!=0){
								  htmls += '<tr>'+htmlss+'</tr>';
								  htmlss = "";
							  }
									  
						  }
						  
					  }else{
						  htmls +='<tr>'+
									  '<td colspan="14">未查询到相关数据</td>'+
								  '</tr>';
					  }
					  
				  }
			  }
			});
		
		if(htmlHead!="" &&htmls!=""){
			$("#stuFenBuTable").html(htmlHead + htmls);
		}else{
			var s='<tr>'+
			  '<td colspan="14">未查询到相关数据</td>'+
			  '</tr>';
			$("#stuFenBuTable").html(s);
		}
		
	};
	//加载表格数据
	classSum.getTab();
})(jQuery, window);
