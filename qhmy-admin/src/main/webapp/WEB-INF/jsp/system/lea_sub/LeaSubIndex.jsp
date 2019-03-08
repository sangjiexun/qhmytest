<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>美院</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		<link href="${basepath}static/css/bootstrapValidator.min.css" rel="stylesheet">
	<script src="static/js/bootstrap-dialog.min.js"></script>
		    <!--  table横纵向滚动条 -->
	   <link rel="stylesheet" href="${basepath}static/css/bootstrap-table.css">

  </head>
  
  <body>
   
   <div class="form-inline">
			<input type="text" class="form-control jf_FltConditions" id="keyWord" style="width:18%;" placeholder="科目名称" maxlength="20"/>
			<a class="btn btn-danger" id="poorQuery"><span class="fa fa-search"></span> 查询</a>
	        <a class="btn btn-danger" id="addMeiDep"><span class="fa fa-plus"></span> 新增</a>
	        
		</div> 
   
   <table class="table table-striped table-hover table-border jf_table" id="poorTable" style="text-align: center;">
   </table>
  </body>
  
  	<script type="text/javascript" src="${basepath}static/js/myjs/leaSubIndex.js"></script>
  
</html>
