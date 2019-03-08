<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<!-- 百度echarts -->
<script src="plugins/echarts/echarts.min.js"></script>
</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="hr hr-18 dotted hr-double"></div>
					<div class="row">
						<div class="col-xs-12">
		<div>
<marquee id="notice" scrollamount='1' scrolldelay='60' direction= 'up'  width='1100px' height='50px'  onmouseout="this.start();" onmouseover="this.stop();" >
<p align='center'><c:forEach items="${pd2}" var="notice" >
<c:out value="${notice.value}"></c:out><br/>
</c:forEach></p>
</marquee></div>	
							<div class="alert alert-block alert-success">
								<button type="button" class="close" data-dismiss="alert">
									<i class="ace-icon fa fa-times"></i>
								</button>
								<i class="ace-icon fa fa-check green"></i>
								欢迎使用 联通劳务实名制管理 系统&nbsp;&nbsp;
							</div>
							
							<div id="main" style="width: 1100px;height:600px;">
					
							
						<%
						String strXML = "";
							strXML += "<graph caption='项目出勤率分析图表' xAxisName='项目名称' yAxisName='出勤率' decimalPrecision='2'  numberSuffix='%25' formatNumberScale='0'   yAxisMaxValue='100' >";
							String[] arr= {"AFD8F8","F6BD0F","8BBA00","FF8E46","008E8E","D64646","8E468E","588526","B3AA00","008ED6","9D080D","A186BE"};
						%>
						
						<c:forEach var="item"  items="${pd1}" varStatus="vs">
						
						<c:set var="a" value="${item.key}" scope="request"/>
						<c:set var="b" value="${item.value}" scope="request"/>
						<c:set var="c" value="${vs.index}" scope="request"/>
						
						<%
				 	Object a = request.getAttribute("a");
						Object b = request.getAttribute("b"); 
						Integer c = (Integer)request.getAttribute("c");
						  strXML += "<set name='"+a+"' value='"+b+"' color='"+arr[c]+"'/>";
						
						%>
						
					
					  </c:forEach>
						
						
						
						<% 
						
						
						
						
							/* strXML += "<set name='6' value='960' color='D64646'/>";
							strXML += "<set name='7' value='629' color='8E468E'/>";
							strXML += "<set name='8' value='622' color='588526'/>";
							strXML += "<set name='9' value='376' color='B3AA00'/>";
							strXML += "<set name='10' value='494' color='008ED6'/>";
							strXML += "<set name='11' value='761' color='9D080D'/>";
							strXML += "<set name='12' value='960' color='A186BE'/>"; */
							strXML += "</graph>";
							//Create the chart - Column 3D Chart with data from strXML variable using dataXML method
						%>
					
					<div class="center">
							<div style="float:left;">
								<table border="0" width="50%">
									<tr>
										<td><jsp:include
												page="../../FusionChartsHTMLRenderer1.jsp" flush="true">
												<jsp:param name="chartSWF" value="static/FusionCharts/Line.swf" />
												<jsp:param name="strURL" value="" />
												<jsp:param name="strXML" value="<%=strXML%>" />
												<jsp:param name="chartId" value="myNext" />
												<jsp:param name="chartWidth" value="500" />
												<jsp:param name="chartHeight" value="300" />
												<jsp:param name="debugMode" value="false" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
						
						</div>
							</div>
							
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->


		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		$(top.hangge());
		function stop(){
			$("#notice").stop();
			
		}
		function start(){
			$("#notice").start();
			
		}
	</script>
<script type="text/javascript" src="static/ace/js/jquery.js"></script>
</body>
</html>