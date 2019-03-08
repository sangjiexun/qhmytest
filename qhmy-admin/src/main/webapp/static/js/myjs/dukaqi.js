/**
 * 读卡器API
 * 写入数据1234567890abcdef
 */
var dukaqi = {};

//连接设备
dukaqi.connect = function (){
	
	var version = MWRFATL.openReader(0,9600);//USB端口是0和9600  返回设备版本号  长度18字节
	//MWRFATL.ReaderBeep(30);//控制设备蜂鸣器  ReaderBeep  30毫秒蜂鸣时间
	return version;
};

//寻卡  打开卡片
dukaqi.scard = function(){
	var cardNumber = MWRFATL.openCard(0);//0一次只能操作一张卡片   返回四个字节的序列号    如：5FC5B768
	return cardNumber;
};
		

//加载密码
dukaqi.readerLoadKey = function ()
{
	var key="ffffffffffff";
	//读写器装载密码  参数1:密码类型  0:keya  4:keyb  
	//参数2 需装载密码的扇区号   0～15  共0到15个扇区
	//
	var iRet=MWRFATL.Loadkey(0,1,key);
	if(iRet)
	{
//		alert(iRet);
//		msg.value =msg.value + "读写器装载密码失败！" +"\n";
	}
	else
	{
//		msg.value =msg.value + "读写器装载密码成功！" +"\n";
	}
	return iRet;
};
		
//验证密码
//	函数(1)：SHORT mifare_authentication(SHORT mode, SHORT sector)
//	功能：验证卡片密码（m1 卡及 S70 卡前 16 个扇区用此函数,S70 卡 16 扇区以后
//	用函数 2 来验证）
//	参数：
//	mode: 验证密码类型：
//	0 — 用 KEY A 验证
//	4 — 用 KEY B 验证
//	sector: 将要验证的卡片扇区号(0~15)
//	返回值：=0 成功；
//	函数(2)：SHORT mifare_authentication_2(SHORT mode, SHORT keyAdr,
//	SHORT block)
//	功能： 用读写器中装载的 0～15 扇区的密码来验证指定的扇区
//	（S70 卡前 16 个扇区用函数 1 来验证，16 扇区以后用此函数验证）
//	参数：
//	mode: 验证密码类型：
//	0 — 用 KEY A 验证
//	4 — 用 KEY B 验证
//	keyAdr：密码扇区号（读写器中装载的扇区号）
//	block: 将要验证的扇区所在的任意块号(64～255)，验证成功后，这个扇区的所有块都可以读写操作
//	返回值：=0 成功；
dukaqi.authenticationKey = function()
{
	var iRet = MWRFATL.mifare_authentication(0,11);
	if(iRet)
	{
//		msg.value =msg.value + "验证方式一验证密码失败！" +"\n";
		iRet = MWRFATL.mifare_authentication_2(0,11,44);//此函数验证时传入的是块号，验证成功后，这个扇区的所有块都可以读写操作
		if(iRet)
		{
//			msg.value =msg.value + "验证密码失败！" +"\n";
		}
		else
		{
//			msg.value =msg.value + "验证密码成功！" +"\n";
		}
	}
	else
	{
//		msg.value =msg.value + "验证方式一验证密码成功！" +"\n";
	}
	return iRet;
};

//写数据
//	函数：SHORT mifare_write(SHORT block, BSTR data_buff)
//	参数：
//	block : 写入数据的块地址 (1~63)
//	data_buff: 要写入数据，长度<=16
//	返回值：=0 成功
dukaqi.writeData = function()
{
	var data="科曼信息技术公司";
	var iRet = MWRFATL.mifare_write(44,data);
	if(iRet)
	{
//		alert(iRet);
//		msg.value =msg.value + "写数据失败！" +"\n";
	}
	else
	{
//		msg.value =msg.value + "写数据成功！" +"\n";
	}
	return iRet;
};
		
//读数据
//	函数：BSTR mifare_read(SHORT block)
//	参数：
//	blocknr:读取数据的块(0~63)
//	返回值：返回读取的数据。
dukaqi.readData = function ()
{
	var iRet=MWRFATL.mifare_read(44);
//	alert(iRet);
//	msg.value =msg.value+ iRet +"\n";
	return iRet;
};

/**
 * 显示卡内所有数据块的内容
 * M1卡  前16扇区
 */
dukaqi.readAllData = function ()
{
	//十六个扇区，没扇区4个数据块
	var shanqu = 16;
	var msg = "";
	var iRet = "";
	for(var i=1;i<shanqu;i++){
		msg = msg + "第"+i+"扇区:"+"\n";
		for(var j=i*4;j<(i+1)*4;j++){
			iRet = MWRFATL.mifare_read(j);
			msg = msg + "第"+i+"扇区:第"+j+"块:"+iRet+"\n";
		}
	}
//	document.getElementById("msg").value = msg;
};


