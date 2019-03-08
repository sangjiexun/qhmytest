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
		<style>
		.jf_ZtLaber .jf_tzTree{
			left:150px !important;
		}
		</style>
		
		<script type="text/javascript">
			var zNodes2=new Array();
			<c:forEach items="${zuzhilist}" var="t">  
				var a={id:'${t.DEPARTMENT_ID}',pId:'${t.PARENT_ID}',name:'${t.NAME}',LEIBIE:'${t.LEIBIE}'};
				zNodes2.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
			</c:forEach>
			function changeGradeDept(){
      			$("#banjidept").html("");
      			//根据院校专业、入学年份筛选班级的数据
      			var nodeId = $('#orgtreeDep').val();
      			
      			var grade=$('#gradeDep').val();
      			$.post(_basepath+"stuinfo/getBanJi.json",{SYS_DICTIONARIES_PKID:grade,SYS_DEPARTMENT_PKID:nodeId},function(data){
      				var banJiList=data.banJiList;
      				var opt_str="<option value=''>请选择班级...</option>";
      				for(i in banJiList ){
      					opt_str+="<option value=\'"+banJiList[i].PKID+"\'>"+banJiList[i].CLASS_NAME+"</option>";
      				}
      				console.log(opt_str)
      				$("#banjidept").append(opt_str)
      			});
      	
      	    }
		</script>
		<script type="text/javascript" src="${basepath}static/js/myjs/updateDep_ztree.js"></script>
		<form action="" class="form-horizontal" >
			<input type="hidden" id="PKIDS" name="PKIDS" value="${pd.pkids}" />
			<div class="form-group">
			<label for="" class="col-xs-3 control-label jf_xing">院校专业：</label>
                 <div class="ztreeDepBackground left ">
					<ul class="list" style="margin-bottom:0;">
						<li class="jf_ZtLaber title "> <input class="form-control" id="citySelDep" type="text" readonly value="${pd.ZUZHINAME }" style="width:183px;" onclick="showMenuDep();" />
							<div id="menuContentDep" class="jf_tzTree menuContent jf_ZtBox" style="display:none; position: absolute; z-index:1;">
								<ul id="treeDep" class="ztree" style="margin-top:0; width:300px; max-height: 300px;overflow:auto;"></ul>
							</div>
						</li>
					</ul>
				</div>
				<div style="display:none;">
			        <!--用来存储 树节点的id -->
				    <input type="text" name="tree" id="orgtreeDep" class="form-control" value=""/>
				    <input type="text" name="tree" id="orgtreeDepKeBie" class="form-control" value=""/>
			    </div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">入学年份：</label>
                <select name="" class="form-control" id="gradeDep" style="width:183px;">
                		 <option value="">入学年份</option>
               		  	<c:forEach items="${gradelist}" var="grade">
               				<option value="${grade.DICTIONARIES_ID }" bianma="${grade.BIANMA }">${grade.NAME }</option>
               			</c:forEach>
           		</select>
			</div>
			<%-- <div class="form-group">
				<label for="" class="col-xs-3 control-label">班级：</label>
				<select name="" class="form-control jf_FltConditions" id="banjidept" placeholder="班级">
	                   
	                   
			   	</select>
		   	</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">学生类型：</label>
				<select name="" class="form-control" id="CENGCIDEP" placeholder="请输入内容" style="width:183px;">
                	<c:forEach items="${cengci_list}" var="var">
                		<option value="${var.PKID}" >${var.CENGCI_NAME}</option>
                	</c:forEach>
                 </select>
			</div>
			<div class="form-group">
				<label for="" class="col-xs-3 control-label jf_xing">批次：</label>
                <select name="" class="form-control" id="PICIDEP" placeholder="请输入内容" style="width:183px;">
                     	<c:forEach items="${pici_list}" var="var">
                     		<option value="${var.PKID}" >${var.PICI_NAME}</option>
                     	</c:forEach>
           		</select>
			</div> --%>
		</form>

		<script type="text/javascript">
			var _basepath = "${basepath}";
			$("#gradeDep").bind("change", function() {
				changeGradeDept();
			});
		</script>
