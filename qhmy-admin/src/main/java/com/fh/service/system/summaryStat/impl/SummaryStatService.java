package com.fh.service.system.summaryStat.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.summaryStat.SummaryStatManager;
import com.fh.util.PageData;
import com.fh.util.Tools;

@Service("SummaryStatService")
@SuppressWarnings("unchecked")
public class SummaryStatService implements SummaryStatManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public List<PageData> getList(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		List<PageData> list = new ArrayList<PageData>();
		List<PageData> allList = new ArrayList<PageData>();
		List<Integer> qcList = new ArrayList<Integer>();// 校区 去重
		List<Integer> ssList = new ArrayList<Integer>();// 宿舍楼 去重

		list = (List<PageData>) dao
				.findForList("SummaryStatMapper.getList", pd);

		double z_maxchuang = 0.0;
		double z_nansheng = 0.0;
		double z_nvsheng = 0.0;
		double y_maxchuang = 0.0;
		double y_nansheng = 0.0;
		double y_nvsheng = 0.0;
		double k_maxchuang = 0.0;
		double k_nansheng = 0.0;
		double k_nvsheng = 0.0;
		double rzl = 0.0;

		if (list.size() > 0)
			for (int i = 0; i < list.size(); i++) {
				String str = list.get(i).getString("XIAOQU");
				String old_str = "";
				if (i != list.size() - 1) {
					old_str = list.get(i + 1).getString("XIAOQU");
				}

				z_maxchuang = Tools.add(z_maxchuang, Double.parseDouble(list
						.get(i).getString("Z_MAXCHUANG")));

				z_nansheng = Tools
						.add(z_nansheng,
								Double.parseDouble(list.get(i).getString(
										"Z_NANSHENG")));
				z_nvsheng = Tools.add(z_nvsheng,
						Double.parseDouble(list.get(i).getString("Z_NVSHENG")));
				y_maxchuang = Tools.add(y_maxchuang, Double.parseDouble(list
						.get(i).getString("Y_MAXCHUANG")));
				y_nansheng = Tools
						.add(y_nansheng,
								Double.parseDouble(list.get(i).getString(
										"Y_NANSHENG")));
				y_nvsheng = Tools.add(y_nvsheng,
						Double.parseDouble(list.get(i).getString("Y_NVSHENG")));
				k_maxchuang = Tools.add(k_maxchuang, Double.parseDouble(list
						.get(i).getString("K_MAXCHUANG")));
				k_nansheng = Tools
						.add(k_nansheng,
								Double.parseDouble(list.get(i).getString(
										"K_NANSHENG")));
				k_nvsheng = Tools.add(k_nvsheng,
						Double.parseDouble(list.get(i).getString("K_NVSHENG")));

				list.get(i).put("RZL", list.get(i).getString("RZL") + "%");
				allList.add(list.get(i));
				if (!str.equals(old_str)) {
					PageData row = new PageData();

					row.put("hj_style", "bg-warning");
					row.put("XIAOQU", "");
					row.put("LOU", "合计");
					row.put("CENG", "");
					row.put("Z_MAXCHUANG", (int) z_maxchuang);
					row.put("Z_NANSHENG", (int) z_nansheng);
					row.put("Z_NVSHENG", (int) z_nvsheng);
					row.put("Y_MAXCHUANG", (int) y_maxchuang);
					row.put("Y_NANSHENG", (int) y_nansheng);
					row.put("Y_NVSHENG", (int) y_nvsheng);
					row.put("K_MAXCHUANG", (int) k_maxchuang);
					row.put("K_NANSHENG", (int) k_nansheng);
					row.put("K_NVSHENG", (int) k_nvsheng);
					rzl = y_maxchuang / z_maxchuang * 100;// 合计入住人数统计:已住人数/总人数
					BigDecimal bg = new BigDecimal(rzl).setScale(2,
							BigDecimal.ROUND_HALF_UP);
					rzl = bg.doubleValue();

					row.put("RZL", rzl == 0.0 ? 0 + "%" : rzl + "%");

					z_maxchuang = 0.0;
					z_nansheng = 0.0;
					z_nvsheng = 0.0;
					y_maxchuang = 0.0;
					y_nansheng = 0.0;
					y_nvsheng = 0.0;
					k_maxchuang = 0.0;
					k_nansheng = 0.0;
					k_nvsheng = 0.0;
					rzl = 0.0;
					allList.add(row);
				}
			}

		for (int i = 0; i < allList.size(); i++) {
			String str = allList.get(i).getString("XIAOQU");
			String strs = "";
			String ss = allList.get(i).getString("LOU");
			String ssu = "";
			if (i > 0) {
				strs = allList.get(i - 1).getString("XIAOQU");
				ssu = allList.get(i - 1).getString("LOU");
			}
			if (str.equals(strs)) {
				qcList.add(i);
			}
			if (ss.equals(ssu)) {
				ssList.add(i);
			}
		}

		for (Integer in : qcList) {
			allList.get(in).put("XIAOQU", "");
		}
		for (Integer in : ssList) {
			allList.get(in).put("LOU", "");
		}
		return allList;
	}

	@Override
	public List<PageData> getLou(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("SummaryStatMapper.getLou", pd);
	}

	@Override
	public List<PageData> getRefundlist(Page page) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList(
				"SummaryStatMapper.getRefundlistPage", page);
	}

	@Override
	public List<PageData> getpayitem(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("SummaryStatMapper.getpayitem",
				pd);
	}

	@Override
	public List<PageData> getrxnf(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao
				.findForList("SummaryStatMapper.getrxnf", pd);
	}

	@Override
	public List<PageData> getbanji_type(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList(
				"SummaryStatMapper.getbanji_type", pd);
	}

	@Override
	public List<PageData> getcengci(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("SummaryStatMapper.getcengci",
				pd);
	}

	@Override
	public List<PageData> getRefund(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("SummaryStatMapper.getRefund",
				pd);

	}

	@Override
	public List<PageData> getDormTree(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList(
				"SummaryStatMapper.getDormTree", pd);
	}
	@Override
	public PageData getRefundDetailSum(PageData pd) throws Exception {
		return (PageData) dao.findForObject("SummaryStatMapper.getRefundDetailSum", pd);
	}

}
