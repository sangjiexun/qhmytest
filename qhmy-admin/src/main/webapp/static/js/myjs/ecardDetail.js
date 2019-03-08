/**
 * 
 */
//@ sourceURL=ecardDetail.js
(function($, window) {
	var ecardDetail = {};
	
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
		var date1 = new Date(Date.parse(DATESTART));
		var date2 = new Date(Date.parse(DATEEND));
		if(date1>date2){
			layer.msg("结束时间不能早于开始时间");
			return false;
		}
		//关键字
		var keywords=$("#keywords").val();
		var pay_status = $("#pay_status").val();
		ecardDetail.updateTotalMsg(DATESTART,DATEEND,keywords,pay_status);
		$("#ecardDetailTable").bootstrapTable('destroy');
		ecardDetail.getTab();
	});
	
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
		//关键字
		var keywords=$("#jieshaorenName").val();
		var pay_status = $("#pay_status").val();
		ecardDetail.updateTotalMsg(DATESTART,DATEEND,keywords,pay_status);
		window.location.href=encodeURI(_basepath+'reportStat/ecardDetailExcel.json?DATESTART='+DATESTART
				+"&&DATEEND="+DATEEND+"&&keywords="+keywords+"&&pay_status="+pay_status);
	});
	
	//更新汇总信息
	ecardDetail.updateTotalMsg = function(DATESTART,DATEEND,keywords,pay_status){
		var url = _basepath+"reportStat/getAmountMsgEcardPay.json";
		$.ajax({
			type : 'POST',
			url : url,
			async : false,
			data : {
				DATESTART : DATESTART,
				DATEEND : DATEEND,
				keywords : keywords,
				pay_status : pay_status
			},
			dataType : "json",
			success : function(result) {
				var amountMsg = result.amountMsg;
				$("#TOTALPEOPLE").html("充值人数：<a class='gold'>"+amountMsg.TOTALPEOPLE+"</a>"); 
				$("#TOTALMONEY").html("充值总金额：<a class='gold'>"+amountMsg.TOTALMONEY+"</a>"); 
			}
		});
	};
	
	// 获取bootStrapTable表格数据,及参数设置
	ecardDetail.getTab = function() {
		var url = _basepath + "reportStat/ecardPayTable.json";
		$('#ecardDetailTable').bootstrapTable(
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
						//关键字
						var keywords=$("#keywords").val();
						var pay_status = $("#pay_status").val();
						var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
							limit : params.limit, // 页面大小
							DATESTART : DATESTART,
							DATEEND : DATEEND,
							keywords : keywords,
							pay_status : pay_status,
							sortName : this.sortName,
							sortOrder : this.sortOrder,
							pageindex : this.pageNumber
						// 当前页码
						};
						return temp;
					},
					//返回数据格式处理
			      
					buttonsAlign : "right",// 按钮对齐方式
					selectItemName : 'id',
					toolbar : "#toolbar",
					toolbarAlign : 'left',
					columns : [
							{
								field : 'STUDENT_NAME',
								title : '学生姓名',
								align : "center",
								halign : 'center',
								sortable : false
							},
							{
								field : 'XUEHAO',
								title : '学工号',
								align : "center",
								halign : 'center',
								sortable : false
							},
							{
								field : 'CARDNO',
								title : '卡号',
								align : "left",
								halign : 'center',
								sortable : false
							},
							{
								field : 'ORDERNO',
								title : '订单号',
								align : "center",
								halign : 'center',
								sortable : false
							},
							{
								field : 'TRANSACTION_MONEY',
								title : '充值金额',
								align : "right",
								halign : 'center',
								sortable : false
							},
							{
								field : 'CZSJ',
								title : '充值时间',
								align : "center",
								halign : 'center',
								sortable : false
							},

							{
								field : 'IS_YIKATONG',
								title : '充值结果',
								align : "center",
								halign : 'center',
								sortable : false

							}],
				});
	};
	//加载表格数据
	ecardDetail.getTab();
})(jQuery, window);
