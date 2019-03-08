//@ sourceURL=payexamine.js 
/**
 * 缴费审核页面js
 */
(function($, window) {
	var jfshenhe = {};
	
	laydate.render({
		elem:'#RIQIqi',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	laydate.render({
		elem:'#RIQIzhi',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	
	// 去新增页面
	jfshenhe.goAddStu = function() {
		$(".jf_szright").load(_basepath + "stuinfo/goAdd.json");
	};
	
	//获取选中的行
	jfshenhe.getIdSelections=function () {
			var $table = $('#jfshenheinfotable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.PKID;
        });
	};
	//批量删除方法
	jfshenhe.batchDel=function(e){
		$.post(_basepath+"stuinfo/batchDel.json?pkids="+e,function(data){
			if(data.result=="success"){
				layer.msg("删除成功!");
				 $("#jfshenheinfotable").bootstrapTable('refresh', {url: _basepath+"pay/getjfshtable.json"});
			}
		});
	};
	
	$("#pay_style_sh").change(function(){
		var SCHOOL_YEAR_PKID = $("#school_year_sh").val();
		var PAY_TYPE_PKID = $("#pay_style_sh").val();
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
	
	$("#school_year_sh").change(function(){
		var SCHOOL_YEAR_PKID = $("#school_year_sh").val();
		var PAY_TYPE_PKID = $("#pay_style_sh").val();
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
	
	//批量通过方法  batchNo
	jfshenhe.batchGree=function(e){
		$.post(_basepath+"pay/batchGree.json?pkids="+e,function(data){
			if(data.result=="success"){
				layer.msg("修改成功!");
				 $("#jfshenheinfotable").bootstrapTable('refresh', {url: _basepath+"pay/getjfshtable.json"});
			}
		});
	};
	
	
	//批量不通过方法  
	jfshenhe.batchNo=function(e){
		$.post(_basepath+"pay/batchNo.json?pkids="+e,function(data){
			if(data.result=="success"){
				layer.msg("修改成功!");
				 $("#jfshenheinfotable").bootstrapTable('refresh', {url: _basepath+"pay/getjfshtable.json"});
			}
		});
	};
	
	
	//新增按钮绑定单机事件
	$('#btn_add').bind('click',function(){
		jfshenhe.goAddStu();
	});
	
	//查询
	$('#btn_search').click(function(){
		//选择的日期
		var RIQIqi=$('#RIQIqi').val();
		//选择的日期
		var RIQIzhi=$('#RIQIzhi').val();
		var date1 = new Date(Date.parse(RIQIqi));
		var date2 = new Date(Date.parse(RIQIzhi));
		if(date1>date2){
			layer.msg("结束日期不能早于开始日期");
			return false;
		}
		//审核状态
		var shstatus=$('#shstatus').val();
		//项目名称
		var xmmc=$('#orgtree2').val();
		 var opt = {
				 url :_basepath+"pay/getjfshtable.json",
 			    silent: true,
 			    query:{
 			    	"RQQ":RIQIqi,
 			    	"RQZ":RIQIzhi,
 			    	"STATUS":shstatus,
 			    	"XMMC":xmmc
 			    }
 			  };
		
		 $("#jfshenheinfotable").bootstrapTable('refresh', opt);
		
	});
	
	//通过按钮的批量
	$('#btn_tongguo').click(function(){
		var pkids=jfshenhe.getIdSelections();
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			  BootstrapDialog.show({  //显示需要提交的表单。
				  title:'提示信息',	
      message: '请选择一条数据!',
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
		jfshenhe.batchGree(pkids);
	});
	
	
	//不通过按钮的批量
	$('#btn_btongguo').click(function(){
		var pkids=jfshenhe.getIdSelections();
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			  BootstrapDialog.show({  //显示需要提交的表单。
				  title:'提示信息',	
      message: '请选择一条数据!',
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
		jfshenhe.batchNo(pkids);
	});
	
	
	
	//批量删除按钮绑定单机事件
	$('#btn_del').bind('click',function(){

		var pkids=jfshenhe.getIdSelections();
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			  BootstrapDialog.show({  //显示需要提交的表单。
				  title:'提示信息',	
        message: '请选择一条数据!',
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
          message: '你确定要删除记录吗？',
          closable: false, 
            buttons: [{
			    label: '确定',
			    cssClass: 'btn-danger',
			    action: function(dialogRef){
			    jfshenhe.batchDel(pkids);
              dialogRef.close();
              }
		  }, {
		    label: '关闭',
		    cssClass: 'btn-default',
		    action: function(dialogRef){
		       dialogRef.close();
		    }
		  }
		  ]
          });
		
	
	});
	//获取list表格数据
	jfshenhe.getTab=function () {
		// 引例四
		var url = _basepath+"pay/getjfshtable.json";

		$('#jfshenheinfotable').bootstrapTable(
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
							showColumns : true, //显示下拉框勾选要显示的列  
							showRefresh : false, //显示刷新按钮  --只是前台刷新，个人觉得没什么用
							minimumCountColumns : 2, //最少允许的列数
							sidePagination : "server", //表示服务端请求  
							totalRows : 0, // server side need to set
							singleSelect : false,
							clickToSelect : true,
							onColumnSwitch: function(field,checked){
								//获取全部可显示的节点信息集合
								var resultColumnList = $('#jfshenheinfotable').bootstrapTable('getVisibleColumns');
								var resultColumns = "";
								//将所有field组合成以逗号隔开的字符串
								$.each(resultColumnList, function(i, column) {
									if(column.field=='0'/*||column.field=='opt'*/){
										return;
									}else{
										resultColumns+=column.field+",";
									}									
						     	});
								//更新可查看字段内容
								$.post("stuinfo/updateShowCols.json?table_name=JFSH_LIST",{table_show_cols:resultColumns},function(data){
									if(data.result=="success"){

									}
								})
							},
							onDblClickRow:function(row){
							},
							queryParams : function getParams(params) {
								var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
										
										
									PAY_TYPE_PKID : $("#pay_style_sh").val(),
									SCHOOL_YEAR_PKID : $("#school_year_sh").val(),
									RQQ : $('#RIQIqi').val(),
									RQZ:$('#RIQIzhi').val(),
									STATUS:$('#shstatus').val(),
									XMMC:$('#orgtree2').val(),
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
										title : "全选",//标题
										align : "center",//水平
										checkbox : true,
										valign : "middle",//垂直
										formatter:function(value, row, index){
											   // 根据row.列名   那状态确定返回 true/false
											if(row.ZT=='审核成功' || row.ZT=='审核失败' || row.ZT=='已结束' || row.ZT=='已过期' ){
												  return {
														 disabled : true
														   };
											}
										 }
									},
									{
										field: 'PKID',//可不加  
										align : "center",
										halign : 'center'
									},
									{
										field: 'ITEMLIST_CREATEMODE',//可不加  
										align : "center",
										halign : 'center'
									},
									{
										field : 'PAY_STYLE_NAME',
										title:'缴费类型',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'SCHOOL_YEAR_NAME',
										title:'学年',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'PAYITEM',
										title:'缴费项目',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'RS',
										title : '人数',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'ZJE',
										title : '总金额',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter : function(value, row,
												index) {
												return [
														row.ZJE.toFixed(2)+'' ]
														.join('');
										}
									},
									{
										field : 'YHRS',
										title : '优惠人数',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'YHJE',
										title : '优惠金额',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter : function(value, row,
												index) {
												return [
														row.YHJE.toFixed(2)+'' ]
														.join('');
										}
									},
									{
										field : 'YSZJE',
										title : '应收总金额',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter : function(value, row,
												index) {
												return [
														row.YSZJE.toFixed(2)+'' ]
														.join('');
										}
									},
									{
										field : 'YXQ',
										title : '有效期',
										align : "center",
										halign : 'center',
										sortable : false

									},
									{
										field : 'BXJF',
										title : '是否必须交费',
										align : "center",
										halign : 'center',
										sortable : false

									},
									{
										field : 'ZT',
										title : '审核状态',
										align : "center",
										halign : 'center',
										sortable : false,
										formatter : function(value, row,
												index) {
											if(value == '审核成功'){
												return ['<span class="label label-success">审核成功</span>'].join('');
											}else if(value == '待审核'){
												return ['<span class="label label-info">待审核</span>'].join('');
											}else if(value == '审核失败'){
												return ['<span class="label label-danger">审核失败</span>'].join('');
											}else if(value == '已结束'){
												return ['<span class="label label-default">已结束</span>'].join('');
											}else if(value == '已过期'){
												return ['<span class="label label-warning">已过期</span>'].join('');
											}
										}

									},
									
									{
										field : 'opt',
										title : '操作',
										align : "center",
										halign : 'center',
										formatter : function(value, row,
												index) {
											return [
											      //  '<td class="jf_Operation"><span title="查看详情" class="fa fa-search"></span></td>'
											        //.join('');
											        
													'<span title="查看详情" class="fa fa-search xiangqing"></span>' ]
													.join('');
										}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										 events : {
											'click .xiangqing' : function(e,value, row, index) {
												  //编辑  编辑人员
												  $(".jf_szright").load(_basepath+'pay/goShow.json?PKID='+row.PKID+'&ZT='+row.ZT+'&ITEMLIST_CREATEMODE='+row.ITEMLIST_CREATEMODE);
	    							        
											},
												} 
									}
							],
						});
	    $('#jfshenheinfotable').bootstrapTable('hideColumn', 'PKID');//隱藏列
	    $('#jfshenheinfotable').bootstrapTable('hideColumn', 'ITEMLIST_CREATEMODE');//隱藏列
		if(colStr!=null && colStr!=''){
			var resultColumnList = $('#jfshenheinfotable').bootstrapTable('getVisibleColumns');
            //隐藏全部
			$.each(resultColumnList, function(i, column) {
				if(column.field=='0'/*||column.field=='opt'*/){
					return;
				}
				 $('#jfshenheinfotable').bootstrapTable('hideColumn', column.field);
	     	});
			//展示数据库查询出来的列
			var array=colStr.split(",");
			for(var i=0;i<array.length;i++){
				var field=array[i];
				if(field==''){
					continue;
				}
				$('#jfshenheinfotable').bootstrapTable('showColumn', field);
				
			}
			
		}
	};
	//加载表格数据
	jfshenhe.getTab();
})(jQuery, window);
