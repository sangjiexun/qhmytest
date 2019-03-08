//@ sourceURL=topmain.js  
$(document).ready(function() {
	 layer.load(1, {
		  shade: [0.1,'#fff'] //0.1透明度的白色背景
		});
	 $("#cdli li").eq(0).addClass("jf_active");  //第二个li
	 $("#nameli").load(_basepath+"main/gotopmain.php");
 });
 
 function js_method(namelist,zzname,menuname,e){
	 //非首页，隐藏侧边栏
	
	 $(".topmenu").removeClass("jf_active");
	 $(e).parent().addClass('jf_active');
	 $.ajaxSetup({
			cache:false
	 });
	 layer.load(1, {
		  shade: [0.1,'#fff'] //0.1透明度的白色背景
		});
	 try {
		$("#nameli").load(_basepath + namelist,
				function(response, status, xhr) {
			closeLoading();
				});
	} catch (e) {
		alert(e)
		closeLoading();
	}
	 
	 
	
 }
 function closeLoading(){
	 layer.closeAll('loading');
 }
 var flag=true;
 $("#editpwd").bind("click",function(){
	 var blg= BootstrapDialog.show({  //显示需要提交的表单。
		   	title:'修改密码',
		    message: $('<div></div>').load('goEditPwd.json'),
		    closable: true, 
		      buttons: [{
			    label: '提交',
			    cssClass: 'btn-danger',
			    action: function(dialogRef){
		        savePwd();
		        if(flag==true){
		        	dialogRef.close();
		        }
		             
		        }
		  }, {
		    label: '关闭',
		    cssClass: 'btn-default',
		    action: function(dialogRef){
		       dialogRef.close();
		    }
		  }
		  ]
		    });
 });
 
 
//保存密码
 function savePwd() {
	var reg = /^\s+$/g;
	var oldpwd = $("#oldpwd").val();
	var newpwd = $("#newpwd").val();
	var confirmpwd = $("#confirmpwd").val();
	if (oldpwd.length == 0 || reg.test(oldpwd)) {
		layer.msg("请输入原密码！")
		flag = false;
		return;
	}
	if (newpwd.length == 0 || reg.test(newpwd)) {
		layer.msg("请输入新密码")
		flag = false;
		return;
	}
	if (confirmpwd.length == 0 || reg.test(confirmpwd)) {
		layer.msg("请再次输入新密码")
		flag = false;
		return;
	}
	if (newpwd != confirmpwd) {
		layer.msg("两次新密码输入不一致！")
		flag = false;
		return;
	}
	$.ajax({
		type : "post",

		url : "saveNewPwd.json",
		data : {
			oldpwd : oldpwd,
			newpwd : newpwd,
			confirmpwd : confirmpwd
		},
		success : function(mv) {

			if ("success" == mv.result) {
				BootstrapDialog.show({
					title : '提示信息',
					message : '修改成功,为了你的账户安全请使用新密码重新登录(5s后自动退出)！',
					closable : true,
					buttons : [ {
						label : '关闭',
						cssClass : 'btn-danger',
						action : function(dialogRef) {
							dialogRef.close();
						}
					} ]
				});
				setTimeout(function() {
					$.get("logout.json",function(){});
					window.location.reload()
				}, 5000);

			}
			if ("faild" == mv.result) {
				layer.msg("原密码输入有误！")
				flag = false;
				return;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});

}