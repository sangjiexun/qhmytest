
//@ sourceURL=otherpay.js
//其他缴费项设置页面js
var otherpay={};
otherpay.stuinfo={};// student's infomation 
otherpay.tzzy="";
otherpay.tzxm="";
otherpay.stu_pkid="";
otherpay.psg="";
otherpay.requrl='';
otherpay.old_department_id='';//原来的组织id
otherpay.old_nianji='';//原来的年级
var blg=null;
//原组织名称
otherpay.stu_old_zuzhiname="";

//两小数相加解决精度出现的问题
otherpay.numAdd=function (num1, num2) {//要相加的两个数
	var baseNum, baseNum1, baseNum2;
	     try {
	baseNum1 = num1.toString().split(".")[1].length;
	} catch (e) {
	baseNum1 = 0;
	}
	try {
	baseNum2 = num2.toString().split(".")[1].length;
	} catch (e) {
	baseNum2 = 0;
	}
	baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
	return (num1 * baseNum + num2 * baseNum) / baseNum;
};

//选择一个学生查询
var doSearchForOneStudent = function(studentPkid){
	blg.close();
	otherpay.stu_pkid = studentPkid;
	var pay_item_pkid = $("#otherpayitem").val();
	if(otherpay.stuinfo!=null){
		$("#name").html(otherpay.stuinfo.XINGMING);
		$("#sfz").html(otherpay.stuinfo.SHENFENZHENGHAO);
		$("#xuehao").html(otherpay.stuinfo.XUEHAO);
		$("#xiaoqu").html(otherpay.stuinfo.XIAOQU);
		$("#zuzhi").html(otherpay.stuinfo.ZUZHINAME);
		otherpay.stu_old_zuzhiname=otherpay.stuinfo.ZUZHI;
		$("#zxzt").html(otherpay.stuinfo.ZXZT);
		$("#nianji").html(otherpay.stuinfo.NIANJI);
		otherpay.stu_pkid = studentPkid;
		otherpay.psg=otherpay.stuinfo.PASSMSG;
		otherpay.old_department_id=otherpay.stuinfo.DEPARTMENT_PKID;
		otherpay.old_nianji=otherpay.stuinfo.NIANJI;
		otherpay.requrl=otherpay.stuinfo.METHODURL;
		$("#btn_qtjf").attr("disabled", false);
		$("#btn_qtjf").click(function() {
			$('#mymodal').modal({
				keyboard : true
			});
		});
		var opt = {
				url : _basepath + "pay/otherpaytable.json",
				silent : true,
				query : {
					"PAY_ITEM_PKID" : pay_item_pkid,
					"STUDENT_PKID" : otherpay.stu_pkid
				}
			};
	$("#optable").bootstrapTable('refresh', opt);
	}
}

/**
 * luzhen new  刷新学生信息
 */
otherpay.refStudentInfo = function(studentPkid, status){
	if(status){
		$("#btn_qtjf").attr("disabled", false);
		$("#btn_qtjf").click(function() {
			$('#mymodal').modal({
				keyboard : true
			});
		});
	}
	var pay_item_pkid = $("#otherpayitem").val();
	var datas = new Array();
	datas.push({"name":"PKID","value":studentPkid});
	
	$.ajax({
		type: 'POST',
		url :_basepath+"stuinfo/queryStudentByPkid.json?r="+Math.random(),
        data:datas,
        async: false,
        dataType:'JSON',//here
        success:function (data) {
//        	alert(data.pd.ZUZHINAME);
//        	alert(data.result);
        	if(data.result=="success"){
        		otherpay.stuinfo.XINGMING = data.pd.XINGMING;
        		otherpay.stuinfo.SHENFENZHENGHAO = data.pd.SHENFENZHENGHAO;
        		otherpay.stuinfo.XUEHAO = data.pd.XUEHAO;
        		otherpay.stuinfo.XIAOQU = data.pd.XIAOQU;
        		otherpay.stuinfo.ZUZHINAME = data.pd.ZUZHINAME;
        		otherpay.stuinfo.ZUZHI = data.pd.DEPARTMENT_PKID;
        		otherpay.stuinfo.ZXZT = data.pd.ZXZT;
        		otherpay.stuinfo.NIANJI = data.pd.NIANJI;
        		otherpay.psg = data.pd.PWD;
        		otherpay.stuinfo.DEPARTMENT_PKID = data.pd.DEPARTMENT_PKID;
//        		otherpay.stuinfo.NIANJI = data.pd.NIANJI;
//        		otherpay.stuinfo.METHODURL = data.pd.METHODURL;
        		
        		
    			$("#name").html(otherpay.stuinfo.XINGMING);
    			$("#sfz").html(otherpay.stuinfo.SHENFENZHENGHAO);
    			$("#xuehao").html(otherpay.stuinfo.XUEHAO);
    			$("#xiaoqu").html(otherpay.stuinfo.XIAOQU);
    			$("#zuzhi").html(otherpay.stuinfo.ZUZHINAME);
    			otherpay.stu_old_zuzhiname=otherpay.stuinfo.ZUZHI;
    			$("#zxzt").html(otherpay.stuinfo.ZXZT);
    			$("#nianji").html(otherpay.stuinfo.NIANJI);
    			otherpay.stu_pkid = data.pd.PKID;//学生ID
//    			otherpay.psg=otherpay.stuinfo.PASSMSG;
    			otherpay.old_department_id=otherpay.stuinfo.DEPARTMENT_PKID;
    			otherpay.old_nianji=otherpay.stuinfo.NIANJI;
//    			otherpay.requrl=otherpay.stuinfo.METHODURL;
    			var opt = {
    					url : _basepath + "pay/otherpaytable.json",
    					silent : true,
    					query : {
    						"PAY_ITEM_PKID" : pay_item_pkid,
    						"STUDENT_PKID" : status == true ? studentPkid : otherpay.stu_pkid
    					}
    			};
    			$("#optable").bootstrapTable('refresh', opt);
        	}else{
        		layer.msg(data.msg);
        	}
        },
        error:function(a,b,c){
        	layer.msg("获取学生信息失败!");
        }
    });
	
}
//end 

