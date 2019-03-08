
//@ sourceURL=paymanagerxgjf.js
//发布缴费项页面js
var op={};

var index = 0;
var fbArray = new Array();
var flag_ggje="false";
index=fbArray.length;
op.guizequeren=function(){
	 oldChildNameList = new Array();
	 var stcel=$("#citySel").val();
	 if(stcel==null || ""==stcel){
			layer.msg("专业不能为空！");
			return false;
		}
	 
	 
	 
	index++;
	//树选中节点 编号
	var orgtreeid = $("#orgtree").val();

	//年级编号
	obj = document.getElementsByName("nianji");
    nianjilist = [];
    for(k in obj){
        if(obj[k].checked)
        	nianjilist.push(obj[k].value);
    }
    if(nianjilist.length==0){
		layer.msg("请选择年级！");
		return false;
	}
    
    
    nianjilist= nianjilist.toString();
    
  //层次
	obj = document.getElementsByName("cengci");
    cengcilist = [];
    for(k in obj){
        if(obj[k].checked)
        	cengcilist.push(obj[k].value);
    }
    if(cengcilist.length==0){
		layer.msg("请选择学生类型！");
		return false;
	}
    cengcilist= cengcilist.toString();
    
    //批次
	obj = document.getElementsByName("pici");
	picilist = [];
    for(k in obj){
        if(obj[k].checked)
        	picilist.push(obj[k].value);
    }
    if(picilist.length==0){
		layer.msg("请选择批次！");
		return false;
	}
    picilist= picilist.toString();
    var flag = true;
    $(".itemCostArea").find(".itemCost").each(function(v){
    	if($(this).val()=='' || $(this).val()==null){
    		layer.msg("费用金额不能为空");
    		$(this).focus();
    		flag = false;
    		return flag;
    	}
    });
    if(!flag){
    	return false;
    }
    var itemMap = {};
    $(".itemCostArea").find(".itemCost").each(function(v){
    	var itemName = $(this).attr("text").trim();
    	var itemcost = $(this).val().trim();
    	itemMap[itemName]=itemcost;
    	$("#child").find(".child_item").each(function(v){
    		if($(this).val()==itemName){
    			$(this).attr("oldname",itemName);
    			oldChildNameList.push(itemName);
    		}
    	});
    	//itemListRule.push(itemMap);
    });
  //优惠范围 编号
    obj = document.getElementsByName("youhuifw");
    youhuifw = [];
    for(k in obj){
        if(obj[k].checked)
        	youhuifw.push(obj[k].value);
    }
    youhuifw=youhuifw.toString();
    //优惠方式 编号
    var selectedvalue = $("input[type=radio][name=yhfs]:checked").val();
  //树选中节点 内容
	var orgtreeidtext = $("#citySel").val();
	//年级 内容
	objnj = document.getElementsByName("nianji");
    nianjilisttext = [];
    for(k in objnj){
        if(objnj[k].checked)
        	nianjilisttext.push(objnj[k].getAttribute('val'));
    }
    
  //层次 内容
	objcc = document.getElementsByName("cengci");
	cengcilisttext = [];
    for(k in objcc){
        if(objcc[k].checked)
        	cengcilisttext.push(objcc[k].getAttribute('val'));
    }
    
    
  //批次 内容
	objpc = document.getElementsByName("pici");
	picilisttext = [];
    for(k in objpc){
        if(objpc[k].checked)
        	picilisttext.push(objpc[k].getAttribute('val'));
    }
    
  //优惠范围  内容
    obj = document.getElementsByName("youhuifw");
    youhuifwtext = [];
    for(k in obj){
        if(obj[k].checked)
        	youhuifwtext.push(obj[k].getAttribute('val'));
    }
    if(selectedvalue != 0){//无优惠
    	if(youhuifwtext.length==0){
    		layer.msg("请选择优惠范围！");
    		return false;
    	}
    }
    
    if(selectedvalue == 1){//打折
    	var dazheshu = $("#dazheshu").val();
    	if(dazheshu=="" || dazheshu==null || dazheshu==undefined){
    		layer.msg("请填写打折范围！");
    		$("#dazheshu").val("");
    		return false;
    	}
    	var regu =  /^[1-9]+[1-9]*]*$/;
    	if( !regu.test(dazheshu)){
    		layer.msg("打折范围为1-9之间正整数！");
    		$("#dazheshu").val("");
    		return false;
    	}
    }
    
    var je = $("#fabuje").val();
	var zhijianshu = $("#zhijianshu").val();
    if(selectedvalue == 2){//直减
    	
    	
    	if(zhijianshu=="" || zhijianshu==null || zhijianshu==undefined){
    		layer.msg("请填写直减金额！");
    		$("#zhijianshu").val("");
    		return false;
    	}
    	
    	 var fix_amountTest=/^(([1-9]\d*)|\d)(\.\d{1,2})?$/;
    	if(!fix_amountTest.test(zhijianshu)){
    		layer.msg("请输入有效金额！");
    		$("#zhijianshu").val("");
    		return false;
    	}
    	
    	 if(eval(zhijianshu) > eval(je)){
    		 layer.msg("直减金额不能大于总金额，请重新输入！");
     		$("#zhijianshu").val("");
     		return false;
    	 }
    	
    }
    
	 /*var flagnj=false;
	 var flagtree=false;*/
    for(var i=0;i<fbArray.length;i++){
    	var flagnj=false;
   	 	var flagtree=false;
    	var nianjilarr;
    	var ztreelarr;
    	var nianjilarra;
    	var ztreelarra;
   	 	var nianjilista;
   	 	var orgtreeida;
    	nianjilarr=fbArray[i].nianjilist+",";
    	/*if(nianjilarr.length>0){
    		
    	}*/
    	nianjilarra= nianjilarr.split(',');
    	
    	ztreelarr=fbArray[i].orgtreeid+",";
    	ztreelarra= ztreelarr.split(',');
    	nianjilista=nianjilist.split(',');
    	for(k in nianjilarra){
    		for(n in nianjilista){
    			if(nianjilarra[k] ==nianjilista[n] &&nianjilarra[k]!="" &&nianjilista[n]!=""){
    				flagnj=true;
    			}
    		}
    	}
    	orgtreeid=orgtreeid+",";
    	orgtreeida=orgtreeid.split(',');
    	for(z in ztreelarra){
    		
    		for(o in orgtreeida){
    			if(ztreelarra[z] ==orgtreeida[o] && ztreelarra[z]!="" && orgtreeida[o]!=""){
    				flagtree=true;
    			}
    		}
    	}
    	var flagcengci=false;
	 	var flagpici=false;
	 	var cengcilarr;
	 	var cengcilarrn;
	 	var picilarr;
	 	var picilarrn;
    	cengcilarr=fbArray[i].cengcilist+",";
    	cengcilarr= cengcilarr.split(',');
    	cengcilarrn=cengcilist.split(',');
    	for(z in cengcilarr){
    		for(o in cengcilarrn){
    			if(cengcilarr[z] ==cengcilarrn[o] && cengcilarr[z]!="" && cengcilarrn[o]!=""){
    				flagcengci=true;
    			}
    		}
    	}
    	picilarr=fbArray[i].picilist+",";
    	picilarr= picilarr.split(',');
    	picilarrn=picilist.split(',');
    	for(z in picilarr){
    		for(o in picilarrn){
    			if(picilarr[z] ==picilarrn[o] && picilarr[z]!="" && picilarrn[o]!=""){
    				flagpici=true;
    			}
    		}
    	}
    	if(flagnj && flagtree && flagcengci && flagpici){
        	layer.msg("专业、年级、学生类型和批次的组合必须唯一！");
        	return false;
        	break;
        }
    	
        }
    
    
    
    
    
    
    //优惠方式 内容
    var selectedvaluetext = $("input[type=radio][name=yhfs]:checked").attr('val');
    var dazheshu = $("#dazheshu").val();
    var zhijianshu = $("#zhijianshu").val();
    
    if(selectedvalue =="1"){
    	selectedvaluetext=selectedvaluetext+"-"+dazheshu+"折";
    	zhijianshu=0;
    }else if(selectedvalue =="2"){
    	selectedvaluetext=selectedvaluetext+"-"+zhijianshu+"元";
    }
  
    
   //根据选择条件查询统计人数
    var url = _basepath+'pay/getStuNum.json';
    var tr_stuNum = 0;
	$.ajax({
		type:'post',
		dataType:'json',
		url:url,
		data:{orgtreeid:orgtreeid,nianjilist:nianjilist,cengcilist:cengcilist,picilist:picilist},
		success:function(data){
			tr_stuNum = data.tr_stuNum;
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) { 
	          alert(errorThrown); 
		}
	});
    var zizhi = {};
   		zizhi.index = index;
    	zizhi.orgtreeid = orgtreeid;
		zizhi.nianjilist = nianjilist;
		zizhi.youhuifw = youhuifw;
		zizhi.selectedvalue = selectedvalue;
		zizhi.zhijianshu = zhijianshu;
		zizhi.dazheshu = dazheshu;
		zizhi.orgtreeidtext = orgtreeidtext;
		//zizhi.PKID_ZIZHI = $("#ZIZHI_PKID").val();
		zizhi.cengcilist = cengcilist;
		zizhi.picilist = picilist;
		zizhi.itemMap = itemMap;
		fbArray.push(zizhi);
		var costTd = "";//记录费用内容
		$(".itemCostArea").find(".itemCost").each(function(v){
	    	var itemcost = $(this).val();
	    	costTd = costTd+'<th>'+itemcost+'</th>';
	    });
		$("<tr trNumber='"+index+"' id = 'zizhi"+index+"'><td >"+nianjilisttext+"</td><td>"+orgtreeidtext+"</td><td>"+cengcilisttext+"</td><td>"+picilisttext+"</td><td style='cursor:pointer;color: red;' index='"+index+"'  title='点击下载学生名单' class='downStu'>"+tr_stuNum+"</td>" +
				costTd +
				"<td>"+youhuifwtext+"</td><td>"+selectedvaluetext+"</td><td class='jf_Operation'><span style='cursor:pointer;' class='fa fa-pencil updatefabu' id='update"+index+"'></span>&nbsp;&nbsp;<span style='cursor:pointer;' class='fa fa-trash-o shanchugz' id='delete"+index+"'></span></td></tr>").appendTo("#fabutable"); 
		//$(this).parent().parent().remove();
		$("guizequeren").removeAttr("data-dismiss");
		//$('#mymodaljf').modal('hide')
		$('#mymodaljf').modal('hide');
};



