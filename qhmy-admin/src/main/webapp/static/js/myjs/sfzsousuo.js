
//@ sourceURL=sfzsousuo.js
var interval=null;
var  is_click_sfzkzqsousuobtn=true;
$(document).keydown(function (e) { //回车事件
    if (event.keyCode == 13 && is_click_sfzkzqsousuobtn==true) {
    	is_click_sfzkzqsousuobtn=false;
       $('.sfzkzqsousuobtn').triggerHandler('click');
       setTimeout(function(){
    	   is_click_sfzkzqsousuobtn=true;
       }, 500);
    }
 });
$(function(){
	
	
	
	
	//var interval=null;
/*	$('.sfzkzqsousuo').focus(function(){  //身份证读卡器
		//alert("333");
		//myInterval();
		interval=setInterval("myInterval()",1000);//1000为1秒钟
		//$(".sfzkzqsousuo").val("130525200201024528");
		
	});
	*/
	/*$('.sfzkzqsousuo').blur(function(){  
		clearInterval(interval);
	});*/
	
	

	
	
	
	
	
	
});


//开始循环读取身份证卡
/*function myInterval()
{
	//alert("223");
	//SynCardOcx1.SetloopTime(1000);
	SynCardOcx1.SetReadType(0);
	
	
	var str;
  	str = SynCardOcx1.FindReader();
  	//alert(str+"223");
  	if (str > 0){
  		
  		//alert("11");
  		//var nRet;
  	  	//SynCardOcx1.SetReadType(0);
  	  var nRet = SynCardOcx1.ReadCardMsg();
  	  	//alert("nRet"+nRet);
  	  	if(nRet==0){
  	  		
  	  		//alert(SynCardOcx1.CardNo+"222");
	  	  	$(".sfzkzqsousuo").val(SynCardOcx1.CardNo);
			//clearInterval(interval);
			//var por=SynCardOcx1.Syn_ClosePort(port);
			var idcard=SynCardOcx1.CardNo;
			tipsshow(idcard+"读取成功!");
	  	 // $('.sfzkzqsousuobtn').triggerHandler('click');
  	  		
  	  		
  	  		
  	  		document.all['S1'].value=document.all['S1'].value+"读卡成功\r\n";	
  	  		document.all['S1'].value=document.all['S1'].value+"姓名:"+SynCardOcx1.NameA +"\r\n";
  	  		document.all['S1'].value=document.all['S1'].value+"性别:"+SynCardOcx1.Sex +"\r\n";
  	  		document.all['S1'].value=document.all['S1'].value+"民族:"+SynCardOcx1.Nation +"\r\n";
  	  		document.all['S1'].value=document.all['S1'].value+"出生日期:"+SynCardOcx1.Born +"\r\n";
  	  		document.all['S1'].value=document.all['S1'].value+"地址:"+SynCardOcx1.Address +"\r\n";
  	  		document.all['S1'].value=document.all['S1'].value+"身份证号:"+SynCardOcx1.CardNo +"\r\n";
  	  		document.all['S1'].value=document.all['S1'].value+"有效期开始:"+SynCardOcx1.UserLifeB +"\r\n";
  	  		document.all['S1'].value=document.all['S1'].value+"有效期结束:"+SynCardOcx1.UserLifeE +"\r\n";
  	  		document.all['S1'].value=document.all['S1'].value+"发证机关:"+SynCardOcx1.Police +"\r\n";
  	  		document.all['S1'].value=document.all['S1'].value+"照片文件名:"+SynCardOcx1.PhotoName +"\r\n";
  	  	}else{
  	  	//	clearInterval(interval);
  	  		//alert("cdcd"+SynCardOcx1.CardNo);
  	  	}
  		
  		
  	}else{
  		//clearInterval(interval);
  		tipsshow("没有找到读卡器");
  	}
	
	
	
	
	
	
	var port=1001;
	var iIfOpen=0;
    var status=SynCardOcx1.Syn_OpenPort(port);
   if(status==0){
			var x=SynCardOcx1.Syn_StartFindIDCard(port,iIfOpen);
			if(x==0){
				var y=SynCardOcx1.Syn_SelectIDCard(port,iIfOpen);
				if(y==0){
					var message=SynCardOcx1.Syn_ReadBaseMsg(port,iIfOpen);
					if(message==0){
						$(".sfzkzqsousuo").val(SynCardOcx1.CardNo);
						clearInterval(interval);
						var por=SynCardOcx1.Syn_ClosePort(port);
						var idcard=SynCardOcx1.CardNo;
						tipsshow(idcard+"读取成功!");
					}else{
						SynCardOcx1.Syn_ClosePort(port);
					}
					//alert(message);
					var result=eval('(' + message + ')');
					var str=result.Data;
					var name=str.Name;
					// var jg=str.VisaOffice;
					//alert(name);
					//$("#jiguan").val(jg);
					$("#name").val(name);
					var sex=str.Sex;
					//alert(sex);
					$("#sex").val(sex); 
					var birthday=str.Birthday;
					var birthday1=birthday;
					var birthday2=birthday;
					//alert(birthday);
					var nian=birthday.substring(0,4);
					var yue=birthday1.substring(4,6);
					var ri=birthday2.substring(6,8);
					bir=nian+"-"+yue+"-"+ri;
					$("#chushengriqi").val(bir);
					var nation=str.Nation;
					//alert(nation);
					$("#nation").val(nation); 
					var address=str.Address;
					//alert(address);
					$("#address").val(address); 
					var idcard=str.IdCardNum;
					$("#shenfenzhenghaoma").val(idcard);
					
					$("#jiguan").val(getProvince_card(idcard));
					//alert(idcard);
					var img=str.ImgBase64;
					
					var imgstr="data:image/jpeg;base64,"+img;
					$("#img").attr("src",imgstr);
					//alert(img);
					$("#BASE64IMG").val(img);
					if(zhika.dialogInstance!=null){
				    	   zhika.dialogInstance = null;
				    }
					
					//读卡成功提示
					
					//end 读卡成功提示
					
				}else{
					SynCardOcx1.Syn_ClosePort(port);
				}
			}else{
				SynCardOcx1.Syn_ClosePort(port);
				
			}
		}
}*/
