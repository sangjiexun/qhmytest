/**
 * 介绍人明细页面对象
 */
//@ sourceURL=zsrSumDetail.js
(function($, window) {
	var zsrSumDetail = {};
	
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
			layer.msg("开始时间不能大于结束时间!");
			return false;
		}
		$("#zsrSumDetailTable").bootstrapTable('destroy');
		zsrSumDetail.getTab();
	});
	
	//点击按钮导出
	$("#checkOut").click(function(){
		//选择的日期起
		var DATESTART=$('#DATESTART').val();
		//选择的日期止
		var DATEEND=$('#DATEEND').val();
		//院校名称
		var TREENAME=$('#orgtree').val();
		//缴费状态
		var STATUSSELECT=$("#statusselect").val();
		//介绍人名称
		var ZHAOSHENGRENNAME = $('#zhaoshengrenName').val();
		//介绍人PKID
		var ZHAOSHENGREN_PKID = $("#ZHAOSHENGREN_PKID").val();
		//院校PKID
		var DEPARTMENT_ID = $("#DEPARTMENT_ID").val();
		//批次PKID
		var PICI_PKID = $("#PICI_PKID").val();
		if(new Date(Date.parse(DATESTART)) > new Date(Date.parse(DATEEND))){
			layer.msg("开始时间不能大于结束时间!");
			return false;
		}
		/*var data = {"DATESTART":DATESTART,"DATEEND":DATEEND,"TREENAME":TREENAME,"STATUSSELECT":STATUSSELECT,"ZHAOSHENGRENNAME":ZHAOSHENGRENNAME,
				"ZHAOSHENGREN_PKID":ZHAOSHENGREN_PKID,"DEPARTMENT_ID":DEPARTMENT_ID,"PICI_PKID":PICI_PKID}*/
		window.location.href=encodeURI(_basepath+'report/zsrSumDetailExcel.json?DATESTART='+DATESTART
				+"&&DATEEND="+DATEEND+"&&TREENAME="+TREENAME+"&&STATUSSELECT="+STATUSSELECT
				+"&&ZHAOSHENGRENNAME="+ZHAOSHENGRENNAME+"&&ZHAOSHENGREN_PKID="+ZHAOSHENGREN_PKID
				+"&&DEPARTMENT_ID="+DEPARTMENT_ID+"&&PICI_PKID="+PICI_PKID);
	});
	
	//点击按钮返回
	$("#checkReturn").click(function(){
		var url = _basepath + "report/toZSRSum.php";
		$('.jf_szright').load(url);
	});
	
	// 获取bootStrapTable表格数据,及参数设置
	zsrSumDetail.getTab = function() {
		var url = _basepath + "report/zsrSumDetailTable.json";
		$('#zsrSumDetailTable').bootstrapTable(
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
								//缴费状态
								var STATUSSELECT=$("#statusselect").val();
								//介绍人名称
								var ZHAOSHENGRENNAME = $('#zhaoshengrenName').val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									DATESTART : DATESTART,
									DATEEND : DATEEND,
									TREENAME : TREENAME,
									STATUSSELECT : STATUSSELECT,
									ZHAOSHENGRENNAME : ZHAOSHENGRENNAME,
									PICI_PKID : $("#PICI_PKID").val(),
									DEPARTMENT_ID : $("#DEPARTMENT_ID").val(),
									ZHAOSHENGREN_PKID : $("#ZHAOSHENGREN_PKID").val(),
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
										field : 'DEPARTMENTNAMES',
										title : '院校专业',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'ZHAOSHENGREN_NAME',
										title : '招生人',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'AMOUNTRECEIVABLE',
										title : '应收总金额',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter : function(value, row,
												index) {
												return [
														row.AMOUNTRECEIVABLE.toFixed(2)+'' ]
														.join('');
										}
									},

									{
										field : 'AMOUNTCOLLECTION',
										title : '实收总金额',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter : function(value, row,
												index) {
												return [
														row.AMOUNTCOLLECTION.toFixed(2)+'' ]
														.join('');
										}

									},

									{
										field : 'CJSJ',
										title : '首次缴费时间',
										align : "center",
										halign : 'center',
										sortable : false

									} ],
						});
	};
	//加载表格数据
	zsrSumDetail.getTab();
})(jQuery, window);
