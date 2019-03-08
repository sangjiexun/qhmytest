package com.fh.component;

import com.fh.component.impl.UpdatePayItemListCloseImpl;
import com.fh.component.impl.UpdatePayItemListOkImpl;
import com.fh.service.system.paymanage.PayManageManager;
import com.fh.util.PageData;

/**
 * <p>标题:UpdatePayItemListComponent</p>
 * <p>描述:更新名单表 更新动作组件</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月22日 下午7:14:26
 */
public class UpdatePayItemListComponent {
	
	
	/**
	 * <p>描述:更新名单表状态</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月22日 下午7:15:19
	 * @throws Exception
	 */
	public void updateStatus(PayManageManager payManageManager,PageData pageData) throws Exception{
		UpdatePayItemListStatusInterface updateOk = new UpdatePayItemListOkImpl();
		//UpdatePayItemListStatusInterface updateClose = new UpdatePayItemListCloseImpl();
		
		//updateClose.update(payManageManager, pageData);
		updateOk.update(payManageManager, pageData);
	}
	
}
