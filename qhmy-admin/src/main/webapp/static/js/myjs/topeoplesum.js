(function($, window) {
	function getTab(){
		var htmlHead = '<tr>'+
	
		'<th>院校专业</th>'+
		'<th style="white-space:nowrap;">性别</th>'+
		'<th>总人数</th>'+
		'<th>确认个人信息</th>'+
		'<th>宿舍类型</th>'+
		'<th>已缴费人数</th>'+
		'<th>床位总数</th>'+
		'<th>剩余床位数</th>'+	
	'</tr>';
		var url = _basepath + "reportStat/todepart.json";
		$.ajax({
			  type: 'POST',
			  url: url,
			  async: false,
			  dataType : "json",
			  success : function(result) {
				  var htmlArray = new Array();
                  var sushelist=result.sushe;//宿舍\专业
                  var sexlist=result.sexlist;//性别
                  
                  var maxpeople=0;//人数总数
                  var info=0;//确认信息人数
                  var ispay=0;//缴费人数
                  var maxbed=0;//总床数
                  var lastbed=0;//剩余床数
            	  for(var j=0;j<sexlist.length;j++){
				  for(var i=0;i<sushelist.length;i++){
					  
				
						  var myobject=result["isok"+i+"_"+j];
						  htmlArray.push(
								    "<tr>"+
									    
						"<td>"+sushelist[i].YXNAME+"</td>")
						
							htmlArray.push(
									"<td>"+sexlist[j]+"</td>"+
									    "<td>"+myobject.MAXPEOPLE+"</td>"+
									    "<td>"+myobject.INFO+"</td>"+
									    "<td>"+sushelist[i].DT_NAME+"</td>"+
									    "<td>"+myobject.ISPAY+"</td>"+
									    "<td>"+myobject.MAXBED+"</td>"+
									    "<td>"+myobject.LASTBED+"</td>"+
									    "</tr>"
							)
							 maxpeople+=myobject.MAXPEOPLE;
						     info+=myobject.INFO;
						     ispay+=myobject.ISPAY;
						     maxbed+=myobject.MAXBED;
						     lastbed+=myobject.LASTBED;
					  }
					  
				  }
			
				  
				  
				  htmlArray.push(
						    "<tr>"+
							    "<td></td>"+
				"<td>合计</td>")
				
					htmlArray.push(
							    "<td>"+maxpeople+"</td>"+
							    "<td>"+info+"</td>"+
								"<td></td>"+
							    "<td>"+ispay+"</td>"+
							    "<td>"+maxbed+"</td>"+
							    "<td>"+lastbed+"</td>"+
							    "</tr>"
					)
				  
				  
				  		  $("#maxPeoSum").val(maxpeople);
					  $("#infoSum").val(info);
					  $("#paySum").val(ispay);
					  $("#bedSum").val(maxbed);
					  $("#lastBedSum").val(lastbed);
					  
				  
				  
				  
				  
				  
				  $("#toFeeSumlistTable").html(htmlHead + htmlArray.join(''));
			  },error : function(){
				  alert("网络错误");
			  }
		});
		
		
		table_firsttd = "";
	    table_currenttd = "";
	    table_SpanNum = 0;
	    table_Obj = $("#toFeeSumlistTable" + " tr td:nth-child(" + 1 + ")");
	    table_Objs = $("#toFeeSumlistTable" + " tr td:nth-child(" + 2 + ")");
	    table_Obj.each(function (i) {
	        if (i == 0) {
	            table_firsttd = $(this);
	            table_SpanNum = 1;
	        } else {
	            table_currenttd = $(this);
	            if (table_firsttd.text() == table_currenttd.text()) { //这边注意不是val（）属性，而是text（）属性
	                //td内容为空的不合并
	                if(table_firsttd.text() !=""){
	                    table_SpanNum++;
	                    table_currenttd.hide(); //remove();
	                    table_firsttd.attr("rowSpan", table_SpanNum);
	                }
	            } else {
	                table_firsttd = $(this);
	                table_SpanNum = 1;
	            }
	        }
	    });
	    table_Objs.each(function (i) {
	        if (i == 0) {
	            table_firsttd = $(this);
	            table_SpanNum = 1;
	        } else {
	            table_currenttd = $(this);
	            if (table_firsttd.text() == table_currenttd.text()) { //这边注意不是val（）属性，而是text（）属性
	                //td内容为空的不合并
	                if(table_firsttd.text() !=""){
	                    table_SpanNum++;
	                    table_currenttd.hide(); //remove();
	                    table_firsttd.attr("rowSpan", table_SpanNum);
	                }
	            } else {
	                table_firsttd = $(this);
	                table_SpanNum = 1;
	            }
	        }
	    });
	};
	
	
	
	
	   $("#checkQuery").click(function(){
		   $("#toFeeSumlistTable").html("");
		   var htmlHead = '<tr>'+
		
			'<th>院校专业</th>'+
			'<th style="white-space:nowrap;">性别</th>'+
			'<th>总人数</th>'+
			'<th>确认个人信息</th>'+
			'<th>宿舍类型</th>'+
			'<th>已缴费人数</th>'+
			'<th>床位总数</th>'+
			'<th>剩余床位数</th>'+	
		'</tr>';
			var url = _basepath + "reportStat/todepart.json";
			$.ajax({
				  type: 'POST',
				  url: url,
				  async: false,
				  dataType : "json",
				  data:$("form").serialize(),
				  success : function(result) {
					 
					  var htmlArray = new Array();
	                  var sushelist=result.sushe;//宿舍\专业
	                  var sexlist=result.sexlist;//性别
	                  
	                  var maxpeople=0;//人数总数
	                  var info=0;//确认信息人数
	                  var ispay=0;//缴费人数
	                  var maxbed=0;//总床数
	                  var lastbed=0;//剩余床数
	                  for(var j=0;j<sexlist.length;j++){
					  for(var i=0;i<sushelist.length;i++){
						  
						
							  var myobject=result["isok"+i+"_"+j];
							  htmlArray.push(
									    "<tr>"+
										   
							"<td>"+sushelist[i].YXNAME+"</td>")
							
								htmlArray.push(
										"<td>"+sexlist[j]+"</td>"+
										    "<td>"+myobject.MAXPEOPLE+"</td>"+
										    "<td>"+myobject.INFO+"</td>"+
										    "<td>"+sushelist[i].DT_NAME+"</td>"+
										    "<td>"+myobject.ISPAY+"</td>"+
										    "<td>"+myobject.MAXBED+"</td>"+
										    "<td>"+myobject.LASTBED+"</td>"+
										    "</tr>"
								)
								 maxpeople+=myobject.MAXPEOPLE;
							     info+=myobject.INFO;
							     ispay+=myobject.ISPAY;
							     maxbed+=myobject.MAXBED;
							     lastbed+=myobject.LASTBED;
						  }
						  
					  }
				
					  
					  
					  htmlArray.push(
							    "<tr>"+
								    "<td></td>"+
					"<td>合计</td>")
					
						htmlArray.push(
								
								    "<td>"+maxpeople+"</td>"+
								    "<td>"+info+"</td>"+
								    "<td></td>"+
								    "<td>"+ispay+"</td>"+
								    "<td>"+maxbed+"</td>"+
								    "<td>"+lastbed+"</td>"+
								    "</tr>"
						)
					  
					  
					  
					  $("#maxPeoSum").val(maxpeople);
					  $("#infoSum").val(info);
					  $("#paySum").val(ispay);
					  $("#bedSum").val(maxbed);
					  $("#lastBedSum").val(lastbed);
					  
					  
				
					  $("#toFeeSumlistTable").html(htmlHead + htmlArray.join(''));
					  
					  
				  },error : function(){
					  alert("网络错误");
				  }
			});
			
			
			table_firsttd = "";
		    table_currenttd = "";
		    table_SpanNum = 0;
		    table_Obj = $("#toFeeSumlistTable" + " tr td:nth-child(" + 1 + ")");
		    table_Objs = $("#toFeeSumlistTable" + " tr td:nth-child(" + 2 + ")");
		    table_Obj.each(function (i) {
		        if (i == 0) {
		            table_firsttd = $(this);
		            table_SpanNum = 1;
		        } else {
		            table_currenttd = $(this);
		            if (table_firsttd.text() == table_currenttd.text()) { //这边注意不是val（）属性，而是text（）属性
		                //td内容为空的不合并
		                if(table_firsttd.text() !=""){
		                    table_SpanNum++;
		                    table_currenttd.hide(); //remove();
		                    table_firsttd.attr("rowSpan", table_SpanNum);
		                }
		            } else {
		                table_firsttd = $(this);
		                table_SpanNum = 1;
		            }
		        }
		    });
		    table_Objs.each(function (i) {
		        if (i == 0) {
		            table_firsttd = $(this);
		            table_SpanNum = 1;
		        } else {
		            table_currenttd = $(this);
		            if (table_firsttd.text() == table_currenttd.text()) { //这边注意不是val（）属性，而是text（）属性
		                //td内容为空的不合并
		                if(table_firsttd.text() !=""){
		                    table_SpanNum++;
		                    table_currenttd.hide(); //remove();
		                    table_firsttd.attr("rowSpan", table_SpanNum);
		                }
		            } else {
		                table_firsttd = $(this);
		                table_SpanNum = 1;
		            }
		        }
		    });
		   
		   
	   })
	
	
	$("#checkOut").click(function(){
	   var data=$("form").serialize();
		window.location.href=encodeURI(_basepath+'reportStat/toPeopleExcel.json?'+data);
		
	})
	
	
	   
	   
	
getTab();
	
   
	})(jQuery, window);
