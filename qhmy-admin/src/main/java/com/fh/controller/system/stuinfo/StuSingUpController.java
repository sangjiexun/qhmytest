package com.fh.controller.system.stuinfo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.system.paymanage.PayManageManager;
import com.fh.service.system.stuinfo.StuSignSupManager;
import com.fh.util.Const;
import com.fh.util.ImgUtils;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 
 * <p>
 * 标题:StuSingUpController
 * </p>
 * <p>
 * 描述:学生信息-报名
 * </p>
 * <p>
 * 组织:河北科曼信息技术有限公司
 * </p>
 * 
 * @author Administrator 任笑达
 * @date 2018年11月28日 上午9:39:01
 */
@Controller
@RequestMapping("/signUp")
public class StuSingUpController extends BaseController {

	@Autowired
	private StuSignSupManager StuSignUpService;
	@Resource(name = "payManageService")
	private PayManageManager payManageService;

	/**
	 * 
	 * <p>
	 * 描述:去报名页面
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月28日 下午2:31:39
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sign_list.php")
	public ModelAndView sign_list() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		List<PageData> jtgx = null;// 获取家庭关系
		List<PageData> qhtj = null;// 了解强化途径
		List<PageData> whkSchool = null;// 文化课学校
		List<PageData> xkcj = null;// 获取校考成绩院校
		List<PageData> cengci = null;// 获取层次
		List<PageData> rxnf = null;// 获取入学年份
		List<PageData> banXing = null;// 班型
		List<PageData> banXingYu = null;// 预交班型
		List<PageData> banji = null;// 班级
		List<PageData> kemus = null;// 科目

		jtgx = this.StuSignUpService.getJHRGX(pd);
		qhtj = this.StuSignUpService.getLJQHTJ(pd);
		whkSchool = this.StuSignUpService.getWhkSchool(pd);
		xkcj = this.StuSignUpService.getXKCJ(pd);
		cengci = this.StuSignUpService.getCengCi(pd);
		rxnf = this.StuSignUpService.getRXNF(pd);
		banXing = this.StuSignUpService.getBanJiType(pd);
		banXingYu = this.StuSignUpService.getYuBanJiType(pd);
		banji = this.StuSignUpService.getBanJis(pd);

		kemus = this.StuSignUpService.getKemu(pd);
		mv.addObject("kemus", kemus);

		mv.addObject("jtgx", jtgx);
		mv.addObject("qhtj", qhtj);
		mv.addObject("whkSchool", whkSchool);
		mv.addObject("xkcj", xkcj);
		mv.addObject("cengci", cengci);
		mv.addObject("rxnf", rxnf);
		mv.addObject("banji", banji);
		mv.addObject("banXing", banXing);
		mv.addObject("banXingYu", banXingYu);
		mv.setViewName("/system/stu_singup/singupIndex");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:通过身份证号查询学生信息
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月28日 下午2:31:54
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getStuInFo.json")
	@ResponseBody
	public ModelAndView getStuInFo() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		PageData info = null;
		info = this.StuSignUpService.getStuInfoBySfz(pd);
		PageData yujiao = null;
		yujiao = this.StuSignUpService.getYuJiaoren(pd);
		System.out.println("yujiao>>>" + yujiao);
		if (yujiao != null) {
			map.put("res_yj", "SUCCESS");
			map.put("str_yj", yujiao);
		} else {
			map.put("res_yj", "ERROR");
		}

		if (info != null) {
			map.put("res", "SUCCESS");
			map.put("str", info);

		} else {
			map.put("res", "ERROR");
			map.put("sfz", pd.getString("SHENFENZHENGHAO"));
		}

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	/**
	 * 
	 * <p>
	 * 描述:获取班级下拉列表
	 * </p>
	 * 
	 * @author wn 王宁
	 * @date 2018年12月19日 下午4:19:42
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getClassList.json")
	@ResponseBody
	public ModelAndView getClassList() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();// GRADE
		List<PageData> list = new ArrayList<>();
		// 根据入学年份查找上一年的入学年份
		PageData pd_grade_last = payManageService.getLastGrade(pd);
		if (pd_grade_last != null) {
			pd.put("GRADE", pd_grade_last.getString("DICTIONARIES_ID"));
		} else {
			pd.put("GRADE", "");
		}
		list = StuSignUpService.getlastClassList(pd);
		jsonmap.put("list", list);
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:保存学生信息
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月28日 下午2:31:54
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save.json")
	@ResponseBody
	public ModelAndView save() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();

		Map<String, Object> map = new HashMap<String, Object>();
		boolean bol = this.StuSignUpService.yangZheng(pd);

		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("CAOZUOREN", user.getUSER_ID());

		if (bol) {
			PageData pds = this.StuSignUpService.sava(pd);// 增加学生信息 STUDENT_PKID
															// BMPKID
			map = this.StuSignUpService.payMentSaveAndUpdate(pds);// 生成缴费名单
			map.put("res", "SUCCESS");
		} else {
			map.put("res", "IDENTICAL");
		}

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	/**
	 * 
	 * <p>
	 * 描述:上传头像页面
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月30日 上午10:54:22
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadtouxiang.json")
	public ModelAndView uploadTouXiang() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("pd", pd);
		mv.setViewName("system/stuinfo/uploadtouxiang");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:保存头像
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月30日 上午10:56:07
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/imgCrop")
	@ResponseBody
	public ModelAndView imgCrop(HttpServletRequest request) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		double x = Double.valueOf(pd.get("x").toString());
		double y = Double.valueOf(pd.getString("y"));
		double width = Double.valueOf(pd.getString("width"));
		double height = Double.valueOf(pd.getString("height"));
		String path = pd.getString("path");
		String realPath = request.getSession().getServletContext()
				.getRealPath("/");
		String imgPath = realPath + path;
		String readImageFormat = path.substring(path.length() - 3);
		String dirpath = realPath + "uploadFiles/tx/";
		File dir = new File(dirpath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String empPath = realPath + pd.getString("sfz");
		System.out.println(">>>>>>>>>>>>>>>" + empPath);
		/*
		 * String pathjz = "uploadFiles/tx/" + pd.getString("sfz") + "." +
		 * readImageFormat; pd.put("TOUXIANG", pathjz);
		 */
		ImgUtils.cropImage(imgPath, empPath, (int) x, (int) y, (int) width,
				(int) height, readImageFormat, readImageFormat);
		// 保存照片到数据库

