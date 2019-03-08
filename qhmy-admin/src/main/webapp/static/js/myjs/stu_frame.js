
/**
 * authorityManage/index.php
 * 权限栏目首页
 * @param win
 * @param $
 */
(function(win,$){
	
	var page = {};
	
	/**
	 * 刷新右侧内容区域
	 */
	page.refright = function(liObj){
		
		var url = $(liObj).find("a").attr("menu_url");
		var r = Math.random();
		url = _basepath + url +"?r="+r;
		$('.jf_szright').html("");
		layer.load(1, {
			  shade: [0.1,'#fff'] //0.1透明度的白色背景
		});
		try {
			$('.jf_szright').load(url,function(){
				layer.closeAll('loading');
			});
		} catch (e) {
			 layer.closeAll('loading');
		}
		
	};
	
	
	/**
	 * 初始化
	 */
	page.init = function(){
		
		$.ajaxSetup({
		    aysnc: false // 默认同步加载
		});
		
		/*
		 * 左侧二级菜单点击方法
		 */
		$('.jf_tabli').click(function(){
			$(this).addClass('active');
			$('.jf_tabli').not($(this)).removeClass('active');
			
			idx=$(this).index('.jf_tabli');
			$('.jf_tabrow').eq(idx).stop().show(100);
			$('.jf_tabrow').not($('.jf_tabrow').eq(idx)).hide(100);
			event.preventDefault();
			page.refright(this);
		});
		
		$('.jf_tabli').find("a").click(function(event){
			event.preventDefault();
		});
		//end 
		
		//默认加载第一个左侧菜单
		page.refright($('.jf_tabli:first'));
		
		
	};
	
	
	page.init();
	
})(window,jQuery);

