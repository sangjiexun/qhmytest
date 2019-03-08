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
              var _imgsrc1="${pd.SFZ_IMG}"
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
					      <img src="" id="touxiangs" alt="" width="100%;" />
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
							<div class="form-inline" >
								<div class="form-group">
									<label class="jf_label">身份证：</label>
									<input type="text" name="" id = "SHENFENZHENGHAO" class="form-control" readonly="readonly" value="${pd.SHENFENZHENGHAO}" />
									<input type="hidden" name="" class="form-control" id="cengCiId" readonly="readonly" value="${pd.CENGCI}" />
								</div>
								<div class="form-group">
									<label class="jf_label" >姓名：</label>
									<input type="text" name="" class="form-control"  readonly="readonly" value="${pd.XINGMING}" />
								</div>			
								<div class="form-group">
									<label class="jf_label" for="">性别：</label>
									<input type="text" name="" class="form-control"  readonly="readonly"  value="${pd.XINGBIE}" />
								</div>
							</div>
							<div class="form-inline">
								
								<div class="form-group">
									<label class="jf_label" for="">手机：</label>
									<input type="text" name="" class="form-control"  readonly="readonly"  value="${pd.SHOUJI}"maxlength="100"/>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">家庭住址：</label>	                            
	                        		<input type="text" name="" class="form-control"  readonly="readonly"  value="${pd.JIATINGZHUZHI}"/>
								</div>									
								<div class="form-group">
									<label class="jf_label" for="">邮政编码：</label>	                            
	                        		<input type="text" name="" class="form-control"  readonly="readonly"  value="${pd.YOUZHENGBIANMA}"/>
								</div>									
								<!-- 有用 勿删 -->
								<div style="display:none;">
									<input type="text" name="" id="pkid" class="form-control"  value="${pd.PKID }"/>
									<input type="text" name="" id="tmpkid" class="form-control"  value="${pd.TMPKID }"/>
									<input type="hidden" name="" id="OLD_BANJI" class="form-control"  value="${pd.OLD_BANJI }"/>
									<input type="hidden" name="" id="TONGGUO" class="form-control"  value="${pd.TONGGUO }"/> 
								</div>
								<!--end 有用 勿删 -->
							</div>
							
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label" for="">第一监护人：</label>
	                        		<input type="text" name="" class="form-control"  readonly="readonly" value="${pd.ONE_JHR}" />
								</div>
								<div class="form-group">
									<label class="jf_label">家庭关系：</label>
									<input type="text" name="" class="form-control"   readonly="readonly"  value="${pd.ONE_JHRGX1}"/>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">监护人电话：</label>
									<input type="text" name=""  readonly="readonly"  class="form-control" value="${pd.ONE_JHRDH}"/>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label ">监护人单位：</label>
									<input type="text" name="" readonly="readonly" class="form-control" value="${pd.ONE_JHRDW}" />
								</div>
								<div class="form-group">
									<label class="jf_label " for="">监护人职务：</label>
									<input type="text" name="" readonly="readonly" class="form-control" value="${pd.ONE_JHRZW}" />
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label" for="">第二监护人：</label>
	                        		<input type="text" name="" class="form-control"  readonly="readonly" value="${pd.TWO_JHR}" />
								</div>
								<div class="form-group">
									<label class="jf_label">家庭关系：</label>
									<input type="text" name="" class="form-control"   readonly="readonly"  value="${pd.TWO_JHRGX}"/>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">监护人电话：</label>
									<input type="text" name=""  readonly="readonly"  class="form-control" value="${pd.TWO_JHRDH}"/>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label ">监护人单位：</label>
									<input type="text" name="" readonly="readonly" class="form-control" value="${pd.TWO_JHRDW}" />
								</div>
								<div class="form-group">
									<label class="jf_label " for="">监护人职务：</label>
									<input type="text" name="" readonly="readonly" class="form-control" value="${pd.TWO_JHRZW}" />
								</div>
							</div>
							<div class="form-inline">
									
								<div class="form-group">
									<label class="jf_label" for="">病史：</label>
									<input type="text" name="" class="form-control"  readonly="readonly"  value="${pd.BINGSHI}" />
								</div>
								<div class="form-group">
									<label class="jf_label" for="">了解强化的途径：</label>
									<input type="text" name="" class="form-control"  readonly="readonly"  value="${pd.LJQHTJ}" />
								</div>
								<div class="form-group">
									<label class="jf_label">推荐人：</label>
									<input type="text" name=""  readonly="readonly"  class="form-control" value="${pd.TUIJIANREN}"/>
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
									<label class="jf_label">文化课学校：</label>
			                        <input type="text" name="" class="form-control"  readonly="readonly" value="${pd.WHKXUEXIAONAME}" />
								</div>								
								<div class="form-group">
									<label class="jf_label">高一入学时间：</label>
									 <input type="text" name="" class="form-control"  readonly="readonly" value="${pd.GYRXSJ}" /> 
									<%-- <input type="text" name=""  class="form-control" 
									value='<fmt:formatDate value="${pd.GYRXSJ}" pattern="yyyy-MM-dd" />' 						
									readonly="readonly"  placeholder="请输入内容"  maxlength="25"/> --%>
								</div>		
								<div class="form-group">
									<label class="jf_label" for="">高中班级：</label>
	                        		<input type="text" name="" class="form-control"  readonly="readonly" value="${pd.GZ_BANJI}" />
								</div>							
						  </div>
						  <div class="form-inline">
								<div class="form-group">
									<label class="jf_label" for="">文、理科：</label>
		                       		<input type="text" name="" class="form-control"  readonly="readonly" value="${pd.WENLIKE}" />
								</div>
								<div class="form-group">
									<label class="jf_label" for="">学校班主任：</label>
		                       		<input type="text" name="" class="form-control"  readonly="readonly" value="${pd.BZRSCHOOL}" />
								</div>							
								<div class="form-group">
									<label class="jf_label" for="">班主任电话：</label>
									<input type="text" name="" class="form-control" readonly="readonly"  value="${pd.BZRPHONE}"/>
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
						<div class="form-inline" style="margin-bottom: 15px;">
							<div class="form-group">
 								<label class="jf_label">学生类型：</label> 
 								<input type="text" name="" class="form-control" readonly = "readonly" value="${pd.CENGCI}"/>
						    </div>	
						    <div class="form-group">
								<label class="jf_label">自何时学习美术：</label>
								<%-- <input type="text" name=""  class="form-control" 
									value='<fmt:formatDate value="${pd.STARTMEISHU}" pattern="yyyy-MM-dd" />' 						
									readonly="readonly"  placeholder="请输入内容"  maxlength="25"/> --%>
								 <input type="text" name=""  class="form-control" readonly = "readonly"  value="${pd.STARTMEISHU}"/> 
							</div>
						    <div class="form-group" style="margin-top:10px;">
								<label class="jf_label" for="">已学过科目：</label>
								<input type="checkbox" name="checkbox1"  value="SX"   disabled="disabled"/> 速写
								<input type="checkbox" name="checkbox1"  value="SC"   disabled="disabled"/> 色彩
								<input type="checkbox" name="checkbox1"  value="SM"   disabled="disabled"/> 速描	
													
							</div>	
						    <div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">入学年份：</label> 
	 								<input type="text" name=""  id="RXNIANFEN2" class="form-control" readonly = "readonly" value="${pd.RXNIANFEN}"/>
							    </div>	
							    <div class="form-group">
	 								<label class="jf_label">班型：</label> 
	 								<input type="text" name="" class="form-control" readonly = "readonly" value="${pd.BANJI_TYPE}"/>
							    </div>	
							    <div class="form-group">
	 								<label class="jf_label">班级：</label> 
	 								<input type="text" name="" class="form-control" readonly = "readonly" value="${pd.BANJINAME}"/>
							    </div>	
							    <div class="form-inline" style="margin-bottom: 15px;">
									<div class="form-group">
		 								<label class="jf_label">预交年份：</label> 
		 								<input type="text" name="" class="form-control" readonly = "readonly" value="${pd.YJ_NIANFEN_NAME}"/>
								    </div>	
								    <div class="form-group">
		 								<label class="jf_label">预交班型：</label> 
		 								<input type="text" name="" class="form-control" readonly = "readonly" value="${pd.YJ_BANJI_TYPE}"/>
								    </div>		
								    <div class="form-group">
		 								<label class="jf_label">学号：</label> 
		 								<input type="text" name=""  class="form-control" readonly = "readonly"  value="${pd.XUEHAO}"/>
								    </div>												
							    </div>	
							    <div class="form-inline" style="margin-bottom: 15px;">
									<div class="form-group">
		 								<label class="jf_label">在学状态：</label> 
		 								<input type="text" name=""  class="form-control" readonly = "readonly"  value="${pd.ZXZT}"/>
								    </div>
								    <div class="form-group">
		 								<label class="jf_label">入住状态：</label> 
		 								<input type="text" name="" class="form-control" readonly = "readonly" value="${pd.RZZT}"/>
								    </div>
								    <div class="form-group">
		 								<label class="jf_label">报名时间：</label> 
		 								<%-- <input type="text" name=""  class="form-control" readonly = "readonly"  value="${pd.CJSJTM}"/> --%>
		 								<input type="text" name=""  class="form-control" 
									value='<fmt:formatDate value="${pd.CJSJTM}" pattern="yyyy-MM-dd" />' 						
									readonly="readonly"  placeholder="请输入内容"  maxlength="25"/>
								    </div>																
							    </div>												
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
						<div class="form-inline" style="margin-bottom: 15px;">
							<div class="form-group">
 								<label class="jf_label">学生类型：</label> 
 								<input type="text" name="" class="form-control" readonly = "readonly" value="${pd.CENGCI}"/>
						    </div>	
						    <div class="form-group">
 								<label class="jf_label">原班级：</label> 
 								<input type="text" name="" class="form-control" readonly = "readonly" value="${pd.OLD_BANJINAME}"/>
						    </div>	
						    <div class="form-group">
 								<label class="jf_label">联考分数：</label> 
 								<input type="text" name=""  class="form-control" readonly = "readonly"  value="${pd.LKFS}"/>
						    </div>														
					    </div>
					    <div class="form-inline" style="margin-bottom: 15px;">
							<div class="form-group">
 								<label class="jf_label">考生号：</label> 
 								<input type="text" name="" class="form-control" readonly = "readonly" value="${pd.KSNUMBER}"/>
						    </div>	
						    <div class="form-group" style="margin-top:10px;">
								<label class="jf_label" for="">校考成绩：</label>
								<c:forEach items="${xkcjList}" var="var"> 
									<input type="checkbox" name="checkbox"  value="${var.PKID}"   disabled="disabled"/> ${var.NAME}
								</c:forEach>							
							</div>		
						    <div class="form-group">
 								<label class="jf_label">报名时间：</label> 
 								<%-- <input type="text" name=""  class="form-control" readonly = "readonly"  value="${pd.CJSJTM}"/> --%>
						        <input type="text" name=""  class="form-control" 
									value='<fmt:formatDate value="${pd.CJSJTM}" pattern="yyyy-MM-dd" />' 						
									readonly="readonly"  placeholder="请输入内容"  maxlength="25"/>
						    </div>															
					    </div>
					    <div class="form-inline" style="margin-bottom: 15px;">
							<div class="form-group">
 								<label class="jf_label">入学年份：</label> 
 								<input type="text" name=""  id="RXNIANFEN2" class="form-control" readonly = "readonly" value="${pd.RXNIANFEN}"/>
						    </div>	
						    <div class="form-group">
 								<label class="jf_label">班型：</label> 
 								<input type="text" name="" class="form-control" readonly = "readonly" value="${pd.BANJI_TYPE}"/>
						    </div>
						    <div class="form-group">
 								<label class="jf_label">班级：</label> 
 								<input type="text" name="" class="form-control" readonly = "readonly" value="${pd.BANJINAME}"/>
						    </div>														
					    </div>
					    <div class="form-inline" style="margin-bottom: 15px;">
							<div class="form-group">
 								<label class="jf_label">学号：</label> 
 								<input type="text" name=""  class="form-control" readonly = "readonly"  value="${pd.XUEHAO}"/>
						    </div>
						    <div class="form-group">
 								<label class="jf_label">在学状态：</label> 
 								<input type="text" name=""  class="form-control" readonly = "readonly"  value="${pd.ZXZT}"/>
						    </div>	
						    <div class="form-group">
 								<label class="jf_label">入住状态：</label> 
 								<input type="text" name="" class="form-control" readonly = "readonly" value="${pd.RZZT}"/>
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
						<div class="form-inline" style="margin-bottom: 15px;">
							<div class="form-group">
 								<label class="jf_label">学生类型：</label> 
 								<input type="text" name="" class="form-control" readonly = "readonly" value="${pd.CENGCI}"/>
						    </div>
						    <div class="form-group">
 								<label class="jf_label">联考分数：</label> 
 								<input type="text" name=""  class="form-control" readonly = "readonly"  value="${pd.LKFS}"/>
						    </div>	
						    <div class="form-group">
 								<label class="jf_label">考生号：</label> 
 								<input type="text" name="" class="form-control" readonly = "readonly" value="${pd.KSNUMBER}"/>
						    </div>														
					    </div>
					    <div class="form-inline" style="margin-bottom: 15px;">				
							<div class="form-group" style="margin-top:10px;">
								<label class="jf_label" for="">校考成绩：</label>
								<c:forEach items="${xkcjList}" var="var"> 
									<input type="checkbox" name="checkbox"  value="${var.PKID}"   disabled="disabled"/> ${var.NAME}
								</c:forEach>							
							</div>												
						    <div class="form-group">
 								<label class="jf_label">报名时间：</label>  								
								<input type="text" name=""  class="form-control" 
									value='<fmt:formatDate value="${pd.CJSJTM}" pattern="yyyy-MM-dd" />' 						
									readonly="readonly"  placeholder="请输入内容"  maxlength="25"/>
						    </div>
						    <div class="form-group">
 								<label class="jf_label">在学状态：</label> 
 								<input type="text" name=""  class="form-control" readonly = "readonly"  value="${pd.ZXZT}"/>
						    </div>															
					    </div>	
					    <div class="form-inline" style="margin-bottom: 15px;">
							<div class="form-group">
 								<label class="jf_label">入学年份：</label> 
 								<input type="text" name=""   id="RXNIANFEN2" class="form-control" readonly = "readonly" value="${pd.RXNIANFEN}"/>
						    </div>	
						    <div class="form-group">
 								<label class="jf_label">班型：</label> 
 								<input type="text" name="" class="form-control" readonly = "readonly" value="${pd.BANJI_TYPE}"/>
						    </div>	
						    <div class="form-group">
 								<label class="jf_label">班级：</label> 
 								<input type="text" name="" class="form-control" readonly = "readonly" value="${pd.BANJINAME}"/>
						    </div>													
					    </div>	
					    <div class="form-inline" style="margin-bottom: 15px;">
							<div class="form-group">
 								<label class="jf_label">学号：</label> 
 								<input type="text" name=""  class="form-control" readonly = "readonly"  value="${pd.XUEHAO}"/>
						    </div>
						    <div class="form-group">
 								<label class="jf_label">入住状态：</label> 
 								<input type="text" name="" class="form-control" readonly = "readonly" value="${pd.RZZT}"/>
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
 								<input type="text" name="" class="form-control" placeholder="请输入内容" readonly = "readonly" value="${pd.REMARKS1}" style="width:825px  !important;"/>
						    </div>															
					    </div>
						<div class="form-inline" style="margin-bottom: 15px;">
							<div class="form-group">
 								<label class="jf_label">备注2：</label> 
 								<input type="text" name="" class="form-control" placeholder="请输入内容" readonly = "readonly" value="${pd.REMARKS2}"  style="width:825px  !important;"/>
						    </div>															
					    </div>
						<div class="form-inline" style="margin-bottom: 15px;">
							<div class="form-group">
 								<label class="jf_label">备注3：</label> 
 								<input type="text" name=""  class="form-control" placeholder="请输入内容" readonly = "readonly"  value="${pd.REMARKS3}" style="width:825px  !important;"/>
						    </div>															
					    </div>
				    </div>
				</div>
				<div class="clearfix"></div>
				
				<div class="modal-footer">
					<button class="btn btn-danger" id="btn_shtg">审核通过</button>
					<button class="btn btn-danger" id="btn_shbtg">审核不通过</button>
					<button class="btn btn-default" data-dismiss="modal" id="btn_cancel">返回</buttom>
				</div>
				<div id="mymoda2" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header jf_mod-header">
								<div class="close" data-dismiss="modal">&times;</div>
								<b class="jf_HeaderText" id="jiaotuifei">审核失败原因</b>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label">审核失败原因：</label>
									<input type="text" name="" value="" class="form-control"  id="IS_TONGGUO_REASON1" placeholder="请输入内容"  maxlength="200"
									maxlength="500" style="width:400px  !important;"/>
								</div>
							</div>
							<div class="modal-footer">
								<button class="btn btn-danger" id="payButtonBatch1">确认</button>
								<button class="btn btn-default" data-dismiss="modal" id="cancelButtonBatch">取消</button>
							</div>
						</div>
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
