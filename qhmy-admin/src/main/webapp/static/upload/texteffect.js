(function($) {
	"use strict";
		var isOn = 0, sets, fx, toAnimate = "#effect", settings = {
			animation:8,
			animationType: "in",
			backwards: false,
			easing: "easeOutQuint",
			speed: 2000,
			sequenceDelay: 100,
			startDelay: 0,
			offsetX: 100,
			offsetY: 50,
			onComplete: fireThis,
			restoreHTML: true
		};

		var size = 0;
		var content = new Array();
		
		jQuery(document).ready(function() {
			fx = jQuery("#effect");
			content = ["1","2","3","4","5","6"];
			
			size = content.length;
			
			fx.html(content[0]);
			jQuery.cjTextFx(settings);
			jQuery.cjTextFx.animate(toAnimate);
		});
	    
		function fireThis() {
			if(isOn>=size-1){
				//到头了,初始化为-1
				isOn = -1;
			}
			isOn++;
			
			console.log(isOn);
			
			fx.html("BBBBB:"+content[isOn]);
			jQuery.cjTextFx(settings);
			jQuery.cjTextFx.animate(toAnimate, settings);
		}

})(jQuery);