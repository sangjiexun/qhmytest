/**
 * 宿舍计划汇总
 */
//@ sourceURL=peoplesumcjs2.js
(function($, window) {
	function getTab(){
		$("#toFeeSumlistTable").html("");
		var T_STUDENT_DORM_TYPE_PKID = $("#T_STUDENT_DORM_TYPE_PKID  option:selected").val();
		var SD_SEX = $("#SD_SEX  option:selected").val();
		var DEPARTMENT_ID = $("#DEPARTMENT_ID  option:selected").val();
		var PICI = $("#PICI").val();
		var htmls = '<tr>'+
			'<th>院校专业</th>'+
			'<th style="white-space:nowrap;">性别</th>'+
			'<th>总人数</th>'+
			($("#collegeNameEn").val() == "CAIJING" ? '<th>确认专业人数</th>' : '<th>确认个人信息人数</th>')
			+
			'<th>宿舍类型</th>'+
			'<th>已缴费人数</th>'+
			'<th>床位总数</th>'+
			'<th>剩余床位数</th>'+	
			/*'<th>批次</th>'+	*/
		'</tr>';
		var url = _basepath + "reportStat/getStudentDormPlanSumTable.json";
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  dataType : "json",
			  data:{
				  T_STUDENT_DORM_TYPE_PKID:T_STUDENT_DORM_TYPE_PKID,
				  SD_SEX:SD_SEX,
				  DEPARTMENT_ID:DEPARTMENT_ID,
				  PICI:PICI
			  },
			  success : function(result) {
				  var dormPlanList = result.studentDormPlanSumTable;
				  if(dormPlanList != ''){
					  for(var i=0; i< dormPlanList.length; i++){
						  var dormPlan = dormPlanList[i];
						  htmls += '<tr class="'+(i == dormPlanList.length -1 ? "danger":"")+'">'+
									  	'<td>'+dormPlan.DEPARTMENTNAME+'</td>'+
									  	'<td>'+dormPlan.SD_SEX_NAME+'</td>'+
									  	'<td>'+dormPlan.STUDENTCOUNT_TOTAL+'</td>'+
									  	'<td>'+($("#collegeNameEn").val() == "CAIJING" ? dormPlan.STUDENTCOUNT_TOTAL_SUREDEP : dormPlan.STUDENTCOUNT_TOTAL_SUREINF)+'</td>'+
									  	'<td>'+dormPlan.DT_NAME+'</td>'+
									  	'<td>'+dormPlan.STUDENTCOUNT_YIJIAO+'</td>'+
									  	'<td>'+dormPlan.DORMCOUNT_TOTAL+'</td>'+
									  	'<td>'+dormPlan.DORMCOUNT_SHENGYU+'</td>'+
									  	/*'<td>'+dormPlan.PICI_NAME+'</td>'+*/
								   '</tr>';
					  }
				  }else{
					  htmls += '<tr class="no-records-found">'+
								  	'<td colspan="8" text-align="center">没有找到匹配的记录</td>'+
							   '</tr>';
				  }
				  
				  
			  }
		});
		
		$("#toFeeSumlistTable").html(htmls);
		
	};
	
	$("#checkQuery").click(function(){
		getTab();
	});
	
	$("#checkOut").click(function(){
		var T_STUDENT_DORM_TYPE_PKID = $("#T_STUDENT_DORM_TYPE_PKID  option:selected").val();
		var SD_SEX = $("#SD_SEX  option:selected").val();
		var DEPARTMENT_ID = $("#DEPARTMENT_ID  option:selected").val();
		var PICI = $("#PICI").val();
		window.location.href=encodeURI(_basepath+'reportStat/exportStudentDormPlanSumTable.json?T_STUDENT_DORM_TYPE_PKID='+
		T_STUDENT_DORM_TYPE_PKID+'&SD_SEX='+SD_SEX+'&DEPARTMENT_ID='+DEPARTMENT_ID+'&PICI='+PICI);
	});
	
	getTab();
	
   
	})(jQuery, window);
