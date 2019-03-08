//@ sourceURL=dormhistory_list.js 
/**
 * 宿舍信息页面对象
 */
(function($, window) {
	
	var dormInfo = {};
	var cloumnArray = new Array();//用于记录当前展示的列
	// 去新增页面
	dormInfo.goAddStu = function() {
		$(".jf_szright").load(_basepath + "stuinfo/goAdd.json");
	};
	
	
	dormInfo.getFenPeiIdSelectionsBMPKID=function () {
		var $table = $('#fenpeitable');
		return $.map($table.bootstrapTable('getSelections'), function(row) {
	    return row.BMPKID;
	});
	};
	//获取选中的行
	dormInfo.getIdSelections=function () {
			var $table = $('#dorminfotable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.PKID;
        });
	};
	//获取选中的行的stuid
	dormInfo.getStuIdSelections=function () {
			var $table = $('#dorminfotable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.T_STUDENT_PKID;
        });
	};
	
	//获取选中的行的studepid
	dormInfo.getStudepIdSelections=function () {
			var $table = $('#dorminfotable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.STU_PKID;
        });
	};

	//获取选中的行的宿舍类型
	dormInfo.getTypeSelections=function () {
			var $table = $('#dorminfotable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.T_STUDENT_DORM_TYPE_PKID;
        });
	};
	
	dormInfo.getduidiaoIdSelections=function () {
		var $table = $('#exchangetable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.PKID;
    });
};
	
	//新增按钮绑定单机事件
	$('#btn_add').bind('click',function(){
		dormInfo.goAddStu();
	});
	
	//查询
	$('#btn_search').click(function(){
		//所属组织
		$("#dorminfotable").bootstrapTable('destroy'); 
		 dormInfo.getTab();
		
	});
	//退宿按钮点击事件
	$("#btn_tuisu").click(function(){
		var pkids=dormInfo.getIdSelections();
		var stupkids=dormInfo.getStuIdSelections();
		var types=dormInfo.getTypeSelections();
		var t_studep_pkid=dormInfo.getStudepIdSelections();
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			  BootstrapDialog.show({  //显示需要提交的表单。
				  title:'提示信息',	
		          message: '请至少选择一条数据!',
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
		}else if(stupkids.length != pkids.length){
			BootstrapDialog.show({  //显示需要提交的表单。
				  title:'提示信息',	
		          message: '请选择已有学生入住的宿舍',
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
          message: '你确定要退宿吗？',
          closable: false, 
            buttons: [{
			    label: '确定',
			    cssClass: 'btn-danger',
			    action: function(dialogRef){
			    dormInfo.batchTuiSu(pkids,stupkids,types,t_studep_pkid);
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
	 dormInfo.batchTuiSu = function (pkids,stupkids,types,t_studep_pkid){
		$.post(
				_basepath+"dormitoryInfo/batchTuiSu.json",
				{"pkids":pkids.join(","),
				 "stupkids":stupkids.join(","),
				 "t_studep_pkid":t_studep_pkid.join(","),
				 "types":types.join(",")},
				function(data){
					if(data.result=='success'){
						layer.msg("退宿成功");
						$('#dorminfotable').bootstrapTable("refresh");
					}
				}
			); 
	 };
	$("#btn_import_dorm").click(function(){
		var dialog = BootstrapDialog.show({
			title:'导入宿舍信息',
			message: $('<div></div>').load("importdorm/importdorminfo.json"),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
	            label: '关闭',
	            cssClass: 'btn-danger',
	            action: function(dialog) {
	            	dialog.close();
	            	$('#dorminfotable').bootstrapTable("refresh");
	            }
	        }]
		});
	});
	/**
	 * 修改学生在学状态
	 */
	dormInfo.editstustatus=function(a,b){
		$.post(_basepath+"stuinfo/editstustatus.json?pkids="+a+"&ZAIXUEZT="+b,function(data){
			if(data.result=="success"){
				layer.msg("修改成功!");
				 $("#dorminfotable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}else{
				layer.msg("修改失败!");
			}
		});
	};
	//点击修改学生状态按钮
	$('#btn_editstatus').bind('click',function(){

		var pkids=dormInfo.getIdSelections();
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			  BootstrapDialog.show({ // 显示需要提交的表单。
				title : '提示信息',
				message : '请至少选择一条数据!',
				buttons : [ {
					label : '关闭',
					cssClass : 'btn-danger',
					action : function(dialogRef) {
						dialogRef.close();
					}
				} ]
			  });
			  return false;
		}
		
	 var opt='';
	 for(var i=0;i<zaixuezt.length;i++){
		 opt+='<option value="'+zaixuezt[i].BIANMA+'" >'+zaixuezt[i].NAME+'</option>'
	 }
	  var select_str ='<div  class="form-inline" style="margin-bottom: 25px;"><label class="jf_label" for="">在学状态:</label>'
                     +'<select name="" class="form-control jf_FltConditions" id="newzxstatus">'
                     +opt
	                 +'</select></div>';
	  BootstrapDialog.show({  //显示需要提交的表单。
        	title:'修改在学状态',	
        message: select_str,
        closable: true, 
          buttons: [{
			    label: '保存',
			    cssClass: 'btn-danger',
			    action: function(dialogRef){
			    	//修改在学状态
			    dormInfo.editstustatus(pkids,$('#newzxstatus').val())
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

	});
	
	//调整专业
	dormInfo.updateDep = function(dialogRef,pkids){
		var orgtreeDep = $("#orgtreeDep").val();
		if(orgtreeDep == '' || orgtreeDep==null){
			layer.msg("院校专业不能为空!");
			return false;
		}
		var gradeDep = $("#gradeDep").val();
		var CENGCIDEP = $("#CENGCIDEP").val();
		var PICIDEP = $("#PICIDEP").val();
		$.post(_basepath+"stuinfo/updateDep.json?pkids="+pkids+"&orgtreeDep="+orgtreeDep+"&gradeDep="+gradeDep+"&CENGCIDEP="+CENGCIDEP+"&PICIDEP="+PICIDEP,function(data){
			if(data.result=="success"){
				layer.msg("修改成功!");
				dialogRef.close();
				$("#dorminfotable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}else{
				dialogRef.close();
				layer.msg("修改失败!");
			}
		});
	}
	
	//去调整专业页面
	$("#btn_tzzy").click(function(){
		var pkids=dormInfo.getIdSelections();
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			  BootstrapDialog.show({ // 显示需要提交的表单。
				title : '提示信息',
				message : '请至少选择一条数据!',
				buttons : [ {
					label : '关闭',
					cssClass : 'btn-danger',
					action : function(dialogRef) {
						dialogRef.close();
					}
				} ]
			  });
			  return false;
		}else{
			BootstrapDialog.show({
				title : '调整专业',
				message : $('<div></div>').load(_basepath+'stuinfo/goUpdateDep.json?pkids='+pkids),
				closable : true,
				buttons : [
				           {
				        	   label : '确定',
				        	   cssClass : 'btn-danger',
				        	   action : function(dialogRef) {
				        		   dormInfo.updateDep(dialogRef,pkids);
				        	   }
				           }, {
				        	   label : '取消',
				        	   cssClass : 'btn-default',
				        	   action : function(dialogRef) {
				        		   dialogRef.close();
				        	   }
				           } ]
			});
		}
	});
	
	$("#grade").bind("change", function() {
		changeGrade();
	});
	
	//点击按钮导出
	$("#checkOut").click(function(){
		var dorm_pkid=$("#orgtree3").val();//宿舍下拉列表
		var dorm_type=$("#dormtype_select").val();//宿舍类别下拉列表
		var whkxx=$('#whkxx').val();//文化课学校
		var banx=$('#banx').val();//班型
		var xingbie=$('#xingbie').val();//性别
		var banji=$('#banji').val();//班级
		var seText = $('#search').val();//搜索框
		window.location.href=encodeURI(_basepath+'dormitoryInfo/dormhistoryexportExcel.json?seText='+seText+'&&dorm_pkid='+dorm_pkid+'&&dorm_type='+dorm_type+"&&banji="+banji+"&&xingbie="+xingbie+"&&whkxx="+whkxx+"&&banx="+banx
				);
	});
	
	//批量删除按钮绑定单机事件
	$('#btn_del').bind('click',function(){

		var pkids=dormInfo.getIdSelections();
		var czid = $("input[name='id']:checked").val();
		if(typeof(czid)=="undefined"){
			  BootstrapDialog.show({  //显示需要提交的表单。
				  title:'提示信息',	
        message: '请至少选择一条数据!',
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
			    dormInfo.batchDel(pkids);
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
	
	dormInfo.getFenPeiIdSelectionsRXNF=function () {
		var $table = $('#fenpeitable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.RXNIANFEN_PKID;
    });
};




	
	
	//获取list表格数据
	dormInfo.getTab=function () {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		// 引例四
		var url = _basepath+"dormitoryInfo/getdormhistorytable.json";

		$('#dorminfotable').bootstrapTable(
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
							onColumnSwitch: function(field,checked){
								console.log(field+"::"+checked);
							},
							onDblClickRow:function(row){
							},
							queryParams : function getParams(params) {
								var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, //页面大小
									dorm_pkid:$("#orgtree3").val(),//宿舍下拉列表
									dorm_type:$("#dormtype_select").val(),//宿舍类别下拉列表
									whkxx:$('#whkxx').val(),//文化课学校
									banx:$('#banx').val(),//班型
									xingbie:$('#xingbie').val(),//性别
									banji:$('#banji').val(),//班级
									seText : $('#search').val(),//搜索框
									
									/*ruxuenianfen:$("#ruxuenianfen").find("option:selected").attr("bianma"),//入学年份
									department_pkid:$('#orgtree').val(),//院校专业
									xingbie:$('#xingbie').val(),//性别
									
									zhuangtai:$('#zhuangtai').val(),//使用状态
*/									
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
										field : 'XINGBIE',
										title : '性别',
										align : "center",
										halign : 'center',
										formatter : function(value, row,
												index) {
											if(row.XINGBIE == 'M'){
												return "男";
											}
											if(row.XINGBIE == 'W'){
												return "女";
											}
										},
										sortable : false
									},
									{
										field : 'WHKXUEXIAO',
										title : '文化课学校',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'RXNF',
										title : '入学年份',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'BANXING',
										title : '班型',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'BANJI',
										title:'班级',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'DORM_TYPE_NAME',
										title:'宿舍类型',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{
										field : 'ROOM_NAME',
										title:'宿舍',
										align : "center",
										halign : 'center',
										sortable : false
									},
									{ 
										field : 'RZSJ',
										title : '入住日期',
										align : "center",
										halign : 'center',
										sortable : false
									}	
							],
						});
	   /* $('#dorminfotable').bootstrapTable('hideColumn', 'PKID');//隱藏列
	    $('#dorminfotable').bootstrapTable('hideColumn', 'T_STUDENT_DORM_TYPE_PKID');//隱藏列
	    $('#dorminfotable').bootstrapTable('hideColumn', 'BANJI_TYPE_PKID');//隱藏列
	    $('#dorminfotable').bootstrapTable('hideColumn', 'WHKXUEXIAOPKID');//隱藏列
	    $('#dorminfotable').bootstrapTable('hideColumn', 'RXNIANFEN_PKID');//隱藏列BMPKID   T_STUDENT_BM_PKID
	    $('#dorminfotable').bootstrapTable('hideColumn', 'BMPKID');
	    $('#dorminfotable').bootstrapTable('hideColumn', 'T_STUDENT_BM_PKID');//隱藏列BMPKID
*/	    
	};
	//加载表格数据
	dormInfo.getTab();
	//调宿and对调方法
	dormInfo.changeDorm=function (row,dialogRef,info){

		   //对调宿舍
		 //获取选中的行
		   dormInfo.getExchangeIdSelections=function () {
					var $table = $('#exchangetable');
		        	return $.map($table.bootstrapTable('getSelections'), function(row) {
		            return row.PKID;
		        });
			};
			dormInfo.getExchangeStuIdSelections=function () {
				var $table = $('#exchangetable');
	        	return $.map($table.bootstrapTable('getSelections'), function(row) {
	            return row.T_STUDENT_PKID;
	        });
		};
		
		dormInfo.getExchangeStuIdbmpkidSelections=function () {
			var $table = $('#exchangetable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.BMPKID;
        });
	};
	
	dormInfo.getExchangeStuIdstubmpkidSelections=function () {
		var $table = $('#exchangetable');
    	return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.T_STUDENT_BM_PKID;
    });
};
		   var newpkid=dormInfo.getExchangeIdSelections();
		   var newStuId=dormInfo.getExchangeStuIdSelections();
		   var NEWBMPKID= dormInfo.getExchangeStuIdbmpkidSelections()[0];
		   var T_STUDENT_BM_PKID= dormInfo.getExchangeStuIdstubmpkidSelections()[0];
		   var oldpkid=row.PKID;
		   var oldStuId=row.T_STUDENT_PKID;
		   var OLDBMPKID=row.BMPKID;
		   var T_STUDENT_DORM_TYPE_PKID=row.T_STUDENT_DORM_TYPE_PKID
		   var info=info;
		   //对调宿舍信息
		   $.post(_basepath+"dormitoryInfo/duidiao.json",
				   {
	     			   newpkid:newpkid[0],
	     			   newStuId:newStuId[0],
	     			   oldpkid:oldpkid,
	     			  OLDBMPKID:OLDBMPKID,
	     			 NEWBMPKID:NEWBMPKID,
	     			   oldStuId:oldStuId,
	     			   T_STUDENT_DORM_TYPE_PKID:T_STUDENT_DORM_TYPE_PKID,
	     			   info:info
				   },function(data){
			   if(data.result=='success'){
				   layer.msg("操作成功!");
				   dialogRef.close();
				   $("#dorminfotable").bootstrapTable('refresh', {url: _basepath+"dormitoryInfo/getdormtable.json"});
			   }
		   });
	   
	};
	//分配
	dormInfo.fenpei=function (row,dialogRef){

		   //对调宿舍
		 //获取选中的行
		   dormInfo.getFenPeiIdSelections=function () {
					var $table = $('#fenpeitable');
		        	return $.map($table.bootstrapTable('getSelections'), function(row) {
		            return row.PKID;
		        });
			};
			
			
			
			 
			var dormId=row.PKID;
			var T_STUDENT_DORM_TYPE_PKID = row.T_STUDENT_DORM_TYPE_PKID;
		   //对调宿舍信息
		   $.post(_basepath+"dormitoryInfo/fenpei.json",
				   {
			   			stuId:dormInfo.getFenPeiIdSelections[0],
			   			dormId:dormId,
			   			T_STUDENT_DORM_TYPE_PKID:T_STUDENT_DORM_TYPE_PKID
				   },function(data){
			   if(data.result=='success'){
				   layer.msg("分配成功!");
				   dialogRef.close();
				   $("#dorminfotable").bootstrapTable('refresh', {url: _basepath+"dormitoryInfo/getdormtable.json"});
			   }
		   });
	   
	}
	//更多查询条件
	$('#more').click(function(){
		//所属组织
		if($('#icon').hasClass('fa-angle-double-down')){
			$('#icon').removeClass('fa-angle-double-down');
			$('#icon').addClass('fa-angle-double-up');
		}else{
			$('#icon').removeClass('fa-angle-double-up');
			$('#icon').addClass('fa-angle-double-down');
		}
		
	});
	
	dormInfo.getFenPeiIdSelections=function () {
		var $table2 = $('#fenpeitable');
    	return $.map($table2.bootstrapTable('getSelections'), function(row2) {
        return row2.PKID;
    });
};
})(jQuery, window);
