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
		<title>退费汇总</title>
		<style type="text/css">
			html { overflow-x:hidden; }
		</style>
	</head>
	<link href="${basepath}static/css/bootstrapValidator.min.css" rel="stylesheet">
	   <!-- ztree所需css以及js -->
	    <link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css">
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script>
	    <!--end ztree所需css以及js -->
	     
	    <script type="text/javascript">
	
	var zNodes=new Array();

	var zNodes=new Array();
	<c:forEach items="${treelist}" var="t">  
		var a={id:'${t.PKID}',pId:'${t.PARENT_ID}',name:'${t.NAME}',type:'${t.SD_LEVEL}'};
		zNodes.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
	</c:forEach>
	
		//在学状态
		var zaixuezt=new Array();

	<c:forEach items="${zxztlist}" var="t">  
		var a={BIANMA:'${t.BIANMA}',NAME:'${t.NAME}'};
		zaixuezt.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
	</c:forEach>
		
		var zNodes_dorm=new Array();
		<c:forEach items="${dormlist}" var="t">  
			var a={id:'${t.ID}',pId:'${t.PARENT_ID}',name:'${t.NAME}',type:'${t.type}'};
			zNodes_dorm.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
		</c:forEach>
	</script>
		     <input id ="SESSION_MENU_BUTTONS" value="${SESSION_MENU_BUTTONS }" style="display:none;">
	
        <script type="text/javascript" src="${basepath}static/js/myjs/dormsummary_ztree.js"></script>
        
        
	<script src="static/js/bootstrap-dialog.min.js"></script>
	
	 
	
        
	<script type="text/javascript">
	var _basepath = "${basepath}";
	</script>
	
		
        
         
	<style type="text/css">
			html { overflow-x:hidden; }
			.sf_rowspan{
				vertical-align: middle !important;
			}
			.sf_table tr th{
				text-align: center;
			}
			.sf_table tr td{
				text-align: center;
			}
				.jf_ZtBox{
				left:0 !important;
				width:300px;
			}
		</style>
	<body>
		<form class="form-inline">
		     <div class="form-group">
				<div class="col-xs-7" style="padding-left:4px !important;padding-right:0 !important;">
					<!-- z-tree结构 -->
					<div class="form-group">
		                 <div class="zTreeDemoBackground left">
							<ul class="list" style="margin-bottom:0;">
								<li class="jf_ZtLaber title"><input class="form-control" id="citySel3" type="text" readonly value="" onclick="showMenu3();" placeholder="请选择宿舍"/>
									<div id="menuContent3" class="jf_ZtBox menuContent" style="width:200px;max-height:400px; overflow:auto">
										<ul id="treeDemo3" class="jf_ZtList ztree"></ul>
									</div>
								</li>
							</ul>
						 </div>
						 <div style="display:none;">
					        <!--用来存储 树节点的id -->
						    <input type="text" name="tree" id="orgtree3" class="form-control"/>
					     </div>
					</div>
				</div>
			</div>
		
		<input value="Y" type="hidden" id="xialakuang">
		<%-- 	<select name="" id="sushelou" class="form-control jf_FltConditions"  placeholder="入学年份">
				<option value="">宿舍楼</option>
				<c:forEach items="${loulist}" var="sy" varStatus="syt" >
					<option value="${sy.SD_NAME}">${sy.SD_NAME}</option>
				</c:forEach>
			</select> --%>
		
		
			<a class="btn btn-danger" id="checkQuery"><span class="fa fa-search"></span> 查询</a>
			<a class="btn btn-danger" id="checkOut"><span class="fa fa-upload"></span> 导出</a>
		</form> 
		<table class="table table-bordered sf_table" id="refundSumlistTable" style="margin-top:15px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
		</table>
	</body>
	<script type="text/javascript" src="${basepath}static/js/myjs/summaryStatDor.js"></script>
</html>
