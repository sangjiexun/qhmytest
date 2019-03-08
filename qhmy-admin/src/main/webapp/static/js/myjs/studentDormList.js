//@ sourceURL=studentDormList.js
/**
 * 学生宿舍
 * @param win
 * @param $
 */


/**
 * 是否有子节点
 */
var isHaveChildNode = function(pkid){
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
	
/*
 * 校区卡片List
 */
var setCardListHtml = function(pkid){
	var cardListHtml = '';
	$.ajax({
		  type: 'POST',
		  url: _basepath+"studentDorm/getStudentDormList.json",
		  async: false,
		  data: {
			  pkid:pkid
		  },
		  dataType : "json",
		  success : function(data) {
		    	
		    	if(data.result==true){
		    		var studentDormList = data.studentDormList;
		    		var isEnd = false;
		    		for (var i = 0; i < studentDormList.length; i++) {
		    			var studentDorm = studentDormList[i];
		    			
		    			var LOUCOUNT = 0;
		    			var CENGCOUNT = 0;
		    			var FANGCOUNT = 0;
						var CHUANGCOUNT = 0;
						
						$.ajax({
	    					  type: 'POST',
	    					  url: _basepath+"studentDorm/getStudentDormList.json",
	    					  async: false,
	    					  data: {
	    						  pkid:studentDorm.PKID
	    					  },
	    					  dataType : "json",
	    					  success : function(data) {
	    					    	
	    					    	if(data.result==true){
	    					    		var studentDormList = data.studentDormList;
	    					    		for (var i = 0; i < studentDormList.length; i++) {
	    					    			var studentDorm = studentDormList[i];
	    					    			LOUCOUNT = studentDorm.LOUCOUNT;
	    					    			CENGCOUNT = studentDorm.CENGCOUNT;
	    					    			FANGCOUNT = studentDorm.FANGCOUNT;
	    					    			CHUANGCOUNT = studentDorm.CHUANGCOUNT;
	    					    		}
	    					    		
	    					    	}
	    					  }
	    				});
						
		    			if(i == 0 || i % 5 == 0){//5个卡片一行
		    				cardListHtml += '<div class="row">';
		    			}
		    			if(data.dormLevel == '0'){//点击学校，显示校区
		    				
		    				cardListHtml += '<div class="col-md-2 col-sm-3 col-xs-4" style="margin-bottom:15px;">'+
										        '<div class="sg_wsolid bg-info">'+
										            '<ul>'+
										                '<li class="sg_louhao">'+studentDorm.SD_NAME+'</li>'+
										                '<li class="sg_louceng">楼数'+
										                    '<div class="sg_lcNumber">'+LOUCOUNT+'</div>'+
										                '</li>'+
										                '<li class="sg_louceng">楼层数'+
										                    '<div class="sg_lcNumber">'+CENGCOUNT+'</div>'+
										                '</li>'+
										                '<li class="clearfix"></li>'+
										            '</ul>'+
										            '<ul>'+
										                '<li class="sg_louceng">房间数'+
										                    '<div class="sg_lcNumber">'+FANGCOUNT+'</div>'+
										                '</li>'+
										                '<li class="sg_louceng">床位数'+
										                    '<div class="sg_lcNumber">'+CHUANGCOUNT+'</div>'+
										                '</li>'+
										                '<li class="clearfix"></li>'+
										            '</ul>'+
										        '</div>'+
										    '</div>';
						}else if(data.dormLevel == '1'){//点击校区，显示楼
							cardListHtml += '<div class="col-md-2 col-sm-3 col-xs-4" style="margin-bottom:15px;">'+
											        '<div class="sg_wsolid bg-info">'+
											            '<ul>'+
											                '<li class="sg_louhao">'+studentDorm.SD_NAME+'</li>'+
											                '<li class="sg_louceng">楼层数'+
											                    '<div class="sg_lcNumber">'+CENGCOUNT+'</div>'+
											                '</li>'+
											                '<li class="sg_louceng">房间数'+
											                    '<div class="sg_lcNumber">'+FANGCOUNT+'</div>'+
											                '</li>'+
											                '<li class="clearfix"></li>'+
											                '<li style="margin-top:15px;">总床位数：'+
											                    '<span>'+CHUANGCOUNT+'</span>'+
											                '</li>'+
											            '</ul>'+
											        '</div>'+
											    '</div>';
						}else if(data.dormLevel == '2'){//点击楼，显示楼层
							
							cardListHtml += '<div class="col-md-2 col-sm-3 col-xs-4" style="margin-bottom:15px;">'+
											        '<div class="sg_wsolid bg-info">'+
											            '<ul>'+
											                '<li class="sg_louhao">'+studentDorm.SD_NAME+'</li>'+
											                '<li class="sg_loucengS">房间数'+
											                    '<div class="sg_lcNumber">'+FANGCOUNT+'</div>'+
											                '</li>'+
											                '<li class="clearfix"></li>'+
											                '<li style="margin-top:15px;">总床位数：'+
											                    '<span>'+CHUANGCOUNT+'</span>'+
											                '</li>'+
											            '</ul>'+
											        '</div>'+
											    '</div>';
						}else if(data.dormLevel == '3'){//点击楼层，显示房间
							
							cardListHtml += 
							'<div class="col-md-2 col-sm-3 col-xs-4" style="margin-bottom:15px;">'+
						        '<div class="sg_wsolid bg-info">'+
						            '<ul>'+
						                '<li class="sg_louhao">'+studentDorm.SD_NAME+'</li>'+
						                '<li class="sg_loucengS">床位数'+
						                    '<div class="sg_lcNumber">'+CHUANGCOUNT+'</div>'+
						                '</li>'+
						                '<li class="clearfix"></li>'+
						            '</ul>'+
						        '</div>'+
						    '</div>';
						}else if(data.dormLevel == '4'){//点击房间，显示床
							cardListHtml += 
							'<div class="col-md-2 col-sm-3 col-xs-4" style="margin-bottom:15px;">'+
						        '<div class="sg_wsolidH '+(studentDorm.STATUS == '1'?'bg-danger':'bg-success')+'">'+
						            '<ul>'+
						                '<li class="sg_louhao">'+studentDorm.SD_NAME+'</li>'+
						                '<li><img src="'+_basepath+'static/gxjf/images/bed.png" class="sg_bed" width="55%"/></li>'+
						                '<li class="clearfix"></li>'+
						            '</ul>'+
						        '</div>'+
						    '</div>';
						}
		    			
		    			if((i + 1) % 5  == 0){//5个卡片一行
		    				isEnd = true;
		    				cardListHtml += '</div>';
		    			}else{
		    				isEnd = false;
		    			}
		    			
					}
		    		if(!isEnd){
		    			cardListHtml += '</div>';
		    		}
				}
		    }
	});
	return cardListHtml;
};



/**
 * 表格刷新
 */
var refTable = function(){
	var pkid = $("#treeNodeId").val();
	var r = Math.random();
	var url = _basepath+"studentDorm/getStudentDormLevel.json?r="+r+"&&PKID="+pkid;
	$.ajax({
		 	type: 'post',
		    url: url,
		    async:false,
		    data:{pkid:pkid},
		    dataType:"json",
		    success: function(data) {
		    	if(data.result==true){
		    		//房间点击无效
		    		if(data.dormLevel == '4'){
		    			$("#btn_add_span").css("display","none");
		    		}else{
		    			$("#btn_add_span").css("display","block");
		    		}
		    		$("#cardList").html(setCardListHtml(pkid));
				}
		    }
	});
};



/**
 * 加载tree
 */
var loadTree = function(parent_pkid){
	var treeData = $("#departmentTreeJsonDataDiv").val();
//	console.log(treeData);
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
			var target = $(event.target);
			//节点选中时触发
			$("#cardList").html("");
			var pkid = data.href;
			var parentPkid = data.parentId;
			var url = _basepath+"studentDorm/getStudentDormLevel.json";
			$.ajax({
				 	type: 'post',
				    url: url,
				    async:false,
				    data:{pkid:pkid},
				    dataType:"json",
				    success: function(data) {
				    	if(data.result==true){
				    		//房间点击无效
				    		if(data.dormLevel == '4'){
				    			$("#btn_add_span").css("display","none");
				    		}else{
				    			$("#btn_add_span").css("display","block");
				    		}
				    		$("#cardList").html(setCardListHtml(pkid));
						}
				    }
			});
			var id=data.href;
			$("#treeNodeId").val(id);
			
		}
	 });
	
	//选中指定节点
