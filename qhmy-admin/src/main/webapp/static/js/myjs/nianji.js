/**
 * 入学年份对象
 */
//@ sourceURL=nianji.js
(function($, window) {
	var nianji = {};
	
	//查询按钮点击事件
	$('#nianjiQuery').click(function(){
		$("#nianjiTable").bootstrapTable('destroy');
		nianji.getTab();
	});
	
	//去编辑页面
	nianji.goUpdate = function(pkid){
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '编辑入学年份',
			message : $('<div></div>').load(_basepath+'nianji/goEdit.json?DICTIONARIES_ID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							nianji.updateAdd(dialogRef);
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
			title : '新增入学年份',
			message : $('<div></div>').load(_basepath+'nianji/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							nianji.updateAdd(dialogRef);
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
	nianji.updateAdd = function(dialogRef){
		var DICTIONARIES_ID = $("#DICTIONARIES_ID").val();
		var BIANMA = $("#BIANMA").val();
		var NAME = $("#NAME").val();
		var IS_USED = $("#IS_USED").is(':checked');//是否启用
		var IS_YX = $("#IS_YX").is(':checked');//是否迎新管理
		if(IS_USED){
			IS_USED = 'Y';
		}else{
			IS_USED = 'N';
		}
		if(IS_YX){
			IS_YX = 'Y';
		}else{
			IS_YX = 'N';
		}
		var BZ = $("#BEIZHU").val();
		var flag = nianji.formcheck();
		if(flag){
			dialogRef.close();
			$.ajax({
				type : "post",
				dataType : "json",
				async: false,
				url : _basepath+ 'nianji/edit.json',
				data : {"DICTIONARIES_ID":DICTIONARIES_ID,"BIANMA":BIANMA,"NAME":NAME,"IS_USED":IS_USED,
					"BEIZHU":BZ,"IS_YX":IS_YX},
				success : function(data) {
					if ("success" == data.rst) {
						layer.msg("操作成功！");
						$("#nianjiTable").bootstrapTable('destroy');
						nianji.getTab();
					}
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					alert(errorThrown);
				}
			});
		}
	}
	//验证名称是否唯一
	nianji.validate = function(NAME,DICTIONARIES_ID){
		var flag = true;
		$.ajax({
			type : "post",
			dataType : "json",
			async: false,
			url : _basepath+ 'nianji/getNianJi_NAME.json',
			data : {"DICTIONARIES_ID":DICTIONARIES_ID,"NAME":NAME},
			success : function(data) {
				if ("success" == data.rst) {
					layer.msg("该入学年份名称已存在，请更换！");
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
	nianji.formcheck = function(){
		var DICTIONARIES_ID = $("#DICTIONARIES_ID").val();
		var NAME = $("#NAME").val();
		if(NAME.trim() == ''){
			layer.msg("入学年份名称不能为空");
			$("#NAME").focus();
			return false;
		}
		if(NAME!=''){
			var flag = nianji.validate(NAME,DICTIONARIES_ID);
			if(!flag){
				$("#NAME").focus();
				return false;
			}
		}
		return true;
	}
	
	// 获取bootStrapTable表格数据,及参数设置
	nianji.getTab = function() {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		var url = _basepath + "nianji/nianjiTable.json";
		$('#nianjiTable').bootstrapTable(
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
								//入学年份身份证或姓名
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
										title : '入学年份编号',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'NAME',
										title : '入学年份名称',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'IS_USE',
										title : '是否启用',
										align : "center",
										halign : 'center',
										formatter : function(value, row, index) {
											if(row.IS_USED=='Y'){
												return [
												        '<input type="checkbox" checked="checked" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.nianji_qy == 1?'':'disabled="disabled"')+'>']
												.join('');
											}else{
												return [
												        '<input type="checkbox" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.nianji_qy == 1?'':'disabled="disabled"')+'>']
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
												var url = _basepath + "nianji/updateIsUse.json";
												$.ajax({
													type:"post",
													dataType:"json",
													data:{DICTIONARIES_ID:row.DICTIONARIES_ID,IS_USED:IS_USED},
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
										field : 'IS_YX',
										title : '是否迎新管理',
										align : "center",
										halign : 'center',
										formatter : function(value, row, index) {
											if(row.IS_YX=='Y'){
												return [
												        '<input type="checkbox" checked="checked" class="al-toggle-button is_yx" '+(SESSION_MENU_BUTTONS.nianji_yx == 1?'':'disabled="disabled"')+'>']
												.join('');
											}else{
												return [
												        '<input type="checkbox" class="al-toggle-button is_yx" '+(SESSION_MENU_BUTTONS.nianji_yx == 1?'':'disabled="disabled"')+'>']
												.join('');
											}
										}, // 紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										events : {
											'click .is_yx' : function(e,value, row, index) {
												//启用更改事件
												var is_yx = $(this).prop("checked");
												var IS_YX = 'N';
												if(is_yx){
													IS_YX = 'Y';
												}
												var url = _basepath + "nianji/updateISYX.json";
												$.ajax({
													type:"post",
													dataType:"json",
													data:{DICTIONARIES_ID:row.DICTIONARIES_ID,IS_YX:IS_YX},
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
													SESSION_MENU_BUTTONS.nianji_xg == 1?'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;':'',
													            SESSION_MENU_BUTTONS.nianji_sc == 1?'<a class="cancel ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>':''
															 ]
													.join('');
										}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										 events : {
											'click .edit' : function(e,value, row, index) {
												  //编辑  编辑人员
												nianji.goUpdate(row.DICTIONARIES_ID);
	    							        
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
																url:_basepath+'nianji/delete.json?DICTIONARIES_ID='+row.DICTIONARIES_ID,
																 success:function(data){
																	 if("success" == data.rst){
																		 layer.msg("删除成功！");
																		 $("#nianjiTable").bootstrapTable('destroy');
																		 nianji.getTab();
																	 }
																	 if("error" == data.rst){
																		 layer.msg("该入学年份信息已被使用，无法删除!");
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
	nianji.getTab();
})(jQuery, window);
