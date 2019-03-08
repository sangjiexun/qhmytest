
/**
 * 批量减免
 * @param win
 * @param $
 */
(function(win,$,layer){
	
	var page = {};
	
	
	/**
	 * 解析excel
	 */
	page.parse = function(file){
		var index = layer.load(1, {shade: [0.1,'#fff']});
		var tableName = "ITME_LIST_JIANMIAN";//路径
		var fileName = file.name;//文件名
		
		var item_pkid = $("#item_pkid").val();//选择的缴费项目
		
		var datas = new Array();
		datas.push({"name":"tableName","value":tableName});
		datas.push({"name":"fileName","value":fileName});
		datas.push({"name":"mulu","value":_uuid});
		
		datas.push({"name":"item_pkid","value":item_pkid});
		
		
		$.ajax({
			type : "post",
			dataType : "json",
			data : datas,
			url : _basepath + 'import/import-jianmian-parse.json',
			success : function(data) {
				if (true == data.result) {
					$("#totalCount").html('总记录数:'+data.totalCount+"， ");
					$("#successCount").html('成功导入记录数:'+data.successCount+"， ");
					$("#failCount").html('失败记录数:'+data.failCount+"， ");
					if(data.failCount>0){
						$("#mulu").show();
						$("#mulu").attr("href",_basepath + "uploadFiles\\jianmian\\"+data.mulu);
					}else{
						$("#mulu").hide();
					}
				}
				layer.close(index);
			},
			error : function(XMLHttpRequest,textStatus,errorThrown) {
				alert(errorThrown);
				layer.close(index);
			}
		});
		
		
	};
	
	
	/**
	 * 上传组件配置  config
	 */
	page.uploadConfig = function(){
		
		var def_config = {
				multipleFiles: false, /** 是否允许同时选择多个文件，默认是false */	
				autoUploading: true, /** 当选择完文件是否自动上传，默认是true */
				fileFieldName: "FileData", /** 相当于指定<input type="file" name="FileData">，默认是FileData */
				maxSize: 52428800, /**  当_t.bStreaming = false 时（也就是Flash上传时），2G就是最大的文件上传大小！所以一般需要  2147483648 =2G  5242880 = 5MB */
				simLimit: 100000, /** 允许同时选择文件上传的个数（包含已经上传过的） */
				extFilters: [".xls", ".xlsx"], /** 默认是全部允许，即 [] */
				browseFileId : "i_select_files", /** 文件选择的Dom Id，如果不指定，默认是i_select_files  文件选择的按钮的ID */
				browseFileBtn : "<div>选择文件</div>", /** 选择文件的按钮内容，非自定义UI有效(customered:false) */
//				dragAndDropArea: "i_stream_dropzone", /** 拖拽上传的容器ID   */
				filesQueueId : "i_stream_files_queue", /** 文件上传进度显示框ID，非自定义UI有效(customered:false) */
				filesQueueHeight : 150, /** 文件上传进度显示框的高，非自定义UI有效(customered:false)，默认450px */
//				messagerId : "i_stream_message_container", /** 消息框的Id，当没有自定义onXXX函数，系统会显示onXXX的部分提示信息，如果没有i_stream_message_container则不显示 */
				tokenURL : _basepath+"file/token.json",
				swfURL: _basepath+"static/swf/FlashUploader.swf" ,
				frmUploadURL : _basepath+"file/fd.json", /** Flash上传的URI */
				postVarsPerFile:{tableName: "ITME_LIST_JIANMIAN", mulu: _uuid}, /** 上传文件时的其他参数  */
				uploadURL : _basepath+"file/upload.json",
//				fileRongqiId:"bootstrap-stream-container",
				onSelect: function(files) {
					//选择文件后的响应事件
//					layer.msg('文件被选择');
				},
				onRepeatedFile: function(file) {
					  //重复文件
					  layer.msg('文件[名称='+file.name+',重复上传!');
					  return false;
				},
				onMaxSizeExceed: function(file) {
					//超出文件大小时的提示
					layer.msg('文件[名称='+file.name+',超过文件大小限制'+file.formatLimitSize);
				},
				onFileCountExceed : function(selected, limit) {
					//超出文件个数限制时回调方法
					layer.msg('文件最多同时上传<strong>'+limit+'</strong>个文件');
				},
				onExtNameMismatch: function(info) {
					//文件的扩展名不匹配的响应事件
					layer.msg('扩展名不合法,请上传Excel文件');
				},
				onAddTask: function(file) {
					 //添加文件到上传列表中的响应事件
				},
				onUploadProgress: function(file) {
					//上传进度
				},
				onStop: function() {
					//停止上传后的回调函数
				},
				onCancel: function(file) {
					//取消某个上传文件时的回调函数
//					BootstrapDialog.alert({title: '提示信息',message: '取消某个文件,从上传列表删除,'+file.id });
				},
				onCancelAll: function(numbers) {
					//取消全部上传文件时的回调函数
				},
				onComplete: function(file) {
					//单个文件上传完毕的响应事件 
					layer.msg('['+file.name+']:上传成功,开始分析缴费名单,请稍等...');
					page.parse(file);
				},
				onQueueComplete: function(msg) {
					//所有文件上传完毕的响应事件
//					layer.msg('所有文件上传完成');
				},
				onUploadError: function(status, msg) {
					//文件上传出错的响应事件
					layer.msg('上传附件失败,'+msg+',状态码:'+status);
				},
				onDestroy: function(){
					//组件销毁事件
//					layer.msg('上传组件已销毁');
				}
		};
		return def_config;
	};
	
	/**
	 * 上传组件初始化
	 */
	page.uploadInit = function(){
		var config = page.uploadConfig();
		var upload = new Stream(config);
		return upload;
	};
	
	/**
	 * 初始化
	 */
	page.init = function(){
		$.ajaxSetup({
		    aysnc: false // 默认同步加载
		});
		page.uploadInit();
	};
	
	page.init();
	
	$("#downloadtemplate").bind('click',function(){
		var url=_basepath+"uploadFiles/template/jianmianlist.xlsx";
		window.open(url);
	});
	
})(window,jQuery,layer);


