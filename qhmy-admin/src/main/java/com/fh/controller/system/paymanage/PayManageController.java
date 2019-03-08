package com.fh.controller.system.paymanage;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.controller.system.login.LoginController;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.User;
import com.fh.service.system.paymanage.PayManageManager;
import com.fh.service.system.report.ReportManager;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.util.Const;
import com.fh.util.DictZiDianSelect;
import com.fh.util.Jurisdiction;
import com.fh.util.Md5Util;
import com.fh.util.ObjectExcelView2;
import com.fh.util.PageData;
import com.fh.util.TableColums;
import com.fh.util.UtfZhuanMa;
import com.fh.util.Utils;
import com.keman.zhgd.common.DataZidianSelect.DiscountModeEnum;
import com.keman.zhgd.common.DataZidianSelect.StatusEnum;
import com.keman.zhgd.common.datetime.DateUtil;
import com.fh.util.Tools;
import com.newsky.epay.sdk.HttpClient;

/**
 * 缴费管理控制器
 * <p>
 * 标题:PayManage
 * </p>
 * <p>
 * 描述:
 * </p>
 * <p>
 * 组织:河北科曼信息技术有限公司
 * </p>
 * 
 * @author Administrator 康程亮
 * @date 2017年8月3日 下午5:30:37
 */
@Controller
@RequestMapping(value = "/pay")
public class PayManageController extends BaseController {
	@Resource(name = "payManageService")
	private PayManageManager payManageService;
	@Resource(name = "stuInfoService")
	private StuInfoManager stuInfoService;
	@Resource(name = "reportService")
	private ReportManager reportService;

	/**
	 * 
	 * <p>
	 * 描述:跳转到缴费导航页面
	 * </p>
	 * 
	 * @author Administrator 康程亮
	 * @date 2017年8月3日 下午8:36:45
	 * @return
	 */
	@RequestMapping(value = "/paynav.php")
	public ModelAndView topay_nav() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		/*
		 * 菜单权限
		 */
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String username = user.getUSERNAME();
		List<Menu> allmenuList = (List<Menu>) session.getAttribute(username
				+ Const.SESSION_allmenuList);
		List<Menu> authorityManageMenus = null;
		for (Menu menu : allmenuList) {
			if ("2".equals(menu.getMENU_ID())) {
				// 权限菜单
				authorityManageMenus = menu.getSubMenu();
			}
		}
		mv.addObject("menuList", authorityManageMenus);
		// end

