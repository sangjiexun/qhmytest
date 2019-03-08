<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("basepath", basePath);
	
%>
	    <!-- ztree所需css以及js -->
	      <link rel="stylesheet" href="${basepath}static/css/share.css">
	    <link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css">
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script>
	    <!--end ztree所需css以及js -->
	     <script type="text/javascript">
	
	var zNodes=new Array();
	<c:forEach items="${zuzhilist}" var="t">  
		var a={id:'${t.DEPARTMENT_ID}',pId:'${t.PARENT_ID}',name:'${t.NAME}'};
		zNodes.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
	</c:forEach>
	
		//在学状态
		var zaixuezt=new Array();

	<c:forEach items="${zxztlist}" var="t">  
		var a={BIANMA:'${t.BIANMA}',NAME:'${t.NAME}'};
		zaixuezt.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
	</c:forEach>
	</script>
<!-- 	     <script type="text/javascript" src="${basepath}static/js/myjs/stu_ztree.js"></script> -->
	     <input id ="SESSION_MENU_BUTTONS" value="${SESSION_MENU_BUTTONS }" style="display:none;">
	     <script src="${basepath}static/js/myjs/echarts.min.js"></script>
		<script type="text/javascript" src="${basepath}static/js/myjs/studorm_ztree.js"></script>
		<script type="text/javascript" src="${basepath}static/js/myjs/studentDormMonitor.js"></script>
	   
		<style type="text/css">
			html {overflow-x:hidden;}
			.jf_ZtBox{
				left:0 !important;
				width:300px;
			}
			.col-xs-2{
				width:160px !important;
				float:left;
			}
		</style>
	
	<body>
			<div class="form-inline" style="margin-bottom: 15px;">
					
				<select name="" class="form-control jf_FltConditions" id="grade">
                   <c:forEach items="${gradelist}" var="grade">
                        <option value="${grade.DICTIONARIES_ID }" bianma="${grade.BIANMA }">${grade.NAME }</option>
                   </c:forEach>
		 </select>	
		 <!-- z-tree结构 -->
			<div class="form-group">
                 <div class="zTreeDemoBackground left">
					<ul class="list" style="margin-bottom:0;">
						<li class="jf_ZtLaber title"><input class="form-control" id="citySel" placeholder="院校专业" type="text" readonly value="" style="width:183px;" onclick="showMenu();" />
							<div id="menuContent" class="jf_ZtBox menuContent">
								<ul id="treeDemo" class="jf_ZtList ztree"></ul>
							</div>
						</li>
					</ul>
				 </div>
				 <div style="display:none;">
			        <!--用来存储 树节点的id -->
				    <input type="text" name="tree" id="orgtree" class="form-control"/>
			     </div>
			</div>
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_search"><span class="fa fa-search"></span> 查询</a>
		 </div>
			<div id='xydiv'>
			
			<!-- <div class="panel-heading">
				<div class="pull-left panel-title">传媒广告与艺术设计</div>
				<div class="clearfix"></div>
			</div>
			<div class="panel-body">
				<div class="sg_man">
					<span>男</span>
					<div class="container-fluid">
						<div class="row">
							<div class="col-xs-6">
								<img src="shanXing.png" alt="" width="100%"/>
							</div>
							<div class="col-xs-6">
								<img src="shanXing.png" alt="" width="100%"/>
							</div>
						</div>
					</div>
				</div>
				<div class="sg_woman">
					<span>女</span>
					<div class="container-fluid">
						<div class="row">
							<div class="col-xs-6">
								<img src="shanXing.png" alt="" width="100%"/>
							</div>
							<div class="col-xs-6">
								<img src="shanXing.png" alt="" width="100%"/>
							</div>
						</div>
					</div>
				</div>
			</div> -->
					
					</div>
					
			<!-- 			<div class="panel panel-info">
			
			<div class="panel-heading">
				<div class="pull-left panel-title">传媒广告与艺术设计</div>
				<div class="clearfix"></div>
			</div>
			<div class="panel-body">
				<div class="sg_man">
					<span>男</span>
					<div class="container-fluid">
						<div class="row">
							<div class="col-xs-6">
								<img src="shanXing.png" alt="" width="100%"/>
							</div>
							<div class="col-xs-6">
								<img src="shanXing.png" alt="" width="100%"/>
							</div>
						</div>
					</div>
				</div>
				<div class="sg_woman">
					<span>女</span>
					<div class="container-fluid">
						<div class="row">
							<div class="col-xs-6">
								<img src="shanXing.png" alt="" width="100%"/>
							</div>
							<div class="col-xs-6">
								<img src="shanXing.png" alt="" width="100%"/>
							</div>
						</div>
					</div>
				</div>
			</div>
					
					</div> -->
					<div id="ecid"></div>
					<div class="clearfix"></div>
					
					
	</body>
	<script type="text/javascript">
         var _basepath = "${basepath}";
    </script>
	<script  type="text/javascript">
		
	</script>
</html>

