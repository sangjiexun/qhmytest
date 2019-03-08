/**
 * 调整专业审核对象
 */
//@ sourceURL=changeDepCheck.js
(function($, window) {
	var changeDepCheck = {};
	
	//查询按钮点击事件
	$('#changeDepCheckQuery').click(function(){
		//选择的入学年份
		var grade=$('#grade').val();
		//院校专业pkids
		var orgtree=$('#orgtree').val();
		//审核状态
		var CHECK_STATUS = $('#CHECK_STATUS').val();
		//学生类型
			var xslx=$("#xslx").val();
			var xslx_str='';
			if(xslx!=null){
				for(var i=0;i<xslx.length;i++){
					xslx_str+=xslx[i]+',';
				}
			}
		//关键词
		var keywords = $("#keywords").val();
		$("#changeDepCheckTable").bootstrapTable('refresh', {url: encodeURI(_basepath+"changeDepCheck/changeDepCheckTable.json?grade="+grade+"&&orgtree="+orgtree+"&&CHECK_STATUS="+CHECK_STATUS+"&keywords="+keywords+"&XSLX_PKID="+xslx_str)});
		
	});
	
	//获取选中的行
	changeDepCheck.getIdSelections=function () {
			var $table = $('#changeDepCheckTable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
        		return row.PKID;
        	});
	};
	//通过按钮的批量
	$('#btn_tongguo').click(function(){
		var pkids=changeDepCheck.getIdSelections();
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			  BootstrapDialog.show({  //显示需要提交的表单。
				  title:'提示信息',	
				  message: '请选择一条数据!',
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
		changeDepCheck.updatebatch(pkids,'1');
	});
		
	//不通过按钮的批量
	$('#btn_btongguo').click(function(){
		var pkids=changeDepCheck.getIdSelections();
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			  BootstrapDialog.show({  //显示需要提交的表单。
				  title:'提示信息',	
				  message: '请选择一条数据!',
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
		changeDepCheck.updatebatch(pkids,'2');
	});
	//更改为验收状态
	changeDepCheck.updatePass = function(PKID,CHECK_STATUS){
		var message = "";
		if(CHECK_STATUS=='1'){
			message="确定审核通过吗？";
		}else{
			message="确定审核不通过吗？";
		}
		BootstrapDialog.show({  
		 	 	title: '提示信息',
	            message: message,
	            size :BootstrapDialog.SIZE_NORMAL,
	            closable: true, 
	              buttons: [{
				    label: '确定',
				    cssClass: 'btn btn-danger',
				    action: function(dialogRef){
		                    dialogRef.close();
		                    var url = _basepath+"changeDepCheck/updatePass.json";
		             		$.ajax({
		             			type : 'POST',
		             			url : url,
		             			async : false,
		             			data : {
		             				PKID : PKID,
		             				CHECK_STATUS : CHECK_STATUS
		             			},
		             			dataType : "json",
		             			success : function(data) {
		             				if(data.result=='success'){
		             					layer.msg('操作成功',{skin : 0,offset: [($(window).height())/8, ($(window).width())/2.2]});
		             					//选择的入学年份
		             					var grade=$('#grade').val();
		             					//院校专业pkids
		             					var orgtree=$('#orgtree').val();
		             					//审核状态
		             					var CHECK_STATUS = $('#CHECK_STATUS').val();
		             					//关键词
		             					var keywords = $("#keywords").val();
		             					$("#changeDepCheckTable").bootstrapTable('refresh', {url: encodeURI(_basepath+"changeDepCheck/changeDepCheckTable.json?grade="+grade+"&&orgtree="+orgtree+"&&CHECK_STATUS="+CHECK_STATUS+"&keywords="+keywords)});
		             				}
		             			}
		             		});
			  	}
	              },{
				    label: '取消',
				    cssClass: 'btn-default',
				    action: function(dialogRef){
				       dialogRef.close();
				    }
	              }]
	    });
	};
	
	//批量审核
	changeDepCheck.updatebatch = function(PKIDS,CHECK_STATUS){
		var message = "";
		if(CHECK_STATUS=='1'){
			message="确定审核通过吗？";
		}else{
			message="确定审核不通过吗？";
		}
		BootstrapDialog.show({  
		 	 	title: '提示信息',
	            message: message,
	            size :BootstrapDialog.SIZE_NORMAL,
	            closable: true, 
	              buttons: [{
				    label: '确定',
				    cssClass: 'btn btn-danger',
				    action: function(dialogRef){
		                    dialogRef.close();
		                    var url = _basepath+"changeDepCheck/updatebatch.json?PKIDS="+PKIDS;
		             		$.ajax({
		             			type : 'POST',
		             			url : url,
		             			async : false,
		             			data : {
		             				CHECK_STATUS : CHECK_STATUS
		             			},
		             			dataType : "json",
		             			success : function(data) {
		             				if(data.result=='success'){
		             					layer.msg('操作成功',{skin : 0,offset: [($(window).height())/8, ($(window).width())/2.2]});
		             					//选择的入学年份
		             					var grade=$('#grade').val();
		             					//院校专业pkids
		             					var orgtree=$('#orgtree').val();
		             					//审核状态
		             					var CHECK_STATUS = $('#CHECK_STATUS').val();
		             					//关键词
		             					var keywords = $("#keywords").val();
		             					$("#changeDepCheckTable").bootstrapTable('refresh', {url: encodeURI(_basepath+"changeDepCheck/changeDepCheckTable.json?grade="+grade+"&&orgtree="+orgtree+"&&CHECK_STATUS="+CHECK_STATUS+"&keywords="+keywords)});
		             				}
		             			}
		             		});
			  	}
	              },{
				    label: '取消',
				    cssClass: 'btn-default',
				    action: function(dialogRef){
				       dialogRef.close();
				    }
	              }]
	    });
	};
	
	// 获取bootStrapTable表格数据,及参数设置
	changeDepCheck.getTab = function() {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		var url = _basepath + "changeDepCheck/changeDepCheckTable.json";
		$('#changeDepCheckTable').bootstrapTable(
						{
							url : url,// 数据源
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
								//选择的入学年份
								var grade=$('#grade').val();
								//院校专业pkids
								var orgtree=$('#orgtree').val();
								//审核状态
								var CHECK_STATUS = $('#CHECK_STATUS').val();
								//关键词
								var keywords = $("#keywords").val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									keywords : keywords,
									CHECK_STATUS : CHECK_STATUS,
									orgtree : orgtree,
									grade : grade,
									sortName : this.sortName,
									sortOrder : this.sortOrder,
									pageindex : this.pageNumber,
									YX:$("#yx").val()
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
										valign : "middle",//垂直
										formatter:function(value, row, index){
											   // 根据row.列名   那状态确定返回 true/false
											if(row.CHECK_STATUS=='审核成功' || row.CHECK_STATUS=='审核失败' || row.CHECK_STATUS=='已结束' || row.CHECK_STATUS=='已过期' ){
												  return {
														 disabled : true
														   };
											}
										 }
									},
									{
										field: 'PKID',//可不加  
										align : "center",
										halign : 'center'
									},
									{
										field : 'SHENFENZHENGHAO',
										title : '身份证号',
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
										field : 'NIANJI',
										title : '入学年份',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'XINGBIE',
										title : '性别',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'SHOUJI',
										title : '联系方式',
										align : "right",
										halign : 'center',
										sortable : false
									},
									{
										field : 'XUEHAO',
										title : '学号',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'XUESHENGLEIXING',
										align : "center",
										halign : 'center',
										title : '学生类型',
										width:'150px'
											
									},

									{
										field : 'OLD_DEPARTMENT_PKID',
										title : '调整前专业',
										align : "left",
										halign : 'center',
										sortable : false

									},

									{
										field : 'NEW_DEPARTMENT_PKID',
										title : '调整后专业',
										align : "left",
										halign : 'center',
										sortable : false

									},
									{
										field : 'APPLY_DATE',
										title : '申请时间',
										align : "center",
										halign : 'center',
										sortable : false

									},
									{
										field : 'CHECK_STATUS',
										title : '审核状态',
										align : "center",
										halign : 'center',
										sortable : false

									},
									{
										field : 'CHECK_DATE',
										title : '审核日期',
										align : "center",
										halign : 'center',
										sortable : false

									},
									{
										field : 'USERNAME',
										title : '审核人',
										align : "center",
										halign : 'center',
										sortable : false

									},
									{
										field : 'opt',
										title : '操作',
										align : "center",
										halign : 'center',
										formatter : function(value, row, index) {
											if(row.CHECK_STATUS=='待审核'){
												return [
												        '<span title="查看" class="fa fa-search detail" style=" cursor:pointer;"></span>&nbsp;&nbsp;',
												        SESSION_MENU_BUTTONS.tzzysh_tg == 1?'<span title="通过" class="glyphicon glyphicon-ok pass" style=" cursor:pointer;"></span>&nbsp;&nbsp;':'',
												        		SESSION_MENU_BUTTONS.tzzysh_btg == 1?'<span title="不通过" class="glyphicon glyphicon-remove dispass" style=" cursor:pointer;"></span>':'']
												.join('');
											}else{
												return [
												        '<span title="查看" class="fa fa-search detail" style=" cursor:pointer;"></span>&nbsp;&nbsp;']
												.join('');
											}
										}, // 紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										events : {
											'click .detail' : function(e,value, row, index) {
												//查看详情注册事件，去学生详情页面
												$(".jf_szright").load(_basepath+'changeDepCheck/goDetail.json?PKID='+row.STUDENT_PKID);
											},
											'click .pass' : function(e,value, row, index) {
												//通过注册事件,更改为已审核通过
												changeDepCheck.updatePass(row.PKID,'1');
											},
											'click .dispass' : function(e,value, row, index) {
												//不通过注册事件,更改为已审核失败
												changeDepCheck.updatePass(row.PKID,'2');
											},
										}
									} ],
						});
		$('#changeDepCheckTable').bootstrapTable('hideColumn', 'PKID');//隱藏列
	};
	
	//加载表格数据
	changeDepCheck.getTab();
	$('#xslx').multiselect({
		 enableFiltering: true,
		 includeSelectAllOption: true,
		 selectAllText: ' 全选',
		 nonSelectedText: '请选择学生类型',
		 allSelectedText: '全部选择',
		 selectAllJustVisible: true,
		 filterPlaceholder:'请输入学生类型',
		 buttonWidth: '183px'//设置字体过长显示...
	 });
})(jQuery, window);
