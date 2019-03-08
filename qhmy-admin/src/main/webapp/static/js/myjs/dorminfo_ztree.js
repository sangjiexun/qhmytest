//@ sourceURL=dorminfo_ztree.js 
var settingdorm = {
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
				onClick: onClick23,
				onCheck: onCheck23
			}
		};
		var zNodes_dorm=null;
		var jsonStr = '';

		function onClick23(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo3");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}

		function onCheck23(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo3"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			nodeid="";
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].type=='5'){
					v += nodes[i].name + ",";
					nodeid+=nodes[i].id+",";
				}
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#citySel3");
			cityObj.attr("value", v);
			$("#orgtree3").attr("value", nodeid);
		}

		function showMenu3() {
			if(zNodes_dorm==null || zNodes_dorm==''){
				layer.load(1, {
					  shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
				$.ajax({
					  type: 'POST',
					  url: _basepath+"dormitoryInfo/getStudentDormTreeList.json",
//					  async: false,
					  data: {pkid:""},
					  dataType : "json",
					  success : function(result) {
						  if(result.result == true){
							  zNodes_dorm = result.studentDormTreeJsonData;
							  layer.closeAll('loading');
								zNodes_dorm = eval(zNodes_dorm);
								$.fn.zTree.init($("#treeDemo3"), settingdorm, zNodes_dorm);
						  }
						  return false;
					  }
				});
				
			}
			
			
			var cityObj = $("#citySel3");
			var cityOffset = $("#citySel3").offset();
			$("#menuContent3").css({left:"73px", top:"34px"}).slideDown("fast");

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

//		$(document).ready(function(){
//			$.fn.zTree.init($("#treeDemo3"), setting, zNodes_dorm);
//		});
		