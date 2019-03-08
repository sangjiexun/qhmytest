package com.fh.entity.system;

import java.io.Serializable;

import com.keman.zhgd.common.DataZidianSelect.DiscountModeEnum;

/**
 * 
 * <p>标题:TpayItemList</p>
 * <p>描述:导入名单实体</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月23日 下午4:29:09
 */
public class TpayItemList implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String PKID;
	
	private String PAY_ITEM_PKID;
	
	private String STUDENT_PKID;
	
	/**
	 * 状态  字典类获取,  0欠费, 1核验中,2已完成,3已关闭  字典类型:PayItemListStatusEnum
	 */
	private String STATUS;
	
	/**
	 * 费用
	 */
	private String COST;
	
	/**
	 * 优惠  如：直减 - 2000
	 */
	private String DISCOUNT;
	
	/**
	 * 应收金额
	 */
	private String AMOUNTRECEIVABLE;
	
	/**
	 * 实收金额
	 */
	private String AMOUNTCOLLECTION;
	
	/**
	 * 优惠金额
	 */
	private String DISCOUNT_MONEY;
	
	/**
	 * 优惠方式  0:不优惠  1:打折  2:直减   字典类型:DiscountModeEnum
	 */
	private String DISCOUNT_MODE;
	
	/**
	 * PAY_ITEM_RULE表PKID
	 */
	private String PAY_ITEM_RULE;
	
	/**
	 * 优惠对象
	 */
	private TpayItemListDisCount tpayItemListDisCount;
	
	
	public TpayItemList(){
		this.setDISCOUNT_MODE(DiscountModeEnum.不优惠.getValue());//是否优惠
		this.setDISCOUNT_MONEY("0");//优惠金额
		this.setDISCOUNT("");//优惠描述
	}
	
	public String getPKID() {
		return PKID;
	}

	public void setPKID(String pKID) {
		PKID = pKID;
	}

	public String getPAY_ITEM_PKID() {
		return PAY_ITEM_PKID;
	}

	public void setPAY_ITEM_PKID(String pAY_ITEM_PKID) {
		PAY_ITEM_PKID = pAY_ITEM_PKID;
	}

	public String getSTUDENT_PKID() {
		return STUDENT_PKID;
	}

	public void setSTUDENT_PKID(String sTUDENT_PKID) {
		STUDENT_PKID = sTUDENT_PKID;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getCOST() {
		return COST;
	}

	public void setCOST(String cOST) {
		COST = cOST;
	}

	public String getDISCOUNT() {
		return DISCOUNT;
	}

	public void setDISCOUNT(String dISCOUNT) {
		DISCOUNT = dISCOUNT;
	}

	public String getAMOUNTRECEIVABLE() {
		return AMOUNTRECEIVABLE;
	}

	public void setAMOUNTRECEIVABLE(String aMOUNTRECEIVABLE) {
		AMOUNTRECEIVABLE = aMOUNTRECEIVABLE;
	}

	public String getAMOUNTCOLLECTION() {
		return AMOUNTCOLLECTION;
	}

	public void setAMOUNTCOLLECTION(String aMOUNTCOLLECTION) {
		AMOUNTCOLLECTION = aMOUNTCOLLECTION;
	}

	public String getDISCOUNT_MONEY() {
		return DISCOUNT_MONEY;
	}

	public void setDISCOUNT_MONEY(String dISCOUNT_MONEY) {
		DISCOUNT_MONEY = dISCOUNT_MONEY;
	}

	public String getDISCOUNT_MODE() {
		return DISCOUNT_MODE;
	}

	public void setDISCOUNT_MODE(String dISCOUNT_MODE) {
		DISCOUNT_MODE = dISCOUNT_MODE;
	}

	public String getPAY_ITEM_RULE() {
		return PAY_ITEM_RULE;
	}

	public void setPAY_ITEM_RULE(String pAY_ITEM_RULE) {
		PAY_ITEM_RULE = pAY_ITEM_RULE;
	}

	public TpayItemListDisCount getTpayItemListDisCount() {
		return tpayItemListDisCount;
	}

	public void setTpayItemListDisCount(TpayItemListDisCount tpayItemListDisCount) {
		this.tpayItemListDisCount = tpayItemListDisCount;
	}
	
}