op.xiugaigz=function(){	
	oldChildNameList = new Array();
	var itemMap = {};
	var shu='w';
	var stcel=$("#citySel").val();
	 if(stcel==null || ""==stcel){
			layer.msg("专业不能为空！");
			return false;
		}
	/*//得到修改行号
	 var number=$("#trnumber").val();
	 var trid="zizhi"+number;
	 $("tr[id="+trid+"]").remove();
	 for(var i=0;i<fbArray.length;i++){
			var inde=fbArray[i].index;
			if(inde == number){
				fbArray.splice(i, 1)
			}
	        }
	 */
	 
	 var orgtreeidtext = $("#citySel").val();
	//树选中节点 编号
		var orgtreeid = $("#orgtree").val();
		//年级编号
		obj = document.getElementsByName("nianji");
	    nianjilist = [];
	    for(k in obj){
	        if(obj[k].checked)
	        	nianjilist.push(obj[k].value);
	    }
	    if(nianjilist.length==0){
			layer.msg("请选择年级！");
			return false;
		}
	    nianjilist= nianjilist.toString();
	    
	    //层次
		obj = document.getElementsByName("cengci");
	    cengcilist = [];
	    for(k in obj){
	        if(obj[k].checked)
	        	cengcilist.push(obj[k].value);
	    }
	    if(cengcilist.length==0){
			layer.msg("请选择学生类型！");
			return false;
		}
	    cengcilist= cengcilist.toString();
	    
	    //批次
		obj = document.getElementsByName("pici");
		picilist = [];
	    for(k in obj){
	        if(obj[k].checked)
	        	picilist.push(obj[k].value);
	    }
	    if(picilist.length==0){
			layer.msg("请选择批次！");
			return false;
		}
	    picilist= picilist.toString();
	    var flag = true;
	    $(".itemCostArea").find(".itemCost").each(function(v){
	    	if($(this).val()=='' || $(this).val()==null){
	    		layer.msg("费用金额不能为空");
	    		$(this).focus();
	    		flag = false;
	    		return flag;
	    	}
	    });
	    if(!flag){
	    	return false;
	    }
	    $(".itemCostArea").find(".itemCost").each(function(v){
	    	var itemName = $(this).attr("text").trim();
	    	var itemcost = $(this).val().trim();
	    	itemMap[itemName]=itemcost;
	    	$("#child").find(".child_item").each(function(v){
	    		if($(this).val()==itemName){
	    			$(this).attr("oldname",itemName);
	    			oldChildNameList.push(itemName);
	    		}
	    	});
	    	//itemListRule.push(itemMap);
	    });
	  //优惠范围 编号
	    obj = document.getElementsByName("youhuifw");
	    youhuifw = [];
	    for(k in obj){
	        if(obj[k].checked)
	        	youhuifw.push(obj[k].value);
	    }
	    youhuifw= youhuifw.toString();
	    
	    youhuifwtext = [];
	    for(l in obj){
	        if(obj[l].checked)
	        	youhuifwtext.push(obj[l].getAttribute('val'));
	    }
	    //优惠方式 编号
	    var selectedvalue = $("input[type=radio][name=yhfs]:checked").val();
	    
	    if(selectedvalue != 0){//无优惠
	    	if(youhuifwtext.length==0){
	    		layer.msg("请选择优惠范围！");
	    		return false;
	    	}
	    }
	    var dazheshu = $("#dazheshu").val();
	    
	    var je = $("#fabuje").val();
		var zhijianshu = $("#zhijianshu").val();
	    if(selectedvalue == 1){//打折
	    	//var dazheshu = $("#dazheshu").val();
	    	if(dazheshu=="" || dazheshu==null || dazheshu==undefined){
	    		layer.msg("请填写打折范围！");
	    		$("#dazheshu").val("");
	    		return false;
	    	}
	    	var regu =  /^[1-9]+[1-9]*]*$/;
	    	if( !regu.test(dazheshu)){
	    		layer.msg("打折范围为1-9之间正整数！");
	    		$("#dazheshu").val("");
	    		return false;
	    	}
	    	zhijianshu=0;
	    }
	    
	    
	    var je = $("#fabuje").val();
		var zhijianshu = $("#zhijianshu").val();   
 if(selectedvalue == 2){//直减
	    	
	    	
	    	if(zhijianshu=="" || zhijianshu==null || zhijianshu==undefined){
	    		layer.msg("请填写直减金额！");
	    		$("#zhijianshu").val("");
	    		return false;
	    	}
	    	
	    	 var fix_amountTest=/^(([1-9]\d*)|\d)(\.\d{1,2})?$/;
	    	if(!fix_amountTest.test(zhijianshu)){
	    		layer.msg("请输入有效金额！");
	    		$("#zhijianshu").val("");
	    		return false;
	    	}
	    	
	    	 if(eval(zhijianshu) > eval(je)){
	    		 layer.msg("直减金额不能大于总金额，请重新输入！");
	     		$("#zhijianshu").val("");
	     		return false;
	    	 }
	    	
	    }
	    
	    
 
 
 
 for(var i=0;i<fbArray.length;i++){
 	var flagnj=false;
	 	var flagtree=false;
 	var nianjilarr;
 	var ztreelarr;
 	var nianjilarra;
 	var ztreelarra;
	 	var nianjilista;
	 	var orgtreeida;
	 	
		//跳过自身
	   	 var number=$("#trnumber").val();
		 var trid="zizhi"+number;
		// $("tr[id="+trid+"]").remove();
		// for(var i=0;i<fbArray.length;i++){
				var inde=fbArray[i].index.toString();
				if(inde == number){
					shu=i;
					continue;
				}
		      //  }
	 	
	 	
	 	
	 	
 	nianjilarr=fbArray[i].nianjilist+",";
 	/*if(nianjilarr.length>0){
 		
 	}*/
 	nianjilarra= nianjilarr.split(',');
 	
 	ztreelarr=fbArray[i].orgtreeid+",";
 	ztreelarra= ztreelarr.split(',');
 	nianjilista=nianjilist.split(',');
 	for(k in nianjilarra){
 		for(n in nianjilista){
 			if(nianjilarra[k] ==nianjilista[n] &&nianjilarra[k]!="" &&nianjilista[n]!=""){
 				flagnj=true;
 			}
 		}
 	}
 	if(orgtreeid==""){
 		orgtreeid=fbArray[i].orgtreeid;
 	}
 	orgtreeid=orgtreeid+",";
 	orgtreeida=orgtreeid.split(',');
 	for(z in ztreelarra){
 		
 		for(o in orgtreeida){
 			if(ztreelarra[z] ==orgtreeida[o] && ztreelarra[z]!="" && orgtreeida[o]!=""){
 				flagtree=true;
 			}
 		}
 	}
 	var flagcengci=false;
 	var flagpici=false;
 	var cengcilarr;
 	var cengcilarrn;
 	var picilarr;
 	var picilarrn;
	cengcilarr=fbArray[i].cengcilist+",";
	cengcilarr= cengcilarr.split(',');
	cengcilarrn=cengcilist.split(',');
	for(z in cengcilarr){
		for(o in cengcilarrn){
			if(cengcilarr[z] ==cengcilarrn[o] && cengcilarr[z]!="" && cengcilarrn[o]!=""){
				flagcengci=true;
			}
		}
	}
	picilarr=fbArray[i].picilist+",";
	picilarr= picilarr.split(',');
	picilarrn=picilist.split(',');
	for(z in picilarr){
		for(o in picilarrn){
			if(picilarr[z] ==picilarrn[o] && picilarr[z]!="" && picilarrn[o]!=""){
				flagpici=true;
			}
		}
	}
	if(flagnj && flagtree && flagcengci && flagpici){
    	layer.msg("专业、年级、学生类型和批次的组合必须唯一！");
     	return false;
     	break;
     }
     }
 
	   // var zhijianshu = $("#zhijianshu").val();
 
 if(shu!='w'){
		fbArray.splice(shu, 1);
		var number=$("#trnumber").val();
		 var trid="zizhi"+number;
		 $("tr[id="+trid+"]").remove();
	}
 		index++;
	    var zizhi = {};
		zizhi.index = index;
		zizhi.orgtreeid = orgtreeid;
		zizhi.nianjilist = nianjilist;
		zizhi.youhuifw = youhuifw;
		zizhi.selectedvalue = selectedvalue;
		zizhi.zhijianshu = zhijianshu;
		zizhi.dazheshu = dazheshu;
		zizhi.orgtreeidtext = orgtreeidtext;
		zizhi.cengcilist = cengcilist;
		zizhi.picilist = picilist;
		zizhi.itemMap = itemMap;
		//zizhi.PKID_ZIZHI = $("#ZIZHI_PKID").val();
		fbArray.push(zizhi);
		//根据选择条件查询统计人数
	    var url = _basepath+'pay/getStuNum.json';
	    var tr_stuNum = 0;
		$.ajax({
			type:'post',
			dataType:'json',
			url:url,
			data:{orgtreeid:orgtreeid,nianjilist:nianjilist,cengcilist:cengcilist,picilist:picilist},
			success:function(data){
				tr_stuNum = data.tr_stuNum;
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
		          alert(errorThrown); 
			}
		});
	    
	//树选中节点 内容
		var orgtreeidtext = $("#citySel").val();
		//年级 内容
		objnj = document.getElementsByName("nianji");
	    nianjilisttext = [];
	    for(k in objnj){
	        if(objnj[k].checked)
	        	nianjilisttext.push(objnj[k].getAttribute('val'));
	    }
	    
	  //层次 内容
		objcc = document.getElementsByName("cengci");
		cengcilisttext = [];
	    for(k in objcc){
	        if(objcc[k].checked)
	        	cengcilisttext.push(objcc[k].getAttribute('val'));
	    }
	    
	    
	  //批次 内容
		objpc = document.getElementsByName("pici");
		picilisttext = [];
	    for(k in objpc){
	        if(objpc[k].checked)
	        	picilisttext.push(objpc[k].getAttribute('val'));
	    }
	    
	    
	  //优惠范围  内容
	    obj = document.getElementsByName("youhuifw");
	    youhuifwtext = [];
	    for(k in obj){
	        if(obj[k].checked)
	        	youhuifwtext.push(obj[k].getAttribute('val'));
	    }
	    //优惠方式 内容
	    var selectedvaluetext = $("input[type=radio][name=yhfs]:checked").attr('val');
	    var dazheshu = $("#dazheshu").val();
	    var zhijianshu = $("#zhijianshu").val();
	    
	    if(selectedvalue =="1"){
	    	selectedvaluetext=selectedvaluetext+"-"+dazheshu+"折";
	    }else if(selectedvalue =="2"){
	    	selectedvaluetext=selectedvaluetext+"-"+zhijianshu+"元";
	    }
	 
	    var costTr = "";//记录费用表头
		var costTd = "";//记录费用内容
		$(".itemCostArea").find(".itemCost").each(function(v){
			var itemName = $(this).attr("text");
	    	var itemcost = $(this).val();
	    	costTr = costTr+'<th>'+itemName+'</th>';
	    	costTd = costTd+'<th>'+itemcost+'</th>';
	    });
		$("#ruleTr").html('<th>年级</th><th>专业</th><th>学生类型</th><th>批次</th><th>人数</th>'+costTr+
				'<th>优惠学生类型</th>'+
				'<th>优惠方式</th>'+
				'<th>操作</th>');
		
		$("<tr trNumber='"+index+"' id = 'zizhi"+index+"'><td >"+nianjilisttext+"</td><td>"+orgtreeidtext+"</td><td>"+cengcilisttext+"</td><td>"+picilisttext+"</td><td style='cursor:pointer;color: red;' index='"+index+"'  title='点击下载学生名单' class='downStu'>"+tr_stuNum+"</td>" +
				costTd +
				"<td>"+youhuifwtext+"</td><td>"+selectedvaluetext+"</td><td class='jf_Operation'><span style='cursor:pointer;' class='fa fa-pencil updatefabu' id='update"+index+"'></span>&nbsp;&nbsp;<span style='cursor:pointer;' class='fa fa-trash-o shanchugz' id='delete"+index+"'></span></td></tr>").appendTo("#fabutable"); 
	 //$("<tr trNumber='"+index+"' id = 'zizhi"+index+"'><td >"+nianjilisttext+"</td><td>"+orgtreeidtext+"</td><td>"+cengcilisttext+"</td><td>"+picilisttext+"</td><td>"+youhuifwtext+"</td><td>"+selectedvaluetext+"</td><td class='jf_Operation'><span style='cursor:pointer;' class='fa fa-pencil updatefabu' id='update"+index+"'></span>&nbsp;&nbsp;<span style='cursor:pointer;' class='fa fa-trash-o shanchugz' id='delete"+index+"'></span></td></tr>").appendTo("#fabutable"); 
	 $('#mymodaljf').modal('hide');
}
//监听学生数点击动作

