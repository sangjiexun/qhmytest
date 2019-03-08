//@ sourceURL=stuPayDetail.js
(function($, window) {
	var stuPayDetail = {};
	
	$("#NIANJI").bind("change", function() {
		changeGrade();
	});
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		$("#getAllGoodsTable").bootstrapTable('destroy'); 
		stuPayDetail.getTab();
	});
	//导出按钮点击事件
	$("#checkOut").click(function(){
		var YESBIANMA=$("#pay_style_mx").val();
		var STYBIANMA=$("#school_year_mx").val();
		window.location.href=encodeURI(_basepath+'report/allGoodsDetailExcel.json?YESBIANMA='+YESBIANMA
				+"&&STYBIANMA="+STYBIANMA
				);
	});
	
	stuPayDetail.chankan = function(YSPKID,PYPKID,SYPKID,ITPKID){
		$(".jf_szright").load(_basepath+'goodsGetDetail/toGoodsGetDetail.php?xuenian='+YSPKID
				+"&&paystyle="+SYPKID+"&&payitem="+PYPKID+"&&childitem="+ITPKID+"&&btu_type=1");
	}
	
	// 获取bootStrapTable表格数据,及参数设置
	stuPayDetail.getTab = function() {
		var PageSizeDel=20;
		var PageClassHt=$(".page-size").html();
		if(PageClassHt!=""&&PageClassHt!=null){
			PageSizeDel=PageClassHt;
		}
		var url = _basepath + "report/getAllGoodsSum.json";
		$('#getAllGoodsTable').bootstrapTable(
				{
					url : url,// 数据源
					method: 'post',  
					dataField : "rows",// 服务端返回数据键值
					dataType: "json",					// 就是说记录放的键值是rows，分页时使用总记录数的键值为total
					totalField : 'total',
					contentType:"application/x-www-form-urlencoded; charset=UTF-8",
					sortOrder : 'desc',
					striped : true, // 表格显示条纹
					pagination : true, // 启动分页
					pageNumber : 1, // 当前第几页
					pageSize : PageSizeDel, // 每页显示的记录数
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
					queryParams : function getParams(params) {
						var YESBIANMA=$("#pay_style_mx").val();
						var STYBIANMA=$("#school_year_mx").val();
						
						
						var temp = {
							limit : params.limit, // 页面大小
							searchText : this.searchText,
							sortName : this.sortName,
							sortOrder : this.sortOrder,
							pageindex : this.pageNumber,
							YESBIANMA:YESBIANMA,
							STYBIANMA:STYBIANMA,
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
								field : 'ZIXIANG',
								title : '子项目',
								align : "center",
								halign : 'center',
								sortable : false
							},
							{
								field : 'MAXPEOPLE',
								title : '购买人数',
								align : "center",
								halign : 'center',
								sortable : false
							},
							{
								field : 'LASTPEOPLE',
								title : '领取人数',
								align : "center",
								halign : 'center',
								sortable : false
							},{
								 title: "操作",
					              align: 'center',
					              valign: 'middle',
					              width: 160, // 定义列的宽度，单位为像素px
					              formatter: function (value, row, index) {
					            		    return '<a class="edit ml10" href="javascript:void(0)" title="修改">查看明细</a>'
							                 ;
					            	  
					              
					              },events:{
					            	  'click .edit' : function(e,value, row, index) {
										  //编辑  编辑人员
					            		
					            		  stuPayDetail.chankan(row.YSPKID,row.PYPKID,row.SYPKID,row.ITPKID);
											
							        
									}
					              }
								
							}

					],
						});
	};
	//加载表格数据
	stuPayDetail.getTab();
})(jQuery, window);
