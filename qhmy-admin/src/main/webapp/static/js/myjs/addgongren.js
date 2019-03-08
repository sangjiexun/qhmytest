//@ sourceURL=addgongren.js 
var selectedCount=$('#renyuantable').find('tr').length-1;
var selectedtr=''
//表格每行的选中事件
	tr=$("#labourdata").html();
function checklabours(a,e){
	selectedtr='<tr><td>'+a.GONGHAO+'</td><td>'+a.XINGMING+'</td><td>'+a.SHFENFENZHENG+'</td><td>'+a.FENBAODUIWU+'</td><td>'+a.BANZUMC+'</td><td>'+a.GZMC+'</td><td>'+a.LIANXIDIANHUA+'</td><td style="display:none;">'+a.DUIWU_PKID+'</td><td style="display:none;">'+a.PROJECT_DUIWU_PKID+'</td><td style="display:none;">'+a.BANZU_PKID+'</td><td style="display:none;">'+a.GONGZHONG_BIANMA+'</td><td style="display:none;">'+a.PKID+'</td><td><a href="javascript:void(0);" onclick="removetr(this);" style="margin-left:10px" data-operation="remove"><span class="glyphicon glyphicon-trash"></span></a></td></tr>'

	
	
		if(e[0].checked==true){
			selectedCount++;
			if(tr.indexOf(a.GONGHAO)>=0){
				$('.select_labour_btn').html('添加('+selectedCount+')')
				return true;
			}
			tr+=selectedtr;
		}else{
			tr=tr.replace(a.GONGHAO,'');
			selectedCount--;
			
		}
		$('.select_labour_btn').html('添加('+selectedCount+')')
	}

function unchecklabours(a,e){
	selectedtr='<tr><td>'+a.GONGHAO+'</td><td>'+a.XINGMING+'</td><td>'+a.SHFENFENZHENG+'</td><td>'+a.FENBAODUIWU+'</td><td>'+a.BANZUMC+'</td><td>'+a.GZMC+'</td><td>'+a.LIANXIDIANHUA+'</td><td style="display:none;">'+a.DUIWU_PKID+'</td><td style="display:none;">'+a.PROJECT_DUIWU_PKID+'</td><td style="display:none;">'+a.BANZU_PKID+'</td><td style="display:none;">'+a.GONGZHONG_BIANMA+'</td><td style="display:none;">'+a.PKID+'</td><td><a href="javascript:void(0);" onclick="removetr(this);" style="margin-left:10px" data-operation="remove"><span class="glyphicon glyphicon-trash"></span></a></td></tr>'

	
	
			tr=tr.replace(selectedtr,'');
			selectedCount--;
			
		$('.select_labour_btn').html('添加('+selectedCount+')')
	}
//选择全部
function checkAll(e){
	
	var alltr='';
	for(var i=0;i<e.length;i++){
		if(tr.indexOf(e[i].GONGHAO)>=0){
			continue;
		}
		alltr='<tr><td>'+e[i].GONGHAO+'</td><td>'+e[i].XINGMING+'</td><td>'+e[i].SHFENFENZHENG+'</td><td>'+e[i].FENBAODUIWU+'</td><td>'+e[i].BANZUMC+'</td><td>'+e[i].GZMC+'</td><td>'+e[i].LIANXIDIANHUA+'</td><td style="display:none;">'+e[i].DUIWU_PKID+'</td><td style="display:none;">'+e[i].PROJECT_DUIWU_PKID+'</td><td style="display:none;">'+e[i].BANZU_PKID+'</td><td style="display:none;">'+e[i].GONGZHONG_BIANMA+'</td><td style="display:none;">'+e[i].PKID+'</td><td><a href="javascript:void(0);" onclick="removetr(this);" style="margin-left:10px" data-operation="remove"><span class="glyphicon glyphicon-trash"></span></a></td></tr>'
		tr=tr.replace(alltr,'');
		tr+=alltr;
		selectedCount++;
	}
	
      $(".select_labour_btn").text("添加(" + selectedCount + ")");
}

//选择全部
function uncheckAll(e){
	for(var i=0;i<e.length;i++){
		alltr='<tr><td>'+e[i].GONGHAO+'</td><td>'+e[i].XINGMING+'</td><td>'+e[i].SHFENFENZHENG+'</td><td>'+e[i].FENBAODUIWU+'</td><td>'+e[i].BANZUMC+'</td><td>'+e[i].GZMC+'</td><td>'+e[i].LIANXIDIANHUA+'</td><td style="display:none;">'+e[i].DUIWU_PKID+'</td><td style="display:none;">'+e[i].PROJECT_DUIWU_PKID+'</td><td style="display:none;">'+e[i].BANZU_PKID+'</td><td style="display:none;">'+e[i].GONGZHONG_BIANMA+'</td><td style="display:none;">'+e[i].PKID+'</td><td><a href="javascript:void(0);" onclick="removetr(this);" style="margin-left:10px" data-operation="remove"><span class="glyphicon glyphicon-trash"></span></a></td></tr>'
		tr=tr.replace(alltr,'');
	}
	 selectedCount -= e.length;
	 if(selectedCount<=0){
		 selectedCount=0;
		 tr='';
	 }
      $(".select_labour_btn").text("添加("+selectedCount+")");
}
//table加载完毕之后（或点击分页后）,之前勾选过的要加默认勾选
function checkSelected(datas){
	for(var i=0;i<datas.length;i++){
		if(tr.indexOf(datas[i].GONGHAO)>=0){
			$('#tableforbootrenyuan').find('tr')[i+1].children[0].children[0].checked=true;
		}
	}
}


