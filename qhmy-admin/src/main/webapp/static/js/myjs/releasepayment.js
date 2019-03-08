
//@ sourceURL=releasepayment.js
//发布缴费项页面js
(function($, window) {
	var op={};
	var fbArray = new Array();//发布缴费对象
	$.ajaxSetup({cache : false });
	$("#daorumd").attr("checked","checked");
	$("#jfszbtn").attr({"disabled":"disabled"});
	$("#bzfy").attr("disabled",true);
	$(".guizeshezhi_select").attr("disabled",true);
	$("#addfytz").attr("disabled",true);
	$("#top_pay_style").change(function(){//缴费类型变化
		op.indefyItem();
		op.yujiaoOption();
	});
	$("#top_grade").change(function(){//入学年份变化
		op.indefyItem();
		op.updateClassList();
	});
	$("#top_classType").change(function(){//班型变化
		op.indefyItem();
		op.updateClassList();
	});
	var flag_save = true;
	//根据缴费类型筛选规则下拉
	op.yujiaoOption = function(){
		var top_pay_style = $("#top_pay_style").find("option:selected").text();//缴费类型
		if(top_pay_style.trim()=='预交'){
			$(".guizeshezhi_select").empty();
			$(".guizeshezhi_select").append('<option value="">无</option><option value="bmsj">报名时间</option>');
		}else{
			$(".guizeshezhi_select").empty();
			$(".guizeshezhi_select").append('<option value="">无</option>'+
			'<option value="hzxy">合作学校</option>'+
			'<option value="bmsj">报名时间</option>'+
			'<option value="yujiao">预交</option>'+
			'<option value="lkcj">联考成绩</option>'+
			'<option value="banji">班级</option>'+
			'<option value="meiyuan">美院</option>');
		}
	};
	op.yujiaoOption();
	//入学年份和班型下拉变更时，刷新班级列表
	op.updateClassList = function(){
		var top_grade = $("#top_grade").val();//入学年份
		var top_classType = $("#top_classType").val();//班型
		var url = _basepath+'pay/getClassList.json';
		$.ajax({
			type:"post",
			dataType:"json",
			data:{"GRADE":top_grade,"CLASSTYPE":top_classType},
			url:url,
			 success:function(data){
				var list = data.list;
				$("#feiyongtiaozhengarea").find(".guizeshezhi_select").each(function(){
					var obj = $(this);
					if(obj.val()=='banji'){
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
					}
				});
			  },
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
			          alert(errorThrown); 
			}
		});
	};
	//验证缴费项目是否已经发布
	op.indefyItem = function(){
		var top_pay_style = $("#top_pay_style").val();//缴费类型
		var top_grade = $("#top_grade").val();//入学年份
		var top_classType = $("#top_classType").val();//班型
		var url=_basepath+"pay/indefyItem.json";
		$.ajax({
			type:"post",
			dataType:"json",
			data:{"PAY_TYPE_PKID":top_pay_style,"GRADE_PKID":top_grade,"CLASSTYPE_PKID":top_classType},
			url:url,
			 success:function(data){
				 if(data.result=="success"){//表示查到有发布的
					 flag_save = false;
					 layer.msg("该规则缴费项目已经发布!");
				 }else if(data.result=="error"){//表示查不到有发布同规则的
					 flag_save = true;
				 }
				 
			  },
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
			          alert(errorThrown); 
			}
		});
	};
	$("#btn_import_list").click(function(){
		var pay_style = $("#top_pay_style").val();//缴费类型
		var grade = $("#top_grade").val();//入学年份
		var classType = $("#top_classType").val();//班型
		if(pay_style==null || ""==pay_style){
			layer.msg("请先选择缴费类型！");
			return false;
		}
		if(grade==null || ""==grade){
			layer.msg("请先选择入学年份！");
			return false;
		}
		if(classType==null || ""==classType){
			layer.msg("请先选择班型！");
			return false;
		}
		op.openImportlist("");
	});
	var v = 1;
	var html_select_area = "";
	function html_select_areaa_yj(v){
		html_select_area='<div class="form-inline jf-feiYong">'+
		'<div class="form-group pull-left">'+
		'<label for="" style="margin-right: 4px;"> 费用调整</label>'+
		'<select name="" class="form-control guizeshezhi_select">'+
		'<option value="">无</option>'+
		'<option value="bmsj">报名时间</option>'+
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
		'<option value="">无</option>'+
		'<option value="hzxy">合作学校</option>'+
		'<option value="bmsj">报名时间</option>'+
		'<option value="yujiao">预交</option>'+
		'<option value="lkcj">联考成绩</option>'+
		'<option value="banji">班级</option>'+
		'<option value="meiyuan">美院</option>'+
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
	
	/**
	 * 弹出 点击 导入缴费名单
	 */
	op.openImportlist = function(item_pkid){
		var grade = $("#top_grade").val();//入学年份
		var classType = $("#top_classType").val();//班型
		BootstrapDialog.show({
			title:'导入缴费名单',
			message: $('<div></div>').load("pay/import-list.json?item_pkid="+item_pkid+"&&GRADE_PKID="+grade+"&&CLASSTYPE_PKID="+classType),
			draggable: true,//可以拖拽
			 closable: true,
			buttons: [{
	            label: '关闭',
	            cssClass: 'btn-danger',
	            action: function(dialog) {
	            	dialog.close();
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
	//发布缴费保存方法
	$('#savefabu').click(function(){
		op.indefyItem();
		if(flag_save){
			op.savefabu();
		}else{
			return false;
		}
	});
	op.savefabu=function(){
		var PAY_TYPE_PKID = $("#top_pay_style").val();//缴费类型
		var GRADE_PKID = $("#top_grade").val();//入学年份
		var CLASSTYPE_PKID = $("#top_classType").val();//班型
		if(PAY_TYPE_PKID=='' || PAY_TYPE_PKID==null){
			layer.msg("请先选择缴费类型！");
			return false;
		}
		if(GRADE_PKID=='' || GRADE_PKID==null){
			layer.msg("请先选择入学年份！");
			return false;
		}
		if(CLASSTYPE_PKID=='' || CLASSTYPE_PKID==null){
			layer.msg("请先选择班型！");
			return false;
		}
		var fix_Test=/^(([1-9]\d*)|\d)(\.\d{1,2})?$/;
		var guize_flag = true;
		//导入或规则
		var ITEMLIST_CREATEMODE;
		var selectgz = $("input[type=radio][name=jfszr]:checked").val();
		if (selectgz == 'dr') {
			ITEMLIST_CREATEMODE=1;
		}else if (selectgz == 'gz') {
			fbArray = new Array();
			ITEMLIST_CREATEMODE=2;
			var bzfy = $("#bzfy").val();
			if(bzfy==null || bzfy==''){
				layer.msg("请先填写标准费用！");
				$("#bzfy").focus();
				return false;
			}else if(!fix_Test.test(bzfy)){
				layer.msg("请输入有效金额！");
				$("#bzfy").focus();
				return false;
			}
			var index_yujiao = 0;//用来判断预交规则有几个
			$("#feiyongtiaozhengarea").find(".guizeshezhi_select").each(function(){
				var fb_obj = {};
				if($(this).val()=='hzxy'){//表示是合作学校，则必须选择学校
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
				}else if($(this).val()=='bmsj'){//表示报名时间
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
				}else if($(this).val()=='yujiao'){//表示预交
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
				}else if($(this).val()=='lkcj'){//表示联考成绩
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
					if(liankaozuidi==null || liankaozuidi==''){
						layer.msg("请先填写最低分数！");
						$(this).parent().parent().next().find(".liankaozuidi").focus();
						guize_flag = false;
						return guize_flag;
					}else if(!fix_Test.test(liankaozuidi)){
						layer.msg("请输入有效分数！");
						$(this).parent().parent().next().find(".liankaozuidi").focus();
						guize_flag = false;
						return guize_flag;
					}
					if(liankaozuigao==null || liankaozuigao==''){
						layer.msg("请先填写最高分数！");
						$(this).parent().parent().next().find(".liankaozuigao").focus();
						guize_flag = false;
						return guize_flag;
					}else if(!fix_Test.test(liankaozuigao)){
						layer.msg("请输入有效分数！");
						$(this).parent().parent().next().find(".liankaozuigao").focus();
						guize_flag = false;
						return guize_flag;
					}
					fb_obj["liankaozuigao"]=liankaozuigao;
					fb_obj["liankaozuidi"]=liankaozuidi;
					fbArray.push(fb_obj);
				}else if($(this).val()=='banji'){//表示班级
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
				}else if($(this).val()=='meiyuan'){//表示美院
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
	    }
		if(!guize_flag){
			return false;
		}
		$("#savefabu").attr("disabled","true");
		layer.load(1, {
			  shade: [0.1,'#fff'] //0.1透明度的白色背景
		});
		var successList=$("#list").val();
		var xiugai="N";
		var data={successList:successList,PAY_TYPE_PKID:PAY_TYPE_PKID,GRADE_PKID:GRADE_PKID,CLASSTYPE_PKID:CLASSTYPE_PKID,
					ITEMLIST_CREATEMODE:ITEMLIST_CREATEMODE,fbArray:JSON.stringify(fbArray),xiugai:xiugai,bzfy:bzfy};
		var url=_basepath+"pay/fabusave.json";
		$.ajax({
			type:"post",
			dataType:"json",
			data:data,
			url:url,
			 success:function(data){
				 if(data.result=="success"){
					 layer.msg("发布项目成功!");
					 layer.closeAll('loading');
					 $("#nameli").load(_basepath+"pay/paynav.php");	
					}
				 
			  },
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
			          alert(errorThrown); 
			}
		});
	};
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
	op.jianguize = function(obj){
		obj.parent().next().html("");
		obj.parent().remove();
	};
	var bm_index = 0;//报名参数
	op.guizeshezhi = function(objj){
		var obj = objj;
		var discount_mode = obj.val();
		if(discount_mode=='hzxy'){//表示是合作学校
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
		}else if(discount_mode=='bmsj'){//报名时间
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
//				min: 0,
				format: 'yyyy-MM-dd',
				festival: true, //显示节日
//				start: 0
			});
			bm_index++;
		}else if(discount_mode=='yujiao'){//预交
			obj.parent().parent().find(".zengjianarea").show();
			obj.parent().parent().next().html('');
		}else if(discount_mode=='lkcj'){//联考
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
		}else if(discount_mode=='banji'){//班级
			obj.parent().parent().find(".zengjianarea").show();
			var url = _basepath+'pay/getClassList.json';
			var grade = $("#top_grade").val();//入学年份
			var classType = $("#top_classType").val();//班型
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
		}else if(discount_mode=='meiyuan'){//美院
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
	$(".guizeshezhi_select").change(function(){
		op.guizeshezhi($(this));
	});
	$("#addfytz").click(function(){
		var top_pay_style = $("#top_pay_style").find("option:selected").text();//缴费类型
		if(top_pay_style.trim()=='预交'){
			$("#feiyongtiaozhengarea").append(html_select_areaa_yj(v));
		}else{
			$("#feiyongtiaozhengarea").append(html_select_areaa(v));
		}
		v++;
		$(".guizeshezhi_select").change(function(){
			op.guizeshezhi($(this));
		});
		$(".jianguize").click(function(){
			op.jianguize($(this));
		});
		//op.yujiaoOption();
	});
	//选择规则还是导入 对应的radio
	$('input[type=radio][name=jfszr]').change(function(){
		var selectedvalue = $("input[type=radio][name=jfszr]:checked").val();
		if (selectedvalue == 'dr') {
			$("#bzfy").attr("disabled",true);
			$(".guizeshezhi_select").attr("disabled",true);
			$("#addfytz").attr("disabled",true);
			if($("#bzfy").val()!=null && $("#bzfy").val()!=''){
				BootstrapDialog.show({  //显示需要提交的表单。
					title:'提示信息',	
					message: '若改为导入名单方式会删除对应规则设置？',
					closable: false,
					buttons: [{
						label: '确定',
						cssClass: 'btn-danger',
						action: function(dialogRef){
							$('#btn_import_list').attr("disabled",false);
							$("#jfszbtn").attr({"disabled":"disabled"});
							$("#bzfy").val("");
							if(top_pay_style.trim()=='预交'){
								$("#feiyongtiaozhengarea").append(html_select_areaa_yj(0));
							}else{
								$("#feiyongtiaozhengarea").html(html_select_areaa(0));
							}
							$(".guizeshezhi_select").attr("disabled",true);
							$(".guizeshezhi_select").change(function(){
								op.guizeshezhi($(this));
							});
							dialogRef.close();
						}
					}, {
						label: '取消',
						cssClass: 'btn-default',
						action: function(dialogRef){
							$("#bzfy").attr("disabled",false);
							$(".guizeshezhi_select").attr("disabled",false);
							$("#addfytz").attr("disabled",false);
							$("#daorumd").prop("checked",false);
							$("#guizesc").prop("checked",true); 
							dialogRef.close();
						}
					}
					]
				});
			}else{
				$('#btn_import_list').attr("disabled",false);
			}
	    }
	    else if (selectedvalue == 'gz') {
	    	BootstrapDialog.show({  //显示需要提交的表单。
	        	title:'提示信息',	
		        message: '请先确认缴费类型，点击确定后将无法修改',
		        closable: false,
		          buttons: [{
				    label: '确定',
				    cssClass: 'btn-danger',
				    action: function(dialogRef2){
				    	dialogRef2.close();
				    	$("#top_pay_style").attr("disabled",true);
				    	var successList=$("#list").val();
				    	$("#bzfy").attr("disabled",false);
						$(".guizeshezhi_select").attr("disabled",false);
						$("#addfytz").attr("disabled",false);
				    	if(successList!="" && successList!=undefined && successList!="[]"){
				    		BootstrapDialog.show({  //显示需要提交的表单。
					        	title:'提示信息',	
					        message: '若改为规则方式会删除导入名单生成的名单数据？',
					        closable: false,
					          buttons: [{
							    label: '确定',
							    cssClass: 'btn-danger',
							    action: function(dialogRef){
							    	$("#list").val("");
					            dialogRef.close();
					            $('#jfszbtn').attr("disabled",false);
					       	 	$("#btn_import_list").attr({"disabled":"disabled"});
					            }
						  }, {
						    label: '取消',
						    cssClass: 'btn-default',
						    action: function(dialogRef){
						    	$("#daorumd").prop("checked",true); 
						    	$("#guizesc").prop("checked",false); 
						       dialogRef.close();
						    }
						  }
						  ]
					        });
				    		
				    	}else{
//				    		 $('#jfszbtn').attr("disabled",false);
					       	 $("#btn_import_list").attr({"disabled":"disabled"});
				    	}
		            }
			  }, {
			    label: '取消',
			    cssClass: 'btn-default',
			    action: function(dialogRef2){
			       dialogRef2.close();
			       $("#daorumd").prop("checked",true);
			       $("#guizesc").prop("checked",false); 
			    }
			  }
			  ]
	        });
	    }
	});
})(jQuery, window);
