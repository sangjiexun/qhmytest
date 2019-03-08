
/**
 * 扩展Date对象方法，格式化日期
 * @param fmt
 * @returns
 */
Date.prototype.format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};


function keman_tooles(){
	this.treeview = {};
	this.treeview.dosome = function(num,treeName){
		if(num == 1)
		{
			$('#'+treeName).treeview('checkAll', { silent: true });//全选
		}else if(num == 2){
			$('#'+treeName).treeview('uncheckAll', { silent: true });//取消全选
		}else if(num == 3){
			$('#'+treeName).treeview('collapseAll', { silent: true });//折叠
		}else if(num == 4){
			$('#'+treeName).treeview('expandAll', { silent: true });//展开所有二级节点
		}
	};

	this.Tooles = {};
	
	/**
	 * 从数组中，根据fieldName查询字段的值，并按symbol字符分隔拼写成字符串
	 */
	this.Tooles.joinStrSplitComma = function(array,fieldName,symbol){
		var rst = "";
		if(array == null){
			return rst;
		}
		
		$.each(array, function (i, item) {
			rst = rst + item[fieldName] + symbol;
        });
		
		if(rst!=null && rst!=""){
			rst = rst.substring(0, rst.length-1);
		}
		return rst;
	};
}

var kemanTooles = new keman_tooles();

window.kemanTooles = kemanTooles;

