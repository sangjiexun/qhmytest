package com.fh.controller.importstuinfo;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.excel.ImportErrorEnum;
import com.fh.excel.ItemListParseExcel;
import com.fh.service.system.partschool.PartSchoolManager;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.Utils;
import com.fh.util.upload.UploadConst;
/**
 * 导入学生信息
 * <p>标题:ImportStuInfoController</p>
 * <p>描述:</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator wzz
 * @date 2018年12月4日 下午5:05:42
 */
@Controller
@RequestMapping(value="/importstu")
public class ImportStuInfoController  extends BaseController{
	
	@Resource
	private StuInfoManager stuInfoService;
	
	@Resource(name="partSchoolService")
	private PartSchoolManager partSchoolService;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	/**
	 * 
	 * <p>描述:学生信息上传页面</p>
	 * @author Administrator wzz
	 * @date 2018年12月4日 下午5:05:42
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importstuinfo.json")
	public ModelAndView importStuInfo() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.setViewName("system/import/importstuinfo");
		mv.addObject("uuid", this.get32UUID());
		return mv;
	}
	
	
	/**
	 * 
	 * <p>描述:学生名单解析</p>
	 * @author Administrator wzz
	 * @date 2018年12月11日10:11:07
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/import-stuinfo-parse")
	public ModelAndView importListParse(Page page) throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		File file = null;
		try {
			//得到上传的文件
			String basePath = UploadConst.uploadPathMap.get(UploadConst.STU_INFO).getAbsolutePath() + "\\"+pd.getString("mulu")+"\\";
			basePath = basePath + pd.getString("fileName");
			file = new File(basePath);
			
			//解析excel
			ItemListParseExcel itemListParseExcel = new ItemListParseExcel();
			List<PageData> datas = itemListParseExcel.getStuInfoList(file,pd.getString("fileName"));
			List<PageData> errorList = new ArrayList<PageData>();//记录错误的导入信息
			List<PageData> successList=new ArrayList<PageData>();//记录正常数据
			List<PageData> batchList=new ArrayList<PageData>();//批量提交的数据
			String regex = "";//身份证号正则匹配
			String SHENFENZHENGHAO = "";//身份证号
			String phone = "";
			int xingBie = 0;
			PageData sysDicPd = null;  
			PageData jichupd = null;
			PageData isPd = new PageData();
			
			String yzbm = "";
			String REMARKS1 = null;
		    String REMARKS2 = null;
		    String REMARKS3 = null;
		    String kemus = "";
		    String xkmarks = "";
		    //
			Properties prop = new Properties();
			InputStream in = null;
			in=ImportStuInfoController.class.getClassLoader().getResourceAsStream("conf.properties");
			prop.load(in);
			String methodurl="";
			Map<String,String> rep=new HashMap<String,String>();
			if(prop!=null){
				methodurl=prop.getProperty("neiUrl");
			}
			//end 解析excel
			boolean IdCardIsDuplicated=false;
			boolean xueHaoIsDuplicated=false;
			for (PageData pageData : datas) {
				sysDicPd = new PageData();
				 /**-------------验证不能为空开始--------------*/
				if (Tools.isEmpty(pageData.getString("SHENFENZHENGHAO"))
						|| Tools.isEmpty(pageData.getString("RXNIANFEN"))
						|| Tools.isEmpty(pageData.getString("BANJI_TYPE"))
						|| Tools.isEmpty(pageData.getString("CENGCI"))
						||Tools.isEmpty(pageData.getString("XINGMING"))
						||Tools.isEmpty(pageData.getString("SHOUJI"))
						||Tools.isEmpty(pageData.getString("ONE_JHR"))
						||Tools.isEmpty(pageData.getString("ONE_JHRGX"))
						||Tools.isEmpty(pageData.getString("ONE_JHRDH"))
						||Tools.isEmpty(pageData.getString("WHKXUEXIAO"))
						
						) {
					pageData.put("error", ImportErrorEnum.core_data_empty.getValue());
					errorList.add(pageData);
					continue;
				}
				 /**-------------验证不能为空开始--------------*/
				
