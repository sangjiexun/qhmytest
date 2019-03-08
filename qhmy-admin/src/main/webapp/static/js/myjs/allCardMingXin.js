//@ sourceURL=stuPayDetail.js
(function($, window) {
	var stuPayDetail = {};
	
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
	$("#NIANJI").bind("change", function() {
		changeGrade();
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
		
		$("#getAllShengYuanTable").bootstrapTable('destroy'); 
		stuPayDetail.getTab();
	});
	//导出按钮点击事件
	$("#checkOut").click(function(){
		var NIANJI=$("#BIANMA").val();
		var DEPARTMENT_ID=$("#YXID").val();
		var STUINPUT=$("#STUINPUT").val();
		//选择的日期起
		var DATESTART=$('#DATESTART').val();
		//选择的日期止
		var DATEEND=$('#DATEEND').val();
		window.location.href=encodeURI(_basepath+'report/allCardDetailExcel.json?NIANJI='+NIANJI
				+"&&DEPARTMENT_ID="+DEPARTMENT_ID
				+"&&STUINPUT="+STUINPUT
				+"&&DATESTART="+DATESTART
				+"&&DATEEND="+DATEEND
				);
	});
	

	
	// 获取bootStrapTable表格数据,及参数设置
	stuPayDetail.getTab = function() {
		var PageSizeDel=20;
		var PageClassHt=$(".page-size").html();
		if(PageClassHt!=""&&PageClassHt!=null){
			PageSizeDel=PageClassHt;
		}
		var url = _basepath + "report/getCardList.json";
		$('#getAllShengYuanTable').bootstrapTable(
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
					pageSize : PageSizeDel, // 每页显示的记录数
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
						var NIANJI=$("#BIANMA").val();
						var DEPARTMENT_ID=$("#YXID").val();
						var STUINPUT=$("#STUINPUT").val();
						//选择的日期起
						var DATESTART=$('#DATESTART').val();
						//选择的日期止
						var DATEEND=$('#DATEEND').val();
						
						var temp = {
							limit : params.limit, // 页面大小
							searchText : this.searchText,
							sortName : this.sortName,
							sortOrder : this.sortOrder,
							pageindex : this.pageNumber,
							NIANJI:NIANJI,
							DEPARTMENT_ID:DEPARTMENT_ID,
							STUINPUT:STUINPUT,
							DATESTART:DATESTART,
							DATEEND:DATEEND
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
									title : '身份证',
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
								field : 'XINGMING',
								title : '姓名',
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
								field : 'NAME',
								title : '院校专业',
								align : "center",
								halign : 'center',
								sortable : false
							},
							{
								field : 'CLASS_NAME',
								title : '班级',
								align : "center",
								halign : 'center',
								sortable : false
							},{
								field : 'BDTIME',
								title : '领取时间',
								align : "center",
								halign : 'center',
								sortable : false
								
							}

					],
						});
	};
	//加载表格数据
	stuPayDetail.getTab();
})(jQuery, window);
