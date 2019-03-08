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
		<title>分班</title>
		<style type="text/css">
			html { overflow-x:hidden; }
			.multiselect-container{
				max-height:350px;
				overflow-y:auto;
			}
		</style>
	</head>
	<link href="${basepath}static/css/bootstrapValidator.min.css" rel="stylesheet">
	<script src="static/js/bootstrap-dialog.min.js"></script>
	<!-- ztree所需css以及js -->
    <link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css">
    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script>
    <!--end ztree所需css以及js -->
    
    
    
    
	    
    <script type="text/javascript">
	
		var _basepath = "${basepath}";
		
	</script>
		<script type="text/javascript" src="${basepath}static/js/myjs/assignedClass.js"></script>
	
	   <style type="text/css">
			html { overflow-x:hidden; }
			#assignedClassTable{
			   table-layout:fixed;/* 只有定义了表格的布局算法为fixed，下面td的定义才能起作用。 */
			}
			.jf_table tr td{
			   width:100%;
			   word-break:keep-all;/* 不换行 */
			   white-space:nowrap;/* 不换行 */
			   overflow:hidden;/* 内容超出宽度时隐藏超出部分的内容 */
			   text-overflow:ellipsis;/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用*/
			   -o-text-overflow:ellipsis;
			   -icab-text-overflow: ellipsis;
			   -khtml-text-overflow: ellipsis;
			   -moz-text-overflow: ellipsis;
			   -webkit-text-overflow: ellipsis;
			   max-width:'150px'
			}
	    </style>
		<style type="text/css">
			html {overflow-x:hidden;}
			.jf_ZtBox{
				left:0 !important;
				width:300px;
			}
			.btn{
			    height:34px !important;
				text-align:left !important;				
			}
			/* 调整下拉框宽度 */
			.dropdown-menu {
			    min-width: 190px !important;
			}
			.caret{
			    float:right;
			    margin-top: 6px;
			    border-top: 7px dashed !important;
			    border-right: 3.5px solid transparent !important;
			    border-left: 3.5px solid transparent !important;
			}
			.page-list .btn-group .caret{
				border-top: none !important;
			}
		</style>
	<body>
		
		<div class="form-inline" style="margin-bottom:15px;">
		
		
			<select class="ppbj" id="grades" multiple="multiple">								
				<c:forEach items="${rxnf}" var="grade">
					<option   value="${grade.PKID }">${grade.NAME}</option>
				</c:forEach>
			</select>	
			
			
			<select class="ppbj" id="banxing" multiple="multiple">								
				<c:forEach items="${banXing}" var="var">
					<option  value="${var.PKID }">${var.NAME}</option>
				</c:forEach>
			</select>	
			
			<select id="banji" multiple="multiple">								
			<%-- 	<c:forEach items="${banji}" var="grade">
					<option   value="${grade.PKID }">${grade.NAME}</option>
				</c:forEach> --%>
			</select>
			
			<select id="partschool" multiple="multiple">								
				<c:forEach items="${whkSchool}" var="grade">
					<option   value="${grade.PKID }">${grade.SCHOOLNAME}</option>
				</c:forEach>
			</select>
		
		
		
			<select name="" class="form-control jf_FltConditions" id="xingbie" style="width:18%;">
				<option value="">性别</option>
				<option value="M">男</option>
				<option value="W">女</option>
			</select>
			
				<select name="" class="form-control jf_FltConditions" id="IS_FENBAN" style="width:18%;">
				<option value="">是否分班</option>
				<option value="Y">是</option>
				<option value="N">否</option>
			</select>
			
			<input type="text" class="form-control jf_FltConditions sfzkzqsousuo" id="LKFSQI" style="width:18%;" placeholder="联考分数开始"/>
			-<input type="text" class="form-control jf_FltConditions sfzkzqsousuo" id="LKFSZHI" style="width:18%;" placeholder="联考分数结束"/>
			
			
			
		
			
			<select name="" class="form-control jf_FltConditions" id="XKCJ">
                   <option value="">校考成绩</option>
                   <c:forEach items="${xkcj}" var="grade">
                       <option value="${grade.PKID }" >${grade.NAME }</option>
                  </c:forEach>
		    </select>
		    
			<input type="text" class="form-control jf_FltConditions sfzkzqsousuo" id="keywords" style="width:18%;" placeholder="身份证/学号/姓名/备注"/>
			<button class="btn btn-danger sfzkzqsousuobtn" id="assignedClassQuery"><span class="fa fa-search"></span> 查询</button>
			<a href="javascript:void(0);" class="btn btn-danger" id="checkOut"><span class="fa fa-upload"></span> 导出</a>
		    <a href="javascript:void(0);" class="btn btn-danger" id="btn_fenban"><span class="fa fa-tag" ></span>手动分班</a>
		       <a href="javascript:void(0);" class="btn btn-danger" id="btn_tiaoban"><span class="fa fa-retweet" ></span>调班</a>
			
		</div> 
		<fieldset>
		<table id="assignedClassTable" class="table table-striped table-hover table-border jf_table" style="text-align: center;">
		</table>
	</fieldset>
	<div class="clearfix"></div>


	</body>

	<OBJECT id=SynCardOcx1 style="WIDTH: 0px; HEIGHT: 0px"
		codeBase=${basepath}static/js/myjs/SynCardOcx.CAB#version=1,0,0,1
		classid=clsid:4B3CB088-9A00-4D24-87AA-F65C58531039></OBJECT>
	<script type="text/javascript" src="${basepath}static/js/myjs/sfzsousuo.js"></script>
</html>
