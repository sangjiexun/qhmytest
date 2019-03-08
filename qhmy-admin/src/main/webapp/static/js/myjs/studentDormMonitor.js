//@ sourceURL=studentDormMonitor.js 
/**
 * 学生信息页面对象
 */
(function($, window) {
	//查询
	setInterval(function(){
		dormsearch();
	}, 300000);
	$('#btn_search').click(function(){
		dormsearch();
	});
	
	function dormsearch(){
		$("#xydiv").html("");
		/*var NIANJI = $("#grade").find("option:selected").attr("bianma");
		if(NIANJI == '' || NIANJI==null){
			layer.msg("年级不能为空!");
			return false;
		}
		var orgtreeDep = $('#orgtree').val();
		if(orgtreeDep == '' || orgtreeDep==null){
			layer.msg("院校专业不能为空!");
			return false;
		}*/
		
		var nj=$("#grade").find("option:selected").attr("bianma");
		if(nj=="" || typeof(nj) == "undefined"){
			nj="null";
		}
		var saveUrl = _basepath+"studentDorm/getlistxy.json";
		$.ajax({
			 type: 'post',
			    url: saveUrl,
			    data: {
			    	'DEPARTMENT_PKID':$('#orgtree').val(),
			    	'NIANJI':nj
			    },
			    dataTpye:"json",
			    success: function(data) {
			    	if(data.result==true){
			    	var xmList=data.depList;
			  		var str='';
			  		for(var i=0;i<xmList.length;i++){
			  			var shu=1;
			  			var shunv=1;
			  			var obj = xmList[i].linedata
			  			if(obj.length>=1){
			  				
			  			var divstr='<div class="panel panel-info">'
			  				   +'<div class="panel-heading">'
			  				   +'<div class="pull-left panel-title">'+xmList[i].NAME+'</div>'
			  				   +'<div class="clearfix"></div>'
			  				   +'</div>'
			  				   +'<div class="panel-body">'
			  				   +'<div class="sg_man">'
			  				   +'<span>男</span>'
			  				   +'<div class="container-fluid">'
			  				   +'<div class="row">'
			  				 if(obj.length>=1){
									for(var k=0;k<obj.length;k++){
										if(obj[k].SD_SEX=='1'){
											divstr+='<div class="col-xs-2"  style="height:200px;" width="300" id="pienan'+i+k+'"></div>';
											shu++;
										}
									}
								}
			  					
			  					if(shu == 1){
									divstr+='<div class="col-xs-2"  style="height:200px;" id="nanwsj" width="300">无数据</div>';
								}/*else{
									divstr+='<div class="col-xs-2"  style="height:200px;" width="300">无数据</div>'
								}*/
			 				 	divstr+='</div></div></div>'
			 				 		+'<div class="sg_woman">'
				  				   +'<span>女</span>'
				  				   +'<div class="container-fluid">'
				  				   +'<div class="row">'
				  				 if(obj.length>=1){
										for(var o=0;o<obj.length;o++){
											if(obj[o].SD_SEX=='0'){
												divstr+='<div class="col-xs-2" style="height:200px;" id="pienv'+i+o+'"></div>';
												shunv++;
											}
										}
									}
			 				 		if(shunv == 1){
										divstr+='<div class="col-xs-2" id="nvwsj"  style="height:200px;" >无数据</div>';
									}/*else{
										divstr+='<div class="col-xs-2" style="height:200px;"> 无数据 </div>'
									}*/
				  				divstr+='</div></div></div></div></div>'
			 				 	$("#xydiv").append(divstr);
				  				 
				  				 
				  				if(obj.length>=1){
				  					for (var j = 0; j < obj.length; j++) {
					  					if(obj[j].SD_SEX=='1'){
					  						pie("pienan"+i+j,obj[j])
										}
					  				}
					  				
					  				for (var p = 0; p < obj.length; p++) {
					  					if(obj[p].SD_SEX=='0'){
					  						pie("pienv"+i+p,obj[p])
										}
					  				}
				  				}
				  				
			  		}
			  		   }
			    		
			    		
					}else{
						layer.msg("操作失败!");
			    	}
			    }
		});
		
	};
	
	
	
	function pie(i,obj){
		var a=obj;
		if(document.getElementById(i) == null){
			return false;
		}
		var myChartPie = echarts.init(document.getElementById(i),'customed');
		option = {
			title : {
				text : a.SSLX,
				subtext : '',
				x : 'center'
			},
			tooltip : {
				trigger : 'none',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				//orient: 'horizontal',
				orient : 'horizontal',//饼图下边的备注为竖直方向 ，去掉或不写默认水平
				x : 'left',
				y : 'bottom',
				data : [ '占用', '总计' ]
			},
			color:['red', 'green'], 
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
				width: '10%',
				height: '10%',
		    },
		    graphic:{
		    	type:'text',
		    	left:'center',
		    	top:160,
		    	z:2,
		    	zlevel:100,
		    	style:{
		    		text:a.ZY+'/'+a.ZS,
		    		x:100,
		    		y:200,
		    		textAlign:'center',
		    		fill:'fff',
		    		width:30,
		    		height:30
		    	}
		    	
		    },
			series : [ {
				name : '人数：',
				type : 'pie',
				radius : '50%',
				center : [ '50%', '50%' ],
				 itemStyle : {
			          normal : {
			            label : {
			              show : false
			            },
			            labelLine : {
			              show : false
			            }
			          },

			        },
				data : [ {
					value : a.ZY,
					name : '占用'
				}, {
					value : a.ZS - a.ZY ,
					name : '总计'
				} ]
			} ]
		};
		//$('#'+i).css('line-height', '1800px');
		myChartPie.setOption(option);
		$(window).resize(function (){
			myChartPie.resize();	
		});
	};

	
	
		
	dormsearch();
	
	
})(jQuery, window);
