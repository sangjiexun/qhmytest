/**
 * 宿舍类型对象
 */
//@ sourceURL=dormitorytype.js
(function($, window) {
	var dormitorytype = {};
	
	//查询按钮点击事件
	$('#dormitorytypeQuery').click(function(){
		$("#dormitorytypeTable").bootstrapTable('destroy');
		dormitorytype.getTab();
	});
	
	//去编辑页面
	dormitorytype.goUpdate = function(pkid){
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '编辑宿舍类型',
			message : $('<div></div>').load(_basepath+'dormitorytype/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							dormitorytype.updateAdd(dialogRef);
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
			title : '新增宿舍类型',
			message : $('<div></div>').load(_basepath+'dormitorytype/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							dormitorytype.updateAdd(dialogRef);
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
	dormitorytype.updateAdd = function(dialogRef){
		var PKID = $("#PKID").val();
		var DT_NAME = $("#DT_NAME").val();
		var IS_UPLOAD_PIC = $("#IS_UPLOAD_PIC").val();
		var IS_NUM_LIMIT = $("#IS_NUM_LIMIT").val();
		var pic_paths = "";
		$("#child").find(".file").each(function(){
			pic_paths = pic_paths+$(this).attr("path")+","; 
		});
		var DISCRIBES = $("#DISCRIBES").val();//描述
		var IS_USE ='0'/* e.currentTarget.checked==true?true:false;//$("#IS_USE").is(':checked');//是否启用
*/		
		if($('#IS_USE').is(':checked')) {
			IS_USE = '1';
		}else{
			IS_USE = '0';
		}
		
		/*if(IS_USE){
			
		}else{
			
		}*/
		var REMARKS = $("#REMARKS").val();
		var PRIORITY = $("#PRIORITY").val();
		var flag = dormitorytype.formcheck();
		if(flag){
			dialogRef.close();
			$.ajax({
				type : "post",
				dataType : "json",
				async: false,
				url : _basepath+ 'dormitorytype/edit.json',
				data : {"PKID":PKID,"DT_NAME":DT_NAME.trim(),"IS_UPLOAD_PIC":IS_UPLOAD_PIC,"PICS":pic_paths,"DISCRIBES":DISCRIBES,"IS_USE":IS_USE,
					"REMARKS":REMARKS,"IS_NUM_LIMIT":IS_NUM_LIMIT,"PRIORITY":PRIORITY},
				success : function(data) {
					if ("success" == data.rst) {
						layer.msg("操作成功！");
						$("#dormitorytypeTable").bootstrapTable('destroy');
						dormitorytype.getTab();
					}
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					alert(errorThrown);
				}
			});
		}
	}
	//验证类型名称是否唯一
	dormitorytype.validate = function(DT_NAME,PKID){
		var flag = true;
		$.ajax({
			type : "post",
			dataType : "json",
			async: false,
			url : _basepath+ 'dormitorytype/getDT_NAME.json',
			data : {"PKID":PKID,"DT_NAME":DT_NAME},
			success : function(data) {
				if ("success" == data.rst) {
					layer.msg("该类型名称已存在，请更换！");
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
	dormitorytype.formcheck = function(){
		var flag = true;
		var DT_NAME = $("#DT_NAME").val();
		var PKID = $("#PKID").val();
		if(DT_NAME.trim()==''){
			layer.msg("类型名称不能为空");
			$("#DT_NAME").focus();
			return false;
		}else if(DT_NAME!=''){
			flag = dormitorytype.validate(DT_NAME.trim(),PKID);
		}
		return flag;
	}
	
	// 获取bootStrapTable表格数据,及参数设置
	dormitorytype.getTab = function() {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		var url = _basepath + "dormitorytype/dormitorytypeTable.json";
		$('#dormitorytypeTable').bootstrapTable(
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
							onLoadSuccess:function(){
					            $('.bootstrap-table tr td').each(function () {
					                $(this).attr("title", $(this).text());
					                $(this).css("cursor", 'pointer');
					            });
					        },
							queryParams : function getParams(params) {
								//班级名称
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
										field : 'DT_NO',
										title : '类型编号',
										align : "center",
										halign : 'center',
										sortable : false
									},*/
									{
										field : 'DT_NAME',
										title : '类型名称',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'IS_UPLOAD_PIC',
										title : '是否上传图片',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'DISCRIBES',
										title : '文字描述',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'IS_NUM_LIMIT',
										title : '是否限制数量',
										align : "center",
										halign : 'center',
										sortable : false
									},{
										field : 'PRIORITY',
										title : '优先级',
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
												        '<input type="checkbox" checked="checked" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.dormitorytype_qy == 1?'':'disabled="disabled"')+'>']
												.join('');
											}else{
												return [
												        '<input type="checkbox" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.dormitorytype_qy == 1?'':'disabled="disabled"')+'>']
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
												var url = _basepath + "dormitorytype/updateIsUse.json";
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
													SESSION_MENU_BUTTONS.dormitorytype_xg == 1?'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;':'',
															SESSION_MENU_BUTTONS.dormitorytype_sc == 1?'<a class="cancel ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>':'' ]
													.join('');
										}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										 events : {
											'click .edit' : function(e,value, row, index) {
												  //编辑  编辑人员
												dormitorytype.goUpdate(row.PKID);
	    							        
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
																url:_basepath+'dormitorytype/delete.json?PKID='+row.PKID,
																 success:function(data){
																	 if("success" == data.rst){
																		 layer.msg("删除成功！");
																		 $("#dormitorytypeTable").bootstrapTable('destroy');
																		 dormitorytype.getTab();
																	 }
																	 if("error" == data.rst){
																		 layer.msg("该宿舍类型已被使用，无法删除!");
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
	dormitorytype.getTab();
})(jQuery, window);
