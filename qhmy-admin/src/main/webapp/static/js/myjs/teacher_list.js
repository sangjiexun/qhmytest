
/**
 * 教师管理
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
	
	//获取选中的行
	page.getIdSelections=function () {
			var $table = $('#teacherTable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.PKID;
        });
	};
	
	/**
	 * 加载表格
	 */
	page.loadTable = function(){
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		
		var r = new Date().getTime();
		var url = _basepath+"teacher/getTeacherlistPage.json?r="+r;
		var columns = [
			{
				field : 'all',
			   	title: "全选",//标题
			    align: "center",//水平
			    checkbox: true,
			    valign: "middle"
			},
			{
				field : 'PKID',// 可不加
				align : "center",
				halign : 'center'
			},
			{
				field : 'SHENFENZHENGHAO',
				title : '身份证号',
				align : "center",
				halign : 'center'
			},
			{
				field : 'XINGMING',
				title : '姓名',
				align : "center",
				halign : 'center'
			},
			{
				field : 'XINGBIE',
				title : '性别',
				align : "center",
				halign : 'center',
				formatter : function(value, row, index) {
					if(value!=null){
						return value == 1 ? "男" : "女";
					}else{
						return "-";
					}
				}
			}/*,
			{
				field : 'CHUSHENGRIQI',
				title : '出生日期',
				align : "center",
				halign : 'center',
				formatter : function(value, row, index) {
					if(value!=null){
						var date = new Date();
						date.setTime(value);
						return date.format("yyyy-MM-dd");
					}else{
						return "";
					}
				},
				sortable : false
			}*/,
			{
				field : 'SHOUJI',
				title : '手机号码',
				align : "center",
				halign : 'center'
			},
			{
				field : 'TEACHER_NO',
				title : '教职工编号',
				align : "center",
				halign : 'center'
			},
			/*{
				field : 'BINDCARD',
				title : '是否绑定支付宝',
				align : "center",
				halign : 'center',
				formatter : function(value, row, index) {
					if(value!=null){
						return value == 1 ? "是" : "";
					}else{
						return "";
					}
				}
			},*/
			{
				field : 'op',
				title : '操作',
				align : "center",
				halign : 'center',
				formatter : function(value, row, index) {
					return [
								SESSION_MENU_BUTTONS.jzg_bj == 1?'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;':'',
										SESSION_MENU_BUTTONS.jzg_sc == 1?'<a class="del ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>':'' ]
								.join('');
				}, // 紫色为添加图标（icon），插件：font-awesome，效果图见底部。
				events : {
					'click .edit' : function(e, value, row, index) {
						var pkid = row.PKID;
						page.edit(pkid);
					},
					'click .del' : function(e, value,row, index) {
						var pkid = row.PKID+"";
						page.del(pkid);
					}
				}// events
			}//操作
		];
		
		$('#teacherTable').bootstrapTable({
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
			singleSelect : false,
			clickToSelect : true,
			onDblClickRow : function(row) {
				//双击行事件
			},
			onLoadSuccess : function(data){
				//数据加载成功
				
			},
			queryParams : function getParams(params) {
				var teacherName=$("#teacherSelect").val();
				
				//当前树节点
				var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					limit : params.limit, // 页面大小
					searchText : this.searchText,
					sortName : this.sortName,
					sortOrder : this.sortOrder,
					pageindex : this.pageNumber,  // 当前页码
					teacherName : teacherName
				};
				return temp;
			},
			buttonsAlign : "right",// 按钮对齐方式
			selectItemName : 'id_role',
			toolbar : "#toolbar",
			toolbarAlign : 'left',
			columns : columns //columns
		});
		
	    $('#teacherTable').bootstrapTable('hideColumn', 'PKID');//隱藏列
	    
	};
	
	
	/**
	 * 表单验证
	 */
	page.formcheck = function(){
//		var role_name = $("#role_name").val();
//		if(role_name == null || role_name == ""){
//			layer.msg("角色名称不能为空!");
//			return false;
//		}
		return true;
	};
	
	/**
	 * 保存
	 */
	page.save = function(dialog){
		var XINGMING = $("#XINGMING").val();
		var XINGBIE = $("input[name='XINGBIE']:checked").val();
		var SHENFENZHENGHAO = $("#SHENFENZHENGHAO").val();
		var TEACHER_NO = $("#TEACHER_NO").val();
		var SHOUJI = $("#SHOUJI").val();
		var DEPARTMENT_ID = $("#DEPARTMENT_PKID").val();
		var dep = $("#citySel2").val();
		var COLLEGES_PKID = $("#orgtree22").val();
		var PKID = $("#PKID").val();//修改参数
		
		if(XINGMING.trim() == ""){
			$("#XINGMING").focus();
			layer.msg("姓名不能为空!");
			return false;
		}
		if(XINGBIE == ""){
			layer.msg("性别不能为空!");
			return false;
		}
		if(SHENFENZHENGHAO.trim() == ""){
			$("#SHENFENZHENGHAO").focus();
			layer.msg("身份证号不能为空!");
			return false;
		}
		if(SHOUJI != '' && !SHOUJI.match(/^1[345789]\d{9}$/)){
			layer.msg("手机号码格式不正确");
			$("#SHOUJI").focus();
			return false;
		}
		if(dep=='' || dep ==null){
			$("#citySel2").focus();
			layer.msg("组织机构不能为空!");
			return false;
		}
		//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
	    var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;  
	    if(reg.test(SHENFENZHENGHAO) === false)  
	    {  
	    	layer.msg("身份证号格式不正确!");
	        return false;  
	    }
//		if(DEPARTMENT_ID == ""){
//			layer.msg("请选择院校专业!");
//			return false;
//		}
		var saveUrl = _basepath+"teacher/saveTeacher.json";
		$.ajax({
			 type: 'post',
			    url: saveUrl,
			    data: {
			    	'XINGMING':XINGMING,
			    	'XINGBIE':XINGBIE,
			    	'SHENFENZHENGHAO':SHENFENZHENGHAO.trim(),
			    	'TEACHER_NO':TEACHER_NO,
			    	'SHOUJI':SHOUJI,
			    	'DEPARTMENT_PKID':DEPARTMENT_ID,
			    	'COLLEGES_PKID':COLLEGES_PKID,
			    	'PKID':PKID
			    },
			    dataTpye:"json",
			    success: function(data) {
			    	if(data.result==true){
			    		layer.msg("操作成功!");
			    		page.refTable("teacherTable", null);
			    		dialog.close();
			    		//刷新页面
					}else if(data.result=='use'){
						layer.msg(data.msg);
//						$("#SHENFENZHENGHAO").focus();
					}else{
						layer.msg("操作失败!");
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
	 * 新增窗口
	 */
	page.toAdd = function(event){
		var dialog = BootstrapDialog.show({
			title:'新增教师',
			message: $('<div></div>').load("teacher/toAddTeacher.json"),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
                label: '保存',
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
		dialog.setSize(BootstrapDialog.SIZE_WIDE);//设置宽度
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
							url : _basepath + 'teacher/deleteTeacher.json?pkids='+ pkids,
							success : function(data) {
								if (true == data.result) {
									layer.msg("删除成功！");
						    		page.refTable("teacherTable", null);
									dialogRef.close();
								}else if(data.result=='use'){
									layer.msg("您选择的信息包含已使用的教职工，删除失败!");
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
	 * 修改页面
	 */
	page.edit = function(pkid){
		var dialog = BootstrapDialog.show({
			title:'编辑教师信息',
			message: $('<div></div>').load("teacher/toAddTeacher.json?PKID="+pkid),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
                label: '保存',
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
		dialog.setSize(BootstrapDialog.SIZE_WIDE);//设置宽度
	};
	
	
	/**
	 * 初始化
	 */
	page.init = function(){
		
		$.ajaxSetup({
		    aysnc: false // 默认同步加载
		});
	    
	    //查询
	    $("#btn_search").bind("click", function(){
            var condition = $("#teacherSelect").val();
            var departmentPkids = $("#orgtree").val();
            var opt = {
			    url: _basepath+"teacher/getTeacherlistPage.json",
			    silent: true,
			    query:{
			    	"condition":condition,
			    	"departmentPkids":departmentPkids
			    }
			};
            $("#teacherTable").bootstrapTable('refresh', opt);
		});
	    //end 查询
		
	    
	    //新增按钮注册事件
	    $("#btn_add").click(page.toAdd);
	    
		/*
		 * 删除
		 */
		$("#btn_delete").click(function(){
			var rows = $("#teacherTable").bootstrapTable('getSelections', null);
			if(rows == null || rows.length<=0){
				layer.msg("请选择要删除的教师!");
				return;
			}
			
			var pkids = window.kemanTooles.Tooles.joinStrSplitComma(rows,"PKID",",");
			
			page.del(pkids);
		});
		//end 
		$("#btn_import_tea").click(function(){
			var dialog = BootstrapDialog.show({
				title:'导入职工信息',
				message: $('<div></div>').load("importteacher/importteacherinfo.json"),
				closable: true,//是否显示叉号
				draggable: true,//可以拖拽
				buttons: [{
		            label: '关闭',
		            cssClass: 'btn-danger',
		            action: function(dialog) {
		            	dialog.close();
		            	$('#teacherTable').bootstrapTable("refresh");
		            }
		        }]
			});
		});
		/*
		 * 导出
		 */
		$("#btn_export_tea").click(function(){
			//所属组织
			var condition=$('#teacherSelect').val();
			var departmentPkids = $("#orgtree").val();
			window.location.href=encodeURI(_basepath+'importteacher/exportExcel.json?condition='+condition+'&departmentPkids='+departmentPkids);
		
		});
		
		page.loadTable();//加载表格
		
	};
	
	page.init();
	
})(window,jQuery,layer);


