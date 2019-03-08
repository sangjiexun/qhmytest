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

<style type="text/css">
.modal-dialog {
    width:600px !important;
}
.keman_tupian{
	transform: translate(-50%, -50%) !important;
    -webkit-transform: translate(-50%, -50%);
    -moz-transform: translate(-50%, -50%);
    -ms-transform: translate(-50%, -50%);
    -o-transform: translate(-50%, -50%);
	position:absolute;
	top:50%;
	left:50%;
}
 .keman_btn{
 	height:35px;
 }
 .keman_imbox{
 	background: #b8b8b8;
    display: block;
    width: 460px;
    margin: 0 auto;
    /* height: 380px; */
    height: 318px;
    margin-top: 15px;
    margin-bottom: 15px;
    position: relative;
 }
 .keman_loa{
 	position: absolute;
    top: 44%;
    left: 44%;
  }
  .keman_xuanq {
    position: absolute;
    top: 36%;
    left: 40%;
    font-size: 80px;
    color: #e5e5e5;
}
.modal-body{
	overflow-x:hidden !important;
}
</style>

<link href="${basepath}static/css/imgareaselect-animated.css" rel="stylesheet">
<script type="text/javascript">
    var _basepath = "${basepath}";
</script>
</head>
<body class="no-skin">
	<div class="container" style="margin-left:41px;">
	    <div class="action">
	        <div class="new-contentarea tc">
	            <a href="javascript:void(0)" class="upload-img">
	            	                <c:if test="${pd.text eq 'tx'}">
	            
	                <label class="btn btn-danger" for="upload-file">选择头像</label>
	                	                </c:if>
	                
	                
	                <c:if test="${pd.text eq 'sfz'}">
	                     <label class="btn btn-danger" for="upload-file">选择身份证</label>
	                </c:if>
	            </a>
	            
	            <input type="hidden" id="is_text" value="${pd.text }">
	            <input type="file" name="upload-file" id="upload-file" style="display: none;" >
	            <span class="fa fa-exclamation-circle" id="tuodongshubiao" style="display: none;color:#f59942;margin-left:40px;font-size:15px;"> 点击拖动鼠标，裁剪照片</span>
	        </div>
	    </div>
	   
	</div>
	<div class="imageBox keman_imbox">
	        <div class="thumbBox"><span class="fa fa-picture-o keman_xuanq"></span></div>
	        <div class="spinner keman_loa"></div>
	         <input type ="hidden" id= "width" value ="0"/>  
             <input type ="hidden" id= "height" value ="0"/>  
             <input type ="hidden" id="x" value= "0"/>  
             <input type ="hidden" id="y" value= "0"/>
    </div>
    
    <input hidden="hidden"  id="path" value="">
        <input hidden="hidden"  id="sfzpath" value="">
    
    
    
	<script src="${basepath}static/js/myjs/jquery.imgareaselect.js" type="text/javascript"></script>
    <script src="static/js/myjs/ajaxfileupload.js" type="text/javascript"></script>
    <script src="static/js/myjs/uploadstutouxiang.js" type="text/javascript"></script>
	<script type="text/javascript">
		
	</script>
	
</body>
</html>
