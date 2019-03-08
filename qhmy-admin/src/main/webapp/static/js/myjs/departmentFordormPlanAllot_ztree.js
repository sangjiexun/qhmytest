//@ sourceURL=departmentFordormPlanAllot_ztree.js
var setting4 = {
			check: {
				enable: true,
//				chkStyle: "checkbox",
//				chkboxType: { "Y": "s", "N": "ps" }
				chkStyle: "radio",  //单选框
	            radioType: "all"   //对所有节点设置单选
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
				onClick: onClick4,
				onCheck: onCheck4
			}
		};
		
		var zNodes4/* =[
			{"id":"41", "pId":"0", "name":"石家庄"}
		 ]*/;
		var jsonStr = '';
		$.ajax({
			  type: 'POST',
			  url: _basepath+"studentDorm/getDepartmentPlanTreeForQuery.json",
			  async: false,
			  data: {pkid:$("#treeNodeId").val()},
			  dataType : "json",
			  success : function(result) {
				  if(result.result == true){
					  zNodes4 = result.departmentTreeJsonData;
				  }
				  return false;
			  }
			});
		zNodes4 = eval(zNodes4);
		function onClick4(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo4");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}

		function onCheck4(e, treeId, treeNode) {
			 $("#citySel4").attr("value", "");
			var zTree = $.fn.zTree.getZTreeObj("treeDemo4"),
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
			var cityObj = $("#citySel4");
			cityObj.attr("value", v);
			$("#orgtree4").attr("value", nodeid);
		}

		function showMenu4() {
			var cityObj = $("#citySel4");
			var cityOffset = $("#citySel4").offset();
			$("#menuContent4").css({left:"0px", top:"34px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown4);
		}
		function hideMenu4() {
			$("#menuContent4").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown4);
		}
		function onBodyDown4(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "citySel4" || event.target.id == "menuContent4" || $(event.target).parents("#menuContent4").length>0)) {
				hideMenu4();
			}
		}

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo4"), setting4, zNodes4);
		});
		
		