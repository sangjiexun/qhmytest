//自定义ie9兼容问题
(function(window){  
    var jsPlaceHolder={};  
    jsPlaceHolder.addListener = function(e, n, o, u){  
        if(e.addEventListener){  
            e.addEventListener(n, o, u);  
            return true;  
        } else if(e.attachEvent){  
            e.attachEvent('on' + n, o);  
            return true;  
        }  
        return false;  
    };  
    jsPlaceHolder.removeListener = function(e, n, o, u){  
        if(e.addEventListener){  
            e.removeEventListener(n, o, u);  
            return true;  
        } else if(e.attachEvent){  
            e.detachEvent('on' + n, o);  
            return true;  
        }  
        return false;  
    };  
    jsPlaceHolder.trim = function(str){  
        return str.replace(/^\s+|\s+$/,"");  
    };  
    //oTxtId:文本框id或者Dom元素 ;option: 获得焦点color值 与失去焦点的color值  
    jsPlaceHolder.play=function(oTxtId,option){  
        var _this=this;  
        if(!oTxtId) return false;  
        //检测placeHolder支持情况  
        if("placeholder" in document.createElement("input")){  
            return false;  
        };  
        var setting={  
            focusColor:"#333",  
            blurColor:"#999"  
        };  
        option = option||{};  
        for(var key in setting){  
            if(!option[key]){  
                option[key] = setting[key];  
            }  
        };  
        var oTxt=null;  
        if(typeof(oTxtId)!="string" && oTxtId.nodeType==1){  
            oTxt = oTxtId;  
            oTxtId=oTxt.id;  
        }  
        if(!oTxt){  
            oTxt = document.getElementById(oTxtId);  
            if(!oTxt) return false;  
        }  
        var blurTxt = _this.trim(oTxt.getAttribute("placeHolder"));  
        oTxt.value = blurTxt;  
        oTxt.style.color=setting.blurColor;  
        _this.addListener(oTxt,"focus",function(){  
            if(_this.trim(oTxt.value)==blurTxt){  
                oTxt.value="";  
                oTxt.style.color=setting.focusColor;  
            }  
        });  
        _this.addListener(oTxt,"blur",function(){  
            if(_this.trim(oTxt.value)==blurTxt || _this.trim(oTxt.value)==""){  
                oTxt.value=blurTxt;  
                oTxt.style.color=setting.blurColor;  
            }  
        })  
    }  
    window.jsPlaceHolder = jsPlaceHolder;  
})(window);  