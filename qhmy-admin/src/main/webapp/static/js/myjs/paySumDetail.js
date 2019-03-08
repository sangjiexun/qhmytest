/**
 * 核账页面对象
 */
//@ sourceURL=paySumDetail.js
(function($, window) {
	var paySumDetail = {};
	
	laydate.render({
		elem:'#DATESTART',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: -7
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
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		$("#paySumDetailTable").bootstrapTable('destroy');
		paySumDetail.getTab();
	});
	
	//点击按钮导出
	$("#checkOut").click(function(){
		//选择的日期起
		var DATESTART=$('#DATESTART').val();
		//选择的日期止
		var DATEEND=$('#DATEEND').val();
		//缴费方式
		var DETAILSTATUS = $("#detailStatus").val();
		//关键词
		var serchText = $('#serchText').val();
		var PKID = $("#paySumPKID").val();
		window.location.href=encodeURI(_basepath+'reportStat/exportExcel.json?SERCHTEXT='+serchText+'&&DATESTART='+DATESTART+'&&DATEEND='+DATEEND
				+'&&PKID='+PKID+"&&DETAILSTATUS="+DETAILSTATUS);
	});
	
	//点击按钮返回
	$("#checkReturn").click(function(){
		var url = _basepath + "reportStat/reportStat.php";
		$('.jf_szright').load(url);
	});
	
	// 获取bootStrapTable表格数据,及参数设置
	paySumDetail.getTab = function() {
		var url = _basepath + "reportStat/goPayDetailTable.json";
		$('#paySumDetailTable').bootstrapTable(
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
								//缴费方式
								var DETAILSTATUS = $("#detailStatus").val();
								//关键词
								var serchText = $('#serchText').val();
								var PKID = $("#paySumPKID").val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									DATESTART : DATESTART,
									DATEEND : DATEEND,
									PKID : PKID,
									DETAILSTATUS : DETAILSTATUS,
									SERCHTEXT : serchText,
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
										field : 'ITEMNAME',
										title : '项目名称',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'XINGMING',
										title : '姓名',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'DEPTNAME',
										title : '院系',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'COST',
										title : '缴费金额',
										align : "right",
										halign : 'center',
										sortable : false
									},
									{
										field : 'DISCOUNT',
										title : '优惠方式',
										align : "right",
										halign : 'center',
										sortable : false
									},
									{
										field : 'AMOUNTRECEIVABLE',
										title : '应收金额',
										align : "right",
										halign : 'center',
										sortable : false
									},
									{
										field : 'MSG',
										title : '缴费方式-缴费金额',
										align : "left",
										halign : 'center',
										sortable : false

									},
									{
										field : 'XGSJ',
										title : '缴费时间',
										align : "center",
										halign : 'center',
										sortable : false

									}],
						});
	};
	//加载表格数据
	paySumDetail.getTab();
})(jQuery, window);
