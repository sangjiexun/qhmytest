 $(document).ready(function() {
	 
	 
	 $('#dxtd').show(100);
	 
	 if(jwsjtb=="Y"){
		 $("#jwtb").prop({checked:true});
	 }else if(jwsjtb=="N"){
		 $("#jwtb").prop({checked:false});
	 }
	 $('#fwqdz').val(ipdz);
	 $('#DUANKOU').val(dkh);
	 $('#JIEKOUDIZHI').val(jkdz);
	
	 if(tbfs=="1"){
		 $("input[name='tbr']").get(0).checked=true; 	 
	 }else if(tbfs=="2"){
		 $("input[name='tbr']").get(1).checked=true; 	 
	 }else if(tbfs=="3"){
		 $("input[name='tbr']").get(2).checked=true; 	 
	 }
	 
	 
	 
	$('.jf_tabli').bind("click", function(){
			$(this).addClass('active');
			$('.jf_tabli').not($(this)).removeClass('active');
			idx=$(this).index('.jf_tabli');
			$('.jf_tabrow').eq(idx).stop().show(100);
			$('.jf_tabrow').not($('.jf_tabrow').eq(idx)).hide(100);
		});
	//是否开启教务系统同步
	 $("#jwtb").change(function() {
		 if(document.getElementById("jwtb").checked){
			 $.ajax({
	  			type:"post",
	  			dataType:"json",
	  			data: {SFKQSJTB:"Y"},
	  			url:"shezhi/setjwtbkq.json",
	  		success : function(data) {
	  		},
	  		error : function(XMLHttpRequest, textStatus, errorThrown) {
	  			alert(errorThrown);
	  		}
	  	});
		 }else{
			 $.ajax({
		  			type:"post",
		  			dataType:"json",
		  			data: {SFKQSJTB:"N"},
		  			url:"shezhi/setjwtbkq.json",
		  		success : function(data) {
		  		},
		  		error : function(XMLHttpRequest, textStatus, errorThrown) {
		  			alert(errorThrown);
		  		}
		  	});
		 }
	 });
	 //服务器地址更改
	 $("#fwqdz").blur(function(){
		 var IPDIZHI=$("#fwqdz").val();
		 
		 $.ajax({
	  			type:"post",
	  			dataType:"json",
	  			data: {IPDIZHI:IPDIZHI},
	  			url:"shezhi/setipdz.json",
	  		success : function(data) {
	  		},
	  		error : function(XMLHttpRequest, textStatus, errorThrown) {
	  			alert(errorThrown);
	  		}
	  	});
		 
	 });
	 
	//端口号更改  
	 $("#DUANKOU").blur(function(){
		 var DUANKOU=$("#DUANKOU").val();
		 
		 $.ajax({
	  			type:"post",
	  			dataType:"json",
	  			data: {DUANKOU:DUANKOU},
	  			url:"shezhi/setipdz.json",
	  		success : function(data) {
	  		},
	  		error : function(XMLHttpRequest, textStatus, errorThrown) {
	  			alert(errorThrown);
	  		}
	  	});
		 
	 });
	 
	 $("#JIEKOUDIZHI").blur(function(){
		 var JIEKOUDIZHI=$("#JIEKOUDIZHI").val();
		 $.ajax({
	  			type:"post",
	  			dataType:"json",
	  			data: {JIEKOUDIZHI:JIEKOUDIZHI},
	  			url:"shezhi/setipdz.json",
	  		success : function(data) {
	  		},
	  		error : function(XMLHttpRequest, textStatus, errorThrown) {
	  			alert(errorThrown);
	  		}
	  	});
		 
	 });
	 
	 //$("input[name='radio_name'][checked]").val(); 
	 $(".radioItem").change(function(){
		var TONGBUFS= $("input[name='tbr']:checked").val();
		$.ajax({
  			type:"post",
  			dataType:"json",
  			data: {TONGBUFS:TONGBUFS},
  			url:"shezhi/setipdz.json",
  		success : function(data) {
  		},
  		error : function(XMLHttpRequest, textStatus, errorThrown) {
  			alert(errorThrown);
  		}
  	});
		 });
	
	

});