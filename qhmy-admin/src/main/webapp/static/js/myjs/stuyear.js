/**
 * 年级对象
 */
//@ sourceURL=stuyear.js
(function($, window) {
	var stuyear = {};
	
	//查询按钮点击事件
	$('#stuyearQuery').click(function(){
		$("#stuyearTable").bootstrapTable('destroy');
		stuyear.getTab();
	});
	
	//去编辑页面
	stuyear.goUpdate = function(pkid){
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '编辑学年',
			message : $('<div></div>').load(_basepath+'stuyear/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							stuyear.updateAdd(dialogRef);
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
	
	//学年删除
	stuyear.deleteStuYear = function(pkid){
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '提示信息',
			message : '确认删除这个学年吗？',
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							$.ajax({
								type : "post",
								dataType : "json",
								async: false,
								url : _basepath+ 'stuyear/delete.json',
								data : {"PKID":PKID},
								success : function(data) {
									if ("success" == data.rst) {
										layer.msg("操作成功！");
										dialogRef.close();
										$("#stuyearTable").bootstrapTable('destroy');
										stuyear.getTab();
									}else if("error" == data.rst){
										layer.msg("该学年类型已被使用，无法删除！");
									}
								},
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									alert(errorThrown);
								}
							});
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
			title : '新增学年',
			message : $('<div></div>').load(_basepath+'stuyear/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							stuyear.updateAdd(dialogRef);
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
	stuyear.updateAdd = function(dialogRef){
		var PKID = $("#PKID").val();
		var BIANMA = $("#BIANMA").val();
		var NAME = $("#NAME").val();
		var IS_USED = $("#IS_USED").is(':checked');//是否启用
		var riqi_str='';
		var endloop=false;
		$("#dateArea").find("input").each(function(){
			var temp=$(this).val();
			if(temp==null || temp==''){
				return true;//continue
			}
			if(riqi_str.indexOf(temp) != -1){
				layer.msg('选择的日期存在重复的月份!');
				endloop=true;
				return false;
			}
			riqi_str+=temp+",";
			
		});
		if(endloop==true){
			return;
		}
		if(riqi_str==null || riqi_str==''){
			layer.msg('请选择日期!');
			return false;
		}
		if(IS_USED){
			IS_USED = 'Y';
		}else{
			IS_USED = 'N';
		}
		var BZ = $("#BEIZHU").val();
		var flag = stuyear.formcheck();
		if(flag){
			dialogRef.close();
			$.ajax({
				type : "post",
				dataType : "json",
				async: false,
				url : _basepath+ 'stuyear/edit.json',
				data : {"PKID":PKID,"BIANMA":BIANMA,"NAME":NAME,"IS_USED":IS_USED,"BEIZHU":BZ,'RIQI':riqi_str},
				success : function(data) {
					if ("success" == data.rst) {
						layer.msg("操作成功！");
						$("#stuyearTable").bootstrapTable('destroy');
						stuyear.getTab();
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
	stuyear.validate = function(NAME,PKID){
		var flag = true;
		$.ajax({
			type : "post",
			dataType : "json",
			async: false,
			url : _basepath+ 'stuyear/getStuyear_NAME.json',
			data : {"PKID":PKID,"NAME":NAME.trim()},
			success : function(data) {
				if ("success" == data.rst) {
					layer.msg("该学年名称已存在，请更换！");
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
	stuyear.formcheck = function(){
		var PKID = $("#PKID").val();
		var NAME = $("#NAME").val();
		if(NAME.trim() == ''){
			layer.msg("学年名称不能为空");
			$("#NAME").focus();
			return false;
		}
		if(NAME!=''){
			var flag = stuyear.validate(NAME,PKID);
			if(!flag){
				$("#NAME").focus();
				return false;
			}
		}
		return true;
	}
	
	// 获取bootStrapTable表格数据,及参数设置
	stuyear.getTab = function() {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		var url = _basepath + "stuyear/stuyearTable.json";
		$('#stuyearTable').bootstrapTable(
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
										field : 'SCHOOL_YEAR_BIANMA',
										title : '学年编号',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'SCHOOL_YEAR_NAME',
										title : '学年名称',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'RIQI',
										title : '日期时间',
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
											if(row.IS_USE=='Y'){
												return [
												        '<input type="checkbox" checked="checked" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.stuyear_qy == 1?'':'disabled="disabled"')+'>']
												.join('');
											}else{
												return [
												        '<input type="checkbox" class="al-toggle-button" '+(SESSION_MENU_BUTTONS.stuyear_qy == 1?'':'disabled="disabled"')+'>']
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
												var url = _basepath + "stuyear/updateIsUse.json";
												$.ajax({
													type:"post",
													dataType:"json",
													data:{PKID:row.PKID,IS_USED:IS_USED},
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
										formatter : function(value, row,index) {
											return [
													SESSION_MENU_BUTTONS.stuyear_xg == 1?'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>':'',
													SESSION_MENU_BUTTONS.stuyear_del == 1?' <a class="delete ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o" ></i></a>':''
															 ]
													.join('');
										}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										 events : {
											'click .edit' : function(e,value, row, index) {
												  //编辑  编辑人员
												stuyear.goUpdate(row.PKID);
	    							        
											},
											'click .delete' : function(e,value, row, index) {
												  //删除学年 
												stuyear.deleteStuYear(row.PKID);
	    							        
											}
												} 
									} ],
						});
	};
	//加载表格数据
	stuyear.getTab();
})(jQuery, window);
