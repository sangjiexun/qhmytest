/**
 * 分班对象
 */
//@ sourceURL=assignedClass.js
(function($, window) {
	var assignedClass = {};
	
	
	 $('#grades').multiselect({
    	 enableFiltering: true,
    	 includeSelectAllOption: true,
    	 selectAllText: ' 全选',
    	 nonSelectedText: '请选择入学年份',
         allSelectedText: '全部选择',
         selectAllJustVisible: true,
    	 filterPlaceholder:'请输入入学年份名称',
    	 buttonWidth: '183px'//设置字体过长显示...
    });
	 $('#banxing').multiselect({
    	 enableFiltering: true,
    	 includeSelectAllOption: true,
    	 selectAllText: ' 全选',
    	 nonSelectedText: '请选择班型',
         allSelectedText: '全部选择',
         selectAllJustVisible: true,
    	 filterPlaceholder:'请输入批次班型',
    	 buttonWidth: '183px'//设置字体过长显示...
    });
	 $('#banji').multiselect({
    	 enableFiltering: true,
    	 includeSelectAllOption: true,
    	 selectAllText: ' 全选',
    	 nonSelectedText: '请选择班级',
         allSelectedText: '全部选择',
         selectAllJustVisible: true,
    	 filterPlaceholder:'请输入班级名称',
    	 buttonWidth: '183px'//设置字体过长显示...
    });
	 $('#partschool').multiselect({
		 enableFiltering: true,
		 includeSelectAllOption: true,
		 selectAllText: ' 全选',
		 nonSelectedText: '请选择文化课学校',
		 allSelectedText: '全部选择',
		 selectAllJustVisible: true,
		 filterPlaceholder:'请输入文化课学校',
		 buttonWidth: '183px'//设置字体过长显示...
	 });
	
	$("#grade").bind("change", function() {
		changeGrade();
	});
	
	//获取选中的行
	assignedClass.getIdSelections=function () {
			var $table = $('#assignedClassTable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.TMPKID;
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
	
		 $("#assignedClassTable").bootstrapTable('refresh');

	});
	$(".ppbj").change(function(){
		
		//入学年份
		var grade=$("#grades").val();
		var grade_str='';
		if(grade!=null){
			for(var i=0;i<grade.length;i++){
				grade_str+=grade[i]+',';
			}
		}
	//班型
		var banxing=$("#banxing").val();
		var banxing_str='';
		if(banxing!=null){
			for(var i=0;i<banxing.length;i++){
				banxing_str+=banxing[i]+',';
			}
		}
		
		
		
		$.post(_basepath+"assignedClass/getbj.json",
				{grade_str:grade_str,banxing_str:banxing_str},function(data){
					var list=data.list;
				
					$("#banji").html("");
					var opt="";
					for(var i=0;i<list.length;i++){
						console.log("<option value='"+list[i].PKID+"'>"+list[i].NAME+"</option>");
						opt+="<option value='"+list[i].PKID+"'>"+list[i].NAME+"</option>";
					}
					$("#banji").append(opt);
					$('#banji').multiselect('destroy');
					$('#banji').multiselect({
				    	 enableFiltering: true,
				    	 includeSelectAllOption: true,
				    	 selectAllText: ' 全选',
				    	 nonSelectedText: '请选择班级',
				         allSelectedText: '全部选择',
				         selectAllJustVisible: true,
				    	 filterPlaceholder:'请输入班级名称',
				    	 buttonWidth: '183px'//设置字体过长显示...
				    });
			
		});
		
	});
	
	//导出功能
	$("#checkOut").click(function(){
		//入学年份
			var grade=$("#grades").val();
			var grade_str='';
			if(grade!=null){
				for(var i=0;i<grade.length;i++){
					grade_str+=grade[i]+',';
  			}
			}
		//班型
			var banxing=$("#banxing").val();
			var banxing_str='';
			if(banxing!=null){
				for(var i=0;i<banxing.length;i++){
					banxing_str+=banxing[i]+',';
  			}
			}
		
		//班级下拉框
		var banji=$("#banji").val();
			var banji_str='';
			if(banji!=null){
				for(var i=0;i<banji.length;i++){
					banji_str+=banji[i]+',';
  			}
			}
		//合作学校下拉框
			var partschool=$("#partschool").val();
			var partschool_str='';
			if(partschool!=null){
				for(var i=0;i<partschool.length;i++){
					partschool_str+=partschool[i]+',';
				}
			}
		
		
		
		//alert(YX);
		window.location.href=encodeURI(_basepath+'assignedClass/exportExcel.json?grade_str='+grade_str+
				'&&banxing_str='+banxing_str+'&&banji_str='+banji_str+'&&partschool_str='+partschool_str+
				"&&XKCJ="+$("#XKCJ").val()+"&&seText="+$.trim($("#keywords").val())+
				"&XINGBIE="+$("#xingbie").val()+"&IS_FENBAN="+$("#IS_FENBAN").val()+"&LKFSQI="+$('#LKFSQI').val()+
				"&LKFSZHI="+$('#LKFSZHI').val());
	});
	
	assignedClass.uniq = function (array){
	    var temp = []; //一个新的临时数组
	    for(var i = 0; i < array.length; i++){
	        if(temp.indexOf(array[i]) == -1){
	            temp.push(array[i]);
	        }
	    }
	    return temp;
	}
	//分班按钮点击事件
	$("#btn_fenban").click(function(){
		
		var obj=$("#assignedClassTable").bootstrapTable('getAllSelections');
		var stupkid="";
		
		
		if(obj.length==0){
			layer.msg("请选择至少一名学生");
			return false;
		}
		
		
	//------------------------------------入学年份班型 相同性验证start	
		var arrayrxnf=new Array();
		var arraybx=new Array();
		for(var i=0;i<obj.length;i++){
			arrayrxnf[i]=obj[i]['RXNIANFEN_PKID'];
		}
		for(var i=0;i<obj.length;i++){
			arraybx[i]=obj[i]['BANJI_TYPE'];
		}
		
		for(var i=0;i<obj.length;i++){
			var val=obj[i]['IS_BANJI'];
			if(val!="N"){
				layer.msg("所选学生包括已经分班的学生，请重新选择");
				return false;
			}
		}
		
		arrayrxnf=assignedClass.uniq(arrayrxnf);
		arraybx=assignedClass.uniq(arraybx);

		if(arrayrxnf.length!=1||arraybx.length!=1){
			layer.msg("学生的入学年份和班型要一致");
			return false;
		}
   //------------------------------------入学年份班型 相同性验证end	

		var dialog = BootstrapDialog.show({
			title:'分班',
			message: $('<div></div>').load("assignedClass/godiviClass.json?",{
				RXNFNAME:arrayrxnf[0],
				BXNAME:arraybx[0]
			}),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
	            label: '确定',
	            cssClass: 'btn-danger',
	            action: function(dialog) {
	            	var banji = $("#banji_fen").val();
	          	if(banji=="" || banji==null){
	            		layer.msg("班级不能为空");
	            		return false;
	            	}
	          	for(var i=0;i<obj.length;i++){
	    			stupkid+=obj[i]['TMPKID']+",";
	    		}
	          	BootstrapDialog.show({
	            		title:'提示信息',
	        			message: '待分班人数'+obj.length+"人，确定分班吗？",
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
	        	            		url: _basepath+'assignedClass/diviClass.json?stupkid='+stupkid,
	        	            		success:function(data){
	        	            			if(data.result=='success'){
	        	            				layer.msg("分班成功");
	        	            			    $("#assignedClassTable").bootstrapTable('refresh');
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
	
	//调班按钮点击事件
	$("#btn_tiaoban").click(function(){
		
		var obj=$("#assignedClassTable").bootstrapTable('getAllSelections');
		var stupkid="";
		var banjipkid="";
		
		for(var i=0;i<obj.length;i++){
			if(obj[i]['IS_BANJI']!="N"){
				if(obj[0]['IS_BANJI']!=obj[i]['IS_BANJI']){
					layer.msg("所选班级必须保持一致");
					return false;
				}
			}else{
				layer.msg("请选择已分班的学生");
				return false;
			}
			
		}
		
		banjipkid=obj[0]['IS_BANJI'];//获取班级PKID
		
		
	//------------------------------------入学年份班型 相同性验证start	
		var arrayrxnf=new Array();
		var arraybx=new Array();
		for(var i=0;i<obj.length;i++){
			arrayrxnf[i]=obj[i]['RXNIANFEN_PKID'];
		}
		for(var i=0;i<obj.length;i++){
			arraybx[i]=obj[i]['BANJI_TYPE'];
		}
		

		
		arrayrxnf=assignedClass.uniq(arrayrxnf);
		arraybx=assignedClass.uniq(arraybx);

		if(arrayrxnf.length!=1||arraybx.length!=1){
			layer.msg("学生的入学年份和班型要一致");
			return false;
		}
   //------------------------------------入学年份班型 相同性验证end	

		var dialog = BootstrapDialog.show({
			title:'分班',
			message: $('<div></div>').load("assignedClass/godiviClass.json?",{
				RXNFNAME:arrayrxnf[0],
				BXNAME:arraybx[0],
				BANJIPKID:banjipkid,
			}),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
	            label: '确定',
	            cssClass: 'btn-danger',
	            action: function(dialog) {
	            	var banji = $("#banji_fen").val();
	          	if(banji=="" || banji==null){
	            		layer.msg("班级不能为空");
	            		return false;
	            	}
	          	for(var i=0;i<obj.length;i++){
	    			stupkid+=obj[i]['TMPKID']+",";
	    		}
	          	BootstrapDialog.show({
	            		title:'提示信息',
	        			message: '待调班人数'+obj.length+"人，确定调班吗？",
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
	        	            		url: _basepath+'assignedClass/diviClass.json?stupkid='+stupkid,
	        	            		success:function(data){
	        	            			if(data.result=='success'){
	        	            				layer.msg("调班成功");
	        	            			    $("#assignedClassTable").bootstrapTable('refresh');
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



	
	// 获取bootStrapTable表格数据,及参数设置
	assignedClass.getTab = function() {
		//获得按钮权限
		var url = _basepath + "assignedClass/stuFenBan.json";
		$('#assignedClassTable').bootstrapTable(
						{
							method: 'post',  
							url : url,//数据源
							dataType: "json",
							contentType:"application/x-www-form-urlencoded; charset=UTF-8",
							dataField : "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
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
							clickToSelect : true,
							onDblClickRow : function(row) {
							},
							onLoadSuccess:function(data){
								res_data=data;
								$('.bootstrap-table tr td').each(function () {
					                $(this).attr("title", $(this).text());
					                $(this).css("cursor", 'pointer');
					            });
							},
							queryParams : function getParams(params) {
								
								
								//入学年份
				      			var grade=$("#grades").val();
				      			var grade_str='';
				      			if(grade!=null){
				      				for(var i=0;i<grade.length;i++){
				      					grade_str+=grade[i]+',';
				          			}
				      			}
				      		//班型
				      			var banxing=$("#banxing").val();
				      			var banxing_str='';
				      			if(banxing!=null){
				      				for(var i=0;i<banxing.length;i++){
				      					banxing_str+=banxing[i]+',';
				          			}
				      			}
								
				      		//班级下拉框
								var banji=$("#banji").val();
				      			var banji_str='';
				      			if(banji!=null){
				      				for(var i=0;i<banji.length;i++){
				      					banji_str+=banji[i]+',';
				          			}
				      			}
				      		//合作学校下拉框
				      			var partschool=$("#partschool").val();
				      			var partschool_str='';
				      			if(partschool!=null){
				      				for(var i=0;i<partschool.length;i++){
				      					partschool_str+=partschool[i]+',';
				      				}
				      			}
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									grade_str:grade_str,
									banxing_str:banxing_str,
									banji_str:banji_str,
									partschool_str:partschool_str,
									XKCJ:$("#XKCJ").val(),
									seText:$.trim($("#keywords").val()),
									XINGBIE:$("#xingbie").val(),
									IS_FENBAN:$("#IS_FENBAN").val(),
									LKFSQI:$('#LKFSQI').val(),//联考分数起
									LKFSZHI:$('#LKFSZHI').val(),//联考分数止
									sortName : this.sortName,
									sortOrder : this.sortOrder,
									pageindex : this.pageNumber,
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
							        	   field : '',  
							        	   checkbox : true,
							        	   formatter:function(value,row,index){
							        		   if(row.XUEHAO==null){
							        			   return {disabled : true}
							        		   }
							        	   	}
							        	   },
							        	   {
												field : 'TMPKID',
												align : "center",
												halign : 'center',
												visible:false,
												sortable : false
											},
									{
										field : 'SHENFENZHENGHAO',
										title : '身份证号',
										align : "center",
										halign : 'center',
										width:'200px',

										sortable : false
									},
									{
										field : 'XINGMING',
										title : '姓名',
										align : "center",
										halign : 'center',
										width:'120px',
										sortable : false
									},
									{
										field : 'XINGBIE',
										title : '性别',
										align : "center",
										halign : 'center',
										width:'90px',
										sortable : false
									},
									
									{
										field : 'XUEHAO',
										title : '学号',
										align : "center",
										halign : 'center',
										sortable : false
									},

									{
										field : 'RXNIANFEN_PKID',
										title : '入学年份',
										align : "center",
										halign : 'center',
										sortable : false

									},
									{
										field : 'BANJI_TYPE',
										title : '班型',
										align : "center",
										halign : 'center',
										width:'90px',
										sortable : false
										
									},
									{
										field : 'CENGCI',
										title : '学生类型',
										align : "center",
										halign : 'center',
										width:'90px',
										sortable : false
										
									},
									
									{
										field : 'XK_MARK',
										width:'120',
										title : '校考成绩',
										align : "center",
										halign : 'center',
										width:'190px',
										sortable : false
									
										
									},

									{
										field : 'LKFS',
										title : '联考分数',
										align : "center",
										halign : 'center',
										width:'90px',
										sortable : false

									},
									{
										field : 'OLD_BANJINAME',
										title : '原班级',
										align : "center",
										halign : 'center',
										sortable : false

									},
									{
										field : 'BANJINAME',
										title : '班级',
										align : "center",
										halign : 'center',
										sortable : false

									}
									],
						});
		
	};
	//加载表格数据
	assignedClass.getTab();
})(jQuery, window);
