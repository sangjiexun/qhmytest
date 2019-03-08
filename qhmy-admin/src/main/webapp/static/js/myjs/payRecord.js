/**
 * 交易记录对象
 */
//@ sourceURL=payRecord.js
(function($, window) {
	var payRecord = {};
	
	//日期插件参数设置
	laydate.render({
		elem:'#DATESTART',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'datetime',
		format: 'yyyy-MM-dd HH:mm:ss',
		festival: true, //显示节日
		start: 0
	});
	laydate.render({
		elem:'#DATEEND',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'datetime',
		format: 'yyyy-MM-dd HH:mm:ss',
		festival: true, //显示节日
		start: 0
	});
	
	//获取选中的行
	payRecord.getIdSelections=function () {
			var $table = $('#payRecordTable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.PKID;
        });
	};
	
	//通过按钮点击事件
	$("#btn_tongguo").click(function(){
		var pkids=payRecord.getIdSelections();
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
	  }
	  ]
        }); 
			return false;
		}
		  BootstrapDialog.show({  //显示需要提交的表单。
          	title:'提示信息',	
          message: '你确定要通过吗？',
          closable: false, 
            buttons: [{
			    label: '确定',
			    cssClass: 'btn-danger',
			    action: function(dialogRef){
			    	payRecord.batchTongGuo(pkids);
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
	
	//批量通过方法
	payRecord.batchTongGuo=function(pkids){
		$.post(_basepath+"payRecord/batchTongGuo.json?pkids="+pkids,function(data){
			if(data.result=="success"){
				layer.msg("操作成功!");
				$("#payRecordTable").bootstrapTable('destroy');
				payRecord.getTab();
			}
		});
	}
	
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		$("#payRecordTable").bootstrapTable('destroy');
		payRecord.getTab();
	});
	
	//点击按钮导出
	$("#checkOut").click(function(){
		//选择的日期起
		var DATESTART=$('#DATESTART').val();
		//选择的日期止
		var DATEEND=$('#DATEEND').val();
		//院校名称
		var TREENAME=$('#orgtree').val();
		//费用类型
		var PAY_TYPE=$("#PAY_TYPE").val();
		//关键词
		var keywords = $("#keywords").val();
		//学生PKID
		var stuPkid = $("#stuPkid").val();
		window.location.href=encodeURI(_basepath+'payRecord/payRecordExcel.json?DATESTART='+DATESTART
				+"&&DATEEND="+DATEEND+"&&TREENAME="+TREENAME+"&&PAY_TYPE="+PAY_TYPE
				+"&&keywords="+keywords+"&&stuPkid="+stuPkid);
	});
	//导入事件
	$("#btn_import_stu").click(function(){
		var dialog = BootstrapDialog.show({
			title:'导入交易记录',
			message: $('<div></div>').load("importRec/importpayRecord.json"),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
	            label: '关闭',
	            cssClass: 'btn-danger',
	            action: function(dialog) {
	            	dialog.close();
	            	$("#payRecordTable").bootstrapTable('destroy');
	        		payRecord.getTab();
	            }
	        }]
		});
	});
	
	//点击按钮返回
	$("#checkReturn").click(function(){
		var url = _basepath + "report/toTradeSum.php";
		$('.jf_szright').load(url);
	});
	
	//去编辑页面
	payRecord.goUpdate = function(pkid){
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '编辑交易记录',
			message : $('<div></div>').load(_basepath+'payRecord/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							payRecord.updateAdd(dialogRef);
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
	
	
	//新增按钮触发事件
	$("#btn_add").click(function(){
		var pkid = '';
		BootstrapDialog.show({ 
			title : '新增交易记录',
			message : $('<div></div>').load(_basepath+'payRecord/goEdit.json?PKID='+pkid),
			closable : true,
			buttons : [
					{
						label : '确定',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							payRecord.updateAdd(dialogRef);
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
	payRecord.updateAdd = function(dialogRef){
		var PKID = $("#PKID").val();
		var SHENFENZHENGHAO = $("#SHENFENZHENGHAO").val();
		var PAY_MODE = $("#PAY_MODE").val();
		var PAY_MONEY = $("#PAY_MONEY").val();
		var flag = payRecord.formcheck();
		if(flag){
			var STUDENT_PKID = $("#STUDENT_PKID").val();
			dialogRef.close();
			$.ajax({
				type : "post",
				dataType : "json",
				async: false,
				url : _basepath+ 'payRecord/edit.json',
				data : {"PKID":PKID,"SHENFENZHENGHAO":SHENFENZHENGHAO,"PAY_MODE":PAY_MODE,"PAY_MONEY":PAY_MONEY,"STUDENT_PKID":STUDENT_PKID},
				success : function(data) {
					if ("success" == data.rst) {
						layer.msg("操作成功！");
						$("#payRecordTable").bootstrapTable('destroy');
						payRecord.getTab();
					}
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					alert(errorThrown);
				}
			});
		}
	}
	
	//页面校验
	payRecord.formcheck = function(){
		var PKID = $("#PKID").val();
		var SHENFENZHENGHAO = $("#SHENFENZHENGHAO").val();
		var PAY_MONEY = $("#PAY_MONEY").val();
		if(SHENFENZHENGHAO==''){
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
			var flag = payRecord.validate(SHENFENZHENGHAO,PKID);
			if(!flag){
				$("#SHENFENZHENGHAO").focus();
				return false;
			}
		}
		var fix_amountTest=/^(([1-9]\d*)|\d)(\.\d{1,2})?$/;
		if(PAY_MONEY == ''){
			layer.msg("费用金额不能为空");
			$("#PAY_MONEY").focus();
			return false;
		}else if(!fix_amountTest.test(PAY_MONEY)){
			layer.msg("费用金额必须为数字，且最多保留两位小数");
			$("#PAY_MONEY").focus();
			return false;
		}else{
			return true;
		}
	}
	
	//验证身份证号是否存在
	payRecord.validate = function(SHENFENZHENGHAO,PKID){
		var flag = true;
		$.ajax({
			type : "post",
			dataType : "json",
			async: false,
			url : _basepath+ 'payRecord/getShenFenZheng.json',
			data : {"PKID":PKID,"SHENFENZHENGHAO":SHENFENZHENGHAO},
			success : function(data) {
				if ("success" == data.rst) {
					layer.msg("该身份证号不存在，请更换！");
					flag = false;
				}else{
					$("#STUDENT_PKID").val(data.STUDENT_PKID);
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
	
	// 获取bootStrapTable表格数据,及参数设置
	payRecord.getTab = function() {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		var url = _basepath + "payRecord/payRecordTable.json";
		$('#payRecordTable').bootstrapTable(
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
							showColumns : true, // 显示下拉框勾选要显示的列
							showRefresh : false, // 显示刷新按钮 --只是前台刷新，个人觉得没什么用
							minimumCountColumns : 2, // 最少允许的列数
							sidePagination : "server", // 表示服务端请求
							totalRows : 0, // server side need to set
							singleSelect : false,
							clickToSelect : true,
							onDblClickRow : function(row) {
							},
							queryParams : function getParams(params) {
								//选择的日期起
								var DATESTART=$('#DATESTART').val();
								//选择的日期止
								var DATEEND=$('#DATEEND').val();
								//院校名称
								var TREENAME=$('#orgtree').val();
								//费用类型
								var PAY_TYPE=$("#PAY_TYPE").val();
								//关键词
								var keywords = $("#keywords").val();
								//学生PKID
								var stuPkid = $("#stuPkid").val();
								//是否通过
								var IS_TONGGUO=$("#IS_TONGGUO").val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									DATESTART : DATESTART,
									DATEEND : DATEEND,
									TREENAME : TREENAME,
									PAY_TYPE : PAY_TYPE,
									keywords : keywords,
									stuPkid : stuPkid,
									IS_TONGGUO : IS_TONGGUO,
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
										field : 'DEP_NAME',
										title : '院校专业',
										align : "left",
										halign : 'center',
										sortable : false
									},
									{
										field : 'CENGCI_NAME',
										title : '学生类型',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'PICI_NAEM',
										title : '批次',
										align : "center",
										halign : 'center',
										sortable : false
									},

									{
										field : 'JIESHAOREN_NAME',
										title : '介绍人',
										align : "left",
										halign : 'center',
										sortable : false

									},

									{
										field : 'PAY_MODE',
										title : '费用类型',
										align : "center",
										halign : 'center',
										sortable : false

									},

									{
										field : 'PAY_MONEY',
										title : '费用金额',
										align : "right",
										halign : 'center',
										sortable : false

									},

									{
										field : 'CJSJ',
										title : '操作时间',
										align : "center",
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
											if(row.IS_TONGGUO == '通过'){//表示审核通过
												/*return [
														'<a class="cancel ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>' ]
														.join('');*/
											}else{
												return [
												        SESSION_MENU_BUTTONS.jyjl_bj == 1?'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;':'',
												        		SESSION_MENU_BUTTONS.jyjl_sc == 1?'<a class="cancel ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>':''
												        ]
												.join('');
											}
										}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										 events : {
											'click .edit' : function(e,value, row, index) {
												  //编辑  编辑人员
												payRecord.goUpdate(row.PKID);
	    							        
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
																url:_basepath+'payRecord/delete.json?PKID='+row.PKID,
																 success:function(data){
																	 if("success" == data.rst){
																		 layer.msg("删除成功！");
																		 $("#payRecordTable").bootstrapTable('destroy');
																		 payRecord.getTab();
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
	payRecord.getTab();
})(jQuery, window);
