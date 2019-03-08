
//@ sourceURL=paymanage.js
//缴费管理页面js
var pm={};
var index_id = 1;//文件id
var index_file = 1;
var indexFileArray = new Array();
var itemChildNameList;//用于存储新的项目名
var oldChildNameList;//用于存储旧的项目名
var fbArray = new Array();
//日期插件参数设置

laydate.render({
	elem:'#startDate',
	isclear: true, //是否显示清空
	istoday: true, //是否显示今天
	type: 'datetime',
	max: 1,
	format: 'yyyy-MM-dd HH:mm:ss',
	festival: true, //显示节日
	start: 0
});
laydate.render({
	elem:'#endDate',
	isclear: true, //是否显示清空
	istoday: true, //是否显示今天
	type: 'datetime',
	max: 1,
	format: 'yyyy-MM-dd HH:mm:ss',
	festival: true, //显示节日
	start: 0
});
laydate.render({
	elem:'#RIQIqi',
	isclear: true, //是否显示清空
	istoday: true, //是否显示今天
	type: 'datetime',
	min: 0,
	format: 'yyyy-MM-dd HH:mm:ss',
	festival: true, //显示节日
	start: 0
});
laydate.render({
	elem:'#RIQIzhi',
	isclear: true, //是否显示清空
	istoday: true, //是否显示今天
	type: 'datetime',
	min: 0,
	format: 'yyyy-MM-dd HH:mm:ss',
	festival: true, //显示节日
	start: 0
});
var v = 1;
var bm_index = 0;//报名参数
var html_select_area = "";
function html_select_areaa_yj(v){
	html_select_area='<div class="form-inline jf-feiYong">'+
	'<div class="form-group pull-left">'+
	'<label for="" style="margin-right: 4px;"> 费用调整</label>'+
	'<select name="" class="form-control guizeshezhi_select">'+
	'<option value="">无</option>'+
	'<option value="2">报名时间</option>'+
	'</select> '+
	'</div>'+
	'<div class="zengjianarea" hidden="hidden">'+
		'<div class="pull-left jf-zhiJian">'+
		'<input type="radio" checked="checked" value="0" name="xuan'+v+'"/> 直减'+
		'</div>'+
		'<div class="pull-left jf-zhiJian">'+
		'<input type="radio" value="1" name="xuan'+v+'"/> 增长'+
		'</div>'+
		'<input class="form-control jianjiajine" type="text" placeholder="金额" style="width:70px !important;"/> '+
	'</div>'+
	'<span class="fa fa-minus-square-o jianguize" style="margin-left:25px;line-height:42px;"></span>'+
	'<div class="clearfix"></div>'+
	'</div>'+
	'<div class="form-inline youhuiarea" style="margin-left:65px;">'+
	'<div class="clearfix"></div> '+
	'</div>';
	return html_select_area;
}
function html_select_areaa(v){
	html_select_area='<div class="form-inline jf-feiYong">'+
	'<div class="form-group pull-left">'+
	'<label for="" style="margin-right: 4px;"> 费用调整</label>'+
	'<select name="" class="form-control guizeshezhi_select">'+
	'<option value="0">无</option>'+
	'<option value="1">合作学校</option>'+
	'<option value="2">报名时间</option>'+
	'<option value="3">预交</option>'+
	'<option value="4">联考成绩</option>'+
	'<option value="5">班级</option>'+
	'<option value="6">美院</option>'+
	'</select> '+
	'</div>'+
	'<div class="zengjianarea" hidden="hidden">'+
		'<div class="pull-left jf-zhiJian">'+
		'<input type="radio" checked="checked" value="0" name="xuan'+v+'"/> 直减'+
		'</div>'+
		'<div class="pull-left jf-zhiJian">'+
		'<input type="radio" value="1" name="xuan'+v+'"/> 增长'+
		'</div>'+
		'<input class="form-control jianjiajine" type="text" placeholder="金额" style="width:70px !important;"/> '+
	'</div>'+
	'<span class="fa fa-minus-square-o jianguize" style="margin-left:25px;line-height:42px;"></span>'+
	'<div class="clearfix"></div>'+
	'</div>'+
	'<div class="form-inline youhuiarea" style="margin-left:65px;">'+
	'<div class="clearfix"></div> '+
	'</div>';
	return html_select_area;
};
//根据缴费类型筛选规则下拉
pm.yujiaoOption = function(){
	var top_pay_style = $("#top_pay_style").val();//缴费类型
	if(top_pay_style.trim()=='预交'){
		$(".guizeshezhi_select").empty();
		$(".guizeshezhi_select").append('<option value="">无</option><option value="2">报名时间</option>');
	}
};
pm.jianguize = function(obj){
	obj.parent().next().html("");
	obj.parent().remove();
};
pm.guizeshezhi = function(objj){
	var obj = objj;
	var discount_mode = obj.val();
	if(discount_mode=='1'){//表示是合作学校
		obj.parent().parent().find(".zengjianarea").show();
		obj.parent().parent().next().show();
		var url = _basepath+'pay/gethzxxList.json';
		$.ajax({
			type:'post',
			dataType:'json',
			//async:false, //设置为同步
			url:url,
			data:{},
			success:function(data){
				var list = data.list;
				var html = "";
				for(var i=0;i<list.length;i++){
					html = html+'<option value="'+list[i].PKID+'">'+list[i].SCHOOLNAME+'</option>';
				}
				obj.parent().parent().next().html('<select class="hzxy_select" multiple="multiple">'+								
						html+
				'</select>');
				$('.hzxy_select').multiselect({
					enableFiltering: true,
					includeSelectAllOption: true,
					selectAllText: ' 全选',
					nonSelectedText: '请选择合作学校',
					allSelectedText: '全部选择',
					selectAllJustVisible: true,
					filterPlaceholder:'请输入合作学校',
					buttonWidth: '183px'//设置字体过长显示...
				});
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
		         alert(errorThrown); 
			}
		});
	}else if(discount_mode=='2'){//报名时间
		obj.parent().parent().find(".zengjianarea").show();
		obj.parent().parent().next().html('<input class="form-control baomingtimeq" id="baomingtimeq'+bm_index+'" type="text" placeholder="起始时间"'+
				'style="width:120px !important;" readonly="readonly" /> 至'+
				'<input class="form-control baomingtimez" id="baomingtimez'+bm_index+'" type="text" placeholder="终止时间" style="width:120px !important;" readonly="readonly" /> '+
				'<span class="jf-zhu">注：包含所选时间当天</span>');
		laydate.render({
			elem:'#baomingtimeq'+bm_index,
			isclear: true, //是否显示清空
			istoday: true, //是否显示今天
			type: 'date',
			format: 'yyyy-MM-dd',
			festival: true, //显示节日
		});
		laydate.render({
			elem:'#baomingtimez'+bm_index,
			isclear: true, //是否显示清空
			istoday: true, //是否显示今天
			type: 'date',
//			min: 0,
			format: 'yyyy-MM-dd',
			festival: true, //显示节日
//			start: 0
		});
		bm_index++;
	}else if(discount_mode=='3'){//预交
		obj.parent().parent().find(".zengjianarea").show();
		obj.parent().parent().next().html('');
	}else if(discount_mode=='4'){//联考
		obj.parent().parent().find(".zengjianarea").show();
		obj.parent().parent().next().html('<div class="form-inline">'+
				'<label class="pull-left" for=""> 学生类型：</label>'+
				'<div class="pull-left cengCi"><input type="checkbox" value="bxfks"/> 本校复课生</div>'+
				'<div class="pull-left cengCi"><input type="checkbox" value="wxfks"/> 外校复课生</div>'+
				'<div class="clearfix"></div>'+
			'</div>'+
			'<div class="form-inline">'+
				'<label class="pull-left jf-fenShu" for=""> 分数：</label>'+
				'<input class="form-control liankaozuidi" type="text" placeholder="最低分" style=" width:100px !important;"/> 至'+
				'<input class="form-control liankaozuigao" type="text" placeholder="最高分" style="width:100px !important;"/> '+
				'<span class="jf-zhu">注：包含所填分数</span>'+
				'<div class="clearfix"></div>'+
			'</div>');
	}else if(discount_mode=='5'){//班级
		obj.parent().parent().find(".zengjianarea").show();
		var url = _basepath+'pay/getClassList.json';
		var grade = $("#top_grade").attr("grade");//入学年份pkid
		var classType = $("#top_classType").attr("classType");//班型pkid
		$.ajax({
			type:'post',
			dataType:'json',
			url:url,
			data:{"GRADE":grade,"CLASSTYPE":classType},
			success:function(data){
				var list = data.list;
				var html = "";
				for(var i=0;i<list.length;i++){
					if(i%3==0){
						html = html+'<div class="form-inline">';
					}
					html = html + '<div class="pull-left cengCi banjiarea"><input value="'+list[i].PKID+'" type="checkbox"/>'+list[i].CLASS_NAME+'</div>';
					if((i>0 && (i+1)%3==0) || (i==(list.length-1))){
						html = html+'<div class="clearfix"></div>'+'</div>';
					}
				}
				obj.parent().parent().next().html(html);
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
		          alert(errorThrown); 
			}
		});
	}else if(discount_mode=='6'){//美院
		obj.parent().parent().find(".zengjianarea").show();
		var url = _basepath+'pay/getMeiYuanList.json';
		$.ajax({
			type:'post',
			dataType:'json',
			url:url,
			data:{},
			success:function(data){
				var list = data.list;
				var html = "";
				for(var i=0;i<list.length;i++){
					if(i%4==0){
						html = html+'<div class="form-inline">';
					}
					html = html + '<div class="pull-left cengCi meiyuanarea"><input value="'+list[i].PKID+'" type="checkbox"/>'+list[i].NAME+'</div>';
					if((i>0 && (i+1)%4==0) || (i==(list.length-1))){
						html = html+'<div class="clearfix"></div>'+'</div>';
					}
				}
				obj.parent().parent().next().html(html);
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
		          alert(errorThrown); 
			}
		});
	}else{//无
		obj.parent().parent().find(".zengjianarea").hide();
		obj.parent().parent().next().html('');
	}
};
$("#addfytz").click(function(){
	var top_pay_style = $("#top_pay_style").val();//缴费类型
	if(top_pay_style.trim()=='预交'){
		$("#feiyongtiaozhengarea").append(html_select_areaa_yj(v));
	}else{
		$("#feiyongtiaozhengarea").append(html_select_areaa(v));
	}
	v++;
	$(".guizeshezhi_select").change(function(){
		pm.guizeshezhi($(this));
	});
	$(".jianguize").click(function(){
		pm.jianguize($(this));
	});
	//pm.yujiaoOption();
});
/*$("#pay_style_gl").change(function(){
	var SCHOOL_YEAR_PKID = $("#school_year_gl").val();
	var PAY_TYPE_PKID = $("#pay_style_gl").val();
	var zNodes2;
	var setting2 = {
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "s", "N": "ps" }
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick2,
				onCheck: onCheck2
			}
	};
	$.ajax({
		  type: 'POST',
		  url: _basepath+"receipt/getAllPayItemListList.json",
		  async: false,
		  data: {SCHOOL_YEAR_PKID:SCHOOL_YEAR_PKID,PAY_TYPE_PKID:PAY_TYPE_PKID},
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  zNodes2 = result.payItemListResult;
			  }
			  return false;
		  }
		});
	zNodes2 = eval(zNodes2);
	$.fn.zTree.init($("#treeDemo2"), setting2, zNodes2);
});*/

