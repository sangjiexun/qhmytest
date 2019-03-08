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
		<title>新增学生</title>
		<link rel="stylesheet" href="${basepath}static/gxjf/bs/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${basepath}static/gxjf/css/Employee.css" />
		<link rel="stylesheet" href="${basepath}static/gxjf/css/framework-font.css" />
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
			 .jf_ZtLaber .jf_ZtBox{
				position:absolute;
			 	left:126px !important;
			 	top:39px !important;
			 } 
			 .btn_mima{
			 	position:absolute;
			 	top:270px;
			 }
		</style> 
		<script type="text/javascript">
              var _basepath = "${basepath}";
              var _imgsrc="${pd.TOUXIANG}";
              var _imgsrc1="${pd.SFZ_IMG}";
        </script>
	</head>
	<body>
				<div class="jf_ZhaoPian"  style="width:99px;height:133px;">
					<img src="" alt="" width="100%;" id="touxiang" style="width:99px;height:133px;" onerror="this.src='static/gxjf/images/touxiang_error.jpg'"/>
					<a href="javascript:void(0);" id="btn_tx">
						<div class="jf_ImgUploading"><p>上传照片</p></div>
					</a>
								
					<div style="width:99px;margin-top: 30px;">
				    	<a href="javascript:void(0);" id="btn_sfztx">
					      <img src="" id="touxiangs" alt="" width="100%;"  height="62px"/>
					    </a>
					</div>
					
					 <c:if test="${pd.PKID!=null}" >
					    <a href="javascript:void(0);" class="btn btn-danger btn_mima" id="btn_resetpwd">初始化密码</a>
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
									<label class="jf_label jf_xing">身份证号：</label>
									<input type="text" name="" id="SHENFENZHENGHAO" class="form-control" placeholder="请输入内容"  value="${pd.SHENFENZHENGHAO}" maxlength="25"/>
								    <input type="hidden" name="" class="form-control" id="cengCiId" readonly="readonly" value="${pd.CENGCI}" />
								</div>
								
								<div class="form-group">
									<label class="jf_label jf_xing">姓名：</label>
									<input type="text" name="" id="XINGMING" class="form-control" placeholder="请输入内容"  value="${pd.XINGMING}" maxlength="25"/>
								</div>
								<div class="form-group">
									<label class="jf_label jf_xing" for="">性别：</label>
									<input type="text" name=""  id="XINGBIE" class="form-control" placeholder="请输入内容"   value="${pd.XINGBIE}"  readonly = "readonly" />
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label jf_xing" >手机：</label>
									<input type="text" name="" id="SHOUJI" class="form-control" placeholder="请输入内容"  value="${pd.SHOUJI}" maxlength="25"/>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">家庭住址：</label>
									<input type="text" name="" id="JIATINGZHUZHI" class="form-control" placeholder="请输入内容"  value="${pd.JIATINGZHUZHI}" maxlength="100"/>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">邮政编码：</label>	                            
	                        		<input type="text" name="" id="YOUZHENGBIANMA" class="form-control" placeholder="请输入内容"  value="${pd.YOUZHENGBIANMA}"/>
								</div>									
								<!-- 有用 勿删 -->
								<div style="display:none;">
									<input type="hidden" name="" id="PKID" class="form-control"  value="${pd.PKID }"/>
									<input type="hidden" name="" id="TONGGUO" class="form-control"  value="${pd.TONGGUO }"/>
									<input type="hidden" name="" id="sfzh" class="form-control"  value="${pd.SHENFENZHENGHAO }"/>
									<input type="hidden" name="" id="TMPKID" class="form-control"  value="${pd.TMPKID }"/>
									<input type="hidden" name="" id="yjnianfenPkid" class="form-control"  value="${pd.YJ_NIANFEN }"/> 
									<input type="hidden" name="" id="yjbanxingPkid" class="form-control"  value="${pd.YJ_BANJI_TYPE_PKID }"/> 
									<input type="hidden" name="" id="RXNIANFEN_PKID" class="form-control"  value="${pd.RXNIANFEN_PKID }"/> 
									<input type="hidden" name="" id="BANJI_TYPE_PKID" class="form-control"  value="${pd.BANJI_TYPE_PKID }"/> 
									<input type="hidden" name="" id="OLD_BANJI" class="form-control"  value="${pd.OLD_BANJI }"/> 
									<input type="hidden" name="" id="YJ_STUBM_PKID" class="form-control"  value="${pd.YJ_STUBM_PKID }"/> 
									<input type="text" name="" id="STUTOUXIANG" class="form-control"  />
									<input type="text" name="" id="SFZTOUXIANG" class="form-control"  />
								</div>
								<!--end 有用 勿删 -->
							</div>
							
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label jf_xing">第一监护人：</label>
									<input type="text" name="" id="ONE_JHR" class="form-control" placeholder="请输入内容"  value="${pd.ONE_JHR}" maxlength="25"/>
								</div>
								<div class="form-group">
									<label class="jf_label jf_xing" for="">家庭关系：</label>
	                                <select name="" class="form-control" id="ONE_JHRGX1" placeholder="请输入内容" style="width:183px;">
	                                	<option value="" <c:if test="${pd.ONE_JHRGX_PKID==null||pd.ONE_JHRGX_PKID==''}">selected</c:if>>请选择家庭关系</option>
	                                  	<c:forEach items="${jtgxList}" var="var">
	                                  		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.ONE_JHRGX_PKID}">selected</c:if>>${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>								
								
								<div class="form-group">
									<label class="jf_label jf_xing" for="">监护人电话：</label>
									<input type="text" name="" id="ONE_JHRDH"  class="form-control" placeholder="请输入内容"  value="${pd.ONE_JHRDH}" maxlength="25"/>
								</div>
							 </div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label ">监护人单位：</label>
									<input type="text" name="" id="ONE_JHRDW" class="form-control" value="${pd.ONE_JHRDW}" placeholder="请输入内容"  maxlength="25"/>
								</div>
								<div class="form-group">
									<label class="jf_label " for="">监护人职务：</label>
									<input type="text" name="" id="ONE_JHRZW"  class="form-control" value="${pd.ONE_JHRZW}" placeholder="请输入内容"  maxlength="25"/>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label">第二监护人：</label>
									<input type="text" name="" id="TWO_JHR" class="form-control" placeholder="请输入内容"  value="${pd.TWO_JHR}" maxlength="25"/>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">家庭关系：</label>
	                                <select name="" class="form-control" id="TWO_JHRGX" placeholder="请输入内容" style="width:183px;">
	                                <option value="" <c:if test="${pd.TWO_JHRGX_PKID==null||pd.TWO_JHRGX_PKID==''}">selected</c:if>>请选择家庭关系</option>
	                                  	<c:forEach items="${jtgxList2}" var="var">
	                                  		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.TWO_JHRGX_PKID}">selected</c:if>>${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>								
								
								<div class="form-group">
									<label class="jf_label" for="">监护人电话：</label>
									<input type="text" name="" id="TWO_JHRDH"  class="form-control" placeholder="请输入内容"  value="${pd.TWO_JHRDH}" maxlength="25"/>
								</div>
							 </div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label ">监护人单位：</label>
									<input type="text" name="" id="TWO_JHRDW" class="form-control" value="${pd.TWO_JHRDW}" placeholder="请输入内容"  maxlength="25"/>
								</div>
								<div class="form-group">
									<label class="jf_label " for="">监护人职务：</label>
									<input type="text" name="" id="TWO_JHRZW"  class="form-control" value="${pd.TWO_JHRZW}" placeholder="请输入内容"  maxlength="25"/>
								</div>
							</div>
							<div class="form-inline">
								
								<div class="form-group">
									<label class="jf_label" for="">病史：</label>
									<input type="text" name="" id="BINGSHI"  class="form-control" placeholder="请输入内容"  value="${pd.BINGSHI}" maxlength="25"/>
								</div>							
								<div class="form-group">
									<label class="jf_label" for="">了解强化的途径：</label>
	                                <select name="" class="form-control" id="LJQHTJ" placeholder="请输入内容" style="width:183px;">
	                                <option value="" <c:if test="${pd.LJQHTJ_PKID==null||pd.LJQHTJ_PKID==''}">selected</c:if>>请选择了解强化的途径</option>
	                                  	<c:forEach items="${ljqhtjList}" var="var">
	                                  		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.LJQHTJ_PKID}">selected</c:if>>${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">推荐人：</label>
									<input type="text" name="" id="TUIJIANREN"  class="form-control" placeholder="请输入内容"  value="${pd.TUIJIANREN}" maxlength="25"/>
								</div>
							</div>
					</div>
				</div>
				<div class="panel panel-info">
						<div class="jf_PanelHead panel-heading">
							<div class="jf_PanelTitle panel-title">
								高中信息
							</div>
						</div>
						<div class="panel-body">
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label jf_xing " for="">文化课学校：</label>
									<select name="" class="form-control loadTables" id="WHKXUEXIAO" placeholder="请输入内容" style="width:183px;">
		                                 	<c:forEach items="${partSchoollist}" var="var">
		                                 		<option value="${var.PKID}" <c:if test="${var.PKID == pd.WHKXUEXIAO}">selected</c:if>>${var.SCHOOLNAME}</option>
		                                 	</c:forEach>
		                       		</select>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">高一入学时间：</label>
									<input class="span10 date-picker form-control" name="GYRXSJ" placeholder="请选择内容" id="GYRXSJ"
		 								  value='<fmt:formatDate value="${pd.GYRXSJE}" pattern="yyyy-MM" />' type="text" 
		 								data-date-format="yyyy-mm" readonly="readonly" style="width:170px;height: 35px;cursor:pointer;"  title="高一入学时间"/>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">高中班级：</label>	                            
	                        		<input type="text" name="" id="GZ_BANJI" class="form-control" placeholder="请输入内容"  value="${pd.GZ_BANJI}"/>
								</div>									
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label" >文、理科：</label>
									<select name=""  class="form-control" id="WENLIKE" <c:if test="${pd.isEdit == 'isEdit'}">disabled="disabled"</c:if> placeholder="请输入内容" style="width:183px;">                                   	
	                                	<option value="" <c:if test="${pd.WENLIKE==null||pd.WENLIKE==''}">selected</c:if>>请选择文理科</option> 
	                                	<option value="文科" <c:if test="${'文科' == pd.WENLIKE }">selected</c:if>>文科</option>
	                                	<option value="理科" <c:if test="${'理科' == pd.WENLIKE }">selected</c:if>>理科</option>                                  	
			                        </select>
								</div>
								<div class="form-group">
									<label class="jf_label" >学校班主任：</label>
									<input type="text" name="" id="BZRSCHOOL" class="form-control" placeholder="请输入内容"  value="${pd.BZRSCHOOL}" maxlength="25"/>
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">班主任电话：</label>	                            
	                        		<input type="text" name="" id="BZRPHONE" class="form-control" placeholder="请输入内容"  value="${pd.BZRPHONE}"/>
								</div>									
							</div>
				  	    </div>
				</div>
				<div class="panel panel-info"  id="yingjiesheng">
						<div class="jf_PanelHead panel-heading">
							<div class="jf_PanelTitle panel-title">
								班型信息
							</div>
						</div>
						<div class="panel-body">
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label jf_xing" for="">学生类型：</label>
									<select name="" class="form-control loadTables" id="ccyingjiesheng" placeholder="请输入内容" style="width:183px;">
		                                 	<c:forEach items="${cengci_list}" var="var">
		                                 		<option value="${var.PKID}" <c:if test="${var.PKID == pd.CENGCI}">selected</c:if>>${var.CENGCI_NAME}</option>
		                                 	</c:forEach>
		                       		</select>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">自何时学习美术：</label>
									<input class="span10 date-picker form-control" name="STARTMEISHU1" placeholder="请选择内容" id="STARTMEISHU"
		 								  value='<fmt:formatDate value="${pd.STARTMEISHUE}" pattern="yyyy-MM" />' type="text" 
		 								data-date-format="yyyy-mm" readonly="readonly" style="width:170px;height: 35px;cursor:pointer;"  title="入学时间"/>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">已学过科目：</label>
									<div class="checkbox" id="KEMU1">										
										<!-- <input type="checkbox" name="checkbox1"  value="SX" /> 速写
										<input type="checkbox" name="checkbox1"  value="SC" /> 色彩
										<input type="checkbox" name="checkbox1"  value="SM" /> 素描 -->
										<c:forEach items="${kemuArrList}" var="var"> 
											<input type="checkbox" name="checkbox1" value="${var.PKID}"/> ${var.NAME}
										</c:forEach>	
									</div>
								</div>																	
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label jf_xing" for="">入学年份：</label>
									<select name="" class="form-control loadTables" id="RXNIANFEN1" placeholder="请输入内容" style="width:183px;">
									<option value="" <c:if test="${pd.RXNIANFEN_PKID==null||pd.RXNIANFEN_PKID==''}">selected</c:if>>请选择入学年份</option>
		                                 	<c:forEach items="${gradelist}" var="var">
		                                 		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.RXNIANFEN_PKID}">selected</c:if>>${var.NAME}</option>
		                                 	</c:forEach>
		                       		</select>
								</div>
								<div class="form-group">
									<label class="jf_label jf_xing" for="">班型：</label>
									<select name="" class="form-control loadTables" id="BANJI_TYPE1" placeholder="请输入内容" style="width:183px;">
									<option value="" <c:if test="${pd.BANJI_TYPE_PKID==null||pd.BANJI_TYPE_PKID==''}">selected</c:if>>请选择班型</option> 
		                                 	<c:forEach items="${banXinglist}" var="var">
		                                 		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.BANJI_TYPE_PKID}">selected</c:if>>${var.NAME}</option>
		                                 	</c:forEach>
		                       		</select>
								</div>
								<div class="form-group">
									<label class="jf_label" >在学状态：</label>
									<input type="text" name="" id="ZXZT1"  readonly="readonly"  class="form-control" placeholder="请输入内容"  value="${pd.ZXZT}" maxlength="25"/>
								</div>																									
							</div>
							<div class="form-inline" id="yujiaodiv">
								<div class="form-group">
									<label class="jf_label" for="">预交年份：</label>
									<select name="" class="form-control loadTables" id="YJ_NIANFEN1"  placeholder="请输入内容" style="width:183px;">
		                                 	<option value="" <c:if test="${pd.YJ_NIANFEN==null||pd.YJ_NIANFEN==''}">selected</c:if>>请选择预交年份</option>
		                                 	<c:forEach items="${gradelist2}" var="var">
		                                 		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.YJ_NIANFEN}">selected</c:if>>${var.NAME}</option>
		                                 	</c:forEach>
		                       		</select>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">预交班型：</label>
									<select name="" class="form-control loadTables" id="YJ_BANJI_TYPE1"  placeholder="请输入内容" style="width:183px;">
		                                 	<option value="" <c:if test="${pd.YJ_BANJI_TYPE_PKID==null||pd.YJ_BANJI_TYPE_PKID==''}">selected</c:if>>请选择预交班型</option>
		                                 	<c:forEach items="${banXinglist2}" var="var">
		                                 		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.YJ_BANJI_TYPE_PKID}">selected</c:if>>${var.NAME}</option>
		                                 	</c:forEach>
		                       		</select>
								</div>																									
							</div>
				  	    </div>
				</div>
				<div class="panel panel-info"  id="bxfukesheng">
						<div class="jf_PanelHead panel-heading">
							<div class="jf_PanelTitle panel-title">
								班型信息
							</div>
						</div>
						<div class="panel-body">
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label jf_xing" for="">学生类型：</label>
									<select name="" class="form-control loadTables" id="ccbxfukesheng" placeholder="请输入内容" style="width:183px;">
		                                 	<c:forEach items="${cengci_list}" var="var">
		                                 		<option value="${var.PKID}" <c:if test="${var.PKID == pd.CENGCI_PKID}">selected</c:if>>${var.CENGCI_NAME}</option>
		                                 	</c:forEach>
		                       		</select>
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">考生号：</label>	                            
	                        		<input type="text" name="" id="KSNUMBER2" class="form-control" placeholder="请输入内容"  value="${pd.KSNUMBER}"/>
								</div>																	
								<div class="form-group">
									<label class="jf_label" for="">联考分数：</label>	                            
	                        		<input type="text" name="" id="LKFS2" class="form-control" placeholder="请输入内容"  value="${pd.LKFS}"/>
								</div>																	
							</div>
							
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label jf_xing" for="">入学年份：</label>
									<select name="" class="form-control loadTables" id="RXNIANFEN2" placeholder="请输入内容" style="width:183px;">
									<option value="" <c:if test="${pd.RXNIANFEN_PKID==null||pd.RXNIANFEN_PKID==''}">selected</c:if>>请选择入学年份</option>
		                                 	<c:forEach items="${gradelist}" var="var">
		                                 		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.RXNIANFEN_PKID}">selected</c:if>>${var.NAME}</option>
		                                 	</c:forEach>
		                       		</select>
								</div>
								<div class="form-group">
									<label class="jf_label jf_xing" for="">班型：</label>
									<select name="" class="form-control loadTables" id="BANJI_TYPE2" placeholder="请输入内容" style="width:183px;">
		                                 	<option value="" <c:if test="${pd.BANJI_TYPE_PKID==null||pd.BANJI_TYPE_PKID==''}">selected</c:if>>请选择班型</option>
		                                 	<c:forEach items="${banXinglist}" var="var">
		                                 		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.BANJI_TYPE_PKID}">selected</c:if>>${var.NAME}</option>
		                                 	</c:forEach>
		                       		</select>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">原班级：</label>	                            
	                        		<%-- <input type="text" name="" id="OLD_BANJINAME2" class="form-control" placeholder="请输入内容"  value="${pd.OLD_BANJI}"/> --%>
									<select name="" class="form-control loadTables" id="OLD_BANJINAME2"  placeholder="请输入内容" style="width:183px;">
	                                	<%-- <option value="">请选择</option>
	                                  	<c:forEach items="${banji}" var="var">
	                                  		<option value="${var.PKID}" >${var.NAME}</option>
	                                  	</c:forEach> --%>
	                        		</select>
								</div>
									
																															
							</div>
							<div class="form-inline">
								<div class="form-group" style="margin-top:10px;">
									<label class="jf_label" for="">校考成绩：</label>
									<div class="checkbox loadTables" id="XKCJCHECKBOX2"  style="width:500px;">	
										<c:forEach items="${meiyuanlist}" var="var"> 
											<input type="checkbox" name="checkbox"  value="${var.PKID}"/> ${var.NAME}
										</c:forEach>	
									</div>						
								</div>	
								<div class="form-group">
									<label class="jf_label" >在学状态：</label>
									<input type="text" name="" id="ZXZT2"  readonly="readonly"  class="form-control" placeholder="请输入内容"  value="${pd.ZXZT}" maxlength="25"/>
								</div>																									
							</div>
				  	    </div>
				</div>
				<div class="panel panel-info"  id="wxfukesheng">
						<div class="jf_PanelHead panel-heading">
							<div class="jf_PanelTitle panel-title">
								班型信息
							</div>
						</div>
						<div class="panel-body">
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label jf_xing" for="">学生类型：</label>
									<select name="" class="form-control loadTables" id="ccwxfukesheng" placeholder="请输入内容" style="width:183px;">
		                                 	<c:forEach items="${cengci_list}" var="var">
		                                 		<option value="${var.PKID}" <c:if test="${var.PKID == pd.CENGCI_PKID}">selected</c:if>>${var.CENGCI_NAME}</option>
		                                 	</c:forEach>
		                       		</select>
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">联考分数：</label>	                            
	                        		<input type="text" name="" id="LKFS3" class="form-control" placeholder="请输入内容"  value="${pd.LKFS}"/>
								</div>																	
								<div class="form-group">
									<label class="jf_label" for="">考生号：</label>	                            
	                        		<input type="text" name="" id="KSNUMBER3" class="form-control" placeholder="请输入内容"  value="${pd.KSNUMBER}"/>
								</div>																	
							</div>
							<div class="form-inline">								
								<div class="form-group" style="margin-top:10px;">
									<label class="jf_label" for="">校考成绩：</label>
									<div class="checkbox loadTables" id="XKCJCHECKBOX3" style="width:800px;">	
										<c:forEach items="${xkcjList}" var="var"> 
											<input type="checkbox" name="checkbox"  value="${var.PKID}" /> ${var.NAME}
										</c:forEach>	
									</div>							
								</div>																									
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label jf_xing" for="">入学年份：</label>
									<select name="" class="form-control loadTables" id="RXNIANFEN3" placeholder="请输入内容" style="width:183px;">
		                                 	<option value="" <c:if test="${pd.RXNIANFEN_PKID==null||pd.RXNIANFEN_PKID==''}">selected</c:if>>请选择入学年份</option>
		                                 	<c:forEach items="${gradelist}" var="var">
		                                 		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.RXNIANFEN_PKID}">selected</c:if>>${var.NAME}</option>
		                                 	</c:forEach>
		                       		</select>
								</div>
								<div class="form-group">
									<label class="jf_label jf_xing" for="">班型：</label>
									<select name="" class="form-control loadTables" id="BANJI_TYPE3" placeholder="请输入内容" style="width:183px;">
									<option value="" <c:if test="${pd.BANJI_TYPE_PKID==null||pd.BANJI_TYPE_PKID==''}">selected</c:if>>请选择班型</option>
		                                 	<c:forEach items="${banXinglist}" var="var">
		                                 		<option value="${var.DICTIONARIES_ID}" <c:if test="${var.DICTIONARIES_ID == pd.BANJI_TYPE_PKID}">selected</c:if>>${var.NAME}</option>
		                                 	</c:forEach>
		                       		</select>
								</div>	
								<div class="form-group">
									<label class="jf_label" >在学状态：</label>
									<input type="text" name="" id="ZXZT3"  readonly="readonly"  class="form-control" placeholder="请输入内容"  value="${pd.ZXZT}" maxlength="25"/>
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
	 								<input type="text" name="" id="REMARKS1" class="form-control" placeholder="请输入内容"  value="${pd.REMARKS1}" maxlength="500" style="width:825px  !important;"/>
							    </div>															
						    </div>
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">备注2：</label> 
	 								<input type="text" name="" id="REMARKS2" class="form-control" placeholder="请输入内容"  value="${pd.REMARKS2}"  maxlength="500" style="width:825px  !important;"/>
							    </div>															
						    </div>
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">备注3：</label> 
	 								<input type="text" name="" id="REMARKS3"  class="form-control" placeholder="请输入内容"   value="${pd.REMARKS3}" maxlength="500" style="width:825px  !important;"/>
							    </div>															
						    </div>
					    </div>
					</div>
					<div class="panel panel-info" id="yuxianshijinediv">
			      		<div id="panel_bodys" class="panel-body">
			      			<div id="yuxianshijine" class="form-inline" style="margin-bottom: 15px;">
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
		var xkmark=new Array();
		<c:forEach items="${xkmarkList}" var="t">  
			var a={PKID:'${t.PKID}'};
			xkmark.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
		</c:forEach>
     
		var kemus=new Array();
		<c:forEach items="${kemuList}" var="t">  
			var a={BIANMA:'${t.BIANMA}'};
			kemus.push(a); //js中可以使用此标签，将EL表达式中的值push到数组中  
		</c:forEach>
	</script>

	<script type="text/javascript" src="${basepath}static/js/myjs/editstu.js"></script>
</html>
