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
		<title>权限管理-导航页</title>
		<link rel="stylesheet" href="${basepath}static/gxjf/css/Employee.css" />
		<link rel="stylesheet" href="${basepath}static/gxjf/css/framework-font.css" />
		
		<div class="jf_ctn jf_conbg">
			<div class="jf_content jf_conDingw">
				<div class="jf_row">
						<!-- 左侧菜单 -->
						<div class="jf_shezhinav" style="margin-left:0;">
							<ul class="nav nav-pills nav-stacked">
								<c:forEach items="${menuList}" var="menu" varStatus="index">
							  		<li class="<c:if test='${index.index==0 }'>active</c:if> jf_tabli">
							  			<a href="${menu.MENU_URL}" menu_url="${menu.MENU_URL}" menu_name="${menu.MENU_NAME}" ><span class="jf_LeftList"><i class="fa fa-tags"></i>&nbsp;&nbsp;${menu.MENU_NAME}</span></a>
								    </li>
			                    </c:forEach>
							</ul>
						</div>
						<!-- end 左侧菜单 -->
						
						<!-- 右侧区域 -->
						<div class="jf_szright">
								
						</div>
						<!-- end 右侧区域 -->
						
						
						
						<div class="clearfix"></div>
				</div>
			</div>	
		</div>
		
		<script type="text/javascript" src="${basepath }static/js/myjs/authorityManageIndex.js"></script>
		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
		
		