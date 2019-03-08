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
		<title>缴费类型管理</title>
		
		<form action="" class="form-horizontal">
			
		    <input type="hidden" id="pkid" name="parent_pkid" value="${pd.PKID }" />
		     <input type="hidden" id="oldname" name="parent_pkid" value="${pd.PAY_STYLE_NAME }" />
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">缴费类型名称</label>
				<div class="col-xs-7">
					<input name="department_name" type="text" class="form-control" value="${pd.PAY_STYLE_NAME}" placeholder="请输入内容" id="pay_style_name"/>
				</div>
			</div>
			<c:if test="${sessionUser.COLLEGES_NAME_EN == 'GUANGDIAN' }">
				<div class="form-group">
					<label for="" class="col-xs-3 control-label jf_xing">票据类型</label>
					<div class="col-xs-7">
						<select name="" class="form-control" id="BILL_TYPE" style="width:183px;">
                                <option value="1" <c:if test="${pd.BILL_TYPE == '1'}"> selected="selected"</c:if>>收据</option>
                                <option value="2" <c:if test="${pd.BILL_TYPE == '2'}"> selected="selected"</c:if>>发票</option>
                        </select>
					</div>
				</div>
			</c:if>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">排序</label>
				<div class="col-xs-7">
					<input name="department_name" type="text" class="form-control" value="${pd.PAIXU}" placeholder="请输入内容" id="paixu"/>
				</div>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label ">是否启用</label>
					<div class="col-xs-6">
						<input type="checkbox" class="al-toggle-button" id="is_use" <c:if test="${pd.IS_USE=='Y' or pd.IS_USE==null }">checked="checked"</c:if>   >
					</div> 
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label">备注</label>
				<div class="col-xs-7">
					<textarea class="form-control" name="remark" id="remark" rows="5" cols="50" style="max-width:318px;min-width:318px;">${pd.REMARK}</textarea>
				</div>
			</div>
			
		</form>

		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
	
