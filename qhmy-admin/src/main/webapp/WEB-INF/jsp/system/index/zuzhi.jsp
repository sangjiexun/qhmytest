<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
		<meta charset="UTF-8">
		<title>公司首页展示</title>
		<link rel="stylesheet" type="text/css" href="${basepath }static/ace/mycss/home_chart2.css">
	</head>
<div class="km_outbox" style="background:#f9f9f9;" id="zuzhdiv">
			<ul>
				<li class="km_xiangm" style="">
					<p class="biaot">在施项目数</p>
					<h1 class="shuz" style="margin-top:-6px !important;">${zcxms }</h1>
				</li>
				<li class="km_gongr" title="在施项目登记过(含退场)的工人数">
					<p class="biaot">在册工人数</p>
					<h1 class="shuz" style="margin-top:-6px !important;">${zaicers }</h1>
				</li>
				<li class="km_zaic" title="在施项目登记未退场的工人数">
					<p class="biaot">在场工人数</p>
					<h1 class="shuz" style="margin-top:-6px !important;">${zaichrs}</h1>
				</li>
				<li class="km_jinc">
					<p class="biaot">日累计进场</p>
					<h1 class="shuz" style="margin-top:-6px !important;">${leijjchrs}</h1>
				</li>
				<div class="clearfix"></div>
			</ul>
			<div class="km_cenbox">
				<div class="km_xianc">
					<p>现场实时人数</p>
					<h1>${shishrs}</h1>
				</div>
				<div class="km_chart">
					<div id='xmtj' style="height:400px;"></div>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="km_blbox">
				<p class="km_gz">现场工人工种分布</p>
				<div class="km_sjin">
					<input type="button" value="实时" class="bingtu" id="shsh_btn"/>
					<input type="button" value="今日" class="bingtu" id="jinri_btn"/>
				</div>
				<div class="clearfix"></div>
				<div style="width:60%;margin: 0 auto; " >	
				<div id="xchgz" style="height:400px"></div>
				</div>
			</div>
			<div class="km_brbox">
				<p class="km_chuq">最近7天出勤率</p>
				<div style="width:60%;margin: 0 auto; ">
				<div id="chuqinlv" style="height:400px"></div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		
		
		<!--详情list页面  -->
<div class="ct-body lbr-page-body body-view-pro-team-workType-list"
	style="display: none;" id="xmlistdiv">
	<div class="ct-filter-bar" style="font-size: 16pt;padding-left:5px" id="biaotiname">
		<span class="glyphicon glyphicon-home km_color"></span> 
		<span><a class="lbr-btn-back-home km_shouye" href="javascript:void(0);" onclick="backToHome();">首页</a></span>
	</div>





	<div class="panel-body lbr-pro-team-summary-list tablediv" style="display:none;"
		id="xmlist">
		<table class="ct-table table table-hover fixed" id="xmtable">
			<thead class="tall">
				<tr>
					<th width="50">序号</th>
					<th width="150">项目</th>
					<th width="80">现场实时人数</th>
					<th width="80">日累计进场人数</th>
					<th width="80">队伍在场人数</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${xiangmulist}" var="i" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${i.xmmc}</td>
						<td>${i.RENSHU}</td>
						<td>${i.ljrs}</td>
						<td>${i.zcrs}</td>
					</tr>
				</c:forEach>
				<tr id="totalRow"></tr>
			</tbody>
		</table>
	</div>
	
	<div class="panel-body lbr-pro-team-summary-list tablediv" style="display:none;"
		id="kqlist">
		<table class="ct-table table table-hover fixed" id="kqtable">
			<thead class="tall">
				<tr>
					<th width="50">序号</th>
					<th width="150">项目</th>
					<th width="80">日累计进场</th>
					<th width="80">项目在场人数</th>
					<th width="80">考勤率</th>
				</tr>
			</thead>
			<tbody>
				
				
			</tbody>
		</table>
	</div>
	
	<div class="panel-body lbr-pro-team-summary-list tablediv"style="display:none;" id="zuzhikqmx">
			<table class="ct-table table table-hover fixed" id="zuzhikqtable">
				<thead class="tall">
				<tr>
				<th width="50">序号</th>
				<th width="150">队伍</th>
				<th width="80">日累计进场</th>
				<th width="80">队伍在场人数</th>
				<th width="80">出勤率</th>
				</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
	</div>
