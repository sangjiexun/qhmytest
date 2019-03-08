package com.fh.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fh.excel.util.ExcelConst;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

/**
 * 
 * <p>
 * 标题:ItemListReadExcel
 * </p>
 * <p>
 * 描述:导入缴费名单 excel解析类
 * </p>
 * <p>
 * 组织:河北科曼信息技术有限公司
 * </p>
 * 
 * @author Administrator 鲁震
 * @date 2017年8月16日 下午10:27:41
 */
public class ItemListParseExcel extends AbsParseExcel {

	/**
	 * 
	 * <p>
	 * 描述:解析excel，封装成List并返回
	 * </p>
	 * 
	 * @author Administrator 鲁震
	 * @date 2017年8月16日 下午11:32:52
	 * @param file
	 * @param string
	 * @return
	 */
	public List<PageData> getList(File file, String fileName) throws Exception {
		this.setFileName(fileName);
		ExcelBase excelBase = this.openExcel(file);// 打开excel
		List<PageData> rstDatas = excelBase.getList();
		this.closeExcel();// 关闭excel
		return rstDatas;
	}
	/**
	 * 
	 * <p>
	 * 描述:解析合作学校excel，封装成List并返回
	 * </p>
	 * 
	 * @author Administrator wzz
	 * @date 2018年11月27日 下午11:32:52
	 * @param file
	 * @param string
	 * @return
	 */
	public List<PageData> getPartSchoolList(File file, String fileName) throws Exception {
		this.setFileName(fileName);
		ExcelBase excelBase = this.openExcel(file);// 打开excel
		List<PageData> rstDatas = excelBase.getPartSchoolList();
		this.closeExcel();// 关闭excel
		return rstDatas;
	}
	
	/**
	 * 
	 * <p>描述:解析学生信息名单excel，封装成List并返回</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月18日 下午7:58:53
	 * @param file
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getPaidList(File file, String fileName) throws Exception {
		this.setFileName(fileName);
		ExcelBase excelBase = this.openExcel(file);// 打开excel
		List<PageData> rstDatas = excelBase.getPaidList();
		this.closeExcel();// 关闭excel
		return rstDatas;
	}
	/**
	 * 
	 * <p>描述:解析减免excel，封装List并返回</p>
	 * @author ning 王宁
	 * @date 2018年7月12日 上午9:26:03
	 * @param file
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getJianMianList(File file, String fileName) throws Exception {
		this.setFileName(fileName);
		ExcelBase excelBase = this.openExcel(file);// 打开excel
		List<PageData> rstDatas = excelBase.getJianMianList();
		this.closeExcel();// 关闭excel
		return rstDatas;
	}
	public List<PageData> getStuInfoList(File file, String fileName) throws Exception {
		this.setFileName(fileName);
		ExcelBase excelBase = this.openExcel(file);// 打开excel
		List<PageData> rstDatas = excelBase.getStuInfoList();
		this.closeExcel();// 关闭excel
		return rstDatas;
	}
	public List<PageData> getStuLoanList(File file, String fileName) throws Exception {
		this.setFileName(fileName);
		ExcelBase excelBase = this.openExcel(file);// 打开excel
		List<PageData> rstDatas = excelBase.getStuLoanList();
		this.closeExcel();// 关闭excel
		return rstDatas;
	}
	public List<PageData> getStuInfoCJList(File file, String fileName) throws Exception {
		this.setFileName(fileName);
		ExcelBase excelBase = this.openExcel(file);// 打开excel
		List<PageData> rstDatas = excelBase.getStuInfoCJList();
		this.closeExcel();// 关闭excel
		return rstDatas;
	}
	
	public List<PageData> getPayRecordList(File file, String fileName) throws Exception {
		this.setFileName(fileName);
		ExcelBase excelBase = this.openExcel(file);// 打开excel
		List<PageData> rstDatas = excelBase.getPayRecordList();
		this.closeExcel();// 关闭excel
		return rstDatas;
	}
	
	public List<PageData> getjffbList(File file, String fileName) throws Exception {
		this.setFileName(fileName);
		ExcelBase excelBase = this.openExcel(file);// 打开excel
		List<PageData> rstDatas = excelBase.getjffbList();
		this.closeExcel();// 关闭excel
		return rstDatas;
	}
	
	public List<PageData> getTeacherInfoList(File file, String fileName) throws Exception {
		this.setFileName(fileName);
		ExcelBase excelBase = this.openExcel(file);// 打开excel
		List<PageData> rstDatas = excelBase.getTeacherInfoList();
		this.closeExcel();// 关闭excel
		return rstDatas;
	}
	public List<PageData> getClassInfoList(File file, String fileName) throws Exception {
		this.setFileName(fileName);
		ExcelBase excelBase = this.openExcel(file);// 打开excel
		List<PageData> rstDatas = excelBase.getClassInfoList();
		this.closeExcel();// 关闭excel
		return rstDatas;
	}
	public List<PageData> getDeptInfoList(File file, String fileName) throws Exception {
		this.setFileName(fileName);
		ExcelBase excelBase = this.openExcel(file);// 打开excel
		List<PageData> rstDatas = excelBase.getDeptList();
		this.closeExcel();// 关闭excel
		return rstDatas;
	}
	public List<PageData> getCJDeptInfoList(File file, String fileName) throws Exception {
		this.setFileName(fileName);
		ExcelBase excelBase = this.openExcel(file);// 打开excel
		List<PageData> rstDatas = excelBase.getCJDeptList();
		this.closeExcel();// 关闭excel
		return rstDatas;
	}
	public List<PageData> getNationInfoList(File file, String fileName) throws Exception {
		this.setFileName(fileName);
		ExcelBase excelBase = this.openExcel(file);// 打开excel
		List<PageData> rstDatas = excelBase.getNationList();
		this.closeExcel();// 关闭excel
		return rstDatas;
	}
	
	public List<PageData> getStuSourceInfoList(File file, String fileName) throws Exception {
		this.setFileName(fileName);
		ExcelBase excelBase = this.openExcel(file);// 打开excel
		List<PageData> rstDatas = excelBase.getStuSourceList();
		this.closeExcel();// 关闭excel
		return rstDatas;
	}
	/**
	 * 
	 * <p>
	 * 描述:生成excel文档
	 * </p>
	 * @author Administrator 鲁震
	 * @date 2017年8月17日 上午4:23:55
	 * @param errorList
	 * @return 返回一个下载绝对路径
	 */
	public String writeExcel(List<PageData> errorList) {

		String mulu = UuidUtil.get32UUID();// 目录

		// 放到 uploadFiles\itemlist 目录下
		ClassLoader loader = this.getClass().getClassLoader();
		URL url = loader.getResource("");
		String classBasePath = url.getPath();
		String path = classBasePath.replaceAll("WEB-INF/classes/", "");
		// E:\\luzhen\\usr\\apache-tomcat-7.0.78\\webapps\\colleges-admin
		path = path + "\\uploadFiles\\itemlist\\write\\" + mulu + "\\";

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}

