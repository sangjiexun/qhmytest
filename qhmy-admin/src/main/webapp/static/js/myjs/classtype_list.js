/**
 * 班型对象
 */
//@ sourceURL=classtype_list.js
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
			title : '编辑班型',
			message : $('<div></div>').load(_basepath+'basicClass/goClassTypeEdit.json?PKID='+pkid),
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
			title : '新增班型',
			message : $('<div></div>').load(_basepath+'basicClass/goClassTypeEdit.json?PKID='+pkid),
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
		var NAME = $("#NAME").val();
		//班型
		var BIANMA = $("#BIANMA").val();
		//班容量
		var SFQY = $("#SFQY").val();
		
		var IS_USED = $("#IS_USED").val();
		//验证班容量不能为空
		if(NAME==''||NAME==null){
			layer.msg("班型名称不能为空");
			$("#NAME").focus();
			return false;
		}
		if(BIANMA==''||BIANMA==null){
			layer.msg("学号代码不能为空");
			$("#BIANMA").focus();
			return false;
		}
		var IS_USED = $("#IS_USED").is(':checked');//是否启用
		if(IS_USED){
			IS_USED = 'Y';
		}else{
			IS_USED = 'N';
		}
		var SFQY = $("#SFQY").is(':checked');//是否启用
		if(SFQY){
			SFQY = '1';
		}else{
			SFQY = '0';
		}
		if(flag){
			
			$.ajax({
				type : "post",
				dataType : "json",
				async: false,
				url : _basepath+ 'basicClass/editClassType.json',
				data : {"PKID":PKID,"NAME":NAME,"BIANMA":BIANMA,"IS_USED":IS_USED,
					"SFQY":SFQY},
				success : function(data) {
					if ("success" == data.rst) {
						layer.msg("操作成功！");
						$("#classTable").bootstrapTable('destroy');
						classmanage.getTab();
						dialogRef.close();
					}else if ("relname" == data.rst){
						layer.msg("该班型名称已存在！");
					}else if ("relbianma" == data.rst){
						layer.msg("该学号代码已存在！");
					}else{
						layer.msg("操作失败，请重试！");
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
		var url = _basepath + "basicClass/classtypeTable.json";
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
								//班型名称
								var className = $("#className").val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									classTypeName:className,
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
										field : 'NAME',
										title : '班型名称',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'BIANMA',
										title : '学号代码',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'IS_BUILTIN',
										title : '是否内置',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter:function(value,row,index){
											if(value=="1")
											return '是';
											else
											return '否';
										}
									},
									{
										field : 'SFQY',
										title : '是否预交',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter:function(value,row,index){
											if(value=="1")
											return '<input type="checkbox" checked="checked" class="al-toggle-button" >';
											else
											return '<input type="checkbox" class="al-toggle-button" >';
										},events:{
											'click .al-toggle-button':function(e,value,row,index){
												 var bol=e.currentTarget.checked==true?true:false;
												 var isqy="0";
												 if(bol)
													 isqy="1";
												 var pkid=row.PKID;
												 var url = _basepath + "basicClass/updateclasstypeSFQY.json";
												 $.get(url,{SFQY:isqy,PKID:pkid},function(result){
													 if(result.rst=="success"){
														 layer.msg("更改成功");
														 $("#poorTable").bootstrapTable('refresh');
													 }else{
														 layer.msg("更改失败,请联系管理员");
													 }
														 

												 });
												
												 
											}
										}
									},
									{
										field : 'IS_USED',
										title : '是否启用',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter:function(value,row,index){
											if(value=="Y"||value=="y")
											return '<input type="checkbox" checked="checked" class="al-toggle-button" >';
											else
											return '<input type="checkbox" class="al-toggle-button" >';
										},events:{
											'click .al-toggle-button':function(e,value,row,index){
												 var bol=e.currentTarget.checked==true?true:false;
												 var isqy="N";
												 if(bol)
													 isqy="Y";
												 var pkid=row.PKID;
												 var url = _basepath + "basicClass/updateclasstypeIS_USED.json";
												 $.get(url,{IS_USED:isqy,PKID:pkid},function(result){
													 if(result.rst=="success"){
														 layer.msg("更改成功");
														 $("#poorTable").bootstrapTable('refresh');
													 }else{
														 layer.msg("更改失败,请联系管理员");
													 }
														 

												 });
												
												 
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
											if(row.IS_BUILTIN != '1'){
												return [
														SESSION_MENU_BUTTONS.bxgl_bj == 1?'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;':'',
																SESSION_MENU_BUTTONS.bxgl_sc == 1?'<a class="cancel ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>':'' ]
														.join('');
											}
											
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
																url:_basepath+'basicClass/deleteClassType.json?PKID='+row.PKID,
																 success:function(data){
																	 if("success" == data.rst){
																		 layer.msg("删除成功！");
																		 $("#classTable").bootstrapTable('destroy');
																		 classmanage.getTab();
																	 }else if("rel" == data.rst){
																		 layer.msg("该班型已被应用，禁止删除！");
																	 }else{
																		 layer.msg("删除失败，请重试！");
																	 }
																	/* if("error" == data.rst){
																		 layer.msg("该班型信息已被使用，无法删除!");
																	 }*/
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
