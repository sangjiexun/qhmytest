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
		<title>新增编辑学生宿舍</title>
		
		<form action="" class="form-horizontal">
			<input type="hidden" id="PKID" name="PKID" value="${studentDorm.PKID }" />
		    <input type="hidden" id="PARENT_PKID" name="PARENT_PKID" value="${studentDorm.PARENT_PKID }" />
		    <input type="hidden" id="SD_LEVEL_CLICK" name="SD_LEVEL_CLICK" value="${studentDorm.SD_LEVEL_CLICK }" />
		    <input type="hidden" id="SD_LEVEL" name="SD_LEVEL" value="${studentDorm.SD_LEVEL }" />
		    
		    <c:if test="${pd.action == 'add' }">
		    	<div class="form-group">
					<label for="" class="col-xs-3 control-label jf_xing">添加项目</label>
					<div class="col-xs-7">
						<input name="SD_LEVEL_NAME" type="text" class="form-control" value="${studentDorm.SD_LEVEL_NAME }" placeholder="请输入内容" readonly="readonly" disabled="disabled" style="background:#eee"/>
					</div>
				</div>
				
				<!-- 左侧选择的是学校（添加校区）、校区（添加宿舍楼 -->
				<c:if test="${studentDorm.SD_LEVEL_CLICK == null or studentDorm.SD_LEVEL_CLICK == '' or studentDorm.SD_LEVEL_CLICK == '1'}">
					<div class="form-group">
					<label for="" class="col-xs-3 control-label jf_xing">名称</label>
					<div class="col-xs-7">
						<input name="SD_NAME" type="text" class="form-control" value="${studentDorm.SD_NAME }" placeholder="请输入内容" id="SD_NAME"/>
					</div>
				</div>
				</c:if>
				<!-- 左侧选择的是宿舍楼（添加楼层）、楼层（添加房间） -->
				<c:if test="${studentDorm.SD_LEVEL_CLICK == '2' or studentDorm.SD_LEVEL_CLICK == '3'}">
					<div class="form-group">
						<label for="" class="col-xs-3 control-label jf_xing">添加操作</label>
						<div class="col-xs-7">
							<input type="radio" name="addType" id="addType" value="0" checked="checked">单个
							<input type="radio" name="addType" id="addType" value="1">批量
						</div>
					</div>
				</c:if>
				<!-- 左侧选择的是宿舍楼（添加楼层） -->
				<c:if test="${studentDorm.SD_LEVEL_CLICK == '2'}">
					<div class="form-group">
						<label for="" class="col-xs-3 control-label jf_xing">楼层</label>
						<div class="col-xs-7">
							<input name="SD_NAME_LOUCENG_FROM" type="text" class="form-control" style="width:85px !important;" value="" maxlength="3" placeholder="请输入数字" id="SD_NAME_LOUCENG_FROM" onkeyup="if(value.length==1){value=value.replace(/[^(\-?)\d+]/ig,'')}else{value=value.substring(0,1)+value.substring(1,value.length).replace(/[^\d+]/ig,'');}"/>
							<span id="SD_NAME_LOUCENG_TO_SPAN" style="display:none;margin: -44px 0px 0px 90px;">
								<div class="form-inline">-
								<input name="SD_NAME_LOUCENG_TO" type="text" class="form-control" style="width:85px !important;" value="" maxlength="3" placeholder="请输入数字" id="SD_NAME_LOUCENG_TO" onkeyup="if(value.length==1){value=value.replace(/[^(\-?)\d+]/ig,'')}else{value=value.substring(0,1)+value.substring(1,value.length).replace(/[^\d+]/ig,'');}"/>
								</div>
							</span>
							
						</div>
					</div>
				</c:if>
				
				<!-- 左侧选择的是楼层（添加房间） -->
				<c:if test="${studentDorm.SD_LEVEL_CLICK == '3'}">
					<div class="form-group">
						<label for="" class="col-xs-3 control-label ">前缀</label>
						<div class="col-xs-7">
							<input name="SD_NAME_QIANZHUI" type="text" class="form-control" value="" placeholder="请输入内容" id="SD_NAME_QIANZHUI"/>
						</div>
					</div>
					<div class="form-group">
						<label for="" class="col-xs-3 control-label jf_xing">序列号</label>
						<div class="col-xs-7">
							<input name="SD_NAME_XULIEHAO_FROM" type="text" class="form-control" style="width:85px !important;" value="" maxlength="4" placeholder="请输入数字" id="SD_NAME_XULIEHAO_FROM"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
							<span id="SD_NAME_XULIEHAO_TO_SPAN" style="display:none;margin: -44px 0px 0px 90px;">
								<div class="form-inline">-
								<input name="SD_NAME_XULIEHAO_TO" type="text" class="form-control" style="width:85px !important;" value="" maxlength="4" placeholder="请输入数字" id="SD_NAME_XULIEHAO_TO"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
								</div>
							</span>
							
						</div>
					</div>
					<div class="form-group">
						<label for="" class="col-xs-3 control-label jf_xing">宿舍类型</label>
						<div class="col-xs-7">
							<select name="" class="form-control jf_FltConditions" style="width:18%;" placeholder="选择宿舍类型" id="T_STUDENT_DORM_TYPE_PKID">
								<c:forEach var="dormType" items="${studentDormTypeList }">
									<option value="<c:out value="${dormType.PKID}"/>"><c:out value="${dormType.DT_NAME}"/></option>
								</c:forEach>
								
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="" class="col-xs-3 control-label jf_xing">床位数</label>
						<div class="col-xs-7">
							<input name="CHUANGCOUNT" type="text" class="form-control" value="" maxlength="2" placeholder="请输入数字" id="CHUANGCOUNT"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
						</div>
					</div>
					<div class="form-group">
						<label for="" class="col-xs-3 control-label jf_xing">性别</label>
						<div class="col-xs-7">
							<input type="radio" name="SD_SEX" id="SD_SEX" value="0" checked="checked">女
							<input type="radio" name="SD_SEX" id="SD_SEX" value="1">男
						</div>
					</div>
				</c:if>
		    </c:if>
		    <c:if test="${pd.action == 'edit' }">
		    	<!-- 左侧选择的是编辑校区 、宿舍楼-->
				<c:if test="${studentDorm.SD_LEVEL_CLICK == '1' or studentDorm.SD_LEVEL_CLICK == '2'}">
					<div class="form-group">
					<label for="" class="col-xs-3 control-label jf_xing">名称</label>
					<div class="col-xs-7">
						<input name="SD_NAME" type="text" class="form-control" value="${studentDorm.SD_NAME }" placeholder="请输入内容" id="SD_NAME"/>
					</div>
				</div>
				</c:if>
				
				<!-- 左侧选择的编辑房间 -->
				<c:if test="${studentDorm.SD_LEVEL_CLICK == '4'}">
					<div class="form-group">
						<label for="" class="col-xs-3 control-label ">前缀</label>
						<div class="col-xs-7">
							<input name="SD_NAME_QIANZHUI" type="text" class="form-control" value="${studentDorm.SD_PREFIX }" placeholder="请输入内容" id="SD_NAME_QIANZHUI"/>
						</div>
					</div>
					<div class="form-group">
						<label for="" class="col-xs-3 control-label jf_xing">名称</label>
						<div class="col-xs-7">
							<input name="SD_NAME" type="text" class="form-control" value="${studentDorm.SD_NAME }" placeholder="请输入内容" id="SD_NAME" readonly="readonly" disabled="disabled" style="background:#eee"/>
						</div>
					</div>
					<div class="form-group">
						<label for="" class="col-xs-3 control-label jf_xing">宿舍类型</label>
						<div class="col-xs-7">
							<select name="" class="form-control jf_FltConditions" style="width:18%;" placeholder="选择宿舍类型" id="T_STUDENT_DORM_TYPE_PKID" disabled="disabled">
								<c:forEach var="dormType" items="${studentDormTypeList }">
									<c:if test="${dormType.PKID == studentDorm.T_STUDENT_DORM_TYPE_PKID }">
										<option value="<c:out value="${dormType.PKID}"/>"><c:out value="${dormType.DT_NAME}"/></option>
									</c:if>
									
								</c:forEach>
								
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="" class="col-xs-3 control-label jf_xing">床位数</label>
						<div class="col-xs-7">
							<input name="CHUANGCOUNT" type="text" class="form-control" placeholder="请输入数字" value="${studentDorm.CHUANGCOUNT}"  disabled="disabled" style="background:#eee"/>
						</div>
					</div>
					<div class="form-group">
						<label for="" class="col-xs-3 control-label jf_xing">性别</label>
						<div class="col-xs-7">
							<input type="radio" name="SD_SEX" id="SD_SEX" value="0" <c:if test="${studentDorm.SD_SEX == '0'}">checked="checked"</c:if> <c:if test="${studentDorm.CHUANG_ISUSE == true}">disabled</c:if>>女
							<input type="radio" name="SD_SEX" id="SD_SEX" value="1" <c:if test="${studentDorm.SD_SEX == '1'}">checked="checked"</c:if> <c:if test="${studentDorm.CHUANG_ISUSE == true}">disabled</c:if>>男
						</div>
					</div>
				</c:if>
		    </c:if>
			
		</form>

		<script type="text/javascript">
			var _basepath = "${basepath}";
			
			function clearNoNum(obj){  
			    //修复第一个字符是小数点 的情况.  
			    if(obj.value !=''&& obj.value.substr(0,1) == '.'){  
			        obj.value="";  
			    }  
			    obj.value = obj.value.replace(/^0*(0\.|[1-9])/, '$1');//解决 粘贴不生效  
			    obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
			    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的       
			    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");      
			    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数       
			    if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额  
			        if(obj.value.substr(0,1) == '0' && obj.value.length == 2){  
			            obj.value= obj.value.substr(1,obj.value.length);      
			        }  
			    }      
			} 
		</script>
		
		<script type="text/javascript" src="${basepath }static/js/myjs/addUpdateStudentDorm.js"></script>
		
