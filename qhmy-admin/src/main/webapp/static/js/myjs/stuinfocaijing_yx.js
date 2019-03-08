//@ sourceURL=stuinfo.js 
/**
 * 学生信息页面对象
 */
(function($, window) {
	
	var stu = {};
	var cloumnArray = new Array();//用于记录当前展示的列
	
	//日期插件参数设置
	laydate.render({
		elem:'#ZCXJSJBEGIN',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	//日期插件参数设置
	laydate.render({
		elem:'#ZCXJSJEND',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	//日期插件参数设置
	laydate.render({
		elem:'#CHUSHENGRIQIBEGIN',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	//日期插件参数设置
	laydate.render({
		elem:'#CHUSHENGRIQIEND',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	//日期插件参数设置
	laydate.render({
		elem:'#RUXUESHIJIANBEGIN',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	//日期插件参数设置
	laydate.render({
		elem:'#RUXUESHIJIANIEND',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'date',
		format: 'yyyy-MM-dd',
		festival: true, //显示节日
		start: 0
	});
	// 去新增页面
	stu.goAddStu = function() {
		$(".jf_szright").load(_basepath + "stucjinfo/goAdd.json");
	};
	
	//获取选中的行
	stu.getIdSelections=function () {
			var $table = $('#stuinfotable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.PKID;
        });
	};
	//获取选中的行XUESHENGLEIXING_PKID
	stu.getXSLXPKIDSelections=function () {
		var $table = $('#stuinfotable');
		return $.map($table.bootstrapTable('getSelections'), function(row) {
			return row.XSLX_PKID;
		});
	};
	
	//获取选中的行的院校专业
	stu.getDepSelections=function () {
			var $table = $('#stuinfotable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.DEPARTMENT_PKID;
        });
	};
	
	//获取选中的行的入学年份
	stu.getGraSelections=function () {
			var $table = $('#stuinfotable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.NIANJI_BM;
        });
	};
	
	//获取选中的行的层次
	stu.getCengCiSelections=function () {
			var $table = $('#stuinfotable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.CENGCI_PKID;
        });
	};
	
	//获取选中的行的批次
	stu.getPiCiSelections=function () {
			var $table = $('#stuinfotable');
        	return $.map($table.bootstrapTable('getSelections'), function(row) {
            return row.PICI_PKID;
        });
	};
	
	//批量删除方法
	stu.batchDel=function(e){
		$.post(_basepath+"stuinfo/batchDel.json?pkids="+e,function(data){
			if(data.result=="success"){
				layer.msg("删除成功!");
				 $("#stuinfotable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}
			if(data.result=="paid"){
				layer.msg("您选择的信息包含已缴费的学生，不能删除!");
			}
			if(data.result=="useddorm"){
				layer.msg("您选择的学生已经占用床位或预定床位，不能删除!");
			}
		});
	};

	//批量通过方法
	stu.batchTongGuo=function(pkids){
		$.post(_basepath+"stuinfo/batchTongGuo.json?pkids="+pkids,function(data){
			if(data.result=="success"){
				layer.msg("操作成功!");
				$("#stuinfotable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}
		});
	}
	
	//新增按钮绑定单机事件
	$('#btn_add').bind('click',function(){
		stu.goAddStu();
	});
	
	//查询
	$('#btn_search').click(function(){
		//所属组织
		$("#stuinfotable").bootstrapTable('destroy'); 
		 stu.getTab();
		
	});
	//通过按钮点击事件
	$("#btn_tongguo").click(function(){
		var pkids=stu.getIdSelections();
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
          message: '你确定要通过吗？',
          closable: false, 
            buttons: [{
			    label: '确定',
			    cssClass: 'btn-danger',
			    action: function(dialogRef){
			    stu.batchTongGuo(pkids);
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
	
	$("#btn_import_stu").click(function(){
		var dialog = BootstrapDialog.show({
			title:'导入学生信息',
			message: $('<div></div>').load("importstucj/importstuinfo.json"),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
	            label: '关闭',
	            cssClass: 'btn-danger',
	            action: function(dialog) {
	            	dialog.close();
	            	$('#stuinfotable').bootstrapTable("refresh");
	            }
	        }]
		});
	});
	$("#btn_importedit_stu").click(function(){
		var dialog = BootstrapDialog.show({
			title:'学生信息导入修改',
			message: $('<div></div>').load("importstucj/importUpdateStuInfo.json"),
			closable: true,//是否显示叉号
			draggable: true,//可以拖拽
			buttons: [{
				label: '关闭',
				cssClass: 'btn-danger',
				action: function(dialog) {
					dialog.close();
					$('#stuinfotable').bootstrapTable("refresh");
				}
			}]
		});
	});
	/**
	 * 修改学生在学状态
	 */
	stu.editstustatus=function(a,b){
		$.post(_basepath+"stuinfo/editstustatus.json?pkids="+a+"&ZAIXUEZT="+b,function(data){
			if(data.result=="success"){
				layer.msg("修改成功!");
				 $("#stuinfotable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}else{
				layer.msg("修改失败!");
			}
		});
	};
	//点击修改学生状态按钮
	$('#btn_editstatus').bind('click',function(){

		var pkids=stu.getIdSelections();
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
			    stu.editstustatus(pkids,$('#newzxstatus').val())
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
	stu.updateDep = function(dialogRef,pkids){
		var orgtreeDep = $("#orgtreeDep").val();
		var orgtreeDepKeBie = $("#orgtreeDepKeBie").val();
		if(orgtreeDep == '' || orgtreeDep==null){
			layer.msg("院校专业不能为空!");
			return false;
		}
		var gradeDep = $("#gradeDep").find("option:selected").attr("bianma");
		if(gradeDep == '' || gradeDep==null){
			layer.msg("入学年份不能为空!");
			return false;
		}
		if(orgtreeDepKeBie!='4'){
			layer.msg("院校专业只能选择到专业层级");
			return false;
		}
		/*var banji=$("#banjidept").val();*/
		/*var CENGCIDEP = $("#CENGCIDEP").val();
		var PICIDEP = $("#PICIDEP").val();*/
		var deps = stu.getDepSelections();
		var grades = stu.getGraSelections();
		var cengcis = stu.getCengCiSelections();
		var picis = stu.getPiCiSelections();
		var flag = true;
		for(var i=0;i<pkids.length;i++){
			if((deps[i]==orgtreeDep)&&(grades[i]==gradeDep)/*&&(CENGCIDEP==cengcis[i])&&(picis[i]==PICIDEP)*/){
				layer.msg("调整后专业与调整前专业相同!");
				flag = false;
				return false;
			}
		}
		if(!flag){
			return false;
		}
		$.post(encodeURI(_basepath+"stuinfo/updateDep.json?pkids="+pkids+/*"&banji="+banji+*/"&orgtreeDep="+orgtreeDep+"&gradeDep="+gradeDep/*+"&CENGCIDEP="+CENGCIDEP+"&PICIDEP="+PICIDEP*/),function(data){
			if(data.result=="success"){
				layer.msg("修改成功!");
				dialogRef.close();
				$("#stuinfotable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}else{
				dialogRef.close();
				layer.msg("修改失败!");
			}
		});
	}
	
	//去调整专业页面
	$("#btn_tzzy").click(function(){
		var pkids=stu.getIdSelections();
		var xslxpkid=stu.getXSLXPKIDSelections();
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
				message : $('<div></div>').load(_basepath+'stuinfo/goUpdateDep.json?pkids='+pkids+"&XSLX_PKID="+xslxpkid),
				closable : true,
				buttons : [
				           {
				        	   label : '确定',
				        	   cssClass : 'btn-danger',
				        	   action : function(dialogRef) {
				        		   stu.updateDep(dialogRef,pkids);
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
		 //页面展示字段拼接
		var resultColumnList = $('#stuinfotable').bootstrapTable('getVisibleColumns');
		var resultColumns = "";			
		$.each(resultColumnList, function(i, column) {
			if(column.field=='0'||column.field=='opt'){
				return;
			}else{
				resultColumns+=","+column.field;
			}									
	 	});
		resultColumns = resultColumns.substr(1);
		//所属组织
		var orgtree=$('#orgtree').val();
		//年级
		var grade=$("#grade").find("option:selected").attr("bianma");
		if(grade==undefined){
			grade = "";
		}
		//缴费金额起
		var moneyqi=$('#moneyqi').val();
		//缴费金额止
		var moneyzhi=$('#moneyzhi').val();
		//身份证号 学号 姓名
		var searchText=$('#search').val();
		//是否通过
		var IS_TONGGUO=$("#IS_TONGGUO").val();
		var QRXX=$("#qrxx").val();
		var QRZY=$("#qrzy").val();
		var STU_FROM=$("#stu_from").val();
		var beizhu=$("#beizhu").val();

		var XINGBIE=$("#XINGBIE").val();
		var SHOUJI=$("#SHOUJI").val();
		var KAOSHENGHAO=$("#KAOSHENGHAO").val();
		var YOUZHENGBIANMA=$("#YOUZHENGBIANMA").val();
		var JTCYXM=$("#JTCYXM").val();
		var JTCYLXDH=$("#JTCYLXDH").val();
		var JIATINGZHUZHI=$("#JIATINGZHUZHI").val();
		var ZXMC=$("#ZXMC").val();
		/*var LUQUZHUANYE=$("#LUQUZHUANYE").val();*/
		
		//document.getElementById("kongcheckbox").checked
		var  flag=$("#kongcheckbox").is(':checked');
		if(flag){  //选中空
			var LUQUZHUANYE="kong";
		}else{
			var LUQUZHUANYE=$("#LUQUZHUANYE").val();
		}
		
		
		var CHUSHENGRIQI=$("#CHUSHENGRIQI").val();
		var IS_RECEIVED=$("#IS_RECEIVED").val();		       
		var ZCXJSJBEGIN=$("#ZCXJSJBEGIN").val();
		var ZCXJSJEND=$("#ZCXJSJEND").val();
		var CHUSHENGRIQIBEGIN=$("#CHUSHENGRIQIBEGIN").val();
		var CHUSHENGRIQIEND=$("#CHUSHENGRIQIEND").val();
		var RUXUESHIJIANBEGIN=$("#RUXUESHIJIANBEGIN").val();
		var RUXUESHIJIANIEND=$("#RUXUESHIJIANIEND").val();
		var TOUDANGCHENGJIQI = $("#TOUDANGCHENGJIQI").val();
		var TOUDANGCHENGJIZHI = $("#TOUDANGCHENGJIZHI").val();
		//班级下拉框
		var banji=$("#banji").val();
			var banji_str='';
			if(banji!=null){
				for(var i=0;i<banji.length;i++){
					banji_str+=banji[i]+',';
  			}
			}
			
			//在学状态下拉
			var zxzt=$('#zxzt').val();
			var zxzt_str='';
			if(zxzt!=null){
				for(var i=0;i<zxzt.length;i++){
					zxzt_str+=zxzt[i]+',';
  			}
			}
			
			//层次
			var cengci=$("#cengci").val();
			var cengci_str='';
			if(cengci!=null){
				for(var i=0;i<cengci.length;i++){
					cengci_str+=cengci[i]+',';
  			}
			}
			
			//批次
			var pici=$("#pici").val();
			var pici_str='';
			if(pici!=null){
				for(var i=0;i<pici.length;i++){
					pici_str+=pici[i]+',';
  			}
			}
			
			//学籍类型
			var schoolroll=$("#schoolroll").val();
			var schoolroll_str='';
			var schoolroll_null='';
			if(schoolroll!=null){
				for(var i=0;i<schoolroll.length;i++){
					if("kong"==schoolroll[i]){
  						schoolroll_null="kong";
  					}
					
					schoolroll_str+=schoolroll[i]+',';
  			}
			}
			//计划性质
			var planenature=$("#planenature").val();
			var planenature_str='';
			if(planenature!=null){
				for(var i=0;i<planenature.length;i++){
					planenature_str+=planenature[i]+',';
  			}
			}
			//科类
			var kelei=$("#kelei").val();
			var kelei_str='';
			if(kelei!=null){
				for(var i=0;i<kelei.length;i++){
					kelei_str+=kelei[i]+',';
				}
			}
			//入学年份
  			var grade=$("#grade").val();
  			var grade_str='';
  			if(grade!=null){
  				for(var i=0;i<grade.length;i++){
  					grade_str+=grade[i]+',';
      			}
  			}
  		   //民族
  			var minzu=$("#minzu").val();
  			var minzu_str='';
  			if(minzu!=null){
  				for(var i=0;i<minzu.length;i++){
  					minzu_str+=minzu[i]+',';
  				}
  			}
  			//政治面貌
  			var zzmm=$("#zzmm").val();
  			var zzmm_str='';
  			if(zzmm!=null){
  				for(var i=0;i<zzmm.length;i++){
  					zzmm_str+=zzmm[i]+',';
  				}
  			}
  			//家庭成员关系
  			var jtcygx=$("#jtcygx").val();
  			var jtcygx_str='';
  			if(jtcygx!=null){
  				for(var i=0;i<jtcygx.length;i++){
  					jtcygx_str+=jtcygx[i]+',';
  				}
  			}
  			//户口性质
  			var hkxz=$("#hkxz").val();
  			var hkxz_str='';
  			if(hkxz!=null){
  				for(var i=0;i<hkxz.length;i++){
  					hkxz_str+=hkxz[i]+',';
  				}
  			}
  			//健康状况
  			var jkzk=$("#jkzk").val();
  			var jkzk_str='';
  			if(jkzk!=null){
  				for(var i=0;i<jkzk.length;i++){
  					jkzk_str+=jkzk[i]+',';
  				}
  			}
  			//学生类型
  			var xslx=$("#xslx").val();
  			var xslx_str='';
  			if(xslx!=null){
  				for(var i=0;i<xslx.length;i++){
  					xslx_str+=xslx[i]+',';
  				}
  			}
  			//是否贫困
  			var sfpk=$("#sfpk").val();
  			var sfpk_str='';
  			if(sfpk!=null){
  				for(var i=0;i<sfpk.length;i++){
  					sfpk_str+=sfpk[i]+',';
  				}
  			}
  			//升学标识
  			var sxbs=$("#sxbs").val();
  			var sxbs_str='';
  			var sxbs_null='';
  			if(sxbs!=null){
  				for(var i=0;i<sxbs.length;i++){
  					if("kong"==sxbs[i]){
  						sxbs_null="kong";
  					}
  					sxbs_str+=sxbs[i]+',';
  				}
  			}
  			//学制
  			var xuezhi=$("#xuezhi").val();
  			var xuezhi_str='';
  			if(xuezhi!=null){
  				for(var i=0;i<xuezhi.length;i++){
  					xuezhi_str+=xuezhi[i]+',';
  				}
  			}
  			//迎新
  			var YX = $("#yx").val();
		window.location.href=encodeURI(_basepath+'stuinfo/exportExcel.json?seText='+searchText+
				'&&DEPARTMENT_PKID='+orgtree+'&&MONEYQI='+moneyqi+
				"&&MONEYZHI="+moneyzhi+"&&ZXZT="+zxzt_str+"&&NIANJI="+grade_str+"&&CENGCI="+cengci_str+"&&PICI="+pici_str+
				"&&IS_TONGGUO="+IS_TONGGUO+"&SCHOOLROLL="+schoolroll_str+"&SCHOOLROLLNULL="+schoolroll_null+"&PLANENATURE="+planenature_str+"&KELEI="+kelei_str+
				"&QRXX="+QRXX+"&QRZY="+QRZY+"&STU_FROM="+STU_FROM+"&BANJI="+banji_str+"&NATION_PKID="+minzu_str+
				"&ZHENGZHIMIANMAO_PKID="+jtcygx_str+"&ZHENGZHIMIANMAO_PKID="+zzmm_str+"&JTCYGX_PKID="+jtcygx_str+
				"&HUKOUXINGZHI_PKID="+hkxz_str+"&JKZK_PKID="+jkzk_str+"&POOR_PKID="+sfpk_str+"&SHENGXUEBIAOSHI_PKID="+sxbs_str+"&SHENGXUEBIAOSHI_PKIDNULL="+sxbs_null+
				"&XUEZHI_PKID="+xuezhi_str+"&XSLX_PKID="+xslx_str+"&BEIZHU="+beizhu+"&XINGBIE="+XINGBIE
				+"&SHOUJI="+SHOUJI+"&KAOSHENGHAO="+KAOSHENGHAO+"&YOUZHENGBIANMA="+YOUZHENGBIANMA+"&JTCYXM="+JTCYXM
				+"&JTCYLXDH="+JTCYLXDH+"&JIATINGZHUZHI="+JIATINGZHUZHI+"&ZXMC="+ZXMC+"&LUQUZHUANYE="+LUQUZHUANYE
				+"&CHUSHENGRIQI="+CHUSHENGRIQI+"&IS_RECEIVED="+IS_RECEIVED+"&ZCXJSJBEGIN="+ZCXJSJBEGIN+"&ZCXJSJEND="+ZCXJSJEND
				+"&CHUSHENGRIQIBEGIN="+CHUSHENGRIQIBEGIN+"&CHUSHENGRIQIEND="+CHUSHENGRIQIEND+"&RUXUESHIJIANBEGIN="+RUXUESHIJIANBEGIN
				+"&RUXUESHIJIANIEND="+RUXUESHIJIANIEND+"&resultColumns="+resultColumns
				+'&TOUDANGCHENGJIQI='+TOUDANGCHENGJIQI+'&TOUDANGCHENGJIZHI='+TOUDANGCHENGJIZHI+'&YX='+YX);
	});
	

	//批量删除按钮绑定单机事件
	$('#btn_del').bind('click',function(){

		var pkids=stu.getIdSelections();
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
			    stu.batchDel(pkids);
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
	stu.getTab=function () {
		//获得按钮权限
		var SESSION_MENU_BUTTONS = eval("(" + $("#SESSION_MENU_BUTTONS").val().replace(/=/g,':') + ")");
		// 引例四
		var url = _basepath+"stucjinfo/getstutable.json";
		$('#stuinfotable').bootstrapTable(
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
							showColumns : true, //显示下拉框勾选要显示的列  
							showRefresh : false, //显示刷新按钮  --只是前台刷新，个人觉得没什么用
							minimumCountColumns : 2, //最少允许的列数
							sidePagination : "server", //表示服务端请求  
							totalRows : 0, // server side need to set
							singleSelect : false,
							clickToSelect : true,
							onColumnSwitch: function(field,checked){

								var resultColumnList = $('#stuinfotable').bootstrapTable('getVisibleColumns');
								var resultColumns = "";		
								$.each(resultColumnList, function(i, column) {
									if(column.field=='0'/*||column.field=='opt'*/){
										return;
									}else{
										resultColumns+=column.field+",";
									}									
						     	});
								$.post("stuinfo/updateShowCols.json",{table_show_cols:resultColumns,table_name:'T_STUDENT_CJ'},function(data){
									if(data.result=="success"){
										colStr=resultColumns;//重新初始化字段字符串
									}
								})
							},
							onLoadSuccess:function(data){
								res_data=data;
								$('.bootstrap-table tr td').each(function () {
					                $(this).attr("title", $(this).text());
					                $(this).css("cursor", 'pointer');
					            });
							},
							onDblClickRow:function(row){
							},
							queryParams : function getParams(params) {
								//班级下拉框
								var banji=$("#banji").val();
				      			var banji_str='';
				      			if(banji!=null){
				      				for(var i=0;i<banji.length;i++){
				      					banji_str+=banji[i]+',';
				          			}
				      			}
				      			
				      			//在学状态下拉
				      			var zxzt=$('#zxzt').val();
				      			var zxzt_str='';
				      			if(zxzt!=null){
				      				for(var i=0;i<zxzt.length;i++){
				      					zxzt_str+=zxzt[i]+',';
				          			}
				      			}
				      			
				      			//层次
				      			var cengci=$("#cengci").val();
				      			var cengci_str='';
				      			if(cengci!=null){
				      				for(var i=0;i<cengci.length;i++){
				      					cengci_str+=cengci[i]+',';
				          			}
				      			}
				      			
				      			//批次
				      			var pici=$("#pici").val();
				      			var pici_str='';
				      			if(pici!=null){
				      				for(var i=0;i<pici.length;i++){
				      					pici_str+=pici[i]+',';
				          			}
				      			}
				      			
				      			//学籍类型
				      			var schoolroll=$("#schoolroll").val();
				      			var schoolroll_str='';
				      			var schoolroll_null='';
				      			if(schoolroll!=null){
				      				for(var i=0;i<schoolroll.length;i++){
				      					if("kong"==schoolroll[i]){
				      						schoolroll_null="kong";
				      					}
				      					schoolroll_str+=schoolroll[i]+',';
				          			}
				      			}
				      			//计划性质
				      			var planenature=$("#planenature").val();
				      			var planenature_str='';
				      			if(planenature!=null){
				      				for(var i=0;i<planenature.length;i++){
				      					planenature_str+=planenature[i]+',';
				          			}
				      			}
				      			//科类
				      			var kelei=$("#kelei").val();
				      			var kelei_str='';
				      			if(kelei!=null){
				      				for(var i=0;i<kelei.length;i++){
				      					kelei_str+=kelei[i]+',';
				          			}
				      			}
				      			//入学年份
				      			var grade=$("#grade").val();
				      			var grade_str='';
				      			if(grade!=null){
				      				for(var i=0;i<grade.length;i++){
				      					grade_str+=grade[i]+',';
				          			}
				      			}
				      			//民族
				      			var minzu=$("#minzu").val();
				      			var minzu_str='';
				      			if(minzu!=null){
				      				for(var i=0;i<minzu.length;i++){
				      					minzu_str+=minzu[i]+',';
				      				}
				      			}
				      			//政治面貌
				      			var zzmm=$("#zzmm").val();
				      			var zzmm_str='';
				      			if(zzmm!=null){
				      				for(var i=0;i<zzmm.length;i++){
				      					zzmm_str+=zzmm[i]+',';
				      				}
				      			}
				      			//家庭成员关系
				      			var jtcygx=$("#jtcygx").val();
				      			var jtcygx_str='';
				      			if(jtcygx!=null){
				      				for(var i=0;i<jtcygx.length;i++){
				      					jtcygx_str+=jtcygx[i]+',';
				      				}
				      			}
				      			//户口性质
				      			var hkxz=$("#hkxz").val();
				      			var hkxz_str='';
				      			if(hkxz!=null){
				      				for(var i=0;i<hkxz.length;i++){
				      					hkxz_str+=hkxz[i]+',';
				      				}
				      			}
				      			//健康状况
				      			var jkzk=$("#jkzk").val();
				      			var jkzk_str='';
				      			if(jkzk!=null){
				      				for(var i=0;i<jkzk.length;i++){
				      					jkzk_str+=jkzk[i]+',';
				      				}
				      			}
				      		    //学籍类型
				      			var xslx=$("#xslx").val();
				      			var xslx_str='';
				      			if(xslx!=null){
				      				for(var i=0;i<xslx.length;i++){
				      					xslx_str+=xslx[i]+',';
				          			}
				      			}
				      			//是否贫困
				      			var sfpk=$("#sfpk").val();
				      			var sfpk_str='';
				      			if(sfpk!=null){
				      				for(var i=0;i<sfpk.length;i++){
				      					sfpk_str+=sfpk[i]+',';
				      				}
				      			}
				      			//升学标识
				      			var sxbs=$("#sxbs").val();
				      			var sxbs_str='';
				      			var sxbs_strnull='';
				      			if(sxbs!=null){
				      				for(var i=0;i<sxbs.length;i++){
				      					if("kong"==sxbs[i]){
				      						sxbs_strnull="kong";
				      					}
				      					sxbs_str+=sxbs[i]+',';
				      				}
				      			}
				      			//录取专业
				      			var  flag=$("#kongcheckbox").is(':checked');
				      			if(flag){  //选中空
				      				var LUQUZHUANYE="kong";
				      			}else{
				      				var LUQUZHUANYE=$("#LUQUZHUANYE").val();
				      			}
				      			
				      			//学制
				      			var xuezhi=$("#xuezhi").val();
				      			var xuezhi_str='';
				      			if(xuezhi!=null){
				      				for(var i=0;i<xuezhi.length;i++){
				      					xuezhi_str+=xuezhi[i]+',';
				      				}
				      			}
								var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
									limit : params.limit, //页面大小
									seText : $('#search').val(),
									DEPARTMENT_PKID:$('#orgtree').val(),
									MONEYQI:$('#moneyqi').val(),
									MONEYZHI:$('#moneyzhi').val(),
									BEIZHU:$('#beizhu').val(),
									ZXZT:zxzt_str,
									NIANJI:grade_str,
									CENGCI:cengci_str,
									PICI:pici_str,
									IS_TONGGUO:$("#IS_TONGGUO").val(),
									SCHOOLROLL:schoolroll_str,
									SCHOOLROLLNULL:schoolroll_null,
									PLANENATURE:planenature_str,
									KELEI:kelei_str,
									QRXX:$("#qrxx").val(),
									QRZY:$("#qrzy").val(),
									STU_FROM:$("#stu_from").val(),
									BANJI:banji_str,
									NATION_PKID:minzu_str,
									ZHENGZHIMIANMAO_PKID:zzmm_str,
									JTCYGX_PKID:jtcygx_str,
									HUKOUXINGZHI_PKID:hkxz_str,
									JKZK_PKID:jkzk_str,
									XSLX_PKID:xslx_str,
									POOR_PKID:sfpk_str,
									SHENGXUEBIAOSHI_PKID:sxbs_str,
									SHENGXUEBIAOSHI_PKIDNULL:sxbs_strnull,
									XUEZHI_PKID:xuezhi_str,
									XINGBIE:$("#XINGBIE").val(),
									SHOUJI:$("#SHOUJI").val(),
									KAOSHENGHAO:$("#KAOSHENGHAO").val(),
									YOUZHENGBIANMA:$("#YOUZHENGBIANMA").val(),
									JTCYXM:$("#JTCYXM").val(),
									JTCYLXDH:$("#JTCYLXDH").val(),
									JIATINGZHUZHI:$("#JIATINGZHUZHI").val(),
									ZXMC:$("#ZXMC").val(),
									LUQUZHUANYE:LUQUZHUANYE,		
									CHUSHENGRIQI:$("#CHUSHENGRIQI").val(),
									IS_RECEIVED:$("#IS_RECEIVED").val(),
									ZCXJSJBEGIN:$("#ZCXJSJBEGIN").val(),
									ZCXJSJEND:$("#ZCXJSJEND").val(),
									CHUSHENGRIQIBEGIN:$("#CHUSHENGRIQIBEGIN").val(),
									CHUSHENGRIQIEND:$("#CHUSHENGRIQIEND").val(),
									RUXUESHIJIANBEGIN:$("#RUXUESHIJIANBEGIN").val(),
									RUXUESHIJIANIEND:$("#RUXUESHIJIANIEND").val(),
									TOUDANGCHENGJIQI:$("#TOUDANGCHENGJIQI").val(),
									TOUDANGCHENGJIZHI:$("#TOUDANGCHENGJIZHI").val(),
									sortName : this.sortName,
									sortOrder : this.sortOrder,
									pageindex : this.pageNumber,
									YX:$("#YX").val()
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
										field: 'XSLX_PKID',
										align : "center",
										halign : 'center'
									},
									{
										field : 'SHENFENZHENGHAO',
										title:'身份证',
										align : "center",
										halign : 'center',
										sortable : false,
										width:'200px'
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
										field : 'XINGBIE',
										title : '性别',
										align : "center",
										halign : 'center',
										sortable : false,
										width:'50px'
									},
									{
										field: 'SHOUJI',//可不加  
										title : '联系电话',//标题  可不加
										align : "center",
										halign : 'center',
										width:'150px'
									},
									{
										field : 'XUEHAO',
										title : '学号',
										align : "center",
										halign : 'center',
										sortable : false,
										width:'150px'
									},
									{
										field : 'NIANJI',
										align : "center",
										halign : 'center',
										title : '入学年份',
										width:'150px'
											

									},
									{
										field : 'RUXUESHIJIAN',
										align : "center",
										halign : 'center',
										title : '入学时间',
										width:'150px'
											
											
									},
									{
										field : 'ZUZHINAME',
										align : "center",
										halign : 'center',
										title : '院校专业',
										width:'150px'

									},
									{
										field : 'ZUZHIBIANMA',
										align : "center",
										halign : 'center',
										title : '院校专业代码',
										width:'150px'
											
									},
									{
										field : 'CLASS_NAME',
										align : "center",
										halign : 'center',
										title : '所在班级',
										width:'150px'
									},
									{
										field : 'IS_SURE_INF',
										align : "center",
										halign : 'center',
										title : ' 是否确认信息',
										width:'120px'

									},
									{
										field : 'IS_SURE_DEP',
										align : "center",
										halign : 'center',
										title : ' 是否确认专业',
										width:'120px'

									},
									{
										field : 'CENGCI',
										align : "center",
										halign : 'center',
										title : '学生类型',
										width:'150px'

									},
									{
										field : 'PICI',
										align : "center",
										halign : 'center',
										title : '批次',
										width:'150px'

									},
									{
										field : 'STUDENT_SOURCE',
										align : "center",
										halign : 'center',
										title : '计划性质',
										width:'150px',
										formatter : function(value, row,
												index) {
											if(row.STUDENT_SOURCE == '1'){//
												return '计划内';
											}
											if(row.STUDENT_SOURCE == '2'){//
												return '计划外'
											}
										}
											
									},
									{
										field : 'IS_TONGGUO',
										align : "center",
										halign : 'center',
										title : '审核状态',
										width:'100px',
										
										formatter : function(value, row,
												index) {
											if(row.IS_TONGGUO == '1'){//表示审核通过
												return '通过';
											}
											if(row.IS_TONGGUO == '0'){//表示审核通过
												return '待审核'
											}
										}

									},
									{
										field : 'BAODAOSJ',
										align : "center",
										halign : 'center',
										title : '报到时间',
										width:'150px'
											
									},	
									{
										field : 'IS_RECEIVED',
										align : "center",
										halign : 'center',
										title : '报到状态',
										width:'100px',
										formatter : function(value, row,
												index) {
											if(row.IS_RECEIVED == 'Y'){
												return '已报到';
											}
											if(row.IS_RECEIVED == 'N'){
												return '未报到'
											}
										}

									},
									{
										field : 'ZXZT',
										align : "center",
										halign : 'center',
										title : '在学状态',
										width:'100px'

									},
															
									{
										field : 'ISPAY',
										align : "center",
										halign : 'center',
										title : '缴费状态',
										width:'100px',										
									},									
									{
										field : 'KAOSHENGHAO',
										align : "center",
										halign : 'center',
										title : '考生号',
										width:'150px'
											
									},									
									{
										field : 'CHUSHENGRIQI',
										align : "center",
										halign : 'center',
										title : '出生日期',
										width:'150px'
											
									},									
									{
										field : 'MINZU',
										align : "center",
										halign : 'center',
										title : '民族',
										width:'150px'
											
									},									
									{
										field : 'ZHENGZHIMIANMAO',
										align : "center",
										halign : 'center',
										title : '政治面貌',
										width:'150px'
											
									},									
									{
										field : 'YOUZHENGBIANMA',
										align : "center",
										halign : 'center',
										title : '邮政编码',
										width:'150px'
											
									},																										
									{
										field : 'JTCYXM',
										align : "center",
										halign : 'center',
										title : '家庭成员姓名',
										width:'150px'
											
									},									
									{
										field : 'JTCYGX',
										align : "center",
										halign : 'center',
										title : '家庭成员关系',
										width:'150px'
											
									},									
									{
										field : 'JTCYLXDH',
										align : "center",
										halign : 'center',
										title : '家庭成员联系电话',
										width:'150px'
											
									},
									{
										field : 'JIATINGZHUZHI',
										align : "center",
										halign : 'center',
										title : '家庭地址',
										width:'150px'
											
									},	
									{
										field : 'HUKOUXINGZHI',
										align : "center",
										halign : 'center',
										title : '户口性质',
										width:'150px'
											
									},	
									{
										field : 'JKZK',
										align : "center",
										halign : 'center',
										title : '健康状况',
										width:'150px'
											
									},	
									{
										field : 'SHIFOUPINKUN',
										align : "center",
										halign : 'center',
										title : '是否贫困',
										width:'150px'
											
									},	
									{
										field : 'ZXMC',
										align : "center",
										halign : 'center',
										title : '中学名称',
										width:'150px'
											
									},	
									{
										field : 'LUQUZHUANYE1',
										align : "center",
										halign : 'center',
										title : '录取专业',
										width:'150px'
											
									},	
									{
										field : 'TOUDANGCHENGJI',
										align : "center",
										halign : 'center',
										title : '投档成绩',
										width:'150px'
											
									},	
									{
										field : 'XUEZHI',
										align : "center",
										halign : 'center',
										title : '学制',
										width:'150px'
											
									},	
									{
										field : 'KEBIE',
										align : "center",
										halign : 'center',
										title : '科别',
										width:'150px'
											
									},		
									{
										field : 'XUEJILEIXING',
										align : "center",
										halign : 'center',
										title : '学籍类型',
										width:'150px'
											
									},	
									{
										field : 'XUESHENGLEIXING',
										align : "center",
										halign : 'center',
										title : '学生类型',
										width:'150px'
											
									},	
									{
										field : 'ZCXJSJ',
										align : "center",
										halign : 'center',
										title : '注册学籍时间',
										width:'150px'
											
									},	
									{
										field : 'SHENGXUEBIAOSHI',
										align : "center",
										halign : 'center',
										title : '升学标识',
										width:'150px'
											
									},										
									{
										field : 'ZHUANYEBIANDONG',
										align : "center",
										halign : 'center',
										title : '专业变动',
										width:'150px'
											
									},	
									{
										field : 'XUEYUANNAME',
										align : "center",
										halign : 'center',
										title : '所在学院',
										width:'150px'
											
									},	
									{
										field : 'FENSUZHUANGTAI',
										align : "center",
										halign : 'center',
										title : '分宿状态',
										width:'100px',
										formatter : function(value, row,
												index) {
											if(row.FENSUZHUANGTAI > 0){
												return '是';
											}
											else{
												return '否'
											}
										}
											
									},
									{
										field : 'IS_TAKEKEY',
										align : "center",
										halign : 'center',
										title : '领取宿舍钥匙',
										width:'120px',
										formatter : function(value, row,
												index) {
											if(row.IS_TAKEKEY > 0){
												return '已领取';
											}
											else{
												return '未领取'
											}
										}
											
									},
									{
										field : 'REMARKS1',
										align : "center",
										halign : 'center',
										title : '备注1',
										width:'150px'
											
									},
									{
										field : 'REMARKS2',
										align : "center",
										halign : 'center',
										title : '备注2',
										width:'150px'
											
									},
									{
										field : 'REMARKS3',
										align : "center",
										halign : 'center',
										title : '备注3',
										width:'150px'
											
									},
									{
										field : 'REMARKS4',
										align : "center",
										halign : 'center',
										title : '备注4',
										width:'150px'
											
									},
									{
										field : 'REMARKS5',
										align : "center",
										halign : 'center',
										title : '备注5',
										width:'150px'
											
									},
									{
										field : 'REMARKS6',
										align : "center",
										halign : 'center',
										title : '备注6',
										width:'150px'
											
									},
									{
										field : 'REMARKS7',
										align : "center",
										halign : 'center',
										title : '备注7',
										width:'150px'
											
									},
									{
										field : 'REMARKS8',
										align : "center",
										halign : 'center',
										title : '备注8',
										width:'150px'
											
									},
									
									{
										field : 'opt',
										title : '操作',
										align : "center",
										halign : 'center',										
										width:'150px',
										formatter : function(value, row,
												index) {
											if(row.IS_TONGGUO == '1'){//表示审核通过
												return [
												        SESSION_MENU_BUTTONS.xslb_gjtz_yx == 1?'<a class="highEdit ml10" href="javascript:void(0)" title="高级调整"><i class="fa fa-exchange"></i></a>&nbsp;&nbsp;':'',
														SESSION_MENU_BUTTONS.xslb_sc_yx == 1?'<a class="cancel ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>&nbsp;&nbsp;':'' ]
														.join('');
											}else{
												return [
												        SESSION_MENU_BUTTONS.xslb_bj_yx == 1?'<a class="edit ml10" href="javascript:void(0)" title="修改"><i class="fa fa-pencil" ></i></a>&nbsp;&nbsp;':'',
												        SESSION_MENU_BUTTONS.xslb_gjtz_yx == 1?'<a class="highEdit ml10" href="javascript:void(0)" title="高级调整"><i class="fa fa-exchange"></i></a>&nbsp;&nbsp;':'',
												        SESSION_MENU_BUTTONS.xslb_sc_yx == 1?'<a class="cancel ml10" href="javascript:void(0)" title="删除"><i class="fa fa-trash-o"></i></a>&nbsp;&nbsp;':'',
												        '<a id="detailQuery" style="cursor:pointer;"><span title="查看详情" class="fa fa-search"></span></a>']
												.join('');
											}
										}, //紫色为添加图标（icon），插件：font-awesome，效果图见底部。
										 events : {
											'click .edit' : function(e,value, row, index) {
												  //编辑  编辑人员
												  $(".jf_szright").load(_basepath+'stucjinfo/goEdit.json?PKID='+row.PKID+"&isEdit="+"isEdit");
	    							        
											},
											'click .highEdit' : function(e,value, row, index) {
												  //高级调整  编辑人员
												  $(".jf_szright").load(_basepath+'stucjinfo/goEdit.json?PKID='+row.PKID);
	    							        
											},
											'click #detailQuery' : function(e,value, row, index) {
												//去查看详情页面
												 $(".jf_szright").load(_basepath+'stucjinfo/goDetail.json?PKID='+row.PKID);
											},

											'click .cancel' : function(e,value, row, index) {
												
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
																url:_basepath+'stuinfo/delsingledata.json?PKID='+row.PKID,
																 success:function(data){
																	 if("success" == data.rst){
																		 layer.msg("删除成功！");
																		 //删除成功后刷新表格
																		  $("#stuinfotable").bootstrapTable('refresh', {url:url});
																	 }
																	 if("paid" == data.rst){
																		 layer.msg("有过缴费记录的学生不能删除!");
																	 }
																	 if("useddorm" == data.rst){
																		 layer.msg("有床位的学生或已经预定床位的学生不能删除!");
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
	    $('#stuinfotable').bootstrapTable('hideColumn', 'PKID');//隱藏列
	    $('#stuinfotable').bootstrapTable('hideColumn', 'XSLX_PKID');//隱藏列
		if(colStr!=null && colStr!=''){
			var resultColumnList = $('#stuinfotable').bootstrapTable('getVisibleColumns');

			$.each(resultColumnList, function(i, column) {
				if(column.field=='0'){
					return;
				}
				$('#stuinfotable').bootstrapTable('hideColumn', column.field);
	     	});		
			var array=colStr.split(",");
			for(var i=0;i<array.length;i++){
				var field=array[i];
				if(field==''){
					continue;
				}
				$('#stuinfotable').bootstrapTable('showColumn', field);
				
			}
			
		}
	};
	//加载表格数据
	stu.getTab();
	//更多查询条件
	$('#more').click(function(){
		//所属组织
		if($('#icon').hasClass('fa-angle-double-down')){
			$('#icon').removeClass('fa-angle-double-down');
			$('#icon').addClass('fa-angle-double-up');
			$("#moreQueryArea").show();
		}else{
			$('#icon').removeClass('fa-angle-double-up');
			$('#icon').addClass('fa-angle-double-down');
			$("#moreQueryArea").hide();
		}
		
	});
	//确认专业
	$("#btn_qrzy").click(function(){
		var pkids=stu.getIdSelections();
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
          message: '你确定要确认专业吗？',
          closable: false, 
            buttons: [{
			    label: '确定',
			    cssClass: 'btn-danger',
			    action: function(dialogRef){
			    stu.batchConfirmProfess(pkids);
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
	//确认信息
	$("#btn_qrxx").click(function(){
		var pkids=stu.getIdSelections();
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
          message: '你确定要确认信息吗？',
          closable: false, 
            buttons: [{
			    label: '确定',
			    cssClass: 'btn-danger',
			    action: function(dialogRef){
			    stu.batchConfirmInfo(pkids);
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
	//批量确认专业方法
	stu.batchConfirmProfess=function(pkids){
		$.post(_basepath+"stuinfo/batchConfirmProfess.json?pkids="+pkids,function(data){
			if(data.result=="success"){
				layer.msg("操作成功!");
				$("#stuinfotable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}
		});
	}
	//批量确认专业方法
	stu.batchConfirmInfo=function(pkids){
		$.post(_basepath+"stuinfo/batchConfirmInfo.json?pkids="+pkids,function(data){
			if(data.result=="success"){
				layer.msg("操作成功!");
				$("#stuinfotable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
			}
		});
	}
	
	$('#grade').multiselect({
   	 enableFiltering: true,
   	 includeSelectAllOption: true,
   	 selectAllText: ' 全选',
   	 nonSelectedText: '请选择入学年份',
        allSelectedText: '全部选择',
        selectAllJustVisible: true,
   	 filterPlaceholder:'请输入入学年份名称',
   	 buttonWidth: '183px'//设置字体过长显示...
   });
	 $('#banji').multiselect({
   	 enableFiltering: true,
   	 includeSelectAllOption: true,
   	 selectAllText: ' 全选',
   	 nonSelectedText: '请选择班级',
        allSelectedText: '全部选择',
        selectAllJustVisible: true,
   	 filterPlaceholder:'请输入班级名称',
   	 buttonWidth: '183px'//设置字体过长显示...
   });
	 $('#zxzt').multiselect({
   	 enableFiltering: true,
   	 includeSelectAllOption: true,
   	 selectAllText: ' 全选',
   	 nonSelectedText: '请选择在学状态',
        allSelectedText: '全部选择',
        selectAllJustVisible: true,
   	 filterPlaceholder:'请输入在学状态名称',
   	 buttonWidth: '183px'//设置字体过长显示...
   });
	 $('#cengci').multiselect({
   	 enableFiltering: true,
   	 includeSelectAllOption: true,
   	 selectAllText: ' 全选',
   	 nonSelectedText: '请选择学生类型',
        allSelectedText: '全部选择',
        selectAllJustVisible: true,
   	 filterPlaceholder:'请输入学生类型名称',
   	 buttonWidth: '183px'//设置字体过长显示...
   });
	 $('#pici').multiselect({
   	 enableFiltering: true,
   	 includeSelectAllOption: true,
   	 selectAllText: ' 全选',
   	 nonSelectedText: '请选择批次',
        allSelectedText: '全部选择',
        selectAllJustVisible: true,
   	 filterPlaceholder:'请输入批次名称',
   	 buttonWidth: '183px'//设置字体过长显示...
   });
	 $('#schoolroll').multiselect({
   	 enableFiltering: true,
   	 includeSelectAllOption: true,
   	 selectAllText: ' 全选',
   	 nonSelectedText: '请选择学籍类型',
        allSelectedText: '全部选择',
        selectAllJustVisible: true,
   	 filterPlaceholder:'请输入学籍类型',
   	 buttonWidth: '183px'//设置字体过长显示...
   });
	 $('#planenature').multiselect({
   	 enableFiltering: true,
   	 includeSelectAllOption: true,
   	 selectAllText: ' 全选',
   	 nonSelectedText: '请选择计划性质',
        allSelectedText: '全部选择',
        selectAllJustVisible: true,
   	 filterPlaceholder:'请输入计划性质',
   	 buttonWidth: '183px'//设置字体过长显示...
   });
	 $('#kelei').multiselect({
   	 enableFiltering: true,
   	 includeSelectAllOption: true,
   	 selectAllText: ' 全选',
   	 nonSelectedText: '请选择科别',
        allSelectedText: '全部选择',
        selectAllJustVisible: true,
   	 filterPlaceholder:'请输入科别',
   	 buttonWidth: '183px'//设置字体过长显示...
   });
	 $('#minzu').multiselect({
		 enableFiltering: true,
		 includeSelectAllOption: true,
		 selectAllText: ' 全选',
		 nonSelectedText: '请选择民族',
		 allSelectedText: '全部选择',
		 selectAllJustVisible: true,
		 filterPlaceholder:'请输入民族',
		 buttonWidth: '183px'//设置字体过长显示...
	 });
	 $('#zzmm').multiselect({
		 enableFiltering: true,
		 includeSelectAllOption: true,
		 selectAllText: ' 全选',
		 nonSelectedText: '请选择政治面貌',
		 allSelectedText: '全部选择',
		 selectAllJustVisible: true,
		 filterPlaceholder:'请输入政治面貌',
		 buttonWidth: '183px'//设置字体过长显示...
	 });
	 $('#jtcygx').multiselect({
		 enableFiltering: true,
		 includeSelectAllOption: true,
		 selectAllText: ' 全选',
		 nonSelectedText: '请选择家庭成员关系',
		 allSelectedText: '全部选择',
		 selectAllJustVisible: true,
		 filterPlaceholder:'请输入家庭成员关系',
		 buttonWidth: '183px'//设置字体过长显示...
	 });
	 $('#hkxz').multiselect({
		 enableFiltering: true,
		 includeSelectAllOption: true,
		 selectAllText: ' 全选',
		 nonSelectedText: '请选择户口性质',
		 allSelectedText: '全部选择',
		 selectAllJustVisible: true,
		 filterPlaceholder:'请输入户口性质',
		 buttonWidth: '183px'//设置字体过长显示...
	 });
	 $('#jkzk').multiselect({
		 enableFiltering: true,
		 includeSelectAllOption: true,
		 selectAllText: ' 全选',
		 nonSelectedText: '请选择健康状况',
		 allSelectedText: '全部选择',
		 selectAllJustVisible: true,
		 filterPlaceholder:'请输入健康状况',
		 buttonWidth: '183px'//设置字体过长显示...
	 });
	 $('#xslx').multiselect({
		 enableFiltering: true,
		 includeSelectAllOption: true,
		 selectAllText: ' 全选',
		 nonSelectedText: '请选择学生类型',
		 allSelectedText: '全部选择',
		 selectAllJustVisible: true,
		 filterPlaceholder:'请输入学生类型',
		 buttonWidth: '183px'//设置字体过长显示...
	 });
	 $('#sfpk').multiselect({
		 enableFiltering: true,
		 includeSelectAllOption: true,
		 selectAllText: ' 全选',
		 nonSelectedText: '请选择是否贫困',
		 allSelectedText: '全部选择',
		 selectAllJustVisible: true,
		 filterPlaceholder:'请输入是否贫困',
		 buttonWidth: '183px'//设置字体过长显示...
	 });
	 $('#sxbs').multiselect({
		 enableFiltering: true,
		 includeSelectAllOption: true,
		 selectAllText: ' 全选',
		 nonSelectedText: '请选择升学标识',
		 allSelectedText: '全部选择',
		 selectAllJustVisible: true,
		 filterPlaceholder:'请输入升学标识',
		 buttonWidth: '183px'//设置字体过长显示...
	 });
	 $('#xuezhi').multiselect({
		 enableFiltering: true,
		 includeSelectAllOption: true,
		 selectAllText: ' 全选',
		 nonSelectedText: '请选择学制',
		 allSelectedText: '全部选择',
		 selectAllJustVisible: true,
		 filterPlaceholder:'请输入学制',
		 buttonWidth: '183px'//设置字体过长显示...
	 });
		//报到按钮
		$("#btn_baodao").click(function(){
			var pkids=stu.getIdSelections();
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
	          message: '你确定要报到吗？',
	          closable: false, 
	            buttons: [{
				    label: '确定',
				    cssClass: 'btn-danger',
				    action: function(dialogRef){
				    stu.batchBaoDao(pkids);
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
		//批量通过方法
		stu.batchBaoDao=function(pkids){
			$.post(_basepath+"stuinfo/batchBaoDao.json?pkids="+pkids,function(data){
				if(data.result=="success"){
					layer.msg("操作成功!");
					$("#stuinfotable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
				}
			});
		};
		//领取钥匙
		$("#btn_takeKey").click(function(){
			var pkids=stu.getIdSelections();
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
	          message: '你确定要领取钥匙吗？',
	          closable: false, 
	            buttons: [{
				    label: '确定',
				    cssClass: 'btn-danger',
				    action: function(dialogRef){
				    stu.batchTakeKey(pkids);
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
		//批量通过方法
		stu.batchTakeKey=function(pkids){
			$.post(_basepath+"stuinfo/batchTakeKey.json?pkids="+pkids,function(data){
				if(data.result=="success"){
					layer.msg("操作成功!");
					$("#stuinfotable").bootstrapTable('refresh', {url: _basepath+"stuinfo/getstutable.json"});
				}else if (data.result=="nodorm"){
					layer.msg("勾选的学生尚未分配宿舍!");
				}
			});
		};
})(jQuery, window);
