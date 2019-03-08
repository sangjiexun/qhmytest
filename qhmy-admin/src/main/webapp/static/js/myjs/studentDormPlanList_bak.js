//@ sourceURL=studentDormPlanList.js
/**
 * 学生宿舍计划
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
var setCardListHtml = function(pkid,RUXUENIANFEN,T_STUDENT_DORM_TYPE_PKID,STATUS,DEPARTMENT_PKID,dormLevel,SD_SEX){
	var cardListHtml = '';
	$.ajax({
		  type: 'POST',
		  url: _basepath+"studentDorm/getStudentDormPlanList.json",
		  async: false,
		  data: {
			  pkid:pkid,
			  RUXUENIANFEN:RUXUENIANFEN,
			  T_STUDENT_DORM_TYPE_PKID:T_STUDENT_DORM_TYPE_PKID,
			  STATUS:STATUS,
			  DEPARTMENT_PKID:DEPARTMENT_PKID,
			  dormLevel:dormLevel,
			  SD_SEX:SD_SEX
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
						var CHUANGCOUNT_RUZHU = 0;
						var NANCOUNT = 0;
						var NVCOUNT = 0;
						var NANCOUNT_RUZHU = 0;
						var NVCOUNT_RUZHU = 0;
						
						LOUCOUNT = studentDorm.LOUCOUNT;
		    			CENGCOUNT = studentDorm.CENGCOUNT;
		    			FANGCOUNT = studentDorm.FANGCOUNT;
						CHUANGCOUNT = studentDorm.CHUANGCOUNT;
		    			CHUANGCOUNT_RUZHU = studentDorm.CHUANGCOUNT_RUZHU;
		    			
		    			NANCOUNT = studentDorm.NANCOUNT;
		    			NVCOUNT = studentDorm.NVCOUNT;
		    			NANCOUNT_RUZHU = studentDorm.NANCOUNT_RUZHU;
		    			NVCOUNT_RUZHU = studentDorm.NVCOUNT_RUZHU;
						
		    			if(i == 0 || i % 5 == 0){//5个卡片一行
		    				cardListHtml += '<div class="row">';
		    			}
		    			if(data.dormLevel == '0'){//点击学校，显示校区
		    				
		    				cardListHtml += '<div class="col-md-2 col-sm-3 col-xs-4" style="margin-bottom:15px;cursor:pointer;" onclick="javascript:clickCard(\''+studentDorm.PKID+'\');">'+
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
										                '<li style="margin-top:15px;">男：'+
										                	'<span>'+NANCOUNT_RUZHU +'/'+ NANCOUNT+'</span>'+
										                '</li>'+
										                '<li  style="">女：'+
										                	'<span>'+NVCOUNT_RUZHU +'/'+ NVCOUNT+'</span>'+
										                '</li>'+
										                '<li  style="margin-bottom:15px;">总：'+
										                	'<span>'+CHUANGCOUNT_RUZHU +'/'+ CHUANGCOUNT+'</span>'+
										                '</li>'+
										                '<li  style="margin-bottom:15px;">'+
										                	'<span>'+studentDorm.NANHOVERSTR +''+ studentDorm.NVHOVERSTR+'</span>'+
										                '</li>'+
										                '<li class="clearfix"></li>'+
										            '</ul>'+
										            '<div class="progress">'+
							                        	'<div class="progress-bar progress-bar-danger" style="width:'+(CHUANGCOUNT_RUZHU/CHUANGCOUNT)*100+'%;"></div>'+
							                   		'</div>'+
										        '</div>'+
										    '</div>';
						}else if(data.dormLevel == '1'){//点击校区，显示楼
							cardListHtml += '<div class="col-md-2 col-sm-3 col-xs-4" style="margin-bottom:15px;cursor:pointer;" onclick="javascript:clickCard(\''+studentDorm.PKID+'\');">'+
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
											                '<li class="sg_loucengS">总床数'+
											                    '<div class="sg_lcNumber">'+CHUANGCOUNT+'</div>'+
											                '</li>'+
											                '<li style="margin-top:15px;">男：'+
											                	'<span>'+NANCOUNT_RUZHU +'/'+ NANCOUNT+'</span>'+
											                '</li>'+
											                '<li  style="">女：'+
											                	'<span>'+NVCOUNT_RUZHU +'/'+ NVCOUNT+'</span>'+
											                '</li>'+
											                '<li  style="margin-bottom:15px;">总：'+
											                	'<span>'+CHUANGCOUNT_RUZHU +'/'+ CHUANGCOUNT+'</span>'+
											                '</li>'+
											                '<li class="clearfix"></li>'+
											            '</ul>'+
											            '<div class="progress">'+
								                        	'<div class="progress-bar progress-bar-danger" style="width:'+(CHUANGCOUNT_RUZHU/CHUANGCOUNT)*100+'%;"></div>'+
								                   		'</div>'+
											        '</div>'+
											    '</div>';
						}else if(data.dormLevel == '2'){//点击楼，显示楼层
							
							cardListHtml += '<div class="col-md-2 col-sm-3 col-xs-4" style="margin-bottom:15px;cursor:pointer;" onclick="javascript:clickCard(\''+studentDorm.PKID+'\');">'+
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
											                '<li  style="margin-bottom:15px;">已入住:'+
											                	'<span>'+CHUANGCOUNT_RUZHU+'</span>'+
											                '</li>'+
											                '<li class="clearfix"></li>'+
											            '</ul>'+
											            '<div class="progress">'+
								                        	'<div class="progress-bar progress-bar-danger" style="width:'+(CHUANGCOUNT_RUZHU/CHUANGCOUNT)*100+'%;"></div>'+
								                   		'</div>'+
											        '</div>'+
											    '</div>';
						}else if(data.dormLevel == '3'){//点击楼层，显示房间
							
							cardListHtml += 
							'<div class="col-md-2 col-sm-3 col-xs-4" style="margin-bottom:15px;cursor:pointer;" onclick="javascript:clickCard(\''+studentDorm.PKID+'\');">'+
						        '<div class="sg_wsolid bg-info">'+
						            '<ul>'+
						                '<li class="sg_louhao">'+studentDorm.SD_NAME+'</li>'+
						                '<li class="sg_loucengS">床位数'+
						                    '<div class="sg_lcNumber">'+CHUANGCOUNT+'</div>'+
						                '</li>'+
						                '<li class="clearfix"></li>'+
						                '<li style="margin-top:15px;">总床位：'+
						                    '<span>'+CHUANGCOUNT+'</span>'+
						                '</li>'+
						                '<li  style="margin-bottom:15px;">已入住:'+
						                	'<span>'+CHUANGCOUNT_RUZHU+'</span>'+
						                '</li>'+
						                '<li class="clearfix"></li>'+
						            '</ul>'+
						            '<div class="progress">'+
			                        	'<div class="progress-bar progress-bar-danger" style="width:'+(CHUANGCOUNT_RUZHU/CHUANGCOUNT)*100+'%;"></div>'+
			                   		'</div>'+
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
var refTable = function(click_pkid){
	
	$("#cardList").html("");
	
//	var pkid = $("#treeNodeId").val();
	var RUXUENIANFEN = $("#RUXUENIANFEN  option:selected").val();
    var T_STUDENT_DORM_TYPE_PKID = $("#T_STUDENT_DORM_TYPE_PKID  option:selected").val();
    var STATUS = $("#STATUS  option:selected").val();
    var DEPARTMENT_PKID = $('#orgtree2').val();
    var SD_SEX = $("#SD_SEX  option:selected").val();
    
	var r = Math.random();
	var url = _basepath+"studentDorm/getStudentDormLevel.json?r="+r;
	$.ajax({
		 	type: 'post',
		    url: url,
		    async:false,
		    data:{pkid:click_pkid},
		    dataType:"json",
		    success: function(data) {
		    	if(data.result==true){
		    		$("#cardList").html(setCardListHtml(click_pkid,RUXUENIANFEN,T_STUDENT_DORM_TYPE_PKID,STATUS,DEPARTMENT_PKID,data.dormLevel,SD_SEX));
				}
		    }
	});
	
};


var selectSchoolPkid = $("#treeNodeId").val();
/**
 * 加载tree
 */
