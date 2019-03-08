package com.fh.controller.importdorminfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.excel.DormInfoListParseExcel;
import com.fh.excel.ImportErrorEnum;
import com.fh.service.importitem.itemlist.ItemListManager;
import com.fh.service.system.dormitoryInfo.DormitoryInfoManager;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.upload.UploadConst;
import com.keman.zhgd.common.util.Tools;

/**
 * 导入学生信息
 * <p>标题:ImportStuInfoController</p>
 * <p>描述:</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 康程亮
 * @date 2017年8月18日 下午5:05:42
 */
@Controller
@RequestMapping(value="/importdorm")
public class ImportDromInfoController extends BaseController {
	
	@Resource
	private ItemListManager itemListManager;
	@Resource
	private StuInfoManager stuInfoService;
	
	@Resource(name="dormitoryInfoService")
	private DormitoryInfoManager  dormitoryInfoService;
	
	/**
	 * 
	 * <p>描述:学生信息上传页面</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月18日 下午5:04:00
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importdorminfo.json")
	public ModelAndView importStuInfo() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.setViewName("system/import/importdorminfo");
		mv.addObject("uuid", this.get32UUID());
		//mv.addObject("item_pkid", pd.getString("item_pkid"));
		//mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:学生名单解析</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月16日 下午10:11:07
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/import-dorminfo-parse")
	public ModelAndView importListParse(Page page) throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		File file = null;
		
		try {
			//得到上传的文件
			String basePath = UploadConst.uploadPathMap.get(UploadConst.DORM_INFO).getAbsolutePath() + "\\"+pd.getString("mulu")+"\\";
			basePath = basePath + pd.getString("fileName");
			file = new File(basePath);
			
			//解析excel
			DormInfoListParseExcel dormInfoListParseExcel = new DormInfoListParseExcel();
			List<PageData> datas = dormInfoListParseExcel.getDormInfoList(file,pd.getString("fileName"));
			//end 解析excel
			
			
			//业务处理
			PageData studentPageData = null;
			//stupd 用于判断学生是否存在
			PageData stupd=null;
			List<PageData> errorList = new ArrayList<PageData>();//记录错误的导入信息
			List<PageData> successList=new ArrayList<PageData>();//记录正常数据
			List<PageData> batchList=new ArrayList<PageData>();//批量提交的数据
			boolean excelDormIsDuplicated=false;
			for (PageData pageData : datas) {
				pageDatatrim(pageData);//去掉前后空格
				if ((Tools
								.isEmpty(pageData.getString("SHENFENZHENGHAO"))
								|| Tools.isEmpty(pageData
										.getString("XIAOQU"))
								|| Tools.isEmpty(pageData.getString("SUSHELOU"))
								|| Tools.isEmpty(pageData.getString("LOUCENG"))
								|| Tools.isEmpty(pageData.getString("FANGJIAN"))
								|| Tools.isEmpty(pageData.getString("CHUANGHAO"))
								|| Tools.isEmpty(pageData.getString("RUXUENIANFEN"))
								|| Tools.isEmpty(pageData.getString("BANXING"))

						)) {
					pageData.put("error", ImportErrorEnum.core_data_empty.getValue());
					errorList.add(pageData);
					continue;
				}
				
				//根据学号和身份证号 查询学生档案表,判断学生是否存在
				stupd=new PageData();
				stupd.put("cardNo", pageData.getString("SHENFENZHENGHAO"));
				stupd.put("RUXUENIANFEN", pageData.getString("RUXUENIANFEN"));
				stupd.put("BANXING", pageData.getString("BANXING"));

				studentPageData = itemListManager.daoruqueryStu(stupd);
				
				/*
				 * 判断填写的身份证号是否有误
				 */
				if (studentPageData == null ) {//为空 代表库里无此学生
					pageData.put("error", ImportErrorEnum.stunotexist.getValue());
					errorList.add(pageData);
					continue;
				}else{
					pageData.put("BX", studentPageData.getString("BANJI_TYPE_PKID"));
					pageData.put("RXNF", studentPageData.getString("RXNIANFEN_PKID"));
					pageData.put("BMPKID", studentPageData.getString("BMPKID"));
					pageData.put("WHKXUEXIAO", studentPageData.getString("WHKXUEXIAO"));
				}
				//pageData.put("STU_DEPARTMENT_PKID", studentPageData.getString("DEPARTMENT_PKID"));
				pageData.put("T_STUDENT_PKID", studentPageData.getString("PKID"));
				
				/*
				 * 判断是否允许导入（缴费名单缴费项为子项，住宿费，限制数量，实缴大于0 不允许导入）
				 */
				
				/*List<PageData> infoList = dormitoryInfoService.getinfoList(pageData);
				if(infoList!=null && infoList.size()>0){
					pageData.put("error", ImportErrorEnum.dzxexist.getValue());
					errorList.add(pageData);
					continue;
				}*/
				
				/*
				 * 判断当前学生有无宿舍绑定关系
				 */
				PageData stuDrom = dormitoryInfoService.getdaoruStuDrom(studentPageData);
				if(stuDrom!=null){//已经有宿舍绑定关系
					pageData.put("error", ImportErrorEnum.stuinfochongfu.getValue());
					errorList.add(pageData);
					continue;
				}
				
