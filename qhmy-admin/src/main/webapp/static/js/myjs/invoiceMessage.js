//@ sourceURL=invoiceMessage.js
(function($, window) {
	var InvoiceTable = {};
	
	
	
	
	
	//获取选中的行
	InvoiceTable.getIdSelections=function () {
			var $table = $('#InvoiceTable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.PKID;
        });
	};
	
	
	
	$("#zuofei").click(function(){
		var pkids=InvoiceTable.getIdSelections();
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			  BootstrapDialog.show({ // 显示需要提交的表单。
				title : '提示信息',
				message : '请至少选择一条数据!',
				buttons : [ {
					label : '关闭',
					cssClass : 'btn-danger',
					action : function(dialogRef) {
						dialogRef.close();
					}
				} ]
			  });
			  return false;
		}else{
			BootstrapDialog.show({
				title : '作废',
				message : $('<div></div>').load(_basepath+'report/zuofeiInvoiceNum.json?pkids='+pkids+'&TISHI=zuofei'),
				closable : true,
				buttons : [
				           {
				        	   label : '确定',
				        	   cssClass : 'btn-danger',
				        	   action : function(dialogRef) {
				        		   InvoiceTable.invoicezf(dialogRef,pkids);
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
	});
	
	
	$("#chongqi").click(function(){
		var pkids=InvoiceTable.getIdSelections();
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			  BootstrapDialog.show({ // 显示需要提交的表单。
				title : '提示信息',
				message : '请至少选择一条数据!',
				buttons : [ {
					label : '关闭',
					cssClass : 'btn-danger',
					action : function(dialogRef) {
						dialogRef.close();
					}
				} ]
			  });
			  return false;
		}else{
			BootstrapDialog.show({
				title : '重启',
				message : $('<div></div>').load(_basepath+'report/zuofeiInvoiceNum.json?pkids='+pkids+'&TISHI=chongqi'),
				closable : true,
				buttons : [
				           {
				        	   label : '确定',
				        	   cssClass : 'btn-danger',
				        	   action : function(dialogRef) {
				        		   InvoiceTable.invoicechongqi(dialogRef,pkids);
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
	});
	
	
	
	   $("#lingyong").click(function(){
			var pkids=InvoiceTable.getIdSelections();
			var czid = $("input[name='id']:checked").val();
			if(typeof(czid)=="undefined"){
				  BootstrapDialog.show({ // 显示需要提交的表单。
					title : '提示信息',
					message : '请至少选择一条数据!',
					buttons : [ {
						label : '关闭',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							dialogRef.close();
						}
					} ]
				  });
				  return false;
			}else{
				BootstrapDialog.show({
					title : '领用',
					message : $('<div></div>').load(_basepath+'report/lingyongInvoiceNum.json?pkids='+pkids),
					closable : true,
					buttons : [
					           {
					        	   label : '确定',
					        	   cssClass : 'btn-danger',
					        	   action : function(dialogRef) {
					        		   InvoiceTable.invoicelingyong(dialogRef,pkids);
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
		});
	    
	
	   $("#tuiling").click(function(){
			var pkids=InvoiceTable.getIdSelections();
			var czid = $("input[name='id']:checked").val();
			if(typeof(czid)=="undefined"){
				  BootstrapDialog.show({ // 显示需要提交的表单。
					title : '提示信息',
					message : '请至少选择一条数据!',
					buttons : [ {
						label : '关闭',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							dialogRef.close();
						}
					} ]
				  });
				  return false;
			}else{
				BootstrapDialog.show({
					title : '退领',
					message : $('<div></div>').load(_basepath+'report/tuilingInvoiceNum.json?pkids='+pkids+'&TISHI=tuiling'),
					closable : true,
					buttons : [
					           {
					        	   label : '确定',
					        	   cssClass : 'btn-danger',
					        	   action : function(dialogRef) {
					        		   InvoiceTable.invoicetuiling(dialogRef,pkids);
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
		});
	   
	   $("#shanchu").click(function(){
			var pkids=InvoiceTable.getIdSelections();
			var czid = $("input[name='id']:checked").val();
			if(typeof(czid)=="undefined"){
				  BootstrapDialog.show({ // 显示需要提交的表单。
					title : '提示信息',
					message : '请至少选择一条数据!',
					buttons : [ {
						label : '关闭',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							dialogRef.close();
						}
					} ]
				  });
				  return false;
			}else{
				BootstrapDialog.show({
					title : '删除',
					message : $('<div></div>').load(_basepath+'report/shanchuInvoiceNum.json?pkids='+pkids),
					closable : true,
					buttons : [
					           {
					        	   label : '确定',
					        	   cssClass : 'btn-danger',
					        	   action : function(dialogRef) {
					        		   InvoiceTable.invoiceshanchu(dialogRef,pkids);
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
		});
	
	
	
    $("#btn_addinvoice").click(function(){
		var dialog = BootstrapDialog.show({
			title:'新增发票',
			message: $('<div></div>').load("report/addInvoiceNum.json"),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
                label: '确定',
                cssClass: 'btn-danger',
                action: function(dialog) {
                	var checkRst = InvoiceTable.formcheck();
                	if(checkRst){
                		//保存
                		InvoiceTable.save(dialog);
                	}
                }
            }, {
                label: '取消',
                cssClass: 'btn-default',
                action: function(dialog) {
                	dialog.close();
                }
            }],
            onshow: function(dialogRef){
            	//内容加载完成，但是弹出层UI还没有显示时调用
            },
            onshown: function(dialogRef){
            	//内容加载完成，弹出层UI显示时调用
            },
            onhide: function(dialogRef){
            	//将要关闭时调用。
            },
            onhidden: function(dialogRef){
            	//UI关闭后低啊用
            }
		});
		//dialog.setSize(BootstrapDialog.SIZE_WIDE);//设置宽度
	});
	
	
 
    
    
    
	
    InvoiceTable.formcheck = function(){
		
    	
    	
		var fapiaohaoinputk = $("#fapiaohaoinputk").val();//发票号开始
		var fapiaohaoinputz = $("#fapiaohaoinputz").val();//发票号结束
		var qianzhuiinput = $("#qianzhuiinput").val();  //前缀
		
		var checkval=$("input[name='invoiceradio']:checked").val();
		var reg = new RegExp("[0-9]+");
		
		if(checkval=="1"){//单个添加
			if(fapiaohaoinputk == null || fapiaohaoinputk.trim() == ""){
				layer.msg("发票号不能为空!");
				return false;
			}
			
			if(!reg.test(fapiaohaoinputk)){
				layer.msg("发票号只能包含数字!");
				return false;
			}
		}
		if(checkval=="2"){//批量添加
			if(fapiaohaoinputk == null || fapiaohaoinputk.trim() == ""){
				layer.msg("发票号开始不能为空!");
				return false;
			}
			if(fapiaohaoinputz == null || fapiaohaoinputz.trim() == ""){
				layer.msg("发票号截止不能为空!");
				return false;
			}
			if(!reg.test(fapiaohaoinputk)){
				layer.msg("发票号开始只能包含数字!");
				return false;
			}
			if(!reg.test(fapiaohaoinputz)){
				layer.msg("发票号截止只能包含数字!");
				return false;
			}
			
			if(fapiaohaoinputz<fapiaohaoinputk){
				layer.msg("发票号截止必须大于发票号开始!");
				return false;
			}
		}
		
		//var reg1 = new RegExp("[0-9a-zA-Z]+");
		var repPass = /^[0-9a-zA-Z]*$/g;
		if(qianzhuiinput != null && qianzhuiinput.trim() != ""){
			if(!repPass.test(qianzhuiinput)){
				layer.msg("前缀只能包含数字和字母!");
				return false;
			}
			
		}
		
		
	/*	var userid=$("#orgtree").val();
		if(userid == null || userid.trim() == ""){
			layer.msg("请选择领用人!");
			return false;
		}*/
		/*if(department_name.length>25){
			layer.msg("院校专业名称长度不能超过25个字符!");
			return false;
		}
		
		var bianma = $("#BIANMA").val();
		if(bianma == null || bianma.trim() == ""){
			layer.msg("院校专业编码不能为空!");
			return false;
		}
		var reg = new RegExp("[0-9]+");
		if(!reg.test(bianma)){
			layer.msg("院校专业编码只能包含数字!");
			return false;
		}
		
		if(bianma.length>10){
			layer.msg("院校专业编码长度不能超过10个字符!");
			return false;
		}*/
		//验证学生类型	
	
		var flag=false;
		var url = _basepath+"report/getlistbyfph.json";
		$.ajax({
			 	type: 'post',
			    url: url,
			    async:false,
			    data:{QINAZHUI:qianzhuiinput,FPHQ:fapiaohaoinputk,FPHZ:fapiaohaoinputz,RADIOVAL:checkval},
			    dataTpye:"json",
			    success: function(data) {
			    	if(data.result==true){
			    		flag=true;
					}else{
						
			    	}
			    }
		});
		
		if(flag){
			layer.msg("发票号已经存在,请更换!");
			return false;
		}
		
		return true;
	};
	
    InvoiceTable.formcheckedit = function(){
		
    	
    	var yfapiaohaoinputk = $("#yfapiaohaoinputk").val();
		var fapiaohaoinputk = $("#editfapiaohaoinputk").val();//发票号开始
		var editqianzhuiinput = $("#editqianzhuiinput").val();
		
			if(fapiaohaoinputk == null || fapiaohaoinputk.trim() == ""){
				layer.msg("发票号不能为空!");
				return false;
			}
			
			var repPass = /^[0-9a-zA-Z]*$/g;
			if(editqianzhuiinput != null && editqianzhuiinput.trim() != ""){
				if(!repPass.test(editqianzhuiinput)){
					layer.msg("前缀只能包含数字和字母!");
					return false;
				}
				
			}
			
		var flag=false;
		var url = _basepath+"report/getlistbyfphedit.json";
		$.ajax({
			 	type: 'post',
			    url: url,
			    async:false,
			    data:{FPHQ:fapiaohaoinputk,qianzhui:editqianzhuiinput,YFPHQ:yfapiaohaoinputk},
			    dataTpye:"json",
			    success: function(data) {
			    	if(data.result==true){
			    		flag=true;
					}else{
						
			    	}
			    }
		});
		
		if(flag){
			layer.msg("发票号已经存在,请更换!");
			return false;
		}
		
		return true;
	};
	/**
	 * 保存
	 */
	InvoiceTable.save = function(dialog){
		
		var fapiaohaoinputk = $("#fapiaohaoinputk").val();//发票号开始
		var fapiaohaoinputz = $("#fapiaohaoinputz").val();//发票号结束
		var qianzhuiinput = $("#qianzhuiinput").val();  //前缀
		var checkval=$("input[name='invoiceradio']:checked").val();
		var userid=$("#orgtree").val();
		
		var datas = new Array();
		datas.push({"name":"QINAZHUI","value":qianzhuiinput});
		datas.push({"name":"FPHQ","value":fapiaohaoinputk});
		datas.push({"name":"FPHZ","value":fapiaohaoinputz});
		datas.push({"name":"RADIOVAL","value":checkval});
		datas.push({"name":"LEASER_USER","value":userid});
		
		var saveUrl = _basepath+"report/invoice-save.json";
		$.ajax({
			 type: 'post',
			    url: saveUrl,
			    data: datas,
			    async:false,
			    dataTpye:"json",
			    success: function(data) {
			    	if(data.result==true){
			    		layer.msg("保存成功!");
			    		//$("#departmentTreeJsonDataDiv").val(data.departmentTreeJsonData);//左侧树json数据
			    		//page.loadTree(parent_pkid);//加载左侧树
			    		//page.loadTable();//加载表格
			    		//page.refTable("departmentTable", null);
			    		dialog.close();
			    		$("#InvoiceTable").bootstrapTable('destroy');
			    		InvoiceTable.getTab();
			    		//$("#departmentTable").bootstrapTable('refresh', opt);//刷新表格
			    		//刷新页面
					}else{
						layer.msg("保存失败!");
			    	}
			    }
		});
	};
	
	
	
	
	/**
	 * 作废
	 */
	InvoiceTable.invoicezf = function(dialog){
		var pkids=InvoiceTable.getIdSelections();
		var czid = $("input[name='id']:checked").val();
		var datas = new Array();
		datas.push({"name":"pkids","value":pkids});
		var saveUrl = _basepath+"report/invoice-zuofei.json";
		$.ajax({
			 type: 'post',
			    url: saveUrl,
			    data: datas,
			    async:false,
			    dataTpye:"json",
			    success: function(data) {
			    	if(data.result==true){
			    		layer.msg("作废成功!");
			    		dialog.close();
			    		$("#InvoiceTable").bootstrapTable('destroy');
			    		InvoiceTable.getTab();
					}else{
						layer.msg("作废失败!");
			    	}
			    }
		});
	};
	
	
	/**
	 * 领用
	 */
	InvoiceTable.invoicelingyong = function(dialog){
		var pkids=InvoiceTable.getIdSelections();
		var czid = $("input[name='id']:checked").val();
		var userid=$("#orgtree").val();
		if(userid == null || userid.trim() == ""){
			layer.msg("请选择领用人!");
			return false;
		}
		var datas = new Array();
		datas.push({"name":"pkids","value":pkids});
		datas.push({"name":"LEASER_USER","value":userid});
		var saveUrl = _basepath+"report/invoice-lingyong.json";
		$.ajax({
			 type: 'post',
			    url: saveUrl,
			    data: datas,
			    async:false,
			    dataTpye:"json",
			    success: function(data) {
			    	if(data.result==true){
			    		layer.msg("领用成功!");
			    		dialog.close();
			    		$("#InvoiceTable").bootstrapTable('destroy');
			    		InvoiceTable.getTab();
					}else{
						layer.msg("领用失败!");
			    	}
			    }
		});
	};
	
	
	/**
	 * 重启
	 */
	InvoiceTable.invoicechongqi = function(dialog){
		var pkids=InvoiceTable.getIdSelections();
		var czid = $("input[name='id']:checked").val();
		var datas = new Array();
		datas.push({"name":"pkids","value":pkids});
		var saveUrl = _basepath+"report/invoice-chongqi.json";
		$.ajax({
			 type: 'post',
			    url: saveUrl,
			    data: datas,
			    async:false,
			    dataTpye:"json",
			    success: function(data) {
			    	if(data.result==true){
			    		layer.msg("重启成功!");
			    		dialog.close();
			    		$("#InvoiceTable").bootstrapTable('destroy');
			    		InvoiceTable.getTab();
					}else{
						layer.msg("重启失败!");
			    	}
			    }
		});
	};
	
	/**
	 * tuiling
	 */
	InvoiceTable.invoicetuiling = function(dialog){
		var pkids=InvoiceTable.getIdSelections();
		var czid = $("input[name='id']:checked").val();
		var datas = new Array();
		datas.push({"name":"pkids","value":pkids});
		var saveUrl = _basepath+"report/invoice-tuiling.json";
		$.ajax({
			 type: 'post',
			    url: saveUrl,
			    data: datas,
			    async:false,
			    dataTpye:"json",
			    success: function(data) {
			    	if(data.result==true){
			    		layer.msg("退领成功!");
			    		dialog.close();
			    		$("#InvoiceTable").bootstrapTable('destroy');
			    		InvoiceTable.getTab();
					}else{
						layer.msg("退领失败!");
			    	}
			    }
		});
	};
	
	InvoiceTable.invoiceshanchu = function(dialog){
		var pkids=InvoiceTable.getIdSelections();
		var czid = $("input[name='id']:checked").val();
		var datas = new Array();
		datas.push({"name":"pkids","value":pkids});
		var saveUrl = _basepath+"report/invoice-shanchu.json";
		$.ajax({
			 type: 'post',
			    url: saveUrl,
			    data: datas,
			    async:false,
			    dataTpye:"json",
			    success: function(data) {
			    	if(data.result==true){
			    		layer.msg("删除成功!");
			    		dialog.close();
			    		$("#InvoiceTable").bootstrapTable('destroy');
			    		InvoiceTable.getTab();
					}else{
						layer.msg("删除失败!");
			    	}
			    }
		});
	};
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//日期插件参数设置
	InvoiceTable.InvoiceTable=function(){
		laydate({
			   isclear: false, //是否显示清空
			   istoday: true, //是否显示今天
			   festival: true, //显示节日
			   istime:true,
			   format : 'YYYY-MM-DD hh:mm:ss',
			   max: laydate.now(), //最大日期
		});
	};
	
	//日期初始化
	$('.laydate-icon').click(function(){
		InvoiceTable.InvoiceTable();
	});
	
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		$("#InvoiceTable").bootstrapTable('destroy');
		InvoiceTable.getTab();
	});
	
	//点击按钮导出
	$("#checkOut").click(function(){
		//选择的日期起
		var DATESTART=$('#DATESTART').val();
		//选择的日期止
		var DATEEND=$('#DATEEND').val();
		//院校名称
		var DPKID=$('#orgtree').val();
		var CENGCI_PKID= $("#CENGCI_PKID option:selected").val();
		var PICI_PKID=$("#PICI_PKID option:selected").val();
		var PAYITEM=$("#PAYITEM").val();
		var XINGMING=$("#XINGMING").val();
		var NIANJI=$("#NIANJI option:selected").val();
		window.location.href=encodeURI(_basepath+'report/tradeSumExcel.json?DATESTART='+DATESTART
				+"&&DATEEND="+DATEEND+"&&DPKID="+DPKID+"&&CENGCI_PKID="+CENGCI_PKID
				+"&&PICI_PKID="+PICI_PKID+"&&PAYITEM="+PAYITEM
				+"&&XINGMING="+XINGMING
				+"&&NIANJI="+NIANJI);
	});
	
	//点击按钮返回
	$("#checkReturn").click(function(){
		var url = _basepath + "report/toTradeSum.php";
		$('.jf_szright').load(url);
	});
	
	// 获取bootStrapTable表格数据,及参数设置
	InvoiceTable.getTab = function() {
		var url = _basepath + "report/invoiceTable.json";
		$('#InvoiceTable').bootstrapTable(
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
								var zhuangtai=$('#zhuangtai').val();
								//院校专业pkids
								var orgtree=$('#orgtree1').val();
								//审核状态
								var CHECK_STATUS = $('#CHECK_STATUS').val();
								//关键词
								var keywords = $("#keywords").val();
								var fapiaohao = $("#fapiaohao").val();
								
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									keywords : keywords,
									FAPIAOHAO : fapiaohao,
									ZHUANGTAI : zhuangtai,
									LEASER_USER : orgtree,
									sortName : this.sortName,
									sortOrder : this.sortOrder,
									pageindex : this.pageNumber,
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
										 formatter : function(value, row,
													index) {
							                	if(row.STATUS=='作废'){
							                		return {
														 disabled : true
														   };
							                	}
											}
									},
									{
										field : 'PKID',// 可不加
										align : "center",
										halign : 'center'
									},
									{
										field : 'INVOICE_NO',
										title : '发票号',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'USERNAME',
										title : '领用人',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'STATUS',
										title : '使用状态',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'REGISTER_DATE',
										title : '登记时间',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'DENGJIREN',
										title : '登记人',
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
											
											if(row.STATUS == '已使用'){
												return [
												        '<a class="zuofei ml10" href="javascript:void(0)" title="作废"><i class="fa" >作废</i></a>&nbsp;&nbsp;',
												        '<a class="chongqi ml10" href="javascript:void(0)" title="重启"><i class="fa">重启</i></a>'
														]
														.join('');
											}else if(row.STATUS == '未使用' && row.USERNAME!=""&& row.USERNAME!="null"&& row.USERNAME!=null){
												return [
												        '<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa" >修改</i></a>&nbsp;&nbsp;',
												        '<a class="shanchu ml10" href="javascript:void(0)" title="删除"><i class="fa" >删除</i></a>&nbsp;&nbsp;',
												        '<a class="tuiling ml10" href="javascript:void(0)" title="退领"><i class="fa" >退领</i></a>&nbsp;&nbsp;'
												        
														]
														.join('');
											}else if((row.USERNAME=="" || row.USERNAME=="null" || row.USERNAME==null)&&row.STATUS != '作废'){
												return [
														'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa" >修改</i></a>&nbsp;&nbsp;',
														'<a class="shanchu ml10" href="javascript:void(0)" title="删除"><i class="fa" >删除</i></a>&nbsp;&nbsp;'
												        
														]
														.join('');
											}
											
											
										},
										events : {
											'click .zuofei' : function(e,value, row, index) {
												BootstrapDialog.show({
													title : '作废',
													message : '确定作废该发票号?',
													closable : true,
													buttons : [
													           {
													        	   label : '确定',
													        	   cssClass : 'btn-danger',
													        	   action : function(dialogRef) {
													        		   InvoiceTable.invoicezf(dialogRef);
													        	   }
													           }, {
													        	   label : '取消',
													        	   cssClass : 'btn-default',
													        	   action : function(dialogRef) {
													        		   dialogRef.close();
													        	   }
													           } ]
												});
											},
											'click .edit' : function(e,value, row, index) {
												BootstrapDialog.show({
												title:'修改',
												message: $('<div></div>').load("report/editInvoiceNum.json?PKID="+row.PKID),
												closable: true,//是否显示叉号
												draggable: true,//可以拖拽
												buttons: [{
									                label: '确定',
									                cssClass: 'btn-danger',
									                action: function(dialog) {
									                	var checkRst = InvoiceTable.formcheckedit();
									                	if(checkRst){
									                		//保存
									                		InvoiceTable.editsave(dialog,row.PKID);
									                	}
									                }
									            }, {
									                label: '取消',
									                cssClass: 'btn-default',
									                action: function(dialog) {
									                	dialog.close();
									                }
									            }],
									            onshow: function(dialogRef){
									            	//内容加载完成，但是弹出层UI还没有显示时调用
									            },
									            onshown: function(dialogRef){
									            	//内容加载完成，弹出层UI显示时调用
									            },
									            onhide: function(dialogRef){
									            	//将要关闭时调用。
									            },
									            onhidden: function(dialogRef){
									            	//UI关闭后低啊用
									            }
												});
											},
											'click .shanchu' : function(e,value, row, index) {
												BootstrapDialog.show({
													title : '删除',
													message : '确定删除该发票号?',
													closable : true,
													buttons : [
													           {
													        	   label : '确定',
													        	   cssClass : 'btn-danger',
													        	   action : function(dialogRef) {
													        		   InvoiceTable.invoiceshanchu(dialogRef);
													        	   }
													           }, {
													        	   label : '取消',
													        	   cssClass : 'btn-default',
													        	   action : function(dialogRef) {
													        		   dialogRef.close();
													        	   }
													           } ]
												});
											},
											'click .tuiling' : function(e,value, row, index) {
												BootstrapDialog.show({
													title : '退领',
													message : '确定退领该发票号?',
													closable : true,
													buttons : [
													           {
													        	   label : '确定',
													        	   cssClass : 'btn-danger',
													        	   action : function(dialogRef) {
													        		   InvoiceTable.invoicetuiling(dialogRef);
													        	   }
													           }, {
													        	   label : '取消',
													        	   cssClass : 'btn-default',
													        	   action : function(dialogRef) {
													        		   dialogRef.close();
													        	   }
													           } ]
												});
											},
											'click .chongqi' : function(e,value, row, index) {
												BootstrapDialog.show({
													title : '重启',
													message : '确定重启该发票号?',
													closable : true,
													buttons : [
													           {
													        	   label : '确定',
													        	   cssClass : 'btn-danger',
													        	   action : function(dialogRef) {
													        		   InvoiceTable.invoicechongqi(dialogRef);
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
										}
									}],
						});
		
		
		
		
		 $('#InvoiceTable').bootstrapTable('hideColumn', 'PKID');//隱藏列
	};
	
	InvoiceTable.editsave = function(dialog,pkid){
		
		var fapiaohaoinputk = $("#editfapiaohaoinputk").val();//发票号开始
		var editqianzhuiinput = $("#editqianzhuiinput").val();
		
		var datas = new Array();
		datas.push({"name":"PKID","value":pkid});
		datas.push({"name":"FPHQ","value":fapiaohaoinputk});
		datas.push({"name":"qianzhui","value":editqianzhuiinput});
		
		var saveUrl = _basepath+"report/invoice-saveedit.json";
		$.ajax({
			 type: 'post',
			    url: saveUrl,
			    data: datas,
			    async:false,
			    dataTpye:"json",
			    success: function(data) {
			    	if(data.result==true){
			    		layer.msg("保存成功!");
			    		//$("#departmentTreeJsonDataDiv").val(data.departmentTreeJsonData);//左侧树json数据
			    		//page.loadTree(parent_pkid);//加载左侧树
			    		//page.loadTable();//加载表格
			    		//page.refTable("departmentTable", null);
			    		dialog.close();
			    		$("#InvoiceTable").bootstrapTable('destroy');
			    		InvoiceTable.getTab();
			    		//$("#departmentTable").bootstrapTable('refresh', opt);//刷新表格
			    		//刷新页面
					}else{
						layer.msg("保存失败!");
			    	}
			    }
		});
	};
	//加载表格数据
	InvoiceTable.getTab();
})(jQuery, window);
