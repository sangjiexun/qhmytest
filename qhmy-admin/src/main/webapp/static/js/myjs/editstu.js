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
		format: 'yyyy-mm',
		startView: 1,
        minViewMode:1,
        endYear:'+1m'
	});
	//根据身份证号生成男女
	stuinfo.onloadvalues = function(){
		//身份证号验证
		var sfz = $('#SHENFENZHENGHAO').val();
		//性别
		var xingBie = sfz.charAt(sfz.length-2);
		xingBie = xingBie % 2 == 1 ? "男" : "女";
		$("#XINGBIE").val(xingBie); 
	};
	stuinfo.onloadvalues();
	//身份证号识别男女
	$('#SHENFENZHENGHAO').bind('change',function(){
		//身份证号验证
		var sfz = $('#SHENFENZHENGHAO').val();
		if(sfz == ''){
			layer.msg("请输入身份证号");
			$("#SHENFENZHENGHAO").focus();
			return false;
		}
		if(checkCard(sfz)==false){
			return false;
		}else{
			//性别
			var xingBie = sfz.charAt(sfz.length-2);
			xingBie = xingBie % 2 == 1 ? "男" : "女";
			$("#XINGBIE").val(xingBie); 
		}
		 
	});
	// 保存方法
	stuinfo.save = function() {
		var reg=/^\s+$/g;
		//验证身份证号不能为空
		var sfz = $('#SHENFENZHENGHAO').val();
		if(sfz==''||sfz==null||reg.test(sfz)){
			layer.msg("身份证号不能为空");
			$("#SHENFENZHENGHAO").focus();
			return false;
		}
		if(checkCard(sfz)==false){
			layer.msg("身份证号格式不正确!");
			$("#SHENFENZHENGHAO").focus();
			return false;
		}
		var XINGMING = $("#XINGMING").val();
		if(XINGMING==''||XINGMING==null||reg.test(XINGMING)){
			layer.msg("姓名不能为空");
			$("#XINGMING").focus();
			return false;
		}
		//验证手机号
		var phone = $('#SHOUJI').val();
		if(phone != '' && !phone.match(/^\d{11}$/)){
			layer.msg("联系电话格式不正确");
			$("#SHOUJI").focus();
			return false;
		}
		if(phone==''||phone==null||reg.test(phone)){
			layer.msg("手机号码不能为空");
			$("#SHOUJI").focus();
			return false;
		}
		//获取监护人1姓名
		var ONE_JHR=$("#ONE_JHR").val();				
		if(ONE_JHR == ''){
			layer.msg("第一监护人不能为空");
			$("#ONE_JHR").focus();
			return false;
		}
		//获取监护人1家庭关系PKID
		var ONE_JHRGX1=$("#ONE_JHRGX1").val();	
		if(ONE_JHRGX1 == ''){
			layer.msg("第一监护人家庭关系不能为空");
			$("#ONE_JHRGX1").focus();
			return false;
		}
		//获取监护人1电话
		var ONE_JHRDH=$("#ONE_JHRDH").val();			
		if(ONE_JHRDH == ''){
			layer.msg("第一监护人电话不能为空");
			$("#ONE_JHRDH").focus();
			return false;
		}
		if(ONE_JHRDH != '' && !ONE_JHRDH.match(/^\d{11}$/)){
			layer.msg("监护人联系电话格式不正确");
			$("#ONE_JHRDH").focus();
			return false;
		}
		//第二监护人家庭电话
		var TWO_JHRDH =$("#TWO_JHRDH").val();
		if(TWO_JHRDH != '' && !TWO_JHRDH.match(/^\d{11}$/)){
			layer.msg("监护人联系电话格式不正确");
			$("#TWO_JHRDH").focus();
			return false;
		}
		//班主任电话
		var BZRPHONE =$("#BZRPHONE").val();
		if(BZRPHONE != '' && !BZRPHONE.match(/^\d{11}$/)){
			layer.msg("班主任电话格式不正确");
			$("#BZRPHONE").focus();
			return false;
		}
		//获取文化课学校
		var WHKXUEXIAO=$("#WHKXUEXIAO").val();			    
		if(WHKXUEXIAO == ''){
			layer.msg("文化课学校不能为空");
			$("#WHKXUEXIAO").focus();
			return false;
		}
		
		//家庭住址
		var JIATINGZHUZHI =$("#JIATINGZHUZHI").val();
		//邮政编码
		var YOUZHENGBIANMA =$("#YOUZHENGBIANMA").val();
		//第二监护人
		var TWO_JHR =$("#TWO_JHR").val();

		//第二监护人家庭关系
		var TWO_JHRGX =$("#TWO_JHRGX").val();
		
		//病史
		var BINGSHI =$("#BINGSHI").val();
		//了解强化的途径
		var LJQHTJ =$("#LJQHTJ").val();
		//推荐人
		var TUIJIANREN =$("#TUIJIANREN").val();
		//高一入学时间
		var GYRXSJ =$("#GYRXSJ").val();
		//高中班级
		var GZ_BANJI =$("#GZ_BANJI").val();
		//文理科
		var WENLIKE =$("#WENLIKE").val();
		//学校班主任
		var BZRSCHOOL =$("#BZRSCHOOL").val();
		
		var ONE_JHRDW = $("#ONE_JHRDW").val();          //监护人1单位
		var ONE_JHRZW = $("#ONE_JHRZW").val();          //监护人1职务
		var TWO_JHRDW = $("#TWO_JHRDW").val();          //监护人2单位
		var TWO_JHRZW = $("#TWO_JHRZW").val();          //监护人2职务
		
		var yingjiesheng = $("#yingjiesheng").is(":hidden");
		var bxfukesheng = $("#bxfukesheng").is(":hidden");
		var wxfukesheng = $("#wxfukesheng").is(":hidden");
		//层次
		var cengci = "";
		//自何时学习美术
		var STARTMEISHU = "";
		
		//已学过科目
		var KEMU = "";
		//入学年份
		var RXNIANFEN = "";
		//班型
		var BANJI_TYPE = "";
		//在学状态
		var ZXZT = "";
		//预交年份
		var YJ_NIANFEN = "";
		//预交班型
		var YJ_BANJI_TYPE = "";
		//原班级
		var OLD_BANJINAME = "";
		//联考分数
		var LKFS = "";
		//考生号
		var KSNUMBER = "";
		//性别
		var XINGBIE = $("#XINGBIE").val();
		if(XINGBIE == "男"){
			XINGBIE = "M";
		}else{
			XINGBIE = "W";
		}
		//获取校考成绩
		var XK_MARK="";
		var cengciFlag = "";
		var IPKID="";
		$(".ipkid").each(function(){
			IPKID+=$(this).val()+",";
		});
		var PAYNAME="";
		$(".payname").each(function(){
			PAYNAME+=$(this).val()+",";
		});
		var PAYPRICE="";
		$(".payprice").each(function(){
			PAYPRICE+=$(this).val()+",";
		});
		var DISCOUNT_MONEY="";
		$(".discount_money").each(function(){
			DISCOUNT_MONEY+=$(this).val()+",";
		});
		var RPKID="";
		$(".rpkid").each(function(){
			RPKID+=$(this).val()+",";
		});
		var PAYCOST="";
		$(".pay_cost").each(function(){
			PAYCOST+=$(this).val()+",";
		});
		//联考分数正则匹配
/*		var reg2 =  /^[1-9]\d*(\.\d)?$/g;*/
		var reg2=/^(([1-9]\d*)|\d)(\.\d{1,2})?$/;
		//应届生
		if(yingjiesheng == false){
			//获取层次PKID
			cengci=$("#ccyingjiesheng").val();
			if(cengci == ''){
				layer.msg("学生类型不能为空");
				$("#ccyingjiesheng").focus();
				return false;
			}			
			//获取入学年份PKID
			RXNIANFEN = $("#RXNIANFEN1").val();
			if(RXNIANFEN == ''){
				layer.msg("入学年份不能为空");
				$("#RXNIANFEN1").focus();
				return false;
			}
			//获取班型PKID
			BANJI_TYPE = $("#BANJI_TYPE1").val();
			if(BANJI_TYPE == ''){
				layer.msg("班型不能为空");
				$("#BANJI_TYPE1").focus();
				return false;
			}
			STARTMEISHU = $("#STARTMEISHU").val();//自何时学习美术
			
		    //获取已学过科目 SX速写 SC色彩 SM素描
			$("#KEMU1 input[type='checkbox']:checked").each(function(){
				KEMU+=$(this).val()+",";
			});
			KEMU=KEMU.substring(0,KEMU.length-1);

			ZXZT = $("#ZXZT1").val();//在学状态
			//获取预交入学年份PKID
			YJ_NIANFEN = $("#YJ_NIANFEN1").val();
			//获取预交班型PKID
			YJ_BANJI_TYPE = $("#YJ_BANJI_TYPE1").val();
			if(YJ_NIANFEN!=null&&YJ_NIANFEN!=""){
				if(YJ_BANJI_TYPE==null||YJ_BANJI_TYPE==""){
					layer.msg("预交入学年份和预交班型必须同时有值！");
					return false;
				}
			}
			if(YJ_BANJI_TYPE!=null&&YJ_BANJI_TYPE!=""){
				if(YJ_NIANFEN==null||YJ_NIANFEN==""){
					layer.msg("预交入学年份和预交班型必须同时有值！");
					return false;
				}
			}
			var banxingname = $("#BANJI_TYPE1").find("option:selected").text();
			if(banxingname!="寒假班"&&banxingname!="暑假班"){
				$("#yujiaodiv").hide();
				YJ_BANJI_TYPE="";
				YJ_NIANFEN="";
			}else{
				$("#yujiaodiv").show();
			}
			cengciFlag ='1';
			
		}
		//本校复课生
		if(bxfukesheng == false){
			//获取层次PKID
			cengci=$("#ccbxfukesheng").val();  
			if(cengci == ''){
				layer.msg("学生类型不能为空");
				$("#ccbxfukesheng").focus();
				return false;
			}			
			//获取入学年份PKID
			 RXNIANFEN = $("#RXNIANFEN2").val();  
			if(RXNIANFEN == ''){
				layer.msg("入学年份不能为空");
				$("#RXNIANFEN2").focus();
				return false;
			}
			//获取班型PKID
			BANJI_TYPE = $("#BANJI_TYPE2").val();
			if(BANJI_TYPE == ''){
				layer.msg("班型不能为空");
				$("#BANJI_TYPE2").focus();
				return false;
			}
			//校考成绩				                
			$("#XKCJCHECKBOX2 input[type='checkbox']:checked").each(function(){
				XK_MARK+=$(this).val()+",";
			});
			XK_MARK=XK_MARK.substring(0,XK_MARK.length-1);
			
			OLD_BANJINAME = $("#OLD_BANJINAME2").val();//原班级
			LKFS = $("#LKFS2").val();//联考分数
			if(LKFS!=""&&LKFS!=null){
				if(!reg2.test(LKFS)){
					layer.msg("联考分数只能填写整数或小数（小数后保留两位）");
					$("#LKFS2").focus();
					return false;
				}
			}
			KSNUMBER = $("#KSNUMBER2").val();//考生号
			ZXZT = $("#ZXZT2").val();//在学状态
			cengciFlag ='2';
		}
		//外校复课生
		if(wxfukesheng == false){
			//获取层次PKID
			cengci=$("#ccwxfukesheng").val();  
			if(cengci == ''){
				layer.msg("学生类型不能为空");
				$("#ccwxfukesheng").focus();
				return false;
			}			
			//获取入学年份PKID
			RXNIANFEN = $("#RXNIANFEN3").val();  
			if(RXNIANFEN == ''){
				layer.msg("入学年份不能为空");
				$("#RXNIANFEN3").focus();
				return false;
			}
			//获取班型PKID
			BANJI_TYPE = $("#BANJI_TYPE3").val();
			if(BANJI_TYPE == ''){
				layer.msg("班型不能为空");
				$("#BANJI_TYPE3").focus();
				return false;
			}
			//校考成绩
			$("#XKCJCHECKBOX3 input[type='checkbox']:checked").each(function(){
				XK_MARK+=$(this).val()+",";
			});
			XK_MARK=XK_MARK.substring(0,XK_MARK.length-1);
			
			LKFS = $("#LKFS3").val();//联考分数
			if(LKFS!=""&&LKFS!=null){
				if(!reg2.test(LKFS)){
					layer.msg("联考分数只能填写整数或小数（小数后保留两位）");
					$("#LKFS3").focus();
					return false;
				}
			}
			KSNUMBER = $("#KSNUMBER3").val();//考生号
			ZXZT = $("#ZXZT3").val();//在学状态
			cengciFlag ='2';
		}
		var REMARKS1 = $("#REMARKS1").val();//备注1
		var REMARKS2 = $("#REMARKS2").val();//备注2
		var REMARKS3 = $("#REMARKS3").val();//备注3
		var PKID = $("#PKID").val();//
		var TMPKID = $("#TMPKID").val();//
		var yjnianfenPkid = $("#yjnianfenPkid").val();
		var yjbanxingPkid = $("#yjbanxingPkid").val();
		var isYj = "";
		if(yjnianfenPkid==""||yjnianfenPkid==null){
			isYj = '0';//代表之前无预交
		}else{
			isYj = '1';//代表之前有预交
		}

		var RXNIANFEN_PKID = $("#RXNIANFEN_PKID").val();
		var BANJI_TYPE_PKID = $("#BANJI_TYPE_PKID").val();
		var sfzh = $("#sfzh").val();
		var SFZTOUXIANG = $("#SFZTOUXIANG").val();
		var STUTOUXIANG = $("#STUTOUXIANG").val();
		var YJ_STUBM_PKID = $("#YJ_STUBM_PKID").val();
		if(YJ_STUBM_PKID!=TMPKID){
			YJ_STUBM_PKID="";
		}
		//如果原来YJ_STUBM_PKID没有值 现在预交年份和预交班型有值则update
		if(YJ_NIANFEN!=null&&YJ_NIANFEN!=""&&(YJ_STUBM_PKID==null||YJ_STUBM_PKID=="")){
			YJ_STUBM_PKID = TMPKID;
		}
		//如果原来YJ_STUBM_PKID有值 现在预交年份和预交班型没有值则置空
		else if(YJ_STUBM_PKID!=null&&YJ_STUBM_PKID!=""&&(YJ_NIANFEN==null||YJ_NIANFEN=="")){
			YJ_STUBM_PKID="";
		}
		
		//如果原来YJ_STUBM_PKID有值 现在预交年份和预交班型没有值则置空
		else if(YJ_STUBM_PKID!=null&&YJ_STUBM_PKID!=""&&YJ_NIANFEN!=null&&YJ_NIANFEN!=""){
			YJ_STUBM_PKID=$("#YJ_STUBM_PKID").val();
		}
		STARTMEISHU = STARTMEISHU.substring(0, 7);
		GYRXSJ = GYRXSJ.substring(0, 7);
		var TONGGUO = $("#TONGGUO").val();
		var url=_basepath+"stuinfo/saveedu.json";
		var data={SHENFENZHENGHAO:sfz,XINGMING:XINGMING,SHOUJI:phone,ONE_JHR:ONE_JHR,ONE_JHRGX_PKID:ONE_JHRGX1,
				ONE_JHRDH:ONE_JHRDH,WHKXUEXIAO:WHKXUEXIAO,JIATINGZHUZHI:JIATINGZHUZHI,YOUZHENGBIANMA:YOUZHENGBIANMA,
				TWO_JHR:TWO_JHR,TWO_JHRGX:TWO_JHRGX,TWO_JHRDH:TWO_JHRDH,BINGSHI:BINGSHI,LJQHTJ:LJQHTJ,cengciFlag:cengciFlag,
				TUIJIANREN:TUIJIANREN,GYRXSJ:GYRXSJ,GZ_BANJI:GZ_BANJI,WENLIKE:WENLIKE,BZRSCHOOL:BZRSCHOOL,
				BZRPHONE:BZRPHONE,CENGCI:cengci,STARTMEISHU:STARTMEISHU,KEMU:KEMU,RXNIANFEN:RXNIANFEN,
				BANJI_TYPE:BANJI_TYPE,ZXZT:ZXZT,YJ_NIANFEN:YJ_NIANFEN,YJ_BANJI_TYPE:YJ_BANJI_TYPE,XK_MARK:XK_MARK,
				OLD_BANJINAME:OLD_BANJINAME,LKFS:LKFS,KSNUMBER:KSNUMBER,YJ_STUBM_PKID:YJ_STUBM_PKID,
				REMARKS1:REMARKS1,REMARKS2:REMARKS2,REMARKS3:REMARKS3,PKID:PKID,TMPKID:TMPKID,XINGBIE:XINGBIE,
				IPKID:IPKID,PAYNAME:PAYNAME,PAYCOST:PAYCOST,PAYPRICE:PAYPRICE,DISCOUNT_MONEY:DISCOUNT_MONEY,
				RPKID:RPKID,sign:'update',isYj:isYj,RXNIANFEN_PKID:RXNIANFEN_PKID,BANJI_TYPE_PKID:BANJI_TYPE_PKID,
				SFZTOUXIANG:SFZTOUXIANG,STUTOUXIANG:STUTOUXIANG,sfzh:sfzh,yjnianfenPkid:yjnianfenPkid,yjbanxingPkid:yjbanxingPkid,
				ONE_JHRDW:ONE_JHRDW, ONE_JHRZW:ONE_JHRZW,TWO_JHRDW:TWO_JHRDW,TWO_JHRZW:TWO_JHRZW,TONGGUO:TONGGUO
		};
		$.post(url,data,function(data){
		    if(data.rst=="success"){
				layer.msg("保存成功！");
				$(".jf_szright").load(_basepath + "stuinfo/stuinfo_list.php");
			}else if(data.rst=="isExist"){
				layer.msg("同一身份证号、入学年份、班型的学生已经存在！");
			}else if(data.rst=="isExSfz"){
				layer.msg("同一身份证号的学生已经存在！");
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
	stuinfo.uploadTouXiang=function(aa){
		//var PKID = $("#PKID").val();
		  
		    	 BootstrapDialog.show({
					title:'上传近照',
		            message: $('<div></div>').load(_basepath+"signUp/uploadtouxiang.json?text="+aa),
		            closable: true,
		            buttons: [{
					    label: '保存',
						    cssClass: 'btn btn-danger',
						    action: function(dialogRef){
						    
						    	if($('#width').val()==0||$('#height').val()==0){
									layer.msg('图片尺寸异常!');
									return false;
						    	}
						    	var is_text=$("#is_text").val();
						    	
						    	var path="";
						    	if("sfz"==is_text){
						    		path = $("#sfzpath").val();
						    		  $.ajax({  
					        	            type : "POST",  
					        	            url : _basepath+"signUp/imgCrop.json?sfz="+path,  
					        	            dateType : "json",  
					        	            data : {  
					        	                "x" : $('#x').val(),  
					        	                "y" : $('#y').val(),  
					        	                "width" : $('#width').val(),  
					        	                "height" : $('#height').val(),  
					        	                "path" : path,
					        	                
					        	            },  
					        	            success : function(dat) {
					        	            	var random = Math.random();
					        	            	console.log(dat.path);
					        	            	
					        	            	if (typeof(dat.path) == "undefined"){
					        	            		$('#touxiangs').attr('src',_basepath+"static/gxjf/images/newshenfenzheng.jpg");  

					        	            	}else{
					        	            		 $("#touxiangs").attr('src',_basepath+dat.path+"?"+Math.random());  
					        	            		$("#SFZTOUXIANG").val(dat.path);
					        	            		
					        	            	}
					        	                
					        	                
					        	            }  
					        	        });
						    	}else{
				                    path = $("#path").val();
				                    $.ajax({  
				        	            type : "POST",  
				        	            url : _basepath+"signUp/imgCrop.json?sfz="+path,  
				        	            dateType : "json",  
				        	            data : {  
				        	                "x" : $('#x').val(),  
				        	                "y" : $('#y').val(),  
				        	                "width" : $('#width').val(),  
				        	                "height" : $('#height').val(),  
				        	                "path" : path,
				        	                
				        	            },  
				        	            success : function(dat) {
				        	            	var random = Math.random();
				        	            	console.log(dat.path);
				        	            	
				        	            	if (typeof(dat.path) == "undefined"){
				        	            		$('#touxiang').attr('src',_basepath+"static/gxjf/images/defaulttouxiang.jpg"); 

				        	            	}else{
				        	            		 $("#touxiang").attr('src',_basepath+dat.path+"?"+Math.random());  
				        	            		$("#STUTOUXIANG").val(dat.path);
				        	            		
				        	            	}
				        	                
				        	                
				        	            }  
				        	        });

						    	}
						    	
			                   
			                   
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
			                    'url': _basepath+'signUp/pic_delete?PKID='+PKID,  
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
		   
		
	};;
	
	
	
	
	
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
		var pkid=$('#TMPKID').val();
		var sfz=$('#SHENFENZHENGHAO').val();
		stuinfo.resetPwd(pkid,sfz);
	});
	
	//审核通过按钮
	$("#btn_shtg").click(function(){
		var pkids = $("#tmpkid").val();
		var TONGGUO = $("#TONGGUO").val();
		if(TONGGUO=='0'){
			layer.msg("审核不通过的学生不能再次审核不通过!");
			return false;
		}
		  BootstrapDialog.show({  //显示需要提交的表单。
          	title:'提示信息',	
	          message: '你确定要审核通过吗？',
	          closable: false, 
	            buttons: [{
				    label: '确定',
				    cssClass: 'btn-danger',
				    action: function(dialogRef){
				    	stuinfo.batchTongGuo(pkids,1);
				    	dialogRef.close();
	              }
	            }, {
			    label: '关闭',
			    cssClass: 'btn-default',
			    action: function(dialogRef){
			       dialogRef.close();
			    }
			  }]
       });
	});
	//审核不通过按钮
	$("#btn_shbtg").click(function(){
		var pkids = $("#tmpkid").val();
		var TONGGUO = $("#TONGGUO").val();
		if(TONGGUO=='1'){
			layer.msg("审核通过的学生不能再次审核不通过!");
			return false;
		}
		$('#mymoda2').modal({
			keyboard: true
		});
		$("#IS_TONGGUO_REASON1").val('');
	});
	//批量通过方法
	stuinfo.batchTongGuo=function(pkids,status){
		$.post(_basepath+"stuinfo/batchTongGuo.json?pkids="+pkids+'&&status='+status+"&&IS_TONGGUO_REASON="+"",function(data){
			if(data.result=="success"){
				layer.msg("操作成功!");
				$(".jf_szright").load(_basepath + "stuinfo/stuinfo_list.php");
			}
		});
	};
	//批量缴费点击保存
	$('#payButtonBatch1').click(function(){
		var pkids = $("#tmpkid").val();
		var IS_TONGGUO_REASON = $("#IS_TONGGUO_REASON1").val();
		$.post(encodeURI(_basepath+"stuinfo/batchTongGuo.json?pkids="+pkids+'&&status='+'0'+"&IS_TONGGUO_REASON="+IS_TONGGUO_REASON),function(data){
			if(data.result=="success"){
				$("#cancelButtonBatch").click();
				layer.msg("操作成功!");
				$(".jf_szright").load(_basepath + "stuinfo/stuinfo_list.php");
			}
		});
	});
	
	$("#ccyingjiesheng").change(function(){
		var ccyingjiesheng = $("#ccyingjiesheng").find("option:selected").text();
		var ccyingjieshengval = $("#ccyingjiesheng").val();
		if(ccyingjiesheng=="应届生"){
			$("#yingjiesheng").show();
			$("#bxfukesheng").hide();
			$("#wxfukesheng").hide();
			var banxingname = $("#BANJI_TYPE1").find("option:selected").text();
			if(banxingname!="寒假班"&&banxingname!="暑假班"){
				$("#yujiaodiv").hide();
			}else{
				$("#yujiaodiv").show();
			}
		}else if(ccyingjiesheng=="本校复课生"){
			$("#yingjiesheng").hide();
			$("#bxfukesheng").show();
			$("#wxfukesheng").hide();
		}else if(ccyingjiesheng=="外校复课生"){
			$("#yingjiesheng").hide();
			$("#bxfukesheng").hide();
			$("#wxfukesheng").show();
		}
		$("#ccbxfukesheng").val(ccyingjieshengval);
		$("#ccwxfukesheng").val(ccyingjieshengval);
		
	});
	$("#ccbxfukesheng").change(function(){
		var ccbxfukesheng = $("#ccbxfukesheng").find("option:selected").text();
		var ccbxfukeshengval = $("#ccbxfukesheng").val();
		if(ccbxfukesheng=="应届生"){
			$("#yingjiesheng").show();
			$("#bxfukesheng").hide();
			$("#wxfukesheng").hide();
			var banxingname = $("#BANJI_TYPE1").find("option:selected").text();
			if(banxingname!="寒假班"&&banxingname!="暑假班"){
				$("#yujiaodiv").hide();
			}else{
				$("#yujiaodiv").show();
			}
		}else if(ccbxfukesheng=="本校复课生"){
			$("#yingjiesheng").hide();
			$("#bxfukesheng").show();
			$("#wxfukesheng").hide();
		}else if(ccbxfukesheng=="外校复课生"){
			$("#yingjiesheng").hide();
			$("#bxfukesheng").hide();
			$("#wxfukesheng").show();
		}
		$("#ccyingjiesheng").val(ccbxfukeshengval);
		$("#ccwxfukesheng").val(ccbxfukeshengval);
	});
	$("#ccwxfukesheng").change(function(){
		var ccwxfukesheng = $("#ccwxfukesheng").find("option:selected").text();
		var ccwxfukeshengval = $("#ccwxfukesheng").val();
		if(ccwxfukesheng=="应届生"){
			$("#yingjiesheng").show();
			$("#bxfukesheng").hide();
			$("#wxfukesheng").hide();
			var banxingname = $("#BANJI_TYPE1").find("option:selected").text();
			if(banxingname!="寒假班"&&banxingname!="暑假班"){
				$("#yujiaodiv").hide();
			}else{
				$("#yujiaodiv").show();
			}
		}else if(ccwxfukesheng=="本校复课生"){
			$("#yingjiesheng").hide();
			$("#bxfukesheng").show();
			$("#wxfukesheng").hide();
		}else if(ccwxfukesheng=="外校复课生"){
			$("#yingjiesheng").hide();
			$("#bxfukesheng").hide();
			$("#wxfukesheng").show();
		}
		$("#ccyingjiesheng").val(ccwxfukeshengval);
		$("#ccbxfukesheng").val(ccwxfukeshengval);
	});
	$("#BANJI_TYPE1").change(function(){
		var banxingname = $("#BANJI_TYPE1").find("option:selected").text();
		if(banxingname!="寒假班"&&banxingname!="暑假班"){
			$("#yujiaodiv").hide();
		}else{
			$("#yujiaodiv").show();
		}
	});

	//点击重置密码按钮
	$('#btn_resetpwd').bind('click',function(){
		var pkid=$('#tmpkid').val();
		var sfz=$('#SHENFENZHENGHAO').val();
		stuinfo.resetPwd(pkid,sfz);
	});

	$("#RXNIANFEN2").change(function(){//入学年份变更
		$("#OLD_BANJINAME2").html("");
		stuinfo.updateClassList();
	});
	//入学年份下拉变更时，刷新班级列表
	stuinfo.updateClassList = function(){
		var RXNIANFEN_PKID = $("#RXNIANFEN2").val();//入学年份
		var url = _basepath+'signUp/getClassList.json';
		$.ajax({
			type:"post",
			dataType:"json",
			data:{"GRADE":RXNIANFEN_PKID},
			url:url,
			 success:function(data){
				var list = data.list;
				var optionHtml = '<option value="">请选择</option>';
				for(var i=0;i<list.length;i++){
					optionHtml = optionHtml + '<option value="'+list[i].PKID+'">'+list[i].CLASS_NAME+'</option>';
				}
				$("#OLD_BANJINAME2").append(optionHtml);
			  },
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
			          alert(errorThrown); 
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

	
	$(".loadTables").change(function(){
		stuinfo.loadyujiao();
	});
	$("#LKFS2").bind("input propertychange",function(event){//联考分数实时监测
		stuinfo.loadyujiao();
	});
	$("#LKFS3").bind("input propertychange",function(event){//联考分数实时监测
		stuinfo.loadyujiao();
	});
	//点击上传头像按钮
	$("#btn_tx").click(function(){
		stuinfo.uploadTouXiang("tx");
	});
	
	//点击上传身份证按钮
	$("#btn_sfztx").click(function(){
		stuinfo.uploadTouXiang("sfz");
	});
	
	/**
	 * 加载预缴费项目
	 */
	stuinfo.loadyujiao = function(){
		var yingjiesheng = $("#yingjiesheng").is(":hidden");
		var bxfukesheng = $("#bxfukesheng").is(":hidden");
		var wxfukesheng = $("#wxfukesheng").is(":hidden");
		var post_flag = false; //防止ajax多次提交
		var RXNIANFEN = "";//入学年份
		var BANJI_TYPE = "";//班型
		var YJ_NIANFEN = "";//预交年份
		var YJ_BANJI_TYPE = "";//预交班型
		var stat = "";//预缴费项 是否为预交
		//根据层次展示不同的班型详情 获取层次内容***
		var CENGCIHTML = $("#cengCiId").val();
		var WHKXUEXIAO = $("#WHKXUEXIAO").val();//获取文化课学校
         var OLD_BANJI = $("#OLD_BANJINAME2").val();//获取原来班级        
     	 var XK_MARK = "";//获取校考成绩
     	 
         var LKFS = "";//获取联考分数
         var PKID=$("#PKID").val();//获取页面pkid
         if(CENGCIHTML=="本校复课生")
        	 CENGCIHTML="bxfks";
         if(CENGCIHTML=="外校复课生")
        	 CENGCIHTML="wxfks";
         if(CENGCIHTML=="应届生")
        	 CENGCIHTML="yjs";
		//应届生
		if(yingjiesheng == false){
			//获取层次PKID
			cengci=$("#ccyingjiesheng").val();              		
			//获取入学年份PKID
			RXNIANFEN = $("#RXNIANFEN1").val();
			//获取班型PKID
			BANJI_TYPE = $("#BANJI_TYPE1").val();
			//获取预交入学年份PKID
			YJ_NIANFEN = $("#YJ_NIANFEN1").val();
			//获取预交班型PKID
			YJ_BANJI_TYPE = $("#YJ_BANJI_TYPE1").val();
			CENGCIHTML="yjs";

		}
		//本校复课生
		if(bxfukesheng == false){
			//获取层次PKID
			cengci=$("#ccbxfukesheng").val();              		
			//获取入学年份PKID
			 RXNIANFEN = $("#RXNIANFEN2").val();  
			//获取班型PKID
			BANJI_TYPE = $("#BANJI_TYPE2").val();

			//校考成绩				                
			$("#XKCJCHECKBOX2 input[type='checkbox']:checked").each(function(){
				XK_MARK+=$(this).val()+",";
			});
			/*XK_MARK=XK_MARK.substring(0,XK_MARK.length-1);*/
			LKFS = $("#LKFS2").val();//联考分数
			CENGCIHTML="bxfks";
		}
		//外校复课生
		if(wxfukesheng == false){
			//获取层次PKID
			cengci=$("#ccwxfukesheng").val();              		
			//获取入学年份PKID
			RXNIANFEN = $("#RXNIANFEN3").val();
			//获取班型PKID
			BANJI_TYPE = $("#BANJI_TYPE3").val();
			LKFS = $("#LKFS3").val();//联考分数
			//校考成绩				                
			$("#XKCJCHECKBOX3 input[type='checkbox']:checked").each(function(){
				XK_MARK+=$(this).val()+",";
			});
			 CENGCIHTML="wxfks";
		}
		 //应届生
		if(yingjiesheng == false){
			var banxingname = $("#BANJI_TYPE1").find("option:selected").text();
			if(banxingname=="寒假班"||banxingname=="暑假班"){
				stuinfo.yanZhengrxnf();
			}
			if(banxingname!="寒假班"&&banxingname!="暑假班"){
				YJ_NIANFEN="";
				YJ_BANJI_TYPE="";
			}
			
		}
		stat=stuinfo.yanZhengPayType(RXNIANFEN,BANJI_TYPE,YJ_NIANFEN,YJ_BANJI_TYPE,CENGCIHTML);
		 if(post_flag){
			 return;
		 } 
		 post_flag = true;
		 if("XX"!=stat&&""!=stat&&null!=stat)
		 $.post(_basepath+"signUp/loadPayMent.json",{
			 STUPKID:PKID,
			 RXNIANFEN:RXNIANFEN,
			 BANJI_TYPE:BANJI_TYPE,
			 YJ_NIANFEN:YJ_NIANFEN,
			 YJ_BANJI_TYPE:YJ_BANJI_TYPE,
			 payStat:stat,
			 WHKXUEXIAO:WHKXUEXIAO,
			 CENGCIHTML:CENGCIHTML,
			 OLD_BANJI:OLD_BANJI,
			 XK_MARK:XK_MARK,
			 LKFS:LKFS
		 },function(data){
			 console.log(data.msg);
			 stuinfo.loadTable(data);
			 post_flag =false;
		 });
	};
	
	stuinfo.loadTable = function(data){
		$("#yuxianshijine").html("");//置空
		 var head = '';
		 var body = '';
		 var foot = '';
		
		 var list=data.paydata;
		 
		 
		 head = '<div class="pa-row">'+
			'<table class="table table-striped table-bordered jf_table" style="text-align: center;">'+
			'<tr>'+
				'<th width="500">缴费项</th>'+
				'<th>应缴金额</th>'+
			'</tr>';
		 
		 if(typeof(list) != "undefined"&&"error_null"!=data.msg&&list.length>0){
			 for(var i=0;i<list.length;i++){
				 var DISCOUNT_MONEY=list[i].DISCOUNT_MONEY;//优惠金额
				 var RPKID=list[i].RPKID;//优惠规则的pkid
				 var COST=list[i].PAYPRICE;
				 if(typeof(DISCOUNT_MONEY)=="undefined"){
					 DISCOUNT_MONEY=0;
				 }
				 if(typeof(COST)=="undefined"){
					 COST=0;
				 }
				 if(typeof(RPKID)=="undefined"){
					 RPKID='xxx';
				 }
				 body += '<tr>'+
					'<td>'+list[i].PAYNAME+
					'<input class="ipkid" value="'+list[i].IPKID+'" type="hidden">'+
					'<input class="payname" value="'+list[i].PAYNAME+'" type="hidden">'+
					'<input class="payprice" value="'+list[i].PAYPRICES+'" type="hidden">'+
					'<input class="pay_cost" value="'+list[i].PAYPRICE+'" type="hidden">'+
					'<input class="discount_money" value="'+DISCOUNT_MONEY+'" type="hidden">'+
					'<input class="rpkid" value="'+RPKID+'" type="hidden">'+
					'</td>'+
					'<td>'+list[i].PAYCOST.toFixed(2)+'</td>'+
					'</tr>';
			 }
		 }else{
			 
			 body = '<tr><td colspan="2">暂无此年份班型的缴费项</td></tr>'
		 }
		 
		 
		 foot = '</table></div>';
		 
		 $("#yuxianshijine").html(head+body+foot);//加载到页面
		 $("#yuxianshijinediv").show();
		 $(".jf_table tr:last").attr("class","bg-danger");
		 $(".jf_table tr:last").find("input[type='hidden']").attr("class","");
	};
	
	/**
	 * 验证预缴费项 是正常状态 还是预交状态
	 * ZC正常  YJ预交 
	 */
	stuinfo.yanZhengPayType = function(RXNIANFEN,BANJI_TYPE,YJ_NIANFEN,YJ_BANJI_TYPE,CENGCIHTML){
		stat="XX";
		 
		 if(RXNIANFEN!=""&&BANJI_TYPE!=""){//显示非预交的 缴费项
			 stat="ZC";
		 }
		 if(YJ_NIANFEN!=""&&YJ_BANJI_TYPE!=""&&CENGCIHTML=="yjs"){//显示预交后的 缴费项
			 stat="YJ";
		 }
		 return stat;
	};
	stuinfo.yanZhengrxnf = function(){
		var YJ_NIANFENhtml=$("#YJ_NIANFEN1").find("option:selected").text();//预交年份
		var YJ_BANJI_TYPE_PKID=$("#YJ_BANJI_TYPE1").find("option:selected").text();//预交班型
		var BANJI_TYPE_PKID=$("#BANJI_TYPE1").find("option:selected").text();//班型
		var RXNIANFEN_PKIDhtml=$("#RXNIANFEN1").find("option:selected").text();//入学年份
		if(YJ_NIANFENhtml!="请选择"&&YJ_BANJI_TYPE_PKID!="请选择"){
			if(BANJI_TYPE_PKID==YJ_BANJI_TYPE_PKID){
				if(YJ_NIANFENhtml<=RXNIANFEN_PKIDhtml){
					layer.msg("预交年份必须大于入学年份");
					$("#YJ_NIANFEN1").val("");
					$("#YJ_NIANFEN1").focus();
				}				
			}			
		}
		return false;
	};
	
})(jQuery, window);

$(function(){
	//根据层次展示不同的班型详情
	var cengcival = $("#cengCiId").val();
	if(cengcival=="应届生"){
		$("#yingjiesheng").show();
		$("#bxfukesheng").hide();
		$("#wxfukesheng").hide();
		var banxingname = $("#BANJI_TYPE1").find("option:selected").text();
		if(banxingname!="寒假班"&&banxingname!="暑假班"){
			$("#yujiaodiv").hide();
		}else{
			$("#yujiaodiv").show();
		}
	}else if(cengcival=="本校复课生"){
		$("#yingjiesheng").hide();
		$("#bxfukesheng").show();
		$("#wxfukesheng").hide();
	}else if(cengcival=="外校复课生"){
		$("#yingjiesheng").hide();
		$("#bxfukesheng").hide();
		$("#wxfukesheng").show();
	}
	
	for(var i=0;i<xkmark.length;i++){
		$("input[name='checkbox']").each(function() {  	           
        	if($(this)[0].value==xkmark[i].PKID){
        		$(this)[0].checked=true;
        	}
		});  
	}
	
	for(var i=0;i<kemus.length;i++){
		$("input[name='checkbox1']").each(function() {  	           
			if($(this)[0].value==kemus[i].BIANMA){
				$(this)[0].checked=true;
			}
		});  
	}
	$("#yuxianshijinediv").hide();

	//解决头像更换后的缓存问题
	if(_imgsrc!=''){
		$('#touxiang').attr('src',_basepath+_imgsrc+"?r="+Math.random()); 
	}else{
		$('#touxiang').attr('src',_basepath+"static/gxjf/images/defaulttouxiang.jpg");  
	}

	if(_imgsrc1!=''){
		$('#touxiangs').attr('src',_basepath+_imgsrc1+"?r="+Math.random()); 
	}else{
		$('#touxiangs').attr('src',_basepath+"static/gxjf/images/newshenfenzheng.jpg");  
	}
	
	var RXNIANFEN_PKID = $("#RXNIANFEN2").val();//入学年份
	var OLD_BANJI = $("#OLD_BANJI").val();
	var url = _basepath+'signUp/getClassList.json';
	$.ajax({
		type:"post",
		dataType:"json",
		data:{"GRADE":RXNIANFEN_PKID},
		url:url,
		 success:function(data){
			var list = data.list;
			var optionHtml = '<option value="">请选择</option>';
			for(var i=0;i<list.length;i++){
				if(list[i].PKID==OLD_BANJI){
					optionHtml = optionHtml + '<option selected="selected" value="'+list[i].PKID+'">'+list[i].CLASS_NAME+'</option>';
				}else{
					optionHtml = optionHtml + '<option value="'+list[i].PKID+'">'+list[i].CLASS_NAME+'</option>';
				}
			}
			$("#OLD_BANJINAME2").append(optionHtml);
		  },
		error: function (XMLHttpRequest, textStatus, errorThrown) { 
		          alert(errorThrown); 
		}
	});
});