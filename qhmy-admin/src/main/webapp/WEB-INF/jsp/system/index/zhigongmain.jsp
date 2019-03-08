<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basepath", basePath); %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>职工首页</title>
		<link rel="stylesheet" href="${basepath}static/gxjf/bs/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${basepath}static/gxjf/css/Employee.css" />
		<link rel="stylesheet" href="${basepath}static/gxjf/css/framework-font.css" />
		<link rel="stylesheet" href="${basepath}static/gxjf/css/md-share.css" />

		<link href="${basepath}static/css/bootstrap-table.min.css" rel="stylesheet">
		<script src="static/js/bootstrap-table.min.js"></script>
	    <script src="static/js/bootstrap-dialog.min.js"></script>
	    <script src="static/js/bootstrap-table-zh-CN.min.js"></script>

      <!--   <script type="text/javascript">
        var totalCounts="${pd.totalCounts}";//总人数
        var totalOnlineCounts="${pd.totalOnlineCounts}";//网上缴费人数
        var totalOtherCounts="${pd.totalOtherCounts}";//其它缴费人数
        
        var todayTotalMoney="${pd.todayTotalMoney}";//
        var bararray=new Array();
        <c:forEach items="${list}" var="t">  
		var a={PAYITEM:'${t.PAYITEM}',TOTALMONEY:'${t.TOTALMONEY}'}
		bararray.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
		</c:forEach>
		//折线图
		var linearray=new Array();
		<c:forEach items="${xmList}" var="t">  
		var a={linedata:'${t.linedata}',pkid:'${t.PKID}'}
		linearray.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
		</c:forEach>
        </script> -->
        <!-- 引入echarts -->
        <script src="${basepath}static/js/myjs/echarts.min.js"></script>
        <!-- echaerts主题 -->
        <script src="${basepath}static/js/myjs/echartstheme.js"></script>
       <%--   <meta charset="UTF-8">
		<!--使得ie内核使用更高的渲染展示效果-->
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<!--设置支持移动端的视窗-->
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<title>首页</title>
		<!--Bootstrap-->
		<link href="${basepath}static/gxjf/bs/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
		<!--Fa字体样式-->
		<link href="${basepath}static/gxjf/css/framework-fontsy.css" rel="stylesheet"/>
		<link rel="stylesheet" href="${basepath}static/gxjf/css/sharesy.css" />
		<style type="text/css">
			html,body{
				background:#edf0f5;
				height: 100% !important;
			}
		</style> --%>
		
	</head>
	<body style="background: #ddd;">
		<div class="md-bg">
 			<%-- <div  class="md-picture"><img src="${basepath}static/gxjf/images/zhigong_sy/bg.png" width="100%"/></div> --%>
			<ul class="fl-sideBox">
				<c:forEach items="${menuList}" var="menu" >
						<c:if test="${menu.MENU_ID == '2' }">
							<li class="fl-listBox pull-left">
								<a href="javascript:void(0);"  onclick="js_method('${menu.MENU_URL}','${zzname}','${menu.MENU_NAME}')">
									<div class="fl-iconBox">
										<span class="fa fa-jpy"></span>
									</div>
									<div>${menu.MENU_NAME}</div>
								</a>
						    </li>
　　						</c:if>
						
						<c:if test="${menu.MENU_ID == '3' }">
							<li class="fl-listBox pull-left">
								<a href="javascript:void(0);"  onclick="js_method('${menu.MENU_URL}','${zzname}','${menu.MENU_NAME}')">
									<div class="fl-iconBox bg-yellow">
									<span class="fa fa-bar-chart-o"></span>
									</div>
									<div>${menu.MENU_NAME}</div>
								</a>
						    </li>
　　						</c:if>
						<c:if test="${menu.MENU_ID == '4' }">
							<li class="fl-listBox pull-left">
								<a href="javascript:void(0);"  onclick="js_method('${menu.MENU_URL}','${zzname}','${menu.MENU_NAME}')">
									<div class="fl-iconBox bg-blue">
									<span class="fa fa-file-text-o"></span>
									</div>
									<div>${menu.MENU_NAME}</div>
								</a>
						    </li>
　　						</c:if>
						<c:if test="${menu.MENU_ID == '79' }">
							<li class="fl-listBox fl-marginR pull-left">
								<a href="javascript:void(0);"  onclick="js_method('${menu.MENU_URL}','${zzname}','${menu.MENU_NAME}')">
									<div class="fl-iconBox bg-cyan">
									<span class="fa fa-flag"></span>
									</div>
									<div>${menu.MENU_NAME}</div>
								</a>
						    </li>
　　						</c:if>
						<c:if test="${menu.MENU_ID == '50' }">
							<li class="fl-listBox pull-left">
								<a href="javascript:void(0);"  onclick="js_method('${menu.MENU_URL}','${zzname}','${menu.MENU_NAME}')">
									<div class="fl-iconBox bg-orange">
									<span class="fa fa-building-o"></span>
									</div>
									<div>${menu.MENU_NAME}</div>
								</a>
						    </li>
　　						</c:if>
						<c:if test="${menu.MENU_ID == '27' }">
							<li class="fl-listBox  pull-left">
								<a href="javascript:void(0);"  onclick="js_method('${menu.MENU_URL}','${zzname}','${menu.MENU_NAME}')">
									<div class="fl-iconBox bg-green">
									<span class="fa fa-gears"></span>
									</div>
									<div>${menu.MENU_NAME}</div> 
								</a>
						    </li>
　　						</c:if>
						<c:if test="${menu.MENU_ID == '5' }">
							<li class="fl-listBox pull-left" style="margin-top:-20px;">
							 <a href="javascript:void(0);"  onclick="js_method('${menu.MENU_URL}','${zzname}','${menu.MENU_NAME}')"> 
								<div class="fl-iconBox bg-purple">
								<span class="fa fa-key"></span>
								</div>
								<div>${menu.MENU_NAME}</div>
							 </a>
						    </li>
　　						</c:if>
	             </c:forEach>
				<li class="clearfix"></li>
			</ul>
			
		</div>
	</body>
</html>

