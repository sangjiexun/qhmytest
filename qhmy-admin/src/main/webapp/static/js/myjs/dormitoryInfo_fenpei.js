//@ sourceURL=dormitoryInfo_fenpei.js 
/**
 * 床位对调页面
 */
(function($, window) {
	
	
	
	
	$('#search2').keydown(function(e){if(e.keyCode==13){
	    $('#btn_search2').triggerHandler('click');
	}
	});
	/*$(input2).keydown(function(e){if(e.keyCode==13){
	    //TODO 执行 button2的操作
	}*/
	
	/* $(document).keydown(function (event) { //回车事件
		    if (event.keyCode == 13) {
		       $('#btn_search2').triggerHandler('click');
		    }
		 });*/
	
	var fepei = {};
	
	//获取选中的行
	fepei.getIdSelections=function () {
			var $table = $('#fenpeitable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.PKID;
        });
	};



	
	//查询
	$('#btn_search2').click(function(){
		//所属组织
		$("#fenpeitable").bootstrapTable('destroy'); 
		 fepei.getTab();
		
	});
	
	
	//批量删除按钮绑定单机事件
	//获取list表格数据
	fepei.getTab=function () {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		// 引例四
		var url = _basepath+"dormitoryInfo/getfenpeistutable.json";

		$('#fenpeitable').bootstrapTable(
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
									seText : $('#search2').val(),//搜索框
									sortName : this.sortName,
									sortOrder : this.sortOrder,
									pageindex : this.pageNumber,
									sd_sex:$("#sd_sex").val(),
									pkid:$("#pkid").val(),
									WHKXUEXIAO:$("#WHKXUEXIAO").val(),
									BANXING:$("#BANXING").val(),
									dept_id:$("#dept_id").val(),
									t_student_dorm_type_pkid : $("#t_student_dorm_type_pkid").val()
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
										field : 'XINGMING',
										title:'姓名',
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
										field : 'SHENFENZHENGHAO',
										title : '身份证号码',
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
										field : 'HZXX',
										title : '文化课学校',
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
									},
									{
										field : 'RXNF',
										title : '入学年份',
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
									}
									,{
										field : 'ROOM_NAME',
										title : '宿舍',
										align : "center",
										halign : 'center',
										sortable : false
									}
									,{
										field : 'RXNIANFEN_PKID',
										title : '入学年份',
										align : "center",
										halign : 'center',
										sortable : false
									},{
										field : 'BMPKID',
										title : '入学年份',
										align : "center",
										halign : 'center',
										sortable : false
									}
							],
						});
	    $('#fenpeitable').bootstrapTable('hideColumn', 'PKID');//隱藏列
	    $('#fenpeitable').bootstrapTable('hideColumn', 'RXNIANFEN_PKID');//隱藏列
	    $('#fenpeitable').bootstrapTable('hideColumn', 'BMPKID');//隱藏列
	    
	};
	//加载表格数据
	fepei.getTab();
	//更多查询条件
})(jQuery, window);
