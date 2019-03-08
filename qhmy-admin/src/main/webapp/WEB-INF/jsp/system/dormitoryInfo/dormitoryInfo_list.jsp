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
		
		var zNodes_dorm=new Array();
		<c:forEach items="${dormlist}" var="t">  
			var a={id:'${t.ID}',pId:'${t.PARENT_ID}',name:'${t.NAME}',type:'${t.type}'};
			zNodes_dorm.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
		</c:forEach>
	</script>
	     <input id ="SESSION_MENU_BUTTONS" value="${SESSION_MENU_BUTTONS }" style="display:none;">
	    <script type="text/javascript" src="${basepath}static/js/myjs/dormitoryInfo_list.js"></script>
	    
        <script type="text/javascript" src="${basepath}static/js/myjs/dorminfo_ztree.js"></script>
        <script type="text/javascript" src="${basepath}static/js/myjs/dorminfo_dept_ztree.js"></script>
		<style type="text/css">
			html { overflow-x:hidden; }
			table{
			   width:100px;
			   table-layout:fixed;/* 只有定义了表格的布局算法为fixed，下面td的定义才能起作用。 */
			}
			td{
			   width:100%;
			   word-break:keep-all;/* 不换行 */
			   white-space:nowrap;/* 不换行 */
			   overflow:hidden;/* 内容超出宽度时隐藏超出部分的内容 */
			   text-overflow:ellipsis;/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用*/
			   -o-text-overflow:ellipsis;
			   -icab-text-overflow: ellipsis;
			   -khtml-text-overflow: ellipsis;
			   -moz-text-overflow: ellipsis;
			   -webkit-text-overflow: ellipsis;
			}
			.jf_ZtBox{
				left:0 !important;
				top:39px !important;
				width:300px;
			}
			.modal-dialog{
				width:900px !important;
			}
		</style>
		<script type="text/javascript">
              var _basepath = "${basepath}";
              
              function changeGrade(){
      			$("#banji").html("");
      			//根据院校专业、入学年份筛选班级的数据
      			var nodeId = $('#orgtree').val();
      			
      			var grade=$('#grade').val();
      			$.post(_basepath+"stuinfo/getBanJi.json",{SYS_DICTIONARIES_PKID:grade,SYS_DEPARTMENT_PKID:nodeId},function(data){
      				var banJiList=data.banJiList;
      				var opt_str="<option value=''>请选择班级...</option>";
      				for(i in banJiList ){
      					opt_str+="<option value=\'"+banJiList[i].PKID+"\'>"+banJiList[i].CLASS_NAME+"</option>";
      				}
      				$("#banji").append(opt_str)
      			});
      	
      	    }
        </script>
        
		<div class="form-inline" style="margin-bottom: 15px;padding-right:0;">
			
             <div class="form-group">
				<div class="col-xs-7" style="padding-left:4px !important;padding-right:0 !important;">
					<!-- z-tree结构 -->
					<div class="form-group">
		                 <div class="zTreeDemoBackground left">
							<ul class="list" style="margin-bottom:0;">
								<li class="jf_ZtLaber title"><input class="form-control" id="citySel3" type="text" readonly value="" onclick="showMenu3();" placeholder="请选择宿舍"/>
									<div id="menuContent3" class="jf_ZtBox menuContent" style="width:300px;max-height:400px; overflow:auto;">
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
	   		 <select name="" class="form-control jf_FltConditions" id="ssgs">
                        <option value="" bianma="">宿舍归属</option>
                   <c:forEach items="${hzxxlist}" var="hzxx">
                        <option value="${hzxx.PKID }" >${hzxx.SCHOOLNAME }</option>
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
		   		
			<!-- z-tree结构 -->
			<!-- <div class="form-group">
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
			        用来存储 树节点的id
				    <input type="text" name="tree" id="orgtree" class="form-control"/>
			     </div>
			</div> -->
			<select name="" class="form-control jf_FltConditions" id="xingbie">
                   <option value="">性别</option>
                   <option value="1">男</option>
                   <option value="0">女</option>
		   	</select>

			<!-- <a href="javascript:void(0);" class="btn btn-danger" id="btn_search"><span class="fa fa-search"></span> 查询</a> -->
			<button class="btn btn-danger sfzkzqsousuobtn" id="btn_search"><span class="fa fa-search"></span> 查询</button>
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_import_dorm"><span class="fa fa-sign-in"></span> 导入</a>
			<a href="javascript:void(0);" class="btn btn-danger" id="checkOut"><span class="fa fa-upload"></span> 导出</a>

		  </div>
		  <div class="form-inline" style="margin-bottom: 15px;"> 		
			<label class="jf_label" for=""></label>
               <select name="" class="form-control jf_FltConditions" id="zhuangtai">
                        <option value="">使用状态</option>
                        <option value="1">已占用</option>
                        <option value="0">未占用</option>
		   		</select>
		   	<input type="text" class="form-control jf_FltConditions sfzkzqsousuo" placeholder="身份证号/学号/姓名" id="search"/>	
         </div>
         <div class="form-inline" style="margin-bottom: 15px;"> 		
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_tuisu"><span class="fa fa-mail-reply"></span> 退宿</a>	
			<!-- <a href="javascript:void(0);" class="btn btn-danger" id="btn_fensu"><span class="fa fa-mail-reply"></span> 批量分宿</a>	 -->
         </div>
	<fieldset>
		<table id="dorminfotable" class="table table-striped table-hover table-border jf_table" style="text-align: center;">
		</table>
	</fieldset>

	<div class="clearfix"></div>
	<OBJECT id=SynCardOcx1 style="WIDTH: 0px; HEIGHT: 0px"
		codeBase=${basepath}static/js/myjs/SynCardOcx.CAB#version=1,0,0,1
		classid=clsid:4B3CB088-9A00-4D24-87AA-F65C58531039></OBJECT>
	<script type="text/javascript" src="${basepath}static/js/myjs/sfzsousuo.js"></script>

