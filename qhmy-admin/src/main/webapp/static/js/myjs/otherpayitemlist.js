
//@ sourceURL=otherpayitemlist.js
//其它缴费项设置页面js
var op={};
//新增
$("#addone").click(function(){
	op.add();
});
//新增方法
op.add=function(){
	$('#mymodal').modal({
		keyboard: true
	});
};
//点击取消按钮触发事件
$("#btn_cancel").click(function(){
	$("#xmmc").val("");
	$("#gbzy").attr("checked",false);
	$("#gbjfxm").attr("checked",false);
});
//保存方法
op.save=function(){
	//项目名称
	var PAYITEM=$("#xmmc").val();
	//改变专业
	var UPDATEPROFESSION=$('#gbzy')[0].checked==true?"Y":"N";
	//改变缴费项目
	var UPDATEPAYITEM=$("#gbjfxm")[0].checked==true?"Y":"N";
	var url=_basepath+"pay/save.json";
	$.post(url,{PAYITEM:PAYITEM,UPDATEPROFESSION:UPDATEPROFESSION,UPDATEPAYITEM:UPDATEPAYITEM},function(data){
		if(data.result=="success"){
			layer.msg("保存成功!");
			$("#btn_cancel").click();
			$("#otherpaytable").bootstrapTable('refresh', {url: _basepath+"pay/table.json"});
		}
	});
};
//编辑保存
op.save_edit=function(){
	//pkid
	var PKID=$("#pkid").val();
	//项目名称
	var PAYITEM=$("#xmmc2").val();
	var reg=/^\s+$/g;
	if (PAYITEM.length == 0 || reg.test(PAYITEM)) {
		layer.msg("缴费项名称不能为空！");
		return false;
	}
	//改变专业
	var UPDATEPROFESSION=$('#gbzy2')[0].checked==true?"Y":"N";
	//改变缴费项目
	var UPDATEPAYITEM=$("#gbjfxm2")[0].checked==true?"Y":"N";
	var url=_basepath+"pay/updateotherpayitemset.json";
	$.post(url,{PAYITEM:PAYITEM,UPDATEPROFESSION:UPDATEPROFESSION,UPDATEPAYITEM:UPDATEPAYITEM,PKID:PKID},function(data){
		if(data.result=="success"){
			layer.msg("保存成功!");
			$("#btn_cancel2").click();
			 $("#otherpaytable").bootstrapTable('refresh', {url: _basepath+"pay/table.json"});
		}
	});
};

