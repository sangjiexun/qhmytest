/**
 * 上传学生头像
 */

$(function(){
	
	$("#upload-file").change(function(e){
		var is_text=$("#is_text").val();
		
		if(is_text=="sfz"){
			fileChanges();  
		}else{
			fileChange();  
		}
		
    });
	
	function fileChange(){
		if(isPicType($('#upload-file').val())){
			$.ajaxFileUpload({
	            url : _basepath+'signUp/upload.json',
	            fileElementId : 'upload-file',// 文件选择框的id属性  
	            dataType:'json',// 服务器返回的格式，可以是JSON 但是注意必须是大写 
	            secureuri : false, 
	            async:false,
	            type : 'post',  
	            // data : {"operation":1},  
	            success : function(data, status) {
	            	try{
		                if (data.result == 1) {
		                	$('.thumbBox').hide();
		                	$('.spinner').hide();
		                    $('#preview-img').remove();  
		                    $("#tuodongshubiao").show();
		                    $('<img  id="preview-img" class="keman_tupian" />').insertAfter($('#width')); 
		                    var random = Math.random();
		                    $("#preview-img").attr("src", _basepath+data.path+'?_rn='+random);
		                    // ---根据高宽 设置 等比例  
		                    $("#preview-img").removeAttr("style");
		                    if (data['scale'] > 1.0) {  
		                        $("#preview-img").css(data.style, "100%");  
		                    }  
		                    $('#preview-img').imgAreaSelect({  
		                        aspectRatio : '3:4',  
		                        handles : true,  
		                        x1 : 0,y1 : 0,x2 : 0,y2 : 0,  
		                        onSelectEnd : function(img, selection) {
		                            $('#x').val(selection.x1 * data.scale);  
		                            $('#y').val(selection.y1 * data.scale); 
		                            $('#width').val(selection.width * data.scale);  
		                            $('#height').val(selection.height * data.scale);  
		                        }  
		                    });
		                    $("#path").val(data.path);

		                    
		                }  
	            	}catch(e){
	            		alert("出现异常，异常原因:"+e);
	            	}
	            },  
	            error:function(data, status, e){
	            	alert("出现异常，异常原因:"+e);
	            }  
	        });
			
			$("#upload-file").unbind();
			
			$("#upload-file").change(function(e){
				fileChange();  
		    });
			
	    }
	}
	function fileChanges(){
		if(isPicType($('#upload-file').val())){
			$.ajaxFileUpload({
	            url : _basepath+'signUp/upload.json',
	            fileElementId : 'upload-file',// 文件选择框的id属性  
	            dataType:'json',// 服务器返回的格式，可以是JSON 但是注意必须是大写 
	            secureuri : false, 
	            async:false,
	            type : 'post',  
	            // data : {"operation":1},  
	            success : function(data, status) {
	            	try{
		                if (data.result == 1) {
		                	$('.thumbBox').hide();
		                	$('.spinner').hide();
		                    $('#preview-img').remove();  
		                    $("#tuodongshubiao").show();
		                    $('<img  id="preview-img" class="keman_tupian" />').insertAfter($('#width')); 
		                    var random = Math.random();
		                    $("#preview-img").attr("src", _basepath+data.path+'?_rn='+random);
		                    // ---根据高宽 设置 等比例  
		                    $("#preview-img").removeAttr("style");
		                    if (data['scale'] > 1.0) {  
		                        $("#preview-img").css(data.style, "100%");  
		                    }  
		                    $('#preview-img').imgAreaSelect({  
		                        aspectRatio : '4:2.5',  
		                        handles : true,  
		                        x1 : 0,y1 : 0,x2 : 0,y2 : 0,  
		                        onSelectEnd : function(img, selection) {
		                            $('#x').val(selection.x1 * data.scale);  
		                            $('#y').val(selection.y1 * data.scale); 
		                            $('#width').val(selection.width * data.scale);  
		                            $('#height').val(selection.height * data.scale);  
		                        }  
		                    });
		                    $("#sfzpath").val(data.path);

		                    
		                }  
	            	}catch(e){
	            		alert("出现异常，异常原因:"+e);
	            	}
	            },  
	            error:function(data, status, e){
	            	alert("出现异常，异常原因:"+e);
	            }  
	        });
			
			$("#upload-file").unbind();
			
			$("#upload-file").change(function(e){
				fileChanges();  
		    });
			
	    }
	}
//---判断文件是否是图片  错误使用notify悬浮框插件显示   
function isPicType(picValue) {  
    var allowExtention = new Array("jpg", "png");  
    var len = picValue.length;  
    var math = picValue.substring(len - 3, len);  
    var f = false;  
    for (var i = 0; i < allowExtention.length; i++) {  
        if (allowExtention[i] == math) {  
            f = true;  
        }  
    }  
    if (!f) {  
        $.notify({  
            title : '<strong>Failure!  </strong>',  
            message : 'Please choose correct image format.'  
            }, {  
            type : 'danger',  
            z_index : 2000  
       });  
    }  
    return f;  
};  
});