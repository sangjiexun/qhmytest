//@ sourceURL=allotStudentDorm.js
/**
 * 分配宿舍
 * @param win
 * @param $
 */
(function(win,$,layer){
	
	var page = {};
	
	$('#WENHUAKEXUEXIAO').multiselect({
		 enableFiltering: true,
		 includeSelectAllOption: true,
		 selectAllText: ' 全选',
		 nonSelectedText: '请选择文化课学校',
		 allSelectedText: '全部选择',
		 selectAllJustVisible: true,
		 filterPlaceholder:'请输入文化课学校',
		 buttonWidth: '183px',//设置字体过长显示...
		 onDropdownHide:function(){
			 $('.modal-content').css("height","372px");
			 $('.modal-body').css("height","252px");
		 }
	 });
	
	$('.multiselect').click(function(){
		$('.modal-content').css("height","548px");
		$('.modal-body').css("height","430px");
	});
	
	
	/**
	 * 初始化
	 */
	page.init = function(){
		$.ajaxSetup({
		    aysnc: false // 默认同步加载
		});
		
	};
	
	page.init();
	
	
	 $("#SFHZXX").change(function(){
		 $('#WENHUAKEXUEXIAO').multiselect('clearSelection');
		 /*$('#WENHUAKEXUEXIAO').multiselect('clearSelection');
		 $('#WENHUAKEXUEXIAO').multiselect('refresh');
		 $("#WENHUAKEXUEXIAO").find("option").remove(); */
		 /*$("#WENHUAKEXUEXIAO").html('<option value="">请选择</option>');*/
		 var SFHZXX=$("#SFHZXX").val(); 
			var r = Math.random();
	    	var url = _basepath+"studentDorm/getSchool.json?r="+r;
	    	$.ajax({
	    		 	type: 'post',
	    		    url: url,
	    		    async:false,
	    		    data:{SFHZXX:SFHZXX},
	    		    dataType:"json",
	    		    success: function(data) {
	    		    	if(data.result==true){
	    		    		 $('#WENHUAKEXUEXIAO').empty();
	    		    		var wenhuakexuexiaoList=data.wenhuakexuexiaoList;
	    		    		for(var i = 0;i <wenhuakexuexiaoList.length; i++){
	    		    		    var $select=$("#WENHUAKEXUEXIAO");
	    		    		    $select.append('<option value="'+wenhuakexuexiaoList[i].PKID+'">'+wenhuakexuexiaoList[i].SCHOOLNAME+'</option>');
	    		    		}
	    		    		
	    		    		$('#WENHUAKEXUEXIAO').multiselect('rebuild')
	    		    		
	    				}
	    		    }
	    	});
		 
		 
	   });
	
	
})(window,jQuery,layer);