//	$('#departmentTreeview').treeview('toggleNodeChecked', [ arrayInfo[i].nodeId, { silent: true } ]);
	
	if(parent_pkid!=null && typeof(parent_pkid)!='undefined'){
		var node = $('#departmentTreeview').treeview('getNodeByHref', parent_pkid);
		if(node.nodeId != 'undefined' && node.nodeId != null){
			node.state.expanded = true;
			$('#departmentTreeview').treeview('revealNode', [node.nodeId, { silent: true } ]);
		}
		$('#departmentTreeview').treeview('toggleNodeSelected', [ node.nodeId, { silent: true } ]);
	}
	
};

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
		var pkid=$("#defaultTopPkid").val();
		$("#cardList").html(setCardListHtml(pkid));
		
		//选中第一节点
		if(pkid!=null && typeof(pkid)!='undefined'){
			var node = $('#departmentTreeview').treeview('getNodeByHref', pkid);
			if(node.nodeId != 'undefined' && node.nodeId != null){
				node.state.expanded = true;
				$('#departmentTreeview').treeview('revealNode', [node.nodeId, { silent: true } ]);
			}
			$('#departmentTreeview').treeview('toggleNodeSelected', [ node.nodeId, { silent: true } ]);
		}
		$("#treeNodeId").val(pkid);
	};
	
	
	
	/**
	 * 表单验证
	 */
	page.formcheck = function(){

		var PARENT_PKID = $("#PARENT_PKID").val();
		if(PARENT_PKID == null || PARENT_PKID == ""){
			layer.msg("请先选择左侧树节点!");
			return false;
		}
		
		var SD_LEVEL = $("#SD_LEVEL").val();
		if(SD_LEVEL == null || SD_LEVEL == ""){
			layer.msg("请先选择左侧树节点!");
			return false;
		}
		
		var SD_LEVEL_CLICK = $("#SD_LEVEL_CLICK").val();
		if(SD_LEVEL_CLICK == null || SD_LEVEL_CLICK == "" || SD_LEVEL_CLICK == "1"){//左侧点击学校、校区
			var SD_NAME = $("#SD_NAME").val();
			if(SD_NAME == null || SD_NAME == ""){
				layer.msg("名称不能为空!");
				return false;
			}
			
			var flag=false;
			var url = _basepath+"studentDorm/getStudentDormBySdName.json";
			$.ajax({
				 	type: 'post',
				    url: url,
				    async:false,
				    data:{SD_NAME:SD_NAME,PARENT_PKID:PARENT_PKID},
				    dataType:"json",
				    success: function(data) {
				    	if(data.result==true){
				    		flag=true;
						}
				    }
			});
			
			if(flag){
				layer.msg("该名称已存在，请更换!");
				return false;
			}
		}else if(SD_LEVEL_CLICK == "2"){//左侧点击楼
			//单个、批量
			var addType = $('input[name="addType"]:checked').val();
			if(addType == '0'){//单个
				var SD_NAME_LOUCENG_FROM = $("#SD_NAME_LOUCENG_FROM").val();
				if(SD_NAME_LOUCENG_FROM == null || SD_NAME_LOUCENG_FROM == ""){
					layer.msg("楼层不能为空!");
					return false;
				}
				if(Number(SD_NAME_LOUCENG_FROM) == 0){
					layer.msg("楼层不能为0!");
					return false;
				}
				if(Number(SD_NAME_LOUCENG_FROM) < -10){
					layer.msg("楼层必须为大于-10的整数!");
					return false;
				}
				
				
			}else if(addType == '1'){//批量
				var SD_NAME_LOUCENG_FROM = $("#SD_NAME_LOUCENG_FROM").val();
				var SD_NAME_LOUCENG_TO = $("#SD_NAME_LOUCENG_TO").val();
				if(SD_NAME_LOUCENG_FROM == null || SD_NAME_LOUCENG_FROM == "" || SD_NAME_LOUCENG_TO == null || SD_NAME_LOUCENG_TO == ""){
					layer.msg("楼层不能为空!");
					return false;
				}
				if(Number(SD_NAME_LOUCENG_FROM) < -10){
					layer.msg("楼层起必须为大于-10的整数!");
					return false;
				}
				if(Number(SD_NAME_LOUCENG_TO) < -10){
					layer.msg("楼层止必须为大于-10的整数!");
					return false;
				}
				if(Number(SD_NAME_LOUCENG_FROM) >= Number(SD_NAME_LOUCENG_TO)){
					layer.msg("起始楼层要小于终止楼层!");
					return false;
				}
				
				
			}
			
		}else if(SD_LEVEL_CLICK == "3"){//左侧点击楼层
			//单个、批量
			var addType = $('input[name="addType"]:checked').val();
			if(addType == '0'){//单个
				var SD_NAME_XULIEHAO_FROM = $("#SD_NAME_XULIEHAO_FROM").val();
				if(SD_NAME_XULIEHAO_FROM == null || SD_NAME_XULIEHAO_FROM == ""){
					layer.msg("序列号不能为空!");
					return false;
				}
				if(isNaN(Number(SD_NAME_XULIEHAO_FROM))){
					layer.msg("序列号必须为大于0的整数!");
					return false;
				}
				if(Number(SD_NAME_XULIEHAO_FROM) <= 0){
					layer.msg("序列号必须为大于0的整数!");
					return false;
				}
				
				
			}else if(addType == '1'){//批量
				var SD_NAME_XULIEHAO_FROM = $("#SD_NAME_XULIEHAO_FROM").val();
				var SD_NAME_XULIEHAO_TO = $("#SD_NAME_XULIEHAO_TO").val();
				if(SD_NAME_XULIEHAO_FROM == null || SD_NAME_XULIEHAO_FROM == "" || SD_NAME_XULIEHAO_TO == null || SD_NAME_XULIEHAO_TO == ""){
					layer.msg("序列号不能为空!");
					return false;
				}
				if(isNaN(Number(SD_NAME_XULIEHAO_FROM))){
					layer.msg("序列号起必须为大于0的整数!");
					return false;
				}
				if(Number(SD_NAME_XULIEHAO_FROM) <= 0){
					layer.msg("序列号起必须为大于0的整数!");
					return false;
				}
				if(isNaN(Number(SD_NAME_XULIEHAO_TO))){
					layer.msg("序列号止必须为大于0的整数!");
					return false;
				}
				if(Number(SD_NAME_XULIEHAO_TO) <= 0){
					layer.msg("序列号止必须为大于0的整数!");
					return false;
				}
				if(Number(SD_NAME_XULIEHAO_FROM) >= Number(SD_NAME_XULIEHAO_TO)){
					layer.msg("起始序列号要小于终止序列号!");
					return false;
				}
				
				
			}
			
			var T_STUDENT_DORM_TYPE_PKID = $("#T_STUDENT_DORM_TYPE_PKID  option:selected").val();
			if(T_STUDENT_DORM_TYPE_PKID == null || T_STUDENT_DORM_TYPE_PKID == ""){
				layer.msg("请选择宿舍类型!");
				return false;
			}
			
			var CHUANGCOUNT = $("#CHUANGCOUNT").val();
			if(CHUANGCOUNT == null || CHUANGCOUNT == ""){
				layer.msg("床位数不能为空！");
				return false;
			}
			if(isNaN(Number(CHUANGCOUNT))){
				layer.msg("床位数必须为大于0的整数!");
				return false;
			}
			if(Number(CHUANGCOUNT) <= 0){
				layer.msg("床位数必须为大于0的整数!");
				return false;
			}
		}
		
		
		return true;
	};
	
	
	/**
	 * 保存
	 */
	page.save = function(dialog){
		var datas = new Array();
		var PKID = $("#PKID").val();
		datas.push({"name":"PKID","value":PKID});
		
		var SD_LEVEL = $("#SD_LEVEL").val();
		datas.push({"name":"SD_LEVEL","value":SD_LEVEL});
		
		var PARENT_PKID = $("#PARENT_PKID").val();
		datas.push({"name":"PARENT_PKID","value":PARENT_PKID});
		
		if(SD_LEVEL == '1'){//添加校区
			var SD_NAME = $("#SD_NAME").val();
			datas.push({"name":"SD_NAME","value":SD_NAME});
		}else if(SD_LEVEL == '2'){//添加楼
			var SD_NAME = $("#SD_NAME").val();
			datas.push({"name":"SD_NAME","value":SD_NAME});
			
		}else if(SD_LEVEL == '3'){//添加楼层
			datas.push({"name":"ADD_DORM_TYPE","value":"ADD_LOUCENG"});
			var addType = $('input[name="addType"]:checked').val();
			datas.push({"name":"ADD_TYPE","value":addType});
			
			var SD_NAME_LOUCENG_FROM = $("#SD_NAME_LOUCENG_FROM").val();
			
			if(addType == '0'){//单个
				datas.push({"name":"SD_NAME_LOUCENG_FROM","value":Number(SD_NAME_LOUCENG_FROM)});
				
			}else if(addType == '1'){//批量
				datas.push({"name":"SD_NAME_LOUCENG_FROM","value":Number(SD_NAME_LOUCENG_FROM)});
				
				var SD_NAME_LOUCENG_TO = $("#SD_NAME_LOUCENG_TO").val();
				datas.push({"name":"SD_NAME_LOUCENG_TO","value":Number(SD_NAME_LOUCENG_TO)});
				
			}
			
		}else if(SD_LEVEL == '4'){//添加房间
			datas.push({"name":"ADD_DORM_TYPE","value":"ADD_FANGJIAN"});
			
			var addType = $('input[name="addType"]:checked').val();
			datas.push({"name":"ADD_TYPE","value":addType});
			
			var SD_NAME_XULIEHAO_FROM = $("#SD_NAME_XULIEHAO_FROM").val();
			if(addType == '0'){//单个
				datas.push({"name":"SD_NAME_XULIEHAO_FROM","value":Number(SD_NAME_XULIEHAO_FROM)});
				
			}else if(addType == '1'){//批量
				datas.push({"name":"SD_NAME_XULIEHAO_FROM","value":Number(SD_NAME_XULIEHAO_FROM)});
				
				var SD_NAME_XULIEHAO_TO = $("#SD_NAME_XULIEHAO_TO").val();
				datas.push({"name":"SD_NAME_XULIEHAO_TO","value":Number(SD_NAME_XULIEHAO_TO)});
				
			}
			var SD_NAME_QIANZHUI = $("#SD_NAME_QIANZHUI").val();
			datas.push({"name":"SD_PREFIX","value":SD_NAME_QIANZHUI});
			
			var T_STUDENT_DORM_TYPE_PKID = $("#T_STUDENT_DORM_TYPE_PKID  option:selected").val();
			datas.push({"name":"T_STUDENT_DORM_TYPE_PKID","value":T_STUDENT_DORM_TYPE_PKID});
			
			var CHUANGCOUNT = $("#CHUANGCOUNT").val();
			datas.push({"name":"CHUANGCOUNT","value":CHUANGCOUNT});
			
			var SD_SEX = $('input[name="SD_SEX"]:checked').val();
			datas.push({"name":"SD_SEX","value":SD_SEX});
			
		}
		
		
		datas.push({"name":"ACTION","value":"add"});
		
		var saveUrl = _basepath+"studentDorm/addUpdateStudentDorm.json";
		$.ajax({
			 type: 'post',
			    url: saveUrl,
			    data: datas,
			    dataType:"json",
			    success: function(data) {
			    	if(data.result == true){
			    		layer.msg("保存成功!");
			    		$("#departmentTreeJsonDataDiv").val(data.studentDormTreeJsonData);//左侧树json数据
			    		loadTree(PARENT_PKID);//加载左侧树
			    		refTable();
			    		dialog.close();
			    		//刷新页面
					}else{
						if(data.errorinfo != ''){
							layer.msg(data.errorinfo);
						}else{
							layer.msg("保存失败!");
						}
					}
			    }
		});
		
	};
	
	
	page.saveFlag = false;
	/**
	 * 添加页面
	 */
	page.add = function(event){
		//获取点击的节点
		var pkid = null;
		var selectNode = $('#departmentTreeview').treeview('getSelected', null);
		if(selectNode == null || selectNode.length<=0){
			layer.msg("请选择一个树节点");
			return;
		}else{
			 pkid = selectNode[0].href;//节点pkid
		}
		
		var dialog = BootstrapDialog.show({
			title:'新增宿舍信息',
			message: $('<div></div>').load("studentDorm/toAddUpdateStudentDorm.json?pkid="+pkid+"&action=add"),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
                label: '确定',
                cssClass: 'btn-danger',
                action: function(dialog) {
                	//防止重复点击
                	if(page.saveFlag){
            			return;
            		}
                	page.saveFlag = true;
                	
                	var checkRst = page.formcheck();
                	if(checkRst){
                		//保存
                		page.save(dialog);
                	}
                	
                	page.saveFlag = false;
                }
            }, {
                label: '取消',
                cssClass: 'btn-default',
                action: function(dialog) {
                	page.saveFlag = false;
                	dialog.close();
                }
            }],
            onshow: function(dialogRef){
            	//内容加载完成，但是弹出层UI还没有显示时调用
            	$("#SD_NAME").val("");
            },
            onshown: function(dialogRef){
            	//内容加载完成，弹出层UI显示时调用
            	$("#SD_NAME").val("");
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
	 * 修改页面
	 */
	page.edit = function(pkid){};
	
	
	/**
	 * 初始化
	 */
	page.init = function(){
		/*$.ajaxSetup({
		    async: false // 默认同步加载
		});*/
		
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
	    var url = _basepath+"authorityManage/student-department-table.json";
	    $("#btn_search").bind("click", function(){
            var searchsr = $("#searchsr").val();
            var treeNodeId = $("#treeNodeId").val();
            if(treeNodeId==''){
            	layer.msg("请先选择左侧组织节点!");
            	return false;
            }
            var opt = {
			    url: url,
			    silent: true,
			    query:{
			    	"seText":searchsr,
			    	"PKID":treeNodeId
			    }
			};
//            page.loadTree();
            $("#departmentTable").bootstrapTable('refresh', opt);
		});
	    //end 查询
		
	    
	    //新增按钮注册事件
	    $("#btn_add").click(page.add);
	    
		loadTree();//加载左侧树
		page.loadTable();//加载表格
		
	};
	
	page.init();
	
})(window,jQuery,layer);

