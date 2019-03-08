 //@ sourceURL=reconciliationsummary.js
//对账汇总页面js
var reconsummary={};

reconsummary.search=function(){
	//销毁table
	$('#detailtable').bootstrapTable("destroy");
	//加载table
	reconsummary.getTable();
};
//点击按钮导出
$("#checkOut").click(function(){
	var STARTDATE =$("#startdate").val();//开始时间
    var ENDDATE = $("#enddate").val();//截止时间
    var KEYWORDS=$("#keywords").val();//学号姓名或电话
    var TRANSACTION_PAY_PLATFORM = $("#TRANSACTION_PAY_PLATFORM  option:selected").val();
    var TRANSACTION_STATUS = $("#TRANSACTION_STATUS  option:selected").val();
	window.location.href=encodeURI(_basepath+'reconciliation/exportdetailExcel.json?STARTDATE='+STARTDATE+'&ENDDATE='+ENDDATE+'&KEYWORDS='+KEYWORDS+'&RIQI='+RIQI+
			'&TRANSACTION_PAY_PLATFORM='+TRANSACTION_PAY_PLATFORM+'&TRANSACTION_STATUS='+TRANSACTION_STATUS);
});
//加载table数据
reconsummary.getTable = function() {
	var url = _basepath + "reconciliation/detailtable.json";
	$('#detailtable')
			.bootstrapTable(
					{
						url : url,// 数据源
						method: 'post',  
						dataField : "rows",// 服务端返回数据键值
						dataType: "json",					// 就是说记录放的键值是rows，分页时使用总记录数的键值为total
						totalField : 'total',
						contentType:"application/x-www-form-urlencoded; charset=UTF-8",
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
							var temp = {
								limit : params.limit, // 页面大小
								searchText : this.searchText,
								sortName : this.sortName,
								sortOrder : this.sortOrder,
								pageindex : this.pageNumber,
								RIQI:RIQI,//对账汇总的 列表日期
								KEYWORDS:$("#keywords").val(),//学号姓名或电话
								STARTDATE : $("#startdate").val(),//开始时间
							    ENDDATE : $("#enddate").val(),//截止时间
							    TRANSACTION_PAY_PLATFORM : $("#TRANSACTION_PAY_PLATFORM  option:selected").val(),
							    TRANSACTION_STATUS : $("#TRANSACTION_STATUS  option:selected").val()
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
									field : 'PAY_ORDER_ORDERNO',
									title : '订单号',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'TRANSACTION_HOST_SN',
									title : '交易平台流水号',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{	field : 'RIQI',
									title : '交易时间',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'TRANSACTION_PAY_PLATFORM',
									title : '支付方式',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'STUNAME',
									title : '付款方姓名',
									align : "left",
									halign : 'center',
									sortable : false
								},
								{
									field : 'SHENFENZHENGHAO',
									title : '身份证号',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'GRADE_NAME',
									title : '入学年份',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'CLASSTYPE_NAME',
									title : '班型',
									align : "left",
									halign : 'center',
									sortable : false
								},
								{
									field : 'PAYITEM',
									title : '摘要',
									align : "left",
									halign : 'center',
									sortable : false
								},

								{
									field : 'MONEY',
									title : '付款方金额',
									align : "right",
									halign : 'center',
									sortable : false
								},
								{
									field : 'TRANSACTION_STATUS',
									title : '对账结果',
									align : "right",
									halign : 'center',
									sortable : false
								}

						],
					});

};

$('#btn_search').bind('click',function(){
	reconsummary.search();
});

$('#back_tohuizong').bind('click',function(){
	$('.jf_szright').load(_basepath+"reconciliation/summary.php");
	
});


$(function(){
	laydate.render({
		elem:'#startdate',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	laydate.render({
		elem:'#enddate',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	reconsummary.getTable();
});
