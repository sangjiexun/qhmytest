package com.fh.vo;


/**
 * 
 * <p>标题:ItemListVo</p>
 * <p>描述:导入缴费名单使用的VO对象</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月17日 上午12:19:30
 */
public class ItemListVo {
	
	/**
	 * 项目名称
	 */
	private String itemName; 
	
	/**
	 * 学生名称
	 */
	private String stuName;
	
	/**
	 * 学号
	 */
	private String stuNumber;
	
	/**
	 * 身份证号
	 */
	private String cardNo;
	
	/**
	 * 优惠金额
	 */
	private String discountMoney;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getStuNumber() {
		return stuNumber;
	}

	public void setStuNumber(String stuNumber) {
		this.stuNumber = stuNumber;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(String discountMoney) {
		this.discountMoney = discountMoney;
	}
	
	
}
