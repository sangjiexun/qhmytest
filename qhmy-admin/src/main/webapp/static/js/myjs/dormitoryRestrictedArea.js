/**
 * 宿舍限制范围对象
 */
//@ sourceURL=dormitoryRestrictedArea.js
(function($, window) {
	var dormitoryRestrictedArea = {};

	//去修改页面
	$("#btn_reset").click(function(){
		var PKID = $("#PKID").val();
		$.ajax({
			type : "post",
			dataType : "json",
			async: false,
			url : _basepath+ 'drArea/getStudent.json',
			data : {"PKID":PKID},
			success : function(data) {
				if ("success" == data.rst) {
					BootstrapDialog.show({ // 显示需要提交的表单。
						title : '宿舍限制范围',
						message : $('<div></div>').load(_basepath+'/drArea/goEdit.json?PKID='+PKID),
						closable : true,
						buttons : [
								{
									label : '确定',
									cssClass : 'btn-danger',
									action : function(dialogRef) {
										dormitoryRestrictedArea.updateArea(dialogRef);
									}
								}, {
									label : '取消',
									cssClass : 'btn-default',
									action : function(dialogRef) {
										dialogRef.close();
									}
								} ]
					});
				}else{
					layer.msg("已经有宿舍数据生成，不允许重置");
				}
			},
			error : function(XMLHttpRequest, textStatus,
					errorThrown) {
				alert(errorThrown);
			}
		});
		
	});
	//修改方法
	dormitoryRestrictedArea.updateArea = function(dialogRef){
		var PKID = $("#PKID").val();
		var AREATYPE = $("#AREATYPE").val();
		if(AREATYPE == ''){
			layer.msg("请选择要修改的宿舍限制范围！");
			return;
		}
		dialogRef.close();
		$.ajax({
			type : "post",
			dataType : "json",
			async: false,
			url : _basepath+ 'drArea/editArea.json',
			data : {"PKID":PKID,"AREATYPE":AREATYPE},
			success : function(data) {
				if ("success" == data.rst) {
					layer.msg("操作成功！");
					$("#drArea").text(AREATYPE);
				}
			},
			error : function(XMLHttpRequest, textStatus,
					errorThrown) {
				alert(errorThrown);
			}
		});
	}
	
})(jQuery, window);
