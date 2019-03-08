package com.fh.excel;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.fh.util.PageData;
import com.keman.zhgd.common.Tools;

/**
 * 
 * <p>标题:XlsExcel</p>
 * <p>描述:支持2003excel</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月16日 下午11:11:20
 */
public class XlsExcel extends ExcelBase{
	
	private HSSFWorkbook book;

	public HSSFWorkbook getBook() {
		return book;
	}

	public void setBook(HSSFWorkbook book) {
		this.book = book;
	}
	
	@Override
	public List<PageData> getList() {
		HSSFSheet sheet = book.getSheetAt(0);
		HSSFRow row = null;
		PageData data = null;
		
		List<PageData> list = new ArrayList<PageData>();
		
		//遍历行
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
			if(row ==null){
				continue;
			}
			HSSFCell stuNameCell = row.getCell(0);//学生名称
			HSSFCell stuNumberCell = row.getCell(1);//学号
			HSSFCell cardNoCell = row.getCell(2);//身份证号
			HSSFCell gradeCell = row.getCell(3);//入学年份
			HSSFCell classTypeCell = row.getCell(4);//班型
			HSSFCell costCell = row.getCell(5);//应收金额
			
			data = new PageData();
			
			/*if (itemNameCell!=null) {
				itemNameCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("itemName", itemNameCell.getStringCellValue().trim());
			}else {
				data.put("itemName", "");
			}*/
			if (stuNameCell!=null) {
				stuNameCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("stuName", stuNameCell.getStringCellValue().trim());
			}else {
				data.put("stuName", "");
			}
			if (stuNumberCell!=null) {
				stuNumberCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("stuNumber", stuNumberCell.getStringCellValue().trim());
			}else {
				data.put("stuNumber", "");
			}
			if (cardNoCell!=null) {
				cardNoCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("cardNo", cardNoCell.getStringCellValue().trim());
			}else {
				data.put("cardNo", "");
			}
			if (costCell!=null) {
				costCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("cost", costCell.getStringCellValue().trim());
			}else {
				data.put("cost", "");
			}
			if (gradeCell!=null) {
				gradeCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("grade", gradeCell.getStringCellValue().trim());
			}else {
				data.put("grade", "");
			}
			if (classTypeCell!=null) {
				classTypeCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("classType", classTypeCell.getStringCellValue().trim());
			}else {
				data.put("classType", "");
			}
			if("".equals(data.getString("cost"))&&"".equals(data.getString("stuName"))&&"".equals(data.getString("stuNumber"))&&"".equals(data.getString("cardNo"))&&
					"".equals(data.getString("grade"))&&"".equals(data.getString("classType"))){
				continue;
			}
			list.add(data);
		}
		return list;
	}
	@Override
	public List<PageData> getPartSchoolList() {
		HSSFSheet sheet = book.getSheetAt(0);
		HSSFRow row = null;
		PageData data = null;
		
		List<PageData> list = new ArrayList<PageData>();
		
		//遍历行
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
			if(row ==null){
				continue;
			}
			HSSFCell SCHOOLNAME = row.getCell(0);//合作学校名称
			HSSFCell DINGJIN = row.getCell(1);//定金
			
			data = new PageData();

			if (SCHOOLNAME!=null) {
				SCHOOLNAME.setCellType(Cell.CELL_TYPE_STRING);
				data.put("SCHOOLNAME", SCHOOLNAME.getStringCellValue().trim());
			}else {
				data.put("SCHOOLNAME", "");
			}
			if (DINGJIN!=null) {
				DINGJIN.setCellType(Cell.CELL_TYPE_STRING);
				data.put("DINGJIN", DINGJIN.getStringCellValue().trim());
			}else {
				data.put("DINGJIN", "");
			}

			
			if("".equals(data.getString("SCHOOLNAME"))&&"".equals(data.getString("DINGJIN"))){
				continue;
			}
			list.add(data);
		}
		return list;
	}
	
	@Override
	public List<PageData> getPaidList() {
		HSSFSheet sheet = book.getSheetAt(0);
		HSSFRow row = null;
		PageData data = null;
		
		List<PageData> list = new ArrayList<PageData>();
		
		//遍历行
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
//			HSSFCell itemNameCell = row.getCell(0);//缴费项目名称
			HSSFCell stuNameCell = row.getCell(0);//学生名称
			HSSFCell stuNumberCell = row.getCell(1);//学号
			HSSFCell cardNoCell = row.getCell(2);//身份证号
			HSSFCell totalMoneyCell = row.getCell(3);//缴费总金额
			HSSFCell cashCell = row.getCell(4);//现金
			HSSFCell bankCardNoCell = row.getCell(5);//银行卡号
			HSSFCell bankCardMoneyCell = row.getCell(6);//银行卡金额
			HSSFCell weChatCell = row.getCell(7);//微信
			HSSFCell alipayCell = row.getCell(8);//支付宝
			HSSFCell ttCell = row.getCell(9);//电汇
			data = new PageData();
			
			/*if (itemNameCell!=null) {
				itemNameCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("itemName", itemNameCell.getStringCellValue().trim());
			}else {
				data.put("itemName", "");
			}*/
			if (stuNameCell!=null) {
				stuNameCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("stuName", stuNameCell.getStringCellValue().trim());
			}else {
				data.put("stuName", "");
			}
			if (stuNumberCell!=null) {
				stuNumberCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("stuNumber", stuNumberCell.getStringCellValue().trim());
			}else {
				data.put("stuNumber", "");
			}
			if (cardNoCell!=null) {
				cardNoCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("cardNo", cardNoCell.getStringCellValue().trim());
			}else {
				data.put("cardNo", "");
			}
			if (totalMoneyCell!=null) {
				totalMoneyCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("TOTALMONEY", totalMoneyCell.getStringCellValue().trim());
			}else {
				data.put("TOTALMONEY", "");
			}
			if (cashCell!=null) {
				cashCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("CASH", cashCell.getStringCellValue().trim());
			}else {
				data.put("CASH", "");
			}
			if (bankCardNoCell!=null) {
				bankCardNoCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("BANKCARDNO", bankCardNoCell.getStringCellValue().trim());
			}else {
				data.put("BANKCARDNO", "");
			}
			if (bankCardMoneyCell!=null) {
				bankCardMoneyCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("CARD", bankCardMoneyCell.getStringCellValue().trim());
			}else {
				data.put("CARD", "");
			}
			if (weChatCell!=null) {
				weChatCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("WX", weChatCell.getStringCellValue().trim());
			}else {
				data.put("WX", "");
			}
			if (alipayCell!=null) {
				alipayCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("ZFB", alipayCell.getStringCellValue().trim());
			}else {
				data.put("ZFB", "");
			}
			if (ttCell!=null) {
				ttCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("TT", ttCell.getStringCellValue().trim());
			}else {
				data.put("TT", "");
			}
			if(/*"".equals(data.getString("itemName"))&&*/"".equals(data.getString("stuName"))&&"".equals(data.getString("stuNumber"))&&"".equals(data.getString("cardNo"))&&
					"".equals(data.getString("TOTALMONEY"))&&"".equals(data.getString("CASH"))&&"".equals(data.getString("BANKCARDNO"))
					&&"".equals(data.getString("CARD"))&&"".equals(data.getString("WX"))&&"".equals(data.getString("ZFB"))
					&&"".equals(data.getString("TT"))){
				continue;
			}
			list.add(data);
		}
		return list;
	}
	
	@Override
	public List<PageData> getStuInfoList() {
		HSSFSheet sheet = book.getSheetAt(0);
		HSSFRow row = null;
		PageData data = null;
		
		List<PageData> list = new ArrayList<PageData>();
		
		//遍历行
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
			if(row ==null){
				continue;
			}
			HSSFCell SHENFENZHENGHAO = row.getCell(0);//身份证号
			HSSFCell XINGMING = row.getCell(1);//姓名
			/*HSSFCell OLD_BANJI = row.getCell(2);//原班级
*/			HSSFCell SHOUJI = row.getCell(2);//手机
			HSSFCell XUEHAO = row.getCell(3);//学号
			HSSFCell RXNIANFEN = row.getCell(4);//入学年份
			HSSFCell BANJI_TYPE = row.getCell(5);//班型
			HSSFCell CENGCI = row.getCell(6);//层次
			HSSFCell WHKXUEXIAO = row.getCell(7);//文化课学校
			HSSFCell YJ_NIANFEN = row.getCell(8);//预交年份
			HSSFCell YJ_BANJI_TYPE = row.getCell(9);//预交班型
			HSSFCell JIATINGZHUZHI = row.getCell(10);//家庭地址		
			HSSFCell YOUZHENGBIANMA= row.getCell(11);//邮政编码
			HSSFCell ONE_JHR = row.getCell(12);//第一监护人
			HSSFCell ONE_JHRGX = row.getCell(13);//第一监护人家庭关系
			HSSFCell ONE_JHRDH = row.getCell(14);//第一监护人联系电话
			HSSFCell TWO_JHR = row.getCell(15);//第二监护人
			HSSFCell TWO_JHRGX = row.getCell(16);//第二监护人家庭关系
			HSSFCell TWO_JHRDH = row.getCell(17);//第二监护人联系电话			
			HSSFCell BINGSHI = row.getCell(18);//病史
			HSSFCell LJQHTJ = row.getCell(19);//了解强化的途径
			HSSFCell TUIJIANREN = row.getCell(20);//推荐人
			HSSFCell GYRXSJ = row.getCell(21);//高一入学时间
			HSSFCell GZ_BANJI = row.getCell(22);//高中班级
			HSSFCell WENLIKE = row.getCell(23);//文理科
			HSSFCell BZRSCHOOL = row.getCell(24);//学校班主任
			HSSFCell BZRPHONE = row.getCell(25);//班主任电话
			HSSFCell STARTMEISHU = row.getCell(26);//自何时学习美术
			HSSFCell KEMU = row.getCell(27);//已学过科目
			HSSFCell LKFS = row.getCell(28);//联考分数
			HSSFCell XK_MARK = row.getCell(29);//校考成绩
			HSSFCell KSNUMBER = row.getCell(30);//考生号
			HSSFCell REMARKS1 = row.getCell(31);//备注1
			HSSFCell REMARKS2 = row.getCell(32);//备注2
			HSSFCell REMARKS3 = row.getCell(33);//备注3
			
			data = new PageData();
			if (SHENFENZHENGHAO!=null) {
				SHENFENZHENGHAO.setCellType(Cell.CELL_TYPE_STRING);
				data.put("SHENFENZHENGHAO", SHENFENZHENGHAO.getStringCellValue().trim());
			}else {
				data.put("SHENFENZHENGHAO", "");
			}
			if (XINGMING!=null) {
				XINGMING.setCellType(Cell.CELL_TYPE_STRING);
				data.put("XINGMING", XINGMING.getStringCellValue().trim());
			}else {
				data.put("XINGMING", "");
			}
			if (SHOUJI!=null) {
				SHOUJI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("SHOUJI", SHOUJI.getStringCellValue().trim());
			}else {
				data.put("SHOUJI", "");
			}
			if (XUEHAO!=null) {
				XUEHAO.setCellType(Cell.CELL_TYPE_STRING);
				data.put("XUEHAO", XUEHAO.getStringCellValue().trim());
			}else {
				data.put("XUEHAO", "");
			}
			if (RXNIANFEN!=null) {
				RXNIANFEN.setCellType(Cell.CELL_TYPE_STRING);
				data.put("RXNIANFEN", RXNIANFEN.getStringCellValue().trim());
			}else {
				data.put("RXNIANFEN", "");
			}
			if (BANJI_TYPE!=null) {
				BANJI_TYPE.setCellType(Cell.CELL_TYPE_STRING);
				data.put("BANJI_TYPE", BANJI_TYPE.getStringCellValue().trim());
			}else {
				data.put("BANJI_TYPE", "");
			}
			if (CENGCI!=null) {
				CENGCI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("CENGCI", CENGCI.getStringCellValue().trim());
			}else {
				data.put("CENGCI", "");
			}
			if (WHKXUEXIAO!=null) {
				WHKXUEXIAO.setCellType(Cell.CELL_TYPE_STRING);
				data.put("WHKXUEXIAO", WHKXUEXIAO.getStringCellValue().trim());
			}else {
				data.put("WHKXUEXIAO", "");
			}
			if (YJ_NIANFEN!=null) {
				YJ_NIANFEN.setCellType(Cell.CELL_TYPE_STRING);
				data.put("YJ_NIANFEN", YJ_NIANFEN.getStringCellValue().trim());
			}else {
				data.put("YJ_NIANFEN", "");
			}
			if (YJ_BANJI_TYPE!=null) {
				YJ_BANJI_TYPE.setCellType(Cell.CELL_TYPE_STRING);
				data.put("YJ_BANJI_TYPE", YJ_BANJI_TYPE.getStringCellValue().trim());
			}else {
				data.put("YJ_BANJI_TYPE", "");
			}
			if (JIATINGZHUZHI!=null) {
				JIATINGZHUZHI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("JIATINGZHUZHI", JIATINGZHUZHI.getStringCellValue().trim());
			}else {
				data.put("JIATINGZHUZHI", "");
			}
			if (YOUZHENGBIANMA!=null) {
				YOUZHENGBIANMA.setCellType(Cell.CELL_TYPE_STRING);
				data.put("YOUZHENGBIANMA", YOUZHENGBIANMA.getStringCellValue().trim());
			}else {
				data.put("YOUZHENGBIANMA", "");
			}
			if (ONE_JHR!=null) {
				ONE_JHR.setCellType(Cell.CELL_TYPE_STRING);
				data.put("ONE_JHR", ONE_JHR.getStringCellValue().trim());
			}else {
				data.put("ONE_JHR", "");
			}
			if (ONE_JHRGX!=null) {
				ONE_JHRGX.setCellType(Cell.CELL_TYPE_STRING);
				data.put("ONE_JHRGX", ONE_JHRGX.getStringCellValue().trim());
			}else {
				data.put("ONE_JHRGX", "");
			}
			if (ONE_JHRDH!=null) {
				ONE_JHRDH.setCellType(Cell.CELL_TYPE_STRING);
				data.put("ONE_JHRDH", ONE_JHRDH.getStringCellValue().trim());
			}else {
				data.put("ONE_JHRDH", "");
			}
			if (TWO_JHR!=null) {
				TWO_JHR.setCellType(Cell.CELL_TYPE_STRING);
				data.put("TWO_JHR", TWO_JHR.getStringCellValue().trim());
			}else {
				data.put("TWO_JHR", "");
			}
			if (TWO_JHRGX!=null) {
				TWO_JHRGX.setCellType(Cell.CELL_TYPE_STRING);
				data.put("TWO_JHRGX", TWO_JHRGX.getStringCellValue().trim());
			}else {
				data.put("TWO_JHRGX", "");
			}
			if (TWO_JHRDH!=null) {
				TWO_JHRDH.setCellType(Cell.CELL_TYPE_STRING);
				data.put("TWO_JHRDH", TWO_JHRDH.getStringCellValue().trim());
			}else {
				data.put("TWO_JHRDH", "");
			}
			if (BINGSHI!=null) {
				BINGSHI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("BINGSHI", BINGSHI.getStringCellValue().trim());
			}else {
				data.put("BINGSHI", "");
			}
			if (LJQHTJ!=null) {
				LJQHTJ.setCellType(Cell.CELL_TYPE_STRING);
				data.put("LJQHTJ", LJQHTJ.getStringCellValue().trim());
			}else {
				data.put("LJQHTJ", "");
			}
			if (TUIJIANREN!=null) {
				TUIJIANREN.setCellType(Cell.CELL_TYPE_STRING);
				data.put("TUIJIANREN", TUIJIANREN.getStringCellValue().trim());
			}else {
				data.put("TUIJIANREN", "");
			}
			if (GYRXSJ!=null) {
				GYRXSJ.setCellType(Cell.CELL_TYPE_STRING);
				data.put("GYRXSJ", GYRXSJ.getStringCellValue().trim());
			}else {
				data.put("GYRXSJ", "");
			}
			if (GZ_BANJI!=null) {
				GZ_BANJI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("GZ_BANJI", GZ_BANJI.getStringCellValue().trim());
			}else {
				data.put("GZ_BANJI", "");
			}
			if (WENLIKE!=null) {
				WENLIKE.setCellType(Cell.CELL_TYPE_STRING);
				data.put("WENLIKE", WENLIKE.getStringCellValue().trim());
			}else {
				data.put("WENLIKE", "");
			}
			if (BZRSCHOOL!=null) {
				BZRSCHOOL.setCellType(Cell.CELL_TYPE_STRING);
				data.put("BZRSCHOOL", BZRSCHOOL.getStringCellValue().trim());
			}else {
				data.put("BZRSCHOOL", "");
			}
			if (BZRPHONE!=null) {
				BZRPHONE.setCellType(Cell.CELL_TYPE_STRING);
				data.put("BZRPHONE", BZRPHONE.getStringCellValue().trim());
			}else {
				data.put("BZRPHONE", "");
			}
			if (STARTMEISHU!=null) {
				STARTMEISHU.setCellType(Cell.CELL_TYPE_STRING);
				data.put("STARTMEISHU", STARTMEISHU.getStringCellValue().trim());
			}else {
				data.put("STARTMEISHU", "");
			}
			if (KEMU!=null) {
				KEMU.setCellType(Cell.CELL_TYPE_STRING);
				data.put("KEMU", KEMU.getStringCellValue().trim());
			}else {
				data.put("KEMU", "");
			}
			if (LKFS!=null) {
				LKFS.setCellType(Cell.CELL_TYPE_STRING);
				data.put("LKFS", LKFS.getStringCellValue().trim());
			}else {
				data.put("LKFS", "");
			}
			if (XK_MARK!=null) {
				XK_MARK.setCellType(Cell.CELL_TYPE_STRING);
				data.put("XK_MARK", XK_MARK.getStringCellValue().trim());
			}else {
				data.put("XK_MARK", "");
			}
			if (KSNUMBER!=null) {
				KSNUMBER.setCellType(Cell.CELL_TYPE_STRING);
				data.put("KSNUMBER", KSNUMBER.getStringCellValue().trim());
			}else {
				data.put("KSNUMBER", "");
			}

			if (REMARKS1!=null) {
				REMARKS1.setCellType(Cell.CELL_TYPE_STRING);
				data.put("REMARKS1", REMARKS1.getStringCellValue().trim());
			}else {
				data.put("REMARKS1", "");
			}
			if (REMARKS2!=null) {
				REMARKS2.setCellType(Cell.CELL_TYPE_STRING);
				data.put("REMARKS2", REMARKS2.getStringCellValue().trim());
			}else {
				data.put("REMARKS2", "");
			}
			if (REMARKS3!=null) {
				REMARKS3.setCellType(Cell.CELL_TYPE_STRING);
				data.put("REMARKS3", REMARKS3.getStringCellValue().trim());
			}else {
				data.put("REMARKS3", "");
			}
			
			if("".equals(data.getString("SHENFENZHENGHAO"))&&"".equals(data.getString("XINGMING"))
					&&"".equals(data.getString("SHOUJI"))&&"".equals(data.getString("XUEHAO"))&&"".equals(data.getString("RXNIANFEN"))
					&&"".equals(data.getString("BANJI_TYPE"))&&"".equals(data.getString("CENGCI"))&&"".equals(data.getString("WHKXUEXIAO"))
					&&"".equals(data.getString("YJ_NIANFEN"))&&"".equals(data.getString("YJ_BANJI_TYPE"))&&"".equals(data.getString("JIATINGZHUZHI"))
					&&"".equals(data.getString("YOUZHENGBIANMA"))&&"".equals(data.getString("ONE_JHR"))&&"".equals(data.getString("REMARKS3"))
					&&"".equals(data.getString("ONE_JHRGX"))&&"".equals(data.getString("ONE_JHRDH"))&&"".equals(data.getString("TWO_JHR"))
					&&"".equals(data.getString("TWO_JHRGX"))&&"".equals(data.getString("TWO_JHRDH"))&&"".equals(data.getString("BINGSHI"))
					&&"".equals(data.getString("LJQHTJ"))&&"".equals(data.getString("TUIJIANREN"))&&"".equals(data.getString("GYRXSJ"))
					&&"".equals(data.getString("GZ_BANJI"))&&"".equals(data.getString("WENLIKE"))&&"".equals(data.getString("BZRSCHOOL"))
					&&"".equals(data.getString("BZRPHONE"))&&"".equals(data.getString("STARTMEISHU"))&&"".equals(data.getString("KEMU"))
					&&"".equals(data.getString("LKFS"))&&"".equals(data.getString("XK_MARK"))&&"".equals(data.getString("KSNUMBER"))
					&&"".equals(data.getString("OLD_BANJI"))&&"".equals(data.getString("REMARKS1"))&&"".equals(data.getString("REMARKS2"))){
					continue;
				}
			list.add(data);
		}
		return list;
	}
	
	@Override
	public List<PageData> getStuInfoCJList() {
		HSSFSheet sheet = book.getSheetAt(0);
		HSSFRow row = null;
		PageData data = null;
		
		List<PageData> list = new ArrayList<PageData>();
		
		//遍历行
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
			HSSFCell SHENFENZHENGHAO = row.getCell(0);//身份证号
			HSSFCell XUEHAO = row.getCell(1);//学号
			HSSFCell XINGMING = row.getCell(2);//姓名
			HSSFCell ZHENGZHIMIANMAO = row.getCell(3);//政治面貌
			HSSFCell SHOUJI = row.getCell(4);//联系方式
			HSSFCell KAOSHENGHAO = row.getCell(5);//考生号
			HSSFCell ZAIXUEZT = row.getCell(6);//在学状态
			HSSFCell DEPARTMEN_ID = row.getCell(7);//院校专业
			HSSFCell NIANJI = row.getCell(8);//入学年份
			HSSFCell BANJI = row.getCell(9);//班级
			HSSFCell CENGCI = row.getCell(10);//层次
			HSSFCell PICI = row.getCell(11);//批次		
			HSSFCell XUEJI = row.getCell(12);//学籍类型
			HSSFCell SHENGXUEBIAOSHI = row.getCell(13);//升学标识
			HSSFCell KELEI = row.getCell(14);//科类
			HSSFCell LUQUZHUANYE = row.getCell(15);//录取专业			
			HSSFCell MINZU = row.getCell(16);//民族
			HSSFCell YOUZHENGBIANMA = row.getCell(17);//邮政编码
			HSSFCell JIATINGZHUZHI = row.getCell(18);//家庭地址
			HSSFCell JTCYGX = row.getCell(19);//家庭成员关系
			HSSFCell JTCYXM = row.getCell(20);//家庭成员姓名
			HSSFCell JTCYLXDH = row.getCell(21);//家庭成员联系电话
			HSSFCell HUKOUXINGZHI = row.getCell(22);//户口性质
			HSSFCell JKZK = row.getCell(23);//健康状况
			HSSFCell KAOSHENGTECHANG = row.getCell(24);//考生特长
			HSSFCell SHIFOUPINKUN = row.getCell(25);//是否贫困
			HSSFCell ZXMC = row.getCell(26);//中学名称
			HSSFCell TOUDANGCHENGJI = row.getCell(27);//投档成绩
			HSSFCell XUEZHI = row.getCell(28);//学制
			HSSFCell JIHUAXINGZHI = row.getCell(29);//计划性质
			HSSFCell XSLX = row.getCell(30);//学生类型
			HSSFCell ZCXJSJ = row.getCell(31);//注册学籍时间
			HSSFCell REMARKS1 = row.getCell(32);//备注1
			HSSFCell REMARKS2 = row.getCell(33);//备注2
			HSSFCell REMARKS3 = row.getCell(34);//备注3
			HSSFCell REMARKS4 = row.getCell(35);//备注4
			HSSFCell REMARKS5 = row.getCell(36);//备注5
			HSSFCell REMARKS6 = row.getCell(37);//备注6
			HSSFCell REMARKS7 = row.getCell(38);//备注7
			HSSFCell REMARKS8 = row.getCell(39);//备注8
			HSSFCell TESHUBIAOJI = row.getCell(40);//特殊标记
			
			data = new PageData();
			
			if (TESHUBIAOJI!=null) {
				TESHUBIAOJI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("TESHUBIAOJI", TESHUBIAOJI.getStringCellValue().trim());
			}else {
				data.put("TESHUBIAOJI", "");
			}
			if (REMARKS1!=null) {
				REMARKS1.setCellType(Cell.CELL_TYPE_STRING);
				data.put("REMARKS1", REMARKS1.getStringCellValue().trim());
			}else {
				data.put("REMARKS1", "");
			}
			if (REMARKS2!=null) {
				REMARKS2.setCellType(Cell.CELL_TYPE_STRING);
				data.put("REMARKS2", REMARKS2.getStringCellValue().trim());
			}else {
				data.put("REMARKS2", "");
			}
			if (REMARKS3!=null) {
				REMARKS3.setCellType(Cell.CELL_TYPE_STRING);
				data.put("REMARKS3", REMARKS3.getStringCellValue().trim());
			}else {
				data.put("REMARKS3", "");
			}
			if (REMARKS4!=null) {
				REMARKS4.setCellType(Cell.CELL_TYPE_STRING);
				data.put("REMARKS4", REMARKS4.getStringCellValue().trim());
			}else {
				data.put("REMARKS4", "");
			}
			if (REMARKS5!=null) {
				REMARKS5.setCellType(Cell.CELL_TYPE_STRING);
				data.put("REMARKS5", REMARKS5.getStringCellValue().trim());
			}else {
				data.put("REMARKS5", "");
			}
			if (REMARKS6!=null) {
				REMARKS6.setCellType(Cell.CELL_TYPE_STRING);
				data.put("REMARKS6", REMARKS6.getStringCellValue().trim());
			}else {
				data.put("REMARKS6", "");
			}
			if (REMARKS7!=null) {
				REMARKS7.setCellType(Cell.CELL_TYPE_STRING);
				data.put("REMARKS7", REMARKS7.getStringCellValue().trim());
			}else {
				data.put("REMARKS7", "");
			}
			if (REMARKS8!=null) {
				REMARKS8.setCellType(Cell.CELL_TYPE_STRING);
				data.put("REMARKS8", REMARKS8.getStringCellValue().trim());
			}else {
				data.put("REMARKS8", "");
			}
			if (XUEHAO!=null) {
				XUEHAO.setCellType(Cell.CELL_TYPE_STRING);
				data.put("XUEHAO", XUEHAO.getStringCellValue().trim());
			}else {
				data.put("XUEHAO", "");
			}
			
			if (XUEJI!=null) {
				XUEJI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("XUEJI", XUEJI.getStringCellValue().trim());
			}else {
				data.put("XUEJI", "");
			}
			
			if (KELEI!=null) {
				KELEI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("KELEI", KELEI.getStringCellValue().trim());
			}else {
				data.put("KELEI", "");
			}
			
			if (LUQUZHUANYE!=null) {
				LUQUZHUANYE.setCellType(Cell.CELL_TYPE_STRING);
				data.put("LUQUZHUANYE", LUQUZHUANYE.getStringCellValue().trim());
			}else {
				data.put("LUQUZHUANYE", "");
			}
			
			if (XINGMING!=null) {
				XINGMING.setCellType(Cell.CELL_TYPE_STRING);
				data.put("XINGMING", XINGMING.getStringCellValue().trim());
			}else {
				data.put("XINGMING", "");
			}
			
			if (SHENFENZHENGHAO!=null) {
				SHENFENZHENGHAO.setCellType(Cell.CELL_TYPE_STRING);
				data.put("SHENFENZHENGHAO", SHENFENZHENGHAO.getStringCellValue().trim().toUpperCase());
			}else {
				data.put("SHENFENZHENGHAO", "");
			}
			
			if (DEPARTMEN_ID!=null) {
				DEPARTMEN_ID.setCellType(Cell.CELL_TYPE_STRING);
				data.put("DEPARTMEN_ID", DEPARTMEN_ID.getStringCellValue().trim());
			}else {
				data.put("DEPARTMEN_ID", "");
			}
			
			if (BANJI!=null) {
				BANJI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("BANJI", BANJI.getStringCellValue().trim());
			}else {
				data.put("BANJI", "");
			}
			if (NIANJI!=null) {
				NIANJI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("NIANJI", NIANJI.getStringCellValue().trim());
			}else {
				data.put("NIANJI", "");
			}
			
			if (CENGCI!=null) {
				CENGCI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("CENGCI", CENGCI.getStringCellValue().trim());
			}else {
				data.put("CENGCI", "");
			}
			
			if (PICI!=null) {
				PICI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("PICI", PICI.getStringCellValue().trim());
			}else {
				data.put("PICI", "");
			}
			
			if (SHOUJI!=null) {
				SHOUJI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("SHOUJI", SHOUJI.getStringCellValue().trim());
			}else {
				data.put("SHOUJI", "");
			}
			
			if (ZHENGZHIMIANMAO!=null) {
				ZHENGZHIMIANMAO.setCellType(Cell.CELL_TYPE_STRING);
				data.put("ZHENGZHIMIANMAO", ZHENGZHIMIANMAO.getStringCellValue().trim());
			}else {
				data.put("ZHENGZHIMIANMAO", "");
			}
			
			if (KAOSHENGHAO!=null) {
				KAOSHENGHAO.setCellType(Cell.CELL_TYPE_STRING);
				data.put("KAOSHENGHAO", KAOSHENGHAO.getStringCellValue().trim());
			}else {
				data.put("KAOSHENGHAO", "");
			}
			
			if (MINZU!=null) {
				MINZU.setCellType(Cell.CELL_TYPE_STRING);
				data.put("MINZU", MINZU.getStringCellValue().trim());
			}else {
				data.put("MINZU", "");
			}
			
			if (YOUZHENGBIANMA!=null) {
				YOUZHENGBIANMA.setCellType(Cell.CELL_TYPE_STRING);
				data.put("YOUZHENGBIANMA", YOUZHENGBIANMA.getStringCellValue().trim());
			}else {
				data.put("YOUZHENGBIANMA", "");
			}
			
			if (JIATINGZHUZHI!=null) {
				JIATINGZHUZHI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("JIATINGZHUZHI", JIATINGZHUZHI.getStringCellValue().trim());
			}else {
				data.put("JIATINGZHUZHI", "");
			}
			
			if (JTCYGX!=null) {
				JTCYGX.setCellType(Cell.CELL_TYPE_STRING);
				data.put("JTCYGX", JTCYGX.getStringCellValue().trim());
			}else {
				data.put("JTCYGX", "");
			}
			
			if (JTCYXM!=null) {
				JTCYXM.setCellType(Cell.CELL_TYPE_STRING);
				data.put("JTCYXM", JTCYXM.getStringCellValue().trim());
			}else {
				data.put("JTCYXM", "");
			}
			
			if (JTCYLXDH!=null) {
				JTCYLXDH.setCellType(Cell.CELL_TYPE_STRING);
				data.put("JTCYLXDH", JTCYLXDH.getStringCellValue().trim());
			}else {
				data.put("JTCYLXDH", "");
			}
			
			if (HUKOUXINGZHI!=null) {
				HUKOUXINGZHI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("HUKOUXINGZHI", HUKOUXINGZHI.getStringCellValue().trim());
			}else {
				data.put("HUKOUXINGZHI", "");
			}
			
			if (JKZK!=null) {
				JKZK.setCellType(Cell.CELL_TYPE_STRING);
				data.put("JKZK", JKZK.getStringCellValue().trim());
			}else {
				data.put("JKZK", "");
			}
			
			if (KAOSHENGTECHANG!=null) {
				KAOSHENGTECHANG.setCellType(Cell.CELL_TYPE_STRING);
				data.put("KAOSHENGTECHANG", KAOSHENGTECHANG.getStringCellValue().trim());
			}else {
				data.put("KAOSHENGTECHANG", "");
			}
			
			if (SHIFOUPINKUN!=null) {
				SHIFOUPINKUN.setCellType(Cell.CELL_TYPE_STRING);
				data.put("SHIFOUPINKUN", SHIFOUPINKUN.getStringCellValue().trim());
			}else {
				data.put("SHIFOUPINKUN", "");
			}
			
			if (ZXMC!=null) {
				ZXMC.setCellType(Cell.CELL_TYPE_STRING);
				data.put("ZXMC", ZXMC.getStringCellValue().trim());
			}else {
				data.put("ZXMC", "");
			}
			
			if (TOUDANGCHENGJI!=null) {
				TOUDANGCHENGJI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("TOUDANGCHENGJI", TOUDANGCHENGJI.getStringCellValue().trim());
			}else {
				data.put("TOUDANGCHENGJI", "");
			}
			
			if (XUEZHI!=null) {
				XUEZHI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("XUEZHI", XUEZHI.getStringCellValue().trim());
			}else {
				data.put("XUEZHI", "");
			}
			
			if (JIHUAXINGZHI!=null) {
				JIHUAXINGZHI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("JIHUAXINGZHI", JIHUAXINGZHI.getStringCellValue().trim());
			}else {
				data.put("JIHUAXINGZHI", "");
			}
			
			if (XSLX!=null) {
				XSLX.setCellType(Cell.CELL_TYPE_STRING);
				data.put("XSLX", XSLX.getStringCellValue().trim());
			}else {
				data.put("XSLX", "");
			}
			
			if (ZCXJSJ!=null) {
				ZCXJSJ.setCellType(Cell.CELL_TYPE_STRING);
				data.put("ZCXJSJ", ZCXJSJ.getStringCellValue().trim());
			}else {
				data.put("ZCXJSJ", "");
			}
			
			if (ZAIXUEZT!=null) {
				ZAIXUEZT.setCellType(Cell.CELL_TYPE_STRING);
				data.put("ZAIXUEZT", ZAIXUEZT.getStringCellValue().trim());
			}else {
				data.put("ZAIXUEZT", "");
			}
			
			if (SHENGXUEBIAOSHI!=null) {
				SHENGXUEBIAOSHI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("SHENGXUEBIAOSHI", SHENGXUEBIAOSHI.getStringCellValue().trim());
			}else {
				data.put("SHENGXUEBIAOSHI", "");
			}
			
			if("".equals(data.getString("XUEHAO"))&&"".equals(data.getString("XINGMING"))&&"".equals(data.getString("SHENFENZHENGHAO"))
					&&"".equals(data.getString("DEPARTMEN_ID"))&&"".equals(data.getString("NIANJI"))
					&&"".equals(data.getString("BANJI"))&&"".equals(data.getString("KAOSHENGHAO"))&&"".equals(data.getString("SHOUJI"))
					&&"".equals(data.getString("XINGBIE"))&&"".equals(data.getString("MINZU"))
					&&"".equals(data.getString("CENGCI"))&&"".equals(data.getString("PICI"))
					&&"".equals(data.getString("CHUSHENGRIQI"))
					&&"".equals(data.getString("JKZK"))&&"".equals(data.getString("SHIFOUPINKUN"))&&"".equals(data.getString("XUEJI"))){
				continue;
			}

			list.add(data);
		}
		return list;
	}
	
	@Override
	public List<PageData> getPayRecordList() {
		HSSFSheet sheet = book.getSheetAt(0);
		HSSFRow row = null;
		PageData data = null;
		
		List<PageData> list = new ArrayList<PageData>();
		
		//遍历行
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
			HSSFCell SHENFENZHENGHAO = row.getCell(0);//身份证号
			HSSFCell PAY_MODE = row.getCell(1);//费用类型
			HSSFCell PAY_MONEY = row.getCell(2);//费用金额
			data = new PageData();
			
			if (SHENFENZHENGHAO!=null) {
				SHENFENZHENGHAO.setCellType(Cell.CELL_TYPE_STRING);
				data.put("SHENFENZHENGHAO", SHENFENZHENGHAO.getStringCellValue().trim().toUpperCase());
			}else {
				data.put("SHENFENZHENGHAO", "");
			}
			if (PAY_MODE!=null) {
				PAY_MODE.setCellType(Cell.CELL_TYPE_STRING);
				data.put("PAY_MODE", PAY_MODE.getStringCellValue().trim());
			}else {
				data.put("PAY_MODE", "");
			}
			if (PAY_MONEY!=null) {
				PAY_MONEY.setCellType(Cell.CELL_TYPE_STRING);
				data.put("PAY_MONEY", PAY_MONEY.getStringCellValue().trim());
			}else {
				data.put("PAY_MONEY", "");
			}
			if("".equals(data.getString("SHENFENZHENGHAO"))&&"".equals(data.getString("PAY_MODE"))&&"".equals(data.getString("PAY_MONEY"))
			){
				continue;
			}
			list.add(data);
		}
		return list;
	}
	
	
	@Override
	public List<PageData> getjffbList() {
		HSSFSheet sheet = book.getSheetAt(0);
		HSSFRow row = null;
		PageData data = null;
		
		List<PageData> list = new ArrayList<PageData>();
		
		//遍历行
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
			HSSFCell stuNameCell = row.getCell(0);//学生名称
			HSSFCell stuNumberCell = row.getCell(1);//学号
			HSSFCell cardNoCell = row.getCell(2);//身份证号
			HSSFCell costCell = row.getCell(3);//费用
			
			data = new PageData();
			
			/*if (itemNameCell!=null) {
				itemNameCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("itemName", itemNameCell.getStringCellValue());
			}else {
				data.put("itemName", "");
			}*/
			if (stuNameCell!=null) {
				stuNameCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("stuName", stuNameCell.getStringCellValue().trim());
			}else {
				data.put("stuName", "");
			}
			if (stuNumberCell!=null) {
				stuNumberCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("stuNumber", stuNumberCell.getStringCellValue().trim());
			}else {
				data.put("stuNumber", "");
			}
			if (cardNoCell!=null) {
				cardNoCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("cardNo", cardNoCell.getStringCellValue().trim());
			}else {
				data.put("cardNo", "");
			}
			if (costCell!=null) {
				costCell.setCellType(Cell.CELL_TYPE_STRING);
				data.put("cost", costCell.getStringCellValue().trim());
			}else {
				data.put("cost", "");
			}
			if("".equals(data.getString("stuName"))&&"".equals(data.getString("stuNumber"))&&"".equals(data.getString("cardNo"))&&
					"".equals(data.getString("cost"))){
				continue;
			}
			list.add(data);
		}
		return list;
	}
	@Override
	public List<PageData> getClassInfoList() {
		HSSFSheet sheet = book.getSheetAt(0);
		HSSFRow row = null;
		PageData data = null;
		
		List<PageData> list = new ArrayList<PageData>();
		
		//遍历行
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
			HSSFCell grade = row.getCell(0);//入学年份
			HSSFCell departmentId = row.getCell(1);//院校专业编码
			HSSFCell className = row.getCell(2);//班级名称
			HSSFCell userName = row.getCell(3);//班主任名称

			data = new PageData();
			
			if (grade!=null) {
				grade.setCellType(Cell.CELL_TYPE_STRING);
				data.put("grade", grade.getStringCellValue());
			}else {
				data.put("grade", "");
			}
			if (departmentId!=null) {
				departmentId.setCellType(Cell.CELL_TYPE_STRING);
				data.put("departmentId", departmentId.getStringCellValue());
			}else {
				data.put("departmentId", "");
			}
			if (className!=null) {
				className.setCellType(Cell.CELL_TYPE_STRING);
				data.put("className", className.getStringCellValue());
			}else {
				data.put("className", "");
			}
			if (userName!=null) {
				userName.setCellType(Cell.CELL_TYPE_STRING);
				data.put("userName", userName.getStringCellValue());
			}else {
				data.put("userName", "");
			}			
			list.add(data);
		}
		return list;
	}
	@Override
	public List<PageData> getTeacherInfoList() {
		HSSFSheet sheet = book.getSheetAt(0);
		HSSFRow row = null;
		PageData data = null;
		
		List<PageData> list = new ArrayList<PageData>();
		
		//遍历行
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
			HSSFCell XINGMING = row.getCell(0);//姓名
			HSSFCell SHENFENZHENGHAO = row.getCell(1);//身份证号
			HSSFCell XINGBIE = row.getCell(2);//性别
			HSSFCell SHOUJI = row.getCell(3);//联系方式
			HSSFCell TEACHER_NO = row.getCell(4);//教职工编码
			HSSFCell DEPARTMENTNAME = row.getCell(5);//教职工编码
			
			
			data = new PageData();
			
			if (XINGMING!=null) {
				XINGMING.setCellType(Cell.CELL_TYPE_STRING);
				data.put("XINGMING", XINGMING.getStringCellValue());
			}else {
				data.put("XINGMING", "");
			}
			if (SHENFENZHENGHAO!=null) {
				SHENFENZHENGHAO.setCellType(Cell.CELL_TYPE_STRING);
				data.put("SHENFENZHENGHAO", SHENFENZHENGHAO.getStringCellValue().trim().toUpperCase());
			}else {
				data.put("SHENFENZHENGHAO", "");
			}
			if (SHOUJI!=null) {
				SHOUJI.setCellType(Cell.CELL_TYPE_STRING);
				data.put("SHOUJI", SHOUJI.getStringCellValue());
			}else {
				data.put("SHOUJI", "");
			}
			if (XINGBIE!=null) {
				XINGBIE.setCellType(Cell.CELL_TYPE_STRING);
				data.put("XINGBIE", XINGBIE.getStringCellValue());
			}else {
				data.put("XINGBIE", "");
			}
			if (TEACHER_NO!=null) {
				TEACHER_NO.setCellType(Cell.CELL_TYPE_STRING);
				data.put("TEACHER_NO", TEACHER_NO.getStringCellValue());
			}else {
				data.put("TEACHER_NO", "");
			}
			if (DEPARTMENTNAME!=null) {
				DEPARTMENTNAME.setCellType(Cell.CELL_TYPE_STRING);
				data.put("DEPARTMENTNAME", DEPARTMENTNAME.getStringCellValue());
			}else {
				data.put("DEPARTMENTNAME", "");
			}
			list.add(data);
		}
		return list;
	}
	
	@Override
	public List<PageData> getDormInfoList() {
		HSSFSheet sheet = book.getSheetAt(0);
		HSSFRow row = null;
		PageData data = null;
		
		List<PageData> list = new ArrayList<PageData>();
		
		//遍历行
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
			if(row ==null){
				continue;
			}
			HSSFCell SHENFENZHENGHAO = row.getCell(0);//身份证号
			HSSFCell XINGMING = row.getCell(1);//姓名
			HSSFCell XINGBIE = row.getCell(2);//性别
			HSSFCell DORMTYPE = row.getCell(3);//宿舍类型
			HSSFCell XIAOQU = row.getCell(4);//校区
			HSSFCell SUSHELOU = row.getCell(5);//宿舍楼
			HSSFCell LOUCENG = row.getCell(6);//楼层
			HSSFCell FANGJIAN = row.getCell(7);//房间
			HSSFCell CHUANGHAO = row.getCell(8);//床号
			HSSFCell BANXING = row.getCell(9);//班型
			HSSFCell RUXUENIANFEN = row.getCell(10);//入学年份
			if (SHENFENZHENGHAO == null && XINGMING == null && XINGBIE == null
					&& DORMTYPE == null && XIAOQU == null && SUSHELOU == null
					&& LOUCENG == null && FANGJIAN == null && CHUANGHAO == null&& BANXING == null&& RUXUENIANFEN == null) {
				continue;
			}
			
			data = new PageData();
			
			if (SHENFENZHENGHAO!=null) {
				SHENFENZHENGHAO.setCellType(Cell.CELL_TYPE_STRING);
				data.put("SHENFENZHENGHAO", SHENFENZHENGHAO.getStringCellValue().trim().toUpperCase());
			}else {
				data.put("SHENFENZHENGHAO", "");
			}
			if (XINGMING!=null) {
				XINGMING.setCellType(Cell.CELL_TYPE_STRING);
				data.put("XINGMING", XINGMING.getStringCellValue());
			}else {
				data.put("XINGMING", "");
			}
			if (XINGBIE!=null) {
				XINGBIE.setCellType(Cell.CELL_TYPE_STRING);
				data.put("XINGBIE", XINGBIE.getStringCellValue());
			}else {
				data.put("XINGBIE", "");
			}
			
			if (DORMTYPE!=null) {
				DORMTYPE.setCellType(Cell.CELL_TYPE_STRING);
				data.put("DORMTYPE", DORMTYPE.getStringCellValue());
			}else {
				data.put("DORMTYPE", "");
			}
			
			if (XIAOQU!=null) {
				XIAOQU.setCellType(Cell.CELL_TYPE_STRING);
				data.put("XIAOQU", XIAOQU.getStringCellValue());
			}else {
				data.put("XIAOQU", "");
			}
			if (SUSHELOU!=null) {
				SUSHELOU.setCellType(Cell.CELL_TYPE_STRING);
				data.put("SUSHELOU", SUSHELOU.getStringCellValue());
			}else {
				data.put("SUSHELOU", "");
			}
			if (LOUCENG!=null) {
				LOUCENG.setCellType(Cell.CELL_TYPE_STRING);
				data.put("LOUCENG", LOUCENG.getStringCellValue());
			}else {
				data.put("LOUCENG", "");
			}
			if (FANGJIAN!=null) {
				FANGJIAN.setCellType(Cell.CELL_TYPE_STRING);
				data.put("FANGJIAN", FANGJIAN.getStringCellValue());
			}else {
				data.put("FANGJIAN", "");
			}
			if (CHUANGHAO!=null) {
				CHUANGHAO.setCellType(Cell.CELL_TYPE_STRING);
				data.put("CHUANGHAO", CHUANGHAO.getStringCellValue());
			}else {
				data.put("CHUANGHAO", "");
			}
			if (BANXING!=null) {
				BANXING.setCellType(Cell.CELL_TYPE_STRING);
				data.put("BANXING", BANXING.getStringCellValue());
			}else {
				data.put("BANXING", "");
			}
			if (RUXUENIANFEN!=null) {
				RUXUENIANFEN.setCellType(Cell.CELL_TYPE_STRING);
				data.put("RUXUENIANFEN", RUXUENIANFEN.getStringCellValue());
			}else {
				data.put("RUXUENIANFEN", "");
			}
			list.add(data);
		}
		return list;
	}
	
	@Override
	public List<PageData> getDeptList() {
		HSSFSheet sheet = book.getSheetAt(0);
		HSSFRow row = null;
		PageData data = null;
		
		List<PageData> list = new ArrayList<PageData>();
		
		//遍历行
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
			HSSFCell deptName = row.getCell(0);//组织名称
			HSSFCell deptNo = row.getCell(1);//编码
			HSSFCell leibie = row.getCell(2);//类别
			HSSFCell parentDeptNo = row.getCell(3);//上级组织编码
			HSSFCell standardCode = row.getCell(4);//标准代码

			
			
			data = new PageData();
			
			if (deptName!=null) {
				deptName.setCellType(Cell.CELL_TYPE_STRING);
				data.put("deptName", deptName.getStringCellValue().trim());
			}else {
				data.put("deptName", "");
			}
			
			if (deptNo!=null) {
				deptNo.setCellType(Cell.CELL_TYPE_STRING);
				data.put("deptNo", deptNo.getStringCellValue().trim());
			}else {
				data.put("deptNo", "");
			}
			
			if (leibie!=null) {
				leibie.setCellType(Cell.CELL_TYPE_STRING);
				data.put("leibie", leibie.getStringCellValue().trim());
			}else {
				data.put("leibie", "");
			}
			
			if (parentDeptNo!=null) {
				parentDeptNo.setCellType(Cell.CELL_TYPE_STRING);
				data.put("parentDeptNo", parentDeptNo.getStringCellValue().trim());
			}else {
				data.put("parentDeptNo", "");
			}
			if (standardCode!=null) {
				standardCode.setCellType(Cell.CELL_TYPE_STRING);
				data.put("standardCode", standardCode.getStringCellValue().trim());
			}else {
				data.put("standardCode", "");
			}
			list.add(data);
		}
		return list;
	}
	@Override
	public List<PageData> getCJDeptList() {
		HSSFSheet sheet = book.getSheetAt(0);
		HSSFRow row = null;
		PageData data = null;
		
		List<PageData> list = new ArrayList<PageData>();
		
		//遍历行
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
			HSSFCell deptName = row.getCell(0);//组织名称
			HSSFCell deptNo = row.getCell(1);//编码
			HSSFCell leibie = row.getCell(2);//类别
			HSSFCell parentDeptNo = row.getCell(3);//上级组织编码
			HSSFCell XSLX = row.getCell(4);//学生类型
			HSSFCell standardCode = row.getCell(5);//标准代码
			
			
			
			data = new PageData();
			
			if (XSLX!=null) {
				XSLX.setCellType(Cell.CELL_TYPE_STRING);
				data.put("XSLX", XSLX.getStringCellValue().trim());
			}else {
				data.put("XSLX", "");
			}
			if (deptName!=null) {
				deptName.setCellType(Cell.CELL_TYPE_STRING);
				data.put("deptName", deptName.getStringCellValue().trim());
			}else {
				data.put("deptName", "");
			}
			
			if (deptNo!=null) {
				deptNo.setCellType(Cell.CELL_TYPE_STRING);
				data.put("deptNo", deptNo.getStringCellValue().trim());
			}else {
				data.put("deptNo", "");
			}
			
			if (leibie!=null) {
				leibie.setCellType(Cell.CELL_TYPE_STRING);
				data.put("leibie", leibie.getStringCellValue().trim());
			}else {
				data.put("leibie", "");
			}
			
			if (parentDeptNo!=null) {
				parentDeptNo.setCellType(Cell.CELL_TYPE_STRING);
				data.put("parentDeptNo", parentDeptNo.getStringCellValue().trim());
			}else {
				data.put("parentDeptNo", "");
			}
			if (standardCode!=null) {
				standardCode.setCellType(Cell.CELL_TYPE_STRING);
				data.put("standardCode", standardCode.getStringCellValue().trim());
			}else {
				data.put("standardCode", "");
			}
			list.add(data);
		}
		return list;
	}
	@Override
	public List<PageData> getNationList() {
		HSSFSheet sheet = book.getSheetAt(0);
		HSSFRow row = null;
		PageData data = null;
		
		List<PageData> list = new ArrayList<PageData>();
		
		//遍历行
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
			HSSFCell nationName = row.getCell(0);//民族名称
			data = new PageData();
			if (nationName!=null) {
				nationName.setCellType(Cell.CELL_TYPE_STRING);
				data.put("nationName", nationName.getStringCellValue().trim());
			}else {
				data.put("nationName", "");
			}
			if(Tools.isEmpty(data.getString("nationName"))){
				continue;
			}
			list.add(data);
		}
		return list;
	}
	@Override
	public List<PageData> getJianMianList() {
		HSSFSheet sheet = book.getSheetAt(0);
		HSSFRow row = null;
		PageData data = null;
		
		List<PageData> list = new ArrayList<PageData>();
		
		//遍历行
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
			HSSFCell shenFenZhengHao = row.getCell(0);//身份证号
			HSSFCell jianMianJine = row.getCell(1);//减免金额
			data = new PageData();
			if (shenFenZhengHao!=null) {
				shenFenZhengHao.setCellType(Cell.CELL_TYPE_STRING);
				data.put("shenFenZhengHao", shenFenZhengHao.getStringCellValue().trim().toUpperCase());
			}else {
				data.put("shenFenZhengHao", "");
			}
			if (jianMianJine!=null) {
				jianMianJine.setCellType(Cell.CELL_TYPE_STRING);
				data.put("jianMianJine", jianMianJine.getStringCellValue().trim());
			}else {
				data.put("jianMianJine", "");
			}
			if(Tools.isEmpty(data.getString("shenFenZhengHao"))&&Tools.isEmpty(data.getString("jianMianJine"))){
				continue;
			}
			list.add(data);
		}
		return list;
	}
	@Override
	public List<PageData> getStuLoanList() {
		HSSFSheet sheet = book.getSheetAt(0);
		HSSFRow row = null;
		PageData data = null;
		
		List<PageData> list = new ArrayList<PageData>();
		
		//遍历行
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
			if(row ==null){
				continue;
			}
			HSSFCell SHENFENZHENGHAO = row.getCell(0);//身份证号
			HSSFCell LOAN = row.getCell(1);//贷款金额
			
			data = new PageData();
			
			if (SHENFENZHENGHAO!=null) {
				SHENFENZHENGHAO.setCellType(Cell.CELL_TYPE_STRING);
				data.put("SHENFENZHENGHAO", SHENFENZHENGHAO.getStringCellValue().trim().toUpperCase());
			}else {
				data.put("SHENFENZHENGHAO", "");
			}
			if (LOAN!=null) {
				LOAN.setCellType(Cell.CELL_TYPE_STRING);
				data.put("LOAN", LOAN.getStringCellValue().trim());
			}else {
				data.put("LOAN", "");
			}

			if("".equals(data.getString("SHENFENZHENGHAO"))&&"".equals(data.getString("LOAN"))){
				continue;
			}
			list.add(data);
		}
		return list;
	}
	@Override
	public List<PageData> getStuSourceList() {
		HSSFSheet sheet = book.getSheetAt(0);
		HSSFRow row = null;
		PageData data = null;
		
		List<PageData> list = new ArrayList<PageData>();
		
		//遍历行
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			row = sheet.getRow(rowNum);
			HSSFCell stuSourceName = row.getCell(0);//学生来源名称
			data = new PageData();
			if (stuSourceName!=null) {
				stuSourceName.setCellType(Cell.CELL_TYPE_STRING);
				data.put("stuSourceName", stuSourceName.getStringCellValue().trim());
			}else {
				data.put("stuSourceName", "");
			}
			if(Tools.isEmpty(data.getString("stuSourceName"))){
				continue;
			}
			list.add(data);
		}
		return list;
	}
}
