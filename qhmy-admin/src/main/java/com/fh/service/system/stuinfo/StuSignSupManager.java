package com.fh.service.system.stuinfo;

import java.util.List;
import java.util.Map;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 
 * <p>
 * 标题:StuSignSupManager
 * </p>
 * <p>
 * 描述:学生信息-报名
 * </p>
 * <p>
 * 组织:河北科曼信息技术有限公司
 * </p>
 * 
 * @author Administrator 任笑达
 * @date 2018年11月28日 上午9:38:38
 */
public interface StuSignSupManager {
	/**
	 * 
	 * <p>
	 * 描述:增加学生
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月28日 上午9:38:29
	 * @param pd
	 * @throws Exception
	 */
	PageData sava(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:获取家庭关系
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月28日 上午9:40:55
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getJHRGX(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:了解强化途径
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月28日 上午10:00:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getLJQHTJ(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:获取文化课学校 基础配置-合作院校
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月28日 上午10:04:56
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getWhkSchool(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:获取校考成绩院校 基础配置-美院
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月28日 上午10:09:38
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getXKCJ(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:层次
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月28日 上午10:04:08
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getCengCi(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:获取入学年份
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月28日 上午10:05:46
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getRXNF(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:班型
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月28日 上午10:07:25
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getBanJiType(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:班级
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月3日 上午9:06:28
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getBanJi(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:预交班型
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月28日 上午10:07:25
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getYuBanJiType(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:根据验证通过的身份证查询信息
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月28日 下午1:57:15
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getStuInfoBySfz(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:如果通过身份证查询到学生信息的话,将执行更新操作
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月28日 下午2:01:01
	 * @param pd
	 * @throws Exception
	 */
	void updateStuInFo(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:逐个获取校考成绩院校
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月3日 上午10:00:17
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getXKCJs(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:获得班级
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月10日 上午10:17:43
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getBanJis(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:显示分班页面
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月3日 下午2:22:26
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> stuinfo_list(Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:分班导出
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月3日 下午2:25:13
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> stuExcel(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:根据入学年份 班型关联出班级
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月17日 下午5:20:53
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getBanjiByNx(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:分班
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月3日 下午2:26:04
	 * @param pd
	 * @throws Exception
	 */
	void updateStuBanJi(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:验证学生唯一性 身份证+入学年份+班型 组成唯一的一条数据
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月3日 下午4:25:59
	 * @param SHENFENZHENGHAO
	 *            身份证号
	 * @param RXNIANFEN_PKID
	 *            入学年份pkid
	 * @param BANJI_TYPE_PKID
	 *            班型pkid
	 * @return
	 * @throws Exception
	 */
	boolean yangZheng(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:显示方法
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月6日 上午8:42:21
	 * @param pd
	 * @return
	 * @throws Exception
	 */

	Map<String, Object> loadPayMent(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:生成预缴费名单 增加/修改方法
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月5日 下午2:42:06
	 * @param pd
	 * @param sign
	 *            :update/add 修改/增加
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> payMentSaveAndUpdate(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:获取预交年份 班型 通过身份证号
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月17日 下午6:23:24
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getYuJiaoren(PageData pd) throws Exception;
	
	/**
	 * 获得科目
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年12月18日 下午2:30:50
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getKemu(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:获取上一年的班级下拉列表</p>
	 * @author wn 王宁
	 * @date 2018年12月19日 下午4:21:19
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getlastClassList(PageData pd)throws Exception;

}
