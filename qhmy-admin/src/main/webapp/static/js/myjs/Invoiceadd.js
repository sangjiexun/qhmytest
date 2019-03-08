//@ sourceURL=Invoiceadd.js
/**
 * 组织机构 新增
 * @param win
 * @param $
 */
(function(win,$,layer){
	
	var page = {};
	
	//显示下拉树
	page.showTree = function(){
		$('#parendDepartmentTreeview').show();
		
        var treeData = $("#departmentTreeJsonDataDiv").val();
		$('#parendDepartmentTreeview').treeview({
			bootstrap2 : false, 
			showTags : true,  
			data : treeData,
			showCheckbox : false,
			checkedIcon : "glyphicon glyphicon-check", 
			levels : 5,
			highlightSearchResults:true,
		    onNodeChecked: function(event, data){
//		    	// 选中父节点，则自动选择子节点
//	        	if(data.nodes != null){
//	        		var arrayInfo = data.nodes;
//	        		for (var i = 0; i < arrayInfo.length; i++) {
//	        			$('#departmentTreeview').treeview('toggleNodeChecked', [ arrayInfo[i].nodeId, { silent: true } ]);
//					}
//	        	}
		    },
			onNodeUnchecked: function(event, data){
//	        	// 取消选中父节点，则自动取消选择子节点
//	        	if(data.nodes != null){
//	        		var arrayInfo = data.nodes;
//	        		for (var i = 0; i < arrayInfo.length; i++) {
//	        			$('#departmentTreeview').treeview('toggleNodeChecked', [ arrayInfo[i].nodeId, { silent: true } ]);
//					}
//	        	}
			},
			onNodeSelected: function(event, data){
				$("#txt_departmentname").val(data.text);
				$("#parent_pkid").val(data.href);
                $("#parendDepartmentTreeview").hide(); 
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
		
		//下拉树点击事件
		$("#txt_departmentname").click(function() {
			page.showTree();
	    });
	};
	
	page.init();
	
})(window,jQuery,layer);

$(function(){
	
	
	$('input[name=invoiceradio]').on('click',function(){
		var checkval=$("input[name='invoiceradio']:checked").val();
		if(checkval=="1"){//单个添加
			$("#hengxian").css('display','none'); 
			$("#fapiaohaoinputz").css('display','none'); 
		}
		if(checkval=="2"){//批量添加
			$("#hengxian").css('display','block'); 
			$("#fapiaohaoinputz").css('display','block'); 
		}
		
	});
	
	
	
/*
	for(var i=0;i<stu_type.length;i++){		
		$("input[name='checkbox']").each(function() {  	
       	if($(this)[0].value==stu_type[i].type){
       		$(this)[0].checked=true;
       	}
		});  	
	}*/

});
