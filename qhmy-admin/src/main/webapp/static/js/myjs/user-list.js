//@ sourceURL=user-list.js 
/**
 * 组织机构
 * @param win
 * 
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
		var url = _basepath+"authorityManage/user-table.json?r="+r;
		
		$('#userTable').bootstrapTable({
			url : url,// 数据源
			dataField : "rows",// 服务端返回数据键值  // 就是说记录放的键值是rows，分页时使用总记录数的键值为total
			totalField : 'total',
			sortOrder : 'desc',
			method : 'post',
			contentType : "application/x-www-form-urlencoded",
			striped : true, // 表格显示条纹
			pagination : true, // 启动分页
			pageNumber : 1, // 当前第几页
			pageSize : 20, // 每页显示的记录数
			pageList : [ 1, 5, 10, 20, 30, 50, 100 ], // 记录数可选列表
			search : false, // 是否启用查询
			formatSearch : function() {
				return '用户名.真实名.手机号查询';
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
				//双击行事件
			},
			queryParams : function getParams(params) {
				var searchsr=$("#searchsr").val();
				
				//获取点击的节点
				var selectNode = $('#departmentTreeview').treeview('getSelected', null);
				var pkid = "";
				if(selectNode != null && selectNode.length>0){
					pkid = selectNode[0].href;
				}
				
				//当前树节点
				var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					limit : params.limit, // 页面大小
					searchText : this.searchText,
					sortName : this.sortName,
					sortOrder : this.sortOrder,
					pageindex : this.pageNumber,  // 当前页码
					seText : searchsr,
					PKID : pkid  //父节点ID
				};
				return temp;
			},
			buttonsAlign : "right",// 按钮对齐方式
			selectItemName : 'id',
			toolbar : "#toolbar_user",
			toolbarAlign : 'left',
			columns : [
			{
			   	title: "全选",//标题
			    align: "center",//水平
			    checkbox: true,
			    valign: "middle"
			},
			{
				field : 'USER_ID',// 可不加
				align : "center",
				halign : 'center'
			},
			
			{
				field : 'DEPARTMENT_ID',
				align : "center",
				halign : 'center'
			},
			
			{
				field : 'ROLE_ID',
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
				field : 'USERNAME',
				title : '用户名称',
				align : "center",
				halign : 'center'
			},
			
			{
				field : 'NAME',
				title : '真实名称',
				align : "center",
				halign : 'center'
			},
			
			{
				field : 'PHONE',
				title : '手机号',
				align : "center",
				halign : 'center'
			},
			{
				field : 'DEPARTMENT_NAME',
				title : '所属组织',
				align : "center",
				halign : 'center'
			},
			{
				field : 'ROLE_NAME',
				title : '所属角色',
				align : "center",
				halign : 'center'
			},
			{
				field : 'LAST_LOGIN',
				title : '最后登录时间',
				align : "center",
				halign : 'center',
				sortable : false
			},
			{
				field : 'STATUS',
				title : '状态',
				align : "center",
				halign : 'center',
				formatter : function(value, row, index) {
					if(value=="0"){
						return "正常";
					}else if(value=="1"){
						return "停用";
					}
					return "未知";
				},
				sortable : false
			}
			],//columns
		});
		
	    $('#userTable').bootstrapTable('hideColumn', 'USER_ID');//隱藏列
	    $('#userTable').bootstrapTable('hideColumn', 'DEPARTMENT_ID');//隱藏列
	    $('#userTable').bootstrapTable('hideColumn', 'ROLE_ID');//隱藏列
	};
	
	
	/**
	 * 加载tree
	 */
	page.loadTree = function(){
		var treeData = $("#departmentTreeJsonDataDiv").val();
//		console.log(treeData);
		$('#departmentTreeview').treeview({
			data : treeData,
			showCheckbox : false,
			levels : 2,
			highlightSearchResults:true,
		    onNodeChecked: function(event, data){
		    	// 选中父节点，则自动选择子节点
	        	if(data.nodes != null){
	        		var arrayInfo = data.nodes;
	        		for (var i = 0; i < arrayInfo.length; i++) {
	        			$('#departmentTreeview').treeview('toggleNodeChecked', [ arrayInfo[i].nodeId, { silent: true } ]);
					}
	        	}
		    },
			onNodeUnchecked: function(event, data){
	        	// 取消选中父节点，则自动取消选择子节点
	        	if(data.nodes != null){
	        		var arrayInfo = data.nodes;
	        		for (var i = 0; i < arrayInfo.length; i++) {
	        			$('#departmentTreeview').treeview('toggleNodeChecked', [ arrayInfo[i].nodeId, { silent: true } ]);
					}
	        	}
			},
			onNodeSelected: function(event, data){
				//节点选中时触发
				var id=data.href;
				var url = _basepath+"authorityManage/user-table.json";
	            var opt = {
    			    url: url,
    			    silent: true,
    			    query:{
    			    	"department_id":id
    			    }
    		    };
	            $("#userTable").bootstrapTable('refresh', opt);
			}
		 });
	};
	
	/**
	 * 表单验证
	 */
	page.formcheck = function(){
		var username = $("#username").val();//用户名
		var department_name = $("#department_name").val();//组织名称
		var department_id = $("#department_id").val();//组织ID
		var name = $("#name").val();//真实名称
		var phone = $("#phone").val();//手机号
		var status = $("#status").val();//状态
		var user_id = $("#user_id").val();//user主键
		
		if(username == null || username == ""){
			layer.msg("用户名不能为空!");
			return false;
		}else{
			var flag = true;
			$.ajax({
				async: false,
				type:'post',
				dataType:'json',
				url: _basepath+"authorityManage/usernameIdentify.json",
				data : {"USERNAME":username,"USER_ID":user_id},
				success : function(data){
					if(data.result == 'true'){
						layer.msg("用户名已存在!");
						flag=false;
					}
				},
				error : function(a,b,c){
					layer.msg(b);
				}
			});
			if(!flag){
				return false;
			}
		}
		
		
		
		if(name == null || name == ""){
			layer.msg("真实名称不能为空!");
			return false;
		}
		
		if(phone == null || phone == ""){
			layer.msg("手机号不能为空!");
			return false;
		}
		if(phone.length!=11){
			layer.msg("请输入有效的手机号码!");
			return false;
		}
//		var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
//		if(!myreg.test(phone)){
//			layer.msg("请输入有效的手机号码!");
//		    return false; 
//		}
		
		return true;
	};
	
	/**
	 * 保存
	 */
	page.save = function(dialog){
		var username = $("#username").val();//用户名
		var department_name = $("#department_name").val();//组织名称
		var department_id = $("#department_id").val();//组织ID
		var name = $("#name").val();//真实名称
		var phone = $("#phone").val();//手机号
		var status = $("#status").val();//状态
		var user_id = $("#user_id").val();//用户ID  修改的时候才会有
		var DEPARTMENT_PKID = $("#DEPARTMENT_PKID").val();//所属组织PKID，修改的时候才会有
		
		var datas = new Array();
		datas.push({"name":"username","value":username});
		datas.push({"name":"department_name","value":department_name});
		datas.push({"name":"department_id","value":department_id});
		datas.push({"name":"name","value":name});
		datas.push({"name":"phone","value":phone});
		datas.push({"name":"status","value":status});
		datas.push({"name":"user_id","value":user_id});
		datas.push({"name":"DEPARTMENT_PKID","value":DEPARTMENT_PKID});
		var saveUrl = _basepath+"authorityManage/user-save.json";
		$.ajax({
			 type: 'post',
			    url: saveUrl,
			    data: datas,
			    dataTpye:"json",
			    success: function(data) {
			    	if(data.result==true){
			    		layer.msg("保存成功!");
//			    		if(user_id==null || user_id!=''){
//			    			$("#departmentTreeJsonDataDiv").val(data.departmentTreeJsonData);//左侧树json数据
//			    		}
			    		page.refTable("userTable", null);
			    		dialog.close();
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
	page.add = function(event){
		//获取点击的节点
		var selectNode = $('#departmentTreeview').treeview('getSelected', null);
		if(selectNode == null || selectNode.length<=0){
			layer.msg("请选择一个树节点");
			return;
		}
		var department_id = selectNode[0].href;//节点pkid
		
		var dialog = BootstrapDialog.show({
			title:'新增用户',
			message: $('<div></div>').load("authorityManage/addUser.json?department_id="+department_id),
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
		//dialog.setSize(BootstrapDialog.SIZE_SMALL);//设置宽度
	};
	
	
	/**
	 * 分配 角色
	 */
	page.giveRole  = function(event){
		var rows = $("#userTable").bootstrapTable('getSelections');
		
		if(rows == null || rows.length<=0){
			layer.msg("请选择用户!");
			return;
		}
		
		var pkids = window.kemanTooles.Tooles.joinStrSplitComma(rows,"USER_ID",",");
		
		var dialog = BootstrapDialog.show({
			title:'分配角色',
			message: $('<div></div>').load("authorityManage/user-showRole.json"),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
                label: '确定',
                cssClass: 'btn-danger',
                action: function(dialog) {
            		//保存
            		page.saveUserRole(dialog,pkids);
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
		dialog.setSize(BootstrapDialog.SIZE_WIDE);//设置宽度
	};
	
	
	/**
	 * 分配 数据权限
	 */
	page.shujuRole  = function(event){
		var rows = $("#userTable").bootstrapTable('getSelections');
		
		if(rows == null || rows.length<=0){
			layer.msg("请选择用户!");
			return;
		}
		if(rows.length>1){
			layer.msg("只可以选择一个角色进行操作");
			return;
		}
		
		page.showJur(rows[0].USER_ID);
		
	};
	//end 分配权限
	
	/**
	 * 分配 用户数据权限
	 */
	page.yhshujuRole  = function(event){
		var rows = $("#userTable").bootstrapTable('getSelections');
		
		if(rows == null || rows.length<=0){
			layer.msg("请选择用户!");
			return;
		}
		if(rows.length>1){
			layer.msg("只可以选择一个角色进行操作");
			return;
		}
		
		page.showyhJur(rows[0].USER_ID);
		
	};
	//end 分配权限
	
	
	/**
	 * 弹出 分配数据权限
	 * 
	 */
	page.showJur = function(userid){
		var dialog = BootstrapDialog.show({
			title:'分配宿舍数据权限',
			message: $('<div></div>').load("authorityManage/qxshowJur.json?USER_ID="+userid),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
                label: '确定',
                cssClass: 'btn-danger',
                action: function(dialog) {
                	/*var checkRst = page.treeFormCheck();
                	if(checkRst){*/
                		//保存权限
                		page.saveqxJur(dialog,userid);
                	/*}*/
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
	 * 弹出 分配用户数据权限
	 * 
	 */
	page.showyhJur = function(userid){
		var dialog = BootstrapDialog.show({
			title:'分配用户数据权限',
			message: $('<div></div>').load("authorityManage/yhqxshowJur.json?USER_ID="+userid),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
                label: '确定',
                cssClass: 'btn-danger',
                action: function(dialog) {
                	/*var checkRst = page.treeyhFormCheck();
                	if(checkRst){*/
                		//保存权限
                		page.saveyhqxJur(dialog,userid);
                	/*}*/
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
	 * 验证分配权限树节点是否选中
	 */
	page.treeFormCheck = function(){
		var nodes = $('#sjqxTreeview').treeview('getChecked','');
		if(nodes.length<1){
			layer.msg("请选择权限点");
			return false;
		}
		return true;
	};
	
	/**
	 * 验证分配权限树节点是否选中
	 */
	page.treeyhFormCheck = function(){
		var nodes = $('#yhsjqxTreeview').treeview('getChecked','');
		if(nodes.length<1){
			layer.msg("请选择权限点");
			return false;
		}
		return true;
	};
	
	/**
	 * 保存数据权限
	 */
	page.saveqxJur = function(dialog,userid){
		var nodes = $('#sjqxTreeview').treeview('getChecked','');
		var datas = new Array();
		datas.push({"name":"datas","value":JSON.stringify(nodes)});
		datas.push({"name":"USER_ID","value":userid});
		
		var saveUrl = _basepath+"authorityManage/sjqxjur-save.json";
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
	 * 保存用户数据权限
	 */
	page.saveyhqxJur = function(dialog,userid){
		var nodes = $('#yhsjqxTreeview').treeview('getChecked','');
		var datas = new Array();
		datas.push({"name":"datas","value":JSON.stringify(nodes)});
		datas.push({"name":"USER_ID","value":userid});
		
		var saveUrl = _basepath+"authorityManage/yhsjqxjur-save.json";
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
	 * 更新用户角色
	 */
	page.saveUserRole = function(dialog,pkids){
//		var rows = $("#roleTable").bootstrapTable('getSelections');
		
		var $checkedRole = $.map($('#roleTable').bootstrapTable('getSelections'), function(row) {
			console.log(row)
	        return row.ROLE_ID;
	    });
		
		if($checkedRole == null ){
			layer.msg("请选择角色!");
			return;
		}

		
		var role_ids = $checkedRole
		
//		role_ids = role_ids.substring(0,role_ids.length-1);
		
		var datas = new Array();
		datas.push({"name":"pkids","value":pkids});
		datas.push({"name":"role_id","value":role_ids});
		
		$.ajax({
			type : "post",
			dataType : "json",
			url : _basepath + 'authorityManage/saveUserRole.json',
			data : datas,
			success : function(data) {
				if (true == data.result) {
					layer.msg("更新用户角色成功！");
		    		page.refTable("userTable", null);
		    		dialog.close();
				}else{
					layer.msg("更新用户角色失败！");
				}
			},
			error : function(XMLHttpRequest,textStatus,errorThrown) {
				alert(errorThrown);
			}
		});
		
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
	 * 删除 组织机构
	 */
	page.del = function(pkid){
		//检查是否有子节点
		var rst = page.isHaveChildNode(pkid);
		
		if(rst){//有子节点
			layer.msg("有子节点的组织不能删除!");
			return;
		}
		
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '提示信息',
			message : '你确定要删除这条记录吗？',
			closable : true,
			buttons : [
				{
					label : '确定',
					cssClass : 'btn-danger',
					action : function(dialogRef) {
						$.ajax({
							type : "post",
							dataType : "json",
							url : _basepath + 'authorityManage/department-del.json?pkid='+ pkid,
							success : function(data) {
								if (true == data.result) {
									layer.msg("删除成功！");
									$("#departmentTreeJsonDataDiv").val(data.departmentTreeJsonData);//左侧树json数据
									page.loadTree();//加载左侧树
						    		page.refTable("userTable", null);
									dialogRef.close();
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
	 * 修改页面
	 */
	page.edit = function(pkid){
		
		var rows = $("#userTable").bootstrapTable('getSelections', null);
		if(rows == null || rows.length<=0){
			layer.msg("请选择要修改的用户!");
			return;
		}
		
		if(rows.length>1){
			layer.msg("请选择一个用户!");
			return;
		}
		/*if(rows[0].USERNAME=="admin"){
			layer.msg("admin用户不可修改!");
			return;
		}*/
	//	console.log(rows);
		
		
		var dialog = BootstrapDialog.show({
			title:'编辑用户',
			message: $('<div></div>').load("authorityManage/addUser.json?edit=true&user_id="+rows[0].USER_ID+"&username="+rows[0].USERNAME),
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
	 * 启用
	 */
	page.start = function(pkid){
		var rows = $("#userTable").bootstrapTable('getSelections', null);
		if(rows == null || rows.length<=0){
			layer.msg("请选择要启用的用户!");
			return;
		}
		var pkids = window.kemanTooles.Tooles.joinStrSplitComma(rows,"USER_ID",",");
		
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '提示信息',
			message : '确定要启用吗？',
			closable : true,
			buttons : [
				{
					label : '确定',
					cssClass : 'btn-danger',
					action : function(dialogRef) {
						$.ajax({
							type : "post",
							dataType : "json",
							url : _basepath + 'authorityManage/user-status.json?status=0&pkids='+ pkids,
							success : function(data) {
								if (true == data.result) {
									layer.msg("启用成功！");
						    		page.refTable("userTable", null);
									dialogRef.close();
								}else{
									//启用失败，别显示失败原因
									if(data.errorinfo!=null && data.errorinfo.length>0){
										layer.msg(data.errorinfo);
									}else{
										layer.msg("启用失败!");
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
	 * 删除
	 */
	page.del = function(pkid){
		var boolean=false;
		var rows = $("#userTable").bootstrapTable('getSelections', null);
		if(rows == null || rows.length<=0){
			layer.msg("请选择要删除的用户!");
			return;
		}
		
		$.each(rows,function(i,item){
			if(rows[i].USERNAME=="admin"){
				//layer.msg("admin用户不可删除,请重新选择!");
				boolean=true;
			}
		　　});
		if(boolean){
			layer.msg("admin用户不可删除,请重新选择!");
			return;
		}
		
		var pkids = window.kemanTooles.Tooles.joinStrSplitComma(rows,"USER_ID",",");
		
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '提示信息',
			message : '确定要删除吗？',
			closable : true,
			buttons : [
				{
					label : '确定',
					cssClass : 'btn-danger',
					action : function(dialogRef) {
						$.ajax({
							type : "post",
							dataType : "json",
							url : _basepath + 'authorityManage/user-del.json?pkids='+ pkids,
							success : function(data) {
								if (true == data.result) {
									layer.msg("删除成功！");
						    		page.refTable("userTable", null);
									dialogRef.close();
								}else{
									//启用失败，别显示失败原因
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
	 * 停用
	 */
	page.stop = function(pkid){
		var rows = $("#userTable").bootstrapTable('getSelections', null);
		if(rows == null || rows.length<=0){
			layer.msg("请选择要停用的用户!");
			return;
		}
		var pkids = window.kemanTooles.Tooles.joinStrSplitComma(rows,"USER_ID",",");
		
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '提示信息',
			message : '确定要停用吗？',
			closable : true,
			buttons : [
				{
					label : '确定',
					cssClass : 'btn-danger',
					action : function(dialogRef) {
						$.ajax({
							type : "post",
							dataType : "json",
							url : _basepath + 'authorityManage/user-status.json?status=1&pkids='+ pkids,
							success : function(data) {
								if (true == data.result) {
									layer.msg("停用成功！");
						    		page.refTable("userTable", null);
									dialogRef.close();
								}else{
									//启用失败，别显示失败原因
									if(data.errorinfo!=null && data.errorinfo.length>0){
										layer.msg(data.errorinfo);
									}else{
										layer.msg("停用失败!");
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
	 * 密码重置
	 */
	page.settingPassword  = function(pkid){
		var rows = $("#userTable").bootstrapTable('getSelections', null);
		if(rows == null || rows.length<=0){
			layer.msg("请选择要重置密码的用户!");
			return;
		}
		var pkids = window.kemanTooles.Tooles.joinStrSplitComma(rows,"USER_ID",",");
		
		BootstrapDialog.show({ // 显示需要提交的表单。
			title : '重置密码',
			message : '确定要重置密码吗？',
			closable : true,
			buttons : [
				{
					label : '确定',
					cssClass : 'btn-danger',
					action : function(dialogRef) {
						$.ajax({
							type : "post",
							dataType : "json",
							url : _basepath + 'authorityManage/user-updatePwd.json?pkids='+ pkids,
							success : function(data) {
								if (true == data.result) {
									layer.msg("密码重置为123456！");
						    		page.refTable("userTable", null);
									dialogRef.close();
								}else{
									//启用失败，别显示失败原因
									if(data.errorinfo!=null && data.errorinfo.length>0){
										layer.msg(data.errorinfo);
									}else{
										layer.msg("重置密码失败!");
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
	 * 初始化
	 */
	page.init = function(){
		$.ajaxSetup({
		    aysnc: false // 默认同步加载
		});
		
		//展开
	    $("#department_unfold").click(function(){
	    	var keman_tooles = new window.keman_tooles(); 
	    	keman_tooles.treeview.dosome(4,'departmentTreeview');
	    });
	    
	    //折叠
	    $("#department_fold").click(function(){
	    	var keman_tooles = new window.keman_tooles(); 
	    	keman_tooles.treeview.dosome(3,'departmentTreeview');
	    });
	    
	    //查询
	    var url = _basepath+"authorityManage/user-table.json";
	    $("#user_btn_search").bind("click", function(){
            var searchsr = $("#searchsr").val();
            var opt = {
			    url: url,
			    silent: true,
			    query:{
			    	"seText":searchsr
			    }
			};
            
            
            $("#userTable").bootstrapTable('refresh', opt);
            page.loadTree();
		});
	    //end 查询
		
	    
	    //新增按钮注册事件
	    $("#user_btn_add").click(page.add);
	    //修改按钮注册事件
	    $("#user_btn_edit").click(page.edit);
	    //启用
	    $("#user_btn_start").click(page.start);
	  //删除
	    $("#user_btn_del").click(page.del);
	    //停用
	    $("#user_btn_stop").click(page.stop);
	    //密码重置
	    $("#user_btn_password").click(page.settingPassword);
	    //分配角色
	    $("#user_btn_role").click(page.giveRole);
	    //分配数据权限
	    $("#user_btn_qx").click(page.shujuRole);
	  //分配用户数据权限
	    $("#user_btn_yhqx").click(page.yhshujuRole);
	    
	   
	    
		page.loadTree();//加载左侧树
		page.loadTable();//加载表格
	};
	
	page.init();
	
})(window,jQuery,layer);


