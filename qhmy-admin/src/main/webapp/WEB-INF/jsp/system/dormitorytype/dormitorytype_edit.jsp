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
		<title>宿舍类型管理</title>
	    <style>
	    	.list .jf_ZtBox{
	    		left:0 !important;
	    		background:#fff;
	    	}
	    	.jf_ZhaoPian{
				margin-top:15px;
				margin-right:5px;
			} 
	    </style>
		<form action="" class="form-horizontal" >
			<input type="hidden" id="PKID" name="PKID" value="${pd_dormitorytype.PKID}" />
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">类型名称</label>
				<div class="col-xs-7">
					<input id="DT_NAME" name="DT_NAME" maxlength="50" type="text" class="form-control" value="${pd_dormitorytype.DT_NAME}" placeholder="请输入类型名称" />
				</div>
			</div>
			
			<div class="form-group" id="child">
				<c:choose>
					<c:when test="${pd_dormitorytype.IS_UPLOAD_PIC == null || pd_dormitorytype.IS_UPLOAD_PIC ==''}">
						<input id="IS_UPLOAD_PIC" hidden="hidden" value="N"/>
					</c:when>
					<c:otherwise>
						<input id="IS_UPLOAD_PIC" hidden="hidden" value="${pd_dormitorytype.IS_UPLOAD_PIC}"/>
					</c:otherwise>
				</c:choose>
				<label for="" class="col-xs-3 control-label">照片</label>
				<div class="jf_ZhaoPian">
					<input type="file" class="file" name="0" path="${pd_pic0.PIC }" id="0" style="display: none;"/>
					<img onerror="this.src='${basepath}static/images/itemPic/img.jpg'" src="${pd_pic0.PIC }" alt="" width="100%;" class="touxiang1" style="width:99px;height:133px;"/>
					<a href="javascript:void(0);" class="btn_tx1"><div class="jf_ImgUploading"><p>上传照片</p></div></a>
				</div>
				<div class="jf_ZhaoPian">
					<input type="file" class="file" name="0" path="${pd_pic1.PIC }" id="1" style="display: none;"/>
					<img onerror="this.src='${basepath}static/images/itemPic/img.jpg'" src="${pd_pic1.PIC }" alt="" width="100%;" class="touxiang2" style="width:99px;height:133px;"/>
					<a href="javascript:void(0);" class="btn_tx2"><div class="jf_ImgUploading"><p>上传照片</p></div></a>
				</div>
				<div class="jf_ZhaoPian">
					<input type="file" class="file" name="0" path="${pd_pic2.PIC }" id="2" style="display: none;"/>
					<img onerror="this.src='${basepath}static/images/itemPic/img.jpg'" src="${pd_pic2.PIC }" alt="" width="100%;" class="touxiang3" style="width:99px;height:133px;"/>
					<a href="javascript:void(0);" class="btn_tx3"><div class="jf_ImgUploading"><p>上传照片</p></div></a>
				</div>
				<div class="jf_ZhaoPian">
				    <input type="file" class="file" name="0" path="${pd_pic3.PIC }" id="3" style="display: none;"/>
					<img onerror="this.src='${basepath}static/images/itemPic/img.jpg'"  src="${pd_pic3.PIC }" alt="" width="100%;" class="touxiang4" style="width:99px;height:133px;"/>
					<a href="javascript:void(0);" class="btn_tx4"><div class="jf_ImgUploading"><p>上传照片</p></div></a>
				</div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label">文字描述</label>
				<div class="col-xs-7">
					<textarea id="DISCRIBES" name="DISCRIBES"  class="form-control" placeholder="请输入文字描述" >${pd_dormitorytype.DISCRIBES}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">限制数量</label>
				<div class="col-xs-7">
					<select name="" class="form-control jf_FltConditions" id="IS_NUM_LIMIT" <c:if test="${pd_dormitorytype.IS_NUM_LIMIT != null}"> disabled="disabled" </c:if>>
						<option <c:if test="${pd_dormitorytype.IS_NUM_LIMIT == 'Y'}"> selected="selected" </c:if>  value="Y">是</option>
						<option <c:if test="${pd_dormitorytype.IS_NUM_LIMIT == 'N'}"> selected="selected" </c:if> value="N">否</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">优先级</label>
				<div class="col-xs-7">
					<select name="" class="form-control jf_FltConditions" id="PRIORITY">
						<option <c:if test="${pd_dormitorytype.PRIORITY == '1'}"> selected="selected" </c:if> value="1">一级</option>
						<option <c:if test="${pd_dormitorytype.PRIORITY == '2'}"> selected="selected" </c:if> value="2">二级</option>
						<option <c:if test="${pd_dormitorytype.PRIORITY == '3'}"> selected="selected" </c:if> value="3">三级</option>
						<option <c:if test="${pd_dormitorytype.PRIORITY == '4'}"> selected="selected" </c:if> value="4">四级</option>
						<option <c:if test="${pd_dormitorytype.PRIORITY == '5'}"> selected="selected" </c:if> value="5">五级</option>
						<option <c:if test="${pd_dormitorytype.PRIORITY == '6'}"> selected="selected" </c:if> value="6">六级</option>
						<option <c:if test="${pd_dormitorytype.PRIORITY == '7'}"> selected="selected" </c:if> value="7">七级</option>
						<option <c:if test="${pd_dormitorytype.PRIORITY == '8'}"> selected="selected" </c:if> value="8">八级</option>
						<option <c:if test="${pd_dormitorytype.PRIORITY == '9'}"> selected="selected" </c:if> value="9">九级</option>
						<option <c:if test="${pd_dormitorytype.PRIORITY == '10'}"> selected="selected" </c:if> value="10">十级</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label ">是否启用</label>
				<div class="col-xs-7">
					<input type="checkbox" class="al-toggle-button" <c:if test="${pd_dormitorytype.IS_USE=='1' || pd_dormitorytype.IS_USE== null}">checked="checked"</c:if> id="IS_USE">
				</div>
			</div>
			<%-- <div class="form-group">
				<label for="" class="col-xs-3 control-label">备注</label>
				<div class="col-xs-7">
					<input name="REMARKS" maxlength="255" type="text" class="form-control" value="${pd_dormitorytype.REMARKS }" placeholder="请输入备注" id="REMARKS"/>
				</div>
			</div> --%>
		</form>

		<script type="text/javascript">
			var _basepath = "${basepath}";
		</script>
		<script src="static/js/myjs/ajaxfileupload.js" type="text/javascript"></script>
		<script type="text/javascript" src="${basepath}static/js/myjs/uploadPicture_dormitorytype.js"></script>
