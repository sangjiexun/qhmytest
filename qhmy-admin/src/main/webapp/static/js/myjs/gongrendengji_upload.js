/**
 * 本模块的js json对象
 */
var gongrendengji = {};

/*
 * 上传组件主表对象
 */
var op = {};
var zhubiao = null;
var myupload = null;

var dialog = null;

//end 上传组件主表对象

/*
 * 删除附件行
 */
gongrendengji.delFjTr = function(fileId){
	var PKID = $("#PKID").val();//劳务人员PKID
	//删除行
	$("#"+fileId).remove();
	//删除物理文件,删除数据库附件表记录
	
	var dataParams = new Array();
	dataParams.push({"name":"laowurenyuan_pkid","value":PKID});
	dataParams.push({"name":"fileid","value":fileId});
	
	BootstrapDialog.show({//显示需要提交的表单。
		title:'提示信息',
	    message:"确认删除该证书吗？",
        buttons: [{
		    label: '确定',
		    closable: true,
            draggable: true,
		    cssClass: 'keman_btn',
		    action: function(dialogRef){
		       dialogRef.close();
			   $.ajax({
					type: 'post',
				    url: _basepath+'workerTeam/delHtfj.json',
				    data: dataParams,
				    dataTpye:"json",
				    success: function(data) {
				    	if(data.result==true){
					    	
				    	}else{
				    		
				    	}
				    }
				});
		    }
	    },{
		    label: '取消',
		    cssClass: 'keman_btn',
		    action: function(dialogRef){
		       dialogRef.close();
		    }
	    }
	    ]
    });
};

gongrendengji.destroy = function(){
	//取消未上传列表,由于只是隐藏，因此要取消
		//取消上传任务
  	myupload.delAllTask();
  	myupload.chearTableTr();
  	myupload.destroy();//销毁上传对象
  	zhubiao = null;//主表对象为空
  	//清空附件行 end 取消未上传列表,由于只是隐藏，因此要取消
};



gongrendengji.formatBytes = function(size) {
	var size = size * 1;
	if (size < 100) {
		return (size + 'B');
	} else if (size < 102400) {
		size = Math.round(100 * (size / 1024)) / 100;
		size = isNaN(size) ? 0 : parseFloat(size).toFixed(2);
		return (size + 'K');
	} else if (size < 1047527424) {
		size = Math.round(100 * (size / 1048576)) / 100;
		size = isNaN(size) ? 0 : parseFloat(size).toFixed(2);
		return (size + 'M');
	}
	
	size = Math.round(100 * (size / 1073741824)) / 100;
	size = isNaN(size) ? 0 : parseFloat(size).toFixed(2);
	return (size + 'G');
};


/*
 * 初始化上传组件
 */
