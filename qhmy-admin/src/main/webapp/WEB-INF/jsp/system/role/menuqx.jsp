<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../index/top.jsp"%>
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	
	
		<link type="text/css" rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css"/>
	<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.excheck.js"></script>
	<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.exhide.js"></script>
	<style type="text/css">
	footer{height:50px;position:fixed;bottom:0px;left:0px;width:100%;text-align: center;}
	</style>
<body>

</head>
<body class="no-skin">
	
	
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<div id="zhongxin">
								<div style="overflow: scroll; scrolling: yes;height:415px;width: 309px;">
								<ul id="tree" class="ztree" style="overflow:auto;"></ul>
								</div>
							</div>
							<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">正在保存...</h4></div>
							</div>
						<!-- /.col -->
						</div>
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
	
		<div style="width: 100%;padding-top: 5px;" class="center">
			<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
			<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
		</div>
	
	<script type="text/javascript">
		$(top.hangge());
		var zTree;
		$(document).ready(function(){
			
			var setting = {
			    showLine: true,
			    checkable: true,
			/*     data: {
					simpleData: {
						enable: false,
						idKey: "id",
						pIdKey: "pId"
					},
					key: {
						checked: "isChecked",
						name: "name"
					}
				},
				view : {
					filter:true,  //是否启动过滤
					expandLevel:0,  //展开层级
					showFilterChildren:true, //是否显示过滤数据孩子节点
					showFilterParent:true, //是否显示过滤数据父节点
					showLine : false
				},  */
				check: {
					enable: true,
					 chkDisabledInherit: false,
					 chkStyle: "checkbox",
					chkboxType: { "Y": "ps", "N": "s" }   
				}
			};
			
			var zn = '${zTreeNodes}';
			var zTreeNodes = eval(zn);
			
		/* 	
			for(var i=0;zTreeNodes.length>i;i++){
				console.log(zTreeNodes[i].name);
				console.log(zTreeNodes[i].id);
				console.log(zTreeNodes[i].pId);
			}
			 */
			//console.log(zTreeNodes);
			//zTree = $("#tree").zTree(setting, zTreeNodes);
			
			zTree = $.fn.zTree.init($("#tree"), setting, zTreeNodes);
			//$("#disabledTrue").bind("click", {disabled: true}, disabledNode);
			//$("#disabledFalse").bind("click", {disabled: false}, disabledNode);
			
			var roleid = '${roleid}';
			var parent_id = '${parent_id}';
			var pid = '${pid}';
			var a = '${a}';
			var ROLE_ID = '${ROLE_ID}';
			var departmentid1 = '${departmentid1}';
			
			if("0" == departmentid1 && "0" == parent_id){
				
				}else{
					 // 遍历所有节点，恢复禁用状态为活动状态
					 var dsblNodes = zTree.getNodesByParam("chkDisabled", true);

					 // 遍历节点取消禁用状态
					 for (var i=0, l=dsblNodes.length; i < l; i++) {

					     // 取消禁用状态
					     zTree.setChkDisabled(dsblNodes[i], false);
					 }

					 // 取得未选中的节点
					var nodes1 = zTree.getCheckedNodes(false);

					 // 遍历节点恢复禁用状态
					 for (var i=0, l=dsblNodes.length; i < l; i++) {

					     // 恢复禁用状态
					     zTree.setChkDisabled(dsblNodes[i], true);
					 }
					for(var j=0;j<nodes1.length;j++){
						zTree.hideNode(nodes1[j]);
					}}
			
			
			
		});
	
		//保存
		 function save(){

			 // 遍历所有节点，恢复禁用状态为活动状态
			 var dsblNodes = zTree.getNodesByParam("chkDisabled", true);

			 // 遍历节点取消禁用状态
			 for (var i=0, l=dsblNodes.length; i < l; i++) {

			     // 取消禁用状态
			     zTree.setChkDisabled(dsblNodes[i], false);
			 }

			 // 取得选中的节点
			var nodes = zTree.getCheckedNodes();
			var nodes1 = zTree.getCheckedNodes(false);
			//alert(nodes1);
			 // 遍历节点恢复禁用状态
			 for (var i=0, l=dsblNodes.length; i < l; i++) {

			     // 恢复禁用状态
			     zTree.setChkDisabled(dsblNodes[i], true);
			 }
			
	
			var tmpNode;
			var ids = "";
			for(var i=0; i<nodes.length; i++){
				tmpNode = nodes[i];
				if(i!=nodes.length-1){
					ids += tmpNode.id+",";
				}else{
					ids += tmpNode.id;
				}
			}
		
			var ROLE_ID = "${ROLE_ID}";
			var url = "<%=basePath%>role/saveMenuqx.do";
			var postData;
			postData = {"ROLE_ID":ROLE_ID,"menuIds":ids};
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			$.post(url,postData,function(data){
				top.Dialog.close();
			});
	
		 }
	
	</script>
</body>
</html>