package com.fh.service.system.dormitoryInfo.impl;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.controller.system.login.LoginController;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.dormitoryInfo.DormitoryInfoManager;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.service.system.stuinfo.Impl.StuInfoService;
import com.fh.util.ArithUtil;
import com.fh.util.PageData;
import com.newsky.epay.sdk.HttpClient;
@Service
public class DormitoryInfoService implements DormitoryInfoManager {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	@Resource(name="stuInfoService")
	private StuInfoManager  stuInfoService;
	
	@Override
	public List<PageData> getDormlistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("DormitoryInfoMapper.getDormlistPage", page);
	}
	
	@Override
	public List<PageData> getDuiDiaoDormlistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("DormitoryInfoMapper.getDuiDiaoDormlistPage", page);
	}
	
	@Override
	public List<PageData> getDormhistorylistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("DormitoryInfoMapper.getDormhistorylistPage", page);
	}

	@Override
	public PageData getUserDormDepartments(String user_id) throws Exception {
		return (PageData) dao.findForObject("DormitoryInfoMapper.getUserDormDepartments", user_id);
	}

	@Override
	public List<PageData> getDormDepts(String str) throws Exception {
		String[] str_array = str.split(",");
		return (List<PageData>) dao.findForList("DormitoryInfoMapper.getDormDepts", str_array);
	}

	@Override
	public List<PageData> getUserAllDeptsBeds(List<PageData> list) throws Exception {
		return (List<PageData>) dao.findForList("DormitoryInfoMapper.getUserAllDeptsBeds", list);
	}

	@Override
	public List<PageData> getDormList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DormitoryInfoMapper.getDormList", pd);
	}

	@Override
	public List<PageData> getDormTypeList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DormitoryInfoMapper.getDormTypeList", pd);
	}

	@Override
	public List<PageData> getUserDormInfoDepts(String[] array) throws Exception {
		return (List<PageData>) dao.findForList("DormitoryInfoMapper.getUserDormInfoDepts", array);
	}

	
	@Override
	public void saveStuDorm(PageData pd) throws Exception {
		
		dao.save("DormitoryInfoMapper.saveoldDormUsedLog", pd);
		dao.save("DormitoryInfoMapper.savenewDormUsedLog", pd);
		
	}
	@Override
	public void updateStuDorm(PageData pd) throws Exception {
		
		PageData oldSYS_DICTIONARIES_PKID=(PageData) dao.findForObject("DormitoryInfoMapper.getold", pd);
		PageData newSYS_DICTIONARIES_PKID=(PageData) dao.findForObject("DormitoryInfoMapper.getnew", pd);
		if(oldSYS_DICTIONARIES_PKID!=null){
			pd.put("OLDSYS_DICTIONARIES_PKID", oldSYS_DICTIONARIES_PKID.get("SYS_DICTIONARIES_PKID"));
		}
		if(newSYS_DICTIONARIES_PKID!=null){
			pd.put("NEWSYS_DICTIONARIES_PKID", newSYS_DICTIONARIES_PKID.get("SYS_DICTIONARIES_PKID"));
		}
		//互换宿舍相关信息
		dao.update("DormitoryInfoMapper.changeoldDormInfo", pd);
		//Thread.sleep(500);
		dao.update("DormitoryInfoMapper.changenewDormInfo", pd);
		
		//记录log
		
		//dao.save("DormitoryInfoMapper.saveoldDormUsedLog", pd);
		//dao.save("DormitoryInfoMapper.savenewDormUsedLog", pd);
		
		
		//dao.save("DormitoryInfoMapper.saveDormUsedLog", logPdData);
		
		
		
		
		/*
		
		
		
		
		
		
		PageData tempPd=new PageData();
		tempPd.put("PKID", pd.getString("newpkid"));
		tempPd.put("T_STUDENT_PKID", "null".equals(pd.getString("oldStuId")) ? null :pd.getString("oldStuId"));//避免数据库插入'null'字符串的尴尬
		
		dao.update("DormitoryInfoMapper.changeEachOtherDormInfo", tempPd);
		
		PageData fwPd=(PageData) dao.findForObject("DormitoryInfoMapperNew.getJingQueFanWei", null);
		if(pd.getString("newStuId")!=null && !"null".equals(pd.getString("newStuId")) && "专业".equals(fwPd.getString("AREATYPE"))){//对调改关系
			dao.update("DormitoryInfoMapper.changeDORMDEPARTMENT", tempPd);
		}
				
		
		tempPd.put("PKID", pd.getString("oldpkid"));
		tempPd.put("T_STUDENT_PKID", "null".equals(pd.getString("newStuId")) ? null :pd.getString("newStuId"));
		dao.update("DormitoryInfoMapper.changeEachOtherDormInfo", tempPd);
		if(pd.getString("newStuId")!=null && !"null".equals(pd.getString("newStuId")) && "专业".equals(fwPd.getString("AREATYPE"))){//对调改关系
			dao.update("DormitoryInfoMapper.changeDORMDEPARTMENT", tempPd);
		}
		
		
		 * 调宿操作要记录日志
		 
		if(pd.getString("newStuId")==null || "null".equals(pd.getString("newStuId")) ){//调宿
			{
				
				 * 旧记录绑定关系解除
				 
				PageData logPdData = new PageData();
				logPdData.put("used_type", 0);
				logPdData.put("handle_resource", "平台调宿操作");
				logPdData.put("t_student_pkid", pd.getString("oldStuId"));
				logPdData.put("pkid", pd.getString("oldpkid"));
				dao.save("DormitoryInfoMapper.saveDormUsedLog", logPdData);
				
				
				 * 新纪录绑定关系
				 
				logPdData.put("used_type", 1);
				logPdData.put("handle_resource", "平台调宿操作");
				logPdData.put("t_student_pkid", pd.getString("oldStuId"));
				logPdData.put("pkid", pd.getString("newpkid"));
				dao.save("DormitoryInfoMapper.saveDormUsedLog", logPdData);
				
				
				 * 获取宿舍分配精确范围
				 
				
				PageData fanweiPd=(PageData) dao.findForObject("DormitoryInfoMapperNew.getJingQueFanWei", null);
				if ("专业".equals(fanweiPd.getString("AREATYPE")) ){
					//新宿舍原来所属的专业
					PageData oldDeptPd=(PageData) dao.findForObject("DormitoryInfoMapperNew.getDormBelongDept", pd.getString("newpkid"));
					//新宿舍现在的所属的专业
					PageData newDeptPd=(PageData) dao.findForObject("DormitoryInfoMapperNew.getDormBelongDept", pd.getString("oldpkid"));
					
					{
						
						 * 调宿后把新宿舍的专业更新为原宿舍专业
						 
						PageData dormDeptPd = new PageData();
						dormDeptPd.put("DORM_ID", pd.getString("newpkid"));
						dormDeptPd.put("DEPARTMENT_ID", newDeptPd.getString("DEPARTMENT_ID"));
						dao.update("DormitoryInfoMapperNew.updateDormBelongDept", dormDeptPd);
					}										
					stuInfoService.updateDorm(pd.getString("oldStuId"), "1",
							pd.getString("newpkid"), "调宿", pd.getString("T_STUDENT_DORM_TYPE_PKID"),
							oldDeptPd.getString("DEPARTMENT_ID"), newDeptPd.getString("DEPARTMENT_ID"));
				}
				
			}		
		}
		
		
		//调宿   处理宿舍资源     胡文浩
		PageData dorm_res = new PageData();//宿舍信息
		dorm_res = (PageData)dao.findForObject("DormitoryInfoMapper.getDromres", "1");
		if(dorm_res!=null && "专业".equals(dorm_res.getString("AREATYPE"))){
			if("调宿".equals(pd.getString("info"))){
				
			}
		}
		
		
		
		
		
	*/}

	@Override
	public void updateTuisu(PageData pd) throws Exception {
		
		
		
		
		dao.update("DormitoryInfoMapper.updatetuisuDorm", pd);
		
		
		dao.save("DormitoryInfoMapper.savetuisuDormUsedLog", pd);
		
		
		/*
		PageData fwPd=(PageData) dao.findForObject("DormitoryInfoMapperNew.getJingQueFanWei", null);
		if("学校".equals(fwPd.getString("AREATYPE"))){
			//stuInfoService.updateDorm(pd.getString("t_student_pkid"), "0", pd.getString("pkid"), "宿舍信息退宿", pd.getString("T_STUDENT_DORM_TYPE_PKID"));
		}else if("专业".equals(fwPd.getString("AREATYPE"))){
			//stuInfoService.updateDorm(pd.getString("t_student_pkid"), "0", pd.getString("pkid"), "宿舍信息退宿", pd.getString("T_STUDENT_DORM_TYPE_PKID"),pd.getString("t_studep_pkid"), pd.getString("t_studep_pkid"));
		}
		//退宿后修改缴费名单等信息      胡文浩
		List<PageData> jfinfo = null;//缴费项信息  多条
		List<PageData> fuhe =new ArrayList();  ;//符合条件的
		//我要获取当前的日期
		Date date = new Date();
		//设置要获取到什么样的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String STUDENT_PKID = pd.getString("t_student_pkid");
		String DORM_PKID=pd.getString("pkid");
		jfinfo = (List<PageData>)dao.findForList("StuInfoMapper.getItemInfo", STUDENT_PKID);
		if(jfinfo.size()>0){
			for(int o=0;o<jfinfo.size();o++){
				if("0".equals(jfinfo.get(o).getString("CHARGERULL"))){
					String yuefen=jfinfo.get(o).getString("SCHOOL_YEAR_STR");  //得到学年月份字符串
					List<String> yue = Arrays.asList(yuefen.split(","));
					String zuidarq=StuInfoService.getIntersection(yue);
					String createdate = sdf.format(date);
					boolean flug=StuInfoService.compareDate(zuidarq,createdate);//最大日期等于/当前日期 之后或等于  为 true
					if(flug ){  //符合条件的生成新的LIST
						fuhe.add(jfinfo.get(o));
					}
				}
				
			}
		}
		
		if(fuhe.size()>0){
			//判断 是否有子项
			
			for(int k=0;k<fuhe.size();k++){
				if(fuhe.get(k).getString("PAY_ITEM_PKID").equals(fuhe.get(k).getString("PAY_ITEM_PARENT_PKID"))){
					//itemlist表  PAY_ITEM_PKID和PAY_ITEM_PARENT_PKID相同
					String zongje=fuhe.get(k).getString("AMOUNTRECEIVABLE");
					String shishou=fuhe.get(k).getString("AMOUNTCOLLECTION");
					
					String yhje=fuhe.get(k).getString("DISCOUNT_MONEY");
					if(null==yhje || "null".equals(yhje)|| "".equals(yhje)){
						yhje="0";
					}
					String PKID=fuhe.get(k).getString("PKID");
					String yuefen=fuhe.get(k).getString("SCHOOL_YEAR_STR");
					tuisu(yuefen,zongje,STUDENT_PKID,PKID,shishou,yhje,fuhe.get(k).getString("PAY_ITEM_PARENT_PKID"));
					
				}else{
					
					PageData pd_stu= new PageData();
					pd_stu.put("STUDENT_PKID", STUDENT_PKID);
					pd_stu.put("DORM_PKID", DORM_PKID);
					
					PageData info = (PageData)dao.findForObject("StuInfoMapper.getdormInfo", DORM_PKID);
					String DORMPKID=info.getString("T_STUDENT_DORM_TYPE_PKID");
					
					
					//itemlist表  PAY_ITEM_PKID和PAY_ITEM_PARENT_PKID不同，查看宿舍类型PKID是否相同
					if(fuhe.get(k).getString("T_DORM_TYPE_PKID").equals(DORMPKID)){
						String zongje=fuhe.get(k).getString("AMOUNTRECEIVABLE");
						String PKID=fuhe.get(k).getString("PKID");
						String yhje=fuhe.get(k).getString("DISCOUNT_MONEY");
						if(null==yhje || "null".equals(yhje)|| "".equals(yhje)){
							yhje="0";
						}
						String shishou=fuhe.get(k).getString("AMOUNTCOLLECTION");
						String yuefen=fuhe.get(k).getString("SCHOOL_YEAR_STR");
						tuisu(yuefen,zongje,STUDENT_PKID,PKID,shishou,yhje,fuhe.get(k).getString("PAY_ITEM_PKID"));
					}
					
				}
			}
			
			
			
		}
		
	*/}

	@Override
	public List<PageData> getStuInfoList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("DormitoryInfoMapper.getStuInfolistPage", page);
	}
	
	
	@Override
	public List<PageData> getfenpeiStuInfoList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("DormitoryInfoMapper.getfenpeiStuInfolistPage", page);
	}

	@Override
	public List<PageData> ExportDormInfo(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DormitoryInfoMapper.ExportDormInfo", pd);
	}
	
	@Override
	public List<PageData> ExportDormhistoryInfo(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DormitoryInfoMapper.ExportDormhistoryInfo", pd);
	}

	@Override
	public PageData getStuDrom(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DormitoryInfoMapper.getStuDrom", pd);
	}
	
	@Override
	public PageData getdaoruStuDrom(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DormitoryInfoMapper.getdaoruStuDrom", pd);
	}

	@Override
	public List<PageData> getDromList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DormitoryInfoMapper.getDromList", pd);
	}
	
	@Override
	public List<PageData> getdaoruDromList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DormitoryInfoMapper.getdaoruDromList", pd);
	}
	
	@Override
	public List<PageData> getinfoList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DormitoryInfoMapper.getinfoList", pd);
	}

	@Override
	public PageData getDormName(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DormitoryInfoMapper.getDormName", pd);
	}

	@Override
	public void updatebatchBindStuAndDorm(List<PageData> list) throws Exception {
		/*dao.batchUpdate("DormitoryInfoMapper.bindStuAndDorm", list);//批量更新
		dao.batchSave("DormitoryInfoMapper.batchsaveDormUsedLog", list);//批量记录床位记录
		//调用公共方法 资源
		PageData fwPd=(PageData) dao.findForObject("DormitoryInfoMapperNew.getJingQueFanWei", null);
		PageData dormDeptPd=new PageData();*/
		for(int i=0;i<list.size();i++){
			
			//dao.update("DormitoryInfoMapper.updateDormBelongDept", list.get(i));
			dao.update("DormitoryInfoMapper.updatedaoruDorm", list.get(i));
			
			dao.save("DormitoryInfoMapper.savedaorulog", list.get(i));
			
			
			/*if("专业".equals(fwPd.getString("AREATYPE"))){//对调改关系
				stuInfoService.updateDorm(list.get(i).getString("T_STUDENT_PKID"), "1", list.get(i).getString("DORM_PKID"), 
				"批量导入宿舍信息",list.get(i).getString("T_STUDENT_DORM_TYPE_PKID"),list.get(i).getString("DORM_DEPARTMENT_ID"),list.get(i).getString("STU_DEPARTMENT_PKID"));
				
				dormDeptPd.put("DORM_ID", list.get(i).getString("DORM_PKID"));
				dormDeptPd.put("DEPARTMENT_ID", list.get(i).getString("STU_DEPARTMENT_PKID"));
				dao.update("DormitoryInfoMapperNew.updateDormBelongDept", dormDeptPd);
				
			}else if("学校".equals(fwPd.getString("AREATYPE"))){
				stuInfoService.updateDorm(list.get(i).getString("T_STUDENT_PKID"), "1", list.get(i).getString("DORM_PKID"), 
						"批量导入宿舍信息",list.get(i).getString("T_STUDENT_DORM_TYPE_PKID"));
			}*/
		}
		
		
	}

	@Override
	public List<PageData> dormOrderlistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("DormitoryInfoMapper.dormOrderlistPage", page);
	}

	@Override
	public List<PageData> getDormDeptsBykey(PageData pd) throws Exception {
		String[] str_array = pd.getString("DEPARTMENT_PKID").split(",");
		pd.put("array", str_array);
		return (List<PageData>) dao.findForList("DormitoryInfoMapper.getDormDeptsBykey", pd);
	}

	@Override
	public List<PageData> getDormOrder(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("DormitoryInfoMapper.getDormOrder", pd);
	}

	@Override
	public void updateDormStu(PageData pd_stuDorm) throws Exception {
		//判断学生是否已经分配了宿舍，如果分配了，跳过该学生的分配
		PageData pd_stu = (PageData)dao.findForObject("DormitoryInfoMapper.getStuDorm", pd_stuDorm);
		if(pd_stu!=null){
			
		}else{
			dao.update("DormitoryInfoMapper.updateDormStu", pd_stuDorm);
			//插入操作日志
			dao.save("DormitoryInfoMapper.saveUsedLog", pd_stuDorm);
			/**
			 * 发送模板消息
			 */
			/*Map map = new HashMap<>();
			map.put("STUDENT_PKID", pd_stuDorm.getString("STUDENT_PKID"));
			map.put("DORM_PKID", pd_stuDorm.getString("DORM_PKID"));
			map.put("template_title", "预约成功提醒");
			Properties prop = new Properties();
			InputStream in = null;
			in=LoginController.class.getClassLoader().getResourceAsStream("conf.properties");
			prop.load(in);
			String methodurl="";
			if(prop!=null){
				methodurl=prop.getProperty("neiUrl");
			}
			HttpClient httpClient = new HttpClient(methodurl+"/colleges-pay/dingDanPayController/sendMubanMessage", 30000, 30000);
			int resultCode = httpClient.send(map, "UTF-8");*/
		}
	}

	@Override
	public PageData getStuDorm(PageData pd) throws Exception {
		return (PageData)dao.findForObject("DormitoryInfoMapper.getStuDorm", pd);
	}
	public  void tuisu(String yuefen,String zongje,String STUDENT_PKID ,String PKID,String shishou,String yhje,String parentPayItemPkid) throws Exception{  
		//1.按月生成的缴费名单修改
		
		PageData fpinfo = new PageData();//缴费项信息
		fpinfo = (PageData)dao.findForObject("StuInfoMapper.getLogInfo", STUDENT_PKID);
		//String cj=fpinfo.getString("CJSJ");
		
		PageData loginfo = new PageData();//得到缴费项按月单价
		loginfo = (PageData)dao.findForObject("StuInfoMapper.getLogInfo", STUDENT_PKID);
		String cj=loginfo.getString("CJSJ");
		//学年里月份
		List<String> result = Arrays.asList(yuefen.split(","));
		String [] sortD= sortDate(yuefen.split(","));
		
		//我要获取当前的日期
        Date date = new Date();
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        //获取String类型的时间
        String createdate = sdf.format(date);
        int syyu=getint(sortD,createdate);
       // List<String> shijian = new ArrayList();
        //两个日期之间夸月
       // shijian=StuInfoService.getMonthBetween(cj,createdate);
        //两个list相同值
       // List<String> jieguo= StuInfoService.getIntersection(result, shijian);
        //相同的月份数
       // int a=jieguo.size();
        PageData pd_rule = new PageData();
        pd_rule.put("parentPayItemPkid", parentPayItemPkid);
        pd_rule.put("STUDENT_PKID", STUDENT_PKID);
        //得到单价
        PageData rulePd=(PageData) dao.findForObject("DormitoryInfoMapperNew.getPayItemRuleByPkid", pd_rule);
        
	//	String yue=String.valueOf(result.size());
		
//		double yingshou=ArithUtil.div(zongje, yue);
        if(rulePd!=null && !"null".equals(rulePd)){
        	double yingshou=Double.parseDouble(rulePd.getString("COST"));
        	double ysze=Double.parseDouble(rulePd.getString("AMOUNTRECEIVABLE"));
        	//AMOUNTRECEIVABLE
    		//double ys=ArithUtil.mul(String.valueOf(a), String.valueOf(yingshou));
    		double ys=ArithUtil.mul(String.valueOf(syyu), String.valueOf(yingshou));
    		
    		//ysze
    		double jianhoujine=ArithUtil.sub(String.valueOf(ysze),String.valueOf(ys));
    		
    		double cost=ArithUtil.add(String.valueOf(ys), yhje);
    		double  ss=Double.parseDouble(shishou);
    		PageData gai = new PageData();//得到缴费项按月单价
    		if(Double.doubleToLongBits(ss) == Double.doubleToLongBits(ys)){
    			//实收和应收一致
    			gai.put("STATUS", 1);
    		}else{
    			gai.put("STATUS", 0);
    		}
    		gai.put("PKID", PKID);
    		gai.put("AMOUNTRECEIVABLE", jianhoujine);
    		gai.put("COST", cost);
    		dao.update("StuInfoMapper.updateItem", gai);
        }
		
		
	} 
	
	
	
	
	
	
	 public static void main(String []arg0) throws ParseException{
		 
		 
		 double jianhoujine=ArithUtil.sub(String.valueOf("900"),String.valueOf("200"));
		 
		/* String a="2013-09";
	        String []dateArray = {"2013-06","2013-04","2013-09",
	            "2013-11"};
	        
	        int k=0;
			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			 for(int i=0;i<dateArray.length;i++){
		        	System.out.println(dateArray[i]);
		        if(dateFormat.parse(a).getTime()<dateFormat.parse(dateArray[i]).getTime()){
		        	k++;
		        }
			 }*/
			 System.out.println(jianhoujine);
	        }
	    
	
	 private static int  getint(String[] dateArray,String date) throws ParseException {
		//List<String> result;
		 int k=0;
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		 for(int i=0;i<dateArray.length;i++){
	        if(dateFormat.parse(date).getTime()<dateFormat.parse(dateArray[i]).getTime()){
	        	k++;
	        }
		 }
		 return k;
	    }
	 
	 
	 
	 
	 private static String [] sortDate(String[] dateArray) {
	        Map<String, Integer> dateMap = new TreeMap<String, Integer>();
	        int i, arrayLen;
	        arrayLen = dateArray.length;
	        for(i = 0; i < arrayLen; i++){
	            String dateKey = dateArray[i];
	            if(dateMap.containsKey(dateKey)){
	                int value = dateMap.get(dateKey) + 1;
	                dateMap.put(dateKey, value);
	            }else{
	                dateMap.put(dateKey, 1);
	            }
	        }
	        Set<String> keySet = dateMap.keySet();
	        String []sorttedArray = new String[keySet.size()];
	        Iterator<String> iter = keySet.iterator();
	        int index = 0;
	        while (iter.hasNext()) {
	            String key = iter.next();
	        //    System.out.println(key + ":" + dateMap.get(key));
	            sorttedArray[index++] = key;
	        }
	        int sorttedArrayLen = sorttedArray.length;
	        System.out.println("最小日期是：" + sorttedArray[0] + "," +
	                " 天数为" + dateMap.get(sorttedArray[0]));
	        System.out.println("最大日期是：" + sorttedArray[sorttedArrayLen - 1] + "," +
	                " 天数为" + dateMap.get(sorttedArray[sorttedArrayLen - 1]));
	        return sorttedArray;
	    }
	
	
	
	
	
	
	
	@Override
	public void updatefenpei(PageData pd) throws Exception {
		//判断这个学生是否已经有床位，如果有，不做操作
		/*PageData pd_stu_dorm = (PageData)dao.findForObject("DormitoryInfoMapper.getStuIsDorm", pd);
		if(pd_stu_dorm!=null){//表示该学生已经分配了床位不做处理
		}else{*/
			//为学生分配床位
			
			dao.update("DormitoryInfoMapper.updatefenpeiDorm", pd);
			
			dao.save("DormitoryInfoMapper.savefenpeiDormUsedLog", pd);
			
			//stuInfoService.updateDorm(pd.getString("stuId"), "1", pd.getString("dormId"), "分配床位",pd.getString("T_STUDENT_DORM_TYPE_PKID"));
			
			/*PageData pd_dep = (PageData)dao.findForObject("DormitoryInfoMapper.getDepRESTR", pd);
			if("专业".equals(pd_dep.getString("AREATYPE"))){
				//根据学生PKID获取学生的所属专业PKID
				pd.put("STUDENT_PKID", pd.getString("stuId"));
				PageData pd_stu_dep = (PageData)dao.findForObject("DormitoryInfoMapper.getstuDep", pd);
				stuInfoService.updateDorm(pd.getString("stuId"), "1", pd.getString("dormId"), "分配床位",pd.getString("T_STUDENT_DORM_TYPE_PKID"),pd.getString("DEPARTMENT_PKID"),pd_stu_dep.getString("DEPARTMENT_PKID"));
				pd.put("DEPARTMENT_PKID", pd_stu_dep.getString("DEPARTMENT_PKID"));
				dao.update("DormitoryInfoMapper.updateDormDeptID", pd);
			}else if("学校".equals(pd_dep.getString("AREATYPE"))){
				stuInfoService.updateDorm(pd.getString("stuId"), "1", pd.getString("dormId"), "分配床位",pd.getString("T_STUDENT_DORM_TYPE_PKID"));
			}*/
		/*}*/
		
	}
	
	
	@Override
	public void updatetiaosu(PageData pd) throws Exception {
			
		
		
			dao.update("DormitoryInfoMapper.updatenewDorm", pd);
			dao.update("DormitoryInfoMapper.updateoldDorm", pd);
			
			
		
	}
