package com.fh.service.system.dateAccount.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.dateAccount.DateAccountManager;
import com.fh.util.ArithUtil;
import com.fh.util.PageData;
import com.keman.zhgd.common.DataZidianSelect.PayItemListStatusEnum;
@Service("dateAccountService")
public class DateAccountService implements DateAccountManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Override
	public PageData getAmountMsg(PageData pd) throws Exception {
		return (PageData)dao.findForObject("dateAccountMapper.getAmountMsg", pd);
	}
	@Override
	public List<PageData> dateAccountlistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("dateAccountMapper.dateAccountlistPage", page);
	}
	@Override
	public PageData getlastCheckDate(PageData pd) throws Exception {
		return (PageData)dao.findForObject("dateAccountMapper.getlastCheckDate", pd);
	}
	@Override
	public List<PageData> dateAccountlist(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("dateAccountMapper.dateAccountlist", pd);
	}
	@Override
	public void saveCheck(PageData amountMsg, List<PageData> listDetail) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		amountMsg.put("DATE", sdf.format(date));
		PageData countPd = (PageData)dao.findForObject("dateAccountMapper.findCheckMoneyCount",amountMsg);
		amountMsg.put("CHECKDAY_NO", sdf.format(date)+countPd.getString("COUNT").trim());
		dao.save("dateAccountMapper.saveCheckMoney", amountMsg);
		for(PageData checkDetail:listDetail){
			checkDetail.put("CHECKDAY_PKID", amountMsg.getString("PKID"));
			checkDetail.put("CHECKDAY_NO", amountMsg.getString("CHECKDAY_NO"));
			checkDetail.put("CJR", amountMsg.getString("CJR"));
			dao.save("dateAccountMapper.saveCheckMoneyDetail", checkDetail);
		}
		
	}
	@Override
	public List<PageData> dateAccountlistExcel(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("dateAccountMapper.dateAccountlistExcel", pd);
	}
	@Override
	public void updateClearOrder(PageData pd) throws Exception {
		String[] order_detail_pkids = pd.getString("detail_pkids").split(",");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date d = new Date();
		PageData pd_order = null;
		List<PageData> detailList = null;
		//根据订单明细查询跟他同订单的所有明细
		for(String detail_pkid : order_detail_pkids){
			pd.put("DETAIL_PKID", detail_pkid);
			detailList = (List<PageData>)dao.findForList("dateAccountMapper.getDetailList", pd);
			for(PageData pd_detail : detailList){
				pd.put("PKID", pd_detail.getString("PKID"));
				//根据订单明细PKID找出需要删除的信息插入备份表中
				dao.save("dateAccountMapper.updateClearOrder", pd);
				pd_order = new PageData();
				//将原名单的钱变回来
				pd_order = (PageData)dao.findForObject("dateAccountMapper.getOderDetail", pd);
				//锁名单
				PageData pd_item_list = (PageData)dao.findForObject("dateAccountMapper.itemlistLock", pd_order);
				String AMOUNTRECEIVABLE = pd_item_list.getString("AMOUNTRECEIVABLE");//获取应收
				String AMOUNTCOLLECTION = pd_item_list.getString("AMOUNTCOLLECTION");//获取实收
				String MONEY = pd_order.getString("MONEY");//缴费金额
				if("JX".equals(pd_order.getString("INPUT_OUTPUT"))){//代表收入
					pd_item_list.put("AMOUNTCOLLECTION", ArithUtil.sub(AMOUNTCOLLECTION, MONEY));
					if(Double.parseDouble(AMOUNTRECEIVABLE)==ArithUtil.sub(AMOUNTCOLLECTION, MONEY)){
						pd_item_list.put("STATUS", PayItemListStatusEnum.已完成.getValue());
						pd_item_list.put("VERIFICATION_DATE",sdf.format(d));
					}else{
						pd_item_list.put("STATUS", PayItemListStatusEnum.欠费.getValue());
						pd_item_list.put("VERIFICATION_DATE","");
					}
				}else{//代表支出
					pd_item_list.put("AMOUNTCOLLECTION", ArithUtil.add(AMOUNTCOLLECTION, MONEY));
					if(Double.parseDouble(AMOUNTRECEIVABLE)==ArithUtil.add(AMOUNTCOLLECTION, MONEY)){
						pd_item_list.put("STATUS", PayItemListStatusEnum.已完成.getValue());
						pd_item_list.put("VERIFICATION_DATE",sdf.format(d));
					}else{
						pd_item_list.put("STATUS", PayItemListStatusEnum.欠费.getValue());
						pd_item_list.put("VERIFICATION_DATE","");
					}
				}
				
				if(PayItemListStatusEnum.已关闭.getValue().equals(pd_item_list.getString("STATUS"))){
					pd_item_list.put("STATUS",PayItemListStatusEnum.已关闭.getValue());
				}
				dao.update("dateAccountMapper.updateItemList", pd_item_list);
				//删除订单信息
				dao.delete("dateAccountMapper.deleteOrder", pd_order);
				//删除订单日志信息
				dao.delete("dateAccountMapper.deleteOrderLog", pd_order);
				//删除订单money信息
				dao.delete("dateAccountMapper.deleteOrderDetailMoney", pd_order);
				//删除订单明细信息
				dao.delete("dateAccountMapper.deleteOrderDetail", pd_order);
			}
		}
	}

}