				/**-------------校验身份证号格式是否正确开始--------------*/	
				regex = "(^\\d{15}$)|(^\\d{17}(\\d|X)$)";
				SHENFENZHENGHAO = pageData.getString("SHENFENZHENGHAO");
				if(Utils.isNotEmpty(SHENFENZHENGHAO)){
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(SHENFENZHENGHAO);
					boolean isMatch = m.matches();
					if(!isMatch){
						pageData.put("error", ImportErrorEnum.stuIdCardError.getValue());
						errorList.add(pageData);
						continue;
					}
				}
				/**-------------校验身份证号格式是否正确结束--------------*/	
				
				/**-------------验证文理科格式是否正确开始--------------*/	
				if(!Tools.isEmpty(pageData.getString("WENLIKE"))){
					if(!"文科".equals(pageData.getString("WENLIKE"))&&!"理科".equals(pageData.getString("WENLIKE"))){
						pageData.put("error", ImportErrorEnum.wenlikeError.getValue());
						errorList.add(pageData);
						continue;
					}
				}
				
				/**-------------验证文理科格式否正确结束--------------*/	
				
				/**-------------性别和出生日期根据身份证号生成开始--------------*/
				xingBie = SHENFENZHENGHAO.charAt(SHENFENZHENGHAO.length()-2);
				pageData.put("XINGBIE", xingBie % 2 == 1 ? "M" : "W");
				/**-------------性别和出生日期根据身份证号生成结束--------------*/
				
				/**-------------验证手机、第一监护人联系电话、第二监护人联系电话、班主任电话格式是否正确开始--------------*/				
				regex = "^1[345789]\\d{9}$";
				//手机
				phone = pageData.getString("SHOUJI");
				if(Utils.isNotEmpty(phone)){
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(phone);
					boolean isMatch = m.matches();
					if(!isMatch){
						pageData.put("error", ImportErrorEnum.stuIphoneError.getValue());
						errorList.add(pageData);
						continue;
					}
				}
				//第一监护人联系电话
				phone = pageData.getString("ONE_JHRDH");
				if(Utils.isNotEmpty(phone)){
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(phone);
					boolean isMatch = m.matches();
					if(!isMatch){
						pageData.put("error", ImportErrorEnum.jhrlxdh_error.getValue());
						errorList.add(pageData);
						continue;
					}
				}
				//第二监护人联系电话
				phone = pageData.getString("TWO_JHRDH");
				if(Utils.isNotEmpty(phone)){
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(phone);
					boolean isMatch = m.matches();
					if(!isMatch){
						pageData.put("error", ImportErrorEnum.jhrlxdh_error.getValue());
						errorList.add(pageData);
						continue;
					}
				}
				//班主任电话
				phone = pageData.getString("BZRPHONE");
				if(Utils.isNotEmpty(phone)){
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(phone);
					boolean isMatch = m.matches();
					if(!isMatch){
						pageData.put("error", ImportErrorEnum.bzrdh_error.getValue());
						errorList.add(pageData);
						continue;
					}
				}
				/**-------------验证手机、第一监护人联系电话、第二监护人联系电话、班主任电话格式是否正确结束--------------*/	
				
				/**-------------验证学号不能重复开始--------------*/
				if(pageData.getString("XUEHAO")!=null&&!"".equals(pageData.getString("XUEHAO"))){
					sysDicPd.put("XUEHAO", pageData.getString("XUEHAO"));
					jichupd = (PageData) stuInfoService.getStuinfoByXuehao(sysDicPd);
					if(jichupd!=null){
						pageData.put("error", ImportErrorEnum.xuehaorepeat_input_error.getValue());
						errorList.add(pageData);
						continue;					
					}
				}				
				/**-------------验证学号不能重复结束--------------*/
				
