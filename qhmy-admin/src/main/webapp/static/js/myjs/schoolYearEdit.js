//@ sourceURL=schoolYearEdit.js
var schoolYearEdit={};
$(function(){
	//日期插件参数设置
	laydate.render({
		elem:'#SCHOOLYEAR_NAME',
		isclear: true, //是否显示清空
		istoday: true, //是否显示今天
		type: 'year',
		format: 'yyyy',
		festival: true, //显示节日
		start: 0
	});
});
