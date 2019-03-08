/**
 * 个人欠费明细对象
 */
//@ sourceURL=stuArrearageDetail.js
//(function($, window) {
	var stuArrearageSum = {};
	
	$("#NIANJI").bind("change", function() {
		changeGrade();
	});
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		$("#stuArrearageSumTable").bootstrapTable('destroy'); 
		stuArrearageSum.getTab();
		stuArrearageSum.sums();
	});
	//汇总
	stuArrearageSum.sums = function(){
		//收费学年
		var shoufeixuenian = $('#shoufeixuenian').val();
		//收费项目
		var payitem = $('#payitem').val();
		//选择的入学年份
		var NIANJI=$('#NIANJI').val();
		//院校专业pkids
		var orgtree=$('#orgtree').val();
		//班级
		var banji = $('#banji').val();
		//关键词
		var keywords = $("#keywords").val();
		//学生id
		var STUDENT_PKID = $("#STUDENT_PKID").val();
		//班级
		var banji = $("#BANJI").val();
		$.ajax({
			  type: 'POST',
			  url: _basepath+"report/stuArrearageDetail.json",
			  async: false,
			  data: {shoufeixuenian:shoufeixuenian,payitem:payitem,NIANJI:NIANJI,orgtree:orgtree,banji:banji,keywords:keywords,STUDENT_PKID:STUDENT_PKID},
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  $("#QIANFEIRENSHU").html("欠费人数：<a>"+(result.details == 'null' || result.details == null ?'0':result.details.QIANFEIRENSHU)+"</a>");
					  $("#ZONGYINGSHOU").html("总应收：<a>"+(result.details == 'null' || result.details == null ?'0':result.details.ZONGYINGSHOU).toFixed(2)+"</a>");
					  $("#ZONGSHISHOU").html("总实收：<a>"+(result.details == 'null' || result.details == null ?'0':result.details.ZONGSHISHOU).toFixed(2)+"</a>");
					  $("#ZONGQIANFEI").html("欠费：<a>"+(result.details == 'null' || result.details == null ?'0':result.details.ZONGQIANFEI).toFixed(2)+"</a>");
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
		//关键词
		var keywords = $("#keywords").val();
		var shoufeixuenian = $('#shoufeixuenian').val();
		var payitem = $('#payitem').val();
		var STUDENT_PKID = $("#STUDENT_PKID").val();
		window.location.href=encodeURI(_basepath+'report/stuArrearageDetailExcel.json?NIANJI='+NIANJI
				+"&&orgtree="+orgtree+"&&BANJI="+banji+"&&keywords="+keywords+"&shoufeixuenian="
				+shoufeixuenian+"&payitem="+payitem+"&STUDENT_PKID="+STUDENT_PKID
				);
	});
	
	//返回按钮点击事件
	$("#back_tohuizong").click(function(){
		$(".jf_szright").load(_basepath+ $("#backUrl").val());
	});
	$("#back_tobanjihuizong").click(function(){
		$(".jf_szright").load(_basepath+ "report/toBanJiArrearageSum.php");
	});
	
	// 获取bootStrapTable表格数据,及参数设置
	stuArrearageSum.getTab = function() {
		//获得按钮权限
		var url = _basepath + "report/stuArrearageDeatilTable.json";
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
								if(banji==null || banji==''){
									banji=banji2;
								}

								//关键词
								var keywords = $("#keywords").val();
								var shoufeixuenian = $('#shoufeixuenian').val();
								var payitem = $('#payitem').val();
								var STUDENT_PKID = $("#STUDENT_PKID").val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									keywords : keywords,
									NIANJI : NIANJI,
									orgtree : orgtree,
									BANJI : banji,
									STUDENT_PKID:STUDENT_PKID,
									shoufeixuenian:shoufeixuenian,
									payitem:payitem,
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
										field : 'XUEHAO',
										title : '学号',
										align : "center",
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
										field : 'DEPARTMENTNAME',
										title : '院校专业',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'CLASS_NAME',
										title : '班级名称',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'SCHOOL_YEAR_NAME',
										title : '收费学年',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'FUXIANGMU',
										title : '缴费项目',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'ZIXIANGMU',
										title : '子项目',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'TOTALMONEY',
										title : '费用',
										align : "right",
										halign : 'center',
										sortable : false
									},

									{
										field : 'TOTAL_DISTINCT',
										title : '优惠',
										align : "right",
										halign : 'center',
										sortable : false,
										

									},
									
									{
										field : 'LOAN_MONE',
										title:'贷款/缓交',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter : function(value, row,
												index) {
											var LOAN_MONE = row.LOAN_MONE != null?row.LOAN_MONE.toFixed(2):0;
											return LOAN_MONE
										}
									},
									{
										field : 'TOTALAMOUNTRECEIVABLE',
										title : '应收金额',
										align : "right",
										halign : 'center',
										sortable : false

									},
									{
										field : 'TOTALAMOUNTCOLLECTION',
										title : '实收金额',
										align : "right",
										halign : 'center',
										sortable : false

									},
									{
										field : 'QIANFEIHEJI',
										title : '欠费金额',
										align : "right",
										halign : 'center',
										sortable : false,
										formatter : function(value, row,
												index) {
											return (row.TOTALAMOUNTRECEIVABLE-row.TOTALAMOUNTCOLLECTION)
										}
									}
									],
						});
	};
	//加载表格数据
	stuArrearageSum.getTab();
	stuArrearageSum.sums();
//})(jQuery, window);