otherpay.search = function(){
	var pay_item_pkid = $("#otherpayitem").val();
	
	//查询学生信息
	var url=_basepath+"pay/getStudentQuerylist2.json";
	$.post(url,{conditions:$("#search").val()},function(result){
		if(result.result == 'success'){
			if(result.stuList.length == 0){
				layer.msg("未查询到该学生！");
			}else if(result.stuList.length == 1){
				otherpay.refStudentInfo(result.stuList[0].PKID,true);
				return false;
			}else{
				otherpay.refreshStudentTable();
			}
			
		}else{
			otherpay.refreshStudentTable();
		}
		
	});
    
};
otherpay.refreshStudentTable = function(){
	blg= BootstrapDialog.show({  //显示需要提交的表单。
	    message: $('<div></div>').load('pay/selectstu.json?a='+Math.random()),
	    title: '查询结果（双击选择您要查询的一个学生）',
	    closable: true, 
	    draggable: true
	    });
	    blg.setSize(BootstrapDialog.SIZE_WIDE);
};

/*otherpay.search = function() {

	// 获取输入的身份证号或学号
	var searchText = $("#search").val();
	// 获取下拉其他缴费项目列表
	var pay_item_pkid = $("#otherpayitem").val();
	var reg = /^\s+$/g;
	if (searchText.length == 0 || reg.test(searchText)) {
		layer.msg("请输入学号或身份证号！");
		$("#btn_qtjf").attr("disabled", true);
		return false;
	}
	var url = _basepath + "pay/getstuotherpay.json";
	$.ajax({
		type : 'POST',
		url : url,
		data : {
			SEARCHTEXT : searchText
		},
		async : false,
		dataType : 'JSON',
		success : function(mv) {
			if(mv.result=='failed'){
				layer.msg("输入信息不正确!")
			}else{
				otherpay.requrl=mv.methodurl;
				var stu_pd = mv.stupd;
				if (stu_pd == null) {
					$("#btn_qtjf").attr("disabled", true);
					return false;
				}
				if (stu_pd != null) {
					$("#name").html(stu_pd.XINGMING);
					$("#sfz").html(stu_pd.SHENFENZHENGHAO);
					$("#xuehao").html(stu_pd.XUEHAO);
					$("#xiaoqu").html(stu_pd.XIAOQU);
					$("#zuzhi").html(stu_pd.ZUZHI);
					otherpay.stu_old_zuzhiname=stu_pd.ZUZHI;
					$("#zxzt").html(stu_pd.ZXZT);
					$("#nianji").html(stu_pd.NIANJI);
					otherpay.stu_pkid = stu_pd.PKID;
					otherpay.psg=stu_pd.PSG;
					otherpay.old_department_id=stu_pd.DEPARTMENT_PKID;
					otherpay.old_nianji=stu_pd.NIANJI
					$("#btn_qtjf").attr("disabled", false);
					$("#btn_qtjf").click(function() {

						$('#mymodal').modal({
							keyboard : true
						});
					});
				}
			}
			

		},

	});

	var opt = {
		url : _basepath + "pay/otherpaytable.json",
		silent : true,
		query : {
			"PAY_ITEM_PKID" : pay_item_pkid,
			"STUDENT_PKID" : otherpay.stu_pkid
		}
	};

	$("#optable").bootstrapTable('refresh', opt);

};*/
/**
 * 可转出的项目
 */
otherpay.canoutxms='';

/**
 * 得到学生可转出项目
 */
otherpay.getStuCanOutPayitem = function() {
	// 我需要排除页面上已经选择了的转出项目的pkid
	var pkids=new Array();
	$('.zcxm').find("option:selected").each(function() {
		if($(this).val()!=''){
			pkids.push($(this).val());
		}
		
	});
	
	$.ajax({
		type: 'POST',
        url: _basepath + 'pay/getcanoutpayitem.json',
        data:{
    		STUDENT_PKID : otherpay.stu_pkid,
    		ZC_PKIDS : JSON.stringify(pkids)
    	},
        async:false,
        dataType:'JSON',
        success:function (mv) {
    		var canoutitems = mv.stuCanOutPayItemsList;
    		if (canoutitems != null) {
    			otherpay.canoutxms = '<option value="">请选择</option>';
    			for (var i = 0; i < canoutitems.length; i++) {
    				otherpay.canoutxms += "<option value=\'" + canoutitems[i].PKID
    						+ "\' xmje=\'" + canoutitems[i].AMOUNTCOLLECTION
    						+ "\'>" + canoutitems[i].PAYITEM + "</option>";
    			}
    		}
    	},
        
    });
};

//点击取消按钮事件
$("#btn_cancel").click(function(){
	otherpay.qingkong();
});

