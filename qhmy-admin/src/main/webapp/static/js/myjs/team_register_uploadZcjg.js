/**
 * 本模块的js json对象
 */
var zizhiJsonObj = {};

/*
 * 上传组件主表对象
 */
zizhiJsonObj.list = new fhadminZhubiaoList({});//集合对象
zizhiJsonObj.zhubiao = null;
zizhiJsonObj.myupload = null;
zizhiJsonObj.myupload2 = null;
//end 上传组件主表对象


/*
 * 从zizhiArray数组中删除
 */
zizhiJsonObj.delZizhiArrayObj = function(zizhiArray,index){
	var id = 0;
	for(var i=0;i<zizhiArray.length;i++){
		if(zizhiArray[i].index == index){
			id = i;
			break;
		}
	}
	zizhiArray.splice(id,1);
};




/*
 * 删除附件行
 */
zizhiJsonObj.delFjTr = function(fileId){
	myupload.stream.cancelOne(fileId);
	$("#"+fileId).remove();
	
	var file = {};
	file.fileid = fileId;
	zhubiao.remove(file);//从容器中删除
};

zizhiJsonObj.destroy = function(){
	//取消未上传列表,由于只是隐藏，因此要取消
		//取消上传任务
	if(myupload!=null){
		myupload.delAllTask();
	  	myupload.chearTableTr();
	  	myupload.destroy();//销毁上传对象
	}
  	
  	zhubiao = null;//主表对象为空
  	//清空附件行 end 取消未上传列表,由于只是隐藏，因此要取消
  	
  	//资质附件销毁
  	zizhiJsonObj.list.destroy();
  	//end 资质附件销毁
  	
  	$(".zzzjDiv").html("<div class=\"zzzjDivYdc\"></div>");//销毁 zzzjDiv 
};

/*
 * 数组合并
 */
zizhiJsonObj.filesWscMerge = function(){
	var tempArray = new Array();
	if(zizhiJsonObj.list.zhubiaoList!=null){
		for(var i=0;i<zizhiJsonObj.list.zhubiaoList.length;i++){
			tempArray = tempArray.concat(zizhiJsonObj.list.zhubiaoList[i].wsc_files);
		}
	}
	return tempArray;
};


zizhiJsonObj.filesDelMerge = function(){
	var tempArray = new Array();
	if(zizhiJsonObj.list.zhubiaoList!=null){
		for(var i=0;i<zizhiJsonObj.list.zhubiaoList.length;i++){
			tempArray = tempArray.concat(zizhiJsonObj.list.zhubiaoList[i].del_files);
		}
	}
	return tempArray;
};


/**
 * 资质文件附件上传
 */
zizhiJsonObj.upload = function(pkid){
	var muluStr = "";
	if(zizhiJsonObj.list.zhubiaoList!=null){
		for(var i=0;i<zizhiJsonObj.list.zhubiaoList.length;i++){
			muluStr = pkid+"_"+zizhiJsonObj.list.zhubiaoList[i].config.trNumber;
//			console.log(muluStr);
			
			if(zizhiJsonObj.list.zhubiaoList[i].upload!=null && zizhiJsonObj.list.zhubiaoList[i].upload.stream!=null){
				zizhiJsonObj.list.zhubiaoList[i].upload.stream.config.postVarsPerFile
				= {tableName: "T_QIYEZIZHI_FJ", mulu: muluStr};
				zizhiJsonObj.list.zhubiaoList[i].upload.stream.upload();//执行上传
			}
		}
	}
};
