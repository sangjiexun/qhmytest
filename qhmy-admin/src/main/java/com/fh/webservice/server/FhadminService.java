package com.fh.webservice.server;

import javax.jws.WebService;

@WebService
public interface FhadminService {
	
	
	/**
	 * 数据接口入口
	 * @param requestData JSON格式的字符串数据
	 * requestData属性说明：
	 * XMBM:项目编码    必传字段
	 * REQUEST_DATE:请求时间(yyyy-MM-dd HH24:mi:ss)  必传   
	 * DATAMESSAGES:数据密文   必填字段   将XMBM属性的值  + HBKEMAN2017 这样的字符进行MD5加密后的密文
	 * METHOD:方法:   心跳检测接口：JC_XTJC 等等  详见文档
	 * MAXRECORD：请求记录最大行数: -1不限制行数或者为空都不限制   其他有效整数做限制
	 * REFRESH_DATE:刷新时间:服务端会根据此时间查询数据表的数据修改时间，大于等于此时间点的数据会返回给客户端 (yyyy-MM-dd HH24:mi:ss)
	 * 
	 * @return JSON格式字符串数据
	 * SERVER_DATE:  服务器时间  必传字段
	 * RST  : 1:成功   -1失败     必传字段  
	 * ERROR: 错误信息描述            必传字段
	 * ERRORCODE：错误类型代码  必传字段
	 * 
	 * 
	 */
	public String dataMain(String requestData);
	
}