$("#fabutable").on("click",".downStu",function(){
	var ind_num = $(this).attr("index");
	var orgtreeid = fbArray[ind_num].orgtreeid;
	var nianjilist = fbArray[ind_num].nianjilist;
	var cengcilist = fbArray[ind_num].cengcilist;
	var picilist = fbArray[ind_num].picilist;
	window.location.href=encodeURI(_basepath+'pay/exportStuNumList.json?orgtreeid='+orgtreeid+
			'&nianjilist='+nianjilist+'&cengcilist='+cengcilist+'&picilist='+picilist
	);
});

function getDate(strDate) {    
    var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,    
    function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');    
    return date;    
};



op.openImportlist = function(item_pkid){
	
//	if("undefined" == typeof item_pkid){
//		item_pkid = "";
//	}
	
	var dialog = BootstrapDialog.show({
		title:'导入缴费名单',
		message: $('<div></div>').load("pay/import-list.json?item_pkid="+item_pkid),
		closable: true,//是否显示叉号
		draggable: true,//可以拖拽
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




op.deletedrlist = function(item_pkid){
	
	BootstrapDialog.show({  //显示需要提交的表单。
    	title:'提示信息',	
    message: '删除原导入的数据,并不可恢复？',
    closable: false,
      buttons: [{
	    label: '确定',
	    cssClass: 'btn-danger',
	    action: function(dialogRef){
	   
	    	
	    	var url=_basepath+"pay/deletedr.json";
	 		$.ajax({
	 			type:"post",
	 			dataType:"json",
	 			
	 			data:{pkid:item_pkid},
	 			url:url,
	 			 success:function(data){
	 				 if(data.result=="success"){
	 					 layer.msg("删除成功!");
	 					 $("#xslist").val("");
	 					dialogRef.close();
	 					}
	 				 
	 			  },
	 			error: function (XMLHttpRequest, textStatus, errorThrown) { 
	 			          alert(errorThrown); 
	 			}
	 		});
	    	
	    	
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
	
};



op.savefabu=function(){
	
	
	
	var RIQIqi = $("#RIQIqi").val();
	var RIQIzhi = $("#RIQIzhi").val();
	var xmmc = $("#fabuxm").val();
	if(xmmc==null || ""==xmmc){
		layer.msg("缴费项目名称不能为空！");
		return false;
	}
	
	//是否包含子项
	var IS_INCLUDE_CHILD = $("#is_include_child").val();
	
	
/*	if(RIQIqi==null || ""==RIQIqi){
		layer.msg("请选择起始日期！");
		return false;
	}
	if(RIQIzhi==null || ""==RIQIzhi){
		layer.msg("请选择结束日期！");
		return false;
	}
	*/
	
	var IS_FQ = $("#ISFQ").is(':checked');//是否启用
	if(IS_FQ){
		IS_FQ = 'Y';
	}else{
		IS_FQ = 'N';
	}
    if(RIQIqi!=null && ""!=RIQIqi){
   	 if(RIQIzhi==null || ""==RIQIzhi){
   			layer.msg("有效期设置的日期起始和结束值必须同时选择！");
   			return false;
   		}
	}
   
   if(RIQIzhi!=null && ""!=RIQIzhi){
  	 if(RIQIqi==null || ""==RIQIqi){
  			layer.msg("有效期设置的日期起始和结束值必须同时选择！");
  			return false;
  		}
	}
	
	if(getDate(RIQIqi)>getDate(RIQIzhi)){
		 layer.msg("开始日期不能大于结束日期！");
		return false;
	};
	
	//项目名称
	var fabuxm = $("#fabuxm").val();
	//缴费类型
	var PAY_TYPE_PKID = $("#pay_style").val();
	//学年
	var SCHOOL_YEAR_PKID = $("#school_year").val();
	//项目有效期起
	var yxqqi = $("#RIQIqi").val();
	//项目有效期止
	var yxqzhi = $("#RIQIzhi").val();
	//导入或规则
	var ITEMLIST_CREATEMODE=$("#gzhdr").val();
	 if (ITEMLIST_CREATEMODE == 2) {
	  	 if(fbArray.length==0){
	  		 layer.msg("请添加至少一条规则！");
	  		 return false;
	  	 }
     }
	var list_child = new Array();
	var list_child_name = new Array();
	var picPathArray = new Array();//记录图片路径集合
	var picTextArray = new Array();//记录图片备注集合
	if(IS_INCLUDE_CHILD=='Y'){
		$("#child").find(".child_item").each(function(v){
			// 然后在each里面去写你的逻辑
			list_child.push($(this).attr("itemPkid"));
			list_child_name.push($(this).val());
		});
		$("#child").find(".imgpic").each(function(){
			var alt = $(this).attr("alt");
			if(alt=='null' || alt==null){
				alt = '';
			}
			picPathArray.push(alt);
		});
		$("#child").find(".beizhu_child").each(function(){
			picTextArray.push($(this).val());
		});
	}
	 //是否必须缴费
	var MUSTPAY = $('#bixujf').val();
	var successList=$("#list").val();
	//var xsList=$("#xslist").val();
	var xiugai="Y";
	var pkid=$('#pkid').val();
	var room_interface = $("#room_interface").val();//是否开通了宿舍接口
	var pay_style = $("#pay_style option:selected").text();//是否选择的是住宿费	
	var data={pkid:pkid,flag_ggje:flag_ggje,successList:successList,fabuxm:fabuxm,yxqqi:yxqqi,yxqzhi:yxqzhi,PAY_TYPE_PKID:PAY_TYPE_PKID,SCHOOL_YEAR_PKID:SCHOOL_YEAR_PKID,
			list_child:JSON.stringify(list_child),list_child_name:JSON.stringify(list_child_name), picPathArray:JSON.stringify(picPathArray),picTextArray:JSON.stringify(picTextArray),
			IS_INCLUDE_CHILD:IS_INCLUDE_CHILD,ITEMLIST_CREATEMODE:ITEMLIST_CREATEMODE,MUSTPAY:MUSTPAY,fbArray:JSON.stringify(fbArray),xiugai:xiugai,
			'room_interface':room_interface,'pay_style':pay_style,'IS_FQ':IS_FQ};
	if(pay_style=='住宿费'){
		 var chargerule=$("#chargerule").val();
		 var school_year_yuefen = $("#school_year option:selected").attr("yuefen");
		 if(chargerule=='0' && ''==school_year_yuefen ){
			 layer.msg("你选择的学年尚未设置月份!");
			 layer.closeAll('loading');
			 return false;
		 }
		 data.chargerule=chargerule;
		 
	 }
	 var url=_basepath+"pay/fabuUpadte.json";
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
//优惠方式change时间
//var selectedvaluetext = $("input[type=radio][name=yhfs]:checked").attr('val');
$("input[type=radio][name=yhfs]").change(function() { 
	var selectedvaluetext = $("input[type=radio][name=yhfs]:checked").val();
	if(selectedvaluetext ==0){ //无优惠
		$("#yhdiv").hide();
		$("#dazheshu").val("");
		$("#zhijianshu").val("");
		 $("input[name='youhuifw']").removeAttr("checked"); 
	}else if(selectedvaluetext ==1){//打折
		$("#yhdiv").show();
		$("#zhijianshu").val("");
		
	}else if(selectedvaluetext ==2){//直减
		$("#yhdiv").show();
		$("#dazheshu").val("");
		
	}
}); 



//更改金额后触发
/*$("#fabuje").focus(function(){
	
	var gzhdr=$("#gzhdr").val();
	if(gzhdr =='1'){  //导入
		BootstrapDialog.show({  //显示需要提交的表单。
        	title:'提示信息',	
        message: '若更改金额则需要重新导入名单数据？',
        closable: false,
          buttons: [{
		    label: '确定',
		    cssClass: 'btn-danger',
		    action: function(dialogRef){
		    	flag_ggje="true";
		    //	$("#fabuje").val(""); 
		    	//$("#fabuje").focus();
		    	$("#list").val(""); 
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
	}else if(gzhdr =='2'){  //规则生成
		
		
	}
	

	});
*/



//model框show
$('#jfszbtn').click(function(){
	$(".itemCostArea").empty();
	var xmmc = $("#fabuxm").val();
	if(xmmc==null || ""==xmmc){
		layer.msg("请先填写项目名称！");
		return false;
	}
	if($("#school_year  option:selected").attr("yuefen")=="" 
		&& $("#chargerulediv option:selected").val()=="0" 
			&& $("#pay_style option:selected").attr("leixing")=="住宿费"){
		layer.msg("您选择的学年尚未设置月份!");
		return false;
	}
	if($("#chargerulediv option:selected").val()=='0' 
		&& $("#pay_style option:selected").attr("leixing")=="住宿费"){
		var yuefenstr=$("#school_year option:selected").attr("yuefen");
		var tipStr="";
		if(yuefenstr==""){
			tipStr="<span style='color:red;'>您选择的学年尚未设置月份!</span>";				
		}else{
			tipStr="<span style='color:red;'>您选择的学年包含以下月份："+yuefenstr+"</span>";
		}
		$("#tipDiv").html(tipStr);
		$("#tipDiv").show();
	}
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	treeObj.checkAllNodes(false);
	
	var is_include_child = $("#is_include_child").val();//获取是否勾选了包含子项
	if(is_include_child=='Y'){//表示勾选了包含子项
		if(!op.childValidate()){
			return false;
		}
	}else{
		$(".itemCostArea").append('<label for="" class="jf_xing itemName"  style="width:70px;margin-left: 5px;">费用</label>'+
				'<input type="text" value="" maxlength="50" text="费用" class="form-control itemCost"'+
				'style="margin-left: 40px;" onkeyup="value=value.replace(/[^\\-?\\d.]/g,\'\')" placeholder="填写费用"  />');
	}
	
	var cityObj = $("#citySel");
	cityObj.attr("value", "");
	 //去掉选中年级
	 $("input[name='nianji']").removeAttr("checked"); 
	//去掉选中年级
	 $("input[name='cengci']").removeAttr("checked"); 
	//去掉选中年级
	 $("input[name='pici']").removeAttr("checked");
	//去掉费用
	 $(".itemCostArea").find(".itemCost").val("");
	//去掉选中范围
	 $("input[name='youhuifw']").removeAttr("checked");
	 $("#dazheshu").val("");
	 $("#zhijianshu").val("");
	 $("input[name='yhfs']").get(0).checked=true; 
	$(".queren").attr("id","guizequeren");
	//$('#mymodaljf').modal('show');
	 $('#mymodaljf').modal({
			keyboard: true
		});
	
});

op.childValidate = function(){
	var flag = true;
	var list = new Array();
	$("#child").find(".child_item").each(function(v){
		// 然后在each里面去写你的逻辑
		if($(this).val()=='' || $(this).val()==null){
			layer.msg("子项目名称不能为空");
			$(this).focus();
			flag = false;
			return flag;
		}else{
			if($.inArray($(this).val(), list)!=-1){
				layer.msg("子项目名称不能重复");
				$(this).focus();
				flag = false;
				return flag;
			}
			list.push($(this).val());
		}
		$(".itemCostArea").append('<label for="" class="jf_xing itemName"  style="margin-left: 5px;margin-bottom: 15px; width:160px;">'+$(this).val()+'-费用</label>'+
				'<input type="text" value="" maxlength="10" text="'+$(this).val()+'"  class="form-control itemCost"'+
		'style="margin-left: 40px;" onkeyup="value=value.replace(/[^\\-?\\d.]/g,\'\')" placeholder="填写费用"  /><div style="clear:both;"></div>');
	});
	return flag;
}




//更改金额后触发
/*$("#fabuje").focus(function(){
	
	var gzhdr=$("#gzhdr").val();
	if(gzhdr =='1'){  //导入
		BootstrapDialog.show({  //显示需要提交的表单。
        	title:'提示信息',	
        message: '若更改金额则需要重新导入名单数据？',
        closable: false,
          buttons: [{
		    label: '确定',
		    cssClass: 'btn-danger',
		    action: function(dialogRef){
		    	flag_ggje="true";
		    //	$("#fabuje").val(""); 
		    	//$("#fabuje").focus();
		    	$("#list").val(""); 
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
	}else if(gzhdr =='2'){  //规则生成
		
		
	}
	

	});*/


//发布缴费保存方法
$('#btn_save2').click(function(){
	 // var jje=$('#jje').val();
//	 var xje=$('#fabuje').val();
//	 if($.trim(jje) != $.trim(xje)){   //更改了金额
		 var gzhdr=$("#gzhdr").val();
			if(gzhdr =='1'){  //导入
		    	flag_ggje="true";
		    	op.savefabu();
			}else if(gzhdr =='2'){  //规则生成
				 var flag=true;
				 var list = new Array();
				//是否包含子项
				var IS_INCLUDE_CHILD = $("#is_include_child").val();
				if(IS_INCLUDE_CHILD=='Y'){
					$("#child").find(".child_item").each(function(v){
						// 然后在each里面去写你的逻辑
						if($(this).val()=='' || $(this).val()==null){
							layer.msg("子项目名称不能为空");
							$(this).focus();
							flag = false;
							return flag;
						}else{
							if($.inArray($(this).val(), list)!=-1){
								layer.msg("子项目名称不能重复");
								$(this).focus();
								flag = false;
								return flag;
							}
							list.push($(this).val());
						}
					});
					for(var ind=0;ind<list.length;ind++){
						if(list[ind]!=oldChildNameList[ind]){
							layer.msg("子项目名称与规则子项目名称不一致！");
							flag = false;
							return flag;
						}
					}
					
				}
				if(!flag){
					return false;
				}
				 
				 for(var i=0;i<fbArray.length;i++){
					var sjz=fbArray[i].zhijianshu;
					var itemMap = fbArray[i].itemMap;
					var select=fbArray[i].selectedvalue.toString();
					if(select=="2"){
						//遍历动态子项目费用列
						 $.each(itemMap,function(key,values){
							 var je = values;
							 if(eval(sjz) > eval(je)){
								 flag=true;
								 layer.msg("优惠金额大于发布金额,请更改规则直减金额!");
								 return false;
							 }
						 }); 
					}
				 }
				 if(!flag){
					 return false;
				 }else{
					 flag_ggje="true";
					 op.savefabu();
				 }
				
				
				/*BootstrapDialog.show({  //显示需要提交的表单。
		        	title:'提示信息',	
		        message: '若更改金额则需要生成规则数据？',
		        closable: false,
		          buttons: [{
				    label: '确定',
				    cssClass: 'btn-danger',
				    action: function(dialogRef){
				    	flag_ggje="true";
				    //	$("#fabuje").val(""); 
				    	//$("#fabuje").focus();
				    	op.savefabu();
				    	$("#jje").val(""); 
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
		        });*/
				
				
				
			}
	 /*}else{
		 flag_ggje="true";
		 op.savefabu();
	 }*/
	
	
	
	
});


//验证项目名称是否重复
$('#fabuxm').change(function(){
	var flag=true;
	var mc=$('#fabuxm').val();
	var url=_basepath+"pay/yanzmc.json";
	$.ajax({
		type:"post",
		dataType:"json",
		data:{MC:mc},
		url:url,
		 success:function(data){
			 if(data.result=="false"){
				 layer.msg("项目名称已存在,请更换!");
				 $('#fabuxm').val("");
				 flag=false;
				}
			 
		  },
		error: function (XMLHttpRequest, textStatus, errorThrown) { 
		          alert(errorThrown); 
		}
	});
	return flag;
	
});




//规则设置保存方法  $("#testdiv ul").on("click","li", function() {document
$(".modal-footer").on("click","#guizequeren", function() {
	op.guizequeren();
});


//规则编辑保存方法
$(".modal-footer").on("click","#xiugaigz", function() {
	op.xiugaigz();
});

//导入名单
$("#btn_import_list_dr").click(function(){
	op.openImportlist("");
});

//删除名单
$("#delete_list_dr").click(function(){
	var pkid=$('#pkid').val();
	op.deletedrlist(pkid);
});

$(function(){
	
	
	
	$.ajaxSetup({cache : false });
	
	 //删除发布缴费 规则设置
	 $("#fabutable").on("click",".shanchugz",function(e){
		 var trNumber = $(this).parent().parent().attr("trNumber");
		 for(var i=0;i<fbArray.length;i++){
		var inde=fbArray[i].index;
		if(inde == trNumber){
			fbArray.splice(i, 1)
		}
        }
			$(this).parent().parent().remove();
		
	});
	 //编辑 发布缴费的某条规则设置
	 $("#fabutable").on("click",".updatefabu",function(e){
		// $('#mymodaljf').modal('show');
		 var IS_INCLUDE_CHILD = $("#is_include_child").val();
		 if(IS_INCLUDE_CHILD=='Y'){
			 pm.updateItemName();
		 }
		 $(".queren").attr("id","xiugaigz");
		 
		 //去掉选中年级
		 $("input[name='nianji']").removeAttr("checked"); 
		//去掉选中年级
		 $("input[name='cengci']").removeAttr("checked"); 
		//去掉选中年级
		 $("input[name='pici']").removeAttr("checked"); 
		//去掉选中范围
		 $("input[name='youhuifw']").removeAttr("checked"); 
		 
		 //打折数和直减金额付空
		 var dazheshu = $("#dazheshu").val("");
		    var zhijianshu = $("#zhijianshu").val("");
		 
		    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");  
		    treeObj.checkAllNodes(false);
		    
		    
		 //打开modal
		 $('#mymodaljf').modal({
				keyboard: true
			});
		//得到选中行的array
		 var trNumber = $(this).parent().parent().attr("trNumber");//得到行号
		 $("#trnumber").val(trNumber);
		 var nianjil;
		 var cengcil;
		 var picil;
		 var youhuifwl;
		 var ztreel;
		 var selectedvaluel;
		 var youhuije;
		 var dazheshu;
		 var treetext;
		 var itemMap;
		 for(var i=0;i<fbArray.length;i++){
				var inde=fbArray[i].index.toString();
				if(inde == trNumber){
					nianjil=fbArray[i].nianjilist;
					cengcil=fbArray[i].cengcilist;
					picil=fbArray[i].picilist;
					youhuifwl=fbArray[i].youhuifw;
					ztreel=fbArray[i].orgtreeid;
					selectedvaluel=fbArray[i].selectedvalue;
					youhuije=fbArray[i].zhijianshu;
					dazheshu=fbArray[i].dazheshu;
					treetext=fbArray[i].orgtreeidtext;
					itemMap=fbArray[i].itemMap;
				}
		        }
		 $(".itemCostArea").empty();
		 var is_include_child = $("#is_include_child").val();//获取是否勾选了包含子项
		 if(is_include_child=='Y'){//表示勾选了包含子项
			 //遍历动态子项目费用列
			 $.each(itemMap,function(key,values){
				 $(".itemCostArea").append('<label for="" class="jf_xing itemName"  style="margin-left: 5px;margin-bottom: 15px; width:160px;">'+key+'-费用</label>'+
						 '<input type="text" value="'+values+'" maxlength="10" text="'+key+'"  class="form-control itemCost"'+
				 'style="margin-left: 40px;" onkeyup="value=value.replace(/[^\\-?\\d.]/g,\'\')" placeholder="填写费用"  /><div style="clear:both;"></div>');
				 //$(".itemCostArea").find("input[text='"+key+"']").val(values);
			 }); 
		 }else{
			 $.each(itemMap,function(key,values){
				 $(".itemCostArea").append('<label for="" class="jf_xing itemName"  style="margin-left: 5px;">费用</label>'+
							'<input type="text" value="'+values+'" maxlength="50" text="'+key+'" class="form-control itemCost"'+
							'style="margin-left: 40px;" onkeyup="value=value.replace(/[^\\-?\\d.]/g,\'\')" placeholder="填写费用"  />');
			 });
		 }
		 
		 $("#orgtree").attr("value", ztreel);
		 var cityObj = $("#citySel");
		 cityObj.attr("value", treetext);
		 //选中行的checkbox数据和页面匹配 选中
		 obj = document.getElementsByName("nianji");
		 nianjil=nianjil+",";
		 nianjil= nianjil.split(',');
		    for(k in obj){
		    	for(n in nianjil){
		    		if(obj[k].value == nianjil[n]){
		    			obj[k].checked=true;
		    		}
		    	}
		    }
		    
		  //选中行的checkbox数据和页面匹配 选中
			 obj = document.getElementsByName("cengci");
			 cengcil=cengcil+",";
			 cengcil= cengcil.split(',');
			    for(k in obj){
			    	for(n in cengcil){
			    		if(obj[k].value == cengcil[n]){
			    			obj[k].checked=true;
			    		}
			    	}
			    }
			    
			    
			  //选中行的checkbox数据和页面匹配 选中
				 obj = document.getElementsByName("pici");
				 picil=picil+",";
				 picil= picil.split(',');
				    for(k in obj){
				    	for(n in picil){
				    		if(obj[k].value == picil[n]){
				    			obj[k].checked=true;
				    		}
				    	}
				    }
		  //选中行的checkbox数据和页面匹配 选中 优惠范围
		    youhuifwl=youhuifwl+",";
		    youhuifwl= youhuifwl.split(',');
		    objyouhuifw = document.getElementsByName("youhuifw");
		    for(k in objyouhuifw){
		    	for(n in youhuifwl){
		    		if(objyouhuifw[k].value == youhuifwl[n]){
		    			objyouhuifw[k].checked=true;
		    		}
		    	}
		    }
		    
		    //优惠方式
		   
		    selectedvaluel=selectedvaluel+"";
		    objyhfs = document.getElementsByName("yhfs");
		    for(k in objyhfs){
		    	for(n in selectedvaluel){
		    		if(objyhfs[k].value == selectedvaluel[n]){
		    			objyhfs[k].checked=true;
		    			if(selectedvaluel[n]=="1"){
		    				 var dazheshu = $("#dazheshu").val(dazheshu);
		    				 $("#yhdiv").show();
		    			}else if(selectedvaluel[n]=="2"){
		    				var zhijianshu = $("#zhijianshu").val(youhuije);
		    				$("#yhdiv").show();
		    			}else if(selectedvaluel[n]=="0"){  //无优惠
		    				$("#yhdiv").hide();
		    				 $("input[name='youhuifw']").removeAttr("checked");
		    			}
		    		}
		    	}
		    }
		   //树信息
		    
		   // $("#citySel").val(treetext);
		    //$(this).parent().parent().remove();
 		 // edit_zizhi(ZIZHIMC,ZIZHIWJBH,BEIZHU,trNumber,PKID_ZIZHI);
		// $(this).parent().parent().remove();
	});
	 



});
