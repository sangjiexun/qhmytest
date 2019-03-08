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
		<title></title>
		<script type="text/javascript">
			var zNodes=new Array();
			<c:forEach items="${dormlist}" var="t">  
				var a={id:'${t.ID}',pId:'${t.PARENT_ID}',name:'${t.NAME}',type:'${t.type}'};
				zNodes.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
			</c:forEach>
			var _basepath = "${basepath}";
		</script>
		<style type="text/css">
			html {overflow-x:hidden;}
			.jf_ZtBox{
				left:0 !important;
				width:300px;
				top:107px !important;
			}
			.jf_ZtBox{
				position:fixed !important;
				left:151px !important;
			}
		</style>
		<script type="text/javascript" src="${basepath}static/js/myjs/tiaosuDorm_ztree.js"></script>
		<input id="HZXX" hidden="hidden" value="${pd.HZXX }">
		<input id="BANXING" hidden="hidden" value="${pd.BANXING }">
		<input id="RXNIANFENPKID" hidden="hidden" value="${pd.RXNIANFENPKID }">
		<input id="SD_SEX" hidden="hidden" value="${pd.SD_SEX }">
		<form action="" class="form-horizontal">
		    <div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">宿舍</label>
				<div class="col-xs-7">
					<!-- z-tree结构 -->
					<div class="form-group">
		                 <div class="zTreeDemoBackground left">
							<ul class="list" style="margin-bottom:0;">
								<li class="jf_ZtLaber title"><input class="form-control" id="citySel4" type="text" readonly value="" onclick="showMenu4();" placeholder="请选择宿舍"/>
									<div id="menuContent4" class="jf_ZtBox menuContent" style="width:300px;max-height:200px; overflow:auto;top:107px !important;">
										<ul id="treeDemo4" class="jf_ZtList ztree"></ul>
									</div>
								</li>
							</ul>
						 </div>
						 <div style="display:none;">
					        <!--用来存储 树节点的id -->
						    <input type="text" name="tree" id="orgtree4" class="form-control"/>
					     </div>
					</div>
				</div>
			</div>
		    
		</form>

		<script type="text/javascript">
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
