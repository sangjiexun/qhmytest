package com.fh.controller.system.rule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.shiro.session.Session;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.alibaba.druid.util.StringUtils;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Department;
import com.fh.entity.system.Project;
import com.fh.entity.system.User;
import com.fh.orgtree.OrgTree;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.rule.RuleManager;
import com.fh.service.system.seq.SeqManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DictZiDianSelect;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.RuleMessge;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.keman.zhgd.common.util.UuidUtil;

/** 
 * 说明：组织机构
 * 创建人：zhoudibo
 * 创建时间：2015-12-16
 */
@Controller
@RequestMapping(value="/rule")
public class RuleController extends BaseController {
	
	String menuUrl = "rule/rule-list.php"; //菜单地址(权限用)
	@Resource(name="ruleService")
	private RuleManager ruleService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="seqService")
	private SeqManager seqService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	
	
	@RequestMapping(value="/rule-list.php")
	public ModelAndView controllist(Page page) throws Exception{
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData qygz = new PageData();
		PageData xzsj = new PageData();
		PageData mrsj = new PageData();
		PageData sjpd = new PageData();
		PageData xmpd = new PageData();
		PageData shangji = new PageData();  //查找上级参数
		PageData shangjinl = new PageData();  //查找上级参数
		PageData shangjizs = new PageData();  //查找上级参数
		PageData xmshangji = new PageData();  //项目查找上级参数
		PageData xmshangjinl = new PageData();  //查找上级参数
		PageData xmshangjizs = new PageData();  //查找上级参数
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String  ID=(String) session.getAttribute(Const.SELECTED_ZZJG);
		if(ID==null ){
			mv.setViewName("system/labour/error");
		}else{
			String uuid=UuidUtil.get32UUID();
			Map<String,Object> map= RuleMessge.rulemrz;
			pd.put("ID", ID);
			pd = ruleService.gettypeid(pd);
			String type="";
			String qybianma="";
			if(pd==null){
				type="3";
				pd = new PageData();
				pd.put("XMID", ID);
				
			}else if("1".equals(pd.get("TYPEID").toString())){
				type="1";
				pd.put("QYID", pd.get("BIANMA").toString());
				qybianma=pd.get("BIANMA").toString();
			}else if("2".equals(pd.get("TYPEID").toString())){
				type="2";
				pd.put("FGSID", pd.get("BIANMA").toString());
				qybianma=pd.get("BIANMA").toString();
			}
			
			qygz = ruleService.getqygz(pd);
			
			if("1".equals(type)){        //企业
				if(qygz==null){
					mrsj.put("PKID",uuid );
					mrsj.put("QIYE_BIANMA",qybianma);
					mrsj.put("YOUCHENGFAJLKYDJ",map.get("YOUCHENGFAJLKYDJ"));
					mrsj.put("TONGGONGNL",map.get("TONGGONGNL"));
					mrsj.put("NANXINGTXNL",map.get("NANXINGTXNL"));
					mrsj.put("NVXINGTXNL",map.get("NVXINGTXNL"));
					mrsj.put("SHIFOUBXSCZS",map.get("SHIFOUBXSCZS"));
					mrsj.put("HEIMINGDANX",map.get("HEIMINGDANX"));
					mrsj.put("CHENGFA_JZXJXG",map.get("CHENGFA_JZXJXG"));
					mrsj.put("NIANLING_JZXJXG",map.get("NIANLING_JZXJXG"));
					mrsj.put("ZHENGSHU_JZXJXG",map.get("ZHENGSHU_JZXJXG"));
					mrsj.put("TGBKDJ_CK",map.get("TGBKDJ_CK"));
					mrsj.put("NXTXBDJ_CK",map.get("NXTXBDJ_CK"));
					mrsj.put("VXTXBDJ_CK",map.get("VXTXBDJ_CK"));
					ruleService.saveqy(mrsj);
					xzsj=ruleService.getqygz(pd);
					mv.addObject("varList", xzsj);
					
				}else{
					mv.addObject("varList", qygz);
				}
				
			}else if("2".equals(type)){          //分公司
				sjpd.put("BIANMA", qybianma);
				sjpd.put("LENGTH", 6);
				
				if(qygz==null){
					mrsj.put("PKID",uuid );
					mrsj.put("QIYE_BIANMA",qybianma);
					mrsj.put("YOUCHENGFAJLKYDJ",map.get("YOUCHENGFAJLKYDJ"));
					mrsj.put("CHENGDUJIBIE","");
					mrsj.put("TONGGONGNL",map.get("TONGGONGNL"));
					mrsj.put("NANXINGTXNL",map.get("NANXINGTXNL"));
					mrsj.put("NVXINGTXNL",map.get("NVXINGTXNL"));
					mrsj.put("SHIFOUBXSCZS",map.get("SHIFOUBXSCZS"));
					mrsj.put("CHENGFA_JZXJXG",map.get("CHENGFA_JZXJXG"));
					mrsj.put("NIANLING_JZXJXG",map.get("NIANLING_JZXJXG"));
					mrsj.put("ZHENGSHU_JZXJXG",map.get("ZHENGSHU_JZXJXG"));
					mrsj.put("TGBKDJ_CK",map.get("TGBKDJ_CK"));
					mrsj.put("NXTXBDJ_CK",map.get("NXTXBDJ_CK"));
					mrsj.put("VXTXBDJ_CK",map.get("VXTXBDJ_CK"));
					ruleService.savefgs(mrsj);
					xzsj=ruleService.getqygz(pd);
					shangji=ruleService.shangji(sjpd);
					shangjinl=ruleService.shangjinl(sjpd);
					shangjizs=ruleService.shangjizs(sjpd);
					mv.addObject("varList", xzsj);
					mv.addObject("sjList", shangji);
					mv.addObject("nlsjList", shangjinl);
					mv.addObject("zssjList", shangjizs);
				
					
					
				}else{
					shangji=ruleService.shangji(sjpd);
					shangjinl=ruleService.shangjinl(sjpd);
					shangjizs=ruleService.shangjizs(sjpd);
					mv.addObject("nlsjList", shangjinl);
					mv.addObject("zssjList", shangjizs);
					mv.addObject("sjList", shangji);
					mv.addObject("varList", qygz);
				}
				
				
			}else if("3".equals(type)){	
				String XMBM = (String) session.getAttribute(Const.SELECTED_BIANMA);
				//sjpd.put("BIANMA", XMBM);
				sjpd.put("BIANMA", XMBM.substring(5,XMBM.length()));
				sjpd.put("LENGTH", 6);//项目
				if(qygz==null){
					
					//xmshangji=ruleService.xmshangji(xmpd);
					/*if(xmshangji ==null){*/
						mrsj.put("PKID",uuid );
						mrsj.put("XIANGMU_BIANMA",XMBM);
						mrsj.put("YOUCHENGFAJLKYDJ",map.get("YOUCHENGFAJLKYDJ"));
						mrsj.put("CHENGDUJIBIE","");
						mrsj.put("TONGGONGNL",map.get("TONGGONGNL"));
						mrsj.put("NANXINGTXNL",map.get("NANXINGTXNL"));
						mrsj.put("NVXINGTXNL",map.get("NVXINGTXNL"));
						mrsj.put("SHIFOUBXSCZS",map.get("SHIFOUBXSCZS"));
						mrsj.put("CHENGFA_JZXJXG",map.get("CHENGFA_JZXJXG"));
						mrsj.put("NIANLING_JZXJXG",map.get("NIANLING_JZXJXG"));
						mrsj.put("ZHENGSHU_JZXJXG",map.get("ZHENGSHU_JZXJXG"));
						mrsj.put("TGBKDJ_CK",map.get("TGBKDJ_CK"));
						mrsj.put("NXTXBDJ_CK",map.get("NXTXBDJ_CK"));
						mrsj.put("VXTXBDJ_CK",map.get("VXTXBDJ_CK"));
						mrsj.put("SHIFOUQYGRJZ",map.get("SHIFOUQYGRJZ"));
						ruleService.savexm(mrsj);
						xzsj=ruleService.getqygz(pd);
						
						shangji=ruleService.xmshangji(sjpd);
						shangjinl=ruleService.xmshangjinl(sjpd);
						shangjizs=ruleService.xmshangjizs(sjpd);
						mv.addObject("nlsjList", shangjinl);
						mv.addObject("zssjList", shangjizs);
						mv.addObject("sjList", shangji);
						mv.addObject("varList", xzsj);
			
					
				}else{
					shangji=ruleService.xmshangji(sjpd);
					shangjinl=ruleService.xmshangjinl(sjpd);
					shangjizs=ruleService.xmshangjizs(sjpd);
					mv.addObject("nlsjList", shangjinl);
					mv.addObject("zssjList", shangjizs);
					mv.addObject("sjList", shangji);
					mv.addObject("varList", qygz);
				}
				
				
			}
			mv.setViewName("system/rule/rule");
		}
		return mv;
	}
	
	
	
	
	
	@RequestMapping(value="/deletedy.json")
	@ResponseBody
	public ModelAndView deletedy() throws Exception{
		
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "success";
		ruleService.deletedy(pd);	//执行删除
		map.put("result", errInfo);
		return new ModelAndView(new MappingJackson2JsonView(),map);
	}
	
	
	
	

	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAddMQ")
	public ModelAndView goAddMQ()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/rule/addmq");
		return mv;
		
	}	
	@RequestMapping(value="/goAddDY")
	public ModelAndView goAddDY()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> listsf=ruleService.getshengfen(pd);
		String  djtxv=DictZiDianSelect.DengJiEnum.getValueByName("登记提醒");
		String  jzdjv=DictZiDianSelect.DengJiEnum.getValueByName("禁止登记");
		mv.addObject("listsf", listsf);
		mv.addObject("djtxv", djtxv);
		mv.addObject("jzdjv", jzdjv);
		mv.setViewName("system/rule/adddy");
		return mv;
		
	}	
	
	
	@ResponseBody
	@RequestMapping(value="/adddiyu")
	public ModelAndView adddiyu() throws Exception{
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String  xmbianma=(String) session.getAttribute(Const.SELECTED_BIANMA);
		String QYBM=xmbianma.substring(5,11);
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("XIANGMUBM", xmbianma);
		pd.put("QIYE_BIANMA", QYBM);
		String uuid=UuidUtil.get32UUID();
		pd.put("PKID", uuid);
		ruleService.savediyu(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	@ResponseBody
	@RequestMapping(value="/addmq")
	public ModelAndView addmq() throws Exception{
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String  xmbianma=(String) session.getAttribute(Const.SELECTED_BIANMA);
		String QYBM=xmbianma.substring(5,11);
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("XIANGMUBM", xmbianma);
		pd.put("QIYE_BIANMA", QYBM);
		String uuid=UuidUtil.get32UUID();
		pd.put("PKID", uuid);
		ruleService.savemq(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	@ResponseBody
    @RequestMapping(value = "/mingcheng.json")
	public ModelAndView mingcheng(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> list = ruleService.listformc(pd);
		Map<String, Object> map = new HashMap<String, Object>();
            //用户存在（存在的用户是当前用户，登录名一致，校验通过，否则校验不通过）
            if(list.isEmpty()){
                map.put("valid",true);
            }else {
                map.put("valid", false);
            }
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	
	
	
	
	
	/**
	 * 获取表格数据 json
	 * @return
	 * @throws Exception
	 */
	@ResponseBody  
	@RequestMapping(value = "/mztablejson")
	public ModelAndView mztablejson(Page page) throws Exception {
		PageData pd = new PageData();
		PageData pdmz = new PageData();
		pd = this.getPageData();
		/*int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));*/
		Object keyValue = pd.get("seText");
		if(keyValue != null){
			keyValue = new String(keyValue.toString().getBytes("ISO8859-1"), "utf-8");
			pd.put("seText", keyValue);
		}
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String  xmbianma=(String) session.getAttribute(Const.SELECTED_BIANMA);
		pd.put("XIANGMU_BIANMA", xmbianma);
		/*page.setShowCount(limit);
		page.setCurrentPage(pageindex);*/
		page.setPd(pd);
		String roleid=user.getROLE_ID();
		List<PageData> list=null;
			 list = ruleService.mzlistPage(page);
		if(list==null || list.size()==0){
			List<PageData> mz=ruleService.mzlist(page);
			for(int i=0;i<mz.size();i++){
				String uuid=UuidUtil.get32UUID();
				pdmz.put("PKID", uuid);
				pdmz.put("XIANGMU_BIANMA", xmbianma);
				String minzu=mz.get(i).getString("MINZU");
				pdmz.put("MINZU", minzu);
				ruleService.savemz(pdmz);
			}
		}
		list = ruleService.mzlistPage(page);	 
			 
			 
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageNumber", page.getCurrentPage());//当前第几页
		map.put("total", page.getTotalResult());//总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(),map);
	}
	
	/**
	 * 获取表格数据 json
	 * @return
	 * @throws Exception
	 */
	@ResponseBody  
	@RequestMapping(value = "/dytablejson")
	public ModelAndView dytablejson(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		Object keyValue = pd.get("seText");
		if(keyValue != null){
			keyValue = new String(keyValue.toString().getBytes("ISO8859-1"), "utf-8");
			pd.put("seText", keyValue);
		}
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String  xmbianma=(String) session.getAttribute(Const.SELECTED_BIANMA);
		pd.put("XIANGMU_BIANMA", xmbianma);
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		List<PageData> list=null;
			 list = ruleService.dylistPage(page);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageNumber", page.getCurrentPage());//当前第几页
		map.put("total", page.getTotalResult());//总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(),map);
	}
	
	
	
	/**
	 * 获取表格数据 json
	 * @return
	 * @throws Exception
	 */
	@ResponseBody  
	@RequestMapping(value = "/tablejson")
	public ModelAndView tablejson(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		Object keyValue = pd.get("seText");
		if(keyValue != null){
			keyValue = new String(keyValue.toString().getBytes("ISO8859-1"), "utf-8");
			pd.put("seText", keyValue);
		}
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String  xmbianma=(String) session.getAttribute(Const.SELECTED_BIANMA);
		pd.put("XIANGMU_BIANMA", xmbianma);
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		String roleid=user.getROLE_ID();
		List<PageData> list=null;
			 list = ruleService.mqlistPage(page);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageNumber", page.getCurrentPage());//当前第几页
		map.put("total", page.getTotalResult());//总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(),map);
	}
	
	
	
	
	
	
	@RequestMapping(value = "/mzwu.json")
	@ResponseBody
	public Object mzwu(HttpServletRequest requests) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String RID = pd.getString("PKID");
		String ROLEIDS[] = RID.split(",");
		String errInfo = "success";
		List<PageData> list = new ArrayList<>();
		for(String ROLE_ID : ROLEIDS){
				PageData pdd = new PageData();
				pdd.put("PKID", ROLE_ID);
				list.add(pdd);
		}
		ruleService.updatemzwuall(list);
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	@RequestMapping(value = "/mzdjtx.json")
	@ResponseBody
	public Object mzdjtx(HttpServletRequest requests) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String RID = pd.getString("PKID");
		String ROLEIDS[] = RID.split(",");
		String errInfo = "success";
		List<PageData> list = new ArrayList<>();
		for(String ROLE_ID : ROLEIDS){
				PageData pdd = new PageData();
				pdd.put("PKID", ROLE_ID);
				list.add(pdd);
		}
		ruleService.updatemzdjtxall(list);
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	@RequestMapping(value = "/mzjzdj.json")
	@ResponseBody
	public Object mzjzdj(HttpServletRequest requests) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String RID = pd.getString("PKID");
		String ROLEIDS[] = RID.split(",");
		String errInfo = "success";
		List<PageData> list = new ArrayList<>();
		for(String ROLE_ID : ROLEIDS){
				PageData pdd = new PageData();
				pdd.put("PKID", ROLE_ID);
				list.add(pdd);
		}
		ruleService.updatemzjzdjall(list);
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/changemzwu")
	public ModelAndView changemzwu() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		ruleService.updatechangemzwu(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	@ResponseBody
	@RequestMapping(value="/changedjtx")
	public ModelAndView changedjtx() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		ruleService.updatechangedjtx(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	@ResponseBody
	@RequestMapping(value="/changejzdj")
	public ModelAndView changejzdj() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		ruleService.updatechangejzdj(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	
	@ResponseBody
	@RequestMapping(value="/changemqty")
	public ModelAndView changemqty() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		ruleService.updatechangemqty(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	@ResponseBody
	@RequestMapping(value="/changemqqy")
	public ModelAndView changemqqy() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		ruleService.updatechangemqqy(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/yujingrule")
	public ModelAndView yujingrule() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> mapkq= RuleMessge.rulemrz;
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String  xmbianma=(String) session.getAttribute(Const.SELECTED_BIANMA);
		pd.put("XIANGMU_BIANMA", xmbianma);
		PageData yujingxx = ruleService.getyujing(pd);
		if(yujingxx==null){    //项目 考勤规则无值插入默认值
			String uuid=UuidUtil.get32UUID();
			pd.put("PKID", uuid);
			pd.put("CHUQIN_BANZU", mapkq.get("CHUQIN_BANZU"));
			pd.put("CHUQIN_DUIWU", mapkq.get("CHUQIN_DUIWU"));
			pd.put("CHUQIN_GONGZHONG", mapkq.get("CHUQIN_GONGZHONG"));
			pd.put("GONGRENZCSJ_TIAN", mapkq.get("GONGRENZCSJ_TIAN"));
			pd.put("GONGRENZCSJ_XIAOSHI", mapkq.get("GONGRENZCSJ_XIAOSHI"));
			pd.put("GONGREN_YJWCC", mapkq.get("GONGREN_YJWCC"));
			pd.put("ANQUANJY_YUE", mapkq.get("ANQUANJY_YUE"));
			pd.put("ZHENGSHU_TIAN", mapkq.get("ZHENGSHU_TIAN"));
			pd.put("CHUQIN_BANZU_BTN", mapkq.get("CHUQIN_BANZU_BTN"));
			pd.put("CHUQIN_DUIWU_BTN", mapkq.get("CHUQIN_DUIWU_BTN"));
			pd.put("CHUQIN_GONGZHONG_BTN", mapkq.get("CHUQIN_GONGZHONG_BTN"));
			pd.put("GONGRENZCSJ_BTN", mapkq.get("GONGRENZCSJ_BTN"));
			pd.put("ANQUANJY_BTN", mapkq.get("ANQUANJY_BTN"));
			pd.put("ZHENGSHU_BTN", mapkq.get("ZHENGSHU_BTN"));
			pd.put("GONGREN_YJWCC_BTN", mapkq.get("GONGREN_YJWCC_BTN"));
			ruleService.saveyj(pd);
		}
		PageData yjlist = ruleService.getyujing(pd);
		map.put("yjmessage", yjlist);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}	
	
	@RequestMapping(value = "/setyjbtn.json")
	@ResponseBody
	public ModelAndView setyjbtn(HttpServletRequest requests) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String  xmbianma=(String) session.getAttribute(Const.SELECTED_BIANMA);
		pd.put("XIANGMU_BIANMA", xmbianma);
		ruleService.updateyjbtn(pd);
		return new ModelAndView(new MappingJackson2JsonView(),map);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/kqtxrule")
	public ModelAndView kqtxrule() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> mapkq= RuleMessge.rulemrz;
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String  xmbianma=(String) session.getAttribute(Const.SELECTED_BIANMA);
		pd.put("XIANGMU_BIANMA", xmbianma);
		PageData qygz = ruleService.getxxbybianma(pd);
		if(qygz==null){    //项目 考勤规则无值插入默认值
			String uuid=UuidUtil.get32UUID();
			pd.put("PKID", uuid);
			pd.put("WEIJIAOYUJC", mapkq.get("WEIJIAOYUJC"));
			pd.put("CHANGSJBSKBSX", mapkq.get("CHANGSJBSKBSX"));
			ruleService.savekq(pd);
		}
		PageData qygzlist = ruleService.getxxbybianma(pd);
		map.put("kqmessage", qygzlist);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	@ResponseBody
	@RequestMapping(value="/setjypx")
	public ModelAndView setjypx() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String  xmbianma=(String) session.getAttribute(Const.SELECTED_BIANMA);
		pd.put("XIANGMU_BIANMA", xmbianma);
		ruleService.updatejypx(pd);
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	@ResponseBody
	@RequestMapping(value="/setjyts")
	public ModelAndView setjyts() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String  xmbianma=(String) session.getAttribute(Const.SELECTED_BIANMA);
		pd.put("XIANGMU_BIANMA", xmbianma);
		//setrysx   updaterysx   setjyts
		ruleService.updatejyts(pd);
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/setrysx")
	public ModelAndView setrysx() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String  xmbianma=(String) session.getAttribute(Const.SELECTED_BIANMA);
		pd.put("XIANGMU_BIANMA", xmbianma);
		//setrysx   updaterysx
		ruleService.updaterysx(pd);
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/setbzcql")
	public ModelAndView setbzcql() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String  xmbianma=(String) session.getAttribute(Const.SELECTED_BIANMA);
		pd.put("XIANGMU_BIANMA", xmbianma);
		ruleService.updatebzcql(pd);
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	@ResponseBody
	@RequestMapping(value="/setyjwcc")
	public ModelAndView setyjwcc() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String  xmbianma=(String) session.getAttribute(Const.SELECTED_BIANMA);
		pd.put("XIANGMU_BIANMA", xmbianma);
		ruleService.updatebzcql(pd);
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/setryts")
	public ModelAndView setryts() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String  xmbianma=(String) session.getAttribute(Const.SELECTED_BIANMA);
		pd.put("XIANGMU_BIANMA", xmbianma);
		ruleService.updateryts(pd);
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	@ResponseBody
	@RequestMapping(value="/setewm")
	public ModelAndView setewm() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String  xmbianma=(String) session.getAttribute(Const.SELECTED_BIANMA);
		pd.put("XIANGMU_BIANMA", xmbianma);
		ruleService.updateewm(pd);
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	@ResponseBody
	@RequestMapping(value="/setdrkq")
	public ModelAndView setdrkq() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String  xmbianma=(String) session.getAttribute(Const.SELECTED_BIANMA);
		pd.put("XIANGMU_BIANMA", xmbianma);
		ruleService.updatedrkq(pd);
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/rule-blacklistAgreement.json")
	public ModelAndView blacklistAgreement() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData qypd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/rulesetting/blacklistAgreement");
		return mv;

	}
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/rulemenu")
	public ModelAndView rulemenu() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//pd = ruleService.findBybianma(pd);	//根据ID读取
		String type="";
		pd = ruleService.gettypeid(pd);
		if(pd==null){
			type="3";
		}else{
			type=pd.get("TYPEID").toString();
		}
		/*Object typeid=pd.get("TYPEID");
		if(null==typeid || ""==typeid){
			type="3";
		}else{
			type=pd.get("TYPEID").toString();
		}*/
		//Object typeid=pd.get("TYPEID");
		map.put("result", "success");
		map.put("typeid", type);
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	
	
	
	//童工
			@ResponseBody
			@RequestMapping(value="/agetg.json")
			public ModelAndView agetg() throws Exception{
				Map<String,String> map = new HashMap<String,String>();
				PageData pd = new PageData();
				PageData qypd = new PageData();
				pd = this.getPageData();
				String agetg=pd.get("agetg").toString();
				//pd = ruleService.findBybianma(pd);	//根据ID读取
				Session session = Jurisdiction.getSession();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
				pd.put("ID", ID);
				String type="";
				pd = ruleService.gettypeid(pd);
				if(pd==null){
					type="3";
					qypd.put("XMID", ID);
					qypd.put("AGETG", agetg);
				}else{
					//type=pd.get("TYPEID").toString();
					qypd.put("ID", ID);
					qypd.put("AGETG", agetg);
				}
				
				ruleService.updateagetg(qypd);
				map.put("result", "success");
				return new ModelAndView(new MappingJackson2JsonView(),map);

			}
			
			
			//童工
			@ResponseBody
			@RequestMapping(value="/tyagetg.json")
			public ModelAndView tyagetg() throws Exception{
				Map<String,String> map = new HashMap<String,String>();
				PageData pd = new PageData();
				PageData qypd = new PageData();
				pd = this.getPageData();
				
				//pd = ruleService.findBybianma(pd);	//根据ID读取
				Session session = Jurisdiction.getSession();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
				pd.put("ID", ID);
				String type="";
				pd = ruleService.gettypeid(pd);
				if(pd==null){
					type="3";
					qypd.put("XMID", ID);
					
				}else{
					//type=pd.get("TYPEID").toString();
					qypd.put("ID", ID);
					
				}
				
				ruleService.updatetyagetg(qypd);
				map.put("result", "success");
				return new ModelAndView(new MappingJackson2JsonView(),map);

			}
			//男性
			@ResponseBody
			@RequestMapping(value="/agenx.json")
			public ModelAndView agenx() throws Exception{
				Map<String,String> map = new HashMap<String,String>();
				PageData pd = new PageData();
				PageData qypd = new PageData();
				pd = this.getPageData();
				String agenx=pd.get("agenx").toString();
				//pd = ruleService.findBybianma(pd);	//根据ID读取
				Session session = Jurisdiction.getSession();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
				pd.put("ID", ID);
				String type="";
				pd = ruleService.gettypeid(pd);
				if(pd==null){
					type="3";
					qypd.put("XMID", ID);
					qypd.put("AGENX", agenx);
				}else{
					//type=pd.get("TYPEID").toString();
					qypd.put("ID", ID);
					qypd.put("AGENX", agenx);
				}
				
				ruleService.updateagenx(qypd);
				map.put("result", "success");
				return new ModelAndView(new MappingJackson2JsonView(),map);

			}
			
			
			//男性
			@ResponseBody
			@RequestMapping(value="/tyagenx.json")
			public ModelAndView tyagenx() throws Exception{
				Map<String,String> map = new HashMap<String,String>();
				PageData pd = new PageData();
				PageData qypd = new PageData();
				pd = this.getPageData();
				
				//pd = ruleService.findBybianma(pd);	//根据ID读取
				Session session = Jurisdiction.getSession();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
				pd.put("ID", ID);
				String type="";
				pd = ruleService.gettypeid(pd);
				if(pd==null){
					type="3";
					qypd.put("XMID", ID);
					
				}else{
					//type=pd.get("TYPEID").toString();
					qypd.put("ID", ID);
					
				}
				
				ruleService.updatetyagenx(qypd);
				map.put("result", "success");
				return new ModelAndView(new MappingJackson2JsonView(),map);

			}
			
			//女性
			@ResponseBody
			@RequestMapping(value="/agenv.json")
			public ModelAndView agenv() throws Exception{
				Map<String,String> map = new HashMap<String,String>();
				PageData pd = new PageData();
				PageData qypd = new PageData();
				pd = this.getPageData();
				String agenv=pd.get("agenv").toString();
				//pd = ruleService.findBybianma(pd);	//根据ID读取
				Session session = Jurisdiction.getSession();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
				pd.put("ID", ID);
				String type="";
				pd = ruleService.gettypeid(pd);
				if(pd==null){
					type="3";
					qypd.put("XMID", ID);
					qypd.put("AGENV", agenv);
				}else{
					//type=pd.get("TYPEID").toString();
					qypd.put("ID", ID);
					qypd.put("AGENV", agenv);
				}
				
				ruleService.updateagenv(qypd);
				map.put("result", "success");
				return new ModelAndView(new MappingJackson2JsonView(),map);

			}
			
			
			//女性
			@ResponseBody
			@RequestMapping(value="/tyagenv.json")
			public ModelAndView tyagenv() throws Exception{
				Map<String,String> map = new HashMap<String,String>();
				PageData pd = new PageData();
				PageData qypd = new PageData();
				pd = this.getPageData();
				
				//pd = ruleService.findBybianma(pd);	//根据ID读取
				Session session = Jurisdiction.getSession();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
				pd.put("ID", ID);
				String type="";
				pd = ruleService.gettypeid(pd);
				if(pd==null){
					type="3";
					qypd.put("XMID", ID);
					
				}else{
					//type=pd.get("TYPEID").toString();
					qypd.put("ID", ID);
					
				}
				
				ruleService.updatetyagenv(qypd);
				map.put("result", "success");
				return new ModelAndView(new MappingJackson2JsonView(),map);

			}
			
			//童工年龄
			@ResponseBody
			@RequestMapping(value="/updatetgnl.json")
			public ModelAndView updatetgnl() throws Exception{
				Map<String,String> map = new HashMap<String,String>();
				PageData pd = new PageData();
				PageData qypd = new PageData();
				pd = this.getPageData();
				String agetg=pd.get("agetg").toString();
				//pd = ruleService.findBybianma(pd);	//根据ID读取
				Session session = Jurisdiction.getSession();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
				pd.put("ID", ID);
				String type="";
				pd = ruleService.gettypeid(pd);
				if(pd==null){
					type="3";
					qypd.put("XMID", ID);
					qypd.put("AGETG", agetg);
				}else{
					//type=pd.get("TYPEID").toString();
					qypd.put("ID", ID);
					qypd.put("AGETG", agetg);
				}
				
				ruleService.updatetgnl(qypd);
				map.put("result", "success");
				return new ModelAndView(new MappingJackson2JsonView(),map);

			}
			//男性年龄
			@ResponseBody
			@RequestMapping(value="/updatenxnl.json")
			public ModelAndView updatenxnl() throws Exception{
				Map<String,String> map = new HashMap<String,String>();
				PageData pd = new PageData();
				PageData qypd = new PageData();
				pd = this.getPageData();
				String agenx=pd.get("agenx").toString();
				//pd = ruleService.findBybianma(pd);	//根据ID读取
				Session session = Jurisdiction.getSession();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
				pd.put("ID", ID);
				String type="";
				pd = ruleService.gettypeid(pd);
				if(pd==null){
					type="3";
					qypd.put("XMID", ID);
					qypd.put("AGENX", agenx);
				}else{
					//type=pd.get("TYPEID").toString();
					qypd.put("ID", ID);
					qypd.put("AGENX", agenx);
				}
				
				ruleService.updatenxnl(qypd);
				map.put("result", "success");
				return new ModelAndView(new MappingJackson2JsonView(),map);

			}
			
			//女性年龄
			@ResponseBody
			@RequestMapping(value="/updatenvnl.json")
			public ModelAndView updatenvnl() throws Exception{
				Map<String,String> map = new HashMap<String,String>();
				PageData pd = new PageData();
				PageData qypd = new PageData();
				pd = this.getPageData();
				String agenv=pd.get("agenv").toString();
				//pd = ruleService.findBybianma(pd);	//根据ID读取
				Session session = Jurisdiction.getSession();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
				pd.put("ID", ID);
				String type="";
				pd = ruleService.gettypeid(pd);
				if(pd==null){
					type="3";
					qypd.put("XMID", ID);
					qypd.put("AGENV", agenv);
				}else{
					//type=pd.get("TYPEID").toString();
					qypd.put("ID", ID);
					qypd.put("AGENV", agenv);
				}
				
				ruleService.updatenvnl(qypd);
				map.put("result", "success");
				return new ModelAndView(new MappingJackson2JsonView(),map);

			}
			
			//证书起
			@ResponseBody
			@RequestMapping(value="/zsqy.json")
			public ModelAndView zsqy() throws Exception{
				Map<String,String> map = new HashMap<String,String>();
				PageData pd = new PageData();
				PageData qypd = new PageData();
				pd = this.getPageData();
				
				//pd = ruleService.findBybianma(pd);	//根据ID读取
				Session session = Jurisdiction.getSession();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
				pd.put("ID", ID);
				String type="";
				pd = ruleService.gettypeid(pd);
				if(pd==null){
					type="3";
					qypd.put("XMID", ID);
					
				}else{
					//type=pd.get("TYPEID").toString();
					qypd.put("ID", ID);
					
				}
				
				ruleService.updatezhengsqy(qypd);
				map.put("result", "success");
				return new ModelAndView(new MappingJackson2JsonView(),map);

			}
			//证书起
			@ResponseBody
			@RequestMapping(value="/zsty.json")
			public ModelAndView zsty() throws Exception{
				Map<String,String> map = new HashMap<String,String>();
				PageData pd = new PageData();
				PageData qypd = new PageData();
				pd = this.getPageData();
				
				//pd = ruleService.findBybianma(pd);	//根据ID读取
				Session session = Jurisdiction.getSession();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
				pd.put("ID", ID);
				String type="";
				pd = ruleService.gettypeid(pd);
				if(pd==null){
					type="3";
					qypd.put("XMID", ID);
					
				}else{
					//type=pd.get("TYPEID").toString();
					qypd.put("ID", ID);
					
				}
				
				ruleService.updatezhengsty(qypd);
				map.put("result", "success");
				return new ModelAndView(new MappingJackson2JsonView(),map);

			}
			//黑名单启用
			@ResponseBody
			@RequestMapping(value="/hmdqy.json")
			public ModelAndView hmdqy() throws Exception{
				Map<String,String> map = new HashMap<String,String>();
				PageData pd = new PageData();
				PageData qypd = new PageData();
				pd = this.getPageData();
				
				//pd = ruleService.findBybianma(pd);	//根据ID读取
				Session session = Jurisdiction.getSession();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
				pd.put("ID", ID);
				String type="";
				pd = ruleService.gettypeid(pd);
				if(pd==null){
					type="3";
					qypd.put("XMID", ID);
					
				}else{
					//type=pd.get("TYPEID").toString();
					qypd.put("ID", ID);
					
				}
				
				ruleService.updatehmdqy(qypd);
				map.put("result", "success");
				return new ModelAndView(new MappingJackson2JsonView(),map);

			}
			//黑名单停
			@ResponseBody
			@RequestMapping(value="/hmdty.json")
			public ModelAndView hmdty() throws Exception{
				Map<String,String> map = new HashMap<String,String>();
				PageData pd = new PageData();
				PageData qypd = new PageData();
				pd = this.getPageData();
				
				//pd = ruleService.findBybianma(pd);	//根据ID读取
				Session session = Jurisdiction.getSession();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
				pd.put("ID", ID);
				String type="";
				pd = ruleService.gettypeid(pd);
				if(pd==null){
					type="3";
					qypd.put("XMID", ID);
					
				}else{
					//type=pd.get("TYPEID").toString();
					qypd.put("ID", ID);
					
				}
				
				ruleService.updatehmdty(qypd);
				map.put("result", "success");
				return new ModelAndView(new MappingJackson2JsonView(),map);

			}
	
			
			//工人近照启用
			@ResponseBody
			@RequestMapping(value="/grjzqy.json")
			public ModelAndView grjzqy() throws Exception{
				Map<String,String> map = new HashMap<String,String>();
				PageData pd = new PageData();
				PageData qypd = new PageData();
				pd = this.getPageData();
				
				//pd = ruleService.findBybianma(pd);	//根据ID读取
				Session session = Jurisdiction.getSession();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
				pd.put("ID", ID);
				String type="";
				pd = ruleService.gettypeid(pd);
				if(pd==null){
					type="3";
					qypd.put("XMID", ID);
					
				}else{
					//type=pd.get("TYPEID").toString();
					qypd.put("ID", ID);
					
				}
				
				ruleService.updategrjzqy(qypd);
				map.put("result", "success");
				return new ModelAndView(new MappingJackson2JsonView(),map);

			}
			//工人近照停
			@ResponseBody
			@RequestMapping(value="/grjzty.json")
			public ModelAndView grjzty() throws Exception{
				Map<String,String> map = new HashMap<String,String>();
				PageData pd = new PageData();
				PageData qypd = new PageData();
				pd = this.getPageData();
				
				//pd = ruleService.findBybianma(pd);	//根据ID读取
				Session session = Jurisdiction.getSession();
				User user = (User) session.getAttribute(Const.SESSION_USER);
				String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
				pd.put("ID", ID);
				String type="";
				pd = ruleService.gettypeid(pd);
				if(pd==null){
					type="3";
					qypd.put("XMID", ID);
					
				}else{
					//type=pd.get("TYPEID").toString();
					qypd.put("ID", ID);
					
				}
				
				ruleService.updategrjzty(qypd);
				map.put("result", "success");
				return new ModelAndView(new MappingJackson2JsonView(),map);

			}
	
	
	//允许登记
		@ResponseBody
		@RequestMapping(value="/yxdj.json")
		public ModelAndView yxdj() throws Exception{
			Map<String,String> map = new HashMap<String,String>();
			PageData pd = new PageData();
			PageData qypd = new PageData();
			pd = this.getPageData();
			//pd = ruleService.findBybianma(pd);	//根据ID读取
			Session session = Jurisdiction.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
			pd.put("ID", ID);
			String type="";
			pd = ruleService.gettypeid(pd);
			if(pd==null){
				type="3";
				qypd.put("XMID", ID);
			}else{
				//type=pd.get("TYPEID").toString();
				qypd.put("ID", ID);
			}
			
			ruleService.updateyxdj(qypd);
			map.put("result", "success");
			return new ModelAndView(new MappingJackson2JsonView(),map);

		}
		//不允许登记
				@ResponseBody
				@RequestMapping(value="/byxdj.json")
				public ModelAndView byxdj() throws Exception{
					Map<String,String> map = new HashMap<String,String>();
					PageData pd = new PageData();
					PageData qypd = new PageData();
					pd = this.getPageData();
					String cd=pd.getString("level").toString();
					//pd = ruleService.findBybianma(pd);	//根据ID读取
					Session session = Jurisdiction.getSession();
					User user = (User) session.getAttribute(Const.SESSION_USER);
					String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
					pd.put("ID", ID);
					String type="";
					pd = ruleService.gettypeid(pd);
					if(pd==null){
						type="3";
						qypd.put("XMID", ID);
						qypd.put("LEVEL", cd);
					}else{
						//type=pd.get("TYPEID").toString();
						qypd.put("ID", ID);
						qypd.put("LEVEL", cd);
					}
					
					ruleService.updatebyxdj(qypd);
					map.put("result", "success");
					return new ModelAndView(new MappingJackson2JsonView(),map);

				}
				//程度级别
				@ResponseBody
				@RequestMapping(value="/cdjb.json")
				public ModelAndView cdjb() throws Exception{
					Map<String,String> map = new HashMap<String,String>();
					PageData pd = new PageData();
					PageData qypd = new PageData();
					pd = this.getPageData();
					String cd=pd.getString("level").toString();
					//pd = ruleService.findBybianma(pd);	//根据ID读取
					Session session = Jurisdiction.getSession();
					User user = (User) session.getAttribute(Const.SESSION_USER);
					String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
					pd.put("ID", ID);
					String type="";
					pd = ruleService.gettypeid(pd);
					if(pd==null){
						type="3";
						qypd.put("XMID", ID);
						qypd.put("LEVEL", cd);
					}else{
						//type=pd.get("TYPEID").toString();
						qypd.put("ID", ID);
						qypd.put("LEVEL", cd);
					}
					
					ruleService.updatecdjb(qypd);
					map.put("result", "success");
					return new ModelAndView(new MappingJackson2JsonView(),map);

				}
	
	
	
	//惩罚启用
	@ResponseBody
	@RequestMapping(value="/chengfaqy.json")
	public ModelAndView chengfaqy() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		PageData qypd = new PageData();
		PageData shangjixx = new PageData();
		PageData sjpd = new PageData();
		PageData xmpd = new PageData();
		PageData xmshangji = new PageData();
		pd = this.getPageData();
		//pd = ruleService.findBybianma(pd);	//根据ID读取
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String BIANMA = (String) session.getAttribute(Const.SELECTED_BIANMA);
		pd.put("USER_ID", user.getUSER_ID());
		String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
		pd.put("ID", ID);
		String type="";
		pd = ruleService.gettypeid(pd);
		OrgTree orgTree = null;
		/*if("0".equals(user.getROLE_ID())){//系统管理员
*/			if(pd==null){//项目
				type="3";
				qypd.put("XMID", ID);
				/*xmpd.put("BIANMA", BIANMA);
				xmshangji=ruleService.xmshangji(xmpd);
				map.put("xmsjxx", xmshangji);*/
				
			}else{ //组织
				qypd.put("ID", ID);
				/*sjpd.put("BIANMA", BIANMA);
				shangjixx=ruleService.shangji(sjpd);
				map.put("sjxx", shangjixx);*/
			}
			
			ruleService.updatecf(qypd);
			
			
		/*}else if("1".equals(user.getROLE_ID())){//企业管理员
			
		}else{
			
		}
		*/
		
		
		
		
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	//惩罚关闭
	@ResponseBody
	@RequestMapping(value="/chengfagb.json")
	public ModelAndView chengfagb() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		PageData qypd = new PageData();
		pd = this.getPageData();
		//pd = ruleService.findBybianma(pd);	//根据ID读取
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
		pd.put("ID", ID);
		String type="";
		pd = ruleService.gettypeid(pd);
		if(pd==null){
			type="3";
			qypd.put("XMID", ID);
		}else{
			//type=pd.get("TYPEID").toString();
			qypd.put("ID", ID);
		}
		
		ruleService.updatecfgb(qypd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	//年龄启用
		@ResponseBody
		@RequestMapping(value="/nianlingqy.json")
		public ModelAndView nianlingqy() throws Exception{
			Map<String,String> map = new HashMap<String,String>();
			PageData pd = new PageData();
			PageData qypd = new PageData();
			pd = this.getPageData();
			//pd = ruleService.findBybianma(pd);	//根据ID读取
			Session session = Jurisdiction.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
			pd.put("ID", ID);
			String type="";
			pd = ruleService.gettypeid(pd);
			if(pd==null){
				type="3";
				qypd.put("XMID", ID);
			}else{
				//type=pd.get("TYPEID").toString();
				qypd.put("ID", ID);
			}
			
			ruleService.updatenlqy(qypd);
			map.put("result", "success");
			return new ModelAndView(new MappingJackson2JsonView(),map);

		}
		//年龄关闭
		@ResponseBody
		@RequestMapping(value="/nianlinggb.json")
		public ModelAndView nianlinggb() throws Exception{
			Map<String,String> map = new HashMap<String,String>();
			PageData pd = new PageData();
			PageData qypd = new PageData();
			pd = this.getPageData();
			//pd = ruleService.findBybianma(pd);	//根据ID读取
			Session session = Jurisdiction.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
			pd.put("ID", ID);
			String type="";
			pd = ruleService.gettypeid(pd);
			if(pd==null){
				type="3";
				qypd.put("XMID", ID);
			}else{
				//type=pd.get("TYPEID").toString();
				qypd.put("ID", ID);
			}
			
			ruleService.updatenlgb(qypd);
			map.put("result", "success");
			return new ModelAndView(new MappingJackson2JsonView(),map);

		}
		//证书启用
				@ResponseBody
				@RequestMapping(value="/zhengshuqy.json")
				public ModelAndView zhengshuqy() throws Exception{
					Map<String,String> map = new HashMap<String,String>();
					PageData pd = new PageData();
					PageData qypd = new PageData();
					pd = this.getPageData();
					//pd = ruleService.findBybianma(pd);	//根据ID读取
					Session session = Jurisdiction.getSession();
					User user = (User) session.getAttribute(Const.SESSION_USER);
					String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
					pd.put("ID", ID);
					String type="";
					pd = ruleService.gettypeid(pd);
					if(pd==null){
						type="3";
						qypd.put("XMID", ID);
					}else{
						//type=pd.get("TYPEID").toString();
						qypd.put("ID", ID);
					}
					
					ruleService.updatezsqy(qypd);
					map.put("result", "success");
					return new ModelAndView(new MappingJackson2JsonView(),map);

				}
				//证书关闭
				@ResponseBody
				@RequestMapping(value="/zhengshugb.json")
				public ModelAndView zhengshugb() throws Exception{
					Map<String,String> map = new HashMap<String,String>();
					PageData pd = new PageData();
					PageData qypd = new PageData();
					pd = this.getPageData();
					//pd = ruleService.findBybianma(pd);	//根据ID读取
					Session session = Jurisdiction.getSession();
					User user = (User) session.getAttribute(Const.SESSION_USER);
					String ID=session.getAttribute(Const.SELECTED_ZZJG).toString();
					pd.put("ID", ID);
					String type="";
					pd = ruleService.gettypeid(pd);
					String DTTOP_BM = (String) session.getAttribute(Const.SELECTED_BIANMA);
					if(pd==null){
						type="3";
						qypd.put("XMBM", DTTOP_BM);
					}else{
						//type=pd.get("TYPEID").toString();
						qypd.put("BM", DTTOP_BM);
					}
					
					ruleService.updatezsgb(qypd);
					map.put("result", "success");
					return new ModelAndView(new MappingJackson2JsonView(),map);

				}
			
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 /**去修改页面
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/goEdit.json")
		@ResponseBody
		public ModelAndView goEdit()throws Exception{
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			Map<String,Object> map = new HashMap<String,Object>();
			List<PageData> listgzz=ruleService.getgzz(pd);
			List<PageData> listlxj=ruleService.getlxj(pd);
			pd = ruleService.findBybianma(pd);	//根据ID读取
			Object bm =pd.get("BIANMA");
			String b="";
			if(bm!=null && !"".equals(bm)){
				b=bm.toString();
			}
			mv.setViewName("system/control/control_edit");
			mv.addObject("result", "success");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.addObject("bm", b);
			mv.addObject("listgzz", listgzz);
			mv.addObject("listlxj", listlxj);
			return mv;
		}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/control-add")
	public ModelAndView save() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		ruleService.save(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	
	@ResponseBody
	@RequestMapping(value="/control-edit")
	public ModelAndView edit() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//ruleService.save(pd);
		ruleService.edit(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	
	
	/**
	 * 删除
	 * @param DEPARTMENT_ID
	 * @param
	 * @throws Exception 
	 */
	@RequestMapping(value="/control-del.json")
	@ResponseBody
	public Object delete() throws Exception{
		
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "success";
		
		ruleService.delete(pd);	//执行删除
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 *//*
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		ruleService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	*/
	@RequestMapping(value="/editActive")
	public ModelAndView editActive() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		ruleService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String keywords = pd.getString("keywords");					//检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String DEPARTMENT_ID = null == pd.get("DEPARTMENT_ID")?"":pd.get("DEPARTMENT_ID").toString();
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			DEPARTMENT_ID = pd.get("id").toString();
		}
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID);					//上级ID
		page.setPd(pd);
		List<PageData>	varList = ruleService.list(page);	//列出Dictionaries列表	
		mv.addObject("pd", ruleService.findById(pd));		//传入上级所有信息
		mv.addObject("DEPARTMENT_ID", DEPARTMENT_ID);			//上级ID
		mv.setViewName("system/department/department_list");
		mv.addObject("varList", varList);
		
		return mv;
	}
	
	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listAllDepartment")
	public ModelAndView listAllDepartment(Model model,String DEPARTMENT_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdd = new PageData();
		
		
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String roleid=null;
		String departmentid1 =null;
		String username=null;
		if(user!=null){
			
			 roleid =user.getROLE_ID();
			  departmentid1 = user.getDEPARTMENT_ID();
			  username =user.getUSERNAME();
			  
		}
		String parent_id =null;
		List<Department>  department =new ArrayList<Department>();
		if(departmentid1!=null){
			PageData pddd =new PageData();
			pddd.put("DEPARTMENT_ID", departmentid1);
			pddd=ruleService.findById(pddd);
			if(pddd!=null){
				parent_id =pddd.getString("PARENT_ID");
			
			}
		
			List<Department>  department1=ruleService.listAllDepartment(parent_id);
			if(department1.size()>0){
				for(int i =0;i<department1.size();i++){
					String depid=department1.get(i).getDEPARTMENT_ID();
					if(departmentid1.equals(depid)){
						department.add(department1.get(i));
					}
					
					
				}
			}
		
		}
		
		try{
			JSONArray arr = JSONArray.fromObject(department);
			String json = arr.toString();

			json = json.replaceAll("DEPARTMENT_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subDepartment", "nodes").replaceAll("hasDepartment", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("DEPARTMENT_ID",DEPARTMENT_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("system/department/department_ztree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 *//*
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DEPARTMENT_ID = null == pd.get("DEPARTMENT_ID")?"":pd.get("DEPARTMENT_ID").toString();
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID);					//上级ID
		mv.addObject("pds",ruleService.findById(pd));//传入上级所有信息	
		mv.addObject("DEPARTMENT_ID", DEPARTMENT_ID);//传入ID，作为子级ID用
		
		//定义一个集合，根据获取的DEPARTMENT_TYPE_ID，通过SYS_DEPARTMENT_TYPE表获取它的名字
		List<Project> pj=ruleService.typeName();
        mv.addObject("pj", pj);
        List<PageData> province = ruleService.S_province(pd);
        mv.addObject("province", province);
    
		
		mv.setViewName("system/department/department_edit");
		mv.addObject("msg", "save");
		return mv;
	}	*/
	
/*	 *//**去修改页面
	 * @param
	 * @throws Exception
	 *//*
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DEPARTMENT_ID = pd.getString("DEPARTMENT_ID");
		pd = ruleService.findById(pd);	//根据ID读取
		mv.addObject("pd", pd);					//放入视图容器
		pd.put("DEPARTMENT_ID",pd.get("PARENT_ID").toString());			//用作上级信息
		mv.addObject("pds",ruleService.findById(pd));				//传入上级所有信息
		mv.addObject("DEPARTMENT_ID", pd.get("PARENT_ID").toString());	//传入上级ID，作为子ID用
		pd.put("DEPARTMENT_ID",DEPARTMENT_ID);		//复原本ID
		
		List<Project> pj=ruleService.typeName();
        mv.addObject("pj", pj);
       List<PageData> province = ruleService.S_province(pd);
       mv.addObject("province", province);
       
       List<PageData> city = ruleService.S_city(pd);
       mv.addObject("city", city);
       
       List<PageData> district = ruleService.S_district(pd);
       mv.addObject("district", district);
        
        
        
        
        
		mv.setViewName("system/department/department_edit");
		mv.addObject("msg", "edit");
		return mv;
	}	*/

	
	@RequestMapping(value="/goActive")
	public ModelAndView goActive(HttpServletRequest req)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		HttpSession session =  req.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);	
		String DEPARTMENT_ID =user.getDEPARTMENT_ID();
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID);
		pd = ruleService.findById(pd);	//根据ID读取
		mv.addObject("pd", pd);					//放入视图容器
		pd.put("DEPARTMENT_ID",pd.get("PARENT_ID").toString());			//用作上级信息
		mv.addObject("pds",ruleService.findById(pd));				//传入上级所有信息
		mv.addObject("DEPARTMENT_ID", pd.get("PARENT_ID").toString());	//传入上级ID，作为子ID用
		pd.put("DEPARTMENT_ID",DEPARTMENT_ID);		//复原本ID
		
		List<Project> pj=ruleService.typeName();
        mv.addObject("pj", pj);
       List<PageData> province = ruleService.S_province(pd);
       mv.addObject("province", province);
       
       /*List<PageData> city = ruleService.S_city(pd);
       mv.addObject("city", city);*/
       
     /*  List<PageData> district = ruleService.S_district(pd);
       mv.addObject("district", district);*/
        
		mv.setViewName("system/department/depactive");
		mv.addObject("msg", "edit");
		return mv;
	}	
	
	
	
	
	
	/**判断编码是否存在
	 * @return
	 */
	@RequestMapping(value="/hasBianma")
	@ResponseBody
	public Object hasBianma(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(ruleService.findByBianma(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	@RequestMapping(value="/getPinyin")
	@ResponseBody
	public Object getPinyin(){
		Map<String,String> map = new HashMap<String,String>();
		String name ="";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			name=pd.getString("NAME");
			name=PinyinHelper.getShortPinyin(name);//返回结果
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	
		map.put("name", name);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**获取city信息
	 * @return
	 */
	@RequestMapping(value="/getDq")
	@ResponseBody
	public Object getDq(){
		List<PageData> data = new ArrayList<PageData>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			data=ruleService.S_district(pd);
				
			
		} catch(Exception e){
			logger.error(e.toString(), e);
		}			//返回结果
		return data;
	}
	
	@RequestMapping(value="/getCity")
	@ResponseBody
	public Object getCity(){
		List<PageData> data = new ArrayList<PageData>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			data=ruleService.S_city(pd);
				
			
		} catch(Exception e){
			logger.error(e.toString(), e);
		}			//返回结果
		return data;
	}
	
	/**获取DEPARTMENT_TYPE_ID=1的名称和DEPARTMENT_ID，用于角色管理的添加角色页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/departmenttypeid")
	@ResponseBody
	public Object departmenttypeid() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		String pd11 = null;
		try{
			pd = this.getPageData();
			List <PageData> pd1 = ruleService.findByTypeId(pd);
			
			
			ObjectMapper mapper = new ObjectMapper();
			pd11 = mapper.writeValueAsString(pd1);
			if(pd11 != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);//返回结果
		map.put("dp", pd11);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
