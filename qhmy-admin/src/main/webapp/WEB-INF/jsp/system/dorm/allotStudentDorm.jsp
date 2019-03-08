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
		<title>分配学生宿舍</title>
		<script type="text/javascript" src="${basepath}static/js/myjs/studentDormFordormPlanAllot_ztree.js"></script>
		<style>
			 .jf_content3 .jf_ZtBox{
				position:fixed !important;
				top:143px !important;
				left:151px !important;
			}
			.jf_content4  .jf_ZtBox{
				position:fixed !important;
				top:167px !important;
				left:151px !important;
			}
			.multiselect-container{
				height:160px;
				overflow-y:auto;
			}
			.btn{
			    height:34px !important;
				text-align:left !important;
				padding-right:7px !important;				
			}
			.caret{
			    float:right;
			    margin-top: 6px;
			    border-top: 6px dashed !important;
			    border-right: 3.5px solid transparent !important;
			    border-left: 2.5px solid transparent !important;
			}
			.modal-content{
				min-height:420px !important;
			}
			.modal-body{
				min-height:300px !important;
			} 
			.multiselect-item .input-group .form-control{
				width:198px !important;
			}
		</style>
		<form action="" class="form-horizontal">
			<input type="hidden" id="PKID" name="PKID" value="${studentDorm.PKID }" />
		    <input type="hidden" id="PARENT_PKID" name="PARENT_PKID" value="${studentDorm.PARENT_PKID }" />
		    <input type="hidden" id="SD_LEVEL_CLICK" name="SD_LEVEL_CLICK" value="${studentDorm.SD_LEVEL_CLICK }" />
		    <input type="hidden" id="SD_LEVEL" name="SD_LEVEL" value="${studentDorm.SD_LEVEL }" />
		    <h5 style="font-weight:600;">宿舍信息</h5>
		    <div class="form-group" style="margin-bottom:0;">
				<label for="" class="col-xs-3 control-label jf_xing">宿舍</label>
				<div class="col-xs-7">
					<!-- z-tree结构 -->
					<div class="form-group">
		                 <div class="zTreeDemoBackground left">
							<ul class="list" style="margin-bottom:0;">
								<li class="jf_ZtLaber title jf_content3"><input class="form-control" id="citySel3" type="text" readonly value="" onclick="showMenu3();" placeholder="请选择宿舍"/>
									<div id="menuContent3" class="jf_ZtBox menuContent" style="margin-top:0; width:300px; max-height: 300px;overflow:auto;">
										<ul id="treeDemo3" class="jf_ZtList ztree"></ul>
									</div>
								</li>
							</ul>
						 </div>
						 <div style="display:none;">
					        <!--用来存储 树节点的id -->
						    <input type="text" name="tree" id="orgtree3" class="form-control"/>
						    <input type="text" name="tree" id="isXM3" class="form-control"/>
					     </div>
					</div>
				</div>
			</div>
			<h5 style="font-weight:600;">班型信息</h5>
			 <div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">班型</label>
				<select name="BANXING" class="form-control jf_FltConditions" id="BANXING">
							<option  value="">请选择</option>
							<c:forEach var="banxing" items="${banxingList }">
								<option value="<c:out value="${banxing.DICTIONARIES_ID}"/>"><c:out value="${banxing.NAME}"/></option>
							</c:forEach>
					</select>
					
				<label for="" class="col-xs-3 control-label">是否合作学校</label>
				<select name="SFHZXX" class="form-control jf_FltConditions" id="SFHZXX" > 
						<option value="">请选择</option>
						<option value="Y">是</option>
						<option value="N">否</option>
				</select>
					
				<label for="" class="col-xs-3 control-label jf_xing">文化课学校</label>
				<select name="WENHUAKEXUEXIAO" class="form-control jf_FltConditions" id="WENHUAKEXUEXIAO" multiple="multiple"> 
						<!-- <option value="">请选择</option> -->
						<c:forEach var="wenhuakexuexiao" items="${wenhuakexuexiaoList }">
							<option value="<c:out value="${wenhuakexuexiao.PKID}"/>"><c:out value="${wenhuakexuexiao.SCHOOLNAME}"/></option>
						</c:forEach>
				</select>
			</div>
		    
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
		
		<script type="text/javascript" src="${basepath }static/js/myjs/allotStudentDorm.js"></script>
		
