package com.fh.service.itemlist.discount;

/**
 * 
 * <p>标题:DisCountCalculateFactory</p>
 * <p>描述:获得计算优惠实现类</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月23日 下午8:08:51
 */
public class DisCountCalculateFactory {
	
	private static DisCountCalculateByDis disCountCalculateByDis = new DisCountCalculateByDis();
	
	private static DisCountCalculateByRule disCountCalculateByRule = new DisCountCalculateByRule();
	
	public enum Type{
		按名单表优惠子表计算,
		按项目规则表计算
	}
	
	private DisCountCalculateFactory(){
		
	}
	
	public static DisCountCalculateInterface getInstance(Type type){
		if (type.name().toString().equals(Type.按名单表优惠子表计算.toString())) {
			return disCountCalculateByDis;
		}else if (type.name().toString().equals(Type.按项目规则表计算.toString())) {
			return disCountCalculateByRule;
		}
		return null;
	}
	
	
}
