
/**
 * 缴费比例
 * @param win
 * @param $
 */
(function(win,$,layer){
	
	var page = {};
	
	//显示下拉树
	page.showItemTree = function(){
		var $itemPkid = new Array();
		var $itemName = new Array();
		$('#parendItemTreeview').show();
        var treeData = $("#listItemTreeJsonDataDiv").val();
		$('#parendItemTreeview').treeview({
			bootstrap2 : false, 
			showTags : true,  
			data : treeData,
			showCheckbox : true,
			checkedIcon : "glyphicon glyphicon-check", 
			levels : 2,
			highlightSearchResults:true,
		    onNodeChecked: function(event, data){
		    	$itemPkid.push(data.href);
		    	$itemName.push(data.text);
		    	$("#parent_item_pkid").val($itemPkid);
		    	$("#txt_itemName").val($itemName);
		    },
			onNodeUnchecked: function(event, data){
				$.each($itemPkid, function(){
					if(this==data.href){
						$itemPkid.splice($.inArray(data.href,$itemPkid),1);//JQUERY移除指定数组的元素值
					}
				});
				$.each($itemName, function(){
					if(this==data.text){
						$itemName.splice($.inArray(data.text,$itemName),1);//JQUERY移除指定数组的元素值
					}
				});
				$("#parent_item_pkid").val($itemPkid);
				$("#txt_itemName").val($itemName);
			},
			onNodeSelected: function(event, data){
//				$("#txt_itemName").val(data.text);
//                $("#parendItemTreeview").hide(); 
			}
		 });
	};
	
	//查询按钮点击事件
	$('#checkQuery').click(function(){
		var parent_dep_pkid = $("#orgtree").val();
		var parent_item_pkid = $("#orgtreeItem").val();
		var grade = $("#grade").val();
		var url = _basepath+"reportStat/getPayMsg.json";
		$.ajax({
			type : 'POST',
			url : url,
			async : false,
			data : {
				PARENT_DEP_PKID : parent_dep_pkid,
				PARENT_ITEM_PKID : parent_item_pkid,
				GRADE : grade
			},
			dataType : "json",
			success : function(result) {
				var msgList = result.msgList;
				var oweMoney = 0;//欠费总额
				var okMoney = 0;//完成总额
				var oweRatio = 0;//欠费占比 oweMoney/(oweMoney+okMoney).toFixed(2)
				var okRatio = 0;//完成占比 1-oweRatio
				var oweStudent = 0;//欠费人数
				var okStudent = 0;//完成人数
				var oweSRatio = 0;//欠费人数占比
				var okSRatio = 0;//完成人数占比
				for(var i=0;i<msgList.length;i++){
					if(msgList[i].STATUS=='OWE'){
						oweMoney = msgList[i].MONEY;
						oweStudent = msgList[i].STUCOUNT;
					}else{
						okMoney = msgList[i].MONEY;
						okStudent = msgList[i].STUCOUNT;
					}
				}
				oweRatio = (oweMoney/(oweMoney+okMoney)).toFixed(2);
				okRatio =  (1-oweRatio).toFixed(2);
				oweSRatio = (oweStudent/(oweStudent+okStudent)).toFixed(2);
				okSRatio = (1-oweSRatio).toFixed(2);
				page.cakePayPic(oweMoney,okMoney,oweRatio,okRatio);
				page.cakeStuPic(oweStudent,okStudent,oweSRatio,okSRatio);
//				page.checkEmpty(oweMoney,oweStudent,okMoney,okStudent);
			}
		});
	});
	
	//获取初始化饼图数据
	page.getPieMsg = function(){
		var url = _basepath+"reportStat/getPayMsg.json";
		$.ajax({
			type : 'POST',
			url : url,
			async : false,
			dataType : "json",
			success : function(result) {
				var msgList = result.msgList;
				var oweMoney = 0;//欠费总额
				var okMoney = 0;//完成总额
				var oweRatio = 0;//欠费占比 oweMoney/(oweMoney+okMoney).toFixed(2)
				var okRatio = 0;//完成占比 1-oweRatio
				var oweStudent = 0;//欠费人数
				var okStudent = 0;//完成人数
				var oweSRatio = 0;//欠费人数占比
				var okSRatio = 0;//完成人数占比
				for(var i=0;i<msgList.length;i++){
					if(msgList[i].STATUS=='OWE'){
						oweMoney = msgList[i].MONEY;
						oweStudent = msgList[i].STUCOUNT;
					}else{
						okMoney = msgList[i].MONEY;
						okStudent = msgList[i].STUCOUNT;
					}
				}
				oweRatio = (oweMoney/(oweMoney+okMoney)).toFixed(2);
				okRatio =  (1-oweRatio).toFixed(2);
				oweSRatio = (oweStudent/(oweStudent+okStudent)).toFixed(2);
				okSRatio = (1-oweSRatio).toFixed(2);
				page.cakePayPic(oweMoney,okMoney,oweRatio,okRatio);
				page.cakeStuPic(oweStudent,okStudent,oweSRatio,okSRatio);
//				page.checkEmpty(oweMoney,oweStudent,okMoney,okStudent);
			}
		});
	}
	
	//判空
	page.checkEmpty = function(oweMoney,oweStudent,okMoney,okStudent){
		if(oweMoney==0 && okMoney == 0){
			$("#paypie").html("暂无数据");
		}
		if(oweStudent==0 && okStudent == 0){
			$("#studentpie").html("暂无数据");
		}
	}
	//加载费用饼图
	page.cakePayPic = function(oweMoney,okMoney,oweRatio,okRatio){
		var myChartpie;
		// 基于准备好的dom，初始化echarts图表
		myChartpie = echarts.init(document.getElementById('paypie','customed'));
		option = {
			    title : {
			        text: '费用',
			        subtext: '',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'vertical',
			        x : 'left',
			        data:['欠费('+oweMoney+')','实收('+okMoney+')']
			    },
			    calculable : true,
			    series : [
			        {
			            name:'费用',
			            type:'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:[
			                {value:oweRatio, name:'欠费('+oweMoney+')'},
			                {value:okRatio, name:'实收('+okMoney+')'}
			            ]
			        }
			    ]
			};
			myChartpie.setOption(option);
	}
	
	//加载学生饼图
	page.cakeStuPic = function(oweStudent,okStudent,oweSRatio,okSRatio){
		var myChartpie;
		// 基于准备好的dom，初始化echarts图表
		myChartpie = echarts.init(document.getElementById('studentpie','customed'));
		option = {
			    title : {
			        text: '缴费人数',
			        subtext: '',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'vertical',
			        x : 'left',
			        data:['欠费('+oweStudent+')','完成('+okStudent+')']
			    },
			    calculable : true,
			    series : [
			        {
			            name:'缴费人数',
			            type:'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:[
			                {value:oweSRatio, name:'欠费('+oweStudent+')'},
			                {value:okSRatio, name:'完成('+okStudent+')'}
			            ]
			        }
			    ]
			};
		myChartpie.setOption(option);
	}
	/**
	 * 初始化
	 */
	page.init = function(){
		$.ajaxSetup({
		    aysnc: false // 默认同步加载
		});
		page.getPieMsg();
		//下拉树点击事件
		$("#txt_itemName").click(function() {
			page.showItemTree();
			alert("111");
			$("#parent_item_pkid").val("");
			$("#txt_itemName").val("");
			$(window.document).click(function(e){
				if($(e.target).closest(".list-group-item").length == 0 && $(e.target).closest("#txt_itemName").length == 0){
					$('#parendItemTreeview').hide();
				}
			});
	    });
	};
	
	page.init();
	
})(window,jQuery,layer);


