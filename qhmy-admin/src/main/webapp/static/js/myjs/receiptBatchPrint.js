
//@ sourceURL=paymanage.js
//收据页面js
var receiptBatchPrint={};
receiptBatchPrint.updateGroupName_pkid;
receiptBatchPrint.moveGroup_type;
receiptBatchPrint.moveGroup_pkid;


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
			  way:'forPrint',
			  payItemPkids:offlinePay.getIdSelections()+"",
			  studentPkid:offlinePay.studentPkid
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  if(result.payItemListResult.length <= 0){
					  return false;
				  }
				  $("#noGroupSys").append('<div style="margin-bottom: 15px;"><label for="">'+
							'<input type="checkbox" id="noGroupBody"/>&nbsp;'+
							'<span class="label label-warning">未分组（默认）</span>'+
						'</label>');
				  
				  //计数器
				  var ccount = 0;
				  var ccount2 = 0;
				  var sysGroup = '';
				  var noGroup = '';
				  var payItemListResult = result.payItemListResult;
				  for(var i=0; i< payItemListResult.length; i++){
					  if(payItemListResult[i].ITEM_TYPE == '0'){
						  ccount ++;
						  sysGroup += '<form action="" class="form-inline">';
						  sysGroup += '<div class="form-group col-xs-3">'+
														'<input type="checkbox" name="payItem1" id="" value="--@|@'+payItemListResult[i].PKID+'"/>&nbsp;'+
														'<span>'+payItemListResult[i].PAYITEM+'</span>'+
													'</div>';
						  sysGroup += '</form>';
						  
					  }else{
						  ccount2 ++;
						  noGroup += '<form action="" class="form-inline">';
						  noGroup += '<div class="form-group col-xs-3">'+
													'<input type="checkbox" name="payItem2" id="" value="--@|@'+payItemListResult[i].PKID+'"/>&nbsp;'+
													'<span>'+payItemListResult[i].PAYITEM+'</span>'+
												'</div>';
						  noGroup += '</form>';
					  }
				  }
				  if(ccount % 4 == 0){
					  sysGroup += '<div class="clearfix"></div>';
				  }else if(ccount % 4 <5){
					  sysGroup += '<div class="clearfix"></div>';
				  }
				  if(ccount2 % 4 == 0){
					  noGroup += '<div class="clearfix"></div>';
				  }
				  $("#noGroupSys").append(sysGroup);
				  $("#noGroupSys").append(noGroup);
				  $("#noGroupSys").append('<div class="clearfix"></div></div>');
			  }
			  return false;
		  }
		});
};
//各已分组全选
var clickCheckBox = function(index){
	if($("#payItem3_"+index).is(':checked')){
		$(":checkbox[class='payItem3_"+index+"']").attr("checked", "checked");
		$(":checkbox[class='payItem3_"+index+"']").prop("checked", true);
	}else{
		$(":checkbox[class='payItem3_"+index+"']").removeAttr("checked");
	}
	
};
//查看已分组数据
var getGroup = function(){
	//清空数据
	$('#groups').html("");
	$.ajax({
		  type: 'POST',
		  url: _basepath+"receipt/getPayItemGroupsList.json",
		  async: false,
		  data: {
			  way:'forPrint',
			  payItemPkids:offlinePay.getIdSelections()+"",
			  studentPkid:offlinePay.studentPkid
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  var index = 0;
				  for(var key in result) {
					  if(key != 'result' && key !='payItemListResult' && result[key].length > 0){
						  index ++;
						  var itemArray = key.split("@|@");
						  $('#groups').append('<div style="margin-bottom: 15px;"><label for="">'+
									'<input type="checkbox" id="payItem3_'+index+'" onclick="javascript:clickCheckBox('+index+');"/>&nbsp;'+
									'<span class="label label-warning">'+itemArray[1]+'</span>'+
								'</label><form action="" class="form-inline">');
						  //计数器
						  var ccount = 0;
						  for(var i = 0; i<result[key].length; i++){
							  	ccount ++;
								$('#groups').append('<div class="form-group col-xs-3">'+
														'<input type="checkbox" class="payItem3_'+index+'" name="payItem3" id="payItem3_'+index+'_'+i+'" value="'+itemArray[0]+'@|@'+result[key][i].PAY_ITEM_PKID+'"/>&nbsp;'+
														'<span>'+(result[key][i].OTHERITEM_FLAG == "Y"?result[key][i].OTHERPAYITEM:result[key][i].PAYITEM)+'</span>'+
													'</div>');
								if(ccount % 4 == 0){
									$('#groups').append('<div class="clearfix"></div>');
								}
						  }
						  if(ccount < 5){
							  $('#groups').append('<div class="clearfix"></div>');
						  }
						  $('#groups').append('</form></div>');
					  }
					 
					 
				  } 
			  }
			  return false;
		  }
		});
}

//刷新页面
var refreshPage = function(){
	//获得所有未分组的缴费项目
	getNoGroup();
	//获得所有分组的缴费项目
	getGroup();
	
	//未分组全选
	$("#noGroupBody").bind("click", function () {
		if($("#noGroupBody").is(':checked')){
			$("[name = payItem1]:checkbox").attr("checked", "checked");
			$("[name = payItem2]:checkbox").attr("checked", "checked");
			$("[name = payItem1]:checkbox").prop("checked", true)
		    $("[name = payItem2]:checkbox").prop("checked", true)
		}else{
			$("[name = payItem1]:checkbox").attr("checked", false);
		    $("[name = payItem2]:checkbox").attr("checked", false);
		}
	});
	
}