				/**-------------验证入学年份是否存在开始--------------*/
				if(pageData.getString("RXNIANFEN")!=null&&!"".equals(pageData.getString("RXNIANFEN"))){
					sysDicPd.put("NAME", pageData.getString("RXNIANFEN"));
					sysDicPd.put("PARENT_ID", "GRADE");
					jichupd = (PageData) stuInfoService.getSysDictyionaries(sysDicPd);
					if(jichupd==null){
						pageData.put("error", ImportErrorEnum.grade_input_error.getValue());
						errorList.add(pageData);
						continue;					
					}else{
						pageData.put("RXNIANFEN_PKID", jichupd.getString("DICTIONARIES_ID"));
					}
				}else{
					pageData.put("RXNIANFEN_PKID", "");
				}				
				/**-------------验证入学年份是否存在结束--------------*/
				
				/**-------------验证班型是否存在开始--------------*/
				if(pageData.getString("BANJI_TYPE")!=null&&!"".equals(pageData.getString("BANJI_TYPE"))){
					sysDicPd.put("NAME", pageData.getString("BANJI_TYPE"));
					sysDicPd.put("PARENT_ID", "CLASSTYPE");
					jichupd = (PageData) stuInfoService.getSysDictyionaries(sysDicPd);
					if(jichupd==null){
						pageData.put("error", ImportErrorEnum.banxing_input_error.getValue());
						errorList.add(pageData);
						continue;					
					}else{
						pageData.put("BANJI_TYPE_PKID", jichupd.getString("DICTIONARIES_ID"));
					}
				}else{
					pageData.put("BANJI_TYPE_PKID", "");
				}				
				/**-------------验证班型是否存在结束--------------*/
				
				/**-------------身份证号+入学年份+班型应该是唯一开始--------------*/
				PageData isCf = null;//唯一验证
				isPd.put("SHENFENZHENGHAO", pageData.getString("SHENFENZHENGHAO"));
				isPd.put("RXNIANFEN", pageData.getString("RXNIANFEN_PKID"));
				isPd.put("BANJI_TYPE", pageData.getString("BANJI_TYPE_PKID"));
				isCf = stuInfoService.getStuBySfzRnBx(isPd);
				if(isCf!=null){
					pageData.put("error", ImportErrorEnum.bxrnsfn_input_error.getValue());
					errorList.add(pageData);
					continue;
				}
				/**-------------身份证号+入学年份+班型应该是唯一结束--------------*/

				/**-------------判断层次输入是否正确开始--------------*/
				jichupd = stuInfoService.getCengCiByName(pageData);
				if(jichupd==null){
					pageData.put("error", ImportErrorEnum.cengci_input_error.getValue());
					errorList.add(pageData);
					continue;
				}else{
					pageData.put("CENGCI_PKID", jichupd.getString("PKID"));
				}
				/**-------------判断层次输入是否正确结束--------------*/
				
				/**-------------验证预交年份是否存在开始--------------*/
				if(pageData.getString("YJ_NIANFEN")!=null&&!"".equals(pageData.getString("YJ_NIANFEN"))){
					sysDicPd.put("NAME", pageData.getString("YJ_NIANFEN"));
					sysDicPd.put("PARENT_ID", "GRADE");
					jichupd = (PageData) stuInfoService.getSysDictyionaries(sysDicPd);
					if(jichupd==null){
						pageData.put("error", ImportErrorEnum.yjgrade_input_error.getValue());
						errorList.add(pageData);
						continue;					
					}else{
						pageData.put("YJ_NIANFEN", jichupd.getString("DICTIONARIES_ID"));
					}
				}else{
					pageData.put("YJ_NIANFEN", "");
				}				
				/**-------------验证预交年份是否存在结束--------------*/
				
