/**
 * 介绍人汇总页面对象
 */
//@ sourceURL=jieshaorenSum.js
(function($, window) {
	var paySum = {};
	
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
		//选择的日期起
		var DATESTART=$('#DATESTART').val();
		//选择的日期止
		var DATEEND=$('#DATEEND').val();
		if(new Date(Date.parse(DATESTART)) > new Date(Date.parse(DATEEND))){
			layer.msg("首次缴费时间起不能大于首次缴费时间止!");
			return false;
		}
		$("#zsrSumTable").bootstrapTable('destroy');
		paySum.getTab();
	});
	//点击按钮导出
	$("#checkOut").click(function(){
		//选择的日期起
		var DATESTART=$('#DATESTART').val();
		//选择的日期止
		var DATEEND=$('#DATEEND').val();
		if(new Date(Date.parse(DATESTART)) > new Date(Date.parse(DATEEND))){
			layer.msg("首次缴费时间起不能大于首次缴费时间止!");
			return false;
		}
		//院校名称
		var TREENAME='';
		//介绍人名称
		var ZHAOSHENGRENNAME = $('#ZHAOSHENGRENNAME').val();
		window.location.href=encodeURI(_basepath+'report/zsrSumExcel.json?DATESTART='+DATESTART
				+"&&DATEEND="+DATEEND+"&&TREENAME="+TREENAME+"&&ZHAOSHENGRENNAME="+ZHAOSHENGRENNAME);
	});
	//点击按钮打印
	$("#printOut").click(function(){
		$("#batchPrintArea2").html('');
		var htmlHead = '<div><h3 style="text-align: center;margin:7px 0px 7px 0px;">招生人汇总</h3></div>';
		htmlHead += '<table class="table table-bordered sf_table" id="" style="margin-top:15px;">';
		htmlHead += '<tr>'+
					'<th style="text-align: center">工号</th>'+
					'<th style="text-align: center">招生人</th>'+
					'<th style="text-align: center">院校专业</th>'+
					'<th style="text-align: center">招生人数</th>'+
					'<th style="text-align: center">欠费人数</th>'+
					'<th style="text-align: center">实收总金额</th>'+
				'</tr>';
		//选择的日期起
		var DATESTART=$('#DATESTART').val();
		//选择的日期止
		var DATEEND=$('#DATEEND').val();
		if(new Date(Date.parse(DATESTART)) > new Date(Date.parse(DATEEND))){
			layer.msg("首次缴费时间起不能大于首次缴费时间止!");
			return false;
		}
		//院校名称
		var TREENAME='';
		//介绍人名称
		var ZHAOSHENGRENNAME = $('#ZHAOSHENGRENNAME').val();
		
		var url=_basepath+'report/zsrSumPrint.json';
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  data: {
				  DATESTART : DATESTART,
				DATEEND : DATEEND,
				TREENAME : TREENAME,
				ZHAOSHENGRENNAME : ZHAOSHENGRENNAME
			  },
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  var htmlArray = new Array();
					  var list = result.list;
					  for (var i = 0; i < list.length; i++) {
						  var data = list[i];
						  htmlArray.push('<tr>'+
								  	'<td>'+(data.TEACHER_NO == "" || data.TEACHER_NO == null ? "":data.TEACHER_NO)+'</td>'+
							  		'<td>'+data.XINGMING+'</td>'+
									'<td>'+data.NAME+'</td>'+
									'<td>'+data.STUNUM+'</td>'+
									'<td>'+data.NONUM+'</td>'+
									'<td>'+data.SUMMONEY+'</td>'+
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
	paySum.getTab = function() {
		var url = _basepath + "report/zsrSumTable.json";
		$('#zsrSumTable').bootstrapTable(
						{
							url : url,// 数据源
							method : 'post',
							contentType:"application/x-www-form-urlencoded; charset=UTF-8",
							dataField : "rows",// 服务端返回数据键值
							// 就是说记录放的键值是rows，分页时使用总记录数的键值为total
							totalField : 'total',
							sortOrder : 'desc',
							striped : true, // 表格显示条纹
							pagination : false, // 启动分页
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
								//院校名称
								var TREENAME=$('#orgtree').val();
								//介绍人名称
								var ZHAOSHENGRENNAME = $('#ZHAOSHENGRENNAME').val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : 10000/*params.limit*/, // 页面大小
									DATESTART : DATESTART,
									DATEEND : DATEEND,
									TREENAME : TREENAME,
									ZHAOSHENGRENNAME : ZHAOSHENGRENNAME,
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
										field : 'TEACHER_NO',
										title : '工号',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'XINGMING',
										title : '招生人',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'NAME',
										title : '院校专业',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'STUNUM',
										title : '招生人数',
										align : "center",
										halign : 'center',
										sortable : false
									},

									{
										field : 'NONUM',
										title : '欠费人数',
										align : "center",
										halign : 'center',
										sortable : false

									},
									{
										field : 'SUMMONEY',
										title : '实收总金额',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'opt',
										title : '查看详情',
										align : "center",
										halign : 'center',
										formatter : function(value, row, index) {
												return [
												        value != ''?'<a id="detailQuery" style="cursor:pointer;"><span title="查看详情" class="fa fa-search"></span></a>':'']
																.join('');
										}, // 紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										events : {
											'click #detailQuery' : function(e,value, row, index) {
												//去查看详情页面
												var DATESTART = $("#DATESTART").val();
												var DATEEND = $("#DATEEND").val();
												var data = {"ZHAOSHENGREN_PKID":row.ZHAOSHENGREN_PKID,"DEPARTMENT_ID":row.DEPARTMENT_ID,"PICI_PKID":row.PICI_PKID,
														"DATESTART":DATESTART,"DATEEND":DATEEND};
												$(".jf_szright").load(_basepath+ "report/seeZSRSumDetail.json",data);
											}
										}
									} ],
						});
	};
	//加载表格数据
	paySum.getTab();
})(jQuery, window);