//初始化新增其他缴费页面
otherpay.qingkong = function(){
	//清空
	$("#quyu").empty();
	otherpay.grade='';
	otherpay.profess='';
	$("#select_pro").find("option:selected").attr("selected",false);
	$("#select_pro  option[value=''] ").attr("selected",true);
	//读取下拉框tzzy的属性值，Y的话表示可以调整专业，N的话表示不可以调整专业
	otherpay.tzzy=$("#select_pro").find("option:selected").attr("tzzy");
	otherpay.itempkid=$("#select_pro").find("option:selected").attr("value");
	otherpay.tzxm=$("#select_pro").find("option:selected").attr("tzxm");
	if(otherpay.tzzy=="Y"){
		$("#zuzhitree").show();
	}else{
		$("#zuzhitree").hide();
	}
	if(otherpay.tzxm=="Y"){
		otherpay.addagroup();
	}
	$("#citySel").val("");
	$("#grade").val("");
	$("#sfradio").prop("checked",true);
//	$.fn.zTree.destroy("treeDemo");
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	$("#xcsf").prop("checked",false);
	otherpay.TRANSACTION_TYPE='QT';
	//关闭计算总金额按钮
	$("#calTotalMoney").attr("disabled", true);
	
	$("#jiaoqian").hide();
	otherpay.isSitefees='N';
	otherpay.clearTotoalMoneyToZero();
}

//监听模态框消失事件
$('#mymodal').on('hidden.bs.modal', function () {
	otherpay.qingkong();
})
/**
 * 转入转出 模板
 */
otherpay.moban=function(a,b){
	return '<div class="list form-inline" id="x1">'+
	'<div style="margin-bottom: 15px;"> '+
	'<div class="form-group col-xs-4">'+
		'<label class="jf_OfLabel">转出项目</label>'+
		'<select class="form-control jf_ModInputW zcxm  payitem">  '+
		
		a+
		'</select> '+
	'</div>'+
	'<div class="form-group col-xs-4">'+
		'<label class="jf_OfLabel">实收费用</label>'+
		'<input class="form-control jf_ModInputW" name="zcfy" readonly="readonly"/>'+
	'</div>'+
	'<div class="pull-right">'+
	'<a href="javascript:void(0);" class="jf_add removeself"><span class="fa fa-times-circle jf_icon"></span> 删除</a>'+
   '</div>'+
	'<div class="clearfix"></div>'+
	'</div>'+
	'<div style="margin-bottom: 15px;"> '+
	'<div class="form-group col-xs-4">'+
	'	<label class="jf_OfLabel">转入项目</label>'+
		'<select class="form-control jf_ModInputW zrxm payitem">  '+
		b+
		'</select> '+
	'</div>'+
	'<div class="form-group col-xs-4">'+
	'	<label class="jf_OfLabel">发布费用</label>'+
		'<input class="form-control jf_ModInputW" name="zrxmfy" readonly="readonly"/>'+
	'</div>'+
	'<div class="form-group col-xs-4" style="padding-right:0;">'+
	'	<label class="jf_OfLabel" style="width:90px;">实际转入费用</label>'+
	'	<input class="form-control jf_ModInputW" readonly="readonly"  name="zrfy"/>'+
	'</div>'+
	'<div class="clearfix"></div>'+
	'</div>'+
	'<div style="margin-bottom: 15px;"> '+
	'<div class="form-group col-xs-4">'+
		'<label class="jf_OfLabel">优惠方式</label>'+
		'<input class="form-control jf_ModInputW" readonly="readonly" name="youhui"/>'+
	'</div>'+
	'<div class="form-group col-xs-4">'+
		'<label class="jf_OfLabel">应缴费用</label>'+
		'<input class="form-control jf_ModInputW" readonly="readonly" name="ysfy"/>'+
	'</div>'+
	'<div class="form-group col-xs-4" style="padding-right:0;">'+
	'<label class="jf_OfLabel" style="width:90px;">优惠金额</label>'+
	'<input class="form-control jf_ModInputW" readonly="readonly" name="youhuijine" style="margin-left:4px;"/>'+
    '</div>'+
	'<div class="clearfix"></div>'+
	'</div>'+
	'<div> '+
	'<div class="form-group col-xs-4">'+
		'<label class="jf_OfLabel">现金</label>'+
		'<input class="form-control jf_ModInputW " name="cash" placeholder="请输入金额" value="0"/>'+
	'</div>'+
	'<div class="form-group col-xs-4">'+
		'<label class="jf_OfLabel">银行卡</label>'+
		'<input class="form-control jf_ModInputW " name="card" placeholder="请输入金额" value="0"/>'+
	'</div>'+
	'<div class="form-group col-xs-4" style="padding-right:0;">'+
		'<label class="jf_OfLabel" style="width:94px;">微信</label>'+
		'<input class="form-control jf_ModInputW" name="wechat" placeholder="请输入金额" value="0"/>'+
	'</div>'+
	'<div class="form-group col-xs-4" style="margin-top: 15px;">'+
		'<label class="jf_OfLabel">支付宝</label>'+
		'<input class="form-control jf_ModInputW" name="alipay" placeholder="请输入金额" value="0"/>'+
	'</div>'+
	'<div class="clearfix"></div>'+
	'</div>'+
	'<div style="margin: 15px;border-top: 1px dashed red;"></div>'+
	'</div>';
};





/**
 * 实收费用
 */
otherpay.shishoufeiyong=null;

/**
 * 页面上所有的项目pkids
 */
otherpay.allpayitempkids='';


/**
 * 追加一组转入转出
 */
otherpay.addagroup=function(){
	//根据当前选择的项目 是否可以调整专业和调整项目 来动态追加行
	
	//调整专业并且同时调整项目
	if(otherpay.tzzy=="Y" && otherpay.tzxm=="Y"){
		
		otherpay.getStuCanOutPayitem();
		
		$("#quyu").append(otherpay.moban(otherpay.canoutxms,''));
		
		otherpay.event('Y');//y代表调整专业
	}
	
	//只调整专业
	if(otherpay.tzzy=="Y" && otherpay.tzxm=="N"){
		//只显示组织信息和年级 不显示 
		//1 更新学生信息
		
		//调王宁接口
	}
	//只调整项目
	if(otherpay.tzzy=="N" && otherpay.tzxm=="Y"){
		otherpay.getStuCanOutPayitem();
		$("#quyu").append(otherpay.moban(otherpay.canoutxms,otherpay.canoutxms));
		//给新增的转入转出项目下拉列表绑定事件
		otherpay.event('');//传空代表不调整专业
		
	}
	$('.removeself').bind('click',function(){
		$(this).parent().parent().parent().remove();
	});
};

