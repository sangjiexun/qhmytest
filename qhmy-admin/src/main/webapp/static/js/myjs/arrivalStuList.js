//@ sourceURL=stuinfo.js 
/**
 * 学生信息页面对象
 */
(function($, window) {

	var arrivalstu = {};
	
	//查询
	$('#btn_search').click(function(){
		//所属组织
		$("#arrivalstutable").bootstrapTable('destroy'); 
		arrivalstu.getTab1();
		
	});
	
	//获取list表格数据
	arrivalstu.getTab1=function () {
		// 引例四
		var url = _basepath+"verify/getveritystulist.json";

		$('#arrivalstutable').bootstrapTable(
						{
							method: 'post',  
							url : url,//数据源
							dataType: "json",
							contentType:"application/x-www-form-urlencoded; charset=UTF-8",
							dataField : "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
							totalField : 'total',
							sortOrder : 'desc',
							striped : false, //表格显示条纹  
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
									limit : params.limit, //页面大小
									seText : $('#search').val(),									
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
										field: 'PKID',//可不加  
										align : "center",
										halign : 'center'
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
										field : 'ZUZHINAME',
										align : "center",
										halign : 'center',
										title : '院校专业',
										width:'150px'

									}
							],
						});
		
	    $('#arrivalstutable').bootstrapTable('hideColumn', 'PKID');//隱藏列hideAllColumns	   
	};
	//获取list表格数据
	arrivalstu.getTab=function () {
		// 引例四
		var url = _basepath+"payment/getarrivalstu.json";
		
		$('#arrivalstutable').bootstrapTable(
				{
					
					ajax : function (request) {
						$.ajax({
						 	type : "post",
				            url : url,
				            contentType:"application/x-www-form-urlencoded; charset=UTF-8",
							dataType:"json",
							data:'',
							jsonp:'callback',
				            success : function (msg) {			
								request.success({
				                    row : msg.rows
				                });
				                $('#arrivalstutable').bootstrapTable('load', msg);
				            },
							error:function(){
								alert("加载失败");
							}
						});
					},					
					dataField : "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
					totalField : 'total',
					sortOrder : 'desc',
					striped : false, //表格显示条纹  
					/*pagination : true, //启动分页  
					pageNumber : 1, //当前第几页  
					pageSize : 20, //每页显示的记录数  
					pageList : [1,5,10,20,30,50,100], //记录数可选列表  
*/					search : false, //是否启用查询  
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
								limit : params.limit, //页面大小
								seText : $('#search').val(),									
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
					        	   field : 'XINGMING',
					        	   title:'姓名',
					        	   align : "center",
					        	   halign : 'center'
					           },
					           {
					        	   field : 'ZUZHINAME',
					        	   align : "center",
					        	   halign : 'center',
					        	   title : '院校专业'
					        		   
					           }
					           ],
				});
	};
	
	//加载表格数据
	arrivalstu.getTab();
	
	$("#xiangqing").click(function(){
		/*$("#arrivalstutable").bootstrapTable('destroy'); 
		verifystu.getTab();*/
		/*window.location.href=encodeURI(_basepath+'verify/verifystu_list.php');*/
		/*alert(2222);*/
		$(".jf_szright").load(_basepath+ 'payment/arrivalstu_list.php');
	});
	
})(jQuery, window);

