package com.fh.service.importitem.itemlist.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.service.importitem.itemlist.ItemListManager;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

/**
 * 
 * <p>标题:ItemListService</p>
 * <p>描述:导入缴费名单</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月17日 上午12:55:33
 */
@Service("itemListManager")
public class ItemListService implements ItemListManager{
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public void saveItemList(PageData pageData) throws Exception {
		dao.save("ItemListMapper.saveItemList", pageData);
	}

	@Override
	public PageData queryPayItemAndList(PageData pageData) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject("ItemListMapper.queryPayItemAndList", pageData);
	}

	@Override
	public PageData queryStudent(PageData pageData) throws Exception {
		return (PageData) dao.findForObject("ItemListMapper.queryStudent", pageData);
	}
	
	
	@Override
	public PageData queryStu(PageData pageData) throws Exception {
		return (PageData) dao.findForObject("ItemListMapper.queryStu", pageData);
	}
	
	@Override
	public PageData daoruqueryStu(PageData pageData) throws Exception {
		return (PageData) dao.findForObject("ItemListMapper.daoruqueryStu", pageData);
	}

	@Override
	public void updateItemList(PageData pageData) throws Exception {
		// TODO Auto-generated method stub
		dao.update("ItemListMapper.updateItemList", pageData);
	}

	@Override
	public PageData stuIsExsitsInList(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ItemListMapper.stuIsExsitsInList", pd);
		
	}

	@Override
	public PageData queryPayList(PageData pageData) throws Exception {
		return (PageData) dao.findForObject("ItemListMapper.queryItemListOnly", pageData);
	}

	@Override
	public PageData getItemMsg(PageData pageData) throws Exception {
		return (PageData)dao.findForObject("ItemListMapper.getItemMsg", pageData);
	}

	@Override
	public void batchSaveDatas(List<PageData> batchSaveDatas) throws Exception {
		try {
			dao.batchSave("ItemListMapper.batchSaveDatas", batchSaveDatas);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("批量异常");
		}
	}

	@Override
	public PageData queryStudentList(PageData pageData) throws Exception {
		return (PageData)dao.findForObject("ItemListMapper.queryStudentList", pageData);
	}

	@Override
	public PageData getPayList(PageData pageData) throws Exception {
		return (PageData)dao.findForObject("ItemListMapper.getPayList", pageData);
	}

	@Override
	public void updatePayListDicout(List<PageData> successList)
			throws Exception {
		for(PageData pd:successList){
			PageData pageData = (PageData) dao.findForObject("ItemListMapper.getPayItemListLock", pd);//名单 锁表
			if(pageData != null && pageData.get("COST") != null){
				BigDecimal costYet = new BigDecimal(pageData.getString("COST"));
				if("0".equals(pd.getString("jianMianJine"))){//直减情况下
					pd.put("discountMoney", "0");
				}else{
					pd.put("discountMoney", pd.getString("jianMianJine"));
				}
				pd.put("payItemListPkid", pageData.getString("PKID"));
				dao.delete("PayManageMapper.deletePayItemListDiscount", pd);
				pd.put("PKID", UuidUtil.get32UUID());
				pd.put("ALONEFLAG", "Y");
				pd.put("discountZhe", null);
				pd.put("discountMode", "2");
				pd.put("amountreceivable", costYet.subtract(new BigDecimal(pd.getString("jianMianJine"))));
				dao.save("PayManageMapper.insertPayItemListDiscount", pd);
				pd.put("discount", "直减-"+pd.getString("jianMianJine"));
				dao.update("ItemListMapper.updatePayItemList", pd);
			}
		}
		
	}

	@Override
	public void updateStuPayItemLoan(List<PageData> list) throws Exception {
		dao.batchUpdate("ItemListMapper.updateStuPayItemLoan", list);
	}

	@Override
	public void updateStuPayLoan(PageData pd) throws Exception {
		dao.update("ItemListMapper.updateStuPayLoan", pd);
	}

	@Override
	public void updateStuPayDerate(PageData pd) throws Exception {
		PageData pageData = (PageData) dao.findForObject("ItemListMapper.getPayItemListLock", pd);//名单 锁表
		if(pageData != null && pageData.get("COST") != null){
			BigDecimal costYet = new BigDecimal(pageData.getString("COST"));
			if("0".equals(pd.getString("jianMianJine"))){//直减情况下
				pd.put("discountMoney", "0");
			}else{
				pd.put("discountMoney", pd.getString("jianMianJine"));
			}
			pd.put("payItemListPkid", pageData.getString("PKID"));
			dao.delete("PayManageMapper.deletePayItemListDiscount", pd);
			pd.put("PKID", UuidUtil.get32UUID());
			pd.put("ALONEFLAG", "Y");
			pd.put("discountZhe", null);
			pd.put("discountMode", "2");
			pd.put("amountreceivable", costYet.subtract(new BigDecimal(pd.getString("jianMianJine"))));
			dao.save("PayManageMapper.insertPayItemListDiscount", pd);
			pd.put("discount", "直减-"+pd.getString("jianMianJine"));
			dao.update("ItemListMapper.updatePayItemList", pd);
		}
	}

	@Override
	public PageData queryStudentYj(PageData pageData) throws Exception {
		return (PageData) dao.findForObject("ItemListMapper.queryStudentYj", pageData);
	}
	
	
}