var loadTree = function(click_pkid){
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
			//节点选中时触发
			$("#cardList").html("");
			var pkid = data.href;
			var parentPkid = data.parentId;
			
			/*
			 * 如果左侧树选择的是学校
			 * 切换不同的学校时清空院校专业
			 */
			//通过左侧选择的某一节点获得其对应的最顶级节点的PKID
			if(data.type == '0'){
				if(selectSchoolPkid != pkid){
	    			selectSchoolPkid = pkid;
	    			$("#citySel2").attr("value","");
					$("#orgtree2").attr("value","");
	    		}
			}else{
				$.ajax({
				 	type: 'post',
				    url: _basepath+"studentDorm/getStudentDormTop.json",
				    async:false,
				    data:{pkid:pkid},
				    dataType:"json",
				    success: function(data2) {
				    	if(data2.result==true){
				    		if(selectSchoolPkid != data2.dormTopPkid){
				    			selectSchoolPkid = data2.dormTopPkid;
				    			$("#citySel2").attr("value","");
								$("#orgtree2").attr("value","");
				    		}
						}
				    }
				});
			}
			
			//刷新右侧卡片
			refTable(pkid);
			
			var id=data.href;
			$("#treeNodeId").val(id);
			
		}
	 });
	
	//选中指定节点
