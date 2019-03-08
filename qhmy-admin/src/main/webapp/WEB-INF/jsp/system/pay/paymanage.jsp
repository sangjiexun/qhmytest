<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basepath", basePath);
%>
	
     <script type="text/javascript">
		var zNodes=new Array();
		<c:forEach items="${zuzhilist}" var="t">  
			var a={id:'${t.DEPARTMENT_ID}',pId:'${t.PARENT_ID}',name:'${t.NAME}',leibie:'${t.LEIBIE}'};
			zNodes.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
		</c:forEach>
		var colStr="${colStr}";
	 </script> 
    <%--  <script type="text/javascript" src="${basepath}static/js/myjs/fbjf_ztree.js"></script>
     <script type="text/javascript" src="${basepath}static/js/myjs/payitem_ztree3.js"></script> --%>
	    	    <!--  table横纵向滚动条 -->
	   <link rel="stylesheet" href="${basepath}static/css/bootstrap-table.css">
	   
	    
	    
	    <style type="text/css">
			html { overflow-x:hidden; }
			.menuContent{
				position: absolute !important;
				z-index:1;
				width:300px !important;
			}
			.btn{
			    height:34px !important;
				text-align:left !important;				
			}
			.dropdown-menu {
			    min-width: 190px !important;
			}
			.jf_ZhaoPian{
				margin-top:15px;
				margin-right:15px;
			}
			.zengjianarea{
				float:left;
			}
			.liankaozuigao{
				margin-left: 5px !important;
			}
			.baomingtimez{
				margin-left: 5px !important;
			}
			html { overflow-x:hidden; }
			.stream-browse-files{
				margin-bottom:15px !important;
			}
			
			.multiselect-container{
				max-height:180px;
				overflow-y:auto;
			}
		</style> 
		<input type="hidden" name="gzhdr" id="gzhdr" value="" />
		<input type="hidden" name="drpkid" id="drpkid" value="" />
		<input type="hidden" name="pkidItem" id="pkidItem" value="" />
		<div class="form-inline">
			<!-- <input type="text" class="laydate-icon form-control" name="startDate" id="startDate"  readonly="readonly" style="cursor:pointer;margin-right:0;"  placeholder="开始日期"/>
			~
			<input  type="text" class="laydate-icon form-control"  name="endDate" id="endDate"  readonly="readonly" style="cursor:pointer;"  placeholder="结束日期"/>
			
			<select name="STATUS" class="form-control jf_FltConditions" style="width:18%;" id="statusselect">
					<option  value="">审核状态</option>
					<option  value="0">待审核</option>
					<option  value="1">审核成功</option>
					<option  value="-1">审核失败</option>
					<option  value="2">已结束</option>
					<option  value="3">已过期</option>
			</select>
			<select name="STARTUSING" class="form-control jf_FltConditions" style="width:18%;" id="startusingselect">
					<option  value="">启用状态</option>
					<option  value="Y">启用</option>
					<option  value="N">不启用</option>
			</select> -->
			<select name="" id="ruxuenianfen" class="form-control jf_FltConditions"  placeholder="学年">
			<option value="">入学年份</option>
			<c:forEach items="${ruxuenianfen}" var="rxnf" varStatus="rxnft" >
				<option value="${rxnf.DICTIONARIES_ID}">${rxnf.NAME}</option>
			</c:forEach>
			</select>
			
			<select name="" id="banxing" class="form-control jf_FltConditions"  placeholder="学年">
			<option value="">班型</option>
			<c:forEach items="${banxing}" var="bx" varStatus="bxt" >
				<option value="${bx.DICTIONARIES_ID}">${bx.NAME}</option>
			</c:forEach>
			</select>
			
			
			<select name="" id="pay_style_gl" class="form-control jf_FltConditions"  placeholder="缴费类型">
			<option value="">缴费类型</option>
			<c:forEach items="${pay_style_list}" var="fs" varStatus="fst" >
				<option value="${fs.PKID}">${fs.PAY_STYLE_NAME}</option>
			</c:forEach>
			</select>
			<button class="btn btn-danger" id="btn_search"><span class="fa fa-search"></span> 查询</button>
		</div>
			<div id="mymodal2" class="modal fade" aria-hidden="true" data-backdrop="static">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header jf_mod-header">
							<div class="close" data-dismiss="modal">&times;</div>
							<b class="jf_HeaderText">缴费项目</b>
						</div>
						<div class="modal-body">
							<div class="form-inline" style="margin-bottom:15px;">
								<input id="room_interface" hidden="hidden" value="">
								<div class="form-group" style="margin-right:5%;">
									<label for="" class="jf_xing"> 缴费类型</label>
									<input class="form-control" id="top_pay_style" payStyle="" readonly="readonly" type="text"/> 
								</div>
								<div class="form-group" style="margin-right:5%;">
									<label for="" class="jf_xing"> 入学年份</label>
									<input class="form-control" id="top_grade" grade="" readonly="readonly" type="text"/> 
								</div>
								<div class="form-group" style="margin-right:5%;">
									<label for="" class="jf_xing"> 班型</label>
									<input class="form-control" id="top_classType" classType="" readonly="readonly" type="text"/> 
								</div>
							</div>
							<div id="xgjfsz">
								<div class="form-inline" style="margin-bottom:15px;margin-top:10px;">
									<div class="form-group">
										<label for="" style="margin-right: 4px;color:#F00;font-size : 16px;margin-left: 4px;"> 缴费设置</label>
									</div>
								</div>
								<div style="margin-left:83px;">
									<div class="form-inline">
										<div class="form-group">
											<label for="" class="jf_xing"> 标准费用</label>
											<input class="form-control" id="bzfy" type="text"/> 
										</div>
									</div>
									<div id="feiyongtiaozhengarea">
									</div>
									<button class="btn btn-danger" id="addfytz" style="margin:15px 0 0 65px;">增加费用调整</button>
								</div>
							</div>
							<div  id="xgdrmd" style="margin-top:10px;margin-bottom:15px;">
								<button class="btn btn-danger" id="btn_import_list_dr"><span class="fa fa-sign-in"></span> 导入缴费名单</button>
								<button class="btn btn-danger" id="delete_list_dr"><span class="fa fa-sign-in"></span> 删除已导入名单</button>
							</div>
						</div>	
						<div class="modal-footer">
							<button class="btn btn-danger" id="btn_save2">保存</button>
							<button class="btn btn-default" data-dismiss="modal" id="btn_cancel2">取消</buttom>
						</div>
					</div>
				</div>
		</div>
	<fieldset>
		<table id="paymanagetable"
			class="table" data-height="720" style="text-align: center;">
		</table>
	</fieldset>
	<input type="hidden" name="trnumber" id="trnumber" value="" />
	<input type="hidden" name="list" id="list" value="" />
	<!-- <input type="hidden" name="jje" id="jje" value="" /> -->
		<div class="clearfix"></div>
		<!-- <script type="text/javascript">
			//日期框
			/* $('.date-picker').datepicker({autoclose: true,todayHighlight: true}); */
		</script> -->
	<script type="text/javascript" src="${basepath}static/js/myjs/paymanage.js"></script>
	<%-- <script type="text/javascript" src="${basepath}static/js/myjs/uploadPicture.js"></script>
	<script type="text/javascript" src="${basepath}static/js/myjs/paymanagerxgjf.js"></script> --%>
	<OBJECT id=SynCardOcx1 style="WIDTH: 0px; HEIGHT: 0px"
		codeBase=${basepath}static/js/myjs/SynCardOcx.CAB#version=1,0,0,1
		classid=clsid:4B3CB088-9A00-4D24-87AA-F65C58531039></OBJECT>
	<script type="text/javascript" src="${basepath}static/js/myjs/sfzsousuo.js"></script>
	<!-- 上传插件
	<script type="text/javascript" src="${basepath }static/js/stream-v1.js"></script>
	<script type="text/javascript" src="${basepath }static/js/myjs/import/import-list.js"></script>
	 -->
