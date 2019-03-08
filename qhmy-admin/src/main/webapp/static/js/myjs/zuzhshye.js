//@ sourceURL=zuzhshye.js 
(function($){
	// 使用柱状图就加载bar模块，按需加载
	var myChartBar = echarts.init(document.getElementById('xmtj'));
	//console.log(xmarray)
	var mcarray = new Array();
	var ssarray = new Array();
	var ljarray = new Array();
	var zcarray = new Array();

	var colorList = [
	                 '#ff7f50','#87cefa','#da70d6','#32cd32','#6495ed',
	                 '#ff69b4','#ba55d3','#cd5c5c','#ffa500','#40e0d0'
	               ];
	for (var i = 0; i < xmarray.length; i++) {
		mcarray.push(xmarray[i].xmmc)
		ssarray.push(xmarray[i].ssrs)
		ljarray.push(xmarray[i].ljrs)
		zcarray.push(xmarray[i].zcrs)
	}
	option = {
		title : {
			text : '现场总人数前十项目'
				,
			//subtext : '',
			x:'center'
            
            
		},
		tooltip : {
			trigger : 'axis',
			axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            },
			formatter : function(params) {
				// for text color
				var color = colorList[params[0].dataIndex];
				var res = '<div style="color:white">';
				res += '<strong>' + params[0].name + '</strong>'
				for (var i = 0, l = params.length; i < l; i++) {
					res += '<br/>' + params[i].seriesName + ' : '
							+ params[i].data
				}
				res += '</div>';
				return res;
			}
		},
		legend : {
			data : [ '现场实时人数', '日累计进场', '项目在场人数' ],
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
			data : mcarray,
			axisLabel:{
                rotate: 30//旋转X轴的名称（30度）
			}
		} ],
		yAxis : [ {
			type : 'value',
				axisLabel : {
					formatter: function (value) {
	                     return Math.round(value) + '人';
	                 }
				}
		} ],
		series : [ {
			name : '现场实时人数',
			type : 'bar',
			itemStyle: {
                normal: {
                	 color: '#FFAD33', //'#00b24d',
                     barBorderColor: '#ccc',
                     barBorderWidth: 0,
                     barBorderRadius: 1,
                    label: {
                        show: true,
                        position: 'top'
                    }
                }
            },
			data : ssarray
		}, {
			name : '日累计进场',
			type : 'bar',
			itemStyle: {
                normal: {
                	 color: '#b7cdbe',
                     barBorderColor: 'tomato',
                     barBorderWidth: 0,
                     barBorderRadius: 1,
                    label: {
                        show: true,
                        position: 'top'
                    }
                }
            },
			data : ljarray
		}, {
			name : '项目在场人数',
			type : 'bar',
			// stack: 'own',
			itemStyle: {
                normal: {
                	 color: '#2aacfb',
                     barBorderColor: 'tomato',
                     barBorderWidth: 0,
                     barBorderRadius: 1,
                    label: {
                        show: true,
                        position: 'top'
                    }
                }
            },
			data : zcarray
		} ]
	};

	if(xmarray.length>0){
		myChartBar.setOption(option);
	}else{
		$('#xmtj').css('line-height', '400px');
		$('#xmtj').html("暂无数据")
		$('#xmtj').css('text-align', 'center');
	}
	
	//点击柱状图显示对应的list
        $('#xmtj').bind("click", function() {
		
		$('#zuzhdiv').hide();
		var biaoti=' <span>/工人在场情况</span>';
        $(biaoti).appendTo('#biaotiname')
        $('#xmlistdiv').show();
        $("#totalRow").empty();
        sum2('xmtable tr','totalRow');//调用表格的合计方法
		$('#xmlist').show();

	});
        var myChartPie;
    $('.bingtu').bind("click",function(){
    	$(".bingtu").removeClass("km_active");//移除按钮的样式
    	var id=this.id;
    	var dataarray=new Array();
    	if(id=="jinri_btn"){
    		dataarray=jrgzh;
    	}else{
    		dataarray=shshgzh;
    	}
    	 //饼图    --工种统计
    	// 基于准备好的dom，初始化echarts图表
    	myChartPie = echarts.init(document.getElementById('xchgz'));
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
    			x : ''
    		},
    		tooltip : {
    			trigger : 'item',
    			formatter : "{a} <br/>{b} : {c} ({d}%)"
    		},
    		legend : {
    			 orient: 'horizontal',
    			//orient : 'vertical',饼图下边的备注为竖直方向 ，去掉或不写默认水平
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
    		myChartPie.setOption(option);
    	}else{
    		$('#xchgz').css('line-height', '400px');
    		$('#xchgz').html("暂无数据")
    		$('#xchgz').css('text-align', 'center');
    	}
    	$("#"+id).addClass("km_active");//添加按钮点击后的样式
    })
   
        //组织--折线图--出勤率
    
 // 折线图--出勤率
	// 基于准备好的dom，初始化echarts图表
	var myChartLine = echarts.init(document.getElementById('chuqinlv'));
	var xarray = new Array();
	var yarray = new Array();
	for (var i = 0; i < cqlarray.length; i++) {
        	xarray.push(cqlarray[i].RIQI);
		yarray.push(cqlarray[i].KAOQINLV.toFixed(2));
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
		calculable : false,
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
			itemStyle: {
                normal: {
                    areaStyle: {
                        type: 'default'
                    }
                }
            },
			data : yarray,

		} ]
	};

	//var ecConfig = require('echarts/config');

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
				kaoqinriqi : name
			},
			success : function(mv) {
				var kqList = mv.moutiankqlist;
//				console.log(kqList)
				$('#kqtable').find("tbody").empty();
				for (var i = 0; i < kqList.length; i++) {
					var xiangmubianma=kqList[i].XIANGMUBM;
					var kqtr = '<tr>' + '<td>' + (i + 1) + '</td>'
							+ '<td><span style="font-size:12pt;text-decoration:underline;color:blue;" onclick="b(\''+xiangmubianma+'\',\''+name+'\',\''+kqList[i].XIANGMUMC+'\');">' + kqList[i].XIANGMUMC
						    + '</span></td>'
							+ '<td>' + kqList[i].ljrs + '</td>' + '<td>'
							+ kqList[i].findzcrs + '</td>' + '<td>' + kqList[i].Kqlv.toFixed(2)
							+ '%</td>' + '</tr>'
					$(kqtr).appendTo($('#kqtable').find("tbody"));
				}
				$('#zuzhdiv').hide();
				var biaoti=' <span>/'+name.replace('/', '月')+'日考勤率</span>';
		        $(biaoti).appendTo('#biaotiname')
		        var zongjitr='<tr id="totalkqlv"></tr>';
		        $(zongjitr).appendTo($('#kqtable').find("tbody"))
		        $('#xmlistdiv').show();
		        sum2('kqtable tr','totalkqlv');//调用表格的合计方法
				$('#kqlist').show();
				
				
//				// 由JSON字符串转换为JSON对象
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(errorThrown);
			}
		});

	}
	//myChartLine.on(ecConfig.EVENT.CLICK, queryXY);
	myChartLine.on('click',function(params){
		queryXY(params);
		})
	// 为echarts对象加载数据 
		
		if(cqlarray.length>0){
			myChartLine.setOption(option);
		}else{
			$('#chuqinlv').css('line-height', '400px');
			$('#chuqinlv').html("暂无数据")
			$('#chuqinlv').css('text-align', 'center');
		}
        
	 //改变窗口大小的时候重绘图表
    var resize2 = function () {
        if (myChartBar)
            myChartBar.resize;
        
        if (myChartPie)
            myChartPie.resize;
        
        if (myChartLine)
            myChartLine.resize;
    }
    //改变窗口大小的时候重绘图表 窗口大小发生改变时候触发
    $(window).resize(function (){
    	if (myChartBar)
            myChartBar.resize();
        
        if (myChartPie)
            myChartPie.resize();
        
        if (myChartLine)
            myChartLine.resize();
    });
        
    
})(jQuery);

$(function(){
	//页面加载完毕默认触发饼图实时按钮，因为饼图的数据加载写在两个按钮的点击事件里了
	$("#shsh_btn").click();
	$("#shsh_btn").addClass("km_active");//添加按钮点击后的样式

});