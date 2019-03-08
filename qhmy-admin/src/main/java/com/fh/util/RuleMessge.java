package com.fh.util;

import java.util.HashMap;
import java.util.Map;

public class RuleMessge {
	
	public static final Map<String,Object> rulemrz = new HashMap<String,Object>();
	static{
		//实名登记规则默认值
		rulemrz.put("YOUCHENGFAJLKYDJ", "Y");
		rulemrz.put("TONGGONGNL", 0);
		rulemrz.put("NANXINGTXNL", 0);
		rulemrz.put("NVXINGTXNL", 0);
		rulemrz.put("SHIFOUBXSCZS", "Y");
		rulemrz.put("SHIFOUQYGRJZ", "N");
		rulemrz.put("CHENGFA_JZXJXG", "N");
		rulemrz.put("NIANLING_JZXJXG", "N");
		rulemrz.put("ZHENGSHU_JZXJXG", "N");
		rulemrz.put("HEIMINGDANX", "N");
		rulemrz.put("TGBKDJ_CK", "N");
		rulemrz.put("NXTXBDJ_CK", "N");
		rulemrz.put("VXTXBDJ_CK", "N");
		
		//考勤同行规则默认值
		rulemrz.put("WEIJIAOYUJC", "Y");
		rulemrz.put("CHANGSJBSKBSX", "Y");
		
		//工人地域规则默认值
		//工人民族规则默认值
		//预警设置规则默认值
		rulemrz.put("CHUQIN_BANZU", 80);
		rulemrz.put("CHUQIN_DUIWU", 80);
		rulemrz.put("CHUQIN_GONGZHONG", 80);
		rulemrz.put("GONGRENZCSJ_TIAN", 3);
		rulemrz.put("GONGRENZCSJ_XIAOSHI", 10);
		rulemrz.put("GONGREN_YJWCC","24:00");
		rulemrz.put("ANQUANJY_YUE", 1);
		rulemrz.put("ZHENGSHU_TIAN", 5);
		
		rulemrz.put("CHUQIN_BANZU_BTN", 0);
		rulemrz.put("CHUQIN_DUIWU_BTN", 0);
		rulemrz.put("CHUQIN_GONGZHONG_BTN", 0);
		rulemrz.put("GONGRENZCSJ_BTN", 0);
		rulemrz.put("ANQUANJY_BTN", 0);
		rulemrz.put("ZHENGSHU_BTN", 0);
		rulemrz.put("GONGREN_YJWCC_BTN", 0);
		
		
		
		
		
		
		//门区设置规则默认值
		
		
		
	}

}