				/**-------------验证预交班型是否存在开始--------------*/
				if(pageData.getString("YJ_BANJI_TYPE")!=null&&!"".equals(pageData.getString("YJ_BANJI_TYPE"))){
					sysDicPd.put("NAME", pageData.getString("YJ_BANJI_TYPE"));
					sysDicPd.put("PARENT_ID", "CLASSTYPE");
					jichupd = (PageData) stuInfoService.getSysDictyionaries(sysDicPd);
					if(jichupd==null){
						pageData.put("error", ImportErrorEnum.yjbanxing_input_error.getValue());
						errorList.add(pageData);
						continue;					
					}else{
						pageData.put("YJ_BANJI_TYPE_PKID", jichupd.getString("DICTIONARIES_ID"));
					}
				}else{
					pageData.put("YJ_BANJI_TYPE_PKID", "");
				}				
				/**-------------验证预交班型是否存在结束--------------*/
				
				/**-------------验证预交班型预交年份必须同时存在开始--------------*/
				if(!Tools.isEmpty(pageData.getString("YJ_BANJI_TYPE_PKID"))&&Tools.isEmpty(pageData.getString("YJ_NIANFEN"))){
					pageData.put("error", ImportErrorEnum.yjbxnf_input_error.getValue());
					errorList.add(pageData);
					continue;
				}
				if(Tools.isEmpty(pageData.getString("YJ_BANJI_TYPE_PKID"))&&!Tools.isEmpty(pageData.getString("YJ_NIANFEN"))){
					pageData.put("error", ImportErrorEnum.yjbxnf_input_error.getValue());
					errorList.add(pageData);
					continue;
				}
				
				/**------------验证预交班型预交年份必须同时存在结束--------------*/
				
				
				/**-------------验证应届生、寒假班、暑假班时才可以导入预交开始--------------*/
				if(!"应届生".equals(pageData.getString("CENGCI"))
						&&(!"寒假班".equals(pageData.getString("BANJI_TYPE"))
								||!"暑假班".equals(pageData.getString("BANJI_TYPE")))){
					pageData.put("YJ_BANJI_TYPE_PKID", "");
					pageData.put("YJ_NIANFEN", "");
					
				}
				//判断如果预交已经存在 且和模板中的不同不允许导入
				PageData pdsfz = stuInfoService.getStuinfoBySfz(pageData);
				if(pdsfz!=null){
					if(!"".equals(pageData.getString("YJ_NIANFEN"))
							&&(!pdsfz.getString("YJ_NIANFEN").equals(pageData.getString("YJ_NIANFEN"))
							||!pdsfz.getString("YJ_BANJI_TYPE_PKID").equals(pageData.getString("YJ_BANJI_TYPE_PKID")))){
						pageData.put("error", ImportErrorEnum.yjsfznfbx_input_error.getValue());
						errorList.add(pageData);
						continue;
						
					}
				}
				
				pageData.put("TMPKID", this.get32UUID());
				pageData.put("YJ_STUBM_PKID", pageData.getString("TMPKID"));
				//如果应届生导入模板中有预交年份和班型 数据库中不存在预交入学年份和班型那么覆盖预交
				if(pdsfz!=null){
					if(!Tools.isEmpty(pageData.getString("YJ_NIANFEN"))&&Tools.isEmpty(pdsfz.getString("YJ_NIANFEN"))&&Tools.isEmpty(pdsfz.getString("YJ_BANJI_TYPE_PKID"))){
						pageData.put("YJ_STUBM_PKID", pageData.getString("TMPKID"));
						
					}
				}
				//如果应届生导入模板中有预交入学年份和班型 数据库中存在一样的值那么不覆盖
				if(pdsfz!=null){
					if(!Tools.isEmpty(pageData.getString("YJ_NIANFEN"))&&pdsfz.getString("YJ_NIANFEN").equals(pageData.getString("YJ_NIANFEN"))
							&&pdsfz.getString("YJ_BANJI_TYPE_PKID").equals(pageData.getString("YJ_BANJI_TYPE_PKID"))){
						pageData.put("YJ_NIANFEN", "");
						pageData.put("YJ_BANJI_TYPE_PKID", "");
						pageData.put("YJ_STUBM_PKID", "");
						
					}
				}
				
