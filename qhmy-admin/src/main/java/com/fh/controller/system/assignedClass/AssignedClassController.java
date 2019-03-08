package com.fh.controller.system.assignedClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.assignedClass.AssignedClassManager;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.service.system.stuinfo.StuSignSupManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView2;
import com.fh.util.PageData;
import com.fh.util.UtfZhuanMa;
import com.fh.util.Utils;
import com.keman.zhgd.common.util.Tools;

/**
 * 
 * <p>
 * 标题:AssignedClassController
 * </p>
 * <p>
 * 描述:分班
 * </p>
 * <p>
 * 组织:河北科曼信息技术有限公司
 * </p>
 * 
 * @author Administrator 王宁
 * @date 2018年3月23日 上午11:18:23
 */
@Controller
@RequestMapping(value = "/assignedClass")
public class AssignedClassController extends BaseController {
	@Resource(name = "assignedClassService")
	private AssignedClassManager assignedClassService;
	@Resource(name = "stuInfoService")
	private StuInfoManager stuInfoService;
	@Autowired
	private StuSignSupManager StuSignUpService;

	/**
	 * 
	 * <p>
	 * 描述:去分班页面
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月3日 上午9:02:38
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAssignedClassList.php")
	public ModelAndView toAssignedClassList() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> rxnf = null;// 获取入学年份
		List<PageData> banXing = null;// 班型
		List<PageData> whkSchool = null;// 文化课学校
		List<PageData> xkcj = null;// 获取校考成绩院校
		List<PageData> banji = null;// 获取班级

		whkSchool = this.StuSignUpService.getWhkSchool(pd);
		xkcj = assignedClassService.getXKCJ(pd);
		rxnf = assignedClassService.getRXNF(pd);
		banXing = assignedClassService.getBanJiType(pd);
		banji = StuSignUpService.getBanJi(pd);

		mv.addObject("whkSchool", whkSchool);
		mv.addObject("xkcj", xkcj);
		mv.addObject("rxnf", rxnf);
		mv.addObject("banXing", banXing);
		mv.addObject("banji", banji);

		mv.addObject("pd", pd);
		mv.setViewName("system/assignedClass/assignedClass_list");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:获取分班表数据
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月3日 上午9:49:03
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/stuFenBan.json")
	public ModelAndView stuFenBan(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();

		// 年纪
		pd.put("gradeArray", pd.getString("grade_str").split(","));
		// 班型
		pd.put("banxingArray", pd.getString("banxing_str").split(","));
		// 班级
		pd.put("banjiArray", pd.getString("banji_str").split(","));
		// 合作学校
		pd.put("partschoolArray", pd.getString("partschool_str").split(","));

		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		List<PageData> list = null;
		list = this.StuSignUpService.stuinfo_list(page);
		for (PageData pp : list) {
			String xkcjValue = "";
			String XK_MARK = pp.getString("XK_MARK").trim();
			if (!"null".equals(XK_MARK) && !"".equals(XK_MARK)) {

				String xklist[] = XK_MARK.split(",");

				for (int i = 0; i < xklist.length; i++) {
					PageData my = new PageData();
					PageData val = null;
					my.put("MYPKID", xklist[i]);
					val = this.StuSignUpService.getXKCJs(my);
					if (val != null) {

						if ((i + 1) == xklist.length) {
							xkcjValue += val.getString("NAME");
							continue;
						}
						xkcjValue += val.getString("NAME") + ",";

					}
				}

				pp.put("XK_MARK", xkcjValue);

			}

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	/**
	 * 
	 * <p>
	 * 描述:获取分班表数据
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月3日 上午9:49:03
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getbj.json")
	public ModelAndView getbj(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();

		// 年纪
		pd.put("gradeArray", pd.getString("grade_str").split(","));
		// 班型
		pd.put("banxingArray", pd.getString("banxing_str").split(","));

		List<PageData> list = null;
		list = this.StuSignUpService.getBanjiByNx(pd);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);

		for (PageData b : list) {
			System.out.println(b);
		}

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	/**
	 * 
	 * <p>
	 * 描述:分班信息导出
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月3日 下午2:28:51
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportExcel.json")
	public ModelAndView daoChu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);// 中文转码
		String[] headerNames = new String[] { "身份证号", "姓名", "性别", "学号", "入学年份",
				"班型", "学生类型", "校考成绩", "联考分数", "原班级", "班级" };
		String[] bodyNames = new String[] { "SHENFENZHENGHAO", "XINGMING",
				"XINGBIE", "XUEHAO", "RXNIANFEN_PKID", "BANJI_TYPE", "CENGCI",
				"XK_MARK", "LKFS", "OLD_BANJINAME", "BANJINAME" };

		// 年纪
		pd.put("gradeArray", pd.getString("grade_str").split(","));
		// 班型
		pd.put("banxingArray", pd.getString("banxing_str").split(","));
		// 班级
		pd.put("banjiArray", pd.getString("banji_str").split(","));
		// 合作学校
		pd.put("partschoolArray", pd.getString("partschool_str").split(","));

		List<PageData> varOList = this.StuSignUpService.stuExcel(pd);

		for (PageData pp : varOList) {
			String xkcjValue = "";
			String XK_MARK = pp.getString("XK_MARK").trim();
			if (!"null".equals(XK_MARK) && !"".equals(XK_MARK)) {

				String xklist[] = XK_MARK.split(",");

				for (int i = 0; i < xklist.length; i++) {
					PageData my = new PageData();
					PageData val = null;
					my.put("MYPKID", xklist[i]);
					val = this.StuSignUpService.getXKCJs(my);
					if (val != null) {

						if ((i + 1) == xklist.length) {
							xkcjValue += val.getString("NAME");
							continue;
						}
						xkcjValue += val.getString("NAME") + ",";

					}
				}

				pp.put("XK_MARK", xkcjValue);

			}

		}
		Map<String, Object> dataMap = Utils.toExcel(headerNames, bodyNames,
				varOList);
		dataMap.put("fileName", "分班信息");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:去分班页面
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月3日 下午2:29:18
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/godiviClass.json")
	public ModelAndView godiviClass() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> list_class = this.StuSignUpService.getBanJi(pd);

		mv.addObject("pd", pd);
		mv.addObject("list_class", list_class);
		mv.setViewName("system/assignedClass/diviClass");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:执行分班操作
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月3日 下午2:29:25
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/diviClass.json")
	public ModelAndView diviClass() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("stuArray", pd.getString("stupkid").split(","));

		this.StuSignUpService.updateStuBanJi(pd);
		jsonmap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 分学号进度条
	 */
	public static int progressIndex;

