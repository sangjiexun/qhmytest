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
		<title>学生详情页</title>
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
		</style> 
		<script type="text/javascript">
              var _basepath = "${basepath}";
              var _imgsrc="${pd.TOUXIANG}";
        </script>
	</head>
	<body>
				<div class="jf_ZhaoPian">
					<img src="" alt="" width="100%;" id="touxiang" style="width:99px;height:133px;"/>
				</div>
				<div class="jf_DetailsList">
					<div class="panel panel-info">
						<div class="jf_PanelHead panel-heading">
							<div class="jf_PanelTitle panel-title">
								个人信息
							</div>
						</div>
						<div class="panel-body" >
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
									<label class="jf_label">身份证：</label>
									<input type="text" name="" id="sfz" class="form-control" readonly="readonly"  placeholder="请输入内容" value="${pd.SHENFENZHENGHAO}" maxlength="25"/>
								</div>
								
								<div class="form-group">
									<label class="jf_label">出生日期：</label>
									<input type="text" name="" id="csrq" class="form-control" 
									value='<fmt:formatDate value="${pd.CHUSHENGRIQI}" pattern="yyyy-MM-dd" />' 						
									readonly="readonly"  placeholder="请输入内容"  maxlength="25"/>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">性别：</label>
									<input type="text" name=""  id="xingbie" class="form-control"  readonly="readonly" placeholder="请输入内容" value="${pd.XINGBIE}" />
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label" >姓名：</label>
									<input type="text" name="" id="name" class="form-control"  readonly="readonly" placeholder="请输入内容" value="${pd.XINGMING}" maxlength="25"/>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">联系电话：</label>
									<input type="text" name="" id="phone" class="form-control"  readonly="readonly" placeholder="请输入内容" value="${pd.SHOUJI}"maxlength="100"/>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">民族：</label>	                                
	                        		<input type="text" name="" id="minzu" class="form-control"  readonly="readonly" value="${pd.MINZU}" />
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
	                        		<input type="text" name="" id="zzmm" class="form-control"  readonly="readonly" value="${pd.ZHENGZHIMIANMAO}" />
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">健康状态：</label>
	                        		<input type="text" name="" id="jkzk" class="form-control"  readonly="readonly" value="${pd.JKZK}" />
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">户口性质：</label>
	                        		<input type="text" name="" id="hukouxingzhi" class="form-control"  readonly="readonly" value="${pd.HUKOUXINGZHI}" />
								</div>								
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label">邮政编码：</label>
									<input type="text" name="" id="yzbm" class="form-control"   readonly="readonly" value="${pd.YOUZHENGBIANMA}" maxlength="25"/>
								</div>
								
								<div class="form-group">
									<label class="jf_label">家庭成员姓名：</label>
									<input type="text" name="" id="jtcyxm" class="form-control"   readonly="readonly"  value="${pd.JTCYXM}" maxlength="25"/>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">家庭成员联系电话：</label>
									<input type="text" name="" id="lxdh"  readonly="readonly"  class="form-control" value="${pd.JTCYLXDH}" maxlength="25"/>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label" for="">家庭关系：</label>
	                        		<input type="text" name="" id="jtcygx" class="form-control"  readonly="readonly" value="${pd.JTCYGX}" />
								</div>
								<div class="form-group">
									<label class="jf_label" for="">家庭地址：</label>
									<input type="text" name="" id="jiatingzhuzhi" class="form-control"  readonly="readonly" placeholder="请输入内容" value="${pd.JIATINGZHUZHI}" style="width:498px  !important;"/>
								</div>
							</div>
							<div class="form-inline">								
								<div class="form-group">
									<label class="jf_label" for="">学号：</label>	                            
	                        		<input type="text" name="" id="xuehao" class="form-control"  readonly="readonly" placeholder="请输入内容" value="${pd.XUEHAO}"/>
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">是否贫困：</label>
	                        		<input type="text" name="" id="poorstu" class="form-control"  readonly="readonly" value="${pd.SHIFOUPINKUN}" />
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">中学名称：</label>
									<input type="text" name="" id="zxmc"  readonly="readonly" class="form-control" placeholder="请输入内容" value="${pd.ZXMC}"/>
								</div>								
							</div>
							<div class="form-inline">								
								<div class="form-group">
									<label class="jf_label" for="">考生号：</label>	                            
	                        		<input type="text" name="" id="kaoshenghao" class="form-control"  readonly="readonly" placeholder="请输入内容" value="${pd.KAOSHENGHAO}"/>
								</div>								
								<div class="form-group" style="margin-top:10px;">
									<label class="jf_label" for="">考生特长：</label>
									<c:forEach items="${kstcList}" var="var"> 
										<input type="checkbox" name="checkbox" value="${var.BIANMA}"  disabled="disabled"/> ${var.NAME}
									</c:forEach>																	
								</div>							
								<div class="form-group" style="margin-top:10px;">
									<label class="jf_label" for="">特殊标记：</label>
									<c:forEach items="${stu_FlagList1}" var="var"> 
										<input type="checkbox" name="checkbox1" value="${var.BIANMA}"  disabled="disabled"/> ${var.NAME}
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
							<div class="form-inline" style="margin-bottom: 15px;">								
								<div class="form-group">
									<label class="jf_label">入学年份：</label>
			                        <input type="text" name="" id="grade" class="form-control"  readonly="readonly" value="${pd.NIANJI}" />
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">录取专业：</label>
									<input type="text" name="" id="luquzhuanye"  class="form-control"  readonly="readonly" placeholder="请输入内容" value="${pd.LUQUZHUANYE}"/>
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">院校专业：</label>
									<input type="text" name="" id="departmentid" class="form-control"  placeholder="请输入内容" value="${pd.ZUZHINAME}"/>
								</div>								
						  </div>
						  <div class="form-inline">
								<div class="form-group">
									<label class="jf_label" for="">学生类型：</label>
		                       		<input type="text" name="" id="CENGCI" class="form-control"  readonly="readonly" value="${pd.CENGCI}" />
								</div>
								<div class="form-group">
									<label class="jf_label" for="">批次：</label>
		                       		<input type="text" name="" id="PICI" class="form-control"  readonly="readonly" value="${pd.PICI}" />
								</div>							
								<div class="form-group">
									<label class="jf_label" for="">投档成绩：</label>
									<input type="text" name="" id="toudangchengji" class="form-control" readonly="readonly"  placeholder="请输入内容" value="${pd.TOUDANGCHENGJI}"/>
								</div>								
						  </div>
						  <div class="form-inline">
								
								<div class="form-group">
									<label class="jf_label" for="">学制：</label>
	                        		<input type="text" name="" id="xuezhi" class="form-control"  readonly="readonly" value="${pd.XUEZHI}" />
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">科别：</label>
	                        		<input type="text" name="" id="kebie" class="form-control"  readonly="readonly" value="${pd.KEBIE}" />
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">学籍类型：</label>
	                        		<input type="text" name="" id="SCHOOLROLL" class="form-control"  readonly="readonly" value="${pd.XUEJILEIXING}" />
								</div>															
					    </div>
						<div class="form-inline">
								<div class="form-group">
									<label class="jf_label" for="">所在班级：</label>
	                        		<input type="text" name="" id="banji" class="form-control"  readonly="readonly" value="${pd.CLASS_NAME}" />
								</div>							
								<div class="form-group">
									<label class="jf_label" for="">在学状态：</label>
			                        <input type="text" name="" id="xuejizhuangtai" class="form-control"  readonly="readonly" value="${pd.ZAIXUEZT}" />
								</div>								
								<div class="form-group">
									<label class="jf_label" for="">升学标识：</label>
	                        		<input type="text" name="" id="sxbs" class="form-control"  readonly="readonly" value="${pd.SHENGXUEBIAOSHI}" />
								</div>															
					    </div>
						<div class="form-inline">
							<div class="form-group">
									<label class="jf_label" for="">学生类型：</label>
	                        		<input type="text" name="" id="SCHOOLROLL" class="form-control"  readonly="readonly" value="${pd.XUESHENGLEIXING}" />
							</div>
							<div class="form-group">
 								<label class="jf_label">注册学籍时间：</label> 								
 								<input type="text" name="" id="ZCXJSJ" value='<fmt:formatDate value="${pd.ZCXJSJ}" pattern="yyyy-MM-dd" />'  readonly="readonly" class="form-control"  />
						    </div>	
						    <div class="form-group">
									<label class="jf_label" for="">计划性质：</label>
	                        		<input type="text" name="" id="jhxz" class="form-control"  readonly="readonly" value="${pd.STUDENT_SOURCE}" />
							</div>															
					    </div>
						<div class="form-inline">								
						   													
						    <div class="form-group">
									<label class="jf_label" for="">所在学院：</label>
	                        		<input type="text" name="" id="szxy" class="form-control"  readonly="readonly" value="${pd.XUEYUANNAME}" />
							</div>
							<div class="form-group">
 								<label class="jf_label">入学时间：</label> 								
 								<input type="text" name="" id="RUXUESHIJIAN" value='<fmt:formatDate value="${pd.RUXUESHIJIAN}" pattern="yyyy-MM-dd" />'  readonly="readonly" class="form-control"  />
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
	 								<input type="text" name="" id="REMARKS1"   readonly="readonly" class="form-control" placeholder="请输入内容" value="${pd.REMARKS1}" style="width:825px  !important;"/>
							    </div>															
						    </div>
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">备注2：</label> 
	 								<input type="text" name="" id="REMARKS2"  readonly="readonly" class="form-control" placeholder="请输入内容" value="${pd.REMARKS2}" style="width:825px  !important;"/>
							    </div>															
						    </div>
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">备注3：</label> 
	 								<input type="text" name="" id="REMARKS3"  readonly="readonly" class="form-control" placeholder="请输入内容" value="${pd.REMARKS3}" style="width:825px  !important;"/>
							    </div>															
						    </div>
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">备注4：</label> 
	 								<input type="text" name="" id="REMARKS4" readonly="readonly" class="form-control" placeholder="请输入内容" value="${pd.REMARKS4}" style="width:825px  !important;"/>
							    </div>															
						    </div>
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">备注5：</label> 
	 								<input type="text" name="" id="REMARKS5" readonly="readonly" class="form-control" placeholder="请输入内容" value="${pd.REMARKS5}" style="width:825px  !important;"/>
							    </div>															
						    </div>
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">备注6：</label> 
	 								<input type="text" name="" id="REMARKS6" readonly="readonly" class="form-control" placeholder="请输入内容" value="${pd.REMARKS6}" style="width:825px  !important;"/>
							    </div>															
						    </div>
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">备注7：</label> 
	 								<input type="text" name="" id="REMARKS7" readonly="readonly" class="form-control" placeholder="请输入内容" value="${pd.REMARKS7}" style="width:825px  !important;"/>
							    </div>															
						    </div>
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
	 								<label class="jf_label">备注8：</label> 
	 								<input type="text" name="" id="REMARKS8" readonly="readonly" class="form-control" placeholder="请输入内容" value="${pd.REMARKS8}" style="width:825px  !important;"/>
							    </div>															
						    </div>
					    </div>
					</div>
			
			<div class="clearfix"></div>
			<div class="panel panel-info">
				<div class="modal-footer">
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
	var inputs = $("input");
	inputs.each(function(){
		$(this).attr("readonly",true);
	});
		
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

	var requrl="${requrl}"	
     
	
	</script>
	<script type="text/javascript" src="${basepath}static/js/myjs/stu_ztree.js"></script>
	<script type="text/javascript" src="${basepath}static/js/myjs/editcaijingstu.js"></script>
</html>
