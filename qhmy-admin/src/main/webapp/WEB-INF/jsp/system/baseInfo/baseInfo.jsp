<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("basepath", basePath);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>基础配置-导航页</title>
		<link rel="stylesheet" href="${basepath}static/gxjf/bs/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${basepath}static/gxjf/css/Employee.css" />
		<link rel="stylesheet" href="${basepath}static/gxjf/css/framework-font.css" />
		<link href="${basepath}static/css/bootstrap-table.min.css" rel="stylesheet">
		<script src="static/js/bootstrap-table.min.js"></script>
	    <script src="static/js/bootstrap-dialog.min.js"></script>
	    <script src="static/js/bootstrap-table-zh-CN.min.js"></script>
		<style type="text/css">
			html { overflow-x:hidden; }
		</style> 
	</head>
	<body>
	
		<div class="jf_ctn jf_conbg">
			<div class="jf_content jf_conDingw">
				<div class="jf_row">
					<!-- 左侧菜单 -->
					<div class="jf_shezhinav" style="margin-left:0;">
						<ul class="nav nav-pills nav-stacked">
							<c:forEach items="${menuList}" var="menu" varStatus="index">
						  		<li class="<c:if test='${index.index==0 }'>active</c:if> jf_tabli">
						  			<a href="javascript:void(0)" menu_url="${menu.MENU_URL}" menu_name="${menu.MENU_NAME}" ><span class="jf_LeftList"><i class="fa fa-tags"></i>&nbsp;&nbsp;${menu.MENU_NAME}</span></a>
							    </li>
		                    </c:forEach>
						</ul>
					</div>
					<!-- end 左侧菜单 -->
					<div class="jf_szright">
					</div>
					<div class="clearfix"></div>
				</div>
			</div>	
		</div>	
	</body>
	<script type="text/javascript">
         var _basepath = "${basepath}";
    </script>
	<script  type="text/javascript">
		$.ajaxSetup({
		    aysnc: false // 默认同步加载
		});
		$('.jf_tabli').click(function(){
			$(this).addClass('active');
			$('.jf_tabli').not($(this)).removeClass('active');
			
			var url = $(this).find("a").attr("menu_url");
			var r = Math.random();
			url = _basepath + url +"?r="+r;
			$('.jf_szright').html("");
			layer.load(1, {
				  shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
			try {
				$('.jf_szright').load(url,function(){
					layer.closeAll('loading');
				});
			} catch (e) {
				 layer.closeAll('loading');
			}
		});
		
		//默认加载第一个左侧菜单
		$('.jf_tabli:first').click();
		
	</script>
</html>

