/**
 * 侧边栏按钮事件
 * @param $
 */

(function($){
	
	$.extend($.fn,{
		//扩展Jquery对象
		leftRegion: function (option){
			return new LeftRegion(option,this);
		}
	});
	
	var LeftRegion = function(op,element){
		this.option = {};
		this.btn = null;
		this.init = function(){
			var myobj = this;
			this.option = $.extend({},this.option,op);
			
			this.btn = $(element);
			
			if(this.option.def == "left"){
				var $i = this.btn.find("i");
				$("."+this.option.parentClass).offset({left: 0 });
				$i.removeClass("glyphicon-chevron-right");
				$i.addClass("glyphicon-chevron-left");
			}else{
				$("."+this.option.parentClass).offset({left: -208 });
				var $i = this.btn.find("i");
				$i.removeClass("glyphicon-chevron-left");
				$i.addClass("glyphicon-chevron-right");
			}
			
			
			var methons = {};
			
			/*
			 * 点击侧边栏按钮
			 */
			methons.clickSplitBtn = function(){
				var $i = methons.btn.find("i");
				var rst = $i.hasClass("glyphicon-chevron-left");
				
				var rightd=myobj.option.rightDiv
				
				if(rst){
					//是左侧按钮,向左移动，隐藏左边栏
					myobj.left();
					$(rightd).css('margin-left','10px');
				}else{
					//向右边栏
					myobj.right();
					$(rightd).css('margin-left','220px');
				}
			};
			
			methons.btn = this.btn;
			this.btn.bind("click",function(){
				methons.clickSplitBtn();
			});
			
		};
		
		this.left = function(){
			$("."+this.option.parentClass).offset({left: -208 });
			var $i = this.btn.find("i");
			$i.removeClass("glyphicon-chevron-left");
			$i.addClass("glyphicon-chevron-right");
		};
		
		this.right = function(){
			$("."+this.option.parentClass).offset({left: 0 });
			var $i = this.btn.find("i");
			$i.removeClass("glyphicon-chevron-right");
			$i.addClass("glyphicon-chevron-left");
		};
		
		this.display = function(){
			alert("dfgdfgdfg");
		};
		this.init();
	};
})(jQuery);