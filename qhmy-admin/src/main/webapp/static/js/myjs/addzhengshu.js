/**
 * 添加证书 js
 * @param $
 * @param window
 */

(function($,window){

	
	
})(jQuery,window);



var addzhengshu = {};

$("input").placeholder();
var $form = $('#zhengshuFrom').bootstrapValidator({
	message : '该值无效',
	fields: {
		jinengdj: {
            validators: {
                notEmpty: {
                    message: '技能等级不能为空'
                }
            }
        },
        
        zhengshubh: {
            validators: {
                notEmpty: {
                    message: '证书编号不能为空'
                }
            }
        },
        
        fazhengjg: {
            validators: {
                notEmpty: {
                    message: '发证机关不能为空'
                }
            }
        },
        
        fazhengrq: {
        	message : '发证日期无效',
        	validators: {
                notEmpty: {
                    message: '发证日期不能为空'
                }
//                date: {
//                	message: '发证日期格式不正确',
//                    format: 'yyyy-MM-dd'
//                }
            }
        },
        youxiaojzrq:{
        	validators: {
        		 callback:{
            			message:'有效截止日期必须大于发证日期',
            			callback: function(value, validator) { 
	            			var fazhengrq=$("#fazhengrq").val();  
            			    var start=new Date(fazhengrq.replace("-", "/").replace("-", "/"));  
            			    var youxiaojzrq=$("#youxiaojzrq").val();
            			    var end=new Date(youxiaojzrq.replace("-", "/").replace("-", "/"));  
            			    if(end<start){  
            			        return false;  
            			    }  
            			    return true;
            			} 
            		}
            }
        }
	}
});


var messages = "";

/**
 * 验证错误时
 */
$form.on('error.field.bv', function(e, data) {
    // $(e.target)  --> The form instance
    // $(e.target).data('bootstrapValidator')
    //              --> The BootstrapValidator instance

    // data.field   --> The field name
    // data.element --> The new field element
    // data.options --> The new field options
	
//	console.log("data.field"+data.field);
//	console.log("data.element"+data.element);
//	console.log("data.options"+data.options);
	
	if(messages==''){
		messages = data.bv.getMessages(data.element);
	}
	
//	console.log("data.messages:"+messages);
	
	boolen = false;
//	data.element.focus();
	data.element.data('bv.messages').find('.help-block[data-bv-for="' + data.field + '"]').hide();
});


/*
 * 上传组件主表对象
 */
var myupload2 = null;
var zhubiao2 = null;
//end 上传组件主表对象

/*
 * 删除附件行
 */
addzhengshu.delFjTr = function(fileId){
	myupload2.stream.cancelOne(fileId);
	$("#"+fileId).remove();
	
	var file = {};
	file.fileid = fileId;
	zhubiao2.remove(file);//从容器中删除
};

addzhengshu.destroy = function(){
	//取消未上传列表,由于只是隐藏，因此要取消
		//取消上传任务
  	myupload2.delAllTask();
  	myupload2.chearTableTr();
  	myupload2.destroy();//销毁上传对象
  	zhubiao2 = null;//主表对象为空
  	//dialog.close();
  	//清空附件行 end 取消未上传列表,由于只是隐藏，因此要取消
};


addzhengshu.tipsshow = function(word){
	$(".message-hint").html(word);
	$(".message-hint-wrap").show();
	setTimeout('$(".message-hint-wrap").hide();',1500);
	return false;
};


/**
 * 验证
 */
addzhengshu.yanzheng = function(){
	messages = "";
	
	//手动执行验证
	$form.data('bootstrapValidator').validate();
	
	var rst = $form.data('bootstrapValidator').isValid();
	if(!rst){
		
		addzhengshu.tipsshow(messages);
//		BootstrapDialog.show({  
//			title:'提示信息',
//            message:messages,
//            draggable: true,
//            closable: true,
//            size :BootstrapDialog.SIZE_NORMAL,
//            buttons: [{
//		    label: '关闭',
//		    cssClass: 'keman_btn',
//		    action: function(dialogRef){
//		       dialogRef.close();
//		    }
//		  }
//		  ]
//        });
		$form.data('bootstrapValidator').resetForm();
	}
	return rst;//返回验证结果
};



/*
 * 初始化上传组件
 */
