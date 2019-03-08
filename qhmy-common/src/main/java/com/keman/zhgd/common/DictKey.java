package com.keman.zhgd.common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 字典数据
 * @author Administrator
 *
 */
public class DictKey {
	//系統常量
	public final static int EXPORTKEY =10000;//导出excel限制数 限制10000条
	//导出时作为zip导出的阀值
	public final static int EXPORTZIPKEY =5000;//导出excel限制数 限制10000条,超过10000条导出zip
	//导出时作为zip导出每个excle行数
	public final static int EXPORTPAGESIZEKEY =5000;//导出excel限制数 限制10000条,超过10000条导出zip
	
	
	//系统授权用户数量
	public static int sqsl = 5;
	
	//操作类型说明
	public static String XINZENG = "X";//新增
	public static String XIUGAI = "E";//更新
	public static String QIYONG = "Y";//启用
	public static String JINYONG = "N";//禁用
	public static String ZHIDU = "V";//只读
	
	//征收方式
	public static String CZZS = "1";//只读
	public static String HDZS = "0";//只读
	
	//webservice 成功 0，失败 1
	public static String SUCCESS="0";
	public static String FAIL="1";
	
	/**
	 * 内外网标识
	 * 0 - 内网  , 1 - 外网  ,2 - 定时任务为空
	 */
	public static String WLBZ="2";
	/**
	 * 内网到外网
	 * @return
	 */


	public static String[] N_TO_W = new String[]{"t_xt_zy","t_qx_yhbm","t_xt_bm","t_qx_yhjs","t_qx_gnqx","t_qx_js"};
	
	/**
	 * 外网到内网
	 * @return
	 */
	public static String[] W_TO_N = new String[]{"t_yw_fyxx_zdsb","t_yw_tfxx_zb","t_yw_rzxx_zb","t_yw_rzxx_fb1","t_jc_fyxx","t_jc_qydlyh","t_jc_qyxx","t_jc_zdsb","t_jc_xiaoqu"};
	/**
	 * 文件生成路径
	 * @return
	 */
	public static String CreateFilePath="E:/Data1/";
	//public static String CreateFilePath="D:/Data1/";
	/**
	 * 服务器发送地址
	 * @return暂时未使用
	 */
	public static String SentFilePath="D:/Data2/";
	
	/**
	 * 服务器接收地址
	 * @return
	 */
	//public static String ResFilePath="D:/Data3/";
	//public static String ResFilePath="d:/FTP/xly/";
	public static String ResFilePath="d:/FTP/新边界接收文件夹/xly/";
	
	/**
	 * 文件备份地址
	 * @return
	 */
	public static String backupFilePath="D:/Data4/";
	
	public static Map<String,Object> getZts(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("X", "新增");
		result.put("E", "更新");
		result.put("Y", "启用");
		result.put("N", "禁用");
		result.put("F", "已发布");
		return result;
	}

	
	/**
	 * 获取作废状态
	 * @return
	 */
	public static Map<String,Object> getZFZts(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("B", "报废");
		result.put("X", "销售");
		result.put("G", "取消挂靠");
		return result;
	}
	/**
	 * 扩权权限
	 * @return
	 */
	public static Map<String,Object> getKQQX(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("", "全部");
		result.put("01", "省局权限");
		result.put("02", "市局权限");
		return result;
	}
	
	/**
	 * 获取用户组类型
	 * @return
	 */
	public static Map<String,Object> getJslxs(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("", "请选择");
		result.put("GX", "共享使用");
		result.put("GR", "本人使用");
		return result;
	}
	/**
	 * 获取用户组类型
	 * @return
	 */
	public static Map<String,Object> getQJslxs(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("", "全部");
		result.put("XT", "系统");
		result.put("GX", "共享使用");
		result.put("GR", "本人使用");
		return result;
	}
	
	/**
	 * 获取房主状态
	 * @return
	 */
	public static Map<String,Object> getQyzt(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
	
		result.put("", "全部");
		result.put("1", "正常");
		result.put("4", "注销");
		return result;
	}

	
	/**
	 * 获取纳入汇缴
	 * @return
	 */
	public static Map<String,Object> getNrhj(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
	
		result.put("", "全部");
		result.put("00", "未纳入");
		result.put("01", "已纳入");
//		result.put("02", "已取消");
		return result;
	}

	
	/**
	 * 获取纳入汇缴
	 * @return
	 */
	public static Map<String,Object> getNrhjDC(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
	
		result.put("", "全部");
		result.put("00", "未纳入");
		result.put("01", "已纳入");
		result.put("02", "已取消");
		return result;
	}

	
	/**
	 * 获取征收方式
	 * @return
	 */
	public static Map<String,Object> getZsfs(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
	
		result.put("", "全部");
		result.put("1", "查帐征收");
		result.put("0", "核定征收");
		return result;
	}

	
	/**
	 * 获取税务机关层级
	 * @return
	 */
	public static Map<String,Object> getSwjgcjs(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("01", "01");
		result.put("03", "03");
		result.put("05", "05");
		result.put("07", "07");
		result.put("09", "09");
		return result;
	}
	
	
	public static String ZF = "N";//通过
	public static String ZC = "Y";//退回
	
	public static Map<String,Object> getDjzt(){
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("N", "作废");
		result.put("Y", "正常");
		return result;
	}