/**
 * 表单验证
 */
var formcheck = function(){

	var PKID = $("#PKID").val();
	var PARENT_PKID = $("#PARENT_PKID").val();
	if(PARENT_PKID == null || PARENT_PKID == ""){
		layer.msg("请先选择左侧树节点!");
		return false;
	}
	
	var SD_LEVEL = $("#SD_LEVEL").val();
	if(SD_LEVEL == null || SD_LEVEL == ""){
		layer.msg("请先选择左侧树节点!");
		return false;
	}
	
	var SD_LEVEL_CLICK = $("#SD_LEVEL_CLICK").val();
	if(SD_LEVEL_CLICK == null || SD_LEVEL_CLICK == "" || SD_LEVEL_CLICK == "1"){//左侧点击学校、校区
		var SD_NAME = $("#SD_NAME").val();
		if(SD_NAME == null || SD_NAME == ""){
			layer.msg("名称不能为空!");
			return false;
		}
		
		var flag=false;
		var url = _basepath+"studentDorm/getStudentDormBySdName.json";
		$.ajax({
			 	type: 'post',
			    url: url,
			    async:false,
			    data:{SD_NAME:SD_NAME,PARENT_PKID:PARENT_PKID},
			    dataType:"json",
			    success: function(data) {
			    	if(data.result==true){
			    		if(data.dormPd.PKID != PKID){
			    			flag=true;
			    		}
			    		
					}
			    }
		});
		
		if(flag){
			layer.msg("该名称已存在，请更换!");
			return false;
		}
	}else if(SD_LEVEL_CLICK == "2"){//左侧点击楼
		//单个、批量
		var addType = $('input[name="addType"]:checked').val();
		if(addType == '0'){//单个
			var SD_NAME_LOUCENG_FROM = $("#SD_NAME_LOUCENG_FROM").val();
			if(SD_NAME_LOUCENG_FROM == null || SD_NAME_LOUCENG_FROM == ""){
				layer.msg("楼层不能为空!");
				return false;
			}
			
		}else if(addType == '1'){//批量
			var SD_NAME_LOUCENG_FROM = $("#SD_NAME_LOUCENG_FROM").val();
			var SD_NAME_LOUCENG_TO = $("#SD_NAME_LOUCENG_TO").val();
			if(SD_NAME_LOUCENG_FROM == null || SD_NAME_LOUCENG_FROM == "" || SD_NAME_LOUCENG_TO == null || SD_NAME_LOUCENG_TO == ""){
				layer.msg("楼层不能为空!");
				return false;
			}
			
		}
		
	}else if(SD_LEVEL_CLICK == "4"){//编辑房间
		
		var SD_NAME = $("#SD_NAME").val();
		if(SD_NAME == null || SD_NAME == ""){
			layer.msg("房间名不能为空!");
			return false;
		}
		
		/*var SD_NAME_QIANZHUI = $("#SD_NAME_QIANZHUI").val();
		if(SD_NAME_QIANZHUI == null || SD_NAME_QIANZHUI == ""){
			layer.msg("前缀不能为空!");
			return false;
		}*/
		
		var SD_SEX = $('input[name="SD_SEX"]:checked').val();
		if(SD_SEX == null || SD_SEX == ""){
			layer.msg("性别不能为空!");
			return false;
		}
	}
	
	
	return true;
};

