package com.fh.service.system.userorg.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.service.system.userorg.UserorgManager;
import com.fh.util.PageData;

/**
 * 用户组织权限
 * 
 * @author zhangbin 修改时间：2017.2.13
 */
@Service("userorgService")
public class UserorgService implements UserorgManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 列出某用户的所有组织权限
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listUserorgs(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserOrgMapper.listUserorgs",
				pd);

	}

}
