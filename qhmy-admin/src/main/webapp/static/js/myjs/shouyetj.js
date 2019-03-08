(function($){
	/**
	 * 页面初始化
	 */
	//选择项目后加载三个图
	xmbar(dwarray);
	xmline(kqlarray,bianma);
	xmpie(jrgzarray,gzarray);	
	xmbarlist(dwarray);
	xmpielist1(gzarray1);
	leftdiv();
	allpro();
	
	// 使用柱状图就加载bar模块，按需加载
	var myChartbar;
	function xmbar(dwarray){
		myChartbar = echarts.init(document.getElementById('renshutj'));
		var mcarray = new Array();
		var ssarray = new Array();
		var ljarray = new Array();
		var zcarray = new Array();

		var colorList = [
		                 '#ff7f50','#87cefa','#da70d6','#32cd32','#6495ed',
		                 '#ff69b4','#ba55d3','#cd5c5c','#ffa500','#40e0d0'
		               ];
		var length=dwarray.length;//取实时人数前10的队伍
		if(length>10){
			length=10
		}
		for (var i = 0; i < length; i++) {
			mcarray.push(dwarray[i].DUIZHANGMC)
			ssarray.push(dwarray[i].ssrs)
			ljarray.push(dwarray[i].ljrs)
			zcarray.push(dwarray[i].zcrs)
		}
		option = {
			title : {
				text : '现场人数前十队伍',
				subtext : ''
			},
			tooltip : {
				trigger : 'axis',
				axisPointer: { // 坐标轴指示器，坐标轴触发有效
	                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
	            },
	            backgroundColor: 'rgba(0,0,0,0.5)',
				 formatter: function(params) {
		            // for text color
		            var color = colorList[params[0].dataIndex];
		            var res = '<div style="color:white">';
		            res += '<strong>' + params[0].name + '</strong>'
		            for (var i = 0, l = params.length; i < l; i++) {
		                res += '<br/>' + params[i].seriesName + ' : ' + params[i].data 
		            }
		            res += '</div>';
		            return res;
		        }
			},
			legend : {
				data : [ '现场实时人数', '日累计进场', '队伍在场人数' ],
			y: 'top',
	        x: 'right'
			},
			toolbox : {
				show : false,
				feature : {
					mark : {
						show : true
					},
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				data : mcarray
			} ],
			yAxis : [ {
				type : 'value',
				 boundaryGap: [0, 0],
	             axisLabel: {
	                 formatter: function (value) {
	                     return Math.round(value) + '人';
	                 }
	             }
			} ],
			series : [ {
				name : '现场实时人数',
				type : 'bar',
				itemStyle : {
					normal : {
						color : '#FFAD33', // '#00b24d',
						barBorderColor : '#ccc',
						barBorderWidth : 0,
						barBorderRadius : 1,
						label : {
							show : true,
							position : 'top'
						}
					}
				},
				data : ssarray
			}, {
				name : '日累计进场',
				type : 'bar',
				itemStyle : { 
					normal: {
						 color: '#b7cdbe',
		                 barBorderColor: 'tomato',
		                 barBorderWidth: 0,
		                 barBorderRadius: 1,
						label : {
							show : true,
							position : 'top'
						}
					}
				},
				data : ljarray
			}, {
				name : '队伍在场人数',
				type : 'bar',
				itemStyle : {
					normal : {
						color : '#2aacfb',
						barBorderColor : 'tomato',
						barBorderWidth : 0,
						barBorderRadius : 1,
						label : {
							show : true,
							position : 'top'
						}
					}
				},
				data : zcarray
			} ]
		};

		if(dwarray.length>0){
			myChartbar.setOption(option);
		}else{
			$('#renshutj').css('line-height', '400px');
			$('#renshutj').html("暂无数据")
			$('#renshutj').css('text-align', 'center');
		}
//		var resize = function () {
//	        if (myChartbar)
//	            myChartbar.resize();  
//	    }
//	    window.onresize = resize;
	}
	
	

	// 折线图--出勤率
	// 基于准备好的dom，初始化echarts图表
	var myChartline;
	function xmline(kqlarray,bianma){
		myChartline = echarts.init(document.getElementById('chuqltj'));
		var xarray = new Array();
		var yarray = new Array();
		for (var i = 0; i < kqlarray.length; i++) {
	        	xarray.push(kqlarray[i].riqi);
			yarray.push(kqlarray[i].kaoqinlv);
		}
		option = {
			title : {
				text : '',
				subtext : ''
			},
			tooltip : {
				trigger : 'axis',
				formatter: function (params) {
	                return params[0].name + '<br/>' + '出勤率:' + params[0].data + '%'
	            }
			},
			legend : {
				data : [ '出勤率' ],
				x : 'left'

			},
			toolbox : {
				show : false,
				feature : {
					mark : {
						show : true
					},
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : xarray,
			} ],
			yAxis : [ {
				type : 'value',
				axisLabel : {
					formatter : '{value} %'
				}
			} ],
			series : [ {
				name : '出勤率',
				type : 'line',
				data : yarray,

			} ]
		};


		function queryXY(param) {
			var seriesIndex = param.seriesIndex;
			var dataIndex = param.dataIndex;
			var seriesName = param.seriesName;
			var name = param.name;//这就获取到了x轴对应的 日期 
			var data = param.data;
			var value = param.value;
			$.ajax({
				type : "post",
				dataType : "json",
				url : _basepath +"main/index.do",
				data : {
					riqi : name,
					projectbm:bianma
				},
				success : function(mv) {
//					console.log(mv.varList)
					var kqmxList = mv.varList;
					$('#kqmx').find("tbody").empty();
					for (var i = 0; i < kqmxList.length; i++) {
						var kqv = 0;
						if (kqmxList[i].dtljrs == 0) {
							kqlv = 0;
						} else {
							kqlv = (kqmxList[i].dtljrs / kqmxList[i].zcrs * 100).toFixed(2);
						}
						var kqmxtable = '<tr>' + '<td>' + (i + 1) + '</td>'
								+ '<td><span style="font-size:12pt;font-weight: bolder;">' + kqmxList[i].DUIZHANGMC + '</span></td>'
								+ '<td>' + kqmxList[i].dtljrs + '</td>' + '<td>'
								+ kqmxList[i].zcrs + '</td>' + '<td>' + kqlv
								+ '%</td>' + '</tr>'
						$(kqmxtable).appendTo($('#kqmx').find("tbody"));
					}
					$('<tr id="totalkq"></tr>').appendTo($('#kqmx').find("tbody"));
					$('#xmshye').hide();
					var biaoti= '<span class="lbr-back-to-pro-type-echart" >' + name.replace('/', '月') + '日考勤率</span>'
	                $(biaoti).appendTo('#biaoti');
	                sum('kqtable tr','totalkq');
					$('#bigdiv').show()
					$('#kqmx').show();
					// 由JSON字符串转换为JSON对象
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					console.log(errorThrown);
				}
			});

		}
		//myChartline.on(ecConfig.EVENT.CLICK, queryXY);
		myChartline.on('click',function(params){
			queryXY(params);
			})
		// 为echarts对象加载数据 
			if(kqlarray.length>0){
				myChartline.setOption(option);
			}else{
				$('#chuqltj').css('line-height', '400px');
				$('#chuqltj').html("暂无数据")
				$('#chuqltj').css('text-align', 'center');
			}
//		var resize = function () {
//	        if (myChartline)
//	        	myChartline.resize();  
//	    }
//	    window.onresize = resize;
	}
	
//柱状图点开后的list
	function xmbarlist(dwarray){
		$('#renshutj').bind("click", function() {
			
			$('#dwtable').find("tbody").empty();
			for (var i = 0; i < dwarray.length; i++) {
				var kqv = 0;
				var dwmxtable = '<tr>' + '<td>' + (i + 1) + '</td>'
						+ '<td><span style="font-size:12pt;">' + dwarray[i].DUIZHANGMC+'-'+dwarray[i].FENBAOSHANGMC + '</span></td>'
						+ '<td>' + dwarray[i].ssrs + '</td>' + '<td>'
						+ dwarray[i].ljrs + '</td>' + '<td>'
						+ dwarray[i].zcrs + '</td>' + '</tr>'
				$(dwmxtable).appendTo($('#dwtable').find("tbody"));
			}
			$('<tr id="totaldw"></tr>').appendTo($('#dwtable').find("tbody"));
			$('#xmshye').hide();
			var biaoti= '<span class="lbr-back-to-pro-type-echart" onclick="backto()">工人在场情况</span>'
		    $(biaoti).appendTo('#biaoti')
		    $("#totaldw").empty();
		    sum('dwtable tr','totaldw');
			$('#xmshye').hide();
	        $('#bigdiv').show();
			$('#dwrs').show();

		});
	}
	
	//饼图
	var myChartpie;
	function xmpie(jrgzarray,gzarray){
		$('.gzbt').bind("click", function() {
			$(".gzbt").removeClass("km_active");//移除按钮的样式
			var btnid = this.id;
			var dataarray=new Array();
	    	if(btnid=="jrgz"){
	    		dataarray=jrgzarray;
	    	}else{
	    		dataarray=gzarray;
	    	}
		
				// 基于准备好的dom，初始化echarts图表
				myChartpie = echarts.init(document.getElementById('gongzhongtj'));
				var mcarry = new Array();
				var dataarry = new Array();
				for (var i = 0; i < dataarray.length; i++) {
					mcarry.push(dataarray[i].MINGCHENG+'('+dataarray[i].RENSHU+'人)')
					var a = {
						value : dataarray[i].RENSHU,
						name : dataarray[i].MINGCHENG+'('+dataarray[i].RENSHU+'人)'
					}
					dataarry.push(a)
				}
				option = {
					title : {
						text : '',
						subtext : '',
						x : 'center'
					},
					tooltip : {
						trigger : 'item',
						formatter : "{a} <br/>{b} : {c} ({d}%)"
					},
					legend : {
						//orient : 'vertical',竖直方向
						y : 'bottom',
						data : mcarry
					//左上角工种名称
					},
					toolbox : {
						show : false,
						feature : {
							mark : {
								show : true
							},
							dataView : {
								show : true,
								readOnly : false
							},
							magicType : {
								show : true,
								type : [ 'pie', 'funnel' ],
								option : {
									funnel : {
										x : '25%',
										width : '50%',
										funnelAlign : 'left',
										max : 1548
									}
								}
							},
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
						}
					},
					calculable : true,
					series : [ {
						name : '工种分布',
						type : 'pie',
						radius : '55%',
						center : [ '50%', '60%' ],
						data : dataarry
					} ]
				};

				// 为echarts对象加载数据 
				if(dataarray.length>0){
					myChartpie.setOption(option);
					
				}else{
					$('#gongzhongtj').css('line-height', '400px');
					$('#gongzhongtj').html("暂无数据")
					$('#gongzhongtj').css('text-align', 'center');
				}
//				var resize = function () {
//			        if (myChartpie)
//			        	myChartpie.resize();  
//			    }
//			    window.onresize = resize;
				$("#"+btnid).addClass("km_active");//添加按钮点击后的样式
		})
	}
	
	 //改变窗口大小的时候重绘图表 窗口大小发生改变时候触发
    $(window).resize(function (){
    	if (myChartbar)
            myChartbar.resize();
        
        if (myChartpie)
        	myChartpie.resize();
        
        if (myChartline)
            myChartline.resize();
    });
	//获取第一个工种 
	function xmpielist1(gzarray1){
		$('#gongzhongtj').bind(
				"click",
				function() {
						var gzmxList = gzarray1;
						$('#gzmx').find("tbody").empty();
						for (var i = 0; i < gzmxList.length; i++) {
							var kqv = 0;
							var gzmxtable = '<tr>' + '<td>' + (i + 1) + '</td>'
									+ '<td><span style="font-size:12pt;text-decoration:underline;color:blue;" class="dianwo" onclick="a('+gzmxList[i].BIANMA+',\''+gzmxList[i].XIANGMU_BIANMA+'\',\''+gzmxList[i].MINGCHENG+'\');">' + gzmxList[i].MINGCHENG + '</span></td>'
									+ '<td>' + gzmxList[i].ssrs + '</td>' + '<td>'
									+ gzmxList[i].ljrs + '</td>' + '<td>'
									+ gzmxList[i].zcrs + '</td>' + '</tr>'
							$(gzmxtable).appendTo($('#gzmx').find("tbody"));
						}
						$('<tr id="totalgz"></tr>').appendTo($('#gzmx').find("tbody"));
						$('#xmshye').hide();
						var biaoti= '<span class="lbr-back-to-pro-type-echart" onclick="backto()">现场工人工种分布情况</span>'
					    $(biaoti).appendTo('#biaoti')
					    sum('gztable tr','totalgz');
						$('#bigdiv').show();
						$('#gzmx').show();
				});
	}
	
	
	//风险预警
	$('#zhankai').bind("click", function() {
		var i=document.getElementById('fx');
		//判断当前是否为展开状态
		if(i.style.display=="none"){
			
			//改变下拉按钮
			$('#xiala').hide();
			var li='';
            if(totalfengxian!='0'){
            	if(bzyjcount!='0'){
    				li+='<li class="km_yjlist">有<span class="km_geshu">'+bzyjcount+'个</span>班组出勤率低于'+bzrule+'%</li>';
    			}
    			if(duiwucount!='0'){
    				li+='<li class="km_yjlist">有<span class="km_geshu">'+duiwucount+'个</span>队伍出勤率低于'+dwrule+'%</li>';
    			}
    			if(gongzhcount!='0'){
    				li+='<li class="km_yjlist">有<span class="km_geshu">'+gongzhcount+'个</span>工种出勤率低于'+gongzhrule+'%</li>';
    			}
    			if(lxwccrs!='0'){
    				li+='<li class="km_yjlist">有<span class="km_geshu">'+lxwccrs+'名</span>工人连续 '+lianxutianshu+' 天，每天在场超过'+lianxuxiaoshi+'小时</li>'
    			}
    			if(yjwcc!='0'){
    				li+='<li class="km_yjlist">有<span class="km_geshu">'+yjwcc+'名</span>工人夜间超过24:00未出场</li>'
    			}
    			if(isneedsafeedu=='true'){
    				li+='<li class="km_yjlist">当前项目需要进行安全教育 </li>'
    			}
    			$(li).appendTo('#fx')	
			}
			
			$('#fuwei').show();
			$('#fx').show();
		}else{
			$('#fx').empty();
			$('#fx').hide();
			$('#fuwei').hide();
			$('#xiala').show();
			
		}
		
	});
	
	 //改变窗口大小的时候重绘图表
/*    var resize = function () {
        if (myChartbar)
            myChartbar.resize();
        
        if (myChartpie)
            myChartpie.resize();
        
        if (myChartline)
            myChartline.resize();
    }
    window.onresize = resize;
	*/
	
	
	//点击menu添加颜色
	$('.menu').bind("click", function() {
		$('.menu').find('i').css("color","#585858");
		$('.menu').find('span').css("color","#585858");
		$(this).find('i').css("color","#1997e4");
		$(this).find('span').css("color","#1997e4");
	})
	
	$('.sub').bind("click", function() {
		$('.menu').find('i').css("color","#585858");
		$('.menu').find('span').css("color","#585858");
		$(this).parent().parent().prev().prev().find('i').css("color","#1997e4");
		$(this).parent().parent().prev().prev().find('span').css("color","#1997e4");
	})
	
	function leftdiv(){
		$('.leftxm').bind("click",function(){
			$('.list-group-item').removeClass('active');
			$(this).parent().addClass("active");
			gohome($(this)[0].innerHTML);
			var bianma=$(this)[0].title;
			var url= _basepath +"main/index.do?bianma="+bianma;
			$.get( url,function(data){
				var pd=data.pd;
				$("#zuzhishye").hide();
				
				$("#xmshye").empty();
				var xmdiv='<div class="km_xiangmmc">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; '+pd.xmmc+'<a id ="pinjie" href="#"></a></div>'
										+'<div class="km_outbox" style="background:#f9f9f9;">'
											
										+'<div class="km_leftbox">'
										+'<div class="km_lefttop">'
										+'<ul style="margin: 20px;">'
										+'<li class="km_shis"  title="当前在施工区的实时作业人数">'
										+'<h1>'+pd.ssrs+'</h1>'
										+'<p>现场实时人数</p>'
										+'</li>'
										+'<li class="km_leij">'
										+'<h1>'+pd.ljrs+'</h1>'
										+'<p>日累计进场</p>'
										+'</li>'
										+'<li class="km_zc" title="当前项目登记未退场的人数">'
										+'<h1>'+pd.zcrs +'</h1>'
										+'<p>项目在场人数</p>'
										+'</li>'
										+'<div class="clearfix"></div>'
										+'</ul>'
										+'<div style="width: 95%;margin: 0 auto;">'
										+'<div id="renshutj" style="height:400px;width:100%;"></div>'
										+'</div>'
										+'</div>'
										+'<div class="km_lbottom">'
										+'<div class="km_xtitle">最近一周出勤率</div>'
										+'<div style="width: 95%;margin: 0 auto;">'
										+'<div id="chuqltj" style="height:400px"></div> '
										+'</div>'
										+'</div>'
										+'</div>'
										+'<div class="km_rightbox">'
										+'<div class="km_rtop">'
										+'<div class="km_yuj">'
										+'<img src="'+_basepath+'static/ace/images/shouye/yujing.png" />'
										+'</div>'
										+'<div class="km_yjtext">'
										+'<p>风险预警</p>'
										+'<h2>'+pd.totalfengxian +'条</h2>'
										+'</div>'
										+'<div class="km_xial" id="zhankai">'
										+'<img src="'+_basepath+'static/ace/images/shouye/xiala.png"'
										+'		alt="" id="xiala"/>'
										+'<img src="'+_basepath+'static/ace/images/shouye/fuwei.png"'
										+'	alt="" style="display:none" id="fuwei"/>'
										+'	</div>'
										+'<div class="clearfix"></div>'
										+'<ul class="km_tishi" id="fx" style="display:none">'
										+'</ul>'
										+'</div>'
										+'	<div class="km_rcenter">'
										+'	<p class="km_gongz">现场工人工种分布</p>'
										+'	<div class="km_shijin">'
										+'		<input type="button" value="实时" class="gzbt" id="ssgz"/> '
										+'		<input type="button" value="今日" class="gzbt" id="jrgz"/>'
										+'		</div>'
										+'		<div class="clearfix"></div>'
										+'		<div style="width: 95%;margin: 0 auto;">'
										+'			<div id="gongzhongtj" style="height:400px"></div>'
										+'		</div>'
										+'	</div>'
										+'	<div class="km_dongt">&nbsp;&nbsp;&nbsp;最新动态</div>'
										+'	<ul class="km_list">';
										if(pd.zxdtlist.length>0){
											for(var i=0;i<pd.zxdtlist.length;i++){
												xmdiv+='<li class="km_lhang"><span class="km_zhaop"><img'
												+'					src="'+_basepath+'static/ace/images/shouye/zhaop.png"'
												+'				alt="" /></span>'
												+'			<div class="km_time">'
												+'				<div style="margin-bottom: 5px;font-size:16px;">'
												+					pd.zxdtlist[i].XINGMING +'<span></span>'
												+'				</div>'
												+'				<div style="color: #b0b0b0;font-size:14px;">'+pd.zxdtlist[i].CJSJ +'</div>'
												+'			</div>'
												+'			<div class="km_zhuangt">'+pd.zxdtlist[i].OPERTYPE +'</div>'
												+'			<div class="clearfix"></div></li>'
											}
										}
										else{
											xmdiv+='	<c:if test="${empty zxdtlist}">'
											+'	            <div style="text-align: center;">暂无数据</div>'
											+'	</c:if>'
										}
												
										xmdiv+='	</ul>'
										+'	</div>'
										+'	<div class="clearfix"></div>'
										+'</div>'
										+'<div class="clearfix"></div>'
				$("#xmshye").append(xmdiv)
				$("#xmshye").show();
				xmbar(pd.dWuList);
				xmline(pd.kaoqinList,bianma);
				xmpie(pd.gztjlist,pd.jinrigongZhongList);
				$("#ssgz").click();
				xmbarlist(pd.dWuList);
				$('#bt').text(pd.xmmc)
				xmpielist1(pd.gzList);
			})
		});
	}
	
	//全部项目 点击
	function allpro(){
		$('#allpro').bind('click',function(){
			$('.list-group-item').removeClass('active');
			$(this).addClass("active");
			$('.lbr-pro-team-summary-list').hide();
			$('#bigdiv').hide();
			$("#xmshye").hide();
			$("#zuzhishye").show();
		});
	}
	
	
	//
	$('#XMMC').bind('input propertychange', function() {
	    var XMMC = document.getElementById("XMMC").value;
		var zzjg = document.getElementById("zzjg").value;
		$.ajax({
		type:"post",
		dataType:"json",
		data:{"DEPARTMENT_ID":zzjg,"keywords":XMMC},
		url:_basepath+"login/getXM.json",
		success : function(mv) {
			var listmx = mv.list_xm;
			$("#sidebar_content li").remove();
			$('#sidebar_content').find('li').each(function() {
				var t = $(this).attr('t');
				if (t == 'clearUp') {
					$(this).remove();
				}
			});
			   var li='';
			   if(XMMC=='' || XMMC==null){
				   li='<li class="list-group-item" id="allpro">'
					+'<div style="width:186px;cursor:pointer"'
						+'title="全部项目" class="text-overflow">全部项目</div></li>'
			   }
				for (var i = 0; i < listmx.length; i++) {
					if(listmx[i].isshouquan==true){
						if(listmx[i].outyxq==false){
							 li+='<li class="list-group-item"><div style="width:186px;cursor:pointer"'
							    +'title="'+listmx[i].XIANGMUBM+'" class="text-overflow leftxm">'+listmx[i].XIANGMUMC+'</div></li>';
						}else{
							 li+='<li class="list-group-item"><div style="width:186px;cursor:pointer"'
							    +'title="'+listmx[i].XIANGMUBM+'" class="text-overflow">'+listmx[i].XIANGMUMC+'(过期)</div></li>';
						}
					}else{
						li+='<li class="list-group-item"><div style="width:186px;cursor:pointer"'
						    +'title="'+listmx[i].XIANGMUBM+'" class="text-overflow">'+listmx[i].XIANGMUMC+'(未授权)</div></li>';
					}
					
						

				};
				$(li).appendTo("#sidebar_content");
				leftdiv();
				allpro();
		},
		error : function(XMLHttpRequest,textStatus,errorThrown) {
		alert(errorThrown);
		}
		});
		});
	
	
	
	
	
	
})(jQuery);

$(function(){
	$("input").placeholder(); 
	$("#ssgz").click();
	$('#allpro').click();
	$("#ssgz").addClass("km_active");//添加按钮点击后的样式
	if('PRO'==leixing){
		$("#zuzhishye").hide();
		$("#xmshye").show();
		$("#_index_left-region").hide();
//		$("#_index_left-region").show();
	}
	if('ORG'==leixing){
		$("#zuzhishye").show();
		$("#xmshye").hide();
		$("#_index_left-region").show();
	}
});