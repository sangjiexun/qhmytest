//@ sourceURL=addstus.js 
/**
 * 学生信息新增对象
 */
(function($, window) {
	var stuinfo = {};
	var createpay = {};
    $("#mybuttontest").click(function(){
		 $("#touxiang").attr('src',_basepath+$("#STUTOUXIANG").val()+"?v="+Math.random());  
   });
	//日期插件参数设置
	$('.date-picker').datepicker({
		autoclose: true,
		todayHighlight: false,
		format: 'yyyy-mm',
		startView: 1,
        minViewMode:1,
        endYear:'+1m'
	});
	
	stuinfo.resfHtml = function(){
		$("#PKID").val("");

		if($("#STUTOUXIANG").val()=="")
			$('#touxiang').attr('src',_basepath+"static/gxjf/images/defaulttouxiang.jpg"); 
    		if($("#SFZTOUXIANG").val()=="")
    			$('#touxiangs').attr('src',_basepath+"static/gxjf/images/newshenfenzheng.jpg");  
		
		
		$("#YJ_NIANFEN").attr("disabled",false);
		$("#YJ_BANJI_TYPE_PKID").attr("disabled",false);
		$("#YJ_NIANFEN").val("");
		$("#YJ_BANJI_TYPE_PKID").val("");
		//$("select").val("");

		$("input[type='checkbox']").attr("checked",false);
	
		
		
	};
	
	
	/**
	 * 显示预缴费项目事件 先绑定change
	 */
	

	
/*	$("body").on({
		
		change : function(){
			createpay.loadyujiao();
		},
		click : function(){
			
		}
		
	},'#RXNIANFEN_PKID,#BANJI_TYPE_PKID,#YJ_NIANFEN,#YJ_BANJI_TYPE_PKID,#XKCJCHECKBOX input[type="checkbox"],#WHKXUEXIAO');
	*/
	
	$(".loadTables").change(function(){
		createpay.loadyujiao();
	});
	
	$("#LKFS").bind("input propertychange",function(event){//联考分数实时监测
		createpay.loadyujiao();
	});
	$("#RXNIANFEN_PKID").change(function(){//入学年份变更
		$("#OLD_BANJI").html("");
		stuinfo.updateClassList();
	});
	
	//入学年份下拉变更时，刷新班级列表
	stuinfo.updateClassList = function(){
		var RXNIANFEN_PKID = $("#RXNIANFEN_PKID").val();//入学年份
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
				$("#OLD_BANJI").append(optionHtml);
			  },
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
			          alert(errorThrown); 
			}
		});
	};
	/**
	 * 加载预缴费项目
	 */
	createpay.loadyujiao = function(){
		var post_flag = false; //防止ajax多次提交
		 var RXNIANFEN=$("#RXNIANFEN_PKID").val();//入学年份
		 var BANJI_TYPE=$("#BANJI_TYPE_PKID").val();//班型
		 var YJ_NIANFEN=$("#YJ_NIANFEN").val();//预交年份
		 var YJ_BANJI_TYPE=$("#YJ_BANJI_TYPE_PKID").val();//预交班型
		 var stat="";//预缴费项 是否为预交
		 var WHKXUEXIAO=$("#WHKXUEXIAO").val();			    //获取文化课学校
		 var CENGCIHTML=$("#stu_cc").find("option:selected").text();//获取层次内容***
		 var BANJITYPEHTML=$("#BANJI_TYPE_PKID").find("option:selected").text();//获取层次内容***

         var OLD_BANJI=$("#OLD_BANJI").val();			//获取原来班级
     	 var XK_MARK=" ";				                //获取校考成绩
         var LKFS=$("#LKFS").val();			        	//获取联考分数
         var fix_Test=/^(([1-9]\d*)|\d)(\.\d{1,2})?$/;
         if(LKFS.trim()!='' && !fix_Test.test(LKFS)){
        	layer.msg("联考分数不合法！");
			$("#LKFS").focus();
			return false; 
         }
         var PKID=$("#PKID").val();//获取页面pkid
         if(CENGCIHTML=="本校复课生")
        	 CENGCIHTML="bxfks";
         if(CENGCIHTML=="外校复课生")
        	 CENGCIHTML="wxfks";
         if(CENGCIHTML=="应届生")
        	 CENGCIHTML="yjs";
         
         
         if(BANJITYPEHTML=="寒假班"||BANJITYPEHTML=="暑假班"){
        	 BANJITYPEHTML=true;
         }else{
        	 BANJITYPEHTML=false;
         }
		 console.log(BANJITYPEHTML);


		 $("#XKCJCHECKBOX input[type='checkbox']:checked").each(function(){
		 	XK_MARK+=$(this).val()+",";
	     });
		 stat=createpay.yanZhengPayType(RXNIANFEN,BANJI_TYPE,YJ_NIANFEN,YJ_BANJI_TYPE,CENGCIHTML,BANJITYPEHTML);
		 
		 console.log(stat);
		 
		 if(createpay.yanZhengrxnf()&&CENGCIHTML=="yjs"){
			 createpay.loadTable("1");
			 return;
		 }
		 
		 
		 
		 if(post_flag) return; 
		 
		 
 var BANXINGHTML=$("#BANJI_TYPE_PKID").find("option:selected").text();//获取层次内容***
		 
		 if(BANXINGHTML!="寒假班"&&BANXINGHTML!="暑假班"){
			/* 
			 *  $("#YJ_NIANFEN").val("");
				$("#YJ_BANJI_TYPE_PKID").val("");
				$("#YJ_NIANFEN").attr("disabled",false);
				$("#YJ_BANJI_TYPE_PKID").attr("disabled",false);*/
				YJ_NIANFEN="";
				YJ_BANJI_TYPE="";
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
			 createpay.loadTable(data);
			 post_flag =false;
		 });
	}
	
	
	createpay.loadTable = function(data){
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
				 var RPKID=list[i].RPKID//优惠规则的pkid
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
					'<input class="pay_cost" value="'+COST+'" type="hidden">'+//原金额
					'<input class="discount_money" value="'+DISCOUNT_MONEY+'" type="hidden">'+//优惠金额
					'<input class="payprice" value="'+list[i].PAYCOST.toFixed(2)+'" type="hidden">'+//减免后金额
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
		 $(".jf_table tr:last").css("background-color","#f2dede");
		 $(".jf_table tr:last").find("input[type='hidden']").attr("class","");
	}
	

	createpay.yanZhengrxnf = function(){
		var YJ_NIANFENhtml=$("#YJ_NIANFEN").find("option:selected").text();//获取层次内容***
		var YJ_BANJI_TYPE_PKID=$("#YJ_BANJI_TYPE_PKID").find("option:selected").text();//获取层次内容***
		var BANJI_TYPE_PKID=$("#BANJI_TYPE_PKID").find("option:selected").text();//获取层次内容***
		var RXNIANFEN_PKIDhtml=$("#RXNIANFEN_PKID").find("option:selected").text();//获取层次内容***
		
		if(YJ_NIANFENhtml!="请选择"&&YJ_BANJI_TYPE_PKID!="请选择"){
			
			
			
			
			/*if(YJ_NIANFENhtml==RXNIANFEN_PKIDhtml){//如果预交年份跟入学年份相等的话
				if(BANJI_TYPE_PKID==YJ_BANJI_TYPE_PKID){//班型不能相等
					layer.msg("班型不能相同");
					return true;
				}
				
			}
			
			if(BANJI_TYPE_PKID==YJ_BANJI_TYPE_PKID){//如果班型相等的话
	
				if(YJ_NIANFENhtml<=RXNIANFEN_PKIDhtml){//预交年份小于等于入学年份的话
					layer.msg("预交年份要大于入学年份");
					return true;
				}
			}
			*/
			
		}
	
		
		return false;
	}
	/**
	 * 验证预缴费项 是正常状态 还是预交状态
	 * ZC正常  YJ预交 
	 */
	createpay.yanZhengPayType = function(RXNIANFEN,BANJI_TYPE,YJ_NIANFEN,YJ_BANJI_TYPE,CENGCIHTML,BANJITYPEHTML){
		stat="XX";
		 
		 if(RXNIANFEN!=""&&BANJI_TYPE!=""){//显示非预交的 缴费项
			 stat="ZC";
		 }
		 if(YJ_NIANFEN!=""&&YJ_BANJI_TYPE!=""&&CENGCIHTML=="yjs"&&BANJITYPEHTML){//显示预交后的 缴费项
			 stat="YJ";
		 }
		 
		 return stat;
	};
	

	
	
	
	
	stuinfo.uploadStudentInfo = function(sfz){
		$.ajax({  
	            type : "POST",  
	            url :_basepath+"signUp/getStuInFo.json",  
	            dateType : "json",  
	            data : {  
	            	SHENFENZHENGHAO:sfz,
	            },  
	            success : function(data) {
	            	if("SUCCESS"==data.res_yj){
	            		var stu_yj=data.str_yj;
	            		

	            		if(stu_yj.GRADE_PKID!=null&&stu_yj.CLASSTYPE_PKID!=null){
	            			$("#YJ_NIANFEN").val(stu_yj.GRADE_PKID);
		            		$("#YJ_BANJI_TYPE_PKID").val(stu_yj.CLASSTYPE_PKID);
		            		$("#YJ_NIANFEN").attr("disabled","disabled");
		            		$("#YJ_BANJI_TYPE_PKID").attr("disabled","disabled");
		            		$("#IS_YJ").val("Y");
		            		
	            		}
	            	
	            	}
	            	
	            	if("SUCCESS"==data.res){
	            		var stu=data.str;
	            		if(stu.TOUXIANG!=""&&stu.TOUXIANG!=null){
		            		 $('#touxiang').attr('src',_basepath+stu.TOUXIANG+"?t="+ new Date().getTime());  
	            		}
	            		if(stu.SFZ_IMG!=""&&stu.SFZ_IMG!=null){
		            		 $('#touxiangs').attr('src',_basepath+stu.SFZ_IMG+"?t="+ new Date().getTime());  
	            		}

	            /*		alert(stu_yj.GRADE_PKID);
	            		alert(stu_yj.CLASSTYPE_PKID);*/
	            		
	            		$("#PKID").val(stu.PKID);
	            		$("#XINGMING").val(stu.XINGMING);
	            		$("#XINGBIE").val(stu.XINGBIE=="M"?"男":"女");
	            		$("#SHOUJI").val(stu.SHOUJI);
	            		$("#JIATINGZHUZHI").val(stu.JIATINGZHUZHI);
	            		$("#YOUZHENGBIANMA").val(stu.YOUZHENGBIANMA);
	            		$("#ONE_JHR").val(stu.ONE_JHR);
	            		$("#ONE_JHRGX_PKID").val(stu.ONE_JHRGX_PKID);
	            		$("#ONE_JHRDH").val(stu.ONE_JHRDH);
	            		$("#ONE_JHRDW").val(stu.ONE_JHRDW);
	            		$("#ONE_JHRZW").val(stu.ONE_JHRZW);
	            		$("#TWO_JHR").val(stu.TWO_JHR);
	            		$("#TWO_JHRGX_PKID").val(stu.TWO_JHRGX_PKID);
	            		$("#TWO_JHRDH").val(stu.TWO_JHRDH);
	            		$("#TWO_JHRDW").val(stu.TWO_JHRDW);
	            		$("#TWO_JHRZW").val(stu.TWO_JHRZW);
	            		$("#BINGSHI").val(stu.BINGSHI);
	            		$("#LJQHTJ_PKID").val(stu.LJQHTJ_PKID);
	            		$("#TUIJIANREN").val(stu.TUIJIANREN);
	            		$("#WHKXUEXIAO").val(stu.WHKXUEXIAO);
	            		$("#GYRXSJ").val(stu.GYRXSJ);
	            		$("#WENLIKE").val(stu.WENLIKE);
	            		$("#BZRSCHOOL").val(stu.BZRSCHOOL);
	            		$("#BZRPHONE").val(stu.BZRPHONE);
	            		$("#STARTMEISHU").val(stu.STARTMEISHU);
	            		$("#GZ_BANJI").val(stu.GZ_BANJI);
	            		
	            		
	            		if($("#STUTOUXIANG").val()=="")
	            		$("#STUTOUXIANG").val(stu.TOUXIANG);
	            		if($("#SFZTOUXIANG").val()=="")
	            		$("#SFZTOUXIANG").val(stu.SFZ_IMG);

	            		if(stu.KEMU!=null){
	            			var kmlist=stu.KEMU.split(",");
		            		
		            		for(var i=0;i<kmlist.length;i++){
		 	            		console.log(kmlist[i]);

		            			$("#YXGKMCHECKBOX input[value='"+kmlist[i]+"']").attr("checked",true);
		            		}
	            		}
	            		
	            		
	            	}
	            	
	            	if("ERROR"==data.res){
	            		var sfzs=data.sfz;
	            		var xingBie = sfzs.charAt(sfzs.length-2);
	            		xingBie = xingBie % 2 == 1 ? "男" : "女";
	            		stuinfo.resfHtml("","");
	            		$("#XINGBIE").val(xingBie); 
	            		$("#SHENFENZHENGHAO").val(sfzs);

	            	}
				
	            }  
	        });
	}
	
	
	//身份证号验证以及出生日期和性别根据身份证号读取
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
			stuinfo.uploadStudentInfo(sfz);
		}
	});
	
	  //入学年份发生改变后,判断预交年份是否大与等于入学年份   等于显示预交年份   
		/*$("#RXNIANFEN_PKID").bind("change",function(){
			var sfz = $('#SHENFENZHENGHAO').val();

			$.post(_basepath+"signUp/getStuInFo.json",{SHENFENZHENGHAO:sfz},function(data){
					if("SUCCESS"==data.res){
						var rxnf=$("#RXNIANFEN_PKID").find("option:selected").text();//获取选择入学年份

						var yrxnf=data.str.YJNFS;						//获取数据库预交年份
					
						if(yrxnf>=rxnf){								//如果选择年份小于预交年份的话  自动填充预交年份
							$("#YJ_NIANFEN").val(data.str.YJ_NIANFEN);
						}else{
							$("#YJ_NIANFEN").val("");
						}
						
					}
			});
	
	
		});
	*/
		
	
	
	stuinfo.yanzheng = function(){
		var XINGMING=$("#XINGMING").val();              //获取姓名
		if(XINGMING == ''){
			layer.msg("学生姓名不能为空");
			$("#XINGMING").focus();
			return false;
		}
		
		var XINGBIE=$("#XINGBIE").val();				//获取性别
		if(XINGBIE == ''){
			layer.msg("学生性别不能为空");
			$("#XINGBIE").focus();
			return false;
		}
		
		var SHOUJI=$("#SHOUJI").val();			    	//获取手机
		if(SHOUJI == ''){
			layer.msg("手机号不能为空");
			$("#SHOUJI").focus();
			return false;
		}
		
		if(SHOUJI.length<11){
			layer.msg("手机号长度不足11位");
			$("#SHOUJI").focus();
			return false;
		}
		if(!(/^1[345789]\d{9}$/.test(SHOUJI))){ 
			layer.msg("手机号码有误，请重填");  
	        return false; 
	    }
		
		var ONE_JHR=$("#ONE_JHR").val();				//获取监护人1姓名
		if(ONE_JHR == ''){
			layer.msg("第一监护人不能为空");
			$("#ONE_JHR").focus();
			return false;
		}
		
		var ONE_JHRGX_PKID=$("#ONE_JHRGX_PKID").val();	//获取监护人1家庭关系PKID
		if(ONE_JHRGX_PKID == ''){
			layer.msg("第一监护人家庭关系不能为空");
			$("#ONE_JHRGX_PKID").focus();
			return false;
		}
		
		var ONE_JHRDH=$("#ONE_JHRDH").val();			//获取监护人1电话
		if(ONE_JHRDH == ''){
			layer.msg("第一监护人电话不能为空");
			$("#ONE_JHRDH").focus();
			return false;
		}else if(ONE_JHRDH.length!=11){
			layer.msg("第一监护人电话格式不正确");
			$("#ONE_JHRDH").focus();
			return false;
		}
		var TWO_JHRDH=$("#TWO_JHRDH").val();			//获取监护人2电话
		if(TWO_JHRDH != '' && TWO_JHRDH.length!=11 ){
			layer.msg("第二监护人电话格式不正确");
			$("#TWO_JHRDH").focus();
			return false;
		}
		
		var WHKXUEXIAO=$("#WHKXUEXIAO").val();			    //获取文化课学校
		if(WHKXUEXIAO == ''){
			layer.msg("文化课学校不能为空");
			$("#WHKXUEXIAO").focus();
			return false;
		}
		
		var BZRPHONE=$("#BZRPHONE").val();			//班主任电话
		if(BZRPHONE != '' && BZRPHONE.length!=11){
			layer.msg("班主任电话格式不正确");
			$("#BZRPHONE").focus();
			return false;
		}
		
		var CENGCI_PKID=$("#stu_cc").val();              //获取层次PKID
		if(CENGCI_PKID == ''){
			layer.msg("学生类型不能为空");
			$("#CENGCI_PKID").focus();
			return false;
		}
		
		var RXNIANFEN_PKID=$("#RXNIANFEN_PKID").val();  //获取入学年份PKID
		if(RXNIANFEN_PKID == ''){
			layer.msg("入学年份不能为空");
			$("#RXNIANFEN_PKID").focus();
			return false;
		}
		
		var BANJI_TYPE_PKID=$("#BANJI_TYPE_PKID").val();//获取班型PKID
		if(BANJI_TYPE_PKID == ''){
			layer.msg("班型不能为空");
			$("#BANJI_TYPE_PKID").focus();
			return false;
		}
		
		var CENGCIHTML=$("#stu_cc").find("option:selected").text();//获取层次内容***
		var BANXINGHTML=$("#BANJI_TYPE_PKID").find("option:selected").text();//获取层次内容***

		var YJ_NIANFEN=$("#YJ_NIANFEN").val();          //获取预交年份
		var YJ_BANJI_TYPE_PKID=$("#YJ_BANJI_TYPE_PKID").val();//获取预交班型
		if(BANXINGHTML == "寒假班" || BANXINGHTML=="暑假班")
		if(CENGCIHTML=="应届生"){
			if(YJ_NIANFEN != ''){
				if(YJ_BANJI_TYPE_PKID == ""){
					layer.msg("预交班型不能为空");
					$("#YJ_BANJI_TYPE_PKID").focus();
					return false;
				}
				
				var YJ_NIANFENhtml=$("#YJ_NIANFEN").find("option:selected").text();//获取层次内容***
				var RXNIANFEN_PKIDhtml=$("#RXNIANFEN_PKID").find("option:selected").text();//获取层次内容***
				
				if(YJ_NIANFENhtml<RXNIANFEN_PKIDhtml){
					layer.msg("预交年份必须大于入学年份");
					$("#YJ_NIANFEN").focus();
					return false;
				}

			}
			if(YJ_BANJI_TYPE_PKID != ''){
				if(YJ_NIANFEN == ""){
					layer.msg("预交年份不能为空");
					$("#YJ_NIANFEN").focus();
					return false;
				}
			}
		}
	    if(CENGCIHTML=="本校复课生"){
	    	var LKFS=$("#LKFS").val();			        	//获取联考分数

			///^[1-9]\d*(\.\d)?$/g
			if(LKFS!=""&&LKFS.length>0){
				if(!(/^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/.test(LKFS))){ 
					layer.msg("联考分数只能填写整数或小数（小数后保留两位）");		  
					$("#LKFS").focus();
					return false; 
			    }
			}
			
	    }
		
		
		
		
		return true;
		
	}
	
	// 保存方法
	stuinfo.save = function() {
		var CENGCIHTML=$("#stu_cc").find("option:selected").text();//获取层次内容***
		var PKID=$("#PKID").val();//获取页面pkid
		var SHENFENZHENGHAO=$("#SHENFENZHENGHAO").val();//获取页面身份证号
		var XINGMING=$("#XINGMING").val();              //获取姓名
		var XINGBIE=$("#XINGBIE").val();				//获取性别
		if(XINGBIE=="男"){
			XINGBIE="M";
		}else{
			XINGBIE="W";
		}
		var SHOUJI=$("#SHOUJI").val();			    	//获取手机
		var JIATINGZHUZHI=$("#JIATINGZHUZHI").val();	//获取家庭住址
		var YOUZHENGBIANMA=$("#YOUZHENGBIANMA").val();  //获取邮政编码	
		var ONE_JHR=$("#ONE_JHR").val();				//获取监护人1姓名
		var ONE_JHRGX_PKID=$("#ONE_JHRGX_PKID").val();	//获取监护人1家庭关系PKID
		var ONE_JHRDH=$("#ONE_JHRDH").val();			//获取监护人1电话
		var ONE_JHRDW = $("#ONE_JHRDW").val();          //监护人1单位
		var ONE_JHRZW = $("#ONE_JHRZW").val();          //监护人1职务
		var TWO_JHR=$("#TWO_JHR").val();				//获取监护人2姓名
		var TWO_JHRGX_PKID=$("#TWO_JHRGX_PKID").val();	//获取监护人2家庭关系PKID
		var TWO_JHRDH=$("#TWO_JHRDH").val();			//获取监护人2电话
		var TWO_JHRDW = $("#TWO_JHRDW").val();          //监护人2单位
		var TWO_JHRZW = $("#TWO_JHRZW").val();          //监护人2职务
		var BINGSHI=$("#BINGSHI").val();				//获取病史
		var LJQHTJ_PKID=$("#LJQHTJ_PKID").val();		//获取了解强化的途径PKID
		var TUIJIANREN=$("#TUIJIANREN").val();			//获取推荐人
		var WHKXUEXIAO=$("#WHKXUEXIAO").val();			//获取文化课学校
		var GYRXSJ=$("#GYRXSJ").val();				    //获取高一入学时间
		var GZ_BANJI=$("#GZ_BANJI").val();				//获取高中班级
		var WENLIKE=$("#WENLIKE").val();			    //获取文、理科
		var BZRSCHOOL=$("#BZRSCHOOL").val();			//获取学校班主任
		var BZRPHONE=$("#BZRPHONE").val();				//获取班主任电话
		var CENGCI_PKID=$("#stu_cc").val();              //获取层次PKID
		var STARTMEISHU=$("#STARTMEISHU").val();		//获取自何时学习美术
		var KSNUMBER=$("#KSNUMBER").val();				//获取考生号
		var LKFS=$("#LKFS").val();			        	//获取联考分数
		var OLD_BANJI=$("#OLD_BANJI").val();			//获取原来班级
		var RXNIANFEN_PKID=$("#RXNIANFEN_PKID").val();  //获取入学年份PKID
		var BANJI_TYPE_PKID=$("#BANJI_TYPE_PKID").val();//获取班型PKID
		var YJ_NIANFEN=$("#YJ_NIANFEN").val();          //获取预交年份
		var YJ_BANJI_TYPE_PKID=$("#YJ_BANJI_TYPE_PKID").val();//获取预交班型
		var REMARKS1=$("#REMARKS1").val();				//获取备注1
		var REMARKS2=$("#REMARKS2").val();				//获取备注2
		var REMARKS3=$("#REMARKS3").val();				//获取备注3
		var TOUXIANG=$("#STUTOUXIANG").val(); 			//头像图片路径
		var SFZ_IMG=$("#SFZTOUXIANG").val();			//身份证 图片路径
		var ZXZT='ZX';			                    	//在学状态 默认为在学
		
		var KEMU="";			        	            //获取已学过科目 SX速写 SC色彩 SM素描
		$("#YXGKMCHECKBOX input[type='checkbox']:checked").each(function(){
			KEMU+=$(this).val()+",";
		});
		KEMU=KEMU.substring(0,KEMU.length-1);
		
		var XK_MARK=" ";				                //获取校考成绩
		$("#XKCJCHECKBOX input[type='checkbox']:checked").each(function(){
			XK_MARK+=$(this).val()+",";
		});

		var IPKID="";
		$(".ipkid").each(function(){
			IPKID+=$(this).val()+",";
		});
		var PAYNAME="";
		$(".payname").each(function(){
			PAYNAME+=$(this).val()+",";
		});
		var PAYPRICE="";//优惠后的金额
		$(".payprice").each(function(){
			PAYPRICE+=$(this).val()+",";
		});
		var DISCOUNT_MONEY="";//优惠金额
		$(".discount_money").each(function(){
			DISCOUNT_MONEY+=$(this).val()+",";
		});
		var RPKID="";
		$(".rpkid").each(function(){
			RPKID+=$(this).val()+",";
		});
		var PAYCOST="";//原金额
		$(".pay_cost").each(function(){
			PAYCOST+=$(this).val()+",";
		});
		
		//'<input class="discount_money" value="'+list[i].DISCOUNT_MONEY+'" type="">'+
		//'<input class="rpkid" value="'+list[i].RPKID+'" type="">'+
		
	
	

	
		
		
		if(CENGCIHTML=="应届生"){
			KSNUMBER="";
			LKFS="";
			OLD_BANJI="";
			XK_MARK="";
		}
		
		if($("IS_YJ").val()=="N"){
			if(CENGCIHTML=="本校复课生"){
				YJ_NIANFEN="";
				YJ_BANJI_TYPE_PKID="";
			}
			if(CENGCIHTML=="外校复课生"){
				OLD_BANJI="";
				YJ_NIANFEN="";
				YJ_BANJI_TYPE_PKID="";
			}
		}
		
		//验证身份证号
		if(checkCard(SHENFENZHENGHAO)==false){
			return false;
		}
		
		if(!stuinfo.yanzheng()){
			return false;
		}
				
		
		
		//	'<input id="IPKID" value="'+list[i].IPKID+'" type="hidden">'+
		//	'<input id="PAYNAME" value="'+list[i].PAYNAME+'" type="hidden">'+
		//	'<input id="PAYPRICE" value="'+list[i].PAYPRICE+'" type="hidden">'+
	
		var url=_basepath+"signUp/save.json";
		var data={
				 sign:'add',
				 PKID:PKID,
				 IPKID:IPKID,
				 PAYNAME:PAYNAME,
				 PAYCOST:PAYCOST,
				 PAYPRICE:PAYPRICE,
				 DISCOUNT_MONEY:DISCOUNT_MONEY,
				 RPKID:RPKID,
			     SHENFENZHENGHAO:SHENFENZHENGHAO,
				 XINGMING:XINGMING,              
				 XINGBIE:XINGBIE,				
				 SHOUJI:SHOUJI,			    	
				 JIATINGZHUZHI:JIATINGZHUZHI,	
				 YOUZHENGBIANMA:YOUZHENGBIANMA, 
				 ONE_JHR:ONE_JHR,				
				 ONE_JHRGX_PKID:ONE_JHRGX_PKID,	
				 ONE_JHRDH:ONE_JHRDH,
				 ONE_JHRDW:ONE_JHRDW,
				 ONE_JHRZW:ONE_JHRZW,
				 TWO_JHR:TWO_JHR,			
				 TWO_JHRGX_PKID:TWO_JHRGX_PKID,	
				 TWO_JHRDH:TWO_JHRDH,	
				 TWO_JHRDW:TWO_JHRDW,
				 TWO_JHRZW:TWO_JHRZW,
				 BINGSHI:BINGSHI,				
				 LJQHTJ_PKID:LJQHTJ_PKID,		
				 TUIJIANREN:TUIJIANREN,			
				 WHKXUEXIAO:WHKXUEXIAO,			
				 GYRXSJ:GYRXSJ,				    
				 GZ_BANJI:GZ_BANJI,				
				 WENLIKE:WENLIKE,			    
				 BZRSCHOOL:BZRSCHOOL,			
				 BZRPHONE:BZRPHONE,				
				 CENGCI_PKID:CENGCI_PKID,              
				 STARTMEISHU:STARTMEISHU,		
				 KEMU:KEMU,			        	
				 KSNUMBER:KSNUMBER,				
				 LKFS:LKFS,			        	
				 OLD_BANJI:OLD_BANJI,			
				 XK_MARK:$.trim(XK_MARK),				
				 RXNIANFEN_PKID:RXNIANFEN_PKID,  
				 YJ_NIANFEN:YJ_NIANFEN,
				 YJ_BANJI_TYPE_PKID:YJ_BANJI_TYPE_PKID,
				 BANJI_TYPE_PKID:BANJI_TYPE_PKID,
				 REMARKS1:REMARKS1,				
				 REMARKS2:REMARKS2,				
				 REMARKS3:REMARKS3,				
				 TOUXIANG:TOUXIANG,							
				 SFZ_IMG:SFZ_IMG,		
				 ZXZT:ZXZT		
		};
		$.post(url,data,function(data){
			 if("SUCCESS"==data.res){
				layer.msg('报名信息保存成功!');
            	//stuinfo.resfHtml();
            	//$("#yuxianshijine").html("");//置空
				var url = 'signUp/sign_list.php';
				var r = Math.random();
				url = _basepath + url +"?r="+r;
				$('.jf_szright').html("");
				layer.load(1, {
					  shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
				try {
					$('.jf_szright').load(url,function(){
						layer.closeAll('loading');
					});
				} catch (e) {
					 layer.closeAll('loading');
				}
				
				
				
			 }else if("IDENTICAL"==data.res){
				layer.msg('该学生已经有该入学年份和班型的组合!');
         		
			 }else{
				 layer.msg('网络故障');
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
			        	            		$('#touxiang').attr('src',""); 

				        	            	if (typeof(dat.path) == "undefined"){
				        	            		$('#touxiang').attr('src',_basepath+"static/gxjf/images/defaulttouxiang.jpg"); 

				        	            	}else{
				        	            		 $("#touxiang").attr('src',_basepath+dat.path+"?t="+Math.random());  
				        	            		$("#STUTOUXIANG").val(dat.path);
				        	            		
				        	            		
				        	            		
//				        	            		for(var i=0;i<=20;i++){
//				        	            	       var time=100*i;
//				        	            			setTimeout(function(){
//					        	            			$("#mybuttontest").click();
//					        	            		}, time );
//				        	            		}
//				        	            		setTimeout(function(){
//				        	            			$("#mybuttontest").click();
//				        	            		}, 3000 );
				        	            	
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
		stuinfo.uploadTouXiang("tx");
	});
	
	//点击上传身份证按钮
	$("#btn_sfztx").click(function(){
		stuinfo.uploadTouXiang("sfz");
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
	
});