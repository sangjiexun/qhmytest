//@ sourceURL=teacherhome.js 
(function($,window){
/**
 * 教师首页
 */
var teacherhome={};
//饼图   缴费人数统计
// 基于准备好的dom，初始化echarts图表
teacherhome.pie=function(){
	var myChartPie = echarts.init(document.getElementById('pie'),'customed');
	option = {
		title : {
			text : '今日缴费人数' + totalCounts + '人',
			subtext : '',
			x : 'center'
		},
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		legend : {
			//orient: 'horizontal',
			orient : 'vertical',//饼图下边的备注为竖直方向 ，去掉或不写默认水平
			x : 'left',
			y : 'bottom',
			data : [ '网上', '其他' ]
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
		grid:{
			width: '100%',
			height: '100%',
	    },
		series : [ {
			name : '缴费人数：',
			type : 'pie',
			radius : '55%',
			center : [ '50%', '60%' ],
			data : [ {
				value : totalOnlineCounts,
				name : '网上'
			}, {
				value : totalOtherCounts,
				name : '其他'
			} ]
		} ]
	};
	//$('#pie').css('line-height', '1800px');
	myChartPie.setOption(option);
	$(window).resize(function (){
		myChartPie.resize();	
	});
};


//柱状图 统计各项缴费金额


//使用柱状图就加载bar模块，按需加载
teacherhome.bar=function(){
	var myChartBar = echarts.init(document.getElementById('bar'),'customed');
	var namearray=new Array();
	var moneyarray=new Array();
	var colorList = [
	                 '#ff7f50','#87cefa','#da70d6','#32cd32','#6495ed',
	                 '#ff69b4','#ba55d3','#cd5c5c','#ffa500','#40e0d0'
	               ];
	for(var i=0;i<bararray.length;i++){
		namearray.push(bararray[i].PAYITEM);
		moneyarray.push(bararray[i].TOTALMONEY);
	}
	option = {
		title : {
			text : ('今日缴费总额  '+(todayTotalMoney==''?0:todayTotalMoney)+' 元'),
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
							+ params[i].data+'元'
				}
				res += '</div>';
				return res;
			}
		},
		legend : {
			data : namearray,
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
			data : namearray,
			axisLabel:{
				formatter: function (value) {
                    return value.length > 8 ? value.substring(0, 7) + '..' : value;
                },
				 interval:0,//横轴信息全部显示  
	            rotate: 30//旋转X轴的名称（30度）
			}
		} ],
		yAxis : [ {
			type : 'value',
				axisLabel : {
					formatter: '{value}元'
				}
		} ],
		series : [ {
			name : '缴费金额',
			type : 'bar',
			barWidth : 30,//柱状图宽度
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
			data : moneyarray
		} ]
	};
	myChartBar.setOption(option);
	$(window).resize(function (){
		myChartBar.resize();	
	});
};


/**
 * 图表的自适应
 */



//折线图--出勤率
// 基于准备好的dom，初始化echarts图表
teacherhome.line=function(xarray,onlinearray,otherarray,id){
	var myChartline = echarts.init(document.getElementById(id),'customed');
		
		option = {
			title : {
				text : '',
				subtext : ''
			},
			tooltip : {
				trigger : 'axis',
				formatter : function(params) {
					// for text color
					
					var res = '<div style="color:white">';
					res += '<strong>' + params[0].name + '</strong>'
					for (var i = 0, l = params.length; i < l; i++) {
						res += '<br/>' + params[i].seriesName + ' : '
								+ params[i].data+'元'
					}
					res += '</div>';
					return res;
				}
			},
			legend : {
				data : ['网上','其他'],
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
					formatter : '{value}元'
				}
			} ],
			series : [ {
				name : '网上',
				type : 'line',
				data : onlinearray,

			},
			{
				name : '其他',
				type : 'line',
				data : otherarray,

			}
			
			]
		};


		function queryXY(param) {}
		
		// 为echarts对象加载数据 
		
//		$('#'+id).css('line-height', '800px');
		myChartline.setOption(option);
		$(window).resize(function (){
			myChartline.resize();	
		});
		
	
};
//遍历折线图的数据 转换为json对象
for (var i = 0; i < linearray.length; i++) {
	var a = linearray[i].linedata;
	var obj = $.parseJSON(a);
	var xarray = new Array();
	var onlinearray = new Array();
	var otherarray = new Array();
	for (var j = 0; j < obj.length; j++) {
		xarray.push(obj[j].TODAY);
		onlinearray.push(obj[j].ONLINEPAY);
		otherarray.push(obj[j].OTHERPAY);
	}
	// 加载折线图
	teacherhome.line(xarray, onlinearray, otherarray, obj[0].PKID);
}




