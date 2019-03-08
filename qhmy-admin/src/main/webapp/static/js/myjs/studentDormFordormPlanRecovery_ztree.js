//@ sourceURL=studentDormFordormPlanAllot_ztree.js 
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
		
		var zNodes5/* =[
			{"id":"41", "pId":"0", "name":"石家庄"}
		 ]*/;
		var jsonStr = '';
		$.ajax({
			  type: 'POST',
			  url: _basepath+"studentDorm/getStudentDormPlanTreeForRecovery.json",
			  async: false,
			  data: {pkid:$("#treeNodeId").val(),ALLOT_TYPE:'0'},
			  dataType : "json",
			  success : function(result) {
				  if(result.result == true){
					  zNodes5 = result.studentDormTreeJsonData;
//					  var chuangCount = typeof result.chuangCount != 'undefined' && result.chuangCount != null ? result.chuangCount : 0;
//					  var chuangCount_use = typeof result.chuangCount_use != 'undefined' && result.chuangCount_use != null ? result.chuangCount_use : 0;
//					  $("#recoveryRemax").html("可回收床位共 "+chuangCount+" 张，其中 "+chuangCount_use+" 张床位已被使用");
				  }
				  return false;
			  }
			});
		zNodes5 = eval(zNodes5);
		function onClick5(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo5");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}

		function onCheck5(e, treeId, treeNode) {
			 $("#citySel5").attr("value", "");
			var zTree = $.fn.zTree.getZTreeObj("treeDemo5"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			//var  nodeid=$("#orgtree").attr("value");
			nodeid="";
			isXM="";
			ids="";
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].id != '@@@'){
					v += nodes[i].name + ",";
//					nodeid+=nodes[i].id+",";
					if(nodes[i].isXM != null && nodes[i].isXM != '' && nodes[i].isXM != 'null'){//只有床才赋值
						ids+=nodes[i].id+",";
						nodeid+=nodes[i].isXM+",";
					}
						
				}
				
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#citySel5");
			cityObj.attr("value", v);
			$("#orgtree5").attr("value", nodeid);
			
			/*$.ajax({
				 type: 'post',
				    url: _basepath+"studentDorm/getStudentDormCount.json",
				    data: {
				    	STUDENT_DORM_PKIDS : ids
				    },
				    dataType:"json",
				    async: false,
				    success: function(data) {
				    	if(data.result == true){
				    		var chuangCount = typeof data.chuangCount != 'undefined' && data.chuangCount != null ? data.chuangCount : 0;
						    var chuangCount_use = typeof data.chuangCount_use != 'undefined' && data.chuangCount_use != null ? data.chuangCount_use : 0;
						    $("#recoveryRemax").html("回收床位共 "+chuangCount+" 张，其中 "+chuangCount_use+" 张床位已被使用");
						}
				    }
			});*/
		}

		function showMenu5() {
			var cityObj = $("#citySel5");
			var cityOffset = $("#citySel5").offset();
			$("#menuContent5").css({left:"0px", top:"34px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown5);
		}
		function hideMenu5() {
			$("#menuContent5").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown5);
		}
		function onBodyDown5(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "citySel5" || event.target.id == "menuContent5" || $(event.target).parents("#menuContent5").length>0)) {
				hideMenu5();
			}
		}

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo5"), setting5, zNodes5);
		});
		
		