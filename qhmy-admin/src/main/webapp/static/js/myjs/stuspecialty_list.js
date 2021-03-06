/**
 * 考生特长对象
 */
//@ sourceURL=stuspecialty_list.js
(function($, window) {
	var poor = {};
	
	//查询按钮点击事件
	$('#poorQuery').click(function(){
		$("#poorTable").bootstrapTable('destroy');
		poor.getTab();
	});
	
	//去编辑页面
	poor.goUpdate = function(pkid){
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '编辑考生特长',
			message : $('<div></div>').load(_basepath+'stuSpecialty/goEdit.json?DICTIONARIES_ID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							poor.updateAdd(dialogRef);
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
	//删除
	poor.del = function(pkid){
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
						url:_basepath+'stuSpecialty/del.json?DICTIONARIES_ID='+pkid,
						 success:function(data){
							 if("success" == data.rst){
								 layer.msg("删除成功！");
								 //删除成功后刷新表格
								 $("#poorTable").bootstrapTable('destroy');
									poor.getTab();
							 }else if("error" == data.rst){
								 layer.msg("该考生特长已被使用，无法删除");
							 }else{
								 layer.msg("删除失败!")
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
	}
	//去新增页面
	$("#btn_add").click(function(){
		var pkid = '';
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '新增考生特长',
			message : $('<div></div>').load(_basepath+'stuSpecialty/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							poor.updateAdd(dialogRef);
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
	poor.updateAdd = function(dialogRef){
		var DICTIONARIES_ID = $("#DICTIONARIES_ID").val();
		var NAME = $("#NAME").val();
		var IS_USED = $("#IS_USED").is(':checked');//是否启用
		if(IS_USED){
			IS_USED = 'Y';
		}else{
			IS_USED = 'N';
		}
		var BZ = $("#BEIZHU").val();
		var flag = poor.formcheck();
		if(flag){
			dialogRef.close();
			$.ajax({
				type : "post",
				dataType : "json",
				async: false,
				url : _basepath+ 'stuSpecialty/edit.json',
				data : {"DICTIONARIES_ID":DICTIONARIES_ID,"NAME":NAME,"IS_USED":IS_USED,"BZ":BZ},
				success : function(data) {
					if ("success" == data.rst) {
						layer.msg("操作成功！");
						$("#poorTable").bootstrapTable('destroy');
						poor.getTab();
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
	poor.validate = function(NAME,PKID){
		var flag = true;
		$.ajax({
			type : "post",
			dataType : "json",
			async: false,
			url : _basepath+ 'stuSpecialty/getpoor_NAME.json',
			data : {"PKID":PKID,"NAME":NAME.trim()},
			success : function(data) {
				if ("error" == data.rst) {
					layer.msg("该名称已存在，请更换！");
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
	poor.formcheck = function(){
		var PKID = $("#DICTIONARIES_ID").val();
		var NAME = $("#NAME").val();
		if(NAME.trim() == ''){
			layer.msg("考生特长不能为空");
			$("#NAME").focus();
			return false;
		}
		if(NAME!=''){
			var flag = poor.validate(NAME,PKID);
			if(!flag){
				$("#NAME").focus();
				return false;
			}
		}
		return true;
	}
	
	// 获取bootStrapTable表格数据,及参数设置
	poor.getTab = function() {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		var url = _basepath + "stuSpecialty/accountNatureTable.json";
		$('#poorTable').bootstrapTable(
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
								//年级身份证或姓名
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
										title : '编号',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'NAME',
										title : '考生特长',
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
												        '<input type="checkbox" checked="checked" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.kstc_qy == 1?'':'disabled="disabled"')+'>']
												.join('');
											}else{
												return [
												        '<input type="checkbox" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.kstc_qy == 1?'':'disabled="disabled"')+'>']
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
												var url = _basepath + "stuSpecialty/updateIsUse.json";
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
										formatter : function(value, row,index) {
											return [
													SESSION_MENU_BUTTONS.kstc_edit == 1?'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;':'',
													SESSION_MENU_BUTTONS.kstc_del == 1?' <a class="del ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o" ></i></a>':''
															 ]
													.join('');
										}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										 events : {
											'click .edit' : function(e,value, row, index) {
												  //编辑  编辑人员
												poor.goUpdate(row.DICTIONARIES_ID);
	    							        
											},
											'click .del' : function(e,value, row, index) {
												  //编辑  编辑人员
												poor.del(row.DICTIONARIES_ID);
	    							        
											}
												} 
									} ],
						});
	};
	//加载表格数据
	poor.getTab();
})(jQuery, window);
