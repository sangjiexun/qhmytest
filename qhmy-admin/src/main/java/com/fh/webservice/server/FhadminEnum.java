package com.fh.webservice.server;


public enum FhadminEnum {
		心跳检测("JC_XTJC"),//T_PROJECT_XT
		上传刷卡数据("SC_DKSJ"),
		下载基础数据("XZ_JCSJ"),//基础数据下载  工作站、摄像机、控制器、项目信息、企业信息
		下载劳务人员("XZ_LWRY"),//下载劳务人员数据
		上传工作站状态("SC_GZZ"),//上传工作站状态
		下载黑名单("XZ_HMD"),//下载黑名单数据
		;
		private String value;
		
		FhadminEnum(String value){
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
			FhadminEnum [] fhadminEnums = values();
			for (FhadminEnum fhadminEnum : fhadminEnums) {
				if (fhadminEnum.getvalue().equals(value)) {
					return fhadminEnum.getName();
				}
			}
			return "";
		}
		
		public static void main(String[] args) {
			
		}
		
	
	
}