gongrendengji.initUpload = function(fileObjs){
	
	if(myupload!=null){
		return myupload;
	}
	
	//创建一个附件上传组件对象
	var option = {
		browseFileId : "i_select_files", /** 文件选择的Dom Id，如果不指定，默认是i_select_files  文件选择的按钮的ID */
		fileRongqiId : "bootstrap-stream-container",//扩展属性，存储文件存放的容器id  对应页面的tbody的id
		autoUploading: true,//自动上传
		onSelect: function(files) {
			//选择后自定上传，先存储数据库附件表记录
		},
		onRepeatedFile: function(file) {
			  //重复文件
//			  console.log("文件： " + file.name
//			   + " 大小：" + file.size + "已存在于上传队列中");
//			  var tempFile = {};
//			  tempFile.fileid = file.id;
//			  gongrendengji.delFjTr(file.id);
//			  zhubiao.remove(tempFile);
			  return false;
		},
		onMaxSizeExceed: function(file) {
			BootstrapDialog.show({
				title:'提示信息',
			    message:'文件[name='+file.name+']超过文件大小限制'+file.formatLimitSize,
		        buttons: [{
				    label: '关闭',
				    closable: true,
		            draggable: true,
				    cssClass: 'keman_btn',
				    action: function(dialogRef){
				       dialogRef.close();
				    }
			    }
			    ]
		    });
		},
		onFileCountExceed : function(selected, limit) {
			//超出文件个数限制时回调方法
			BootstrapDialog.show({
				title:'提示信息',
			    message:'文件最多同时上传<strong>'+limit+'</strong>个文件',
		        buttons: [{
				    label: '关闭',
				    closable: true,
		            draggable: true,
				    cssClass: 'keman_btn',
				    action: function(dialogRef){
				       dialogRef.close();
				    }
			    }
			    ]
		    });
		},
		onExtNameMismatch: function(info) {
			//文件的扩展名不匹配的响应事件
			BootstrapDialog.show({
				title:'提示信息',
			    message:'扩展名不合法',
		        buttons: [{
				    label: '关闭',
				    closable: true,
		            draggable: true,
				    cssClass: 'keman_btn',
				    action: function(dialogRef){
				       dialogRef.close();
				    }
			    }
			    ]
		    });
		},
		onAddTask: function(file) {
			var filename = "";
			 if(file.name.length>=10){
				 filename = file.name.substring(0,10)+"...";
			 }else{
				 filename = file.name;
			 }
			 //添加文件到上传列表中的响应事件
			 var file = '<tr id="' + file.id + '"  filename="' + file.name + '" filesize="' + file.formatSize + '" class="template-upload fade in">' +
		     '<td style="display: none;"><span class="preview">'+file.id+'</span></td>' +
		     '<td><p class="name">' + filename + '</p>' +
		     '</td>' +
		     '<td><p class="size">' + file.formatSize + '</p>' +
		     '</td>' +
		     '<td><span style="cursor: pointer;" class="glyphicon glyphicon-remove" onClick="javascript:gongrendengji.delFjTr(\'' + file.id + '\')"></span>' +
		     '</td></tr>';
			$("#bootstrap-stream-container").append(file);
		},
		onUploadProgress: function(file) {
			//上传进度
		},
		onStop: function() {
			//停止上传后的回调函数
		},
		onCancel: function(file) {
			//取消某个上传文件时的回调函数
//			BootstrapDialog.alert({title: '提示信息',message: '取消某个文件,从上传列表删除,'+file.id });
		},
		onCancelAll: function(numbers) {
			//取消全部上传文件时的回调函数
		},
		onComplete: function(file) {
			//单个文件上传完毕的响应事件 
			//上传完成后，修改tr行，添加下载按钮
			$("#"+file.id).find(".glyphicon-remove").parent().append("<a href=javascript:void(0);>下载</a>");
			//注册下载点击事件
			var laowurenyuan_pkid = $("#PKID").val();
			
			//下载文件
			$("#"+file.id).find("a").bind("click",function(){
				var url = _basepath+"file/download.json?r="+Math.random()+"&fileid="+file.id+"&laowurenyuan_pkid="+laowurenyuan_pkid+"&tableName=T_LAOWURENYUAN_HT";//下载文件地址
				window.location.href=url;
			});
			
			var fileid = file.id;//文件ID
			var filename = file.name;//文件名称
			var filesize = file.size;//文件大小
			var gonghao = $(".lbr-worker-number").html();//工号
			var htbh = $(".txt-contract-code").val();//合同编号
			
			var dataParams = new Array();
			dataParams.push({"name":"laowurenyuan_pkid","value":laowurenyuan_pkid});
			dataParams.push({"name":"fileid","value":fileid});
			
			dataParams.push({"name":"filesize","value":filesize});
			dataParams.push({"name":"gonghao","value":gonghao});
			dataParams.push({"name":"htbh","value":htbh});
			
			dataParams.push({"name":"filename","value":filename});
			
			//保存附表数据库记录
			$.ajax({
				 type: 'post',
				    url: _basepath+'workerTeam/saveHtfj.json',
				    data: dataParams,
				    dataTpye:"json",
				    success: function(data) {
				    	if(data.result==true){
				    		BootstrapDialog.show({
								title:'提示信息',
							    message:"添加成功！",
					            buttons: [{
								    label: '关闭',
								    cssClass: 'keman_btn',
								    action: function(dialogRef){
								       dialogRef.close();
								    }
							    }]
				            });
				    	}else{
				    		
				    	}
				    }
			});
			//end 保存
			
			
			
			
//			BootstrapDialog.alert({title: '提示信息',message: '['+file.name+']:上传成功!' });
		},
		onQueueComplete: function(msg) {
			//全部上传完成的回调方法
//			gongrendengji.destroy();
			//BootstrapDialog.alert({title: '提示信息',message: '全部文件上传成功!' });
		},
		onUploadError: function(status, msg) {
			//文件上传出错的响应事件
			BootstrapDialog.show({
				title:'提示信息',
			    message:'上传附件失败,'+msg+',状态码:'+status,
		        buttons: [{
				    label: '关闭',
				    closable: true,
		            draggable: true,
				    cssClass: 'keman_btn',
				    action: function(dialogRef){
				       dialogRef.close();
				    }
			    }
			    ]
		    });
		}
	};
	var PKID = $("#PKID").val();
	
	myupload = new fhadminUpload(option);
	
	myupload.stream.config.postVarsPerFile = {tableName: "T_LAOWURENYUAN_HT", mulu: PKID};
};