addzhengshu.initUpload = function(fileObjs){
	
	/*
	 * 这块是使用fhadminFileVo类和fhadminZhubiao类的例子
	 */
	zhubiao2 = new fhadminZhubiao({
		yscDivId:'dbfujian_div',//已上传divID
		yscUl:'yscwj1',//已上传Ul Id
		fileRongqiId:'bootstrap-stream-container2',
		addCallback:function(op){
			//添加后的回调函数
		},
		removeCallback:function(op){
			//删除后的回调函数
		}
	});//创建一个主表对象 一个主表对象会存储多个附件对象
	
	//zhubiao2.loadYscFile(null);//两种方式加载已上传附件
	zhubiao2.loadYscFile(fileObjs);//两种方式加载已上传附件
	
	
	//创建一个附件上传组件对象
	var option = {
		multipleFiles: false, /** 是否允许同时选择多个文件，默认是false */
		autoUploading: true, /** 当选择完文件是否自动上传，默认是true */
		simLimit: 100, /** 允许同时选择文件上传的个数（包含已经上传过的） */
		browseFileId : "i_select_files2", /** 文件选择的Dom Id，如果不指定，默认是i_select_files  文件选择的按钮的ID */
		fileRongqiId : "bootstrap-stream-container2",//扩展属性，存储文件存放的容器id  对应页面的tbody的id
		extFilters: [".jpg", ".png", ".jpeg", ".gif", ".bmp"],
		onSelect: function(files) {
			//选择文件后的响应事件
			var tempArray = new Array();
			zhubiao2.files = new Array();//只保存一个
			for(var i=0;i<files.length;i++){
				tempArray[i] = new fhadminFileVo();
				tempArray[i].filename = files[i].name;
				tempArray[i].fileid = files[i].id;
				tempArray[i].filesize = files[i].size;
				zhubiao2.add(tempArray[i]);//加入到主表容器中
			}
			//获取生成pkid  pkid
			var NEWPKID = $("#NEWPKID").val();
			var ZSPKID = $("#ZSPKID").val();//证书PKID
		    var lujing = $("#lujing").val();//图片路径
		    
			if(NEWPKID!=null && NEWPKID!=""){
				//新增操作，新增产生的pkid
				//修改操作，修改保存
				myupload2.stream.config.postVarsPerFile = {tableName: "T_LAOWURENYUAN_ZS", mulu: NEWPKID};
			}else{
				//修改操作，修改保存
				myupload2.stream.config.postVarsPerFile = {tableName: "T_LAOWURENYUAN_ZS", mulu: ZSPKID};
			}
		},
		onRepeatedFile: function(file) {
			  //重复文件
//			  console.log("文件： " + file.name
//			   + " 大小：" + file.size + "已存在于上传队列中");
//			  var tempFile = {};
//			  tempFile.fileid = file.id;
//			  fenbaoshang.delFjTr(file.id);
//			  zhubiao2.remove(tempFile);
			  return false;
		},
		onMaxSizeExceed: function(file) {
			BootstrapDialog.show({  
				title:'提示信息',
	            message:'文件[name='+file.name+']超过文件大小限制'+file.formatLimitSize,
	            draggable: true,
	            closable: true,
	            size :BootstrapDialog.SIZE_NORMAL,
	            buttons: [{
			    label: '关闭',
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
	            message:'只能上传<strong>'+limit+'</strong>个文件',
	            draggable: true,
	            closable: true,
	            size :BootstrapDialog.SIZE_NORMAL,
	            buttons: [{
			    label: '关闭',
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
	            size :BootstrapDialog.SIZE_NORMAL,
	            draggable: true,
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
		},
		onAddTask: function(file) {
//			var filename = "";
//			 if(file.name.length>=10){
//				 filename = file.name.substring(0,10)+"...";
//			 }else{
//				 filename = file.name;
//			 }
//			 //添加文件到上传列表中的响应事件
//			 var file = '<tr id="' + file.id + '"  filename="' + file.name + '" filesize="' + file.formatSize + '"  _size="' + file.size + '" class="template-upload fade in">' +
//		     '<td style="display: none;"><span class="preview">'+file.id+'</span></td>' +
//		     '<td><p class="name">' + filename + '</p>' +
//		     '</td>' +
//		     '<td><p class="size">' + file.formatSize + '</p>' +
//		     '</td>' +
//		     '<td><span style="cursor: pointer;" class="glyphicon glyphicon-remove" onClick="javascript:addzhengshu.delFjTr(\'' + file.id + '\')"></span>' +
//		     '</td></tr>';
//			$("#bootstrap-stream-container2").append(file);
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
			//gongrendengji.loadZsList();//刷新右侧列表区域  加载证书列表
			//dialog.close();
			
			var NEWPKID = $("#NEWPKID").val();
			var ZSPKID = $("#ZSPKID").val();//证书PKID
		    var lujing = $("#lujing").val();//图片路径
		    var lujing = "";
			if(NEWPKID!=null && NEWPKID!=""){
				lujing = _basepath+"uploadFiles/lwry/zs/"+NEWPKID+"/"+file.name;
				//编码
				lujing = _basepath+"/file/download.json?tableName=T_LAOWURENYUAN_ZS&MULU="+NEWPKID+"&FILENAME="+file.name+"&r="+ Math.random();
			}else{
				//编码
				lujing = _basepath+"/file/download.json?tableName=T_LAOWURENYUAN_ZS&MULU="+ZSPKID+"&FILENAME="+file.name+"&r="+ Math.random();
			}
			
//			console.log("lujing:"+lujing);
			
			
			
			lujing = encodeURI(lujing);
			
//			console.log("lujing:"+lujing);
			
			$("#yltp").get(0).src=lujing;
		},
		onQueueComplete: function(msg) {
			//全部上传完成的回调方法
			//addzhengshu.destroy();  //修改到，点取消，点保存，点叉号的时候才销毁
			//BootstrapDialog.alert({title: '提示信息',message: '全部文件上传成功!' });
		},
		onUploadError: function(status, msg) {
			//文件上传出错的响应事件
			BootstrapDialog.show({  
				title:'提示信息',
	            message:'上传附件失败,'+msg+',状态码:'+status,
	            draggable: true,
	            closable: true,
	            size :BootstrapDialog.SIZE_NORMAL,
	            buttons: [{
			    label: '关闭',
			    cssClass: 'keman_btn',
			    action: function(dialogRef){
			       dialogRef.close();
			    }
			  }
			  ]
	        });
		}
	};
	myupload2 = new fhadminUpload(option);
	
};





/**
 * 初始化
 */
$(function(){
	/*var datetimeOption = {
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  0,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		maxView:4,
		minView:2,
		minuteStep:1,
		forceParse: 0,
		format:'yyyy-mm-dd',
//		format:'hh:ii',
        showMeridian: 1
	};
	
	
	
    $("#fazhengrq").datetimepicker(datetimeOption).on('hide',function(e) {
        $('#zhengshuFrom').data('bootstrapValidator')  
            .updateStatus($(this).attr("name"), 'NOT_VALIDATED',null)  
            .validateField($(this).attr("name"));  
    });
    
    $("#youxiaojzrq").datetimepicker(datetimeOption);*/
    
	
	var fazhengrqDate = {
			  elem: '#fazhengrq',
			  format: 'YYYY-MM-DD',
			  //min: laydate.now(), //设定最小日期为当前日期
			  max: '2099-06-16', //最大日期
			  istime: true,
			  istoday: false,
			  choose: function(datas){
				 youxiaojzrqDate.min = datas; //开始日选好后，重置结束日的最小日期
				 youxiaojzrqDate.start = datas //将结束日的初始值设定为开始日
			     $('#zhengshuFrom').data('bootstrapValidator').resetForm();
			  }
	};
	
	var youxiaojzrqDate = {
	  elem: '#youxiaojzrq',
	  format: 'YYYY-MM-DD',
	  //min: laydate.now(),
	  max: '2099-06-16',
	  istime: true,
	  istoday: false,
	  choose: function(datas){
		  fazhengrqDate.max = datas; //结束日选好后，重置开始日的最大日期
	    $('#zhengshuFrom').data('bootstrapValidator').resetForm();
	  }
	};
	
	laydate(fazhengrqDate);
	laydate(youxiaojzrqDate);
	
	
	$('body').on('click', '.laydate-icon', function() {
		laydate({
			istoday: false, //是否显示今天
   		    choose: function(datas){ //选择日期完毕的回调
   		    	$('#zhengshuFrom').data('bootstrapValidator').resetForm(); 
   		    }
	   	});
	});
  
    var ZSPKID = $("#ZSPKID").val();//证书PKID
    var lujing = $("#lujing").val();//图片路径
    
    if(ZSPKID!=null && ZSPKID!="" && lujing!=null && lujing!=""){
    	var tempArray = new Array();
    	var file = {};
    	file.filename = $("#_filename").val();
    	file.fileid = $("#_fileid").val();
    	file.filesize = $("#_filesize").val();
    	file.data = {};
//    	file.data.laowurenyuanid = $("#PKID").val();//劳务人员PKID
    	file.data.zspkid = ZSPKID;//证书pkid
    	file.data.tableName = "T_LAOWURENYUAN_ZS";
    	tempArray.push(file);
    	
    	//加载上传控件
    	addzhengshu.initUpload(tempArray);
    	
    	//去掉删除按钮
    	zhubiao2.hiddenDownButton();
    	//end 去掉删除按钮
    	
    }else{
    	
    	//加载上传控件
    	addzhengshu.initUpload();
    }
});
/**
 * end 初始化
 */






