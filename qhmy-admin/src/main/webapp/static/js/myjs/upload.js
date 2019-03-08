/**
 * 配置文件（如果没有默认字样，说明默认值就是注释下的值）
 * 但是，on*（onSelect， onMaxSizeExceed...）等函数的默认行为
 * 是在ID为i_stream_message_container的页面元素中写日志
 */
(function($){
	
	/**
	 * 文件类
	 * fileid文件ID
	 * filename文件名称
	 * shifouysc是否已上传到服务器
	 * option {fileid:'',filename:'',shifouysc:false|true def=false} 
	 */
	var FileVo = function(option){
		this.config = {
			shifouxs:true,
			delFlag:false,
			shifouysc:false
		};
		
		$.extend(this.config,option);
		
		this.filename = this.config.filename;
		
		this.fileid = this.config.fileid;
		
		this.filesize = this.config.filesize;
		
		//存储业务数据
		this.data = {};
		
		/*
		 * 是否显示  只有点删除了,才需要设置为false
		 */
		this.shifouxs = this.config.shifouxs;
		
		/*
		 * 用户是否点删除
		 */
		this.delFlag = this.config.delFlag;
		
		/*
		 * 是否已上传
		 */
		this.shifouysc = this.config.shifouysc;
		
		/*
		 * 格式化字节大小
		 */
		this.formatBytes = function(size) {
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
		
	};
	
	
	/**
	 * 主表类 每个主表会有多个文件类对象  基于数组
	 * yscDivId:'dbfujian_div',//已上传divID
	 * yscUl:'yscwj1',//已上传Ul Id
	 */
	var Zhubiao = function(option){
		
		this.config = {
			yscDivId:"",
			yscUl:"",
			fileRongqiId:"bootstrap-stream-container",
			removeCallback:function(op){},  //删除的回调函数
			addCallback:function(op){}  //添加的回调函数
		};
		
		$.extend(this.config,option);
		
		/*
		 * 文件容器
		 */
		this.files = new Array();
		
		
		/**
		 * 去掉删除按钮
		 */
		this.hiddenDownButton = function(){
			$("#"+this.config.fileRongqiId).find("a").css("display","none");
		};
		
		/**
		 * 添加文件
		 */
		this.add = function(file){
			this.files[this.files.length] = file;
			//调用回调函数
			this.config.addCallback(op);
		};
		
		/**
		 * 删除文件
		 */
		this.remove = function(file){
			
			var myfile = this.getIndex(file);
			
			if(myfile!=null){
				//this.files.splice(index,index);//删除数组中的file对象     不直接删除file对象了,修改属性
				myfile.shifouxs = false;//不显示了
				myfile.delFlag = true;//已经删除
				//调用回调函数
				this.config.removeCallback(op);
				return myfile;
			}else{
				return myfile;
			}
		};
		
		/**
		 * 查找file对象的索引位置
		 */
		this.getIndex = function(file){
			for(var i=0;i<this.files.length;i++){
				if(this.files[i].fileid == file.fileid){
					return this.files[i];
				}
			}
			return null;
		};
		
		
		/**
		 * 检查是否有需要上传的文件
		 */
		this.isHaveUploadFile = function(){
			var isHave = false;
			for(var i=0;i<this.files.length;i++){
				if(this.files[i].shifouxs == true && this.files[i].shifouysc==false){
					isHave = true;
					break;
				}
			}
			return isHave;
		};
		
		
		/**
		 * 加载已上传文件,将已上传文件加载到Zhubiao容器中,并且动态生成行代码
		 */
		this.loadYscFile = function(files){
			var tempArray = new Array();
			var self = this;
			
//			<li fileid="12123123123" filename="xxx.doc" filesize="50KB" >xxx.doc</li>
//			<li fileid="45643645646" filename="yyy.doc" filesize="10KB" >yyy.doc</li>
			
			
			if(files == null){
				//通过ul加载
				$("#"+this.config.yscUl).find("li").each(function(i){
					var filename = $(this).attr("filename");
					var filesize = $(this).attr("filesize");
					var fileid = $(this).attr("fileid");
					var _data = $(this).attr("_data");
					
					var dataJson = null;
					if(_data!=null && _data!=""){
						//解析成json对象
						dataJson = $.parseJSON(_data);
					}else{
						dataJson = {};
					}
					
					tempArray[i] = new fhadminFileVo();
					tempArray[i].shifouysc = true;//是否已上传到服务器
					tempArray[i].filename = filename;
					tempArray[i].fileid = fileid;
					tempArray[i].filesize = filesize;
					tempArray[i].data = dataJson;//业务数据
					
					fhadminUploadUtil.addTr(self,self.config.fileRongqiId,tempArray[i]);//插入行
					
					self.files[self.files.length] = tempArray[i];//直接添加，不调用回调方法
				});
			}else{
				//通过files加载
				if(files instanceof Array){
					//如果是数组
					for(var i=0;i<files.length;i++){
						tempArray[i] = new fhadminFileVo();
						tempArray[i].filename = files[i].filename;
						tempArray[i].fileid = files[i].fileid;
						tempArray[i].filesize = files[i].filesize;
						tempArray[i].shifouysc = true;//是否已上传到服务器 
						tempArray[i].data = files[i].data;
						fhadminUploadUtil.addTr(this,this.config.fileRongqiId,tempArray[i]);//插入行
						
						//动态生成行数据
						this.files[this.files.length] = tempArray[i];//直接添加，不调用回调方法
					}
				}else{
					//不是数组
					
				}
			}
		};
		//end 加载已上传文件
		
		
		
	};
	
	
	
	
	
	/**
	 * 文件上传二次封装类
	 */
	var upload = function(option){
		var self = this;
		var def_config = {
				enabled: true, /** 是否启用文件选择，默认是true */
				customered: true,//是否自定义UI
				multipleFiles: true, /** 是否允许同时选择多个文件，默认是false */	
				autoRemoveCompleted: false, /** 是否自动移除已经上传完毕的文件，非自定义UI有效(customered:false)，默认是false */
				autoUploading: false, /** 当选择完文件是否自动上传，默认是true */
				fileFieldName: "FileData", /** 相当于指定<input type="file" name="FileData">，默认是FileData */
				maxSize: 5242880, /**  当_t.bStreaming = false 时（也就是Flash上传时），2G就是最大的文件上传大小！所以一般需要  2147483648 =2G  5242880 = 5MB */
				simLimit: 100000, /** 允许同时选择文件上传的个数（包含已经上传过的） */
//				extFilters: [".txt", ".gz", ".jpg", ".png", ".jpeg", ".gif", ".avi", ".html", ".htm"], /** 默认是全部允许，即 [] */
				browseFileId : "i_select_files", /** 文件选择的Dom Id，如果不指定，默认是i_select_files  文件选择的按钮的ID */
				browseFileBtn : "<div>选择文件</div>", /** 选择文件的按钮内容，非自定义UI有效(customered:false) */
//				dragAndDropArea: "i_stream_dropzone", /** 拖拽上传的容器ID   */
//				filesQueueId : "i_stream_files_queue", /** 文件上传进度显示框ID，非自定义UI有效(customered:false) */
//				filesQueueHeight : 450, /** 文件上传进度显示框的高，非自定义UI有效(customered:false)，默认450px */
//				messagerId : "i_stream_message_container", /** 消息框的Id，当没有自定义onXXX函数，系统会显示onXXX的部分提示信息，如果没有i_stream_message_container则不显示 */
				tokenURL : _basepath+"file/token.json",
				swfURL: _basepath+"static/swf/FlashUploader.swf" ,
				frmUploadURL : _basepath+"file/fd.json", /** Flash上传的URI */
				postVarsPerFile:{tableName: "T_FENBAOSHANG_FJ", mulu: ""}, /** 上传文件时的其他参数  */
				uploadURL : _basepath+"file/upload.json",
				fileRongqiId:"bootstrap-stream-container",
				onSelect: function(files) {
					//选择文件后的响应事件
					//console && console.log("-------------onSelect-------------------");
					//console && console.log(files);
					//console && console.log("-------------onSelect-------------------End");
				},
				onRepeatedFile: function(file) {
					  //重复文件
//					  console.log("文件： " + file.name
//					   + " 大小：" + file.size + "已存在于上传队列中");
					  return false;
				},
				onMaxSizeExceed: function(file) {
					//超出文件大小时的提示
					//console && console.log("-------------onMaxSizeExceed-------------------");
					//console && console.log(file);
					BootstrapDialog.alert({title: '提示信息',message: '文件[name='+file.name+'超过文件大小限制'+file.formatLimitSize });
					//console && console.log("-------------onMaxSizeExceed-------------------End");
				},
				onFileCountExceed : function(selected, limit) {
					//超出文件个数限制时回调方法
					//console && console.log("-------------onFileCountExceed-------------------");
					//console && console.log(selected + "," + limit);
					BootstrapDialog.alert({title: '提示信息',message: '文件最多同时上传<strong>'+limit+'</strong>个文件'});
//					$("#i_error_tips > span.text-message").append("同时最多上传<strong>"+limit+"</strong>个文件，但是已选择<strong>"+selected+"</strong>个<br>");
					//console && console.log("-------------onFileCountExceed-------------------End");
				},
				onExtNameMismatch: function(info) {
					//文件的扩展名不匹配的响应事件
					BootstrapDialog.alert({title: '提示信息',message: '扩展名不合法'});
					//console && console.log("-------------onExtNameMismatch-------------------");
					//console && console.log(info);
//					$("#i_error_tips > span.text-message").append("<strong>"+info.name+"</strong>文件类型不匹配[<strong>"+info.filters.toString() + "</strong>]<br>");
					//console && console.log("-------------onExtNameMismatch-------------------End");
				},
				onAddTask: function(file) {
					 //添加文件到上传列表中的响应事件
					 var file = '<tr id="' + file.id + '"  filename="' + file.name + '" filesize="' + file.formatSize + '" class="template-upload fade in">' +
				     '<td style="display: none;"><span class="preview">'+file.id+'</span></td>' +
				     '<td><p class="name">' + file.name + '</p>' +
//				     '    <div><span class="label label-info">进度：</span> <span class="message-text"></span></div>' +
//				     '    <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">' +
//					 '			<div class="progress-bar progress-bar-success" title="" style="width: 0%;"></div>' +
//					 '	  </div>' +
				     '</td>' +
				     '<td><p class="size">' + file.formatSize + '</p>' +
				     '</td>' +
				     '<td><span class="glyphicon glyphicon-remove" onClick="javascript:_t.cancelOne(\'' + file.id + '\')"></span>' +
				     '</td></tr>';
					
					$("#"+def_config.fileRongqiId).append(file);
				},
				onUploadProgress: function(file) {
					//上传进度
					//console && console.log("-------------onUploadProgress-------------------");
					//console && console.log(file);
					
//					var $bar = $("#"+file.id).find("div.progress-bar");
//					$bar.css("width", file.percent + "%");
//					var $message = $("#"+file.id).find("span.message-text");
//					$message.text("已上传:" + file.formatLoaded + "/" + file.formatSize + "(" + file.percent + "%" + ") 速  度:" + file.formatSpeed);
					
//					var $total = $("#stream_total_progress_bar");
//					$total.find("div.progress-bar").css("width", file.totalPercent + "%");
//					$total.find("span.stream_total_size").html(file.formatTotalLoaded + "/" + file.formatTotalSize);
//					$total.find("span.stream_total_percent").html(file.totalPercent + "%");
					
					//console && console.log("-------------onUploadProgress-------------------End");
				},
				onStop: function() {
					//停止上传后的回调函数
					
					//console && console.log("-------------onStop-------------------");
					//console && console.log("系统已停止上传！！！");
					//console && console.log("-------------onStop-------------------End");
				},
				onCancel: function(file) {
					//取消某个上传文件时的回调函数
					
//					BootstrapDialog.alert({title: '提示信息',message: '取消某个文件,从上传列表删除,'+file.id });
					
					//console && console.log("-------------onCancel-------------------");
					//console && console.log(file);
					
//					$("#"+file.id).remove();
					
//					var $total = $("#stream_total_progress_bar");
//					$total.find("div.progress-bar").css("width", file.totalPercent + "%");
//					$total.find("span.stream_total_size").text(file.formatTotalLoaded + "/" + file.formatTotalSize);
//					$total.find("span.stream_total_percent").text(file.totalPercent + "%");
					//console && console.log("-------------onCancel-------------------End");
				},
				onCancelAll: function(numbers) {
					//取消全部上传文件时的回调函数
					
					//console && console.log("-------------onCancelAll-------------------");
					//console && console.log(numbers + " 个文件已被取消上传！！！");
//					$("#i_error_tips > span.text-message").append(numbers + " 个文件已被取消上传！！！");
					
					//console && console.log("-------------onCancelAll-------------------End");
				},
				onComplete: function(file) {
//					BootstrapDialog.alert({title: '提示信息',message: '['+file.name+']:上传成功!' });
					//console && console.log("-------------onComplete-------------------");
					//console && console.log(file);
					//单个文件上传完毕的响应事件 
					
					/** 100% percent */
//					var $bar = $("#"+file.id).find("div.progress-bar");
//					$bar.css("width", file.percent + "%");
//					var $message = $("#"+file.id).find("span.message-text");
//					$message.text("已上传:" + file.formatLoaded + "/" + file.formatSize + "(" + file.percent + "%" + ")");
//					/** remove the `cancel` button */
//					var $cancelBtn = $("#"+file.id).find("td > span");
//					$cancelBtn.remove();
					
					/** modify the total progress bar */
//					var $total = $("#stream_total_progress_bar");
//					$total.find("div.progress-bar").css("width", file.totalPercent + "%");
//					$total.find("span.stream_total_size").text(file.formatTotalLoaded + "/" + file.formatTotalSize);
//					$total.find("span.stream_total_percent").text(file.totalPercent + "%");
					
					//console && console.log("-------------onComplete-------------------End");
				},
				onQueueComplete: function(msg) {
					//所有文件上传完毕的响应事件
					
//					BootstrapDialog.alert({title: '提示信息',message: '全部文件上传成功!' });
					
					//console && console.log("-------------onQueueComplete-------------------");
					//console && console.log(msg);
					//console && console.log("-------------onQueueComplete-------------------End");
				},
				onUploadError: function(status, msg) {
					//文件上传出错的响应事件
					
					//console && console.log("-------------onUploadError-------------------");
					//console && console.log(msg + ", 状态码:" + status);
					BootstrapDialog.alert({title: '提示信息',message: '上传附件失败,'+msg+',状态码:'+status });
//					$("#i_error_tips > span.text-message").append(msg + ", 状态码:" + status);
					
					//console && console.log("-------------onUploadError-------------------End");
				},
				onDestroy: function(){
					//组件销毁事件
//					console.log("Stream插件已销毁！");
				}
		};
		this.config = {};
		
		$.extend(this.config,def_config,option);
		
		this.stream = new Stream(this.config);
		
		
		
		/**
		 * 检查文件名是否重复
		 */
		this.checkFileNameWeiyi = function(files){
			var rst = null;
			
			for(var i=files.length-1;i>0;i--){
//				console.log(files[i]);
				var temp = files[i];
				for(var j=i-1;j>0;j--){
					if(temp.name == files[j].name){
						//按文件名称匹配，如果重名提示不能添加
						rst = temp;
						break;
					}
				}
				
				if(!rst){
					break;
				}
			}
			return rst;
		};
		
		//end 检查文件名是否重复
		
		/**
		 * 返回文件列表  数组，数组元素是FileVo对象
		 */
		this.getFiles = function(){
			var files = new Array();
			
			$("#"+this.config.fileRongqiId).find("tr").each(function(i){
				var filename = $(this).attr("filename");
				var fileid = $(this).attr("id");
				var filesize = $(this).attr("filesize");
				files[i] = new FileVo({'filename':filename,'fileid':fileid,'filesize':filesize});
			});
			return files;
		};
		
		
		/**
		 * 删除所有任务
		 */
		this.delAllTask = function(){
			if(this.stream!=null){
				this.stream.cancel();
			}
		};
		
		/**
		 * 销毁对象
		 */
		this.destroy = function(){
			if(this.stream!=null){
				this.stream.destroy();
				this.stream = null;
			}
		};
		
		/**
		 * 清空附件行
		 */
		this.chearTableTr = function(){
			$("#"+this.config.fileRongqiId).html("");//清空附件行
		};
		
	}
	
	
	
	/**
	 * fhadminUploadUtil  工具函数
	 */
	var fhadminUploadUtil = {};
	
	//新增行
	fhadminUploadUtil.addTr = function(zhubiao,fileRongqiId,file){
		
		var dataJson = file.data;
		
//		console.log(dataJson);
		
		 var filename = "";
		 if(file.filename.length>=18){
			 filename = file.filename.substring(0,18)+"...";
		 }else{
			 filename = file.filename;
		 }
		
		//需要生成下载链接,下载链接，需要一个URL 参数需要主表的pkid+clientid
		var filestr = '<tr id="'+ file.fileid +'"  filename="' + file.filename + '" filesize="' + file.filesize + '" class="template-upload fade in">' +
	     '<td style="display: none;"><span class="preview">' + file.fileid + '</span></td>' +
	     '<td><p class="name">' + filename + '</p>' +
	     '</td>' +
	     '<td><p class="size">' + file.formatBytes(file.filesize) + '</p>' +
	     '</td>' +
	     '<td><a href=javascript:void(0);>下载</a><span yscspan="true" style="cursor: pointer;" class="glyphicon glyphicon-remove yscspan" ></span>' +
	     '</td></tr>';
		
		var $tr = $(filestr);
		$("#"+fileRongqiId).append(filestr);
		
		$tr = $("#"+fileRongqiId).find("tr[id='"+file.fileid+"']");
		
		
		/*
		 * 循环写自定义数据到tr行
		 */
		
		if(dataJson!=null){
			var dataStrHead = "{";
			var dataStrEnd = "}";
			var dataStr = "";
			for(var sx in dataJson){
//				console.log("dataJson sx:"+sx);
//				console.log("dataJson value:"+dataJson[sx]);
				dataStr = dataStr + "\"" + sx +"\":\""+dataJson[sx] + "\",";
//				$tr.attr(sx,dataJson['sx']);
			}
			
			if(dataStr!=""){
				dataStr = dataStr.substring(0, dataStr.length-1);
			}
//			console.log("dataStr:"+dataStr);
			
			var rstStr = dataStrHead + dataStr + dataStrEnd;
			
//			console.log("rstStr:"+rstStr);
			
			$tr.attr("_data",rstStr);
			$tr.attr("zidingyi",true);
			
			//end 循环写自定义数据到tr行
		}else{
		}
		
		//删除行的点击事件
		$("#"+fileRongqiId).find("tr[id='"+file.fileid+"']").find("span[yscspan]").bind("click",function(){
//			alert("sdfsdf");
			fhadminUploadUtil.delFjTr(zhubiao,file.fileid);
		});
		
		//下载文件
		$("#"+fileRongqiId).find("tr[id='"+file.fileid+"']").find("a").bind("click",function(){
			var $tr = $(this).parent().parent();
			var dataJson = null;
			if($tr.attr("_data")!=null && $tr.attr("_data")!=undefined){
				dataJson = $.parseJSON($tr.attr("_data"));
			}
			
			var url = _basepath+"file/download.json?r="+Math.random()+"&fileid="+file.fileid;//下载文件地址
			
			var params = "";
			for(var sx in dataJson){
				params = params + "&" + sx + "="+dataJson[sx] + "";
				
			}
//			console.log(params);
//			
//			console.log(url+params);
//			var $doc = $(document);
//			$("<form id='_hidden_form'></form>").appendTo($doc);
//			
//			$("#_hidden_form").append("");
//			
//			var url = "";//下载文件地址
			window.location.href=url+params;
		});
		
	};
	
	//删除行
	fhadminUploadUtil.delFjTr = function(zhubiao,fileId){
		var fileObj = {};
		fileObj.fileid = fileId;
		zhubiao.remove(fileObj);//操作容器,从主表容器中删除file
		$("#"+fileId).remove();
	};
	//end 工具函数
	
	
	window.fhadminUploadUtil = fhadminUploadUtil;
	
	window.fhadminUpload = upload;
	
	window.fhadminZhubiao = Zhubiao;
	
	window.fhadminFileVo = FileVo;
	
})(jQuery);


















/**
 * DEMO
 * 以下是其他js或页面调用的方法
 */
//创建一个附件上传组件对象
//var option = {
//	browseFileId : "i_select_files", /** 文件选择的Dom Id，如果不指定，默认是i_select_files  文件选择的按钮的ID */
//	onSelect: function(files) {
//		//选择文件后的响应事件
//	},
//	onMaxSizeExceed: function(file) {
//		BootstrapDialog.alert({title: '提示信息',message: '文件[name='+file.name+'超过文件大小限制'+file.formatLimitSize });
//	},
//	onFileCountExceed : function(selected, limit) {
//		//超出文件个数限制时回调方法
//		BootstrapDialog.alert({title: '提示信息',message: '文件最多同时上传<strong>'+limit+'</strong>个文件'});
//	},
//	onExtNameMismatch: function(info) {
//		//文件的扩展名不匹配的响应事件
//		BootstrapDialog.alert({title: '提示信息',message: '扩展名不合法'});
//	},
//	onAddTask: function(file) {
//		 //添加文件到上传列表中的响应事件
//		 var file = '<tr id="' + file.id + '" class="template-upload fade in">' +
//	     '<td><span class="preview">'+file.id+'</span></td>' +
//	     '<td><p class="name">' + file.name + '</p>' +
//	     '</td>' +
//	     '<td><p class="size">' + file.formatSize + '</p>' +
//	     '</td>' +
//	     '<td><span class="glyphicon glyphicon-remove" onClick="javascript:_t.cancelOne(\'' + file.id + '\')"></span>' +
//	     '</td></tr>';
//		
//		$("#bootstrap-stream-container").append(file);
//	},
//	onUploadProgress: function(file) {
//		//上传进度
//	},
//	onStop: function() {
//		//停止上传后的回调函数
//	},
//	onCancel: function(file) {
//		//取消某个上传文件时的回调函数
//	},
//	onCancelAll: function(numbers) {
//		//取消全部上传文件时的回调函数
//	},
//	onComplete: function(file) {
//		//单个文件上传完毕的响应事件 
//		BootstrapDialog.alert({title: '提示信息',message: '['+file.name+']:上传成功!' });
//	},
//	onQueueComplete: function(msg) {
//		BootstrapDialog.alert({title: '提示信息',message: '全部文件上传成功!' });
//	},
//	onUploadError: function(status, msg) {
//		//文件上传出错的响应事件
//		BootstrapDialog.alert({title: '提示信息',message: '上传附件失败,'+msg+',状态码:'+status });
//	}
//};
//var myupload = new fhadminUpload(option);
//
//
///**
// * 保存附件
// */
//$("#baocun").click(function(){
//	/*
//	 * 伪代码    一般情况，先调用保存主表数据
//	 * suijishu表示的意思是创建的唯一名称的目录,上传时候用,建议使用主表的pkid
//	 */
//	var suijishu = Math.round(Math.random()*10000);//设置随机数
//	//设置附件表名称和目录名称  
//	myupload.stream.config.postVarsPerFile = {tableName: "T_FENBAOSHANG_FJ", mulu: suijishu.toString()};
//	//end 伪代码
//	
//	//执行上传附件
//	myupload.stream.upload();
//});

//end 以下是其他js或页面调用的方法


//	console.log(config.tokenURL);
//	console.log(config.frmUploadURL);
//	console.log(config.uploadURL);
	