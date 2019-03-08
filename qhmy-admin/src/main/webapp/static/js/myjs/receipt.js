
//@ sourceURL=paymanage.js
//收据页面js
var receipt={};
receipt.updateGroupName_pkid;
receipt.moveGroup_type;
receipt.moveGroup_pkid;
receipt.otherItemFlag;
receipt.groupPkid;

//查看未分组数据
var getNoGroup = function(){
	//清空数据
	$("#noGroupSys").html("");
	$("#noGroup").html("");
	$.ajax({
		  type: 'POST',
		  url: _basepath+"receipt/getAllPayItemList.json",
		  async: false,
		  data: {
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  //计数器
				  var ccount = 0;
				  var payItemListResult = result.payItemListResult;
				  for(var i=0; i< payItemListResult.length; i++){
					  if(payItemListResult[i].ITEM_TYPE == '0'){
						  $("#noGroupSys").append('<div class="jf_RecItem" style="padding-top:10px;">'+
									'<div class="bg-warning jf_RecItemText">'+
									'<span>'+payItemListResult[i].PAYITEM+'</span>'+
								'</div>'+
							'</div>');
					  }else{
						  ccount ++;
						  $("#noGroup").append('<div class="jf_RecItem" style="padding-top:10px;">'+
									'<div class="bg-warning jf_RecItemText">'+
									'<span>'+payItemListResult[i].PAYITEM+'</span>'+
									'<span class="fa fa-arrows pull-right jf_shuzi3" onclick="javascript:toMoveNoGroup(\''+payItemListResult[i].PKID+'\',\''+payItemListResult[i].OTHERITEM_FLAG+'\');"></span><div class="clearfix"></div>'+
								'</div>'+
							'</div>');
						  
					  }
					  if(ccount % 4 == 0){
						  $("#noGroup").append('<div class="clearfix"></div>');
						  
					  }
				  }
				  $("#noGroupSys").append('<div class="clearfix"></div>');
				 
			  }
			  return false;
		  }
		});
}

//查看已分组数据
var getGroup = function(){
	//清空数据
	$('#groups').html("");
	$.ajax({
		  type: 'POST',
		  url: _basepath+"receipt/getPayItemGroupsList.json",
		  async: false,
		  data: {
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  var groupsHtml = "";
				  for(var key in result) {
					  if(key != 'result'){
						  var itemArray = key.split("@|@");
						  groupsHtml += '<div class="panel panel-info" style="margin-top: 15px;">'+
									'<div class="jf_PanelHead panel-heading">'+
										'<div class="panel-title">'+itemArray[1]+
											'<span class="pull-right">'+
												'<i title="编辑" class="fa fa-pencil jf_shuzi3" onclick="javascript:toUpdateGroupName(\''+itemArray[0]+'\',\''+itemArray[1]+'\');"></i>&nbsp;&nbsp;'+
												'<i title="删除" class="fa fa-trash-o jf_shuzi3" onclick="javascript:deleteGroup(\''+itemArray[0]+'\',\''+itemArray[1]+'\');"></i>'+
											'</span>'+
										'</div>'+
									'</div>';
						  groupsHtml += '<div class="panel-body">';
						  groupsHtml += '<div>';
						  var html = "";
						  for(var i = 0; i<result[key].length; i++){
//							  alert(result[key][i].PKID;
							  html += '<div class="jf_RecItem" style="padding-top:10px;">'+
														'<div class="bg-warning jf_RecItemText">'+
															'<span>'+(result[key][i].OTHERITEM_FLAG == "Y"?result[key][i].OTHERPAYITEM:result[key][i].PAYITEM)+'</span>'+
															'<span class="fa fa-arrows pull-right jf_shuzi3" onclick="javascript:toMoveGroup(\''+result[key][i].PKID+'\',\''+result[key][i].OTHERITEM_FLAG+'\',\''+itemArray[0]+'\');"></span>'+
														'</div>'+
													'</div>';
						  }
						  groupsHtml += html;
						  groupsHtml += '</div>';
						  groupsHtml += '</div>';
						  groupsHtml += '<div class="clearfix"></div>';
						  groupsHtml += '</div>';
					  }
					 
					 
				  } 
				  $("#groups").html(groupsHtml);
			  }
			  return false;
		  }
		});
}

//添加分组
$('#insertGroup').click(function(){
	var groupNameForInsert = $("#groupNameForInsert").val();
	if(groupNameForInsert == ''){
		layer.msg("组名不能为空！");
		return;
	}
	$.ajax({
	  type: 'POST',
	  url: _basepath+"receipt/insertPayItemGroup.json",
	  async: false,
	  data: {
		  groupName:groupNameForInsert
	  },
	  dataType : "json",
	  success : function(result) {
		  if(result.result == 'success'){
			  $("#btn_cancel_insert").click();
			//获得所有分组的缴费项目
			getGroup();
			  layer.msg("添加分组成功！");
		  }
		  return false;
	  }
	});
});

