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
		<title>权限管理-院校专业</title>
		
		<form action="" class="form-horizontal">
			<input type="hidden" id="pkid" name="pkid" value="${department.PKID }" />
		    <input type="hidden" id="parent_pkid" name="parent_pkid" value="${pd.pkid }" />
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">院校专业名称</label>
				<div class="col-xs-7">
					<input name="department_name" type="text" class="form-control" value="${department.DEPARTMENT_NAME}" placeholder="请输入内容" id="department_name"/>
				</div>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">院校专业编码</label>
				<div class="col-xs-7">
					<input name="BIANMA" type="text" class="form-control" value="${department.BIANMA}" placeholder="请输入内容" id="BIANMA"/>
				</div>
			</div>
			<c:choose>
				<c:when test="${department.PKID!=null && department.PKID!=''&& department.LEIBIE != 1}">
					<div class="form-group">
						<label for="" class="col-xs-3 control-label jf_xing">上级院校专业</label>
						<div class="col-xs-7">
							<input type="text" id="txt_departmentname" name="txt_departmentname" class="form-control" 
							value="${department.PARENT_NAME }"  placeholder="所属上级院校专业">  
							<div id="parendDepartmentTreeview" style="display: none;"></div>
						</div>
					</div>
				</c:when>
			</c:choose>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">院校专业类型</label>
				<div class="col-xs-7">
					<select name="" class="form-control jf_FltConditions" id="LEIBIE" <c:if test="${pd.action == 'addSchool'  or department.LEIBIE == 1}"> disabled="disabled" </c:if>>
						<c:if test="${pd.action == 'addSchool' or department.LEIBIE == 1 }">
							<option value="1" selected="selected">学校</option>
						</c:if>
						<option value="5" <c:if test="${department.LEIBIE == 5 }">selected="selected"</c:if>>其它</option>
						<option value="4" <c:if test="${department.LEIBIE == 4 }">selected="selected"</c:if>>专业</option>
						<option value="3" <c:if test="${department.LEIBIE == 3 }">selected="selected"</c:if>>系</option>
						<option value="2" <c:if test="${department.LEIBIE == 2 }">selected="selected"</c:if>>学院</option>
					</select>
				</div>
				
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label">标准代码</label>
				<div class="col-xs-7">
					<input name="STANDARDCODE" type="text" class="form-control" value="${department.STANDARDCODE}" placeholder="请输入内容" id="STANDARDCODE"/>					
				</div>
			</div>
			<c:if test="${COLLEGES_NAME_EN=='CAIJING'}">				
				<div class="form-group">
					<label class="col-xs-3 control-label" for="">学生类型</label>
					<div class="col-xs-7">
						<c:forEach items="${schoolrollList}" var="var"> 
							<input type="checkbox" name="checkbox"  value="${var.DICTIONARIES_ID}"/> ${var.NAME}&nbsp;
						</c:forEach>
					</div>												
				</div>												
			</c:if>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label ">是否启用</label>
				<div class="col-xs-7">
					<input type="checkbox" class="al-toggle-button" 
					
					<c:if test="${department.JIHUOZT=='Y' ||department.JIHUOZT==null ||department.JIHUOZT=='' }">checked="checked" </c:if>
					 id="IS_JIHUOZT">
				</div>
			</div>
			<%-- 用来判断是财经还是非财经 --%>
			<input type="hidden"  value="${COLLEGES_NAME_EN}" id="COLLEGES_NAME_EN"/>		
			<div class="form-group">
				<label for="" class="col-xs-3 control-label">备注</label>
				<div class="col-xs-7">
					<textarea class="form-control" name="remark" id="remark" rows="5" cols="50" style="max-width:318px;min-width:318px;">${department.REMARK}</textarea>
				</div>
			</div>
		</form>

		<script type="text/javascript">
			var _basepath = "${basepath}";
			var stu_type=new Array();
			<c:forEach items="${xslxList}" var="t">  
				var a={type:'${t}'};
				stu_type.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
			</c:forEach>
		</script>
		
		<script type="text/javascript" src="${basepath }static/js/myjs/student-department-add.js"></script>
		