/**
 * 删除一组转入转出
 */
otherpay.removeagroup=function(){
	//删除最后一组转入转出项目
	var $childs=$("#quyu").children();
	$childs[$childs.length-1].remove();
};

otherpay.TRANSACTION_TYPE='';
otherpay.OUT_PKIDS='';
otherpay.IN_PKIDS='';
otherpay.OUT_MONEY='';
otherpay.IN_MONEY='';
otherpay.YS_MONEY='';
/**
 * 转入项目费用拼接字符串(发布时的费用)
 */
otherpay.zrxmfy='';

/**
 * 优惠后但不包括转入的金额
 */
otherpay.AMOUNTRECEIVABLE='';

/**
 * 优惠项目拼接字符串
 */
otherpay.youhuistr='';

/**
 * 优惠金额
 */
otherpay.youhuijinestr='';

/**
 * 优惠方式
 */
otherpay.youhuifangshistr='';

/**
 * t_pay_item_rule pkid strs
 */
otherpay.rulepkidstr='';


/**
 * 检查实际转入费用，不能为负数
 */
otherpay.checkSjzrfy = function(){
	var rst = true;
	$('input[name=zrfy]').each(function(){
		var val = $(this).val();
		if(val!=null && val!=""){
			val = val*1;
			if(val<0){
				rst = false;
				return false;
			}
		}
	});
	return rst;
};



/**
 * 缴费，生成订单
 */
