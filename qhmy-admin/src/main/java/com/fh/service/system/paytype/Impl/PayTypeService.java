package com.fh.service.system.paytype.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.paytype.PayTypeManager;
import com.fh.util.PageData;
/**
 * 
 * <p>标题:PayTypeService</p>
 * <p>描述:</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 康程亮
 * @date 2018年1月16日 上午9:35:31
 */
@Service
public class PayTypeService implements PayTypeManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public List<PageData> payTypelistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList(
				"PayTypeMapper.payTypelistPage", page);
	}

	@Override
	public void addPayType(PageData pd) throws Exception {
		dao.save("PayTypeMapper.addPayType", pd);

	}

	@Override
	public void editPayType(PageData pd) throws Exception {
		dao.update("PayTypeMapper.editPayType", pd);
	}

	@Override
	public void delPayType(PageData pd) throws Exception {
		dao.delete("PayTypeMapper.delPayType", pd);

	}

	@Override
	public PageData getPayTypeByPkid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PayTypeMapper.getPayTypeByPkid", pd);
	}

	@Override
	public List<PageData> getPayItems(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PayTypeMapper.getPayItems", pd);
	}

	@Override
	public List<PageData> getPayItemsByName(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PayTypeMapper.getPayItemsByName", pd);
	}

	@Override
	public void updatePaixu(PageData pd) throws Exception {
		dao.update("PayTypeMapper.updatePaixu", pd);
	}

	@Override
	public void updateDJLQ(PageData pd) throws Exception {
		dao.update("PayTypeMapper.updateDJLQ", pd);
	}
}
