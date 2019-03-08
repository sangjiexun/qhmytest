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
		
		//验证手机号
		var phone = $('#phone').val();
		if(phone != '' && !phone.match(/^1[345789]\d{9}$/)){
			layer.msg("联系电话格式不正确");
			$("#phone").focus();
			return false;
		}
		var reg3 =  /^[A-Za-z0-9]{0,20}$/g;
		var xuehao = $('#xuehao').val();//学号
		if(xuehao!=""&&xuehao!=null){
			if(!reg3.test(xuehao)){
				layer.msg("学号只能输入数字和字母且不能超过20位");
				$("#xuehao").focus();
				return false;
			}
		}
		//家庭成员联系电话
		var lxdh = $('#lxdh').val();
		if(lxdh != '' && !lxdh.match(/^1[345789]\d{9}$/)){
			layer.msg("家庭成员联系电话格式不正确");
			$("#lxdh").focus();
			return false;
		}
		//入学年份验证不能为空
		var grade=$('#grade').find("option:selected").attr("bianma");
		if(grade==''||grade==null){
			layer.msg("入学年份不能为空");
			return false;
		}
		//院校专业验证不能为空		
		var citySel=$('#citySel').val();
		
		if (citySel.length == 0 || reg.test(citySel)) {
			layer.msg("请选择院校专业");
			return false;
		}
		//验证层次不能为空
		var CENGCI = $("#CENGCI").val();
		if(CENGCI==''){
			layer.msg("请选择学生类型");
			return false;
		}
		//验证批次不能为空
		var PICI = $("#PICI").val();
		if(PICI==''){
			layer.msg("请选择批次");
			return false;
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
		//特殊标记
		var specialFlag = '';
		$("input[name='checkbox']").each(function() {
			if ($(this)[0].checked == true) {
				specialFlag += $(this)[0].value + ",";
			}
		});
		
		var nodeId = $('#orgtree').val();//院校专业编码
		var csrq = $('#csrq').val();//出生日期
		var xingbie = $('#xingbie').val();//性别
		var name = $('#name').val();//姓名		
		var jtcyxm = $('#jtcyxm').val();//家庭成员
		var jtcygx = $('#jtcygx').val();//家庭成员关系
		var poorstu = $('#poorstu').val();//是否贫困
		var jiatingzhuzhi = $('#jiatingzhuzhi').val();//家庭地址
		var zxmc = $('#zxmc').val();//中学名称
		var stuxssy = $('#stuxssy').val();//学生生源
		var banji = $("#banji").val();//班级
		var zaixuezhuangtai = $("#zaixuezhuangtai").val();//在学状态
		var shifoujidu = $("#shifoujidu").val();//是否寄读
		var RUXUESHIJIAN = $("#RUXUESHIJIAN").val();//入学时间
		var REMARKS1 = $("#REMARKS1").val();//备注1
		var REMARKS2 = $("#REMARKS2").val();//备注2
		var REMARKS3 = $("#REMARKS3").val();//备注3
		
		var url=_basepath+"stuinfo/saveedu.json";
		var data={SHENFENZHENGHAO:sfz,CENGCI:CENGCI,PICI:PICI,DEPARTMENT_PKID:nodeId,NIANJI:grade,SHOUJI:phone,JTCYLXDH:lxdh,
				  TOUDANGCHENGJI:toudangchengji,specialFlag:specialFlag,CHUSHENGRIQI:csrq,XINGBIE:xingbie,XINGMING:name,
				  XUEHAO:xuehao,JTCYXM:jtcyxm,JTCYGX_PKID:jtcygx,POOR_PKID:poorstu,JIATINGZHUZHI:jiatingzhuzhi,ZXMC:zxmc,
				  STU_BIRTH_PKID:stuxssy,BANJI:banji,ZAIXUEZT:zaixuezhuangtai,ISPOST:shifoujidu,RUXUESHIJIAN:RUXUESHIJIAN,
				  REMARKS1:REMARKS1,REMARKS2:REMARKS2,REMARKS3:REMARKS3				  
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
	}
	
});