package com.fh.service.system.learningSubjects;

import java.util.List;
import java.util.Map;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 
 * <p>
 * 标题:LearningSubjectsManager
 * </p>
 * <p>
 * 描述:学习科目
 * </p>
 * <p>
 * 组织:河北科曼信息技术有限公司
 * </p>
 * 
 * @author Administrator 任笑达
 * @date 2018年11月27日 上午9:12:52
 */
public interface LearningSubjectsManager {
	/**
	 * 
	 * <p>
	 * 描述:增加
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月27日 上午9:13:09
	 * @param pd
	 */
	void save(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:删除
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月27日 上午9:13:17
	 * @param pd
	 */
	Map<String, Object> del(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:通过id查找单条 用于更新
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月27日 上午9:13:27
	 * @param pd
	 * @return
	 */
	PageData getSubById(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:更新
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月27日 上午9:13:49
	 * @param pd
	 */
	void update(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:更新是否已启用
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月27日 上午9:22:17
	 * @param pd
	 * @throws Exception
	 */
	void updateForIsqy(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:查询所有
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月27日 上午9:13:57
	 */
	List<PageData> sublist(Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:唯一性验证
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月5日 下午2:09:58
	 * @param NAME
	 * @return
	 * @throws Exception
	 */
	boolean getSubByName(PageData pd) throws Exception;

}
