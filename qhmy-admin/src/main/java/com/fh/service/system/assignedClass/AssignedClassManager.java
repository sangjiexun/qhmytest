package com.fh.service.system.assignedClass;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 安全教育记录接口
 * 创建人：zhoudibo
 * 创建时间：2016-06-25
 * @version
 */
public interface AssignedClassManager{
	/**
	 * 
	 * <p>描述:获取分班表格数据</p>
	 * @author Administrator 王宁
	 * @date 2018年3月29日 上午8:59:48
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> assignedClasslistPage(Page page) throws Exception;
	/**
	 * 
	 * <p>描述:根据院校专业和入学年份获取班级</p>
	 * @author Administrator 王宁
	 * @date 2018年3月29日 上午8:59:25
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getBanJi(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:导出分班信息</p>
	 * @author Administrator 王宁
	 * @date 2018年3月29日 上午8:59:13
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> exportStuInfoToExcel(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:更新学生班级信息</p>
	 * @author Administrator 王宁
	 * @date 2018年3月29日 上午11:22:15
	 * @param pd
	 * @throws Exception
	 */
	public void updateStuClass(PageData pd)throws Exception;
	
	/**
	 * 获得所有学生
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年7月12日 上午11:22:50
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getassignedClasslist(PageData pd) throws Exception;
	
	/**
	 * 批量分学号
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年7月12日 上午11:46:00
	 * @throws Exception
	 */
	public void updateXueHaoBatch(PageData pd) throws Exception;
	
	/**
	 * 获得学号序列
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年7月13日 上午10:51:09
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String getSeqXuehaoNext(PageData pd)throws Exception;
	
	/**
	 * 获得所有入学年份
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年7月12日 上午11:22:50
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void createSeq(PageData pd) throws Exception;
	
	
	/**
	 * 
	 * <p>描述:获取分班表格数据</p>
	 * @author Administrator 王宁
	 * @date 2018年3月29日 上午8:59:48
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> diviXuehaolistPage(Page page) throws Exception;
	
	/**
	 * 
	 * <p>描述:导出分班信息</p>
	 * @author Administrator 王宁
	 * @date 2018年3月29日 上午8:59:13
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> exportDiviXuehaoExcel(PageData pd)throws Exception;
	
	/**
	 * 获得班级
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年11月28日 下午1:59:11
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getClass(PageData pd)throws Exception;
	
	
	/**
	 * 获得所有学生
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年7月12日 上午11:22:50
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getdiviXuehaolist(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:获取校考成绩</p>
	 * @author wn 王宁
	 * @date 2018年12月19日 下午5:42:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getXKCJ(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:获取入学年份</p>
	 * @author wn 王宁
	 * @date 2018年12月19日 下午5:43:30
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getRXNF(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取班型列表</p>
	 * @author wn 王宁
	 * @date 2018年12月19日 下午5:44:16
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getBanJiType(PageData pd)throws Exception;
	
}

