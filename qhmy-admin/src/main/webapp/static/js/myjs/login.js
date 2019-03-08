/**
 *  登录页面 login  js
 */
(function(win, $) {
	
	alert("sdfsdfsdf");
	

	var login = {};

	/**
	 * 登录检查
	 */
	login.severCheck = function() {
		if (login.check()) {
			var loginname = $("#loginname").val();
			var password = $("#password").val();
			var lx = $("#qiehuan").attr("val");
			var code = "zhoudibo" + loginname + ",fh," + password + "zhoudibo"
					+ ",fh," + $("#code").val() + ",fh," + lx;
			$.ajax({
				type : "POST",
				url : 'login_login',
				data : {
					KEYDATA : code,
					tm : new Date().getTime()
				},
				dataType : 'json',
				cache : false,
				success : function(data) {
					if ("success" == data.result) {
						login.saveCookie();
						window.location.href = "main/index";
					} else if ("usererror" == data.result) {
						$("#loginname").tips({
							side : 1,
							msg : "用户名或密码有误",
							bg : '#ff3737',
							time : 15
						});
						$("#loginname").focus();
					} else if ("codeerror" == data.result) {
						$("#code").tips({
							side : 1,
							msg : "验证码输入有误",
							bg : '#ff3737',
							time : 15
						});
						$("#code").focus();
					} else {
						$("#loginname").tips({
							side : 1,
							msg : "缺少参数",
							bg : '#ff3737',
							time : 15
						});
						$("#loginname").focus();
					}
				}
			});
		}

	};
	

	
	/**
	 * 初始化方法
	 */
	login.init = function() {
		login.changeCode();
		
		//初始化layer
		layer.config({
　　			path: "static/js/layer/"
		});
		
		$("#saveid").bind("click",login.savePaw);//cookies记住登录
		
		$("#to-recover").bind("click",login.severCheck);//登录
		
		//忘记密码
		$("#forgetPassword").bind("click",function(){
			alert("dddddddd");
		});
		
		$("#codeImg").bind("click", login.changeCode);
		$("#qiehuan").on("click", function(e) {
			var lx = $("#qiehuan").attr("val");
			if (lx == "1") { //切换到学生登录
				$("#loginname").val("");
				$("#password").val("");
				$("#loginname").attr("placeholder", "请输入学号");
				$("#qiehuan").html("切换到员工登录&nbsp&nbsp&nbsp&nbsp▏");
				$("#qiehuan").attr("val", "2");
			} else if (lx == "2") {
				$("#loginname").val("");
				$("#password").val("");
				$("#qiehuan").html("切换到学生登录&nbsp&nbsp&nbsp&nbsp▏");
				$("#qiehuan").attr("val", "1");
				$("#loginname").attr("placeholder", "请输入用户名");
			}

		});

		$(document).keyup(function(event) {
			if (event.keyCode == 13) {
				$("#to-recover").trigger("click");
			}
		});

		var loginname = $.cookie('loginname');
		var password = $.cookie('password');
		if (typeof (loginname) != "undefined"
				&& typeof (password) != "undefined") {
			$("#loginname").val(loginname);
			$("#password").val(password);
			$("#saveid").attr("checked", true);
			$("#code").focus();
		}

		//TOCMAT重启之后 点击左侧列表跳转登录首页 
		if (window != top) {
			top.location.href = location.href;
		}
	};

	/**
	 * 获得系统时间
	 */
	login.genTimestamp = function() {
		var time = new Date();
		return time.getTime();
	};

	/**
	 * 修改验证码
	 */
	login.changeCode = function() {
		$("#codeImg").attr("src", "code.do?t=" + login.genTimestamp());
	};

	/**
	 * 登录检查
	 */
	login.check = function() {
		if ($("#loginname").val() == "") {
			$("#loginname").tips({
				side : 2,
				msg : '用户名不得为空',
				bg : '#f0ad4e',
				time : 3
			});
			$("#loginname").focus();
			return false;
		} else {
			$("#loginname").val(jQuery.trim($('#loginname').val()));
		}

		if ($("#password").val() == "") {
			$("#password").tips({
				side : 2,
				msg : '密码不得为空',
				bg : '#f0ad4e',
				time : 3
			});
			$("#password").focus();
			return false;
		}
		
		if ($("#code").val() == "") {
			$("#code").tips({
				side : 1,
				msg : '验证码不得为空',
				bg : '#f0ad4e',
				time : 3
			});
			$("#code").focus();
			return false;
		}

		$("#loginbox").tips({
			side : 1,
			msg : '正在登录 , 请稍后 ...',
			bg : '#68B500',
			time : 10
		});
		return true;
	};

	/**
	 * 保存密码
	 */
	login.savePaw = function() {
		if (!$("#saveid").attr("checked")) {
			$.cookie('loginname', '', {
				expires : -1
			});
			$.cookie('password', '', {
				expires : -1
			});
			$("#loginname").val('');
			$("#password").val('');
		}
	};

	/**
	 * 保存到cookie
	 */
	login.saveCookie = function() {
		if ($("#saveid").attr("checked")) {
			$.cookie('loginname', $("#loginname").val(), {
				expires : 7
			});
			$.cookie('password', $("#password").val(), {
				expires : 7
			});
		}
	};

	/**
	 * 注销
	 */
	login.quxiao = function() {
		$("#loginname").val('');
		$("#password").val('');
	};

	login.init();

})(window, jQuery);

//服务器校验
