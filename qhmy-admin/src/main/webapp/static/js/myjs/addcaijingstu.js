//@ sourceURL=addstus.js 
/**
 * 学生信息新增对象
 */
(function($, window) {
	var stuinfo = {};

	//日期插件参数设置
	$('.date-picker').datepicker({
		autoclose: true,
		todayHighlight: false,
		format: 'yyyy-mm-dd',
		startView: 'month',
        minViewMode:'days',
        endYear:'+1m'
	});
	$("#jieshaoren_xiala").find("a").click(function(){
		var xingming = $(this).attr("data-name");
		var shenfenzhegn = $(this).attr("data-identification");
		var pkid = $(this).attr("data-id");
		$("#jieshaoren_id").val(pkid);
		$("#JIESHAOREN").val(xingming);
		$("#jieshaoren_xiala").hide();
	});
	//点击介绍人搜索关键信息
	$("#JIESHAOREN").bind('input propertychange', function() {
		var JIESHAOREN = $("#JIESHAOREN").val();
//		if($.trim(JIESHAOREN)!=''){
			$("#jieshaoren_xiala").show();
			$("#jieshaoren_id").val("");
			$.ajax({
				type : "post",
				dataType:"json",
				data:{"JIESHAOREN":$.trim(JIESHAOREN)},
				url:_basepath + "stuinfo/listJieShaoRen.json",
				success:function(data){
					$("#jieshaoren_xiala").html("");
					var list = data.listJieShaoRen;
					for(var i =0;i<list.length;i++){
						$('<li style="margin-top:0;margin-bottom:3px"><a href="javascript:void(0)" style="border-bottom:1px solid #eee;padding-left:0" data-name="'+list[i].XINGMING+'" data-identification="'+list[i].SHENFENZHENGHAO+'" data-id="'+list[i].PKID+'">'+
						'<span style="display:inline-block;margin-left:5px;">'+list[i].XINGMING+'</span>'+
						'<span style="display:inline-block;">—'+list[i].SHENFENZHENGHAO+'</span>'+
						'</a></li>').appendTo("#jieshaoren_xiala");
					}
					$("#jieshaoren_xiala").find("a").click(function(){
						var xingming = $(this).attr("data-name");
						var shenfenzhegn = $(this).attr("data-identification");
						var pkid = $(this).attr("data-id");
						$("#jieshaoren_id").val(pkid);
						$("#JIESHAOREN").val(xingming);
						$("#jieshaoren_xiala").hide();
					});
				},
				error:function(XMLHttpRequest, textStatus, errorThrown) {
					alert(errorThrown);
				}
			});
	});
	
	$("#JIESHAOREN").bind('click', function() {
		$("#jieshaoren_xiala").show();
		
	});
	$("#ZHAOSHENGREN").bind('click', function() {
		$("#zhaoshengren_xiala").show();
		
	});
	
	$("#zhaoshengren_xiala").find("a").click(function(){
		var xingming = $(this).attr("data-name");
		var shenfenzhegn = $(this).attr("data-identification");
		var pkid = $(this).attr("data-id");
		$("#zhaoshengren_id").val(pkid);
		$("#ZHAOSHENGREN").val(xingming);
		$("#zhaoshengren_xiala").hide();
	});
	//点击招生人搜索信息
	$("#ZHAOSHENGREN").bind('input propertychange', function() {
		var ZHAOSHENGREN = $("#ZHAOSHENGREN").val();
			$("#zhaoshengren_xiala").show();
			$("#zhaoshengren_id").val("");
			$.ajax({
				type : "post",
				dataType:"json",
				data:{"ZHAOSHENGREN":$.trim(ZHAOSHENGREN)},
				url:_basepath + "stuinfo/listZhaoShengRen.json",
				success:function(data){
					$("#zhaoshengren_xiala").html("");
					var list = data.listZhaoShengRen;
					for(var i =0;i<list.length;i++){
						$('<li style="margin-top:0;margin-bottom:3px"><a href="javascript:void(0)" style="border-bottom:1px solid #eee;padding-left:0" data-name="'+list[i].XINGMING+'" data-identification="'+list[i].SHENFENZHENGHAO+'" data-id="'+list[i].PKID+'">'+
						'<span style="display:inline-block;margin-left:5px;">'+list[i].XINGMING+'</span>'+
						'<span style="display:inline-block;">—'+list[i].SHENFENZHENGHAO+'</span>'+
						'</a></li>').appendTo("#zhaoshengren_xiala");
					}
					$("#zhaoshengren_xiala").find("a").click(function(){
						var xingming = $(this).attr("data-name");
						var shenfenzhegn = $(this).attr("data-identification");
						var pkid = $(this).attr("data-id");
						$("#zhaoshengren_id").val(pkid);
						$("#ZHAOSHENGREN").val(xingming);
						$("#zhaoshengren_xiala").hide();
					});
				},
				error:function(XMLHttpRequest, textStatus, errorThrown) {
					alert(errorThrown);
				}
			});
	});
	
	//检查身份证号码是否符合规范
	stuinfo.isCardNo = function(card)  
	{  
	    //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
	    var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;  
	    if(reg.test(card) === false)  
	    {  
	    	layer.msg("身份证号格式不正确!");
	        return false;  
	    }  
	    return true;  
	};
	//身份证号验证以及出生日期和性别根据身份证号读取
	$('#sfz').bind('change',function(){
		//身份证号验证
		var sfz = $('#sfz').val();
		if(sfz == ''){
			layer.msg("请输入身份证号");
			$("#sfz").focus();
			return false;
		}
		if(checkCard(sfz)==false){
			return false;
		}else{
			//性别
			var xingBie = sfz.charAt(sfz.length-2);
			xingBie = xingBie % 2 == 1 ? "男" : "女";
			//出生日期
			var csrq = sfz.substr(6, 8);
			csrq = csrq.substr(0,4)+"-"+csrq.substr(4,2)+"-"+csrq.substr(6,7);
			$("#xingbie").val(xingBie); 
			$("#csrq").val(csrq); 
		}
		 
	});
	
	// 保存方法
	stuinfo.save = function() {
		var reg=/^\s+$/g;
		//验证身份证号不能为空
		var sfz = $('#sfz').val();
		if(sfz==''||sfz==null||reg.test(sfz)){
			layer.msg("身份证号不能为空");
			$("#sfz").focus();
			return false;
		}
		if(checkCard(sfz)==false){
			layer.msg("身份证号格式不正确!");
			$("#sfz").focus();
			return false;
		}
		//姓名验证
		var name = $('#name').val();
		if (name.length == 0 || reg.test(name)) {
			layer.msg("姓名不能为空！");
			return false;
		}
		//验证手机号
		var phone = $('#phone').val();
		if(phone != '' && !phone.match(/^1[345789]\d{9}$/)){
			layer.msg("联系电话格式不正确");
			$("#phone").focus();
			return false;
		}
        if(phone==''||phone==null){
			layer.msg("联系电话不能为空");
			$("#phone").focus();
			return false;
		}
		//家庭成员联系电话
		var lxdh = $('#lxdh').val();
		if(lxdh != '' && !lxdh.match(/^1[345789]\d{9}$/)){
			layer.msg("家庭成员联系电话格式不正确");
			$("#lxdh").focus();
			return false;
		}
		//邮政编码验证开始
		var yzbm = $('#yzbm').val();
		var reg1 =  /^\d{6}(?!\d)$/g;
		if(yzbm!=""&&yzbm!=null){
			if(!reg1.test(yzbm)){
				layer.msg("邮政编码格式错误");
				$("#yzbm").focus();
				return false;
			}
		}
		var toudangchengji = $('#toudangchengji').val();//投档成绩
		var reg2 =  /^[1-9]\d*(\.\d)?$/g;
		if(toudangchengji!=""&&toudangchengji!=null){
			if(!reg2.test(toudangchengji)){
				layer.msg("投档成绩只能填写整数或小数（小数后保留一位）");
				$("#toudangchengji").focus();
				return false;
			}
		}
		//验证家庭住址不为空
		var jiatingzhuzhi = $('#jiatingzhuzhi').val();
		if(jiatingzhuzhi == ''||jiatingzhuzhi==null){
			layer.msg("家庭住址不能为空");
			$("#jiatingzhuzhi").focus();
			return false;
		}
		//验证中学名称不为空
		var zxmc = $('#zxmc').val();
		if(zxmc == ''){
			layer.msg("中学名称不能为空");
			$("#zxmc").focus();
			return false;
		}
		//验证层次和批次不能为空
		var CENGCI = $("#CENGCI").val();
		if(CENGCI==''){
			layer.msg("请选择学生类型");
			return false;
		}
		var PICI = $("#PICI").val();
		if(PICI==''){
			layer.msg("请选择批次");
			return false;
		}
		var minzu = $('#minzu').val();//民族
		if(minzu==''||minzu==null){
			layer.msg("民族不能为空");
			$("#minzu").focus();
			return false;
		}
		//院校专业验证不能为空		
		var citySel=$('#citySel').val();
		if (citySel.length == 0 || reg.test(citySel)) {
			layer.msg("请选择院校专业");
			return false;
		}
		
		//考生特长
		var kaoshengtechang = '';
		$("input[name='checkbox']").each(function() {
			if ($(this)[0].checked == true) {
				kaoshengtechang += $(this)[0].value + ",";
			}
		});
		//特殊标记
		var specialFlag = '';
		$("input[name='checkbox1']").each(function() {
			if ($(this)[0].checked == true) {
				specialFlag += $(this)[0].value + ",";
			}
		});
		var grade=$('#grade').find("option:selected").attr("bianma");//入学学年
		if(grade==''||grade==null){
			layer.msg("入学年份不能为空");
			return false;
		}
		var STUDENT_SOURCE = $("#jihuaxingzhi").val();//计划性质
		if(STUDENT_SOURCE==''||STUDENT_SOURCE==null){
			layer.msg("计划性质不能为空");
			return false;
		}
		var kebie = $('#kebie').val();//科别
		if(kebie==''||kebie==null){
			layer.msg("科别不能为空");
			return false;
		}
		var xueshengleixing = $("#xueshengleixing").val();//学生类型
		if(xueshengleixing==''||xueshengleixing==null){
			layer.msg("学生类型不能为空");
			return false;
		}
		var nodeId = $('#orgtree').val();
		var sfz = $('#sfz').val();
		var csrq = $('#csrq').val();//出生日期
		var xingbie = $('#xingbie').val();//性别
		var zhengzhimianmao = $('#zhengzhimianmao').val();//政治面貌
		var jkzk = $('#jkzk').val();//健康状况
		var hukouxingzhi = $('#hukouxingzhi').val();//户口性质		
		var jtcyxm = $('#jtcyxm').val();//家庭成员
		var jtcygx = $('#jtcygx').val();//家庭成员关系
		var xuehao = $('#xuehao').val();//学号
		var poorstu = $('#poorstu').val();//是否贫困
		var kaoshenghao = $('#kaoshenghao').val();//考生号
		var luquzhuanye = $('#luquzhuanye').val();//录取专业				
		var xuezhi = $('#xuezhi').val();//学制
		var SCHOOLROLL = $('#SCHOOLROLL').val();//学籍类型
		var banji = $("#banji").val();//班级
		var zaixuezhuangtai = $("#zaixuezhuangtai").val();//在学状态
		var xuejizhuangtai = $("#xuejizhuangtai").val();//学籍状态
		var sxbs = $("#sxbs").val();//升学标识		
		var ZCXJSJ = $("#ZCXJSJ").val();//注册学籍时间			
		var RUXUESHIJIAN = $("#RUXUESHIJIAN").val();//入学时间
		var PKID = $("#pkid").val();
		var REMARKS1 = $("#REMARKS1").val();//备注1
		var REMARKS2 = $("#REMARKS2").val();//备注2
		var REMARKS3 = $("#REMARKS3").val();//备注3
		var REMARKS4 = $("#REMARKS4").val();//备注4
		var REMARKS5 = $("#REMARKS5").val();//备注5
		var REMARKS6 = $("#REMARKS6").val();//备注6
		var REMARKS7 = $("#REMARKS7").val();//备注7
		var REMARKS8 = $("#REMARKS8").val();//备注8
		var url=_basepath+"stucjinfo/saveeducj.json";
		var data={SHENFENZHENGHAO:sfz,CHUSHENGRIQI:csrq,XINGBIE:xingbie,XINGMING:name,SHOUJI:phone,NATION_PKID:minzu,
				  ZHENGZHIMIANMAO_PKID:zhengzhimianmao,JKZK_PKID:jkzk,HUKOUXINGZHI_PKID:hukouxingzhi,YOUZHENGBIANMA:yzbm,JTCYXM:jtcyxm,
				  JTCYLXDH:lxdh,JTCYGX_PKID:jtcygx,JIATINGZHUZHI:jiatingzhuzhi,XUEHAO:xuehao,POOR_PKID:poorstu,STUDENT_SOURCE:STUDENT_SOURCE,
				  ZXMC:zxmc,KAOSHENGHAO:kaoshenghao,KSTCList:kaoshengtechang,NIANJI:grade,LUQUZHUANYE:luquzhuanye,DEPARTMENT_PKID:nodeId,
				  CENGCI:CENGCI,PICI:PICI,TOUDANGCHENGJI:toudangchengji,XUEZHI_PKID:xuezhi,KELEI_PKID:kebie,XUEJI_PKID:SCHOOLROLL,BANJI:banji,
				  ZAIXUEZT:zaixuezhuangtai,SHENGXUEBIAOSHI_PKID:sxbs,ZCXJSJ:ZCXJSJ,REMARKS1:REMARKS1,REMARKS2:REMARKS2,REMARKS3:REMARKS3,
				  REMARKS4:REMARKS4,REMARKS5:REMARKS5,REMARKS6:REMARKS6,REMARKS7:REMARKS7,REMARKS8:REMARKS8,PKID:PKID,
				  XSLX_PKID:xueshengleixing,XUEJIZHUANGTAI_PKID:xuejizhuangtai,RUXUESHIJIAN:RUXUESHIJIAN,specialFlag:specialFlag
		};
		$.post(url,data,function(data){
			if(data.rst=='idCardIsExist'){
				layer.msg("身份证号已存在!");
			}
			else if(data.rst=='xuehaoIsExist'){
				layer.msg("学号已存在!");
			}
			else if(data.rst=="success"){
				layer.msg("保存成功！");
				$(".jf_szright").load(_basepath + "stuinfo/stuinfo_list.php");
			}else{
				layer.msg("保存失败!");
			}
		});

	};

	//重置密码
	stuinfo.resetPwd=function(e,f){
		$.get(_basepath+'stuinfo/resetpwd.json?PKID='+e+'&SHENFENZHENGHAO='+f,function(data){
			if(data.result=='success'){
				layer.msg("重置密码成功！");
			}
		});
	};
	
	
	//上传头像
	stuinfo.uploadTouXiang=function(){
		var PKID = $("#pkid").val();
		var sfz = $("#sfz").val();
		    if(PKID==null||PKID==""){
				layer.msg('请先保存学生基本信息!');
				   return false;
		    }else{
		    	 BootstrapDialog.show({
					title:'上传近照',
		            message: $('<div></div>').load(_basepath+"stuinfo/uploadtouxiang.json?sfz="+sfz),
		            closable: true,
		            buttons: [{
					    label: '保存',
						    cssClass: 'btn btn-danger',
						    action: function(dialogRef){
			                    var path = $("#path").val();
			                    $.ajax({  
			        	            type : "POST",  
			        	            url : _basepath+"stuinfo/imgCrop.json?sfz="+sfz,  
			        	            dateType : "json",  
			        	            data : {  
			        	                "x" : $('#x').val(),  
			        	                "y" : $('#y').val(),  
			        	                "width" : $('#width').val(),  
			        	                "height" : $('#height').val(),  
			        	                "path" : path,
			        	                "PKID":PKID
			        	            },  
			        	            success : function(dat) {
			        	            	var random = Math.random();
			        	                $('#touxiang').attr('src',_basepath+dat.path+"?r="+random);  
			        	            }  
			        	        });
			                    dialogRef.close();
			                    $('.imgareaselect-selection').parent().remove();
			                    $('.imgareaselect-outer').remove();
			                }
					  	},{
					    label: '取消',
					    cssClass: 'btn btn-default',
					    action: function(dialogRef){
					       dialogRef.close();
					       $('.imgareaselect-outer').remove();
					       $('.imgareaselect-selection').parent().remove();
			                $.ajax({  
			                    'url': _basepath+'stuinfo/pic_delete?PKID='+PKID,  
			                    type: 'POST',  
			                    data:{  
			                        path:$("#path").val() 
			                	},
			                	success : function(data) {
		        	            }
			                    }); 
					    }
					  }
					  ] 
				});
		    }
		
	};
	
	
	
	
	
	//点击保存按钮
	$('#btn_save').bind('click',function(){
		stuinfo.save();
	});
	//点击返回(取消)按钮
	$('#btn_cancel').bind('click',function(){
		$(".jf_szright").load(_basepath + "stuinfo/stuinfo_list.php");
	});
	
	//点击重置密码按钮
	$('#btn_resetpwd').bind('click',function(){
		var pkid=$('#pkid').val();
		var sfz=$('#sfz').val();
		stuinfo.resetPwd(pkid,sfz);
	});
	
	//点击上传头像按钮
	$("#btn_tx").click(function(){
		stuinfo.uploadTouXiang();
	});
	$("#grade").bind("change", function() {
		changeGrade();
	});
	
})(jQuery, window);

$(function(){
	//解决头像更换后的缓存问题
	if(_imgsrc!=''){
		$('#touxiang').attr('src',_basepath+_imgsrc+"?r="+Math.random()); 
	}else{
		$('#touxiang').attr('src',_basepath+"static/gxjf/images/defaulttouxiang.jpg"); 
		$('#touxiangs').attr('src',_basepath+"static/gxjf/images/newshenfenzheng.jpg"); 
	}
	 
	for(var i=0;i<stu_PersonalFlag.length;i++){
		$("input[name='checkbox']").each(function() {       
        	if($(this)[0].value==stu_PersonalFlag[i].EXCEPTIONAL){
        		$(this)[0].checked=true;
        	}
		});  
	}
	
	
	
});