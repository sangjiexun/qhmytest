//@ sourceURL=fbjf_ztree.js 
var setting2 = {
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
		
		var zNodes2/* =[
			{"id":"41", "pId":"0", "name":"石家庄"}
		 ]*/;
		var jsonStr = '';
		$.ajax({
			  type: 'POST',
			  url: _basepath+"receipt/getAllPayItemListListAll.json",
			  async: false,
			  data: {},
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  zNodes2 = result.payItemListResult;
				  }
				  return false;
			  }
			});
		zNodes2 = eval(zNodes2);
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
			//var  nodeid=$("#orgtree").attr("value");
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
			$("#orgtree2").attr("value", nodeid);
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
			$.fn.zTree.init($("#treeDemo2"), setting2, zNodes2);
		});
		
		