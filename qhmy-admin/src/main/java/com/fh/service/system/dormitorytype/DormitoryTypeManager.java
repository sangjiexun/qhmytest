package com.fh.service.system.dormitorytype;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;


public interface DormitoryTypeManager {
	/**
	 * 
	 * <p>描述:获取宿舍类型表数据</p>
	 * @author Administrator 王宁
	 * @date 2018年3月9日 下午6:19:27
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> dormitorytype_list(Page page) throws Exception;
	/**
	 * 
	 * <p>描述:根据宿舍类型PKID获取宿舍类型数据</p>
	 * @author Administrator 王宁
	 * @date 2018年3月12日 下午2:23:52
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getDormitorytype(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:根据类型名称获取宿舍类型信息</p>
	 * @author Administrator 王宁
	 * @date 2018年3月12日 下午4:29:58
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getDT_NAME(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:编辑宿舍类型信息</p>
	 * @author Administrator 王宁
	 * @date 2018年3月12日 下午4:52:17
	 * @param pd
	 * @throws Exception
	 */
	void update(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述：新增宿舍类型信息</p>
	 * @author Administrator 王宁
	 * @date 2018年3月12日 下午4:54:50
	 * @param pd
	 * @throws Exception
	 */
	void save(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:更新宿舍类型启用状态</p>
	 * @author Administrator 王宁
	 * @date 2018年3月12日 下午6:27:02
	 * @param pd
	 * @throws Exception
	 */
	void updateIsUse(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:判断该宿舍类型允不允许删</p>
	 * @author Administrator 王宁
	 * @date 2018年3月14日 下午3:03:25
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getDormUse(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:删除宿舍类型</p>
	 * @author Administrator 王宁
	 * @date 2018年3月14日 下午3:15:36
	 * @param pd
	 * @throws Exception
	 */
	void delete(PageData pd)throws Exception;
	
}
