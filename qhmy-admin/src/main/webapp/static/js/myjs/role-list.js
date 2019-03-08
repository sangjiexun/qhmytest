
/**
 * 角色管理
 * @param win
 * @param $
 */
(function(win,$,layer){
	
	var page = {};
	
	/**
	 * 刷新右侧内容区域
	 */
	page.refright = function(liObj){
		var url = $(liObj).find("a").attr("menu_url");
		var r = Math.random();
		url = _basepath + url +"?r="+r;
		$('.jf_szright').load(url);
	};
	
	/**
	 * 加载表格
	 */
	page.loadTable = function(){
		var r = new Date().getTime();
		var url = _basepath+"authorityManage/role-table.json?r="+r;
		
		var showFlag = $("#showFlag").val();//是否只是显示查询  因为用户功能会调用角色列表
		var opbutton = "";
		//是否有编辑权限
		if(jurisdiction_edit == "1"){
			opbutton = '<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;';
		}
		
		//是否有删除权限
		if(jurisdiction_del == "1"){
			opbutton = opbutton + '<a class="del ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>';
		}
		
		
		
		var columns = [
		               
			{
				field : 'all',
			   	title: "全选",//标题
			    align: "center",//水平
			    checkbox: true,
			    valign: "middle"
			},
			{
				field : 'ROLE_ID',// 可不加
				align : "center",
				halign : 'center'
			},
			{
				field : 'XH',
				title : '序号',
				align : "center",
				halign : 'center'
			},
			{
				field : 'ROLE_NAME',
				title : '角色名称',
				align : "center",
				halign : 'center'
			},
			{
				field : 'ROLE_TYPE',
				title : '角色类型',
				align : "center",
				halign : 'center',
				formatter : function(value, row, index) {
					if(value=="1"){
						return "系统角色";
					}else if(value=="2"){
						return "用户角色";
					}
				}
			},
			{
				field : 'USERNAME',
				title : '操作人',
				align : "center",
				halign : 'center'
			},
			{
				field : 'CJSJ',
				title : '创建时间',
				align : "center",
				halign : 'center',
				formatter : function(value, row, index) {
					if(value!=null){
						var date = new Date();
						date.setTime(value);
						return date.format("yyyy-MM-dd hh:mm:ss");
					}else{
						return "";
					}
				},
				sortable : false
			},
			{
				field : 'op',
				title : '操作',
				align : "center",
				halign : 'center',
				formatter : function(value, row, index) {
					return opbutton;
				}, // 紫色为添加图标（icon），插件：font-awesome，效果图见底部。
				events : {
					'click .edit' : function(e, value, row, index) {
						var pkid = row.ROLE_ID;
						page.edit(pkid);
					},
					'click .del' : function(e, value,row, index) {
						var pkid = row.ROLE_ID;
						page.del(pkid);
					}
				}// events
			}//操作
		];
		
		
		
		
		$('#roleTable').bootstrapTable({
			url : url,// 数据源
			dataField : "rows",// 服务端返回数据键值  // 就是说记录放的键值是rows，分页时使用总记录数的键值为total
			totalField : 'total',
			sortOrder : 'desc',
			//height : 300,
			undefinedText : "", //当数据为null或未定义时显示指定文字替换
			method : 'post',
			contentType : "application/x-www-form-urlencoded",
			striped : true, // 表格显示条纹
			pagination : true, // 启动分页
			//sortName :   定义排序列名称 
			pageNumber : 1, // 当前第几页
			pageSize : 20, // 每页显示的记录数
			pageList : [ 1, 5, 10, 20, 30, 50, 100 ], // 记录数可选列表
			search : false, // 是否启用查询
			formatSearch : function() {
				return '请输入角色名称查询';
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
			singleSelect : showFlag=='true'?true:false,
			clickToSelect : true,
			onDblClickRow : function(row) {
				//双击行事件
			},
			onLoadSuccess : function(data){
				//数据加载成功
				var rows = data.rows;
				var checkboxs = $("input[name='id_role']");
				$.each(checkboxs,function(i,item){
					$(item).attr("role_id",rows[i].ROLE_ID);
				});
			},
			queryParams : function getParams(params) {
				var searchsr=$("#searchsr_role").val();
				
				//当前树节点
				var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					limit : params.limit, // 页面大小
					searchText : this.searchText,
					sortName : this.sortName,
					sortOrder : this.sortOrder,
					pageindex : this.pageNumber,  // 当前页码
					seText : searchsr
				};
				return temp;
			},
			buttonsAlign : "right",// 按钮对齐方式
			selectItemName : 'id_role',
			toolbar : "#toolbar",
			toolbarAlign : 'left',
			columns : columns //columns
		});
		
	    $('#roleTable').bootstrapTable('hideColumn', 'ROLE_ID');//隱藏列
	    
	    if(showFlag=="true"){
	    	$('#roleTable').bootstrapTable('hideColumn', 'op');//隱藏列
	    }
	    
	};
	
	
	/**
	 * 表单验证
	 */
	page.formcheck = function(){
		var role_name = $("#role_name").val();
		var pkid = $("#pkid").val();
		if(role_name == null || role_name == ""){
			layer.msg("角色名称不能为空!");
			return false;
		}else{
			var flag = true;
			$.ajax({
				async:false,
				type:'post',
				dataType:'json',
				data : {"PKID":pkid,"ROLE_NAME":role_name},
				url: _basepath+"authorityManage/rolenameIdentify.json",
				success : function(data){
					if(data.result=='true'){
						layer.msg("角色名称已存在！");
						flag=false;
					}
				},
				error: function(a,b,c){
					layer.msg(b);
				}
			});
			return flag;
		}
		return true;
	};
	
	/**
	 * 保存
	 */
	page.save = function(dialog){
		var role_name = $("#role_name").val();
		var pkid = $("#pkid").val();//修改参数
		
		var datas = new Array();
		datas.push({"name":"role_name","value":role_name});
		datas.push({"name":"pkid","value":pkid});
		
		var saveUrl = _basepath+"authorityManage/role-save.json";
		$.ajax({
			 type: 'post',
			    url: saveUrl,
			    data: datas,
			    dataTpye:"json",
			    success: function(data) {
			    	if(data.result==true){
			    		layer.msg("保存成功!");
			    		page.refTable("roleTable", null);
			    		dialog.close();
			    		//刷新页面
					}else{
						layer.msg("保存失败!");
			    	}
			    }
		});
	};
	
	
	/**
	 * 表格刷新
	 */
	page.refTable = function(tableName,op){
		if(op!=null){
            $("#"+tableName).bootstrapTable('refresh', op);
		}else{
			$("#"+tableName).bootstrapTable('refresh');
		}
	};
	
	
	/**
	 * 添加页面
	 */
	page.add = function(event,pkid){
		if(typeof pkid === 'undefined'){
			pkid="";
		}
		var dialog = BootstrapDialog.show({
			title:'新增角色',
			message: $('<div></div>').load("authorityManage/addRole.json?pkid="+pkid),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
                label: '确定',
                cssClass: 'btn-danger',
                action: function(dialog) {
                	var checkRst = page.formcheck();
                	if(checkRst){
                		//保存
                		page.save(dialog);
                	}
                	//dialog.close();
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
	};
	
	/**
	 * 是否有子节点
	 */
	page.isHaveChildNode = function(pkid){
		var node = $('#departmentTreeview').treeview('getNodeByHref', pkid);
		try{
			if(node.nodes.length>0){
				return true;
			}
		}catch(e){
			return false;
		}
		return false;
	};
	
	/**
	 * 删除 角色
	 */
	page.del = function(pkids){
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '提示信息',
			message : '你确定要删除吗？',
			closable : true,
			buttons : [
				{
					label : '确定',
					cssClass : 'btn-danger',
					action : function(dialogRef) {
						$.ajax({
							type : "post",
							dataType : "json",
							url : _basepath + 'authorityManage/role-del.json?pkids='+ pkids,
							success : function(data) {
								if (true == data.result) {
									layer.msg("删除成功！");
						    		page.refTable("roleTable", null);
									dialogRef.close();
								}else{
									//删除失败，别显示失败原因
									if(data.errorinfo!=null && data.errorinfo.length>0){
										layer.msg(data.errorinfo);
									}else{
										layer.msg("删除失败!");
									}
								}
							},
							error : function(XMLHttpRequest,textStatus,errorThrown) {
								alert(errorThrown);
							}
						});
					}
				},
				{
					label : '取消',
					cssClass : 'btn-default',
					action : function(dialogRef) {
						dialogRef.close();
					}
				} 
			]
		});// BootstrapDialog.show
	};
	
	/**
	 * 验证分配权限树节点是否选中
	 */
	page.treeFormCheck = function(){
		var nodes = $('#menuAndButtonTreeview').treeview('getChecked','');
		if(nodes.length<1){
			layer.msg("请选择权限点");
			return false;
		}
		return true;
	};
	
	
	/**
	 * 保存权限
	 */
	page.saveJurisdiction = function(dialog,role_id){
		var nodes = $('#menuAndButtonTreeview').treeview('getChecked','');
		var datas = new Array();
		datas.push({"name":"datas","value":JSON.stringify(nodes)});
		datas.push({"name":"role_id","value":role_id});
		
		var saveUrl = _basepath+"authorityManage/jurisdiction-save.json";
		$.ajax({
			type: 'post',
		    url: saveUrl,
		    data: datas,
		    dataTpye:"json",
		    success: function(data) {
		    	if(data.result==true){
		    		layer.msg("保存成功!");
		    		dialog.close();
		    		//刷新页面
				}else{
					layer.msg("保存失败!");
		    	}
		    },
		    error: function(a,b,c){
		    	alert(a);
		    	alert(b);
		    	alert(c);
		    }
		});
	};
	
	
	/**
	 * 弹出 分配权限
	 * rold_id:角色ID
	 */
	page.showJurisdiction = function(role_id){
		var dialog = BootstrapDialog.show({
			title:'分配权限',
			message: $('<div></div>').load("authorityManage/showJurisdiction.json?role_id="+role_id),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
                label: '确定',
                cssClass: 'btn-danger',
                action: function(dialog) {
                	var checkRst = page.treeFormCheck();
                	if(checkRst){
                		//保存权限
                		page.saveJurisdiction(dialog,role_id);
                	}
                	//dialog.close();
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
	};
	
	
	
	/**
	 * 修改页面
	 */
	page.edit = function(pkid){
		var dialog = BootstrapDialog.show({
			title:'编辑角色',
			message: $('<div></div>').load("authorityManage/addRole.json?edit=true&pkid="+pkid),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
                label: '确定',
                cssClass: 'btn-danger',
                action: function(dialog) {
                	var checkRst = page.formcheck();
                	if(checkRst){
                		//保存
                		page.save(dialog,pkid);
                	}
                	//dialog.close();
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
	};
	
	
	/**
	 * 初始化
	 */
	page.init = function(){
		
		$.ajaxSetup({
		    aysnc: false // 默认同步加载
		});
	    
	    //查询
	    var url = _basepath+"authorityManage/role-table.json";
	    $("#btn_search").bind("click", function(){
            var searchsr = $("#searchsr_role").val();
            var opt = {
			    url: url,
			    silent: true,
			    query:{
			    	"seText":searchsr
			    }
			};
            $("#roleTable").bootstrapTable('refresh', opt);
		});
	    //end 查询
		
	    
	    //新增按钮注册事件
	    $("#btn_add").click(page.add);
	    
	    
	    /*
	     * 编辑
	     */
		$("#btn_edit").click(function(){
			var rows = $("#roleTable").bootstrapTable('getSelections', null);
			if(rows == null || rows.length<=0){
				layer.msg("请选择要修改的角色!");
				return;
			}
			
			if(rows.length>1){
				layer.msg("只可以选择一个角色进行操作");
				return;
			}
			page.edit(rows[0].ROLE_ID);
		});
	    //end 编辑
		
		/*
		 * 删除
		 */
		$("#btn_delete").click(function(){
			var rows = $("#roleTable").bootstrapTable('getSelections', null);
			if(rows == null || rows.length<=0){
				layer.msg("请选择要删除的角色!");
				return;
			}
			
			var pkids = window.kemanTooles.Tooles.joinStrSplitComma(rows,"ROLE_ID",",");
			
			page.del(pkids);
		});
		//end 
		
		/*
		 * 分配权限
		 */
		$("#btn_jurisdiction").click(function(){
			var rows = $("#roleTable").bootstrapTable('getSelections', null);
			if(rows == null || rows.length<=0){
				layer.msg("请选择角色!");
				return;
			}
			
			if(rows.length>1){
				layer.msg("只可以选择一个角色进行操作");
				return;
			}
			
			page.showJurisdiction(rows[0].ROLE_ID);
		});
		//end 分配权限
	    
		page.loadTable();//加载表格
		
	};
	
	page.init();
	
})(window,jQuery,layer);