(function($){
	$('#DUIWU').change(function(){
		var XM_DUIWU_PKID = $(this).val();
		$.ajax({
			type:"post",
			dataType:"json",
			data:{"XM_DUIWU_PKID":XM_DUIWU_PKID},
			url:_basepath+"labour/listbz.json",
			success:function(mv){
				var list_bz = mv.list_bz;
				$('#BANZU').empty();
				var option='<option value="allbanzu" >全部班组</option>';
				for(var i=0;i<list_bz.length;i++){
					option+='<option value="'+list_bz[i].PKID+'">'+list_bz[i].MINGCHENG+'</option>';
				}
				$(option).appendTo('#BANZU');
			},
			error:function(a,b,exception){
				alert(exception);
			}
		});
	});
	
	
	
	
	
})(jQuery);


function getrenyuantable(data){
	// 引例四
	
	var url = encodeURI(_basepath+"labour/getrenyuantable.json");
	

	$('#tableforbootrenyuan').bootstrapTable(
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
//						height:350,//固定表头
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
						toolbar : "#toolbar3",
						toolbarAlign : 'left',
						onCheck: function (arg1, arg2) {
							checklabours(arg1,arg2);
					    },
					    onUncheck: function (arg1, arg2) {
					    	unchecklabours(arg1,arg2);
					    },
					    onCheckAll: function (arg1, arg2) {
					    	checkAll(arg1)
					    },
					    onUncheckAll: function (arg1, arg2) {
					    	uncheckAll(arg1)
					    },
					    onLoadSuccess: function (data) {
					        checkSelected(data.rows);
					    },
						columns : [

								{
									title : "全选",//标题
									align : "center",//水平
									checkbox : true,
									valign : "middle"//垂直

								},
								
								{
									field : 'GONGHAO',
									title:'工号',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'XINGMING',
									title:'姓名',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'SHFENFENZHENG',
									title : '身份证号',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field: 'FENBAODUIWU',//可不加  
									title : '分包队伍',//标题  可不加
									align : "center",
									halign : 'center'
								},
								{
									field : 'BANZUMC',
									title : '班组名称',
									align : "center",
									halign : 'center',
									sortable : false	
								},
								{
									field : 'GZMC',
									title : '工种',
									align : "left",
									halign : 'center'
								},
								{
									field : 'LIANXIDIANHUA',
									align : "center",
									halign : 'center',
									title : '电话'
								},
								{
									field : 'DUIWU_PKID',
									align : "center",
									halign : 'center',
									title : 'DUIWU_PKID',
									visible:false
								},
								{
									field : 'PROJECT_DUIWU_PKID',
									align : "center",
									halign : 'center',
									title : 'PROJECT_DUIWU_PKID',
									visible:false
								},
								{
									field : 'BANZU_PKID',
									align : "center",
									halign : 'center',
									title : 'BANZU_PKID',
									visible:false
								},
								{
									field : 'GONGZHONG_BIANMA',
									align : "center",
									halign : 'center',
									title : 'GONGZHONG_BIANMA',
										visible:false
								},
								{
									field : 'PKID',
									align : "center",
									halign : 'center',
									title : 'PKID',
									visible:false
								}
								
								
						],
					});
}













$(function(){
	//ie9搜索加提示
	jsPlaceHolder.play("keywords")
	getrenyuantable();
	$('.btn_search').bind('click',function(){
		var DUIWU_PKID=$('#DUIWU').val();
		var BANZU_PKID=$('#BANZU').val();
		var EDUSTATUS=$('#edustatus').val();
		var ANQUANJIAOYU_PKID=pkid
		if(EDUSTATUS=='all' ){
			EDUSTATUS='';
		}
		if(ANQUANJIAOYU_PKID=='' ){
			ANQUANJIAOYU_PKID=new Date().getTime()+''; 
		}
		
		
		var KEYWORDS=$('#keywords').val();
		if(KEYWORDS=='搜工号、姓名、身份证号'){
			KEYWORDS='';
		}
		var url = encodeURI(_basepath+"labour/getrenyuantable.json?DUIWU_PKID="+DUIWU_PKID+"&BANZU_PKID="+BANZU_PKID+"&ANQUANJIAOYU_PKID="+ANQUANJIAOYU_PKID+"&KEYWORDS="+KEYWORDS+"&EDUSTATUS="+EDUSTATUS);

		$("#tableforbootrenyuan").bootstrapTable(
				'refresh',
				{
					url : url
				});
		
		
	})
	$('.select_labour_btn').html('添加('+selectedCount+')')
});