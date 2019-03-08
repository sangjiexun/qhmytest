package com.fh.service.system.dormitorytype.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.dormitorytype.DormitoryTypeManager;
import com.fh.util.PageData;
@Service("dormitorytypeService")
public class DormitoryTypeService implements DormitoryTypeManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public List<PageData> dormitorytype_list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("DormitorytypeMapper.dormitorytype_listPage", page);
	}

	@Override
	public PageData getDormitorytype(PageData pd) throws Exception {
		return (PageData)dao.findForObject("DormitorytypeMapper.getDormitorytype", pd);
	}

	@Override
	public PageData getDT_NAME(PageData pd) throws Exception {
		return (PageData)dao.findForObject("DormitorytypeMapper.getDT_NAME", pd);
	}

	@Override
	public void update(PageData pd) throws Exception {
		//dao.update("DormitorytypeMapper.updateItem", pd);
		dao.update("DormitorytypeMapper.update", pd);
		
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("DormitorytypeMapper.save", pd);
	}

	@Override
	public void updateIsUse(PageData pd) throws Exception {
		dao.update("DormitorytypeMapper.updateIsUse", pd);
	}

	@Override
	public PageData getDormUse(PageData pd) throws Exception {
		//如果发布缴费中已经使用或者宿舍配置中已经使用则不允许删除
	/*	PageData pdd = (PageData)dao.findForObject("DormitorytypeMapper.getFBDormtype", pd);
		if(pdd!=null){
			return pdd;
		}else{*/
		PageData	pdd = (PageData)dao.findForObject("DormitorytypeMapper.getDormUse", pd);
			return pdd; 
		/*}*/
	}

	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("DormitorytypeMapper.delete", pd);
	}


}