otherpay.save=function(){
	
	var isShow = $("#zuzhitree").css("display");
	var isTzzy = 'N';//默认不是调整专业
	if(isShow == null || isShow == "" || isShow=="block"){
		//说明是调整专业
		isTzzy = 'Y';
	}
	
	var grade=$("#grade").val();
	if(isTzzy == "Y"){//是否调整专业 是 调整专业才要判断
		//判断调整的新专业是否和原来一样
		var newzuzhiname=$("#citySel").val();
		//if(otherpay.stu_old_zuzhiname == newzuzhiname) {
		if($("#zuzhi").html() == newzuzhiname){
			layer.msg("调整专业不能选择原专业!");
			return false;
		}
		if(newzuzhiname == ""){
			layer.msg("院校专业不能为空!");
			return false;
		}
		if(grade == ""){
			layer.msg("年级不能为空!");
			return false;
		}
	}
	
	if(!otherpay.checkSjzrfy()){
		layer.msg("数据异常:实际转入金额不能为负数!");
		return false;
	}
	
	var url=otherpay.requrl+"/colleges-pay/dingDanPayController/dingDan.php";
	//备注
	var beizhu=$('#beizhu').val();
	//银行卡号
	var cardnum=$('#cardnum').val();
	var PROFESSION=$("#orgtree").val();
	
	/*
	 * luzhen
	 * 修复一个BUG,在调整专业，选择专业和班级后，切换到调整宿舍,传递的班级和专业就不对了，传的是当时选择的新的专业和班级
	 * 直接查询当前查询用户的用户的信息
	 */
	

	if(isTzzy == 'N'){//不是调整专业
		var datas = new Array();
		var student_pkid = otherpay.stu_pkid;//查询的学生pkid
		datas.push({"name":"PKID","value":student_pkid});
		
		$.ajax({
			type: 'POST',
			url :_basepath+"stuinfo/queryStudentByPkid.json?r="+Math.random(),
	        data:datas,
	        async: false,
	        dataType:'JSON',//here
	        success:function (data) {
	        	if(data.result=="success"){
//	        		grade = data.pd.NIANJI;
//	        		PROFESSION = data.pd.DEPARTMENT_PKID;
	        	}else{
	        		grade = "";
	        		PROFESSION = "";
	        		layer.msg(data.msg);
	        	}
	        },
	        error:function(a,b,c){
	        	grade = "";
	    		PROFESSION = "";
	        	layer.msg("保存失败,获取学生信息失败!");
	        }
	    });
	}
	//end luzhen
	
	//转出项目pkids
	otherpay.OUT_PKIDS='';
	
	var flag = true;
	$('.zcxm').find("option:selected").each(function(){
		if($(this).val()==''){//表示没选转出项目
			layer.msg("请选择转出项目");
			flag =  false;
		}
		otherpay.OUT_PKIDS+= $(this).val()+",";
	});
	if(!flag){
		return false;
	}
	//转入项目pkids
	otherpay.IN_PKIDS='';
	$('.zrxm').find("option:selected").each(function(){
		if($(this).val()==''){//表示没选转入项目
			layer.msg("请选择转入项目");
			flag =  false;
		}
		otherpay.IN_PKIDS+= $(this).val()+",";
	});
	if(!flag){
		return false;
	}
	//所有转出费用拼接字符串
	otherpay.OUT_MONEY='';
	$('input[name=zcfy]').each(function(){
		otherpay.OUT_MONEY+=$(this).val()+",";
	});
	//所有转入费用拼接字符串
	otherpay.IN_MONEY='';
	$('input[name=zrfy]').each(function(){
		otherpay.IN_MONEY+=$(this).val()+",";
	});
	//所有应收费用拼接字符串
	otherpay.YS_MONEY='';
	$('input[name=ysfy]').each(function(){
		otherpay.YS_MONEY+=$(this).val()+",";
	});
	// 转入项的发布费用拼接字符串
	otherpay.zrxmfy='';
	$('input[name=zrxmfy]').each(function(){
		otherpay.zrxmfy+=$(this).val()+",";
	});
	//优惠拼接字符串
	otherpay.youhuistr='';
	otherpay.youhuifangshistr='';
	otherpay.youhuifangshistr='';
	otherpay.rulepkidstr='';
	$('input[name=youhui]').each(function(){
		otherpay.youhuistr+=$(this).val()+",";//打8折...
		otherpay.youhuifangshistr+=$(this).attr('discount_mode')+',';//0 1 2
		otherpay.rulepkidstr+=$(this).attr('rulepkid')+',';//转出项目的pkid
	});
	//发布费用-优惠费用
	otherpay.AMOUNTRECEIVABLE='';
	otherpay.youhuijinestr='';
	$('input[name=ysfy]').each(function(){
		var ysfy=$(this).val()
		var zrfy=$(this).parent().parent().prev().children().eq(2).children().eq(1).val();
		var fy=$(this).parent().parent().prev().children().eq(1).children().eq(1).val();
		var yhje=$(this).parent().next().children().eq(1).val();
		var wn_ysfy = otherpay.numAdd(fy,yhje*(-1));
		otherpay.AMOUNTRECEIVABLE+=wn_ysfy+",";
//		otherpay.youhuijinestr+=(Number(fy)-Number(ysfy)-Number(zrfy))+",";
		otherpay.youhuijinestr+=$(this).parent().next().children().eq(1).val()+",";
	});
	
	//总额
	//给各种支付方式赋值
	otherpay.totalcash=$("#totalcash").val();
	otherpay.totalcard=$("#totalcard").val();
	otherpay.totalalipay=$("#totalalipay").val();
	otherpay.totalwechat=$("#totalwechat").val();	
	
	//收费还是退费
	/**
	 * 收费 退费 
	 */
	if($('#xcsf')[0].checked==false){
		otherpay.TRANSACTION_TYPE='QT';
	}else{
		if($("#sfradio")[0].checked==true){
			otherpay.TRANSACTION_TYPE='SF';
		}
		if($("#tfradio")[0].checked==true){
			otherpay.TRANSACTION_TYPE='TF';
		}
	}
	otherpay.calculateTotalMoneyAndStr();
	var data={
			PASSMSG:otherpay.psg,
			ORDERCREATE_MODE:'DO',//订单生成方式:U:线上（学生APP或PC）D:线下（线下缴费操作）  DO:线下其他收费（其他收费）I:导入
			STUDENT_PKID:otherpay.stu_pkid,//学生PKID
			ORDERCREATE_TERMINAL:'07',//订单生成终端  07:互联网  08:移动-iOS 09:移动-Andriod  10:移动-其他
			TRANSACTION_TYPE:otherpay.TRANSACTION_TYPE,//收费还是退费
			TOTALMONEY_CASH:Math.abs(otherpay.totalcash),//订单现金总金额
			TOTALMONEY_CARD:Math.abs(otherpay.totalcard),//订单银行卡总金额
			TOTALMONEY_WX:otherpay.totalwechat,//订单微信总金额
			TOTALMONEY_ZFB:otherpay.totalalipay,//订单支付宝总金额
			CJR:user_id,//创建人
			REMARK:beizhu,//备注
			CARDNO:cardnum,//银行卡号 （当银行卡金额不为0时必填）
			CASH:otherpay.totalcashstr,//现金各缴费项目缴费金额（当有转出转入项目时必传）
			CARD:otherpay.totalcardstr,//银行卡各缴费项目缴费金额（当有转出转入项目时必传）
			WX:otherpay.totalwechatstr,//微信支付各缴费项目金额（当有转出转入项目时必传）
			ZFB:otherpay.totalalipaystr,//支付宝支付各缴费项目金额（当有转出转入项目时必传）
			OTHER_ITEM:otherpay.itempkid,//如果ordercreate_mode的值为DO,t_pay_other_item表pkid
			GRADE:grade,//调整后年级: 如果调整专业选项为true，则该字段必传
			DEPARTMENT_PKID:PROFESSION,//调整后班级: 如果调整专业选项为true，则该字段必传
			OUT_PKIDS:otherpay.OUT_PKIDS,//转出项PKIDS: 如果调整项目选项为true，并且有转出项时，则该字段必传（逗号分隔）
			OUT_MONEY:otherpay.OUT_MONEY,//转出项金额（逗号分隔）
			IN_PKIDS:otherpay.IN_PKIDS,//转入项PKDIS:如果调整项目选项为true，并且有转入项时，则该字段必传（逗号分隔）
			IN_MONEY:otherpay.IN_MONEY,//转入项金额（逗号分隔），和转出金额相等
			XIANCHAGNYINGSHOU:otherpay.YS_MONEY,//现场应收金额，正数是收费，负数是退费（逗号分隔）
			XIANCHANG:otherpay.isSitefees,// 是否勾选现场收费选项,勾选为Y，没勾选则为N
			COST: otherpay.zrxmfy,// 优惠之前的应缴费用：发布费用，逗号隔开
			DISCOUNT:otherpay.youhuistr,// 优惠 如：直减2000,8折，或者不优惠，逗号隔开
			AMOUNTRECEIVABLE: otherpay.AMOUNTRECEIVABLE,// 应收金额，指优惠后但不包括转入项目的金额， 逗号分隔
			DISCOUNT_MONEY: otherpay.youhuijinestr,// 优惠金额，优惠金额
			DISCOUNT_MODE: otherpay.youhuifangshistr,// 优惠方式：0：不优惠，1：打折，2：直减，逗号隔开
			PAY_ITEM_RULE: otherpay.rulepkidstr//PAY_ITEM_RULE表PKID，逗号隔开
			
	};
	$.ajax({
		type: 'POST',
        url: url,
        data:data,
        dataType:'JSONP',//here
        jsonp:"callback",
        success:function (data) {
        	if(data.result=="true"){
        		layer.msg("保存成功!");
    			$("#btn_cancel").click();
    			var pay_item_pkid=$("#otherpayitem").val();
    			var opt = {
    					url :_basepath+"pay/otherpaytable.json",
    				    silent: true,
    				    query:{
    				    	"PAY_ITEM_PKID":pay_item_pkid,
    				    	"STUDENT_PKID":otherpay.stu_pkid
    				    }
    			 };
    			
    			 /*
    			  * luzhen 刷新学生信息
    			  */
    			if(isTzzy == 'N'){//不是调整专业
    				$("#optable").bootstrapTable('refresh', opt);
    			}else{
    				otherpay.refStudentInfo(otherpay.stu_pkid);
    			}
    			//end luzhen
        	}
        	if(data.result=="false"){
        		layer.msg(data.msg);
        	}
        	
        },
        
    });
};




