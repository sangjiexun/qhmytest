
/**
 * 新增更新宿舍
 * @param win
 * @param $
 */
(function(win,$,layer){
	
	var page = {};
	
	page.changeAddType = function(){
		var addType = $('input[name="addType"]:checked').val();
		if(addType == '0'){//单个
			$("#SD_NAME_LOUCENG_TO_SPAN").css("display","none");
			$("#SD_NAME_XULIEHAO_TO_SPAN").css("display","none");
		}else if(addType == '1'){//批量
			$("#SD_NAME_LOUCENG_TO_SPAN").css("display","block");
			$("#SD_NAME_XULIEHAO_TO_SPAN").css("display","block");
		}
	};
	
	/**
	 * 初始化
	 */
	page.init = function(){
		$.ajaxSetup({
		    aysnc: false // 默认同步加载
		});
		
		$('input[name="addType"]').change(function() {
	    	page.changeAddType();
	    }); 
	};
	
	page.init();
	
	/*
	 * 校区卡片List
	 */
	/*page.setCardListHtml = function(pkid){
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
							        '<div class="sg_wsolidH bg-info">'+
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
	};*/
	
	/**
	 * 加载tree
	 */
	/*page.loadTree = function(parent_pkid){
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
					    			$("#btn_add").attr("disabled","disabled");
					    		}else{
					    			$("#btn_add").removeAttr("disabled");
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
//		$('#departmentTreeview').treeview('toggleNodeChecked', [ arrayInfo[i].nodeId, { silent: true } ]);
		
		if(parent_pkid!=null && typeof(parent_pkid)!='undefined'){
			var node = $('#departmentTreeview').treeview('getNodeByHref', parent_pkid);
			if(node.nodeId != 'undefined' && node.nodeId != null){
				node.state.expanded = true;
				$('#departmentTreeview').treeview('revealNode', [node.nodeId, { silent: true } ]);
			}
			$('#departmentTreeview').treeview('toggleNodeSelected', [ node.nodeId, { silent: true } ]);
		}
		

		//左侧树点击编辑
		$(".studentTreeNodeEdit").click(function(){
			var pkid = $(this).attr("val");
			alert("编辑树"+pkid);
		});

		//左侧树点击删除
		$(".studentTreeNodeDel").click(function(){
			var pkid = $(this).attr("val");
			alert("删除树"+pkid);
		});
		
	};*/
	
})(window,jQuery,layer);


