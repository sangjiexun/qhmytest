package com.fh.service.system.paymanage.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.TpayItemList;
import com.fh.service.itemlist.discount.impl.DisCountCalculateImpl;
import com.fh.service.system.paymanage.PayManageManager;
import com.fh.util.ArithUtil;
import com.fh.util.DictZiDianSelect.PayItemListStatusEnum;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;
import com.keman.zhgd.common.Tools;

@Service
public class PayManageService implements PayManageManager {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource
	private DisCountCalculateImpl disCountCalculateImpl;
	@Override
	public void saveOtherPayItem(PageData pd) throws Exception {		
		dao.save("PayManageMapper.saveOtherPayItem", pd);
	}
	@Override
	public List<PageData> otherPayItemlistPage(Page page) throws Exception {
		
		return (List<PageData>) dao.findForList("PayManageMapper.otherPayItemlistPage", page);
	}
	@Override
	public void updateotherpayitemset(PageData pd) throws Exception {
		dao.update("PayManageMapper.updateotherpayitemset", pd);
		
	}
	
	@Override
	public void updateitemlist(PageData pd) throws Exception {
		dao.update("PayManageMapper.updateitemlist", pd);
		
	}
	@Override
	public void deleteotherpayitemset(PageData pd) throws Exception {
		dao.update("PayManageMapper.deleteotherpayitemset", pd);
		
	}
	
