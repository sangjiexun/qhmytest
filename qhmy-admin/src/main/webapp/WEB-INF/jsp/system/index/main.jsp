﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basepath", basePath);
	
/*	Runtime lRuntime = Runtime.getRuntime();
	out.println("*** BEGIN MEMORY STATISTICS ***<br/>");
	out.println("Free Memory: "+lRuntime.freeMemory()+"<br/>");
	out.println("Max   Memory: "+lRuntime.maxMemory()+"<br/>");
	out.println("Total Memory: "+lRuntime.totalMemory()+"<br/>");
	out.println("Available Processors : "+lRuntime.availableProcessors()+"<br/>");
	out.println("*** END MEMORY STATISTICS ***");*/
	
%>  
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="top.jsp"%>
	<style type="text/css">
	.commitopacity{position:absolute; width:100%; height:100px; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.8; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
	</style>
	<style>
		ul,li{
			list-style:none;
		}
	</style>
</head>
	<body class="no-skin">
		<!-- #section:basics/navbar.layout -->
		
		<!-- 页面顶部¨ -->
		
		<!-- #section:basics/navbar.layout -->
		<div id="navbar" class="navbar navbar-default navbar-collapse h-navbar keman_hea" style="background:#34495e;">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
				$.ajaxSetup ({ cache: false });
			</script>

				<div class="navbar-header pull-left" style="margin-right:30px;">
					<!-- #section:basics/navbar.layout.brand -->
					<a href="#" class="navbar-brand keman_title" style="margin-left:6px !important;">
						<small >
							<img src="${basepath }static/ace/images/shim.png" alt=""/>
<!-- 							<i class="fa fa-user" style="color:white;font-size:24px;"></i> -->
							<span style="font-size:22px;color:#fba14d;font-family:'微软雅黑';">${pd.SYSNAME}</span>
						</small>
					</a>

					<!-- /section:basics/navbar.layout.brand -->

					<!-- #section:basics/navbar.toggle -->
					<button class="pull-right navbar-toggle navbar-toggle-img collapsed" type="button" data-toggle="collapse" data-target=".navbar-buttons,.navbar-menu">
						<span class="sr-only">Toggle user menu</span>
						<img src="${basepath }static/ace/avatars/user.jpg" alt="Jason's Photo" />
					</button>
					

					<button class="pull-right navbar-toggle collapsed" type="button" data-toggle="collapse" data-target="#sidebar">
						<span class="sr-only">Toggle sidebar</span>

						<span class="icon-bar"></span>

						<span class="icon-bar"></span>

						<span class="icon-bar"></span>
					</button>

					<!-- /section:basics/navbar.toggle -->
				</div>

				<!-- #section:basics/navbar.dropdown -->
				<div class="navbar-buttons navbar-header pull-right  collapse navbar-collapse" role="navigation">
					<ul class="nav ace-nav keman_hright">
						<li class="transparent">
							
							<div class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
								<div class="tabbable">
									<div class="tab-content">
										<div id="navbar-tasks" class="tab-pane in active">
											<ul class="dropdown-menu-right dropdown-navbar dropdown-menu">
												<li class="dropdown-content">
													<ul class="dropdown-menu dropdown-navbar">
														<li>
															<a href="#">
																<div class="clearfix">
																	<span class="pull-left">Software Update</span>
																	<span class="pull-right">65%</span>
																</div>

																<div class="progress progress-mini">
																	<div style="width:65%" class="progress-bar"></div>
																</div>
															</a>
														</li>

														<li>
															<a href="#">
																<div class="clearfix">
																	<span class="pull-left">Hardware Upgrade</span>
																	<span class="pull-right">35%</span>
																</div>

																<div class="progress progress-mini">
																	<div style="width:35%" class="progress-bar progress-bar-danger"></div>
																</div>
															</a>
														</li>

														<li>
															<a href="#">
																<div class="clearfix">
																	<span class="pull-left">Unit Testing</span>
																	<span class="pull-right">15%</span>
																</div>

																<div class="progress progress-mini">
																	<div style="width:15%" class="progress-bar progress-bar-warning"></div>
																</div>
															</a>
														</li>

														<li>
															<a href="#">
																<div class="clearfix">
																	<span class="pull-left">Bug Fixes</span>
																	<span class="pull-right">90%</span>
																</div>

																<div class="progress progress-mini progress-striped active">
																	<div style="width:90%" class="progress-bar progress-bar-success"></div>
																</div>
															</a>
														</li>
													</ul>
												</li>

												<li class="dropdown-footer">
													<a href="#">
														See tasks with details
														<i class="ace-icon fa fa-arrow-right"></i>
													</a>
												</li>
											</ul>
										</div><!-- /.tab-pane -->

										<div id="navbar-messages" class="tab-pane">
											<ul class="dropdown-menu-right dropdown-navbar dropdown-menu">
												<li class="dropdown-content">
													<ul class="dropdown-menu dropdown-navbar">
														<li>
															<a href="#">
																<img src="${basepath }static/ace/avatars/avatar.png" class="msg-photo" alt="Alex's Avatar" />
																<span class="msg-body">
																	<span class="msg-title">
																		<span class="blue">Alex:</span>
																		Ciao sociis natoque penatibus et auctor ...
																	</span>

																	<span class="msg-time">
																		<i class="ace-icon fa fa-clock-o"></i>
																		<span>a moment ago</span>
																	</span>
																</span>
															</a>
														</li>

														<li>
															<a href="#">
																<img src="${basepath }static/ace/avatars/avatar3.png" class="msg-photo" alt="Susan's Avatar" />
																<span class="msg-body">
																	<span class="msg-title">
																		<span class="blue">Susan:</span>
																		Vestibulum id ligula porta felis euismod ...
																	</span>

																	<span class="msg-time">
																		<i class="ace-icon fa fa-clock-o"></i>
																		<span>20 minutes ago</span>
																	</span>
																</span>
															</a>
														</li>

														<li>
															<a href="#">
																<img src="${basepath }static/ace/avatars/avatar4.png" class="msg-photo" alt="Bob's Avatar" />
																<span class="msg-body">
																	<span class="msg-title">
																		<span class="blue">Bob:</span>
																		Nullam quis risus eget urna mollis ornare ...
																	</span>

																	<span class="msg-time">
																		<i class="ace-icon fa fa-clock-o"></i>
																		<span>3:15 pm</span>
																	</span>
																</span>
															</a>
														</li>

														<li>
															<a href="#">
																<img src="${basepath }static/ace/avatars/avatar2.png" class="msg-photo" alt="Kate's Avatar" />
																<span class="msg-body">
																	<span class="msg-title">
																		<span class="blue">Kate:</span>
																		Ciao sociis natoque eget urna mollis ornare ...
																	</span>

																	<span class="msg-time">
																		<i class="ace-icon fa fa-clock-o"></i>
																		<span>1:33 pm</span>
																	</span>
																</span>
															</a>
														</li>

														<li>
															<a href="#">
																<img src="${basepath }static/ace/avatars/avatar5.png" class="msg-photo" alt="Fred's Avatar" />
																<span class="msg-body">
																	<span class="msg-title">
																		<span class="blue">Fred:</span>
																		Vestibulum id penatibus et auctor  ...
																	</span>

																	<span class="msg-time">
																		<i class="ace-icon fa fa-clock-o"></i>
																		<span>10:09 am</span>
																	</span>
																</span>
															</a>
														</li>
													</ul>
												</li>

												<li class="dropdown-footer">
													<a href="inbox.html">
														See all messages
														<i class="ace-icon fa fa-arrow-right"></i>
													</a>
												</li>
											</ul>
										</div><!-- /.tab-pane -->
									</div><!-- /.tab-content -->
								</div><!-- /.tabbable -->
							</div><!-- /.dropdown-menu -->
						</li>
						
						
						<li class="grey keman_lh" style="display:none">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="ace-icon fa fa-tasks"></i>
								<span class="badge badge-grey">2</span>
							</a>

							<ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="ace-icon fa fa-check"></i>
									预留功能,待开发
								</li>
								<li class="dropdown-footer">
									<a href="javascript:">
										预留功能,待开发
										<i class="ace-icon fa fa-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li>

						<li title="即时聊天" class="purple keman_lh"  onclick="creatw();" style="display:none"><!-- creatw()在 WebRoot\plugins\websocketInstantMsg\websocket.js中 -->
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="ace-icon fa fa-bell icon-animated-bell"></i>
								<span class="badge badge-important"></span>
							</a>

							<ul class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="ace-icon fa fa-bell-o"></i>
									FH Aadmin 即时通讯
								</li>
							</ul>
						</li>

						<li title="站内信" class="green keman_lh" onclick="fhsms();" id="fhsmstss" style="display:none"><!-- fhsms()在 WebRoot\static\js\myjs\head.js中 -->
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="ace-icon fa fa-envelope icon-animated-vertical"></i>
								<span class="badge badge-success" id="fhsmsCount"></span>
							</a>
						</li>
						
						
						<!-- #section:basics/navbar.user_menu -->
						<li class="light-blue user-min keman_lh">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="${basepath }static/ace/avatars/user.jpg" alt="Jason's Photo" />
<!-- 								<span class="user-info" id="user-info"> -->
<!-- 								</span> -->
								<i class="ace-icon fa fa-caret-down"></i>
							</a>

							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li style="height: 40px;">
									<a style="cursor:pointer;height:40px;line-height:30px;"><i class="ace-icon fa fa-user"></i>${username},欢迎您！</a><!-- editUserH()在 WebRoot\static\js\myjs\head.js中 -->
								</li>
								<li style="height: 40px;">
									<a onclick="editPwd();" style="cursor:pointer;height:40px;line-height:30px;"><i class="ace-icon fa fa-cog"></i>修改密码</a><!-- editUserH()在 WebRoot\static\js\myjs\head.js中 -->
								</li>
								<li class="divider"></li>
								<li>
									<a href="logout" style="cursor:pointer;height:40px;line-height:30px;"><i class="ace-icon fa fa-power-off"></i><span id="logout">退出登录</span></a>
								</li>
							</ul>
						</li>

						<!-- /section:basics/navbar.user_menu -->
					</ul>
				</div>

				<!-- /section:basics/navbar.dropdown -->
				<nav role="navigation" class="navbar-menu pull-left collapse navbar-collapse">
					<!-- #section:basics/navbar.nav -->
					
					<!-- 下拉组织项目树 -->

				<ul class="nav navbar-nav navbar-left keman_xial">
					<li class="dropdown">
					    
					    <!-- 下拉按钮区域 -->
						<a href="#" class="dropdown-toggle link-banner" data-toggle="dropdown"
							role="button" aria-expanded="false" style="height:50px !important;padding-top:14px !important;"> 
							<span class="lbr-cur-project-name" id="zzname"  style="font-size:14px !important;">${zzname}</span> 
							<span class="caret"></span>
						</a>
						<!-- end 下拉按钮区域 -->
					    
					    
						<div class="dropdown-menu" role="menu"
							style="max-height:400px; width: 400px; overflow:auto;left:0;">
							
							<!-- 搜索区域 -->
							<div  id="querydiv" class="form-group ct-dropdown-group"
								style="margin-top:8px;margin-bottom:0px;padding: 0px 10px;">
								<input
									class="form-control glyphicon glyphicon-search input-org_filter"
									placeholder="按照名称过滤" style="width: 100%!important;" id="queryinput" type="text">
									
								<button id="searchButton" style="position: absolute;right:6px;top:3px;"
									class="btn btn-link glyphicon glyphicon-search lbr-wkr-info-btn-search-in-result"
									type="button" disabled=""></button>
							</div>
							<!-- end 搜索区域 -->
                            
							<ul class="menu-tree_nav lbr-change-project ct-org_nav" id="orgxm_content">
								${treecode }
								
							</ul>

							<ul class="menu-tree_nav list-org_nav ct-org_nav"
								style="display:none;">
							</ul>
							
							<li class="ct-list-empty" style="display:none;">暂无数据</li>
						</div>
						 <script type="text/javascript">
						 $.ajaxSetup ({ cache: false });
						  //输入组织事件
					      $('#queryinput').bind('input propertychange', function(event) {
							    var keywords = document.getElementById("queryinput").value;
								$.ajax({
									type:"post",
									dataType:"json",
									async:false,
									data:{"keywords":keywords},
									url:"<%=basePath%>login/getOrgandXm.json",
									  success:function(mv){
										  var listmx = mv.list_xm;
			// 							  $("sidebar_content li").remove();
										   $('#orgxm_content').find('li').each(function(){
									      		var t = $(this).attr('t');
									      		if(t == 'clearUp'){
									         	 $(this).remove();
									      		} 
			    							});
										  $(listmx).appendTo("#orgxm_content");
									  // 由JSON字符串转换为JSON对象
									  },
									error: function (XMLHttpRequest, textStatus, errorThrown) {
									           alert(errorThrown); 
									}
								});
						 });
					      
					     
					      $('#queryinput').bind('keyup', function(event) {
					    	  if(event.keyCode==8){
					    		  var keywords = document.getElementById("queryinput").value;
								    //console.log("keyup:"+keywords);
									$.ajax({
										type:"post",
										dataType:"json",
										async:false,
										data:{"keywords":keywords},
										url:"<%=basePath%>login/getOrgandXm.json",
										  success:function(mv){
											  var listmx = mv.list_xm;
				// 							  $("sidebar_content li").remove();
											   $('#orgxm_content').find('li').each(function(){
										      		var t = $(this).attr('t');
										      		if(t == 'clearUp'){
										         	 $(this).remove();
										      		} 
				    							});
											  $(listmx).appendTo("#orgxm_content");
										  // 由JSON字符串转换为JSON对象
										  },
										error: function (XMLHttpRequest, textStatus, errorThrown) { 
										           alert(errorThrown); 
										}
									});  
					    	  }
						 });
					     
					     
					      
                        </script>
					</li>
				</ul>

				<!--
					<ul class="nav navbar-nav">
						<li>
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								选择组织
								<i class="ace-icon fa fa-angle-down bigger-110"></i>
							</a>
							
							<ul class="dropdown-menu dropdown-light-blue dropdown-caret" id="tree2">
								
							</ul>
							
						</li>
					</ul>
					-->
					<!-- end 下拉组织项目树 -->

					<!-- /section:basics/navbar.nav -->
				
				</nav>
			</div><!-- /.navbar-container -->
		</div>

		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<!-- #section:basics/sidebar.horizontal -->
			<div id="sidebar" class="sidebar  h-sidebar  navbar-collapse collapse keman_caidlist" style="margin-top:10px !important;"> 
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>

				<ul id="_navlist_ul" class="nav nav-list keman_caid">
					<!-- 遍历首页tab标签 -->
					<c:forEach items="${lMenus}" var="menu">
				  		<li class="open hover keman_caidh">
				  				<a href="${basepath }${menu.MENU_URL}?ZZJG=${ZZJG}" class="dropdown-toggle menu" style="height:49px;padding-top:8px !important;">
		                           <i class="${menu.MENU_ICON} keman_caidi"></i>
									<span class="menu-text keman_caidi">
										${menu.MENU_NAME}
									</span>
									<b class="arrow fa fa-angle-down"></b>
					  			</a>
					  			   <b class="arrow"></b>
								  <ul class="submenu">
					                   <c:forEach items="${menu.subMenu}" var="submenu">
											<li class="open hover">
												<a href="javascript:void(0);" onclick="js_method('${submenu.MENU_URL}','${zzname}','${submenu.MENU_NAME}')" class="sub">

												<i class="menu-icon fa fa-caret-right">
											    </i>
												${submenu.MENU_NAME}									
												<b class="arrow fa fa-angle-down"></b>
												</a>
												<b class="arrow"></b>
											</li>
									  </c:forEach>
								</ul>
					    </li>
                    </c:forEach>
				</ul><!-- /.nav-list -->

				<!-- #section:basics/sidebar.layout.minimize -->

				<!-- /section:basics/sidebar.layout.minimize -->
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			</div>
			
			
			<!-- /section:basics/sidebar.horizontal -->
			<div class="main-content" style="position:absolute;top:111px;left:0;right:0;bottom:0;">
				<div class="main-content-inner" style="position:absolute;top:0;left:0;right:0;bottom:0;overflow: auto;">
					<div class="page-content" id="" >
						<!--
						<div class="page-header">
							
						</div>
						-->
						
						<div class="row" >
							<div class="col-xs-12" >
								<!-- 左边栏 -->
								<div id="_index_left-region" class="left-region shadow" style="width: 220px;height:800px;display:none;position:fixed;top:123px;" >
									<!-- 搜索 -->
						            <div style="margin: 10px 0px;width:195px;">
						                <div class="form-group ct-dropdown-group">
						                    <input type="text" class="form-control ct-dropdown-input lbr-home-pro-qry query-input keman_lesea"  id="XMMC" placeholder="搜项目名称">
						                    <input type="hidden" class="form-control ct-dropdown-input lbr-home-pro-qry query-input keman_linp"  id="zzjg" value="${ZZJG}">

						                    <!-- 搜索按钮 -->
						                    <button type="button" class="btn btn-link glyphicon glyphicon-search  lbr-home-btn-search" style="position: absolute; right: 3px;top: 3px;"></button>
						                    <!-- 删除按钮 默认隐藏 -->
						                    <button type="button" class="btn btn-link glyphicon glyphicon-remove  lbr-home-btn-remove" style="position: absolute;right: 20px;top: 12px;display: none;"></button>
						                </div>
						            </div>
						            <!-- end 搜索 -->
						            
						            <div style="overflow-y: auto;width: 90%;max-height:92%;margin-left: 10px;" >
						                <ul class="list-group nav ct-nav-list project-list keman_ul" style="margin-top:0;" id="sidebar_content">
											<li class="list-group-item" id="allpro">
												<div style="width:186px;cursor:pointer"
													title="全部项目" class="text-overflow">全部项目</div>
											</li>
										<c:forEach items="${xms}" var="xm">
											<c:if test="${xm.isshouquan==true}">
											 <c:choose>
												<c:when test="${xm.outyxq==false}">
													<li class="list-group-item">
														<div style="width:186px;cursor:pointer"
															title="${xm.XIANGMUBM }" class="text-overflow leftxm">${xm.XIANGMUMC }</div>
													</li>
												</c:when>
												<c:otherwise>
													<li class="list-group-item">
														<div style="width:186px;cursor:pointer"
															title="${xm.XIANGMUBM }" class="text-overflow">${xm.XIANGMUMC }(过期)</div>
													</li>
												</c:otherwise>
											 </c:choose>
											</c:if>
											<c:if test="${xm.isshouquan==false}">
												<li class="list-group-item">
														<div style="width:186px;cursor:pointer"
															title="${xm.XIANGMUBM }" class="text-overflow">${xm.XIANGMUMC }(未授权)</div>
													</li>
											</c:if>
										</c:forEach>
									</ul>

						            	<div class="ct-list-empty" style="display: none;">暂无数据</div>
						            </div>
								<script type="text/javascript">
									try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
									
																			/* function toserch(){
																			} */
																			/* $('#sidebar_content').find('li').each(function(){
																				$(this).click(function(){
																					
																					$('#zuzhishye').hide();
																					$('#xmshye').show();
																				})
																			}) */
																		</script>
								<div class="split-btn">
						                <i class="ace-icon glyphicon glyphicon glyphicon-chevron-left"></i>
						            </div>
						            
						        </div>
						        <!-- end 左边栏 -->
								
			<!-- 图表 统计 -->					
								<!-- PAGE CONTENT BEGINS -->
								<div id="right-region" style="max-height:92%;margin-left: 10px;margin-top: 0px;" class="">
				             
									<!-- 项目首页 -->
									<div id="nameli">
									<c:if test="${leixing=='ORG' }">
										<div id=zuzhishye style="display:block;">
											<%@ include file="zuzhi.jsp"%>
										</div>
									</c:if>

									<div id="xmshye" style="display:block">
									<div class="km_xiangmmc">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${ pd.xmmc}<a id ="pinjie" href="#"></a></div>
									<div class="km_outbox" style="background:#f9f9f9;">
										
										<div class="km_leftbox">
											<div class="km_lefttop">
												<ul style="margin: 20px;">
													<li class="km_shis"  title="当前在施工区的实时作业人数">
														<h1>${pd.ssrs}</h1>
														<p>现场实时人数</p>
													</li>
													<li class="km_leij">
														<h1>${pd.ljrs}</h1>
														<p>日累计进场</p>
													</li>
													<li class="km_zc" title="当前项目登记未退场的人数">
														<h1>${pd.zcrs }</h1>
														<p>项目在场人数</p>
													</li>
													<div class="clearfix"></div>
												</ul>
												<div style="width: 95%;margin: 0 auto;">
													<div id='renshutj' style="height:400px;width:100%;"></div>
												</div>
											</div>
											<div class="km_lbottom">
												<div class="km_xtitle">最近一周出勤率</div>
												<div style="width: 95%;margin: 0 auto;">
													<div id='chuqltj' style="height:400px"></div> 
												</div>
											</div>
										</div>
										<div class="km_rightbox">
											<div class="km_rtop">
												<div class="km_yuj">
													<img src="${basepath }static/ace/images/shouye/yujing.png" />
												</div>
												<div class="km_yjtext">
													<p>风险预警</p>
													<h2>${pd.totalfengxian }条</h2>
												</div>
												<div class="km_xial" id="zhankai">
													<img src="${basepath }static/ace/images/shouye/xiala.png"
														alt="" id="xiala"/>
													<img src="${basepath }static/ace/images/shouye/fuwei.png"
														alt="" style="display:none" id="fuwei"/>
												</div>
												<div class="clearfix"></div>
												<ul class="km_tishi" id="fx" style="display:none;margin:0 !important;">
												</ul>
											</div>
											<div class="km_rcenter">
												<p class="km_gongz">现场工人工种分布</p>
												<div class="km_shijin">
													<input type="button" value="实时" class="gzbt" id='ssgz'/> 
													<input type="button" value="今日" class="gzbt" id='jrgz'/>
												</div>
												<div class="clearfix"></div>
												<div style="width: 95%;margin: 0 auto;">
													<div id='gongzhongtj' style="height:400px"></div>
												</div>
											</div>
											<div class="km_dongt">&nbsp;&nbsp;&nbsp;最新动态</div>
											<ul class="km_list">
											<c:if test="${!empty pd.zxdtlist}">
											<c:forEach items="${pd.zxdtlist}" var="i"  varStatus="status">
												<li class="km_lhang"><span class="km_zhaop"><img
														src="${basepath }static/ace/images/shouye/zhaop.png"
														alt="" /></span>
													<div class="km_time">
														<div style="margin-bottom: 5px;font-size:16px;">
															${i.XINGMING }<span></span>
														</div>
														<div style="color: #b0b0b0;font-size:14px;">${i.CJSJ }</div>
													</div>
													<div class="km_zhuangt">${i.OPERTYPE }</div>
													<div class="clearfix"></div></li>
											</c:forEach>
											</c:if>
											<c:if test="${empty pd.zxdtlist}">
										            <div style="text-align: center;">暂无数据</div>
											</c:if>
											
											</ul>
										</div>
										<div class="clearfix"></div>
									</div>
									<div class="clearfix"></div>
									</div>
									<div class="ct-body lbr-page-body body-view-pro-team-workType-list" style="display: none;" id="bigdiv">
                    <div class="ct-filter-bar" style="font-size: 16pt;padding-left:5px" id="biaoti">
                        <span class="glyphicon glyphicon-flag km_color"></span>
                        <a class="lbr-back-to-pro-workType-home-name km_shouye" href="javascript:void(0);" onclick="gohome('${pd.xmmc }');" id="bt">${pd.xmmc }</a>
                        /
                    </div>

		<div class="panel-body lbr-pro-team-summary-list"style="display:none;" id="dwrs">
			<table class="ct-table table table-hover fixed" id="dwtable">
				<thead class="tall">
				<tr>
				<th width="50">序号</th>
				<th width="150">队伍</th>
				<th width="80">现场实时人数</th>
				<th width="80">日累计进场人数</th>
				<th width="80">队伍在场人数</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${pd.dWuList}" var="i"  varStatus="status">  
				   <tr>
				   <td>${status.count}</td>
				   <td><span style="font-size:12pt;">${i.DUIZHANGMC}-${i.FENBAOSHANGMC}</span></td>
				   <td>${i.ssrs}</td>
				   <td>${i.ljrs}</td>
				   <td>${i.zcrs}</td>
				   </tr>
   			       </c:forEach> 
   			       <tr id="totaldw"></tr>
				</tbody>
			</table>
		</div>
		<div class="panel-body lbr-pro-team-summary-list"style="display:none;" id="kqmx">
			<table class="ct-table table table-hover fixed" id="kqtable">
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
		<div class="panel-body lbr-pro-team-summary-list"style="display:none;" id="gzmx">
			<table class="ct-table table table-hover fixed" id="gztable">
				<thead class="tall">
				<tr>
				<th width="50">序号</th>
				<th width="150">工种</th>
				<th width="80">现场实时人数</th>
				<th width="80">日累计进场</th>
				<th width="80">项目在场人数</th>
				</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		
		<div class="panel-body lbr-pro-team-summary-list"style="display:none;" id="bzmx">
			<table class="ct-table table table-hover fixed" id="bzmxtable">
				<thead class="tall">
				<tr>
				<th width="50">序号</th>
				<th width="150">队伍/班组</th>
				<th width="80">现场实时人数</th>
				<th width="80">日累计进场</th>
				<th width="80">项目在场人数</th>
				</tr>
				</thead>
				<tbody>
				</tbody>
				<tfoot></tfoot>
			</table>
		</div>
		</div>							  
		</div>
		

		
		
									</div>
								</div>
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
							
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div>
			

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		

		<!-- basic scripts -->
		<!-- 页面底部js¨ -->
		<%@ include file="foot.jsp"%>
		
		<!-- page specific plugin scripts -->

		<!-- ace scripts -->

		<!-- inline scripts related to this page -->

		<%@ include file="baseScript.jsp"%>

	
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="static/js/myjs/head.js"></script>
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="static/js/myjs/left-region.js"></script>
		<script type="text/javascript" src="${basepath }static/js/myjs/index.js"></script>
		
		<!-- 文件上传 -->
		<script type="text/javascript" src="static/js/stream-v1.js"></script>
		<!-- end 文件上传 -->
		
		<!-- 从表的附件上传 二次封装 -->
		<script type="text/javascript" src="${basepath }static/js/myjs/uploadZcjg.js"></script>
		<!-- end 从表的附件上传 二次封装 -->
		
		<!-- 资质附件上传-->
		<script type="text/javascript" src="${basepath }static/js/myjs/team_register_uploadZcjg.js"></script>
		
		<!-- 引入echarts -->
        <script src="static/js/myjs/echarts.min.js"></script>
		<script type="text/javascript">
		    var leixing="${leixing}"
		    var _basepath = "${basepath}";
   			var gztjlist="${pd.gztjlist}"
   			var kaoqinList="${pd.kaoqinList}"
   			var gzarray = new Array();  
   			
   			var bzrule="${pd.yujingpd.CHUQIN_BANZU}";
   			var dwrule="${pd.yujingpd.CHUQIN_DUIWU}";
   			var gongzhrule="${pd.yujingpd.CHUQIN_GONGZHONG}"
   			var lianxutianshu="${pd.yujingpd.GONGRENZCSJ_TIAN}"
   			var lianxuxiaoshi="${pd.yujingpd.GONGRENZCSJ_XIAOSHI}"
   			var bzyjcount="${pd.bzyjcount}"
   			var duiwucount="${pd.duiwucount}"
   			var yjwcc="${pd.yjwcc}"
   			var lxwccrs="${pd.lxwccrs}"
   			var gongzhcount="${pd.gongzhcount}"
   			var bianma="${XIANGMU_BIANMA}"
   			var isneedsafeedu="${pd.isneedsafeedu}"
   			var totalfengxian="${pd.totalfengxian}"
   			//项目实时工种
   			<c:forEach items="${pd.gztjlist}" var="t">  
   			var a={MINGCHENG:'${t.MINGCHENG}',RENSHU:${t.RENSHU}}
   			gzarray.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
   			</c:forEach> 
   			//项目今日工种
   			var jrgzarray=new Array();
   			<c:forEach items="${pd.jinrigongZhongList}" var="t">  
   			var a={MINGCHENG:'${t.MINGCHENG}',RENSHU:${t.RENSHU}}
   			jrgzarray.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
   			</c:forEach> 
   			
   			var gzarray1=new Array();
   			<c:forEach items="${pd.gzList}" var="t">  
   			var a={BIANMA:'${t.BIANMA}',MINGCHENG:'${t.MINGCHENG}',ssrs:${t.ssrs},ljrs:${t.ljrs},zcrs:${t.zcrs},XIANGMU_BIANMA:'${t.XIANGMU_BIANMA}'}
   			gzarray1.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
   			</c:forEach> 
   			
   			
   			
   			//考勤率图标用到的数组
   			var kqlarray=new Array();
   			<c:forEach items="${pd.kaoqinList}" var="i">  
   			var b={riqi:'${i.riqi}',kaoqinlv:${i.kaoqinlv}};
   			kqlarray.push(b); //js中可以使用此标签，将EL表达式中的值push到数组中  
   			</c:forEach> 
   			
   			//队伍人数 折线图用到的数组
   			var dwarray=new Array();
   			<c:forEach items="${pd.dWuList}" var="i">  
   			var b={DUIZHANGMC:'${i.DUIZHANGMC}',ssrs:${i.ssrs},ljrs:${i.ljrs},zcrs:${i.zcrs},FENBAOSHANGMC:'${i.FENBAOSHANGMC}'};
   			dwarray.push(b); //js中可以使用此标签，将EL表达式中的值push到数组中  
   			</c:forEach>
   			//默认是取出项目下所有的队伍，这里按照实时人数降序排序，实现队伍在场人数多的在最左侧显示，最少的在最右侧显示
			dwarray.sort(function(a, b) {
				return b.ssrs - a.ssrs
			});

			//合计行方法
			var sum = function(tableid, zongjiid) {
				var total1 = 0;
				var total2 = 0;
				var total3 = 0;
				$("#" + tableid).each(function() {
					$(this).find('td:eq(2)').each(function() {
						total1 += parseFloat($(this).text());
					});
					$(this).find('td:eq(3)').each(function() {
						total2 += parseFloat($(this).text());
					});
					$(this).find('td:eq(4)').each(function() {
						total3 += parseFloat($(this).text());
					});
				});
				if (zongjiid == 'totalkq') {
					var percent=total2==0?0:(total1*100/total2).toFixed(2)
					$("#" + zongjiid).append(
							'<td></td><td>合计</td><td>' + total1 + '</td><td>'
									+ total2 + '</td><td>'
									+ percent
									+ '%</td>');
					return;
				}
				$("#" + zongjiid).empty();
				$("#" + zongjiid).append(
						'<td></td><td><span style="font-size:12pt;">合计</span></td><td>'
								+ total1 + '</td><td>' + total2 + '</td><td>'
								+ total3 + '</td>');

			};

			var xmmc = "${pd.xmmc}"

			var a = function(e, f, g) {
				var gzbianma = e;
				var gzmc = g;
				$.get(
								_basepath + "main/index.do?gzbianma="
										+ gzbianma + "&xiangmubm=" + f,
								function(mv) {
									console.log(mv)
									var dwList = mv.fenbsList

									$('#gzmx').hide();
									for (var i = 0; i < dwList.length; i++) {
										var kqv = 0;
										var gzmxtr = '<tr>'
												+ '<td>'
												+ (i + 1)
												+ '</td>'
												+ '<td><span style="font-size:12pt;font-weight: bolder;">'
												+ dwList[i].DUIZHANGMC + '-'
												+ dwList[i].FENBAOSHANGMC
												+ '</span></td>' + '<td>'
												+ dwList[i].ssrs + '</td>'
												+ '<td>' + dwList[i].ljrs
												+ '</td>' + '<td>'
												+ dwList[i].zcrs + '</td>'
												+ '</tr>'
										$(gzmxtr).appendTo(
												$('#bzmx').find("tbody"));

										var bzList = dwList[i].bzList
										for (var j = 0; j < bzList.length; j++) {

											var bzmxtr = '<tr class="fenzhi">'
													+ '<td>'
													+ (i + 1)
													+ '.'
													+ (j + 1)
													+ '</td>'
													+ '<td><span style="font-size:12pt;">'
													+ bzList[j].MINGCHENG
													+ '</span></td>' + '<td>'
													+ bzList[j].ssrs + '</td>'
													+ '<td>' + bzList[j].ljrs
													+ '</td>' + '<td>'
													+ bzList[j].zcrs + '</td>'
													+ '</tr>'
											$(bzmxtr).appendTo(
													$('#bzmx').find("tbody"));

										}

									}
									$('#xmshye').hide();
									var biaoti = '<span class="lbr-back-to-pro-type-echart">/'
											+ gzmc + '</span>'
									$(biaoti).appendTo('#biaoti')
									$('<tr id="totalgz2"></tr>').appendTo(
											$('#bzmx').find("tfoot"));
									sum('bzmxtable tr:not(.fenzhi)', 'totalgz2');//避免重复计算，所以这里不计算分支tr
									$('#gzmx').hide();
									$('#bigdiv').show();
									$('#bzmx').show();
								})
			}
			
			var gohome=function(xmmc){
				$('.lbr-pro-team-summary-list').hide();
				$('#xmshye').show();
				$('#bigdiv').hide();
				var biaoti= '<span class="glyphicon glyphicon-flag km_color"></span>'
				           +'<a class="lbr-back-to-pro-workType-home-name km_shouye" href="javascript:void(0);" onclick="gohome(\''+xmmc+'\');">'+xmmc+'/</a>'
				$('#biaoti').empty();
				$(biaoti).appendTo('#biaoti');
			};
			var backto=function(){
				var xmmc="${pd.xmmc}";
				$('.lbr-pro-team-summary-list').hide();
				$('#gzmx').show();
				var biaoti= '<span class="glyphicon glyphicon-flag km_color"></span>'
			           +'<a class="lbr-back-to-pro-workType-home-name km_shouye" href="javascript:void(0);" onclick="gohome(\''+xmmc+'\');">'+xmmc+'/</a><span class="lbr-back-to-pro-type-echart" onclick="backto();">现场工人工种分布情况</span>'
			$('#biaoti').empty();
			$(biaoti).appendTo('#biaoti');
			}
			
			var flag=true;
			 function editPwd() {
					
				    var blg= BootstrapDialog.show({  //显示需要提交的表单。
				   	title:'修改密码',
				    message: $('<div></div>').load('edit/goEditPwd.json'),
				    closable: false, 
				      buttons: [{
					    label: '提交',
					    cssClass: 'btn-danger',
					    action: function(dialogRef){
				        savePwd();
				        if(flag==true){
				        	dialogRef.close();
				        }
				             
				        // $("#tableforboot").bootstrapTable('refresh', {url:"<%=basePath%>gongzuozhan/tablejson.json"});
				        }
				  }, {
				    label: '关闭',
				    cssClass: 'btn-warning keman_btnx',
				    action: function(dialogRef){
				       dialogRef.close();
				    }
				  }
				  ]
				    });
				};
			
				//保存密码
		   function savePwd(){
					var reg=/^\s+$/g;
		   var oldpwd=$("#oldpwd").val();
		   var newpwd=$("#newpwd").val();
		   var confirmpwd=$("#confirmpwd").val();
		   if(oldpwd.length == 0 || reg.test(oldpwd)){
                layer.msg("请输入原密码！")
			   flag=false;
			   return;
		   }
		   if(newpwd.length == 0 || reg.test(newpwd)){
			   layer.msg("请输入新密码")
			   flag=false;
			   return;
		   }
		   if(confirmpwd.length == 0 || reg.test(confirmpwd)){
			   layer.msg("请再次输入新密码")
			   flag=false;
			   return;
		   }
		   if(newpwd!=confirmpwd){
			   layer.msg("两次新密码输入不一致！")
			   flag=false;
			   return;
		   }
			$.ajax({
				type : "post",
				
				url : "<%=basePath%>edit/saveNewPwd.json",
				data:{oldpwd:oldpwd,newpwd:newpwd,confirmpwd:confirmpwd},
				success : function(data) {

					if ("success" == data.result) {
						 BootstrapDialog.show({
								title : '提示信息',
								message : '修改成功,为了你的账户安全请使用新密码重新登录(5s后自动退出)！',
								closable : true,
								buttons : [ {
									label : '关闭',
									cssClass : 'btn-warning keman_btnx',
									action : function(dialogRef) {
										dialogRef.close();
									}
								} ]
							});
						 setTimeout(function(){$('#logout').click();},5000);
						
					}
					if ("faild" == data.result) {
						layer.msg("原密码输入有误！")
						   flag=false;
						   return;
						 }
				  },
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(errorThrown);
				}
			});

		}
			
		</script>
		<script type="text/javascript" src="static/js/myjs/shouyetj.js"></script>
		<script type="text/javascript">
			function js_method(namelist,zzname,menuname){
				// alert(evnet);
				//stopPropagation
				// var e=evt||window.event;
				 //非首页，隐藏侧边栏
 				 yincang_cebianlan();//隐藏侧边栏
				 $.ajaxSetup({
	 					cache:false
 				 });
				if (menuname=='规则设置') {
				 	$("#nameli").load("<%=basePath%>"+namelist);
				}else{
					var date = new Date();
			    	$("#nameli").load("<%=basePath%>"+namelist);
				}
 				 
			}
		</script>
		
	</body>
</html>