//修改组名
$('#updateGroupName').click(function(){
	var groupName = $("#groupName").val();
	if(groupName == ''){
		layer.msg("组名不能为空！");
		return;
	}
	$.ajax({
	  type: 'POST',
	  url: _basepath+"receipt/updatePayItemGroup.json",
	  async: false,
	  data: {
		  pkid:receipt.updateGroupName_pkid,
		  groupName:groupName
	  },
	  dataType : "json",
	  success : function(result) {
		  if(result.result == 'success'){
			  $("#btn_cancel_update").click();
			  refreshPage();
			  layer.msg("修改组名成功！");
		  }
		  return false;
	  }
	});
});
//修改组名框
var toUpdateGroupName = function(pkid,itemName){
	$("#groupName").val(itemName);
	$("#updateGroup_obj").html('修改"'+itemName+'"组名');
	receipt.updateGroupName_pkid = pkid;
	$('#mymodal').modal({
		keyboard: true
	});


}

//移动已分组缴费项目
$('#moveGroup').click(function(){
	var groupPkid = $("#groupSelect").val();
	if(receipt.groupPkid == groupPkid){
		$("#btn_cancel_move").click();
		layer.msg("移动成功！");
		return false;
	}
	$.ajax({
	  type: 'POST',
	  url: _basepath+"receipt/moveGroup.json",
	  async: false,
	  data: {
		  type:receipt.moveGroup_type,
		  groupPkid:groupPkid,
		  payItemPkid:receipt.moveGroup_pkid,
		  otherItemFlag:receipt.otherItemFlag
	  },
	  dataType : "json",
	  success : function(result) {
		  if(result.result == 'success'){
			  $("#btn_cancel_move").click();
			  refreshPage();
			  layer.msg("移动成功！");
		  }
		  return false;
	  }
	});
});

//给下拉列表框赋值
var fullSelect = function(){
	//清空数据
	$("#groupSelect").html("");
	$.ajax({
		  type: 'POST',
		  url: _basepath+"receipt/getAllGroups.json",
		  async: false,
		  data: {
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  for(var i = 0; i < result.pds.length; i++){
					  if(receipt.moveGroup_type == '1'){
						  $("#groupSelect").append('<option  value="'+result.pds[i].PKID+'">'+result.pds[i].GROUP_NAME+'</option>');
					  }else{
						  $("#groupSelect").append('<option  value="">未分组</option><option  value="'+result.pds[i].PKID+'">'+result.pds[i].GROUP_NAME+'</option>');
					  }
				  }
			  }
			  return false;
		  }
		});
}
//移动未分组缴费醒目
var toMoveNoGroup = function(pkid,otherItemFlag){
	receipt.moveGroup_pkid = pkid;
	receipt.moveGroup_type = '1';
	receipt.otherItemFlag = otherItemFlag;
	$('#mymodal2').modal({
		keyboard: true
	});
	fullSelect();
}

//移动已分组缴费醒目
var toMoveGroup = function(pkid,otherItemFlag,groupPkid){
	receipt.moveGroup_pkid = pkid;
	receipt.moveGroup_type = '2';
	receipt.otherItemFlag = otherItemFlag;
	receipt.groupPkid = groupPkid;
	$('#mymodal2').modal({
		keyboard: true
	});
	fullSelect();
}

//添加分组
var toInsertGroup = function(){
	$('#mymodal3').modal({
		keyboard: true
	});
}

//刷新页面
var refreshPage = function(){
	//获得所有未分组的缴费项目
	getNoGroup();
	//获得所有分组的缴费项目
	getGroup();
}

//删除分组
var deleteGroup = function(pkid,itemName){
	BootstrapDialog.show({  //显示需要提交的表单。
    	title:'提示信息',	
    message: '你确定要删除"'+itemName+'"组吗？',
    closable: true, 
      buttons: [{
	    label: '确定',
	    cssClass: 'btn-danger',
	    action: function(dialogRef){
	    	 $.ajax({
					type:"post",
					dataType:"json",
					url:_basepath+'receipt/deleteGroup.json?pkid='+pkid,
					 success:function(data){
						 if("success" == data.result){
							 layer.msg("删除成功！");
							//获得所有分组的缴费项目
							 refreshPage();
						 }else if("fail" == data.result){
							 layer.msg("删除失败，请重试！");
						 }
					  },
					error: function (XMLHttpRequest, textStatus, errorThrown) { 
					          alert("删除失败，请重试！");
					       }
					});
	    	
	    	
        dialogRef.close();
        }
  }, {
    label: '取消',
    cssClass: 'btn-default',
    action: function(dialogRef){
       dialogRef.close();
    }
  }
  ]
    });
}
$(function(){
	refreshPage();
});
