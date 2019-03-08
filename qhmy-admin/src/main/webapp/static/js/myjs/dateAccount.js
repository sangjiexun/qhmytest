/**
 * 日结账页面对象
 */
//@ sourceURL=dateAccount.js
(function($, window) {
	var dateAccount = {};
	
	laydate.render({
		elem:'#dateAccountDateq',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'datetime',
		format: 'yyyy-MM-dd HH:mm:ss',
		festival: true, //显示节日
		start: 0
	});
	laydate.render({
		elem:'#dateAccountDatez',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'datetime',
		format: 'yyyy-MM-dd HH:mm:ss',
		festival: true, //显示节日
		start: 0
	});
	
	//获取选中的行
	dateAccount.getIdSelections=function () {
			var $table = $('#dateAccountTable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.PKID;
        });
	};
	
	//查询按钮点击事件
	$('#accountQuery').click(function(){
		$("#dateAccountTable").bootstrapTable('destroy'); 
		dateAccount.getTab();
	});
	
	//点击导出Excel按钮事件
	$("#accountExcel").click(function(){
		//选择的日期
		var dateAccountDate=$('#dateAccountDate').val();
		//学生姓名
		var studentName = $('#studentName').val();
		//缴费类型
		var JIAOFEILEIXING = $('#JIAOFEILEIXING').val();
		window.location.href=encodeURI(_basepath+'dateAccount/exportExcel.json?DATEACCOUNTDATE='+dateAccountDate+'&&STUDENTNAME='+studentName+
				'&&JIAOFEILEIXING='+JIAOFEILEIXING);
	});
	
	//更新汇总信息
	dateAccount.updateTotalMsg = function(dateAccountDate,studentName,JIAOFEILEIXING){
		var url = _basepath+"dateAccount/getAmountMsg.json";
		$.ajax({
			type : 'POST',
			url : url,
			async : false,
			data : {
				DATEACCOUNTDATE : dateAccountDate,
				STUDENTNAME : studentName,
				JIAOFEILEIXING : JIAOFEILEIXING
			},
			dataType : "json",
			success : function(result) {
				var amountMsg = result.amountMsg;
				$("#TOTALPEOPLE").html("收费人数：<a>"+amountMsg.TOTALPEOPLE+"</a>"); 
				$("#CASHTOTALMONEY").html("现金：<a>"+amountMsg.CASHTOTALMONEY+"</a>"); 
				$("#TOTALMONEY").html("总费用：<a>"+amountMsg.TOTALMONEY+"</a>"); 
				$("#CARDTOTALMONEY").html("银行卡：<a>"+amountMsg.CARDTOTALMONEY+"</a>"); 
				$("#TTTOTALMONEY").html("电汇：<a>"+amountMsg.TTTOTALMONEY+"</a>"); 
				$("#ZFBTOTALMONEY").html("支付宝：<a>"+amountMsg.ZFBTOTALMONEY+"</a>"); 
				$("#WXTOTALMONEY").html("微信：<a>"+amountMsg.WXTOTALMONEY+"</a>");
			}
		});
	};
	
	//作废按钮点击事件
	$("#accountZuoFei").click(function(){
		dateAccount.zuofei();
	});
	
	//作废订单明细
	dateAccount.zuofei = function(){
		var pkids = dateAccount.getIdSelections();
		if(pkids.length==0){
			layer.msg('请先选择要作废的记录!');
			return false;
		}
		BootstrapDialog.show({  
	 	 	title: '提示信息',
            message: '会将同订单记录一起作废且无法恢复，确定作废吗？',
            size :BootstrapDialog.SIZE_NORMAL,
            closable: true, 
              buttons: [{
			    label: '确定',
			    cssClass: 'btn btn-danger',
			    action: function(dialogRef){
			    		var detail_pkids = "";
			    		for(var i=0;i<pkids.length;i++){
			    			detail_pkids = detail_pkids+pkids[i]+",";
			    		}
	                    var url = _basepath+"dateAccount/zuofei.json";
	             		$.ajax({
	             			type : 'POST',
	             			url : url,
	             			async : false,
	             			data : {detail_pkids:detail_pkids},
	             			dataType : "json",
	             			success : function(data) {
	             				if(data.result=='success'){
	             					layer.msg('作废成功!');
	             					dialogRef.close();
	             					$("#dateAccountTable").bootstrapTable('destroy'); 
	             					dateAccount.getTab();
	             				}
	             			},
	             			error : function(){
	             			}
	             		});
		  	}
              },{
			    label: '取消',
			    cssClass: 'btn-default',
			    action: function(dialogRef){
			       dialogRef.close();
			    }
              }]
		});
	};
	
	//确定并结账点击事件
	$("#settleAccounts").click(function(){
		dateAccount.dateAccount();
	});
	
	//确定并结账settleAccounts
	dateAccount.dateAccount = function(){
		BootstrapDialog.show({  
	 	 	title: '提示信息',
            message: '确定结账吗？',
            size :BootstrapDialog.SIZE_NORMAL,
            closable: true, 
              buttons: [{
			    label: '确定',
			    cssClass: 'btn btn-danger',
			    action: function(dialogRef){
	                    dialogRef.close();
	                    var url = _basepath+"dateAccount/settleAccounts.json";
	             		$.ajax({
	             			type : 'POST',
	             			url : url,
	             			async : false,
	             			dataType : "json",
	             			success : function(data) {
	             				if(data.result=='success'){
	             					layer.msg('结账成功',{skin : 0,offset: [($(window).height())/8, ($(window).width())/2.2]});
	             					//选择的日期
	             					var dateAccountDate=$('#dateAccountDate').val();
	             					//学生姓名
	             					var studentName = $('#studentName').val();
	             					//缴费类型
	             					var JIAOFEILEIXING = $('#JIAOFEILEIXING').val();
	             					dateAccount.updateTotalMsg(dateAccountDate,studentName,JIAOFEILEIXING);
	             					$("#dateAccountTable").bootstrapTable('refresh', {url: encodeURI(_basepath+"dateAccount/dateAccountTable.json?DATEACCOUNTDATE="+dateAccountDate+"&&STUDENTNAME="+studentName
	             							+"&&JIAOFEILEIXING="+JIAOFEILEIXING)});
	             				}else if(data.result=='error'){
	             					BootstrapDialog.show({  
		             			 	 	title: '提示信息',
		             		            message: '没有结账记录，无法结账',
		             		            size :BootstrapDialog.SIZE_NORMAL,
		             		            closable: true, 
		             		              buttons: [{
		             					    label: '确定',
		             					    cssClass: 'btn btn-danger',
		             					    action: function(dialogRef){
		             			                    dialogRef.close();
		             					    }
		             		              }]
		             				});
	             				}
	             			},
	             			error : function(){
	             				BootstrapDialog.show({  
	             			 	 	title: '提示信息',
	             		            message: '结账失败，请重新点击结账或联系管理人员',
	             		            size :BootstrapDialog.SIZE_NORMAL,
	             		            closable: true, 
	             		              buttons: [{
	             					    label: '确定',
	             					    cssClass: 'btn btn-danger',
	             					    action: function(dialogRef){
	             			                    dialogRef.close();
	             					    }
	             		              }]
	             				});
	             			}
	             		});
		  	}
              },{
			    label: '取消',
			    cssClass: 'btn-default',
			    action: function(dialogRef){
			       dialogRef.close();
			    }
              }]
		});
	};
		// 获取bootStrapTable表格数据,及参数设置
		dateAccount.getTab = function() {
		var url = _basepath + "dateAccount/dateAccountTable.json";
		$('#dateAccountTable').bootstrapTable(
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
							showColumns : true, // 显示下拉框勾选要显示的列
							showRefresh : false, // 显示刷新按钮 --只是前台刷新，个人觉得没什么用
							minimumCountColumns : 2, // 最少允许的列数
							sidePagination : "server", // 表示服务端请求
							totalRows : 0, // server side need to set
							singleSelect : false,
							clickToSelect : true,
							onColumnSwitch: function(field,checked){
								/*var resultColumnList = $('#dateAccountTable').bootstrapTable('getVisibleColumns');
								var resultColumns = "";		
								$.each(resultColumnList, function(i, column) {
									if(column.field=='0'||column.field=='opt'){
										return;
									}else{
										resultColumns+=column.field+",";
									}									
						     	});
								$.post("stuinfo/updateShowCols.json",{table_show_cols:resultColumns,table_name:'T_RIJIEZHANG'},function(data){
									if(data.result=="success"){
//										layer.msg("保存成功!");
									}
								})*/
							},
							onDblClickRow : function(row) {
							},
							queryParams : function getParams(params) {
								//选择的日期起
								var dateAccountDateq=$('#dateAccountDateq').val();
								//选择的日期止
								var dateAccountDatez=$('#dateAccountDatez').val();
								//学生姓名学号身份证号
								var studentName = $('#studentName').val();
								//缴费方式
								var JIAOFEILEIXING = $('#JIAOFEILEIXING').val();
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									DATEACCOUNTDATEQ : dateAccountDateq,
									DATEACCOUNTDATEZ : dateAccountDatez,
									STUDENTNAME : studentName,
									JIAOFEILEIXING : JIAOFEILEIXING,
									sortName : this.sortName,
									sortOrder : this.sortOrder,
									pageindex : this.pageNumber
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
										field : 'SHENFENZHENGHAO',
										title : '身份证号',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'XINGMING',
										title : '姓名',
										align : "left",
										halign : 'center',
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
										field : 'PAY_ORDERNO',
										title : '订单号',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'GRADE_NAME',
										title : '入学年份',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'CLASSTYPE_NAME',
										title : '班型',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'PAY_STYLE_NAME',
										title : '缴费类型',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'JIAOFEILEIXING',
										title : '缴费方式',
										align : "center",
										halign : 'center',
										sortable : false

									},
									{
										field : 'INPUT_OUTPUT',
										title : '收支方式',
										align : "center",
										halign : 'center',
										sortable : false

									},
									{
										field : 'MONEY',
										title : '缴费金额',
										align : "center",
										halign : 'center',
										sortable : false

									},
									{
										field : 'CJSJ',
										title : '操作时间',
										align : "center",
										halign : 'center',
										sortable : false

									}],
						});
		/*if(colStr!=null && colStr!=''){
			var resultColumnList = $('#dateAccountTable').bootstrapTable('getVisibleColumns');
			$.each(resultColumnList, function(i, column) {
				if(column.field=='0'||column.field=='opt'){
					return;
				}
				 $('#dateAccountTable').bootstrapTable('hideColumn', column.field);
	     	});		
			var array=colStr.split(",");
			for(var i=0;i<array.length;i++){
				var field=array[i];
				if(field==''){
					continue;
				}
				$('#dateAccountTable').bootstrapTable('showColumn', field);
				
			}
			
		}*/
	};
	//加载表格数据
	dateAccount.getTab();
})(jQuery, window);