		mv.setViewName("system/pay/pay_nav");
		return mv;
	}
	
	
	/**
	 * 
	 * <p>描述:</p>
	 * @author Administrator 胡文浩
	 * @date 2019年1月9日 下午1:37:37
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tosendMsg.json")
	public ModelAndView tosendMsg() throws Exception {
		ModelAndView mv = this.getModelAndView();
		/*
		 * 查询当前项目的欠费人数以及有openid的人数
		 */
		PageData pd = this.getPageData();
		PageData sendMsgCountsPd = payManageService.getSendMsgCounts(pd);
		mv.addObject("sendMsgCountsPd", sendMsgCountsPd);
		mv.setViewName("system/pay/sendMsg");
		return mv;
	}

	
	@RequestMapping(value = "/sendWxMsg.json")
	public ModelAndView sendWxMsg(Page page) throws Exception {
		PageData pd = this.getPageData();
		Properties prop = new Properties();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		InputStream in = null;
		in=LoginController.class.getClassLoader().getResourceAsStream("conf.properties");
		prop.load(in);
		Map<String,String> rep=new HashMap<String,String>();
		String methodurl="";
		if(prop!=null){
			methodurl=prop.getProperty("neiUrl");
		}
		rep.put("MUDI", "yijiancuijiao");
		rep.put("template_title", "缴费通知");
		rep.put("PKID", pd.getString("payitempkid"));
		rep.put("OPERATOR", user.getUSER_ID());
		HttpClient httpClient2 = new HttpClient(methodurl+"/qhmy-pay/dingDanPayController/sendMubanMessage", 30000, 30000);
		int resultCode2 = httpClient2.send(rep, "UTF-8");
		return null;
	
	
	}
	/**
	 * 
	 * <p>
	 * 描述:其它缴费列表
	 * </p>
	 * 
	 * @author Administrator 康程亮
	 * @date 2017年8月3日 下午8:37:30
	 * @return
	 */
	@RequestMapping(value = "/otherpayitemset.php")
	public ModelAndView toOtherpayitemlist() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/pay/otherpayitemlist");
		return mv;
	}

	/**
	 * 保存其他项目设置
	 * <p>
	 * 描述:
	 * </p>
	 * 
	 * @author Administrator 康程亮
	 * @date 2017年8月3日 下午9:11:55
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save.json")
	public ModelAndView save(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();// json结果map
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("PKID", this.get32UUID());
		pd.put("CJR", user.getUSER_ID());
		pd.put("XGR", user.getUSER_ID());
		payManageService.saveOtherPayItem(pd);
		jsonmap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:其它缴费项table列表
	 * </p>
	 * 
	 * @author Administrator 康程亮
	 * @date 2017年8月3日 下午9:57:06
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/table")
	public ModelAndView tablejson(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		List<PageData> list = null;
		page.setPd(pd);
		list = payManageService.otherPayItemlistPage(page);
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
	 * 描述:更新其它缴费项设置
	 * </p>
	 * 
	 * @author Administrator 康程亮
	 * @date 2017年8月3日 下午11:54:54
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateotherpayitemset.json")
	public ModelAndView updateotherpayitemset(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();// json结果map
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("XGR", user.getUSER_ID());
		payManageService.updateotherpayitemset(pd);
		jsonmap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 删除一条数据
	 * <p>
	 * 描述:
	 * </p>
	 * 
	 * @author Administrator 康程亮
	 * @date 2017年8月3日 下午2:31:33
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/delsingledata")
	@ResponseBody
	public ModelAndView delete() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		payManageService.deleteotherpayitemset(pd);
		jsonmap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:其它缴费页面
	 * </p>
	 * 
	 * @author Administrator 康程亮
	 * @date 2017年8月3日 下午8:37:30
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/otherpay.php")
	public ModelAndView toOtherpaylist() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		PageData userpd = new PageData();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		userpd.put("USERID", user.getUSER_ID());
		List<PageData> otherpayList = payManageService.getOtherPayItems();
		List<PageData> zuzhilist = stuInfoService.getZuzhis(pd);
		List<PageData> gradelist = stuInfoService.getGrades(pd);
		mv.addObject("otherpayList", otherpayList);
		mv.addObject("zuzhilist", zuzhilist);
		mv.addObject("gradelist", gradelist);
		mv.addObject("userpd", userpd);
		mv.setViewName("system/pay/otherpay");
		return mv;
	}

	/**
	 * 
	 * <p>描述:发布缴费页面</p>
	 * @author wn 王宁
	 * @date 2018年11月28日 下午3:21:50
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fabujf.php")
	public ModelAndView tofabujflist() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//缴费类型列表
		List<PageData> list_payStyle = payManageService.getPayStyleList(pd);
		//获取入学年份列表
		List<PageData> list_nianji = payManageService.getnianji(pd);
		//班型列表
		List<PageData> list_banxing = payManageService.getBanXing(pd);
		mv.addObject("pd", pd);
		mv.addObject("list_payStyle", list_payStyle);
		mv.addObject("list_nianji", list_nianji);
		mv.addObject("list_banxing", list_banxing);
		mv.setViewName("system/pay/releasepayment");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:保存发布缴费
	 * </p>
	 * 
	 * @author Administrator 胡文浩
	 * @date 2017年8月8日 上午9:09:44
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fabusave.json")
	public ModelAndView fabusave() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();// json结果map
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> successList = new ArrayList<PageData>();
		String List = pd.getString("successList");
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("CJR", user.getUSER_ID());
		if("Y".equals(pd.getString("xiugai"))){//表示修改
			pd.put("ITEMPKID", pd.getString("pkid"));
		}else{
			pd.put("ITEMPKID", this.get32UUID());
		}
		pd.put("RULEPKID", this.get32UUID());
		PageData drpd = new PageData();
		PageData pd_stu = null;//记录学生主要信息作为入参
		PageData pd_stu_bm = null;//记录学生附表查询回来的信息
		List<PageData> ryList = new ArrayList<PageData>();
		if ("1".equals(pd.getString("ITEMLIST_CREATEMODE"))) { // 导入
			// 都不为空，表示修改的时候旧数据和新数据都存在
			if (!"null".equals(List) && !"".equals(List) && null != List) {
				JSONArray jsonarraye = JSONArray.fromObject(pd.get(
						"successList").toString());
				successList = (List<PageData>) JSONArray.toCollection(
						jsonarraye, PageData.class);
				for (int i = 0; i < successList.size(); i++) { // 循环倒入列表并计算相关费用
					drpd = new PageData();
					String id = successList.get(i).getString("cardNo");
					String fabuje = successList.get(i).getString("cost");//发布金额
					pd_stu = new PageData();
					pd_stu.put("SHENFENZHENGHAO", id);//身份证号
					pd_stu.put("GRADE_PKID", pd.getString("GRADE_PKID"));//入学年份
					pd_stu.put("CLASSTYPE_PKID", pd.getString("CLASSTYPE_PKID"));//班型
					pd_stu_bm = payManageService.findpkbyid(pd_stu);
					if (pd_stu_bm!=null) {
						drpd.put("IPKID", this.get32UUID());
						drpd.put("STUDENT_PKID", pd_stu_bm.getString("STUDENT_PKID"));
						drpd.put("STUDENT_BM_PKID", pd_stu_bm.getString("PKID"));
						drpd.put("PAY_ITEM_PKID", pd.getString("ITEMPKID"));
						drpd.put("COST", fabuje);
						drpd.put("DISCOUNT", "不优惠");
						drpd.put("AMOUNTRECEIVABLE", fabuje);
						drpd.put("DISCOUNT_MONEY", 0);
						drpd.put("DISCOUNT_MODE", 0);
						ryList.add(drpd);
					}
				}
				// 执行保存方法
				payManageService.savefabumd(pd, ryList);
			} else {
				payManageService.saveitem(pd);
			}

		} else if ("2".equals(pd.getString("ITEMLIST_CREATEMODE"))) { // 规则
			if (!"[]".equals(pd.get("fbArray").toString())
					&& !"null".equals(pd.get("fbArray").toString())
					&& !"".equals(pd.get("fbArray").toString())
					&& pd.get("fbArray").toString() != null) {
				JSONArray jsonarray = JSONArray.fromObject(pd.get("fbArray")
						.toString());
				List<PageData> edulist = (List<PageData>) JSONArray
						.toCollection(jsonarray, PageData.class);
				List<PageData> list_gz = new ArrayList<>();//规则数据
				List<PageData> list_gz_fb = new ArrayList<>();//规则附表数据
				PageData pd_rule_fb = null;//组建规则附表数据
				String fabuje = pd.getString("bzfy");//标准费用
				for (PageData pdd : edulist) {
					//组建规则数据
					pdd.put("RULEPKID", this.get32UUID());
					pdd.put("COST",fabuje);
					pdd.put("T_PAY_ITEM_PKID",pd.getString("ITEMPKID"));
					pdd.put("YOUHUI_TYPE", pdd.getString("guizetype"));
					pdd.put("ZENGJIAN_TYPE", pdd.getString("jianjiaType"));
					pdd.put("ZENGJIAN_JINE", pdd.getString("jianjiajine"));
					list_gz.add(pdd);
					pd_rule_fb = new PageData();
					pd_rule_fb.put("RULEFBPKID", this.get32UUID());
					pd_rule_fb.put("T_PAY_ITEM_RULE_PKID", pdd.getString("RULEPKID"));
					String guizetype = pdd.getString("guizetype");
					if("1".equals(guizetype)){//表示合作学校
						pd_rule_fb.put("HZXY", pdd.getString("hzxy"));
						list_gz_fb.add(pd_rule_fb);
					}else if("2".equals(guizetype)){//表示报名时间
						pd_rule_fb.put("BAOMINGTIMEQ", pdd.getString("baomingtimeq")+" 00:00:00");
						pd_rule_fb.put("BAOMINGTIMEZ", pdd.getString("baomingtimez")+" 23:59:59");
						list_gz_fb.add(pd_rule_fb);
					}else if("4".equals(guizetype)){//表示联考成绩
						pd_rule_fb.put("CENGCI", pdd.getString("cengci_str"));
						pd_rule_fb.put("ZUIGAOFEN", pdd.getString("liankaozuigao"));
						pd_rule_fb.put("ZUIDIFEN", pdd.getString("liankaozuidi"));
						list_gz_fb.add(pd_rule_fb);
					}else if("5".equals(guizetype)){//表示班级
						pd_rule_fb.put("T_CLASS_PKIDS", pdd.getString("banji_str"));
						list_gz_fb.add(pd_rule_fb);
					}else if("6".equals(guizetype)){//表示美院
						pd_rule_fb.put("SYS_MEI_DEPARTMENT_PKIDS", pdd.getString("meiyuan_str"));
						list_gz_fb.add(pd_rule_fb);
					}
				}
				payManageService.savefabu(pd, list_gz,list_gz_fb);
			} else {
				payManageService.saveitem(pd);
			}

		}
		jsonmap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}
	/**
	 * 
	 * <p>描述:验证发布项目是否已存在</p>
	 * @author wn 王宁
	 * @date 2018年12月4日 下午2:07:00
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/indefyItem.json")
	@ResponseBody
	public ModelAndView indefyItem() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pd_item = payManageService.indefyItem(pd);
		if(pd_item==null){
			jsonmap.put("result", "error");
		}else{
			jsonmap.put("result", "success");//表示可以发布
		}
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}
	/**
	 * 
	 * <p>描述:获取合作学校下拉列表</p>
	 * @author wn 王宁
	 * @date 2018年12月3日 上午10:14:21
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gethzxxList.json")
	@ResponseBody
	public ModelAndView gethzxxList() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> list = new ArrayList<>();
		list = payManageService.gethzxxList(pd);
		jsonmap.put("list", list);
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}
	/**
	 * 
	 * <p>描述:获取合作学校包含回显信息</p>
	 * @author wn 王宁
	 * @date 2019年1月23日 上午11:28:58
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gethzxxListAdd.json")
	@ResponseBody
	public ModelAndView gethzxxListAdd() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> list = new ArrayList<>();
		list = payManageService.gethzxxListAdd(pd);
		jsonmap.put("list", list);
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}
	/**
	 * 
	 * <p>描述:获取班级下拉列表</p>
	 * @author wn 王宁
	 * @date 2018年12月3日 下午2:33:56
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getClassList.json")
	@ResponseBody
	public ModelAndView getClassList() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();//GRADE
		List<PageData> list = new ArrayList<>();
		//根据入学年份查找上一年的入学年份
		PageData pd_grade_last = payManageService.getLastGrade(pd);
		if(pd_grade_last!=null){
			pd.put("GRADE", pd_grade_last.getString("DICTIONARIES_ID"));
		}else{
			pd.put("GRADE", "");
		}
		list = payManageService.getClassList(pd);
		jsonmap.put("list", list);
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}
	/**
	 * 
	 * <p>描述:获取美院列表</p>
	 * @author wn 王宁
	 * @date 2018年12月3日 下午2:48:31
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMeiYuanList.json")
	@ResponseBody
	public ModelAndView getMeiYuanList() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> list = new ArrayList<>();
		list = payManageService.getMeiYuanList(pd);
		jsonmap.put("list", list);
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}
	/**
	 * 
	 * <p>
	 * 描述:跳转到缴费管理页面
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月8日 下午8:36:45
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/paymanage.php")
	public ModelAndView paymanage() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// 得到年级
		List<PageData> ruxuenianfen = payManageService.getnianji(pd);
		
		List<PageData> banxing = payManageService.getbanxing(pd);
		
		mv.addObject("banxing", banxing);
		// 得到优惠范围
		//List<PageData> listyouhuifw = payManageService.getyouhuifw(pd);
		
		//获得批次
		/*List<PageData> piciList = reportService.getPICIList(pd);
		mv.addObject("listpici", piciList);*/
		//获得层次
		/*List<PageData> cengciList = reportService.getCENGCIList(pd);
		mv.addObject("listcengci", cengciList);*/
		
		//获取缴费类型列表
		List<PageData> pay_style_list = reportService.getPayStyleListAll(pd);
		//获取学年列表
		//List<PageData> school_year_list = reportService.getSchoolYeaListAll(pd);
		
				
		// 得到优惠方式
		List list = new ArrayList();
		Map<String, String> typeMap;
		for (DiscountModeEnum type : DiscountModeEnum.values()) {
			typeMap = new HashMap<>();
			typeMap.put("NAME", type.name());
			typeMap.put("VALUE", type.getValue());
			list.add(typeMap);
		}
		// 组织树
		//List<PageData> zuzhilist = payManageService.getZuzhis(pd);
		List<PageData> zuzhilist=stuInfoService.getZuzhis(pd);
		String colStr="";
		colStr=TableColums.currentUserTableShowCollums("JFGL_LIST");		
		mv.addObject("colStr",colStr);
		mv.addObject("zuzhilist", zuzhilist);
		mv.addObject("pay_style_list", pay_style_list);
		//mv.addObject("school_year_list", school_year_list);
		mv.addObject("listyouhuifs", list);
		mv.addObject("ruxuenianfen", ruxuenianfen);
		//mv.addObject("listyouhuifw", listyouhuifw);
		mv.setViewName("system/pay/paymanage");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:缴费管理table列表
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月8日 下午9:57:06
	 * @param page
	 * @param startusing
	 *            是否启用：Y启用，N不启用
	 * @param status
	 *            状态: 0待审核 1:审核成功 -1审核失败 2已结束 3已过期
	 * @param payitem
	 *            项目名称
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/paymanagetable.json")
	public ModelAndView paymanagetable(Page page, String startusing,
			String status, String payitem, String startDate, String endDate)
			throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		List<PageData> list = null;
		// pd.put("startusing",startusing);
		// pd.put("status",status);
		// pd.put("startdate",StringUtils.isEmpty(startDate)?null:DateUtil.fomatDate(DateUtil.fomatDate(startDate
		// +" 00:00:00"), "yyyy/MM/dd"));
		// pd.put("enddate",StringUtils.isEmpty(endDate)?null:DateUtil.fomatDate(DateUtil.fomatDate(endDate
		// +" 23:59:59"), "yyyy/MM/dd"));
		if (!StringUtils.isEmpty(payitem)) {
			payitem = new String(payitem.toString().getBytes("ISO8859-1"),
					"utf-8");
			pd.put("payitem", payitem);
		}
		page.setPd(pd);
		list = payManageService.getPayItemlistPage(page);
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
	 * 描述:得到学生可转出项目
	 * </p>
	 * 
	 * @author Administrator 康程亮
	 * @date 2017年8月9日 上午10:32:43
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/getcanoutpayitem.json")
	@ResponseBody
	public ModelAndView getStuCanOutPayitem() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		// list 用来存放页面上已经勾选的转出项目pkid
		List<String> list = null;
		// 接收 用来存放页面上已经勾选的转出项目pkid
		if (pd != null && !"[\"\"]".equals(pd.getString("ZC_PKIDS"))
				&& !"[]".equals(pd.getString("ZC_PKIDS"))) {
			// 把array转成list
			JSONArray jsonarray = JSONArray.fromObject(pd.getString("ZC_PKIDS")
					.toString());
			list = (List<String>) JSONArray.toCollection(jsonarray,
					String.class);
		}
		// 可以转出的缴费项目
		pd.put("list", list);
		List<PageData> stuCanOutPayItemsList = payManageService
				.getStuCanOutPayitems(pd, list);
		jsonmap.put("result", "success");
		jsonmap.put("stuCanOutPayItemsList", stuCanOutPayItemsList);
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:可转入的项目们
	 * </p>
	 * 
	 * @author Administrator 康程亮
	 * @date 2017年8月9日 下午2:36:40
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getstucaninpayitem.json")
	@ResponseBody
	public ModelAndView getStuCanInPayitem() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		// list 用来存放页面上已经勾选的转出项目pkid
		List<String> list = null;
		// 接收 用来存放页面上已经勾选的转出项目pkid
		if (pd != null && !"[\"\"]".equals(pd.getString("PKIDS"))
				&& !"[]".equals(pd.getString("PKIDS"))) {
			// 把array转成list
			JSONArray jsonarray = JSONArray.fromObject(pd.getString("PKIDS")
					.toString());
			list = (List<String>) JSONArray.toCollection(jsonarray,
					String.class);
		}
		// 可以转入的缴费项目
		pd.put("list", list);
		List<PageData> stuCanInPayItemsList = payManageService
				.getStuCanInPayitems(pd);
		jsonmap.put("result", "success");
		jsonmap.put("stuCanInPayItemsList", stuCanInPayItemsList);
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:取得某个学生某个项目实际支付的金额
	 * </p>
	 * 
	 * @author Administrator 康程亮
	 * @date 2017年8月8日 下午3:47:28
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getsturealpaymoney.json")
	@ResponseBody
	public ModelAndView getStuRealPayMoney() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData stupd = null;

		stupd = payManageService.getStuOrderMoney(pd);
		if (stupd == null) {
			stupd = new PageData();
			stupd.put("YIJIAOJINE", 0);
		}
		jsonmap.put("result", "success");
		jsonmap.put("stupd", stupd);

		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:更新是否启用设置
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月8日 下午11:54:54
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePayItem.json")
	public ModelAndView updatePayItem(Page page, String startusing)
			throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();// json结果map
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("startusing", startusing);
		payManageService.updatePayItem(pd);
		jsonmap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:页面回显转入项目优惠方式和优惠金额
	 * </p>
	 * 
	 * @author Administrator 康程亮
	 * @date 2017年8月8日 上午10:41:44
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllKindMoney.json")
	@ResponseBody
	public ModelAndView getStuYouhui() throws Exception {
		// ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();

		PageData zrpd = payManageService.getpayItemMoneyByList(pd);// 根据学生和项目ID查询名单表

		if (zrpd != null) {
			pd.put("PAY_ITEM_RULE", zrpd.getString("PAY_ITEM_RULE"));
		}

		if ("Y".equals(pd.getString("ISTZZY"))) {// 如果是调整专业,优惠金额必须重新计算
			PageData zrpd_newprofess = payManageService
					.getPayItemMoneyByRule(pd);
			if (zrpd != null && zrpd_newprofess != null) {
				zrpd_newprofess.put("SYYJJE",
						(new BigDecimal(zrpd_newprofess.getString("SYYJJE"))
								.subtract(new BigDecimal(zrpd
										.getString("AMOUNTCOLLECTION"))))
								.toString());
				zrpd = zrpd_newprofess;
			}
			if (zrpd == null) {
				zrpd = zrpd_newprofess;
			}
		} else {// 不是调整专业
			if (zrpd == null) {// 为空,从规则表获取
				zrpd = payManageService.getPayItemMoneyByRule(pd);
			}
		}

		jsonmap.put("result", "success");
		jsonmap.put("zrpd", zrpd);
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:list列表
	 * </p>
	 * 
	 * @author Administrator 康程亮
	 * @date 2017年8月10日 下午7:09:07
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/otherpaytable.json")
	public ModelAndView stutablejson(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		if ("PAY_ITEM_PKID".equals(pd.getString("PAY_ITEM_PKID"))) {
			return null;
		}
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		List<PageData> list = payManageService.datalistPage(page);
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
	 * 描述:跳转到缴费名单页面
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月8日 下午8:36:45
	 * 
	 * @return
	 */
	@RequestMapping(value = "/seePayItemList.json")
	public ModelAndView seePayItemList(String payItemPkid) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, String> statusMap = new HashMap<>();
		Map<String, String> discountModeMap = new HashMap<>();
		// 获得缴费状态集合
		for (DictZiDianSelect.PayItemListStatusEnum status : DictZiDianSelect.PayItemListStatusEnum
				.values()) {
			statusMap.put(status.name(), status.getValue());
		}
		
		List<PageData> ruxuenianfen = payManageService.getnianji(pd);
		mv.addObject("ruxuenianfen", ruxuenianfen);
		
		List<PageData> banxing = payManageService.getbanxing(pd);
		mv.addObject("banxing", banxing);
		// 获得年级集合
/*		try {
			List<Dictionaries> dictionariesList = dictionariesService
					.listSubDictByParentId("GRADE");
			mv.addObject(dictionariesList);
		} catch (Exception e) {
			e.printStackTrace();
		}
*/		/*// 获得折扣模式
		for (DataZidianSelect.DiscountModeEnum mode : DataZidianSelect.DiscountModeEnum
				.values()) {
			discountModeMap.put(mode.name(), mode.getValue());
		}*/
		mv.addObject("statusMap", statusMap);
		//mv.addObject("discountModeMap", discountModeMap);
		mv.addObject("payItemPkid", payItemPkid);
		pd = UtfZhuanMa.zhuanMa(pd);
		mv.addObject("pd", pd);
		mv.setViewName("system/pay/payItemList");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:缴费审核
	 * </p>
	 * 
	 * @author Administrator 胡文浩
	 * @date 2017年8月10日 上午8:49:57
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/jfshenhe.php")
	public ModelAndView tojfshenhelist() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// Enum 审核状态值
		List list = new ArrayList();
		Map<String, String> typeMap;
		for (StatusEnum type : StatusEnum.values()) {
			typeMap = new HashMap<>();
			typeMap.put("NAME", type.name());
			typeMap.put("VALUE", type.getValue());
			list.add(typeMap);
		}
		mv.addObject("zhuangtai", list);
		mv.setViewName("system/pay/PayExamine");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:缴费审核tablle
	 * </p>
	 * 
	 * @author Administrator 胡文浩
	 * @date 2017年8月10日 上午9:24:27
	 * @param page
	 * @return
	 * @throws Exception
	 */

	@ResponseBody
	@RequestMapping(value = "/getjfshtable.json")
	public ModelAndView getjfshjson(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		String XMMC = pd.getString("XMMC");
		if (!"".equals(XMMC) && !"null".equals(XMMC)) {
			XMMC = new String(XMMC.getBytes("iso-8859-1"), "utf-8");
			pd.put("XMMC", XMMC);
		}
		// String searchText = new String(pd.get("XMMC").getBytes("ISO8859-1"),
		// "UTF-8");
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		List<PageData> list = null;
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		list = payManageService.jfshenhelistPage(page);
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
	 * 描述:批量通过 审核项目
	 * </p>
	 * 
	 * @author Administrator 胡文浩
	 * @date 2017年8月10日 下午5:32:19
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchGree")
	@ResponseBody
	public ModelAndView batchGree() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("XGR", user.getUSER_ID());
		String PKIDS = pd.getString("pkids");
		if (null != PKIDS && !"".equals(PKIDS)) {
			String pkid_arr[] = PKIDS.split(",");
			// ruleService.updatetyagenv(qypd);
			// pd.put("item", pkid_arr);
			payManageService.batchGree(pkid_arr);
			jsonmap.put("result", "success");
		} else {
			jsonmap.put("result", "fail");
		}

		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:通过 详情里的通过
	 * </p>
	 * 
	 * @author Administrator 胡文浩
	 * @date 2017年8月10日 下午9:57:59
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchGreeOne")
	@ResponseBody
	public ModelAndView batchGreeOne() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("XGR", user.getUSER_ID());
		payManageService.batchGreeOne(pd);
		jsonmap.put("result", "success");

		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:批量不通过
	 * </p>
	 * 
	 * @author Administrator 胡文浩
	 * @date 2017年8月10日 下午10:02:30
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchNo")
	@ResponseBody
	public ModelAndView batchNo() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("XGR", user.getUSER_ID());
		String PKIDS = pd.getString("pkids");
		if (null != PKIDS && !"".equals(PKIDS)) {
			String pkid_arr[] = PKIDS.split(",");
			// ruleService.updatetyagenv(qypd);
			// pd.put("item", pkid_arr);
			payManageService.batchNo(pkid_arr);
			jsonmap.put("result", "success");
		} else {
			jsonmap.put("result", "fail");
		}

		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:单个不通过
	 * </p>
	 * 
	 * @author Administrator 胡文浩
	 * @date 2017年8月10日 下午10:02:51
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchNoOne")
	@ResponseBody
	public ModelAndView batchNoOne() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("XGR", user.getUSER_ID());
		payManageService.batchNoOne(pd);
		jsonmap.put("result", "success");

		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:缴费名单table列表
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月8日 下午9:57:06
	 * @param page
	 * @param status
	 *            缴费状态
	 * @param grade
	 *            年级
	 * @param profession
	 *            专业
	 * @param conditions
	 *            搜索条件：身份证/学号/姓名
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/payItemListTable.json")
	public ModelAndView payItemListTable(Page page, String payItemPkid,
			String status, String grade, String DEPARTMENT_PKID,
			String conditions) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		List<PageData> list = null;
		pd.put("payItemPkid", payItemPkid);
		pd.put("status", status);
		pd.put("grade", grade);
		pd.put("DEPARTMENT_PKID", DEPARTMENT_PKID);
		if (!StringUtils.isEmpty(conditions) && this.isMessyCode(conditions)) {
			conditions = new String(
					conditions.toString().getBytes("ISO8859-1"), "utf-8");
			pd.put("conditions", conditions);
		}
		if (!StringUtils.isEmpty(grade) && this.isMessyCode(grade)) {
			grade = new String(grade.toString().getBytes("ISO8859-1"), "utf-8");
			pd.put("grade", grade);
		}
		if (!StringUtils.isEmpty(pd.getString("DEPARTMENT_PKID"))
				&& !"null".equals(pd.getString("DEPARTMENT_PKID"))) {
			List<String> DEPARTMENT_PKIDList = new ArrayList<>();
			String[] DEPARTMENT_PKIDArray = pd.getString("DEPARTMENT_PKID")
					.split(",");
			for (int i = 0; i < DEPARTMENT_PKIDArray.length; i++) {
				if (!StringUtils.isEmpty(DEPARTMENT_PKIDArray[i])) {
					DEPARTMENT_PKIDList.add(DEPARTMENT_PKIDArray[i]);
				}
			}
			pd.put("departmentPkids", DEPARTMENT_PKIDList);
		}
		page.setPd(pd);

		list = payManageService.getPayItemListPage(page);
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
	 * 描述:更新缴费名单
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月9日 下午11:54:54
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePayItemList.json")
	public ModelAndView updatePayItemList(String pkid, String amountreceivable,
			String cost, String discountMode, String discount) throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();// json结果map
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("pkid", pkid);
		pd.put("amountreceivable", amountreceivable);
		pd.put("cost", cost);
		pd.put("discountMode", discountMode);
		pd.put("discount", discount);
		pd.put("PKID", pkid);
		pd.put("payItemListPkid", pkid);

		try {
			payManageService.updatePayItemList(pd);
			jsonmap.put("result", "success");
		} catch (Exception e) {
			jsonmap.put("result", "fail");
		}

		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:详情页
	 * </p>
	 * 
	 * @author Administrator 胡文浩
	 * @date 2017年8月10日 下午8:14:28
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goShow.json")
	public ModelAndView goShow() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pdItem = new PageData();
		pd = this.getPageData();
		String ZT = pd.getString("ZT");
		if (!"".equals(ZT) && !"null".equals(ZT)) {
			ZT = new String(ZT.getBytes("iso-8859-1"), "utf-8");
			pd.put("ZT", ZT);
		}
		// List<PageData> list = null;
		pdItem = payManageService.getItem(pd);

		List<PageData> list = null;
		list = payManageService.rulelist(pd);
		// $("<tr ><td >"+dataset[i].GRADE+"</td><td>"+dataset[i].NIANJI+"</td><td>"+dataset[i].XSLX+"</td><td>"+dataset[i].YHFS+"</tr>").appendTo("#fabutable");
		JSONArray json = new JSONArray();
		for (PageData a : list) {
			JSONObject jo = new JSONObject();
			jo.put("GRADE", a.get("GRADE"));
			jo.put("NIANJI", a.get("NIANJI"));
			jo.put("CENGCI_NAME", Tools.notEmpty(a.getString("CENGCI_NAME"))?a.getString("CENGCI_NAME"):"无");
			jo.put("PICI_NAME", Tools.notEmpty(a.getString("PICI_NAME"))?a.getString("PICI_NAME"):"无");
			jo.put("YHFS", a.get("YHFS"));
			jo.put("XSLX", a.get("XSLX"));
			jo.put("YHFS", a.get("YHFS"));
			json.add(jo);
		}

		String CREATEMODE = pd.getString("ITEMLIST_CREATEMODE");
		mv.addObject("json", json);
		mv.addObject("list", list);
		mv.addObject("pdItem", pdItem);
		mv.addObject("CREATEMODE", CREATEMODE);
		mv.addObject("pd", pd);
		mv.setViewName("system/pay/releasepaymentMessage");
		return mv;
	}

	/**
	 * 删除缴费项
	 * <p>
	 * 描述:
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月9日 下午2:31:33
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePayItem")
	@ResponseBody
	public ModelAndView deletePayItem(String payItemPkid) throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("payItemPkid", payItemPkid);
		
		try {
			
			List<PageData> list = null;
			list = payManageService.getsclist(pd);
			if(list!=null && list.size()>0 ){
				jsonmap.put("result", "cunzai");
			}else{
				payManageService.deletePayItem(pd);
				jsonmap.put("result", "success");
			}
			
		} catch (Exception e) {
			jsonmap.put("result", "fail");
			e.printStackTrace();
		}

		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:跳转到线下缴费页面
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月10日 下午8:36:45
	 * @return
	 */
	@RequestMapping(value = "/offlinePay.php")
	public ModelAndView offlinePay() {
		ModelAndView mv = this.getModelAndView();
		// 获得缴费状态集合
		Map<String, String> statusMap = new HashMap<>();
		for (DictZiDianSelect.PayItemListStatusEnum status : DictZiDianSelect.PayItemListStatusEnum
				.values()) {
			statusMap.put(status.name(), status.getValue());
		}
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		mv.addObject("userId", user.getUSER_ID());
		mv.addObject("statusMap", statusMap);
		mv.setViewName("system/pay/offlinePay");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:线下缴费学生信息
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月10日 下午8:36:45
	 * @param conditions
	 *            身份证号/学号
	 * @param status
	 *            状态: 缴费状态
	 * @return
	 */
	@RequestMapping(value = "/getStudentInfoForOfflinePay.json")
	@ResponseBody
	public ModelAndView getStudentInfoForOfflinePay() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> resultMap = new HashMap<>();
		PageData stuInfo = payManageService.getStudentInfo2(pd);

		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		resultMap.put("CJR", user.getUSER_ID());
		resultMap.put("XGR", user.getUSER_ID());
		resultMap.put("stuInfo", stuInfo);
		resultMap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), resultMap);
	}

	/**
	 * 
	 * <p>
	 * 描述:线下缴费table列表
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月10日 下午9:57:06
	 * @param page
	 * @param studentPkid
	 *            学生PKID
	 * @param status
	 *            状态: 缴费状态
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/offlinePayTable.json")
	public ModelAndView offlinePayTable(Page page, String studentPkid,
			String status) throws Exception {
		PageData pd = new PageData();
		PageData k = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		PageData pd_stu_yj = null;new PageData();
		PageData pod = null;
		List<PageData> list = null;
		List<PageData> plist = new ArrayList<PageData>();
		List<PageData> newlist = null;
		List<PageData> alllist = null;
		List<PageData> listrule= null;
		pd.put("studentPkid", studentPkid);
		/*pd.put("status", status);
*/
		page.setPd(pd);
		if (!StringUtils.isEmpty(studentPkid)) {
			list = payManageService
					.getPayItemlistPageByStudentPkidAndStatus(page);
		}
		//胡文浩修改 调整应收  2019.01.16
		
		//1. 先筛选出导入生成的LIST 或者 实收大于0的 或者 在rule表中不存在的 或者 做过费用变更的，不做处理
		if( list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				String ITEMLIST_CREATEMODE=list.get(i).getString("ITEMLIST_CREATEMODE");
				//String AMOUNTCOLLECTION=list.get(i).getString("AMOUNTCOLLECTION");
				String a=list.get(i).getString("AMOUNTCOLLECTION");
				String ys=list.get(i).getString("AMOUNTRECEIVABLE");
				
				double  AMOUNTRECEIVABLE=-1;
				if(ys!=null && !"".equals(ys) && !"null".equals(ys)){
					AMOUNTRECEIVABLE= Double.parseDouble(ys);
				}
				
				double  AMOUNTCOLLECTION=-1;
				if(a!=null && !"".equals(a) && !"null".equals(a)){
					AMOUNTCOLLECTION= Double.parseDouble(a);
				}
				//int  
				String b=list.get(i).getString("LOGEXISE");
				double  LOGEXISE=-1;
				if(b!=null && !"".equals(b) && !"null".equals(b)){
					LOGEXISE= Double.parseDouble(b);
				}
				
				pod =new PageData();
				pod = payManageService.getorderInfo(list.get(i).getString("PKID"));
				
				//int  LOGEXISE= Integer.parseInt(list.get(i).getString("LOGEXISE"));  getorderInfo
				//String  EXISE= list.get(i).getString("EXISE");
				if(!"1".equals(ITEMLIST_CREATEMODE) && AMOUNTCOLLECTION<=0  && LOGEXISE<=0 && AMOUNTRECEIVABLE>=0 && pod==null){
					//list.remove(i);
				//	k=list.get(i);
					plist.add(list.get(i));
					//plist.
				}
			}
		}
		
		//2. 循环LIST，寻找优惠力度最大的值
		
		if( plist!=null && plist.size()>0){
			for(int q=0;q<plist.size();q++){
				//找到all  rule 判定是直减还是增加  getallrulebypkid
				double prices = 0.0;// 获取最大的优惠金额  
				double price =0.0; 
				listrule = payManageService.getallrulebypkid(plist.get(q));
				if(listrule!=null && listrule.size()>0){
					price=Double.parseDouble(listrule.get(0).getString("COST")); //标准费用
					for(PageData r : listrule){
						PageData gz = new PageData();
						PageData jgpd = null;
						gz.put("IPKID", r.getString("T_PAY_ITEM_PKID"));
						gz.put("YOUHUI_TYPE", r.getString("YOUHUI_TYPE"));
						if("1".equals(r.getString("YOUHUI_TYPE"))){  //合作学校  getitemlistmax
							String HZXY = plist.get(q).getString("WHKXUEXIAO").trim();
							if (HZXY != null && HZXY.length() > 0) {
								gz.put("HZXY", HZXY);
								jgpd = payManageService.getitemlistmax(gz);
								if (jgpd != null) {
									double cost = Double.parseDouble(jgpd.getString("COST"));
									if (prices == 0.0) {
										prices = cost;
									}
									if (cost > prices) {
										prices = cost;
									}
							}
							}
						}else if("2".equals(r.getString("YOUHUI_TYPE"))){//报名时间
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
							String date = df.format(new Date());
							gz.put("BMSHIJIAN", date);
							jgpd = payManageService.getitemlistmax(gz);
							if (jgpd != null) {
								double cost = Double.parseDouble(jgpd.getString("COST"));
								if (prices == 0.0) {
									prices = cost;
								}
								if (cost > prices) {
									prices = cost;
								}
							}
						}else if("3".equals(r.getString("YOUHUI_TYPE"))){//预交
							jgpd = payManageService.getitemlistmax(gz);
							if (jgpd != null) {
								double cost = Double.parseDouble(jgpd.getString("COST"));
								if (prices == 0.0) {
									prices = cost;
								}
								if (cost > prices) {
									prices = cost;
								}
							}
						}else if("4".equals(r.getString("YOUHUI_TYPE"))){//联考成绩
							String CENGCI = plist.get(q).getString("CENGCI_PKID");
							String LKFS = plist.get(q).getString("LKFS");
							if (!"null".equals(CENGCI) && !"".equals(CENGCI)&& !"null".equals(LKFS) && !"".equals(LKFS)) {
								gz.put("CENGCI", CENGCI);
								gz.put("LKFS", LKFS);
								jgpd = payManageService.getitemlistmax(gz);
								if (jgpd != null) {
									double cost = Double.parseDouble(jgpd.getString("COST"));
									if (prices == 0.0) {
										prices = cost;
									}
									if (cost > prices) {
										prices = cost;
									}
								}
							}
							
						}else if("5".equals(r.getString("YOUHUI_TYPE"))){//班级
							String CENGCI = plist.get(q).getString("CENGCI_PKID");
							String BANJIPKID = plist.get(q).getString("OLD_BANJI");
							if ("bxfks".equals(CENGCI)) {
								if (!"null".equals(BANJIPKID)&& !"".equals(BANJIPKID)) {
									gz.put("BANJIPKID", BANJIPKID);
									jgpd = payManageService.getitemlistmax(gz);
									if (jgpd != null) {
										double cost = Double.parseDouble(jgpd.getString("COST"));
										if (prices == 0.0) {
											prices = cost;
										}
										if (cost > prices) {
											prices = cost;
										}
									}
								}
								
							}
						}else if("6".equals(r.getString("YOUHUI_TYPE"))){//美院
							String CENGCI = plist.get(q).getString("CENGCI_PKID");
							String XK_MARK = plist.get(q).getString("XK_MARK").trim();
							if (!"yjs".equals(CENGCI)) {
								if(!Tools.isEmpty(plist.get(q).getString("XK_MARK"))){
									List<PageData> meiyuanList = new ArrayList<PageData>();
									for (String s : XK_MARK.split(",")) {// 获取符合条件的美院
										//PageData youhui = null;
										gz.put("XK_MARK", s);
										jgpd = payManageService.getitemlistmax(gz);
										if (jgpd != null)
											meiyuanList.add(jgpd);
									}
									
									if (meiyuanList.size() > 0)
										for (int i = 0; i < meiyuanList.size() ; i++) {// 将匹配的优惠规则去重
											for (int j = meiyuanList.size() - 1; j > i; j--) {
												if (meiyuanList.get(j).getString("REPPKID").equals(meiyuanList.get(i).getString("REPPKID"))) {
													meiyuanList.remove(j);
												}
											}
										}
									for (PageData m : meiyuanList) {// 进行优惠
										if (m != null) {
											double cost = Double.parseDouble(m.getString("COST"));
											if (prices == 0.0) {
												prices = cost;
											}
											if (cost > prices) {
												prices = cost;
											}
										}
									}
									
								/*	String[] array =plist.get(q).getString("XK_MARK").split(",");
									gz.put("XK_MARK", array);
									jgpd = payManageService.getStudentInfo2(gz);
									if (jgpd != null) {
										double cost = Double.parseDouble(jgpd.getString("COST"));
										if (prices == 0.0) {
											prices = cost;
										}
										if (cost > prices) {
											prices = cost;
										}
									}*/
								}
								
								
							}
						}
					}
				}
				//double price = Double.parseDouble(p.getString("PAYPRICE"));
				price = Tools.sub(price, prices);//最后的应收
				//查询是不是学费  是学费的话看看是不是有预交  再减去预交
				if("学费".equals(plist.get(q).getString("PAY_STYLE_NAME"))){
					//item_pd.put("STUDENT_BM_PKID", pd_stubm.getString("STUDENT_BM_PKID"));
					//PageData pd_stu_yj = (PageData)dao.findForObject("StuInfoMapper.getStuYJInfo", );
					pd_stu_yj =new PageData();
					pd_stu_yj = payManageService.getStuYJInfo(plist.get(q));
					if(pd_stu_yj!=null && pd_stu_yj.getString("GRADE_PKID").equals(plist.get(q).getString("GRADE_PKID"))
							&& pd_stu_yj.getString("CLASSTYPE_PKID").equals(plist.get(q).getString("CLASSTYPE_PKID"))){//表示该学生存在预交
						//BigDecimal yingShouyj = new BigDecimal();//预交的应收
						//bgResult = bgResult.subtract(yingShouyj);
						double cost = Double.parseDouble(pd_stu_yj.getString("AMOUNTRECEIVABLE"));
						price = Tools.sub(price, cost);
					}
				}

				
				
				
				BigDecimal bg = new BigDecimal(price).setScale(2,BigDecimal.ROUND_HALF_UP);
				price = bg.doubleValue();
				pd.put("YINGSHOU", price);
				pd.put("LISTPKID", plist.get(q).getString("PKID"));
				payManageService.updateitemlist(pd);
				
				//找到最大优惠值以后 更新  updateitemlist
				
			}
		}
		
	/*	PageData drpd = new PageData();
		if (!StringUtils.isEmpty(studentPkid)) {
			newlist = payManageService.getPayItemlistPageByStudentPkidAndStatus(page);
			for(int g=0;g<newlist.size();g++){
				String yings=newlist.get(g).getString("AMOUNTRECEIVABLE");
				String shis=newlist.get(g).getString("AMOUNTCOLLECTION");
				if(yings!=null && !"null".equals(yings) &&!"".equals(yings) ){
					if(yings.equals(shis)){
						drpd = new PageData();
						drpd.put("LISTPKID", newlist.get(g).getString("PKID"));
						drpd.put("STATUS", 2);
						payManageService.updateitemlist(drpd);
					}
				}
			}
		}*/
		
		
		if (!StringUtils.isEmpty(studentPkid)) {
			alllist = payManageService.getPayItemlistPageByStudentPkidAndStatus(page);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", alllist);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	/**
	 * 
	 * <p>
	 * 描述:获得线下缴费记录
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月12日 下午8:36:45
	 * @return
	 */
	@RequestMapping(value = "/getPayOrderDetailList.json")
	@ResponseBody
	public ModelAndView getPayOrderDetailList() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> resultMap = new HashMap<>();

		pd.put("createModels", null);
		// 获得线下缴费记录列表
		List<PageData> payOrderDetailList = payManageService
				.getPayOrderDetailList(pd);
		/*if (payOrderDetailList != null && payOrderDetailList.size() > 0) {
			for (PageData payOrderDetail : payOrderDetailList) {
				// 各种支付方式总金额
				BigDecimal totalMoney = new BigDecimal(0);
				pd.put("orderDetailPkid", payOrderDetail.get("PKID"));
				// 获得缴费记录金额和支付方式列表
				List<PageData> payOrderDetailMoneyList = payManageService
						.getPayOrderDetailMoneyList(pd);
				if (payOrderDetailMoneyList != null
						&& payOrderDetailMoneyList.size() > 0) {
					for (PageData payOrderDetailMoney : payOrderDetailMoneyList) {
						if (payOrderDetailMoney.get("PAY_MODE") != null) {
							// 金额
							String money = payOrderDetailMoney.get("MONEY") != null ? payOrderDetailMoney
									.get("MONEY").toString() : "0";
							// 将各种支付方式和金额放入集合
							payOrderDetail.put(
									payOrderDetailMoney.get("PAY_MODE")
											.toString(), money);

							// 计算各种支付方式总金额
							if (!"".equals(money)) {
								totalMoney = totalMoney.add(new BigDecimal(
										money));
							}
							//支付方式
							if("CASH".equals(payOrderDetailMoney.getString("PAY_MODE"))&&!"0".equals(money)){
								payOrderDetail.put("PAY_MODE", "现金");
							}
							//支付方式
							if("WX".equals(payOrderDetailMoney.getString("PAY_MODE"))&&!"0".equals(money)){
								payOrderDetail.put("PAY_MODE", "微信");
							}
							//支付方式
							if("ZFB".equals(payOrderDetailMoney.getString("PAY_MODE"))&&!"0".equals(money)){
								payOrderDetail.put("PAY_MODE", "支付宝");
							}
							//支付方式
							if("TT".equals(payOrderDetailMoney.getString("PAY_MODE"))&&!"0".equals(money)){
								payOrderDetail.put("PAY_MODE", "电汇");
							}
							//支付方式
							if("CARD".equals(payOrderDetailMoney.getString("PAY_MODE"))&&!"0".equals(money)){
								payOrderDetail.put("PAY_MODE", "银行卡");
							}
						}
					}
				}
				payOrderDetail.put(
						"CJSJ",
						StringUtils.isEmpty(payOrderDetail.get("CJSJ")) ? ""
								: DateUtil.fomatDate(new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss")
										.parse(payOrderDetail.get("CJSJ")
												.toString()),
										"yyyy-MM-dd HH:mm:ss"));
				payOrderDetail.put("totalMoney", totalMoney.toString());
			}
		}*/
		resultMap.put("result", "success");
		resultMap.put("payOrderDetailList", payOrderDetailList);
		return new ModelAndView(new MappingJackson2JsonView(), resultMap);
	}

	/**
	 * 
	 * <p>
	 * 描述:获得缴费名单
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月12日 下午8:36:45
	 * @return
	 */
	@RequestMapping(value = "/getPayItemList.json")
	@ResponseBody
	public ModelAndView getPayItemList() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> resultMap = new HashMap<>();

		pd = payManageService.getPayItemList(pd);
		resultMap.put("result", "success");
		resultMap.put("payItemListObject", pd);
		return new ModelAndView(new MappingJackson2JsonView(), resultMap);
	}

	/**
	 * 
	 * <p>
	 * 描述:获得缴费记录详情
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月12日 下午8:36:45
	 * @return
	 */
	@RequestMapping(value = "/getPayOrderDetail.json")
	@ResponseBody
	public ModelAndView getPayOrderDetail() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> resultMap = new HashMap<>();

		List<PageData> payOrderDetailListResult = new ArrayList<>();
		List<PageData> payOrderDetailList = payManageService
				.getPayOrderDetail(pd);
		if (payOrderDetailList != null && payOrderDetailList.size() > 0) {
			for (PageData payOrderDetail : payOrderDetailList) {
				pd.put("orderDetailPkid", payOrderDetail.get("PKID"));

				// 获得缴费记录金额和支付方式列表
				List<PageData> payOrderDetailMoneyList = payManageService
						.getPayOrderDetailMoneyList(pd);
				if (payOrderDetailMoneyList != null
						&& payOrderDetailMoneyList.size() > 0) {
					Map<String, String> map = new HashMap<>();
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < payOrderDetailMoneyList.size(); i++) {
						PageData payOrderDetailMoney = payOrderDetailMoneyList
								.get(i);
						if (payOrderDetailMoney.get("PAY_MODE") != null) {
							// 金额
							String money = payOrderDetailMoney.get("MONEY") != null ? payOrderDetailMoney
									.get("MONEY").toString() : "0";
							// 将各种支付方式和金额放入集合
							sb.append(payOrderDetailMoney.get("PAY_MODE")
									.toString());
							sb.append(":");
							sb.append(money);
							if ("CARD".equals(payOrderDetailMoney
									.getString("PAY_MODE"))) {// 如果是银行卡支付，则取卡号
								sb.append("@");
								sb.append(payOrderDetailMoney
										.getString("CARDNO"));
							}
							if (i != payOrderDetailMoneyList.size() - 1) {
								sb.append("|");
							}
						}

					}
					payOrderDetail.put("ZFFS", sb.toString());
				}
				payOrderDetail.put(
						"CJSJ",
						StringUtils.isEmpty(payOrderDetail.get("CJSJ")) ? ""
								: DateUtil.fomatDate(new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss")
										.parse(payOrderDetail.get("CJSJ")
												.toString()),
										"yyyy/MM/dd HH:mm:ss"));
				payOrderDetailListResult.add(payOrderDetail);
				break;
			}
		} else {
			if ("线下其他".equals(pd.getString("orderCreateModel"))) {

				PageData payOrderDetail = (PageData) payManageService
						.getDetailByOtherPayItem(pd);
				payOrderDetail.put("ORDERCREATE_MODE", "线下其他");
				// 获得缴费记录金额和支付方式列表
				List<PageData> payOrderDetailMoneyList = payManageService
						.getPayOrderDetailMoneyList(pd);
				if (payOrderDetailMoneyList != null
						&& payOrderDetailMoneyList.size() > 0) {
					Map<String, String> map = new HashMap<>();
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < payOrderDetailMoneyList.size(); i++) {
						PageData payOrderDetailMoney = payOrderDetailMoneyList
								.get(i);
						if (payOrderDetailMoney.get("PAY_MODE") != null) {
							// 金额
							String money = payOrderDetailMoney.get("MONEY") != null ? payOrderDetailMoney
									.get("MONEY").toString() : "0";
							// 将各种支付方式和金额放入集合
							sb.append(payOrderDetailMoney.get("PAY_MODE")
									.toString());
							sb.append(":");
							sb.append(money);
							if ("CARD".equals(payOrderDetailMoney
									.getString("PAY_MODE"))) {// 如果是银行卡支付，则取卡号
								sb.append("@");
								sb.append(payOrderDetailMoney
										.getString("CARDNO"));
							}
							if (i != payOrderDetailMoneyList.size() - 1) {
								sb.append("|");
							}
						}

					}
					payOrderDetail.put("ZFFS", sb.toString());
				}
				payOrderDetail.put(
						"CJSJ",
						StringUtils.isEmpty(payOrderDetail.get("CJSJ")) ? ""
								: DateUtil.fomatDate(new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss")
										.parse(payOrderDetail.get("CJSJ")
												.toString()),
										"yyyy/MM/dd HH:mm:ss"));
				payOrderDetailListResult.add(payOrderDetail);
			}
		}
		resultMap.put("result", "success");
		resultMap.put("payOrderDetail", payOrderDetailListResult);
		return new ModelAndView(new MappingJackson2JsonView(), resultMap);
	}

	/**
	 * 
	 * <p>
	 * 描述:修改打印状态
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月16日 下午8:36:45
	 * @return
	 */
	@RequestMapping(value = "/updatePrintStatus.json")
	@ResponseBody
	public ModelAndView updatePrintStatus() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> resultMap = new HashMap<>();
		// 状态置为已打印
		pd.put("printFlag", "Y");
		payManageService.updatePayOrderDetail(pd);
		resultMap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), resultMap);
	}

	/**
	 * 
	 * <p>
	 * 描述:打印时获取所有缴费记录
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月16日 下午8:36:45
	 * @return
	 */
	@RequestMapping(value = "/getPayOrderDetailListForPrint.json")
	@ResponseBody
	public ModelAndView getPayOrderDetailListForPrint() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> resultMap2 = new HashMap<>();
		List<PageData> resultList2 = new ArrayList<>();

		List<String> createModelsList = new ArrayList<>();
		createModelsList.add("D");
		createModelsList.add("DO");
		pd.put("createModels", createModelsList);

		List<String> payItemPkidsList = new ArrayList<>();
		if (!StringUtils.isEmpty(pd.get("payItemPkids"))) {
			String payItemPkids = pd.get("payItemPkids").toString();
			String[] payItemPkidsArray = payItemPkids.split(",");

			Map<String, List<String>> mm = new HashMap<>();

			for (int j = 0; j < payItemPkidsArray.length; j++) {
				if (!mm.containsKey(payItemPkidsArray[j].split("@|@")[0])) {
					mm.put(payItemPkidsArray[j].split("@|@")[0], null);
				}

			}
			for (Map.Entry<String, List<String>> entry : mm.entrySet()) {
				List<String> pp = new ArrayList<>();
				for (int j = 0; j < payItemPkidsArray.length; j++) {
					if (entry.getKey().equals(
							payItemPkidsArray[j].split("@|@")[0])) {
						pp.add(payItemPkidsArray[j].split("@|@")[2]);
					}
				}
				mm.put(entry.getKey(), pp);
			}
			for (Map.Entry<String, List<String>> entry : mm.entrySet()) {
				List<String> itemPkidList = entry.getValue();
				StringBuffer ss = new StringBuffer();
				for (int i = 0; i < itemPkidList.size(); i++) {
					String itemPkid = itemPkidList.get(i);
					if (!StringUtils.isEmpty(itemPkid)) {

						BigDecimal totalMoneyAll = new BigDecimal(0);

						pd.put("payItemPkid", itemPkid);
						pd.put("createModels", null);
						// 获得线下缴费记录列表
						List<PageData> payOrderDetailList = payManageService
								.getPayOrderDetailListForPrint(pd);
						PageData pdd = new PageData();
						StringBuffer resultSB = new StringBuffer();
						String payItem = "";
						String createDate = "";
						if (payOrderDetailList != null
								&& payOrderDetailList.size() > 0) {
							for (int j = 0; j < payOrderDetailList.size(); j++) {
								PageData payOrderDetail = payOrderDetailList
										.get(j);
								payItem = payOrderDetailList.get(0)
										.get("PAYITEM").toString();
								createDate = payOrderDetailList.get(0)
										.get("CJSJ").toString();
								// 各种支付方式总金额
								BigDecimal totalMoney = new BigDecimal(0);
								pd.put("orderDetailPkid",
										payOrderDetail.get("PKID"));
								// 获得缴费记录金额和支付方式列表
								List<PageData> payOrderDetailMoneyList = payManageService
										.getPayOrderDetailMoneyList(pd);
								if (payOrderDetailMoneyList != null
										&& payOrderDetailMoneyList.size() > 0) {
									for (PageData payOrderDetailMoney : payOrderDetailMoneyList) {
										// 金额
										String money = payOrderDetailMoney
												.get("MONEY") != null ? payOrderDetailMoney
												.get("MONEY").toString() : "0";

										// 计算各种支付方式总金额
										if (!"".equals(money)) {
											totalMoney = totalMoney
													.add(new BigDecimal(money));
										}

									}
								}
								totalMoneyAll = totalMoneyAll.add(totalMoney);
							}
							resultSB.append(totalMoneyAll.toString());
							resultSB.append("@|@");
							resultSB.append(payItem);
							resultSB.append("@|@");
							resultSB.append(createDate);
						}

						ss.append(resultSB.toString());
						ss.append("+");
						// resultMap.put(payItemPkidsArray2[2],
						// resultSB.toString());
					}
				}

				resultMap.put(entry.getKey(), ss.toString());
			}

		}

		resultMap.put("result", "success");
		// resultMap.put("payOrderDetailList", resultMap2);
		return new ModelAndView(new MappingJackson2JsonView(), resultMap);
	}
	/**
	 * 
	 * <p>
	 * 描述:未缴学生table列表
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月18日 下午9:57:06
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudentPaylistPage.json")
	public ModelAndView getStudentPaylistPage(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();

		String conditions = pd.getString("conditions");
		if (!StringUtils.isEmpty(conditions)) {
			conditions = new String(
					conditions.toString().getBytes("ISO8859-1"), "utf-8");
			pd.put("conditions", conditions);
		}

		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		List<PageData> list = payManageService.getStudentPaylistPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	/**
	 * 保存缴费名单
	 * <p>
	 * 描述:
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月18日 下午9:11:55
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/savePayItemList.json")
	public ModelAndView savePayItemList(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			payManageService.savePayItemList(pd);
			jsonmap.put("result", "success");
		} catch (Exception e) {
			jsonmap.put("result", "fail");
			jsonmap.put("failMsg", e.getMessage());
		}
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 导出缴费名单
	 * <p>
	 * 描述:
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月18日 下午9:11:55
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportStudentPayList.json")
	public ModelAndView exportStudentPayList(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd = UtfZhuanMa.zhuanMa(pd);
		String[] headerNames = new String[] { "身份证号","学号", "姓名", "入学年份","班型","缴费类型","班级", "应收金额", "实收金额","欠费金额","缴费状态" };
		String[] bodyNames = new String[] { "SHENFENZHENGHAO", "XUEHAO","XINGMING","RUXUENIANFEN","BANXING","PAY_STYLE_NAME","CLASS_NAME","AMOUNTRECEIVABLE","AMOUNTCOLLECTION", "QIANFEI","ZTSTATUS"};

		List<PageData> pdList = payManageService.exportStudentPayList(pd);
		Map<String, Object> dataMap = Utils.toExcel(headerNames, bodyNames,
				pdList);
		dataMap.put("fileName", "学生缴费名单表");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:加密学生PKID
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月18日 下午8:36:45
	 * @return
	 */
	@RequestMapping(value = "/encryptStudent.json")
	@ResponseBody
	public ModelAndView encryptStudent() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> resultMap = new HashMap<>();

		Properties prop = new Properties();
		InputStream in = null;
		in = LoginController.class.getClassLoader().getResourceAsStream(
				"conf.properties");
		prop.load(in);
		String methodurl = "";
		if (prop != null) {
			methodurl = prop.getProperty("methodurl");
		}
		resultMap.put("methodurl", methodurl);
		resultMap.put("PASSMSG", Md5Util.md5(pd.getString("studentPkid")));
		resultMap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), resultMap);
	}

	/**
	 * 
	 * <p>
	 * 描述:缴费记录详情
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月19日 下午2:36:45
	 * @return
	 */
	@RequestMapping(value = "/getPayOrderDetailListList.json")
	@ResponseBody
	public ModelAndView getPayOrderDetailListList() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> resultMap = new HashMap<>();
		//
		if (!StringUtils.isEmpty(pd.get("createModels"))) {
			List<String> createModels = new ArrayList<>();
			String[] createModelArray = pd.get("createModels").toString()
					.split(",");
			for (int i = 0; i < createModelArray.length; i++) {
				createModels.add(createModelArray[i]);
			}
			pd.put("createModels", createModels);
		}
		// 获得线下缴费记录列表
		List<PageData> payOrderDetailList = payManageService
				.getPayOrderDetailList(pd);
		if (payOrderDetailList != null && payOrderDetailList.size() > 0) {
			int count = 0;
			for (PageData payOrderDetail : payOrderDetailList) {
				pd.put("orderDetailPkid", payOrderDetail.get("PKID"));
				if (count == 0) {
					payOrderDetail.put("PAY_ITEM_CJSJ",
							DateUtil.fomatDate(new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss").parse(payOrderDetail
									.get("PAY_ITEM_CJSJ").toString()),
									"yyyy-MM-dd HH:mm:ss"));
				}
				count++;

				// 获得缴费记录金额和支付方式列表
				List<PageData> payOrderDetailMoneyList = payManageService
						.getPayOrderDetailMoneyList(pd);
				if (payOrderDetailMoneyList != null
						&& payOrderDetailMoneyList.size() > 0) {
					Map<String, String> map = new HashMap<>();
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < payOrderDetailMoneyList.size(); i++) {
						PageData payOrderDetailMoney = payOrderDetailMoneyList
								.get(i);
						if (payOrderDetailMoney.get("PAY_MODE") != null) {
							// 金额
							String money = payOrderDetailMoney.get("MONEY") != null ? payOrderDetailMoney
									.get("MONEY").toString() : "0";
							// 将各种支付方式和金额放入集合
							sb.append(payOrderDetailMoney.get("PAY_MODE")
									.toString());
							sb.append(":");
							sb.append(money);
							if (i != payOrderDetailMoneyList.size() - 1) {
								sb.append("|");
							}
						}

					}
					payOrderDetail.put("ZFFS", sb.toString());
				}
				payOrderDetail.put(
						"CJSJ",
						StringUtils.isEmpty(payOrderDetail.get("CJSJ")) ? ""
								: DateUtil.fomatDate(new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss")
										.parse(payOrderDetail.get("CJSJ")
												.toString()),
										"yyyy-MM-dd HH:mm:ss"));
			}
		}
		resultMap.put("result", "success");
		resultMap.put("payOrderDetailList", payOrderDetailList);
		return new ModelAndView(new MappingJackson2JsonView(), resultMap);
	}

	/**
	 * 
	 * <p>
	 * 描述:缴费名单弹出文件选择
	 * </p>
	 * 
	 * @author Administrator 胡文浩
	 * @date 2017年8月18日 上午11:30:16
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/import-list.json")
	public ModelAndView importList() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.setViewName("system/import/importjffb");
		mv.addObject("uuid", this.get32UUID());
		mv.addObject("item_pkid", pd.getString("item_pkid"));
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 
	 * <p>描述:添加缴费名单页面</p>
	 * @author wn 王宁
	 * @date 2018年12月11日 下午3:07:44
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addItemList.json")
	public ModelAndView addItemList() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("item_pkid", pd.getString("item_pkid"));
		mv.addObject("pd", pd);
		mv.setViewName("system/pay/addItemList");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:验证发布项目名称是否重叠
	 * </p>
	 * 
	 * @author Administrator 胡文浩
	 * @date 2017年8月18日 下午10:38:45
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/yanzmc.json")
	@ResponseBody
	public ModelAndView yanzmc() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> pdList = payManageService.yanzmc(pd);
		if (pdList != null && pdList.size() != 0) {
			jsonmap.put("result", "false");
		} else {
			jsonmap.put("result", "success");
		}
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:获得缴费记录条数
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月21日 下午17:38:45
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/getPayOrderDetailCount.json")
	@ResponseBody
	public ModelAndView getPayOrderDetailCount() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdd = payManageService.getPayOrderDetailCount(pd);
		if (pdd != null && !"0".equals(pdd.getString("CCOUNT"))) {
			jsonmap.put("result", "success");
		} else {
			jsonmap.put("result", "fail");
		}
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:缴费管理模块修改模块
	 * </p>
	 * 
	 * @author Administrator 胡文浩
	 * @date 2017年8月22日 下午8:45:19
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/updatejfgz.json")
	public ModelAndView updatejfgz() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();// json结果map
		PageData pd = new PageData();
		PageData pdItem = new PageData();
		pd = this.getPageData();
		pdItem = payManageService.getItem(pd);
		List<PageData> list = null;
		list = payManageService.rulelist(pd);
		JSONArray json = new JSONArray();
		PageData pd_rule_fb = null;
		JSONObject jo = null;
		for (PageData a : list) {
			/*
			 * 根据优惠类型查询对应的数据
			 */
			jo = new JSONObject();
			String YOUHUI_TYPE = a.getString("YOUHUI_TYPE");//优惠类型
			if("1".equals(YOUHUI_TYPE)){//表示合作学校
				pd_rule_fb = payManageService.getRuleFB(a);//获取规则附表数据
				jo.put("HZXY", pd_rule_fb.getString("HZXY"));
			}else if("2".equals(YOUHUI_TYPE)){//表示报名时间
				pd_rule_fb = payManageService.getRuleFB(a);//获取规则附表数据
				jo.put("BAOMINGTIMEQ", pd_rule_fb.getString("BAOMINGTIMEQ"));
				jo.put("BAOMINGTIMEZ", pd_rule_fb.getString("BAOMINGTIMEZ"));
			}else if("3".equals(YOUHUI_TYPE)){//表示预交
			}else if("4".equals(YOUHUI_TYPE)){//表示联考成绩
				pd_rule_fb = payManageService.getRuleFB(a);//获取规则附表数据
				jo.put("CENGCI", pd_rule_fb.getString("CENGCI"));
				jo.put("ZUIGAOFEN", pd_rule_fb.getString("ZUIGAOFEN"));
				jo.put("ZUIDIFEN", pd_rule_fb.getString("ZUIDIFEN"));
			}else if("5".equals(YOUHUI_TYPE)){//表示班级
				pd_rule_fb = payManageService.getRuleFB(a);//获取规则附表数据
				jo.put("T_CLASS_PKIDS", pd_rule_fb.getString("T_CLASS_PKIDS"));
			}else if("6".equals(YOUHUI_TYPE)){//表示美院
				pd_rule_fb = payManageService.getRuleFB(a);//获取规则附表数据
				jo.put("SYS_MEI_DEPARTMENT_PKIDS", pd_rule_fb.getString("SYS_MEI_DEPARTMENT_PKIDS"));
			}
			jo.putAll(a);
			json.add(jo);
		}
		String CREATEMODE = pd.getString("ITEMLIST_CREATEMODE");
		jsonmap.put("CREATEMODE", CREATEMODE);
		jsonmap.put("json", json);
		jsonmap.put("pdItem", pdItem);
		jsonmap.put("pd", pd);
		jsonmap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}
	/**
	 * 
	 * <p>描述:查询下拉列表</p>
	 * @author wn 王宁
	 * @date 2018年12月10日 下午2:41:44
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSelectTypeList.json")
	@ResponseBody
	public ModelAndView getSelectTypeList() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pd_select = new PageData();
		if("1".equals(pd.getString("TYPE"))){//表示合作学校
			pd_select = payManageService.getHzxyName(pd);
		}
		jsonmap.put("result", "success");
		jsonmap.put("pdselect", pd_select);
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}
	/**
	 * 
	 * <p>
	 * 描述:导出名单 单独写的
	 * </p>
	 * 
	 * @author Administrator 康程亮
	 * @date 2017年8月22日 下午8:19:38
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportStuPayList.json")
	public ModelAndView exportStuPayList(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		String[] headerNames = new String[] { "身份证号", "姓名", "所属组织","年级","学生类型","批次", "项目名称",
				"费用", "优惠", "应收金额", "实收金额", "缴费状态","欠费金额" };
		String[] bodyNames = new String[] { "SHENFENZHENGHAO", "XINGMING",
				"ZUZHINAME","NIANJI","CENGCI_NAME","PICI_NAME","PAYITEM", "COST", "DISCOUNT", "AMOUNTRECEIVABLE",
				"AMOUNTCOLLECTION","STATUS", "AMOUNTQF" };

		List<String> DEPARTMENT_PKIDList = new ArrayList<>();
		if (!StringUtils.isEmpty(pd.getString("DEPARTMENT_PKID"))) {
			String[] DEPARTMENT_PKIDArray = pd.getString("DEPARTMENT_PKID")
					.split(",");
			for (int i = 0; i < DEPARTMENT_PKIDArray.length; i++) {
				if (!StringUtils.isEmpty(DEPARTMENT_PKIDArray[i])) {
					DEPARTMENT_PKIDList.add(DEPARTMENT_PKIDArray[i]);
				}
			}

		}
		pd.put("DEPARTMENT_PKID", DEPARTMENT_PKIDList);

		List<PageData> pdList = payManageService.exportStuPayList(pd);
		Map<String, Object> dataMap = Utils.toExcel(headerNames, bodyNames,
				pdList);
		dataMap.put("fileName", "学生缴费名单表");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:删除缴费名单
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月23日 下午17:38:45
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/deletePayItemListObject.json")
	@ResponseBody
	public ModelAndView deletePayItemListObject() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		payManageService.deletePayItemListObject(pd);
		jsonmap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:修改缴费设置中导入方式的删除原有数据按钮操作
	 * </p>
	 * 
	 * @author Administrator 胡文浩
	 * @date 2017年8月26日 下午1:16:13
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletedr.json")
	@ResponseBody
	public ModelAndView deletedr() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		payManageService.deletedr(pd);
		jsonmap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}
	/**
	 * 
	 * <p>描述:保存添加缴费名单</p>
	 * @author wn 王宁
	 * @date 2018年12月11日 下午4:10:10
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveAddItemList.json")
	@ResponseBody
	public ModelAndView saveAddItemList() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		payManageService.saveAddItemList(pd);
		jsonmap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}
	/**
	 * 
	 * <p>
	 * 描述:得到导入的学生list
	 * </p>
	 * 
	 * @author Administrator 胡文浩
	 * @date 2017年8月26日 下午2:34:28
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/getxslist.json")
	public ModelAndView getxslist() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();// json结果map
		PageData pd = new PageData();
		PageData pdd = null;
		PageData pdItem = new PageData();
		pd = this.getPageData();
		List<PageData> successList = new ArrayList<PageData>(); // List<PageData>
																// list = null;
		List<PageData> list = null;
		list = payManageService.getlist(pd);
		JSONArray json = new JSONArray();
		for (PageData a : list) {
			pdd = new PageData();
			pdd.put("cardNo", a.get("CARDNO"));
			pdd.put("stuNumber", a.get("STUNUMBER"));
			pdd.put("discountMoney", a.get("DISCOUNT_MONEY"));
			pdd.put("stuName", a.get("STUNAME"));
			successList.add(pdd);
		}
		String CREATEMODE = pd.getString("ITEMLIST_CREATEMODE");
		jsonmap.put("xsList", JSONArray.fromObject(successList).toString());
		jsonmap.put("CREATEMODE", CREATEMODE);
		jsonmap.put("json", json);
		jsonmap.put("pdItem", pdItem);
		jsonmap.put("pd", pd);

		jsonmap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	@RequestMapping(value = "/selectstu.json")
	public ModelAndView selectStuInfo() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/pay/stuinfolist");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:获得缴费名单
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月28日 下午02:38:45
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPayItemListForPrint.json")
	@ResponseBody
	public ModelAndView getPayItemListForPrint() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();

		jsonmap.put("result", "fail");

		String payItemListPkids = pd.getString("payItemListPkids");
		if (!StringUtils.isEmpty(payItemListPkids)) {
			List<String> payItemListPkidList = new ArrayList<>();
			String[] payItemListPkidsArray = payItemListPkids.split(",");
			for (String payItemListPkid : payItemListPkidsArray) {
				payItemListPkidList.add(payItemListPkid);
			}
			pd.put("payItemListPkidList", payItemListPkidList);

			List<PageData> payItemListList = payManageService
					.getPayItemListForPrint(pd);
			if (payItemListList != null && payItemListList.size() > 0) {
				jsonmap.put("payItemListList", payItemListList);
				jsonmap.put("result", "success");
			}
		}

		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 
	 * <p>
	 * 描述:查询学生table列表
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月29日 下午16:57:06
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudentQuerylistPage")
	public ModelAndView getStudentQuerylistPage(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		List<PageData> list = null;
		if (!StringUtils.isEmpty(pd.getString("conditions"))) {
			String conditions = new String(pd.getString("conditions").getBytes(
					"ISO8859-1"), "utf-8");
			pd.put("conditions", conditions);
		}

		page.setPd(pd);
		list = payManageService.getStudentQuerylistPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:获取要添加的学生名单</p>
	 * @author wn 王宁
	 * @date 2018年12月11日 下午3:24:26
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getaddStutable")
	public ModelAndView getaddStutable(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		List<PageData> list = null;
		page.setPd(pd);
		list = payManageService.getaddStutablelistPage(page);
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
	 * 描述:学生列表表格数据
	 * </p>
	 * 
	 * @author Administrator 康程亮
	 * @date 2017年8月2日 下午5:41:04
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getstutable.json")
	public ModelAndView stuinfotablejson(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		List<PageData> list = stuInfoService.stuinfo_list(page);
		Properties prop = new Properties();
		InputStream in = null;
		in = LoginController.class.getClassLoader().getResourceAsStream(
				"conf.properties");
		prop.load(in);
		String methodurl = "";
		if (prop != null) {
			methodurl = prop.getProperty("methodurl");
		}
		for (PageData pageData : list) {
			pageData.put("PASSMSG", Md5Util.md5(pageData.getString("PKID")));
			pageData.put("METHODURL", methodurl);
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
	 * 描述:获得学生
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年8月31日 下午02:38:45
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getStudentQuerylist2.json")
	@ResponseBody
	public ModelAndView getStudentQuerylist2() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();


		List<PageData> stuList = payManageService.getStudentQuerylist2(pd);
		jsonmap.put("result", "success");
		jsonmap.put("stuList", stuList);
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}

	/**
	 * 关闭项目
	 * <p>
	 * 描述:
	 * </p>
	 * 
	 * @author Administrator wzz
	 * @date 2018年12月10日 下午2:11:55
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/closeProject.json")
	public ModelAndView closeProject(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();// json结果map
		PageData pd = new PageData();
		pd = this.getPageData();
		payManageService.updatePayItemListStatusIsClose(pd);
		jsonmap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}
	/**
	 * 
	 * <p>描述:激活缴费名单</p>
	 * @author wzz
	 * @date 2018年12月10日 下午5:09:01
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/jihuoItemList")
	public ModelAndView jihuoItemList(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		payManageService.updateItemList(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 获得所有缴费项目
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getAllPayItemListList3.json")
	@ResponseBody
	public ModelAndView getAllPayItemListList3() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> resultMap = new HashMap<>();
		List<PageData> pdTypeList = null;
		PageData pdTypeName = null;
		//入学年份班型查询对应缴费类型除去预交
		pdTypeList = payManageService.getPayTypePkid(pd);
		if(pdTypeList!=null&&pdTypeList.size()>0){
			for(int i =0;i<pdTypeList.size();i++){
				PageData pdType = pdTypeList.get(i);
				pdTypeName = payManageService.getPayTypeName(pdType);
				pdTypeList.get(i).put("pdTypeName", pdTypeName.getString("PAY_STYLE_NAME"));
				if("预交".equals(pdTypeName.getString("PAY_STYLE_NAME"))){
					pdTypeList.remove(pdTypeList.get(i));
				}
			}
		}
		PageData pdYjIsExist = payManageService.getYjIsBd(pd);
		if(pdYjIsExist!=null){
			//预交年份班型缴费类型
			PageData pdYjType = payManageService.getPayTypeYjPkid(pd);
			if(pdYjType!=null){
				pdYjType.put("pdTypeName", pdYjType.getString("PAY_STYLE_NAME"));
				pdTypeList.add(pdYjType);
			}
		}
		
		resultMap.put("pdTypeList", pdTypeList);
		resultMap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),resultMap);
	}
	/**
	 * 添加缴费项目名单
	 * <p>
	 * 描述:
	 * </p>
	 * 
	 * @author Administrator 陈超超
	 * @date 2017年9月8日 下午5:11:55
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertPayItemList.json")
	public ModelAndView insertPayItemList(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd.put("DISCOUNT", "不优惠");
			payManageService.insertPayItemList(pd);
			jsonmap.put("result", "success");
		} catch (Exception e) {
			jsonmap.put("result", "fail");
			jsonmap.put("failMsg", e.getMessage());
		}
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}
	/**
	 * 
	 * <p>描述:删除线下缴费单个人的名单</p>
	 * @author ning 王宁
	 * @date 2018年6月15日 上午8:46:19
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/delitemlist")
	public ModelAndView delitemlist(Page page) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdIs = payManageService.getPayItemListByPkid(pd);
		if(!"0".equals(pdIs.getString("AMOUNTCOLLECTION"))){
			map.put("result", "isJf");
		}else{
			payManageService.delitemlist(pd);
			map.put("result", "success");
		}
		
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	
	
	
	
	/**
	 * 
	 * <p>描述:删除缴费名单</p>
	 * @author Administrator 胡文浩
	 * @date 2018年12月10日 上午9:36:09
	 * @return
	 * @throws Exception
	 */
	
	
	@RequestMapping(value = "/deletePayItemList.json")
	@ResponseBody
	public ModelAndView deletePayItemList() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> pdTypeList = null;
		String sfz="";
		try{
			String[] strArray=null;
			strArray=pd.getString("pkids").split(",");
			if(strArray.length>0){
				
			}
			for(int i=0;i<strArray.length;i++){
			pdTypeList = payManageService.getlistbyPkid(strArray[i]);
			if(pdTypeList.size()>0 && pdTypeList!=null){//order  不为空
				sfz+=pdTypeList.get(0).getString("SHENFENZHENGHAO")+",";
			}else{
				payManageService.deletePayItemListbypkid(strArray[i]);
			}
			}
			//getlistbyPkid
			
			//pdTypeList = payManageService.getPayTypePkid(pd);
			
			
			//payManageService.deletePayItemListObject(pd);
			
		}catch (Exception e) {
			jsonmap.put("result", "false");
			e.printStackTrace();
		}
	//	payManageService.deletePayItemListObject(pd);
		jsonmap.put("result", "success");
		jsonmap.put("sfz", sfz);
		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}
	
	
	/**
	 * 
	 * <p>描述:调整应收</p>
	 * @author Administrator 胡文浩
	 * @date 2018年12月10日 下午3:14:34
	 * @param pkid
	 * @param amountreceivable
	 * @param cost
	 * @param discountMode
	 * @param discount
	 * @return
	 * @throws Exception
	 */
	
	
	@RequestMapping(value = "/updatePayItemListTZ.json")
	public ModelAndView updatePayItemListTZ() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();// json结果map
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String username = user.getUSERNAME();
		pd.put("CCZ", username);
		BigDecimal num1 = new BigDecimal(pd.getString("AMOUNTRECEIVABLE"));
		BigDecimal num2 = new BigDecimal(pd.getString("TZHMONEY"));
		//BigDecimal result = num1.add(num2);
		String result = num2.subtract(num1).toString();
		pd.put("CHAE", result);
		/*if(pd.getString("AMOUNTRECEIVABLE").equals(pd.getString("TZHMONEY"))){
			pd.put("STATUS", 0);
		}else{
			pd.put("STATUS", 1);
		}*/
		
		try {
			payManageService.updatePayItemListTZ(pd);
			jsonmap.put("result", "success");
		} catch (Exception e) {
			jsonmap.put("result", "fail");
		}

		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}
	
