package com.fh.service.system.basicclass;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

public interface BasicClassManager {
	
	/**根据字典
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getFromSYS_DICT(PageData pd)throws Exception;
	
	/**查询班级数据
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> basicclass_listPage(Page page)throws Exception;
	
	/**新增班级
	 * @param pd
	 * @throws Exception
	 */
	public void saveBasicClass(PageData pd)throws Exception;
	
	/**根据pkid查看班级详情
	 * @param pd
	 * @throws Exception
	 */
	public PageData getClassByPkid(PageData pd)throws Exception;
	/**根据班级pkid查看班级是否已经被使用
	 * @param pd
	 * @throws Exception
	 */
	public PageData getClassUse(PageData pd)throws Exception;
	
	/**根据pkid删除一条班级信息
	 * @param pd
	 * @throws Exception
	 */
	public void deleteClassByPkid(PageData pd)throws Exception;
	
	/**根据pkid修改一条班级信息
	 * @param pd
	 * @throws Exception
	 */
	public void updateClassByPkid(PageData pd)throws Exception;
	
	/**查询班级数据
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> classtype_listPage(Page page)throws Exception;
	
	/**新增班级
	 * @param pd
	 * @throws Exception
	 */
	public void saveclasstype(PageData pd)throws Exception;
	
	/**根据pkid查看班级详情
	 * @param pd
	 * @throws Exception
	 */
	public PageData getclasstypeByPkid(PageData pd)throws Exception;
	
	/**根据pkid删除一条班级信息
	 * @param pd
	 * @throws Exception
	 */
	public void deleteclasstypeByPkid(PageData pd)throws Exception;
	
	/**根据pkid修改一条班级信息
	 * @param pd
	 * @throws Exception
	 */
	public void updateclasstypeByPkid(PageData pd)throws Exception;
	
	/**根据pkid修改一条班级信息
	 * @param pd
	 * @throws Exception
	 */
	public void updateclasstypeIS_USED(PageData pd)throws Exception;
	
	/**根据pkid修改一条班级信息
	 * @param pd
	 * @throws Exception
	 */
	public void updateclasstypeSFQY(PageData pd)throws Exception;
	
	/**根据pkid查看班级详情
	 * @param pd
	 * @throws Exception
	 */
	public PageData getclasstypeBIANMA(PageData pd)throws Exception;
	
	public PageData getclasstypeName(PageData pd)throws Exception;
	
	public PageData getclasstypeBIANMAs(PageData pd)throws Exception;
	
	public PageData getclasstypeNames(PageData pd)throws Exception;
	

	public PageData getclasstypePkid(PageData pd)throws Exception;
	
	/** 根据班型入学年份和班级名称查询班级
	 * @param pd
	 * @throws Exception
	 */
	public PageData getClassByGradeBxNm(PageData pd)throws Exception;
	
	/**
	 * 查询班级个数
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年11月27日 下午3:07:05
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getClassCount(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:变更启用禁用状态</p>
	 * @author Administrator 王宁
	 * @date 2018年3月9日 下午5:07:58
	 * @param pd
	 * @throws Exception
	 */
	void updateIsUse(PageData pd)throws Exception;

}
