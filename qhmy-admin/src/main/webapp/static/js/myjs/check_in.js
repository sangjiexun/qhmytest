/**
 * 对应了check_in.jsp页面
 * @param $
 * @param window
 */
(function($, window) {
	
	var check = {};
	var fun = {};
	fun.html = function(id,val){
		if(""==val || null==val){
			$("#"+id).html();
		}else{
			$("#"+id).html(val);
		}
		
	};
	
	/**
	 * 查询按钮点击事件
	 */
	$("#checkQuery").click(function(){
		$("#assignedClassTable").bootstrapTable('refresh');
		$('#mymodaStudentList').modal();
	});
	
	$("#checkQuery_rz").click(function(){
		if(""==$("#StuInFoId").val()){
			return false;
		}
		if("disabled"==$("#checkQuery_rz").attr("disabled"))
			return false;
			
		$.post(_basepath + "checkIn/updateRz.json",{PKID:$("#StuInFoId").val()},function(data){
			if("SUCCESS"==data.res){
				  layer.msg("办理入住成功!");
				  $("#checkQuery_rz").attr("disabled","disabled");
				  $("#con").html('<p class="text-success">已办理</p>');
			}
			if("ERROR"==data.res){
				  layer.msg("网络异常!");
				  
			}	
		});
		 e.stopPropagation();
	});

	
	

   check.loadHtml = function(DORMNAME,IS_RZ){
	   var head= '';
	   var body= '';
	   var foot='';
	   $("#checkQuery_rz").show();
	   $("#ruzhudengjiFlowContent").html("");
	   head = '<div class="pa-row">'+
		'<table class="table table-striped table-hover table-bordered jf_table" style="text-align: center;">'+
		'<tr>'+
			'<th width="500">宿舍</th>'+
			'<th>是否办理入住</th>'+
		'</tr>';
	   
	   if(DORMNAME=="N"||IS_RZ=="Y"){
		   $("#checkQuery_rz").attr("disabled",true);

	   }else{
		   $("#checkQuery_rz").removeAttr("disabled");

	   }
	   
	   
	  
	   
	   
	   if("Y"==IS_RZ){
		   IS_RZ='<p class="text-success">已办理</p>';

	   }else{
		   IS_RZ='<p style="color:#a94442;">未办理</p>';

	   }
	  
	   if("N"==DORMNAME){
			  body='<tr>'+
				'<td colspan="2">暂无分宿记录</td>'+
				'</tr>';
		   }else{
			   body='<tr>'+
				'<td>'+DORMNAME+'</td>'+
				'<td id="con">'+IS_RZ+'</td>'+
				'</tr>';
		   }
	
	   
	   
		
		foot = '</table></div>';
		$("#ruzhudengjiFlowContent").html(head+body+foot);
   	}
	
	
	
	
	check.loadTabData = function(data){
		$("#StuInFoId").val(data.PKID);
		
		var WHKSCHOOL = data.WHKSCHOOL;
		var XUEHAO = data.XUEHAO;
		var SHENFENZHENGHAO = data.SHENFENZHENGHAO;
		var RXNF = data.RXNIANFEN_PKID;
		var BANJI = data.BANJI;
		var XINGMING = data.XINGMING;
		var XINGBIE = data.XINGBIE;
		var BANJI_TYPE = data.BANJI_TYPE;

		
		if(data.TOUXIANG!=""&&data.TOUXIANG!=null){
    		 $('#touxiang').attr('src',_basepath+data.TOUXIANG+"?t="+ new Date().getTime());  
		}else{
			$('#touxiang').attr('src',_basepath+"static/gxjf/images/defaulttouxiang.jpg"); 
		}
		
		fun.html("WHKSCHOOL",WHKSCHOOL==""?"-":WHKSCHOOL);
		fun.html("XUEHAO",XUEHAO==""?"-":XUEHAO);
		fun.html("SHENFENZHENGHAO",SHENFENZHENGHAO==""?"-":SHENFENZHENGHAO);
		fun.html("RXNF",RXNF==""?"-":RXNF);
		fun.html("BANJI",BANJI==""?"-":BANJI);
		fun.html("XINGMING",XINGMING==""?"-":XINGMING);
		fun.html("XINGBIE",XINGBIE==""?"-":XINGBIE);
		fun.html("BANJI_TYPE",BANJI_TYPE==""?"-":BANJI_TYPE);
	}
	
	// 获取bootStrapTable表格数据,及参数设置
	check.getTab = function() {
		//获得按钮权限
		var url = _basepath + "checkIn/stuAssignedClassTable.json";
		$('#assignedClassTable').bootstrapTable(
						{
							url : url,// 数据源
							method : 'post',
							contentType:"application/x-www-form-urlencoded; charset=UTF-8",
							dataField : "rows",// 服务端返回数据键值
							// 就是说记录放的键值是rows，分页时使用总记录数的键值为total
							totalField : 'total',
							sortOrder : 'desc',
							striped : true, // 表格显示条纹
							pagination : true, // 启动分页
							pageNumber : 1, // 当前第几页
							pageSize : 6, // 每页显示的记录数
							pageList : [1 , 4, 6,  8 ], // 记录数可选列表
							search : false, // 是否启用查询
							formatSearch : function() {
								return '请输入简拼.编码.企业名称查询';
							},// 搜索框提示文字
							searchAlign : 'left',
							buttonsAlign : 'left',
							toolbarAlign : 'left',
							searchOnEnterKey : true,// true为按回车触发搜索事件
							showColumns : false, // 显示下拉框勾选要显示的列
							showRefresh : false, // 显示刷新按钮 --只是前台刷新，个人觉得没什么用
							minimumCountColumns : 2, // 最少允许的列数
							sidePagination : "server", // 表示服务端请求
							totalRows : 0, // server side need to set
							singleSelect : false,
							clickToSelect : true,

							onDblClickRow:function(data){
								check.loadTabData(data);
								check.loadHtml(data.DORMNAME,data.IS_RZ);
								$("#close").click();
							},
							
							onLoadSuccess:function(data){
								res_data=data;
								$(".pagination-info").html("");
								$('.bootstrap-table tr td').each(function () {
					                $(this).attr("title", $(this).text());
					                $(this).css("cursor", 'pointer');
					            }); 
					        
					            
							},
							queryParams : function getParams(params) {
								  var inputValue=$("#conditions").val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									seText:inputValue,
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
												field : 'PKID',
												align : "center",
												halign : 'center',
												visible:false,
												sortable : false
								    },
									{
										field : 'XINGMING',
										title : '姓名',
										width:'200px',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'XUEHAO',
										title : '学号',
										width:'200px',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'RXNIANFEN_PKID',
										title : '入学年份',
										width:'200px',
										align : "center",
										halign : 'center',
										width:'90px',
										sortable : false
									},
									
									{
										field : 'BANJI',
										title : '班级',
										width:'250px',
										align : "center",
										halign : 'center',
										sortable : false
									},
									
									{
										field : 'BANJI_TYPE',
										title : '班型',
										width:'200px',
										align : "center",
										halign : 'center',
										sortable : false

									}
,
									
									{
										field : 'WHKSCHOOL',
										title : '班型',
										align : "center",
										halign : 'center',
										sortable : false,
										visible:false,


									}
									],
						});
	};
	check.getTab();
	
	})(jQuery, window);
