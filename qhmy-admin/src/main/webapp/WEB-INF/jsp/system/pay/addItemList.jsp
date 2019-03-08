<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("basepath", basePath);
	
%>
	    <input id ="item_pkid" value="${item_pkid}" hidden="hidden">
	    <script type="text/javascript" src="${basepath}static/js/myjs/addItemList.js"></script>
		<style type="text/css">
			html {overflow-x:hidden;}
			.jf_ZtBox{
				left:0 !important;
				width:300px;
			}
		</style>
		<script type="text/javascript">
              var _basepath = "${basepath}";            
        </script>
		<div class="form-inline" style="margin-bottom: 15px;">
			<input type="text" class="form-control jf_FltConditions sfzkzqsousuobtn" placeholder="身份证号/学号/姓名" id="search2"/>		
			<button class="btn btn-danger" id="btn_search2"><span class="fa fa-search"></span> 查询</button>
		</div>
		<table id="addListtable" class="table table-striped table-hover table-border jf_table" style="text-align: center;">
		</table>
