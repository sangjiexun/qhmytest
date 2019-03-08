//@ sourceURL=stu_ztree2.js 
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
				onClick: onClick2,
				onCheck: onCheck2
			}
		};

		function onClick2(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo2");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}

		function onCheck2(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo2"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			nodeid="";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				nodeid=nodes[i].id;
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#citySel2");
			cityObj.attr("value", v);
			$("#orgtree2").attr("value", nodeid);
		}

		function showMenu2() {
			var cityObj = $("#citySel2");
			var cityOffset = $("#citySel2").offset();
			$("#menuContent2").css({left:"73px", top:"34px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown2);
		}
		function hideMenu2() {
			$("#menuContent2").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown2);
		}
		function onBodyDown2(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "citySel2" || event.target.id == "menuContent2" || $(event.target).parents("#menuContent2").length>0)) {
				hideMenu2();
			}
		}

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo2"), setting, zNodes);
		});
		
		