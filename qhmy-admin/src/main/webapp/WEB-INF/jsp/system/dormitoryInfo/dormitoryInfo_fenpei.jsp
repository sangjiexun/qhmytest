<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("basepath", basePath);
	
%>
	    <!-- ztree所需css以及js -->
	    <%-- <link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css">
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script> --%>
	
	    <input id ="pkid" value="${pkid}" style="display:none;">
	    <input id ="sd_sex" value="${sd_sex}" style="display:none;">
	    <input id ="dept_id" value="${dept_id}" style="display:none;">
	     <input id ="WHKXUEXIAO" value="${WHKXUEXIAO}" style="display:none;">
	      <input id ="BANXING" value="${BANXING}" style="display:none;">
	    <input id ="t_student_dorm_type_pkid" value="${t_student_dorm_type_pkid}" style="display:none;">
	     <input id ="t_student_dorm_type_pkid" value="${t_student_dorm_type_pkid}" style="display:none;">
	    <script type="text/javascript" src="${basepath}static/js/myjs/dormitoryInfo_fenpei.js"></script>
	   
		
		<style type="text/css">
			html { overflow-x:hidden; }
			table{
			   width:100px;
			   table-layout:fixed;/* 只有定义了表格的布局算法为fixed，下面td的定义才能起作用。 */
			}
			td{
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
			}
			.
			.jf_ZtBox{
				left:0 !important;
				width:300px;
			}
			.modal-dialog{
				width:900px !important;
			}
		</style>
		<script type="text/javascript">
              var _basepath = "${basepath}";            
        </script>
        
		<div class="form-inline" style="margin-bottom: 15px;">
			<input type="text" class="form-control jf_FltConditions sfzkzqsousuobtn" placeholder="身份证号/学号/姓名" id="search2"/>		
			<!-- <a href="javascript:void(0);" class="btn btn-danger" id="btn_search2"><span class="fa fa-search"></span> 查询</a> -->
			<button class="btn btn-danger" id="btn_search2"><span class="fa fa-search"></span> 查询</button>
		</div>
		
		
	<fieldset>
		<table id="fenpeitable" class="table table-striped table-hover table-border jf_table" style="text-align: center;">
		</table>
	</fieldset>

	<div class="clearfix"></div>
	<%-- <script type="text/javascript" src="${basepath}static/js/myjs/sfzsousuo.js"></script>
 --%>
