//@ sourceURL=safeedu.js 
//添加工人到新增列表
var tr=''
var pkid=''
function addgongren(dialogRef){
	if(selectedCount==0){
		layer.msg('请选择要添加的工人！')
		return;
	}
	/*$('input:checkbox[name=chkItem]:checked').each(function(i){
		tr=$('#labourdata').html();
		if(tr.indexOf($(this).parent().parent().html().replace('<td><input type="checkbox" onclick="checklabours(this)" name="chkItem"></td>',''))>0){
			tr+='';
			return true;
		}else{
			tr+='<tr>'+$(this).parent().parent().html().replace('<td><input type="checkbox" onclick="checklabours(this)" name="chkItem"></td>','')+'<td><a href="javascript:void(0);" onclick="removetr(this);" style="margin-left:10px" data-operation="remove"><span class="glyphicon glyphicon-trash"></span></a></td></tr>';

		}
	})*/
	$('#labourdata').empty();
	 $(tr).appendTo('#labourdata');	
	 layer.msg("添加成功！");
	 dialogRef.close();
	}
//删除某一行
function removetr(e){
	
	BootstrapDialog.show({  //显示需要提交的表单。
    	title:'提示信息',	
    message: '你确定要删除这条记录吗？',
    closable: true, 
      buttons: [{
	    label: '确定',
	    cssClass: 'btn-success keman_btnx',
	    action: function(dialogRef){
	    	console.log($(e).parent().parentNode)
	    	$(e).parent().parent().remove();//删除行
	    	tr=tr.replace('<tr>'+$(e).parent().parent().html()+'</tr>', '');//去掉相应的tr拼接字符串内容
	    	dialogRef.close();//关闭窗口
	    	layer.msg("删除成功！")
	    }
  }, {
    label: '取消',
    cssClass: 'btn-warning keman_btnx',
    action: function(dialogRef){
       dialogRef.close();
    }
  }
  ]
    });
	

}

$("#btn_leixing").change(function(){
	  pingxing();
});
$("#btn_search").click(function(){
	pingxing();
})

function pingxing() {
	// 教育类型
	var leixing = $("#btn_leixing").val();
	if (leixing == 'all') {
		leixing = ''
	}
	// 关键词搜索
	var search = $("#searchsr").val();
	//日期起
	var startdate=$('#RIQIqi').val();
	//日期至
	var enddate=$('#RIQIzhi').val();
	$("#tableforboot").bootstrapTable(
			'refresh',
			{
				url : encodeURI(_basepath + "labour/gettable.json?KEYWORDS="
						+ search + "&JIAOYULEIXING=" + leixing+"&STARTDATE="+startdate+"&ENDDATE="+enddate)
			});
}

