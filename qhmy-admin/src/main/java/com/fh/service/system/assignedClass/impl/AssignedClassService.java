package com.fh.service.system.assignedClass.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.assignedClass.AssignedClassManager;
import com.fh.util.PageData;
import com.keman.zhgd.common.util.Tools;

/**
 * 
 * <p>标题:assignedClassService</p>
 * <p>描述:</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 王宁
 * @date 2018年3月21日 下午4:07:41
 */
@Service("assignedClassService")
public class AssignedClassService implements AssignedClassManager{
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Override
	public List<PageData> assignedClasslistPage(Page page) throws Exception{
		return (List<PageData>)dao.findForList("AssignedClassMapper.getassignedClasslistPage", page);
	}
	@Override
	public List<PageData> getBanJi(PageData pd) throws Exception {
		//SYS_DEPARTMENT_PKID
		pd.put("deptArray", pd.getString("SYS_DEPARTMENT_PKID").split(","));
		return (List<PageData>)dao.findForList("AssignedClassMapper.getBanJi", pd);
	}
	@Override
	public List<PageData> exportStuInfoToExcel(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AssignedClassMapper.exportStuInfoToExcel", pd);
	}
	@Override
	public void updateStuClass(PageData pd) throws Exception {
		dao.update("AssignedClassMapper.updateStuClass", pd);
	}
	@Override
	public List<PageData> getassignedClasslist(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AssignedClassMapper.getassignedClasslist", pd);
	}
	@Override
	public void updateXueHaoBatch(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		List<PageData> stuList = (List<PageData>)pd.get("list");
//		dao.batchUpdate("AssignedClassMapper.updateStuXuehao", stuList);
		dao.update("AssignedClassMapper.updateStuXuehao", stuList);
	}
	
	@Override
	@Transactional
	public String getSeqXuehaoNext(PageData pd) throws Exception {
		return dao.getSeq4(pd.getString("SEQ_RUXUENIANFEN"));
	}
	@Override
	public void createSeq(PageData pd) throws Exception {
		String regEx="[^0-9]";
		Pattern p = Pattern.compile(regEx);
		List<PageData> ruxuenianfenList = (List<PageData>)dao.findForList("AssignedClassMapper.getRuxuenianfenList", pd);
		if(ruxuenianfenList != null){
			List<PageData> banxingList = (List<PageData>)dao.findForList("AssignedClassMapper.getBanxingList", pd);
			for(PageData nianfen : ruxuenianfenList){
				String RUXUENIANFEN = nianfen.getString("NAME");
				Matcher m = p.matcher(RUXUENIANFEN);
				RUXUENIANFEN = m.replaceAll("").trim();
				if(Tools.notEmpty(RUXUENIANFEN)){//包含数字
					if(RUXUENIANFEN.length() < 2){
						RUXUENIANFEN = "X"+RUXUENIANFEN;
					}else{
						RUXUENIANFEN = RUXUENIANFEN.substring(RUXUENIANFEN.length() - 2, RUXUENIANFEN.length());
					}
				}else{//没有数字
					RUXUENIANFEN = "XX";
				}
				for(PageData banxing : banxingList){
					String BANXINGBIANMA = banxing.getString("BIANMA");
					pd.put("SEQNAME", "SEQ_XUEHAO_"+BANXINGBIANMA+RUXUENIANFEN);
					PageData seq = (PageData)dao.findForObject("AssignedClassMapper.getSeqCount", pd);
					if("0".equals(seq.getString("CCOUNT"))){
						dao.creatSeq("", pd.getString("SEQNAME"));
					}
				}
				
			}
		}
	}
	
	@Override
	public List<PageData> diviXuehaolistPage(Page page) throws Exception{
		return (List<PageData>)dao.findForList("AssignedClassMapper.diviXuehaolistPage", page);
	}
	@Override
	public List<PageData> exportDiviXuehaoExcel(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AssignedClassMapper.exportDiviXuehaoExcel", pd);
	}
	@Override
	public List<PageData> getClass(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AssignedClassMapper.getClass", pd);
	}
	@Override
	public List<PageData> getdiviXuehaolist(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AssignedClassMapper.getdiviXuehaolist", pd);
	}
	@Override
	public List<PageData> getXKCJ(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AssignedClassMapper.getXKCJ", pd);
	}
	@Override
	public List<PageData> getRXNF(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AssignedClassMapper.getRXNF", pd);
	}
	@Override
	public List<PageData> getBanJiType(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AssignedClassMapper.getBanJiType", pd);
	}
}