$('.lookdetail').bind('click',function(){
	var PKID=$(this).prev().val();
	$(".jf_content").load(_basepath+ 'home/goDetailJson.json?PKID='+PKID);
});

$(function(){
	teacherhome.pie();
	teacherhome.bar();
})
teacherhome.currentPage=1;
$("#dddd").scroll(function(){
    var $this =$(this),
    viewH =$(this).height(),//可见高度
    contentH =$(this).get(0).scrollHeight,//内容高度
    scrollTop =$(this).scrollTop();//滚动高度
   //if(contentH - viewH - scrollTop <= 100) { //到达底部100px时,加载新内容
   if(scrollTop/(contentH -viewH)>=0.95){ //到达底部100px时,加载新内容
   // 这里加载数据..
	   teacherhome.currentPage++;
	   //分页获取数据
	   $.get(_basepath+"main/getLineData.json?currentPage="+teacherhome.currentPage,function(mv){
		   var xmList=mv.xmList;
		   var str='';
		   for(var i=0;i<xmList.length;i++){
			   str= line_str(xmList[i]);
			   $('.jf_content').append(str);
			 //遍历折线图的数据 转换为json对象
			  
			   	var obj = xmList[i].linedata
			   	var xarray = new Array();
			   	var onlinearray = new Array();
			   	var otherarray = new Array();
			   	for (var j = 0; j < obj.length; j++) {
			   		xarray.push(obj[j].TODAY);
			   		onlinearray.push(obj[j].ONLINEPAY);
			   		otherarray.push(obj[j].OTHERPAY);
			   	}
			   	// 加载折线图
			   	teacherhome.line(xarray, onlinearray, otherarray, xmList[i].PKID);
			  
		   }
		   
		  
		   $('.lookdetail').bind('click',function(){
				var PKID=$(this).prev().val();
				$(".jf_content").load(_basepath+ 'home/goDetailJson.json?PKID='+PKID);
			});
	   });
   }
});

function line_str(i){
	
	var a = '<div class="panel panel-info">'+
	'<div class="panel-heading">'+
'<div class="panel-title jf_pantit">'+
	'<span class="fa fa-flag"></span>'+i.ITEMNAME+
	'<input type="hidden" value="'+i.PKID+'">'+
	'<a href="javascript:void(0);" class="pull-right lookdetail">查看详情>></a>'+
'</div>'+
'</div>'+
'<div class="panel-body jf_panel" style="min-width:1360px;width:1300px;margin:0 auto;">'+
'<div class="pull-left" style="margin:50px 0 0 20px;">'+
	'<ul class="pull-left" style="margin-right:40px;">'+
		'<li class="jf_li">'+
			'<div class="jf_shuzis"><span>'+i.RECEPEOPLE+'</span>人</div>'+
			'<div>应收总人数</div>'+
		'</li>'+
		'<li class="jf_li">'+
			'<div class="jf_shuzis"><span>'+i.RECEMONEY.toFixed(2)+'</span>元</div>'+
			'<div>应收总金额</div>'+
		'</li>'+
		'<li class="jf_li">'+
			'<div class="jf_shuzis"><span>'+i.UPEOMON.toFixed(2)+'</span>元</div>'+
			'<div>网上</div>'+
		'</li>'+
	'</ul>'+
	'<ul class="pull-left">'+
		'<li class="jf_li">'+
		'	<div class="jf_shuzis"><span>'+i.ACTUALPEOPLE+'</span>人</div>'+
		'	<div>实收总人数</div>'+
		'</li>'+
	'	<li class="jf_li">'+
	'		<div class="jf_shuzis"><span>'+i.ACTUALMONEY.toFixed(2)+'</span>元</div>'+
	'		<div>实收总金额</div>'+
	'	</li>'+
	'	<li class="jf_li">'+
	'		<div class="jf_shuzis"><span>'+i.OPEOMON.toFixed(2)+'</span>元</div>'+
	'		<div>其他</div>'+
	'	</li>'+
	'</ul>'+
'</div>'+
	'<div class="linelineline pull-right" style="height:334px;width:1000px;"id="'+i.PKID+'">'+
	'</div>'+
'</div>'+
'</div>';
	return a;
};
	

})(jQuery,window);