//匿名函数，避免变量冲突
(function($){
	var jyrq;
	laydate({
		  elem: '#RIQIqi', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		  event: 'focus', //响应事件。如果没有传入event，则按照默认的click
		  festival: true, //是否显示节日
		  choose: function(dates){ //选择好日期的回调
			  var a=dates;
		  }
		});
	laydate({
		  elem: '#RIQIzhi', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		  event: 'focus', //响应事件。如果没有传入event，则按照默认的click
		  festival: true, //是否显示节日
		  choose: function(dates){ //选择好日期的回调
			  
		  }
		});
	
	laydate({
		  elem: '#jyrq', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		  event: 'focus', //响应事件。如果没有传入event，则按照默认的click
		  festival: true, //是否显示节日
		  //max: laydate.now(),//不能选大于今天的日期
		  choose: function(dates){ //选择好日期的回调
			  jyrq=dates;
		  }
		});
	
	//安全教育培训  add
	$('.lbr-safe-btn-add').bind('click',function(){
		//创建上传组件
		$('#yijie').hide();
		$('.lbr-safe-view-edit').show();
		safeedu.initUpload();
	})
	
	$('#cancel_btn').bind('click',function(){
		
		$("#nameli").load(_basepath+"labour/safety-training.php");//回到list界面
	});
	
	//选择工人
	$("#addgr").bind("click", function() {
        var blg= BootstrapDialog.show({  //显示需要提交的表单。
        message: $('<div></div>').load('labour/addgongren.json?a='+Math.random()),
        title: '选择工人',
        closable: true, 
        draggable: true,
          buttons: [{ 
		    label: '添加(0)',
		    cssClass: 'btn-success keman_btn select_labour_btn',
		    action: function(dialogRef){
		    	
            	addgongren(dialogRef);
            	
            }
	  }, {
	    label: '取消',
	    cssClass: 'btn-warning keman_btn',
	    action: function(dialogRef){
	    dialogRef.close();
	    }
	  }
	  ]
        });
        blg.setSize(BootstrapDialog.SIZE_WIDE);
 });
	
	//点击保存
	$('#save_btn').bind('click',function(){
		/*主表记录 */
		//教育类型
		var jylx=$('#jylx').val();
		//教育日期
		var jydate=$("#jyrq").val();
		//培训课时
		var pxks=$('#pxks').val();
		//教育主题
		var jyzt=$('#jyzt').val();
		//培训人
		var pxr=$('#pxr').val();
		//考核人
		var khr=$('#khr').val();
		//培训方式
		var pxfs=$('#pxfs').val();
		//备注
		var beizhu=$('#beizhu').val();
		var reg=/^\s+$/g;
		if(jydate=='请选择培训日期'){
			jydate='';
		}
		if(jydate.length == 0 || reg.test(jydate)){
			layer.msg("请选择培训日期！")
            return false;
		}
		if(jyzt=='最多一百字'){
			jyzt='';
		}
		if(jyzt.length == 0 || reg.test(jyzt)){
			layer.msg("请输入培训主题！")
            return false;
		}
		var PKID=pkid;
		/*人员表记录  */
		var datas=new Array();
		var tableObj=$('#renyuantable');
		var rows=tableObj[0].rows;
		var renyuan=new Object();
		var renyuans={};
		if(rows.length==1){
       	 BootstrapDialog.show({  //显示需要提交的表单。
       		  title:"提示信息",
                 message:"安全教育中应至少添加一个工人!",
                 buttons: [{
     		    label: '关闭',
     		    cssClass: 'btn-warning keman_btn',
     		    action: function(dialogRef){
     		       dialogRef.close();
     		    }
     		  }
     		  ]
             });
       	  return false;
        }
		for (var i = 1; i < rows.length; i++) { 
	        
	        //遍历Table的所有Row
	      datas[datas.length]={GONGHAO:rows[i].cells[0].innerText,XINGMING:rows[i].cells[1].innerText,SHENFENZHENGHAO:rows[i].cells[2].innerText,DUIWU_PKID:rows[i].cells[7].innerText
	        ,PROJECT_DUIWU_PKID:rows[i].cells[8].innerText,BANZU_PKID:rows[i].cells[9].innerText,GONGZHONG_PKID:rows[i].cells[10].innerText,LAOWURENYUAN_PKID:rows[i].cells[11].innerText,DIANHUA:rows[i].cells[6].innerText};
	    }
		var eduarray=new Array();
		eduarray[0]={"pkid":PKID,"JIAOYULEIXING":jylx,"ZHUTI":jyzt,"RIQI":jydate,"JIANGSHIMC":pxr,"BEIZHU":beizhu,"KAOHEREN":khr,"PEIXUNFANGSHI":pxfs,"PEIXUNKESHI":pxks}
		/*附件记录 */
		 var fjfiles = zhubiao.files;
		 var fjfilesStr = JSON.stringify(fjfiles);//转换为json字符串
		// safeeduVo.fjfilesStr = fjfilesStr;
		var requrl="";
		if(PKID!=""){
			requrl="editedu.json";
		}else{
			requrl="saveedu.json";
		}
		
		
		
		$.ajax({
			type:"post",
			dataType:"json",
			data: {'rydata':JSON.stringify(datas),'edudata':JSON.stringify(eduarray),'fjfilesStr':fjfilesStr},
			url:_basepath+"labour/"+requrl,
			success:function(result){
				//附件上传
	        	safeedu.pkidList = result.pkidList;
				if(zhubiao.isHaveUploadFile()==true){
					var pkidObj = safeedu.pkidList[0];
	        		
		        	var PKID = pkidObj.PKIDRY;//人员pkid
				    //处理附件上传
					//设置附件表名称和目录名称  
					myupload.stream.config.postVarsPerFile = {tableName: "T_ANQUANJIAOYU_FJ", mulu: PKID};
					//end
					
					//执行上传附件
					myupload.stream.upload();
					//end
	        	}else{
	        		$("#nameli").load(_basepath+"labour/safety-training.php");//回到list界面
	        	}
				if( $('.keman_xinzlaow').html()=='编辑安全教育培训记录'){
					layer.msg("修改成功！")
				}else{
					layer.msg("新增成功！")
				}
				
				
			},
			error:function(a,b,exception){
				alert(exception);
			}
		});
		
		
	})
	
	
})(jQuery);


