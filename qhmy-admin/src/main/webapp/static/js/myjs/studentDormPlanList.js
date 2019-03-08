//@ sourceURL=studentDormPlanList.js
/**
 * 学生宿舍计划
 * @param win
 * @param $
 */

/*
 * 截取字符串
 */
var subStrings=function(str,n){
	  var r=/[^\x00-\xff]/g;
	  if(str.replace(r,"mm").length<=n){return str;}
	  var m=Math.floor(n/2);
	  for(var i=m;i<str.length;i++){
	      if(str.substr(0,i).replace(r,"mm").length>=n){
	          return str.substr(0,i)+"...";
	      }
	  }
	  return str;
};

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

//刷新table
var refreshCardListTable = function(pkid_click,dormLevel){
	var pkid=$("#defaultTopPkid").val();
	var BANXING2 = $("#BANXING2  option:selected").val();
	var WENHUAKEXUEXIAO2 = $("#WENHUAKEXUEXIAO2  option:selected").val();
    var T_STUDENT_DORM_TYPE_PKID = $("#T_STUDENT_DORM_TYPE_PKID  option:selected").val();
    var STATUS = $("#STATUS  option:selected").val();
    var SD_SEX = $("#SD_SEX  option:selected").val();
	var url = _basepath+"studentDorm/getStudentDormTablelistPage.json";
 	 var opt = {
			url :url,
		    silent: true,
		    query:{
		    	pkid:pkid_click,
		    	BANXING:BANXING2,
		    	WENHUAKEXUEXIAO:WENHUAKEXUEXIAO2,
				T_STUDENT_DORM_TYPE_PKID:T_STUDENT_DORM_TYPE_PKID,
				STATUS:STATUS,
				SD_SEX:SD_SEX,
				dormLevel:dormLevel
		    }
		  };
	 $("#cardListTable").bootstrapTable('refresh', opt);
};
	
/*
 * 校区卡片List
 */
