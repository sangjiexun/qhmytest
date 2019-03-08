package com.fh.service.system.accountnature;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

public interface AccoutNatureManager {

	List<PageData> poor_list(Page page) throws Exception;

	PageData getpoor(PageData pd) throws Exception;

	PageData getBianMa(PageData pd) throws Exception;

	void update(PageData pd) throws Exception;

	void save(PageData pd) throws Exception;

	void updateIsUse(PageData pd) throws Exception;

	PageData getpoor_NAME(PageData pd) throws Exception;

	void del(PageData pd) throws Exception;

	PageData getIsUsed(PageData pd) throws Exception;

}
