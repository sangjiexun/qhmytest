package com.fh.service.system.schoolYear;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

public interface SchoolYearManager {
	/**
	 * 
	 * <p>描述:获取列表</p>
	 * @author 柴飞
	 * @date 2018年11月28日15:17:14
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> schoolYearlist(Page page) throws Exception;
	/**
	 * 
	 * <p>描述:根据id获取入学年份</p>
	 * @author 柴飞
	 * @date 2018年11月28日15:18:06
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getSchoolYearById(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:根据名称获取入学年份</p>
	 * @author Administrator 柴飞
	 * @date 2018年12月24日 上午11:37:09
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getSchoolYearByName(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述: 编辑保存</p>
	 * @author 柴飞
	 * @date 2018年11月28日15:20:13
	 * @param pd
	 * @throws Exception
	 */
	void update(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:新增保存</p>
	 * @author 柴飞
	 * @date 2018年11月28日15:20:20
	 * @param pd
	 * @throws Exception
	 */
	void save(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:更新启用状态</p>
	 * @author 柴飞
	 * @date 2018年11月28日15:20:29
	 * @param pd
	 * @throws Exception
	 */
	void updateIsUse(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:根据id查询学生</p>
	 * @author 柴飞
	 * @date 2018年11月28日15:21:00
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getStuSchoolYear(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:删除操作</p>
	 * @author 柴飞
	 * @date 2018年11月28日15:21:14
	 * @param pd
	 * @throws Exception
	 */
	void delete(PageData pd) throws Exception;
}