	@Override
	public PageData getSendMsgCounts(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PayManageMapper.getSendMsgCounts", pd);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getnianji(PageData pd) throws Exception {
		List<PageData> list = null;
		list = (List<PageData>) dao.findForList("PayManageMapper.getnianji", pd);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getbanxing(PageData pd) throws Exception {
		List<PageData> list = null;
		list = (List<PageData>) dao.findForList("PayManageMapper.getbanxing", pd);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getyouhuifw(PageData pd) throws Exception {
		List<PageData> list = null;
		list = (List<PageData>) dao.findForList("PayManageMapper.getyouhuifw", pd);
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getZuzhis(PageData pd) throws Exception {
		List<PageData> list = null;
		list = (List<PageData>) dao.findForList("PayManageMapper.getZuzhis", pd);
		return list;
	}
	
	

	@Override
	public PageData getStuInfo(PageData pd) throws Exception {
		
		return (PageData) dao.findForObject("PayManageMapper.getStuInfo", pd);
	}
	@Override
	public List<PageData> getOtherPayItems() throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getOtherPayItems", "");
	}
	
	
	@Override
	public PageData findpkbyid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PayManageMapper.findpkbyid", pd);
	}
	
	@Override
	public void savefabumd(PageData pd,List<PageData> list) throws Exception {	
		//修改该项目时应该先删除再重新插入
		if("Y".equals(pd.getString("xiugai"))){
			dao.update("PayManageMapper.updateitem", pd);
			dao.delete("PayManageMapper.deleteItemlist",pd);
			String pkid=pd.getString("pkid");
			for(PageData pdd:list){
				pdd.put("PAY_ITEM_PKID", pkid);
	 			dao.save("PayManageMapper.saveItemListdr", pdd);
	 		}
		}else{
			//保存 item表信息
			dao.save("PayManageMapper.saveItem", pd);
			for(PageData pdd:list){
	 			dao.save("PayManageMapper.saveItemListdr", pdd);
	 		}
		}	
	}
	@Override
	public void saveitem(PageData pd) throws Exception {	
		if("true".equals(pd.getString("flag_ggje"))){
			dao.delete("PayManageMapper.deleteItemlist",pd);
		}
		//保存 item表信息
		if("Y".equals(pd.getString("xiugai"))){
			//dao.update("PayManageMapper.updateitem", pd);
			dao.delete("PayManageMapper.deleteItemrule",pd);
		}else{
			dao.save("PayManageMapper.saveItem", pd);
		}
 		
 		
 		
 		
	}
	@Override
	public void savefabu(PageData pd, List<PageData> list_gz,
			List<PageData> list_gz_fb) throws Exception {
		//修改该项目时应该先删除再重新插入
		if("Y".equals(pd.getString("xiugai"))){
			pd.put("pkid", pd.getString("ITEMPKID"));
			dao.delete("PayManageMapper.deleteItemrulefb", pd);
			dao.delete("PayManageMapper.deleteItemrule",pd);
		}else{
			//保存 item表信息
	 		dao.save("PayManageMapper.saveItem", pd);
		}
		//保存 rule表信息
		for(PageData pdd:list_gz){
			dao.save("PayManageMapper.saveRule",pdd);
		}
		//保存rule_fb表信息
		for(PageData pdd:list_gz_fb){
			dao.save("PayManageMapper.saveRuleFb",pdd);
		}
	}
	@Override
	public void savefabu(PageData pd,List<PageData> list_gz) throws Exception {
		
		//修改该项目时应该先删除再重新插入
		if("Y".equals(pd.getString("xiugai"))){
			//dao.delete("PayManageMapper.deleteItem",pd);
			dao.update("PayManageMapper.updateitem", pd);
			String pkid=pd.getString("pkid");
			pd.put("ITEMPKID", pkid);
			dao.delete("PayManageMapper.deleteItemrule",pd);
			dao.delete("PayManageMapper.deleteItemlist",pd);
			
		}else{
			//保存 item表信息
	 		dao.save("PayManageMapper.saveItem", pd);
		}
		
		
		
		//保存 rule表信息 保存 list表信息
		List<PageData> list_xs;
		List<PageData> list_flag;
		PageData list_up;
		String je=pd.getString("fabuje");
		//发布项目时的规则条数
		for(PageData pdd:list_gz){
			pdd.put("ITEMPKID", pd.getString("ITEMPKID"));//队伍PKID
			dao.save("PayManageMapper.saveRule",pdd);
			
			list_xs=new ArrayList<>();
			//pageData.put("client_id", UuidUtil.get32UUID())
			list_xs=(List<PageData>) dao.findForList("PayManageMapper.getxspkidlist", pdd);
			//规则选定的组织的学生数
			for(int i=0;i<list_xs.size();i++){
				pdd.put("XSPKID", list_xs.get(i).getString("PKID"));
				pdd.put("LISTPKID", UuidUtil.get32UUID());
				pdd.put("JE", je);
				dao.save("PayManageMapper.saveItemLlstxs",pdd);
				list_flag=new ArrayList<>();
				list_flag =(List<PageData>) dao.findForList("PayManageMapper.getxsflaglist", pdd);
				//查询 学生对应的flag
				if(list_flag.size()>0){
					for(int j=0;j<list_flag.size();j++){
						pdd.put("FLAG", list_flag.get(j).getString("EXCEPTIONAL"));
						list_up=new PageData();
						list_up =(PageData) dao.findForObject("PayManageMapper.getxsyhlist", pdd);
						if(list_up != null){ //有学生 但是该学生没有对应的 rule表的优惠范围
							String yhlx=list_up.getString("DISCOUNT_MODE");
							//0:不优惠  1:打折  2:直减
							String DC="";
							double ysje = 0;
							double DMONEY = 0;
							if("0".equals(yhlx)){
								DC="不优惠";
							}else if("1".equals(yhlx)){
								String dt=list_up.getString("DISCOUNT");
								DC="打折-"+dt;
								double zk=ArithUtil.div(dt, "10");
								ysje=ArithUtil.mul(je,Double.toString(zk));//打折后金额
								DMONEY= ArithUtil.sub(je, Double.toString(ysje));//优惠金额
								
							}else if("2".equals(yhlx)){
								String zj=list_up.getString("DISCOUNT_MONEY");
								DC="直减-"+zj;
								ysje= ArithUtil.sub(je,zj);
								DMONEY= ArithUtil.sub(je, Double.toString(ysje));//优惠金额
							}
							pdd.put("yhlx", yhlx); //优惠类型
							pdd.put("DC", DC); // 页面展现 如：直减 - 2000
							pdd.put("ysje", ysje); //应收金额
							pdd.put("DMONEY", DMONEY); //优惠金额
							dao.update("PayManageMapper.updateList", pdd);
							break;
						}else{
							pdd.put("yhlx", "0"); //优惠类型
							pdd.put("DC", "不优惠"); // 页面展现 如：直减 - 2000
							pdd.put("ysje", je); //应收金额
							pdd.put("DMONEY", "0"); //优惠金额
							dao.update("PayManageMapper.updateList", pdd);
						}
					}
				}else{
					
					pdd.put("yhlx", "0"); //优惠类型
					pdd.put("DC", "不优惠"); // 页面展现 如：直减 - 2000
					pdd.put("ysje", je); //应收金额
					pdd.put("DMONEY", "0"); //优惠金额
					dao.update("PayManageMapper.updateList", pdd);
				}
				
				
			}
		}
	}
	
	
	@Override
	public List<PageData> jfshenhelistPage(Page page) throws Exception {
		
		return (List<PageData>) dao.findForList("PayManageMapper.jfshenhelistPage", page);
	}
	
	@Override
	public List<PageData> rulelist(PageData pd) throws Exception {
		
		return (List<PageData>) dao.findForList("PayManageMapper.rulelist", pd);
	}
	
	@Override
	public List<PageData> getsclist(PageData pd) throws Exception {
		
		return (List<PageData>) dao.findForList("PayManageMapper.getsclist", pd);
	}
	
	@Override
	public List<PageData> getlist(PageData pd) throws Exception {
		
		return (List<PageData>) dao.findForList("PayManageMapper.getlist", pd);
	}
	
	@Override
	public List<PageData> getlistrule(PageData pd) throws Exception {
		
		return (List<PageData>) dao.findForList("PayManageMapper.rulelist", pd);
	}
	
	@Override
	public void batchGree(String[] pkids) throws Exception {
		dao.update("PayManageMapper.updatebatchGree", pkids);
	}
	
	@Override
	public void batchGreeOne(PageData pd) throws Exception {
		dao.update("PayManageMapper.updatebatchGreeOne", pd);
	}
	
	@Override
	public void batchNo(String[] pkids) throws Exception {
		dao.update("PayManageMapper.updatebatchNo", pkids);
	}
	
	@Override
	public void batchNoOne(PageData pd) throws Exception {
		dao.update("PayManageMapper.updatebatchNoOne", pd);
	}
	
	
	
	@Override
	public PageData getItem(PageData pd) throws Exception {
		
		return (PageData) dao.findForObject("PayManageMapper.getItem", pd);
	}

	@Override
	public List<PageData> getPayItemlistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getPayItemlistPage", page);
	}
	@Override
	public void updatePayItem(PageData pd) throws Exception {
		dao.update("PayManageMapper.updatePayItem", pd);
	}
	@Override
	public List<PageData> getPayItemListPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getPayItemListlistPage", page);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void updatePayItemList(PageData pd) throws Exception {
		PageData pageData = (PageData) dao.findForObject("PayManageMapper.getPayItemListInfoLock", pd);
		