$("#school_year_gl").change(function(){
	var SCHOOL_YEAR_PKID = $("#school_year_gl").val();
	var PAY_TYPE_PKID = $("#pay_style_gl").val();
	var zNodes2;
	var setting2 = {
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "s", "N": "ps" }
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick2,
				onCheck: onCheck2
			}
	};
	$.ajax({
		  type: 'POST',
		  url: _basepath+"receipt/getAllPayItemListList.json",
		  async: false,
		  data: {SCHOOL_YEAR_PKID:SCHOOL_YEAR_PKID,PAY_TYPE_PKID:PAY_TYPE_PKID},
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  zNodes2 = result.payItemListResult;
			  }
			  return false;
		  }
		});
	zNodes2 = eval(zNodes2);
	$.fn.zTree.init($("#treeDemo2"), setting2, zNodes2);
});

//获取list表格数据
pm.getTab=function (url,isRefresh) {
	//获得按钮权限
	/*var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")"); */
	
	var url = _basepath+"pay/paymanagetable.json";

	$('#paymanagetable').bootstrapTable(
					{
						url : url,//数据源
						dataField : "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
						totalField : 'total',
						sortOrder : 'desc',
						striped : true, //表格显示条纹  
						pagination : true, //启动分页  
						pageNumber : 1, //当前第几页  
						pageSize : 20, //每页显示的记录数  
						pageList : [1,5,10,20,30,50,100], //记录数可选列表  
						search : false, //是否启用查询  
						formatSearch : function() {
							return '查询';
						},//搜索框提示文字
						searchAlign : 'left',
						buttonsAlign : 'left',
						toolbarAlign : 'left',
						searchOnEnterKey : false,//true为按回车触发搜索事件
						showColumns : false, //显示下拉框勾选要显示的列  
						showRefresh : false, //显示刷新按钮  --只是前台刷新，个人觉得没什么用
						minimumCountColumns : 2, //最少允许的列数
						sidePagination : "server", //表示服务端请求  
						totalRows : 0, // server side need to set
						singleSelect : false,
						clickToSelect : true,
						onColumnSwitch: function(field,checked){
							//获取全部可显示的节点信息集合
							var resultColumnList = $('#paymanagetable').bootstrapTable('getVisibleColumns');
							var resultColumns = "";		
							//将所有field组合成以逗号隔开的字符串
							$.each(resultColumnList, function(i, column) {
								if(column.field=='0'/*||column.field=='opt'*/){
									return;
								}else{
									resultColumns+=column.field+",";
								}									
					     	});
							//更新可查看字段内容
							$.post("stuinfo/updateShowCols.json?table_name=JFGL_LIST",{table_show_cols:resultColumns},function(data){
								if(data.result=="success"){
//									layer.msg("保存成功!");
								}
							})
						},
						onDblClickRow:function(row){
						},
						queryParams : function getParams(params) {
							
							//开始日期
							var startDate = $("#startDate").val();
							var endDate = $("#endDate").val();
							//查询条件-是否启用
							var startusing = $("#startusingselect  option:selected").val();
							//查询条件-审核状态
							var status = $("#statusselect  option:selected").val();
							//查询条件-项目名称
//							var payitem = $("#payitemselect").val();
							var payitem = $("#orgtree2").val();
							
							var SCHOOL_YEAR_PKID = $("#school_year_gl").val();
							
							var PAY_TYPE_PKID = $("#pay_style_gl").val();
							var IS_FQ = $("#IS_FQ").val();
							
							var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
								limit : params.limit, //页面大小
								searchText : this.searchText,
								sortName : this.sortName,
								sortOrder : this.sortOrder,
								pageindex : this.pageNumber,
								startusing:startusing,
						    	status:status,
						    	payitem:payitem,
						    	startDate:startDate,
						    	endDate:endDate,
						    	IS_FQ:IS_FQ,
						    	SCHOOL_YEAR_PKID : SCHOOL_YEAR_PKID,
						    	PAY_TYPE_PKID : PAY_TYPE_PKID
								
							//当前页码
							};
							return temp;
						},
						buttonsAlign : "right",//按钮对齐方式
						selectItemName : 'id',
						toolbar : "#toolbar",
						toolbarAlign : 'left',
						columns : [
								{
									field : 'PKID',// 可不加
									align : "center",
									halign : 'center'
								},
								{
									field: 'GRADE_NAME',//可不加  
									title:'入学年份',
									align : "center",
									halign : 'center'
								},
								{
									field: 'CLASSTYPE_NAME',//可不加  
									title:'班型',
									align : "center",
									halign : 'center'
								},
								{
									field: 'STYLE_NAME',//可不加  
									title:'缴费类型',
									align : "center",
									halign : 'center'
								}
								,
								{
									field: 'ITEMLIST_CREATEMODE',//可不加  
									title:'缴费名单',
									align : "center",
									halign : 'center'
								},
								{
									field: 'JFMDFS',//可不加  
									title:'JFMDFS',
									align : "center",
									halign : 'center'
								},
								{
									field : 'STARTUSING',
									title:'是否启用',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										var result = '<input type="checkbox" class="al-toggle-button dj" checked="false">' ;
										var result2 = '<input type="checkbox" class="al-toggle-button dj" checked="false" disabled="true">' ;
										var result3 = '<input type="checkbox" class="al-toggle-button dj" >' ;
										var result4 = '<input type="checkbox" class="al-toggle-button dj"  disabled="true">' ;
										if(row.STARTUSING=="Y"){
											return [result].join('');
											
										}else{
											return [result3].join('');
										}
										
									},
									 events : {
											'click .dj' : function(e,value, row, index) {
												/*if(row.STATUS != "审核成功"){
													layer.msg("只有审核成功的才能设置是否启用!");
										    		return false;
												}*/
												var startusing=e.currentTarget.checked==true?"Y":"N";
												$.get(_basepath+"pay/updatePayItem.json?startusing="+startusing+"&PKID="+row.PKID,function(data){
													if(data.result=="success"){
														layer.msg("修改成功!");
														 $("#otherpaytable").bootstrapTable('refresh', {url:url});
													}
												});
											}
									 }
								},
								{
									field : 'opt',
									title : '操作',
									align : "center",
									halign : 'center',
									formatter : function(value, row,
											index) {
										
										var resultUpdate = "&nbsp;";
										var resultUpdateItem = "&nbsp;";
										var resultSee = '<span class="see2 fa fa-gear" title="查看缴费设置"></span>&nbsp;&nbsp;';
										var resultDel = '<span class="del fa fa-trash-o" title="删除"></span>&nbsp;&nbsp;';
										var resultDel2 = "&nbsp;";
										var resultImport = "&nbsp;";
										var resultImport2 = "&nbsp;";
										if(row.ITEMLIST_CREATEMODE=="系统根据规则生成"){
											resultUpdate = '<span class="edit fa fa-pencil" title="修改缴费设置"></span>&nbsp;&nbsp;';
										}
									/*	if(row.STATUS == '待审核'){
											if(row.ITEMLIST_CREATEMODE=="系统根据规则生成"){
												resultUpdate = '<span class="edit fa fa-pencil" title="修改缴费设置"></span>&nbsp;&nbsp;';
											}
											resultSee = "&nbsp;";
											resultDel2 = resultDel;
											if(row.IS_INCLUDE_CHILD == '是'){
												resultImport ='';
											}else if(row.PARENT_ID == null){
												resultImport ='<span class="import-list fa fa-sign-in" title="导入缴费名单"></span>&nbsp;&nbsp;';
											}else{
												resultImport ='';
											}
											
												
										}
										if(row.STATUS == '审核成功'){
											resultUpdateItem = '<span class="editItemName fa fa-pencil" title="修改项目名称"></span>&nbsp;&nbsp;';
											if(row.IS_INCLUDE_CHILD == '是'){
												resultImport ='';
											}else if(row.PARENT_ID == null){
												resultImport ='<span class="import-list fa fa-sign-in" title="导入缴费名单"></span>&nbsp;&nbsp;';
												resultImport2 ='<span class="import-paid-list fa fa-download" title="导入已缴名单"></span>&nbsp;&nbsp;';
											}else{
												resultImport ='';
											}
											
											
										}*/
										return [
										        /*SESSION_MENU_BUTTONS.jfgl_xgxmmc == 1?resultUpdateItem:'',
										        SESSION_MENU_BUTTONS.jfgl_sgjfsz == 1?resultUpdate:'',
										        resultSee,*/
										        resultUpdate,
												'<span class="see fa fa-file-text-o" title="查看缴费名单"></span>&nbsp;&nbsp;',
												resultDel
												/*SESSION_MENU_BUTTONS.jfgl_drjfmd == 1?resultImport:'',
												SESSION_MENU_BUTTONS.jfgl_dryjmd == 1?resultImport2:'',
												SESSION_MENU_BUTTONS.jfgl_sc == 1?resultDel2:'']*/
												]
												.join('');
										
									}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
									 events : {
										'click .edit' : function(e,value, row, index) {
											$('#pkidItem').val(row.PKID);
											$('#top_pay_style').val(row.STYLE_NAME);
											$('#top_pay_style').attr("payStyle",row.PAY_TYPE_PKID);
											$('#top_grade').val(row.GRADE_NAME);
											$('#top_grade').attr("grade",row.GRADE_PKID);
											$('#top_classType').val(row.CLASSTYPE_NAME);
											$('#top_classType').attr("classType",row.CLASSTYPE_PKID);
											if(row.ITEMLIST_CREATEMODE=="系统根据规则生成"){
												$("#gzhdr").val("2");
												 $("#xgdrmd").css('display','none');
												 $("#xgjfsz").css('display','block'); 
												 var url=_basepath+"pay/updatejfgz.json";
													$.ajax({
														type:"post",
														dataType:"json",
														async: false,
														data:{PKID:row.PKID},
														url:url,
														 success:function(data){
															 if(data.result=="success"){
															    $('#mymodal2').modal({
																	keyboard: true
															    });
																var dataset= data.json;
																$("#bzfy").val(dataset[0].COST);
																fbArray = [];
																$("#feiyongtiaozhengarea").html("");
																for(var i=0;i<dataset.length;i++){
																	var select_option = "";
																	var youhuiarea = "";//优惠区域显示
																	var zhijian_hidden = "";//直减是否隐藏
																	var zhijian_check = "";//增加还是直减
																	if(dataset[i].YOUHUI_TYPE==0){
																		zhijian_hidden = "hidden";
																	}
																	if(dataset[i].ZENGJIAN_TYPE==0){//表示直减
																		zhijian_check=
																			'<div class="pull-left jf-zhiJian">'+
																			'<input type="radio" checked="checked" value="0" name="xuan'+v+'"/> 直减'+
																			'</div>'+
																			'<div class="pull-left jf-zhiJian">'+
																			'<input type="radio" value="1" name="xuan'+v+'"/> 增长'+
																			'</div>';
																	}else{//表示增加
																		zhijian_check=
																			'<div class="pull-left jf-zhiJian">'+
																			'<input type="radio" value="0" name="xuan'+v+'"/> 直减'+
																			'</div>'+
																			'<div class="pull-left jf-zhiJian">'+
																			'<input type="radio" checked="checked" value="1" name="xuan'+v+'"/> 增长'+
																			'</div>';
																	}
																	if(dataset[i].YOUHUI_TYPE==0){
																		if($('#top_pay_style').val()=='预交'){
																			select_option = 
																				'<option selected="selected" value="0">无</option>'+
																				'<option value="2">报名时间</option>';
																		}else{
																			select_option = 
																				'<option selected="selected" value="0">无</option>'+
																				'<option value="1">合作学校</option>'+
																				'<option value="2">报名时间</option>'+
																				'<option value="3">预交</option>'+
																				'<option value="4">联考成绩</option>'+
																				'<option value="5">班级</option>'+
																				'<option value="6">美院</option>';
																		}
																	}else if(dataset[i].YOUHUI_TYPE==1){//表示合作学校
																		select_option = 
																			'<option value="0">无</option>'+
																			'<option selected="selected" value="1">合作学校</option>'+
																			'<option value="2">报名时间</option>'+
																			'<option value="3">预交</option>'+
																			'<option value="4">联考成绩</option>'+
																			'<option value="5">班级</option>'+
																			'<option value="6">美院</option>';
																		var url = _basepath+'pay/gethzxxListAdd.json';
																		var html = "";
																		$.ajax({
																			type:'post',
																			dataType:'json',
																			async:false, //设置为同步
																			url:url,
																			data:{hzxy:dataset[i].HZXY},
																			success:function(data){
																				var list = data.list;
																				var hezuoxuexiao = dataset[i].HZXY.split(',');
																				for(var k=0;k<list.length;k++){
																					var option_select = "";
																					for(var j=0;j<hezuoxuexiao.length-1;j++){
																						if(list[k].PKID==hezuoxuexiao[j]){
																							option_select = '<option selected="selected" value="'+list[k].PKID+'">'+list[k].SCHOOLNAME+'</option>';
																							break;
																						}else if(list[k].ISHEZUO=='Y'){
																							option_select = '<option value="'+list[k].PKID+'">'+list[k].SCHOOLNAME+'</option>';
																						}
																					}
																					html = html+option_select;
																				}
																			},
																			error: function (XMLHttpRequest, textStatus, errorThrown) { 
																		         alert(errorThrown); 
																			}
																		});
																		youhuiarea='<select class="hzxy_select" multiple="multiple">'+								
																		html+'</select>';
																	}else if(dataset[i].YOUHUI_TYPE==2){//表示报名时间
																		if($('#top_pay_style').val()=='预交'){
																			select_option = 
																				'<option value="0">无</option>'+
																				'<option selected="selected" value="2">报名时间</option>';
																		}else{
																			select_option = 
																				'<option value="0">无</option>'+
																				'<option value="1">合作学校</option>'+
																				'<option selected="selected" value="2">报名时间</option>'+
																				'<option value="3">预交</option>'+
																				'<option value="4">联考成绩</option>'+
																				'<option value="5">班级</option>'+
																				'<option value="6">美院</option>';
																		}
																		youhuiarea='<input class="form-control baomingtimeq" id="baomingtimeq'+bm_index+'" value="'+dataset[i].BAOMINGTIMEQ+'" type="text" placeholder="起始时间"'+
																		'style="width:120px !important;" readonly="readonly" /> 至'+
																		'<input class="form-control baomingtimez" id="baomingtimez'+bm_index+'" value="'+dataset[i].BAOMINGTIMEZ+'" type="text" placeholder="终止时间" style="width:120px !important;" readonly="readonly" /> '+
																		'<span class="jf-zhu">注：包含所选时间当天</span>';
																	}else if(dataset[i].YOUHUI_TYPE==3){//表示预交
																		select_option = 
																			'<option value="0">无</option>'+
																			'<option value="1">合作学校</option>'+
																			'<option value="2">报名时间</option>'+
																			'<option selected="selected" value="3">预交</option>'+
																			'<option value="4">联考成绩</option>'+
																			'<option value="5">班级</option>'+
																			'<option value="6">美院</option>';
																	}else if(dataset[i].YOUHUI_TYPE==4){//联考成绩
																		select_option = 
																			'<option value="0">无</option>'+
																			'<option value="1">合作学校</option>'+
																			'<option value="2">报名时间</option>'+
																			'<option value="3">预交</option>'+
																			'<option selected="selected" value="4">联考成绩</option>'+
																			'<option value="5">班级</option>'+
																			'<option value="6">美院</option>';
																		var checked_bx = "";
																		var checked_wx = "";
																		var cengci = dataset[i].CENGCI.split(",");
																		for(var j=0;j<cengci.length-1;j++){
																			if(cengci[j]=='bxfks'){
																				checked_bx = 'checked="checked"';
																			}else if(cengci[j]=='wxfks'){
																				checked_wx = 'checked="checked"';
																			}
																		}
																		youhuiarea = 
																			'<div class="form-inline">'+
																			'<label class="pull-left" for=""> 学生类型：</label>'+
																			'<div class="pull-left cengCi"><input type="checkbox" '+checked_bx+' value="bxfks"/> 本校复课生</div>'+
																			'<div class="pull-left cengCi"><input type="checkbox" '+checked_wx+' value="wxfks"/> 外校复课生</div>'+
																			'<div class="clearfix"></div>'+
																		'</div>'+
																		'<div class="form-inline">'+
																			'<label class="pull-left jf-fenShu" for=""> 分数：</label>'+
																			'<input class="form-control liankaozuidi" type="text" placeholder="最低分" value="'+dataset[i].ZUIDIFEN+'" style=" width:100px !important;"/> 至'+
																			'<input class="form-control liankaozuigao" type="text" placeholder="最高分" value="'+dataset[i].ZUIGAOFEN+'" style="width:100px !important;"/> '+
																			'<span class="jf-zhu">注：包含所填分数</span>'+
																			'<div class="clearfix"></div>'+
																		'</div>';
																	}else if(dataset[i].YOUHUI_TYPE==5){//班级
																		select_option = 
																			'<option value="0">无</option>'+
																			'<option value="1">合作学校</option>'+
																			'<option value="2">报名时间</option>'+
																			'<option value="3">预交</option>'+
																			'<option value="4">联考成绩</option>'+
																			'<option selected="selected" value="5">班级</option>'+
																			'<option value="6">美院</option>';
																		var classPkids = dataset[i].T_CLASS_PKIDS.split(",");//班级pkids
																		var url = _basepath+'pay/getClassList.json';
																		var grade = $("#top_grade").attr("grade");//入学年份pkid
																		var classType = $("#top_classType").attr("classType");//班型pkid
																		var html = "";
																		$.ajax({
																			type:'post',
																			dataType:'json',
																			async:false, //设置为同步
																			url:url,
																			data:{"GRADE":grade,"CLASSTYPE":classType},
																			success:function(data){
																				var list = data.list;
																				for(var j=0;j<list.length;j++){
																					var check_class = "";
																					for(var k=0;k<classPkids.length-1;k++){
																						if(classPkids[k]==list[j].PKID){
																							check_class='checked="checked"';
																						}
																					}
																					if(j%3==0){
																						html = html+'<div class="form-inline">';
																					}
																					html = html + '<div class="pull-left cengCi banjiarea"><input '+check_class+' value="'+list[j].PKID+'" type="checkbox"/>'+list[j].CLASS_NAME+'</div>';
																					if((j>0 && (j+1)%3==0) || (j==(list.length-1))){
																						html = html+'<div class="clearfix"></div>'+'</div>';
																					}
																				}
																			},
																			error: function (XMLHttpRequest, textStatus, errorThrown) { 
																		          alert(errorThrown); 
																			}
																		});
																		youhuiarea = html;
																	}else if(dataset[i].YOUHUI_TYPE==6){//美院
																		select_option = 
																			'<option value="0">无</option>'+
																			'<option value="1">合作学校</option>'+
																			'<option value="2">报名时间</option>'+
																			'<option value="3">预交</option>'+
																			'<option value="4">联考成绩</option>'+
																			'<option value="5">班级</option>'+
																			'<option selected="selected" value="6">美院</option>';
																		var url = _basepath+'pay/getMeiYuanList.json';
																		var html = "";
																		var deptPkids = dataset[i].SYS_MEI_DEPARTMENT_PKIDS.split(",");//美院pkids
																		$.ajax({
																			type:'post',
																			dataType:'json',
																			async:false, //设置为同步
																			url:url,
																			data:{},
																			success:function(data){
																				var list = data.list;
																				for(var j=0;j<list.length;j++){
																					var check_class = "";
																					for(var k=0;k<deptPkids.length-1;k++){
																						if(deptPkids[k]==list[j].PKID){
																							check_class='checked="checked"';
																						}
																					}
																					if(j%4==0){
																						html = html+'<div class="form-inline">';
																					}
																					html = html + '<div class="pull-left cengCi meiyuanarea"><input '+check_class+' value="'+list[j].PKID+'" type="checkbox"/>'+list[j].NAME+'</div>';
																					if((j>0 && (j+1)%4==0) || (j==(list.length-1))){
																						html = html+'<div class="clearfix"></div>'+'</div>';
																					}
																				}
																			},
																			error: function (XMLHttpRequest, textStatus, errorThrown) { 
																		          alert(errorThrown); 
																			}
																		});
																		youhuiarea = html;
																	}
													
																	html_select_area='<div class="form-inline jf-feiYong">'+
																	'<div class="form-group pull-left">'+
																	'<label for="" style="margin-right: 4px;"> 费用调整</label>'+
																	'<select name="" class="form-control guizeshezhi_select">'+
																	select_option+
																	'</select> '+
																	'</div>'+
																	'<div class="zengjianarea" '+zhijian_hidden+'>'+
																	zhijian_check+
																		'<input class="form-control jianjiajine" type="text" placeholder="金额" value="'+dataset[i].ZENGJIAN_JINE+'" style="width:70px !important;"/> '+
																	'</div>'+
																	'<span class="fa fa-minus-square-o jianguize" style="margin-left:25px;line-height:42px;"></span>'+
																	'<div class="clearfix"></div>'+
																	'</div>'+
																	'<div class="form-inline youhuiarea" style="margin-left:65px;">'+
																	youhuiarea+
																	'<div class="clearfix"></div> '+
																	'</div>';
																	$("#feiyongtiaozhengarea").append(html_select_area);
																	$('.hzxy_select').multiselect({
																		enableFiltering: true,
																		includeSelectAllOption: true,
																		selectAllText: ' 全选',
																		nonSelectedText: '请选择合作学校',
																		allSelectedText: '全部选择',
																		selectAllJustVisible: true,
																		filterPlaceholder:'请输入合作学校',
																		buttonWidth: '183px'//设置字体过长显示...
																	});
																	laydate.render({
																		elem:'#baomingtimeq'+bm_index,
																		isclear: true, //是否显示清空
																		istoday: true, //是否显示今天
																		type: 'date',
																		format: 'yyyy-MM-dd',
																		festival: true, //显示节日
																	});
																	laydate.render({
																		elem:'#baomingtimez'+bm_index,
																		isclear: true, //是否显示清空
																		istoday: true, //是否显示今天
																		type: 'date',
//																		min: 0,
																		format: 'yyyy-MM-dd',
																		festival: true, //显示节日
//																		start: 0
																	});
																	if(dataset[i].YOUHUI_TYPE==2){//表示报名时间
																		bm_index++;
																	}
																	v++;
																}
																$(".guizeshezhi_select").change(function(){
																	pm.guizeshezhi($(this));
																});
																$(".jianguize").click(function(){
																	pm.jianguize($(this));
																});
																}
														  },
														error: function (XMLHttpRequest, textStatus, errorThrown) { 
														          alert(errorThrown); 
														}
													});
											}
										},
										'click .editItemName' : function(e,value, row, index) {
											BootstrapDialog.show({  //显示需要提交的表单。
								            	title:'修改缴费项目名称',	
									            message: $('<div></div>').load("pay/toUpdateItemName.json?PKID="+row.PKID),
									            closable: true, 
								                buttons: [{
											    label: '确定',
											    cssClass: 'btn-danger',
											    action: function(dialogRef){
											    	var PAYITEM = $("#PAYITEMUPDATE").val();
											    	if(PAYITEM=='' || PAYITEM == null){
											    		layer.msg("项目名称不能为空！");
											    		return false;
											    	}
											    	 $.ajax({
															type:"post",
															dataType:"json",
															data:{"PAYITEM":PAYITEM},
															url:_basepath+'pay/updateItemName.json?PKID='+row.PKID,
															 success:function(data){
																 if("success" == data.result){
																	 layer.msg("修改成功！");
																	 //删除成功后刷新表格
																	  $("#paymanagetable").bootstrapTable('refresh', {url:url});
																 }else{
																	 layer.msg("修改失败！");
																 }
															  },
															error: function (XMLHttpRequest, textStatus, errorThrown) { 
															          alert(errorThrown);
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
										},
										'click .see2' : function(e,value, row, index) {
											var r = Math.random();
											var drhgz;
											if(row.ITEMLIST_CREATEMODE=="系统根据规则生成"){
												drhgz=2;
											}else if(row.ITEMLIST_CREATEMODE=="导入缴费名单"){
												drhgz=1;
											}
											
											$('.jf_szright').load(_basepath+'pay/goShow.json?PKID='+row.PKID+'&ITEMLIST_CREATEMODE='+drhgz+'&xgym='+'Y');
										},
										'click .see' : function(e,value, row, index) {
											var r = Math.random();
											$('.jf_szright').load(encodeURI('pay/seePayItemList.json?payItemPkid='+row.PKID+'&payitem='+row.PAYITEM+'&status='+row.STATUSNO+'&IS_INCLUDE_CHILD='+row.IS_INCLUDE_CHILD+'&r='+r+'&JFMDFS='+row.JFMDFS));
										},
										'click .import-list' : function(e,value, row, index) {
											$('#pkid').val(row.PKID);
											pm.openImportlist(row.PKID);
										},
										'click .import-paid-list' : function(e,value, row, index) {
											$('#pkid').val(row.PKID);
											pm.openImportPaidlist(row.PKID);
										},
										'click .del' : function(e,value, row, index) {
											/*if(row.BGXLX!="自定义"){
									    		layer.msg("系统自带的缴费项不能删除!")
									    		return false;
									    	}*/
											BootstrapDialog.show({  //显示需要提交的表单。
								            	title:'提示信息',	
								            message: '你确定要删除这条记录吗？',
								            closable: true, 
								              buttons: [{
											    label: '确定',
											    cssClass: 'btn-danger',
											    action: function(dialogRef){
											    	 $.ajax({
															type:"post",
															dataType:"json",
															url:_basepath+'pay/deletePayItem.json?payItemPkid='+row.PKID,
															 success:function(data){
																 if("success" == data.result){
																	 layer.msg("删除成功！");
																	 //删除成功后刷新表格
																	  $("#paymanagetable").bootstrapTable('refresh', {url:url});
																 }else if("fail" == data.result){
																	 layer.msg("删除失败！原因：有关联数据！");
																 }else if("cunzai" == data.result){
																	 layer.msg("删除失败！原因：已有学生生成订单！");
																 }
															  },
															error: function (XMLHttpRequest, textStatus, errorThrown) { 
															          alert(errorThrown);
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
											
											
										    
			 
										},
											} 
								}
						],
					});
    $('#paymanagetable').bootstrapTable('hideColumn', 'PKID');//隱藏列
//    $('#paymanagetable').bootstrapTable('hideColumn', 'STARTDATE');
//    $('#paymanagetable').bootstrapTable('hideColumn', 'ENDDATE');
    $('#paymanagetable').bootstrapTable('hideColumn', 'JFMDFS');
    
//    $('#paymanagetable').bootstrapTable('hideColumn', 'PARENT_ID');
  /*  if(colStr!=null && colStr!=''){
		var resultColumnList = $('#paymanagetable').bootstrapTable('getVisibleColumns');
		//隐藏全部
		$.each(resultColumnList, function(i, column) {
			if(column.field=='0'||column.field=='opt'){
				return;
			}
			 $('#paymanagetable').bootstrapTable('hideColumn', column.field);
     	});		
		//展示数据库查询出来的列
		var array=colStr.split(",");
		for(var i=0;i<array.length;i++){
			var field=array[i];
			if(field==''){
				continue;
			}
			$('#paymanagetable').bootstrapTable('showColumn', field);
			
		}
		
	}*/
//    $('#paymanagetable').bootstrapTable('hideColumn', 'STATUSNO');//隐藏状态栏(数字)
   
};

//查看某一条缴费记录信息
var seeRecordDetail = function(){
	
}
//点击保存
/*$('#btn_save').click(function(){
	pm.save();
});*/

//点击保存
$('#btn_save2').click(function(){
	pm.savefabu();
});
pm.savefabu=function(){
	var T_PAY_ITEM_PKID = $('#pkidItem').val();//缴费项目PKID
	//导入或规则
	var ITEMLIST_CREATEMODE;
	ITEMLIST_CREATEMODE=2;
	var bzfy = $("#bzfy").val();
	var fix_Test=/^(([1-9]\d*)|\d)(\.\d{1,2})?$/;
	if(bzfy==null || bzfy==''){
		layer.msg("请先填写标准费用！");
		return false;
	}else if(!fix_Test.test(bzfy)){
		layer.msg("请输入有效金额！");
		$("#bzfy").focus();
		return false;
	}
	var guize_flag = true;
	var index_yujiao = 0;//用来判断预交规则有几个
	fbArray = new Array();
	$("#feiyongtiaozhengarea").find(".guizeshezhi_select").each(function(){
		var fb_obj = {};
		if($(this).val()=='1'){//表示是合作学校，则必须选择学校
			fb_obj["guizetype"]=1;
			var hzxy = $(this).parent().parent().next().find(".hzxy_select").val();
			var jianjiajine = $(this).parent().parent().find(".jianjiajine").val();
			var jianjiaType = $(this).parent().parent().find("input[type='radio']:checked").val();
			if(jianjiajine==null || jianjiajine ==''){
				layer.msg("请先填写金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}else if(!fix_Test.test(jianjiajine)){
				layer.msg("请输入有效金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}else if(jianjiajine*1 > bzfy*1 && jianjiaType == 0){
				layer.msg("优惠金额不能超过标准金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}
			fb_obj["jianjiaType"]=jianjiaType;
			fb_obj["jianjiajine"]=jianjiajine;
			var hzxy_str='';
  			if(hzxy!=null){
  				for(var i=0;i<hzxy.length;i++){
  					hzxy_str+=hzxy[i]+',';
      			}
  				fb_obj["hzxy"]=hzxy_str;
  				fbArray.push(fb_obj);
  			}else{
  				layer.msg("请先选择合作学校！");
  				guize_flag = false;
				return guize_flag;
  			}
		}else if($(this).val()=='2'){//表示报名时间
			fb_obj["guizetype"]=2;
			var jianjiajine = $(this).parent().parent().find(".jianjiajine").val();
			var jianjiaType = $(this).parent().parent().find("input[type='radio']:checked").val();
			if(jianjiajine==null || jianjiajine ==''){
				layer.msg("请先填写金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}else if(!fix_Test.test(jianjiajine)){
				layer.msg("请输入有效金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}else if(jianjiajine*1 > bzfy*1 && jianjiaType == 0){
				layer.msg("优惠金额不能超过标准金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}
			fb_obj["jianjiaType"]=jianjiaType;
			fb_obj["jianjiajine"]=jianjiajine;
			var bmsjq = $(this).parent().parent().next().find(".baomingtimeq").val();//报名时间起
			var bmsjz = $(this).parent().parent().next().find(".baomingtimez").val();//报名时间止
			if(bmsjq==null || bmsjq==''){
				layer.msg("请先选择报名时间起！");
				guize_flag = false;
				return guize_flag;
			}
			if(bmsjz==null || bmsjz==''){
				layer.msg("请先选择报名时间止！");
				guize_flag = false;
				return guize_flag;
			}
			fb_obj["baomingtimeq"]=bmsjq;
			fb_obj["baomingtimez"]=bmsjz;
			fbArray.push(fb_obj);
		}else if($(this).val()=='3'){//表示预交
			index_yujiao++;
			if(index_yujiao>1){
				layer.msg("预交规则最多只能添加一个！");
				guize_flag = false;
				return guize_flag;
			}
			fb_obj["guizetype"]=3;
			var jianjiajine = $(this).parent().parent().find(".jianjiajine").val();
			var jianjiaType = $(this).parent().parent().find("input[type='radio']:checked").val();
			if(jianjiajine==null || jianjiajine ==''){
				layer.msg("请先填写金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}else if(!fix_Test.test(jianjiajine)){
				layer.msg("请输入有效金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}else if(jianjiajine*1 > bzfy*1 && jianjiaType == 0){
				layer.msg("优惠金额不能超过标准金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}
			fb_obj["jianjiaType"]=jianjiaType;
			fb_obj["jianjiajine"]=jianjiajine;
			fbArray.push(fb_obj);
		}else if($(this).val()=='4'){//表示联考成绩
			fb_obj["guizetype"]=4;
			var jianjiajine = $(this).parent().parent().find(".jianjiajine").val();
			var jianjiaType = $(this).parent().parent().find("input[type='radio']:checked").val();
			if(jianjiajine==null || jianjiajine ==''){
				layer.msg("请先填写金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}else if(!fix_Test.test(jianjiajine)){
				layer.msg("请输入有效金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}else if(jianjiajine*1 > bzfy*1 && jianjiaType == 0){
				layer.msg("优惠金额不能超过标准金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}
			fb_obj["jianjiaType"]=jianjiaType;
			fb_obj["jianjiajine"]=jianjiajine;
			var cengci_str = "";
			$(this).parent().parent().next().find("input[type='checkbox']:checked").each(function(){
				cengci_str=cengci_str+$(this).val()+",";
			});
			if(cengci_str==null || cengci_str==''){
				layer.msg("请先选择学生类型！");
				guize_flag = false;
				return guize_flag;
			}
			fb_obj["cengci_str"]=cengci_str;
			var liankaozuigao = $(this).parent().parent().next().find(".liankaozuigao").val();//联考最高分
			var liankaozuidi = $(this).parent().parent().next().find(".liankaozuidi").val();//联考最低分
			if(liankaozuigao==null || liankaozuigao==''){
				layer.msg("请先填写最高分数！");
				$(this).parent().parent().next().find(".liankaozuigao").focus();
				guize_flag = false;
				return guize_flag;
			}
			if(liankaozuidi==null || liankaozuidi==''){
				layer.msg("请先填写最低分数！");
				$(this).parent().parent().next().find(".liankaozuidi").focus();
				guize_flag = false;
				return guize_flag;
			}
			fb_obj["liankaozuigao"]=liankaozuigao;
			fb_obj["liankaozuidi"]=liankaozuidi;
			fbArray.push(fb_obj);
		}else if($(this).val()=='5'){//表示班级
			fb_obj["guizetype"]=5;
			var jianjiajine = $(this).parent().parent().find(".jianjiajine").val();
			var jianjiaType = $(this).parent().parent().find("input[type='radio']:checked").val();
			if(jianjiajine==null || jianjiajine ==''){
				layer.msg("请先填写金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}else if(!fix_Test.test(jianjiajine)){
				layer.msg("请输入有效金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}else if(jianjiajine*1 > bzfy*1 && jianjiaType == 0){
				layer.msg("优惠金额不能超过标准金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}
			fb_obj["jianjiaType"]=jianjiaType;
			fb_obj["jianjiajine"]=jianjiajine;
			var banji_str = "";
			$(this).parent().parent().next().find("input[type='checkbox']:checked").each(function(){
				banji_str=banji_str+$(this).val()+",";
			});
			if(banji_str==null || banji_str==''){
				layer.msg("请先选择班级！");
				guize_flag = false;
				return guize_flag;
			}
			fb_obj["banji_str"]=banji_str;
			fbArray.push(fb_obj);
		}else if($(this).val()=='6'){//表示美院
			fb_obj["guizetype"]=6;
			var jianjiajine = $(this).parent().parent().find(".jianjiajine").val();
			var jianjiaType = $(this).parent().parent().find("input[type='radio']:checked").val();
			if(jianjiajine==null || jianjiajine ==''){
				layer.msg("请先填写金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}else if(!fix_Test.test(jianjiajine)){
				layer.msg("请输入有效金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}else if(jianjiajine*1 > bzfy*1 && jianjiaType == 0){
				layer.msg("优惠金额不能超过标准金额！");
				$(this).parent().parent().find(".jianjiajine").focus();
				guize_flag = false;
				return guize_flag;
			}
			fb_obj["jianjiaType"]=jianjiaType;
			fb_obj["jianjiajine"]=jianjiajine;
			var meiyuan_str = "";
			$(this).parent().parent().next().find("input[type='checkbox']:checked").each(function(){
				meiyuan_str=meiyuan_str+$(this).val()+",";
			});
			if(meiyuan_str==null || meiyuan_str==''){
				layer.msg("请先选择班级！");
				guize_flag = false;
				return guize_flag;
			}
			fb_obj["meiyuan_str"]=meiyuan_str;
			fbArray.push(fb_obj);
		}else{
			fb_obj["guizetype"]=0;
			fb_obj["jianjiaType"]=0;
			fb_obj["jianjiajine"]=0;
			fbArray.push(fb_obj);
		}
	});
	if(!guize_flag){
		return false;
	}
	if(fbArray.length<1){
		layer.msg("至少添加一条规则！");
		return false;
	}
	var successList=$("#list").val();
	var xiugai="Y";
	var data={successList:successList,ITEMLIST_CREATEMODE:ITEMLIST_CREATEMODE,fbArray:JSON.stringify(fbArray),xiugai:xiugai,bzfy:bzfy,"pkid":T_PAY_ITEM_PKID};
	var url=_basepath+"pay/fabusave.json";
	$.ajax({
		type:"post",
		dataType:"json",
		data:data,
		url:url,
		 success:function(data){
			 if(data.result=="success"){
				 layer.msg("修改项目成功!");
				 $("#list").val("");
				 $('#btn_cancel2').click();
				 fbArray = [];
				 var u=_basepath+"pay/paymanagetable.json";
				 $("#paymanagetable").bootstrapTable('refresh',{url:u});
				}
			 
		  },
		error: function (XMLHttpRequest, textStatus, errorThrown) { 
		          alert(errorThrown); 
		}
	});
};
$('#btn_search').click(function(){
	//开始日期
	/*var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	
	if(new Date(Date.parse(startDate)) > new Date(Date.parse(endDate))){
		layer.msg("开始日期不能大于结束日期!");
		return false;
	}*/
	//查询条件-是否启用
	var ruxuenianfen = $("#ruxuenianfen  option:selected").val();
	var banxing = $("#banxing  option:selected").val();
	var pay_style_gl = $("#pay_style_gl  option:selected").val();
	//查询条件-审核状态
	var status = $("#statusselect  option:selected").val();
	//查询条件-项目名称
//	var payitem = $("#payitemselect").val();
	/*var payitem = $("#orgtree2").val();
	var IS_FQ = $("#IS_FQ").val();*/
	var url = _basepath+"pay/paymanagetable.json";
   	 var opt = {
		url :url,
	    silent: true,
	    query:{
	    	"GRADE_PKID":ruxuenianfen,
	    	"CLASSTYPE_PKID":banxing,
	    	"PAY_TYPE_PKID":pay_style_gl
	    	
	    }
	  };
	 $("#paymanagetable").bootstrapTable('refresh', opt);
   
});
/**
 * 弹出 点击 导入缴费名单
 */
pm.openImportlist = function(item_pkid){
	
//	if("undefined" == typeof item_pkid){
//		item_pkid = "";
//	}
	
	var dialog = BootstrapDialog.show({
		title:'导入缴费名单',
		message: $('<div></div>').load("import/import-list.json?item_pkid="+item_pkid),
		closable: true,//是否显示叉号
		draggable: true,//可以拖拽
		buttons: [{
            label: '关闭',
            cssClass: 'btn-danger',
            action: function(dialog) {
            	dialog.close();
//            	$(".jf_szright").load(_basepath + "pay/paymanage.php");
            	$('#btn_search').click();
            }
        }],
        onshow: function(dialogRef){
        	//内容加载完成，但是弹出层UI还没有显示时调用
        },
        onshown: function(dialogRef){
        	//内容加载完成，弹出层UI显示时调用
        },
        onhide: function(dialogRef){
        	//将要关闭时调用。
        },
        onhidden: function(dialogRef){
        	//UI关闭后低啊用
        }
	});
	
};

$("#btn_import_list").click(function(){
	pm.openImportlist("");
});
/**
 * 弹出 点击 导入已缴费名单
 */
pm.openImportPaidlist = function(payitem){
	
	var dialog = BootstrapDialog.show({
		title:'导入已缴名单',
		message: $('<div></div>').load("import/importpaidlist.json?item_pkid="+payitem),
		closable: true,//是否显示叉号
		draggable: true,//可以拖拽
		buttons: [{
            label: '关闭',
            cssClass: 'btn-danger',
            action: function(dialog) {
            	dialog.close();
            	$('#btn_search').click();
            	return false;
            }
        }]
	});
	
};
$("#btn_import_already_list").click(function(){
	pm.openImportPaidlist("");
});




$(function(){
	pm.getTab();
	
	
	
	
});