/**
 * 上传证书数据及上传附件   保存
 */
gongrendengji.uploadZs = function(dialog){
	
	var laowurenyuan_pkid = $("#PKID").val();
	var gonghao = $(".lbr-worker-number").html();//工号
	var datas = $("#zhengshuFrom").serializeArray();
	
	var pkid = $("#ZSPKID").val();
	var newpkid = $("#NEWPKID").val();
	
//	id="' + file.id + '"  filename="' + file.name + '" filesize="' + file.formatSize + '"  _size="' + file.size + '"
	
	var file = zhubiao2.files;
	
	
	if(file!=null && file.length>0){
		fileStr = JSON.stringify(file);//转换为json字符串
	}else{
		fileStr = "[]";
	}
	
	datas.push({"name":"fileStr","value":fileStr});
	datas.push({"name":"laowurenyuan_pkid","value":laowurenyuan_pkid});
	datas.push({"name":"gonghao","value":gonghao});
	datas.push({"name":"pkid","value":pkid});
	datas.push({"name":"newpkid","value":newpkid});
	
	$.ajax({
		type: 'post',
	    url: _basepath+'workerTeam/saveZs.json',
	    data: datas,
	    dataTpye:"json",
	    success: function(data) {
	    	if(data.result=="success"){
	    		//文件上传
			    //处理附件上传
				//设置附件表名称和目录名称
	    		
	    		gongrendengji.loadZsList();//刷新右侧列表区域  加载证书列表
    			addzhengshu.destroy();
    			dialog.close();
	    		layer.msg('操作成功',{skin : 0,offset: [($(window).height())/8, ($(window).width())/2.2]});
	    	}else{
	    		
	    	}
	    }
	});
	function tipsshow(word){
		$(".message-hint").html(word);
		$(".message-hint-wrap").show();
		setTimeout('$(".message-hint-wrap").hide();',1500);
		return false;
	}
};

/**
 * 加载证书列表
 */
gongrendengji.loadZsList = function(){
//	console.log("加载证书列表!");
	
	var laowurenyuan_pkid = $("#PKID").val();
	
	var dataParams = new Array();
	dataParams.push({"name":"laowurenyuan_pkid","value":laowurenyuan_pkid});
	
	$.ajax({
		type: 'post',
	    url: _basepath+'workerTeam/zhengshulist.json',//加载证书list
	    data: dataParams,
	    dataTpye:"html",
	    success: function(html) {
	    	
    		$("#zhengshuListDiv").html(html);
    		
    		//注册编辑事件
    		$("#zhengshuListDiv").find("a[data-operation='modify']").click(function(event){
    			event.preventDefault();
    			var pkid = $(this).attr("pkid");
    			/*
    			 * 模态框
    			 */
    			dialog = BootstrapDialog.show({
    				title:'编辑证书',
    				message: $('<div></div>').load("workerTeam/addzhengshu.json?PKID="+pkid),
    				closable: true,//是否显示叉号
    				draggable: true,//可以拖拽
    				buttons: [{
    	                label: '保存',
    	                cssClass: 'keman_btn',
    	                action: function(dialog) {
    	                	var rst = addzhengshu.yanzheng();
    	                	if(rst){
    	                		//插入证书数据，持久化到数据库,附件上传
    	                		gongrendengji.uploadZs(dialog);
    	                	}
    	                }
    	            }, {
    	                label: '取消',
    	                cssClass: 'keman_btn',
    	                action: function(dialog) {
    	                	dialog.close();
    	                }
    	            }],
    	            onshow: function(dialogRef){
    	            	//内容加载完成，但是弹出层UI还没有显示时调用
    	            },
    	            onshown: function(dialogRef){
    	            	//内容加载完成，弹出层UI显示时调用
    	            },
    	            onhide: function(dialogRef){
    	            	//将要关闭时调用。
    	            },
    	            onhidden: function(dialogRef){
    	            	//UI关闭后低啊用
    	            }
    			});
    			
    			dialog.setSize(BootstrapDialog.SIZE_WIDE);//设置宽度
//    			dialog.setSize(BootstrapDialog.SIZE_LARGE);
    			//end 模态框
    		});
    		
    		//注册删除事件
    		$("#zhengshuListDiv").find("a[data-operation='remove']").click(function(event){
    			event.preventDefault();
    			var pkid = $(this).attr("pkid");
    			var $this = $(this);
    			BootstrapDialog.show({ 
					title:'提示信息',
			        message: '确定要删除该证书？',
			        closable: true, 
			          buttons: [{
					    label: '确定',
					    cssClass: 'btn-success keman_btnx',
					    action: function(dialogRef){
					       dialogRef.close();
					     //end 注册删除事件
					       $.ajax({
			    				type: 'post',
			    			    url: _basepath+'workerTeam/delZs.json?pkid='+pkid,
			    			    data: null,
			    			    dataTpye:"json",
			    			    success: function(data) {
			    			    	if(data.result == "success"){
			    			    		BootstrapDialog.show({//显示需要提交的表单。
			    							title:'提示信息',
			    						    message:"删除成功！",
			    						    closable: true,
			    						    draggable: true,
			    				            buttons: [{
			    							    label: '关闭',
			    							    cssClass: 'keman_btn',
			    							    action: function(dialogRef){
			    							       dialogRef.close();
			    							    }
			    						    }]
			    			            });
			    			    		$this.parent().parent().parent().parent().remove();
			    			    	}
			    			    }
			    			});
			            }
				  }, {
				    label: '关闭',
				    cssClass: 'btn-warning keman_btnx',
				    action: function(dialogRef){
				       dialogRef.close();
				    }
				  }
				  ]
    			});
    			
    		});
    		
	    }
	});
	
};
//end 