		if(pageData != null && pageData.get("COST") != null){
			BigDecimal costYet = new BigDecimal(pd.getString("cost")/*pageData.get("COST").toString()*/);
			if("0".equals(pd.getString("discountMode"))){//直减情况下
				pd.put("discountMoney", "0");
			}else{
				pd.put("discountMoney", costYet.subtract(new BigDecimal(pd.getString("amountreceivable"))));
			}
			/*TpayItemList tpayItemList = disCountCalculateImpl.disCountCalculateReturnTpayItemListOnly(pageData.getString("STUDENT_PKID"), pageData.getString("PAY_ITEM_PKID"),pd.getString("cost"));
			pd.put("discountMoney", tpayItemList.getDISCOUNT_MONEY());*/
		}
		dao.delete("PayManageMapper.deletePayItemListDiscount", pd);
		pd.put("PKID", UuidUtil.get32UUID());
		pd.put("ALONEFLAG", "Y");
		dao.save("PayManageMapper.insertPayItemListDiscount", pd);
		dao.update("PayManageMapper.updatePayItemList", pd);
	}
	@Override
	public PageData getPayItemListInfo(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PayManageMapper.getPayItemListInfo", pd);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void deletePayItem(PageData pd) throws Exception {
		List<PageData> payItemListList = (List<PageData>)dao.findForList("PayManageMapper.getPayItemListByPayItemPkid", pd);
		if(payItemListList != null && payItemListList.size() > 0){
			for (int i = 0; i <payItemListList.size(); i++) {
				PageData pdd = payItemListList.get(i);
				pdd.put("PKID", UuidUtil.get32UUID());
				//添加到缴费名单删除表
				dao.save("PayManageMapper.insertPayItemListDel", pdd);
			}
			
			//删除缴费名单表
			dao.delete("PayManageMapper.deletePayItemList", pd);
		}
		
		//删除缴费项目表
		pd.put("PKID", pd.getString("payItemPkid"));
		dao.delete("PayManageMapper.deletePayItem", pd);
	}
	@Override
	public List<PageData> getPayItemListByPayItemPkid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getPayItemListByPayItemPkid", pd);
	}
	@Override
	public List<PageData> getPayItems(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getPayItems", pd);
	}
	@Override
	public PageData getStuOrderMoney(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PayManageMapper.getStuOrderMoney", pd);
	}
	@Override
	public PageData getStuItemDiscountMoney(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PayManageMapper.getStuItemDiscountMoney", pd);
	}
	@Override
	public List<PageData> getStuCanOutPayitems(PageData pd,List<String> list) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getStuCanOutPayitems", pd);
	}
	@Override
	public List<PageData> getStuCanInPayitems(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getStuCanInPayitems", pd);
	}
	@Override
	public List<PageData> getPayItemlistPageByStudentPkidAndStatus(Page page) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getPayItemlistPageByStudentPkidAndStatus", page);
	}
	
	@Override
	public List<PageData> getallrulebypkid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getallrulebypkid", pd);
	}
	
	@Override
	public List<PageData> getPayItemlistByStudentPkidAndStatus(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getPayItemlistByStudentPkidAndStatus", pd);
	}
	@Override
	public List<PageData> datalistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.datalistPage", page);
	}

	public PageData getpayItemMoneyByList(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PayManageMapper.getpayItemMoneyByList", pd);
	}
	@Override
	public PageData getPayItemMoneyByRule(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PayManageMapper.getPayItemMoneyByRule", pd);
	}

	@Override
	public List<PageData> getPayOrderDetailList(PageData pd)
			throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getPayOrderDetailList", pd);
	}
	@Override
	public List<PageData> getPayOrderDetailMoneyList(PageData pd)
			throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getPayOrderDetailMoneyList", pd);
	}
	@Override
	public PageData getPayItemList(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PayManageMapper.getPayItemList", pd);
	}
	
	@Override
	public PageData getStuYJInfo(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PayManageMapper.getStuYJInfo", pd);
	}
	
	@Override
	public PageData getorderInfo(String PKID) throws Exception {
		return (PageData) dao.findForObject("PayManageMapper.getorderInfo", PKID);
	}
	
	@Override
	public List<PageData>  getPayOrderDetail(PageData pd) throws Exception {
		return (List<PageData> ) dao.findForList("PayManageMapper.getPayOrderDetail", pd);
	}
	@Override
	public void updatePayOrderDetail(PageData pd) throws Exception {
		dao.update("PayManageMapper.updatePayOrderDetail", pd);
	}
	@Override
	public List<PageData> getPayOrderDetailGroup(PageData pd) throws Exception {
		return (List<PageData> ) dao.findForList("PayManageMapper.getPayOrderDetailGroup", pd);
	}
	@Override
	public List<PageData> getPayOrderDetailListForPrint(PageData pd)
			throws Exception {
		return (List<PageData> ) dao.findForList("PayManageMapper.getPayOrderDetailListForPrint", pd);
	}
	@Override
	public List<PageData> getAllPayItems(PageData pd) throws Exception {
		return  (List<PageData> ) dao.findForList("PayManageMapper.getAllPayItems", pd);
	}
	@Override
	public void updatePayItemStatus(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.update("PayManageMapper.updatePayItemStatus", pd);
	}
	@Override
	public List<PageData> getStudentPaylistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getStudentPaylistPage", page);
	}
	
	@Override
	public List<PageData> getManeyChangelistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getManeyChangelistPage", page);
	}
	
	@Override
	public List<PageData> getManeyLOGlistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getManeyLOGlistPage", page);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void savePayItemList(PageData pd) throws Exception {
		try {
			if(!StringUtils.isEmpty(pd.getString("studentPkids"))){
				String [] studentPkidsArray = pd.getString("studentPkids").split(",");
				for (int i = 0; i < studentPkidsArray.length; i++) {
					pd.put("studentPkid", studentPkidsArray[i]);
					//查询缴费记录里是否有该学生
					PageData countPD = (PageData)dao.findForObject("PayManageMapper.getCountFromPayItemList", pd);
					if("0".equals(countPD.getString("CCOUNT"))){
						PageData pdd = new PageData();
						pdd.put("PKID", pd.getString("payItemPkid"));
						PageData payItem = (PageData)dao.findForObject("PayManageMapper.getPayItemInfo", pdd);
						if(payItem != null){
							pd.put("COST",payItem.getString("COST"));
							pd.put("studentPkid", studentPkidsArray[i]);
							
							pd.put("PKID",UuidUtil.get32UUID());
							
							TpayItemList tpayItemList = disCountCalculateImpl.disCountCalculateReturnTpayItemListOnly(studentPkidsArray[i], pd.getString("payItemPkid"),payItem.getString("COST"));
							pd.put("DISCOUNT", StringUtils.isEmpty(tpayItemList.getDISCOUNT())?"不优惠":tpayItemList.getDISCOUNT());
							if(!StringUtils.isEmpty(payItem.getString("COST")) && !StringUtils.isEmpty(tpayItemList.getDISCOUNT_MONEY())){
								pd.put("AMOUNTRECEIVABLE", new BigDecimal(payItem.getString("COST")).subtract(new BigDecimal(tpayItemList.getDISCOUNT_MONEY())));
								
							}else{
								pd.put("AMOUNTRECEIVABLE", payItem.getString("COST"));
							}
							pd.put("DISCOUNT_MONEY", tpayItemList.getDISCOUNT_MONEY());
							pd.put("DISCOUNT_MODE", tpayItemList.getDISCOUNT_MODE());
							
							dao.save("PayManageMapper.savePayItemList", pd);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("添加缴费名单产生异常");
		}
		
		
	}
	@Override
	public List<PageData> exportStudentPayList(PageData pd) throws Exception {
		List<String> DEPARTMENT_PKID = new ArrayList<>();
		if(!StringUtils.isEmpty(pd.getString("departmentPkids")) && !("undefined").equals((pd.getString("departmentPkids")))                         ){
			String [] DEPARTMENT_PKIDArray = pd.getString("departmentPkids").split(",");
			for (int i = 0; i < DEPARTMENT_PKIDArray.length; i++) {
				if(!StringUtils.isEmpty(DEPARTMENT_PKIDArray[i])){
					DEPARTMENT_PKID.add(DEPARTMENT_PKIDArray[i]);
				}
			}
		}
		pd.put("departmentPkids", DEPARTMENT_PKID);
		return (List<PageData>)dao.findForList("PayManageMapper.getPayItemListList", pd);
	}
	
	
	
	
	@Override
	public List<PageData> yanzmc(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.yanzmc", pd);
	}
	
	
	@Override
	public void updatePayItemListStatusClose(PageData pageData) throws Exception{
		dao.update("PayManageMapper.updatePayItemListStatusClose", pageData);
	}
	

	@Override
	public PageData getPayOrderDetailCount(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PayManageMapper.getPayOrderDetailCount", pd);
	}


	@Override
	public void updatePayItemListStatusOk(PageData pageData) throws Exception{
		dao.update("PayManageMapper.updatePayItemListStatusOk", pageData);
	}
	
	/**
	 * 
	 * <p>描述:导出名单</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月22日 下午8:21:33
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> exportStuPayList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("PayManageMapper.getCanExportPayItemList", pd);
	}
	@Override
	public void deletePayItemListObject(PageData pd) throws Exception {
		dao.delete("PayManageMapper.deletePayItemListObject", pd);
	}
	@Override
	public void deletedr(PageData pd) throws Exception {
		dao.delete("PayManageMapper.deletedr", pd);
	}
	@Override
	public List<PageData> getPayItemListForPrint(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getPayItemListForPrint", pd);
	}
	@Override
	public List<PageData> getStudentQuerylistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getStudentQuerylistPage", page);
	}
	@Override
	public PageData getStudentInfo(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PayManageMapper.getStudentInfo", pd);
	}
	@Override
	public PageData getStudentInfo2(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PayManageMapper.getStudentInfo2", pd);
	}
	
	@Override
	public PageData getitemlistmax(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PayManageMapper.getitemlistmax", pd);
	}
	
	@Override
	public List<PageData> getStudentQuerylist2(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getStudentQuerylist2", pd);
	}
	@Override
	public void updatePayItemListStatusIsClose(PageData pd) throws Exception {
		dao.update("PayManageMapper.updatePayItemListStatus", pd);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void insertPayItemList(PageData pd) throws Exception {
		try {			
			dao.save("PayManageMapper.savePayItemList", pd);		
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("添加缴费名单产生异常");
		}
		
		
	}
	@Override
	public PageData getDetailByOtherPayItem(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PayManageMapper.getDetailByOtherPayItem", pd);
	}
	@Override
	public List<PageData> getPayStyleList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("PayManageMapper.getPayStyleList", pd);
	}
	@Override
	public List<PageData> getBanXing(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("PayManageMapper.getBanXing", pd);
	}
	@Override
	public List<PageData> gethzxxList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("PayManageMapper.gethzxxList", pd);
	}
	@Override
	public List<PageData> gethzxxListAdd(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("PayManageMapper.gethzxxListAdd", pd);
	}
	@Override
	public List<PageData> getClassList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("PayManageMapper.getClassList", pd);
	}
	@Override
	public List<PageData> getMeiYuanList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("PayManageMapper.getMeiYuanList", pd);
	}
	@Override
	public PageData indefyItem(PageData pd) throws Exception {
		return (PageData)dao.findForObject("PayManageMapper.indefyItem", pd);
	}
	@Override
	public List<PageData>  getPayTypePkid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getPayTypePkid", pd);
	}
	@Override
	public PageData  getPayTypeYjPkid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PayManageMapper.getPayTypeYjPkid", pd);
	}
	@Override
	public PageData getPayTypeName(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PayManageMapper.getPayTypeName", pd);
	}
	@Override
	public void delitemlist(PageData pd) throws Exception {
		PageData pdList = (PageData)dao.findForObject("PayManageMapper.getPayItemListByPkid", pd);
		if(pdList!=null){
			dao.save("PayManageMapper.savePayItemListDel", pdList);
		}
		dao.delete("PayManageMapper.deletePayItemListByPkid", pd);
	}
	@Override
	public PageData getRuleFB(PageData a) throws Exception {
		return (PageData)dao.findForObject("PayManageMapper.getRuleFB", a);
	}
	@Override
	public PageData getHzxyName(PageData pd) throws Exception {
		return (PageData)dao.findForObject("PayManageMapper.getHzxyName", pd);
	}
	@Override
	public void updateItemList(PageData pd) throws Exception {
		PageData pd_itemList = (PageData)dao.findForObject("PayManageMapper.getPayItemListByPkid", pd);//一条缴费
		List<PageData> pd_itemBmList = (List<PageData>)dao.findForList("PayManageMapper.getPayItemListByBmPkid", pd);//根据学生副表pkid查询缴费list
		PageData pd_isYj = (PageData)dao.findForObject("PayManageMapper.getPayStyleByPkid", pd_itemList);//查询选择的数据缴费类型是否是预交
		BigDecimal AMOUNTRECEIVABLE = new BigDecimal(pd_itemList.getString("AMOUNTRECEIVABLE"));//应收金额
		BigDecimal AMOUNTCOLLECTION = new BigDecimal(pd_itemList.getString("AMOUNTCOLLECTION"));//实收金额
		int i = AMOUNTRECEIVABLE.compareTo(AMOUNTCOLLECTION);
		if(i!=0){//表示应收大于实收 ，更改名单状态为欠费
			pd_itemList.put("STATUS", PayItemListStatusEnum.欠费.getValue());
			dao.update("PayManageMapper.updateItemListStatus", pd_itemList);
		}else{//更改名单状态为已完成
			pd_itemList.put("STATUS", PayItemListStatusEnum.核验中.getValue());
			dao.update("PayManageMapper.updateItemListStatus", pd_itemList);
		}
		//如果选择的数据是预交类型，判断是否存在其他未关闭的预交项， 如果存在的话将其他的预交项目关闭
		if(pd_isYj!=null&&"预交".equals(pd_isYj.getString("PAY_STYLE_NAME"))){
			PageData pdTypeName = null;
			//循环缴费项目查询缴费项目对应的是否是预交类型
			if(pd_itemBmList!=null&&pd_itemBmList.size()>0){
				for(PageData pdType : pd_itemBmList){
					pdTypeName = (PageData)dao.findForObject("PayManageMapper.getPayStyleByPkid", pdType);
					if(pdTypeName!=null&&"预交".equals(pdTypeName.getString("PAY_STYLE_NAME"))){
						pdType.put("STATUS", PayItemListStatusEnum.已关闭.getValue());
						dao.update("PayManageMapper.updateItemListStatus", pdType);
					}
				}
			}
		}
		
	}
	@Override
	public List<PageData>  getlistbyPkid(String pkid) throws Exception {
		return (List<PageData>) dao.findForList("PayManageMapper.getlistbyPkid", pkid);
	}
	@Override
	public void deletePayItemListbypkid(String pkid) throws Exception {
		dao.save("PayManageMapper.savePayItemListbypkidDel", pkid);
		
		dao.delete("PayManageMapper.deletePayItemListbypkid", pkid);
	}
	@Override
	public void updatePayItemListTZ(PageData pd) throws Exception {
		PageData pageData = (PageData) dao.findForObject("PayManageMapper.getPayItemListInfoTZ", pd);
		BigDecimal AMOUNTRECEIVABLE = new BigDecimal(pageData.getString("AMOUNTCOLLECTION"));//实收金额
		BigDecimal AMOUNTCOLLECTION = new BigDecimal(pd.getString("TZHMONEY"));//应收金额
		BigDecimal teshu = new BigDecimal("0");//应收金额
		int i = AMOUNTRECEIVABLE.compareTo(AMOUNTCOLLECTION);
		int k = teshu.compareTo(AMOUNTCOLLECTION);
		if(i==0){
			if(k==0){
				pd.put("STATUS",  PayItemListStatusEnum.已完成.getValue());
			}else{
				pd.put("STATUS",  PayItemListStatusEnum.核验中.getValue());
			}
			
		}else{
			pd.put("STATUS",  PayItemListStatusEnum.欠费.getValue());
		}
		
		dao.save("PayManageMapper.insertPayItemListCHANGELOG", pd);
		dao.update("PayManageMapper.updatePayItemListTZ", pd);
	}
	@Override
	public void updatePayItemListJM(PageData pd) throws Exception {
		PageData pageData = (PageData) dao.findForObject("PayManageMapper.getPayItemListInfoTZ", pd);
		BigDecimal AMOUNTRECEIVABLE = new BigDecimal(pageData.getString("AMOUNTCOLLECTION"));// 实收金额
		BigDecimal AMOUNTCOLLECTION = new BigDecimal(pd.getString("jmdjTZHMONEY"));//应收金额
		BigDecimal teshu = new BigDecimal("0");
		int k = teshu.compareTo(AMOUNTCOLLECTION);
		int i = AMOUNTRECEIVABLE.compareTo(AMOUNTCOLLECTION);
		if(i==0){
			if(k==0){
				pd.put("STATUS",  PayItemListStatusEnum.已完成.getValue());
			}else{
				pd.put("STATUS",  PayItemListStatusEnum.核验中.getValue());
			}
		}else{
			pd.put("STATUS",  PayItemListStatusEnum.欠费.getValue());
		}
		dao.save("PayManageMapper.insertPayItemListCHANGELOGJM", pd);
		dao.update("PayManageMapper.updatePayItemListJM", pd);
	}
	@Override
	public List<PageData> getaddStutablelistPage(Page page) throws Exception {
		PageData pd = page.getPd();
		PageData pd_item = (PageData)dao.findForObject("PayManageMapper.getItemInfo", pd);
		if("预交".equals(pd_item.getString("PAY_STYLE_NAME"))){
			return (List<PageData>)dao.findForList("PayManageMapper.getaddStutableyjlistPage", page);
		}else{
			return (List<PageData>)dao.findForList("PayManageMapper.getaddStutablelistPage", page);
		}
	}
	@Override
	public void saveAddItemList(PageData pd) throws Exception {
		String[] STUDENT_BM_PKIDs = pd.getString("STUDENT_BM_PKIDs").split(",");//学生附表PKID
		PageData pdRule = null;
		for(String STUDENT_BM_PKID:STUDENT_BM_PKIDs){
			pd.put("STUDENT_BM_PKID", STUDENT_BM_PKID);
			//查询学生对应的缴费规则最优惠的
			pdRule = (PageData) dao.findForObject("PayManageMapper.getRuleCost", pd);
			youHui(pdRule, pd);
		}
		
	}
	@Override
	public PageData getPayItemListByPkid(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("PayManageMapper.getPayItemListByPkid", pd);
	}
	/**
	 * 
	 * <p>描述:</p>
	 * @author wn 王宁
	 * @date 2018年12月18日 下午3:49:19
	 * @param bgCost  优惠前金额
	 * @param bgZJJinE 优惠金额
	 * @param pdRule  查询出来的规则
	 * @param pd 最后要插入的名单PD
	 * @throws Exception
	 */
	private double youHui(PageData pdRule,PageData pd) throws Exception{
		BigDecimal bgCost = null;
		BigDecimal bgZJJinE = new BigDecimal(0);
		if(pdRule!=null){
			bgCost = new BigDecimal(pdRule.getString("COST"));//应收费用
			bgZJJinE = new BigDecimal(pdRule.getString("ZENGJIAN_JINE"));//优惠金额
			//优惠类型 0表示直减 1表示增加
			String zenJianType = "";//优惠类型
			BigDecimal bgResult = new BigDecimal(0);
			zenJianType = pdRule.getString("ZENGJIAN_TYPE");
			if("0".equals(zenJianType)){//0则相减	
				bgResult = bgCost.subtract(bgZJJinE);
				pd.put("DISCOUNT", "优惠"+bgZJJinE);//优惠
				pd.put("DISCOUNT_MODE", 2);//优惠方式
			}else{//1则相加
				bgResult =  bgCost.add(bgZJJinE);
				pd.put("DISCOUNT", "增加"+bgZJJinE);//优惠
				pd.put("DISCOUNT_MODE", 3);//优惠方式
			}
			//优惠金额如果时0则不优惠
			if(bgZJJinE.equals(new BigDecimal(0))){
				pd.put("DISCOUNT", "不优惠");//优惠
				pd.put("DISCOUNT_MODE", 0);//优惠方式
			}
			pd.put("DISCOUNT_MONEY", bgZJJinE);//优惠金额
			pd.put("AMOUNTRECEIVABLE", bgResult);//应收金额
			pd.put("PAY_ITEM_RULE", pdRule.getString("PKID"));//PAY_ITEM_RULE表PKID
			pd.put("COST", pdRule.getString("COST"));//费用
			dao.save("PayManageMapper.saveAddItemList", pd);
		}else{//表示查询不到优惠
			//根据项目PKID获取规则COST
			pdRule  = (PageData)dao.findForObject("PayManageMapper.getOnlyRuleCost", pd);
			if(pdRule!=null){
				//优惠金额如果时0则不优惠
				if(bgZJJinE.equals(new BigDecimal(0))){
					pd.put("DISCOUNT", "不优惠");//优惠
					pd.put("DISCOUNT_MODE", 0);//优惠方式
				}
				pd.put("DISCOUNT_MONEY", bgZJJinE);//优惠金额
				pd.put("AMOUNTRECEIVABLE", pdRule.getString("COST"));//应收金额
				pd.put("PAY_ITEM_RULE", pdRule.getString("PKID"));//PAY_ITEM_RULE表PKID
				pd.put("COST", pdRule.getString("COST"));//费用
				dao.save("PayManageMapper.saveAddItemList", pd);
			}
		}
		return bgZJJinE.doubleValue();
	}
	@Override
	public PageData getLastGrade(PageData pd) throws Exception {
		return (PageData)dao.findForObject("PayManageMapper.getLastGrade", pd);
	}
	@Override
	public double getItemCost(PageData pd) throws Exception {
		PageData pdRule = null;
		pdRule = (PageData) dao.findForObject("PayManageMapper.getRuleCost", pd);
		BigDecimal bgCost = null;
		BigDecimal pdCost = new BigDecimal(0);
		BigDecimal bgZJJinE = new BigDecimal(0);
		if(pdRule!=null){
			bgCost = new BigDecimal(pdRule.getString("COST"));//应收费用
			bgZJJinE = new BigDecimal(pdRule.getString("ZENGJIAN_JINE"));//优惠金额
			//优惠类型 0表示直减 1表示增加
			String zenJianType = "";//优惠类型
			BigDecimal bgResult = new BigDecimal(0);
			zenJianType = pdRule.getString("ZENGJIAN_TYPE");
			if("0".equals(zenJianType)){//0则相减	
				bgResult = bgCost.subtract(bgZJJinE);
				pd.put("DISCOUNT", "优惠"+bgZJJinE);//优惠
				pd.put("DISCOUNT_MODE", 2);//优惠方式
			}else{//1则相加
				bgResult =  bgCost.add(bgZJJinE);
				pd.put("DISCOUNT", "增加"+bgZJJinE);//优惠
				pd.put("DISCOUNT_MODE", 3);//优惠方式
			}
			//优惠金额如果时0则不优惠
			if(bgZJJinE.equals(new BigDecimal(0))){
				pd.put("DISCOUNT", "不优惠");//优惠
				pd.put("DISCOUNT_MODE", 0);//优惠方式
			}
			pd.put("DISCOUNT_MONEY", bgZJJinE);//优惠金额
			pd.put("AMOUNTRECEIVABLE", bgResult);//应收金额
			pd.put("PAY_ITEM_RULE", pdRule.getString("PKID"));//PAY_ITEM_RULE表PKID
			pd.put("COST", pdRule.getString("COST"));//费用
			pdCost = bgResult;
		}else{//表示查询不到优惠
			//根据项目PKID获取规则COST
			pdRule  = (PageData)dao.findForObject("PayManageMapper.getOnlyRuleCost", pd);
			if(pdRule!=null){
				//优惠金额如果时0则不优惠
				if(bgZJJinE.equals(new BigDecimal(0))){
					pd.put("DISCOUNT", "不优惠");//优惠
					pd.put("DISCOUNT_MODE", 0);//优惠方式
				}
				pd.put("DISCOUNT_MONEY", bgZJJinE);//优惠金额
				pd.put("AMOUNTRECEIVABLE", pdRule.getString("COST"));//应收金额
				pd.put("PAY_ITEM_RULE", pdRule.getString("PKID"));//PAY_ITEM_RULE表PKID
				pd.put("COST", pdRule.getString("COST"));//费用
				pdCost = new BigDecimal(pdRule.getString("COST"));
			}
		}
		return pdCost.doubleValue();
	}
	@Override
	public PageData getYjIsBd(PageData pd) throws Exception {
		return (PageData)dao.findForObject("PayManageMapper.getYjIsBd", pd);
	}
	@Override
	public void updateStuTuiXue(PageData pd) throws Exception {
		dao.update("PayManageMapper.updateStuTuiXue", pd);
	}
}