		// stuInfoService.saveTouxiangUrlToDB(pd);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("path", path);
		data.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), data);
	}

	/**
	 * 
	 * <p>
	 * 描述:保存头像到本地
	 * </p>
	 * 
	 * @author Administrator 康程亮
	 * @date 2017年8月3日 上午9:43:33
	 * @param myfiles
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload.json")
	public void upload(@RequestParam("upload-file") MultipartFile myfiles,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("utf-8");
		PageData pd = this.getPageData();
		MultipartFile img = myfiles;
		String uPath = request.getSession().getServletContext()
				.getRealPath("/");
		String tempImg_path = "uploadFiles/tx/";
		File dir = new File(uPath + tempImg_path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String pic_path = null;
		String result = "0"; // --标识是否成功--
		int srcWidth = 0;
		int srcHeight = 0;
		if (img != null && !img.isEmpty()) {
			int index = img.getOriginalFilename().lastIndexOf('.');
			String picName = get32UUID()
					+ img.getOriginalFilename().substring(index);
			pic_path = tempImg_path + picName;
			img.transferTo(new File(uPath + tempImg_path + picName));

			BufferedImage bi = ImageIO.read(new File(uPath + tempImg_path
					+ picName));
			srcWidth = bi.getWidth();
			srcHeight = bi.getHeight();

			result = "1";
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("result", result);
		data.put("path", pic_path);
		data.put("width", String.valueOf(srcWidth));
		data.put("height", String.valueOf(srcHeight));
		if (srcWidth < srcHeight) {
			data.put("scale", ImgUtils.imgScale(srcWidth, srcHeight, 200, 300));
		} else {
			data.put("scale", ImgUtils.imgScale(srcWidth, srcHeight, 460, 400));
		}
		data.put("style", srcWidth >= srcHeight ? "width" : "height");
		PrintWriter printWriter = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(data);
		printWriter
				.write("<html><head><meta charset=\"utf-8\"/></head><body><pre>"
						+ json + "</pre></body></html>");
		printWriter.flush();
		printWriter.close();
	}

	/**
	 * 
	 * <p>
	 * 描述:删除头像
	 * </p>
	 * 
	 * @author Administrator 康程亮
	 * @date 2017年8月3日 上午9:44:43
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pic_delete")
	@ResponseBody
	public ModelAndView pic_delete(HttpServletRequest request) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		String path = pd.getString("path");
		String realPath = request.getSession().getServletContext()
				.getRealPath("/");
		// String imgPath = realPath+path;
		// ImgUtils.deleteFiles(imgPath);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), data);
	}

	/**
	 * 
	 * <p>
	 * 描述:显示缴费名单
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月6日 上午8:40:24
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadPayMent.json")
	@ResponseBody
	public ModelAndView loadPayMent() throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();

		res = this.StuSignUpService.loadPayMent(pd);

		return new ModelAndView(new MappingJackson2JsonView(), res);
	}

	/**
	 * 
	 * <p>
	 * 描述:生成预缴费名单 增加/修改 通用方法
	 * </p>
	 * 
	 * @param sign
	 *            标识符 add增加 update更新
	 * @author Administrator 任笑达
	 * @date 2018年12月5日 下午2:31:49
	 * @return
	 * @throws Exception
	 */
	/*
	 * @RequestMapping("/certerPayMent.json")
	 * 
	 * @ResponseBody public ModelAndView certerPayMent() throws Exception {
	 * Map<String, Object> res = new HashMap<String, Object>(); PageData pd =
	 * new PageData(); pd = this.getPageData(); String sign =
	 * pd.getString("sign"); if ("add".equals(sign)) res =
	 * this.StuSignUpService.payMentAdd(pd); else if ("update".equals(sign)) res
	 * = this.StuSignUpService.payMentUpdate(pd); else res.put("msg",
	 * "sign is null");
	 * 
	 * return new ModelAndView(new MappingJackson2JsonView(), res); }
	 */

	/**
	 * 
	 * <p>
	 * 描述:验证
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月6日 下午2:10:25
	 * @param pd
	 * @return
	 */
	public double is_xkMark(PageData pd) {

		return 0.0;

	}
}
