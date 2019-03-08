/**
 * 核账明细页面对象
 */
//@ sourceURL=checkMoneyDetail.js
(function($, window) {
	var checkDetailMoney = {};
	
	//获取核账主表PKID
	checkDetailMoney.checkMoneyPKID = $("#checkMoneyPKID").val();
	
	//日期插件参数设置
	checkDetailMoney.checkDetailDate=function(){
		laydate({
			   isclear: true, //是否显示清空
			   istoday: true, //是否显示今天
			   festival: true, //显示节日
			   start: laydate.now(0, "YYYY-MM-DD"),
		});
	};
	
	//日期初始化
	$('#checkDetailDate').click(function(){
		checkDetailMoney.checkDetailDate();
	});
	
	//查询按钮点击事件
	$('#checkDeatilQuery').click(function(){
		//学年
		var ACADEMICYEAR = $('#school_year_sj').val();
		//缴费类型
		var PAYCOSTTYPE = $('#pay_style_sj').val();
		//选择的日期
		var checkDetailDate=$('#checkDetailDate').val();
		//学生姓名
		var studentName = $('#studentName').val();
		//支付类型
		var JIAOFEILEIXING = $('#JIAOFEILEIXING').val();
		var checkMoneyPKID = $("#checkMoneyPKID").val();
		$("#checkMoneyDetailTable").bootstrapTable('refresh', {url: encodeURI(_basepath+"checkMoney/checkMoneyDeailTable.json?CHECKDETAILDATE="+checkDetailDate+"&&STUDENTNAME="+studentName
				+"&&PKID="+checkMoneyPKID+"&&JIAOFEILEIXING="+JIAOFEILEIXING+"&&ACADEMICYEAR="+ACADEMICYEAR+"&&PAYCOSTTYPE="+PAYCOSTTYPE)});
		
	});
	
	//点击导出按钮事件
	$("#checkDeatilOut").click(function(){
		//学年
		var ACADEMICYEAR = $('#school_year_sj').val();
		//缴费类型
		var PAYCOSTTYPE = $('#pay_style_sj').val();
		//选择的日期
		var checkDetailDate=$('#checkDetailDate').val();
		//学生姓名
		var studentName = $('#studentName').val();
		//缴费类型
		var JIAOFEILEIXING = $('#JIAOFEILEIXING').val();
		var checkMoneyPKID = $("#checkMoneyPKID").val();
		window.location.href=encodeURI(_basepath+'checkMoney/exportExcel.json?CHECKDETAILDATE='+checkDetailDate+'&&STUDENTNAME='+studentName+
				'&&JIAOFEILEIXING='+JIAOFEILEIXING+'&&PKID='+checkMoneyPKID+"&&ACADEMICYEAR="+ACADEMICYEAR+"&&PAYCOSTTYPE="+PAYCOSTTYPE);
	});
	
	//点击返回按钮事件
	$("#checkdetailReturn").click(function(){
		$('.jf_szright').load(_basepath+"checkMoney/checkMoney.php");
	});
		// 获取bootStrapTable表格数据,及参数设置
		checkDetailMoney.getTab = function(PKID) {
		var url = _basepath + "checkMoney/checkMoneyDeailTable.json?PKID="+PKID;
		$('#checkMoneyDetailTable').bootstrapTable(
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
							showColumns : true, // 显示下拉框勾选要显示的列
							showRefresh : false, // 显示刷新按钮 --只是前台刷新，个人觉得没什么用
							minimumCountColumns : 2, // 最少允许的列数
							sidePagination : "server", // 表示服务端请求
							totalRows : 0, // server side need to set
							singleSelect : false,
							clickToSelect : true,
							onColumnSwitch: function(field,checked){

								var resultColumnList = $('#checkMoneyDetailTable').bootstrapTable('getVisibleColumns');
								var resultColumns = "";		
								$.each(resultColumnList, function(i, column) {
									if(column.field=='0'/*||column.field=='opt'*/){
										return;
									}else{
										resultColumns+=column.field+",";
									}									
						     	});
								$.post("stuinfo/updateShowCols.json",{table_show_cols:resultColumns,table_name:'T_HEZHANGMINGXI'},function(data){
									if(data.result=="success"){
//										layer.msg("保存成功!");
									}
								})
							},
							onDblClickRow : function(row) {
							},
							queryParams : function getParams(params) {
								//学年
								var ACADEMICYEAR = $('#school_year_sj').val();
								//缴费类型
								var PAYCOSTTYPE = $('#pay_style_sj').val();
								//选择的日期
								var checkDetailDate=$('#checkDetailDate').val();
								//支付方式
								var payModes=$('#payModes').val();
								//学生姓名
								var studentName = $('#studentName').val();
								var PKID = $("#checkMoneyPKID").val();
								//缴费类型
								var JIAOFEILEIXING = $('#JIAOFEILEIXING').val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									CHECKDETAILDATE : checkDetailDate,
									PKID:PKID,
									JIAOFEILEIXING:JIAOFEILEIXING,
									ACADEMICYEAR:ACADEMICYEAR,
									PAYCOSTTYPE:PAYCOSTTYPE,
									STUDENTNAME : studentName,
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
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'NIANJI',
										title : '入学年份',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'ACADEMICYEAR',
										title : '学年',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'ZHUANYE',
										title : '专业',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'PARENTITEM',
										title : '缴费项目',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'PAYCOSTTYPE',
										title : '缴费类型',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'ITEMNAME',
										title : '缴费子项',
										align : "left",
										halign : 'center',
										sortable : false
									},

									{
										field : 'JIAOFEILEIXING',
										title : '支付类型',
										align : "center",
										halign : 'center',
										sortable : false

									},

									{
										field : 'CASHMONEY',
										title : '现金',
										align : "right",
										halign : 'center',
										sortable : false

									},
									{
										field : 'CARDMONEY',
										title : '银行卡',
										align : "right",
										halign : 'center',
										sortable : false

									},
									{
										field : 'TTMONEY',
										title : '电汇',
										align : "right",
										halign : 'center',
										sortable : false

									},
									{
										field : 'WXMONEY',
										title : '微信',
										align : "right",
										halign : 'center',
										sortable : false

									},
									{
										field : 'ZFBMONEY',
										title : '支付宝',
										align : "right",
										halign : 'center',
										sortable : false

									},
									{
										field : 'CJSJ',
										title : '操作时间',
										align : "center",
										halign : 'center',
										sortable : false

									}],
						});
		if(colStr!=null && colStr!=''){
			var resultColumnList = $('#checkMoneyDetailTable').bootstrapTable('getVisibleColumns');

			$.each(resultColumnList, function(i, column) {
				if(column.field=='0'/*||column.field=='opt'*/){
					return;
				}
				 $('#checkMoneyDetailTable').bootstrapTable('hideColumn', column.field);
	     	});		
			var array=colStr.split(",");
			for(var i=0;i<array.length;i++){
				var field=array[i];
				if(field==''){
					continue;
				}
				$('#checkMoneyDetailTable').bootstrapTable('showColumn', field);
				
			}
			
		}
	};
	//加载表格数据
	checkDetailMoney.getTab(checkDetailMoney.checkMoneyPKID);
})(jQuery, window);