//	$('#departmentTreeview').treeview('toggleNodeChecked', [ arrayInfo[i].nodeId, { silent: true } ]);
	if(click_pkid!=null && typeof(click_pkid)!='undefined'){
		var node = $('#departmentTreeview').treeview('getNodeByHref', click_pkid);
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
		//$("#cardList").html(setCardListHtml(pkid));
		
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
		
		
		$("#cardList").html("");
		
//		var pkid = $("#treeNodeId").val();
		var RUXUENIANFEN = $("#RUXUENIANFEN  option:selected").val();
	    var T_STUDENT_DORM_TYPE_PKID = $("#T_STUDENT_DORM_TYPE_PKID  option:selected").val();
	    var STATUS = $("#STATUS  option:selected").val();
	    var DEPARTMENT_PKID = $('#orgtree2').val();
	    var SD_SEX = $("#SD_SEX  option:selected").val();
	    
		var r = Math.random();
		var url = _basepath+"studentDorm/getStudentDormLevel.json?r="+r;
		$.ajax({
			 	type: 'post',
			    url: url,
			    async:false,
			    data:{pkid:pkid},
			    dataType:"json",
			    success: function(data) {
			    	if(data.result==true){
			    		$("#cardList").html(setCardListHtml(pkid,RUXUENIANFEN,T_STUDENT_DORM_TYPE_PKID,STATUS,DEPARTMENT_PKID,data.dormLevel,SD_SEX));
					}
			    }
		});
	};
	
	
	
	/**
	 * 分配表单验证
	 */
	page.formcheckAllot = function(pkid){

		if(pkid == null || pkid == ""){
			layer.msg("请先选择左侧一个节点！");
			return false;
		}
		
		var STUDENT_DORM_PKIDS = $("#orgtree3").val();
		if(STUDENT_DORM_PKIDS == null || STUDENT_DORM_PKIDS == ""){
			layer.msg("请先选择宿舍！");
			return false;
		}
		
		var DEPARTMENT_PKIDS = $("#orgtree4").val();
		if(DEPARTMENT_PKIDS == null || DEPARTMENT_PKIDS == ""){
			layer.msg("请先选择院校专业！");
			return false;
		}
		
		return true;
	};
	
	/**
	 * 回收表单验证
	 */
	page.formcheckRecovery = function(pkid){

//		var pkid = $("#treeNodeId").val();
		if(pkid == null || pkid == ""){
			layer.msg("请先选择左侧一个节点！");
			return false;
		}
		var STUDENT_DORM_PKIDS = $("#orgtree5").val();
		if(STUDENT_DORM_PKIDS == null || STUDENT_DORM_PKIDS == ""){
			layer.msg("请先选择宿舍！");
			return false;
		}
		return true;
	};
	
	/**
	 * 保存分配
	 */
	page.saveAllot = function(pkid,click_pkid,dialog){
		var datas = new Array();
//		var pkid = $("#treeNodeId").val();
		datas.push({"name":"pkid","value":pkid});
		
		var STUDENT_DORM_PKIDS = $("#orgtree3").val();
		datas.push({"name":"STUDENT_DORM_PKIDS","value":STUDENT_DORM_PKIDS});
		var DEPARTMENT_IDS = $("#isXM3").val();
		datas.push({"name":"DEPARTMENT_IDS","value":DEPARTMENT_IDS});
		
		var DEPARTMENT_PKID = $("#orgtree4").val();
		datas.push({"name":"DEPARTMENT_PKID","value":DEPARTMENT_PKID});
		
		var saveUrl = _basepath+"studentDorm/allotStudentDorm.json";
		$.ajax({
			 type: 'post',
			    url: saveUrl,
			    data: datas,
			    dataType:"json",
			    success: function(data) {
			    	if(data.result == true){
			    		layer.msg("分配成功!");
			    		$("#departmentTreeJsonDataDiv").val(data.studentDormTreeJsonData);//左侧树json数据
			    		loadTree(click_pkid);//加载左侧树
			    		refTable(click_pkid);
			    		dialog.close();
			    		//刷新页面
					}else{
						if(data.errorinfo != ''){
							layer.msg(data.errorinfo);
						}else{
							layer.msg("分配失败!");
						}
					}
			    }
		});
	};
	
	/**
	 * 保存回收
	 */
	page.saveRecovery = function(pkid,click_pkid,dialog){
		var datas = new Array();
		
//		var pkid = $("#treeNodeId").val();
		datas.push({"name":"pkid","value":pkid});
		
		var STUDENT_DORM_PKIDS = $("#orgtree5").val();
		datas.push({"name":"STUDENT_DORM_PKIDS","value":STUDENT_DORM_PKIDS});
		
		var saveUrl = _basepath+"studentDorm/recoveryStudentDorm.json";
		$.ajax({
			 type: 'post',
			    url: saveUrl,
			    data: datas,
			    dataType:"json",
			    success: function(data) {
			    	if(data.result == true){
			    		layer.msg("回收成功!");
			    		$("#departmentTreeJsonDataDiv").val(data.studentDormTreeJsonData);//左侧树json数据
			    		loadTree(click_pkid);//加载左侧树
			    		refTable(click_pkid);
			    		dialog.close();
			    		//刷新页面
					}else{
						if(data.errorinfo != ''){
							layer.msg(data.errorinfo);
						}else{
							layer.msg("回收失败，请重试!");
						}
					}
			    }
		});
	};
	
	
	page.allotFlag = false;
	/**
	 * 分配页面
	 */
	page.allot = function(event){
		//获取点击的节点
		var pkid = null;
		var click_pkid = null;
		var selectNode = $('#departmentTreeview').treeview('getSelected', null);
		if(selectNode == null || selectNode.length<=0){
			layer.msg("请选择左侧一个节点");
			return;
		}else if(selectNode[0].type == '0'){
			pkid = selectNode[0].href;//节点pkid
			click_pkid = pkid;
		}else{
			pkid = selectNode[0].href;//节点pkid
		    click_pkid = pkid;
			
			//通过左侧选择的某一节点获得其对应的最顶级节点的PKID
			$.ajax({
				 	type: 'post',
				    url: _basepath+"studentDorm/getStudentDormTop.json",
				    async:false,
				    data:{pkid:pkid},
				    dataType:"json",
				    success: function(data) {
				    	if(data.result==true){
				    		pkid = data.dormTopPkid;
						}else{
							layer.msg("请选择左侧一个节点");
							return;
						}
				    }
			});
		}
		
		$("#treeNodeId").val(pkid);
		
		var dialog = BootstrapDialog.show({
			title:'分配宿舍',
			message: $('<div></div>').load("studentDorm/toAllotStudentDorm.json?pkid="+pkid),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
                label: '确定',
                cssClass: 'btn-danger',
                action: function(dialog) {
                	if(page.allotFlag){
                		return;
                	}
                	page.allotFlag = true;
                	
                	var checkRst = page.formcheckAllot(pkid);
                	if(checkRst){
                		//保存
                		page.saveAllot(pkid,click_pkid,dialog);
                	}
                	
                	page.allotFlag = false;
                }
            }, {
                label: '取消',
                cssClass: 'btn-default',
                action: function(dialog) {
                	page.allotFlag = false;
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
	
	page.recoveryFlag = false;
	/**
	 * 回收宿舍页面
	 */
	page.recovery = function(event){
		//获取点击的节点
		var pkid = null;
		var click_pkid = null;
		var selectNode = $('#departmentTreeview').treeview('getSelected', null);
		if(selectNode == null || selectNode.length<=0){
			layer.msg("请选择左侧一个节点");
			return;
		}else if(selectNode[0].type == '0'){
			pkid = selectNode[0].href;//节点pkid
			click_pkid = pkid;
		}else{
			pkid = selectNode[0].href;//节点pkid
			click_pkid = pkid;
			
			//通过左侧选择的某一节点获得其对应的最顶级节点的PKID
			$.ajax({
				 	type: 'post',
				    url: _basepath+"studentDorm/getStudentDormTop.json",
				    async:false,
				    data:{pkid:pkid},
				    dataType:"json",
				    success: function(data) {
				    	if(data.result==true){
				    		pkid = data.dormTopPkid;
						}else{
							layer.msg("请选择左侧一个节点");
							return;
						}
				    }
			});
		}
		$("#treeNodeId").val(pkid);
		
		var dialog = BootstrapDialog.show({
			title:'回收宿舍',
			message: $('<div></div>').load("studentDorm/toRecoveryStudentDorm.json?pkid="+pkid),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
                label: '确定',
                cssClass: 'btn-danger',
                action: function(dialog) {
                	
                	if(page.recoveryFlag){
                		return;
                	}
                	page.recoveryFlag = true;
                	
                	var checkRst = page.formcheckRecovery(pkid);
                	if(checkRst){
                		//保存
                		page.saveRecovery(pkid,click_pkid,dialog);
                	}
                	
                	page.recoveryFlag = false;
                }
            }, {
                label: '取消',
                cssClass: 'btn-default',
                action: function(dialog) {
                	page.recoveryFlag = false;
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
	 * 修改页面
	 */
	page.edit = function(pkid){};
	
	
	/**
	 * 初始化
	 */
	page.init = function(){
		$.ajaxSetup({
		    async: false // 默认同步加载
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
	    $("#btn_search").bind("click", function(){
            var pkid = '';
            var pkid_click = '';
            var selectNode = $('#departmentTreeview').treeview('getSelected', null);
    		if(selectNode == null || selectNode.length<=0){
    			layer.msg("请选择左侧一个节点");
    			return;
    		}else if(selectNode[0].type == '0'){
    			pkid = selectNode[0].href;//节点pkid
    		}else{
    			pkid = selectNode[0].href;//节点pkid
    			
    			//通过左侧选择的某一节点获得其对应的最顶级节点的PKID
    			$.ajax({
    				 	type: 'post',
    				    url: _basepath+"studentDorm/getStudentDormTop.json",
    				    async:false,
    				    data:{pkid:pkid},
    				    dataType:"json",
    				    success: function(data) {
    				    	if(data.result==true){
    				    		pkid = data.dormTopPkid;
    						}else{
    							layer.msg("请选择左侧一个节点");
    							return;
    						}
    				    }
    			});
    		}
    		pkid_click = selectNode[0].href;//节点pkid
    		
    		$("#treeNodeId").val(pkid);
			
			//刷新右侧卡片
			refTable(pkid_click);
			
			
		});
	    //end 查询
		
	    //分配按钮注册事件
	    $("#btn_allot").click(page.allot);
	    //回收按钮注册事件
	    $("#btn_recovery").click(page.recovery);
	    
	    
		loadTree();//加载左侧树
		page.loadTable();//加载表格
		
	};
	
	page.init();
	
})(window,jQuery,layer);

/**
 * 点击卡片下钻
 */
var clickCard = function(studentDormPkid){
	
	//左侧树选中节点
	if(studentDormPkid!=null && typeof(studentDormPkid)!='undefined'){
		var node = $('#departmentTreeview').treeview('getNodeByHref', studentDormPkid);
		if(node.nodeId != 'undefined' && node.nodeId != null){
			node.state.expanded = true;
			$('#departmentTreeview').treeview('revealNode', [node.nodeId, { silent: true } ]);
		}
		$('#departmentTreeview').treeview('toggleNodeSelected', [ node.nodeId, { silent: true } ]);
	}
	
	$("#treeNodeId").val(studentDormPkid);
	
	//刷新右侧卡片
	refTable(studentDormPkid);
};
