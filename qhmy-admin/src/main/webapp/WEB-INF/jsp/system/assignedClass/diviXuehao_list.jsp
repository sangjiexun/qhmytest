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
		<title>分班</title>
		<style type="text/css">
			html { overflow-x:hidden; }
		</style>
	</head>
	<link href="${basepath}static/css/bootstrapValidator.min.css" rel="stylesheet">
	<script src="static/js/bootstrap-dialog.min.js"></script>
	<!-- ztree所需css以及js -->
    <link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css">
    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script>
    <!--end ztree所需css以及js -->
	    
    <script type="text/javascript">
		var _basepath = "${basepath}";
		 function changeGrade(){
      			$("#BANJI").html("");
      			var grade_str='';
      			$('#grade option:selected').each(function() {
                    grade_str+=$(this).attr('idd')+',';
                });
      			var banxing=$("#BANJI_TYPE_PKID").val();
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
      				$("#BANJI").append(opt_str)
      				$('#BANJI').multiselect("destroy").multiselect({
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
	
	<script type="text/javascript" src="${basepath}static/js/myjs/diviXuehao.js"></script>
	<body>
		
		<div class="form-inline" style="margin-bottom:15px;">
			<select  multiple="multiple" id="grade">
                  <c:forEach items="${gradelist}" var="grade">
                       <option  idd="${grade.DICTIONARIES_ID }" value="${grade.DICTIONARIES_ID }" >${grade.NAME }</option>
                  </c:forEach>
			 </select>
			<select  multiple="multiple" id="BANJI_TYPE_PKID">
                  <c:forEach items="${banxinglist}" var="banxing">
                       <option value="${banxing.DICTIONARIES_ID }" >${banxing.NAME }</option>
                  </c:forEach>
			 </select>
			<select  multiple="multiple" id="BANJI" style="width:18%;">
			</select>
			<select name="" class="form-control jf_FltConditions" id="IS_FENXUEHAO" style="width:18%;">
				<option value="">是否分学号</option>
				<option value="Y">是</option>
				<option value="N">否</option>
			</select>
			<select name="" class="form-control jf_FltConditions" id="IS_JIAOFEI" style="width:18%;">
				<option value="">是否缴费</option>
				<option value="Y">是</option>
				<option value="N">否</option>
			</select>
			<input type="text" class="form-control jf_FltConditions sfzkzqsousuo" id="keywords" style="width:18%;" placeholder="身份证/学号/姓名/备注"/>
			<button class="btn btn-danger sfzkzqsousuobtn" id="assignedClassQuery"><span class="fa fa-search"></span> 查询</button>
			<a href="javascript:void(0);" class="btn btn-danger" id="btn_fenxuehao"><span class="fa fa-tag" ></span>分学号</a>
			<a href="javascript:void(0);" class="btn btn-danger" id="checkOut"><span class="fa fa-upload"></span> 导出</a>
			<!-- <a class="btn btn-danger" id="assignedClassQuery"><span class="fa fa-search"></span> 查询</a> -->
		</div> 
		<table class="table table-striped table-hover table-border jf_table" id="assignedClassTable" style="text-align: center;">
		</table>
		<input id ="SESSION_MENU_BUTTONS" value="${SESSION_MENU_BUTTONS }" style="display:none;">
		<input id="yx" name="yx" value="${pd.yx }" type="hidden">
	</body>
	<OBJECT id=SynCardOcx1 style="WIDTH: 0px; HEIGHT: 0px"
		codeBase=${basepath}static/js/myjs/SynCardOcx.CAB#version=1,0,0,1
		classid=clsid:4B3CB088-9A00-4D24-87AA-F65C58531039></OBJECT>
	<script type="text/javascript" src="${basepath}static/js/myjs/sfzsousuo.js"></script>
</html>