//以16进制写数据
//	函数：SHORT mifare_writeHex(SHORT block, BSTR data_buff)
//	参数：
//	block : 写入数据的块地址 (1~63)
//	data_buff: 要写入数据，长度必须为 32，如果长度不够 32，函数会失败
//	返回值：=0 成功
//	说明：要写入的字符串为“a0b1c2d3e4f5”，该函数先将其转换为 16 进制再写
//	进卡片，写入卡片的数据为{0xa0,0xb1,0xc2,0xd3,0xe4,0xf5}
dukaqi.writeDataHex = function (val)
{
	if(val == null || val==""){
		alert("要写入的数据不能为空");
		return;
	}
	alert(val);
	if(val.length!=32){
		alert("长度必须为三十二位");
		return;
	}
	
	var iRet = MWRFATL.mifare_writeHex(44,val);
	if(iRet)
	{
//		msg.value =msg.value + "以16进制写数据失败！" +"\n";
	}
	else
	{
//		msg.value =msg.value + "以16进制写数据成功！" +"\n";
	}
	return iRet;
};

//以16进制读数据
//	函数：BSTR mifare_readHex(SHORT block)
//	参数：
//	blocknr:读取数据的块(0~63)
//	返回值：返回读取的数据。
//	说明：卡片中某块存储的数据为{0xa0,0xb1,0xc2,0xd3,0xe4,0xf5}
//	则读出的数据为字符串“a0b1c2d3e4f5”
dukaqi.readDataHex = function()
{
//	alert(44);
	var databuff = MWRFATL.mifare_readHex(44);
//	alert(databuff);
//	msg.value =msg.value + databuff +"\n";
	return databuff;
};
		
//改写密码
//	函数：SHORT ChangeKey(SHORT sector, BSTR KeyA, SHORT B0, SHORT B1,
//			SHORT B2, SHORT B3, BSTR KeyB)
//			参数：
//			sector： 要改写密码的扇区
//			KeyA： A 密码
//			B0 ： 块 0控制字，低 3位（D2D1D0）对应 C10、C20、C30
//			B1 ： 块 1控制字，低 3 位（D2D1D0）对应 C11、C21、C31
//			B2 ： 块 2控制字，低 3 位（D2D1D0）对应 C12、C22、C32
//			B3 ： 块 3控制字，低 3 位（D2D1D0）对应 C13、C23、C33
//			KeyB： B密码
//			返回值：=0 成功。
//			例：
//			__int16 st=ChangeKey(0,"ffffffffffff",0,0,0,1,"ffffffffffff");
dukaqi.changeCardKey = function ()
{
	var iRet = MWRFATL.ChangeKey(11,"ffffffffffff",0,0,0,1,"ffffffffffff");
	alert(iRet);
	if(iRet)
	{
//		msg.value =msg.value + "改写密码失败！" +"\n";
	}
	else
	{
//		msg.value =msg.value + "改写密码成功！" +"\n";
	}
	return iRet;
};
		
//关闭卡片
dukaqi.closeCard = function()
{
	var iRet = MWRFATL.CloseCard();//关闭开篇
//	if(iRet)
//	{
//		msg.value =msg.value + "关闭卡片失败！" +"\n";
//	}
//	else
//	{
//		msg.value =msg.value + "关闭卡片成功！" +"\n";
//	}
//		MWRFATL.ReaderBeep(30);
	return iRet;
};
		
//断开设备  关闭读卡器
dukaqi.exit = function()
{
	var iRet = MWRFATL.CloseReader();
	if(iRet)
	{
//		msg.value =msg.value + "断开设备失败！" +"\n";
	}
	else
	{
//		msg.value =msg.value + "断开设备成功！" +"\n";
	}
	return iRet;
};

//ascII转换为Hex 十六进制
dukaqi.ascToHex = function(){
	var asc = "12345678"+"";
	alert(asc.length);
	var bstr = MWRFATL.ascToHex(asc, asc.length);
	alert(bstr);
	return bstr;
};


//十六进制转换为ascII
dukaqi.hexToAsc = function(){
	var hex = "";
	var rst = MWRFATL.hexToAsc(hex,hex.length);
	alert(rst);
	return rst;
};


/**
 * 初始化某一块的值
 * 函数：SHORT mifare_initValue(SHORT block, LONG value)
	参数:
	block: 块地址
	value: 初始化的值
	返回值：=0 成功
 */
dukaqi.initValue = function(block,value){
	var rst = MWRFATL.mifare_initValue(block,value);
	alert("结果:"+rst);
};

/**
 * 根据块地址，读取块中的值
 * 函数：LONG mifare_value(SHORT block)
	参数：
	block:  值操作的块地址
	返回值：返回操作块的当前值
 */
dukaqi.getBlockValue = function(block){
	var rst = MWRFATL.mifare_value(block*1);
	alert("结果:"+rst);
};

/**
 * 对值操作的块进行增值操作
 * 函数：SHORT mifare_increment(SHORT block, LONG value)
	参 数：
	block: 值操作的块地址(1-63)
	value: 增加的值
	返回值：=0 成功。
 */
dukaqi.increment = function(block,value){
	var b = 64;
	var v = 0;
	var rst = MWRFATL.mifare_increment(b,v);
	alert(rst);
};


/**
 * 对值操作的块进行减值操作
 * 函数：SHORT mifare_decrement(SHORT block, LONG value)
   参 数：block: 值操作的块地址 
   value: 减少的值
   返回值：=0 成功。
 */
dukaqi.decrement = function(bolck,value){
	var b = 64;
	var v = 0;
	var rst = MWRFATL.mifare_decrement(b,v);
	alert(rst);
};