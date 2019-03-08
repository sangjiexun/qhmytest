/**
 * 迎新页面
 */
//@ sourceURL=wgyyingxin.js
var yingxin = {};
yingxin.payItemPkid = '';
	yingxin.userPkid = '';
	yingxin.studentPkid  = $("#STUDENT_PKID_FLOW").val();
	yingxin.studentName = '';
	yingxin.jj = '';
	yingxin.payItemPkids = '';
	yingxin.payItemNames = '';

	yingxin.moneyBatchArray = '';
	yingxin.moneyCardBatchArray = '';
	yingxin.moneyAlipayBatchArray = '';
	yingxin.moneyWechatBatchArray = '';
	yingxin.moneyTTBatchArray = '';
	yingxin.ccount = 0;
	yingxin.AMOUNTRECEIVABLE_total = 0;
	yingxin.AMOUNTCOLLECTION_total = 0;
	yingxin.moneyBatchTextIndex = 0;
	yingxin.payItemListAllChild;
	yingxin.STUDENT_PKID = "";
	yingxin.fromSchool = $("#COLLEGES_NAME_EN").val();
	var payButtonStatus = false;

	var resetPayButtionStatus = function(){
		$("#payButton").removeAttr("disabled");
		$("#payButtonBatch").removeAttr("disabled");
		payButtonStatus = false;
	};
	
