/**
 * 预交费明细页面对象
 */
//@ sourceURL=advancePay.js
(function($, window) {
	var advancePay = {};
	
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
		// 入学年份
		var RXNFPKID = $('#schoolYear').val();
		// 班型
		var BANJITYPEPKID = $('#classType').val();
		var CENGCIPKID = $('#cengci').val();
		var SYPKID = $('#itempay').val();
		//关键字
		var sextest=$.trim($("#keywords").val());
		advancePay.updateTotalMsg(RXNFPKID,BANJITYPEPKID,CENGCIPKID,SYPKID,sextest,DATESTART,DATEEND);
		$("#advancePayTable").bootstrapTable('destroy');
		advancePay.getTab();
	});
	
	//点击按钮导出
	$("#checkOut").click(function(){
		//选择的日期起
		var STARTTIME=$('#DATESTART').val();
		//选择的日期止
		var ENDTIME=$('#DATEEND').val();
		var date1 = new Date(Date.parse(STARTTIME));
		var date2 = new Date(Date.parse(ENDTIME));
		if(date1>date2){
			layer.msg("结束时间不能早于开始时间");
			return false;
		}
		// 入学年份
		var RXNFPKID = $('#schoolYear').val();
		// 班型
		var BANJITYPEPKID = $('#classType').val();
		var CENGCIPKID = $('#cengci').val();
		var SYPKID = $('#itempay').val();
		//关键字
		var sextest=$.trim($("#keywords").val());
		
		advancePay.updateTotalMsg(RXNFPKID,BANJITYPEPKID,CENGCIPKID,SYPKID,sextest,STARTTIME,ENDTIME);
		window.location.href=encodeURI(_basepath+'summarystat/tuifeiExcel.json?STARTTIME='+STARTTIME
				+"&&ENDTIME="+ENDTIME+"&&RXNFPKID="+RXNFPKID
				+"&&BANJITYPEPKID="+BANJITYPEPKID+"&&CENGCIPKID="+CENGCIPKID+"&&SYPKID="+SYPKID+"&&sextest="+sextest);
	});
	//更新汇总信息
	advancePay.updateTotalMsg = function(RXNFPKID,BANJITYPEPKID,CENGCIPKID,SYPKID,sextest,STARTTIME,ENDTIME){
		var url = _basepath+"summarystat/getRefundDetailSum.json";
		$.ajax({
			type : 'POST',
			url : url,
			async : false,
			data : {
				STARTTIME : STARTTIME,
				ENDTIME : ENDTIME,
				SYPKID : SYPKID,
				CENGCIPKID : CENGCIPKID,
				BANJITYPEPKID : BANJITYPEPKID,
				RXNFPKID : RXNFPKID,
				sextest : sextest
			},
			dataType : "json",
			success : function(result) {
				var amountMsg = result.sumPd;
				$("#TOTALMONEY").html("退费总金额：<a class='gold'>"+amountMsg.TOALREFUNDMONEY+"</a>"); 
				$("#CASHTOTALMONEY").html("现金：<a class='gold'>"+amountMsg.TOTALCASH+"</a>"); 
				$("#CARDTOTALMONEY").html("银行卡：<a class='gold'>"+amountMsg.TOTALCARDMONEY+"</a>"); 
				$("#TTTOTALMONEY").html("电汇：<a class='gold'>"+amountMsg.TOTALTTMONEY+"</a>"); 
				
				
			}
		});
	};
	// 获取bootStrapTable表格数据,及参数设置
	advancePay.getTab = function() {
		var url = _basepath + "summarystat/RefundTable.json";
		$('#advancePayTable').bootstrapTable(
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
								var STARTTIME=$('#DATESTART').val();
								//选择的日期止
								var ENDTIME=$('#DATEEND').val();
								// 入学年份
								var RXNFPKID = $('#schoolYear').val();
								// 班型
								var BANJITYPEPKID = $('#classType').val();
								var CENGCIPKID = $('#cengci').val();
								var SYPKID = $('#itempay').val();

								//关键字
								var sextest=$.trim($("#keywords").val());
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									STARTTIME : STARTTIME,
									ENDTIME : ENDTIME,
									RXNFPKID : RXNFPKID,
									BANJITYPEPKID : BANJITYPEPKID,
									CENGCIPKID:CENGCIPKID,
									SYPKID:SYPKID,
									sextest : sextest,
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
										field : 'XUEHAO',
										title : '学号',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'WHKXUEXIAONAME',
										title : '文化课学校',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'XINGBIE',
										title : '性别',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'CCNAME',
										title : '学生类型',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'PAY_STYLE_NAME',
										title : '缴费类型',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'RXNF',
										title : '入学年份',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'BANJI_TYPE',
										title : '班型',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'MONEY',
										title : '退费金额',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'CJSJ',
										title : '退费时间',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'CASH',
										title : '现金',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'CARD',
										title : '银行卡',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'TT',
										title : '电汇',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'REMARK',
										title : '退费原因',
										align : "center",
										halign : 'center',
										sortable : false
									}
							],
						});
	};
	//加载表格数据
	advancePay.getTab();
})(jQuery, window);
