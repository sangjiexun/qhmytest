/**
 * 预定分配对象
 */
//@ sourceURL=dormOrder.js
(function($, window) {
	var dormOrder = {};
	//查询按钮点击事件
	$('#dormOrderQuery').click(function(){
		$("#dormOrderTable").bootstrapTable('destroy'); 
		dormOrder.getTab();
	});
	
	//获取选中的行
	dormOrder.getIdSelections=function () {
			var $table = $('#dormOrderTable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.PKID;
        });
	};
	
	//获取选中的行的文化课学校pkid
	dormOrder.getShoolSelections=function () {
			var $table = $('#dormOrderTable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.SCHOOL_PKID;
        });
	};
	
	//获取选中的行的班型pkid
	dormOrder.getClassTypeSelections=function () {
			var $table = $('#dormOrderTable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.CLASSTYPE_PKID;
        });
	};
	
	//获取选中的行的性别
	dormOrder.getSd_SexSelections=function () {
			var $table = $('#dormOrderTable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.XINGBIE;
        });
	};
	//分配按钮
	$("#btn_fenpei").click(function(){
		var pkids=dormOrder.getIdSelections();
		if(pkids==null || pkids ==''){
			layer.msg("请选择至少一名学生");
			return false;
		}
		var SCHOOL_PKIDS = dormOrder.getShoolSelections();//文化课PKIDS
		var CLASSTYPE_PKIDS = dormOrder.getClassTypeSelections();//班型PKIDS
		var se_sexs = dormOrder.getSd_SexSelections();
		var flag = true;
		var SCHOOL_PKID = "";
		var tempn = "";
		$.each(SCHOOL_PKIDS,function(i,item){
			tempn = SCHOOL_PKID;
			SCHOOL_PKID = item;
			if(tempn!='' && SCHOOL_PKID!=tempn){
				layer.msg("所选学生的文化课学校要一致");
				flag = false;
				return flag;
			}
		});
		if(!flag){
			return false;
		}
		var CLASSTYPE_PKID = "";
		var tempn = ""; 
		$.each(CLASSTYPE_PKIDS,function(i,item){
			tempn = CLASSTYPE_PKID;
			CLASSTYPE_PKID = item;
			if(tempn!='' && CLASSTYPE_PKID!=tempn){
				layer.msg("所选学生的班型要一致");
				flag = false;
				return flag;
			}
		});
		if(!flag){
			return false;
		}
		var SD_SEX = "";
		var tempn = "";
		$.each(se_sexs,function(i,item){
			tempn = SD_SEX;
			SD_SEX = item;
			if(tempn!='' && SD_SEX!=tempn){
				layer.msg("所选学生的性别要一致");
				flag = false;
				return flag;
			}
		});
		if(!flag){
			return false;
		}
		if(SD_SEX=='男'){
			SD_SEX='1';
		}else{
			SD_SEX='0';
		}
		BootstrapDialog.show({
			title:'分配',
			message: $('<div></div>').load("dormitoryInfo/godiviDorm.json?SD_SEX="+SD_SEX+"&&CLASSTYPE_PKID="+CLASSTYPE_PKID+"&&SCHOOL_PKID="+SCHOOL_PKID),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
	            label: '确定',
	            cssClass: 'btn-danger',
	            action: function(dialog) {
	            	var dormtree = $("#orgtree3").val();
	            	if(dormtree=="" || dormtree==null){
	            		layer.msg('请选择宿舍', {time:800});
	            		return false;
	            	}
	            	var dorm_length = dormtree.split(',').length-1;
	            	var stu_lenght = pkids.length;
	            	if(dorm_length>stu_lenght){//宿舍数大于学生数
	            		BootstrapDialog.show({
	            			title:'提示',
	            			message: '选择的学生数'+stu_lenght+'，床位数'+dorm_length+'，学生资源不足，是否继续',
		        			closable: true,//是否显示叉号
		        			draggable: true,//可以拖拽
		        			buttons: [{
		        	            label: '确定',
		        	            cssClass: 'btn-danger',
		        	            action: function(dialog2) {
		        	            	$.ajax({
		        	            		type:'post',
		        	            		dataType:'json',
		        	            		data:{"dormtree":dormtree,"stu_lenght":stu_lenght,"dorm_length":dorm_length},
		        	            		url: _basepath+'dormitoryInfo/diviDorm.json?pkids='+pkids,
		        	            		success:function(data){
		        	            			if(data.result=='success'){
		        	            				layer.msg("分配成功");
		        	            				$("#dormOrderTable").bootstrapTable('destroy'); 
		        	            				dormOrder.getTab();
		        	            				dialog2.close();
		        	            				dialog.close();
		        	            			}
		        	            		},
		        	            		error:function(a,error,c){
//		        	            			alert(error);
		        	            		}
		        	            	});
		        	            }
		        	        },
		        	        {
		        	            label: '取消',
		        	            cssClass: 'btn-danger',
		        	            action: function(dialog2) {
		        	            	dialog2.close();
		        	            }
		        	        }
		        	        ]
		        		});
	            	}else if(dorm_length<stu_lenght){//学生数大于宿舍数
	            		BootstrapDialog.show({
	            			title:'提示',
	            			message: '选择的学生数'+stu_lenght+'人，床位数'+dorm_length+'人，宿舍资源不足，是否继续',
		        			closable: true,//是否显示叉号
		        			draggable: true,//可以拖拽
		        			buttons: [{
		        	            label: '确定',
		        	            cssClass: 'btn-danger',
		        	            action: function(dialog2) {
		        	            	$.ajax({
		        	            		type:'post',
		        	            		dataType:'json',
		        	            		data:{"dormtree":dormtree,"stu_lenght":stu_lenght,"dorm_length":dorm_length},
		        	            		url: _basepath+'dormitoryInfo/diviDorm.json?pkids='+pkids,
		        	            		success:function(data){
		        	            			if(data.result=='success'){
		        	            				layer.msg("分配成功");
		        	            				$("#dormOrderTable").bootstrapTable('destroy'); 
		        	            				dormOrder.getTab();
		        	            				dialog2.close();
		        	            				dialog.close();
		        	            			}
		        	            		},
		        	            		error:function(a,error,c){
//		        	            			alert(error);
		        	            		}
		        	            	});
		        	            }
		        	        },
		        	        {
		        	            label: '取消',
		        	            cssClass: 'btn-danger',
		        	            action: function(dialog2) {
		        	            	dialog2.close();
		        	            }
		        	        }]
		        		});
	            	}else{
	            		$.ajax({
    	            		type:'post',
    	            		dataType:'json',
    	            		data:{"dormtree":dormtree,"stu_lenght":stu_lenght,"dorm_length":dorm_length},
    	            		url: _basepath+'dormitoryInfo/diviDorm.json?pkids='+pkids,
    	            		success:function(data){
    	            			if(data.result=='success'){
    	            				layer.msg("分配成功");
    	            				$("#dormOrderTable").bootstrapTable('destroy'); 
    	            				dormOrder.getTab();
    	            				dialog.close();
    	            			}
    	            		},
    	            		error:function(a,error,c){
    	            			//alert(error);
    	            		}
    	            	});
	            	}
	            }
	        }]
		});
	});
	
	// 获取bootStrapTable表格数据,及参数设置
	dormOrder.getTab = function() {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		var url = _basepath + "dormitoryInfo/dormOrderTable.json";
		$('#dormOrderTable').bootstrapTable(
						{
							method: 'post',  
							url : url,//数据源
							dataType: "json",
							contentType:"application/x-www-form-urlencoded; charset=UTF-8",
							dataField : "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
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
							onLoadSuccess:function(data){
							},
							queryParams : function getParams(params) {
								//文化课学校
								var PART_SCHOOL = $("#part_school").val();
								//班型
								var CLASS_TYPE=$("#class_type").val();
								//性别
								var XINGBIE = $('#XINGBIE').val();
								//是否分配
								var IS_FENPEI = $("#IS_FENPEI").val();
								//关键词
								var keywords = $("#keywords").val();
								//入学年份
								var RUXUENIANFEN = $("#RUXUENIANFEN").val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									keywords : keywords,
									XINGBIE : XINGBIE,
									IS_FENPEI : IS_FENPEI,
									CLASS_TYPE : CLASS_TYPE,
									PART_SCHOOL : PART_SCHOOL,
									RUXUENIANFEN : RUXUENIANFEN,
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
										valign : "middle",//垂直
										formatter:function(value, row, index){
											   // 根据row.列名   那状态确定返回 true/false
											if(row.IS_FENPEI=='Y'){
												  return {
														 disabled : true
														  };
											}
										}
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
										field : 'XINGBIE',
										title : '性别',
										align : "center",
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
										field : 'SCHOOLNAME',
										title : '文化课学校',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'GRADE',
										title : '入学年份',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'CLASSTYPE',
										title : '班型',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'CLASS_NAME',
										title : '班级',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'AMOUNTRECEIVABLEALL',
										title : '实收总金额',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter : function(value, row,
												index) {
												return [
														(row.AMOUNTRECEIVABLEALL*1).toFixed(2)+'' ]
														.join('');
										}
									},
									{
										field : 'DORMNAME',
										title : '宿舍',
										align : "left",
										halign : 'center',
										sortable : false

									}],
						});
	};
	//加载表格数据
	dormOrder.getTab();
})(jQuery, window);
