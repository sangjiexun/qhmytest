/**
 * 民族对象
 */
//@ sourceURL=nation.js
(function($, window) {
	var nation = {};
	
	//查询按钮点击事件
	$('#nationQuery').click(function(){
		$("#nationTable").bootstrapTable('destroy');
		nation.getTab();
	});
	
	//导入民族
	$("#btn_import_nation").click(function(){
		var dialog = BootstrapDialog.show({
			title:'导入民族',
			message: $('<div></div>').load("importNation/importNation.json"),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
	            label: '关闭',
	            cssClass: 'btn-danger',
	            action: function(dialog) {
		    		dialog.close();
		    		$("#nationTable").bootstrapTable('destroy');
		    		nation.getTab();
	            }
	        }]
		});
	});
	
	//去编辑页面
	nation.goUpdate = function(pkid){
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '编辑民族',
			message : $('<div></div>').load(_basepath+'nation/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							nation.updateAdd(dialogRef);
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
			title : '新增民族',
			message : $('<div></div>').load(_basepath+'nation/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							nation.updateAdd(dialogRef);
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
	nation.updateAdd = function(dialogRef){
		var PKID = $("#PKID").val();
		var NATION_BIANMA = $("#NATION_BIANMA").val();
		var NATION_NAME = $("#NATION_NAME").val();
		var IS_USED = $("#IS_USED").is(':checked');//是否启用
		if(IS_USED){
			IS_USED = 'Y';
		}else{
			IS_USED = 'N';
		}
		var BEIZHU = $("#BEIZHU").val();
		var flag = nation.formcheck();
		if(flag){
			dialogRef.close();
			$.ajax({
				type : "post",
				dataType : "json",
				async: false,
				url : _basepath+ 'nation/edit.json',
				data : {"PKID":PKID,"NATION_BIANMA":NATION_BIANMA,"NAME":NATION_NAME,"IS_USED":IS_USED,
					"BEIZHU":BEIZHU},
				success : function(data) {
					if ("success" == data.rst) {
						layer.msg("操作成功！");
						$("#nationTable").bootstrapTable('destroy');
						nation.getTab();
					}
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					alert(errorThrown);
				}
			});
		}
	}
	//验证民族名称是否唯一
	nation.validate = function(NATION_NAME,PKID){
		var flag = true;
		$.ajax({
			type : "post",
			dataType : "json",
			async: false,
			url : _basepath+ 'nation/getnation_NAME.json',
			data : {"PKID":PKID,"NAME":NATION_NAME},
			success : function(data) {
				if ("success" == data.rst) {
					layer.msg("该民族名称已存在，请更换！");
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
	nation.formcheck = function(){
		var PKID = $("#PKID").val();
		var NATION_NAME = $("#NATION_NAME").val();
		if(NATION_NAME.trim() == ''){
			layer.msg("民族名称不能为空");
			$("#NATION_NAME").focus();
			return false;
		}
		if(NATION_NAME!=''){
			var flag = nation.validate(NATION_NAME,PKID);
			if(!flag){
				$("#NATION_NAME").focus();
				return false;
			}
		}
		return true;
	}
	
	// 获取bootStrapTable表格数据,及参数设置
	nation.getTab = function() {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		var url = _basepath + "nation/nationTable.json";
		$('#nationTable').bootstrapTable(
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
								//民族身份证或姓名
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
										title : '民族编号',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'NAME',
										title : '民族名称',
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
												        '<input type="checkbox" checked="checked" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.mzgl_qy == 1?'':'disabled="disabled"')+'>']
												.join('');
											}else{
												return [
												        '<input type="checkbox" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.mzgl_qy == 1?'':'disabled="disabled"')+'>']
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
												var url = _basepath + "nation/updateIsUse.json";
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
											return [
													SESSION_MENU_BUTTONS.mzgl_xg == 1?'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;':'',
															SESSION_MENU_BUTTONS.mzgl_sc == 1?'<a class="cancel ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>':'' ]
													.join('');
										}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										 events : {
											'click .edit' : function(e,value, row, index) {
												  //编辑  编辑人员
												nation.goUpdate(row.DICTIONARIES_ID);
	    							        
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
																url:_basepath+'nation/delete.json?PKID='+row.DICTIONARIES_ID,
																 success:function(data){
																	 if("success" == data.rst){
																		 layer.msg("删除成功！");
																		 $("#nationTable").bootstrapTable('destroy');
																		 nation.getTab();
																	 }
																	 if("error" == data.rst){
																		 layer.msg("该民族信息已被使用，无法删除!");
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
	nation.getTab();
})(jQuery, window);
