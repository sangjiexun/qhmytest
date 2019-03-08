/**
 * 班级欠费汇总对象
 */
//@ sourceURL=banjiArrearageSum.js
(function($, window) {
	var stuArrearageSum = {};
	
	$("#NIANJI").bind("change", function() {
		changeGrade();
	});
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		//选择的入学年份
		var NIANJI=$('#NIANJI').val();
		//院校专业pkids
		var orgtree=$('#orgtree').val();
		//班级
		var banji = $('#banji').val();
		//关键词
		$("#stuArrearageSumTable").bootstrapTable('refresh', {url: encodeURI(_basepath+"report/banJiArrearageSumTable.json?NIANJI="+NIANJI+"&&orgtree="+orgtree+
				"&BANJI="+banji)});
		stuArrearageSum.sums();
		
	});
	//汇总
	stuArrearageSum.sums = function(){
		//选择的入学年份
		var NIANJI=$('#NIANJI').val();
		//院校专业pkids
		var orgtree=$('#orgtree').val();
		//班级
		var banji = $('#banji').val();
		$.ajax({
			  type: 'POST',
			  url: _basepath+"report/banJiArrearageSum.json",
			  async: false,
			  data: {NIANJI:NIANJI,orgtree:orgtree,BANJI:banji},
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  $("#QIANFEIRENSHU").html("欠费人数：<a>"+(result.sums == 'null' || result.sums == null ?'0':result.sums.QIANFEIRENSHU)+"</a>");
					  $("#ZONGYINGSHOU").html("总应收：<a>"+(result.sums == 'null' || result.sums == null ?'0':result.sums.TOTALAMOUNTRECEIVABLE)+"</a>");
					  $("#ZONGSHISHOU").html("总实收：<a>"+(result.sums == 'null' || result.sums == null ?'0':result.sums.TOTALAMOUNTCOLLECTION)+"</a>");
					  $("#ZONGQIANFEI").html("欠费：<a>"+(result.sums == 'null' || result.sums == null ?'0':result.sums.QIANFEIHEJI)+"</a>");
				  }
			  }
			});
	};
	//导出按钮点击事件
	$("#checkOut").click(function(){
		//选择的入学年份
		var NIANJI=$('#NIANJI').val();
		//院校专业pkids
		var orgtree=$('#orgtree').val();
		//班级
		var banji = $('#banji').val();
		window.location.href=encodeURI(_basepath+'report/banJiArrearageSumExcel.json?NIANJI='+NIANJI
				+"&&orgtree="+orgtree+"&&BANJI="+banji
				);
	});
	
	// 获取bootStrapTable表格数据,及参数设置
	stuArrearageSum.getTab = function() {
		//获得按钮权限
		var url = _basepath + "report/banJiArrearageSumTable.json";
		$('#stuArrearageSumTable').bootstrapTable(
						{
							url : url,// 数据源
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
								//选择的入学年份
								var NIANJI=$('#NIANJI').val();
								//院校专业pkids
								var orgtree=$('#orgtree').val();
								//班级
								var banji = $('#banji').val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									NIANJI : NIANJI,
									orgtree : orgtree,
									BANJI : banji,
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
										field : 'NIANJI',
										title : '入学年份',
										align : "center",
										halign : 'center',
										sortable : false,
										cellStyle:function(value,row,index){  
										    if (value=='合计'){  
										        return {css:{"background-color":"#fcf8e3"}}  
										    }else{  
										        return {css:{"background-color":"white"}}  
										    }  
										} 
									},
									{
										field : 'DEPARTMENTNAME',
										title : '院校专业',
										align : "left",
										halign : 'center',
										sortable : false,
										cellStyle:function(value,row,index){  
										    if (row.NIANJI=='合计'){  
										        return {css:{"background-color":"#fcf8e3"}}  
										    }else{  
										        return {css:{"background-color":"white"}}  
										    }  
										} 
									},
									{
										field : 'CLASS_NAME',
										title : '班级名称',
										align : "left",
										halign : 'center',
										sortable : false,
										cellStyle:function(value,row,index){  
										    if (row.NIANJI=='合计'){  
										        return {css:{"background-color":"#fcf8e3"}}  
										    }else{  
										        return {css:{"background-color":"white"}}  
										    }  
										}
									},
									{
										field : 'SCHOOL_YEAR_NAME',
										title : '收费学年',
										align : "left",
										halign : 'center',
										sortable : false,
										cellStyle:function(value,row,index){  
										    if (row.NIANJI=='合计'){  
										        return {css:{"background-color":"#fcf8e3"}}  
										    }else{  
										        return {css:{"background-color":"white"}}  
										    }  
										}
									},
									{
										field : 'PAYITEM',
										title : '缴费项目',
										align : "left",
										halign : 'center',
										sortable : false,
										cellStyle:function(value,row,index){  
										    if (row.NIANJI=='合计'){  
										        return {css:{"background-color":"#fcf8e3"}}  
										    }else{  
										        return {css:{"background-color":"white"}}  
										    }  
										}
									},
									{
										field : 'QIANFEIRENSHU',
										title : '欠费人数',
										align : "left",
										halign : 'center',
										sortable : false,
										cellStyle:function(value,row,index){  
										    if (row.NIANJI=='合计'){  
										        return {css:{"background-color":"#fcf8e3"}}  
										    }else{  
										        return {css:{"background-color":"white"}}  
										    }  
										}
									},
									{
										field : 'TOTALMONEY',
										title : '总费用',
										align : "right",
										halign : 'center',
										sortable : false,
										cellStyle:function(value,row,index){  
										    if (row.NIANJI=='合计'){  
										        return {css:{"background-color":"#fcf8e3"}}  
										    }else{  
										        return {css:{"background-color":"white"}}  
										    }  
										}
									},

									{
										field : 'TOTAL_DISTINCT',
										title : '优惠合计',
										align : "right",
										halign : 'center',
										sortable : false,
										cellStyle:function(value,row,index){  
										    if (row.NIANJI=='合计'){  
										        return {css:{"background-color":"#fcf8e3"}}  
										    }else{  
										        return {css:{"background-color":"white"}}  
										    }  
										}

									},

									{
										field : 'TOTALAMOUNTRECEIVABLE',
										title : '总应收',
										align : "right",
										halign : 'center',
										sortable : false,
										cellStyle:function(value,row,index){  
										    if (row.NIANJI=='合计'){  
										        return {css:{"background-color":"#fcf8e3"}}  
										    }else{  
										        return {css:{"background-color":"white"}}  
										    }  
										}

									},
									{
										field : 'TOTALAMOUNTCOLLECTION',
										title : '总实收',
										align : "right",
										halign : 'center',
										sortable : false,
										cellStyle:function(value,row,index){  
										    if (row.NIANJI=='合计'){  
										        return {css:{"background-color":"#fcf8e3"}}  
										    }else{  
										        return {css:{"background-color":"white"}}  
										    }  
										}

									},
									{
										field : 'QIANFEIHEJI',
										title : '欠费合计',
										align : "right",
										halign : 'center',
										sortable : false,
										formatter : function(value, row,
												index) {
											return (row.TOTALAMOUNTRECEIVABLE-row.TOTALAMOUNTCOLLECTION)
										},
										cellStyle:function(value,row,index){  
										    if (row.NIANJI=='合计'){  
										        return {css:{"background-color":"#fcf8e3"}}  
										    }else{  
										        return {css:{"background-color":"white"}}  
										    }  
										}
									},
									{

										field : 'opt',
										title : '查看详情',
										align : "center",
										halign : 'center',
										formatter : function(value, row, index) {
											if(row.NIANJI=='合计'){
												return ''
											}
											return [ '<a class="fa fa-search detail" href="javascript:void(0)" title="查看详情"><i class="" ></i></a>' ]
													.join('');
										},
										cellStyle:function(value,row,index){  
										    if (row.NIANJI=='合计'){  
										        return {css:{"background-color":"#fcf8e3"}}  
										    }else{  
										        return {css:{"background-color":"white"}}  
										    }  
										},
										events : {
											'click .detail' : function(e, value,
													row, index) {
												// 查看详情
												$(".jf_szright").load(_basepath+'report/toStuArrearageDetail.php?',
														{
															NIANJI:row.NIANJI,
															DEPARTMENT_PKID:row.DEPARTMENT_PKID,
															SCHOOL_YEAR_PKID:row.SCHOOL_YEAR_PKID,
															BANJI:row.BANJI,
															CLASS_NAME:row.CLASS_NAME,
															PAY_ITEM_PKID:row.PAY_ITEM_PKID,
															FROMBANJIHUIZONG:'yes'
														},function(){
															
														}
														);
											}
										}

									}
									],
						});
	};
	//加载表格数据
	stuArrearageSum.getTab();
	stuArrearageSum.sums();
})(jQuery, window);
