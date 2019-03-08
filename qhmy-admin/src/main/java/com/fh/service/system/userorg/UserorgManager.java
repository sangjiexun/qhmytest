package com.fh.service.system.userorg;

import java.util.List;

import com.fh.util.PageData;


/** 用户组织权限
 * @author zhangbin
 * 修改时间：2017.2.13
 */
public interface UserorgManager {
	
	/**列出某角色下的所有用户
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listUserorgs(PageData pd) throws Exception;
	

}
