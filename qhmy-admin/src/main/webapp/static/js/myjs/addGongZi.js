/**
 * 本模块的js json对象
 */
var gongzi = {};

/*
 * 上传组件主表对象
 */
var op = {};
var zhubiao = null;
var myupload = null;
var myupload2 = null;
//end 上传组件主表对象

gongzi.pkidList = null;

//上传完成后的业务处理
gongzi.biz = function() {
	$("#nameli").load(_basepath + "gongzifafang/list.php");
	$("#addPage").empty();
	$("#addPage").hide();
};
/*
 * 删除附件行
 */
gongzi.delFjTr = function(fileId) {
	myupload.stream.cancelOne(fileId);
	$("#" + fileId).remove();

	var file = {};
	file.fileid = fileId;
	zhubiao.remove(file);//从容器中删除
};

gongzi.destroy = function() {
	//取消未上传列表,由于只是隐藏，因此要取消
	//取消上传任务
	myupload.delAllTask();
	myupload.chearTableTr();
	myupload.destroy();//销毁上传对象
	zhubiao = null;//主表对象为空
	//清空附件行 end 取消未上传列表,由于只是隐藏，因此要取消
};

/*
 * 初始化上传组件
 */
gongzi.initUpload = function(fileObjs) {

	/*
	 * 这块是使用fhadminFileVo类和fhadminZhubiao类的例子
	 */
	zhubiao = new fhadminZhubiao({
		yscDivId : 'dbfujian_div',//已上传divID
		yscUl : 'yscwj1',//已上传Ul Id
		fileRongqiId : 'bootstrap-stream-container',
		addCallback : function(op) {
			//添加后的回调函数
		},
		removeCallback : function(op) {
			//删除后的回调函数
		}
	});//创建一个主表对象 一个主表对象会存储多个附件对象

	//zhubiao.loadYscFile(null);//两种方式加载已上传附件
	zhubiao.loadYscFile(fileObjs);//两种方式加载已上传附件

	//创建一个附件上传组件对象
	var option = {
		browseFileId : "i_select_files",
		/** 文件选择的Dom Id，如果不指定，默认是i_select_files  文件选择的按钮的ID */
		fileRongqiId : "bootstrap-stream-container",//扩展属性，存储文件存放的容器id  对应页面的tbody的id
		onSelect : function(files) {
			//选择文件后的响应事件
			var tempArray = new Array();
			for (var i = 0; i < files.length; i++) {
				tempArray[i] = new fhadminFileVo();
				tempArray[i].filename = files[i].name;
				tempArray[i].fileid = files[i].id;
				tempArray[i].filesize = files[i].size;
				zhubiao.add(tempArray[i]);//加入到主表容器中
			}
		},
		onRepeatedFile: function(file) {
			  //重复文件
			  BootstrapDialog.show({  
			 	 	title: '提示信息',
		            message: "文件： " + file.name + "已存在于上传队列中",
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
//			  var tempFile = {};
//			  tempFile.fileid = file.id;
//			  gongzi.delFjTr(file.id);
//			  zhubiao.remove(tempFile);
			  return false;
		},
		onMaxSizeExceed : function(file) {
			var tempFile = {};
			tempFile.fileid = file.id;
			zhubiao.remove(tempFile);
			
			BootstrapDialog.show({
				title : '提示信息',
				message : '文件[name=' + file.name + ']超过文件大小限制'
						+ file.formatLimitSize,
				closable : true,
				buttons : [ {
					label : '关闭',
					cssClass : 'keman_btn',
					action : function(dialogRef) {
						dialogRef.close();
					}
				} ]
			});
		},
		onFileCountExceed : function(selected, limit) {
			//超出文件个数限制时回调方法
			BootstrapDialog.show({
				title : '提示信息',
				message : '文件最多同时上传<strong>' + limit + '</strong>个文件',
				closable : true,
				buttons : [ {
					label : '关闭',
					cssClass : 'keman_btn',
					action : function(dialogRef) {
						dialogRef.close();
					}
				} ]
			});
		},
		onExtNameMismatch : function(info) {
			//文件的扩展名不匹配的响应事件
			BootstrapDialog.show({
				title : '提示信息',
				message : '扩展名不合法',
				closable : true,
				buttons : [ {
					label : '关闭',
					cssClass : 'keman_btn',
					action : function(dialogRef) {
						dialogRef.close();
					}
				} ]
			});
		},
		onAddTask : function(file) {
			var filename = "";
			if (file.name.length >= 18) {
				filename = file.name.substring(0, 18) + "...";
			} else {
				filename = file.name;
			}
			//添加文件到上传列表中的响应事件
			var file = '<tr  id="'
					+ file.id
					+ '"  filename="'
					+ file.name
					+ '" filesize="'
					+ file.formatSize
					+ '" class="template-upload fade in">'
					+ '<td style="display: none;" ><span class="preview">'
					+ file.id
					+ '</span></td>'
					+ '<td><p class="name">'
					+ filename
					+ '</p>'
					+ '</td>'
					+ '<td><p class="size">'
					+ file.formatSize
					+ '</p>'
					+ '</td>'
					+ '<td><span style="cursor: pointer;" class="glyphicon glyphicon-remove" onClick="javascript:gongzi.delFjTr(\''
					+ file.id + '\')"></span>' + '</td></tr>';
			$("#bootstrap-stream-container").append(file);
		},
		onUploadProgress : function(file) {
			//上传进度
		},
		onStop : function() {
			//停止上传后的回调函数
		},
		onCancel : function(file) {
			//取消某个上传文件时的回调函数
			//			BootstrapDialog.alert({title: '提示信息',message: '取消某个文件,从上传列表删除,'+file.id });
		},
		onCancelAll : function(numbers) {
			//取消全部上传文件时的回调函数
		},
		onComplete : function(file) {
			//单个文件上传完毕的响应事件 
			//			BootstrapDialog.alert({title: '提示信息',message: '['+file.name+']:上传成功!' });
		},
		onQueueComplete : function(msg) {
			//全部上传完成的回调方法
			gongzi.biz();
			gongzi.destroy();
			//BootstrapDialog.alert({title: '提示信息',message: '全部文件上传成功!' });
		},
		onUploadError : function(status, msg) {
			//文件上传出错的响应事件
			BootstrapDialog.show({
				title : '提示信息',
				message : '上传附件失败,' + msg + ',状态码:' + status,
				closable : true,
				buttons : [ {
					label : '关闭',
					cssClass : 'keman_btn',
					action : function(dialogRef) {
						dialogRef.close();
					}
				} ]
			});
		}
	};

	myupload = new fhadminUpload(option);
	//	
	//	myupload2 = new fhadminUpload(option);
};
