<div id="sidebar" class="sidebar                  responsive">
<script type="text/javascript">
	try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
</script>
<script type="text/javascript">
jQuery(document).ready(function($){	
ace.addClass(sidebar , 'menu-min');
ace.settings.set('sidebar', 'collapsed');
var ui =document.getElementById("sidebar_content");
ui.style.display="none";
var u = document.getElementById("sidebar-collapse");
var winHeight=0;
if (window.innerHeight)
winHeight = window.innerHeight;
else if ((document.body) && (document.body.clientHeight))
winHeight = document.body.clientHeight;
//通过深入Document内部对body进行检测，获取浏览器窗口高度
if (document.documentElement && document.documentElement.clientHeight)
winHeight = document.documentElement.clientHeight; 
u.style.top=winHeight/2.5+"px"; 
var zzjg = document.getElementById("zzjg").value;
// var zzjg = '0';
var XMMC = document.getElementById("XMMC").value;
$.ajax({
type:"post",
dataType:"json",
data:{"DEPARTMENT_ID":zzjg,"keywords":XMMC},
url:"<%=basePath%>login/getXM.do",
  success:function(mv){
	  var listmx = mv.list_xm;
	$('#sidebar_content').find('li').each(function(){
         var t = $(this).attr('t');
         if(t == 'clearUp'){
             $(this).remove();
         }
    });
	  for(var i=0;i<listmx.length;i++){
	 	 	$("<li t='clearUp' ><a href='main/index?ZZJG='"+listmx[i].ID+"><span class='menu-text' style='margin-left: 8px'>"+listmx[i].XIANGMUMC+"</span></li>").appendTo("#sidebar_content");
	  }; 
  // 由JSON字符串转换为JSON对象
  },
error: function (XMLHttpRequest, textStatus, errorThrown) { 
           alert(errorThrown); 
       }
});
}); 
</script>
				<ul class="nav nav-list sidebar_content" id="sidebar_content">
				<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large" style="margin-left: 8px;margin-top: 5px;margin-bottom: 5px;">
					<div class="input-group" id="dongdiv">
					<input
					type="text" class="form-control" id="XMMC" onchange="toserch();" placeholder="搜项目名称"
					aria-describedby="basic-addon1">
					</div>
				</div>
				</ul><!-- /.nav-list -->
				<!-- #section:basics/sidebar.layout.minimize -->
				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse" style="" >
					<i class="ace-icon fa fa-angle-double-right" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>

				<!-- /section:basics/sidebar.layout.minimize -->
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
					$('#XMMC').bind('input propertychange', function() {  
						var zzjg = document.getElementById("zzjg").value;
// 						alert("111"+zzjg);
						var XMMC = document.getElementById("XMMC").value;
						$.ajax({
						type:"post",
						dataType:"json",
						data:{"DEPARTMENT_ID":zzjg,"keywords":XMMC},
						url:"<%=basePath%>login/getXM.do",
						  success:function(mv){
							  var listmx = mv.list_xm;
// 							  $("sidebar_content li").remove();
							   $('#sidebar_content').find('li').each(function(){
						      		var t = $(this).attr('t');
						      		if(t == 'clearUp'){
						         	 $(this).remove();
						      		}
    							});
							  for(var i=0;i<listmx.length;i++){
							 	 	$("<li t='clearUp' ><a href='main/index?ZZJG='"+listmx[i].ID+"'><span class='menu-text' style='margin-left: 8px'>"+listmx[i].XIANGMUMC+"</span></li>").appendTo("#sidebar_content");
	  						}; 
						  // 由JSON字符串转换为JSON对象
						  },
						error: function (XMLHttpRequest, textStatus, errorThrown) { 
						           alert(errorThrown); 
						       }
						});
						});
					/* function toserch(){
					} */
				</script>
			</div>