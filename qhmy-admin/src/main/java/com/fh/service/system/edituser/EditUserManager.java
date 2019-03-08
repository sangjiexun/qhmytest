package com.fh.service.system.edituser;

import com.fh.util.PageData;

public interface EditUserManager {

	/**
	 * 修改密码
	 */
	void updatePwd(PageData pd) throws Exception;
}