</div>
<!-- 引入echarts -->
		
        <!-- luzhen优化  <script src="plugins/echarts/echarts.min.js"></script> -->
        <script src="static/js/myjs/echarts.min.js"></script>
        
        <script type="text/javascript">
                                    //柱状图数据
									var xmarray = new Array();
									<c:forEach items="${xiangmulist}" var="t">
									var a = {
										xmmc : '${t.xmmc}',
										ssrs : ${t.RENSHU},
										ljrs : ${t.ljrs},
										zcrs : ${t.zcrs}
									}
									xmarray.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
									</c:forEach>
									xmarray.sort(function(a, b) {
										return b.RENSHU - a.RENSHU
									});

									
									
									
									//饼图数据
									//实时工种数据
									var shshgzh=new Array();
									<c:forEach items="${shshgzhlist}" var="t">
									var a = {
											MINGCHENG : '${t.MINGCHENG}',
											RENSHU : ${t.RENSHU}
									}
									shshgzh.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
									</c:forEach>
									//当天工种数据
									var jrgzh=new Array();
									<c:forEach items="${jrgzhlist}" var="t">
									var a = {
											MINGCHENG : '${t.MINGCHENG}',
											RENSHU : ${t.RENSHU}
									}
									jrgzh.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
									</c:forEach>
									
									//出勤率数据
									var cqlarray=new Array();
									<c:forEach items="${sevenDaysRslist}" var="t">
									var a = {
											RIQI : '${t.RIQI}'.substring(5,10).replace(/-/g, '/'),
											KAOQINLV : ${t.everyDayKqlv}
									}
									cqlarray.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
									</c:forEach>
									
									//合计行方法
									var sum2=function(tableid,zongjiid){
										 var total1=0;
										 var total2=0;
										 var total3=0;
										    $("#"+tableid).each(function(){  
										        $(this).find('td:eq(2)').each(function(){  
										        	total1 += parseFloat($(this).text());   
										        });  
										        $(this).find('td:eq(3)').each(function(){  
										        	total2 += parseFloat($(this).text());   
										        }); 
										        $(this).find('td:eq(4)').each(function(){  
										        	total3 += parseFloat($(this).text());   
										        }); 
										    });  
										    if(zongjiid=='totalkqlv' || zongjiid =='totalzuzhikq'){
										    	var percent=total2==0?0:(total1*100/total2).toFixed(2)
										    	$("#"+zongjiid).append('<td></td><td>合计</td><td>'+total1+'</td><td>'+total2+'</td><td>'+percent+'%</td>');  
										    	return;
										    }
										   
										    $("#"+zongjiid).append('<td></td><td>合计</td><td>'+total1+'</td><td>'+total2+'</td><td>'+total3+'</td>');  
										
									};
									
									var b=function(projectbm,riqi,xmmc){
										$.ajax({
											type : "post",
											dataType : "json",
											url : _basepath +"main/index.do",
											data : {
												riqi : riqi,
												projectbm:projectbm
											},
											success : function(mv) {
												console.log(mv.varList)
												var kqmxList = mv.varList;
												$('#zuzhikqmx').find("tbody").empty();
												for (var i = 0; i < kqmxList.length; i++) {
													var kqv = 0;
													if (kqmxList[i].dtljrs == 0) {
														kqlv = 0;
													} else {
														kqlv = (kqmxList[i].dtljrs / kqmxList[i].zcrs * 100).toFixed(2);
													}
													var kqmxtable = '<tr>' + '<td>' + (i + 1) + '</td>'
															+ '<td><span style="font-size:12pt;">' + kqmxList[i].DUIZHANGMC +'-'+kqmxList[i].FENBAOSHANGMC + '</span></td>'
															+ '<td>' + kqmxList[i].dtljrs + '</td>' + '<td>'
															+ kqmxList[i].zcrs + '</td>' + '<td>' + kqlv
															+ '%</td>' + '</tr>'
													$(kqmxtable).appendTo($('#zuzhikqmx').find("tbody"));
												}
												$('<tr id="totalzuzhikq"></tr>').appendTo($('#zuzhikqmx').find("tbody"));
												$('#zuzhdiv').hide();
												$('#kqlist').hide();
												var biaoti= '<span class="glyphicon glyphicon-home km_color"></span> <span><a class="lbr-btn-back-home km_shouye" href="javascript:void(0);"onclick="backToHome();">首页</a></span><span class="lbr-back-to-pro-type-echart" >/<a href="javascript:void(0);"onclick="backToLastList(\''+riqi+'\');">公司出勤率</a>/' + xmmc+'/'+riqi.replace('/', '月') + '日考勤率</span>'
												$('#biaotiname').empty();
												$(biaoti).appendTo('#biaotiname');
								                sum2('zuzhikqtable tr','totalzuzhikq');
												$('#xmlistdiv').show()
												$('#zuzhikqmx').show();
												// 由JSON字符串转换为JSON对象
											},
											error : function(XMLHttpRequest, textStatus, errorThrown) {
												console.log(errorThrown);
											}
										});
									}
									
									
									var backToHome=function(){
								    	$("#xmlistdiv").hide();
								    	$('.tablediv').hide();
								    	$('#zuzhdiv').show();
								    	$('#biaotiname').html('<span class="glyphicon glyphicon-home km_color"></span><span><a class="lbr-btn-back-home km_shouye" href="javascript:void(0);" onclick="backToHome();">首页</a></span>');
								    }
									var backToLastList= function(riqi){
										$('.tablediv').hide();
										$('#kqlist').show();
										var biaoti= '<span class="glyphicon glyphicon-home km_color"></span> <span><a class="lbr-btn-back-home km_shouye" href="javascript:void(0);"onclick="backToHome();">首页</a></span><span>/'+riqi.replace('/', '月') + '日考勤率</span>'
										$('#biaotiname').empty();
										$(biaoti).appendTo('#biaotiname');
									}
									
									
									
									
		</script>
		<script type="text/javascript" src="static/js/myjs/zuzhshye.js"></script>
		
		
		