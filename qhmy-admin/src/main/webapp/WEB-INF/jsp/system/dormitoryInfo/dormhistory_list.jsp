<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("basepath", basePath);
	
%>
	    <!-- ztree所需css以及js -->
	    <link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css">
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script>
	    <!--end ztree所需css以及js -->
	    
	    <script type="text/javascript">
	
	var zNodes=new Array();

	var zNodes=new Array();
	<c:forEach items="${treelist}" var="t">  
		var a={id:'${t.PKID}',pId:'${t.PARENT_ID}',name:'${t.NAME}'};
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
	    <script type="text/javascript" src="${basepath}static/js/myjs/dormhistory_list.js"></script>
        <script type="text/javascript" src="${basepath}static/js/myjs/dormhistory_ztree.js"></script>
		<style type="text/css">
			html {overflow-x:hidden;}
			.jf_ZtBox{
				left:0 !important;
				width:300px;
			}
		</style>
		<div class="form-inline" style="margin-bottom: 15px;padding-right:0;">
			
           <div class="form-group">
				<div class="col-xs-7" style="padding-left:4px !important;padding-right:0 !important;">
					<!-- z-tree结构 -->
					<div class="form-group">
		                 <div class="zTreeDemoBackground left">
							<ul class="list" style="margin-bottom:0;">
								<li class="jf_ZtLaber title"><input class="form-control" id="citySel3" type="text" readonly value="" onclick="showMenu3();" placeholder="请选择宿舍"/>
									<div id="menuContent3" class="jf_ZtBox menuContent" style="width:300px;max-height:400px; overflow:auto">
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
	   		<select name="" class="form-control jf_FltConditions" id="dormtype_select">
	                 <option value="">宿舍类型</option>
	                 <c:forEach items="${dormtypelist}" var="i">
	                      <option value="${i.PKID }" >${i.DT_NAME}</option>
	                 </c:forEach>
	   		</select>
			<select name="" class="form-control jf_FltConditions" id="whkxx">
                        <option value="" bianma="">文化课学校</option>
                   <c:forEach items="${hzxxlist}" var="hzxx">
                        <option value="${hzxx.PKID }" >${hzxx.SCHOOLNAME }</option>
                   </c:forEach>
		 </select>
		 <select name="" class="form-control jf_FltConditions" id="banx">
                        <option value="" bianma="">班型</option>
                   <c:forEach items="${banxinglist}" var="bx">
                        <option value="${bx.DICTIONARIES_ID }" bianma="${bx.BIANMA }">${bx.NAME }</option>
                   </c:forEach>
		 </select>
			<select name="" class="form-control jf_FltConditions" id="xingbie">
                   <option value="">性别</option>
                   <option value="1">男</option>
                   <option value="0">女</option>
		   	</select>
		   	<select name="" class="form-control jf_FltConditions" id="banji">
                    <option value="" bianma="">班级</option>
                   <c:forEach items="${banjilist}" var="bj">
                        <option value="${bj.PKID }" >${bj.CLASS_NAME }</option>
                   </c:forEach>
		 </select>
		   	<input type="text" class="form-control jf_FltConditions sfzkzqsousuo" placeholder="身份证号/学号/姓名" id="search"/>	
			<button class="btn btn-danger sfzkzqsousuobtn" id="btn_search"><span class="fa fa-search"></span> 查询</button>
			<a href="javascript:void(0);" class="btn btn-danger" id="checkOut"><span class="fa fa-upload"></span> 导出</a>

		  </div>
		 <!--  <div class="form-inline" style="margin-bottom: 15px;"> 		
		   	<input type="text" class="form-control jf_FltConditions sfzkzqsousuo" placeholder="身份证号/学号/姓名" id="search"/>	
         </div>
	<fieldset> -->
		<table id="dorminfotable" class="table table-striped table-hover table-border jf_table" style="text-align: center;">
		</table>
	</fieldset>

	<div class="clearfix"></div>
	<OBJECT id=SynCardOcx1 style="WIDTH: 0px; HEIGHT: 0px"
		codeBase=${basepath}static/js/myjs/SynCardOcx.CAB#version=1,0,0,1
		classid=clsid:4B3CB088-9A00-4D24-87AA-F65C58531039></OBJECT>
	<script type="text/javascript" src="${basepath}static/js/myjs/sfzsousuo.js"></script>

