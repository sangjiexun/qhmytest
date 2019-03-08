//@ sourceURL=stuinfo.js 
/**
 * 学生信息页面对象
 */
(function($, window) {
	

	
	
	var stu = {};
	
	laydate.render({
		elem:'#GYRXSJBEGIN',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'month',
		format: 'yyyy-MM',
		festival: true, //显示节日
		start: 0
	});
	//日期插件参数设置
	laydate.render({
		elem:'#GYRXSJEND',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'month',
		format: 'yyyy-MM',
		festival: true, //显示节日
		start: 0
	});
	laydate.render({
		elem:'#CJSJTMBEGIN',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	//日期插件参数设置
	laydate.render({
		elem:'#CJSJTMEND',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	
	//批量删除方法
	/*stu.batchDel=function(e){
		$.post(_basepath+"stuinfo/batchDel.json?pkids="+e,function(data){
			if(data.result=="success"){
				layer.msg("删除成功!");
				 $("#stuinfotable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}
			if(data.result=="paid"){
				layer.msg("您选择的信息包含已缴费的学生，不能删除!");
			}
			if(data.result=="useddorm"){
				layer.msg("您选择的学生已经占用床位或预定床位，不能删除!");
			}
		});
	};*/

	//批量通过方法
/*	stu.batchTongGuo=function(pkids){
		$.post(_basepath+"stuinfo/batchTongGuo.json?pkids="+pkids,function(data){
			if(data.result=="success"){
				layer.msg("操作成功!");
				$("#stuinfotable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}
		});
	};*/
	
	
	
	//查询
	$('#btn_search').click(function(){
		//所属组织
		$("#stuinfotable").bootstrapTable('destroy'); 
		 stu.getTab();
		
	});
	

	
	$("#btn_import_stu").click(function(){
		var dialog = BootstrapDialog.show({
			title:'导入学生信息',
			message: $('<div></div>').load("importstu/importstuinfo.json"),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
	            label: '关闭',
	            cssClass: 'btn-danger',
	            action: function(dialog) {
	            	dialog.close();
	            	$('#stuinfotable').bootstrapTable("refresh");
	            }
	        }]
		});
	});



	//点击按钮导出
	$("#checkOut").click(function(){
		//页面展示字段拼接
		var resultColumnList = $('#stuinfotable').bootstrapTable('getVisibleColumns');
		var resultColumns = "";			
		$.each(resultColumnList, function(i, column) {
			if(column.field=='0'||column.field=='opt'){
				return;
			}else{
				resultColumns+=","+column.field;
			}									
	 	});
		resultColumns = resultColumns.substr(1);
		//入学年份
		var grade=$("#grade").val();
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
		//在学状态下拉
		var zxzt=$('#zxzt').val();
		//层次
		var cengci=$("#cengci").val();
		var cengci_str='';
		if(cengci!=null){
			for(var i=0;i<cengci.length;i++){
				cengci_str+=cengci[i]+',';
		}
		}
		//预交年份
		var yjgrade=$("#yjgrade").val();
		var yjgrade_str='';
		if(yjgrade!=null){
			for(var i=0;i<yjgrade.length;i++){
				yjgrade_str+=yjgrade[i]+',';
			}
		}
		//校考成绩
		var meiyuan=$("#meiyuan").val();
		var meiyuan_str='';
		if(meiyuan!=null){
			for(var i=0;i<meiyuan.length;i++){
				meiyuan_str+=meiyuan[i]+',';
			}
		}
		var GYRXSJBEGIN =  $('#GYRXSJBEGIN').val();//高一入学时间起
		var GYRXSJEND =  $('#GYRXSJEND').val();////高一入学时间止
		var LKFSQI =  $('#LKFSQI').val();//联考分数起
		var LKFSZHI =  $('#LKFSZHI').val();//联考分数止	
		var searchText =  $('#search').val();//身份证学号姓名备注
		var CJSJTMBEGIN =  $('#CJSJTMBEGIN').val();//报名时间起
		var CJSJTMEND =  $('#CJSJTMEND').val();//报名时间止
		var IS_TONGGUO =  $('#IS_TONGGUO').val();//审核状态
		var IS_RUZHU =  $('#IS_RUZHU').val();//入住状态
		var IS_ISPAY =  $('#IS_ISPAY').val();//是否缴费
		var XUEHAOQ = $("#XUEHAOQ").val();//学号起
		var XUEHAOZ = $("#XUEHAOZ").val();//学号止
		var sortName = $("#sortName").val();//排序名称
		var sortOrder = $("#sortOrder").val();//排序方式
		
		window.location.href=encodeURI(_basepath+'stuinfo/exportExcel.json?seText='+searchText+
				'&&LKFSZHI='+LKFSZHI+"&&LKFSQI="+LKFSQI+"&&GYRXSJEND="+GYRXSJEND+
				"&&GYRXSJBEGIN="+GYRXSJBEGIN+"&&meiyuan_str="+meiyuan_str+"&&yjgrade_str="+yjgrade_str+
				"&&cengci_str="+cengci_str+"&grade_str="+grade_str+'&resultColumns='+resultColumns+
				"&&zxzt="+zxzt+"&&partschool_str="+partschool_str+"&&banji_str="+banji_str+"&banxing_str="+banxing_str+
				"&CJSJTMBEGIN="+CJSJTMBEGIN+"&CJSJTMEND="+CJSJTMEND+"&IS_TONGGUO="+IS_TONGGUO+"&IS_RUZHU="+IS_RUZHU+
				"&IS_ISPAY="+IS_ISPAY+"&XUEHAOQ="+XUEHAOQ+"&XUEHAOZ="+XUEHAOZ+"&sortName="+sortName+"&sortOrder="+sortOrder);
	});
	
	
	
	//通过按钮点击事件
	$("#btn_tongguo").click(function(){
		var pkids=stu.getIdSelections();
		var tongguos=stu.getTongGuoSelections()+"";//获取选中的所有的审核状态值
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			  BootstrapDialog.show({  //显示需要提交的表单。
				  title:'提示信息',
			        message: '请至少选择一条数据!',
			         buttons: [{
				    label: '关闭',
				    cssClass: 'btn-danger',
				    action: function(dialogRef){
				       dialogRef.close();
				    }
				  }
				  ]
			  }); 
			return false;
		}
		var isFlag = true;
		var tongguosArrays = tongguos.split(",");
		for(var i=0;i<tongguosArrays.length;i++){
			if(tongguosArrays[i]!='0'){
				isFlag = false;
			}
		}
		if(isFlag==true){
			layer.msg("审核不通过的学生不能再次审核通过!");
			return false;
		}
		BootstrapDialog.show({  //显示需要提交的表单。
		          	title:'提示信息',	
		          message: '你确定要审核通过吗？',
		         closable: false, 
		          buttons: [{
					    label: '确定',
					    cssClass: 'btn-danger',
					    action: function(dialogRef){
					    stu.batchTongGuo(pkids,1);
		              dialogRef.close();
		              }
				  },
				  {
				    label: '关闭',
				    cssClass: 'btn-default',
				    action: function(dialogRef){
				       dialogRef.close();
				    }
				  }]
          });
	});
	//不通过按钮点击事件
	$("#btn_butongguo").click(function(){
		var pkids=stu.getIdSelections();
		var tongguos=stu.getTongGuoSelections()+"";
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			BootstrapDialog.show({  //显示需要提交的表单。
				title:'提示信息',
				message: '请至少选择一条数据!',
				buttons: [{
					label: '关闭',
					cssClass: 'btn-danger',
					action: function(dialogRef){
						dialogRef.close();
					}
				}
				]
			}); 
			return false;
		}
		var isFlag = true;
		var tongguosArrays = tongguos.split(",");
		for(var i=0;i<tongguosArrays.length;i++){
			if(tongguosArrays[i]!='1'){
				isFlag = false;
			}
		}
		if(isFlag==true){
			layer.msg("审核通过的学生不能再次审核不通过!");
			return false;
		}
		$('#mymoda1').modal({
			keyboard: true
		});
		$("#IS_TONGGUO_REASON").val('');
	});
	//批量缴费点击保存
	$('#payButtonBatch').click(function(){
		var pkids=stu.getIdSelections();
		var IS_TONGGUO_REASON = $("#IS_TONGGUO_REASON").val();
		/*alert(IS_TONGGUO_REASON);
		var url=_basepath+"stuinfo/batchTongGuo.json";
		var data={pkids:pkids,status:'0',IS_TONGGUO_REASON:IS_TONGGUO_REASON			  };*/
		/*alert(IS_TONGGUO_REASON);*/
		$.post(encodeURI(_basepath+"stuinfo/batchTongGuo.json?pkids="+pkids+'&&status='+'0'+"&IS_TONGGUO_REASON="+IS_TONGGUO_REASON),function(data){
			if(data.result=="success"){
				$("#cancelButtonBatch").click();
				layer.msg("操作成功!");
				$("#stuinfotable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}
		});
	});
	
	//退学按钮点击事件
	$("#btn_tuixue").click(function(){
		var pkids=stu.getIdSelections();
		var rxnfpkids = stu.getRXNFPkidSelections();
		var bxpkids = stu.getBXPkidelections();
		var stuPkids = stu.getPKIdSelections();
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			BootstrapDialog.show({  //显示需要提交的表单。
				title:'提示信息',
				message: '请至少选择一条数据!',
				buttons: [{
					label: '关闭',
					cssClass: 'btn-danger',
					action: function(dialogRef){
						dialogRef.close();
					}
				}
				]
			}); 
			return false;
		}
		BootstrapDialog.show({  //显示需要提交的表单。
			title:'提示信息',	
			message: '你确定要批量退学吗？',
			closable: false, 
			buttons: [{
				label: '确定',
				cssClass: 'btn-danger',
				action: function(dialogRef){
					stu.batchByOrTuiXue(pkids,"TX",rxnfpkids,bxpkids,stuPkids);
					dialogRef.close();
				}
			},
			{
				label: '关闭',
				cssClass: 'btn-default',
				action: function(dialogRef){
					dialogRef.close();
				}
			}]
		});
	});
	//毕业按钮点击事件
	$("#btn_biye").click(function(){
		var pkids = stu.getIdSelections();
		var rxnfpkids = stu.getRXNFPkidSelections();
		var bxpkids = stu.getBXPkidelections();
		var stuPkids = stu.getPKIdSelections();
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			BootstrapDialog.show({  //显示需要提交的表单。
				title:'提示信息',
				message: '请至少选择一条数据!',
				buttons: [{
					label: '关闭',
					cssClass: 'btn-danger',
					action: function(dialogRef){
						dialogRef.close();
					}
				}
				]
			}); 
			return false;
		}
		BootstrapDialog.show({  //显示需要提交的表单。
			title:'提示信息',	
			message: '你确定要批量毕业吗？',
			closable: false, 
			buttons: [{
				label: '确定',
				cssClass: 'btn-danger',
				action: function(dialogRef){
					stu.batchByOrTuiXue(pkids,"BY",rxnfpkids,bxpkids,stuPkids);
					dialogRef.close();
				}
			},
			{
				label: '关闭',
				cssClass: 'btn-default',
				action: function(dialogRef){
					dialogRef.close();
				}
			}]
		});
	});
	
	//复学按钮点击事件
	$("#btn_fuxue").click(function(){
		var pkids = stu.getIdSelections();
		var rxnfpkids = stu.getRXNFPkidSelections();
		var bxpkids = stu.getBXPkidelections();
		var stuPkids = stu.getPKIdSelections();
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			BootstrapDialog.show({  //显示需要提交的表单。
				title:'提示信息',
				message: '请至少选择一条数据!',
				buttons: [{
					label: '关闭',
					cssClass: 'btn-danger',
					action: function(dialogRef){
						dialogRef.close();
					}
				}
				]
			}); 
			return false;
		}
		BootstrapDialog.show({  //显示需要提交的表单。
			title:'提示信息',	
			message: '你确定要批量复学吗？',
			closable: false, 
			buttons: [{
				label: '确定',
				cssClass: 'btn-danger',
				action: function(dialogRef){
					stu.batchByFuXue(pkids,"BY",rxnfpkids,bxpkids,stuPkids);
					dialogRef.close();
				}
			},
			{
				label: '关闭',
				cssClass: 'btn-default',
				action: function(dialogRef){
					dialogRef.close();
				}
			}]
		});
	});
	
	//批量删除按钮点击事件
	$("#btn_del").click(function(){
		var pkids=stu.getIdSelections();
		var rxnfpkids = stu.getRXNFPkidSelections();
		var bxpkids = stu.getBXPkidelections();
		var stuPkids = stu.getPKIdSelections();
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			BootstrapDialog.show({  //显示需要提交的表单。
				title:'提示信息',
				message: '请至少选择一条数据!',
				buttons: [{
					label: '关闭',
					cssClass: 'btn-danger',
					action: function(dialogRef){
						dialogRef.close();
					}
				}
				]
			}); 
			return false;
		}
		BootstrapDialog.show({  //显示需要提交的表单。
			title:'提示信息',	
			message: '你确定要批量删除吗？',
			closable: false, 
			buttons: [{
				label: '确定',
				cssClass: 'btn-danger',
				action: function(dialogRef){
					stu.batchDel(pkids,rxnfpkids,bxpkids,stuPkids);
					dialogRef.close();
				}
			},
			{
				label: '关闭',
				cssClass: 'btn-default',
				action: function(dialogRef){
					dialogRef.close();
				}
			}]
		});
		
	});
	//批量通过方法
	stu.batchTongGuo=function(pkids,status){
		$.post(_basepath+"stuinfo/batchTongGuo.json?pkids="+pkids+'&&status='+status+"&&IS_TONGGUO_REASON="+"",function(data){
			if(data.result=="success"){
				layer.msg("操作成功!");
				$("#stuinfotable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}
		});
	};
	//批量毕业或退学方法
	stu.batchByOrTuiXue=function(pkids,status,rxnfpkids,bxpkids,stuPkids){
		$.post(_basepath+"stuinfo/batchByOrTuiXue.json?pkids="+pkids+'&&status='+status+'&&rxnfpkids='
				+rxnfpkids+'&&bxpkids='+bxpkids+'&&stuPkids='+stuPkids,function(data){
			if(data.result=="success"){
				layer.msg("操作成功!");
				$("#stuinfotable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}
		});
	};
	
	//批量复学方法
	stu.batchByFuXue=function(pkids,status,rxnfpkids,bxpkids,stuPkids){
		$.post(_basepath+"stuinfo/batchByFuXue.json?pkids="+pkids+'&&status='+status+'&&rxnfpkids='
				+rxnfpkids+'&&bxpkids='+bxpkids+'&&stuPkids='+stuPkids,function(data){
			if(data.result=="success"){
				layer.msg("操作成功!");
				$("#stuinfotable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}
		});
	};
	//批量删除方法
	stu.batchDel=function(e,rxnfpkids,bxpkids,stuPkids){
		$.post(_basepath+"stuinfo/batchDel.json?pkids="+e+'&&rxnfpkids='+rxnfpkids+'&&bxpkids='+bxpkids+'&&stuPkids='+stuPkids,function(data){
			if(data.result=="success"){
				layer.msg("删除成功!");
				 $("#stuinfotable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}
			if(data.result=="paid"){
				layer.msg("您选择的信息包含已缴费的学生，不能删除!");
			}
		});
	};
	
	//获取list表格数据
	stu.getTab=function () {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		//迎新
		var YX = $("#YX").val();
		// 引例四
		var url = _basepath+"stuinfo/getstutable.json?";

		var res_data="";
		$.ajaxSettings.async = true;
		$('#stuinfotable').bootstrapTable(
						{
							method: 'post',  
							url : url,//数据源
							dataType: "json",
							contentType:"application/x-www-form-urlencoded; charset=UTF-8",
							dataField : "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
							totalField : 'total',
							sortOrder : 'desc',
							striped : true, //表格显示条纹  
							pagination : true, //启动分页  
							pageNumber : 1, //当前第几页  
							pageSize : 20, //每页显示的记录数  
							pageList : [1,5,10,20,30,50,100], //记录数可选列表  
							search : false, //是否启用查询  
							formatSearch : function() {
								return '请输入简拼.编码.企业名称查询';
							},//搜索框提示文字
							searchAlign : 'left',
							buttonsAlign : 'left',
							toolbarAlign : 'left',
							searchOnEnterKey : false,//true为按回车触发搜索事件
							showColumns : true, //显示下拉框勾选要显示的列  
							showRefresh : false, //显示刷新按钮  --只是前台刷新，个人觉得没什么用
							minimumCountColumns : 2, //最少允许的列数
							sidePagination : "server", //表示服务端请求  
							totalRows : 0, // server side need to set
							singleSelect : false,
							clickToSelect : true,
							onColumnSwitch: function(field,checked){

								var resultColumnList = $('#stuinfotable').bootstrapTable('getVisibleColumns');
								var resultColumns = "";		
								$.each(resultColumnList, function(i, column) {
									if(column.field=='0'/*||column.field=='opt'*/){
										return;
									}else{
										resultColumns+=column.field+",";
									}									
						     	});
								$.post("stuinfo/updateShowCols.json",{table_show_cols:resultColumns,table_name:'T_STUDENT'},function(data){
									if(data.result=="success"){
										colStr=resultColumns;//重新初始化字段字符串
										res_data=data;
										$('.bootstrap-table tr td').each(function () {
							                $(this).attr("title", $(this).text());
							                $(this).css("cursor", 'pointer');
							            });
									}
								})
							},
							onLoadSuccess:function(data){
								res_data=data;
								$('.bootstrap-table tr td').each(function () {
					                $(this).attr("title", $(this).text());
					                $(this).css("cursor", 'pointer');
					            });
							},
							onDblClickRow:function(row){
							},
							onCheckSome:function(rows){
								console.log(rows)
							},
							queryParams : function getParams(params) {
								//入学年份
				      			var grade=$("#grade").val();
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
				      			//在学状态下拉
				      			var zxzt=$('#zxzt').val();
				      			//层次
				      			var cengci=$("#cengci").val();
				      			var cengci_str='';
				      			if(cengci!=null){
				      				for(var i=0;i<cengci.length;i++){
				      					cengci_str+=cengci[i]+',';
				          			}
				      			}
				      			//预交年份
				      			var yjgrade=$("#yjgrade").val();
				      			var yjgrade_str='';
				      			if(yjgrade!=null){
				      				for(var i=0;i<yjgrade.length;i++){
				      					yjgrade_str+=yjgrade[i]+',';
				      				}
				      			}
				      			//校考成绩
				      			var meiyuan=$("#meiyuan").val();
				      			var meiyuan_str='';
				      			if(meiyuan!=null){
				      				for(var i=0;i<meiyuan.length;i++){
				      					meiyuan_str+=meiyuan[i]+',';
				      				}
				      			}
				      			$("#sortName").val(this.sortName);
				      			$("#sortOrder").val(this.sortOrder);
								var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, //页面大小
									seText : $('#search').val(),
									grade_str:grade_str,
									banxing_str:banxing_str,
									banji_str:banji_str,
									partschool_str:partschool_str,
									zxzt:zxzt,
									yjgrade_str:yjgrade_str,
									meiyuan_str:meiyuan_str,
									cengci_str:cengci_str,
									GYRXSJBEGIN:$('#GYRXSJBEGIN').val(),//高一入学时间起
									GYRXSJEND:$('#GYRXSJEND').val(),//高一入学时间止
									LKFSQI:$('#LKFSQI').val(),//联考分数起
									LKFSZHI:$('#LKFSZHI').val(),//联考分数止
									CJSJTMBEGIN:$('#CJSJTMBEGIN').val(),//报名时间起
									CJSJTMEND:$('#CJSJTMEND').val(),////报名时间止
									IS_TONGGUO:$('#IS_TONGGUO').val(),//审核状态
									IS_RUZHU:$('#IS_RUZHU').val(),//入住状态
									IS_ISPAY:$('#IS_ISPAY').val(),//是否缴费
									XUEHAOQ:$("#XUEHAOQ").val(),//学号起
									XUEHAOZ:$("#XUEHAOZ").val(),//学号止
									sortName : this.sortName,
									sortOrder : this.sortOrder,
									pageindex : this.pageNumber
								//当前页码
								};
								return temp;
							},
							 queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求  
							  silent: true,  //刷新事件必须设置  
							buttonsAlign : "right",//按钮对齐方式
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
										field : 'SHENFENZHENGHAO',
										title:'身份证号',
										align : "center",
										halign : 'center',
										sortable : false,
										width:'200px'
									},
									{
										field : 'XINGMING',
										title:'姓名',
										align : "center",
										halign : 'center',
										sortable : false,
										width:'150px'
									},
									{
										field : 'XUEHAO',
										title : '学号',
										align : "center",
										halign : 'center',
										sortable : true,
										width:'150px'
									},
									
									{
										field : 'XINGBIE',
										title : '性别',
										align : "center",
										halign : 'center',
										sortable : false,
										width:'50px'
									},
									{
										field : 'SHOUJI',
										align : "center",
										halign : 'center',
										title : '手机',
										width:'150px'
											
									},	
									{
										field: 'JIATINGZHUZHI',//可不加  
										title : '家庭住址',//标题  可不加
										align : "center",
										halign : 'center',
										width:'150px'
									},
									{
										field : 'YOUZHENGBIANMA',
										align : "center",
										halign : 'center',
										title : '邮政编码',
										width:'150px'
											
									},									
									{
										field : 'ONE_JHR',
										align : "center",
										halign : 'center',
										title : '第一监护人',
										width:'150px'
											
									},									
									{
										field : 'ONE_JHRGX',
										align : "center",
										halign : 'center',
										title : '家庭关系',
										width:'200px'
											
									},
									{
										field : 'ONE_JHRDH',
										align : "center",
										halign : 'center',
										title : '监护人电话',
										width:'150px'
											
									},	
									{
										field : 'ONE_JHRDW',
										align : "center",
										halign : 'center',
										title : '监护人单位',
										width:'150px'
											
									},
									{
										field : 'ONE_JHRZW',
										align : "center",
										halign : 'center',
										title : '监护人职务',
										width:'150px'
											
									},
									{
										field : 'TWO_JHR',
										align : "center",
										halign : 'center',
										title : '第二监护人',
										width:'150px'
											
									},	
									{
										field : 'TWO_JHRGX',
										align : "center",
										halign : 'center',
										title : '家庭关系',
										width:'200px'
											
									},	
									{
										field : 'TWO_JHRDH',
										align : "center",
										halign : 'center',
										title : '监护人电话',
										width:'150px'
											
									},
									{
										field : 'TWO_JHRDW',
										align : "center",
										halign : 'center',
										title : '监护人单位',
										width:'150px'
											
									},
									{
										field : 'TWO_JHRZW',
										align : "center",
										halign : 'center',
										title : '监护人职务',
										width:'150px'
											
									},
									{
										field : 'BINGSHI',
										align : "center",
										halign : 'center',
										title : '病史',
										width:'150px'
											

									},
									{
										field : 'LJQHTJ',
										align : "center",
										halign : 'center',
										title : '了解强化的途径',
										width:'150px'

									},
									{
										field : 'TUIJIANREN',
										align : "center",
										halign : 'center',
										title : '推荐人',
										width:'150px'

									},
									{
										field : 'WHKXUEXIAONAME',
										align : "center",
										halign : 'center',
										title : '文化课学校',
										sortable : true,
										width:'150px'

									},
									{
										field : 'GYRXSJ',
										align : "center",
										halign : 'center',
										title : '高一入学时间',
										width:'150px'
									},
									{
										field : 'BANJINAME',
										align : "center",
										halign : 'center',
										title : '班级',
										width:'150px'								
									},
									{
										field : 'WENLIKE',
										align : "center",
										halign : 'center',
										title : '文理科',
										width:'150px'
											
									},
									{
										field : 'BZRSCHOOL',
										align : "center",
										halign : 'center',
										title : '学校班主任',
										width:'100px'

									},
									{
										field : 'BZRPHONE',
										align : "center",
										halign : 'center',
										title : '班主任电话',
										width:'100px'
											
									},
									{
										field : 'CENGCI',
										align : "center",
										halign : 'center',
										title : '学生类型',
										width:'120px'

									},
									{
										field : 'STARTMEISHU',
										align : "center",
										halign : 'center',
										title : ' 自何时学习美术',
										width:'120px'

									},
									{
										field : 'KEMU',
										align : "center",
										halign : 'center',
										title : '已学过的科目',
										width:'150px'
									},
									{
										field : 'RXNIANFEN',
										align : "center",
										halign : 'center',
										title : '入学年份',
										sortable : true,
										width:'100px'

									},
									{
										field : 'CJSJTM',
										align : "center",
										halign : 'center',
										title : '报名时间',
										width:'100px'
											
									},
									{
										field : 'BANJI_TYPE',
										align : "center",
										halign : 'center',
										title : '班型',
										sortable : true,
										width:'150px'
											
									},	
									{
										field : 'YJ_BANJI_TYPE',
										align : "center",
										halign : 'center',
										title : '预交班型',
										sortable : true,
										width:'150px'

									},
									{
										field : 'YJ_NIANFEN',
										align : "center",
										halign : 'center',
										title : '预交年份',
										sortable : true,
										width:'150px'
											
									},
									{
										field : 'OLD_BANJINAME',
										align : "center",
										halign : 'center',
										title : '原班级',
										width:'150px'
											
									},
									{
										field : 'GZ_BANJI',
										align : "center",
										halign : 'center',
										title : '高中班级',
										width:'150px'
											
									},
									{
										field : 'LKFS',
										align : "center",
										halign : 'center',
										title : '联考分数',
										width:'150px'
											
									},
									{
										field : 'KSNUMBER',
										align : "center",
										halign : 'center',
										title : '考生号',
										width:'150px'
											
									},
									{
										field : 'XK_MARK',
										align : "center",
										halign : 'center',
										title : '校考成绩',
										width:'150px'
											
									},
									/*{
										field : 'IS_BD',
										align : "center",
										halign : 'center',
										title : '是否报到',
										width:'150px',
										formatter : function(value, row,
												index) {
											if(row.IS_BD == 'Y'){
												return '是';
											}
											if(row.IS_BD == 'N'){
												return '否';
											}
										}
											
									},*/
									{
										field : 'ZXZT',
										align : "center",
										halign : 'center',
										title : '在学状态',
										width:'150px'
											
									},
									/*{
										field : 'BD_DATE',
										align : "center",
										halign : 'center',
										title : '报到时间',
										width:'150px'
											
									},*/
									{
										field : 'IS_TONGGUO',
										align : "center",
										halign : 'center',
										title : '审核状态',
										width:'150px'
											
									},
									{
										field : 'SHTGSJ',
										align : "center",
										halign : 'center',
										title : '审核通过时间',
										width:'150px'
											
									},
									{
										field : 'TOTALMONEY',
										align : "center",
										halign : 'center',
										title : '缴费总额',
										width:'150px'

									},
									{
										field : 'RZZT',
										align : "center",
										halign : 'center',
										title : '入住状态',
										width:'100px'
											
									},
									{
										field : 'CAOZUOREN',
										align : "center",
										halign : 'center',
										title : '操作人',
										width:'150px'
											
									},
									{
										field : 'REMARKS1',
										align : "center",
										halign : 'center',
										title : '备注1',
										width:'150px'
											
									},
									{
										field : 'REMARKS2',
										align : "center",
										halign : 'center',
										title : '备注2',
										width:'150px'
											
									},
									{
										field : 'REMARKS3',
										align : "center",
										halign : 'center',
										title : '备注3',
										width:'150px'
											
									},
									{
										field : 'opt',
										title : '操作',
										align : "center",
										halign : 'center',									
										width:'150px',
										formatter : function(value, row,
												index) {	
												var jihuoHtml = "";
												if(row.TONGGUO!=0 ){//表示名单已经关闭，此时显示激活按钮
													jihuoHtml = SESSION_MENU_BUTTONS.xslb_bj == 1?'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;':'';
												}
												return [
												        	jihuoHtml,
												        '<a id="detailQuery" style="cursor:pointer;"><span title="查看详情" class="fa fa-search"></span></a>']
												.join('');
										
										}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										 events : {
											'click .edit' : function(e,value, row, index) {
												  //编辑  编辑人员
												  $(".jf_szright").load(_basepath+'stuinfo/goEdit.json?TMPKID='+row.TMPKID+"&isEdit="+"isEdit");
	    							        
											},
											'click #detailQuery' : function(e,value, row, index) {
												//去查看详情页面
												 $(".jf_szright").load(_basepath+'stuinfo/goDetail.json?TMPKID='+row.TMPKID);
											},
										} 
									},
									{
										field: 'PKID',//可不加  
										align : "center",
										halign : 'center',
										checkbox : true
									},
									{
										field: 'TONGGUO',//可不加  
										align : "center",
										halign : 'center',
										checkbox : true
									},
									{
										field: 'RXNIANFEN_PKID',//可不加  
										align : "center",
										halign : 'center',
										checkbox : true
									},
									{
										field: 'BANJI_TYPE_PKID',//可不加  
										align : "center",
										halign : 'center',
										checkbox : true
									},
									{
										field: 'TMPKID',//可不加  
										align : "center",
										halign : 'center',
										checkbox : true
									},
							],
						});
		
	    $('#stuinfotable').bootstrapTable('hideColumn', 'PKID');//隱藏列hideAllColumns	   
	    $('#stuinfotable').bootstrapTable('hideColumn', 'TMPKID');//隱藏列hideAllColumns	   
	    $('#stuinfotable').bootstrapTable('hideColumn', 'RXNIANFEN_PKID');//隱藏列hideAllColumns	   
	    $('#stuinfotable').bootstrapTable('hideColumn', 'BANJI_TYPE_PKID');//隱藏列hideAllColumns	   
	    $('#stuinfotable').bootstrapTable('hideColumn', 'TONGGUO');//隱藏列hideAllColumns	   
	    
		if(colStr!=null && colStr!=''){
			var resultColumnList = $('#stuinfotable').bootstrapTable('getVisibleColumns');

			$.each(resultColumnList, function(i, column) {
				if(column.field=='0'/*||column.field=='opt'*/){
					return;
				}
				 $('#stuinfotable').bootstrapTable('hideColumn', column.field);
	     	});		
			var array=colStr.split(",");
			for(var i=0;i<array.length;i++){
				var field=array[i];
				if(field==''){
					continue;
				}
				$('#stuinfotable').bootstrapTable('showColumn', field);
				
			}
			
		}
		
	};
	
	//加载表格数据
	stu.getTab();
	//更多查询条件
	$('#more').click(function(){
		//所属组织
		if($('#icon').hasClass('fa-angle-double-down')){
			$('#icon').removeClass('fa-angle-double-down');
			$('#icon').addClass('fa-angle-double-up');
			$("#moreQueryArea").show();
		}else{
			$('#icon').removeClass('fa-angle-double-up');
			$('#icon').addClass('fa-angle-double-down');
			$("#moreQueryArea").hide();
		}
		
	});
	$("#grade").bind("change", function() {
		changeGrade();
	});
	$("#banxing").bind("change", function() {
		changeGrade();
	});
	//获取选中的行pkid
	stu.getPKIdSelections=function () {
			var $table = $('#stuinfotable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.PKID;
        });
	};
	//获取选中的行bm的pkid
	stu.getIdSelections=function () {
		var $table = $('#stuinfotable');
		return $.map($table.bootstrapTable('getSelections'), function(row) {
			return row.TMPKID;
		});
	};
	//获取选中的行bm的pkid
	stu.getTongGuoSelections=function () {
		var $table = $('#stuinfotable');
		return $.map($table.bootstrapTable('getSelections'), function(row) {
			return row.TONGGUO;
		});
	};
	
	//获取选中的行入学年份pkid
	stu.getRXNFPkidSelections=function () {
		var $table = $('#stuinfotable');
		return $.map($table.bootstrapTable('getSelections'), function(row) {
			return row.RXNIANFEN_PKID;
		});
	};
	//获取选中的行班型pkid
	stu.getBXPkidelections=function () {
		var $table = $('#stuinfotable');
		return $.map($table.bootstrapTable('getSelections'), function(row) {
			return row.BANJI_TYPE_PKID;
		});
	};
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
	 $('#cengci').multiselect({
    	 enableFiltering: true,
    	 includeSelectAllOption: true,
    	 selectAllText: ' 全选',
    	 nonSelectedText: '请选择学生类型',
         allSelectedText: '全部选择',
         selectAllJustVisible: true,
    	 filterPlaceholder:'请输入学生类型名称',
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
	 $('#yjgrade').multiselect({
		 enableFiltering: true,
		 includeSelectAllOption: true,
		 selectAllText: ' 全选',
		 nonSelectedText: '请选择预交年份',
		 allSelectedText: '全部选择',
		 selectAllJustVisible: true,
		 filterPlaceholder:'请输入预交年份',
		 buttonWidth: '183px'//设置字体过长显示...
	 });
	 $('#meiyuan').multiselect({
		 enableFiltering: true,
		 includeSelectAllOption: true,
		 selectAllText: ' 全选',
		 nonSelectedText: '请选择校考成绩',
		 allSelectedText: '全部选择',
		 selectAllJustVisible: true,
		 filterPlaceholder:'请输入校考成绩',
		 buttonWidth: '183px'//设置字体过长显示...
	 });
	
})(jQuery, window);

