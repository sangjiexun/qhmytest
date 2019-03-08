<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>编辑学生</title>
		<link rel="stylesheet" href="${basepath}static/gxjf/bs/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${basepath}static/gxjf/css/Employee.css" />
		<link rel="stylesheet" href="${basepath}static/gxjf/css/framework-font.css" />
		<link href="${basepath}static/js/layer/skin/default/layer.css" rel="stylesheet">
	    <!-- 日期框 -->
		<link rel="stylesheet" href="${basepath}static/ace/css/datepicker.css" />
		<link href="${basepath}static/js/layer/skin/default/layer.css" rel="stylesheet">
		<script src="${basepath}static/ace/js/date-time/bootstrap-datepicker.js"></script>
		<!--身份证校验  -->
		<script type="text/javascript" src="${basepath}static/js/cardcheck.js"></script>
	    
		
		<!-- ztree所需css以及js -->
        <link rel="stylesheet" href="${basepath}static/zTree/css/zTreeStyle/metro.css">
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="${basepath}static/zTree/js/jquery.ztree.excheck.js"></script>
		
		
		<style type="text/css">
			html { overflow-x:hidden; }
			.jf_label{
				width: 75px;
				text-align: right;
			}
			.form-control{
				width:170px !important;
			}
			.jf_DetailsList{
				width:1020px;
				margin-left:115px;
			}
			.form-inline .form-group{
				margin-right:25px;
			}
			.jf_ZhaoPian{
				position:absolute;
			}
			.jf_label{
				width:126px;
			}
			 .zhuanYe{
			 	width: 126px;
			    display: inline-block;
			    padding-left: 50px;
			 }
		</style> 
		<script type="text/javascript">
              var _basepath = "${basepath}";
              var _imgsrc="${pd.TOUXIANG}";
        </script>
	</head>
	<body>
				<div class="jf_ZhaoPian">
					<img src="" alt="" width="100%;" id="touxiang" style="width:99px;height:133px;"/>
					<a href="javascript:void(0);" id="btn_tx"><div class="jf_ImgUploading"><p>上传照片</p></div></a>
					<c:if test="${pd.PKID!=null}" >
					    <a href="javascript:void(0);" class="btn btn-danger" style="margin-top: 15px;" id="btn_resetpwd">初始化密码</a>
					</c:if>
					
				</div>
				<div class="jf_DetailsList">
					<div class="panel panel-info">
						<div class="jf_PanelHead panel-heading">
							<div class="jf_PanelTitle panel-title">
								个人信息
							</div>
						</div>
						<div class="panel-body" >
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label jf_xing">身份证：</label>
									<input type="text" name="" id="sfz" class="form-control"  <c:if test="${pd.isEdit == 'isEdit'}">readonly="readonly"</c:if>   placeholder="请输入内容" value="${pd.SHENFENZHENGHAO}" maxlength="25"/>
								</div>
								
								<div class="form-group">
									<label class="jf_label jf_xing">出生日期：</label>
									<input type="text" name="" id="csrq" class="form-control" placeholder="请输入内容"  readonly="readonly"
									   value='<fmt:formatDate value="${pd.CHUSHENGRIQI}" pattern="yyyy-MM-dd" />' maxlength="25"/>
								</div>
								<div class="form-group">
									<label class="jf_label jf_xing" for="">性别：</label>
									<input type="text" name=""  id="xingbie" class="form-control" placeholder="请输入内容"  readonly="readonly"  value="${pd.XINGBIE}" />
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label jf_xing" >姓名：</label>
									<input type="text" name="" id="name" class="form-control" placeholder="请输入内容" value="${pd.XINGMING}" maxlength="25"/>
								</div>
								<div class="form-group">
									<label class="jf_label jf_xing" for="">联系电话：</label>
									<input type="text" name="" id="phone" class="form-control" placeholder="请输入内容" value="${pd.SHOUJI}"maxlength="100"/>
								</div>
								<div class="form-group">
									<label class="jf_label jf_xing" for="">民族：</label>
	                                <select name="" class="form-control" id="minzu" placeholder="请输入内容" style="width:183px;">
	                                <option value="" <c:if test="${pd.NATION_PKID==null||pd.NATION_PKID==''}">selected</c:if>>请选择民族</option>
	                                  	<c:forEach items="${minzuList}" var="var">
	                                  		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.NATION_PKID}">selected</c:if>>${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>								
								<!-- 有用 勿删 -->
								<div style="display:none;">
									<input type="text" name="" id="pkid" class="form-control"  value="${pd.PKID }"/>
								</div>
								<!--end 有用 勿删 -->
							</div>
							<div class="form-inline">
								
								<div class="form-group">
									<label class="jf_label" for="">政治面貌：</label>
	                                <select name="" class="form-control" id="zhengzhimianmao" placeholder="请输入内容" style="width:183px;">
	                                <option value="" <c:if test="${pd.ZHENGZHIMIANMAO_PKID==null||pd.ZHENGZHIMIANMAO_PKID==''}">selected</c:if>>请选择政治面貌</option>
	                                  	<c:forEach items="${zzmmList}" var="var">
	                                  		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.ZHENGZHIMIANMAO_PKID}">selected</c:if>>${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">健康状态：</label>
	                                <select name="" class="form-control" id="jkzk" placeholder="请输入内容" style="width:183px;">
	                                <option value="" <c:if test="${pd.JKZK_PKID==null||pd.JKZK_PKID==''}">selected</c:if>>请选择健康状况</option>
	                                  	<c:forEach items="${jkzkList}" var="var">
	                                  		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.JKZK_PKID}">selected</c:if>>${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">户口性质：</label>
	                                <select name="" class="form-control" id="hukouxingzhi" placeholder="请输入内容" style="width:183px;">
	                                <option value="" <c:if test="${pd.HUKOUXINGZHI_PKID==null||pd.HUKOUXINGZHI_PKID==''}">selected</c:if>>请选择户口性质</option>
	                                  	<c:forEach items="${hkxzList}" var="var">
	                                  		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.HUKOUXINGZHI_PKID}">selected</c:if>>${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>								
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label">邮政编码：</label>
									<input type="text" name="" id="yzbm" class="form-control" placeholder="请输入内容" value="${pd.YOUZHENGBIANMA}" maxlength="25"/>
								</div>
								
								<div class="form-group">
									<label class="jf_label">家庭成员姓名：</label>
									<input type="text" name="" id="jtcyxm" class="form-control" placeholder="请输入内容" value="${pd.JTCYXM}" maxlength="25"/>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">家庭成员联系电话：</label>
									<input type="text" name="" id="lxdh"  class="form-control" placeholder="请输入内容" value="${pd.JTCYLXDH}" maxlength="25"/>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label" for="">家庭成员关系：</label>
	                                <select name="" class="form-control" id="jtcygx" placeholder="请输入内容" style="width:183px;">
	                                <option value="" <c:if test="${pd.JTCYGX_PKID==null||pd.JTCYGX_PKID==''}">selected</c:if>>请选择家庭成员关系</option>
	                                  	<c:forEach items="${jtgxList}" var="var">
	                                  		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.JTCYGX_PKID}">selected</c:if>>${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>
								<div class="form-group">
									<label class="jf_label jf_xing" for="">家庭地址：</label>
									<input type="text" name="" id="jiatingzhuzhi" class="form-control"  placeholder="请输入内容" 
									<c:if test="${pd.isEdit == 'isEdit'}">readonly="readonly"</c:if>
									value="${pd.JIATINGZHUZHI}" style="width:498px  !important;"/>
								</div>
							</div>
							<div class="form-inline">								
								<div class="form-group">
									<label class="jf_label" for="">学号：</label>	                            
	                        		<input type="text" name="" id="xuehao" class="form-control"  <c:if test="${pd.isEdit == 'isEdit'}">readonly="readonly"</c:if>  placeholder="请输入内容" value="${pd.XUEHAO}"/>
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">是否贫困：</label>
	                                <select name="" class="form-control" id="poorstu" placeholder="请输入内容" style="width:183px;">
	                                <option value="" <c:if test="${pd.POOR_PKID==null||pd.POOR_PKID==''}">selected</c:if>>请选择是否贫困</option>
	                                  	<c:forEach items="${sfpkList}" var="var">
	                                  		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.POOR_PKID}">selected</c:if>>${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>								
								<div class="form-group">
									<label class="jf_label jf_xing" for="">中学名称：</label>
									<input type="text" name="" id="zxmc" class="form-control" placeholder="请输入内容" value="${pd.ZXMC}"/>
								</div>								
							</div>
							<div class="form-inline">								
								<div class="form-group">
									<label class="jf_label" for="">考生号：</label>	                            
	                        		<input type="text" name="" id="kaoshenghao" class="form-control"  <c:if test="${pd.isEdit == 'isEdit'}">readonly="readonly"</c:if> placeholder="请输入内容" value="${pd.KAOSHENGHAO}"/>
								</div>								
								<div class="form-group" style="margin-top:10px;">
									<label class="jf_label" for="">考生特长：</label>
									<c:forEach items="${kstcList}" var="var"> 
										<input type="checkbox" name="checkbox" value="${var.BIANMA}"/> ${var.NAME}
									</c:forEach>																	
								</div>							
								<div class="form-group" style="margin-top:10px;">
									<label class="jf_label" for="">特殊标记：</label>
									<c:forEach items="${stu_FlagList1}" var="var"> 
										<input type="checkbox" name="checkbox1" value="${var.BIANMA}"/> ${var.NAME}
									</c:forEach>																	
								</div>							
							</div>
					</div>
				</div>
				<div class="panel panel-info">
						<div class="jf_PanelHead panel-heading">
							<div class="jf_PanelTitle panel-title">
								学生信息
							</div>
						</div>
						<div class="panel-body">
							<div class="form-inline">								
								<div class="form-group">
									<label class="jf_label jf_xing">入学年份：</label>
                                    <select name=""  class="form-control" id="grade" disabled="disabled" style="width:183px;">
                                    	<c:choose> 
                                    		  <c:when test="${pd.NIANJI!=null}"> 
                                    		  	<c:forEach items="${gradelist}" var="grade">
                                    				<option value="${grade.DICTIONARIES_ID }" bianma="${grade.BIANMA}" <c:if test="${grade.BIANMA == pd.NIANJI }">selected</c:if>>${grade.NAME }</option>
                                    			</c:forEach>
                                    		  </c:when>
                                    		  <c:otherwise>
                                    		  	<c:forEach items="${gradelist}" var="grade">
                                    				<option value="${grade.DICTIONARIES_ID }" bianma="${grade.BIANMA}" <c:if test="${grade.BIANMA == '18级' }">selected</c:if>>${grade.NAME }</option>
                                    			</c:forEach>
                                    		  </c:otherwise>
                                    	
                                    	</c:choose>
                                    	
			                        </select>
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">录取专业：</label>
									<input type="text" name="" id="luquzhuanye"  class="form-control" placeholder="请输入内容"
									<c:if test="${pd.isEdit == 'isEdit'}">readonly="readonly"</c:if> value="${pd.LUQUZHUANYE}"/>
								</div>								
								<div class="form-group" style="margin-left:5px;">
					                 <div class="zTreeDemoBackground left ">
										<ul class="list" style="margin-bottom:0;">
											<li class="jf_ZtLaber title"><span class="jf_xing zhuanYe">院校专业：</span> <input class="form-control" id="citySel" type="text"  <c:if test="${pd.ZHUANYEBIANDONG != '0'}" >disabled="disabled"</c:if>
											disabled="disabled" readonly value="${pd.ZUZHINAME }" style="width:183px;" onclick="showMenu();" />
												<div id="menuContent" class="menuContent jf_ZtBox" style="display:none; position: absolute; z-index:1;">
													<ul id="treeDemo" class="ztree" style="margin-top:0; width:300px; max-height: 300px;overflow:auto;"></ul>
												</div>
											</li>
										</ul>
									</div>
									<div style="display:none;">
								        <!--用来存储 树节点的id -->
									    <input type="text" name="tree" id="orgtree" class="form-control" value="${pd.DEPARTMENT_PKID}"/>
								    </div>
						        </div>	
																
						  </div>
						  <div class="form-inline">
								<div class="form-group">
									<label class="jf_label jf_xing" for="">学生类型：</label>
									<select name=""<c:if test="${pd.PKID!=null}" >disabled="disabled"</c:if> class="form-control" id="CENGCI" placeholder="请输入内容" style="width:183px;">
		                                 	<c:forEach items="${cengci_list}" var="var">
		                                 		<option value="${var.PKID}" <c:if test="${var.PKID == pd.CENGCI_PKID}">selected</c:if>>${var.CENGCI_NAME}</option>
		                                 	</c:forEach>
		                       		</select>
								</div>
								<div class="form-group">
									<label class="jf_label jf_xing" for="">批次：</label>
		                               <select name=""<c:if test="${pd.PKID!=null}" >disabled="disabled"</c:if> class="form-control" id="PICI" placeholder="请输入内容" style="width:183px;">
		                                 	<c:forEach items="${pici_list}" var="var">
		                                 		<option value="${var.PKID}" <c:if test="${var.PKID == pd.PICI_PKID}">selected</c:if>>${var.PICI_NAME}</option>
		                                 	</c:forEach>
		                       		</select>
								</div>							
								<div class="form-group">
									<label class="jf_label" for="">投档成绩：</label>
									<input type="text" name="" id="toudangchengji" class="form-control" placeholder="请输入内容" 
									<c:if test="${pd.isEdit == 'isEdit'}">readonly="readonly"</c:if> value="${pd.TOUDANGCHENGJI}"/>
								</div>								
						  </div>
						  <div class="form-inline">
								
								<div class="form-group">
									<label class="jf_label" for="">学制：</label>
	                                <select name="" class="form-control" id="xuezhi" <c:if test="${pd.isEdit == 'isEdit'}">disabled="disabled"</c:if>  placeholder="请输入内容" style="width:183px;">
	                                <option value="" <c:if test="${pd.XUEZHI_PKID==null||pd.XUEZHI_PKID==''}">selected</c:if>>请选择学制</option>
	                                  	<c:forEach items="${cengci_list}" var="var">
	                                  		<option value="${var.PKID}" <c:if test="${var.PKID == pd.XUEZHI_PKID}">selected</c:if>>${var.XUENIANZHI}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>								
								<div class="form-group">
									<label class="jf_label jf_xing" for="">科别：</label>
	                                <select name="" class="form-control" id="kebie" <c:if test="${pd.isEdit == 'isEdit'}">disabled="disabled"</c:if> placeholder="请输入内容" style="width:183px;">
	                                <option value="" <c:if test="${pd.KELEI_PKID==null||pd.KELEI_PKID==''}">selected</c:if>>请选择科别</option>  	
	                                  	<c:forEach items="${keleiList}" var="var">
	                                  		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.KELEI_PKID}">selected</c:if>>${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">学籍类型：</label>
	                                <select name="" class="form-control" id="SCHOOLROLL" <c:if test="${pd.isEdit == 'isEdit'}">disabled="disabled"</c:if> placeholder="请输入内容" style="width:183px;">
	                                <option value="" <c:if test="${pd.XUEJI_PKID==null||pd.XUEJI_PKID==''}">selected</c:if>>请选择学籍类型</option>  	
	                                  	<c:forEach items="${schoolrollList}" var="var">
	                                  		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.XUEJI_PKID}">selected</c:if>>${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>															
					    </div>
						<div class="form-inline">
								<div class="form-group">
									<label class="jf_label" for="">所在班级：</label>
									
									<select name="" class="form-control" id="banji" placeholder="请输入内容" style="width:183px;">
										<option value=''>请选择班级...</option>
	                                  	<c:if test="${pd.PKID!=null}" >
	                                  		<c:forEach items="${banJiList}" var="var">
		                                  		<option <c:if test="${pd.BJ_PKID==var.PKID}">selected="selected"</c:if> value="${var.PKID}" >${var.CLASS_NAME}</option>
	                                  		</c:forEach>
	                                  	</c:if>
	                        		</select>
								</div>							
								<div class="form-group">
									<label class="jf_label" for="">在学状态：</label>
                                    <select name=""  class="form-control" id="zaixuezhuangtai" disabled="disabled" placeholder="请输入内容" style="width:183px;">
                                    <option value="" <c:if test="${pd.ZAIXUEZT==null||pd.ZAIXUEZT==''}">selected</c:if>>请选择在学状态</option>  	
                                    	<c:forEach items="${zxztlist}" var="zxzt">
                                    		<option value="${zxzt.BIANMA }" <c:if test="${zxzt.BIANMA == pd.ZAIXUEZT }">selected</c:if> >${zxzt.NAME }</option>
                                    	</c:forEach>
			                        </select>
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">升学标识：</label>
	                                <select name="" class="form-control" id="sxbs" placeholder="请输入内容" <c:if test="${pd.isEdit == 'isEdit'}">disabled="disabled"</c:if> style="width:183px;">
	                                <option value="" <c:if test="${pd.SHENGXUEBIAOSHI_PKID==null||pd.SHENGXUEBIAOSHI_PKID==''}">selected</c:if>>请选择升学标识</option>  
	                                  	<c:forEach items="${sxbsList}" var="var">
	                                  		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.SHENGXUEBIAOSHI_PKID}">selected</c:if>>${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>															
					    </div>
						<div class="form-inline">
							<div class="form-group">
									<label class="jf_label jf_xing" for="">学生类型：</label>
	                                <select name="" class="form-control" id="xueshengleixing" <c:if test="${pd.isEdit == 'isEdit'}">disabled="disabled"</c:if> placeholder="请输入内容" style="width:183px;">
	                                <option value="" <c:if test="${pd.XSLX_PKID==null||pd.XSLX_PKID==''}">selected</c:if>>请选择学生类型</option> 
	                                  	<c:forEach items="${xueshengList}" var="var">
	                                  		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.XSLX_PKID}">selected</c:if>>${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
							</div>	
							<div class="form-group">
 								<label class="jf_label">注册学籍时间：</label> 								 								
							    <c:choose> 
                              		  <c:when test="${pd.isEdit == 'isEdit'}"> 
                              		  	<input type="text" name="" id="ZCXJSJ" class="form-control"   readonly="readonly"
									   value='<fmt:formatDate value="${pd.ZCXJSJ}" pattern="yyyy-MM-dd" />' maxlength="25"/>
                              		  </c:when>
                              		  <c:otherwise>
                              		  	<input class="span10 date-picker form-control"  readonly="readonly" name="ZCXJSJ" placeholder="请选择内容" id="ZCXJSJ" 
			 								 value='<fmt:formatDate value="${pd.ZCXJSJ}" pattern="yyyy-MM-dd" />' type="text" 
			 								data-date-format="yyyy-mm-dd" style="width:170px;height: 35px;cursor:pointer;"  title="注册学籍时间" />
                              		  </c:otherwise>                                 	
                                </c:choose>
						    </div>
							<div class="form-group">
								<input type="hidden" name="" id="xuejizhuangtai" class="form-control" value="${pd.XUEJIZHUANGTAI_PKID}"/>
								<label class="jf_label jf_xing" for="">计划性质：</label>
                                <select name=""  class="form-control" id="jihuaxingzhi" <c:if test="${pd.isEdit == 'isEdit'}">disabled="disabled"</c:if> placeholder="请输入内容" style="width:183px;">                                   	
                                	<option value="" <c:if test="${pd.STUDENT_SOURCE==null||pd.STUDENT_SOURCE==''}">selected</c:if>>请选择计划性质</option> 
                                	<option value="1" <c:if test="${'1' == pd.STUDENT_SOURCE }">selected</c:if>>计划内</option>
                                	<option value="2" <c:if test="${'2' == pd.STUDENT_SOURCE }">selected</c:if>>计划外</option>                                  	
		                        </select>
							</div>															
					    </div>
					    <div class="form-inline">				    
							<div class="form-group">
 								<label class="jf_label">入学时间：</label> 								 								                           		
                            		  	<input class="span10 date-picker form-control"  readonly="readonly" name="RUXUESHIJIAN" placeholder="请选择内容" id="RUXUESHIJIAN" 
	 								 value='<fmt:formatDate value="${pd.RUXUESHIJIAN}" pattern="yyyy-MM-dd" />' type="text" 
	 								data-date-format="yyyy-mm-dd" style="width:170px;height: 35px;cursor:pointer;"  title="注册学籍时间" />                              
						    </div>															
					    </div>
				  </div>
			  </div>
			  <div class="panel panel-info">
						<div class="jf_PanelHead panel-heading">
							<div class="jf_PanelTitle panel-title">
								备注信息
							</div>
						</div>
						<div class="panel-body">
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">备注1：</label> 
	 								<input type="text" name="" id="REMARKS1" class="form-control" maxlength="500" <c:if test="${pd.isEdit == 'isEdit'}">readonly="readonly"</c:if> placeholder="请输入内容" value="${pd.REMARKS1}" style="width:825px  !important;" maxlength="500"/>
							    </div>															
						    </div>
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">备注2：</label> 
	 								<input type="text" name="" id="REMARKS2" class="form-control" maxlength="500" <c:if test="${pd.isEdit == 'isEdit'}">readonly="readonly"</c:if> placeholder="请输入内容" value="${pd.REMARKS2}" style="width:825px  !important;" maxlength="500"/>
							    </div>															
						    </div>
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">备注3：</label> 
	 								<input type="text" name="" id="REMARKS3" class="form-control" maxlength="500" <c:if test="${pd.isEdit == 'isEdit'}">readonly="readonly"</c:if> placeholder="请输入内容" value="${pd.REMARKS3}" style="width:825px  !important;" maxlength="500"/>
							    </div>															
						    </div>
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">备注4：</label> 
	 								<input type="text" name="" id="REMARKS4" class="form-control" maxlength="500" <c:if test="${pd.isEdit == 'isEdit'}">readonly="readonly"</c:if> placeholder="请输入内容" value="${pd.REMARKS4}" style="width:825px  !important;" maxlength="500"/>
							    </div>															
						    </div>
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">备注5：</label> 
	 								<input type="text" name="" id="REMARKS5" class="form-control" maxlength="500" <c:if test="${pd.isEdit == 'isEdit'}">readonly="readonly"</c:if> placeholder="请输入内容" value="${pd.REMARKS5}" style="width:825px  !important;" maxlength="500"/>
							    </div>															
						    </div>
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">备注6：</label> 
	 								<input type="text" name="" id="REMARKS6" class="form-control" maxlength="500" <c:if test="${pd.isEdit == 'isEdit'}">readonly="readonly"</c:if> placeholder="请输入内容" value="${pd.REMARKS6}" style="width:825px  !important;" maxlength="500"/>
							    </div>															
						    </div>
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">备注7：</label> 
	 								<input type="text" name="" id="REMARKS7" class="form-control" maxlength="500" <c:if test="${pd.isEdit == 'isEdit'}">readonly="readonly"</c:if> placeholder="请输入内容" value="${pd.REMARKS7}" style="width:825px  !important;" maxlength="500"/>
							    </div>															
						    </div>
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">备注8：</label> 
	 								<input type="text" name="" id="REMARKS8" class="form-control" maxlength="500" <c:if test="${pd.isEdit == 'isEdit'}">readonly="readonly"</c:if> placeholder="请输入内容" value="${pd.REMARKS8}" style="width:825px  !important;" maxlength="500"/>
							    </div>															
						    </div>
					    </div>
					</div>
			
				<div class="clearfix"></div>
				<div class="panel panel-info">
					<div class="modal-footer">
								<button class="btn btn-danger" id="btn_save">保存</button>
								<button class="btn btn-default" data-dismiss="modal" id="btn_cancel">返回</buttom>
					</div>
				</div>
	</body>
	
	<script type="text/javascript">
	
	var zNodes=new Array();

	<c:forEach items="${zuzhilist}" var="t">  
		var a={id:'${t.DEPARTMENT_ID}',pId:'${t.PARENT_ID}',name:'${t.NAME}'};
		zNodes.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
	</c:forEach>
		
		
	var stu_PersonalFlag=new Array();
	<c:forEach items="${stu_PersonalFlagList}" var="t">  
		var a={EXCEPTIONAL:'${t.EXCEPTIONAL}'};
		stu_PersonalFlag.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
	</c:forEach>
	var stu_PersonalFlag1=new Array();
	<c:forEach items="${stu_PersonalFlagList1}" var="t">  
		var a={EXCEPTIONAL:'${t.EXCEPTIONAL}'};
		stu_PersonalFlag1.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
	</c:forEach>

	var xuehao="${pd.XUEHAO}";
	var idcard="${pd.SHENFENZHENGHAO}";
     
  
	
	</script>
	<script type="text/javascript" src="${basepath}static/js/myjs/stu_ztree1.js"></script>
	<script type="text/javascript" src="${basepath}static/js/myjs/editcaijingstu.js"></script>
</html>
