<%@ page language="java" contentType="text/html; charset=GB2312" import="com.fh.util.Const"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
	request.setAttribute("basepath", basePath);
	request.setAttribute("STUDENT_LOGIN", Const.STUDENT_LOGIN);
	
	/*
	* 未退出状态，直接跳转到首页
	* add by ccc
	*/
	if(request.getSession().getAttribute("sessionUser") != null){
		 response.sendRedirect(basePath+"main/index"); 
	}
	
	
%>
<!DOCTYPE html>
<html lang="en" style="height:100%;background:url('${basepath}static/login/images/bg_img.jpg') no-repeat center;background-size:100% 100%;">

<head>
<title>${pd.SYSNAME}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="renderer" content="webkit|ie-comp|ie-stand">

<link rel="stylesheet" href="${basepath}static/css/labor.css" />
<link rel="stylesheet" href="${basepath}static/login/bootstrap.min.css" />
<link rel="stylesheet" href="${basepath}static/login/css/camera.css" />
<link rel="stylesheet" href="${basepath}static/login/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${basepath}static/login/matrix-login.css" />
<link href="${basepath}static/login/font-awesome.css" rel="stylesheet" />
<script type="text/javascript" src="${basepath}static/login/js/jquery-1.5.1.min.js"  charset="UTF-8"></script>
<style>
	.jf_footer{
	position:fixed;
	bottom:5%;
	left:50%;
	-webkit-transform:translate(-50%,0);
    -moz-transform:translate(-50%,0);
    -ms-transform:translate(-50%,0);
    -o-transform:translate(-50%,0);
	}
</style>
<script type="text/javascript">
          var _basepath = "${basepath}";
</script>
</head>
<body>
	<div class="jf_waibox">
		<div id="loginbox">
			<div style="margin-bottom:20px;"><img src="${basepath}static/login/logo2.png" /></div>
			<form action="" method="post" name="loginForm" id="loginForm" style="padding-top:25px;z-index:9999;">
			<div class="jf_xzcenter" >
					<%-- <div class="control-group">
						<div class="controls">
							<div class="main_input_box jf_mib" style="text-align:left;">
								<span class="add-on bg_lg">
								<i><img src="static/login/school.png" /></i>
								</span>
								<select class="jf_select" id="selectxx">
								<c:forEach items="${xxList}" var="list">
								<option value="${list.DEPARTMENT_ID}">${list.NAME}</option>
								</c:forEach>
								</select>
							</div>
						</div>
					</div> --%>
					<div class="control-group">
						<div class="controls">
							<div class="main_input_box jf_mib" style="text-align:left;">
								<span class="add-on bg_lg">
								<i><img src="${basepath}static/login/user.png" /></i>
								</span><input type="text" name="loginname" id="loginname" value="" placeholder="请输入用户名" />
							</div>
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<div class="main_input_box" style="text-align:left;">
								<span class="add-on bg_ly">
								<i><img src="${basepath}static/login/suo.png" /></i>
								</span><input type="password" name="password" id="password" placeholder="请输入密码" value=""/>
							</div>
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<div class="main_input_box" style="text-align:left;position:relative;">
								<span class="add-on bg_ly">
								<i><img src="${basepath}static/login/yan.png" /></i>
								</span>
								<input class="jf_input" type="text"  name="code" id="code" placeholder="验证码" value=""/>
								<i class="jf_yanzma"><img style="height:22px;" id="codeImg" alt="点击更换"
									title="点击更换" src="" /></i>
									<div class="jf_jizhu">
										<div style="float: left;margin-top:3px;margin-right:2px;">
											<font color="white">记住密码</font>
										</div>
										<div style="float: left;">
											<input name="form-field-checkbox" id="saveid" type="checkbox"
												 style="padding-top:0px;width:15px;height:15px;" />
										</div>
										<div class="clearfix"></div>
									</div>
							</div>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="form-actions">
					<div style="margin-top:15px;">
						<!-- <span class="pull-right" style="padding-right:3%;"><a href="javascript:login.quxiao();" class="btn btn-success">取消</a></span> -->
						<a  class="jf_btn_dengl flip-link btn btn-warning " id="to-recover">登录</a>
					</div>
					<div class="jf_a">
