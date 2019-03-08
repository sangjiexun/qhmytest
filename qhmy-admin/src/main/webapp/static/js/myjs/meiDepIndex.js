(function($, window) {
	var poor = {};
	
	
	
	
	
	//查询按钮点击事件
	$('#poorQuery').click(function(){
/*		$("#poorTable").bootstrapTable('destroy');
		poor.getTab();*/
		 $("#poorTable").bootstrapTable('refresh');

	});
	
	//页面校验
	poor.yanzheng = function(){
		
		var NAME = $("#NAME").val();
		var ORDER_NUMBER = $("#ORDER_NUMBER").val();
		var IS_QY = $("#IS_QY").is(':checked');//是否启用
		if(IS_QY){
			IS_QY = 'Y';
		}else{
			IS_QY = 'N';
		}
		

		
		if(NAME.trim()==""){
			 $("#NAME").focus();
			  layer.msg("美院名称不能为空");
			  return false;
		}

		if(NAME.trim().length>=20){
			 $("#NAME").focus();
			  layer.msg("美院名称过长");
			  return false;
		}

		var regEn = /[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im,
		    regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;
		 
		if(regEn.test(NAME) || regCn.test(NAME)) {
			 layer.msg("美院名称不能使用特殊符号");
		    return false;
		}
			
		return true;
	};
	
	
	
	$("#addMeiDep").click(function(){
		poor.goUpdateAndAdd("");
	});
	poor.goUpdateAndAdd = function(PKID){
		var tit="新增美院";
		if(PKID!=""){
			tit="修改美院";
		}
		
		//去编辑或新增页面页面
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : tit,
			message : $('<div></div>').load(_basepath+'departmentMei/goEdit.json?PKID='+PKID),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							poor.updateAdds(dialogRef);
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
	
	//新增/修改方法
	poor.updateAdds = function(dialogRef){

		var PKID = $("#PKID").val();
		var NAME = $("#NAME").val();
		NAME=$.trim(NAME);
		var ORDER_NUMBER = " ";
		var IS_QY =$("#IS_QY").is(':checked');//是否启用
		if(IS_QY){
			IS_QY = 'Y';
		}else{
			IS_QY = 'N';
		}
		
		
		
		var flag = poor.yanzheng();
		if(flag){
			
			$.ajax({
				type : "post",
				dataType : "json",
				async: false,
				url : _basepath+ 'departmentMei/insert.json',
				data : {"PKID":PKID,"NAME":NAME,"ORDER_NUMBER":ORDER_NUMBER,"IS_QY":IS_QY,"CFNAME":$("#CFNAME").val()},
				success : function(data) {
					if ("SUCCESS" == data.result) {
						dialogRef.close();
						layer.msg("操作成功！");
						 $("#poorTable").bootstrapTable('refresh');

					}
					if("CHOGNFU" == data.result){
						layer.msg("名称重复！");
					}
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					alert(errorThrown);
				}
			});
		}
	};
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
		    	 var url = _basepath + "departmentMei/delete.json";
				 $.get(url,{PKID:pkid},function(result){
					 if(result.result=="SUCCESS"){
						 layer.msg("删除成功");
						 $("#poorTable").bootstrapTable('refresh');

					 }else if(result.result=="CHONGFU"){
						 layer.msg("删除失败,该美院已被使用");
					 }else{
						 layer.msg("删除失败,请联系管理员");
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
	// 获取bootStrapTable表格数据,及参数设置
	poor.getTab = function() {
		//获得按钮权限
		var url = _basepath + "departmentMei/list.json";
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
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
										limit : params.limit, // 页面大小
										sortName : this.sortName,
										NAME:$("#keyWord").val(),
										sortOrder : this.sortOrder,
										pageindex : this.pageNumber
								};
								return temp;
							},
							buttonsAlign : "right",// 按钮对齐方式
							selectItemName : 'id',
							toolbar : "#toolbar",
							toolbarAlign : 'left',
							columns : [
									/*{
										field : 'ORDER_NUMBER',
										title : '编号',
										align : "center",
										halign : 'center',
										sortable : false
									},*/
									{
										field : 'NAME',
										title : '美院名称',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'IS_QY',
										title : '是否启用',
										align : "center",
										halign : 'center',
										formatter:function(value,row,index){
											if(value=="Y"||value=="y")
											return '<input type="checkbox" checked="checked" class="al-toggle-button" >';
											else
											return '<input type="checkbox" class="al-toggle-button" >';
										},events:{
											'click .al-toggle-button':function(e,value,row,index){
												 var bol=e.currentTarget.checked==true?true:false;//$(this).is(':checked');
												 var isqy="N";
												 if(bol)
													 isqy="Y";
												 var pkid=row.PKID;
												 var url = _basepath + "departmentMei/update_isqy.json";
												 $.get(url,{IS_QY:isqy,PKID:pkid},function(result){
													 if(result.result=="SUCCESS"){
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
										field : '',
										title : '操作',
										align : "center",
										halign : 'center',
										formatter : function(value, row,index) {
											return [
													'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;',
													' <a class="del ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o" ></i></a>'
															 ]
													.join('');
										}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										 events : {
											'click .edit' : function(e,value, row, index) {
												  //编辑  编辑人员
												poor.goUpdateAndAdd(row.PKID);
	    							        
											},
											'click .del' : function(e,value, row, index) {
												  //编辑  编辑人员
												var pkid=row.PKID;
												poor.del(pkid);
	    							        
											}
												} 
									}],
						});
	};
	
	
	
	
	
	
	
	
	//加载表格数据
	poor.getTab();
	
	})(jQuery, window);