//获取list表格数据
op.getTab=function () {
	// 引例四
	var url = _basepath+"pay/table.json";

	$('#otherpaytable').bootstrapTable(
					{
						url : url,//数据源
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
								searchText : this.searchText,
								sortName : this.sortName,
								sortOrder : this.sortOrder,
								pageindex : this.pageNumber
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
									field: 'PAYITEM',//可不加  
									title:'缴费项目',
									align : "center",
									halign : 'center'
								},
								{
									field : 'BGXLX',
									title:'变更项类型',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'UPDATEPROFESSION',
									title:'改变专业',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										if(row.BGXLX=="自定义"){
											if(row.UPDATEPROFESSION=='Y'){
												return ['<input type="checkbox" class="al-toggle-button dj"checked="false" >' ].join('');
											}else{
												return ['<input type="checkbox" class="al-toggle-button dj" >' ].join('');
											}
										}else{
											if(row.UPDATEPROFESSION=='Y'){
												return ['<input type="checkbox" class="al-toggle-button dj"checked="false" disabled="true">' ].join('');
											}else{
												return ['<input type="checkbox" class="al-toggle-button dj" disabled="true">' ].join('');
											}
										}
										
									},
									 events : {
											'click .dj' : function(e,value, row, index) {
												var UPDATEPROFESSION=$(this)[0].checked==true?"Y":"N";
												$.get(_basepath+"pay/updateotherpayitemset.json?UPDATEPROFESSION="+UPDATEPROFESSION+"&PKID="+row.PKID,function(data){
													if(data.result=="success"){
														layer.msg("修改成功!");
														 $("#otherpaytable").bootstrapTable('refresh', {url:url});
													}
												});
											}
									 }
								},
								{
									field : 'UPDATEPAYITEM',
									title : '改变缴费项目',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										if(row.BGXLX=="自定义"){
											if(row.UPDATEPAYITEM=='Y'){
												return ['<input type="checkbox" class="al-toggle-button dj"checked="false">' ].join('');
											}else{
												return ['<input type="checkbox" class="al-toggle-button dj" >' ].join('');
											}
										}else{
											if(row.UPDATEPAYITEM=='Y'){
												return ['<input type="checkbox" class="al-toggle-button dj"checked="false" disabled="true">' ].join('');
											}else{
												return ['<input type="checkbox" class="al-toggle-button dj" disabled="true">' ].join('');
											}
										}
										
									},
									 events : {
											'click .dj' : function(e,value, row, index) {
												var UPDATEPAYITEM=$(this)[0].checked==true?"Y":"N";
												$.get(_basepath+"pay/updateotherpayitemset.json?UPDATEPAYITEM="+UPDATEPAYITEM+"&PKID="+row.PKID,function(data){
													if(data.result=="success"){
														layer.msg("修改成功!");
														 $("#otherpaytable").bootstrapTable('refresh', {url:url});
													}
												});
											}
									 }
								},
								{
									field: 'XGR',//可不加  
									title : '操作人',//标题  可不加
									align : "center",
									halign : 'center'
								},
								{
									field : 'XGSJ',
									title : '操作时间',
									align : "center",
									halign : 'center',
									sortable : false
								},
								
								{
									field : 'opt',
									title : '操作',
									align : "center",
									halign : 'center',
									formatter : function(value, row,
											index) {
										return [
												'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;',
												'<a class="del ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>' ]
												.join('');
									}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
									 events : {
										'click .edit' : function(e,value, row, index) {
											if(row.BGXLX!="自定义"){
									    		layer.msg("系统自带的缴费项不能编辑!");
									    		return false;
									    	}
											$('#xmmc2').val(row.PAYITEM);
											$('#pkid').val(row.PKID);
											if(row.UPDATEPROFESSION=="Y"){
												$("#gbzy2")[0].checked=true;
											}else{
												$("#gbzy2")[0].checked=false;
											}
											
											if(row.UPDATEPAYITEM=="Y"){
												$("#gbjfxm2")[0].checked=true;
											}else{
												$("#gbjfxm2")[0].checked=false;
											}
											
											  //编辑  编辑人员
											$('#mymodal2').modal({
												keyboard: true
											});
    							        
										},

										'click .del' : function(e,value, row, index) {
											if(row.BGXLX!="自定义"){
									    		layer.msg("系统自带的缴费项不能删除!")
									    		return false;
									    	}
											BootstrapDialog.show({  //显示需要提交的表单。
								            	title:'提示信息',	
								            message: '你确定要删除这条记录吗？',
								            closable: true, 
								              buttons: [{
											    label: '确定',
											    cssClass: 'btn-danger',
											    action: function(dialogRef){
											    	 $.ajax({
															type:"post",
															dataType:"json",
															url:_basepath+'pay/delsingledata.json?PKID='+row.PKID,
															 success:function(data){
																 if("success" == data.result){
																	 layer.msg("删除成功！");
																	 //删除成功后刷新表格
																	  $("#otherpaytable").bootstrapTable('refresh', {url:url});
																 }
															  },
															error: function (XMLHttpRequest, textStatus, errorThrown) { 
															          alert(errorThrown);
															       }
															});
											    	
											    	
								                dialogRef.close();
								                }
										  }, {
										    label: '取消',
										    cssClass: 'btn-default',
										    action: function(dialogRef){
										       dialogRef.close();
										    }
										  }
										  ]
								            });
											
											
										    
			 
										},
											} 
								}
						],
					});
    $('#otherpaytable').bootstrapTable('hideColumn', 'PKID');//隱藏列
};

//点击保存
$('#btn_save').click(function(){
	op.save();
});

//点击保存
$('#btn_save2').click(function(){
	op.save_edit();
});

$(function(){
	op.getTab();
});
