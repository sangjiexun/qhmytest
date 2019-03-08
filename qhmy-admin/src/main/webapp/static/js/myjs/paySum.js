/**
 * 核账页面对象
 */
//@ sourceURL=paySum.js
(function($, window) {
	var paySum = {};
	
	//日期插件参数设置
	paySum.paySum=function(){
		laydate({
			   isclear: false, //是否显示清空
			   istoday: true, //是否显示今天
			   festival: true, //显示节日
			   max: laydate.now(), //最大日期
		});
	};
	
	//日期初始化
	$('.laydate-icon').click(function(){
		paySum.paySum();
	});
	
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		$("#paySumTable").bootstrapTable('destroy');
		paySum.getTab();
	});
	
	// 获取bootStrapTable表格数据,及参数设置
	paySum.getTab = function() {
		var url = _basepath + "reportStat/paySumTable.json";
		$('#paySumTable').bootstrapTable(
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
								//审核状态
								var checkStatus=$('#checkStatus').val();
								//项目名称
								var ITEMNAME = $('#itemName').val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									DATESTART : DATESTART,
									DATEEND : DATEEND,
									CHECKSTATUS : checkStatus,
									ITEMNAME : ITEMNAME,
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
										field : 'RECEPEOPLE',
										title : '应收人数',
										align : "right",
										halign : 'center',
										sortable : false
									},
									{
										field : 'RECEMONEY',
										title : '应收金额',
										align : "right",
										halign : 'center',
										sortable : false
									},
									{
										field : 'ACTUALPEOPLE',
										title : '实收人数',
										align : "right",
										halign : 'center',
										sortable : false
									},
									{
										field : 'ACTUALMONEY',
										title : '实收金额',
										align : "right",
										halign : 'center',
										sortable : false
									},

									{
										field : 'UPEOMON',
										title : '网上缴费人数-金额',
										align : "left",
										halign : 'center',
										sortable : false

									},
									{
										field : 'OPEOMON',
										title : '其他缴费人数-金额',
										align : "left",
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
														'<a id="detailQuery" style="cursor:pointer;"><span title="查看详情" class="fa fa-search"></span></a>']
																.join('');
										}, // 紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										events : {
											'click #detailQuery' : function(e,value, row, index) {
												//去查看详情页面
												$(".jf_szright").load(_basepath+ 'reportStat/goDetailJson.json?PKID='+ row.PKID);
											}
										}
									} ],
						});
	};
	//加载表格数据
	paySum.getTab();
})(jQuery, window);