	/**
	 * 分学号进度条
	 * <p>
	 * 描述:
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2018年7月12日 下午5:18:08
	 * @return
	 */
	@RequestMapping(value = "/progress.json")
	@ResponseBody
	public int progress() {
		System.out
				.println(progressIndex
						+ "-------------------------------------------------------------------------------");
		return progressIndex;
	}

	/**
	 * 分学号
	 * <p>
	 * 描述:
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2018年7月12日 上午11:19:52
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/diviXuehao.json")
	public ModelAndView diviXuehao() throws Exception {
		progressIndex = 0;
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		try {

			progressIndex = 5;
			/*
			 * 创建序列 每个入学年份生成一个序列
			 */
			assignedClassService.createSeq(pd);

			progressIndex = 10;

			/*
			 * 第1步、 查询所有需要分学号的学生
			 */

			pd.put("banxingArray", pd.getString("BANJI_TYPE_PKID").split(","));
			pd.put("gradeArray", pd.getString("grade").split(","));
			// 班级
			pd.put("banjiArray", pd.getString("BANJI").split(","));

			String STUDENTBM_PKIDs = pd.getString("STUDENTBM_PKIDs");
			pd.put("STUDENTBM_PKIDsArray", STUDENTBM_PKIDs.split(","));

			List<PageData> stuList = new ArrayList<>();
			stuList = assignedClassService.getdiviXuehaolist(pd);// 获取分班表格数据

