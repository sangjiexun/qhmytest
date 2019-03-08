/**
 * 在学状态对象
 */
//@ sourceURL=zxzt.js
(function($, window) {
	var zxzt = {};
	
	//查询按钮点击事件
	$('#zxztQuery').click(function(){
		$("#zxztTable").bootstrapTable('destroy');
		zxzt.getTab();
	});
	
	//去编辑页面
	zxzt.goUpdate = function(pkid){
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '编辑在学状态',
			message : $('<div></div>').load(_basepath+'zxzt/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							zxzt.updateAdd(dialogRef);
						}
					}, {
						label : '取消',
						cssClass : 'btn-default',
						action : function(dialogRef) {
							dialogRef.close();
						}
					} ]
		});
	}
	
	//去新增页面
	$("#btn_add").click(function(){
		var pkid = '';
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '新增在学状态',
			message : $('<div></div>').load(_basepath+'zxzt/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							zxzt.updateAdd(dialogRef);
						}
					}, {
						label : '取消',
						cssClass : 'btn-default',
						action : function(dialogRef) {
							dialogRef.close();
						}
					} ]
		});
	});
	
	//编辑方法
	zxzt.updateAdd = function(dialogRef){
		var PKID = $("#PKID").val();
		var ZXZT_BIANMA = $("#ZXZT_BIANMA").val();
		var ZXZT_NAME = $("#ZXZT_NAME").val();
		var IS_USED = $("#IS_USED").is(':checked');//是否启用
		if(IS_USED){
			IS_USED = 'Y';
		}else{
			IS_USED = 'N';
		}
		var BEIZHU = $("#BEIZHU").val();
		var flag = zxzt.formcheck();
		if(flag){
			dialogRef.close();
			$.ajax({
				type : "post",
				dataType : "json",
				async: false,
				url : _basepath+ 'zxzt/edit.json',
				data : {"PKID":PKID,"ZXZT_BIANMA":ZXZT_BIANMA,"NAME":ZXZT_NAME,"IS_USED":IS_USED,
					"BEIZHU":BEIZHU},
				success : function(data) {
					if ("success" == data.rst) {
						layer.msg("操作成功！");
						$("#zxztTable").bootstrapTable('destroy');
						zxzt.getTab();
					}
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					alert(errorThrown);
				}
			});
		}
	}
	//验证在学状态名称是否唯一
	zxzt.validate = function(ZXZT_NAME,PKID){
		var flag = true;
		$.ajax({
			type : "post",
			dataType : "json",
			async: false,
			url : _basepath+ 'zxzt/getzxzt_NAME.json',
			data : {"PKID":PKID,"NAME":ZXZT_NAME},
			success : function(data) {
				if ("success" == data.rst) {
					layer.msg("该在学状态名称已存在，请更换！");
					flag = false;
				}
			},
			error : function(XMLHttpRequest, textStatus,
					errorThrown) {
				alert(errorThrown);
				flag = false;
			}
		});
		return flag;
	}
	
	//页面校验
	zxzt.formcheck = function(){
		var PKID = $("#PKID").val();
		var ZXZT_NAME = $("#ZXZT_NAME").val();
		if(ZXZT_NAME.trim() == ''){
			layer.msg("在学状态名称不能为空");
			$("#ZXZT_NAME").focus();
			return false;
		}
		if(ZXZT_NAME!=''){
			var flag = zxzt.validate(ZXZT_NAME,PKID);
			if(!flag){
				$("#ZXZT_NAME").focus();
				return false;
			}
		}
		return true;
	}
	
	// 获取bootStrapTable表格数据,及参数设置
	zxzt.getTab = function() {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		var url = _basepath + "zxzt/zxztTable.json";
		$('#zxztTable').bootstrapTable(
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
								//在学状态身份证或姓名
								var keyWord = $('#keyWord').val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									keyWord : keyWord,
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
										field : 'BIANMA',
										title : '在学状态编号',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'NAME',
										title : '在学状态名称',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'IS_USED',
										title : '是否启用',
										align : "center",
										halign : 'center',
										formatter : function(value, row, index) {
											if(row.IS_USED=='Y'){
												return [
												        '<input type="checkbox" checked="checked" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.zxzt_qy == 1?'':'disabled="disabled"')+'>']
												.join('');
											}else{
												return [
												        '<input type="checkbox" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.zxzt_qy == 1?'':'disabled="disabled"')+'>']
												.join('');
											}
										}, // 紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										events : {
											'click .al-toggle-button' : function(e,value, row, index) {
												//启用更改事件
												var isUse = $(this).prop("checked");
												var IS_USED = 'N';
												if(isUse){
													IS_USED = 'Y';
												}
												var url = _basepath + "zxzt/updateIsUse.json";
												$.ajax({
													type:"post",
													dataType:"json",
													data:{PKID:row.DICTIONARIES_ID,IS_USED:IS_USED},
													url:url,
													 success:function(data){
														 if(data.rst=="success"){
															 	layer.msg("修改成功！");
															}
													  },
													error: function (XMLHttpRequest, textStatus, errorThrown) { 
													          alert(errorThrown); 
													}
												})
											}
										}

									},
									{
										field : 'BZ',
										title : '备注',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'opt',
										title : '操作',
										align : "center",
										halign : 'center',
										formatter : function(value, row,
												index) {
											if(!isNaN(row.BIANMA)){
												return [
												        SESSION_MENU_BUTTONS.zxzt_xg == 1?'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;':'',
												        		SESSION_MENU_BUTTONS.zxzt_sc == 1?'<a class="cancel ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>':'' ]
												.join('');
											}
										}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										 events : {
											'click .edit' : function(e,value, row, index) {
												  //编辑  编辑人员
												zxzt.goUpdate(row.DICTIONARIES_ID);
	    							        
											},
											'click .cancel' : function(e,value, row, index) {
												BootstrapDialog.show({  //显示需要提交的表单。
									            	title:'提示信息',	
										            message: '你确定要删除这条记录吗？',
										            closable: true, 
									              buttons: [{
												    label: '确定',
												    cssClass: 'btn-danger',
												    action: function(dialogRef){
												    	 $.ajax({
																type:"post",
																dataType:"json",
																url:_basepath+'zxzt/delete.json?PKID='+row.BIANMA,
																 success:function(data){
																	 if("success" == data.rst){
																		 layer.msg("删除成功！");
																		 $("#zxztTable").bootstrapTable('destroy');
																		 zxzt.getTab();
																	 }
																	 if("error" == data.rst){
																		 layer.msg("该在学状态信息已被使用，无法删除!");
																	 }
																  },
																error: function (XMLHttpRequest, textStatus, errorThrown) { 
																          alert(errorThrown);
																       }
																});
												    	
												    	
									                dialogRef.close();
									                }
											  }, {
											    label: '取消',
											    cssClass: 'btn-default',
											    action: function(dialogRef){
											       dialogRef.close();
											    }
											  }
											  ]
									            });
												
												
											    
				 
											},
												} 
									} ],
						});
	};
	//加载表格数据
	zxzt.getTab();
})(jQuery, window);
