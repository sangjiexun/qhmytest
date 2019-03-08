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
			message : $('<div></div>').load(_basepath+'basicClass/goEdit.json?PKID='+pkid),
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

	//去新增页面
	$("#btn_add").click(function(){
		var pkid = '';
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '新增班级',
			message : $('<div></div>').load(_basepath+'basicClass/goEdit.json?PKID='+pkid),
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
		var reg=/^\s+$/g;
		//pkid
		var PKID = $("#PKID").val();
		//入学年份
		var SYS_DICTIONARIES_PKID = $("#grade_add").val();
		//班型
		var BANXING_PKID = $("#banxing_add").val();
		//班容量
		var BANRONGLIANG = $("#BANRONGLIANG").val();
		//验证班容量不能为空
		if(BANRONGLIANG==''||BANRONGLIANG==null||reg.test(BANRONGLIANG)){
			layer.msg("班容量不能为空");
			$("#BANRONGLIANG").focus();
			return false;
		}
		//验证班容量只能输入数字
		var regs = /^\d+$/;
		if(!regs.test(BANRONGLIANG)){
			layer.msg("班容量只能输入数字");
			$("#BANRONGLIANG").focus();
			return false;
		}
		//班级名称
		var CLASS_NAME = $("#CLASS_NAME").val();
		//验证班级名称不能为空
		if(CLASS_NAME==''||CLASS_NAME==null||reg.test(CLASS_NAME)){
			layer.msg("班级名称不能为空");
			$("#CLASS_NAME").focus();
			return false;
		}
		var IS_USE = $("#IS_USE").is(':checked');//是否启用
		if(IS_USE){
			IS_USE = '1';
		}else{
			IS_USE = '0';
		}
		var SYS_DICTIONARIES_PKID_HIDDEN = $("#SYS_DICTIONARIES_PKID_HIDDEN").val();
		var BANXING_PKID_HIDDEN = $("#BANXING_PKID_HIDDEN").val();
		var CLASS_NAME_HIDDEN = $("#CLASS_NAME_HIDDEN").val();
		if(flag){
			
			$.ajax({
				type : "post",
				dataType : "json",
				async: false,
				url : _basepath+ 'basicClass/edit.json',
				data : {"PKID":PKID,"CLASS_NAME":CLASS_NAME,"SYS_DICTIONARIES_PKID":SYS_DICTIONARIES_PKID,
					"BANXING_PKID":BANXING_PKID,"SYS_DICTIONARIES_PKID_HIDDEN":SYS_DICTIONARIES_PKID_HIDDEN,
					"BANXING_PKID_HIDDEN":BANXING_PKID_HIDDEN,"CLASS_NAME_HIDDEN":CLASS_NAME_HIDDEN,
					"BANRONGLIANG":BANRONGLIANG,"IS_USE":IS_USE},
				success : function(data) {
					if ("success" == data.rst) {
						dialogRef.close();
						layer.msg("操作成功！");
						$("#classTable").bootstrapTable('destroy');
						classmanage.getTab();
					}
					if("isExist" == data.rst){
						layer.msg("已存在相同入学年份和班型的班级！");
					}
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					alert(errorThrown);
				}
			});
		}
	};

	

	
	// 获取bootStrapTable表格数据,及参数设置
	classmanage.getTab = function() {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		var url = _basepath + "basicClass/basicClassTable.json";
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
								var banxing = $('#banxing').val();
								var grade = $("#grade").val();
								var className = $("#className").val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									BANXING_PKID : banxing,
									SYS_DICTIONARIES_PKID:grade,
									CLASS_NAME:className,
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
										field : 'GRADE',
										title : '入学年份',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'BANXING',
										title : '班型',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'CLASS_NAME',
										title : '班级名称',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'BANRONGLIANG',
										title : '班级容量',
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
												var isUse = e.currentTarget.checked==true?true:false;//$(this).prop("checked");
												var IS_USE = 0;
												if(isUse){
													IS_USE = 1;
												}
												var url = _basepath + "basicClass/updateIsUse.json";
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
																url:_basepath+'basicClass/delete.json?PKID='+row.PKID,
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