/**
 * 
 * <p>描述:减免登记</p>
 * @author Administrator 胡文浩
 * @date 2018年12月10日 下午5:24:09
 * @return
 * @throws Exception
 */
	
	
	@RequestMapping(value = "/updatePayItemListJM.json")
	public ModelAndView updatePayItemListJM() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();// json结果map
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String username = user.getUSERNAME();
		pd.put("CCZ", username);
		BigDecimal num1 = new BigDecimal(pd.getString("jmdjAMOUNTRECEIVABLE"));
		BigDecimal num2 = new BigDecimal(pd.getString("jmdjTZHMONEY"));
		//BigDecimal result = num1.add(num2);
		String result = num2.subtract(num1).toString();
		pd.put("CHAE", result);
		try {
			payManageService.updatePayItemListJM(pd);
			jsonmap.put("result", "success");
		} catch (Exception e) {
			jsonmap.put("result", "fail");
		}

		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}
	/**
	 * 
	 * <p>描述:费用变动记录</p>
	 * @author Administrator 胡文浩
	 * @date 2018年12月11日 上午10:12:32
	 * @param page
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "/getManeyChangelistPage.json")
	public ModelAndView getManeyChangelistPage(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		List<PageData> list = payManageService.getManeyChangelistPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:缴费记录</p>
	 * @author Administrator 胡文浩
	 * @date 2018年12月11日 下午2:17:17
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getManeyJLlistPage.json")
	public ModelAndView getManeyJLlistPage(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		List<PageData> list = payManageService.getManeyLOGlistPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:收费中添加缴费项目金额自动生成</p>
	 * @author Administrator wzz
	 * @date 2018年12月21日 下午2:17:17
	 */
	@ResponseBody
	@RequestMapping(value = "/getItemCost.json")
	public ModelAndView getItemCost() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();

		Double dbCost = payManageService.getItemCost(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pd", pd);
		map.put("dbCost", dbCost);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:学生退学</p>
	 * @author wn 王宁
	 * @date 2019年2月13日 下午3:10:44
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/tuixue.json")
	public ModelAndView tuixue() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		payManageService.updateStuTuiXue(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pd", pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 判断字符是否是中文
	 * 
	 * @param c
	 *            字符
	 * @return 是否是中文
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否是乱码
	 * 
	 * @param strName
	 *            字符串
	 * @return 是否是乱码
	 */
	private boolean isMessyCode(String strName) {
		Pattern p = Pattern.compile("\\s*|t*|r*|n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		float chLength = ch.length;
		float count = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {
				if (!isChinese(c)) {
					count = count + 1;
				}
			}
		}
		float result = count / chLength;
		if (result > 0.4) {
			return true;
		} else {
			return false;
		}

	}
}
