//@ sourceURL=sontable.js 
/**
 * 本页面的js脚本
 * @param $
 * @param window
 */
(function($,window){
	//表格操作对象
	function KemanTable(option){
		this.def = {
			tableName:'',//表格ID
			buttonZj:null,//追加按钮
			buttonSc:null,//删除按钮
			buttonCr:null //插入按钮
		};
		
		this.config = {};
		
	 	$.extend(this.config,this.def,option);
	 	
	  	this.tableObj = $("#"+this.config.tableName);//表格对象
	  	
	  	this.buttonZj = this.config.buttonZj;
	  	this.buttonSc = this.config.buttonSc;
	  	this.buttonCr = this.config.buttonCr;
	  	
	  	this.mobanTr = this.tableObj.find("tbody").find("tr.moban");//模板行
	  	
	  	var self = this;
	  	var tablename=self.tableObj.selector;
	  	//注册行点击事件
	  	this.tableObj.find("tbody").find("tr:not(.moban)").each(function(){
	  		if(!$(this).hasClass("moban")){
	  			//如果不是模板行
	  			$(this).click(function(){
		  			//行点击事件
		  			KemanTable.trClick($(this),self.tableObj);
		  		});
	  		}
	  	});
	  	
	  	
	  	//追加行
	  	this.buttonZj.click(function(){
	  		//在表格尾部动态创建行
	  		var $tr = self.mobanTr.clone();
	  		$tr.removeClass("moban");
	  		$tr.css("display","");
	  		$tr.appendTo(self.tableObj);
	  		self.zhuceTrClickEvent($tr);
	  		//注册Input等事件
	  		//self.zhuceColEvent($tr);
	  	});
	  	
	  	/**
	  	 * 注册input等组件事件
	  	 */
	  	this.zhuceColEvent = function($tr){
	  		var datetimeOption = {
	  		        language:  'zh-CN',
	  		        weekStart: 1,
	  		        todayBtn:  1,
	  				autoclose: 1,
	  				todayHighlight: 1,
	  				startView: 1,
	  				maxView:1,
	  				minView:0,
	  				minuteStep:1,
	  				forceParse: 0,
	  				format:'hh:ii',
	  		        showMeridian: 1
	  		};
	  		
	  		$tr.find("input.datetime").datetimepicker(datetimeOption);//为事件控件注册事件
	  		
	  		
	  	};
	  	
	  	//删除行
	  	this.buttonSc.click(function(){
	  		var $selectTr = self.tableObj.find("tbody").find("tr.trselect");
	  		if($selectTr == null || $selectTr.size()==0 ){
	  			BootstrapDialog.show({
					title : '提示信息',
					message : '请选择一行进行操作！',
					closable : true,
					buttons : [ {
						label : '关闭',
						cssClass : 'btn-warning keman_btn',
						action : function(dialogRef) {
							dialogRef.close();
						}
					} ]
				});
	  			return null;
	  		}
	  		if($selectTr[0].id=="nodelete"){
	  			BootstrapDialog.show({
					title : '提示信息',
					message : '该条记录禁止删除！',
					closable : true,
					buttons : [ {
						label : '关闭',
						cssClass : 'btn-warning keman_btn',
						action : function(dialogRef) {
							dialogRef.close();
						}
					} ]
				});
	  			return null;
	  		}
	  		
	  		
	  		BootstrapDialog.show({
	        	title: '提示信息',
	        	closable: false, 
	        	message: '确认删除？',
	        	buttons: [{
	            label: '确定',
	           	cssClass: 'btn-primary keman_btn',
	            action: function(dialog) {
	            dialog.close();	  		
		  		$selectTr.remove();
		  		layer.msg('删除成功！')
	            }
	       		 },
	       		 {
	            label: '取消',
	           	cssClass: 'btn-primary keman_btn',
	            action: function(dialog) {
	              dialog.close();
	            }
	       		 }]
	   		 });
	  	});
	  	
	  	
	  	//插入行
	  	this.buttonCr.click(function(){
	  		var $selectTr = self.tableObj.find("tbody").find("tr.trselect");
	  		if($selectTr == null || $selectTr.size()==0){
	  			BootstrapDialog.show({
	  				title: '提示信息',
	  				message: '请选择一行进行操作！',
	  				closable:true,
	  			buttons: [{
			    label: '关闭',
			    cssClass: 'btn-warning keman_btn',
			    action: function(dialogRef){
			       dialogRef.close();
			    }
			  }
			  ] 
	  					});
	  			return null;
	  		}
	  		if($selectTr[0].id=="nodelete"){
	  			BootstrapDialog.show({
	  				title: '提示信息',
	  				message: '请在该行之后插入！',
	  				closable:true,
	  			buttons: [{
			    label: '关闭',
			    cssClass: 'btn-warning keman_btn',
			    action: function(dialogRef){
			       dialogRef.close();
			    }
			  }
			  ] 
	  					});
	  			return null;
	  		}
	  		var $tr = self.mobanTr.clone();
	  		$tr.removeClass("moban");
	  		$tr.css("display","");
	  		$tr.insertBefore($selectTr);
	  		self.zhuceTrClickEvent($tr);
	  		
	  		self.zhuceColEvent($tr);
	  	});
	  	
	  	
	  	this.addTr = function(){
	  		var $tr = this.mobanTr.clone();
	  		$tr.removeClass("moban");
	  		$tr.appendTo(this.tableObj);
	  		this.zhuceTrClickEvent($tr);
	  	};
	  	
	  	
	  	this.zhuceTrClickEvent = function($tr){
  			//如果不是模板行
	  		$tr.click(function(){
	  			//行点击事件
	  			KemanTable.trClick($(this),self.tableObj);
	  		});
	  	};
	  	
	}
	
	/**
	 * 行点击事件
	 */
	KemanTable.trClick = function($tr,$table){
		//取消样式
		$table.find("tbody").find("tr").each(function(){
			$(this).removeClass("trselect");//删除行选中样式
			$(this).css("background-color","white");
		});
		$tr.addClass("trselect");//添加选中行样式
		$tr.css("background-color","#DCDCDC");
	};
	
	var flag=true;
	var flag2=true;
	var flagDate=true;
	var flagDate2=true;
	var flagzcsj=true;
	/**
	 * 将表格数据行，转换为json对象数组
	 */
	KemanTable.prototype.toArray = function(b){
		var tempArray = new Array();
		var tempArray2 = new Array();
		var resarry=new Array();
		var tempDate=null;
		var tempDate2=null;
		//获取考勤类型
		var kqlx=$("input[name='optionsRadios']:checked").val();
			//如果是标准考勤
			if(kqlx==1){
				$('#sontableTable').find("tbody").find("tr:not(.moban)").each(function(){
					var pkid        = $(this).children().eq(0).children().eq(2).children().eq(3).children().eq(0)[0].value;
					var starttime   = $(this).children().eq(0).children().eq(0).children().eq(1).children().eq(0)[0].value;
					var endtime     = $(this).children().eq(0).children().eq(0).children().eq(3).children().eq(0)[0].value;
					var zaigongyxsj = $(this).children().eq(0).children().eq(1).children().eq(1).children().eq(0)[0].value;
//					var qianhousj   = $(this).children().eq(0).children().eq(2).children().eq(1).children().eq(0)[0].value;
					var qianhousj   =30;
					var gong        = $(this).children().eq(0).children().eq(2).children().eq(1).children().eq(0)[0].value;
					var startarray=new Array();
					var endarray=new Array();
					startarray=starttime.split(':');
					endarray=endtime.split(':');
					var time1=Number(startarray[0])*60+Number(startarray[1]);
					var time2=Number(endarray[0])*60+Number(endarray[1]);
					if(time1>=time2){
						
						flagDate=false;
						return;
					}
					if(tempDate!=null && tempDate >time1){
						flagDate=false;
						return;
					}
						tempDate=time2;
					
//					if(qianhousj<30){
//						
//						flag=false;
//						return;
//					}
					if(gong==""){
						flag2=false;
						return;
					}
					starttime=starttime+":00";
					endtime=endtime+":59";
					var dates1=starttime.split(":"); 
					var dates2=endtime.split(":"); 
					var start=dates1[0]*3600+dates1[1]*60+dates1[2]*1;
					var end=dates2[0]*3600+dates2[1]*60+dates2[2]*1;
					if(zaigongyxsj*60>(end-start)){
						flagzcsj=false;
						return;
					}
					var a= '{"PKID":"'+pkid+'","STARTDATE":"'+starttime+'","ENDDATE":"'+endtime+'","ZAIGONGYXSJ":"'+zaigongyxsj+'","QIANHOUSJ":"'+qianhousj+'","GONG":"'+gong+'"}';
					$.parseJSON(a);
					tempArray.push(a);
					
				})
				var arry=JSON.stringify(tempArray);
				return arry
				
			}
			//如果是在场时间考勤
			if(kqlx==3){
				$('#sontableTable2').find("tbody").find("tr:not(.moban)").each(function(){
					var pkid        = $(this).children().eq(0).children().eq(1).children().eq(0).children().eq(0)[0].value;
					var starttime   = $(this).children().eq(0).children().eq(0).children().eq(1).children().eq(0)[0].value;
					var endtime     = $(this).children().eq(0).children().eq(0).children().eq(3).children().eq(0)[0].value;
//					var qianhousj   = $(this).children().eq(0).children().eq(1).children().eq(1).children().eq(0)[0].value;
					var qianhousj   =30;
					var startarray=new Array();
					var endarray=new Array();
					startarray=starttime.split(':');
					endarray=endtime.split(':');
					var time1=Number(startarray[0])*60+Number(startarray[1]);
					var time2=Number(endarray[0])*60+Number(endarray[1]);
					
					if(time1>=time2){
						
						flagDate2=false;
						return;
					}
					if(tempDate2!=null && tempDate2 >time1){
						flagDate2=false;
						return;
					}
						tempDate2=time2;
					
					
					
					
					
					starttime=starttime+":00";
					endtime=endtime+":59";
					var a= '{"PKID":"'+pkid+'","STARTDATE":"'+starttime+'","ENDDATE":"'+endtime+'","QIANHOUSJ":"'+qianhousj+'"}';
					$.parseJSON(a);
					tempArray.push(a);
					
					
				})
				var arry=JSON.stringify(tempArray);
					resarry.push(arry);
				$('#sontableTable3').find("tbody").find("tr:not(.moban)").each(function(){
					var pkid        = $(this).children().eq(0).children().eq(3).children().eq(3).children().eq(0)[0].value;
					var dyxs        = $(this).children().eq(0).children().eq(0).children().eq(1).children().eq(0)[0].value;
					var xyxs        = $(this).children().eq(0).children().eq(2).children().eq(1).children().eq(0)[0].value;
					var gong        = $(this).children().eq(0).children().eq(3).children().eq(1).children().eq(0)[0].value;
					var a= '{"PKID":"'+pkid+'","DYXS":"'+dyxs+'","XYXS":"'+xyxs+'","GONG":"'+gong+'"}';
					
					$.parseJSON(a);
					tempArray2.push(a);
					
				})
				var arry2=JSON.stringify(tempArray2);
					resarry.push(arry2)
				
			}
			
	
		return resarry;
		
		
		
	};
	
	$('#save').bind("click",function(){
		//获取考勤类型
		var kqlx=$("input[name='optionsRadios']:checked").val();
		//获取考勤日
		var kqrq=$('#mySelect').children('option:selected').val();
		//获取考勤规则
		//var rule=$("input[name='ruleRadios']:checked").val();
		//进出设置
		var jcsz='N';
		if($("#jcsz")[0].checked==true){
			jcsz='Y';
		}
//		alert(jcsz)
		//发送请求
		/*$.get(_basepath+"kaoqinshezhi/updateKaoqinsz.json?kqrq="+kqrq+"&kqlx="+kqlx+"&rule="+rule,
  				function(data,status){
  			});*/
		//标准考勤
		if(kqlx==1){
			var arry=KemanTable.prototype.toArray(this);
					if (flagDate == false) {
						BootstrapDialog.show({
							title : '提示信息',
							message : '起止时间段设置不符合要求!',
							closable : true,
							buttons : [ {
								label : '关闭',
								cssClass : 'btn-warning keman_btn',
								action : function(dialogRef) {
									dialogRef.close();
								}
							} ]
						});
						flagDate = true;
						return;
					}
			if(flag==false){
				BootstrapDialog.show({
					title : '提示信息',
					message : '打卡有效时间不能少于30分钟!',
					closable : true,
					buttons : [ {
						label : '关闭',
						cssClass : 'btn-warning keman_btn',
						action : function(dialogRef) {
							dialogRef.close();
						}
					} ]
				});
				flag=true;
				return;
			}
			if(flag2==false){
				BootstrapDialog.show({
					title : '提示信息',
					message : '工时不能为空!',
					closable : true,
					buttons : [ {
						label : '关闭',
						cssClass : 'btn-warning keman_btn',
						action : function(dialogRef) {
							dialogRef.close();
						}
					} ]
				});
				flag2=true;
				return;
			}
			if(flagzcsj==false){
				BootstrapDialog.show({
					title : '提示信息',
					message : '在场时间设置不符合要求!',
					closable : true,
					buttons : [ {
						label : '关闭',
						cssClass : 'btn-warning keman_btn',
						action : function(dialogRef) {
							dialogRef.close();
						}
					} ]
				});
				flagzcsj=true;
				return;
			}
			
			
			 
			$.ajax({
				traditional:true,
				type:"post",
				data:{'data':arry},
				url:_basepath+"kaoqinshezhi/save.json?kqrq="+kqrq+"&kqlx="+kqlx+"&jcsz="+jcsz,
			    success:function(data){
			    	if(data.rst=='success'){
			    		BootstrapDialog.show({
							title : '提示信息',
							message : '保存成功！',
							closable : true,
							buttons : [ {
								label : '关闭',
								cssClass : 'btn-warning keman_btn',
								action : function(dialogRef) {
									dialogRef.close();
								}
							} ]
						});
			    		$('#sontableTable').bootstrapTable('resetView');
		  			}
		  			if(data.rst=='error'){
		  				BootstrapDialog.show({
							title : '提示信息',
							message : '保存失败！',
							closable : true,
							buttons : [ {
								label : '关闭',
								cssClass : 'btn-warning keman_btn',
								action : function(dialogRef) {
									dialogRef.close();
								}
							} ]
						});
		  			}
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		}
		else if (kqlx==2){
			//简单考勤
			var gong=$("#gong").val();
			
				$.get(_basepath+"kaoqinshezhi/save.json?kqrq="+kqrq+"&kqlx="+kqlx+"&gong="+gong+"&jcsz="+jcsz,
		  				function(data,status){
		  			if(data.rst=='success'){
		  				BootstrapDialog.show({
							title : '提示信息',
							message : '保存成功！',
							closable : true,
							buttons : [ {
								label : '关闭',
								cssClass : 'btn-warning keman_btn',
								action : function(dialogRef) {
									dialogRef.close();
								}
							} ]
						});
		  			}
		  			if(data.rst=='error'){
		  				BootstrapDialog.show({
							title : '提示信息',
							message : '保存失败！',
							closable : true,
							buttons : [ {
								label : '关闭',
								cssClass : 'btn-warning keman_btn',
								action : function(dialogRef) {
									dialogRef.close();
								}
							} ]
						});
		  			}
		  			
		  			});
		}else if(kqlx==3){
			var arry=KemanTable.prototype.toArray(this);
			if (flagDate2 == false) {
				BootstrapDialog.show({
					title : '提示信息',
					message : '起止时间段设置不符合要求!',
					closable : true,
					buttons : [ {
						label : '关闭',
						cssClass : 'btn-warning keman_btn',
						action : function(dialogRef) {
							dialogRef.close();
						}
					} ]
				});
				flagDate2 = true;
				return;
			}
			$.ajax({
				traditional:true,
				type:"post",
				data:{'data':arry[0],'data2':arry[1]},
				url:_basepath+"kaoqinshezhi/save.json?kqrq="+kqrq+"&kqlx="+kqlx+"&jcsz="+jcsz,
			    success:function(data){
			    	if(data.rst=='success'){
			    		BootstrapDialog.show({
							title : '提示信息',
							message : '保存成功！',
							closable : true,
							buttons : [ {
								label : '关闭',
								cssClass : 'btn-warning keman_btn',
								action : function(dialogRef) {
									dialogRef.close();
								}
							} ]
						});
		  				$('#sontableTable2').bootstrapTable('resetView');
		  				$('#sontableTable3').bootstrapTable('resetView');
		  			}
		  			if(data.rst=='error'){
		  				BootstrapDialog.show({
							title : '提示信息',
							message : '保存失败！',
							closable : true,
							buttons : [ {
								label : '关闭',
								cssClass : 'btn-warning keman_btn',
								action : function(dialogRef) {
									dialogRef.close();
								}
							} ]
						});
		  			}
				  },
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				           alert(errorThrown); 
				       }
				});
		}
		$("#nameli").load(_basepath+"kaoqinshezhi/sontable.php");//回到list界面
		
	 });
	//考勤类型设置 和 下边panel联动
	$('input[type=radio][name=optionsRadios]').change(function() {
		var kqlx=$(this).val();
			$('.kqlx').removeClass("active");
			$('.tab-pane').removeClass("active");
			if (kqlx==1) {
			
				$("#biaozhun").addClass("active");
				$("#panel-101204").addClass("active");
				bzkqtable();
			} else if(kqlx==2) {
				$("#jiandan").addClass("active");
	        	$("#panel-204765").addClass("active");
			} else {
				$("#zaigong").addClass("active");
	        	$("#panel-204769").addClass("active");
	        	dksdtable();
			}
		
	})
	
	 
	 //考勤类型和panel联动
	 $('input[type=radio][name=optionsRadios]').each(function(index){
		if ($("input[name='optionsRadios']").get(index).value == kqtype) {
	        //$("input[name='optionsRadios']").get(index).checked = true;
	        if(kqtype==2){
	        	$("#jiandan").addClass("active");
	        	$("#panel-204765").addClass("active");
	        }else if(kqtype==3){
	        	$("#zaigong").addClass("active");
	        	$("#panel-204769").addClass("active");
	        	dksdtable();
	        }else {
	        	$("#biaozhun").addClass("active");
	        	$("#panel-101204").addClass("active");
	        	 bzkqtable();
	        }
	    }
	})
	checkkqlx();
	function checkkqlx(){
		if(kqtype==1){
			$('#optionsRadios1')[0].checked=true;
			 //bzkqtable();
		}else if(kqtype==2){
			$('#optionsRadios2')[0].checked=true;
		}else{
			$('#optionsRadios3')[0].checked=true;
			//dksdtable();
		}
	}
	 	
	//penel和考勤类型联动
	$('.kqlx').click(function(){
		var id=$(this).attr('id');
		if(id=='biaozhun'){
			$('#optionsRadios1').click();
			 bzkqtable();
		}else if(id=='jiandan'){
			$('#optionsRadios2').click();
		}else{
			$('#optionsRadios3').click();
			dksdtable();
        	
		}
		
	});
	
	//根据单选框的值 给 radio加checked属性
	$('input[type=radio][name=ruleRadios]').each(function(index){
		if ($("input[name='ruleRadios']").get(index).value == rule) {
	        $("input[name='ruleRadios']").get(index).checked = true;
	    }
	})
	
	
	
	
	 
	window.KemanTable = KemanTable;
})(jQuery,window);

//获取标准考勤table数据
function bzkqtable(){
	var tr='';
	//先删除已有的数据避免重复
	$('#bztbody').find("tr:not(.moban)").each(function(){
		$(this).remove();  
	})
	$.get(_basepath+'kaoqinshezhi/getbzkqtable.json?kqlx=1',function(data){
		var bzlist=data.bzlist;
		for(var i=0;i<bzlist.length;i++){
			tr='<tr onclick=dianji(this);>'+
				'<td>'+
					'<div class="col-md-4 column">'+
						'<div style="display:inline;float: left;line-height:28px;">从&nbsp</div>'+
						'<div style="display:inline;float: left;">'+
						'<input name="startDate" type="text" class="input-sm  keman_kaos Wdate" onfocus="WdatePicker({dateFmt:\'H:mm\'})" id="'+bzlist[i].PKID+'" '+
						'style="width: 100px;"  placeholder="开始时间" value="'+bzlist[i].STARTDATE +'">'+
						'</div>'+
						'<div style="display:inline;float: left;line-height:28px;">&nbsp至&nbsp</div>'+
						'<div style="display:inline;float: left;">'+
						'	<input name="endDate" type="text" style="width: 100px;" class="input-sm  keman_kaos Wdate" onfocus="WdatePicker({dateFmt:\'H:mm\'})" id="'+bzlist[i].PKID+i+'" placeholder="结束时间" value="'+bzlist[i].ENDDATE+'">'+
						'</div>'+
					'</div>'+
					
					
					'<div class="col-md-4 column">'+
					'	<div style="display:inline;float: left;line-height:28px;">在场时间超过</div>'+
					'	<div style="display:inline;float: left;">'+
						'	<input name="yxdate" type="text" style="width: 100px;" class="input-sm keman_kaos" placeholder="文本输入" value="'+bzlist[i].ZAIGONGYXSJ+'">'+
					'	</div>'+
					'	<div style="display:inline;float: left;line-height:28px;">有效</div>'+
					'</div>'+
					
					
					
					
					
					'<div class="col-md-4 column">'+
					'	<div style="display:inline;float: left;line-height:28px;">计</div>'+
					'	<div style="display:inline;float: left;">'+
					'		<input name="gong" type="text" style="width: 100px;" class="input-sm keman_kaos" placeholder="文本输入" value="'+bzlist[i].GONG +'">'+
					'	</div>'+
						'<div style="display:inline;float: left;line-height:28px;">个工</div>'+
					'	<div style="display:none">'+
					'	    <input name="pkid" type="text" value="'+bzlist[i].PKID+'">'+
					'	</div>'+
					'</div>'+
				'</td>'+
			'</tr>'
				$(tr).appendTo('#bztbody');
			
			
		}
		
	})
}
//标准考勤table行点击事件
function dianji(e){
	
		$('#sontableTable').find("tbody").find("tr").each(function(){
			$(this).removeClass("trselect");//删除行选中样式
			$(this).css("background-color","white");
			
		});
		$(e).addClass("trselect");//添加选中行样式
		$(e).css("background-color","#DCDCDC");
		
	
}
//在工考勤打卡片段table行点击事件
function dianji2(e){
	
		$('#sontableTable2').find("tbody").find("tr").each(function(){
			$(this).removeClass("trselect");//删除行选中样式
			$(this).css("background-color","white");
			
		});
		$(e).addClass("trselect");//添加选中行样式
		$(e).css("background-color","#DCDCDC");
		
	
}


//获取在场时间考勤，打卡时段设置table数据
function dksdtable(){
	
	var tr='';
	//先删除已有的数据避免重复
	$('#dksdtbody').find("tr:not(.moban)").each(function(){
		$(this).remove();  
	})
	$.get(_basepath+'kaoqinshezhi/getbzkqtable.json?kqlx=3',function(data){
		var dksdlist=data.dksdlist;
		for(var i=0;i<dksdlist.length;i++){
			  tr= '<tr onclick="dianji2(this)">'+
				'<td>'+
				'<div class="col-md-3 column">'+
					'<div style="display:inline;float: left;line-height:28px;">从&nbsp</div>'+
					'<div style="display:inline;float: left;">'+
					'	<input name="startDate" type="text" class="input-sm Wdate" onfocus="WdatePicker({dateFmt:\'H:mm\'})" '+
					'		style="width: 100px;" id="'+dksdlist[i].PKID+'" placeholder="开始时间" value="'+dksdlist[i].STARTDATE+'">'+
					'</div>'+
					'<div style="display:inline;float: left;line-height:28px;">&nbsp至&nbsp</div>'+
					'<div style="display:inline;float: left;">'+
					'	<input name="endDate" type="text" style="width: 100px;" onfocus="WdatePicker({dateFmt:\'H:mm\'})" id="'+dksdlist[i].PKID+i+'" class="input-sm Wdate" placeholder="" value="'+dksdlist[i].ENDDATE+'">'+
					'</div>'+
			'	</div>'+	
			'	<div class="col-md-3 column">'+
				'	<div style="display:none">'+
				'		<input name="pkid" type="text" value="'+dksdlist[i].PKID+'">'+
				'	</div>'+
				'</div>'+	
			'</td>'+
		'</tr>'
			$(tr).appendTo('#dksdtbody');
			}
		gzqjtable(data.gzqjlist)
	})
	
}
//在工时间考勤--工作区间设置
function gzqjtable(a){
	var gzqjlist=a;
	var tr='';
	//先删除已有的数据避免重复
	$('#gzqjtbody').find("tr:not(.moban)").each(function(){
		if($(this)[0].id=='nodelete'){
			return true;
		}
		$(this).remove();  
	})
	for(var i=0;i<gzqjlist.length;i++){
		tr+='<tr>'+
		'<td>	'+										
		'<div class="col-md-3 column">'+
			'<div style="display:inline;float: left;line-height:28px;">大于&nbsp</div>'+
			'<div style="display:inline;float: left;">'+
				'<input name="yxdate" type="text" style="width: 100px;" class="input-sm" placeholder="文本输入" value="'+gzqjlist[i].DYXS+'">'+
			'</div>'+
			'<div style="display:inline;float: left;line-height:28px;">&nbsp个小时</div>'+
		'</div>'+
		'<div class="col-md-3 column" style="line-height:28px;">且</div>'+
		'<div class="col-md-3 column">'+
			'<div style="display:inline;float: left;line-height:28px;">小于&nbsp</div>'+
			'<div style="display:inline;float: left;">'+
				'<input name="yxdate" type="text" style="width: 100px;" class="input-sm" placeholder="文本输入" value="'+gzqjlist[i].XYXS +'">'+
			'</div>'+
			'<div style="display:inline;float: left;line-height:28px;">&nbsp个小时</div>'+
		'</div>'+
		
		'<div class="col-md-3 column">'+
			'<div style="display:inline;float: left;line-height:28px;">计&nbsp</div>'+
			'<div style="display:inline;float: left;">'+
			'	<input name="gong" type="text" style="width: 100px;" class="input-sm" placeholder="文本输入" value="'+gzqjlist[i].GONG+'">'+
			'</div>'+
			'<div style="display:inline;float: left;line-height:28px;">&nbsp个工</div>'+
			'<div style="display:none">'+
			 '   <input name="pkid" type="text" value="'+gzqjlist[i].PKID+'">'+
			'</div>'+
		'</div>'+
	'</td>'+
'</tr>'
	}
	$(tr).appendTo('#gzqjtbody');
	
}


/**
 * 本页面业务json对象
 */
var sontable = {};

sontable.kemanTable = null;
sontable.kemanTable2 = null;
sontable.kemanTable3 = null;

$(function(){
	
	var datetimeOption = {
	        language:  'zh-CN',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 1,
			startDate: "1970-1-1",
			maxView:1,
			minView:0,
			minuteStep:1,
			forceParse: 0,
			format:'hh:ii',
	        showMeridian: 1
	};
	
//	$('.datetime').datetimepicker(datetimeOption);
	
	var option = {
		tableName:'sontableTable',//表格ID
		buttonZj:$("#zj1"),//追加按钮
		buttonSc:$("#sc1"),//删除按钮
		buttonCr:$("#cr1") //插入按钮
	};
	var option2 = {
			tableName:'sontableTable2',//表格ID
			buttonZj:$("#zj2"),//追加按钮
			buttonSc:$("#sc2"),//删除按钮
			buttonCr:$("#cr2") //插入按钮
		};
	var option3= {
			tableName:'sontableTable3',//表格ID
			buttonZj:$("#zj3"),//追加按钮
			buttonSc:$("#sc3"),//删除按钮
			buttonCr:$("#cr3") //插入按钮
		};
	sontable.kemanTable = new KemanTable(option);
	sontable.kemanTable2 = new KemanTable(option2);
	sontable.kemanTable3 = new KemanTable(option3);
	
	$.ajaxSetup({  
	    async : false //取消异步  
	});  
});





