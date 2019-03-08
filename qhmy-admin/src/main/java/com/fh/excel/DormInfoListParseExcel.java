package com.fh.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

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
 * <p>标题:DormInfoListParseExcel</p>
 * <p>描述:</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 康程亮
 * @date 2018年4月2日 下午2:33:26
 */
public class DormInfoListParseExcel extends AbsParseExcel {


	

	public List<PageData> getDormInfoList(File file, String fileName) throws Exception {
		this.setFileName(fileName);
		ExcelBase excelBase = this.openExcel(file);// 打开excel
		List<PageData> rstDatas = excelBase.getDormInfoList();
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
			rowHead.createCell(0).setCellValue("学生名称");//
			rowHead.createCell(1).setCellValue("学号");//
			rowHead.createCell(2).setCellValue("身份证号");//
			rowHead.createCell(3).setCellValue("费用");//
			rowHead.createCell(4).setCellValue("优惠金额");//
			rowHead.createCell(5).setCellValue("失败原因");//
			
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
				tempRow.createCell(3).setCellValue(pageData.getString("cost"));//
				tempRow.createCell(4).setCellValue(pageData.getString("discountMoney"));//
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
			rowHead.createCell(0).setCellValue("学生名称");//
			rowHead.createCell(1).setCellValue("学号");//
			rowHead.createCell(2).setCellValue("身份证号");//
			rowHead.createCell(3).setCellValue("缴费总额");//
			rowHead.createCell(4).setCellValue("现金");//
			rowHead.createCell(5).setCellValue("银行卡号");//
			rowHead.createCell(6).setCellValue("银行卡金额");//
			rowHead.createCell(7).setCellValue("微信");//
			rowHead.createCell(8).setCellValue("支付宝");//
			rowHead.createCell(9).setCellValue("失败原因");//
			
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
				tempRow.createCell(9).setCellValue(pageData.getString("error"));//
				
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
		path = path + "\\uploadFiles\\dorminfo\\write\\" + mulu + "\\";

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
			rowHead.createCell(0).setCellValue("身份证号码");//
			rowHead.createCell(1).setCellValue("姓名");//
			rowHead.createCell(2).setCellValue("性别");//
			rowHead.createCell(3).setCellValue("宿舍类型");//		
			rowHead.createCell(4).setCellValue("校区");//
			rowHead.createCell(5).setCellValue("宿舍楼");//
			rowHead.createCell(6).setCellValue("楼层");//
			rowHead.createCell(7).setCellValue("房间");//
			rowHead.createCell(8).setCellValue("床号");//
			rowHead.createCell(9).setCellValue("班型");//
			rowHead.createCell(10).setCellValue("入学年份");//
			rowHead.createCell(11).setCellValue("失败原因");//
			
			Row tempRow = null;
			PageData pageData = null;
			
			for (int j = 0; j < errorList.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				tempRow = sheet.createRow(j + 1);
				// 得到要插入的每一条记录
				pageData = errorList.get(j);
				tempRow.createCell(0).setCellValue(pageData.getString("SHENFENZHENGHAO"));//
				tempRow.createCell(1).setCellValue(pageData.getString("XINGMING"));//
				tempRow.createCell(2).setCellValue(pageData.getString("XINGBIE"));//
				tempRow.createCell(3).setCellValue(pageData.getString("DORMTYPE"));//
				tempRow.createCell(4).setCellValue(pageData.getString("XIAOQU"));//
				tempRow.createCell(5).setCellValue(pageData.getString("SUSHELOU"));//
				tempRow.createCell(6).setCellValue(pageData.getString("LOUCENG"));//
				tempRow.createCell(7).setCellValue(pageData.getString("FANGJIAN"));//	
				tempRow.createCell(8).setCellValue(pageData.getString("CHUANGHAO"));//	
				tempRow.createCell(9).setCellValue(pageData.getString("BANXING"));//	
				tempRow.createCell(10).setCellValue(pageData.getString("RUXUENIANFEN"));//	
				tempRow.createCell(11).setCellValue(pageData.getString("error"));//
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
			rowHead.createCell(4).setCellValue("优惠金额");//
			rowHead.createCell(5).setCellValue("失败原因");//
			
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
				tempRow.createCell(4).setCellValue(pageData.getString("discountMoney"));//
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
}
