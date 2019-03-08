package com.keman.zhgd.common;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 标题:DataZidianSelect
 * </p>
 * <p>
 * 描述:高校缴费系统字典类
 * </p>
 * <p>
 * 组织:河北科曼信息技术有限公司
 * </p>
 * 
 * @author Administrator 鲁震
 * @date 2017年8月3日 上午10:22:45
 */
public class DataZidianSelect {

	/**
	 * <p>
	 * 标题:Zhiwei
	 * </p>
	 * <p>
	 * 描述:职位
	 * </p>
	 * <p>
	 * 组织:河北科曼信息技术有限公司
	 * </p>
	 * 
	 * @author Administrator 鲁震
	 * @date 2017年8月3日 上午10:23:19
	 */
	public enum Zhiwei {

		党委书记("001"), 校长("002"), 副校长("003"), 团委书记("004"), 团支部书记("005"), 学生会主席(
				"006"), 学生处主任("007"), 教研室主任("008"), 科系主任("009"), 班主任("010"), 辅导员(
				"011"), 导师("012"), ;
		private String value;

		Zhiwei(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			Zhiwei[] lanmuUrlMobans = values();
			for (Zhiwei XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			Zhiwei[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (Zhiwei obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}

	/**
	 * <p>标题:StudentZt</p>
	 * <p>描述:学生状态</p>
	 * <p>组织:河北科曼信息技术有限公司</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月3日 上午10:42:59
	 */
	public enum StudentZt {

		正常("1"), 作废("0"), ;
		private String value;

		StudentZt(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			StudentZt[] lanmuUrlMobans = values();
			for (StudentZt XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			StudentZt[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (StudentZt obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	
	/**
	 * 
	 * <p>标题:TransactionType</p>
	 * <p>描述:交易类型</p>
	 * <p>组织:河北科曼信息技术有限公司</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月3日 上午11:08:30
	 */
	public enum TransactionType {

		收费("SF"), 退费("TF"), QT("其他");
		private String value;

		TransactionType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			TransactionType[] lanmuUrlMobans = values();
			for (TransactionType XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			TransactionType[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (TransactionType obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	/**
	 * 
	 * <p>标题:CheckStatus</p>
	 * <p>描述:核验状态枚举</p>
	 * <p>组织:河北科曼信息技术有限公司</p>
	 * @author wn 王宁
	 * @date 2017年8月12日 下午3:09:17
	 */
	public enum CheckStatus {

		已验("1"), 
		未验("0"); 
		private String value;

		CheckStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			CheckStatus[] checkStatus = values();
			for (CheckStatus checkStatu : checkStatus) {
				if (checkStatu.getValue().equals(value)) {
					return checkStatu.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			CheckStatus[] checkStatus = values();
			List list = new ArrayList();
			for (CheckStatus obj : checkStatus) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	/**
	 * 
	 * <p>标题:TransactionDirection</p>
	 * <p>描述:余额方向</p>
	 * <p>组织:河北科曼信息技术有限公司</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月3日 上午11:10:14
	 */
	public enum TransactionDirection{

		增加("ADD"), 减少("DEL"), ;
		private String value;

		TransactionDirection(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			TransactionDirection[] lanmuUrlMobans = values();
			for (TransactionDirection XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			TransactionDirection[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (TransactionDirection obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	
	/**
	 * 
	 * <p>标题:TransactionMode</p>
	 * <p>描述:支付方式渠道</p>
	 * <p>组织:河北科曼信息技术有限公司</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月3日 上午11:11:36
	 */
	public enum TransactionMode{
		网上("ONLINE"),
		现场("LOCALE"),
		其他("OTHER"),
		;
		private String value;

		TransactionMode(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			TransactionMode[] lanmuUrlMobans = values();
			for (TransactionMode XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			TransactionMode[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (TransactionMode obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	
	/**
	 * 
	 * <p>标题:TransactionPayPlatform</p>
	 * <p>描述:支付平台</p>
	 * <p>组织:河北科曼信息技术有限公司</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月3日 上午11:13:49
	 */
	public enum TransactionPayPlatform{
		农信社("NXS"),
		支付宝("ZFB"),
		微信("WX"),
		只记账("ZJZ"),
		银联("UNION")
		;
		private String value;

		TransactionPayPlatform(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			TransactionPayPlatform[] lanmuUrlMobans = values();
			for (TransactionPayPlatform XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			TransactionPayPlatform[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (TransactionPayPlatform obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	
	/**
	 * <p>标题:TransactionStatus</p>
	 * <p>描述:交易状态</p>
	 * <p>组织:河北科曼信息技术有限公司</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月3日 上午11:15:45
	 */
	public enum TransactionStatus{
		未支付("0"),
		支付成功("1"),
		支付失败("-1"),
		对账成功("2"),
		对账失败("-2"),
		;
		private String value;

		TransactionStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			TransactionStatus[] lanmuUrlMobans = values();
			for (TransactionStatus XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			TransactionStatus[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (TransactionStatus obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	
	/**
	 * <p>标题:OrdercreateMode</p>
	 * <p>描述:订单生成方式</p>
	 * <p>组织:河北科曼信息技术有限公司</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月3日 下午2:23:43
	 */
	public enum OrdercreateMode{
		线上("U"),
		线下("D"),
		线下其他收费("DO"),
		导入("I"),
		;
		private String value;

		OrdercreateMode(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			OrdercreateMode[] lanmuUrlMobans = values();
			for (OrdercreateMode XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			OrdercreateMode[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (OrdercreateMode obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	
	/**
	 * 
	 * <p>标题:OrdercreateTerminal</p>
	 * <p>描述:订单生成终端</p>
	 * <p>组织:河北科曼信息技术有限公司</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月3日 下午2:34:49
	 */
	public enum OrdercreateTerminal{
		PC("07"),
		iOS("08"),
		Android("09"),
		其他("10"),
		;
		private String value;

		OrdercreateTerminal(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			OrdercreateTerminal[] lanmuUrlMobans = values();
			for (OrdercreateTerminal XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			OrdercreateTerminal[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (OrdercreateTerminal obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	
	/**
	 * <p>标题:YesOrNo</p>
	 * <p>描述:是或否</p>
	 * <p>组织:河北科曼信息技术有限公司</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月3日 下午3:26:28
	 */
	public enum YesOrNo{
		是("Y"),
		否("N"),
		;
		private String value;

		YesOrNo(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			YesOrNo[] lanmuUrlMobans = values();
			for (YesOrNo XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			YesOrNo[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (YesOrNo obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	
	/**
	 * <p>标题:PayOrderDetailStatus</p>
	 * <p>描述:订单明细表状态</p>
	 * <p>组织:河北科曼信息技术有限公司</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月3日 下午3:27:44
	 */
	public enum PayOrderDetailStatus{
		正常("1"),
		转入("2"),
		转出("3"),
		;
		private String value;

		PayOrderDetailStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			PayOrderDetailStatus[] lanmuUrlMobans = values();
			for (PayOrderDetailStatus XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			PayOrderDetailStatus[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (PayOrderDetailStatus obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	
	/**
	 * <p>标题:InputOutputEnum</p>
	 * <p>描述:进项或销项标记  进项:JX   销项:XX   进项表示金额是正数,增加的   销项表示金额是负数,减少的,需要根据收费,退费,正常,转入,转出等状态判断</p>
	 * <p>组织:河北科曼信息技术有限公司</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月3日 下午3:30:22
	 */
	public enum InputOutputEnum{
		进项("JX"),
		销项("XX"),
		;
		private String value;

		InputOutputEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			InputOutputEnum[] lanmuUrlMobans = values();
			for (InputOutputEnum XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			InputOutputEnum[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (InputOutputEnum obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	
	/**
	 * <p>标题:PayModeEnum</p>
	 * <p>描述:</p>
	 * <p>组织:河北科曼信息技术有限公司</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月3日 下午3:41:35
	 */
	public enum PayModeEnum{
		现金("CASH"),
		银行卡("CARD"),
		微信("WX"),
		支付宝("ZFB"),
		;
		private String value;

		PayModeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			PayModeEnum[] lanmuUrlMobans = values();
			for (PayModeEnum XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			PayModeEnum[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (PayModeEnum obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	
	/**
	 * 
	 * <p>标题:ItemListCreateModeEnum</p>
	 * <p>描述:项目名单生成方式</p>
	 * <p>组织:河北科曼信息技术有限公司</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月3日 下午3:48:37
	 */
	public enum ItemListCreateModeEnum{
		导入名单("1"),
		系统根据规则生成("2"),
		;
		private String value;

		ItemListCreateModeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			ItemListCreateModeEnum[] lanmuUrlMobans = values();
			for (ItemListCreateModeEnum XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			ItemListCreateModeEnum[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (ItemListCreateModeEnum obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	
	/**
	 * <p>标题:PayItemListStatusEnum</p>
	 * <p>描述:缴费名单状态</p>
	 * <p>组织:河北科曼信息技术有限公司</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月3日 下午3:50:42
	 */
	public enum PayItemListStatusEnum{
		欠费("0"),
		核验中("1"),
		已完成("2"),
		已关闭("3"),
		;
		private String value;

		PayItemListStatusEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			PayItemListStatusEnum[] lanmuUrlMobans = values();
			for (PayItemListStatusEnum XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			PayItemListStatusEnum[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (PayItemListStatusEnum obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	
	
//	优惠方式  0:不优惠  1:打折  2:直减
	public enum DiscountModeEnum{
		不优惠("0"),
		打折("1"),
		直减("2"),
		;
		private String value;

		DiscountModeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			DiscountModeEnum[] lanmuUrlMobans = values();
			for (DiscountModeEnum XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			DiscountModeEnum[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (DiscountModeEnum obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	

	
//	状态:  0待审核 1:审核成功  -1审核失败  2已结束  3已过期
	public enum StatusEnum{
		待审核("0"),
		审核成功("1"),
		审核失败("-1"),
		已结束("2"),
		已过期("3"),
		;
		private String value;

		StatusEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			StatusEnum[] lanmuUrlMobans = values();
			for (StatusEnum XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			StatusEnum[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (StatusEnum obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	
	
	/**
	 * 
	 * <p>标题:MenuButtonEnum</p>
	 * <p>描述:菜单还是按钮的字典</p>
	 * <p>组织:河北科曼信息技术有限公司</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月12日 下午6:02:36
	 */
	public enum MenuButtonEnum{
		菜单("menu"),
		按钮("button"),
		;
		private String value;

		MenuButtonEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			MenuButtonEnum [] lanmuUrlMobans = values();
			for (MenuButtonEnum XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			MenuButtonEnum [] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (MenuButtonEnum obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	
	/**
	 * 
	 * <p>标题:UserStats</p>
	 * <p>描述:后台用户状态</p>
	 * <p>组织:河北科曼信息技术有限公司</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月13日 下午5:01:20
	 */
	public enum UserStats {

		正常("0"), 停用("1"), ;
		private String value;

		UserStats(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return this.name();
		}

		/**
		 * 根据value找name
		 * 
		 * @param value
		 * @return
		 */
		public static String getNameByValue(String value) {
			UserStats[] lanmuUrlMobans = values();
			for (UserStats XmZt : lanmuUrlMobans) {
				if (XmZt.getValue().equals(value)) {
					return XmZt.getName();
				}
			}
			return "";
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getValueList() {
			UserStats[] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (UserStats obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}

	}
	
	
	
}