var setCardListHtml = function(pkid,BANXING2,WENHUAKEXUEXIAO2,T_STUDENT_DORM_TYPE_PKID,STATUS,dormLevel,SD_SEX,pageindex,pageSize){
	var cardListHtml = '';
	if(dormLevel == '2' || dormLevel == '3'){//点击楼、层，显示房间
		if($('.pl-item').eq(0).attr("class") == "pl-item active"){
			$(".pl-fanYe").css("display","block");
			$("#cardViewPage").css("display","block");
		}else{
			$(".pl-fanYe").css("display","none");
			$("#cardViewPage").css("display","none");
		}
		var pageNumber = Number($("#pageNumber").html());
		var limit = 48;
		if(typeof pageSize != 'undefined'){
			limit = pageSize;
		}
		if($(".page-size").html() != ''){
			limit = Number($(".page-size").html());
		}
		
		if(typeof pageindex == 'undefined' || pageindex == ''){
			pageindex = 1;
		}else if(pageindex == 'last'){
			pageindex = pageNumber - 1;
		}else if(pageindex == 'next'){
			pageindex = pageNumber + 1;
		}
		$.ajax({
			  type: 'POST',
			  url: _basepath+"studentDorm/getStudentDormTablelistPage.json",
			  async: false,
			  data: {
				  limit : limit, //页面大小
				  pageindex : pageindex,
				  pkid:pkid,
				  BANXING:BANXING2,
				  WENHUAKEXUEXIAO:WENHUAKEXUEXIAO2,
				  T_STUDENT_DORM_TYPE_PKID:T_STUDENT_DORM_TYPE_PKID,
				  STATUS:STATUS,
				  dormLevel:dormLevel,
				  SD_SEX:SD_SEX
			  },
			  dataType : "json",
			  success : function(data) {
				  var studentDormList = data.rows;
				  var isEnd = false;
				  
		    		for (var i = 0; i < studentDormList.length; i++) {
		    			var studentDorm = studentDormList[i];
		    			var rowCount = 6;
		    			if(i == 0 || i % rowCount == 0){//5个卡片一行
		    				cardListHtml += '<div class="row"><div class="container-fluid">';
		    			}
		    			
		    			var FANGSTATUS = studentDorm.FANGSTATUS;
		    			if(FANGSTATUS == '满'){
		    				FANGSTATUS = 'pl-man';
		    			}else if(FANGSTATUS == '未满'){
		    				FANGSTATUS = 'pl-wei';
		    			}else if(FANGSTATUS == '空'){
		    				FANGSTATUS = 'pl-kong';
		    			}
		    			cardListHtml += '<div class="col-lg-2 col-md-3 col-sm-4 pl-shanGe"  style="cursor:pointer;" onclick="javascript:clickCard(\''+studentDorm.PKID+'\');">'+
											'<div class="fangstatus '+FANGSTATUS+'">'+
												'<span>'+studentDorm.CHUANGCOUNT_RUZHU+'</span><span>/</span><span>'+studentDorm.CHUANGCOUNT+'</span>'+
											'</div>'+
											'<p class="pl-miaoShu" data-toggle="tooltip" data-placement="bottom" title="'+studentDorm.ROOM_NAMES+'">'+subStrings(studentDorm.ROOM_NAMES,24)+'</p>'+
										'</div>';
		    			
		    			if((i + 1) % rowCount  == 0){//5个卡片一行
		    				isEnd = true;
		    				cardListHtml += '</div></div>';
		    			}else{
		    				isEnd = false;
		    			}
		    			
		    		}
		    		if(!isEnd){
		    			cardListHtml += '</div>';
		    		}
		    		
		    		//计算分页
		    		var pageNumber = data.pageNumber;
		    		if(pageNumber == 1){
		    			if(pageNumber * limit > data.total){
		    				$("#pageNumberStr").html("显示第 "+pageNumber+" 到第 "+data.total+" 条记录，总共 "+data.total+" 条记录");
		    			}else{
		    				$("#pageNumberStr").html("显示第 "+pageNumber+" 到第 "+pageNumber * limit+" 条记录，总共 "+data.total+" 条记录");
		    			}
		    		}else{
		    			if(pageNumber * limit > data.total){
		    				var pn = (pageNumber - 1) * limit + 1;
		    				$("#pageNumberStr").html("显示第 "+(pn < 0 ? 0 : pn)+" 到第 "+data.total+" 条记录，总共 "+data.total+" 条记录");
		    			}else{
		    				var pn = (pageNumber - 1) * limit + 1;
		    				$("#pageNumberStr").html("显示第 "+(pn < 0 ? 0 : pn)+" 到第 "+pageNumber * limit+" 条记录，总共 "+data.total+" 条记录");
		    			}
		    			
		    		}
		    		var pageNumberCount = Math.ceil(data.total/limit);
		    		var htmls = '<li class="page-pre"><a href="javascript:nextPage(\'last\');">‹</a></li>';
		    		if(pageNumberCount > 0){
		    			for(var i = 0; i< pageNumberCount; i++){
			    			htmls += '<li class="page-number '+(pageNumber == (i + 1) ? "active" : "")+'"><a href="javascript:nextPage('+(i+1)+');">'+(i+1)+'</a></li>';
			    		}
		    		}
		    		htmls += '<li class="page-next"><a href="javascript:nextPage(\'next\');">›</a></li>';
		    		$("#pagenumberli").html(htmls);
		    		
		    		$("#pageNumber").html(pageNumber);
		    		$("#pageNumberTotal").html(Math.ceil(data.total/limit));
//		    		$("#totalData").html("总共 "+data.total+" 条记录");
				  
			  }
			  
		});
		
	}else{
		
		$(".pl-fanYe").css("display","none");
		$("#cardViewPage").css("display","none");
		
		$.ajax({
			  type: 'POST',
			  url: _basepath+"studentDorm/getStudentDormPlanList.json",
			  async: false,
			  data: {
				  pkid:pkid,
				  BANXING:BANXING2,
				  WENHUAKEXUEXIAO:WENHUAKEXUEXIAO2,
				  T_STUDENT_DORM_TYPE_PKID:T_STUDENT_DORM_TYPE_PKID,
				  STATUS:STATUS,
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
							
			    			var rowCount = 4;
			    			if(dormLevel == 4){
			    				rowCount = 4;
			    			}
			    			if(data.dormLevel == '2' || data.dormLevel == '3'){//点击楼、层，显示房间
			    				rowCount = 6;
			    			}
			    			if(i == 0 || i % rowCount == 0){//5个卡片一行
			    				cardListHtml += '<div class="row">';
			    			}
			    			var NANHOVERSTR = studentDorm.NANHOVERSTR + studentDorm.NVHOVERSTR;
			    			if(data.dormLevel == '0'){//点击学校，显示校区
			    				cardListHtml += '<div class="col-lg-3 col-md-4" style="margin-bottom:15px;cursor:pointer;" data-toggle="tooltip" data-placement="bottom" title="'+NANHOVERSTR+'" onclick="javascript:clickCard(\''+studentDorm.PKID+'\');">'+
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
											                '<li class="clearfix"></li>'+
											            '</ul>'+
											            '<div class="progress">'+
								                        	'<div class="progress-bar progress-bar-danger" style="width:'+(CHUANGCOUNT_RUZHU/CHUANGCOUNT)*100+'%;"></div>'+
								                   		'</div>'+
											        '</div>'+
											    '</div>';
							}else if(data.dormLevel == '1'){//点击校区，显示楼
								cardListHtml += '<div class="col-lg-3 col-md-4" style="margin-bottom:15px;cursor:pointer;"  data-toggle="tooltip" data-placement="bottom" title="'+NANHOVERSTR+'" onclick="javascript:clickCard(\''+studentDorm.PKID+'\');">'+
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
							}else if(data.dormLevel == '2' || data.dormLevel == '3'){//点击楼||楼层  显示房间
								
								
							}else if(data.dormLevel == '4'){//点击房间，显示床
								if(studentDorm.XINGMING == '' || studentDorm.XINGMING == null || studentDorm.XINGMING == 'null'){
									cardListHtml += 
										'<div class="col-lg-3 col-md-4 pl-shanGe">'+
											'<div class="pl-wSolid '+(studentDorm.STATUS == '1'?'bg-info':'bg-hui')+'">'+
												'<h5>'+studentDorm.SD_NAME+'</h5>'+
												'<div><img src="'+_basePath +'static/gxjf/images/defaulttouxiang.jpg" style="width:99px;height:110px;"/></div>'+
												'<p data-toggle="tooltip" data-placement="bottom" style="text-align:center;">--<br/></p>'+
											'</div>'+
										'</div>';
								}else{
									cardListHtml += 
										'<div class="col-lg-3 col-md-4 pl-shanGe">'+
											'<div class="pl-wSolid '+(studentDorm.STATUS == '1'?'bg-info':'bg-hui')+'">'+
												'<h5>'+studentDorm.SD_NAME+'</h5>'+
												'<div><img src="'+ _basePath + (studentDorm.TOUXIANG == null || studentDorm.TOUXIANG == '' ? 'static/gxjf/images/defaulttouxiang.jpg' : studentDorm.TOUXIANG) +'" style="width:99px;height:110px;" onerror="this.src=\'static/gxjf/images/touxiang_error.jpg\'"/></div>'+
												'<p data-toggle="tooltip" data-placement="bottom" title="'+studentDorm.XINGMINGS+'">'+subStrings(studentDorm.XINGMINGS,25)+'</p>'+
											'</div>'+
										'</div>';
								}
								
							}
			    			
			    			if((i + 1) % rowCount  == 0){//5个卡片一行
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
	}
	
	return cardListHtml;
};



/**
 * 表格刷新
 */
var refTable = function(click_pkid,pageSize){
	
	$("#cardList").html("");
	
//	var pkid = $("#treeNodeId").val();
	var BANXING2 = $("#BANXING2  option:selected").val();
	var WENHUAKEXUEXIAO2 = $("#WENHUAKEXUEXIAO2  option:selected").val();
    var T_STUDENT_DORM_TYPE_PKID = $("#T_STUDENT_DORM_TYPE_PKID  option:selected").val();
    var STATUS = $("#STATUS  option:selected").val();
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
		    		$("#cardList").html(setCardListHtml(click_pkid,BANXING2,WENHUAKEXUEXIAO2,T_STUDENT_DORM_TYPE_PKID,STATUS,data.dormLevel,SD_SEX,'',pageSize));
				}
		    }
	});
	
};

