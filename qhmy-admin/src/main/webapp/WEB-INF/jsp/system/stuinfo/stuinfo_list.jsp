<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("basepath", basePath);
	
%>
	    
	    	    <!--  table横纵向滚动条 -->
	   <link rel="stylesheet" href="${basepath}static/css/bootstrap-table.css">
	    
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
	    <script type="text/javascript" src="${basepath}static/js/myjs/stuinfo.js"></script>
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
			/* .fixed-table-container{
				height:auto !important;
			} */
			.multiselect-container{
				max-height:350px;
				overflow-y:auto;
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
			.modal-backdrop {
			    position: static!important;
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
      			var grade_str='';
      			$('#grade option:selected').each(function() {
                    grade_str+=$(this).attr('idd')+',';
                });
      			var banxing=$("#banxing").val();
      			var banxing_str='';
      			if(banxing!=null){
      				for(var i=0;i<banxing.length;i++){
      					banxing_str+=banxing[i]+',';
      				}
      			}

      			$.post(_basepath+"stuinfo/getBanJi.json",{SYS_DICTIONARIES_PKID:grade_str,BANXING_PKID:banxing_str},function(data){
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
			<input hidden="hidden" id="sortName" >		
			<input hidden="hidden" id="sortOrder" >			
			<select id="grade" multiple="multiple">								
				<c:forEach items="${gradelist}" var="grade">
					<option  idd="${grade.DICTIONARIES_ID }" value="${grade.DICTIONARIES_ID }">${grade.NAME}</option>
				</c:forEach>
			</select>									
			<select id="banxing" multiple="multiple">								
				<c:forEach items="${banXinglist}" var="var">
					<option  value="${var.DICTIONARIES_ID }">${var.NAME}</option>
				</c:forEach>
			</select>									


			<select name="" id="banji" multiple="multiple">
		   	</select>
		   		
			
            <select name=""  id="partschool" multiple="multiple">
                   <c:forEach items="${partSchoollist}" var="var">
                        <option value="${var.PKID }" >${var.SCHOOLNAME }</option>
                   </c:forEach>
		    </select>
		    <select name="" class="form-control jf_FltConditions" id="zxzt">
                   <option value="">在学状态</option>
                   <option value="ZX">在学</option>
                   <option value="TX">退学</option>
                   <option value="BY">毕业</option>
		   	</select>
		   	<input type="text" class="form-control jf_FltConditions sfzkzqsousuo" placeholder="身份证号/学号/姓名/备注" id="search"/>	
		   	<a href="#demo" class="btn btn-danger" data-toggle="collapse" title='更多查询条件' id='more'><i id='icon' class="fa fa-angle-double-down"></i> 更多查询条件</a>
		 </div>
		 <div id="demo" class="collapse">
               <form class="form-inline" id="moreQueryArea" style="margin-bottom: 15px;">
		   		
		   		<select name=""  id="cengci" multiple="multiple">                 
                   <c:forEach items="${cengci_list}" var="var">
                        <option value="${var.PKID }">${var.CENGCI_NAME}</option>
                   </c:forEach>
		   		</select>
		   		<select id="yjgrade" multiple="multiple">								
					<c:forEach items="${gradelist}" var="var">
						<option value="${var.DICTIONARIES_ID }">${var.NAME}</option>
					</c:forEach>
				</select>			   		
				<input type="text"  id="GYRXSJBEGIN" class="form-control"  placeholder="高一入学时间开始" readonly="readonly" />
		   		~<input type="text"  id="GYRXSJEND" class="form-control"  placeholder="高一入学时间结束" readonly="readonly" />
		   		<input type="text" class="form-control" style="width:75px;" placeholder="联考分数起" id="LKFSQI"/> -
				<input type="text" class="form-control" style="width:75px;"  placeholder="联考分数止" id="LKFSZHI"/>
				<select id="meiyuan" multiple="multiple">								
					<c:forEach items="${meiyuanlist}" var="var">
						<option value="${var.PKID }">${var.NAME}</option>
					</c:forEach>
				</select>	
				<input type="text"  id="CJSJTMBEGIN" class="form-control"  placeholder="报名时间开始" readonly="readonly" />
		   		~<input type="text"  id="CJSJTMEND" class="form-control"  placeholder="报名时间结束" readonly="readonly" />
 				<select name="" class="form-control jf_FltConditions" id="IS_TONGGUO">
                   <option value="">审核状态</option>
                   <option value="1">通过</option>
                   <option value="0">不通过</option>
                   <option value="2">未审核</option>
		   		</select>
 				<select name="" class="form-control jf_FltConditions" id="IS_RUZHU">
                   <option value="">入住状态</option>
                   <option value="YY">已入住</option>
                   <option value="NN">未入住</option>
		   		</select>
		   		<select name="" class="form-control jf_FltConditions" id="IS_ISPAY">
                   <option value="">是否缴费</option>
                   <option value="YY">是</option>
                   <option value="NN">否</option>
		   		</select>
		   		<input type="text" class="form-control" style="width:75px;" placeholder="学号起" id="XUEHAOQ"/> -
				<input type="text" class="form-control" style="width:75px;"  placeholder="学号止" id="XUEHAOZ"/>
		</form>
		</div>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_dr == '1' }">
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_import_stu"><span class="fa fa-sign-in"></span> 导入</a>
		</c:if>
		<a href="javascript:void(0);" class="btn btn-danger" id="checkOut"><span class="fa fa-upload"></span> 导出</a>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_tg == '1' }">
			<a  class="btn btn-danger" id="btn_tongguo"><span class="fa fa-check-circle" ></span> 审核通过</a>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_btg == '1' }">
			<a  class="btn btn-danger" id="btn_butongguo"><span class="fa fa-check-circle" ></span> 审核不通过</a>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_tx == '1' }">
			<a  class="btn btn-danger" id="btn_tuixue"><span class="fa fa-check-circle" ></span> 退学</a>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_by == '1' }">
			<a  class="btn btn-danger" id="btn_biye"><span class="fa fa-check-circle" ></span> 毕业</a>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_fuxue == '1' }">
			<a  class="btn btn-danger" id="btn_fuxue"><span class="fa fa-check-circle" ></span> 复学</a>
		</c:if>
		<c:if test="${SESSION_MENU_BUTTONS.xslb_sc == '1' }">
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_del"><span class="fa fa-trash-o"></span> 批量删除</a>
		</c:if>
		<button class="btn btn-danger sfzkzqsousuobtn" id="btn_search"><span class="fa fa-search"></span> 查询</button>

	<fieldset>
		<table id="stuinfotable" class="table table-striped table-hover table-border jf_table" 
		style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;" data-height="680"
		>
		 
		</table>
	</fieldset>
	
	<div class="clearfix"></div>
	<div id="mymoda1" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header jf_mod-header">
					<div class="close" data-dismiss="modal">&times;</div>
					<b class="jf_HeaderText" id="jiaotuifei">审核失败原因</b>
				</div>
				<div class="form-inline">
					<div class="form-group">
						<label class="jf_label"   style="padding: 15px !important;" >审核失败原因：</label>
						<input type="text" name="" value="" class="form-control"  id="IS_TONGGUO_REASON" placeholder="请输入内容"  maxlength="200"
						maxlength="500" style="width:450px  !important;"/>
					</div>
				</div>
				<div class="modal-footer">
					<button class="btn btn-danger" id="payButtonBatch">确认</button>
					<button class="btn btn-default" data-dismiss="modal" id="cancelButtonBatch">取消</button>
				</div>
			</div>
		</div>
	</div>
	<OBJECT id=SynCardOcx1 style="WIDTH: 0px; HEIGHT: 0px"
		codeBase=${basepath}static/js/myjs/SynCardOcx.CAB#version=1,0,0,1
		classid=clsid:4B3CB088-9A00-4D24-87AA-F65C58531039></OBJECT>
	<script type="text/javascript" src="${basepath}static/js/myjs/sfzsousuo.js"></script>

