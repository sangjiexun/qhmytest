 $(document).ready(function() {
	 
	 
	 
	 
	 $(".lbr-warn-group-attent-days").change(function(){
		 var bzcql=$('.lbr-warn-group-attent-days').val();
  	   if(!isNaN(bzcql) ){
		   var r =  /^(\+|-)?\d+$/;
		   if(!r.test(bzcql) || bzcql<1 ||  bzcql>100){
	            	 BootstrapDialog.show({  
					 	 	title: '提示信息',
				            message: '请输入大于0,小于101的正整数！',
				            closable: true, 
				            draggable: true,
				              buttons: [{
							    label: '关闭',
							    cssClass: 'btn-success keman_btn',
							    action: function(dialogRef){
				                     dialogRef.close();
				                }
						  }
						  ]
				          });
	            	$('.lbr-warn-group-attent-days').val("");
	            	//$(".lbr-warn-group-attent-days").focus();
	            	 return false;
	             }
		 }else{
		   BootstrapDialog.show({
  	            title: '提示信息',
  	            closable: true, 
  	          draggable: true,
  	            message: '输入格式错误,请输入数字!',
  	            buttons: [{
  	                label: '关闭',
  	               cssClass: 'btn-primary keman_btn',
  	                action: function(dialog) {
  	                   dialog.close();
  	                }
  	           		 }]
  	       		 });
		   //$(".lbr-warn-group-attent-days").focus();
		   $('.lbr-warn-group-attent-days').val("");
		   return false;
		 }
  	   
  	 $.ajax({
			type:"post",
			dataType:"json",
			url:"rule/setbzcql.json?CHUQIN_BANZU="+bzcql,
		success : function(data) {
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
  	   
	 });
	 
	 $(".lbr-warn-team-attent-days").change(function(){
		 var dwcql=$('.lbr-warn-team-attent-days').val();
  	   if(!isNaN(dwcql) ){
		   var r =  /^(\+|-)?\d+$/;
		   if(!r.test(dwcql) || dwcql<1 ||  dwcql>100){
	            	 BootstrapDialog.show({  
					 	 	title: '提示信息',
				            message: '请输入大于0,小于101的正整数！',
				            closable: true, 
				            draggable: true,
				              buttons: [{
							    label: '关闭',
							    cssClass: 'btn-success keman_btn',
							    action: function(dialogRef){
				                     dialogRef.close();
				                }
						  }
						  ]
				          });
	            	$('.lbr-warn-team-attent-days').val("");
	            	//$(".lbr-warn-group-attent-days").focus();
	            	 return false;
	             }
		 }else{
		   BootstrapDialog.show({
  	            title: '提示信息',
  	            closable: true, 
  	          draggable: true,
  	            message: '输入格式错误,请输入数字!',
  	            buttons: [{
  	                label: '关闭',
  	               cssClass: 'btn-primary keman_btn',
  	                action: function(dialog) {
  	                   dialog.close();
  	                }
  	           		 }]
  	       		 });
		   //$(".lbr-warn-group-attent-days").focus();
		   $('.lbr-warn-team-attent-days').val("");
		   return false;
		 }
  	   
  	 $.ajax({
			type:"post",
			dataType:"json",
			url:"rule/setbzcql.json?CHUQIN_DUIWU="+dwcql,
		success : function(data) {
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
  	   
	 });
	 
	 $(".lbr-warn-wlk-type-attent-days").change(function(){
		 var gzcql=$('.lbr-warn-wlk-type-attent-days').val();
  	   if(!isNaN(gzcql) ){
		   var r =  /^(\+|-)?\d+$/;
		   if(!r.test(gzcql) || gzcql<1 ||  gzcql>100){
	            	 BootstrapDialog.show({  
					 	 	title: '提示信息',
				            message: '请输入大于0,小于101的正整数！',
				            closable: true, 
				            draggable: true,
				              buttons: [{
							    label: '关闭',
							    cssClass: 'btn-success keman_btn',
							    action: function(dialogRef){
				                     dialogRef.close();
				                }
						  }
						  ]
				          });
	            	$('.lbr-warn-wlk-type-attent-days').val("");
	            	//$(".lbr-warn-group-attent-days").focus();
	            	 return false;
	             }
		 }else{
		   BootstrapDialog.show({
  	            title: '提示信息',
  	            closable: true, 
  	          draggable: true,
  	            message: '输入格式错误,请输入数字!',
  	            buttons: [{
  	                label: '关闭',
  	               cssClass: 'btn-primary keman_btn',
  	                action: function(dialog) {
  	                   dialog.close();
  	                }
  	           		 }]
  	       		 });
		   //$(".lbr-warn-group-attent-days").focus();
		   $('.lbr-warn-wlk-type-attent-days').val("");
		   return false;
		 }
  	   
  	 $.ajax({
			type:"post",
			dataType:"json",
			url:"rule/setbzcql.json?CHUQIN_GONGZHONG="+gzcql,
		success : function(data) {
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
  	   
	 });
	 $(".lbr-warm-attent-days-count").change(function(){
		 var lxts=$('.lbr-warm-attent-days-count').val();
  	   if(!isNaN(lxts) ){
		   var r =  /^(\+|-)?\d+$/;
		   if(!r.test(lxts) || lxts<1 ||  lxts>10){
	            	 BootstrapDialog.show({  
					 	 	title: '提示信息',
				            message: '请输入大于0,小于10的正整数！',
				            closable: true, 
				            draggable: true,
				              buttons: [{
							    label: '关闭',
							    cssClass: 'btn-success keman_btn',
							    action: function(dialogRef){
				                     dialogRef.close();
				                }
						  }
						  ]
				          });
	            	$('.lbr-warm-attent-days-count').val("");
	            	//$(".lbr-warn-group-attent-days").focus();
	            	 return false;
	             }
		 }else{
		   BootstrapDialog.show({
  	            title: '提示信息',
  	            closable: true, 
  	          draggable: true,
  	            message: '输入格式错误,请输入数字!',
  	            buttons: [{
  	                label: '关闭',
  	               cssClass: 'btn-primary keman_btn',
  	                action: function(dialog) {
  	                   dialog.close();
  	                }
  	           		 }]
  	       		 });
		   //$(".lbr-warn-group-attent-days").focus();
		   $('.lbr-warm-attent-days-count').val("");
		   return false;
		 }
  	   
  	 $.ajax({
			type:"post",
			dataType:"json",
			url:"rule/setbzcql.json?GONGRENZCSJ_TIAN="+lxts,
		success : function(data) {
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
  	   
	 });
	 
	 $(".lbr-warm-attent-days").change(function(){
		 var lxxs=$('.lbr-warm-attent-days').val();
  	   if(!isNaN(lxxs) ){
		   var r =  /^(\+|-)?\d+$/;
		   if(!r.test(lxxs) || lxxs<9 ||  lxxs>24){
	            	 BootstrapDialog.show({  
					 	 	title: '提示信息',
				            message: '请输入大于9,小于24的正整数！',
				            closable: true, 
				            draggable: true,
				              buttons: [{
							    label: '关闭',
							    cssClass: 'btn-success keman_btn',
							    action: function(dialogRef){
				                     dialogRef.close();
				                }
						  }
						  ]
				          });
	            	$('.lbr-warm-attent-days').val("");
	            	//$(".lbr-warn-group-attent-days").focus();
	            	 return false;
	             }
		 }else{
		   BootstrapDialog.show({
  	            title: '提示信息',
  	            closable: true, 
  	          draggable: true,
  	            message: '输入格式错误,请输入数字!',
  	            buttons: [{
  	                label: '关闭',
  	               cssClass: 'btn-primary keman_btn',
  	                action: function(dialog) {
  	                   dialog.close();
  	                }
  	           		 }]
  	       		 });
		   //$(".lbr-warn-group-attent-days").focus();
		   $('.lbr-warm-attent-days').val("");
		   return false;
		 }
  	   
  	 $.ajax({
			type:"post",
			dataType:"json",
			url:"rule/setbzcql.json?GONGRENZCSJ_XIAOSHI="+lxxs,
		success : function(data) {
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
  	   
	 });
	 
	 
	 
	 $(".lbr-warm-night").change(function(){
		 var yjwcc=$('.lbr-warm-night').val();
		 var regTime = /^([0-2][0-9]):([0-5][0-9])$/;
		    var result = false;
		    if (regTime.test(yjwcc)) {
		        if ((parseInt(RegExp.$1) < 24) && (parseInt(RegExp.$2) < 60)) {
		            result = true;
		        }
		    }
		    if (result) {
		    	 $.ajax({
		 			type:"post",
		 			dataType:"json",
		 			url:"rule/setyjwcc.json?GONGREN_YJWCC="+yjwcc,
		 		success : function(data) {
		 		},
		 		error : function(XMLHttpRequest, textStatus, errorThrown) {
		 			alert(errorThrown);
		 		}
		 	});
		    	
		    	
		    	
		    	
		    }else {
		    	 BootstrapDialog.show({
		  	            title: '提示信息',
		  	            closable: true, 
		  	          draggable: true,
		  	            message: '输入错误,时间范围是00:00到24:00!',
		  	            buttons: [{
		  	                label: '关闭',
		  	               cssClass: 'btn-primary keman_btn',
		  	                action: function(dialog) {
		  	                   dialog.close();
		  	                }
		  	           		 }]
		  	       		 });
		    	
		    }
		 return result;
		 
		 
		 
		 
		 
		 
/*  	   if(!isNaN(yjwcc) ){
		   var r =  /^(\+|-)?\d+$/;
		   if(!r.test(lxxs) || lxxs<9 ||  lxxs>24){
	            	 BootstrapDialog.show({  
					 	 	title: '提示信息',
				            message: '请输入大于9,小于24的正整数！',
				            closable: true, 
				            draggable: true,
				              buttons: [{
							    label: '关闭',
							    cssClass: 'btn-success keman_btn',
							    action: function(dialogRef){
				                     dialogRef.close();
				                }
						  }
						  ]
				          });
	            	$('.lbr-warm-attent-days').val("");
	            	//$(".lbr-warn-group-attent-days").focus();
	            	 return false;
	             }
		 }else{
		   BootstrapDialog.show({
  	            title: '提示信息',
  	            closable: true, 
  	          draggable: true,
  	            message: '输入格式错误,请输入数字!',
  	            buttons: [{
  	                label: '关闭',
  	               cssClass: 'btn-primary keman_btn',
  	                action: function(dialog) {
  	                   dialog.close();
  	                }
  	           		 }]
  	       		 });
		   //$(".lbr-warn-group-attent-days").focus();
		   $('.lbr-warm-attent-days').val("");
		   return false;
		 }
  	   
  	 $.ajax({
			type:"post",
			dataType:"json",
			url:"rule/setbzcql.json?GONGRENZCSJ_XIAOSHI="+lxxs,
		success : function(data) {
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});*/
  	   
	 });
	 
	 
	 
	 
	 
	 $(".lbr-warn-safy-tra-days").change(function(){
		 var pxy=$('.lbr-warn-safy-tra-days').val();
  	   if(!isNaN(pxy) ){
		   var r =  /^(\+|-)?\d+$/;
		   if(!r.test(pxy) || pxy<1 ||  pxy>6){
	            	 BootstrapDialog.show({  
					 	 	title: '提示信息',
				            message: '请输入 1 到 6 之间的月份！',
				            closable: true, 
				            draggable: true,
				              buttons: [{
							    label: '关闭',
							    cssClass: 'btn-success keman_btn',
							    action: function(dialogRef){
				                     dialogRef.close();
				                }
						  }
						  ]
				          });
	            	$('.lbr-warn-safy-tra-days').val("");
	            	//$(".lbr-warn-group-attent-days").focus();
	            	 return false;
	             }
		 }else{
		   BootstrapDialog.show({
  	            title: '提示信息',
  	            closable: true, 
  	          draggable: true,
  	            message: '输入格式错误,请输入数字!',
  	            buttons: [{
  	                label: '关闭',
  	               cssClass: 'btn-primary keman_btn',
  	                action: function(dialog) {
  	                   dialog.close();
  	                }
  	           		 }]
  	       		 });
		   //$(".lbr-warn-group-attent-days").focus();
		   $('.lbr-warn-safy-tra-days').val("");
		   return false;
		 }
  	   
  	 $.ajax({
			type:"post",
			dataType:"json",
			url:"rule/setbzcql.json?ANQUANJY_YUE="+pxy,
		success : function(data) {
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
  	   
	 });
	 
	 $(".warn-cert-days").change(function(){
		 var zsdq=$('.warn-cert-days').val();
  	   if(!isNaN(zsdq) ){
		   var r =  /^(\+|-)?\d+$/;
		   if(!r.test(zsdq) || zsdq<1 ||  zsdq>180){
	            	 BootstrapDialog.show({  
					 	 	title: '提示信息',
				            message: '请输入 1 到 180 之间的天数!',
				            closable: true, 
				            draggable: true,
				              buttons: [{
							    label: '关闭',
							    cssClass: 'btn-success keman_btn',
							    action: function(dialogRef){
				                     dialogRef.close();
				                }
						  }
						  ]
				          });
	            	$('.warn-cert-days').val("");
	            	//$(".lbr-warn-group-attent-days").focus();
	            	 return false;
	             }
		 }else{
		   BootstrapDialog.show({
  	            title: '提示信息',
  	            closable: true, 
  	          draggable: true,
  	            message: '输入格式错误,请输入数字!',
  	            buttons: [{
  	                label: '关闭',
  	               cssClass: 'btn-primary keman_btn',
  	                action: function(dialog) {
  	                   dialog.close();
  	                }
  	           		 }]
  	       		 });
		   //$(".lbr-warn-group-attent-days").focus();
		   $('.warn-cert-days').val("");
		   return false;
		 }
  	   
  	 $.ajax({
			type:"post",
			dataType:"json",
			url:"rule/setbzcql.json?ZHENGSHU_TIAN="+zsdq,
		success : function(data) {
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
  	   
	 });
	 
	 
	 $(".btn-warn-group-attent-open").bind("click", function(){
			$.ajax({
				type:"post",
				dataType:"json",
				url:"rule/setyjbtn.json?CHUQIN_BANZU_BTN="+1,
				 success:function(data){
					 $(".btn-warn-group-attent-open").css('display', 'none');//隐藏
					 $(".btn-warn-group-attent-closed").css('display', 'block');//显示
					 $(".lbr-warn-group-attent-days").attr("disabled",true);
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		});
	 $(".btn-warn-group-attent-closed").bind("click", function(){
			$.ajax({
				type:"post",
				dataType:"json",
				url:"rule/setyjbtn.json?CHUQIN_BANZU_BTN="+0,
				 success:function(data){
					 $(".btn-warn-group-attent-open").css('display', 'block');
					 $(".btn-warn-group-attent-closed").css('display', 'none');
					 $(".lbr-warn-group-attent-days").attr("disabled",false);
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		});
	
	 $(".btn-warn-team-attent-open").bind("click", function(){
			$.ajax({
				type:"post",
				dataType:"json",
				url:"rule/setyjbtn.json?CHUQIN_DUIWU_BTN="+1,
				 success:function(data){
					 $(".btn-warn-team-attent-open").css('display', 'none');
					 $(".btn-warn-team-attent-closed").css('display', 'block');
					 $(".lbr-warn-team-attent-days").attr("disabled",true);
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		});
	 $(".btn-warn-team-attent-closed").bind("click", function(){
			$.ajax({
				type:"post",
				dataType:"json",
				url:"rule/setyjbtn.json?CHUQIN_DUIWU_BTN="+0,
				 success:function(data){
					 $(".btn-warn-team-attent-open").css('display', 'block');
					 $(".btn-warn-team-attent-closed").css('display', 'none');
					 $(".lbr-warn-team-attent-days").attr("disabled",false);
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		});
	 
	 
	 $(".btn-warn-wlk-type-attent-open").bind("click", function(){
			$.ajax({
				type:"post",
				dataType:"json",
				url:"rule/setyjbtn.json?CHUQIN_GONGZHONG_BTN="+1,
				 success:function(data){
					 $(".btn-warn-wlk-type-attent-open").css('display', 'none');
					 $(".btn-warn-wlk-type-attent-closed").css('display', 'block');
					 $(".lbr-warn-wlk-type-attent-days").attr("disabled",true);
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		});
	 $(".btn-warn-wlk-type-attent-closed").bind("click", function(){
			$.ajax({
				type:"post",
				dataType:"json",
				url:"rule/setyjbtn.json?CHUQIN_GONGZHONG_BTN="+0,
				 success:function(data){
					 $(".btn-warn-wlk-type-attent-open").css('display', 'block');
					 $(".btn-warn-wlk-type-attent-closed").css('display', 'none');
					 $(".lbr-warn-wlk-type-attent-days").attr("disabled",false);
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		});
	 
	 
	 $(".btn-warn-attent-open").bind("click", function(){
			$.ajax({
				type:"post",
				dataType:"json",
				url:"rule/setyjbtn.json?GONGRENZCSJ_BTN="+1,
				 success:function(data){
					 $(".btn-warn-attent-open").css('display', 'none');
					 $(".btn-warn-attent-closed").css('display', 'block');
					 $(".lbr-warm-attent-days-count").attr("disabled",true);
					 $(".lbr-warm-attent-days").attr("disabled",true);
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		});
	 $(".btn-warn-attent-closed").bind("click", function(){
			$.ajax({
				type:"post",
				dataType:"json",
				url:"rule/setyjbtn.json?GONGRENZCSJ_BTN="+0,
				 success:function(data){
					 $(".btn-warn-attent-open").css('display', 'block');
					 $(".btn-warn-attent-closed").css('display', 'none');
					 $(".lbr-warm-attent-days-count").attr("disabled",false);
					 $(".lbr-warm-attent-days").attr("disabled",false);
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		});
	 
	 $(".btn-warn-unattent-open").bind("click", function(){
			$.ajax({
				type:"post",
				dataType:"json",
				url:"rule/setyjbtn.json?GONGREN_YJWCC_BTN="+1,
				 success:function(data){
					 $(".btn-warn-unattent-open").css('display', 'none');
					 $(".btn-warn-unattent-closed").css('display', 'block');
					 $(".lbr-warm-night").attr("disabled",true);
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		});
	 $(".btn-warn-unattent-closed").bind("click", function(){
			$.ajax({
				type:"post",
				dataType:"json",
				url:"rule/setyjbtn.json?GONGREN_YJWCC_BTN="+0,
				 success:function(data){
					 $(".btn-warn-unattent-open").css('display', 'block');
					 $(".btn-warn-unattent-closed").css('display', 'none');
					 $(".lbr-warm-night").attr("disabled",false);
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		});
	 
	 
	 $(".btn-warn-safy-open").bind("click", function(){
			$.ajax({
				type:"post",
				dataType:"json",
				url:"rule/setyjbtn.json?ANQUANJY_BTN="+1,
				 success:function(data){
					 $(".btn-warn-safy-open").css('display', 'none');
					 $(".btn-warn-safy-closed").css('display', 'block');
					 $(".lbr-warn-safy-tra-days").attr("disabled",true);
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		});
	 $(".btn-warn-safy-closed").bind("click", function(){
			$.ajax({
				type:"post",
				dataType:"json",
				url:"rule/setyjbtn.json?ANQUANJY_BTN="+0,
				 success:function(data){
					 $(".btn-warn-safy-open").css('display', 'block');
					 $(".btn-warn-safy-closed").css('display', 'none');
					 $(".lbr-warn-safy-tra-days").attr("disabled",false);
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		});
	 
	 $(".btn-warn-cert-open").bind("click", function(){
			$.ajax({
				type:"post",
				dataType:"json",
				url:"rule/setyjbtn.json?ZHENGSHU_BTN="+1,
				 success:function(data){
					 $(".btn-warn-cert-open").css('display', 'none');
					 $(".btn-warn-cert-closed").css('display', 'block');
					 $(".warn-cert-days").attr("disabled",true);
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		});
	 $(".btn-warn-cert-closed").bind("click", function(){
			$.ajax({
				type:"post",
				dataType:"json",
				url:"rule/setyjbtn.json?ZHENGSHU_BTN="+0,
				 success:function(data){
					 $(".btn-warn-cert-open").css('display', 'block');
					 $(".btn-warn-cert-closed").css('display', 'none');
					 $(".warn-cert-days").attr("disabled",false);
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		});
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 function getdepid() {
			var $table = $('#minzutable');
 	return $.map($table.bootstrapTable('getSelections'), function(row) {
     return row.PKID;
 });
}
	 
	 
		$("#wuall").bind("click", function(){
				var ROLEID=getdepid();
				if(ROLEID=="" || ROLEID==null){
					BootstrapDialog.show({
			 			title: '提示信息',
					message: '请选择民族！',
					closable: true, 
					draggable: true,
					buttons: [{
						label: '关闭',
						cssClass: 'btn keman_btn',
						action: function(dialogItself){
									dialogItself.close();
								}
					}]
				});
					return false;
				}
				$.ajax({
					type:"post",
					dataType:"json",
					url:"rule/mzwu.json?PKID="+ROLEID,
					 success:function(data){
						 BootstrapDialog.show({
					 			title: '提示信息',
							message: '修改成功！',
							closable: true, 
							draggable: true,
							buttons: [{
								label: '关闭',
								
								cssClass: 'keman_btn',
								action: function(dialogItself){
											dialogItself.close();
											 $("#minzutable").bootstrapTable('refresh', {url:"rule/mztablejson.json"});
										}
							}]
						});
						 
					  },
					error: function (XMLHttpRequest, textStatus, errorThrown) { 
					           alert(errorThrown); 
					       }
					});
			});
		
		
		
		$("#djtxall").bind("click", function(){
			var ROLEID=getdepid();
			if(ROLEID=="" || ROLEID==null){
				BootstrapDialog.show({
		 			title: '提示信息',
				message: '请选择民族！',
				closable: true, 
				draggable: true,
				buttons: [{
					label: '关闭',
					cssClass: 'btn keman_btn',
					action: function(dialogItself){
								dialogItself.close();
							}
				}]
			});
				return false;
			}
			$.ajax({
				type:"post",
				dataType:"json",
				url:"rule/mzdjtx.json?PKID="+ROLEID,
				 success:function(data){
					 BootstrapDialog.show({
				 			title: '提示信息',
						message: '修改成功！',
						closable: true, 
						draggable: true,
						buttons: [{
							label: '关闭',
							cssClass: 'keman_btn',
							action: function(dialogItself){
										dialogItself.close();
										 $("#minzutable").bootstrapTable('refresh', {url:"rule/mztablejson.json"});
									}
						}]
					});
					 
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		});
		
		
		
		
		$("#jzdjall").bind("click", function(){
			var ROLEID=getdepid();
			if(ROLEID=="" || ROLEID==null){
				BootstrapDialog.show({
		 			title: '提示信息',
				message: '请选择民族！',
				closable: true, 
				draggable: true,
				buttons: [{
					label: '关闭',
					cssClass: 'btn keman_btn',
					action: function(dialogItself){
								dialogItself.close();
							}
				}]
			});
				return false;
			}
			$.ajax({
				type:"post",
				dataType:"json",
				url:"rule/mzjzdj.json?PKID="+ROLEID,
				 success:function(data){
					 BootstrapDialog.show({
				 			title: '提示信息',
						message: '修改成功！',
						closable: true, 
						draggable: true,
						buttons: [{
							label: '关闭',
							cssClass: 'keman_btn',
							action: function(dialogItself){
										dialogItself.close();
										 $("#minzutable").bootstrapTable('refresh', {url:"rule/mztablejson.json"});
									}
						}]
					});
					 
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		});
		
		
		
		
		
		
	
	 
	 
	 //考勤通行规则设置              lbr-wlk-fail-rule-agree
	 
	 $(".lbr-wlk-handImport").change(function() {
		 if(document.getElementById("drkq").checked){
			 $.ajax({
	  			type:"post",
	  			dataType:"json",
	  			url:"rule/setdrkq.json?SHOUDONGDRKQSJ="+"Y",
	  		success : function(data) {
	  		},
	  		error : function(XMLHttpRequest, textStatus, errorThrown) {
	  			alert(errorThrown);
	  		}
	  	});
		 }else{
			 $.ajax({
		  			type:"post",
		  			dataType:"json",
		  			url:"rule/setdrkq.json?SHOUDONGDRKQSJ="+"N",
		  		success : function(data) {
		  		},
		  		error : function(XMLHttpRequest, textStatus, errorThrown) {
		  			alert(errorThrown);
		  		}
		  	});
		 }
	 });
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 $(".lbr-wlk-qrCode").change(function() {
		 if(document.getElementById("ewm").checked){
			 $.ajax({
	  			type:"post",
	  			dataType:"json",
	  			url:"rule/setewm.json?ERWEIMATONGX="+"Y",
	  		success : function(data) {
	  		},
	  		error : function(XMLHttpRequest, textStatus, errorThrown) {
	  			alert(errorThrown);
	  		}
	  	});
		 }else{
			 $.ajax({
		  			type:"post",
		  			dataType:"json",
		  			url:"rule/setewm.json?ERWEIMATONGX="+"N",
		  		success : function(data) {
		  		},
		  		error : function(XMLHttpRequest, textStatus, errorThrown) {
		  			alert(errorThrown);
		  		}
		  	});
		 }
	 });
	 
	 
	 
	 
	 
	 
	 $(".lbr-wlk-fail-rules").change(function() {
		 $.ajax({
  			type:"post",
  			dataType:"json",
  			url:"rule/setrysx.json?CHANGSJBSKBSX="+"Y",
  		success : function(data) {
  			
  		},
  		error : function(XMLHttpRequest, textStatus, errorThrown) {
  			alert(errorThrown);
  		}
  	});
	 });
	 
	    $(".lbr-wlk-fail-rule-agree").change(function() {
		       if(document.getElementById("checksk").checked){
		    	   var tianshu=$('#skzdsx').val();
		    	   if(!isNaN(tianshu) ){
		    		   var r =  /^(\+|-)?\d+$/;
		    		   if(!r.test(tianshu) || tianshu<1  ){
		 	            	 BootstrapDialog.show({  
		 					 	 	title: '提示信息',
		 				            message: '请输入1及以上的正整数！',
		 				            closable: true, 
		 				           draggable: true,
		 				              buttons: [{
		 							    label: '关闭',
		 							    cssClass: 'btn-success keman_btn',
		 							    action: function(dialogRef){
		 				                     dialogRef.close();
		 				                }
		 						  }
		 						  ]
		 				          });
		 	            	 $("#checksk").prop({checked:false});
		 	            	$('#skzdsx').val("");
		 	            	 return false;
		 	             }
		    		   
		    		  
		    		 }else{
		    		   BootstrapDialog.show({
		      	            title: '提示信息',
		      	            closable: true, 
		      	          draggable: true,
		      	            message: '输入格式错误,请输入数字!',
		      	            buttons: [{
		      	                label: '关闭',
		      	               cssClass: 'btn-primary',
		      	                action: function(dialog) {
		      	                   dialog.close();
		      	                }
		      	           		 }]
		      	       		 });
		    		   $('#skzdsx').val("");
		    		   $("#checksk").prop({checked:false});
		    		   return false;
		    		 }
		    	  
		    	   
		    	   $.ajax({
		      			type:"post",
		      			dataType:"json",
		      			url:"rule/setrysx.json?SXTIANSHU="+tianshu+"&&CHANGSJBSKBSX="+"N",
		      		success : function(data) {
		      		},
		      		error : function(XMLHttpRequest, textStatus, errorThrown) {
		      			alert(errorThrown);
		      		}
		      	});
		    	}
		     
		    });
	    $("#skzdsx").blur(function(){
	 	   var tianshu=$('#skzdsx').val();
    	   if(!isNaN(tianshu) ){
    		   var r =  /^(\+|-)?\d+$/;
    		   if(!r.test(tianshu) || tianshu<1  ){
 	            	 BootstrapDialog.show({  
 					 	 	title: '提示信息',
 				            message: '请输入1及以上的正整数！',
 				            closable: true, 
 				           draggable: true,
 				              buttons: [{
 							    label: '关闭',
 							    cssClass: 'btn-success keman_btn',
 							    action: function(dialogRef){
 				                     dialogRef.close();
 				                }
 						  }
 						  ]
 				          });
 	            	$('#skzdsx').val("");
 	            	 return false;
 	             }
    		   
    		  
    		 }else{
    		   BootstrapDialog.show({
      	            title: '提示信息',
      	            closable: true, 
      	          draggable: true,
      	            message: '输入格式错误,请输入数字!',
      	            buttons: [{
      	                label: '关闭',
      	               cssClass: 'btn-primary',
      	                action: function(dialog) {
      	                   dialog.close();
      	                }
      	           		 }]
      	       		 });
    		   $('#skzdsx').val("");
    		   return false;
    		 }
    	  
    	   
    	   $.ajax({
      			type:"post",
      			dataType:"json",
      			url:"rule/setryts.json?SXTIANSHU="+tianshu,
      		success : function(data) {
      		},
      		error : function(XMLHttpRequest, textStatus, errorThrown) {
      			alert(errorThrown);
      		}
      	});
	    });
	    
	
	    	
	    	
	    	
	    
	    
	    
	    
	    
	 
	 
	 
	 
	 $(".lbr-wlk-safe-edu-rule-agree").change(function() {
		 $.ajax({
  			type:"post",
  			dataType:"json",
  			url:"rule/setjypx.json?WEIJIAOYUJC="+"Y",
  		success : function(data) {
  			
  		},
  		error : function(XMLHttpRequest, textStatus, errorThrown) {
  			alert(errorThrown);
  		}
  	});
	 });
	 
	 
	 
	    $(".lbr-wlk-safe-edu-rule-disagree").change(function() {
	       if(document.getElementById("checkjz").checked){
	    	   var tianshu=$('#tianshu').val();
	    	   if(!isNaN(tianshu) ){
	    		   var r =  /^(\+|-)?\d+$/;
	    		   if(!r.test(tianshu) || tianshu<1  ){
	 	            	 BootstrapDialog.show({  
	 					 	 	title: '提示信息',
	 				            message: '请输入1及以上的正整数！',
	 				            closable: true, 
	 				           draggable: true,
	 				              buttons: [{
	 							    label: '关闭',
	 							    cssClass: 'btn-success keman_btn',
	 							    action: function(dialogRef){
	 				                     dialogRef.close();
	 				                }
	 						  }
	 						  ]
	 				          });
	 	            	 $("#checkjz").prop({checked:false});
	 	            	$('#tianshu').val("");
	 	            	 return false;
	 	             }
	    		   
	    		  
	    		 }else{
	    		   BootstrapDialog.show({
	      	            title: '提示信息',
	      	            closable: true, 
	      	          draggable: true,
	      	            message: '输入格式错误,请输入数字!',
	      	            buttons: [{
	      	                label: '关闭',
	      	               cssClass: 'btn-primary keman_btn',
	      	                action: function(dialog) {
	      	                   dialog.close();
	      	                }
	      	           		 }]
	      	       		 });
	    		   $('#tianshu').val("");
	    		   $("#checkjz").prop({checked:false});
	    		   return false;
	    		 }
	    	  
	    	   
	    	   $.ajax({
	      			type:"post",
	      			dataType:"json",
	      			url:"rule/setjypx.json?TIANSHU="+tianshu+"&&WEIJIAOYUJC="+"N",
	      		success : function(data) {
	      		},
	      		error : function(XMLHttpRequest, textStatus, errorThrown) {
	      			alert(errorThrown);
	      		}
	      	});
	    	}
	     
	    });
	 
	 
	    $("#tianshu").blur(function(){
	    	   var tianshu=$('#tianshu').val();
	    	   if(!isNaN(tianshu) ){
	    		   var r =  /^(\+|-)?\d+$/;
	    		   if(!r.test(tianshu) || tianshu<1  ){
	 	            	 BootstrapDialog.show({  
	 					 	 	title: '提示信息',
	 				            message: '请输入1及以上的正整数！',
	 				            closable: true, 
	 				           draggable: true,
	 				              buttons: [{
	 							    label: '关闭',
	 							    cssClass: 'btn-success keman_btn',
	 							    action: function(dialogRef){
	 				                     dialogRef.close();
	 				                }
	 						  }
	 						  ]
	 				          });
	 	            	$('#tianshu').val("");
	 	            	 return false;
	 	             }
	    		   
	    		  
	    		 }else{
	    		   BootstrapDialog.show({
	      	            title: '提示信息',
	      	            closable: true, 
	      	          draggable: true,
	      	            message: '输入格式错误,请输入数字!',
	      	            buttons: [{
	      	                label: '关闭',
	      	               cssClass: 'btn-primary keman_btn',
	      	                action: function(dialog) {
	      	                   dialog.close();
	      	                }
	      	           		 }]
	      	       		 });
	    		   $('#tianshu').val("");
	    		   return false;
	    		 }
	    	  
	    	   
	    	   $.ajax({
	      			type:"post",
	      			dataType:"json",
	      			url:"rule/setjyts.json?TIANSHU="+tianshu,
	      		success : function(data) {
	      		},
	      		error : function(XMLHttpRequest, textStatus, errorThrown) {
	      			alert(errorThrown);
	      		}
	      	});
	    	});
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 //惩罚开启  关闭按钮 
	 $("#cfkq").bind("click", function(){
		 
		 
		 $.ajax({
				type:"post",
				dataType:"json",
				url:"rule/chengfaqy.json",
			success : function(data) {
				if ("success" == data.result) {
					$("#cfkqdiv").hide();
					$("#cfgbdiv").show();
					
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown);
			}
		});
		 
	 });
 $("#cfgb").bind("click", function(){
		 
		 $.ajax({
				type:"post",
				dataType:"json",
				url:"rule/chengfagb.json",
			success : function(data) {
				if ("success" == data.result) {
					$("#cfkqdiv").show();
					 $("#cfgbdiv").hide();
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown);
			}
		});
		 
	 });
 
 //年龄开启  关闭按钮 
 $("#nlkq").bind("click", function(){
	 $.ajax({
			type:"post",
			dataType:"json",
			url:"rule/nianlingqy.json",
		success : function(data) {
			if ("success" == data.result) {
				$("#nlkqdiv").hide();
				$("#nlgbdiv").show();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
	 
 });
$("#nlgb").bind("click", function(){
	 $.ajax({
			type:"post",
			dataType:"json",
			url:"rule/nianlinggb.json",
		success : function(data) {
			if ("success" == data.result) {
				$("#nlkqdiv").show();
				 $("#nlgbdiv").hide();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
 });


//证书开启  关闭按钮 
$("#zsqy").bind("click", function(){
	 $.ajax({
			type:"post",
			dataType:"json",
			url:"rule/zhengshuqy.json",
		success : function(data) {
			if ("success" == data.result) {
				$("#zskqdiv").hide();
				$("#zsgbdiv").show();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
	 
});
$("#zsgb").bind("click", function(){
	 $.ajax({
			type:"post",
			dataType:"json",
			url:"rule/zhengshugb.json",
		success : function(data) {
			if ("success" == data.result) {
				$("#zskqdiv").show();
				 $("#zsgbdiv").hide();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
});
	 
    //不良记录规则：
    // 允许登记
     $(".lbr-wlk-pun-register-agree").change(function() {
        var $selectedvalue = $("input[name='punregister']:checked").val();
       if($selectedvalue=='on'){
    	   $.ajax({
   			type:"post",
   			dataType:"json",
   			url:"rule/yxdj.json",
   		success : function(data) {
   			
   		},
   		error : function(XMLHttpRequest, textStatus, errorThrown) {
   			alert(errorThrown);
   		}
   	});
       }
    });
     //禁止登记
    $(".lbr-wlk-register-pun-disagree").change(function() {
        var $selectedvalue = $("input[name='punregister']:checked").val();
        var level=$('#level').val();
        if($selectedvalue=='on'){
        	$.ajax({
       			type:"post",
       			dataType:"json",
       			url:"rule/byxdj.json?level="+level,
       		success : function(data) {
       			
       		},
       		error : function(XMLHttpRequest, textStatus, errorThrown) {
       			alert(errorThrown);
       		}
       	});
       }
    });
    //选择级别
     $("#level").change(function() {
        var level=$('#level').val();
       
        $.ajax({
   			type:"post",
   			dataType:"json",
   			url:"rule/cdjb.json?level="+level,
   		success : function(data) {
   			
   		},
   		error : function(XMLHttpRequest, textStatus, errorThrown) {
   			alert(errorThrown);
   		}
   	});
    });
   //选择是否设置童工
    $(".lbr-wlk-child-age-register").change(function() {
       if(document.getElementById("tgbox").checked){
    	   var agetg=$('#agetg').val();
    	   if(!isNaN(agetg)){
    		   if(agetg*1.0<16 || agetg*1.0>65){
    	    		  
        		   BootstrapDialog.show({
       	            title: '提示信息',
       	            closable: true, 
       	         draggable: true,
       	            message: '请先修改年龄,16—65之间!',
       	            buttons: [{
       	                label: '关闭',
       	               cssClass: 'btn keman_btn',
       	                action: function(dialog) {
       	                   dialog.close();
       	                }
       	           		 }]
       	       		 });
        		   $("#tgbox").prop({checked:false});
        		   return false;
        	   }
    		 }else{
    		   BootstrapDialog.show({
      	            title: '提示信息',
      	            closable: true, 
      	          draggable: true,
      	            message: '输入格式错误,请输入数字!',
      	            buttons: [{
      	                label: '关闭',
      	               cssClass: 'btn-primary ',
      	                action: function(dialog) {
      	                   dialog.close();
      	                }
      	           		 }]
      	       		 });
    		   $("#tgbox").prop({checked:false});
    		   return false;
    		 }
    	  
    	   
    	   $.ajax({
      			type:"post",
      			dataType:"json",
      			url:"rule/agetg.json?agetg="+agetg,
      		success : function(data) {
      		},
      		error : function(XMLHttpRequest, textStatus, errorThrown) {
      			alert(errorThrown);
      		}
      	});
    	}
       else{
    	   $.ajax({
     			type:"post",
     			dataType:"json",
     			url:"rule/tyagetg.json",
     		success : function(data) {
     			
     		},
     		error : function(XMLHttpRequest, textStatus, errorThrown) {
     			alert(errorThrown);
     		}
     	});
       }
    });
   //设置男性登记限制
   $(".lbr-wlk-male-age-register").change(function() {
	   
       if(document.getElementById("nanxbox").checked){
    	   var agenx=$('#agenx').val();
    	   if(!isNaN(agenx)){
    		   if(agenx*1.0<18 || agenx*1.0>80){
        		   BootstrapDialog.show({
          	            title: '提示信息',
          	            closable: true, 
          	          draggable: true,
          	            message: '请先修改年龄,18—80之间!',
          	            buttons: [{
          	                label: '关闭',
          	               cssClass: 'btn keman_btn',
          	                action: function(dialog) {
          	                   dialog.close();
          	                }
          	           		 }]
          	       		 });
        		   $("#nanxbox").prop({checked:false});
        		   return false;
        	   }
    	   }else{
    		   BootstrapDialog.show({
     	            title: '提示信息',
     	            closable: true, 
     	           draggable: true,
     	            message: '输入格式错误,请输入数字!',
     	            buttons: [{
     	                label: '关闭',
     	               cssClass: 'btn-primary keman_btn',
     	                action: function(dialog) {
     	                   dialog.close();
     	                }
     	           		 }]
     	       		 });
    		   $("#nanxbox").prop({checked:false});
    		   return false;
    	   }
    	   
    	   
    	   $.ajax({
      			type:"post",
      			dataType:"json",
      			url:"rule/agenx.json?agenx="+agenx,
      		success : function(data) {
      			
      		},
      		error : function(XMLHttpRequest, textStatus, errorThrown) {
      			alert(errorThrown);
      		}
      	});
    	}
       else{
    	   $.ajax({
     			type:"post",
     			dataType:"json",
     			url:"rule/tyagenx.json",
     		success : function(data) {
     			
     		},
     		error : function(XMLHttpRequest, textStatus, errorThrown) {
     			alert(errorThrown);
     		}
     	});
       }
    });
    //设置女性登记限制
   $(".lbr-wlk-fmale-age-register").change(function() {
	   if(document.getElementById("nvxbox").checked){
    	   var agenv=$('#agenv').val();
    	   
    	   
    	   
    	   if(!isNaN(agenv)){
    		   if(agenv*1.0<18 || agenv*1.0>80){
        		   BootstrapDialog.show({
        	            title: '提示信息',
        	            closable: true, 
        	            draggable: true,
        	            message: '请先修改年龄,18—80之间!',
        	            buttons: [{
        	                label: '关闭',
        	               cssClass: 'btn keman_btn',
        	                action: function(dialog) {
        	                   dialog.close();
        	                }
        	           		 }]
        	       		 });
        		   $("#nvxbox").prop({checked:false});
        		   return false;
        	   }
    	   }else{
    		   BootstrapDialog.show({
     	            title: '提示信息',
     	            closable: true, 
     	           draggable: true,
     	            message: '输入格式错误,请输入数字!',
     	            buttons: [{
     	                label: '关闭',
     	               cssClass: 'btn-primary keman_btn',
     	                action: function(dialog) {
     	                   dialog.close();
     	                }
     	           		 }]
     	       		 });
    		   $("#nvxbox").prop({checked:false});
    		   return false;
    	   }
    	   
    	   
    	   $.ajax({
      			type:"post",
      			dataType:"json",
      			url:"rule/agenv.json?agenv="+agenv,
      		success : function(data) {
      			
      		},
      		error : function(XMLHttpRequest, textStatus, errorThrown) {
      			alert(errorThrown);
      		}
      	});
    	}
       else{
    	   $.ajax({
     			type:"post",
     			dataType:"json",
     			url:"rule/tyagenv.json",
     		success : function(data) {
     			
     		},
     		error : function(XMLHttpRequest, textStatus, errorThrown) {
     			alert(errorThrown);
     		}
     	});
       }
    });
    //设置证书规则限制
   $(".lbr-wlk-up-certif").change(function() {
	     if(document.getElementById("zsbox").checked){
	    	   $.ajax({
	      			type:"post",
	      			dataType:"json",
	      			url:"rule/zsqy.json",
	      		success : function(data) {
	      			
	      		},
	      		error : function(XMLHttpRequest, textStatus, errorThrown) {
	      			alert(errorThrown);
	      		}
	      	});
	    	}
	       else{
	    	   $.ajax({
	     			type:"post",
	     			dataType:"json",
	     			url:"rule/zsty.json",
	     		success : function(data) {
	     			
	     		},
	     		error : function(XMLHttpRequest, textStatus, errorThrown) {
	     			alert(errorThrown);
	     		}
	     	});
	       }
    });
    //设置黑名单共享限制
   $(".cbx-start-blacklist").change(function() {
	   if(document.getElementById("hmdbox").checked){
    	   $.ajax({
      			type:"post",
      			dataType:"json",
      			url:"rule/hmdqy.json",
      		success : function(data) {
      			
      		},
      		error : function(XMLHttpRequest, textStatus, errorThrown) {
      			alert(errorThrown);
      		}
      	});
    	}
       else{
    	   $.ajax({
     			type:"post",
     			dataType:"json",
     			url:"rule/hmdty.json",
     		success : function(data) {
     			
     		},
     		error : function(XMLHttpRequest, textStatus, errorThrown) {
     			alert(errorThrown);
     		}
     	});
       }
    });
     //设置工人近照限制
   $(".lbr-wlk-recent-photo").change(function() {
	   if(document.getElementById("grjzbox").checked){
    	   $.ajax({
      			type:"post",
      			dataType:"json",
      			url:"rule/grjzqy.json",
      		success : function(data) {
      			
      		},
      		error : function(XMLHttpRequest, textStatus, errorThrown) {
      			alert(errorThrown);
      		}
      	});
    	}
       else{
    	   $.ajax({
     			type:"post",
     			dataType:"json",
     			url:"rule/grjzty.json",
     		success : function(data) {
     			
     		},
     		error : function(XMLHttpRequest, textStatus, errorThrown) {
     			alert(errorThrown);
     		}
     	});
       }
    });
   // 设置童工年龄
   $('#agetg').blur(function(){
	   var agetg=$('#agetg').val();
	   
	   

	   if(!isNaN(agetg)){
			   if(agetg*1.0<16 || agetg*1.0>65){
				   BootstrapDialog.show({
			            title: '提示信息',
			            closable: true, 
			            draggable: true,
			            message: '请先修改年龄,16—65之间!',
			            buttons: [{
			                label: '关闭',
			               cssClass: 'btn keman_btn',
			                action: function(dialog) {
			                   dialog.close();
			                }
			           		 }]
			       		 });
				   
				   $("#tgbox").prop({checked:false});
				   return false;
			   }
	   }
		   else{
		   BootstrapDialog.show({
 	            title: '提示信息',
 	            closable: true, 
 	           draggable: true,
 	            message: '输入格式错误,请输入数字!',
 	            buttons: [{
 	                label: '关闭',
 	               cssClass: 'btn-primary keman_btn',
 	                action: function(dialog) {
 	                   dialog.close();
 	                }
 	           		 }]
 	       		 });
		   $("#tgbox").prop({checked:false});
		   return false;
	   }
	   
	   
	   
	  
	   
	   $.ajax({
  			type:"post",
  			dataType:"json",
  			url:"rule/updatetgnl.json?agetg="+agetg,
  		success : function(data) {
  			
  		},
  		error : function(XMLHttpRequest, textStatus, errorThrown) {
  			alert(errorThrown);
  		}
  	});
     
     
    });
   // 设置男性年龄
    $('#agenx').blur(function() {
    	var agenx=$('#agenx').val();
    	
    	
    	
    	 if(!isNaN(agenx)){
    			
    	 	   if(agenx*1.0<18 || agenx*1.0>80){
    	 		  BootstrapDialog.show({
    		            title: '提示信息',
    		            closable: true,
    		            draggable: true,
    		            message: '请先修改年龄,18—80之间!',
    		            buttons: [{
    		                label: '关闭',
    		               cssClass: 'btn keman_btn',
    		                action: function(dialog) {
    		                   dialog.close();
    		                }
    		           		 }]
    		       		 });
    	 		   $("#nanxbox").prop({checked:false});
    	 		   return false;
    	 	   }
  	   }else{
  		   BootstrapDialog.show({
   	            title: '提示信息',
   	            closable: true, 
   	         draggable: true,
   	            message: '输入格式错误,请输入数字!',
   	            buttons: [{
   	                label: '关闭',
   	               cssClass: 'btn-primary keman_btn',
   	                action: function(dialog) {
   	                   dialog.close();
   	                }
   	           		 }]
   	       		 });
  		  $("#nanxbox").prop({checked:false});
		   return false;
  	   }
  	   
    	
    	
    	
    	
    	
    	
    
 	   
 	   $.ajax({
   			type:"post",
   			dataType:"json",
   			url:"rule/updatenxnl.json?agenx="+agenx,
   		success : function(data) {
   			
   		},
   		error : function(XMLHttpRequest, textStatus, errorThrown) {
   			alert(errorThrown);
   		}
   	});
    });
   //设置女性年龄
   $('#agenv').blur(function(){
	   var agenv=$('#agenv').val();
	   
	   
	   if(!isNaN(agenv)){
			
		   if(agenv*1.0<18 || agenv*1.0>80){
			   BootstrapDialog.show({
		            title: '提示信息',
		            closable: true, 
		            draggable: true,
		            message: '请先修改年龄,18—80之间!',
		            buttons: [{
		                label: '关闭',
		               cssClass: 'btn keman_btn',
		                action: function(dialog) {
		                   dialog.close();
		                }
		           		 }]
		       		 });
			   $("#nvxbox").prop({checked:false});
			   return false;
		   }
	   }else{
		   BootstrapDialog.show({
	            title: '提示信息',
	            closable: true, 
	            draggable: true,
	            message: '输入格式错误,请输入数字!',
	            buttons: [{
	                label: '关闭',
	               cssClass: 'btn-primary keman_btn',
	                action: function(dialog) {
	                   dialog.close();
	                }
	           		 }]
	       		 });
		   $("#nvxbox").prop({checked:false});
		   return false;
	   }

	   
	   $.ajax({
  			type:"post",
  			dataType:"json",
  			url:"rule/updatenvnl.json?agenv="+agenv,
  		success : function(data) {
  			
  		},
  		error : function(XMLHttpRequest, textStatus, errorThrown) {
  			alert(errorThrown);
  		}
  	});
    });

});