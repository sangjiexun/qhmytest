/**
 * 介绍人明细页面对象
 */
//@ sourceURL=dormrztj.js
(function($, window) {
	var Dormrztjlist = {};
	
	
	
	laydate.render({
		elem:'#DATESTART',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	laydate.render({
		elem:'#DATEEND',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	
	
	//两小数相加解决精度出现的问题
	Dormrztjlist.numAdd=function (num1, num2) {//要相加的两个数
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
		var DATESTART = $("#DATESTART").val();
		var DATEEND = $("#DATEEND").val();
		var date1 = new Date(Date.parse(DATESTART));
		var date2 = new Date(Date.parse(DATEEND));
		if(date1>date2){
			layer.msg("结束时间不能早于开始时间");
			return false;
		}
		$("#dormrztjTable").bootstrapTable('destroy');
		Dormrztjlist.getTab();
	});
	
	//点击按钮导出
	$("#checkOut").click(function(){
		var DATESTART = $("#DATESTART").val();
		var DATEEND = $("#DATEEND").val();
		var date1 = new Date(Date.parse(DATESTART));
		var date2 = new Date(Date.parse(DATEEND));
		if(date1>date2){
			layer.msg("结束时间不能早于开始时间");
			return false;
		}
		window.location.href=encodeURI(_basepath+'studentDorm/DormrzdjExcel.json?DATESTART='+DATESTART+"&&DATEEND="+DATEEND);
	});
	
	//点击按钮打印
	$("#printOut").click(function(){
		var DATESTART = $("#DATESTART").val();
		var DATEEND = $("#DATEEND").val();

		
		var date1 = new Date(Date.parse(DATESTART));
		var date2 = new Date(Date.parse(DATEEND));
		if(date1>date2){
			layer.msg("结束时间不能早于开始时间");
			return false;
		}
		$("#batchPrintArea2").html('');
		
		var htmlHead = '';
		
		
		
		
		if((DATESTART != '' && DATEEND == '') || (DATESTART == '' && DATEEND != '') || (DATESTART != '' && DATEEND != '')){
			htmlHead += '<div style="font-size: 16px;"><span style="text-align: center;margin:7px 7px 7px 300px;font-size: 18px;">宿舍入住统计</span>('+DATESTART+' ~ '+DATEEND+')</div>';
		}else{
			htmlHead += '<div><h3 style="text-align: center;margin:7px 0px 7px 0px;">宿舍入住统计</h3></div>';
		}
			
		htmlHead += '<table class="table table-bordered2 sf_table" id="" style="margin-top:15px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;font-size: 12px;">';
		
		htmlHead += '<tr>'+
		'<th style="text-align: center">日期</th>'+
		'<th style="text-align: center">入住人数</th>'+
		'<th style="text-align: center">男生人数</th>'+
		'<th style="text-align: center">女生人数</th>'+		
		'</tr>';

		
		var DATESTART = $("#DATESTART").val();
		var DATEEND = $("#DATEEND").val();
		var url=_basepath+'studentDorm/DormrztjlistPrint.json?DATESTART='+DATESTART+"&&DATEEND="+DATEEND;
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
						  htmlArray.push('<tr>'+
								  	'<td>'+data.RZSJ+'</td>'+
							  		'<td>'+data.ZS+'</td>'+
									'<td>'+data.NAN+'</td>'+
									'<td>'+data.NV+'</td>'+
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
	Dormrztjlist.getTab = function() {

		//获得按钮权限
		var url = _basepath + "studentDorm/dormitoryrztjTable.json";
		$('#dormrztjTable').bootstrapTable(
						{
							url : url,// 数据源
							method : 'post',
							contentType:"application/x-www-form-urlencoded; charset=UTF-8",
							dataField : "rows",// 服务端返回数据键值
							// 就是说记录放的键值是rows，分页时使用总记录数的键值为total
							totalField : 'total',
							sortOrder : 'desc',
							striped : true, // 表格显示条纹
							pagination : true, // 启动分页
							pageNumber : 1, // 当前第几页
							pageSize : 20, // 每页显示的记录数
							pageList : [ 1, 5, 10, 20, 30, 50, 100 ], // 记录数可选列表
							search : false, // 是否启用查询
							formatSearch : function() {
								return '';
							},// 搜索框提示文字
							searchAlign : 'left',
							buttonsAlign : 'left',
							toolbarAlign : 'left',
							searchOnEnterKey : false,// true为按回车触发搜索事件
							showColumns : false, // 显示下拉框勾选要显示的列
							showRefresh : false, // 显示刷新按钮 --只是前台刷新，个人觉得没什么用
							minimumCountColumns : 2, // 最少允许的列数
							sidePagination : "server", // 表示服务端请求
							totalRows : 0, // server side need to set
							singleSelect : false,
							clickToSelect : true,
							onDblClickRow : function(row) {
							},
							onLoadSuccess:function(){
					            $('.bootstrap-table tr td').each(function () {
					                $(this).attr("title", $(this).text());
					                $(this).css("cursor", 'pointer');
					            });
					        },
							queryParams : function getParams(params) {
								//班级名称
								var keyWord = $('#keyWord').val();
								var DATESTART = $("#DATESTART").val();
								var DATEEND = $("#DATEEND").val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									keyWord : keyWord,
									DATESTART:DATESTART,
									DATEEND:DATEEND,
									sortName : this.sortName,
									sortOrder : this.sortOrder,
									pageindex : this.pageNumber
								// 当前页码
								};
								return temp;
							},
							buttonsAlign : "right",// 按钮对齐方式
							selectItemName : 'id',
							toolbar : "#toolbar",
							toolbarAlign : 'left',
							columns : [
									/*{
										field : 'DT_NO',
										title : '类型编号',
										align : "center",
										halign : 'center',
										sortable : false
									},*/
									{
										field : 'RZSJ',
										title : '日期',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'ZS',
										title : '入住人数',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'NAN',
										title : '男生人数',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'NV',
										title : '女生人数',
										align : "center",
										halign : 'center',
										sortable : false
									}],
						});
	
	};
	//加载表格数据
	Dormrztjlist.getTab();
})(jQuery, window);