/**
 * 保存
 */
var updateStudentDorm = function(dialog){
	var datas = new Array();
	var PKID = $("#PKID").val();
	datas.push({"name":"PKID","value":PKID});
	
	var SD_LEVEL_CLICK = $("#SD_LEVEL_CLICK").val();
	
	var PARENT_PKID = $("#PARENT_PKID").val();
	datas.push({"name":"PARENT_PKID","value":PARENT_PKID});
	
	if(SD_LEVEL_CLICK == '1'){//编辑校区
		var SD_NAME = $("#SD_NAME").val();
		datas.push({"name":"SD_NAME","value":SD_NAME});
	}else if(SD_LEVEL_CLICK == '2'){//添加楼
		var SD_NAME = $("#SD_NAME").val();
		datas.push({"name":"SD_NAME","value":SD_NAME});
		
	}else if(SD_LEVEL_CLICK == '3'){//编辑楼层
		
		
	}else if(SD_LEVEL_CLICK == '4'){//编辑房间
		datas.push({"name":"ADD_DORM_TYPE","value":"ADD_FANGJIAN"});
		
		
		var SD_NAME_XULIEHAO_FROM = $("#SD_NAME_XULIEHAO_FROM").val();
		datas.push({"name":"SD_NAME","value":SD_NAME_XULIEHAO_FROM});
		
		var SD_NAME_QIANZHUI = $("#SD_NAME_QIANZHUI").val();
		datas.push({"name":"SD_PREFIX","value":SD_NAME_QIANZHUI});
		
		var SD_SEX = $('input[name="SD_SEX"]:checked').val();
		datas.push({"name":"SD_SEX","value":SD_SEX});
		
	}
	datas.push({"name":"ACTION","value":"edit"});
	var saveUrl = _basepath+"studentDorm/addUpdateStudentDorm.json";
	$.ajax({
		 type: 'post',
		    url: saveUrl,
		    data: datas,
		    dataType:"json",
		    success: function(data) {
		    	if(data.result == true){
		    		layer.msg("保存成功!");
		    		$("#departmentTreeJsonDataDiv").val(data.studentDormTreeJsonData);//左侧树json数据
		    		loadTree(PKID);//加载左侧树
		    		refTable();
		    		dialog.close();
		    		//刷新页面
				}else{
					if(data.errorinfo != ''){
						layer.msg(data.errorinfo);
					}else{
						layer.msg("保存失败!");
					}
				}
		    }
	});
};

