//@ sourceURL=updateDep_ztree.js 
var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
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
				onClick: onClick,
				onCheck: onCheck
			}
		};

//		var zNodes =[
//			{id:4, pId:0, name:"河北省", open:true, nocheck:true},
//			{id:41, pId:4, name:"石家庄"},
//			{id:42, pId:4, name:"保定"},
//			{id:43, pId:4, name:"邯郸"},
//			{id:44, pId:4, name:"承德"},
//			{id:5, pId:0, name:"广东省", open:true, nocheck:true},
//			{id:51, pId:5, name:"广州"},
//			{id:52, pId:5, name:"深圳"},
//			{id:53, pId:5, name:"东莞"},
//			{id:54, pId:5, name:"佛山"},
//			{id:6, pId:0, name:"福建省", open:true, nocheck:true},
//			{id:61, pId:6, name:"福州"},
//			{id:62, pId:6, name:"厦门"},
//			{id:63, pId:6, name:"泉州"},
//			{id:64, pId:6, name:"三明"}
//		 ];

		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDep");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}

		function onCheck(e, treeId, treeNode) {
			 $("#citySelDep").attr("value", "");
			var zTree = $.fn.zTree.getZTreeObj("treeDep"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			//var  nodeid=$("#orgtreeDep").attr("value");
			nodeid="";
			nodeleibie="";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				nodeid+=nodes[i].id;
				nodeleibie+=nodes[i].LEIBIE;
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#citySelDep");
			cityObj.attr("value", v);
			$("#orgtreeDep").attr("value", nodeid);
			$("#orgtreeDepKeBie").attr("value", nodeleibie);
			changeGradeDept();
		}

		function showMenuDep() {
			var cityObj = $("#citySelDep");
			var cityOffset = $("#citySelDep").offset();
			$("#menuContentDep").css({left:"73px", top:"34px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown44);
		}
		function hideMenu44() {
			$("#menuContentDep").fadeOut("fast");
			$("#itemContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown44);
		}
		function onBodyDown44(event) {		
			if (!(event.target.id == "menuBtn" || event.target.id == "citySelDep" || event.target.id == "menuContentDep" || $(event.target).parents("#menuContentDep").length>0 || event.target.id == "itemSel" || event.target.id == "itemContent" || $(event.target).parents("#itemContent").length>0)) {
				hideMenu44();
			}
		}

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDep"), setting, zNodes2);
		});
		
		