//@ sourceURL=tradeSumTable.js
(function($, window) {
	var tradeSumTable = {};
	
	//日期插件参数设置
	tradeSumTable.tradeSumTable=function(){
		laydate({
			   isclear: false, //是否显示清空
			   istoday: true, //是否显示今天
			   festival: true, //显示节日
			   istime:true,
			   format : 'YYYY-MM-DD hh:mm:ss',
			   max: laydate.now(), //最大日期
		});
	};
	
	//日期初始化
	$('.laydate-icon').click(function(){
		tradeSumTable.tradeSumTable();
	});
	
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		$("#tradeSumTable").bootstrapTable('destroy');
		tradeSumTable.getTab();
	});
	
	//点击按钮导出
	$("#checkOut").click(function(){
		//选择的日期起
		var DATESTART=$('#DATESTART').val();
		//选择的日期止
		var DATEEND=$('#DATEEND').val();
		//院校名称
		var DPKID=$('#orgtree').val();
		var CENGCI_PKID= $("#CENGCI_PKID option:selected").val();
		var PICI_PKID=$("#PICI_PKID option:selected").val();
		var PAYITEM=$("#PAYITEM").val();
		var XINGMING=$("#XINGMING").val();
		var NIANJI=$("#NIANJI option:selected").val();
		window.location.href=encodeURI(_basepath+'report/tradeSumExcel.json?DATESTART='+DATESTART
				+"&&DATEEND="+DATEEND+"&&DPKID="+DPKID+"&&CENGCI_PKID="+CENGCI_PKID
				+"&&PICI_PKID="+PICI_PKID+"&&PAYITEM="+PAYITEM
				+"&&XINGMING="+XINGMING
				+"&&NIANJI="+NIANJI);
	});
	
	//点击按钮返回
	$("#checkReturn").click(function(){
		var url = _basepath + "report/toTradeSum.php";
		$('.jf_szright').load(url);
	});
	
	// 获取bootStrapTable表格数据,及参数设置
	tradeSumTable.getTab = function() {
		var url = _basepath + "report/tradeSumTable.json";
		$('#tradeSumTable').bootstrapTable(
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
								//院校名称
								var TREENAME=$('#orgtree').val();
								var CENGCI_PKID= $("#CENGCI_PKID option:selected").val();
								var PICI_PKID=$("#PICI_PKID option:selected").val();
								var PAYITEM=$("#PAYITEM").val();
								var XINGMING=$("#XINGMING").val();
								var NIANJI=$("#NIANJI option:selected").val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									DATESTART : DATESTART,
									DATEEND : DATEEND,
									DPKID : TREENAME,
									CENGCI_PKID : CENGCI_PKID,
									PICI_PKID : PICI_PKID,
									PAYITEM : PAYITEM,
									XINGMING : XINGMING,
									NIANJI : NIANJI,
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
										field : 'PKID',// 可不加
										align : "center",
										halign : 'center'
									},
									{
										field : 'SHENFENZHENGHAO',
										title : '身份证号',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'XINGMING',
										title : '姓名',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'XINGBIE',
										title : '姓名',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'SHOUJI',
										title : '联系方式',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'NIANJI',
										title : '年级',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'DEPARTMENTNAMES',
										title : '院校专业',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'CENGCI_NAME',
										title : '学生类型',
										align : "center",
										halign : 'center',
										sortable : false
									},

									{
										field : 'PICI_NAME',
										title : '批次',
										align : "center",
										halign : 'center',
										sortable : false

									},

									{
										field : 'JINXIAONIANFEN',
										title : '进校年份',
										align : "center",
										halign : 'center',
										sortable : false

									},

									{
										field : 'SHISHOUMONEY',
										title : '实收金额',
										align : "center",
										halign : 'center',
										sortable : false

									},

									{
										field : 'HEZUOMONEY',
										title : '合作费总额',
										align : "center",
										halign : 'center',
										sortable : false

									},

									{
										field : 'FANKUANMONEY',
										title : '退款费总额',
										align : "center",
										halign : 'center',
										sortable : false

									},

									{
										field : 'opt',
										title : '操作',
										align : "center",
										halign : 'center',
										formatter : function(value, row,
												index) {
											return [
													'<span class="see fa fa-file-text-o" title="查看"></span>']
													.join('');
										},
										events : {
											'click .see' : function(e,value, row, index) {
												var url = _basepath + "payRecord/toPayRecord.php?stuPkid="+row.PKID;
												$('.jf_szright').load(url);
											}
										}
									}],
						});
		 $('#tradeSumTable').bootstrapTable('hideColumn', 'PKID');//隱藏列
	};
	//加载表格数据
	tradeSumTable.getTab();
})(jQuery, window);
