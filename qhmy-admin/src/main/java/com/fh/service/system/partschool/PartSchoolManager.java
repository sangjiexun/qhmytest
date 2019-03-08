package com.fh.service.system.partschool;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

public interface PartSchoolManager {
	

	/**查询合作学校数据
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> partschool_listPage(Page page)throws Exception;
	
	/**新增合作学校
	 * @param pd
	 * @throws Exception
	 */
	public void savePartSchool(PageData pd)throws Exception;
	
	/**删除合作学校
	 * @param pd
	 * @throws Exception
	 */
	public void deletePartSchoolByPkid(PageData pd)throws Exception;
	
	/**批量删除合作学校
	 * @param pd
	 * @throws Exception
	 */
	public void batchDelete(String[] pkids) throws Exception;
	
	/**
	 * 
	 * <p>描述:批量插入合作学校</p>
	 * @author Administrator wzz
	 * @date 2018年11月27日 下午2:29:34
	 */
	void batchSavePart(List<PageData> list)throws Exception;
	/**
	 * 
	 * <p>描述:根据合作学校名称查询合作学校</p>
	 * @author Administrator wzz
	 * @date 2018年12月10日 下午2:29:34
	 */
	PageData getPartSchoolByName(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:根据pkid查询合作学校</p>
	 * @author Administrator wzz
	 * @date 2018年12月10日 下午2:29:34
	 */
	PageData getPartSchoolByPkid(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:根据学校学校pkid查询是否已经被学生使用</p>
	 * @author Administrator wzz
	 * @date 2018年12月10日 下午2:29:34
	 */
	List<PageData> getStuinfoByPkid(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:根据学校学校pkidS查询是否已经被学生使用</p>
	 * @author Administrator wzz
	 * @date 2018年12月10日 下午2:29:34
	 */
	List<PageData> getStuinfoByPkids(String[] pkids)throws Exception;
	/**
	 * 
	 * <p>描述:根据pkid修改合作学校</p>
	 * @author Administrator wzz
	 * @date 2018年12月10日 下午2:29:34
	 */
	void updatePartSchoolByPkid(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:更新是否合作学校状态</p>
	 * @author wzz
	 * @date 2019年1月23日 下午3:58:51
	 * @param pd
	 * @throws Exception
	 */
	void updateIsHezuo(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:查询文化课学校导出</p>
	 * @author Administrator wzz
	 * @date 2019年1月23日 下午2:29:34
	 */
	List<PageData> exportPartSchoolList(PageData pd)throws Exception;

}