$(function(){
	var PKID=$("#PKID").val();
	if(PKID == null || PKID == ""){
		
	}else{
		gongrendengji.initUpload();
		
		//加载合同附件行
		$("#bootstrap-stream-container").find("tr").each(function(i){
			var $this = $(this);
			var fileid = $this.attr("id");
			var filename = $this.attr("filename");
			var filesize = $this.attr("filesize");
			
			//格式化文件大小
			var psize = $this.find("p.size").html();
			$this.find("p.size").html(gongrendengji.formatBytes(psize));
			//end 格式化文件大小
			
			//下载文件
			$this.find("a").bind("click",function(){
				var url = _basepath+"file/download.json?r="+Math.random()+"&fileid="+fileid+"&laowurenyuan_pkid="+PKID+"&tableName=T_LAOWURENYUAN_HT";//下载文件地址
				window.location.href=url;
			});
			
		});
		
		
		/**
		 * 添加证书
		 */
		$("#tianjiazsButton").click(function(){
			/*
			 * 模态框
			 */
			dialog = BootstrapDialog.show({
				title:'新增证书',
				message: $('<div></div>').load("workerTeam/addzhengshu.json"),
				closable: true,//是否显示叉号
				draggable: true,//可以拖拽
				buttons: [{
	                label: '保存',
	                cssClass: 'keman_btn',
	                action: function(dialog) {
	                	var rst = addzhengshu.yanzheng();
	                	if(rst){
	                		//插入证书数据，持久化到数据库,附件上传
	                		gongrendengji.uploadZs(dialog);
	                	}
	                }
	            }, {
	                label: '取消',
	                cssClass: 'keman_btn',
	                action: function(dialog) {
	                	dialog.close();
	                }
	            }],
	            onshow: function(dialogRef){
	            	//内容加载完成，但是弹出层UI还没有显示时调用
	            },
	            onshown: function(dialogRef){
	            	//内容加载完成，弹出层UI显示时调用
	            },
	            onhide: function(dialogRef){
	            	//将要关闭时调用。
	            },
	            onhidden: function(dialogRef){
	            	//UI关闭后低啊用
	            }
			});
			
			dialog.setSize(BootstrapDialog.SIZE_WIDE);//设置宽度
//			dialog.setSize(BootstrapDialog.SIZE_LARGE);
			//end 模态框
			
		});
		//end 添加证书
		
		
		gongrendengji.loadZsList();//加载证书列表
		
		
	}
});



//	console.log(config.tokenURL);
//	console.log(config.frmUploadURL);
//	console.log(config.uploadURL);
	