/**
 * 给添加按钮绑定事件
 */
$("#addone").click(function(){
	otherpay.addagroup();
});

/**
 * 给删除按钮绑定事件
 */
$("#removeone").click(function(){
	otherpay.removeagroup();
});

/**
 * 选择的其他项目的pkid
 */
otherpay.itempkid='';
/**
 * 年级
 */

otherpay.grade='';
/**
 * 专业
 */
otherpay.profess='';

/**
 * 选择其他缴费项目触发事件
 */
$("#select_pro").bind("change", function() {
	//清空
	$("#quyu").empty();
	otherpay.grade='';
	otherpay.profess='';
	//读取下拉框tzzy的属性值，Y的话表示可以调整专业，N的话表示不可以调整专业
	otherpay.tzzy=$("#select_pro").find("option:selected").attr("tzzy");
	otherpay.itempkid=$("#select_pro").find("option:selected").attr("value");
	otherpay.tzxm=$("#select_pro").find("option:selected").attr("tzxm");
	if(otherpay.tzzy=="Y"){
		//是否调整专业   
		$("#zuzhitree").show();
	}else{
		$("#zuzhitree").hide();
	}
	if(otherpay.tzxm=="Y"){
		otherpay.addagroup();
	}
});

/**
 * 可转入的项目
 */
otherpay.caninxms='';
/**
 * 选择 新组织和年级后 要去查可以转入的专业
 * 
 */
$("#grade").bind("change", function() {
	var PROFESSION=$('#orgtree').val();
	var GRADE=$("#grade").find("option:selected").attr("value");
	if(PROFESSION==''){
		layer.msg("请选择所属组织!");
		return false;
	}
	otherpay.cleanZrxmInputContext();//切换年级 清空转入项目相关信息
});
/**
 * 院校组织改变 清空转入项目信息
 */
$("#citySel").bind('change',function(){
	otherpay.cleanZrxmInputContext();
});
/**
 * 清除 转入项目相关的输入框内容
 */
otherpay.cleanZrxmInputContext=function(){
	
	$('.zcxm').each(function(){
		$(this).html(otherpay.canoutxms);
	});
	$('input[name=zcfy]').each(function(){
		$(this).val("");
	});
	$('.zrxm').each(function(){
		$(this).html("");
	});
	$('input[name=zrxmfy]').each(function(){
		$(this).val("");
	});
	$('input[name=zrfy]').each(function(){
		$(this).val("");
	});
	$('input[name=youhui]').each(function(){
		$(this).val("");
	});
	$('input[name=ysfy]').each(function(){
		$(this).val("");
	});
	$('input[name=youhuijine]').each(function(){
		$(this).val("");
	});
	$('input[name=cash]').each(function(){
		$(this).val(0);
	});
	$('input[name=card]').each(function(){
		$(this).val(0);
	});
	$('input[name=wechat]').each(function(){
		$(this).val(0);
	});
	$('input[name=alipay]').each(function(){
		$(this).val(0);
	});
	otherpay.clearTotoalMoneyToZero();
};

/**
 * 现金总额
 */
otherpay.totalcash=0;

/**
 * 银行卡总额
 */
otherpay.totalcard=0;

/**
 * 微信总额
 */
otherpay.totalwechat=0;
/**
 * 支付宝总额
 */
otherpay.totalalipay=0;

/**
 * 现金总额字符串
 */
otherpay.totalcashstr='';

/**
 * 银行卡总额字符串
 */
otherpay.totalcardstr='';

/**
 * 微信总额字符串
 */
otherpay.totalwechatstr='';
/**
 * 支付宝总额字符串
 */
otherpay.totalalipaystr='';




otherpay.isSitefees='';

/**
 * 勾选 现场收费 后 
 */
$("#xcsf").change(function() { 
	if(this.checked==true){
		//开启计算总金额按钮
		$("#calTotalMoney").attr("disabled", false);
		
		otherpay.isSitefees='Y';
		if($(this)[0].checked==true){
			//otherpay.calculateTotalMoneyAndStr();
			
			$("#jiaoqian").show();
			//otherpay.TRANSACTION_TYPE='SF';
		}else{
			//退费
		$("#jiaoqian").hide();
		otherpay.totalwechat=0;
		otherpay.totalalipay=0;
		//otherpay.TRANSACTION_TYPE='TF';
		}
	}else{
		//关闭计算总金额按钮
		$("#calTotalMoney").attr("disabled", true);
		
		$("#jiaoqian").hide();
		otherpay.isSitefees='N';
		
		otherpay.clearTotoalMoneyToZero();
	}
	
});

/**
 * 给各项总金额置0
 */
