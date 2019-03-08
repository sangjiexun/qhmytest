//@ sourceURL=goodsReturn_ztree.js 
var setting4 = {
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "s", "N": "ps" }
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
		
		var zNodes4;
		var jsonStr = '';
		$.ajax({
			  type: 'POST',
			  url: _basepath+"receipt/getAllPayItemListList.json",
			  async: false,
			  data: {},
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  zNodes4 = result.payItemListResult;
				  }
				  return false;
			  }
			});
		zNodes4 = eval(zNodes4);
		function onClick2(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo2");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}

		function onCheck2(e, treeId, treeNode) {
			 $("#citySel2").attr("value", "");
			var zTree = $.fn.zTree.getZTreeObj("treeDemo2"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			nodeid="";
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].id != '@@@'){
					v += nodes[i].name + ",";
					nodeid+=nodes[i].id+",";
				}
				
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#citySel2");
			cityObj.attr("value", v);
			$("#payitem").attr("value", nodeid);
		}

		function showMenu2() {
			var cityObj = $("#citySel2");
			var cityOffset = $("#citySel2").offset();
			$("#menuContent2").css({left:"0px", top:"34px"}).slideDown("fast");

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
			$.fn.zTree.init($("#treeDemo2"), setting4, zNodes4);
		});
		
var setting5 = {
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "s", "N": "ps" }
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
				onClick: onClick5,
				onCheck: onCheck5
			}
		};

		function onClick5(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}

		function onCheck5(e, treeId, treeNode) {
			 $("#citySel").attr("value", "");
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			nodeid="";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				nodeid+=nodes[i].id+",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#citySel");
			cityObj.attr("value", v);
			$("#orgtree").attr("value", nodeid);
		}

		function showMenu5() {
			var cityObj = $("#citySel");
			var cityOffset = $("#citySel").offset();
			$("#menuContent").css({left:"0px", top:"34px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown5);
		}
		function hideMenu5() {
			$("#menuContent").fadeOut("fast");
			$("#itemContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown5);
		}
		function onBodyDown5(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0 || event.target.id == "itemSel" || event.target.id == "itemContent" || $(event.target).parents("#itemContent").length>0)) {
				hideMenu5();
			}
		}

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting5, zNodes);
		});
				
					