(function($, window) {
	
	
	
	
	//获取学生信息
	yingxin.getStudentInfo = function(STUDENT_PKID){
		var flag = false;
		$.ajax({
			  type: 'POST',
			  url: _basepath+"yingxin/getStudentInfo.json",
			  data: {
				STUDENT_PKID : STUDENT_PKID
			  },
			  dataType : "json",
			  async:false,
			  success : function(result) {
				  if(result.result == "success"){
				  	  flag = true;
					  var resultPd = result.resultPd;
					  $("#TOUXIANG").attr("src",resultPd.TOUXIANG == null || resultPd.TOUXIANG == '' ? 'static/gxjf/images/defaulttouxiang.jpg' : resultPd.TOUXIANG);
					  $("#LUQUHAO").html(resultPd.LUQUHAO == null || resultPd.LUQUHAO == '' ? ' - ' : resultPd.LUQUHAO);
					  $("#SHENFENZHENGHAO").html(resultPd.SHENFENZHENGHAO == null || resultPd.SHENFENZHENGHAO == '' ? ' - ' : resultPd.SHENFENZHENGHAO);
					  $("#XUEYUAN").html(resultPd.XUEYUAN == null || resultPd.XUEYUAN == '' ? ' - ' : resultPd.XUEYUAN);
					  $("#RUXUENIANFEN").html(resultPd.RUXUENIANFEN == null || resultPd.RUXUENIANFEN == '' ? ' - ' : resultPd.RUXUENIANFEN);
					  $("#XINGMING").html(resultPd.XINGMING == null || resultPd.XINGMING == '' ? ' - ' : resultPd.XINGMING);
					  $("#XINGBIE").html(resultPd.XINGBIE == null || resultPd.XINGBIE == '' ? ' - ' : resultPd.XINGBIE);
					  $("#ZHUANYE").html(resultPd.ZHUANYE == null || resultPd.ZHUANYE == '' ? ' - ' : resultPd.ZHUANYE);
					  $("#DIANHUA").html(resultPd.DIANHUA == null || resultPd.DIANHUA == '' ? ' - ' : resultPd.DIANHUA);
				  }
			  }
			});
		return flag;
	};
	
	//获取流程状态
	yingxin.getFlowStatusN = function(STUDENT_PKID,NOW_FLOW_NODE){
		var flowStatusN = "";
		if(STUDENT_PKID != null && STUDENT_PKID !=''){
			$.ajax({
				  type: 'POST',
				  url: _basepath+"yingxin/getFlowStatus.json",
				  async:false,
				  data: {
					STUDENT_PKID : STUDENT_PKID
				  },
				  dataType : "json",
				  success : function(result) {
					  if(result.result == "success"){
						  var flowStatus = 0;
						  var resultPd = result.resultPd;
						  if(resultPd.isBaodao == 'N'){
							  flowStatus = 1;
							  if(NOW_FLOW_NODE != '1')
								  flowStatusN = "报到";
						  }/*else if(resultPd.IS_OPENECARD == 'N'){
							  flowStatus = 2;
							  if(NOW_FLOW_NODE != '2')
								  flowStatusN = "一卡通发卡";
						  }*/else if(resultPd.IS_FENQIN == 'N'){
							  flowStatus = 3;
							  if(NOW_FLOW_NODE != '3')
								  flowStatusN = "分寝";
						  }/*else if(resultPd.isLvshitongdao == 'N'){
							  flowStatus = 4;
							  if(NOW_FLOW_NODE != '4')
								  flowStatusN = "办理绿色通道";
						  }*//*else if(resultPd.isJiaofei == 'N'){
							  flowStatus = 5;
							  if(NOW_FLOW_NODE != '5')
								  flowStatusN = "缴费";
						  }else if(resultPd.isLingquwupin == 'N'){
							  flowStatus = 4;
							  if(NOW_FLOW_NODE != '4')
								  flowStatusN = "领取物品";
						  }else if(resultPd.isRuzhu == 'N'){
							  flowStatus = 6;
							  if(NOW_FLOW_NODE != '6')
								  flowStatusN = "入住";
						  }*//*else if(resultPd.IS_OPENECARD == 'N'){
							  flowStatus = 2;
							  if(NOW_FLOW_NODE != '2')
								  flowStatusN = "一卡通发卡";
						  }*/
						  
						  if(Number(NOW_FLOW_NODE) < flowStatus){
							  flowStatusN = "";
						  }
					  }
					  
					   
				  }
				});
		}
		
		return flowStatusN;
		
	};
	
	//获取流程状态
	yingxin.getFlowStatus = function(STUDENT_PKID){
		if(STUDENT_PKID != null && STUDENT_PKID !=''){
			$.ajax({
				  type: 'POST',
				  url: _basepath+"yingxin/getFlowStatus.json",
				  async:false,
				  data: {
					STUDENT_PKID : STUDENT_PKID
				  },
				  dataType : "json",
				  success : function(result) {
					  if(result.result == "success"){
						  var resultPd = result.resultPd;
						  if(resultPd.isBaodao == 'Y'){
							  $("#baodaoStatus").attr("class","fa fa-check-circle");
						  }else{
							  $("#baodaoStatus").attr("class","fa fa-minus-circle");
						  }
						  if(resultPd.isLvshitongdao == 'Y'){
							  $("#banlilvsetongdaoStatus").attr("class","fa fa-check-circle");
						  }else{
							  $("#banlilvsetongdaoStatus").attr("class","fa fa-minus-circle");
						  }
						  if(resultPd.isJiaofei == 'Y'){
							  $("#jiaofeiStatus").attr("class","fa fa-check-circle");
						  }else{
							  $("#jiaofeiStatus").attr("class","fa fa-minus-circle");
						  }
						 /* if(resultPd.isLingquwupin == 'Y'){
							  $("#lingquwupinStatus").attr("class","fa fa-check-circle");
						  }else{
							  $("#lingquwupinStatus").attr("class","fa fa-minus-circle");
						  }*/
						  if(resultPd.isRuzhu == 'Y'){
							  $("#ruzhuStatus").attr("class","fa fa-check-circle");
						  }else{
							  $("#ruzhuStatus").attr("class","fa fa-minus-circle");
						  }
						  if(resultPd.IS_OPENECARD == 'Y'){
							  $("#ecardopencard").attr("class","fa fa-check-circle");
						  }else{
							  $("#ecardopencard").attr("class","fa fa-minus-circle");
						  }
						  if(resultPd.IS_FENQIN == 'Y'){
							  $("#fenqinStatus").attr("class","fa fa-check-circle");
						  }else{
							  $("#fenqinStatus").attr("class","fa fa-minus-circle");
						  }
						  
						  
						  
					  }
					  
					   
				  }
				});
		}
		
		var flowNode = $("#flowNode").val();
		if(flowNode == "1"){//报到
			$("#baodaoStatus").attr("class","fa fa-dot-circle-o");
		}else if(flowNode == "2"){//一卡通
			$("#ecardopencard").attr("class","fa fa-dot-circle-o");
		}else if(flowNode == "3"){//分寝
			$("#fenqinStatus").attr("class","fa fa-dot-circle-o");
		}else if(flowNode == "4"){//办理绿色通道
			$("#banlilvsetongdaoStatus").attr("class","fa fa-dot-circle-o");
		}else if(flowNode == "5"){//缴费
			$("#jiaofeiStatus").attr("class","fa fa-dot-circle-o");
		}else if(flowNode == "6"){//入住
			$("#ruzhuStatus").attr("class","fa fa-dot-circle-o");
		}
		
		/*else if(flowNode == "2"){//办理绿色通道
			$("#banlilvsetongdaoStatus").attr("class","fa fa-dot-circle-o");
		}else if(flowNode == "3"){//缴费
			$("#jiaofeiStatus").attr("class","fa fa-dot-circle-o");
		}else if(flowNode == "4"){//领取物品
			$("#lingquwupinStatus").attr("class","fa fa-dot-circle-o");
		}else if(flowNode == "5"){//入住
			$("#ruzhuStatus").attr("class","fa fa-dot-circle-o");
		}*/
	};
	
	//获取统计信息
	yingxin.getStatisticsInfo = function(STUDENT_PKID){
		$.ajax({
			  type: 'POST',
			  url: _basepath+"yingxin/getStatisticsInfo.json",
			  data: {
				STUDENT_PKID : STUDENT_PKID
			  },
			  dataType : "json",
			  success : function(result) {
				  if(result.result == "success"){
					  var resultPd = result.resultPd;
					  $("#yingdaorenshuCount").html(resultPd.studentCount != null ? resultPd.studentCount : '0');
					  $("#baodaoCount").html(resultPd.baodaoCount != null ? resultPd.baodaoCount : '0');
					  $("#lvsetongdaoCount").html(resultPd.lvsetongdaoCount != null ? resultPd.lvsetongdaoCount : '0');
					  $("#jiaofeiCount").html(resultPd.jiaofeiCount != null ? resultPd.jiaofeiCount : '0');
					 /* $("#lingquwupinCount").html(resultPd.lingquwupinCount != null ? resultPd.lingquwupinCount : '0');*/
					  $("#ruzhuCount").html(resultPd.ruzhuCount != null ? resultPd.ruzhuCount : '0');
					  
					  $("#baodaoPercent").html(resultPd.baodaoPercent != null ? resultPd.baodaoPercent : '0%');
					  $("#lvsetongdaoPercent").html(resultPd.lvsetongdaoPercent != null ? resultPd.lvsetongdaoPercent : '0%');
					  $("#jiaofeiPercent").html(resultPd.jiaofeiPercent != null ? resultPd.jiaofeiPercent : '0%');
					  $("#lingquwupinPercent").html(resultPd.lingquwupinPercent != null ? resultPd.lingquwupinPercent : '0%');
					  $("#ruzhuPercent").html(resultPd.ruzhuPercent != null ? resultPd.ruzhuPercent : '0%');
				  }
			  }
			});
	};
	//获取报到名单
	yingxin.getArrivalStuInfo = function(){
		$.ajax({
			type: 'POST',
			url: _basepath+"payment/getarrivalstu.json",			
			dataType : "json",
			success : function(result) {
				if(result.result == "success"){
					var rows = result.rows;//后台查询数据list
					var total = result.total;//查询数据总条数
					var lenth = null;
					//设置报到名单只展示12条数据
					if(total>12){
						lenth = 12;
					}else{
						lenth=total;
					}
					//如果查询结果有值则遍历将值放在ul下
					if(rows!=null&&lenth>0){
						for(var i = 0;i<lenth;i++){
							if(rows[i].XINGMING==null||rows[i].XINGMING=='null'){
								rows[i].XINGMING = "";
							}
							$("#resultListId").append("<li>"+rows[i].ZUZHINAME+"<span class='pull-right'>"+rows[i].XINGMING+"</span></li>");							
						}
					}
				}
			}
		});
	};
	
	//学生列表弹框
	yingxin.getStudentPayTab = function(){
		
		var url = _basepath+"yingxin/getStudentQuerylistPage.json";
		$('#studentListTable').bootstrapTable(
						{
							url : url,//数据源
							dataField : "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
							totalField : 'total',
							sortOrder : 'desc',
							striped : true, //表格显示条纹  
							pagination : true, //启动分页  
							pageNumber : 1, //当前第几页  
							pageSize : 9, //每页显示的记录数  
							pageList : [1,5,10,20,30,50,100], //记录数可选列表  
							search : false, //是否启用查询  
							formatSearch : function() {
								return '查询';
							},//搜索框提示文字
							searchAlign : 'left',
							buttonsAlign : 'left',
							toolbarAlign : 'left',
							searchOnEnterKey : false,//true为按回车触发搜索事件
							showColumns : false, //显示下拉框勾选要显示的列  
							showRefresh : false, //显示刷新按钮  --只是前台刷新，个人觉得没什么用
							minimumCountColumns : 2, //最少允许的列数
							sidePagination : "server", //表示服务端请求  
							totalRows : 0, // server side need to set
							singleSelect : false,
							clickToSelect : true,
							onDblClickRow:function(row){
								var studentPkid = row.PKID;
								$("#flowNode").val("1");
				            	yingxin.doSearchForOneStudent(studentPkid);
							},
							queryParams : function getParams(params) {
								var conditions = $("#conditions").val();
								var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, //页面大小
									searchText : this.searchText,
									sortName : this.sortName,
									sortOrder : this.sortOrder,
									pageindex : this.pageNumber,
									conditions:conditions
									
								//当前页码
								};
								return temp;
							},
							buttonsAlign : "right",//按钮对齐方式
							selectItemName : 'id',
							toolbar : "#toolbar",
							toolbarAlign : 'left',
							columns : [
									{
										field : 'PKID',// 可不加
										align : "center",
										halign : 'center'
									},

									{
										field: 'XUEHAO',//可不加  
										title:'学号',
										align : "center",
										halign : 'center'
									},
									{
										field: 'SHENFENZHENGHAO',//可不加  
										title:'身份证',
										align : "center",
										halign : 'center'
									},
									{
										field : 'XINGMING',
										title:'姓名',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'DEPARTMENT_NAME',
										title:'所属组织',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'XINGBIE',
										title:'性别',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'RUXUESHIJIAN',
										title:'入学时间',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter : function(value, row,
												index){
											return row.RUXUESHIJIAN != null?new Date(row.RUXUESHIJIAN).format("yyyy-MM-dd hh:mm:ss"):"-";
										}
										
									},
									{
										field : 'ZAIXUEZT',
										title:'在学状态',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter : function(value, row,
												index) {
											if(value == 'ZX'){
												return '在学';
											}else if(value == 'TX'){
												return '退学';
											}else if(value == 'XX'){
												return '休学';
											}else if(value == 'RW'){
												return '入伍';
											}
										}
										
									},
									{
										field : 'SUSHE',
										title:'宿舍',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'DAOSHI',
										title:'导师',
										align : "center",
										halign : 'center',
										sortable : false
									}
							],
						});
	    $('#studentListTable').bootstrapTable('hideColumn', 'PKID');//隱藏列
	};

	//刷新学生table
	yingxin.refreshStudentTable = function(conditions, status){
		var url = _basepath+"yingxin/getStudentQuerylistPage.json";
		 var opt = {
				url :url,
			    silent: true,
			    query:{
			    	"conditions":conditions
			    }
			  };
		 $("#studentListTable").bootstrapTable('refresh', opt);
	};
	
	//刷新学生table
	yingxin.refreshStudentTable = function(conditions, status){
		var url = _basepath+"yingxin/getStudentQuerylistPage.json";
		 var opt = {
				url :url,
			    silent: true,
			    query:{
			    	"conditions":conditions
			    }
			  };
		 $("#studentListTable").bootstrapTable('refresh', opt);
	};
	
	//办理绿色通道
	yingxin.banlilvsetongdaoFlowContent = function(STUDENT_PKID){
		$("#banlilvsetongdaoFlowContent").html("");
		var htmls = '<div class="pa-row">'+
						'<table class="table table-striped table-hover table-bordered jf_table" style="text-align: center;">'+
							'<tr>'+
//								'<th><input type="checkbox" name="" id="" value="" /></th>'+
								'<th>学年</th>'+
								'<th>缴费类型</th>'+
								'<th>应收金额</th>'+
								'<th>贷款/缓交</th>'+
								'<th>操作</th>'+
							'</tr>';
		$.ajax({
			  type: 'POST',
			  url: _basepath+"yingxin/getBanlilvsetongdaoFlowContent.json",
			  data: {
				STUDENT_PKID : STUDENT_PKID
			  },
			  dataType : "json",
			  async: false,
			  success : function(result) {
				  if(result.result == "success"){
					  var resultList = result.resultList;
					  if(resultList != null && resultList != ''){
						  for (var i = 0; i < resultList.length; i++) {
							  //SELECT Y.SCHOOL_YEAR_NAME, PS.PAY_STYLE_NAME, L.AMOUNTRECEIVABLE, L.LOAN_MONEY
							  htmls += '<tr>'+
//											'<td><input type="checkbox" name="" id="" value="" /></td>'+
											'<td>'+resultList[i].SCHOOL_YEAR_NAME+'</td>'+
											'<td>'+resultList[i].PAY_STYLE_NAME+'</td>'+
											'<td>'+resultList[i].AMOUNTRECEIVABLE+'</td>'+
											'<td>'+(resultList[i].LOAN_MONEY == null || resultList[i].LOAN_MONEY == '' || resultList[i].LOAN_MONEY == 'null'?"-":resultList[i].LOAN_MONEY)+'</td>'+
											'<td style="text-align: center;">'+
												'<span title="登记" class="fa fa-yen text-left" onclick="javascript:yingxin.signLoanMoney(\''+resultList[i].PAY_ITEM_PARENT_PKID+'\');" PAY_ITEM_PARENT_PKID="'+resultList[i].PAY_ITEM_PARENT_PKID+'"></span>'+
											'</td>'+
										'</tr>';
						  }
					  }else{
						  htmls += '<tr class="no-records-found"><td colspan="5">没有找到匹配的记录</td></tr>';
					  }
					  
				  }else{
					  htmls += '<tr class="no-records-found"><td colspan="5">没有找到匹配的记录</td></tr>';
				  }
			  }
			});
		
		htmls += '</table>'+
		'</div>';
		
		$("#banlilvsetongdaoFlowContent").html(htmls);
	};


	//登记
	yingxin.signLoanMoney = function(PAY_ITEM_PARENT_PKID){
		$("#LOAN_MONEY").val("");
//		var PAY_ITEM_PARENT_PKID = $("#dengji").attr("PAY_ITEM_PARENT_PKID");
		var studentPkid = $("#STUDENT_PKID_FLOW").val();
		var flowNodeN = yingxin.getFlowStatusN(studentPkid,"2");
		if(flowNodeN != ''){
			layer.msg("该学生尚未办理【"+flowNodeN+"】");
			return false;
		}
		$("#PAY_ITEM_PARENT_PKID").val(PAY_ITEM_PARENT_PKID);
		$('#mymodaDaikuandengji').modal({
			keyboard: true
		});
	};

	//登记提交
	$("#submitDengjiButton").click(function(){
		var LOAN_MONEY = $("#LOAN_MONEY").val();
		if(LOAN_MONEY == ''){
			layer.msg("请先输入贷款/缓交金额！");
			return false;
		}
		if(Number(LOAN_MONEY) > 0 && Number(LOAN_MONEY) < 100000){
			var PAY_ITEM_PARENT_PKID = $("#PAY_ITEM_PARENT_PKID").val();
			var STUDENT_PKID = $("#STUDENT_PKID_FLOW").val();
			
			var url=_basepath+"yingxin/dengjiLoanMoney.json";
			$.post(url,{LOAN_MONEY:LOAN_MONEY,PAY_ITEM_PARENT_PKID : PAY_ITEM_PARENT_PKID, STUDENT_PKID:STUDENT_PKID},function(result){
				if(result.result == 'success'){
					layer.msg("登记成功！");
					var studentPkid = $("#STUDENT_PKID_FLOW").val();;
					yingxin.banlilvsetongdaoFlowContent(studentPkid);
					//统计
					yingxin.getStatisticsInfo();
					$("#cancelDengjiButton").click();
					return false;
				}else{
					layer.msg("登记失败，请重试！");
					return false;
				}
				 
			});
		}else{
			layer.msg("金额必须是大于0且小于100000的数！");
			return false;
		}
		
	
	});
	//获取list表格数据
	yingxin.getTab=function (STUDENT_PKID,STATUS) {
		
		// 引例四
		var url = _basepath+"payment/offlinePayTable.json";
		$('#offlinePayTable').bootstrapTable(
				{
					method: 'post',
		            url : url,
		            contentType:"application/x-www-form-urlencoded; charset=UTF-8",
					dataType:"json",				
					dataField : "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
					totalField : 'total',
					sortOrder : 'desc',
					striped : false, //表格显示条纹  
					/*pagination : true, //启动分页  
					pageNumber : 1, //当前第几页  
					pageSize : 20, //每页显示的记录数  
					pageList : [1,5,10,20,30,50,100], //记录数可选列表  */
					search : false, //是否启用查询  
					formatSearch : function() {
						return '请输入简拼.编码.企业名称查询';
					},//搜索框提示文字
					searchAlign : 'left',
					buttonsAlign : 'left',
					toolbarAlign : 'left',
					searchOnEnterKey : false,//true为按回车触发搜索事件
					showColumns : false, //显示下拉框勾选要显示的列  
					showRefresh : false, //显示刷新按钮  --只是前台刷新，个人觉得没什么用
					minimumCountColumns : 2, //最少允许的列数
					sidePagination : "server", //表示服务端请求  
					totalRows : 0, // server side need to set
					singleSelect : false,
					clickToSelect : true,
					onColumnSwitch: function(field,checked){
						
					},
					onLoadSuccess:function(data){
						res_data=data;
					},
					onDblClickRow:function(row){
					},
					onCheckSome:function(rows){
						console.log(rows);
					},
					queryParams : function getParams(params) {
						
						var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
								studentPkid : STUDENT_PKID,
								status : STATUS
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
									field : 'PKID',// 可不加
									align : "center",
									halign : 'center'
								},
					           {
					        	   field : 'SCHOOL_YEAR_NAME',
					        	   title:'学年',
					        	   align : "center",
					        	   halign : 'center'
					           },
					           {
					        	   field : 'PAY_STYLE_NAME',
					        	   align : "center",
					        	   halign : 'center',
					        	   title : '缴费类型'
					        		   
					           },
					           {
					        	   field : 'PAYITEM_CHILD',
					        	   align : "center",
					        	   halign : 'center',
					        	   title : '子项目'
					        		   
					           },
					           {
					        	   field : 'STATUSNAME',
					        	   align : "center",
					        	   halign : 'center',
					        	   title : '缴费状态'
					        		   
					           },
					           {
					        	   field : 'AMOUNTRECEIVABLE',
					        	   align : "center",
					        	   halign : 'center',
					        	   title : '应收金额'
					        		   
					           },
					           {
					        	   field : 'LOAN_MONEY',
					        	   align : "center",
					        	   halign : 'center',
					        	   title : '贷款/缓交'
					        		   
					           },
					           {
					        	   field : 'AMOUNTCOLLECTION',
					        	   align : "center",
					        	   halign : 'center',
					        	   title : '实收金额'
					        		   
					           },
					           {
									field : 'QIANFEI',
									title:'欠费金额',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										return (row.AMOUNTRECEIVABLE-row.AMOUNTCOLLECTION).toFixed(2);
										
									}
								},
								{
									field : 'opt',
									title : '操作',
									align : "center",
									halign : 'center',
									formatter : function(value, row,
											index) {
										
										var changeChildPayItemHtml = "";
										if(row.STATUS!=3&&row.AMOUNTCOLLECTION <= 0 && row.PAY_ITEM_PARENT_PKID != row.PAY_ITEM_PKID){//表示名单没关闭										
											//变更子项按钮											
											changeChildPayItemHtml='<span class="fa fa-random"  title="变更子项" onclick="javascript:changeChildPayItem(\''+row.PAY_ITEM_PKID+'\',\''+row.PAY_ITEM_PARENT_PKID+'\',\''+row.IS_INCLUDE_CHILD+'\',\''+row.PKID+'\');"></span>&nbsp;&nbsp;';
										}
										var jiaofeiHtml = "";
										if(row.STATUS!=3){//表示审核通过										
											jiaofeiHtml = '<span class="fa fa-yen"  title="缴费" onclick="javascript:jiaotui(\''+row.PAY_ITEM_PKID+'\',1,\''+row.STATUS+'\',\''+row.AMOUNTRECEIVABLE+'\',\''+row.AMOUNTCOLLECTION+'\');">';											 
										}
										
										return [changeChildPayItemHtml+jiaofeiHtml].join('');
									}
								}
					           
					           ],
				});
		$('#offlinePayTable').bootstrapTable('hideColumn', 'PKID');//隱藏列
		$(".fixed-table-container").attr('style','border:0px');//去掉多余边框
	};

	
	//刷新领取物品table
	yingxin.refreshlingquwupinTable = function(STUDENT_PKID){
		var url = _basepath+"goodsGet/goodsGetTable.json";
		 var opt = {
				url :url,
			    silent: true,
			    query:{
			    	"STUDENT_PKID":STUDENT_PKID
			    }
			  };
		 $("#lingquwupin").bootstrapTable('refresh', opt);
	};
	//领取物品
	yingxin.lingquwupinFlowContent = function(STUDENT_PKID){
		if(STUDENT_PKID=="" || typeof(STUDENT_PKID)=="undefined"){
			$('#lingquwupin').bootstrapTable({
				columns : [{field : 'PKID',checkbox : true},
				           {field : 'SCHOOL_YEAR_NAME',title : '学年',align : "center",halign : 'center',width : '16%'},
				           {field : 'PAY_STYLE_NAME',title : '缴费类型',align : "center",halign : 'center',width : '16%'},
				           {field : 'PAYITEM',title : '缴费项目',align : "center",halign : 'center',width : '16%'},
				           {field : 'PAYITEM_CHILD',title : '子项目',align : "center",halign : 'center',width : '16%'},
				           {field : 'OPT_STATE',title : '领取状态',align : "center",halign : 'center',width : '16%'},
				           {field : 'opt',title : '操作',align : "center",halign : 'center',width : '16%'}]
			});
		}else{
			$("#lingquwupin").bootstrapTable('destroy'); 
			var url = _basepath+"goodsGet/goodsGetTable.json";
			$('#lingquwupin').bootstrapTable({
				url : url,//数据源
				dataField : "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
				totalField : 'total',
				sortOrder : 'desc',
				striped : false, //表格显示条纹  
				pagination : false, //启动分页  
				pageNumber : 1, //当前第几页  
				pageSize : 20, //每页显示的记录数  
				pageList : [1,5,10,20,30,50,100], //记录数可选列表  
				search : false, //是否启用查询  
				formatSearch : function() {
					return '查询';
				},//搜索框提示文字
				searchAlign : 'left',
				buttonsAlign : 'left',
				toolbarAlign : 'left',
				searchOnEnterKey : false,//true为按回车触发搜索事件
				showColumns : false, //显示下拉框勾选要显示的列  
				showRefresh : false, //显示刷新按钮  --只是前台刷新，个人觉得没什么用
				minimumCountColumns : 2, //最少允许的列数
				sidePagination : "server", //表示服务端请求  
				totalRows : 0, // server side need to set
				singleSelect : false,
				clickToSelect : true,
//				onPostBody : function(data){
//					if(data.length>0){
//						$('#lingqubtn').attr('style','display:block');
//					}
//				},
				onColumnSwitch: function(field,checked){
					//获取全部可显示的节点信息集合
					var resultColumnList = $('#lingquwupin').bootstrapTable('getVisibleColumns');
					var resultColumns = "";		
					//将所有field组合成以逗号隔开的字符串
					$.each(resultColumnList, function(i, column) {
						if(column.field=='0'/*||column.field=='opt'*/){
							return;
						}else{
							resultColumns+=column.field+",";
						}									
			     	});
					
				},
				queryParams : function getParams(params) {	
					var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
						STUDENT_PKID:STUDENT_PKID
					};
					return temp;
				},
				buttonsAlign : "right",//按钮对齐方式
				selectItemName : 'id',
				columns : [
						{
							title : "全选",//标题
							align : "center",//水平
							checkbox : true,
							valign : "middle",//垂直
							formatter:function(value, row, index){
								   // 根据row.列名   那状态确定返回 true/false
								if(row.OPT_STATE=='已领取'){
									  return {
											 disabled : true
									  };
								}
							 }
						},
						{
							field : 'PKID',
							visible: false
						},
						{
							field : 'STUDENT_PKID',
							visible: false
						},
						{
							field : 'PAY_ITEM_PKID',
							visible: false
						},
						{
							field : 'SCHOOL_YEAR_NAME',
							title : '学年',
							align : "center",
							halign : 'center',
							sortable : false,
							width : '16%'
						},
						{
							field : 'PAY_STYLE_NAME',
							title : '缴费类型',
							align : "center",
							halign : 'center',
							sortable : false,
							width : '16%'
						},
						{
							field : 'PAYITEM',
							title : '缴费项目',
							align : "center",
							halign : 'center',
							sortable : false,
							width : '16%'
						},
						{
							field : 'PAYITEM_CHILD',
							title : '子项目',
							align : "center",
							halign : 'center',
							sortable : false,
							width : '16%'
						},
						{
							field : 'OPT_STATE',
							title : '领取状态',
							align : "center",
							halign : 'center',
							sortable : false,
							width : '16%'
						},{
							field : 'opt',
							title : '操作',
							align : "center",
							halign : 'center',
							sortable : false,
							width : '16%',
							formatter : function(value,row,index){
								if(row.OPT_STATE == '未领取'){
									return [
									    '<a href="javascript:void(0);" class="lingqu" style="color:green">领取</a>'
									].join('');
								}else{
									return [
									    '<a href="javascript:void(0);" class="tuiling" style="color:green">退领</a>'
									].join('');
								}
							},
							events : {
								'click .lingqu' : function(e,value, row, index) {
									var studentPkid = $("#STUDENT_PKID_FLOW").val();
									var flowNode = $("#flowNode").val();
									var flowNodeN = yingxin.getFlowStatusN(studentPkid,flowNode);
									if(flowNodeN != ''){
										layer.msg("该学生尚未办理【"+flowNodeN+"】");
										return false;
									}
									var url=_basepath+"goodsGet/edit.json";
									$.post(url,{PKID:row.PKID,PAY_ITEM_PKID:row.PAY_ITEM_PKID,STUDENT_PKID:STUDENT_PKID},function(result){
										if(result.result == 'success'){
											layer.msg("领取成功！");
											yingxin.refreshlingquwupinTable(STUDENT_PKID);
											//统计
											yingxin.getStatisticsInfo();
										}else{
											layer.msg("领取失败，请重试！");
										}  
									});
								},
								'click .tuiling' : function(e,value, row, index) {
									var url=_basepath+"goodsGet/edit.json";
									$.post(url,{PKID:row.PKID,OPT_STATE:row.OPT_STATE},function(result){
										if(result.result == 'success'){
											layer.msg("退领成功！");
											yingxin.refreshlingquwupinTable(STUDENT_PKID);
										}else{
											layer.msg("退领失败，请重试！");
										}  
									});
								},
							}
						}]
				});
		}
		$(".fixed-table-container").attr('style','border:0px');//去掉多余边框
	};
	
	//批量领取物品
	$("#lingqubtn").click(function(){
		var studentPkid = $("#STUDENT_PKID_FLOW").val();
		var flowNode = $("#flowNode").val();
		var flowNodeN = yingxin.getFlowStatusN(studentPkid,flowNode);
		if(flowNodeN != ''){
			layer.msg("该学生尚未办理【"+flowNodeN+"】");
			return false;
		}
		var list = $("#lingquwupin").bootstrapTable("getSelections");
		if(list.length>0){
			var url=_basepath+"goodsGet/batchReceive.json";
			$.post(url,{list:JSON.stringify(list)},function(result){
				if(result.result == 'success'){
					layer.msg("领取成功！");
					yingxin.refreshlingquwupinTable(studentPkid);
					//统计
					yingxin.getStatisticsInfo();
				}else{
					layer.msg("领取失败，请重试！");
				}  
			});
		}else{
			layer.msg("请至少选择一条可领取物品信息！");
		}
	});
	
	//刷新入住table
	yingxin.ruzhuTable = function(STUDENT_PKID){
		var url = _basepath+"ruZhu/ruZhuTable.json";
		 var opt = {
				url :url,
			    silent: true,
			    query:{
			    	"STUDENT_PKID":STUDENT_PKID
			    }
			  };
		 $("#ruzhuTable").bootstrapTable('refresh', opt);
	};
	//入住
	yingxin.ruzhuFlowContent = function(STUDENT_PKID){
		if(STUDENT_PKID=="" || typeof(STUDENT_PKID)=="undefined"){
			$('#ruzhuTable').bootstrapTable({
				columns : [{field : 'ROOM_NAME',title : '宿舍',align : "center",halign : 'center',width : '50%'},
				           {field : 'RUZHU',title : '是否办理入住',align : "center",halign : 'center',width : '50%'}]
			});
		}else{
			$("#ruzhuTable").bootstrapTable('destroy'); 
			var url =  _basepath+"ruZhu/ruZhuTable.json";
			$('#ruzhuTable').bootstrapTable({
				url : url,//数据源
				dataField : "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
				totalField : 'total',
				sortOrder : 'desc',
				striped : false, //表格显示条纹  
				pagination : false, //启动分页  
				pageNumber : 1, //当前第几页  
				pageSize : 20, //每页显示的记录数  
				pageList : [1,5,10,20,30,50,100], //记录数可选列表  
				search : false, //是否启用查询  
				formatSearch : function() {
					return '查询';
				},//搜索框提示文字
				searchAlign : 'left',
				buttonsAlign : 'left',
				toolbarAlign : 'left',
				searchOnEnterKey : false,//true为按回车触发搜索事件
				showColumns : false, //显示下拉框勾选要显示的列  
				showRefresh : false, //显示刷新按钮  --只是前台刷新，个人觉得没什么用
				minimumCountColumns : 2, //最少允许的列数
				sidePagination : "server", //表示服务端请求  
				totalRows : 0, // server side need to set
				singleSelect : false,
				clickToSelect : true,
				queryParams : function getParams(params) {	
					var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
						STUDENT_PKID:STUDENT_PKID
					};
					return temp;
				},
				buttonsAlign : "right",//按钮对齐方式
				selectItemName : 'id',
				columns : [{
							field : 'ROOM_NAME',
							title : '宿舍',
							align : "center",
							halign : 'center',
							sortable : false,
							width : '50%'
						},
						{
							field : 'RUZHU',
							title : '是否办理入住',
							align : "center",
							halign : 'center',
							sortable : false,
							width : '50%',
							formatter : function(value, row,
									index){
								if(value=="未办理"){	
									$('#ruzhubtn').attr('style','backgroud:red;border-color:red;');
									$('#ruzhubtn').attr('disabled',false);
									$('#fensubtn').attr('style','background:#888888;border-color:#888888');
									$('#fensubtn').attr('disabled',true);
								}else{
									$('#fensubtn').attr('style','background:#888888;border-color:#888888');
									$('#fensubtn').attr('disabled',true);
								}
								return value;
							}	
						}]
				});
		}
		$(".fixed-table-container").attr('style','border:0px');//去掉多余边框
	};
	//入住
	$("#ruzhubtn").click(function(){
		var studentPkid = $("#STUDENT_PKID_FLOW").val();
		var flowNode = $("#flowNode").val();
		var flowNodeN = yingxin.getFlowStatusN(studentPkid,flowNode);
		if(flowNodeN != ''){
			layer.msg("该学生尚未办理【"+flowNodeN+"】");
			return false;
		}
		var url=_basepath+"ruZhu/edit.json";
		var pkid = $("#STUDENT_PKID_FLOW").val();
		$.post(url,{STUDENT_PKID:pkid},function(result){
			if(result.result == 'success'){
				yingxin.ruzhuTable(pkid);
				//统计
				yingxin.getStatisticsInfo();
				$("#ruzhubtn").attr("disabled",true);
				$("#fensubtn").attr("disabled",true);
				layer.msg("办理成功！");
			}else{
				layer.msg("办理失败，请重试！");
			}  
		});
		
	});
	
	//分宿
	$("#fensubtn").click(function(){
		var studentPkid = $("#STUDENT_PKID_FLOW").val();
		var flowNodeN = yingxin.getFlowStatusN(studentPkid,"4");
		if(flowNodeN != ''){
			layer.msg("该学生尚未办理【"+flowNodeN+"】");
			return false;
		}
		var url = "ruZhu/getStuInfo.json";
		$.post(url,{STUDENT_PKID:studentPkid},function(result){
			if(result.result == 'success'){
				var pd = result.pd;
				var SD_SEX = pd.SD_SEX;
				var T_DORM_TYPE_PKID = pd.T_DORM_TYPE_PKID;
				BootstrapDialog.show({
					title:'分配',
					message: $('<div></div>').load("dormitoryInfo/godiviDorm.json?SD_SEX="+SD_SEX+"&&T_DORM_TYPE_PKID="+T_DORM_TYPE_PKID),
					closable: true,//是否显示叉号
					draggable: true,//可以拖拽
					buttons: [{
			            label: '确定',
			            cssClass: 'btn-danger',
			            action: function(dialog) {
			            	var dormtree = $("#orgtree3").val();
			            	if(dormtree=="" || dormtree==null){
			            		layer.msg('请选择宿舍', {time:800});
			            		return false;
			            	}
			            	var T_DORM_TYPE_PKIDs = dormtree;
			            	var dorm_length = dormtree.split(',').length-1;
			            	var stu_lenght = 1;
			            	if(dorm_length>stu_lenght){//宿舍数大于学生数
			            		BootstrapDialog.show({
			            			title:'提示',
			            			message: '选择的学生数'+stu_lenght+'，床位数'+dorm_length+'，学生资源不足，是否继续',
				        			closable: true,//是否显示叉号
				        			draggable: true,//可以拖拽
				        			buttons: [{
				        	            label: '确定',
				        	            cssClass: 'btn-danger',
				        	            action: function(dialog2) {
				        	            	$.ajax({
				        	            		type:'post',
				        	            		dataType:'json',
				        	            		data:{"dormtree":dormtree,"stu_lenght":stu_lenght,"dorm_length":dorm_length},
				        	            		url: _basepath+'dormitoryInfo/diviDorm.json?pkids='+studentPkid+'&T_DORM_TYPE_PKIDs='+T_DORM_TYPE_PKIDs,
				        	            		success:function(data){
				        	            			if(data.result=='success'){
				        	            				layer.msg("分配成功");
				        	            				yingxin.ruzhuTable(studentPkid);
				        	            				dialog2.close();
				        	            				dialog.close();
				        	            			}
				        	            		},
				        	            		error:function(a,error,c){
//				        	            			alert(error);
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
			            	}else if(dorm_length<stu_lenght){//学生数大于宿舍数
			            		BootstrapDialog.show({
			            			title:'提示',
			            			message: '选择的学生数'+stu_lenght+'人，床位数'+dorm_length+'人，宿舍资源不足，是否继续',
				        			closable: true,//是否显示叉号
				        			draggable: true,//可以拖拽
				        			buttons: [{
				        	            label: '确定',
				        	            cssClass: 'btn-danger',
				        	            action: function(dialog2) {
				        	            	$.ajax({
				        	            		type:'post',
				        	            		dataType:'json',
				        	            		data:{"dormtree":dormtree,"stu_lenght":stu_lenght,"dorm_length":dorm_length},
				        	            		url: _basepath+'dormitoryInfo/diviDorm.json?pkids='+studentPkid+'&T_DORM_TYPE_PKIDs='+T_DORM_TYPE_PKIDs,
				        	            		success:function(data){
				        	            			if(data.result=='success'){
				        	            				layer.msg("分配成功");
				        	            				yingxin.ruzhuTable(studentPkid);
				        	            				dialog2.close();
				        	            				dialog.close();
				        	            			}
				        	            		},
				        	            		error:function(a,error,c){
//				        	            			alert(error);
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
				        	        }]
				        		});
			            	}else{
			            		$.ajax({
		    	            		type:'post',
		    	            		dataType:'json',
		    	            		data:{"dormtree":dormtree,"stu_lenght":stu_lenght,"dorm_length":dorm_length},
		    	            		url: _basepath+'dormitoryInfo/diviDorm.json?pkids='+studentPkid+'&T_DORM_TYPE_PKIDs='+T_DORM_TYPE_PKIDs,
		    	            		success:function(data){
		    	            			if(data.result=='success'){
		    	            				layer.msg("分配成功");
		    	            				yingxin.ruzhuTable(studentPkid);
		    	            				dialog.close();
		    	            			}
		    	            		},
		    	            		error:function(a,error,c){
		    	            			//alert(error);
		    	            		}
		    	            	});
			            	}
			            }
			        }]
				});
			}else if(result.result == 'noStu'){
				layer.msg("没有学生信息");
				return false;
			}else if(result.result == 'noJf'){
				layer.msg("未缴纳带子项住宿费");
				return false;
			}
		});
	});
	
	
	//刷新缴费列表
	yingxin.refreshofflinePayTable = function(STUDENT_PKID,STATUS){
		var url = _basepath+"payment/offlinePayTable.json";
		 var opt = {
				url :url,
			    silent: true,
			    query:{
			    	"studentPkid" : STUDENT_PKID,
					"status" : STATUS
			    }
			  };
		 $("#offlinePayTable").bootstrapTable('refresh', opt);
	};
	//选择一个学生查询
	yingxin.doSearchForOneStudent = function(studentPkid){
		if(studentPkid == null || studentPkid ==''){
			return false;
		}

		var flag = yingxin.getStudentInfo(studentPkid);
		if(flag == true){
			yingxin.getFlowStatus(studentPkid);
			yingxin.getStatisticsInfo(studentPkid);				
			yingxin.paymentreport(studentPkid);//报到					
			var STATUS = $("#statusSelected").find(".active").attr("text");//tap页的状态
			yingxin.refreshofflinePayTable(studentPkid,STATUS);//缴费
			var flowNode = $("#flowNode").val();
			//if(flowNode == "4"){
				//yingxin.lingquwupinFlowContent(studentPkid);
			//}
			//if(flowNode == "5"){
				//yingxin.ruzhuFlowContent(studentPkid);
			//}
			
			//关闭弹框
			$("#btn_query_cancel").click();
			
			$("#STUDENT_PKID_FLOW").val(studentPkid);
			
			//刷新流程内容
			
			yingxin.userPkid = $("#userid").val();
			/*yingxin.studentPkid = result.stuInfo.PKID;
			yingxin.studentName = result.stuInfo.XINGMING;*/
			var flowNodeN='';
			if(/*flowNode=="1" ||*/ flowNode=="2" || flowNode=="3" || flowNode=="4" || flowNode=="5"){
				flowNodeN = yingxin.getFlowStatusN(studentPkid,"2");
			}else if(flowNode=="6"){
				flowNodeN = yingxin.getFlowStatusN(studentPkid,"6");
			}
			
			if(flowNodeN != ''){
				layer.msg("该学生尚未办理【"+flowNodeN+"】");
				return false;
			}
			//$("#flowNode").val("1");
			yingxin.setFlowTitle();
		}else{
			layer.msg("未查询到该学生！");
//			$("#checkReset").click();
			return false;
		}
	};
	
	
	//一卡通发卡
	yingxin.ecardOpenCardFlowContent = function(STUDENT_PKID) {
		var baodaohtmls = '<div class="pa-row">'
				+ '<table class="table table-striped table-hover table-bordered jf_table" style="text-align: center;">'
				+ '<tr>' + '<th>项目</th>' + '<th>领取状态</th>' + '</tr>';
		$.ajax({
			type : 'POST',
			url : _basepath + "yingxin/getbaodaoFlowContent.json",
			data : {
				STUDENT_PKID : STUDENT_PKID
			},
			dataType : "json",
			success : function(result) {
				if (result.result == "success") {
					var resultList = result.resultList;
					var sfbd = "";
					if (resultList != null && resultList != '') {
						for (var l = 0; l < resultList.length; l++) {
							sfbd = resultList[l].IS_OPENECARD;
							if(sfbd=='Y'){
								lingqu="已领取";
							}else{
								lingqu="未领取";
							}
							baodaohtmls += '<tr>' + '<td>一卡通发卡</td>' + '<td>'
									+ lingqu + '</td>'
									+ '</tr>';
						}
						baodaohtmls += '</table>' + '</div>';

						$("#ecardOpenCardFlowContent").html(baodaohtmls);
						if (sfbd == "Y") {
							$("#opencardbtn").attr('disabled', true);
						} else {
							$("#opencardbtn").attr('disabled', false);
						}
					}

					

				}
			}
		});
	};
	
	//分寝
	yingxin.fenqinFlowContent = function(STUDENT_PKID) {
		var fenqinhtmls = '<div class="pa-row">'
				+ '<table class="table table-striped table-hover table-bordered jf_table" style="text-align: center;">'
				+ '<tr>'+ '<th>选择</th>' + '<th>宿舍类型</th>' + '<th>宿舍</th>' +'<th>操作</th>' + '</tr>';
		$.ajax({
			type : 'POST',
			url : _basepath + "yingxin/fenqinFlowContent.json",
			data : {
				STUDENT_PKID : STUDENT_PKID
			},
			dataType : "json",
			success : function(result) {
				if (result.result == "success") {
					var resultList = result.resultList;
					var sfbd = "";
					var sslx = "";
					var ss = "";
					var int="0";
					var PAY_ITEM_PKID = "";
					var PAY_ITEM_PARENT_PKID = "";
					var IS_INCLUDE_CHILD = "";
					var PKID = "";
					var AMOUNTCOLLECTION = "";
					if (resultList != null && resultList != '') {
						for (var l = 0; l < resultList.length; l++) {
							sslx = resultList[l].DT_NAME;
							ss = resultList[l].SUSHE;
							itemlistpkid = resultList[l].PKID;
							
							AMOUNTCOLLECTION= resultList[l].AMOUNTCOLLECTION;
							PAY_ITEM_PKID = resultList[l].PAY_ITEM_PKID;
							PAY_ITEM_PARENT_PKID = resultList[l].PAY_ITEM_PARENT_PKID;
							IS_INCLUDE_CHILD = resultList[l].IS_INCLUDE_CHILD;
							PKID = resultList[l].PKID;
							if(PAY_ITEM_PKID=='null'||PAY_ITEM_PKID==null){
								PAY_ITEM_PKID="";
							}
							if(PAY_ITEM_PARENT_PKID=='null'||PAY_ITEM_PARENT_PKID==null){
								PAY_ITEM_PARENT_PKID="";
							}
							if(IS_INCLUDE_CHILD=='null'||IS_INCLUDE_CHILD==null){
								IS_INCLUDE_CHILD="";
							}
							if(sslx=='null'||sslx==null){
								sslx="";
							}
							if(ss=='null'||ss==null){
								ss="";
							}else{
								int="1";
							}
							/*<input type="radio"  id="pass" />   [{SUSHE=null, DT_NAME=4人间}]*/
							fenqinhtmls += '<tr>' + 
											'<td><input type="radio" name="radio1"  value='+ itemlistpkid +' /></td>'+ 
											'<td>'+sslx+'</td>' + 
											'<td>'+ ss + '</td>'+ 
											/*'<td>'+'更换宿舍标准'+'</td>'*/
											'<td style="vertical-align: middle; cursor: pointer;">';
											if(int =='0' && AMOUNTCOLLECTION <= 0){
												fenqinhtmls +='<span title="更换宿舍标准"  onclick="javascript:yingxin.changebz(\''+PAY_ITEM_PKID+'\',\''+PAY_ITEM_PARENT_PKID+'\',\''+IS_INCLUDE_CHILD+'\',\''+PKID+'\');">更换宿舍标准</span>'+'</td></tr>';
											}else{
												fenqinhtmls +='<span title="更换宿舍标准" style="color:gray;" >更换宿舍标准</span>'+'</td></tr>';
											}
											
						}
						/*fenqinhtmls += '</table>' + '</div>';
						$("#fenqinFlowContent").html(fenqinhtmls);*/
						if (int !="0") {
							$("#fenqinbtn").attr('disabled', true);
						} else {
							$("#fenqinbtn").attr('disabled', false);
						}
					}else{
						fenqinhtmls += '<tr class="no-records-found"><td colspan="4">没有找到匹配的记录</td></tr>';
					  }
				}else{
					fenqinhtmls += '<tr class="no-records-found"><td colspan="4">没有找到匹配的记录</td></tr>';
				  }
				fenqinhtmls += '</table>'+'</div>';
				$("#fenqinFlowContent").html(fenqinhtmls);
			}
		});
		
		
	};
	
	
	//点击变更子项按钮
	yingxin.changebz= function(PAY_ITEM_PKID,PAY_ITEM_PARENT_PKID,IS_INCLUDE_CHILD,PAY_ITEM_LIST_PKID){
		var studentPkid = $("#STUDENT_PKID_FLOW").val();
		var flowNodeN = yingxin.getFlowStatusN(studentPkid,"3");
		/*if(flowNodeN != ''){
			layer.msg("该学生尚未办理【"+flowNodeN+"】");
			return false;
		}*/
		$("#payitem_child_change").html("");
		$("#cost_change_html").html('<input type="text" name="" value="" readonly="readonly" class="form-control" placeholder="金额" id="cost_change"  onkeyup="clearNoNum(this)"/>');
		
		$.ajax({
			  type: 'POST',
			  url: _basepath+"receipt/getAllChildPayItemsByParentPkid.json",
			  async: false,
			  data: {
				  PARENT_ID: PAY_ITEM_PARENT_PKID
			  },
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  var payitem_pkid;
					  var ITEMLIST_CREATEMODE;
					  var flag = 0;
					  var payItemListAllChild = result.payItemListAllChild;
					  for (var i = 0; i < payItemListAllChild.length; i++) {
						  var payItem = payItemListAllChild[i];
						  
						  if(payItem.PKID != PAY_ITEM_PKID){
							  if(flag == 0){
								  payitem_pkid = payItem.PKID;
								  ITEMLIST_CREATEMODE = payItem.ITEMLIST_CREATEMODE;
								  flag++;
							  }
							  
							  $("#payitem_child_change").append("<option value='"+payItem.PKID+"' ITEMLIST_CREATEMODE = '"+payItem.ITEMLIST_CREATEMODE+"' parentId='"+payItem.PARENT_ID+"' sourcePayItemListPkid='"+PAY_ITEM_LIST_PKID+"' PAY_ITEM_PKID_YET='"+PAY_ITEM_PKID+"'>"+payItem.PAYITEM+"</option>");
						  }
					  }
					  
					  putCostHtml(payitem_pkid,ITEMLIST_CREATEMODE,$("#cost_change_html"),"cost_change",'readonly="readonly"');
					  
					  $('#mymoda8').modal({
							keyboard: true
						});
				  }
				  return false;
			  }
			});
};
	
	
	
	
	//分宿
	$("#fenqinbtn").click(function(){
		var studentPkid = $("#STUDENT_PKID_FLOW").val();
		var item_list_pkid = $("input[name='radio1']:checked").val();
		if(item_list_pkid=='undifind' || item_list_pkid=='null' || item_list_pkid==null){
			layer.msg("请选择要分寝的宿舍！");
			return false;
		}
		
		/*var flowNodeN = yingxin.getFlowStatusN(studentPkid,"4");
		 * 
		{T_DORM_TYPE_PKID=0DD03976F8EE4A448D759F02037B3608, XINGMING=凤帜晗, 
		DORM_PKID=null, SD_SEX=0, PKID=7ebf491cc93f462ea2461041faefd1e7, NIANJI=20级, 
		DEPARTMENT_PKID=6, ITEMPKID=b8317290be3647838031e2b0f941d328, XUEHAO=20886029, SHENFENZHENGHAO=530323198206115817}
		if(flowNodeN != ''){
			layer.msg("该学生尚未办理【"+flowNodeN+"】");  
			return false;
		}*/
		var url = "ruZhu/getfenqinStuInfo.json";
		$.post(url,{STUDENT_PKID:studentPkid,ITEMPKID:item_list_pkid},function(result){
			if(result.result == 'success'){
				var pd = result.pd;
				var SD_SEX = pd.SD_SEX;
				var T_DORM_TYPE_PKID = pd.T_DORM_TYPE_PKID;
				BootstrapDialog.show({
					title:'分配',
					message: $('<div></div>').load("dormitoryInfo/godiviDorm.json?SD_SEX="+SD_SEX+"&&T_DORM_TYPE_PKID="+T_DORM_TYPE_PKID),
					closable: true,//是否显示叉号
					draggable: true,//可以拖拽
					buttons: [{
			            label: '确定',
			            cssClass: 'btn-danger',
			            action: function(dialog) {
			            	var dormtree = $("#orgtree3").val();
			            	if(dormtree=="" || dormtree==null){
			            		layer.msg('请选择宿舍', {time:800});
			            		return false;
			            	}
			            	var T_DORM_TYPE_PKIDs = dormtree;
			            	var dorm_length = dormtree.split(',').length-1;
			            	var stu_lenght = 1;
			            	if(dorm_length>stu_lenght){//宿舍数大于学生数
			            		BootstrapDialog.show({
			            			title:'提示',
			            			message: '选择的学生数'+stu_lenght+'，床位数'+dorm_length+'，学生资源不足，是否继续',
				        			closable: true,//是否显示叉号
				        			draggable: true,//可以拖拽
				        			buttons: [{
				        	            label: '确定',
				        	            cssClass: 'btn-danger',
				        	            action: function(dialog2) {
				        	            	$.ajax({
				        	            		type:'post',
				        	            		dataType:'json',
				        	            		data:{"dormtree":dormtree,"stu_lenght":stu_lenght,"dorm_length":dorm_length,"pkids":studentPkid,"T_DORM_TYPE_PKIDs":T_DORM_TYPE_PKIDs},
				        	            		url: _basepath+'dormitoryInfo/diviDorm.json',
				        	            		success:function(data){
				        	            			if(data.result=='success'){
				        	            				layer.msg("分配成功");
				        	            				yingxin.fenqinFlowContent(studentPkid);
				        	            				//yingxin.ruzhuTable(studentPkid);
				        	            				dialog2.close();
				        	            				dialog.close();
				        	            			}
				        	            		},
				        	            		error:function(a,error,c){
//				        	            			alert(error);
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
			            	}else if(dorm_length<stu_lenght){//学生数大于宿舍数
			            		BootstrapDialog.show({
			            			title:'提示',
			            			message: '选择的学生数'+stu_lenght+'人，床位数'+dorm_length+'人，宿舍资源不足，是否继续',
				        			closable: true,//是否显示叉号
				        			draggable: true,//可以拖拽
				        			buttons: [{
				        	            label: '确定',
				        	            cssClass: 'btn-danger',
				        	            action: function(dialog2) {
				        	            	$.ajax({
				        	            		type:'post',
				        	            		dataType:'json',
				        	            		data:{"dormtree":dormtree,"stu_lenght":stu_lenght,"dorm_length":dorm_length},
				        	            		url: _basepath+'dormitoryInfo/diviDorm.json?pkids='+studentPkid+'&T_DORM_TYPE_PKIDs='+T_DORM_TYPE_PKIDs,
				        	            		success:function(data){
				        	            			if(data.result=='success'){
				        	            				layer.msg("分配成功");
				        	            				yingxin.ruzhuTable(studentPkid);
				        	            				dialog2.close();
				        	            				dialog.close();
				        	            			}
				        	            		},
				        	            		error:function(a,error,c){
//				        	            			alert(error);
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
				        	        }]
				        		});
			            	}else{
			            		$.ajax({
		    	            		type:'post',
		    	            		dataType:'json',
		    	            		data:{"dormtree":dormtree,"stu_lenght":stu_lenght,"dorm_length":dorm_length},
		    	            		url: _basepath+'dormitoryInfo/diviDorm.json?pkids='+studentPkid+'&T_DORM_TYPE_PKIDs='+T_DORM_TYPE_PKIDs,
		    	            		success:function(data){
		    	            			if(data.result=='success'){
		    	            				layer.msg("分配成功");
		    	            				//yingxin.ruzhuTable(studentPkid);
		    	            				yingxin.fenqinFlowContent(studentPkid);
		    	            				dialog.close();
		    	            			}
		    	            		},
		    	            		error:function(a,error,c){
		    	            			//alert(error);
		    	            		}
		    	            	});
			            	}
			            }
			        }]
				});
			}else if(result.result == 'noStu'){
				layer.msg("没有学生信息");
				return false;
			}else if(result.result == 'noJf'){
				layer.msg("未缴纳带子项住宿费");
				return false;
			}
		});
	});
	
	
	
	
	
	//设置流程标题
	yingxin.setFlowTitle = function(){
		var flowNode = $("#flowNode").val();
		var studentPkid = $("#STUDENT_PKID_FLOW").val();;
		if(flowNode == "1"){//报到
			$("#flowTitle").html("报到");
			$("#baodaoFlowContent").css("display","block");
			$("#banlilvsetongdaoFlowContent").css("display","none");
			$("#jiaofeiFlowContent").css("display","none");
			$("#lingquwupinFlowContent").css("display","none");
			$("#ruzhuFlowContent").css("display","none");
			//为了回到缴费页面时tap页显示在欠费状态
			$("#ecardOpenCardFlowContent").css("display","none");
			$("#fenqinFlowContent").css("display","none");
			$("#statusSelected").find(".active").removeClass('active');
			$("#baodaoa").show();
			$("#qianfeiTap").addClass('active');
			$("#opencarddiv").hide();
			$("#fenqindiv").hide();
		}else if(flowNode == "2"){//办理绿色通道
			$("#flowTitle").html("一卡通发卡");
			$("#yanzhengFlowContent").css("display","none");
			$("#baodaoFlowContent").css("display","none");
			$("#ecardOpenCardFlowContent").css("display","block");
			$("#banlilvsetongdaoFlowContent").css("display","none");
			$("#jiaofeiFlowContent").css("display","none");
			$("#lingquwupinFlowContent").css("display","none");
			$("#ruzhuFlowContent").css("display","none");
			$("#ecardOpenCardFlowContent").css("display","block");
			$("#fenqinFlowContent").css("display","none");
			$("#opencardbtn").attr('disabled', true);
			yingxin.ecardOpenCardFlowContent(studentPkid);
			$("#baodaoa").hide();
			$("#opencarddiv").show();
			$("#fenqindiv").hide();
			
			$("#statusSelected").find(".active").removeClass('active');
			$("#qianfeiTap").addClass('active');
			/*$("#flowTitle").html("办理绿色通道");
			$("#baodaoFlowContent").css("display","none");
			$("#banlilvsetongdaoFlowContent").css("display","block");
			$("#jiaofeiFlowContent").css("display","none");
			$("#lingquwupinFlowContent").css("display","none");
			$("#ruzhuFlowContent").css("display","none");
			yingxin.banlilvsetongdaoFlowContent(studentPkid);
			$("#baodaoa").hide();
			$("#statusSelected").find(".active").removeClass('active');
			$("#qianfeiTap").addClass('active');*/
		}else if(flowNode == "3"){//分寢
			$("#flowTitle").html("分寢");
			$("#baodaoFlowContent").css("display","none");
			$("#banlilvsetongdaoFlowContent").css("display","none");
			$("#jiaofeiFlowContent").css("display","none");
			$("#lingquwupinFlowContent").css("display","none");
			$("#ruzhuFlowContent").css("display","none");
			$("#ecardOpenCardFlowContent").css("display","none");
			$("#fenqinFlowContent").css("display","block");
			//yingxin.getTab(studentPkid,0);
			yingxin.fenqinFlowContent(studentPkid);
			$("#baodaoa").hide();
			$("#opencarddiv").hide();
			$("#fenqindiv").show();
			/*$("#flowTitle").html("缴费");
			$("#baodaoFlowContent").css("display","none");
			$("#banlilvsetongdaoFlowContent").css("display","none");
			$("#jiaofeiFlowContent").css("display","block");
			$("#lingquwupinFlowContent").css("display","none");
			$("#ruzhuFlowContent").css("display","none");
			yingxin.getTab(studentPkid,0);
			$("#baodaoa").hide();*/
		}else if(flowNode == "4"){//办理绿色通道
			/*$("#flowTitle").html("领取物品");
			$("#baodaoFlowContent").css("display","none");
			$("#banlilvsetongdaoFlowContent").css("display","none");
			$("#jiaofeiFlowContent").css("display","none");
			$("#lingquwupinFlowContent").css("display","block");
			$("#ruzhuFlowContent").css("display","none");
			yingxin.lingquwupinFlowContent(studentPkid);
			$("#baodaoa").hide();
			$("#statusSelected").find(".active").removeClass('active');
			$("#qianfeiTap").addClass('active');*/
			$("#flowTitle").html("办理绿色通道");
			$("#baodaoFlowContent").css("display","none");
			$("#banlilvsetongdaoFlowContent").css("display","block");
			$("#jiaofeiFlowContent").css("display","none");
			$("#lingquwupinFlowContent").css("display","none");
			$("#ruzhuFlowContent").css("display","none");
			$("#ecardOpenCardFlowContent").css("display","none");
			$("#fenqinFlowContent").css("display","none");
			yingxin.banlilvsetongdaoFlowContent(studentPkid);
			$("#baodaoa").hide();
			$("#statusSelected").find(".active").removeClass('active');
			$("#qianfeiTap").addClass('active');
			$("#opencarddiv").hide();
			$("#fenqindiv").hide();
		}else if(flowNode == "5"){//入住
			/*$("#flowTitle").html("入住登记");
			$("#baodaoFlowContent").css("display","none");
			$("#banlilvsetongdaoFlowContent").css("display","none");
			$("#jiaofeiFlowContent").css("display","none");
			$("#lingquwupinFlowContent").css("display","none");
			$("#ruzhuFlowContent").css("display","block");
			yingxin.ruzhuFlowContent(studentPkid);
			$("#baodaoa").hide();
			$("#statusSelected").find(".active").removeClass('active');
			$("#qianfeiTap").addClass('active');*/
			$("#flowTitle").html("缴费");
			$("#baodaoFlowContent").css("display","none");
			$("#banlilvsetongdaoFlowContent").css("display","none");
			$("#jiaofeiFlowContent").css("display","block");
			$("#lingquwupinFlowContent").css("display","none");
			$("#ruzhuFlowContent").css("display","none");
			$("#ecardOpenCardFlowContent").css("display","none");
			$("#fenqinFlowContent").css("display","none");
			$("#fenqindiv").hide();
			yingxin.getTab(studentPkid,0);
			$("#baodaoa").hide();
			$("#opencarddiv").hide();
		}else if(flowNode == "6"){
			$("#flowTitle").html("入住登记");
			$("#baodaoFlowContent").css("display","none");
			$("#banlilvsetongdaoFlowContent").css("display","none");
			$("#jiaofeiFlowContent").css("display","none");
			$("#lingquwupinFlowContent").css("display","none");
			$("#ruzhuFlowContent").css("display","block");
			$("#ecardOpenCardFlowContent").css("display","none");
			$("#fenqinFlowContent").css("display","none");
			$("#fenqindiv").hide();
			yingxin.ruzhuFlowContent(studentPkid);
			$("#baodaoa").hide();
			$("#statusSelected").find(".active").removeClass('active');
			$("#qianfeiTap").addClass('active');
			$("#opencarddiv").hide();
		}
		
	};
	
	/*
	 * 流程节点根据权限是否可以点击
	 */
	yingxin.isCanClickFlowNode = function(flowNode){
		var result = false;
		//获得流程节点权限
		for (var i = 0; i < sessionMenuListJson.length; i++) {
			if(sessionMenuListJson[i].XTMC == '报到' && flowNode == '1'){
				result = true;
				break;
			}else if(sessionMenuListJson[i].XTMC == '一卡通发卡' && flowNode == '2'){
				result = true;
				break;
			}else if(sessionMenuListJson[i].XTMC == '分寝' && flowNode == '3'){
				result = true;
				break;
			}else if(sessionMenuListJson[i].XTMC == '办理绿色通道' && flowNode == '4'){
				result = true;
				break;
			}else if(sessionMenuListJson[i].XTMC == '缴费' && flowNode == '5'){
				result = true;
				break;
			}else if(sessionMenuListJson[i].XTMC == '领宿舍钥匙入住' && flowNode == '6'){
				result = true;
				break;
			}
			
		}
		return result;
	};
	//流程节点点击事件
	$('#baodaoFlow').click(function(){
		if(yingxin.isCanClickFlowNode("1") == false){
			layer.msg("权限不足！");
			return false;
		};
		$("#flowNode").val("1");
		var STUDENT_PKID = $("#STUDENT_PKID_FLOW").val();
		yingxin.doSearchForOneStudent(STUDENT_PKID);
	});
	//流程节点点击事件
	$('#ecardopencardFlow').click(function(){
		if(yingxin.isCanClickFlowNode("2") == false){
			layer.msg("权限不足！");
			return false;
		};
		$("#flowNode").val("2");
		var STUDENT_PKID = $("#STUDENT_PKID_FLOW").val();
		yingxin.doSearchForOneStudent(STUDENT_PKID);
	});
	//流程节点点击事件
	$('#fenqinFlow').click(function(){
		if(yingxin.isCanClickFlowNode("3") == false){
			layer.msg("权限不足！");
			return false;
		};
		$("#flowNode").val("3");
		var STUDENT_PKID = $("#STUDENT_PKID_FLOW").val();
		yingxin.doSearchForOneStudent(STUDENT_PKID);
	});
	//流程节点点击事件
	$('#banlilvsetongdaoFlow').click(function(){
		if(yingxin.isCanClickFlowNode("4") == false){
			layer.msg("权限不足！");
			return false;
		};
		$("#flowNode").val("4");
		var STUDENT_PKID = $("#STUDENT_PKID_FLOW").val();
		yingxin.doSearchForOneStudent(STUDENT_PKID);
	});
	//流程节点点击事件
	$('#jiaofeiFlow').click(function(){
		if(yingxin.isCanClickFlowNode("5") == false){
			layer.msg("权限不足！");
			return false;
		};
		$("#flowNode").val("5");
		var STUDENT_PKID = $("#STUDENT_PKID_FLOW").val();
		yingxin.doSearchForOneStudent(STUDENT_PKID);
	});
	//流程节点点击事件
	$('#ruzhuFlow').click(function(){
		if(yingxin.isCanClickFlowNode("6") == false){
			layer.msg("权限不足！");
			return false;
		};
		$("#flowNode").val("6");
		var STUDENT_PKID = $("#STUDENT_PKID_FLOW").val();
		yingxin.doSearchForOneStudent(STUDENT_PKID);
	});
	
	
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		yingxin.getStudentPayTab();
		
		var conditions=$('#conditions').val();
		
		//查询学生信息
		var url=_basepath+"pay/getStudentQuerylist2.json";
		$.post(url,{conditions:conditions},function(result){
			if(result.result == 'success'){
				if(result.stuList.length == 0){
					layer.msg("未查询到该学生！");
//					$("#checkReset").click();
				}else if(result.stuList.length == 1){
					
					yingxin.doSearchForOneStudent(result.stuList[0].PKID);
					return false;
				}else{
					$('#mymodaStudentList').modal({
						keyboard: true
					});
					yingxin.refreshStudentTable(conditions);
				}
				
			}else{
				$('#mymodaStudentList').modal({
					keyboard: true
				});
				yingxin.refreshStudentTable(conditions, status);
			}
			
		});
		
	});
	
	//报到
	
	yingxin.paymentreport = function(STUDENT_PKID){
		
		//$("#baodaoa").show();
		$("#baodaobutton1").css("display","block");
		// $("#baodaobutton1").attr('disabled',"true");
		//$("#baodaobutton1").css("display","block");
		var baodaohtmls = '<div class="pa-row">'+
		'<table class="table table-striped table-hover table-bordered jf_table" style="text-align: center;">'+
			'<tr>'+
				'<th>项目</th>'+
				'<th>报到状态</th>'+
			'</tr>';
		$.ajax({
		type: 'POST',
		url: _basepath+"yingxin/getbaodaoFlowContent.json",
		data: {
		STUDENT_PKID : STUDENT_PKID
		},
		dataType : "json",
		success : function(result) {
		  if(result.result == "success"){
			  var resultList = result.resultList;
			  var sfbd="";
			  if(resultList != null && resultList != ''){
				  for (var l = 0; l < resultList.length; l++) {
					  sfbd=resultList[l].IS_RECEIVED;
					  //SELECT Y.SCHOOL_YEAR_NAME, PS.PAY_STYLE_NAME, L.AMOUNTRECEIVABLE, L.LOAN_MONEY
					  baodaohtmls += '<tr>'+
									'<td>报到</td>'+
									'<td>'+resultList[l].IS_RECEIVED+'</td>'+
								'</tr>';
				  }
				  baodaohtmls += '</table>'+
				  '</div>';

				  $("#baodaoFlowContent").html(baodaohtmls);
			  }
			  
			  if(sfbd=="已报到"){
				  $("#baodaobutton1").attr('disabled',true);
				  $('#baodaobutton1').attr('style','background:#888888;border-color:#888888');
			  }else if(sfbd=="未报到"){
				  $("#baodaobutton1").attr('disabled',false);
				  $('#baodaobutton1').attr('style','backgroud:red;border-color:red;');
			  }
			
			  
		  }
		}
		});

		
};

//报到按钮
$('#baodaobutton1').click(function(){
	
	var studentPkid = $("#STUDENT_PKID_FLOW").val();
	var sfzhm=$("#SHENFENZHENGHAO").html();
	
	var url=_basepath+"yingxin/baodao.json";
	$.post(url,{PKID:studentPkid},function(result){
		if(result.result == 'success'){
			layer.msg("报到成功！");
			var studentPkid = $("#STUDENT_PKID_FLOW").val();
			$("#resultListId").find("li").remove();
			yingxin.getArrivalStuInfo();
			yingxin.paymentreport(studentPkid);
			yingxin.getStatisticsInfo(studentPkid);
			yingxin.getFlowStatus(studentPkid);
			return false;
		}else{
			layer.msg("报到失败，请重试！");
			return false;
		}
		 
	});
	
	
	
});


	
	
	//重置按钮点击事件
	$('#checkReset').click(function(){
		var flowNode = $("#flowNode").val();
		$('.s-bgWhite').load(_basepath+'payment/arrivalstu_list.php?flowNode='+ flowNode);
	});
	//报到名单
	yingxin.getArrivalStuInfo();
	//刷新流程标题
	yingxin.setFlowTitle();
	//统计
	yingxin.getStatisticsInfo();
	//获取流程状态
	yingxin.getFlowStatus();
  $('.pa-item').click(function () {
		$(this).addClass('active');
		$('.pa-item').not($(this)).removeClass('active');
		
		idx = $(this).index('.pa-item');
		$('.pa-row').eq(idx).stop().show(100);
		$('.pa-row').not($('.pa-row').eq(idx)).hide(100);
		var STATUS = $("#statusSelected").find(".active").attr("text");
	    var STUDENT_PKID = $("#STUDENT_PKID_FLOW").val();
	   /* $("#offlinePayTable").bootstrapTable('destroy'); 
	    yingxin.getTab(STUDENT_PKID,STATUS);//缴费*/
	    yingxin.refreshofflinePayTable(STUDENT_PKID,STATUS);
	});
})(jQuery, window);

//点击缴费/退费
var jiaotui = function(payItemPkid, index, status,AMOUNTRECEIVABLE,AMOUNTCOLLECTION){
	var studentPkid = $("#STUDENT_PKID_FLOW").val();
	var flowNodeN = yingxin.getFlowStatusN(studentPkid,"3");
	if(flowNodeN != ''){
		layer.msg("该学生尚未办理【"+flowNodeN+"】");
		return false;
	}
	
	//清空文本框
	$("#moneyPay").val('');
	$("#moneyCardPay").val('');
	$("#moneyTTPay").val('');
	$("#moneyAlipayPay").val('');
	$("#moneyWechatPay").val('');
	$("#remarksPay").val('');
	$("#cardNOPay").val('');
	
	$("#zfb_txm").val('');
	$("#wx_txm").val('');
	
	/*$("input[name='payType']").get(0).checked=true; 
	$("#moneyPay").removeAttr("disabled");
	
	$("#moneyCardPay").attr("disabled","disabled");
	$("#cardNOPay").attr("disabled","disabled");*/
	//update by ccc
	$("input[name='payType']").get(1).checked=true; 
	$("#moneyPay").attr("disabled","disabled");
	
	$("#moneyCardPay").removeAttr("disabled");
	$("#cardNOPay").removeAttr("disabled");
	$("#moneyAlipayPay").attr("disabled","disabled");
	$("#zfb_txm").attr("disabled","disabled");
	$("#moneyWechatPay").attr("disabled","disabled");
	$("#wx_txm").attr("disabled","disabled");
	$("#moneyTTPay").attr("disabled","disabled");
	
	yingxin.AMOUNTRECEIVABLE_total = AMOUNTRECEIVABLE;
	yingxin.payItemPkid = payItemPkid;
	yingxin.AMOUNTRECEIVABLE_total = AMOUNTRECEIVABLE-AMOUNTCOLLECTION;
	$("#jiaotuifei").html("缴费");
	yingxin.jj = 'SF';
	$("#tuiObj").show();

	$('#mymoda3').modal({
			keyboard: true
	});
	//update by ccc
	setTimeout(function(){
		$("#moneyCardPay").focus();
	}, 300);
};

$('#payBatchButton').click(function(){
	var studentPkid = $("#STUDENT_PKID_FLOW").val();
	var flowNodeN = yingxin.getFlowStatusN(studentPkid,"3");
	if(flowNodeN != ''){
		layer.msg("该学生尚未办理【"+flowNodeN+"】");
		return false;
	}
	var pkids = yingxin.getPkidSelections() +"";
	if(pkids == ''){
		layer.msg("请至少选择一条数据！");
		return false;
	}
	yingxin.AMOUNTRECEIVABLE_total = 0;
	
	var AMOUNTRECEIVABLEs = yingxin.getAMOUNTRECEIVABLESelections() + "";
	var AMOUNTRECEIVABLEsArray = AMOUNTRECEIVABLEs.split(",");
	for (var int = 0; int < AMOUNTRECEIVABLEsArray.length; int++) {
		if(AMOUNTRECEIVABLEsArray[int] != ''){
			yingxin.AMOUNTRECEIVABLE_total += Number(AMOUNTRECEIVABLEsArray[int]);
		}
	}
	
	$(".moneyPayBatch").val('');
	$(".moneyCardPayBatch").val('');
	$(".moneyAlipayPayBatch").val('');
	$(".moneyWechatPayBatch").val('');
	$(".moneyTTPayBatch").val('');
	$("#remarksPayBatch").val('');
	$("#cardNOPayBatch").val('');
	
	yingxin.jj = 'SF';
	$("#batchText").html("批量缴费");
	
	payBatch();
});
$(".payType").click(function(){
	
	var isCheckPayTypeMoney = $('input:radio[name=payType]')[0].checked;
	var isCheckPayTypeCard = $('input:radio[name=payType]')[1].checked;
	var isCheckPayTypeAlipay = $('input:radio[name=payType]')[2].checked;
	var isCheckPayTypeWechat = $('input:radio[name=payType]')[3].checked;
	var isCheckPayTypeTT = $('input:radio[name=payType]')[4].checked;
	if(isCheckPayTypeMoney){
		$("#moneyCardPay").val('');
		$("#moneyCardPay").attr("disabled","disabled");
		$("#cardNOPay").val('');
		$("#cardNOPay").attr("disabled","disabled");
		$("#moneyAlipayPay").val('');
		$("#moneyAlipayPay").attr("disabled","disabled");
		$("#zfb_txm").val('');
		$("#zfb_txm").attr("disabled","disabled");
		$("#moneyWechatPay").val('');
		$("#moneyWechatPay").attr("disabled","disabled");
		$("#wx_txm").val('');
		$("#wx_txm").attr("disabled","disabled");
		$("#moneyPay").removeAttr("disabled");
		$("#moneyTTPay").val('');
		$("#moneyTTPay").attr("disabled","disabled");
	}else if(isCheckPayTypeCard){
		$("#moneyPay").val('');
		$("#moneyPay").attr("disabled","disabled");
		$("#cardNOPay").val('');
		$("#cardNOPay").attr("disabled","disabled");
		$("#moneyAlipayPay").val('');
		$("#moneyAlipayPay").attr("disabled","disabled");
		$("#zfb_txm").val('');
		$("#zfb_txm").attr("disabled","disabled");
		$("#moneyWechatPay").val('');
		$("#moneyWechatPay").attr("disabled","disabled");
		$("#wx_txm").val('');
		$("#wx_txm").attr("disabled","disabled");
		$("#moneyCardPay").removeAttr("disabled");
		$("#cardNOPay").removeAttr("disabled");
		$("#moneyTTPay").val('');
		$("#moneyTTPay").attr("disabled","disabled");
	}else if(isCheckPayTypeAlipay){
		$("#moneyPay").val('');
		$("#moneyPay").attr("disabled","disabled");
		$("#moneyCardPay").val('');
		$("#moneyCardPay").attr("disabled","disabled");
		$("#cardNOPay").val('');
		$("#cardNOPay").attr("disabled","disabled");
		$("#moneyWechatPay").val('');
		$("#moneyWechatPay").attr("disabled","disabled");
		$("#wx_txm").val('');
		$("#wx_txm").attr("disabled","disabled");
		$("#moneyAlipayPay").removeAttr("disabled");
		$("#zfb_txm").removeAttr("disabled");
		$("#moneyTTPay").val('');
		$("#moneyTTPay").attr("disabled","disabled");
	}else if(isCheckPayTypeWechat){
		$("#moneyPay").val('');
		$("#moneyPay").attr("disabled","disabled");
		$("#moneyCardPay").val('');
		$("#moneyCardPay").attr("disabled","disabled");
		$("#cardNOPay").val('');
		$("#cardNOPay").attr("disabled","disabled");
		$("#moneyAlipayPay").val('');
		$("#moneyAlipayPay").attr("disabled","disabled");
		$("#zfb_txm").val('');
		$("#zfb_txm").attr("disabled","disabled");
		$("#wx_txm").removeAttr("disabled");
		$("#moneyWechatPay").removeAttr("disabled");
		$("#moneyTTPay").val('');
		$("#moneyTTPay").attr("disabled","disabled");
	}else if(isCheckPayTypeTT){
		$("#moneyPay").val('');
		$("#moneyPay").attr("disabled","disabled");
		$("#moneyCardPay").val('');
		$("#moneyCardPay").attr("disabled","disabled");
		$("#cardNOPay").val('');
		$("#cardNOPay").attr("disabled","disabled");
		$("#moneyAlipayPay").val('');
		$("#moneyAlipayPay").attr("disabled","disabled");
		$("#zfb_txm").val('');
		$("#zfb_txm").attr("disabled","disabled");
		$("#moneyWechatPay").val('');
		$("#moneyWechatPay").attr("disabled","disabled");
		$("#wx_txm").val('');
		$("#wx_txm").attr("disabled","disabled");
		$("#moneyTTPay").val('');
		$("#moneyTTPay").removeAttr("disabled");
	}
});

//点击缴费/退费保存
$('#payButton').click(function(){
	
	if(payButtonStatus){
		return false;
	}
	$(this).attr("disabled","disabled");
	
	payButtonStatus = true;
	
	var moneyPay = $("#moneyPay").val();
	if(moneyPay != ''){
		moneyPay = Number(moneyPay);
	}else{
		moneyPay = '0';
	}
	var moneyCardPay = $("#moneyCardPay").val();
	if(moneyCardPay != ''){
		moneyCardPay = Number(moneyCardPay);
	}else{
		moneyCardPay = '0';
	}
	var cardNOPay = $("#cardNOPay").val();
	var moneyAlipayPay = $("#moneyAlipayPay").val();
	if(moneyAlipayPay != ''){
		moneyAlipayPay = Number(moneyAlipayPay);
	}else{
		moneyAlipayPay = '0';
	}
	var moneyWechatPay = $("#moneyWechatPay").val();
	if(moneyWechatPay != ''){
		moneyWechatPay = Number(moneyWechatPay);
	}else{
		moneyWechatPay = '0';
	}
	var moneyTTPay = $("#moneyTTPay").val();
	if(moneyTTPay != ''){
		moneyTTPay = Number(moneyTTPay);
	}else{
		moneyTTPay = '0';
	}
	var remarksPay = $("#remarksPay").val();
	
	var zfb_txm = $("#zfb_txm").val();
	var wx_txm = $("#wx_txm").val();
	
	var moneyPayTotal = moneyPay;
	var moneyCardPayTotal = moneyCardPay;
	var moneyWechatPayTotal = moneyWechatPay;
	var moneyAlipayPayTotal = moneyAlipayPay;
	var moneyTTPayTotal = moneyTTPay;
	
	var isCheckPayTypeMoney = $('input:radio[name=payType]')[0].checked;
	var isCheckPayTypeCard = $('input:radio[name=payType]')[1].checked;
	var isCheckPayTypeAlipay = $('input:radio[name=payType]')[2].checked;
	var isCheckPayTypeWechat = $('input:radio[name=payType]')[3].checked;
	var isCheckPayTypeTT = $('input:radio[name=payType]')[4].checked;
	if(isCheckPayTypeAlipay){
		if(zfb_txm == '' || zfb_txm == 'undefined' || typeof zfb_txm == 'undefined'){
			layer.msg("支付宝付款码不能为空，请先扫码！");
			resetPayButtionStatus();
			return false;
		}else if(!offlinePay.checkAuthCode(zfb_txm, 1)){
			layer.msg("非支付宝付款码，请确认！");
			resetPayButtionStatus();
			return false;
		}
	}else if(isCheckPayTypeWechat){
		if(wx_txm == '' || wx_txm == 'undefined' || typeof wx_txm == 'undefined'){
			layer.msg("微信付款码不能为空，请先扫码！");
			resetPayButtionStatus();
			return false;
		}else if(!offlinePay.checkAuthCode(wx_txm, 2)){
			layer.msg("非微信付款码，请确认！");
			resetPayButtionStatus();
			return false;
		}
	}else if(isCheckPayTypeMoney){
		
	}else if(isCheckPayTypeTT){
		
	}else if(isCheckPayTypeCard){
		
	}/*update by ccc else if(isCheckPayTypeCard){
		if(cardNOPay == '' || cardNOPay == 'undefined' || typeof cardNOPay == 'undefined'){
			layer.msg("卡号不能为空！");
			resetPayButtionStatus();
			return false;
		}
	}*/else{
		layer.msg("请选择支付方式！");
		resetPayButtionStatus();
		return false;
	}
	
	//缴费、退费
	payJT(moneyPay,
			moneyCardPay,
			cardNOPay,
			moneyAlipayPay,
			moneyWechatPay,
			moneyTTPay,
			remarksPay,
			moneyPayTotal,moneyCardPayTotal,moneyWechatPayTotal,moneyAlipayPayTotal,moneyTTPayTotal,yingxin.payItemPkid,zfb_txm,wx_txm);
	
	
});
//缴费/退费/批量缴费
var payJT = function(moneyPay,moneyCardPay,cardNOPay,moneyAlipayPay,moneyWechatPay,moneyTTPay,remarksPay,
		moneyPayTotal,moneyCardPayTotal,moneyWechatPayTotal,moneyAlipayPayTotal,moneyTTPayTotal,payItemPkids,zfb_txm,wx_txm){
	yingxin.studentPkid = $("#STUDENT_PKID_FLOW").val();
	if(moneyCardPay == '' || moneyCardPay == '0'){
		cardNOPay = '';
	}
	var AMOUNTRECEIVABLE_total = Number(yingxin.AMOUNTRECEIVABLE_total);
	if(yingxin.jj == 'SF' && ((Number(moneyPayTotal) + Number(moneyCardPayTotal) + Number(moneyWechatPayTotal) + Number(moneyAlipayPayTotal) +Number(moneyTTPayTotal)) > AMOUNTRECEIVABLE_total.toFixed(2))){
		layer.msg("实收金额不能超出应收金额!");
		resetPayButtionStatus();
		return false;
	}
	if(yingxin.jj == 'TF' && ((Number(moneyPayTotal) + Number(moneyCardPayTotal) + Number(moneyWechatPayTotal) + Number(moneyAlipayPayTotal) +Number(moneyTTPayTotal)) > AMOUNTRECEIVABLE_total.toFixed(2))){
		layer.msg("退费超上限！");
		resetPayButtionStatus();
		return false;
	}
	/*if(Number(moneyPayTotal) + Number(moneyCardPayTotal) + Number(moneyWechatPayTotal) + Number(moneyAlipayPayTotal) +Number(moneyTTPayTotal)<= 0){
		layer.msg("输入金额不能为空!");
		resetPayButtionStatus();
		return false;
	}*/

	
	payButtonStatus = true;
	//加密学生
	$.ajax({
		  type: 'POST',
		  url: _basepath+"pay/encryptStudent.json",
		  async: false,
		  data: {
			studentPkid:yingxin.studentPkid,
			TRANSACTION_TYPE:yingxin.jj,//收费还是退费
			PAY_ITEM_LIST_PKIDS:payItemPkids,//缴费名单集合，通过逗号分隔
			TOTALMONEY_CASH:moneyPayTotal == ''?'0':moneyPayTotal,//订单现金总金额
			TOTALMONEY_CARD:moneyCardPayTotal == ''?'0':moneyCardPayTotal,//订单银行卡总金额
			TOTALMONEY_WX:moneyWechatPayTotal == ''?'0':moneyWechatPayTotal,//订单微信总金额
			TOTALMONEY_ZFB:moneyAlipayPayTotal == ''?'0':moneyAlipayPayTotal,//订单支付宝总金额
			TOTALMONEY_TT:moneyTTPayTotal == ''?'0':moneyTTPayTotal//订单支付宝总金额
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == "success"){
				  var url = result.methodurl+"/colleges-pay/dingDanPayController/dingDan.php";
					var data={
							ORDERCREATE_MODE:'D',//订单生成方式:U:线上（学生APP或PC）D:线下（线下缴费操作）  DO:线下其他收费（其他收费）I:导入
							STUDENT_PKID:yingxin.studentPkid,//学生PKID
							ORDERCREATE_TERMINAL:'07',//订单生成终端  07:互联网  08:移动-iOS 09:移动-Andriod  10:移动-其他
							TRANSACTION_TYPE:yingxin.jj,//收费还是退费
							TOTALMONEY_CASH:moneyPayTotal == ''?'0':moneyPayTotal,//订单现金总金额
							TOTALMONEY_CARD:moneyCardPayTotal == ''?'0':moneyCardPayTotal,//订单银行卡总金额
							TOTALMONEY_WX:moneyWechatPayTotal == ''?'0':moneyWechatPayTotal,//订单微信总金额
							TOTALMONEY_ZFB:moneyAlipayPayTotal == ''?'0':moneyAlipayPayTotal,//订单支付宝总金额
							TOTALMONEY_TT:moneyTTPayTotal == ''?'0':moneyTTPayTotal,//订单电汇总金额
							CJR:yingxin.userPkid,//创建人
							REMARK:remarksPay,//备注
							CARDNO:cardNOPay,//银行卡号 （当银行卡金额不为'0'时必填）
							CASH:moneyPay == ''?'0':moneyPay,//现金各缴费项目缴费金额（当有转出转入项目时必传）
							CARD:moneyCardPay == ''?'0':moneyCardPay,//银行卡各缴费项目缴费金额（当有转出转入项目时必传）
							WX:moneyWechatPay == ''?'0':moneyWechatPay,//微信支付各缴费项目金额（当有转出转入项目时必传）
							ZFB:moneyAlipayPay == ''?'0':moneyAlipayPay,//支付宝支付各缴费项目金额（当有转出转入项目时必传）
							TT:moneyTTPay == ''?'0':moneyTTPay,//电汇支付各缴费项目金额（当有转出转入项目时必传）
							PAY_ITEM_LIST_PKIDS:payItemPkids,
							PASSMSG:result.PASSMSG,
							ZFB_TXM:zfb_txm,
							WX_TXM:wx_txm
							
					};
					
					$.ajax({
						type: 'POST',
				        url: url,
				        data:data,
				        dataType:'JSONP',//here
				        jsonp:"callback",
				        success:function (data) {
				        	
				        	resetPayButtionStatus();
				        	
				        	if(data.result == 'true'){
				        		layer.msg("操作成功!");
				    			$("#cancelButton").click();
				    			$("#cancelButtonBatch").click();
				    			//隐藏蒙版
				    			$(".modal-backdrop").remove();
				    			
				    			var status = $("#statusSelected").find(".active").attr("text");
				    			
				    			//刷新页面
				    			//doSearchForOneStudent(yingxin.studentPkid, status);
				    			//$("#offlinePayTable").bootstrapTable('refresh'); 
				    			yingxin.refreshofflinePayTable(yingxin.studentPkid,status);
				    			yingxin.getStatisticsInfo();
				        	}else{
				        		layer.msg(data.msg);
				        	}
				        	
				        },
				        error: function (XMLHttpRequest, textStatus, errorThrown) {
				        	resetPayButtionStatus();
		                    // 状态码
		                    console.log(XMLHttpRequest.status);
		                    // 状态
		                    console.log(XMLHttpRequest.readyState);
		                    // 错误信息   
		                    console.log(textStatus);
		                }
				    });
			  }else{//验证宿舍资源失败或其他情况失败
				  resetPayButtionStatus();
				  if(result.errorinfo == ''){
					  layer.msg("操作失败，请重试!");
				  }else{//1.含有宿舍类型的缴费项目，并且宿舍资源已被抢完.  2.其他错误
					  layer.msg(result.errorinfo);
				  }
				  
			  }

		  
		  }
		});
	return false;

};
function clearNoNum(obj){  
    //修复第一个字符是小数点 的情况.  
    if(obj.value !=''&& obj.value.substr(0,1) == '.'){  
        obj.value="";  
    }  
    obj.value = obj.value.replace(/^0*(0\.|[1-9])/, '$1');//解决 粘贴不生效  
    obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的       
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");      
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数       
    if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额  
        if(obj.value.substr(0,1) == '0' && obj.value.length == 2){  
            obj.value= obj.value.substr(1,obj.value.length);      
        }  
    }      
} 
//获取选中的行
yingxin.getPkidSelections=function () {
		var $table = $('#offlinePayTable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.PKID;
    });
};

//获取选中的行
yingxin.getIdSelections=function () {
		var $table = $('#offlinePayTable');
  	return $.map($table.bootstrapTable('getSelections'), function(row) {
      return row.PAY_ITEM_PKID;
  });
};
//获取选中的行
yingxin.getNameSelections=function () {
		var $table = $('#offlinePayTable');
  	return $.map($table.bootstrapTable('getSelections'), function(row) {
      return row.PAYITEM;
  });
};

//获取选中的行
yingxin.getChildNameSelections=function () {
		var $table = $('#offlinePayTable');
  	return $.map($table.bootstrapTable('getSelections'), function(row) {
      return row.PAYITEM_CHILD;
  });
};

//获取选中的行
yingxin.getStatusSelections=function () {
		var $table = $('#offlinePayTable');
  	return $.map($table.bootstrapTable('getSelections'), function(row) {
      return row.STATUS;
  });
};

//获取选中的行
yingxin.getAMOUNTRECEIVABLESelections=function () {
		var $table = $('#offlinePayTable');
  	return $.map($table.bootstrapTable('getSelections'), function(row) {
      return row.AMOUNTRECEIVABLE;
  });
};
//获取选中的行获得 贷款/缓交 金额
yingxin.getLOANMONEYSelections=function () {
	var $table = $('#offlinePayTable');
	return $.map($table.bootstrapTable('getSelections'), function(row) {
		return row.LOAN_MONEY;
	});
};

//获取选中的行
yingxin.getAMOUNTCOLLECTIONSelections=function () {
		var $table = $('#offlinePayTable');
  	return $.map($table.bootstrapTable('getSelections'), function(row) {
      return row.AMOUNTCOLLECTION;
  });
};
var payBatch = function(){
	//如果没有多选,批量缴费点击无效果
	if($("#payBatchButton").attr("disabled") == "disabled" && $("#payBatchButton").attr("data-target") == "#mymoda1"){
		$("#payBatchButton").attr("data-target","");
		return;
	}
	
	var payBatchArrays = '';
	var selectPayItemPkids = yingxin.getIdSelections()+"";
	var payItemPkidArrays = selectPayItemPkids.split(",");
	var payItemNamess = yingxin.getNameSelections()+"";
	var payItemNameArrays = payItemNamess.split(",");
	var payItemChildNamess = yingxin.getChildNameSelections()+"";
	var payItemChildNameArrays = payItemChildNamess.split(",");
	var AMOUNTRECEIVABLEss = yingxin.getAMOUNTRECEIVABLESelections()+"";
	var AMOUNTRECEIVABLEArrays = AMOUNTRECEIVABLEss.split(",");
	var AMOUNTCOLLECTIONss = yingxin.getAMOUNTCOLLECTIONSelections()+"";
	var AMOUNTCOLLECTIONArrays = AMOUNTCOLLECTIONss.split(",");
	var LOANMONEYss = yingxin.getLOANMONEYSelections()+"";
	var LOANMONEYArrays = LOANMONEYss.split(",");
	
	
	
	var moneyTotalBatchs = 0;
	
	
	//如果已经多选
	if(yingxin.getIdSelections() !=''){
//	if(payItemPkidArrays != '' && payItemPkidArrays.length > 0){
		$("#payBatchButton").attr("disabled",false);
		$("#payBatchButton").attr("data-target","#mymoda1");
	}
	
	//批量索引
	yingxin.moneyBatchTextIndex = payItemPkidArrays.length;
	
	payBatchArrays += '<div style="margin-bottom: 15px;">'+
	'<label for="">缴费项目</label>';
	
	for(var i=0; i<payItemPkidArrays.length; i++){
		if(payItemPkidArrays[i] != ''){
			//贷款/缓交 金额
			var loanmoney = Number(LOANMONEYArrays[i]);
			//剩余应缴费用 有贷款的，欠费金额-贷款金额，出现负数时，默认0，0项保存时过滤此项
			var surplusMoney = Number(AMOUNTRECEIVABLEArrays[i]) - Number(AMOUNTCOLLECTIONArrays[i])-loanmoney;
			if(surplusMoney<0){
				surplusMoney = 0;
			}
			var surplusMoneyHidden = Number(AMOUNTRECEIVABLEArrays[i]) - Number(AMOUNTCOLLECTIONArrays[i]);
			payBatchArrays += '<div action="" class="form-inline">'+
				'<div class="form-group col-xs-3" style="line-height:42px;">'+
					'<span>'+payItemNameArrays[i]+(payItemChildNameArrays[i] !='' && payItemChildNameArrays[i] !='undefined'&& payItemChildNameArrays[i] !=' ' && typeof payItemChildNameArrays[i] !='undefined'? '-'+payItemChildNameArrays[i] : '' )+'</span>'+
				'</div>'+
				'<div class="form-group col-xs-8">'+
					'<span>金额 </span>'+
					'<span style="0px;"><input type="text" id="yingjiaojine_'+i+'" '+(
							yingxin.jj == "SF"? ' value="'+surplusMoney.toFixed(2)+'" disabled="disabled" ' : ''		
					)+' class="moneyPayBatch moneyBatchText'+i+' form-control" style="width: 50px;" placeholder="金额" onkeyup="clearNoNum(this)"/>&nbsp;&nbsp;'+
					'<label><input class="payTypeBatch" name="changeAmountColl" type="checkbox" value="'+i+'" id="changeAmountColl" checked="checked"/></label>'+
					'<input type="hidden" id="qianfeijine_'+i+'" value="'+surplusMoneyHidden.toFixed(2)+'" >'+
					'<input type="hidden" id="yingjiaojinehidden_'+i+'" value="'+surplusMoney.toFixed(2)+'" >'+
					'<input type="hidden" id="payItemPkidArrays_'+i+'" value="'+payItemPkidArrays[i]+'" >'+
					'<span>贷款/缓交</span>&nbsp;'+
					'<span>'+loanmoney.toFixed(2)+'</span>&nbsp;'+
					/*'<input type="text" name="" '+(
						yingxin.jj == "SF"? ' value="'+loanmoney.toFixed(2)+'" disabled="disabled" ' : ''		
					)+' class="moneyBatchText'+i+' form-control" style="width: 83px;" placeholder="金额" onkeyup="clearNoNum(this)"/>'+*/
					'</span>'+
					
				'</div>';
			payBatchArrays += '<div class="clearfix"></div>'+
			'</div>';
			
			moneyTotalBatchs += surplusMoney;
		}
		
	}
	payBatchArrays += '</div>';
	
	payBatchArrays += '<div style="margin-bottom: 15px;"><label for="">合计</label>';
	
	payBatchArrays += '<div action="" class="form-inline">'+
		'<div class="form-group col-xs-3">'+
			'<span id="moneyTotalBatch" style="color:#f0ad4e !important;font-weight:bold;font-size: 20px;">'+(yingxin.jj == "SF"? moneyTotalBatchs.toFixed(2) : '0')+'</span>'+
		'</div><div class="clearfix"></div></div></div>';
	
	payBatchArrays += '<div style="margin-bottom: 15px;">'+
	'<label for="">支付方式</label>';
	payBatchArrays += '<div action="" class="form-inline" style="margin-bottom: 10px;">'+
		'<div class="form-group col-xs-5">'+
			'<label><input class="payTypeBatch" name="payTypeBatch" type="radio" value="" /></label><span>现金</span>'+
		'</div><div class="clearfix"></div></div>';
	
	payBatchArrays += '<div action="" class="form-inline" style="margin-bottom: 10px;" >'+
	'<div class="form-group col-xs-5">'+
	'<label><input class="payTypeBatch" name="payTypeBatch" type="radio" value="" checked/></label><span>银行卡</span>'+
	'<span style="padding-left:20px;"><input type="text" name="" value="" class="form-control" style="height:34px;" placeholder="卡号" id="cardNOPayBatch"/></span>'+
	'</div><div class="clearfix"></div></div>';
	
	var isDisplay = yingxin.jj == 'TF' ? ' display:none; ' : '';
	payBatchArrays += '<div action="" class="form-inline" style="margin-bottom: 10px;'+isDisplay+'" >'+
	'<div class="form-group col-xs-5">'+
	'<label><input class="payTypeBatch" name="payTypeBatch" type="radio" value="" /></label><span>支付宝</span>'+
	'<span style="padding-left:20px;"><input type="text" name="" value="" class="form-control" style="height:34px;" placeholder="支付宝付款码" id="zfb_txm_batch"/></span>'+
	'</div><div class="clearfix"></div></div>';
	payBatchArrays += '<div action="" class="form-inline" style="margin-bottom: 10px;'+isDisplay+'" >'+
	'<div class="form-group col-xs-5">'+
	'<label><input class="payTypeBatch" name="payTypeBatch" type="radio" value="" /></label><span>微信</span>'+
	'<span style="padding-left:35px;"><input type="text" name="" value="" class="form-control" style="height:34px;" placeholder="微信付款码" id="wx_txm_batch"/></span>'+
	'</div><div class="clearfix"></div></div>';
	payBatchArrays += '<div action="" class="form-inline" style="margin-bottom: 10px;'+isDisplay+'" >'+
	'<div class="form-group col-xs-5">'+
	'<label><input class="payTypeBatch" name="payTypeBatch" type="radio" value="" /></label><span>电汇</span>'+
	'</div><div class="clearfix"></div></div>';
	payBatchArrays += '</div>';
		
	payBatchArrays += '<div style="margin-bottom: 15px;"><label for="">备注</label>';
	payBatchArrays += '<div action="" class="form-inline">'+
		'<div class="form-group col-xs-3">'+
			'<span><input  id="remarksPayBatch" type="text" name=""  value="" class="form-control" placeholder="备注" style="width:450px;"/></span>'+
		'</div><div class="clearfix"></div></div></div>';
	
	//总金额赋初值
	$("#moneyTotalBatch").html(0);
	$("#moneyCardTotalBatch").html(0);
	$("#moneyAlipayTotalBatch").html(0);
	$("#moneyWechatTotalBatch").html(0);
	$("#moneyTTTotalBatch").html(0);
	
	$("#payBatchArray").html(payBatchArrays);
	
	if(yingxin.jj == 'TF'){
		//隐藏支付宝和微信
		$(".payBatchTui").hide();
	}else{
		$(".payBatchTui").show();
	}

	
	//绑定输入框事件
	$(".moneyPayBatch").bind('input propertychange', function() {
		
		var moneyTotalBatchs = 0;
		$(".moneyPayBatch").each(function(){
			clearNoNum(this);
			moneyTotalBatchs += Number($(this).val());
		});
		$("#moneyTotalBatch").html(moneyTotalBatchs);
	});
	$(".moneyCardPayBatch").bind('input propertychange', function() {
		
		var moneyCardTotalBatchs = 0;
		$(".moneyCardPayBatch").each(function(){
			clearNoNum(this);
			moneyCardTotalBatchs += Number($(this).val());
		});
		$("#moneyCardTotalBatch").html(moneyCardTotalBatchs);
	});
	$(".moneyAlipayPayBatch").bind('input propertychange', function() {
		
		var moneyAlipayPayBatchs = 0;
		$(".moneyAlipayPayBatch").each(function(){
			clearNoNum(this);
			moneyAlipayPayBatchs += Number($(this).val());
		});
		$("#moneyAlipayTotalBatch").html(moneyAlipayPayBatchs);
		
	});
	$(".moneyWechatPayBatch").bind('input propertychange', function() {
		
		var moneyWechatTotalBatchs = 0;
		$(".moneyWechatPayBatch").each(function(){
			clearNoNum(this);
			moneyWechatTotalBatchs += Number($(this).val());
		});
		$("#moneyWechatTotalBatch").html(moneyWechatTotalBatchs);

	});
	
	$(".moneyTTPayBatch").bind('input propertychange', function() {
		
		var moneyTTTotalBatchs = 0;
		$(".moneyTTPayBatch").each(function(){
			clearNoNum(this);
			moneyTTTotalBatchs += Number($(this).val());
		});
		$("#moneyTTTotalBatch").html(moneyTTTotalBatchs);

	});
	$('#mymoda1').modal({
			keyboard: true
		});

	$("#cardNOPayBatch").val('');
	//update by ccc
//	$("#cardNOPayBatch").attr("disabled","disabled");
	setTimeout(function(){
		$("#cardNOPayBatch").focus();
	}, 300);
	$("#zfb_txm_batch").val('');
	$("#zfb_txm_batch").attr("disabled","disabled");
	$("#wx_txm_batch").val('');
	$("#wx_txm_batch").attr("disabled","disabled");
	//批量缴费页面的checkbox点击事件
	$("input[name='changeAmountColl']").click(function(){
		/*alert(this.value);*/
		/*alert($(this).attr("checked"));*/
		//判断页面是否选中checkbox
		var idCheck = $(this).is(":checked");
		//欠费金额
		var qianfeijine = $("#qianfeijine_"+this.value).val();
		//页面加载出来的应交金额
		var yingjiaojine = $("#yingjiaojinehidden_"+this.value).val();
		//如果选中金额列展示 有贷款的，欠费金额-贷款金额，出现负数时，默认0，0项保存时过滤此项
		//如果不选中金额列展示欠费金额
		if(idCheck==true){
			$("#yingjiaojine_"+this.value).val(yingjiaojine);
		}else{
			$("#yingjiaojine_"+this.value).val(qianfeijine);
		}
		//将缴费项的金额合计
		var moneyTotalBatchUpdate = 0;
				
		for(var i=0; i<payItemPkidArrays.length; i++){
			var moneyTotalBatch =  $("#yingjiaojine_"+i).val();
			moneyTotalBatchUpdate+=Number(moneyTotalBatch);		
		}		
		//改变合计金额
		var moneyTotalBatchresult = moneyTotalBatchUpdate.toFixed(2);
		$("#moneyTotalBatch").html(moneyTotalBatchresult);
		
	});
	
	
	$(".payTypeBatch").click(function(){
		var isCheckPayTypeMoney = $('input:radio[name=payTypeBatch]')[0].checked;
		var isCheckPayTypeCard = $('input:radio[name=payTypeBatch]')[1].checked;
		var isCheckPayTypeAlipay = $('input:radio[name=payTypeBatch]')[2].checked;
		var isCheckPayTypeWechat = $('input:radio[name=payTypeBatch]')[3].checked;
		var isCheckPayTypeTT = $('input:radio[name=payTypeBatch]')[4].checked;
		if(isCheckPayTypeMoney){
			$("#cardNOPayBatch").val('');
			$("#cardNOPayBatch").attr("disabled","disabled");
			$("#zfb_txm_batch").val('');
			$("#zfb_txm_batch").attr("disabled","disabled");
			$("#wx_txm_batch").val('');
			$("#wx_txm_batch").attr("disabled","disabled");
			
		}else if(isCheckPayTypeCard){
			$("#zfb_txm_batch").val('');
			$("#zfb_txm_batch").attr("disabled","disabled");
			$("#wx_txm_batch").val('');
			$("#wx_txm_batch").attr("disabled","disabled");
			$("#cardNOPayBatch").removeAttr("disabled");
			
		}else if(isCheckPayTypeAlipay){
			$("#cardNOPayBatch").val('');
			$("#cardNOPayBatch").attr("disabled","disabled");
			$("#wx_txm_batch").val('');
			$("#wx_txm_batch").attr("disabled","disabled");
			$("#zfb_txm_batch").removeAttr("disabled");
		}else if(isCheckPayTypeWechat){
			$("#cardNOPayBatch").val('');
			$("#cardNOPayBatch").attr("disabled","disabled");
			$("#zfb_txm_batch").val('');
			$("#zfb_txm_batch").attr("disabled","disabled");
			$("#wx_txm_batch").removeAttr("disabled");
		}else if(isCheckPayTypeTT){
			$("#cardNOPayBatch").val('');
			$("#cardNOPayBatch").attr("disabled","disabled");
			$("#zfb_txm_batch").val('');
			$("#zfb_txm_batch").attr("disabled","disabled");
			$("#wx_txm_batch").val('');
			$("#wx_txm_batch").attr("disabled","disabled");
		}
	});
};


//批量缴费点击保存
$('#payButtonBatch').click(function(){
	var selectPayItemPkids = yingxin.getIdSelections()+"";
	var payItemPkidArrays = selectPayItemPkids.split(",");
	if(payButtonStatus){
		return false;
	}
	$(this).attr("disabled","disabled");
	payButtonStatus = true;
	
	yingxin.moneyBatchArray = '';
	yingxin.moneyCardBatchArray = '';
	yingxin.moneyAlipayBatchArray = '';
	yingxin.moneyWechatBatchArray = '';
	yingxin.moneyTTBatchArray = '';
	
	//批量各项金额必须输入
/*	if(yingxin.moneyBatchTextIndex > 0){
		for (var i = 0; i < yingxin.moneyBatchTextIndex; i++) {
			var totalMoneyInput = 0;
			$(".moneyBatchText" + i).each(function(){
				alert(i);
				var obj = $(this).val();
				if($(this).val() != ''){
					totalMoneyInput += Number(obj);
				}else{
					totalMoneyInput += 0;
				}
				alert(totalMoneyInput);
			});
			if(totalMoneyInput == 0){
				layer.msg("输入金额必须大于0!");
				resetPayButtionStatus();
				return false;
			}
		}
		
	}*/
	
	var inputCount = 0;
	//拼接金额
	$(".moneyPayBatch").each(function(){
		inputCount ++;
		if($(this).val() != '0.00'&&$(this).val() !=""&&$(this).val() !=null){
			yingxin.moneyBatchArray += $(this).val();
			yingxin.moneyBatchArray += ',';
		}
		
	});
	$(".moneyCardPayBatch").each(function(){
		inputCount ++;
		if($(this).val() != '0.00'&&$(this).val() !=""&&$(this).val() !=null){
			yingxin.moneyCardBatchArray += $(this).val();
			yingxin.moneyCardBatchArray += ',';
		}
		
	});
	$(".moneyAlipayPayBatch").each(function(){
		inputCount ++;
		if($(this).val() != '0.00'&&$(this).val() !=""&&$(this).val() !=null){
			yingxin.moneyAlipayBatchArray += $(this).val();
			yingxin.moneyAlipayBatchArray += ',';
		}
		
	});
	$(".moneyWechatPayBatch").each(function(){
		inputCount ++;
		if($(this).val() != '0.00'&&$(this).val() !=""&&$(this).val() !=null){
			yingxin.moneyWechatBatchArray += $(this).val();
			yingxin.moneyWechatBatchArray += ',';
		}
		
	});
	$(".moneyTTPayBatch").each(function(){
		inputCount ++;
		if($(this).val() != '0.00'&&$(this).val() !=""&&$(this).val() !=null){
			yingxin.moneyTTBatchArray += $(this).val();
			yingxin.moneyTTBatchArray += ',';
		}
		
	});

	var remarksPay = $("#remarksPayBatch").val();
	var cardNOPay = $("#cardNOPayBatch").val();
	
	var moneyPayTotal = $("#moneyTotalBatch").html();
	var moneyCardPayTotal = $("#moneyCardTotalBatch").html();
	var moneyWechatPayTotal = $("#moneyWechatTotalBatch").html();
	var moneyAlipayPayTotal = $("#moneyAlipayTotalBatch").html();
	var moneyTTPayTotal = $("#moneyTTTotalBatch").html();
	
	var cardNOPayBatch = $("#cardNOPayBatch").val();
	var zfb_txm = $("#zfb_txm_batch").val();
	var wx_txm = $("#wx_txm_batch").val();
	var isCheckPayTypeMoneyBatch = $('input:radio[name=payTypeBatch]')[0].checked;
	var isCheckPayTypeCardBatch = $('input:radio[name=payTypeBatch]')[1].checked;
	var isCheckPayTypeAlipayBatch = $('input:radio[name=payTypeBatch]')[2].checked;
	var isCheckPayTypeWechatBatch = $('input:radio[name=payTypeBatch]')[3].checked;
	var isCheckPayTypeTTBatch = $('input:radio[name=payTypeBatch]')[4].checked;
	if(isCheckPayTypeMoneyBatch){
		
		yingxin.moneyAlipayBatchArray = getMoneyArray(inputCount);
		yingxin.moneyCardBatchArray = getMoneyArray(inputCount);
		yingxin.moneyWechatBatchArray = getMoneyArray(inputCount);
		yingxin.moneyTTBatchArray = getMoneyArray(inputCount);
		moneyAlipayPayTotal = 0;
		moneyCardPayTotal = 0;
		moneyWechatPayTotal = 0;
		moneyTTPayTotal = 0;
	}else if(isCheckPayTypeCardBatch){
		yingxin.moneyCardBatchArray = yingxin.moneyBatchArray;
		moneyCardPayTotal = moneyPayTotal;
		
		yingxin.moneyBatchArray = getMoneyArray(inputCount);
		yingxin.moneyAlipayBatchArray = getMoneyArray(inputCount);
		yingxin.moneyWechatBatchArray = getMoneyArray(inputCount);
		yingxin.moneyTTBatchArray = getMoneyArray(inputCount);
		moneyPayTotal = 0;
		moneyAlipayPayTotal = 0;
		moneyWechatPayTotal = 0;
		moneyTTPayTotal = 0;
		//update by ccc
		/*if(cardNOPayBatch == '' || cardNOPayBatch == 'undefined' || typeof cardNOPayBatch == 'undefined'){
			layer.msg("卡号不能为空！");
			resetPayButtionStatus();
			return false;
		}*/
	}else if(isCheckPayTypeAlipayBatch){
		yingxin.moneyAlipayBatchArray = yingxin.moneyBatchArray;
		moneyAlipayPayTotal = moneyPayTotal;
		
		yingxin.moneyBatchArray = getMoneyArray(inputCount);
		yingxin.moneyCardBatchArray = getMoneyArray(inputCount);
		yingxin.moneyWechatBatchArray = getMoneyArray(inputCount);
		yingxin.moneyTTBatchArray = getMoneyArray(inputCount);
		moneyPayTotal = 0;
		moneyCardPayTotal = 0;
		moneyWechatPayTotal = 0;
		moneyTTPayTotal = 0;
		if(zfb_txm == '' || zfb_txm == 'undefined' || typeof zfb_txm == 'undefined'){
			layer.msg("支付宝付款码不能为空，请先扫码！");
			resetPayButtionStatus();
			return false;
		}else if(!yingxin.checkAuthCode(zfb_txm, 1)){
			layer.msg("非支付宝付款码，请确认！");
			resetPayButtionStatus();
			return false;
		}
	}else if(isCheckPayTypeWechatBatch){
		
		yingxin.moneyWechatBatchArray = yingxin.moneyBatchArray;
		moneyWechatPayTotal = moneyPayTotal;
		
		yingxin.moneyBatchArray = getMoneyArray(inputCount);
		yingxin.moneyCardBatchArray = getMoneyArray(inputCount);
		yingxin.moneyAlipayBatchArray = getMoneyArray(inputCount);
		yingxin.moneyTTBatchArray = getMoneyArray(inputCount);
		moneyPayTotal = 0;
		moneyCardPayTotal = 0;
		moneyAlipayPayTotal = 0;
		moneyTTPayTotal = 0;
		if(wx_txm == '' || wx_txm == 'undefined' || typeof wx_txm == 'undefined'){
			layer.msg("微信付款码不能为空，请先扫码！");
			resetPayButtionStatus();
			return false;
		}else if(!yingxin.checkAuthCode(wx_txm, 2)){
			layer.msg("非微信付款码，请确认！");
			resetPayButtionStatus();
			return false;
		}
	}else if(isCheckPayTypeTTBatch){
		yingxin.moneyTTBatchArray = yingxin.moneyBatchArray;
		moneyTTPayTotal = moneyPayTotal;
		
		yingxin.moneyBatchArray = getMoneyArray(inputCount);
		yingxin.moneyCardBatchArray = getMoneyArray(inputCount);
		yingxin.moneyAlipayBatchArray = getMoneyArray(inputCount);
		yingxin.moneyWechatBatchArray = getMoneyArray(inputCount);
		moneyPayTotal = 0;
		moneyCardPayTotal = 0;
		moneyAlipayPayTotal = 0;
		moneyWechatPayTotal = 0;
	}else{
		layer.msg("请选择支付方式！");
		return false;
	}
	//拼接应交金额不为0的pkid
	var payItemPkidNewArray="";
	var length = payItemPkidArrays.length-1;
	for(var i=0; i<payItemPkidArrays.length; i++){
		var moneyTotalBatch =  $("#yingjiaojine_"+i).val();
		//拼接缴费项目id
		if(moneyTotalBatch!=0){				
			if(i==length){
				payItemPkidNewArray+=$("#payItemPkidArrays_"+i).val();
			}else{
				payItemPkidNewArray+=($("#payItemPkidArrays_"+i).val()+",");
			}				
		}			
	}
	yingxin.payItemPkidsNew = payItemPkidNewArray;
	if(yingxin.payItemPkidsNew==""){
		layer.msg("实交金额必须大于0！");
		resetPayButtionStatus();
		return false;
	}
	//alert(yingxin.payItemPkidsNew);
	//批量缴费
	payJT(yingxin.moneyBatchArray,
			yingxin.moneyCardBatchArray,
			cardNOPay,
			yingxin.moneyAlipayBatchArray,
			yingxin.moneyWechatBatchArray,
			yingxin.moneyTTBatchArray,
			remarksPay,
			moneyPayTotal,moneyCardPayTotal,moneyWechatPayTotal,moneyAlipayPayTotal,moneyTTPayTotal,yingxin.payItemPkidsNew+"",zfb_txm,wx_txm);
	
});
var getMoneyArray = function(count){
	var moneyArray = "";
	for (var i = 0; i < count; i++) {
		moneyArray += "0";
		if(i != count -1){
			moneyArray += ",";
		}
	}
	return moneyArray;
};
//点击批量打印
$("#payPrintBatchButton").click(function(){
	var studentPkid = $("#STUDENT_PKID_FLOW").val();
	var flowNodeN = yingxin.getFlowStatusN(studentPkid,"3");
	if(flowNodeN != ''){
		layer.msg("该学生尚未办理【"+flowNodeN+"】");
		return false;
	}
	var pkids = yingxin.getPkidSelections() +"";
	if(pkids == ''){
		layer.msg("请至少选择一条数据！");
		return false;
	}
	payPrintBatch4();

});
//批打收据（哈尔滨外国语）
var payPrintBatch4 = function(){
	//如果没有多选,批量打印点击无效果
	if($("#payPrintBatchButton").attr("disabled") == "disabled" ){
		$("#payPrintBatchButton").attr("data-target","")
		return;
	}
	var payItemPkids = yingxin.getIdSelections()+"";
	var payItemPkidsArrays = payItemPkids.split(",");
	//如果已经多选
	if(payItemPkidsArrays != '' && payItemPkidsArrays.length > 0){
		$("#payBatchButton").attr("disabled",false);
		$("#payBatchButton").attr("data-target","#mymoda3");
	}
	
	$.ajax({
		  type: 'POST',
		  url: _basepath+"receipt/getPayOrderDetail.json",
		  async: false,
		  data: {
			payItemPkids:payItemPkids,
			studentPkid:yingxin.studentPkid,
			isOffline:'true',
			printFlag:'N',
			fromSchool:yingxin.fromSchool
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  var dataMap = result.dataMap;
				  if(JSON.stringify(dataMap) == "{}"){
					  layer.msg("请至少选择一条未打印的缴费记录！"); 
					  return false;
				  }
				  var isEnd = false;
				  var htmls = '';
				  var totalMoney = 0;
				  var XGR = "";
				  var KAIPIAOREN = "";
				  //循环人
				  var count = 0;//单据数量
				  var recordCount = 0;//每张单据里记录条数
				  var isFirstCount = 0;
				  var RECEIPTNO = "";
				  var paperCount = 0;
				  var RECEIPTNOArray = new Array();
				  var RECEIPTNOS = "";
				  var ORDERCREATE_MODES = "";//收款方式
				  var ORDERCREATE_MODEArray = new Array();
				  var SHOUKUANRENArray = new Array();
				  var SHOUKUANRENS = "";
				  
				 var createDate = result.sysDay;
				  var createDateArray = createDate.split("-");
					
				  for(var key in dataMap){
					    var payOrderDetailList = dataMap[key];
					    
					    //循环人拥有的所有记录
					    for (var i = 0; i < payOrderDetailList.length - 1; i++) {

					    	var payItemList = payOrderDetailList[i];
					    	
							//表头-------------------------------------------------------------------------------------------------
							if(i == 0 || i % 2  == 0){//4条数据打一张单据;border:1px solid
								count ++;
								totalMoney = 0;
								count ++;
								
								htmls += '<div class="box" style="width:179mm;height:75mm;margin:0 auto;">';//
								htmls += '<div class="chart-head" style="height:5mm;">'+
											'&nbsp;'+
										'</div>';
								htmls += '<div class="chart-body" style="margin-top:0;margin-bottom:0;height:6mm;">'+
											'<ul>'+
												'<li class="danWei" style="padding-left:63mm;margin-top:0;margin-bottom:0;">'+
													''+createDateArray[0]+'&nbsp;<span></span>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+createDateArray[1]+'<span></span>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; '+createDateArray[2]+' &nbsp;'+
												'</li>'+
												'<li class="number">&nbsp;<span>&nbsp;</span></li>'+
											'</ul>'+
										'</div>';
								htmls += '<table style="border-collapse: collapse;table-layout: fixed;" class="dataTable">'+
												'<tbody>'+
												'<tr style="height:6mm;">'+
													'<td style="width: 45mm;">&nbsp;</td>'+
													'<td colspan="2" style="width: 135mm;white-space:nowrap !important;">'+
													(payItemList.XINGMING == '' || payItemList.XINGMING == null || payItemList.XINGMING == 'null'  || typeof payItemList.XINGMING == 'undefined'
														? '' :payItemList.XINGMING)
														+(payItemList.SHENFENZHENGHAO == '' || payItemList.SHENFENZHENGHAO == null || payItemList.SHENFENZHENGHAO == 'null'  || typeof payItemList.SHENFENZHENGHAO == 'undefined'
															? '' :'-'+payItemList.SHENFENZHENGHAO)
															+(payItemList.NIANJI == '' || payItemList.NIANJI == null || payItemList.NIANJI == 'null'  || typeof payItemList.NIANJI == 'undefined'
																? '' :'-'+payItemList.NIANJI)
																+(payItemList.DEPARTMENT_NAME == '' || payItemList.DEPARTMENT_NAME == null || payItemList.DEPARTMENT_NAME == 'null'  || typeof payItemList.DEPARTMENT_NAME == 'undefined'
																	? '' :'-'+payItemList.DEPARTMENT_NAME)
																+(payItemList.CLASS_NAME == '' || payItemList.CLASS_NAME == null || payItemList.CLASS_NAME == 'null'  || typeof payItemList.CLASS_NAME == 'undefined'
																? '' :'-'+payItemList.CLASS_NAME)
																+'</td>'+
												'</tr>'+
												'<tr style="height:6mm;">'+
													'<td style="width: 45mm;">&nbsp;</td>'+
													'<td style="width: 44mm;">&nbsp;</td>'+
													'<td style="width: 90mm;">&nbsp;</td>'+
												'</tr>';
								isFirstCount ++;
							};
						  
							if(payItemList.INPUT_OUTPUT == 'JX'){
								totalMoney += payItemList.MONEY;
							}else{
								totalMoney -= payItemList.MONEY;
							}
							
							XGR = payItemList.XGR;
							KAIPIAOREN = payItemList.KAIPIAOREN;
							
							RECEIPTNOS += payItemList.RECEIPTNO;
							RECEIPTNOS += " |";
							
							if(ORDERCREATE_MODES.indexOf(payItemList.PAY_MODE) < 0){
								ORDERCREATE_MODES += payItemList.PAY_MODE;
								ORDERCREATE_MODES += "+";
							}
							
							if(payItemList.XGR != '' && payItemList.XGR != null && payItemList.XGR != 'null'){
								if(SHOUKUANRENS.indexOf(payItemList.XGR) < 0){
									SHOUKUANRENS += payItemList.XGR;
									SHOUKUANRENS += "/";
								}
							}else{
								SHOUKUANRENS += "";
							}
							//(payItemList.INPUT_OUTPUT == 'XX' ? '-' : '')+
//							var money = ""+payItemList.MONEY +"";
//							var moneyArray = splitMoney(money);
							var money = payItemList.INPUT_OUTPUT == 'JX'? payItemList.MONEY : "-"+payItemList.MONEY;
							//表中-项目记录-------------------------------------------------------------------------------------------------
							htmls += '<tr style="height:12mm;">'+
										'<td style="white-space:nowrap !important;">'+(payItemList.SCHOOL_YEAR_NAME == '' || payItemList.SCHOOL_YEAR_NAME == null || payItemList.SCHOOL_YEAR_NAME == 'null'  || typeof payItemList.SCHOOL_YEAR_NAME == 'undefined'
											? '' :payItemList.SCHOOL_YEAR_NAME)+' '+(payItemList.PAY_STYLE_NAME == '' || payItemList.PAY_STYLE_NAME == null || payItemList.PAY_STYLE_NAME == 'null'  || typeof payItemList.PAY_STYLE_NAME == 'undefined'
												? '' :payItemList.PAY_STYLE_NAME)+'</td>'+
										'<td>'+money+'</td>'+
										'<td></td>'
									'</tr>';
							recordCount ++;
							
							//表尾-------------------------------------------------------------------------------------------------
							if((i + 1) % 2  == 0){//4条数据打一张单据
								isEnd = true;
//								var totalMoneyArray = splitMoney(""+totalMoney+"");
								htmls += '<tr style="height:6mm;">'+
											'<td style="text-align: left;padding-left: 15px;">&nbsp;</td>'+
											'<td>'+totalMoney.toFixed(2)+'</td>'+
											'<td></td>'+
										'</tr>'+
										'<tr style="height:6mm;">'+
											'<td colspan="3" style="text-align: left;padding-left: 15px;white-space:nowrap !important;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; '+
											smalltoBIG(totalMoney)+'</td>'+
										'</tr>';
								htmls += '</table>'+
										'<div class="footer" style="height:6mm;margin-bottom:0;">'+
												'<ul  style="margin-top:0;margin-bottom:0;">'+
												'<li class="danWei" style="margin-top:0;margin-bottom:0;">'+
													'&nbsp;'+
												'</li>'+
												'<li class="huoBi" style="margin-top:0;margin-bottom:0;">'+
													'&nbsp;<span  id="paperSHOUKUANREN'+paperCount+'" style="white-space:nowrap !important;"></span>'+
												'</li>'+
											'</ul>'+
										'</div>';
								htmls += '</div>';
								totalMoney = 0;
								recordCount = 0;
								
								RECEIPTNOArray[paperCount] = RECEIPTNOS;
								RECEIPTNOS = "";

								ORDERCREATE_MODEArray[paperCount] = ORDERCREATE_MODES;
								ORDERCREATE_MODES = "";
								
								SHOUKUANRENArray[paperCount] = SHOUKUANRENS;
								SHOUKUANRENS = "";
								
								paperCount ++;
								
							}else{
								isEnd = false;
								
							}
					  }
					  //表尾-------------------------------------------------------------------------------------------------
					  if(!isEnd){
						  //补空行
						  for (var i = 0; i < (2 - recordCount); i++) {
							  htmls += '<tr style="height:12mm;">'+
											'<td></td>'+
											'<td></td>'+
											'<td></td>'+
										'</tr>';
						  }
//						  var totalMoneyArray = splitMoney(""+totalMoney+"");
						  htmls += '<tr style="height:6mm;">'+
										'<td style="text-align: left;padding-left: 15px;">&nbsp;</td>'+
										'<td>'+totalMoney.toFixed(2)+'</td>'+
										'<td></td>'+
									'</tr>'+
									'<tr style="height:6mm;">'+
										'<td colspan="3" style="text-align: left;padding-left: 15px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
										smalltoBIG(totalMoney)+'</td>'+
									'</tr>';
							htmls += '</table>'+
									'<div class="footer" style="height:6mm;margin-bottom:0;">'+
											'<ul  style="margin-top:0;margin-bottom:0;">'+
											'<li class="danWei" style="margin-top:0;margin-bottom:0;">'+
												'&nbsp;'+
											'</li>'+
											'<li class="huoBi" style="margin-top:0;margin-bottom:0;">'+
												'&nbsp;<span  id="paperSHOUKUANREN'+paperCount+'" style="white-space:nowrap !important;"></span>'+
											'</li>'+
										'</ul>'+
									'</div>';
						  htmls += '</div>';
						  
						  totalMoney = 0;
						  recordCount = 0;
						  XGR = "";
						  
						  RECEIPTNOArray[paperCount] = RECEIPTNOS;
						  RECEIPTNOS = "";
						  
						  ORDERCREATE_MODEArray[paperCount] = ORDERCREATE_MODES;
						  ORDERCREATE_MODES = "";
						  
						  SHOUKUANRENArray[paperCount] = SHOUKUANRENS;
						  SHOUKUANRENS = "";
							
						  paperCount ++;
					  }
					 
				}
				$("#printAreaWAIGUOYUshouju2").html(htmls);
				
				//将每张票的收据号添加到票上
				/*for(var i = 0; i < RECEIPTNOArray.length; i++){
					$("#paperRECEIPTNO"+i).html(RECEIPTNOArray[i].substr(0, RECEIPTNOArray[i].length - 1));
				}*/
				/*for(var i = 0; i < ORDERCREATE_MODEArray.length; i++){
					$("#paperORDERCREATE_MODE"+i).html(ORDERCREATE_MODEArray[i].substr(0, ORDERCREATE_MODEArray[i].length - 1));
				}*/
				//将每张票的收款人添加到票上
				for(var i = 0; i < SHOUKUANRENArray.length; i++){
					var SHOUKUANRENSS = SHOUKUANRENArray[i].substr(0, SHOUKUANRENArray[i].length - 1);
					var SHOUKUANRENSSArray = SHOUKUANRENSS.split("/");
					if(SHOUKUANRENSSArray.length > 2){
						SHOUKUANRENSS = SHOUKUANRENSSArray[0]+"/"+SHOUKUANRENSSArray[1];
					}
					$("#paperSHOUKUANREN"+i).html(SHOUKUANRENSS);
				}
				
				//打印区域
				$("#printAreaWAIGUOYUshouju").printArea();
				
				//刷新table
//				refreshReceiptPrintTable(receiptPrint.studentPkid);
				 
			  }else{
				  layer.msg("打印失败，请重试!");
			  }
			  return false;
		  }
		});
	//批打按钮显示
	yingxin.buttonStatus();
};

yingxin.buttonStatus = function(){
	if(yingxin.getIdSelections() != ''){
		$("#payBatchButton").attr("disabled",false);
		$("#payBatchButton").attr("data-target","#mymoda1");
		$("#payTuiBatchButton").attr("disabled",false);
		$("#payTuiBatchButton").attr("data-target","#mymoda1");
		$("#payPrintBatchButton").attr("disabled",false);
		$("#payPrintBatchButton").attr("data-target","#mymoda2");
		$("#printBatchButton").attr("disabled",false);
	}else{
		$("#payBatchButton").attr("data-target","#mymoda1");
		$("#payBatchButton").attr("disabled","disabled");
		$("#payTuiBatchButton").attr("data-target","#mymoda1");
		$("#payTuiBatchButton").attr("disabled","disabled");
		$("#payPrintBatchButton").attr("data-target","#mymoda2");
		$("#payPrintBatchButton").attr("disabled","disabled");
		$("#printBatchButton").attr("disabled","disabled");
	}
};
/*
 * 拆分金额
 */
var splitMoney = function(money){
	var wan = "";
	var qian = "";
	var bai = "";
	var shi = "";
	var yuan = "";
	var jiao = "";
	var fen = "";
	
	var index = money.indexOf('.');
	if(index > 0){//有小数点 110.01
		var moneyArray = money.split(".");
		if(moneyArray[0] == ""){
			moneyArray[0] = "0";
		}
		if(Number(moneyArray[0]) > 0){//1.01
			var moneyTemp = money.replace(".", "");
			var moneyResult = moneyTemp.split("");
			if(moneyResult.length == 2){
				yuan = moneyResult[0];
				jiao = moneyResult[1];
				fen = "0";
			}else if(moneyResult.length == 3){
				yuan = moneyResult[0];
				jiao = moneyResult[1];
				fen = moneyResult[2];
			}else if(moneyResult.length == 4){
				shi = moneyResult[0];
				yuan = moneyResult[1];
				jiao = moneyResult[2];
				fen = moneyResult[3];
			}else if(moneyResult.length == 5){
				bai = moneyResult[0];
				shi = moneyResult[1];
				yuan = moneyResult[2];
				jiao = moneyResult[3];
				fen = moneyResult[4];
			}else if(moneyResult.length == 6){
				qian = moneyResult[0];
				bai = moneyResult[1];
				shi = moneyResult[2];
				yuan = moneyResult[3];
				jiao = moneyResult[4];
				fen = moneyResult[5];
			}else if(moneyResult.length >= 7){
				wan = moneyResult[0];
				qian = moneyResult[1];
				bai = moneyResult[2];
				shi = moneyResult[3];
				yuan = moneyResult[4];
				jiao = moneyResult[5];
				fen = moneyResult[6];
			}
		}else{//0.01
			var moneyResult = Number(moneyArray[1]);
			if(moneyResult.length > 1){
				moneyResult = ""+moneyResult+"";
				moneyResult = moneyResult.split("");
				jiao = moneyResult[0];
				fen = moneyResult[1];
			}else{
				fen = moneyResult;
			}
		}
	}else{//没有小数点 110
		jiao = "0";
		fen = "0";
		var moneyResult = money.split("");
		if(moneyResult.length == 1){
			yuan = moneyResult[0];
		}else if(moneyResult.length == 2){
			shi = moneyResult[0];
			yuan = moneyResult[1];
		}else if(moneyResult.length == 3){
			bai = moneyResult[0];
			shi = moneyResult[1];
			yuan = moneyResult[2];
		}else if(moneyResult.length == 4){
			qian = moneyResult[0];
			bai = moneyResult[1];
			shi = moneyResult[2];
			yuan = moneyResult[3];
		}else if(moneyResult.length >= 5){
			wan = moneyResult[0];
			qian = moneyResult[1];
			bai = moneyResult[2];
			shi = moneyResult[3];
			yuan = moneyResult[4];
		}
	}
	return new Array(wan,qian,bai,shi,yuan,jiao,fen);
	
};

//点击变更子项按钮
var changeChildPayItem = function(PAY_ITEM_PKID,PAY_ITEM_PARENT_PKID,IS_INCLUDE_CHILD,PAY_ITEM_LIST_PKID){
	var studentPkid = $("#STUDENT_PKID_FLOW").val();
	var flowNodeN = yingxin.getFlowStatusN(studentPkid,"3");
	if(flowNodeN != ''){
		layer.msg("该学生尚未办理【"+flowNodeN+"】");
		return false;
	}
	$("#payitem_child_change").html("");
	$("#cost_change_html").html('<input type="text" name="" value="" readonly="readonly" class="form-control" placeholder="金额" id="cost_change"  onkeyup="clearNoNum(this)"/>');
	
	$.ajax({
		  type: 'POST',
		  url: _basepath+"receipt/getAllChildPayItemsByParentPkid.json",
		  async: false,
		  data: {
			  PARENT_ID: PAY_ITEM_PARENT_PKID
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  var payitem_pkid;
				  var ITEMLIST_CREATEMODE;
				  var flag = 0;
				  var payItemListAllChild = result.payItemListAllChild;
				  for (var i = 0; i < payItemListAllChild.length; i++) {
					  var payItem = payItemListAllChild[i];
					  
					  if(payItem.PKID != PAY_ITEM_PKID){
						  if(flag == 0){
							  payitem_pkid = payItem.PKID;
							  ITEMLIST_CREATEMODE = payItem.ITEMLIST_CREATEMODE;
							  flag++;
						  }
						  
						  $("#payitem_child_change").append("<option value='"+payItem.PKID+"' ITEMLIST_CREATEMODE = '"+payItem.ITEMLIST_CREATEMODE+"' parentId='"+payItem.PARENT_ID+"' sourcePayItemListPkid='"+PAY_ITEM_LIST_PKID+"' PAY_ITEM_PKID_YET='"+PAY_ITEM_PKID+"'>"+payItem.PAYITEM+"</option>");
					  }
				  }
				  
				  putCostHtml(payitem_pkid,ITEMLIST_CREATEMODE,$("#cost_change_html"),"cost_change",'readonly="readonly"');
				  
				  $('#mymoda8').modal({
						keyboard: true
					});
			  }
			  return false;
		  }
		});
};

//选择子项目时触发
$("#payitem_child_change").change(function(){
	  $("#cost_change_html").html('<input type="text" name="" value="" readonly="readonly" class="form-control" placeholder="金额" id="cost_change"  onkeyup="clearNoNum(this)"/>');
	  var payitem_pkid = $("#payitem_child_change  option:selected").val();
	  var ITEMLIST_CREATEMODE = $("#payitem_child_change  option:selected").attr("ITEMLIST_CREATEMODE");
	  putCostHtml(payitem_pkid,ITEMLIST_CREATEMODE,$("#cost_change_html"),"cost_change",'readonly="readonly"');
});
//给金额赋内容
var putCostHtml = function(payitem_pkid,ITEMLIST_CREATEMODE,costObj,costid,readonly){
	var studentPkid = $("#STUDENT_PKID_FLOW").val();
	 if(ITEMLIST_CREATEMODE == 1){//导入方式
		 costObj.html('<input type="text" name="" value="" class="form-control" placeholder="金额" id="'+costid+'"  '+readonly+' onkeyup="clearNoNum(this)"/>');
	  }else if(ITEMLIST_CREATEMODE == 2){//规则生成
		  
		  $.ajax({
			  type: 'POST',
			  url: _basepath+"pay/getRuleCOST.json",
			  async: false,
			  data: {
				studentPkid:studentPkid,
				PAY_ITEM_PKID:payitem_pkid
			  },
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  if(result.COST == 0){//未匹配到规则
						  costObj.html('<input type="text" name=""  class="form-control" placeholder="金额" id="'+costid+'"  onkeyup="clearNoNum(this)"/>');
					  }else{
						  costObj.html('<input type="text" name="" value="'+result.COST+'" RULE_COST_UNIT="'+result.RULE_COST_UNIT+'" class="form-control" placeholder="金额" id="'+costid+'"  '+readonly+'  onkeyup="clearNoNum(this)"/>');
					  }
				  }
				  return false;
			  }
			});
		  
	  }
};


//点击保存变更子项按钮
var submitChangePayItemButton = function(){
	var payitem_pkid_child = $("#payitem_child_change  option:selected").val();
	var payitem_pkid_parent = $("#payitem_child_change  option:selected").attr("parentId");
	var payitemlist_pkid_child_source = $("#payitem_child_change  option:selected").attr("sourcePayItemListPkid");
	var RULE_COST_UNIT = $("#cost_change").attr("RULE_COST_UNIT");
	var PAY_ITEM_PKID_YET = $("#payitem_child_change  option:selected").attr("PAY_ITEM_PKID_YET");
	var cost = $("#cost_change").val();
	yingxin.studentPkid = $("#STUDENT_PKID_FLOW").val();
	if(yingxin.studentPkid == ''){
		layer.msg("未知学生！");
		return false;
	}
	if(typeof payitem_pkid_child == 'undefined' || payitem_pkid_child == ''){
		layer.msg("请先选择缴费项目！");
		return false;
	}
	
	if(typeof payitem_pkid_parent == 'undefined' || payitem_pkid_parent == ''){
		layer.msg("非法项目！");
		return false;
	}
	
	if(typeof cost == 'undefined' || cost == ''){
		layer.msg("费用不能为空！");
		return false;
	}else if(Number(cost) <= 0){
		layer.msg("费用必须大于0！");
		return false;
	}
	
	$.ajax({
		  type: 'POST',
		  url: _basepath+"pay/changeChildPayItemList.json",
		  async: false,
		  data: {
			payitem_pkid_parent:payitem_pkid_parent,
			payitem_pkid_child:payitem_pkid_child,
			COST:cost,
			studentPkid:yingxin.studentPkid,
			payitemlist_pkid_child_source:payitemlist_pkid_child_source,
			RULE_COST_UNIT:RULE_COST_UNIT,
			PAY_ITEM_PKID_YET:PAY_ITEM_PKID_YET
		  },
		  dataType : "json",
		  success : function(result) {
			  if(result.result == 'success'){
				  layer.msg("变更成功！");
				  $("#cancelChangePayItemButton").click();
				  yingxin.getStatisticsInfo();
				  //刷新页面
				  var status = $("#statusSelected").find(".active").attr("text");
				  //分寝时调整子项刷新
				  yingxin.fenqinFlowContent(yingxin.studentPkid);
				 yingxin.refreshofflinePayTable(yingxin.studentPkid,status);
			  }else{
				  layer.msg(result.failMsg);
			  }
			  return false;
		  }
		});
	
};

//一卡通发卡按钮
$('#opencardbtn').click(function(){
	
	var studentPkid = $("#STUDENT_PKID_FLOW").val();
	var sfzhm=$("#SHENFENZHENGHAO").html();
	
	var url=_basepath+"yingxin/faka.json";
	$.post(url,{PKID:studentPkid},function(result){
		if(result.result == 'success'){
			layer.msg("发卡成功！");
			var studentPkid = $("#STUDENT_PKID_FLOW").val();;
			yingxin.ecardOpenCardFlowContent(studentPkid);
			yingxin.getStatisticsInfo(studentPkid);
			return false;
		}else{
			layer.msg("发卡失败，请重试！");
			return false;
		}
		 
	});
	
});