				/**------------验证应届生、寒假班、暑假班时才可以导入预交结束--------------*/
				
				/**-------------验证邮政编码是否正确开始--------------*/
				regex = "^[0-9]\\d{5}$";
				yzbm = pageData.getString("YOUZHENGBIANMA");
				if(Utils.isNotEmpty(yzbm)){
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(yzbm);
					boolean isMatch = m.matches();
					if(!isMatch){
						pageData.put("error", ImportErrorEnum.yzbmphoneError.getValue());
						errorList.add(pageData);
						continue;
					}
				}
				/**-------------验证邮政编码是否正确结束--------------*/
				
				/**-------------验证第一监护人家庭关系是否存在开始--------------*/
				if(pageData.getString("ONE_JHRGX")!=null&&!"".equals(pageData.getString("ONE_JHRGX"))){
					sysDicPd.put("NAME", pageData.getString("ONE_JHRGX"));
					sysDicPd.put("PARENT_ID", "FAMILY");
					jichupd = (PageData) stuInfoService.getSysDictyionaries(sysDicPd);
					if(jichupd==null){
						pageData.put("error", ImportErrorEnum.family_name_error.getValue());
						errorList.add(pageData);
						continue;					
					}else{
						pageData.put("ONE_JHRGX_PKID", jichupd.getString("DICTIONARIES_ID"));
					}
				}else{
					pageData.put("ONE_JHRGX_PKID", "");
				}				
				/**-------------验证第一监护人家庭关系是否存在结束--------------*/
				
				/**-------------验证第二监护人家庭关系是否存在开始--------------*/
				if(pageData.getString("TWO_JHRGX")!=null&&!"".equals(pageData.getString("TWO_JHRGX"))){
					sysDicPd.put("NAME", pageData.getString("TWO_JHRGX"));
					sysDicPd.put("PARENT_ID", "FAMILY");
					jichupd = (PageData) stuInfoService.getSysDictyionaries(sysDicPd);
					if(jichupd==null){
						pageData.put("error", ImportErrorEnum.family_name_error.getValue());
						errorList.add(pageData);
						continue;					
					}else{
						pageData.put("TWO_JHRGX_PKID", jichupd.getString("DICTIONARIES_ID"));
					}
				}else{
					pageData.put("TWO_JHRGX_PKID", "");
				}				
				/**-------------验证第二监护人家庭关系是否存在结束--------------*/
				
				/**-------------验证了解强化的途径是否存在开始--------------*/
				if(pageData.getString("LJQHTJ")!=null&&!"".equals(pageData.getString("LJQHTJ"))){
					sysDicPd.put("NAME", pageData.getString("LJQHTJ"));
					sysDicPd.put("PARENT_ID", "UNDWAY");
					jichupd = (PageData) stuInfoService.getSysDictyionaries(sysDicPd);
					if(jichupd==null){
						pageData.put("error", ImportErrorEnum.ljqhtj_input_error.getValue());
						errorList.add(pageData);
						continue;					
					}else{
						pageData.put("LJQHTJ_PKID", jichupd.getString("DICTIONARIES_ID"));
					}
				}else{
					pageData.put("LJQHTJ_PKID", "");
				}				
				/**-------------验证了解强化的途径是否存在结束--------------*/
				
				/**-------------验证合作学校是否存在开始--------------*/
				pageData.put("WHKXUEXIAONAME", pageData.getString("WHKXUEXIAO"));
				if(pageData.getString("WHKXUEXIAO")!=null&&!"".equals(pageData.getString("WHKXUEXIAO"))){
					sysDicPd.put("SCHOOLNAME", pageData.getString("WHKXUEXIAO"));
					jichupd = (PageData) partSchoolService.getPartSchoolByName(sysDicPd);
					if(jichupd==null){
						pageData.put("error", ImportErrorEnum.whkxx_input_error.getValue());
						errorList.add(pageData);
						continue;					
					}else{
						pageData.put("WHKXUEXIAO", jichupd.getString("PKID"));
					}
				}else{
					pageData.put("WHKXUEXIAO", "");
				}				
				/**-------------验证合作学校是否存在结束--------------*/
				
