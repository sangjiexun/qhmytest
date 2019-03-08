//@ sourceURL=department-list.js
/**
 * 组织机构
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
		var url = _basepath+"authorityManage/department-table.json?r="+r;
		
		var opbutton = "";
		
		//是否有编辑权限
		if(jurisdiction_edit == "1"){
			opbutton = '<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;';
		}
		
		//是否有删除权限
		if(jurisdiction_del == "1"){
			opbutton = opbutton + '<a class="del ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>';
		}
		
		var columns = [{
				field : 'PKID',// 可不加
				align : "center",
				halign : 'center'
			},
			{
				field : 'PARENT_PKID',// 可不加
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
				field : 'DEPARTMENT_NAME',
				title : '组织机构名称',
				align : "center",
				halign : 'center'
			},
			{
				field : 'USERNAME',// 可不加
				title : '操作人',// 标题 可不加
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
						var pkid = row.PKID;
						page.edit(pkid);
					},
					'click .del' : function(e, value,row, index) {
						var pkid = row.PKID;
						page.del(pkid);
					}
				}// events
			}
		];//操作
		
		
		$('#departmentTable').bootstrapTable({
			url : url,// 数据源
			dataField : "rows",// 服务端返回数据键值  // 就是说记录放的键值是rows，分页时使用总记录数的键值为total
			totalField : 'total',
			sortOrder : 'desc',
			method : 'get',
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
			toolbar : "#toolbar",
			toolbarAlign : 'left',
			columns : columns
		});
		
	    $('#departmentTable').bootstrapTable('hideColumn', 'PKID');//隱藏列
	    $('#departmentTable').bootstrapTable('hideColumn', 'PARENT_PKID');//隱藏列
	    
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
				var url = _basepath+"authorityManage/department-table.json";
	            var opt = {
    			    url: url,
    			    silent: true,
    			    query:{
    			    	"PKID":id
    			    }
    		    };
	            $("#departmentTable").bootstrapTable('refresh', opt);
			}
		 });
	};
	
	/**
	 * 表单验证
	 */
	page.formcheck = function(){
		var department_name = $("#department_name").val();
		if(department_name == null || department_name == ""){
			layer.msg("组织机构名称不能为空!");
			return false;
		}
		
		if(department_name.length>25){
			layer.msg("组织机构名称长度不能超过25个字符!");
			return false;
		}
		
		var remark = $("#remark").val();
		if(remark.length>250){
			layer.msg("备注长度不能超过250个字符!");
			return false;
		}
		
		
		
		
		return true;
	};
	
	/**
	 * 保存
	 */
	page.save = function(dialog){
		var department_name = $("#department_name").val();
		var remark = $("#remark").val();
		var parent_pkid = $("#parent_pkid").val();
		var pkid = $("#pkid").val();//修改参数
		
		if(parent_pkid == pkid){
			layer.msg("上级节点不能选择自己本身,请修改上级组织");
			return false;
		}
		
		var datas = new Array();
		datas.push({"name":"department_name","value":department_name});
		datas.push({"name":"remark","value":remark});
		datas.push({"name":"parent_pkid","value":parent_pkid});
		datas.push({"name":"pkid","value":pkid});
		var saveUrl = _basepath+"authorityManage/department-save.json";
		$.ajax({
			 type: 'post',
			    url: saveUrl,
			    data: datas,
			    dataTpye:"json",
			    success: function(data) {
			    	if(data.result==true){
			    		layer.msg("保存成功!");
			    		$("#departmentTreeJsonDataDiv").val(data.departmentTreeJsonData);//左侧树json数据
			    		page.loadTree();//加载左侧树
			    		//page.loadTable();//加载表格
			    		page.refTable("departmentTable", null);
			    		dialog.close();
			    		//$("#departmentTable").bootstrapTable('refresh', opt);//刷新表格
			    		//刷新页面
					}else{
						layer.msg("保存失败!"+data.errorinfo);
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
			layer.msg("请选择左侧组织结构");
			return;
		}
		var pkid = selectNode[0].href;//节点pkid
		
		var dialog = BootstrapDialog.show({
			title:'新增组织机构',
			message: $('<div></div>').load("authorityManage/addDepartment.json?pkid="+pkid),
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
	
	page.CheckUser = function(pkid){
		var flag=false;
		var checkUrl = _basepath+"authorityManage/CheckUser.json";
		$.ajax({
			 type: 'post',
			    url: checkUrl,
			    async:false,
			    data : {pkid:pkid},
			    dataTpye:"json",
			    success: function(data) {
			    	if(data.result=="false"){
			    		flag=true;
					}
			    }
		});
		
		return flag;
		
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
		
		var check = page.CheckUser(pkid);
		if(check){//有子节点
			layer.msg("该组织下有用户存在不可删除!");
			return false;
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
						    		page.refTable("departmentTable", null);
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
		if(pkid == null || pkid == ""){//不是点行的编辑按钮
			//获取点击的节点
			var selectNode = $('#departmentTreeview').treeview('getSelected', null);
			if(selectNode == null || selectNode.length<=0){
				layer.msg("请选择要编辑的数据");
				return;
			}
			pkid = selectNode[0].href;//节点pkid
		}
		
		var dialog = BootstrapDialog.show({
			title:'编辑组织机构',
			message: $('<div></div>').load("authorityManage/addDepartment.json?edit=true&pkid="+pkid),
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
	    var url = _basepath+"authorityManage/department-table.json";
	    $("#btn_search").bind("click", function(){
            var searchsr = $("#searchsr").val();
            var opt = {
			    url: url,
			    silent: true,
			    query:{
			    	"seText":searchsr
			    }
			};
            page.loadTree();
            $("#departmentTable").bootstrapTable('refresh', opt);
		});
	    //end 查询
		
	    
	    //新增按钮注册事件
	    $("#btn_add").click(page.add);
	    
	    
		page.loadTree();//加载左侧树
		page.loadTable();//加载表格
		
	};
	
	
	page.init();
	
})(window,jQuery,layer);


