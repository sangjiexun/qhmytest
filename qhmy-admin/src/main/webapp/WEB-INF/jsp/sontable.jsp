<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("basepath", basePath);
	
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Stream demo</title>
<base href="<%=basePath%>">
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!-- jsp文件头和头部 -->
<%@include file="system/index/top.jsp"%>
<link href="${basepath }static/css/bootstrap-dialog.min.css" rel="stylesheet">
<!-- <link href="${basepath }static/ace/css/datetime/bootstrap-datetimepicker.css" rel="stylesheet" media="screen"> -->
<!-- 新修改样式 -->
<link href="${basepath}static/ace/mycss/keman_yu.css" rel="stylesheet">

<script type="text/javascript">
  var _basepath = "${basepath}";
  var rule="${bZKQlist.kqsz.GUIZE}";
  var kqtype="${bZKQlist.kqsz.KAOQINLEIXING}";
</script>

</head>
<body>
	
	<div class="container keman_work">
		<!-- row -->
		<div class="row clearfix">
			
			<!-- 考勤类型设置 -->
			<div class="col-md-12 column">
				<h5 class="text-left keman_chengf" style="margin-top:15px;">
					考勤类型设置
				</h5>
				<div class="radio">
				  <label>
				    <input type="radio" name="optionsRadios" id="optionsRadios1" value="1" >规范型考勤
				  </label>
				  <label>
				    <input type="radio" name="optionsRadios" id="optionsRadios2" value="2" >简易型考勤
				  </label>
				  <label>
				    <input type="radio" name="optionsRadios" id="optionsRadios3" value="3" >在场工作时长考勤
				  </label>
				</div>
			</div>
			<!-- end 考勤类型设置 -->
			
			<!-- 考勤月设置 -->
			<div class="col-md-12 column">
				<h5 class="text-left keman_chengf">
					考勤日设置
				</h5>
				
				<div class="keman_kaoq">
					<div style="float: left;">考勤日为每月:</div>
					<div style="float: left;" class="keman_kaos">
						<select class="form-control" id="mySelect">
							<c:forEach var="i" begin="15" end="26" step="1">
									<option value="${i}"  <c:if test="${bZKQlist.kqsz.KAOQINRIQI==i}">selected="selected" </c:if>>${i}</option>
							</c:forEach>
						</select>
					</div>
				    <div style="float: left;">号</div>
				</div>
			</div>
			<!-- end 考勤月设置 -->
			<!-- 是否区分进出设置 -->
			<div class="col-md-12 column">
				<h5 class="text-left keman_chengf">
					进出设置
				</h5>
				
				<div class="keman_kaoq">
					<div style="float: left;">区分进出:</div>
					<div style="float: left;" class="keman_kaos">
						<input type="checkbox" id="jcsz" <c:if test="${bZKQlist.kqsz.SFQFJCSB=='Y'}">checked="checked" </c:if> />
					</div>
				    
				</div>
			</div>
			<!-- end 是否区分进出设置 -->
		
			<!-- column -->
			<div class="col-md-12 column">
			
				<!-- tab -->
				<div class="tabbable keman_tab" id="tabs-320133">
					
					<ul class="nav nav-tabs">
						<li id="biaozhun" class="kqlx">
							 <a href="#panel-101204" data-toggle="tab">规范型考勤</a>
						</li>
						<li id="jiandan" class="kqlx">
							 <a href="#panel-204765" data-toggle="tab">简易型考勤</a>
						</li>
						<li id="zaigong" class="kqlx">
							 <a href="#panel-204769" data-toggle="tab" >在场工作时长考勤</a>
						</li>
					</ul>
					
					<div class="tab-content">
						<div class="tab-pane" id="panel-101204">
							<button type="button" class="btn btn-xs zj keman_btnd" id="zj1">追加</button>
							<button type="button" class="btn btn-xs cr keman_btnd" id="cr1">插入</button>
							<button type="button" class="btn btn-xs sc keman_btnd" id="sc1">删除</button>
							<br />
							<br />
							<!-- table -->
							<table id="sontableTable" class="table table-condensed table-bordered" >
								<thead>
									<tr>
										<th>打卡时段</th>
										
									</tr>
								</thead>
								<tbody id='bztbody'>
									<tr class="moban" style="display:none;">
										<td>
											<div class="col-md-4 column keman_kaoq">
												<div style="display:inline;float: left;line-height:28px;">从&nbsp</div>
												<div style="display:inline;float: left;">
													<input name="startDate" type="text" class="input-sm  keman_kaos Wdate" onfocus="WdatePicker({dateFmt:'H:mm'})" 
														style="width: 100px;" placeholder="文本输入">
												</div>
												<div style="display:inline;float: left;line-height:28px;">&nbsp至&nbsp</div>
												<div style="display:inline;float: left;">
													<input name="endDate" type="text" style="width: 100px;" class="input-sm  keman_kaos Wdate" onfocus="WdatePicker({dateFmt:'H:mm'})" placeholder="文本输入">
												</div>
											</div>
											
											
											<div class="col-md-4 column keman_kaoq">
												<div style="display:inline;float: left;line-height:28px;">在场时间超过</div>
												<div style="display:inline;float: left;">
													<input name="yxdate" type="text" style="width: 100px;" class="input-sm keman_kaos" placeholder="文本输入">
												</div>
												<div style="display:inline;float: left;line-height:28px;">有效</div>
											</div>
											
											
											<!-- <div class="col-md-3 column keman_kaoq" style="display:none;">
												<div style="display:inline;float: left;line-height:28px;">在起止时间</div>
												<div style="display:inline;float: left;">
													<input name="qhDate" type="text" style="width: 100px;" class="input-sm keman_kaos" placeholder="文本输入">
												</div>
												<div style="display:inline;float: left;line-height:28px;">前后打卡有效</div>
											</div> -->
											
											
											<div class="col-md-4 column keman_kaoq">
												<div style="display:inline;float: left;line-height:28px;">计</div>
												<div style="display:inline;float: left;">
													<input name="gong" type="text" style="width: 100px;" class="input-sm keman_kaos" placeholder="文本输入">
												</div>
												<div style="display:inline;float: left;line-height:28px;">个工</div>
												<div style="display:none">
													<input name="pkid" type="text" value="99999">
												</div>
											</div>
											
										</td>
									</tr>
									
								</tbody>
							</table>
							<!-- end table -->
							<!-- 考勤类型设置 -->
