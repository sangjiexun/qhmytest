//@ sourceURL=payexamineMessage.js 
/**
 * 学生信息页面对象
 */
(function($, window) {
	
	

	
	var jfshenhe = {};
	//批量通过方法  batchNo
	jfshenhe.batchGreeOne=function(e){
		$.post(_basepath+"pay/batchGreeOne.json?PKID="+e,function(data){
			if(data.result=="success"){
				layer.msg("修改成功!");
				$('.jf_szright').load(_basepath+'pay/jfshenhe.php');
				 //$("#jfshenheinfotable").bootstrapTable('refresh', {url: _basepath+"pay/getjfshtable.json"});
			}
		});
	};
	
	
	//批量不通过方法  
	jfshenhe.batchNoOne=function(e){
		$.post(_basepath+"pay/batchNoOne.json?PKID="+e,function(data){
			if(data.result=="success"){
				layer.msg("修改成功!");
				$('.jf_szright').load(_basepath+'pay/jfshenhe.php');
				 //$("#jfshenheinfotable").bootstrapTable('refresh', {url: _basepath+"pay/getjfshtable.json"});
			}
		});
	};
	
	
	
	
	//返回
	$('#fanhui').click(function(){
		var xgym=$('#xgym').val();
		
		
		if(xgym == "Y"){
			$('.jf_szright').load(_basepath+'pay/paymanage.php');
		}else{
			$('.jf_szright').load(_basepath+'pay/jfshenhe.php');
		}
		
	});

	
	//通过按钮的批量
	$('#Mtongguo').click(function(){		
		var PKID=$('#PKID').val();
		
		jfshenhe.batchGreeOne(PKID);
	});
	
	
	//不通过按钮的批量
	$('#Mbtongguo').click(function(){
		//var pkids=jfshenhe.getIdSelections();
		var PKID=$('#PKID').val();
		
		jfshenhe.batchNoOne(PKID);
	});
	
	//展开描述点击事件
	$(".expandchild").click(function(){
		var text = $(this).attr("text");
		if(text=='zhankai'){//表示要展开了
			$(this).attr("text","shouqi");
			$(this).html("<span class='fa fa-sort-down jf_icon'></span> 收起描述");
			$(this).parent().parent().parent().find(".child_area").show();
		}else{
			$(this).attr("text","zhankai");
			$(this).html("<span class='fa fa-sort-down jf_icon'></span> 展开描述");
			$(this).parent().parent().parent().find(".child_area").hide();
		}
	});

})(jQuery, window);