otherpay.clearTotoalMoneyToZero=function(){
	//给各种支付方式赋0
	$("#totalcash").val(0);
	$("#totalcard").val(0);
	$("#totalalipay").val(0);
	$("#totalwechat").val(0);
	//给银行卡卡号置空
	$("#cardno").val("");
};

/**
 * 计算总金额
 */
$('#calTotalMoney').bind('click',function(){
	otherpay.calculateTotalMoneyAndStr();
});

/**
 * 收费 退费 单选框触发事件 
 */
$('input[type=radio][name=jiaof]').change(function() {
	if(this.id=="tfradio"){
		$("#wx").hide();
		$("#zfb").hide();
		//otherpay.TRANSACTION_TYPE='TF';
	}else{
		$("#wx").show();
		$("#zfb").show();
		//otherpay.TRANSACTION_TYPE='SF';
	}
});



/**
 * 搜索按钮 搜索功能
 */
$("#btn_search").click(function(){
	otherpay.search();
});

/**
 * 保存按钮 保存功能
 */
$("#btn_save").click(function(){
	otherpay.save();
});

/**
 * 计算各项总额以及字符串的方法
 */
otherpay.calculateTotalMoneyAndStr=function(){

	otherpay.totalcash=0;
	otherpay.totalcard=0;
	otherpay.totalwechat=0;
	otherpay.totalalipay=0;
	otherpay.totalcashstr='';
	otherpay.totalcardstr='';
	otherpay.totalwechatstr='';
	otherpay.totalalipaystr='';
	if(otherpay.tzxm=='Y' ){
		//现金总额
		$('input[name=cash]').each(function(){
			var wn = $(this).parent().parent().prev().children().eq(1).children().eq(1).val();
			var cash = $(this).val();
			if(wn<0){
				cash = cash*(-1);
			}
			otherpay.totalcash = otherpay.numAdd(otherpay.totalcash, cash);
			otherpay.totalcashstr+=$(this).val()+",";
		});
		//银行卡总额
		$('input[name=card]').each(function(){
			var wn = $(this).parent().parent().prev().children().eq(1).children().eq(1).val();
			var card = $(this).val();
			if(wn<0){
				card = card*(-1);
			}
			otherpay.totalcard = otherpay.numAdd(otherpay.totalcard, card);
			otherpay.totalcardstr+=$(this).val()+",";
		});
		//微信总额
		$('input[name=wechat]').each(function(){
			otherpay.totalwechat = otherpay.numAdd(otherpay.totalwechat, $(this).val());
			otherpay.totalwechatstr+=$(this).val()+",";
		});
		//支付宝总额
		$('input[name=alipay]').each(function(){
			otherpay.totalalipay = otherpay.numAdd(otherpay.totalalipay, $(this).val());
			otherpay.totalalipaystr+=$(this).val()+",";
		});
		//给各种支付方式赋值
		$("#totalcash").val(Math.abs(otherpay.totalcash));
		$("#totalcard").val(Math.abs(otherpay.totalcard));
		$("#totalalipay").val(Math.abs(otherpay.totalalipay));
		$("#totalwechat").val(Math.abs(otherpay.totalwechat));	
	}else{
		otherpay.totalcash=$("#totalcash").val();
		otherpay.totalcard=$("#totalcard").val();
		otherpay.totalalipay=$("#totalalipay").val();
		otherpay.totalwechat=$("#totalwechat").val();	
	}
	
};


otherpay.getTable=function(){

	// 引例四
	var url = _basepath+"pay/otherpaytable.json";

	$('#optable').bootstrapTable(
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
							return '请输入简拼.编码.企业名称查询';
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
						onDblClickRow:function(row){
						},
						queryParams : function getParams(params) {
							//    	                   var searchKey1 = $("#JIAOYISHIJIAN").val();  //搜索框数据
							//    	                    var searchKey2 = $("#s2").val();  
							var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
								limit : params.limit, //页面大小
								//    	                                 searchKey1:searchKey1,
								//    	                                 searchKey2:searchKey2,
								searchText : this.searchText,
								sortName : this.sortName,
								sortOrder : this.sortOrder,
								pageindex : this.pageNumber,
								STUDENT_PKID:otherpay.stu_pkid
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
									field : 'OTHERPAYITEM',
									title : '其他缴费项名称',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'ZHUANGTAI',
									title:'状态',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'MONEY',
									title:'实缴金额',
									align : "center",
									halign : 'center',
									sortable : false
								},
								
								{
									field : 'CASH',
									title : '现金',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field: 'CARD',//可不加  
									title : '银行卡',//标题  可不加
									align : "center",
									halign : 'center'
								},
								
								{
									field : 'ZFB',
									align : "center",
									halign : 'center',
									title : ' 支付宝'

								},
								{
									field : 'WX',
									align : "center",
									halign : 'center',
									title : '微信'

								},
								
								{
									field : 'CJSJ',
									align : "center",
									halign : 'center',
									title : '操作时间'

								}
								
						],
					});
};