				/**-------------验证联考分数是否正确开始-------------*/
				
				/*regex = "^(0|([1-9][0-9]*))(\\.[0-9])?$";*///一位小数
				regex = "^(0|([1-9]\\d{0,18}))(\\.\\d{1,2})?$";//两位小数
				String LKFS = pageData.getString("LKFS");
				pageData.put("LKFSNAME", pageData.getString("LKFS"));
				if(Utils.isNotEmpty(LKFS)){
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(LKFS);
					boolean isMatch = m.matches();
					if(!isMatch){
						pageData.put("error", ImportErrorEnum.lkfsError.getValue());
						errorList.add(pageData);
						continue;
					}
				}
				/**-------------验证联考分数是否正确结束-------------*/
				
				/**-------------判断备注长度不能超过500个字开始--------------*/
				REMARKS1 = pageData.getString("REMARKS1");
				REMARKS2 = pageData.getString("REMARKS2");
				REMARKS3 = pageData.getString("REMARKS3");
				if(!Tools.isEmpty(REMARKS1)){
					if(REMARKS1.length()>500){
						pageData.put("error", ImportErrorEnum.remarks_error.getValue());
						errorList.add(pageData);
						continue;
					}
				}
				if(!Tools.isEmpty(REMARKS2)){
					if(REMARKS2.length()>500){
						pageData.put("error", ImportErrorEnum.remarks_error.getValue());
						errorList.add(pageData);
						continue;
					}
				}
				if(!Tools.isEmpty(REMARKS2)){
					if(REMARKS3.length()>500){
						pageData.put("error", ImportErrorEnum.remarks_error.getValue());
						errorList.add(pageData);
						continue;
					}
				}
				/**-------------判断备注长度不能超过500个字结束--------------*/
				
				/**-------------验证已学过科目开始--------------*/
				kemus = pageData.getString("KEMU"); 
				pageData.put("KEMUNAME", pageData.getString("KEMU"));
				PageData pdKemu = null;
				String kemuArr = "";
				if(!"".equals(kemus)){
					kemus = kemus.replace("，", ",");
					String[] kemu_arr = kemus.split(",");
					for(String kemu : kemu_arr){
						if(kemu!=null&&!"".equals(kemu)){
							jichupd.put("NAME", kemu);
							pdKemu = stuInfoService.getSubByName(jichupd);
							if(pdKemu!=null){								
								kemuArr+=pdKemu.getString("PKID")+",";	
							}else{
								pageData.put("error", ImportErrorEnum.kemus_error.getValue());
								errorList.add(pageData);
								continue;
							}
							
						}
					}
					if(kemuArr!=""){
						kemuArr = kemuArr.substring(0, kemuArr.length()-1);
					}
					pageData.put("KEMU", kemuArr);
				}
				/**-------------验证已学过科目是否正确结束--------------*/				
				
				/**-------------验证校考成绩是否正确开始--------------*/
				xkmarks = pageData.getString("XK_MARK");
				pageData.put("XK_MARK_NAME", xkmarks);
				if(!"".equals(xkmarks)){
					xkmarks = xkmarks.replace("，", ",");
					String[] xkmark_arr = xkmarks.split(",");
					PageData pdXmark = null;
					String XK_MARK = "";
					for(String xkmark : xkmark_arr){
						pdXmark = stuInfoService.getMeiByName(xkmark);
						if(pdXmark==null){
							pageData.put("error", ImportErrorEnum.xkmarks_error.getValue());
							errorList.add(pageData);
							continue;
						}else{
							XK_MARK += pdXmark.getString("PKID")+",";
						}						
					}
					if(XK_MARK!=""){
						XK_MARK = XK_MARK.substring(0, XK_MARK.length()-1);
					}
					pageData.put("XK_MARK", XK_MARK);
					
				}
				
				
				/**-------------验证高一入学时间、自何时学习美术开始--------------*/
				
