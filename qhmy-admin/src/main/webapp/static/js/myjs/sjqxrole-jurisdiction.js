//@ sourceURL=sjqxrole-jurisdiction.js 
/**
 * 角色管理
 * @param win
 * @param $
 */
(function(win,$,layer){
	
	var page = {};
	
	
	
	 
	page.quanxuan = function(){
		$('#sjqxTreeview').treeview('checkAll', { silent: $('#chk-check-silent').is(':checked') });
    };
	
    page.quxiaoqx = function(){
    	$('#sjqxTreeview').treeview('uncheckAll', { silent: $('#chk-check-silent').is(':checked') });
    };
	
    
    
    
    
	/**
	 * 选中父节点
	 */
	page.getParent = function(data){
		$('#sjqxTreeview').treeview('checkNode', [ data.nodeId, { silent: true } ]);
		/*var parentNode = $('#sjqxTreeview').treeview('getParent',data.nodeId);
		if(parentNode.nodeId!=null){
			$('#sjqxTreeview').treeview('checkNode', [ parentNode.nodeId, { silent: true } ]);
			page.getParent(parentNode);
		}*/
    };
    
    /**
     * 获得所有要选中的子节点
     */
    page.walkNode = function(nodes,nodesIdsArray){
       if (nodes.length > 0){
	       	for (var i = 0; i < nodes.length; i++) {
	       		nodesIdsArray.push(nodes[i].nodeId);
				if(nodes[i].nodes!=null){
					page.walkNode(nodes[i].nodes,nodesIdsArray);
				}
			}
       }
    };
    
    /**
     * 获得所有要取消的节点
     */
    page.unWalkNode = function(nodes,nodesIdsArray){
       if (nodes.length > 0){
	       	for (var i = 0; i < nodes.length; i++) {
	       		nodesIdsArray.push(nodes[i].nodeId);
				if(nodes[i].nodes!=null){
					page.unWalkNode(nodes[i].nodes,nodesIdsArray);
				}
			}
       }
    };
	
	
	/**
	 * 加载tree
	 */
	page.loadTree = function(){
		var treeData = $("#sjqxTreeData").val();
		
		$('#sjqxTreeview').treeview({
			data : treeData,
			showCheckbox : true,
			levels : 2,
			async: false,
			highlightSearchResults:true,
		    onNodeChecked: function(event, data){
		    	//var arrays = $('#treeview_addqx').treeview('getSiblings', data);  //得到兄弟节点数组
				//alert(data.nodes);//得到所有子节点
				//page.getParent(data);
		    	page.getParent(data);//选中父节点
    			if(data.nodes != null){
    				var arrayInfo = data.nodes;
    				var nodesIdsArray = new Array();
    				//page.walkNode(arrayInfo,nodesIdsArray);
    				
    				if(nodesIdsArray!=null && nodesIdsArray.length>0){
    					$('#sjqxTreeview').treeview('checkNode', [ nodesIdsArray, { silent: true } ]);
    				}
    			}
		    	// 选中父节点，则自动选择子节点
//	        	if(data.nodes != null){
//	        		var arrayInfo = data.nodes;
//	        		for (var i = 0; i < arrayInfo.length; i++) {
//	        			$('#sjqxTreeview').treeview('toggleNodeChecked', [ arrayInfo[i].nodeId, { silent: true } ]);
//					}
//	        	}
		    },
			onNodeUnchecked: function(event, data){
	        	// 取消选中父节点，则自动取消选择子节点
				if(data.nodes != null){
        			var nodesIdsArray = new Array();
        			var arrayInfo = data.nodes;
        		//	page.unWalkNode(arrayInfo,nodesIdsArray);
        			$('#sjqxTreeview').treeview('uncheckNode', [ nodesIdsArray , { silent: true } ]);
        		}
				
//	        	if(data.nodes != null){
//	        		var arrayInfo = data.nodes;
//	        		for (var i = 0; i < arrayInfo.length; i++) {
//	        			$('#sjqxTreeview').treeview('toggleNodeChecked', [ arrayInfo[i].nodeId, { silent: true } ]);
//					}
//	        	}
			},
			onNodeSelected: function(event, data){
				//节点选中时触发
//				var id=data.href;
//				var url = _basepath+"authorityManage/department-table.json";
//	            var opt = {
//    			    url: url,
//    			    silent: true,
//    			    query:{
//    			    	"PKID":id
//    			    }
//    		    };
//	            $("#departmentTable").bootstrapTable('refresh', opt);
			}
		 });
	};
	
	
	/**
	 * 初始化
	 */
	page.init = function(){
		
		$.ajaxSetup({
		    aysnc: false // 默认同步加载
		});
		
		page.loadTree();
		
		
		//全选
	    $("#quanxuan").click(page.quanxuan);
	  //取消
	    $("#quxiaoqx").click(page.quxiaoqx);
	};
	
	page.init();
	
})(window,jQuery,layer);


