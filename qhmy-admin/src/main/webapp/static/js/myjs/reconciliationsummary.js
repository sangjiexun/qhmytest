//@ sourceURL=reconciliationsummary.js
//对账汇总页面js
var reconsummary={};

reconsummary.search=function(){
	//销毁table
	$('#rctable').bootstrapTable("destroy");
	//加载table
	reconsummary.getTable();
};
//点击按钮导出
$("#checkOut").click(function(){
	var STARTDATE =$("#startdate").val();//开始时间
    var ENDDATE = $("#enddate").val();//截止时间
	window.location.href=encodeURI(_basepath+'reconciliation/exportExcel.json?STARTDATE='+STARTDATE+'&ENDDATE='+ENDDATE);
});
//加载table数据
reconsummary.getTable = function() {
	var url = _basepath + "reconciliation/table.json";
	$('#rctable')
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
								STARTDATE : $("#startdate").val(),//开始时间
							    ENDDATE : $("#enddate").val()//截止时间
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
									field : 'RQ',
									title : '对账日期',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'ZDZS',
									title : '账单总数',
									align : "right",
									halign : 'center',
									sortable : false
								},
								{
									field : 'ZDZJE',
									title : '账单总金额',
									align : "right",
									halign : 'center',
									sortable : false
								},

								{
									field : 'SUCCCOUNTS',
									title : '成功账单总数',
									align : "right",
									halign : 'center',
									sortable : false
								},
								{
									field : '',
									title : '失败账单总数',
									align : "right",
									halign : 'center',
									sortable : false,
									formatter : function(value, row, index) {
										return row.ZDZS-row.SUCCCOUNTS;
									}
								},
								{
									field : 'SUCCMONEYCOUNTS',// 可不加
									title : '成功账单金额',// 标题 可不加
									align : "right",
									halign : 'center'
								},

								{

									field : 'opt',
									title : '查看详情',
									align : "center",
									halign : 'center',
									formatter : function(value, row, index) {
										return [ '<a class="fa fa-search detail" href="javascript:void(0)" title="查看详情"><i class="" ></i></a>' ]
												.join('');
									},
									events : {
										'click .detail' : function(e, value,
												row, index) {
											// 查看详情
											$(".jf_szright").load(_basepath+ 'reconciliation/detail.json?RIQI='+ row.RQ+'&flag=y');
										}
									}

								}

						],
					});

};

$('#btn_search').bind('click',function(){
	reconsummary.search();
});


$(function(){
	//日期插件参数设置
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
