$(function(){
	
	/**
	 * 人员对象
	 */
	
	Renyuan = function(option){
		this.ops = {gonghao:"",pkid:"",mingcheng:""};
		$.extend(this.ops,option);
		
		this.gonghao = this.ops.gonghao;
		this.pkid = this.ops.pkid;
		this.mingcheng = this.ops.mingcheng;
		this.shifouzhika = false;//是否制卡,默认为false
		this.qx = null;
	};
	//end 人员对象
	
	
	/**
	 * 容器类,用来存储要制卡的人员信息
	 */
	 Rongqi = function(){
		//先进先出
		this.rq = new Array();
		
		/**
		 * 添加元素
		 */
		this.add = function(renyuan){
			this.rq[this.rq.length] = renyuan;
		};
		
		/**
		 * 删除最开始的元素
		 */
		this.remove = function(){
			this.rq.splice(0,1);//删除顶部元素
		};
		
		/**
		 * 获得当前元素
		 */
		this.getCurrent = function(){
			return this.rq[0];
		};
		
		/**
		 * 获得容器大小
		 */
		this.size = function(){
			return this.rq.length;
		};
		
	};
	//end 容器类,用来存储要制卡的人员信息
	
	
	/**
	 * 制卡定时器
	 */
	 _zhika_timer = null;
	
	/**
	 * 容器类实例
	 */
	_zhika_rongqi = null;
	
	/**
	 * 制卡页面基本操作对象
	 */
	
	zhika = {};
	
	/**
	 * 工作状态  是否在执行业务操作的状态
	 */
	zhika.working = false;
	
	
	/**
	 * 模块框
	 */
	zhika.dialogInstance = null;
	
	/**
	 * 停止定时器
	 */
	zhika.stopTimer = function(){
		if(_zhika_timer!=null){
			window.clearInterval(_zhika_timer);
	    }
	};
	
	/**
	 * 产生随机数
	 */
	zhika.suijishu = function(){
		var rst = Math.round(Math.random()*10000);
		return rst;
	};
	
	/**
	 * 获取门岗IDS
	 */
	zhika.joinMengangPkid = function(){
		var s='';
		$("input[name='fuxuan']:checked").each(function(){
			if(this.checked == true){
				s+=this.value+','; //如果选中，将value添加到变量s中
			}
		});
		if(s!=null && s!="" && s.length>0){
			s.substring(0, s.length-1);
		}
		return s;
	};
	
	/**
	 * 获得卡号 寻卡
	 */
	zhika.getM1kahao = function(){
//		console.log("zhika.getM1Kahao 1");
//		try{
//			dukaqi.exit();//先执行关闭设备
//		}catch(e){
//			console.log(e.message);//测试代码
//		}
		try{
//			console.log("寻卡中...");//测试代码
			//打开设备
//			var version = dukaqi.connect();
			//获得卡号
			var cardNumber = dukaqi.scard();
			cardNumber = cardNumber.toString();
			
			if(cardNumber!=null && cardNumber.length==8){
				zhika.stopTimer();//停止timer 执行业务逻辑方法
				zhika.excuteZhika(cardNumber);//执行制卡的业务逻辑方法
			}else{
				//读卡错误情况，是否提示用户,让用户换卡或重新放卡????
				
				//...................................
			}
		}catch(e){
//			console.log(e.message);//测试代码
		}finally{
			var iRet = dukaqi.closeCard();//关闭卡
			if(iRet){
				//关闭失败
			}
//			dukaqi.exit();//执行关闭设备
		}
		return cardNumber;
	};
	
	
	/**
	 * 重新开启定时器  判断容器中是否有待制卡的用户
	 */
	zhika.resetTimer = function(){
		/*
		 * 判断是否容器中还有是否需要制卡的人员，如果有,重启定时器;否则;提示用户,制卡完成,关闭模态框
		 */
		var checkRst = zhika.checkRongqi();//检查容器
		if(checkRst){
			//重启定时器
			_zhika_timer = window.setInterval("zhika.getM1kahao()",3000);//每三秒执行一次
		}else{
			BootstrapDialog.show({
				title:'提示信息',
	            message: '制卡已完成，已没有要执行制卡的人员！',
	            closable: true, 
		        buttons: [
		            {
					    label: '关闭',
					    cssClass: 'keman_btn',
					    action: function(dialogRef){
					       //点击关闭后，定时任务重新开启
					    	dialogRef.close();
					    }
		            }
		        ]
			});
			if(zhika.dialogInstance!=null){
		    	zhika.dialogInstance.close();//关闭模态框
		    }
			return;
		}
		//end 判断是否容器中还有是否需要制卡的人员，如果有,重启定时器;否则;提示用户,制卡完成,关闭模态框
	};
	
	
	/**
	 * 执行制卡的业务逻辑方法
	 * 执行完成后,判断是否容器中还有是否需要制卡的人员，如果有,重启定时器;否则;提示用户,制卡完成,关闭模态框
	 */
	zhika.excuteZhika = function(cardNumber){
		var checkRst = zhika.checkRongqi();//检查容器
		var rst = null;
		if(checkRst){
			//执行核心业务,绑卡操作
			try{
				rst = zhika.excuteZhikaCore(cardNumber);
				//判断是否制卡成功
				if(rst == "1"){
					//绑卡成功了,从容器删除制卡信息,开始定时器，继续执行
					BootstrapDialog.show({
						title:'提示信息',
			            message: '用户['+_zhika_rongqi.getCurrent().mingcheng+']制卡成功!',
			            closable: true,
			            onhidden: function(dialogRef){
			            	dialogRef.close();
			            	_zhika_rongqi.remove();//删除当前操作的用户
					    	zhika.resetTimer();//执行重新启动定时器
			            },
				        buttons: [
				            {
							    label: '关闭',
							    cssClass: 'keman_btn',
							    action: function(dialogRef){
							       //点击关闭后，定时任务重新开启
							    	dialogRef.close();
							    }
				            }
				        ]
					});
					return;
				}
				//end 判断是否制卡成功!
			}catch(e){
				if(e == "cardNumber is use"){
					//卡片已经绑定,提示用户更换卡
					BootstrapDialog.show({
						title:'提示信息',
			            message: '用户['+_zhika_rongqi.getCurrent().mingcheng+']制卡失败,原因:该卡片已经被绑定,请更换卡片',
			            closable: true, 
				        buttons: [
				            {
							    label: '关闭',
							    cssClass: 'keman_btn',
							    action: function(dialogRef){
							       //点击关闭后，定时任务重新开启
							    	dialogRef.close();
							    	zhika.resetTimer();//执行重新启动定时器
							    }
				            }
				        ]
					});
				}
				/*else if(e == "user is carded"){
					//该人员已制卡，并且卡状态为正常
					BootstrapDialog.show({
						title:'提示信息',
			            message: '用户['+_zhika_rongqi.getCurrent().mingcheng+']制卡失败,原因:该人员已制卡且卡状态正常,请更换人员',
			            closable: true, 
				        buttons: [
				            {
							    label: '关闭',
							    cssClass: 'keman_btn',
							    action: function(dialogRef){
							       //点击关闭后，定时任务重新开启
							    	dialogRef.close();
							    	zhika.resetTimer();//执行重新启动定时器
							    }
				            }
				        ]
					});
				}*/
				else if(e == "bangka is error"){
					//绑卡失败  服务端错误,就闭关制卡层吧
//					console.log("zhika.excuteZhika异常");
					BootstrapDialog.show({
						title:'提示信息',
			            message: '用户['+_zhika_rongqi.getCurrent().mingcheng+']制卡失败,原因:服务端异常,请联系管理员',
			            closable: true, 
				        buttons: [
				            {
							    label: '关闭',
							    cssClass: 'keman_btn',
							    action: function(dialogRef){
							       //点击关闭后，定时任务重新开启
							    	dialogRef.close();
							    }
				            }
				        ]
					});
					if(zhika.dialogInstance!=null){
				    	zhika.dialogInstance.close();//关闭模态框
				    }
				}else{
//					console.log("zhika.excuteZhika异常");
					BootstrapDialog.show({
						title:'提示信息',
			            message: e,
			            closable: true, 
				        buttons: [
				            {
							    label: '关闭',
							    cssClass: 'keman_btn',
							    action: function(dialogRef){
							       //点击关闭后，定时任务重新开启
							    	dialogRef.close();
							    }
				            }
				        ]
					});
					if(zhika.dialogInstance!=null){
				    	zhika.dialogInstance.close();//关闭模态框
				    }
				}
			}finally{
				
			}
			//end 执行核心业务,绑卡操作
		}
		//end 执行核心业务,绑卡操作
		
		
	};
	
	/**
	 * 执行核心业务处理方法
	 */
	zhika.excuteZhikaCore = function(cardNumber){
		var renyuanObj = _zhika_rongqi.getCurrent();
		
		//验证该卡号是否已经绑定
		var param = {};
		param.renyuan = renyuanObj;
		param.cardNumber = cardNumber;
		var checkRst = zhika.checkIsBangding(param);
		if(checkRst == null || checkRst == "" || checkRst == undefined){
			checkRst = -1*1;
		}else{
			checkRst = checkRst*1;
		}
		if(checkRst == -2){
			throw "user is carded";
		}
		if(checkRst == -1){
			//服务端异常,制卡失败
			throw "服务端验证出现异常,制卡失败!";
		}
		
		if(checkRst>0){
			//该卡已经被其他用户绑定,请换卡
			throw "cardNumber is use";
		}
		//end 验证该卡号是否已经绑定
		
		
		/*
		 * 如果该卡号没有被使用,将用户和卡号进行绑定,提示制卡结果
		 */
		var rst = 0;
		rst = zhika.bingding(param);
		if(rst == null || rst == "" || rst == undefined){
			rst = -1*1;
		}else{
			rst = rst*1;
		}
		
		if(rst == -1){
			//制卡绑卡失败
			throw "bangka is error";
		}
		
		
		return rst;
		//end 如果该卡号没有被使用,将用户和卡号进行绑定,提示制卡结果
	};
	
	
	/**
	 * 用户与卡号与权限绑定
	 */
	zhika.bingding = function(param){
		var datas = new Array();
		datas[datas.length] = {name:"laowurenyuan_pkid",value:param.renyuan.pkid};
		datas[datas.length] = {name:"laowurenyuan_gonghao",value:param.renyuan.gonghao};
		datas[datas.length] = {name:"qx",value:param.renyuan.qx};//门区权限
		datas[datas.length] = {name:"kahao",value:param.cardNumber};
		
		var rst = null;
		$.ajax({
		   type: "POST",
		   url: "labour/bingding.json",
		   async: false,
		   dataType:"json",
		   data: datas,
		   success: function(msg){
		     rst = msg.result;
		   }
		});
		return rst;
	};
	
	
	
	/**
	 * 关闭打开设备 返回设备版本号
	 */
	zhika.closeOpenShebei = function(){
//		console.log("打开设备");//测试代码
		try{
			dukaqi.exit();//先执行关闭设备
		}catch(e){
//			console.log(e.message);//测试代码
		}
		//打开设备
		var version = dukaqi.connect();
//		console.log("设备号:"+version);//测试代码
		return version;
	};
	
	/**
	 * 检查是否已经绑定了此卡,是否可用状态
	 */
	zhika.checkIsBangding = function(param){
		var datas = new Array();
		datas[datas.length] = {name:"laowurenyuan_pkid",value:param.renyuan.pkid};
		datas[datas.length] = {name:"laowurenyuan_gonghao",value:param.renyuan.gonghao};
		datas[datas.length] = {name:"kahao",value:param.cardNumber};
		
		var rst = null;
		$.ajax({
		   type: "POST",
		   url: "labour/checkCardIsBangding.json",
		   async: false,
		   dataType:"json",
		   data: datas,
		   success: function(msg){
		     rst = msg.result;
		   }
		});
		return rst;
	};
	
	
	/**
	 * 检查容器类,是否还有待执行制卡的人员
	 */
	zhika.checkRongqi = function(){
		if(_zhika_rongqi!=null && _zhika_rongqi.size()>0){
			return true;
		}
		return false;
	};
	
	
	/**
	 * 断开设备
	 */
	zhika.closeShebei = function(){
//		console.log("断开设备");//测试代码
		try{
			dukaqi.exit();//先执行关闭设备
//			console.log("断开成功!");//测试代码
		}catch(e){
//			console.log(e.message);//测试代码
		}
	};
	
	/**
	 * end  制卡页面基本操作对象  制卡
	 */
	window.zhika = zhika;
	
	
	/**
	 * 制卡按钮事件
	 */
	$("button.btn-select-made-card").click(function(){
		/**
		 * 先获得要制卡的人员数据
		 * 创建人员对象
		 * 创建人员制卡容器对象
		 */
		//1、模拟测试数据，此处需要具体业务代码改写    获得  t_laowurenyuan 劳务人员表中的员工工号，或 pkid
		//var laowurenyuan_pkid = zhika.suijishu().toString();
		//var laowurenyuan_gonghao = "G"+zhika.suijishu().toString();
		
		//2、获取权限区  t_mengang_rule 获取pkids
		var mengang_pkids = zhika.joinMengangPkid();
		
		if(mengang_pkids == null || mengang_pkids == ""){
			BootstrapDialog.show({
				title:'提示信息',
	            message: '请选择要制卡的门区权限',
	            closable: true, 
		        buttons: [
		            {
					    label: '关闭',
					    cssClass: 'keman_btn',
					    action: function(dialogRef){
					       //点击关闭后，定时任务重新开启
					    	dialogRef.close();
					    }
		            }
		        ]
			});
			return;
		}
		
		var pid=$("#PKID").val();
		var gh=$(".lbr-worker-number").html();
		var name=$("#name").val();
		//创建人员对象
		option = {};
		option.gonghao = gh;
		option.pkid = pid;
		option.mingcheng = name;
		renyuan = new Renyuan(option);
		renyuan.qx = mengang_pkids;//门区权限,放到用户对象中吧
		
		//创建待制卡的容器对象
		_zhika_rongqi = new Rongqi();
		_zhika_rongqi.add(renyuan);//加入一个人员
		//end 
		
		var checkRst = zhika.checkRongqi();//检查容器
		
		if(checkRst){
			/**
			 *  制卡弹出层
			 */
			zhika.dialogInstance = BootstrapDialog.show({
				title:'写IC卡',
	            message: $('<div></div>').load("zhika/zhikatip.json"),
	            closable: true, 
	            onshown: function(dialogRef){
//	            	console.log("onshown start");
	            	//加载内容完成后执行
	            	var version = zhika.closeOpenShebei();//关闭打开设备
//	            	console.log("onshown start 2");
//	            	console.log("onshown start 3"+version);
	            	if(version!=null){//此处判断需要修改,验证是否正常打开设备
	            		//打开定时器，循环寻卡
	            		if(_zhika_timer == null){
	            			_zhika_timer = window.setInterval("zhika.getM1kahao()",3000);//每三秒执行一次
//	            			console.log("dingshiqihou");
	            		}else{
//	            			console.log("dingshiqihou 2");
	            			zhika.stopTimer();//停止timer
	            			_zhika_timer = window.setInterval("zhika.getM1kahao()",3000);//每三秒执行一次
	            		}
	            		//end 打开定时器，循环寻卡
	            	}
	            },
		        buttons: [
		            {
					    label: '关闭',
					    cssClass: 'keman_btn',
					    action: function(dialogRef){
					       zhika.closeShebei();//关闭设备
					       if(_zhika_timer!=null){
					    	   window.clearInterval(_zhika_timer);
					       }
					       dialogRef.close();
					       if(zhika.dialogInstance!=null){
					    	   zhika.dialogInstance = null;
					       }
					    }
		            }
		        ]
			});
			//end 制卡弹出层
			
			
			
			//页面加载完成后, 先关闭设备,打开设备, 开启定时器
			//定时器内容,每3秒执行寻卡操作,寻到卡后,停止定时器,执行寻卡后的业务逻辑,绑定人卡，删除容器中的人,完成业务操作后，重新开启定时器,继续寻卡
			//判断卡号是否返回成功，判断位数  5FC5B768  长度为8位
			//setTimeout()、clearTimeout()、setInterval()、clearInterval()。
			//window.setInterval("checkXinxx()", 60000);
		}else{
			BootstrapDialog.show({
				title:'提示信息',
	            message: '请选择需要制卡的人员!',
	            closable: true, 
		        buttons: [
		            {
					    label: '关闭',
					    cssClass: 'keman_btn',
					    action: function(dialogRef){
					       //点击关闭后，定时任务重新开启
					    	dialogRef.close();
					    }
		            }
		        ]
			});
			return;
		}
	});
	//end 制卡按钮事件
	
	
	
	//测试阻断
	$("input.ceshi").click(function(){
		BootstrapDialog.show({
			title:'提示信息',
            message: '制卡失败，原因:该卡片已经被绑定，请更换卡片！',
            closable: true, 
	        buttons: [
	            {
				    label: '关闭',
				    cssClass: 'keman_btn',
				    action: function(dialogRef){
				       //点击关闭后，定时任务重新开启
				    	dialogRef.close();
				    }
	            }
	        ]
		});
		
	});
	
	
	
});

