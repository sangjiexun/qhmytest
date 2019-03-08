<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="${basepath}static/ace/css/chosen.css" />
<script type="text/javascript" src="static/js/jquery.min.js"></script>
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<script type="text/javascript">
jQuery(document).ready(function($){
	var  bs=$("#ISLABOUR").val();
	if("y"==bs){
		$("#usermessage").css("display","block");
	}else{
		$("#usermessage").css("display","none");
	}

});

</script>
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12" >
								<form action="user/${msg }.do" name="userForm" id="userForm" method="post">
									<input type="hidden" name="ID" id="id" value="${pd.ID }"/>
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<tr>
											<td style="text-align: center;" width="80px">所属部门:</td>
											<td id="bumen">
											<c:forEach items="${pd1}" var="deparment">
											<c:if test="${deparment.DEPARTMENT_ID == pd.DEPARTMENT_ID }">
											<input type="text" name="DEPARTMENT_ID" id="DEPARTMENT_ID" value="${deparment.NAME}" maxlength="32"  title="所属部门" style="width:98%;" readonly="readonly"/>
											<input type="hidden" name="DEP_ID" id="DEP_ID" value="${deparment.DEPARTMENT_ID }"/>
											</c:if>
											</c:forEach>
											</td>
											<c:if test="${fx != 'head'}">
											<td style="text-align: right;padding-top: 13px;">角色:</td>
											<td id="juese">
											<select class="chosen-select form-control" name="ROLE_ID" id="role_id" data-placeholder="请选择角色" style="vertical-align:top;" style="width:98%;" >
											<option value=""></option>
											<c:forEach items="${roleList}" var="role">
												<option value="${role.ROLE_ID }" <c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>
											</c:forEach>
											</select>
											</td>
											</c:if>
											<c:if test="${fx == 'head'}">
											<input name="ROLE_ID" id="role_id" value="${pd.ROLE_ID }" type="hidden" />
											</c:if>
											<td style="vertical-align:middle;padding-top: 13px;"  rowspan="3">扫描:</td>
											
											<td rowspan="2">
											<div style="text-align: center;">
											<img alt="" src="static/images/default.png" width="80" height="100"/>
											</div>
											</td>
											
											
										
										</tr>
										
										<input type="hidden" name="DEPARTMENT_ID" id="DEPARTMENT_ID" value="${pd.DEPARTMENT_ID }"/>
										
										
										
										<tr>
											<td style="text-align: right;padding-top: 13px;">密码:</td>
											<td><input type="password" name="PASSWORD" id="password"  maxlength="32" placeholder="输入密码" title="密码" style="width:98%;"/></td>
											<td style="text-align: right;padding-top: 13px;">确认密码:</td>
											<td><input type="password" name="chkpwd" id="chkpwd"  maxlength="32" placeholder="确认密码" title="确认密码" style="width:98%;"/></td>
											
											
											
										</tr>
										
										
										<tr>
											<td style="vertical-align:middle;padding-top: 13px;"  >用户名:</td>
											<td>
											<input type="text" name="USERNAME" id="loginname" value="${pd.USERNAME }" onblur="hasU('${pd.USERNAME }')" maxlength="32" placeholder="这里输入用户名" title="用户名" style="width:98%;"/>
											</td>
											<td style="text-align: right;padding-top: 13px;">身份证号:</td>
											<td><input type="text" name="USER_IDCARD" id="USER_IDCARD"  value="${pd.USER_IDCARD }"  maxlength="32" placeholder="这里输入身份证号" title="身份证号"    style="width:98%;"/></td>
											
											<td rowspan="1" style="vertical-align:middle; text-align: center;"><button>点击扫描</button></td>
											
											
										</tr>
										
										
 										
 										<%-- <tr >
 										<td colspan="2">
 										<div id="usermessage" style="padding-top: 13px;">
 										<table  id="table_report1" class="table table-striped table-bordered table-hover" style="padding: 0px">
 										  <tr>
											<td style="text-align: right;padding-top: 13px;">状态：</td>
											<td>
											<select name ="STATUS1" id = "STATUS1"    style="width:98%;">
											<option value = "" >请选择...</option>
											<option value = "0" >0</option>
											<option value ="1" >1</option>
											</select>
											</td>
										  </tr>
										  <tr>
											<td style="text-align: right;padding-top: 13px;">手机号:</td>
											<td><input type="number" name="PHONE" id="PHONE"  value="${pd.PHONE }"  maxlength="32" placeholder="这里输入手机号" title="手机号" style="width:98%;"/></td>
										</tr>
 										</table>
 										</div>
 										</td>
										</tr>
 									 --%>
 
 
 
 
										<tr>
											<td style="text-align: right;padding-top: 13px;">状态码：</td>
											<td><select name ="STATUS" id = "STATUS"  maxlength="32"  style="width:98%;"><option value = "" >请选择...</option><option value = "0" <c:if test="${'0' == pd.STATUS }">selected</c:if>>0</option><option value ="1" <c:if test="${'1' == pd.STATUS }">selected</c:if>>1</option></select></td>
											
											<td style="text-align: right;padding-top: 13px;">手机号:</td>
											<td><input type="number" name="PHONE" id="PHONE"  value="${pd.PHONE }"  maxlength="32" placeholder="这里输入手机号" title="手机号" style="width:98%;"/></td>
											
											<td style="text-align: right;padding-top: 13px;">邮箱:</td>
											<td><input type="email" name="EMAIL" id="EMAIL"  value="${pd.EMAIL }" maxlength="32" placeholder="这里输入邮箱" title="邮箱" onblur="hasE('${pd.USERNAME }')" style="width:98%;"/></td>
											
											
										</tr>
										
										
										<tr>
										<td style="text-align: right;padding-top: 13px;"  maxlength="32"  style="width:98%;">是否为劳务人员：</td>
											<td>
											<select class="chosen-select form-control" name="ISLABOUR" id="ISLABOUR" data-placeholder="请选择角色" style="vertical-align:top;" style="width:98%;"  >
											<option value="n">否</option>
											<option value="y">是</option>
											</select>
											</td>
											<td style="text-align: right;padding-top: 13px;">姓名:</td>
											<td><input type="text" name="NAME" id="name"  value="${pd.NAME }"  maxlength="32" placeholder="这里输入姓名" title="姓名" style="width:98%;"/></td>
											<td style="text-align: right;padding-top: 13px;">备注:</td>
											<td><input type="text" name="BZ" id="BZ"value="${pd.BZ }" placeholder="这里输入备注" maxlength="64" title="备注" style="width:98%;"/></td>
										</tr>
										<tr>
										<td style="text-align: right;padding-top: 13px;">编号:</td>
											<td><input type="text" name="NUMBER" id="NUMBER" value="${pd.NUMBER }" maxlength="32" placeholder="这里输入编号" title="编号" onblur="hasN('${pd.USERNAME }')" style="width:98%;"/></td>
										</tr>
										
										<tr >
 										<td colspan="100">
 										<div id="usermessage" style="padding-top: 13px;">
 										<table  id="table_report1" class="table table-striped table-bordered table-hover">
 										  <tr>
											<td style="text-align: right;padding-top: 13px;">学历：</td>
											<td>
											<select name ="USER_XUELI" id = "USER_XUELI"    style="width:98%;">
											<option value = "博士" >博士</option>
											<option value ="硕士" >硕士</option>
											<option value ="研究生" >研究生</option>
											<option value ="本科" >本科</option>
											<option value ="专科" >专科</option>
											<option value ="中专" >中专</option>
											<option value ="高中" >高中</option>
											<option value ="初中" >初中</option>
											<option value ="小学" >小学</option>
											</select>
											</td>
											<td style="text-align: right;padding-top: 13px;">邮政编码:</td>
											<td><input type="text" name="USER_POST" id="USER_POST"  value="${pd.USER_POST }"  maxlength="32" placeholder="这里输入邮政编码" title="邮政编码" style="width:98%;"/></td>
											
											
											<td style="vertical-align:middle;padding-top: 13px;text-align: right;"  rowspan="2">照片正面:</td>
											
											<td rowspan="2">
											<div style="text-align: center;">
											<img alt="" src="static/images/default.png" width="80" height="100"/>
											</div>
											</td>
											
											
											
											
										  </tr>
										  
										  <tr>
										  	<td style="text-align: right;padding-top: 13px;">出生日期:</td>
											<td><input type="text" name="USER_BIRTHDAY" id="USER_BIRTHDAY"  value="${pd.USER_BIRTHDAY }"  maxlength="32" placeholder="这里输入出生日期" title="出生日期" style="width:98%;"/></td>
											
											<td style="text-align: right;padding-top: 13px;">最新住址:</td>
											<td><input type="text" name="NEWADDRESS" id="NEWADDRESS"  value="${pd.NEWADDRESS }"  maxlength="32" placeholder="这里输入最新住址" title="最新住址" style="width:98%;"/></td>
										  
										 
										  </tr>
										  <tr>
										  	<td style="text-align: right;padding-top: 13px;">联系电话:</td>
											<td><input type="text" name="TEL" id="TEL"  value="${pd.TEL }"  maxlength="32" placeholder="这里输入电话" title="电话" style="width:98%;"/></td>
											
											<td style="text-align: right;padding-top: 13px;">家庭住址:</td>
											<td><input type="text" name="QT_ADDRESS" id="QT_ADDRESS"  value="${pd.QT_ADDRESS }"  maxlength="32" placeholder="这里输入家庭住址" title="家庭住址" style="width:98%;"/></td>
											
											<td style="vertical-align:middle;padding-top: 13px;text-align: right;"  rowspan="2">照片背面:</td>
											
											<td rowspan="2">
											<div style="text-align: center;">
											<img alt="" src="static/images/default.png" width="80" height="100"/>
											</div>
											</td>
										  	
										  
										  </tr>
										  
										  <tr>
										  	
										  	<td style="text-align: right;padding-top: 13px;">证件名称:</td>
											<td><input type="text" name="LW_ZJMC" id="LW_ZJMC"  value="${pd.LW_ZJMC }"  maxlength="32" placeholder="这里输入证件名称" title="证件名称" style="width:98%;"/></td>
											
											<td style="text-align: right;padding-top: 13px;">证件类型:</td>
											<td><input type="text" name="LW_ZJLX" id="LW_ZJLX"  value="${pd.LW_ZJLX }"  maxlength="32" placeholder="这里输入证件类型" title="证件类型" style="width:98%;"/></td>
										  
										  </tr>
										  
										  <tr>
										 <td style="text-align: right;padding-top: 13px;">证件编号:</td>
										 <td><input type="text" name="LW_ZJBH" id="LW_ZJBH"  value="${pd.LW_ZJBH }"  maxlength="32" placeholder="这里输入证件编号" title="证件编号" style="width:98%;"/></td>
										  <td style="text-align: right;padding-top: 13px;">是否黑名单:</td>
											<td>
											<select name ="BLACKLIST" id = "BLACKLIST"    style="width:98%;">
											<option value = "否" >否</option>
											<option value ="是" >是</option>
											</select>
											</td>
										  	 <td style="text-align: right;padding-top: 13px;">地址:</td>
											<td><input type="text" name="USER_ADDRESS" id="USER_ADDRESS"  value="${pd.USER_ADDRESS }"  maxlength="32" placeholder="这里输入地址" title="地址" style="width:98%;"/></td>
										  </tr>
										  <tr>
										  	
										   <td style="text-align: right;padding-top: 13px;">性别:</td>
											<td>
											<select name ="USER_SEX" id = "USER_SEX"    style="width:98%;">
											<option value = "男" >男</option>
											<option value ="女" >女</option>
											</select>
											</td>
											
											<td style="text-align: right;padding-top: 13px;">民族:</td>
											<td>
											<select name ="USER_NATION" id = "USER_NATION"    style="width:98%;">
											<option value = "汉" >汉</option>
											<option value ="藏" >藏</option>
											</select>
											</td>
											
											<td style="text-align: right;padding-top: 13px;">籍贯:</td>
											<td><input type="text" name="USER_NATIVE" id="USER_NATIVE"  value="${pd.USER_NATIVE }"  maxlength="32" placeholder="这里输入籍贯" title="籍贯" style="width:98%;"/></td>
											
										  </tr>
 										</table>
 										</div>
 										</td>
										</tr>
										<tr>
											<td style="text-align: center;" colspan="10">
												<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
												<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
											</td>
										</tr>
									</table>
									</div>
									<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
								</form>
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
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>
<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function(){
		/* if($("#user_id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		} */
		
		$("#ISLABOUR").bind("change", function(){
			var  bs=$("#ISLABOUR").val();
			if("y"==bs){
				$("#usermessage").css("display","block");
			}else{
				$("#usermessage").css("display","none");
			}
			
			});
		
		
		
		
		
	});
	//保存
	function save(){
		if($("#role_id").val()==""){
			$("#juese").tips({
				side:3,
	            msg:'选择角色',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#role_id").focus();
			return false;
		}
		if($("#loginname").val()=="" || $("#loginname").val()=="此用户名已存在!"){
			$("#loginname").tips({
				side:3,
	            msg:'输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#loginname").focus();
			$("#loginname").val('');
			$("#loginname").css("background-color","white");
			return false;
		}else{
			$("#loginname").val(jQuery.trim($('#loginname').val()));
		}
		
		if($("#NUMBER").val()==""){
			$("#NUMBER").tips({
				side:3,
	            msg:'输入编号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#NUMBER").focus();
			return false;
		}else{
			$("#NUMBER").val($.trim($("#NUMBER").val()));
		}
		if($("#user_id").val()=="" && $("#password").val()==""){
			$("#password").tips({
				side:3,
	            msg:'输入密码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#password").focus();
			return false;
		}
		if($("#password").val()!=$("#chkpwd").val()){
			
			$("#chkpwd").tips({
				side:3,
	            msg:'两次密码不相同',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#chkpwd").focus();
			return false;
		}
		if($("#name").val()==""){
			$("#name").tips({
				side:3,
	            msg:'输入姓名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#name").focus();
			return false;
		}
		var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
		if($("#PHONE").val()==""){
			
			$("#PHONE").tips({
				side:3,
	            msg:'输入手机号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#PHONE").focus();
			return false;
		}else if($("#PHONE").val().length != 11 && !myreg.test($("#PHONE").val())){
			$("#PHONE").tips({
				side:3,
	            msg:'手机号格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#PHONE").focus();
			return false;
		}
		if($("#EMAIL").val()==""){
			
			$("#EMAIL").tips({
				side:3,
	            msg:'输入邮箱',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}else if(!ismail($("#EMAIL").val())){
			$("#EMAIL").tips({
				side:3,
	            msg:'邮箱格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}
		
		
if($("#USER_IDCARD").val()==""){
			
			$("#USER_IDCARD").tips({
				side:3,
	            msg:'输入身份证号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#USER_IDCARD").focus();
			return false;
		}else if(!isUserIdCard($("#USER_IDCARD").val())){
			$("#USER_IDCARD").tips({
				side:3,
	            msg:'身份证号格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#USER_IDCARD").focus();
			return false;
		}
		
var ISLABOUR = $.trim($("#ISLABOUR").val());
var USER_IDCARD = $.trim($("#USER_IDCARD").val());
var DEP_ID = $.trim($("#DEP_ID").val());
if('n'==ISLABOUR){
	$.ajax({
		type: "POST",
		url: '<%=basePath%>user/USER_IDCARD.do',
    	data: {USER_IDCARD:USER_IDCARD,USERNAME:USERNAME,tm:new Date().getTime()},
		dataType:'json',
		cache: false,
		success: function(data){
			 if("success" != data.result){
				 $("#USER_IDCARD").tips({
						side:3,
			            msg:'身份证号 '+USER_IDCARD+' 已存在',
			            bg:'#AE81FF',
			            time:3
			        });
				 $("#USER_IDCARD").val('');
			 }
		}
	});
}else{
	
	$.ajax({
		type: "POST",
		url: '<%=basePath%>user/LW_IDCARD.do',
    	data: {USER_IDCARD:USER_IDCARD,USERNAME:USERNAME,tm:new Date().getTime(),DEP_ID:DEP_ID},
		dataType:'json',
		cache: false,
		success: function(data){
			 if("success" != data.result){
				 $("#USER_IDCARD").tips({
						side:3,
			            msg:'该公司下已存在身份证号为'+USER_IDCARD+'的用户',
			            bg:'#AE81FF',
			            time:3
			        });
				 $("#USER_IDCARD").val('');
			 }
		}
	});
	
}
		
		
		
		
		if($("#user_id").val()==""){
			hasU();
		}else{
			$("#userForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}
	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
		}
	
	 
	
	function isUserIdCard(UserIdCard){
		return(new RegExp(/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/).test(UserIdCard));
	}
	
	
	
	
	//判断用户名是否存在
	function hasU(){
		
		var USERNAME = $.trim($("#loginname").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasU.do',
	    	data: {USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#userForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					 $("#loginname").tips({
							side:3,
				            msg:'用户名 '+USERNAME+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#loginname").val('');
				 }
			}
		});
	}
	
	//判断邮箱是否存在
	function hasE(USERNAME){
		var EMAIL = $.trim($("#EMAIL").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasE.do',
	    	data: {EMAIL:EMAIL,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#EMAIL").tips({
							side:3,
				            msg:'邮箱 '+EMAIL+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#EMAIL").val('');
				 }
			}
		});
	}
	
	//判断编码是否存在
	function hasN(USERNAME){
		var NUMBER = $.trim($("#NUMBER").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasN.do',
	    	data: {NUMBER:NUMBER,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#NUMBER").tips({
							side:3,
				            msg:'编号 '+NUMBER+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#NUMBER").val('');
				 }
			}
		});
	}
	
	
	//判断身份证是否存在
	function hasUCID(USERNAME){
		var ISLABOUR = $.trim($("#ISLABOUR").val());
		var USER_IDCARD = $.trim($("#USER_IDCARD").val());
		var DEP_ID = $.trim($("#DEP_ID").val());
		if('n'==ISLABOUR){
			$.ajax({
				type: "POST",
				url: '<%=basePath%>user/USER_IDCARD.do',
		    	data: {USER_IDCARD:USER_IDCARD,USERNAME:USERNAME,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("success" != data.result){
						 $("#USER_IDCARD").tips({
								side:3,
					            msg:'身份证号 '+USER_IDCARD+' 已存在',
					            bg:'#AE81FF',
					            time:3
					        });
						 $("#USER_IDCARD").val('');
					 }
				}
			});
		}else{
			
			$.ajax({
				type: "POST",
				url: '<%=basePath%>user/LW_IDCARD.do',
		    	data: {USER_IDCARD:USER_IDCARD,USERNAME:USERNAME,tm:new Date().getTime(),DEP_ID:DEP_ID},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("success" != data.result){
						 $("#USER_IDCARD").tips({
								side:3,
					            msg:'该公司下已存在身份证号为'+USER_IDCARD+'的用户',
					            bg:'#AE81FF',
					            time:3
					        });
						 $("#USER_IDCARD").val('');
					 }
				}
			});
			
		}
		
	}
	
	
	
	
	$(function() {
		//下拉框
		if(!ace.vars['touch']) {
			$('.chosen-select').chosen({allow_single_deselect:true}); 
			$(window)
			.off('resize.chosen')
			.on('resize.chosen', function() {
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			}).trigger('resize.chosen');
			$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
				if(event_name != 'sidebar_collapsed') return;
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			});
			$('#chosen-multiple-style .btn').on('click', function(e){
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
				 else $('#form-field-select-4').removeClass('tag-input-style');
			});
		}
	});
	
	
	<%-- //获取部门下的角色
	function roles(id){
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/getrights.do',
	    	data: {DEPARTMENT_ID:id,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
				 }else{
					if(data.pd11.length == 0){
						alert("当前部门下没有角色");
					}else{
						var data = eval("(" + data.pd11 + ")"); 
						for(var i = 0; i <data.length;i++){
							var deptid = data[i].ROLE_ID;
							var deptName = data[i].ROLE_NAME;
							$("#role_id").append("<option value = '" + deptid + "'>" + deptName+ "</option>" );
						}
					}
				 }
			}
		});
		
	}; --%>
	
	
	
	
	
	
	
	
	
</script>
</html>