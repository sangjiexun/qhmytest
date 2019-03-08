package com.fh.service.system.report.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.report.ReportManager;
import com.fh.util.PageData;
import com.keman.zhgd.common.tree.VO;
/**
 * 报表统计实现类
 * <p>标题:ReportService</p>
 * <p>描述:</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 陈超超
 * @date 2017年10月31日 上午11:14:42
 */
@Service("reportService")
public class ReportService implements ReportManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public List<PageData> getZSRSumTablelistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getZSRSumTablelistPage", page);
	}

	@Override
	public List<PageData> getZuzhis(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getZuzhis", pd);
	}

	@Override
	public List<PageData> getZSRSumDetailTablelistPage(Page page)
			throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getZSRSumDetailTablelistPage", page);
	}

	@Override
	public List<PageData> zsrSumTablelist(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.zsrSumTablelist", pd);
	}
	
	@Override
	public List<PageData> zsrSumDetailTablelist(PageData pd)
			throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.zsrSumDetailTablelist", pd);
	}

	@Override
	public List<PageData> getRefundSumlistTable(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getRefundSumlist", pd);
	}

	@Override
	public List<PageData> getRefundDetailTablelistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getRefundDetailTablelistPage", page);
	}

	@Override
	public List<PageData> getPICIList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getPICIList", pd);
	}

	@Override
	public List<PageData> getCENGCIList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getCENGCIList", pd);
	}
	
	@Override
	public List<PageData> getRefundDetailTableList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getRefundDetailTableList", pd);
	}

	@Override
	public List<PageData> getTradeSumlistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getTradeSumlistPage", page);
	}

	@Override
	public List<PageData> getTradeSumList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getTradeSumList", pd);
	}

	@Override
	public List<PageData> getPayStyleList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getPayStyleList", pd);
	}

	@Override
	public List<PageData> getSchoolYeaList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getSchoolYeaList", pd);
	}

	@Override
	public PageData getPayStyleRoom(PageData pd) throws Exception {
		return (PageData)dao.findForObject("reportMapper.getPayStyleRoom", pd);
	}

	@Override
	public List<PageData> getstuArrearageSumlistPage(Page page)
			throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getstuArrearageSumlistPage", page);
	}

	@Override
	public List<PageData> stuArrearageSumExcel(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.stuArrearageSumExcel", pd);
	}

	@Override
	public List<PageData> getBanJiArrearageSumlistPage(Page page)
			throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getBanJiArrearageSumlistPage", page);
	}

	@Override
	public PageData getSumRowResult(PageData pd) throws Exception {
		return (PageData) dao.findForObject("reportMapper.getSumRowResult", pd);
	}

	@Override
	public List<PageData> getstuArrearageDetaillistPage(Page page)
			throws Exception {
		return (List<PageData>) dao.findForList("reportMapper.getstuArrearageDetaillistPage", page);
	}
	
	@Override
	public List<PageData> getstuArrearageDetaillist(Page page)
			throws Exception {
		return (List<PageData>) dao.findForList("reportMapper.getstuArrearageDetaillist", page);
	}

	@Override
	public List<PageData> getPayItemList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("reportMapper.getPayItemList", pd);
	}

	@Override
	public List<PageData> stuArrearageDetailExcel(Page page) throws Exception {
		return (List<PageData>) dao.findForList("reportMapper.getstuArrearageDetailExport", page);
	}

	@Override
	public List<PageData> getPayStyleListAll(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getPayStyleListAll", pd);
	}

	@Override
	public List<PageData> getSchoolYeaListAll(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getSchoolYeaListAll", pd);
	}

	@Override
	public List<PageData> getstuPayDetaillistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getstuPayDetaillistPage", page);
	}

	@Override
	public List<PageData> stuPayDetailExcel(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.stuPayDetailExcel", pd);
	}
	@Override
	public PageData getstuArrearageSum(PageData pd)throws Exception{
		return (PageData) dao.findForObject("reportMapper.getstuArrearageSum", pd);
	}

	@Override
	public PageData getRefundDetailSum(PageData pd) throws Exception {
		return (PageData) dao.findForObject("reportMapper.getRefundDetailSum", pd);
	}
	
	@Override
	public PageData getinvoice(PageData pd) throws Exception {
		return (PageData) dao.findForObject("reportMapper.getinvoice", pd);
	}

	@Override
	public PageData getstuArrearageDetail(PageData pd) throws Exception {
		return (PageData) dao.findForObject("reportMapper.getstuArrearageDetail", pd);
	}

	@Override
	public List<PageData> getBanJiArrearageSumlist(PageData pd)
			throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getBanJiArrearageSumlist", pd);
	}
	
	@Override
	public List<PageData> getinvoicelistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getinvoicelistPage", page);
	}



	@Override
	public List<PageData> getgoodsSumDetaillistPage(Page page) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("reportMapper.getgoodsSumDetaillistPage", page);
	}
	
	@Override
	public List<PageData> printgetgoodsSumDetaillist(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("reportMapper.printgetgoodsSumDetaillist", pd);
	}

	@Override
	public List<PageData> getXueNian(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("reportMapper.getXueNian", pd);
	}

	@Override
	public List<PageData> getJiaoFeiLeiXing(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("reportMapper.getJiaoFeiLeiXing", pd);
	}
	
	@Override
	public List<PageData> getstudepartment(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getstudepartment", pd);
	}
	
	@Override
	public List<PageData> getlist(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getlist", pd);
	}
	
	
	@Override
	public List<PageData> getalllou(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getalllou", pd);
	}
	
	@Override
	public List<PageData> getRuXueNianFen(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("reportMapper.getRuXueNianFen", pd);
		}

	@Override
	public List<PageData> getShengYuanSum(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("reportMapper.getShengYuanSum", pd);
		}
	@Override
	public List<PageData> getCardDetaillistPage(Page page) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("reportMapper.getCardDetaillistPage", page);
	}
	@Override
	public List<PageData> getZhuanYeList(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("reportMapper.getZhuanYeList", pd);
		}

	@Override
	public List<PageData> getCardDetaillist(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("reportMapper.getCardDetaillist", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getAdvancePayTable(Page page) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getAdvancePaylistPage", page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getAdvancePayList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("reportMapper.getAdvancePaylist", pd);
	}

	@Override
	public PageData getAdvancePaySum(PageData pd) throws Exception {
		return (PageData) dao.findForObject("reportMapper.getAdvancePaySum", pd);
	}
}
