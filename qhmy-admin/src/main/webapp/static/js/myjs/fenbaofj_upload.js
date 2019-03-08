/**
 * 本模块的js json对象
 */
var fenbaoshang = {};

/*
 * 删除附件行
 */
fenbaoshang.delFjTr = function(fileId){
	myupload.stream.cancelOne(fileId);
	$("#"+fileId).remove();
	
	var file = {};
	file.fileid = fileId;
	zhubiao.remove(file);//从容器中删除
};


/*
 * 这块是使用fhadminFileVo类和fhadminZhubiao类的例子
 */
var op = {};
var zhubiao = new fhadminZhubiao({
	yscDivId:'dbfujian_div',//已上传divID
	yscUl:'yscwj1',//已上传Ul Id
	fileRongqiId:'bootstrap-stream-container',
	addCallback:function(op){
		//添加后的回调函数
	},
	removeCallback:function(op){
		//删除后的回调函数
	}
});//创建一个主表对象 一个主表对象会存储多个附件对象

zhubiao.loadYscFile(null);//两种方式加载已上传附件

//var fileVo1 = new fhadminFileVo({
//	filename:'a',
//	fileid:'001'
//});//创建一个FileVo对象
//zhubiao.add(fileVo1);//加一个附件加入到主表中




/**
 * DEMO
 * 以下是其他js或页面调用的方法
 */
//创建一个附件上传组件对象
var option = {
	browseFileId : "i_select_files", /** 文件选择的Dom Id，如果不指定，默认是i_select_files  文件选择的按钮的ID */
	fileRongqiId : "bootstrap-stream-container",//扩展属性，存储文件存放的容器id  对应页面的tbody的id
	onSelect: function(files) {
		//选择文件后的响应事件
		var tempArray = new Array();
		for(var i=0;i<files.length;i++){
			tempArray[i] = new fhadminFileVo();
			tempArray[i].filename = files[i].name;
			tempArray[i].fileid = files[i].id;
			tempArray[i].filesize = files[i].size;
			zhubiao.add(tempArray[i]);//加入到主表容器中
		}
	},
	onMaxSizeExceed: function(file) {
		BootstrapDialog.alert({title: '提示信息',message: '文件[name='+file.name+'超过文件大小限制'+file.formatLimitSize });
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
		 //添加文件到上传列表中的响应事件
		 var file = '<tr id="' + file.id + '"  filename="' + file.name + '" filesize="' + file.formatSize + '" class="template-upload fade in">' +
	     '<td><span class="preview">'+file.id+'</span></td>' +
	     '<td><p class="name">' + file.name + '</p>' +
	     '</td>' +
	     '<td><p class="size">' + file.formatSize + '</p>' +
	     '</td>' +
	     '<td><span style="cursor: pointer;" class="glyphicon glyphicon-remove" onClick="javascript:fenbaoshang.delFjTr(\'' + file.id + '\')"></span>' +
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
		BootstrapDialog.alert({title: '提示信息',message: '取消某个文件,从上传列表删除,'+file.id });
	},
	onCancelAll: function(numbers) {
		//取消全部上传文件时的回调函数
	},
	onComplete: function(file) {
		//单个文件上传完毕的响应事件 
		BootstrapDialog.alert({title: '提示信息',message: '['+file.name+']:上传成功!' });
	},
	onQueueComplete: function(msg) {
		BootstrapDialog.alert({title: '提示信息',message: '全部文件上传成功!' });
	},
	onUploadError: function(status, msg) {
		//文件上传出错的响应事件
		BootstrapDialog.alert({title: '提示信息',message: '上传附件失败,'+msg+',状态码:'+status });
	}
};

var myupload = new fhadminUpload(option);

myupload.destroy();

/**
 * 保存附件
 */
$("#baocun").click(function(){
	
	/*
	 * 1、获取附件数据
	 */
	//zhubiao.files;  //这个是主表中附件数据,内容包含,已上传的,未上传的,要删除的  file对象有属性进行标识
	//end 获取附件数据
	
	
	
	//2、保存主表业务数据  保存附件表数据(后台处理,根据files参数,删除数据表中要删除的数据,删除文件,插入未上传的数据到附件表)
	//返回到这里的参数：附表主表ID
	
	
	//3、附件上传
	
	
	/*
	 * 伪代码    一般情况，先调用保存主表数据
	 * suijishu表示的意思是创建的唯一名称的目录,上传时候用,建议使用主表的pkid
	 */
	var suijishu = Math.round(Math.random()*10000);//设置随机数
	//设置附件表名称和目录名称  
	myupload.stream.config.postVarsPerFile = {tableName: "T_FENBAOSHANG_FJ", mulu: suijishu.toString()};
	//end 伪代码
	
	//执行上传附件
	myupload.stream.upload();
});

//end 以下是其他js或页面调用的方法


//	console.log(config.tokenURL);
//	console.log(config.frmUploadURL);
//	console.log(config.uploadURL);
	