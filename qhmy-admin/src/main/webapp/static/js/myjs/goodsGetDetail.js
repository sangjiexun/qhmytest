/**
 * 物品领取明细对象
 */
//@sourceURL=goodsGetDetail.js
(function($, window) {
	var goodsGetDetail = {};
	
	$("#NIANJI").bind("change", function() {
		changeGrade();
	});
	
	//查询按钮点击事件
	$('#btn_search').click(function(){
		$("#goodsGetDetailTable").bootstrapTable('destroy'); 
		goodsGetDetail.getTab();
	});
	
	//导出按钮点击事件
	$("#btn_export").click(function(){
		//选择的入学年份
		var NIANJI=$("#NIANJI").val();
		//院校专业pkids
		var orgtree=$("#orgtree").val();
		//缴费类型
		var paystyle = $("#paystyle2").val() == ""? $("#paystyle").val() : $("#paystyle2").val();
		//学年
		var xuenian = $("#xuenian2").val() == ""? $("#xuenian").val() : $("#xuenian2").val();
		//缴费项目
		var payitem = $("#payitem2").val() == ""? $("#payitem").val() : $("#payitem2").val();
		//关键词
		var keywords = $("#keywords").val();
		//子项
		var childitem = $("#childitem").val();
		
		window.location.href=encodeURI(_basepath+'goodsGetDetail/goodsGetDetailExcel.json?NIANJI='+NIANJI
				+"&&orgtree="+orgtree+"&&keywords="+keywords+"&xuenian="+xuenian+
				"&payitem="+payitem+"&paystyle="+paystyle+"&childitem="+childitem
				);
	});
	
	$("#paystyle").change(function(){
		var SCHOOL_YEAR_PKID = $("#xuenian").val();
		var PAY_TYPE_PKID = $("#paystyle").val();
		var zNodes2;
		var setting2 = {
				check: {
					enable: true,
					chkStyle: "checkbox",
					chkboxType: { "Y": "s", "N": "ps" }
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: onClick2,
					onCheck: onCheck2
				}
		};
		$.ajax({
			  type: 'POST',
			  url: _basepath+"receipt/getAllPayItemListList.json",
			  async: false,
			  data: {SCHOOL_YEAR_PKID:SCHOOL_YEAR_PKID,PAY_TYPE_PKID:PAY_TYPE_PKID},
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  zNodes2 = result.payItemListResult;
				  }
				  return false;
			  }
		});
		zNodes2 = eval(zNodes2);
		$.fn.zTree.init($("#treeDemo2"), setting2, zNodes2);
	});

	$("#xuenian").change(function(){
		var SCHOOL_YEAR_PKID = $("#xuenian").val();
		var PAY_TYPE_PKID = $("#paystyle").val();
		var zNodes2;
		var setting2 = {
				check: {
					enable: true,
					chkStyle: "checkbox",
					chkboxType: { "Y": "s", "N": "ps" }
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: onClick2,
					onCheck: onCheck2
				}
		};
		$.ajax({
			  type: 'POST',
			  url: _basepath+"receipt/getAllPayItemListList.json",
			  async: false,
			  data: {SCHOOL_YEAR_PKID:SCHOOL_YEAR_PKID,PAY_TYPE_PKID:PAY_TYPE_PKID},
			  dataType : "json",
			  success : function(result) {
				  if(result.result == 'success'){
					  zNodes2 = result.payItemListResult;
				  }
				  return false;
			  }
		});
		zNodes2 = eval(zNodes2);
		$.fn.zTree.init($("#treeDemo2"), setting2, zNodes2);
	});
	
	$("#back_tohuizong").click(function(){
		$(".jf_szright").load(_basepath+ "report/toAllGoods.php");
	});
	
	// 获取bootStrapTable表格数据,及参数设置
	goodsGetDetail.getTab = function() {
		//获得按钮权限
		var url = _basepath + "goodsGetDetail/goodsGetDetailListPage.json";
		$('#goodsGetDetailTable').bootstrapTable(
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
							clickToSelect : true,
							onDblClickRow : function(row) {
							},
							onLoadSuccess:function(data){
					            $('.bootstrap-table tr td:nth-child(11)').each(function () {
					            	var str = $(this).text();
					            	if(str.length>10){
					            		$(this).text(str.substr(0,10)+"...");
					            	}
					                $(this).attr("title", str);
					                $(this).css("cursor", 'pointer');
					            });
					        },
							queryParams : function getParams(params) {
								//选择的入学年份
								var NIANJI=$("#NIANJI").val();
								//院校专业pkids
								var orgtree=$("#orgtree").val();
								//缴费类型
								var paystyle = $("#paystyle2").val() == ""? $("#paystyle").val() : $("#paystyle2").val();
								//学年
								var xuenian = $("#xuenian2").val() == ""? $("#xuenian").val() : $("#xuenian2").val();
								//缴费项目
								var payitem = $("#payitem2").val() == ""? $("#payitem").val() : $("#payitem2").val();
								//关键词
								var keywords = $("#keywords").val();
								//子项
								var childitem = $("#childitem").val();
								
								var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, // 页面大小
									keywords : keywords,
									NIANJI : NIANJI,
									orgtree : orgtree,
									xuenian:xuenian,
									payitem:payitem,
									paystyle:paystyle,
									childitem:childitem,
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
										field : 'SCHOOL_YEAR_NAME',
										title : '学年',
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
										field : 'PAYITEM',
										title : '缴费项目',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'PAYITEM_CHILD',
										title : '子项目',
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
										field : 'XUEHAO',
										title : '学号',
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
										field : 'NIANJI',
										title : '年级',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'ZHUANYE',
										title : '专业',
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
										field : 'ROOM_NAME',
										title : '宿舍',
										align : "center",
										halign : 'center',
										sortable : false,

									},
									
									{
										field : 'OPT_DATE',
										title:'领取时间',
										align : "center",
										halign : 'center',
										sortable : false,
									},
									{
										field : 'OPT_NAME',
										title : '操作人',
										align : "center",
										halign : 'center',
										sortable : false

									}
									],
						});
	};
	//加载表格数据
	goodsGetDetail.getTab();
})(jQuery, window);