//分页
var nextPage = function(pageindex){
	$("#cardList").html("");
	
	var click_pkid = $("#treeNodeId").val();
	var BANXING2 = $("#BANXING2  option:selected").val();
	var WENHUAKEXUEXIAO2 = $("#WENHUAKEXUEXIAO2  option:selected").val();
    var T_STUDENT_DORM_TYPE_PKID = $("#T_STUDENT_DORM_TYPE_PKID  option:selected").val();
    var STATUS = $("#STATUS  option:selected").val();
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
		    		var pageSize = $(".page-size").html();
		    		$("#cardList").html(setCardListHtml(click_pkid,BANXING2,WENHUAKEXUEXIAO2,T_STUDENT_DORM_TYPE_PKID,STATUS,data.dormLevel,SD_SEX,pageindex,pageSize));
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
			if($('.pl-item').eq(0).attr("class") == "pl-item active"){
				refTable(pkid);
			}else{
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
		    		    		refreshCardListTable(pkid,data.dormLevel);
		    				}
		    		    }
		    	});
			}
			
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
		var BANXING2 = $("#BANXING2  option:selected").val();
		var WENHUAKEXUEXIAO2 = $("#WENHUAKEXUEXIAO2  option:selected").val();
	    var T_STUDENT_DORM_TYPE_PKID = $("#T_STUDENT_DORM_TYPE_PKID  option:selected").val();
	    var STATUS = $("#STATUS  option:selected").val();
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
			    		$("#cardList").html(setCardListHtml(pkid,BANXING2,WENHUAKEXUEXIAO2,T_STUDENT_DORM_TYPE_PKID,STATUS,data.dormLevel,SD_SEX));
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
		
		var BANXING2 = $("#BANXING  option:selected").val();
		var WENHUAKEXUEXIAO2 = $("#WENHUAKEXUEXIAO  option:selected").val();
		
		if(BANXING2 == null || BANXING2 == ""){
			layer.msg("请先选择班型！");
			return false;
		}
		
		if(WENHUAKEXUEXIAO2 == null || WENHUAKEXUEXIAO2 == ""){
			layer.msg("请先选择文化课学校！");
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
		
		var BANXING2 = $("#BANXING  option:selected").val();
		datas.push({"name":"BANXING","value":BANXING2});
		
		
		var partschool=$("#WENHUAKEXUEXIAO").val();
			var partschool_str='';
			if(partschool!=null){
				for(var i=0;i<partschool.length;i++){
					partschool_str+=partschool[i]+',';
				}
			}
	
		//var WENHUAKEXUEXIAO2 = $("#WENHUAKEXUEXIAO  option:selected").val();
		/*var WENHUAKEXUEXIAO2_str='';
		if(WENHUAKEXUEXIAO2!=null){
			for(var i=0;i<WENHUAKEXUEXIAO2.length;i++){
				WENHUAKEXUEXIAO2_str+=WENHUAKEXUEXIAO2[i]+',';
			}
		}*/
		datas.push({"name":"WENHUAKEXUEXIAO","value":partschool});
		
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
			    		if($('.pl-item').eq(0).attr("class") == "pl-item active"){
			    			refTable(click_pkid);
						}else{
							refreshCardListTable(click_pkid);
						}
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
			    		if($('.pl-item').eq(0).attr("class") == "pl-item active"){
			    			refTable(click_pkid);
						}else{
							refreshCardListTable(click_pkid);
						}
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
			title:'宿舍分配',
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
			title:'宿舍回收',
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
	
	//导出
	page.btn_export = function(){
		var click_pkid = $("#treeNodeId").val();
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
//    		    		var pkid=$("#defaultTopPkid").val();
    		    		var BANXING2 = $("#BANXING2  option:selected").val();
    		    		var WENHUAKEXUEXIAO2 = $("#WENHUAKEXUEXIAO2  option:selected").val();
    		    	    var T_STUDENT_DORM_TYPE_PKID = $("#T_STUDENT_DORM_TYPE_PKID  option:selected").val();
    		    	    var STATUS = $("#STATUS  option:selected").val();
    		    	    var SD_SEX = $("#SD_SEX  option:selected").val();
    		    	    
    		    		window.location.href=encodeURI(_basepath+'studentDorm/exportStudentDormPlanTable.json?pkid='+click_pkid
    		    				+"&&BANXING="+BANXING2+"&&T_STUDENT_DORM_TYPE_PKID="+T_STUDENT_DORM_TYPE_PKID+"&&STATUS="+STATUS
    		    				+"&&WENHUAKEXUEXIAO="+WENHUAKEXUEXIAO2+"&&SD_SEX="+SD_SEX+"&&dormLevel="+data.dormLevel);
    				}
    		    }
    	});
    	
		
	};
	/**
	 * 修改页面
	 */
	page.edit = function(pkid){};
	
	//获取list表格数据
	page.getCardListTable=function () {
		
		
		var url = _basepath+"studentDorm/getStudentDormTablelistPage.json";

		$('#cardListTable').bootstrapTable(
						{
							url : url,//数据源
							dataField : "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
							totalField : 'total',
							sortOrder : 'desc',
							striped : true, //表格显示条纹  
							pagination : true, //启动分页  
							pageNumber : 1, //当前第几页  
							pageSize : 15, //每页显示的记录数  
							pageList : [1,5,10,15,20,30,50,100], //记录数可选列表  
							search : false, //是否启用查询  
							formatSearch : function() {
								return '查询';
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
							//行选
							onClickRow : function(row, tr,flied){
				            	//设置批量按钮是否可点击
					        },
					        //全选
					        onCheckAll:function(rows){
					        	//设置批量按钮是否可点击
					        	
					        },
				            //点击每一个单选框时触发的操作
				            onCheck:function(row){
				            	//设置批量按钮是否可点击
				            },
				            //取消每一个单选框时对应的操作；
				            onUncheck:function(row){
				            	//设置批量按钮是否可点击
				            },
				            //取消每一个单选框时对应的操作；
				            onUncheckAll:function(rows){
				            	//设置批量按钮是否可点击
				            },
							queryParams : function getParams(params) {
								var pkid=$("#treeNodeId").val();
								var BANXING2 = $("#BANXING2  option:selected").val();
								var WENHUAKEXUEXIAO2 = $("#WENHUAKEXUEXIAO2  option:selected").val();
							    var T_STUDENT_DORM_TYPE_PKID = $("#T_STUDENT_DORM_TYPE_PKID  option:selected").val();
							    var STATUS = $("#STATUS  option:selected").val();
							    var SD_SEX = $("#SD_SEX  option:selected").val();
								
								var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, //页面大小
									searchText : this.searchText,
									sortName : this.sortName,
									sortOrder : this.sortOrder,
									pageindex : this.pageNumber,
									pkid:pkid,
									WENHUAKEXUEXIAO:WENHUAKEXUEXIAO2,
									T_STUDENT_DORM_TYPE_PKID:T_STUDENT_DORM_TYPE_PKID,
									STATUS:STATUS,
									BANXING:BANXING2,
									SD_SEX:SD_SEX
									
								//当前页码
								};
								return temp;
							},
							buttonsAlign : "right",//按钮对齐方式
							selectItemName : 'id',
//							toolbar : "#toolbar",
							toolbarAlign : 'left',
							onLoadSuccess:function(){
					            $('.bootstrap-table tr td').each(function () {
					                $(this).attr("title", $(this).text());
					                $(this).css("cursor", 'pointer');
					            });
					        },
							columns : [{
						                checkbox: true,
						                formatter : function(value, row,
												index) {
										}
						            },
									{
										field : 'PKID',// 可不加
										align : "center",
										halign : 'center'
									},
									{
										field : 'ROOM_NAME',
										title:'宿舍',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'SD_SEX_NAME',
										title:'性别',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'DEPARTMENT_NAMES',
										title:'宿舍归属',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'DT_NAME',
										title:'宿舍类型',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field: 'CHUANGCOUNT',//可不加  
										title:'应住人数',
										align : "center",
										halign : 'center'
									},

									{
										field: 'CHUANGCOUNT_RUZHU',//可不加  
										title:'实住人数',
										align : "center",
										halign : 'center'
									},
									{
										field : 'FANGSTATUS',
										title:'房间状态',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter : function(value, row,
												index) {
											if(value == '空'){
												return '<span class="text-black">空</span>';
											}else if(value == '满'){
												return '<span class="text-red">满</span>';
											}else if(value == '未满'){
												return '<span class="text-bule">未满</span>';
											}else if(value == '预留'){
												return '<span class="text-yellow">预留</span>';
											}
										}
									}
							],
						});
	    $('#cardListTable').bootstrapTable('hideColumn', 'PKID');//隱藏列
	    
	   
	};
	
	
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
    			/*$.ajax({
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
    			});*/
    		}
    		pkid_click = selectNode[0].href;//节点pkid
    		
    		$("#treeNodeId").val(pkid);
			
			//刷新右侧卡片
    		if($('.pl-item').eq(0).attr("class") == "pl-item active"){
    			refTable(pkid_click);
			}else{
				var click_pkid = $("#treeNodeId").val();
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
		    		    		refreshCardListTable(pkid_click,data.dormLevel);
		    				}
		    		    }
		    	});
			}
			
		});
	    //end 查询
		
	    //分配按钮注册事件
	    $("#btn_allot").click(page.allot);
	    //回收按钮注册事件
	    $("#btn_recovery").click(page.recovery);
	    
	    $("#btn_export").click(page.btn_export);
	    
	    /*$("#RUXUENIANFEN").change(function(){
	    	$("#btn_search").click();
	    });
	    $("#T_STUDENT_DORM_TYPE_PKID").change(function(){
	    	$("#btn_search").click();
	    });
	    $("#STATUS").change(function(){
	    	$("#btn_search").click();
	    });
	    $("#SD_SEX").change(function(){
	    	$("#btn_search").click();
	    });
*/
	    
	    
	    $("#cardTab").click(function(){
	    	var click_pkid = $("#treeNodeId").val();
	    	refTable(click_pkid);
	    });
	    
	    $("#cardTabTable").click(function(){
	    	var click_pkid = $("#treeNodeId").val();
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
	    		    		refreshCardListTable(click_pkid,data.dormLevel);
	    				}
	    		    }
	    	});
	    	
	    });
	    
	    $('#dropdown-menu22').find('li').each(function() {
            $(this).click(function() {
            	var pageSize = $(this).find("a").html();
            	$(".page-size").html(pageSize);
            	var click_pkid = $("#treeNodeId").val();
    	    	refTable(click_pkid,pageSize);
            });
        });
	    
		loadTree();//加载左侧树
		page.loadTable();//加载表格
		page.getCardListTable();//加载表格数据
		
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
