<%@page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%> <%@taglib prefix="c"
		uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib
		prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
		request.setAttribute("basepath", basePath);
	%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<!-- jsp文件头和头部 -->
<%@include file="../system/index/top.jsp"%>

<link rel="stylesheet" href="${basepath}static/css/cloudt.css" />
<link rel="stylesheet" href="${basepath}static/css/labor.css" />
	<!-- 新样式 -->
<link rel="stylesheet" href="static/ace/mycss/keman_yu.css" charset="utf-8" rel="stylesheet" type="text/css" >
</head>
<body class="no-skin">
	<!-- 制卡弹出层样式 -->
	<div style="padding: 1px; border: 5px solid rgb(255, 133, 51);height: 200px;"
		class="active-work-warp panel-made-ic-card">
		<div style="background-color: rgb(204, 204, 204);"
			class="inline-warp">
			<div class="first">请将IC卡放置于IC卡读卡器上</div>
			<div style="display: none;" class="third"></div>
			<div style="display: none;" class="sec"></div>
		</div>
	</div>
	<!-- end 制卡弹出层样式 -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@include file="../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/jquery.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/bootstrap.js"></script>
	<!--提示框-->
	<script src="static/js/layer/layer.js"></script>
	
	<script type="text/javascript">
			
	</script>
</body>
</html>