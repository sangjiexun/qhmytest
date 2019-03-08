<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("basepath", basePath);
%>
		<meta charset="UTF-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<title>权限管理-用户</title>
		<script type="text/javascript">
	
			var zNodes2=new Array();
	
			<c:forEach items="${departmentList}" var="t">  
				var a={id:'${t.PKID}',pId:'${t.PARENT_ID}',name:'${t.DEPARTMENT_NAME}',colleges_pkid:'${t.COLLEGES_PKID}'};
				zNodes2.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
			</c:forEach>
		</script>
		<link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css">
		<script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="${basepath}static/js/myjs/department_ztree_add.js"></script>
		<style>
			.list .jf_ZtBox{
				position: absolute; z-index:1;left:15px !important;top:39px !important;
			}
		</style>
		<form action="" class="form-horizontal">
			<input type="hidden" id="user_id" name="user_id" value="${userPageData.USER_ID }" />
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">用户名称</label>
				<div class="col-xs-7">
					<input name="username" <c:if test="${username=='admin' }">readonly='readonly'</c:if>   type="text" class="form-control" value="${userPageData.USERNAME }" placeholder="请输入内容" id="username"/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="zTreeDemoBackground left ">
					<label for="" class="col-xs-3 control-label jf_xing">所属组织</label>
					<ul class="list" style="margin-bottom:0;">
						<li class="jf_ZtLaber title col-xs-7">
						<input class="form-control" id="citySel2" type="text" readonly value="${pd.department_name}" style="width:183px;display: inline;" onclick="showMenu2();" />
							<div id="menuContent2" class="menuContent jf_ZtBox" style="display:none; position: absolute; z-index:1;">
								<ul id="treeDemo2" class="ztree" style="margin-top:0; width:300px; max-height: 300px;overflow:auto;"></ul>
							</div>
						</li>
					</ul>
				</div>
				<div style="display:none;">
			        <!-- 用来存储 树节点的id -->
				    <input type="text" name="tree" id="DEPARTMENT_PKID" class="form-control" value="${pd.department_id}"/>
			    </div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">真实姓名</label>
				<div class="col-xs-7">
					<input name="name" type="text" class="form-control" value="${userPageData.NAME }" placeholder="请输入内容" id="name"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">手机号</label>
				<div class="col-xs-7">
					<input name="phone" type="text" class="form-control" value="${userPageData.PHONE }" placeholder="请输入内容" id="phone"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label">状态</label>
				<div class="col-xs-7">
					<select id="status" name="status" class="form-control">
						<c:forEach items="${userStatusList }" var="ustatus" varStatus="">
							<option <c:if test="${userPageData.STATUS==ustatus.value }">selected</c:if> value="${ustatus.value }">${ustatus.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</form>

		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
		
		<script type="text/javascript" src="${basepath }static/js/myjs/user-add.js"></script>
		
