//@ sourceURL=paytype_list.js 
/**
 * 学生信息页面对象
 */
//获得按钮权限
var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
(function($, window) {
	var paytype = {};

	// 去新增页面
	paytype.goAddStu = function() {
		BootstrapDialog.show({
			title : '新增缴费类型',
			message : $('<div></div>').load(_basepath+'paytype/paytype_add.json'),
			closable : true,
			buttons : [
			           {
			        	   label : '确定',
			        	   cssClass : 'btn-danger',
			        	   action : function(dialogRef) {
			        		   paytype.savePayType(dialogRef,"add");
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
	
	//获取选中的行
	paytype.getIdSelections=function () {
			var $table = $('#paytypetable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.PKID;
        });
	};
	//批量删除方法
	paytype.batchDel=function(e){
		$.post(_basepath+"stuinfo/batchDel.json?pkids="+e,function(data){
			if(data.result=="success"){
				layer.msg("删除成功!");
				 $("#paytypetable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}
			if(data.result=="paid"){
				layer.msg("您选择的信息包含已缴费的学生，不能删除!");
			}
		});
	};

	//批量通过方法
	paytype.batchTongGuo=function(pkids){
		$.post(_basepath+"stuinfo/batchTongGuo.json?pkids="+pkids,function(data){
			if(data.result=="success"){
				layer.msg("操作成功!");
				$("#paytypetable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}
		});
	}
	
	//新增按钮绑定单机事件
	$('#btn_add').bind('click',function(){
		paytype.goAddStu();
	});
	
	//查询
	$('#btn_search').click(function(){
		//所属组织
		$("#paytypetable").bootstrapTable('destroy'); 
		 paytype.getTab();
		
	});
	//通过按钮点击事件
	$("#btn_tongguo").click(function(){
		var pkids=paytype.getIdSelections();
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
			    paytype.batchTongGuo(pkids);
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
	
	$("#btn_import_stu").click(function(){
		var dialog = BootstrapDialog.show({
			title:'导入学生信息',
			message: $('<div></div>').load("importstu/importstuinfo.json"),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
	            label: '关闭',
	            cssClass: 'btn-danger',
	            action: function(dialog) {
	            	dialog.close();
	            	$('#paytypetable').bootstrapTable("refresh");
	            }
	        }]
		});
	});
	/**
	 * 修改学生在学状态
	 */
	paytype.editstustatus=function(a,b){
		$.post(_basepath+"stuinfo/editstustatus.json?pkids="+a+"&ZAIXUEZT="+b,function(data){
			if(data.result=="success"){
				layer.msg("修改成功!");
				 $("#paytypetable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}else{
				layer.msg("修改失败!");
			}
		});
	};
	//点击修改学生状态按钮
	$('#btn_editstatus').bind('click',function(){

		var pkids=paytype.getIdSelections();
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
		}
		
	 var opt='';
	 for(var i=0;i<zaixuezt.length;i++){
		 opt+='<option value="'+zaixuezt[i].BIANMA+'" >'+zaixuezt[i].NAME+'</option>'
	 }
	  var select_str ='<div  class="form-inline" style="margin-bottom: 25px;"><label class="jf_label" for="">在学状态:</label>'
                     +'<select name="" class="form-control jf_FltConditions" id="newzxstatus">'
                     +opt
	                 +'</select></div>';
	  BootstrapDialog.show({  //显示需要提交的表单。
        	title:'修改在学状态',	
        message: select_str,
        closable: true, 
          buttons: [{
			    label: '保存',
			    cssClass: 'btn-danger',
			    action: function(dialogRef){
			    	//修改在学状态
			    paytype.editstustatus(pkids,$('#newzxstatus').val())
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

	});
	
	//
	paytype.savePayType = function(dialogRef,a) {
		var pay_style_name = $("#pay_style_name").val();
		var oldname = $("#oldname").val();
		if (pay_style_name.trim() == '' || pay_style_name == null) {
			layer.msg("缴费类型名称不能为空!");
			return false;
		}
		var remark = $('#remark').val();
		var paixu=$('#paixu').val();
		if (paixu.trim() == '' || paixu == null) {
			layer.msg("排序不能为空!");
			return false;
		}
		 var reg = /^[0-9]\d*$/;
         if(!reg.test(paixu)) {
        	 layer.msg( '排序只能为正整数');
        	 return false;
         }
         
		var is_use = $('#is_use')[0].checked == true ? "Y" : "N";//$('#is_use')[0].checked == true ? "Y" : "N";

		$.post(_basepath + "paytype/save.json?", {
			pay_style_name : pay_style_name.trim(),
			remark : remark,
			is_use : is_use,
			edit:a,
			pkid:$("#pkid").val(),
			oldname:oldname,
			paixu:paixu,
			BILL_TYPE:$("#BILL_TYPE option:selected").val()

		}, function(data) {
			if (data.result == "success") {
				layer.msg("保存成功!");
				dialogRef.close();
				$("#paytypetable").bootstrapTable('refresh', {
					url : _basepath + "paytype/table.json"
				});
			}else if(data.result =="nameExisted"){
				layer.msg("缴费类型名称已存在!");
				return false;
			} 
			else {
				dialogRef.close();
				layer.msg("保存失败!");
			}
		});
	}
	
	//去调整专业页面
	$("#btn_tzzy").click(function(){
		var pkids=paytype.getIdSelections();
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
				title : '调整专业',
				message : $('<div></div>').load(_basepath+'stuinfo/goUpdateDep.json?pkids='+pkids),
				closable : true,
				buttons : [
				           {
				        	   label : '确定',
				        	   cssClass : 'btn-danger',
				        	   action : function(dialogRef) {
				        		   paytype.savePayType(dialogRef,pkids);
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
	
	
	
	//点击按钮导出
	$("#checkOut").click(function(){
		//所属组织
		var orgtree=$('#orgtree').val();
		//在学状态
		var zxzt=$('#zxzt').val();
		//年级
		var grade=$("#grade").val();
		//层次
		var cengci=$("#cengci").val();
		//批次
		var pici=$("#pici").val();
		//缴费金额起
		var moneyqi=$('#moneyqi').val();
		//缴费金额止
		var moneyzhi=$('#moneyzhi').val();
		//身份证号 学号 姓名
		var searchText=$('#search').val();
		//是否通过
		var IS_TONGGUO=$("#IS_TONGGUO").val();
		window.location.href=encodeURI(_basepath+'stuinfo/exportExcel.json?seText='+searchText+
				'&&DEPARTMENT_PKID='+orgtree+'&&MONEYQI='+moneyqi+
				"&&MONEYZHI="+moneyzhi+"&&ZXZT="+zxzt+"&&NIANJI="+grade+"&&CENGCI="+cengci+"&&PICI="+pici+
				"&&IS_TONGGUO="+IS_TONGGUO);
	});
	
	//批量删除按钮绑定单机事件
	$('#btn_del').bind('click',function(){

		var pkids=paytype.getIdSelections();
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
          message: '你确定要删除记录吗？',
          closable: false, 
            buttons: [{
			    label: '确定',
			    cssClass: 'btn-danger',
			    action: function(dialogRef){
			    paytype.batchDel(pkids);
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
	//获取list表格数据
	paytype.getTab=function () {
		//获得按钮权限
		// 引例四
		var url = _basepath+"paytype/table.json";

		$('#paytypetable').bootstrapTable(
						{
							method: 'post',  
							url : url,//数据源
							dataType: "json",
							contentType:"application/x-www-form-urlencoded; charset=UTF-8",
							dataField : "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
							totalField : 'total',
							sortOrder : 'desc',
							striped : true, //表格显示条纹  
							pagination : true, //启动分页  
							pageNumber : 1, //当前第几页  
							pageSize : 20, //每页显示的记录数  
							pageList : [1,5,10,20,30,50,100], //记录数可选列表  
							search : false, //是否启用查询  
							formatSearch : function() {
								return '请输入简拼.编码.企业名称查询';
							},//搜索框提示文字
							searchAlign : 'left',
							buttonsAlign : 'left',
							toolbarAlign : 'left',
							searchOnEnterKey : false,//true为按回车触发搜索事件
							showColumns : false, //显示下拉框勾选要显示的列  
							showRefresh : false, //显示刷新按钮  --只是前台刷新，个人觉得没什么用
							minimumCountColumns : 2, //最少允许的列数
							sidePagination : "server", //表示服务端请求  
							totalRows : 0, // server side need to set
							singleSelect : false,
							clickToSelect : true,
							onDblClickRow:function(row){
							},
							queryParams : function getParams(params) {
								var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, //页面大小
									seText : $('#search').val(),
									sortName : this.sortName,
									sortOrder : this.sortOrder,
									pageindex : this.pageNumber
								//当前页码
								};
								return temp;
							},
							 queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求  
							  silent: true,  //刷新事件必须设置  
							buttonsAlign : "right",//按钮对齐方式
							selectItemName : 'id',
							toolbar : "#toolbar",
							toolbarAlign : 'left',
							columns : [

									{
										field: 'PKID',//可不加  
										align : "center",
										halign : 'center'
									},
									{
										field : 'PAY_STYLE_BIANMA',
										title : '缴费类型编号',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'PAY_STYLE_NAME',
										title:'缴费类型名称',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'INTERFACE_NAME',
										title:'接口',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'DEFINE_TYPE',
										title:'定义类型',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'PAIXU',
										title:'排序',
										align : "center",
										halign : 'center',
										sortable : false,
										editable: {
						                    type: 'text',
						                    title: '排序',
						                    validate: function (v) {
						                        if (!v) return '排序不能为空';
						                        var reg = /^[0-9]\d*$/;
						                        if(!reg.test(v)) return '只能为正整数'

						                    }
						                }
									},
									{
										field : 'IS_USE',
										title : '是否启用',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter : function(value, row,
												index) {
											if(SESSION_MENU_BUTTONS.type_qy==1){
												if(row.DEFINE_TYPE=="自定义"){
														if(row.IS_USE=='Y'){
															return ['<input type="checkbox" class="al-toggle-button dj"checked="false" >' ].join('');
														}else{
															return ['<input type="checkbox" class="al-toggle-button dj" >' ].join('');
														}
												}else{
													if(row.IS_USE=='Y'){
														return ['<input type="checkbox" class="al-toggle-button dj"checked="false" >' ].join('');
													}else{
														return ['<input type="checkbox" class="al-toggle-button dj" >' ].join('');
													}
												}
											}
											
										},
										 events : {
												'click .dj' : function(e,value, row, index) {
													var is_use=e.currentTarget.checked==true?"Y":"N";//$(this)[0].checked==true?"Y":"N";
													$.post(_basepath+"paytype/save.json",{edit:'edit',pkid:row.PKID,is_use:is_use,pay_style_name:row.PAY_STYLE_NAME,remark:row.REMARK,paixu:row.PAIXU,
														BILL_TYPE:row.BILL_TYPE == null || row.BILL_TYPE == 'null' || row.BILL_TYPE == 'undefined' || typeof row.BILL_TYPE == 'undefined' ? row.BILL_TYPE : ""},function(data){
														if(data.result=="success"){
															layer.msg("修改成功!");
															 $("#paytypetable").bootstrapTable('refresh', {url:url});
														}
													});
												}
										 }
									},/*,
									{
										field : 'IS_DJLQ',
										title : '是否允许分宿',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter : function(value, row,
												index) {
											if(SESSION_MENU_BUTTONS.IS_DJLQ==1){
												if(row.IS_DJLQ=='Y'){
													return ['<input type="checkbox" class="al-toggle-button DJLQ" checked="false" >' ].join('');
												}else{
													return ['<input type="checkbox" class="al-toggle-button DJLQ" >' ].join('');
												}
											}
											
										}
										 events : {
												'click .DJLQ' : function(e,value, row, index) {
													var IS_DJLQ=$(this)[0].checked==true?"Y":"N";
													$.post(_basepath+"paytype/updateDJLQ.json",{pkid:row.PKID,IS_DJLQ:IS_DJLQ},function(data){
														if(data.result=="success"){
															layer.msg("修改成功!");
															 $("#paytypetable").bootstrapTable('refresh', {url:url});
														}
													});
												}
										 }
									},*/
									{
										field: 'REMARK',//可不加  
										title : '备注',//标题  可不加
										align : "center",
										halign : 'center'
									},
									{
										field : 'opt',
										title : '操作',
										align : "center",
										halign : 'center',
										formatter : function(value, row,
												index) {
											if(row.DEFINE_TYPE != '系统设置'){//表示审核通过
												return [
												        SESSION_MENU_BUTTONS.type_xg==1?'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;':'',
										        		SESSION_MENU_BUTTONS.type_sc==1?'<a class="cancel ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>&nbsp;&nbsp;':'']
												.join('');
											}else{
												return ''
											}
										}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										 events : {
											'click .edit' : function(e,value, row, index) {
												  //编辑  

												BootstrapDialog.show({
													title : '编辑缴费类型',
													message : $('<div></div>').load(_basepath+'paytype/paytype_add.json?pkid='+row.PKID),
													closable : true,
													buttons : [
													           {
													        	   label : '确定',
													        	   cssClass : 'btn-danger',
													        	   action : function(dialogRef) {
													        		   paytype.savePayType(dialogRef,"edit");
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
																url:_basepath+'paytype/delete.json?pkid='+row.PKID,
																 success:function(data){
																	 if("success" == data.result){
																		 layer.msg("删除成功！");
																		 //删除成功后刷新表格
																		  $("#paytypetable").bootstrapTable('refresh', {url:url});
																	 }else if("is_used"==data.result){
																	 layer.msg("删除失败,该记录已被缴费项目使用!");
																	 }else{
																		 layer.msg("删除失败!");
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
									}
							],
							onEditableSave: function (field, row, oldValue, $el) {
				                $.ajax({
				                    type: "post",
				                    url: _basepath+"paytype/updatepaixu.json",
				                    data: {pkid:row.PKID,paixu:row.PAIXU},
				                    dataType: 'JSON',
				                    success: function (data, status) {
				                        if (status == "success") {
				                        	
				                        	$("#paytypetable").bootstrapTable('refresh', {url:url});
				                        	layer.msg('保存成功');
				                        }
				                    },
				                    error: function () {
				                        layer.msg('编辑失败');
				                    },
				                    complete: function () {

				                    }

				                });
				            }
						});
	    $('#paytypetable').bootstrapTable('hideColumn', 'PKID');//隱藏列
	    $('#paytypetable').bootstrapTable('hideColumn', 'PAY_STYLE_BIANMA');//隱藏列
	};
	//加载表格数据
	paytype.getTab();
})(jQuery, window);
