/**
 * 与upload.js文件的区别是：本文件针对的是主从结构中的附件上传，从表的附件上传。
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
		
		this.trNumber = "";//
		
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
		
		
		if(this.filesize!=null && this.filesize>0){
			this.filesize = this.formatBytes(this.filesize);
		}
	};
	
	
	/**
	 * 主表类 每个主表会有多个文件类对象  基于数组
	 * yscDivId:'dbfujian_div',//已上传divID
	 * yscUl:'yscwj1',//已上传Ul Id
	 * trNumber:标识是哪个行对象的行号
	 */
	var Zhubiao = function(option){
		
		this.config = {
			trNumber:"",//行号
			yscDivId:"",
			yscUl:"",
			webzujianDiv:"zzzjDiv",//web组件DIV
			webTrDiv:"zzDiv",//webzujianDiv下面的行div
			dataTableName:"data_table",//数据table
			inputPageDiv:"zzwjtbody", //labour_add页面中需要显示附件列表的div id
			fileRongqiId:"bootstrap-stream-container",
			removeCallback:function(op){},  //删除的回调函数
			addCallback:function(op){}  //添加的回调函数
		};
		
		$.extend(this.config,option);
		
		/*
		 * 已上传的文件  已经物理上传的文件
		 */
		this.ysc_files = new Array();
		
		/*
		 * 需要上传的文件
		 */
		this.wsc_files = new Array();
		
		/*
		 * 需要删除的已上传的文件  已物理上传的
		 */
		this.del_files = new Array();
		
		/*
		 * 需要显示在回显列表中的文件
		 */
		this.show_files = new Array();
		
		/*
		 * 是否已经初始化加载数据库已上传文件记录
		 */
		this.initFlag = false;
		
		/*
		 * 存储file控件的html片段
		 */
		this.fileHtml = null;
		
		/*
		 * 关联Upload类型对象
		 */
		this.upload = null;
		
		/**
		 * 添加文件
		 */
		this.add = function(file){
			file.trNumber = this.config.trNumber;//写行号
			
			//添加文件,是要显示在回显列表中的
			this.show_files[this.show_files.length] = file;
			
			//添加文件,是需要上传的文件
			this.wsc_files[this.wsc_files.length] = file;
			
			//调用回调函数
//			this.config.addCallback(op);
		};
		
		
		/**
		 * 删除文件
		 */
		this.remove = function(file){
			file = this.getFileFromShowFiles(file);
			
//			console.log(file);
			
			//物理文件删除操作
			if(file.shifouysc == true){
				this.del_files[this.del_files.length] = file;//记录需要删除的物理文件
			}
			//从需要回显的列表中删除文件
			var show_file = this.getIndexFromShowFiles(file);
			this.show_files.splice(show_file,1);
			
			//从需要上传的列表中删除文件
			var wsc_file = this.getIndexFromWscFiles(file);
			
//			alert(wsc_file);
			
			this.wsc_files.splice(wsc_file,1);
			
//			alert(this.wsc_files.length);
		};
		
		
		this.display = function(){
			
//			console.log("以下打印[删除]列表:");
			for(var i=0;i<this.del_files.length;i++){
				//console.log("del_files:"+this.del_files[i].fileid);
			}
//			console.log("END [删除]列表.................");
			
			
//			console.log("以下打印[显示]列表:");
			for(var i=0;i<this.show_files.length;i++){
				//console.log("show_files:"+this.show_files[i].fileid);
			}
//			console.log("END [显示]列表.................");
			
//			console.log("以下打印[未上传]列表:");
			for(var i=0;i<this.wsc_files.length;i++){
				//console.log("wsc_files:"+this.wsc_files[i].fileid);
			}
//			console.log("END [未上传]列表.................");
			
//			console.log("以下打印[已上传]列表:");
			for(var i=0;i<this.ysc_files.length;i++){
				//console.log("ysc_files:"+this.ysc_files[i].fileid);
			}
//			console.log("END [已上传]列表.................");
			
			
		};
		
		/**
		 * 从show_files中查找file对象,返回该对象
		 */
		this.getFileFromShowFiles = function(file){
//			console.log(this.show_files.length);
//			console.log(file.fileid);
			
			for(var i=0;i<this.show_files.length;i++){
//				console.log("show_files:"+this.show_files[i].fileid);
				if(this.show_files[i].fileid == file.fileid){
					return this.show_files[i];
				}
			}
			return null;
		};
		
		
		/**
		 * 从show_files中查找file对象的索引位置
		 */
		this.getIndexFromShowFiles = function(file){
			for(var i=0;i<this.show_files.length;i++){
				if(this.show_files[i].fileid == file.fileid){
					return i;
				}
			}
			return -1;
		};
		
		/*
		 * 从wsc_files中查找file对象的索引位置
		 */
		this.getIndexFromWscFiles = function(file){
			for(var i=0;i<this.wsc_files.length;i++){
				if(this.wsc_files[i].fileid == file.fileid){
					return i;
				}
			}
			return -1;
		};
		
		
		/*
		 * 从ysc_files中查找file对象的索引位置
		 */
		this.getIndexFromYscFiles = function(file){
			for(var i=0;i<this.ysc_files.length;i++){
				if(this.ysc_files[i].fileid == file.fileid){
					return i;
				}
			}
			return -1;
		};
		
		
		/*
		 * 从del_files中查找file对象的索引位置
		 */
		this.getIndexFromDelFiles = function(file){
			for(var i=0;i<this.del_files.length;i++){
				if(this.del_files[i].fileid == file.fileid){
					return i;
				}
			}
			return -1;
		};
		
		
//		_zhubiao.updateUploadQue();
		
		/*
		 * 根据主表容器中的需要上传的附件，更新上传插件的序列
		 */
		this.updateUploadQue = function(){
//			console.log("updateUploadQue start");
			var files = this.upload.stream.getFiles();//得到上传列表的所有文件
//			console.log("updateUploadQue files:"+files);
			
			
//			for(var j=0;j<this.wsc_files.length;j++){
//				console.log("wsc_files:"+this.wsc_files[j].fileid);
//			}
			
			
			if(files!=null){
				for(var fileId in files){
					var isHave = false;//是否需要上传
//					console.log("updateUploadQue fileId:"+fileId);
					
					for(var j=0;j<this.wsc_files.length;j++){
						if(fileId == this.wsc_files[j].fileid){
//							console.log("updateUploadQue 有一样的,需要上传的"+fileId);
							isHave = true;
							break;
						}
					}
					
					if(!isHave){
//						console.log("updateUploadQue 需要删除的 start:"+fileId);
						//如果没有，从插件序列中删除
						this.upload.stream.cancelOne(fileId);
//						console.log("updateUploadQue 需要删除的 ok"+fileId);
					}
				}
			}
		};
		
		
		/**
		 * 检查是否有需要上传的文件
		 */
//		this.isHaveUploadFile = function(){
//			var isHave = false;
//			for(var i=0;i<this.files.length;i++){
//				if(this.files[i].shifouxs == true && this.files[i].shifouysc==false){
//					isHave = true;
//					break;
//				}
//			}
//			return isHave;
//		};
		
		
		/**
		 * 加载已上传文件,将已上传文件加载到Zhubiao容器中,并且动态生成行代码
		 */
		this.loadYscFile = function(files){
			
			if(this.initFlag == true){
//				console.log("loadYscFile已经初始化了一次,不用再次执行");
				return;
			}
			var tempArray = new Array();
			var self = this;
			this.initFlag = true;//已经初始化了回显列表
			
			
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
					
					tempArray[i] = new fhadminFileVoZc();
					tempArray[i].shifouysc = true;//是否已上传到服务器
					tempArray[i].filename = filename;
					tempArray[i].fileid = fileid;
					tempArray[i].filesize = filesize;
					tempArray[i].trNumber = self.config.trNumber;//写行号
					tempArray[i].data = dataJson;//业务数据
					
					//fhadminUploadUtilZc.addTr(self,self.config.fileRongqiId,tempArray[i]);//插入行
					
					self.ysc_files[self.ysc_files.length] = tempArray[i];//已上传的
					self.show_files[self.show_files.length] = tempArray[i];//回显区域的
				});
			}else{
				//通过files加载
				if(files instanceof Array){
					//如果是数组
					for(var i=0;i<files.length;i++){
						tempArray[i] = new fhadminFileVoZc();
						tempArray[i].filename = files[i].filename;
						tempArray[i].fileid = files[i].fileid;
						tempArray[i].filesize = files[i].filesize;
						tempArray[i].shifouysc = true;//是否已上传到服务器 
						tempArray[i].trNumber = this.config.trNumber;//写行号
						
						//暂时缺少处理File.data数据的加载，此处逻辑暂时不用
						
						//fhadminUploadUtilZc.addTr(this,this.config.fileRongqiId,tempArray[i]);//插入行
						
						//动态生成行数据
						this.ysc_files[this.ysc_files.length] = tempArray[i];//直接添加，不调用回调方法
						this.show_files[this.show_files.length] = tempArray[i];//直接添加，不调用回调方法
					}
				}else{
					//不是数组
					
				}
			}
		};
		//end 加载已上传文件
		
		
		/**
		 * 回显数据生成行
		 */
		this.createTrHtml = function(){
			for(var i=0;i<this.show_files.length;i++){
				fhadminUploadUtilZc.addTr(this,this.config.inputPageDiv,this.show_files[i]);//插入行
			}
		};
		
		
		/**
		 * 删除附件行
		 */
		this.delFjTr = function(fileid){
			var tempfile = {};
			tempfile.fileid = fileid;
			this.remove(tempfile);//删除file对象
			$("#"+fileid).remove();//删除dom
		};
		
		
		/**
		 * 创建upload对象，由主表对象进行创建  一个主表对象对应一个组件
		 */
		this.createUploadObj = function(){
			   var self = this;
			   //创建一个附件上传组件对象
			   var option = {
					browseFileId : "i_select_files_"+self.config.trNumber, /** 文件选择的Dom Id，如果不指定，默认是i_select_files  文件选择的按钮的ID */
					fileRongqiId : "bootstrap-stream-container_"+self.config.trNumber,//扩展属性，存储文件存放的容器id  对应页面的tbody的id
					onSelect: function(files) {
						//选择文件后的响应事件
						var tempArray = new Array();
						
						for(var i=0;i<files.length;i++){
							tempArray[i] = new fhadminFileVoZc();
							tempArray[i].filename = files[i].name;
							tempArray[i].fileid = files[i].id;
							tempArray[i].filesize = files[i].size;
							self.add(tempArray[i]);//加入到主表容器中
						}
					},
					onRepeatedFile: function(file) {
//						  console.log("文件： " + file.name
//						   + " 大小：" + file.size + "已存在于上传队列中");
						  var tempFile = {};
						  tempFile.fileid = file.id;
						  self.remove(tempFile);//加入到主表容器中
						  return false;
					},
					onMaxSizeExceed: function(file) {
						var tempFile = {};
						tempFile.fileid = file.id;
						self.remove(tempFile);//加入到主表容器中
						BootstrapDialog.show({  
					 	 	title: '提示信息',
				            message: '文件[name='+file.name+']超过文件大小限制'+file.formatLimitSize,
				            closable: true, 
				              buttons: [{
							    label: '关闭',
							    cssClass: 'keman_btn',
							    action: function(dialogRef){
				                     dialogRef.close();
				                }
						  	}
						  	]
					    });
						//BootstrapDialog.alert({title: '提示信息',message: '文件[name='+file.name+']超过文件大小限制'+file.formatLimitSize });
					},
					onFileCountExceed : function(selected, limit) {
						//超出文件个数限制时回调方法
						BootstrapDialog.alert({title: '提示信息',message: '文件最多同时上传<strong>'+limit+'</strong>个文件'});
					},
					onExtNameMismatch: function(info) {
						//文件的扩展名不匹配的响应事件
						BootstrapDialog.alert({title: '提示信息',message: '扩展名不合法'});
					},
					onAddTask: function(file) {
						
						 var filename = "";
						 if(file.name.length>=18){
							 filename = file.name.substring(0,18)+"...";
						 }else{
							 filename = file.name;
						 }
						
						 //添加文件到上传列表中的响应事件
						 var filestr = '<tr id="' + file.id + '"  filename="' + file.name + '" filesize="' + file.formatSize + '" class="template-upload fade in">' +
					     '<td style="display: none;"><span class="preview">'+file.id+'</span></td>' +
					     '<td><p class="name">' + filename + '</p>' +
					     '</td>' +
					     '<td><p class="size">' + file.formatSize + '</p>' +
					     '</td>' +
					     '<td><span yscspan="true" style="cursor: pointer;" class="glyphicon glyphicon-remove"></span>' +
					     '</td></tr>';
						 
						if(self.config.inputPageDiv!=null && self.config.inputPageDiv != ""){
							$("#"+self.config.inputPageDiv).append(filestr);
							//注册删除行点击事件
							//都是没有上传的文件
							
//							console.log("onAddTask code");
							
							$("#"+self.config.inputPageDiv).find("tr[id='"+file.id+"']").find("span[yscspan]").bind("click",function(){
								self.delFjTr(file.id);//标记哪些是删除的,删除dom行,不删除上传任务
							});
							
							//删除行的点击事件
							/*$("#"+fileRongqiId).find("tr[id='"+file.fileid+"']").find("span[yscspan]").bind("click",function(){
								self.delFjTr(file.fileid);
							});
							*/
						}else{
							$("#bootstrap-stream-container_"+self.config.trNumber).append(filestr);
							//删除行事件,此处没有用，暂时不写此处逻辑
							
						}
					},
					onUploadProgress: function(file) {
						//上传进度
					},
					onStop: function() {
						//停止上传后的回调函数
					},
					onCancel: function(file) {
						//取消某个上传文件时的回调函数
//						BootstrapDialog.alert({title: '提示信息',message: '取消某个文件,从上传列表删除,'+file.id });
					},
					onCancelAll: function(numbers) {
						//取消全部上传文件时的回调函数
					},
					onComplete: function(file) {
						//单个文件上传完毕的响应事件 
//						BootstrapDialog.alert({title: '提示信息',message: '['+file.name+']:上传成功!' });
					},
					onQueueComplete: function(msg) {
						//全部上传完成的回调方法
//						fenbaoshang.destroy();
						self.upload.delAllTask();
						self.upload.destroy();
//						BootstrapDialog.alert({title: '提示信息',message: '全部文件上传成功!' });
					},
					onUploadError: function(status, msg) {
						//文件上传出错的响应事件
						BootstrapDialog.alert({title: '提示信息',message: '上传附件失败,'+msg+',状态码:'+status });
					}
				};
				var tempUpLoad = new fhadminUploadZc(option);
				this.upload = tempUpLoad;
		};
		
		
		this.destroy = function(){
			this.ysc_files = new Array();
			this.wsc_files = new Array();
			this.del_files = new Array();
			this.show_files = new Array();
		};
		
		
	};
	
	
	/**
	 * 主表的容器类,里面有很多主表对象  存放的是Zhubiao类型的对象
	 */
	var ZhubiaoCollection = function(option){
		
		this.config = {
			zhuangtai:"ZC" //状态  DEL:表示删除  ZC:表示正常  默认正常状态   
		};
		
		this.zhubiaoList = new Array();//主表对象集合
		
		this.zhubiaoFbList = new Array();//主表对象副本集合
		
		$.extend(this.config,option);
		
		/**
		 * 根据行ID查找主表对象
		 */
		this.findZhubiaoByTrnumber = function(trNumber){
//			console.log("findZhubiaoByTrnumber:"+trNumber);
			var zhubiao = null;
//			console.log("findZhubiaoByTrnumber:"+this.zhubiaoList.length);
			for(var i=0;i<this.zhubiaoList.length;i++){
				
//				console.log("findZhubiaoByTrnumber for each:"+this.zhubiaoList[i].config.trNumber);
				
				if(trNumber == this.zhubiaoList[i].config.trNumber){
					zhubiao = this.zhubiaoList[i];
					break;
				}
			}
			return zhubiao;
		};
		
		/**
		 * 设置为NULL  修改了，变成全部删除
		 */
		this.setNull = function(zhubiao){
			var zhubiaofb = this.findZhubiaoFbByTrnumber(zhubiao.config.trNumber);
			//将已上传的文件删除,已上传文件拷贝到del_files中，清空其他数组  主体拷贝到副本
			zhubiao.del_files = new Array();
			if(zhubiao.ysc_files != null && zhubiao.ysc_files.length>0){
				zhubiao.del_files = zhubiao.del_files.concat(zhubiao.ysc_files);
			}
			zhubiao.ysc_files = new Array();//设置为空
			zhubiao.show_files = new Array();//设置为空
			zhubiao.wsc_files = new Array();//设置为空
			this.copyObject(zhubiao, zhubiaofb);//同步副本对象
//			var index = this.findIndexByTrnumber(zhubiao.config.trNumber);
//			this.zhubiaoList.splice(index,1);
//			this.zhubiaoFbList.splice(index,1);
		};
		
		/**
		 * 设置为nULL，但是保留已删除的
		 */
		this.setNullBlDelList = function(zhubiao){
//			var zhubiaofb = this.findZhubiaoFbByTrnumber(zhubiao.config.trNumber);
			zhubiao.del_files = new Array();
			if(zhubiao.ysc_files != null && zhubiao.ysc_files.length>0){
				zhubiao.del_files = zhubiao.del_files.concat(zhubiao.ysc_files);
			}
			zhubiao.ysc_files = new Array();//设置为空
			zhubiao.show_files = new Array();//设置为空
			zhubiao.wsc_files = new Array();//设置为空
//			this.copyObject(zhubiao, zhubiaofb);//同步副本对象
		};
		
		/**
		 * 根据行ID查找主表对象的副本对象
		 */
		this.findZhubiaoFbByTrnumber = function(trNumber){
			var zhubiao = null;
			for(var i=0;i<this.zhubiaoFbList.length;i++){
				if(trNumber == this.zhubiaoFbList[i].config.trNumber){
					zhubiao = this.zhubiaoFbList[i];
					break;
				}
			}
			return zhubiao;
		};
		
		/**
		 * 根据行ID返回下标   此方法可能没有用,主体和副本对象可能大小不一样
		 */
		this.findIndexByTrnumber = function(trNumber){
			var index = -1;
			for(var i=0;i<this.zhubiaoList.length;i++){
				if(trNumber == this.zhubiaoList[i].config.trNumber){
					index = i;
					break;
				}
			}
			return index;
		};
		
		/**
		 * 添加副本主表对象
		 */
		this.addFb = function(zhubiao){
			this.zhubiaoFbList[this.zhubiaoFbList.length] = zhubiao;
		};
		
		
		/**
		 * 添加本体主表对象
		 */
		this.addBt = function(zhubiao){
			this.zhubiaoList[this.zhubiaoList.length] = zhubiao;
		};
		
		/**
		 * 主体和副本各添加一个
		 */
		this.add = function(zhubiao){
			this.zhubiaoList[this.zhubiaoList.length] = zhubiao;
			var fbObj = this.copyObject(zhubiao,null);//将主对象复制一个副本对象
			this.zhubiaoFbList[this.zhubiaoFbList.length] = fbObj;
		};
		
		/**
		 * 删除行
		 */
		this.remove = function(){
			this.config.zhuangtai = "DEL";
		};
		
		
		/**
		 * 创建副本对象
		 */
		this.createFb = function(){
			
		};
		
		
		/**
		 * 打印主体和副本对象
		 */
		this.display = function(index){
			var fb = this.findZhubiaoFbByTrnumber(index);
			var zt = this.findZhubiaoByTrnumber(index);
//			console.log("--------以下是主体对象-----------");
			zt.display();
			
//			console.log("\n");
//			console.log("\r");
			
//			console.log("--------以下是副本对象-----------");
			fb.display();
		};
		
		
		/**
		 * 对象深拷贝,将obj1拷贝到obj2
		 * obj1和obj2是zhubiao类型的对象
		 * fx:方向  zTOf:表示主体拷贝到副本   fTOz:表示从副本拷贝到主体
		 */
		this.copy = function(trNumber,fx){
			var rst = true;
			var zt = this.findZhubiaoByTrnumber(trNumber);//主体对象
			var fb = this.findZhubiaoFbByTrnumber(trNumber);//副本对象
			
			if(fx == "zTOf"){
				//主体拷贝到副本
				this.copyObject(zt,fb);
			}else if(fx == "fTOz"){
				//副本拷贝到主体
				this.copyObject(fb,zt);
			}else{
				rst = false;
			}
			return rst;
		};
		
		/**
		 * 将源对象深拷贝到一个副本对象
		 */
		this.copyObject = function(objsrc,objtarget){
			if(objsrc == null){
//				console.log("copyObject error:objsrc对象为null");
				return;
			}
			
			if(objtarget == null){
				objtarget = new fhadminZhubiaoZc(objsrc.config);
			}
			
			
			objtarget.initFlag = objsrc.initFlag;
			objtarget.ysc_files = new Array();
			objtarget.wsc_files = new Array();
			objtarget.del_files = new Array();
			objtarget.show_files = new Array();
			objtarget.fileHtml = objsrc.fileHtml;
			objtarget.upload = objsrc.upload;
			objtarget.data = objsrc.data;//复制业务数据
			
			for(var i=0;i<objsrc.ysc_files.length;i++){
				var fileSrc = objsrc.ysc_files[i];//源对象  File
				objtarget.ysc_files[i] = new fhadminFileVoZc(fileSrc.config);//目标对象
				
				objtarget.ysc_files[i].filename = fileSrc.filename;
				objtarget.ysc_files[i].fileid = fileSrc.fileid;
				objtarget.ysc_files[i].filesize = fileSrc.filesize;
				objtarget.ysc_files[i].shifouxs = fileSrc.shifouxs;
				objtarget.ysc_files[i].delFlag = fileSrc.delFlag;
				objtarget.ysc_files[i].shifouysc = fileSrc.shifouysc;
				objtarget.ysc_files[i].trNumber = fileSrc.trNumber;
			}
			
			for(var i=0;i<objsrc.wsc_files.length;i++){
				var fileSrc = objsrc.wsc_files[i];//源对象  File
				objtarget.wsc_files[i] = new fhadminFileVoZc(fileSrc.config);//目标对象
				objtarget.wsc_files[i].filename = fileSrc.filename;
				objtarget.wsc_files[i].fileid = fileSrc.fileid;
				objtarget.wsc_files[i].filesize = fileSrc.filesize;
				objtarget.wsc_files[i].shifouxs = fileSrc.shifouxs;
				objtarget.wsc_files[i].delFlag = fileSrc.delFlag;
				objtarget.wsc_files[i].shifouysc = fileSrc.shifouysc;
				objtarget.wsc_files[i].trNumber = fileSrc.trNumber;
			}
			
			for(var i=0;i<objsrc.del_files.length;i++){
				var fileSrc = objsrc.del_files[i];//源对象  File
				objtarget.del_files[i] = new fhadminFileVoZc(fileSrc.config);//目标对象
				objtarget.del_files[i].filename = fileSrc.filename;
				objtarget.del_files[i].fileid = fileSrc.fileid;
				objtarget.del_files[i].filesize = fileSrc.filesize;
				objtarget.del_files[i].shifouxs = fileSrc.shifouxs;
				objtarget.del_files[i].delFlag = fileSrc.delFlag;
				objtarget.del_files[i].shifouysc = fileSrc.shifouysc;
				objtarget.del_files[i].trNumber = fileSrc.trNumber;
			}
			
			for(var i=0;i<objsrc.show_files.length;i++){
				var fileSrc = objsrc.show_files[i];//源对象  File
				objtarget.show_files[i] = new fhadminFileVoZc(fileSrc.config);//目标对象
				objtarget.show_files[i].filename = fileSrc.filename;
				objtarget.show_files[i].fileid = fileSrc.fileid;
				objtarget.show_files[i].filesize = fileSrc.filesize;
				objtarget.show_files[i].shifouxs = fileSrc.shifouxs;
				objtarget.show_files[i].delFlag = fileSrc.delFlag;
				objtarget.show_files[i].shifouysc = fileSrc.shifouysc;
				objtarget.show_files[i].trNumber = fileSrc.trNumber;
			}
			
			if(objtarget == null){
				alert("异常:uploadZcjg中的:objtarget为NULL");
			}
			
			if(objsrc == null){
				alert("异常:uploadZcjg中的:objsrc为NULL");
			}
			
			if(objtarget.config == null){
				alert("异常:uploadZcjg中的:objtarget config 为NULL");
			}
			
			if(objsrc.config == null){
				alert("异常:uploadZcjg中的:objsrc config 为NULL");
			}
			
			return objtarget;
		};
		
		/*
		 * 销毁
		 */
		this.destroy = function(){
			if(this.zhubiaoList!=null && this.zhubiaoList.length>0){
				for(var i=0;i<this.zhubiaoList.length;i++){
					var tempZubiao = this.zhubiaoList[i];
					if(tempZubiao.upload!=null){
						//删除资质行时有可能为空,因为创建了zhubiao对象，但是没有创建upload对象
						tempZubiao.upload.delAllTask();
						tempZubiao.upload.destroy();
					}
				}
				this.zhubiaoList = new Array();
			}
			this.zhubiaoFbList = new Array();
		};
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
	 * fhadminUploadUtilZc  工具函数
	 */
	var fhadminUploadUtilZc = {};
	
	//新增行
	fhadminUploadUtilZc.addTr = function(zhubiao,fileRongqiId,file){
		//需要生成下载链接,下载链接，需要一个URL 参数需要主表的pkid+clientid
		var filestr = '';
		
		var filename = "";
		 if(file.filename.length>=18){
			 filename = file.filename.substring(0,18)+"...";
		 }else{
			 filename = file.filename;
		 }
		
		//是否已上传
		if(file.shifouysc == true){
			//已上传
			 filestr = '<tr id="'+ file.fileid +'"  filename="' + file.filename + '" filesize="' + file.filesize + '" class="template-upload fade in">' +
		     '<td style="display: none;"><span class="preview">' + file.fileid + '</span></td>' +
		     '<td><p class="name">' + filename + '</p>' +
		     '</td>' +
		     '<td><p class="size">' + file.formatBytes(file.filesize) + '</p>' +
		     '</td>' +
		     '<td><a class="xiazai" href=javascript:void(0);>下载</a><span yscspan="true" style="cursor: pointer;" class="glyphicon glyphicon-remove yscspan" ></span>' +
		     '</td></tr>';
		}else{
			//未上传
			filestr = '<tr id="'+ file.fileid +'"  filename="' + file.filename + '" filesize="' + file.filesize + '" class="template-upload fade in">' +
		     '<td style="display: none;"><span class="preview">' + file.fileid + '</span></td>' +
		     '<td><p class="name">' + filename + '</p>' +
		     '</td>' +
		     '<td><p class="size">' + file.formatBytes(file.filesize) + '</p>' +
		     '</td>' +
		     '<td><span yscspan="true" style="cursor: pointer;" class="glyphicon glyphicon-remove yscspan" ></span>' +
		     '</td></tr>';
		}
		
		var $tr = $(filestr);
		$("#"+fileRongqiId).append(filestr);
		
		//删除行的点击事件
		$("#"+fileRongqiId).find("tr[id='"+file.fileid+"']").find("span[yscspan]").bind("click",function(){
//			alert("sdfsdf");
			fhadminUploadUtilZc.delFjTr(zhubiao,file.fileid);
		});
		
		
		//下载
		$("#"+fileRongqiId).find("tr[id='"+file.fileid+"']").find("a").bind("click",function(){
			var dataJson = file.data;
			var tableName = zhubiao.upload.config.postVarsPerFile.tableName;
			//注册下载事件
			var url = _basepath+"file/download.json?r="+Math.random()+"&fileid="+file.fileid;//下载文件地址
			var params = "";
			for(var sx in dataJson){
				params = params + "&" + sx + "="+dataJson[sx] + "";
			}
//			console.log(params);
//			console.log(url+params);
			window.location.href=url+params;
		});
		
	};
	
	//删除行
	fhadminUploadUtilZc.delFjTr = function(zhubiao,fileId){
		var fileObj = {};
		fileObj.fileid = fileId;
		zhubiao.remove(fileObj);//操作容器,从主表容器中删除file
		$("#"+fileId).remove();
	};
	
//	<div class="zzzjDiv" style="display: none;">
//    <div id="zzDiv_1" >
//    	<!-- 上传按钮 -->
//      <div class="form-group">
//			<div class="btn-group">
//				<button style="width: 100px;" type="button" class="btn btn-default" id="i_select_files_1">
//					<span class="glyphicon glyphicon-plus-sign"></span>添加文件
//				</button>
//			</div>
//		</div>
//		<!-- end 上传按钮 -->
//                         
//        <p class="help-block">可上传最大为 5.0 MB 文件。如果文件比较大请先压缩文件。</p>
//        
//        <!-- 选择的文件列表 -->
//        <div style="width: 30%">
//			<table id="data_table_1" style="" class="table tablesorter">
//				<thead>
//					<tr><th>编号</th>
//						<th>文件</th>
//						<th>大小</th>
//						<th>操作</th>
//					</tr>
//				</thead>
//				<tbody id="bootstrap-stream-container_1">
//				</tbody>
//			</table>
//		</div>
//		<!-- 选择的文件列表 -->
//	</div>
//</div>
	
	//生成组件静态代码
	/*
	 * index,生成的索引号
	 * divClass,从哪个样式class下面生成   zzzjDiv
	 */
	fhadminUploadUtilZc.createUploadStaticHtml = function(index,divClass){
		var _index = index*1;
		var divId = "zzDiv"+_index;
		var staticHTML = "<div id=\""+divId+"\" ></div>";
		$(staticHTML).appendTo($("."+divClass));
		
		var i_select_filesName = "i_select_files_"+_index;
		var data_table_name = "data_table_"+_index;
		var data_tbody_name = "bootstrap-stream-container_"+_index;
		
		var buttonStaticHTML = '<div style="width:100px !important; height:38px !important; border-radius:6px;padding: 6px 12px;" type="button" class="btn"  id="'+i_select_filesName+'"><span class="glyphicon glyphicon-open"></span>本地上传</div>';
		
		
		var staticHTML = '<div class="form-group">											   '+
		'		<div class="btn-group">										   '+
		buttonStaticHTML + 
//		'			<button style="width: 100px;" type="button" class="btn btn-default" id="'+i_select_filesName+'"> '+
//		'				<span class="glyphicon glyphicon-plus-sign"></span>添加文件			   '+
//		'			</button>										   '+
		'		</div>												   '+
		'</div>       													   '+
		'<p class="help-block">可上传最大为 5.0 MB 文件。如果文件比较大请先压缩文件。</p>				   '+
		'<div style="width: 30%">											   '+
		'	<table id="'+data_table_name+'" style="" class="table tablesorter">						   '+
		'		<thead>												   '+
		'			<tr><th style="display: none;">编号</th>									   '+
		'				<th>文件</th>									   '+
		'				<th>大小</th>									   '+
		'				<th>操作</th>									   '+
		'			</tr>											   '+
		'		</thead>											   '+
		'		<tbody id="'+data_tbody_name+'">							   '+
		'		</tbody>											   '+
		'	</table>												   '+
		'</div>														   ';
		
		$("#"+divId).append(staticHTML);
	};
	
	
	//end 工具函数
	
	window.fhadminZhubiaoList = ZhubiaoCollection;
	
	window.fhadminUploadUtilZc = fhadminUploadUtilZc;
	
	window.fhadminUploadZc = upload;
	
	window.fhadminZhubiaoZc = Zhubiao;
	
	window.fhadminFileVoZc = FileVo;
	
})(jQuery);
	