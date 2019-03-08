package com.fh.component.impl;

import com.fh.component.UpdatePayItemListStatusInterface;
import com.fh.service.system.paymanage.PayManageManager;
import com.fh.util.PageData;

/**
 * 
 * <p>标题:UpdatePayItemListCloseImpl</p>
 * <p>描述:更新成已关闭状态</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月22日 下午6:59:35
 */
public class UpdatePayItemListCloseImpl implements UpdatePayItemListStatusInterface {
	
	@Override
	public void update(PayManageManager payManageManager,PageData pageData) throws Exception {
		// TODO Auto-generated method stub
		payManageManager.updatePayItemListStatusClose(pageData);
	}

}