			/*
			 * 第2步、 筛选出可分学号（不符合规则）的学生
			 * 
			 * 1.分班页面增加一键分学号，没有学号的学生进行分学号，判断学生的学号规则和学号是否相符，不相符的根据新规则（学院编码）重新分配学号
			 * 2.学号生成规则：入学年份名称后2位、院系专业的学院层级的组织机构编码、序号4位，按报到时间排序
			 * 3.100条批处理，加个同步进度条
			 */
			List<PageData> list = new ArrayList<>();
			if (stuList != null && stuList.size() > 0) {
				String regEx = "[^0-9]";
				Pattern p = Pattern.compile(regEx);

				for (PageData stuPd : stuList) {
					// 入学年份
					String RUXUENIANFEN = stuPd.getString("RUXUENIANFEN");
					// 入学年份截取数字
					if (Tools.notEmpty(RUXUENIANFEN)) {
						Matcher m = p.matcher(RUXUENIANFEN);
						RUXUENIANFEN = m.replaceAll("").trim();
						if (Tools.notEmpty(RUXUENIANFEN)) {// 包含数字
							if (RUXUENIANFEN.length() < 2) {
								RUXUENIANFEN = "X" + RUXUENIANFEN;
							} else {
								RUXUENIANFEN = RUXUENIANFEN.substring(
										RUXUENIANFEN.length() - 2,
										RUXUENIANFEN.length());
							}
						} else {// 没有数字
							RUXUENIANFEN = "XX";
						}
					} else {// 没有入学年份
						RUXUENIANFEN = "XX";
					}
					// 班型
					String BANXINGBIANMA = stuPd.getString("BANXINGBIANMA");
					String XUEHAOPREFIX = BANXINGBIANMA + RUXUENIANFEN;
					// 数据库中存储的学生的学号
					String XUEHAO = stuPd.getString("XUEHAO");

					if (Tools.notEmpty(XUEHAO)) {
						if (XUEHAO.length() > 4) {
							// 学号前缀是否符合规则
							if (!XUEHAOPREFIX.equals(XUEHAO.substring(0,
									XUEHAO.length() - 4))) {
								// 学号前缀
								stuPd.put("XUEHAOPREFIX", XUEHAOPREFIX);
								stuPd.put("SEQ_RUXUENIANFEN", "SEQ_XUEHAO_"
										+ XUEHAOPREFIX);
								stuPd.put("SEQNO", assignedClassService
										.getSeqXuehaoNext(stuPd));
								list.add(stuPd);
							}
						} else {
							// 学号前缀
							stuPd.put("XUEHAOPREFIX", XUEHAOPREFIX);
							stuPd.put("SEQ_RUXUENIANFEN", "SEQ_XUEHAO_"
									+ XUEHAOPREFIX);
							stuPd.put("SEQNO", assignedClassService
									.getSeqXuehaoNext(stuPd));
							list.add(stuPd);
						}
					} else {
						// 学号前缀
						stuPd.put("XUEHAOPREFIX", XUEHAOPREFIX);
						stuPd.put("SEQ_RUXUENIANFEN", "SEQ_XUEHAO_"
								+ XUEHAOPREFIX);
						stuPd.put("SEQNO",
								assignedClassService.getSeqXuehaoNext(stuPd));
						list.add(stuPd);
					}

				}
			}

