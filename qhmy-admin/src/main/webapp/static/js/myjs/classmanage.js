/**
 * 班级对象
 */
//@ sourceURL=classmanage.js
(function($, window) {
	var classmanage = {};
	
	//查询按钮点击事件
	$('#classQuery').click(function(){
		$("#classTable").bootstrapTable('destroy');
		classmanage.getTab();
	});
	
	//去编辑页面
	classmanage.goUpdate = function(pkid){
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '编辑班级',
			message : $('<div></div>').load(_basepath+'class/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							classmanage.updateAdd(dialogRef);
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
	//去导入页面
	$("#btn_import").click(function(){
		var dialog = BootstrapDialog.show({
			title:'导入班级信息',
			message: $('<div></div>').load("importclass/importClassInfo.json"),
			draggable: true,//可以拖拽
			closable: true,
			buttons: [{
	            label: '关闭',
	            cssClass: 'btn-danger',
	            action: function(dialog) {
	            	dialog.close();
	            	$('#classTable').bootstrapTable("refresh");
	            }
	        }]
		});
	});
	//去新增页面
	$("#btn_add").click(function(){
		var pkid = '';
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '新增班级',
			message : $('<div></div>').load(_basepath+'class/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							classmanage.updateAdd(dialogRef);
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
	classmanage.updateAdd = function(dialogRef){
		var PKID = $("#PKID").val();
		var CLASS_NAME = $("#CLASS_NAME").val();
		var grade = $("#grade_add").val();
		var orgtree = $("#orgtree").val();
		var SYS_USER_PKID = $("#SYS_USER_PKID").val();
		var IS_USE = $("#IS_USE").is(':checked');//是否启用
		if(IS_USE){
			IS_USE = '1';
		}else{
			IS_USE = '0';
		}
		var REMARKS = $("#REMARKS").val();
		var flag = classmanage.formcheck();
		if(flag){
			dialogRef.close();
			$.ajax({
				type : "post",
				dataType : "json",
				async: false,
				url : _basepath+ 'class/edit.json',
				data : {"PKID":PKID,"CLASS_NAME":CLASS_NAME,"grade":grade,"orgtree":orgtree,"SYS_USER_PKID":SYS_USER_PKID,"IS_USE":IS_USE,
					"REMARKS":REMARKS},
				success : function(data) {
					if ("success" == data.rst) {
						layer.msg("操作成功！");
						$("#classTable").bootstrapTable('destroy');
						classmanage.getTab();
					}
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					alert(errorThrown);
				}
			});
		}
	}
	//验证班级名称在该院校专业和入学年份下是否唯一
	classmanage.validate = function(PKID,orgtree,grade,CLASS_NAME){
		var flag = true;
		$.ajax({
			type : "post",
			dataType : "json",
			async: false,
			url : _basepath+ 'class/getClassName.json',
			data : {"PKID":PKID,"orgtree":orgtree,"grade":grade,"CLASS_NAME":CLASS_NAME},
			success : function(data) {
				if ("success" == data.rst) {
					layer.msg("该班级名称在所选院校专业和入学年份条件下已存在，请更换！");
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
	classmanage.formcheck = function(){
		var CLASS_NAME = $("#CLASS_NAME").val();
		var orgtree = $("#orgtree").val();//院校专业
		var SYS_USER_PKID = $("#SYS_USER_PKID").val();
		var grade = $("#grade_add").val();//入学年份
		if(CLASS_NAME.trim()==''){
			layer.msg("班级名称不能为空");
			$("#CLASS_NAME").focus();
			return false;
		}else if(orgtree == ''){
			layer.msg("院校专业不能为空");
			$("#citySel").focus();
			return false;
		}
		/*
		 * 判断在同一院校专业和入学年份下，班级名称有没有重复
		 */
		var PKID = $("#PKID").val();
		var flag = classmanage.validate(PKID,orgtree,grade,CLASS_NAME);
		if(!flag){
			return false;
		}
		if(SYS_USER_PKID==''){
			layer.msg("班主任不能为空");
			$("#SYS_USER_PKID").focus();
			return false;
		}else{
			return true;
		}
	}
	
	// 获取bootStrapTable表格数据,及参数设置
	classmanage.getTab = function() {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		var url = _basepath + "class/classTable.json";
		$('#classTable').bootstrapTable(
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
								return '';
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
								//班级名称
								var keyWord = $('#keyWord').val();
								var dictionaries_id = $("#grade").val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									keyWord : keyWord,
									dictionaries_id:dictionaries_id,
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
										field : 'CLASS_BIANMA',
										title : '班级编号',
										align : "center",
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
										field : 'NIANJI',
										title : '入学年份',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'DEPARTMENT_NAME',
										title : '院校专业',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'USERNAME',
										title : '班主任',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'IS_USE',
										title : '是否启用',
										align : "center",
										halign : 'center',
										formatter : function(value, row, index) {
											if(row.IS_USE=='1'){
												return [
												        '<input type="checkbox" checked="checked" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.class_qy == 1?'':'disabled="disabled"')+'>']
												.join('');
											}else{
												return [
												        '<input type="checkbox" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.class_qy == 1?'':'disabled="disabled"')+'>']
												.join('');
											}
										}, // 紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										events : {
											'click .al-toggle-button' : function(e,value, row, index) {
												//启用更改事件
												var isUse = $(this).prop("checked");
												var IS_USE = 0;
												if(isUse){
													IS_USE = 1;
												}
												var url = _basepath + "class/updateIsUse.json";
												$.ajax({
													type:"post",
													dataType:"json",
													data:{PKID:row.PKID,IS_USE:IS_USE},
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
										field : 'REMARKS',
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
											return [
													SESSION_MENU_BUTTONS.class_xg == 1?'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;':'',
															SESSION_MENU_BUTTONS.class_sc == 1?'<a class="cancel ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>':'' ]
													.join('');
										}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										 events : {
											'click .edit' : function(e,value, row, index) {
												  //编辑  编辑人员
												classmanage.goUpdate(row.PKID);
	    							        
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
																url:_basepath+'class/delete.json?PKID='+row.PKID,
																 success:function(data){
																	 if("success" == data.rst){
																		 layer.msg("删除成功！");
																		 $("#classTable").bootstrapTable('destroy');
																		 classmanage.getTab();
																	 }
																	 if("error" == data.rst){
																		 layer.msg("该班级信息已被使用，无法删除!");
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
	classmanage.getTab();
})(jQuery, window);