				//regex = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
				regex = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29) ";
				String GYRXSJ = pageData.getString("GYRXSJ");
				
				if(!Tools.isEmpty(GYRXSJ)){
					String GYRXSJT = GYRXSJ+"-01";
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(GYRXSJT);
					boolean isMatch = m.matches();
					if(!isMatch){
						pageData.put("error", ImportErrorEnum.gyrxsj_name_error.getValue());
						errorList.add(pageData);
						continue;
					}else{
						GYRXSJ = sdf.format(sdf.parse(GYRXSJ));
						pageData.put("GYRXSJ", GYRXSJ);
					}	
				}
				String STARTMEISHU = pageData.getString("STARTMEISHU");
				
				if(!Tools.isEmpty(STARTMEISHU)){
					String STARTMEISHUT = STARTMEISHU+"-01";
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(STARTMEISHUT);
					boolean isMatch = m.matches();
					if(!isMatch){
						pageData.put("error", ImportErrorEnum.zhsxxms_name_error.getValue());
						errorList.add(pageData);
						continue;
					}else{
						STARTMEISHU = sdf.format(sdf.parse(STARTMEISHU));
						pageData.put("STARTMEISHU", STARTMEISHU);
					}	
				}
				/**-------------验证高一入学时间、自何时学习美术结束--------------*/
				
				/**-------------根据层次置空开始--------------*/
				pageData.put("KSNUMBERNAME", pageData.getString("KSNUMBER"));
					if("应届生".equals(pageData.getString("CENGCI"))){
						/*pageData.put("OLD_BANJI", "");//原班级
	*/					pageData.put("LKFS", "");//联考分数
						pageData.put("KSNUMBER", "");//考生号
						pageData.put("XK_MARK", "");//校考成绩
					}else if("本校复课生".equals(pageData.getString("CENGCI"))){
						pageData.put("STARTMEISHU", "");//自何时学习美术
						pageData.put("KEMU", "");//已经学过科目
						pageData.put("YJ_NIANFEN", "");//预交年份
						pageData.put("YJ_BANJI_TYPE_PKID", "");//预交班型
					}else if("外校复课生".equals(pageData.getString("CENGCI"))){
						/*pageData.put("OLD_BANJI", "");//原班级
	*/					pageData.put("STARTMEISHU", "");//自何时学习美术
						pageData.put("KEMU", "");//已经学过科目
						pageData.put("YJ_NIANFEN", "");//预交年份
						pageData.put("YJ_BANJI_TYPE_PKID", "");//预交班型
					}
					
				/**-------------根据层次置空结束-------------*/
					
				/**-------------身份证号 入学年份 班型 验证不能重复开始--------------*/
				IdCardIsDuplicated=false;
				//遍历正确数据集合，和后边的数据进行身份证号验重
				Iterator<PageData> it = successList.iterator();
				PageData successpd = null;
				while (it.hasNext()) {
					successpd = it.next();
					if (successpd.getString("SHENFENZHENGHAO").equals(pageData.getString("SHENFENZHENGHAO"))&&
							successpd.getString("RXNIANFEN_PKID").equals(pageData.getString("RXNIANFEN_PKID"))&&
							successpd.getString("BANJI_TYPE_PKID").equals(pageData.getString("BANJI_TYPE_PKID"))) {
						IdCardIsDuplicated = true;
						// 把重复的数据放到errorlist 并从正确数据集合中删除
						pageData.put("error",
								ImportErrorEnum.bxrnsfn_add_error.getValue());
						successpd.put("error",
								ImportErrorEnum.bxrnsfn_add_error.getValue());
						errorList.add(pageData);
						errorList.add(successpd);
						it.remove();
						break;
					}
				}				
				if(IdCardIsDuplicated==true){
					continue;
				}
				
				/**-------------身份证号 入学年份 班型 验证不能重复结束--------------*/
				
				/**-------------验证学号不能重复开始--------------*/
				xueHaoIsDuplicated = false;
				Iterator<PageData> it1 = successList.iterator(); 
				while (it1.hasNext()) {
					successpd = it1.next();
					if (Tools.notEmpty(successpd.getString("XUEHAO")) && Tools.notEmpty(pageData.getString("XUEHAO")) && successpd.getString("XUEHAO").equals(
							pageData.getString("XUEHAO"))) {
						xueHaoIsDuplicated = true;
						// 把重复的数据放到errorlist 并从正确数据集合中删除
						pageData.put("error",
								ImportErrorEnum.stuxuehao_cffail.getValue());
						successpd.put("error",
								ImportErrorEnum.stuxuehao_cffail.getValue());
						errorList.add(pageData);
						errorList.add(successpd);
						it1.remove();
						break;
					}
				}
				if(xueHaoIsDuplicated==true){
					continue;
				}
				/**-------------验证学号不能重复结束--------------*/
				/*
				 * 获取当前用户
				 */
				Session session = Jurisdiction.getSession();
				User user = (User)session.getAttribute(Const.SESSION_USER);
				pageData.put("CAOZUOREN", user.getUSER_ID());
				pageData.put("PKID", this.get32UUID());
				//pageData.put("TMPKID", this.get32UUID());				
				pageData.put("ZXZT", "ZX");//在学状态默认在学
				pageData.put("ZHUANGTAI",1 );//状态  1正常  0删除 
				pageData.put("PWD",new SimpleHash("SHA-1","","123456").toString());
				successList.add(pageData);
			}
			//end 业务处理
			for (PageData pageData2 : successList) {
				// 插入到t_stuinfo
				batchList.add(pageData2);
				try {
					if (batchList.size() % 100 == 0) {
						batchSaveStuInfo(batchList, methodurl, rep);
					}
				} catch (Exception e) {
					e.printStackTrace();
					if ("批量异常".equals(e.getMessage())) {
						for (PageData pageData3 : batchList) {
							pageData3.put("error",
									ImportErrorEnum.insert_fail.getValue());
							errorList.add(pageData3);
						}
						batchList.clear();// 清空
						continue;
					} else {
						pageData2.put("error",
								ImportErrorEnum.insert_fail.getValue());
						errorList.add(pageData2);
						batchList.clear();// 清空
						continue;
					}
				}
			}
			try {
				if (batchList.size()>0 && batchList.size()<100) {
					batchSaveStuInfo(batchList, methodurl, rep);
				}
			} catch (Exception e) {
				for (PageData pageData3 : batchList) {
					pageData3.put("error",
							ImportErrorEnum.insert_fail.getValue());
					errorList.add(pageData3);
				}
				batchList.clear();// 清空
				e.printStackTrace();
			}			
			map.put("totalCount", datas.size());//总记录数
			map.put("successCount", datas.size() - errorList.size());//成功数
			map.put("failCount", errorList.size());//失败记录数
			
			String mulu = null;
			if (errorList.size()>0) {
				//生成下载文件,提供下载
				mulu = itemListParseExcel.writeExcel(errorList,null,null);//pkid
			}			
			map.put("mulu", mulu);
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		   }catch (Exception e) {
				e.printStackTrace();
				map.put("result", false);//结果
				map.put("errorinfo", e.getMessage());//错误信息
		   }
			
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	private void batchSaveStuInfo(List<PageData> batchList, String methodurl,
			Map<String, String> rep) throws Exception {
		stuInfoService.batchSaveStu(batchList,methodurl);
		batchList.clear();
	}

}