public void updatetiaosuold(PageData pd) throws Exception {
			
		
		dao.update("DormitoryInfoMapper.updateoldDorm", pd);
			
		
	}

@Override
public void savelog(PageData pd) throws Exception {

	dao.save("DormitoryInfoMapper.savelog", pd);
}

	@Override
	public PageData getDormType(PageData pd_stuDorm) throws Exception {
		return (PageData)dao.findForObject("DormitoryInfoMapper.getDormType", pd_stuDorm);
	}

	@Override
	public PageData getItemList(PageData pd_stu) throws Exception {
		return (PageData)dao.findForObject("DormitoryInfoMapper.getItemList", pd_stu);
	}

	@Override
	public List<PageData> getStuInfoList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DormitoryInfoMapper.getStuInfolist", pd);
	}
	
	 public static String getIntersection(List<String> list1) throws Exception {
		 List dateList = new ArrayList();
	       DateFormat dateFormat=new SimpleDateFormat("yyyy-MM");
	       String zuida="";
	       Date date = null; 
	       for (int i=0;i<list1.size();i++) {  
	    	   date = dateFormat.parse(list1.get(i)); 
	    	   dateList.add(date);
	   		
	        }  
	       ComparatorDate c = new ComparatorDate();  
	       Collections.sort(dateList, c);  
	       dateList.get(dateList.size()-1);
	       String dateString = dateFormat.format(dateList.get(dateList.size()-1));
		   return dateString;
	     //  System.out.print(getStringDateShort(dateList.get(dateList.size()-1)));
	    }
		
		public static class ComparatorDate implements Comparator {  
		    public int compare(Object obj1, Object obj2) {  
		        Date begin = (Date) obj1;  
		        Date end = (Date) obj2;  
		        if (begin.after(end)) {  
		            return 1;  
		        } else {  
		            return -1;  
		        }  
		    }  
		}

		@Override
		public List<PageData> getWhkList(PageData pd) throws Exception {
			return (List<PageData>)dao.findForList("DormitoryInfoMapper.getWhkList", pd);
		}

		@Override
		public List<PageData> getClassTypeList(PageData pd) throws Exception {
			return (List<PageData>)dao.findForList("DormitoryInfoMapper.getClassTypeList", pd);
		} 
		
		@Override
		public PageData getdorminfo(PageData pd) throws Exception {
			return (PageData) dao.findForObject("DormitoryInfoMapper.getdorminfo", pd);
		}

		@Override
		public List<PageData> getRuxuenianfenList(PageData pd) throws Exception {
			return (List<PageData>)dao.findForList("DormitoryInfoMapper.getRuxuenianfenList", pd);
		}
		
		@Override
		public PageData getSCHOOLNAME(PageData pd) throws Exception {
			return (PageData) dao.findForObject("DormitoryInfoMapper.getSCHOOLNAME", pd);
		}
		
}
