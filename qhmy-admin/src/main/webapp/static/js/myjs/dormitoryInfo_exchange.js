//@ sourceURL=dormitoryInfo_exchange.js 
/**
 * 床位对调页面
 */
(function($, window) {
	
	$('.duidiaochaxun').keydown(function(e){if(e.keyCode==13){
	    $('.chaxunbtn').triggerHandler('click');
	}
	});
	
	
	var exchangeBed = {};
	
	//获取选中的行
	exchangeBed.getIdSelections=function () {
			var $table = $('#exchangetable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.PKID;
        });
	};



	
	//查询
	$('#btn_search2').click(function(){
		//所属组织
		$("#exchangetable").bootstrapTable('destroy'); 
		 exchangeBed.getTab();
		
	});
	
	
	//批量删除按钮绑定单机事件
	//获取list表格数据
	exchangeBed.getTab=function () {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		// 引例四
		var url = _basepath+"dormitoryInfo/getduidiaodormtable.json";

		$('#exchangetable').bootstrapTable(
						{
							method: 'post',  
							url : url,//数据源
							dataType: "json",
							contentType:"application/x-www-form-urlencoded; charset=UTF-8",
							dataField : "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
							totalField : 'total',
							sortOrder : 'desc',
							striped : true, //表格显示条纹  
							pagination : true, //启动分页  
							pageNumber : 1, //当前第几页  
							pageSize : 5, //每页显示的记录数  
							pageList : [1,5,10,20,30,50,100], //记录数可选列表  
							search : false, //是否启用查询  
							formatSearch : function() {
								return '请输入简拼.编码.企业名称查询';
							},//搜索框提示文字
							searchAlign : 'left',
							buttonsAlign : 'left',
							toolbarAlign : 'left',
							searchOnEnterKey : false,//true为按回车触发搜索事件
							showColumns : false, //显示下拉框勾选要显示的列  
							showRefresh : false, //显示刷新按钮  --只是前台刷新，个人觉得没什么用
							minimumCountColumns : 2, //最少允许的列数
							sidePagination : "server", //表示服务端请求  
							totalRows : 0, // server side need to set
							singleSelect : false,
							clickToSelect : true,
							onColumnSwitch: function(field,checked){
								console.log(field+"::"+checked);
							},
							onDblClickRow:function(row){
							},
							onLoadSuccess:function(){
					            $('.bootstrap-table tr td').each(function () {
					                $(this).attr("title", $(this).text());
					                $(this).css("cursor", 'pointer');
					            });
					        },
							queryParams : function getParams(params) {
								var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, //页面大小
									dorm_pkid:$("#dorm_select").val(),//宿舍下拉列表
									dorm_type:$("#dorm_type_pkid").val(),//宿舍类别下拉列表
									ruxuenianfen:$("#ruxuenianfen").val(),//入学年份
									department_pkid:$('#orgtree').val(),//院校专业
									xingbie:$('#xingbie').val(),//性别
//									zhuangtai:$('#zhuangtai').val(),//使用状态
									seText : $('#search2').val(),//搜索框
									sortName : this.sortName,
									sortOrder : this.sortOrder,
									pageindex : this.pageNumber,
									pkid:$("#pkid").val(),
									tiaosu:$("#tiaosu").val(),
									dorm_pkid2:$("#dorm_select2").val(),//宿舍下拉列表
									whkxx:$("#WHKXUEXIAOPKID").val(),
									banx:$("#BANXING").val(),
									sd_sex:$("#sd_sex").val()
								//当前页码
								};
								return temp;
							},
							 queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求  
							  silent: true,  //刷新事件必须设置  
							buttonsAlign : "right",//按钮对齐方式
							selectItemName : 'id',
							toolbar : "#toolbar",
							toolbarAlign : 'left',
							columns : [

									{
										title : "全选",//标题
										align : "center",//水平
										radio : true,
										valign : "middle"//垂直

									},
									{
										field: 'PKID',//可不加  
										align : "center",
										halign : 'center'
									},
									{
										field : 'ROOM_NAME',
										title : '宿舍',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'SD_SEX',
										title : '性别',
										align : "center",
										halign : 'center',
										formatter : function(value, row,
												index) {
											if(row.SD_SEX == '1'){
												return "男";
											}
											if(row.SD_SEX == '0'){
												return "女";
											}
										},
										sortable : false
									},
									{
										field : 'BANJI',
										title : '班级',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'WHKXUEXIAO',
										title : '文化课学校',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'DORM_TYPE_NAME',
										title : '宿舍类型',
										align : "center",
										halign : 'center',
										sortable : false
									},
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
										title:'姓名',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'T_STUDENT_DORM_TYPE_PKID',
										title : '宿舍类型pkid',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'BMPKID',
										title : 'pkid',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'T_STUDENT_BM_PKID',
										title : 'pkid',
										align : "center",
										halign : 'center',
										sortable : false
									}
							],
						});
	    $('#exchangetable').bootstrapTable('hideColumn', 'PKID');//隱藏列
	    $('#exchangetable').bootstrapTable('hideColumn', 'T_STUDENT_DORM_TYPE_PKID');//隱藏列
	    $('#exchangetable').bootstrapTable('hideColumn', 'BMPKID');//隱藏列
	    $('#exchangetable').bootstrapTable('hideColumn', 'T_STUDENT_BM_PKID');
	};
	//加载表格数据
	exchangeBed.getTab();
	//更多查询条件
})(jQuery, window);