//table 取数据
function getTab(data) {
	// 引例四
	var url = _basepath+"labour/gettable.json";

	$('#tableforboot').bootstrapTable(
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
						showRefresh : true, //显示刷新按钮  --只是前台刷新，个人觉得没什么用
						minimumCountColumns : 2, //最少允许的列数
						sidePagination : "server", //表示服务端请求  
						totalRows : 0, // server side need to set
						singleSelect : false,
						clickToSelect : true,
						onDblClickRow:function(row){
//						    BootstrapDialog.show({  //显示需要提交的表单。
//						    title:"详情",
//				            message: $('<div></div>').load('safeedulist/goXiangQing.json?PKID='+row.PKID+'&rypkid='+row.RYPKID),
//				            closable: true, 
//				            buttons: [{
//						    label: '关闭',
//						    cssClass: 'btn-warning keman_btnr',
//						    action: function(dialogRef){
//						       dialogRef.close();
//						    }
//						  }
//						  ]
//			            });
						},
						queryParams : function getParams(params) {
							//    	                   var searchKey1 = $("#JIAOYISHIJIAN").val();  //搜索框数据
							//    	                    var searchKey2 = $("#s2").val();  
							var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
								limit : params.limit, //页面大小
								//    	                                 searchKey1:searchKey1,
								//    	                                 searchKey2:searchKey2,
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
									valign : "middle"//垂直

								},
								{
									field: 'PKID',//可不加  
									align : "center",
									halign : 'center'
								},
								{
									field : 'XUHAO',
									title:'序号',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'RIQI',
									title:'培训日期',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field : 'JIAOYULEIXING',
									title : '教育类型',
									align : "center",
									halign : 'center',
									sortable : false
								},
								{
									field: 'ZHUTI',//可不加  
									title : '培训主题',//标题  可不加
									align : "center",
									halign : 'center'
								},
								{
									field : 'EDUCOUNT',
									title : '参加培训人数',
									align : "center",
									halign : 'center',
									sortable : false,
									formatter : function(value, row,
											index) {
										return ['<a class="cjry ml10" href="javascript:void(0)" title="参加培训人员详情">'+value+'</a>'].join('');
									}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
									events : {
										'click .cjry' : function(e,value, row, index) {
											var blg= BootstrapDialog.show({  //显示需要提交的表单。
										        message: $('<div></div>').load('labour/edurylist.json?a='+Math.random()+'&ANQUANJIAOYU_PKID='+row.PKID+'&flag=edu'),
										        title: '参加安全培训人员列表',
										        closable: true, 
										        draggable: true,
										          buttons: [{
											    label: '关闭',
											    cssClass: 'btn-warning keman_btn',
											    action: function(dialogRef){
											    dialogRef.close();
											    }
											  }
											  ]
										        });
										        blg.setSize(BootstrapDialog.SIZE_WIDE);
										}
											} 
								},
								{
									field : 'NOEDUCOUNT',
									title : '未培训人数',
									align : "center",
									halign : 'center',
									formatter : function(value, row,
											index) {
										return ['<a class="wcjry ml10" href="javascript:void(0)" title="未参加培训人员详情">'+value+'</a>'].join('');
									}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
									events : {
										'click .wcjry' : function(e,value, row, index) {
											var blg= BootstrapDialog.show({  //显示需要提交的表单。
										        message: $('<div></div>').load('labour/edurylist.json?a='+Math.random()+'&ANQUANJIAOYU_PKID='+row.PKID+'&flag=noedu'),
										        title: '未参加安全培训人员列表',
										        closable: true, 
										        draggable: true,
										          buttons: [{
											    label: '关闭',
											    cssClass: 'btn-warning keman_btn',
											    action: function(dialogRef){
											    dialogRef.close();
											    }
											  }
											  ]
										        });
										        blg.setSize(BootstrapDialog.SIZE_WIDE);
										}
											} 
								},
								{
									field : 'BEIZHU',
									align : "center",
									halign : 'center',
									title : '备注'

								},
								{
									field : 'FILECOUNT',
									title : '文件',
									align : "center",
									halign : 'center',
									formatter : function(value, row,index) {
										if (row.FILECOUNT == undefined) {
											return "--";
										} else {
											return '<span class="glyphicon glyphicon-paperclip"></span>'+row.FILECOUNT;
										}
									},
				
								},
								{
									field : 'opt',
									title : '操作',
									align : "center",
									halign : 'center',
									formatter : function(value, row,
											index) {
										return [
												'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-edit" style="color:#f59942;font-size:16px;"></i></a>&nbsp;&nbsp;',
												'<a class="cancel ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o" style="color:#f59942;font-size:16px;"></i></a>' ]
												.join('');
									}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
									 events : {
										'click .edit' : function(e,value, row, index) {
											  //编辑  编辑人员
											  $.get(_basepath+'labour/goEdit.json?PKID='+row.PKID+'&rypkid='+row.RYPKID,function(mv){
												  var pd=mv.pd;//主表数据
												  var ryList=mv.ryList
												  var fjList=mv.fjList
												
												  //给主表记录框赋值
												    $('#jylx').val(pd.JIAOYULEIXING);
													//教育日期
													$("#jyrq").val(pd.EDUDATE);
													//培训课时
													$('#pxks').val(pd.PEIXUNKESHI);
													//教育主题
													$('#jyzt').val(pd.ZHUTI);
													//培训人
													$('#pxr').val(pd.JIANGSHIMC);
													//考核人
													$('#khr').val(pd.KAOHEREN);
													//培训方式
													$('#pxfs').val(pd.PEIXUNFANGSHI);
													//备注
													$('#beizhu').val(pd.BEIZHU);
													//隐藏框存储pkid
													//$('#pkid').val(pd.PKID);
													pkid=pd.PKID
												    //填充人员表
													var $tr=''
													for(var i=0;i<ryList.length;i++){
														$tr+='<tr><td>'+ryList[i].GONGHAO+'</td><td>'+ryList[i].XINGMING+'</td><td>'+ryList[i].SHENFENZHENGHAO+'</td><td>'+ryList[i].FENBAODUIWU+'</td><td>'+ryList[i].BANZUMC+'</td><td>'+ryList[i].GZMC+'</td><td>'+ryList[i].DIANHUA+'</td><td style="display:none;">'+ryList[i].DUIWU_PKID+'</td><td style="display:none;">'+ryList[i].PROJECT_DUIWU_PKID+'</td><td style="display:none;">'+ryList[i].BANZU_PKID+'</td><td style="display:none;">'+ryList[i].GONGZHONG_PKID+'</td><td style="display:none;">'+ryList[i].LAOWURENYUAN_PKID+'</td><td><a href="javascript:void(0);" onclick="removetr(this);" style="margin-left:10px" data-operation="remove"><span class="glyphicon glyphicon-trash"></span></a></td></tr>'
													}
													 $($tr).appendTo('#labourdata');	
													//填充附件表
													 var li="";
													 for(var i=0;i<fjList.length;i++){
														 li+="<li _data='{\"ANQUANJIAOYU_PKID\":\""+fjList[i].ANQUANJIAOYU_PKID+"\",\"tableName\":"+"\"T_ANQUANJIAOYU_FJ\",\"CLIENT_ID\":\""+fjList[i].CLIENT_ID +"\"}' fileid='"+fjList[i].CLIENT_ID+"' filename='"+fjList[i].BENDIWENJIANMC+"' filesize='"+fjList[i].SIZE+"' >"+fjList[i].BENDIWENJIANMC+"</li>";
													 }
												  $(li).appendTo('#yscwj1')
												  
												  $('#yijie').hide();
//	    							              $('#erjie').hide();
//												  $('.dbfujian_div').show()
												  $('.keman_xinzlaow').html('编辑安全教育培训记录')
	    							              $('.lbr-safe-view-edit').show();
												  
												//创建上传组件
												  safeedu.initUpload();
												  
											  })
    							              
    							              
    							              
    							              
    							              
    							              
										},

										'click .cancel' : function(e,value, row, index) {
											
											BootstrapDialog.show({  //显示需要提交的表单。
								            	title:'提示信息',	
								            message: '你确定要删除这条记录吗？',
								            closable: true, 
								              buttons: [{
											    label: '确定',
											    cssClass: 'btn-success keman_btnx',
											    action: function(dialogRef){
											    	 $.ajax({
															type:"post",
															dataType:"json",
															url:_basepath+'labour/delsingledata.json?PKID='+row.PKID+'&rypkid='+row.RYPKID,
															 success:function(data){
																 if("success" == data.rst){
																	 //tanchuang("删除");
																	 layer.msg("删除成功！")
																	  $("#tableforboot").bootstrapTable('refresh', {url:url});
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
										    cssClass: 'btn-warning keman_btnx',
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
    $('#tableforboot').bootstrapTable('hideColumn', 'PKID');//隱藏列
    $('#tableforboot').bootstrapTable('hideColumn', 'RYPKID');//隱藏列
}













$(function(){
	//ie9提示兼容问题
	jsPlaceHolder.play("RIQIqi")
	jsPlaceHolder.play("RIQIzhi")
	jsPlaceHolder.play("searchsr")
	jsPlaceHolder.play("jyrq")
	jsPlaceHolder.play("pxks")
	jsPlaceHolder.play("jyzt")
	jsPlaceHolder.play("pxr")
	jsPlaceHolder.play("khr")
//	$("input").placeholder(); 
	getTab();
});