	//流程发布状态
	public static String WEISHEJI = "1";//未设计
	public static String YISHEJI = "2";//已设计
	public static String YIFABU = "3";//已发布
	
	public static Map<String,Object> getFbzts(){
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("1", "未设计");
		result.put("2", "已设计");
		result.put("3", "已发布");
		return result;
	}
	
	//合同状态
	public static String XINJIAN = "X";//新建
	public static String ZUOFEI = "Z";//作废
	public static String SHENPIZHOGN = "S";//审批中
	public static String SHENPITONGGUO = "T";//审批通过
	public static String SHENPIWEITONGGUO = "D";//审批未通过
	public static String SHENPITUIHUI = "N";//审批退回
	public static String BUFENZHIXING = "B";//部分执行
	public static String ZHIXINGWANBI = "W";//执行完毕
	
	//比对结果
	public static Map<String,Object> getBdjgs(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("", "全部");
		result.put("1", "一致");
		result.put("0", "不一致");
		return result;
	}
	//适用会计准则：01,房主、小房主会计制度/02,新会计准则
	public static Map<String,Object> getKjzzs(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("", "全部");
		result.put("01", "小会计准则");
		result.put("02", "会计准则");
		result.put("03", "商业银行");
		result.put("04", "保险公司");
		result.put("05", "证券公司");
		result.put("06", "事业单位会计制度");
		result.put("07", "非营利组织会计制度");
		result.put("08", "房主会计制度");
		return result;
	}
	
	//汇总纳税
	public static Map<String,Object> getHznss(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("", "不汇总");
		result.put("0", "不汇总");
		result.put("1", "汇总");
		return result;
	}

	
	//申报类型 一般工商房主（01）、金融类房主（02）、事业单位、社会团体、民办非房主单位（03）
	public static Map<String,Object> getSblxs(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("", "全部");
		result.put("01", "一般工商房主");
		result.put("02", "金融类房主");
		result.put("03", "事业单位、社会团体、民办非房主单位");
		result.put("06", "二级分支机构");
		return result;
	}
	//申报类型 一般工商房主（01）、金融类房主（02）、事业单位（03）、非营利组织（04）
	public static Map<String,Object> getQSblxs(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("", "全部");
		result.put("01", "一般房主");
		result.put("02", "金融类房主");
		result.put("03", "事业单位、社会团体、民办非房主单位");
		result.put("06", "二级分支机构");
		result.put("05", "定率征收");
		return result;
	}
	
	//
	public static String getDictKeyMC(String key,Map<String,Object> DictMap){
		if(DictMap!=null && DictMap.size()>0){
			try{
				return DictMap.get(key).toString();
			}catch(Exception e){
				return "";
			}
		}
		return "";
	}
	
	//-----------------------

	/** 获取启用、禁用状态 */
	public static Map<String,Object> getQJZts(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("", "全部");
		result.put("Y", "启用");
		result.put("N", "禁用");
		return result;
	}
	/**房主性质1国有房主、2集体房主、有限责任公司、3股份有限公司、4私营房主、5中外合资房主、6外商投资房主、0其他*/
	public static Map<String,Object> getQyxz(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("1", "国有房主");
		result.put("2", "集体房主");
		result.put("3", "有限责任公司");
		result.put("4", "股份有限公司");
		result.put("5", "私营房主");
		result.put("6", "中外合资房主");
		result.put("7", "外商投资房主");
		result.put("0", "其他");
		return result;
	}
	/**是否涉外**/
	public static Map<String,Object> sfsw(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("Y", "是");
		result.put("N", "否");
		return result;
	}
	/**房主状态**/
	public static Map<String,Object> qyzt(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("1", "开业");
		result.put("2", "停业");
		result.put("3", "注销");
		return result;
	}
	/**注销标志**/
	public static Map<String,Object> zxbz(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("Y", "已经注销");
		result.put("N", "没有注销");
		return result;
	}
	/**房源出租方式**/
	public static Map<String,Object> czfs(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("1", "整租");
		result.put("2", "合租");
		return result;
	}
	/** 获取房源状态 */
	public static Map<String,Object> fyzt(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("", "全部");
		result.put("1", "正常");
		result.put("2", "停租");
		return result;
	}
	/** 房主注册信息审批状态 */
	public static Map<String,Object> spzt(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("", "全部");
		result.put("0", "未审批");
		result.put("1", "审批通过");
		result.put("2", "审批未通过");
		return result;

	}
	/** 终端设备状态 */
	public static Map<String,Object> zdsbzt(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("1", "正常");
		result.put("2", "停用");
		result.put("3", "异常");
		return result;
	}		

	
	/** 房主等级 */
	public static Map<String,Object> qydj(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("", "请选择");
		result.put("1", "特级");
		result.put("2", "一级");
		result.put("3", "二级");
		result.put("4", "三级");
		result.put("5", "地方级");
		result.put("6", "对外有偿服务的军队宾馆招待所");
		result.put("7", "租用部队房产开办的宾馆招待所");
		result.put("9", "其它");
		return result;
	}
	
	/** 房主星级*/
	public static Map<String,Object> qyxj(){
		Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("", "请选择");
		result.put("1", "一星级");
		result.put("2", "二星级");
		result.put("3", "三星级");
		result.put("4", "四星级");
		result.put("5", "五星级");
		result.put("9", "其他");
		return result;
	}

	
	
}
