package com.fh.service.system.checkIn;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

public interface CheckInManager {
	/**
	 * 
	 * <p>
	 * 描述:获取学生列表
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月4日 下午1:52:48
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> stulist(Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:更新学生的入住状态
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月5日 上午9:06:43
	 * @param pd
	 * @throws Exception
	 */
	void updateRz(PageData pd) throws Exception;

}
