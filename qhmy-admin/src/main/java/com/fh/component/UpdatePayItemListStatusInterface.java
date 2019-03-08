package com.fh.component;

import com.fh.service.system.paymanage.PayManageManager;
import com.fh.util.PageData;

/**
 * <p>标题:UpdatePayItemListStatusInterface</p>
 * <p>描述:更新t_pay_item_list表状态</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月22日 下午6:57:18
 */
public interface UpdatePayItemListStatusInterface {
	
	void update(PayManageManager payManageManager,PageData pageData) throws Exception;
	
}
