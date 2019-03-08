/**
 * 考勤表js
 */
(function($){
	
	/**
	 * 页面初始化
	 */
	$(function(){
		
		/*
		 * 日期控件
		 */		
		$('.date-picker').datepicker({
			autoclose: true,
			todayHighlight: false,
			format: 'yyyy-mm',
			startView: 'year',
// 	        maxViewMode:'months',
	        minViewMode:'months',
	        endYear:'+1m'
		});
		
		$('#TJDATE').datepicker('setEndDate', 'null');
		
		
		
		
		/*
		 * 选择队伍  更新班组
		 */
		$('#DUIWU').change(function(){
			var XM_DUIWU_PKID = $(this).val();
			$.ajax({
				type:"post",
				dataType:"json",
				data:{"XM_DUIWU_PKID":XM_DUIWU_PKID},
				url:_basepath+"workerTeam/banzuListByTeamID.json",
				success:function(data){
					var banzu_list = data.banzu_list;
					$('#BANZU').empty();
					$('<option value="" >全部</option>').appendTo('#BANZU');
					for(var i=0;i<banzu_list.length;i++){
						$('<option value="'+banzu_list[i].BANZU_PKID+'">'+banzu_list[i].MINGCHENG+'</option>').appendTo('#BANZU');
					}
				},
				error:function(a,b,exception){
					alert(exception);
				}
			});
		});
		
		
		/*
		 * 检索
		 */
		$("#nav-search-icon").click(function(){
			$("#tools").find("button").attr("disabled",false);
			var XM_DUIWU_PKID = $("#DUIWU").val();
			if(XM_DUIWU_PKID==null || XM_DUIWU_PKID ==''){
				BootstrapDialog.show({  
			 	 	title: '提示信息',
		            message: '请选择队伍',
		            closable: false, 
		              buttons: [{
					    label: '确定',
					    cssClass: 'btn-success keman_btn',
					    action: function(dialogRef){
		                     dialogRef.close();
		                }
				  	}
				  	]
		        });
				return false;
			}else{
				var BANZU_PKID = $("#BANZU").val();
				var TJDATE = $("#TJDATE").val();
				var ZIRANYUE = $("#ZIRANYUE").prop("checked");
				/*
				 * 传递服务端的参数
				 * XM_DUIWU_PKID=项目队伍PKID
				 * BANZU_PKID=班组PKID
				 * TJDATE=统计时间
				 * ZIRANYUE=是否自然月
				 */
//				$("#simple-table").load(_basepath+"bbcx/HMClist.json?XM_DUIWU_PKID="+XM_DUIWU_PKID+"&&BANZU_PKID="+BANZU_PKID+"&&TJDATE="+TJDATE+"&&ZIRANYUE="+ZIRANYUE);
				$("#simple-table").load(_basepath+"bbcx/kqtjlist.json?XM_DUIWU_PKID="+XM_DUIWU_PKID+"&BANZU_PKID="+BANZU_PKID+"&TJDATE="+TJDATE+"&ZIRANYUE="+ZIRANYUE);
			}
		});
		
		
		//打印预览注册点击事件
		$(".x-emb-preview").click(function(){
			
			var disabled = $(this).attr("disabled");
			if(disabled == false){
				return;
			}
			
			var XM_DUIWU_PKID = $("#DUIWU").val();
			if(XM_DUIWU_PKID==null || XM_DUIWU_PKID ==''){
				BootstrapDialog.show({  
			 	 	title: '提示信息',
		            message: '请选择队伍',
		            closable: false, 
		              buttons: [{
					    label: '确定',
					    cssClass: 'btn-success keman_btn',
					    action: function(dialogRef){
		                     dialogRef.close();
		                }
				  	}
				  	]
		        });
				return false;
			}else{
				console.log("kaoqintongji-2");
				var BANZU_PKID = $("#BANZU").val();
				var TJDATE = $("#TJDATE").val();
				var ZIRANYUE = $("#ZIRANYUE").prop("checked");
				var url = _basepath+"bbcx/kqtjprint.json?XM_DUIWU_PKID="+XM_DUIWU_PKID+"&BANZU_PKID="+BANZU_PKID+"&TJDATE="+TJDATE+"&ZIRANYUE="+ZIRANYUE+"&random="+Math.random();
				
				window.open(url, "打印考勤表", "", false);
				
			}
		});
		//end 打印预览注册点击事件
		
		
		$(".x-emb-export").click(function(){
			var disabled = $(this).attr("disabled");
			if(disabled == false){
				return;
			}
			
			var XM_DUIWU_PKID = $("#DUIWU").val();
			if(XM_DUIWU_PKID==null || XM_DUIWU_PKID ==''){
				BootstrapDialog.show({  
			 	 	title: '提示信息',
		            message: '请选择队伍',
		            closable: false, 
		              buttons: [{
					    label: '确定',
					    cssClass: 'btn-success keman_btn',
					    action: function(dialogRef){
		                     dialogRef.close();
		                }
				  	}
				  	]
		        });
				return false;
			}else{
				
				var BANZU_PKID = $("#BANZU").val();
				var TJDATE = $("#TJDATE").val();
				var ZIRANYUE = $("#ZIRANYUE").prop("checked");
				var url = _basepath+"bbcx/kqtjexcel.json?XM_DUIWU_PKID="+XM_DUIWU_PKID+"&BANZU_PKID="+BANZU_PKID+"&TJDATE="+TJDATE+"&ZIRANYUE="+ZIRANYUE;
				
				window.location.href=url;
				
				//window.open(url, ""+Math.random(), null, false);
			}
		});
		
	});//end 初始化
	
	
	
	
	
	
	
	
	
	
})(jQuery);

