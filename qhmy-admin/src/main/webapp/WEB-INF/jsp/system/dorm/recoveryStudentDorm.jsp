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
		<title>回收学生宿舍</title>
		<script type="text/javascript" src="${basepath}static/js/myjs/studentDormFordormPlanRecovery_ztree.js"></script>
		<style>
			.jf_ZtBox{
				position:fixed !important;
				top:108px !important;
				left:151px !important;
			}
		</style>
		<form action="" class="form-horizontal">
			<input type="hidden" id="PKID" name="PKID" value="${studentDorm.PKID }" />
		    <input type="hidden" id="PARENT_PKID" name="PARENT_PKID" value="${studentDorm.PARENT_PKID }" />
		    <input type="hidden" id="SD_LEVEL_CLICK" name="SD_LEVEL_CLICK" value="${studentDorm.SD_LEVEL_CLICK }" />
		    <input type="hidden" id="SD_LEVEL" name="SD_LEVEL" value="${studentDorm.SD_LEVEL }" />
		    <h5 style="font-weight:600;">宿舍信息</h5>
		    <div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">宿舍</label>
				<div class="col-xs-7">
					<!-- z-tree结构 -->
					<div class="form-group">
		                 <div class="zTreeDemoBackground left">
							<ul class="list" style="margin-bottom:0;">
								<li class="jf_ZtLaber title"><input class="form-control" id="citySel5" type="text" readonly value="" onclick="showMenu5();" placeholder="请选择宿舍" style="width:183px;"/>
									<div id="menuContent5" class="jf_ZtBox menuContent" style="margin-top:35px; width:300px; max-height: 300px;overflow:auto;">
										<ul id="treeDemo5" class="jf_ZtList ztree"></ul>
									</div>
								</li>
							</ul>
						 </div>
						 <div style="display:none;">
					        <!--用来存储 树节点的id -->
						    <input type="text" name="tree" id="orgtree5" class="form-control"/>
					     </div>
					</div>
				</div>
				<%-- <label for="" class="col-xs-3 control-label "><img src="${basepath}static/images/gantanhao.png"/></label>
				<div class="col-xs-7" id="recoveryRemax">
					回收床位共 0 张，其中 0 张床位已被使用
				</div> --%>
				
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
		
