package com.fh.service.system.receipt.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.receipt.ReceiptManager;
import com.fh.util.PageData;

@Service
public class ReceiptService implements ReceiptManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Override
	public List<PageData> getAllPayItemList(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("ReceiptMapper.getAllPayItemList", pd);
	}
	
	@Override
	public List<PageData> getAllPayItemList2(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("ReceiptMapper.getAllPayItemList2", pd);
	}
	
	@Override
	public List<PageData> getAllPayItemList3(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("ReceiptMapper.getAllPayItemList3", pd);
	}

	@Override
	public List<PageData> getAllPayOtherItemList(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("ReceiptMapper.getAllPayOtherItemList", pd);
	}

	@Override
	public List<PageData> getPayItemGroupList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReceiptMapper.getPayItemGroupList", pd);
	}

	@Override
	public List<PageData> getPayItemGroupsList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReceiptMapper.getPayItemGroupsList", pd);
	}

	@Override
	public void updatePayItemGroup(PageData pd) throws Exception {
		dao.update("ReceiptMapper.updatePayItemGroup", pd);
	}

	@Override
	public void insertPayItemGroup(PageData pd) throws Exception {
		dao.save("ReceiptMapper.insertPayItemGroup", pd);
	}

	@Override
	public void deleteGroup(PageData pd) throws Exception {
		dao.delete("ReceiptMapper.deleteGroup", pd);
		dao.delete("ReceiptMapper.deleteGroupChild", pd);
	}

	@Override
	public PageData getPayOrderDetailCount(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ReceiptMapper.getPayOrderDetailCount", pd);
	}

	@Override
	public void deleteGroupByPayItemPkid(PageData pd) throws Exception {
		dao.delete("ReceiptMapper.deleteGroupByPayItemPkid", pd);
	}

	@Override
	public List<PageData> getPayOrderDetaillistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ReceiptMapper.getPayOrderDetaillistPage", page);
	}

	@Override
	public  List<PageData> getPayOrderDetailList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReceiptMapper.getPayOrderDetailList", pd);
	}

	@Override
	public void updatePayOrderDetails(PageData pd) throws Exception {
		dao.update("ReceiptMapper.updatePayOrderDetails", pd);
	}

	@Override
	public List<PageData> getPayItemListGroupByPayItemPkid(PageData pd)
			throws Exception {
		return (List<PageData>) dao.findForList("ReceiptMapper.getPayItemListGroupByPayItemPkid", pd);
	}
	
	@Override
	public List<PageData> getAllChildPayItemsByParentPkid(PageData pd)
			throws Exception {
		return (List<PageData>) dao.findForList("ReceiptMapper.getAllChildPayItemsByParentPkid", pd);
	}

	@Override
	public String getSeqReceiptNO(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return com.keman.zhgd.common.util.DateUtil.getYear().substring(2, 4) + dao.getSeq2(pd.getString("SEQNAME"));
	}
	
	@Override
	public PageData getSeqCount(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		PageData pdd = (PageData)dao.findForObject("ReceiptMapper.getSeqCount", pd);
		return pdd;
	}
	
	@Override
	public void createSeq(PageData pd) throws Exception {
		dao.creatSeq2("", pd.getString("SEQNAME"));
	}

	@Override
	public List<PageData> getAllPayItemListAll(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("ReceiptMapper.getAllPayItemListAll", pd);
	}

	@Override
	public PageData getInvoiceInfo(PageData pd) throws Exception {
		List<PageData> InvoiceNoList = (List<PageData>)  dao.findForList("ReceiptMapper.getInvoiceInfo", pd);
		if(InvoiceNoList!=null&&InvoiceNoList.size()>0){
			if(InvoiceNoList.size()-1<Integer.valueOf((String) pd.get("index"))){
				return null;
			}
			return InvoiceNoList.get(Integer.valueOf((String) pd.get("index")));
		}
		return null;
	}

	@Override
	public void updateInvoiceStatus(PageData pd) throws Exception {
		dao.update("ReceiptMapper.updateInvoiceStatus", pd);
	}

	@Override
	public List<PageData> getUserList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReceiptMapper.getUserList", pd);
	}

}
