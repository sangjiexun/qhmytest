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
		var colStr="${colStr}";
	<c:forEach items="${zxztlist}" var="t">  
		var a={BIANMA:'${t.BIANMA}',NAME:'${t.NAME}'};
		zaixuezt.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
	</c:forEach>
	</script>
	
<!-- 	     <script type="text/javascript" src="${basepath}static/js/myjs/stu_ztree.js"></script> -->
	     <input id ="SESSION_MENU_BUTTONS" value="${SESSION_MENU_BUTTONS }" style="display:none;">
	    <script type="text/javascript" src="${basepath}static/js/myjs/stuinfocaijing_yx.js"></script>
<script type="text/javascript" src="${basepath}static/js/myjs/stu_ztree.js"></script>
	    <style type="text/css">
			html { overflow-x:hidden; }
			table{
			   table-layout:fixed;/* 只有定义了表格的布局算法为fixed，下面td的定义才能起作用。 */
			}
			.jf_table tr td{
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
			   max-width:'150px'
			}
	    </style>
		<style type="text/css">
			html {overflow-x:hidden;}
			.jf_ZtBox{
				left:0 !important;
				width:300px;
			}
			.btn{
			    height:34px !important;
				text-align:left !important;				
			}
			/* 调整下拉框宽度 */
			.dropdown-menu {
			    min-width: 190px !important;
			}
			.caret{
			    float:right;
			    margin-top: 6px;
			    border-top: 7px dashed !important;
			    border-right: 3.5px solid transparent !important;
			    border-left: 3.5px solid transparent !important;
			}
			.page-list .btn-group .caret{
				border-top: none !important;
			}
		</style>
		<script type="text/javascript">
              var _basepath = "${basepath}";
              
              function changeGrade(){
      			$("#banji").html("");
      			//根据院校专业、入学年份筛选班级的数据
      			var nodeId = $('#orgtree').val();
      			var grade_str='';
      			$('#grade option:selected').each(function() {
                    grade_str+=$(this).attr('idd')+',';
                });

      			$.post(_basepath+"stuinfo/getBanJi.json",{SYS_DICTIONARIES_PKID:grade_str,SYS_DEPARTMENT_PKID:nodeId},function(data){
      				var banJiList=data.banJiList;
      				var opt_str="";
      				for(i in banJiList ){
      					opt_str+="<option value=\'"+banJiList[i].PKID+"\'>"+banJiList[i].CLASS_NAME+"</option>";
      				}
      				$("#banji").append(opt_str)
      				$('#banji').multiselect("destroy").multiselect({
      			    	 enableFiltering: true,
      			    	 includeSelectAllOption: true,
      			    	 selectAllText: ' 全选',
      			    	 nonSelectedText: '请选择班级',
      			         allSelectedText: '全部选择',
      			         selectAllJustVisible: true,
      			    	 filterPlaceholder:'请输入班级名称',
      			    	 buttonWidth: '183px'//设置字体过长显示...
      			    });
      			});
      	
      	    }
        </script>
        
		<div class="form-inline" style="margin-bottom: 15px;">
					
					
			<select id="grade" multiple="multiple">								
				<c:forEach items="${gradelist}" var="grade">
					<option idd="${grade.DICTIONARIES_ID }" value="${grade.BIANMA }">${grade.NAME}</option>
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
			<select name="" id="banji" multiple="multiple">
		   	</select>
		   		
			
            <select name=""  id="zxzt" multiple="multiple">
                   <c:forEach items="${zxztlist2}" var="zxzt">
                        <option value="${zxzt.BIANMA }" >${zxzt.NAME }</option>
                   </c:forEach>
		    </select>
		   	<input type="text" class="form-control jf_FltConditions sfzkzqsousuo" placeholder="身份证号/学号/姓名" id="search"/>	
		   	<a href="#demo" class="btn btn-danger" data-toggle="collapse" title='更多查询条件' id='more'><i id='icon' class="fa fa-angle-double-down"></i> 更多查询条件</a>
		   		</div>
		   	   <div id="demo" class="collapse">
               <form class="form-inline" id="moreQueryArea" style="margin-bottom: 15px;">
		   		
		   		<select name=""  id="cengci" multiple="multiple">                 
                   <c:forEach items="${cengci_list}" var="var">
                        <option value="${var.PKID }">${var.CENGCI_NAME}</option>
                   </c:forEach>
		   		</select>
		   		
		   		<select name="" id="pici" multiple="multiple">
                   <c:forEach items="${pici_list}" var="var">
                        <option value="${var.PKID }">${var.PICI_NAME}</option>
                   </c:forEach>
		   		</select>
		   		<select name="" class="form-control jf_FltConditions" id="IS_TONGGUO">
                   <option value="">审核状态</option>
                   <option value="1">通过</option>
                   <option value="0">待审核</option>
		   		</select>
		   		<select name=""  id="schoolroll" multiple="multiple">
		   		<option value="kong">空</option>
                   <c:forEach items="${schoolrollList}" var="var">
                        <option value="${var.DICTIONARIES_ID}">${var.NAME}</option>
                   </c:forEach>
		   		</select>
		   		<select name=""  id="planenature" multiple="multiple">
                    <option value="1">计划内</option>
                    <option value="2">计划外</option>
		   		</select>
		   		<select name=""  id="kelei" multiple="multiple">
                   <c:forEach items="${keleiList}" var="var">
                        <option value="${var.DICTIONARIES_ID}">${var.NAME}</option>
                   </c:forEach>
		   		</select>
		   		 <select name="" class="form-control jf_FltConditions" id="qrxx">
                   <option value="">是否确认信息</option>
                   <option value="Y">已确认</option>
                   <option value="N">未确认</option>
		   		</select>
		   		<select name="" class="form-control jf_FltConditions" id="qrzy">
                   <option value="">是否确认专业</option>
                   <option value="Y">已确认</option>
                   <option value="N">未确认</option>
		   		</select>
		   		<!--<select name="" class="form-control jf_FltConditions" id="stu_from">
                   <option value="">学生来源</option>
                   <option value="1">统招</option>
                   <option value="2">非统招</option>
		   		</select> -->		   		
		   		<input type="hidden" class="form-control" style="width:75px;" placeholder="学生来源" id="stu_from"/> 
		   		
		   		<select name=""  id="minzu" multiple="multiple">
                   <c:forEach items="${minzuList}" var="var">
                        <option value="${var.DICTIONARIES_ID}">${var.NAME}</option>
                   </c:forEach>
		   		</select>
		   		<select name=""  id="zzmm" multiple="multiple">
                   <c:forEach items="${zzmmList}" var="var">
                        <option value="${var.DICTIONARIES_ID}">${var.NAME}</option>
                   </c:forEach>
		   		</select>
		   		<select name=""  id="jtcygx" multiple="multiple">
                   <c:forEach items="${jtgxList}" var="var">
                        <option value="${var.DICTIONARIES_ID}">${var.NAME}</option>
                   </c:forEach>
		   		</select>
		   		<select name=""  id="hkxz" multiple="multiple">
                   <c:forEach items="${hkxzList}" var="var">
                        <option value="${var.DICTIONARIES_ID}">${var.NAME}</option>
                   </c:forEach>
		   		</select>
		   		<select name=""  id="jkzk" multiple="multiple">
                   <c:forEach items="${jkzkList}" var="var">
                        <option value="${var.DICTIONARIES_ID}">${var.NAME}</option>
                   </c:forEach>
		   		</select>
		   		<%-- <select name=""  id="schoolroll" multiple="multiple">
                   <c:forEach items="${schoolrollList}" var="var">
                        <option value="${var.DICTIONARIES_ID}">${var.NAME}</option>
                   </c:forEach>
		   		</select> --%>
		   		<select name=""  id="xslx" multiple="multiple">
                   <c:forEach items="${schoolrollList}" var="var">
                        <option value="${var.DICTIONARIES_ID}">${var.NAME}</option>
                   </c:forEach>
		   		</select>
		   		<select name=""  id="sfpk" multiple="multiple">
                   <c:forEach items="${sfpkList}" var="var">
                        <option value="${var.DICTIONARIES_ID}">${var.NAME}</option>
                   </c:forEach>
		   		</select>
		   		<select name=""  id="sxbs" multiple="multiple">
		   		<option value="kong">空</option>
                   <c:forEach items="${sxbsList}" var="var">
                        <option value="${var.DICTIONARIES_ID}">${var.NAME}</option>
                   </c:forEach>
		   		</select>
		   		<select name=""  id="xuezhi" multiple="multiple">
                   <c:forEach items="${cengci_list}" var="var">
                        <option value="${var.PKID}">${var.XUENIANZHI}</option>
                   </c:forEach>
		   		</select>
		   		<input type="text" class="form-control" style="width:75px;" placeholder="联系电话" id="SHOUJI"/> 
		   		<input type="text" class="form-control" style="width:75px;" placeholder="考生号" id="KAOSHENGHAO"/> 
		   		<input type="text" class="form-control" style="width:75px;" placeholder="邮政编码" id="YOUZHENGBIANMA"/> 
		   		<input type="text" class="form-control" style="width:75px;" placeholder="家庭成员姓名" id="JTCYXM"/> 
		   		<input type="text" class="form-control" style="width:75px;" placeholder="家庭成员联系电话" id="JTCYLXDH"/> 
		   		<input type="text" class="form-control" style="width:75px;" placeholder="家庭住址" id="JIATINGZHUZHI"/> 
		   		<input type="text" class="form-control" style="width:75px;" placeholder="中学名称" id="ZXMC"/>
		   		<div style="display:inline-block;position: relative;">
		   			<input type="text" class="form-control" style="width:75px;" placeholder="录取专业" id="LUQUZHUANYE"/>
		   			<div style="position:absolute;right:10px;top:11px;">
		   				<input id="kongcheckbox" type="checkbox">&nbsp;<div style="float:right;">空</div>
		   			</div>
		   		</div>
		   		<!-- <input type="text" class="form-control" style="width:75px;" placeholder="投档成绩" id="TOUDANGCHENGJI"/>  -->
		   		<!-- <input type="text" class="form-control" style="width:75px;" placeholder="出生日期" id="CHUSHENGRIQI"/>  -->
		   		<!-- <input type="text" class="form-control" style="width:75px;" placeholder="注册学籍时间开始" id="ZCXJSJBEGIN"/>  -->
		   		<input type="text"  id="ZCXJSJBEGIN" class="form-control"  placeholder="注册学籍时间开始" readonly="readonly" />
		   		~<input type="text"  id="ZCXJSJEND" class="form-control"  placeholder="注册学籍时间结束" readonly="readonly" />
		   		<input type="text"  id="CHUSHENGRIQIBEGIN" class="form-control"  placeholder="出生日期时间开始" readonly="readonly" />
		   		~<input type="text"  id="CHUSHENGRIQIEND" class="form-control"  placeholder="出生日期时间结束" readonly="readonly" />
		   		<input type="text"  id="RUXUESHIJIANBEGIN" class="form-control"  placeholder="入学时间开始" readonly="readonly" />
		   		~<input type="text"  id="RUXUESHIJIANIEND" class="form-control"  placeholder="入学时间结束" readonly="readonly" />
		   		<select name="" class="form-control jf_FltConditions" id="IS_RECEIVED">
                   <option value="">报到状态</option>
                   <option value="Y">已报到</option>
                   <option value="N">未报到</option>
		   		</select>
		   		<select name="" class="form-control jf_FltConditions" id="XINGBIE">
                   <option value="">性别</option>
                   <option value="男">男</option>
                   <option value="女">女</option>
		   		</select>
		   		<input type="text" class="form-control" style="width:75px;" placeholder="备注" id="beizhu"/> 
			<input type="hidden" class="form-control" style="width:75px;" placeholder="缴费区间" id="moneyqi"/> 
			<input type="hidden" class="form-control" style="width:75px;" placeholder="缴费区间" id="moneyzhi"/>
			<input type="text" class="form-control" style="width:75px;" placeholder="投档成绩区间起" id="TOUDANGCHENGJIQI"/> -
			<input type="text" class="form-control" style="width:75px;" placeholder="投档成绩区间止" id="TOUDANGCHENGJIZHI"/>
		</form>
		</div>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_xz_yx == '1' }">
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_add"><span class="fa fa-plus" ></span> 新增</a>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_dr_yx == '1' }">
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_import_stu"><span class="fa fa-sign-in"></span> 导入</a>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_drxg_yx == '1' }">
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_importedit_stu"><span class="fa fa-sign-in"></span> 导入修改</a>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_sc_yx == '1' }">
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_del"><span class="fa fa-trash-o"></span> 批量删除</a>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_xgzxzt_yx == '1' }">
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_editstatus"><span class="fa fa-edit"></span> 修改在学状态</a>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_tzzy_yx == '1' }">
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_tzzy"><span class="fa fa-exchange"></span> 调整专业</a>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_tg_yx == '1' }">
			<a  class="btn btn-danger" id="btn_tongguo"><span class="fa fa-check-circle" ></span> 审核通过</a>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_qrxx_yx == '1' }">
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_qrxx"><span class="fa fa-check-circle"></span> 确认信息</a>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_qrzy_yx == '1' }">
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_qrzy"><span class="fa fa-check-circle"></span> 确认专业</a>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_baodao_yx == '1' }">
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_baodao"><span class="fa fa-check-circle"></span> 报到</a>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_takekey_yx == '1' }">
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_takeKey"><span class="fa fa-check-circle"></span> 领取宿舍钥匙</a>
		</c:if>
			<a href="javascript:void(0);" class="btn btn-danger" id="checkOut"><span class="fa fa-upload"></span> 导出</a>
			
			
			
			<!-- <a href="javascript:void(0);" class="btn btn-danger" id="btn_search"><span class="fa fa-search"></span> 查询</a> -->
			<button class="btn btn-danger sfzkzqsousuobtn" id="btn_search"><span class="fa fa-search"></span> 查询</button>
<!-- 	<table class="table table-striped table-hover table-border jf_table" style="text-align: center;"> -->
		
<!-- 	</table> -->
	<input id="YX" name="YX" value="${pd.yx }" type="hidden">
	<fieldset>
		<table id="stuinfotable" class="table table-striped table-hover table-border jf_table" style="text-align: center;">
		</table>
	</fieldset>
	
	<div class="clearfix"></div>
	
	<OBJECT id=SynCardOcx1 style="WIDTH: 0px; HEIGHT: 0px"
		codeBase=${basepath}static/js/myjs/SynCardOcx.CAB#version=1,0,0,1
		classid=clsid:4B3CB088-9A00-4D24-87AA-F65C58531039></OBJECT>
	<script type="text/javascript" src="${basepath}static/js/myjs/sfzsousuo.js"></script>