<!-- 							<div class="">      删除了类 col-md-12 column -->
<!-- 								<h5 class="text-left keman_chengf"> -->
<!-- 									规范型考勤规则 -->
<!-- 								</h5> -->
<!-- 								<div class="radio"> -->
<!-- 								  <label> -->
<!-- 								    <input type="radio"  name="ruleRadios" id="ruleRadios1" value="1" >规范打卡,有效时间内记录有进有出 -->
<!-- 								  </label> -->
<!-- 								  <label> -->
<!-- 								    <input type="radio"  name="ruleRadios" id="ruleRadios2" value="2" >只进场或出场打卡 -->
<!-- 								  </label> -->
<!-- 								</div> -->
<!-- 							</div> -->
						</div>
						<div class="tab-pane" id="panel-204765">
							<div style="display:inline;float: left;line-height:28px;">每日计&nbsp</div>
							<div style="display:inline;float: left;margin-top:-3px;">
								<input id="gong" name="gong" type="text" style="width: 100px;"
									class="input-sm" placeholder="文本输入" value="${bZKQlist.kqsz.MEIRIJIGEGONG }">
							</div>
							<div style="display:inline;float: left;line-height:28px;">&nbsp个工</div>
							<div class="clearfix"></div>
						</div>
						<div class="tab-pane" id="panel-204769">
							<div>
							 <button type="button" class="btn btn-xs zj keman_btnd" id="zj2">追加</button>
							<button type="button" class="btn btn-xs cr keman_btnd" id="cr2">插入</button>
							<button type="button" class="btn btn-xs sc keman_btnd" id="sc2">删除</button>
							<br />
							<br />
							</div>
						    
							<!-- table -->
							<table id="sontableTable2" class="table table-condensed table-bordered" >
							
								<thead>
									<tr>
										<th>打卡时段设置</th>
									</tr>
								</thead>
								<tbody id='dksdtbody'>
									 <tr class="moban" style="display:none;">
										<td style="padding-top:10px;padding-bottom:8px;">
											<div class="col-md-3 column">
												<div style="display:inline;float: left;line-height:28px;">从&nbsp</div>
												<div style="display:inline;float: left;margin-top:-3px;">
													<input name="startDate" type="text" class="input-sm Wdate" 
														style="width: 100px;" placeholder="开始时间" onfocus="WdatePicker({dateFmt:'H:mm'})">
												</div>
												<div style="display:inline;float: left;line-height:28px;">&nbsp至&nbsp</div>
												<div style="display:inline;float: left;margin-top:-3px;">
													<input name="endDate" type="text" style="width: 100px;" class="input-sm Wdate" placeholder="结束时间" onfocus="WdatePicker({dateFmt:'H:mm'})" >
												</div>
											</div>
											
											
											<div class="col-md-3 column">
												
												<div style="display:none">
													<input name="pkid" type="text" value="99999">
												</div>
											</div>
											
											
											
										</td>
									</tr>
									
								</tbody>
							</table>
							<!-- end table -->
							<div>
								<button type="button" class="btn btn-xs zj keman_btnd" id="zj3">追加</button>
							<button type="button" class="btn btn-xs cr keman_btnd" id="cr3">插入</button>
							<button type="button" class="btn btn-xs sc keman_btnd" id="sc3">删除</button>
								<br /> 
								<br />
							</div>


							<!-- table -->
							<table id="sontableTable3" class="table table-condensed table-bordered" >
								<thead>
									<tr>
										<th>工作区间设置</th>
										
									</tr>
								</thead>
								<tbody id='gzqjtbody'>
								<tr id="nodelete">
								<td>											
											<div class="col-md-3 column" style="display:none">
												<div style="display:inline;float: left;line-height:28px;">大于</div>
												<div style="display:inline;float: left;">
													<input name="yxdate" type="text" style="width: 100px;" class="input-sm" placeholder="文本输入" value="${bZKQlist.gzqujiandata1.DYXS }">
												</div>
												<div style="display:inline;float: left;line-height:28px;">个小时</div>
											</div>
											<div class="col-md-3 column" style="display:none;line-height:28px;">且</div>
											<div class="col-md-3 column">
												<div style="display:inline;float: left;line-height:28px;">小于&nbsp</div>
												<div style="display:inline;float: left;">
													<input name="yxdate" type="text" style="width: 100px;" class="input-sm" placeholder="文本输入" value="${bZKQlist.gzqujiandata1.XYXS }">
												</div>
												<div style="display:inline;float: left;line-height:28px;">&nbsp个小时</div>
											</div>
											
											<div class="col-md-3 column">
												<div style="display:inline;float: left;line-height:28px;">计&nbsp</div>
												<div style="display:inline;float: left;">
													<input name="gong" type="text" style="width: 100px;" class="input-sm" placeholder="文本输入" value="${bZKQlist.gzqujiandata1.GONG }">
												</div>
												<div style="display:inline;float: left;line-height:28px;">&nbsp个工</div>
												<div style="display:none">
												    <input name="pkid" type="text" value="${bZKQlist.gzqujiandata1.PKID }">
												</div>
											</div>
										</td>
									</tr>
									<tr class="moban" style="display:none;">
										<td>
											<div class="col-md-3 column">
												<div style="display:inline;float: left;line-height:28px;">大于&nbsp</div>
												<div style="display:inline;float: left;">
													<input name="yxdate" type="text" style="width: 100px;" class="input-sm" placeholder="文本输入" >
												</div>
												<div style="display:inline;float: left;line-height:28px;">&nbsp个小时</div>
											</div>
											<div class="col-md-3 column" style="line-height:28px;">且</div>
											<div class="col-md-3 column">
												<div style="display:inline;float: left;line-height:28px;">小于&nbsp</div>
												<div style="display:inline;float: left;">
													<input name="yxdate" type="text" style="width: 100px;" class="input-sm" placeholder="文本输入" >
												</div>
												<div style="display:inline;float: left;line-height:28px;">&nbsp个小时</div>
											</div>
											
											<div class="col-md-3 column">
												<div style="display:inline;float: left;line-height:28px;">计&nbsp</div>
												<div style="display:inline;float: left;">
													<input name="gong" type="text" style="width: 100px;" class="input-sm" placeholder="文本输入" >
												</div>
												<div style="display:inline;float: left;line-height:28px;">&nbsp个工</div>
												<div style="display:none">
												    <input name="pkid" type="text" value="">
												</div>
											</div>
											
										</td>
									</tr>
									
								</tbody>
							</table>
							<!-- end table -->
							<!-- end table -->
							
						</div>
					</div>
				</div>
				<!-- end tab -->
			</div>
			<!-- end column -->
		</div>
		<!-- end row -->
		
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class=""   style="margin: 10px;text-align: center;" >
					<button type="button" class="btn keman_btnr" id="save">保存</button>
				</div>
			</div>
		</div>
		
	</div>
	
	
	
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@include file="system/index/foot.jsp"%>
	<script src="${basepath}static/ace/js/bootbox.js"></script>
	<script src="${basepath}static/js/jquery-2.1.1.min.js"></script>
	<script src="${basepath}static/ace/js/bootstrap.js"></script>
	<script src="${basepath}static/js/bootstrap-table.min.js"></script>
    <script src="${basepath}static/js/bootstrap-table-zh-CN.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript" src="${basepath}static/ace/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${basepath}static/ace/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	
	<script	src="${basepath}static/My97DatePicker/WdatePicker.js"></script>
	
	<script	src="${basepath}static/js/bootstrap-dialog.min.js"></script>
	<!-- 删除时确认窗口 -->
	<script src="${basepath}static/ace/js/bootbox.js"></script>
	<script type="text/javascript">
		$.ajaxSetup ({ cache: false });
		var kaoqinriqi="${bZKQlist.kqsz.KAOQINRIQI}"
	</script>
	<!-- sontable-->
	<script type="text/javascript" src="${basepath }static/js/myjs/sontable.js"></script>
	<!-- end sontable -->
	
	
</body>
</html>