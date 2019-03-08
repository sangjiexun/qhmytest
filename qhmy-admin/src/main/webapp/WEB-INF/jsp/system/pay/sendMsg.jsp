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
		<title></title>

		<style type="text/css">
			html {overflow-x:hidden;}
			.jf_ZtBox{
				left:0 !important;
				width:300px;
			}
		</style>
		<form action="" class="form-horizontal">
		    <div class="form-group">
				
				<div class="col-xs-7">
					<!-- <input type="text" id="wxmsg"/> -->微信消息<br/>
					本项目共${sendMsgCountsPd.TOTALCOUNT}人欠费，其中${sendMsgCountsPd.SENDCOUNT}人关注了微信公众号
				</div>
			</div>
		    
		</form>

