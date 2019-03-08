package com.fh.webservice.server;

public enum ErrorCodeEnum {
	
	E_001("服务端代码异常"),
	E_002("客户端数据解析异常"),
	E_003("验签不通过"),
	E_004("验签异常"),
	E_005("服务端转JSON字符串异常"),
	E_006("获取IP地址出现异常"),
	E_007("MD5加密出现异常"),
	E_008("转换为JSON字符串出现异常"),
	E_009("心跳异常"),
	E_010("服务器时间获取出现异常"),
	E_011("请求参数异常"),
	E_012("下载基础数据异常"),
	E_013("上传打卡记录数据异常"),
	E_014("下载劳务人员记录数据异常"),
	E_015("日志插入异常"),
	E_016("方法参数为空"),
	E_017("上传工作站状态异常"),
	E_018("下载黑名单异常"),
	;
	private String value;
	
	ErrorCodeEnum(String value){
		this.value = value;
	}

	public String getvalue() {
		return value;
	}

	public void setvalue(String value) {
		this.value = value;
	}
	
	public String getName(){
		return this.name();
	}
	
	/**
	 * 根据value找name
	 * @param value
	 * @return
	 */
	public static String getNameByValue(String value){
		ErrorCodeEnum [] fhadminEnums = values();
		for (ErrorCodeEnum fhadminEnum : fhadminEnums) {
			if (fhadminEnum.getvalue().equals(value)) {
				return fhadminEnum.getName();
			}
		}
		return "";
	}
	
}
