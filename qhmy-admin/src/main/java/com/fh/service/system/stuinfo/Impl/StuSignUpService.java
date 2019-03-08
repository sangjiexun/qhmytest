package com.fh.service.system.stuinfo.Impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.stuinfo.StuSignSupManager;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.keman.zhgd.common.util.UuidUtil;

@Service("/StuSignUpServices")
public class StuSignUpService implements StuSignSupManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public PageData sava(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		String pkid = pd.getString("PKID");
		String uuid = UuidUtil.get32UUID();
		if (!"".equals(pkid) && !"null".equals(pkid)) {// PKID存在 执行更新
			dao.update("StuSignUpMapper.updateStuInFo", pd);
			pd.put("STUDENT_PKID", pkid);
		} else {// pkid为空 说明无此学生基本数据
			String passwd = new SimpleHash("SHA-1", "", "123456").toString();
			pd.put("PKID", uuid);
			pd.put("PWD", passwd);
			dao.save("StuSignUpMapper.sava", pd);
			pd.put("STUDENT_PKID", uuid);
		}
		pd.put("BMPKID", UuidUtil.get32UUID());

		dao.save("StuSignUpMapper.saveBm", pd);

		return pd;
	}

	@Override
	public List<PageData> getJHRGX(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("StuSignUpMapper.getJHRGX", pd);
	}

	@Override
	public List<PageData> getLJQHTJ(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao
				.findForList("StuSignUpMapper.getLJQHTJ", pd);
	}

	@Override
	public List<PageData> getWhkSchool(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("StuSignUpMapper.getWhkSchool",
				pd);
	}

	@Override
	public List<PageData> getCengCi(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao
				.findForList("StuSignUpMapper.getCengCi", pd);

	}

	@Override
	public List<PageData> getRXNF(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("StuSignUpMapper.getRXNF", pd);

	}

	@Override
	public List<PageData> getBanJiType(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("StuSignUpMapper.getBanJiType",
				pd);

	}

	@Override
	public List<PageData> getYuBanJiType(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList(
				"StuSignUpMapper.getYuBanJiType", pd);
	}

	@Override
	public List<PageData> getXKCJ(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("StuSignUpMapper.getXKCJ", pd);
	}

	@Override
	public PageData getStuInfoBySfz(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject("StuSignUpMapper.getStuInfoBySfz",
				pd);
	}

	@Override
	public void updateStuInFo(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.update("StuSignUpMapper.updateStuInFo", pd);
	}

	@Override
	public List<PageData> getBanJi(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("StuSignUpMapper.getBanJi", pd);
	}

	@Override
	public PageData getXKCJs(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject("StuSignUpMapper.getXKCJs", pd);
	}

	@Override
	public List<PageData> stuinfo_list(Page page) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("StuSignUpMapper.stulistPage",
				page);
	}

	@Override
	public List<PageData> stuExcel(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("StuSignUpMapper.stuExcel", pd);
	}

	@Override
	public void updateStuBanJi(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.update("StuSignUpMapper.updateStuBanJi", pd);
	}

	@Override
	public boolean yangZheng(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		PageData spd = (PageData) dao.findForObject(
				"StuSignUpMapper.yanZhengStuInfo", pd);
		int count = Integer.parseInt(spd.getString("STUCOUNT"));
		if (count == 0)
			return true;

		return false;
	}

	public static double sumPrice(PageData pr, double price) throws Exception {
		if (Integer.parseInt(pr.getString("COST_TYPE")) == 0) {// 减少金额
			price = Tools.sub(price, Double.parseDouble(pr.getString("COST")));
		}
		if (Integer.parseInt(pr.getString("COST_TYPE")) == 1) {// 增加金额
			price = Tools.add(price, Double.parseDouble(pr.getString("COST")));
		}
		return price;
	}

	public static double sumPriceDiscount(PageData pr, double DISCOUNT_MONEY)
			throws Exception {
		if (Integer.parseInt(pr.getString("COST_TYPE")) == 1) {// 减少金额
			DISCOUNT_MONEY = Tools.sub(DISCOUNT_MONEY,
					Double.parseDouble(pr.getString("COST")));
		}
		if (Integer.parseInt(pr.getString("COST_TYPE")) == 0) {// 增加金额
			DISCOUNT_MONEY = Tools.add(DISCOUNT_MONEY,
					Double.parseDouble(pr.getString("COST")));
		}
		return DISCOUNT_MONEY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> loadPayMent(PageData pd) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		List<PageData> paylist = null;

		// System.out.println(">" + pd);
		PageData trem = new PageData();
		// ------------------- 第一步 根据stat判断 用预交年份班型 还是 正常年份班型
		String payStat = pd.getString("payStat");
		String stuPkid = pd.getString("STUPKID");
		if (!"null".equals(payStat) && !"".equals(payStat)) {
			String RXNIANFEN = "", BANJI_TYPE = "";
			RXNIANFEN = pd.getString("RXNIANFEN");
			BANJI_TYPE = pd.getString("BANJI_TYPE");

			trem.put("RXNIANFEN", RXNIANFEN);
			trem.put("BANJI_TYPE", BANJI_TYPE);
			trem.put("stat", "ZC");

			// ------------------- 第二步 根据 年份班型 获取缴费项目 PKID\名称\金额
			paylist = (List<PageData>) dao.findForList(
					"StuSignUpMapper.getItem", trem);
			// ------------------- 第二步 根据预交年份预交班型 显示预交项目
			if ("YJ".equals(payStat)) {
				PageData yj = new PageData();
				yj.put("RXNIANFEN", pd.getString("YJ_NIANFEN"));
				yj.put("BANJI_TYPE", pd.getString("YJ_BANJI_TYPE"));
				yj.put("stat", pd.getString("payStat"));
				PageData pdRestult = (PageData) dao.findForObject(
						"StuSignUpMapper.getItem", yj);
				if (pdRestult != null) {
					paylist.add(pdRestult);
				}
			}
			// ------------------- 第三步 根据 查询到的缴费项目PKID查询减免规则

			if (paylist.size() > 0)
				for (PageData p : paylist) {// 循环所有缴费项目
					if (p == null) {
						continue;
					}
					if ("学费".equals(p.getString("PAYNAME"))
							&& !"null".equals(stuPkid) && !"".equals(stuPkid)) {
						PageData yjInfo = new PageData();
						yjInfo.put("RXNIANFEN", RXNIANFEN);
						yjInfo.put("BANJI_TYPE", BANJI_TYPE);
						yjInfo.put("STUPKID", stuPkid);
						yjInfo = (PageData) dao.findForObject(
								"StuSignUpMapper.getYuJiaoPay", yjInfo);

						if (yjInfo != null) {
							double pr = Double.parseDouble(yjInfo
									.getString("COST"));
							double pi = Double.parseDouble(p
									.getString("PAYPRICE"));

							p.put("PAYPRICE", Tools.sub(pi, pr));
						}
					}

					double price = Double.parseDouble(p.getString("PAYPRICE"));// 获取此项目应收金额
					double prices = 0.0;// 获取最大的优惠金额
					List<PageData> rulelist = null;// 根据项目主键去规则表中匹配对应的优惠类型
					rulelist = (List<PageData>) dao.findForList(
							"StuSignUpMapper.getRuleByItemId", p);

					for (PageData r : rulelist) {
						PageData gz = new PageData();
						gz.put("IPKID", p.getString("IPKID"));
						gz.put("YOUHUI_TYPE", r.getString("YHNUM"));

						switch (Integer
								.parseInt(r.getString("YHNUM") == "null" ? "00"
										: r.getString("YHNUM"))) {
						case 1:// 合作学校
							String HZXY = pd.getString("WHKXUEXIAO").trim();
							if (HZXY != null && HZXY.length() > 0) {
								PageData hzxy = null;
								gz.put("HZXY", HZXY);
								hzxy = (PageData) dao.findForObject(
										"StuSignUpMapper.youHuiYanZheng", gz);

								if (hzxy != null) {
									double cost = Double.parseDouble(hzxy
											.getString("COST"));

									if (prices == 0.0) {
										prices = cost;
									}
									if (cost > prices) {
										prices = cost;
									}

									p.put("RPKID", hzxy
											.getString("T_PAY_ITEM_RULE_PKID"));
								} else {
									res.put("msg", "hzxy is null");
								}
							}

							break;
						case 2:// 报名时间
							SimpleDateFormat df = new SimpleDateFormat(
									"yyyy-MM-dd");
							String date = df.format(new Date());
							PageData bmsj = null;
							gz.put("BMSHIJIAN", date);
							bmsj = (PageData) dao.findForObject(
									"StuSignUpMapper.youHuiYanZheng", gz);
							if (bmsj != null) {
								double cost = Double.parseDouble(bmsj
										.getString("COST"));
								if (prices == 0.0) {
									prices = cost;
								}
								if (cost > prices) {
									prices = cost;
								}
								p.put("RPKID",
										bmsj.getString("T_PAY_ITEM_RULE_PKID"));
							} else {
								res.put("msg", "banji is null");
							}
							break;
						case 3:// 预交

							if ("YJ".equals(payStat)) {
								PageData yujiao = null;
								yujiao = (PageData) dao.findForObject(
										"StuSignUpMapper.youHuiYanZheng", gz);
								if (yujiao != null) {
									double cost = Double.parseDouble(yujiao
											.getString("COST"));
									if (prices == 0.0) {
										prices = cost;
									}
									if (cost > prices) {
										prices = cost;
									}

									p.put("RPKID", yujiao
											.getString("T_PAY_ITEM_RULE_PKID"));
								} else {
									res.put("msg", "yj is null");
								}
							}
							break;
						case 4:// 联考成绩
							if ("yjs".equals(pd.getString("CENGCIHTML"))) {
								break;
							}
							String CENGCI = pd.getString("CENGCIHTML");
							String LKFS = pd.getString("LKFS");
							gz.put("CENGCI", CENGCI);
							gz.put("LKFS", LKFS);

							if (!"null".equals(CENGCI) && !"".equals(CENGCI)&& !"null".equals(LKFS) && !"".equals(LKFS)) {

								PageData lkcj = null;
								lkcj = (PageData) dao.findForObject(
										"StuSignUpMapper.youHuiYanZheng", gz);
								if (lkcj != null) {
									double cost = Double.parseDouble(lkcj
											.getString("COST"));
									if (prices == 0.0) {
										prices = cost;
									}
									if (cost > prices) {
										prices = cost;
									}

									p.put("RPKID", lkcj
											.getString("T_PAY_ITEM_RULE_PKID"));
								}
							} else {
								res.put("msg", "lkcj or cengci is null");
							}
							break;
						case 5:// 班级
							if (!"bxfks".equals(pd.getString("CENGCIHTML"))) {
								break;
							}
							String BANJIPKID = pd.getString("OLD_BANJI");

							if (!"null".equals(BANJIPKID)&& !"".equals(BANJIPKID)) {

								PageData banji = null;
								gz.put("BANJIPKID", BANJIPKID);
								banji = (PageData) dao.findForObject(
										"StuSignUpMapper.youHuiYanZheng", gz);
								if (banji != null) {
									double cost = Double.parseDouble(banji
											.getString("COST"));
									if (prices == 0.0) {
										prices = cost;
									}
									if (cost > prices) {
										prices = cost;
									}
									p.put("RPKID", banji
											.getString("T_PAY_ITEM_RULE_PKID"));
								}

							}

							break;
						case 6:// 美院
							if ("yjs".equals(pd.getString("CENGCIHTML"))) {
								break;
							}
							String XK_MARK = pd.getString("XK_MARK").trim();
							if (XK_MARK != null && XK_MARK.length() > 0) {
								List<PageData> meiyuanList = new ArrayList<PageData>();
								for (String s : XK_MARK.split(",")) {// 获取符合条件的美院
									PageData youhui = null;
									gz.put("XK_MARK", s);
									// 根据:项目pkid\优惠类型,获取优惠规则

									youhui = (PageData) dao.findForObject(
											"StuSignUpMapper.youHuiYanZheng",
											gz);
									if (youhui != null)
										meiyuanList.add(youhui);
								}
								if (meiyuanList.size() > 0)
									for (int i = 0; i < meiyuanList.size() - 1; i++) {// 将匹配的优惠规则去重

										for (int j = meiyuanList.size() - 1; j > i; j--) {
											if (meiyuanList.get(j).getString("REPPKID").equals(meiyuanList.get(i).getString("REPPKID"))) {
												meiyuanList.remove(j);
											}
										}

									}

								for (PageData m : meiyuanList) {// 进行优惠
									if (m != null) {
										double cost = Double.parseDouble(m
												.getString("COST"));
										if (prices == 0.0) {
											prices = cost;
										}
										if (cost > prices) {
											prices = cost;
										}

										p.put("RPKID",
												m.getString("T_PAY_ITEM_RULE_PKID"));
									}
								}

							}

							break;
						default:
							res.put("msg", "没找到对应优惠类型");// 不优惠
							break;

						}

					}
					price = Tools.sub(price, prices);

					BigDecimal bg = new BigDecimal(price).setScale(2,BigDecimal.ROUND_HALF_UP);

					price = bg.doubleValue();

					p.put("DISCOUNT_MONEY", prices);
					p.put("PAYCOST", price);// 优惠金额
				}

			// 合计
			if (paylist.size() > 0) {
				double sum = 0.0;
				for (PageData p : paylist) {
					if (p == null) {
						continue;
					}
					sum = Tools.add(sum,
							Double.parseDouble(p.getString("PAYCOST")));
				}
				PageData heji = new PageData();
				heji.put("PAYNAME", "合计");
				heji.put("PAYCOST", sum);
				heji.put("IPKID", "xxx");
				paylist.add(heji);

				res.put("paydata", paylist);
				res.put("msg", "success");

			} else {
				res.put("msg", "error_null");
			}

		} else {// if(!"null".equals(stat)&&!"".equals(stat))
			res.put("msg", "the stat is null");
		}
		return res; // TODO Auto-generated method stub
	}

	@Override
	public Map<String, Object> payMentSaveAndUpdate(PageData pd)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String STUDENT_PKID = pd.getString("STUDENT_PKID");// 获取学生主表PKID
		String BMPKID = pd.getString("BMPKID");// 获取学生副表PKID
		String[] IPKID = pd.getString("IPKID").split(",");// 页面项目PKID
		String[] AMOUNTRECEIVABLE = pd.getString("PAYPRICE").split(",");// 项目减免后金额
		String[] DISCOUNT_MONEY = pd.getString("DISCOUNT_MONEY").split(",");// 项目减免金额
		String[] RPKID = pd.getString("RPKID").split(",");// 优惠规则表PKID
		String[] PAYCOST = pd.getString("PAYCOST").split(",");// 项目原金额
		String[] PAYNAME = pd.getString("PAYNAME").split(",");
		if (IPKID.length < 0) {
			map.put("pay_msg", "pay is null success");
			return map;
		}

		PageData stubm = (PageData) dao.findForObject("StuSignUpMapper.isyjbm",
				pd);
		if (stubm != null) {
			if (stubm.getString("ISSTUBM").equals("Y")) {// 主表绑定预交副本的字段为空,直接添加
				dao.update("StuSignUpMapper.update_yjbm", pd);
			}
		}

		PageData yujiao = null;
		PageData xtpd = null;
		yujiao = (PageData) dao.findForObject("StuSignUpMapper.isyj_ren", pd);
		// 循环缴费项目
		for (int i = 0; i < IPKID.length; i++) {
			PageData pay = new PageData();
			String ITEMPKID = IPKID[i];
			pay.put("PKID", UuidUtil.get32UUID());
			pay.put("STUDENT_PKID", STUDENT_PKID);
			pay.put("PRICE", AMOUNTRECEIVABLE[i]);
			pay.put("DISCOUNT_MONEY", DISCOUNT_MONEY[i]);
			pay.put("PAY_ITEM_RULE", RPKID[i]);
			pay.put("BMPKID", BMPKID);
			pay.put("PAYCOST", PAYCOST[i]);
			pay.put("T_PAY_ITEM_PKID", ITEMPKID);
			pay.put("SHENFENZHENGHAO", pd.getString("SHENFENZHENGHAO"));

			if ("update".equals(pd.getString("sign"))) {// 更新方法
				// 根据BMPKID+ITEMPKID查询是否有匹配预交项目
				PageData up = (PageData) dao.findForObject(
						"StuSignUpMapper.getItemListByPay", pay);
				if (up != null) {// 查询到关联缴费名单 执行update
					pay.put("LISTPKID", up.getString("PKID"));
					dao.update("StuSignUpMapper.updatePay", pay);
				}

				map.put("pay_msg", "success");
				return map;
			}
			if ("预交".equals(PAYNAME[i])) {
				if ("Y".equals(yujiao.getString("ISYJ"))) {// 判断是否有预交项, 不区分缴费状态

					xtpd = (PageData) dao.findForObject(
							"StuSignUpMapper.isyj_xiangtong", pay);// 查看是否有相同
																	// 入学年份 班型
																	// 的缴费项,并且是关闭状态
					if (xtpd != null) {
						System.out.println(xtpd);
						dao.update("StuSignUpMapper.update_payitemlist", xtpd);
					}

					continue;
				}
				dao.update("StuSignUpMapper.update_yjbm", pd);

			}

			if (!"null".equals(ITEMPKID) && !"".equals(ITEMPKID)) {
				dao.save("StuSignUpMapper.savaPay", pay);
			}

		}

		map.put("pay_msg", "success");
		return map;
	}

	@Override
	public List<PageData> getBanJis(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao
				.findForList("StuSignUpMapper.getBanJis", pd);
	}

	@Override
	public List<PageData> getBanjiByNx(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("StuSignUpMapper.getBanjiBygn",
				pd);
	}

	@Override
	public PageData getYuJiaoren(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject("StuSignUpMapper.getYuJiaoren", pd);
	}

	@Override
	public List<PageData> getKemu(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("StuSignUpMapper.getKemu", pd);
	}

	@Override
	public List<PageData> getlastClassList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"StuSignUpMapper.getlastClassList", pd);
	}

}
