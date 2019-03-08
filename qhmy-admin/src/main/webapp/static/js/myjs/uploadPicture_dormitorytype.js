/*
*介绍:基于JQUERY扩展,图片上传预览插件 目前兼容浏览器(IE 谷歌 火狐) 不支持safari
*插件网站:http://keleyi.com/keleyi/phtml/image/16.htm
*参数说明: Img:图片ID;Width:预览宽度;Height:预览高度;ImgType:支持文件类型;Callback:选择文件显示图片后回调方法;
*使用方法: 
<div>
<img id="ImgPr" width="120" height="120" /></div>
<input type="file" id="up" />
把需要进行预览的IMG标签外 套一个DIV 然后给上传控件ID给予uploadPreview事件
$("#up").uploadPreview({ Img: "ImgPr", Width: 120, Height: 120, ImgType: ["gif", "jpeg", "jpg", "bmp", "png"], Callback: function () { }});
*/
function ajaxFileUploadImg(obj,img){
	var id = obj.find(".file").attr("id");
	var name = obj.find(".file").attr("name");//这个用来区份图片是属于哪个子项
	var url = _basepath+"dormitorytype/uploadPicture.json?name="+name+"&id="+id;
	$.ajaxFileUpload({
        url : url,
        fileElementId : id,// 文件选择框的id属性  
        dataType:'json',// 服务器返回的格式，可以是JSON 但是注意必须是大写 
        secureuri : false, 
        async:false,
        type : 'post',  
        success : function(data) {
	    	if(data.result=='success'){
	    		obj.find(".file").attr("path",data.path);
	    		obj.find(".imgpic").attr("alt",data.path);
	    		$("#IS_UPLOAD_PIC").val("Y");
	    	}
	    },    
	    error: function(data){
	    	
	    }    
	});   
}
	jQuery.fn.extend({
	    uploadPreview: function (opts,object) {
	        var _self = this,
	            _this = $(this);
	        opts = jQuery.extend({
	            Img: "ImgPr",
	            Width: 100,
	            Height: 100,
	            ImgType: ["gif", "jpeg", "jpg", "bmp", "png"],
	            Callback: function () {}
	        }, opts || {});
	        _self.getObjectURL = function (file) {
	            var url = null;
	            if (window.createObjectURL != undefined) {
	                url = window.createObjectURL(file)
	            } else if (window.URL != undefined) {
	                url = window.URL.createObjectURL(file)
	            } else if (window.webkitURL != undefined) {
	                url = window.webkitURL.createObjectURL(file)
	            }
	            return url
	        };
	        _this.change(function () {
	            if (this.value) {
	                if (!RegExp("\.(" + opts.ImgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
	                    alert("选择文件错误,图片类型必须是" + opts.ImgType.join("，") + "中的一种");
	                    this.value = "";
	                    return false
	                }
	                if ($.support.msie) {
	                    try {
	                    	object.find("." + opts.Img).attr('src', _self.getObjectURL(this.files[0]));
	                    	ajaxFileUploadImg(object,opts.Img);
	                    } catch (e) {
	                        var src = "";
	                        var obj = object.find("." + opts.Img);
	                        var div = obj.parent("div")[0];
	                        _self.select();
	                        if (top != self) {
	                            window.parent.document.body.focus()
	                        } else {
	                            _self.blur()
	                        }
	                        src = document.selection.createRange().text;
	                        document.selection.empty();
	                        obj.hide();
	                        obj.parent("div").css({
	                            'filter': 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)',
	                            'width': opts.Width + 'px',
	                            'height': opts.Height + 'px'
	                        });
	                        div.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = src
	                    }
	                } else {
	                	object.find("." + opts.Img).attr('src', _self.getObjectURL(this.files[0]));
	                	ajaxFileUploadImg(object,opts.Img);
	                }
	                opts.Callback()
	            }
	        })
	    }
	});
	$(function(){
		$("#child").on("click",".btn_tx1",function(){
			$(this).parent().find(".file").uploadPreview({ Img: "touxiang1", Width: 99, Height: 133 },$(this).parent());
			$(this).parent().find(".file").click();
		});
		$("#child").on("click",".btn_tx2",function(){
			$(this).parent().find(".file").uploadPreview({ Img: "touxiang2", Width: 99, Height: 133 },$(this).parent());
			$(this).parent().find(".file").click();
		});
		$("#child").on("click",".btn_tx3",function(){
			$(this).parent().find(".file").uploadPreview({ Img: "touxiang3", Width: 99, Height: 133 },$(this).parent());
			$(this).parent().find(".file").click();
		});
		$("#child").on("click",".btn_tx4",function(){
			$(this).parent().find(".file").uploadPreview({ Img: "touxiang4", Width: 99, Height: 133 },$(this).parent());
			$(this).parent().find(".file").click();
		});
		 
	});