//@ sourceURL=goodbadedit.js 
$(document).ready(function() {
	$("input").placeholder(); 

	
	 $('.lbr-black-btn-back').bind("click",function(){
	        $("#nameli").load("goodbad/goodbad.php");
	        $("#erjie").empty();
	        $("#erjie").hide();
	     });
	
	
	 $('#RIQI').bind('click',function() {
		laydate({
   		  	isclear: false ,//是否显示清空,
   		 max: laydate.now()
	   	});
	 });
	 
	 $("#searchgrxx").keydown( function(event){
		 var content=$(this).val();
		  //alert(content);
		  var time=new Date().getTime();
		  
		  $.ajax({
				type:"post",
				dataType:"json",
				 data:{p:content,time:time},
				url:"goodbad/zdtc.json",
			success : function(data) {
				autocompleteFn(data.jsonarray);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown);
			}
		});
	 });
	 
	 
	 function autocompleteFn(names){
		var names1= jQuery.parseJSON( names );
		
         $("#searchgrxx").autocomplete(names1,{  
           minChars:1,  
           selectFirst: false,
           matchContains: true,
           max: 10,  
           autoFocus:false,
           dataType:"json",  
           scrollHeight: 220,  
           formatItem: function(data, i, total) {
             return data.XINGMING+"-"+data.MINGCHENG+"-"+data.DUIZHANGMC +data.XIANGMUMC +data.SHFENFENZHENG +data.GONGHAO;  
           },  
           formatMatch: function(data, i, total) { 
             return data.XINGMING+"-"+data.MINGCHENG+"-"+data.DUIZHANGMC +data.XIANGMUMC +data.SHFENFENZHENG +data.GONGHAO;  
           },  
           formatResult: function(data) {
             return data.XINGMING ;  
           }
         })
       }  
	 
	 $("#searchgrxx").result(function(event, data, formatted) {
		 var PKID=data.PKID;
		 var XIANGMU_BIANMA=data.XIANGMU_BIANMA;
		 var tableObj = document.getElementById("tbitem");
		 var rows=tableObj.rows;
		 var boolean=false;
		 
			 for (var i = 0; i < rows.length; i++) {
			       //遍历Table的所有Row
				 if(rows[i].cells[4].innerText==PKID){
					 boolean=true;
				 }
				 }
			   
		 
			 if(!boolean){
				 $("#tbitem").append('<tr><td><div style="word-break: break-all;">'+data.XINGMING+'</div></td><td>'+data.SHFENFENZHENG+'</td><td>'+data.BZMINGCHENG+'</td>'+
				 '<td>'+data.MINGCHENG+'</td>'+'<td hidden>'+data.PKID+'</td>'+'<td hidden>1</td><td><span class="glyphicon glyphicon-trash" data-operation="remove"></span></td></tr>');
			 }else{
				 BootstrapDialog.show({  //显示需要提交的表单。
		       		  title:"提示信息",
		                message:"该人员已经添加,请重新选择!",
		                buttons: [{
		    		    label: '关闭',
		    		    cssClass: 'btn-warning keman_btn',
		    		    action: function(dialogRef){
		    		       dialogRef.close();
		    		    }
		    		  }
		    		  ]
		            });
			 }
		 
		
		   }); 
	 
	 $( document ).on( "click", ".glyphicon-trash", function(){ 
		         $(this).parent().parent().remove();
	}); 
	 
	 $("#searchbzxx").keydown( function(event){
		 var content=$(this).val();
		  //alert(content);
		  var time=new Date().getTime();
		  
		  $.ajax({
				type:"post",
				dataType:"json",
				 data:{p:content,time:time},
				url:"goodbad/zdtcbzxx.json",
			success : function(data) {
				autocompleteBZFN(data.jsonarray);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown);
			}
		});
	 });
	 
	 
	 function autocompleteBZFN(names){
		var names1= jQuery.parseJSON( names );
		
         $("#searchbzxx").autocomplete(names1,{  
           minChars:1,  
           selectFirst: false,
           matchContains: true,
           max: 10,  
           autoFocus:false,
           dataType:"json",  
           scrollHeight: 220,  
           formatItem: function(data, i, total) {
        	   if(data.XINGMING==" " || data.XINGMING==null){
        		   return data.BZMINGCHENG;   
        	   }else{
        		   return data.XINGMING+"-"+data.BZMINGCHENG;  
        	   } 
           },  
           formatMatch: function(data, i, total) { 
        	   if(data.XINGMING==" " || data.XINGMING==null){
        		   return data.BZMINGCHENG;   
        	   }else{
        		   return data.XINGMING+"-"+data.BZMINGCHENG;  
        	   }
           },  
           formatResult: function(data) {
             return data.BZMINGCHENG ;  
           }
         })
       }  
	 $("#searchbzxx").result(function(event, data, formatted) {
			var PKID=data.BZPKID;
			 var XIANGMU_BIANMA=data.XIANGMU_BIANMA;
			var tableObj = document.getElementById("tbitem2");
			 var rows=tableObj.rows;
			 var boolean=false;
			 
				 for (var i = 0; i < rows.length; i++) {
				       //遍历Table的所有Row
					 if(rows[i].cells[3].innerText==PKID){
						 boolean=true;
					 }
					 }
				   
			 
				 if(!boolean){
					 $("#tbitem2").append('<tr><td><div style="word-break: break-all;">'+data.BZMINGCHENG+'</div></td><td>'+data.XINGMING+'</td><td>'+data.GZMC+'</td>'+'<td hidden>'+data.BZPKID+'</td>'+
		  	         '<td hidden>2</td><td><span class="glyphicon glyphicon-trash" data-operation="remove"></span></td></tr>');
				 }else{
					 BootstrapDialog.show({  //显示需要提交的表单。
			       		  title:"提示信息",
			                message:"该班组已经添加,请重新选择!",
			                buttons: [{
			    		    label: '关闭',
			    		    cssClass: 'btn-warning keman_btn',
			    		    action: function(dialogRef){
			    		       dialogRef.close();
			    		    }
			    		  }
			    		  ]
			            });
				 }
			
				           }); 


