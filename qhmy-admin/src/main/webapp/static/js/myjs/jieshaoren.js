/**
 * 介绍人对象
 */
//@ sourceURL=jieshaoren.js
(function($, window) {
	var jieshaoren = {};
	
	//查询按钮点击事件
	$('#jieshaorenQuery').click(function(){
		$("#jieshaorenTable").bootstrapTable('destroy');
		jieshaoren.getTab();
	});
	
	//去编辑页面
	jieshaoren.goUpdate = function(pkid){
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '编辑介绍人',
			message : $('<div></div>').load(_basepath+'jieshaoren/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							jieshaoren.updateAdd(dialogRef);
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
			title : '新增介绍人',
			message : $('<div></div>').load(_basepath+'jieshaoren/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							jieshaoren.updateAdd(dialogRef);
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
	jieshaoren.updateAdd = function(dialogRef){
		var PKID = $("#PKID").val();
		var SHENFENZHENGHAO = $("#SHENFENZHENGHAO").val();
		var XINGMING = $("#XINGMING").val();
		var TELPHONE = $("#TELPHONE").val();
		var IS_USE = $("#IS_USE").is(':checked');//是否启用
		if(IS_USE){
			IS_USE = '1';
		}else{
			IS_USE = '0';
		}
		var BEIZHU = $("#BEIZHU").val();
		var flag = jieshaoren.formcheck();
		if(flag){
			dialogRef.close();
			$.ajax({
				type : "post",
				dataType : "json",
				async: false,
				url : _basepath+ 'jieshaoren/edit.json',
				data : {"PKID":PKID,"SHENFENZHENGHAO":SHENFENZHENGHAO,"XINGMING":XINGMING,"TELPHONE":TELPHONE,"IS_USE":IS_USE,
					"BEIZHU":BEIZHU},
				success : function(data) {
					if ("success" == data.rst) {
						layer.msg("操作成功！");
						$("#jieshaorenTable").bootstrapTable('destroy');
						jieshaoren.getTab();
					}
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					alert(errorThrown);
				}
			});
		}
	}
	//验证身份证号是否唯一
	jieshaoren.validate = function(SHENFENZHENGHAO,PKID){
		var flag = true;
		$.ajax({
			type : "post",
			dataType : "json",
			async: false,
			url : _basepath+ 'jieshaoren/getShenFenZheng.json',
			data : {"PKID":PKID,"SHENFENZHENGHAO":SHENFENZHENGHAO},
			success : function(data) {
				if ("success" == data.rst) {
					layer.msg("该身份证号已存在，请更换！");
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
	jieshaoren.formcheck = function(){
		var PKID = $("#PKID").val();
		var SHENFENZHENGHAO = $("#SHENFENZHENGHAO").val();
		var XINGMING = $("#XINGMING").val();
		var TELPHONE = $("#TELPHONE").val();
		if(SHENFENZHENGHAO.trim()==''){
			layer.msg("身份证号不能为空");
			$("#SHENFENZHENGHAO").focus();
			return false;
		}
		if(SHENFENZHENGHAO!=''){
			if(!SHENFENZHENGHAO.match(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/)){
				layer.msg("身份证号格式不正确");
				$("#SHENFENZHENGHAO").focus();
				return false;
			}
			var flag = jieshaoren.validate(SHENFENZHENGHAO,PKID);
			if(!flag){
				$("#SHENFENZHENGHAO").focus();
				return false;
			}
		}
		if(XINGMING.trim() == ''){
			layer.msg("姓名不能为空");
			$("#XINGMING").focus();
			return false;
		}else if(TELPHONE.trim() == ''){
			layer.msg("手机号不能为空");
			$("#TELPHONE").focus();
			return false;
		}else if(!TELPHONE.match(/^1[34578]\d{9}$/)){
			layer.msg("手机号格式不正确");
			$("#TELPHONE").focus();
			return false;
		}else{
			return true;
		}
	}
	
	// 获取bootStrapTable表格数据,及参数设置
	jieshaoren.getTab = function() {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		var url = _basepath + "jieshaoren/jieshaorenTable.json";
		$('#jieshaorenTable').bootstrapTable(
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
								//介绍人身份证或姓名
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
										field : 'SHENFENZHENGHAO',
										title : '身份证',
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
										field : 'TELPHONE',
										title : '联系方式',
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
												        '<input type="checkbox" checked="checked" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.jsr_qy == 1?'':'disabled="disabled"')+'>']
												.join('');
											}else{
												return [
												        '<input type="checkbox" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.jsr_qy == 1?'':'disabled="disabled"')+'>']
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
												var url = _basepath + "jieshaoren/updateIsUse.json";
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
										field : 'BEIZHU',
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
													SESSION_MENU_BUTTONS.jsr_bj == 1?'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;':'',
															SESSION_MENU_BUTTONS.jsr_sc == 1?'<a class="cancel ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>':'' ]
													.join('');
										}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										 events : {
											'click .edit' : function(e,value, row, index) {
												  //编辑  编辑人员
												jieshaoren.goUpdate(row.PKID);
	    							        
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
																url:_basepath+'jieshaoren/delete.json?PKID='+row.PKID,
																 success:function(data){
																	 if("success" == data.rst){
																		 layer.msg("删除成功！");
																		 $("#jieshaorenTable").bootstrapTable('destroy');
																		 jieshaoren.getTab();
																	 }
																	 if("error" == data.rst){
																		 layer.msg("该介绍人信息已被使用，无法删除!");
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
	jieshaoren.getTab();
})(jQuery, window);
