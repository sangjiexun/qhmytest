//@ sourceURL=studentDormFordormPlanAllot_ztree.js 
var setting3 = {
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
				onClick: onClick3,
				onCheck: onCheck3
			}
		};
		
		var zNodes3/* =[
			{"id":"41", "pId":"0", "name":"石家庄"}
		 ]*/;
		var jsonStr = '';
		$.ajax({
			  type: 'POST',
			  url: _basepath+"studentDorm/getStudentDormPlanTreeForAllot.json",
			  async: false,
			  data: {pkid:$("#treeNodeId").val(),ALLOT_TYPE:'0',STATUS:"0"},
			  dataType : "json",
			  success : function(result) {
				  if(result.result == true){
					  zNodes3 = result.studentDormTreeJsonData;
				  }
				  return false;
			  }
			});
		zNodes3 = eval(zNodes3);
		function onClick3(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo3");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}

		function onCheck3(e, treeId, treeNode) {
			 $("#citySel3").attr("value", "");
			var zTree = $.fn.zTree.getZTreeObj("treeDemo3"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			//var  nodeid=$("#orgtree").attr("value");
			nodeid="";
			isXM="";
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].id != '@@@'){
					v += nodes[i].name + ",";
//					nodeid+=nodes[i].id+",";
					if(nodes[i].isXM != null && nodes[i].isXM != '' && nodes[i].isXM != 'null')//只有床才赋值
						nodeid+=nodes[i].isXM+",";
				}
				
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#citySel3");
			cityObj.attr("value", v);
			$("#orgtree3").attr("value", nodeid);
//			$("#isXM3").attr("value", isXM);
		}

		function showMenu3() {
			var cityObj = $("#citySel3");
			var cityOffset = $("#citySel3").offset();
			$("#menuContent3").css({left:"0px", top:"34px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown3);
		}
		function hideMenu3() {
			$("#menuContent3").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown3);
		}
		function onBodyDown3(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "citySel3" || event.target.id == "menuContent3" || $(event.target).parents("#menuContent3").length>0)) {
				hideMenu3();
			}
		}

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo3"), setting3, zNodes3);
		});
		
		