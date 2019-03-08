var isIE=!!window.ActiveXObject; 
var isIE6=isIE&&!window.XMLHttpRequest; 
//var isIE6or7=$.browser.msie &&( /6.0/.test(navigator.userAgent)|| /7.0/.test(navigator.userAgent)) ;
var isIE6or7=false;

var thename="";


function hideAllSelect(){
 var t=document.getElementsByTagName("select");
 for (var i=0;i<t.length;i++){
  t[i].style.visibility="hidden";
 }
 for (var temp=0;temp<window.frames.length;temp++){
  var t=window.frames[temp].document.getElementsByTagName("select");
  for (var i=0;i<t.length;i++){
   t[i].style.visibility="hidden";
  }
 }
}
function showAllSelect(){
 var t=document.getElementsByTagName("select");
 for (var i=0;i<t.length ;i++){
  t[i].style.visibility="";
 }
 for (var temp=0;temp<window.frames.length ;temp++ ){
  var t=window.frames[temp].document.getElementsByTagName("select");
  for (var i=0;i<t.length ;i++){
   t[i].style.visibility="";
  }
 }
}




	//departmentTree
	var setting = {
		data: {
			key: {
				children: "nodes"
			}
		},
		async: {
			enable: true,
			url:'dictionaries/listAllDict2.do',
			autoParam:[],
			otherParam:{},
			dataFilter: filter
		},
		callback: {
			onClick: onClick
		}
	};
	
	
	
	//dict
	var settingdict = {
		data: {
	key: {
		children: "nodes"
	}
		},
		async: {
			enable: true,
			url:'dictionaries/listAllDict2.do',
			autoParam:[],
			otherParam:{},
			dataFilter: filter
		},
		callback: {
			onClick: onClick
		}
	};
	






	//小区入住统计分析用
	var settingQuhua = {
			data: {
				simpleData: {
					enable: true
				}
			},
			async: {
				enable: true,
				url:'jcxx/zuzhijigouguanli/treeQuhuaByBmdm.action',
				autoParam:[],
				otherParam:{},
				dataFilter: filter
			},
			callback: {
				onClick: onClick
			}
		};
	
	
	
	//departmentTree
	var setting_dailog = {
			data: {
				simpleData: {
					enable: false
				},
				key: {
					children: "children"
				}
			},
			
		async: {
			enable: true,
			url:'role/queryBumen.do',
			autoParam:[],
			otherParam:{},
			dataFilter: filter
		},
		callback: {
			onClick: onClick,
			onAsyncError: function(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown){
				alert(XMLHttpRequest);
				alert(textStatus);
				alert(errorThrown);
			}
		}
	};
	var setting2 = {
		data: {
			simpleData: {
				enable: true
			}
		},
		async: {
			enable: true,
			url:'yezhu/queryCheXing.do',
			autoParam:[],
			otherParam:{},
			dataFilter: filter
		},
		callback: {
			onClick: onClick
		}
	};
	
	//HangYe
	var setting3 = {
		data: {
			simpleData: {
				enable: true
			}
		},
		async: {
			enable: true,
			url:'gxzj/hytree/treeByXmDL.action',
			autoParam:[],
			otherParam:{},
			dataFilter: hyfilter
		},
		callback: {
			onClick: onClickHy
		}
	};
	
	//hangye2
	var setting4 = {
		data: {
			simpleData: {
				enable: true
			}
		},
		async: {
			enable: true,
			url:'system/hyTree.do',
			autoParam:[],
			otherParam:{},
			dataFilter: hyfilter2
		},
		callback: {
			onClick: onClickHy2
		}
	};


	//HangYe
	var setting5 = {
		data: {
			simpleData: {
				enable: true
			}
		},
		async: {
			enable: true,
			url:'chaxuntongji/xmsjcx/treeByXmDL.action',
			autoParam:[],
			otherParam:{},
			dataFilter: xmfilter
		},
		callback: {
			onClick: onClickXm
		}
	};




	
 	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}
		 
	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("orgTreeSelect"+thename),
		nodes = zTree.getSelectedNodes(),
		v = "";
		n = "";
		nodes.sort(function compare(a,b){return a.id-b.id;});
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
			n += nodes[i].id + ",";
	
		}
		if (v.length > 0 ){
		 	v = v.substring(0, v.length-1);
		 	n = n.substring(0, n.length-1);
		}
		var orgObj = $("#jg_mc_id"+thename);
		orgObj.attr("value", v);
		//hidden jg_dm
		$("#jg_mc_id"+thename+"div").html("<span style='line-height: 22px;margin-left: 6px;'>"+v+"</span>");
		$("#jg_dm_id"+thename).attr("value", n);
		hideMenu2();
	}

	function showMenu() {
		var orgObj = $("#jg_mc_id"+thename+"div");
		var orgOffset = $("#jg_mc_id"+thename+"div").offset();
		//$("#menuContent"+thename).css({left:orgOffset.left-100 + ">px", top:orgOffset.top 
		//	+ orgObj.outerHeight()-83 + "px"}).slideDown("fast");
		//alert(isIE6);
		if(isIE6or7 ){
			$("#menuContent"+thename).css({"margin-left": "-170px", top:orgOffset.top 
				+ orgObj.height() + "px"}).slideDown("fast");
		}else{
			
			$("#menuContent"+thename).css({ top:orgOffset.top 
				+ orgObj.height() + "px"}).slideDown("fast");
		}

		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent"+thename).fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "jg_mc_id"+thename || event.target.id == "menuContent"+thename || $(event.target).parents("#menuContent"+thename).length>0)) {
			hideMenu2();
			//showAllSelect();
		}
	}
	
	function getOrgTree_dailog(tname){	
		//alert(${xmsjcxparams.ysbmdm});
		thename=tname;
		//alert(setting.async.url);
		//alert(thename);
		$(document).ready(function(){
			$.fn.zTree.init($("#orgTreeSelect"+thename), setting_dailog);
		});
		showMenu();
		//hideAllSelect();
		
	}
	function showMenu_dailog() {
		var orgObj = $("#jg_mc_id"+thename+"div");
		var orgOffset = $("#jg_mc_id"+thename+"div").offset();
		if($.browser.msie &&( /7.0/.test(navigator.userAgent)) ){
			$("#menuContent"+thename).css({"margin-left": "-170px", top:orgOffset.top 
				+ orgObj.outerHeight()-83 + "px"}).slideDown("fast");
		}else{
			$("#menuContent"+thename).css({ top:orgOffset.top 
				+ orgObj.outerHeight()-83 + "px"}).slideDown("fast");
		}

		$("body").bind("mousedown", onBodyDown);
	}
	
	function getOrgTree(tname){
		//alert(${xmsjcxparams.ysbmdm});
		thename=tname;
		//alert(setting.async.url);
		$(document).ready(function(){
			$.fn.zTree.init($("#orgTreeSelect"+thename), setting);
		});
		showMenu();
		//hideAllSelect();
	}
	
	function getDemoTree(tname){
		//alert(${xmsjcxparams.ysbmdm});
		thename=tname;
		//alert(thename);
		$(document).ready(function(){
			$.fn.zTree.init($("#orgTreeSelect"+thename), setting);
		});
		showMenu();
		//hideAllSelect();
	}
	
	/*
	 * 行政区划
	 */
	function getQuhuaTree(tname){
		//alert(${xmsjcxparams.ysbmdm});
		thename=tname;
		//alert(setting.async.url);
		//alert(thename);
		$(document).ready(function(){
			console.log("bbb,"+$("#jg_dm_id"+thename).val());
			settingQuhua.async.otherParam = {
					"bmdm":$("#jg_dm_id"+thename).val()
			};
			console.log("ccc,"+settingQuhua.async.otherParam.bmdm);
			$.fn.zTree.init($("#orgTreeSelect"+thename), settingQuhua);
		});
		showMenu();
		//hideAllSelect();
	}
	
	//for county
	function getCountyOrgTree(){	
		$(document).ready(function(){
			$.fn.zTree.init($("#orgTreeSelect"+thename), setting2);
		});
		showMenu();
	}
	
	
	function getCheliangPinpaiOrgTree(tname){
		thename=tname;
		$(document).ready(function(){
			$.fn.zTree.init($("#orgTreeSelect"+thename), setting2);
		});
		showMenu();
	}
	
	
	/**************************************************/
	
	function hyfilter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}
	
	function onClickHy(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("hyTreeSelect"+thename),
		nodes = zTree.getSelectedNodes(),
		v = "";
		n = "";
		nodes.sort(function compare(a,b){return a.id-b.id;});
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
			n += nodes[i].id + ",";
		}
		if (v.length > 0 ){
		 	v = v.substring(0, v.length-1);
		 	n = n.substring(0, n.length-1);
		}
		var hyObj = $("#hy_mc_id"+thename);
		hyObj.attr("value", v);
		//hidden jg_dm
		$("#hy_mc_id"+thename+"div").html("<span style='line-height: 22px;margin-left: 6px;'>"+v+"</span>");
		if(n!='00'){
			$("#hy_dm_id"+thename).attr("value", n);
		}else{
			$("#hy_dm_id"+thename).attr("value", '');
		}
		hideHyMenu();
	}
	function showHyMenu() {
		var orgObj = $("#hy_mc_id"+thename+"div");
		var orgOffset = $("#hy_mc_id"+thename+"div").offset();
		//$("#hyContent"+thename).css({left:orgOffset.left + "", top:orgOffset.top 
		//	+ orgObj.outerHeight()-83 + "px"}).slideDown("fast");

		if(isIE6or7 ){
			$("#hyContent"+thename).css({"margin-left": "-170px", top:orgOffset.top 
				+ orgObj.outerHeight()-83 + "px"}).slideDown("fast");
		}else{
			$("#hyContent"+thename).css({ top:orgOffset.top 
				+ orgObj.outerHeight()-83 + "px"}).slideDown("fast");
		}

		$("body").bind("mousedown", onHyBodyDown);
	}
	function hideHyMenu() {
		$("#hyContent"+thename).fadeOut("fast");
		$("body").unbind("mousedown", onHyBodyDown);
	}
	function onHyBodyDown(event) {
		if (!(event.target.id == "hy_mc_id"+thename || event.target.id == "hyContent"+thename || $(event.target).parents("#hyContent"+thename).length>0)) {
			hideHyMenu();
		}
	}
	
	//for hy
	function getHyTree(tname){	
		thename=tname;
		$(document).ready(function(){
			$.fn.zTree.init($("#hyTreeSelect"+thename), setting3);
		});
		showHyMenu();
	}
	
	/***********************hy 2***************************/
	
	function hyfilter2(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}
	
	function onClickHy2(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("hyTreeSelect2"),
		nodes = zTree.getSelectedNodes(),
		v = "";
		n = "";
		nodes.sort(function compare(a,b){return a.id-b.id;});
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
			n += nodes[i].id + ",";
		}
		if (v.length > 0 ){
		 	v = v.substring(0, v.length-1);
		 	n = n.substring(0, n.length-1);
		}
		var hyObj = $("#hy_mc_id2");
		hyObj.attr("value", v);
		//hidden jg_dm
		$("#hy_dm_id2").attr("value", n);
		hideHyMenu2();
	}
	function showHyMenu2() {
		var orgObj = $("#hy_mc_id2");
		var orgOffset = $("#hy_mc_id2").offset();
		$("#hyContent2").css({left:orgOffset.left + "px", top:orgOffset.top 
			+ orgObj.outerHeight() + "px"}).slideDown("fast");

		$("body").bind("mousedown", onHyBodyDown2);
	}
	function hideHyMenu2() {
		$("#hyContent2").fadeOut("fast");
		$("body").unbind("mousedown", onHyBodyDown2);
	}
	function onHyBodyDown2(event) {
		if (!(event.target.id == "hy_mc_id2" || event.target.id == "hyContent2" || $(event.target).parents("#hyContent2").length>0)) {
			hideHyMenu2();
		}
	}
	
	//for hy2
	function getHyTree2(){	
		$(document).ready(function(){
			$.fn.zTree.init($("#hyTreeSelect2"), setting4);
		});
		showHyMenu2();
	}
	
	/******************************************************/
	//hangye
	function czhy(){
		$('#hy_dm_id'+thename).val('');
		$('#hy_mc_id'+thename).val('');
	}
	
	//hangye2
	function czhy2(){
		$('#hy_dm_id2').val('');
		$('#hy_mc_id2').val('');
	}
	
	
	
	
	
	
	
	
		/*****************xiang mu*********************************/
	
	function xmfilter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}
	
	function onClickXm(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("xmTreeSelect"+thename),
		nodes = zTree.getSelectedNodes(),
		v = "";
		n = "";
		nodes.sort(function compare(a,b){return a.id-b.id;});
		if(!treeNode.isParent){
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
			n += nodes[i].zsbm + ",";
		}
		if (v.length > 0 ){
		 	v = v.substring(0, v.length-1);
		 	n = n.substring(0, n.length-1);
		}
		var tempnode=nodes[0].getParentNode();
		
		var xmqc=nodes[0].name;
		
		var fl = parseInt(nodes[0].level);
		//alert(fl+'=='+xmqc+'=='+tempnode.name)
		
		for(var i=0;i<fl;i++){
			if(i==0){
				tempnode = nodes[0].getParentNode();
			}else{
				//alert(tempnode.getParentNode());
				
				
				tempnode = tempnode.getParentNode();
			}
			
			xmqc=tempnode.name+'-'+xmqc;
			
			//alert(i+tempnode.name);
		}
		
		
		//alert(xmqc);

		var xmObj = $("#xm_mc_id"+thename);
		xmObj.attr("value", v);
		//hidden jg_dm
		$("#xm_mc_id"+thename+"div").html("<span style='line-height: 22px;margin-left: 6px;'>"+v+"</span>");
		$("#xm_dm_id"+thename).attr("value", n);
		$("#tzly"+thename).attr("value", nodes[0].tzly);
		$("#xmlbbm"+thename).attr("value", nodes[0].xmlbbm);
		$("#xmflbm"+thename).attr("value", nodes[0].xmflbm);
		$("#xmcxym"+thename).attr("value", nodes[0].xmcxym);
		$("#xmmxtzym"+thename).attr("value", nodes[0].xmmxtzym);
		$("#xmflmc"+thename).attr("value", nodes[0].xmflmc);
		$("#xmqc"+thename).attr("value", xmqc);
		hideXmMenu();
		}
	}
	
	function showXmMenu() {
		var orgObj = $("#xm_mc_id"+thename+"div");
		var orgOffset = $("#xm_mc_id"+thename+"div").offset();
		$("#xmContent"+thename).css({left:orgOffset.left + "", top:orgOffset.top 
			+ orgObj.outerHeight()-83 + "px"}).slideDown("fast");

		$("body").bind("mousedown", onXmBodyDown);
	}
	function hideXmMenu() {
		$("#xmContent"+thename).fadeOut("fast");
		$("body").unbind("mousedown", onXmBodyDown);
	}
	function onXmBodyDown(event) {
		if (!(event.target.id == "xm_mc_id"+thename || event.target.id == "xmContent"+thename || $(event.target).parents("#xmContent"+thename).length>0)) {
			hideXmMenu();
		}
	}
	
	//for hy
	function getXmTree(tname){
		thename=tname;	
		$(document).ready(function(){
			$.fn.zTree.init($("#xmTreeSelect"+thename), setting5);
		});
		showXmMenu();
	}
	
	//for zclx
	function getZclxTree(tname){
		thename=tname;	
		$(document).ready(function(){
			$.fn.zTree.init($("#zclxTreeSelect"+thename), zclxsetting);
		});
		showZclxMenu();
	}
	var zclxsetting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		async: {
			enable: true,
			url:'chaxuntongji/qyxx/treeByZclx.action',
			autoParam:[],
			otherParam:{},
			dataFilter: filter
		},
		callback: {
			onClick: zclxonClick
		}
	};
	function showZclxMenu() {
		var orgObj = $("#zclx_mc_id"+thename+"div");
		var orgOffset = $("#zclx_mc_id"+thename+"div").offset();
		//$("#zclxContent"+thename).css({left:orgOffset.left + "", top:orgOffset.top 
		//	+ orgObj.outerHeight()-83 + "px"}).slideDown("fast");

		if(isIE6or7 ){
			$("#zclxContent"+thename).css({"margin-left": "-170px", top:orgOffset.top 
				+ orgObj.outerHeight()-83 + "px"}).slideDown("fast");
		}else{
			$("#zclxContent"+thename).css({ top:orgOffset.top 
				+ orgObj.outerHeight()-83 + "px"}).slideDown("fast");
		}



		$("body").bind("mousedown", onZclxBodyDown);
	}
	function onZclxBodyDown(event) {
		if (!(event.target.id == "zclx_mc_id"+thename || event.target.id == "zclxContent"+thename || $(event.target).parents("#zclxContent"+thename).length>0)) {
			hideZclxMenu();
		}
	}
	function hideZclxMenu() {
		$("#zclxContent"+thename).fadeOut("fast");
		$("body").unbind("mousedown", onZclxBodyDown);
	}
	function zclxonClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("zclxTreeSelect"+thename),
		nodes = zTree.getSelectedNodes(),
		v = "";
		if(nodes.length>0){
			var xx=nodes[0].id;
		}else{
			var xx="";
		}
		n = "";
		nodes.sort(function compare(a,b){return a.id-b.id;});
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
			n += nodes[i].id + ",";
		}
		if (v.length > 0 ){
		 	v = v.substring(0, v.length-1);
		 	n = n.substring(0, n.length-1);
		}
		var zclxObj = $("#zclx_mc_id"+thename);
		if(xx!='001'){
			zclxObj.attr("value", n);
		}else{
			zclxObj.attr("value", '');
		}
		$("#zclx_mc_id"+thename+"div").html("<span style='line-height: 22px;margin-left: 6px;'>"+v+"</span>");
		$("#zclx_dm_id"+thename).attr("value", n);
		hideZclxMenu();
	}
	