				/*
				 * 判断输入的 宿舍信息是否正确
				 */
				List<PageData> dromList = dormitoryInfoService.getdaoruDromList(pageData);
				String excelDormName =pageData.getString("XIAOQU")+"-"+pageData.getString("SUSHELOU")+"-"+pageData.getString("LOUCENG")+"-"+pageData.getString("FANGJIAN")+"-"+pageData.getString("CHUANGHAO");
				Boolean nameRight = false;
				PageData dpd =null;
				PageData dormPd =null;
				for (PageData pageData2 : dromList) {
					dpd = dormitoryInfoService.getDormName(pageData2);
					if(excelDormName.equals(dpd.getString("DORMNAME"))){//代表用户输入对了
						nameRight=true;
						dormPd=pageData2;
						pageData.put("DORM_PKID", pageData2.getString("PKID"));
						pageData.put("T_STUDENT_DORM_TYPE_PKID", pageData2.getString("T_STUDENT_DORM_TYPE_PKID"));
						//pageData.put("DORM_DEPARTMENT_ID", pageData2.getString("DEPARTMENT_ID"));
						
						break;
					}
				}
				if(nameRight==false){
					pageData.put("error", ImportErrorEnum.dormnamewrong.getValue());
					errorList.add(pageData);
					continue;
				}
				/*
				 * 判断学生性别和宿舍的性别是否一致
				 */
				if(!("M".equals(studentPageData.getString("XINGBIE")) && "1".equals(dormPd.getString("SD_SEX")) || "W".equals(studentPageData.getString("XINGBIE")) && "0".equals(dormPd.getString("SD_SEX")))){
					pageData.put("error", ImportErrorEnum.stucannotmatchdormxingbie.getValue());
					errorList.add(pageData);
					continue;
				}
				pageData.put("used_type", 1);
				pageData.put("handle_resource", "导入宿舍信息");
				
				
				
				//判断excel里同一个床有没有给了不同的人
				excelDormIsDuplicated=false;
				//遍历正确数据集合，和后边的数据进行身份证号验重
				Iterator<PageData> it = successList.iterator();
				PageData successpd = null;
				while (it.hasNext()) {
					successpd = it.next();
					String DormName =successpd.getString("XIAOQU")+"-"+successpd.getString("SUSHELOU")+"-"+successpd.getString("LOUCENG")+"-"+successpd.getString("FANGJIAN")+"-"+successpd.getString("CHUANGHAO");
					if(excelDormName.equals(DormName)){
						excelDormIsDuplicated = true;
						// 把重复的数据放到errorlist 并从正确数据集合中删除
						pageData.put("error",
								ImportErrorEnum.exceldormchongfu.getValue());
						successpd.put("error",
								ImportErrorEnum.exceldormchongfu.getValue());
						errorList.add(pageData);
						errorList.add(successpd);
						it.remove();
						break;
					}
					/*
					 * 同一个人分了多个床
					 */
					if((successpd.getString("SHENFENZHENGHAO").equals(pageData.getString("SHENFENZHENGHAO")))&&(successpd.getString("RUXUENIANFEN").equals(pageData.getString("RUXUENIANFEN")))&&(successpd.getString("BANXING").equals(pageData.getString("BANXING"))) ){
						excelDormIsDuplicated = true;
						pageData.put("error",
								ImportErrorEnum.stualreadyhasstu.getValue());
						successpd.put("error",
								ImportErrorEnum.stualreadyhasstu.getValue());
						errorList.add(pageData);
						errorList.add(successpd);
						it.remove();
						break;
					}
					
				}
				
				if(excelDormIsDuplicated==true){
					continue;
				}
				
				successList.add(pageData);
			}
			//end 业务处理
			for (PageData pageData2 : successList) {
				// 插入到t_stuinfo
				batchList.add(pageData2);
				try {
					if (batchList.size() % 100 == 0) {
						dormitoryInfoService.updatebatchBindStuAndDorm(batchList);
						batchList.clear();
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
						continue;
					}
				}
			}
			try {
				if (batchList.size()>0 && batchList.size()<100) {
					dormitoryInfoService.updatebatchBindStuAndDorm(batchList);
					batchList.clear();
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
				mulu = dormInfoListParseExcel.writeExcel(errorList,null,null);//pkid
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


	/**
	 * 
	 * <p>描述:去掉前后空格</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月18日 上午3:03:43
	 * @param pageData
	 */
	private void pageDatatrim(PageData pageData) {
		// TODO Auto-generated method stub
		pageData.put("item", pageData.getString("itemName").trim());
		pageData.put("stuName", pageData.getString("stuName").trim());
		pageData.put("stuNumber", pageData.getString("stuNumber").trim());
		pageData.put("cardNo", pageData.getString("cardNo").trim());
		pageData.put("discountMoney", pageData.getString("discountMoney").trim());
	}

	/**
	 * 
	 * <p>描述:是否都为空</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月18日 上午2:55:12
	 * @return
	 */
	private boolean isAllEmpty(PageData pageData){
		if(Tools.isEmpty(pageData.getString("itemName"))
			&& Tools.isEmpty(pageData.getString("stuName"))
			&& Tools.isEmpty(pageData.getString("stuNumber"))
			&& Tools.isEmpty(pageData.getString("cardNo"))
			&& Tools.isEmpty(pageData.getString("discountMoney"))  ){
			return true;
		}
		return false;
	}
	
	
	
	
}
