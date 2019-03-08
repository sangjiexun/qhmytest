<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				margin-left:50px;
			}
			.form-inline .form-group{
				margin-right:25px;
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
			 .jf_szright{
			 	min-width:1300px;
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
					<a href="javascript:void(0);" id="btn_tx">
					<div style="width:99px;!important;" class="jf_ImgUploading">
					<p >上传照片</p>
					</div>
					</a>
					
					<div style="width:99px;margin-top: 30px;">
				    	<a href="javascript:void(0);" id="btn_sfztx">
					      <img src="" id="touxiangs" alt="" width="100%;" height="62px"/>
					    </a>
					
					</div>
				</div>
				<button style="display:none;" id="mybuttontest"></button>
				<div class="jf_DetailsList">
					<div class="panel panel-info">
						
						<div class="panel-body" >
							<div class="form-inline">
							<div style="display:none;">
								        <!--用来存储 树节点的id -->
									    <input type="text" name="tree" id="orgtree" class="form-control" value="${pd.DEPARTMENT_PKID}"/>
								    </div>
								<div class="form-group">
									<label class="jf_label jf_xing">身份证号：</label>
									<input type="text" name="" id="SHENFENZHENGHAO" class="form-control" placeholder="请输入内容"  maxlength="25"/>
								</div>
								
								<div class="form-group">
									<label class="jf_label jf_xing">姓名：</label>
									<input type="text" name="" id="XINGMING" class="form-control" placeholder="请输入内容"   maxlength="25"/>
								</div>
								
								<div class="form-group">
									<label class="jf_label jf_xing" for="">性别：</label>
									<input type="text" name=""  id="XINGBIE" class="form-control" placeholder="请输入内容"  readonly = "readonly" />
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label jf_xing" >手机：</label>
									<input type="text" name="" id="SHOUJI" class="form-control" placeholder="请输入内容" maxlength="11"/>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">家庭住址：</label>
									<input type="text" name="" id="JIATINGZHUZHI" class="form-control" placeholder="请输入内容" maxlength="100"/>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">邮政编码：</label>	                            
	                        		<input type="text" name="" id="YOUZHENGBIANMA" class="form-control" placeholder="请输入内容" maxlength="10" />
								</div>									
								<!-- 有用 勿删 -->
								<div style="display:none;">
									<input type="text" name="" id="PKID" class="form-control"  />
									<input type="text" name="" id="STUTOUXIANG" class="form-control"  />
									<input type="text" name="" id="SFZTOUXIANG" class="form-control"  />
									<input type="text" name="" id="IS_YJ" value="N"/>
								</div>
								<!--end 有用 勿删 -->
							</div>
							
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label jf_xing">第一监护人：</label>
									<input type="text" name="" id="ONE_JHR" class="form-control" placeholder="请输入内容"  maxlength="25"/>
								</div>
								<div class="form-group">
									<label class="jf_label jf_xing" for="">家庭关系：</label>
	                                <select name="" class="form-control" id="ONE_JHRGX_PKID" placeholder="请输入内容" style="width:183px;">
	                                <option value="">请选择</option>
	                                  	<c:forEach items="${jtgx}" var="var">
	                                  		<option value="${var.PKID}" >${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>								
								<div class="form-group">
									<label class="jf_label jf_xing" for="">监护人电话：</label>
									<input type="text" name="" id="ONE_JHRDH"  class="form-control" placeholder="请输入内容"  maxlength="25"/>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label ">监护人单位：</label>
									<input type="text" name="" id="ONE_JHRDW" class="form-control" placeholder="请输入内容"  maxlength="25"/>
								</div>
								<div class="form-group">
									<label class="jf_label " for="">监护人职务：</label>
									<input type="text" name="" id="ONE_JHRZW"  class="form-control" placeholder="请输入内容"  maxlength="25"/>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label">第二监护人：</label>
									<input type="text" name="" id="TWO_JHR" class="form-control" placeholder="请输入内容"  maxlength="25"/>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">家庭关系：</label>
	                                <select name="" class="form-control" id="TWO_JHRGX_PKID" placeholder="请输入内容" style="width:183px;">
	                                <option value="">请选择</option>
	                                  	<c:forEach items="${jtgx}" var="var">
	                                  		<option value="${var.PKID}" >${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>								
							
								<div class="form-group">
									<label class="jf_label" for="">监护人电话：</label>
									<input type="text" name="" id="TWO_JHRDH"  class="form-control" placeholder="请输入内容"  maxlength="25"/>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label ">监护人单位：</label>
									<input type="text" name="" id="TWO_JHRDW" class="form-control" placeholder="请输入内容"  maxlength="25"/>
								</div>
								<div class="form-group">
									<label class="jf_label " for="">监护人职务：</label>
									<input type="text" name="" id="TWO_JHRZW"  class="form-control" placeholder="请输入内容"  maxlength="25"/>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label class="jf_label">病史：</label>
									<input type="text" name="" id="BINGSHI" class="form-control" placeholder="请输入内容"  maxlength="25"/>
								</div>
								<div class="form-group">
									<label class="jf_label" for="">了解强化的途径：</label>
	                                <select name="" class="form-control" id="LJQHTJ_PKID" placeholder="请输入内容" style="width:183px;">
	                                <option value="">请选择</option>
	                                  	<c:forEach items="${qhtj}" var="var">
	                                  		<option value="${var.PKID}" >${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>								
							
								<div class="form-group">
									<label class="jf_label" for="">推荐人：</label>
									<input type="text" name="" id="TUIJIANREN"  class="form-control" placeholder="请输入内容"  maxlength="25"/>
								</div>
							</div>
							
							</div>
					
					
				
					
				 
			  </div>
			  
			  
			  
			   <div class="panel panel-info">
						
						<div class="panel-body">
							<div class="form-inline">								
									<div class="form-group">
									<label class="jf_label jf_xing" for="">文化课学校：</label>
	                                <select name="" class="form-control loadTables" id="WHKXUEXIAO" placeholder="请输入内容" style="width:183px;">
	                                <option value="">请选择</option>
	                                  	<c:forEach items="${whkSchool}" var="var">
	                                  		<option value="${var.PKID}" >${var.SCHOOLNAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>								
								
								<div class="form-group">
	 								<label class="jf_label">高一入学时间：</label> 
	 								<input class="span10 date-picker form-control" name="RUXUESHIJIAN" placeholder="请选择内容" id="GYRXSJ"  value="${pd.RUXUESHIJIAN}" type="text" 
	 								data-date-format="yyyy-mm" readonly="readonly" style="width:170px;height: 35px;cursor:pointer;"  title="入学时间"/>
						        </div>	
								<div class="form-group">
									<label class="jf_label">高一班级：</label>
									<input type="text" name="" id="GZ_BANJI" class="form-control" placeholder="请输入内容"  maxlength="25"/>
								</div>
									
									
						        </div>	
						      
						  <div class="form-inline">
						  
						      <div class="form-group">
								<label class="jf_label" for="">文、理科：</label>
								<select name="" class="form-control" id="WENLIKE" placeholder="请输入内容" style="width:183px;">
								        <option value="">请选择</option>
	                                 	
	                                 		<option value="文科" >文科</option>
	                                        <option value="理科" >理科</option>
	                       		</select>
								</div>
								
									<div class="form-group">
									<label class="jf_label">学校班主任：</label>
									<input type="text" name="" id="BZRSCHOOL" class="form-control" placeholder="请输入内容"  maxlength="25"/>
								</div>
									<div class="form-group">
									<label class="jf_label">班主任电话：</label>
									<input type="text" name="" id="BZRPHONE" class="form-control" placeholder="请输入内容"  maxlength="25"/>
								</div>
								
						  </div>	
					    </div>
					</div>
			  
			  
			  
			  
			  <div class="panel panel-info" id="yjnf_content">
						
						<div id="panel_bodys" class="panel-body">
							<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group">
									<label class="jf_label jf_xing" for="">学生类型：</label>
	                                <select name="" class="form-control loadTables" id="stu_cc" placeholder="请输入内容" style="width:183px;">
	                                <option value="">请选择</option>
	                                  	<c:forEach items="${cengci}" var="var">
	                                  		<option value="${var.PKID}" >${var.CENGCI_NAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>	
								<div class="form-group" id="stu_cc_ms" style="display:none;">
	 								<label class="jf_label">自何时学习美术：</label> 
	 								<input class="span10 date-picker form-control" name="RUXUESHIJIAN" placeholder="请选择内容" id="STARTMEISHU"  value="${pd.RUXUESHIJIAN}" type="text" 
	 								data-date-format="yyyy-mm" readonly="readonly" style="width:170px;height: 35px;cursor:pointer;"  title="入学时间"/>
						        </div>	
								
									<div class="form-group" id="stu_cc_km" style="display:none;">
	 								<label class="jf_label">已学过科目：</label> 
	 								<div class="checkbox" id="YXGKMCHECKBOX" style="width:175px;">
	 								<c:forEach items="${kemus}"  var="var">
	                                  		 <label style="margin-right:10px;"><input class="checkbox_km" type="checkbox" value="${var.PKID }">${var.NAME }</label>
	                                 </c:forEach>
  									 <!-- <label><input type="checkbox" value="SX">速写</label>
  									 <label><input type="checkbox" value="SC">色彩</label>
  									 <label><input type="checkbox" value="SM">素描</label> -->
									</div>
								
						        </div>			
								
								<div class="form-group" id="stu_fw_ksh" style="display:none;">
	 								<label class="jf_label">考生号：</label> 
	 													<input type="text" name="" id="KSNUMBER" class="form-control" placeholder="请输入内容"  maxlength="25"/>
							        </div>	
						        <div class="form-group" id="stu_fw_lkfs" style="display:none;">
	 								<label class="jf_label">联考分数：</label> 
	 											<input type="text" name="" id="LKFS" class="form-control" placeholder="请输入内容"  maxlength="25"/>
							        </div>	
										
																					
						    </div>
						
							
							<div class="form-inline" style="margin-bottom: 15px;">
									<div class="form-group" id="stu_cc_rxnf" style="display:none;">
									<label class="jf_label jf_xing" for="">入学年份：</label>
	                                <select name="" class="form-control loadTables" id="RXNIANFEN_PKID"  placeholder="请输入内容" style="width:183px;">
	                                <option value="">请选择</option>
	                                  	<c:forEach items="${rxnf}" var="var">
	                                  		<option value="${var.PKID}" >${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
								</div>	
							
							<div class="form-group" id="stu_cc_bx" style="display:none;">
									<label class="jf_label jf_xing" for="">班型：</label>
	                                <select name="" class="form-control loadTables" id="BANJI_TYPE_PKID"  placeholder="请输入内容" style="width:183px;">
	                                <option value="">请选择</option>
	                                  	<c:forEach items="${banXing}" var="var">
	                                  		<option value="${var.PKID}" >${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
							</div>	
							
							 <div class="form-group" id="stu_fw_lzxzt" style="display:none;">
	 								<label class="jf_label">在学状态：</label> 
													在学
							        </div>	
							
							
							
							   <div class="form-group" id="stu_fw_oldbj" style="display:none;">
	 								<label class="jf_label">原班级：</label> 
	 								<select name="" class="form-control loadTables" id="OLD_BANJI"  placeholder="请输入内容" style="width:183px;">
	                                	<%-- <option value="">请选择</option>
	                                  	<c:forEach items="${banji}" var="var">
	                                  		<option value="${var.PKID}" >${var.NAME}</option>
	                                  	</c:forEach> --%>
	                        		</select>
							        </div>	
							
							</div>	
							
							
						<div class="form-inline" style="margin-bottom: 15px;">
								<div class="form-group" id="stu_cc_yjrxnf" style="display:none;">
									<label class="jf_label" for="">预交年份：</label>
	                                <select name="" class="form-control loadTables" id="YJ_NIANFEN"  placeholder="请输入内容" style="width:183px;">
	                                <option value="">请选择</option>
	                                  	<c:forEach items="${rxnf}" var="var">
	                                  		<option value="${var.PKID}" >${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
							</div>	
								<div class="form-group" id="stu_cc_yjbx" style="display:none;">
									<label class="jf_label" for="">预交班型：</label>
	                                <select name="" class="form-control loadTables" id="YJ_BANJI_TYPE_PKID"  placeholder="请输入内容" style="width:183px;">
	                                <option value="">请选择</option>
	                                  	<c:forEach items="${banXingYu}" var="var">
	                                  		<option value="${var.PKID}" >${var.NAME}</option>
	                                  	</c:forEach>
	                        		</select>
							</div>	
							
								<div class="form-group" id="stu_cc_xkcj" style="display:none;">
	 								<label class="jf_label">校考成绩：</label> 
	 								<div class="checkbox loadTables" id="XKCJCHECKBOX" style="width:570px;">
  									 <c:forEach items="${xkcj}"  var="var">
	                                  		 <label style="margin-right:10px;"><input class="checkbox_xkcj" type="checkbox" value="${var.PKID }">${var.NAME }</label>
	                                 </c:forEach>
									</div>
								
						        </div>	
						        <div class="form-group" id="stu_fw_zxzt" style="display:none;">
	 								<label class="jf_label">在学状态：</label> 
													在学
							        </div>	
						       
						</div>	
							
							
					    </div>
					</div>
					
					
						  <div class="panel panel-info">
						
						<div id="panel_bodys" class="panel-body">
					     <div class="form-inline" style="margin-bottom: 15px;">
						
						   <div class="form-group" id="stu_fw_oldbj" >
	 								<label class="jf_label">备注1：</label> 
	 											<input type="text" name="" id="REMARKS1" class="form-control" placeholder="请输入内容"  maxlength="25"/>
							        </div>	
							           <div class="form-group" id="stu_fw_oldbj" >
	 								<label class="jf_label">备注2：</label> 
	 											<input type="text" name="" id="REMARKS2" class="form-control" placeholder="请输入内容"  maxlength="25"/>
							        </div>	
							           <div class="form-group" id="stu_fw_oldbj" >
	 								<label class="jf_label">备注3：</label> 
	 											<input type="text" name="" id="REMARKS3" class="form-control" placeholder="请输入内容"  maxlength="25"/>
							        </div>	
						
						</div>
						
						
						
						
						</div>
						</div>
						
						
		      <div class="panel panel-info">
		      		<div id="panel_bodys" class="panel-body">
		      			<div id="yuxianshijine" class="form-inline" style="margin-bottom: 15px;">
		                </div>
				    </div>
				</div>
					
			
			<div class="clearfix"></div>
				<div class="modal-footer">
				<center>
					<button class="btn btn-danger" id="btn_save">保存</button>
					</center>
				</div>
			
	</body>
	
<script type="text/javascript">
     $(function(){
    	 $("#SHENFENZHENGHAO").focus();
    	 $("#stu_cc").change(function(){
    		var val=$(this).find("option:selected").text();
    		
    		//获取班型PKID

    		if(val=="应届生"){//应届生
    			$("#stu_cc_ms").css("display","");   //自何时学习美术
    			$("#stu_cc_km").css("display","");   //已学过科目
    			$("#stu_cc_rxnf").css("display",""); //入学年份
    			$("#stu_cc_bx").css("display","");   //班型
    			$("#stu_fw_lzxzt").css("display","");   //在学状态

    			$("#stu_fw_zxzt").css("display","none");   //在学状态
    			$("#stu_fw_ksh").css("display","none");
        		$("#stu_fw_lkfs").css("display","none");
        		$("#stu_fw_oldbj").css("display","none");
    			$("#stu_cc_xkcj").css("display","none");

    			    var tex=$("#BANJI_TYPE_PKID").find("option:selected").text();
    			    if(tex=="暑假班"||tex=="寒假班"){
   					 $("#stu_cc_yjrxnf").css("display",""); //预交入学年份
   					 $("#stu_cc_yjbx").css("display","");   //预交班型
   				 }else{
   					 $("#stu_cc_yjrxnf").css("display","none");
   					 $("#stu_cc_yjbx").css("display","none");
   				 }
    			
    			 $("#BANJI_TYPE_PKID").change(function(){
    				 var tex=$(this).find("option:selected").text();
    		    		var val=$("#stu_cc").find("option:selected").text();
					if(val=="应届生")
    				 if(tex=="暑假班"||tex=="寒假班"){
    					 $("#stu_cc_yjrxnf").css("display",""); //预交入学年份
    					 $("#stu_cc_yjbx").css("display","");   //预交班型
    				 }else{
    					 $("#stu_cc_yjrxnf").css("display","none");
    					 $("#stu_cc_yjbx").css("display","none");
    				 }
    			 });
    			
    			
    			 return;
    		}
    		if(val=="本校复课生"){//本校复课生
        		
        		$("#stu_fw_ksh").css("display","");
        		$("#stu_fw_lkfs").css("display","");
        		$("#stu_fw_oldbj").css("display","");
    			$("#stu_cc_rxnf").css("display","");
    			$("#stu_cc_bx").css("display","");
    			$("#stu_cc_xkcj").css("display","");
    			$("#stu_fw_zxzt").css("display","");   //在学状态

        		
    			$("#stu_fw_lzxzt").css("display","none");   //在学状态

        		$("#stu_cc_ms").css("display","none");
        		$("#stu_cc_km").css("display","none");
        		 $("#stu_cc_yjrxnf").css("display","none");
				 $("#stu_cc_yjbx").css("display","none");
				 return;
        	}
    		if(val=="外校复课生"){//外校复课生
            	
            	$("#stu_fw_ksh").css("display","");
        		$("#stu_fw_lkfs").css("display","");
        		$("#stu_cc_rxnf").css("display","");
    			$("#stu_cc_bx").css("display","");
    			$("#stu_cc_xkcj").css("display","");
    			$("#stu_fw_lzxzt").css("display","");   //在学状态

    			$("#stu_fw_zxzt").css("display","none");   //在学状态
            	$("#stu_cc_ms").css("display","none");
            	$("#stu_cc_km").css("display","none");
        		 $("#stu_cc_yjrxnf").css("display","none");
				 $("#stu_cc_yjbx").css("display","none");
	        		$("#stu_fw_oldbj").css("display","none");
					return;
            }
            	$("#stu_cc_rxnf").css("display","none");
    			$("#stu_cc_bx").css("display","none");
            	$("#stu_fw_ksh").css("display","none");
        		$("#stu_fw_lkfs").css("display","none");
        		$("#stu_fw_oldbj").css("display","none");
            	$("#stu_cc_ms").css("display","none");
            	$("#stu_cc_km").css("display","none");
        		 $("#stu_cc_yjrxnf").css("display","none");
				 $("#stu_cc_yjbx").css("display","none");
	        		$("#stu_fw_oldbj").css("display","none");
	    			$("#stu_cc_xkcj").css("display","none");
	    			$("#stu_fw_zxzt").css("display","none");   //在学状态
	    			$("#stu_fw_lzxzt").css("display","none");   //在学状态

            

    		
    	 });
     });

</script>
	<script type="text/javascript" src="${basepath}static/js/myjs/singupIndex.js?rnd="+ Math.random();></script>
	
	
</html>
