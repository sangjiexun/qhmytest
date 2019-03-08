
$(function(){
	
	$('#querydiv').click(function(event){
        event.stopPropagation();
    });
	
	$("ul[class='submenu']").find("a").bind("click",function(e){
		e.stopPropagation();
		yincang_cebianlan();//隐藏侧边栏
		$("#nameli").load($(this).attr("menuurl"));
	});
	
	
	$("#_navlist_ul li").bind("click",function(index,i){
		if($(this).find("ul > li").size()<=0){
			var $a = $(this).find("a:first");
			if($(this).prev().size() == 0){
				//点击的是首页
				window.location.href = $a.attr("href");
			}else{
				$("#nameli").load($a.attr("href"),function(){
					var $Loginpage = $("#isLoginFlag");
					if($Loginpage!=null && $Loginpage.size()>0 && $Loginpage.val()=='true'){
						//是登录页面
						window.top.location.href = $a.attr("href");
					}
				});
				//非首页，隐藏侧边栏
				yincang_cebianlan();//隐藏侧边栏
			}
		}
	});
	index_cebianlan();
});



function yincang_cebianlan(){
	$("#_index_left-region").hide();
	//获取边距
	$("#right-region").css("margin-left","10px");//设置为0
}

function xianshi_cebianlan(){
	$("#_index_left-region").show();
}


/**
 * 侧边栏
 */
function index_cebianlan(){
	var op = {};
	op.parentClass = "left-region";
	op.def = "right";
	op.rightDiv = "#right-region";
	var a = $(".split-btn").leftRegion(op);
	//end 侧边栏
}
//end 侧边栏
