/**
 * 层次对象
 */
//@ sourceURL=cengci.js
(function($, window) {
	var cengci = {};
	
	//查询按钮点击事件
	$('#cengciQuery').click(function(){
		$("#cengciTable").bootstrapTable('destroy');
		cengci.getTab();
	});
	
	//去编辑页面
	cengci.goUpdate = function(pkid){
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '编辑学生类型',
			message : $('<div></div>').load(_basepath+'cengci/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							cengci.updateAdd(dialogRef);
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
			title : '新增学生类型',
			message : $('<div></div>').load(_basepath+'cengci/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							cengci.updateAdd(dialogRef);
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
	cengci.updateAdd = function(dialogRef){
		var PKID = $("#PKID").val();
		var CENGCI_BIANMA = $("#CENGCI_BIANMA").val();
		var CENGCI_NAME = $("#CENGCI_NAME").val();
		var XUENIANZHI = $("#XUENIANZHI").val();
		var IS_USE = $("#IS_USE").is(':checked');//是否启用
		var CCDAIMA=$("#CCDAIMA").val();
		if(IS_USE){
			IS_USE = '1';
		}else{
			IS_USE = '0';
		}
		var BEIZHU = $("#BEIZHU").val();
		var flag = cengci.formcheck();
		if(flag){
			dialogRef.close();
			$.ajax({
				type : "post",
				dataType : "json",
				async: false,
				url : _basepath+ 'cengci/edit.json',
				data : {"PKID":PKID,"CENGCI_BIANMA":CENGCI_BIANMA,"XUENIANZHI":XUENIANZHI,"CENGCI_NAME":CENGCI_NAME,"IS_USE":IS_USE,
					"BEIZHU":BEIZHU,"CCDAIMA":CCDAIMA},
				success : function(data) {
					if ("success" == data.rst) {
						layer.msg("操作成功！");
						$("#cengciTable").bootstrapTable('destroy');
						cengci.getTab();
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
	cengci.validate = function(CENGCI_NAME,PKID){
		var flag = true;
		$.ajax({
			type : "post",
			dataType : "json",
			async: false,
			url : _basepath+ 'cengci/getCENGCI_NAME.json',
			data : {"PKID":PKID,"CENGCI_NAME":CENGCI_NAME},
			success : function(data) {
				if ("success" == data.rst) {
					layer.msg("该学生类型名称已存在，请更换！");
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
	//验证层次代码是否唯一
	cengci.ccdaimayanzheng = function(CCDAIMA,PKID){
		var bol = true;
		$.ajax({
			type : "post",
			dataType : "json",
			async: false,
			url : _basepath+ 'cengci/getCCDAIMA.json',
			data : {"PKID":PKID,"CCDAIMA":CCDAIMA},
			success : function(data) {
				if ("success" == data.rst) {
					layer.msg("提示该学生类型代码已经存在，不可保存");
					bol = false;
				}
			},
			error : function(XMLHttpRequest, textStatus,
					errorThrown) {
				alert(errorThrown);
				bol = false;
			}
		});
		return bol;
		
		
		
	}
	
	
	//页面校验
	cengci.formcheck = function(){
		var PKID = $("#PKID").val();
		var CENGCI_NAME = $("#CENGCI_NAME").val();
		var XUENIANZHI = $("#XUENIANZHI").val();
		var CCDAIMA=$("#CCDAIMA").val();
		var reg=/^[0-9]*$/;
		
		if(!reg.test(CCDAIMA)){
			layer.msg("格式不正确，不可保存");
			return false;
		}
		if(CENGCI_NAME.trim() == ''){
			layer.msg("学生类型名称不能为空");
			$("#CENGCI_NAME").focus();
			return false;
		}else if(XUENIANZHI.trim() == ''){
			layer.msg("学年制不能为空");
			$("#XUENIANZHI").focus();
			return false;
		}
		if(CENGCI_NAME!=''){
			var flag = cengci.validate(CENGCI_NAME,PKID);
			var bol=cengci.ccdaimayanzheng(CCDAIMA,PKID);
			if(!flag){
				$("#CENGCI_NAME").focus();
				return false;
			}
			if(!bol){
				$("#CCDAIMA").focus();
				return false;
			}
		}
		return true;
	}
	
	// 获取bootStrapTable表格数据,及参数设置
	cengci.getTab = function() {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		var url = _basepath + "cengci/cengciTable.json";
		$('#cengciTable').bootstrapTable(
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
								//层次身份证或姓名
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
									/*{
										field : 'CENGCI_BIANMA',
										title : '学生类型编号',
										align : "center",
										halign : 'center',
										sortable : false
									},*/
									{
										field : 'CENGCI_NAME',
										title : '学生类型名称',
										align : "center",
										halign : 'center',
										sortable : false
									},
									/*{
										field : 'XUENIANZHI',
										title : '学年制',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'CCDAIMA',
										title : '学生类型代码',
										align : "left",
										halign : 'center',
										sortable : false
									},*/
									{
										field : 'IS_USE',
										title : '是否启用',
										align : "center",
										halign : 'center',
										formatter : function(value, row, index) {
											if(row.IS_USE=='1'){
												return [
												        '<input type="checkbox" disabled="true" checked="checked" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.cc_qy == 1?'':'disabled="disabled"')+'>']
												.join('');
											}else{
												return [
												        '<input type="checkbox" disabled="true" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.cc_qy == 1?'':'disabled="disabled"')+'>']
												.join('');
											}
										}, // 紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										events : {
											'click .al-toggle-button' : function(e,value, row, index) {
												//启用更改事件
												var isUse =  e.currentTarget.checked==true?true:false;//$(this).prop("checked");
												var IS_USE = 0;
												if(isUse){
													IS_USE = 1;
												}
												var url = _basepath + "cengci/updateIsUse.json";
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

									}/*,
									{
										field : 'BEIZHU',
										title : '备注',
										align : "left",
										halign : 'center',
										sortable : false
									},*/
						/*			{
										field : 'opt',
										title : '操作',
										align : "center",
										halign : 'center',
										formatter : function(value, row,
												index) {
											return [
													SESSION_MENU_BUTTONS.cc_bj == 1?'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;':'',
															SESSION_MENU_BUTTONS.cc_bj == 1?'<a class="cancel ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>':'' ]
													.join('');
										}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										 events : {
											'click .edit' : function(e,value, row, index) {
												  //编辑  编辑人员
												cengci.goUpdate(row.PKID);
	    							        
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
																url:_basepath+'cengci/delete.json?PKID='+row.PKID,
																 success:function(data){
																	 if("success" == data.rst){
																		 layer.msg("删除成功！");
																		 $("#cengciTable").bootstrapTable('destroy');
																		 cengci.getTab();
																	 }
																	 if("error" == data.rst){
																		 layer.msg("该学生类型信息已被使用，无法删除!");
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
									}*/ ],
						});
	};
	//加载表格数据
	cengci.getTab();
})(jQuery, window);