//保存
$('#save').bind("click",function(){
	var jllx = $("#jllx").val();  //奖励类型
	var RIQI = $("#RIQI").val();  //奖励类型
	var jlmc = $("#jlmc").val();  //奖励类型
	var beizhu = $("#beizhu").val();  //奖励类型
      var datas=new Array();
      var datas2=new Array();
       var tableObj = document.getElementById("tbitem");
       var tableObj2 = document.getElementById("tbitem2");
       var rows=tableObj.rows;
       var rows2=tableObj2.rows;
       var blackVo=new Object();
	    var renyuans={};
	    var bzs={};
	    if(jlmc==""){
	       	 BootstrapDialog.show({  //显示需要提交的表单。
	       		  title:"提示信息",
	                message:"惩罚名称必须填写!",
	                buttons: [{
	    		    label: '关闭',
	    		    cssClass: 'btn-warning keman_btn',
	    		    action: function(dialogRef){
	    		       dialogRef.close();
	    		    }
	    		  }
	    		  ]
	            });
	       	  return false;
	        }
	    if(rows.length==0 && rows2.length==0){
	        	 BootstrapDialog.show({  //显示需要提交的表单。
	        		  title:"提示信息",
                     message:"惩罚中应至少包含一个工人或队伍!",
                     buttons: [{
         		    label: '关闭',
         		    cssClass: 'btn-warning keman_btn',
         		    action: function(dialogRef){
         		       dialogRef.close();
         		    }
         		  }
         		  ]
                 });
	        	  return false;
	         }
   for (var i = 0; i < rows.length; i++) { 
       //遍历Table的所有Row
     datas[datas.length]={Name:rows[i].cells[0].innerText,Sfzhm:rows[i].cells[1].innerText,Bzmc:rows[i].cells[2].innerText,gz:rows[i].cells[3].innerText,PKID:rows[i].cells[4].innerText,BANZUHUOGONGREN:rows[i].cells[5].innerText
       ,jllx:jllx,RIQI:RIQI,jlmc:jlmc,beizhu:beizhu};
   }
   for (var j = 0; j < rows2.length; j++) { 
       //遍历Table的所有Row
     datas2[datas2.length]={bzmc:rows2[j].cells[0].innerText,bzz:rows2[j].cells[1].innerText,gz:rows2[j].cells[2].innerText,PKID:rows2[j].cells[3].innerText,BANZUHUOGONGREN:rows2[j].cells[4].innerText
       ,jllx:jllx,RIQI:RIQI,jlmc:jlmc,beizhu:beizhu};
   }
    
    blackVo={renyuans:datas,bzs:datas2};
    //获取附件数据
	 var fjfiles = zhubiao.files;
	 var fjfilesStr = JSON.stringify(fjfiles);//转换为json字符串
	 blackVo.fjfilesStr = fjfilesStr;
	 //end
	 
	$.ajax({
		type: 'POST',   
        data: JSON.stringify(blackVo),  
		//data:{'renyuans':JSON.stringify(datas),'fjfilesStr':fjfilesStr},
      // data:{'rydata':JSON.stringify(datas),'edudata':JSON.stringify(datas),'fjfilesStr':fjfilesStr},
	    url:'goodbad/testListwg.json',
       dataType: 'json',  
       contentType : 'application/json',       
       success: function(result) {
       	//附件上传
       	black.pkidList = result.pkidList;
       	if(zhubiao.isHaveUploadFile()==true){
				var pkidObj = black.pkidList[0];
	        	var PKID = pkidObj.PKIDRY;//人员pkid
			    //处理附件上传
				//设置附件表名称和目录名称  
				myupload.stream.config.postVarsPerFile = {tableName: "T_JIANGCHENG_FJ", mulu: PKID};
				//end
				//执行上传附件
				myupload.stream.upload();
				//end
       	}else{
       		BootstrapDialog.show({
	             title: '提示信息',
	             closable: true, 
	             message: '新增成功！',
	             buttons: [{
	                 label: '关闭',
	                cssClass: 'btn-primary keman_btn',
	                 action: function(dialog) {
	                    dialog.close();
	                    $("#nameli").load(_basepath+"goodbad/goodbad.php");
	               	    $("#erjie").empty();
	               	    $("#erjie").hide();
	                 }
	            		 }]
	        		 });
       		
       	}
       },
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
});
	 
	

});