		OutputStream out = null;

		try {
			Workbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet("导入失败记录");
			// Row row = sheet.getRow(i);
			// sheet.removeRow(row);
			out = new FileOutputStream(path + "error.xls");
			Row rowHead= sheet.createRow(0);//表头行
//			rowHead.createCell(0).setCellValue("缴费项目名称");//
			rowHead.createCell(0).setCellValue("学生姓名");//
			rowHead.createCell(1).setCellValue("学号");//
			rowHead.createCell(2).setCellValue("身份证号");//
			rowHead.createCell(3).setCellValue("入学年份");//
			rowHead.createCell(4).setCellValue("班型");//
			rowHead.createCell(5).setCellValue("费用");//
			rowHead.createCell(6).setCellValue("失败原因");//
			
			Row tempRow = null;
			PageData pageData = null;
			
			for (int j = 0; j < errorList.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				tempRow = sheet.createRow(j + 1);
				// 得到要插入的每一条记录
				pageData = errorList.get(j);
//				tempRow.createCell(0).setCellValue(pageData.getString("itemName"));//
				tempRow.createCell(0).setCellValue(pageData.getString("stuName"));//
				tempRow.createCell(1).setCellValue(pageData.getString("stuNumber"));//
				tempRow.createCell(2).setCellValue(pageData.getString("cardNo"));//
				tempRow.createCell(3).setCellValue(pageData.getString("grade"));//
				tempRow.createCell(4).setCellValue(pageData.getString("classType"));//
				tempRow.createCell(5).setCellValue(pageData.getString("cost"));//
				tempRow.createCell(6).setCellValue(pageData.getString("error"));//
			}
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		return path + "error.xls";
		return "write\\" + mulu + "\\error.xls";
	}
	/**
	 * 
	 * <p>
	 * 描述:生成excel文档
	 * </p>
	 * @author Administrator wzz
	 * @date 2018年11月27日 上午4:23:55
	 * @param errorList
	 * @return 返回一个下载绝对路径
	 */
	public String writePartSchoolExcel(List<PageData> errorList) {
		
		String mulu = UuidUtil.get32UUID();// 目录
		
		// 放到 uploadFiles\itemlist 目录下
		ClassLoader loader = this.getClass().getClassLoader();
		URL url = loader.getResource("");
		String classBasePath = url.getPath();
		String path = classBasePath.replaceAll("WEB-INF/classes/", "");
		// E:\\luzhen\\usr\\apache-tomcat-7.0.78\\webapps\\colleges-admin
		path = path + "\\uploadFiles\\partschool\\write\\" + mulu + "\\";
		
		File file = new File(path);
		
		if (!file.exists()) {
			file.mkdirs();
		}
		
		OutputStream out = null;
		
		try {
			Workbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet("导入失败记录");
			out = new FileOutputStream(path + "error.xls");
			Row rowHead= sheet.createRow(0);//表头行
			rowHead.createCell(0).setCellValue("合作学校名称");//
			rowHead.createCell(1).setCellValue("定金");//
			rowHead.createCell(2).setCellValue("失败原因");//
			
			Row tempRow = null;
			PageData pageData = null;
			
			for (int j = 0; j < errorList.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				tempRow = sheet.createRow(j + 1);
				// 得到要插入的每一条记录
				pageData = errorList.get(j);
				tempRow.createCell(0).setCellValue(pageData.getString("SCHOOLNAME"));//
				tempRow.createCell(1).setCellValue(pageData.getString("DINGJIN"));//
				tempRow.createCell(2).setCellValue(pageData.getString("error"));//
			}
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		return path + "error.xls";
		return "write\\" + mulu + "\\error.xls";
	}

	/**
	 * 
	 * <p>
	 * 描述:读取一个excel并返回工作簿
	 * </p>
	 * 
	 * @author Administrator 鲁震
	 * @date 2017年8月17日 上午4:37:48
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static Workbook getWorkbok(File file) throws IOException {
		Workbook wb = null;
		FileInputStream in = new FileInputStream(file);
		if (file.getName().endsWith(ExcelConst.OFFICE_EXCEL_2003_POSTFIX)) { // Excel
																				// 2003
			wb = new HSSFWorkbook(in);
		} else if (file.getName()
				.endsWith(ExcelConst.OFFICE_EXCEL_2010_POSTFIX)) { // Excel
																	// 2007/2010
			wb = new XSSFWorkbook(in);
		}
		return wb;
	}
	
	/**
	 * 
	 * <p>描述:写出错误已缴费名单错误记录</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月19日 下午2:39:12
	 * @param errorList
	 * @param baseurl
	 * @return
	 */
	public String writeExcel(List<PageData> errorList,String baseurl) {

		String mulu = UuidUtil.get32UUID();// 目录

		// 放到 uploadFiles\itemlist 目录下
		ClassLoader loader = this.getClass().getClassLoader();
		URL url = loader.getResource("");
		String classBasePath = url.getPath();
		String path = classBasePath.replaceAll("WEB-INF/classes/", "");
		// E:\\luzhen\\usr\\apache-tomcat-7.0.78\\webapps\\colleges-admin
		path = path + "\\uploadFiles\\itempaidlist\\write\\" + mulu + "\\";

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}

		OutputStream out = null;

		try {
			Workbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet("导入失败记录");
			// Row row = sheet.getRow(i);
			// sheet.removeRow(row);
			out = new FileOutputStream(path + "error.xls");
			Row rowHead= sheet.createRow(0);//表头行
//			rowHead.createCell(0).setCellValue("缴费项目名称");//
			rowHead.createCell(0).setCellValue("学生姓名");//
			rowHead.createCell(1).setCellValue("学号");//
			rowHead.createCell(2).setCellValue("身份证号");//
			rowHead.createCell(3).setCellValue("缴费总额");//
			rowHead.createCell(4).setCellValue("现金");//
			rowHead.createCell(5).setCellValue("银行卡号");//
			rowHead.createCell(6).setCellValue("银行卡金额");//
			rowHead.createCell(7).setCellValue("微信");//
			rowHead.createCell(8).setCellValue("支付宝");//
			rowHead.createCell(9).setCellValue("电汇");//
			rowHead.createCell(10).setCellValue("失败原因");//
			
			Row tempRow = null;
			PageData pageData = null;
			
			for (int j = 0; j < errorList.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				tempRow = sheet.createRow(j + 1);
				// 得到要插入的每一条记录
				pageData = errorList.get(j);
//				tempRow.createCell(0).setCellValue(pageData.getString("itemName"));//
				tempRow.createCell(0).setCellValue(pageData.getString("stuName"));//
				tempRow.createCell(1).setCellValue(pageData.getString("stuNumber"));//
				tempRow.createCell(2).setCellValue(pageData.getString("cardNo"));//
				tempRow.createCell(3).setCellValue(pageData.getString("TOTALMONEY"));//
				tempRow.createCell(4).setCellValue(pageData.getString("CASH"));//
				tempRow.createCell(5).setCellValue(pageData.getString("BANKCARDNO"));//
				tempRow.createCell(6).setCellValue(pageData.getString("CARD"));//
				tempRow.createCell(7).setCellValue(pageData.getString("WX"));//
				tempRow.createCell(8).setCellValue(pageData.getString("ZFB"));//
				tempRow.createCell(9).setCellValue(pageData.getString("TT"));//
				tempRow.createCell(10).setCellValue(pageData.getString("error"));//
				
			}
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		return path + "error.xls";
		return "write\\" + mulu + "\\error.xls";
	}
	/**
	 * 写出学生信息错误记录
	 * <p>描述:</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月19日 下午2:39:35
	 * @param errorList
	 * @param baseurl
	 * @param stu
	 * @return
	 */
	public String writeExcel(List<PageData> errorList,String baseurl,String stu) {

		String mulu = UuidUtil.get32UUID();// 目录

		// 放到 uploadFiles\itemlist 目录下
		ClassLoader loader = this.getClass().getClassLoader();
		URL url = loader.getResource("");
		String classBasePath = url.getPath();
		String path = classBasePath.replaceAll("WEB-INF/classes/", "");
		// E:\\luzhen\\usr\\apache-tomcat-7.0.78\\webapps\\colleges-admin
		path = path + "\\uploadFiles\\stuinfo\\write\\" + mulu + "\\";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		OutputStream out = null;
		try {
			Workbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet("导入失败记录");
			out = new FileOutputStream(path + "error.xls");
			Row rowHead= sheet.createRow(0);//表头行
			rowHead.createCell(0).setCellValue("身份证号");//
			rowHead.createCell(1).setCellValue("姓名");//
			/*rowHead.createCell(2).setCellValue("原班级");//
*/			rowHead.createCell(2).setCellValue("手机");//
			rowHead.createCell(3).setCellValue("学号");//
			rowHead.createCell(4).setCellValue("入学年份");//
			rowHead.createCell(5).setCellValue("班型");//
			rowHead.createCell(6).setCellValue("学生类型");//
			rowHead.createCell(7).setCellValue("文化课学校");//
			rowHead.createCell(8).setCellValue("预交年份");//
			rowHead.createCell(9).setCellValue("预交班型");//
			rowHead.createCell(10).setCellValue("家庭地址");//
			rowHead.createCell(11).setCellValue("邮政编码");//
			rowHead.createCell(12).setCellValue("第一监护人");//
			rowHead.createCell(13).setCellValue("家庭关系");//
			rowHead.createCell(14).setCellValue("联系电话");//
			rowHead.createCell(15).setCellValue("第二监护人");//
			rowHead.createCell(16).setCellValue("家庭关系");//
			rowHead.createCell(17).setCellValue("联系电话");//
			rowHead.createCell(18).setCellValue("病史");//
			rowHead.createCell(19).setCellValue("了解强化的途径");//
			rowHead.createCell(20).setCellValue("推荐人");//
			rowHead.createCell(21).setCellValue("高一入学时间");//
			rowHead.createCell(22).setCellValue("高中班级");//
			rowHead.createCell(23).setCellValue("文理科");//
			rowHead.createCell(24).setCellValue("学校班主任");//
			rowHead.createCell(25).setCellValue("班主任电话");//
			rowHead.createCell(26).setCellValue("自何时学习美术");//
			rowHead.createCell(27).setCellValue("已学过科目");//
			rowHead.createCell(28).setCellValue("联考分数");//
			rowHead.createCell(29).setCellValue("校考成绩");//
			rowHead.createCell(30).setCellValue("考生号");//
			rowHead.createCell(31).setCellValue("备注1");//
			rowHead.createCell(32).setCellValue("备注2");//
			rowHead.createCell(33).setCellValue("备注3");//
			rowHead.createCell(34).setCellValue("错误信息");//
			Row tempRow = null;
			PageData pageData = null;
			
			for (int j = 0; j < errorList.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				tempRow = sheet.createRow(j + 1);
				// 得到要插入的每一条记录
				pageData = errorList.get(j);
				tempRow.createCell(0).setCellValue(pageData.getString("SHENFENZHENGHAO"));//
				tempRow.createCell(1).setCellValue(pageData.getString("XINGMING"));//
				/*tempRow.createCell(2).setCellValue(pageData.getString("OLD_BANJI"));//
*/				tempRow.createCell(2).setCellValue(pageData.getString("SHOUJI"));//
				tempRow.createCell(3).setCellValue(pageData.getString("XUEHAO"));//
				tempRow.createCell(4).setCellValue(pageData.getString("RXNIANFEN"));//
				tempRow.createCell(5).setCellValue(pageData.getString("BANJI_TYPE"));//
				tempRow.createCell(6).setCellValue(pageData.getString("CENGCI"));//
				String WHKXUEXIAONAME = pageData.getString("WHKXUEXIAONAME");
				if(WHKXUEXIAONAME==null||"null".equals(WHKXUEXIAONAME)){
					WHKXUEXIAONAME="";
				}
				tempRow.createCell(7).setCellValue(WHKXUEXIAONAME);//
				tempRow.createCell(8).setCellValue(pageData.getString("YJ_NIANFEN"));//				
				tempRow.createCell(9).setCellValue(pageData.getString("YJ_BANJI_TYPE"));//
				tempRow.createCell(10).setCellValue(pageData.getString("JIATINGZHUZHI"));//
				tempRow.createCell(11).setCellValue(pageData.getString("YOUZHENGBIANMA"));//
				tempRow.createCell(12).setCellValue(pageData.getString("ONE_JHR"));//
				tempRow.createCell(13).setCellValue(pageData.getString("ONE_JHRGX"));//
				tempRow.createCell(14).setCellValue(pageData.getString("ONE_JHRDH"));//
				tempRow.createCell(15).setCellValue(pageData.getString("TWO_JHR"));//
				tempRow.createCell(16).setCellValue(pageData.getString("TWO_JHRGX"));//
				tempRow.createCell(17).setCellValue(pageData.getString("TWO_JHRDH"));//
				tempRow.createCell(18).setCellValue(pageData.getString("BINGSHI"));//
				tempRow.createCell(19).setCellValue(pageData.getString("LJQHTJ"));//
				tempRow.createCell(20).setCellValue(pageData.getString("TUIJIANREN"));//
				tempRow.createCell(21).setCellValue(pageData.getString("GYRXSJ"));//
				tempRow.createCell(22).setCellValue(pageData.getString("GZ_BANJI"));//
				tempRow.createCell(23).setCellValue(pageData.getString("WENLIKE"));//
				tempRow.createCell(24).setCellValue(pageData.getString("BZRSCHOOL"));//
				tempRow.createCell(25).setCellValue(pageData.getString("BZRPHONE"));//
				tempRow.createCell(26).setCellValue(pageData.getString("STARTMEISHU"));//
				String KEMUNAME = pageData.getString("KEMUNAME");
				if(KEMUNAME==null||"null".equals(KEMUNAME)){
					KEMUNAME="";
				}
				tempRow.createCell(27).setCellValue(KEMUNAME);//
				String LKFSNAME = pageData.getString("LKFSNAME");
				if(LKFSNAME==null||"null".equals(LKFSNAME)){
					LKFSNAME="";
				}
				tempRow.createCell(28).setCellValue(pageData.getString("LKFSNAME"));//
				String XK_MARK_NAME = pageData.getString("XK_MARK_NAME");
				if(XK_MARK_NAME==null||"null".equals(XK_MARK_NAME)){
					XK_MARK_NAME="";
				}
				tempRow.createCell(29).setCellValue(XK_MARK_NAME);//
				String KSNUMBERNAME = pageData.getString("KSNUMBERNAME");
				if(KSNUMBERNAME==null||"null".equals(KSNUMBERNAME)){
					KSNUMBERNAME="";
				}
				tempRow.createCell(30).setCellValue(KSNUMBERNAME);//
				tempRow.createCell(31).setCellValue(pageData.getString("REMARKS1"));//
				tempRow.createCell(32).setCellValue(pageData.getString("REMARKS2"));//
				tempRow.createCell(33).setCellValue(pageData.getString("REMARKS3"));//
				tempRow.createCell(34).setCellValue(pageData.getString("error"));//
			}
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "write\\" + mulu + "\\error.xls";
	}
	/**
	 * 写出财经学生信息错误记录
	 * <p>描述:</p>
	 * @author Administrator wzz
	 * @date 2018年7月2日 下午2:39:35
	 * @param errorList
	 * @param baseurl
	 * @param stu
	 * @return
	 */
	public String writeCJExcel(List<PageData> errorList,String baseurl,String stu) {
		
		String mulu = UuidUtil.get32UUID();// 目录
		
		// 放到 uploadFiles\itemlist 目录下
		ClassLoader loader = this.getClass().getClassLoader();
		URL url = loader.getResource("");
		String classBasePath = url.getPath();
		String path = classBasePath.replaceAll("WEB-INF/classes/", "");
		path = path + "\\uploadFiles\\stuinfocj\\write\\" + mulu + "\\";
		
		File file = new File(path);
		
		if (!file.exists()) {
			file.mkdirs();
		}
		
		OutputStream out = null;
		
		try {
			Workbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet("导入失败记录");
			out = new FileOutputStream(path + "error.xls");
			Row rowHead= sheet.createRow(0);//表头行
			rowHead.createCell(0).setCellValue("身份证号码");//
			rowHead.createCell(1).setCellValue("学号");//
			rowHead.createCell(2).setCellValue("姓名");//
			rowHead.createCell(3).setCellValue("政治面貌");//
			rowHead.createCell(4).setCellValue("联系方式");//
			rowHead.createCell(5).setCellValue("考生号");//
			rowHead.createCell(6).setCellValue("在学状态");//
			rowHead.createCell(7).setCellValue("院校专业");//
			rowHead.createCell(8).setCellValue("入学年份");//
			rowHead.createCell(9).setCellValue("班级");//
			rowHead.createCell(10).setCellValue("学生类型");//
			rowHead.createCell(11).setCellValue("批次");//
			rowHead.createCell(12).setCellValue("学籍类型");//
			rowHead.createCell(13).setCellValue("升学标识");//
			rowHead.createCell(14).setCellValue("科类");//
			rowHead.createCell(15).setCellValue("录取专业");//
			rowHead.createCell(16).setCellValue("民族");//
			rowHead.createCell(17).setCellValue("邮政编码");//
			rowHead.createCell(18).setCellValue("家庭地址");//
			rowHead.createCell(19).setCellValue("家庭成员关系");//
			rowHead.createCell(20).setCellValue("家庭成员姓名");//
			rowHead.createCell(21).setCellValue("家庭成员联系电话");//		
			rowHead.createCell(22).setCellValue("户口性质");//
			rowHead.createCell(23).setCellValue("健康状况");//
			rowHead.createCell(24).setCellValue("考生特长");//
			rowHead.createCell(25).setCellValue("是否贫困");//
			rowHead.createCell(26).setCellValue("中学名称");//
			rowHead.createCell(27).setCellValue("投档成绩");//
			rowHead.createCell(28).setCellValue("学制");//
			rowHead.createCell(29).setCellValue("计划性质");//
			rowHead.createCell(30).setCellValue("学生类型");//
			rowHead.createCell(31).setCellValue("注册学籍时间");//
			rowHead.createCell(32).setCellValue("备注1");//
			rowHead.createCell(33).setCellValue("备注2");//
			rowHead.createCell(34).setCellValue("备注3");//
			rowHead.createCell(35).setCellValue("备注4");//
			rowHead.createCell(36).setCellValue("备注5");//
			rowHead.createCell(37).setCellValue("备注6");//
			rowHead.createCell(38).setCellValue("备注7");//
			rowHead.createCell(39).setCellValue("备注8");//
			rowHead.createCell(40).setCellValue("特殊标记");//
			rowHead.createCell(41).setCellValue("失败原因");//
			Row tempRow = null;
			PageData pageData = null;
			
			for (int j = 0; j < errorList.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				tempRow = sheet.createRow(j + 1);
				// 得到要插入的每一条记录
				pageData = errorList.get(j);
				tempRow.createCell(0).setCellValue(pageData.getString("SHENFENZHENGHAO"));//身份证号
				tempRow.createCell(1).setCellValue(pageData.getString("XUEHAO"));//学号
				tempRow.createCell(2).setCellValue(pageData.getString("XINGMING"));//姓名
				tempRow.createCell(3).setCellValue(pageData.getString("ZHENGZHIMIANMAO"));//政治面貌
				tempRow.createCell(4).setCellValue(pageData.getString("SHOUJI"));//手机号
				tempRow.createCell(5).setCellValue(pageData.getString("KAOSHENGHAO"));//考生号
				tempRow.createCell(6).setCellValue(pageData.getString("ZAIXUEZT"));//在学状态
				tempRow.createCell(7).setCellValue(pageData.getString("DEPARTMEN_ID"));//院校专业
				tempRow.createCell(8).setCellValue(pageData.getString("NIANJI"));//入学年份
				tempRow.createCell(9).setCellValue(pageData.getString("BANJI"));//班级
				tempRow.createCell(10).setCellValue(pageData.getString("CENGCI"));//层次
				tempRow.createCell(11).setCellValue(pageData.getString("PICI"));//批次
				tempRow.createCell(12).setCellValue(pageData.getString("XUEJI"));//学籍类型
				tempRow.createCell(13).setCellValue(pageData.getString("SHENGXUEBIAOSHI"));//升学标识
				tempRow.createCell(14).setCellValue(pageData.getString("KELEI"));//科类
				tempRow.createCell(15).setCellValue(pageData.getString("LUQUZHUANYE"));//录取专业
				tempRow.createCell(16).setCellValue(pageData.getString("MINZU"));//民族
				tempRow.createCell(17).setCellValue(pageData.getString("YOUZHENGBIANMA"));//邮政编码
				tempRow.createCell(18).setCellValue(pageData.getString("JIATINGZHUZHI"));//家庭地址
				tempRow.createCell(19).setCellValue(pageData.getString("JTCYGX"));//家庭成员关系
				tempRow.createCell(20).setCellValue(pageData.getString("JTCYXM"));//家庭成员姓名
				tempRow.createCell(21).setCellValue(pageData.getString("JTCYLXDH"));//家庭成员联系电话
				tempRow.createCell(22).setCellValue(pageData.getString("HUKOUXINGZHI"));//户口性质
				tempRow.createCell(23).setCellValue(pageData.getString("JKZK"));//健康状况
				tempRow.createCell(24).setCellValue(pageData.getString("KAOSHENGTECHANG"));//考生特长
				tempRow.createCell(25).setCellValue(pageData.getString("SHIFOUPINKUN"));//是否贫困
				tempRow.createCell(26).setCellValue(pageData.getString("ZXMC"));//中学名称
				tempRow.createCell(27).setCellValue(pageData.getString("TOUDANGCHENGJI"));//投档成绩
				tempRow.createCell(28).setCellValue(pageData.getString("XUEZHI"));//学制
				tempRow.createCell(29).setCellValue(pageData.getString("JIHUAXINGZHI"));//所在学院
				tempRow.createCell(30).setCellValue(pageData.getString("XSLX"));//学生类型
				tempRow.createCell(31).setCellValue(pageData.getString("ZCXJSJ"));//注册学籍时间
				tempRow.createCell(32).setCellValue(pageData.getString("REMARKS1"));//备注
				tempRow.createCell(33).setCellValue(pageData.getString("REMARKS2"));//备注
				tempRow.createCell(34).setCellValue(pageData.getString("REMARKS3"));//备注
				tempRow.createCell(35).setCellValue(pageData.getString("REMARKS4"));//备注
				tempRow.createCell(36).setCellValue(pageData.getString("REMARKS5"));//备注
				tempRow.createCell(37).setCellValue(pageData.getString("REMARKS6"));//备注
				tempRow.createCell(38).setCellValue(pageData.getString("REMARKS7"));//备注
				tempRow.createCell(39).setCellValue(pageData.getString("REMARKS8"));//备注
				tempRow.createCell(40).setCellValue(pageData.getString("TESHUBIAOJI"));//特殊标记
				tempRow.createCell(41).setCellValue(pageData.getString("error"));//
			}
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "write\\" + mulu + "\\error.xls";
	}
	
	
	public String writeExcelRecord(List<PageData> errorList) {

		String mulu = UuidUtil.get32UUID();// 目录

		// 放到 uploadFiles\itemlist 目录下
		ClassLoader loader = this.getClass().getClassLoader();
		URL url = loader.getResource("");
		String classBasePath = url.getPath();
		String path = classBasePath.replaceAll("WEB-INF/classes/", "");
		// E:\\luzhen\\usr\\apache-tomcat-7.0.78\\webapps\\colleges-admin
		path = path + "\\uploadFiles\\payRecord\\write\\" + mulu + "\\";

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}

		OutputStream out = null;

		try {
			Workbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet("导入失败记录");
			// Row row = sheet.getRow(i);
			// sheet.removeRow(row);
			out = new FileOutputStream(path + "error.xls");
			Row rowHead= sheet.createRow(0);//表头行
			rowHead.createCell(0).setCellValue("身份证号");
			rowHead.createCell(1).setCellValue("费用类型");
			rowHead.createCell(2).setCellValue("费用金额");
			rowHead.createCell(3).setCellValue("失败原因");//
			
			Row tempRow = null;
			PageData pageData = null;
			
			for (int j = 0; j < errorList.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				tempRow = sheet.createRow(j + 1);
				// 得到要插入的每一条记录
				pageData = errorList.get(j);
				tempRow.createCell(0).setCellValue(pageData.getString("SHENFENZHENGHAO"));//
				tempRow.createCell(1).setCellValue(pageData.getString("PAY_MODE"));//
				tempRow.createCell(2).setCellValue(pageData.getString("PAY_MONEY"));//
				tempRow.createCell(3).setCellValue(pageData.getString("error"));
			}
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		return path + "error.xls";
		return "write\\" + mulu + "\\error.xls";
	}

	/**
	 * 
	 * <p>描述:</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月26日 上午9:20:57
	 * @param errorList
	 * @return
	 */
	public String writeExcelfb(List<PageData> errorList) {

		String mulu = UuidUtil.get32UUID();// 目录

		// 放到 uploadFiles\itemlist 目录下
		ClassLoader loader = this.getClass().getClassLoader();
		URL url = loader.getResource("");
		String classBasePath = url.getPath();
		String path = classBasePath.replaceAll("WEB-INF/classes/", "");
		// E:\\luzhen\\usr\\apache-tomcat-7.0.78\\webapps\\colleges-admin
		path = path + "\\uploadFiles\\itemlist\\write\\" + mulu + "\\";

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}

		OutputStream out = null;

		try {
			Workbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet("导入失败记录");
			// Row row = sheet.getRow(i);
			// sheet.removeRow(row);
			out = new FileOutputStream(path + "error.xls");
			Row rowHead= sheet.createRow(0);//表头行
			//rowHead.createCell(0).setCellValue("缴费项目名称");//
			rowHead.createCell(0).setCellValue("学生名称");//
			rowHead.createCell(1).setCellValue("学号");//
			rowHead.createCell(2).setCellValue("身份证号");//
			rowHead.createCell(3).setCellValue("费用");//
			rowHead.createCell(4).setCellValue("失败原因");//
			
			Row tempRow = null;
			PageData pageData = null;
			
			for (int j = 0; j < errorList.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				tempRow = sheet.createRow(j + 1);
				// 得到要插入的每一条记录
				pageData = errorList.get(j);
				//tempRow.createCell(0).setCellValue(pageData.getString("itemName"));//
				tempRow.createCell(0).setCellValue(pageData.getString("stuName"));//
				tempRow.createCell(1).setCellValue(pageData.getString("stuNumber"));//
				tempRow.createCell(2).setCellValue(pageData.getString("cardNo"));//
				tempRow.createCell(3).setCellValue(pageData.getString("cost"));//
				tempRow.createCell(4).setCellValue(pageData.getString("error"));//
			}
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//return path + "error.xls";
		return "write\\" + mulu + "\\error.xls";
	}
	
	/**
	 * 写出老师信息错误记录
	 * <p>描述:</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月19日 下午2:39:35
	 * @param errorList
	 * @param baseurl
	 * @param stu
	 * @return
	 */
	public String writeExcelTeacher(List<PageData> errorList,String baseurl,String stu) {

		String mulu = UuidUtil.get32UUID();// 目录

		// 放到 uploadFiles\itemlist 目录下
		ClassLoader loader = this.getClass().getClassLoader();
		URL url = loader.getResource("");
		String classBasePath = url.getPath();
		String path = classBasePath.replaceAll("WEB-INF/classes/", "");
		// E:\\luzhen\\usr\\apache-tomcat-7.0.78\\webapps\\colleges-admin
		path = path + "\\uploadFiles\\teacher\\write\\" + mulu + "\\";

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}

		OutputStream out = null;

		try {
			Workbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet("导入失败记录");
			// Row row = sheet.getRow(i);
			// sheet.removeRow(row);
			out = new FileOutputStream(path + "error.xls");
			Row rowHead= sheet.createRow(0);//表头行
			rowHead.createCell(0).setCellValue("姓名");//
			rowHead.createCell(1).setCellValue("身份证号码");//
			rowHead.createCell(2).setCellValue("性别");//
			rowHead.createCell(3).setCellValue("联系方式");//
			rowHead.createCell(4).setCellValue("教职工编码");//
			rowHead.createCell(4).setCellValue("所属组织");//
			rowHead.createCell(5).setCellValue("是否绑定支付宝");//
			Row tempRow = null;
			PageData pageData = null;
			
			for (int j = 0; j < errorList.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				tempRow = sheet.createRow(j + 1);
				// 得到要插入的每一条记录
				pageData = errorList.get(j);
				tempRow.createCell(0).setCellValue(pageData.getString("XINGMING"));//
				tempRow.createCell(1).setCellValue(pageData.getString("SHENFENZHENGHAO"));//
				tempRow.createCell(2).setCellValue(pageData.getString("XINGBIE"));//
				tempRow.createCell(3).setCellValue(pageData.getString("SHOUJI"));//
				tempRow.createCell(4).setCellValue(pageData.getString("TEACHER_NO"));//
				tempRow.createCell(4).setCellValue(pageData.getString("DEPARTMENTNAME"));//
				tempRow.createCell(5).setCellValue(pageData.getString("error"));//
			}
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		return path + "error.xls";
		return "write\\" + mulu + "\\error.xls";
	}
	/**
	 * 导入班级信息错误记录
	 * <p>描述:</p>
	 * @author Administrator wzz
	 * @date 2018年6月15日 
	 * @param errorList
	 * @param baseurl
	 * @param stu
	 * @return
	 */
	public String writeExcelClassErrInfo(List<PageData> errorList,String baseurl,String stu) {
		
		String mulu = UuidUtil.get32UUID();// 目录
		
		// 放到 uploadFiles\itemlist 目录下
		ClassLoader loader = this.getClass().getClassLoader();
		URL url = loader.getResource("");
		String classBasePath = url.getPath();
		String path = classBasePath.replaceAll("WEB-INF/classes/", "");
		path = path + "\\uploadFiles\\classinfo\\write\\" + mulu + "\\";
		
		File file = new File(path);
		
		if (!file.exists()) {
			file.mkdirs();
		}		
		OutputStream out = null;
		
		try {
			Workbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet("导入失败记录");
			// Row row = sheet.getRow(i);
			// sheet.removeRow(row);
			out = new FileOutputStream(path + "error.xls");
			Row rowHead= sheet.createRow(0);//表头行
			rowHead.createCell(0).setCellValue("入学年份");//
			rowHead.createCell(1).setCellValue("院校专业编码");//
			rowHead.createCell(2).setCellValue("班级名称");//
			rowHead.createCell(3).setCellValue("班主任");//
			rowHead.createCell(4).setCellValue("失败原因");//
			Row tempRow = null;
			PageData pageData = null;			
			for (int j = 0; j < errorList.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				tempRow = sheet.createRow(j + 1);
				// 得到要插入的每一条记录
				pageData = errorList.get(j);
				tempRow.createCell(0).setCellValue(pageData.getString("grade"));//
				tempRow.createCell(1).setCellValue(pageData.getString("departmentId"));//
				tempRow.createCell(2).setCellValue(pageData.getString("className"));//
				tempRow.createCell(3).setCellValue(pageData.getString("userName"));//
				tempRow.createCell(4).setCellValue(pageData.getString("error"));//
			}
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "write\\" + mulu + "\\error.xls";
	}
	
	public String writeExcelDept(List<PageData> errorList) {

		String mulu = UuidUtil.get32UUID();// 目录

		// 放到 uploadFiles\itemlist 目录下
		ClassLoader loader = this.getClass().getClassLoader();
		URL url = loader.getResource("");
		String classBasePath = url.getPath();
		String path = classBasePath.replaceAll("WEB-INF/classes/", "");
		// E:\\luzhen\\usr\\apache-tomcat-7.0.78\\webapps\\colleges-admin
		path = path + "\\uploadFiles\\dept\\write\\" + mulu + "\\";

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}

		OutputStream out = null;

		try {
			Workbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet("导入失败记录");
			// Row row = sheet.getRow(i);
			// sheet.removeRow(row);
			out = new FileOutputStream(path + "error.xls");
			Row rowHead= sheet.createRow(0);//表头行
			rowHead.createCell(0).setCellValue("院校专业名称");
			rowHead.createCell(1).setCellValue("院校专业编码");
			rowHead.createCell(2).setCellValue("院校专业类型");
			rowHead.createCell(3).setCellValue("上级院校专业编码");
			rowHead.createCell(4).setCellValue("失败原因");//
			
			Row tempRow = null;
			PageData pageData = null;
			
			for (int j = 0; j < errorList.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				tempRow = sheet.createRow(j + 1);
				// 得到要插入的每一条记录
				pageData = errorList.get(j);
				tempRow.createCell(0).setCellValue(pageData.getString("deptName"));//
				tempRow.createCell(1).setCellValue(pageData.getString("deptNo"));//
				tempRow.createCell(2).setCellValue(pageData.getString("leibie"));//
				tempRow.createCell(3).setCellValue(pageData.getString("parentDeptNo"));//
				tempRow.createCell(4).setCellValue(pageData.getString("error"));
			}
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		return path + "error.xls";
		return "write\\" + mulu + "\\error.xls";
	}
	public String writeExcelCJDept(List<PageData> errorList) {
		
		String mulu = UuidUtil.get32UUID();// 目录
		
		// 放到 uploadFiles\itemlist 目录下
		ClassLoader loader = this.getClass().getClassLoader();
		URL url = loader.getResource("");
		String classBasePath = url.getPath();
		String path = classBasePath.replaceAll("WEB-INF/classes/", "");
		path = path + "\\uploadFiles\\dept\\write\\" + mulu + "\\";
		
		File file = new File(path);
		
		if (!file.exists()) {
			file.mkdirs();
		}
		
		OutputStream out = null;
		
		try {
			Workbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet("导入失败记录");
			out = new FileOutputStream(path + "error.xls");
			Row rowHead= sheet.createRow(0);//表头行
			rowHead.createCell(0).setCellValue("院校专业名称");
			rowHead.createCell(1).setCellValue("院校专业编码");
			rowHead.createCell(2).setCellValue("院校专业类型");
			rowHead.createCell(3).setCellValue("上级院校专业编码");
			rowHead.createCell(4).setCellValue("学生类型");
			rowHead.createCell(5).setCellValue("失败原因");//
			
			Row tempRow = null;
			PageData pageData = null;
			
			for (int j = 0; j < errorList.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				tempRow = sheet.createRow(j + 1);
				// 得到要插入的每一条记录
				pageData = errorList.get(j);
				tempRow.createCell(0).setCellValue(pageData.getString("deptName"));//
				tempRow.createCell(1).setCellValue(pageData.getString("deptNo"));//
				tempRow.createCell(2).setCellValue(pageData.getString("leibie"));//
				tempRow.createCell(3).setCellValue(pageData.getString("parentDeptNo"));//
				tempRow.createCell(4).setCellValue(pageData.getString("XSLX"));
				tempRow.createCell(5).setCellValue(pageData.getString("error"));
			}
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		return path + "error.xls";
		return "write\\" + mulu + "\\error.xls";
	}
	public String writeExcelNation(List<PageData> errorList) {

		String mulu = UuidUtil.get32UUID();// 目录

		// 放到 uploadFiles\itemlist 目录下
		ClassLoader loader = this.getClass().getClassLoader();
		URL url = loader.getResource("");
		String classBasePath = url.getPath();
		String path = classBasePath.replaceAll("WEB-INF/classes/", "");
		// E:\\luzhen\\usr\\apache-tomcat-7.0.78\\webapps\\colleges-admin
		path = path + "\\uploadFiles\\nation\\write\\" + mulu + "\\";

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}

		OutputStream out = null;

		try {
			Workbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet("导入失败记录");
			// Row row = sheet.getRow(i);
			// sheet.removeRow(row);
			out = new FileOutputStream(path + "error.xls");
			Row rowHead= sheet.createRow(0);//表头行
			rowHead.createCell(0).setCellValue("民族名称");
			rowHead.createCell(1).setCellValue("失败原因");//
			
			Row tempRow = null;
			PageData pageData = null;
			
			for (int j = 0; j < errorList.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				tempRow = sheet.createRow(j + 1);
				// 得到要插入的每一条记录
				pageData = errorList.get(j);
				tempRow.createCell(0).setCellValue(pageData.getString("nationName"));//
				tempRow.createCell(1).setCellValue(pageData.getString("error"));
			}
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		return path + "error.xls";
		return "write\\" + mulu + "\\error.xls";
	}
	public String writeJianMianExcel(List<PageData> errorList) {

		String mulu = UuidUtil.get32UUID();// 目录
		// 放到 uploadFiles\itemlist 目录下
		ClassLoader loader = this.getClass().getClassLoader();
		URL url = loader.getResource("");
		String classBasePath = url.getPath();
		String path = classBasePath.replaceAll("WEB-INF/classes/", "");
		// E:\\luzhen\\usr\\apache-tomcat-7.0.78\\webapps\\colleges-admin
		path = path + "\\uploadFiles\\jianmian\\write\\" + mulu + "\\";

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}

		OutputStream out = null;

		try {
			Workbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet("导入失败记录");
			out = new FileOutputStream(path + "error.xls");
			Row rowHead= sheet.createRow(0);//表头行
			rowHead.createCell(0).setCellValue("身份证号");
			rowHead.createCell(1).setCellValue("减免金额");//
			rowHead.createCell(2).setCellValue("失败原因");//
			
			Row tempRow = null;
			PageData pageData = null;
			
			for (int j = 0; j < errorList.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				tempRow = sheet.createRow(j + 1);
				// 得到要插入的每一条记录
				pageData = errorList.get(j);
				tempRow.createCell(0).setCellValue(pageData.getString("shenFenZhengHao"));//
				tempRow.createCell(1).setCellValue(pageData.getString("jianMianJine"));
				tempRow.createCell(2).setCellValue(pageData.getString("error"));
			}
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		return path + "error.xls";
		return "write\\" + mulu + "\\error.xls";
	}
	public String writeStuLoanErrorExcel(List<PageData> errorList) {
		
		String mulu = UuidUtil.get32UUID();// 目录
		// 放到 uploadFiles\itemlist 目录下
		ClassLoader loader = this.getClass().getClassLoader();
		URL url = loader.getResource("");
		String classBasePath = url.getPath();
		String path = classBasePath.replaceAll("WEB-INF/classes/", "");
		path = path + "\\uploadFiles\\stuloan\\write\\" + mulu + "\\";
		
		File file = new File(path);
		
		if (!file.exists()) {
			file.mkdirs();
		}
		
		OutputStream out = null;
		
		try {
			Workbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet("导入失败记录");
			out = new FileOutputStream(path + "error.xls");
			Row rowHead= sheet.createRow(0);//表头行
			rowHead.createCell(0).setCellValue("身份证号");
			rowHead.createCell(1).setCellValue("贷款金额");//
			rowHead.createCell(2).setCellValue("失败原因");//
			
			Row tempRow = null;
			PageData pageData = null;
			
			for (int j = 0; j < errorList.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				tempRow = sheet.createRow(j + 1);
				// 得到要插入的每一条记录
				pageData = errorList.get(j);
				tempRow.createCell(0).setCellValue(pageData.getString("SHENFENZHENGHAO"));//
				tempRow.createCell(1).setCellValue(pageData.getString("LOAN"));
				tempRow.createCell(2).setCellValue(pageData.getString("error"));
			}
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		return path + "error.xls";
		return "write\\" + mulu + "\\error.xls";
	}
	
	public String writeExcelStuSource(List<PageData> errorList) {

		String mulu = UuidUtil.get32UUID();// 目录

		// 放到 uploadFiles\itemlist 目录下
		ClassLoader loader = this.getClass().getClassLoader();
		URL url = loader.getResource("");
		String classBasePath = url.getPath();
		String path = classBasePath.replaceAll("WEB-INF/classes/", "");
		// E:\\luzhen\\usr\\apache-tomcat-7.0.78\\webapps\\colleges-admin
		path = path + "\\uploadFiles\\stuSource\\write\\" + mulu + "\\";

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}

		OutputStream out = null;

		try {
			Workbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet("导入失败记录");
			// Row row = sheet.getRow(i);
			// sheet.removeRow(row);
			out = new FileOutputStream(path + "error.xls");
			Row rowHead= sheet.createRow(0);//表头行
			rowHead.createCell(0).setCellValue("学生生源名称");
			rowHead.createCell(1).setCellValue("失败原因");//
			
			Row tempRow = null;
			PageData pageData = null;
			
			for (int j = 0; j < errorList.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				tempRow = sheet.createRow(j + 1);
				// 得到要插入的每一条记录 
				pageData = errorList.get(j);
				tempRow.createCell(0).setCellValue(pageData.getString("stuSourceName"));//
				tempRow.createCell(1).setCellValue(pageData.getString("error"));
			}
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		return path + "error.xls";
		return "write\\" + mulu + "\\error.xls";
	}
}
