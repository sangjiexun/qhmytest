package com.fh.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DictZiDianSelect {
	
	
	public enum DicLeiXingEnum{
		分类("001"),
		地区("003"),
		项目类型("004"),
		违规违纪类型("005"),
		教育类型("006"),
		奖励类型("007"),
		预警类型("008"),
		分包单位类型("009"),
		班组类型("010"),
		受教育程度("011"),
		;
		private String value;
		
		DicLeiXingEnum(String value){
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
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
			DicLeiXingEnum [] lanmuUrlMobans = values();
			for (DicLeiXingEnum obj : lanmuUrlMobans) {
				if (obj.getValue().equals(value)) {
					return obj.getName();
				}
			}
			return "";
		}
		
		
		/**
		 * 根据name找value
		 * @param value
		 * @return
		 */
		public static String getValueByName(String name){
			DicLeiXingEnum [] lanmuUrlMobans = values();
			for (DicLeiXingEnum obj : lanmuUrlMobans) {
				if (obj.getName().equals(name)) {
					return obj.getValue();
				}
			}
			return "";
		}
		
		
		
		
		public static List getValueList(){
			DicLeiXingEnum [] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (DicLeiXingEnum obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}
		
		
		
	}
	public enum ShexiangjiEnum{
		松下("001"),
		佳能("002"),
		三星("003"),
		爱国者("004"),
		康佳("005"),
		JVC("006"),
		其他("007"),
		
		;
		private String value;
		
		ShexiangjiEnum(String value){
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
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
			ShexiangjiEnum [] lanmuUrlMobans = values();
			for (ShexiangjiEnum obj : lanmuUrlMobans) {
				if (obj.getValue().equals(value)) {
					return obj.getName();
				}
			}
			return "";
		}
		
		
		/**
		 * 根据name找value
		 * @param value
		 * @return
		 */
		public static String getValueByName(String name){
			ShexiangjiEnum [] lanmuUrlMobans = values();
			for (ShexiangjiEnum obj : lanmuUrlMobans) {
				if (obj.getName().equals(name)) {
					return obj.getValue();
				}
			}
			return "";
		}
		
		
		public static List getValueList(){
			ShexiangjiEnum [] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (ShexiangjiEnum obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}
		
		
		
	}
	public enum PingJiaZTEnum{
		合格("HG"),
		不合格("BHG");
		private String value;
		
		PingJiaZTEnum(String value){
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
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
			PingJiaZTEnum [] lanmuUrlMobans = values();
			for (PingJiaZTEnum obj : lanmuUrlMobans) {
				if (obj.getValue().equals(value)) {
					return obj.getName();
				}
			}
			return "";
		}
		
		
		/**
		 * 根据name找value
		 * @param value
		 * @return
		 */
		public static String getValueByName(String name){
			PingJiaZTEnum [] lanmuUrlMobans = values();
			for (PingJiaZTEnum obj : lanmuUrlMobans) {
				if (obj.getName().equals(name)) {
					return obj.getValue();
				}
			}
			return "";
		}
		public static List getValueList(){
			PingJiaZTEnum [] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (PingJiaZTEnum obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}
		
		
		
	}//
	
	
	
	public enum DengJiEnum{
		登记提醒("0"),
		禁止登记("1");
		private String value;
		
		DengJiEnum(String value){
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
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
			DengJiEnum [] lanmuUrlMobans = values();
			for (DengJiEnum obj : lanmuUrlMobans) {
				if (obj.getValue().equals(value)) {
					return obj.getName();
				}
			}
			return "";
		}
		
		
		/**
		 * 根据name找value
		 * @param value
		 * @return
		 */
		public static String getValueByName(String name){
			DengJiEnum [] lanmuUrlMobans = values();
			for (DengJiEnum obj : lanmuUrlMobans) {
				if (obj.getName().equals(name)) {
					return obj.getValue();
				}
			}
			return "";
		}
		
		
		public static List getListMap(){
			DengJiEnum [] lanmuUrlMobans = values();
			Map a= new HashMap<String,Object>();
			List list = new ArrayList();
			
			/*for (DengJiEnum obj : lanmuUrlMobans) {
				a.put(obj.getName(), obj.getValue());
				list.add(a);
				//break;
			}*/
			return list;
		}
		public static List getValueList(){
			DengJiEnum [] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (DengJiEnum obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}
	}
	
	
	
	
	public enum GongZiDW{
		天("t"),
		月("y"),
		工("g");
		private String value;
		
		GongZiDW(String value){
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
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
			GongZiDW [] lanmuUrlMobans = values();
			for (GongZiDW obj : lanmuUrlMobans) {
				if (obj.getValue().equals(value)) {
					return obj.getName();
				}
			}
			return "";
		}
		
		
		/**
		 * 根据name找value
		 * @param value
		 * @return
		 */
		public static String getValueByName(String name){
			GongZiDW [] lanmuUrlMobans = values();
			for (GongZiDW obj : lanmuUrlMobans) {
				if (obj.getName().equals(name)) {
					return obj.getValue();
				}
			}
			return "";
		}
		
		
		public static List getListMap(){
			GongZiDW [] lanmuUrlMobans = values();
			Map a= new HashMap<String,Object>();
			List list = new ArrayList();
			
			/*for (DengJiEnum obj : lanmuUrlMobans) {
				a.put(obj.getName(), obj.getValue());
				list.add(a);
				//break;
			}*/
			return list;
		}
		public static List getValueList(){
			GongZiDW [] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (GongZiDW obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}
	}
	
	
	public enum QIYELEIBIE{
		劳务分包("001"),
		专业分包("002");
		private String value;
		
		QIYELEIBIE(String value){
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
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
			QIYELEIBIE [] lanmuUrlMobans = values();
			for (QIYELEIBIE obj : lanmuUrlMobans) {
				if (obj.getValue().equals(value)) {
					return obj.getName();
				}
			}
			return "";
		}
		
		
		/**
		 * 根据name找value
		 * @param value
		 * @return
		 */
		public static String getValueByName(String name){
			QIYELEIBIE [] lanmuUrlMobans = values();
			for (QIYELEIBIE obj : lanmuUrlMobans) {
				if (obj.getName().equals(name)) {
					return obj.getValue();
				}
			}
			return "";
		}
		public static List getValueList(){
			QIYELEIBIE [] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (QIYELEIBIE obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}
		
		
		
	}

	public enum HeiMingDanEnum{
		内部("1"),
		外部("2"),
		
		;
		private String value;
		
		HeiMingDanEnum(String value){
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
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
			HeiMingDanEnum [] lanmuUrlMobans = values();
			for (HeiMingDanEnum obj : lanmuUrlMobans) {
				if (obj.getValue().equals(value)) {
					return obj.getName();
				}
			}
			return "";
		}
		
		
		/**
		 * 根据name找value
		 * @param value
		 * @return
		 */
		public static String getValueByName(String name){
			HeiMingDanEnum [] lanmuUrlMobans = values();
			for (HeiMingDanEnum obj : lanmuUrlMobans) {
				if (obj.getName().equals(name)) {
					return obj.getValue();
				}
			}
			return "";
		}
		
		
		public static List getValueList(){
			HeiMingDanEnum [] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (HeiMingDanEnum obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}
		
		
		
	}
	
	
	/**
	 * 技能等级
	 * @author Administrator
	 *
	 */
	public enum JndjEnum{
		初级("CJ"),
		中级("ZJ"),
		高级("GJ"),
		特种工("TZG"),
		;
		private String value;
		
		JndjEnum(String value){
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
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
			JndjEnum [] lanmuUrlMobans = values();
			for (JndjEnum obj : lanmuUrlMobans) {
				if (obj.getValue().equals(value)) {
					return obj.getName();
				}
			}
			return "";
		}
		
		
		/**
		 * 根据name找value
		 * @param value
		 * @return
		 */
		public static String getValueByName(String name){
			JndjEnum [] lanmuUrlMobans = values();
			for (JndjEnum obj : lanmuUrlMobans) {
				if (obj.getName().equals(name)) {
					return obj.getValue();
				}
			}
			return "";
		}
		
		
		public static List getValueList(){
			JndjEnum [] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (JndjEnum obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}
		
		
		
	}
	/**
	 * 安全教育状态
	 */

	public enum EduStatus{
		全部("all"),
		已教育("1"),
		未教育("0"),
		;
		private String value;
		
		EduStatus(String value){
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
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
			EduStatus [] lanmuUrlMobans = values();
			for (EduStatus obj : lanmuUrlMobans) {
				if (obj.getValue().equals(value)) {
					return obj.getName();
				}
			}
			return "";
		}
		
		
		/**
		 * 根据name找value
		 * @param value
		 * @return
		 */
		public static String getValueByName(String name){
			EduStatus [] lanmuUrlMobans = values();
			for (EduStatus obj : lanmuUrlMobans) {
				if (obj.getName().equals(name)) {
					return obj.getValue();
				}
			}
			return "";
		}
		
		
		public static List getValueList(){
			EduStatus [] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (EduStatus obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}
		
		
		
	}
	
	public enum PayItemListStatusEnum{
		欠费("0"),
		核验中("1"),
		已完成("2"),
		已关闭("3"),
		;
		private String value;
		
		PayItemListStatusEnum(String value){
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
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
			PayItemListStatusEnum [] lanmuUrlMobans = values();
			for (PayItemListStatusEnum obj : lanmuUrlMobans) {
				if (obj.getValue().equals(value)) {
					return obj.getName();
				}
			}
			return "";
		}
		
		
		/**
		 * 根据name找value
		 * @param value
		 * @return
		 */
		public static String getValueByName(String name){
			PayItemListStatusEnum [] lanmuUrlMobans = values();
			for (PayItemListStatusEnum obj : lanmuUrlMobans) {
				if (obj.getName().equals(name)) {
					return obj.getValue();
				}
			}
			return "";
		}
		
		
		
		
		public static List getValueList(){
			PayItemListStatusEnum [] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (PayItemListStatusEnum obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}
		
		
		
	}
	
	public enum PayModeEnum{
		现金("CASH"),
		银行卡("CARD"),
		微信("WX"),
		支付宝("ZFB"),
		;
		private String value;
		
		PayModeEnum(String value){
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
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
			PayModeEnum [] lanmuUrlMobans = values();
			for (PayModeEnum obj : lanmuUrlMobans) {
				if (obj.getValue().equals(value)) {
					return obj.getName();
				}
			}
			return "";
		}
		
		
		/**
		 * 根据name找value
		 * @param value
		 * @return
		 */
		public static String getValueByName(String name){
			PayModeEnum [] lanmuUrlMobans = values();
			for (PayModeEnum obj : lanmuUrlMobans) {
				if (obj.getName().equals(name)) {
					return obj.getValue();
				}
			}
			return "";
		}
		
		
		
		
		public static List getValueList(){
			PayModeEnum [] lanmuUrlMobans = values();
			List list = new ArrayList();
			for (PayModeEnum obj : lanmuUrlMobans) {
				list.add(obj.getValue());
			}
			return list;
		}
		
		
		
	}

}