var studentTreeNodeEditFlag = false;
//点击左侧树节点编辑
var studentTreeNodeEdit = function(obj){
	
	var pkid = $(obj).attr("val");
	//选中节点
	if(pkid!=null && typeof(pkid)!='undefined'){
		var node = $('#departmentTreeview').treeview('getNodeByHref', pkid);
		$('#departmentTreeview').treeview('toggleNodeSelected', [ node.nodeId, { silent: true } ]);
	}
	
	var dialog = BootstrapDialog.show({
		title:'编辑宿舍信息',
		message: $('<div></div>').load("studentDorm/toAddUpdateStudentDorm.json?pkid="+pkid+"&action=edit"),
		closable: true,//是否显示叉号
		draggable: true,//可以拖拽
		buttons: [{
            label: '确定',
            cssClass: 'btn-danger',
            action: function(dialog) {
            	
            	if(studentTreeNodeEditFlag){
            		return false;
            	}
            	studentTreeNodeEditFlag = true;
            	
            	var checkRst = formcheck();
            	if(checkRst){
            		//保存
            		$("#treeNodeId").val(pkid);
            		updateStudentDorm(dialog);
            	}
            	
            	studentTreeNodeEditFlag = false;
            }
        }, {
            label: '取消',
            cssClass: 'btn-default',
            action: function(dialog) {
            	studentTreeNodeEditFlag = false;
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
//点击左侧树节点删除
var studentTreeNodeDel = function(obj){
	var pkid = $(obj).attr("val");
	//选中节点
	if(pkid!=null && typeof(pkid)!='undefined'){
		var node = $('#departmentTreeview').treeview('getNodeByHref', pkid);
		$('#departmentTreeview').treeview('toggleNodeSelected', [ node.nodeId, { silent: true } ]);
	}
	//检查是否有子节点
	/*var rst = isHaveChildNode(pkid);
	if(rst){//有子节点
		layer.msg("有子节点的宿舍资源不能删除!");
		return;
	}*/
	
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
						url : _basepath + 'studentDorm/delStudentDorm.json?pkid='+ pkid,
						success : function(data) {
							if (true == data.result) {
								layer.msg("删除成功！");
								$("#departmentTreeJsonDataDiv").val(data.studentDormTreeJsonData);//左侧树json数据
//								var pkid = $("#treeNodeId").val();
								var parent_pkid = data.pd.lastPkid;
								$("#treeNodeId").val(parent_pkid);
								loadTree(parent_pkid);//加载左侧树
					    		refTable();
								dialogRef.close();
							}else if(data.errorinfo != null || data.errorinfo != ''){
								layer.msg(data.errorinfo);
							}else{
								layer.msg("删除失败！");
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
	});
};