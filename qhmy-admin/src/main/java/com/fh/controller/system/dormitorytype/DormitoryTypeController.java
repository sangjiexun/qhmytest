package com.fh.controller.system.dormitorytype;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Case;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.dormitorytype.DormitoryTypeManager;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.util.PageData;

/**
 * 
 * <p>标题:DormitoryTypeController</p>
 * <p>描述:宿舍类型管理</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 王宁
 * @date 2018年3月9日 下午5:04:49
 */
@Controller
@RequestMapping(value="/dormitorytype")
public class DormitoryTypeController extends BaseController {
	@Resource(name="dormitorytypeService")
	private DormitoryTypeManager  dormitorytypeService;
	@Resource(name="stuInfoService")
	private StuInfoManager  stuInfoService;
	/**
	 * 
	 * <p>描述:</p>
	 * @author Administrator 王宁
	 * @date 2018年3月9日 下午5:05:47
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/dormitorytype_list.php")
	public ModelAndView dormitorytype_list() throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/dormitorytype/dormitorytype_list");
		return mv;
	}
	/**
	 * 
	 * <p>描述:获取表格数据</p>
	 * @author Administrator 王宁
	 * @date 2018年3月9日 下午6:02:12
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/dormitorytypeTable.json")
	public ModelAndView dormitorytypeTable(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("keyWord", pd.getString("keyWord").trim());
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
		page.setPd(pd);
		List<PageData> list = dormitorytypeService.dormitorytype_list(page);
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:跳转到编辑新增页面</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午1:48:24
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit.json")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String PKID = pd.getString("PKID");
		PageData pd_dormitorytype = null;
		String pics = null;
		PageData pd_pic0 = null;
		PageData pd_pic1 = null;
		PageData pd_pic2 = null;
		PageData pd_pic3 = null;
		if(!"".equals(PKID) && !"null".equals(PKID)){//表示是编辑页面
			pd_dormitorytype = dormitorytypeService.getDormitorytype(pd);
			if(pd_dormitorytype.getString("PICS")!=null){
				switch (pd_dormitorytype.getString("PICS").split(",").length){
				case 4:
					pics = pd_dormitorytype.getString("PICS").split(",")[3];
					pd_pic3 = new PageData();
					pd_pic3.put("PIC", pics);
				case 3:
					pics = pd_dormitorytype.getString("PICS").split(",")[2];
					pd_pic2 = new PageData();
					pd_pic2.put("PIC", pics);
				case 2:
					pics = pd_dormitorytype.getString("PICS").split(",")[1];
					pd_pic1 = new PageData();
					pd_pic1.put("PIC", pics);
				case 1:
					pics = pd_dormitorytype.getString("PICS").split(",")[0];
					pd_pic0 = new PageData();
					pd_pic0.put("PIC", pics);
				default:
					break;
				}
			}
		}
		mv.addObject("pd_pic0", pd_pic0);
		mv.addObject("pd_pic1", pd_pic1);
		mv.addObject("pd_pic2", pd_pic2);
		mv.addObject("pd_pic3", pd_pic3);
		mv.addObject("pd_dormitorytype", pd_dormitorytype);
		mv.addObject("pd", pd);
		mv.setViewName("system/dormitorytype/dormitorytype_edit");
		return mv;
	}
	@ResponseBody
	@RequestMapping(value = "/getDT_NAME.json")
	public ModelAndView getDT_NAME() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		//查询该类型名称是否已存在
		PageData pd_dt_name = dormitorytypeService.getDT_NAME(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		if(pd_dt_name!=null){
			if("".equals(pd.getString("PKID"))){//表示新增
				map.put("rst", "success");
			}else if(!pd_dt_name.getString("PKID").equals(pd.getString("PKID"))){//表示编辑
				map.put("rst", "success");
			}
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:操作宿舍类型信息</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午3:16:23
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/edit.json")
	public ModelAndView edit() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		String PKID = pd.getString("PKID");
		if(!"".equals(PKID) && !"null".equals(PKID)){//表示是编辑页面
			dormitorytypeService.update(pd);
		}else{//表示新增
			dormitorytypeService.save(pd);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rst", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:更新启用状态</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午3:41:34
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateIsUse.json")
	public ModelAndView updateIsUse() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		dormitorytypeService.updateIsUse(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rst", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:删除宿舍信息</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午3:49:20
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/delete.json")
	public ModelAndView delete() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		//查询该宿舍类型信息是否已被使用  如果发布缴费中已经使用或者宿舍配置中已经使用则不允许删除
		PageData pd_dormTypeUse = dormitorytypeService.getDormUse(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		if(pd_dormTypeUse!=null){//已被使用
			map.put("rst", "error");
		}else{
			dormitorytypeService.delete(pd);
			map.put("rst", "success");
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	  * 
	  * <p>描述:保存头像到本地</p>
	  * @author Administrator 康程亮
	  * @date 2017年8月3日 上午9:43:33
	  * @param myfiles
	  * @param request
	  * @param response
	  * @throws Exception
	  */
	@RequestMapping(value = "/uploadPicture.json")
	public void upload(MultipartHttpServletRequest multipartRequest,  
           HttpServletResponse response,ModelMap model,HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("utf-8");
		PageData pd = this.getPageData();
		String tempImg_path = "uploadFiles/uploadImgs/temp/";
		String uPath = request.getSession().getServletContext()
				.getRealPath("/");
		//支持多文件上传，其实这里只有一个图片
		String pic_path = null;
		String result = "error";
		for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {    
	        String key = (String) it.next();    
	        MultipartFile imgFile = multipartRequest.getFile(key);
	        String childFileName = pd.getString("name");//获取子项目的象征id
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");  
	        File dir = new File(uPath + tempImg_path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			Date date = new Date(); 
			if (imgFile != null && !imgFile.isEmpty()) {
				int index = imgFile.getOriginalFilename().lastIndexOf('.');
				String picName = childFileName+"-"+ sdf.format(date)
						+ imgFile.getOriginalFilename().substring(index);;
				pic_path = tempImg_path + picName;
				imgFile.transferTo(new File(uPath + tempImg_path + picName));
				result= "success";
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("result", result);
		data.put("path", pic_path);
		PrintWriter printWriter = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(data);
		printWriter
				.write("<html><head><meta charset=\"utf-8\"/></head><body><pre>"
						+ json + "</pre></body></html>");
		printWriter.flush();
		printWriter.close();
	}
}