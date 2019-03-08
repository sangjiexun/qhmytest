package com.fh.service.system.recordOfCostChanges.impl;





import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.recordOfCostChanges.RecordOfCostChangesManager;
import com.fh.service.system.reportStat.ReportStatManager;
import com.fh.util.PageData;
import com.keman.zhgd.common.tree.VO;
import com.keman.zhgd.common.util.Tools;
/**
 * 
 * <p>标题:recordOfCostChangesMapperService</p>
 * <p>描述:</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 刘晓
 * @date 2019年1月16日 上午9:46:57
 */
@Service("recordOfCostChangesService")
public class RecordOfCostChangesService implements RecordOfCostChangesManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public List<PageData> paySumTablelistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.paySumTablelistPage", page);
	}

	@Override
	public List<PageData> paySumDetailTablelistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.paySumDetailTablelistPage", page);
	}

	@Override
	public List<PageData> getListItem(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getListItem", pd);
	}

	@Override
	public List<VO> departmentList(PageData pd) throws Exception {
		return (List<VO>) dao.findForList("recordOfCostChangesMapper.departmentList", pd);
	}

	@Override
	public List<PageData> getGrades(PageData pd) throws Exception {
		return (List<PageData> )dao.findForList("recordOfCostChangesMapper.getGrades", pd);
	}

	@Override
	public List<PageData> getPayMsg(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getPayMsg", pd);
	}

	@Override
	public List<PageData> paySumDetailTablelist(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.paySumDetailTablelist", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> payDetailTablelistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.payDetailTablelistPage", page);
	}

	@Override
	public List<PageData> jieshaorenSumTablelistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.jieshaorenSumTablelistPage", page);
	}

	@Override
	public List<PageData> getZuzhis(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getZuzhis", pd);
	}

	@Override
	public List<PageData> jieshaorenDetailTablelistPage(Page page)
			throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.jieshaorenDetailTablelistPage", page);
	}

	@Override
	public List<PageData> jieshaorenDetailTablelist(PageData pd)
			throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.jieshaorenDetailTablelist", pd);
	}

	@Override
	public List<PageData> jieshaorenSumTablelist(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.jieshaorenSumTablelist", pd);
	}

	@Override
	public List<PageData> getFeeSumlistTable(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getFeeSumlistTable", pd);
	}

	@Override
	public List<PageData> getlistDep(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getlistDep", pd);
	}

	@Override
	public List<PageData> getFeeDetailTablelistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getFeeDetailTablelistPage", page);
	}

	@Override
	public List<PageData> getFeeDetailTablelist(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getFeeDetailTablelist", pd);
	}

	@Override
	public List<PageData> getListItemBL(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getListItemBL", pd);
	}

	@Override
	public List<PageData> getPd_msg(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getPd_msg", pd);
	}

	@Override
	public List<PageData> getStu_msg(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		PageData pd_all = (PageData)dao.findForObject("recordOfCostChangesMapper.getStu_msg_all", pd);
		PageData pd_owe = (PageData)dao.findForObject("recordOfCostChangesMapper.getStu_msg", pd);
		List<PageData> list = new ArrayList<>();
		list.add(pd_owe);
		pd_all.put("STUCOUNT", Integer.parseInt(pd_all.getString("STUCOUNT"))-Integer.parseInt(pd_owe.getString("STUCOUNT")));
		pd_all.put("STATUS", "OK");
		list.add(pd_all);
		return list;
	}

	@Override
	public PageData getAmountMsgFeeDetial(PageData pd) throws Exception {
		return (PageData)dao.findForObject("recordOfCostChangesMapper.getAmountMsgFeeDetial", pd);
	}

	@Override
	public List<PageData> getCollegeSummaryTableListPage(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getCollegeSummaryTableListPage", pd);
	}

	@Override
	public List<PageData> getYuanXiao(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getYuanXiaoList", pd);
	}

	@Override
	public List<PageData> getSuShersList(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getSuShersList", pd);
	}

	@Override
	public PageData getdepartList(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("recordOfCostChangesMapper.getdepartList", pd);
	}

	@Override
	public List<PageData> sushelist(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.sushelist", pd);
	}
	
	@Override
	public PageData getdepartListCj(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("recordOfCostChangesMapper.getdepartListCj", pd);
	}

	@Override
	public List<PageData> getYuanXiaoLists(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getYuanXiaoLists", pd);
	}

	public List<PageData> getStudentDormPlanSumTable2(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		List<PageData> studentDormPlanSumTableList = new ArrayList<>();
		
		if("CAIJING".equals(pd.getString("collegeNameEn"))){
			pd.put("LEIBIE", "2");//财经：统计到学院
		}else{
			pd.put("LEIBIE", "4");//非财经：统计到专业
		}
		
		/*
		 * 获得院校专业列表
		 */
		List<PageData> departmentList = (List<PageData>)dao.findForList("recordOfCostChangesMapper.getDepartmentList", pd);
		
		//总人数-汇总
		int STUDENTCOUNT_TOTAL = 0;
		//确认信息人数-汇总
		int STUDENTCOUNT_TOTAL_SUREINF = 0;
		//应缴人数-汇总
		int STUDENTCOUNT_YIJIAO = 0;
		//床总数-汇总
		int DORMCOUNT_TOTAL = 0;
		//床剩余数-汇总
		int DORMCOUNT_SHENGYU = 0;
		if(departmentList != null && departmentList.size() > 0){
			Map<String, List<PageData>> departmentDormMap = new HashMap<>();
			for(PageData department : departmentList){
				pd.put("DEPARTMENT_ID", department.getString("DEPARTMENT_ID"));
				//获得指定学院下表格数据
				List<PageData> studentDormPlanSumList = (List<PageData>)dao.findForList("recordOfCostChangesMapper.getStudentDormPlanSumTableList", pd);
				departmentDormMap.put(department.getString("NAME"), studentDormPlanSumList);
			}
			
			for(String key : departmentDormMap.keySet()) {
				List<PageData> studentDormPlanSumList = (List<PageData>)departmentDormMap.get(key);
				int i = 0;
				Set<String> nannvSet = new HashSet<String>();
				Set<String> studentCountNanSet = new HashSet<String>();
				Set<String> studentCountNvSet = new HashSet<String>();
				Set<String> studentCountNanSureInfSet = new HashSet<String>();
				Set<String> studentCountNvSureInfSet = new HashSet<String>();
				for(PageData studentDormPlanSum : studentDormPlanSumList){
					//院校专业
					if(i == 0)
						studentDormPlanSum.put("DEPARTMENTNAME", key);
					else
						studentDormPlanSum.put("DEPARTMENTNAME", "");
					i ++;
					
					String SD_SEX_NAME = studentDormPlanSum.getString("SD_SEX_NAME");
					//男女
					if(!nannvSet.contains(studentDormPlanSum.getString("SD_SEX_NAME"))){
						studentDormPlanSum.put("SD_SEX_NAME", studentDormPlanSum.getString("SD_SEX_NAME"));
						nannvSet.add(studentDormPlanSum.getString("SD_SEX_NAME"));
					}else{
						studentDormPlanSum.put("SD_SEX_NAME", "");
					}
					
					if("男".equals(SD_SEX_NAME)){

						//男总人数
						if(!studentCountNanSet.contains(studentDormPlanSum.getString("STUDENTCOUNT_NAN"))){
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL", studentDormPlanSum.getString("STUDENTCOUNT_NAN"));
							studentCountNanSet.add(studentDormPlanSum.getString("STUDENTCOUNT_NAN"));
							
							//合计
							STUDENTCOUNT_TOTAL += Integer.valueOf(studentDormPlanSum.getString("STUDENTCOUNT_NAN")).intValue();
						}else{
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL", "");
						}
						//男确认信息人数
						if(!studentCountNanSureInfSet.contains(studentDormPlanSum.getString("STUDENTCOUNT_NAN_SUREINF"))){
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL_SUREINF", studentDormPlanSum.getString("STUDENTCOUNT_NAN_SUREINF"));
							studentCountNanSureInfSet.add(studentDormPlanSum.getString("STUDENTCOUNT_NAN_SUREINF"));
							
							//合计
							STUDENTCOUNT_TOTAL_SUREINF += Integer.valueOf(studentDormPlanSum.getString("STUDENTCOUNT_NAN_SUREINF")).intValue();
						}else{
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL_SUREINF", "");
						}
					}else if("女".equals(SD_SEX_NAME)){

						//女总人数
						if(!studentCountNvSet.contains(studentDormPlanSum.getString("STUDENTCOUNT_NV"))){
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL", studentDormPlanSum.getString("STUDENTCOUNT_NV"));
							studentCountNvSet.add(studentDormPlanSum.getString("STUDENTCOUNT_NV"));
							
							//合计
							STUDENTCOUNT_TOTAL += Integer.valueOf(studentDormPlanSum.getString("STUDENTCOUNT_NV")).intValue();
						}else{
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL", "");
						}
						//女确认信息人数
						if(!studentCountNvSureInfSet.contains(studentDormPlanSum.getString("STUDENTCOUNT_NV_SUREINF"))){
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL_SUREINF", studentDormPlanSum.getString("STUDENTCOUNT_NV_SUREINF"));
							studentCountNvSureInfSet.add(studentDormPlanSum.getString("STUDENTCOUNT_NV_SUREINF"));
							
							//合计
							STUDENTCOUNT_TOTAL_SUREINF += Integer.valueOf(studentDormPlanSum.getString("STUDENTCOUNT_NV_SUREINF")).intValue();
						}else{
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL_SUREINF", "");
						}
					}
					
					studentDormPlanSum.put("DORMCOUNT_TOTAL", Integer.valueOf(studentDormPlanSum.getString("CHUANGCOUNT")).intValue() 
							- Integer.valueOf(studentDormPlanSum.getString("CHUANGCOUNT_LAOSHENG")).intValue());
					studentDormPlanSum.put("DORMCOUNT_SHENGYU", Integer.valueOf(studentDormPlanSum.getString("CHUANGCOUNT")).intValue() 
							- Integer.valueOf(studentDormPlanSum.getString("CHUANGCOUNT_LAOSHENG")).intValue()
							- Integer.valueOf(studentDormPlanSum.getString("CHUANGCOUNT_XINSHENG")).intValue());
					
					//合计
					STUDENTCOUNT_YIJIAO += Integer.valueOf(studentDormPlanSum.getString("STUDENTCOUNT_YIJIAO")).intValue();
					DORMCOUNT_TOTAL += Integer.valueOf(studentDormPlanSum.getString("DORMCOUNT_TOTAL")).intValue();
					DORMCOUNT_SHENGYU += Integer.valueOf(studentDormPlanSum.getString("DORMCOUNT_SHENGYU")).intValue();
				}
				studentDormPlanSumTableList.addAll(studentDormPlanSumList);
			}
			
			PageData totalPd = new PageData();
			totalPd.put("DEPARTMENTNAME", "");
			totalPd.put("SD_SEX_NAME", "合计");
			totalPd.put("STUDENTCOUNT_TOTAL", STUDENTCOUNT_TOTAL);
			totalPd.put("STUDENTCOUNT_TOTAL_SUREINF", STUDENTCOUNT_TOTAL_SUREINF);
			totalPd.put("DT_NAME", "");
			totalPd.put("STUDENTCOUNT_YIJIAO", STUDENTCOUNT_YIJIAO);
			totalPd.put("DORMCOUNT_TOTAL", DORMCOUNT_TOTAL);
			totalPd.put("DORMCOUNT_SHENGYU", DORMCOUNT_SHENGYU);
			
			studentDormPlanSumTableList.add(totalPd);
			
		}
		return studentDormPlanSumTableList;
	}

	@Override
	public List<PageData> getTheInvoicingList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getTheInvoicingList", pd);
	}

	@Override
	public List<PageData> getOperatorList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getOperatorList", pd);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getStudentDormPlanSumTable(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		List<PageData> studentDormPlanSumTableList = new ArrayList<>();
		
		if("CAIJING".equals(pd.getString("collegeNameEn"))){
			pd.put("LEIBIE", "2");//财经：统计到学院
		}else{
			pd.put("LEIBIE", "4");//非财经：统计到专业
		}
		
		/*
		 * 获得院校专业列表
		 */
		List<PageData> departmentList = (List<PageData>)dao.findForList("recordOfCostChangesMapper.getDepartmentList", pd);
		
		//总人数-汇总
		int STUDENTCOUNT_TOTAL = 0;
		//确认信息人数-汇总
		int STUDENTCOUNT_TOTAL_SUREINF = 0;
		//确认专业人数-汇总
		int STUDENTCOUNT_TOTAL_SUREDEP = 0;
		//应缴人数-汇总
		int STUDENTCOUNT_YIJIAO = 0;
		//床总数-汇总
		int DORMCOUNT_TOTAL = 0;
		//床剩余数-汇总
		int DORMCOUNT_SHENGYU = 0;
		if(departmentList != null && departmentList.size() > 0){
			Map<String, List<PageData>> departmentDormMap = new HashMap<>();
			for(PageData department : departmentList){
				pd.put("DEPARTMENT_ID", department.getString("DEPARTMENT_ID"));
				//获得指定学院下表格数据
				List<PageData> studentDormPlanSumList = (List<PageData>)dao.findForList("recordOfCostChangesMapper.getStudentDormPlanSumTableList", pd);
				departmentDormMap.put(department.getString("NAME"), studentDormPlanSumList);
			}
			
			for(String key : departmentDormMap.keySet()) {
				List<PageData> studentDormPlanSumList = (List<PageData>)departmentDormMap.get(key);
				int i = 0;
				Set<String> nannvSet = new HashSet<String>();
				Set<String> studentCountNanSet = new HashSet<String>();
				Set<String> studentCountNvSet = new HashSet<String>();
				Set<String> studentCountNanSureInfSet = new HashSet<String>();
				Set<String> studentCountNvSureInfSet = new HashSet<String>();
				Set<String> studentCountNanSureDepSet = new HashSet<String>();
				Set<String> studentCountNvSureDepSet = new HashSet<String>();
				for(PageData studentDormPlanSum : studentDormPlanSumList){
					//院校专业
					if(i == 0)
						studentDormPlanSum.put("DEPARTMENTNAME", key);
					else
						studentDormPlanSum.put("DEPARTMENTNAME", "");
					i ++;
					
					String SD_SEX_NAME = studentDormPlanSum.getString("SD_SEX_NAME");
					//男女
					if(!nannvSet.contains(studentDormPlanSum.getString("SD_SEX_NAME"))){
						studentDormPlanSum.put("SD_SEX_NAME", studentDormPlanSum.getString("SD_SEX_NAME"));
						nannvSet.add(studentDormPlanSum.getString("SD_SEX_NAME"));
					}else{
						studentDormPlanSum.put("SD_SEX_NAME", "");
					}
					
					if("男".equals(SD_SEX_NAME)){

						//男总人数
						if(!studentCountNanSet.contains(studentDormPlanSum.getString("STUDENTCOUNT"))){
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL", studentDormPlanSum.getString("STUDENTCOUNT"));
							studentCountNanSet.add(studentDormPlanSum.getString("STUDENTCOUNT"));
							
							//合计
							STUDENTCOUNT_TOTAL += Integer.valueOf(studentDormPlanSum.getString("STUDENTCOUNT")).intValue();
						}else{
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL", "");
						}
						//男确认信息人数
						if(!studentCountNanSureInfSet.contains(studentDormPlanSum.getString("STUDENTCOUNT_SUREINF"))){
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL_SUREINF", studentDormPlanSum.getString("STUDENTCOUNT_SUREINF"));
							studentCountNanSureInfSet.add(studentDormPlanSum.getString("STUDENTCOUNT_SUREINF"));
							
							//合计
							STUDENTCOUNT_TOTAL_SUREINF += Integer.valueOf(studentDormPlanSum.getString("STUDENTCOUNT_SUREINF")).intValue();
						}else{
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL_SUREINF", "");
						}
						//男确认专业人数
						if(!studentCountNanSureDepSet.contains(studentDormPlanSum.getString("STUDENTCOUNT_SUREDEP"))){
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL_SUREDEP", studentDormPlanSum.getString("STUDENTCOUNT_SUREDEP"));
							studentCountNanSureDepSet.add(studentDormPlanSum.getString("STUDENTCOUNT_SUREDEP"));
							
							//合计
							STUDENTCOUNT_TOTAL_SUREDEP += Integer.valueOf(studentDormPlanSum.getString("STUDENTCOUNT_SUREDEP")).intValue();
						}else{
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL_SUREDEP", "");
						}
					}else if("女".equals(SD_SEX_NAME)){

						//女总人数
						if(!studentCountNvSet.contains(studentDormPlanSum.getString("STUDENTCOUNT"))){
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL", studentDormPlanSum.getString("STUDENTCOUNT"));
							studentCountNvSet.add(studentDormPlanSum.getString("STUDENTCOUNT"));
							
							//合计
							STUDENTCOUNT_TOTAL += Integer.valueOf(studentDormPlanSum.getString("STUDENTCOUNT")).intValue();
						}else{
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL", "");
						}
						//女确认信息人数
						if(!studentCountNvSureInfSet.contains(studentDormPlanSum.getString("STUDENTCOUNT_SUREINF"))){
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL_SUREINF", studentDormPlanSum.getString("STUDENTCOUNT_SUREINF"));
							studentCountNvSureInfSet.add(studentDormPlanSum.getString("STUDENTCOUNT_SUREINF"));
							
							//合计
							STUDENTCOUNT_TOTAL_SUREINF += Integer.valueOf(studentDormPlanSum.getString("STUDENTCOUNT_SUREINF")).intValue();
						}else{
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL_SUREINF", "");
						}
						//女确认专业人数
						if(!studentCountNvSureDepSet.contains(studentDormPlanSum.getString("STUDENTCOUNT_SUREDEP"))){
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL_SUREDEP", studentDormPlanSum.getString("STUDENTCOUNT_SUREDEP"));
							studentCountNvSureDepSet.add(studentDormPlanSum.getString("STUDENTCOUNT_SUREDEP"));
							
							//合计
							STUDENTCOUNT_TOTAL_SUREDEP += Integer.valueOf(studentDormPlanSum.getString("STUDENTCOUNT_SUREDEP")).intValue();
						}else{
							studentDormPlanSum.put("STUDENTCOUNT_TOTAL_SUREDEP", "");
						}
					}
					
					//合计
					STUDENTCOUNT_YIJIAO += Integer.valueOf(studentDormPlanSum.getString("STUDENTCOUNT_YIJIAO")).intValue();
					DORMCOUNT_TOTAL += Integer.valueOf(studentDormPlanSum.getString("DORMCOUNT_TOTAL")).intValue();
					DORMCOUNT_SHENGYU += Integer.valueOf(studentDormPlanSum.getString("DORMCOUNT_SHENGYU")).intValue();
				}
				studentDormPlanSumTableList.addAll(studentDormPlanSumList);
			}
			
			PageData totalPd = new PageData();
			totalPd.put("DEPARTMENTNAME", "");
			totalPd.put("SD_SEX_NAME", "合计");
			totalPd.put("STUDENTCOUNT_TOTAL", STUDENTCOUNT_TOTAL);
			totalPd.put("STUDENTCOUNT_TOTAL_SUREINF", STUDENTCOUNT_TOTAL_SUREINF);
			totalPd.put("STUDENTCOUNT_TOTAL_SUREDEP", STUDENTCOUNT_TOTAL_SUREDEP);
			totalPd.put("DT_NAME", "");
			totalPd.put("STUDENTCOUNT_YIJIAO", STUDENTCOUNT_YIJIAO);
			totalPd.put("DORMCOUNT_TOTAL", DORMCOUNT_TOTAL);
			totalPd.put("DORMCOUNT_SHENGYU", DORMCOUNT_SHENGYU);
			
			studentDormPlanSumTableList.add(totalPd);
			
		}
		return studentDormPlanSumTableList;
	}

	@Override
	public List<PageData> getRuxuenianfenList(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getRuxuenianfenList", pd);
	}

	@Override
	public List<PageData> getBanxingList(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getBanxingList", pd);
	}

	@Override
	public List<PageData> getJiaofeileixingList(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("recordOfCostChangesMapper.getJiaofeileixingList", pd);
	}

}
