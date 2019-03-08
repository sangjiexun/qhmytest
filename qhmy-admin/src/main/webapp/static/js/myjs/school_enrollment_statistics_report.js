/**
 * 介绍人明细页面对象
 */
//@ sourceURL=toFeeSumlist.js
(function($, window) {
	var toFeeSumlist = {};
	
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
	
	//两小数相加解决精度出现的问题
	toFeeSumlist.numAdd=function (num1, num2) {//要相加的两个数
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
		var RUXUENIANFEN = $("#RUXUENIANFEN").val();
		var BANXING = $("#BANXING").val();
		var JIAOFEILEIXING = $("#JIAOFEILEIXING").val();
		toFeeSumlist.updateTotalMsg(RUXUENIANFEN,BANXING,JIAOFEILEIXING);
		$("#toFeeSumlistTable").bootstrapTable('destroy');
		toFeeSumlist.getTab();
	});
	
	
	//更新汇总信息
	toFeeSumlist.updateTotalMsg = function(RUXUENIANFEN,BANXING,JIAOFEILEIXING){
		var url = _basepath+"school_enrollment_statistics_report/getAmountMsgFeeDetial.json";
		var RUXUENIANFEN = $("#RUXUENIANFEN").val();
		var BANXING = $("#BANXING").val();
		var JIAOFEILEIXING = $("#JIAOFEILEIXING").val();
		$.ajax({
			type : 'POST',
			url : url,
			async : false,
			data : {
				RUXUENIANFEN : RUXUENIANFEN,
				BANXING : BANXING,
				JIAOFEILEIXING : JIAOFEILEIXING
			},
			dataType : "json",
			success : function(result) {
				var amountMsg = result.amountMsg;
				$("#TOTALMONEY").html("合计：<a class='gold'>"+amountMsg.TOTALSUM+"</a>"); 
			}
		});
	};
	
	//点击按钮导出
	$("#checkOut").click(function(){
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
		var RUXUENIANFEN = $("#RUXUENIANFEN").val();
		var BANXING = $("#BANXING").val();
		var JIAOFEILEIXING = $("#JIAOFEILEIXING").val();
		window.location.href=encodeURI(_basepath+'school_enrollment_statistics_report/feeSumlistExcel.json?RUXUENIANFEN='+RUXUENIANFEN
				+"&&BANXING="+BANXING+"&&JIAOFEILEIXING="+JIAOFEILEIXING+"&&DATESTART="+DATESTART+"&&DATEEND="+DATEEND
				);
	});
	
	//点击按钮打印
	$("#printOut").click(function(){
		$("#batchPrintArea2").html('');
		//选择的日期起
		var DATESTART=$('#DATESTART').val();
		//选择的日期止
		var DATEEND=$('#DATEEND').val();
		var htmlHead = '';
		
		
		htmlHead += '<div><h3 style="text-align: center;margin:7px 0px 7px 0px;">收费汇总</h3></div>';
		
		htmlHead += '<table class="table table-bordered2 sf_table" id="" style="margin-top:15px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">';
		
		htmlHead += '<tr>'+
					'<th style="text-align: center">入学年份</th>'+
					'<th style="text-align: center">班型</th>'+
					'<th style="text-align: center">缴费类型</th>'+
					'<th style="text-align: center">人数</th>'+
					'<th style="text-align: center">实收总金额</th>'+
					'<th style="text-align: center">现金总金额</th>'+
					'<th style="text-align: center">银行卡总金额</th>'+
					'<th style="text-align: center">电汇总金额</th>'+
					'<th style="text-align: center">支付宝总金额</th>'+
					'<th style="text-align: center">微信总金额</th>'+
				'</tr>';
		var RUXUENIANFEN = $("#RUXUENIANFEN").val();
		var BANXING = $("#BANXING").val();
		var JIAOFEILEIXING = $("#JIAOFEILEIXING").val();
		
		var url=_basepath+'school_enrollment_statistics_report/feeSumlistPrint.json?RUXUENIANFEN='+RUXUENIANFEN
		+"&&BANXING="+BANXING+"&&JIAOFEILEIXING="+JIAOFEILEIXING+'&&DATESTART='+DATESTART+'&&DATEEND='+DATEEND;
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
								  	'<td>'+data.RUXUENIANFEN+'</td>'+
							  		'<td>'+data.BANXING+'</td>'+
							  		'<td>'+(data.JIAOFEILEIXING == null || data.JIAOFEILEIXING == 'null' || data.JIAOFEILEIXING == 'undefined' ? '' : data.JIAOFEILEIXING)+'</td>'+
									'<td>'+data.STU_COUNT+'</td>'+
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
	toFeeSumlist.getTab = function() {
		//$("#toFeeSumlistTable").html('');
/*		//选择的日期起
		var DATESTART=$('#DATESTART').val();
		//选择的日期止
		var DATEEND=$('#DATEEND').val();
		var htmlHead = '<tr>'+
					'<th>入学年份</th>'+
					'<th>班型</th>'+
					'<th style="white-space:nowrap;">文化课学校</th>'+
					'<th>人数</th>'+
				'</tr>';
		var url = _basepath + "school_enrollment_statistics_report/getFeeSumlistTable.json";
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  data: {
				  RUXUENIANFEN:$("#RUXUENIANFEN").val(),
				  BANXING:$("#BANXING").val(),
				  JIAOFEILEIXING:$("#JIAOFEILEIXING").val(),
				  DATESTART : DATESTART,
					DATEEND : DATEEND,
			  },
			  dataType : "json",
			  success : function(result) {
				  var htmlArray = new Array();
				  if(result.result == 'success'){
					  var list = result.list;
					  var departments = new Array();
					  
					  //学生总数
					  var stucountTotals = 0;
					  //学生总数小计
					  var stucountTotalsXiaoji = 0;
					  //行数
					  var count = 0;
					  for (var i = 0; i < list.length; i++) {
						  var data = list[i];
						  //是否包含该部门
						  var isContainDepartment = $.inArray(data.RUXUENIANFEN + data.BANXING, departments);
						  //学生总数
						  var stucountTotal = data.STU_COUNT;
						  if(stucountTotal == 0) continue;
						  //总计 start=========================================================
						  stucountTotals = toFeeSumlist.numAdd(stucountTotals,stucountTotal);
						  //总计 end =========================================================
						  htmlArray.push('<tr>'+
								  		'<td>'+($.inArray(data.RUXUENIANFEN + data.BANXING, departments) >=0 ? data.RUXUENIANFEN : data.RUXUENIANFEN)+'</td>'+
								  		'<td>'+($.inArray(data.RUXUENIANFEN + data.BANXING, departments) >=0 ? data.BANXING : data.BANXING)+'</td>'+
								  		'<td>'+(data.SCHOOLNAME == null || data.SCHOOLNAME == 'null' || data.SCHOOLNAME == 'undefined' ? '' : data.SCHOOLNAME)+'</td>'+
										'<td>'+data.STU_COUNT+'</td>'+
									'</tr>');
						  count ++;
						  //换部门时重置小计
						  if(isContainDepartment == -1){
							  stucountTotalsXiaoji = 0;
						  }
						  stucountTotalsXiaoji = toFeeSumlist.numAdd(stucountTotalsXiaoji,stucountTotal);
						  
						  htmlArray.push('<tr class="warning">'+
							  		'<td >&nbsp;</td>'+
							  		'<td>小计</td>'+
							  		'<td >&nbsp;</td>'+
									'<td>'+stucountTotalsXiaoji+'</td>'+
									'<td>'+shishoumoneyTotalsXiaoji.toFixed(2)+'</td>'+
									'<td>'+cashmoneyTotalXiaoji.toFixed(2)+'</td>'+
									'<td>'+cardmoneyTotalXiaoji.toFixed(2)+'</td>'+
									'<td>'+ttmoneyTotalXiaoji.toFixed(2)+'</td>'+
									'<td>'+zfbmoneyTotalXiaoji.toFixed(2)+'</td>'+
									'<td>'+wxmoneyTotalXiaoji.toFixed(2)+'</td>'+
								'</tr>');
						  count ++;
						  //去除重复的小计
						  if(isContainDepartment >= 0){
							  htmlArray.splice(count - 3,1);
							  count --;
						  }
						  //添加不重复部门
						  if(isContainDepartment == -1){
							  departments.push(data.RUXUENIANFEN + data.BANXING);
						  }
						  
						  
					  }
					  htmlArray.push('<tr class="danger">'+
								'<td>合计</td>'+
								'<td>&nbsp;</td>'+
								'<td>&nbsp;</td>'+
								'<td>'+stucountTotals+'</td>'+
					
							'</tr>')
				  }
				  
				  $("#toFeeSumlistTable").html(htmlHead + htmlArray.join(''));
				  
				  return false;
			  }
			});*/
		
		var url = _basepath + "school_enrollment_statistics_report/goDetailTable.json";
		$('#toFeeSumlistTable').bootstrapTable(
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
								return '请输入简拼.编码.企业名称查询';
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
							queryParams : function getParams(params) {
								//选择的日期起
								var DATESTART=$('#DATESTART').val();
								//选择的日期止
								var DATEEND=$('#DATEEND').val();
								// 入学年份
								var RUXUENIANFEN = $('#RUXUENIANFEN').val();
								// 班型
								var BANXING = $('#BANXING').val();
								//关键字
								var JIAOFEILEIXING=$("#JIAOFEILEIXING").val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									DATESTART : DATESTART,
									DATEEND : DATEEND,
									RUXUENIANFEN : RUXUENIANFEN,
									BANXING : BANXING,
									JIAOFEILEIXING : JIAOFEILEIXING,
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
									{
										field : 'RUXUENIANFEN',
										title : '入学年份',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'BANXING',
										title : '班型',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'SCHOOLNAME',
										title : '文化课学校',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'STU_COUNT',
										title : '人数',
										align : "center",
										halign : 'center',
										sortable : false
									} ],
						});
		
		
	};
	//加载表格数据
	toFeeSumlist.getTab();
})(jQuery, window);