			progressIndex = 20;
			/*
			 * 第3步、 批量分班 100个学生分一次学号
			 */
			if (list != null && list.size() > 0) {
				if (list.size() >= 100) {
					// 商
					int countShang = list.size() / 100;
					BigDecimal countShangBig = new BigDecimal(countShang);
					BigDecimal listSizeBig = new BigDecimal(list.size());
					BigDecimal baiBig = new BigDecimal(100);
					if (countShang > 0) {
						for (int i = 0; i < countShang; i++) {
							pd.put("list", list.subList(i * 100, (i + 1) * 100));
							// 分学号
							assignedClassService.updateXueHaoBatch(pd);

							progressIndex += (countShangBig.divide(listSizeBig,
									2, RoundingMode.HALF_UP).multiply(baiBig)
									.intValue());
							if (progressIndex > 100)
								progressIndex = 100;
							System.out
									.println(progressIndex
											+ "=============================================================================");
						}
					}
					// 余数
					int countYu = list.size() % 100;
					if (countYu > 0) {
						pd.put("list",
								list.subList(countShang * 100, list.size()));
						// 分学号
						assignedClassService.updateXueHaoBatch(pd);
						progressIndex = 100;
					}

				} else {
					pd.put("list", list);
					progressIndex = 30;
					// 分学号
					assignedClassService.updateXueHaoBatch(pd);
					progressIndex = 100;
				}
				jsonmap.put("result", "success");
			} else {
				progressIndex = 100;
				jsonmap.put("result", "success");
				jsonmap.put("errorinfo", "分学号成功！");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			progressIndex = 100;
			jsonmap.put("result", "fail");
			jsonmap.put("errorinfo", "分学号产生异常，请重试！");
		}
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:去分学号页面
	 * </p>
	 * 
	 * @author Administrator 王宁
	 * @date 2018年3月23日 上午11:19:27
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toDiviXuehaoList.php")
	public ModelAndView toDiviXuehaoList() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> zuzhilist = new ArrayList<>();
		// 查询入学年份列表
		pd.put("PARENT_ID", "GRADE");
		List<PageData> gradelist = stuInfoService.getFromSYS_DICT(pd);
		// 查询入学年份列表
		pd.put("PARENT_ID", "CLASSTYPE");
		List<PageData> banxinglist = stuInfoService.getFromSYS_DICT(pd);
		mv.addObject("banxinglist", banxinglist);
		mv.addObject("gradelist", gradelist);
		mv.addObject("pd", pd);
		mv.setViewName("system/assignedClass/diviXuehao_list");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:获取分班表数据
	 * </p>
	 * 
	 * @author Administrator 王宁
	 * @date 2018年3月21日 下午3:36:32
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("diviXuehaoTable.json")
	public ModelAndView diviXuehaoTable(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);// 中文转码
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		List<PageData> list = new ArrayList<>();
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		/*
		 * if(this.getUserAllDepts()!=null && this.getUserAllDepts().size()>0){
		 * pd.put("userAllDepts", this.getUserAllDepts()); page.setPd(pd);
		 * list=assignedClassService.diviXuehaolistPage(page);//获取分班表格数据 }else
		 * if (stuInfoService.getUserAllClass(user.getUSER_ID())!=null &&
		 * stuInfoService.getUserAllClass(user.getUSER_ID()).size()>=1) {
		 * //代表是班主任 List<PageData> classList =
		 * stuInfoService.getUserAllClass(user.getUSER_ID());
		 * pd.put("classList", classList); page.setPd(pd);
		 * list=assignedClassService.diviXuehaolistPage(page);//获取分班表格数据 }
		 */

		pd.put("banxingArray", pd.getString("BANJI_TYPE_PKID").split(","));
		pd.put("gradeArray", pd.getString("grade").split(","));
		// 班级
		pd.put("banjiArray", pd.getString("BANJI").split(","));

		page.setPd(pd);
		list = assignedClassService.diviXuehaolistPage(page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	/**
	 * 导出分学号
	 * <p>
	 * 描述:
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2018年12月14日 下午9:59:17
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportDiviXuehaoExcel.json")
	public ModelAndView exportDiviXuehaoExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);

		pd.put("banxingArray", pd.getString("BANJI_TYPE_PKID").split(","));
		pd.put("gradeArray", pd.getString("grade").split(","));
		// 班级
		pd.put("banjiArray", pd.getString("BANJI").split(","));

		String[] headerNames = new String[] { "身份证号", "姓名", "性别", "学号", "入学年份",
				"班型", "学生类型", "班级", "缴费总额" };
		String[] bodyNames = new String[] { "SHENFENZHENGHAO", "XINGMING",
				"XINGBIE", "XUEHAO", "RUXUENIANFEN", "BANXING", "CENGCI_NAME",
				"CLASS_NAME", "TOTALMONEY" };
		List<PageData> varOList = assignedClassService
				.exportDiviXuehaoExcel(pd);
		Map<String, Object> dataMap = Utils.toExcel(headerNames, bodyNames,
				varOList);
		dataMap.put("fileName", "学生信息");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	/**
	 * 获得班级
	 * <p>
	 * 描述:
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2018年11月28日 下午2:00:31
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getBanJi.json")
	public ModelAndView getBanji() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> banJiList = assignedClassService.getClass(pd);
		jsonmap.put("banJiList", banJiList);
		jsonmap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}
}
