/**
 * 学年对象
 */
//@ sourceURL=stuyear_edit.js
(function($, window) {
	var page={};
	page.addDateSelect=function(){
		var date_str=
			'<div class="form-inline">'+
				'<input id="selectMonth" class="form-control jf_FltConditions riqi" placeholder="请选择日期" type="text"  onClick="WdatePicker({dateFmt:\'yyyy-MM\'})"/>'+
					'<a href="javascript:void(0);" class="btn btn-danger btn_delete" ><span class="fa fa-trash-o" ></span>'+
					'</a>'+
			'</div>';
			$("#dateArea").append(date_str);
			$(".btn_delete").click(function(){
				$(this).parent().remove();
			});
	};
	$(".btn_delete").click(function(){
		$(this).parent().remove();
	});
	$("#btn_addate").click(function(){
		page.addDateSelect();
	});
})(jQuery, window);
