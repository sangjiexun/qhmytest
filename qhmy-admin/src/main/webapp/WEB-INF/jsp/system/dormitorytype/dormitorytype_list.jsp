<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>宿舍类型信息</title>
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
		</style>
	</head>
	<link href="${basepath}static/css/bootstrapValidator.min.css" rel="stylesheet">
	<script src="static/js/bootstrap-dialog.min.js"></script>
	<script type="text/javascript">
	var _basepath = "${basepath}";
	</script>
	<body>
	<input id ="SESSION_MENU_BUTTONS" value="${SESSION_MENU_BUTTONS }" style="display:none;">
		<div class="form-inline">
			<input type="text" class="form-control jf_FltConditions" id="keyWord" style="width:18%;" placeholder="类型名称"/>
			<a class="btn btn-danger" id="dormitorytypeQuery"><span class="fa fa-search"></span> 查询</a>
			<c:if test="${SESSION_MENU_BUTTONS.dormitorytype_xz == '1' }">
				<a href="javascript:void(0);" class="btn btn-danger" id="btn_add"><span class="fa fa-plus" ></span> 新增</a>
			</c:if>
		</div> 
		<table class="table table-striped table-hover table-border jf_table" id="dormitorytypeTable" style="text-align: center;">
		</table>
	</body> 
	<script type="text/javascript" src="${basepath}static/js/myjs/dormitorytype.js"></script>
</html>
