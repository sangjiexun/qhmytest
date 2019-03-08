//@ sourceURL=yxlc_list.js 
/**
 * 学生信息页面对象
 */
//获得按钮权限
var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
(function($, window) {
	var yxlc = {};

	
	//查询
	$('#btn_search').click(function(){
		//所属组织
		$("#yxlctable").bootstrapTable('destroy'); 
		 yxlc.getTab();
		
	});
	
	//获取list表格数据
	yxlc.getTab=function () {
		//获得按钮权限
		// 引例四
		var url = _basepath+"yxlc/table.json";

		$('#yxlctable').bootstrapTable(
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
							showColumns : false, //显示下拉框勾选要显示的列  
							showRefresh : false, //显示刷新按钮  --只是前台刷新，个人觉得没什么用
							minimumCountColumns : 2, //最少允许的列数
							sidePagination : "server", //表示服务端请求  
							totalRows : 0, // server side need to set
							singleSelect : false,
							clickToSelect : true,
							onDblClickRow:function(row){
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
										field: 'PKID',//可不加  
										align : "center",
										halign : 'center'
									},
									{
										field : 'LC_NAME',
										title:'迎新流程名称',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'LC_ORDER',
										title:'排序',
										align : "center",
										halign : 'center',
										sortable : false,
										editable: {
						                    type: 'text',
						                    title: '排序',
						                    validate: function (v) {
						                        if (!v) return '排序不能为空';
						                        var reg = /^[0-9]\d*$/;
						                        if(!reg.test(v)) return '只能为正整数'

						                    }
						                }
									},
									{
										field : 'IS_USE',
										title : '是否启用',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter : function(value, row,
												index) {
											if(SESSION_MENU_BUTTONS.yxlc_qy==1){
													if(row.IS_USED=='Y'){
														return ['<input type="checkbox" class="al-toggle-button dj"checked="false" >' ].join('');
													}else{
														return ['<input type="checkbox" class="al-toggle-button dj" >' ].join('');
													}
											}
											
										},
										 events : {
												'click .dj' : function(e,value, row, index) {
													var is_use=$(this)[0].checked==true?"Y":"N";
													$.post(_basepath+"yxlc/updateUsed.json",{"PKID":row.PKID,"IS_USED":is_use
													},function(data){
														if(data.result=="success"){
															layer.msg("修改成功!");
															 $("#yxlctable").bootstrapTable('refresh', {url:url});
														}
													});
												}
										 }
									}
							],
							onEditableSave: function (field, row, oldValue, $el) {
				                $.ajax({
				                    type: "post",
				                    url: _basepath+"yxlc/updatepaixu.json",
				                    data: {"PKID":row.PKID,"LC_ORDER":row.LC_ORDER},
				                    dataType: 'JSON',
				                    success: function (data, status) {
				                        if (status == "success") {
				                        	
				                        	$("#yxlctable").bootstrapTable('refresh', {url:url});
				                        	layer.msg('保存成功');
				                        }
				                    },
				                    error: function () {
				                        layer.msg('编辑失败');
				                    },
				                    complete: function () {

				                    }

				                });
				            }
						});
	    $('#yxlctable').bootstrapTable('hideColumn', 'PKID');//隱藏列
	    
	    if($("#COLLEGES_NAME_EN").val() != 'GUANGDIAN'){
	    	$('#yxlctable').bootstrapTable('hideColumn', 'BILL_TYPE');//隱藏列
	    }
	};
	//加载表格数据
	yxlc.getTab();
})(jQuery, window);