<!-- 						<a href="${STUDENT_LOGIN }"  id="qiehuan" val="1">切换到学生登录&nbsp;&nbsp;&nbsp;▏</a> -->
						<a id="forgetPassword" href="javascript:void(0);" >忘记密码？</a>
					</div>
				</div>
			</form>
			<!-- <div class="jf_footer controls">
				<div class="main_input_box">
					<div style="width:90px;margin:0 auto;"><img src="static/login/erweima.png" /></div>
					<font color="black"><div id="nameerr" style="margin-top:10px;">  版权所有  河北科曼信息技术有限公司</div></font>
                    <font color="black"><div id="nameerr">Copyright 2016-2030 河北科曼信息技术有限公司  All Rights Reserved</div ></font>
				</div>
			</div> -->
			<!-- <div class="jf_footer controls" style="width:550px;">
				<div class="main_input_box" style="text-align: left;">
					<div style="width:80px;float:left;margin-right:10px;"><img src="static/login/erweima.png" /></div>
					<font color="black"><div id="nameerr">电话：85201003(院办)</div ></font>
					<font color="black"><div id="nameerr">地址：石家庄学府路236号&nbsp;&nbsp;邮编：050061</div ></font>
					<font color="black"><div id="nameerr">国家信息产业部ICP备案：冀ICP备05021458-4号</div ></font>
					<font color="black"><div id="nameerr">Copyright 2008-2012 All Rights Reserved|石家庄财经职业学院 版权所有</div></font>
				</div>
			</div> -->
		</div>
	</div>
	<!-- <div id="templatemo_banner_slide" class="container_wapper">
		<div class="camera_wrap camera_emboss" id="camera_slide">
			<div data-src="static/login/images/banner_slide_011.jpg"></div>
			<div data-src="static/login/images/banner_slide_021.jpg"></div>
			<div data-src="static/login/images/banner_slide_031.jpg"></div>
		</div>
		#camera_wrap_3
	</div> -->

	
	
	<c:if test="${'1' == pd.msg}">
		<script type="text/javascript">
		$(tsMsg());
		function tsMsg(){
			alert('此用户在其它终端已经早于您登录,您暂时无法登录');
		}
		</script>
	</c:if>
	<c:if test="${'2' == pd.msg}">
		<script type="text/javascript">
			$(tsMsg());
			function tsMsg(){
				alert('您被系统管理员强制下线');
			}
		</script>
	</c:if>
	
	<script src="${basepath}static/login/js/bootstrap.min.js"  charset="UTF-8"></script>
	<script src="${basepath}static/js/jquery-1.7.2.js"  charset="UTF-8"></script>
	<script src="${basepath}static/login/js/jquery.easing.1.3.js"  charset="UTF-8"></script>
	<script src="${basepath}static/login/js/jquery.mobile.customized.min.js"  charset="UTF-8"></script>
	
	<!--  luzhen update 
	<script src="static/login/js/camera.min.js"></script>
	<script src="static/login/js/templatemo_script.js"></script>
	-->
	<script type="text/javascript" src="${basepath}static/js/jquery.tips.js"  charset="UTF-8"></script>
	<script type="text/javascript" src="${basepath}static/js/jquery.cookie.js"  charset="UTF-8"></script>
	
	<!-- 提示框 -->
	<script type="text/javascript" src="${basepath}static/js/layer/layer.js"  charset="UTF-8"></script>
	
	<!-- login js -->
	<script type="text/javascript" src="${basepath}static/js/login.js"  charset="UTF-8"></script>
	
	
</body>

</html>