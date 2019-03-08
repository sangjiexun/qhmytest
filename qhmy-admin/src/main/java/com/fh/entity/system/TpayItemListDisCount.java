package com.fh.entity.system;

import java.io.Serializable;

/**
 * 
 * <p>标题:TpayItemListDisCount</p>
 * <p>描述:名单优惠表</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月23日 下午5:25:35
 */
public class TpayItemListDisCount implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String PKID;
	
	/**
	 * 优惠范围
	 */
	private String DISCOUNT_SCOPE;
	
	/**
	 * 优惠金额
	 */
	private String DISCOUNT_MONEY;
	
	/**
	 * 折扣  0-10之间的值
	 */
	private String DISCOUNT;
	
	/**
	 * 优惠方式  0:不优惠  1:打折  2:直减  字典类型DiscountModeEnum
	 */
	private String DISCOUNT_MODE;
	
	/**
	 * 名单表pkid
	 */
	private String PAY_ITEM_LIST_PKID;
	
	/**
	 * 是否单独调整的  Y:是  N:否  字典类型YesOrNo
	 */
	private String ALONEFLAG;

	public String getPKID() {
		return PKID;
	}

	public void setPKID(String pKID) {
		PKID = pKID;
	}

	public String getDISCOUNT_SCOPE() {
		return DISCOUNT_SCOPE;
	}

	public void setDISCOUNT_SCOPE(String dISCOUNT_SCOPE) {
		DISCOUNT_SCOPE = dISCOUNT_SCOPE;
	}

	public String getDISCOUNT_MONEY() {
		return DISCOUNT_MONEY;
	}

	public void setDISCOUNT_MONEY(String dISCOUNT_MONEY) {
		DISCOUNT_MONEY = dISCOUNT_MONEY;
	}

	public String getDISCOUNT() {
		return DISCOUNT;
	}

	public void setDISCOUNT(String dISCOUNT) {
		DISCOUNT = dISCOUNT;
	}

	public String getDISCOUNT_MODE() {
		return DISCOUNT_MODE;
	}

	public void setDISCOUNT_MODE(String dISCOUNT_MODE) {
		DISCOUNT_MODE = dISCOUNT_MODE;
	}

	public String getPAY_ITEM_LIST_PKID() {
		return PAY_ITEM_LIST_PKID;
	}

	public void setPAY_ITEM_LIST_PKID(String pAY_ITEM_LIST_PKID) {
		PAY_ITEM_LIST_PKID = pAY_ITEM_LIST_PKID;
	}

	public String getALONEFLAG() {
		return ALONEFLAG;
	}

	public void setALONEFLAG(String aLONEFLAG) {
		ALONEFLAG = aLONEFLAG;
	}
	
}
