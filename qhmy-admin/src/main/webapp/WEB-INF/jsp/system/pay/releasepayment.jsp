<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("basepath", basePath);
%>


	<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>发布缴费</title>
	    <script type="text/javascript">
	/* 
	var zNodes=new Array();

	<c:forEach items="${zuzhilist}" var="t">  
		var a={id:'${t.DEPARTMENT_ID}',pId:'${t.PARENT_ID}',name:'${t.NAME}'};
		zNodes.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
	</c:forEach> */
	
	</script>
<!-- 	     <script type="text/javascript" src="${basepath}static/js/myjs/fbjf_ztree.js"></script> -->
	    <style type="text/css">
			html { overflow-x:hidden; }
			.jf_ZtBox{
				left:0 !important;
				width:300px;
			}
			.btn{
			    height:34px !important;
				text-align:left !important;				
			}
			.menuContent{
				position: absolute !important;
				z-index:1;
				top:49px !important;
				left:93px !important;
			}
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
			.zengjianarea{
				float:left;
			}
			.jf_szright{
				position:fixed;
			}
			.multiselect-container{
				max-height:200px;
				overflow-y:auto;
			}
			.liankaozuigao{
				margin-left: 5px !important;
			}
			.baomingtimez{
				margin-left: 5px !important;
			}
		</style> 
	
	</head>
	<script type="text/javascript">
	var _basepath = "${basepath}";
	</script>
		<body>
		<div class="panel panel-info">
			<div class="jf_PanelHead panel-heading">
				<div class="panel-title">
					缴费项目
				</div>
			</div>
			<div class="panel-body">
				<form class="form-inline">
					<div class="form-group" style="margin-right:5%;">
						<label for="" class="jf_xing"> 缴费类型</label>
						<select name="" class="form-control jf_FltConditions" style="width:18%;" id="top_pay_style">
						<c:forEach items="${list_payStyle}" var="var">
							<option value="${var.PKID}">${var.PAY_STYLE_NAME} </option>
						</c:forEach>
						</select>
					</div>
					<div class="form-group" style="margin-right:5%;">
						<label for="" class="jf_xing"> 入学年份</label>
						<select name="" class="form-control jf_FltConditions" style="width:18%;" id="top_grade">
						<c:forEach items="${list_nianji}" var="var">
							<option value="${var.DICTIONARIES_ID}">${var.NAME} </option>
						</c:forEach>
						</select>
					</div>
					<div class="form-group" style="margin-right:5%;">
						<label for="" class="jf_xing"> 班型</label>
						<select name="" class="form-control jf_FltConditions" style="width:18%;" id="top_classType">
						<c:forEach items="${list_banxing}" var="var">
							<option value="${var.DICTIONARIES_ID}">${var.NAME} </option>
						</c:forEach>
						</select>
					</div>
					<div class="clearfix"></div>
				</form>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="jf_PanelHead panel-heading">
				<div class="panel-title">
					缴费名单
				</div>
			</div>
			<div class="panel-body">
				<div class="form-inline" style="margin-bottom:15px;">
					<div class="radio" style="float:left;">
						<label for="">
							<input type="radio" id="daorumd" value="dr"   name="jfszr"/> 导入名单
						</label>
					</div>
					<!-- <div  style="float:left;margin-left:75px;">
						<button class="btn btn-danger" id="btn_import_list"><span class="fa fa-sign-in"></span> 导入缴费名单</button>
					</div>  -->
					<div style="clear:both;"></div>
				</div>
				<div class="form-inline" style="margin-bottom:15px;">
					<div class="radio" style="margin-right:15px;">
						<label for="">
							<input type="radio" id="guizesc" value="gz"  name="jfszr"/> 系统根据规则生成
						</label>
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
						<div class="form-inline jf-feiYong">
							<div class="form-group pull-left">
								<label for=""> 费用调整</label>
								<select name="" class="form-control guizeshezhi_select">
									<option value="">无</option>
									<option value="hzxy">合作学校</option>
									<option value="bmsj">报名时间</option>
									<option value="yujiao">预交</option>
									<option value="lkcj">联考成绩</option>
									<option value="banji">班级</option>
									<option value="meiyuan">美院</option>
								</select> 
							</div>
							<div class="zengjianarea" hidden="hidden">
								<div class="pull-left jf-zhiJian">
									<input type="radio" checked="checked" value="0" name="xuan0"/> 直减
								</div>
								<div class="pull-left jf-zhiJian">
									<input type="radio" value="1" name="xuan0"/> 增长
								</div>
								<input class="form-control jianjiajine" type="text" placeholder="金额" style="width:70px !important;"/> 
<!-- 								<span class="fa fa-minus-square-o jianguize" style="margin-left:50px;"></span> -->
							</div>
							<div class="clearfix"></div>
						</div>
						<div class="form-inline youhuiarea" style="margin-left:65px;">
								<div class="clearfix"></div> 
						</div>
					</div>
					<button class="btn btn-danger" id="addfytz" style="margin:15px 0 0 65px;">增加费用调整</button>
				</div>
			</div>
		</div>
		<input type="hidden" name="trnumber" id="trnumber" value="" />
		<input type="hidden" name="list" id="list" value="" />
		<c:if test="${SESSION_MENU_BUTTONS.fbjf_qrbbc == '1' }">
			<button  id="savefabu"  class="btn btn-danger pull-right" >确认并保存</button>
		</c:if>
		
		<div class="clearfix"></div>
	</body>
	<script type="text/javascript">
	var successList = "${successList}";
	</script>
	<script type="text/javascript" src="${basepath}static/js/myjs/releasepayment.js"></script>
</html>
	