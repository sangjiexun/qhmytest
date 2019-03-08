package com.fh.service.system.family;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

public interface FamilyManager {
	/**
	 * 
	 * <p>描述:获取列表</p>
	 * @author wn 王宁
	 * @date 2018年11月27日 下午3:37:03
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> familylist(Page page) throws Exception;
	/**
	 * 
	 * <p>描述:获取单个信息</p>
	 * @author wn 王宁
	 * @date 2018年11月27日 下午3:37:16
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getFamily(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述: 编辑保存</p>
	 * @author wn 王宁
	 * @date 2018年11月27日 下午3:50:53
	 * @param pd
	 * @throws Exception
	 */
	void update(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:新增保存</p>
	 * @author wn 王宁
	 * @date 2018年11月27日 下午3:51:23
	 * @param pd
	 * @throws Exception
	 */
	void save(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:更新启用状态</p>
	 * @author wn 王宁
	 * @date 2018年11月27日 下午3:58:51
	 * @param pd
	 * @throws Exception
	 */
	void updateIsUse(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:根据id查询学生</p>
	 * @author wn 王宁
	 * @date 2018年11月27日 下午4:06:33
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getStuFamily(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:删除操作</p>
	 * @author wn 王宁
	 * @date 2018年11月27日 下午4:17:50
	 * @param pd
	 * @throws Exception
	 */
	void delete(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:根据家庭关系名称获取数据</p>
	 * @author wn 王宁
	 * @date 2018年12月19日 下午4:56:19
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getFamilybyName(PageData pd)throws Exception;

}
