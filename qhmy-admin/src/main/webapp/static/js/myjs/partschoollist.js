/**
 * 合作学校对象
 */
(function($, window) {
	var partschool = {};
	
	//查询按钮点击事件
	$('#partSchoolQuery').click(function(){
		$("#partSchoolTable").bootstrapTable('destroy');
		partschool.getTab();
	});
	$("#btn_import").click(function(){
		var dialog = BootstrapDialog.show({
			title:'导入学校',
			message: $('<div></div>').load("importPartSchool/importpartSchool.json"),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
	            label: '关闭',
	            cssClass: 'btn-danger',
	            action: function(dialog) {
	            	dialog.close();
	            	$('#partSchoolTable').bootstrapTable("refresh");
	            }
	        }]
		});
	});
	
	//点击按钮导出
	$("#btn_export").click(function(){
		//合作学校名称
		var SCHOOLNAMESEARCH = $('#SCHOOLNAMESEARCH').val();
		
		window.location.href=encodeURI(_basepath+'partSchool/exportExcel.json?SCHOOLNAME='+SCHOOLNAMESEARCH);
	});
	//去新增页面
	$("#btn_add").click(function(){
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '新增学校',
			message : $('<div></div>').load(_basepath+'partSchool/goAdd.json?PKID='+""),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							partschool.updateAdd(dialogRef);
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
	
	//新增方法
	partschool.updateAdd = function(dialogRef){
		var reg=/^\s+$/g;
		//合作学校名称
		var SCHOOLNAME = $("#SCHOOLNAME").val();
		//验证班容量不能为空
		if(SCHOOLNAME==''||SCHOOLNAME==null||reg.test(SCHOOLNAME)){
			layer.msg("文化课学校名称不能为空");
			$("#SCHOOLNAME").focus();
			return false;
		}
		//是否合作学校
		var ISHEZUO  = $("#ISHEZUO").is(':checked');//是否启用
		if(ISHEZUO){
			ISHEZUO = 'Y';
		}else{
			ISHEZUO = 'N';
		}
		//定金
		var DINGJIN = $("#DINGJIN").val();
		/*var reg2 =  /^[1-9]\d*(\.\d)?$/g;*/
		var reg2=/^(([1-9]\d*)|\d)(\.\d{1,2})?$/;
		if(DINGJIN!=""&&DINGJIN!=null){
			if(!reg2.test(DINGJIN)){
				layer.msg("定金只能填写整数或小数（小数后保留两位）");
				$("#DINGJIN").focus();
				return false;
			}
		}
		var pkid = $("#SchoolPkid").val();
		var SchoolNameHidden = $("#SchoolNameHidden").val();
		$.ajax({
			type : "post",
			dataType : "json",
			async: false,
			url : _basepath+ 'partSchool/add.json',
			data : {"SCHOOLNAME":SCHOOLNAME,"DINGJIN":DINGJIN,"PKID":pkid,"SchoolNameHidden":SchoolNameHidden,
					"ISHEZUO":ISHEZUO},
			success : function(data) {
				if ("success" == data.rst) {
					dialogRef.close();
					layer.msg("操作成功！");
					$("#partSchoolTable").bootstrapTable('destroy');
					partschool.getTab();
				}
				if("exist"==data.rst){
					layer.msg("已存在相同的学校名称！");
				}
			},
			error : function(XMLHttpRequest, textStatus,
					errorThrown) {
				alert(errorThrown);
			}
		});
	};


	//批量删除按钮绑定单机事件
	$('#btn_del').bind('click',function(){

		var pkids=partschool.getIdSelections();
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			 BootstrapDialog.show({  //显示需要提交的表单。
					  title:'提示信息',	
			        message: '请至少选择一条数据!',
			         buttons: [{
					    label: '关闭',
					    cssClass: 'btn-danger',
					    action: function(dialogRef){
					       dialogRef.close();
					    }
				    }]
	        }); 
			return false;
		}
		  BootstrapDialog.show({  //显示需要提交的表单。
          	title:'提示信息',	
          message: '你确定要删除记录吗？',
          closable: false, 
            buttons: [{
			    label: '确定',
			    cssClass: 'btn-danger',
			    action: function(dialogRef){
			    partschool.batchDel(pkids);
                dialogRef.close();
              }
		  }, {
		    label: '关闭',
		    cssClass: 'btn-default',
		    action: function(dialogRef){
		       dialogRef.close();
		    }
		  }
		  ]
          });
		
	
	});
	//去编辑页面
	partschool.goUpdate = function(pkid){
		
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '编辑学校',
			message : $('<div></div>').load(_basepath+'partSchool/goAdd.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {							
							partschool.edit(pkid,dialogRef);
							//dialogRef.close();
						}
					}, {
						label : '取消',
						cssClass : 'btn-default',
						action : function(dialogRef) {
							dialogRef.close();
						}
					} ]
		});
	};
	//批量删除方法
	partschool.batchDel=function(e){
		$.post(_basepath+"partSchool/batchDel.json?pkids="+e,function(data){
			if(data.result=="success"){
				layer.msg("删除成功!");
				 $("#partSchoolTable").bootstrapTable('refresh', {url: _basepath+"partSchool/partschoolTable.json"});
			}
			if("isExist" == data.result){
				 layer.msg("文化课学校已经被学生使用不能删除！");
			 }
		});
	};
	//修改方法
	partschool.edit=function(e,dialogRef){
		var reg=/^\s+$/g;
		//文化课学校名称
		var SCHOOLNAME = $("#SCHOOLNAME").val();
		//是否合作学校
		var ISHEZUO  = $("#ISHEZUO").is(':checked');//是否启用
		if(ISHEZUO){
			ISHEZUO = 'Y';
		}else{
			ISHEZUO = 'N';
		}
		//验证班容量不能为空
		if(SCHOOLNAME==''||SCHOOLNAME==null||reg.test(SCHOOLNAME)){
			layer.msg("文化课学校名称不能为空");
			$("#SCHOOLNAME").focus();
			return false;
		}
		//定金
		var DINGJIN = $("#DINGJIN").val();
		/*var reg2 =  /^[1-9]\d*(\.\d)?$/g;*/
		var reg2=/^(([1-9]\d*)|\d)(\.\d{1,2})?$/;
		if(DINGJIN!=""&&DINGJIN!=null){
			if(!reg2.test(DINGJIN)){
				layer.msg("定金只能填写整数或小数（小数后保留两位）");
				$("#DINGJIN").focus();
				return false;
			}
		}
		var SchoolNameHidden = $("#SchoolNameHidden").val();
		$.ajax({
			type : "post",
			dataType : "json",
			async: false,
			url : _basepath+ 'partSchool/add.json',
			data : {"SCHOOLNAME":SCHOOLNAME,"DINGJIN":DINGJIN,"PKID":e,"SchoolNameHidden":SchoolNameHidden,
					"ISHEZUO":ISHEZUO},
			success : function(data) {
				if ("success" == data.rst) {
					layer.msg("操作成功！");
					dialogRef.close();
					$("#partSchoolTable").bootstrapTable('destroy');
					partschool.getTab();
				}
				if("exist"==data.rst){
					layer.msg("已存在相同的学校名称！");
				}
			},
			error : function(XMLHttpRequest, textStatus,
					errorThrown) {
				alert(errorThrown);
			}
		});
	};
	
	// 获取bootStrapTable表格数据,及参数设置
	partschool.getTab = function() {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		var url = _basepath + "partSchool/partschoolTable.json";
		$('#partSchoolTable').bootstrapTable(
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
								//文化课学校名称
								var SCHOOLNAMESEARCH = $('#SCHOOLNAMESEARCH').val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									SCHOOLNAME : SCHOOLNAMESEARCH,
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
										 title : "全选",//标题
									     align : "center",//水平
										 checkbox : true,
										 valign : "middle"//垂直
										
									  },
						             {
						        	    field : 'PKID',
						        	    align : "center",
						        	    halign : 'center'
						             },
									{
										field : 'SCHOOLNAME',
										title : '文化课学校',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'DINGJIN',
										title : '定金',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'ISHEZUO',
										title : '是否合作学校',
										align : "center",
										halign : 'center',
										formatter : function(value, row, index) {
											if(row.ISHEZUO=='Y'){
												return [
												        '<input type="checkbox" checked="checked" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.partschool_qy == 1?'':'disabled="disabled"')+'>']
												.join('');
											}else{
												return [
												        '<input type="checkbox" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.partschool_qy == 1?'':'disabled="disabled"')+'>']
												.join('');
											}
										}, // 紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										events : {
											'click .al-toggle-button' : function(e,value, row, index) {
												//启用更改事件
												var isUse =e.currentTarget.checked==true?true:false;
												var ISHEZUO = 'N';
												if(isUse){
													ISHEZUO = 'Y';
												}
												var url = _basepath + "partSchool/updateIsHezuo.json";
												$.ajax({
													type:"post",
													dataType:"json",
													data:{PKID:row.PKID,ISHEZUO:ISHEZUO},
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
											return ['<a class="edit ml10" href="javascript:void(0)" title="编辑"><i class="fa fa-pencil"></i></a>&nbsp;&nbsp;'
													+'<a class="cancel ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>'
													]
													.join('');
										}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										 events : {
											'click .edit' : function(e,value, row, index) {
												  //编辑学校
												 partschool.goUpdate(row.PKID);
	    							        
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
																url:_basepath+'partSchool/delete.json?PKID='+row.PKID,
																 success:function(data){
																	 if("success" == data.rst){
																		 layer.msg("删除成功！");
																		 $("#partSchoolTable").bootstrapTable('destroy');
																		 partschool.getTab();
																	 }
																	 if("isExist" == data.rst){
																		 layer.msg("文化课学校已经被学生使用不能删除！");
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
		 $('#partSchoolTable').bootstrapTable('hideColumn', 'PKID');//隱藏列
	};
	//获取选中的行
	partschool.getIdSelections=function () {
			var $table = $('#partSchoolTable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.PKID;
        });
	};
	//加载表格数据
	partschool.getTab();
})(jQuery, window);
