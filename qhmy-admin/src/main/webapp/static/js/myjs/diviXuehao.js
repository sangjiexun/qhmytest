/**
 * 分班对象
 */
//@ sourceURL=diviXuehao.js
(function($, window) {
	var assignedClass = {};
	
	$("#grade").bind("change", function() {
		changeGrade();
	});
	$("#BANJI_TYPE_PKID").bind("change", function() {
		changeGrade();
	});
	
	//获取选中的行
	assignedClass.getIdSelections=function () {
			var $table = $('#assignedClassTable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.PKID;
        });
	};
	//获取选中的行的入学年份
	assignedClass.getNianJiSelections=function () {
			var $table = $('#assignedClassTable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.NIANJI_BM;
        });
	};
	
	//获取选中的行的院校专业
	assignedClass.getDepSelections=function () {
			var $table = $('#assignedClassTable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.DEPARTMENT_PKID;
        });
	};
	
	//查询按钮点击事件
	$('#assignedClassQuery').click(function(){
		//选择的入学年份
		var grade=$('#grade').val();
		//院校专业pkids
		var orgtree=$('#orgtree').val();
		//班级
		var BANJI = $("#BANJI").val();
		//是否分班
		var IS_FENXUEHAO = $('#IS_FENXUEHAO').val();
		//关键词
		var keywords = $("#keywords").val();
		//性别
		var BANJI_TYPE_PKID=$("#BANJI_TYPE_PKID").val();
		//报到状态
		var IS_RECEIVED=$("#IS_RECEIVED").val();
		var grade_str='';
			$('#grade option:selected').each(function() {
            grade_str+=$(this).attr('idd')+',';
        });
			grade=grade_str;
			
			var banxing=$("#BANJI_TYPE_PKID").val();
			var banxing_str='';
			if(banxing!=null){
				for(var i=0;i<banxing.length;i++){
					banxing_str+=banxing[i]+',';
				}
			}
			BANJI_TYPE_PKID = banxing_str;
			
			var banji_str='';
			if(BANJI!=null){
				for(var i=0;i<BANJI.length;i++){
					banji_str+=BANJI[i]+',';
				}
			}
			BANJI = banji_str;
		$("#assignedClassTable").bootstrapTable('refresh', {url: encodeURI(_basepath+"assignedClass/diviXuehaoTable.json?grade="+grade+"&&orgtree="+orgtree+"&&BANJI="+BANJI+"&keywords="+keywords+"&IS_FENXUEHAO="+IS_FENXUEHAO+"&BANJI_TYPE_PKID="+BANJI_TYPE_PKID+"&IS_RECEIVED="+IS_RECEIVED)});
		
	});
	
	//导出功能
	$("#checkOut").click(function(){
		//选择的入学年份
		var grade=$('#grade').val();
		//院校专业pkids
		var orgtree=$('#orgtree').val();
		//班级
		var BANJI = $("#BANJI").val();
		//是否分班
		var IS_FENXUEHAO = $('#IS_FENXUEHAO').val();
		var IS_JIAOFEI = $('#IS_JIAOFEI').val();
		//关键词
		var keywords = $("#keywords").val();
		//性别
		var BANJI_TYPE_PKID=$("#BANJI_TYPE_PKID").val();
		//报到状态
		var IS_RECEIVED=$("#IS_RECEIVED").val();
		//批次
		var CENGCI = $("#CENGCI").val();
		//迎新
		var YX = $("#yx").val();
		var grade_str='';
			$('#grade option:selected').each(function() {
            grade_str+=$(this).attr('idd')+',';
        });
			grade=grade_str;
			
			var banxing=$("#BANJI_TYPE_PKID").val();
			var banxing_str='';
			if(banxing!=null){
				for(var i=0;i<banxing.length;i++){
					banxing_str+=banxing[i]+',';
				}
			}
			BANJI_TYPE_PKID = banxing_str;
			
			var banji_str='';
			if(BANJI!=null){
				for(var i=0;i<BANJI.length;i++){
					banji_str+=BANJI[i]+',';
				}
			}
			BANJI = banji_str;
		//alert(YX);
		window.location.href=encodeURI(_basepath+'assignedClass/exportDiviXuehaoExcel.json?keywords='+keywords+
				'&&IS_FENXUEHAO='+IS_FENXUEHAO+'&&BANJI='+BANJI+'&&YX='+YX+
				"&&orgtree="+orgtree+"&&grade="+grade+"&BANJI_TYPE_PKID="+BANJI_TYPE_PKID+"&IS_RECEIVED="+IS_RECEIVED+"&CENGCI="+CENGCI+"&IS_JIAOFEI="+IS_JIAOFEI);
	});
	
	//分班按钮点击事件
	$("#btn_fenban").click(function(){
		var pkids=assignedClass.getIdSelections();
		if(pkids==null || pkids ==''){
			layer.msg("请选择至少一名学生");
			return false;
		}
		var nianjis = assignedClass.getNianJiSelections();
		var deps = assignedClass.getDepSelections();
		var arrnianji = new Array();
		var flag = true;
		var nianji = "";
		var tempn = "";
		$.each(nianjis,function(i,item){
			tempn = nianji;
			nianji = item;
			if(tempn!='' && nianji!=tempn){
				layer.msg("请选择相同的入学年份");
				flag = false;
				return flag;
			}
		});
		if(!flag){
			return false;
		}
		var arrdep = new Array();
		var dep = "";
		var temp = "";
		$.each(deps,function(i,item){
			temp = dep;
			dep = item;
			if(temp!='' && dep!=temp){
				layer.msg("请选择相同的院校专业");
				flag = false;
				return flag;
			}
		});
		if(!flag){
			return false;
		}
		var dialog = BootstrapDialog.show({
			title:'分班',
			message: $('<div></div>').load("assignedClass/godiviClass.json?",
					{SYS_DICTIONARIES_PKID:nianji,
				     SYS_DEPARTMENT_PKID:dep
				     }),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
	            label: '确定',
	            cssClass: 'btn-danger',
	            action: function(dialog) {
	            	var banji = $("#banji").val();
	            	if(banji=="" || banji==null){
	            		layer.msg("班级不能为空");
	            		return false;
	            	}
	            	BootstrapDialog.show({
	            		title:'提示信息',
	        			message: '待分班人数'+pkids.length+"人，确定分班吗？",
	        			closable: true,//是否显示叉号
	        			draggable: true,//可以拖拽
	        			buttons: [{
	        	            label: '确定',
	        	            cssClass: 'btn-danger',
	        	            action: function(dialog2) {
	        	            	dialog2.close();
	        	            	$.ajax({
	        	            		type:'post',
	        	            		dataType:'json',
	        	            		data:{"BANJI":banji},
	        	            		url: _basepath+'assignedClass/diviClass.json?pkids='+pkids,
	        	            		success:function(data){
	        	            			if(data.result=='success'){
	        	            				layer.msg("分班成功");
	        	            				//选择的入学年份
	        	            				var grade=$('#grade').val();
	        	            				//院校专业pkids
	        	            				var orgtree=$('#orgtree').val();
	        	            				//班级
	        	            				var BANJI = $("#BANJI").val();
	        	            				//是否分班
	        	            				var IS_FENXUEHAO = $('#IS_FENXUEHAO').val();
	        	            				var IS_JIAOFEI = $('#IS_JIAOFEI').val();
	        	            				//关键词
	        	            				var keywords = $("#keywords").val();
	        	            				//性别
	        	            				var BANJI_TYPE_PKID=$("#BANJI_TYPE_PKID").val();
	        	            				//报到状态
	        	            				var IS_RECEIVED=$("#IS_RECEIVED").val();
	        	            				//批次
	        	            				var CENGCI = $("#CENGCI").val();
	        	            				//迎新
	        	            				var YX = $("#yx").val();
	        	            				var grade_str='';
	        				      			$('#grade option:selected').each(function() {
	        				                    grade_str+=$(this).attr('idd')+',';
	        				                });
	        				      			grade=grade_str;
	        				      			
	        				      			var banxing=$("#BANJI_TYPE_PKID").val();
	        				      			var banxing_str='';
	        				      			if(banxing!=null){
	        				      				for(var i=0;i<banxing.length;i++){
	        				      					banxing_str+=banxing[i]+',';
	        				      				}
	        				      			}
	        				      			BANJI_TYPE_PKID = banxing_str;
	        				      			
	        				      			var banji_str='';
	        				      			if(BANJI!=null){
	        				      				for(var i=0;i<BANJI.length;i++){
	        				      					banji_str+=BANJI[i]+',';
	        				      				}
	        				      			}
	        				      			BANJI = banji_str;
	        	            				$("#assignedClassTable").bootstrapTable('refresh', {url: encodeURI(_basepath+"assignedClass/exportDiviXuehaoExcel.json?keywords="+keywords+"&&IS_FENXUEHAO="+IS_FENXUEHAO+"&&BANJI="+BANJI+"&&YX="+YX+
				"&&orgtree="+orgtree+"&&grade="+grade+"&BANJI_TYPE_PKID="+BANJI_TYPE_PKID+"&IS_RECEIVED="+IS_RECEIVED+"&CENGCI="+CENGCI+"&IS_JIAOFEI="+IS_JIAOFEI)});
	        	            				dialog.close();
	        	            			}
	        	            		},
	        	            		error:function(a,error,c){
	        	            			alert(error);
	        	            		}
	        	            	});
	        	            }
	        			 },
	        			 {
		        	            label: '取消',
		        	            cssClass: 'btn-danger',
		        	            action: function(dialog2) {
		        	            	dialog2.close();
		        	            }
		        		 }
	        			 ]
	        		});
	            }
	        }]
		});
	});
	
	
	 var t;
	 var time = 50;
	 var timer_is_on=1;
	 function progressXuehao(dialog2){
			if(timer_is_on == 1){
	 			t=setTimeout(function(){progressXuehao(dialog2)},300);
	 			ajaxFenXuehao(dialog2);
	 		}else{
	 			clearTimeout(t);
	 			timer_is_on=1;	
	 		}
	 	
	 }
	   function ajaxFenXuehao(dialog2){
	   	
	   	var xmlhttp;
	   	if (window.XMLHttpRequest)
	   	   {// code for IE7+, Firefox, Chrome, Opera, Safari
	   	   xmlhttp=new XMLHttpRequest();
	   	   }
	   	 else
	   	   {// code for IE6, IE5
	   	   xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	   	   }
	   	xmlhttp.onreadystatechange = function(){
	   		if (xmlhttp.readyState==4)
	   	    {
	   	    	//alert(xmlhttp.status);
	   	    	if(xmlhttp.status == 200){
	   	    		
	   	    		//alert(xmlhttp.responseText+'~~~~');
	   	    		if(xmlhttp.responseText >= 100){
	   	    			$("#prog").removeClass("progress-bar-success").css("width","0%").text("分学号成功");
	   	    			clearTimeout(t);
	   	    			timer_is_on=0;
	   	    		}
	   	    		if(xmlhttp.responseText < 101){
	   	    			startProgerss(xmlhttp.responseText);
	   	    		}
	   	    	}
	   	    }
	   	}
	   	xmlhttp.open("post",  _basepath+'assignedClass/progress.json');
	   	xmlhttp.send(null);
	}
	   function reset( ) {
	       //value = 0
	         $("#prog").removeClass("progress-bar-success").css("width","0%").text("等待分学号");
	         //setTimeout(increment,5000);
	     }
	 
	   function startProgerss(value){
	    if(value<=100){
	       	$("#prog").css("width",value + "%").text(value + "%");
	   	 }else{
	           setTimeout(reset,3000);
	           return;
	       }
	   }
	   
	//分学号按钮点击事件
	$("#btn_fenxuehao").click(function(){
		var STUDENTBM_PKIDss = assignedClass.getIdSelections()+"";
		if(STUDENTBM_PKIDss == ''){
			layer.msg("请先勾选您要进行分学号的学生！");
			return false;
		}
		BootstrapDialog.show({
    		title:'提示信息',
			message: '确定要分学号吗？',
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
	            label: '确定',
	            cssClass: 'btn-danger',
	            action: function(dialog2) {
	            	timer_is_on = 1;
	            	dialog2.setTitle("分学号进度");
	            	dialog2.setMessage('<div class="progress progress-striped active" id="proges">'+
					        '<div id="prog" class="progress-bar" role="progressbar" aria-valuenow="" aria-valuemin="0" aria-valuemax="100" style="width:0%;">'+
				            '<span id="proglabel">正在启动，请稍后......</span>'+
				        '</div>'+
				  '</div>');
	            	$(".bootstrap-dialog-footer-buttons").children("button")[0].remove();

	            	setTimeout(function(){
	            		//选择的入学年份
		        		var grade=$('#grade').val();
		        		//院校专业pkids
		        		var orgtree=$('#orgtree').val();
		        		//班级
		        		var BANJI = $("#BANJI").val();
		        		//是否分班
		        		var IS_FENXUEHAO = $('#IS_FENXUEHAO').val();
		        		//关键词
		        		var keywords = $("#keywords").val();
		        		//性别
		        		var BANJI_TYPE_PKID=$("#BANJI_TYPE_PKID").val();
		        		//报到状态
		        		var IS_RECEIVED=$("#IS_RECEIVED").val();
		        		
		        		var grade_str='';
		      			$('#grade option:selected').each(function() {
		                    grade_str+=$(this).attr('idd')+',';
		                });
		      			grade=grade_str;
		      			
		      			var banxing=$("#BANJI_TYPE_PKID").val();
		      			var banxing_str='';
		      			if(banxing!=null){
		      				for(var i=0;i<banxing.length;i++){
		      					banxing_str+=banxing[i]+',';
		      				}
		      			}
		      			BANJI_TYPE_PKID = banxing_str;
		      			
		      			var banji_str='';
		      			if(BANJI!=null){
		      				for(var i=0;i<BANJI.length;i++){
		      					banji_str+=BANJI[i]+',';
		      				}
		      			}
		      			BANJI = banji_str;
		      			
		        		var STUDENTBM_PKIDs = assignedClass.getIdSelections()+"";
		            	$.ajax({
		            		type:'post',
		            		dataType:'json',
		            		data:{grade:grade,orgtree:orgtree,BANJI:BANJI,IS_FENXUEHAO:IS_FENXUEHAO,keywords:keywords,BANJI_TYPE_PKID:BANJI_TYPE_PKID,IS_RECEIVED:IS_RECEIVED,STUDENTBM_PKIDs:STUDENTBM_PKIDs},
		            		url: _basepath+'assignedClass/diviXuehao.json',
		            		success:function(data){
		            			if(data.result=='success'){
		            				//选择的入学年份
    	            				var grade=$('#grade').val();
    	            				//院校专业pkids
    	            				var orgtree=$('#orgtree').val();
    	            				//班级
    	            				var BANJI = $("#BANJI").val();
    	            				//是否分班
    	            				var IS_FENXUEHAO = $('#IS_FENXUEHAO').val();
    	            				var IS_JIAOFEI = $('#IS_JIAOFEI').val();
    	            				//关键词
    	            				var keywords = $("#keywords").val();
    	            				//性别
    	            				var BANJI_TYPE_PKID=$("#BANJI_TYPE_PKID").val();
    	            				//报到状态
    	            				var IS_RECEIVED=$("#IS_RECEIVED").val();
    	            				//批次
    	            				var CENGCI = $("#CENGCI").val();
    	            				//迎新
    	            				var YX = $("#yx").val();
    	            				
    	            				var grade_str='';
    				      			$('#grade option:selected').each(function() {
    				                    grade_str+=$(this).attr('idd')+',';
    				                });
    				      			grade=grade_str;
    				      			
    				      			var banxing=$("#BANJI_TYPE_PKID").val();
    				      			var banxing_str='';
    				      			if(banxing!=null){
    				      				for(var i=0;i<banxing.length;i++){
    				      					banxing_str+=banxing[i]+',';
    				      				}
    				      			}
    				      			BANJI_TYPE_PKID = banxing_str;
    				      			
    				      			var banji_str='';
    				      			if(BANJI!=null){
    				      				for(var i=0;i<BANJI.length;i++){
    				      					banji_str+=BANJI[i]+',';
    				      				}
    				      			}
    				      			BANJI = banji_str;
    				      			
    	            				$("#assignedClassTable").bootstrapTable('refresh', {url: encodeURI(_basepath+"assignedClass/diviXuehaoTable.json?keywords="+keywords+"&IS_FENXUEHAO="+IS_FENXUEHAO+"&BANJI="+BANJI+"&YX="+YX+
		"&orgtree="+orgtree+"&grade="+grade+"&BANJI_TYPE_PKID="+BANJI_TYPE_PKID+"&IS_RECEIVED="+IS_RECEIVED+"&CENGCI="+CENGCI+"&IS_JIAOFEI="+IS_JIAOFEI)});
		            			}else{
		            				layer.msg(data.errorinfo);
		            			}
		            		},
		            		error:function(a,error,c){
//		            			alert(error);
		            			layer.msg("分学号失败，请重试！");
		            		}
		            	});
	            	}, 500);
	        		
	            	setTimeout(function(){
	            		//调用进度条
	            		progressXuehao(dialog2);
	            		
	            	}, 600);
	            	
	            }
			 },
			 {
    	            label: '关闭',
    	            cssClass: 'btn-danger',
    	            action: function(dialog2) {
    	            	dialog2.close();
    	            }
    		 }
			 ]
		});
		
	});
	
	// 获取bootStrapTable表格数据,及参数设置
	assignedClass.getTab = function() {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		var url = _basepath + "assignedClass/diviXuehaoTable.json";
		$('#assignedClassTable').bootstrapTable(
						{
							url : url,// 数据源
							dataField : "rows",// 服务端返回数据键值
							// 就是说记录放的键值是rows，分页时使用总记录数的键值为total
							totalField : 'total',
							sortOrder : 'desc',
							striped : true, // 表格显示条纹
							pagination : true, // 启动分页
							pageNumber : 1, // 当前第几页
							pageSize : 20, // 每页显示的记录数
							pageList : [ 1, 5, 10, 20, 30, 50, 100 ], // 记录数可选列表
							search : false, // 是否启用查询
							formatSearch : function() {
								return '请输入简拼.编码.企业名称查询';
							},// 搜索框提示文字
							searchAlign : 'left',
							buttonsAlign : 'left',
							toolbarAlign : 'left',
							searchOnEnterKey : false,// true为按回车触发搜索事件
							showColumns : false, // 显示下拉框勾选要显示的列
							showRefresh : false, // 显示刷新按钮 --只是前台刷新，个人觉得没什么用
							minimumCountColumns : 2, // 最少允许的列数
							sidePagination : "server", // 表示服务端请求
							totalRows : 0, // server side need to set
							singleSelect : false,
							clickToSelect : false,
							onDblClickRow : function(row) {
							},
							queryParams : function getParams(params) {
								//选择的入学年份
								var grade=$('#grade').val();
								//院校专业pkids
								var orgtree=$('#orgtree').val();
								//班级
								var BANJI = $("#BANJI").val();
								//是否分班
								var IS_FENXUEHAO = $('#IS_FENXUEHAO').val();
								var IS_JIAOFEI = $('#IS_JIAOFEI').val();
								//关键词
								var keywords = $("#keywords").val();
								//批次
								var CENGCI = $("#CENGCI").val();
								//性别
								var BANJI_TYPE_PKID=$("#BANJI_TYPE_PKID").val();
								
								var grade_str='';
				      			$('#grade option:selected').each(function() {
				                    grade_str+=$(this).attr('idd')+',';
				                });
				      			grade=grade_str;
				      			
				      			var banxing=$("#BANJI_TYPE_PKID").val();
				      			var banxing_str='';
				      			if(banxing!=null){
				      				for(var i=0;i<banxing.length;i++){
				      					banxing_str+=banxing[i]+',';
				      				}
				      			}
				      			BANJI_TYPE_PKID = banxing_str;
				      			
				      			var banji_str='';
				      			if(BANJI!=null){
				      				for(var i=0;i<BANJI.length;i++){
				      					banji_str+=BANJI[i]+',';
				      				}
				      			}
				      			BANJI = banji_str;
				      			
								//报到状态
								var IS_RECEIVED=$("#IS_RECEIVED").val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									keywords : keywords,
									IS_FENXUEHAO : IS_FENXUEHAO,
									IS_JIAOFEI : IS_JIAOFEI,
									orgtree : orgtree,
									grade : grade,
									BANJI : BANJI,
									CENGCI : CENGCI,
									BANJI_TYPE_PKID : BANJI_TYPE_PKID,
									IS_RECEIVED : IS_RECEIVED,
									sortName : this.sortName,
									sortOrder : this.sortOrder,
									pageindex : this.pageNumber,
									YX:$("#yx").val()
								// 当前页码
								};
								return temp;
							},
							buttonsAlign : "right",// 按钮对齐方式
							selectItemName : 'id',
							toolbar : "#toolbar",
							toolbarAlign : 'left',
							columns : [
									{
										title : "全选",//标题
										align : "center",//水平
										checkbox : true,
										valign : "middle"//垂直
									
									},
									{
										field : 'PKID',
										title : '',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'SHENFENZHENGHAO',
										title : '身份证号',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'XINGMING',
										title : '姓名',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'XINGBIE',
										title : '性别',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter : function(value, row,
												index) {
											return value == 'M' ? '男':'女';
										}
									},
									
									{
										field : 'XUEHAO',
										title : '学号',
										align : "center",
										halign : 'center',
										sortable : false
									},
						            {
						        	   field : 'NIANJI',
						        	   title : '入学年份',
						        	   align : "center",
						        	   halign : 'center',
						        	   sortable : false
						            },
						            {
						        	   field : 'BANXING',
						        	   title : '班型',
						        	   align : "center",
						        	   halign : 'center',
						        	   sortable : false
						            },
									{
										field : 'CENGCI_NAME',
										title : '学生类型',
										align : "center",
										halign : 'center',
										sortable : false
										
									},
						            {
						        	   field : 'CLASS_NAME',
						        	   title : '班级',
						        	   align : "center",
						        	   halign : 'center',
						        	   sortable : false
						            },
						            {
						        	   field : 'TOTALMONEY',
						        	   title : '缴费总额',
						        	   align : "center",
						        	   halign : 'center',
						        	   sortable : false
						            }
									],
						});
		$('#assignedClassTable').bootstrapTable('hideColumn', 'PKID');//隱藏列
	};
	//加载表格数据
	assignedClass.getTab();
	
	$('#grade').multiselect({
   	 enableFiltering: true,
   	 includeSelectAllOption: true,
   	 selectAllText: ' 全选',
   	 nonSelectedText: '请选择入学年份',
        allSelectedText: '全部选择',
        selectAllJustVisible: true,
   	 filterPlaceholder:'请输入入学年份名称',
   	 buttonWidth: '183px'//设置字体过长显示...
   });
	 $('#BANJI').multiselect({
   	 enableFiltering: true,
   	 includeSelectAllOption: true,
   	 selectAllText: ' 全选',
   	 nonSelectedText: '请选择班级',
        allSelectedText: '全部选择',
        selectAllJustVisible: true,
   	 filterPlaceholder:'请输入班级名称',
   	 buttonWidth: '183px'//设置字体过长显示...
   });
	 $('#BANJI_TYPE_PKID').multiselect({
   	 enableFiltering: true,
   	 includeSelectAllOption: true,
   	 selectAllText: ' 全选',
   	 nonSelectedText: '请选择班型',
        allSelectedText: '全部选择',
        selectAllJustVisible: true,
   	 filterPlaceholder:'请输入班型',
   	 buttonWidth: '183px'//设置字体过长显示...
   });
})(jQuery, window);