otherpay.event=function(e){
	//给新增的转出项目下拉列表绑定事件
	$(".zcxm").bind("change", function() {
		var $zcselect=$(this);
		
		/*
		 * luzhen  修改一个bug，选择转出项目后，先清空转入项目的option,否则，上一次选择的转入项目，会传入到后台，显示不出来
		 */
		$zcselect.parent().parent().parent().find(".zrxm").html("");
		//end luzhen
		
		//给实收费用框赋值
		otherpay.shishoufeiyong=$(this).find("option:selected").attr("xmje");
		//转出项目实收费用
		$zcselect.parent().next().children().eq(1).val(otherpay.shishoufeiyong);
		//转入项目可转入费用
		//$zcselect.parent().parent().next().children().eq(2).children().eq(1).val(otherpay.shishoufeiyong);
		//通过选择的转出项目 去查 可转入的项目， 转入的项目中需排除页面上的所有转出项目 包括没选中的，排除转入select框中已选中的项目
		//获取页面上所有的项目ids
		var pkids=new Array();
		$('.zcxm').find("option:selected").each(function(){
			//只要不为空的，为空代表当前select框没有选择项目
			if ($(this).val() != '') {
				pkids.push( $(this).val());
			}
		});
		$('.zrxm').find("option:selected").each(function(){
			//只要不为空的，为空代表当前select框没有选择项目
			if ($(this).val() != '') {
				pkids.push( $(this).val());
			}
		});
		//专业
		otherpay.profess=$("#orgtree").val();
		//年级
		otherpay.grade=$("#grade").find("option:selected").attr("value");
		otherpay.caninxms="<option value=''>请选择</option>";
		$.ajax({
			type: 'POST',
	        url: _basepath + 'pay/getstucaninpayitem.json',
	        data:{
	    		STUDENT_PKID : otherpay.stu_pkid,
	    		PKIDS : JSON.stringify(pkids),
	    		PROFESSION:otherpay.profess,
	    		GRADE:otherpay.grade,
	    		changeprofess:e,
	    		department_pkid:otherpay.old_department_id,
	    		nianji:otherpay.old_nianji
	    	},
	        async:false,
	        dataType:'JSON',
	        success:function (mv) {
	    		var inpayitems=mv.stuCanInPayItemsList;
	    		if(inpayitems!=null) {
	    			for (var i = 0; i < inpayitems.length; i++) {
	    				otherpay.caninxms+="<option value=\'"+inpayitems[i].PKID+"\'>"+inpayitems[i].PAYITEM+"</option>";
	    			}
	    		}
	    	},
	        
	    });
    	//把取回来的转入项目填到select中
    	$zcselect.parent().parent().next().children().eq(0).children().eq(1).html(otherpay.caninxms);
    	//清空转入项目已有的数据
    	$zcselect.parent().parent().next().children().eq(1).children().eq(1).val('');
    	$zcselect.parent().parent().next().children().eq(2).children().eq(1).val('');
    	$zcselect.parent().parent().next().next().children().eq(0).children().eq(1).val('');
    	$zcselect.parent().parent().next().next().children().eq(1).children().eq(1).val('');
	});
	//给新增的转入项目下拉列表绑定事件
	$(".zrxm").bind("change", function() {
		//转入项目费用
		//var xmje=$(this).find("option:selected").attr("xmje");
		//$(this).parent().next().children().eq(1).val(xmje);
		var $select=$(this);
		var STUDENT_PKID=otherpay.stu_pkid;
//		var GRADE=$("#grade").find("option:selected").attr("value");
		var GRADE=otherpay.grade;
//		var PROFESSION=$("#orgtree").val();
		var PROFESSION=otherpay.profess
		var PAY_ITEM_PKID=$(this).find("option:selected").attr("value");
		
		var isShow = $("#zuzhitree").css("display");
		var isTzzy = 'N';//默认不是调整专业
		if(isShow == null || isShow == "" || isShow=="block"){
			//说明是调整专业
			isTzzy = 'Y';
		}
		
		$.post(_basepath+"pay/getAllKindMoney.json",{STUDENT_PKID:STUDENT_PKID,GRADE:GRADE,PROFESSION:PROFESSION,PAY_ITEM_PKID:PAY_ITEM_PKID,ISTZZY:isTzzy},function(mv){
			if(mv.zrpd!=null){
				var zrpd=mv.zrpd;
				var yhfs=zrpd.YHFS;
				var yhje=zrpd.DISCOUNT_MONEY;
				$select.parent().next().children().eq(1).val(zrpd.COST);
//				$select.parent().parent().next().children().eq(0).children().eq(0).html(yhfs);
				$select.parent().parent().next().children().eq(0).children().eq(1).val(yhfs);
				$select.parent().parent().next().children().eq(0).children().eq(1).attr("rulepkid",zrpd.PAY_ITEM_RULE_PKID);
				$select.parent().parent().next().children().eq(0).children().eq(1).attr("discount_mode",zrpd.DISCOUNT_MODE);
				
				//实际能转入的金额 要拿剩余金额和转出项目的金额比较
				var zcsffy=$select.parent().parent().prev().children().eq(1).children().eq(1).val();
				var shijiyingjiaofeiyong=0;
				if(Number(zcsffy)>Number(zrpd.SYYJJE)){
					shijiyingjiaofeiyong=zrpd.SYYJJE;
//					shijiyingjiaofeiyong=(Number(zrpd.SYYJJE)-Number(zcsffy))
				}else{
					shijiyingjiaofeiyong=zcsffy;
				}
				//实际能转入的费用
				$select.parent().next().next().children().eq(1).val(shijiyingjiaofeiyong);
				//实际应缴纳的费用otherpay.numAdd
				$select.parent().parent().next().children().eq(1).children().eq(1).val(otherpay.numAdd(zrpd.SYYJJE,zcsffy*(-1)));
				
				//优惠金额
				$select.parent().parent().next().children().eq(2).children().eq(1).val(yhje);
				
				var obj_wx = $select.parent().parent().next().next().children().eq(2).children().eq(1);
				var obj_zff = $select.parent().parent().next().next().children().eq(3).children().eq(1);
				if(Number(zrpd.SYYJJE)-Number(zcsffy)<0){
					obj_wx.attr("disabled",true);
					obj_wx.val("0");
					obj_zff.attr("disabled",true);
					obj_zff.val("0");
				}else{
					obj_wx.attr("disabled",false);
					obj_zff.attr("disabled",false);
				}
			}
			
		});
	});
};


$(function(){
	otherpay.getTable();
});
