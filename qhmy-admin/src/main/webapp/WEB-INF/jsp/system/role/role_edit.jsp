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
<meta http-equiv="Content-Type" content="text/html;charset=gbk"/>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>

<link rel="stylesheet" href="${basepath}static/selecttree/selecttree.css" />
<link rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css" />

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
						<form action="role/${msg}.do" name="form1" id="form1"  method="post">
						<input type="hidden" name="ROLE_ID" id="id" value="${pd.ROLE_ID}"/>
						<input type="hidden" name="DEPARTMENT_ID" id="department_id" value="${pd.DEPARTMENT_ID}"/>
						<input name="PARENT_ID" id="parent_id" value="${pd.parent_id }" type="hidden">
							<div id="zhongxin" style="padding-top:13px;">
							<table class="center" style="width:100%;">
								<tr style="text-align: center;">
									<td style="width:79px;text-align: right;padding-top: 13px;">角色名称:</td><td><input type="text" name="ROLE_NAME" id="roleName" placeholder="这里输入名称" value="${pd.ROLE_NAME}"  title="名称" style="width:99%;"/></td>
								</tr>
								<c:if test="${pd.ROLE_ID == '0'}">
								<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">所属单位:</td>
								<td width="195px">
										<!-- 显示名称,修改了此input的样式 -->
										<input class="input_css" value="部门名称" type="hidden" id="jg_mc_idgxjg"
											 name="bmmc" value="${pd.NAME}" onClick="getOrgTree_dailog('gxjg');" size="30" />
										<!-- end 显示名称,修改了此input的样式 -->
										
										<!-- input后面那个搜索按钮 -->
										<div class="combox" style="width:135px">
										  <div id="combox_zsfs" class="select" style="width:100%" >
										    <a id="jg_mc_idgxjgdiv" class="" value="全部" name="zsfs" style="width:100%;" href="javascript:void(0);" onClick="getOrgTree_dailog('gxjg');">
										      <span  placeholder="这里选择部门信息" style="line-height: 22px;margin-left: 6px;"   title="所属部门">${pd.NAME}</span>
										    </a>
										  </div>
										</div>
										<!-- end input后面那个搜索按钮 -->
										
										<!-- 选中节点的编码 -->
										<input type="hidden" id="jg_dm_idgxjg" name="suoshuzagxjg" value="${pd.DEPARTMENT_ID}" />
										<!-- end 选中节点的编码 -->
										
										<!-- ztree弹出层 -->
										<div id="menuContentgxjg" class="menuContent" style="display:none; position: absolute;width:200;">
											<ul id="orgTreeSelectgxjg" class="ztree" style="margin-top:0; width:200px; height:300;"></ul>
											<iframe style='position:absolute;top:0px;left:0px; width:200px; height:80px; z-index:-1;'></iframe>
										</div>
										<!-- end ztree弹出层 -->
									</td>
								
							</tr>
								</c:if>
								<tr style="text-align: center;">
									<td style="text-align: center;padding-top:5px;position:absolute;left:150px;top:80px">
										<a class="btn btn-mini btn-primary" onclick="save('${msg}');">保存</a>
									</td>
									<td style="text-align: center;padding-top:5px;position:absolute;left:200px;top:80px">
										<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
									</td>
								</tr>
							</table>
							</div>
						</form>
					
					<div id="zhongxin2" class="center" style="display:none"><img src="static/images/jzx.gif" style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>

					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- Ztree生成插件 -->
	<script type="text/javascript" src="static/selecttree/orgTree.js"></script>
	<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.excheck.js"></script>
	<script type="text/javascript">
	top.hangge();
	//保存
	function save(msg){
		if($("#roleName").val()==""){
			$("#roleName").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#roleName").focus();
			return false;
		}
		
		if($("#DEPARTMENT_ID").val()==""){
			$("#DEPARTMENT_ID").tips({
				side:3,
	            msg:'请选择部门',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#DEPARTMENT_ID").focus();
			return false;
		}
		
		
		var depid=$("#DEPARTMENT_ID").val();
		$("#form1").action="role/"+msg+".do?DEPARTMENT_ID="+depid;
			$("#form1").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
	}
	
	
	
	//获取企业ID
	$(document).ready(function(){
		$.ajax({
			type: "POST",
			url: '<%=basePath%>department/departmenttypeid.do',
	    	data: {DEPARTMENT_TYPE_ID:1,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
				 }else{
					if(data.dp.length == 0){
						alert("当前没有企业");
					}else{
						var data = eval("(" + data.dp + ")"); 
						for(var i = 0; i <data.length;i++){
							var deptid = data[i].DEPARTMENT_ID;
							var deptName = data[i].NAME;
							$("#DEPARTMENT_ID").append("<option value = '" + deptid + "'>" + deptName+ "</option>" );
						}
					}
				 }
			}
		});
		
	});
	
	<%-- function change(ROLE_ID){
		$.ajax({
			type: "POST",
			url: '<%=basePath%>role/getrights.do',
			data: {ROLE_ID:ROLE_ID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 alert(data);
							$("#RIGHTS").val(data.pd.RIGHTS);
						
					
				 
			}
		});
	} --%>
	
	</script>
</body>
</html>

