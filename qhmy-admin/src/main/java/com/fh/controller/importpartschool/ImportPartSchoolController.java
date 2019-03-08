package com.fh.controller.importpartschool;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.excel.ImportErrorEnum;
import com.fh.excel.ItemListParseExcel;
import com.fh.service.system.partschool.PartSchoolManager;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Utils;
import com.fh.util.upload.UploadConst;
import com.keman.zhgd.common.util.Tools;


/**
 * <p>标题:AuthorityManageController</p>
 * <p>描述:导入合作学校</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator wzz
 * @date 2018年11月27日 下午5:31:16
 */
@Controller
@RequestMapping(value="/importPartSchool")
public class ImportPartSchoolController extends BaseController{
	
	@Resource(name="partSchoolService")
	private PartSchoolManager partSchoolService;
	
	
	/**
	 * <p>描述:导入合作学校 首页</p>
	 * @author Administrator wzz
	 * @date 2018年11月27日 下午5:34:27
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importpartSchool.json")
	public ModelAndView importList() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.setViewName("system/import/importPartSchool");
		mv.addObject("uuid", this.get32UUID());
		mv.addObject("pd", pd);
		return mv;
	}
	
	
	
	/**
	 * 
	 * <p>描述:合作学校名单</p>
	 * @author Administrator wzz
	 * @date 2018年11月27日 下午10:11:07
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/import-list-parse")
	public ModelAndView importListParse(Page page) throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		File file = null;
		
		try {
			//得到上传的文件
			String basePath = UploadConst.uploadPathMap.get(UploadConst.PART_SCHOOL).getAbsolutePath() + "\\"+pd.getString("mulu")+"\\";
			basePath = basePath + pd.getString("fileName");
			file = new File(basePath);
			
			//解析excel
			ItemListParseExcel itemListParseExcel = new ItemListParseExcel();
			List<PageData> datas = itemListParseExcel.getPartSchoolList(file,pd.getString("fileName"));
			//end 解析excel
			boolean IdCardIsDuplicated=false;
			List<PageData> errorList = new ArrayList<PageData>();//记录错误的导入信息
			List<PageData> successList=new ArrayList<PageData>();//记录正常数据
			PageData pd_classUse = null;
			String reg = "";
			for (PageData pageData : datas) {
				/**-------------验证合作学校名称不能为空开始--------------*/
				if(Tools.isEmpty(pageData.getString("SCHOOLNAME"))){
					pageData.put("error", ImportErrorEnum.schoolname_data_empty.getValue());
					errorList.add(pageData);
					continue;
				}
				/**-------------验证合作学校名称不能为空结束--------------*/
			
				/**-------------验证合作学校名称与系统中的不能重复开始--------------*/
					pd_classUse = partSchoolService.getPartSchoolByName(pageData);
					if(pd_classUse!=null){
						pageData.put("error", ImportErrorEnum.schoolname_data_exist.getValue());
						errorList.add(pageData);
						continue;
					}
				/**-------------验证合作学校名称与系统中的不能重复结束--------------*/
				
				/**-------------验证定金只能输入两位小数或者正整数开始--------------*/
				//reg = "^[0-9]+(.[0-9]{1,2})?$";
				reg = "^(0|([1-9]\\d{0,18}))(\\.\\d{1,2})?$";
				String DINGJIN = pageData.getString("DINGJIN");
				if(Utils.isNotEmpty(DINGJIN)){
					Pattern p = Pattern.compile(reg);
					Matcher m = p.matcher(DINGJIN);
					boolean isMatch = m.matches();
					if(!isMatch){
						pageData.put("error", ImportErrorEnum.dingjin_error.getValue());
						errorList.add(pageData);
						continue;
					}
				}
				/**-------------验证定金只能输入两位小数或者正整数结束--------------*/
				
				
				/**-------------遍历正确数据集合，和后边的数据进行学校名称验重开始--------------*/
				IdCardIsDuplicated=false;
				//遍历正确数据集合，和后边的数据进行身份证号验重
				Iterator<PageData> it = successList.iterator();
				PageData successpd = null;
				while (it.hasNext()) {
					successpd = it.next();
					if (Tools.notEmpty(successpd.getString("SCHOOLNAME")) && Tools.notEmpty(pageData.getString("SCHOOLNAME")) && successpd.getString("SCHOOLNAME").equals(
							pageData.getString("SCHOOLNAME"))) {
						IdCardIsDuplicated = true;
						// 把重复的数据放到errorlist 并从正确数据集合中删除
						pageData.put("error",
								ImportErrorEnum.schoolname_cffail.getValue());
						successpd.put("error",
								ImportErrorEnum.schoolname_cffail.getValue());
						errorList.add(pageData);
						errorList.add(successpd);
						it.remove();
						break;
					}
				}				
				if(IdCardIsDuplicated==true){
					continue;
				}
				/**-------------遍历正确数据集合，和后边的数据进行学校名称验重结束--------------*/
				
				pageData.put("PKID",this.get32UUID());
				successList.add(pageData);
			}
			
			partSchoolService.batchSavePart(successList);

			//end 业务处理			
 			map.put("totalCount", datas.size());//总记录数
			map.put("successCount", datas.size() - errorList.size());//成功数
			map.put("failCount", errorList.size());//失败记录数
			
			String mulu = null;
			if (errorList.size()>0) {
				//生成下载文件,提供下载
				mulu = itemListParseExcel.writePartSchoolExcel(errorList);//pkid
			}
			
			map.put("mulu", mulu);
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

}
